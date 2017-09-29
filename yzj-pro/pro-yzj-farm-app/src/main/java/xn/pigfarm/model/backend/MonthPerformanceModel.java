package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-18 11:48:45
 *       表：CD_M_MONTH_PERFORMANCE
 */
public class MonthPerformanceModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -4630420424984246225L;

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

    // 编码
    private static final String D_BusinessCode = "businessCode";

    // 备注
    private static final String D_Notes = "notes";

    // 场长备注
    private static final String D_FarmNotes = "farmNotes";

    // 对应的猪场ID
    private static final String D_FarmId = "farmId";

    // 对应的公司ID
    private static final String D_CompanyId = "companyId";

    // 创建人
    private static final String D_CreateId = "createId";

    // 创建日期
    private static final String D_CreateDate = "createDate";

    // 年份
    private static final String D_Year = "year";

    // 月份
    private static final String D_Month = "month";

    // 考核区域
    private static final String D_AssessRegion = "assessRegion";

    // 考核内容
    private static final String D_AssessContent = "assessContent";

    // minAssessIndex
    private static final String D_MinAssessIndex = "minAssessIndex";

    // 考核指标
    private static final String D_AssessIndex = "assessIndex";

    // maxAssessIndex
    private static final String D_MaxAssessIndex = "maxAssessIndex";

    // 奖励金额
    private static final String D_Reward = "reward";

    // 增减量
    private static final String D_Offset = "offset";

    // 增加金额
    private static final String D_IncreasedAmount = "increasedAmount";

    // 系统指标
    private static final String D_SystemIndex = "systemIndex";

    // 最终指标
    private static final String D_UltimaIndex = "ultimaIndex";

    // 系统金额
    private static final String D_SystemAmount = "systemAmount";

    // 最终金额
    private static final String D_UltimaAmount = "ultimaAmount";

    // 绩效系统数据
    private static final String D_SystemNumrator = "systemNumrator";

    // 系统数绩效据
    private static final String D_SystemDenominator = "systemDenominator";

    // 产房存栏系数
    private static final String D_HandModulus = "handModulus";

    // 实际平均存栏
    private static final String D_AcutalAvgHand = "acutalAvgHand";

    // 系数标准存栏
    private static final String D_ModulusStandardHand = "modulusStandardHand";

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
     * 设置编码
     * 
     * @param BUSINESS_CODE
     */
    public void setBusinessCode(String value) {
        set(D_BusinessCode, value);
    }

    /**
     * 获取编码
     * 
     * @return BUSINESS_CODE
     */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
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
     * 设置场长备注
     * 
     * @param FARM_NOTES
     */
    public void setFarmNotes(String value) {
        set(D_FarmNotes, value);
    }

    /**
     * 获取场长备注
     * 
     * @return FARM_NOTES
     */
    public String getFarmNotes() {
        return getString(D_FarmNotes);
    }

    /**
     * 设置对应的猪场ID
     * 
     * @param FARM_ID
     */
    public void setFarmId(Long value) {
        set(D_FarmId, value);
    }

    /**
     * 获取对应的猪场ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置对应的公司ID
     * 
     * @param COMPANY_ID
     */
    public void setCompanyId(Long value) {
        set(D_CompanyId, value);
    }

    /**
     * 获取对应的公司ID
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
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
    public void setCreateDate(Date value) {
        set(D_CreateDate, value);
    }

    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
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
     * 设置考核区域
     * 
     * @param ASSESS_REGION
     */
    public void setAssessRegion(String value) {
        set(D_AssessRegion, value);
    }

    /**
     * 获取考核区域
     * 
     * @return ASSESS_REGION
     */
    public String getAssessRegion() {
        return getString(D_AssessRegion);
    }

    /**
     * 设置考核内容
     * 
     * @param ASSESS_CONTENT
     */
    public void setAssessContent(String value) {
        set(D_AssessContent, value);
    }

    /**
     * 获取考核内容
     * 
     * @return ASSESS_CONTENT
     */
    public String getAssessContent() {
        return getString(D_AssessContent);
    }

    /**
     * 设置minAssessIndex
     * 
     * @param MIN_ASSESS_INDEX
     */
    public void setMinAssessIndex(Double value) {
        set(D_MinAssessIndex, value);
    }

    /**
     * 获取minAssessIndex
     * 
     * @return MIN_ASSESS_INDEX
     */
    public Double getMinAssessIndex() {
        return getDouble(D_MinAssessIndex);
    }

    /**
     * 设置考核指标
     * 
     * @param ASSESS_INDEX
     */
    public void setAssessIndex(Double value) {
        set(D_AssessIndex, value);
    }

    /**
     * 获取考核指标
     * 
     * @return ASSESS_INDEX
     */
    public Double getAssessIndex() {
        return getDouble(D_AssessIndex);
    }

    /**
     * 设置maxAssessIndex
     * 
     * @param MAX_ASSESS_INDEX
     */
    public void setMaxAssessIndex(Double value) {
        set(D_MaxAssessIndex, value);
    }

    /**
     * 获取maxAssessIndex
     * 
     * @return MAX_ASSESS_INDEX
     */
    public Double getMaxAssessIndex() {
        return getDouble(D_MaxAssessIndex);
    }

    /**
     * 设置奖励金额
     * 
     * @param REWARD
     */
    public void setReward(Double value) {
        set(D_Reward, value);
    }

    /**
     * 获取奖励金额
     * 
     * @return REWARD
     */
    public Double getReward() {
        return getDouble(D_Reward);
    }

    /**
     * 设置增减量
     * 
     * @param OFFSET
     */
    public void setOffset(Double value) {
        set(D_Offset, value);
    }

    /**
     * 获取增减量
     * 
     * @return OFFSET
     */
    public Double getOffset() {
        return getDouble(D_Offset);
    }

    /**
     * 设置增加金额
     * 
     * @param INCREASED_AMOUNT
     */
    public void setIncreasedAmount(Double value) {
        set(D_IncreasedAmount, value);
    }

    /**
     * 获取增加金额
     * 
     * @return INCREASED_AMOUNT
     */
    public Double getIncreasedAmount() {
        return getDouble(D_IncreasedAmount);
    }

    /**
     * 设置系统指标
     * 
     * @param SYSTEM_INDEX
     */
    public void setSystemIndex(Double value) {
        set(D_SystemIndex, value);
    }

    /**
     * 获取系统指标
     * 
     * @return SYSTEM_INDEX
     */
    public Double getSystemIndex() {
        return getDouble(D_SystemIndex);
    }

    /**
     * 设置最终指标
     * 
     * @param ULTIMA_INDEX
     */
    public void setUltimaIndex(Double value) {
        set(D_UltimaIndex, value);
    }

    /**
     * 获取最终指标
     * 
     * @return ULTIMA_INDEX
     */
    public Double getUltimaIndex() {
        return getDouble(D_UltimaIndex);
    }

    /**
     * 设置系统金额
     * 
     * @param SYSTEM_AMOUNT
     */
    public void setSystemAmount(Double value) {
        set(D_SystemAmount, value);
    }

    /**
     * 获取系统金额
     * 
     * @return SYSTEM_AMOUNT
     */
    public Double getSystemAmount() {
        return getDouble(D_SystemAmount);
    }

    /**
     * 设置最终金额
     * 
     * @param ULTIMA_AMOUNT
     */
    public void setUltimaAmount(Double value) {
        set(D_UltimaAmount, value);
    }

    /**
     * 获取最终金额
     * 
     * @return ULTIMA_AMOUNT
     */
    public Double getUltimaAmount() {
        return getDouble(D_UltimaAmount);
    }

    /**
     * 设置绩效系统数据
     * 
     * @param SYSTEM_NUMRATOR
     */
    public void setSystemNumrator(Double value) {
        set(D_SystemNumrator, value);
    }

    /**
     * 获取绩效系统数据
     * 
     * @return SYSTEM_NUMRATOR
     */
    public Double getSystemNumrator() {
        return getDouble(D_SystemNumrator);
    }

    /**
     * 设置系统数绩效据
     * 
     * @param SYSTEM_DENOMINATOR
     */
    public void setSystemDenominator(Double value) {
        set(D_SystemDenominator, value);
    }

    /**
     * 获取系统数绩效据
     * 
     * @return SYSTEM_DENOMINATOR
     */
    public Double getSystemDenominator() {
        return getDouble(D_SystemDenominator);
    }

    /**
     * 设置产房存栏系数
     * 
     * @param HAND_MODULUS
     */
    public void setHandModulus(Double value) {
        set(D_HandModulus, value);
    }

    /**
     * 获取产房存栏系数
     * 
     * @return HAND_MODULUS
     */
    public Double getHandModulus() {
        return getDouble(D_HandModulus);
    }

    /**
     * 设置实际平均存栏
     * 
     * @param ACUTAL_AVG_HAND
     */
    public void setAcutalAvgHand(Double value) {
        set(D_AcutalAvgHand, value);
    }

    /**
     * 获取实际平均存栏
     * 
     * @return ACUTAL_AVG_HAND
     */
    public Double getAcutalAvgHand() {
        return getDouble(D_AcutalAvgHand);
    }

    /**
     * 设置系数标准存栏
     * 
     * @param MODULUS_STANDARD_HAND
     */
    public void setModulusStandardHand(Double value) {
        set(D_ModulusStandardHand, value);
    }

    /**
     * 获取系数标准存栏
     * 
     * @return MODULUS_STANDARD_HAND
     */
    public Double getModulusStandardHand() {
        return getDouble(D_ModulusStandardHand);
    }

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("businessCode");
        propertes.add("notes");
        propertes.add("farmNotes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("year");
        propertes.add("month");
        propertes.add("assessRegion");
        propertes.add("assessContent");
        propertes.add("minAssessIndex");
        propertes.add("assessIndex");
        propertes.add("maxAssessIndex");
        propertes.add("reward");
        propertes.add("offset");
        propertes.add("increasedAmount");
        propertes.add("systemIndex");
        propertes.add("ultimaIndex");
        propertes.add("systemAmount");
        propertes.add("ultimaAmount");
        propertes.add("systemNumrator");
        propertes.add("systemDenominator");
        propertes.add("handModulus");
        propertes.add("acutalAvgHand");
        propertes.add("modulusStandardHand");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
