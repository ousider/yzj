
package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import xn.core.model.BaseModel;
import xn.core.model.portal.ModuleModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-4-9 Time:14:03:08 表：CdOrole
 */
public class RoleView extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3581199618711808431L;
	// 角色编码
	private String businessCode;
	// 角色名称
	private String roleName;	
	// 菜单模板ID
	private long templateId;
	// 角色类型
	private String roleType;
	// 拦截Url
	private String iterceptUrl;

	// 所属模块
	private Set<ModuleModel> cdModule = new HashSet<ModuleModel>();

	/**
	 * 设置角色编码
	 * 
	 * @param BUSINESS_CODE
	 *            - 角色编码
	 */
	public void setBusinessCode(String value) {
		this.businessCode = value;
	}

	/**
	 * 获取角色编码
	 * 
	 * @return BUSINESS_CODE - 角色编码
	 */
	public String getBusinessCode() {
		return this.businessCode;
	}

	/**
	 * 设置角色名称
	 * 
	 * @param ROLE_NAME
	 *            - 角色名称
	 */
	public void setRoleName(String value) {
		this.roleName = value;
	}

	/**
	 * 获取角色名称
	 * 
	 * @return ROLE_NAME - 角色名称
	 */
	public String getRoleName() {
		return this.roleName;
	}
	
	/**
	 * 设置菜单模板ID
	 * 
	 * @param TEMPLATE_ID
	 *            - 菜单模板ID
	 */
	public void setTemplateId(long value) {
		this.templateId = value;
	}

	/**
	 * 获取菜单模板ID
	 * 
	 * @return TEMPLATE_ID - 菜单模板ID
	 */
	public long getTemplateId() {
		return this.templateId;
	}

	/**
	 * 设置角色类型
	 * 
	 * @param ROLE_TYPE
	 *            - 角色类型
	 */
	public void setRoleType(String value) {
		this.roleType = value;
	}

	/**
	 * 获取角色类型
	 * 
	 * @return ROLE_TYPE - 角色类型
	 */
	public String getRoleType() {
		return this.roleType;
	}

	public Set<ModuleModel> getCdModule() {
		return cdModule;
	}

	public void setCdModule(Set<ModuleModel> cdModule) {
		this.cdModule = cdModule;
	}

	public String getIterceptUrl() {
		return iterceptUrl;
	}

	public void setIterceptUrl(String iterceptUrl) {
		this.iterceptUrl = iterceptUrl;
	}


}
