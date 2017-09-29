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
 * @date :2017-8-3 16:55:46
 *       表：SAP_MTC_PGONHD
 */
public class SapMtcPgonhdModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 8382198612827513223L;

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

    // 猪只类型 (S : 商品猪, H : 后备猪)
    private static final String D_MtcpigType = "mtcpigType";

    // 期间
    private static final String D_Mtcperiod = "mtcperiod";

    // 日期
    private static final String D_MtcdocDate = "mtcdocDate";

    // 期初存栏
    private static final String D_MtcbegQty = "mtcbegQty";

    // 初生仔猪
    private static final String D_MtcinQty = "mtcinQty";

    // 新购(后备)入场
    private static final String D_MtcpurQty = "mtcpurQty";

    // 转群转入 - 内转
    private static final String D_MtcinQtyInner = "mtcinQtyInner";

    // 转群转入 - 常规
    private static final String D_MtcinQtyNormal = "mtcinQtyNormal";

    // 转群转出 - 内转
    private static final String D_MtcoutQtyInner = "mtcoutQtyInner";

    // 转群转出 - 常规
    private static final String D_MtcoutQtyNormal = "mtcoutQtyNormal";

    // 转出到产房
    private static final String D_MtctransToCf = "mtctransToCf";

    // 转出到保育
    private static final String D_MtctransToBy = "mtctransToBy";

    // 转出到培育
    private static final String D_MtctransToPy = "mtctransToPy";

    // 转出到后备
    private static final String D_MtctransToHb = "mtctransToHb";

    // 转出到育肥
    private static final String D_MtctransToYf = "mtctransToYf";

    // 死亡
    private static final String D_MtcdieQty = "mtcdieQty";

    // 自宰
    private static final String D_MtckillQty = "mtckillQty";

    // 后备转生产
    private static final String D_MtctransToSc = "mtctransToSc";

    // 内部销售
    private static final String D_MtcsalesQtyInner = "mtcsalesQtyInner";

    // 外部销售
    private static final String D_MtcsalesQtyNormar = "mtcsalesQtyNormar";

    // 存栏调整
    private static final String D_MtcadjQty = "mtcadjQty";

    // 期末存栏
    private static final String D_MtcendQty = "mtcendQty";

    // 日龄
    private static final String D_MtcdaySum = "mtcdaySum";

    // 估重
    private static final String D_Mtcweight = "mtcweight";

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
     * 设置期初存栏
     * 
     * @param MTC_BegQty
     */
    public void setMtcbegQty(String value) {
        set(D_MtcbegQty, value);
    }

    /**
     * 获取期初存栏
     * 
     * @return MTC_BegQty
     */
    public String getMtcbegQty() {
        return getString(D_MtcbegQty);
    }

    /**
     * 设置初生仔猪
     * 
     * @param MTC_InQty
     */
    public void setMtcinQty(String value) {
        set(D_MtcinQty, value);
    }

    /**
     * 获取初生仔猪
     * 
     * @return MTC_InQty
     */
    public String getMtcinQty() {
        return getString(D_MtcinQty);
    }

    /**
     * 设置新购(后备)入场
     * 
     * @param MTC_PurQty
     */
    public void setMtcpurQty(String value) {
        set(D_MtcpurQty, value);
    }

    /**
     * 获取新购(后备)入场
     * 
     * @return MTC_PurQty
     */
    public String getMtcpurQty() {
        return getString(D_MtcpurQty);
    }

    /**
     * 设置转群转入 - 内转
     * 
     * @param MTC_InQty_Inner
     */
    public void setMtcinQtyInner(String value) {
        set(D_MtcinQtyInner, value);
    }

    /**
     * 获取转群转入 - 内转
     * 
     * @return MTC_InQty_Inner
     */
    public String getMtcinQtyInner() {
        return getString(D_MtcinQtyInner);
    }

    /**
     * 设置转群转入 - 常规
     * 
     * @param MTC_InQty_Normal
     */
    public void setMtcinQtyNormal(String value) {
        set(D_MtcinQtyNormal, value);
    }

    /**
     * 获取转群转入 - 常规
     * 
     * @return MTC_InQty_Normal
     */
    public String getMtcinQtyNormal() {
        return getString(D_MtcinQtyNormal);
    }

    /**
     * 设置转群转出 - 内转
     * 
     * @param MTC_OutQty_Inner
     */
    public void setMtcoutQtyInner(String value) {
        set(D_MtcoutQtyInner, value);
    }

    /**
     * 获取转群转出 - 内转
     * 
     * @return MTC_OutQty_Inner
     */
    public String getMtcoutQtyInner() {
        return getString(D_MtcoutQtyInner);
    }

    /**
     * 设置转群转出 - 常规
     * 
     * @param MTC_OutQty_Normal
     */
    public void setMtcoutQtyNormal(String value) {
        set(D_MtcoutQtyNormal, value);
    }

    /**
     * 获取转群转出 - 常规
     * 
     * @return MTC_OutQty_Normal
     */
    public String getMtcoutQtyNormal() {
        return getString(D_MtcoutQtyNormal);
    }

    /**
     * 设置转出到产房
     * 
     * @param MTC_TransToCF
     */
    public void setMtctransToCf(String value) {
        set(D_MtctransToCf, value);
    }

    /**
     * 获取转出到产房
     * 
     * @return MTC_TransToCF
     */
    public String getMtctransToCf() {
        return getString(D_MtctransToCf);
    }

    /**
     * 设置转出到保育
     * 
     * @param MTC_TransToBY
     */
    public void setMtctransToBy(String value) {
        set(D_MtctransToBy, value);
    }

    /**
     * 获取转出到保育
     * 
     * @return MTC_TransToBY
     */
    public String getMtctransToBy() {
        return getString(D_MtctransToBy);
    }

    /**
     * 设置转出到培育
     * 
     * @param MTC_TransToPY
     */
    public void setMtctransToPy(String value) {
        set(D_MtctransToPy, value);
    }

    /**
     * 获取转出到培育
     * 
     * @return MTC_TransToPY
     */
    public String getMtctransToPy() {
        return getString(D_MtctransToPy);
    }

    /**
     * 设置转出到后备
     * 
     * @param MTC_TransToHB
     */
    public void setMtctransToHb(String value) {
        set(D_MtctransToHb, value);
    }

    /**
     * 获取转出到后备
     * 
     * @return MTC_TransToHB
     */
    public String getMtctransToHb() {
        return getString(D_MtctransToHb);
    }

    /**
     * 设置转出到育肥
     * 
     * @param MTC_TransToYF
     */
    public void setMtctransToYf(String value) {
        set(D_MtctransToYf, value);
    }

    /**
     * 获取转出到育肥
     * 
     * @return MTC_TransToYF
     */
    public String getMtctransToYf() {
        return getString(D_MtctransToYf);
    }

    /**
     * 设置死亡
     * 
     * @param MTC_DieQty
     */
    public void setMtcdieQty(String value) {
        set(D_MtcdieQty, value);
    }

    /**
     * 获取死亡
     * 
     * @return MTC_DieQty
     */
    public String getMtcdieQty() {
        return getString(D_MtcdieQty);
    }

    /**
     * 设置自宰
     * 
     * @param MTC_KillQty
     */
    public void setMtckillQty(String value) {
        set(D_MtckillQty, value);
    }

    /**
     * 获取自宰
     * 
     * @return MTC_KillQty
     */
    public String getMtckillQty() {
        return getString(D_MtckillQty);
    }

    /**
     * 设置后备转生产
     * 
     * @param MTC_TransToSC
     */
    public void setMtctransToSc(String value) {
        set(D_MtctransToSc, value);
    }

    /**
     * 获取后备转生产
     * 
     * @return MTC_TransToSC
     */
    public String getMtctransToSc() {
        return getString(D_MtctransToSc);
    }

    /**
     * 设置内部销售
     * 
     * @param MTC_SalesQty_Inner
     */
    public void setMtcsalesQtyInner(String value) {
        set(D_MtcsalesQtyInner, value);
    }

    /**
     * 获取内部销售
     * 
     * @return MTC_SalesQty_Inner
     */
    public String getMtcsalesQtyInner() {
        return getString(D_MtcsalesQtyInner);
    }

    /**
     * 设置外部销售
     * 
     * @param MTC_SalesQty_Normar
     */
    public void setMtcsalesQtyNormar(String value) {
        set(D_MtcsalesQtyNormar, value);
    }

    /**
     * 获取外部销售
     * 
     * @return MTC_SalesQty_Normar
     */
    public String getMtcsalesQtyNormar() {
        return getString(D_MtcsalesQtyNormar);
    }

    /**
     * 设置存栏调整
     * 
     * @param MTC_AdjQty
     */
    public void setMtcadjQty(String value) {
        set(D_MtcadjQty, value);
    }

    /**
     * 获取存栏调整
     * 
     * @return MTC_AdjQty
     */
    public String getMtcadjQty() {
        return getString(D_MtcadjQty);
    }

    /**
     * 设置期末存栏
     * 
     * @param MTC_EndQty
     */
    public void setMtcendQty(String value) {
        set(D_MtcendQty, value);
    }

    /**
     * 获取期末存栏
     * 
     * @return MTC_EndQty
     */
    public String getMtcendQty() {
        return getString(D_MtcendQty);
    }

    /**
     * 设置日龄
     * 
     * @param MTC_DaySum
     */
    public void setMtcdaySum(String value) {
        set(D_MtcdaySum, value);
    }

    /**
     * 获取日龄
     * 
     * @return MTC_DaySum
     */
    public String getMtcdaySum() {
        return getString(D_MtcdaySum);
    }

    /**
     * 设置估重
     * 
     * @param MTC_Weight
     */
    public void setMtcweight(String value) {
        set(D_Mtcweight, value);
    }

    /**
     * 获取估重
     * 
     * @return MTC_Weight
     */
    public String getMtcweight() {
        return getString(D_Mtcweight);
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
        propertes.add("mtcpigType");
        propertes.add("mtcperiod");
        propertes.add("mtcdocDate");
        propertes.add("mtcbegQty");
        propertes.add("mtcinQty");
        propertes.add("mtcpurQty");
        propertes.add("mtcinQtyInner");
        propertes.add("mtcinQtyNormal");
        propertes.add("mtcoutQtyInner");
        propertes.add("mtcoutQtyNormal");
        propertes.add("mtctransToCf");
        propertes.add("mtctransToBy");
        propertes.add("mtctransToPy");
        propertes.add("mtctransToHb");
        propertes.add("mtctransToYf");
        propertes.add("mtcdieQty");
        propertes.add("mtckillQty");
        propertes.add("mtctransToSc");
        propertes.add("mtcsalesQtyInner");
        propertes.add("mtcsalesQtyNormar");
        propertes.add("mtcadjQty");
        propertes.add("mtcendQty");
        propertes.add("mtcdaySum");
        propertes.add("mtcweight");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
