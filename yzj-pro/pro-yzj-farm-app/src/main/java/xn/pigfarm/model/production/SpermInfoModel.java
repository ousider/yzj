package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-15 20:44:37
 *       表：PP_L_SPERM_INFO 
 */
public class SpermInfoModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -2634316590500141752L;

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
	 //materialId 
    private static final String D_MaterialId="materialId";
	 //流水编码 
    private static final String D_BusinessCode="businessCode";
	 //配种母猪ID 
    private static final String D_SowPigId="sowPigId";
	 //semenId 
    private static final String D_SemenId="semenId";
	 //精液编号 
    private static final String D_SemenCode="semenCode";
	 //外部编号 
    private static final String D_ExternalCode="externalCode";
	 //仓库ID 
    private static final String D_WarehouseId="warehouseId";
	 //是否检查 
    private static final String D_IsCheck="isCheck";
	 //精液检查日期 
    private static final String D_CheckDate="checkDate";
	 //checkBy 
    private static final String D_CheckBy="checkBy";
	 //规格 
    private static final String D_Spec="spec";
	 //精子活力（%） 
    private static final String D_SpermMotility="spermMotility";
	 //精子密度 
    private static final String D_SpermDensity="spermDensity";
	 //畸形率（%） 
    private static final String D_AbnormationRate="abnormationRate";
	 //凝聚度 
    private static final String D_Cohesion="cohesion";
	 //颜色 
    private static final String D_Color="color";
	 //保存温度 
    private static final String D_Tstg="tstg";
	 //保质期（天） 
    private static final String D_ShelfLife="shelfLife";
	 //包装 
    private static final String D_Pack="pack";
	 //使用日期 
    private static final String D_UseDate="useDate";
	 //报废日期 
    private static final String D_ScrapDate="scrapDate";
	 //scrapBy 
    private static final String D_ScrapBy="scrapBy";
	 //报废原因 
    private static final String D_ScrapCause="scrapCause";
	 //化验员 
    private static final String D_Worker="worker";
	 //采精日期 
    private static final String D_SemenDate="semenDate";
	 //单价 
    private static final String D_Price="price";
	 //销售单价 
    private static final String D_SellPrice="sellPrice";
	 //单据号 
    private static final String D_BillId="billId";
	 //是否销售 0:未销售 1:已销售 
    private static final String D_IsSale="isSale";
	

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
    * 设置materialId
    * @param MATERIAL_ID
    */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
    * 获取materialId
    * @return MATERIAL_ID
    */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

   /**
    * 设置流水编码
    * @param BUSINESS_CODE
    */
	public void setBusinessCode(Long value) {
        set(D_BusinessCode,value);
    }
	
	/**
    * 获取流水编码
    * @return BUSINESS_CODE
    */
    public Long getBusinessCode() {
        return getLong(D_BusinessCode);
    }

   /**
    * 设置配种母猪ID
    * @param SOW_PIG_ID
    */
	public void setSowPigId(Long value) {
        set(D_SowPigId,value);
    }
	
	/**
    * 获取配种母猪ID
    * @return SOW_PIG_ID
    */
    public Long getSowPigId() {
        return getLong(D_SowPigId);
    }

   /**
    * 设置semenId
    * @param SEMEN_ID
    */
	public void setSemenId(Long value) {
        set(D_SemenId,value);
    }
	
	/**
    * 获取semenId
    * @return SEMEN_ID
    */
    public Long getSemenId() {
        return getLong(D_SemenId);
    }

	/**
    * 设置精液编号
    * @param SEMEN_CODE
    */
	public void setSemenCode(String value) {
        set(D_SemenCode,value);
    }
	
   /**
    * 获取精液编号
    * @return SEMEN_CODE
    */
    public String getSemenCode() {
        return getString(D_SemenCode);
    }

	/**
    * 设置外部编号
    * @param EXTERNAL_CODE
    */
	public void setExternalCode(String value) {
        set(D_ExternalCode,value);
    }
	
   /**
    * 获取外部编号
    * @return EXTERNAL_CODE
    */
    public String getExternalCode() {
        return getString(D_ExternalCode);
    }

   /**
    * 设置仓库ID
    * @param WAREHOUSE_ID
    */
	public void setWarehouseId(Long value) {
        set(D_WarehouseId,value);
    }
	
	/**
    * 获取仓库ID
    * @return WAREHOUSE_ID
    */
    public Long getWarehouseId() {
        return getLong(D_WarehouseId);
    }

	/**
    * 设置是否检查
    * @param IS_CHECK
    */
	public void setIsCheck(String value) {
        set(D_IsCheck,value);
    }
	
   /**
    * 获取是否检查
    * @return IS_CHECK
    */
    public String getIsCheck() {
        return getString(D_IsCheck);
    }

	/**
    * 设置精液检查日期
    * @param CHECK_DATE
    */
    public void setCheckDate(Date value) {
        set(D_CheckDate,value);
    }
	
   /**
    * 获取精液检查日期
    * @return CHECK_DATE
    */
    public Date getCheckDate() {
        return getDate(D_CheckDate);
    }

   /**
    * 设置checkBy
    * @param CHECK_BY
    */
	public void setCheckBy(Long value) {
        set(D_CheckBy,value);
    }
	
	/**
    * 获取checkBy
    * @return CHECK_BY
    */
    public Long getCheckBy() {
        return getLong(D_CheckBy);
    }

	/**
    * 设置规格
    * @param SPEC
    */
	public void setSpec(String value) {
        set(D_Spec,value);
    }
	
   /**
    * 获取规格
    * @return SPEC
    */
    public String getSpec() {
        return getString(D_Spec);
    }

	/**
    * 设置精子活力（%）
    * @param SPERM_MOTILITY
    */
	public void setSpermMotility(Double value) {
        set(D_SpermMotility,value);
    }
	
   /**
    * 获取精子活力（%）
    * @return SPERM_MOTILITY
    */
    public Double getSpermMotility() {
        return getDouble(D_SpermMotility);
    }

	/**
    * 设置精子密度
    * @param SPERM_DENSITY
    */
	public void setSpermDensity(Double value) {
        set(D_SpermDensity,value);
    }
	
   /**
    * 获取精子密度
    * @return SPERM_DENSITY
    */
    public Double getSpermDensity() {
        return getDouble(D_SpermDensity);
    }

	/**
    * 设置畸形率（%）
    * @param ABNORMATION_RATE
    */
	public void setAbnormationRate(Double value) {
        set(D_AbnormationRate,value);
    }
	
   /**
    * 获取畸形率（%）
    * @return ABNORMATION_RATE
    */
    public Double getAbnormationRate() {
        return getDouble(D_AbnormationRate);
    }

	/**
    * 设置凝聚度
    * @param COHESION
    */
	public void setCohesion(String value) {
        set(D_Cohesion,value);
    }
	
   /**
    * 获取凝聚度
    * @return COHESION
    */
    public String getCohesion() {
        return getString(D_Cohesion);
    }

	/**
    * 设置颜色
    * @param COLOR
    */
	public void setColor(String value) {
        set(D_Color,value);
    }
	
   /**
    * 获取颜色
    * @return COLOR
    */
    public String getColor() {
        return getString(D_Color);
    }

	/**
    * 设置保存温度
    * @param TSTG
    */
	public void setTstg(Double value) {
        set(D_Tstg,value);
    }
	
   /**
    * 获取保存温度
    * @return TSTG
    */
    public Double getTstg() {
        return getDouble(D_Tstg);
    }

   /**
    * 设置保质期（天）
    * @param SHELF_LIFE
    */
	public void setShelfLife(Long value) {
        set(D_ShelfLife,value);
    }
	
	/**
    * 获取保质期（天）
    * @return SHELF_LIFE
    */
    public Long getShelfLife() {
        return getLong(D_ShelfLife);
    }

	/**
    * 设置包装
    * @param PACK
    */
	public void setPack(String value) {
        set(D_Pack,value);
    }
	
   /**
    * 获取包装
    * @return PACK
    */
    public String getPack() {
        return getString(D_Pack);
    }

	/**
    * 设置使用日期
    * @param USE_DATE
    */
    public void setUseDate(Date value) {
        set(D_UseDate,value);
    }
	
   /**
    * 获取使用日期
    * @return USE_DATE
    */
    public Date getUseDate() {
        return getDate(D_UseDate);
    }

	/**
    * 设置报废日期
    * @param SCRAP_DATE
    */
    public void setScrapDate(Date value) {
        set(D_ScrapDate,value);
    }
	
   /**
    * 获取报废日期
    * @return SCRAP_DATE
    */
    public Date getScrapDate() {
        return getDate(D_ScrapDate);
    }

   /**
    * 设置scrapBy
    * @param SCRAP_BY
    */
	public void setScrapBy(Long value) {
        set(D_ScrapBy,value);
    }
	
	/**
    * 获取scrapBy
    * @return SCRAP_BY
    */
    public Long getScrapBy() {
        return getLong(D_ScrapBy);
    }

	/**
    * 设置报废原因
    * @param SCRAP_CAUSE
    */
	public void setScrapCause(String value) {
        set(D_ScrapCause,value);
    }
	
   /**
    * 获取报废原因
    * @return SCRAP_CAUSE
    */
    public String getScrapCause() {
        return getString(D_ScrapCause);
    }

   /**
    * 设置化验员
    * @param WORKER
    */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
    * 获取化验员
    * @return WORKER
    */
    public Long getWorker() {
        return getLong(D_Worker);
    }

	/**
    * 设置采精日期
    * @param SEMEN_DATE
    */
    public void setSemenDate(Date value) {
        set(D_SemenDate,value);
    }
	
   /**
    * 获取采精日期
    * @return SEMEN_DATE
    */
    public Date getSemenDate() {
        return getDate(D_SemenDate);
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
    * 设置销售单价
    * @param SELL_PRICE
    */
	public void setSellPrice(Double value) {
        set(D_SellPrice,value);
    }
	
   /**
    * 获取销售单价
    * @return SELL_PRICE
    */
    public Double getSellPrice() {
        return getDouble(D_SellPrice);
    }

   /**
    * 设置单据号
    * @param BILL_ID
    */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
    * 获取单据号
    * @return BILL_ID
    */
    public Long getBillId() {
        return getLong(D_BillId);
    }

   /**
    * 设置是否销售 0:未销售 1:已销售
    * @param IS_SALE
    */
	public void setIsSale(Long value) {
        set(D_IsSale,value);
    }
	
	/**
    * 获取是否销售 0:未销售 1:已销售
    * @return IS_SALE
    */
    public Long getIsSale() {
        return getLong(D_IsSale);
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
        propertes.add("materialId");
        propertes.add("businessCode");
        propertes.add("sowPigId");
        propertes.add("semenId");
        propertes.add("semenCode");
        propertes.add("externalCode");
        propertes.add("warehouseId");
        propertes.add("isCheck");
        propertes.add("checkDate");
        propertes.add("checkBy");
        propertes.add("spec");
        propertes.add("spermMotility");
        propertes.add("spermDensity");
        propertes.add("abnormationRate");
        propertes.add("cohesion");
        propertes.add("color");
        propertes.add("tstg");
        propertes.add("shelfLife");
        propertes.add("pack");
        propertes.add("useDate");
        propertes.add("scrapDate");
        propertes.add("scrapBy");
        propertes.add("scrapCause");
        propertes.add("worker");
        propertes.add("semenDate");
        propertes.add("price");
        propertes.add("sellPrice");
        propertes.add("billId");
        propertes.add("isSale");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




