package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-8 13:53:19
 *       表：SC_M_BILL_PRICE_LIST_DETAIL 
 */
public class PriceListDetailModel  extends BaseDataModel implements Serializable{
    
    
    private static final long serialVersionUID = -1016989844060721781L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[1]正常[6]报废 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //单价表ID 
    private static final String D_PriceListId="priceListId";
	 //单价表行号 
    private static final String D_PriceListLn="priceListLn";
	 //物料ID 
    private static final String D_MaterialId="materialId";
	 //单价 
    private static final String D_Price="price";
	 //开始时间 
    private static final String D_StartDate="startDate";
	 //结束时间 
    private static final String D_EndDate="endDate";
	 //创建者(录单人员) 
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
    * 设置[1]正常[6]报废
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]正常[6]报废
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
    * 设置单价表ID
    * @param PRICE_LIST_ID
    */
	public void setPriceListId(Long value) {
        set(D_PriceListId,value);
    }
	
	/**
    * 获取单价表ID
    * @return PRICE_LIST_ID
    */
    public Long getPriceListId() {
        return getLong(D_PriceListId);
    }

   /**
    * 设置单价表行号
    * @param PRICE_LIST_LN
    */
	public void setPriceListLn(Long value) {
        set(D_PriceListLn,value);
    }
	
	/**
    * 获取单价表行号
    * @return PRICE_LIST_LN
    */
    public Long getPriceListLn() {
        return getLong(D_PriceListLn);
    }

   /**
    * 设置物料ID
    * @param MATERIAL_ID
    */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
    * 获取物料ID
    * @return MATERIAL_ID
    */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

	/**
    * 设置单价
    * @param PRICE
    */
	public void setPrice(Double value) {
        set(D_Price,value);
    }
	
   /**
    * 获取单价
    * @return PRICE
    */
    public Double getPrice() {
        return getDouble(D_Price);
    }

	/**
    * 设置开始时间
    * @param START_DATE
    */
	public void setStartDate(Date value) {
        set(D_StartDate,value);
    }
	
   /**
    * 获取开始时间
    * @return START_DATE
    */
    public Date getStartDate() {
        return getDate(D_StartDate);
    }

	/**
    * 设置结束时间
    * @param END_DATE
    */
	public void setEndDate(Date value) {
        set(D_EndDate,value);
    }
	
   /**
    * 获取结束时间
    * @return END_DATE
    */
    public Date getEndDate() {
        return getDate(D_EndDate);
    }

   /**
    * 设置创建者(录单人员)
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建者(录单人员)
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
        propertes.add("priceListId");
        propertes.add("priceListLn");
        propertes.add("materialId");
        propertes.add("price");
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




