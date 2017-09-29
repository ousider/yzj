package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-1-23 9:35:10
 *       表：SC_M_WAREHOUSE 
 */
public class WarehouseModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 2409974372928038174L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //[1]-未初始化[2]-使用中[3]已禁用 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //仓库编码 
    private static final String D_WarehouseName="warehouseName";
	 //仓库类型 
    private static final String D_WarehouseType="warehouseType";
	 //warehouseAddress 
    private static final String D_WarehouseAddress="warehouseAddress";
	 //仓库具体分类[1]-兽药仓库[2]-饲料仓库[3]-添加药仓库[4]-精液仓库 
    private static final String D_WarehouseCategory="warehouseCategory";
	 //操作角色 
    private static final String D_OperationRole="operationRole";
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
    * 设置排序号
    * @param SORT_NBR
    */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
    * 获取排序号
    * @return SORT_NBR
    */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
    * 设置[1]-未初始化[2]-使用中[3]已禁用
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]-未初始化[2]-使用中[3]已禁用
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
    * 设置仓库编码
    * @param WAREHOUSE_NAME
    */
	public void setWarehouseName(String value) {
        set(D_WarehouseName,value);
    }
	
   /**
    * 获取仓库编码
    * @return WAREHOUSE_NAME
    */
    public String getWarehouseName() {
        return getString(D_WarehouseName);
    }

	/**
    * 设置仓库类型
    * @param WAREHOUSE_TYPE
    */
	public void setWarehouseType(String value) {
        set(D_WarehouseType,value);
    }
	
   /**
    * 获取仓库类型
    * @return WAREHOUSE_TYPE
    */
    public String getWarehouseType() {
        return getString(D_WarehouseType);
    }

	/**
    * 设置warehouseAddress
    * @param WAREHOUSE_ADDRESS
    */
	public void setWarehouseAddress(String value) {
        set(D_WarehouseAddress,value);
    }
	
   /**
    * 获取warehouseAddress
    * @return WAREHOUSE_ADDRESS
    */
    public String getWarehouseAddress() {
        return getString(D_WarehouseAddress);
    }

	/**
    * 设置仓库具体分类[1]-兽药仓库[2]-饲料仓库[3]-添加药仓库[4]-精液仓库
    * @param WAREHOUSE_CATEGORY
    */
	public void setWarehouseCategory(String value) {
        set(D_WarehouseCategory,value);
    }
	
   /**
    * 获取仓库具体分类[1]-兽药仓库[2]-饲料仓库[3]-添加药仓库[4]-精液仓库
    * @return WAREHOUSE_CATEGORY
    */
    public String getWarehouseCategory() {
        return getString(D_WarehouseCategory);
    }

   /**
    * 设置操作角色
    * @param OPERATION_ROLE
    */
	public void setOperationRole(Long value) {
        set(D_OperationRole,value);
    }
	
	/**
    * 获取操作角色
    * @return OPERATION_ROLE
    */
    public Long getOperationRole() {
        return getLong(D_OperationRole);
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
        return getTimestamp(D_CreateDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("warehouseName");
        propertes.add("warehouseType");
        propertes.add("warehouseAddress");
        propertes.add("warehouseCategory");
        propertes.add("operationRole");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




