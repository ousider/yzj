package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-8 13:47:34
 *       表：SC_M_BILL_PRICE_LIST 
 */
public class PriceListModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 4806234823470073609L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[1]-待审批[2]-审批未通过[3]-审批通过[4]-入库中[5]-完成[6]-报废[7]完成[8]完结 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //供应商ID 
    private static final String D_SupplierId="supplierId";
	 //月份 
    private static final String D_Month="month";
	 //日期范围 
    private static final String D_DateRange="dateRange";
	 //开始日期 
    private static final String D_StartDate="startDate";
	 //结束日期 
    private static final String D_EndDate="endDate";
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
    * 设置[1]-待审批[2]-审批未通过[3]-审批通过[4]-入库中[5]-完成[6]-报废[7]完成[8]完结
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]-待审批[2]-审批未通过[3]-审批通过[4]-入库中[5]-完成[6]-报废[7]完成[8]完结
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
    * 设置供应商ID
    * @param SUPPLIER_ID
    */
	public void setSupplierId(Long value) {
        set(D_SupplierId,value);
    }
	
	/**
    * 获取供应商ID
    * @return SUPPLIER_ID
    */
    public Long getSupplierId() {
        return getLong(D_SupplierId);
    }

   /**
    * 设置月份
    * @param MONTH
    */
	public void setMonth(Long value) {
        set(D_Month,value);
    }
	
	/**
    * 获取月份
    * @return MONTH
    */
    public Long getMonth() {
        return getLong(D_Month);
    }

	/**
    * 设置日期范围
    * @param DATE_RANGE
    */
	public void setDateRange(String value) {
        set(D_DateRange,value);
    }
	
   /**
    * 获取日期范围
    * @return DATE_RANGE
    */
    public String getDateRange() {
        return getString(D_DateRange);
    }

	/**
    * 设置开始日期
    * @param START_DATE
    */
	public void setStartDate(Date value) {
        set(D_StartDate,value);
    }
	
   /**
    * 获取开始日期
    * @return START_DATE
    */
    public Date getStartDate() {
        return getDate(D_StartDate);
    }

	/**
    * 设置结束日期
    * @param END_DATE
    */
	public void setEndDate(Date value) {
        set(D_EndDate,value);
    }
	
   /**
    * 获取结束日期
    * @return END_DATE
    */
    public Date getEndDate() {
        return getDate(D_EndDate);
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
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("supplierId");
        propertes.add("month");
        propertes.add("dateRange");
        propertes.add("startDate");
        propertes.add("endDate");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




