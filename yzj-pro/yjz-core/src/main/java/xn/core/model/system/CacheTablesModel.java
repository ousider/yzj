package xn.core.model.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 8:56:23
 *       表：SYS_L_CACHE_TABLES
 */
public class CacheTablesModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -6847939456090611207L;

    // 表名
    private static final String D_TableName="tableName";

    // 是否需要缓存 1/2 1需要缓存
    private static final String D_Status="status";
	 //deletedFlag 
    private static final String D_DeletedFlag="deletedFlag";

    // 查询条件
    private static final String D_CacheCond="cacheCond";

    // 缓存列
    private static final String D_CacheColumns="cacheColumns";

    // 表主键
    private static final String D_PrimaryColumns="primaryColumns";

    // 表的索引
    private static final String D_Indexes="indexes";

    // 是否根据猪场缓存(Y/N 默认N不需要)
    private static final String D_FarmFlag="farmFlag";

    // NULL时默认为FARM_ID
    private static final String D_FarmColumn="farmColumn";

    // 排序字段
    private static final String D_OrderColumn="orderColumn";

    // 创建时间
    private static final String D_CreateDate="createDate";
	

	/**
     * 设置表名
     * 
     * @param TABLE_NAME
     */
	public void setTableName(String value) {
        set(D_TableName,value);
    }
	
    /**
     * 获取表名
     * 
     * @return TABLE_NAME
     */
    public String getTableName() {
        return getString(D_TableName);
    }

	/**
     * 设置是否需要缓存 1/2 1需要缓存
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取是否需要缓存 1/2 1需要缓存
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置deletedFlag
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取deletedFlag
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
     * 设置查询条件
     * 
     * @param CACHE_COND
     */
	public void setCacheCond(String value) {
        set(D_CacheCond,value);
    }
	
    /**
     * 获取查询条件
     * 
     * @return CACHE_COND
     */
    public String getCacheCond() {
        return getString(D_CacheCond);
    }

	/**
     * 设置缓存列
     * 
     * @param CACHE_COLUMNS
     */
	public void setCacheColumns(String value) {
        set(D_CacheColumns,value);
    }
	
    /**
     * 获取缓存列
     * 
     * @return CACHE_COLUMNS
     */
    public String getCacheColumns() {
        return getString(D_CacheColumns);
    }

	/**
     * 设置表主键
     * 
     * @param PRIMARY_COLUMNS
     */
	public void setPrimaryColumns(String value) {
        set(D_PrimaryColumns,value);
    }
	
    /**
     * 获取表主键
     * 
     * @return PRIMARY_COLUMNS
     */
    public String getPrimaryColumns() {
        return getString(D_PrimaryColumns);
    }

	/**
     * 设置表的索引
     * 
     * @param INDEXES
     */
	public void setIndexes(String value) {
        set(D_Indexes,value);
    }
	
    /**
     * 获取表的索引
     * 
     * @return INDEXES
     */
    public String getIndexes() {
        return getString(D_Indexes);
    }

	/**
     * 设置是否根据猪场缓存(Y/N 默认N不需要)
     * 
     * @param FARM_FLAG
     */
	public void setFarmFlag(String value) {
        set(D_FarmFlag,value);
    }
	
    /**
     * 获取是否根据猪场缓存(Y/N 默认N不需要)
     * 
     * @return FARM_FLAG
     */
    public String getFarmFlag() {
        return getString(D_FarmFlag);
    }

	/**
     * 设置NULL时默认为FARM_ID
     * 
     * @param FARM_COLUMN
     */
	public void setFarmColumn(String value) {
        set(D_FarmColumn,value);
    }
	
    /**
     * 获取NULL时默认为FARM_ID
     * 
     * @return FARM_COLUMN
     */
    public String getFarmColumn() {
        return getString(D_FarmColumn);
    }

	/**
     * 设置排序字段
     * 
     * @param ORDER_COLUMN
     */
	public void setOrderColumn(String value) {
        set(D_OrderColumn,value);
    }
	
    /**
     * 获取排序字段
     * 
     * @return ORDER_COLUMN
     */
    public String getOrderColumn() {
        return getString(D_OrderColumn);
    }

	/**
     * 设置创建时间
     * 
     * @param CREATE_DATE
     */
	public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
    /**
     * 获取创建时间
     * 
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }
	
	
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_TableName);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_CacheCond);
	        setPropertes(D_CacheColumns);
	        setPropertes(D_PrimaryColumns);
	        setPropertes(D_Indexes);
	        setPropertes(D_FarmFlag);
	        setPropertes(D_FarmColumn);
	        setPropertes(D_OrderColumn);
	        setPropertes(D_CreateDate);
	    }
	    return super.getPropertes();
	}

}




