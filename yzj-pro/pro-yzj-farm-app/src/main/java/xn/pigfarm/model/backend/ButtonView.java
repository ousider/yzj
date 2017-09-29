
package xn.pigfarm.model.backend;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-6-1 Time:11:10:28 表：CdObutton
 */
public class ButtonView extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 菜单ID
	private Long moduleId;
	// 页面ID
	private Long pageId;
	// 类型
	private String btnType;
	// 按钮编码
	private String btnCode;
	// 名称
	private String btnName;
	// 图标
	private String iconCls;
	// 方法名
	private String funName;	
	// 是否选择
	private boolean checked=false;
	
	//权限Id
	private Long  limitId;  
		
	//权限 [对象类型]
	private  String  objType;

	/**
	 * 设置菜单ID
	 * 
	 * @param MODULE_ID
	 *            - 菜单ID
	 */
	public void setModuleId(Long value) {
		this.moduleId = value;
	}

	/**
	 * 获取菜单ID
	 * 
	 * @return MODULE_ID - 菜单ID
	 */
	public Long getModuleId() {
		return this.moduleId;
	}

	/**
	 * 设置页面ID
	 * 
	 * @param PAGE_ID
	 *            - 页面ID
	 */
	public void setPageId(Long value) {
		this.pageId = value;
	}

	/**
	 * 获取页面ID
	 * 
	 * @return PAGE_ID - 页面ID
	 */
	public Long getPageId() {
		return this.pageId;
	}

	/**
	 * 设置类型
	 * 
	 * @param BTN_TYPE
	 *            - 类型
	 */
	public void setBtnType(String value) {
		this.btnType = value;
	}

	/**
	 * 获取类型
	 * 
	 * @return BTN_TYPE - 类型
	 */
	public String getBtnType() {
		return this.btnType;
	}

	/**
	 * 设置按钮编码
	 * 
	 * @param BTN_CODE
	 *            - 按钮编码
	 */
	public void setBtnCode(String value) {
		this.btnCode = value;
	}

	/**
	 * 获取按钮编码
	 * 
	 * @return BTN_CODE - 按钮编码
	 */
	public String getBtnCode() {
		return this.btnCode;
	}

	/**
	 * 设置名称
	 * 
	 * @param BTN_NAME
	 *            - 名称
	 */
	public void setBtnName(String value) {
		this.btnName = value;
	}

	/**
	 * 获取名称
	 * 
	 * @return BTN_NAME - 名称
	 */
	public String getBtnName() {
		return this.btnName;
	}

	/**
	 * 设置图标
	 * 
	 * @param ICON_CLS
	 *            - 图标
	 */
	public void setIconCls(String value) {
		this.iconCls = value;
	}

	/**
	 * 获取图标
	 * 
	 * @return ICON_CLS - 图标
	 */
	public String getIconCls() {
		return this.iconCls;
	}

	/**
	 * 设置方法名
	 * 
	 * @param FUN_NAME
	 *            - 方法名
	 */
	public void setFunName(String value) {
		this.funName = value;
	}

	/**
	 * 获取方法名
	 * 
	 * @return FUN_NAME - 方法名
	 */
	public String getFunName() {
		return this.funName;
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
