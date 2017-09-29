package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-9-18 17:10:45
 *       表：SC_R_WAREHOUSE_MATERIAL_TYPE 
 */
public class WarehouseMaterialTypeModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -4510912516708709908L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //仓库ID 
    private static final String D_WarehouseId="warehouseId";
	 //物料类型 
    private static final String D_MaterialType="materialType";
	 //创建人 
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
    * 设置物料类型
    * @param MATERIAL_TYPE
    */
	public void setMaterialType(String value) {
        set(D_MaterialType,value);
    }
	
   /**
    * 获取物料类型
    * @return MATERIAL_TYPE
    */
    public String getMaterialType() {
        return getString(D_MaterialType);
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
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("warehouseId");
        propertes.add("materialType");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




