package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-30 11:26:09
 *       表：HR_R_SRM
 */
public class SrmModel  extends BaseDataModel implements Serializable{

    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -685086341979689122L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 供应商、客户ID
    private static final String D_CussupId="cussupId";

    // 客户、供应商类型，C(客户)/S（供应商）
    private static final String D_CussupType="cussupType";

    // 科目
    private static final String D_SubjectId="subjectId";

    // 创建类型 1：自建 2：已建供应商（客户）3：平台中公司
    private static final String D_CreateType="createType";

    // 是否销售结算(Y结算/N不结算）
    private static final String D_IsSaleAccount="isSaleAccount";

    // 公司法人
    private static final String D_LegalPerson="legalPerson";

    // 供应商类型
    private static final String D_SupplierType="supplierType";

    // 业务伙伴类型
    private static final String D_BusinessPartnerType="businessPartnerType";

    // 交易币种
    private static final String D_TradeCurrency="tradeCurrency";

    // 付款条件
    private static final String D_PayCondition="payCondition";

    // 付款账期
    private static final String D_PayDays="payDays";

    // 信用额度
    private static final String D_CreditLimit="creditLimit";

    // 版本号
    private static final String D_Version="version";

    // 客户类型
    private static final String D_CustomerType="customerType";

    // 母猪规模
    private static final String D_SowSize="sowSize";

    // 客户等级
    private static final String D_CustomerLevel="customerLevel";

    // 经销/直销
    private static final String D_SellType="sellType";

    // 销售大区
    private static final String D_SellDivision="sellDivision";

    // 销售片区
    private static final String D_SellArea="sellArea";

    // 业务员
    private static final String D_Salesman="salesman";

    // 每月固定结算日
    private static final String D_FixedBalanceDay="fixedBalanceDay";

    // 收款条件
    private static final String D_CollectCondition="collectCondition";

    // 收款账期
    private static final String D_CollectDays="collectDays";

    // 财务管理单元
    private static final String D_FinanceArea="financeArea";

    // 上级客户
    private static final String D_Customerparent="customerparent";
	

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
     * 设置供应商、客户ID
     * 
     * @param CUSSUP_ID
     */
	public void setCussupId(Long value) {
        set(D_CussupId,value);
    }
	
	/**
     * 获取供应商、客户ID
     * 
     * @return CUSSUP_ID
     */
    public Long getCussupId() {
        return getLong(D_CussupId);
    }

	/**
     * 设置客户、供应商类型，C(客户)/S（供应商）
     * 
     * @param CUSSUP_TYPE
     */
	public void setCussupType(String value) {
        set(D_CussupType,value);
    }
	
    /**
     * 获取客户、供应商类型，C(客户)/S（供应商）
     * 
     * @return CUSSUP_TYPE
     */
    public String getCussupType() {
        return getString(D_CussupType);
    }

    /**
     * 设置科目
     * 
     * @param SUBJECT_ID
     */
	public void setSubjectId(Long value) {
        set(D_SubjectId,value);
    }
	
	/**
     * 获取科目
     * 
     * @return SUBJECT_ID
     */
    public Long getSubjectId() {
        return getLong(D_SubjectId);
    }

	/**
     * 设置创建类型 1：自建 2：已建供应商（客户）3：平台中公司
     * 
     * @param CREATE_TYPE
     */
	public void setCreateType(String value) {
        set(D_CreateType,value);
    }
	
    /**
     * 获取创建类型 1：自建 2：已建供应商（客户）3：平台中公司
     * 
     * @return CREATE_TYPE
     */
    public String getCreateType() {
        return getString(D_CreateType);
    }

	/**
     * 设置是否销售结算(Y结算/N不结算）
     * 
     * @param IS_SALE_ACCOUNT
     */
	public void setIsSaleAccount(String value) {
        set(D_IsSaleAccount,value);
    }
	
    /**
     * 获取是否销售结算(Y结算/N不结算）
     * 
     * @return IS_SALE_ACCOUNT
     */
    public String getIsSaleAccount() {
        return getString(D_IsSaleAccount);
    }

	/**
     * 设置公司法人
     * 
     * @param LEGAL_PERSON
     */
	public void setLegalPerson(String value) {
        set(D_LegalPerson,value);
    }
	
    /**
     * 获取公司法人
     * 
     * @return LEGAL_PERSON
     */
    public String getLegalPerson() {
        return getString(D_LegalPerson);
    }

	/**
     * 设置供应商类型
     * 
     * @param SUPPLIER_TYPE
     */
	public void setSupplierType(String value) {
        set(D_SupplierType,value);
    }
	
    /**
     * 获取供应商类型
     * 
     * @return SUPPLIER_TYPE
     */
    public String getSupplierType() {
        return getString(D_SupplierType);
    }

	/**
     * 设置业务伙伴类型
     * 
     * @param BUSINESS_PARTNER_TYPE
     */
	public void setBusinessPartnerType(String value) {
        set(D_BusinessPartnerType,value);
    }
	
    /**
     * 获取业务伙伴类型
     * 
     * @return BUSINESS_PARTNER_TYPE
     */
    public String getBusinessPartnerType() {
        return getString(D_BusinessPartnerType);
    }

	/**
     * 设置交易币种
     * 
     * @param TRADE_CURRENCY
     */
	public void setTradeCurrency(String value) {
        set(D_TradeCurrency,value);
    }
	
    /**
     * 获取交易币种
     * 
     * @return TRADE_CURRENCY
     */
    public String getTradeCurrency() {
        return getString(D_TradeCurrency);
    }

	/**
     * 设置付款条件
     * 
     * @param PAY_CONDITION
     */
	public void setPayCondition(String value) {
        set(D_PayCondition,value);
    }
	
    /**
     * 获取付款条件
     * 
     * @return PAY_CONDITION
     */
    public String getPayCondition() {
        return getString(D_PayCondition);
    }

	/**
     * 设置付款账期
     * 
     * @param PAY_DAYS
     */
	public void setPayDays(String value) {
        set(D_PayDays,value);
    }
	
    /**
     * 获取付款账期
     * 
     * @return PAY_DAYS
     */
    public String getPayDays() {
        return getString(D_PayDays);
    }

    /**
     * 设置信用额度
     * 
     * @param CREDIT_LIMIT
     */
	public void setCreditLimit(Long value) {
        set(D_CreditLimit,value);
    }
	
	/**
     * 获取信用额度
     * 
     * @return CREDIT_LIMIT
     */
    public Long getCreditLimit() {
        return getLong(D_CreditLimit);
    }

    /**
     * 设置版本号
     * 
     * @param VERSION
     */
	public void setVersion(Long value) {
        set(D_Version,value);
    }
	
	/**
     * 获取版本号
     * 
     * @return VERSION
     */
    public Long getVersion() {
        return getLong(D_Version);
    }

	/**
     * 设置客户类型
     * 
     * @param CUSTOMER_TYPE
     */
	public void setCustomerType(String value) {
        set(D_CustomerType,value);
    }
	
    /**
     * 获取客户类型
     * 
     * @return CUSTOMER_TYPE
     */
    public String getCustomerType() {
        return getString(D_CustomerType);
    }

    /**
     * 设置母猪规模
     * 
     * @param SOW_SIZE
     */
	public void setSowSize(Long value) {
        set(D_SowSize,value);
    }
	
	/**
     * 获取母猪规模
     * 
     * @return SOW_SIZE
     */
    public Long getSowSize() {
        return getLong(D_SowSize);
    }

	/**
     * 设置客户等级
     * 
     * @param CUSTOMER_LEVEL
     */
	public void setCustomerLevel(String value) {
        set(D_CustomerLevel,value);
    }
	
    /**
     * 获取客户等级
     * 
     * @return CUSTOMER_LEVEL
     */
    public String getCustomerLevel() {
        return getString(D_CustomerLevel);
    }

	/**
     * 设置经销/直销
     * 
     * @param SELL_TYPE
     */
	public void setSellType(String value) {
        set(D_SellType,value);
    }
	
    /**
     * 获取经销/直销
     * 
     * @return SELL_TYPE
     */
    public String getSellType() {
        return getString(D_SellType);
    }

    /**
     * 设置销售大区
     * 
     * @param SELL_DIVISION
     */
	public void setSellDivision(Long value) {
        set(D_SellDivision,value);
    }
	
	/**
     * 获取销售大区
     * 
     * @return SELL_DIVISION
     */
    public Long getSellDivision() {
        return getLong(D_SellDivision);
    }

    /**
     * 设置销售片区
     * 
     * @param SELL_AREA
     */
	public void setSellArea(Long value) {
        set(D_SellArea,value);
    }
	
	/**
     * 获取销售片区
     * 
     * @return SELL_AREA
     */
    public Long getSellArea() {
        return getLong(D_SellArea);
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
     * 设置每月固定结算日
     * 
     * @param FIXED_BALANCE_DAY
     */
	public void setFixedBalanceDay(Long value) {
        set(D_FixedBalanceDay,value);
    }
	
	/**
     * 获取每月固定结算日
     * 
     * @return FIXED_BALANCE_DAY
     */
    public Long getFixedBalanceDay() {
        return getLong(D_FixedBalanceDay);
    }

	/**
     * 设置收款条件
     * 
     * @param COLLECT_CONDITION
     */
	public void setCollectCondition(String value) {
        set(D_CollectCondition,value);
    }
	
    /**
     * 获取收款条件
     * 
     * @return COLLECT_CONDITION
     */
    public String getCollectCondition() {
        return getString(D_CollectCondition);
    }

	/**
     * 设置收款账期
     * 
     * @param COLLECT_DAYS
     */
	public void setCollectDays(String value) {
        set(D_CollectDays,value);
    }
	
    /**
     * 获取收款账期
     * 
     * @return COLLECT_DAYS
     */
    public String getCollectDays() {
        return getString(D_CollectDays);
    }

    /**
     * 设置财务管理单元
     * 
     * @param FINANCE_AREA
     */
	public void setFinanceArea(Long value) {
        set(D_FinanceArea,value);
    }
	
	/**
     * 获取财务管理单元
     * 
     * @return FINANCE_AREA
     */
    public Long getFinanceArea() {
        return getLong(D_FinanceArea);
    }

    /**
     * 设置上级客户
     * 
     * @param CUSTOMERPARENT
     */
	public void setCustomerparent(Long value) {
        set(D_Customerparent,value);
    }
	
	/**
     * 获取上级客户
     * 
     * @return CUSTOMERPARENT
     */
    public Long getCustomerparent() {
        return getLong(D_Customerparent);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("cussupId");
        propertes.add("cussupType");
        propertes.add("subjectId");
        propertes.add("createType");
        propertes.add("isSaleAccount");
        propertes.add("legalPerson");
        propertes.add("supplierType");
        propertes.add("businessPartnerType");
        propertes.add("tradeCurrency");
        propertes.add("payCondition");
        propertes.add("payDays");
        propertes.add("creditLimit");
        propertes.add("version");
        propertes.add("customerType");
        propertes.add("sowSize");
        propertes.add("customerLevel");
        propertes.add("sellType");
        propertes.add("sellDivision");
        propertes.add("sellArea");
        propertes.add("salesman");
        propertes.add("fixedBalanceDay");
        propertes.add("collectCondition");
        propertes.add("collectDays");
        propertes.add("financeArea");
        propertes.add("customerparent");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




