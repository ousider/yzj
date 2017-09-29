package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-21 11:36:50
 *       表：SC_R_RECEIPT_INPUT 
 */
public class ReceiptInputModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -3041812933067818590L;

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
	 //物料对应猪场ID 
    private static final String D_FarmId="farmId";
	 //发票单据ID 
    private static final String D_ReceiptId="receiptId";
	 //物料信息行号 
    private static final String D_MaterialInfoLn="materialInfoLn";
	 //入库单ID 
    private static final String D_InputId="inputId";
	 //入库单行号 
    private static final String D_InputLn="inputLn";
	 //物料ID 
    private static final String D_MaterialId="materialId";
	 //物料ONLY 
    private static final String D_MaterialOnly="materialOnly";
	 //物料BATCH 
    private static final String D_MaterialBatch="materialBatch";
	 //开票单价 
    private static final String D_ReceiptPrice="receiptPrice";
	 //开票数量 
    private static final String D_ReceiptQty="receiptQty";
	 //金额 
    private static final String D_TotalPrice="totalPrice";
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
    * 设置物料对应猪场ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取物料对应猪场ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
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
    * 设置物料信息行号
    * @param MATERIAL_INFO_LN
    */
	public void setMaterialInfoLn(Long value) {
        set(D_MaterialInfoLn,value);
    }
	
	/**
    * 获取物料信息行号
    * @return MATERIAL_INFO_LN
    */
    public Long getMaterialInfoLn() {
        return getLong(D_MaterialInfoLn);
    }

   /**
    * 设置入库单ID
    * @param INPUT_ID
    */
	public void setInputId(Long value) {
        set(D_InputId,value);
    }
	
	/**
    * 获取入库单ID
    * @return INPUT_ID
    */
    public Long getInputId() {
        return getLong(D_InputId);
    }

   /**
    * 设置入库单行号
    * @param INPUT_LN
    */
	public void setInputLn(Long value) {
        set(D_InputLn,value);
    }
	
	/**
    * 获取入库单行号
    * @return INPUT_LN
    */
    public Long getInputLn() {
        return getLong(D_InputLn);
    }

   /**
    * 设置物料ID
    * @param MATERIAL_ID
    */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
    * 获取物料ID
    * @return MATERIAL_ID
    */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

	/**
    * 设置物料ONLY
    * @param MATERIAL_ONLY
    */
	public void setMaterialOnly(String value) {
        set(D_MaterialOnly,value);
    }
	
   /**
    * 获取物料ONLY
    * @return MATERIAL_ONLY
    */
    public String getMaterialOnly() {
        return getString(D_MaterialOnly);
    }

	/**
    * 设置物料BATCH
    * @param MATERIAL_BATCH
    */
	public void setMaterialBatch(String value) {
        set(D_MaterialBatch,value);
    }
	
   /**
    * 获取物料BATCH
    * @return MATERIAL_BATCH
    */
    public String getMaterialBatch() {
        return getString(D_MaterialBatch);
    }

	/**
    * 设置开票单价
    * @param RECEIPT_PRICE
    */
	public void setReceiptPrice(Double value) {
        set(D_ReceiptPrice,value);
    }
	
   /**
    * 获取开票单价
    * @return RECEIPT_PRICE
    */
    public Double getReceiptPrice() {
        return getDouble(D_ReceiptPrice);
    }

	/**
    * 设置开票数量
    * @param RECEIPT_QTY
    */
	public void setReceiptQty(Double value) {
        set(D_ReceiptQty,value);
    }
	
   /**
    * 获取开票数量
    * @return RECEIPT_QTY
    */
    public Double getReceiptQty() {
        return getDouble(D_ReceiptQty);
    }

	/**
    * 设置金额
    * @param TOTAL_PRICE
    */
	public void setTotalPrice(Double value) {
        set(D_TotalPrice,value);
    }
	
   /**
    * 获取金额
    * @return TOTAL_PRICE
    */
    public Double getTotalPrice() {
        return getDouble(D_TotalPrice);
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
        propertes.add("receiptId");
        propertes.add("materialInfoLn");
        propertes.add("inputId");
        propertes.add("inputLn");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialBatch");
        propertes.add("receiptPrice");
        propertes.add("receiptQty");
        propertes.add("totalPrice");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




