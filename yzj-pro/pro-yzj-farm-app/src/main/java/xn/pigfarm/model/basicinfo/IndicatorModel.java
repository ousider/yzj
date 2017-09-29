package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-20 16:22:20
 *       表：PP_L_INDICATOR
 */
public class IndicatorModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -3448311731740398171L;

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

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag = "originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp = "originApp";

    // 备注
    private static final String D_Notes = "notes";

    // 对应的公司ID
    private static final String D_FarmId = "farmId";

    // companyId
    private static final String D_CompanyId = "companyId";

    // 指标类型ID
    private static final String D_IndTypeId = "indTypeId";

    // 编号
    private static final String D_BusinessCode = "businessCode";

    // 指标名称
    private static final String D_IndName = "indName";

    // 标准值
    private static final String D_StandardValue = "standardValue";

    // 最小值
    private static final String D_MinValue = "minValue";

    // 最大值
    private static final String D_MaxValue = "maxValue";

    // 正负区间
    private static final String D_Section = "section";

    // 单位
    private static final String D_Unit = "unit";

    // 计算公式
    private static final String D_Formula = "formula";

    // 描述
    private static final String D_Description = "description";

    // 周目标
    private static final String D_WeekStandardValue = "weekStandardValue";

    // 月目标
    private static final String D_MonthStandardValue = "monthStandardValue";

    // 季度目标
    private static final String D_QuarterStandardValue = "quarterStandardValue";

    // 年目标
    private static final String D_YearStandardValue = "yearStandardValue";

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
     * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @param ORIGIN_FLAG
     */
    public void setOriginFlag(String value) {
        set(D_OriginFlag, value);
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
        set(D_OriginApp, value);
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
     * 设置对应的公司ID
     * 
     * @param FARM_ID
     */
    public void setFarmId(Long value) {
        set(D_FarmId, value);
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
        set(D_CompanyId, value);
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
     * 设置指标类型ID
     * 
     * @param IND_TYPE_ID
     */
    public void setIndTypeId(Long value) {
        set(D_IndTypeId, value);
    }

    /**
     * 获取指标类型ID
     * 
     * @return IND_TYPE_ID
     */
    public Long getIndTypeId() {
        return getLong(D_IndTypeId);
    }

    /**
     * 设置编号
     * 
     * @param BUSINESS_CODE
     */
    public void setBusinessCode(String value) {
        set(D_BusinessCode, value);
    }

    /**
     * 获取编号
     * 
     * @return BUSINESS_CODE
     */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

    /**
     * 设置指标名称
     * 
     * @param IND_NAME
     */
    public void setIndName(String value) {
        set(D_IndName, value);
    }

    /**
     * 获取指标名称
     * 
     * @return IND_NAME
     */
    public String getIndName() {
        return getString(D_IndName);
    }

    /**
     * 设置标准值
     * 
     * @param STANDARD_VALUE
     */
    public void setStandardValue(Double value) {
        set(D_StandardValue, value);
    }

    /**
     * 获取标准值
     * 
     * @return STANDARD_VALUE
     */
    public Double getStandardValue() {
        return getDouble(D_StandardValue);
    }

    /**
     * 设置最小值
     * 
     * @param MIN_VALUE
     */
    public void setMinValue(Double value) {
        set(D_MinValue, value);
    }

    /**
     * 获取最小值
     * 
     * @return MIN_VALUE
     */
    public Double getMinValue() {
        return getDouble(D_MinValue);
    }

    /**
     * 设置最大值
     * 
     * @param MAX_VALUE
     */
    public void setMaxValue(Double value) {
        set(D_MaxValue, value);
    }

    /**
     * 获取最大值
     * 
     * @return MAX_VALUE
     */
    public Double getMaxValue() {
        return getDouble(D_MaxValue);
    }

    /**
     * 设置正负区间
     * 
     * @param SECTION
     */
    public void setSection(Double value) {
        set(D_Section, value);
    }

    /**
     * 获取正负区间
     * 
     * @return SECTION
     */
    public Double getSection() {
        return getDouble(D_Section);
    }

    /**
     * 设置单位
     * 
     * @param UNIT
     */
    public void setUnit(String value) {
        set(D_Unit, value);
    }

    /**
     * 获取单位
     * 
     * @return UNIT
     */
    public String getUnit() {
        return getString(D_Unit);
    }

    /**
     * 设置计算公式
     * 
     * @param FORMULA
     */
    public void setFormula(String value) {
        set(D_Formula, value);
    }

    /**
     * 获取计算公式
     * 
     * @return FORMULA
     */
    public String getFormula() {
        return getString(D_Formula);
    }

    /**
     * 设置描述
     * 
     * @param DESCRIPTION
     */
    public void setDescription(String value) {
        set(D_Description, value);
    }

    /**
     * 获取描述
     * 
     * @return DESCRIPTION
     */
    public String getDescription() {
        return getString(D_Description);
    }

    /**
     * 设置周目标
     * 
     * @param WEEK_STANDARD_VALUE
     */
    public void setWeekStandardValue(Double value) {
        set(D_WeekStandardValue, value);
    }

    /**
     * 获取周目标
     * 
     * @return WEEK_STANDARD_VALUE
     */
    public Double getWeekStandardValue() {
        return getDouble(D_WeekStandardValue);
    }

    /**
     * 设置月目标
     * 
     * @param MONTH_STANDARD_VALUE
     */
    public void setMonthStandardValue(Double value) {
        set(D_MonthStandardValue, value);
    }

    /**
     * 获取月目标
     * 
     * @return MONTH_STANDARD_VALUE
     */
    public Double getMonthStandardValue() {
        return getDouble(D_MonthStandardValue);
    }

    /**
     * 设置季度目标
     * 
     * @param QUARTER_STANDARD_VALUE
     */
    public void setQuarterStandardValue(Double value) {
        set(D_QuarterStandardValue, value);
    }

    /**
     * 获取季度目标
     * 
     * @return QUARTER_STANDARD_VALUE
     */
    public Double getQuarterStandardValue() {
        return getDouble(D_QuarterStandardValue);
    }

    /**
     * 设置年目标
     * 
     * @param YEAR_STANDARD_VALUE
     */
    public void setYearStandardValue(Double value) {
        set(D_YearStandardValue, value);
    }

    /**
     * 获取年目标
     * 
     * @return YEAR_STANDARD_VALUE
     */
    public Double getYearStandardValue() {
        return getDouble(D_YearStandardValue);
    }

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("indTypeId");
        propertes.add("businessCode");
        propertes.add("indName");
        propertes.add("standardValue");
        propertes.add("minValue");
        propertes.add("maxValue");
        propertes.add("section");
        propertes.add("unit");
        propertes.add("formula");
        propertes.add("description");
        propertes.add("weekStandardValue");
        propertes.add("monthStandardValue");
        propertes.add("quarterStandardValue");
        propertes.add("yearStandardValue");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
