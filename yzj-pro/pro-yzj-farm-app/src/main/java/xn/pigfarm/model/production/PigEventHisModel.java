package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 11:51:35
 *       表：PP_L_PIG_EVENT_HIS
 */
public class PigEventHisModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -4582812637680102209L;

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

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 记录行号
    private static final String D_LineNumber="lineNumber";

    // 猪群ID
    private static final String D_SwineryId="swineryId";

    // 猪只ID
    private static final String D_PigId="pigId";

    // 产线ID
    private static final String D_LineId="lineId";

    // 猪舍ID
    private static final String D_HouseId="houseId";

    // 栏号ID
    private static final String D_PigpenId="pigpenId";

    // 单据号
    private static final String D_BillId="billId";

    // 负责人
    private static final String D_Worker="worker";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 耳牌号
    private static final String D_EarBrand="earBrand";

    // 猪类别
    private static final String D_PigType="pigType";

    // 性别
    private static final String D_Sex="sex";

    // 猪只状态
    private static final String D_PigClass="pigClass";

    // 胎次
    private static final String D_Parity="parity";

    // 入场日期
    private static final String D_EnterDate="enterDate";

    // 最后一次事件日期
    private static final String D_LastEventDate="lastEventDate";

    // 事件名称
    private static final String D_EventName="eventName";

    // 事件描述
    private static final String D_EventNotes="eventNotes";

    // 对应数据库表
    private static final String D_TableName="tableName";

    // 对应数据库表记录ID
    private static final String D_TableRowId="tableRowId";
	

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

    /**
     * 设置猪群ID
     * 
     * @param SWINERY_ID
     */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
     * 获取猪群ID
     * 
     * @return SWINERY_ID
     */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

    /**
     * 设置猪只ID
     * 
     * @param PIG_ID
     */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
     * 获取猪只ID
     * 
     * @return PIG_ID
     */
    public Long getPigId() {
        return getLong(D_PigId);
    }

    /**
     * 设置产线ID
     * 
     * @param LINE_ID
     */
	public void setLineId(Long value) {
        set(D_LineId,value);
    }
	
	/**
     * 获取产线ID
     * 
     * @return LINE_ID
     */
    public Long getLineId() {
        return getLong(D_LineId);
    }

    /**
     * 设置猪舍ID
     * 
     * @param HOUSE_ID
     */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
     * 获取猪舍ID
     * 
     * @return HOUSE_ID
     */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

    /**
     * 设置栏号ID
     * 
     * @param PIGPEN_ID
     */
	public void setPigpenId(Long value) {
        set(D_PigpenId,value);
    }
	
	/**
     * 获取栏号ID
     * 
     * @return PIGPEN_ID
     */
    public Long getPigpenId() {
        return getLong(D_PigpenId);
    }

    /**
     * 设置单据号
     * 
     * @param BILL_ID
     */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
     * 获取单据号
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

    /**
     * 设置负责人
     * 
     * @param WORKER
     */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
     * 获取负责人
     * 
     * @return WORKER
     */
    public Long getWorker() {
        return getLong(D_Worker);
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
     * 设置耳牌号
     * 
     * @param EAR_BRAND
     */
	public void setEarBrand(String value) {
        set(D_EarBrand,value);
    }
	
    /**
     * 获取耳牌号
     * 
     * @return EAR_BRAND
     */
    public String getEarBrand() {
        return getString(D_EarBrand);
    }

	/**
     * 设置猪类别
     * 
     * @param PIG_TYPE
     */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
    /**
     * 获取猪类别
     * 
     * @return PIG_TYPE
     */
    public String getPigType() {
        return getString(D_PigType);
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
     * 设置猪只状态
     * 
     * @param PIG_CLASS
     */
	public void setPigClass(Long value) {
        set(D_PigClass,value);
    }
	
	/**
     * 获取猪只状态
     * 
     * @return PIG_CLASS
     */
    public Long getPigClass() {
        return getLong(D_PigClass);
    }

    /**
     * 设置胎次
     * 
     * @param PARITY
     */
	public void setParity(Long value) {
        set(D_Parity,value);
    }
	
	/**
     * 获取胎次
     * 
     * @return PARITY
     */
    public Long getParity() {
        return getLong(D_Parity);
    }

	/**
     * 设置入场日期
     * 
     * @param ENTER_DATE
     */
	public void setEnterDate(Date value) {
        set(D_EnterDate,value);
    }
	
    /**
     * 获取入场日期
     * 
     * @return ENTER_DATE
     */
    public Date getEnterDate() {
        return getDate(D_EnterDate);
    }

	/**
     * 设置最后一次事件日期
     * 
     * @param LAST_EVENT_DATE
     */
	public void setLastEventDate(Date value) {
        set(D_LastEventDate,value);
    }
	
    /**
     * 获取最后一次事件日期
     * 
     * @return LAST_EVENT_DATE
     */
    public Date getLastEventDate() {
        return getDate(D_LastEventDate);
    }

	/**
     * 设置事件名称
     * 
     * @param EVENT_NAME
     */
	public void setEventName(String value) {
        set(D_EventName,value);
    }
	
    /**
     * 获取事件名称
     * 
     * @return EVENT_NAME
     */
    public String getEventName() {
        return getString(D_EventName);
    }

	/**
     * 设置事件描述
     * 
     * @param EVENT_NOTES
     */
	public void setEventNotes(String value) {
        set(D_EventNotes,value);
    }
	
    /**
     * 获取事件描述
     * 
     * @return EVENT_NOTES
     */
    public String getEventNotes() {
        return getString(D_EventNotes);
    }

	/**
     * 设置对应数据库表
     * 
     * @param TABLE_NAME
     */
	public void setTableName(String value) {
        set(D_TableName,value);
    }
	
    /**
     * 获取对应数据库表
     * 
     * @return TABLE_NAME
     */
    public String getTableName() {
        return getString(D_TableName);
    }

    /**
     * 设置对应数据库表记录ID
     * 
     * @param TABLE_ROW_ID
     */
	public void setTableRowId(Long value) {
        set(D_TableRowId,value);
    }
	
	/**
     * 获取对应数据库表记录ID
     * 
     * @return TABLE_ROW_ID
     */
    public Long getTableRowId() {
        return getLong(D_TableRowId);
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
	        setPropertes(D_LineNumber);
	        setPropertes(D_SwineryId);
	        setPropertes(D_PigId);
	        setPropertes(D_LineId);
	        setPropertes(D_HouseId);
	        setPropertes(D_PigpenId);
	        setPropertes(D_BillId);
	        setPropertes(D_Worker);
	        setPropertes(D_CreateId);
	        setPropertes(D_CreateDate);
	        setPropertes(D_EarBrand);
	        setPropertes(D_PigType);
	        setPropertes(D_Sex);
	        setPropertes(D_PigClass);
	        setPropertes(D_Parity);
	        setPropertes(D_EnterDate);
	        setPropertes(D_LastEventDate);
	        setPropertes(D_EventName);
	        setPropertes(D_EventNotes);
	        setPropertes(D_TableName);
	        setPropertes(D_TableRowId);
	    }
	    return super.getPropertes();
	}

}




