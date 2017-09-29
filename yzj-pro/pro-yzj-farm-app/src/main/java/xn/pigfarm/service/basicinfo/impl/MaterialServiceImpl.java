package xn.pigfarm.service.basicinfo.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaMaterial;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.BreedMapper;
import xn.pigfarm.mapper.basicinfo.BoarMapper;
import xn.pigfarm.mapper.basicinfo.ConsumableMapper;
import xn.pigfarm.mapper.basicinfo.DeviceMapper;
import xn.pigfarm.mapper.basicinfo.DisinfectorMapper;
import xn.pigfarm.mapper.basicinfo.DrugMapper;
import xn.pigfarm.mapper.basicinfo.FeedMapper;
import xn.pigfarm.mapper.basicinfo.HardwareMapper;
import xn.pigfarm.mapper.basicinfo.MaterialGroupMapper;
import xn.pigfarm.mapper.basicinfo.MaterialMapper;
import xn.pigfarm.mapper.basicinfo.PorkPigMapper;
import xn.pigfarm.mapper.basicinfo.PpeMapper;
import xn.pigfarm.mapper.basicinfo.RawMaterialMapper;
import xn.pigfarm.mapper.basicinfo.SowMapper;
import xn.pigfarm.mapper.basicinfo.SpermMapper;
import xn.pigfarm.mapper.basicinfo.VaccineMapper;
import xn.pigfarm.model.backend.BreedModel;
import xn.pigfarm.model.basicinfo.BoarModel;
import xn.pigfarm.model.basicinfo.ConsumableModel;
import xn.pigfarm.model.basicinfo.DeviceModel;
import xn.pigfarm.model.basicinfo.DisinfectorModel;
import xn.pigfarm.model.basicinfo.DrugModel;
import xn.pigfarm.model.basicinfo.FeedModel;
import xn.pigfarm.model.basicinfo.HardwareModel;
import xn.pigfarm.model.basicinfo.MaterialGroupModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.basicinfo.PpeModel;
import xn.pigfarm.model.basicinfo.RawMaterialModel;
import xn.pigfarm.model.basicinfo.SowModel;
import xn.pigfarm.model.basicinfo.SpermModel;
import xn.pigfarm.model.basicinfo.VaccineModel;
import xn.pigfarm.service.basicinfo.IMaterialService;
import xn.pigfarm.util.constants.MaterialConstants;
import xn.pigfarm.util.enums.MaterialInfoEnum;

/**
 * @Description: 物料主数据
 * @author fangc
 * @date 2016年5月3日 下午2:58:51
 */
@Service("MaterialService")
public class MaterialServiceImpl extends BaseServiceImpl implements IMaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialGroupMapper materialGroupMapper;

    @Autowired
    private BoarMapper boarMapper;

    @Autowired
    private PorkPigMapper porkPigMapper;

    @Autowired
    private SowMapper sowMapper;

    @Autowired
    private VaccineMapper vaccineMapper;

    @Autowired
    private SpermMapper spermMapper;

    @Autowired
    private RawMaterialMapper rawMaterialMapper;

    @Autowired
    private PpeMapper ppeMapper;

    @Autowired
    private HardwareMapper hardwareMapper;

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private DisinfectorMapper disinfectorMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private ConsumableMapper consumableMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private BreedMapper breedMapper;

    @Override
    public List<MaterialModel> searchMaterialToList(String materialType) throws Exception {

        if (StringUtil.isBlank(materialType)) {
            return null;
        }
        // 猪只入场的pigType，进行转化查询
        // 允许传多个materialType
        SqlCon sql = new SqlCon();
        StringBuffer materialTypes = new StringBuffer();
        String material = null;
        String[] materailArray = materialType.split(",");
        if (materialType.indexOf(",") > 0) {
            for (int i = 0; i < materailArray.length; i++) {
                material = MaterialConstants.MATERIAL_MAP.get(materailArray[i]);
                if (i == 0) {
                    materialTypes.append(material);
                } else {
                    materialTypes.append(",");
                    materialTypes.append(material);
                }
            }
            sql.addCondition(materialTypes.toString(), " AND MATERIAL_TYPE IN ? ", false, true);
        } else {
            material = MaterialConstants.MATERIAL_MAP.get(materialType);
            sql.addConditionWithNull(material, " AND MATERIAL_TYPE = ?");
        }
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        return getList(materialMapper, sql);
    }

    @Override
    public List<MaterialModel> searchBoarMaterialToList(String materialType, String rowId) throws Exception {

        // 精液主数据绑定公猪主数据已绑定的主数据不能重复绑定
        materialType = MaterialConstants.MATERIAL_MAP.get(materialType);
        SqlCon materialSql = new SqlCon();
        materialSql.addMainSql(" SELECT GROUP_CONCAT(BOAR_ID) as notes FROM cd_o_sperm WHERE STATUS = 1 AND DELETED_FLAG = 0 ");
        // materialSql.addConditionWithNull(rowId, " AND MATERIAL_ID != ?");
        materialSql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        List<MaterialModel> materialIds = setSql(materialMapper, materialSql);
        String boarIds = null;
        if (!materialIds.isEmpty()) {
            boarIds = materialIds.get(0).getNotes();
        }

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(materialType, " AND MATERIAL_TYPE = ?");
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        sql.addCondition(boarIds, " AND ROW_ID NOT IN ?", false, true);
        return getList(materialMapper, sql);
    }

    @Override
    public BasePageInfo searchMaterialToPage(String materialType) throws Exception {
        setToPage();
        List<MaterialModel> list = null;
        if (StringUtil.isBlank(materialType)) {
            list = materialMapper.searchToList(getFarmId());
        } else {
            SqlCon sql = new SqlCon();
            sql.addConditionWithNull(materialType, " AND MATERIAL_TYPE = ?");
            sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
            list = getList(materialMapper, sql);
        }
        list = CacheUtil.setName(list, NameEnum.CODE_NAME, CodeEnum.MATERIAL_TYPE_NAME);
        for (MaterialModel me : list) {
            if ("Sperm".equals(materialType)) {
                SqlCon spermSqlCon = new SqlCon();
                spermSqlCon.addCondition(me.getRowId(), " AND MATERIAL_ID = ?");
                List<SpermModel> spermModelList = getList(spermMapper, spermSqlCon);
                SpermModel spermModel = spermModelList.get(0);

                SqlCon boarSqlCon = new SqlCon();
                boarSqlCon.addCondition(spermModel.getBoarId(), " AND MATERIAL_ID = ?");
                List<BoarModel> boarModelList = getList(boarMapper, boarSqlCon);
                BoarModel boarModel = boarModelList.get(0);

                BreedModel breedModel = breedMapper.searchById(boarModel.getBreedId());
                me.set("breedId", breedModel.getRowId());
                me.set("breedName", breedModel.getBreedName());
            }
            if ("N".equals(me.getIsPurchase())) {
                me.set("isPurchaseName", "否");
            } else {
                me.set("isPurchaseName", "是");
            }
            if ("N".equals(me.getIsSell())) {
                me.set("isSellName", "否");
            } else {
                me.set("isSellName", "是");
            }
            if ("N".equals(me.getIsWarehouse())) {
                me.set("isWarehouseName", "否");
            } else {
                me.set("isWarehouseName", "是");
            }
        }

        return getPageInfo(list);
    }

    @Override
    public synchronized void editMaterial(Map<String, Object> map, String materialType) throws Exception {

        MaterialModel materialModel = getBean(MaterialModel.class, map);

        // 统一单位大写
        if (materialModel.getUnit() != null) {
            materialModel.setUnit(materialModel.getUnit().toUpperCase());
        }

        // 统一单位大写
        if (materialModel.getSpec() != null) {
            materialModel.setSpec(materialModel.getSpec().toUpperCase());
        }

        // 去除空格
        if (materialModel.getManufacturer() != null) {
            materialModel.setManufacturer(materialModel.getManufacturer().trim());
        }

        if (materialModel.getSpecAll() != null) {
            materialModel.setSpecAll(materialModel.getSpecAll().trim());
        }

        if ("Boar".equals(materialModel.getMaterialType()) || "Sow".equals(materialModel.getMaterialType()) || "PorkPig".equals(materialModel
                .getMaterialType()) || "Sperm".equals(materialModel.getMaterialType())) {
            // 验证名称重复
            long breedCodeIsExist = ParamUtil.isExist("cd_m_material", materialModel.getRowId(), materialModel.getMaterialName() + "," + getFarmId(),
                    "MATERIAL_NAME,FARM_ID");
            if (breedCodeIsExist > 0) {
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, materialModel.getMaterialName());
            }
        } else {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addCondition(materialModel.getMaterialName(), " AND MATERIAL_NAME = ?");
            sqlCon.addCondition(materialModel.getSpecAll(), " AND SPEC_ALL = ?");
            sqlCon.addCondition(materialModel.getSupplierId(), " AND SUPPLIER_ID = ?");
            sqlCon.addCondition(materialModel.getManufacturer(), " AND MANUFACTURER = ?");
            sqlCon.addCondition(materialModel.getRowId(), " AND ROW_ID <> ?");
            List<MaterialModel> existsList = getList(materialMapper, sqlCon);
            if (existsList != null && !existsList.isEmpty()) {
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, materialModel.getMaterialName());
            }
        }

        Long materialId = 0l;
        if (materialModel.getRowId() == 0) {

            // 自动生成编码
            String busCode = ParamUtil.getBCode("MATERIAL_" + materialType.toUpperCase(), getEmployeeId(), getCompanyId(), getFarmId());
            materialModel.setBusinessCode(busCode);
            materialModel.setCompanyId(getCompanyId());
            materialModel.setFarmId(getFarmId());

            // START
            if (!("Boar".equals(materialModel.getMaterialType()) || "Sow".equals(materialModel.getMaterialType()) || "PorkPig".equals(materialModel
                    .getMaterialType()) || "Sperm".equals(materialModel.getMaterialType()))) {

                // 新农旗下公司无法自己创建物料
                if (getCompanyMark().startsWith("1,2,3,")) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新农旗下子公司，无法创建非猪只物料数据，请联系新农公司管理员创建。。。");
                }

                SqlCon sqlCon2 = new SqlCon();
                sqlCon2.addMainSql("SELECT MAX(MATERIAL_CLASS_NUMBER) AS materialClassNumber");
                sqlCon2.addMainSql(" FROM CD_M_MATERIAL");
                sqlCon2.addMainSql(" WHERE ");
                sqlCon2.addConditionWithNull(getFarmId(), " FARM_ID = ?");
                sqlCon2.addCondition(materialModel.getMaterialFirstClass(), " AND MATERIAL_FIRST_CLASS = ?");
                sqlCon2.addCondition(materialModel.getMaterialSecondClass(), " AND MATERIAL_SECOND_CLASS = ?");

                List<MaterialModel> list = setSql(materialMapper, sqlCon2);

                // 物料SAP编码
                String materialClassNumber = null;
                // 没有找到数据
                if (list.isEmpty()) {
                    materialClassNumber = materialModel.getMaterialFirstClass() + materialModel.getMaterialSecondClass() + "0001";
                } else {
                    // 已存在分类
                    materialClassNumber = String.valueOf(Long.valueOf(list.get(0).getMaterialClassNumber()) + 1L);
                }
                materialModel.setMaterialClassNumber(materialClassNumber);

                // 物料科目
                materialModel.setFinanceSubject(CacheUtil.getFinanceSubjectByMaterialClass(materialModel.getMaterialFirstClass(), materialModel
                        .getMaterialSecondClass()));
            }
            // END

            materialMapper.insert(materialModel);

            // BEGIN 更新RelatedId为它自己的ROW_ID,供应链需求
            materialModel.setRelatedId(materialModel.getRowId());
            materialMapper.update(materialModel);
            // END

            map.put("farmId", getFarmId());
            map.put("companyId", getCompanyId());
            materialId = materialModel.getRowId();

            if (MaterialConstants.MATERIAL_BOAR.equalsIgnoreCase(materialType)) {
                BoarModel boarModel = getBean(BoarModel.class, map);
                boarModel.setMaterialId(materialId);
                if (boarModel.getProductionDayAge() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【转生产日龄【不能为空！");
                }
                if (boarModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种【不能为空！");
                }
                boarMapper.insert(boarModel);
            } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
                VaccineModel vaccineModel = getBean(VaccineModel.class, map);
                vaccineModel.setMaterialId(materialId);
                vaccineMapper.insert(vaccineModel);
            } else if (MaterialConstants.MATERIAL_SPERM.equalsIgnoreCase(materialType)) {
                SpermModel spermModel = getBean(SpermModel.class, map);
                spermModel.setMaterialId(materialId);
                spermMapper.insert(spermModel);
            } else if (MaterialConstants.MATERIAL_SOW.equalsIgnoreCase(materialType)) {
                SowModel sowModel = getBean(SowModel.class, map);
                if (sowModel.getChangeLabor() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【配种后转入产房区间】不能为空！");
                }
                if (sowModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (sowModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (sowModel.getPregnancyCheckDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊检天数】不能为空！");
                }
                if (sowModel.getPregnancyDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠天数】不能为空！");
                }
                if (sowModel.getErrorLimit() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠误差】不能为空！");
                }
                sowModel.setMaterialId(materialId);
                sowMapper.insert(sowModel);
            } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
                RawMaterialModel rawMaterialModel = getBean(RawMaterialModel.class, map);
                rawMaterialModel.setMaterialId(materialId);
                rawMaterialMapper.insert(rawMaterialModel);
            } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
                PpeModel ppeModel = getBean(PpeModel.class, map);
                ppeModel.setMaterialId(materialId);
                ppeMapper.insert(ppeModel);
            } else if (MaterialConstants.MATERIAL_PORK_PIG.equalsIgnoreCase(materialType)) {
                PorkPigModel porkPigModel = getBean(PorkPigModel.class, map);
                porkPigModel.setMaterialId(materialId);
                if (porkPigModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (porkPigModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (porkPigModel.getBoarId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【父本】不能为空！");
                }
                if (porkPigModel.getSowId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【母本】不能为空！");
                }
                // 肉猪主数据是否存在父本，母本重复
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" SELECT  CONCAT(IFNULL(T1.MATERIAL_NAME,''),'【',IFNULL(T1.BUSINESS_CODE,''),'】' ) NOTES FROM cd_o_pork_pig  T0    "
                        + "INNER JOIN cd_m_material T1    ON T0.MATERIAL_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
                sqlCon.addMainSql("  WHERE T0.BOAR_ID = " + porkPigModel.getBoarId() + " AND T0.SOW_ID = " + porkPigModel.getSowId()
                        + " AND T0.DELETED_FLAG = '0' AND T0.ROW_ID <>" + materialId);
                sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
                List<PorkPigModel> list1 = setSql(porkPigMapper, sqlCon);
                if (list1.size() > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, list1.get(0).getNotes() + "肉猪主数据已存在父本与母本配对，不能重复！");
                }

                porkPigMapper.insert(porkPigModel);
            } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
                HardwareModel hardwareModel = getBean(HardwareModel.class, map);
                hardwareModel.setMaterialId(materialId);
                hardwareMapper.insert(hardwareModel);
            } else if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
                FeedModel feedModel = getBean(FeedModel.class, map);
                feedModel.setMaterialId(materialId);
                feedMapper.insert(feedModel);
            } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
                DrugModel drugModel = getBean(DrugModel.class, map);
                drugModel.setMaterialId(materialId);
                drugMapper.insert(drugModel);
            } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
                DisinfectorModel disinfectorModel = getBean(DisinfectorModel.class, map);
                disinfectorModel.setMaterialId(materialId);
                disinfectorMapper.insert(disinfectorModel);
            } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
                DeviceModel deviceModel = getBean(DeviceModel.class, map);
                deviceModel.setMaterialId(materialId);
                deviceMapper.insert(deviceModel);
            } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
                ConsumableModel consumableModel = getBean(ConsumableModel.class, map);
                consumableModel.setMaterialId(materialId);
                consumableMapper.insert(consumableModel);
            }

            // START HANA
            // 不是猪只和精液
            if (!("Boar".equals(materialModel.getMaterialType()) || "Sow".equals(materialModel.getMaterialType()) || "PorkPig".equals(materialModel
                    .getMaterialType()) || "Sperm".equals(materialModel.getMaterialType()))) {

                // 目前：新农公司才同步数据到SAP
                if (("1,2,3").equals(getCompanyMark())) {
                    Date currentDate = new Date();

                    String mtcCardCode = HanaUtil.getMTC_CardCode(materialModel.getSupplierId());

                    HanaMaterial hanaMaterial = new HanaMaterial();
                    // 物料编号
                    hanaMaterial.setMTC_ItemCode(materialModel.getMaterialClassNumber());
                    // 物料描述
                    hanaMaterial.setMTC_ItemName(materialModel.getMaterialName());
                    // 物料规格
                    hanaMaterial.setMTC_Spec(materialModel.getSpecAll());
                    // 物料组(科目)
                    hanaMaterial.setMTC_ItmsGrpCod(materialModel.getFinanceSubject());
                    // 计量单位
                    hanaMaterial.setMTC_Unit(materialModel.getUnit());
                    // 供应商
                    hanaMaterial.setMTC_CardCode(mtcCardCode);
                    // 制造商
                    hanaMaterial.setMTC_ValidComm(materialModel.getManufacturer());
                    // 管理物料由
                    // 物料类为：Y - 批次
                    // 费用类：N - 无
                    String mtcManBtchNum = null;
                    if (hanaMaterial.getMTC_ItmsGrpCod() != null) {
                        switch (hanaMaterial.getMTC_ItmsGrpCod()) {
                        // 原材料-饲料
                        case "101":
                            mtcManBtchNum = "Y";
                            break;
                        // 原材料-药物-针剂
                        case "102":
                            mtcManBtchNum = "Y";
                            break;
                        // 原材料-药物-疫苗
                        case "103":
                            mtcManBtchNum = "Y";
                            break;
                        // 原材料-药物-兽用器械
                        case "104":
                            mtcManBtchNum = "Y";
                            break;
                        // 原材料-药物-粉剂
                        case "105":
                            mtcManBtchNum = "Y";
                            break;
                        // 原材料-药物-消毒剂
                        case "106":
                            mtcManBtchNum = "Y";
                            break;
                        default:
                            mtcManBtchNum = "N";
                            break;
                        }
                    } else {
                        mtcManBtchNum = "N";
                    }
                    hanaMaterial.setMTC_ManBtchNum(mtcManBtchNum);

                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    // mtcITFC.setMTC_Branch(mtcBranchID);
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                    // 业务代码: 物料
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1060);
                    // 业务代码: 物料
                    mtcITFC.setMTC_DocNum(materialModel.getMaterialClassNumber());
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
                    // JSON文件
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterial);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                    hanaCommonService.insertMTC_ITFC(mtcITFC);
                }
            }
            // END HANA

        } else {

            if (MaterialConstants.MATERIAL_BOAR.equalsIgnoreCase(materialType)) {
                BoarModel boarModel = getBean(BoarModel.class, map);

                if (boarModel.getProductionDayAge() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【转生产日龄】不能为空！");
                }
                if (boarModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                boarModel.setRowId(Maps.getLongClass(map, "childTableId"));
                materialId = boarModel.getMaterialId();
                boarMapper.update(boarModel);
            } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
                VaccineModel vaccineModel = getBean(VaccineModel.class, map);
                vaccineModel.setRowId(Maps.getLongClass(map, "childTableId"));
                vaccineMapper.update(vaccineModel);
                materialId = vaccineModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_SPERM.equalsIgnoreCase(materialType)) {
                SpermModel spermModel = getBean(SpermModel.class, map);
                spermModel.setRowId(Maps.getLongClass(map, "childTableId"));
                spermMapper.update(spermModel);
                materialId = spermModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_SOW.equalsIgnoreCase(materialType)) {
                SowModel sowModel = getBean(SowModel.class, map);
                if (sowModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (sowModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (sowModel.getPregnancyCheckDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊检天数】不能为空！");
                }
                if (sowModel.getPregnancyDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠天数】不能为空！");
                }
                if (sowModel.getErrorLimit() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠误差】不能为空！");
                }
                sowModel.setRowId(Maps.getLongClass(map, "childTableId"));
                sowMapper.update(sowModel);
                materialId = sowModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
                RawMaterialModel rawMaterialModel = getBean(RawMaterialModel.class, map);
                rawMaterialModel.setRowId(Maps.getLongClass(map, "childTableId"));
                rawMaterialMapper.update(rawMaterialModel);
                materialId = rawMaterialModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
                PpeModel ppeModel = getBean(PpeModel.class, map);
                ppeModel.setRowId(Maps.getLongClass(map, "childTableId"));
                ppeMapper.update(ppeModel);
                materialId = ppeModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_PORK_PIG.equalsIgnoreCase(materialType)) {
                PorkPigModel porkPigModel = getBean(PorkPigModel.class, map);
                if (porkPigModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (porkPigModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (porkPigModel.getBoarId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【父本】不能为空！");
                }
                if (porkPigModel.getSowId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【母本】不能为空！");
                }
                // 肉猪主数据是否存在父本，母本重复
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" SELECT  CONCAT(IFNULL(T1.MATERIAL_NAME,''),'【',IFNULL(T1.BUSINESS_CODE,''),'】' ) NOTES FROM cd_o_pork_pig  T0    "
                        + "INNER JOIN cd_m_material T1    ON T0.MATERIAL_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
                sqlCon.addMainSql("  WHERE T0.BOAR_ID = " + porkPigModel.getBoarId() + " AND T0.SOW_ID = " + porkPigModel.getSowId()
                        + " AND T0.DELETED_FLAG = '0' AND T1.ROW_ID <> " + materialModel.getRowId());
                sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
                List<PorkPigModel> list1 = setSql(porkPigMapper, sqlCon);
                if (list1.size() > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, list1.get(0).getNotes() + "肉猪主数据已存在父本与母本配对，不能重复！");
                }
                if ("Y".equals(porkPigModel.getIsSelect())) {
                    if (porkPigModel.getStockBoarId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【种公猪主数据】不能为空！");
                    }
                    if (porkPigModel.getBroodSowId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【种母猪主数据】不能为空！");
                    }
                }
                porkPigModel.setRowId(Maps.getLongClass(map, "childTableId"));
                porkPigMapper.update(porkPigModel);
                materialId = porkPigModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
                HardwareModel hardwareModel = getBean(HardwareModel.class, map);
                hardwareModel.setRowId(Maps.getLongClass(map, "childTableId"));
                hardwareMapper.update(hardwareModel);
                materialId = hardwareModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
                FeedModel feedModel = getBean(FeedModel.class, map);
                feedModel.setRowId(Maps.getLongClass(map, "childTableId"));
                feedMapper.update(feedModel);
                materialId = feedModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
                DrugModel drugModel = getBean(DrugModel.class, map);
                drugModel.setRowId(Maps.getLongClass(map, "childTableId"));
                drugMapper.update(drugModel);
                materialId = drugModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
                DisinfectorModel disinfectorModel = getBean(DisinfectorModel.class, map);
                disinfectorModel.setRowId(Maps.getLongClass(map, "childTableId"));
                disinfectorMapper.update(disinfectorModel);
                materialId = disinfectorModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
                DeviceModel deviceModel = getBean(DeviceModel.class, map);
                deviceModel.setRowId(Maps.getLongClass(map, "childTableId"));
                deviceMapper.update(deviceModel);
                materialId = deviceModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
                ConsumableModel consumableModel = getBean(ConsumableModel.class, map);
                consumableModel.setRowId(Maps.getLongClass(map, "childTableId"));
                consumableMapper.update(consumableModel);
                materialId = consumableModel.getMaterialId();
            }
            materialModel.setRowId(materialId);
            materialMapper.update(materialModel);

            // START HANA
            // 不是猪只和精液
            if (!("Boar".equals(materialModel.getMaterialType()) || "Sow".equals(materialModel.getMaterialType()) || "PorkPig".equals(materialModel
                    .getMaterialType()) || "Sperm".equals(materialModel.getMaterialType()))) {
                boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());

                // 新农公司
                if (("1,2,3").equals(getCompanyMark())) {
                    if (isToHana) {
                        Date currentDate = new Date();

                        String mtcCardCode = HanaUtil.getMTC_CardCode(materialModel.getSupplierId());

                        Map<String, String> materialInfoMap = CacheUtil.getMaterialInfo(String.valueOf(materialModel.getRelatedId()),
                                MaterialInfoEnum.MATERIAL_INFO);

                        HanaMaterial hanaMaterial = new HanaMaterial();
                        // 物料编号
                        hanaMaterial.setMTC_ItemCode(Maps.getString(materialInfoMap, "materialClassNumber"));
                        // 物料描述
                        hanaMaterial.setMTC_ItemName(materialModel.getMaterialName());
                        // 物料规格
                        hanaMaterial.setMTC_Spec(materialModel.getSpecAll());
                        // 物料组
                        hanaMaterial.setMTC_ItmsGrpCod(Maps.getString(materialInfoMap, "financeSubject"));
                        // 计量单位
                        hanaMaterial.setMTC_Unit(materialModel.getUnit());
                        // 供应商
                        hanaMaterial.setMTC_CardCode(mtcCardCode);
                        // 制造商
                        hanaMaterial.setMTC_ValidComm(materialModel.getManufacturer());
                        // 管理物料由
                        // 物料类为：Y - 批次
                        // 费用类：N - 无
                        String mtcManBtchNum = null;
                        if (hanaMaterial.getMTC_ItmsGrpCod() != null) {
                            switch (hanaMaterial.getMTC_ItmsGrpCod()) {
                            // 原材料-饲料
                            case "101":
                                mtcManBtchNum = "Y";
                                break;
                            // 原材料-药物-针剂
                            case "102":
                                mtcManBtchNum = "Y";
                                break;
                            // 原材料-药物-疫苗
                            case "103":
                                mtcManBtchNum = "Y";
                                break;
                            // 原材料-药物-兽用器械
                            case "104":
                                mtcManBtchNum = "Y";
                                break;
                            // 原材料-药物-粉剂
                            case "105":
                                mtcManBtchNum = "Y";
                                break;
                            // 原材料-药物-消毒剂
                            case "106":
                                mtcManBtchNum = "Y";
                                break;
                            default:
                                mtcManBtchNum = "N";
                                break;
                            }
                        } else {
                            mtcManBtchNum = "N";
                        }
                        hanaMaterial.setMTC_ManBtchNum(mtcManBtchNum);

                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        // mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                        // 业务代码: 物料
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1060);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaMaterial.getMTC_ItemCode());
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_U);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterial);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                        hanaCommonService.insertMTC_ITFC(mtcITFC);
                    }
                }
            }
            // END HANA
        }
    }

    @Override
    public void deleteMaterials(Map<String, Object> inputMap) throws Exception {
        List<HashMap> materialList = getJsonList(Maps.getString(inputMap, "materialList"), HashMap.class);

        long[] ids = new long[materialList.size()];
        Set<String> materialTypeSet = new HashSet<String>();
        for (int i = 0; i < materialList.size(); i++) {
            Map<String, Object> materialMap = materialList.get(i);
            Long materialId = Maps.getLongClass(materialMap, "materialId");
            ids[i] = materialId;
            String materialType = Maps.getString(materialMap, "materialType");

            MaterialModel materialModel = materialMapper.searchById(materialId);
            if (materialModel != null) {
                String mainTable = null;
                if (!MaterialConstants.MATERIAL_SOW.equals(materialModel.getMaterialType()) && !MaterialConstants.MATERIAL_BOAR.equals(materialModel
                        .getMaterialType()) && !MaterialConstants.MATERIAL_PORK_PIG.equals(materialModel.getMaterialType())
                        && !MaterialConstants.MATERIAL_SPERM.equals(materialModel.getMaterialType())) {
                    // 集团创建的物料主数据，子公司无法删除
                    if (materialModel.getRowId().compareTo(materialModel.getRelatedId()) != 0) {
                        Thrower.throwException(BaseBusiException.CHILD_CAN_NOT_DEL_GROUP_CREATE, materialModel.getMaterialName());
                    }

                    mainTable = "SC_M_STORE_MATERIAL";

                } else if (MaterialConstants.MATERIAL_SPERM.equals(materialModel.getMaterialType())) {
                    mainTable = "PP_L_SPERM_INFO";

                } else if (MaterialConstants.MATERIAL_SOW.equals(materialModel.getMaterialType()) || MaterialConstants.MATERIAL_BOAR.equals(
                        materialModel.getMaterialType())) {

                    // 查看肉猪的公本，母本是否是这个主数据
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql("SELECT T2.MATERIAL_NAME AS materialName");
                    sqlCon.addMainSql(" FROM CD_O_PORK_PIG T1");
                    sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T2");
                    sqlCon.addMainSql(" ON(T1.MATERIAL_ID = T2.ROW_ID");
                    sqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
                    sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
                    sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
                    sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
                    if (MaterialConstants.MATERIAL_SOW.equals(materialModel.getMaterialType())) {
                        sqlCon.addCondition(materialModel.getRowId(), " AND T1.SOW_ID = ?");
                    } else {
                        sqlCon.addCondition(materialModel.getRowId(), " AND T1.BOAR_ID = ?");
                    }
                    sqlCon.addMainSql(" LIMIT 1");

                    List<MaterialModel> existCheckList = setSql(materialMapper, sqlCon);
                    if (existCheckList != null && !existCheckList.isEmpty()) {
                        Thrower.throwException(BaseBusiException.MATERIAL_IS_BOAR_OR_SOW, materialModel.getMaterialName(), existCheckList.get(0)
                                .getMaterialName(), MaterialConstants.MATERIAL_SOW.equals(materialModel.getMaterialType()) ? "母本" : "父本");
                    }

                    if (MaterialConstants.MATERIAL_BOAR.equals(materialModel.getMaterialType())) {
                        // 查看精液是否关联到这个公猪主数据
                        SqlCon spermSqlCon = new SqlCon();
                        spermSqlCon.addMainSql("SELECT T2.MATERIAL_NAME AS materialName");
                        spermSqlCon.addMainSql(" FROM CD_O_SPERM T1");
                        spermSqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T2");
                        spermSqlCon.addMainSql(" ON(T1.MATERIAL_ID = T2.ROW_ID");
                        spermSqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
                        spermSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
                        spermSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
                        spermSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
                        spermSqlCon.addCondition(materialModel.getRowId(), " AND T1.BOAR_ID = ? LIMIT 1");
                        List<MaterialModel> existSpermCheckList = setSql(materialMapper, spermSqlCon);
                        if (existSpermCheckList != null && !existSpermCheckList.isEmpty()) {
                            Thrower.throwException(BaseBusiException.MATERIAL_IS_BOAR_OR_SOW, materialModel.getMaterialName(), existSpermCheckList
                                    .get(0).getMaterialName(), "精液对应公猪主数据");
                        }
                    }

                    mainTable = "PP_L_PIG";

                } else if (MaterialConstants.MATERIAL_PORK_PIG.equals(materialModel.getMaterialType())) {

                    mainTable = "PP_L_PIG";

                }

                SqlCon commonCheckSqlCon = new SqlCon();
                // 判断这个物料主数据是否已经被使用过
                commonCheckSqlCon.addMainSql("SELECT ROW_ID FROM CD_M_MATERIAL T1 WHERE DELETED_FLAG = '0'");
                if ("SC_M_STORE_MATERIAL".equals(mainTable)) {
                    commonCheckSqlCon.addCondition(materialModel.getRowId(), " AND RELATED_ID = ?");
                    commonCheckSqlCon.addMainSql(" AND (");
                    commonCheckSqlCon.addMainSql("EXISTS (SELECT 1 FROM SC_M_DAILY_RECORD WHERE DELETED_FLAG = '0'");
                    commonCheckSqlCon.addMainSql(" AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");
                    commonCheckSqlCon.addMainSql(" OR EXISTS(SELECT 1 FROM SC_M_BILL_REQUIRE_DETAIL WHERE DELETED_FLAG = '0'");
                    commonCheckSqlCon.addMainSql(" AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");
                    commonCheckSqlCon.addMainSql(" OR EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0'");
                    commonCheckSqlCon.addMainSql(" AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");
                    commonCheckSqlCon.addMainSql(" OR EXISTS(SELECT 1 FROM SC_M_BILL_INPUT_DETAIL WHERE DELETED_FLAG = '0'");
                    commonCheckSqlCon.addMainSql(" AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");
                    commonCheckSqlCon.addMainSql(" OR EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL WHERE DELETED_FLAG = '0'");
                    commonCheckSqlCon.addMainSql(" AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");
                    commonCheckSqlCon.addMainSql(" OR EXISTS(SELECT 1 FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
                    commonCheckSqlCon.addMainSql(" AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");
                    commonCheckSqlCon.addMainSql(" )");
                } else {
                    commonCheckSqlCon.addCondition(materialModel.getRowId(), " AND ROW_ID = ?");
                    commonCheckSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                    commonCheckSqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM " + mainTable
                            + " WHERE DELETED_FLAG = '0' AND FARM_ID = T1.FARM_ID AND MATERIAL_ID = T1.ROW_ID LIMIT 1)");

                }
                commonCheckSqlCon.addMainSql(" LIMIT 1");

                List<MaterialModel> commonExistCheckList = setSql(materialMapper, commonCheckSqlCon);
                if (commonExistCheckList != null && !commonExistCheckList.isEmpty()) {
                    Thrower.throwException(BaseBusiException.MATERIAL_HAS_BEEN_USED, materialModel.getMaterialName());
                }

            } else {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
            }

            materialTypeSet.add(materialType);

        }

        materialMapper.deletes(ids);
        SqlCon getSynchronizedMaterialIdsCon = new SqlCon();
        getSynchronizedMaterialIdsCon.addMainSql("SELECT GROUP_CONCAT(''+ROW_ID) AS materialIds FROM CD_M_MATERIAL WHERE ROW_ID <> RELATED_ID");
        getSynchronizedMaterialIdsCon.addMainSql(" AND RELATED_ID IN (");
        for (int i = 0; i < ids.length; i++) {
            getSynchronizedMaterialIdsCon.addMainSql(String.valueOf(ids[i]));
            if (i != ids.length - 1) {
                getSynchronizedMaterialIdsCon.addMainSql(",");
            }
        }
        getSynchronizedMaterialIdsCon.addMainSql(")");

        Map<String, String> getSynchronizedMaterialIdsMap = new HashMap<String, String>();
        getSynchronizedMaterialIdsMap.put("sql", getSynchronizedMaterialIdsCon.getCondition());

        List<Map<String, Object>> synchronizedMaterialIds = paramMapper.getObjectInfos(getSynchronizedMaterialIdsMap);
        String synchronizedMaterialIdsString = null;

        if (synchronizedMaterialIds != null && !synchronizedMaterialIds.isEmpty() && synchronizedMaterialIds.get(0) != null) {
            synchronizedMaterialIdsString = Maps.getString(synchronizedMaterialIds.get(0), "materialIds");
        }

        if (synchronizedMaterialIdsString != null) {
            setDeletes(materialMapper, synchronizedMaterialIdsString, "ROW_ID");
        }

        for (String materialType : materialTypeSet) {
            if (MaterialConstants.MATERIAL_BOAR.equalsIgnoreCase(materialType)) {
                setDeletes(boarMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(boarMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_SOW.equalsIgnoreCase(materialType)) {
                setDeletes(sowMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(sowMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_PORK_PIG.equalsIgnoreCase(materialType)) {
                setDeletes(porkPigMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(porkPigMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_SPERM.equalsIgnoreCase(materialType)) {
                setDeletes(spermMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(spermMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
                setDeletes(feedMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(feedMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
                setDeletes(rawMaterialMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(rawMaterialMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
                setDeletes(drugMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(drugMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
                setDeletes(vaccineMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(vaccineMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
                setDeletes(disinfectorMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(disinfectorMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
                setDeletes(deviceMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(deviceMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
                setDeletes(consumableMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(consumableMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
                setDeletes(hardwareMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(hardwareMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
                setDeletes(ppeMapper, ids, "MATERIAL_ID");
                if (synchronizedMaterialIdsString != null) {
                    setDeletes(ppeMapper, synchronizedMaterialIdsString, "MATERIAL_ID");
                }

            }
        }

    }

    @Override
    public List<MaterialGroupModel> searchMaterialGroupToList(String materialType) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(materialType, " AND MATERIAL_TYPE = ?");
        return getList(materialGroupMapper, sqlCon);
    }

    @Override
    public BasePageInfo searchMaterialGroupToPage() throws Exception {
        long farmId = getUserDetail().getFarmId();
        setToPage();
        return getPageInfo(materialGroupMapper.searchToList(farmId));
    }

    @Override
    public void editMaterialGroup(MaterialGroupModel cdMaterialGroupModel, String codelist) throws Exception {
        long companyId = getUserDetail().getCompanyId();
        long farmId = getUserDetail().getFarmId();

        // 验证名字重复
        long breedCodeIsExist = ParamUtil.isExist("CD_M_MATERIAL_GROUP", cdMaterialGroupModel.getRowId(), cdMaterialGroupModel.getGroupName() + ","
                + getFarmId(), "GROUP_NAME,FARM_ID");

        if (breedCodeIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, cdMaterialGroupModel.getGroupName());
        }

        if (cdMaterialGroupModel.getRowId() == 0) {

            String busCode = ParamUtil.getBCode("MATERIAL_" + cdMaterialGroupModel.getMaterialType().toUpperCase(), getEmployeeId(), getCompanyId(),
                    getFarmId());
            cdMaterialGroupModel.setBusinessCode(busCode);
            cdMaterialGroupModel.setCompanyId(companyId);
            cdMaterialGroupModel.setFarmId(farmId);
            materialGroupMapper.insert(cdMaterialGroupModel);
        } else {
            materialGroupMapper.update(cdMaterialGroupModel);
        }
    }

    @Override
    public void deleteMaterialGroups(long[] ids) throws Exception {

        materialGroupMapper.deletes(ids);
    }

    @Override
    public List<BoarModel> searchBoarToList(String materialType) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(materialType, " AND MATERIAL_TYPE = ?");
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        return getList(boarMapper, sql);
    }

    @Override
    public BasePageInfo searchBoarToPage() throws Exception {
        setToPage();
        return getPageInfo(boarMapper.searchToList(getFarmId()));
    }

    @Override
    public void editBoar(BoarModel boarModel) throws Exception {

        // 新增或修改cd_boar表
        if (boarModel.getRowId() == 0 && "0".equals(boarModel.getDeletedFlag())) {
            boarModel.setCompanyId(getCompanyId());
            boarModel.setFarmId(getFarmId());
            boarMapper.insert(boarModel);
        } else {
            boarMapper.update(boarModel);
        }

    }

    @Override
    public void deleteBoars(long[] ids) throws Exception {

        boarMapper.deletes(ids);
    }

    @Override
    public List<SowModel> searchSowToList(String materialType) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(materialType, " AND MATERIAL_TYPE = ?");
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        return getList(sowMapper, sql);
    }

    @Override
    public BasePageInfo searchSowToPage() throws Exception {
        setToPage();
        return getPageInfo(sowMapper.searchToList(getFarmId()));
    }

    @Override
    public void editSow(SowModel sowModel) throws Exception {
        long companyId = getUserDetail().getCompanyId();
        long farmId = getUserDetail().getFarmId();

        // 新增或修改cd_sow表
        if (sowModel.getRowId() == 0 && "0".equals(sowModel.getDeletedFlag())) {
            sowModel.setCompanyId(companyId);
            sowModel.setFarmId(farmId);
            sowMapper.insert(sowModel);
        } else {
            sowMapper.update(sowModel);
        }

    }

    @Override
    public void deleteSows(long[] ids) throws Exception {

        sowMapper.deletes(ids);
    }

    @Override
    public List<PorkPigModel> searchPorkPigToList(String materialType) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(materialType, " AND MATERIAL_TYPE = ?");
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        return getList(porkPigMapper, sql);
    }

    @Override
    public BasePageInfo searchPorkPigToPage() throws Exception {
        setToPage();
        return getPageInfo(porkPigMapper.searchToList(getFarmId()));
    }

    @Override
    public void editPorkPig(PorkPigModel porkPigModel) throws Exception {
        long companyId = getUserDetail().getCompanyId();
        long farmId = getUserDetail().getFarmId();

        // 新增或修改cd_pork_pig表
        if (porkPigModel.getRowId() == 0 && "0".equals(porkPigModel.getDeletedFlag())) {
            porkPigModel.setCompanyId(companyId);
            porkPigModel.setFarmId(farmId);
            porkPigMapper.insert(porkPigModel);
        } else {
            porkPigMapper.update(porkPigModel);
        }
    }

    @Override
    public void deletePorkPigs(long[] ids) throws Exception {

        porkPigMapper.deletes(ids);
    }

    @Override
    public Map<String, Object> searchDetailMaterialToList(long materialId, String materialType) throws Exception {

        // String condition = " AND FARM_ID= " + getFarmId() + " AND MATERIAL_ID = " + materialId;
        SqlCon condition = new SqlCon();
        condition.addConditionWithNull(materialId, " AND MATERIAL_ID = ?");
        condition.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        Map<String, Object> result = new HashMap<String, Object>();

        // 主数据和明细应该为1对1的关系，只取一条
        if (MaterialConstants.MATERIAL_BOAR.equalsIgnoreCase(materialType)) {
            List<BoarModel> boarList = getList(boarMapper, condition);
            result = (boarList != null && !boarList.isEmpty()) ? boarList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
            List<VaccineModel> vaccineList = getList(vaccineMapper, condition);
            result = (vaccineList != null && !vaccineList.isEmpty()) ? vaccineList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_SPERM.equalsIgnoreCase(materialType)) {
            List<SpermModel> spermList = getList(spermMapper, condition);
            result = (spermList != null && !spermList.isEmpty()) ? spermList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_SOW.equalsIgnoreCase(materialType)) {
            List<SowModel> sowList = getList(sowMapper, condition);
            result = (sowList != null && !sowList.isEmpty()) ? sowList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
            List<RawMaterialModel> rawMaterialList = getList(rawMaterialMapper, condition);
            result = (rawMaterialList != null && !rawMaterialList.isEmpty()) ? rawMaterialList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
            List<PpeModel> ppeList = getList(ppeMapper, condition);
            result = (ppeList != null && !ppeList.isEmpty()) ? ppeList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_PORK_PIG.equalsIgnoreCase(materialType)) {
            List<PorkPigModel> porkPigList = getList(porkPigMapper, condition);
            result = (porkPigList != null && !porkPigList.isEmpty()) ? porkPigList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
            List<HardwareModel> hardwareList = getList(hardwareMapper, condition);
            result = (hardwareList != null && !hardwareList.isEmpty()) ? hardwareList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
            List<FeedModel> feedList = getList(feedMapper, condition);
            result = (feedList != null && !feedList.isEmpty()) ? feedList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
            List<DrugModel> drugList = getList(drugMapper, condition);
            result = (drugList != null && !drugList.isEmpty()) ? drugList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
            List<DisinfectorModel> disinfectorList = getList(disinfectorMapper, condition);
            result = (disinfectorList != null && !disinfectorList.isEmpty()) ? disinfectorList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
            List<DeviceModel> deviceList = getList(deviceMapper, condition);
            result = (deviceList != null && !deviceList.isEmpty()) ? deviceList.get(0).getData() : new HashMap<String, Object>();
        } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
            List<ConsumableModel> consumableList = getList(consumableMapper, condition);
            result = (consumableList != null && !consumableList.isEmpty()) ? consumableList.get(0).getData() : new HashMap<String, Object>();
        }
        // 插入字表的rowId为childTableId
        result.put("childTableId", Maps.getLongClass(result, "rowId"));

        return result;
    }

    @Override
    public BasePageInfo searchCdMaterialToPageForAdvancedSearch(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID =  ?");
        sqlCon.addCondition(inputMap.get("businessCode"), " AND BUSINESS_CODE LIKE  ?", true);
        sqlCon.addCondition(inputMap.get("materialName"), " AND MATERIAL_NAME LIKE  ?", true);
        sqlCon.addCondition(inputMap.get("materialType"), " AND MATERIAL_TYPE =  ?");

        List<MaterialModel> list = getList(materialMapper, sqlCon);
        list = CacheUtil.setName(list, NameEnum.CODE_NAME, CodeEnum.MATERIAL_TYPE_NAME);
        return getPageInfo(list);
    }

}
