package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-15 11:48:14
 *       表：HR_L_LINK
 */
public class HrLlinkModel  extends BaseDataModel implements Serializable{
    
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

    // SRM表row_id
    private static final String D_SrmId="srmId";

    // 版本号
    private static final String D_Version="version";

    // 联系人姓名
    private static final String D_LinkmanName="linkmanName";

    // 座机号
    private static final String D_TelNumber="telNumber";

    // 手机号
    private static final String D_MobileNumber="mobileNumber";

    // 记录行号
    private static final String D_LineNumber="lineNumber";
	

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
     * 设置SRM表row_id
     * 
     * @param SRM_ID
     */
	public void setSrmId(Long value) {
        set(D_SrmId,value);
    }
	
	/**
     * 获取SRM表row_id
     * 
     * @return SRM_ID
     */
    public Long getSrmId() {
        return getLong(D_SrmId);
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
     * 设置联系人姓名
     * 
     * @param LINKMAN_NAME
     */
	public void setLinkmanName(String value) {
        set(D_LinkmanName,value);
    }
	
    /**
     * 获取联系人姓名
     * 
     * @return LINKMAN_NAME
     */
    public String getLinkmanName() {
        return getString(D_LinkmanName);
    }

	/**
     * 设置座机号
     * 
     * @param TEL_NUMBER
     */
	public void setTelNumber(String value) {
        set(D_TelNumber,value);
    }
	
    /**
     * 获取座机号
     * 
     * @return TEL_NUMBER
     */
    public String getTelNumber() {
        return getString(D_TelNumber);
    }

	/**
     * 设置手机号
     * 
     * @param MOBILE_NUMBER
     */
	public void setMobileNumber(String value) {
        set(D_MobileNumber,value);
    }
	
    /**
     * 获取手机号
     * 
     * @return MOBILE_NUMBER
     */
    public String getMobileNumber() {
        return getString(D_MobileNumber);
    }

    /**
     * 设置记录行号
     * 
     * @param LINE_NUMBER
     */
	public void setLineNumber(Long value) {
        set(D_LineNumber,value);
    }
	
	/**
     * 获取记录行号
     * 
     * @return LINE_NUMBER
     */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("srmId");
        propertes.add("version");
        propertes.add("linkmanName");
        propertes.add("telNumber");
        propertes.add("mobileNumber");
        propertes.add("lineNumber");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




