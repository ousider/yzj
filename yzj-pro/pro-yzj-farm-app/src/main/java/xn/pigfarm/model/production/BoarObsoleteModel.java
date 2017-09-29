package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-11-22 9:04:04
 *       表：PP_L_BILL_BOAR_OBSOLETE
 */
public class BoarObsoleteModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -6363452161338353472L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 备注
    private static final String D_Notes="notes";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 记录行号
    private static final String D_LineNumber="lineNumber";

    // 仔猪ID
    private static final String D_PigId="pigId";

    // 单据ID
    private static final String D_BillId="billId";
	 //obsoleteReason 
    private static final String D_ObsoleteReason="obsoleteReason";
	 //obsoleteDate 
    private static final String D_ObsoleteDate="obsoleteDate";

    // 负责人
    private static final String D_Worker="worker";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";

    // 审批日期
    private static final String D_AuditDate="auditDate";
	

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
	public void setRowId(Long value) {
        set(D_RowId,value);
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
     * 设置备注
     * 
     * @param NOTES
     */
	public void setNotes(String value) {
        set(D_Notes,value);
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
     * 设置表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
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
        set(D_DeletedFlag,value);
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
     * 设置对应的公司ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
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
        set(D_CompanyId,value);
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
        set(D_LineNumber,value);
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
     * 设置仔猪ID
     * 
     * @param PIG_ID
     */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
     * 获取仔猪ID
     * 
     * @return PIG_ID
     */
    public Long getPigId() {
        return getLong(D_PigId);
    }

    /**
     * 设置单据ID
     * 
     * @param BILL_ID
     */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
     * 获取单据ID
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

	/**
     * 设置obsoleteReason
     * 
     * @param OBSOLETE_REASON
     */
	public void setObsoleteReason(String value) {
        set(D_ObsoleteReason,value);
    }
	
    /**
     * 获取obsoleteReason
     * 
     * @return OBSOLETE_REASON
     */
    public String getObsoleteReason() {
        return getString(D_ObsoleteReason);
    }

	/**
     * 设置obsoleteDate
     * 
     * @param OBSOLETE_DATE
     */
	public void setObsoleteDate(Date value) {
        set(D_ObsoleteDate,value);
    }
	
    /**
     * 获取obsoleteDate
     * 
     * @return OBSOLETE_DATE
     */
    public Date getObsoleteDate() {
        return getDate(D_ObsoleteDate);
    }

    /**
     * 设置负责人
     * 
     * @param WORKER
     */
	public void setWorker(Long value) {
        set(D_Worker,value);
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
        set(D_CreateId,value);
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
        set(D_CreateDate,value);
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
     * 设置审批日期
     * 
     * @param AUDIT_DATE
     */
    public void setAuditDate(Date value) {
        set(D_AuditDate,value);
    }
	
    /**
     * 获取审批日期
     * 
     * @return AUDIT_DATE
     */
    public Date getAuditDate() {
        return getDate(D_AuditDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("notes");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("lineNumber");
        propertes.add("pigId");
        propertes.add("billId");
        propertes.add("obsoleteReason");
        propertes.add("obsoleteDate");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("auditDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




