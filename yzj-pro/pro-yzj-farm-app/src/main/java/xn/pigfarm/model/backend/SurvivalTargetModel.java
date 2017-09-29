package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-2-22 11:48:29
 *       表：CD_M_SURVIVAL_TARGET
 */
public class SurvivalTargetModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -3030644010413970409L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 备注
    private static final String D_Notes="notes";

    // 对应的猪场ID
    private static final String D_FarmId="farmId";

    // 对应的公司ID
    private static final String D_CompanyId="companyId";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 开始日期
    private static final String D_StartDate="startDate";

    // 结束日期
    private static final String D_EndDate="endDate";

    // 目标数值
    private static final String D_TargetNumber="targetNumber";
	

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
     * 设置对应的猪场ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取对应的猪场ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置对应的公司ID
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取对应的公司ID
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置创建人
     * 
     * @param CREATE_ID
     */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
     * 获取创建人
     * 
     * @return CREATE_ID
     */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
     * 设置创建日期
     * 
     * @param CREATE_DATE
     */
    public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }

	/**
     * 设置开始日期
     * 
     * @param START_DATE
     */
	public void setStartDate(Date value) {
        set(D_StartDate,value);
    }
	
    /**
     * 获取开始日期
     * 
     * @return START_DATE
     */
    public Date getStartDate() {
        return getDate(D_StartDate);
    }

	/**
     * 设置结束日期
     * 
     * @param END_DATE
     */
	public void setEndDate(Date value) {
        set(D_EndDate,value);
    }
	
    /**
     * 获取结束日期
     * 
     * @return END_DATE
     */
    public Date getEndDate() {
        return getDate(D_EndDate);
    }

	/**
     * 设置目标数值
     * 
     * @param TARGET_NUMBER
     */
	public void setTargetNumber(Double value) {
        set(D_TargetNumber,value);
    }
	
    /**
     * 获取目标数值
     * 
     * @return TARGET_NUMBER
     */
    public Double getTargetNumber() {
        return getDouble(D_TargetNumber);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("startDate");
        propertes.add("endDate");
        propertes.add("targetNumber");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




