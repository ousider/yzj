package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-21 14:48:14
 *       表：SC_M_BILL_INPUT_DETAIL 
 */
public class InputDetailModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 6124677033511586518L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //状态 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除(作废) 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //入库ID 
    private static final String D_InputId="inputId";
	 //入库行号 
    private static final String D_InputLn="inputLn";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //物料生成号 
    private static final String D_MaterialOnly="materialOnly";
	 //物料批次号 
    private static final String D_MaterialBatch="materialBatch";
	 //入库数量 
    private static final String D_InputQty="inputQty";
	 //入库仓库ID(到货入库,退货入库,反报废) 
    private static final String D_InputWarehouseId="inputWarehouseId";
	 //入库人员(到货入库,退货入库,反报废) 
    private static final String D_InputerId="inputerId";
	 //单据编号 
    private static final String D_BillCode="billCode";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //单据事件(单据类型) 
    private static final String D_EventCode="eventCode";
	 //发票单据ID 
    private static final String D_ReceiptId="receiptId";
	 //发票单据行号 
    private static final String D_ReceiptLn="receiptLn";
	 //已开票数量 
    private static final String D_ReceiptQty="receiptQty";
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
    * 设置状态
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取状态
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
    * 设置[0]-未删除;[1]-逻辑删除(作废)
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取[0]-未删除;[1]-逻辑删除(作废)
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
    * 设置入库ID
    * @param INPUT_ID
    */
	public void setInputId(Long value) {
        set(D_InputId,value);
    }
	
	/**
    * 获取入库ID
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
    * 设置入库数量
    * @param INPUT_QTY
    */
	public void setInputQty(Double value) {
        set(D_InputQty,value);
    }
	
   /**
    * 获取入库数量
    * @return INPUT_QTY
    */
    public Double getInputQty() {
        return getDouble(D_InputQty);
    }

   /**
    * 设置入库仓库ID(到货入库,退货入库,反报废)
    * @param INPUT_WAREHOUSE_ID
    */
	public void setInputWarehouseId(Long value) {
        set(D_InputWarehouseId,value);
    }
	
	/**
    * 获取入库仓库ID(到货入库,退货入库,反报废)
    * @return INPUT_WAREHOUSE_ID
    */
    public Long getInputWarehouseId() {
        return getLong(D_InputWarehouseId);
    }

   /**
    * 设置入库人员(到货入库,退货入库,反报废)
    * @param INPUTER_ID
    */
	public void setInputerId(Long value) {
        set(D_InputerId,value);
    }
	
	/**
    * 获取入库人员(到货入库,退货入库,反报废)
    * @return INPUTER_ID
    */
    public Long getInputerId() {
        return getLong(D_InputerId);
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
    * 设置发票单据ID
    * @param RECEIPT_ID
    */
	public void setReceiptId(Long value) {
        set(D_ReceiptId,value);
    }
	
	/**
    * 获取发票单据ID
    * @return RECEIPT_ID
    */
    public Long getReceiptId() {
        return getLong(D_ReceiptId);
    }

   /**
    * 设置发票单据行号
    * @param RECEIPT_LN
    */
	public void setReceiptLn(Long value) {
        set(D_ReceiptLn,value);
    }
	
	/**
    * 获取发票单据行号
    * @return RECEIPT_LN
    */
    public Long getReceiptLn() {
        return getLong(D_ReceiptLn);
    }

	/**
    * 设置已开票数量
    * @param RECEIPT_QTY
    */
	public void setReceiptQty(Double value) {
        set(D_ReceiptQty,value);
    }
	
   /**
    * 获取已开票数量
    * @return RECEIPT_QTY
    */
    public Double getReceiptQty() {
        return getDouble(D_ReceiptQty);
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
        propertes.add("inputId");
        propertes.add("inputLn");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialBatch");
        propertes.add("inputQty");
        propertes.add("inputWarehouseId");
        propertes.add("inputerId");
        propertes.add("billCode");
        propertes.add("billDate");
        propertes.add("eventCode");
        propertes.add("receiptId");
        propertes.add("receiptLn");
        propertes.add("receiptQty");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




