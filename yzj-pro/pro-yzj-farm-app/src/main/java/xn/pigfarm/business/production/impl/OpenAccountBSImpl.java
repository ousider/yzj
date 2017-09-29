package xn.pigfarm.business.production.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.portal.UserMapper;
import xn.core.mapper.system.BcodeMapper;
import xn.core.model.portal.UserModel;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.business.production.IOpenAccountBS;
import xn.pigfarm.exception.basicInfo.BasicInfoException;
import xn.pigfarm.mapper.backend.WeekMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeRoleMapper;
import xn.pigfarm.mapper.basicinfo.IndicatorMapper;
import xn.pigfarm.mapper.basicinfo.PigObsoleteMapper;
import xn.pigfarm.mapper.basicinfo.PigObsoleteModuleMapper;
import xn.pigfarm.mapper.basicinfo.RoleMapper;
import xn.pigfarm.mapper.basicinfo.SettingMapper;
import xn.pigfarm.mapper.basicinfo.SettingModuleMapper;
import xn.pigfarm.model.backend.WeekModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.EmployeeRoleModel;
import xn.pigfarm.model.basicinfo.PigObsoleteModel;
import xn.pigfarm.model.basicinfo.PigObsoleteModuleModel;
import xn.pigfarm.model.basicinfo.RoleModel;
import xn.pigfarm.model.basicinfo.SettingModel;
import xn.pigfarm.model.basicinfo.SettingModuleModel;
import xn.pigfarm.util.constants.RoleEmployeeConstans;

/**
 * @Description: 开新账户BS层
 * @author zhangjs
 * @date 2016年8月23日 下午1:31:35
 */
@Component("openAccountBS")
public class OpenAccountBSImpl extends BaseServiceImpl implements IOpenAccountBS {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeekMapper weekMapper;

    @Autowired
    private SettingModuleMapper settingModuleMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private BcodeMapper bcodeMapper;

    @Autowired
    private IndicatorMapper indicatorMapper;

    @Autowired
    private PigObsoleteMapper pigObsoleteMapper;

    @Autowired
    private PigObsoleteModuleMapper pigObsoleteModuleMapper;

    /**
     * @Description: 开户
     * @author zhangjs
     * @param companyClass
     * @param companyType
     * @param companyCode
     * @param companyId
     * @param farmId
     * @throws Exception
     */
    @Override
    public void openAccount(String companyClass, String companyType, String companyCode, Long companyId, Long farmId, Long employeeId)
            throws Exception {

        // 判断管理是否存在
        SqlCon roleSql = new SqlCon();
        roleSql.addMainSql("SELECT COUNT(1) AS count FROM CD_O_ROLE WHERE DELETED_FLAG = 0");
        roleSql.addConditionWithNull(farmId, " AND FARM_ID = ?");
        roleSql.addCondition(RoleEmployeeConstans.ROLE_OF_ENTERPRISE, " AND ROLE_TYPE = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", roleSql.getCondition());
        List<Map<String, Object>> role = paramMapper.getObjectInfos(sqlMap);

        long count = Maps.getLong(role.get(0), "count");
        if (count > 0) {
            Thrower.throwException(BasicInfoException.ROLE_OF_ENTERPRISE_IS_EXISTS);
        }

        // 创建管理员角色
        RoleModel roleModel = roleMapper.searchById(RoleEmployeeConstans.ADMIN_ROLE_ID);
        roleModel.setCompanyId(companyId);
        roleModel.setFarmId(farmId);
        roleMapper.insert(roleModel);

        // 创建管理员信息
        EmployeeModel employeeModel = employeeMapper.searchById(RoleEmployeeConstans.ADMIN_EMPLOYEE_ID);
        employeeModel.setCompanyId(companyId);
        employeeModel.setFarmId(farmId);
        employeeModel.setEmployeeType(RoleEmployeeConstans.EMPLOYEE_OF_ADMIN);
        employeeModel.setSortNbr(1L);
        employeeMapper.insert(employeeModel);

        // 创建管理员角色信息
        EmployeeRoleModel employeeRoleModel = new EmployeeRoleModel();
        employeeRoleModel.setStatus(CommonConstants.STATUS);
        employeeRoleModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        employeeRoleModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        employeeRoleModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        employeeRoleModel.setEmployeeId(employeeModel.getRowId());
        employeeRoleModel.setRoleId(roleModel.getRowId());
        employeeRoleMapper.insert(employeeRoleModel);

        // 创建管理员帐号
        UserModel userModel = new UserModel();
        userModel.setCompanyCode(companyCode);
        userModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        userModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        userModel.setUserName(RoleEmployeeConstans.ADMIN_USERNAME);
        userModel.setNickName(RoleEmployeeConstans.ADMIN_NICKNAME);
        userModel.setPassword(RoleEmployeeConstans.ADMIN_PASSWORD);
        userModel.setEmployeeId(employeeModel.getRowId());
        userModel.setStatus(CommonConstants.STATUS);
        userModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        userModel.setModuleId(RoleEmployeeConstans.ADMIN_MODULE_ID);
        userModel.setIsInitPw(RoleEmployeeConstans.INIT_PASSWORD);
        userMapper.insert(userModel);

        // 添加设置
        List<SettingModuleModel> settingModuleModelList = settingModuleMapper.searchToList();
        List<SettingModel> settingModelList = new ArrayList<SettingModel>();
        for (SettingModuleModel settingModuleModel : settingModuleModelList) {
            SettingModel settingModel = new SettingModel();
            settingModel.setCompanyId(companyId);
            settingModel.setCorrelationId(settingModuleModel.getCorrelationId());
            settingModel.setCreateDate(new Date());
            settingModel.setCreateId(employeeId);
            settingModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            settingModel.setFarmId(farmId);
            settingModel.setFarmType(companyType);
            settingModel.setGroupCode(settingModuleModel.getGroupCode());
            settingModel.setGroupName(settingModuleModel.getGroupName());
            settingModel.setMemo(settingModuleModel.getMemo());
            settingModel.setNotes(settingModuleModel.getNotes());
            settingModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            settingModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            settingModel.setSettingCode(settingModuleModel.getSettingCode());
            settingModel.setSettingModule(settingModuleModel.getSettingModule());
            settingModel.setSettingName(settingModuleModel.getSettingName());
            settingModel.setSettingValue(settingModuleModel.getSettingValue());
            settingModel.setSortNbr(settingModuleModel.getSortNbr());
            settingModel.setSowType(settingModuleModel.getSowType());
            settingModel.setStatus(CommonConstants.STATUS);
            settingModel.setUpdateDate(null);
            settingModel.setUpdateId(null);
            settingModelList.add(settingModel);
        }
        settingMapper.inserts(settingModelList);

        // 添加业务编码
        SqlCon cdSql = new SqlCon();
        cdSql.addMainSql("INSERT INTO CD_L_BCODE (STATUS,DELETED_FLAG,ORIGIN_FLAG,ORIGIN_APP,FARM_ID,COMPANY_ID,BCODE_TYPE_ID,");
        cdSql.addMainSql(" SERIAL_LENGTH,SERIAL_MAX,SERIAL_MIN,PRIFIX_CODE,IS_USE_BDATE,LAST_NUM,NEXT_SEIAL)");
        cdSql.addMainSql(" SELECT STATUS,DELETED_FLAG,ORIGIN_FLAG,ORIGIN_APP,");
        cdSql.addConditionWithNull(farmId, " ? FARM_ID,");
        cdSql.addConditionWithNull(companyId, " ? COMPANY_ID,");
        cdSql.addMainSql(" BCODE_TYPE_ID,SERIAL_LENGTH,SERIAL_MAX,SERIAL_MIN,PRIFIX_CODE,IS_USE_BDATE,LAST_NUM,NEXT_SEIAL");
        cdSql.addMainSql(" FROM CD_L_BCODE WHERE FARM_ID=2 AND DELETED_FLAG='0' AND STATUS='1'");
        setSql(bcodeMapper, cdSql);

        // 添加指标库
        SqlCon indicatorSql = new SqlCon();
        indicatorSql.addMainSql("INSERT INTO PP_L_INDICATOR (SORT_NBR,STATUS,DELETED_FLAG,ORIGIN_FLAG,ORIGIN_APP,NOTES,");
        indicatorSql.addMainSql(" FARM_ID,COMPANY_ID,IND_TYPE_ID,BUSINESS_CODE,IND_NAME,STANDARD_VALUE,MIN_VALUE,");
        indicatorSql.addMainSql(" MAX_VALUE,SECTION,UNIT,FORMULA,DESCRIPTION)");
        indicatorSql.addMainSql(" SELECT SORT_NBR,STATUS,DELETED_FLAG,ORIGIN_FLAG,ORIGIN_APP,NOTES,");
        indicatorSql.addConditionWithNull(farmId, " ? FARM_ID,");
        indicatorSql.addConditionWithNull(companyId, " ? COMPANY_ID,");
        indicatorSql.addMainSql(" IND_TYPE_ID,BUSINESS_CODE,IND_NAME,STANDARD_VALUE,MIN_VALUE,MAX_VALUE,SECTION,UNIT,FORMULA,");
        indicatorSql.addMainSql(" DESCRIPTION FROM PP_L_INDICATOR WHERE FARM_ID=2 AND DELETED_FLAG='0' AND STATUS='1'");
        setSql(indicatorMapper, indicatorSql);

        // 添加淘汰指标
        List<PigObsoleteModuleModel> obsoleteModuleList = pigObsoleteModuleMapper.searchToList();
        List<PigObsoleteModel> obsoleteModelList = new ArrayList<PigObsoleteModel>();
        Date createDate = new Date();
        for (PigObsoleteModuleModel model : obsoleteModuleList) {
            PigObsoleteModel obsoleteModel = new PigObsoleteModel();
            obsoleteModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            obsoleteModel.setCreateDate(createDate);
            obsoleteModel.setNotes(model.getNotes());
            obsoleteModel.setObsoleteCode(model.getObsoleteCode());
            obsoleteModel.setObsoleteStandard(model.getObsoleteStandard());
            obsoleteModel.setSortNbr(model.getSortNbr());
            obsoleteModel.setStatus(CommonConstants.STATUS);
            obsoleteModel.setParam1(model.getParam1());
            obsoleteModel.setParam2(model.getParam2());
            obsoleteModel.setParam3(model.getParam3());
            obsoleteModel.setCreateId(employeeId);
            obsoleteModel.setCreateDate(createDate);
            obsoleteModel.setFarmId(farmId);
            obsoleteModel.setCompanyId(companyId);
            obsoleteModelList.add(obsoleteModel);
        }
        pigObsoleteMapper.inserts(obsoleteModelList);

        // 创建周次
        createWeek(farmId, companyId);
    }

    @Override
    public String getSettingValueByCode(Long farmId, String settingCode) throws Exception {
        SqlCon setSql = new SqlCon();
        setSql.addConditionWithNull(farmId, " AND FARM_ID = ?");
        setSql.addCondition(settingCode, " AND SETTING_CODE = ?");
        SettingModel settingModel = getModel(settingMapper, setSql);
        return settingModel.getSettingValue();
    }

    @Override
    public void createWeek(Long farmId, Long companyId) throws Exception {
        // 创建周次

        int nowYear = TimeUtil.getSysCalendar().get(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
        List<WeekModel> weekModelList = new ArrayList<WeekModel>();
        Calendar calendar = Calendar.getInstance();
        Calendar betweenCal = Calendar.getInstance();
        long sortNbr = 1;
        // 判断是否 取工作周
        if ("ON".equals(this.getSettingValueByCode(farmId, "QGZZ"))) {
            for (int i = -10; i <= 10; i++) {
                Map<String, Object> record = new HashMap<String, Object>();
                record.put("RECORD_CON", "YEAR");
                record.put("RECORD_VALUES", nowYear + i);
                records.add(record);
                Date vDate = sdf.parse(nowYear + i + "-01-01");
                calendar.setTime(vDate);
                int ivDate = calendar.get(Calendar.YEAR);
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.setMinimalDaysInFirstWeek(1);

                while (ivDate <= nowYear + i) {

                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    Date startDate = calendar.getTime();
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    Date endDate = calendar.getTime();
                    betweenCal.setTime(startDate);
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);
                    if (week == 1 && ivDate == betweenCal.get(Calendar.YEAR) && betweenCal.get(Calendar.MONTH) == 11) {
                        endDate = sdf.parse(ivDate + "-12-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        week = 53;
                    } else if (week == 1 && ivDate != betweenCal.get(Calendar.YEAR)) {
                        startDate = sdf.parse(ivDate + "-01-01");
                    }
                    WeekModel weekModel = new WeekModel();
                    weekModel.setCompanyId(companyId);
                    weekModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    weekModel.setEndDate(endDate);
                    weekModel.setFarmId(farmId);
                    weekModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    weekModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    weekModel.setSortNbr(sortNbr);
                    weekModel.setStartDate(startDate);
                    weekModel.setStatus(CommonConstants.STATUS);
                    weekModel.setWeek((long) week);
                    weekModel.setYear((long) ivDate);
                    weekModelList.add(weekModel);
                    vDate = new Date(vDate.getTime() + 7 * 24 * 60 * 60 * 1000);
                    calendar.setTime(vDate);
                    ivDate = calendar.get(Calendar.YEAR);
                    sortNbr++;
                }

            }
        } else {
            for (int i = -10; i <= 10; i++) {
                Map<String, Object> record = new HashMap<String, Object>();
                record.put("RECORD_CON", "YEAR");
                record.put("RECORD_VALUES", nowYear + i);
                records.add(record);
                Date vDate = sdf.parse(nowYear + i + "-01-01");
                calendar.setTime(vDate);
                int ivDate = calendar.get(Calendar.YEAR);
                calendar.setFirstDayOfWeek(Calendar.SUNDAY);
                calendar.setMinimalDaysInFirstWeek(1);
                while (ivDate <= nowYear + i) {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    Date startDate = calendar.getTime();
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                    Date endDate = calendar.getTime();
                    betweenCal.setTime(startDate);
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);

                    if (week == 1 && ivDate == betweenCal.get(Calendar.YEAR) && betweenCal.get(Calendar.MONTH) == 11) {
                        endDate = sdf.parse(ivDate + "-12-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        week = 53;
                    } else if (week == 1 && ivDate != betweenCal.get(Calendar.YEAR)) {
                        startDate = sdf.parse(ivDate + "-01-01");
                    }
                    WeekModel weekModel = new WeekModel();
                    weekModel.setCompanyId(companyId);
                    weekModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    weekModel.setEndDate(endDate);
                    weekModel.setFarmId(farmId);
                    weekModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    weekModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    weekModel.setSortNbr(sortNbr);
                    weekModel.setStartDate(startDate);
                    weekModel.setStatus(CommonConstants.STATUS);
                    weekModel.setWeek((long) week);
                    weekModel.setYear((long) ivDate);
                    weekModelList.add(weekModel);
                    vDate = new Date(vDate.getTime() + 7 * 24 * 60 * 60 * 1000);
                    calendar.setTime(vDate);
                    ivDate = calendar.get(Calendar.YEAR);
                    sortNbr++;
                }

            }
        }
        weekMapper.deletesByCon(records, farmId);
        weekMapper.inserts(weekModelList);
    }
}
