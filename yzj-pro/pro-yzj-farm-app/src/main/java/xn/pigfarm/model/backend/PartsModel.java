package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:15:42
 *       表：CD_O_PARTS
 */
public class PartsModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 7496170131722070348L;

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

    // 部件编号
    private static final String D_PartsCode="partsCode";

    // 部件名称
    private static final String D_PartsName="partsName";

    // 模块ID：能够描述此模块信息的ID。
    private static final String D_ModuleId="moduleId";

    // 图标地址
    private static final String D_IconCls="iconCls";

    // 图标字体
    private static final String D_Glyph="glyph";

    // 能否使用 Y/N
    private static final String D_UsingFlag="usingFlag";
	

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
     * 设置部件编号
     * 
     * @param PARTS_CODE
     */
	public void setPartsCode(String value) {
        set(D_PartsCode,value);
    }
	
    /**
     * 获取部件编号
     * 
     * @return PARTS_CODE
     */
    public String getPartsCode() {
        return getString(D_PartsCode);
    }

	/**
     * 设置部件名称
     * 
     * @param PARTS_NAME
     */
	public void setPartsName(String value) {
        set(D_PartsName,value);
    }
	
    /**
     * 获取部件名称
     * 
     * @return PARTS_NAME
     */
    public String getPartsName() {
        return getString(D_PartsName);
    }

    /**
     * 设置模块ID：能够描述此模块信息的ID。
     * 
     * @param MODULE_ID
     */
	public void setModuleId(Long value) {
        set(D_ModuleId,value);
    }
	
	/**
     * 获取模块ID：能够描述此模块信息的ID。
     * 
     * @return MODULE_ID
     */
    public Long getModuleId() {
        return getLong(D_ModuleId);
    }

	/**
     * 设置图标地址
     * 
     * @param ICON_CLS
     */
	public void setIconCls(String value) {
        set(D_IconCls,value);
    }
	
    /**
     * 获取图标地址
     * 
     * @return ICON_CLS
     */
    public String getIconCls() {
        return getString(D_IconCls);
    }

	/**
     * 设置图标字体
     * 
     * @param GLYPH
     */
	public void setGlyph(String value) {
        set(D_Glyph,value);
    }
	
    /**
     * 获取图标字体
     * 
     * @return GLYPH
     */
    public String getGlyph() {
        return getString(D_Glyph);
    }

	/**
     * 设置能否使用 Y/N
     * 
     * @param USING_FLAG
     */
	public void setUsingFlag(String value) {
        set(D_UsingFlag,value);
    }
	
    /**
     * 获取能否使用 Y/N
     * 
     * @return USING_FLAG
     */
    public String getUsingFlag() {
        return getString(D_UsingFlag);
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
	        setPropertes(D_PartsCode);
	        setPropertes(D_PartsName);
	        setPropertes(D_ModuleId);
	        setPropertes(D_IconCls);
	        setPropertes(D_Glyph);
	        setPropertes(D_UsingFlag);
	    }
	    return super.getPropertes();
	}

}




