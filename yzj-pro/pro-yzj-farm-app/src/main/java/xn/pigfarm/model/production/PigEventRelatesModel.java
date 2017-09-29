package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 11:51:35
 *       表：PP_L_PIG_EVENT_RELATES
 */
public class PigEventRelatesModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 2917532291701357318L;

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

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

    // 单据号
    private static final String D_BillId="billId";

    // 入场日期
    private static final String D_EventDate="eventDate";

    // 事件描述
    private static final String D_LastBillId="lastBillId";

    // 最后一次事件日期
    private static final String D_LastEventDate="lastEventDate";

    // 删除了这次记录的撤销单据ID
    private static final String D_DeletedBillId="deletedBillId";
	

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
     * 设置入场日期
     * 
     * @param EVENT_DATE
     */
	public void setEventDate(Date value) {
        set(D_EventDate,value);
    }
	
    /**
     * 获取入场日期
     * 
     * @return EVENT_DATE
     */
    public Date getEventDate() {
        return getDate(D_EventDate);
    }

    /**
     * 设置事件描述
     * 
     * @param LAST_BILL_ID
     */
	public void setLastBillId(Long value) {
        set(D_LastBillId,value);
    }
	
	/**
     * 获取事件描述
     * 
     * @return LAST_BILL_ID
     */
    public Long getLastBillId() {
        return getLong(D_LastBillId);
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
     * 设置删除了这次记录的撤销单据ID
     * 
     * @param DELETED_BILL_ID
     */
	public void setDeletedBillId(Long value) {
        set(D_DeletedBillId,value);
    }
	
	/**
     * 获取删除了这次记录的撤销单据ID
     * 
     * @return DELETED_BILL_ID
     */
    public Long getDeletedBillId() {
        return getLong(D_DeletedBillId);
    }
	
	
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_FarmId);
	        setPropertes(D_CompanyId);
	        setPropertes(D_SwineryId);
	        setPropertes(D_PigId);
	        setPropertes(D_LineId);
	        setPropertes(D_HouseId);
	        setPropertes(D_PigpenId);
	        setPropertes(D_EarBrand);
	        setPropertes(D_PigType);
	        setPropertes(D_Sex);
	        setPropertes(D_PigClass);
	        setPropertes(D_Parity);
	        setPropertes(D_BillId);
	        setPropertes(D_EventDate);
	        setPropertes(D_LastBillId);
	        setPropertes(D_LastEventDate);
	        setPropertes(D_DeletedBillId);
	    }
	    return super.getPropertes();
	}

}




