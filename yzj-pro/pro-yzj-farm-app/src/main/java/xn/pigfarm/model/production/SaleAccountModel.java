package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-15 20:17:59
 *       表：PP_L_BILL_SALE_ACCOUNT
 */
public class SaleAccountModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 5426277915720676122L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // 记录删除标志: [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 公司Id
    private static final String D_CompanyId="companyId";

    // 猪场Id
    private static final String D_FarmId="farmId";

    // 单据编号
    private static final String D_BillCode="billCode";

    // 客户ID
    private static final String D_CustomerId="customerId";

    // 销售单据ID
    private static final String D_SaleBillId="saleBillId";

    // 销售单据总金额
    private static final String D_SaleBillTotalPrice="saleBillTotalPrice";

    // 结算日期
    private static final String D_SaleAccountDate="saleAccountDate";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 码单号
    private static final String D_BusinessCode="businessCode";

    // 毛猪头数
    private static final String D_PigNum="pigNum";

    // 毛猪总重
    private static final String D_PigWeight="pigWeight";

    // 挂牌价格
    private static final String D_NominalPrice="nominalPrice";

    // 胴体总重
    private static final String D_CarcassTotalWeight="carcassTotalWeight";

    // 原膘比例
    private static final String D_FatRate="fatRate";

    // 扣款合计
    private static final String D_TotalItemPrice="totalItemPrice";

    // 结算价
    private static final String D_TotalAccount="totalAccount";

    // 结算总金额
    private static final String D_TotalAccountPrice="totalAccountPrice";
	

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
     * 设置排序号
     * 
     * @param SORT_NBR
     */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
     * 获取排序号
     * 
     * @return SORT_NBR
     */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
     * 设置状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置记录删除标志: [0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取记录删除标志: [0]-未删除;[1]-逻辑删除
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
     * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @param ORIGIN_FLAG
     */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
    /**
     * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @return ORIGIN_FLAG
     */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
     * 设置数据来源应用的代码
     * 
     * @param ORIGIN_APP
     */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
    /**
     * 获取数据来源应用的代码
     * 
     * @return ORIGIN_APP
     */
    public String getOriginApp() {
        return getString(D_OriginApp);
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
     * 设置公司Id
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取公司Id
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置猪场Id
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取猪场Id
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

	/**
     * 设置单据编号
     * 
     * @param BILL_CODE
     */
	public void setBillCode(String value) {
        set(D_BillCode,value);
    }
	
    /**
     * 获取单据编号
     * 
     * @return BILL_CODE
     */
    public String getBillCode() {
        return getString(D_BillCode);
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
     * 设置销售单据ID
     * 
     * @param SALE_BILL_ID
     */
	public void setSaleBillId(Long value) {
        set(D_SaleBillId,value);
    }
	
	/**
     * 获取销售单据ID
     * 
     * @return SALE_BILL_ID
     */
    public Long getSaleBillId() {
        return getLong(D_SaleBillId);
    }

	/**
     * 设置销售单据总金额
     * 
     * @param SALE_BILL_TOTAL_PRICE
     */
	public void setSaleBillTotalPrice(Double value) {
        set(D_SaleBillTotalPrice,value);
    }
	
    /**
     * 获取销售单据总金额
     * 
     * @return SALE_BILL_TOTAL_PRICE
     */
    public Double getSaleBillTotalPrice() {
        return getDouble(D_SaleBillTotalPrice);
    }

	/**
     * 设置结算日期
     * 
     * @param SALE_ACCOUNT_DATE
     */
	public void setSaleAccountDate(Date value) {
        set(D_SaleAccountDate,value);
    }
	
    /**
     * 获取结算日期
     * 
     * @return SALE_ACCOUNT_DATE
     */
    public Date getSaleAccountDate() {
        return getDate(D_SaleAccountDate);
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
     * 设置码单号
     * 
     * @param BUSINESS_CODE
     */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
    /**
     * 获取码单号
     * 
     * @return BUSINESS_CODE
     */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

    /**
     * 设置毛猪头数
     * 
     * @param PIG_NUM
     */
	public void setPigNum(Long value) {
        set(D_PigNum,value);
    }
	
	/**
     * 获取毛猪头数
     * 
     * @return PIG_NUM
     */
    public Long getPigNum() {
        return getLong(D_PigNum);
    }

	/**
     * 设置毛猪总重
     * 
     * @param PIG_WEIGHT
     */
	public void setPigWeight(Double value) {
        set(D_PigWeight,value);
    }
	
    /**
     * 获取毛猪总重
     * 
     * @return PIG_WEIGHT
     */
    public Double getPigWeight() {
        return getDouble(D_PigWeight);
    }

	/**
     * 设置挂牌价格
     * 
     * @param NOMINAL_PRICE
     */
	public void setNominalPrice(Double value) {
        set(D_NominalPrice,value);
    }
	
    /**
     * 获取挂牌价格
     * 
     * @return NOMINAL_PRICE
     */
    public Double getNominalPrice() {
        return getDouble(D_NominalPrice);
    }

	/**
     * 设置胴体总重
     * 
     * @param CARCASS_TOTAL_WEIGHT
     */
	public void setCarcassTotalWeight(Double value) {
        set(D_CarcassTotalWeight,value);
    }
	
    /**
     * 获取胴体总重
     * 
     * @return CARCASS_TOTAL_WEIGHT
     */
    public Double getCarcassTotalWeight() {
        return getDouble(D_CarcassTotalWeight);
    }

	/**
     * 设置原膘比例
     * 
     * @param FAT_RATE
     */
	public void setFatRate(Double value) {
        set(D_FatRate,value);
    }
	
    /**
     * 获取原膘比例
     * 
     * @return FAT_RATE
     */
    public Double getFatRate() {
        return getDouble(D_FatRate);
    }

	/**
     * 设置扣款合计
     * 
     * @param TOTAL_ITEM_PRICE
     */
	public void setTotalItemPrice(Double value) {
        set(D_TotalItemPrice,value);
    }
	
    /**
     * 获取扣款合计
     * 
     * @return TOTAL_ITEM_PRICE
     */
    public Double getTotalItemPrice() {
        return getDouble(D_TotalItemPrice);
    }

	/**
     * 设置结算价
     * 
     * @param TOTAL_ACCOUNT
     */
	public void setTotalAccount(Double value) {
        set(D_TotalAccount,value);
    }
	
    /**
     * 获取结算价
     * 
     * @return TOTAL_ACCOUNT
     */
    public Double getTotalAccount() {
        return getDouble(D_TotalAccount);
    }

	/**
     * 设置结算总金额
     * 
     * @param TOTAL_ACCOUNT_PRICE
     */
	public void setTotalAccountPrice(Double value) {
        set(D_TotalAccountPrice,value);
    }
	
    /**
     * 获取结算总金额
     * 
     * @return TOTAL_ACCOUNT_PRICE
     */
    public Double getTotalAccountPrice() {
        return getDouble(D_TotalAccountPrice);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("companyId");
        propertes.add("farmId");
        propertes.add("billCode");
        propertes.add("customerId");
        propertes.add("saleBillId");
        propertes.add("saleBillTotalPrice");
        propertes.add("saleAccountDate");
        propertes.add("createDate");
        propertes.add("businessCode");
        propertes.add("pigNum");
        propertes.add("pigWeight");
        propertes.add("nominalPrice");
        propertes.add("carcassTotalWeight");
        propertes.add("fatRate");
        propertes.add("totalItemPrice");
        propertes.add("totalAccount");
        propertes.add("totalAccountPrice");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




