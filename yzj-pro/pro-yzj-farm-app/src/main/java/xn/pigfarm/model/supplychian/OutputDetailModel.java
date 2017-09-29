package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-12-26 21:50:29
 *       表：SC_M_BILL_OUTPUT_DETAIL 
 */
public class OutputDetailModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 2300718108349674947L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //status 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //出库ID 
    private static final String D_OutputId="outputId";
	 //出库行号 
    private static final String D_OutputLn="outputLn";
	 //入库ID 
    private static final String D_InputId="inputId";
	 //入库行号 
    private static final String D_InputLn="inputLn";
	 //入库数量 
    private static final String D_InputQty="inputQty";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //物料生成号 
    private static final String D_MaterialOnly="materialOnly";
	 //药品批次号 
    private static final String D_MaterialBatch="materialBatch";
	 //预计出库数量 
    private static final String D_PlanQty="planQty";
	 //出库数量 
    private static final String D_OutputQty="outputQty";
	 //出库仓库ID 
    private static final String D_OutputWarehouseId="outputWarehouseId";
	 //出库人员ID(领用,调拨,报废,采购退货) 
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
	 //猪只类型 
    private static final String D_PigType="pigType";
	 //猪只数量 
    private static final String D_PigQty="pigQty";
	 //[1]采购单退货[2]物料退货 
    private static final String D_ReturnOrgin="returnOrgin";
	 //[1]本场[2]外场 
    private static final String D_AllotDestination="allotDestination";
	 //调拨猪场ID 
    private static final String D_OutputToFarmId="outputToFarmId";
	 //调拨入库仓库ID 
    private static final String D_OutputToWarehouseId="outputToWarehouseId";
	 //报废方式 
    private static final String D_ScrapType="scrapType";
	 //报废原因 
    private static final String D_ScrapReason="scrapReason";
	 //确认领料时间 
    private static final String D_OutputUseDate="outputUseDate";
	 //领用人员ID(领用,调拨,报废,采购退货) 
    private static final String D_UserId="userId";
	 //单据编号 
    private static final String D_BillCode="billCode";
	 //单据日期 
    private static final String D_BillDate="billDate";
	 //单据事件(单据类型) 
    private static final String D_EventCode="eventCode";
	 //创建者 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	

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
    * 设置status
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取status
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
    * 设置出库ID
    * @param OUTPUT_ID
    */
	public void setOutputId(Long value) {
        set(D_OutputId,value);
    }
	
	/**
    * 获取出库ID
    * @return OUTPUT_ID
    */
    public Long getOutputId() {
        return getLong(D_OutputId);
    }

   /**
    * 设置出库行号
    * @param OUTPUT_LN
    */
	public void setOutputLn(Long value) {
        set(D_OutputLn,value);
    }
	
	/**
    * 获取出库行号
    * @return OUTPUT_LN
    */
    public Long getOutputLn() {
        return getLong(D_OutputLn);
    }

   /**
    * 设置入库ID
    * @param INPUT_ID
    */
	public void setInputId(Long value) {
        set(D_InputId,value);
    }
	
	/**
    * 获取入库ID
    * @return INPUT_ID
    */
    public Long getInputId() {
        return getLong(D_InputId);
    }

   /**
    * 设置入库行号
    * @param INPUT_LN
    */
	public void setInputLn(Long value) {
        set(D_InputLn,value);
    }
	
	/**
    * 获取入库行号
    * @return INPUT_LN
    */
    public Long getInputLn() {
        return getLong(D_InputLn);
    }

	/**
    * 设置入库数量
    * @param INPUT_QTY
    */
	public void setInputQty(Double value) {
        set(D_InputQty,value);
    }
	
   /**
    * 获取入库数量
    * @return INPUT_QTY
    */
    public Double getInputQty() {
        return getDouble(D_InputQty);
    }

   /**
    * 设置物料主数据ID
    * @param MATERIAL_ID
    */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
    * 获取物料主数据ID
    * @return MATERIAL_ID
    */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

	/**
    * 设置物料生成号
    * @param MATERIAL_ONLY
    */
	public void setMaterialOnly(String value) {
        set(D_MaterialOnly,value);
    }
	
   /**
    * 获取物料生成号
    * @return MATERIAL_ONLY
    */
    public String getMaterialOnly() {
        return getString(D_MaterialOnly);
    }

	/**
    * 设置药品批次号
    * @param MATERIAL_BATCH
    */
	public void setMaterialBatch(String value) {
        set(D_MaterialBatch,value);
    }
	
   /**
    * 获取药品批次号
    * @return MATERIAL_BATCH
    */
    public String getMaterialBatch() {
        return getString(D_MaterialBatch);
    }

	/**
    * 设置预计出库数量
    * @param PLAN_QTY
    */
	public void setPlanQty(Double value) {
        set(D_PlanQty,value);
    }
	
   /**
    * 获取预计出库数量
    * @return PLAN_QTY
    */
    public Double getPlanQty() {
        return getDouble(D_PlanQty);
    }

	/**
    * 设置出库数量
    * @param OUTPUT_QTY
    */
	public void setOutputQty(Double value) {
        set(D_OutputQty,value);
    }
	
   /**
    * 获取出库数量
    * @return OUTPUT_QTY
    */
    public Double getOutputQty() {
        return getDouble(D_OutputQty);
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
    * 设置出库人员ID(领用,调拨,报废,采购退货)
    * @param OUTPUTER_ID
    */
	public void setOutputerId(Long value) {
        set(D_OutputerId,value);
    }
	
	/**
    * 获取出库人员ID(领用,调拨,报废,采购退货)
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
    * 设置猪只类型
    * @param PIG_TYPE
    */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
   /**
    * 获取猪只类型
    * @return PIG_TYPE
    */
    public String getPigType() {
        return getString(D_PigType);
    }

   /**
    * 设置猪只数量
    * @param PIG_QTY
    */
	public void setPigQty(Long value) {
        set(D_PigQty,value);
    }
	
	/**
    * 获取猪只数量
    * @return PIG_QTY
    */
    public Long getPigQty() {
        return getLong(D_PigQty);
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
    * 设置报废方式
    * @param SCRAP_TYPE
    */
	public void setScrapType(String value) {
        set(D_ScrapType,value);
    }
	
   /**
    * 获取报废方式
    * @return SCRAP_TYPE
    */
    public String getScrapType() {
        return getString(D_ScrapType);
    }

	/**
    * 设置报废原因
    * @param SCRAP_REASON
    */
	public void setScrapReason(String value) {
        set(D_ScrapReason,value);
    }
	
   /**
    * 获取报废原因
    * @return SCRAP_REASON
    */
    public String getScrapReason() {
        return getString(D_ScrapReason);
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
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("outputId");
        propertes.add("outputLn");
        propertes.add("inputId");
        propertes.add("inputLn");
        propertes.add("inputQty");
        propertes.add("materialId");
        propertes.add("materialOnly");
        propertes.add("materialBatch");
        propertes.add("planQty");
        propertes.add("outputQty");
        propertes.add("outputWarehouseId");
        propertes.add("outputerId");
        propertes.add("outputDestination");
        propertes.add("outputFarmId");
        propertes.add("outputSwineryId");
        propertes.add("outputHouseId");
        propertes.add("outputDeptId");
        propertes.add("pigType");
        propertes.add("pigQty");
        propertes.add("returnOrgin");
        propertes.add("allotDestination");
        propertes.add("outputToFarmId");
        propertes.add("outputToWarehouseId");
        propertes.add("scrapType");
        propertes.add("scrapReason");
        propertes.add("outputUseDate");
        propertes.add("userId");
        propertes.add("billCode");
        propertes.add("billDate");
        propertes.add("eventCode");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




