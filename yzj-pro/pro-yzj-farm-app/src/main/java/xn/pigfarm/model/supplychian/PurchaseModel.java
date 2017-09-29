package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-2-15 14:07:49
 *       表：SC_M_BILL_PURCHASE 
 */
public class PurchaseModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -743146470750736933L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[1]-待审批[2]-审批未通过[3]-审批通过[4]-入库中[5]-完成[6]-报废[7]完成[8]完结 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //单据编号 
    private static final String D_BillCode="billCode";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //单据事件(单据类型) 
    private static final String D_EventCode="eventCode";
	 //[1]物料采购[2]猪只采购 
    private static final String D_BillType="billType";
	 //供应商ID 
    private static final String D_SupplierId="supplierId";
	 //预计到货日期 
    private static final String D_ArriveDate="arriveDate";
	 //采购员ID 
    private static final String D_PurchaserId="purchaserId";
	 //[1]-集团采购[2]-个体采购 
    private static final String D_GroupOrSelf="groupOrSelf";
	 //原采购表ID 单据完结 自动补单关联ID 
    private static final String D_OriginalPurchaseId="originalPurchaseId";
	 //创建者 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //申请人OA账号 
    private static final String D_OaUsername="oaUsername";
	 //审核未通过原因 
    private static final String D_ReviewFailReason="reviewFailReason";
	 //OA审核用01 
    private static final String D_FtriggerFlag="ftriggerFlag";
	 //OA审核用02 
    private static final String D_Requestid="requestid";
	

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
    * 设置[1]-待审批[2]-审批未通过[3]-审批通过[4]-入库中[5]-完成[6]-报废[7]完成[8]完结
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]-待审批[2]-审批未通过[3]-审批通过[4]-入库中[5]-完成[6]-报废[7]完成[8]完结
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
    * 设置[1]物料采购[2]猪只采购
    * @param BILL_TYPE
    */
	public void setBillType(String value) {
        set(D_BillType,value);
    }
	
   /**
    * 获取[1]物料采购[2]猪只采购
    * @return BILL_TYPE
    */
    public String getBillType() {
        return getString(D_BillType);
    }

   /**
    * 设置供应商ID
    * @param SUPPLIER_ID
    */
	public void setSupplierId(Long value) {
        set(D_SupplierId,value);
    }
	
	/**
    * 获取供应商ID
    * @return SUPPLIER_ID
    */
    public Long getSupplierId() {
        return getLong(D_SupplierId);
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
    * 设置原采购表ID 单据完结 自动补单关联ID
    * @param ORIGINAL_PURCHASE_ID
    */
	public void setOriginalPurchaseId(Long value) {
        set(D_OriginalPurchaseId,value);
    }
	
	/**
    * 获取原采购表ID 单据完结 自动补单关联ID
    * @return ORIGINAL_PURCHASE_ID
    */
    public Long getOriginalPurchaseId() {
        return getLong(D_OriginalPurchaseId);
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
        return getTimestamp(D_CreateDate);
    }

	/**
    * 设置申请人OA账号
    * @param OA_USERNAME
    */
	public void setOaUsername(String value) {
        set(D_OaUsername,value);
    }
	
   /**
    * 获取申请人OA账号
    * @return OA_USERNAME
    */
    public String getOaUsername() {
        return getString(D_OaUsername);
    }

	/**
    * 设置审核未通过原因
    * @param REVIEW_FAIL_REASON
    */
	public void setReviewFailReason(String value) {
        set(D_ReviewFailReason,value);
    }
	
   /**
    * 获取审核未通过原因
    * @return REVIEW_FAIL_REASON
    */
    public String getReviewFailReason() {
        return getString(D_ReviewFailReason);
    }

   /**
    * 设置OA审核用01
    * @param FtriggerFlag
    */
	public void setFtriggerFlag(Long value) {
        set(D_FtriggerFlag,value);
    }
	
	/**
    * 获取OA审核用01
    * @return FtriggerFlag
    */
    public Long getFtriggerFlag() {
        return getLong(D_FtriggerFlag);
    }

   /**
    * 设置OA审核用02
    * @param requestid
    */
	public void setRequestid(Long value) {
        set(D_Requestid,value);
    }
	
	/**
    * 获取OA审核用02
    * @return requestid
    */
    public Long getRequestid() {
        return getLong(D_Requestid);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("billCode");
        propertes.add("billDate");
        propertes.add("eventCode");
        propertes.add("billType");
        propertes.add("supplierId");
        propertes.add("arriveDate");
        propertes.add("purchaserId");
        propertes.add("groupOrSelf");
        propertes.add("originalPurchaseId");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("oaUsername");
        propertes.add("reviewFailReason");
        propertes.add("ftriggerFlag");
        propertes.add("requestid");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




