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
 * @date :2017-6-28 10:40:43
 *       表：SAP_MTC_PGTSDTL
 */
public class SapMtcPgtsdtlModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -862255498713499382L;

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

    // 关联猪场Id
    private static final String D_Farmidrela = "farmidrela";

    // 关联猪舍id
    private static final String D_Houseidrela = "houseidrela";

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

    // 猪只类型 (S : 商品猪, H : 后备猪)
    private static final String D_MtcpigType = "mtcpigType";

    // 期间
    private static final String D_Mtcperiod = "mtcperiod";

    // 日期
    private static final String D_MtcdocDate = "mtcdocDate";

    // 业务类型 ("3010 -后备入场 3020 -后备转生产 3030 -分娩记录 3040 -转群记录 -常规 3050 -转群记录 -内转(包括商品猪转后备) 3060 -死亡记录 3070 -自宰 3080 -内部销售 3090 -常规销售 3100 -存栏调整" )
    private static final String D_MtctransType = "mtctransType";

    // 关联猪场
    private static final String D_MtcrelaDeptId = "mtcrelaDeptId";

    // 关联猪舍
    private static final String D_MtcrelaUnit = "mtcrelaUnit";

    // 调入数量
    private static final String D_MtcinQty = "mtcinQty";

    // 调入重量
    private static final String D_MtcinWeight = "mtcinWeight";

    // 调出数量
    private static final String D_MtcoutQty = "mtcoutQty";

    // 调出重量
    private static final String D_MtcoutWeight = "mtcoutWeight";

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
     * 设置关联猪场Id
     * 
     * @param FARM_ID_Rela
     */
    public void setFarmidrela(Long value) {
        set(D_Farmidrela, value);
    }

    /**
     * 获取关联猪场Id
     * 
     * @return FARM_ID_Rela
     */
    public Long getFarmidrela() {
        return getLong(D_Farmidrela);
    }

    /**
     * 设置关联猪舍id
     * 
     * @param HOUSE_ID_Rela
     */
    public void setHouseidrela(Long value) {
        set(D_Houseidrela, value);
    }

    /**
     * 获取关联猪舍id
     * 
     * @return HOUSE_ID_Rela
     */
    public Long getHouseidrela() {
        return getLong(D_Houseidrela);
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
     * 设置猪只类型 (S : 商品猪, H : 后备猪)
     * 
     * @param MTC_PigType
     */
    public void setMtcpigType(String value) {
        set(D_MtcpigType, value);
    }

    /**
     * 获取猪只类型 (S : 商品猪, H : 后备猪)
     * 
     * @return MTC_PigType
     */
    public String getMtcpigType() {
        return getString(D_MtcpigType);
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
     * 设置日期
     * 
     * @param MTC_DocDate
     */
    public void setMtcdocDate(Date value) {
        set(D_MtcdocDate, value);
    }

    /**
     * 获取日期
     * 
     * @return MTC_DocDate
     */
    public Date getMtcdocDate() {
        return getDate(D_MtcdocDate);
    }

    /**
     * 设置业务类型 ("3010 -后备入场 3020 -后备转生产 3030 -分娩记录 3040 -转群记录 -常规 3050 -转群记录 -内转(包括商品猪转后备) 3060 -死亡记录 3070 -自宰 3080 -内部销售 3090 -常规销售 3100 -存栏调整" )
     * 
     * @param MTC_TransType
     */
    public void setMtctransType(String value) {
        set(D_MtctransType, value);
    }

    /**
     * 获取业务类型 ("3010 -后备入场 3020 -后备转生产 3030 -分娩记录 3040 -转群记录 -常规 3050 -转群记录 -内转(包括商品猪转后备) 3060 -死亡记录 3070 -自宰 3080 -内部销售 3090 -常规销售 3100 -存栏调整" )
     * 
     * @return MTC_TransType
     */
    public String getMtctransType() {
        return getString(D_MtctransType);
    }

    /**
     * 设置关联猪场
     * 
     * @param MTC_RelaDeptID
     */
    public void setMtcrelaDeptId(String value) {
        set(D_MtcrelaDeptId, value);
    }

    /**
     * 获取关联猪场
     * 
     * @return MTC_RelaDeptID
     */
    public String getMtcrelaDeptId() {
        return getString(D_MtcrelaDeptId);
    }

    /**
     * 设置关联猪舍
     * 
     * @param MTC_RelaUnit
     */
    public void setMtcrelaUnit(String value) {
        set(D_MtcrelaUnit, value);
    }

    /**
     * 获取关联猪舍
     * 
     * @return MTC_RelaUnit
     */
    public String getMtcrelaUnit() {
        return getString(D_MtcrelaUnit);
    }

    /**
     * 设置调入数量
     * 
     * @param MTC_InQty
     */
    public void setMtcinQty(String value) {
        set(D_MtcinQty, value);
    }

    /**
     * 获取调入数量
     * 
     * @return MTC_InQty
     */
    public String getMtcinQty() {
        return getString(D_MtcinQty);
    }

    /**
     * 设置调入重量
     * 
     * @param MTC_InWeight
     */
    public void setMtcinWeight(String value) {
        set(D_MtcinWeight, value);
    }

    /**
     * 获取调入重量
     * 
     * @return MTC_InWeight
     */
    public String getMtcinWeight() {
        return getString(D_MtcinWeight);
    }

    /**
     * 设置调出数量
     * 
     * @param MTC_OutQty
     */
    public void setMtcoutQty(String value) {
        set(D_MtcoutQty, value);
    }

    /**
     * 获取调出数量
     * 
     * @return MTC_OutQty
     */
    public String getMtcoutQty() {
        return getString(D_MtcoutQty);
    }

    /**
     * 设置调出重量
     * 
     * @param MTC_OutWeight
     */
    public void setMtcoutWeight(String value) {
        set(D_MtcoutWeight, value);
    }

    /**
     * 获取调出重量
     * 
     * @return MTC_OutWeight
     */
    public String getMtcoutWeight() {
        return getString(D_MtcoutWeight);
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
        propertes.add("farmidrela");
        propertes.add("houseidrela");
        propertes.add("hanaLogId");
        propertes.add("itfcId");
        propertes.add("createTime");
        propertes.add("mtcbranchId");
        propertes.add("mtcdeptId");
        propertes.add("mtcunit");
        propertes.add("mtcpigType");
        propertes.add("mtcperiod");
        propertes.add("mtcdocDate");
        propertes.add("mtctransType");
        propertes.add("mtcrelaDeptId");
        propertes.add("mtcrelaUnit");
        propertes.add("mtcinQty");
        propertes.add("mtcinWeight");
        propertes.add("mtcoutQty");
        propertes.add("mtcoutWeight");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
