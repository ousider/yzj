package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-5-8 15:30:55
 *       表：PP_M_SWINERY 
 */
public class SwineryModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 5138944853815909314L;

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
	 //产线ID 
    private static final String D_LineId="lineId";
	 //猪群编号 
    private static final String D_BusinessCode="businessCode";
	 //猪舍类别 
    private static final String D_HouseType="houseType";
	 //猪群名称 
    private static final String D_SwineryName="swineryName";
	 //建群方式 
    private static final String D_CreateType="createType";
	 //猪只类别 
    private static final String D_PigType="pigType";
	 //对应肉猪群 
    private static final String D_PorkSwineryId="porkSwineryId";
	 //计划头数 
    private static final String D_PlanHead="planHead";
	 //开始日期 
    private static final String D_BeginDate="beginDate";
	 //结束日期 
    private static final String D_EndDate="endDate";
	 //对应周 
    private static final String D_WeekNum="weekNum";
	 //批次日龄固定值 
    private static final String D_SwineryDayage="swineryDayage";
	 //上一操作日期 
    private static final String D_LastOperateDate="lastOperateDate";
	 //开始操作日期 
    private static final String D_StartOperateDate="startOperateDate";
	

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
    * 设置猪群编号
    * @param BUSINESS_CODE
    */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
   /**
    * 获取猪群编号
    * @return BUSINESS_CODE
    */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

   /**
    * 设置猪舍类别
    * @param HOUSE_TYPE
    */
	public void setHouseType(Long value) {
        set(D_HouseType,value);
    }
	
	/**
    * 获取猪舍类别
    * @return HOUSE_TYPE
    */
    public Long getHouseType() {
        return getLong(D_HouseType);
    }

	/**
    * 设置猪群名称
    * @param SWINERY_NAME
    */
	public void setSwineryName(String value) {
        set(D_SwineryName,value);
    }
	
   /**
    * 获取猪群名称
    * @return SWINERY_NAME
    */
    public String getSwineryName() {
        return getString(D_SwineryName);
    }

	/**
    * 设置建群方式
    * @param CREATE_TYPE
    */
	public void setCreateType(String value) {
        set(D_CreateType,value);
    }
	
   /**
    * 获取建群方式
    * @return CREATE_TYPE
    */
    public String getCreateType() {
        return getString(D_CreateType);
    }

	/**
    * 设置猪只类别
    * @param PIG_TYPE
    */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
   /**
    * 获取猪只类别
    * @return PIG_TYPE
    */
    public String getPigType() {
        return getString(D_PigType);
    }

   /**
    * 设置对应肉猪群
    * @param PORK_SWINERY_ID
    */
	public void setPorkSwineryId(Long value) {
        set(D_PorkSwineryId,value);
    }
	
	/**
    * 获取对应肉猪群
    * @return PORK_SWINERY_ID
    */
    public Long getPorkSwineryId() {
        return getLong(D_PorkSwineryId);
    }

   /**
    * 设置计划头数
    * @param PLAN_HEAD
    */
	public void setPlanHead(Long value) {
        set(D_PlanHead,value);
    }
	
	/**
    * 获取计划头数
    * @return PLAN_HEAD
    */
    public Long getPlanHead() {
        return getLong(D_PlanHead);
    }

	/**
    * 设置开始日期
    * @param BEGIN_DATE
    */
	public void setBeginDate(Date value) {
        set(D_BeginDate,value);
    }
	
   /**
    * 获取开始日期
    * @return BEGIN_DATE
    */
    public Date getBeginDate() {
        return getDate(D_BeginDate);
    }

	/**
    * 设置结束日期
    * @param END_DATE
    */
	public void setEndDate(Date value) {
        set(D_EndDate,value);
    }
	
   /**
    * 获取结束日期
    * @return END_DATE
    */
    public Date getEndDate() {
        return getDate(D_EndDate);
    }

   /**
    * 设置对应周
    * @param WEEK_NUM
    */
	public void setWeekNum(Long value) {
        set(D_WeekNum,value);
    }
	
	/**
    * 获取对应周
    * @return WEEK_NUM
    */
    public Long getWeekNum() {
        return getLong(D_WeekNum);
    }

	/**
    * 设置批次日龄固定值
    * @param SWINERY_DAYAGE
    */
	public void setSwineryDayage(Double value) {
        set(D_SwineryDayage,value);
    }
	
   /**
    * 获取批次日龄固定值
    * @return SWINERY_DAYAGE
    */
    public Double getSwineryDayage() {
        return getDouble(D_SwineryDayage);
    }

	/**
    * 设置上一操作日期
    * @param LAST_OPERATE_DATE
    */
	public void setLastOperateDate(Date value) {
        set(D_LastOperateDate,value);
    }
	
   /**
    * 获取上一操作日期
    * @return LAST_OPERATE_DATE
    */
    public Date getLastOperateDate() {
        return getDate(D_LastOperateDate);
    }

	/**
    * 设置开始操作日期
    * @param START_OPERATE_DATE
    */
	public void setStartOperateDate(Date value) {
        set(D_StartOperateDate,value);
    }
	
   /**
    * 获取开始操作日期
    * @return START_OPERATE_DATE
    */
    public Date getStartOperateDate() {
        return getDate(D_StartOperateDate);
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
        propertes.add("lineId");
        propertes.add("businessCode");
        propertes.add("houseType");
        propertes.add("swineryName");
        propertes.add("createType");
        propertes.add("pigType");
        propertes.add("porkSwineryId");
        propertes.add("planHead");
        propertes.add("beginDate");
        propertes.add("endDate");
        propertes.add("weekNum");
        propertes.add("swineryDayage");
        propertes.add("lastOperateDate");
        propertes.add("startOperateDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




