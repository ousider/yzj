package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:15:43
 *       表：CD_L_PIG_HOUSE
 */
public class PigHouseModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -7823324443776876628L;

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
	 //houseTypeName 
    private static final String D_HouseTypeName="houseTypeName";

    // 猪舍可以入的猪类别的ID，cd_l_pig_class的row_id,以逗号分隔
    private static final String D_PigClass="pigClass";
	 //eventId 
    private static final String D_EventId="eventId";

    // 清洗消毒天数
    private static final String D_DisinfectDay="disinfectDay";

    // 消毒方法
    private static final String D_DisinfectMethod="disinfectMethod";

    // 每栏容量
    private static final String D_ColumnNum="columnNum";

    // 形状
    private static final String D_Shape="shape";
	 //length 
    private static final String D_Length="length";
	 //width 
    private static final String D_Width="width";
	 //height 
    private static final String D_Height="height";
	

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
     * 设置houseTypeName
     * 
     * @param HOUSE_TYPE_NAME
     */
	public void setHouseTypeName(String value) {
        set(D_HouseTypeName,value);
    }
	
    /**
     * 获取houseTypeName
     * 
     * @return HOUSE_TYPE_NAME
     */
    public String getHouseTypeName() {
        return getString(D_HouseTypeName);
    }

	/**
     * 设置猪舍可以入的猪类别的ID，cd_l_pig_class的row_id,以逗号分隔
     * 
     * @param PIG_CLASS
     */
	public void setPigClass(String value) {
        set(D_PigClass,value);
    }
	
    /**
     * 获取猪舍可以入的猪类别的ID，cd_l_pig_class的row_id,以逗号分隔
     * 
     * @return PIG_CLASS
     */
    public String getPigClass() {
        return getString(D_PigClass);
    }

	/**
     * 设置eventId
     * 
     * @param EVENT_ID
     */
	public void setEventId(String value) {
        set(D_EventId,value);
    }
	
    /**
     * 获取eventId
     * 
     * @return EVENT_ID
     */
    public String getEventId() {
        return getString(D_EventId);
    }

    /**
     * 设置清洗消毒天数
     * 
     * @param DISINFECT_DAY
     */
	public void setDisinfectDay(Long value) {
        set(D_DisinfectDay,value);
    }
	
	/**
     * 获取清洗消毒天数
     * 
     * @return DISINFECT_DAY
     */
    public Long getDisinfectDay() {
        return getLong(D_DisinfectDay);
    }

	/**
     * 设置消毒方法
     * 
     * @param DISINFECT_METHOD
     */
	public void setDisinfectMethod(String value) {
        set(D_DisinfectMethod,value);
    }
	
    /**
     * 获取消毒方法
     * 
     * @return DISINFECT_METHOD
     */
    public String getDisinfectMethod() {
        return getString(D_DisinfectMethod);
    }

    /**
     * 设置每栏容量
     * 
     * @param COLUMN_NUM
     */
	public void setColumnNum(Long value) {
        set(D_ColumnNum,value);
    }
	
	/**
     * 获取每栏容量
     * 
     * @return COLUMN_NUM
     */
    public Long getColumnNum() {
        return getLong(D_ColumnNum);
    }

	/**
     * 设置形状
     * 
     * @param SHAPE
     */
	public void setShape(String value) {
        set(D_Shape,value);
    }
	
    /**
     * 获取形状
     * 
     * @return SHAPE
     */
    public String getShape() {
        return getString(D_Shape);
    }

	/**
     * 设置length
     * 
     * @param LENGTH
     */
	public void setLength(Double value) {
        set(D_Length,value);
    }
	
    /**
     * 获取length
     * 
     * @return LENGTH
     */
    public Double getLength() {
        return getDouble(D_Length);
    }

	/**
     * 设置width
     * 
     * @param WIDTH
     */
	public void setWidth(Double value) {
        set(D_Width,value);
    }
	
    /**
     * 获取width
     * 
     * @return WIDTH
     */
    public Double getWidth() {
        return getDouble(D_Width);
    }

	/**
     * 设置height
     * 
     * @param HEIGHT
     */
	public void setHeight(Double value) {
        set(D_Height,value);
    }
	
    /**
     * 获取height
     * 
     * @return HEIGHT
     */
    public Double getHeight() {
        return getDouble(D_Height);
    }
	
	
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_SortNbr);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_OriginFlag);
	        setPropertes(D_OriginApp);
	        setPropertes(D_Notes);
	        setPropertes(D_HouseTypeName);
	        setPropertes(D_PigClass);
	        setPropertes(D_EventId);
	        setPropertes(D_DisinfectDay);
	        setPropertes(D_DisinfectMethod);
	        setPropertes(D_ColumnNum);
	        setPropertes(D_Shape);
	        setPropertes(D_Length);
	        setPropertes(D_Width);
	        setPropertes(D_Height);
	    }
	    return super.getPropertes();
	}

}




