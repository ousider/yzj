package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-9-27 17:07:25
 *       表：CD_M_PIG_OBSOLETE
 */
public class PigObsoleteModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 6658716122515572682L;

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

    // 备注
    private static final String D_Notes = "notes";

    // obsoleteCode
    private static final String D_ObsoleteCode = "obsoleteCode";

    // obsoleteStandard
    private static final String D_ObsoleteStandard = "obsoleteStandard";

    // param1
    private static final String D_Param1 = "param1";

    // param2
    private static final String D_Param2 = "param2";

    // param3
    private static final String D_Param3 = "param3";

    // 创建人
    private static final String D_CreateId = "createId";

    // 创建日期
    private static final String D_CreateDate = "createDate";

    // 猪场ID
    private static final String D_FarmId = "farmId";

    // 公司ID
    private static final String D_CompanyId = "companyId";

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
     * 设置obsoleteCode
     * 
     * @param OBSOLETE_CODE
     */
    public void setObsoleteCode(String value) {
        set(D_ObsoleteCode, value);
    }

    /**
     * 获取obsoleteCode
     * 
     * @return OBSOLETE_CODE
     */
    public String getObsoleteCode() {
        return getString(D_ObsoleteCode);
    }

    /**
     * 设置obsoleteStandard
     * 
     * @param OBSOLETE_STANDARD
     */
    public void setObsoleteStandard(String value) {
        set(D_ObsoleteStandard, value);
    }

    /**
     * 获取obsoleteStandard
     * 
     * @return OBSOLETE_STANDARD
     */
    public String getObsoleteStandard() {
        return getString(D_ObsoleteStandard);
    }

    /**
     * 设置param1
     * 
     * @param PARAM1
     */
    public void setParam1(String value) {
        set(D_Param1, value);
    }

    /**
     * 获取param1
     * 
     * @return PARAM1
     */
    public String getParam1() {
        return getString(D_Param1);
    }

    /**
     * 设置param2
     * 
     * @param PARAM2
     */
    public void setParam2(String value) {
        set(D_Param2, value);
    }

    /**
     * 获取param2
     * 
     * @return PARAM2
     */
    public String getParam2() {
        return getString(D_Param2);
    }

    /**
     * 设置param3
     * 
     * @param PARAM3
     */
    public void setParam3(String value) {
        set(D_Param3, value);
    }

    /**
     * 获取param3
     * 
     * @return PARAM3
     */
    public String getParam3() {
        return getString(D_Param3);
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

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("obsoleteCode");
        propertes.add("obsoleteStandard");
        propertes.add("param1");
        propertes.add("param2");
        propertes.add("param3");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("farmId");
        propertes.add("companyId");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
