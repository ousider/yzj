package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-11-30 22:16:29
 *       表：SC_M_STORE_MATERIAL 
 */
public class StoreMaterialModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 1210358031344157587L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
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
	 //物料生成号 
    private static final String D_MaterialOnly="materialOnly";
	 //药品批次号 
    private static final String D_MaterialBatch="materialBatch";
	 //生产日期 
    private static final String D_ProductionDate="productionDate";
	 //有效日期 
    private static final String D_EffectiveDate="effectiveDate";
	 //入库仓位ID 
    private static final String D_WarehouseId="warehouseId";
	 //在库实际数量 
    private static final String D_ActualQty="actualQty";
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
    * 设置药品批次号
    * @param MATERIAL_BATCH
    */
	public void setMaterialBatch(String value) {
        set(D_MaterialBatch,value);
    }
	
   /**
    * 获取药品批次号
    * @return MATERIAL_BATCH
    */
    public String getMaterialBatch() {
        return getString(D_MaterialBatch);
    }

	/**
    * 设置生产日期
    * @param PRODUCTION_DATE
    */
	public void setProductionDate(Date value) {
        set(D_ProductionDate,value);
    }
	
   /**
    * 获取生产日期
    * @return PRODUCTION_DATE
    */
    public Date getProductionDate() {
        return getDate(D_ProductionDate);
    }

	/**
    * 设置有效日期
    * @param EFFECTIVE_DATE
    */
	public void setEffectiveDate(Date value) {
        set(D_EffectiveDate,value);
    }
	
   /**
    * 获取有效日期
    * @return EFFECTIVE_DATE
    */
    public Date getEffectiveDate() {
        return getDate(D_EffectiveDate);
    }

   /**
    * 设置入库仓位ID
    * @param WAREHOUSE_ID
    */
	public void setWarehouseId(Long value) {
        set(D_WarehouseId,value);
    }
	
	/**
    * 获取入库仓位ID
    * @return WAREHOUSE_ID
    */
    public Long getWarehouseId() {
        return getLong(D_WarehouseId);
    }

	/**
    * 设置在库实际数量
    * @param ACTUAL_QTY
    */
	public void setActualQty(Double value) {
        set(D_ActualQty,value);
    }
	
   /**
    * 获取在库实际数量
    * @return ACTUAL_QTY
    */
    public Double getActualQty() {
        return getDouble(D_ActualQty);
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
        return getTimestamp(D_CreateDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialBatch");
        propertes.add("productionDate");
        propertes.add("effectiveDate");
        propertes.add("warehouseId");
        propertes.add("actualQty");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




