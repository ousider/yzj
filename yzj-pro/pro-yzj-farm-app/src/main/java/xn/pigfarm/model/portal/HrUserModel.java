
package xn.pigfarm.model.portal;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-4-9 Time:14:03:07 表：HrMuser
 */
public class HrUserModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5882886255805877191L;
	// 公司编码
	private String companyCode;
	// 账号
	private String userName;
	// 昵称
	private String nickName;
	// 密码
	private String password;
	// 重置码
	private String resetKey;
	// 账号类型
	private String userType;
	// 人员ID
	private long employeeId;

	//公司详细信息
	private HrCompanyModel hrCompany;

	/**
	 * 设置公司编码
	 * 
	 * @param COMPANY_CODE
	 *            - 公司编码
	 */
	public void setCompanyCode(String value) {
		this.companyCode = value;
	}

	/**
	 * 获取公司编码
	 * 
	 * @return COMPANY_CODE - 公司编码
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * 设置账号
	 * 
	 * @param USER_NAME
	 *            - 账号
	 */
	public void setUserName(String value) {
		this.userName = value;
	}

	/**
	 * 获取账号
	 * 
	 * @return USER_NAME - 账号
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 设置昵称
	 * 
	 * @param NICK_NAME
	 *            - 昵称
	 */
	public void setNickName(String value) {
		this.nickName = value;
	}

	/**
	 * 获取昵称
	 * 
	 * @return NICK_NAME - 昵称
	 */
	public String getNickName() {
		return this.nickName;
	}

	/**
	 * 设置密码
	 * 
	 * @param PASSWORD
	 *            - 密码
	 */
	public void setPassword(String value) {
		this.password = value;
	}

	/**
	 * 获取密码
	 * 
	 * @return PASSWORD - 密码
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设置重置码
	 * 
	 * @param RESET_KEY
	 *            - 重置码
	 */
	public void setResetKey(String value) {
		this.resetKey = value;
	}

	/**
	 * 获取重置码
	 * 
	 * @return RESET_KEY - 重置码
	 */
	public String getResetKey() {
		return this.resetKey;
	}

	/**
	 * 设置账号类型
	 * 
	 * @param USER_TYPE
	 *            - 账号类型
	 */
	public void setUserType(String value) {
		this.userType = value;
	}

	/**
	 * 获取账号类型
	 * 
	 * @return USER_TYPE - 账号类型
	 */
	public String getUserType() {
		return this.userType;
	}

	/**
	 * 设置人员ID
	 * 
	 * @param EMPLOYEE_ID
	 *            - 人员ID
	 */
	public void setEmployeeId(long value) {
		this.employeeId = value;
	}

	/**
	 * 获取人员ID
	 * 
	 * @return EMPLOYEE_ID - 人员ID
	 */
	public long getEmployeeId() {
		return this.employeeId;
	}

	public HrCompanyModel getHrCompany() {
		return hrCompany;
	}

	public void setHrCompany(HrCompanyModel hrCompany) {
		this.hrCompany = hrCompany;
	}

}
