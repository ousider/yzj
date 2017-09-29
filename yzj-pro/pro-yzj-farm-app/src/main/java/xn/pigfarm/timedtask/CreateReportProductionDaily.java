package xn.pigfarm.timedtask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.data.SqlCon;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.SpringContextUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.backend.SysReportProductionDailyMapper;
import xn.pigfarm.model.backend.SysReportProductionDailyModel;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.util.constants.WechatConstants;

/**
 * @Description: 定时任务：生成综合生产日报
 * @author THL
 * @date 2016年6月29日 下午3:08:54
 */
public class CreateReportProductionDaily implements Job {

    private static Logger log = Logger.getLogger(CreateReportProductionDaily.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行CreateReportProductionDaily...........");
        Calendar calendar = Calendar.getInstance();

        Date currentDate = calendar.getTime();
        String currentDateString = null;
        try {
            currentDateString = TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT);
        }
        catch (Exception e) {
            // e.printStackTrace();
        }

        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);
        SqlCon farmSqlCon = new SqlCon();
        farmSqlCon.addMainSql("SELECT ROW_ID AS farmId FROM HR_M_COMPANY");
        farmSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND COMPANY_MARK LIKE '1,2,%'");
        farmSqlCon.addMainSql(" AND ROW_ID <> 469 AND EXISTS(");
        farmSqlCon.addMainSql(" SELECT 1 FROM PP_L_PIG WHERE DELETED_FLAG = '0' AND FARM_ID = HR_M_COMPANY.ROW_ID LIMIT 1)");

        List<Map<String, Object>> farmList = paramMapperSetSQL(paramMapper, farmSqlCon);

        List<SysReportProductionDailyModel> sysReportProductionDailyModelList = new ArrayList<SysReportProductionDailyModel>();

        for (Map<String, Object> farmMap : farmList) {
            long farmId = Maps.getLong(farmMap, "farmId");

            SysReportProductionDailyModel sysReportProductionDailyModel = new SysReportProductionDailyModel();
            sysReportProductionDailyModel.setStatus(CommonConstants.STATUS);
            sysReportProductionDailyModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            sysReportProductionDailyModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            sysReportProductionDailyModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            sysReportProductionDailyModel.setReportDate(currentDate);
            sysReportProductionDailyModel.setFarmId(farmId);

            // 繁殖-配种-生产
            sysReportProductionDailyModel.setBreedPzSc(getBreedPzScAndBreedPzHb(paramMapper, farmId, currentDateString,
                    " AND T1.LAST_STATUS >= '5'"));
            // 繁殖-配种-后备
            sysReportProductionDailyModel.setBreedPzHb(getBreedPzScAndBreedPzHb(paramMapper, farmId, currentDateString, " AND T1.LAST_STATUS = '4'"));

            List<Map<String, Object>> breedFqAndBreedLcAndBreedKhList = getBreedFqAndBreedLcAndBreedKh(paramMapper, farmId, currentDateString);

            if (breedFqAndBreedLcAndBreedKhList.isEmpty()) {
                // 繁殖-返情
                sysReportProductionDailyModel.setBreedFq(0L);
                // 繁殖-流产
                sysReportProductionDailyModel.setBreedLc(0L);
                // 繁殖-空怀
                sysReportProductionDailyModel.setBreedKh(0L);
            } else {
                // 繁殖-返情
                sysReportProductionDailyModel.setBreedFq(Maps.getLong(breedFqAndBreedLcAndBreedKhList.get(0), "breedFq"));
                // 繁殖-流产
                sysReportProductionDailyModel.setBreedLc(Maps.getLong(breedFqAndBreedLcAndBreedKhList.get(0), "breedLc"));
                // 繁殖-空怀
                sysReportProductionDailyModel.setBreedKh(Maps.getLong(breedFqAndBreedLcAndBreedKhList.get(0), "breedKh"));
            }
            // 繁殖-断奶
            sysReportProductionDailyModel.setBreedDn(getBreedDn(paramMapper, farmId, currentDateString));

            List<Map<String, Object>> deliveryInfoList = getDeliveryInfo(paramMapper, farmId, currentDateString);
            if (deliveryInfoList.isEmpty()) {
                // 产仔-产窝数
                sysReportProductionDailyModel.setDeliveryCws(0L);
                // 产仔-健仔
                sysReportProductionDailyModel.setDeliveryJz(0L);
                // 产仔-弱仔
                sysReportProductionDailyModel.setDeliveryRz(0L);
                // 产仔-死木畸
                sysReportProductionDailyModel.setDeliverySmj(0L);
                // 产仔-窝均健仔
                sysReportProductionDailyModel.setDeliveryWjjz(0D);
            } else {
                // 产仔-产窝数
                sysReportProductionDailyModel.setDeliveryCws(Maps.getLong(deliveryInfoList.get(0), "deliveryCws"));
                // 产仔-健仔
                sysReportProductionDailyModel.setDeliveryJz(Maps.getLong(deliveryInfoList.get(0), "deliveryJz"));
                // 产仔-弱仔
                sysReportProductionDailyModel.setDeliveryRz(Maps.getLong(deliveryInfoList.get(0), "deliveryRz"));
                // 产仔-死木畸
                sysReportProductionDailyModel.setDeliverySmj(Maps.getLong(deliveryInfoList.get(0), "deliverySmj"));
                // 产仔-窝均健仔
                sysReportProductionDailyModel.setDeliveryWjjz(Maps.getDouble(deliveryInfoList.get(0), "deliveryWjjz"));
            }
            List<Map<String, Object>> dieInfoList = getDieInfo(paramMapper, farmId, currentDateString);
            if (dieInfoList.isEmpty()) {
                // 死亡-产房
                sysReportProductionDailyModel.setDeliveryCf(0L);
                // 死亡-保育
                sysReportProductionDailyModel.setDeliveryBy(0L);
                // 死亡-育肥
                sysReportProductionDailyModel.setDeliveryYf(0L);
                // 死亡-种猪
                sysReportProductionDailyModel.setDeliveryZz(0L);
                // 死亡-合计
                sysReportProductionDailyModel.setDeliveryTotal(0L);
            } else {
                // 死亡-产房
                sysReportProductionDailyModel.setDeliveryCf(Maps.getLong(dieInfoList.get(0), "deliveryCf"));
                // 死亡-保育
                sysReportProductionDailyModel.setDeliveryBy(Maps.getLong(dieInfoList.get(0), "deliveryBy"));
                // 死亡-育肥
                sysReportProductionDailyModel.setDeliveryYf(Maps.getLong(dieInfoList.get(0), "deliveryYf"));
                // 死亡-种猪
                sysReportProductionDailyModel.setDeliveryZz(Maps.getLong(dieInfoList.get(0), "deliveryZz"));
                // 死亡-合计
                long totalNum = Maps.getLong(dieInfoList.get(0), "deliveryCf") + Maps.getLong(dieInfoList.get(0), "deliveryBy") + Maps.getLong(
                        dieInfoList.get(0), "deliveryYf") + Maps.getLong(dieInfoList.get(0), "deliveryZz");
                sysReportProductionDailyModel.setDeliveryTotal(totalNum);
            }
            List<Map<String, Object>> saleInfoList = getSaleInfo(paramMapper, farmId, currentDateString);
            if (saleInfoList.isEmpty()) {
                // 外销-自宰
                sysReportProductionDailyModel.setOutSaleZz(0L);
                // 外销-苗猪
                sysReportProductionDailyModel.setOutSaleMz(0L);
                // 外销-肥猪
                sysReportProductionDailyModel.setOutSaleFz(0L);
                // 外销-残次猪
                sysReportProductionDailyModel.setOutSaleCcz(0L);
                // 外销-种猪销售
                sysReportProductionDailyModel.setOutSaleZzxs(0L);
                // 种猪淘汰
                sysReportProductionDailyModel.setZztt(0L);
                // 内销-商品猪
                sysReportProductionDailyModel.setInSaleSpz(0L);
                // 内销-种猪
                sysReportProductionDailyModel.setInSaleZz(0L);
                // 调拨-商品猪
                sysReportProductionDailyModel.setAllotSpz(0L);
                // 调拨-种猪
                sysReportProductionDailyModel.setAllotZz(0L);
            } else {
                // 外销-自宰
                sysReportProductionDailyModel.setOutSaleZz(Maps.getLong(saleInfoList.get(0), "outSaleZz"));
                // 外销-苗猪
                sysReportProductionDailyModel.setOutSaleMz(Maps.getLong(saleInfoList.get(0), "outSaleMz"));
                // 外销-肥猪
                sysReportProductionDailyModel.setOutSaleFz(Maps.getLong(saleInfoList.get(0), "outSaleFz"));
                // 外销-残次猪
                sysReportProductionDailyModel.setOutSaleCcz(Maps.getLong(saleInfoList.get(0), "outSaleCcz"));
                // 外销-种猪销售
                sysReportProductionDailyModel.setOutSaleZzxs(Maps.getLong(saleInfoList.get(0), "outSaleZzxs"));
                // 种猪淘汰
                sysReportProductionDailyModel.setZztt(Maps.getLong(saleInfoList.get(0), "zztt"));
                // 内销-商品猪
                sysReportProductionDailyModel.setInSaleSpz(Maps.getLong(saleInfoList.get(0), "inSaleSpz"));
                // 内销-种猪
                sysReportProductionDailyModel.setInSaleZz(Maps.getLong(saleInfoList.get(0), "inSaleZz"));
                // 调拨-商品猪
                sysReportProductionDailyModel.setAllotSpz(Maps.getLong(saleInfoList.get(0), "allotSpz"));
                // 调拨-种猪
                sysReportProductionDailyModel.setAllotZz(Maps.getLong(saleInfoList.get(0), "allotZz"));
            }
            // 种猪购入
            sysReportProductionDailyModel.setZzgr(getZzgr(paramMapper, farmId, currentDateString));
            // 每日存栏
            List<Map<String, Object>> dailyHandList = getDailyHandInfo(paramMapper, farmId, currentDateString);
            if (dailyHandList.isEmpty()) {
                // 存栏-哺乳猪
                sysReportProductionDailyModel.setLivesBrz(0L);
                // 存栏-保育猪
                sysReportProductionDailyModel.setLivesByz(0L);
                // 存栏-育肥猪
                sysReportProductionDailyModel.setLivesYfz(0L);
                // 存栏-生产母猪
                sysReportProductionDailyModel.setLivesScmz(0L);
                // 存栏-生产公猪
                sysReportProductionDailyModel.setLivesScgz(0L);
                // 存栏-后备母猪
                sysReportProductionDailyModel.setLivesHbmz(0L);
                // 存栏-后备公猪
                sysReportProductionDailyModel.setLivesHbgz(0L);
                // 存栏-淘汰
                sysReportProductionDailyModel.setLivesTt(0L);
                // 总存栏
                sysReportProductionDailyModel.setLivesTotal(0L);
            } else {
                // 存栏-哺乳猪
                long livesBrz = 0L;
                if (Maps.getString(dailyHandList.get(0), "BR_PIG_QTY") != null) {
                    livesBrz = Maps.getLong(dailyHandList.get(0), "BR_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesBrz(livesBrz);
                // 存栏-保育猪
                long livesByz = 0L;
                if (Maps.getString(dailyHandList.get(0), "BY_PIG_QTY") != null) {
                    livesByz = Maps.getLong(dailyHandList.get(0), "BY_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesByz(livesByz);
                // 存栏-育肥猪
                long livesYfz = 0L;
                if (Maps.getString(dailyHandList.get(0), "YF_PIG_QTY") != null) {
                    livesYfz = Maps.getLong(dailyHandList.get(0), "YF_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesYfz(livesYfz);
                // 存栏-生产母猪
                long livesScmz = 0L;
                if (Maps.getString(dailyHandList.get(0), "SCM_PIG_QTY") != null) {
                    livesScmz = Maps.getLong(dailyHandList.get(0), "SCM_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesScmz(livesScmz);
                // 存栏-生产公猪
                long livesScgz = 0L;
                if (Maps.getString(dailyHandList.get(0), "SCG_PIG_QTY") != null) {
                    livesScgz = Maps.getLong(dailyHandList.get(0), "SCG_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesScgz(livesScgz);
                // 存栏-后备母猪
                long livesHbmz = 0L;
                if (Maps.getString(dailyHandList.get(0), "HBM_PIG_QTY") != null) {
                    livesHbmz = Maps.getLong(dailyHandList.get(0), "HBM_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesHbmz(livesHbmz);
                // 存栏-后备公猪
                long livesHbgz = 0L;
                if (Maps.getString(dailyHandList.get(0), "HBG_PIG_QTY") != null) {
                    livesHbgz = Maps.getLong(dailyHandList.get(0), "HBG_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesHbgz(livesHbgz);
                // 存栏-淘汰
                long livesTt = 0L;
                if (Maps.getString(dailyHandList.get(0), "TT_PIG_QTY") != null) {
                    livesTt = Maps.getLong(dailyHandList.get(0), "TT_PIG_QTY");
                }
                sysReportProductionDailyModel.setLivesTt(livesTt);
                // 总存栏
                long livesTotal = livesBrz + livesByz + livesYfz + livesScmz + livesScgz + livesHbmz + livesHbgz + livesTt;
                sysReportProductionDailyModel.setLivesTotal(livesTotal);
            }
            // 创建人
            sysReportProductionDailyModel.setCreateId(0L);
            // 创建时间
            sysReportProductionDailyModel.setCreateDatetime(TimeUtil.getSysTimestamp());

            sysReportProductionDailyModelList.add(sysReportProductionDailyModel);
        }


        SysReportProductionDailyMapper sysReportProductionDailyMapper = SpringContextUtil.getBean("sysReportProductionDailyMapper",
                SysReportProductionDailyMapper.class);
        sysReportProductionDailyMapper.inserts(sysReportProductionDailyModelList);
        this.sendProductionDailyMessge();
        log.info("结束执行CreateReportProductionDaily...........");
    }

    // 配种
    private long getBreedPzScAndBreedPzHb(IParamMapper paramMapper, long farmId, String currentDateString, String statusSql) {
        SqlCon breedPzScSqlCon = new SqlCon();
        breedPzScSqlCon.addMainSql("SELECT COUNT(*) qty");
        breedPzScSqlCon.addMainSql(" FROM pp_l_bill_breed T1");
        breedPzScSqlCon.addMainSql(" INNER JOIN pp_v_pig T2 ");
        breedPzScSqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1'");
        breedPzScSqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID)");
        breedPzScSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        breedPzScSqlCon.addConditionWithNull(farmId, " AND T1.FARM_ID = ?");
        breedPzScSqlCon.addCondition(currentDateString, " AND T1.BREED_DATE_FIRST = ?");
        breedPzScSqlCon.addMainSql(statusSql);
        breedPzScSqlCon.addMainSql(" GROUP BY DATE(T1.BREED_DATE_FIRST)");

        List<Map<String, Object>> breedPzScList = paramMapperSetSQL(paramMapper, breedPzScSqlCon);
        long breedPzSc = 0L;
        if (!breedPzScList.isEmpty()) {
            breedPzSc = Maps.getLong(breedPzScList.get(0), "qty");
        }

        return breedPzSc;
    }

    // 妊娠检查
    private List<Map<String, Object>> getBreedFqAndBreedLcAndBreedKh(IParamMapper paramMapper, long farmId, String currentDateString){
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT SUM(IF(PREGNANCY_RESULT = 5,1,0)) breedLc,");
        sqlCon.addMainSql(" SUM(IF(PREGNANCY_RESULT IN(2,6),1,0)) breedKh,");
        sqlCon.addMainSql(" SUM(IF(PREGNANCY_RESULT = 4,1,0)) breedFq");
        sqlCon.addMainSql(" FROM PP_L_BILL_PREGNANCY_CHECK");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");
        sqlCon.addMainSql(" AND PREGNANCY_RESULT NOT IN('1','3')");
        sqlCon.addCondition(currentDateString, " AND PREGNANCY_DATE = ?");
        sqlCon.addMainSql(" GROUP BY PREGNANCY_DATE");

        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);

        return list;
    }

    // 断奶
    private long getBreedDn(IParamMapper paramMapper, long farmId, String currentDateString){
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT COUNT(1) BreedDn");
        sqlCon.addMainSql(" FROM PP_L_BILL_WEAN T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2");
        sqlCon.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.BILL_ID = T2.BILL_ID AND T1.PIG_ID = T2.PIG_ID ");
        sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T2.CHANGE_TYPE = '8' ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addConditionWithNull(farmId, " AND T1.FARM_ID = ?");
        sqlCon.addCondition(currentDateString, " AND T1.WEAN_DATE = ?");
        sqlCon.addMainSql(" GROUP BY T1.WEAN_DATE");

        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);
        long qty = 0L;
        if (!list.isEmpty()) {
            qty = Maps.getLong(list.get(0), "BreedDn");
        }

        return qty;
    }

    // 产仔
    private List<Map<String, Object>> getDeliveryInfo(IParamMapper paramMapper, long farmId, String currentDateString) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT COUNT(*) deliveryCws,");
        sqlCon.addMainSql(" SUM(HEALTHY_NUM) deliveryJz,");
        sqlCon.addMainSql(" SUM(WEAK_NUM) deliveryRz,");
        sqlCon.addMainSql(" SUM(IFNULL(STILLBIRTH_NUM,0) + IFNULL(MUTANT_NUM,0) + IFNULL(MUMMY_NUM,0) + IFNULL(BLACK_NUM,0)) deliverySmj,");
        sqlCon.addMainSql(" SUM(HEALTHY_NUM)/IF(COUNT(*) IS NULL OR COUNT(*) <= 0 ,1,COUNT(*)) deliveryWjjz ");
        sqlCon.addMainSql(" FROM PP_L_BILL_DELIVERY");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");
        sqlCon.addCondition(currentDateString, " AND DELIVERY_DATE = ?");
        sqlCon.addMainSql(" GROUP BY DELIVERY_DATE");

        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);

        return list;
    }

    // 死亡
    private List<Map<String, Object>> getDieInfo(IParamMapper paramMapper, long farmId, String currentDateString) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT SUM(IF((LEAVE_TYPE = 3 AND  PIG_CLASS = '12' OR (  PIG_CLASS = '13' AND _getSettingValueByCode(FARM_ID,'RZBSCL') = 'OFF')) OR (LEAVE_TYPE = 3 AND PIG_CLASS = '14'),1,0)) deliveryCf,");
        sqlCon.addMainSql(" SUM(IF(LEAVE_TYPE = 4 AND PIG_CLASS = '15',1,0)) deliveryBy,");
        sqlCon.addMainSql(" SUM(IF(LEAVE_TYPE = 4 AND PIG_CLASS = '16',1,0)) deliveryYf,");
        sqlCon.addMainSql(" SUM(IF(LEAVE_TYPE = 2,1,0)) deliveryZz ");
        sqlCon.addMainSql(" FROM PP_L_BILL_LEAVE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");
        sqlCon.addCondition(currentDateString, " AND LEAVE_DATE = ?");
        sqlCon.addMainSql(" GROUP BY LEAVE_DATE");

        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);

        return list;
    }

    // 销售
    private List<Map<String, Object>> getSaleInfo(IParamMapper paramMapper, long farmId, String currentDateString) {
        SqlCon sqlCon = new SqlCon();
        // 外销-自宰
        sqlCon.addMainSql(" SELECT IF(T0.SALE_TYPE = 4 AND T1.SAP_SALE_TYPE = 2 ,SUM(T1.SALE_NUM),0) outSaleZz, ");
        // 外销-苗猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 1 AND T1.SALE_DESCRIBE = 4 AND T0.SAP_SALE_TYPE = 2,SUM(T1.SALE_NUM),0) outSaleMz, ");
        // 外销-肥猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 1 AND T1.SALE_DESCRIBE = 3 AND T0.SAP_SALE_TYPE = 2 ,SUM(T1.SALE_NUM),0) outSaleFz,");
        // 外销-残次猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 1 AND T1.SALE_DESCRIBE = 5 AND T0.SAP_SALE_TYPE = 2,SUM(T1.SALE_NUM),0) outSaleCcz,");
        // 外销-种猪销售
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 2 AND T0.SAP_SALE_TYPE = 2,SUM(T1.SALE_NUM),0) outSaleZzxs,");
        // 种猪淘汰
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 3 AND T0.SAP_SALE_TYPE = 2,SUM(T1.SALE_NUM),0) zztt,");
        // 内销-商品猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 1 AND T0.SAP_SALE_TYPE = 1,SUM(T1.SALE_NUM),0) inSaleSpz,");
        // 内销-种猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE IN(2,3) AND T0.SAP_SALE_TYPE = 1,SUM(T1.SALE_NUM),0) inSaleZz,");
        // 调拨-商品猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE = 1 AND T0.SAP_SALE_TYPE = 3,SUM(T1.SALE_NUM),0) allotSpz,");
        // 调拨-种猪
        sqlCon.addMainSql(" IF(T0.SALE_TYPE IN(2,3) AND T0.SAP_SALE_TYPE = 3,SUM(T1.SALE_NUM),0) allotZz");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T1.FARM_ID AND T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        sqlCon.addConditionWithNull(farmId, " AND T0.FARM_ID = ?");
        sqlCon.addCondition(currentDateString, " AND T1.SALE_DATE = ?");
        sqlCon.addMainSql(" GROUP BY T0.SALE_TYPE,T1.SALE_DATE, T1.SALE_DESCRIBE ,T0.SAP_SALE_TYPE");

        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);

        return list;
    }

    // 种猪购入
    private long getZzgr(IParamMapper paramMapper, long farmId, String currentDateString) {
        SqlCon zzgrSqlCon = new SqlCon();
        zzgrSqlCon.addMainSql("SELECT COUNT(*) qty");
        zzgrSqlCon.addMainSql(" FROM pp_l_pig T0 ");
        zzgrSqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_TYPE BETWEEN 1 AND 2 AND T0.ORIGIN <> 3 ");
        zzgrSqlCon.addConditionWithNull(farmId, " AND T0.FARM_ID = ?");
        zzgrSqlCon.addCondition(currentDateString, " AND T0.ENTER_DATE = ?");
        zzgrSqlCon.addMainSql(" GROUP BY T0.ENTER_DATE");

        List<Map<String, Object>> zzgrScList = paramMapperSetSQL(paramMapper, zzgrSqlCon);
        long zzgr = 0L;
        if (!zzgrScList.isEmpty()) {
            zzgr = Maps.getLong(zzgrScList.get(0), "qty");
        }

        return zzgr;
    }

    // 每日存栏
    private List<Map<String, Object>> getDailyHandInfo(IParamMapper paramMapper, long farmId, String currentDateString) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("CALL P_ProductionDaily (");
        // 开始时间
        sqlCon.addConditionWithNull(currentDateString, " ? ,");
        // 结束时间
        sqlCon.addConditionWithNull(currentDateString, " ? ,");
        // 猪场ID
        sqlCon.addConditionWithNull(farmId, " ? )");
        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);

        return list;
    }
    private List<Map<String, Object>> paramMapperSetSQL(IParamMapper paramMapper, SqlCon sqlCon) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void sendProductionDailyMessge() {
        log.info("开始推送消息...........");
        IBackEndService backEndServiceImpl = SpringContextUtil.getBean("backEndService", IBackEndService.class);

        List<Map<String, Object>> messageMap = new ArrayList<>();
        try {
            messageMap = backEndServiceImpl.searchMessageByCode(WechatConstants.MESSAGE_TYPE_PRODUCTION_DAILY);
            if (messageMap.size() > 0) {
                List<Map<String, Object>> userList = backEndServiceImpl.searchUserIdByMessageId(Maps.getLong(messageMap.get(0), "rowId"));
                String userString = "";
                for (int i = 0; i < userList.size(); i++) {
                    if (i < userList.size() - 1) {
                        userString += Maps.getString(userList.get(i), "QY_USER_ID") + "|";
                    } else {
                        userString += Maps.getString(userList.get(i), "QY_USER_ID");
                    }
                }
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String newDate = df.format(date);
                String title = Maps.getString(messageMap.get(0), "messageTitle");
                String picUrl = Maps.getString(messageMap.get(0), "picUrl");
                String contentUrl = Maps.getString(messageMap.get(0), "contentUrl") + "?reportDate=" + newDate;
                String description = Maps.getString(messageMap.get(0), "description");
                SendWechatMessageTask sendWechatMessageTask = new SendWechatMessageTask();
                sendWechatMessageTask.sendNewsMessageToWechatUser(userString, "", "", title, description, picUrl, contentUrl);
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("结束推送消息...........");
    }
}
