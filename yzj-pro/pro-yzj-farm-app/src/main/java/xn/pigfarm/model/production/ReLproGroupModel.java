package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-4-27 10:36:02
 *       表：RE_L_PRO_GROUP 
 */
public class ReLproGroupModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = -7888644201954467547L;

	//存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //备注 
    private static final String D_Notes="notes";
	 //报表ID 
    private static final String D_ReportId="reportId";
	 //猪场ID 
    private static final String D_FarmId="farmId";
	 //类型 
    private static final String D_ProType="proType";
	 //指标 
    private static final String D_Indicator="indicator";
	 //最优值 
    private static final String D_BestValue="bestValue";
	 //最优猪场ID 
    private static final String D_BestFarmId="bestFarmId";
	 //最优头数 
    private static final String D_BestPigQty="bestPigQty";
	 //最差值 
    private static final String D_WorstValue="worstValue";
	 //最差猪场ID 
    private static final String D_WorstFarmId="worstFarmId";
	 //最差头数 
    private static final String D_WorstPigQty="worstPigQty";
	 //创建ID 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	

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
    * 设置类型
    * @param PRO_TYPE
    */
	public void setProType(Long value) {
        set(D_ProType,value);
    }
	
	/**
    * 获取类型
    * @return PRO_TYPE
    */
    public Long getProType() {
        return getLong(D_ProType);
    }

   /**
    * 设置指标
    * @param INDICATOR
    */
	public void setIndicator(Long value) {
        set(D_Indicator,value);
    }
	
	/**
    * 获取指标
    * @return INDICATOR
    */
    public Long getIndicator() {
        return getLong(D_Indicator);
    }

   /**
    * 设置最优值
    * @param BEST_VALUE
    */
	public void setBestValue(Double value) {
        set(D_BestValue,value);
    }
	
	/**
    * 获取最优值
    * @return BEST_VALUE
    */
    public Double getBestValue() {
        return getDouble(D_BestValue);
    }

   /**
    * 设置最优猪场ID
    * @param BEST_FARM_ID
    */
	public void setBestFarmId(Long value) {
        set(D_BestFarmId,value);
    }
	
	/**
    * 获取最优猪场ID
    * @return BEST_FARM_ID
    */
    public Long getBestFarmId() {
        return getLong(D_BestFarmId);
    }

   /**
    * 设置最优头数
    * @param BEST_PIG_QTY
    */
	public void setBestPigQty(Long value) {
        set(D_BestPigQty,value);
    }
	
	/**
    * 获取最优头数
    * @return BEST_PIG_QTY
    */
    public Long getBestPigQty() {
        return getLong(D_BestPigQty);
    }

   /**
    * 设置最差值
    * @param WORST_VALUE
    */
	public void setWorstValue(Double value) {
        set(D_WorstValue,value);
    }
	
	/**
    * 获取最差值
    * @return WORST_VALUE
    */
    public Double getWorstValue() {
        return getDouble(D_WorstValue);
    }

   /**
    * 设置最差猪场ID
    * @param WORST_FARM_ID
    */
	public void setWorstFarmId(Long value) {
        set(D_WorstFarmId,value);
    }
	
	/**
    * 获取最差猪场ID
    * @return WORST_FARM_ID
    */
    public Long getWorstFarmId() {
        return getLong(D_WorstFarmId);
    }

   /**
    * 设置最差头数
    * @param WORST_PIG_QTY
    */
	public void setWorstPigQty(Long value) {
        set(D_WorstPigQty,value);
    }
	
	/**
    * 获取最差头数
    * @return WORST_PIG_QTY
    */
    public Long getWorstPigQty() {
        return getLong(D_WorstPigQty);
    }

   /**
    * 设置创建ID
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建ID
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
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("deletedFlag");
        propertes.add("status");
        propertes.add("notes");
        propertes.add("reportId");
        propertes.add("farmId");
        propertes.add("proType");
        propertes.add("indicator");
        propertes.add("bestValue");
        propertes.add("bestFarmId");
        propertes.add("bestPigQty");
        propertes.add("worstValue");
        propertes.add("worstFarmId");
        propertes.add("worstPigQty");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




