package xn.hana.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xn.core.data.SqlCon;
import xn.core.datasource.DynamicDataSource;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.ParamUtil;
import xn.core.util.SpringContextUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.supplychian.SupplyChianException;
import xn.pigfarm.mapper.hana.SysHanaDbFarmMapper;
import xn.pigfarm.model.hana.SysHanaDbFarmModel;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.PigConstants;
import xn.pigfarm.util.constants.SupplyChianConstants;
import xn.pigfarm.util.enums.MaterialInfoEnum;

public class HanaUtil {
    private HanaUtil() {

    }

    // 是否需要传入Hana中间表
    public static final String IS_TO_HANA = "isToHana";

    // COMPANY_MARK
    public static final String COMPANY_MARK = "COMPANY_MARK";

    // 企业猪场分类:ACCOUNT_COMPANY_CLASS
    public static final String ACCOUNT_COMPANY_CLASS = "ACCOUNT_COMPANY_CLASS";

    // 新农 CompanyMark
    public static final String COMPANY_MARK_XNFEED = "1,2,3";

    // 新农子公司 CompanyMark
    public static final String COMPANY_MARK_IN_XNFEED = "1,2,3,";

    // 分公司
    public static final String MTC_BranchID = "MTC_BranchID";

    // 分公司RowId
    public static final String MTC_BranchIDRowId = "MTC_BranchIDRowId";

    // 分公司Name
    public static final String MTC_BranchIDName = "MTC_BranchIDName";

    // 分公司SortName
    public static final String MTC_BranchIDSortName = "MTC_BranchIDSortName";

    // 猪场
    public static final String MTC_DeptID = "MTC_DeptID";

    // 猪场RowId
    public static final String MTC_DeptIDRowId = "MTC_DeptIDRowId";

    // 猪场Name
    public static final String MTC_DeptIDName = "MTC_DeptIDName";

    // 猪场SortName
    public static final String MTC_DeptIDSortName = "MTC_DeptIDSortName";

    // 公司ROW_ID
    public static final String ROW_ID = "ROW_ID";

    // 公司编号
    public static final String COMPANY_CODE = "COMPANY_CODE";

    // 公司名称
    public static final String COMPANY_NAME = "COMPANY_NAME";

    // 公司简称
    public static final String SORT_NAME = "SORT_NAME";

    // 判断时候要传入Hana中间表,SAP开关值是否打开
    public static boolean isToHanaCheck(long farmId) throws Exception {
        Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(farmId));
        String sapTOHANASetting = ParamUtil.getSettingValueByCode("SAP_TOHANA", Maps.getLong(companyInfoMap, "SUP_COMPANY_ID"), farmId);
        if ("ON".equals(sapTOHANASetting)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取分公司MTC_BranchID和猪场MTC_DeptID的COMPANY_CODE
    public static Map<String, String> getMTC_BranchIDAndMTC_DeptID(long farmId) throws Exception {
        Map<String, String> companyInfoMap01 = CacheUtil.getData("HR_M_COMPANY", String.valueOf(farmId));
        Map<String, String> map = new HashMap<String, String>();
        // 如果本公司就是企业
        if ("2".equals(Maps.getString(companyInfoMap01, ACCOUNT_COMPANY_CLASS)) || "4".equals(Maps.getString(companyInfoMap01,
                ACCOUNT_COMPANY_CLASS))) {
            map.put(MTC_BranchID, Maps.getString(companyInfoMap01, COMPANY_CODE));
            map.put(MTC_BranchIDRowId, Maps.getString(companyInfoMap01, ROW_ID));
            map.put(MTC_BranchIDName, Maps.getString(companyInfoMap01, COMPANY_NAME));
            map.put(MTC_BranchIDSortName, Maps.getString(companyInfoMap01, SORT_NAME));
            map.put(MTC_DeptID, Maps.getString(companyInfoMap01, COMPANY_CODE));
            map.put(MTC_DeptIDRowId, Maps.getString(companyInfoMap01, ROW_ID));
            map.put(MTC_DeptIDName, Maps.getString(companyInfoMap01, COMPANY_NAME));
            map.put(MTC_DeptIDSortName, Maps.getString(companyInfoMap01, SORT_NAME));
        } else {
            map.put(MTC_DeptID, Maps.getString(companyInfoMap01, COMPANY_CODE));
            map.put(MTC_DeptIDRowId, Maps.getString(companyInfoMap01, ROW_ID));
            map.put(MTC_DeptIDName, Maps.getString(companyInfoMap01, COMPANY_NAME));
            map.put(MTC_DeptIDSortName, Maps.getString(companyInfoMap01, SORT_NAME));

            Map<String, String> companyInfoMap02 = CacheUtil.getData("HR_M_COMPANY", Maps.getString(companyInfoMap01, "SUP_COMPANY_ID"));
            // 如果上级公司就是企业
            if ("2".equals(Maps.getString(companyInfoMap02, ACCOUNT_COMPANY_CLASS)) || "4".equals(Maps.getString(companyInfoMap02,
                    ACCOUNT_COMPANY_CLASS))) {
                map.put(MTC_BranchID, Maps.getString(companyInfoMap02, COMPANY_CODE));
                map.put(MTC_BranchIDRowId, Maps.getString(companyInfoMap02, ROW_ID));
                map.put(MTC_BranchIDName, Maps.getString(companyInfoMap02, COMPANY_NAME));
                map.put(MTC_BranchIDSortName, Maps.getString(companyInfoMap02, SORT_NAME));
            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【HanaUtil.getMTC_BranchIDAndMTC_DeptID】获取分公司失败。。。");
            }
        }

        return map;
    }

    // 获取业务伙伴MTC_CardCode
    public static String getMTC_CardCode(Long supplierId) throws Exception {
        if (supplierId == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【HanaUtil.getMTC_CardCode】获取业务伙伴失败。。。");
        }

        Map<String, String> supplierInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(supplierId));

        String mtcCardCode = Maps.getString(supplierInfoMap, COMPANY_CODE);
        // 如果COMPANY_MARK不为空，将开头的C换成S
        if (StringUtil.isNonBlank(Maps.getString(supplierInfoMap, "COMPANY_MARK"))) {
            mtcCardCode = mtcCardCode.replaceFirst("C", "S");
        }

        return mtcCardCode;
    }

    // 获取销售的业务伙伴
    public static String getMTC_SaleCardCode(long supplierId) throws Exception {
        Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(supplierId));
        // 常规销售
        if (Maps.getString(companyInfoMap, "CUSSUP_TYPE") != null) {
            return Maps.getString(companyInfoMap, COMPANY_CODE);
        }

        // 如果本公司就是企业
        if ("2".equals(Maps.getString(companyInfoMap, ACCOUNT_COMPANY_CLASS)) || "4".equals(Maps.getString(companyInfoMap, ACCOUNT_COMPANY_CLASS))) {
            return Maps.getString(companyInfoMap, COMPANY_CODE);
        } else {
            Map<String, String> companyInfoMap02 = CacheUtil.getData("HR_M_COMPANY", Maps.getString(companyInfoMap, "SUP_COMPANY_ID"));
            // 如果上级公司就是企业
            if ("2".equals(Maps.getString(companyInfoMap02, ACCOUNT_COMPANY_CLASS)) || "4".equals(Maps.getString(companyInfoMap02,
                    ACCOUNT_COMPANY_CLASS))) {
                return Maps.getString(companyInfoMap02, COMPANY_CODE);
            }
        }
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【HanaUtil.getMTC_BranchIDAndMTC_DeptID】获取业务伙伴失败。。。");
        return null;
    }

    // 获取物料主数据的MATERIAL_CLASS_NUMBER
    public static String getMTC_ItemCodeOfMaterial(long materialId) throws Exception {
        Map<String, String> materialInfoMap01 = CacheUtil.getMaterialInfo(String.valueOf(materialId), MaterialInfoEnum.MATERIAL_INFO);
        // 如果它是原始的物料
        if (Maps.getString(materialInfoMap01, "materialId").equals(Maps.getString(materialInfoMap01, "relatedId"))) {
            // 直接返回它的BUSINESS_CODE
            return Maps.getString(materialInfoMap01, "materialClassNumber");
        } else {
            // 如果它是同步的物料
            // 返回原始物料的BUSINESS_CODE
            Map<String, String> materialInfoMap02 = CacheUtil.getMaterialInfo(Maps.getString(materialInfoMap01, "relatedId"),
                    MaterialInfoEnum.MATERIAL_INFO);
            return Maps.getString(materialInfoMap02, "materialClassNumber");
        }
    }

    // 获取猪舍SAP编号
    public static String getMTC_Unit(Long houseId) throws Exception {
        if (houseId == null) {
            return null;
        }

        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT HOUSE_NAME AS houseName,CONCAT(HOUSE_PRIFIX_CODE,HOUSE_UNIT_NUMBER,HOUSE_BUILDING_NUMBER) AS mtcUnit");
        sqlCon.addMainSql(" FROM PP_O_HOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addCondition(houseId, " AND ROW_ID = ?");

        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        String mtcUnit = null;
        if (!list.isEmpty()) {
            String houseName = Maps.getString(list.get(0), "houseName");
            mtcUnit = Maps.getString(list.get(0), "mtcUnit");
            if (mtcUnit == null) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "猪舍【" + houseName + "】没有设置单元号、栋号。。。");
            }
        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【HanaUtil.getMTC_Unit】系统异常。。。");
        }
        return mtcUnit;
    }

    // 获取SAP人员名称
    public static String getMTC_EmpName(long farmId, long employeeId) throws Exception {
        Map<String, String> employeeIdNameMap = CacheUtil.getData(farmId, "HR_O_EMPLOYEE", String.valueOf(employeeId));
        return Maps.getString(employeeIdNameMap, "EMPLOYEE_NAME");
    }

    // 获取猪只等级
    public static String getMTC_Grade(String saleDescribe) {
        String result = null;
        if (PigConstants.SELL_GOOD_HOG_PIG.equals(saleDescribe)) {
            result = HanaConstants.MTC_GRADE_HOG_PIG;
        } else if (PigConstants.SELL_GOOD_PIGGY_PIG.equals(saleDescribe)) {
            result = HanaConstants.MTC_GRADE_PIGGY_PIG;
        } else if (PigConstants.SELL_GOOD_IMPERFECT_PIG.equals(saleDescribe)) {
            result = HanaConstants.MTC_GRADE_IMPERFECT_PIG;
        } else {
            result = "-";
        }
        return result;
    }

    // 根据houseID获取品名-固定值
    public static String getMTC_ItemCodeOfHouseType(long farmId, long houseId) throws Exception {
        Map<String, String> houseMap = CacheUtil.getData(farmId, "PP_O_HOUSE", String.valueOf(houseId));
        String houseType = Maps.getString(houseMap, "HOUSE_TYPE");
        String isFosterHouse = Maps.getString(houseMap, "IS_FOSTER_HOUSE");
        String itemCode = null;
        if (String.valueOf(PigConstants.HOUSE_CLASS_DELIVERY).equals(houseType)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_PIGLET;
        } else if (String.valueOf(PigConstants.HOUSE_CLASS_CAREPIG).equals(houseType)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_NURSING_PIG;
        } else if (String.valueOf(PigConstants.HOUSE_CLASS_FATTEN).equals(houseType) && PigConstants.IS_FOSTER_HOUSE_FALSE.equals(isFosterHouse)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_FATTENING_PIG;
        } else if (String.valueOf(PigConstants.HOUSE_CLASS_FATTEN).equals(houseType) && PigConstants.IS_FOSTER_HOUSE_TRUE.equals(isFosterHouse)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_CULTIVATE_PIG;
        }
        return itemCode;
    }

    // 获取销售品名-固定值(saleDescribe只用于判断后备猪)
    public static String getMTC_ItemCodeOfSale(String saleDescribe, long farmId, long houseId) throws Exception {
        if (PigConstants.SELL_GOOD_RESERVE_BOARD_PIG.equals(saleDescribe) || PigConstants.SELL_GOOD_RESERVE_SOW_PIG.equals(saleDescribe)) {
            return HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;
        } else if (PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(saleDescribe)) {
            return HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR;
        } else if (PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG.equals(saleDescribe)) {
            return HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW;
        } else {
            return getMTC_ItemCodeOfHouseType(farmId, houseId);
        }
    }

    // 获取销售品名-固定值(pigClass只用于判断后备猪)
    public static String getMTC_ItemCodeOfPigClass(long pigClass, long farmId, long houseId) throws Exception {
        if (PigConstants.PIG_CLASS_HBGZ == pigClass || PigConstants.PIG_CLASS_HBMZ == pigClass || PigConstants.PIG_CLASS_HBDP == pigClass) {
            return HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;
        } else if (PigConstants.PIG_CLASS_SCGZ == pigClass) {
            return HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR;
        } else if (pigClass == PigConstants.PIG_CLASS_SCGZ || pigClass == PigConstants.PIG_CLASS_FQ1 || pigClass == PigConstants.PIG_CLASS_LC
                || pigClass == PigConstants.PIG_CLASS_KH || pigClass == PigConstants.PIG_CLASS_RS || pigClass == PigConstants.PIG_CLASS_SOW_DN) {
            return HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW;
        } else {
            return getMTC_ItemCodeOfHouseType(farmId, houseId);
        }
    }

    // 获取猪只品种
    public static String getMTC_Breed(Long breedId) {
        if (breedId == null) {
            return "-";
        }
        return String.valueOf(breedId + 1000);
    }

    // 获取猪只性别
    public static String getMTC_Sex(String sex) {
        String sexStr = null;
        if (PigConstants.PIG_SEX_MIX.equals(sex)) {
            sexStr = "-";
        } else if (PigConstants.PIG_SEX_MALE.equals(sex)) {
            sexStr = "G";
        } else if (PigConstants.PIG_SEX_FEMALE.equals(sex)) {
            sexStr = "M";
        }
        return sexStr;
    }

    // 根据客户判断是否需要传入hana
    public static boolean isSaleToHana(String sapSaleType) throws Exception {
        // 调拨无需传haha
        // 内销在入场时传入HANA
        if (PigConstants.SAP_SALE_TYPE_ALLOCATION.equals(sapSaleType) || PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
            return false;
        }
        return true;
    }

    // 是否需要传两张单据
    public static boolean isSaleToAgain(String sapSaleType) throws Exception {
        // 只有内销需要传两张单据到hana
        if (PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
            return true;
        }
        return false;
    }

    // 获取sapSaleType
    public static String getSapSaleType(Long farmId, Long customerId) throws Exception {
        Map<String, String> farmCompanyMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(farmId));
        Map<String, String> cusCompanyMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(customerId));
        // 外销
        if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(Maps.getString(cusCompanyMap, "CUSSUP_TYPE"))) {
            return PigConstants.SAP_SALE_TYPE_OUTSIDE;
        }
        // 自宰
        if (customerId.compareTo(farmId) == 0) {
            return PigConstants.SAP_SALE_TYPE_SELF_KILL;
        }
        // 调拨
        Map<String, String> farmMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
        Map<String, String> customerMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(customerId);
        if (Maps.getString(farmMap, "MTC_BranchID").equals(Maps.getString(customerMap, "MTC_BranchID"))) {
            return PigConstants.SAP_SALE_TYPE_ALLOCATION;
        }
        // 内销
        String farmCompanyMark = Maps.getString(farmCompanyMap, "COMPANY_MARK");
        String cusCompanyMark = Maps.getString(cusCompanyMap, "COMPANY_MARK");
        String[] farmIds = farmCompanyMark.split(",");
        String[] cusIds = cusCompanyMark.split(",");
        if (farmIds.length <= 2 || cusIds.length <= 2) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "当前账号为新农+无法销售！");
        }
        if (farmIds[2].equals(cusIds[2])) {
            return PigConstants.SAP_SALE_TYPE_INSIDE;
        }
        // 新农+平台中的外销
        return PigConstants.SAP_SALE_TYPE_OUTSIDE;
    }

    // 判断销售类型
    public static String getSaleType(String sapSaleType) throws Exception {
        String saleType = null;
        // 内部销售
        if (PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
            saleType = HanaConstants.MTC_SALES_TYPE_SI;
            // 常规销售（外销）
        } else if (PigConstants.SAP_SALE_TYPE_OUTSIDE.equals(sapSaleType)) {
            saleType = HanaConstants.MTC_SALES_TYPE_SO;
            // 自宰
        } else if (PigConstants.SAP_SALE_TYPE_SELF_KILL.equals(sapSaleType)) {
            saleType = HanaConstants.MTC_SALES_TYPE_SZ;
        }
        return saleType;
    }

    // SAP是否库存管理，库存管理才有出库和反出库操作
    public static boolean isWarehouseMaterialToHanaCheck(String materialFirstClass, String materialSecondClass) {
        // 制造费用 和 固定资产 和 精液 不是库存管理
        // 除去
        // 周转材料
        // 制造费用
        // 精液
        // 固定资产
        if (SupplyChianConstants.MATERIAL_FIRST_CLASS_40.equals(materialFirstClass) || SupplyChianConstants.MATERIAL_FIRST_CLASS_50.equals(materialFirstClass) || SupplyChianConstants.MATERIAL_FIRST_CLASS_60.equals(
                materialFirstClass) || SupplyChianConstants.MATERIAL_FIRST_CLASS_90.equals(materialFirstClass)) {
            return false;
        }

        return true;
    }
    
    // 获取固定资产卡片 - 猪只性别
    public static String getMTC_ItmsGrpCod(String sex) {
        String MTC_ItmsGrpCod = null;
        if(PigConstants.PIG_SEX_FEMALE.equals(sex)){
            MTC_ItmsGrpCod = HanaConstants.MTC_ITEM_SEX_SOW;
        } else if (PigConstants.PIG_SEX_MALE.equals(sex)) {
            MTC_ItmsGrpCod = HanaConstants.MTC_ITEM_SEX_BOAR;
        }
        return MTC_ItmsGrpCod;
    }

    // 获取固定资产卡片 - 资产类型
    public static String getMTC_Type(String branchId, String sex) {
        String MTC_Type = null;
        if (PigConstants.PIG_SEX_FEMALE.equals(sex)) {
            MTC_Type = branchId + "-" + HanaConstants.MTC_TYPE_SOW;
        } else if (PigConstants.PIG_SEX_MALE.equals(sex)) {
            MTC_Type = branchId + "-" + HanaConstants.MTC_TYPE_BOAR;
        }
        return MTC_Type;
    }

    // 判断猪只是否为生产猪
    public static boolean isProductPig(long pigClass) {
        // 生产公猪 || 发情 || 返情 || 流产 || 空怀 || 妊娠 || 哺乳 || 断奶
        if (PigConstants.PIG_CLASS_SCGZ == pigClass || PigConstants.PIG_CLASS_FQ == pigClass || PigConstants.PIG_CLASS_FQ1 == pigClass
                || PigConstants.PIG_CLASS_LC == pigClass || PigConstants.PIG_CLASS_KH == pigClass || PigConstants.PIG_CLASS_RS == pigClass
                || PigConstants.PIG_CLASS_BR == pigClass || PigConstants.PIG_CLASS_SOW_DN == pigClass) {
            return true;
        }
        return false;
    }

    public static String getMTC_ItemCodeOfSale(String saleDescribe, long farmId, long houseId, String pigClass) throws Exception {
        // 如果销售品名为留种公母，并且入场为后备状态，返回后备种猪
        if (String.valueOf(PigConstants.PIG_CLASS_HBMZ).equals(pigClass) || String.valueOf(PigConstants.PIG_CLASS_HBGZ).equals(pigClass)) {
            return HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;
        }
        return getMTC_ItemCodeOfSale(saleDescribe, farmId, houseId);
    }

    // 获取该猪场的HANA DB
    public static String getDbBeanName(long farmId) throws Exception {
        SysHanaDbFarmMapper sysHanaDbFarmMapper = SpringContextUtil.getBean("sysHanaDbFarmMapper", SysHanaDbFarmMapper.class);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(farmId, " AND FARM_ID = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("condition", sqlCon.getCondition());
        List<SysHanaDbFarmModel> sysHanaDbFarmModelList = sysHanaDbFarmMapper.searchListByCon(sqlMap);

        Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(farmId));
        String companyCode = Maps.getString(companyInfoMap, COMPANY_CODE);
        if (sysHanaDbFarmModelList.isEmpty()) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【" + companyCode + "】，HANA数据源没有配置，请联系管理员！");
        } else if (sysHanaDbFarmModelList.size() > 1) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【" + companyCode + "】，HANA数据源存在多条配置，请联系管理员！");
        }
        return DynamicDataSource.DATA_SOURCE + sysHanaDbFarmModelList.get(0).getDbBeanId().toString();
    }

}
