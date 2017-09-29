
package xn.pigfarm.model.backend;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-4-7 Time:9:28:16
 * 表：CdModule
 */
public class CdModuleModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4094349808354013395L;
	
    private Long parentId;
	/**
	 * ID
	 */
    private Long moduleId;
	
    /**
     * 编号
     */
	private String moduleCode;
	
    /**
     * 模块名：能够描述此模块信息的名称。
     */
	private String moduleName;
	
    /**
     * 模块简称：如果名称过长，有些地方可以用简称来代替。
     */
	private String sortName;
	
    /**
     * 模块英文名称：万一要制作英文版，可以用英文名称。
     */
	private String moduleEnNa;
	
    /**
     * 模块英文名简称：也可以用作生成编码字段。
     */
	private String sortEnNa;
	
    /**
     * 图标地址
     */
	private String iconCls;
	
    /**
     * 图标字体
     */
	private String glyph;
	
    /**
     * 点击事件：展开、展开并打开功能页面、打开功能页面
     */
	private String clickEvent;
	
    /**
     * 类型
     */
	private String menuType;
	
    /**
     * 功能
     */
	private String component;
	
    /**
     * 模块内容
     */
	private String moduleUrl;
	
    /**
     * 能否使用
     */
	private String usingFlag;
	
    // 菜单级别
    private Integer levelNum;

    private String menuTypeName;

    public Long getParentId() {
		return parentId;
	}

    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

    public Long getModuleId() {
		return moduleId;
	}

    public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getModuleEnNa() {
		return moduleEnNa;
	}
	public void setModuleEnNa(String moduleEnNa) {
		this.moduleEnNa = moduleEnNa;
	}
	public String getSortEnNa() {
		return sortEnNa;
	}
	public void setSortEnNa(String sortEnNa) {
		this.sortEnNa = sortEnNa;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getGlyph() {
		return glyph;
	}
	public void setGlyph(String glyph) {
		this.glyph = glyph;
	}
	public String getClickEvent() {
		return clickEvent;
	}
	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getModuleUrl() {
		return moduleUrl;
	}
	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}
	public String getUsingFlag() {
		return usingFlag;
	}
	public void setUsingFlag(String usingFlag) {
		this.usingFlag = usingFlag;
	}

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public String getMenuTypeName() {
        return menuTypeName;
    }

    public void setMenuTypeName(String menuTypeName) {
        this.menuTypeName = menuTypeName;
    }

}
