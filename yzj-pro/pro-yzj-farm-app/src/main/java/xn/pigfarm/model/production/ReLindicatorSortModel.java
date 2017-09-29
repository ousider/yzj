package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-3 13:37:17
 *       表：RE_L_INDICATOR_SORT
 */
public class ReLindicatorSortModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 612798442739955778L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId = "rowId";

    // 排序号
    private static final String D_SortNbr = "sortNbr";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag = "deletedFlag";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status = "status";

    // 备注
    private static final String D_Notes = "notes";

    // 报表ID
    private static final String D_ReportId = "reportId";

    // 猪场ID
    private static final String D_FarmId = "farmId";

    // 母猪存栏数
    private static final String D_SowPigNum = "sowPigNum";

    // 商品猪存栏数
    private static final String D_GoodPigNum = "goodPigNum";

    // 全程死亡率
    private static final String D_AllDeathRate = "allDeathRate";

    // 全程死亡率排名
    private static final String D_DeathRateSort = "deathRateSort";

    // 7030重
    private static final String D_Weight7030 = "weight7030";

    // 7030重排名
    private static final String D_WeightSort7030 = "weightSort7030";

    // 110KG出栏日龄
    private static final String D_OutDayage110kg = "outDayage110kg";

    // 110KG出栏日龄排名
    private static final String D_OutDayageSort110kg = "outDayageSort110kg";

    // 全程料肉比
    private static final String D_MaterialMeatRateWhole = "materialMeatRateWhole";

    // 育肥料肉比
    private static final String D_MaterialMeatRateFat = "materialMeatRateFat";

    // 销售猪只均重
    private static final String D_AvgSalePigWeight = "avgSalePigWeight";

    // 库存猪只均重
    private static final String D_AvgInventoryPigWeight = "avgInventoryPigWeight";

    // 创建人ID
    private static final String D_CreateId = "createId";

    // 创建日期
    private static final String D_CreateDate = "createDate";

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
     * 设置报表ID
     * 
     * @param REPORT_ID
     */
    public void setReportId(Long value) {
        set(D_ReportId, value);
    }

    /**
     * 获取报表ID
     * 
     * @return REPORT_ID
     */
    public Long getReportId() {
        return getLong(D_ReportId);
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
     * 设置母猪存栏数
     * 
     * @param SOW_PIG_NUM
     */
    public void setSowPigNum(Long value) {
        set(D_SowPigNum, value);
    }

    /**
     * 获取母猪存栏数
     * 
     * @return SOW_PIG_NUM
     */
    public Long getSowPigNum() {
        return getLong(D_SowPigNum);
    }

    /**
     * 设置商品猪存栏数
     * 
     * @param GOOD_PIG_NUM
     */
    public void setGoodPigNum(Long value) {
        set(D_GoodPigNum, value);
    }

    /**
     * 获取商品猪存栏数
     * 
     * @return GOOD_PIG_NUM
     */
    public Long getGoodPigNum() {
        return getLong(D_GoodPigNum);
    }

    /**
     * 设置全程死亡率
     * 
     * @param ALL_DEATH_RATE
     */
    public void setAllDeathRate(Double value) {
        set(D_AllDeathRate, value);
    }

    /**
     * 获取全程死亡率
     * 
     * @return ALL_DEATH_RATE
     */
    public Double getAllDeathRate() {
        return getDouble(D_AllDeathRate);
    }

    /**
     * 设置全程死亡率排名
     * 
     * @param DEATH_RATE_SORT
     */
    public void setDeathRateSort(Long value) {
        set(D_DeathRateSort, value);
    }

    /**
     * 获取全程死亡率排名
     * 
     * @return DEATH_RATE_SORT
     */
    public Long getDeathRateSort() {
        return getLong(D_DeathRateSort);
    }

    /**
     * 设置7030重
     * 
     * @param WEIGHT_7030
     */
    public void setWeight7030(Double value) {
        set(D_Weight7030, value);
    }

    /**
     * 获取7030重
     * 
     * @return WEIGHT_7030
     */
    public Double getWeight7030() {
        return getDouble(D_Weight7030);
    }

    /**
     * 设置7030重排名
     * 
     * @param WEIGHT_SORT_7030
     */
    public void setWeightSort7030(Long value) {
        set(D_WeightSort7030, value);
    }

    /**
     * 获取7030重排名
     * 
     * @return WEIGHT_SORT_7030
     */
    public Long getWeightSort7030() {
        return getLong(D_WeightSort7030);
    }

    /**
     * 设置110KG出栏日龄
     * 
     * @param OUT_DAYAGE_110KG
     */
    public void setOutDayage110kg(Double value) {
        set(D_OutDayage110kg, value);
    }

    /**
     * 获取110KG出栏日龄
     * 
     * @return OUT_DAYAGE_110KG
     */
    public Double getOutDayage110kg() {
        return getDouble(D_OutDayage110kg);
    }

    /**
     * 设置110KG出栏日龄排名
     * 
     * @param OUT_DAYAGE_SORT_110KG
     */
    public void setOutDayageSort110kg(Long value) {
        set(D_OutDayageSort110kg, value);
    }

    /**
     * 获取110KG出栏日龄排名
     * 
     * @return OUT_DAYAGE_SORT_110KG
     */
    public Long getOutDayageSort110kg() {
        return getLong(D_OutDayageSort110kg);
    }

    /**
     * 设置全程料肉比
     * 
     * @param MATERIAL_MEAT_RATE_WHOLE
     */
    public void setMaterialMeatRateWhole(Double value) {
        set(D_MaterialMeatRateWhole, value);
    }

    /**
     * 获取全程料肉比
     * 
     * @return MATERIAL_MEAT_RATE_WHOLE
     */
    public Double getMaterialMeatRateWhole() {
        return getDouble(D_MaterialMeatRateWhole);
    }

    /**
     * 设置育肥料肉比
     * 
     * @param MATERIAL_MEAT_RATE_FAT
     */
    public void setMaterialMeatRateFat(Double value) {
        set(D_MaterialMeatRateFat, value);
    }

    /**
     * 获取育肥料肉比
     * 
     * @return MATERIAL_MEAT_RATE_FAT
     */
    public Double getMaterialMeatRateFat() {
        return getDouble(D_MaterialMeatRateFat);
    }

    /**
     * 设置销售猪只均重
     * 
     * @param AVG_SALE_PIG_WEIGHT
     */
    public void setAvgSalePigWeight(Double value) {
        set(D_AvgSalePigWeight, value);
    }

    /**
     * 获取销售猪只均重
     * 
     * @return AVG_SALE_PIG_WEIGHT
     */
    public Double getAvgSalePigWeight() {
        return getDouble(D_AvgSalePigWeight);
    }

    /**
     * 设置库存猪只均重
     * 
     * @param AVG_INVENTORY_PIG_WEIGHT
     */
    public void setAvgInventoryPigWeight(Double value) {
        set(D_AvgInventoryPigWeight, value);
    }

    /**
     * 获取库存猪只均重
     * 
     * @return AVG_INVENTORY_PIG_WEIGHT
     */
    public Double getAvgInventoryPigWeight() {
        return getDouble(D_AvgInventoryPigWeight);
    }

    /**
     * 设置创建人ID
     * 
     * @param CREATE_ID
     */
    public void setCreateId(Long value) {
        set(D_CreateId, value);
    }

    /**
     * 获取创建人ID
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

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("deletedFlag");
        propertes.add("status");
        propertes.add("notes");
        propertes.add("reportId");
        propertes.add("farmId");
        propertes.add("sowPigNum");
        propertes.add("goodPigNum");
        propertes.add("allDeathRate");
        propertes.add("deathRateSort");
        propertes.add("weight7030");
        propertes.add("weightSort7030");
        propertes.add("outDayage110kg");
        propertes.add("outDayageSort110kg");
        propertes.add("materialMeatRateWhole");
        propertes.add("materialMeatRateFat");
        propertes.add("avgSalePigWeight");
        propertes.add("avgInventoryPigWeight");
        propertes.add("createId");
        propertes.add("createDate");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
