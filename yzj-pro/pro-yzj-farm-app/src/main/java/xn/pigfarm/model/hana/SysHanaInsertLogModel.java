package xn.pigfarm.model.hana;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-4 13:16:38
 *       表：SYS_HANA_INSERT_LOG
 */
public class SysHanaInsertLogModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 4691830325414553722L;

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

    // 上传的数据日期
    private static final String D_ToSapDate = "toSapDate";

    // 创建时间
    private static final String D_CreatTime = "creatTime";

    // 删除时间
    private static final String D_DeletedTime = "deletedTime";

    // 再次上传SAP,默认N
    private static final String D_ToSapAgin = "toSapAgin";

    // 是否汇总
    private static final String D_Summary = "summary";

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
     * 设置上传的数据日期
     * 
     * @param TO_SAP_DATE
     */
    public void setToSapDate(Date value) {
        set(D_ToSapDate, value);
    }

    /**
     * 获取上传的数据日期
     * 
     * @return TO_SAP_DATE
     */
    public Date getToSapDate() {
        return getDate(D_ToSapDate);
    }

    /**
     * 设置创建时间
     * 
     * @param CREAT_TIME
     */
    public void setCreatTime(Timestamp value) {
        set(D_CreatTime, value);
    }

    /**
     * 获取创建时间
     * 
     * @return CREAT_TIME
     */
    public Timestamp getCreatTime() {
        return getTimestamp(D_CreatTime);
    }

    /**
     * 设置删除时间
     * 
     * @param DELETED_TIME
     */
    public void setDeletedTime(Timestamp value) {
        set(D_DeletedTime, value);
    }

    /**
     * 获取删除时间
     * 
     * @return DELETED_TIME
     */
    public Timestamp getDeletedTime() {
        return getTimestamp(D_DeletedTime);
    }

    /**
     * 设置再次上传SAP,默认N
     * 
     * @param TO_SAP_AGIN
     */
    public void setToSapAgin(String value) {
        set(D_ToSapAgin, value);
    }

    /**
     * 获取再次上传SAP,默认N
     * 
     * @return TO_SAP_AGIN
     */
    public String getToSapAgin() {
        return getString(D_ToSapAgin);
    }

    /**
     * 设置是否汇总
     * 
     * @param SUMMARY
     */
    public void setSummary(String value) {
        set(D_Summary, value);
    }

    /**
     * 获取是否汇总
     * 
     * @return SUMMARY
     */
    public String getSummary() {
        return getString(D_Summary);
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
        propertes.add("toSapDate");
        propertes.add("creatTime");
        propertes.add("deletedTime");
        propertes.add("toSapAgin");
        propertes.add("summary");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
