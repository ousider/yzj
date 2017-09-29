
package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-6-1 Time:11:04:36 表：CdRmenus
 */
public class MenusView extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 关联ID
	private Long correlationId;
	// 根目录编号
	private Long subsystemId;
	// 菜单级别
	private Integer levelNum;
	// 上级菜单ID
	private Long parentId;
	// 模块ID
	private Long moduleId;
	// 菜单模板ID
	private Long templateId;
	// 按钮
	private List<ButtonView> btns;

	// 模块名称
	private String moduleName;

	//权限Id
	private Long  limitId;  
	
	//权限 [对象类型]
	private  String  objType;
	
	// 是否选择
	private boolean checked = false;

	/**
	 * 设置关联ID
	 * 
	 * @param CORRELATION_ID
	 *            - 关联ID
	 */
	public void setCorrelationId(Long value) {
		this.correlationId = value;
	}

	/**
	 * 获取关联ID
	 * 
	 * @return CORRELATION_ID - 关联ID
	 */
	public Long getCorrelationId() {
		return this.correlationId;
	}

	/**
	 * 设置根目录编号
	 * 
	 * @param SUBSYSTEM_ID
	 *            - 根目录编号
	 */
	public void setSubsystemId(Long value) {
		this.subsystemId = value;
	}

	/**
	 * 获取根目录编号
	 * 
	 * @return SUBSYSTEM_ID - 根目录编号
	 */
	public Long getSubsystemId() {
		return this.subsystemId;
	}

	/**
	 * 设置菜单级别
	 * 
	 * @param LEVEL_NUM
	 *            - 菜单级别
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
	 * @param PARENT_ID
	 *            - 上级菜单ID
	 */
	public void setParentId(Long value) {
		this.parentId = value;
	}

	/**
	 * 获取上级菜单ID
	 * 
	 * @return PARENT_ID - 上级菜单ID
	 */
	public Long getParentId() {
		return this.parentId;
	}

	/**
	 * 设置模块ID
	 * 
	 * @param MODULE_ID
	 *            - 模块ID
	 */
	public void setModuleId(Long value) {
		this.moduleId = value;
	}

	/**
	 * 获取模块ID
	 * 
	 * @return MODULE_ID - 模块ID
	 */
	public Long getModuleId() {
		return this.moduleId;
	}

	/**
	 * 设置菜单模板ID
	 * 
	 * @param TEMPLATE_ID
	 *            - 菜单模板ID
	 */
	public void setTemplateId(Long value) {
		this.templateId = value;
	}

	/**
	 * 获取菜单模板ID
	 * 
	 * @return TEMPLATE_ID - 菜单模板ID
	 */
	public Long getTemplateId() {
		return this.templateId;
	}

	public List<ButtonView> getBtns() {
		return btns;
	}

	public void setBtns(List<ButtonView> btns) {
		this.btns = btns;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getLimitId() {
		return limitId;
	}

	public void setLimitId(Long limitId) {
		this.limitId = limitId;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}
	
}
