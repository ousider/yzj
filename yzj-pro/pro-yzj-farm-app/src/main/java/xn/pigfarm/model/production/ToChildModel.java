package xn.pigfarm.model.production;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-2-20 15:28:46
 *       表：PP_L_BILL_TO_CHILD
 */
public class ToChildModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -49948973914321506L;

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

    // 猪群ID
    private static final String D_SwineryId="swineryId";

    // 母猪猪只ID
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

    // 猪只类型
    private static final String D_PigType="pigType";

    // 转入猪舍ID
    private static final String D_InHouseId="inHouseId";

    // 转入单元ID
    private static final String D_InPigpenId="inPigpenId";

    // 转换日期
    private static final String D_ChildDate="childDate";

    // 转舍类型
    private static final String D_ChangeHouseType="changeHouseType";

    // 重量
    private static final String D_ChildWeight="childWeight";

    // 数量
    private static final String D_ChildQty="childQty";

    // 合并群
    private static final String D_MergeSwinery="mergeSwinery";
	 //inSwineryId 
    private static final String D_InSwineryId="inSwineryId";
	

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
     * 设置母猪猪只ID
     * 
     * @param PIG_ID
     */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
     * 获取母猪猪只ID
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
     * 设置猪只类型
     * 
     * @param PIG_TYPE
     */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
    /**
     * 获取猪只类型
     * 
     * @return PIG_TYPE
     */
    public String getPigType() {
        return getString(D_PigType);
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
     * 设置转入单元ID
     * 
     * @param IN_PIGPEN_ID
     */
	public void setInPigpenId(Long value) {
        set(D_InPigpenId,value);
    }
	
	/**
     * 获取转入单元ID
     * 
     * @return IN_PIGPEN_ID
     */
    public Long getInPigpenId() {
        return getLong(D_InPigpenId);
    }

	/**
     * 设置转换日期
     * 
     * @param CHILD_DATE
     */
	public void setChildDate(Timestamp value) {
        set(D_ChildDate,value);
    }
	
    /**
     * 获取转换日期
     * 
     * @return CHILD_DATE
     */
    public Timestamp getChildDate() {
        return getTimestamp(D_ChildDate);
    }

	/**
     * 设置转舍类型
     * 
     * @param CHANGE_HOUSE_TYPE
     */
	public void setChangeHouseType(String value) {
        set(D_ChangeHouseType,value);
    }
	
    /**
     * 获取转舍类型
     * 
     * @return CHANGE_HOUSE_TYPE
     */
    public String getChangeHouseType() {
        return getString(D_ChangeHouseType);
    }

	/**
     * 设置重量
     * 
     * @param CHILD_WEIGHT
     */
	public void setChildWeight(Double value) {
        set(D_ChildWeight,value);
    }
	
    /**
     * 获取重量
     * 
     * @return CHILD_WEIGHT
     */
    public Double getChildWeight() {
        return getDouble(D_ChildWeight);
    }

    /**
     * 设置数量
     * 
     * @param CHILD_QTY
     */
	public void setChildQty(Long value) {
        set(D_ChildQty,value);
    }
	
	/**
     * 获取数量
     * 
     * @return CHILD_QTY
     */
    public Long getChildQty() {
        return getLong(D_ChildQty);
    }

	/**
     * 设置合并群
     * 
     * @param MERGE_SWINERY
     */
	public void setMergeSwinery(String value) {
        set(D_MergeSwinery,value);
    }
	
    /**
     * 获取合并群
     * 
     * @return MERGE_SWINERY
     */
    public String getMergeSwinery() {
        return getString(D_MergeSwinery);
    }

    /**
     * 设置inSwineryId
     * 
     * @param IN_SWINERY_ID
     */
	public void setInSwineryId(Long value) {
        set(D_InSwineryId,value);
    }
	
	/**
     * 获取inSwineryId
     * 
     * @return IN_SWINERY_ID
     */
    public Long getInSwineryId() {
        return getLong(D_InSwineryId);
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
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("pigType");
        propertes.add("inHouseId");
        propertes.add("inPigpenId");
        propertes.add("childDate");
        propertes.add("changeHouseType");
        propertes.add("childWeight");
        propertes.add("childQty");
        propertes.add("mergeSwinery");
        propertes.add("inSwineryId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




