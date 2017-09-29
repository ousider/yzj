package xn.pigfarm.service.basicinfo;

import java.util.List;
import java.util.Map;

import xn.core.model.portal.UserModel;
import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.UserManageView;

public interface IUserManage {

	/***
     * 人员管理 查询人员详情 集合
     * 
     * @return
     * @throws Exception
     */
    public List<UserManageView> searchEmployeeToList() throws Exception;

    /***
     * @Description: 根据猪舍id查人员
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<EmployeeModel> searchEmployeeByIdToList(String houseId) throws Exception;

	/***
     * 人员管理 查询人员详情 集合 (分页)
     * 
     * @return
     * @throws Exception
     */
    public BasePageInfo searchEmployeeToPage(Long farmId, Map<String, Object> inputMap) throws Exception;

	/**
     * 人员管理 人员详情CU操作
     * 
     * @param entity
     * @param jsonObj
     */
    public void editUserByEmployee(Map<String, Object> map) throws Exception;

	/**
     * 人员管理 人员详情批量删除操作
     * 
     * @param ids
     * @throws Exception
     */
	public void deletesEmployee(long[] ids) throws Exception;

	/**
     * 用户信息修改
     * 
     * @param map
     * @throws Exception
     */
    public void editUser(Map<String, Object> map) throws Exception;

    // /**
    // * 根据公司id 查询人员
    // *
    // * @param farmId
    // * @param roleId
    // * @return
    // */
    // public List<Map<String, Object>> searchEmpByRole(Long farmId) throws Exception ;
	
	
	/**
     * 根据公司id，角色id查询人员
     * 
     * @param farmId
     * @param roleId
     * @return
     */
    public List<Map<String, Object>> searchEmpByRoleId(Long roleId) throws Exception;

    /**
     * 点击用户管理，从数据库中查询用户相关信息
     * 
     * @author THL
     * @param map
     * @return
     */
    public List<Map<String, Object>> saerchUserInfoByEmployeeId(Map<String, Object> map) throws Exception;


    /**
     * 高级查询，根据工号，姓名，人员类型
     * 
     * @author THL
     * @param farmId
     * @param map
     * @return
     * @throws Exception
     */
    public BasePageInfo saerchEmployeeByConditionToPage(Long farmId, Map<String, Object> map) throws Exception;

    /**
     * 根据用户名判断用户存不存在
     * 
     * @author THL
     * @param map
     * @return
     */
    public List<UserModel> searchUserIsExists(Map<String, Object> map) throws Exception;

    /**
     * 重置密码
     * 
     * @author THL
     * @param map
     * @return
     */
    public void editUserResetPassword(Map<String, Object> map);

    /**
     * 人员管理 人员详情批量停用操作
     * 
     * @param ids
     * @throws Exception
     */
    public void editDisablesEmployee(long[] ids) throws Exception;

    /**
     * 人员管理 人员详情批量启用操作
     * 
     * @param ids
     * @throws Exception
     */
    public void editEnablesEmployee(long[] ids) throws Exception;

}
