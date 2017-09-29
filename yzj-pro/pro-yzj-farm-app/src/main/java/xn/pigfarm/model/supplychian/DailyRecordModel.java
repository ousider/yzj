package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-9-28 14:47:05
 *       表：SC_M_DAILY_RECORD 
 */
public class DailyRecordModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -1287159875085822055L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[1]-未提交需求[2]-已提交需求 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //数量 
    private static final String D_Quantity="quantity";
	 //计划需求日期 
    private static final String D_PlanDate="planDate";
	 //需求单ID 
    private static final String D_RequireId="requireId";
	 //记录人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	

   /**
    * 设置行号: 系统保留字段，标识一条数据记录。
    * @param ROW_ID
    */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
    * 获取行号: 系统保留字段，标识一条数据记录。
    * @return ROW_ID
    */
    public Long getRowId() {
        return getLong(D_RowId);
    }

	/**
    * 设置[1]-未提交需求[2]-已提交需求
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]-未提交需求[2]-已提交需求
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
    * 设置[0]-未删除;[1]-逻辑删除
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取[0]-未删除;[1]-逻辑删除
    * @return DELETED_FLAG
    */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
    * 设置备注
    * @param NOTES
    */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
   /**
    * 获取备注
    * @return NOTES
    */
    public String getNotes() {
        return getString(D_Notes);
    }

   /**
    * 设置对应的公司ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取对应的公司ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置companyId
    * @param COMPANY_ID
    */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
    * 获取companyId
    * @return COMPANY_ID
    */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

   /**
    * 设置物料主数据ID
    * @param MATERIAL_ID
    */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
    * 获取物料主数据ID
    * @return MATERIAL_ID
    */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

	/**
    * 设置数量
    * @param QUANTITY
    */
	public void setQuantity(Double value) {
        set(D_Quantity,value);
    }
	
   /**
    * 获取数量
    * @return QUANTITY
    */
    public Double getQuantity() {
        return getDouble(D_Quantity);
    }

	/**
    * 设置计划需求日期
    * @param PLAN_DATE
    */
	public void setPlanDate(Date value) {
        set(D_PlanDate,value);
    }
	
   /**
    * 获取计划需求日期
    * @return PLAN_DATE
    */
    public Date getPlanDate() {
        return getDate(D_PlanDate);
    }

   /**
    * 设置需求单ID
    * @param REQUIRE_ID
    */
	public void setRequireId(Long value) {
        set(D_RequireId,value);
    }
	
	/**
    * 获取需求单ID
    * @return REQUIRE_ID
    */
    public Long getRequireId() {
        return getLong(D_RequireId);
    }

   /**
    * 设置记录人
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取记录人
    * @return CREATE_ID
    */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
    * 设置创建日期
    * @param CREATE_DATE
    */
	public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建日期
    * @return CREATE_DATE
    */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("materialId");
        propertes.add("quantity");
        propertes.add("planDate");
        propertes.add("requireId");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




