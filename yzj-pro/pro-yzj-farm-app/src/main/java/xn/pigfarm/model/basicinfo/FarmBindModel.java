package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-12-13 14:49:27
 *       表：HR_R_FARM_BIND
 */
public class FarmBindModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -2524221350621540571L;

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

    // 用户Id
    private static final String D_UserId="userId";

    // 公司Id
    private static final String D_CompanyId="companyId";

    // 猪场Id
    private static final String D_FarmId="farmId";

    // 公司编码
    private static final String D_CompanyCode="companyCode";

    // 用户名
    private static final String D_UserName="userName";

    // 密码
    private static final String D_Password="password";

    // 绑定用户账号Id
    private static final String D_BindUserId="bindUserId";

    // 是否是通过帐号同步绑定（Y/N
    private static final String D_IsAsync="isAsync";
	

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
     * 设置用户Id
     * 
     * @param USER_ID
     */
	public void setUserId(Long value) {
        set(D_UserId,value);
    }
	
	/**
     * 获取用户Id
     * 
     * @return USER_ID
     */
    public Long getUserId() {
        return getLong(D_UserId);
    }

    /**
     * 设置公司Id
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取公司Id
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置猪场Id
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取猪场Id
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
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
     * 设置用户名
     * 
     * @param USER_NAME
     */
	public void setUserName(String value) {
        set(D_UserName,value);
    }
	
    /**
     * 获取用户名
     * 
     * @return USER_NAME
     */
    public String getUserName() {
        return getString(D_UserName);
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
     * 设置绑定用户账号Id
     * 
     * @param BIND_USER_ID
     */
	public void setBindUserId(Long value) {
        set(D_BindUserId,value);
    }
	
	/**
     * 获取绑定用户账号Id
     * 
     * @return BIND_USER_ID
     */
    public Long getBindUserId() {
        return getLong(D_BindUserId);
    }

	/**
     * 设置是否是通过帐号同步绑定（Y/N
     * 
     * @param IS_ASYNC
     */
	public void setIsAsync(String value) {
        set(D_IsAsync,value);
    }
	
    /**
     * 获取是否是通过帐号同步绑定（Y/N
     * 
     * @return IS_ASYNC
     */
    public String getIsAsync() {
        return getString(D_IsAsync);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("userId");
        propertes.add("companyId");
        propertes.add("farmId");
        propertes.add("companyCode");
        propertes.add("userName");
        propertes.add("password");
        propertes.add("bindUserId");
        propertes.add("isAsync");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




