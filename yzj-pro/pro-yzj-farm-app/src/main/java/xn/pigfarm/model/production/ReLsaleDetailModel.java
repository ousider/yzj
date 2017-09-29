package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-5-31 22:33:15
 *       表：RE_L_SALE_DETAIL 
 */
public class ReLsaleDetailModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = 2993498053128427809L;

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
	 //备注 
    private static final String D_Notes="notes";
	 //报表ID 
    private static final String D_ReportId="reportId";
	 //猪场ID 
    private static final String D_FarmId="farmId";
	 //上市总头数 
    private static final String D_TotalNum="totalNum";
	 //苗猪头数 
    private static final String D_MzNum="mzNum";
	 //苗猪均重 
    private static final String D_MzAvgWeight="mzAvgWeight";
	 //苗猪结算单价 
    private static final String D_MzPrice="mzPrice";
	 //苗猪销售总额 
    private static final String D_MzTotalPrice="mzTotalPrice";
	 //肉猪头数 
    private static final String D_RzNum="rzNum";
	 //肉猪均重 
    private static final String D_RzAvgWeight="rzAvgWeight";
	 //肉猪结算单价 
    private static final String D_RzPrice="rzPrice";
	 //肉猪销售总额 
    private static final String D_RzTotalPrice="rzTotalPrice";
	 //种猪头数 
    private static final String D_ZzNum="zzNum";
	 //种猪均重 
    private static final String D_ZzAvgWeight="zzAvgWeight";
	 //种猪结算单价 
    private static final String D_ZzPrice="zzPrice";
	 //种猪销售总额 
    private static final String D_ZzTotalPrice="zzTotalPrice";
	 //销售未结算头数 
    private static final String D_UnblanceNum="unblanceNum";
	 //年销售目标 
    private static final String D_YearSaleTarget="yearSaleTarget";
	 //达成率 
    private static final String D_CompleteRate="completeRate";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //是否是总场 
    private static final String D_IsTotalfield="isTotalfield";
	 //残次猪,猪场自宰销售备注 
    private static final String D_SaleNote="saleNote";
	

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
    * 设置上市总头数
    * @param TOTAL_NUM
    */
	public void setTotalNum(Long value) {
        set(D_TotalNum,value);
    }
	
	/**
    * 获取上市总头数
    * @return TOTAL_NUM
    */
    public Long getTotalNum() {
        return getLong(D_TotalNum);
    }

   /**
    * 设置苗猪头数
    * @param MZ_NUM
    */
	public void setMzNum(Long value) {
        set(D_MzNum,value);
    }
	
	/**
    * 获取苗猪头数
    * @return MZ_NUM
    */
    public Long getMzNum() {
        return getLong(D_MzNum);
    }

	/**
    * 设置苗猪均重
    * @param MZ_AVG_WEIGHT
    */
	public void setMzAvgWeight(Double value) {
        set(D_MzAvgWeight,value);
    }
	
   /**
    * 获取苗猪均重
    * @return MZ_AVG_WEIGHT
    */
    public Double getMzAvgWeight() {
        return getDouble(D_MzAvgWeight);
    }

	/**
    * 设置苗猪结算单价
    * @param MZ_PRICE
    */
	public void setMzPrice(Double value) {
        set(D_MzPrice,value);
    }
	
   /**
    * 获取苗猪结算单价
    * @return MZ_PRICE
    */
    public Double getMzPrice() {
        return getDouble(D_MzPrice);
    }

	/**
    * 设置苗猪销售总额
    * @param MZ_TOTAL_PRICE
    */
	public void setMzTotalPrice(Double value) {
        set(D_MzTotalPrice,value);
    }
	
   /**
    * 获取苗猪销售总额
    * @return MZ_TOTAL_PRICE
    */
    public Double getMzTotalPrice() {
        return getDouble(D_MzTotalPrice);
    }

   /**
    * 设置肉猪头数
    * @param RZ_NUM
    */
	public void setRzNum(Long value) {
        set(D_RzNum,value);
    }
	
	/**
    * 获取肉猪头数
    * @return RZ_NUM
    */
    public Long getRzNum() {
        return getLong(D_RzNum);
    }

	/**
    * 设置肉猪均重
    * @param RZ_AVG_WEIGHT
    */
	public void setRzAvgWeight(Double value) {
        set(D_RzAvgWeight,value);
    }
	
   /**
    * 获取肉猪均重
    * @return RZ_AVG_WEIGHT
    */
    public Double getRzAvgWeight() {
        return getDouble(D_RzAvgWeight);
    }

	/**
    * 设置肉猪结算单价
    * @param RZ_PRICE
    */
	public void setRzPrice(Double value) {
        set(D_RzPrice,value);
    }
	
   /**
    * 获取肉猪结算单价
    * @return RZ_PRICE
    */
    public Double getRzPrice() {
        return getDouble(D_RzPrice);
    }

	/**
    * 设置肉猪销售总额
    * @param RZ_TOTAL_PRICE
    */
	public void setRzTotalPrice(Double value) {
        set(D_RzTotalPrice,value);
    }
	
   /**
    * 获取肉猪销售总额
    * @return RZ_TOTAL_PRICE
    */
    public Double getRzTotalPrice() {
        return getDouble(D_RzTotalPrice);
    }

   /**
    * 设置种猪头数
    * @param ZZ_NUM
    */
	public void setZzNum(Long value) {
        set(D_ZzNum,value);
    }
	
	/**
    * 获取种猪头数
    * @return ZZ_NUM
    */
    public Long getZzNum() {
        return getLong(D_ZzNum);
    }

	/**
    * 设置种猪均重
    * @param ZZ_AVG_WEIGHT
    */
	public void setZzAvgWeight(Double value) {
        set(D_ZzAvgWeight,value);
    }
	
   /**
    * 获取种猪均重
    * @return ZZ_AVG_WEIGHT
    */
    public Double getZzAvgWeight() {
        return getDouble(D_ZzAvgWeight);
    }

	/**
    * 设置种猪结算单价
    * @param ZZ_PRICE
    */
	public void setZzPrice(Double value) {
        set(D_ZzPrice,value);
    }
	
   /**
    * 获取种猪结算单价
    * @return ZZ_PRICE
    */
    public Double getZzPrice() {
        return getDouble(D_ZzPrice);
    }

	/**
    * 设置种猪销售总额
    * @param ZZ_TOTAL_PRICE
    */
	public void setZzTotalPrice(Double value) {
        set(D_ZzTotalPrice,value);
    }
	
   /**
    * 获取种猪销售总额
    * @return ZZ_TOTAL_PRICE
    */
    public Double getZzTotalPrice() {
        return getDouble(D_ZzTotalPrice);
    }

   /**
    * 设置销售未结算头数
    * @param UNBLANCE_NUM
    */
	public void setUnblanceNum(Long value) {
        set(D_UnblanceNum,value);
    }
	
	/**
    * 获取销售未结算头数
    * @return UNBLANCE_NUM
    */
    public Long getUnblanceNum() {
        return getLong(D_UnblanceNum);
    }

   /**
    * 设置年销售目标
    * @param YEAR_SALE_TARGET
    */
	public void setYearSaleTarget(Long value) {
        set(D_YearSaleTarget,value);
    }
	
	/**
    * 获取年销售目标
    * @return YEAR_SALE_TARGET
    */
    public Long getYearSaleTarget() {
        return getLong(D_YearSaleTarget);
    }

	/**
    * 设置达成率
    * @param COMPLETE_RATE
    */
	public void setCompleteRate(Double value) {
        set(D_CompleteRate,value);
    }
	
   /**
    * 获取达成率
    * @return COMPLETE_RATE
    */
    public Double getCompleteRate() {
        return getDouble(D_CompleteRate);
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
    * 设置是否是总场
    * @param IS_TOTALFIELD
    */
	public void setIsTotalfield(Long value) {
        set(D_IsTotalfield,value);
    }
	
	/**
    * 获取是否是总场
    * @return IS_TOTALFIELD
    */
    public Long getIsTotalfield() {
        return getLong(D_IsTotalfield);
    }

	/**
    * 设置残次猪,猪场自宰销售备注
    * @param SALE_NOTE
    */
	public void setSaleNote(String value) {
        set(D_SaleNote,value);
    }
	
   /**
    * 获取残次猪,猪场自宰销售备注
    * @return SALE_NOTE
    */
    public String getSaleNote() {
        return getString(D_SaleNote);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("reportId");
        propertes.add("farmId");
        propertes.add("totalNum");
        propertes.add("mzNum");
        propertes.add("mzAvgWeight");
        propertes.add("mzPrice");
        propertes.add("mzTotalPrice");
        propertes.add("rzNum");
        propertes.add("rzAvgWeight");
        propertes.add("rzPrice");
        propertes.add("rzTotalPrice");
        propertes.add("zzNum");
        propertes.add("zzAvgWeight");
        propertes.add("zzPrice");
        propertes.add("zzTotalPrice");
        propertes.add("unblanceNum");
        propertes.add("yearSaleTarget");
        propertes.add("completeRate");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("isTotalfield");
        propertes.add("saleNote");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




