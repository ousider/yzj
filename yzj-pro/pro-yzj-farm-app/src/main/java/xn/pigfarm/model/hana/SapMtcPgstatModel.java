package xn.pigfarm.model.hana;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-28 10:41:11
 *       表：SAP_MTC_PGSTAT
 */
public class SapMtcPgstatModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 4210658330189603371L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId = "rowId";

    // 排序号
    private static final String D_SortNbr = "sortNbr";

    // 状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status = "status";

    // 记录删除标志: [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag = "deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag = "originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp = "originApp";

    // 备注
    private static final String D_Notes = "notes";

    // 公司Id
    private static final String D_CompanyId = "companyId";

    // 猪场Id
    private static final String D_FarmId = "farmId";

    // 猪舍id
    private static final String D_HouseId = "houseId";

    // sys_hana_insert_log(ID)
    private static final String D_HanaLogId = "hanaLogId";

    // ITFC中间主表ID
    private static final String D_ItfcId = "itfcId";

    // 创建时间
    private static final String D_CreateTime = "createTime";

    // 分公司编码
    private static final String D_MtcbranchId = "mtcbranchId";

    // 猪场编码
    private static final String D_MtcdeptId = "mtcdeptId";

    // 猪舍编号
    private static final String D_Mtcunit = "mtcunit";

    // 期间
    private static final String D_Mtcperiod = "mtcperiod";

    // 妊娠阶段1 (轻胎)
    private static final String D_MtcpregStep1 = "mtcpregStep1";

    // 妊娠阶段2 (重胎)
    private static final String D_MtcpregStep2 = "mtcpregStep2";

    // 妊娠阶段3
    private static final String D_MtcpregStep3 = "mtcpregStep3";

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
     * 设置状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
    public void setStatus(String value) {
        set(D_Status, value);
    }

    /**
     * 获取状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

    /**
     * 设置记录删除标志: [0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
    public void setDeletedFlag(String value) {
        set(D_DeletedFlag, value);
    }

    /**
     * 获取记录删除标志: [0]-未删除;[1]-逻辑删除
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
     * 设置公司Id
     * 
     * @param COMPANY_ID
     */
    public void setCompanyId(Long value) {
        set(D_CompanyId, value);
    }

    /**
     * 获取公司Id
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置猪场Id
     * 
     * @param FARM_ID
     */
    public void setFarmId(Long value) {
        set(D_FarmId, value);
    }

    /**
     * 获取猪场Id
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置猪舍id
     * 
     * @param HOUSE_ID
     */
    public void setHouseId(Long value) {
        set(D_HouseId, value);
    }

    /**
     * 获取猪舍id
     * 
     * @return HOUSE_ID
     */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

    /**
     * 设置sys_hana_insert_log(ID)
     * 
     * @param HANA_LOG_ID
     */
    public void setHanaLogId(Long value) {
        set(D_HanaLogId, value);
    }

    /**
     * 获取sys_hana_insert_log(ID)
     * 
     * @return HANA_LOG_ID
     */
    public Long getHanaLogId() {
        return getLong(D_HanaLogId);
    }

    /**
     * 设置ITFC中间主表ID
     * 
     * @param ITFC_ID
     */
    public void setItfcId(Long value) {
        set(D_ItfcId, value);
    }

    /**
     * 获取ITFC中间主表ID
     * 
     * @return ITFC_ID
     */
    public Long getItfcId() {
        return getLong(D_ItfcId);
    }

    /**
     * 设置创建时间
     * 
     * @param CREATE_TIME
     */
    public void setCreateTime(Timestamp value) {
        set(D_CreateTime, value);
    }

    /**
     * 获取创建时间
     * 
     * @return CREATE_TIME
     */
    public Timestamp getCreateTime() {
        return getTimestamp(D_CreateTime);
    }

    /**
     * 设置分公司编码
     * 
     * @param MTC_BranchID
     */
    public void setMtcbranchId(String value) {
        set(D_MtcbranchId, value);
    }

    /**
     * 获取分公司编码
     * 
     * @return MTC_BranchID
     */
    public String getMtcbranchId() {
        return getString(D_MtcbranchId);
    }

    /**
     * 设置猪场编码
     * 
     * @param MTC_DeptID
     */
    public void setMtcdeptId(String value) {
        set(D_MtcdeptId, value);
    }

    /**
     * 获取猪场编码
     * 
     * @return MTC_DeptID
     */
    public String getMtcdeptId() {
        return getString(D_MtcdeptId);
    }

    /**
     * 设置猪舍编号
     * 
     * @param MTC_Unit
     */
    public void setMtcunit(String value) {
        set(D_Mtcunit, value);
    }

    /**
     * 获取猪舍编号
     * 
     * @return MTC_Unit
     */
    public String getMtcunit() {
        return getString(D_Mtcunit);
    }

    /**
     * 设置期间
     * 
     * @param MTC_Period
     */
    public void setMtcperiod(String value) {
        set(D_Mtcperiod, value);
    }

    /**
     * 获取期间
     * 
     * @return MTC_Period
     */
    public String getMtcperiod() {
        return getString(D_Mtcperiod);
    }

    /**
     * 设置妊娠阶段1 (轻胎)
     * 
     * @param MTC_PregStep_1
     */
    public void setMtcpregStep1(String value) {
        set(D_MtcpregStep1, value);
    }

    /**
     * 获取妊娠阶段1 (轻胎)
     * 
     * @return MTC_PregStep_1
     */
    public String getMtcpregStep1() {
        return getString(D_MtcpregStep1);
    }

    /**
     * 设置妊娠阶段2 (重胎)
     * 
     * @param MTC_PregStep_2
     */
    public void setMtcpregStep2(String value) {
        set(D_MtcpregStep2, value);
    }

    /**
     * 获取妊娠阶段2 (重胎)
     * 
     * @return MTC_PregStep_2
     */
    public String getMtcpregStep2() {
        return getString(D_MtcpregStep2);
    }

    /**
     * 设置妊娠阶段3
     * 
     * @param MTC_PregStep_3
     */
    public void setMtcpregStep3(String value) {
        set(D_MtcpregStep3, value);
    }

    /**
     * 获取妊娠阶段3
     * 
     * @return MTC_PregStep_3
     */
    public String getMtcpregStep3() {
        return getString(D_MtcpregStep3);
    }

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("companyId");
        propertes.add("farmId");
        propertes.add("houseId");
        propertes.add("hanaLogId");
        propertes.add("itfcId");
        propertes.add("createTime");
        propertes.add("mtcbranchId");
        propertes.add("mtcdeptId");
        propertes.add("mtcunit");
        propertes.add("mtcperiod");
        propertes.add("mtcpregStep1");
        propertes.add("mtcpregStep2");
        propertes.add("mtcpregStep3");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
