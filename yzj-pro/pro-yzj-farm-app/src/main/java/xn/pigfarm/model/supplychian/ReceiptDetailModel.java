package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-12-21 13:40:46
 *       表：SC_M_BILL_RECEIPT_DETAIL 
 */
public class ReceiptDetailModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 2342161373083817698L;

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
	 //发票单据ID 
    private static final String D_ReceiptId="receiptId";
	 //发票单据行号 
    private static final String D_ReceiptLn="receiptLn";
	 //发票号 
    private static final String D_ReceiptNumber="receiptNumber";
	 //金额 
    private static final String D_Price="price";
	 //运费 
    private static final String D_TransportPrice="transportPrice";
	 //文件夹相对路径 
    private static final String D_RelativePath="relativePath";
	 //文件夹绝对路径 
    private static final String D_AbsolutePath="absolutePath";
	 //文件原名 
    private static final String D_FileOldName="fileOldName";
	 //文件系统名 
    private static final String D_FileName="fileName";
	 //文件后缀名 
    private static final String D_FileSuffix="fileSuffix";
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
    * 设置发票号
    * @param RECEIPT_NUMBER
    */
	public void setReceiptNumber(String value) {
        set(D_ReceiptNumber,value);
    }
	
   /**
    * 获取发票号
    * @return RECEIPT_NUMBER
    */
    public String getReceiptNumber() {
        return getString(D_ReceiptNumber);
    }

	/**
    * 设置金额
    * @param PRICE
    */
	public void setPrice(Double value) {
        set(D_Price,value);
    }
	
   /**
    * 获取金额
    * @return PRICE
    */
    public Double getPrice() {
        return getDouble(D_Price);
    }

	/**
    * 设置运费
    * @param TRANSPORT_PRICE
    */
	public void setTransportPrice(Double value) {
        set(D_TransportPrice,value);
    }
	
   /**
    * 获取运费
    * @return TRANSPORT_PRICE
    */
    public Double getTransportPrice() {
        return getDouble(D_TransportPrice);
    }

	/**
    * 设置文件夹相对路径
    * @param RELATIVE_PATH
    */
	public void setRelativePath(String value) {
        set(D_RelativePath,value);
    }
	
   /**
    * 获取文件夹相对路径
    * @return RELATIVE_PATH
    */
    public String getRelativePath() {
        return getString(D_RelativePath);
    }

	/**
    * 设置文件夹绝对路径
    * @param ABSOLUTE_PATH
    */
	public void setAbsolutePath(String value) {
        set(D_AbsolutePath,value);
    }
	
   /**
    * 获取文件夹绝对路径
    * @return ABSOLUTE_PATH
    */
    public String getAbsolutePath() {
        return getString(D_AbsolutePath);
    }

	/**
    * 设置文件原名
    * @param FILE_OLD_NAME
    */
	public void setFileOldName(String value) {
        set(D_FileOldName,value);
    }
	
   /**
    * 获取文件原名
    * @return FILE_OLD_NAME
    */
    public String getFileOldName() {
        return getString(D_FileOldName);
    }

	/**
    * 设置文件系统名
    * @param FILE_NAME
    */
	public void setFileName(String value) {
        set(D_FileName,value);
    }
	
   /**
    * 获取文件系统名
    * @return FILE_NAME
    */
    public String getFileName() {
        return getString(D_FileName);
    }

	/**
    * 设置文件后缀名
    * @param FILE_SUFFIX
    */
	public void setFileSuffix(String value) {
        set(D_FileSuffix,value);
    }
	
   /**
    * 获取文件后缀名
    * @return FILE_SUFFIX
    */
    public String getFileSuffix() {
        return getString(D_FileSuffix);
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
        propertes.add("receiptId");
        propertes.add("receiptLn");
        propertes.add("receiptNumber");
        propertes.add("price");
        propertes.add("transportPrice");
        propertes.add("relativePath");
        propertes.add("absolutePath");
        propertes.add("fileOldName");
        propertes.add("fileName");
        propertes.add("fileSuffix");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




