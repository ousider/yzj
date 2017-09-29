package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-31 14:32:12
 *       表：PP_L_BILL_BREED 
 */
public class BillBreedModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -4866641768099351071L;

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
	 //记录行号 
    private static final String D_LineNumber="lineNumber";
	 //猪群ID 
    private static final String D_SwineryId="swineryId";
	 //猪只ID 
    private static final String D_PigId="pigId";
	 //产线ID 
    private static final String D_LineId="lineId";
	 //猪舍ID 
    private static final String D_HouseId="houseId";
	 //栏号ID 
    private static final String D_PigpenId="pigpenId";
	 //单据号 
    private static final String D_BillId="billId";
	 //负责人 
    private static final String D_Worker="worker";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //生产号 
    private static final String D_ProNo="proNo";
	 //胎次 
    private static final String D_Parity="parity";
	 //当前日龄 
    private static final String D_DayAge="dayAge";
	 //上一状态 
    private static final String D_LastStatus="lastStatus";
	 //配种方式 
    private static final String D_BreedType="breedType";
	 //1配种日期 
    private static final String D_BreedDateFirst="breedDateFirst";
	 //1配种公猪 
    private static final String D_BreedBoarFirst="breedBoarFirst";
	 //2配种日期 
    private static final String D_BreedDateSecond="breedDateSecond";
	 //2配种公猪 
    private static final String D_BreedBoarSecond="breedBoarSecond";
	 //3配种日期 
    private static final String D_BreedDateThird="breedDateThird";
	 //3配种公猪 
    private static final String D_BreedBoarThird="breedBoarThird";
	 //1配种公猪的物料主数据 
    private static final String D_BreedBoarFirstMaterialId="breedBoarFirstMaterialId";
	 //1配种公猪的精液ROW_ID 
    private static final String D_BreedBoarFirstSpermRowId="breedBoarFirstSpermRowId";
	 //2配种公猪的精液ROW_ID 
    private static final String D_BreedBoarSecondSpermRowId="breedBoarSecondSpermRowId";
	 //3配种公猪的精液ROW_ID 
    private static final String D_BreedBoarThirdSpermRowId="breedBoarThirdSpermRowId";
	 //静立分 
    private static final String D_JlScore="jlScore";
	 //锁住分 
    private static final String D_SzScore="szScore";
	 //倒流分 
    private static final String D_DlScore="dlScore";
	 //精液质量 
    private static final String D_SemenQuality="semenQuality";
	

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
    * 设置猪群ID
    * @param SWINERY_ID
    */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
    * 获取猪群ID
    * @return SWINERY_ID
    */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
    }

   /**
    * 设置猪只ID
    * @param PIG_ID
    */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
    * 获取猪只ID
    * @return PIG_ID
    */
    public Long getPigId() {
        return getLong(D_PigId);
    }

   /**
    * 设置产线ID
    * @param LINE_ID
    */
	public void setLineId(Long value) {
        set(D_LineId,value);
    }
	
	/**
    * 获取产线ID
    * @return LINE_ID
    */
    public Long getLineId() {
        return getLong(D_LineId);
    }

   /**
    * 设置猪舍ID
    * @param HOUSE_ID
    */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
    * 获取猪舍ID
    * @return HOUSE_ID
    */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

   /**
    * 设置栏号ID
    * @param PIGPEN_ID
    */
	public void setPigpenId(Long value) {
        set(D_PigpenId,value);
    }
	
	/**
    * 获取栏号ID
    * @return PIGPEN_ID
    */
    public Long getPigpenId() {
        return getLong(D_PigpenId);
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
    * 设置生产号
    * @param PRO_NO
    */
	public void setProNo(Long value) {
        set(D_ProNo,value);
    }
	
	/**
    * 获取生产号
    * @return PRO_NO
    */
    public Long getProNo() {
        return getLong(D_ProNo);
    }

   /**
    * 设置胎次
    * @param PARITY
    */
	public void setParity(Long value) {
        set(D_Parity,value);
    }
	
	/**
    * 获取胎次
    * @return PARITY
    */
    public Long getParity() {
        return getLong(D_Parity);
    }

   /**
    * 设置当前日龄
    * @param DAY_AGE
    */
	public void setDayAge(Long value) {
        set(D_DayAge,value);
    }
	
	/**
    * 获取当前日龄
    * @return DAY_AGE
    */
    public Long getDayAge() {
        return getLong(D_DayAge);
    }

   /**
    * 设置上一状态
    * @param LAST_STATUS
    */
	public void setLastStatus(Long value) {
        set(D_LastStatus,value);
    }
	
	/**
    * 获取上一状态
    * @return LAST_STATUS
    */
    public Long getLastStatus() {
        return getLong(D_LastStatus);
    }

	/**
    * 设置配种方式
    * @param BREED_TYPE
    */
	public void setBreedType(String value) {
        set(D_BreedType,value);
    }
	
   /**
    * 获取配种方式
    * @return BREED_TYPE
    */
    public String getBreedType() {
        return getString(D_BreedType);
    }

	/**
    * 设置1配种日期
    * @param BREED_DATE_FIRST
    */
    public void setBreedDateFirst(Date value) {
        set(D_BreedDateFirst,value);
    }
	
   /**
    * 获取1配种日期
    * @return BREED_DATE_FIRST
    */
    public Date getBreedDateFirst() {
        return getDate(D_BreedDateFirst);
    }

   /**
    * 设置1配种公猪
    * @param BREED_BOAR_FIRST
    */
	public void setBreedBoarFirst(Long value) {
        set(D_BreedBoarFirst,value);
    }
	
	/**
    * 获取1配种公猪
    * @return BREED_BOAR_FIRST
    */
    public Long getBreedBoarFirst() {
        return getLong(D_BreedBoarFirst);
    }

	/**
    * 设置2配种日期
    * @param BREED_DATE_SECOND
    */
    public void setBreedDateSecond(Date value) {
        set(D_BreedDateSecond,value);
    }
	
   /**
    * 获取2配种日期
    * @return BREED_DATE_SECOND
    */
    public Date getBreedDateSecond() {
        return getDate(D_BreedDateSecond);
    }

   /**
    * 设置2配种公猪
    * @param BREED_BOAR_SECOND
    */
	public void setBreedBoarSecond(Long value) {
        set(D_BreedBoarSecond,value);
    }
	
	/**
    * 获取2配种公猪
    * @return BREED_BOAR_SECOND
    */
    public Long getBreedBoarSecond() {
        return getLong(D_BreedBoarSecond);
    }

	/**
    * 设置3配种日期
    * @param BREED_DATE_THIRD
    */
    public void setBreedDateThird(Date value) {
        set(D_BreedDateThird,value);
    }
	
   /**
    * 获取3配种日期
    * @return BREED_DATE_THIRD
    */
    public Date getBreedDateThird() {
        return getDate(D_BreedDateThird);
    }

   /**
    * 设置3配种公猪
    * @param BREED_BOAR_THIRD
    */
	public void setBreedBoarThird(Long value) {
        set(D_BreedBoarThird,value);
    }
	
	/**
    * 获取3配种公猪
    * @return BREED_BOAR_THIRD
    */
    public Long getBreedBoarThird() {
        return getLong(D_BreedBoarThird);
    }

   /**
    * 设置1配种公猪的物料主数据
    * @param BREED_BOAR_FIRST_MATERIAL_ID
    */
	public void setBreedBoarFirstMaterialId(Long value) {
        set(D_BreedBoarFirstMaterialId,value);
    }
	
	/**
    * 获取1配种公猪的物料主数据
    * @return BREED_BOAR_FIRST_MATERIAL_ID
    */
    public Long getBreedBoarFirstMaterialId() {
        return getLong(D_BreedBoarFirstMaterialId);
    }

   /**
    * 设置1配种公猪的精液ROW_ID
    * @param BREED_BOAR_FIRST_SPERM_ROW_ID
    */
	public void setBreedBoarFirstSpermRowId(Long value) {
        set(D_BreedBoarFirstSpermRowId,value);
    }
	
	/**
    * 获取1配种公猪的精液ROW_ID
    * @return BREED_BOAR_FIRST_SPERM_ROW_ID
    */
    public Long getBreedBoarFirstSpermRowId() {
        return getLong(D_BreedBoarFirstSpermRowId);
    }

   /**
    * 设置2配种公猪的精液ROW_ID
    * @param BREED_BOAR_SECOND_SPERM_ROW_ID
    */
	public void setBreedBoarSecondSpermRowId(Long value) {
        set(D_BreedBoarSecondSpermRowId,value);
    }
	
	/**
    * 获取2配种公猪的精液ROW_ID
    * @return BREED_BOAR_SECOND_SPERM_ROW_ID
    */
    public Long getBreedBoarSecondSpermRowId() {
        return getLong(D_BreedBoarSecondSpermRowId);
    }

   /**
    * 设置3配种公猪的精液ROW_ID
    * @param BREED_BOAR_THIRD_SPERM_ROW_ID
    */
	public void setBreedBoarThirdSpermRowId(Long value) {
        set(D_BreedBoarThirdSpermRowId,value);
    }
	
	/**
    * 获取3配种公猪的精液ROW_ID
    * @return BREED_BOAR_THIRD_SPERM_ROW_ID
    */
    public Long getBreedBoarThirdSpermRowId() {
        return getLong(D_BreedBoarThirdSpermRowId);
    }

	/**
    * 设置静立分
    * @param JL_SCORE
    */
	public void setJlScore(String value) {
        set(D_JlScore,value);
    }
	
   /**
    * 获取静立分
    * @return JL_SCORE
    */
    public String getJlScore() {
        return getString(D_JlScore);
    }

	/**
    * 设置锁住分
    * @param SZ_SCORE
    */
	public void setSzScore(String value) {
        set(D_SzScore,value);
    }
	
   /**
    * 获取锁住分
    * @return SZ_SCORE
    */
    public String getSzScore() {
        return getString(D_SzScore);
    }

	/**
    * 设置倒流分
    * @param DL_SCORE
    */
	public void setDlScore(String value) {
        set(D_DlScore,value);
    }
	
   /**
    * 获取倒流分
    * @return DL_SCORE
    */
    public String getDlScore() {
        return getString(D_DlScore);
    }

	/**
    * 设置精液质量
    * @param SEMEN_QUALITY
    */
	public void setSemenQuality(String value) {
        set(D_SemenQuality,value);
    }
	
   /**
    * 获取精液质量
    * @return SEMEN_QUALITY
    */
    public String getSemenQuality() {
        return getString(D_SemenQuality);
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
        propertes.add("lineNumber");
        propertes.add("swineryId");
        propertes.add("pigId");
        propertes.add("lineId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("billId");
        propertes.add("worker");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("proNo");
        propertes.add("parity");
        propertes.add("dayAge");
        propertes.add("lastStatus");
        propertes.add("breedType");
        propertes.add("breedDateFirst");
        propertes.add("breedBoarFirst");
        propertes.add("breedDateSecond");
        propertes.add("breedBoarSecond");
        propertes.add("breedDateThird");
        propertes.add("breedBoarThird");
        propertes.add("breedBoarFirstMaterialId");
        propertes.add("breedBoarFirstSpermRowId");
        propertes.add("breedBoarSecondSpermRowId");
        propertes.add("breedBoarThirdSpermRowId");
        propertes.add("jlScore");
        propertes.add("szScore");
        propertes.add("dlScore");
        propertes.add("semenQuality");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




