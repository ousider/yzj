package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-9-6 11:24:54
 *       表：PP_L_BILL_SWINERY_DAYAGE_CHANGE
 */
public class SwineryDayageChangeModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -8130374993040672249L;

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

    // 记录行号
    private static final String D_LineNumber = "lineNumber";

    // 单据号
    private static final String D_BillId = "billId";

    // 猪群ID
    private static final String D_SwineryId = "swineryId";

    // 转出猪群ID
    private static final String D_OldSwineryId = "oldSwineryId";

    // 猪只ID
    private static final String D_PigId = "pigId";

    // (转出)批次日龄固定值
    private static final String D_OldSwineryDayage = "oldSwineryDayage";

    // (转出)开始操作日期
    private static final String D_OldStartOperateDate = "oldStartOperateDate";

    // (转出)上一操作日期
    private static final String D_OldLastOperateDate = "oldLastOperateDate";

    // (转入)批次日龄固定值
    private static final String D_NewSwineryDayage = "newSwineryDayage";

    // (转入)开始操作日期
    private static final String D_NewStartOperateDate = "newStartOperateDate";

    // (转入)上一操作日期
    private static final String D_NewLastOperateDate = "newLastOperateDate";

    // 转入猪群现有肉猪数量
    private static final String D_PorkPigNum = "porkPigNum";

    // 转入或分娩数量肉猪数量
    private static final String D_ChangePigNum = "changePigNum";

    // 转出头数
    private static final String D_RolloutPigNum = "rolloutPigNum";

    // 转舍类型，1：转入，2：转出
    private static final String D_ChangeType = "changeType";

    // 事件日期
    private static final String D_EventDate = "eventDate";

    // 负责人
    private static final String D_Worker = "worker";

    // 创建人
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
     * 设置记录行号
     * 
     * @param LINE_NUMBER
     */
    public void setLineNumber(Long value) {
        set(D_LineNumber, value);
    }

    /**
     * 获取记录行号
     * 
     * @return LINE_NUMBER
     */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

    /**
     * 设置单据号
     * 
     * @param BILL_ID
     */
    public void setBillId(Long value) {
        set(D_BillId, value);
    }

    /**
     * 获取单据号
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

    /**
     * 设置猪群ID
     * 
     * @param SWINERY_ID
     */
    public void setSwineryId(Long value) {
        set(D_SwineryId, value);
    }

    /**
     * 获取猪群ID
     * 
     * @return SWINERY_ID
     */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

    /**
     * 设置转出猪群ID
     * 
     * @param OLD_SWINERY_ID
     */
    public void setOldSwineryId(Long value) {
        set(D_OldSwineryId, value);
    }

    /**
     * 获取转出猪群ID
     * 
     * @return OLD_SWINERY_ID
     */
    public Long getOldSwineryId() {
        return getLong(D_OldSwineryId);
    }

    /**
     * 设置猪只ID
     * 
     * @param PIG_ID
     */
    public void setPigId(Long value) {
        set(D_PigId, value);
    }

    /**
     * 获取猪只ID
     * 
     * @return PIG_ID
     */
    public Long getPigId() {
        return getLong(D_PigId);
    }

    /**
     * 设置(转出)批次日龄固定值
     * 
     * @param OLD_SWINERY_DAYAGE
     */
    public void setOldSwineryDayage(Double value) {
        set(D_OldSwineryDayage, value);
    }

    /**
     * 获取(转出)批次日龄固定值
     * 
     * @return OLD_SWINERY_DAYAGE
     */
    public Double getOldSwineryDayage() {
        return getDouble(D_OldSwineryDayage);
    }

    /**
     * 设置(转出)开始操作日期
     * 
     * @param OLD_START_OPERATE_DATE
     */
    public void setOldStartOperateDate(Date value) {
        set(D_OldStartOperateDate, value);
    }

    /**
     * 获取(转出)开始操作日期
     * 
     * @return OLD_START_OPERATE_DATE
     */
    public Date getOldStartOperateDate() {
        return getDate(D_OldStartOperateDate);
    }

    /**
     * 设置(转出)上一操作日期
     * 
     * @param OLD_LAST_OPERATE_DATE
     */
    public void setOldLastOperateDate(Date value) {
        set(D_OldLastOperateDate, value);
    }

    /**
     * 获取(转出)上一操作日期
     * 
     * @return OLD_LAST_OPERATE_DATE
     */
    public Date getOldLastOperateDate() {
        return getDate(D_OldLastOperateDate);
    }

    /**
     * 设置(转入)批次日龄固定值
     * 
     * @param NEW_SWINERY_DAYAGE
     */
    public void setNewSwineryDayage(Double value) {
        set(D_NewSwineryDayage, value);
    }

    /**
     * 获取(转入)批次日龄固定值
     * 
     * @return NEW_SWINERY_DAYAGE
     */
    public Double getNewSwineryDayage() {
        return getDouble(D_NewSwineryDayage);
    }

    /**
     * 设置(转入)开始操作日期
     * 
     * @param NEW_START_OPERATE_DATE
     */
    public void setNewStartOperateDate(Date value) {
        set(D_NewStartOperateDate, value);
    }

    /**
     * 获取(转入)开始操作日期
     * 
     * @return NEW_START_OPERATE_DATE
     */
    public Date getNewStartOperateDate() {
        return getDate(D_NewStartOperateDate);
    }

    /**
     * 设置(转入)上一操作日期
     * 
     * @param NEW_LAST_OPERATE_DATE
     */
    public void setNewLastOperateDate(Date value) {
        set(D_NewLastOperateDate, value);
    }

    /**
     * 获取(转入)上一操作日期
     * 
     * @return NEW_LAST_OPERATE_DATE
     */
    public Date getNewLastOperateDate() {
        return getDate(D_NewLastOperateDate);
    }

    /**
     * 设置转入猪群现有肉猪数量
     * 
     * @param PORK_PIG_NUM
     */
    public void setPorkPigNum(Long value) {
        set(D_PorkPigNum, value);
    }

    /**
     * 获取转入猪群现有肉猪数量
     * 
     * @return PORK_PIG_NUM
     */
    public Long getPorkPigNum() {
        return getLong(D_PorkPigNum);
    }

    /**
     * 设置转入或分娩数量肉猪数量
     * 
     * @param CHANGE_PIG_NUM
     */
    public void setChangePigNum(Long value) {
        set(D_ChangePigNum, value);
    }

    /**
     * 获取转入或分娩数量肉猪数量
     * 
     * @return CHANGE_PIG_NUM
     */
    public Long getChangePigNum() {
        return getLong(D_ChangePigNum);
    }

    /**
     * 设置转出头数
     * 
     * @param ROLLOUT_PIG_NUM
     */
    public void setRolloutPigNum(Long value) {
        set(D_RolloutPigNum, value);
    }

    /**
     * 获取转出头数
     * 
     * @return ROLLOUT_PIG_NUM
     */
    public Long getRolloutPigNum() {
        return getLong(D_RolloutPigNum);
    }

    /**
     * 设置转舍类型，1：转入，2：转出
     * 
     * @param CHANGE_TYPE
     */
    public void setChangeType(Long value) {
        set(D_ChangeType, value);
    }

    /**
     * 获取转舍类型，1：转入，2：转出
     * 
     * @return CHANGE_TYPE
     */
    public Long getChangeType() {
        return getLong(D_ChangeType);
    }

    /**
     * 设置事件日期
     * 
     * @param EVENT_DATE
     */
    public void setEventDate(Date value) {
        set(D_EventDate, value);
    }

    /**
     * 获取事件日期
     * 
     * @return EVENT_DATE
     */
    public Date getEventDate() {
        return getDate(D_EventDate);
    }

    /**
     * 设置负责人
     * 
     * @param WORKER
     */
    public void setWorker(Long value) {
        set(D_Worker, value);
    }

    /**
     * 获取负责人
     * 
     * @return WORKER
     */
    public Long getWorker() {
        return getLong(D_Worker);
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
        propertes.add("lineNumber");
        propertes.add("billId");
        propertes.add("swineryId");
        propertes.add("oldSwineryId");
        propertes.add("pigId");
        propertes.add("oldSwineryDayage");
        propertes.add("oldStartOperateDate");
        propertes.add("oldLastOperateDate");
        propertes.add("newSwineryDayage");
        propertes.add("newStartOperateDate");
        propertes.add("newLastOperateDate");
        propertes.add("porkPigNum");
        propertes.add("changePigNum");
        propertes.add("rolloutPigNum");
        propertes.add("changeType");
        propertes.add("eventDate");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
