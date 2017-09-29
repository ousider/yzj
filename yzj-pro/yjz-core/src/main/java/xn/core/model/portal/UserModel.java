package xn.core.model.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-11-18 14:10:20
 *       表：HR_M_USER
 */
public class UserModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -7177358541016256046L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // 记录删除标志: [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 公司编码
    private static final String D_CompanyCode="companyCode";

    // 账号
    private static final String D_UserName="userName";

    // 昵称
    private static final String D_NickName="nickName";

    // 密码
    private static final String D_Password="password";

    // 重置码
    private static final String D_ResetKey="resetKey";

    // 账号类型
    private static final String D_UserType="userType";

    // 人员ID
    private static final String D_EmployeeId="employeeId";

    // 首页ID
    private static final String D_ModuleId="moduleId";

    // 是否是初始化密码 Y/N
    private static final String D_IsInitPw="isInitPw";

    // 员工对应的OA账号
    private static final String D_OaUsername="oaUsername";
	
    // 微信首页ID
    private static final String D_WechatModuleId = "wechatModuleId";
    
    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
     * 获取行号: 系统保留字段，标识一条数据记录。
     * 
     * @return ROW_ID
     */
    public Long getRowId() {
        return getLong(D_RowId);
    }

    /**
     * 设置排序号
     * 
     * @param SORT_NBR
     */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
     * 获取排序号
     * 
     * @return SORT_NBR
     */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
     * 设置状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置记录删除标志: [0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取记录删除标志: [0]-未删除;[1]-逻辑删除
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
     * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @param ORIGIN_FLAG
     */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
    /**
     * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @return ORIGIN_FLAG
     */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
     * 设置数据来源应用的代码
     * 
     * @param ORIGIN_APP
     */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
    /**
     * 获取数据来源应用的代码
     * 
     * @return ORIGIN_APP
     */
    public String getOriginApp() {
        return getString(D_OriginApp);
    }

	/**
     * 设置备注
     * 
     * @param NOTES
     */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
    /**
     * 获取备注
     * 
     * @return NOTES
     */
    public String getNotes() {
        return getString(D_Notes);
    }

	/**
     * 设置公司编码
     * 
     * @param COMPANY_CODE
     */
	public void setCompanyCode(String value) {
        set(D_CompanyCode,value);
    }
	
    /**
     * 获取公司编码
     * 
     * @return COMPANY_CODE
     */
    public String getCompanyCode() {
        return getString(D_CompanyCode);
    }

	/**
     * 设置账号
     * 
     * @param USER_NAME
     */
	public void setUserName(String value) {
        set(D_UserName,value);
    }
	
    /**
     * 获取账号
     * 
     * @return USER_NAME
     */
    public String getUserName() {
        return getString(D_UserName);
    }

	/**
     * 设置昵称
     * 
     * @param NICK_NAME
     */
	public void setNickName(String value) {
        set(D_NickName,value);
    }
	
    /**
     * 获取昵称
     * 
     * @return NICK_NAME
     */
    public String getNickName() {
        return getString(D_NickName);
    }

	/**
     * 设置密码
     * 
     * @param PASSWORD
     */
	public void setPassword(String value) {
        set(D_Password,value);
    }
	
    /**
     * 获取密码
     * 
     * @return PASSWORD
     */
    public String getPassword() {
        return getString(D_Password);
    }

	/**
     * 设置重置码
     * 
     * @param RESET_KEY
     */
	public void setResetKey(String value) {
        set(D_ResetKey,value);
    }
	
    /**
     * 获取重置码
     * 
     * @return RESET_KEY
     */
    public String getResetKey() {
        return getString(D_ResetKey);
    }

	/**
     * 设置账号类型
     * 
     * @param USER_TYPE
     */
	public void setUserType(String value) {
        set(D_UserType,value);
    }
	
    /**
     * 获取账号类型
     * 
     * @return USER_TYPE
     */
    public String getUserType() {
        return getString(D_UserType);
    }

    /**
     * 设置人员ID
     * 
     * @param EMPLOYEE_ID
     */
	public void setEmployeeId(Long value) {
        set(D_EmployeeId,value);
    }
	
	/**
     * 获取人员ID
     * 
     * @return EMPLOYEE_ID
     */
    public Long getEmployeeId() {
        return getLong(D_EmployeeId);
    }

    /**
     * 设置首页ID
     * 
     * @param MODULE_ID
     */
	public void setModuleId(Long value) {
        set(D_ModuleId,value);
    }
	
	/**
     * 获取首页ID
     * 
     * @return MODULE_ID
     */
    public Long getModuleId() {
        return getLong(D_ModuleId);
    }

    /**
     * 设置微信首页ID
     * 
     * @param WECHAT_MODULE_ID
     */
    public void setWechatModuleId(Long value) {
        set(D_WechatModuleId, value);
    }

    /**
     * 获取微信首页ID
     * 
     * @return MODULE_ID
     */
    public Long getWechatModuleId() {
        return getLong(D_WechatModuleId);
    }
	
    /**
     * 设置是否是初始化密码 Y/N
     * 
     * @param IS_INIT_PW
     */
	public void setIsInitPw(String value) {
        set(D_IsInitPw,value);
    }
	
    /**
     * 获取是否是初始化密码 Y/N
     * 
     * @return IS_INIT_PW
     */
    public String getIsInitPw() {
        return getString(D_IsInitPw);
    }

	/**
     * 设置员工对应的OA账号
     * 
     * @param OA_USERNAME
     */
	public void setOaUsername(String value) {
        set(D_OaUsername,value);
    }
	
    /**
     * 获取员工对应的OA账号
     * 
     * @return OA_USERNAME
     */
    public String getOaUsername() {
        return getString(D_OaUsername);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("companyCode");
        propertes.add("userName");
        propertes.add("nickName");
        propertes.add("password");
        propertes.add("resetKey");
        propertes.add("userType");
        propertes.add("employeeId");
        propertes.add("moduleId");
        propertes.add("isInitPw");
        propertes.add("oaUsername");
        propertes.add("wechatModuleId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




