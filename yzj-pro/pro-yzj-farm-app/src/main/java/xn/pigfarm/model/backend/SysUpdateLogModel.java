package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-5-26 12:01:39
 *       表：SYS_UPDATE_LOG
 */
public class SysUpdateLogModel  extends BaseDataModel implements Serializable{
    
    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -367319166228480099L;

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

    // 更新时间
    private static final String D_UpdateTime="updateTime";

    // 更新内容
    private static final String D_UpdateLog="updateLog";

    // 版本
    private static final String D_Version="version";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建时间
    private static final String D_CreateDatetime="createDatetime";

    // 更新类型
    private static final String D_UpdateType="updateType";
	

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
     * 设置更新时间
     * 
     * @param UPDATE_TIME
     */
	public void setUpdateTime(String value) {
        set(D_UpdateTime,value);
    }
	
    /**
     * 获取更新时间
     * 
     * @return UPDATE_TIME
     */
    public Timestamp getUpdateTime() {
        return getTimestamp(D_UpdateTime);
    }

	/**
     * 设置更新内容
     * 
     * @param UPDATE_LOG
     */
	public void setUpdateLog(String value) {
        set(D_UpdateLog,value);
    }
	
    /**
     * 获取更新内容
     * 
     * @return UPDATE_LOG
     */
    public String getUpdateLog() {
        return getString(D_UpdateLog);
    }

	/**
     * 设置版本
     * 
     * @param VERSION
     */
	public void setVersion(String value) {
        set(D_Version,value);
    }
	
    /**
     * 获取版本
     * 
     * @return VERSION
     */
    public String getVersion() {
        return getString(D_Version);
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
     * 设置创建时间
     * 
     * @param CREATE_DATETIME
     */
	public void setCreateDatetime(Date value) {
        set(D_CreateDatetime,value);
    }
	
    /**
     * 获取创建时间
     * 
     * @return CREATE_DATETIME
     */
    public Timestamp getCreateDatetime() {
        return getTimestamp(D_CreateDatetime);
    }

    /**
     * 设置更新类型
     * 
     * @param UPDATE_TYPE
     */
	public void setUpdateType(String value) {
        set(D_UpdateType,value);
    }
	
	/**
     * 获取更新类型
     * 
     * @return UPDATE_TYPE
     */
    public Long getUpdateType() {
        return getLong(D_UpdateType);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("updateTime");
        propertes.add("updateLog");
        propertes.add("version");
        propertes.add("createId");
        propertes.add("createDatetime");
        propertes.add("updateType");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




