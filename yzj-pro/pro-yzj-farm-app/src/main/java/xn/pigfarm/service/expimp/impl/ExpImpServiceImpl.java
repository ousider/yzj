package xn.pigfarm.service.expimp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.service.expimp.IExpImpService;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.CodeListMapper;
import xn.pigfarm.mapper.backend.PigHouseMapper;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.basicinfo.MaterialGroupMapper;
import xn.pigfarm.mapper.basicinfo.MaterialMapper;
import xn.pigfarm.mapper.basicinfo.PigpenMapper;
import xn.pigfarm.mapper.production.SearchSemenMapper;
import xn.pigfarm.mapper.production.SwineryMapper;
import xn.pigfarm.model.backend.BreedModel;
import xn.pigfarm.model.backend.CodeListModel;
import xn.pigfarm.model.backend.PigClassModel;
import xn.pigfarm.model.backend.PigHouseModel;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.MaterialGroupModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PigpenModel;
import xn.pigfarm.model.production.SearchSemenModel;
import xn.pigfarm.model.production.SwineryModel;
import xn.pigfarm.model.production.ValidSemenModel;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.service.basicinfo.IBasicInfoService;
import xn.pigfarm.service.basicinfo.IMaterialService;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.ExpImpConstants;
import xn.pigfarm.util.constants.MaterialConstants;
import xn.pigfarm.util.constants.PigConstants;

@Service("ExpImpService")
public class ExpImpServiceImpl extends BaseServiceImpl implements IExpImpService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private IMaterialService materialService;

    @Autowired
    private IBackEndService backEndService;

    @Autowired
    private IBasicInfoService basicInfoService;

    @Autowired
    private SearchSemenMapper searchSemenMapper;

    @Autowired
    private PigpenMapper pigpenMapper;

    @Autowired
    private PigHouseMapper pigHouseMapper;

    @Autowired
    private SwineryMapper swineryMapper;

    @Autowired
    private MaterialGroupMapper materialGroupMapper;
    
    @Autowired
    private CodeListMapper codeListMapper;

    @Override
    public Map<String, List<Map<String, String>>> getHiddenData(Map<String, Object> inparam) throws Throwable {

        switch (Maps.getString(inparam, "typeName")) {

        // 猪只入场导出
        case ExpImpConstants.EAR_BLAND_INITIALIZE:
            return getEarInitHiddenData(inparam);

        // 猪只导入初始化导出模板
        case ExpImpConstants.INPUT_PIG:
            String pigType = Maps.getString(inparam, "pigType");
            if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
                return getInputPigHiddenDataBoar(pigType);

            } else if (PigConstants.PIG_TYPE_SOW.equals(pigType)) {
                return getInputPigHiddenDataSow(getFarmId(), pigType);
            }
            break;

        // 猪舍初始化导出模板
        case ExpImpConstants.INPUT_HOUSE:
            return getHouseInitHiddenData(inparam);
        // 肉猪初始化导出模板
        case ExpImpConstants.MATERIAL_TEMPLATE_PORK:
            return getMaterialTemplatePorkHiddenData(inparam);
        default:
            break;
        }
        return null;
    }

    /**
     * @Description: 导出猪只初始化母猪下拉框
     * @author 程彬
     * @param farmId
     * @param pigType
     * @return
     * @throws Exception
     */
    private Map<String, List<Map<String, String>>> getInputPigHiddenDataSow(Long farmId, String pigType) throws Exception {
        Map<String, List<Map<String, String>>> mapParent = new HashMap<>();

        /* 猪舍 */
        List<Map<String, String>> modelList = new ArrayList<>();
        SqlCon sqlConHouse = new SqlCon();
        sqlConHouse.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        String houseClass = PigConstants.HOUSE_CLASS_ISOLATION + "," + PigConstants.HOUSE_CLASS_MOTHBALL + "," + PigConstants.HOUSE_CLASS_NONPREGNANT
                + "," + PigConstants.HOUSE_CLASS_AGMINPREGNANCY + "," + PigConstants.HOUSE_CLASS_PREGNANCY + "," + PigConstants.HOUSE_CLASS_DELIVERY;
        sqlConHouse.addCondition(houseClass.toString(), " AND HOUSE_TYPE IN ?", false, true);

        List<HouseModel> searchHouseToPage = getList(houseMapper, sqlConHouse);
        if (searchHouseToPage.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪舍");
        }
        StringBuffer houseId = new StringBuffer();
        boolean addComma = false;
        for (HouseModel Model : searchHouseToPage) {
            Long rowId = Model.getRowId();
            houseId = addComma == true ? houseId.append("," + rowId) : houseId.append(rowId);
            addComma = true;
            String houseName = Model.getHouseName();
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(rowId));
            model.put("name", houseName);
            modelList.add(model);
        }
        mapParent.put("C", modelList);

        /* 猪栏 */
        modelList = new ArrayList<>();
        SqlCon sqlConl = new SqlCon();
        sqlConl.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sqlConl.addCondition(houseId.toString(), " AND HOUSE_ID IN ? ", false, true);
        List<PigpenModel> searchListByCon = getList(pigpenMapper, sqlConl);
        for (PigpenModel mapCode : searchListByCon) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mapCode.getRowId()));
            model.put("supId", String.valueOf(mapCode.getHouseId()));
            model.put("name", mapCode.getPigpenName());
            modelList.add(model);
        }
        mapParent.put("E", modelList);

        /* 猪类别 */
        // modelList = new ArrayList<>();
        // List<Map<String, String>> codeList = CacheUtil.getComboCodeList("PIG_TYPE", null);
        // if (codeList.isEmpty()) {
        // Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪类别");
        // }
        // for (Map<String, String> mapCode : codeList) {
        // if (PigConstants.PIG_TYPE_SOW.equals(mapCode.get("codeValue"))) {
        // Map<String, String> model = new HashMap<>();
        // model.put("id", mapCode.get("codeValue"));
        // model.put("name", mapCode.get("codeName"));
        // modelList.add(model);
        // }
        // }
        // mapParent.put("H", modelList);

        /* 物料主数据 */
        modelList = new ArrayList<>();
        SqlCon sqlConz = new SqlCon();
        sqlConz.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sqlConz.addCondition(MaterialConstants.MATERIAL_SOW, " AND MATERIAL_TYPE = ?");
        List<MaterialModel> listMaterial = getList(materialMapper, sqlConz);
        if (listMaterial.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "物料主数据");
        }
        for (MaterialModel mat : listMaterial) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.getRowId()));
            model.put("name", mat.getMaterialName());
            modelList.add(model);
        }
        mapParent.put("H", modelList);

        /* 配种公猪 */
        modelList = new ArrayList<>();
        SearchSemenModel searchSemenModel = new SearchSemenModel();
        searchSemenModel.setCreateId(getEmployeeId());
        searchSemenModel.setCompanyId(getCompanyId());
        searchSemenModel.setFarmId(getFarmId());
        searchSemenModel.setOffset(0L);
        searchSemenModel.setQueryType(PigConstants.SEARCH_SEMEN_QUERY_TYPE_LIKE);
        searchSemenModel.setSememIds("");
        searchSemenModel.setBreedType(PigConstants.PIG_BREED_TYPE_NAT);
        searchSemenModel.setPagesize(50L);
        searchSemenModel.setBreedDate(TimeUtil.getSysTimestamp());
        List<ValidSemenModel> searchSemenToList = searchSemenMapper.searchSemenToList(searchSemenModel);
        if (searchSemenToList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "配种公猪");
        }
        for (ValidSemenModel mat : searchSemenToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.getPigId()));
            model.put("name", mat.getEarBrand());
            modelList.add(model);
        }
        mapParent.put("L", modelList);
        modelList = new ArrayList<>();

        /* 妊娠结果 */
        String typeCode = "PREGNANCY_RESULT";
        List<Map<String, String>> cdCodeList = CacheUtil.getComboCodeList(typeCode, null);
        if (cdCodeList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪类别");
        }
        for (Map<String, String> mapCode : cdCodeList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", mapCode.get("codeValue"));
            model.put("name", mapCode.get("codeName"));
            modelList.add(model);
        }
        mapParent.put("O", modelList);

        /* 产房 */
        StringBuffer sowHouseId = new StringBuffer();
        modelList = new ArrayList<>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(PigConstants.HOUSE_CLASS_DELIVERY, " AND HOUSE_TYPE = ?");
        List<HouseModel> listHouse = getList(houseMapper, sqlCon);
        if (listHouse.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "产房");
        }
        int sowAddComma = 0;
        for (HouseModel mat : listHouse) {
            Map<String, String> model = new HashMap<>();
            Long rowId = mat.getRowId();
            if (sowAddComma == 1) {
                sowHouseId.append("," + rowId);
            } else {
                sowHouseId.append(rowId);
                sowAddComma = 1;
            }
            model.put("id", String.valueOf(rowId));
            model.put("name", mat.getHouseName());
            modelList.add(model);
        }
        mapParent.put("R", modelList);

        /* 猪栏 */
        modelList = new ArrayList<>();
        SqlCon sqlConlo = new SqlCon();
        sqlConlo.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sqlConlo.addCondition(sowHouseId.toString(), " AND HOUSE_ID IN ? ", false, true);
        List<PigpenModel> searchListByCono = getList(pigpenMapper, sqlConl);
        for (PigpenModel mapCode : searchListByCon) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mapCode.getRowId()));
            model.put("supId", String.valueOf(mapCode.getHouseId()));
            model.put("name", mapCode.getPigpenName());
            modelList.add(model);
        }
        mapParent.put("T", modelList);
        return mapParent;
    }

    /**
     * @Description: 导出猪只初始化公猪下拉框
     * @author 程彬
     * @param farmId
     * @param pigType
     * @return
     * @throws Exception
     */
    private Map<String, List<Map<String, String>>> getInputPigHiddenDataBoar(String pigType) throws Exception {
        Map<String, List<Map<String, String>>> mapParent = new HashMap<>();

        /* 猪舍 */
        List<Map<String, String>> modelList = new ArrayList<>();
        SqlCon sqlConHouse = new SqlCon();
        sqlConHouse.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        String houseClass = PigConstants.HOUSE_CLASS_ISOLATION + "," + PigConstants.HOUSE_CLASS_MOTHBALL + "," + PigConstants.HOUSE_CLASS_BOAR;
        sqlConHouse.addCondition(houseClass, " AND HOUSE_TYPE IN ?", false, true);

        List<HouseModel> searchHouseToPage = getList(houseMapper, sqlConHouse);
        if (searchHouseToPage.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪舍");
        }

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
            String houseName = Model.getHouseName();
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(rowId));
            model.put("name", houseName);
            modelList.add(model);
        }
        mapParent.put("C", modelList);

        /* 猪栏 */
        modelList = new ArrayList<>();
        SqlCon sqlConl = new SqlCon();
        sqlConl.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sqlConl.addCondition(houseId.toString(), " AND HOUSE_ID IN ? ", false, true);

        List<PigpenModel> searchListByCon = getList(pigpenMapper, sqlConl);
        for (PigpenModel mapCode : searchListByCon) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mapCode.getRowId()));
            model.put("supId", String.valueOf(mapCode.getHouseId()));
            model.put("name", mapCode.getPigpenName());
            modelList.add(model);
        }
        mapParent.put("E", modelList);

        /* 猪类别 */
        // modelList = new ArrayList<>();
        // List<Map<String, String>> codeList = CacheUtil.getComboCodeList("PIG_TYPE", null);
        // if (codeList.size() == 0) {
        // // Thrower.throwException(CoreBusiException.NOTFOUND_HIDDEN_DATA);
        // Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪类别");
        // }
        // for (Map<String, String> mapCode : codeList) {
        //
        // if (PigConstants.PIG_TYPE_BOAR.equals(mapCode.get("codeValue"))) {
        // Map<String, String> model = new HashMap<>();
        // model.put("id", mapCode.get("codeValue"));
        // model.put("name", mapCode.get("codeName"));
        // modelList.add(model);
        // }
        // }
        // mapParent.put("G", modelList);

        /* 物料主数据 */
        modelList = new ArrayList<>();
        SqlCon sqlConz = new SqlCon();
        sqlConz.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlConz.addCondition(MaterialConstants.MATERIAL_BOAR, " AND MATERIAL_TYPE = ?");
        List<MaterialModel> listMaterial = getList(materialMapper, sqlConz);
        if (listMaterial.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "物料主数据");
        }
        for (MaterialModel mat : listMaterial) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.getRowId()));
            model.put("name", mat.getMaterialName());
            modelList.add(model);
        }
        mapParent.put("G", modelList);

        return mapParent;
    }

    /**
     * @Description: 猪只入场
     * @author 程彬
     * @param inparam
     * @return
     * @throws Throwable
     */
    private Map<String, List<Map<String, String>>> getEarInitHiddenData(Map<String, Object> inparam) throws Throwable {
        String pigType = Maps.getString(inparam, "pigType");
        String changeType = Maps.getString(inparam, "changeType");
        String houseType = Maps.getString(inparam, "houseType");
        Long lineId = Maps.getLongClass(inparam, "lineId");
        String eventName = Maps.getString(inparam, "eventName");
        String cussupType = Maps.getString(inparam, "cussupType");

        Map<String, List<Map<String, String>>> mapParent = new HashMap<>();

        List<Map<String, String>> modelList = new ArrayList<>();
        // 物料名称
        List<MaterialModel> searchMaterialToList = materialService.searchMaterialToList(pigType);
        if (searchMaterialToList.size() == 0) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "物料名称");
        }
        for (MaterialModel mat : searchMaterialToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.getRowId()));
            model.put("name", mat.getMaterialName());
            modelList.add(model);
        }
        mapParent.put("C", modelList);
        modelList = new ArrayList<>();
        // 猪舍名称
        List<HouseModel> searchHouseToList = basicInfoService.searchHouseToList(changeType, houseType, lineId, eventName, null);
        if (searchHouseToList.size() == 0) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪舍名称");
        }
        for (HouseModel mat : searchHouseToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", mat.getRowId() + "");
            model.put("name", mat.getHouseName());
            modelList.add(model);
        }
        // 需要补充的查询
        mapParent.put("H", modelList);
        modelList = new ArrayList<>();
        // 猪只状态
        List<PigClassModel> searchPigClassToList = backEndService.searchPigClassToList(pigType);
        if (searchPigClassToList.size() == 0) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪只状态");
        }
        for (PigClassModel mat : searchPigClassToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", mat.getRowId() + "");
            model.put("name", mat.getPigClassName());
            modelList.add(model);
        }
        // 需要补充的查询
        mapParent.put("J", modelList);
        modelList = new ArrayList<>();
        // 供应商
        List<Map<String, String>> searchCustomerAndSupplierToList = basicInfoService.searchCustomerAndSupplierToList(cussupType);
        if (searchCustomerAndSupplierToList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "供应商");
        }
        for (Map<String, String> mat : searchCustomerAndSupplierToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", mat.get("rowId"));
            model.put("name", mat.get("companyName"));
            modelList.add(model);
        }
        // 需要补充的查询
        mapParent.put("R", modelList);
        modelList = new ArrayList<>();

        return mapParent;
    }

    /**
     * @Description:猪舍初始化
     * @author Administrator
     * @param inparam
     * @return
     * @throws Throwable
     */
    public Map<String, List<Map<String, String>>> getHouseInitHiddenData(Map<String, Object> inparam) throws Throwable {
        Map<String, List<Map<String, String>>> mapParent = new HashMap<>();
        List<Map<String, String>> modelList = new ArrayList<>();
        // 猪舍类型
        List<PigHouseModel> pigHouseList = pigHouseMapper.searchToList();
        for (PigHouseModel houseModel : pigHouseList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(houseModel.getRowId()));
            model.put("name", houseModel.getHouseTypeName());
            modelList.add(model);
        }
        mapParent.put("B", modelList);
        modelList = new ArrayList<>();
        return mapParent;
    }

    /**
     * @Description: 肉猪主数据初始化
     * @author Administrator
     * @param inparam
     * @return
     * @throws Throwable
     */
    public Map<String, List<Map<String, String>>> getMaterialTemplatePorkHiddenData(Map<String, Object> inparam) throws Throwable {
        Map<String, List<Map<String, String>>> mapParent = new HashMap<>();
        List<Map<String, String>> modelList = new ArrayList<>();
        //物料类型
        SqlCon sqlhouseTypeName = new SqlCon();
        sqlhouseTypeName.addConditionWithNull("MATERIAL_TYPE", " AND TYPE_CODE = ?");
        sqlhouseTypeName.addCondition("PorkPig", " AND CODE_VALUE = ?");
       List<CodeListModel> codeListMapperList = getList(codeListMapper, sqlhouseTypeName);
        for (CodeListModel M : codeListMapperList) {
            String rowId = M.getCodeValue();
            String codeName = M.getCodeName();
            Map<String, String> model = new HashMap<>();
            model.put("id", rowId );
            model.put("name", codeName);
            modelList.add(model);
        }
        mapParent.put("B", modelList);
        
        // 物料组名称
        modelList = new ArrayList<>();
        SqlCon sqlMaterialGroup = new SqlCon();
        sqlMaterialGroup.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlMaterialGroup.addCondition("PorkPig", " AND MATERIAL_TYPE = ?");
        List<MaterialGroupModel> listmMaterialGroup = getList(materialGroupMapper, sqlMaterialGroup);
        for (MaterialGroupModel M : listmMaterialGroup) {
            Long rowId = M.getRowId();
            String groupName = M.getGroupName();
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(rowId));
            model.put("name", groupName);
            modelList.add(model);
        }
        if(listmMaterialGroup.size()==0){
            Map<String, String> model = new HashMap<>();
            model.put("id", "");
            model.put("name", "");
            modelList.add(model);
        }
        mapParent.put("D", modelList);

        // 仓库物料
        modelList = new ArrayList<>();
        Map<String, String> model = new HashMap<>();
        model.put("id", "Y");
        model.put("name", "是");
        modelList.add(model);
        Map<String, String> model2 = new HashMap<>();
        model2.put("id", "N");
        model2.put("name", "否");
        modelList.add(model2);
        mapParent.put("F", modelList);

        // 采购物料
        modelList = new ArrayList<>();
        Map<String, String> model3 = new HashMap<>();
        model3.put("id", "Y");
        model3.put("name", "是");
        modelList.add(model3);
        Map<String, String> model4 = new HashMap<>();
        model4.put("id", "N");
        model4.put("name", "否");
        modelList.add(model4);
        mapParent.put("H", modelList);

        // 销售物料
        modelList = new ArrayList<>();
        Map<String, String> model5 = new HashMap<>();
        model5.put("id", "Y");
        model5.put("name", "是");
        modelList.add(model5);
        Map<String, String> model6 = new HashMap<>();
        model6.put("id", "N");
        model6.put("name", "否");
        modelList.add(model6);
        mapParent.put("J", modelList);

        // 计量单位
        modelList = new ArrayList<>();
        List<Map<String, String>> listMaterial = CacheUtil.getComboCodeList("UNIT", null);
        for (Map<String, String> mat : listMaterial) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", mat.get("codeName"));
            mp.put("name", mat.get("codeName"));
            modelList.add(mp);
        }
        mapParent.put("L", modelList);

        // 供应商
        modelList = new ArrayList<>();
        List<Map<String, String>> searchCustomerAndSupplierToList = basicInfoService.searchCustomerAndSupplierToList(
                CompanyConstants.CUS_SUP_TYPE_SUP);
        for (Map<String, String> mat : searchCustomerAndSupplierToList) {
            Map<String, String> md = new HashMap<>();
            md.put("id", String.valueOf(mat.get("rowId")));
            md.put("name", mat.get("companyName"));
            modelList.add(md);
        }
        if(searchCustomerAndSupplierToList.size()==0){
            Map<String, String> md = new HashMap<>();
            md.put("id", "");
            md.put("name", "");
            modelList.add(md);
        }
        mapParent.put("O", modelList);

        // 品种
        modelList = new ArrayList<>();
        List<BreedModel> searchBreedToList = backEndService.searchBreedToList(null, null);
        for (BreedModel mat : searchBreedToList) {
            Map<String, String> md = new HashMap<>();
            md.put("id", String.valueOf(mat.getRowId()));
            md.put("name", mat.getBreedName());
            modelList.add(md);
        }
        mapParent.put("V", modelList);

        // 品系
        modelList = new ArrayList<>();
        List<Map<String, String>> listStrain = CacheUtil.getComboCodeList("STRAIN", null);
        for (Map<String, String> mat : listStrain) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", mat.get("codeValue"));
            mp.put("name", mat.get("codeName"));
            modelList.add(mp);
        }
        mapParent.put("X", modelList);

        // 体况
        modelList = new ArrayList<>();
        List<Map<String, String>> listBodyCondition = CacheUtil.getComboCodeList("BODY_CONDITION", null);
        for (Map<String, String> mat : listBodyCondition) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", mat.get("codeValue"));
            mp.put("name", mat.get("codeName"));
            modelList.add(mp);
        }
        mapParent.put("Z", modelList);

        // 毛色
        modelList = new ArrayList<>();
        List<Map<String, String>> listColor = CacheUtil.getComboCodeList("COLOR", null);
        for (Map<String, String> mat : listColor) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", mat.get("codeValue"));
            mp.put("name", mat.get("codeName"));
            modelList.add(mp);
        }
        mapParent.put("AB", modelList);

        // 父本
        modelList = new ArrayList<>();
        List<MaterialModel> searchMaterialToList = materialService.searchMaterialToList("1");
        for (MaterialModel mat : searchMaterialToList) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", String.valueOf(mat.getRowId()));
            mp.put("name", mat.getMaterialName());
            modelList.add(mp);
        }
        mapParent.put("AR", modelList);

        // 母本
        modelList = new ArrayList<>();
        List<MaterialModel> searchMaterialToList2 = materialService.searchMaterialToList("2");
        for (MaterialModel mat : searchMaterialToList2) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", String.valueOf(mat.getRowId()));
            mp.put("name", mat.getMaterialName());
            modelList.add(mp);
        }
        mapParent.put("AT", modelList);

        // 种公猪主数据
        modelList = new ArrayList<>();
        List<MaterialModel> searchMaterialToList3 = materialService.searchMaterialToList("1");
        for (MaterialModel mat : searchMaterialToList3) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", String.valueOf(mat.getRowId()));
            mp.put("name", mat.getMaterialName());
            modelList.add(mp);
        }
        mapParent.put("AV", modelList);

        // 种母猪主数据
        modelList = new ArrayList<>();
        List<MaterialModel> searchMaterialToList4 = materialService.searchMaterialToList("2");
        for (MaterialModel mat : searchMaterialToList4) {
            Map<String, String> mp = new HashMap<>();
            mp.put("id", String.valueOf(mat.getRowId()));
            mp.put("name", mat.getMaterialName());
            modelList.add(mp);
        }
        mapParent.put("AX", modelList);

        // 是否选种
        modelList = new ArrayList<>();
        Map<String, String> model7 = new HashMap<>();
        model7.put("id", "0");
        model7.put("name", "是");
        modelList.add(model7);
        Map<String, String> model8 = new HashMap<>();
        model8.put("id", "1");
        model8.put("name", "否");
        modelList.add(model8);
        mapParent.put("AZ", modelList);
        
        // 分娩区分性别
        modelList = new ArrayList<>();
        Map<String, String> model9 = new HashMap<>();
        model9.put("id", "0");
        model9.put("name", "是");
        modelList.add(model9);
        Map<String, String> model10 = new HashMap<>();
        model10.put("id", "1");
        model10.put("name", "否");
        modelList.add(model10);
        mapParent.put("BB", modelList);

        return mapParent;
    }

    public Map<String, List<Map<String, String>>> getPorkInitHiddenData(Map<String, Object> inparam) throws Throwable {
        Map<String, List<Map<String, String>>> mapParent = new HashMap<>();
        /* 猪舍 */
        List<Map<String, String>> modelList = new ArrayList<>();
        SqlCon sqlConHouse = new SqlCon();
        sqlConHouse.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        String houseClass = PigConstants.HOUSE_CLASS_CAREPIG + "," + PigConstants.HOUSE_CLASS_FATTEN;
        sqlConHouse.addCondition(houseClass, " AND HOUSE_TYPE IN ?", false, true);

        List<HouseModel> searchHouseToPage = getList(houseMapper, sqlConHouse);
        if (searchHouseToPage.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪舍");
        }

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
            String houseName = Model.getHouseName();
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(rowId));
            model.put("name", houseName);
            modelList.add(model);
        }
        mapParent.put("C", modelList);

        /* 猪栏 */
        // modelList = new ArrayList<>();
        // SqlCon sqlConl = new SqlCon();
        // sqlConl.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        // sqlConl.addCondition(houseId.toString(), " AND HOUSE_ID IN ? ", false, true);
        //
        // List<PigpenModel> searchListByCon = getList(pigpenMapper, sqlConl);
        // for (PigpenModel mapCode : searchListByCon) {
        // Map<String, String> model = new HashMap<>();
        // model.put("id", String.valueOf(mapCode.getRowId()));
        // model.put("supId", String.valueOf(mapCode.getHouseId()));
        // model.put("name", mapCode.getPigpenName());
        // modelList.add(model);
        // }
        // mapParent.put("E", modelList);

        // 批次
        modelList = new ArrayList<>();
        SqlCon swinerySql = new SqlCon();
        swinerySql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        swinerySql.addCondition(PigConstants.PIG_TYPE_PORK, " AND PIG_TYPE = ? ");
        swinerySql.addCondition(PigConstants.SWINERY_CREATE_TYPE_SELF, " AND CREATE_TYPE = ? ");
        List<SwineryModel> swinerys = getList(swineryMapper, swinerySql);

        if (swinerys.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "批次");
        }

        for (SwineryModel swineryModel : swinerys) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(swineryModel.getRowId()));
            model.put("name", swineryModel.getSwineryName());
            modelList.add(model);
        }
        mapParent.put("E", modelList);

        /* 物料主数据 */
        modelList = new ArrayList<>();
        SqlCon sqlConz = new SqlCon();
        sqlConz.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlConz.addCondition(MaterialConstants.MATERIAL_PORK_PIG, " AND MATERIAL_TYPE = ?");
        List<MaterialModel> listMaterial = getList(materialMapper, sqlConz);
        if (listMaterial.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "物料主数据");
        }
        for (MaterialModel mat : listMaterial) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.getRowId()));
            model.put("name", mat.getMaterialName());
            modelList.add(model);
        }
        mapParent.put("A", modelList);

        // 猪只状态
        modelList = new ArrayList<>();
        List<PigClassModel> searchPigClassToList = backEndService.searchPigClassToList(PigConstants.PIG_TYPE_PORK);
        if (searchPigClassToList.size() == 0) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "猪只状态");
        }
        for (PigClassModel mat : searchPigClassToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.getRowId()));
            model.put("name", mat.getPigClassName());
            modelList.add(model);
        }
        // 需要补充的查询
        mapParent.put("G", modelList);

        // 供应商
        modelList = new ArrayList<>();
        List<Map<String, String>> searchCustomerAndSupplierToList = basicInfoService.searchCustomerAndSupplierToList(
                CompanyConstants.CUS_SUP_TYPE_SUP);
        if (searchCustomerAndSupplierToList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOTFOUND_HIDDEN_DATA, "供应商");
        }
        for (Map<String, String> mat : searchCustomerAndSupplierToList) {
            Map<String, String> model = new HashMap<>();
            model.put("id", String.valueOf(mat.get("rowId")));
            model.put("name", mat.get("companyName"));
            modelList.add(model);
        }
        // 需要补充的查询
        mapParent.put("N", modelList);
        return mapParent;
    }

    public static void main(String[] args) {
        StringBuffer houseId = new StringBuffer();

        boolean addComma = false;

        for (int i = 0; i < 10; i++) {
            houseId = addComma == true ? houseId.append("," + i) : houseId.append(i);
            addComma = true;
        }
        System.out.println(houseId);
    }
}
