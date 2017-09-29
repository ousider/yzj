
package xn.pigfarm.model.portal;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-5-19
 * Time:13:44:47
 * 表：HrRemployeePost
 */
public class HrEmployeePostModel extends BaseModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 540003415231232052L;
	//岗位 
	private Long postId;
	//员工 
	private Long employeeId;
	//关系类型 
	private String rtype;


	/**
	* 设置岗位
	* @param POST_ID - 岗位 
	*/
	public void setPostId(Long value) {
		this.postId = value;
	}
	/**
	* 获取岗位
	* @return POST_ID - 岗位 
	*/
	public Long getPostId() {
		return this.postId;
	}
	/**
	* 设置员工
	* @param EMPLOYEE_ID - 员工 
	*/
	public void setEmployeeId(Long value) {
		this.employeeId = value;
	}
	/**
	* 获取员工
	* @return EMPLOYEE_ID - 员工 
	*/
	public Long getEmployeeId() {
		return this.employeeId;
	}
	/**
	* 设置关系类型
	* @param R_TYPE - 关系类型 
	*/
	public void setRtype(String value) {
		this.rtype = value;
	}
	/**
	* 获取关系类型
	* @return R_TYPE - 关系类型 
	*/
	public String getRtype() {
		return this.rtype;
	}
	


}




