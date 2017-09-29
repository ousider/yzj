
package xn.pigfarm.model.portal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-5-4 Time:17:49:44 表：HrOdept
 */
public class HrDeptModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8711178348783857272L;
	// 部门编号
	private String businessCode;
	// 部门名称
	private String deptName;
	// 部门详情
	private String deptDesc;
	// 上级部门ID
	private Long supDeptId;

	//分组
	private Integer groupDept;

	private List<HrDeptModel> children = new ArrayList<HrDeptModel>();

	/**
	 * 设置部门编号
	 * 
	 * @param BUSINESS_CODE
	 *            - 部门编号
	 */
	public void setBusinessCode(String value) {
		this.businessCode = value;
	}

	/**
	 * 获取部门编号
	 * 
	 * @return BUSINESS_CODE - 部门编号
	 */
	public String getBusinessCode() {
		return this.businessCode;
	}

	/**
	 * 设置部门名称
	 * 
	 * @param DEPT_NAME
	 *            - 部门名称
	 */
	public void setDeptName(String value) {
		this.deptName = value;
	}

	/**
	 * 获取部门名称
	 * 
	 * @return DEPT_NAME - 部门名称
	 */
	public String getDeptName() {
		return this.deptName;
	}

	/**
	 * 设置部门详情
	 * 
	 * @param DEPT_DESC
	 *            - 部门详情
	 */
	public void setDeptDesc(String value) {
		this.deptDesc = value;
	}

	/**
	 * 获取部门详情
	 * 
	 * @return DEPT_DESC - 部门详情
	 */
	public String getDeptDesc() {
		return this.deptDesc;
	}

	/**
	 * 设置上级部门ID
	 * 
	 * @param SUP_DEPT_ID
	 *            - 上级部门ID
	 */
	public void setSupDeptId(Long value) {
		this.supDeptId = value;
	}

	/**
	 * 获取上级部门ID
	 * 
	 * @return SUP_DEPT_ID - 上级部门ID
	 */
	public Long getSupDeptId() {
		return this.supDeptId;
	}

	public List<HrDeptModel> getChildren() {
		return children;
	}

	public void setChildren(List<HrDeptModel> children) {
		this.children = children;
	}

	public Integer getGroupDept() {
		return groupDept;
	}

	public void setGroupDept(Integer groupDept) {
		this.groupDept = groupDept;
	}

}
