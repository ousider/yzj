
package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import xn.core.model.BaseModel;
import xn.core.util.time.TimeUtil;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-4-9 Time:14:03:08
 * 表：HrMcompany
 */

public class CompanyView extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 399527606377058606L;

	// 公司编码
	private String companyCode;
	// 公司全称
	private String companyName;
	// 公司简称
	private String sortName;
	// 英文名称
	private String companyNameEn;
	// 上级公司Id
	private long supCompanyId;
	// 是否集团
	private String isBloc;
	// 公司类别
	private String companyClass;
	// 公司类型
	private String companyType;
	// 企业规模
	private String companyScale;
	// 了解途径
	private String traceSource;
	// 介绍人
	private String tracePeople;
	// 介绍人手机号码
	private String tpCell;
	// 公司LOGO
	private String logo;
	// 省
	private String province;
	// 市
	private String city;
	// 县
	private String county;
	// 公司地址
	private String companyAddress;
	// 身份证号
	private String idCard;
	// 公司照片附件
	private String companyPhoto;
	// 经度
	private Long longitude;
	// 纬度
	private Long latitude;
	// 面积
	private Long area;
	// 注册时间
	private Date registerTime;
	// 注册IP
	private String registerIp;
	// 开通时间
	private Date openTime;
	//客户、供应商类型，C(客户)/S（供应商） 
	private String cussupType;

	// 部门
	private List<DeptView> hrDept = new ArrayList<DeptView>();

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
	 * 设置公司全称
	 * 
	 * @param COMPANY_NAME
	 *            - 公司全称
	 */
	public void setCompanyName(String value) {
		this.companyName = value;
	}

	/**
	 * 获取公司全称
	 * 
	 * @return COMPANY_NAME - 公司全称
	 */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * 设置公司简称
	 * 
	 * @param SORT_NAME
	 *            - 公司简称
	 */
	public void setSortName(String value) {
		this.sortName = value;
	}

	/**
	 * 获取公司简称
	 * 
	 * @return SORT_NAME - 公司简称
	 */
	public String getSortName() {
		return this.sortName;
	}

	/**
	 * 设置英文名称
	 * 
	 * @param COMPANY_NAME_EN
	 *            - 英文名称
	 */
	public void setCompanyNameEn(String value) {
		this.companyNameEn = value;
	}

	/**
	 * 获取英文名称
	 * 
	 * @return COMPANY_NAME_EN - 英文名称
	 */
	public String getCompanyNameEn() {
		return this.companyNameEn;
	}

	/**
	 * 设置上级公司Id
	 * 
	 * @param SUP_COMPANY_ID
	 *            - 上级公司Id
	 */
	public void setSupCompanyId(long value) {
		this.supCompanyId = value;
	}

	/**
	 * 获取上级公司Id
	 * 
	 * @return SUP_COMPANY_ID - 上级公司Id
	 */
	public long getSupCompanyId() {
		return this.supCompanyId;
	}

	/**
	 * 设置是否集团
	 * 
	 * @param IS_BLOC
	 *            - 是否集团
	 */
	public void setIsBloc(String value) {
		this.isBloc = value;
	}

	/**
	 * 获取是否集团
	 * 
	 * @return IS_BLOC - 是否集团
	 */
	public String getIsBloc() {
		return this.isBloc;
	}

	/**
	 * 设置公司类别
	 * 
	 * @param COMPANY_CLASS
	 *            - 公司类别
	 */
	public void setCompanyClass(String value) {
		this.companyClass = value;
	}

	/**
	 * 获取公司类别
	 * 
	 * @return COMPANY_CLASS - 公司类别
	 */
	public String getCompanyClass() {
		return this.companyClass;
	}

	/**
	 * 设置公司类型
	 * 
	 * @param COMPANY_TYPE
	 *            - 公司类型
	 */
	public void setCompanyType(String value) {
		this.companyType = value;
	}

	/**
	 * 获取公司类型
	 * 
	 * @return COMPANY_TYPE - 公司类型
	 */
	public String getCompanyType() {
		return this.companyType;
	}

	/**
	 * 设置企业规模
	 * 
	 * @param COMPANY_SCALE
	 *            - 企业规模
	 */
	public void setCompanyScale(String value) {
		this.companyScale = value;
	}

	/**
	 * 获取企业规模
	 * 
	 * @return COMPANY_SCALE - 企业规模
	 */
	public String getCompanyScale() {
		return this.companyScale;
	}

	/**
	 * 设置了解途径
	 * 
	 * @param TRACE_SOURCE
	 *            - 了解途径
	 */
	public void setTraceSource(String value) {
		this.traceSource = value;
	}

	/**
	 * 获取了解途径
	 * 
	 * @return TRACE_SOURCE - 了解途径
	 */
	public String getTraceSource() {
		return this.traceSource;
	}

	/**
	 * 设置介绍人
	 * 
	 * @param TRACE_PEOPLE
	 *            - 介绍人
	 */
	public void setTracePeople(String value) {
		this.tracePeople = value;
	}

	/**
	 * 获取介绍人
	 * 
	 * @return TRACE_PEOPLE - 介绍人
	 */
	public String getTracePeople() {
		return this.tracePeople;
	}

	/**
	 * 设置介绍人手机号码
	 * 
	 * @param TP_CELL
	 *            - 介绍人手机号码
	 */
	public void setTpCell(String value) {
		this.tpCell = value;
	}

	/**
	 * 获取介绍人手机号码
	 * 
	 * @return TP_CELL - 介绍人手机号码
	 */
	public String getTpCell() {
		return this.tpCell;
	}

	/**
	 * 设置公司LOGO
	 * 
	 * @param LOGO
	 *            - 公司LOGO
	 */
	public void setLogo(String value) {
		this.logo = value;
	}

	/**
	 * 获取公司LOGO
	 * 
	 * @return LOGO - 公司LOGO
	 */
	public String getLogo() {
		return this.logo;
	}

	/**
	 * 设置省
	 * 
	 * @param PROVINCE
	 *            - 省
	 */
	public void setProvince(String value) {
		this.province = value;
	}

	/**
	 * 获取省
	 * 
	 * @return PROVINCE - 省
	 */
	public String getProvince() {
		return this.province;
	}

	/**
	 * 设置市
	 * 
	 * @param CITY
	 *            - 市
	 */
	public void setCity(String value) {
		this.city = value;
	}

	/**
	 * 获取市
	 * 
	 * @return CITY - 市
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * 设置县
	 * 
	 * @param COUNTY
	 *            - 县
	 */
	public void setCounty(String value) {
		this.county = value;
	}

	/**
	 * 获取县
	 * 
	 * @return COUNTY - 县
	 */
	public String getCounty() {
		return this.county;
	}

	/**
	 * 设置公司地址
	 * 
	 * @param COMPANY_ADDRESS
	 *            - 公司地址
	 */
	public void setCompanyAddress(String value) {
		this.companyAddress = value;
	}

	/**
	 * 获取公司地址
	 * 
	 * @return COMPANY_ADDRESS - 公司地址
	 */
	public String getCompanyAddress() {
		return this.companyAddress;
	}

	/**
	 * 设置身份证号
	 * 
	 * @param ID_CARD
	 *            - 身份证号
	 */
	public void setIdCard(String value) {
		this.idCard = value;
	}

	/**
	 * 获取身份证号
	 * 
	 * @return ID_CARD - 身份证号
	 */
	public String getIdCard() {
		return this.idCard;
	}

	/**
	 * 设置公司照片附件
	 * 
	 * @param COMPANY_PHOTO
	 *            - 公司照片附件
	 */
	public void setCompanyPhoto(String value) {
		this.companyPhoto = value;
	}

	/**
	 * 获取公司照片附件
	 * 
	 * @return COMPANY_PHOTO - 公司照片附件
	 */
	public String getCompanyPhoto() {
		return this.companyPhoto;
	}

	/**
	 * 设置经度
	 * 
	 * @param LONGITUDE
	 *            - 经度
	 */
	public void setLongitude(Long value) {
		this.longitude = value;
	}

	/**
	 * 获取经度
	 * 
	 * @return LONGITUDE - 经度
	 */
	public Long getLongitude() {
		return this.longitude;
	}

	/**
	 * 设置纬度
	 * 
	 * @param LATITUDE
	 *            - 纬度
	 */
	public void setLatitude(Long value) {
		this.latitude = value;
	}

	/**
	 * 获取纬度
	 * 
	 * @return LATITUDE - 纬度
	 */
	public Long getLatitude() {
		return this.latitude;
	}

	/**
	 * 设置面积
	 * 
	 * @param AREA
	 *            - 面积
	 */
	public void setArea(Long value) {
		this.area = value;
	}

	/**
	 * 获取面积
	 * 
	 * @return AREA - 面积
	 */
	public Long getArea() {
		return this.area;
	}

	/**
	 * 获取注册时间
	 * 
	 * @return REGISTER_TIME - 注册时间
	 */
	public String getRegisterTimeString() {

		return TimeUtil.format(getRegisterTime(), TimeUtil.DATE_FORMAT);
	}

	/**
	 * 设置注册时间
	 * 
	 * @param REGISTER_TIME
	 *            - 注册时间
	 * @throws Exception
	 */
	public void setRegisterTimeString(String value) throws Exception {

		setRegisterTime(TimeUtil.parse(value, TimeUtil.DATE_FORMAT));
	}

	/**
	 * 设置注册时间
	 * 
	 * @param REGISTER_TIME
	 *            - 注册时间
	 */
	public void setRegisterTime(Date value) {
		this.registerTime = value;
	}

	/**
	 * 获取注册时间
	 * 
	 * @return REGISTER_TIME - 注册时间
	 */
	public Date getRegisterTime() {
		return this.registerTime;
	}

	/**
	 * 设置注册IP
	 * 
	 * @param REGISTER_IP
	 *            - 注册IP
	 */
	public void setRegisterIp(String value) {
		this.registerIp = value;
	}

	/**
	 * 获取注册IP
	 * 
	 * @return REGISTER_IP - 注册IP
	 */
	public String getRegisterIp() {
		return this.registerIp;
	}

	/**
	 * 获取开通时间
	 * 
	 * @return OPEN_TIME - 开通时间
	 */
	public String getOpenTimeString() {

		return TimeUtil.format(getOpenTime(), TimeUtil.DATE_FORMAT);
	}

	/**
	 * 设置开通时间
	 * 
	 * @param OPEN_TIME
	 *            - 开通时间
	 * @throws Exception
	 */
	public void setOpenTimeString(String value) throws Exception {

		setOpenTime(TimeUtil.parse(value, TimeUtil.DATE_FORMAT));
	}

	/**
	 * 设置开通时间
	 * 
	 * @param OPEN_TIME
	 *            - 开通时间
	 */
	public void setOpenTime(Date value) {
		this.openTime = value;
	}

	/**
	 * 获取开通时间
	 * 
	 * @return OPEN_TIME - 开通时间
	 */
	public Date getOpenTime() {
		return this.openTime;
	}

	public List<DeptView> getHrDept() {
		return hrDept;
	}

	public void setHrDept(List<DeptView> hrDept) {
		this.hrDept = hrDept;
	}

	public String getCussupType() {
		return cussupType;
	}

	public void setCussupType(String cussupType) {
		this.cussupType = cussupType;
	}
	
	

}
