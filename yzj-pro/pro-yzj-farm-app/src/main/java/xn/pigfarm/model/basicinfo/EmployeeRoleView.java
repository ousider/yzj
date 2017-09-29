
package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-5-4
 * Time:17:49:43
 * 表：CdRemployeeRole
 */
public class EmployeeRoleView extends BaseModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3948298525053392191L;
	//员工 
	private Integer employeeId;
	//角色 
	private Integer roleId;
	/**
	* 设置员工
	* @param EMPLOYEE_ID - 员工 
	*/
	public void setEmployeeId(Integer value) {
		this.employeeId = value;
	}
	/**
	* 获取员工
	* @return EMPLOYEE_ID - 员工 
	*/
	public Integer getEmployeeId() {
		return this.employeeId;
	}
	/**
	* 设置角色
	* @param ROLE_ID - 角色 
	*/
	public void setRoleId(Integer value) {
		this.roleId = value;
	}
	/**
	* 获取角色
	* @return ROLE_ID - 角色 
	*/
	public Integer getRoleId() {
		return this.roleId;
	}
	


}




