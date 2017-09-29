package xn.core.model.system;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-10-24 9:38:18
 *       表：SYS_L_IP_ADDRESSES
 */
public class IpAddressesModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 1212633283993606889L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 页面ID
    private static final String D_Ip="ip";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 国家
    private static final String D_IpCountry="ipCountry";

    // 地区
    private static final String D_IpArea="ipArea";

    // 省
    private static final String D_IpProvince="ipProvince";

    // 市区
    private static final String D_IpCity="ipCity";

    // 县
    private static final String D_IpCounty="ipCounty";

    // ISP公司
    private static final String D_IpIsp="ipIsp";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateTime="createTime";
	

	/**
     * 设置页面ID
     * 
     * @param IP
     */
	public void setIp(String value) {
        set(D_Ip,value);
    }
	
    /**
     * 获取页面ID
     * 
     * @return IP
     */
    public String getIp() {
        return getString(D_Ip);
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
     * 设置国家
     * 
     * @param IP_COUNTRY
     */
	public void setIpCountry(String value) {
        set(D_IpCountry,value);
    }
	
    /**
     * 获取国家
     * 
     * @return IP_COUNTRY
     */
    public String getIpCountry() {
        return getString(D_IpCountry);
    }

	/**
     * 设置地区
     * 
     * @param IP_AREA
     */
	public void setIpArea(String value) {
        set(D_IpArea,value);
    }
	
    /**
     * 获取地区
     * 
     * @return IP_AREA
     */
    public String getIpArea() {
        return getString(D_IpArea);
    }

	/**
     * 设置省
     * 
     * @param IP_PROVINCE
     */
	public void setIpProvince(String value) {
        set(D_IpProvince,value);
    }
	
    /**
     * 获取省
     * 
     * @return IP_PROVINCE
     */
    public String getIpProvince() {
        return getString(D_IpProvince);
    }

	/**
     * 设置市区
     * 
     * @param IP_CITY
     */
	public void setIpCity(String value) {
        set(D_IpCity,value);
    }
	
    /**
     * 获取市区
     * 
     * @return IP_CITY
     */
    public String getIpCity() {
        return getString(D_IpCity);
    }

	/**
     * 设置县
     * 
     * @param IP_COUNTY
     */
	public void setIpCounty(String value) {
        set(D_IpCounty,value);
    }
	
    /**
     * 获取县
     * 
     * @return IP_COUNTY
     */
    public String getIpCounty() {
        return getString(D_IpCounty);
    }

	/**
     * 设置ISP公司
     * 
     * @param IP_ISP
     */
	public void setIpIsp(String value) {
        set(D_IpIsp,value);
    }
	
    /**
     * 获取ISP公司
     * 
     * @return IP_ISP
     */
    public String getIpIsp() {
        return getString(D_IpIsp);
    }

    /**
     * 设置创建人
     * 
     * @param CREATE_ID
     */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
     * 获取创建人
     * 
     * @return CREATE_ID
     */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
     * 设置创建日期
     * 
     * @param CREATE_TIME
     */
	public void setCreateTime(Timestamp value) {
        set(D_CreateTime,value);
    }
	
    /**
     * 获取创建日期
     * 
     * @return CREATE_TIME
     */
    public Timestamp getCreateTime() {
        return getTimestamp(D_CreateTime);
    }
	
	
   static{
        propertes.add("ip");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("ipCountry");
        propertes.add("ipArea");
        propertes.add("ipProvince");
        propertes.add("ipCity");
        propertes.add("ipCounty");
        propertes.add("ipIsp");
        propertes.add("createId");
        propertes.add("createTime");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




