package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-8-3 11:48:00
 *       表：SYS_REPORT_PRODUCTION_DAILY 
 */
public class SysReportProductionDailyModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -5811908737088209162L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //[1]-未确认[2]-人为确认[3]-系统自动确认 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。 
    private static final String D_OriginFlag="originFlag";
	 //数据来源应用的代码 
    private static final String D_OriginApp="originApp";
	 //备注 
    private static final String D_Notes="notes";
	 //报表日期 
    private static final String D_ReportDate="reportDate";
	 //猪场 
    private static final String D_FarmId="farmId";
	 //繁殖-配种-生产 
    private static final String D_BreedPzSc="breedPzSc";
	 //繁殖-配种-后备 
    private static final String D_BreedPzHb="breedPzHb";
	 //繁殖-返情 
    private static final String D_BreedFq="breedFq";
	 //繁殖-流产 
    private static final String D_BreedLc="breedLc";
	 //繁殖-空怀 
    private static final String D_BreedKh="breedKh";
	 //繁殖-断奶 
    private static final String D_BreedDn="breedDn";
	 //产仔-产窝数 
    private static final String D_DeliveryCws="deliveryCws";
	 //产仔-健仔 
    private static final String D_DeliveryJz="deliveryJz";
	 //产仔-弱仔 
    private static final String D_DeliveryRz="deliveryRz";
	 //产仔-死木畸  
    private static final String D_DeliverySmj="deliverySmj";
	 //产仔-窝均健仔 
    private static final String D_DeliveryWjjz="deliveryWjjz";
	 //死亡-产房 
    private static final String D_DeliveryCf="deliveryCf";
	 //死亡-保育 
    private static final String D_DeliveryBy="deliveryBy";
	 //死亡-育肥 
    private static final String D_DeliveryYf="deliveryYf";
	 //死亡-种猪 
    private static final String D_DeliveryZz="deliveryZz";
	 //死亡-合计 
    private static final String D_DeliveryTotal="deliveryTotal";
	 //外销-自宰 
    private static final String D_OutSaleZz="outSaleZz";
	 //外销-苗猪 
    private static final String D_OutSaleMz="outSaleMz";
	 //外销-肥猪 
    private static final String D_OutSaleFz="outSaleFz";
	 //外销-残次猪 
    private static final String D_OutSaleCcz="outSaleCcz";
	 //外销-种猪销售 
    private static final String D_OutSaleZzxs="outSaleZzxs";
	 //种猪购入 
    private static final String D_Zzgr="zzgr";
	 //种猪淘汰 
    private static final String D_Zztt="zztt";
	 //内销-商品猪 
    private static final String D_InSaleSpz="inSaleSpz";
	 //内销-种猪 
    private static final String D_InSaleZz="inSaleZz";
	 //调拨-商品猪 
    private static final String D_AllotSpz="allotSpz";
	 //调拨-种猪 
    private static final String D_AllotZz="allotZz";
	 //存栏-哺乳猪 
    private static final String D_LivesBrz="livesBrz";
	 //存栏-保育猪 
    private static final String D_LivesByz="livesByz";
	 //存栏-育肥猪 
    private static final String D_LivesYfz="livesYfz";
	 //存栏-生产母猪 
    private static final String D_LivesScmz="livesScmz";
	 //存栏-生产公猪 
    private static final String D_LivesScgz="livesScgz";
	 //存栏-后备母猪 
    private static final String D_LivesHbmz="livesHbmz";
	 //存栏-后备公猪 
    private static final String D_LivesHbgz="livesHbgz";
	 //存栏-淘汰 
    private static final String D_LivesTt="livesTt";
	 //总存栏 
    private static final String D_LivesTotal="livesTotal";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建时间 
    private static final String D_CreateDatetime="createDatetime";
	 //确认日期 
    private static final String D_ConfirmDate="confirmDate";
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
    * 设置[1]-未确认[2]-人为确认[3]-系统自动确认
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取[1]-未确认[2]-人为确认[3]-系统自动确认
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
    * 设置报表日期
    * @param REPORT_DATE
    */
	public void setReportDate(Date value) {
        set(D_ReportDate,value);
    }
	
   /**
    * 获取报表日期
    * @return REPORT_DATE
    */
    public Date getReportDate() {
        return getDate(D_ReportDate);
    }

   /**
    * 设置猪场
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取猪场
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置繁殖-配种-生产
    * @param BREED_PZ_SC
    */
	public void setBreedPzSc(Long value) {
        set(D_BreedPzSc,value);
    }
	
	/**
    * 获取繁殖-配种-生产
    * @return BREED_PZ_SC
    */
    public Long getBreedPzSc() {
        return getLong(D_BreedPzSc);
    }

   /**
    * 设置繁殖-配种-后备
    * @param BREED_PZ_HB
    */
	public void setBreedPzHb(Long value) {
        set(D_BreedPzHb,value);
    }
	
	/**
    * 获取繁殖-配种-后备
    * @return BREED_PZ_HB
    */
    public Long getBreedPzHb() {
        return getLong(D_BreedPzHb);
    }

   /**
    * 设置繁殖-返情
    * @param BREED_FQ
    */
	public void setBreedFq(Long value) {
        set(D_BreedFq,value);
    }
	
	/**
    * 获取繁殖-返情
    * @return BREED_FQ
    */
    public Long getBreedFq() {
        return getLong(D_BreedFq);
    }

   /**
    * 设置繁殖-流产
    * @param BREED_LC
    */
	public void setBreedLc(Long value) {
        set(D_BreedLc,value);
    }
	
	/**
    * 获取繁殖-流产
    * @return BREED_LC
    */
    public Long getBreedLc() {
        return getLong(D_BreedLc);
    }

   /**
    * 设置繁殖-空怀
    * @param BREED_KH
    */
	public void setBreedKh(Long value) {
        set(D_BreedKh,value);
    }
	
	/**
    * 获取繁殖-空怀
    * @return BREED_KH
    */
    public Long getBreedKh() {
        return getLong(D_BreedKh);
    }

   /**
    * 设置繁殖-断奶
    * @param BREED_DN
    */
	public void setBreedDn(Long value) {
        set(D_BreedDn,value);
    }
	
	/**
    * 获取繁殖-断奶
    * @return BREED_DN
    */
    public Long getBreedDn() {
        return getLong(D_BreedDn);
    }

   /**
    * 设置产仔-产窝数
    * @param DELIVERY_CWS
    */
	public void setDeliveryCws(Long value) {
        set(D_DeliveryCws,value);
    }
	
	/**
    * 获取产仔-产窝数
    * @return DELIVERY_CWS
    */
    public Long getDeliveryCws() {
        return getLong(D_DeliveryCws);
    }

   /**
    * 设置产仔-健仔
    * @param DELIVERY_JZ
    */
	public void setDeliveryJz(Long value) {
        set(D_DeliveryJz,value);
    }
	
	/**
    * 获取产仔-健仔
    * @return DELIVERY_JZ
    */
    public Long getDeliveryJz() {
        return getLong(D_DeliveryJz);
    }

   /**
    * 设置产仔-弱仔
    * @param DELIVERY_RZ
    */
	public void setDeliveryRz(Long value) {
        set(D_DeliveryRz,value);
    }
	
	/**
    * 获取产仔-弱仔
    * @return DELIVERY_RZ
    */
    public Long getDeliveryRz() {
        return getLong(D_DeliveryRz);
    }

   /**
    * 设置产仔-死木畸 
    * @param DELIVERY_SMJ
    */
	public void setDeliverySmj(Long value) {
        set(D_DeliverySmj,value);
    }
	
	/**
    * 获取产仔-死木畸 
    * @return DELIVERY_SMJ
    */
    public Long getDeliverySmj() {
        return getLong(D_DeliverySmj);
    }

	/**
    * 设置产仔-窝均健仔
    * @param DELIVERY_WJJZ
    */
	public void setDeliveryWjjz(Double value) {
        set(D_DeliveryWjjz,value);
    }
	
   /**
    * 获取产仔-窝均健仔
    * @return DELIVERY_WJJZ
    */
    public Double getDeliveryWjjz() {
        return getDouble(D_DeliveryWjjz);
    }

   /**
    * 设置死亡-产房
    * @param DELIVERY_CF
    */
	public void setDeliveryCf(Long value) {
        set(D_DeliveryCf,value);
    }
	
	/**
    * 获取死亡-产房
    * @return DELIVERY_CF
    */
    public Long getDeliveryCf() {
        return getLong(D_DeliveryCf);
    }

   /**
    * 设置死亡-保育
    * @param DELIVERY_BY
    */
	public void setDeliveryBy(Long value) {
        set(D_DeliveryBy,value);
    }
	
	/**
    * 获取死亡-保育
    * @return DELIVERY_BY
    */
    public Long getDeliveryBy() {
        return getLong(D_DeliveryBy);
    }

   /**
    * 设置死亡-育肥
    * @param DELIVERY_YF
    */
	public void setDeliveryYf(Long value) {
        set(D_DeliveryYf,value);
    }
	
	/**
    * 获取死亡-育肥
    * @return DELIVERY_YF
    */
    public Long getDeliveryYf() {
        return getLong(D_DeliveryYf);
    }

   /**
    * 设置死亡-种猪
    * @param DELIVERY_ZZ
    */
	public void setDeliveryZz(Long value) {
        set(D_DeliveryZz,value);
    }
	
	/**
    * 获取死亡-种猪
    * @return DELIVERY_ZZ
    */
    public Long getDeliveryZz() {
        return getLong(D_DeliveryZz);
    }

   /**
    * 设置死亡-合计
    * @param DELIVERY_TOTAL
    */
	public void setDeliveryTotal(Long value) {
        set(D_DeliveryTotal,value);
    }
	
	/**
    * 获取死亡-合计
    * @return DELIVERY_TOTAL
    */
    public Long getDeliveryTotal() {
        return getLong(D_DeliveryTotal);
    }

   /**
    * 设置外销-自宰
    * @param OUT_SALE_ZZ
    */
	public void setOutSaleZz(Long value) {
        set(D_OutSaleZz,value);
    }
	
	/**
    * 获取外销-自宰
    * @return OUT_SALE_ZZ
    */
    public Long getOutSaleZz() {
        return getLong(D_OutSaleZz);
    }

   /**
    * 设置外销-苗猪
    * @param OUT_SALE_MZ
    */
	public void setOutSaleMz(Long value) {
        set(D_OutSaleMz,value);
    }
	
	/**
    * 获取外销-苗猪
    * @return OUT_SALE_MZ
    */
    public Long getOutSaleMz() {
        return getLong(D_OutSaleMz);
    }

   /**
    * 设置外销-肥猪
    * @param OUT_SALE_FZ
    */
	public void setOutSaleFz(Long value) {
        set(D_OutSaleFz,value);
    }
	
	/**
    * 获取外销-肥猪
    * @return OUT_SALE_FZ
    */
    public Long getOutSaleFz() {
        return getLong(D_OutSaleFz);
    }

   /**
    * 设置外销-残次猪
    * @param OUT_SALE_CCZ
    */
	public void setOutSaleCcz(Long value) {
        set(D_OutSaleCcz,value);
    }
	
	/**
    * 获取外销-残次猪
    * @return OUT_SALE_CCZ
    */
    public Long getOutSaleCcz() {
        return getLong(D_OutSaleCcz);
    }

   /**
    * 设置外销-种猪销售
    * @param OUT_SALE_ZZXS
    */
	public void setOutSaleZzxs(Long value) {
        set(D_OutSaleZzxs,value);
    }
	
	/**
    * 获取外销-种猪销售
    * @return OUT_SALE_ZZXS
    */
    public Long getOutSaleZzxs() {
        return getLong(D_OutSaleZzxs);
    }

   /**
    * 设置种猪购入
    * @param ZZGR
    */
	public void setZzgr(Long value) {
        set(D_Zzgr,value);
    }
	
	/**
    * 获取种猪购入
    * @return ZZGR
    */
    public Long getZzgr() {
        return getLong(D_Zzgr);
    }

   /**
    * 设置种猪淘汰
    * @param ZZTT
    */
	public void setZztt(Long value) {
        set(D_Zztt,value);
    }
	
	/**
    * 获取种猪淘汰
    * @return ZZTT
    */
    public Long getZztt() {
        return getLong(D_Zztt);
    }

   /**
    * 设置内销-商品猪
    * @param IN_SALE_SPZ
    */
	public void setInSaleSpz(Long value) {
        set(D_InSaleSpz,value);
    }
	
	/**
    * 获取内销-商品猪
    * @return IN_SALE_SPZ
    */
    public Long getInSaleSpz() {
        return getLong(D_InSaleSpz);
    }

   /**
    * 设置内销-种猪
    * @param IN_SALE_ZZ
    */
	public void setInSaleZz(Long value) {
        set(D_InSaleZz,value);
    }
	
	/**
    * 获取内销-种猪
    * @return IN_SALE_ZZ
    */
    public Long getInSaleZz() {
        return getLong(D_InSaleZz);
    }

   /**
    * 设置调拨-商品猪
    * @param ALLOT_SPZ
    */
	public void setAllotSpz(Long value) {
        set(D_AllotSpz,value);
    }
	
	/**
    * 获取调拨-商品猪
    * @return ALLOT_SPZ
    */
    public Long getAllotSpz() {
        return getLong(D_AllotSpz);
    }

   /**
    * 设置调拨-种猪
    * @param ALLOT_ZZ
    */
	public void setAllotZz(Long value) {
        set(D_AllotZz,value);
    }
	
	/**
    * 获取调拨-种猪
    * @return ALLOT_ZZ
    */
    public Long getAllotZz() {
        return getLong(D_AllotZz);
    }

   /**
    * 设置存栏-哺乳猪
    * @param LIVES_BRZ
    */
	public void setLivesBrz(Long value) {
        set(D_LivesBrz,value);
    }
	
	/**
    * 获取存栏-哺乳猪
    * @return LIVES_BRZ
    */
    public Long getLivesBrz() {
        return getLong(D_LivesBrz);
    }

   /**
    * 设置存栏-保育猪
    * @param LIVES_BYZ
    */
	public void setLivesByz(Long value) {
        set(D_LivesByz,value);
    }
	
	/**
    * 获取存栏-保育猪
    * @return LIVES_BYZ
    */
    public Long getLivesByz() {
        return getLong(D_LivesByz);
    }

   /**
    * 设置存栏-育肥猪
    * @param LIVES_YFZ
    */
	public void setLivesYfz(Long value) {
        set(D_LivesYfz,value);
    }
	
	/**
    * 获取存栏-育肥猪
    * @return LIVES_YFZ
    */
    public Long getLivesYfz() {
        return getLong(D_LivesYfz);
    }

   /**
    * 设置存栏-生产母猪
    * @param LIVES_SCMZ
    */
	public void setLivesScmz(Long value) {
        set(D_LivesScmz,value);
    }
	
	/**
    * 获取存栏-生产母猪
    * @return LIVES_SCMZ
    */
    public Long getLivesScmz() {
        return getLong(D_LivesScmz);
    }

   /**
    * 设置存栏-生产公猪
    * @param LIVES_SCGZ
    */
	public void setLivesScgz(Long value) {
        set(D_LivesScgz,value);
    }
	
	/**
    * 获取存栏-生产公猪
    * @return LIVES_SCGZ
    */
    public Long getLivesScgz() {
        return getLong(D_LivesScgz);
    }

   /**
    * 设置存栏-后备母猪
    * @param LIVES_HBMZ
    */
	public void setLivesHbmz(Long value) {
        set(D_LivesHbmz,value);
    }
	
	/**
    * 获取存栏-后备母猪
    * @return LIVES_HBMZ
    */
    public Long getLivesHbmz() {
        return getLong(D_LivesHbmz);
    }

   /**
    * 设置存栏-后备公猪
    * @param LIVES_HBGZ
    */
	public void setLivesHbgz(Long value) {
        set(D_LivesHbgz,value);
    }
	
	/**
    * 获取存栏-后备公猪
    * @return LIVES_HBGZ
    */
    public Long getLivesHbgz() {
        return getLong(D_LivesHbgz);
    }

   /**
    * 设置存栏-淘汰
    * @param LIVES_TT
    */
	public void setLivesTt(Long value) {
        set(D_LivesTt,value);
    }
	
	/**
    * 获取存栏-淘汰
    * @return LIVES_TT
    */
    public Long getLivesTt() {
        return getLong(D_LivesTt);
    }

   /**
    * 设置总存栏
    * @param LIVES_TOTAL
    */
	public void setLivesTotal(Long value) {
        set(D_LivesTotal,value);
    }
	
	/**
    * 获取总存栏
    * @return LIVES_TOTAL
    */
    public Long getLivesTotal() {
        return getLong(D_LivesTotal);
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
    * 设置创建时间
    * @param CREATE_DATETIME
    */
    public void setCreateDatetime(Date value) {
        set(D_CreateDatetime,value);
    }
	
   /**
    * 获取创建时间
    * @return CREATE_DATETIME
    */
    public Date getCreateDatetime() {
        return getDate(D_CreateDatetime);
    }

	/**
    * 设置确认日期
    * @param CONFIRM_DATE
    */
    public void setConfirmDate(Date value) {
        set(D_ConfirmDate,value);
    }
	
   /**
    * 获取确认日期
    * @return CONFIRM_DATE
    */
    public Date getConfirmDate() {
        return getTimestamp(D_ConfirmDate);
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
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("reportDate");
        propertes.add("farmId");
        propertes.add("breedPzSc");
        propertes.add("breedPzHb");
        propertes.add("breedFq");
        propertes.add("breedLc");
        propertes.add("breedKh");
        propertes.add("breedDn");
        propertes.add("deliveryCws");
        propertes.add("deliveryJz");
        propertes.add("deliveryRz");
        propertes.add("deliverySmj");
        propertes.add("deliveryWjjz");
        propertes.add("deliveryCf");
        propertes.add("deliveryBy");
        propertes.add("deliveryYf");
        propertes.add("deliveryZz");
        propertes.add("deliveryTotal");
        propertes.add("outSaleZz");
        propertes.add("outSaleMz");
        propertes.add("outSaleFz");
        propertes.add("outSaleCcz");
        propertes.add("outSaleZzxs");
        propertes.add("zzgr");
        propertes.add("zztt");
        propertes.add("inSaleSpz");
        propertes.add("inSaleZz");
        propertes.add("allotSpz");
        propertes.add("allotZz");
        propertes.add("livesBrz");
        propertes.add("livesByz");
        propertes.add("livesYfz");
        propertes.add("livesScmz");
        propertes.add("livesScgz");
        propertes.add("livesHbmz");
        propertes.add("livesHbgz");
        propertes.add("livesTt");
        propertes.add("livesTotal");
        propertes.add("createId");
        propertes.add("createDatetime");
        propertes.add("confirmDate");
        propertes.add("ftriggerFlag");
        propertes.add("requestid");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




