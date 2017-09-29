package xn.pigfarm.service.backend.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.system.BcodeMapper;
import xn.core.mapper.system.BcodeTypeMapper;
import xn.core.mapper.system.QuickMenusMapper;
import xn.core.model.system.BcodeModel;
import xn.core.model.system.BcodeTypeModel;
import xn.core.model.system.BcodeView;
import xn.core.model.system.MenuView;
import xn.core.model.system.QuickMenusModel;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.shiro.UserDetail;
import xn.core.util.JacksonUtil;
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaListOfPigBreeds;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.BreedMapper;
import xn.pigfarm.mapper.backend.CdButtonMapper;
import xn.pigfarm.mapper.backend.CdCodeMapper;
import xn.pigfarm.mapper.backend.CdModuleMapper;
import xn.pigfarm.mapper.backend.CodeListMapper;
import xn.pigfarm.mapper.backend.MenusMapper;
import xn.pigfarm.mapper.backend.MonthPerformanceMapper;
import xn.pigfarm.mapper.backend.MonthPerformanceModuleMapper;
import xn.pigfarm.mapper.backend.PartsMapper;
import xn.pigfarm.mapper.backend.PigClassMapper;
import xn.pigfarm.mapper.backend.PigHouseMapper;
import xn.pigfarm.mapper.backend.SurvivalTargetMapper;
import xn.pigfarm.mapper.backend.SysUpdateLogMapper;
import xn.pigfarm.mapper.backend.TemplateMapper;
import xn.pigfarm.mapper.backend.WeMmessageHisMapper;
import xn.pigfarm.mapper.backend.WeMpushMessageMapper;
import xn.pigfarm.mapper.backend.WeRmessageUserMapper;
import xn.pigfarm.mapper.backend.WeekMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeMapper;
import xn.pigfarm.mapper.basicinfo.IndicatorMapper;
import xn.pigfarm.mapper.basicinfo.SettingMapper;
import xn.pigfarm.mapper.basicinfo.SettingModuleMapper;
import xn.pigfarm.model.backend.BreedModel;
import xn.pigfarm.model.backend.CdButtonModel;
import xn.pigfarm.model.backend.CdCodeListModel;
import xn.pigfarm.model.backend.CdModuleModel;
import xn.pigfarm.model.backend.CodeListModel;
import xn.pigfarm.model.backend.MenusModel;
import xn.pigfarm.model.backend.MonthPerformanceModel;
import xn.pigfarm.model.backend.MonthPerformanceModuleModel;
import xn.pigfarm.model.backend.PartsModel;
import xn.pigfarm.model.backend.PigClassModel;
import xn.pigfarm.model.backend.PigHouseModel;
import xn.pigfarm.model.backend.SurvivalTargetModel;
import xn.pigfarm.model.backend.SysUpdateLogModel;
import xn.pigfarm.model.backend.TemplateModel;
import xn.pigfarm.model.backend.WeMpushMessageModel;
import xn.pigfarm.model.backend.WeRmessageUserModel;
import xn.pigfarm.model.backend.WeekModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.IndicatorModel;
import xn.pigfarm.model.basicinfo.SettingModel;
import xn.pigfarm.model.basicinfo.SettingModuleModel;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.util.TreeBuildUtil;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-4-7 Time:9:39:33
 */
@Service("backEndService")
public class BackEndServiceImpl extends BaseServiceImpl implements IBackEndService {

    @Autowired
    private CdCodeMapper cdCodeMapper;

    @Autowired
    private SysUpdateLogMapper sysUpdateLogMapper;

    @Autowired
    private CodeListMapper codeListMapper;

    @Autowired
    private CdModuleMapper cdModuleMapper;

    @Autowired
    private CdButtonMapper cdButtonMapper;

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Autowired
    private BcodeMapper bcodeMapper;

    @Autowired
    private BcodeTypeMapper bcodeTypeMapper;

    @Autowired
    private SettingModuleMapper settingModuleMapper;

    @Autowired
    private PigClassMapper pigClassMapper;

    @Autowired
    private BreedMapper breedMapper;

    @Autowired
    private PigHouseMapper pigHouseMapper;

    @Autowired
    private WeekMapper weekMapper;

    @Autowired
    private PartsMapper partsMapper;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private MenusMapper menusMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private QuickMenusMapper quickMenusMapper;

    @Autowired
    private MonthPerformanceMapper monthPerformanceMapper;

    @Autowired
    private MonthPerformanceModuleMapper monthPerformanceModuleMapper;

    @Autowired
    private SurvivalTargetMapper survivalTargetMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private WeMpushMessageMapper weMpushMessageMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private WeRmessageUserMapper weRmessageUserMapper;

    @Autowired
    private WeMmessageHisMapper weMmessageHisMapper;

    @Override
    public List<CodeListModel> searchCdCodeDetailToList(String typeCode) throws Exception {

        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(typeCode, " AND TYPE_CODE= ?");
        return getList(codeListMapper, sqlCon);
    }

    @Override
    public List<CdCodeListModel> searchCdCodeByTypeCode(String typeCode, String linkValue) throws Exception {

        return cdCodeMapper.searchByTypeCode(typeCode, linkValue);
    }

    @Override
    public List<CodeListModel> searchCodeListToList() throws Exception {

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" GROUP BY TYPE_CODE");
        return getList(codeListMapper, sqlCon);
    }

    @Override
    public BasePageInfo searchCdCodeListToPage(String typeCode, String typeName) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT ROW_ID,DELETED_FLAG,TYPE_CODE,TYPE_NAME,SUP_TYPE,COUNT(*) AS CODE_VALUE,NOTES FROM CD_L_CODE_LIST ");
        sql.addMainSql(" WHERE DELETED_FLAG = 0 ");
        sql.addCondition(typeCode, " AND  TYPE_CODE LIKE ? ", true);
        sql.addCondition(typeName, " AND  TYPE_NAME LIKE ? ", true);
        sql.addMainSql(" GROUP BY TYPE_CODE");
        setToPage();
        return getPageInfo(setSql(codeListMapper, sql));
    }

    @Override
    public void deleteCdCode(String[] typeCodes) throws Exception {

        // List<Map<String, Object>> listMap = new ArrayList<Map<String,
        // Object>>();
        // Map<String, Object> map = new HashMap<String, Object>();
        // map.put("RECORD_CON", "type_code");
        // map.put("RECORD_CON", codList.getTypeCode());

        // listMap.add(map);
        setDeletes(codeListMapper, typeCodes, "TYPE_CODE");
        // codeListMapper.deletesByCon(listMap);

    }

    @Override
    public List<CdModuleModel> searchMenuByUserId() throws Exception {

        return cdModuleMapper.searchMenuByUserId(getFarmId(), getEmployeeId());
    }

    @Override
    public List<CdButtonModel> searchCdModuelDetailToList(long mainId) throws Exception {

        return cdButtonMapper.searchDetailToList(mainId);
    }

    @Override
    public BasePageInfo searchCdModuelToPage(String moduleName, String component, String usingFlag) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addCondition(moduleName, " AND CM.MODULE_NAME LIKE ?", true);
        sql.addCondition(component, " AND cm.COMPONENT LIKE ?", true);
        sql.addCondition(usingFlag, " AND CM.USING_FLAG = ?");
        setToPage();
        return getPageInfo(getList(cdModuleMapper, sql));
    }

    @Override
    public void editCdModuel(CdModuleModel cdModuleModel, String buttonList) throws Exception {
        // 0 表示新增，1表示更新
        int flag = 0;
        if (cdModuleModel.getRowId() != 0) {
            flag = 1;
        }
        List<CdButtonModel> buttonLists = JacksonUtil.jacksonToCollection(buttonList, ArrayList.class, CdButtonModel.class);

        long nameIsExist = ParamUtil.isExist("cd_o_module", cdModuleModel.getRowId(), cdModuleModel.getModuleName(), "MODULE_NAME");
        if (nameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, cdModuleModel.getModuleName());
        }
        // 新增或修改MODULE表
        if (cdModuleModel.getRowId() == 0 && "0".equals(cdModuleModel.getDeletedFlag())) {
            cdModuleMapper.insert(cdModuleModel);
        } else {
            cdModuleMapper.update(cdModuleModel);
        }
        List<CdButtonModel> cdButtonByInsert = new ArrayList<CdButtonModel>();
        List<CdButtonModel> cdButtonByUpdate = new ArrayList<CdButtonModel>();
        for (Iterator<CdButtonModel> it = buttonLists.iterator(); it.hasNext();) {
            CdButtonModel CdButtonModel = (CdButtonModel) it.next();
            // 获取主表ID
            CdButtonModel.setModuleId(cdModuleModel.getRowId());
            // 名字和代码的重复验证
            long btnCodeIsExist = ParamUtil.isExist("cd_o_button", CdButtonModel.getRowId(), new String[] { CdButtonModel.getBtnCode(), CdButtonModel
                    .getModuleId() + "" }, "BTN_CODE,MODULE_ID");

            long btnNameIsExist = ParamUtil.isExist("cd_o_button", CdButtonModel.getRowId(), new String[] { CdButtonModel.getBtnName(), CdButtonModel
                    .getModuleId() + "" }, "BTN_NAME,MODULE_ID");
            if (btnCodeIsExist > 0) {
                Thrower.throwException(BaseBusiException.CODE_DUPLICATE_ROW_ERROR, CdButtonModel.getLineNumber(), CdButtonModel.getBtnCode());
            }
            if (btnNameIsExist > 0) {
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ROW_ERROR, CdButtonModel.getLineNumber(), CdButtonModel.getBtnName());
            }
            // 新增、修改、删除明细表
            if (flag == 0) {
                cdButtonByInsert.add(CdButtonModel);
            } else {
                if (CdButtonModel.getRowId() == 0 && !"1".equals(CdButtonModel.getDeletedFlag())) {
                    cdButtonByInsert.add(CdButtonModel);
                } else if (CdButtonModel.getRowId() != 0 && "0".equals(CdButtonModel.getDeletedFlag())) {
                    cdButtonByUpdate.add(CdButtonModel);

                } else if ("1".equals(CdButtonModel.getDeletedFlag())) {
                    long[] ids = new long[1];
                    ids[0] = CdButtonModel.getRowId();
                    cdButtonMapper.deletes(ids);
                }
            }
        }
        if (!cdButtonByInsert.isEmpty()) {
            cdButtonMapper.inserts(cdButtonByInsert);
        }
        if (!cdButtonByUpdate.isEmpty()) {
            cdButtonMapper.updates(cdButtonByUpdate);
        }
    }

    @Override
    public List<CdModuleModel> searchCdModuelToList() {
        return cdModuleMapper.searchToList();
    }

    @Override
    public void deleteCdModuels(long[] ids) throws Exception {

        cdModuleMapper.deletes(ids);
        cdButtonMapper.deletes(ids);
    }

    @Override
    public List<IndicatorModel> searchIndicatorToList() throws Exception {

        return indicatorMapper.searchToList(getFarmId());
    }

    @Override
    public List<Map<String, Object>> searchIndicatorCustToList(Map<String, Object> inputMap) throws Exception {
        String farmId = Maps.getString(inputMap, "farmId");
        if (farmId == null) {
            farmId = String.valueOf(getFarmId());
        }
        Map<String, String> map = new HashMap<>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IF(p2.ROW_ID IS NULL,-1,p2.ROW_ID) rowId,p1.BUSINESS_CODE businessCode,p1.IND_NAME indName,");
        sqlCon.addMainSql(" p1.STANDARD_VALUE businessValue,IF(p2.STANDARD_VALUE IS NULL,p1.STANDARD_VALUE,p2.STANDARD_VALUE) standardValue,");
        sqlCon.addMainSql(" IF(p2.WEEK_STANDARD_VALUE IS NULL,p1.WEEK_STANDARD_VALUE,p2.WEEK_STANDARD_VALUE) weekStandardValue,");
        sqlCon.addMainSql(" IF(p2.MONTH_STANDARD_VALUE IS NULL,p1.MONTH_STANDARD_VALUE,p2.MONTH_STANDARD_VALUE) monthStandardValue,");
        sqlCon.addMainSql(" IF(p2.QUARTER_STANDARD_VALUE IS NULL,p1.QUARTER_STANDARD_VALUE,p2.QUARTER_STANDARD_VALUE) quarterStandardValue,");
        sqlCon.addMainSql(" IF(p2.YEAR_STANDARD_VALUE IS NULL,p1.YEAR_STANDARD_VALUE,p2.YEAR_STANDARD_VALUE) yearStandardValue,");
        sqlCon.addMainSql("IF(p2.MIN_VALUE IS NULL,p1.MIN_VALUE,p2.MIN_VALUE) minValue,");
        sqlCon.addMainSql("IF(p2.MAX_VALUE IS NULL,p1.MAX_VALUE,p2.MAX_VALUE) `maxValue`,");
        sqlCon.addMainSql("p1.SECTION section,p1.UNIT unit,");
        sqlCon.addMainSql(" p1.FORMULA formula,p1.DESCRIPTION description FROM pp_l_indicator p1 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_indicator p2 ON p1.BUSINESS_CODE = p2.BUSINESS_CODE AND p2.DELETED_FLAG = '0'  AND p2.STATUS = '1' ");
        sqlCon.addConditionWithNull(farmId, " AND p2.FARM_ID =?");
        sqlCon.addMainSql(" WHERE p1.DELETED_FLAG = '0' AND p1.STATUS = '1' ");
        sqlCon.addConditionWithNull(get10000FarmId(), " AND p1.FARM_ID = ?");
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);
    }

    @Override
    public BasePageInfo searchIndicatorToPage(String businessCode, String indName, String standardValue, String minValue, String maxValue,
            String unit) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(getFarmId(), "AND FARM_ID = ?");
        sql.addCondition(businessCode, " AND BUSINESS_CODE LIKE ? ", true);
        sql.addCondition(indName, " AND IND_NAME LIKE  ? ", true);
        sql.addCondition(standardValue, " AND STANDARD_VALUE = ? ");
        sql.addCondition(minValue, " AND MIN_VALUE >= ? ");
        sql.addCondition(maxValue, " AND MAX_VALUE <= ? ");
        sql.addCondition(unit, " AND UNIT = ? ");
        setToPage();
        return getPageInfo(getList(indicatorMapper, sql));
    }

    @Override
    public void editIndicator(IndicatorModel indicatorModel) throws Exception {

        // 判断是否重名
        long nameIsExist = ParamUtil.isExist("pp_l_indicator", indicatorModel.getRowId(), indicatorModel.getIndName(), "IND_NAME");
        if (nameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, indicatorModel.getIndName());
        }
        indicatorModel.setFarmId(getFarmId());
        // 新增或修改indicator表
        if (indicatorModel.getRowId() == 0 && "0".equals(indicatorModel.getDeletedFlag())) {
            indicatorMapper.insert(indicatorModel);
        } else {
            indicatorMapper.update(indicatorModel);
        }

    }

    @Override
    public void editIndicatorCust(List<IndicatorModel> indicatorList, long[] ids) throws Exception {

        for (IndicatorModel indicatorModel : indicatorList) {
            indicatorModel.setFarmId(getFarmId());
            indicatorModel.setCompanyId(getCompanyId());
        }
        if (ids != null && ids.length > 0) {
            indicatorMapper.deletes(ids);
        }
        indicatorMapper.inserts(indicatorList);
    }

    @Override
    public void recoverIndicatorCust(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            indicatorMapper.deletes(ids);
        }
    }

    @Override
    public void deleteIndicators(long[] ids) throws Exception {
        indicatorMapper.deletes(ids);

    }

    // @Override
    // public List<CdSettingModuleModel> searchCdMsettingModuleToList(long farmId) throws Exception {
    //
    // return cdSettingModuleMapper.searchCdMsettingModuleToList(farmId);
    // }

    @Override
    public List<Map<String, Object>> searchSettingModuleToList() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T1.groupCode, T1.groupName, T1.settingCode, T1.settingName, T1.settingValue, T1.sowType FROM (");
        sqlCon.addMainSql(" SELECT GROUP_CODE AS groupCode, GROUP_NAME AS groupName, SETTING_CODE AS settingCode,");
        sqlCon.addMainSql(" SETTING_NAME AS settingName, SETTING_VALUE AS settingValue, SOW_TYPE AS sowType");
        sqlCon.addMainSql(" FROM CD_M_SETTING WHERE DELETED_FLAG='0' AND STATUS = '1'  ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" UNION");
        sqlCon.addMainSql(" SELECT GROUP_CODE AS groupCode,GROUP_NAME AS groupName, SETTING_CODE AS settingCode, SETTING_NAME AS settingName,");
        sqlCon.addMainSql(" SETTING_VALUE AS settingValue, SOW_TYPE AS sowType FROM CD_M_SETTING_MODULE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG='0' AND STATUS = '1' AND NOT EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM CD_M_SETTING WHERE CD_M_SETTING.SETTING_CODE = CD_M_SETTING_MODULE.SETTING_CODE ");
        sqlCon.addMainSql(" AND DELETED_FLAG='0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" LIMIT 1)");
        sqlCon.addMainSql(" )T1 ORDER BY groupName");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public BasePageInfo searchCdMsettingModuleByPage(String settingModule, String settingName, String settingCode, String settingValue,
            String sowType) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(settingModule, " AND SETTING_MODULE LIKE ? ", true);
        sql.addCondition(settingName, " AND SETTING_NAME LIKE ? ", true);
        sql.addCondition(settingCode, " AND SETTING_CODE LIKE ? ", true);
        sql.addCondition(settingValue, " AND SETTING_VALUE LIKE ? ", true);
        sql.addCondition(sowType, " AND SOW_TYPE LIKE ? ", true);
        setToPage();
        return getPageInfo(getList(settingModuleMapper, sql));
    }

    @Override
    public void editCdSetting(SettingModuleModel entity, long[] ids, String jsonObj) throws Exception {

        long flag = 0;
        if ((entity != null) && entity.getRowId() != 0) {
            flag = settingModuleMapper.update(entity);
        } else if ((entity == null && ids != null) && ids.length > 0) {
            flag = settingModuleMapper.deletes(ids);
        } else if (entity != null) {
            flag = ParamUtil.isExist("cd_m_setting_module", entity.getRowId(), entity.getSettingName(), "SETTING_NAME");
            if (flag > 0)
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, entity.getSettingName());
            flag = settingModuleMapper.insert(entity);
        }
        if (flag < 0)
            Thrower.throwException(BaseBusiException.CUD_OPERATION_ERROR);
    }

    @Override
    public BasePageInfo searchCdTemplateToPage(String templateName) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(templateName, " AND TEMPLATE_NAME LIKE ?", true);
        setToPage();
        return getPageInfo(getList(templateMapper, sql));
    }

    @Override
    public List<Map<String, Object>> searchModuleByTemplateId(long templateId) throws Exception {

        // 获取顶层节点
        List<MenuView> menusList = cdModuleMapper.searchModuleByTemplateId(templateId);
        return TreeBuildUtil.getMenuTreeList(menusList);
    }

    @Override
    public List<CdModuleModel> searchModuleExcludeById(long[] ids) throws Exception {

        return cdModuleMapper.searchModuleExcludeById(ids);
    }

    @Override
    public List<PigClassModel> searchPigClassToList(String pigType) throws Exception {
        // 这里的pigType并不是猪只的类型，而是为了区分不同事件可选择猪只状态的区间范围
        SqlCon sqlCon = new SqlCon();
        if ("1".equals(pigType)) {
            sqlCon.addMainSql(" and ROW_ID = 1");
        } else if ("2".equals(pigType)) {
            sqlCon.addMainSql(" and ROW_ID in (3,11)");
        } else if ("3".equals(pigType)) {
            sqlCon.addMainSql(" and ROW_ID in (15,16)");
        } else if ("4".equals(pigType)) {
            sqlCon.addMainSql(" and ROW_ID in (1,2,3,4,6,7,8,9,11)");
            // 肉猪盘点需要哺乳、保育、育肥三种类型
        } else if ("5".equals(pigType)) {
            sqlCon.addMainSql(" and ROW_ID in (10,15,16)");
        } else if ("6".equals(pigType)) {
            sqlCon.addMainSql(" and ROW_ID in (14,15)");
        }
        return getList(pigClassMapper, sqlCon);
    }

    @Override
    public BasePageInfo searchPigClassToPage(String businessCode, String pigClassName, String pigType) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(businessCode, " AND BUSINESS_CODE LIKE ?", true);
        sql.addCondition(pigClassName, " AND PIG_CLASS_NAME LIKE ?", true);
        sql.addCondition(pigType, "AND PIG_TYPE = ?");
        setToPage();
        return getPageInfo(getList(pigClassMapper, sql));
    }

    @Override
    public void editPigClass(PigClassModel cpigClassModel) throws Exception {
        // 验证代码重复
        long pigHouseNameIsExist = ParamUtil.isExist("cd_l_pig_class", cpigClassModel.getRowId(), new String[] { cpigClassModel.getPigClassName(),
                cpigClassModel.getPigType() }, "PIG_CLASS_NAME,PIG_TYPE");
        if (pigHouseNameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, cpigClassModel.getPigClassName());
        }
        if (cpigClassModel.getRowId() == 0) {
            pigClassMapper.insert(cpigClassModel);
        } else {
            pigClassMapper.update(cpigClassModel);
        }
    }

    @Override
    public void deletePigClass(long[] ids) throws Exception {
        // 验证已被使用

        pigClassMapper.deletes(ids);
    }

    // =======================================
    @Override
    public List<BreedModel> searchBreedToList(String breedCode, String breedName) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(breedCode, " AND BREED_CODE LIKE ?", true);
        sql.addCondition(breedName, " AND BREED_NAME LIKE ?", true);
        // START
        List<BreedModel> breedModels = getList(breedMapper, sql);
        if (breedModels != null && !breedModels.isEmpty()) {
            for (BreedModel breedModel : breedModels) {
                breedModel.set("sapCode", breedModel.getRowId() + 1000);
            }
        }
        return breedModels;
        // END

        // return getList(breedMapper, sql);
    }

    @Override
    public BasePageInfo searchBreedToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sql = new SqlCon();
        sql.addCondition(Maps.getString(inputMap, "breedCode"), " AND BREED_CODE LIKE ?", true);
        sql.addCondition(Maps.getString(inputMap, "breedName"), " AND BREED_NAME LIKE ?", true);
        List<BreedModel> breedModels = getList(breedMapper, sql);
        if (breedModels != null && !breedModels.isEmpty()) {
            for (BreedModel breedModel : breedModels) {
                breedModel.set("sapCode", breedModel.getRowId() + 1000);
            }
        }
        return getPageInfo(breedModels);
        // return getPageInfo(getList(breedMapper, sql));
    }

    @Override
    public void editBreed(BreedModel pigBreedModel, String breedList) throws Exception {
        // 验证代码重复
        long breedCodeIsExist = ParamUtil.isExist("cd_l_breed", pigBreedModel.getRowId(), pigBreedModel.getBreedCode(), "BREED_CODE");
        long breedNameIsExist = ParamUtil.isExist("cd_l_breed", pigBreedModel.getRowId(), pigBreedModel.getBreedName(), "BREED_NAME");
        if (breedCodeIsExist > 0) {
            Thrower.throwException(BaseBusiException.CODE_DUPLICATE_ERROR, pigBreedModel.getBreedCode());
        }
        if (breedNameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, pigBreedModel.getBreedName());
        }
        if (pigBreedModel.getRowId() == 0) {
            breedMapper.insert(pigBreedModel);

            // START
            long farmId = CacheUtil.getFarmId();
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(farmId));
            String companyCode = Maps.getString(companyInfoMap, "COMPANY_CODE");

            Date currentDate = new Date();
            HanaListOfPigBreeds hanaListOfPigBreeds = new HanaListOfPigBreeds();
            // 品种ID
            hanaListOfPigBreeds.setMTC_Code(String.valueOf(pigBreedModel.getRowId() + 1000));
            // 品种描述
            hanaListOfPigBreeds.setMTC_Description(pigBreedModel.getRowId() + 1000 + " " + "-" + " " + pigBreedModel.getBreedName());
            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(companyCode);
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            // 业务代码:主数据 - 猪只品种清单
            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1040);
            // 新农+单据编号：品种编号
            mtcITFC.setMTC_DocNum(String.valueOf(pigBreedModel.getRowId() + 1000));
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
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaListOfPigBreeds);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

            hanaCommonService.insertMTC_ITFC(mtcITFC);

            // END
        } else {
            breedMapper.update(pigBreedModel);

            // START
            long farmId = CacheUtil.getFarmId();
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(farmId));
            String companyCode = Maps.getString(companyInfoMap, "COMPANY_CODE");
            Date currentDate = new Date();
            HanaListOfPigBreeds hanaListOfPigBreeds = new HanaListOfPigBreeds();
            // 品种ID
            hanaListOfPigBreeds.setMTC_Code(String.valueOf(pigBreedModel.getRowId() + 1000));
            // 品种描述
            hanaListOfPigBreeds.setMTC_Description(pigBreedModel.getRowId() + 1000 + " " + "-" + " " + pigBreedModel.getBreedName());
            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(companyCode);
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            // 业务代码:主数据 - 猪只品种清单
            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1040);
            // 新农+单据编号：品种编号
            mtcITFC.setMTC_DocNum(String.valueOf(pigBreedModel.getRowId() + 1000));
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分：猪只品种清单的更新记录处理区分传A
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // DB
            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaListOfPigBreeds);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

            hanaCommonService.insertMTC_ITFC(mtcITFC);

            // END
        }

    }

    @Override
    public void deleteBreeds(long[] ids) throws Exception {
        breedMapper.deletes(ids);
    }

    // =======================================
    @Override
    public List<PigHouseModel> searchPigHouseToList(String pigType) throws Exception {
        SqlCon sqlCon = new SqlCon();
        Map<String, String> sqlMap = new HashMap<String, String>();
        if ("3".equals(pigType)) {
            sqlCon.addMainSql(" AND ROW_ID IN (6,8,9) ");
            sqlMap.put("condition", sqlCon.getCondition());
            return pigHouseMapper.searchListByCon(sqlMap);
        } else {
            return pigHouseMapper.searchToList();
        }
    }

    @Override
    public BasePageInfo searchPigHouseToPage(String houseTypeName, String disinfectDay, String disinfectMethod) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(houseTypeName, " AND HOUSE_TYPE_NAME LIKE ?", true);
        sql.addCondition(disinfectDay, " AND DISINFECT_DAY = ?");
        // 消毒方法
        // sql.addCondition(disinfectMethod, "");
        setToPage();
        return getPageInfo(getList(pigHouseMapper, sql));
    }

    @Override
    public void editPigHouse(PigHouseModel pigHouseModel) throws Exception {
        // 验证代码重复
        long pigHouseNameIsExist = ParamUtil.isExist("cd_l_pig_house", pigHouseModel.getRowId(), pigHouseModel.getHouseTypeName(), "HOUSE_TYPE_NAME");
        if (pigHouseNameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, pigHouseModel.getHouseTypeName());
        }
        if (pigHouseModel.getRowId() == 0) {
            pigHouseMapper.insert(pigHouseModel);
        } else {
            pigHouseMapper.update(pigHouseModel);
        }

    }

    @Override
    public void deletePigHouses(long[] ids) throws Exception {
        pigHouseMapper.deletes(ids);
    }

    @Override
    public List<BcodeView> searchBcodeToList() throws Exception {
        return bcodeMapper.searchAllToPage(getFarmId());
    }

    @Override
    public BasePageInfo searchBcodeToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        BcodeView bcode = new BcodeView();
        bcode.setTypeCode(Maps.getString(inputMap, "typeCode"));
        bcode.setBcodeName(Maps.getString(inputMap, "bcodeName"));
        bcode.setPrifixCode(Maps.getString(inputMap, "prifixCode"));
        List<BcodeView> bcodeViewList = bcodeMapper.searchByConToPage(getFarmId(), bcode);
        BasePageInfo bcodeViewBasePageInfo = getPageInfo(bcodeViewList);
        List<Map<String, Object>> result = new ArrayList<>();

        for (BcodeView bcodeView : bcodeViewList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rowId", bcodeView.getRowId());
            resultMap.put("typeCode", bcodeView.getTypeCode());
            resultMap.put("bcodeName", bcodeView.getBcodeName());
            resultMap.put("serialLength", bcodeView.getSerialLength());
            resultMap.put("prifixCode", bcodeView.getPrifixCode());
            resultMap.put("houseId", bcodeView.getHouseId());
            resultMap.put("isUseBdate", CacheUtil.getName(bcodeView.getIsUseBdate(), NameEnum.CODE_NAME, CodeEnum.IS_USE_BDATE));
            resultMap.put("limitNum", bcodeView.getLimitNum());
            resultMap.put("level", bcodeView.getLevel());
            resultMap.put("houseName", CacheUtil.getName(bcodeView.getHouseId() + "", NameEnum.HOUSE_NAME));
            resultMap.put("notes", bcodeView.getNotes());
            result.add(resultMap);
        }
        bcodeViewBasePageInfo.setList(result);
        return bcodeViewBasePageInfo;
    }

    @Override
    public List<BcodeModel> searchBcodeCustToList() throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql(
                " SELECT IF(c2.Row_ID IS NULL,NULL,c2.ROW_ID) rowId,c1.BCODE_TYPE_ID,IF(c2.SERIAL_LENGTH IS NULL,c1.SERIAL_LENGTH,c2.SERIAL_LENGTH) serialLength, ");
        sql.addMainSql(
                " IF(c2.PRIFIX_CODE IS NULL,c1.PRIFIX_CODE,c2.PRIFIX_CODE) prifixCode,IF(c2.IS_USE_BDATE IS NULL,c1.IS_USE_BDATE,c2.IS_USE_BDATE) isUseBdate, ");
        sql.addMainSql(
                " c1.SERIAL_MAX serialMax,c1.SERIAL_MIN serialMin,c1.HOUSE_ID houseId,c1.LAST_NUM lastNum,c1.NEXT_SEIAL nextSeial FROM cd_l_bcode c1 ");
        sql.addMainSql(" LEFT JOIN cd_l_bcode c2 ON c1.BCODE_TYPE_ID = c2.BCODE_TYPE_ID AND c2.DELETED_FLAG = '0'  AND c2.STATUS = '1' ");
        sql.addConditionWithNull(getFarmId(), "AND c2.FARM_ID = ? ");
        sql.addMainSql(" WHERE c1.DELETED_FLAG = '0' AND c1.STATUS = '1' ");
        sql.addConditionWithNull(get10000FarmId(), "AND c1.FARM_ID = ? ");

        List<BcodeModel> bcodeCusList = setSql(bcodeMapper, sql);

        bcodeCusList = CacheUtil.setName(bcodeCusList, new NameEnum[] { NameEnum.BCODE_NAME, NameEnum.CODE_NAME }, CodeEnum.IS_USE_BDATE);
        return bcodeCusList;
    }

    @Override
    public void editBcode(BcodeTypeModel model, BcodeModel bcodeModel) throws Exception {

        long nameIsExist = ParamUtil.isExist("cd_l_bcode_type", model.getRowId(), model.getBcodeName(), "BCODE_NAME");
        long codeIsExist = ParamUtil.isExist("cd_l_bcode_type", model.getRowId(), model.getTypeCode(), "TYPE_CODE");
        if (nameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, model.getBcodeName());
        }
        if (codeIsExist > 0) {
            Thrower.throwException(BaseBusiException.CODE_DUPLICATE_ERROR, model.getTypeCode());
        }
        // 新增或修改bcode表
        if (model.getRowId() == 0 && "0".equals(model.getDeletedFlag())) {
            bcodeTypeMapper.insert(model);
            bcodeModel.setBcodeTypeId(model.getRowId());
            bcodeModel.setCompanyId(getCompanyId());
            bcodeModel.setFarmId(getFarmId());
            bcodeMapper.insert(bcodeModel);
        } else {
            bcodeTypeMapper.update(model);
            bcodeMapper.updateByBcodeTypeId(bcodeModel);
        }

    }

    @Override
    public void editBcodeCust(List<BcodeModel> bcodeList) throws Exception {
        List<BcodeModel> bcodeUpdateList = new ArrayList<>();
        List<BcodeModel> bcodeInsertList = new ArrayList<>();
        for (BcodeModel bcodeModel : bcodeList) {
            bcodeModel.setCompanyId(getCompanyId());
            bcodeModel.setFarmId(getFarmId());
            if (bcodeModel.getRowId() == null) {
                bcodeInsertList.add(bcodeModel);
            } else {
                bcodeUpdateList.add(bcodeModel);
            }
        }
        if (!bcodeUpdateList.isEmpty()) {
            bcodeMapper.updates(bcodeUpdateList);
        }
        if (!bcodeInsertList.isEmpty()) {
            bcodeMapper.inserts(bcodeInsertList);
        }
    }

    @Override
    public void recoverBcodeCust(long[] ids) throws Exception {
        bcodeMapper.deletes(ids);
    }

    @Override
    public void deleteBcodes(long[] ids) throws Exception {
        // 删除bcodeType
        bcodeTypeMapper.deletes(ids);
        // 删除对应bcodeType的bcode
        SqlCon sql = new SqlCon();
        String typeIds = getStringArray(ids);
        sql.addMainSql("UPDATE CD_L_BCODE SET DELETED_FLAG = 1 WHERE ");
        sql.addCondition(typeIds, " BCODE_TYPE_ID IN ? ", false, true);
        setSql(bcodeMapper, sql);

    }

    public String getStringArray(long ids[]) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            str.append(ids[i]);
            if (i < ids.length - 1) {
                str.append(",");
            }
        }
        return str.toString();
    }
    // @Override
    // public int searchWeekByCompanyID(long companyId, Date date) throws
    // Exception {
    //
    // return weekMapper.searchToList();
    // }

    @Override
    public List<PartsModel> searchPartsToList() throws Exception {

        return partsMapper.searchToList();
    }

    @Override
    public BasePageInfo searchPartsByPage() throws Exception {
        setToPage();
        List<PartsModel> list = partsMapper.searchToList();
        list = CacheUtil.setName(list, NameEnum.MODULE_NAME);
        return getPageInfo(list);
    }

    @Override
    public void editParts(PartsModel entity, long[] ids, String jsonObj) throws Exception {

        long flag = 0;
        if ((entity != null) && entity.getRowId() != 0) {
            flag = partsMapper.update(entity);
        } else if ((entity == null && ids != null) && ids.length > 0) {
            flag = partsMapper.deletes(ids);
        } else if (entity != null) {
            flag = ParamUtil.isExist("cd_o_parts", entity.getRowId(), new String[] { entity.getPartsCode(), entity.getPartsName() },
                    "PARTS_CODE,PARTS_NAME");
            if (flag > 0)
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, entity.getPartsCode(), entity.getPartsName());
            entity.setPartsCode(ParamUtil.getBCode("PARTS_CODE", getEmployeeId(), getCompanyId(), getFarmId()));
            entity.setModuleId(Long.parseLong(entity.getData().get("moduleName").toString()));
            flag = partsMapper.insert(entity);
        }
        if (flag < 0)
            Thrower.throwException(BaseBusiException.CUD_OPERATION_ERROR);
    }

    @Override
    public List<WeekModel> searchWeekToList() throws Exception {

        return weekMapper.searchToList(getFarmId());
    }

    @Override
    public BasePageInfo searchWeekByPage(String companyId, String year, String week, String startDate, String endDate) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addCondition(companyId, " AND COMPANY_ID LIKE ?", true);
        sql.addCondition(year, " AND YEAR = ?");
        sql.addCondition(week, " AND WEEK = ?");
        sql.addCondition(startDate, " AND START_DATE >= ?");
        sql.addCondition(endDate, " AND END_DATE <= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID=?");
        setToPage();
        return getPageInfo(getList(weekMapper, sql));
        // return getPageInfo(weekMapper.searchToList(getFarmId()));
    }

    @Override
    public void editTemplate(TemplateModel entity, String jsonObj) throws Exception {
        long flag = ParamUtil.isExist("cd_o_template", entity.getRowId(), entity.getTemplateName(), "TEMPLATE_NAME");
        if (flag > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, entity.getTemplateName());
        } else {
            List<MenusModel> listMenus = new ArrayList<>();
            if (entity.getRowId() == null || entity.getRowId() == 0) {
                templateMapper.insert(entity);
                if (entity.getRowId() == null || jsonObj == null) {
                    return;
                }
                List<Map<String, Object>> list = getMapList(jsonObj);
                for (Map<String, Object> hashMap : list) {
                    MenusModel menus = new MenusModel();
                    menus.setStatus("1");
                    menus.setDeletedFlag("0");
                    menus.setOriginApp("XN1.0");
                    menus.setOriginFlag("S");
                    menus.setParentId(Maps.getLong(hashMap, "parentId", 1L));
                    menus.setModuleId(Maps.getLong(hashMap, "id"));
                    menus.setTemplateId(entity.getRowId());
                    menus.setLevelNum(Maps.getLong(hashMap, "leaveNum"));
                    listMenus.add(menus);
                }
            } else {
                templateMapper.update(entity);
                setDeletes(menusMapper, entity.getRowId(), "TEMPLATE_ID");
                List<Map<String, Object>> list = getMapList(jsonObj);
                for (Map<String, Object> hashMap : list) {
                    MenusModel menus = new MenusModel();
                    menus.setStatus("1");
                    menus.setDeletedFlag("0");
                    menus.setOriginApp("XN1.0");
                    menus.setOriginFlag("S");
                    menus.setParentId(Maps.getLong(hashMap, "parentId", 1L));
                    menus.setModuleId(Maps.getLong(hashMap, "id"));
                    menus.setTemplateId(entity.getRowId());
                    menus.setLevelNum(Maps.getLong(hashMap, "leaveNum"));
                    listMenus.add(menus);
                }
            }
            if (!listMenus.isEmpty()) {
                menusMapper.inserts(listMenus);
            }
        }
    }

    @Override
    public void editCodeList(CodeListModel codeList, String detialListStr) throws Exception {
        long codeIsExist = ParamUtil.isExist("cd_l_code_list", codeList.getRowId(), codeList.getTypeCode(), "TYPE_CODE");
        long nameIsExist = ParamUtil.isExist("cd_l_code_list", codeList.getRowId(), codeList.getTypeName(), "TYPE_NAME");
        List<CodeListModel> list = getModelList(detialListStr, CodeListModel.class);
        // 编辑下--新增--删除
        if (codeList.getRowId() != 0 && list.size() > 0) {
            if (codeList.getRowId() != null) {

                List<CodeListModel> addList = new ArrayList<CodeListModel>();
                List<CodeListModel> updateList = new ArrayList<CodeListModel>();

                long[] ids = new long[list.size()];
                int sumDelete = 0;
                // 循环区分 新增-修改-删除明细数据
                for (Iterator<CodeListModel> iterator = list.iterator(); iterator.hasNext();) {
                    CodeListModel cdmodel = (CodeListModel) iterator.next();

                    if ("0".equals(cdmodel.getDeletedFlag())) {
                        cdmodel.setTypeCode(codeList.getTypeCode());
                        cdmodel.setTypeName(codeList.getTypeName());
                        cdmodel.setSupType(codeList.getSupType());
                        cdmodel.setNotes(codeList.getNotes());
                        if (cdmodel.getRowId() == null || cdmodel.getRowId() == 0) {
                            addList.add(cdmodel);
                        } else {
                            updateList.add(cdmodel);
                        }
                    } else if ("1".equals(cdmodel.getDeletedFlag())) {
                        ids[sumDelete] = cdmodel.getRowId();
                        sumDelete++;
                    }

                }
                // 新增
                if (addList.size() > 0) {
                    codeListMapper.inserts(addList);
                    CodeListModel model = codeListMapper.searchById(codeList.getRowId());
                    SqlCon sql = new SqlCon();
                    sql.addMainSql(" UPDATE CD_L_CODE_LIST SET ");
                    sql.addCondition(codeList.getTypeCode(), " TYPE_CODE = ? ,");
                    sql.addCondition(codeList.getTypeName(), " TYPE_NAME = ? ");
                    sql.addMainSql(" WHERE DELETED_FLAG = 0 ");
                    sql.addCondition(model.getTypeCode(), " AND TYPE_CODE = ? ");
                    setSql(codeListMapper, sql);
                }
                // 删除
                if (ids[0] != 0) {

                    codeListMapper.deletes(ids);
                }
                // 修改
                if (updateList.size() > 0) {
                    codeListMapper.updates(updateList);
                }
            }
        } else {
            if (codeIsExist > 0) {
                Thrower.throwException(BaseBusiException.CODE_DUPLICATE_ERROR, codeList.getTypeCode());
            }
            if (nameIsExist > 0) {
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, codeList.getTypeName());
            }
            // 新增
            if (codeList.getRowId() == null || codeList.getRowId() == 0) {
                // 有明细
                if (list != null && list.size() > 0) {
                    for (CodeListModel model : list) {
                        model.setTypeCode(codeList.getTypeCode());
                        model.setTypeName(codeList.getTypeName());
                        model.setSupType(codeList.getSupType());
                        model.setNotes(codeList.getNotes());
                    }
                    codeListMapper.inserts(list);
                }
                // 无明细直接插入
                else {
                    codeListMapper.insert(codeList);
                }
            }
            if (list.size() == 0) {
                codeListMapper.update(codeList);
            }
        }

    }

    @Override
    public void editSetting(Map<String, Object> inputMap) throws Exception {

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, NULL AS sortNbr, NULL AS status, NULL AS originFlag, NULL AS originApp, NULL AS notes, ");
        sqlCon.addMainSql(" NULL AS farmType, NULL AS settingModule, NULL AS groupCode, NULL AS groupName, SETTING_CODE AS settingCode,");
        sqlCon.addMainSql(" NULL AS settingName, SETTING_VALUE AS settingValue, SOW_TYPE AS sowType, NULL AS moemo,");
        sqlCon.addMainSql(" NULL AS correlationId FROM CD_M_SETTING WHERE DELETED_FLAG='0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" UNION");
        sqlCon.addMainSql(" SELECT '-1' AS rowId, SORT_NBR AS sortNbr, STATUS AS status, ORIGIN_FLAG AS originFlag, ORIGIN_APP AS originApp,");
        sqlCon.addMainSql(" NOTES AS notes, FARM_TYPE AS farmType, SETTING_MODULE AS settingModule, GROUP_CODE AS groupCode,");
        sqlCon.addMainSql(" GROUP_NAME AS groupName, SETTING_CODE AS settingCode, SETTING_NAME AS settingName,");
        sqlCon.addMainSql(" SETTING_VALUE AS settingValue, SOW_TYPE AS sowType, MEMO AS moemo,");
        sqlCon.addMainSql(" CORRELATION_ID AS correlationId FROM CD_M_SETTING_MODULE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG='0' AND STATUS = '1' AND NOT EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM CD_M_SETTING WHERE CD_M_SETTING.SETTING_CODE = CD_M_SETTING_MODULE.SETTING_CODE AND");
        sqlCon.addMainSql(" DELETED_FLAG='0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ? ");
        sqlCon.addMainSql(" LIMIT 1)");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<SettingModel> resultFromDB = settingMapper.operSql(sqlMap);

        List<SettingModel> insertlist = new ArrayList<>();
        List<SettingModel> updatelist = new ArrayList<>();

        for (SettingModel settingModel : resultFromDB) {

            // 当SOW_TYPE为checkbox
            // 当前台checkbox没有选中时无法将值传入后台
            // 默认VALUE为OFF
            // 选中时为ON
            if ("checkbox".equals(settingModel.getSowType())) {
                settingModel.setSettingValue("OFF");
                if (inputMap.get(settingModel.getSettingCode()) != null) {
                    settingModel.setSettingValue(((String) inputMap.get(settingModel.getSettingCode())).toUpperCase());
                }
            } else {
                // text时，修改数值
                settingModel.setSettingValue((String) inputMap.get(settingModel.getSettingCode()));
            }

            // 当ROW_ID为-1时，数据来源cd_m_setting_module，执行插入
            if (settingModel.getRowId().compareTo(-1L) == 0) {

                settingModel.setDeletedFlag("0");
                settingModel.setFarmId(getFarmId());
                settingModel.setCompanyId(getCompanyId());
                settingModel.setCreateId(getEmployeeId());
                settingModel.setCreateDate(new Date());

                insertlist.add(settingModel);

            } else {
                // 当ROW_ID不为-1时，数据来源cd_m_setting，执行更新

                // 清除不必要的更新字段，减少更新字段
                settingModel.setSettingCode(null);
                settingModel.setSowType(null);

                settingModel.setUpdateId(getEmployeeId());
                settingModel.setUpdateDate(new Date());
                updatelist.add(settingModel);
            }
        }

        if (!insertlist.isEmpty()) {
            settingMapper.inserts(insertlist);
        }
        if (!updatelist.isEmpty()) {
            settingMapper.updates(updatelist);
        }

    }

    @Override
    public void deleteSetting(long farmId) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" UPDATE CD_M_SETTING SET DELETED_FLAG = 1 ");
        sql.addConditionWithNull(getFarmId(), " WHERE FARM_ID = ? ");
        setSql(settingMapper, sql);
    }

    @Override
    public void deleteTemplate(long[] ids) throws Exception {

        setDeletes(menusMapper, ids, "TEMPLATE_ID");
        templateMapper.deletes(ids);

    }

    @Override
    public BasePageInfo searchCdBcodeToPageForAdvancedSearch(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.ROW_ID AS rowId, T0.BCODE_NAME AS bcodeName, T0.TYPE_CODE AS typeCode, T0.LIMIT_NUM AS limitNum,");
        sqlCon.addMainSql(
                "  T0.LEVEL AS level,T1.SERIAL_LENGTH AS serialLength, T1.PRIFIX_CODE AS prifixCode,T1.HOUSE_ID AS houseId, T1.IS_USE_BDATE AS isUseBdate");
        sqlCon.addMainSql(" FROM CD_L_BCODE_TYPE T0");
        sqlCon.addMainSql(" LEFT JOIN CD_L_BCODE T1");
        sqlCon.addMainSql(" ON (T0.ROW_ID = T1.BCODE_TYPE_ID AND T1.DELETED_FLAG= '0' AND T1.STATUS= '1')");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG= '0' AND T0.STATUS= '1'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(inputMap.get("typeCode"), " AND T0.TYPE_CODE LIKE ? ");
        sqlCon.addCondition(inputMap.get("bcodeName"), " AND T0.BCODE_NAME LIKE ? ");
        sqlCon.addCondition(inputMap.get("prifixCode"), " AND T1.PRIFIX_CODE LIKE ? ");
        sqlCon.addCondition(inputMap.get("houseId"), " AND T1.HOUSE_ID LIKE ? ");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : result) {
            map.put("houseName", CacheUtil.getName(String.valueOf(map.get("houseId")), NameEnum.HOUSE_NAME));
        }

        return getPageInfo(result);
    }

    @Override
    public List<Map<String, Object>> searchHouseToList() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, DELETED_FLAG AS deletedFlag, BUSINESS_CODE AS businessCode, ");
        sqlCon.addMainSql(
                " HOUSE_NAME AS houseName, HOUSE_TYPE AS houseType, DEPRECIATION_POLICY AS depreciationPolicy, COST AS cost, NOTES AS notes,");
        sqlCon.addMainSql(" (SELECT T1.pigQty FROM (SELECT HOUSE_ID,COUNT(1) AS pigQty FROM PP_L_PIG WHERE ");
        sqlCon.addMainSql(" DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS <=18 ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID =? ");
        sqlCon.addMainSql(" GROUP BY HOUSE_ID) T1 WHERE T1.HOUSE_ID = ROW_ID) AS pigQty,");
        sqlCon.addMainSql(" (SELECT T2.houseVolume FROM ");
        sqlCon.addMainSql(" (SELECT HOUSE_ID, SUM(IFNULL(PIG_NUM,0)) AS houseVolume FROM PP_O_PIGPEN WHERE");
        sqlCon.addMainSql(" AND DELETED_FLAG = '0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID =? ");
        sqlCon.addMainSql(" GROUP BY HOUSE_ID) T2");
        sqlCon.addMainSql(" WHERE T2.HOUSE_ID = ROW_ID) AS houseVolume ");
        sqlCon.addMainSql(" FROM PP_O_HOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG='0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ? ");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : result) {
            map.put("houseTypeName", CacheUtil.getName(String.valueOf(map.get("houseType")), NameEnum.HOUSE_TYPE_NAME));
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> searchHouseToListForAdvancedSearch() throws Exception {
        List<Map<String, Object>> result = this.searchHouseToList();
        Map<String, Object> map = new HashMap<>();
        map.put("rowId", "-1");
        map.put("houseName", "任意猪舍");
        result.add(0, map);
        return result;
    }

    @Override
    public List<Map<String, Object>> searchCdBcodeCustToListForAdvancedSearch(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IF(c2.Row_ID IS NULL,NULL,c2.ROW_ID) rowId,");
        sqlCon.addMainSql("  IF(c2.SERIAL_LENGTH IS NULL,c1.SERIAL_LENGTH,c2.SERIAL_LENGTH) serialLength,");
        sqlCon.addMainSql("  IF(c2.PRIFIX_CODE IS NULL,c1.PRIFIX_CODE,c2.PRIFIX_CODE) prifixCode,");
        sqlCon.addMainSql("  IF(c2.IS_USE_BDATE IS NULL,c1.IS_USE_BDATE,c2.IS_USE_BDATE) isUseBdate, ");
        sqlCon.addMainSql(" c3.BCODE_NAME bcodeName");
        sqlCon.addMainSql(" FROM cd_l_bcode c1 ");
        sqlCon.addMainSql(" LEFT JOIN cd_l_bcode c2 ON c1.BCODE_TYPE_ID = c2.BCODE_TYPE_ID AND c2.DELETED_FLAG = '0'  AND c2.STATUS = '1' ");
        sqlCon.addConditionWithNull(getFarmId(), "AND c2.FARM_ID = ? ");
        sqlCon.addMainSql("  INNER JOIN CD_L_BCODE_TYPE  c3 ON c1.BCODE_TYPE_ID=c3.ROW_ID AND c3.DELETED_FLAG = '0'  AND c3.STATUS = '1' ");
        sqlCon.addMainSql("  WHERE c1.DELETED_FLAG = '0' AND c1.STATUS = '1'  ");
        sqlCon.addConditionWithNull(get10000FarmId(), " AND c1.FARM_ID = ? ");
        sqlCon.addCondition(inputMap.get("bcodeName"), " AND  c3.BCODE_NAME LIKE ?", true);
        sqlCon.addCondition(inputMap.get("prifixCode"), " AND c1.PRIFIX_CODE LIKE ?", true);

        Map<String, String> sqlMap = new HashMap<>();
        String condition = sqlCon.getCondition();
        sqlMap.put("sql", condition);
        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> resultMap : result) {
            resultMap.put("isUseBdateName", CacheUtil.getName(String.valueOf(resultMap.get("isUseBdate")), NameEnum.CODE_NAME,
                    CodeEnum.IS_USE_BDATE));
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> searchModuleToList(String type) throws Exception {
        Map<String, String> sqlMap = new HashMap<>();
        if (type.equals("PC")) {
            sqlMap.put("sql", "SELECT ROW_ID AS moduleId, MODULE_NAME AS moduleName FROM CD_O_MODULE WHERE DELETED_FLAG = 0 AND MENU_TYPE =2");

        } else if (type.equals("WECHAT")) {
            sqlMap.put("sql", "SELECT ROW_ID AS moduleId, MODULE_NAME AS moduleName FROM CD_O_MODULE WHERE DELETED_FLAG = 0 AND MENU_TYPE = 4");
        }
        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public List<Map<String, Object>> searchMenuBobox() throws Exception {
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", "SELECT ROW_ID AS moduleId, MODULE_NAME AS moduleName FROM CD_O_MODULE WHERE DELETED_FLAG = 0 AND MENU_TYPE = 2");
        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public void editQuickMenus(List<QuickMenusModel> list) throws Exception {
        if (list == null) {
            return;
        }

        // 查询用户以前配置的菜单
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getUserDetail().getUserId(), "AND USER_ID= ?");
        List<QuickMenusModel> preList = getList(quickMenusMapper, sqlCon);

        // 1、新增列表
        List<QuickMenusModel> insertList = new ArrayList<>();
        // 2、修改列表
        List<QuickMenusModel> updateList = new ArrayList<>();
        // 3、删除列表
        List<QuickMenusModel> deleteList = new ArrayList<>();

        /* 前台未传入数据的赋值 */
        for (int i = 0; i < list.size(); i++) {
            QuickMenusModel editModel = list.get(i);
            editModel.setFarmId(getFarmId());
            editModel.setCompanyId(getCompanyId());
            editModel.setUserId(getUserDetail().getUserId());
        }

        for (QuickMenusModel preModel : preList) {
            Long rowId = preModel.getRowId();
            // 循环后还是true则删除
            boolean deleteFlag = true;
            for (int i = 0; i < list.size(); i++) {
                QuickMenusModel editModel = list.get(i);
                // 修改列表 在list、prelist
                if (rowId.equals(editModel.getRowId())) {
                    updateList.add(editModel);
                    deleteFlag = false;
                    list.remove(i);
                    break;
                }
            }
            // 删除列表 在prelist中不在list中
            if (deleteFlag) {
                deleteList.add(preModel);
            }
        }
        // 新增列表 在list中不在prelist
        insertList.addAll(list);

        if (!insertList.isEmpty()) {
            quickMenusMapper.inserts(list);
        }
        if (!updateList.isEmpty()) {
            quickMenusMapper.updates(updateList);
        }
        if (!deleteList.isEmpty()) {
            long[] deleteIds = new long[deleteList.size()];
            for (int i = 0; i < deleteList.size(); i++) {
                deleteIds[i] = deleteList.get(i).getRowId();
            }
            quickMenusMapper.deletes(deleteIds);
        }
    }

    /*************************************** yangy start 绩效管理 *****************************************/
    @Override
    public BasePageInfo performanceManageToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        List<MonthPerformanceModuleModel> monthPerformanceModules = null;
        String assessContent = Maps.getString(inputMap, "assessContent");
        String assessRegion = Maps.getString(inputMap, "assessRegion");
        monthPerformanceModules = monthPerformanceModuleMapper.searchToList(getFarmId());
        SqlCon sqlCon = new SqlCon();
        if (assessRegion != null) {
            sqlCon.addCondition(assessRegion, " AND ASSESS_REGION = ? ");
        }
        if (assessContent != null) {
            sqlCon.addCondition(assessContent, " AND ASSESS_CONTENT = ? ");
        }
        if (sqlCon != null && !sqlCon.getCondition().trim().isEmpty()) {
            sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            monthPerformanceModules = getList(monthPerformanceModuleMapper, sqlCon);
        }

        if (monthPerformanceModules != null && !monthPerformanceModules.isEmpty()) {
            for (MonthPerformanceModuleModel monthPerformanceModuleModel : monthPerformanceModules) {
                monthPerformanceModuleModel.set("isStartName", monthPerformanceModuleModel.getIsStart() == 1 ? "开启" : "关闭");
                monthPerformanceModuleModel.set("performanceDateAreaName", monthPerformanceModuleModel.getPerformanceDateArea() == 1 ? "上月26号到本月25号"
                        : "本月第一天到本月最后一天");
                if ("1".equals(monthPerformanceModuleModel.getAssessRegion())) {
                    monthPerformanceModuleModel.set("assessRegionName", "配种");
                } else if ("2".equals(monthPerformanceModuleModel.getAssessRegion())) {
                    monthPerformanceModuleModel.set("assessRegionName", "产房");
                } else if ("3".equals(monthPerformanceModuleModel.getAssessRegion())) {
                    monthPerformanceModuleModel.set("assessRegionName", "保育");
                } else if ("4".equals(monthPerformanceModuleModel.getAssessRegion())) {
                    monthPerformanceModuleModel.set("assessRegionName", "育肥");
                } else if ("5".equals(monthPerformanceModuleModel.getAssessRegion())) {
                    monthPerformanceModuleModel.set("assessRegionName", "场长");
                } else {
                }
                if ("1".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "产活数(头)");
                } else if ("2".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "分娩率(%)");
                } else if ("3".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "膘情(13-18)(非后备)");
                } else if ("4".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "窝均断奶重(KG)");
                } else if ("5".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "保育成活率(%)");
                } else if ("6".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "保育7030(KG)");
                } else if ("7".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "育肥110公斤出栏日龄");
                } else if ("8".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "育肥成活率(%)");
                } else if ("9".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "窝均断奶头数(头)");
                } else if ("10".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "全程成活率");
                } else if ("11".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "上产房背膘");
                } else if ("12".equals(monthPerformanceModuleModel.getAssessContent())) {
                    monthPerformanceModuleModel.set("assessContentName", "断奶背膘");
                }
            }
        }
        return getPageInfo(monthPerformanceModules);
    }

    @Override
    public void editPerformanceManage(MonthPerformanceModuleModel monthPerformanceModuleModel) throws Exception {
        if (monthPerformanceModuleModel.getAssessIndex() == null && monthPerformanceModuleModel.getMinAssessIndex() == null
                && monthPerformanceModuleModel.getMaxAssessIndex() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入指标！");
        }
        if (monthPerformanceModuleModel.getRowId() == null) {
            monthPerformanceModuleModel.setFarmId(getFarmId());
            monthPerformanceModuleModel.setCompanyId(getCompanyId());
            monthPerformanceModuleMapper.insert(monthPerformanceModuleModel);
        } else {
            monthPerformanceModuleMapper.update(monthPerformanceModuleModel);
            if (monthPerformanceModuleModel.getAssessIndex() == null) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" UPDATE cd_m_month_performance_module SET ASSESS_INDEX=NULL ");
                sqlCon.addCondition(monthPerformanceModuleModel.getRowId(), " WHERE ROW_ID=? ");
                setSql(monthPerformanceModuleMapper, sqlCon);
            }
            if (monthPerformanceModuleModel.getMinAssessIndex() == null) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" UPDATE cd_m_month_performance_module SET MIN_ASSESS_INDEX=NULL ");
                sqlCon.addCondition(monthPerformanceModuleModel.getRowId(), " WHERE ROW_ID=? ");
                setSql(monthPerformanceModuleMapper, sqlCon);
            }
            if (monthPerformanceModuleModel.getMaxAssessIndex() == null) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" UPDATE cd_m_month_performance_module SET MAX_ASSESS_INDEX=NULL ");
                sqlCon.addCondition(monthPerformanceModuleModel.getRowId(), " WHERE ROW_ID=? ");
                setSql(monthPerformanceModuleMapper, sqlCon);
            }
        }
    }

    @Override
    public void recoverPerformanceManage() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("UPDATE CD_M_MONTH_PERFORMANCE SET DELETED_FLAG = 1 WHERE DELETED_FLAG = 0 ");
        sqlCon.addCondition(getFarmId(), "AND FARM_ID = ?");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        monthPerformanceMapper.operSql(sqlMap);
    }

    /*************************************** yangy end 绩效管理 *****************************************/

    /*************************************** yangy start 绩效计算 *****************************************/
    @Override
    public List<Map<String, Object>> performanceCalculateToList(Map<String, Object> inputMap) throws Exception {
        List<MonthPerformanceModel> monthPerformanceModels = new ArrayList<MonthPerformanceModel>();
        List<MonthPerformanceModuleModel> MonthPerformanceModuleModels = null;
        String roleType = Maps.getString(inputMap, "roleType");
        if (roleType != null) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT * FROM cd_m_month_performance_module ");
            sqlCon.addCondition(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), " WHERE " + "" + "?" + ""
                    + " BETWEEN START_DATE AND END_DATE AND IS_START=1 AND DELETED_FLAG=0 AND STATUS=1 ");
            sqlCon.addCondition(Maps.getLongClass(inputMap, "farmId") == null ? getFarmId() : Maps.getLongClass(inputMap, "farmId"),
                    " AND FARM_ID=? ");
            // 技术员绩效
            if (EventConstants.PERFORMANCE_ROLE_TECH.equals(roleType)) {
                sqlCon.addMainSql("AND ASSESS_REGION <> 5");
                // 场长绩效
            } else if (EventConstants.PERFORMANCE_ROLE_FARM.equals(roleType)) {
                sqlCon.addMainSql("AND ASSESS_REGION = 5");
            } else {
            }
            // 产房，保育，育肥，大丰育肥场标准系数
            double cf_livestock = 0D;
            double by_livestock = 0D;
            double yf_livestock = 0D;
            double af_yf_livestock = 0D;
            // 母猪存栏，产房平均存栏，保育平均存栏，育肥平均存栏
            double avg_mz_livestock = 0D;
            double avg_cf_livestock = 0D;
            double avg_by_livestock = 0D;
            double avg_yf_livestock = 0D;
            // 母猪实际存栏，产房实际存栏，保育实际存栏，育肥实际存栏
            double actual_mz_livestock = 0D;
            int dateNum = 0;
            MonthPerformanceModuleModels = setSql(monthPerformanceModuleMapper, sqlCon);
            if (MonthPerformanceModuleModels != null && !MonthPerformanceModuleModels.isEmpty()) {
                Map<String, Object> parmMap = new HashMap<String, Object>();
                /*********************************** 2017-8-18 绩效需求修改 *****************************************/
                SqlCon indexSqlCon = new SqlCon();
                indexSqlCon.addMainSql(" SELECT IF(T1.STANDARD_VALUE IS NULL,T0.STANDARD_VALUE,T1.STANDARD_VALUE) STANDARD_VALUE, ");
                indexSqlCon.addMainSql(" IF(T1.BUSINESS_CODE IS NULL,T0.BUSINESS_CODE,T1.BUSINESS_CODE) BUSINESS_CODE ");
                indexSqlCon.addMainSql(" FROM pp_l_indicator T0 ");
                indexSqlCon.addMainSql(" LEFT JOIN pp_l_indicator T1 ON T0.BUSINESS_CODE=T1.BUSINESS_CODE AND T1.DELETED_FLAG='0' ");
                indexSqlCon.addCondition(Maps.getLongClass(inputMap, "farmId") == null ? getFarmId() : Maps.getLongClass(inputMap, "farmId"),
                        " AND T1.FARM_ID=? ");
                indexSqlCon.addMainSql(" WHERE T0.DELETED_FLAG='0' AND T0.FARM_ID=3 ");
                indexSqlCon.addCondition("Z069,Z070,Z071,Z072", " AND T0.BUSINESS_CODE IN ? ", false, true);
                Map<String, String> map = new HashMap<String, String>();
                map.put("sql", indexSqlCon.getCondition());
                List<Map<String, Object>> indicatorlist = paramMapper.getObjectInfos(map);
                if (indicatorlist != null && !indicatorlist.isEmpty()) {
                    for (Map<String, Object> indicatorMap : indicatorlist) {
                        if (EventConstants.INDICATOR_BUSINESS_CODE_CFCLXS.equals(Maps.getString(indicatorMap, "BUSINESS_CODE"))) {
                            cf_livestock = Maps.getDouble(indicatorMap, "STANDARD_VALUE");
                        } else if (EventConstants.INDICATOR_BUSINESS_CODE_BYCLXS.equals(Maps.getString(indicatorMap, "BUSINESS_CODE"))) {
                            by_livestock = Maps.getDouble(indicatorMap, "STANDARD_VALUE");
                        } else if (EventConstants.INDICATOR_BUSINESS_CODE_YFCLXS.equals(Maps.getString(indicatorMap, "BUSINESS_CODE"))) {
                            yf_livestock = Maps.getDouble(indicatorMap, "STANDARD_VALUE");
                        } else if (EventConstants.INDICATOR_BUSINESS_CODE_DFYFXS.equals(Maps.getString(indicatorMap, "BUSINESS_CODE"))) {
                            af_yf_livestock = Maps.getDouble(indicatorMap, "STANDARD_VALUE");
                        }
                    }
                }
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(Maps.getLongClass(inputMap, "farmId") == null
                        ? getFarmId() : Maps.getLongClass(inputMap, "farmId")));
                String companyMark = Maps.getString(companyInfoMap, "COMPANY_MARK");
                String companyCode = Maps.getString(companyInfoMap, "COMPANY_CODE");
                String companyType = Maps.getString(companyInfoMap, "COMPANY_TYPE");
                // 由于7月份生产日报表中数据存在误差，8月份绩效还是从存储过程取数据
                String startCalculateDate_ = TimeUtil.getLastMoth25(new Date(), TimeUtil.DATE_FORMAT);
                String endCalculateDate_ = TimeUtil.getCurrentMoth26(new Date(), TimeUtil.DATE_FORMAT);
                Map<String, Object> liveStockMap = new HashMap<String, Object>();
                liveStockMap.put("startDate", startCalculateDate_);
                liveStockMap.put("endDate", endCalculateDate_);
                // 大丰保育场取母猪存栏取繁殖场母猪存栏
                if ("C1034".equals(companyCode)) {
                    SqlCon getFZFarmSqlCon = new SqlCon();
                    getFZFarmSqlCon.addMainSql(
                            " SELECT ROW_ID rowId FROM hr_m_company WHERE DELETED_FLAG='0' AND (COMPANY_NAME LIKE '%繁殖%' OR COMPANY_NAME LIKE '%保育%' OR COMPANY_NAME LIKE '%后备%') AND COMPANY_MARK LIKE '1,2,3,5,%' ");
                    Map<String, String> fXfarmIdMap = new HashMap<String, String>();
                    fXfarmIdMap.put("sql", getFZFarmSqlCon.getCondition());
                    List<Map<String, Object>> fZFarmList = paramMapper.getObjectInfos(fXfarmIdMap);
                    StringBuffer sb = new StringBuffer();
                    if (fZFarmList != null && !fZFarmList.isEmpty()) {
                        for (int i = 0; i < fZFarmList.size(); i++) {
                            if (i != fZFarmList.size() - 1) {
                                sb.append(Maps.getLong(fZFarmList.get(i), "rowId"));
                                sb.append(",");
                            } else {
                                sb.append(Maps.getLong(fZFarmList.get(i), "rowId"));
                            }
                        }
                    }
                    liveStockMap.put("farmId", sb.toString());
                } else {
                    liveStockMap.put("farmId", Maps.getLongClass(inputMap, "farmId") == null ? getFarmId() : Maps.getLongClass(inputMap, "farmId"));
                }
                double _mz_livestock = 0D;
                double _cf_livestock = 0D;
                double _by_livestock = 0D;
                double _yf_livestock = 0D;
                List<Map<String, Object>> liveStockList = monthPerformanceMapper.searchLivestock(liveStockMap);
                if (liveStockList != null && !liveStockList.isEmpty()) {
                    for (Map<String, Object> map_ : liveStockList) {
                        _cf_livestock = _cf_livestock + Maps.getInt(map_, "brPigQty");
                        _by_livestock = _by_livestock + Maps.getInt(map_, "byPigQty");
                        _yf_livestock = _yf_livestock + Maps.getInt(map_, "yfPigQty");
                        _mz_livestock = _mz_livestock + Maps.getInt(map_, "scmPigQty");
                    }
                }
                dateNum = TimeUtil.getday(TimeUtil.parseDate(endCalculateDate_), TimeUtil.parseDate(startCalculateDate_)) + 1;
                avg_cf_livestock = _cf_livestock / dateNum;
                avg_by_livestock = _by_livestock / dateNum;
                avg_yf_livestock = _yf_livestock / dateNum;
                avg_mz_livestock = _mz_livestock / dateNum;

                /*********************************** 2017-8-18 绩效需求修改 *****************************************/
                for (MonthPerformanceModuleModel MonthPerformanceModuleModel : MonthPerformanceModuleModels) {
                    String startCalculateDate = null;
                    String endCalculateDate = null;
                    String supCompanyMark = null;
                    MonthPerformanceModel monthPerformanceModel = null;
                    if (EventConstants.PERFORMANCE_DATE_AREA_TYPE_A == (MonthPerformanceModuleModel.getPerformanceDateArea())) {
                        startCalculateDate = TimeUtil.getLastMoth25(new Date(), TimeUtil.DATE_FORMAT);
                        endCalculateDate = TimeUtil.getCurrentMoth26(new Date(), TimeUtil.DATE_FORMAT);
                    } else if (EventConstants.PERFORMANCE_DATE_AREA_TYPE_B == (MonthPerformanceModuleModel.getPerformanceDateArea())) {
                        startCalculateDate = TimeUtil.getFirstDateOfMonth(TimeUtil.format(new Date()));
                        endCalculateDate = TimeUtil.getLastDateOfMonth(TimeUtil.format(new Date()));
                    }
                    // MonthPerformanceModel mnthPerformanceModel_ = monthPerformanceMapper.searchMinPorkBirthDate(startCalculateDate,
                    // endCalculateDate,
                    // companyMark, PigConstants.CHANGE_HOUSE_TYPE_CHI, PigConstants.HOUSE_CLASS_DELIVERY);
                    parmMap.put("startDate", startCalculateDate);
                    parmMap.put("endDate", endCalculateDate);
                    parmMap.put("companyMark", companyMark);
                    // 产活率（头）
                    if (EventConstants.ASSESS_CONTENT_CHS.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionBySurvive(startCalculateDate, endCalculateDate, companyMark);
                        // 分娩率（%）
                    } else if (EventConstants.ASSESS_CONTENT_FML.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByDeliver(startCalculateDate, endCalculateDate, companyMark);
                        // 膘情(13-18)(非后备)
                    } else if (EventConstants.ASSESS_CONTENT_BQ.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByFatness(startCalculateDate, endCalculateDate, companyMark);
                    }
                    // 窝均断奶头数(头)
                    /* } else if (EventConstants.ASSESS_CONTENT_WJDNT.equals(MonthPerformanceModuleModel.getAssessContent())) {
                     * monthPerformanceModel = monthPerformanceMapper.searchProductionByWeanNum(startCalculateDate, endCalculateDate, companyMark,
                     * mnthPerformanceModel_.getNotes());
                     * weanNum = monthPerformanceModel.getSystemIndex().doubleValue();
                     * // 窝均断奶窝重（KG）
                     * } else if (EventConstants.ASSESS_CONTENT_WJDNZ.equals(MonthPerformanceModuleModel.getAssessContent())) {
                     * if (weanNum == 0.0) {
                     * MonthPerformanceModel monthPerformanceModelWean = monthPerformanceMapper.searchProductionByWeanNum(startCalculateDate,
                     * endCalculateDate, companyMark, mnthPerformanceModel_.getNotes());
                     * monthPerformanceModel = monthPerformanceMapper.searchProductionByWean(startCalculateDate, endCalculateDate, companyMark,
                     * getFarmId(), monthPerformanceModelWean.getSystemIndex());
                     * } else {
                     * monthPerformanceModel = monthPerformanceMapper.searchProductionByWean(startCalculateDate, endCalculateDate, companyMark,
                     * getFarmId(), weanNum);
                     * }
                     * // 保育成活率（%）
                     * // 窝均断奶头数(头)窝均断奶窝重（KG）
                     * } */ else if (EventConstants.ASSESS_CONTENT_WJDNT.equals(MonthPerformanceModuleModel.getAssessContent())
                            || EventConstants.ASSESS_CONTENT_WJDNZ.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        Map<String, Object> wjdnsAndWjdnwzMap = monthPerformanceMapper.searchProductionByWjdnsAndWjdnwz(parmMap);
                        if (wjdnsAndWjdnwzMap != null) {
                            monthPerformanceModel = new MonthPerformanceModel();
                            if (EventConstants.ASSESS_CONTENT_WJDNT.equals(MonthPerformanceModuleModel.getAssessContent())) {
                                monthPerformanceModel.setSystemIndex(Maps.getDoubleClass(wjdnsAndWjdnwzMap, "AVG_WEAN_NUM"));
                                monthPerformanceModel.setNotes(Maps.getString(wjdnsAndWjdnwzMap, "DN_NOTES"));
                                monthPerformanceModel.setSystemNumrator(Maps.getDoubleClass(wjdnsAndWjdnwzMap, "WEAN_TOTAL_SIZE"));
                            } else if (EventConstants.ASSESS_CONTENT_WJDNZ.equals(MonthPerformanceModuleModel.getAssessContent())) {
                                monthPerformanceModel.setSystemIndex(Maps.getDoubleClass(wjdnsAndWjdnwzMap, "WEAN_LITTER_WEIGHT"));
                                monthPerformanceModel.setNotes(Maps.getString(wjdnsAndWjdnwzMap, "DNW_NOTES"));
                            }
                        }
                        // 保育成活率（%）
                    } else if (EventConstants.ASSESS_CONTENT_BYCHL.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        if ("C1034".equals(companyCode)) {
                            Map<String, String> supCompanyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(companyInfoMap,
                                    "SUP_COMPANY_ID"));
                            supCompanyMark = Maps.getString(supCompanyInfoMap, "COMPANY_MARK");
                        } else {
                            supCompanyMark = companyMark;
                        }
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByChildSurvive(startCalculateDate, endCalculateDate,
                                supCompanyMark, companyMark);
                        // 保育7030（KG）
                    } else if (EventConstants.ASSESS_CONTENT_BL7030.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByChild(startCalculateDate, endCalculateDate, companyMark, Maps
                                .getLongClass(inputMap, "farmId") == null ? getFarmId() : Maps.getLongClass(inputMap, "farmId"));
                        // 育肥110公斤出栏日龄
                    } else if (EventConstants.ASSESS_CONTENT_YF110CLRL.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByFatField(startCalculateDate, endCalculateDate, companyMark,
                                Maps.getLongClass(inputMap, "farmId") == null ? getFarmId() : Maps.getLongClass(inputMap, "farmId"));
                        // 育肥成活率(%)
                    } else if (EventConstants.ASSESS_CONTENT_YFCHL.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        if ("C1036".equals(companyCode)) {
                            Map<String, String> supCompanyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(companyInfoMap,
                                    "SUP_COMPANY_ID"));
                            supCompanyMark = Maps.getString(supCompanyInfoMap, "COMPANY_MARK");
                        } else {
                            supCompanyMark = companyMark;
                        }
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByFatSurvive(startCalculateDate, endCalculateDate,
                                supCompanyMark, companyMark);
                        // 全程死亡率
                    } else if (EventConstants.ASSESS_CONTENT_QCSW.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByWholeDie(startCalculateDate, endCalculateDate, companyMark);
                        // 上产房背膘
                    } else if (EventConstants.ASSESS_CONTENT_SCFBB.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        parmMap.put("companyType", companyType);
                        monthPerformanceModel = monthPerformanceMapper.searchProductionBySCFFatness(parmMap);
                        // 断奶背膘
                    } else if (EventConstants.ASSESS_CONTENT_DNBB.equals(MonthPerformanceModuleModel.getAssessContent())) {
                        monthPerformanceModel = monthPerformanceMapper.searchProductionByDNFatness(parmMap);
                    }
                    if (monthPerformanceModel != null) {
                        double systemIndex = monthPerformanceModel.getSystemIndex() == null ? 0 : monthPerformanceModel.getSystemIndex();
                        double minAssessIndex = MonthPerformanceModuleModel.getMinAssessIndex() == null ? EventConstants.NULL_TYPE_DOUBLE
                                : MonthPerformanceModuleModel.getMinAssessIndex();
                        double maxAssessIndex = MonthPerformanceModuleModel.getMaxAssessIndex() == null ? EventConstants.NULL_TYPE_DOUBLE
                                : MonthPerformanceModuleModel.getMaxAssessIndex();
                        double assessIndex = MonthPerformanceModuleModel.getAssessIndex() == null ? EventConstants.NULL_TYPE_DOUBLE
                                : MonthPerformanceModuleModel.getAssessIndex();
                        double reward = MonthPerformanceModuleModel.getReward();
                        double increasedAmount = MonthPerformanceModuleModel.getIncreasedAmount();
                        double offset = MonthPerformanceModuleModel.getOffset();
                        BigDecimal bigDecimal = new BigDecimal(Double.toString(systemIndex));
                        // 系统指标
                        BigDecimal systemIndexBigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
                        BigDecimal bigDecimal_ = new BigDecimal(Double.toString(assessIndex));
                        // 考核指标
                        BigDecimal assessIndexBigDecimal = bigDecimal_.setScale(2, BigDecimal.ROUND_HALF_UP);
                        monthPerformanceModel.setSystemIndex(systemIndexBigDecimal.doubleValue());
                        monthPerformanceModel.setAssessIndex(assessIndexBigDecimal.doubleValue());
                        if (assessIndex != EventConstants.NULL_TYPE_DOUBLE && minAssessIndex == EventConstants.NULL_TYPE_DOUBLE
                                && maxAssessIndex == EventConstants.NULL_TYPE_DOUBLE) {
                            if (offset > 0) {
                                if (systemIndex >= assessIndex) {
                                    double systemAmount = (systemIndexBigDecimal.subtract(assessIndexBigDecimal)).doubleValue() / offset
                                            * increasedAmount + reward;
                                    monthPerformanceModel.setSystemAmount(Double.valueOf(Math.round(systemAmount)));
                                } else {
                                    monthPerformanceModel.setSystemAmount(0.0);
                                }
                            } else {
                                if (systemIndex <= assessIndex) {
                                    double systemAmount = (systemIndexBigDecimal.subtract(assessIndexBigDecimal)).doubleValue() / offset
                                            * increasedAmount + reward;
                                    monthPerformanceModel.setSystemAmount(Double.valueOf(Math.round(systemAmount)));
                                } else {
                                    monthPerformanceModel.setSystemAmount(0.0);
                                }
                            }

                        }
                        if (assessIndex == EventConstants.NULL_TYPE_DOUBLE && minAssessIndex != EventConstants.NULL_TYPE_DOUBLE
                                && maxAssessIndex != EventConstants.NULL_TYPE_DOUBLE) {
                            if (systemIndex < minAssessIndex) {
                                monthPerformanceModel.setSystemAmount(0.0);
                            } else if (systemIndex >= minAssessIndex && systemIndex < maxAssessIndex) {
                                monthPerformanceModel.setSystemAmount(reward);
                            } else if (systemIndex >= maxAssessIndex) {
                                monthPerformanceModel.setSystemAmount(Double.valueOf(Math.round(reward + increasedAmount)));
                            }
                        }
                        if (assessIndex != EventConstants.NULL_TYPE_DOUBLE && minAssessIndex != EventConstants.NULL_TYPE_DOUBLE
                                && maxAssessIndex != EventConstants.NULL_TYPE_DOUBLE) {
                            if (systemIndex > maxAssessIndex || systemIndex == 0) {
                                monthPerformanceModel.setSystemAmount(0.0);
                            } else if (systemIndex > assessIndex && systemIndex <= maxAssessIndex) {
                                monthPerformanceModel.setSystemAmount(reward);
                            } else if (systemIndex > minAssessIndex && systemIndex <= assessIndex) {
                                monthPerformanceModel.setSystemAmount(Double.valueOf(Math.round(reward + increasedAmount)));
                            } else if (systemIndex <= minAssessIndex && systemIndex > 0) {
                                monthPerformanceModel.setSystemAmount(Double.valueOf(Math.round(reward + increasedAmount * 2)));
                            } else {
                            }
                        }
                        monthPerformanceModel.setUltimaIndex(monthPerformanceModel.getSystemIndex());
                        monthPerformanceModel.setUltimaAmount(monthPerformanceModel.getSystemAmount());
                        monthPerformanceModel.set("differenceAmount", 0);

                        if ("1".equals(MonthPerformanceModuleModel.getAssessRegion())) {
                            monthPerformanceModel.set("assessRegionName", "配种");
                        } else if ("2".equals(MonthPerformanceModuleModel.getAssessRegion())) {
                            monthPerformanceModel.set("assessRegionName", "产房");
                        } else if ("3".equals(MonthPerformanceModuleModel.getAssessRegion())) {
                            monthPerformanceModel.set("assessRegionName", "保育");
                        } else if ("4".equals(MonthPerformanceModuleModel.getAssessRegion())) {
                            monthPerformanceModel.set("assessRegionName", "育肥");
                        } else if ("5".equals(MonthPerformanceModuleModel.getAssessRegion())) {
                            monthPerformanceModel.set("assessRegionName", "场长");
                        } else {
                        }

                        if ("1".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "产活数(头)");
                        } else if ("2".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "分娩率(%)");
                        } else if ("3".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "膘情(13-18)(非后备)");
                        } else if ("4".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "窝均断奶重(KG)");
                        } else if ("5".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "保育成活率(%)");
                        } else if ("6".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "保育7030(KG)");
                        } else if ("7".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "育肥110公斤出栏日龄");
                        } else if ("8".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "育肥成活率(%)");
                        } else if ("9".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "窝均断奶头数(头)");
                        } else if ("10".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "全程成活率");
                        } else if ("11".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "上产房背膘");
                        } else if ("12".equals(MonthPerformanceModuleModel.getAssessContent())) {
                            monthPerformanceModel.set("assessContentName", "断奶背膘");
                        }
                        monthPerformanceModel.setAssessRegion(MonthPerformanceModuleModel.getAssessRegion());
                        monthPerformanceModel.setAssessContent(MonthPerformanceModuleModel.getAssessContent());
                        monthPerformanceModel.set("minAssessIndex", MonthPerformanceModuleModel.getMinAssessIndex());
                        monthPerformanceModel.set("assessIndex", MonthPerformanceModuleModel.getAssessIndex());
                        monthPerformanceModel.set("maxAssessIndex", MonthPerformanceModuleModel.getMaxAssessIndex());
                        monthPerformanceModel.set("reward", MonthPerformanceModuleModel.getReward());
                        monthPerformanceModel.set("offset", MonthPerformanceModuleModel.getOffset());
                        monthPerformanceModel.set("increasedAmount", MonthPerformanceModuleModel.getIncreasedAmount());
                        monthPerformanceModel.setFarmId(Maps.getLongClass(inputMap, "farmId") == null ? getFarmId()
                                : Maps.getLongClass(inputMap, "farmId"));
                        /*********************************************** 2017-8-18 绩效需求修改 ***************************************************/
                        if (!"1".equals(MonthPerformanceModuleModel.getAssessRegion())) {
                            /* SqlCon reportDailySqlCon = new SqlCon();
                             * reportDailySqlCon.addMainSql(
                             * " SELECT SUM(T0.LIVES_BRZ) LIVES_BRZ,SUM(T0.LIVES_BYZ) LIVES_BYZ,SUM(T0.LIVES_YFZ) LIVES_YFZ,SUM(T0.LIVES_SCMZ) LIVES_SCMZ FROM sys_report_production_daily T0 "
                             * );
                             * // 大丰保育场取母猪存栏取繁殖场母猪存栏
                             * if ("C1034".equals(companyCode)) {
                             * reportDailySqlCon.addMainSql(
                             * " INNER JOIN hr_m_company T1 ON T0.FARM_ID=T1.ROW_ID AND (T1.COMPANY_NAME LIKE '%繁殖%' OR T1.COMPANY_NAME LIKE '%保育%') AND T1.COMPANY_MARK LIKE '1,2,3,5,%' AND T1.DELETED_FLAG='0' "
                             * );
                             * reportDailySqlCon.addMainSql(" WHERE T0.DELETED_FLAG='0' ");
                             * } else {
                             * reportDailySqlCon.addMainSql(" WHERE T0.DELETED_FLAG='0' ");
                             * reportDailySqlCon.addCondition(getFarmId(), " AND T0.FARM_ID=? ");
                             * }
                             * 
                             * reportDailySqlCon.addCondition(startCalculateDate, " AND DATE(T0.REPORT_DATE) BETWEEN ? ");
                             * reportDailySqlCon.addCondition(endCalculateDate, " AND ? ");
                             * Map<String, String> livestockMap = new HashMap<String, String>();
                             * livestockMap.put("sql", reportDailySqlCon.getCondition());
                             * List<Map<String, Object>> livestockList = paramMapper.getObjectInfos(livestockMap);
                             * dateNum = TimeUtil.getday(TimeUtil.parseDate(endCalculateDate), TimeUtil.parseDate(startCalculateDate));
                             * if (livestockList != null && !livestockList.isEmpty()) {
                             * Map<String, Object> livestock = livestockList.get(0);
                             * avg_cf_livestock = Maps.getDouble(livestock, "LIVES_BRZ") / dateNum;
                             * avg_by_livestock = Maps.getDouble(livestock, "LIVES_BYZ") / dateNum;
                             * avg_yf_livestock = Maps.getDouble(livestock, "LIVES_YFZ") / dateNum;
                             * avg_mz_livestock = Maps.getDouble(livestock, "LIVES_SCMZ") / dateNum;
                             * } */
                            // 重新计算金额
                            double systemAmount = 0D;
                            double acutalAvgHand = 0D;
                            // 产房或者大丰厂长繁殖场
                            if ("2".equals(MonthPerformanceModuleModel.getAssessRegion()) || ("5".equals(MonthPerformanceModuleModel
                                    .getAssessRegion()) && (CompanyConstants.ACCOUNT_COMPANY_DFFZ1.equals(companyCode)
                                            || CompanyConstants.ACCOUNT_COMPANY_DFFZ2.equals(companyCode) || CompanyConstants.ACCOUNT_COMPANY_DFFZ3
                                                    .equals(companyCode) || CompanyConstants.ACCOUNT_COMPANY_DFFZ4.equals(companyCode)))) {
                                actual_mz_livestock = avg_mz_livestock * cf_livestock;

                                if (avg_cf_livestock < actual_mz_livestock) {
                                    systemAmount = monthPerformanceModel.getSystemAmount() * (avg_cf_livestock / actual_mz_livestock);
                                }
                                acutalAvgHand = avg_cf_livestock;
                                monthPerformanceModel.setHandModulus(cf_livestock);
                                // 保育或者大丰厂长保育场
                            } else if ("3".equals(MonthPerformanceModuleModel.getAssessRegion()) || ("5".equals(MonthPerformanceModuleModel
                                    .getAssessRegion()) && CompanyConstants.ACCOUNT_COMPANY_DFBY.equals(companyCode))) {
                                actual_mz_livestock = avg_mz_livestock * by_livestock;
                                if (avg_by_livestock < actual_mz_livestock) {
                                    systemAmount = monthPerformanceModel.getSystemAmount() * (avg_by_livestock / actual_mz_livestock);
                                }
                                acutalAvgHand = avg_by_livestock;
                                monthPerformanceModel.setHandModulus(by_livestock);
                                // 育肥或者大丰厂长育肥场
                            } else if ("4".equals(MonthPerformanceModuleModel.getAssessRegion()) || ("5".equals(MonthPerformanceModuleModel
                                    .getAssessRegion()) && CompanyConstants.ACCOUNT_COMPANY_DFYF.equals(companyCode))) {
                                // 大丰育肥场系数标准存栏固定
                                if ("C1036".equals(companyCode)) {
                                    if (avg_yf_livestock < af_yf_livestock) {
                                        systemAmount = monthPerformanceModel.getSystemAmount() * (avg_yf_livestock / af_yf_livestock);
                                    }
                                    actual_mz_livestock = af_yf_livestock;
                                } else {
                                    actual_mz_livestock = avg_mz_livestock * yf_livestock;
                                    if (avg_yf_livestock < actual_mz_livestock) {
                                        systemAmount = monthPerformanceModel.getSystemAmount() * (avg_yf_livestock / actual_mz_livestock);
                                    }
                                }
                                acutalAvgHand = avg_yf_livestock;
                                monthPerformanceModel.setHandModulus(yf_livestock);
                            }
                            if (systemAmount > 0.0) {
                                BigDecimal systemAmount_ = new BigDecimal(systemAmount);
                                systemAmount = systemAmount_.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                                monthPerformanceModel.setUltimaAmount(systemAmount);

                                double differenceAmount = monthPerformanceModel.getSystemAmount() - systemAmount;
                                BigDecimal differenceAmount_ = new BigDecimal(differenceAmount);
                                differenceAmount = differenceAmount_.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                                monthPerformanceModel.set("differenceAmount", differenceAmount);
                            }

                            BigDecimal acutalAvgHand_ = new BigDecimal(acutalAvgHand);
                            acutalAvgHand = acutalAvgHand_.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            monthPerformanceModel.setAcutalAvgHand(acutalAvgHand);

                            BigDecimal actual_mz_livestock_ = new BigDecimal(actual_mz_livestock);
                            actual_mz_livestock = actual_mz_livestock_.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            monthPerformanceModel.setModulusStandardHand(actual_mz_livestock);

                        }
                        /*********************************************** 2017-8-18 绩效需求修改 ***************************************************/
                        monthPerformanceModels.add(monthPerformanceModel);
                    }
                }
            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入技术员");
        }
        return getMapList(monthPerformanceModels);
    }

    @Override
    public void editPerformanceCalculate(List<MonthPerformanceModel> monthPerformanceModels, String roleType) throws Exception {
        Long farmId = null;
        if (monthPerformanceModels != null && !monthPerformanceModels.isEmpty()) {
            farmId = monthPerformanceModels.get(0).getFarmId();
            int month = TimeUtil.getMM();
            int year = TimeUtil.getYYYY();
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT * FROM cd_m_month_performance WHERE DELETED_FLAG='0' AND STATUS='1' ");
            sqlCon.addCondition(farmId == null ? getFarmId() : farmId, " AND FARM_ID=? ");
            sqlCon.addCondition(month, " AND MONTH=? ");
            sqlCon.addCondition(year, " AND YEAR=? ");
            if (EventConstants.PERFORMANCE_ROLE_FARM.equals(roleType)) {
                sqlCon.addMainSql(" AND ASSESS_REGION=5 ");
            } else if (EventConstants.PERFORMANCE_ROLE_TECH.equals(roleType)) {
                sqlCon.addMainSql(" AND ASSESS_REGION<>5 ");
            } else {
            }
            List<MonthPerformanceModel> monthPerformanceModels_ = setSql(monthPerformanceMapper, sqlCon);
            for (MonthPerformanceModel monthPerformanceModel : monthPerformanceModels) {
                monthPerformanceModel.setFarmId(farmId == null ? getFarmId() : farmId);
                monthPerformanceModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                monthPerformanceModel.setMonth(Long.valueOf(month));
                monthPerformanceModel.setYear(Long.valueOf(year));
            }

            if (monthPerformanceModels_ != null && !monthPerformanceModels_.isEmpty()) {
                for (MonthPerformanceModel monthPerformanceModel_ : monthPerformanceModels_) {
                    monthPerformanceModel_.setDeletedFlag("1");
                    SqlCon sqlConDelete = new SqlCon();
                    sqlConDelete.addMainSql(" UPDATE cd_m_month_performance SET DELETED_FLAG=1 ");
                    sqlConDelete.addCondition(monthPerformanceModel_.getRowId(), " WHERE ROW_ID=? ");
                    setSql(monthPerformanceMapper, sqlConDelete);
                }
            }
            monthPerformanceMapper.inserts(monthPerformanceModels);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "没有修改数据");
        }
    }

    /*************************************** yangy end 绩效计算 *****************************************/

    @Override
    public Boolean searchUserType() throws Exception {
        Boolean sysAdminFlag = false;
        UserDetail userDetail = getUserDetail();
        if (("10000").equals(userDetail.getCompanyCode()) && ("sysadmin").equals(userDetail.getUsername())) {
            sysAdminFlag = true;

        } else if (("admin").equals(userDetail.getUsername())) {
            sysAdminFlag = false;
        } else {
            sysAdminFlag = false;
        }
        return sysAdminFlag;
    }

    @Override
    public List<PigClassModel> searchPigClassToListByEvent(String eventName, String pigType) throws Exception {
        SqlCon sqlCon = new SqlCon();
        if (EventConstants.PIG_MOVE_IN.equals(eventName)) {
            if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
                sqlCon.addMainSql(" AND ROW_ID IN (1,2) ");
            } else if (PigConstants.PIG_TYPE_SOW.equals(pigType)) {
                sqlCon.addMainSql(" AND ROW_ID IN (3,11) ");
            } else if (PigConstants.PIG_TYPE_PORK.equals(pigType)) {
                sqlCon.addMainSql(" AND ROW_ID IN (14,15,16) ");
            } else {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪只类型不正确");
            }
        } else if (EventConstants.CHANGE_EAR_BAND.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID BETWEEN 1 AND 18 ");
        } else if (EventConstants.BACKFAT.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID BETWEEN 1 AND 11 ");
        } else if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 1 ");
        } else if (EventConstants.SEMEN.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 2 ");
        } else if (EventConstants.SEMEN_CHECK.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.PREPUBERTAL_CHECK.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 3 ");
        } else if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID IN (9,10) ");
        } else if (EventConstants.BREED_PIG_CHANGE_HOUSE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID <> 10 AND ROW_ID BETWEEN 1 AND 11 ");
        } else if (EventConstants.BREED.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID IN (4,5,6,7,8,11) ");
        } else if (EventConstants.PREGNANCY_CHECK.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 9 ");
        } else if (EventConstants.MISSCARRY.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 9 ");
        } else if (EventConstants.DELIVERY.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 9 ");
        } else if (EventConstants.FOSTER.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 10 ");
        } else if (EventConstants.WEAN.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 10 ");
        } else if (EventConstants.BREED_PIG_OBSOLETE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID NOT IN(10,23,24,25) ");
        } else if (EventConstants.BREED_PIG_DIE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID NOT IN(23,25) ");
        } else if (EventConstants.CHILD_PIG_DIE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 10 ");
        } else if (EventConstants.TO_CHILD_CARE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID IN (14,15) ");
        } else if (EventConstants.TO_CHILD_FATTEN.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID IN (15,16) ");
        } else if (EventConstants.GOOD_PIG_DIE.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.GOOD_PIG_SELL.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID IN (1,2,3,4,6,7,8,9,11,12,13,15,16) ");
        } else if (EventConstants.BILL_CANCEL.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.PIG_EVENT_CANCEL.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.EACH_RECORD_CANCEL.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.CHANGE_HOUSE.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.SEED_PIG.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 10 ");
        } else if (EventConstants.SELECT_BREED_PIG.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID IN (12,13,15,16) ");
        } else if (EventConstants.PORK_PIG_CHECK.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.SALE_SEMEN.equals(eventName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        } else if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventName)) {
            sqlCon.addMainSql(" AND ROW_ID = 10 ");
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪只状态");
        }
        return getList(pigClassMapper, sqlCon);
    }

    @Override
    public BasePageInfo searchSurvivalTargetList() throws Exception {
        setToPage();
        return getPageInfo(survivalTargetMapper.searchToList(getFarmId()));
    }

    @Override
    public void editSurvaivalTargetList(SurvivalTargetModel survivalTargetModel) throws Exception {
        if (survivalTargetModel != null) {
            if (survivalTargetModel.getStartDate() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入开始日期");
            }
            if (survivalTargetModel.getEndDate() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入结束日期");
            }
            if (survivalTargetModel.getTargetNumber() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入成活率目标数值");
            }
            if (TimeUtil.compareDate(survivalTargetModel.getStartDate(), survivalTargetModel.getEndDate(), Calendar.DATE) > 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "开始日期不能大于结束日期");
            }
            if (!Long.valueOf("0").equals(survivalTargetModel.getRowId())) {
                survivalTargetMapper.update(survivalTargetModel);
            } else {
                survivalTargetModel.setFarmId(getFarmId());
                survivalTargetModel.setCompanyId(getCompanyId());
                survivalTargetModel.setCreateId(getEmployeeId());
                survivalTargetModel.setCreateDate(new Date());
                survivalTargetMapper.insert(survivalTargetModel);
            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "没有数据保存");
        }

    }

    @Override
    public void deleteSurvaivalTarget(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            survivalTargetMapper.deletes(ids);
        }

    }

    @Override
    public void deletePerformanceManage(long[] ids) {
        if (ids != null && ids.length > 0) {
            monthPerformanceModuleMapper.deletes(ids);
        }
    }

    @Override
    public List<MonthPerformanceModel> searchPerformanceCalculateToList(Map<String, Object> map) throws Exception {
        String searchDate = Maps.getString(map, "searchDate");
        String roleType = Maps.getString(map, "searchRoleType");
        List<MonthPerformanceModel> monthPerformanceModels = null;
        if (searchDate != null) {
            String year = searchDate.substring(0, 4);
            String month = searchDate.substring(5, 7);
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
            sqlCon.addCondition(year, " AND YEAR=? ");
            sqlCon.addCondition(month, " AND MONTH=? ");
            if (EventConstants.PERFORMANCE_ROLE_FARM.equals(roleType)) {
                sqlCon.addMainSql(" AND ASSESS_REGION=5 ");
            } else if (EventConstants.PERFORMANCE_ROLE_TECH.equals(roleType)) {
                sqlCon.addMainSql(" AND ASSESS_REGION<>5 ");
            } else {
            }
            monthPerformanceModels = getList(monthPerformanceMapper, sqlCon);
            if (monthPerformanceModels != null && !monthPerformanceModels.isEmpty()) {
                for (MonthPerformanceModel monthPerformanceModel : monthPerformanceModels) {
                    monthPerformanceModel.set("differenceAmount", monthPerformanceModel.getUltimaAmount() - monthPerformanceModel.getSystemAmount());
                    if ("1".equals(monthPerformanceModel.getAssessRegion())) {
                        monthPerformanceModel.set("assessRegionName", "配种");
                    } else if ("2".equals(monthPerformanceModel.getAssessRegion())) {
                        monthPerformanceModel.set("assessRegionName", "产房");
                    } else if ("3".equals(monthPerformanceModel.getAssessRegion())) {
                        monthPerformanceModel.set("assessRegionName", "保育");
                    } else if ("4".equals(monthPerformanceModel.getAssessRegion())) {
                        monthPerformanceModel.set("assessRegionName", "育肥");
                    } else if ("5".equals(monthPerformanceModel.getAssessRegion())) {
                        monthPerformanceModel.set("assessRegionName", "场长");
                    } else {
                    }

                    if ("1".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "产活数(头)");
                    } else if ("2".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "分娩率(%)");
                    } else if ("3".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "膘情(13-18)(非后备)");
                    } else if ("4".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "窝均断奶重(KG)");
                    } else if ("5".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "保育成活率(%)");
                    } else if ("6".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "保育7030(KG)");
                    } else if ("7".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "育肥110公斤出栏日龄");
                    } else if ("8".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "育肥成活率(%)");
                    } else if ("9".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "窝均断奶头数(头)");
                    } else if ("10".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "全程成活率");
                    } else if ("11".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "上产房背膘");
                    } else if ("12".equals(monthPerformanceModel.getAssessContent())) {
                        monthPerformanceModel.set("assessContentName", "断奶背膘");
                    }
                }
            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入日期");
        }
        return monthPerformanceModels;
    }

    public void updateLog(SysUpdateLogModel sysUpdateLogModel) throws Exception {
        if (sysUpdateLogModel != null) {
            if (!Long.valueOf("0").equals(sysUpdateLogModel.getRowId())) {
                sysUpdateLogMapper.update(sysUpdateLogModel);
            } else {
                sysUpdateLogModel.setCreateId(getEmployeeId());
                sysUpdateLogModel.setCreateDatetime(new Date());
                sysUpdateLogMapper.insert(sysUpdateLogModel);
            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "没有数据保存");
        }
    }

    @Override
    public BasePageInfo searchUpdateLog(Map<String, Object> inputMap) throws Exception {
        // SqlCon sqlCon = new SqlCon();
        // Map<String, String> map = new HashMap<>();
        // sqlCon.addMainSql("SELECT row_id rowId,DATE(update_time) updateTime,notes FROM `sys_update_log` WHERE STATUS='1' AND deleted_flag='0'");
        // map.put("sql", sqlCon.getCondition());
        // return setSql(SysUpdateLogMapper,sqlCon);
        //// return paramMapper.getObjectInfos(map);
        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql(
                "SELECT T0.ROW_ID rowId,DATE(T0.UPDATE_TIME) updateDate,TIME(T0.UPDATE_TIME) updateCuTime,CONCAT(DATE(T0.UPDATE_TIME),' ',TIME(T0.UPDATE_TIME)) updateTime, ");
        sqlCon.addMainSql(
                "T0.UPDATE_LOG updateLog,T0.UPDATE_TYPE updateType,T1.CODE_NAME updateTypeName,T0.VERSION version,T2.EMPLOYEE_NAME creater,T0.NOTES notes ");
        sqlCon.addMainSql("FROM sys_update_log T0 ");
        sqlCon.addMainSql(
                "LEFT JOIN (SELECT C0.CODE_NAME,C0.CODE_VALUE FROM cd_l_code_list C0 WHERE C0.DELETED_FLAG = '0' AND C0.STATUS = '1' AND C0.TYPE_CODE = 'updatetype') T1 ");
        sqlCon.addMainSql("ON T0.UPDATE_TYPE = T1.CODE_VALUE ");
        sqlCon.addMainSql("LEFT JOIN hr_o_employee T2 ON T0.CREATE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql("WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> returnList = paramMapper.getObjectInfos(map);
        getPageInfo().setTotal(returnList.size());
        return getPageInfo(returnList);
    }

    @Override
    public void deleteUpdateLog(long[] ids) throws Exception {
        Date date = new Date();
        for (long id : ids) {
            sysUpdateLogMapper.delete(id);
        }
    }

    @Override
    public List<Map<String, Object>> getUpdateContentByDate(String date) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT CONCAT(DATE(update_time),' ',TIME(update_time)) updateTime,VERSION version,update_log updateLog ");
        sqlCon.addMainSql("FROM sys_update_log WHERE update_type='2' AND DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addCondition(date, " AND DATE(update_time) = ? ");
        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    public List<PigClassModel> searchSeedPigClassToList(String pigType) throws Exception {
        SqlCon sql = new SqlCon();
        if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
            sql.addMainSql(" AND ROW_ID IN (1,16)");
        } else {
            sql.addMainSql(" AND ROW_ID IN (3,16)");
        }
        List<PigClassModel> list = getList(pigClassMapper, sql);
        return list;
    }

    /**
     * @Description: 消息推送增，编辑
     * @author ZJ
     * @param inputMap
     * @throws Exception
     */
    @Override
    public void editPushMessage(Map<String, Object> inputMap, String pushMessageList, Long farmId) throws Exception {
        List<WeRmessageUserModel> weRmessageUserLists = getJsonList(pushMessageList, WeRmessageUserModel.class);
        if (weRmessageUserLists == null || weRmessageUserLists.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
        } else {
            String messageCode = Maps.getString(inputMap, "messageCode");
            String messageTitle = Maps.getString(inputMap, "messageTitle");
            String messageType = Maps.getString(inputMap, "messageType");
            String description = Maps.getString(inputMap, "description");
            String picUrl = Maps.getString(inputMap, "picUrl");
            String contentUrl = Maps.getString(inputMap, "contentUrl");
            String content = Maps.getString(inputMap, "content");

            Long mainId = Maps.getLongClass(inputMap, "rowId");
            // 消息推送实体
            WeMpushMessageModel weMpushMessageModel = new WeMpushMessageModel();
            // 消息人员关系
            WeRmessageUserModel weRmessageUserModel = new WeRmessageUserModel();
            EmployeeModel employeeModel = new EmployeeModel();
            // 新增
            if (mainId == null || mainId == 0) {
                weMpushMessageModel.setMessageCode(messageCode);
                weMpushMessageModel.setMessageTitle(messageTitle);
                weMpushMessageModel.setMessageType(messageType);
                weMpushMessageModel.setDescription(description);
                weMpushMessageModel.setPicUrl(picUrl);
                weMpushMessageModel.setContentUrl(contentUrl);
                weMpushMessageModel.setContent(content);
                weMpushMessageModel.setStatus("1");
                weMpushMessageModel.setDeletedFlag("0");
                weMpushMessageModel.setUseFlag("N");
                weMpushMessageMapper.insert(weMpushMessageModel);
                Long messageId = weMpushMessageModel.getRowId();
                for (WeRmessageUserModel weRmessageUser : weRmessageUserLists) {
                    Long userId = (Long) weRmessageUser.get("employeeId");
                    weRmessageUserModel.setEmployeeId(userId);
                    weRmessageUserModel.setMessageId(messageId);
                    weRmessageUserModel.setStatus("1");
                    weRmessageUserModel.setDeletedFlag("0");
                    weRmessageUserModel.setNotes((String) weRmessageUser.get("notes"));
                    weRmessageUserMapper.insert(weRmessageUserModel);
                }
        } else {
                // 编辑
                weMpushMessageModel.setMessageCode(messageCode);
                weMpushMessageModel.setMessageTitle(messageTitle);
                weMpushMessageModel.setMessageType(messageType);
                weMpushMessageModel.setDescription(description);
                weMpushMessageModel.setPicUrl(picUrl);
                weMpushMessageModel.setContentUrl(contentUrl);
                weMpushMessageModel.setContent(content);
                weMpushMessageModel.setStatus("1");
                weMpushMessageModel.setDeletedFlag("0");
                weMpushMessageModel.setUseFlag("N");
                weMpushMessageModel.setRowId(mainId);
                weMpushMessageMapper.update(weMpushMessageModel);
                for (WeRmessageUserModel weRmessageUser : weRmessageUserLists) {
                    weRmessageUser.setMessageId(mainId);
                    if (weRmessageUser.getRowId() != null) {
                        weRmessageUserMapper.update(weRmessageUser);
                    } else {
                        weRmessageUserMapper.insert(weRmessageUser);
                    }
                }
            }
        }
    }

    /**
     * 消息推送主页分页
     */
    @Override
    public BasePageInfo searchpushMessageToPage(Map<String, Object> map) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS rowId,MESSAGE_CODE AS messageCode,MESSAGE_TITLE AS messageTitle, ");
        sqlCon.addMainSql(" MESSAGE_TYPE AS messageType,CONTENT AS content,USE_FLAG AS useFlag, CLOSED_DATE AS closedDate, ");
        sqlCon.addMainSql(" DESCRIPTION AS description,PIC_URL AS picUrl,CONTENT_URL AS contentUrl ");
        sqlCon.addMainSql(" FROM WE_M_PUSH_MESSAGE ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG='0' AND STATUS='1'");
        List<WeMpushMessageModel> pushMessageList = setSql(weMpushMessageMapper, sqlCon);
        for (WeMpushMessageModel weMpushMessageModel : pushMessageList) {
            Map<String, Object> dataMap = weMpushMessageModel.getData();
            dataMap.put("messageTypeName", CacheUtil.getName(Maps.getString(dataMap, "messageType"), NameEnum.CODE_NAME, CodeEnum.MESSAGE_TYPE));
        }
        return getPageInfo(pushMessageList);
    }

    /**
     * 消息推送删除
     */
    @Override
    public void deletePushMessage(long[] ids) throws Exception {
        if (ids != null) {
            weMpushMessageMapper.deletes(ids);
        }

    }

    /**
     * 停用启用
     */
    @Override
    public void editDisableOrEnable(long[] ids) throws Exception {
        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT USE_FLAG AS useFlag,ROW_ID AS rowId FROM WE_M_PUSH_MESSAGE ");
        sqlCon.addMainSql("WHERE STATUS='1' AND DELETED_FLAG='0' ");
        sqlCon.addCondition(rowIds, "AND row_id IN ?", false, true);
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            String useFlag = Maps.getString(map, "useFlag");
            if ("Y".equals(useFlag)) {
                SqlCon sqlCon2 = new SqlCon();
                sqlCon2.addMainSql("UPDATE WE_M_PUSH_MESSAGE SET USE_FLAG='N', CLOSED_DATE=NULL ");
                sqlCon2.addMainSql("WHERE STATUS='1' AND DELETED_FLAG='0' AND USE_FLAG='Y' ");
                sqlCon2.addCondition(Maps.getString(map, "rowId"), "AND row_id IN ?", false, true);
                setSql(weMpushMessageMapper, sqlCon2);
            } else {
                SqlCon sqlCon2 = new SqlCon();
                sqlCon2.addMainSql("UPDATE WE_M_PUSH_MESSAGE SET USE_FLAG='Y',CLOSED_DATE=NOW() ");
                sqlCon2.addMainSql("WHERE STATUS='1' AND DELETED_FLAG='0' AND USE_FLAG='N' ");
                sqlCon2.addCondition(Maps.getString(map, "rowId"), "AND row_id IN ?", false, true);
                setSql(weMpushMessageMapper, sqlCon2);
            }

        }

    }

    /**
     * 消息推送选择人员搜索
     */
    @Override
    public BasePageInfo searchChooseUserToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        String rowIds = (String) inputMap.get("rowIds");
        sqlCon.addMainSql("SELECT DISTINCT(QY_USER_ID) AS qyUserId,ROW_ID AS rowId, EMPLOYEE_NAME AS employeeName ");
        sqlCon.addMainSql(" FROM HR_O_EMPLOYEE T ");
        sqlCon.addMainSql(" WHERE STATUS='1' AND DELETED_FLAG='0' AND QY_USER_ID IS NOT NULL");
        sqlCon.addCondition(rowIds, " AND T.ROW_ID NOT IN ? ", false, true);
        sqlCon.addCondition(Maps.getString(inputMap, "employeeName"), " AND EMPLOYEE_NAME LIKE ? ", true, false);
        sqlCon.addMainSql(" GROUP BY QY_USER_ID ");
        sqlCon.addMainSql(" ORDER BY ROW_ID ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        return getPageInfo(paramMapper.getObjectInfos(sqlMap));

    }

    // 消息新增删除
    @Override
    public void deleteAddPushMessage(long[] ids) throws Exception {
        if (ids != null) {
            employeeMapper.deletes(ids);
        }

    }

    // 查询消息推送人员关系明细
    @Override
    public List<Map<String, Object>> searchDetailList(Map<String, Object> inputMap) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T0.ROW_ID rowId,T0.EMPLOYEE_ID employeeId,T1.EMPLOYEE_NAME employeeName,T1.QY_USER_ID qyUserId,T0.NOTES notes");
        sqlCon.addMainSql(" FROM we_r_message_user T0 ");
        sqlCon.addMainSql(" LEFT JOIN  hr_o_employee T1 ON T0.EMPLOYEE_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addCondition(Maps.getString(inputMap, "mainId"), " AND T0.MESSAGE_ID = ? ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(sqlMap);

    }

    @Override
    public List<Map<String, Object>> searchMessageByCode(String messageCode) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.ROW_ID rowId,T0.MESSAGE_TITLE messageTitle,T0.MESSAGE_CODE messageCode,");
        sqlCon.addMainSql(" T0.PIC_URL picUrl,T0.CONTENT_URL contentUrl, ");
        sqlCon.addMainSql(" T0.CONTENT content,T0.DESCRIPTION description ");
        sqlCon.addMainSql(" FROM we_m_push_message T0  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.USE_FLAG = 'N' ");
        sqlCon.addCondition(messageCode, " AND T0.MESSAGE_CODE  = ? ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public List<Map<String, Object>> searchUserIdByMessageId(long messageId) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.*,T1.QY_USER_ID FROM we_r_message_user T0 ");
        sqlCon.addMainSql(" LEFT JOIN hr_o_employee T1 ");
        sqlCon.addMainSql(" ON T0.EMPLOYEE_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addCondition(messageId, " AND T0.MESSAGE_ID  = ? ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(sqlMap);
    }
}
