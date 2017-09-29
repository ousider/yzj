package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-21 9:28:31
 *       表：SC_M_BILL_RECEIPT 
 */
public class ReceiptModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 7428481698871730790L;

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
	 //供应商ID 
    private static final String D_SupplierId="supplierId";
	 //发票法人公司ID 
    private static final String D_ReceiptBranchId="receiptBranchId";
	 //发票猪场ID 
    private static final String D_ReceiptFarmId="receiptFarmId";
	 //填写人ID 
    private static final String D_InputerId="inputerId";
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
    * 设置发票法人公司ID
    * @param RECEIPT_BRANCH_ID
    */
	public void setReceiptBranchId(Long value) {
        set(D_ReceiptBranchId,value);
    }
	
	/**
    * 获取发票法人公司ID
    * @return RECEIPT_BRANCH_ID
    */
    public Long getReceiptBranchId() {
        return getLong(D_ReceiptBranchId);
    }

   /**
    * 设置发票猪场ID
    * @param RECEIPT_FARM_ID
    */
	public void setReceiptFarmId(Long value) {
        set(D_ReceiptFarmId,value);
    }
	
	/**
    * 获取发票猪场ID
    * @return RECEIPT_FARM_ID
    */
    public Long getReceiptFarmId() {
        return getLong(D_ReceiptFarmId);
    }

   /**
    * 设置填写人ID
    * @param INPUTER_ID
    */
	public void setInputerId(Long value) {
        set(D_InputerId,value);
    }
	
	/**
    * 获取填写人ID
    * @return INPUTER_ID
    */
    public Long getInputerId() {
        return getLong(D_InputerId);
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
        propertes.add("supplierId");
        propertes.add("receiptBranchId");
        propertes.add("receiptFarmId");
        propertes.add("inputerId");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




