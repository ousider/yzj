package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-17 14:39:39
 *       表：PP_L_BILL_SEMEN_SALE 
 */
public class SemenSaleModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 3387053366994292746L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。 
    private static final String D_OriginFlag="originFlag";
	 //数据来源应用的代码 
    private static final String D_OriginApp="originApp";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //单据号 
    private static final String D_BillId="billId";
	 //记录行号 
    private static final String D_LineNumber="lineNumber";
	 //是否入库 
    private static final String D_IsEntry="isEntry";
	 //客户ID 
    private static final String D_CustomerId="customerId";
	 //精液ID 
    private static final String D_SemenId="semenId";
	 //仓库ID 
    private static final String D_WarehouseId="warehouseId";
	 //销售数量 
    private static final String D_SaleNum="saleNum";
	 //有效日期 
    private static final String D_ValidDate="validDate";
	 //销售日期 
    private static final String D_SaleDate="saleDate";
	 //采精公猪ID 
    private static final String D_BoarId="boarId";
	 //负责人 
    private static final String D_Worker="worker";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //删除了这次记录的撤销单据ID 
    private static final String D_DeletedBillId="deletedBillId";
	

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
    * 设置排序号
    * @param SORT_NBR
    */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
    * 获取排序号
    * @return SORT_NBR
    */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
    * 设置表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
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
    * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @param ORIGIN_FLAG
    */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
   /**
    * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @return ORIGIN_FLAG
    */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
    * 设置数据来源应用的代码
    * @param ORIGIN_APP
    */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
   /**
    * 获取数据来源应用的代码
    * @return ORIGIN_APP
    */
    public String getOriginApp() {
        return getString(D_OriginApp);
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
    * 设置单据号
    * @param BILL_ID
    */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
    * 获取单据号
    * @return BILL_ID
    */
    public Long getBillId() {
        return getLong(D_BillId);
    }

   /**
    * 设置记录行号
    * @param LINE_NUMBER
    */
	public void setLineNumber(Long value) {
        set(D_LineNumber,value);
    }
	
	/**
    * 获取记录行号
    * @return LINE_NUMBER
    */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

   /**
    * 设置是否入库
    * @param IS_ENTRY
    */
	public void setIsEntry(Long value) {
        set(D_IsEntry,value);
    }
	
	/**
    * 获取是否入库
    * @return IS_ENTRY
    */
    public Long getIsEntry() {
        return getLong(D_IsEntry);
    }

   /**
    * 设置客户ID
    * @param CUSTOMER_ID
    */
	public void setCustomerId(Long value) {
        set(D_CustomerId,value);
    }
	
	/**
    * 获取客户ID
    * @return CUSTOMER_ID
    */
    public Long getCustomerId() {
        return getLong(D_CustomerId);
    }

   /**
    * 设置精液ID
    * @param SEMEN_ID
    */
	public void setSemenId(Long value) {
        set(D_SemenId,value);
    }
	
	/**
    * 获取精液ID
    * @return SEMEN_ID
    */
    public Long getSemenId() {
        return getLong(D_SemenId);
    }

   /**
    * 设置仓库ID
    * @param WAREHOUSE_ID
    */
	public void setWarehouseId(Long value) {
        set(D_WarehouseId,value);
    }
	
	/**
    * 获取仓库ID
    * @return WAREHOUSE_ID
    */
    public Long getWarehouseId() {
        return getLong(D_WarehouseId);
    }

   /**
    * 设置销售数量
    * @param SALE_NUM
    */
	public void setSaleNum(Long value) {
        set(D_SaleNum,value);
    }
	
	/**
    * 获取销售数量
    * @return SALE_NUM
    */
    public Long getSaleNum() {
        return getLong(D_SaleNum);
    }

	/**
    * 设置有效日期
    * @param VALID_DATE
    */
	public void setValidDate(Date value) {
        set(D_ValidDate,value);
    }
	
   /**
    * 获取有效日期
    * @return VALID_DATE
    */
    public Date getValidDate() {
        return getDate(D_ValidDate);
    }

	/**
    * 设置销售日期
    * @param SALE_DATE
    */
	public void setSaleDate(Date value) {
        set(D_SaleDate,value);
    }
	
   /**
    * 获取销售日期
    * @return SALE_DATE
    */
    public Date getSaleDate() {
        return getDate(D_SaleDate);
    }

   /**
    * 设置采精公猪ID
    * @param BOAR_ID
    */
	public void setBoarId(Long value) {
        set(D_BoarId,value);
    }
	
	/**
    * 获取采精公猪ID
    * @return BOAR_ID
    */
    public Long getBoarId() {
        return getLong(D_BoarId);
    }

   /**
    * 设置负责人
    * @param WORKER
    */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
    * 获取负责人
    * @return WORKER
    */
    public Long getWorker() {
        return getLong(D_Worker);
    }

   /**
    * 设置创建人
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建人
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
    * 设置删除了这次记录的撤销单据ID
    * @param DELETED_BILL_ID
    */
	public void setDeletedBillId(Long value) {
        set(D_DeletedBillId,value);
    }
	
	/**
    * 获取删除了这次记录的撤销单据ID
    * @return DELETED_BILL_ID
    */
    public Long getDeletedBillId() {
        return getLong(D_DeletedBillId);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("billId");
        propertes.add("lineNumber");
        propertes.add("isEntry");
        propertes.add("customerId");
        propertes.add("semenId");
        propertes.add("warehouseId");
        propertes.add("saleNum");
        propertes.add("validDate");
        propertes.add("saleDate");
        propertes.add("boarId");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("deletedBillId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




