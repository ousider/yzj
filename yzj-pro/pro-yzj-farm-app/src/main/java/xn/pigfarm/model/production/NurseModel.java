package xn.pigfarm.model.production;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-10-19 15:33:40
 *       表：PP_L_BILL_NURSE
 */
public class NurseModel  extends BaseDataModel implements Serializable{
    
    
    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -2136636044837526961L;

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

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 记录行号
    private static final String D_LineNumber="lineNumber";

    // 胎次
    private static final String D_Parity="parity";

    // 猪群ID
    private static final String D_SwineryId="swineryId";

    // 猪只ID
    private static final String D_PigId="pigId";

    // 断奶数
    private static final String D_WeanNum="weanNum";

    // 母猪窝重、仔猪体重
    private static final String D_WeanWeight="weanWeight";

    // 离场数量
    private static final String D_LeaveQty="leaveQty";

    // 寄养数
    private static final String D_FosterNum="fosterNum";

    // 代养母猪ID
    private static final String D_BoardSowId="boardSowId";

    // 猪舍ID
    private static final String D_HouseId="houseId";

    // 栏号ID
    private static final String D_PigpenId="pigpenId";

    // 单据号
    private static final String D_BillId="billId";

    // 带仔数
    private static final String D_BabyNum="babyNum";

    // 负责人
    private static final String D_Worker="worker";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 转入猪舍ID
    private static final String D_InHouseId="inHouseId";

    // 转入栏号ID
    private static final String D_InPigpenId="inPigpenId";

    // 转入日期
    private static final String D_ChangeHouseDate="changeHouseDate";
	 //inPigId 
    private static final String D_InPigId="inPigId";

    // 原猪只转出猪舍ID
    private static final String D_InPigHouseId="inPigHouseId";

    // 原猪只转出猪栏ID
    private static final String D_InPigPigpenId="inPigPigpenId";
	

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
     * 设置断奶数
     * 
     * @param WEAN_NUM
     */
	public void setWeanNum(Long value) {
        set(D_WeanNum,value);
    }
	
	/**
     * 获取断奶数
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
     * 设置离场数量
     * 
     * @param LEAVE_QTY
     */
	public void setLeaveQty(Long value) {
        set(D_LeaveQty,value);
    }
	
	/**
     * 获取离场数量
     * 
     * @return LEAVE_QTY
     */
    public Long getLeaveQty() {
        return getLong(D_LeaveQty);
    }

    /**
     * 设置寄养数
     * 
     * @param FOSTER_NUM
     */
	public void setFosterNum(Long value) {
        set(D_FosterNum,value);
    }
	
	/**
     * 获取寄养数
     * 
     * @return FOSTER_NUM
     */
    public Long getFosterNum() {
        return getLong(D_FosterNum);
    }

    /**
     * 设置代养母猪ID
     * 
     * @param BOARD_SOW_ID
     */
	public void setBoardSowId(Long value) {
        set(D_BoardSowId,value);
    }
	
	/**
     * 获取代养母猪ID
     * 
     * @return BOARD_SOW_ID
     */
    public Long getBoardSowId() {
        return getLong(D_BoardSowId);
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
	public void setCreateDate(Timestamp value) {
        set(D_CreateDate,value);
    }
	
    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Timestamp getCreateDate() {
        return getTimestamp(D_CreateDate);
    }

    /**
     * 设置转入猪舍ID
     * 
     * @param IN_HOUSE_ID
     */
	public void setInHouseId(Long value) {
        set(D_InHouseId,value);
    }
	
	/**
     * 获取转入猪舍ID
     * 
     * @return IN_HOUSE_ID
     */
    public Long getInHouseId() {
        return getLong(D_InHouseId);
    }

    /**
     * 设置转入栏号ID
     * 
     * @param IN_PIGPEN_ID
     */
	public void setInPigpenId(Long value) {
        set(D_InPigpenId,value);
    }
	
	/**
     * 获取转入栏号ID
     * 
     * @return IN_PIGPEN_ID
     */
    public Long getInPigpenId() {
        return getLong(D_InPigpenId);
    }

	/**
     * 设置转入日期
     * 
     * @param CHANGE_HOUSE_DATE
     */
	public void setChangeHouseDate(Timestamp value) {
        set(D_ChangeHouseDate,value);
    }
	
    /**
     * 获取转入日期
     * 
     * @return CHANGE_HOUSE_DATE
     */
    public Timestamp getChangeHouseDate() {
        return getTimestamp(D_ChangeHouseDate);
    }

    /**
     * 设置inPigId
     * 
     * @param IN_PIG_ID
     */
	public void setInPigId(Long value) {
        set(D_InPigId,value);
    }
	
	/**
     * 获取inPigId
     * 
     * @return IN_PIG_ID
     */
    public Long getInPigId() {
        return getLong(D_InPigId);
    }

    /**
     * 设置原猪只转出猪舍ID
     * 
     * @param IN_PIG_HOUSE_ID
     */
	public void setInPigHouseId(Long value) {
        set(D_InPigHouseId,value);
    }
	
	/**
     * 获取原猪只转出猪舍ID
     * 
     * @return IN_PIG_HOUSE_ID
     */
    public Long getInPigHouseId() {
        return getLong(D_InPigHouseId);
    }

    /**
     * 设置原猪只转出猪栏ID
     * 
     * @param IN_PIG_PIGPEN_ID
     */
	public void setInPigPigpenId(Long value) {
        set(D_InPigPigpenId,value);
    }
	
	/**
     * 获取原猪只转出猪栏ID
     * 
     * @return IN_PIG_PIGPEN_ID
     */
    public Long getInPigPigpenId() {
        return getLong(D_InPigPigpenId);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("lineNumber");
        propertes.add("parity");
        propertes.add("swineryId");
        propertes.add("pigId");
        propertes.add("weanNum");
        propertes.add("weanWeight");
        propertes.add("leaveQty");
        propertes.add("fosterNum");
        propertes.add("boardSowId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("billId");
        propertes.add("babyNum");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("inHouseId");
        propertes.add("inPigpenId");
        propertes.add("changeHouseDate");
        propertes.add("inPigId");
        propertes.add("inPigHouseId");
        propertes.add("inPigPigpenId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




