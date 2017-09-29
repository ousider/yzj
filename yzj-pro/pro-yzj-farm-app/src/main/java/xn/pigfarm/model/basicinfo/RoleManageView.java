package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseModel;

public class RoleManageView extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3581199618711808431L;
	// 角色编码
	private String businessCode;
	// 角色名称
	private String roleName;
	// 菜单模板ID
	private Long templateId;
	// 角色类型
	private String roleType;
	
	//公司Id
	private Long companyId;
	
	//公司名称
	private String companyName;
	
	//菜单模板名称
	private String templateName;
	
	//人员
	private List<EmployeeRoleView> emps;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<EmployeeRoleView> getEmps() {
		return emps;
	}

	public void setEmps(List<EmployeeRoleView> emps) {
		this.emps = emps;
	}
	
}
