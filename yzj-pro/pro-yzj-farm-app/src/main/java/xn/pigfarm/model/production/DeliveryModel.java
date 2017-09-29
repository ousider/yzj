package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-21 15:32:17
 *       表：PP_L_BILL_DELIVERY 
 */
public class DeliveryModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 8425855881972024584L;

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
	 //负责人 
    private static final String D_Worker="worker";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //生产号 
    private static final String D_ProNo="proNo";
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
	 //胎次 
    private static final String D_Parity="parity";
	 //当前日龄 
    private static final String D_DayAge="dayAge";
	 //上一状态 
    private static final String D_LastStatus="lastStatus";
	 //产程 
    private static final String D_Labor="labor";
	 //活仔窝重 
    private static final String D_AliveLitterWeight="aliveLitterWeight";
	 //健仔数 
    private static final String D_HealthyNum="healthyNum";
	 //弱仔数 
    private static final String D_WeakNum="weakNum";
	 //死胎 
    private static final String D_StillbirthNum="stillbirthNum";
	 //畸形 
    private static final String D_MutantNum="mutantNum";
	 //木乃伊 
    private static final String D_MummyNum="mummyNum";
	 //黑胎 
    private static final String D_BlackNum="blackNum";
	 //活仔公 
    private static final String D_AliveLitterY="aliveLitterY";
	 //活仔母 
    private static final String D_AliveLitterX="aliveLitterX";
	 //出生窝号 
    private static final String D_HouseNum="houseNum";
	 //分娩状况 
    private static final String D_DeliveryType="deliveryType";
	 //分娩日期 
    private static final String D_DeliveryDate="deliveryDate";
	 //分娩出的仔猪批次 
    private static final String D_PorkSwineryId="porkSwineryId";
	 //绩效计算猪舍 
    private static final String D_PerformanceHouseId="performanceHouseId";
	

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
    * 设置生产号
    * @param PRO_NO
    */
	public void setProNo(Long value) {
        set(D_ProNo,value);
    }
	
	/**
    * 获取生产号
    * @return PRO_NO
    */
    public Long getProNo() {
        return getLong(D_ProNo);
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

   /**
    * 设置当前日龄
    * @param DAY_AGE
    */
	public void setDayAge(Long value) {
        set(D_DayAge,value);
    }
	
	/**
    * 获取当前日龄
    * @return DAY_AGE
    */
    public Long getDayAge() {
        return getLong(D_DayAge);
    }

   /**
    * 设置上一状态
    * @param LAST_STATUS
    */
	public void setLastStatus(Long value) {
        set(D_LastStatus,value);
    }
	
	/**
    * 获取上一状态
    * @return LAST_STATUS
    */
    public Long getLastStatus() {
        return getLong(D_LastStatus);
    }

	/**
    * 设置产程
    * @param LABOR
    */
	public void setLabor(Double value) {
        set(D_Labor,value);
    }
	
   /**
    * 获取产程
    * @return LABOR
    */
    public Double getLabor() {
        return getDouble(D_Labor);
    }

	/**
    * 设置活仔窝重
    * @param ALIVE_LITTER_WEIGHT
    */
	public void setAliveLitterWeight(Double value) {
        set(D_AliveLitterWeight,value);
    }
	
   /**
    * 获取活仔窝重
    * @return ALIVE_LITTER_WEIGHT
    */
    public Double getAliveLitterWeight() {
        return getDouble(D_AliveLitterWeight);
    }

   /**
    * 设置健仔数
    * @param HEALTHY_NUM
    */
	public void setHealthyNum(Long value) {
        set(D_HealthyNum,value);
    }
	
	/**
    * 获取健仔数
    * @return HEALTHY_NUM
    */
    public Long getHealthyNum() {
        return getLong(D_HealthyNum);
    }

   /**
    * 设置弱仔数
    * @param WEAK_NUM
    */
	public void setWeakNum(Long value) {
        set(D_WeakNum,value);
    }
	
	/**
    * 获取弱仔数
    * @return WEAK_NUM
    */
    public Long getWeakNum() {
        return getLong(D_WeakNum);
    }

   /**
    * 设置死胎
    * @param STILLBIRTH_NUM
    */
	public void setStillbirthNum(Long value) {
        set(D_StillbirthNum,value);
    }
	
	/**
    * 获取死胎
    * @return STILLBIRTH_NUM
    */
    public Long getStillbirthNum() {
        return getLong(D_StillbirthNum);
    }

   /**
    * 设置畸形
    * @param MUTANT_NUM
    */
	public void setMutantNum(Long value) {
        set(D_MutantNum,value);
    }
	
	/**
    * 获取畸形
    * @return MUTANT_NUM
    */
    public Long getMutantNum() {
        return getLong(D_MutantNum);
    }

   /**
    * 设置木乃伊
    * @param MUMMY_NUM
    */
	public void setMummyNum(Long value) {
        set(D_MummyNum,value);
    }
	
	/**
    * 获取木乃伊
    * @return MUMMY_NUM
    */
    public Long getMummyNum() {
        return getLong(D_MummyNum);
    }

   /**
    * 设置黑胎
    * @param BLACK_NUM
    */
	public void setBlackNum(Long value) {
        set(D_BlackNum,value);
    }
	
	/**
    * 获取黑胎
    * @return BLACK_NUM
    */
    public Long getBlackNum() {
        return getLong(D_BlackNum);
    }

   /**
    * 设置活仔公
    * @param ALIVE_LITTER_Y
    */
	public void setAliveLitterY(Long value) {
        set(D_AliveLitterY,value);
    }
	
	/**
    * 获取活仔公
    * @return ALIVE_LITTER_Y
    */
    public Long getAliveLitterY() {
        return getLong(D_AliveLitterY);
    }

   /**
    * 设置活仔母
    * @param ALIVE_LITTER_X
    */
	public void setAliveLitterX(Long value) {
        set(D_AliveLitterX,value);
    }
	
	/**
    * 获取活仔母
    * @return ALIVE_LITTER_X
    */
    public Long getAliveLitterX() {
        return getLong(D_AliveLitterX);
    }

	/**
    * 设置出生窝号
    * @param HOUSE_NUM
    */
	public void setHouseNum(String value) {
        set(D_HouseNum,value);
    }
	
   /**
    * 获取出生窝号
    * @return HOUSE_NUM
    */
    public String getHouseNum() {
        return getString(D_HouseNum);
    }

	/**
    * 设置分娩状况
    * @param DELIVERY_TYPE
    */
	public void setDeliveryType(String value) {
        set(D_DeliveryType,value);
    }
	
   /**
    * 获取分娩状况
    * @return DELIVERY_TYPE
    */
    public String getDeliveryType() {
        return getString(D_DeliveryType);
    }

	/**
    * 设置分娩日期
    * @param DELIVERY_DATE
    */
    public void setDeliveryDate(Date value) {
        set(D_DeliveryDate,value);
    }
	
   /**
    * 获取分娩日期
    * @return DELIVERY_DATE
    */
    public Date getDeliveryDate() {
        return getDate(D_DeliveryDate);
    }

   /**
    * 设置分娩出的仔猪批次
    * @param PORK_SWINERY_ID
    */
	public void setPorkSwineryId(Long value) {
        set(D_PorkSwineryId,value);
    }
	
	/**
    * 获取分娩出的仔猪批次
    * @return PORK_SWINERY_ID
    */
    public Long getPorkSwineryId() {
        return getLong(D_PorkSwineryId);
    }

   /**
    * 设置绩效计算猪舍
    * @param PERFORMANCE_HOUSE_ID
    */
	public void setPerformanceHouseId(Long value) {
        set(D_PerformanceHouseId,value);
    }
	
	/**
    * 获取绩效计算猪舍
    * @return PERFORMANCE_HOUSE_ID
    */
    public Long getPerformanceHouseId() {
        return getLong(D_PerformanceHouseId);
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
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("proNo");
        propertes.add("swineryId");
        propertes.add("pigId");
        propertes.add("lineId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("billId");
        propertes.add("parity");
        propertes.add("dayAge");
        propertes.add("lastStatus");
        propertes.add("labor");
        propertes.add("aliveLitterWeight");
        propertes.add("healthyNum");
        propertes.add("weakNum");
        propertes.add("stillbirthNum");
        propertes.add("mutantNum");
        propertes.add("mummyNum");
        propertes.add("blackNum");
        propertes.add("aliveLitterY");
        propertes.add("aliveLitterX");
        propertes.add("houseNum");
        propertes.add("deliveryType");
        propertes.add("deliveryDate");
        propertes.add("porkSwineryId");
        propertes.add("performanceHouseId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




