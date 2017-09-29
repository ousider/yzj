package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:11
 *       表：PP_O_PIGPEN
 */
public class PigpenModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -117718262735243673L;

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

    // 产线ID
    private static final String D_LineId="lineId";

    // 猪舍ID
    private static final String D_HouseId="houseId";

    // 栏位代码
    private static final String D_BusinessCode="businessCode";

    // 栏位名字
    private static final String D_PigpenName="pigpenName";

    // 长度
    private static final String D_Length="length";

    // 宽度
    private static final String D_Width="width";

    // 养猪数
    private static final String D_PigNum="pigNum";
	

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
     * 设置产线ID
     * 
     * @param LINE_ID
     */
	public void setLineId(Long value) {
        set(D_LineId,value);
    }
	
	/**
     * 获取产线ID
     * 
     * @return LINE_ID
     */
    public Long getLineId() {
        return getLong(D_LineId);
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
     * 设置栏位代码
     * 
     * @param BUSINESS_CODE
     */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
    /**
     * 获取栏位代码
     * 
     * @return BUSINESS_CODE
     */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

	/**
     * 设置栏位名字
     * 
     * @param PIGPEN_NAME
     */
	public void setPigpenName(String value) {
        set(D_PigpenName,value);
    }
	
    /**
     * 获取栏位名字
     * 
     * @return PIGPEN_NAME
     */
    public String getPigpenName() {
        return getString(D_PigpenName);
    }

	/**
     * 设置长度
     * 
     * @param LENGTH
     */
	public void setLength(Double value) {
        set(D_Length,value);
    }
	
    /**
     * 获取长度
     * 
     * @return LENGTH
     */
    public Double getLength() {
        return getDouble(D_Length);
    }

	/**
     * 设置宽度
     * 
     * @param WIDTH
     */
	public void setWidth(Double value) {
        set(D_Width,value);
    }
	
    /**
     * 获取宽度
     * 
     * @return WIDTH
     */
    public Double getWidth() {
        return getDouble(D_Width);
    }

    /**
     * 设置养猪数
     * 
     * @param PIG_NUM
     */
	public void setPigNum(Long value) {
        set(D_PigNum,value);
    }
	
	/**
     * 获取养猪数
     * 
     * @return PIG_NUM
     */
    public Long getPigNum() {
        return getLong(D_PigNum);
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
	        setPropertes(D_FarmId);
	        setPropertes(D_CompanyId);
	        setPropertes(D_LineId);
	        setPropertes(D_HouseId);
	        setPropertes(D_BusinessCode);
	        setPropertes(D_PigpenName);
	        setPropertes(D_Length);
	        setPropertes(D_Width);
	        setPropertes(D_PigNum);
	    }
	    return super.getPropertes();
	}

}




