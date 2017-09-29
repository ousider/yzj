package xn.pigfarm.model.production;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-28 14:53:58
 *       表：RE_M_REPORT
 */
public class ReMreportModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -8121441824162323328L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId = "rowId";

    // 排序号
    private static final String D_SortNbr = "sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status = "status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag = "deletedFlag";

    // 备注
    private static final String D_Notes = "notes";

    // 报表类型 1:周报，2；月报
    private static final String D_ReportType = "reportType";

    // 年份
    private static final String D_Year = "year";

    // 月份
    private static final String D_Month = "month";

    // 周
    private static final String D_Week = "week";

    // 季度
    private static final String D_Quarter = "quarter";

    // 开始时间
    private static final String D_StartDate = "startDate";

    // 结束时间
    private static final String D_EndDate = "endDate";

    // 数据分析
    private static final String D_DataAnalysis = "dataAnalysis";

    // 版本号
    private static final String D_Version = "version";

    // 猪场ID
    private static final String D_FarmId = "farmId";

    // 创建人
    private static final String D_CreateId = "createId";

    // 创建日期
    private static final String D_CreateDate = "createDate";

    // 备注人ID
    private static final String D_NotesId = "notesId";

    // 销售备注
    private static final String D_SaleNotes = "saleNotes";

    // 生产备注
    private static final String D_ProductionNotes = "productionNotes";

    // 大生物安全备注
    private static final String D_BiologicalSafetyNotes = "biologicalSafetyNotes";

    // 种群规划备注
    private static final String D_PopulationPlanningNotes = "populationPlanningNotes";

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
    public void setRowId(Long value) {
        set(D_RowId, value);
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
        set(D_SortNbr, value);
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
        set(D_Status, value);
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
        set(D_DeletedFlag, value);
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
     * 设置备注
     * 
     * @param NOTES
     */
    public void setNotes(String value) {
        set(D_Notes, value);
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
     * 设置报表类型 1:周报，2；月报
     * 
     * @param REPORT_TYPE
     */
    public void setReportType(String value) {
        set(D_ReportType, value);
    }

    /**
     * 获取报表类型 1:周报，2；月报
     * 
     * @return REPORT_TYPE
     */
    public String getReportType() {
        return getString(D_ReportType);
    }

    /**
     * 设置年份
     * 
     * @param YEAR
     */
    public void setYear(Long value) {
        set(D_Year, value);
    }

    /**
     * 获取年份
     * 
     * @return YEAR
     */
    public Long getYear() {
        return getLong(D_Year);
    }

    /**
     * 设置月份
     * 
     * @param MONTH
     */
    public void setMonth(Long value) {
        set(D_Month, value);
    }

    /**
     * 获取月份
     * 
     * @return MONTH
     */
    public Long getMonth() {
        return getLong(D_Month);
    }

    /**
     * 设置周
     * 
     * @param WEEK
     */
    public void setWeek(Long value) {
        set(D_Week, value);
    }

    /**
     * 获取周
     * 
     * @return WEEK
     */
    public Long getWeek() {
        return getLong(D_Week);
    }

    /**
     * 设置季度
     * 
     * @param QUARTER
     */
    public void setQuarter(Long value) {
        set(D_Quarter, value);
    }

    /**
     * 获取季度
     * 
     * @return QUARTER
     */
    public Long getQuarter() {
        return getLong(D_Quarter);
    }

    /**
     * 设置开始时间
     * 
     * @param START_DATE
     */
    public void setStartDate(Date value) {
        set(D_StartDate, value);
    }

    /**
     * 获取开始时间
     * 
     * @return START_DATE
     */
    public Date getStartDate() {
        return getDate(D_StartDate);
    }

    /**
     * 设置结束时间
     * 
     * @param END_DATE
     */
    public void setEndDate(Date value) {
        set(D_EndDate, value);
    }

    /**
     * 获取结束时间
     * 
     * @return END_DATE
     */
    public Date getEndDate() {
        return getDate(D_EndDate);
    }

    /**
     * 设置数据分析
     * 
     * @param DATA_ANALYSIS
     */
    public void setDataAnalysis(String value) {
        set(D_DataAnalysis, value);
    }

    /**
     * 获取数据分析
     * 
     * @return DATA_ANALYSIS
     */
    public String getDataAnalysis() {
        return getString(D_DataAnalysis);
    }

    /**
     * 设置版本号
     * 
     * @param VERSION
     */
    public void setVersion(Long value) {
        set(D_Version, value);
    }

    /**
     * 获取版本号
     * 
     * @return VERSION
     */
    public Long getVersion() {
        return getLong(D_Version);
    }

    /**
     * 设置猪场ID
     * 
     * @param FARM_ID
     */
    public void setFarmId(Long value) {
        set(D_FarmId, value);
    }

    /**
     * 获取猪场ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置创建人
     * 
     * @param CREATE_ID
     */
    public void setCreateId(Long value) {
        set(D_CreateId, value);
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
        set(D_CreateDate, value);
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
     * 设置备注人ID
     * 
     * @param NOTES_ID
     */
    public void setNotesId(Long value) {
        set(D_NotesId, value);
    }

    /**
     * 获取备注人ID
     * 
     * @return NOTES_ID
     */
    public Long getNotesId() {
        return getLong(D_NotesId);
    }

    /**
     * 设置销售备注
     * 
     * @param SALE_NOTES
     */
    public void setSaleNotes(String value) {
        set(D_SaleNotes, value);
    }

    /**
     * 获取销售备注
     * 
     * @return SALE_NOTES
     */
    public String getSaleNotes() {
        return getString(D_SaleNotes);
    }

    /**
     * 设置生产备注
     * 
     * @param PRODUCTION_NOTES
     */
    public void setProductionNotes(String value) {
        set(D_ProductionNotes, value);
    }

    /**
     * 获取生产备注
     * 
     * @return PRODUCTION_NOTES
     */
    public String getProductionNotes() {
        return getString(D_ProductionNotes);
    }

    /**
     * 设置大生物安全备注
     * 
     * @param BIOLOGICAL_SAFETY_NOTES
     */
    public void setBiologicalSafetyNotes(String value) {
        set(D_BiologicalSafetyNotes, value);
    }

    /**
     * 获取大生物安全备注
     * 
     * @return BIOLOGICAL_SAFETY_NOTES
     */
    public String getBiologicalSafetyNotes() {
        return getString(D_BiologicalSafetyNotes);
    }

    /**
     * 设置种群规划备注
     * 
     * @param POPULATION_PLANNING_NOTES
     */
    public void setPopulationPlanningNotes(String value) {
        set(D_PopulationPlanningNotes, value);
    }

    /**
     * 获取种群规划备注
     * 
     * @return POPULATION_PLANNING_NOTES
     */
    public String getPopulationPlanningNotes() {
        return getString(D_PopulationPlanningNotes);
    }

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("reportType");
        propertes.add("year");
        propertes.add("month");
        propertes.add("week");
        propertes.add("quarter");
        propertes.add("startDate");
        propertes.add("endDate");
        propertes.add("dataAnalysis");
        propertes.add("version");
        propertes.add("farmId");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("notesId");
        propertes.add("saleNotes");
        propertes.add("productionNotes");
        propertes.add("biologicalSafetyNotes");
        propertes.add("populationPlanningNotes");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
