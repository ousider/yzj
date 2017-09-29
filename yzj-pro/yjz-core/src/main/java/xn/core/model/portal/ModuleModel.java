package xn.core.model.portal;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 8:52:06
 *       表：CD_O_MODULE
 */
public class ModuleModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -5145417752370107109L;

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

    // 编号
    private static final String D_ModuleCode="moduleCode";

    // 模块名：能够描述此模块信息的名称。
    private static final String D_ModuleName="moduleName";

    // 模块简称：如果名称过长，有些地方可以用简称来代替。
    private static final String D_SortName="sortName";

    // 模块英文名称：万一要制作英文版，可以用英文名称。
    private static final String D_ModuleEnNa="moduleEnNa";

    // 模块英文名简称：也可以用作生成编码字段。
    private static final String D_SortEnNa="sortEnNa";

    // 图标地址
    private static final String D_IconCls="iconCls";

    // 图标字体
    private static final String D_Glyph="glyph";

    // 点击事件：展开、展开并打开功能页面、打开功能页面
    private static final String D_ClickEvent="clickEvent";

    // 类型 1：普通菜单| 2：首页
    private static final String D_MenuType="menuType";

    // 功能模块名
    private static final String D_Component="component";

    // 模块访问地址
    private static final String D_ModuleUrl="moduleUrl";

    // 能否使用Y/N
    private static final String D_UsingFlag="usingFlag";

    // 是否快捷菜单 Y/N
    private static final String D_IsQuickMenu="isQuickMenu";
	

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
     * 设置编号
     * 
     * @param MODULE_CODE
     */
	public void setModuleCode(String value) {
        set(D_ModuleCode,value);
    }
	
    /**
     * 获取编号
     * 
     * @return MODULE_CODE
     */
    public String getModuleCode() {
        return getString(D_ModuleCode);
    }

	/**
     * 设置模块名：能够描述此模块信息的名称。
     * 
     * @param MODULE_NAME
     */
	public void setModuleName(String value) {
        set(D_ModuleName,value);
    }
	
    /**
     * 获取模块名：能够描述此模块信息的名称。
     * 
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return getString(D_ModuleName);
    }

	/**
     * 设置模块简称：如果名称过长，有些地方可以用简称来代替。
     * 
     * @param SORT_NAME
     */
	public void setSortName(String value) {
        set(D_SortName,value);
    }
	
    /**
     * 获取模块简称：如果名称过长，有些地方可以用简称来代替。
     * 
     * @return SORT_NAME
     */
    public String getSortName() {
        return getString(D_SortName);
    }

	/**
     * 设置模块英文名称：万一要制作英文版，可以用英文名称。
     * 
     * @param MODULE_EN_NA
     */
	public void setModuleEnNa(String value) {
        set(D_ModuleEnNa,value);
    }
	
    /**
     * 获取模块英文名称：万一要制作英文版，可以用英文名称。
     * 
     * @return MODULE_EN_NA
     */
    public String getModuleEnNa() {
        return getString(D_ModuleEnNa);
    }

	/**
     * 设置模块英文名简称：也可以用作生成编码字段。
     * 
     * @param SORT_EN_NA
     */
	public void setSortEnNa(String value) {
        set(D_SortEnNa,value);
    }
	
    /**
     * 获取模块英文名简称：也可以用作生成编码字段。
     * 
     * @return SORT_EN_NA
     */
    public String getSortEnNa() {
        return getString(D_SortEnNa);
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
     * 设置点击事件：展开、展开并打开功能页面、打开功能页面
     * 
     * @param CLICK_EVENT
     */
	public void setClickEvent(String value) {
        set(D_ClickEvent,value);
    }
	
    /**
     * 获取点击事件：展开、展开并打开功能页面、打开功能页面
     * 
     * @return CLICK_EVENT
     */
    public String getClickEvent() {
        return getString(D_ClickEvent);
    }

	/**
     * 设置类型 1：普通菜单| 2：首页
     * 
     * @param MENU_TYPE
     */
	public void setMenuType(String value) {
        set(D_MenuType,value);
    }
	
    /**
     * 获取类型 1：普通菜单| 2：首页
     * 
     * @return MENU_TYPE
     */
    public String getMenuType() {
        return getString(D_MenuType);
    }

	/**
     * 设置功能模块名
     * 
     * @param COMPONENT
     */
	public void setComponent(String value) {
        set(D_Component,value);
    }
	
    /**
     * 获取功能模块名
     * 
     * @return COMPONENT
     */
    public String getComponent() {
        return getString(D_Component);
    }

	/**
     * 设置模块访问地址
     * 
     * @param MODULE_URL
     */
	public void setModuleUrl(String value) {
        set(D_ModuleUrl,value);
    }
	
    /**
     * 获取模块访问地址
     * 
     * @return MODULE_URL
     */
    public String getModuleUrl() {
        return getString(D_ModuleUrl);
    }

	/**
     * 设置能否使用Y/N
     * 
     * @param USING_FLAG
     */
	public void setUsingFlag(String value) {
        set(D_UsingFlag,value);
    }
	
    /**
     * 获取能否使用Y/N
     * 
     * @return USING_FLAG
     */
    public String getUsingFlag() {
        return getString(D_UsingFlag);
    }

	/**
     * 设置是否快捷菜单 Y/N
     * 
     * @param IS_QUICK_MENU
     */
	public void setIsQuickMenu(String value) {
        set(D_IsQuickMenu,value);
    }
	
    /**
     * 获取是否快捷菜单 Y/N
     * 
     * @return IS_QUICK_MENU
     */
    public String getIsQuickMenu() {
        return getString(D_IsQuickMenu);
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
	        setPropertes(D_ModuleCode);
	        setPropertes(D_ModuleName);
	        setPropertes(D_SortName);
	        setPropertes(D_ModuleEnNa);
	        setPropertes(D_SortEnNa);
	        setPropertes(D_IconCls);
	        setPropertes(D_Glyph);
	        setPropertes(D_ClickEvent);
	        setPropertes(D_MenuType);
	        setPropertes(D_Component);
	        setPropertes(D_ModuleUrl);
	        setPropertes(D_UsingFlag);
	        setPropertes(D_IsQuickMenu);
	    }
	    return super.getPropertes();
	}

}




