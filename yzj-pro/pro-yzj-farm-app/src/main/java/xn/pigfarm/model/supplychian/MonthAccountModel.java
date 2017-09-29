package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-2-22 14:52:26
 *       表：SC_M_MONTH_ACCOUNT 
 */
public class MonthAccountModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 5027403944092358319L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //物料生成号 
    private static final String D_MaterialOnly="materialOnly";
	 //物料批次号 
    private static final String D_MaterialBatch="materialBatch";
	 //仓库ID 
    private static final String D_WarehouseId="warehouseId";
	 //期初数量 
    private static final String D_StartQty="startQty";
	 //期末盘存数量 
    private static final String D_EndQty="endQty";
	 //月初日期 
    private static final String D_StartDate="startDate";
	 //是否已锁定 
    private static final String D_LockFlag="lockFlag";
	 //盘点人 
    private static final String D_CountUser="countUser";
	 //盘点时间 
    private static final String D_CountDate="countDate";
	 //创建事件 
    private static final String D_EventCode="eventCode";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建时间 
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
    * 设置物料生成号
    * @param MATERIAL_ONLY
    */
	public void setMaterialOnly(String value) {
        set(D_MaterialOnly,value);
    }
	
   /**
    * 获取物料生成号
    * @return MATERIAL_ONLY
    */
    public String getMaterialOnly() {
        return getString(D_MaterialOnly);
    }

	/**
    * 设置物料批次号
    * @param MATERIAL_BATCH
    */
	public void setMaterialBatch(String value) {
        set(D_MaterialBatch,value);
    }
	
   /**
    * 获取物料批次号
    * @return MATERIAL_BATCH
    */
    public String getMaterialBatch() {
        return getString(D_MaterialBatch);
    }

   /**
    * 设置仓库ID
    * @param WAREHOUSE_ID
    */
	public void setWarehouseId(Long value) {
        set(D_WarehouseId,value);
    }
	
	/**
    * 获取仓库ID
    * @return WAREHOUSE_ID
    */
    public Long getWarehouseId() {
        return getLong(D_WarehouseId);
    }

	/**
    * 设置期初数量
    * @param START_QTY
    */
	public void setStartQty(Double value) {
        set(D_StartQty,value);
    }
	
   /**
    * 获取期初数量
    * @return START_QTY
    */
    public Double getStartQty() {
        return getDouble(D_StartQty);
    }

	/**
    * 设置期末盘存数量
    * @param END_QTY
    */
	public void setEndQty(Double value) {
        set(D_EndQty,value);
    }
	
   /**
    * 获取期末盘存数量
    * @return END_QTY
    */
    public Double getEndQty() {
        return getDouble(D_EndQty);
    }

	/**
    * 设置月初日期
    * @param START_DATE
    */
	public void setStartDate(Date value) {
        set(D_StartDate,value);
    }
	
   /**
    * 获取月初日期
    * @return START_DATE
    */
    public Date getStartDate() {
        return getDate(D_StartDate);
    }

	/**
    * 设置是否已锁定
    * @param LOCK_FLAG
    */
	public void setLockFlag(String value) {
        set(D_LockFlag,value);
    }
	
   /**
    * 获取是否已锁定
    * @return LOCK_FLAG
    */
    public String getLockFlag() {
        return getString(D_LockFlag);
    }

   /**
    * 设置盘点人
    * @param COUNT_USER
    */
	public void setCountUser(Long value) {
        set(D_CountUser,value);
    }
	
	/**
    * 获取盘点人
    * @return COUNT_USER
    */
    public Long getCountUser() {
        return getLong(D_CountUser);
    }

	/**
    * 设置盘点时间
    * @param COUNT_DATE
    */
	public void setCountDate(Date value) {
        set(D_CountDate,value);
    }
	
   /**
    * 获取盘点时间
    * @return COUNT_DATE
    */
    public Date getCountDate() {
        return getDate(D_CountDate);
    }

	/**
    * 设置创建事件
    * @param EVENT_CODE
    */
	public void setEventCode(String value) {
        set(D_EventCode,value);
    }
	
   /**
    * 获取创建事件
    * @return EVENT_CODE
    */
    public String getEventCode() {
        return getString(D_EventCode);
    }

   /**
    * 设置创建人
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建人
    * @return CREATE_ID
    */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
    * 设置创建时间
    * @param CREATE_DATE
    */
    public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建时间
    * @return CREATE_DATE
    */
    public Date getCreateDate() {
        return getTimestamp(D_CreateDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("deletedFlag");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialBatch");
        propertes.add("warehouseId");
        propertes.add("startQty");
        propertes.add("endQty");
        propertes.add("startDate");
        propertes.add("lockFlag");
        propertes.add("countUser");
        propertes.add("countDate");
        propertes.add("eventCode");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




