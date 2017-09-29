package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-29 15:31:12
 *       表：PP_L_BILL_PIG_SALE_DETAIL
 */
public class PigSaleDetailModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 6968005296259504111L;

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

    // 记录行号
    private static final String D_LineNumber="lineNumber";

    // 猪群ID
    private static final String D_SwineryId="swineryId";

    // 猪舍ID
    private static final String D_HouseId="houseId";

    // 栏号ID
    private static final String D_PigpenId="pigpenId";

    // 单据ID
    private static final String D_BillId="billId";

    // 销售数量
    private static final String D_SaleNum="saleNum";

    // 总重
    private static final String D_TotalWeight="totalWeight";

    // 重量单价
    private static final String D_TotalUnitPrice="totalUnitPrice";

    // 底重
    private static final String D_BaseWeight="baseWeight";

    // 底重单价
    private static final String D_BaseUnitPrice="baseUnitPrice";

    // 底重金额
    private static final String D_UnitPricePrice="unitPricePrice";

    // 超重
    private static final String D_OverWeight="overWeight";

    // 超重单价
    private static final String D_OverUnitPrice="overUnitPrice";

    // 超重金额
    private static final String D_OverPrice="overPrice";

    // 头单价
    private static final String D_PreUnitPrice="preUnitPrice";

    // 总金额
    private static final String D_TotalPrice="totalPrice";

    // 销售品名
    private static final String D_SaleDescribe="saleDescribe";

    // 销售日期
    private static final String D_SaleDate="saleDate";

    // 负责人
    private static final String D_Worker="worker";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 品种id
    private static final String D_BreedId="breedId";

    // 1公,2母,3混种
    private static final String D_Sex="sex";

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
     * 设置记录行号
     * 
     * @param LINE_NUMBER
     */
	public void setLineNumber(Long value) {
        set(D_LineNumber,value);
    }
	
	/**
     * 获取记录行号
     * 
     * @return LINE_NUMBER
     */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

    /**
     * 设置猪群ID
     * 
     * @param SWINERY_ID
     */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
     * 获取猪群ID
     * 
     * @return SWINERY_ID
     */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

    /**
     * 设置猪舍ID
     * 
     * @param HOUSE_ID
     */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
     * 获取猪舍ID
     * 
     * @return HOUSE_ID
     */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

    /**
     * 设置栏号ID
     * 
     * @param PIGPEN_ID
     */
	public void setPigpenId(Long value) {
        set(D_PigpenId,value);
    }
	
	/**
     * 获取栏号ID
     * 
     * @return PIGPEN_ID
     */
    public Long getPigpenId() {
        return getLong(D_PigpenId);
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
     * 设置销售数量
     * 
     * @param SALE_NUM
     */
	public void setSaleNum(Long value) {
        set(D_SaleNum,value);
    }
	
	/**
     * 获取销售数量
     * 
     * @return SALE_NUM
     */
    public Long getSaleNum() {
        return getLong(D_SaleNum);
    }

	/**
     * 设置总重
     * 
     * @param TOTAL_WEIGHT
     */
	public void setTotalWeight(Double value) {
        set(D_TotalWeight,value);
    }
	
    /**
     * 获取总重
     * 
     * @return TOTAL_WEIGHT
     */
    public Double getTotalWeight() {
        return getDouble(D_TotalWeight);
    }

	/**
     * 设置重量单价
     * 
     * @param TOTAL_UNIT_PRICE
     */
	public void setTotalUnitPrice(Double value) {
        set(D_TotalUnitPrice,value);
    }
	
    /**
     * 获取重量单价
     * 
     * @return TOTAL_UNIT_PRICE
     */
    public Double getTotalUnitPrice() {
        return getDouble(D_TotalUnitPrice);
    }

	/**
     * 设置底重
     * 
     * @param BASE_WEIGHT
     */
	public void setBaseWeight(Double value) {
        set(D_BaseWeight,value);
    }
	
    /**
     * 获取底重
     * 
     * @return BASE_WEIGHT
     */
    public Double getBaseWeight() {
        return getDouble(D_BaseWeight);
    }

	/**
     * 设置底重单价
     * 
     * @param BASE_UNIT_PRICE
     */
	public void setBaseUnitPrice(Double value) {
        set(D_BaseUnitPrice,value);
    }
	
    /**
     * 获取底重单价
     * 
     * @return BASE_UNIT_PRICE
     */
    public Double getBaseUnitPrice() {
        return getDouble(D_BaseUnitPrice);
    }

	/**
     * 设置底重金额
     * 
     * @param UNIT_PRICE_PRICE
     */
	public void setUnitPricePrice(Double value) {
        set(D_UnitPricePrice,value);
    }
	
    /**
     * 获取底重金额
     * 
     * @return UNIT_PRICE_PRICE
     */
    public Double getUnitPricePrice() {
        return getDouble(D_UnitPricePrice);
    }

	/**
     * 设置超重
     * 
     * @param OVER_WEIGHT
     */
	public void setOverWeight(Double value) {
        set(D_OverWeight,value);
    }
	
    /**
     * 获取超重
     * 
     * @return OVER_WEIGHT
     */
    public Double getOverWeight() {
        return getDouble(D_OverWeight);
    }

	/**
     * 设置超重单价
     * 
     * @param OVER_UNIT_PRICE
     */
	public void setOverUnitPrice(Double value) {
        set(D_OverUnitPrice,value);
    }
	
    /**
     * 获取超重单价
     * 
     * @return OVER_UNIT_PRICE
     */
    public Double getOverUnitPrice() {
        return getDouble(D_OverUnitPrice);
    }

	/**
     * 设置超重金额
     * 
     * @param OVER_PRICE
     */
	public void setOverPrice(Double value) {
        set(D_OverPrice,value);
    }
	
    /**
     * 获取超重金额
     * 
     * @return OVER_PRICE
     */
    public Double getOverPrice() {
        return getDouble(D_OverPrice);
    }

	/**
     * 设置头单价
     * 
     * @param PRE_UNIT_PRICE
     */
	public void setPreUnitPrice(Double value) {
        set(D_PreUnitPrice,value);
    }
	
    /**
     * 获取头单价
     * 
     * @return PRE_UNIT_PRICE
     */
    public Double getPreUnitPrice() {
        return getDouble(D_PreUnitPrice);
    }

	/**
     * 设置总金额
     * 
     * @param TOTAL_PRICE
     */
	public void setTotalPrice(Double value) {
        set(D_TotalPrice,value);
    }
	
    /**
     * 获取总金额
     * 
     * @return TOTAL_PRICE
     */
    public Double getTotalPrice() {
        return getDouble(D_TotalPrice);
    }

	/**
     * 设置销售品名
     * 
     * @param SALE_DESCRIBE
     */
	public void setSaleDescribe(String value) {
        set(D_SaleDescribe,value);
    }
	
    /**
     * 获取销售品名
     * 
     * @return SALE_DESCRIBE
     */
    public String getSaleDescribe() {
        return getString(D_SaleDescribe);
    }

	/**
     * 设置销售日期
     * 
     * @param SALE_DATE
     */
	public void setSaleDate(Date value) {
        set(D_SaleDate,value);
    }
	
    /**
     * 获取销售日期
     * 
     * @return SALE_DATE
     */
    public Date getSaleDate() {
        return getDate(D_SaleDate);
    }

    /**
     * 设置负责人
     * 
     * @param WORKER
     */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
     * 获取负责人
     * 
     * @return WORKER
     */
    public Long getWorker() {
        return getLong(D_Worker);
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
     * 设置品种id
     * 
     * @param BREED_ID
     */
	public void setBreedId(Long value) {
        set(D_BreedId,value);
    }
	
	/**
     * 获取品种id
     * 
     * @return BREED_ID
     */
    public Long getBreedId() {
        return getLong(D_BreedId);
    }

    /**
     * 设置1公,2母,3混种
     * 
     * @param SEX
     */
	public void setSex(Long value) {
        set(D_Sex,value);
    }
	
	/**
     * 获取1公,2母,3混种
     * 
     * @return SEX
     */
    public Long getSex() {
        return getLong(D_Sex);
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
        propertes.add("lineNumber");
        propertes.add("swineryId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("billId");
        propertes.add("saleNum");
        propertes.add("totalWeight");
        propertes.add("totalUnitPrice");
        propertes.add("baseWeight");
        propertes.add("baseUnitPrice");
        propertes.add("unitPricePrice");
        propertes.add("overWeight");
        propertes.add("overUnitPrice");
        propertes.add("overPrice");
        propertes.add("preUnitPrice");
        propertes.add("totalPrice");
        propertes.add("saleDescribe");
        propertes.add("saleDate");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("breedId");
        propertes.add("sex");
        propertes.add("sapSaleType");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




