package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-14 13:25:26
 *       表：CD_M_MONTH_PERFORMANCE_MODULE 
 */
public class MonthPerformanceModuleModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = 1979945048370285245L;

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
	 //farmId 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //备注 
    private static final String D_Notes="notes";
	 //编码 
    private static final String D_BusinessCode="businessCode";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //开始日期 
    private static final String D_StartDate="startDate";
	 //结束日期 
    private static final String D_EndDate="endDate";
	 //考核区域 
    private static final String D_AssessRegion="assessRegion";
	 //考核内容 
    private static final String D_AssessContent="assessContent";
	 //最小考核指标 
    private static final String D_MinAssessIndex="minAssessIndex";
	 //最大考核指标 
    private static final String D_MaxAssessIndex="maxAssessIndex";
	 //考核指标 
    private static final String D_AssessIndex="assessIndex";
	 //奖励金额 
    private static final String D_Reward="reward";
	 //增减量 
    private static final String D_Offset="offset";
	 //增加金额 
    private static final String D_IncreasedAmount="increasedAmount";
	 //计算日期范围 
    private static final String D_PerformanceDateArea="performanceDateArea";
	 //计算公式 
    private static final String D_DesignFormulas="designFormulas";
	 //是否启动 1：启动 2：不启动 
    private static final String D_IsStart="isStart";
	

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
    * 设置farmId
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取farmId
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
    * 设置编码
    * @param BUSINESS_CODE
    */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
   /**
    * 获取编码
    * @return BUSINESS_CODE
    */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
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
	public void setCreateDate(Timestamp value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建日期
    * @return CREATE_DATE
    */
    public Timestamp getCreateDate() {
        return getTimestamp(D_CreateDate);
    }

	/**
    * 设置开始日期
    * @param START_DATE
    */
	public void setStartDate(Date value) {
        set(D_StartDate,value);
    }
	
   /**
    * 获取开始日期
    * @return START_DATE
    */
    public Date getStartDate() {
        return getDate(D_StartDate);
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
    * 设置考核区域
    * @param ASSESS_REGION
    */
	public void setAssessRegion(String value) {
        set(D_AssessRegion,value);
    }
	
   /**
    * 获取考核区域
    * @return ASSESS_REGION
    */
    public String getAssessRegion() {
        return getString(D_AssessRegion);
    }

	/**
    * 设置考核内容
    * @param ASSESS_CONTENT
    */
	public void setAssessContent(String value) {
        set(D_AssessContent,value);
    }
	
   /**
    * 获取考核内容
    * @return ASSESS_CONTENT
    */
    public String getAssessContent() {
        return getString(D_AssessContent);
    }

	/**
    * 设置最小考核指标
    * @param MIN_ASSESS_INDEX
    */
	public void setMinAssessIndex(Double value) {
        set(D_MinAssessIndex,value);
    }
	
   /**
    * 获取最小考核指标
    * @return MIN_ASSESS_INDEX
    */
    public Double getMinAssessIndex() {
        return getDouble(D_MinAssessIndex);
    }

	/**
    * 设置最大考核指标
    * @param MAX_ASSESS_INDEX
    */
	public void setMaxAssessIndex(Double value) {
        set(D_MaxAssessIndex,value);
    }
	
   /**
    * 获取最大考核指标
    * @return MAX_ASSESS_INDEX
    */
    public Double getMaxAssessIndex() {
        return getDouble(D_MaxAssessIndex);
    }

	/**
    * 设置考核指标
    * @param ASSESS_INDEX
    */
	public void setAssessIndex(Double value) {
        set(D_AssessIndex,value);
    }
	
   /**
    * 获取考核指标
    * @return ASSESS_INDEX
    */
    public Double getAssessIndex() {
        return getDouble(D_AssessIndex);
    }

	/**
    * 设置奖励金额
    * @param REWARD
    */
	public void setReward(Double value) {
        set(D_Reward,value);
    }
	
   /**
    * 获取奖励金额
    * @return REWARD
    */
    public Double getReward() {
        return getDouble(D_Reward);
    }

	/**
    * 设置增减量
    * @param OFFSET
    */
	public void setOffset(Double value) {
        set(D_Offset,value);
    }
	
   /**
    * 获取增减量
    * @return OFFSET
    */
    public Double getOffset() {
        return getDouble(D_Offset);
    }

	/**
    * 设置增加金额
    * @param INCREASED_AMOUNT
    */
	public void setIncreasedAmount(Double value) {
        set(D_IncreasedAmount,value);
    }
	
   /**
    * 获取增加金额
    * @return INCREASED_AMOUNT
    */
    public Double getIncreasedAmount() {
        return getDouble(D_IncreasedAmount);
    }

   /**
    * 设置计算日期范围
    * @param PERFORMANCE_DATE_AREA
    */
	public void setPerformanceDateArea(Long value) {
        set(D_PerformanceDateArea,value);
    }
	
	/**
    * 获取计算日期范围
    * @return PERFORMANCE_DATE_AREA
    */
    public Long getPerformanceDateArea() {
        return getLong(D_PerformanceDateArea);
    }

	/**
    * 设置计算公式
    * @param DESIGN_FORMULAS
    */
	public void setDesignFormulas(String value) {
        set(D_DesignFormulas,value);
    }
	
   /**
    * 获取计算公式
    * @return DESIGN_FORMULAS
    */
    public String getDesignFormulas() {
        return getString(D_DesignFormulas);
    }

   /**
    * 设置是否启动 1：启动 2：不启动
    * @param IS_START
    */
	public void setIsStart(Long value) {
        set(D_IsStart,value);
    }
	
	/**
    * 获取是否启动 1：启动 2：不启动
    * @return IS_START
    */
    public Long getIsStart() {
        return getLong(D_IsStart);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("notes");
        propertes.add("businessCode");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("startDate");
        propertes.add("endDate");
        propertes.add("assessRegion");
        propertes.add("assessContent");
        propertes.add("minAssessIndex");
        propertes.add("maxAssessIndex");
        propertes.add("assessIndex");
        propertes.add("reward");
        propertes.add("offset");
        propertes.add("increasedAmount");
        propertes.add("performanceDateArea");
        propertes.add("designFormulas");
        propertes.add("isStart");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




