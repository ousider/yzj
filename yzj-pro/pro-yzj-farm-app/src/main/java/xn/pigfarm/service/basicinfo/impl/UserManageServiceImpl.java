package xn.pigfarm.service.basicinfo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.portal.UserMapper;
import xn.core.model.portal.UserModel;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.basicinfo.DeptHouseMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeMapper;
import xn.pigfarm.mapper.basicinfo.EmployeePostMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeRoleMapper;
import xn.pigfarm.model.basicinfo.DeptHouseModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.EmployeePostModel;
import xn.pigfarm.model.basicinfo.EmployeeRoleModel;
import xn.pigfarm.model.basicinfo.UserManageView;
import xn.pigfarm.service.basicinfo.IUserManage;

@Service("UserManageService")
public class UserManageServiceImpl extends BaseServiceImpl implements IUserManage {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeePostMapper employeePostMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private DeptHouseMapper deptHouseMapper;

    // MD5加密
	public static final Md5PasswordEncoder MD5 = new Md5PasswordEncoder();

	@Override
	public List<UserManageView> searchEmployeeToList() throws Exception {
	    List<UserManageView> userManageViewList = employeeMapper.searchByFarmId(getFarmId());

        for (UserManageView userManageView : userManageViewList) {
            userManageView.setEmployeeTypeName(CacheUtil.getName(userManageView.getEmployeeType(), NameEnum.CODE_NAME, CodeEnum.EMPLOYEE_TYPE));
        }
        return userManageViewList;
	}

	@Override
    public List<EmployeeModel> searchEmployeeByIdToList(String houseId) {
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        // 区分销售 选种猪传多个houseId
        if (StringUtil.isNonBlank(houseId)) {
            if (houseId.indexOf(",") > 0) {
                List<String> houseIdList = Arrays.asList(houseId.split(","));
                Set<String> houseIdSet = new HashSet<String>(houseIdList);
                String ids = houseIdSet.toString().replace("[", "").replace("]", "");
                sql.addCondition(ids, " AND HOUSE_ID IN ? ", false, true);
            } else {
                // 精液销售，无法找到猪舍
                if (!"N".equals(houseId)) {
                    sql.addMainSql(" AND HOUSE_ID = " + houseId);
                }
            }
        }
        List<DeptHouseModel> list = getList(deptHouseMapper, sql);

        if (list == null || list.isEmpty()) {
            return null;
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T1.* ");
        sqlCon.addMainSql(" FROM HR_O_EMPLOYEE T1 INNER JOIN HR_R_EMPLOYEE_POST T2 ON (T1.ROW_ID = T2.EMPLOYEE_ID ");
        sqlCon.addMainSql(" AND T2.STATUS='1' AND T2.DELETED_FLAG='0') INNER JOIN HR_O_POST T3 ");
        sqlCon.addMainSql(" ON (T2.POST_ID = T3.ROW_ID AND T3.STATUS='1' AND T3.DELETED_FLAG='0') ");
        sqlCon.addMainSql(" INNER JOIN HR_O_DEPT T4 ON(T3.DEPT_ID = T4.ROW_ID AND T4.STATUS='1' ");
        sqlCon.addMainSql(" AND T4.DELETED_FLAG='0') WHERE T1.STATUS='1' AND T1.DELETED_FLAG='0' ");
        sqlCon.addMainSql(" AND T1.EMPLOYEE_TYPE <> '9' ");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ? ");

        StringBuffer deptId = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                deptId.append(list.get(i).getDeptId());
            } else {
                deptId.append(",");
                deptId.append(list.get(i).getDeptId());
            }
        }
        sqlCon.addCondition(deptId.toString(), " AND T4.ROW_ID IN ? ", false, true);
        List<EmployeeModel> elist = setSql(employeeMapper, sqlCon);
        return elist;
    }

    @Override
    public BasePageInfo searchEmployeeToPage(Long farmId, Map<String, Object> inputMap) throws Exception {

        setToPage();
        EmployeeModel model = new EmployeeModel();
        model.setEmployeeCode(Maps.getString(inputMap, "employeeCode"));
        model.setEmployeeName(Maps.getString(inputMap, "employeeName"));
        model.setEmployeeType(Maps.getString(inputMap, "employeeType"));
        List<UserManageView> userManageViewList = employeeMapper.searchByConToPage(getFarmId(), model);
        for (UserManageView userManageView : userManageViewList) {
            userManageView.setEmployeeTypeName(CacheUtil.getName(userManageView.getEmployeeType(), NameEnum.CODE_NAME, CodeEnum.EMPLOYEE_TYPE));
            if ("1".equals(userManageView.getStatus())) {
                userManageView.setStatusName("启用");
            } else if ("0".equals(userManageView.getStatus())) {
                userManageView.setStatusName("停用");
            }
        }
        return getPageInfo(userManageViewList);
	}

	@Override
    public void editUserByEmployee(Map<String, Object> inputMap) throws Exception {
        EmployeeModel employeeModel = getBean(EmployeeModel.class, inputMap);
        EmployeePostModel employeePostModel = getBean(EmployeePostModel.class, inputMap);
        if (employeeModel.getRowId() == null) {
            // 插入
            String employeeCode = ParamUtil.getBCode("EMPLOYEE", getEmployeeId(), getCompanyId(), getFarmId());
            employeeModel.setEmployeeCode(employeeCode);
            employeeModel.setFarmId(getFarmId());
            employeeModel.setCompanyId(getCompanyId());
            int employeeFlag = employeeMapper.insert(employeeModel);
            // 员工表插入成功
            if (employeeFlag == 1) {
                employeePostModel.setEmployeeId(employeeModel.getRowId());
                employeePostMapper.insert(employeePostModel);

                Object roleIdObject = Maps.get(inputMap, "roleId", null);
                List<String> roleId = null;
                if (roleIdObject instanceof List) {
                    roleId = (List<String>) roleIdObject;
                } else if (roleIdObject instanceof String) {
                    roleId = new ArrayList<String>();
                    roleId.add((String) roleIdObject);
                }

                if (roleId != null) {
                    for (int i = 0; i < roleId.size(); i++) {
                        EmployeeRoleModel employeeRoleModel = new EmployeeRoleModel();
                        employeeRoleModel.setStatus("1");
                        employeeRoleModel.setDeletedFlag("0");
                        employeeRoleModel.setOriginFlag("S");
                        employeeRoleModel.setOriginApp("XN1.0");
                        employeeRoleModel.setEmployeeId(employeeModel.getRowId());
                        employeeRoleModel.setRoleId(Long.valueOf(roleId.get(i)));
                        employeeRoleMapper.insert(employeeRoleModel);
                    }
                }
                // 同事创建用户
                if (inputMap.get("userName") != null) {
                    UserModel user = getBean(UserModel.class, inputMap);
                    user.setCompanyCode(getUserDetail().getCompanyCode());
                    user.setNickName(employeeModel.getEmployeeName());
                    // 默认密码123456
                    user.setPassword(MD5.encodePassword("123456", null));
                    user.setEmployeeId(employeeModel.getRowId());
                    // 强制修改密码
                    user.setIsInitPw("Y");
                    userMapper.insert(user);
                }
            }
        } else {
            // 更新
            employeeMapper.update(employeeModel);
            Map<String, String> map = new HashMap<>();
            map.put("condition", "AND EMPLOYEE_ID = "+employeeModel.getRowId());
            List<EmployeePostModel> updateEmployeePostModelList = employeePostMapper.searchListByCon(map);
            EmployeePostModel updateEmployeePostModel = updateEmployeePostModelList.get(0);
            updateEmployeePostModel.setPostId(Long.valueOf((String) inputMap.get("postId")));
            employeePostMapper.update(updateEmployeePostModel);
        }
	}

	@Override
	public void deletesEmployee(long[] ids) throws Exception {

		employeeMapper.deletes(ids);
        setDeletes(employeePostMapper, ids, "EMPLOYEE_ID");
        // 删除角色
        setDeletes(employeeRoleMapper, ids, "EMPLOYEE_ID");
        // 删除用户
        setDeletes(userMapper, ids, "EMPLOYEE_ID");
	}

	@Override
    public void editUser(Map<String, Object> inputMap) throws Exception {

        if (inputMap.get("rowId") != null) {
            // 修改用户信息

            // 删除原有的角色关系
            // Map<String, Object> conditionMap = new HashMap<>();
            // conditionMap.put("RECORD_CON", "EMPLOYEE_ID");
            // conditionMap.put("RECORD_VALUES", inputMap.get("employeeIdUser"));
            // List<Map<String, Object>> conditionList = new ArrayList<>();
            // conditionList.add(conditionMap);
            // employeeRoleMapper.deletesByCon(conditionList);

            setDeletes(employeeRoleMapper, Maps.getLongClass(inputMap, "employeeIdUser"), "EMPLOYEE_ID");

            // 添加新的角色关系
            Object roleIdObject = Maps.get(inputMap, "roleId", null);
            List<String> roleId = null;
            if (roleIdObject instanceof List) {
                roleId = (List<String>) roleIdObject;
            } else if (roleIdObject instanceof String) {
                roleId = new ArrayList<String>();
                roleId.add((String) roleIdObject);
            }

            if (roleId != null) {
                for (int i = 0; i < roleId.size(); i++) {
                    EmployeeRoleModel employeeRoleModel = new EmployeeRoleModel();
                    employeeRoleModel.setStatus("1");
                    employeeRoleModel.setDeletedFlag("0");
                    employeeRoleModel.setOriginFlag("S");
                    employeeRoleModel.setOriginApp("XN1.0");
                    employeeRoleModel.setEmployeeId(Long.valueOf((String) inputMap.get("employeeIdUser")));
                    employeeRoleModel.setRoleId(Long.valueOf(roleId.get(i)));
                    employeeRoleMapper.insert(employeeRoleModel);
                }
            }
            
            // 更新User主页
            UserModel user = new UserModel();
            user.setRowId(Long.valueOf((String) inputMap.get("rowId")));
            user.setModuleId(Long.valueOf((String) inputMap.get("moduleId")));
            user.setWechatModuleId(Long.valueOf((String) inputMap.get("wechatModuleId")));
            userMapper.update(user);

        } else {
            // 添加新的角色关系
            Object roleIdObject = Maps.get(inputMap, "roleId", null);
            List<String> roleId = null;
            if (roleIdObject instanceof List) {
                roleId = (List<String>) roleIdObject;
            } else if (roleIdObject instanceof String) {
                roleId = new ArrayList<String>();
                roleId.add((String) roleIdObject);
            }

            if (roleId != null) {
                for (int i = 0; i < roleId.size(); i++) {
                    EmployeeRoleModel employeeRoleModel = new EmployeeRoleModel();
                    employeeRoleModel.setStatus("1");
                    employeeRoleModel.setDeletedFlag("0");
                    employeeRoleModel.setOriginFlag("S");
                    employeeRoleModel.setOriginApp("XN1.0");
                    employeeRoleModel.setEmployeeId(Long.valueOf((String) inputMap.get("employeeIdUser")));
                    employeeRoleModel.setRoleId(Long.valueOf(roleId.get(i)));
                    employeeRoleMapper.insert(employeeRoleModel);
                }
            }

            // 添加用户信息
            UserModel user = new UserModel();
            user.setStatus("1");
            user.setDeletedFlag("0");
            user.setOriginFlag("S");
            user.setOriginApp("XN1.0");
            user.setIsInitPw("Y");
            user.setCompanyCode(getUserDetail().getCompanyCode());
            user.setUserName((String) inputMap.get("userName"));
            user.setNickName((String) inputMap.get("employeeName"));
            // 默认密码123456
            user.setPassword(MD5.encodePassword("123456", null));
            user.setEmployeeId(Long.valueOf((String) inputMap.get("employeeIdUser")));
            user.setModuleId(Long.valueOf((String) inputMap.get("moduleId")));
            user.setWechatModuleId(Long.valueOf((String) inputMap.get("wechatModuleId")));
            userMapper.insert(user);
        }
	}

    // @Override
    // public List<Map<String, Object>> searchEmpByRole(Long farmId) throws Exception {
    //
    // List<UserManageView> listUser = employeeMapper.searchEmpByRole(farmId == null ? getFarmId() : farmId);
    // List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    //
    // for (UserManageView userManageView : listUser) {
    // Map<String, Object> entity = new HashMap<>();
    // entity.put("rowId", userManageView.getRowId());
    // entity.put("deletedFlag", userManageView.getDeletedFlag());
    // entity.put("employeeCode", userManageView.getEmployeeCode());
    // entity.put("employeeName", userManageView.getEmployeeName());
    // entity.put("sex", userManageView.getSex());
    // entity.put("employeeType", userManageView.getEmployeeType());
    // entity.put("mobile", userManageView.getMobile());
    // entity.put("postcode", userManageView.getPostcode());
    // entity.put("qq", userManageView.getQq());
    // entity.put("operate", null);
    // maps.add(entity);
    // }
    // return maps;
    // }

	@Override
    public List<Map<String, Object>> searchEmpByRoleId(Long roleId) throws Exception {
		
        List<UserManageView> listUser = employeeMapper.searchEmpByRoleId(roleId);
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

		for (UserManageView userManageView : listUser) {
            Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("rowId", userManageView.getRowId());
			entity.put("deletedFlag", userManageView.getDeletedFlag());
			entity.put("employeeCode", userManageView.getEmployeeCode());
			entity.put("employeeName", userManageView.getEmployeeName());
			entity.put("sex", userManageView.getSex());
            Date birthday = userManageView.getBirthday();
            if (birthday != null) {
                String birthdays = TimeUtil.format(birthday, TimeUtil.DATE_FORMAT);
                entity.put("birthday", birthdays);
            }
            entity.put("nationality", userManageView.getNationality());
			entity.put("employeeType", userManageView.getEmployeeType());
            entity.put("employeeTypeName", CacheUtil.getName(userManageView.getEmployeeType(), NameEnum.CODE_NAME, CodeEnum.EMPLOYEE_TYPE));
            Date entryDate = userManageView.getEntryDate();
            if (entryDate != null) {
                String entryDates = TimeUtil.format(birthday, TimeUtil.DATE_FORMAT);
                entity.put("entryDate", entryDates);
            }
			maps.add(entity);
		}
		return maps;
	}

    @Override
    public List<Map<String, Object>> saerchUserInfoByEmployeeId(Map<String, Object> inputMap) throws Exception {
        HashMap<String, String> sqlMap1 = new HashMap<>();
        sqlMap1.put("sql",
                "SELECT ROW_ID AS rowId, USER_NAME AS userName, MODULE_ID AS moduleId,WECHAT_MODULE_ID AS wechatModuleId FROM HR_M_USER WHERE DELETED_FLAG='0' AND STATUS = '1' "
                        + " AND EMPLOYEE_ID = " + inputMap.get("employeeId"));
        List<Map<String, Object>> result1 = paramMapper.getObjectInfos(sqlMap1);
        
        HashMap<String, String> sqlMap2 = new HashMap<>();
        sqlMap2.put("sql", "SELECT ROLE_ID AS roleId FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG='0' AND STATUS = '1' "
                + " AND EMPLOYEE_ID = " + inputMap.get("employeeId"));

        List<Map<String, Object>> result2 = paramMapper.getObjectInfos(sqlMap2);
        List<String> roleId = new ArrayList<>();
        if (result2 != null && !result2.isEmpty()) {
            for (Map<String, Object> map : result2) {
                roleId.add(String.valueOf(map.get("roleId")));
            }
        }
        if (result1 != null && !result1.isEmpty()) {
            result1.get(0).put("roleId", roleId);
        }

        return result1;
    }

    @Override
    public BasePageInfo saerchEmployeeByConditionToPage(Long farmId, Map<String, Object> inputMap) throws Exception {
        // SqlCon sql = new SqlCon();
        // sql.addCondition("SELECT * FROM HR_O_EMPLOYEE WHERE STATUS='1' AND DELETED_FLAG='0' AND FARM_ID = " + getFarmId());
        // if (!Maps.isEmpty(inputMap, "employeeCode")) {
        // sql.addCondition(" AND EMPLOYEE_CODE LIKE '%" + inputMap.get("employeeCode") + "%'");
        // }
        // if (!Maps.isEmpty(inputMap, "employeeName")) {
        // sql.addCondition(" AND EMPLOYEE_NAME LIKE '%" + inputMap.get("employeeName") + "%'");
        // }
        // if (!Maps.isEmpty(inputMap, "employeeType")) {
        // sql.addCondition(" AND EMPLOYEE_TYPE = '" + inputMap.get("employeeType") + "'");
        // } else {
        // sql.addCondition(" AND EMPLOYEE_TYPE <> '9'");
        // }
        //
        // Map<String, String> sqlMap = new HashMap<String, String>();
        // sqlMap.put("sql", sql.getCondition());
        //
        // setToPage();
        // List<EmployeeModel> employeeList = employeeMapper.operSql(sqlMap);
        //
        // if (employeeList == null) {
        // Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "未查询到数据");
        // }
        //
        // List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        //
        // if (employeeList != null && employeeList.size() > 0) {
        // for (EmployeeModel employeeModel : employeeList) {
        // Map<String, Object> map = employeeModel.getData();
        // map.put("employeeTypeName", CacheUtil.getName((String) map.get("employeeType"), NameEnum.CODE_NAME, CodeEnum.EMPLOYEE_TYPE));
        // resultMap.add(map);
        // }
        // }
        
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addCondition(inputMap.get("employeeCode"), " AND EMPLOYEE_CODE LIKE ?", true);
        sql.addCondition(inputMap.get("employeeName"), " AND EMPLOYEE_NAME LIKE ?", true);
        if (!Maps.isEmpty(inputMap, "employeeType")) {
            sql.addCondition(inputMap.get("employeeType"), " AND EMPLOYEE_TYPE = ?");
        } else {
            sql.addMainSql(" AND EMPLOYEE_TYPE <> '9'");
        }
        setToPage();
        List<EmployeeModel> employeeList = getList(employeeMapper, sql);
        CacheUtil.setName(employeeList, NameEnum.CODE_NAME, CodeEnum.EMPLOYEE_TYPE);
        return getPageInfo( employeeList);
    }

    @Override
    public List<UserModel> searchUserIsExists(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(inputMap.get("userName"), " AND USER_NAME = ?");
        sqlCon.addConditionWithNull(getUserDetail().getCompanyCode(), " AND COMPANY_CODE =?");

        return getList(userMapper, sqlCon);
    }

    @Override
    public void editUserResetPassword(Map<String, Object> inputmMap) {
        UserModel user = new UserModel();
        user.setRowId(Long.valueOf((String) inputmMap.get("rowId")));
        // 默认密码123456
        user.setPassword(MD5.encodePassword("123456", null));
        user.setIsInitPw("Y");
        userMapper.update(user);
    }

    @Override
    public void editDisablesEmployee(long[] ids) throws Exception {
        String status = "0";// 停用
        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sql = new SqlCon();
        sql.addMainSql("UPDATE hr_o_employee SET ");
        sql.addCondition(status, " STATUS = ? ");
        sql.addCondition(rowIds, "WHERE row_id IN ?", false, true);
        setSql(employeeMapper, sql);
    }

    @Override
    public void editEnablesEmployee(long[] ids) throws Exception {
        String status = "1";// 启用
        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sql = new SqlCon();
        sql.addMainSql("UPDATE hr_o_employee SET ");
        sql.addCondition(status, " STATUS = ? ");
        sql.addCondition(rowIds, "WHERE row_id IN ?", false, true);
        setSql(employeeMapper, sql);
    }
}
