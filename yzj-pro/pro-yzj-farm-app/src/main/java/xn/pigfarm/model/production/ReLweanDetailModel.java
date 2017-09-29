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
 * @date :2017-5-29 16:27:20
 *       表：RE_L_WEAN_DETAIL 
 */
public class ReLweanDetailModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = -2850507603193595645L;

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
	 //备注 
    private static final String D_Notes="notes";
	 //报表ID 
    private static final String D_ReportId="reportId";
	 //猪场ID 
    private static final String D_FarmId="farmId";
	 //断奶总窝数 
    private static final String D_WeanTotalSize="weanTotalSize";
	 //产活仔数 
    private static final String D_LiveNum="liveNum";
	 //初生均重 
    private static final String D_BirthAvgWeight="birthAvgWeight";
	 //断奶平均日龄 
    private static final String D_WeanAvgDayage="weanAvgDayage";
	 //断奶头数 
    private static final String D_WeanPigNum="weanPigNum";
	 //窝均产活仔数 
    private static final String D_AvgDeliveryNum="avgDeliveryNum";
	 //窝均断奶数 
    private static final String D_AvgWeanNum="avgWeanNum";
	 //头均断奶重 
    private static final String D_AvgWeanWeight="avgWeanWeight";
	 //断奶窝重 
    private static final String D_WeanLitterWeight="weanLitterWeight";
	 //24日龄断奶窝重 
    private static final String D_WeanDayLitterWeight="weanDayLitterWeight";
	 //头均代乳粉使用量 
    private static final String D_AvgDrfUsedNum="avgDrfUsedNum";
	 //头均教槽王使用量 
    private static final String D_AvgJcwUsedNum="avgJcwUsedNum";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //是否是总场 
    private static final String D_IsTotalfield="isTotalfield";
	

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
    * 设置报表ID
    * @param REPORT_ID
    */
	public void setReportId(Long value) {
        set(D_ReportId,value);
    }
	
	/**
    * 获取报表ID
    * @return REPORT_ID
    */
    public Long getReportId() {
        return getLong(D_ReportId);
    }

   /**
    * 设置猪场ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取猪场ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置断奶总窝数
    * @param WEAN_TOTAL_SIZE
    */
	public void setWeanTotalSize(Long value) {
        set(D_WeanTotalSize,value);
    }
	
	/**
    * 获取断奶总窝数
    * @return WEAN_TOTAL_SIZE
    */
    public Long getWeanTotalSize() {
        return getLong(D_WeanTotalSize);
    }

   /**
    * 设置产活仔数
    * @param LIVE_NUM
    */
	public void setLiveNum(Long value) {
        set(D_LiveNum,value);
    }
	
	/**
    * 获取产活仔数
    * @return LIVE_NUM
    */
    public Long getLiveNum() {
        return getLong(D_LiveNum);
    }

	/**
    * 设置初生均重
    * @param BIRTH_AVG_WEIGHT
    */
	public void setBirthAvgWeight(Double value) {
        set(D_BirthAvgWeight,value);
    }
	
   /**
    * 获取初生均重
    * @return BIRTH_AVG_WEIGHT
    */
    public Double getBirthAvgWeight() {
        return getDouble(D_BirthAvgWeight);
    }

	/**
    * 设置断奶平均日龄
    * @param WEAN_AVG_DAYAGE
    */
	public void setWeanAvgDayage(Double value) {
        set(D_WeanAvgDayage,value);
    }
	
   /**
    * 获取断奶平均日龄
    * @return WEAN_AVG_DAYAGE
    */
    public Double getWeanAvgDayage() {
        return getDouble(D_WeanAvgDayage);
    }

   /**
    * 设置断奶头数
    * @param WEAN_PIG_NUM
    */
	public void setWeanPigNum(Long value) {
        set(D_WeanPigNum,value);
    }
	
	/**
    * 获取断奶头数
    * @return WEAN_PIG_NUM
    */
    public Long getWeanPigNum() {
        return getLong(D_WeanPigNum);
    }

	/**
    * 设置窝均产活仔数
    * @param AVG_DELIVERY_NUM
    */
	public void setAvgDeliveryNum(Double value) {
        set(D_AvgDeliveryNum,value);
    }
	
   /**
    * 获取窝均产活仔数
    * @return AVG_DELIVERY_NUM
    */
    public Double getAvgDeliveryNum() {
        return getDouble(D_AvgDeliveryNum);
    }

	/**
    * 设置窝均断奶数
    * @param AVG_WEAN_NUM
    */
	public void setAvgWeanNum(Double value) {
        set(D_AvgWeanNum,value);
    }
	
   /**
    * 获取窝均断奶数
    * @return AVG_WEAN_NUM
    */
    public Double getAvgWeanNum() {
        return getDouble(D_AvgWeanNum);
    }

	/**
    * 设置头均断奶重
    * @param AVG_WEAN_WEIGHT
    */
	public void setAvgWeanWeight(Double value) {
        set(D_AvgWeanWeight,value);
    }
	
   /**
    * 获取头均断奶重
    * @return AVG_WEAN_WEIGHT
    */
    public Double getAvgWeanWeight() {
        return getDouble(D_AvgWeanWeight);
    }

	/**
    * 设置断奶窝重
    * @param WEAN_LITTER_WEIGHT
    */
	public void setWeanLitterWeight(Double value) {
        set(D_WeanLitterWeight,value);
    }
	
   /**
    * 获取断奶窝重
    * @return WEAN_LITTER_WEIGHT
    */
    public Double getWeanLitterWeight() {
        return getDouble(D_WeanLitterWeight);
    }

	/**
    * 设置24日龄断奶窝重
    * @param WEAN_DAY_LITTER_WEIGHT
    */
	public void setWeanDayLitterWeight(Double value) {
        set(D_WeanDayLitterWeight,value);
    }
	
   /**
    * 获取24日龄断奶窝重
    * @return WEAN_DAY_LITTER_WEIGHT
    */
    public Double getWeanDayLitterWeight() {
        return getDouble(D_WeanDayLitterWeight);
    }

	/**
    * 设置头均代乳粉使用量
    * @param AVG_DRF_USED_NUM
    */
	public void setAvgDrfUsedNum(Double value) {
        set(D_AvgDrfUsedNum,value);
    }
	
   /**
    * 获取头均代乳粉使用量
    * @return AVG_DRF_USED_NUM
    */
    public Double getAvgDrfUsedNum() {
        return getDouble(D_AvgDrfUsedNum);
    }

	/**
    * 设置头均教槽王使用量
    * @param AVG_JCW_USED_NUM
    */
	public void setAvgJcwUsedNum(Double value) {
        set(D_AvgJcwUsedNum,value);
    }
	
   /**
    * 获取头均教槽王使用量
    * @return AVG_JCW_USED_NUM
    */
    public Double getAvgJcwUsedNum() {
        return getDouble(D_AvgJcwUsedNum);
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
    * 设置是否是总场
    * @param IS_TOTALFIELD
    */
	public void setIsTotalfield(Long value) {
        set(D_IsTotalfield,value);
    }
	
	/**
    * 获取是否是总场
    * @return IS_TOTALFIELD
    */
    public Long getIsTotalfield() {
        return getLong(D_IsTotalfield);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("reportId");
        propertes.add("farmId");
        propertes.add("weanTotalSize");
        propertes.add("liveNum");
        propertes.add("birthAvgWeight");
        propertes.add("weanAvgDayage");
        propertes.add("weanPigNum");
        propertes.add("avgDeliveryNum");
        propertes.add("avgWeanNum");
        propertes.add("avgWeanWeight");
        propertes.add("weanLitterWeight");
        propertes.add("weanDayLitterWeight");
        propertes.add("avgDrfUsedNum");
        propertes.add("avgJcwUsedNum");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("isTotalfield");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




