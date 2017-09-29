package xn.pigfarm.model.basicinfo;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

public class UserManageView extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4265409937471254593L;
	
    // 工号: 员工代码
	private String employeeCode;

    // 姓名
	private String employeeName;

    // 英文名
	private String employeeEnNm;

    // 性别
	private String sex;

    // 身份证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
	private String cardType;

    // 身份证件号码
	private String idcard;

    // 出生日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

    // 婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
	private String marryCd;

    // 政治面貌:
	private String pcode;

    // 民族: 具体可选列表见“民族编码表”。
	private String nationality;

    // 全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
	private String edubg;

    // 全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
	private String degree;

    // 非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
	private String pedubg;

    // 非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
	private String pdegree;

    // 岗位职业（专业技术）
	private String qlfs;

    // 主要联系方式
	private String priCntct;

    // 停用启用情况
    private String statusName;

    // 入职日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date entryDate;

    // 工作日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date workDate;

    // 传真号
	private String fax;

    // 固定电话
	private String tel;

    // 移动电话
	private String mobile;

    // 邮编
	private String postcode;

    // 邮箱
	private String email;
	//QQ 
	private String qq;

    // 微信
	private String wechat;

    // 人员类型: 1-内部员工；2-外部员工；9-其他员工。
	private String employeeType;
	
    private String employeeTypeName;

    // 部门id
	private Long deptId;

    // 岗位id
	private Long postId;

    // 账号
	private String userName;

    // 密码
	private String password;
	
    // 部门名称
	private String deptName;
	
    // 岗位名称
	private String  postName;
	
    // 公司id
	private Long companyId;

    // 用户Id
	private Long userId;
	
    // 用户跟岗位Id
	private Long epId;
	
    // 角色Id
//	private Long roleId;
    private String qyUserId;
	
    // 角色
	private List<RoleModel> roles;
	 	
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
     * 设置工号: 员工代码
     * 
     * @param EMPLOYEE_CODE - 工号: 员工代码
     */
	public void setEmployeeCode(String value) {
		this.employeeCode = value;
	}
	
    /**
     * 获取工号: 员工代码
     * 
     * @return EMPLOYEE_CODE - 工号: 员工代码
     */
	public String getEmployeeCode() {
		return this.employeeCode;
	}
	
    /**
     * 设置姓名
     * 
     * @param EMPLOYEE_NAME - 姓名
     */
	public void setEmployeeName(String value) {
		this.employeeName = value;
	}
	
    /**
     * 获取姓名
     * 
     * @return EMPLOYEE_NAME - 姓名
     */
	public String getEmployeeName() {
		return this.employeeName;
	}
	
    /**
     * 设置英文名
     * 
     * @param EMPLOYEE_EN_NM - 英文名
     */
	public void setEmployeeEnNm(String value) {
		this.employeeEnNm = value;
	}
	
    /**
     * 获取英文名
     * 
     * @return EMPLOYEE_EN_NM - 英文名
     */
	public String getEmployeeEnNm() {
		return this.employeeEnNm;
	}
	
    /**
     * 设置性别
     * 
     * @param SEX - 性别
     */
	public void setSex(String value) {
		this.sex = value;
	}
	
    /**
     * 获取性别
     * 
     * @return SEX - 性别
     */
	public String getSex() {
		return this.sex;
	}
	
    /**
     * 设置身份证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
     * 
     * @param CARD_TYPE - 身份证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
     */
	public void setCardType(String value) {
		this.cardType = value;
	}
	
    /**
     * 获取身份证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
     * 
     * @return CARD_TYPE - 身份证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
     */
	public String getCardType() {
		return this.cardType;
	}
	
    /**
     * 设置身份证件号码
     * 
     * @param IDCARD - 身份证件号码
     */
	public void setIdcard(String value) {
		this.idcard = value;
	}
	
    /**
     * 获取身份证件号码
     * 
     * @return IDCARD - 身份证件号码
     */
	public String getIdcard() {
		return this.idcard;
	}
	
	/**
     * 设置出生日期
     * 
     * @param BIRTHDAY - 出生日期
     */
	public void setBirthday(Date value) {
		this.birthday = value;
	}
	
    /**
     * 获取出生日期
     * 
     * @return BIRTHDAY - 出生日期
     */
	public Date getBirthday() {
		return this.birthday;
	}
	
    /**
     * 设置婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
     * 
     * @param MARRY_CD - 婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
     */
	public void setMarryCd(String value) {
		this.marryCd = value;
	}
	
    /**
     * 获取婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
     * 
     * @return MARRY_CD - 婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
     */
	public String getMarryCd() {
		return this.marryCd;
	}
	
    /**
     * 设置政治面貌:
     * 
     * @param PCODE - 政治面貌:
     */
	public void setPcode(String value) {
		this.pcode = value;
	}
	
    /**
     * 获取政治面貌:
     * 
     * @return PCODE - 政治面貌:
     */
	public String getPcode() {
		return this.pcode;
	}
	
    /**
     * 设置民族: 具体可选列表见“民族编码表”。
     * 
     * @param NATIONALITY - 民族: 具体可选列表见“民族编码表”。
     */
	public void setNationality(String value) {
		this.nationality = value;
	}
	
    /**
     * 获取民族: 具体可选列表见“民族编码表”。
     * 
     * @return NATIONALITY - 民族: 具体可选列表见“民族编码表”。
     */
	public String getNationality() {
		return this.nationality;
	}
	
    /**
     * 设置全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @param EDUBG - 全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     */
	public void setEdubg(String value) {
		this.edubg = value;
	}
	
    /**
     * 获取全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @return EDUBG - 全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     */
	public String getEdubg() {
		return this.edubg;
	}
	
    /**
     * 设置全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @param DEGREE - 全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     */
	public void setDegree(String value) {
		this.degree = value;
	}
	
    /**
     * 获取全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @return DEGREE - 全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     */
	public String getDegree() {
		return this.degree;
	}
	
    /**
     * 设置非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @param P_EDUBG - 非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     */
	public void setPedubg(String value) {
		this.pedubg = value;
	}
	
    /**
     * 获取非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @return P_EDUBG - 非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     */
	public String getPedubg() {
		return this.pedubg;
	}
	
    /**
     * 设置非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @param P_DEGREE - 非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     */
	public void setPdegree(String value) {
		this.pdegree = value;
	}
	
    /**
     * 获取非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @return P_DEGREE - 非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     */
	public String getPdegree() {
		return this.pdegree;
	}
	
    /**
     * 设置岗位职业（专业技术）
     * 
     * @param QLFS - 岗位职业（专业技术）
     */
	public void setQlfs(String value) {
		this.qlfs = value;
	}
	
    /**
     * 获取岗位职业（专业技术）
     * 
     * @return QLFS - 岗位职业（专业技术）
     */
	public String getQlfs() {
		return this.qlfs;
	}
	
    /**
     * 设置主要联系方式
     * 
     * @param PRI_CNTCT - 主要联系方式
     */
	public void setPriCntct(String value) {
		this.priCntct = value;
	}
	
    /**
     * 获取主要联系方式
     * 
     * @return PRI_CNTCT - 主要联系方式
     */
	public String getPriCntct() {
		return this.priCntct;
	}
	
	/**
     * 设置入职日期
     * 
     * @param ENTRY_DATE - 入职日期
     */
	public void setEntryDate(Date value) {
		this.entryDate = value;
	}
	
    /**
     * 获取入职日期
     * 
     * @return ENTRY_DATE - 入职日期
     */
	public Date getEntryDate() {
		return this.entryDate;
	}
	
	/**
     * 设置工作日期
     * 
     * @param WORK_DATE - 工作日期
     */
	public void setWorkDate(Date value) {
		this.workDate = value;
	}
	
    /**
     * 获取工作日期
     * 
     * @return WORK_DATE - 工作日期
     */
	public Date getWorkDate() {
		return this.workDate;
	}
	
    /**
     * 设置传真号
     * 
     * @param FAX - 传真号
     */
	public void setFax(String value) {
		this.fax = value;
	}
	
    /**
     * 获取传真号
     * 
     * @return FAX - 传真号
     */
	public String getFax() {
		return this.fax;
	}
	
    /**
     * 设置固定电话
     * 
     * @param TEL - 固定电话
     */
	public void setTel(String value) {
		this.tel = value;
	}
	
    /**
     * 获取固定电话
     * 
     * @return TEL - 固定电话
     */
	public String getTel() {
		return this.tel;
	}
	
    /**
     * 设置移动电话
     * 
     * @param MOBILE - 移动电话
     */
	public void setMobile(String value) {
		this.mobile = value;
	}
	
    /**
     * 获取移动电话
     * 
     * @return MOBILE - 移动电话
     */
	public String getMobile() {
		return this.mobile;
	}
	
    /**
     * 设置邮编
     * 
     * @param POSTCODE - 邮编
     */
	public void setPostcode(String value) {
		this.postcode = value;
	}
	
    /**
     * 获取邮编
     * 
     * @return POSTCODE - 邮编
     */
	public String getPostcode() {
		return this.postcode;
	}
	
    /**
     * 设置邮箱
     * 
     * @param E_MAIL - 邮箱
     */
	public void setEmail(String value) {
		this.email = value;
	}
	
    /**
     * 获取邮箱
     * 
     * @return E_MAIL - 邮箱
     */
	public String getEmail() {
		return this.email;
	}
	
    /**
     * 设置QQ
     * 
     * @param QQ - QQ
     */
	public void setQq(String value) {
		this.qq = value;
	}
	
    /**
     * 获取QQ
     * 
     * @return QQ - QQ
     */
	public String getQq() {
		return this.qq;
	}
	
    /**
     * 设置微信
     * 
     * @param WECHAT - 微信
     */
	public void setWechat(String value) {
		this.wechat = value;
	}
	
    /**
     * 获取微信
     * 
     * @return WECHAT - 微信
     */
	public String getWechat() {
		return this.wechat;
	}
	
    /**
     * 设置人员类型: 1-内部员工；2-外部员工；9-其他员工。
     * 
     * @param EMPLOYEE_TYPE - 人员类型: 1-内部员工；2-外部员工；9-其他员工。
     */
	public void setEmployeeType(String value) {
		this.employeeType = value;
	}
	
    /**
     * 获取人员类型: 1-内部员工；2-外部员工；9-其他员工。
     * 
     * @return EMPLOYEE_TYPE - 人员类型: 1-内部员工；2-外部员工；9-其他员工。
     */
	public String getEmployeeType() {
		return this.employeeType;
	}

	public Long getEpId() {
		return epId;
	}

	public void setEpId(Long epId) {
		this.epId = epId;
	}

	public List<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleModel> roles) {
		this.roles = roles;
	}

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getQyUserId() {
        return qyUserId;
    }

    public void setQyUserId(String qyUserId) {
        this.qyUserId = qyUserId;
    }

}
