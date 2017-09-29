package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:13
 *       表：CD_M_SETTING_MODULE
 */
public class SettingModuleModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -4555126004408700637L;

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

    // 猪场类型
    private static final String D_FarmType="farmType";

    // 系统模块
    private static final String D_SettingModule="settingModule";

    // 分组编号
    private static final String D_GroupCode="groupCode";

    // 分组名称
    private static final String D_GroupName="groupName";

    // 设置项名称
    private static final String D_SettingName="settingName";

    // 设置项代码
    private static final String D_SettingCode="settingCode";

    // 设置值 ON默认值
    private static final String D_SettingValue="settingValue";

    // 显示类型checkbox、text、number、int、lookup、radio
    private static final String D_SowType="sowType";

    // 说明
    private static final String D_Memo="memo";
	 //correlationId 
    private static final String D_CorrelationId="correlationId";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 更新人
    private static final String D_UpdateId="updateId";

    // 更新日期
    private static final String D_UpdateDate="updateDate";
	

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
     * 设置猪场类型
     * 
     * @param FARM_TYPE
     */
	public void setFarmType(String value) {
        set(D_FarmType,value);
    }
	
    /**
     * 获取猪场类型
     * 
     * @return FARM_TYPE
     */
    public String getFarmType() {
        return getString(D_FarmType);
    }

	/**
     * 设置系统模块
     * 
     * @param SETTING_MODULE
     */
	public void setSettingModule(String value) {
        set(D_SettingModule,value);
    }
	
    /**
     * 获取系统模块
     * 
     * @return SETTING_MODULE
     */
    public String getSettingModule() {
        return getString(D_SettingModule);
    }

	/**
     * 设置分组编号
     * 
     * @param GROUP_CODE
     */
	public void setGroupCode(String value) {
        set(D_GroupCode,value);
    }
	
    /**
     * 获取分组编号
     * 
     * @return GROUP_CODE
     */
    public String getGroupCode() {
        return getString(D_GroupCode);
    }

	/**
     * 设置分组名称
     * 
     * @param GROUP_NAME
     */
	public void setGroupName(String value) {
        set(D_GroupName,value);
    }
	
    /**
     * 获取分组名称
     * 
     * @return GROUP_NAME
     */
    public String getGroupName() {
        return getString(D_GroupName);
    }

	/**
     * 设置设置项名称
     * 
     * @param SETTING_NAME
     */
	public void setSettingName(String value) {
        set(D_SettingName,value);
    }
	
    /**
     * 获取设置项名称
     * 
     * @return SETTING_NAME
     */
    public String getSettingName() {
        return getString(D_SettingName);
    }

	/**
     * 设置设置项代码
     * 
     * @param SETTING_CODE
     */
	public void setSettingCode(String value) {
        set(D_SettingCode,value);
    }
	
    /**
     * 获取设置项代码
     * 
     * @return SETTING_CODE
     */
    public String getSettingCode() {
        return getString(D_SettingCode);
    }

	/**
     * 设置设置值 ON默认值
     * 
     * @param SETTING_VALUE
     */
	public void setSettingValue(String value) {
        set(D_SettingValue,value);
    }
	
    /**
     * 获取设置值 ON默认值
     * 
     * @return SETTING_VALUE
     */
    public String getSettingValue() {
        return getString(D_SettingValue);
    }

	/**
     * 设置显示类型checkbox、text、number、int、lookup、radio
     * 
     * @param SOW_TYPE
     */
	public void setSowType(String value) {
        set(D_SowType,value);
    }
	
    /**
     * 获取显示类型checkbox、text、number、int、lookup、radio
     * 
     * @return SOW_TYPE
     */
    public String getSowType() {
        return getString(D_SowType);
    }

	/**
     * 设置说明
     * 
     * @param MEMO
     */
	public void setMemo(String value) {
        set(D_Memo,value);
    }
	
    /**
     * 获取说明
     * 
     * @return MEMO
     */
    public String getMemo() {
        return getString(D_Memo);
    }

    /**
     * 设置correlationId
     * 
     * @param CORRELATION_ID
     */
	public void setCorrelationId(Long value) {
        set(D_CorrelationId,value);
    }
	
	/**
     * 获取correlationId
     * 
     * @return CORRELATION_ID
     */
    public Long getCorrelationId() {
        return getLong(D_CorrelationId);
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
     * @param CREATE_DATE
     */
	public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }

    /**
     * 设置更新人
     * 
     * @param UPDATE_ID
     */
	public void setUpdateId(Long value) {
        set(D_UpdateId,value);
    }
	
	/**
     * 获取更新人
     * 
     * @return UPDATE_ID
     */
    public Long getUpdateId() {
        return getLong(D_UpdateId);
    }

	/**
     * 设置更新日期
     * 
     * @param UPDATE_DATE
     */
	public void setUpdateDate(Date value) {
        set(D_UpdateDate,value);
    }
	
    /**
     * 获取更新日期
     * 
     * @return UPDATE_DATE
     */
    public Date getUpdateDate() {
        return getDate(D_UpdateDate);
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
	        setPropertes(D_FarmType);
	        setPropertes(D_SettingModule);
	        setPropertes(D_GroupCode);
	        setPropertes(D_GroupName);
	        setPropertes(D_SettingName);
	        setPropertes(D_SettingCode);
	        setPropertes(D_SettingValue);
	        setPropertes(D_SowType);
	        setPropertes(D_Memo);
	        setPropertes(D_CorrelationId);
	        setPropertes(D_CreateId);
	        setPropertes(D_CreateDate);
	        setPropertes(D_UpdateId);
	        setPropertes(D_UpdateDate);
	    }
	    return super.getPropertes();
	}

}




