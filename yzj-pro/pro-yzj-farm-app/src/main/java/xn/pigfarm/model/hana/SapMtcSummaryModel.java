package xn.pigfarm.model.hana;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-24 17:00:01
 *       表：SAP_MTC_SUMMARY
 */
public class SapMtcSummaryModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -882934096914207508L;

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

    // 创建时间
    private static final String D_CreateTime = "createTime";

    // 行号
    private static final String D_LineNumber = "lineNumber";

    // 项目
    private static final String D_Type = "type";

    // 期初
    private static final String D_BegQty = "begQty";

    // 期初金额
    private static final String D_BegMoney = "begMoney";

    // 本月增加数量
    private static final String D_AddQty = "addQty";

    // 增加金额
    private static final String D_AddMoney = "addMoney";

    // 本月减少数量
    private static final String D_ReduceQty = "reduceQty";

    // 减少金额
    private static final String D_ReduceMoney = "reduceMoney";

    // 期末数量
    private static final String D_EndQty = "endQty";

    // 期末金额
    private static final String D_EndMoney = "endMoney";

    // 其他
    private static final String D_Other = "other";

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
     * 设置行号
     * 
     * @param LINE_NUMBER
     */
    public void setLineNumber(Long value) {
        set(D_LineNumber, value);
    }

    /**
     * 获取行号
     * 
     * @return LINE_NUMBER
     */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

    /**
     * 设置项目
     * 
     * @param TYPE
     */
    public void setType(String value) {
        set(D_Type, value);
    }

    /**
     * 获取项目
     * 
     * @return TYPE
     */
    public String getType() {
        return getString(D_Type);
    }

    /**
     * 设置期初
     * 
     * @param BEG_QTY
     */
    public void setBegQty(Long value) {
        set(D_BegQty, value);
    }

    /**
     * 获取期初
     * 
     * @return BEG_QTY
     */
    public Long getBegQty() {
        return getLong(D_BegQty);
    }

    /**
     * 设置期初金额
     * 
     * @param BEG_MONEY
     */
    public void setBegMoney(Long value) {
        set(D_BegMoney, value);
    }

    /**
     * 获取期初金额
     * 
     * @return BEG_MONEY
     */
    public Long getBegMoney() {
        return getLong(D_BegMoney);
    }

    /**
     * 设置本月增加数量
     * 
     * @param ADD_QTY
     */
    public void setAddQty(Long value) {
        set(D_AddQty, value);
    }

    /**
     * 获取本月增加数量
     * 
     * @return ADD_QTY
     */
    public Long getAddQty() {
        return getLong(D_AddQty);
    }

    /**
     * 设置增加金额
     * 
     * @param ADD_MONEY
     */
    public void setAddMoney(Long value) {
        set(D_AddMoney, value);
    }

    /**
     * 获取增加金额
     * 
     * @return ADD_MONEY
     */
    public Long getAddMoney() {
        return getLong(D_AddMoney);
    }

    /**
     * 设置本月减少数量
     * 
     * @param REDUCE_QTY
     */
    public void setReduceQty(Long value) {
        set(D_ReduceQty, value);
    }

    /**
     * 获取本月减少数量
     * 
     * @return REDUCE_QTY
     */
    public Long getReduceQty() {
        return getLong(D_ReduceQty);
    }

    /**
     * 设置减少金额
     * 
     * @param REDUCE_MONEY
     */
    public void setReduceMoney(Long value) {
        set(D_ReduceMoney, value);
    }

    /**
     * 获取减少金额
     * 
     * @return REDUCE_MONEY
     */
    public Long getReduceMoney() {
        return getLong(D_ReduceMoney);
    }

    /**
     * 设置期末数量
     * 
     * @param END_QTY
     */
    public void setEndQty(Long value) {
        set(D_EndQty, value);
    }

    /**
     * 获取期末数量
     * 
     * @return END_QTY
     */
    public Long getEndQty() {
        return getLong(D_EndQty);
    }

    /**
     * 设置期末金额
     * 
     * @param END_MONEY
     */
    public void setEndMoney(Long value) {
        set(D_EndMoney, value);
    }

    /**
     * 获取期末金额
     * 
     * @return END_MONEY
     */
    public Long getEndMoney() {
        return getLong(D_EndMoney);
    }

    /**
     * 设置其他
     * 
     * @param OTHER
     */
    public void setOther(String value) {
        set(D_Other, value);
    }

    /**
     * 获取其他
     * 
     * @return OTHER
     */
    public String getOther() {
        return getString(D_Other);
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
        propertes.add("createTime");
        propertes.add("lineNumber");
        propertes.add("type");
        propertes.add("begQty");
        propertes.add("begMoney");
        propertes.add("addQty");
        propertes.add("addMoney");
        propertes.add("reduceQty");
        propertes.add("reduceMoney");
        propertes.add("endQty");
        propertes.add("endMoney");
        propertes.add("other");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
