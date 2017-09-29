package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-4-10 14:42:37
 *       表：CD_M_MATERIAL 
 */
public class MaterialModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -6966258503433969255L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。 
    private static final String D_OriginFlag="originFlag";
	 //数据来源应用的代码 
    private static final String D_OriginApp="originApp";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //创建的公司ID(可能集团创建) 
    private static final String D_RelatedId="relatedId";
	 //物料组ID 
    private static final String D_GroupId="groupId";
	 //物料编码 
    private static final String D_BusinessCode="businessCode";
	 //物料名称 
    private static final String D_MaterialName="materialName";
	 //物料类型 
    private static final String D_MaterialType="materialType";
	 //物料来源 
    private static final String D_MaterialSource="materialSource";
	 //计量单位 
    private static final String D_Unit="unit";
	 //仓库物料 Y/N 
    private static final String D_IsWarehouse="isWarehouse";
	 //采购物料 Y/N 
    private static final String D_IsPurchase="isPurchase";
	 //销售物料 Y/N 
    private static final String D_IsSell="isSell";
	 //默认仓库 
    private static final String D_DefaultWarehouse="defaultWarehouse";
	 //单价 
    private static final String D_Price="price";
	 //允许负库存 
    private static final String D_CanLoseStock="canLoseStock";
	 //允许采购退货 
    private static final String D_CanPurchaseReturn="canPurchaseReturn";
	 //允许报废 
    private static final String D_CanScrap="canScrap";
	 //计算规格(单位) 
    private static final String D_Spec="spec";
	 //计算规格(数量) 
    private static final String D_SpecNum="specNum";
	 //显示规格 
    private static final String D_SpecAll="specAll";
	 //安全库存 
    private static final String D_SafeStock="safeStock";
	 //库存科目 
    private static final String D_StockSubjectId="stockSubjectId";
	 //成本归集科目 
    private static final String D_CostAccSubjectId="costAccSubjectId";
	 //销售收入科目 
    private static final String D_SalesSubjectId="salesSubjectId";
	 //残值率 
    private static final String D_Rmvaluerate="rmvaluerate";
	 //赠送比率 
    private static final String D_FreePercent="freePercent";
	 //供应商 
    private static final String D_SupplierId="supplierId";
	 //生产厂家 
    private static final String D_Manufacturer="manufacturer";
	 //最小领用数量 
    private static final String D_OutputMinQty="outputMinQty";
	 //财务核算一级分类 
    private static final String D_FirstFinanceMaterialType="firstFinanceMaterialType";
	 //财务核算二级分类 
    private static final String D_SecondFinanceMaterialType="secondFinanceMaterialType";
	 //物料大类 
    private static final String D_MaterialFirstClass="materialFirstClass";
	 //物料中类 
    private static final String D_MaterialSecondClass="materialSecondClass";
	 //物料分类流水 
    private static final String D_MaterialClassNumber="materialClassNumber";
	 //财务科目 
    private static final String D_FinanceSubject="financeSubject";
	

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
    * 设置排序号
    * @param SORT_NBR
    */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
    * 获取排序号
    * @return SORT_NBR
    */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
    * 设置表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
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
    * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @param ORIGIN_FLAG
    */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
   /**
    * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @return ORIGIN_FLAG
    */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
    * 设置数据来源应用的代码
    * @param ORIGIN_APP
    */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
   /**
    * 获取数据来源应用的代码
    * @return ORIGIN_APP
    */
    public String getOriginApp() {
        return getString(D_OriginApp);
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
    * 设置创建的公司ID(可能集团创建)
    * @param RELATED_ID
    */
	public void setRelatedId(Long value) {
        set(D_RelatedId,value);
    }
	
	/**
    * 获取创建的公司ID(可能集团创建)
    * @return RELATED_ID
    */
    public Long getRelatedId() {
        return getLong(D_RelatedId);
    }

   /**
    * 设置物料组ID
    * @param GROUP_ID
    */
	public void setGroupId(Long value) {
        set(D_GroupId,value);
    }
	
	/**
    * 获取物料组ID
    * @return GROUP_ID
    */
    public Long getGroupId() {
        return getLong(D_GroupId);
    }

	/**
    * 设置物料编码
    * @param BUSINESS_CODE
    */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
   /**
    * 获取物料编码
    * @return BUSINESS_CODE
    */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

	/**
    * 设置物料名称
    * @param MATERIAL_NAME
    */
	public void setMaterialName(String value) {
        set(D_MaterialName,value);
    }
	
   /**
    * 获取物料名称
    * @return MATERIAL_NAME
    */
    public String getMaterialName() {
        return getString(D_MaterialName);
    }

	/**
    * 设置物料类型
    * @param MATERIAL_TYPE
    */
	public void setMaterialType(String value) {
        set(D_MaterialType,value);
    }
	
   /**
    * 获取物料类型
    * @return MATERIAL_TYPE
    */
    public String getMaterialType() {
        return getString(D_MaterialType);
    }

	/**
    * 设置物料来源
    * @param MATERIAL_SOURCE
    */
	public void setMaterialSource(String value) {
        set(D_MaterialSource,value);
    }
	
   /**
    * 获取物料来源
    * @return MATERIAL_SOURCE
    */
    public String getMaterialSource() {
        return getString(D_MaterialSource);
    }

	/**
    * 设置计量单位
    * @param UNIT
    */
	public void setUnit(String value) {
        set(D_Unit,value);
    }
	
   /**
    * 获取计量单位
    * @return UNIT
    */
    public String getUnit() {
        return getString(D_Unit);
    }

	/**
    * 设置仓库物料 Y/N
    * @param IS_WAREHOUSE
    */
	public void setIsWarehouse(String value) {
        set(D_IsWarehouse,value);
    }
	
   /**
    * 获取仓库物料 Y/N
    * @return IS_WAREHOUSE
    */
    public String getIsWarehouse() {
        return getString(D_IsWarehouse);
    }

	/**
    * 设置采购物料 Y/N
    * @param IS_PURCHASE
    */
	public void setIsPurchase(String value) {
        set(D_IsPurchase,value);
    }
	
   /**
    * 获取采购物料 Y/N
    * @return IS_PURCHASE
    */
    public String getIsPurchase() {
        return getString(D_IsPurchase);
    }

	/**
    * 设置销售物料 Y/N
    * @param IS_SELL
    */
	public void setIsSell(String value) {
        set(D_IsSell,value);
    }
	
   /**
    * 获取销售物料 Y/N
    * @return IS_SELL
    */
    public String getIsSell() {
        return getString(D_IsSell);
    }

   /**
    * 设置默认仓库
    * @param DEFAULT_WAREHOUSE
    */
	public void setDefaultWarehouse(Long value) {
        set(D_DefaultWarehouse,value);
    }
	
	/**
    * 获取默认仓库
    * @return DEFAULT_WAREHOUSE
    */
    public Long getDefaultWarehouse() {
        return getLong(D_DefaultWarehouse);
    }

	/**
    * 设置单价
    * @param PRICE
    */
	public void setPrice(Double value) {
        set(D_Price,value);
    }
	
   /**
    * 获取单价
    * @return PRICE
    */
    public Double getPrice() {
        return getDouble(D_Price);
    }

	/**
    * 设置允许负库存
    * @param CAN_LOSE_STOCK
    */
	public void setCanLoseStock(String value) {
        set(D_CanLoseStock,value);
    }
	
   /**
    * 获取允许负库存
    * @return CAN_LOSE_STOCK
    */
    public String getCanLoseStock() {
        return getString(D_CanLoseStock);
    }

	/**
    * 设置允许采购退货
    * @param CAN_PURCHASE_RETURN
    */
	public void setCanPurchaseReturn(String value) {
        set(D_CanPurchaseReturn,value);
    }
	
   /**
    * 获取允许采购退货
    * @return CAN_PURCHASE_RETURN
    */
    public String getCanPurchaseReturn() {
        return getString(D_CanPurchaseReturn);
    }

	/**
    * 设置允许报废
    * @param CAN_SCRAP
    */
	public void setCanScrap(String value) {
        set(D_CanScrap,value);
    }
	
   /**
    * 获取允许报废
    * @return CAN_SCRAP
    */
    public String getCanScrap() {
        return getString(D_CanScrap);
    }

	/**
    * 设置计算规格(单位)
    * @param SPEC
    */
	public void setSpec(String value) {
        set(D_Spec,value);
    }
	
   /**
    * 获取计算规格(单位)
    * @return SPEC
    */
    public String getSpec() {
        return getString(D_Spec);
    }

	/**
    * 设置计算规格(数量)
    * @param SPEC_NUM
    */
	public void setSpecNum(Double value) {
        set(D_SpecNum,value);
    }
	
   /**
    * 获取计算规格(数量)
    * @return SPEC_NUM
    */
    public Double getSpecNum() {
        return getDouble(D_SpecNum);
    }

	/**
    * 设置显示规格
    * @param SPEC_ALL
    */
	public void setSpecAll(String value) {
        set(D_SpecAll,value);
    }
	
   /**
    * 获取显示规格
    * @return SPEC_ALL
    */
    public String getSpecAll() {
        return getString(D_SpecAll);
    }

	/**
    * 设置安全库存
    * @param SAFE_STOCK
    */
	public void setSafeStock(Double value) {
        set(D_SafeStock,value);
    }
	
   /**
    * 获取安全库存
    * @return SAFE_STOCK
    */
    public Double getSafeStock() {
        return getDouble(D_SafeStock);
    }

   /**
    * 设置库存科目
    * @param STOCK_SUBJECT_ID
    */
	public void setStockSubjectId(Long value) {
        set(D_StockSubjectId,value);
    }
	
	/**
    * 获取库存科目
    * @return STOCK_SUBJECT_ID
    */
    public Long getStockSubjectId() {
        return getLong(D_StockSubjectId);
    }

   /**
    * 设置成本归集科目
    * @param COST_ACC_SUBJECT_ID
    */
	public void setCostAccSubjectId(Long value) {
        set(D_CostAccSubjectId,value);
    }
	
	/**
    * 获取成本归集科目
    * @return COST_ACC_SUBJECT_ID
    */
    public Long getCostAccSubjectId() {
        return getLong(D_CostAccSubjectId);
    }

   /**
    * 设置销售收入科目
    * @param SALES_SUBJECT_ID
    */
	public void setSalesSubjectId(Long value) {
        set(D_SalesSubjectId,value);
    }
	
	/**
    * 获取销售收入科目
    * @return SALES_SUBJECT_ID
    */
    public Long getSalesSubjectId() {
        return getLong(D_SalesSubjectId);
    }

	/**
    * 设置残值率
    * @param RMVALUERATE
    */
	public void setRmvaluerate(Double value) {
        set(D_Rmvaluerate,value);
    }
	
   /**
    * 获取残值率
    * @return RMVALUERATE
    */
    public Double getRmvaluerate() {
        return getDouble(D_Rmvaluerate);
    }

	/**
    * 设置赠送比率
    * @param FREE_PERCENT
    */
	public void setFreePercent(String value) {
        set(D_FreePercent,value);
    }
	
   /**
    * 获取赠送比率
    * @return FREE_PERCENT
    */
    public String getFreePercent() {
        return getString(D_FreePercent);
    }

   /**
    * 设置供应商
    * @param SUPPLIER_ID
    */
	public void setSupplierId(Long value) {
        set(D_SupplierId,value);
    }
	
	/**
    * 获取供应商
    * @return SUPPLIER_ID
    */
    public Long getSupplierId() {
        return getLong(D_SupplierId);
    }

	/**
    * 设置生产厂家
    * @param MANUFACTURER
    */
	public void setManufacturer(String value) {
        set(D_Manufacturer,value);
    }
	
   /**
    * 获取生产厂家
    * @return MANUFACTURER
    */
    public String getManufacturer() {
        return getString(D_Manufacturer);
    }

	/**
    * 设置最小领用数量
    * @param OUTPUT_MIN_QTY
    */
	public void setOutputMinQty(Double value) {
        set(D_OutputMinQty,value);
    }
	
   /**
    * 获取最小领用数量
    * @return OUTPUT_MIN_QTY
    */
    public Double getOutputMinQty() {
        return getDouble(D_OutputMinQty);
    }

	/**
    * 设置财务核算一级分类
    * @param FIRST_FINANCE_MATERIAL_TYPE
    */
	public void setFirstFinanceMaterialType(String value) {
        set(D_FirstFinanceMaterialType,value);
    }
	
   /**
    * 获取财务核算一级分类
    * @return FIRST_FINANCE_MATERIAL_TYPE
    */
    public String getFirstFinanceMaterialType() {
        return getString(D_FirstFinanceMaterialType);
    }

	/**
    * 设置财务核算二级分类
    * @param SECOND_FINANCE_MATERIAL_TYPE
    */
	public void setSecondFinanceMaterialType(String value) {
        set(D_SecondFinanceMaterialType,value);
    }
	
   /**
    * 获取财务核算二级分类
    * @return SECOND_FINANCE_MATERIAL_TYPE
    */
    public String getSecondFinanceMaterialType() {
        return getString(D_SecondFinanceMaterialType);
    }

	/**
    * 设置物料大类
    * @param MATERIAL_FIRST_CLASS
    */
	public void setMaterialFirstClass(String value) {
        set(D_MaterialFirstClass,value);
    }
	
   /**
    * 获取物料大类
    * @return MATERIAL_FIRST_CLASS
    */
    public String getMaterialFirstClass() {
        return getString(D_MaterialFirstClass);
    }

	/**
    * 设置物料中类
    * @param MATERIAL_SECOND_CLASS
    */
	public void setMaterialSecondClass(String value) {
        set(D_MaterialSecondClass,value);
    }
	
   /**
    * 获取物料中类
    * @return MATERIAL_SECOND_CLASS
    */
    public String getMaterialSecondClass() {
        return getString(D_MaterialSecondClass);
    }

	/**
    * 设置物料分类流水
    * @param MATERIAL_CLASS_NUMBER
    */
	public void setMaterialClassNumber(String value) {
        set(D_MaterialClassNumber,value);
    }
	
   /**
    * 获取物料分类流水
    * @return MATERIAL_CLASS_NUMBER
    */
    public String getMaterialClassNumber() {
        return getString(D_MaterialClassNumber);
    }

	/**
    * 设置财务科目
    * @param FINANCE_SUBJECT
    */
	public void setFinanceSubject(String value) {
        set(D_FinanceSubject,value);
    }
	
   /**
    * 获取财务科目
    * @return FINANCE_SUBJECT
    */
    public String getFinanceSubject() {
        return getString(D_FinanceSubject);
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
        propertes.add("relatedId");
        propertes.add("groupId");
        propertes.add("businessCode");
        propertes.add("materialName");
        propertes.add("materialType");
        propertes.add("materialSource");
        propertes.add("unit");
        propertes.add("isWarehouse");
        propertes.add("isPurchase");
        propertes.add("isSell");
        propertes.add("defaultWarehouse");
        propertes.add("price");
        propertes.add("canLoseStock");
        propertes.add("canPurchaseReturn");
        propertes.add("canScrap");
        propertes.add("spec");
        propertes.add("specNum");
        propertes.add("specAll");
        propertes.add("safeStock");
        propertes.add("stockSubjectId");
        propertes.add("costAccSubjectId");
        propertes.add("salesSubjectId");
        propertes.add("rmvaluerate");
        propertes.add("freePercent");
        propertes.add("supplierId");
        propertes.add("manufacturer");
        propertes.add("outputMinQty");
        propertes.add("firstFinanceMaterialType");
        propertes.add("secondFinanceMaterialType");
        propertes.add("materialFirstClass");
        propertes.add("materialSecondClass");
        propertes.add("materialClassNumber");
        propertes.add("financeSubject");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




