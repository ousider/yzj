package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-9-19 10:25:41
 *       表：CD_M_HOUSE_STATUS
 */
public class HouseStatusModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 6143761915376368828L;

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

    // 对应的猪场ID
    private static final String D_FarmId = "farmId";

    // 公司ID
    private static final String D_CompanyId = "companyId";

    // 猪舍ID
    private static final String D_HouseId = "houseId";

    // 采集日期
    private static final String D_CollectDate = "collectDate";

    // 猪只数量
    private static final String D_PigNum = "pigNum";

    // 猪舍状态 :在产、在养、清棚
    private static final String D_HouseSatus = "houseSatus";

    // 采食量 (单位g)
    private static final String D_FeedDose = "feedDose";

    // 用药
    private static final String D_UseDrug = "useDrug";

    // 免疫
    private static final String D_Immune = "immune";

    // 最低温度
    private static final String D_MinTemp = "minTemp";

    // 最高温度
    private static final String D_MaxTemp = "maxTemp";

    // 日龄
    private static final String D_Dayage = "dayage";

    // 猪群健康度 :健康、腹泻
    private static final String D_SwineryHealth = "swineryHealth";

    // 母猪死淘 :产房的母猪死亡/淘汰数量
    private static final String D_SowDieObsolete = "sowDieObsolete";

    // 死亡数量 :商品猪死亡数量
    private static final String D_DieNum = "dieNum";

    // 采集人
    private static final String D_CollectId = "collectId";

    // 创建时间
    private static final String D_CreateTime = "createTime";

    // 腹泻窝数
    private static final String D_DiarrheaNum = "diarrheaNum";

    // 喘气比例
    private static final String D_GaspRate = "gaspRate";

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
     * 设置公司ID
     * 
     * @param COMPANY_ID
     */
    public void setCompanyId(Long value) {
        set(D_CompanyId, value);
    }

    /**
     * 获取公司ID
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置猪舍ID
     * 
     * @param HOUSE_ID
     */
    public void setHouseId(Long value) {
        set(D_HouseId, value);
    }

    /**
     * 获取猪舍ID
     * 
     * @return HOUSE_ID
     */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

    /**
     * 设置采集日期
     * 
     * @param COLLECT_DATE
     */
    public void setCollectDate(Date value) {
        set(D_CollectDate, value);
    }

    /**
     * 获取采集日期
     * 
     * @return COLLECT_DATE
     */
    public Date getCollectDate() {
        return getDate(D_CollectDate);
    }

    /**
     * 设置猪只数量
     * 
     * @param PIG_NUM
     */
    public void setPigNum(Long value) {
        set(D_PigNum, value);
    }

    /**
     * 获取猪只数量
     * 
     * @return PIG_NUM
     */
    public Long getPigNum() {
        return getLong(D_PigNum);
    }

    /**
     * 设置猪舍状态 :在产、在养、清棚
     * 
     * @param HOUSE_SATUS
     */
    public void setHouseSatus(Long value) {
        set(D_HouseSatus, value);
    }

    /**
     * 获取猪舍状态 :在产、在养、清棚
     * 
     * @return HOUSE_SATUS
     */
    public Long getHouseSatus() {
        return getLong(D_HouseSatus);
    }

    /**
     * 设置采食量 (单位g)
     * 
     * @param FEED_DOSE
     */
    public void setFeedDose(String value) {
        set(D_FeedDose, value);
    }

    /**
     * 获取采食量 (单位g)
     * 
     * @return FEED_DOSE
     */
    public String getFeedDose() {
        return getString(D_FeedDose);
    }

    /**
     * 设置用药
     * 
     * @param USE_DRUG
     */
    public void setUseDrug(String value) {
        set(D_UseDrug, value);
    }

    /**
     * 获取用药
     * 
     * @return USE_DRUG
     */
    public String getUseDrug() {
        return getString(D_UseDrug);
    }

    /**
     * 设置免疫
     * 
     * @param IMMUNE
     */
    public void setImmune(String value) {
        set(D_Immune, value);
    }

    /**
     * 获取免疫
     * 
     * @return IMMUNE
     */
    public String getImmune() {
        return getString(D_Immune);
    }

    /**
     * 设置最低温度
     * 
     * @param MIN_TEMP
     */
    public void setMinTemp(Double value) {
        set(D_MinTemp, value);
    }

    /**
     * 获取最低温度
     * 
     * @return MIN_TEMP
     */
    public Double getMinTemp() {
        return getDouble(D_MinTemp);
    }

    /**
     * 设置最高温度
     * 
     * @param MAX_TEMP
     */
    public void setMaxTemp(Double value) {
        set(D_MaxTemp, value);
    }

    /**
     * 获取最高温度
     * 
     * @return MAX_TEMP
     */
    public Double getMaxTemp() {
        return getDouble(D_MaxTemp);
    }

    /**
     * 设置日龄
     * 
     * @param DAYAGE
     */
    public void setDayage(Long value) {
        set(D_Dayage, value);
    }

    /**
     * 获取日龄
     * 
     * @return DAYAGE
     */
    public Long getDayage() {
        return getLong(D_Dayage);
    }

    /**
     * 设置猪群健康度 :健康、腹泻
     * 
     * @param SWINERY_HEALTH
     */
    public void setSwineryHealth(Long value) {
        set(D_SwineryHealth, value);
    }

    /**
     * 获取猪群健康度 :健康、腹泻
     * 
     * @return SWINERY_HEALTH
     */
    public Long getSwineryHealth() {
        return getLong(D_SwineryHealth);
    }

    /**
     * 设置母猪死淘 :产房的母猪死亡/淘汰数量
     * 
     * @param SOW_DIE_OBSOLETE
     */
    public void setSowDieObsolete(Long value) {
        set(D_SowDieObsolete, value);
    }

    /**
     * 获取母猪死淘 :产房的母猪死亡/淘汰数量
     * 
     * @return SOW_DIE_OBSOLETE
     */
    public Long getSowDieObsolete() {
        return getLong(D_SowDieObsolete);
    }

    /**
     * 设置死亡数量 :商品猪死亡数量
     * 
     * @param DIE_NUM
     */
    public void setDieNum(Long value) {
        set(D_DieNum, value);
    }

    /**
     * 获取死亡数量 :商品猪死亡数量
     * 
     * @return DIE_NUM
     */
    public Long getDieNum() {
        return getLong(D_DieNum);
    }

    /**
     * 设置采集人
     * 
     * @param COLLECT_ID
     */
    public void setCollectId(Long value) {
        set(D_CollectId, value);
    }

    /**
     * 获取采集人
     * 
     * @return COLLECT_ID
     */
    public Long getCollectId() {
        return getLong(D_CollectId);
    }

    /**
     * 设置创建时间
     * 
     * @param CREATE_TIME
     */
    public void setCreateTime(Date value) {
        set(D_CreateTime, value);
    }

    /**
     * 获取创建时间
     * 
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return getDate(D_CreateTime);
    }

    /**
     * 设置腹泻窝数
     * 
     * @param DIARRHEA_NUM
     */
    public void setDiarrheaNum(Long value) {
        set(D_DiarrheaNum, value);
    }

    /**
     * 获取腹泻窝数
     * 
     * @return DIARRHEA_NUM
     */
    public Long getDiarrheaNum() {
        return getLong(D_DiarrheaNum);
    }

    /**
     * 设置喘气比例
     * 
     * @param GASP_RATE
     */
    public void setGaspRate(String value) {
        set(D_GaspRate, value);
    }

    /**
     * 获取喘气比例
     * 
     * @return GASP_RATE
     */
    public String getGaspRate() {
        return getString(D_GaspRate);
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
        propertes.add("houseId");
        propertes.add("collectDate");
        propertes.add("pigNum");
        propertes.add("houseSatus");
        propertes.add("feedDose");
        propertes.add("useDrug");
        propertes.add("immune");
        propertes.add("minTemp");
        propertes.add("maxTemp");
        propertes.add("dayage");
        propertes.add("swineryHealth");
        propertes.add("sowDieObsolete");
        propertes.add("dieNum");
        propertes.add("collectId");
        propertes.add("createTime");
        propertes.add("diarrheaNum");
        propertes.add("gaspRate");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
