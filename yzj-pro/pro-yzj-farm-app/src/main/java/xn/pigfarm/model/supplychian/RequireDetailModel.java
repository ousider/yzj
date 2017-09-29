package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-20 13:37:15
 *       表：SC_M_BILL_REQUIRE_DETAIL 
 */
public class RequireDetailModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -6439623907516688277L;

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
	 //需求单据ID 
    private static final String D_RequireId="requireId";
	 //需求单据行号 
    private static final String D_RequireLn="requireLn";
	 //采购单据ID 
    private static final String D_PurchaseId="purchaseId";
	 //采购行号 
    private static final String D_PurchaseLn="purchaseLn";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //物料生成号 
    private static final String D_MaterialOnly="materialOnly";
	 //物料拆分号 
    private static final String D_MaterialSplit="materialSplit";
	 //当时库存量 
    private static final String D_ExistsQty="existsQty";
	 //需求量 
    private static final String D_RequireQty="requireQty";
	 //申请人 
    private static final String D_ApplyId="applyId";
	 //申请单位ID 
    private static final String D_ApplyUnitId="applyUnitId";
	 //核算单位 
    private static final String D_AccountsUnitId="accountsUnitId";
	 //申请类型：参考物料大类 
    private static final String D_ApplyType="applyType";
	 //[1]-普通[2]-紧急 
    private static final String D_EmergencyType="emergencyType";
	 //需求单类型[1]-猪场,[2]-添加药,[3]-实验室 
    private static final String D_RequireType="requireType";
	 //单据编号 
    private static final String D_BillCode="billCode";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //单据事件(单据类型) 
    private static final String D_EventCode="eventCode";
	 //创建者 
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
    * 设置需求单据ID
    * @param REQUIRE_ID
    */
	public void setRequireId(Long value) {
        set(D_RequireId,value);
    }
	
	/**
    * 获取需求单据ID
    * @return REQUIRE_ID
    */
    public Long getRequireId() {
        return getLong(D_RequireId);
    }

   /**
    * 设置需求单据行号
    * @param REQUIRE_LN
    */
	public void setRequireLn(Long value) {
        set(D_RequireLn,value);
    }
	
	/**
    * 获取需求单据行号
    * @return REQUIRE_LN
    */
    public Long getRequireLn() {
        return getLong(D_RequireLn);
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
    * 设置当时库存量
    * @param EXISTS_QTY
    */
	public void setExistsQty(Double value) {
        set(D_ExistsQty,value);
    }
	
   /**
    * 获取当时库存量
    * @return EXISTS_QTY
    */
    public Double getExistsQty() {
        return getDouble(D_ExistsQty);
    }

	/**
    * 设置需求量
    * @param REQUIRE_QTY
    */
	public void setRequireQty(Double value) {
        set(D_RequireQty,value);
    }
	
   /**
    * 获取需求量
    * @return REQUIRE_QTY
    */
    public Double getRequireQty() {
        return getDouble(D_RequireQty);
    }

   /**
    * 设置申请人
    * @param APPLY_ID
    */
	public void setApplyId(Long value) {
        set(D_ApplyId,value);
    }
	
	/**
    * 获取申请人
    * @return APPLY_ID
    */
    public Long getApplyId() {
        return getLong(D_ApplyId);
    }

   /**
    * 设置申请单位ID
    * @param APPLY_UNIT_ID
    */
	public void setApplyUnitId(Long value) {
        set(D_ApplyUnitId,value);
    }
	
	/**
    * 获取申请单位ID
    * @return APPLY_UNIT_ID
    */
    public Long getApplyUnitId() {
        return getLong(D_ApplyUnitId);
    }

   /**
    * 设置核算单位
    * @param ACCOUNTS_UNIT_ID
    */
	public void setAccountsUnitId(Long value) {
        set(D_AccountsUnitId,value);
    }
	
	/**
    * 获取核算单位
    * @return ACCOUNTS_UNIT_ID
    */
    public Long getAccountsUnitId() {
        return getLong(D_AccountsUnitId);
    }

	/**
    * 设置申请类型：参考物料大类
    * @param APPLY_TYPE
    */
	public void setApplyType(String value) {
        set(D_ApplyType,value);
    }
	
   /**
    * 获取申请类型：参考物料大类
    * @return APPLY_TYPE
    */
    public String getApplyType() {
        return getString(D_ApplyType);
    }

	/**
    * 设置[1]-普通[2]-紧急
    * @param EMERGENCY_TYPE
    */
	public void setEmergencyType(String value) {
        set(D_EmergencyType,value);
    }
	
   /**
    * 获取[1]-普通[2]-紧急
    * @return EMERGENCY_TYPE
    */
    public String getEmergencyType() {
        return getString(D_EmergencyType);
    }

	/**
    * 设置需求单类型[1]-猪场,[2]-添加药,[3]-实验室
    * @param REQUIRE_TYPE
    */
	public void setRequireType(String value) {
        set(D_RequireType,value);
    }
	
   /**
    * 获取需求单类型[1]-猪场,[2]-添加药,[3]-实验室
    * @return REQUIRE_TYPE
    */
    public String getRequireType() {
        return getString(D_RequireType);
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
    * 设置创建者
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建者
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
        propertes.add("requireId");
        propertes.add("requireLn");
        propertes.add("purchaseId");
        propertes.add("purchaseLn");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialSplit");
        propertes.add("existsQty");
        propertes.add("requireQty");
        propertes.add("applyId");
        propertes.add("applyUnitId");
        propertes.add("accountsUnitId");
        propertes.add("applyType");
        propertes.add("emergencyType");
        propertes.add("requireType");
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




