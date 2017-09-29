package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-5-9 11:32:31
 *       表：SC_M_BILL_PURCHASE_DETAIL 
 */
public class PurchaseDetailModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -4801525632174998484L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[1]正常[6]报废 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //采购单据ID 
    private static final String D_PurchaseId="purchaseId";
	 //采购行号 
    private static final String D_PurchaseLn="purchaseLn";
	 //赠料行号 
    private static final String D_FreeLn="freeLn";
	 //入库单据ID 
    private static final String D_InputId="inputId";
	 //入库行号 
    private static final String D_InputLn="inputLn";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //物料生成号 
    private static final String D_MaterialOnly="materialOnly";
	 //物料拆分号 
    private static final String D_MaterialSplit="materialSplit";
	 //单价 
    private static final String D_ActualPrice="actualPrice";
	 //实际赠送比率 
    private static final String D_ActualFreePercent="actualFreePercent";
	 //审批时采购量 
    private static final String D_PassQty="passQty";
	 //实际采购量 
    private static final String D_PurchaseQty="purchaseQty";
	 //赠料关联购料ID 
    private static final String D_FreeRelatedId="freeRelatedId";
	 //是否是套餐[1]-否[2]-是 
    private static final String D_IsPackage="isPackage";
	 //到库实际数量 
    private static final String D_ArriveQty="arriveQty";
	 //[1]-集团采购[2]-个体采购 
    private static final String D_GroupOrSelf="groupOrSelf";
	 //供应商 
    private static final String D_SupplierId="supplierId";
	 //需求物料猪场 
    private static final String D_RequireFarm="requireFarm";
	 //0元原因 [A]-购料 [B]-赠料 [C]-返利金额 
    private static final String D_RebateReason="rebateReason";
	 //返利金额 
    private static final String D_RebatePrice="rebatePrice";
	 //采购员ID 
    private static final String D_PurchaserId="purchaserId";
	 //预计到货日期 
    private static final String D_ArriveDate="arriveDate";
	 //单据编号 
    private static final String D_BillCode="billCode";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //单据事件(单据类型) 
    private static final String D_EventCode="eventCode";
	 //创建者(录单人员) 
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
    * 设置[1]正常[6]报废
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]正常[6]报废
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
    * 设置采购单据ID
    * @param PURCHASE_ID
    */
	public void setPurchaseId(Long value) {
        set(D_PurchaseId,value);
    }
	
	/**
    * 获取采购单据ID
    * @return PURCHASE_ID
    */
    public Long getPurchaseId() {
        return getLong(D_PurchaseId);
    }

   /**
    * 设置采购行号
    * @param PURCHASE_LN
    */
	public void setPurchaseLn(Long value) {
        set(D_PurchaseLn,value);
    }
	
	/**
    * 获取采购行号
    * @return PURCHASE_LN
    */
    public Long getPurchaseLn() {
        return getLong(D_PurchaseLn);
    }

   /**
    * 设置赠料行号
    * @param FREE_LN
    */
	public void setFreeLn(Long value) {
        set(D_FreeLn,value);
    }
	
	/**
    * 获取赠料行号
    * @return FREE_LN
    */
    public Long getFreeLn() {
        return getLong(D_FreeLn);
    }

   /**
    * 设置入库单据ID
    * @param INPUT_ID
    */
	public void setInputId(Long value) {
        set(D_InputId,value);
    }
	
	/**
    * 获取入库单据ID
    * @return INPUT_ID
    */
    public Long getInputId() {
        return getLong(D_InputId);
    }

   /**
    * 设置入库行号
    * @param INPUT_LN
    */
	public void setInputLn(Long value) {
        set(D_InputLn,value);
    }
	
	/**
    * 获取入库行号
    * @return INPUT_LN
    */
    public Long getInputLn() {
        return getLong(D_InputLn);
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
    * 设置物料拆分号
    * @param MATERIAL_SPLIT
    */
	public void setMaterialSplit(String value) {
        set(D_MaterialSplit,value);
    }
	
   /**
    * 获取物料拆分号
    * @return MATERIAL_SPLIT
    */
    public String getMaterialSplit() {
        return getString(D_MaterialSplit);
    }

	/**
    * 设置单价
    * @param ACTUAL_PRICE
    */
	public void setActualPrice(Double value) {
        set(D_ActualPrice,value);
    }
	
   /**
    * 获取单价
    * @return ACTUAL_PRICE
    */
    public Double getActualPrice() {
        return getDouble(D_ActualPrice);
    }

	/**
    * 设置实际赠送比率
    * @param ACTUAL_FREE_PERCENT
    */
	public void setActualFreePercent(String value) {
        set(D_ActualFreePercent,value);
    }
	
   /**
    * 获取实际赠送比率
    * @return ACTUAL_FREE_PERCENT
    */
    public String getActualFreePercent() {
        return getString(D_ActualFreePercent);
    }

	/**
    * 设置审批时采购量
    * @param PASS_QTY
    */
	public void setPassQty(Double value) {
        set(D_PassQty,value);
    }
	
   /**
    * 获取审批时采购量
    * @return PASS_QTY
    */
    public Double getPassQty() {
        return getDouble(D_PassQty);
    }

	/**
    * 设置实际采购量
    * @param PURCHASE_QTY
    */
	public void setPurchaseQty(Double value) {
        set(D_PurchaseQty,value);
    }
	
   /**
    * 获取实际采购量
    * @return PURCHASE_QTY
    */
    public Double getPurchaseQty() {
        return getDouble(D_PurchaseQty);
    }

   /**
    * 设置赠料关联购料ID
    * @param FREE_RELATED_ID
    */
	public void setFreeRelatedId(Long value) {
        set(D_FreeRelatedId,value);
    }
	
	/**
    * 获取赠料关联购料ID
    * @return FREE_RELATED_ID
    */
    public Long getFreeRelatedId() {
        return getLong(D_FreeRelatedId);
    }

	/**
    * 设置是否是套餐[1]-否[2]-是
    * @param IS_PACKAGE
    */
	public void setIsPackage(String value) {
        set(D_IsPackage,value);
    }
	
   /**
    * 获取是否是套餐[1]-否[2]-是
    * @return IS_PACKAGE
    */
    public String getIsPackage() {
        return getString(D_IsPackage);
    }

	/**
    * 设置到库实际数量
    * @param ARRIVE_QTY
    */
	public void setArriveQty(Double value) {
        set(D_ArriveQty,value);
    }
	
   /**
    * 获取到库实际数量
    * @return ARRIVE_QTY
    */
    public Double getArriveQty() {
        return getDouble(D_ArriveQty);
    }

	/**
    * 设置[1]-集团采购[2]-个体采购
    * @param GROUP_OR_SELF
    */
	public void setGroupOrSelf(String value) {
        set(D_GroupOrSelf,value);
    }
	
   /**
    * 获取[1]-集团采购[2]-个体采购
    * @return GROUP_OR_SELF
    */
    public String getGroupOrSelf() {
        return getString(D_GroupOrSelf);
    }

   /**
    * 设置供应商
    * @param SUPPLIER_ID
    */
	public void setSupplierId(Long value) {
        set(D_SupplierId,value);
    }
	
	/**
    * 获取供应商
    * @return SUPPLIER_ID
    */
    public Long getSupplierId() {
        return getLong(D_SupplierId);
    }

   /**
    * 设置需求物料猪场
    * @param REQUIRE_FARM
    */
	public void setRequireFarm(Long value) {
        set(D_RequireFarm,value);
    }
	
	/**
    * 获取需求物料猪场
    * @return REQUIRE_FARM
    */
    public Long getRequireFarm() {
        return getLong(D_RequireFarm);
    }

	/**
    * 设置0元原因 [A]-购料 [B]-赠料 [C]-返利金额
    * @param REBATE_REASON
    */
	public void setRebateReason(String value) {
        set(D_RebateReason,value);
    }
	
   /**
    * 获取0元原因 [A]-购料 [B]-赠料 [C]-返利金额
    * @return REBATE_REASON
    */
    public String getRebateReason() {
        return getString(D_RebateReason);
    }

	/**
    * 设置返利金额
    * @param REBATE_PRICE
    */
	public void setRebatePrice(Double value) {
        set(D_RebatePrice,value);
    }
	
   /**
    * 获取返利金额
    * @return REBATE_PRICE
    */
    public Double getRebatePrice() {
        return getDouble(D_RebatePrice);
    }

   /**
    * 设置采购员ID
    * @param PURCHASER_ID
    */
	public void setPurchaserId(Long value) {
        set(D_PurchaserId,value);
    }
	
	/**
    * 获取采购员ID
    * @return PURCHASER_ID
    */
    public Long getPurchaserId() {
        return getLong(D_PurchaserId);
    }

	/**
    * 设置预计到货日期
    * @param ARRIVE_DATE
    */
	public void setArriveDate(Date value) {
        set(D_ArriveDate,value);
    }
	
   /**
    * 获取预计到货日期
    * @return ARRIVE_DATE
    */
    public Date getArriveDate() {
        return getDate(D_ArriveDate);
    }

	/**
    * 设置单据编号
    * @param BILL_CODE
    */
	public void setBillCode(String value) {
        set(D_BillCode,value);
    }
	
   /**
    * 获取单据编号
    * @return BILL_CODE
    */
    public String getBillCode() {
        return getString(D_BillCode);
    }

	/**
    * 设置单据日期
    * @param BILL_DATE
    */
	public void setBillDate(Date value) {
        set(D_BillDate,value);
    }
	
   /**
    * 获取单据日期
    * @return BILL_DATE
    */
    public Date getBillDate() {
        return getDate(D_BillDate);
    }

	/**
    * 设置单据事件(单据类型)
    * @param EVENT_CODE
    */
	public void setEventCode(String value) {
        set(D_EventCode,value);
    }
	
   /**
    * 获取单据事件(单据类型)
    * @return EVENT_CODE
    */
    public String getEventCode() {
        return getString(D_EventCode);
    }

   /**
    * 设置创建者(录单人员)
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建者(录单人员)
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
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("purchaseId");
        propertes.add("purchaseLn");
        propertes.add("freeLn");
        propertes.add("inputId");
        propertes.add("inputLn");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialSplit");
        propertes.add("actualPrice");
        propertes.add("actualFreePercent");
        propertes.add("passQty");
        propertes.add("purchaseQty");
        propertes.add("freeRelatedId");
        propertes.add("isPackage");
        propertes.add("arriveQty");
        propertes.add("groupOrSelf");
        propertes.add("supplierId");
        propertes.add("requireFarm");
        propertes.add("rebateReason");
        propertes.add("rebatePrice");
        propertes.add("purchaserId");
        propertes.add("arriveDate");
        propertes.add("billCode");
        propertes.add("billDate");
        propertes.add("eventCode");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




