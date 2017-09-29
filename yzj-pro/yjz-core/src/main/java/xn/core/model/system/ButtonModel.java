package xn.core.model.system;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 8:56:22
 *       表：CD_O_BUTTON
 */
public class ButtonModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 1674271406453020858L;

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

    // 菜单ID MODULE主键
    private static final String D_ModuleId="moduleId";

    // 页面ID
    private static final String D_PageId="pageId";

    // 类型
    private static final String D_BtnType="btnType";

    // 按钮编码
    private static final String D_BtnCode="btnCode";

    // 名称
    private static final String D_BtnName="btnName";

    // 图标
    private static final String D_IconCls="iconCls";

    // 方法名
    private static final String D_FunName="funName";
	

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
     * 设置菜单ID MODULE主键
     * 
     * @param MODULE_ID
     */
	public void setModuleId(Long value) {
        set(D_ModuleId,value);
    }
	
	/**
     * 获取菜单ID MODULE主键
     * 
     * @return MODULE_ID
     */
    public Long getModuleId() {
        return getLong(D_ModuleId);
    }

    /**
     * 设置页面ID
     * 
     * @param PAGE_ID
     */
	public void setPageId(Long value) {
        set(D_PageId,value);
    }
	
	/**
     * 获取页面ID
     * 
     * @return PAGE_ID
     */
    public Long getPageId() {
        return getLong(D_PageId);
    }

	/**
     * 设置类型
     * 
     * @param BTN_TYPE
     */
	public void setBtnType(String value) {
        set(D_BtnType,value);
    }
	
    /**
     * 获取类型
     * 
     * @return BTN_TYPE
     */
    public String getBtnType() {
        return getString(D_BtnType);
    }

	/**
     * 设置按钮编码
     * 
     * @param BTN_CODE
     */
	public void setBtnCode(String value) {
        set(D_BtnCode,value);
    }
	
    /**
     * 获取按钮编码
     * 
     * @return BTN_CODE
     */
    public String getBtnCode() {
        return getString(D_BtnCode);
    }

	/**
     * 设置名称
     * 
     * @param BTN_NAME
     */
	public void setBtnName(String value) {
        set(D_BtnName,value);
    }
	
    /**
     * 获取名称
     * 
     * @return BTN_NAME
     */
    public String getBtnName() {
        return getString(D_BtnName);
    }

	/**
     * 设置图标
     * 
     * @param ICON_CLS
     */
	public void setIconCls(String value) {
        set(D_IconCls,value);
    }
	
    /**
     * 获取图标
     * 
     * @return ICON_CLS
     */
    public String getIconCls() {
        return getString(D_IconCls);
    }

	/**
     * 设置方法名
     * 
     * @param FUN_NAME
     */
	public void setFunName(String value) {
        set(D_FunName,value);
    }
	
    /**
     * 获取方法名
     * 
     * @return FUN_NAME
     */
    public String getFunName() {
        return getString(D_FunName);
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
	        setPropertes(D_ModuleId);
	        setPropertes(D_PageId);
	        setPropertes(D_BtnType);
	        setPropertes(D_BtnCode);
	        setPropertes(D_BtnName);
	        setPropertes(D_IconCls);
	        setPropertes(D_FunName);
	    }
	    return super.getPropertes();
	}

}




