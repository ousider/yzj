
package xn.pigfarm.model.backend;

import java.io.Serializable;

import javax.persistence.Table;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-4-9
 * Time:14:03:09
 * 表：CdRmenus
 */
@Table(name = "cd_r_menus")
public class CdMenusModel extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2833466867593970768L;
	
    /**
     * 关联ID
     */
	private Integer correlationId;
	
    /**
     * 根目录编号
     */
	private Integer subsystemId;
	
    /**
     * 菜单级别
     */
	private Integer levelNum;
	
    /**
     * 上级菜单ID
     */
	private Integer parentId;
	
    /**
     * 模块ID
     */
	private Integer moduleId;
	
    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 菜单模板ID
     */
	private Integer templateId;


	/**
     * 设置关联ID
     * 
     * @param CORRELATION_ID - 关联ID
     */
	public void setCorrelationId(Integer value) {
		this.correlationId = value;
	}
	
    /**
     * 获取关联ID
     * 
     * @return CORRELATION_ID - 关联ID
     */
	public Integer getCorrelationId() {
		return this.correlationId;
	}
	
    /**
     * 设置根目录编号
     * 
     * @param SUBSYSTEM_ID - 根目录编号
     */
	public void setSubsystemId(Integer value) {
		this.subsystemId = value;
	}
	
    /**
     * 获取根目录编号
     * 
     * @return SUBSYSTEM_ID - 根目录编号
     */
	public Integer getSubsystemId() {
		return this.subsystemId;
	}
	
    /**
     * 设置菜单级别
     * 
     * @param LEVEL_NUM - 菜单级别
     */
	public void setLevelNum(Integer value) {
		this.levelNum = value;
	}
	
    /**
     * 获取菜单级别
     * 
     * @return LEVEL_NUM - 菜单级别
     */
	public Integer getLevelNum() {
		return this.levelNum;
	}
	
    /**
     * 设置上级菜单ID
     * 
     * @param PARENT_ID - 上级菜单ID
     */
	public void setParentId(Integer value) {
		this.parentId = value;
	}
	
    /**
     * 获取上级菜单ID
     * 
     * @return PARENT_ID - 上级菜单ID
     */
	public Integer getParentId() {
		return this.parentId;
	}
	
    /**
     * 设置模块ID
     * 
     * @param MODULE_ID - 模块ID
     */
	public void setModuleId(Integer value) {
		this.moduleId = value;
	}
	
    /**
     * 获取模块ID
     * 
     * @return MODULE_ID - 模块ID
     */
	public Integer getModuleId() {
		return this.moduleId;
	}

    /**
     * 设置模块名称
     * 
     * @param MODULE_NAME - 模块名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 获取模块名称
     * 
     * @return MODULE_NAME - 模块名称
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 设置菜单模板ID
     * 
     * @param TEMPLATE_ID - 菜单模板ID
     */
	public void setTemplateId(Integer value) {
		this.templateId = value;
	}
	
    /**
     * 获取菜单模板ID
     * 
     * @return TEMPLATE_ID - 菜单模板ID
     */
	public Integer getTemplateId() {
		return this.templateId;
	}


}




