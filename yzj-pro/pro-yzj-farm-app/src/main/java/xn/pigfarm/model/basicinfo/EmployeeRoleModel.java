package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:07
 *       表：CD_R_EMPLOYEE_ROLE
 */
public class EmployeeRoleModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -907406605946493026L;

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 员工
    private static final String D_EmployeeId="employeeId";

    // 角色ID
    private static final String D_RoleId="roleId";
	

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
     * 设置表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置[0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取[0]-未删除;[1]-逻辑删除
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
     * 设置员工
     * 
     * @param EMPLOYEE_ID
     */
	public void setEmployeeId(Long value) {
        set(D_EmployeeId,value);
    }
	
	/**
     * 获取员工
     * 
     * @return EMPLOYEE_ID
     */
    public Long getEmployeeId() {
        return getLong(D_EmployeeId);
    }

    /**
     * 设置角色ID
     * 
     * @param ROLE_ID
     */
	public void setRoleId(Long value) {
        set(D_RoleId,value);
    }
	
	/**
     * 获取角色ID
     * 
     * @return ROLE_ID
     */
    public Long getRoleId() {
        return getLong(D_RoleId);
    }
	
	
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_SortNbr);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_OriginFlag);
	        setPropertes(D_OriginApp);
	        setPropertes(D_Notes);
	        setPropertes(D_EmployeeId);
	        setPropertes(D_RoleId);
	    }
	    return super.getPropertes();
	}

}




