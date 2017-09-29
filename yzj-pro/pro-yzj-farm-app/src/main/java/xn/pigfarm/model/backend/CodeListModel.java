package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:15:41
 *       表：CD_L_CODE_LIST
 */
public class CodeListModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 8250537872182548612L;

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
	 //typeCode 
    private static final String D_TypeCode="typeCode";
	 //typeName 
    private static final String D_TypeName="typeName";
	 //codeValue 
    private static final String D_CodeValue="codeValue";
	 //codeName 
    private static final String D_CodeName="codeName";

    // 上级分类代码
    private static final String D_SupType="supType";

    // 上级分类代码关联值
    private static final String D_LinkValue="linkValue";

    // 是否默认值: Y 默认值，N 非默认值，一类代码最多仅有一个为''Y''。
    private static final String D_IsDefault="isDefault";
	

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
     * 设置typeCode
     * 
     * @param TYPE_CODE
     */
	public void setTypeCode(String value) {
        set(D_TypeCode,value);
    }
	
    /**
     * 获取typeCode
     * 
     * @return TYPE_CODE
     */
    public String getTypeCode() {
        return getString(D_TypeCode);
    }

	/**
     * 设置typeName
     * 
     * @param TYPE_NAME
     */
	public void setTypeName(String value) {
        set(D_TypeName,value);
    }
	
    /**
     * 获取typeName
     * 
     * @return TYPE_NAME
     */
    public String getTypeName() {
        return getString(D_TypeName);
    }

	/**
     * 设置codeValue
     * 
     * @param CODE_VALUE
     */
	public void setCodeValue(String value) {
        set(D_CodeValue,value);
    }
	
    /**
     * 获取codeValue
     * 
     * @return CODE_VALUE
     */
    public String getCodeValue() {
        return getString(D_CodeValue);
    }

	/**
     * 设置codeName
     * 
     * @param CODE_NAME
     */
	public void setCodeName(String value) {
        set(D_CodeName,value);
    }
	
    /**
     * 获取codeName
     * 
     * @return CODE_NAME
     */
    public String getCodeName() {
        return getString(D_CodeName);
    }

	/**
     * 设置上级分类代码
     * 
     * @param SUP_TYPE
     */
	public void setSupType(String value) {
        set(D_SupType,value);
    }
	
    /**
     * 获取上级分类代码
     * 
     * @return SUP_TYPE
     */
    public String getSupType() {
        return getString(D_SupType);
    }

	/**
     * 设置上级分类代码关联值
     * 
     * @param LINK_VALUE
     */
	public void setLinkValue(String value) {
        set(D_LinkValue,value);
    }
	
    /**
     * 获取上级分类代码关联值
     * 
     * @return LINK_VALUE
     */
    public String getLinkValue() {
        return getString(D_LinkValue);
    }

	/**
     * 设置是否默认值: Y 默认值，N 非默认值，一类代码最多仅有一个为''Y''。
     * 
     * @param IS_DEFAULT
     */
	public void setIsDefault(String value) {
        set(D_IsDefault,value);
    }
	
    /**
     * 获取是否默认值: Y 默认值，N 非默认值，一类代码最多仅有一个为''Y''。
     * 
     * @return IS_DEFAULT
     */
    public String getIsDefault() {
        return getString(D_IsDefault);
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
	        setPropertes(D_TypeCode);
	        setPropertes(D_TypeName);
	        setPropertes(D_CodeValue);
	        setPropertes(D_CodeName);
	        setPropertes(D_SupType);
	        setPropertes(D_LinkValue);
	        setPropertes(D_IsDefault);
	    }
	    return super.getPropertes();
	}

}




