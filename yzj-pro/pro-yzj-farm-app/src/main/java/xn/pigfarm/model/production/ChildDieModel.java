package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-29 10:45:33
 *       表：PP_L_BILL_CHILD_DIE 
 */
public class ChildDieModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -3674979394349789486L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。 
    private static final String D_OriginFlag="originFlag";
	 //数据来源应用的代码 
    private static final String D_OriginApp="originApp";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //记录行号 
    private static final String D_LineNumber="lineNumber";
	 //猪群ID 
    private static final String D_SwineryId="swineryId";
	 //猪只ID 
    private static final String D_PigId="pigId";
	 //产线ID 
    private static final String D_LineId="lineId";
	 //猪舍ID 
    private static final String D_HouseId="houseId";
	 //栏号ID 
    private static final String D_PigpenId="pigpenId";
	 //单据号 
    private static final String D_BillId="billId";
	 //负责人 
    private static final String D_Worker="worker";
	 //带仔数 
    private static final String D_BabyNum="babyNum";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //转出猪群 
    private static final String D_InSwineryId="inSwineryId";
	 //客户ID 
    private static final String D_CustomerId="customerId";
	 //猪只类型 
    private static final String D_PigType="pigType";
	 //猪只性别 
    private static final String D_Sex="sex";
	 //转出猪舍 
    private static final String D_InHouseId="inHouseId";
	 //离场日期 
    private static final String D_LeaveDate="leaveDate";
	 //离场类型 
    private static final String D_LeaveType="leaveType";
	 //离场重量 
    private static final String D_LeaveWeight="leaveWeight";
	 //离场数量 
    private static final String D_LeaveQty="leaveQty";
	 //离场原因 
    private static final String D_LeaveReason="leaveReason";
	 //金额 
    private static final String D_Money="money";
	 //胎次 
    private static final String D_Parity="parity";
	

   /**
    * 设置行号: 系统保留字段，标识一条数据记录。
    * @param ROW_ID
    */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
    * 获取行号: 系统保留字段，标识一条数据记录。
    * @return ROW_ID
    */
    public Long getRowId() {
        return getLong(D_RowId);
    }

   /**
    * 设置排序号
    * @param SORT_NBR
    */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
    * 获取排序号
    * @return SORT_NBR
    */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
    * 设置表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
    * 设置[0]-未删除;[1]-逻辑删除
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取[0]-未删除;[1]-逻辑删除
    * @return DELETED_FLAG
    */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
    * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @param ORIGIN_FLAG
    */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
   /**
    * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @return ORIGIN_FLAG
    */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
    * 设置数据来源应用的代码
    * @param ORIGIN_APP
    */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
   /**
    * 获取数据来源应用的代码
    * @return ORIGIN_APP
    */
    public String getOriginApp() {
        return getString(D_OriginApp);
    }

	/**
    * 设置备注
    * @param NOTES
    */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
   /**
    * 获取备注
    * @return NOTES
    */
    public String getNotes() {
        return getString(D_Notes);
    }

   /**
    * 设置对应的公司ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取对应的公司ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置companyId
    * @param COMPANY_ID
    */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
    * 获取companyId
    * @return COMPANY_ID
    */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

   /**
    * 设置记录行号
    * @param LINE_NUMBER
    */
	public void setLineNumber(Long value) {
        set(D_LineNumber,value);
    }
	
	/**
    * 获取记录行号
    * @return LINE_NUMBER
    */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

   /**
    * 设置猪群ID
    * @param SWINERY_ID
    */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
    * 获取猪群ID
    * @return SWINERY_ID
    */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

   /**
    * 设置猪只ID
    * @param PIG_ID
    */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
    * 获取猪只ID
    * @return PIG_ID
    */
    public Long getPigId() {
        return getLong(D_PigId);
    }

   /**
    * 设置产线ID
    * @param LINE_ID
    */
	public void setLineId(Long value) {
        set(D_LineId,value);
    }
	
	/**
    * 获取产线ID
    * @return LINE_ID
    */
    public Long getLineId() {
        return getLong(D_LineId);
    }

   /**
    * 设置猪舍ID
    * @param HOUSE_ID
    */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
    * 获取猪舍ID
    * @return HOUSE_ID
    */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

   /**
    * 设置栏号ID
    * @param PIGPEN_ID
    */
	public void setPigpenId(Long value) {
        set(D_PigpenId,value);
    }
	
	/**
    * 获取栏号ID
    * @return PIGPEN_ID
    */
    public Long getPigpenId() {
        return getLong(D_PigpenId);
    }

   /**
    * 设置单据号
    * @param BILL_ID
    */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
    * 获取单据号
    * @return BILL_ID
    */
    public Long getBillId() {
        return getLong(D_BillId);
    }

   /**
    * 设置负责人
    * @param WORKER
    */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
    * 获取负责人
    * @return WORKER
    */
    public Long getWorker() {
        return getLong(D_Worker);
    }

   /**
    * 设置带仔数
    * @param BABY_NUM
    */
	public void setBabyNum(Long value) {
        set(D_BabyNum,value);
    }
	
	/**
    * 获取带仔数
    * @return BABY_NUM
    */
    public Long getBabyNum() {
        return getLong(D_BabyNum);
    }

   /**
    * 设置创建人
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建人
    * @return CREATE_ID
    */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
    * 设置创建日期
    * @param CREATE_DATE
    */
	public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建日期
    * @return CREATE_DATE
    */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }

   /**
    * 设置转出猪群
    * @param IN_SWINERY_ID
    */
	public void setInSwineryId(Long value) {
        set(D_InSwineryId,value);
    }
	
	/**
    * 获取转出猪群
    * @return IN_SWINERY_ID
    */
    public Long getInSwineryId() {
        return getLong(D_InSwineryId);
    }

   /**
    * 设置客户ID
    * @param CUSTOMER_ID
    */
	public void setCustomerId(Long value) {
        set(D_CustomerId,value);
    }
	
	/**
    * 获取客户ID
    * @return CUSTOMER_ID
    */
    public Long getCustomerId() {
        return getLong(D_CustomerId);
    }

	/**
    * 设置猪只类型
    * @param PIG_TYPE
    */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
   /**
    * 获取猪只类型
    * @return PIG_TYPE
    */
    public String getPigType() {
        return getString(D_PigType);
    }

	/**
    * 设置猪只性别
    * @param SEX
    */
	public void setSex(String value) {
        set(D_Sex,value);
    }
	
   /**
    * 获取猪只性别
    * @return SEX
    */
    public String getSex() {
        return getString(D_Sex);
    }

   /**
    * 设置转出猪舍
    * @param IN_HOUSE_ID
    */
	public void setInHouseId(Long value) {
        set(D_InHouseId,value);
    }
	
	/**
    * 获取转出猪舍
    * @return IN_HOUSE_ID
    */
    public Long getInHouseId() {
        return getLong(D_InHouseId);
    }

	/**
    * 设置离场日期
    * @param LEAVE_DATE
    */
	public void setLeaveDate(Date value) {
        set(D_LeaveDate,value);
    }
	
   /**
    * 获取离场日期
    * @return LEAVE_DATE
    */
    public Date getLeaveDate() {
        return getDate(D_LeaveDate);
    }

	/**
    * 设置离场类型
    * @param LEAVE_TYPE
    */
	public void setLeaveType(String value) {
        set(D_LeaveType,value);
    }
	
   /**
    * 获取离场类型
    * @return LEAVE_TYPE
    */
    public String getLeaveType() {
        return getString(D_LeaveType);
    }

	/**
    * 设置离场重量
    * @param LEAVE_WEIGHT
    */
	public void setLeaveWeight(Double value) {
        set(D_LeaveWeight,value);
    }
	
   /**
    * 获取离场重量
    * @return LEAVE_WEIGHT
    */
    public Double getLeaveWeight() {
        return getDouble(D_LeaveWeight);
    }

   /**
    * 设置离场数量
    * @param LEAVE_QTY
    */
	public void setLeaveQty(Long value) {
        set(D_LeaveQty,value);
    }
	
	/**
    * 获取离场数量
    * @return LEAVE_QTY
    */
    public Long getLeaveQty() {
        return getLong(D_LeaveQty);
    }

	/**
    * 设置离场原因
    * @param LEAVE_REASON
    */
	public void setLeaveReason(String value) {
        set(D_LeaveReason,value);
    }
	
   /**
    * 获取离场原因
    * @return LEAVE_REASON
    */
    public String getLeaveReason() {
        return getString(D_LeaveReason);
    }

	/**
    * 设置金额
    * @param MONEY
    */
	public void setMoney(Double value) {
        set(D_Money,value);
    }
	
   /**
    * 获取金额
    * @return MONEY
    */
    public Double getMoney() {
        return getDouble(D_Money);
    }

   /**
    * 设置胎次
    * @param PARITY
    */
	public void setParity(Long value) {
        set(D_Parity,value);
    }
	
	/**
    * 获取胎次
    * @return PARITY
    */
    public Long getParity() {
        return getLong(D_Parity);
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
        propertes.add("swineryId");
        propertes.add("pigId");
        propertes.add("lineId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("billId");
        propertes.add("worker");
        propertes.add("babyNum");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("inSwineryId");
        propertes.add("customerId");
        propertes.add("pigType");
        propertes.add("sex");
        propertes.add("inHouseId");
        propertes.add("leaveDate");
        propertes.add("leaveType");
        propertes.add("leaveWeight");
        propertes.add("leaveQty");
        propertes.add("leaveReason");
        propertes.add("money");
        propertes.add("parity");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




