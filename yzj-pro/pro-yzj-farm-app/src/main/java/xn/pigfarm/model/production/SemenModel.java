package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-21 16:28:48
 *       表：PP_L_BILL_SEMEN 
 */
public class SemenModel  extends BaseDataModel implements Serializable{
    private static final long serialVersionUID = 7628875950928529306L;

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
	 //记录行号 
    private static final String D_LineNumber="lineNumber";
	 //猪群ID 
    private static final String D_SwineryId="swineryId";
	 //猪只ID 
    private static final String D_PigId="pigId";
	 //产线ID 
    private static final String D_LineId="lineId";
	 //猪舍ID 
    private static final String D_HouseId="houseId";
	 //栏号ID 
    private static final String D_PigpenId="pigpenId";
	 //单据号 
    private static final String D_BillId="billId";
	 //负责人 
    private static final String D_Worker="worker";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //materialId 
    private static final String D_MaterialId="materialId";
	 //当前日龄 
    private static final String D_DayAge="dayAge";
	 //采精日期 
    private static final String D_SemenDate="semenDate";
	 //采精方式 
    private static final String D_SemenType="semenType";
	 //精液量（ML） 
    private static final String D_SemenQty="semenQty";
	 //稀释分数 
    private static final String D_AnhNum="anhNum";
	 //化验员 
    private static final String D_Analust="analust";
	 //精液批号 
    private static final String D_SemenBatchNo="semenBatchNo";
	 //精子活力（%） 
    private static final String D_SpermMotility="spermMotility";
	 //精子密度 
    private static final String D_SpermDensity="spermDensity";
	 //畸形率（%） 
    private static final String D_AbnormationRate="abnormationRate";
	 //凝聚度 
    private static final String D_Cohesion="cohesion";
	 //气味 
    private static final String D_Odour="odour";
	 //颜色 
    private static final String D_Color="color";
	 //包装 
    private static final String D_Pack="pack";
	 //规格（ML） 
    private static final String D_Spec="spec";
	 //有效期 
    private static final String D_ValidDate="validDate";
	 //精液来源,1:本厂,2:平台内,3:平台外 
    private static final String D_Origin="origin";
	 //供应商ID 
    private static final String D_SupplierId="supplierId";
	 //生产厂商ID 
    private static final String D_ManufacturerId="manufacturerId";
	 //仓库ID 
    private static final String D_WarehouseId="warehouseId";
	 //打印次数 
    private static final String D_PrintNum="printNum";
	 //入库日期 
    private static final String D_InputDate="inputDate";
	 //公猪耳牌号 
    private static final String D_EarBrand="earBrand";
	 //公猪主数据 
    private static final String D_BoarMaterialId="boarMaterialId";
	 //品种ID 
    private static final String D_BreedId="breedId";
	 //实际精液数量 
    private static final String D_SpermNum="spermNum";
	 //PP_L_BILL_SEMEN_SALE的ROW_ID 
    private static final String D_SemenSaleRowId="semenSaleRowId";
	 //删除了这次记录的撤销单据ID 
    private static final String D_DeletedBillId="deletedBillId";
	

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
    * 设置记录行号
    * @param LINE_NUMBER
    */
	public void setLineNumber(Long value) {
        set(D_LineNumber,value);
    }
	
	/**
    * 获取记录行号
    * @return LINE_NUMBER
    */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

   /**
    * 设置猪群ID
    * @param SWINERY_ID
    */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
    * 获取猪群ID
    * @return SWINERY_ID
    */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

   /**
    * 设置猪只ID
    * @param PIG_ID
    */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
    * 获取猪只ID
    * @return PIG_ID
    */
    public Long getPigId() {
        return getLong(D_PigId);
    }

   /**
    * 设置产线ID
    * @param LINE_ID
    */
	public void setLineId(Long value) {
        set(D_LineId,value);
    }
	
	/**
    * 获取产线ID
    * @return LINE_ID
    */
    public Long getLineId() {
        return getLong(D_LineId);
    }

   /**
    * 设置猪舍ID
    * @param HOUSE_ID
    */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
    * 获取猪舍ID
    * @return HOUSE_ID
    */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

   /**
    * 设置栏号ID
    * @param PIGPEN_ID
    */
	public void setPigpenId(Long value) {
        set(D_PigpenId,value);
    }
	
	/**
    * 获取栏号ID
    * @return PIGPEN_ID
    */
    public Long getPigpenId() {
        return getLong(D_PigpenId);
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
    * 设置负责人
    * @param WORKER
    */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
    * 获取负责人
    * @return WORKER
    */
    public Long getWorker() {
        return getLong(D_Worker);
    }

   /**
    * 设置创建人
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建人
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
    * 设置当前日龄
    * @param DAY_AGE
    */
	public void setDayAge(Long value) {
        set(D_DayAge,value);
    }
	
	/**
    * 获取当前日龄
    * @return DAY_AGE
    */
    public Long getDayAge() {
        return getLong(D_DayAge);
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
    * 设置采精方式
    * @param SEMEN_TYPE
    */
	public void setSemenType(String value) {
        set(D_SemenType,value);
    }
	
   /**
    * 获取采精方式
    * @return SEMEN_TYPE
    */
    public String getSemenType() {
        return getString(D_SemenType);
    }

	/**
    * 设置精液量（ML）
    * @param SEMEN_QTY
    */
	public void setSemenQty(Double value) {
        set(D_SemenQty,value);
    }
	
   /**
    * 获取精液量（ML）
    * @return SEMEN_QTY
    */
    public Double getSemenQty() {
        return getDouble(D_SemenQty);
    }

	/**
    * 设置稀释分数
    * @param ANH_NUM
    */
	public void setAnhNum(Double value) {
        set(D_AnhNum,value);
    }
	
   /**
    * 获取稀释分数
    * @return ANH_NUM
    */
    public Double getAnhNum() {
        return getDouble(D_AnhNum);
    }

   /**
    * 设置化验员
    * @param ANALUST
    */
	public void setAnalust(Long value) {
        set(D_Analust,value);
    }
	
	/**
    * 获取化验员
    * @return ANALUST
    */
    public Long getAnalust() {
        return getLong(D_Analust);
    }

	/**
    * 设置精液批号
    * @param SEMEN_BATCH_NO
    */
	public void setSemenBatchNo(String value) {
        set(D_SemenBatchNo,value);
    }
	
   /**
    * 获取精液批号
    * @return SEMEN_BATCH_NO
    */
    public String getSemenBatchNo() {
        return getString(D_SemenBatchNo);
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
    * 设置气味
    * @param ODOUR
    */
	public void setOdour(String value) {
        set(D_Odour,value);
    }
	
   /**
    * 获取气味
    * @return ODOUR
    */
    public String getOdour() {
        return getString(D_Odour);
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
    * 设置规格（ML）
    * @param SPEC
    */
	public void setSpec(String value) {
        set(D_Spec,value);
    }
	
   /**
    * 获取规格（ML）
    * @return SPEC
    */
    public String getSpec() {
        return getString(D_Spec);
    }

	/**
    * 设置有效期
    * @param VALID_DATE
    */
	public void setValidDate(Date value) {
        set(D_ValidDate,value);
    }
	
   /**
    * 获取有效期
    * @return VALID_DATE
    */
    public Date getValidDate() {
        return getDate(D_ValidDate);
    }

	/**
    * 设置精液来源,1:本厂,2:平台内,3:平台外
    * @param ORIGIN
    */
	public void setOrigin(String value) {
        set(D_Origin,value);
    }
	
   /**
    * 获取精液来源,1:本厂,2:平台内,3:平台外
    * @return ORIGIN
    */
    public String getOrigin() {
        return getString(D_Origin);
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
    * 设置生产厂商ID
    * @param MANUFACTURER_ID
    */
	public void setManufacturerId(Long value) {
        set(D_ManufacturerId,value);
    }
	
	/**
    * 获取生产厂商ID
    * @return MANUFACTURER_ID
    */
    public Long getManufacturerId() {
        return getLong(D_ManufacturerId);
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
    * 设置打印次数
    * @param PRINT_NUM
    */
	public void setPrintNum(Long value) {
        set(D_PrintNum,value);
    }
	
	/**
    * 获取打印次数
    * @return PRINT_NUM
    */
    public Long getPrintNum() {
        return getLong(D_PrintNum);
    }

	/**
    * 设置入库日期
    * @param INPUT_DATE
    */
	public void setInputDate(Date value) {
        set(D_InputDate,value);
    }
	
   /**
    * 获取入库日期
    * @return INPUT_DATE
    */
    public Date getInputDate() {
        return getDate(D_InputDate);
    }

	/**
    * 设置公猪耳牌号
    * @param EAR_BRAND
    */
	public void setEarBrand(String value) {
        set(D_EarBrand,value);
    }
	
   /**
    * 获取公猪耳牌号
    * @return EAR_BRAND
    */
    public String getEarBrand() {
        return getString(D_EarBrand);
    }

   /**
    * 设置公猪主数据
    * @param BOAR_MATERIAL_ID
    */
	public void setBoarMaterialId(Long value) {
        set(D_BoarMaterialId,value);
    }
	
	/**
    * 获取公猪主数据
    * @return BOAR_MATERIAL_ID
    */
    public Long getBoarMaterialId() {
        return getLong(D_BoarMaterialId);
    }

   /**
    * 设置品种ID
    * @param BREED_ID
    */
	public void setBreedId(Long value) {
        set(D_BreedId,value);
    }
	
	/**
    * 获取品种ID
    * @return BREED_ID
    */
    public Long getBreedId() {
        return getLong(D_BreedId);
    }

	/**
    * 设置实际精液数量
    * @param SPERM_NUM
    */
	public void setSpermNum(Double value) {
        set(D_SpermNum,value);
    }
	
   /**
    * 获取实际精液数量
    * @return SPERM_NUM
    */
    public Double getSpermNum() {
        return getDouble(D_SpermNum);
    }

   /**
    * 设置PP_L_BILL_SEMEN_SALE的ROW_ID
    * @param SEMEN_SALE_ROW_ID
    */
	public void setSemenSaleRowId(Long value) {
        set(D_SemenSaleRowId,value);
    }
	
	/**
    * 获取PP_L_BILL_SEMEN_SALE的ROW_ID
    * @return SEMEN_SALE_ROW_ID
    */
    public Long getSemenSaleRowId() {
        return getLong(D_SemenSaleRowId);
    }

   /**
    * 设置删除了这次记录的撤销单据ID
    * @param DELETED_BILL_ID
    */
	public void setDeletedBillId(Long value) {
        set(D_DeletedBillId,value);
    }
	
	/**
    * 获取删除了这次记录的撤销单据ID
    * @return DELETED_BILL_ID
    */
    public Long getDeletedBillId() {
        return getLong(D_DeletedBillId);
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
        propertes.add("lineNumber");
        propertes.add("swineryId");
        propertes.add("pigId");
        propertes.add("lineId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("billId");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("materialId");
        propertes.add("dayAge");
        propertes.add("semenDate");
        propertes.add("semenType");
        propertes.add("semenQty");
        propertes.add("anhNum");
        propertes.add("analust");
        propertes.add("semenBatchNo");
        propertes.add("spermMotility");
        propertes.add("spermDensity");
        propertes.add("abnormationRate");
        propertes.add("cohesion");
        propertes.add("odour");
        propertes.add("color");
        propertes.add("pack");
        propertes.add("spec");
        propertes.add("validDate");
        propertes.add("origin");
        propertes.add("supplierId");
        propertes.add("manufacturerId");
        propertes.add("warehouseId");
        propertes.add("printNum");
        propertes.add("inputDate");
        propertes.add("earBrand");
        propertes.add("boarMaterialId");
        propertes.add("breedId");
        propertes.add("spermNum");
        propertes.add("semenSaleRowId");
        propertes.add("deletedBillId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




