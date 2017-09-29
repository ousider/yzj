package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-9-22 13:08:33
 *       表：SC_M_STORE_PIG 
 */
public class StorePigModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 5525714538001764320L;

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
	 //需求头数（需求单填写） 
    private static final String D_RequiredQty="requiredQty";
	 //当前实际数量 
    private static final String D_UnitEachPrice="unitEachPrice";
	 //采购的头数 
    private static final String D_PurchaseQty="purchaseQty";
	 //实际头数（到库单） 
    private static final String D_ActualQty="actualQty";
	 //需求斤两（需求单填写） 
    private static final String D_RequiredWeight="requiredWeight";
	 //公斤单价 
    private static final String D_UnitPriceWeight="unitPriceWeight";
	 //实际斤两（到库单） 
    private static final String D_ActualWeight="actualWeight";
	 //总金额 
    private static final String D_TotalPrice="totalPrice";
	 //总重量 
    private static final String D_TotalWeight="totalWeight";
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
    * 设置需求头数（需求单填写）
    * @param REQUIRED_QTY
    */
	public void setRequiredQty(Long value) {
        set(D_RequiredQty,value);
    }
	
	/**
    * 获取需求头数（需求单填写）
    * @return REQUIRED_QTY
    */
    public Long getRequiredQty() {
        return getLong(D_RequiredQty);
    }

	/**
    * 设置当前实际数量
    * @param UNIT_EACH_PRICE
    */
	public void setUnitEachPrice(Double value) {
        set(D_UnitEachPrice,value);
    }
	
   /**
    * 获取当前实际数量
    * @return UNIT_EACH_PRICE
    */
    public Double getUnitEachPrice() {
        return getDouble(D_UnitEachPrice);
    }

   /**
    * 设置采购的头数
    * @param PURCHASE_QTY
    */
	public void setPurchaseQty(Long value) {
        set(D_PurchaseQty,value);
    }
	
	/**
    * 获取采购的头数
    * @return PURCHASE_QTY
    */
    public Long getPurchaseQty() {
        return getLong(D_PurchaseQty);
    }

   /**
    * 设置实际头数（到库单）
    * @param ACTUAL_QTY
    */
	public void setActualQty(Long value) {
        set(D_ActualQty,value);
    }
	
	/**
    * 获取实际头数（到库单）
    * @return ACTUAL_QTY
    */
    public Long getActualQty() {
        return getLong(D_ActualQty);
    }

	/**
    * 设置需求斤两（需求单填写）
    * @param REQUIRED_WEIGHT
    */
	public void setRequiredWeight(Double value) {
        set(D_RequiredWeight,value);
    }
	
   /**
    * 获取需求斤两（需求单填写）
    * @return REQUIRED_WEIGHT
    */
    public Double getRequiredWeight() {
        return getDouble(D_RequiredWeight);
    }

	/**
    * 设置公斤单价
    * @param UNIT_PRICE_WEIGHT
    */
	public void setUnitPriceWeight(Double value) {
        set(D_UnitPriceWeight,value);
    }
	
   /**
    * 获取公斤单价
    * @return UNIT_PRICE_WEIGHT
    */
    public Double getUnitPriceWeight() {
        return getDouble(D_UnitPriceWeight);
    }

	/**
    * 设置实际斤两（到库单）
    * @param ACTUAL_WEIGHT
    */
	public void setActualWeight(Double value) {
        set(D_ActualWeight,value);
    }
	
   /**
    * 获取实际斤两（到库单）
    * @return ACTUAL_WEIGHT
    */
    public Double getActualWeight() {
        return getDouble(D_ActualWeight);
    }

	/**
    * 设置总金额
    * @param TOTAL_PRICE
    */
	public void setTotalPrice(Double value) {
        set(D_TotalPrice,value);
    }
	
   /**
    * 获取总金额
    * @return TOTAL_PRICE
    */
    public Double getTotalPrice() {
        return getDouble(D_TotalPrice);
    }

	/**
    * 设置总重量
    * @param TOTAL_WEIGHT
    */
	public void setTotalWeight(Double value) {
        set(D_TotalWeight,value);
    }
	
   /**
    * 获取总重量
    * @return TOTAL_WEIGHT
    */
    public Double getTotalWeight() {
        return getDouble(D_TotalWeight);
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
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("materialId");
        propertes.add("requiredQty");
        propertes.add("unitEachPrice");
        propertes.add("purchaseQty");
        propertes.add("actualQty");
        propertes.add("requiredWeight");
        propertes.add("unitPriceWeight");
        propertes.add("actualWeight");
        propertes.add("totalPrice");
        propertes.add("totalWeight");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




