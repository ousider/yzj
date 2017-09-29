package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-4-27 10:35:16
 *       表：RE_L_PRO_DETAIL 
 */
public class ReLproDetailModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = 6575610506393894413L;

	//存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //备注 
    private static final String D_Notes="notes";
	 //报表ID 
    private static final String D_ReportId="reportId";
	 //猪场ID 
    private static final String D_FarmId="farmId";
	 //类型 
    private static final String D_ProType="proType";
	 //指标 
    private static final String D_Indicator="indicator";
	 //头数 
    private static final String D_PigQty="pigQty";
	 //集团目标 
    private static final String D_GroupTarget="groupTarget";
	 //集团实际生产 
    private static final String D_GroupActual="groupActual";
	 //创建人 
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
    * 设置报表ID
    * @param REPORT_ID
    */
	public void setReportId(Long value) {
        set(D_ReportId,value);
    }
	
	/**
    * 获取报表ID
    * @return REPORT_ID
    */
    public Long getReportId() {
        return getLong(D_ReportId);
    }

   /**
    * 设置猪场ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取猪场ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置类型
    * @param PRO_TYPE
    */
	public void setProType(Long value) {
        set(D_ProType,value);
    }
	
	/**
    * 获取类型
    * @return PRO_TYPE
    */
    public Long getProType() {
        return getLong(D_ProType);
    }

   /**
    * 设置指标
    * @param INDICATOR
    */
	public void setIndicator(Long value) {
        set(D_Indicator,value);
    }
	
	/**
    * 获取指标
    * @return INDICATOR
    */
    public Long getIndicator() {
        return getLong(D_Indicator);
    }

   /**
    * 设置头数
    * @param PIG_QTY
    */
	public void setPigQty(Long value) {
        set(D_PigQty,value);
    }
	
	/**
    * 获取头数
    * @return PIG_QTY
    */
    public Long getPigQty() {
        return getLong(D_PigQty);
    }

   /**
    * 设置集团目标
    * @param GROUP_TARGET
    */
	public void setGroupTarget(Long value) {
        set(D_GroupTarget,value);
    }
	
	/**
    * 获取集团目标
    * @return GROUP_TARGET
    */
    public Long getGroupTarget() {
        return getLong(D_GroupTarget);
    }

   /**
    * 设置集团实际生产
    * @param GROUP_ACTUAL
    */
	public void setGroupActual(Double value) {
        set(D_GroupActual,value);
    }
	
	/**
    * 获取集团实际生产
    * @return GROUP_ACTUAL
    */
    public Double getGroupActual() {
        return getDouble(D_GroupActual);
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
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("deletedFlag");
        propertes.add("status");
        propertes.add("notes");
        propertes.add("reportId");
        propertes.add("farmId");
        propertes.add("proType");
        propertes.add("indicator");
        propertes.add("pigQty");
        propertes.add("groupTarget");
        propertes.add("groupActual");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




