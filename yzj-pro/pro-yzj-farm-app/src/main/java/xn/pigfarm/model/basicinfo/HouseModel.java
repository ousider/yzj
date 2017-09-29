package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-15 16:49:16
 *       表：PP_O_HOUSE 
 */
public class HouseModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 6807946183633617555L;

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
	 //猪舍代码 
    private static final String D_BusinessCode="businessCode";
	 //猪舍名称 
    private static final String D_HouseName="houseName";
	 //饲养员 
    private static final String D_BreederId="breederId";
	 //猪舍类别 
    private static final String D_HouseType="houseType";
	 //产线ID 
    private static final String D_LineId="lineId";
	 //猪类 
    private static final String D_PigClass="pigClass";
	 //清洗消毒天数 
    private static final String D_DisinfectDay="disinfectDay";
	 //消毒方法 
    private static final String D_DisinfectMethod="disinfectMethod";
	 //每栏容量 
    private static final String D_ColumnNum="columnNum";
	 //形状 
    private static final String D_Shape="shape";
	 //面积（平方米） 
    private static final String D_Area="area";
	 //长（米） 
    private static final String D_Length="length";
	 //宽（米） 
    private static final String D_Width="width";
	 //高（米） 
    private static final String D_Height="height";
	 //猪只类型 
    private static final String D_HouseClass="houseClass";
	 //折旧期 
    private static final String D_DepreciationPolicy="depreciationPolicy";
	 //造价 
    private static final String D_Cost="cost";
	 //栏数 
    private static final String D_PenNum="penNum";
	 //最大栏数 
    private static final String D_MaxCapacity="maxCapacity";
	 //猪舍编号前缀（SAP中就1位） 
    private static final String D_HousePrifixCode="housePrifixCode";
	 //猪舍单元号 
    private static final String D_HouseUnitNumber="houseUnitNumber";
	 //猪舍栋号 
    private static final String D_HouseBuildingNumber="houseBuildingNumber";
	 //是否是培育舍 Y/N 
    private static final String D_IsFosterHouse="isFosterHouse";
	

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
    * 设置猪舍代码
    * @param BUSINESS_CODE
    */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
   /**
    * 获取猪舍代码
    * @return BUSINESS_CODE
    */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

	/**
    * 设置猪舍名称
    * @param HOUSE_NAME
    */
	public void setHouseName(String value) {
        set(D_HouseName,value);
    }
	
   /**
    * 获取猪舍名称
    * @return HOUSE_NAME
    */
    public String getHouseName() {
        return getString(D_HouseName);
    }

   /**
    * 设置饲养员
    * @param BREEDER_ID
    */
	public void setBreederId(Long value) {
        set(D_BreederId,value);
    }
	
	/**
    * 获取饲养员
    * @return BREEDER_ID
    */
    public Long getBreederId() {
        return getLong(D_BreederId);
    }

   /**
    * 设置猪舍类别
    * @param HOUSE_TYPE
    */
	public void setHouseType(Long value) {
        set(D_HouseType,value);
    }
	
	/**
    * 获取猪舍类别
    * @return HOUSE_TYPE
    */
    public Long getHouseType() {
        return getLong(D_HouseType);
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
    * 设置猪类
    * @param PIG_CLASS
    */
	public void setPigClass(String value) {
        set(D_PigClass,value);
    }
	
   /**
    * 获取猪类
    * @return PIG_CLASS
    */
    public String getPigClass() {
        return getString(D_PigClass);
    }

   /**
    * 设置清洗消毒天数
    * @param DISINFECT_DAY
    */
	public void setDisinfectDay(Long value) {
        set(D_DisinfectDay,value);
    }
	
	/**
    * 获取清洗消毒天数
    * @return DISINFECT_DAY
    */
    public Long getDisinfectDay() {
        return getLong(D_DisinfectDay);
    }

	/**
    * 设置消毒方法
    * @param DISINFECT_METHOD
    */
	public void setDisinfectMethod(String value) {
        set(D_DisinfectMethod,value);
    }
	
   /**
    * 获取消毒方法
    * @return DISINFECT_METHOD
    */
    public String getDisinfectMethod() {
        return getString(D_DisinfectMethod);
    }

   /**
    * 设置每栏容量
    * @param COLUMN_NUM
    */
	public void setColumnNum(Long value) {
        set(D_ColumnNum,value);
    }
	
	/**
    * 获取每栏容量
    * @return COLUMN_NUM
    */
    public Long getColumnNum() {
        return getLong(D_ColumnNum);
    }

	/**
    * 设置形状
    * @param SHAPE
    */
	public void setShape(String value) {
        set(D_Shape,value);
    }
	
   /**
    * 获取形状
    * @return SHAPE
    */
    public String getShape() {
        return getString(D_Shape);
    }

	/**
    * 设置面积（平方米）
    * @param AREA
    */
	public void setArea(Double value) {
        set(D_Area,value);
    }
	
   /**
    * 获取面积（平方米）
    * @return AREA
    */
    public Double getArea() {
        return getDouble(D_Area);
    }

	/**
    * 设置长（米）
    * @param LENGTH
    */
	public void setLength(Double value) {
        set(D_Length,value);
    }
	
   /**
    * 获取长（米）
    * @return LENGTH
    */
    public Double getLength() {
        return getDouble(D_Length);
    }

	/**
    * 设置宽（米）
    * @param WIDTH
    */
	public void setWidth(Double value) {
        set(D_Width,value);
    }
	
   /**
    * 获取宽（米）
    * @return WIDTH
    */
    public Double getWidth() {
        return getDouble(D_Width);
    }

	/**
    * 设置高（米）
    * @param HEIGHT
    */
	public void setHeight(Double value) {
        set(D_Height,value);
    }
	
   /**
    * 获取高（米）
    * @return HEIGHT
    */
    public Double getHeight() {
        return getDouble(D_Height);
    }

   /**
    * 设置猪只类型
    * @param HOUSE_CLASS
    */
	public void setHouseClass(Long value) {
        set(D_HouseClass,value);
    }
	
	/**
    * 获取猪只类型
    * @return HOUSE_CLASS
    */
    public Long getHouseClass() {
        return getLong(D_HouseClass);
    }

   /**
    * 设置折旧期
    * @param DEPRECIATION_POLICY
    */
	public void setDepreciationPolicy(Long value) {
        set(D_DepreciationPolicy,value);
    }
	
	/**
    * 获取折旧期
    * @return DEPRECIATION_POLICY
    */
    public Long getDepreciationPolicy() {
        return getLong(D_DepreciationPolicy);
    }

	/**
    * 设置造价
    * @param COST
    */
	public void setCost(Double value) {
        set(D_Cost,value);
    }
	
   /**
    * 获取造价
    * @return COST
    */
    public Double getCost() {
        return getDouble(D_Cost);
    }

   /**
    * 设置栏数
    * @param PEN_NUM
    */
	public void setPenNum(Long value) {
        set(D_PenNum,value);
    }
	
	/**
    * 获取栏数
    * @return PEN_NUM
    */
    public Long getPenNum() {
        return getLong(D_PenNum);
    }

   /**
    * 设置最大栏数
    * @param MAX_CAPACITY
    */
	public void setMaxCapacity(Long value) {
        set(D_MaxCapacity,value);
    }
	
	/**
    * 获取最大栏数
    * @return MAX_CAPACITY
    */
    public Long getMaxCapacity() {
        return getLong(D_MaxCapacity);
    }

	/**
    * 设置猪舍编号前缀（SAP中就1位）
    * @param HOUSE_PRIFIX_CODE
    */
	public void setHousePrifixCode(String value) {
        set(D_HousePrifixCode,value);
    }
	
   /**
    * 获取猪舍编号前缀（SAP中就1位）
    * @return HOUSE_PRIFIX_CODE
    */
    public String getHousePrifixCode() {
        return getString(D_HousePrifixCode);
    }

   /**
    * 设置猪舍单元号
    * @param HOUSE_UNIT_NUMBER
    */
	public void setHouseUnitNumber(Long value) {
        set(D_HouseUnitNumber,value);
    }
	
	/**
    * 获取猪舍单元号
    * @return HOUSE_UNIT_NUMBER
    */
    public Long getHouseUnitNumber() {
        return getLong(D_HouseUnitNumber);
    }

   /**
    * 设置猪舍栋号
    * @param HOUSE_BUILDING_NUMBER
    */
	public void setHouseBuildingNumber(Long value) {
        set(D_HouseBuildingNumber,value);
    }
	
	/**
    * 获取猪舍栋号
    * @return HOUSE_BUILDING_NUMBER
    */
    public Long getHouseBuildingNumber() {
        return getLong(D_HouseBuildingNumber);
    }

	/**
    * 设置是否是培育舍 Y/N
    * @param IS_FOSTER_HOUSE
    */
	public void setIsFosterHouse(String value) {
        set(D_IsFosterHouse,value);
    }
	
   /**
    * 获取是否是培育舍 Y/N
    * @return IS_FOSTER_HOUSE
    */
    public String getIsFosterHouse() {
        return getString(D_IsFosterHouse);
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
        propertes.add("businessCode");
        propertes.add("houseName");
        propertes.add("breederId");
        propertes.add("houseType");
        propertes.add("lineId");
        propertes.add("pigClass");
        propertes.add("disinfectDay");
        propertes.add("disinfectMethod");
        propertes.add("columnNum");
        propertes.add("shape");
        propertes.add("area");
        propertes.add("length");
        propertes.add("width");
        propertes.add("height");
        propertes.add("houseClass");
        propertes.add("depreciationPolicy");
        propertes.add("cost");
        propertes.add("penNum");
        propertes.add("maxCapacity");
        propertes.add("housePrifixCode");
        propertes.add("houseUnitNumber");
        propertes.add("houseBuildingNumber");
        propertes.add("isFosterHouse");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




