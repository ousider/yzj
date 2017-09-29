package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:12
 *       表：HR_O_POST
 */
public class PostModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -7983643236561823168L;

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
	 //farmId 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //deptId 
    private static final String D_DeptId="deptId";

    // 职务
    private static final String D_Duty="duty";

    // 职称
    private static final String D_JobTitle="jobTitle";

    // 职级
    private static final String D_DutyLvl="dutyLvl";

    // 任职资格
    private static final String D_Qualification="qualification";
	

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
     * 设置farmId
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取farmId
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置companyId
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取companyId
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置deptId
     * 
     * @param DEPT_ID
     */
	public void setDeptId(Long value) {
        set(D_DeptId,value);
    }
	
	/**
     * 获取deptId
     * 
     * @return DEPT_ID
     */
    public Long getDeptId() {
        return getLong(D_DeptId);
    }

	/**
     * 设置职务
     * 
     * @param DUTY
     */
	public void setDuty(String value) {
        set(D_Duty,value);
    }
	
    /**
     * 获取职务
     * 
     * @return DUTY
     */
    public String getDuty() {
        return getString(D_Duty);
    }

	/**
     * 设置职称
     * 
     * @param JOB_TITLE
     */
	public void setJobTitle(String value) {
        set(D_JobTitle,value);
    }
	
    /**
     * 获取职称
     * 
     * @return JOB_TITLE
     */
    public String getJobTitle() {
        return getString(D_JobTitle);
    }

	/**
     * 设置职级
     * 
     * @param DUTY_LVL
     */
	public void setDutyLvl(String value) {
        set(D_DutyLvl,value);
    }
	
    /**
     * 获取职级
     * 
     * @return DUTY_LVL
     */
    public String getDutyLvl() {
        return getString(D_DutyLvl);
    }

	/**
     * 设置任职资格
     * 
     * @param QUALIFICATION
     */
	public void setQualification(String value) {
        set(D_Qualification,value);
    }
	
    /**
     * 获取任职资格
     * 
     * @return QUALIFICATION
     */
    public String getQualification() {
        return getString(D_Qualification);
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
	        setPropertes(D_FarmId);
	        setPropertes(D_CompanyId);
	        setPropertes(D_DeptId);
	        setPropertes(D_Duty);
	        setPropertes(D_JobTitle);
	        setPropertes(D_DutyLvl);
	        setPropertes(D_Qualification);
	    }
	    return super.getPropertes();
	}

}




