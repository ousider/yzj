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
 * @date :2017-6-27 14:03:14
 *       表：SAP_MTC_ITFC
 */
public class SapMtcItfcModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 2022511776191963970L;

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

    // sys_hana_insert_log(ID)
    private static final String D_HanaLogId = "hanaLogId";

    // 序号
    private static final String D_MtcId = "mtcId";

    // 分公司编号
    private static final String D_Mtcbranch = "mtcbranch";

    // 业务日期
    private static final String D_MtcdocDate = "mtcdocDate";

    // 业务代码
    private static final String D_MtcobjCode = "mtcobjCode";

    // 新农+单据编号
    private static final String D_MtcdocNum = "mtcdocNum";

    // 优先级 -值越小优先级越高
    private static final String D_Mtcpriority = "mtcpriority";

    // 处理区分 (A - 新增 U - 更新 D - 取消 C - 关闭)
    private static final String D_MtctransType = "mtctransType";

    // 原始单据编号
    private static final String D_MtcbaseEntry = "mtcbaseEntry";

    // 业务标识
    private static final String D_Mtcflag = "mtcflag";

    // 创建日期
    private static final String D_MtccreateTime = "mtccreateTime";

    // 同步状态 ("N – 未同步 P – 处理中 Y – 已同步 E – 同步错误")
    private static final String D_Mtcstatus = "mtcstatus";

    // 处理日期
    private static final String D_MtcupdateTime = "mtcupdateTime";

    // 错误日志
    private static final String D_MtcerrorMsg = "mtcerrorMsg";

    // 处理次数
    private static final String D_Mtctimes = "mtctimes";

    // JSON文件
    private static final String D_MtcdataFile = "mtcdataFile";

    // JSON文件长度
    private static final String D_MtcdataFileLen = "mtcdataFileLen";

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
     * 设置序号
     * 
     * @param MTC_ID
     */
    public void setMtcId(Long value) {
        set(D_MtcId, value);
    }

    /**
     * 获取序号
     * 
     * @return MTC_ID
     */
    public Long getMtcId() {
        return getLong(D_MtcId);
    }

    /**
     * 设置分公司编号
     * 
     * @param MTC_Branch
     */
    public void setMtcbranch(String value) {
        set(D_Mtcbranch, value);
    }

    /**
     * 获取分公司编号
     * 
     * @return MTC_Branch
     */
    public String getMtcbranch() {
        return getString(D_Mtcbranch);
    }

    /**
     * 设置业务日期
     * 
     * @param MTC_DocDate
     */
    public void setMtcdocDate(Date value) {
        set(D_MtcdocDate, value);
    }

    /**
     * 获取业务日期
     * 
     * @return MTC_DocDate
     */
    public Date getMtcdocDate() {
        return getDate(D_MtcdocDate);
    }

    /**
     * 设置业务代码
     * 
     * @param MTC_ObjCode
     */
    public void setMtcobjCode(String value) {
        set(D_MtcobjCode, value);
    }

    /**
     * 获取业务代码
     * 
     * @return MTC_ObjCode
     */
    public String getMtcobjCode() {
        return getString(D_MtcobjCode);
    }

    /**
     * 设置新农+单据编号
     * 
     * @param MTC_DocNum
     */
    public void setMtcdocNum(String value) {
        set(D_MtcdocNum, value);
    }

    /**
     * 获取新农+单据编号
     * 
     * @return MTC_DocNum
     */
    public String getMtcdocNum() {
        return getString(D_MtcdocNum);
    }

    /**
     * 设置优先级 -值越小优先级越高
     * 
     * @param MTC_Priority
     */
    public void setMtcpriority(Long value) {
        set(D_Mtcpriority, value);
    }

    /**
     * 获取优先级 -值越小优先级越高
     * 
     * @return MTC_Priority
     */
    public Long getMtcpriority() {
        return getLong(D_Mtcpriority);
    }

    /**
     * 设置处理区分 (A - 新增 U - 更新 D - 取消 C - 关闭)
     * 
     * @param MTC_TransType
     */
    public void setMtctransType(String value) {
        set(D_MtctransType, value);
    }

    /**
     * 获取处理区分 (A - 新增 U - 更新 D - 取消 C - 关闭)
     * 
     * @return MTC_TransType
     */
    public String getMtctransType() {
        return getString(D_MtctransType);
    }

    /**
     * 设置原始单据编号
     * 
     * @param MTC_BaseEntry
     */
    public void setMtcbaseEntry(String value) {
        set(D_MtcbaseEntry, value);
    }

    /**
     * 获取原始单据编号
     * 
     * @return MTC_BaseEntry
     */
    public String getMtcbaseEntry() {
        return getString(D_MtcbaseEntry);
    }

    /**
     * 设置业务标识
     * 
     * @param MTC_Flag
     */
    public void setMtcflag(String value) {
        set(D_Mtcflag, value);
    }

    /**
     * 获取业务标识
     * 
     * @return MTC_Flag
     */
    public String getMtcflag() {
        return getString(D_Mtcflag);
    }

    /**
     * 设置创建日期
     * 
     * @param MTC_CreateTime
     */
    public void setMtccreateTime(Timestamp value) {
        set(D_MtccreateTime, value);
    }

    /**
     * 获取创建日期
     * 
     * @return MTC_CreateTime
     */
    public Timestamp getMtccreateTime() {
        return getTimestamp(D_MtccreateTime);
    }

    /**
     * 设置同步状态 ("N – 未同步 P – 处理中 Y – 已同步 E – 同步错误")
     * 
     * @param MTC_Status
     */
    public void setMtcstatus(String value) {
        set(D_Mtcstatus, value);
    }

    /**
     * 获取同步状态 ("N – 未同步 P – 处理中 Y – 已同步 E – 同步错误")
     * 
     * @return MTC_Status
     */
    public String getMtcstatus() {
        return getString(D_Mtcstatus);
    }

    /**
     * 设置处理日期
     * 
     * @param MTC_UpdateTime
     */
    public void setMtcupdateTime(Timestamp value) {
        set(D_MtcupdateTime, value);
    }

    /**
     * 获取处理日期
     * 
     * @return MTC_UpdateTime
     */
    public Timestamp getMtcupdateTime() {
        return getTimestamp(D_MtcupdateTime);
    }

    /**
     * 设置错误日志
     * 
     * @param MTC_ErrorMsg
     */
    public void setMtcerrorMsg(String value) {
        set(D_MtcerrorMsg, value);
    }

    /**
     * 获取错误日志
     * 
     * @return MTC_ErrorMsg
     */
    public String getMtcerrorMsg() {
        return getString(D_MtcerrorMsg);
    }

    /**
     * 设置处理次数
     * 
     * @param MTC_Times
     */
    public void setMtctimes(Long value) {
        set(D_Mtctimes, value);
    }

    /**
     * 获取处理次数
     * 
     * @return MTC_Times
     */
    public Long getMtctimes() {
        return getLong(D_Mtctimes);
    }

    /**
     * 设置JSON文件
     * 
     * @param MTC_DataFile
     */
    public void setMtcdataFile(String value) {
        set(D_MtcdataFile, value);
    }

    /**
     * 获取JSON文件
     * 
     * @return MTC_DataFile
     */
    public String getMtcdataFile() {
        return getString(D_MtcdataFile);
    }

    /**
     * 设置JSON文件长度
     * 
     * @param MTC_DataFileLen
     */
    public void setMtcdataFileLen(Long value) {
        set(D_MtcdataFileLen, value);
    }

    /**
     * 获取JSON文件长度
     * 
     * @return MTC_DataFileLen
     */
    public Long getMtcdataFileLen() {
        return getLong(D_MtcdataFileLen);
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
        propertes.add("hanaLogId");
        propertes.add("mtcId");
        propertes.add("mtcbranch");
        propertes.add("mtcdocDate");
        propertes.add("mtcobjCode");
        propertes.add("mtcdocNum");
        propertes.add("mtcpriority");
        propertes.add("mtctransType");
        propertes.add("mtcbaseEntry");
        propertes.add("mtcflag");
        propertes.add("mtccreateTime");
        propertes.add("mtcstatus");
        propertes.add("mtcupdateTime");
        propertes.add("mtcerrorMsg");
        propertes.add("mtctimes");
        propertes.add("mtcdataFile");
        propertes.add("mtcdataFileLen");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
