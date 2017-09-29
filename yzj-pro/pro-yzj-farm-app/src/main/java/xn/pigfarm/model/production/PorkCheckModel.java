package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-6 15:57:28
 *       表：PP_L_BILL_PORK_CHECK 
 */
public class PorkCheckModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = 3734329422163692760L;

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
	 //单据号 
    private static final String D_BillId="billId";
	 //记录行号 
    private static final String D_LineNumber="lineNumber";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //公司ID 
    private static final String D_CompanyId="companyId";
	 //猪舍ID 
    private static final String D_HouseId="houseId";
	 //批次 
    private static final String D_SwineryId="swineryId";
	 //盘点头数 
    private static final String D_CheckQty="checkQty";
	 //账上头数 
    private static final String D_AccountQty="accountQty";
	 //均重 
    private static final String D_AvgWeight="avgWeight";
	 //总重 
    private static final String D_TotalWeight="totalWeight";
	 //核算单位 
    private static final String D_CheckOrgan="checkOrgan";
	 //盘点人 
    private static final String D_AccountUser="accountUser";
	 //负责人 
    private static final String D_Worker="worker";
	 //创建人 
    private static final String D_CreateId="createId";
	 //盘点日期 
    private static final String D_CheckDate="checkDate";
	 //批次日龄 
    private static final String D_SwineryAge="swineryAge";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //备注 
    private static final String D_Notes="notes";
	 //盘点猪只类型1:生产公猪，2：后备公猪，3：生产母猪，4：后备母猪，5：肉猪 
    private static final String D_CheckPigType="checkPigType";
	

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
    * 设置公司ID
    * @param COMPANY_ID
    */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
    * 获取公司ID
    * @return COMPANY_ID
    */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
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
    * 设置批次
    * @param SWINERY_ID
    */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
    * 获取批次
    * @return SWINERY_ID
    */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

   /**
    * 设置盘点头数
    * @param CHECK_QTY
    */
	public void setCheckQty(Long value) {
        set(D_CheckQty,value);
    }
	
	/**
    * 获取盘点头数
    * @return CHECK_QTY
    */
    public Long getCheckQty() {
        return getLong(D_CheckQty);
    }

   /**
    * 设置账上头数
    * @param ACCOUNT_QTY
    */
	public void setAccountQty(Long value) {
        set(D_AccountQty,value);
    }
	
	/**
    * 获取账上头数
    * @return ACCOUNT_QTY
    */
    public Long getAccountQty() {
        return getLong(D_AccountQty);
    }

	/**
    * 设置均重
    * @param AVG_WEIGHT
    */
	public void setAvgWeight(Double value) {
        set(D_AvgWeight,value);
    }
	
   /**
    * 获取均重
    * @return AVG_WEIGHT
    */
    public Double getAvgWeight() {
        return getDouble(D_AvgWeight);
    }

   /**
    * 设置总重
    * @param TOTAL_WEIGHT
    */
	public void setTotalWeight(Long value) {
        set(D_TotalWeight,value);
    }
	
	/**
    * 获取总重
    * @return TOTAL_WEIGHT
    */
    public Long getTotalWeight() {
        return getLong(D_TotalWeight);
    }

	/**
    * 设置核算单位
    * @param CHECK_ORGAN
    */
	public void setCheckOrgan(String value) {
        set(D_CheckOrgan,value);
    }
	
   /**
    * 获取核算单位
    * @return CHECK_ORGAN
    */
    public String getCheckOrgan() {
        return getString(D_CheckOrgan);
    }

   /**
    * 设置盘点人
    * @param ACCOUNT_USER
    */
	public void setAccountUser(Long value) {
        set(D_AccountUser,value);
    }
	
	/**
    * 获取盘点人
    * @return ACCOUNT_USER
    */
    public Long getAccountUser() {
        return getLong(D_AccountUser);
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
    * 设置盘点日期
    * @param CHECK_DATE
    */
	public void setCheckDate(Date value) {
        set(D_CheckDate,value);
    }
	
   /**
    * 获取盘点日期
    * @return CHECK_DATE
    */
    public Date getCheckDate() {
        return getDate(D_CheckDate);
    }

   /**
    * 设置批次日龄
    * @param SWINERY_AGE
    */
	public void setSwineryAge(Long value) {
        set(D_SwineryAge,value);
    }
	
	/**
    * 获取批次日龄
    * @return SWINERY_AGE
    */
    public Long getSwineryAge() {
        return getLong(D_SwineryAge);
    }

	/**
    * 设置单据日期
    * @param BILL_DATE
    */
	public void setBillDate(Date value) {
        set(D_BillDate,value);
    }
	
   /**
    * 获取单据日期
    * @return BILL_DATE
    */
    public Date getBillDate() {
        return getDate(D_BillDate);
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
    * 设置盘点猪只类型1:生产公猪，2：后备公猪，3：生产母猪，4：后备母猪，5：肉猪
    * @param CHECK_PIG_TYPE
    */
	public void setCheckPigType(Long value) {
        set(D_CheckPigType,value);
    }
	
	/**
    * 获取盘点猪只类型1:生产公猪，2：后备公猪，3：生产母猪，4：后备母猪，5：肉猪
    * @return CHECK_PIG_TYPE
    */
    public Long getCheckPigType() {
        return getLong(D_CheckPigType);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("billId");
        propertes.add("lineNumber");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("houseId");
        propertes.add("swineryId");
        propertes.add("checkQty");
        propertes.add("accountQty");
        propertes.add("avgWeight");
        propertes.add("totalWeight");
        propertes.add("checkOrgan");
        propertes.add("accountUser");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("checkDate");
        propertes.add("swineryAge");
        propertes.add("billDate");
        propertes.add("createDate");
        propertes.add("notes");
        propertes.add("checkPigType");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




