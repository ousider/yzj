package xn.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;
import xn.core.mapper.auth.AuthRoleMapper;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.portal.ModuleMapper;
import xn.core.mapper.portal.UserMapper;
import xn.core.mapper.system.CacheTablesMapper;
import xn.core.mapper.system.MenuMapper;
import xn.core.mapper.system.QuickMenusMapper;
import xn.core.mapper.system.SequenceMapper;
import xn.core.model.ParamModel;
import xn.core.model.auth.AuthRoleModel;
import xn.core.model.portal.ModuleModel;
import xn.core.model.portal.UserModel;
import xn.core.model.system.CacheTablesModel;
import xn.core.model.system.MenuView;
import xn.core.model.system.QuickMenusView;
import xn.core.model.system.SequenceModel;
import xn.core.service.IParamService;
import xn.core.shiro.UserDetail;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;

/***
 * @Description: 公共工具类
 * @author fangc
 * @date 2016年4月15日 上午10:25:02
 */
@Service("ParamService")
public class ParamServiceImpl extends BaseServiceImpl implements IParamService {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private CacheTablesMapper cacheMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AuthRoleMapper roleCoreMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private QuickMenusMapper quickMenusMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    public long isExist(String tableName, Long rowId, List<ParamModel> paramModel) throws Exception {

        return paramMapper.isExist(tableName, rowId, paramModel);
    }

    @Override
    public synchronized String getBCode(String typeCode, long createId, long companyId, long farmId) throws Exception {
        
        Map<String, Object> map = new HashMap<>();
        map.put("typeCode", typeCode);
        map.put("createId", createId);
        map.put("companyId", companyId);
        map.put("farmId", farmId);
        paramMapper.getBCode(map);
        String businessCode = Maps.getString(map, "bcode");
        String errorCode = Maps.getString(map, "errorCode");
        if (!"0".equals(errorCode)) {
            Thrower.throwException(CoreBusiException.CODE_DUPLICATE_ERROR, Maps.getString(map, "errorMessage"));
        }
        return businessCode;
    }

    @Override
    public String getSettingValueByCode(String settingCode) throws Exception {
     
        long companyId = getCompanyId();
        long farmId = getFarmId();
        return this.getSettingValueByCode(settingCode, companyId, farmId);
    }

    @Override
    public String getSettingValueByCode(String settingCode, long companyId, long farmId) throws Exception {

        return paramMapper.getSettingValueByCode(companyId, farmId, settingCode);
    }

    @Override
    public Map<String, List<Map<String, String>>> getCacheTableData(boolean farmflag, Long farmId) throws Exception {

        SqlCon sqlCon = new SqlCon();
        if (farmflag) {
            sqlCon.addMainSql(" AND FARM_FLAG = 'Y'");
        } else {
            sqlCon.addMainSql(" AND FARM_FLAG = 'N'");
        }
        // 查询需要缓存的表信息
        List<CacheTablesModel> cacheList = getList(cacheMapper, sqlCon);

        Map<String, List<Map<String, String>>> result = new HashMap<>();

        for (int i = 0; i < cacheList.size(); i++) {
            try {
                CacheTablesModel cacheTable = cacheList.get(i);
                String tableName =  cacheTable.getTableName();
                String cacheColumns = StringUtil.isBlank(cacheTable.getCacheColumns()) ? "*" : cacheTable.getCacheColumns();
                String condition = cacheTable.getCacheCond();
                String order = cacheTable.getOrderColumn();
                String farmColumn = cacheTable.getFarmColumn() == null ? "FARM_ID" : cacheTable.getFarmColumn();

                sqlCon = new SqlCon();
                sqlCon.addMainSql("SELECT " + cacheColumns + " FROM " + tableName);

                if (StringUtil.isNonBlank(condition)) {
                    sqlCon.addMainSql("  WHERE " + condition);

                    if (farmId != null) {
                        sqlCon.addMainSql(" AND " + farmColumn + "=" + farmId);
                    }
                } else {
                    if (farmId != null) {
                        sqlCon.addMainSql(" WHERE " + farmColumn + "=" + farmId);
                    }
                }

                if (StringUtil.isNonBlank(order)) {
                    sqlCon.addMainSql(" ORDER BY " + order);
                }

                // 查询缓存表的数据
                Map<String, String> map = new HashMap<>();
                map.put("sql", sqlCon.getCondition());
                List<Map<String, String>> datas = paramMapper.getInfos(map);
                result.put(tableName.toUpperCase(), datas);
            }
            catch (Exception e) {
                log.error(e);
            }
        }
        return result;

    }

    @Override
    public List<CacheTablesModel> getCacheTableConfig(boolean farmflag) throws Exception {

        SqlCon sqlCon = new SqlCon();
        if (farmflag) {
            sqlCon.addMainSql(" AND FARM_FLAG = 'Y'");
        } else {
            sqlCon.addMainSql(" AND FARM_FLAG = 'N'");
        }
        // 查询需要缓存的表信息
        return getList(cacheMapper, sqlCon);
    }

    @Override
    public List<Map<String, String>> getCacheDataBySql(String sql) throws Exception {

        // 查询缓存表的数据
        Map<String, String> map = new HashMap<>();
        map.put("sql", sql);

        return paramMapper.getInfos(map);
    }

    @Override
    public Map<String, Object> searchMenuByUserId(String menuType) throws Exception {

        Map<String, Object> result = new HashMap<>();
        result.put("employName", getUserDetail().getEmployName());
        result.put("farmName", getUserDetail().getFarmName());
        result.put("farmId", getFarmId());
        result.put("companyCode", getUserDetail().getCompanyCode());
        result.put("isInitPw", getIsInitPw());
        result.put("password", getUserDetail().getPassword());
        result.put("companyMark", getCompanyMark());
        /* 查询菜单 */
        List<MenuView> list = new ArrayList<>();
        // 先查询权限 ROLE_TYPE为3的直接不查cd_r_limit
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("  SELECT T2.* FROM CD_R_EMPLOYEE_ROLE T1 ");
        sqlCon.addMainSql("  INNER JOIN CD_O_ROLE T2 ON T1.ROLE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'");
        sqlCon.addMainSql("  WHERE T1.EMPLOYEE_ID=" + getEmployeeId() + " AND T2.FARM_ID=" + getFarmId());
        List<AuthRoleModel> roleList = setSql(roleCoreMapper, sqlCon);

        if (roleList == null || roleList.isEmpty()) {
            return result;
        }

        boolean isMFlag = false;
        for (AuthRoleModel model : roleList) {
            // 超级管理、猪场管理员，直接根据TEMLATE_ID查询，不查cd_r_limit
            if ("1".equals(model.getRoleType().trim()) || "2".equals(model.getRoleType().trim()) || "3".equals(model.getRoleType().trim())) {
                // 一个猪场只有一个ROLE_TYPE为3的账户
                list = menuMapper.searchMenuByTemplateId(model.getTemplateId(), menuType);
                isMFlag = true;
                break;
            }
        }

        // 不是管理员的时候再次查询
        if (!isMFlag) {
            list = menuMapper.searchMenuByUserId(getEmployeeId(), getFarmId(), menuType);
        }
        
        if (list == null || list.isEmpty()) {
            return result;
        }
        result.put("menus", list);

        /* 添加快捷菜单 */
        List<Map<String, Object>> quickMenuList = new ArrayList<Map<String, Object>>();
        for (MenuView menuView : list) {
            if ("Y".equals(menuView.getIsQuickMenu())) {
                quickMenuList.add(menuView.getData());
            }
        }
        result.put("quickMenus", quickMenuList);

        /* 查询首页 */
        UserModel user = userMapper.searchById(getUserDetail().getUserId());
        ModuleModel module = moduleMapper.searchById(user.getModuleId());
        result.put("homePage", module == null ? null : module.getData());
        if (user.getWechatModuleId() == null) {
            result.put("wechatHomePage", null);
        } else {
            ModuleModel wechatModule = moduleMapper.searchById(user.getWechatModuleId());
            result.put("wechatHomePage", wechatModule == null ? null : wechatModule.getData());
        }
        /* 已经添加的快捷菜单 */
        List<QuickMenusView> addedQuickMenus = quickMenusMapper.searchQuickMenusByUserId(getUserDetail().getUserId());
        List<Map<String, Object>> addedQuickMenusMapList = new ArrayList<>();
        if (addedQuickMenus != null && !addedQuickMenus.isEmpty()) {
            for (QuickMenusView quickMenusView : addedQuickMenus) {
                addedQuickMenusMapList.add(quickMenusView.getData());
            }
        }
        result.put("addedQuickMenus", addedQuickMenusMapList);

        return result;
    }

    @Override
    public boolean isExitDetail(String tableName, String columnName, long[] ids, boolean hasFarm) throws Exception {
        
        if (ids == null) {
            return false;
        }

        List<String> sqls = new ArrayList<String>();
        for(int i=0;i<ids.length;i++){
            StringBuffer sql = new StringBuffer("SELECT COUNT(1) IS_EXIT_COUNT FROM " + tableName);
            sql.append(" WHERE " + columnName + "=" + ids[i]);
            sql.append(" AND DELETED_FLAG = '0' AND STATUS = '1' ");
            if (hasFarm) {
                sql.append(" AND FARM_ID = " + getFarmId());
            }
            sqls.add(sql.toString());
        }

        // 查询数据
        List<Map<String, String>> datas = paramMapper.getInfosByList(sqls);
        if (datas == null || datas.isEmpty()) {
            return false;
        }

        for (Map<String, String> map : datas) {
            if (Maps.getInt(map, "IS_EXIT_COUNT") > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<MenuView> searchMenuBobox() throws Exception {

        // 先查询权限 ROLE_TYPE为3的直接不查cd_r_limit
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T2.* FROM CD_R_EMPLOYEE_ROLE T1 ");
        sqlCon.addMainSql(" INNER JOIN CD_O_ROLE T2 ON T1.ROLE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'");
        sqlCon.addMainSql("  WHERE T1.EMPLOYEE_ID=" + getEmployeeId() + " AND T2.FARM_ID=" + getFarmId());
        List<AuthRoleModel> roleList = setSql(roleCoreMapper, sqlCon);
        if (roleList != null && !roleList.isEmpty()) {
            for (AuthRoleModel model : roleList) {

                // 超级管理、猪场管理员，直接根据TEMLATE_ID查询，不查cd_r_limit
                if ("1".equals(model.getRoleType().trim()) || "2".equals(model.getRoleType().trim()) || "3".equals(model.getRoleType().trim())) {
                    // 一个猪场只有一个ROLE_TYPE为3的账户
                    return menuMapper.searchMenuByTemplateId(model.getTemplateId(), "1");
                }
            }
        }
        return menuMapper.searchMenuByUserId(getEmployeeId(), getFarmId(), "1");
    }

    @Override
    public Map<String, String> getWeekInfo(Map<String, Object> inputMap) throws Exception {
        String week = Maps.getString(inputMap, "week");
        String year = Maps.getString(inputMap, "year");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT year,week,start_date,end_date FROM cd_o_week");
        if (year != null && week != null) {
            sqlCon.addMainSql(" WHERE 1 = 1");
            sqlCon.addConditionWithNull(week, "  AND week=?");
            sqlCon.addConditionWithNull(year, "  AND year=?");
        } else {
            sqlCon.addMainSql(" WHERE DATE(NOW()) BETWEEN start_date AND end_date  ");
        }
        sqlCon.addConditionWithNull(getFarmId(), "  AND FARM_ID=?");

        // 查询缓存表的数据
        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());
        
        Map<String, String>  result=new HashMap<>();
        List<Map<String, String>> list=paramMapper.getInfos(map);
        if (list != null && !list.isEmpty() && list.get(0) != null) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public long getSeq(String seqName) throws Exception {
        long value = sequenceMapper.getSeq(seqName);
        SequenceModel sequenceModel = new SequenceModel();
        sequenceModel.setSeqName(seqName);
        sequenceModel.setCurrentVal(value);
        sequenceMapper.update(sequenceModel);
        return value;
    }

    @Override
    public List<Map<String, Object>> getXnplusWechatHomeModule() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT T0.ROW_ID rowId,T0.WECHAT_MODULE_ID wechatModuleId,T1.MODULE_NAME moduleName,T1.MODULE_URL moduleUrl,T1.MENU_TYPE menuType,T1.COMPONENT component");
        sqlCon.addMainSql(" FROM hr_m_user T0 ");
        sqlCon.addMainSql(" LEFT JOIN cd_o_module T1 ON T0.WECHAT_MODULE_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T1.MENU_TYPE = '4' ");
        sqlCon.addConditionWithNull(getUserId(), "  AND T0.ROW_ID =?");

        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        return list;
    }

    @Override
    public List<Map<String, Object>> getXnplusWechatMenuListByType(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT A0.MODULE_ID moduleId,T5.MODULE_NAME moduleName,T5.ICON_CLS moduleIconCls,T5.COMPONENT moduleComponent,T5.MODULE_URL moduleUrl,A0.PARENT_ID parentId,T6.COMPONENT parentComponent");
        sqlCon.addMainSql(" FROM(SELECT DISTINCT(T7.OBJ_ID) MODULE_ID,T4.PARENT_ID  ");
        sqlCon.addMainSql(" FROM hr_m_user T0 ");
        sqlCon.addMainSql(" LEFT JOIN cd_r_employee_role T1 ON T0.EMPLOYEE_ID = T1.EMPLOYEE_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN cd_o_role T2 ON T1.ROLE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN cd_o_template T3 ON T2.TEMPLATE_ID = T3.ROW_ID  AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN cd_r_menus T4 ON T3.ROW_ID = T4.TEMPLATE_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(
                " LEFT JOIN cd_r_limit T7 ON T1.ROLE_ID = T7.ROLE_ID AND T4.MODULE_ID = T7.OBJ_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");

        sqlCon.addConditionWithNull(getUserId(), "  AND T0.ROW_ID =?");
        sqlCon.addMainSql(" ) A0 ");
        sqlCon.addMainSql(" LEFT JOIN cd_o_module T5 ON A0.MODULE_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN cd_o_module T6 ON A0.PARENT_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T5.MENU_TYPE = '3' ");
        if (inputMap.get("parentComponent") != null) {
            sqlCon.addConditionWithNull(inputMap.get("parentComponent"), "  AND T6.COMPONENT =?");
        } else {
            sqlCon.addMainSql(" AND T6.COMPONENT IS NULL ");
        }
        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        return list;
    }
    @Override
    public List<Map<String, Object>> getUpdateContent() throws Exception {
        SqlCon sqlCon = new SqlCon();  
        sqlCon.addMainSql(
                "SELECT CONCAT(DATE(update_time),' ',TIME(update_time)) updateTime,VERSION version,update_log updateLog FROM sys_update_log WHERE CURRENT_DATE=DATE(update_time) AND update_type='2' AND DELETED_FLAG = '0' AND STATUS = '1'");
        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    @Override
    public void editSYS_L_ERROR(String eventName, String errorCode, String errorMsg) throws Exception {
        UserDetail userDetail = getUserDetail();

        Date currentDate = new Date();

        // 插入错误信息
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("INSERT INTO SYS_L_ERROR");
        sqlCon.addMainSql(" (SORT_NBR, STATUS, DELETED_FLAG, FARM_ID, COMPANY_ID");
        sqlCon.addMainSql(", CREATE_ID, CREATE_DATE, EVENT_NAME, ERROR_CODE, ERROR_MESSAGE)");
        sqlCon.addMainSql(" VALUES (0");
        sqlCon.addConditionWithNull(CommonConstants.STATUS, ",?");
        sqlCon.addConditionWithNull(CommonConstants.DELETED_FLAG, ",?");
        sqlCon.addConditionWithNull(userDetail.getFarmId(), ",?");
        sqlCon.addConditionWithNull(userDetail.getCompanyId(), ",?");
        sqlCon.addConditionWithNull(userDetail.getEmployeeId(), ",?");
        sqlCon.addConditionWithNull(currentDate, ",'?'");
        sqlCon.addConditionWithNull(eventName, ",?");
        sqlCon.addConditionWithNull(errorCode, ",?");
        sqlCon.addConditionWithNull(errorMsg, ",?)");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        // 执行SQL
        paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public Long getSwineryAge(Long swineryId, Date date) throws Exception {
        
        return Math.round(this.getSwineryAgeDouble(swineryId, date));
    }

    @Override
    public Double getSwineryAgeDouble(Long swineryId, Date date) throws Exception {
        if(swineryId == null){
            Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "批次ID不能为空");
        }
        if(date == null){
            Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "日期不能为空");
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROUND(IFNULL(_getSwineryAge(ROW_ID");
        sqlCon.addCondition(TimeUtil.format(date, TimeUtil.DATE_FORMAT), ",?");
        sqlCon.addMainSql(",IF(HOUSE_TYPE = 6,1,0)),0),2) AS swineryAge");
        sqlCon.addMainSql(" FROM PP_M_SWINERY WHERE DELETED_FLAG = '0'");
        sqlCon.addCondition(swineryId, " AND ROW_ID = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        // 执行SQL
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list.isEmpty()) {
            return 0D;
        }else{
            return Maps.getDouble(list.get(0), "swineryAge");
        }
    }

}
