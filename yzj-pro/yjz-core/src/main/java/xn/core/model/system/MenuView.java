package xn.core.model.system;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-5-17 15:08:10
 *       remarks :根据用户名查询菜单
 */
public class MenuView  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -9048865121453370978L;

    // 行号: 系统保留字段，标识一条数据记录。
    private final String D_RowId = "rowId";

    // 上级菜单ID
    private final String D_ParentId="parentId";

    // 类型
    private final String D_MenuType="menuType";

    // 编号
    private final String D_ModuleCode="moduleCode";

    // 模块英文名称：万一要制作英文版，可以用英文名称。
    private final String D_ModuleEnNa="moduleEnNa";

    // 模块英文名简称：也可以用作生成编码字段。
    private final String D_SortEnNa="sortEnNa";

    // 模块ID
    private final String D_ModuleId="moduleId";

    // 菜单级别
    private final String D_LevelNum="levelNum";

    // 模块名：能够描述此模块信息的名称。
    private final String D_ModuleName="moduleName";

    // 模块访问地址
    private final String D_ModuleUrl="moduleUrl";

    // 图标字体
    private final String D_Glyph="glyph";

    // 图标地址
    private final String D_IconCls="iconCls";

    // 点击事件：展开、展开并打开功能页面、打开功能页面
    private final String D_ClickEvent="clickEvent";

    private final String D_Component = "component";

    // 是否是快捷菜单
    private final String D_IsQuickMenu = "isQuickMenu";

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
    public void setRowId(Long value) {
        set(D_RowId, value);
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
     * 设置上级菜单ID
     * 
     * @param PARENT_ID
     */
	public void setParentId(Long value) {
        set(D_ParentId,value);
    }
	
	/**
     * 获取上级菜单ID
     * 
     * @return PARENT_ID
     */
    public Long getParentId() {
        return getLong(D_ParentId);
    }

	/**
     * 设置类型
     * 
     * @param MENU_TYPE
     */
	public void setMenuType(String value) {
        set(D_MenuType,value);
    }
	
    /**
     * 获取类型
     * 
     * @return MENU_TYPE
     */
    public String getMenuType() {
        return getString(D_MenuType);
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
     * 设置模块ID
     * 
     * @param MODULE_ID
     */
	public void setModuleId(Long value) {
        set(D_ModuleId,value);
    }
	
	/**
     * 获取模块ID
     * 
     * @return MODULE_ID
     */
    public Long getModuleId() {
        return getLong(D_ModuleId);
    }

    /**
     * 设置菜单级别
     * 
     * @param LEVEL_NUM
     */
    public void setLevelNum(Integer value) {
        set(D_LevelNum,value);
    }
	
	/**
     * 获取菜单级别
     * 
     * @return LEVEL_NUM
     */
    public Integer getLevelNum() {
        return getInteger(D_LevelNum);
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
	
    public void setComponent(String value) {
        set(D_Component, value);
    }

    public String getComponent() {
        return getString(D_Component);
    }
	
    public void setIsQuickMenu(String value) {
        set(D_IsQuickMenu, value);
    }

    public String getIsQuickMenu() {
        return getString(D_IsQuickMenu);
    }

    public List<String> getPropertes() {
        if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
            setPropertes(D_RowId);
            setPropertes(D_ParentId);
            setPropertes(D_MenuType);
            setPropertes(D_ModuleCode);
            setPropertes(D_ModuleEnNa);
            setPropertes(D_SortEnNa);
            setPropertes(D_ModuleId);
            setPropertes(D_LevelNum);
            setPropertes(D_ModuleName);
            setPropertes(D_ModuleUrl);
            setPropertes(D_Glyph);
            setPropertes(D_IconCls);
            setPropertes(D_ClickEvent);
            setPropertes(D_Component);
            setPropertes(D_IsQuickMenu);
        }
        return super.getPropertes();
    }

}




