package xn.pigfarm.service.expimp.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.BeanUtil;
import xn.core.util.ParamUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.excel.ExcelUtil;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.initialization.InputHouseException;
import xn.pigfarm.exception.initialization.InputPigException;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.basicinfo.MaterialMapper;
import xn.pigfarm.mapper.basicinfo.PigpenMapper;
import xn.pigfarm.mapper.initialization.PpInputPigMapper;
import xn.pigfarm.mapper.production.SwineryMapper;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PigpenModel;
import xn.pigfarm.model.initialization.PpInputPigModel;
import xn.pigfarm.model.production.SwineryModel;
import xn.pigfarm.service.basicinfo.IBasicInfoService;
import xn.pigfarm.service.expimp.IExcelExportService;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.ExpImpConstants;
import xn.pigfarm.util.constants.MaterialConstants;
import xn.pigfarm.util.constants.PigConstants;

@Service("excelExportModel")
public class ExcelExportServiceImpl extends BaseServiceImpl implements IExcelExportService {

    @Autowired
    private PpInputPigMapper ppInputPigMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private PigpenMapper pigpenMapper;

    @Autowired
    private SwineryMapper swineryMapper;

    @Autowired
    private IBasicInfoService basicInfoService;

    @Override
    public void exportPig(HttpServletRequest request, String xmlName, String pigType) throws Exception {

        List<Map<String, Object>> uploadData = ExcelUtil.excelExport(request, xmlName);
        if (uploadData.isEmpty()) {
            Thrower.throwException(BaseBusiException.EXCEL_IMPORT_FILE_ERROR);
        }
        // 准备插入数据库导入数据
        List<PpInputPigModel> listPig = new ArrayList<>();

        /* 查询物料主数据，用来验证数据的正确性 */
        // 根据导入的类型查询相应的导入主数据
        String materialTypeBoarOrSow = MaterialConstants.MATERIAL_SOW;
        String houseClass = PigConstants.HOUSE_CLASS_ISOLATION + "," + PigConstants.HOUSE_CLASS_MOTHBALL + "," + PigConstants.HOUSE_CLASS_NONPREGNANT
                + "," + PigConstants.HOUSE_CLASS_AGMINPREGNANCY + "," + PigConstants.HOUSE_CLASS_PREGNANCY + "," + PigConstants.HOUSE_CLASS_DELIVERY;

        if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
            materialTypeBoarOrSow = MaterialConstants.MATERIAL_BOAR;
            houseClass = PigConstants.HOUSE_CLASS_ISOLATION + "," + PigConstants.HOUSE_CLASS_MOTHBALL + "," + PigConstants.HOUSE_CLASS_BOAR;
        }
        SqlCon sqlConMater = new SqlCon();
        sqlConMater.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sqlConMater.addCondition(materialTypeBoarOrSow, " AND MATERIAL_TYPE = ?");
        List<MaterialModel> materialList = getList(materialMapper, sqlConMater);
        // 存物料ID
        Set<Long> materialSet = new HashSet<>();
        for (MaterialModel mm : materialList) {
            materialSet.add(mm.getRowId());
        }

        /* 查询猪舍，用来验证数据的正确性 */
        /* 猪舍 */
        SqlCon sqlConHouse = new SqlCon();
        sqlConHouse.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlConHouse.addCondition(houseClass, " AND HOUSE_TYPE IN ?", false, true);
        List<HouseModel> houseList = getList(houseMapper, sqlConHouse);
        if (houseList.isEmpty()) {
            Thrower.throwException(InputPigException.HAS_NO_HOUSE);
        }
        // 存猪舍ID
        Set<Long> houseSet = new HashSet<>();
        for (HouseModel house : houseList) {
            houseSet.add(house.getRowId());
        }

        /* 验证数据 */
        // 有未执行数据提示先执行
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" AND STATUS='1' ");
        List<PpInputPigModel> PpInputHisList = getList(ppInputPigMapper, sqlCon);
        if (!PpInputHisList.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "已导入数据，请先执行");
        }

        // 错误信息
        Map<String, StringBuffer> errorMessage = new HashMap<>();

        // 存放导入单的母猪耳牌号、胎次。母猪一个耳牌号胎次不能重复
        Map<String, Set<Long>> sowBrandMap = new HashMap<>();
        // 存放导入单的公猪耳牌号。公猪耳号不能重复
        Set<String> boarBrandSet = new HashSet<>();

        long rowIndex = 1;
        for (Map<String, Object> map : uploadData) {
            // 为插入数据库model赋值
            PpInputPigModel pigModel = BeanUtil.getBean(PpInputPigModel.class, map);
            pigModel.setEnterDate(TimeUtil.getSysTimestamp());
            pigModel.setSortNbr(rowIndex++);
            pigModel.setPigType(pigType);
            pigModel.setFarmId(getFarmId());
            pigModel.setCompanyId(getCompanyId());
            pigModel.setCreateDate(TimeUtil.getSysTimestamp());
            pigModel.setCreateId(getEmployeeId());

            /* 公共验证 */
            // 必填项验证
            verifyExportPig(pigModel, rowIndex, errorMessage);
            // 输入的物料必须系统中存在
            putSetErrorMessage(pigModel.getMaterialId(), ExpImpConstants.INPUT_PIG_MATIRAL_NOT_MATCH, rowIndex, materialSet, errorMessage, false);
            // 输入的猪舍必须系统中存在
            putSetErrorMessage(pigModel.getHouseId(), ExpImpConstants.INPUT_PIG_HOUSE_NOT_MATCH, rowIndex, houseSet, errorMessage, false);

            /* 公猪 */
            if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
                pigModel.setParity(null);
                // 导入单据中耳号不可以重复
                String earBrand = pigModel.getEarBrand();
                if (!boarBrandSet.contains(earBrand)) {
                    boarBrandSet.add(earBrand);
                } else {
                    putErrorMessage(ExpImpConstants.INPUT_PIG_EAR_BRAND_REPEAT, rowIndex, errorMessage);
                }
                // 公猪出生日期必填
                if (pigModel.getBirthDate() == null) {
                    putErrorMessage(ExpImpConstants.INPUT_PIG_BOAR_BIRTH, rowIndex, errorMessage);
                }
            }
            /* 母猪 */
            else {
                // 胎次相关验证
                verifyExportPigSowParity(pigModel, rowIndex, sowBrandMap, errorMessage);
                // 一项填了，另几项必填验证
                verifyExportPigReq(pigModel, rowIndex, errorMessage);
            }
            listPig.add(pigModel);
        }

        // 验证错误信息
        verifyErrorMessage(errorMessage);

        ppInputPigMapper.inserts(listPig);
    }

    @Override
    public List<Map<String, Object>> earBlandUpload(HttpServletRequest request, String xmlName, String pigType) throws Exception {
        List<Map<String, Object>> uploadData = ExcelUtil.excelExport(request, xmlName);
        return uploadData;
    }


    /**
     * @Description: 验证错误信息，并抛出相应异常
     * @author zhangjs
     * @throws Exception
     */
    private void verifyErrorMessage(Map<String, StringBuffer> errorMessage) throws Exception {
        if (errorMessage.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (Entry<String, StringBuffer> entry : errorMessage.entrySet()) {
            builder.append("{");
            builder.append("第");
            builder.append(entry.getValue().toString());
            builder.append("行错误:");
            builder.append(ExpImpConstants.INPUT_PIG_ERROR_MAP.get(entry.getKey()));
            builder.append("}");
        }
        Thrower.throwException(InputPigException.INPUT_PIG_ERROR, builder.toString());
    }
    
    /**
     * @Description: 公共验证
     * @author zhangjs
     * @param pigModel
     * @param rowIndex
     * @param errorMessage
     */
    private void verifyExportPig(PpInputPigModel pigModel, long rowIndex, Map<String, StringBuffer> errorMessage) {
        // 1、耳号必填
        if (StringUtil.isBlank(pigModel.getEarBrand())) {
            putErrorMessage(ExpImpConstants.INPUT_PIG_EB_NULL, rowIndex, errorMessage);
        }
        // 2、物料主数据必填
        if (pigModel.getMaterialId() == null || pigModel.getMaterialId() <= 0) {
            putErrorMessage(ExpImpConstants.INPUT_PIG_MATIRAL_NULL, rowIndex, errorMessage);
        }
        // 3、猪舍必填
        if (pigModel.getHouseId() == null || pigModel.getHouseId() <= 0) {
            putErrorMessage(ExpImpConstants.INPUT_PIG_HOUSE_NULL, rowIndex, errorMessage);
        }

    }

    /**
     * @Description: 胎次相关验证
     * @author zhangjs
     * @param pigModel
     * @param rowIndex
     * @param sowBrandMap
     * @param errorMessage
     */
    private void verifyExportPigSowParity(PpInputPigModel pigModel, long rowIndex,  Map<String, Set<Long>> sowBrandMap ,Map<String, StringBuffer> errorMessage) {
        Long parity = pigModel.getParity();
        /* 1、胎次必填 */
        if (parity == null) {
            putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_PARITY_NULL, rowIndex, errorMessage);
        } else {
            /* 2、 母猪同一耳号胎次不能重复 */
            String earBrand = pigModel.getEarBrand();
            Set<Long> listParity = new HashSet<>();
            if (sowBrandMap.get(earBrand) == null) {
                listParity.add(parity);
                sowBrandMap.put(earBrand, listParity);
            } else {
                listParity = sowBrandMap.get(earBrand);
                if (!listParity.contains(parity)) {
                    listParity.add(parity);
                } else {
                    putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_PARITY_REPEAT, rowIndex, errorMessage);
                }
            }

            /* 3、 配种日期和出生日期必须填一个 */
            if (pigModel.getBreedDate() == null && pigModel.getBirthDate() == null) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_BIRTH_BREED, rowIndex, errorMessage);
            }

            /* 出生日期未填计算出生日期 */
            else if (pigModel.getBirthDate() == null) {
                Date birthDate = TimeUtil.dateAddDay(pigModel.getBreedDate(), (int) (-150 * parity - 240));
                pigModel.setBirthDate(birthDate);
            }
        }
    }

    /**
     * @Description: 一项填了，另几项必填验证
     * @author zhangjs
     */
    private void verifyExportPigReq(PpInputPigModel pigModel, long rowIndex, Map<String, StringBuffer> errorMessage) {

        // 配种日期填了
        if (pigModel.getBreedDate() != null) {
            // 配种公猪必填,不然报错
            if (pigModel.getBreedBoar() == null) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_BREED, rowIndex, errorMessage);
            }
            // 配种日期必须大于出生日期,不然报错
            if (TimeUtil.compareDate(pigModel.getBreedDate(), pigModel.getBirthDate(), Calendar.DATE) <= 0) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_BREED_DATE, rowIndex, errorMessage);
            }
        }
        // 转产日期填了
        if (pigModel.getChangeHouseDate() != null) {
            // 产房必填,不然报错
            if (pigModel.getChangeHouseId() == null) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_CHANGE_HOUSE, rowIndex, errorMessage);
            }
            //栏位必填,不然报错
            if(pigModel.getChangePigpenId() == null){
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_CHANGE_HOUSE_COLUMN, rowIndex, errorMessage);
            }
            // 转产房日期必须大于配种日期
            if (TimeUtil.compareDate(pigModel.getChangeHouseDate(), pigModel.getBreedDate(), Calendar.DATE) < 0) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_CHANGE_HOUSE_DATE, rowIndex, errorMessage);
            }
        }

        // 分娩日期必须大于转产日期,不然报错
        if (pigModel.getDeliveryDate() != null && TimeUtil.compareDate(pigModel.getDeliveryDate(), pigModel.getChangeHouseDate(),
                Calendar.DATE) <= 0) {
            putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_DELIVERY_DATE, rowIndex, errorMessage);
        }

        // 断奶日期填了，分娩日期必须填,不然报错
        if (pigModel.getWeanDate() != null && pigModel.getDeliveryDate() == null) {
            putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_WEAN_DELIVERY, rowIndex, errorMessage);
        }

        // 断奶日期填了
        if (pigModel.getWeanDate() != null) {
            // 断奶窝重、断奶数必须填,不然报错
            if (pigModel.getWeanNum() == null || pigModel.getWeanWeight() == null) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_WEAN, rowIndex, errorMessage);
            }
            // 断奶日期必须大于分娩日期
            if (TimeUtil.compareDate(pigModel.getWeanDate(), pigModel.getDeliveryDate(), Calendar.DATE) < 0) {
                putErrorMessage(ExpImpConstants.INPUT_PIG_SOW_WEAN_DATE, rowIndex, errorMessage);
            }
        }
    }
    
    /**
     * @Description: set中是否有id
     * @author zhangjs
     * @param <E>
     * @param id
     * @param key
     * @param rowIndex
     * @param set
     * @param errorMessage
     * @param flag true 包含报错 false 不包含报错
     */
    private <E> void putSetErrorMessage(E id, String key, long rowIndex, Set<E> set, Map<String, StringBuffer> errorMessage, boolean flag) {
        if (flag && set.contains(id)) {
                putErrorMessage(key, rowIndex, errorMessage);
        } else if (!flag && !set.contains(id)) {
            putErrorMessage(key, rowIndex, errorMessage);
        }
    }

    /**
     * @Description: 错误信息放入MAP中
     * @author zhangjs
     * @param errorMessage
     * @param key
     * @param rowIndex
     */
    private void putErrorMessage(String key, long rowIndex, Map<String, StringBuffer> errorMessage) {
        if (errorMessage.get(key) == null) {
            StringBuffer buffer = new StringBuffer("【" + rowIndex + "】");
            errorMessage.put(key, buffer);
        }
    }

    @Override
    public List<Map<String, Object>> houseUpload(HttpServletRequest request, String xmlName) throws Exception {
        List<Map<String, Object>> uploadData = ExcelUtil.excelExport(request, xmlName);
        Set<String> pigpenNameSet = new HashSet<String>();
        if (uploadData.isEmpty()) {
            Thrower.throwException(BaseBusiException.EXCEL_IMPORT_FILE_ERROR);
        }
        long rowIndex = 1;
        for (Map<String, Object> map : uploadData) {
            rowIndex++;
            if (Maps.getString(map, "houseType") == null) {
                Thrower.throwException(InputHouseException.INPUT_HOUSE_TYPE_NULL, rowIndex);
            }
            long nameIsExist = ParamUtil.isExist("pp_o_house",null, new String[] { Maps.getString(map, "houseName"), getFarmId()
                    + "" }, "HOUSE_NAME, FARM_ID");
            if (nameIsExist > 0) {
                Thrower.throwException(InputHouseException.INPUT_HOUSE_NAME_REPEAT, rowIndex);
            }
            if (Maps.getLongClass(map, "pigNum") == null && Maps.getString(map, "pigpenName") == null && Maps.getLongClass(map, "length") == null
                    && Maps.getLongClass(map, "width") == null) {
                continue;// 如果猪栏没填写任何信息，则跳过
            }
            if (StringUtil.isBlank(Maps.getString(map, "pigpenName"))) {
                Thrower.throwException(InputHouseException.INPUT_PIGPEN_NAME_NULL, rowIndex);
            }
            // 猪栏名称不能重复
            if (!pigpenNameSet.contains(Maps.getString(map, "pigpenName") + Maps.getString(map, "houseName"))) {
                pigpenNameSet.add(Maps.getString(map, "pigpenName") + Maps.getString(map, "houseName"));
            } else {
                Thrower.throwException(InputHouseException.INPUT_PIGPEN_NAME_REPEAT, rowIndex);
            }
        }
        return uploadData;
    }

    public List<Map<String, Object>> porkUpload(HttpServletRequest request, String xmlName) throws Exception {
        List<Map<String, Object>> uploadData = ExcelUtil.excelExport(request, xmlName);
        if (uploadData.isEmpty()) {
            Thrower.throwException(BaseBusiException.EXCEL_IMPORT_FILE_ERROR);
        }
        /* 物料主数据 */
        SqlCon sqlConz = new SqlCon();
        sqlConz.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlConz.addCondition(MaterialConstants.MATERIAL_PORK_PIG, " AND MATERIAL_TYPE = ?");
        List<MaterialModel> listMaterial = getList(materialMapper, sqlConz);
        Set<Long> materialIds = new HashSet<>();
        for (MaterialModel materialModel : listMaterial) {
            materialIds.add(materialModel.getRowId());
        }
        /* 猪舍 */
        SqlCon sqlConHouse = new SqlCon();
        sqlConHouse.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        String houseClass = PigConstants.HOUSE_CLASS_CAREPIG + "," + PigConstants.HOUSE_CLASS_FATTEN;
        sqlConHouse.addCondition(houseClass, " AND HOUSE_TYPE IN ?", false, true);
        List<HouseModel> searchHouseToPage = getList(houseMapper, sqlConHouse);
        Set<Long> houseIds = new HashSet<>();
        for (HouseModel houseModel : searchHouseToPage) {
            houseIds.add(houseModel.getRowId());
        }
        /* 猪栏 */
        StringBuffer houseId = new StringBuffer();
        boolean addComma = false;
        for (HouseModel Model : searchHouseToPage) {
            Long rowId = Model.getRowId();
            if (addComma) {
                houseId.append("," + rowId);
            } else {
                houseId.append(rowId);
                addComma = true;
            }
        }
        SqlCon sqlConl = new SqlCon();
        sqlConl.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sqlConl.addCondition(houseId.toString(), " AND HOUSE_ID IN ? ", false, true);
        List<PigpenModel> searchListByCon = getList(pigpenMapper, sqlConl);
        Set<Long> pigpenIds = new HashSet<>();
        for (PigpenModel pigpenModel : searchListByCon) {
            pigpenIds.add(pigpenModel.getRowId());
        }
        // 批次
        SqlCon swinerySql = new SqlCon();
        swinerySql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        swinerySql.addCondition(PigConstants.PIG_TYPE_PORK, " AND PIG_TYPE = ? ");
        swinerySql.addCondition(PigConstants.SWINERY_CREATE_TYPE_SELF, " AND CREATE_TYPE = ? ");
        List<SwineryModel> swinerys = getList(swineryMapper, swinerySql);
        Set<Long> swineryIds = new HashSet<>();
        for (SwineryModel swineryModel : swinerys) {
            swineryIds.add(swineryModel.getRowId());
        }
        // 供应商
        List<Map<String, String>> searchCustomerAndSupplierToList = basicInfoService.searchCustomerAndSupplierToList(
                CompanyConstants.CUS_SUP_TYPE_SUP);
        Set<Long> supplierIds = new HashSet<>();
        for (Map<String, String> map : searchCustomerAndSupplierToList) {
            supplierIds.add(Maps.getLong(map, "rowId"));
        }
        // 错误信息
        Map<String, StringBuffer> errorMessage = new HashMap<>();
        long rowIndex = 1;
        for (Map<String, Object> map : uploadData) {
            rowIndex++;
            if (Maps.getLongClass(map, "materialId") == null) {
                Thrower.throwException(InputPigException.MATERIAL_IS_NULL, rowIndex);
            }
            putSetErrorMessage(Maps.getLongClass(map, "materialId"), ExpImpConstants.INPUT_PIG_MATIRAL_NOT_MATCH, rowIndex, materialIds, errorMessage,
                    false);
            if (Maps.getLongClass(map, "houseId") == null) {
                Thrower.throwException(InputPigException.HOUSE_IS_NULL, rowIndex);
            }
            putSetErrorMessage(Maps.getLongClass(map, "houseId"), ExpImpConstants.INPUT_PIG_HOUSE_NOT_MATCH, rowIndex, houseIds, errorMessage,
                    false);
            if (Maps.getLongClass(map, "pigpenId") != null) {
                putSetErrorMessage(Maps.getLongClass(map, "pigpenId"), ExpImpConstants.INPUT_PIG_PIGPEN_NOT_MATCH, rowIndex, pigpenIds,
                        errorMessage, false);
            }
            if (Maps.getLongClass(map, "swineryId") == null) {
                Thrower.throwException(InputPigException.SWINERY_IS_NULL, rowIndex);
            }
            putSetErrorMessage(Maps.getLongClass(map, "swineryId"), ExpImpConstants.INPUT_PIG_SWINERY_NOT_MATCH, rowIndex, swineryIds, errorMessage,
                    false);
            if (Maps.getLongClass(map, "pigClass") == null) {
                Thrower.throwException(InputPigException.PIG_CLASS_IS_NULL, rowIndex);
            }
            if (Maps.getString(map, "countDate") == null) {
                Thrower.throwException(InputPigException.ENTER_DATE_IS_NULL, rowIndex);
            }
            if (Maps.getLongClass(map, "dayAge") == null) {
                Thrower.throwException(InputPigException.DAY_AGE_IS_NULL, rowIndex);
            }
            if (Maps.getLongClass(map, "totalNum") == null || Maps.getLongClass(map, "totalNum") == 0) {
                Thrower.throwException(InputPigException.TOTAL_NUM_IS_NULL, rowIndex);
            }
            if (Maps.getDoubleClass(map, "avWeight") == null) {
                Thrower.throwException(InputPigException.AV_WEIGHT_IS_NULL, rowIndex);
            }
            if (Maps.getLongClass(map, "supplierId") != null) {
                putSetErrorMessage(Maps.getLongClass(map, "supplierId"), ExpImpConstants.INPUT_PIG_SUPPLIER_NOT_MATCH, rowIndex, supplierIds,
                        errorMessage, false);
            }
        }
        verifyErrorMessage(errorMessage);
        return uploadData;
    }

    @SuppressWarnings("unused")
    @Override
    public List<Map<String, Object>> exportMaterialTemplatePork(HttpServletRequest request, String xmlName) throws Exception {
        List<Map<String, Object>> uploadData = ExcelUtil.excelExport(request, xmlName);
        Set<String> pigpenNameSet = new HashSet<String>();
        if (uploadData.isEmpty()) {
            Thrower.throwException(BaseBusiException.EXCEL_IMPORT_FILE_ERROR);
        }
        long rowIndex = 1;
        for (Map<String, Object> map : uploadData) {
            rowIndex++;
        }
        return uploadData;
    }
}
