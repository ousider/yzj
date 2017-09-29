package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 11:51:38
 *       表：PP_L_BILL_WEAN
 */
public class WeanModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 7585338219698801366L;

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

    // 生产号
    private static final String D_ProNo="proNo";

    // 胎次
    private static final String D_Parity="parity";

    // 当前日龄
    private static final String D_DayAge="dayAge";

    // 上一状态
    private static final String D_LastStatus="lastStatus";

    // 断奶数量
    private static final String D_WeanNum="weanNum";

    // 母猪窝重、仔猪体重
    private static final String D_WeanWeight="weanWeight";

    // 断奶日期
    private static final String D_WeanDate="weanDate";

    // 带仔数
    private static final String D_BabyNum="babyNum";

    // 自动死亡寄养
    private static final String D_AutoDieBoard="autoDieBoard";
	

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
     * 设置生产号
     * 
     * @param PRO_NO
     */
	public void setProNo(Long value) {
        set(D_ProNo,value);
    }
	
	/**
     * 获取生产号
     * 
     * @return PRO_NO
     */
    public Long getProNo() {
        return getLong(D_ProNo);
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
     * 设置当前日龄
     * 
     * @param DAY_AGE
     */
	public void setDayAge(Long value) {
        set(D_DayAge,value);
    }
	
	/**
     * 获取当前日龄
     * 
     * @return DAY_AGE
     */
    public Long getDayAge() {
        return getLong(D_DayAge);
    }

    /**
     * 设置上一状态
     * 
     * @param LAST_STATUS
     */
	public void setLastStatus(Long value) {
        set(D_LastStatus,value);
    }
	
	/**
     * 获取上一状态
     * 
     * @return LAST_STATUS
     */
    public Long getLastStatus() {
        return getLong(D_LastStatus);
    }

    /**
     * 设置断奶数量
     * 
     * @param WEAN_NUM
     */
	public void setWeanNum(Long value) {
        set(D_WeanNum,value);
    }
	
	/**
     * 获取断奶数量
     * 
     * @return WEAN_NUM
     */
    public Long getWeanNum() {
        return getLong(D_WeanNum);
    }

	/**
     * 设置母猪窝重、仔猪体重
     * 
     * @param WEAN_WEIGHT
     */
	public void setWeanWeight(Double value) {
        set(D_WeanWeight,value);
    }
	
    /**
     * 获取母猪窝重、仔猪体重
     * 
     * @return WEAN_WEIGHT
     */
    public Double getWeanWeight() {
        return getDouble(D_WeanWeight);
    }

	/**
     * 设置断奶日期
     * 
     * @param WEAN_DATE
     */
	public void setWeanDate(Date value) {
        set(D_WeanDate,value);
    }
	
    /**
     * 获取断奶日期
     * 
     * @return WEAN_DATE
     */
    public Date getWeanDate() {
        return getDate(D_WeanDate);
    }

    /**
     * 设置带仔数
     * 
     * @param BABY_NUM
     */
	public void setBabyNum(Long value) {
        set(D_BabyNum,value);
    }
	
	/**
     * 获取带仔数
     * 
     * @return BABY_NUM
     */
    public Long getBabyNum() {
        return getLong(D_BabyNum);
    }

	/**
     * 设置自动死亡寄养
     * 
     * @param AUTO_DIE_BOARD
     */
	public void setAutoDieBoard(String value) {
        set(D_AutoDieBoard,value);
    }
	
    /**
     * 获取自动死亡寄养
     * 
     * @return AUTO_DIE_BOARD
     */
    public String getAutoDieBoard() {
        return getString(D_AutoDieBoard);
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
	        setPropertes(D_ProNo);
	        setPropertes(D_Parity);
	        setPropertes(D_DayAge);
	        setPropertes(D_LastStatus);
	        setPropertes(D_WeanNum);
	        setPropertes(D_WeanWeight);
	        setPropertes(D_WeanDate);
	        setPropertes(D_BabyNum);
	        setPropertes(D_AutoDieBoard);
	    }
	    return super.getPropertes();
	}

}




