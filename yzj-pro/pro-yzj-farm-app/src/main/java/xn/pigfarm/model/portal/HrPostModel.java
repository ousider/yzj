
package xn.pigfarm.model.portal;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-5-4
 * Time:17:49:44
 * 表：HrOpost
 */
public class HrPostModel extends BaseModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3373210146707361939L;
	//职务 
	private String duty;
	//职称 
	private String jobTitle;
	//职级 
	private String dutyLvl;
	//部门ID 
	private Integer deptId;
	//任职资格 
	private String qualification;


	/**
	* 设置职务
	* @param DUTY - 职务 
	*/
	public void setDuty(String value) {
		this.duty = value;
	}
	/**
	* 获取职务
	* @return DUTY - 职务 
	*/
	public String getDuty() {
		return this.duty;
	}
	/**
	* 设置职称
	* @param JOB_TITLE - 职称 
	*/
	public void setJobTitle(String value) {
		this.jobTitle = value;
	}
	/**
	* 获取职称
	* @return JOB_TITLE - 职称 
	*/
	public String getJobTitle() {
		return this.jobTitle;
	}
	/**
	* 设置职级
	* @param DUTY_LVL - 职级 
	*/
	public void setDutyLvl(String value) {
		this.dutyLvl = value;
	}
	/**
	* 获取职级
	* @return DUTY_LVL - 职级 
	*/
	public String getDutyLvl() {
		return this.dutyLvl;
	}
	/**
	* 设置部门ID
	* @param DEPT_ID - 部门ID 
	*/
	public void setDeptId(Integer value) {
		this.deptId = value;
	}
	/**
	* 获取部门ID
	* @return DEPT_ID - 部门ID 
	*/
	public Integer getDeptId() {
		return this.deptId;
	}
	/**
	* 设置任职资格
	* @param QUALIFICATION - 任职资格 
	*/
	public void setQualification(String value) {
		this.qualification = value;
	}
	/**
	* 获取任职资格
	* @return QUALIFICATION - 任职资格 
	*/
	public String getQualification() {
		return this.qualification;
	}
	


}




