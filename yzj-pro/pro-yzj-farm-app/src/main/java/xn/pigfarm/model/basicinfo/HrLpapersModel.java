package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-15 11:46:07
 *       表：HR_L_PAPERS
 */
public class HrLpapersModel  extends BaseDataModel implements Serializable{
    
    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 8260931471484586232L;

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

    // COMPANY表ROW_ID
    private static final String D_CussupId="cussupId";

    // SRM表row_id
    private static final String D_SrmId="srmId";

    // 证照类型
    private static final String D_PaperType="paperType";

    // 证照编码
    private static final String D_PaperCode="paperCode";

    // 有效期类型[1]-有效期[2]-长期
    private static final String D_ExpiryType="expiryType";

    // 有效期
    private static final String D_ExpiryDate="expiryDate";

    // 版本号
    private static final String D_Version="version";

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
     * 设置COMPANY表ROW_ID
     * 
     * @param CUSSUP_ID
     */
	public void setCussupId(Long value) {
        set(D_CussupId,value);
    }
	
	/**
     * 获取COMPANY表ROW_ID
     * 
     * @return CUSSUP_ID
     */
    public Long getCussupId() {
        return getLong(D_CussupId);
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
     * 设置证照类型
     * 
     * @param PAPER_TYPE
     */
	public void setPaperType(String value) {
        set(D_PaperType,value);
    }
	
    /**
     * 获取证照类型
     * 
     * @return PAPER_TYPE
     */
    public String getPaperType() {
        return getString(D_PaperType);
    }

	/**
     * 设置证照编码
     * 
     * @param PAPER_CODE
     */
	public void setPaperCode(String value) {
        set(D_PaperCode,value);
    }
	
    /**
     * 获取证照编码
     * 
     * @return PAPER_CODE
     */
    public String getPaperCode() {
        return getString(D_PaperCode);
    }

	/**
     * 设置有效期类型[1]-有效期[2]-长期
     * 
     * @param EXPIRY_TYPE
     */
	public void setExpiryType(String value) {
        set(D_ExpiryType,value);
    }
	
    /**
     * 获取有效期类型[1]-有效期[2]-长期
     * 
     * @return EXPIRY_TYPE
     */
    public String getExpiryType() {
        return getString(D_ExpiryType);
    }

	/**
     * 设置有效期
     * 
     * @param EXPIRY_DATE
     */
	public void setExpiryDate(Date value) {
        set(D_ExpiryDate,value);
    }
	
    /**
     * 获取有效期
     * 
     * @return EXPIRY_DATE
     */
    public Date getExpiryDate() {
        return getDate(D_ExpiryDate);
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
        propertes.add("cussupId");
        propertes.add("srmId");
        propertes.add("paperType");
        propertes.add("paperCode");
        propertes.add("expiryType");
        propertes.add("expiryDate");
        propertes.add("version");
        propertes.add("lineNumber");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




