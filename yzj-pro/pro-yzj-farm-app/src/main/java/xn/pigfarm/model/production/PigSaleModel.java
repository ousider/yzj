package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-29 15:27:42
 *       表：PP_L_BILL_PIG_SALE
 */
public class PigSaleModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 2243780725645615792L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 备注
    private static final String D_Notes="notes";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 单据ID
    private static final String D_BillId="billId";

    // 费用
    private static final String D_OtherFee="otherFee";

    // 货款差异
    private static final String D_PaymentDiff="paymentDiff";
	 //saleType 
    private static final String D_SaleType="saleType";

    // 客户ID
    private static final String D_CustomerId="customerId";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 销售单类型 1：内销 2：外销
    private static final String D_SaleBillType="saleBillType";

    // 销售单状态
    private static final String D_SaleStatus="saleStatus";

    // 业务员
    private static final String D_Salesman="salesman";

    // SAP销售类型
    private static final String D_SapSaleType="sapSaleType";
	

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
     * 获取行号: 系统保留字段，标识一条数据记录。
     * 
     * @return ROW_ID
     */
    public Long getRowId() {
        return getLong(D_RowId);
    }

	/**
     * 设置备注
     * 
     * @param NOTES
     */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
    /**
     * 获取备注
     * 
     * @return NOTES
     */
    public String getNotes() {
        return getString(D_Notes);
    }

	/**
     * 设置表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置[0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取[0]-未删除;[1]-逻辑删除
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

    /**
     * 设置对应的公司ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取对应的公司ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置companyId
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取companyId
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置单据ID
     * 
     * @param BILL_ID
     */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
     * 获取单据ID
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

	/**
     * 设置费用
     * 
     * @param OTHER_FEE
     */
	public void setOtherFee(Double value) {
        set(D_OtherFee,value);
    }
	
    /**
     * 获取费用
     * 
     * @return OTHER_FEE
     */
    public Double getOtherFee() {
        return getDouble(D_OtherFee);
    }

	/**
     * 设置货款差异
     * 
     * @param PAYMENT_DIFF
     */
	public void setPaymentDiff(Double value) {
        set(D_PaymentDiff,value);
    }
	
    /**
     * 获取货款差异
     * 
     * @return PAYMENT_DIFF
     */
    public Double getPaymentDiff() {
        return getDouble(D_PaymentDiff);
    }

	/**
     * 设置saleType
     * 
     * @param SALE_TYPE
     */
	public void setSaleType(String value) {
        set(D_SaleType,value);
    }
	
    /**
     * 获取saleType
     * 
     * @return SALE_TYPE
     */
    public String getSaleType() {
        return getString(D_SaleType);
    }

    /**
     * 设置客户ID
     * 
     * @param CUSTOMER_ID
     */
	public void setCustomerId(Long value) {
        set(D_CustomerId,value);
    }
	
	/**
     * 获取客户ID
     * 
     * @return CUSTOMER_ID
     */
    public Long getCustomerId() {
        return getLong(D_CustomerId);
    }

    /**
     * 设置创建人
     * 
     * @param CREATE_ID
     */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
     * 获取创建人
     * 
     * @return CREATE_ID
     */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
     * 设置创建日期
     * 
     * @param CREATE_DATE
     */
    public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }

    /**
     * 设置销售单类型 1：内销 2：外销
     * 
     * @param SALE_BILL_TYPE
     */
	public void setSaleBillType(Long value) {
        set(D_SaleBillType,value);
    }
	
	/**
     * 获取销售单类型 1：内销 2：外销
     * 
     * @return SALE_BILL_TYPE
     */
    public Long getSaleBillType() {
        return getLong(D_SaleBillType);
    }

	/**
     * 设置销售单状态
     * 
     * @param SALE_STATUS
     */
	public void setSaleStatus(String value) {
        set(D_SaleStatus,value);
    }
	
    /**
     * 获取销售单状态
     * 
     * @return SALE_STATUS
     */
    public String getSaleStatus() {
        return getString(D_SaleStatus);
    }

    /**
     * 设置业务员
     * 
     * @param SALESMAN
     */
	public void setSalesman(Long value) {
        set(D_Salesman,value);
    }
	
	/**
     * 获取业务员
     * 
     * @return SALESMAN
     */
    public Long getSalesman() {
        return getLong(D_Salesman);
    }

	/**
     * 设置SAP销售类型
     * 
     * @param SAP_SALE_TYPE
     */
	public void setSapSaleType(String value) {
        set(D_SapSaleType,value);
    }
	
    /**
     * 获取SAP销售类型
     * 
     * @return SAP_SALE_TYPE
     */
    public String getSapSaleType() {
        return getString(D_SapSaleType);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("notes");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("billId");
        propertes.add("otherFee");
        propertes.add("paymentDiff");
        propertes.add("saleType");
        propertes.add("customerId");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("saleBillType");
        propertes.add("saleStatus");
        propertes.add("salesman");
        propertes.add("sapSaleType");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




