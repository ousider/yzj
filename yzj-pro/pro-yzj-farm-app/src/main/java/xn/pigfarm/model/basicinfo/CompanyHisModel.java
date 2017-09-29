package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-5-26 11:37:46
 *       表：HR_M_COMPANY_HIS
 */
public class CompanyHisModel  extends BaseDataModel implements Serializable{
    
    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -7653004912010266606L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
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

    // 公司标示
    private static final String D_CompanyMark="companyMark";

    // 公司编码
    private static final String D_CompanyCode="companyCode";

    // 公司全称
    private static final String D_CompanyName="companyName";

    // 公司简称
    private static final String D_SortName="sortName";

    // 英文名称
    private static final String D_CompanyNameEn="companyNameEn";

    // 是否集团
    private static final String D_IsBloc="isBloc";
	 //supCompanyId 
    private static final String D_SupCompanyId="supCompanyId";

    // 公司类别
    private static final String D_CompanyClass="companyClass";

    // 公司类型
    private static final String D_CompanyType="companyType";

    // 核算类别:1-集团公司 2-企业 3-猪场 4-企业+猪场 5-子猪场 6-虚拟测试场
    private static final String D_AccountCompanyClass="accountCompanyClass";

    // 客户、供应商类型，C(客户)/S（供应商）
    private static final String D_CussupType="cussupType";

    // 企业规模
    private static final String D_CompanyScale="companyScale";

    // 了解途径
    private static final String D_TraceSource="traceSource";

    // 介绍人
    private static final String D_TracePeople="tracePeople";

    // 介绍人手机号码
    private static final String D_TpCell="tpCell";

    // 公司LOGO
    private static final String D_Logo="logo";

    // 省
    private static final String D_Province="province";

    // 市
    private static final String D_City="city";

    // 县
    private static final String D_County="county";

    // 公司地址
    private static final String D_CompanyAddress="companyAddress";

    // 身份证号
    private static final String D_IdCard="idCard";

    // 公司照片附件
    private static final String D_CompanyPhoto="companyPhoto";

    // 经度
    private static final String D_Longitude="longitude";

    // 纬度
    private static final String D_Latitude="latitude";

    // 面积
    private static final String D_Area="area";

    // 注册时间
    private static final String D_RegisterTime="registerTime";

    // 注册IP
    private static final String D_RegisterIp="registerIp";

    // 开通时间
    private static final String D_OpenTime="openTime";

    // 联系电话
    private static final String D_Phone="phone";

    // 国家
    private static final String D_Country="country";

    // 邮编
    private static final String D_Postcode="postcode";

    // 版本号
    private static final String D_Version="version";

    // 变更内容
    private static final String D_ChangeContent="changeContent";

    // 变更时间
    private static final String D_ChangeDate="changeDate";

    // 变更人
    private static final String D_ChangeWorker="changeWorker";

    // 供应商、客户ID
    private static final String D_CussupId="cussupId";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	

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
     * 设置公司标示
     * 
     * @param COMPANY_MARK
     */
	public void setCompanyMark(String value) {
        set(D_CompanyMark,value);
    }
	
    /**
     * 获取公司标示
     * 
     * @return COMPANY_MARK
     */
    public String getCompanyMark() {
        return getString(D_CompanyMark);
    }

	/**
     * 设置公司编码
     * 
     * @param COMPANY_CODE
     */
	public void setCompanyCode(String value) {
        set(D_CompanyCode,value);
    }
	
    /**
     * 获取公司编码
     * 
     * @return COMPANY_CODE
     */
    public String getCompanyCode() {
        return getString(D_CompanyCode);
    }

	/**
     * 设置公司全称
     * 
     * @param COMPANY_NAME
     */
	public void setCompanyName(String value) {
        set(D_CompanyName,value);
    }
	
    /**
     * 获取公司全称
     * 
     * @return COMPANY_NAME
     */
    public String getCompanyName() {
        return getString(D_CompanyName);
    }

	/**
     * 设置公司简称
     * 
     * @param SORT_NAME
     */
	public void setSortName(String value) {
        set(D_SortName,value);
    }
	
    /**
     * 获取公司简称
     * 
     * @return SORT_NAME
     */
    public String getSortName() {
        return getString(D_SortName);
    }

	/**
     * 设置英文名称
     * 
     * @param COMPANY_NAME_EN
     */
	public void setCompanyNameEn(String value) {
        set(D_CompanyNameEn,value);
    }
	
    /**
     * 获取英文名称
     * 
     * @return COMPANY_NAME_EN
     */
    public String getCompanyNameEn() {
        return getString(D_CompanyNameEn);
    }

	/**
     * 设置是否集团
     * 
     * @param IS_BLOC
     */
	public void setIsBloc(String value) {
        set(D_IsBloc,value);
    }
	
    /**
     * 获取是否集团
     * 
     * @return IS_BLOC
     */
    public String getIsBloc() {
        return getString(D_IsBloc);
    }

    /**
     * 设置supCompanyId
     * 
     * @param SUP_COMPANY_ID
     */
	public void setSupCompanyId(Long value) {
        set(D_SupCompanyId,value);
    }
	
	/**
     * 获取supCompanyId
     * 
     * @return SUP_COMPANY_ID
     */
    public Long getSupCompanyId() {
        return getLong(D_SupCompanyId);
    }

	/**
     * 设置公司类别
     * 
     * @param COMPANY_CLASS
     */
	public void setCompanyClass(String value) {
        set(D_CompanyClass,value);
    }
	
    /**
     * 获取公司类别
     * 
     * @return COMPANY_CLASS
     */
    public String getCompanyClass() {
        return getString(D_CompanyClass);
    }

	/**
     * 设置公司类型
     * 
     * @param COMPANY_TYPE
     */
	public void setCompanyType(String value) {
        set(D_CompanyType,value);
    }
	
    /**
     * 获取公司类型
     * 
     * @return COMPANY_TYPE
     */
    public String getCompanyType() {
        return getString(D_CompanyType);
    }

	/**
     * 设置核算类别:1-集团公司 2-企业 3-猪场 4-企业+猪场 5-子猪场 6-虚拟测试场
     * 
     * @param ACCOUNT_COMPANY_CLASS
     */
	public void setAccountCompanyClass(String value) {
        set(D_AccountCompanyClass,value);
    }
	
    /**
     * 获取核算类别:1-集团公司 2-企业 3-猪场 4-企业+猪场 5-子猪场 6-虚拟测试场
     * 
     * @return ACCOUNT_COMPANY_CLASS
     */
    public String getAccountCompanyClass() {
        return getString(D_AccountCompanyClass);
    }

	/**
     * 设置客户、供应商类型，C(客户)/S（供应商）
     * 
     * @param CUSSUP_TYPE
     */
	public void setCussupType(String value) {
        set(D_CussupType,value);
    }
	
    /**
     * 获取客户、供应商类型，C(客户)/S（供应商）
     * 
     * @return CUSSUP_TYPE
     */
    public String getCussupType() {
        return getString(D_CussupType);
    }

	/**
     * 设置企业规模
     * 
     * @param COMPANY_SCALE
     */
	public void setCompanyScale(String value) {
        set(D_CompanyScale,value);
    }
	
    /**
     * 获取企业规模
     * 
     * @return COMPANY_SCALE
     */
    public String getCompanyScale() {
        return getString(D_CompanyScale);
    }

	/**
     * 设置了解途径
     * 
     * @param TRACE_SOURCE
     */
	public void setTraceSource(String value) {
        set(D_TraceSource,value);
    }
	
    /**
     * 获取了解途径
     * 
     * @return TRACE_SOURCE
     */
    public String getTraceSource() {
        return getString(D_TraceSource);
    }

	/**
     * 设置介绍人
     * 
     * @param TRACE_PEOPLE
     */
	public void setTracePeople(String value) {
        set(D_TracePeople,value);
    }
	
    /**
     * 获取介绍人
     * 
     * @return TRACE_PEOPLE
     */
    public String getTracePeople() {
        return getString(D_TracePeople);
    }

	/**
     * 设置介绍人手机号码
     * 
     * @param TP_CELL
     */
	public void setTpCell(String value) {
        set(D_TpCell,value);
    }
	
    /**
     * 获取介绍人手机号码
     * 
     * @return TP_CELL
     */
    public String getTpCell() {
        return getString(D_TpCell);
    }

	/**
     * 设置公司LOGO
     * 
     * @param LOGO
     */
	public void setLogo(String value) {
        set(D_Logo,value);
    }
	
    /**
     * 获取公司LOGO
     * 
     * @return LOGO
     */
    public String getLogo() {
        return getString(D_Logo);
    }

	/**
     * 设置省
     * 
     * @param PROVINCE
     */
	public void setProvince(String value) {
        set(D_Province,value);
    }
	
    /**
     * 获取省
     * 
     * @return PROVINCE
     */
    public String getProvince() {
        return getString(D_Province);
    }

	/**
     * 设置市
     * 
     * @param CITY
     */
	public void setCity(String value) {
        set(D_City,value);
    }
	
    /**
     * 获取市
     * 
     * @return CITY
     */
    public String getCity() {
        return getString(D_City);
    }

	/**
     * 设置县
     * 
     * @param COUNTY
     */
	public void setCounty(String value) {
        set(D_County,value);
    }
	
    /**
     * 获取县
     * 
     * @return COUNTY
     */
    public String getCounty() {
        return getString(D_County);
    }

	/**
     * 设置公司地址
     * 
     * @param COMPANY_ADDRESS
     */
	public void setCompanyAddress(String value) {
        set(D_CompanyAddress,value);
    }
	
    /**
     * 获取公司地址
     * 
     * @return COMPANY_ADDRESS
     */
    public String getCompanyAddress() {
        return getString(D_CompanyAddress);
    }

	/**
     * 设置身份证号
     * 
     * @param ID_CARD
     */
	public void setIdCard(String value) {
        set(D_IdCard,value);
    }
	
    /**
     * 获取身份证号
     * 
     * @return ID_CARD
     */
    public String getIdCard() {
        return getString(D_IdCard);
    }

	/**
     * 设置公司照片附件
     * 
     * @param COMPANY_PHOTO
     */
	public void setCompanyPhoto(String value) {
        set(D_CompanyPhoto,value);
    }
	
    /**
     * 获取公司照片附件
     * 
     * @return COMPANY_PHOTO
     */
    public String getCompanyPhoto() {
        return getString(D_CompanyPhoto);
    }

	/**
     * 设置经度
     * 
     * @param LONGITUDE
     */
	public void setLongitude(Double value) {
        set(D_Longitude,value);
    }
	
    /**
     * 获取经度
     * 
     * @return LONGITUDE
     */
    public Double getLongitude() {
        return getDouble(D_Longitude);
    }

	/**
     * 设置纬度
     * 
     * @param LATITUDE
     */
	public void setLatitude(Double value) {
        set(D_Latitude,value);
    }
	
    /**
     * 获取纬度
     * 
     * @return LATITUDE
     */
    public Double getLatitude() {
        return getDouble(D_Latitude);
    }

	/**
     * 设置面积
     * 
     * @param AREA
     */
	public void setArea(Double value) {
        set(D_Area,value);
    }
	
    /**
     * 获取面积
     * 
     * @return AREA
     */
    public Double getArea() {
        return getDouble(D_Area);
    }

	/**
     * 设置注册时间
     * 
     * @param REGISTER_TIME
     */
	public void setRegisterTime(Timestamp value) {
        set(D_RegisterTime,value);
    }
	
    /**
     * 获取注册时间
     * 
     * @return REGISTER_TIME
     */
    public Timestamp getRegisterTime() {
        return getTimestamp(D_RegisterTime);
    }

	/**
     * 设置注册IP
     * 
     * @param REGISTER_IP
     */
	public void setRegisterIp(String value) {
        set(D_RegisterIp,value);
    }
	
    /**
     * 获取注册IP
     * 
     * @return REGISTER_IP
     */
    public String getRegisterIp() {
        return getString(D_RegisterIp);
    }

	/**
     * 设置开通时间
     * 
     * @param OPEN_TIME
     */
	public void setOpenTime(Timestamp value) {
        set(D_OpenTime,value);
    }
	
    /**
     * 获取开通时间
     * 
     * @return OPEN_TIME
     */
    public Timestamp getOpenTime() {
        return getTimestamp(D_OpenTime);
    }

	/**
     * 设置联系电话
     * 
     * @param PHONE
     */
	public void setPhone(String value) {
        set(D_Phone,value);
    }
	
    /**
     * 获取联系电话
     * 
     * @return PHONE
     */
    public String getPhone() {
        return getString(D_Phone);
    }

	/**
     * 设置国家
     * 
     * @param COUNTRY
     */
	public void setCountry(String value) {
        set(D_Country,value);
    }
	
    /**
     * 获取国家
     * 
     * @return COUNTRY
     */
    public String getCountry() {
        return getString(D_Country);
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
     * 设置版本号
     * 
     * @param VERSION
     */
	public void setVersion(Long value) {
        set(D_Version,value);
    }
	
	/**
     * 获取版本号
     * 
     * @return VERSION
     */
    public Long getVersion() {
        return getLong(D_Version);
    }

	/**
     * 设置变更内容
     * 
     * @param CHANGE_CONTENT
     */
	public void setChangeContent(String value) {
        set(D_ChangeContent,value);
    }
	
    /**
     * 获取变更内容
     * 
     * @return CHANGE_CONTENT
     */
    public String getChangeContent() {
        return getString(D_ChangeContent);
    }

	/**
     * 设置变更时间
     * 
     * @param CHANGE_DATE
     */
	public void setChangeDate(Date value) {
        set(D_ChangeDate,value);
    }
	
    /**
     * 获取变更时间
     * 
     * @return CHANGE_DATE
     */
    public Date getChangeDate() {
        return getDate(D_ChangeDate);
    }

    /**
     * 设置变更人
     * 
     * @param CHANGE_WORKER
     */
	public void setChangeWorker(Long value) {
        set(D_ChangeWorker,value);
    }
	
	/**
     * 获取变更人
     * 
     * @return CHANGE_WORKER
     */
    public Long getChangeWorker() {
        return getLong(D_ChangeWorker);
    }

    /**
     * 设置供应商、客户ID
     * 
     * @param CUSSUP_ID
     */
	public void setCussupId(Long value) {
        set(D_CussupId,value);
    }
	
	/**
     * 获取供应商、客户ID
     * 
     * @return CUSSUP_ID
     */
    public Long getCussupId() {
        return getLong(D_CussupId);
    }

    /**
     * 设置对应的公司ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取对应的公司ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("companyMark");
        propertes.add("companyCode");
        propertes.add("companyName");
        propertes.add("sortName");
        propertes.add("companyNameEn");
        propertes.add("isBloc");
        propertes.add("supCompanyId");
        propertes.add("companyClass");
        propertes.add("companyType");
        propertes.add("accountCompanyClass");
        propertes.add("cussupType");
        propertes.add("companyScale");
        propertes.add("traceSource");
        propertes.add("tracePeople");
        propertes.add("tpCell");
        propertes.add("logo");
        propertes.add("province");
        propertes.add("city");
        propertes.add("county");
        propertes.add("companyAddress");
        propertes.add("idCard");
        propertes.add("companyPhoto");
        propertes.add("longitude");
        propertes.add("latitude");
        propertes.add("area");
        propertes.add("registerTime");
        propertes.add("registerIp");
        propertes.add("openTime");
        propertes.add("phone");
        propertes.add("country");
        propertes.add("postcode");
        propertes.add("version");
        propertes.add("changeContent");
        propertes.add("changeDate");
        propertes.add("changeWorker");
        propertes.add("cussupId");
        propertes.add("farmId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




