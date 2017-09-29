package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:15:42
 *       表：CD_L_PIG_CLASS
 */
public class PigClassModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 7301443849734935693L;

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
	 //businessCode 
    private static final String D_BusinessCode="businessCode";
	 //pigClassName 
    private static final String D_PigClassName="pigClassName";
	 //pigType 
    private static final String D_PigType="pigType";
	 //beginAgeDay 
    private static final String D_BeginAgeDay="beginAgeDay";
	 //endAgeDay 
    private static final String D_EndAgeDay="endAgeDay";
	 //minWeight 
    private static final String D_MinWeight="minWeight";
	 //maxWeight 
    private static final String D_MaxWeight="maxWeight";
	 //description 
    private static final String D_Description="description";
	

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
     * 设置businessCode
     * 
     * @param BUSINESS_CODE
     */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
    /**
     * 获取businessCode
     * 
     * @return BUSINESS_CODE
     */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

	/**
     * 设置pigClassName
     * 
     * @param PIG_CLASS_NAME
     */
	public void setPigClassName(String value) {
        set(D_PigClassName,value);
    }
	
    /**
     * 获取pigClassName
     * 
     * @return PIG_CLASS_NAME
     */
    public String getPigClassName() {
        return getString(D_PigClassName);
    }

	/**
     * 设置pigType
     * 
     * @param PIG_TYPE
     */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
    /**
     * 获取pigType
     * 
     * @return PIG_TYPE
     */
    public String getPigType() {
        return getString(D_PigType);
    }

	/**
     * 设置beginAgeDay
     * 
     * @param BEGIN_AGE_DAY
     */
	public void setBeginAgeDay(Double value) {
        set(D_BeginAgeDay,value);
    }
	
    /**
     * 获取beginAgeDay
     * 
     * @return BEGIN_AGE_DAY
     */
    public Double getBeginAgeDay() {
        return getDouble(D_BeginAgeDay);
    }

	/**
     * 设置endAgeDay
     * 
     * @param END_AGE_DAY
     */
	public void setEndAgeDay(Double value) {
        set(D_EndAgeDay,value);
    }
	
    /**
     * 获取endAgeDay
     * 
     * @return END_AGE_DAY
     */
    public Double getEndAgeDay() {
        return getDouble(D_EndAgeDay);
    }

	/**
     * 设置minWeight
     * 
     * @param MIN_WEIGHT
     */
	public void setMinWeight(Double value) {
        set(D_MinWeight,value);
    }
	
    /**
     * 获取minWeight
     * 
     * @return MIN_WEIGHT
     */
    public Double getMinWeight() {
        return getDouble(D_MinWeight);
    }

	/**
     * 设置maxWeight
     * 
     * @param MAX_WEIGHT
     */
	public void setMaxWeight(Double value) {
        set(D_MaxWeight,value);
    }
	
    /**
     * 获取maxWeight
     * 
     * @return MAX_WEIGHT
     */
    public Double getMaxWeight() {
        return getDouble(D_MaxWeight);
    }

	/**
     * 设置description
     * 
     * @param DESCRIPTION
     */
	public void setDescription(String value) {
        set(D_Description,value);
    }
	
    /**
     * 获取description
     * 
     * @return DESCRIPTION
     */
    public String getDescription() {
        return getString(D_Description);
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
	        setPropertes(D_BusinessCode);
	        setPropertes(D_PigClassName);
	        setPropertes(D_PigType);
	        setPropertes(D_BeginAgeDay);
	        setPropertes(D_EndAgeDay);
	        setPropertes(D_MinWeight);
	        setPropertes(D_MaxWeight);
	        setPropertes(D_Description);
	    }
	    return super.getPropertes();
	}

}




