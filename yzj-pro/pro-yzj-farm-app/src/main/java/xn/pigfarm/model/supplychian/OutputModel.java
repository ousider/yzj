package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-2-15 14:07:50
 *       表：SC_M_BILL_OUTPUT 
 */
public class OutputModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -1624268633771956787L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //领用单时[1]-待领用[2]-已领用[3]-未领用 
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
	 //出库仓库ID 
    private static final String D_OutputWarehouseId="outputWarehouseId";
	 //出库人员ID 
    private static final String D_OutputerId="outputerId";
	 //猪场全场、猪群、猪舍、部门、其他猪场 
    private static final String D_OutputDestination="outputDestination";
	 //出库猪场ID 
    private static final String D_OutputFarmId="outputFarmId";
	 //出库猪群ID 
    private static final String D_OutputSwineryId="outputSwineryId";
	 //出库猪舍ID 
    private static final String D_OutputHouseId="outputHouseId";
	 //出库部门ID 
    private static final String D_OutputDeptId="outputDeptId";
	 //[1]采购单退货[2]物料退货 
    private static final String D_ReturnOrgin="returnOrgin";
	 //[1]本场[2]外场 
    private static final String D_AllotDestination="allotDestination";
	 //调拨猪场ID 
    private static final String D_OutputToFarmId="outputToFarmId";
	 //调拨入库仓库ID 
    private static final String D_OutputToWarehouseId="outputToWarehouseId";
	 //领料号 
    private static final String D_OutputUseNumber="outputUseNumber";
	 //确认领料时间 
    private static final String D_OutputUseDate="outputUseDate";
	 //领用人员ID(领用,调拨,报废,采购退货) 
    private static final String D_UserId="userId";
	 //创建者 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //打印次数 
    private static final String D_PrintTimes="printTimes";
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
    * 设置领用单时[1]-待领用[2]-已领用[3]-未领用
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取领用单时[1]-待领用[2]-已领用[3]-未领用
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
    * 设置出库仓库ID
    * @param OUTPUT_WAREHOUSE_ID
    */
	public void setOutputWarehouseId(Long value) {
        set(D_OutputWarehouseId,value);
    }
	
	/**
    * 获取出库仓库ID
    * @return OUTPUT_WAREHOUSE_ID
    */
    public Long getOutputWarehouseId() {
        return getLong(D_OutputWarehouseId);
    }

   /**
    * 设置出库人员ID
    * @param OUTPUTER_ID
    */
	public void setOutputerId(Long value) {
        set(D_OutputerId,value);
    }
	
	/**
    * 获取出库人员ID
    * @return OUTPUTER_ID
    */
    public Long getOutputerId() {
        return getLong(D_OutputerId);
    }

	/**
    * 设置猪场全场、猪群、猪舍、部门、其他猪场
    * @param OUTPUT_DESTINATION
    */
	public void setOutputDestination(String value) {
        set(D_OutputDestination,value);
    }
	
   /**
    * 获取猪场全场、猪群、猪舍、部门、其他猪场
    * @return OUTPUT_DESTINATION
    */
    public String getOutputDestination() {
        return getString(D_OutputDestination);
    }

   /**
    * 设置出库猪场ID
    * @param OUTPUT_FARM_ID
    */
	public void setOutputFarmId(Long value) {
        set(D_OutputFarmId,value);
    }
	
	/**
    * 获取出库猪场ID
    * @return OUTPUT_FARM_ID
    */
    public Long getOutputFarmId() {
        return getLong(D_OutputFarmId);
    }

   /**
    * 设置出库猪群ID
    * @param OUTPUT_SWINERY_ID
    */
	public void setOutputSwineryId(Long value) {
        set(D_OutputSwineryId,value);
    }
	
	/**
    * 获取出库猪群ID
    * @return OUTPUT_SWINERY_ID
    */
    public Long getOutputSwineryId() {
        return getLong(D_OutputSwineryId);
    }

   /**
    * 设置出库猪舍ID
    * @param OUTPUT_HOUSE_ID
    */
	public void setOutputHouseId(Long value) {
        set(D_OutputHouseId,value);
    }
	
	/**
    * 获取出库猪舍ID
    * @return OUTPUT_HOUSE_ID
    */
    public Long getOutputHouseId() {
        return getLong(D_OutputHouseId);
    }

   /**
    * 设置出库部门ID
    * @param OUTPUT_DEPT_ID
    */
	public void setOutputDeptId(Long value) {
        set(D_OutputDeptId,value);
    }
	
	/**
    * 获取出库部门ID
    * @return OUTPUT_DEPT_ID
    */
    public Long getOutputDeptId() {
        return getLong(D_OutputDeptId);
    }

	/**
    * 设置[1]采购单退货[2]物料退货
    * @param RETURN_ORGIN
    */
	public void setReturnOrgin(String value) {
        set(D_ReturnOrgin,value);
    }
	
   /**
    * 获取[1]采购单退货[2]物料退货
    * @return RETURN_ORGIN
    */
    public String getReturnOrgin() {
        return getString(D_ReturnOrgin);
    }

	/**
    * 设置[1]本场[2]外场
    * @param ALLOT_DESTINATION
    */
	public void setAllotDestination(String value) {
        set(D_AllotDestination,value);
    }
	
   /**
    * 获取[1]本场[2]外场
    * @return ALLOT_DESTINATION
    */
    public String getAllotDestination() {
        return getString(D_AllotDestination);
    }

   /**
    * 设置调拨猪场ID
    * @param OUTPUT_TO_FARM_ID
    */
	public void setOutputToFarmId(Long value) {
        set(D_OutputToFarmId,value);
    }
	
	/**
    * 获取调拨猪场ID
    * @return OUTPUT_TO_FARM_ID
    */
    public Long getOutputToFarmId() {
        return getLong(D_OutputToFarmId);
    }

   /**
    * 设置调拨入库仓库ID
    * @param OUTPUT_TO_WAREHOUSE_ID
    */
	public void setOutputToWarehouseId(Long value) {
        set(D_OutputToWarehouseId,value);
    }
	
	/**
    * 获取调拨入库仓库ID
    * @return OUTPUT_TO_WAREHOUSE_ID
    */
    public Long getOutputToWarehouseId() {
        return getLong(D_OutputToWarehouseId);
    }

	/**
    * 设置领料号
    * @param OUTPUT_USE_NUMBER
    */
	public void setOutputUseNumber(String value) {
        set(D_OutputUseNumber,value);
    }
	
   /**
    * 获取领料号
    * @return OUTPUT_USE_NUMBER
    */
    public String getOutputUseNumber() {
        return getString(D_OutputUseNumber);
    }

	/**
    * 设置确认领料时间
    * @param OUTPUT_USE_DATE
    */
    public void setOutputUseDate(Date value) {
        set(D_OutputUseDate,value);
    }
	
   /**
    * 获取确认领料时间
    * @return OUTPUT_USE_DATE
    */
    public Date getOutputUseDate() {
        return getTimestamp(D_OutputUseDate);
    }

   /**
    * 设置领用人员ID(领用,调拨,报废,采购退货)
    * @param USER_ID
    */
	public void setUserId(Long value) {
        set(D_UserId,value);
    }
	
	/**
    * 获取领用人员ID(领用,调拨,报废,采购退货)
    * @return USER_ID
    */
    public Long getUserId() {
        return getLong(D_UserId);
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
        return getTimestamp(D_CreateDate);
    }

   /**
    * 设置打印次数
    * @param PRINT_TIMES
    */
	public void setPrintTimes(Long value) {
        set(D_PrintTimes,value);
    }
	
	/**
    * 获取打印次数
    * @return PRINT_TIMES
    */
    public Long getPrintTimes() {
        return getLong(D_PrintTimes);
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
        propertes.add("outputWarehouseId");
        propertes.add("outputerId");
        propertes.add("outputDestination");
        propertes.add("outputFarmId");
        propertes.add("outputSwineryId");
        propertes.add("outputHouseId");
        propertes.add("outputDeptId");
        propertes.add("returnOrgin");
        propertes.add("allotDestination");
        propertes.add("outputToFarmId");
        propertes.add("outputToWarehouseId");
        propertes.add("outputUseNumber");
        propertes.add("outputUseDate");
        propertes.add("userId");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("printTimes");
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




