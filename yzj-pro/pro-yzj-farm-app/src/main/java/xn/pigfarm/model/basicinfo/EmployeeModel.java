package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:07
 *       表：HR_O_EMPLOYEE
 */
public class EmployeeModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 671582588926877209L;

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";
	 //farmId 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 工号: 员工代码
    private static final String D_EmployeeCode="employeeCode";

    // 姓名
    private static final String D_EmployeeName="employeeName";

    // 英文名
    private static final String D_EmployeeEnNm="employeeEnNm";

    // 性别
    private static final String D_Sex="sex";

    // 证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
    private static final String D_CardType="cardType";

    // 证件号码
    private static final String D_Idcard="idcard";

    // 出生日期
    private static final String D_Birthday="birthday";

    // 婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
    private static final String D_MarryCd="marryCd";

    // 政治面貌:
    private static final String D_Pcode="pcode";

    // 民族: 具体可选列表见“民族编码表”。
    private static final String D_Nationality="nationality";

    // 全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
    private static final String D_Edubg="edubg";

    // 全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
    private static final String D_Degree="degree";

    // 非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
    private static final String D_Pedubg="pedubg";

    // 非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
    private static final String D_Pdegree="pdegree";

    // 岗位职业（专业技术）
    private static final String D_Qlfs="qlfs";

    // 主要联系方式
    private static final String D_PriCntct="priCntct";

    // 入职日期
    private static final String D_EntryDate="entryDate";

    // 工作日期
    private static final String D_WorkDate="workDate";

    // 传真号
    private static final String D_Fax="fax";

    // 固定电话
    private static final String D_Tel="tel";

    // 手机号码
    private static final String D_Mobile="mobile";

    // 邮编
    private static final String D_Postcode="postcode";

    // 邮箱
    private static final String D_Email="email";
	 //QQ 
    private static final String D_Qq="qq";

    // 微信
    private static final String D_Wechat="wechat";

    // 人员类型: 1-内部员工；2-外部员工；3-其他员工 9-其他员工（管理员不可以查询出来）。
    private static final String D_EmployeeType="employeeType";

    // 企业微信用户ID
    private static final String D_QyUserId = "qyUserId";
	

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
     * 获取行号: 系统保留字段，标识一条数据记录。
     * 
     * @return ROW_ID
     */
    public Long getRowId() {
        return getLong(D_RowId);
    }

    /**
     * 设置排序号
     * 
     * @param SORT_NBR
     */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
     * 获取排序号
     * 
     * @return SORT_NBR
     */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
     * 设置表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置[0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取[0]-未删除;[1]-逻辑删除
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
     * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @param ORIGIN_FLAG
     */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
    /**
     * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @return ORIGIN_FLAG
     */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
     * 设置数据来源应用的代码
     * 
     * @param ORIGIN_APP
     */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
    /**
     * 获取数据来源应用的代码
     * 
     * @return ORIGIN_APP
     */
    public String getOriginApp() {
        return getString(D_OriginApp);
    }

	/**
     * 设置备注
     * 
     * @param NOTES
     */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
    /**
     * 获取备注
     * 
     * @return NOTES
     */
    public String getNotes() {
        return getString(D_Notes);
    }

    /**
     * 设置farmId
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取farmId
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置companyId
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取companyId
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

	/**
     * 设置工号: 员工代码
     * 
     * @param EMPLOYEE_CODE
     */
	public void setEmployeeCode(String value) {
        set(D_EmployeeCode,value);
    }
	
    /**
     * 获取工号: 员工代码
     * 
     * @return EMPLOYEE_CODE
     */
    public String getEmployeeCode() {
        return getString(D_EmployeeCode);
    }

	/**
     * 设置姓名
     * 
     * @param EMPLOYEE_NAME
     */
	public void setEmployeeName(String value) {
        set(D_EmployeeName,value);
    }
	
    /**
     * 获取姓名
     * 
     * @return EMPLOYEE_NAME
     */
    public String getEmployeeName() {
        return getString(D_EmployeeName);
    }

	/**
     * 设置英文名
     * 
     * @param EMPLOYEE_EN_NM
     */
	public void setEmployeeEnNm(String value) {
        set(D_EmployeeEnNm,value);
    }
	
    /**
     * 获取英文名
     * 
     * @return EMPLOYEE_EN_NM
     */
    public String getEmployeeEnNm() {
        return getString(D_EmployeeEnNm);
    }

	/**
     * 设置性别
     * 
     * @param SEX
     */
	public void setSex(String value) {
        set(D_Sex,value);
    }
	
    /**
     * 获取性别
     * 
     * @return SEX
     */
    public String getSex() {
        return getString(D_Sex);
    }

	/**
     * 设置证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
     * 
     * @param CARD_TYPE
     */
	public void setCardType(String value) {
        set(D_CardType,value);
    }
	
    /**
     * 获取证件类型: 01-身份证，02-军官证，03-士兵证，04-其它。
     * 
     * @return CARD_TYPE
     */
    public String getCardType() {
        return getString(D_CardType);
    }

	/**
     * 设置证件号码
     * 
     * @param IDCARD
     */
	public void setIdcard(String value) {
        set(D_Idcard,value);
    }
	
    /**
     * 获取证件号码
     * 
     * @return IDCARD
     */
    public String getIdcard() {
        return getString(D_Idcard);
    }

	/**
     * 设置出生日期
     * 
     * @param BIRTHDAY
     */
	public void setBirthday(Date value) {
        set(D_Birthday,value);
    }
	
    /**
     * 获取出生日期
     * 
     * @return BIRTHDAY
     */
    public Date getBirthday() {
        return getDate(D_Birthday);
    }

	/**
     * 设置婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
     * 
     * @param MARRY_CD
     */
	public void setMarryCd(String value) {
        set(D_MarryCd,value);
    }
	
    /**
     * 获取婚姻状况: 01-未婚，02-初婚，03-丧偶，04-离婚，05-再婚，99-其他。
     * 
     * @return MARRY_CD
     */
    public String getMarryCd() {
        return getString(D_MarryCd);
    }

	/**
     * 设置政治面貌:
     * 
     * @param PCODE
     */
	public void setPcode(String value) {
        set(D_Pcode,value);
    }
	
    /**
     * 获取政治面貌:
     * 
     * @return PCODE
     */
    public String getPcode() {
        return getString(D_Pcode);
    }

	/**
     * 设置民族: 具体可选列表见“民族编码表”。
     * 
     * @param NATIONALITY
     */
	public void setNationality(String value) {
        set(D_Nationality,value);
    }
	
    /**
     * 获取民族: 具体可选列表见“民族编码表”。
     * 
     * @return NATIONALITY
     */
    public String getNationality() {
        return getString(D_Nationality);
    }

	/**
     * 设置全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @param EDUBG
     */
	public void setEdubg(String value) {
        set(D_Edubg,value);
    }
	
    /**
     * 获取全日制最高学历: 01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @return EDUBG
     */
    public String getEdubg() {
        return getString(D_Edubg);
    }

	/**
     * 设置全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @param DEGREE
     */
	public void setDegree(String value) {
        set(D_Degree,value);
    }
	
    /**
     * 获取全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @return DEGREE
     */
    public String getDegree() {
        return getString(D_Degree);
    }

	/**
     * 设置非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @param P_EDUBG
     */
	public void setPedubg(String value) {
        set(D_Pedubg,value);
    }
	
    /**
     * 获取非全日制最高学历: Part-time highest degree，01-研究生，02-本科，03-大专，04-中专，05-技校（职高），06-高中，07，初中，08-小学，99-其它。
     * 
     * @return P_EDUBG
     */
    public String getPedubg() {
        return getString(D_Pedubg);
    }

	/**
     * 设置非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @param P_DEGREE
     */
	public void setPdegree(String value) {
        set(D_Pdegree,value);
    }
	
    /**
     * 获取非全日制最高学位: 01-双博士，02-博士，03-双硕士，04-硕士，05-双学士，06-学士，07-肄业，08-无。
     * 
     * @return P_DEGREE
     */
    public String getPdegree() {
        return getString(D_Pdegree);
    }

	/**
     * 设置岗位职业（专业技术）
     * 
     * @param QLFS
     */
	public void setQlfs(String value) {
        set(D_Qlfs,value);
    }
	
    /**
     * 获取岗位职业（专业技术）
     * 
     * @return QLFS
     */
    public String getQlfs() {
        return getString(D_Qlfs);
    }

	/**
     * 设置主要联系方式
     * 
     * @param PRI_CNTCT
     */
	public void setPriCntct(String value) {
        set(D_PriCntct,value);
    }
	
    /**
     * 获取主要联系方式
     * 
     * @return PRI_CNTCT
     */
    public String getPriCntct() {
        return getString(D_PriCntct);
    }

	/**
     * 设置入职日期
     * 
     * @param ENTRY_DATE
     */
	public void setEntryDate(Date value) {
        set(D_EntryDate,value);
    }
	
    /**
     * 获取入职日期
     * 
     * @return ENTRY_DATE
     */
    public Date getEntryDate() {
        return getDate(D_EntryDate);
    }

	/**
     * 设置工作日期
     * 
     * @param WORK_DATE
     */
	public void setWorkDate(Date value) {
        set(D_WorkDate,value);
    }
	
    /**
     * 获取工作日期
     * 
     * @return WORK_DATE
     */
    public Date getWorkDate() {
        return getDate(D_WorkDate);
    }

	/**
     * 设置传真号
     * 
     * @param FAX
     */
	public void setFax(String value) {
        set(D_Fax,value);
    }
	
    /**
     * 获取传真号
     * 
     * @return FAX
     */
    public String getFax() {
        return getString(D_Fax);
    }

	/**
     * 设置固定电话
     * 
     * @param TEL
     */
	public void setTel(String value) {
        set(D_Tel,value);
    }
	
    /**
     * 获取固定电话
     * 
     * @return TEL
     */
    public String getTel() {
        return getString(D_Tel);
    }

	/**
     * 设置手机号码
     * 
     * @param MOBILE
     */
	public void setMobile(String value) {
        set(D_Mobile,value);
    }
	
    /**
     * 获取手机号码
     * 
     * @return MOBILE
     */
    public String getMobile() {
        return getString(D_Mobile);
    }

	/**
     * 设置邮编
     * 
     * @param POSTCODE
     */
	public void setPostcode(String value) {
        set(D_Postcode,value);
    }
	
    /**
     * 获取邮编
     * 
     * @return POSTCODE
     */
    public String getPostcode() {
        return getString(D_Postcode);
    }

	/**
     * 设置邮箱
     * 
     * @param E_MAIL
     */
	public void setEmail(String value) {
        set(D_Email,value);
    }
	
    /**
     * 获取邮箱
     * 
     * @return E_MAIL
     */
    public String getEmail() {
        return getString(D_Email);
    }

	/**
     * 设置QQ
     * 
     * @param QQ
     */
	public void setQq(String value) {
        set(D_Qq,value);
    }
	
    /**
     * 获取QQ
     * 
     * @return QQ
     */
    public String getQq() {
        return getString(D_Qq);
    }

	/**
     * 设置微信
     * 
     * @param WECHAT
     */
	public void setWechat(String value) {
        set(D_Wechat,value);
    }
	
    /**
     * 获取微信
     * 
     * @return WECHAT
     */
    public String getWechat() {
        return getString(D_Wechat);
    }

	/**
     * 设置人员类型: 1-内部员工；2-外部员工；3-其他员工 9-其他员工（管理员不可以查询出来）。
     * 
     * @param EMPLOYEE_TYPE
     */
	public void setEmployeeType(String value) {
        set(D_EmployeeType,value);
    }
	
    /**
     * 获取人员类型: 1-内部员工；2-外部员工；3-其他员工 9-其他员工（管理员不可以查询出来）。
     * 
     * @return EMPLOYEE_TYPE
     */
    public String getEmployeeType() {
        return getString(D_EmployeeType);
    }

    public String getQyUserId() {
        return getString(D_QyUserId);
    }

    public void setQyUserId(String value) {
        set(D_QyUserId, value);
    }

	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_SortNbr);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_OriginFlag);
	        setPropertes(D_OriginApp);
	        setPropertes(D_Notes);
	        setPropertes(D_FarmId);
	        setPropertes(D_CompanyId);
	        setPropertes(D_EmployeeCode);
	        setPropertes(D_EmployeeName);
	        setPropertes(D_EmployeeEnNm);
	        setPropertes(D_Sex);
	        setPropertes(D_CardType);
	        setPropertes(D_Idcard);
	        setPropertes(D_Birthday);
	        setPropertes(D_MarryCd);
	        setPropertes(D_Pcode);
	        setPropertes(D_Nationality);
	        setPropertes(D_Edubg);
	        setPropertes(D_Degree);
	        setPropertes(D_Pedubg);
	        setPropertes(D_Pdegree);
	        setPropertes(D_Qlfs);
	        setPropertes(D_PriCntct);
	        setPropertes(D_EntryDate);
	        setPropertes(D_WorkDate);
	        setPropertes(D_Fax);
	        setPropertes(D_Tel);
	        setPropertes(D_Mobile);
	        setPropertes(D_Postcode);
	        setPropertes(D_Email);
	        setPropertes(D_Qq);
	        setPropertes(D_Wechat);
	        setPropertes(D_EmployeeType);
            setPropertes(D_QyUserId);
	    }
	    return super.getPropertes();
	}

}




