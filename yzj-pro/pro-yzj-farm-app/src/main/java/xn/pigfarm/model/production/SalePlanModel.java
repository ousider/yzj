package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-5-26 12:04:48
 *       表：PP_O_SALE_PLAN
 */
public class SalePlanModel  extends BaseDataModel implements Serializable{
    
    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 416038198209278862L;

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

    // 单据ID
    private static final String D_BillId="billId";

    // 计划日期类型（周、月、年）
    private static final String D_PlanDateType="planDateType";

    // 计划年
    private static final String D_PlanYear="planYear";

    // 计划月
    private static final String D_PlanMonth="planMonth";

    // 计划周
    private static final String D_PlanWeek="planWeek";

    // 场内计划苗猪
    private static final String D_FarmChildPig="farmChildPig";

    // 场内计划肥猪
    private static final String D_FarmFatPig="farmFatPig";

    // 预测计划苗猪
    private static final String D_PreChildPig="preChildPig";

    // 预测计划肥猪
    private static final String D_PreFatPig="preFatPig";

    // 当前存栏保育猪
    private static final String D_HandChildPig="handChildPig";

    // 当前存栏育肥猪
    private static final String D_HandFatPig="handFatPig";
	

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
     * 设置单据ID
     * 
     * @param BILL_ID
     */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
     * 获取单据ID
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

	/**
     * 设置计划日期类型（周、月、年）
     * 
     * @param PLAN_DATE_TYPE
     */
	public void setPlanDateType(String value) {
        set(D_PlanDateType,value);
    }
	
    /**
     * 获取计划日期类型（周、月、年）
     * 
     * @return PLAN_DATE_TYPE
     */
    public String getPlanDateType() {
        return getString(D_PlanDateType);
    }

    /**
     * 设置计划年
     * 
     * @param PLAN_YEAR
     */
	public void setPlanYear(Long value) {
        set(D_PlanYear,value);
    }
	
	/**
     * 获取计划年
     * 
     * @return PLAN_YEAR
     */
    public Long getPlanYear() {
        return getLong(D_PlanYear);
    }

    /**
     * 设置计划月
     * 
     * @param PLAN_MONTH
     */
	public void setPlanMonth(Long value) {
        set(D_PlanMonth,value);
    }
	
	/**
     * 获取计划月
     * 
     * @return PLAN_MONTH
     */
    public Long getPlanMonth() {
        return getLong(D_PlanMonth);
    }

    /**
     * 设置计划周
     * 
     * @param PLAN_WEEK
     */
	public void setPlanWeek(Long value) {
        set(D_PlanWeek,value);
    }
	
	/**
     * 获取计划周
     * 
     * @return PLAN_WEEK
     */
    public Long getPlanWeek() {
        return getLong(D_PlanWeek);
    }

    /**
     * 设置场内计划苗猪
     * 
     * @param FARM_CHILD_PIG
     */
	public void setFarmChildPig(Long value) {
        set(D_FarmChildPig,value);
    }
	
	/**
     * 获取场内计划苗猪
     * 
     * @return FARM_CHILD_PIG
     */
    public Long getFarmChildPig() {
        return getLong(D_FarmChildPig);
    }

    /**
     * 设置场内计划肥猪
     * 
     * @param FARM_FAT_PIG
     */
	public void setFarmFatPig(Long value) {
        set(D_FarmFatPig,value);
    }
	
	/**
     * 获取场内计划肥猪
     * 
     * @return FARM_FAT_PIG
     */
    public Long getFarmFatPig() {
        return getLong(D_FarmFatPig);
    }

    /**
     * 设置预测计划苗猪
     * 
     * @param PRE_CHILD_PIG
     */
	public void setPreChildPig(Long value) {
        set(D_PreChildPig,value);
    }
	
	/**
     * 获取预测计划苗猪
     * 
     * @return PRE_CHILD_PIG
     */
    public Long getPreChildPig() {
        return getLong(D_PreChildPig);
    }

    /**
     * 设置预测计划肥猪
     * 
     * @param PRE_FAT_PIG
     */
	public void setPreFatPig(Long value) {
        set(D_PreFatPig,value);
    }
	
	/**
     * 获取预测计划肥猪
     * 
     * @return PRE_FAT_PIG
     */
    public Long getPreFatPig() {
        return getLong(D_PreFatPig);
    }

    /**
     * 设置当前存栏保育猪
     * 
     * @param HAND_CHILD_PIG
     */
	public void setHandChildPig(Long value) {
        set(D_HandChildPig,value);
    }
	
	/**
     * 获取当前存栏保育猪
     * 
     * @return HAND_CHILD_PIG
     */
    public Long getHandChildPig() {
        return getLong(D_HandChildPig);
    }

    /**
     * 设置当前存栏育肥猪
     * 
     * @param HAND_FAT_PIG
     */
	public void setHandFatPig(Long value) {
        set(D_HandFatPig,value);
    }
	
	/**
     * 获取当前存栏育肥猪
     * 
     * @return HAND_FAT_PIG
     */
    public Long getHandFatPig() {
        return getLong(D_HandFatPig);
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
        propertes.add("billId");
        propertes.add("planDateType");
        propertes.add("planYear");
        propertes.add("planMonth");
        propertes.add("planWeek");
        propertes.add("farmChildPig");
        propertes.add("farmFatPig");
        propertes.add("preChildPig");
        propertes.add("preFatPig");
        propertes.add("handChildPig");
        propertes.add("handFatPig");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




