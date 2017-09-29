package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-20 13:37:14
 *       表：SC_M_BILL_REQUIRE 
 */
public class RequireModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 8929705677912548896L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[1]-待审批[2]-审批未通过[3]-已审批[4]-部分完成订单[5]-完成订单[6]-报废 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //单据编号 
    private static final String D_BillCode="billCode";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //单据事件(单据类型) 
    private static final String D_EventCode="eventCode";
	 //申请人 
    private static final String D_ApplyId="applyId";
	 //申请单位ID 
    private static final String D_ApplyUnitId="applyUnitId";
	 //核算单位ID 
    private static final String D_AccountsUnitId="accountsUnitId";
	 //申请类型：参考物料大类 
    private static final String D_ApplyType="applyType";
	 //[1]-普通[2]-紧急 
    private static final String D_EmergencyType="emergencyType";
	 //复制新增单据号ID 
    private static final String D_CopyId="copyId";
	 //需求单类型[1]-猪场,[2]-添加药,[3]-实验室 
    private static final String D_RequireType="requireType";
	 //创建者 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //申请人OA账号 
    private static final String D_OaUsername="oaUsername";
	 //审核未通过原因 
    private static final String D_ReviewFailReason="reviewFailReason";
	 //OA审核用01 
    private static final String D_FtriggerFlag="ftriggerFlag";
	 //OA审核用02 
    private static final String D_Requestid="requestid";
	

   /**
    * 设置行号: 系统保留字段，标识一条数据记录。
    * @param ROW_ID
    */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
    * 获取行号: 系统保留字段，标识一条数据记录。
    * @return ROW_ID
    */
    public Long getRowId() {
        return getLong(D_RowId);
    }

	/**
    * 设置[1]-待审批[2]-审批未通过[3]-已审批[4]-部分完成订单[5]-完成订单[6]-报废
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]-待审批[2]-审批未通过[3]-已审批[4]-部分完成订单[5]-完成订单[6]-报废
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
    * 设置[0]-未删除;[1]-逻辑删除
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取[0]-未删除;[1]-逻辑删除
    * @return DELETED_FLAG
    */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
    * 设置备注
    * @param NOTES
    */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
   /**
    * 获取备注
    * @return NOTES
    */
    public String getNotes() {
        return getString(D_Notes);
    }

   /**
    * 设置对应的公司ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取对应的公司ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置companyId
    * @param COMPANY_ID
    */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
    * 获取companyId
    * @return COMPANY_ID
    */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

	/**
    * 设置单据编号
    * @param BILL_CODE
    */
	public void setBillCode(String value) {
        set(D_BillCode,value);
    }
	
   /**
    * 获取单据编号
    * @return BILL_CODE
    */
    public String getBillCode() {
        return getString(D_BillCode);
    }

	/**
    * 设置单据日期
    * @param BILL_DATE
    */
	public void setBillDate(Date value) {
        set(D_BillDate,value);
    }
	
   /**
    * 获取单据日期
    * @return BILL_DATE
    */
    public Date getBillDate() {
        return getDate(D_BillDate);
    }

	/**
    * 设置单据事件(单据类型)
    * @param EVENT_CODE
    */
	public void setEventCode(String value) {
        set(D_EventCode,value);
    }
	
   /**
    * 获取单据事件(单据类型)
    * @return EVENT_CODE
    */
    public String getEventCode() {
        return getString(D_EventCode);
    }

   /**
    * 设置申请人
    * @param APPLY_ID
    */
	public void setApplyId(Long value) {
        set(D_ApplyId,value);
    }
	
	/**
    * 获取申请人
    * @return APPLY_ID
    */
    public Long getApplyId() {
        return getLong(D_ApplyId);
    }

   /**
    * 设置申请单位ID
    * @param APPLY_UNIT_ID
    */
	public void setApplyUnitId(Long value) {
        set(D_ApplyUnitId,value);
    }
	
	/**
    * 获取申请单位ID
    * @return APPLY_UNIT_ID
    */
    public Long getApplyUnitId() {
        return getLong(D_ApplyUnitId);
    }

   /**
    * 设置核算单位ID
    * @param ACCOUNTS_UNIT_ID
    */
	public void setAccountsUnitId(Long value) {
        set(D_AccountsUnitId,value);
    }
	
	/**
    * 获取核算单位ID
    * @return ACCOUNTS_UNIT_ID
    */
    public Long getAccountsUnitId() {
        return getLong(D_AccountsUnitId);
    }

	/**
    * 设置申请类型：参考物料大类
    * @param APPLY_TYPE
    */
	public void setApplyType(String value) {
        set(D_ApplyType,value);
    }
	
   /**
    * 获取申请类型：参考物料大类
    * @return APPLY_TYPE
    */
    public String getApplyType() {
        return getString(D_ApplyType);
    }

	/**
    * 设置[1]-普通[2]-紧急
    * @param EMERGENCY_TYPE
    */
	public void setEmergencyType(String value) {
        set(D_EmergencyType,value);
    }
	
   /**
    * 获取[1]-普通[2]-紧急
    * @return EMERGENCY_TYPE
    */
    public String getEmergencyType() {
        return getString(D_EmergencyType);
    }

   /**
    * 设置复制新增单据号ID
    * @param COPY_ID
    */
	public void setCopyId(Long value) {
        set(D_CopyId,value);
    }
	
	/**
    * 获取复制新增单据号ID
    * @return COPY_ID
    */
    public Long getCopyId() {
        return getLong(D_CopyId);
    }

	/**
    * 设置需求单类型[1]-猪场,[2]-添加药,[3]-实验室
    * @param REQUIRE_TYPE
    */
	public void setRequireType(String value) {
        set(D_RequireType,value);
    }
	
   /**
    * 获取需求单类型[1]-猪场,[2]-添加药,[3]-实验室
    * @return REQUIRE_TYPE
    */
    public String getRequireType() {
        return getString(D_RequireType);
    }

   /**
    * 设置创建者
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建者
    * @return CREATE_ID
    */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
    * 设置创建日期
    * @param CREATE_DATE
    */
    public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建日期
    * @return CREATE_DATE
    */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }

	/**
    * 设置申请人OA账号
    * @param OA_USERNAME
    */
	public void setOaUsername(String value) {
        set(D_OaUsername,value);
    }
	
   /**
    * 获取申请人OA账号
    * @return OA_USERNAME
    */
    public String getOaUsername() {
        return getString(D_OaUsername);
    }

	/**
    * 设置审核未通过原因
    * @param REVIEW_FAIL_REASON
    */
	public void setReviewFailReason(String value) {
        set(D_ReviewFailReason,value);
    }
	
   /**
    * 获取审核未通过原因
    * @return REVIEW_FAIL_REASON
    */
    public String getReviewFailReason() {
        return getString(D_ReviewFailReason);
    }

   /**
    * 设置OA审核用01
    * @param FtriggerFlag
    */
	public void setFtriggerFlag(Long value) {
        set(D_FtriggerFlag,value);
    }
	
	/**
    * 获取OA审核用01
    * @return FtriggerFlag
    */
    public Long getFtriggerFlag() {
        return getLong(D_FtriggerFlag);
    }

   /**
    * 设置OA审核用02
    * @param requestid
    */
	public void setRequestid(Long value) {
        set(D_Requestid,value);
    }
	
	/**
    * 获取OA审核用02
    * @return requestid
    */
    public Long getRequestid() {
        return getLong(D_Requestid);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("billCode");
        propertes.add("billDate");
        propertes.add("eventCode");
        propertes.add("applyId");
        propertes.add("applyUnitId");
        propertes.add("accountsUnitId");
        propertes.add("applyType");
        propertes.add("emergencyType");
        propertes.add("copyId");
        propertes.add("requireType");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("oaUsername");
        propertes.add("reviewFailReason");
        propertes.add("ftriggerFlag");
        propertes.add("requestid");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




