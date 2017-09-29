package xn.pigfarm.service.hana.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaMtcPgonhd;
import xn.hana.model.common.HanaMtcPgonhdDetail;
import xn.hana.model.common.HanaMtcPgstat;
import xn.hana.model.common.HanaMtcPgstatDetail;
import xn.hana.model.common.HanaMtcPgstatDtl;
import xn.hana.model.common.HanaMtcPgstatDtlDetail;
import xn.hana.model.common.HanaMtcPgtsdtl;
import xn.hana.model.common.HanaMtcPgtsdtlDetail;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.hana.SapMtcItfcMapper;
import xn.pigfarm.mapper.hana.SapMtcPgonhdMapper;
import xn.pigfarm.mapper.hana.SapMtcPgstatDtlMapper;
import xn.pigfarm.mapper.hana.SapMtcPgstatMapper;
import xn.pigfarm.mapper.hana.SapMtcPgtsdtlMapper;
import xn.pigfarm.mapper.hana.SapMtcSummaryMapper;
import xn.pigfarm.mapper.hana.SysHanaInsertLogMapper;
import xn.pigfarm.model.hana.SapMtcItfcModel;
import xn.pigfarm.model.hana.SapMtcPgonhdModel;
import xn.pigfarm.model.hana.SapMtcPgstatDtlModel;
import xn.pigfarm.model.hana.SapMtcPgstatModel;
import xn.pigfarm.model.hana.SapMtcPgtsdtlModel;
import xn.pigfarm.model.hana.SapMtcSummaryModel;
import xn.pigfarm.model.hana.SysHanaInsertLogModel;
import xn.pigfarm.service.hana.ISendToSAPProductionService;
import xn.pigfarm.util.constants.PigConstants;

@Service("sendToSAPProductionService")
public class SendToSAPProductionServiceImpl extends BaseServiceImpl implements ISendToSAPProductionService {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private SysHanaInsertLogMapper sysHanaInsertLogMapper;

    @Autowired
    private SapMtcItfcMapper sapMtcItfcMapper;

    @Autowired
    private SapMtcPgonhdMapper sapMtcPgonhdMapper;

    @Autowired
    private SapMtcPgstatDtlMapper sapMtcPgstatDtlMapper;

    @Autowired
    private SapMtcPgstatMapper sapMtcPgstatMapper;

    @Autowired
    private SapMtcPgtsdtlMapper sapMtcPgtsdtlMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private SapMtcSummaryMapper sapMtcSummaryMapper;

    @Override
    public Object insertSAPDateBase(Long code) throws Exception {
        Object lp = null;
        Map<String, String> startAndEndDate = getStartAndEndDate();
        String startDate = startAndEndDate.get("startDate");
        String endDate = startAndEndDate.get("endDate");
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        if (isToHana) {

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            if (code == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "缺少参数CODE,无法区分表名。。。");
            }

            // 设置上传时间,用于限制事件操作
            // editSendSapDate(endDate, event);

            switch (Integer.valueOf(code.toString())) {
            case 9997:
                // 日报 9997
                lp = sendToSapMtcPgonhd(startDate, endDate, mtcBranchID, mtcDeptID, true, false);
                break;
            case 9998:
                // 存栏变动 9998
                lp = sendToSapMtcPgtsdtl(startDate, endDate, mtcBranchID, mtcDeptID, true);
                break;
            case 9999:
                // sap妊娠母猪状态汇总 9999
                lp = sendToSapPgstat(endDate, mtcBranchIDAndMTC_DeptIDMap, true);
                break;
            case 3030:
                // 生产猪每月存栏位置 3030
                lp = sendToSapPgstatDtl(endDate, mtcBranchID, mtcDeptID, true);
                break;
            default:
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "没有这个类型。。。");
                break;
            }

        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "上传SAP为关闭状态。。。");
        }
        return lp;
    }

    @Override
    public Map<String, String> getStartAndEndDate() throws Exception {
        Date currentDate = new Date();

        Map<String, String> map = new HashMap<>();
        // 获取上月最后一天
        String lastMothByCurrent = TimeUtil.format(TimeUtil.getLastMothByCurrent(currentDate), TimeUtil.DATE_FORMAT);
        // 上月第一天
        String startMothByCurrent = TimeUtil.getFirstDateOfMonth(TimeUtil.format(lastMothByCurrent, TimeUtil.DATE_FORMAT));

        // 本月第一天
        String startTheMoth = TimeUtil.getFirstDateOfMonth(TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT));
        // 本月最后一天
        String endTheMoth = TimeUtil.getLastDateOfMonth(TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT));

        // 本月月末 - 今天
        long compareNow = TimeUtil.compareDate(endTheMoth, TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT), Calendar.DATE);
        // 今天 - 上月月末
        long compareLastDateToNow = TimeUtil.compareDate(lastMothByCurrent, TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT), Calendar.DATE);

        Integer sapMaxDelay = null;
        try {
            sapMaxDelay = Integer.valueOf(paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SAP_MAX_DELAY"));
        }
        catch (Exception e) {
            sapMaxDelay = 0;
        }

        // 同一天
        if (0 == compareNow) {
            map.put("startDate", startTheMoth);
            map.put("endDate", endTheMoth);
            // 距离上月月末 sapMaxDelay 日 内
        } else if (Math.abs(compareLastDateToNow) <= sapMaxDelay) {
            map.put("startDate", startMothByCurrent);
            map.put("endDate", lastMothByCurrent);
            // 超出限制期限
        } else {
            // 超过延时即默认为上月
            map.put("startDate", startMothByCurrent);
            map.put("endDate", lastMothByCurrent);
        }
        return map;
    }

    // 生产猪每月存栏位置 3030
    private List<HanaMtcPgstatDtlDetail> sendToSapPgstatDtl(String endDate, String mtcBranchID, String mtcDeptID, Boolean isSaveSession)
            throws Exception {
        HanaMtcPgstatDtlDetail detail = null;
        List<HanaMtcPgstatDtlDetail> detaillist = new ArrayList<>();

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ");
        sqlCon.addMainSql("   T1.FARM_ID ,T5.HOUSE_ID,T3.SAP_FIXED_ASSETS_EARBRAND,CONCAT(T6.MATERIAL_NAME,'.',T4.EAR_BRAND) PIG_INFO,T1.PARITY, ");
        sqlCon.addMainSql("   IF(T1.PIG_CLASS=24,T2.PIG_CLASS,T1.PIG_CLASS) PIG_CLASS, ");
        sqlCon.addMainSql("   IF(T1.PIG_CLASS=24,T2.TOWORK_DATE,T1.TOWORK_DATE) PIG_CLASS_DATE, ");
        sqlCon.addCondition(endDate, "   TIMESTAMPDIFF(DAY,IF(T1.PIG_CLASS=24,T2.TOWORK_DATE,T1.TOWORK_DATE),?) PIG_CLASS_DAY, ");
        sqlCon.addCondition(endDate, "   IF(T1.PIG_CLASS <> 9,'-',IF(TIMESTAMPDIFF(DAY,IF(T1.PIG_CLASS=24,T2.TOWORK_DATE,T1.TOWORK_DATE),?) ");
        sqlCon.addCondition(PigConstants.PRODUCTION_STAGE, " <= ? ,'L','H')) PIG_STAGE  ");
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T1  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T2  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_PIG_CLASS_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T3  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T3.ORIGIN <> 3  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T4  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T4.FARM_ID AND T3.EAR_CODE_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN ( ");
        sqlCon.addMainSql(" SELECT * FROM PP_L_BILL_CHANGE_HOUSE T WHERE T.DELETED_FLAG = '0' AND T.STATUS = '1'  ");
        sqlCon.addCondition(getFarmId(), " AND T.FARM_ID = ?  ");
        sqlCon.addCondition(endDate, " AND T.CHANGE_HOUSE_DATE <= ?  ");
        sqlCon.addCondition(endDate, " AND IFNULL(T.CHANGE_HOUSE_DATE_OUT,DATE_ADD(NOW(),INTERVAL 1 DAY)) > ?  ");
        sqlCon.addMainSql(" ) T5  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID  ");
        sqlCon.addMainSql(" LEFT JOIN CD_M_MATERIAL T6  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T3.MATERIAL_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.PIG_TYPE <> 3  ");
        sqlCon.addMainSql(" AND T1.PIG_CLASS NOT IN (1,3,4)  ");
        sqlCon.addMainSql(" AND IF(T1.PIG_CLASS= 24,T2.PIG_CLASS,T1.PIG_CLASS) <= 18  ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCon.addCondition(endDate, " AND T1.TOWORK_DATE <= ?  ");
        sqlCon.addCondition(endDate, " AND IFNULL(T1.TOWORK_DATE_OUT,IFNULL(T3.LEAVE_DATE,DATE_ADD(NOW(),INTERVAL 1 DAY))) > ?  ");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            detail = new HanaMtcPgstatDtlDetail();
            detail.setMtc_Period(TimeUtil.parseDate(endDate));
            detail.setMtc_BranchID(mtcBranchID);
            detail.setMtc_ItemCode(Maps.getString(map, "SAP_FIXED_ASSETS_EARBRAND"));
            detail.setMtc_ItemName(Maps.getString(map, "PIG_INFO"));
            detail.setMtc_DeptID(mtcDeptID);
            detail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLong(map, "HOUSE_ID")));
            detail.setMtc_Parity(Maps.getString(map, "PARITY"));
            detail.setMtc_PregStatus(Maps.getString(map, "PIG_CLASS"));
            detail.setMtc_PregDate(Maps.getDate(map, "PIG_CLASS_DATE"));
            detail.setMtc_DaySum(Maps.getString(map, "PIG_CLASS_DAY"));
            detail.setMtc_PregLevel(Maps.getString(map, "PIG_STAGE"));

            detail.setMtc_FarmId(String.valueOf(getFarmId()));
            detail.setMtc_FarmName(CacheUtil.getFarmName());
            detail.setMtc_HouseId(Maps.getString(map, "HOUSE_ID"));
            detail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(map, "HOUSE_ID")));
            detaillist.add(detail);
        }

        if (isSaveSession) {
            SecurityUtils.getSubject().getSession().setAttribute("detaillist3030", detaillist);
            SecurityUtils.getSubject().getSession().setAttribute("endDate3030", endDate);
        }
        if (detaillist.size() == 0) {
            return null;
        }

        // HanaMtcPgstatDtl hanaMtcPgstatDtl = new HanaMtcPgstatDtl();
        // hanaMtcPgstatDtl.setDetailList(detaillist);
        // MTC_ITFC mtcITFC = new MTC_ITFC();
        // // 分公司编号
        // mtcITFC.setMTC_Branch(mtcBranchID);
        // // 业务日期
        // mtcITFC.setMTC_DocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // // 业务代码:猪只销售(实时)
        // mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3030);
        // // 新农+单据编号
        // mtcITFC.setMTC_DocNum(getCompanyCode());
        // // 优先级
        // mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // // 处理区分
        // mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // // 创建日期
        // mtcITFC.setMTC_CreateTime(TimeUtil.getSysTimestamp());
        // // 同步状态
        // mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // // DB
        // mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
        // // JSON文件
        // String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMtcPgstatDtl);
        // mtcITFC.setMTC_DataFile(jsonDataFile);
        // // JSON文件长度
        // mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
        // hanaCommonService.insertMTC_ITFC(mtcITFC);
        SecurityUtils.getSubject().getSession().setAttribute("list3030", "list3030");
        return detaillist;
    }

    // sap妊娠母猪状态汇总 9999
    private List<HanaMtcPgstatDetail> sendToSapPgstat(String endDate, Map<String, String> mtcBranchIDAndMTC_DeptIDMap, Boolean isSaveSession)
            throws Exception {
        HanaMtcPgstatDetail detail = null;
        List<HanaMtcPgstatDetail> detaillist = new ArrayList<>();

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IF(T1.PIG_CLASS = 24 ,IFNULL(T5.HOUSE_ID,T2.HOUSE_ID),IFNULL(T3.HOUSE_ID,T1.HOUSE_ID)) HOUSE_ID,COUNT(1) QTY, ");
        sqlCon.addCondition(endDate, " SUM(IF(DATEDIFF(?,IF(T1.PIG_CLASS = 24 ,T2.TOWORK_DATE,T1.TOWORK_DATE))  ");
        sqlCon.addCondition(PigConstants.PRODUCTION_STAGE, " <=  ?  ,1,0)) QT_PIG_QTY,   ");
        sqlCon.addCondition(endDate, " SUM(IF(DATEDIFF(?,IF(T1.PIG_CLASS = 24 ,T2.TOWORK_DATE,T1.TOWORK_DATE)) ");
        sqlCon.addCondition(PigConstants.PRODUCTION_STAGE, "> ? ,1,0)) ZT_PIG_QTY  ");
        /* T2 --> T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T1  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T2  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_PIG_CLASS_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T3  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T3.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T4  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T4.FARM_ID AND T1.PIG_ID = T4.PIG_ID AND T1.BILL_ID = T4.BILL_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T5  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID AND T2.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.PIG_TYPE = 2  ");
        sqlCon.addMainSql(" AND IF(T1.PIG_CLASS = 24,T2.PIG_CLASS,T1.PIG_CLASS) = 9  ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCon.addCondition(endDate, " AND T1.TOWORK_DATE <= ?  ");
        sqlCon.addCondition(endDate, " AND IFNULL(T1.TOWORK_DATE_OUT,IFNULL(T3.LEAVE_DATE,DATE_ADD(NOW(),INTERVAL 1 DAY))) > ? ");
        sqlCon.addMainSql(" GROUP BY IF(T1.PIG_CLASS = 24 ,IFNULL(T5.HOUSE_ID,T2.HOUSE_ID),IFNULL(T3.HOUSE_ID,T1.HOUSE_ID))  ");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

        for (Map<String, String> map : list) {
            detail = new HanaMtcPgstatDetail();
            detail.setMtc_BranchID(Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID));
            detail.setMtc_DeptID(Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID));
            detail.setMtc_Period(TimeUtil.parseDate(endDate));
            detail.setMtc_PregStep_1(Maps.getString(map, "QT_PIG_QTY"));
            detail.setMtc_PregStep_2(Maps.getString(map, "ZT_PIG_QTY"));
            detail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLong(map, "HOUSE_ID")));

            detail.setMtc_HouseId(Maps.getString(map, "HOUSE_ID"));
            detail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(map, "HOUSE_ID")));
            detail.setMtc_FarmId(String.valueOf(getFarmId()));
            detail.setMtc_FarmName(CacheUtil.getFarmName());

            detaillist.add(detail);
        }

        if (isSaveSession) {
            SecurityUtils.getSubject().getSession().setAttribute("detaillist9999", detaillist);
            SecurityUtils.getSubject().getSession().setAttribute("endDate9999", endDate);
        }
        if (detaillist.size() == 0) {
            return null;
        }
        SecurityUtils.getSubject().getSession().setAttribute("list9999", "list9999");
        return detaillist;
    }

    // 存栏变动 9998 后备情期鉴定 - 不列入转状态情况 - 转舍即为内转
    private Map<String, List<HanaMtcPgtsdtlDetail>> sendToSapMtcPgtsdtl(String startDate, String endDate, String mtcBranchID, String mtcDeptID,
            Boolean isSaveSession) throws Exception {
        Map<String, List<HanaMtcPgtsdtlDetail>> listMap = new HashMap<>();
        List<HanaMtcPgtsdtlDetail> hanaMtcPgtsdtlDetailList = new ArrayList<>();
        List<HanaMtcPgtsdtlDetail> hanaMtcPgtsdtlDetailHBList = new ArrayList<>();

        // 3010 - 新购入场(包括后备猪和商品猪 - 排除本厂新出生和调拨的猪)
        SqlCon sqlCons = new SqlCon();
        sqlCons.addMainSql(
                " SELECT '3010' TRANSTYPE,T0.FARM_ID,T0.HOUSE_ID,T0.ENTER_DATE DOCDATE,T1.FARM_ID OUT_FARM_ID,T1.HOUSE_ID OUT_HOUSE_ID,T1.LEAVE_DATE, ");
        sqlCons.addMainSql(" SUM(IF(T4.PIG_TYPE = 3,1,0)) QTY, ");
        sqlCons.addMainSql(" SUM(IF(T4.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_QTY, ");
        sqlCons.addMainSql(" SUM(IF(T4.PIG_TYPE = 3,IFNULL(T4.CHANGE_WEIGHT,T1.LEAVE_WEIGHT),0)) WEIGHT, ");
        sqlCons.addMainSql(" SUM(IF(T4.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),IFNULL(T4.CHANGE_WEIGHT,T1.LEAVE_WEIGHT),0)) HB_WEIGHT, ");
        sqlCons.addMainSql(" SUM(IF(T4.PIG_TYPE = 3,T1.LEAVE_WEIGHT,0)) OUT_WEIGHT, ");
        sqlCons.addMainSql(" SUM(IF(T4.PIG_TYPE <> 3 AND T1.PIG_CLASS IN (1,3,4),T1.LEAVE_WEIGHT,0)) HB_OUT_WEIGHT ");
        sqlCons.addMainSql(" FROM PP_L_PIG T0  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_LEAVE T1 ");
        sqlCons.addMainSql(" ON T0.RELATION_PIG_ID = T1.PIG_ID AND t1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T4 ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T4.FARM_ID AND T0.ROW_ID = T4.PIG_ID AND T0.BILL_ID = T4.BILL_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_LEAVE T7 ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'  ");
        sqlCons.addMainSql(
                " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND (T4.PIG_TYPE = 3 OR T0.ENTER_PIG_CLASS IN (1,3,4))  AND T7.PIG_ID IS NULL ");
        // 排除本厂出生的猪
        sqlCons.addMainSql(" AND IFNULL(T1.SAP_SALE_TYPE ,1) IN (1,2) ");
        sqlCons.addCondition(getFarmId(),
                " AND T0.BILL_ID NOT IN (SELECT DISTINCT BILL_ID FROM PP_L_BILL_DELIVERY WHERE DELETED_FLAG = 0 AND STATUS = '1' AND FARM_ID = ? ");
        sqlCons.addCondition(startDate, " AND DELIVERY_DATE BETWEEN ? ");
        sqlCons.addCondition(endDate, " AND  ? ) ");
        sqlCons.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
        sqlCons.addCondition(startDate, " AND T0.ENTER_DATE BETWEEN ? ");
        sqlCons.addCondition(endDate, " AND ? ");
        sqlCons.addMainSql(" GROUP BY T4.HOUSE_ID,T0.ENTER_DATE ,T1.HOUSE_ID,T1.LEAVE_DATE ");
        Map<String, String> sqlMaps = new HashMap<>();
        sqlMaps.put("sql", sqlCons.getCondition());
        List<Map<String, Object>> listS = paramMapper.getObjectInfos(sqlMaps);

        for (Map<String, Object> mapList : listS) {
            if (!StringUtil.isBlank(Maps.getString(mapList, "QTY")) && !"0".equals(Maps.getString(mapList, "QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlDetail = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlDetail.setMtc_PigType(HanaConstants.MTC_PigType_SP);
                hanaMtcPgtsdtlDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail.setMtc_Period(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlDetail.setMtc_DocDate(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlDetail.setMtc_TransType(Maps.getString(mapList, "TRANSTYPE"));
                Long longClass = Maps.getLongClass(mapList, "OUT_FARM_ID");
                if (longClass == null) {
                    hanaMtcPgtsdtlDetail.setMtc_RelaDeptID(null);
                } else {
                    hanaMtcPgtsdtlDetail.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(longClass), HanaUtil.MTC_DeptID));
                }
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE_ID")));
                hanaMtcPgtsdtlDetail.setMtc_InQty(Maps.getString(mapList, "QTY"));
                hanaMtcPgtsdtlDetail.setMtc_InWeight(Maps.getString(mapList, "WEIGHT"));
                hanaMtcPgtsdtlDetail.setMtc_OutQty("0");
                hanaMtcPgtsdtlDetail.setMtc_OutWeight("0");

                hanaMtcPgtsdtlDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlDetail.setMtc_FarmName(CacheUtil.getFarmName());
                hanaMtcPgtsdtlDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE_ID"));
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));
                hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlDetail);
            }

            if (!StringUtil.isBlank(Maps.getString(mapList, "HB_QTY")) && !"0".equals(Maps.getString(mapList, "HB_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBDetail = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlHBDetail.setMtc_PigType(HanaConstants.MTC_PigType_HB);
                hanaMtcPgtsdtlHBDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlHBDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlHBDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail.setMtc_Period(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlHBDetail.setMtc_DocDate(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlHBDetail.setMtc_TransType(Maps.getString(mapList, "TRANSTYPE"));
                Long longClass = Maps.getLongClass(mapList, "OUT_FARM_ID");
                if (longClass == null) {
                    hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID(null);
                } else {
                    hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(longClass), HanaUtil.MTC_DeptID));
                }
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail.setMtc_InQty(Maps.getString(mapList, "HB_QTY"));
                hanaMtcPgtsdtlHBDetail.setMtc_InWeight(Maps.getString(mapList, "HB_WEIGHT"));
                hanaMtcPgtsdtlHBDetail.setMtc_OutQty("0");
                hanaMtcPgtsdtlHBDetail.setMtc_OutWeight("0");

                hanaMtcPgtsdtlHBDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlHBDetail.setMtc_FarmName(CacheUtil.getFarmName());
                hanaMtcPgtsdtlHBDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));
                hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBDetail);
            }
        }

        SqlCon sqlCon = new SqlCon();
        // 3020 - 本厂 - 后备转生产
        sqlCon.addMainSql(
                " SELECT '3020' TRANSTYPE,T1.FARM_ID,IFNULL(T6.HOUSE_ID, T1.HOUSE_ID) HOUSE_ID,T1.TOWORK_DATE DOCDATE,'' OUT_FARM_ID,'' OUT_HOUSE_ID,T2.TOWORK_DATE OUT_DATE, ");
        sqlCon.addMainSql("  SUM(IF(T1.PIG_TYPE = 3, 1, 0)) QTY, ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T2.PIG_CLASS IN (1,3,4),1,0)) HB_QTY, ");
        sqlCon.addMainSql("  SUM(IF(T1.PIG_TYPE = 3, IFNULL(T6.CHANGE_WEIGHT,0), 0)) WEIGHT , ");
        sqlCon.addMainSql("  SUM(IF(T1.PIG_TYPE <> 3 AND T2.PIG_CLASS IN (1,3,4), IFNULL(T6.CHANGE_WEIGHT,0), 0)) HB_WEIGHT, ");
        sqlCon.addMainSql("  SUM(IF(T1.PIG_TYPE = 3, IFNULL(T7.CHANGE_WEIGHT,0), 0)) OUT_WEIGHT , ");
        sqlCon.addMainSql("  SUM(IF(T1.PIG_TYPE <> 3 AND T2.PIG_CLASS IN (1,3,4), IFNULL(T7.CHANGE_WEIGHT,0), 0)) HB_OUT_WEIGHT ");
        /* T2->T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T1 ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2 ");
        sqlCon.addMainSql(
                " ON  T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_PIG_CLASS_ID = T2.ROW_ID AND T2.DELETED_FLAG = 0 AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T3  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T3.ORIGIN <> 3 ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T6 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T6.FARM_ID AND T1.PIG_ID = T6.PIG_ID AND T1.BILL_ID = T6.BILL_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T7 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T7.FARM_ID AND T1.PIG_ID = T7.PIG_ID AND T6.CHANGE_HOUSE_DATE = T7.ROW_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T2.PIG_CLASS IN(1,4) AND T1.PIG_CLASS IN(2,9) ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T1.TOWORK_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        // IFNULL(T6.HOUSE_ID,T1.HOUSE_ID),IFNULL(T7.HOUSE_ID,T1.HOUSE_ID) 当T6,T7为空时towork表的houseId即为转入转出舍
        sqlCon.addMainSql(" GROUP BY T1.FARM_ID,IFNULL(T6.HOUSE_ID,T1.HOUSE_ID),T1.TOWORK_DATE,IFNULL(T7.HOUSE_ID,T1.HOUSE_ID),T2.TOWORK_DATE ");
        Map<String, String> sqlMapss = new HashMap<>();
        sqlMapss.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> listSCss = paramMapper.getObjectInfos(sqlMapss);
        for (Map<String, Object> mapList : listSCss) {
            // - 转入
            HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBDetail = new HanaMtcPgtsdtlDetail();
            hanaMtcPgtsdtlHBDetail.setMtc_PigType(HanaConstants.MTC_PigType_HB);
            hanaMtcPgtsdtlHBDetail.setMtc_BranchID(mtcBranchID);
            hanaMtcPgtsdtlHBDetail.setMtc_DeptID(mtcDeptID);
            hanaMtcPgtsdtlHBDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
            hanaMtcPgtsdtlHBDetail.setMtc_Period(Maps.getDate(mapList, "DOCDATE"));
            hanaMtcPgtsdtlHBDetail.setMtc_DocDate(Maps.getDate(mapList, "DOCDATE"));
            hanaMtcPgtsdtlHBDetail.setMtc_TransType(Maps.getString(mapList, "TRANSTYPE"));
            hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID(null);
            hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit(null);
            hanaMtcPgtsdtlHBDetail.setMtc_InQty("0");
            hanaMtcPgtsdtlHBDetail.setMtc_InWeight("0");
            hanaMtcPgtsdtlHBDetail.setMtc_OutQty(Maps.getString(mapList, "HB_QTY"));
            hanaMtcPgtsdtlHBDetail.setMtc_OutWeight(Maps.getString(mapList, "HB_WEIGHT"));

            hanaMtcPgtsdtlHBDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
            hanaMtcPgtsdtlHBDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
            hanaMtcPgtsdtlHBDetail.setMtc_FarmId(String.valueOf(getFarmId()));
            hanaMtcPgtsdtlHBDetail.setMtc_FarmName(CacheUtil.getFarmName());
            hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBDetail);
        }

        // 3030 -- 分娩记录
        sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT '3030' TRANSTYPE,T0.FARM_ID,T0.HOUSE_ID,T0.DELIVERY_DATE DOCDATE, ");
        sqlCon.addCondition(getFarmId(),
                " SUM(T0.HEALTHY_NUM) + SUM(IF((SELECT _getSettingValueByCode( ? ,'RZBSCL')) = 'OFF',IFNULL(t0.WEAK_NUM,0),0)) QTY ,0 HB_QTY,0 RESOUSE_FARM,SUM(IFNULL(T0.ALIVE_LITTER_WEIGHT,0)) WEIGHT  ");
        sqlCon.addMainSql(" FROM PP_L_BILL_DELIVERY T0  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T0.DELIVERY_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID,T0.DELIVERY_DATE ");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> mapList : list) {
            HanaMtcPgtsdtlDetail hanaMtcPgtsdtlDetail = new HanaMtcPgtsdtlDetail();
            hanaMtcPgtsdtlDetail.setMtc_PigType(HanaConstants.MTC_PigType_SP);
            hanaMtcPgtsdtlDetail.setMtc_BranchID(mtcBranchID);
            hanaMtcPgtsdtlDetail.setMtc_DeptID(mtcDeptID);
            hanaMtcPgtsdtlDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
            hanaMtcPgtsdtlDetail.setMtc_Period(Maps.getDate(mapList, "DOCDATE"));
            hanaMtcPgtsdtlDetail.setMtc_DocDate(Maps.getDate(mapList, "DOCDATE"));
            hanaMtcPgtsdtlDetail.setMtc_TransType(Maps.getString(mapList, "TRANSTYPE"));
            hanaMtcPgtsdtlDetail.setMtc_RelaDeptID(null);
            hanaMtcPgtsdtlDetail.setMtc_RelaUnit(null);

            hanaMtcPgtsdtlDetail.setMtc_InQty(Maps.getString(mapList, "QTY"));
            hanaMtcPgtsdtlDetail.setMtc_InWeight(Maps.getString(mapList, "WEIGHT"));
            hanaMtcPgtsdtlDetail.setMtc_OutQty("0");
            hanaMtcPgtsdtlDetail.setMtc_OutWeight("0");

            hanaMtcPgtsdtlDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
            hanaMtcPgtsdtlDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
            hanaMtcPgtsdtlDetail.setMtc_FarmId(String.valueOf(getFarmId()));
            hanaMtcPgtsdtlDetail.setMtc_FarmName(CacheUtil.getFarmName());

            hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlDetail);
        }

        sqlCons = new SqlCon();
        // 3040 - 转群记录 - 常规 (不包含后备转产)
        // 3050 - 转群记录 - 内转
        sqlCons.addMainSql(" SELECT TT1.FARM_ID, TT1.HOUSE_ID , TT1.CHANGE_HOUSE_DATE,TT1.OUT_FARM_ID,TT1.OUT_HOUSE , ");
        sqlCons.addMainSql(" SUM(TT1.NORMAL_IN_QTY) NORMAL_IN_QTY, ");
        sqlCons.addMainSql(" SUM(TT1.INNER_IN_QTY) INNER_IN_QTY, ");
        sqlCons.addMainSql(" SUM(TT1.HB_NORMAL_IN_QTY) HB_NORMAL_IN_QTY, ");
        sqlCons.addMainSql(" SUM(TT1.HB_INNER_IN_QTY) HB_INNER_IN_QTY, ");
        sqlCons.addMainSql(" SUM(TT1.NORMAL_IN_WEIGHT) NORMAL_IN_WEIGHT, ");
        sqlCons.addMainSql(" SUM(TT1.INNER_IN_WEIGHT) INNER_IN_WEIGHT, ");
        sqlCons.addMainSql(" SUM(TT1.HB_NORMAL_IN_WEIGHT) HB_NORMAL_IN_WEIGHT, ");
        sqlCons.addMainSql(" SUM(TT1.HB_INNER_IN_WEIGHT) HB_INNER_IN_WEIGHT, ");
        sqlCons.addMainSql(" SUM(TT1.NORMAL_OUT_QTY) NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(" SUM(TT1.INNER_OUT_QTY) INNER_OUT_QTY,  ");
        sqlCons.addMainSql(" SUM(TT1.HB_NORMAL_OUT_QTY) HB_NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(" SUM(TT1.HB_INNER_OUT_QTY) HB_INNER_OUT_QTY,  ");
        sqlCons.addMainSql(" SUM(TT1.NORMAL_OUT_WEIGHT) NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" SUM(TT1.INNER_OUT_WEIGHT) INNER_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" SUM(TT1.HB_NORMAL_OUT_WEIGHT) HB_NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" SUM(TT1.HB_INNER_OUT_WEIGHT) HB_INNER_OUT_WEIGHT    ");
        sqlCons.addMainSql(" FROM ( ");
        /* 跨场 转群,转入 */
        sqlCons.addMainSql("    SELECT T0.FARM_ID, T7.HOUSE_ID, T0.ENTER_DATE CHANGE_HOUSE_DATE, T1.FARM_ID OUT_FARM_ID, T1.HOUSE_ID OUT_HOUSE,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,1,0)) NORMAL_IN_QTY,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,1,0)) INNER_IN_QTY,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,1,0)) HB_NORMAL_IN_QTY,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,1,0)) HB_INNER_IN_QTY,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) INNER_IN_WEIGHT,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) HB_NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) HB_INNER_IN_WEIGHT,  ");
        sqlCons.addMainSql("    0 NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql("    0 INNER_OUT_QTY,  ");
        sqlCons.addMainSql("    0 HB_NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql("    0 HB_INNER_OUT_QTY,  ");
        sqlCons.addMainSql("    0 NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql("    0 INNER_OUT_WEIGHT,  ");
        sqlCons.addMainSql("    0 HB_NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql("    0 HB_INNER_OUT_WEIGHT    ");
        sqlCons.addMainSql("    FROM PP_L_PIG T0  ");
        sqlCons.addMainSql("    INNER JOIN PP_L_BILL_LEAVE T1  ");
        sqlCons.addMainSql(
                "    ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.SAP_SALE_TYPE = 3 AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.SAP_SALE_TYPE = 3  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T2  ");
        sqlCons.addMainSql("    ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T3  ");
        sqlCons.addMainSql(
                "    ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T5  ");
        sqlCons.addMainSql("    ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T6  ");
        sqlCons.addMainSql(
                "    ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCons.addMainSql("    INNER JOIN PP_L_BILL_CHANGE_HOUSE T7  ");
        sqlCons.addMainSql(
                "    ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'  ");
        sqlCons.addMainSql(
                "    WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.ORIGIN <> 3 AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) =  IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE)  ");
        sqlCons.addCondition(getFarmId(), "    AND T0.FARM_ID = ?  ");
        sqlCons.addCondition(startDate, "    AND T0.ENTER_DATE BETWEEN ?  ");
        sqlCons.addCondition(endDate, "    AND ?  ");
        sqlCons.addMainSql("    GROUP BY T7.HOUSE_ID,T0.ENTER_DATE,T1.LEAVE_DATE,T1.HOUSE_ID  ");
        sqlCons.addMainSql(" UNION ALL ");
        /* 跨场 转群,转出 */
        sqlCons.addMainSql(
                "    SELECT T1.FARM_ID FARM_ID, T1.HOUSE_ID HOUSE_ID, T1.LEAVE_DATE CHANGE_HOUSE_DATE, T0.FARM_ID OUT_FARM_ID, T7.HOUSE_ID OUT_HOUSE,  ");
        sqlCons.addMainSql("    0 NORMAL_IN_QTY,  ");
        sqlCons.addMainSql("    0 INNER_IN_QTY,  ");
        sqlCons.addMainSql("    0 HB_NORMAL_IN_QTY,  ");
        sqlCons.addMainSql("    0 HB_INNER_IN_QTY,  ");
        sqlCons.addMainSql("    0 NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql("    0 INNER_IN_WEIGHT,  ");
        sqlCons.addMainSql("    0 HB_NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql("    0 HB_INNER_IN_WEIGHT , ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,1,0)) NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,1,0)) INNER_OUT_QTY,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,1,0)) HB_NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,1,0)) HB_INNER_OUT_QTY,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql("    SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) INNER_OUT_WEIGHT,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) HB_NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(
                "    SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,T7.CHANGE_WEIGHT,0)) HB_INNER_OUT_WEIGHT  ");
        sqlCons.addMainSql("    FROM PP_L_PIG T0  ");
        sqlCons.addMainSql("    INNER JOIN PP_L_BILL_LEAVE T1  ");
        sqlCons.addMainSql(
                "    ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.SAP_SALE_TYPE = 3 AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T2  ");
        sqlCons.addMainSql("    ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T3  ");
        sqlCons.addMainSql(
                "    ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T5  ");
        sqlCons.addMainSql("    ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");
        sqlCons.addMainSql("    LEFT JOIN HR_M_COMPANY T6  ");
        sqlCons.addMainSql(
                "    ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCons.addMainSql("    INNER JOIN PP_L_BILL_CHANGE_HOUSE T7  ");
        sqlCons.addMainSql(
                "    ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'  ");
        sqlCons.addMainSql(
                "    WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.ORIGIN <> 3 AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) =  IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE)  ");
        sqlCons.addCondition(getFarmId(), "    AND T1.FARM_ID = ?   ");
        sqlCons.addCondition(startDate, "    AND T0.ENTER_DATE BETWEEN ?  ");
        sqlCons.addCondition(endDate, "    AND ?  ");
        sqlCons.addMainSql("    GROUP BY T7.HOUSE_ID,T0.ENTER_DATE,T1.LEAVE_DATE,T1.HOUSE_ID  ");

        /* 场内 转入 */
        sqlCons.addMainSql(" UNION ALL  ");
        sqlCons.addMainSql(" SELECT T1.FARM_ID FARM_ID, T1.HOUSE_ID, T1.CHANGE_HOUSE_DATE, T2.FARM_ID OUT_FARM_ID, T2.HOUSE_ID OUT_HOUSE,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE = 3 , 1, 0)) NORMAL_IN_QTY,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE IS NULL AND T1.PIG_TYPE = 3 , 1, 0)) INNER_IN_QTY,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE <> 3 AND T5.PIG_CLASS = 4 AND T6.PIG_CLASS <> 3 , 1, 0)) HB_NORMAL_IN_QTY, ");
        sqlCons.addMainSql(
                " SUM(IF((T6.PIG_TYPE IS NULL AND T1.PIG_TYPE <> 3 ) OR (T5.PIG_CLASS = 4 AND T6.PIG_CLASS = 3 ), 1, 0)) HB_INNER_IN_QTY, ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE = 3 , IFNULL(T1.CHANGE_WEIGHT, 0), 0)) NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE IS NULL AND T1.PIG_TYPE = 3 , IFNULL(T1.CHANGE_WEIGHT,0), 0)) INNER_IN_WEIGHT,  ");
        sqlCons.addMainSql(
                " SUM(IF(T6.PIG_TYPE <> 3 AND T5.PIG_CLASS = 4 AND T6.PIG_CLASS <> 3 , IFNULL(T1.CHANGE_WEIGHT,0), 0)) HB_NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql(
                " SUM(IF((T6.PIG_TYPE IS NULL AND T1.PIG_TYPE <> 3 ) OR (T5.PIG_CLASS = 4 AND T6.PIG_CLASS = 3 ), IFNULL(T1.CHANGE_WEIGHT,0), 0)) HB_INNER_IN_WEIGHT , ");
        sqlCons.addMainSql(" 0 NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(" 0 INNER_OUT_QTY, ");
        sqlCons.addMainSql(" 0 HB_NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(" 0 HB_INNER_OUT_QTY,  ");
        sqlCons.addMainSql(" 0 NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" 0 INNER_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" 0 HB_NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" 0 HB_INNER_OUT_WEIGHT ");
        // T1是T2的下一条数据 T2 --> T1
        sqlCons.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1  ");
        sqlCons.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2  ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T2.ROW_ID = T1.CHANGE_HOUSE_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T5  ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID AND T1.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T6 ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T6.FARM_ID AND T1.PIG_ID = T6.PIG_ID AND T6.ROW_ID = T5.CHANGE_PIG_CLASS_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCons.addMainSql(" INNER JOIN PP_L_PIG T7  ");
        sqlCons.addMainSql(" ON T1.FARM_ID = T7.FARM_ID AND T1.PIG_ID = T7.ROW_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' AND T7.ORIGIN <> 3 ");
        sqlCons.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        // 避开转入相同舍问题
        sqlCons.addMainSql(" AND T1.HOUSE_ID <> T2.HOUSE_ID ");
        // 转出猪只状态必须是肉猪或者后备猪 t5 = null 即为 内转 pig_type pig_class 不变 因此使用T1 的数据
        sqlCons.addMainSql(" AND (IFNULL(T5.PIG_TYPE,T1.PIG_TYPE) = 3 OR IFNULL(T5.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4)) ");
        // 排除转后备的情况
        sqlCons.addMainSql("  AND IFNULL(T5.PIG_TYPE,1) = IFNULL(T6.PIG_TYPE,1)  ");
        sqlCons.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCons.addCondition(startDate, " AND T1.CHANGE_HOUSE_DATE BETWEEN ? ");
        sqlCons.addCondition(endDate, " AND ? ");
        sqlCons.addMainSql(" GROUP BY T1.HOUSE_ID,T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE,T2.CHANGE_HOUSE_DATE  ");

        // 场内转 转出
        sqlCons.addMainSql(" UNION ALL ");
        sqlCons.addMainSql(" SELECT T2.FARM_ID FARM_ID, T2.HOUSE_ID HOUSE_ID, T1.CHANGE_HOUSE_DATE, T1.FARM_ID OUT_FARM_ID,T1.HOUSE_ID OUT_HOUSE,  ");
        sqlCons.addMainSql(" 0 NORMAL_IN_QTY,  ");
        sqlCons.addMainSql(" 0 INNER_IN_QTY,  ");
        sqlCons.addMainSql(" 0 HB_NORMAL_IN_QTY,  ");
        sqlCons.addMainSql(" 0 HB_INNER_IN_QTY,  ");
        sqlCons.addMainSql(" 0 NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql(" 0 INNER_IN_WEIGHT,  ");
        sqlCons.addMainSql(" 0 HB_NORMAL_IN_WEIGHT,  ");
        sqlCons.addMainSql(" 0 HB_INNER_IN_WEIGHT ,  ");

        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE = 3 , 1, 0)) NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE IS NULL AND T1.PIG_TYPE = 3 , 1, 0)) INNER_OUT_QTY,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE <> 3 AND T5.PIG_CLASS = 4 AND T6.PIG_CLASS <> 3 , 1, 0)) HB_NORMAL_OUT_QTY,  ");
        sqlCons.addMainSql(
                " SUM(IF((T6.PIG_TYPE IS NULL AND T1.PIG_TYPE <> 3 ) OR (T5.PIG_CLASS = 4 AND T6.PIG_CLASS = 3 ), 1, 0)) HB_INNER_OUT_QTY, ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE = 3 , IFNULL(T1.CHANGE_WEIGHT, 0), 0)) NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" SUM(IF(T6.PIG_TYPE IS NULL AND T1.PIG_TYPE = 3 , IFNULL(T1.CHANGE_WEIGHT,0), 0)) INNER_OUT_WEIGHT,  ");
        sqlCons.addMainSql(
                " SUM(IF(T6.PIG_TYPE <> 3 AND T5.PIG_CLASS = 4 AND T6.PIG_CLASS <> 3 , IFNULL(T1.CHANGE_WEIGHT,0), 0)) HB_NORMAL_OUT_WEIGHT, ");
        sqlCons.addMainSql(
                " SUM(IF((T6.PIG_TYPE IS NULL AND T1.PIG_TYPE <> 3 ) OR (T5.PIG_CLASS = 4 AND T6.PIG_CLASS = 3 ) , IFNULL(T1.CHANGE_WEIGHT,0), 0)) HB_INNER_OUT_WEIGHT  ");
        // T2 --> T1
        sqlCons.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1  ");
        sqlCons.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2  ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T2.ROW_ID = T1.CHANGE_HOUSE_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T5  ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID AND T1.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T6  ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T6.FARM_ID AND T1.PIG_ID = T6.PIG_ID AND T5.CHANGE_PIG_CLASS_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCons.addMainSql(" INNER JOIN PP_L_PIG T7  ");
        sqlCons.addMainSql(
                " ON T1.FARM_ID = T7.FARM_ID AND T1.PIG_ID = T7.ROW_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' AND T7.ORIGIN <> 3  ");
        sqlCons.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1'  ");
        // 避开转入相同舍问题
        sqlCons.addMainSql("AND T1.HOUSE_ID <> T2.HOUSE_ID  ");
        // 转出猪只状态必须是肉猪或者后备猪 t5 = null 即为 内转 pig_type pig_class 不变 因此使用T1 的数据
        sqlCons.addMainSql(" AND (IFNULL(T5.PIG_TYPE,T1.PIG_TYPE) = 3 OR IFNULL(T5.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4)) ");
        // 排除转后备的情况
        sqlCons.addMainSql("  AND IFNULL(T5.PIG_TYPE,1) = IFNULL(T6.PIG_TYPE,1) ");
        sqlCons.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCons.addCondition(startDate, " AND T1.CHANGE_HOUSE_DATE BETWEEN ?  ");
        sqlCons.addCondition(endDate, " AND ? ");
        sqlCons.addMainSql(" GROUP BY T1.HOUSE_ID,T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE,T2.CHANGE_HOUSE_DATE ");

        // 转后备 转入
        sqlCons.addMainSql(" UNION ALL ");
        sqlCons.addMainSql(
                " SELECT T0.FARM_ID,IFNULL(T3.HOUSE_ID,T0.HOUSE_ID) HOUSE_ID, T0.TOWORK_DATE CHANGE_HOUSE_DATE, T1.FARM_ID OUT_FARM_ID, IFNULL(T4.HOUSE_ID,T1.HOUSE_ID) OUT_HOUSE,   ");
        sqlCons.addMainSql(" 0 NORMAL_IN_QTY,   ");
        sqlCons.addMainSql(" 0 INNER_IN_QTY,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_IN_QTY,   ");
        sqlCons.addMainSql(" COUNT(1) HB_INNER_IN_QTY,   ");
        sqlCons.addMainSql(" 0 NORMAL_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 INNER_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_IN_WEIGHT,   ");
        // 不能取上次转舍重量与本次转舍无关
        sqlCons.addMainSql(" SUM(IFNULL(T3.CHANGE_WEIGHT,0)) HB_INNER_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 NORMAL_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 INNER_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 HB_INNER_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 NORMAL_OUT_WEIGHT,   ");
        sqlCons.addMainSql(" 0 INNER_OUT_WEIGHT,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" 0 HB_INNER_OUT_WEIGHT  ");
        sqlCons.addMainSql(" FROM PP_L_BILL_TOWORK T0  ");
        sqlCons.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T1  ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.PIG_ID AND T0.CHANGE_PIG_CLASS_ID = T1.ROW_ID AND T0.PIG_TYPE <> T1.PIG_TYPE AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3 ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T3.FARM_ID AND T0.PIG_ID = T3.PIG_ID AND T0.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T4  ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T4.FARM_ID AND T0.PIG_ID = T4.PIG_ID AND T4.ROW_ID = T3.CHANGE_HOUSE_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1'  ");
        sqlCons.addMainSql(" INNER JOIN PP_L_PIG T6  ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T6.FARM_ID AND T0.PIG_ID = T6.ROW_ID AND T6.ORIGIN <> 3 AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'  ");
        sqlCons.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T1.PIG_TYPE = 3 AND T0.PIG_TYPE <> 3  ");
        sqlCons.addCondition(getFarmId(), " AND T0.FARM_ID = ?  ");
        sqlCons.addCondition(startDate, " AND T0.TOWORK_DATE BETWEEN  ?  ");
        sqlCons.addCondition(endDate, " AND  ?  ");
        sqlCons.addMainSql(" GROUP BY T0.FARM_ID,IFNULL(T3.HOUSE_ID,T0.HOUSE_ID),IFNULL(T4.HOUSE_ID,T1.HOUSE_ID),T0.TOWORK_DATE,T1.TOWORK_DATE ");

        // 转后备 转出
        sqlCons.addMainSql(" UNION ALL ");
        sqlCons.addMainSql(
                " SELECT T1.FARM_ID,IFNULL(T4.HOUSE_ID,T1.HOUSE_ID) HOUSE_ID, T0.TOWORK_DATE CHANGE_HOUSE_DATE, T0.FARM_ID OUT_FARM_ID, IFNULL(T3.HOUSE_ID,T0.HOUSE_ID) OUT_HOUSE,   ");
        sqlCons.addMainSql(" 0 NORMAL_IN_QTY,   ");
        sqlCons.addMainSql(" 0 INNER_IN_QTY,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_IN_QTY,   ");
        sqlCons.addMainSql(" 0 HB_INNER_IN_QTY,   ");
        sqlCons.addMainSql(" 0 NORMAL_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 INNER_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 HB_INNER_IN_WEIGHT,   ");
        sqlCons.addMainSql(" 0 NORMAL_OUT_QTY,   ");
        sqlCons.addMainSql(" COUNT(1) INNER_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 HB_INNER_OUT_QTY,   ");
        sqlCons.addMainSql(" 0 NORMAL_OUT_WEIGHT,   ");
        // 转出时重量即为转入重量,不能取上次转舍重量与本次转舍无关
        sqlCons.addMainSql(" SUM(IFNULL(T3.CHANGE_WEIGHT,0)) INNER_OUT_WEIGHT,   ");
        sqlCons.addMainSql(" 0 HB_NORMAL_OUT_WEIGHT,  ");
        sqlCons.addMainSql(" 0 HB_INNER_OUT_WEIGHT  ");
        sqlCons.addMainSql(" FROM PP_L_BILL_TOWORK T0  ");
        sqlCons.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T1  ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.PIG_ID AND T0.CHANGE_PIG_CLASS_ID = T1.ROW_ID AND T0.PIG_TYPE <> T1.PIG_TYPE AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3 ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T3.FARM_ID AND T0.PIG_ID = T3.PIG_ID AND T0.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'  ");
        sqlCons.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T4  ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T4.FARM_ID AND T0.PIG_ID = T4.PIG_ID AND T4.ROW_ID = T3.CHANGE_HOUSE_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1'  ");
        sqlCons.addMainSql(" INNER JOIN PP_L_PIG T6  ");
        sqlCons.addMainSql(
                " ON T0.FARM_ID = T6.FARM_ID AND T0.PIG_ID = T6.ROW_ID AND T6.ORIGIN <> 3 AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'  ");
        sqlCons.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T1.PIG_TYPE = 3 AND T0.PIG_TYPE <> 3  ");
        sqlCons.addCondition(getFarmId(), " AND T0.FARM_ID = ?  ");
        sqlCons.addCondition(startDate, " AND T0.TOWORK_DATE BETWEEN  ?  ");
        sqlCons.addCondition(endDate, " AND  ?  ");
        sqlCons.addMainSql(" GROUP BY T0.FARM_ID,IFNULL(T3.HOUSE_ID,T0.HOUSE_ID),IFNULL(T4.HOUSE_ID,T1.HOUSE_ID),T0.TOWORK_DATE,T1.TOWORK_DATE ");
        sqlCons.addMainSql(" ) TT1  ");
        sqlCons.addMainSql(" GROUP BY TT1.FARM_ID, TT1.HOUSE_ID , TT1.CHANGE_HOUSE_DATE,TT1.OUT_FARM_ID,TT1.OUT_HOUSE ");
        Map<String, String> sqlMapIntOut = new HashMap<>();
        sqlMapIntOut.put("sql", sqlCons.getCondition());
        List<Map<String, Object>> listIntOut = paramMapper.getObjectInfos(sqlMapIntOut);
        // 3040 - 转群记录 - 常规
        // 3050 - 转群记录 - 内转
        for (Map<String, Object> mapList : listIntOut) {
            // 内转 转入
            if (!StringUtil.isBlank(Maps.getString(mapList, "INNER_IN_QTY")) && !"0".equals(Maps.getString(mapList, "INNER_IN_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlDetail = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlDetail.setMtc_PigType(HanaConstants.MTC_PigType_SP);
                hanaMtcPgtsdtlDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlDetail.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlDetail.setMtc_TransType("3050");
                hanaMtcPgtsdtlDetail.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlDetail.setMtc_InQty(Maps.getString(mapList, "INNER_IN_QTY"));
                hanaMtcPgtsdtlDetail.setMtc_InWeight(Maps.getString(mapList, "INNER_IN_WEIGHT"));
                hanaMtcPgtsdtlDetail.setMtc_OutQty("0");
                hanaMtcPgtsdtlDetail.setMtc_OutWeight("0");

                hanaMtcPgtsdtlDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlDetail.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlDetail);
            }
            // 内转 转出
            if (!StringUtil.isBlank(Maps.getString(mapList, "INNER_OUT_QTY")) && !"0".equals(Maps.getString(mapList, "INNER_OUT_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlOutDetail = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlOutDetail.setMtc_PigType(HanaConstants.MTC_PigType_SP);
                hanaMtcPgtsdtlOutDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlOutDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlOutDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlOutDetail.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlOutDetail.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlOutDetail.setMtc_TransType("3050");
                hanaMtcPgtsdtlOutDetail.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlOutDetail.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlOutDetail.setMtc_InQty("0");
                hanaMtcPgtsdtlOutDetail.setMtc_InWeight("0");
                hanaMtcPgtsdtlOutDetail.setMtc_OutQty(Maps.getString(mapList, "INNER_OUT_QTY"));
                hanaMtcPgtsdtlOutDetail.setMtc_OutWeight(Maps.getString(mapList, "INNER_OUT_WEIGHT"));

                hanaMtcPgtsdtlOutDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlOutDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlOutDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlOutDetail.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlOutDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlOutDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlOutDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlOutDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlOutDetail);
            }
            // 常规 转入
            if (!StringUtil.isBlank(Maps.getString(mapList, "NORMAL_IN_QTY")) && !"0".equals(Maps.getString(mapList, "NORMAL_IN_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlDetail2 = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlDetail2.setMtc_PigType(HanaConstants.MTC_PigType_SP);
                hanaMtcPgtsdtlDetail2.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlDetail2.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlDetail2.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail2.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlDetail2.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlDetail2.setMtc_TransType("3040");
                hanaMtcPgtsdtlDetail2.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlDetail2.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlDetail2.setMtc_InQty(Maps.getString(mapList, "NORMAL_IN_QTY"));
                hanaMtcPgtsdtlDetail2.setMtc_InWeight(Maps.getString(mapList, "NORMAL_IN_WEIGHT"));
                hanaMtcPgtsdtlDetail2.setMtc_OutQty("0");
                hanaMtcPgtsdtlDetail2.setMtc_OutWeight("0");

                hanaMtcPgtsdtlDetail2.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlDetail2.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail2.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlDetail2.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlDetail2.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlDetail2.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlDetail2.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlDetail2.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlDetail2);
            }
            // 常规转出
            if (!StringUtil.isBlank(Maps.getString(mapList, "NORMAL_OUT_QTY")) && !"0".equals(Maps.getString(mapList, "NORMAL_OUT_QTY"))) {

                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlOutDetail2 = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlOutDetail2.setMtc_PigType(HanaConstants.MTC_PigType_SP);
                hanaMtcPgtsdtlOutDetail2.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlOutDetail2.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlOutDetail2.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlOutDetail2.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlOutDetail2.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlOutDetail2.setMtc_TransType("3040");
                hanaMtcPgtsdtlOutDetail2.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlOutDetail2.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlOutDetail2.setMtc_InQty("0");
                hanaMtcPgtsdtlOutDetail2.setMtc_InWeight("0");
                hanaMtcPgtsdtlOutDetail2.setMtc_OutQty(Maps.getString(mapList, "NORMAL_OUT_QTY"));
                hanaMtcPgtsdtlOutDetail2.setMtc_OutWeight(Maps.getString(mapList, "NORMAL_OUT_WEIGHT"));

                hanaMtcPgtsdtlOutDetail2.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlOutDetail2.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlOutDetail2.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlOutDetail2.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlOutDetail2.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlOutDetail2.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps
                        .getString(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlOutDetail2.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlOutDetail2.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlOutDetail2);
            }
            // 后备 内转 转入
            if (!StringUtil.isBlank(Maps.getString(mapList, "HB_INNER_IN_QTY")) && !"0".equals(Maps.getString(mapList, "HB_INNER_IN_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBDetail = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlHBDetail.setMtc_PigType(HanaConstants.MTC_PigType_HB);
                hanaMtcPgtsdtlHBDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlHBDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlHBDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBDetail.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBDetail.setMtc_TransType("3050");
                hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBDetail.setMtc_InQty(Maps.getString(mapList, "HB_INNER_IN_QTY"));
                hanaMtcPgtsdtlHBDetail.setMtc_InWeight(Maps.getString(mapList, "HB_INNER_IN_WEIGHT"));
                hanaMtcPgtsdtlHBDetail.setMtc_OutQty("0");
                hanaMtcPgtsdtlHBDetail.setMtc_OutWeight("0");

                hanaMtcPgtsdtlHBDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlHBDetail.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBDetail);
            }
            // 后备 内转 转出
            if (!StringUtil.isBlank(Maps.getString(mapList, "HB_INNER_OUT_QTY")) && !"0".equals(Maps.getString(mapList, "HB_INNER_OUT_QTY"))) {

                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBOutDetail = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlHBOutDetail.setMtc_PigType(HanaConstants.MTC_PigType_HB);
                hanaMtcPgtsdtlHBOutDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlHBOutDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlHBOutDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLong(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBOutDetail.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_TransType("3050");
                hanaMtcPgtsdtlHBOutDetail.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlHBOutDetail.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBOutDetail.setMtc_InQty("0");
                hanaMtcPgtsdtlHBOutDetail.setMtc_InWeight("0");
                hanaMtcPgtsdtlHBOutDetail.setMtc_OutQty(Maps.getString(mapList, "HB_INNER_OUT_QTY"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_OutWeight(Maps.getString(mapList, "HB_INNER_OUT_WEIGHT"));

                hanaMtcPgtsdtlHBOutDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBOutDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlHBOutDetail.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps
                        .getString(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBOutDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBOutDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBOutDetail);
            }
            // 后备 常规 转入
            if (!StringUtil.isBlank(Maps.getString(mapList, "HB_NORMAL_IN_QTY")) && !"0".equals(Maps.getString(mapList, "HB_NORMAL_IN_QTY"))) {

                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBDetail2 = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlHBDetail2.setMtc_PigType(HanaConstants.MTC_PigType_HB);
                hanaMtcPgtsdtlHBDetail2.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlHBDetail2.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlHBDetail2.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail2.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBDetail2.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBDetail2.setMtc_TransType("3040");
                hanaMtcPgtsdtlHBDetail2.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlHBDetail2.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBDetail2.setMtc_InQty(Maps.getString(mapList, "HB_NORMAL_IN_QTY"));
                hanaMtcPgtsdtlHBDetail2.setMtc_InWeight(Maps.getString(mapList, "HB_NORMAL_IN_WEIGHT"));
                hanaMtcPgtsdtlHBDetail2.setMtc_OutQty("0");
                hanaMtcPgtsdtlHBDetail2.setMtc_OutWeight("0");

                hanaMtcPgtsdtlHBDetail2.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlHBDetail2.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail2.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlHBDetail2.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBDetail2.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlHBDetail2.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                        mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBDetail2.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBDetail2.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBDetail2);
            }
            // 后备 常规 转出
            if (!StringUtil.isBlank(Maps.getString(mapList, "HB_NORMAL_OUT_QTY")) && !"0".equals(Maps.getString(mapList, "HB_NORMAL_OUT_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBOutDetail2 = new HanaMtcPgtsdtlDetail();
                hanaMtcPgtsdtlHBOutDetail2.setMtc_PigType(HanaConstants.MTC_PigType_HB);
                hanaMtcPgtsdtlHBOutDetail2.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlHBOutDetail2.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlHBOutDetail2.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_Period(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_DocDate(Maps.getDate(mapList, "CHANGE_HOUSE_DATE"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_TransType("3040");
                hanaMtcPgtsdtlHBOutDetail2.setMtc_RelaDeptID(Maps.getString(HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLongClass(mapList,
                        "OUT_FARM_ID")), HanaUtil.MTC_DeptID));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_RelaUnit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_InQty("0");
                hanaMtcPgtsdtlHBOutDetail2.setMtc_InWeight("0");
                hanaMtcPgtsdtlHBOutDetail2.setMtc_OutQty(Maps.getString(mapList, "HB_NORMAL_OUT_QTY"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_OutWeight(Maps.getString(mapList, "HB_NORMAL_OUT_WEIGHT"));

                hanaMtcPgtsdtlHBOutDetail2.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_FarmName(CacheUtil.getFarmName());
                Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps
                        .getString(mapList, "OUT_HOUSE")));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                hanaMtcPgtsdtlHBOutDetail2.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBOutDetail2);
            }

        }

        // 3060 - 死亡记录
        // 3070 - 自宰
        // 3080 - 内部销售
        // 3090 - 常规销售
        SqlCon sqlCon2 = new SqlCon();
        sqlCon2.addMainSql(
                " SELECT IF(T0.SAP_SALE_TYPE = 1,'3080',IF(T0.SAP_SALE_TYPE = 2,'3090',IF(T0.SAP_SALE_TYPE = 4,'3070','3060'))) TRANSTYPE, T0.FARM_ID,T0.HOUSE_ID,T0.LEAVE_DATE DOCDATE,SUM(IF(T0.PIG_TYPE = 3,1,0)) QTY ,SUM(IF(T0.PIG_TYPE <> 3,1,0)) HB_QTY,GROUP_CONCAT(DISTINCT T1.CUSTOMER_ID+'') RESOUSE_FARM,SUM(IF(T0.PIG_TYPE = 3, IFNULL(T0.LEAVE_WEIGHT, 0), 0)) WEIGHT ,SUM(IF(T0.PIG_TYPE <> 3, IFNULL(T0.LEAVE_WEIGHT, 0), 0)) HB_WEIGHT    ");
        sqlCon2.addMainSql(" FROM PP_L_BILL_LEAVE T0 ");
        sqlCon2.addMainSql(" LEFT JOIN PP_L_BILL_PIG_SALE T1  ");
        sqlCon2.addMainSql(" ON T0.FARM_ID = T1.FARM_ID AND T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'  ");
        sqlCon2.addMainSql(" INNER JOIN PP_L_PIG T2 ");
        sqlCon2.addMainSql(
                " ON T0.FARM_ID = T2.FARM_ID AND T0.PIG_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T0.BILL_ID <> T2.BILL_ID AND T2.ORIGIN <> 3 AND T2.SAP_FIXED_ASSETS_EARBRAND IS NULL ");
        sqlCon2.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T3 ");
        sqlCon2.addMainSql(
                " ON T0.FARM_ID = T3.FARM_ID AND T0.PIG_ID = T3.PIG_ID AND T0.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        sqlCon2.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T4 ");
        sqlCon2.addMainSql(
                " ON T0.FARM_ID = T4.FARM_ID AND T0.PIG_ID = T4.PIG_ID AND T3.CHANGE_PIG_CLASS_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon2.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T5 ");
        sqlCon2.addMainSql(
                " ON T0.FARM_ID = T5.FARM_ID AND T0.PIG_ID = T5.PIG_ID AND T4.CHANGE_PIG_CLASS_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' ");
        sqlCon2.addMainSql(
                " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND IF(T0.PIG_TYPE <> 3,IF(T0.PIG_CLASS = 24 ,T5.PIG_CLASS,T0.PIG_CLASS) ,1 ) IN (1,3,4) AND (T0.SAP_SALE_TYPE <> 3 OR T0.SAP_SALE_TYPE IS NULL)");
        sqlCon2.addCondition(getFarmId(), " AND T0.FARM_ID =  ? ");
        sqlCon2.addCondition(startDate, " AND T0.LEAVE_DATE BETWEEN  ? ");
        sqlCon2.addCondition(endDate, " AND ? ");
        sqlCon2.addMainSql(" GROUP BY T0.HOUSE_ID,T0.LEAVE_DATE,T0.LEAVE_TYPE,T0.SAP_SALE_TYPE ");
        Map<String, String> sqlMap2 = new HashMap<>();
        sqlMap2.put("sql", sqlCon2.getCondition());
        List<Map<String, Object>> list2 = paramMapper.getObjectInfos(sqlMap2);

        for (Map<String, Object> mapList : list2) {
            if (!StringUtil.isBlank(Maps.getString(mapList, "QTY")) && !"0".equals(Maps.getString(mapList, "QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlDetail = new HanaMtcPgtsdtlDetail();

                hanaMtcPgtsdtlDetail.setMtc_PigType(HanaConstants.MTC_PigType_SP);
                hanaMtcPgtsdtlDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail.setMtc_Period(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlDetail.setMtc_DocDate(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlDetail.setMtc_TransType(Maps.getString(mapList, "TRANSTYPE"));
                hanaMtcPgtsdtlDetail.setMtc_RelaDeptID(null);
                hanaMtcPgtsdtlDetail.setMtc_RelaUnit(null);
                hanaMtcPgtsdtlDetail.setMtc_InQty("0");
                hanaMtcPgtsdtlDetail.setMtc_InWeight("0");
                hanaMtcPgtsdtlDetail.setMtc_OutQty(Maps.getString(mapList, "QTY"));
                hanaMtcPgtsdtlDetail.setMtc_OutWeight(Maps.getString(mapList, "WEIGHT"));

                hanaMtcPgtsdtlDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlDetail.setMtc_FarmName(CacheUtil.getFarmName());
                // Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                // hanaMtcPgtsdtlDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE_ID"));
                // hanaMtcPgtsdtlDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"), Maps.getString(
                // mapList, "OUT_HOUSE")));
                // hanaMtcPgtsdtlDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                // hanaMtcPgtsdtlDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailList.add(hanaMtcPgtsdtlDetail);
            }

            if (!StringUtil.isBlank(Maps.getString(mapList, "HB_QTY")) && !"0".equals(Maps.getString(mapList, "HB_QTY"))) {
                HanaMtcPgtsdtlDetail hanaMtcPgtsdtlHBDetail = new HanaMtcPgtsdtlDetail();

                hanaMtcPgtsdtlHBDetail.setMtc_PigType(HanaConstants.MTC_PigType_HB);
                hanaMtcPgtsdtlHBDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgtsdtlHBDetail.setMtc_DeptID(mtcDeptID);
                hanaMtcPgtsdtlHBDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Maps.getLongClass(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail.setMtc_Period(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlHBDetail.setMtc_DocDate(Maps.getDate(mapList, "DOCDATE"));
                hanaMtcPgtsdtlHBDetail.setMtc_TransType(Maps.getString(mapList, "TRANSTYPE"));
                hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID(null);
                hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit(null);
                hanaMtcPgtsdtlHBDetail.setMtc_InQty("0");
                hanaMtcPgtsdtlHBDetail.setMtc_InWeight("0");
                hanaMtcPgtsdtlHBDetail.setMtc_OutQty(Maps.getString(mapList, "HB_QTY"));
                hanaMtcPgtsdtlHBDetail.setMtc_OutWeight(Maps.getString(mapList, "HB_WEIGHT"));

                hanaMtcPgtsdtlHBDetail.setMtc_HouseId(Maps.getString(mapList, "HOUSE_ID"));
                hanaMtcPgtsdtlHBDetail.setMtc_HouseName(CacheUtil.getHouseName(Maps.getString(mapList, "HOUSE_ID")));
                hanaMtcPgtsdtlHBDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgtsdtlHBDetail.setMtc_FarmName(CacheUtil.getFarmName());
                // Map<String, String> data = CacheUtil.getData("HR_M_COMPANY", Maps.getString(mapList, "OUT_FARM_ID"));
                // hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit_HouseId(Maps.getString(mapList, "OUT_HOUSE_ID"));
                // hanaMtcPgtsdtlHBDetail.setMtc_RelaUnit_HouseName(CacheUtil.getHouseNameByFarmId(Maps.getLong(mapList, "OUT_FARM_ID"),
                // Maps.getString(
                // mapList, "OUT_HOUSE")));
                // hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID_FarmId(Maps.getString(mapList, "OUT_FARM_ID"));
                // hanaMtcPgtsdtlHBDetail.setMtc_RelaDeptID_FarmName(Maps.getString(data, "COMPANY_NAME"));

                hanaMtcPgtsdtlDetailHBList.add(hanaMtcPgtsdtlHBDetail);
            }
        }

        if (isSaveSession) {
            SecurityUtils.getSubject().getSession().setAttribute("hanaMtcPgtsdtlDetailList9998", hanaMtcPgtsdtlDetailList);
            SecurityUtils.getSubject().getSession().setAttribute("hanaMtcPgtsdtlDetailHBList9998", hanaMtcPgtsdtlDetailHBList);
            SecurityUtils.getSubject().getSession().setAttribute("endDate9998", endDate);
        }

        if (hanaMtcPgtsdtlDetailList.size() == 0 && hanaMtcPgtsdtlDetailHBList.size() == 0) {
            return null;
        }

        listMap.put("SP", hanaMtcPgtsdtlDetailList);
        listMap.put("HB", hanaMtcPgtsdtlDetailHBList);

        HanaMtcPgtsdtl hanaMtcPgtsdtl = new HanaMtcPgtsdtl();
        hanaMtcPgtsdtl.setDetailList(hanaMtcPgtsdtlDetailList);

        HanaMtcPgtsdtl hanaMtcPgtsdtl2 = new HanaMtcPgtsdtl();
        hanaMtcPgtsdtl2.setDetailList(hanaMtcPgtsdtlDetailHBList);

        SecurityUtils.getSubject().getSession().setAttribute("list9998", "list9998");
        return listMap;
        // return hanaAllDetailList;
    }

    // 日报 9997 后备情期鉴定 - 不列入转状态情况 - 转舍即为内转
    private Map<String, List<HanaMtcPgonhdDetail>> sendToSapMtcPgonhd(String startDate, String endDate, String mtcBranchID, String mtcDeptID,
            Boolean isSaveSession, Boolean isSummery) throws Exception {
        Map<String, List<HanaMtcPgonhdDetail>> listMap = new HashMap<>();
        List<HanaMtcPgonhdDetail> listHanaMtcPgonhd = new ArrayList<>();
        List<HanaMtcPgonhdDetail> listHanaMtcPgonhdHB = new ArrayList<>();
        // List<HanaMtcPgonhdDetail> allHanaMtcPgonhdList = new ArrayList<>();

        // 查询盘点表的盘点数据
        Map<String, String> sqlMap = null;
        SqlCon sqlCon = null;

        // 获取盘点的数据
        List<Map<String, Object>> infos = null;
        if (isSummery) {
            infos = getCheckPigOfTypeSummaryQty(endDate);
        } else {
            infos = getCheckPigOfTypeQty(endDate);
        }

        if (infos.size() == 0) {
            // 必须盘点后上传,不能达到要求
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "尚未盘点,不能上传至财务系统; 请确认盘点后再上传!");
        }
        // 计算当月天数
        long compareDate = TimeUtil.compareDate(endDate, startDate, Calendar.DATE) + 1;

        // 获取当天期初期末对应猪舍的猪只数量
        List<Map<String, Object>> infosPigQTY = getBegAndEndPigQtyByDateHouse(startDate, compareDate);

        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();
        // 期间出生数量
        for (int len = 0; len < compareDate; len++) {
            if (len > 0) {
                sqlCon.addMainSql(" UNION ALL ");
            }
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len),
                    " SELECT ? DATE ,T0.HOUSE_ID,T0.DELIVERY_DATE,SUM(IFNULL(T0.HEALTHY_NUM, 0)) HEALTHY_NUM,SUM(IFNULL(T0.WEAK_NUM,0)) WEAK_NUM,SUM(IFNULL(T0.STILLBIRTH_NUM,0)) STILLBIRTH_NUM,SUM(IFNULL(T0.MUTANT_NUM,0)) MUTANT_NUM,SUM(IFNULL(T0.MUMMY_NUM,0)) MUMMY_NUM,SUM(IFNULL(T0.BLACK_NUM,0)) BLACK_NUM  ");
            sqlCon.addMainSql(" FROM PP_L_BILL_DELIVERY T0 ");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
            sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID=? ");
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len), "  AND T0.DELIVERY_DATE = ? ");
            sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID,T0.DELIVERY_DATE ");
        }
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosDeliveryQTY = paramMapper.getObjectInfos(sqlMap);

        // 新购入场(后备-商品猪) 排除本厂出生 和 调拨的猪
        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();

        sqlCon.addMainSql(
                " SELECT T1.FARM_ID,T2.HOUSE_ID,T3.HOUSE_NAME,T1.ENTER_DATE,SUM(IF(T2.PIG_TYPE = 3 ,1, 0)) QTY ,SUM(IF(T2.PIG_TYPE <> 3 AND T1.ENTER_PIG_CLASS IN (1,3,4),1, 0)) HB_QTY  ");
        sqlCon.addMainSql(" FROM PP_L_PIG T1 ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T2 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.ROW_ID = T2.PIG_ID AND T1.BILL_ID = T2.BILL_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T3 ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T3.FARM_ID AND T2.HOUSE_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_LEAVE T4 ");
        sqlCon.addMainSql(" ON T1.RELATION_PIG_ID = T4.PIG_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_DELIVERY T5 ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.FARM_ID AND T1.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0'  ");

        sqlCon.addMainSql(
                " WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.ORIGIN <> 3 AND (T2.PIG_TYPE = 3 OR T1.ENTER_PIG_CLASS IN (1,3,4)) ");
        sqlCon.addMainSql(" AND IFNULL(T4.SAP_SALE_TYPE,1) IN (1,2) ");
        // 排除本厂出生的猪
        sqlCon.addMainSql(" AND T5.ROW_ID IS NULL ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID=? ");
        sqlCon.addCondition(startDate, " AND T1.ENTER_DATE BETWEEN ?  ");
        sqlCon.addCondition(endDate, " AND ?  ");
        // 排除本厂出生的猪
        sqlCon.addCondition(getFarmId(),
                " AND T1.BILL_ID NOT IN ( SELECT DISTINCT T.BILL_ID FROM PP_L_BILL_DELIVERY T WHERE T.DELETED_FLAG = '0' AND T.STATUS = '1' AND T.FARM_ID =  ? ");
        sqlCon.addCondition(startDate, " AND T.DELIVERY_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ) ");
        sqlCon.addMainSql(" GROUP BY T1.FARM_ID,T2.HOUSE_ID,T1.ENTER_DATE ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosHBMoveInQTY = paramMapper.getObjectInfos(sqlMap);

        // 后备转生产(转出到生产状态的猪舍及数量)
        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IFNULL(IFNULL(T4.HOUSE_ID,T3.HOUSE_ID),T1.HOUSE_ID) HOUSE_ID,T5.HOUSE_NAME,T1.TOWORK_DATE DATE,COUNT(1) QTY  ");
        /* T2 -> T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T1  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_PIG_CLASS_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.PIG_ID AND T1.BILL_ID = T3.BILL_ID AND T1.LINE_NUMBER = T3.LINE_NUMBER AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T4 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T4.FARM_ID AND T1.PIG_ID = T4.PIG_ID AND T4.ROW_ID = T3.CHANGE_HOUSE_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T5 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T5.FARM_ID AND IFNULL(IFNULL(T4.HOUSE_ID,T3.HOUSE_ID),T1.HOUSE_ID) = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");

        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T6 ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T1.PIG_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'  AND T6.ORIGIN <> 3 ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T2.PIG_CLASS IN (1,3,4) AND T1.PIG_CLASS IN (2,9)  ");
        sqlCon.addCondition(startDate, " AND T1.TOWORK_DATE BETWEEN ?  ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addMainSql(" GROUP BY T1.TOWORK_DATE,IFNULL(IFNULL(T4.HOUSE_ID,T3.HOUSE_ID),T1.HOUSE_ID) ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosHBToProduct = paramMapper.getObjectInfos(sqlMap);

        // 肉猪-后备 内转-常规 转入-转出
        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql("   TT.HOUSE_ID, ");
        sqlCon.addMainSql("   TT.CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql("   SUM(TT.NORMAL_IN_QTY) NORMAL_IN_QTY, ");
        sqlCon.addMainSql("   SUM(TT.INNER_IN_QTY) INNER_IN_QTY, ");
        sqlCon.addMainSql("   SUM(TT.NORMAL_OUT_QTY) NORMAL_OUT_QTY, ");
        sqlCon.addMainSql("   SUM(TT.INNER_OUT_QTY) INNER_OUT_QTY , ");
        sqlCon.addMainSql("   SUM(TT.HB_NORMAL_IN_QTY) HB_NORMAL_IN_QTY, ");
        sqlCon.addMainSql("   SUM(TT.HB_INNER_IN_QTY) HB_INNER_IN_QTY, ");
        sqlCon.addMainSql("   SUM(TT.HB_NORMAL_OUT_QTY) HB_NORMAL_OUT_QTY, ");
        sqlCon.addMainSql("   SUM(TT.HB_INNER_OUT_QTY) HB_INNER_OUT_QTY  ");
        sqlCon.addMainSql(" FROM (  ");

        /* 场内转入 排除转后备 */
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql("   T1.HOUSE_ID, ");
        sqlCon.addMainSql("   T1.CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T6.PIG_TYPE = 3 , 1, 0)) NORMAL_IN_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T6.PIG_TYPE IS NULL AND T1.PIG_TYPE = 3 , 1, 0)) INNER_IN_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T6.PIG_TYPE <> 3 AND T5.PIG_CLASS = 4 AND T6.PIG_CLASS <> 3 , 1, 0)) HB_NORMAL_IN_QTY, ");
        sqlCon.addMainSql(
                " SUM(IF((T6.PIG_TYPE IS NULL AND T1.PIG_TYPE <> 3 ) OR (T5.PIG_CLASS = 4 AND T6.PIG_CLASS = 3 ), 1, 0)) HB_INNER_IN_QTY, ");
        sqlCon.addMainSql("   0 NORMAL_OUT_QTY, ");
        sqlCon.addMainSql("   0 INNER_OUT_QTY, ");
        sqlCon.addMainSql("   0 HB_NORMAL_OUT_QTY, ");
        sqlCon.addMainSql("   0 HB_INNER_OUT_QTY  ");
        /* T2->T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1  ");
        sqlCon.addMainSql(" INNER JOIN  PP_L_BILL_CHANGE_HOUSE T2 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T2.ROW_ID = T1.CHANGE_HOUSE_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.HOUSE_ID <> T2.HOUSE_ID ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T5   ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID AND T1.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T6  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T6.FARM_ID AND T1.PIG_ID = T6.PIG_ID AND T5.CHANGE_PIG_CLASS_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T7  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T7.FARM_ID AND T1.PIG_ID = T7.ROW_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' AND T7.ORIGIN <> 3  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        // 去除入场的转入(即第一条转舍记录)
        sqlCon.addMainSql(" AND T1.BILL_ID <> T7.BILL_ID ");
        // 转出猪只状态必须是肉猪或者后备猪 t5 = null 即为 内转 pig_type pig_class 不变 因此使用T1 的数据
        sqlCon.addMainSql(" AND (IFNULL(T5.PIG_TYPE,T1.PIG_TYPE) = 3 OR IFNULL(T5.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4)) ");
        // 去除 转栏不转舍(转出舍 = 转入舍,奶妈转舍)
        sqlCon.addMainSql(" AND T1.HOUSE_ID <> T2.HOUSE_ID ");
        // 排除转后备 - 转后备不一定转舍,查询不全面
        sqlCon.addMainSql(" AND IF(T5.PIG_TYPE IS NOT NULL , T5.PIG_TYPE = T6.PIG_TYPE ,' 1=1 ') ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T1.CHANGE_HOUSE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T1.HOUSE_ID ,T1.CHANGE_HOUSE_DATE  ");

        sqlCon.addMainSql(" UNION ALL  ");
        /* 场间转入 */
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql("   T7.HOUSE_ID, ");
        sqlCon.addMainSql("   T0.ENTER_DATE CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,1,0)) NORMAL_IN_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,1,0)) INNER_IN_QTY,  ");
        sqlCon.addMainSql(
                " SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS,1,0)) HB_NORMAL_IN_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4) AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS,1,0)) HB_INNER_IN_QTY, ");
        sqlCon.addMainSql("   0 NORMAL_OUT_QTY,0 INNER_OUT_QTY,0 HB_NORMAL_OUT_QTY,0 HB_INNER_OUT_QTY  ");
        sqlCon.addMainSql(" FROM PP_L_PIG T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T1   ");
        sqlCon.addMainSql(
                " ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.SAP_SALE_TYPE = 3 AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.SAP_SALE_TYPE = 3  ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2   ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T3   ");
        sqlCon.addMainSql(
                " ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T5   ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T6   ");
        sqlCon.addMainSql(
                " ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T7  ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'  ");
        sqlCon.addMainSql(
                " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.ORIGIN <> 3 AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) = IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE) ");
        sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ?  ");
        sqlCon.addCondition(startDate, " AND T0.ENTER_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T7.HOUSE_ID,T0.ENTER_DATE  ");

        /* 场内转出 排除转后备 */
        sqlCon.addMainSql(" UNION ALL  ");
        sqlCon.addMainSql(" SELECT T2.HOUSE_ID, T1.CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" 0 NORMAL_IN_QTY, ");
        sqlCon.addMainSql(" 0 INNER_IN_QTY, ");
        sqlCon.addMainSql(" 0 HB_NORMAL_IN_QTY, ");
        sqlCon.addMainSql(" 0 HB_INNER_IN_QTY, ");

        sqlCon.addMainSql(" SUM(IF(T6.PIG_TYPE = 3 , 1, 0)) NORMAL_OUT_QTY,   ");
        sqlCon.addMainSql(" SUM(IF(T6.PIG_TYPE IS NULL AND T1.PIG_TYPE = 3 , 1, 0)) INNER_OUT_QTY,   ");
        sqlCon.addMainSql(" SUM(IF(T6.PIG_TYPE <> 3 AND T5.PIG_CLASS = 4 AND T6.PIG_CLASS <> 3 , 1, 0)) HB_NORMAL_OUT_QTY,  ");
        sqlCon.addMainSql(
                " SUM(IF((T6.PIG_TYPE IS NULL AND T1.PIG_TYPE <> 3 ) OR (T5.PIG_CLASS = 4 AND T6.PIG_CLASS = 3 ), 1, 0)) HB_INNER_OUT_QTY ");
        // T2 --> T1
        sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T2.ROW_ID = T1.CHANGE_HOUSE_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T5  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID AND T1.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T6  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T6.FARM_ID AND T1.PIG_ID = T6.PIG_ID AND T5.CHANGE_PIG_CLASS_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T7  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T7.FARM_ID AND T1.PIG_ID = T7.ROW_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' AND T7.ORIGIN <> 3  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        // 去除 转栏不转舍(转出舍 = 转入舍,奶妈转舍)
        sqlCon.addMainSql(" AND T1.HOUSE_ID <> T2.HOUSE_ID  ");
        // 转出猪只状态必须是肉猪或者后备猪 T5 = null 即为 内转 pig_type pig_class 不变 因此使用T1 的数据
        sqlCon.addMainSql(" AND (IFNULL(T5.PIG_TYPE,T1.PIG_TYPE) = 3 OR IFNULL(T5.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4)) ");
        // 排除转后备的情况
        sqlCon.addMainSql(" AND IFNULL(T5.PIG_TYPE,1) = IFNULL(T6.PIG_TYPE,1) ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T1.CHANGE_HOUSE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE ");

        /* 场间转出 */
        sqlCon.addMainSql(" UNION ALL  ");
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql("   T1.HOUSE_ID, ");
        sqlCon.addMainSql("   T1.LEAVE_DATE CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql("   0 NORMAL_IN_QTY, ");
        sqlCon.addMainSql("   0 INNER_IN_QTY, ");
        sqlCon.addMainSql("   0 HB_NORMAL_IN_QTY, ");
        sqlCon.addMainSql("   0 HB_INNER_IN_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE = 3 AND t0.ENTER_PIG_CLASS > t1.PIG_CLASS,1,0)) NORMAL_OUT_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE = 3 AND t0.ENTER_PIG_CLASS = t1.PIG_CLASS ,1,0)) INNER_OUT_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE <> 3 AND t1.PIG_CLASS IN (1,3,4) AND t0.ENTER_PIG_CLASS > t1.PIG_CLASS,1,0)) HB_NORMAL_OUT_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T7.PIG_TYPE <> 3 AND t1.PIG_CLASS IN (1,3,4) AND t0.ENTER_PIG_CLASS = t1.PIG_CLASS ,1,0)) HB_INNER_OUT_QTY  ");
        sqlCon.addMainSql(" FROM PP_L_PIG T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T1   ");
        sqlCon.addMainSql(
                " ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.SAP_SALE_TYPE = 3 AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.SAP_SALE_TYPE = 3 AND t0.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2   ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T3   ");
        sqlCon.addMainSql(
                " ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T5   ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T6   ");
        sqlCon.addMainSql(
                " ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T7 ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  ");
        sqlCon.addMainSql(" AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) = IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE)  ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCon.addCondition(startDate, " AND T1.LEAVE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND  ?  ");
        sqlCon.addMainSql(" GROUP BY T1.HOUSE_ID,T1.LEAVE_DATE  ");

        // 转后备 转出
        sqlCon.addMainSql(" UNION ALL ");
        sqlCon.addMainSql(" SELECT IFNULL(T4.HOUSE_ID,T1.HOUSE_ID) HOUSE_ID, T0.TOWORK_DATE CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" 0 NORMAL_IN_QTY,  ");
        sqlCon.addMainSql(" 0 INNER_IN_QTY,  ");
        sqlCon.addMainSql(" 0 HB_NORMAL_IN_QTY,  ");
        sqlCon.addMainSql(" 0 HB_INNER_IN_QTY,  ");
        sqlCon.addMainSql(" 0 NORMAL_OUT_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3,1,0)) INNER_OUT_QTY,   ");
        sqlCon.addMainSql(" 0 HB_NORMAL_OUT_QTY,  ");
        sqlCon.addMainSql(" 0 HB_INNER_OUT_QTY   ");
        // T1-->T0
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T1   ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.PIG_ID AND T0.CHANGE_PIG_CLASS_ID = T1.ROW_ID AND T0.PIG_TYPE <> T1.PIG_TYPE AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'   ");
        // 转入
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3  ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T3.FARM_ID AND T0.PIG_ID = T3.PIG_ID AND T0.BILL_ID = T3.BILL_ID AND T0.LINE_NUMBER = T3.LINE_NUMBER AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'   ");
        // 转出
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T4   ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T4.FARM_ID AND T0.PIG_ID = T4.PIG_ID AND T3.CHANGE_HOUSE_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1'  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T6   ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T6.FARM_ID AND T0.PIG_ID = T6.ROW_ID AND T6.ORIGIN <> 3 AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T7  ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T7.FARM_ID AND IFNULL(T4.HOUSE_ID,T1.HOUSE_ID) = T7.ROW_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T1.PIG_TYPE = 3 AND T0.PIG_TYPE <> 3   ");
        sqlCon.addCondition(getFarmId(), "AND T0.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T0.TOWORK_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY IFNULL(T4.HOUSE_ID,T1.HOUSE_ID),T1.TOWORK_DATE  ");

        // 转后备 转入
        sqlCon.addMainSql(" UNION ALL ");
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql(" IFNULL(T3.HOUSE_ID,T0.HOUSE_ID) HOUSE_ID,  ");
        sqlCon.addMainSql(" T0.TOWORK_DATE CHANGE_HOUSE_DATE,   ");
        sqlCon.addMainSql(" 0 NORMAL_IN_QTY,    ");
        sqlCon.addMainSql(" 0 INNER_IN_QTY,    ");
        sqlCon.addMainSql(" 0 HB_NORMAL_IN_QTY,    ");
        sqlCon.addMainSql(" COUNT(1) HB_INNER_IN_QTY,    ");
        sqlCon.addMainSql(" 0 NORMAL_OUT_QTY,    ");
        sqlCon.addMainSql(" 0 INNER_OUT_QTY,    ");
        sqlCon.addMainSql(" 0 HB_NORMAL_OUT_QTY,    ");
        sqlCon.addMainSql(" 0 HB_INNER_OUT_QTY ");
        // T1 -> T0
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T1   ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.PIG_ID AND T0.CHANGE_PIG_CLASS_ID = T1.ROW_ID AND T0.PIG_TYPE <> T1.PIG_TYPE AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3  ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T3.FARM_ID AND T0.PIG_ID = T3.PIG_ID AND T0.BILL_ID = T3.BILL_ID AND T0.LINE_NUMBER = T3.LINE_NUMBER  AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T6   ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T6.FARM_ID AND T0.PIG_ID = T6.ROW_ID AND T6.ORIGIN <> 3 AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'   ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T1.PIG_TYPE = 3 AND T0.PIG_TYPE <> 3   ");
        sqlCon.addCondition(getFarmId(), "   AND T0.FARM_ID = ? ");
        sqlCon.addCondition(startDate, "  AND T0.TOWORK_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, "  AND ? ");
        sqlCon.addMainSql(" GROUP BY T0.FARM_ID,IFNULL(T3.HOUSE_ID,T0.HOUSE_ID),T0.TOWORK_DATE  ");

        sqlCon.addMainSql(" ) TT  ");
        sqlCon.addMainSql(" GROUP BY TT.HOUSE_ID,TT.CHANGE_HOUSE_DATE  ");
        sqlCon.addMainSql(" ORDER BY TT.CHANGE_HOUSE_DATE  ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> pigNZInfos = paramMapper.getObjectInfos(sqlMap);

        // 只关心 转入后的状态
        // 转出到产房
        // 转出到保育
        // 转出到培育
        // 转出到后备
        // 转出到育肥
        List<Map<String, Object>> infosOutToHouseQTY = null;
        if (isSummery) {
            infosOutToHouseQTY = getInfosOutToHouseSummaryQTY(startDate, endDate);
        } else {
            infosOutToHouseQTY = getInfosOutToHouseQTY(startDate, endDate);
        }

        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();
        // 死亡
        // 自宰
        // 内部销售
        // 外部销售
        sqlCon.addMainSql(" SELECT T0.FARM_ID,T0.HOUSE_ID,DATE(T0.LEAVE_DATE) LEAVE_DATE, ");
        sqlCon.addMainSql("  SUM(IF(T0.LEAVE_TYPE IN (2, 3, 4) AND T0.PIG_TYPE = 3, 1, 0)) DIEQTY, ");
        sqlCon.addMainSql("  SUM(IF(T0.SAP_SALE_TYPE = 1 AND T0.PIG_TYPE = 3, 1, 0)) SALESQTY_INNER, ");
        sqlCon.addMainSql("  SUM(IF(T0.SAP_SALE_TYPE = 2 AND T0.PIG_TYPE = 3, 1, 0)) SALESQTY_NORMAR, ");
        sqlCon.addMainSql("  SUM(IF(T0.SAP_SALE_TYPE = 4 AND T0.PIG_TYPE = 3, 1, 0)) KILLQTY , ");
        sqlCon.addMainSql("  SUM(IF(T0.LEAVE_TYPE IN (2, 3, 4) AND T0.PIG_TYPE <> 3 , 1, 0)) HB_DIEQTY, ");
        sqlCon.addMainSql("  SUM(IF(T0.SAP_SALE_TYPE = 1 AND T0.PIG_TYPE <> 3 , 1, 0)) HB_SALESQTY_INNER, ");
        sqlCon.addMainSql("  SUM(IF(T0.SAP_SALE_TYPE = 2 AND T0.PIG_TYPE <> 3 , 1, 0)) HB_SALESQTY_NORMAR, ");
        sqlCon.addMainSql("  SUM(IF(T0.SAP_SALE_TYPE = 4 AND T0.PIG_TYPE <> 3 , 1, 0)) HB_KILLQTY  ");
        sqlCon.addMainSql(" FROM PP_L_BILL_LEAVE T0  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T.FARM_ID AND T0.PIG_ID = T.ROW_ID AND T0.BILL_ID <> T.BILL_ID  AND t.ORIGIN <> 3 ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T3 ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T3.FARM_ID AND T0.PIG_ID = T3.PIG_ID AND T0.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T4 ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T4.FARM_ID AND T0.PIG_ID = T4.PIG_ID AND T3.CHANGE_PIG_CLASS_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T5 ");
        sqlCon.addMainSql(
                " ON T0.FARM_ID = T5.FARM_ID AND T0.PIG_ID = T5.PIG_ID AND T4.CHANGE_PIG_CLASS_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' ");

        sqlCon.addMainSql(
                " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND IF(T0.PIG_TYPE <> 3,IF(T0.PIG_CLASS = 24 ,T5.PIG_CLASS,T0.PIG_CLASS) ,1 ) IN (1,3,4) ");
        sqlCon.addCondition(startDate, " AND T0.LEAVE_DATE BETWEEN  ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
        sqlCon.addMainSql(" GROUP BY T0.FARM_ID,T0.HOUSE_ID,T0.LEAVE_DATE ORDER BY T0.LEAVE_DATE  ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosSZXQTY = paramMapper.getObjectInfos(sqlMap);

        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();
        // 猪舍
        sqlCon.addMainSql(" SELECT T0.FARM_ID,T1.COMPANY_NAME,T0.ROW_ID HOUSE_ID,T0.HOUSE_NAME,T0.HOUSE_TYPE,T0.IS_FOSTER_HOUSE ");
        sqlCon.addMainSql(" FROM PP_O_HOUSE T0 ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T1 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addMainSql(" AND T0.HOUSE_PRIFIX_CODE IS NOT NULL ");
        sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
        sqlCon.addMainSql(" AND T0.HOUSE_TYPE NOT IN (10,11,12,13,14) ");
        sqlCon.addMainSql(" ORDER BY T0.HOUSE_TYPE,T0.HOUSE_NAME ");

        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosHouse = paramMapper.getObjectInfos(sqlMap);

        for (int a = 0; a < compareDate; a++) {
            String dateAddDay = TimeUtil.dateAddDay(startDate, a);
            for (Map<String, Object> houseMap : infosHouse) {
                HanaMtcPgonhdDetail hanaMtcPgonhdDetail = new HanaMtcPgonhdDetail();
                HanaMtcPgonhdDetail hanaHBMtcPgonhdDetail = new HanaMtcPgonhdDetail();

                hanaMtcPgonhdDetail.setMtc_HouseId(Maps.getString(houseMap, "HOUSE_ID"));
                hanaMtcPgonhdDetail.setMtc_IS_FOSTER_HOUSE(Maps.getString(houseMap, "IS_FOSTER_HOUSE"));
                hanaMtcPgonhdDetail.setMtc_HouseName(Maps.getString(houseMap, "HOUSE_NAME"));
                hanaMtcPgonhdDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaMtcPgonhdDetail.setMtc_FarmName(Maps.getString(houseMap, "COMPANY_NAME"));
                hanaMtcPgonhdDetail.setMtc_BranchID(mtcBranchID);
                hanaMtcPgonhdDetail.setMtc_DeptID(mtcDeptID);
                // 日期
                hanaMtcPgonhdDetail.setMtc_DocDate(TimeUtil.parseDate(dateAddDay));
                hanaMtcPgonhdDetail.setMtc_Period(TimeUtil.parseDate(dateAddDay));
                // 除了盘点的重量外都设置为空(0)
                hanaMtcPgonhdDetail.setMtc_Weight("0");
                // 日龄暂时为空
                hanaMtcPgonhdDetail.setMtc_DaySum("0");
                String mapHouseId = Maps.getString(houseMap, "HOUSE_ID");
                hanaMtcPgonhdDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Long.valueOf(mapHouseId)));
                // 猪只类型
                hanaMtcPgonhdDetail.setMtc_PigType(HanaConstants.MTC_PigType_SP);

                hanaHBMtcPgonhdDetail.setMtc_HouseId(Maps.getString(houseMap, "HOUSE_ID"));
                hanaHBMtcPgonhdDetail.setMtc_HouseName(Maps.getString(houseMap, "HOUSE_NAME"));
                hanaHBMtcPgonhdDetail.setMtc_FarmId(String.valueOf(getFarmId()));
                hanaHBMtcPgonhdDetail.setMtc_FarmName(Maps.getString(houseMap, "COMPANY_NAME"));
                hanaHBMtcPgonhdDetail.setMtc_BranchID(mtcBranchID);
                hanaHBMtcPgonhdDetail.setMtc_DeptID(mtcDeptID);
                hanaHBMtcPgonhdDetail.setMtc_DocDate(TimeUtil.parseDate(dateAddDay));
                hanaHBMtcPgonhdDetail.setMtc_Period(TimeUtil.parseDate(dateAddDay));
                hanaHBMtcPgonhdDetail.setMtc_Weight("0");
                hanaHBMtcPgonhdDetail.setMtc_DaySum("0");
                hanaHBMtcPgonhdDetail.setMtc_Unit(HanaUtil.getMTC_Unit(Long.valueOf(mapHouseId)));
                hanaHBMtcPgonhdDetail.setMtc_PigType(HanaConstants.MTC_PigType_HB);

                // 后备转生产
                hanaMtcPgonhdDetail.setMtc_TransToSC("0");
                hanaHBMtcPgonhdDetail.setMtc_TransToSC("0");
                for (Map<String, Object> pigHouse : infosHBToProduct) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "DATE");
                    if (mapHouseId.equals(HouseId) && 0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                        hanaMtcPgonhdDetail.setMtc_TransToSC("0");
                        hanaHBMtcPgonhdDetail.setMtc_TransToSC(Maps.getString(pigHouse, "QTY"));
                        break;
                    }
                }

                // 期末存栏 月末
                hanaMtcPgonhdDetail.setMtc_EndQty("0");
                hanaHBMtcPgonhdDetail.setMtc_EndQty("0");
                // 期末存栏 -- 新增肉猪盘点数据
                // 最后一天的盘点取盘点,没有盘点的就取 sql 的
                if (0 == TimeUtil.compareDate(dateAddDay, endDate, Calendar.DATE)) {
                    for (Map<String, Object> checkPigHouse : infos) {
                        String houseId = Maps.getString(checkPigHouse, "HOUSE_ID");
                        String lastDate = Maps.getString(checkPigHouse, "CHECK_DATE");
                        // 最后一天
                        if (mapHouseId.equals(houseId) && 0 == TimeUtil.compareDate(dateAddDay, lastDate, Calendar.DATE)) {
                            if (5 == Maps.getLong(checkPigHouse, "CHECK_PIG_TYPE")) {
                                hanaMtcPgonhdDetail.setMtc_EndQty(String.valueOf(Maps.getLong(checkPigHouse, "CHECK_QTY")));
                                // 日龄
                                hanaMtcPgonhdDetail.setMtc_DaySum(Maps.getString(checkPigHouse, "SWINERY_AGE"));
                                // 期初为实际盘点的重量
                                hanaMtcPgonhdDetail.setMtc_Weight(Maps.getString(checkPigHouse, "TOTAL_WEIGHT"));
                            }

                            if (2 == Maps.getLong(checkPigHouse, "CHECK_PIG_TYPE")) {
                                // 后备猪
                                hanaHBMtcPgonhdDetail.setMtc_EndQty(String.valueOf(Maps.getLong(checkPigHouse, "CHECK_QTY")));
                                hanaHBMtcPgonhdDetail.setMtc_DaySum(Maps.getString(checkPigHouse, "SWINERY_AGE"));
                                hanaHBMtcPgonhdDetail.setMtc_Weight(Maps.getString(checkPigHouse, "TOTAL_WEIGHT"));
                            }
                            break;
                        }
                    }
                }
                // 期初 + 期末存栏 非盘点月末
                hanaMtcPgonhdDetail.setMtc_BegQty("0");
                hanaHBMtcPgonhdDetail.setMtc_BegQty("0");
                for (Map<String, Object> pigHouse : infosPigQTY) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "DATE");
                    if (mapHouseId.equals(HouseId)) {
                        // 期末的期初
                        if (0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                            if ("0".equals(hanaMtcPgonhdDetail.getMtc_BegQty())) {
                                hanaMtcPgonhdDetail.setMtc_BegQty(Maps.getString(pigHouse, "QTY"));
                            }
                            if ("0".equals(hanaHBMtcPgonhdDetail.getMtc_BegQty())) {
                                hanaHBMtcPgonhdDetail.setMtc_BegQty(Maps.getString(pigHouse, "HB_QTY"));
                            }
                            // 期末
                        } else if (-1 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                            if (0 == TimeUtil.compareDate(dateAddDay, endDate, Calendar.DATE)) {
                                String houseName = Maps.getString(houseMap, "HOUSE_NAME");
                                // 肉猪
                                String endQTY = Maps.getString(pigHouse, "QTY");
                                String mtcEndQty = hanaMtcPgonhdDetail.getMtc_EndQty();
                                // 后备
                                String mtcEndHBQTY = hanaHBMtcPgonhdDetail.getMtc_EndQty();
                                String endHBQTY = Maps.getString(pigHouse, "HB_QTY");
                                if (!mtcEndQty.equals(endQTY) || !mtcEndHBQTY.equals(endHBQTY)) {
                                    if (!"0".equals(hanaMtcPgonhdDetail.getMtc_EndQty())) {
                                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "[ " + houseName + " ]:[ " + dateAddDay + " ]肉猪盘点"
                                                + mtcEndQty + "与系统" + endQTY + "数量不一致,请重新盘点!");
                                    } else if (!"0".equals(hanaHBMtcPgonhdDetail.getMtc_EndQty())) {
                                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "[ " + houseName + " ]:[ " + dateAddDay + " ]后备猪盘点"
                                                + mtcEndHBQTY + "与系统" + endHBQTY + "数量不一致,请重新盘点!");
                                    } else {
                                        // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "[ " + dateAddDay + " ] [ " + houseName
                                        // + " ]:盘点:0,与系统肉猪:" + endQTY + ",后备猪:" + endHBQTY + "数量不一致,请重新盘点!");
                                    }
                                }
                            }
                            hanaMtcPgonhdDetail.setMtc_EndQty(Maps.getString(pigHouse, "QTY"));
                            hanaHBMtcPgonhdDetail.setMtc_EndQty(Maps.getString(pigHouse, "HB_QTY"));
                        }
                    }
                    if (!"0".equals(hanaMtcPgonhdDetail.getMtc_BegQty()) && !"0".equals(hanaMtcPgonhdDetail.getMtc_EndQty())) {
                        break;
                    }

                }
                // 期间初生数量
                hanaMtcPgonhdDetail.setMtc_InQty("0");
                hanaHBMtcPgonhdDetail.setMtc_InQty("0");
                for (Map<String, Object> pigHouse : infosDeliveryQTY) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "DATE");
                    if (mapHouseId.equals(HouseId) && 0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                        hanaMtcPgonhdDetail.setMtc_InQty(Maps.getString(pigHouse, "HEALTHY_NUM"));
                        hanaHBMtcPgonhdDetail.setMtc_InQty("0");
                        break;
                    }
                }
                // 新购入场(商品猪-肉猪)
                hanaMtcPgonhdDetail.setMtc_PurQty("0");
                hanaHBMtcPgonhdDetail.setMtc_PurQty("0");
                for (Map<String, Object> pigHouse : infosHBMoveInQTY) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "ENTER_DATE");
                    if (mapHouseId.equals(HouseId) && 0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                        hanaMtcPgonhdDetail.setMtc_PurQty(Maps.getString(pigHouse, "QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_PurQty(Maps.getString(pigHouse, "HB_QTY"));
                        break;
                    }
                }
                // 期间转入转出
                hanaMtcPgonhdDetail.setMtc_InQty_Inner("0");
                hanaMtcPgonhdDetail.setMtc_InQty_Normal("0");
                hanaMtcPgonhdDetail.setMtc_OutQty_Inner("0");
                hanaMtcPgonhdDetail.setMtc_OutQty_Normal("0");
                hanaHBMtcPgonhdDetail.setMtc_InQty_Inner("0");
                hanaHBMtcPgonhdDetail.setMtc_InQty_Normal("0");
                hanaHBMtcPgonhdDetail.setMtc_OutQty_Inner("0");
                hanaHBMtcPgonhdDetail.setMtc_OutQty_Normal("0");
                for (Map<String, Object> pigHouse : pigNZInfos) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "CHANGE_HOUSE_DATE");
                    if (mapHouseId.equals(HouseId) && 0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                        hanaMtcPgonhdDetail.setMtc_InQty_Inner(Maps.getString(pigHouse, "INNER_IN_QTY"));
                        hanaMtcPgonhdDetail.setMtc_InQty_Normal(Maps.getString(pigHouse, "NORMAL_IN_QTY"));
                        hanaMtcPgonhdDetail.setMtc_OutQty_Inner(Maps.getString(pigHouse, "INNER_OUT_QTY"));
                        hanaMtcPgonhdDetail.setMtc_OutQty_Normal(Maps.getString(pigHouse, "NORMAL_OUT_QTY"));

                        hanaHBMtcPgonhdDetail.setMtc_InQty_Inner(Maps.getString(pigHouse, "HB_INNER_IN_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_InQty_Normal(Maps.getString(pigHouse, "HB_NORMAL_IN_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_OutQty_Inner(Maps.getString(pigHouse, "HB_INNER_OUT_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_OutQty_Normal(Maps.getString(pigHouse, "HB_NORMAL_OUT_QTY"));
                        // 存栏调整 不知道应该填什么 暂时填 "0"
                        hanaMtcPgonhdDetail.setMtc_AdjQty("0");
                        hanaHBMtcPgonhdDetail.setMtc_AdjQty("0");
                        break;
                    }
                }

                // 转出到产房
                // 转出到保育
                // 转出到培育
                // 转出到后备
                // 转出到育肥
                hanaMtcPgonhdDetail.setMtc_TransToCF("0");
                hanaMtcPgonhdDetail.setMtc_TransToBY("0");
                hanaMtcPgonhdDetail.setMtc_AdjQty("0");
                hanaMtcPgonhdDetail.setMtc_TransToHB("0");
                hanaMtcPgonhdDetail.setMtc_TransToYF("0");
                hanaMtcPgonhdDetail.setMtc_TransToPY("0");
                //
                hanaHBMtcPgonhdDetail.setMtc_TransToCF("0");
                hanaHBMtcPgonhdDetail.setMtc_TransToBY("0");
                hanaHBMtcPgonhdDetail.setMtc_AdjQty("0");
                hanaHBMtcPgonhdDetail.setMtc_TransToHB("0");
                hanaHBMtcPgonhdDetail.setMtc_TransToYF("0");
                hanaHBMtcPgonhdDetail.setMtc_TransToPY("0");
                for (Map<String, Object> pigHouse : infosOutToHouseQTY) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "CHANGE_HOUSE_DATE");
                    if (mapHouseId.equals(HouseId) && 0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                        hanaMtcPgonhdDetail.setMtc_TransToCF(Maps.getString(pigHouse, "CF_QTY"));
                        hanaMtcPgonhdDetail.setMtc_TransToBY(Maps.getString(pigHouse, "BY_QTY"));
                        hanaMtcPgonhdDetail.setMtc_AdjQty("0");
                        hanaMtcPgonhdDetail.setMtc_TransToHB(Maps.getString(pigHouse, "HB_QTY"));
                        hanaMtcPgonhdDetail.setMtc_TransToYF(Maps.getString(pigHouse, "YF_QTY"));
                        hanaMtcPgonhdDetail.setMtc_TransToPY(Maps.getString(pigHouse, "PY_QTY"));
                        // 后备猪需要
                        hanaHBMtcPgonhdDetail.setMtc_TransToCF(Maps.getString(pigHouse, "HB_CF_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_TransToBY(Maps.getString(pigHouse, "HB_BY_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_AdjQty("0");
                        hanaHBMtcPgonhdDetail.setMtc_TransToHB(Maps.getString(pigHouse, "HB_HB_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_TransToYF(Maps.getString(pigHouse, "HB_YF_QTY"));
                        hanaHBMtcPgonhdDetail.setMtc_TransToPY(Maps.getString(pigHouse, "HB_PY_QTY"));
                        break;
                    }
                }

                // 死亡
                // 自宰
                // 内部销售
                // 外部销售
                // 存栏调整
                hanaMtcPgonhdDetail.setMtc_DieQty("0");
                hanaMtcPgonhdDetail.setMtc_KillQty("0");
                hanaMtcPgonhdDetail.setMtc_SalesQty_Inner("0");
                hanaMtcPgonhdDetail.setMtc_SalesQty_Normar("0");
                //
                hanaHBMtcPgonhdDetail.setMtc_DieQty("0");
                hanaHBMtcPgonhdDetail.setMtc_KillQty("0");
                hanaHBMtcPgonhdDetail.setMtc_SalesQty_Inner("0");
                hanaHBMtcPgonhdDetail.setMtc_SalesQty_Normar("0");
                for (Map<String, Object> pigHouse : infosSZXQTY) {
                    String HouseId = Maps.getString(pigHouse, "HOUSE_ID");
                    String Date = Maps.getString(pigHouse, "LEAVE_DATE");
                    if (mapHouseId.equals(HouseId) && 0 == TimeUtil.compareDate(dateAddDay, Date, Calendar.DATE)) {
                        hanaMtcPgonhdDetail.setMtc_DieQty(Maps.getString(pigHouse, "DIEQTY"));
                        hanaMtcPgonhdDetail.setMtc_KillQty(Maps.getString(pigHouse, "KILLQTY"));
                        hanaMtcPgonhdDetail.setMtc_SalesQty_Inner(Maps.getString(pigHouse, "SALESQTY_INNER"));
                        hanaMtcPgonhdDetail.setMtc_SalesQty_Normar(Maps.getString(pigHouse, "SALESQTY_NORMAR"));

                        hanaHBMtcPgonhdDetail.setMtc_DieQty(Maps.getString(pigHouse, "HB_DIEQTY"));
                        hanaHBMtcPgonhdDetail.setMtc_KillQty(Maps.getString(pigHouse, "HB_KILLQTY"));
                        hanaHBMtcPgonhdDetail.setMtc_SalesQty_Inner(Maps.getString(pigHouse, "HB_SALESQTY_INNER"));
                        hanaHBMtcPgonhdDetail.setMtc_SalesQty_Normar(Maps.getString(pigHouse, "HB_SALESQTY_NORMAR"));
                        break;
                    }
                }

                listHanaMtcPgonhd.add(hanaMtcPgonhdDetail);
                listHanaMtcPgonhdHB.add(hanaHBMtcPgonhdDetail);
            }
        }
        if (isSaveSession) {
            SecurityUtils.getSubject().getSession().setAttribute("listHanaMtcPgonhd9997", listHanaMtcPgonhd);
            SecurityUtils.getSubject().getSession().setAttribute("listHanaMtcPgonhdHB9997", listHanaMtcPgonhdHB);
            SecurityUtils.getSubject().getSession().setAttribute("endDate9997", endDate);
        }
        if (listHanaMtcPgonhd.size() == 0) {
            return null;
        }

        listMap.put("HB", listHanaMtcPgonhdHB);
        listMap.put("SP", listHanaMtcPgonhd);

        HanaMtcPgonhd hanaMtcPgonhd = new HanaMtcPgonhd();
        hanaMtcPgonhd.setDetailList(listHanaMtcPgonhd);
        HanaMtcPgonhd hanaMtcPgonhd2 = new HanaMtcPgonhd();
        hanaMtcPgonhd2.setDetailList(listHanaMtcPgonhdHB);

        SecurityUtils.getSubject().getSession().setAttribute("list9997", "list9997");
        return listMap;
        // return allHanaMtcPgonhdList;
    }

    // 获取对应猪舍当天期初 的猪只数量
    private List<Map<String, Object>> getBegAndEndPigQtyByDateHouse(String startDate, Long compareDate) throws Exception {
        Map<String, String> sqlMap = new HashMap<String, String>();
        SqlCon sqlCon = new SqlCon();
        // 期初当天对应的昨天的存栏数据 -- 查询上月最后一天到本月倒数第二天的每日存栏

        for (int len = 0; len <= compareDate; len++) {
            if (len > 0) {
                sqlCon.addMainSql(" UNION ALL ");
            }
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len),
                    " SELECT ? DATE,T1.HOUSE_ID,T0.HOUSE_NAME,SUM(IF(T2.PIG_TYPE = 3,1,0)) QTY,SUM(IF(T2.PIG_TYPE <> 3 AND IFNULL(T2.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_QTY  ");
            sqlCon.addMainSql(" FROM PP_O_HOUSE T0 ");
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T1 ");
            sqlCon.addMainSql(" ON T0.FARM_ID = T1.FARM_ID AND T0.ROW_ID = T1.HOUSE_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
            sqlCon.addMainSql(" LEFT JOIN ( ");
            sqlCon.addMainSql(" SELECT T.FARM_ID,T.PIG_ID,IF(T.PIG_CLASS = 24 ,U.PIG_CLASS,T.PIG_CLASS) PIG_CLASS,T.PIG_TYPE, ");
            sqlCon.addMainSql(" IF(T.PIG_CLASS = 24 ,U.TOWORK_DATE,T.TOWORK_DATE) TOWORK_DATE  ");
            sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T  ");
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK U ");
            sqlCon.addMainSql(
                    " ON T.FARM_ID = U.FARM_ID AND T.PIG_ID = U.PIG_ID AND T.CHANGE_PIG_CLASS_ID = U.ROW_ID AND U.DELETED_FLAG = '0' AND U.STATUS = '1'  ");
            sqlCon.addMainSql(" WHERE T.DELETED_FLAG = '0' AND T.STATUS = '1'  ");
            sqlCon.addMainSql(" AND IF(T.PIG_CLASS = 24 ,U.PIG_CLASS,T.PIG_CLASS) <= 18  ");
            sqlCon.addMainSql(" AND IF(T.PIG_CLASS = 24 ,U.PIG_CLASS,T.PIG_CLASS) IN (1,3,4,12,13,14,15,16,17,18)  ");
            sqlCon.addCondition(getFarmId(), " AND T.FARM_ID = ?  ");
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len - 1), " AND T.TOWORK_DATE <= ? ");
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len - 1), " AND IFNULL(T.TOWORK_DATE_OUT,DATE_ADD(NOW(),INTERVAL 1 DAY)) > ? ");
            sqlCon.addMainSql("  ) T2 ");
            sqlCon.addMainSql(" ON T0.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID  ");
            sqlCon.addMainSql(" INNER JOIN pp_l_pig T3 ");
            sqlCon.addMainSql(" ON t0.FARM_ID = t3.FARM_ID AND t1.PIG_ID = t3.ROW_ID AND t3.DELETED_FLAG = '0' AND t3.STATUS = '1' ");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  AND t3.ORIGIN <> 3 ");
            sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len - 1), " AND T1.CHANGE_HOUSE_DATE <= ? ");
            sqlCon.addCondition(TimeUtil.dateAddDay(startDate, len - 1),
                    " AND IFNULL(T1.CHANGE_HOUSE_DATE_OUT,ifnull(t3.LEAVE_DATE,DATE_ADD(NOW(),INTERVAL 1 DAY))) > ? ");
            sqlCon.addMainSql(" GROUP BY T1.HOUSE_ID ");
        }
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosPigQTY = paramMapper.getObjectInfos(sqlMap);
        return infosPigQTY;
    }

    // 获取盘点数据 -- 汇总
    private List<Map<String, Object>> getCheckPigOfTypeSummaryQty(String endDate) {
        Map<String, String> sqlMap = new HashMap<String, String>();
        SqlCon sqlCon = new SqlCon();
        /**
         * 盘点猪只类型
         * 1:生产公猪，3:生产母猪，
         * 2:后备公猪，4:后备母猪，
         * 5:肉猪
         */
        sqlCon.addMainSql(
                " SELECT T0.HOUSE_ID,t0.CHECK_PIG_TYPE,T0.CHECK_DATE,SUM(T0.CHECK_QTY) CHECK_QTY,  SUM(T0.ACCOUNT_QTY) ACCOUNT_QTY, SUM(T0.TOTAL_WEIGHT) / SUM(T0.CHECK_QTY) AVG_WEIGHT,  SUM(T0.TOTAL_WEIGHT) TOTAL_WEIGHT,  SUM(T0.CHECK_QTY * SWINERY_AGE) / SUM(T0.CHECK_QTY) SWINERY_AGE,  T0.CHECK_ORGAN, T0.ACCOUNT_USER, T0.WORKER, T0.CREATE_ID, T0.BILL_DATE, T0.CREATE_DATE, T0.NOTES  ");
        sqlCon.addMainSql(" FROM PP_L_BILL_PORK_CHECK T0  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' ");
        sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
        sqlCon.addCondition(endDate, " AND T0.CHECK_DATE = ? ");
        sqlCon.addMainSql(" GROUP BY t0.CHECK_PIG_TYPE ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> checkPigOfTypeQty = paramMapper.getObjectInfos(sqlMap);
        // 存进session,汇总分别统计用
        SecurityUtils.getSubject().getSession().setAttribute("checkPigOfTypeQty9997", checkPigOfTypeQty);
        // 实际返回结果
        return getCheckPigOfTypeQty(endDate);
    }

    // 获取盘点数据
    private List<Map<String, Object>> getCheckPigOfTypeQty(String endDate) {
        Map<String, String> sqlMap = new HashMap<String, String>();
        SqlCon sqlCon = new SqlCon();
        /* 盘点猪只类型1:生产公猪，2：后备公猪，3：生产母猪，4：后备母猪，5：肉猪 */
        sqlCon.addMainSql(
                " SELECT IF(t0.CHECK_PIG_TYPE IN (2,4),2,IF(t0.CHECK_PIG_TYPE IN (5),5,1))  CHECK_PIG_TYPE,T0.HOUSE_ID, T0.CHECK_DATE, T1.HOUSE_NAME , SUM(T0.CHECK_QTY) CHECK_QTY,  SUM(T0.ACCOUNT_QTY) ACCOUNT_QTY, SUM(T0.TOTAL_WEIGHT) / SUM(T0.CHECK_QTY) AVG_WEIGHT,  SUM(T0.TOTAL_WEIGHT) TOTAL_WEIGHT,  SUM(T0.CHECK_QTY * SWINERY_AGE) / SUM(T0.CHECK_QTY) SWINERY_AGE,  T0.CHECK_ORGAN, T0.ACCOUNT_USER, T0.WORKER, T0.CREATE_ID, T0.BILL_DATE, T0.CREATE_DATE, T0.NOTES ");
        sqlCon.addMainSql(" FROM PP_L_BILL_PORK_CHECK T0  ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T1 ON T0.HOUSE_ID = T1.ROW_ID  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0'  ");
        sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
        sqlCon.addCondition(endDate, " AND T0.CHECK_DATE = ? ");
        sqlCon.addMainSql("  GROUP BY T0.HOUSE_ID,IF(t0.CHECK_PIG_TYPE IN (2,4),2,IF(t0.CHECK_PIG_TYPE IN (5),5,1))  ");
        sqlCon.addMainSql(" ORDER BY T1.HOUSE_TYPE,T1.HOUSE_NAME  ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);
        return infos;
    }

    // 新增上传SAP记录 XN_SAP 实际上传
    @SuppressWarnings("unchecked")
    @Override
    public void editInsertSendSap(String json, Boolean isSummery) throws Exception {
        String endDate = (String) SecurityUtils.getSubject().getSession().getAttribute("endDate9997");
        if (StringUtil.isBlank(endDate)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "上传日期不能为空!");
        }
        Timestamp creatTime = TimeUtil.getSysTimestamp();
        List<SapMtcSummaryModel> jsonList = getJsonList(json, SapMtcSummaryModel.class);
        SysHanaInsertLogModel sysHanaInsertLogUpdate = new SysHanaInsertLogModel();
        SqlCon sqlCon = new SqlCon();
        // String settingValueByCode = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SAP_MAX_DELAY");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ? ");
        sqlCon.addCondition(endDate, " AND TO_SAP_DATE = ? ");
        sqlCon.addMainSql(" AND TO_SAP_AGIN = 'Y' ");
        // sqlCon.addMainSql(" AND TIMESTAMPDIFF(DAY,TO_SAP_DATE,NOW()) BETWEEN 0 AND " + settingValueByCode);
        sqlCon.addMainSql(" ORDER BY TO_SAP_DATE,ROW_ID DESC LIMIT 1 ");
        List<SysHanaInsertLogModel> list = getList(sysHanaInsertLogMapper, sqlCon);
        for (SysHanaInsertLogModel syslist : list) {
            // 修改上一条记录的删除时间,并将其状态设置为删除
            sysHanaInsertLogUpdate.setDeletedTime(creatTime);
            sysHanaInsertLogUpdate.setRowId(syslist.getRowId());
            sysHanaInsertLogUpdate.setFarmId(getFarmId());
            sysHanaInsertLogMapper.update(sysHanaInsertLogUpdate);
            sysHanaInsertLogMapper.delete(syslist.getRowId());

            // 上传该月最新数据的同时,删除过时数据
            Map<String, String> map = new HashMap<>();
            map = new HashMap<>();
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" UPDATE  SAP_MTC_PGTSDTL");
            sqlCon.addMainSql(" SET DELETED_FLAG  =  '1'  WHERE DELETED_FLAG  =  '0' ");
            sqlCon.addCondition(getFarmId(), " AND FARM_ID= ? ");
            sqlCon.addCondition(syslist.getRowId(), " AND HANA_LOG_ID = ? ");
            map.put("sql", sqlCon.getCondition());
            sapMtcPgtsdtlMapper.operSql(map);

            map = new HashMap<>();
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" UPDATE  SAP_MTC_PGSTAT ");
            sqlCon.addMainSql(" SET DELETED_FLAG  =  '1'  WHERE DELETED_FLAG  =  '0' ");
            sqlCon.addCondition(getFarmId(), " AND FARM_ID= ? ");
            sqlCon.addCondition(syslist.getRowId(), " AND HANA_LOG_ID = ? ");
            map.put("sql", sqlCon.getCondition());
            sapMtcPgstatMapper.operSql(map);

            map = new HashMap<>();
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" UPDATE  SAP_MTC_PGSTAT_DTL ");
            sqlCon.addMainSql(" SET DELETED_FLAG  =  '1'  WHERE DELETED_FLAG  =  '0' ");
            sqlCon.addCondition(getFarmId(), " AND FARM_ID= ? ");
            sqlCon.addCondition(syslist.getRowId(), " AND HANA_LOG_ID = ? ");
            map.put("sql", sqlCon.getCondition());
            sapMtcPgstatDtlMapper.operSql(map);

            map = new HashMap<>();
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" UPDATE  SAP_MTC_PGONHD ");
            sqlCon.addMainSql(" SET DELETED_FLAG  =  '1'  WHERE DELETED_FLAG  =  '0' ");
            sqlCon.addCondition(getFarmId(), " AND FARM_ID= ? ");
            sqlCon.addCondition(syslist.getRowId(), " AND HANA_LOG_ID = ? ");
            map.put("sql", sqlCon.getCondition());
            sapMtcPgonhdMapper.operSql(map);

            map = new HashMap<>();
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" UPDATE  SAP_MTC_ITFC ");
            sqlCon.addMainSql(" SET DELETED_FLAG  =  '1'  WHERE DELETED_FLAG  =  '0' ");
            sqlCon.addCondition(getFarmId(), " AND FARM_ID= ? ");
            sqlCon.addCondition(syslist.getRowId(), " AND HANA_LOG_ID = ? ");
            map.put("sql", sqlCon.getCondition());
            sapMtcItfcMapper.operSql(map);

            // 汇总
            // if (isSummery) {
            map = new HashMap<>();
            sqlCon = new SqlCon();
            sqlCon.addCondition(syslist.getRowId(), " AND HANA_LOG_ID = ? ");
            List<SapMtcSummaryModel> listSummary = getList(sapMtcSummaryMapper, sqlCon);
            if (listSummary.size() > 0) {
                for (SapMtcSummaryModel sapSummary : listSummary) {
                    sapMtcSummaryMapper.delete(sapSummary.getRowId());
                }
            }
            // }
        }

        // 新增上传日志
        SysHanaInsertLogModel sysHanaInsertLog = new SysHanaInsertLogModel();
        sysHanaInsertLog.setDeletedFlag("0");
        sysHanaInsertLog.setStatus("1");
        sysHanaInsertLog.setOriginApp("XN1.0");
        sysHanaInsertLog.setOriginFlag("S");
        sysHanaInsertLog.setSortNbr(0L);
        sysHanaInsertLog.setCompanyId(getCompanyId());
        sysHanaInsertLog.setFarmId(getFarmId());
        sysHanaInsertLog.setToSapDate(TimeUtil.parseDate(endDate));
        sysHanaInsertLog.setCreatTime(creatTime);
        sysHanaInsertLog.setToSapAgin("N");
        // 汇总
        if (isSummery) {
            sysHanaInsertLog.setSummary("Y");
        } else {
            sysHanaInsertLog.setSummary("N");
        }
        int insert = sysHanaInsertLogMapper.insert(sysHanaInsertLog);
        if (insert == 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "上传日志出现异常。。。");
        }
        Long rowId = sysHanaInsertLog.getRowId();
        // 保存到数据库 并 上传sap
        Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
        String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

        // 是否汇总上传
        // if (isSummery) {
        List<SapMtcSummaryModel> summartList = new ArrayList<>();

        List<Map<String, Object>> summary10000RowList = (List<Map<String, Object>>) SecurityUtils.getSubject().getSession().getAttribute(
                "summary10000RowList");
        for (Map<String, Object> model : summary10000RowList) {
            SapMtcSummaryModel sapMtcSummaryModel = new SapMtcSummaryModel();
            sapMtcSummaryModel.setSortNbr(0l);
            sapMtcSummaryModel.setStatus("1");
            sapMtcSummaryModel.setDeletedFlag("0");
            sapMtcSummaryModel.setOriginFlag("S");
            sapMtcSummaryModel.setOriginApp("XN1.0");
            sapMtcSummaryModel.setCompanyId(getCompanyId());
            sapMtcSummaryModel.setFarmId(getFarmId());
            sapMtcSummaryModel.setHanaLogId(rowId);
            sapMtcSummaryModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcSummaryModel.setLineNumber(Maps.getLongClass(model, "lineNumber"));
            sapMtcSummaryModel.setType(Maps.getString(model, "type"));
            sapMtcSummaryModel.setBegQty(Maps.getLongClass(model, "begQty"));
            sapMtcSummaryModel.setBegMoney(Maps.getLongClass(model, "begMoney"));
            sapMtcSummaryModel.setAddQty(Maps.getLongClass(model, "addQty"));
            sapMtcSummaryModel.setAddMoney(Maps.getLongClass(model, "addMoney"));
            sapMtcSummaryModel.setReduceQty(Maps.getLongClass(model, "reduceQty"));
            sapMtcSummaryModel.setReduceMoney(Maps.getLongClass(model, "reduceMoney"));
            sapMtcSummaryModel.setEndQty(Maps.getLongClass(model, "endQty"));
            sapMtcSummaryModel.setEndMoney(Maps.getLongClass(model, "endMoney"));
            sapMtcSummaryModel.setOther(Maps.getString(model, "other"));
            // sapMtcSummaryMapper.insert(sapMtcSummaryModel);
            summartList.add(sapMtcSummaryModel);
        }

        // 保存汇总数据
        sapMtcSummaryMapper.inserts(summartList);
        // }

        // 9997 XN_SAP 实际上传
        editInsertXNfarmSapMtcPgonhd(mtcBranchID, mtcDeptID, rowId);
        // 9998 XN_SAP 实际上传
        editInsertXNfarmSapMtcPgtsdtl(mtcBranchID, mtcDeptID, rowId);
        // 9999 XN_SAP 实际上传
        editInsertXNfarmSapMtcPgstat(mtcBranchID, mtcDeptID, rowId);
        // 3030 XN_SAP 实际上传
        editInsertXNfarmSapMtcPgstatDtl(mtcBranchID, mtcDeptID, rowId);

        SecurityUtils.getSubject().getSession().removeAttribute("summary10000RowList");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9997");
        SecurityUtils.getSubject().getSession().removeAttribute("summary10000RowList");
    }

    // 3030 XN_SAP 实际上传
    @SuppressWarnings("unchecked")
    private void editInsertXNfarmSapMtcPgstatDtl(String mtcBranchID, String mtcDeptID, Long rowId) throws Exception {
        String endDate = (String) SecurityUtils.getSubject().getSession().getAttribute("endDate3030");
        if (StringUtil.isBlank(endDate)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "妊娠母猪状态汇总-上传的数据日期不能为空!");
        }
        List<HanaMtcPgstatDtlDetail> detaillist = (List<HanaMtcPgstatDtlDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                "detaillist3030");

        HanaMtcPgstatDtl hanaMtcPgstatDtl = new HanaMtcPgstatDtl();
        hanaMtcPgstatDtl.setDetailList(detaillist);
        // JSON文件
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMtcPgstatDtl);
        /******************************************************* XN部分 begin *********************************************************************/
        SapMtcItfcModel sapMtcItfcModel = new SapMtcItfcModel();
        sapMtcItfcModel.setSortNbr(0L);
        sapMtcItfcModel.setStatus("1");
        sapMtcItfcModel.setDeletedFlag("0");
        sapMtcItfcModel.setOriginFlag("S");
        sapMtcItfcModel.setOriginApp("XN1.0");
        sapMtcItfcModel.setCompanyId(getCompanyId());
        sapMtcItfcModel.setFarmId(getFarmId());
        sapMtcItfcModel.setHanaLogId(rowId);
        // 分公司编号
        sapMtcItfcModel.setMtcbranch(mtcBranchID);
        // 业务日期
        sapMtcItfcModel.setMtcdocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:库存报废
        sapMtcItfcModel.setMtcobjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3030);
        sapMtcItfcModel.setMtcdocNum(getCompanyCode());
        sapMtcItfcModel.setMtccreateTime(TimeUtil.getSysTimestamp());
        // 优先级
        sapMtcItfcModel.setMtcpriority(Long.valueOf(HanaConstants.MTC_ITFC_PRIORITY_1));
        // 处理区分
        sapMtcItfcModel.setMtctransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 同步状态
        sapMtcItfcModel.setMtcstatus(HanaConstants.MTC_ITFC_STATUS_N);
        // json 字符串过长,有明细表保存数据,不存数据库
        sapMtcItfcModel.setMtcdataFile("");
        // JSON文件长度
        sapMtcItfcModel.setMtcdataFileLen(Long.valueOf(jsonDataFile.length()));
        if (detaillist.size() != 0) {
            sapMtcItfcMapper.insert(sapMtcItfcModel);
        }
        List<SapMtcPgstatDtlModel> records = new ArrayList<>();
        SapMtcPgstatDtlModel sapMtcPgonhdModel = null;
        for (HanaMtcPgstatDtlDetail pgonhd : detaillist) {
            sapMtcPgonhdModel = new SapMtcPgstatDtlModel();
            sapMtcPgonhdModel.setSortNbr(0L);
            sapMtcPgonhdModel.setStatus("1");
            sapMtcPgonhdModel.setDeletedFlag("0");
            sapMtcPgonhdModel.setOriginFlag("S");
            sapMtcPgonhdModel.setOriginApp("XN1.0");
            sapMtcPgonhdModel.setCompanyId(getCompanyId());
            sapMtcPgonhdModel.setFarmId(getFarmId());
            sapMtcPgonhdModel.setHouseId(Long.valueOf(pgonhd.getMtc_HouseId()));
            sapMtcPgonhdModel.setHanaLogId(rowId);
            sapMtcPgonhdModel.setItfcId(sapMtcItfcModel.getRowId());
            sapMtcPgonhdModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcPgonhdModel.setMtcperiod(pgonhd.getMtc_Period());
            sapMtcPgonhdModel.setMtcbranchId(pgonhd.getMtc_BranchID());
            sapMtcPgonhdModel.setMtcitemCode(pgonhd.getMtc_ItemCode());
            sapMtcPgonhdModel.setMtcitemName(pgonhd.getMtc_ItemName());
            sapMtcPgonhdModel.setMtcdeptId(pgonhd.getMtc_DeptID());
            sapMtcPgonhdModel.setMtcunit(pgonhd.getMtc_Unit());
            sapMtcPgonhdModel.setMtcparity(pgonhd.getMtc_Parity());
            sapMtcPgonhdModel.setMtcpregStatus(pgonhd.getMtc_PregStatus());
            sapMtcPgonhdModel.setMtcpregDate(TimeUtil.parseDate(pgonhd.getMtc_PregDate()));
            sapMtcPgonhdModel.setMtcdaySum(pgonhd.getMtc_DaySum());
            sapMtcPgonhdModel.setMtcpregLevel(pgonhd.getMtc_PregLevel());

            records.add(sapMtcPgonhdModel);
        }
        if (records.size() > 0) {
            sapMtcPgstatDtlMapper.inserts(records);
        }
        /******************************************************* XN部分 end *********************************************************************/

        /******************************************************* SAP部分 begin *********************************************************************/

        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(mtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:猪只销售(实时)
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3030);
        // 猪场名称
        mtcITFC.setMTC_DocNum(getCompanyCode());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(TimeUtil.getSysTimestamp());
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));

        // JSON 文件
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
        if (detaillist.size() != 0) {
            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }
        /******************************************************* SAP部分 end **********************************************************************/
        SecurityUtils.getSubject().getSession().removeAttribute("detaillist3030");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate3030");
    }

    // 9999 XN_SAP 实际上传
    @SuppressWarnings("unchecked")
    private void editInsertXNfarmSapMtcPgstat(String mtcBranchID, String mtcDeptID, Long rowId) throws Exception {
        String endDate = (String) SecurityUtils.getSubject().getSession().getAttribute("endDate9999");
        if (StringUtil.isBlank(endDate)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "妊娠母猪状态汇总-上传的数据日期不能为空,或从新生成明细!");
        }
        List<HanaMtcPgstatDetail> detaillist = (List<HanaMtcPgstatDetail>) SecurityUtils.getSubject().getSession().getAttribute("detaillist9999");
        HanaMtcPgstat hanaMtcPgstat = new HanaMtcPgstat();
        hanaMtcPgstat.setDetailList(detaillist);
        // JSON文件
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMtcPgstat);
        /******************************************************* XN部分 begin *********************************************************************/
        SapMtcItfcModel sapMtcItfcModel = new SapMtcItfcModel();
        sapMtcItfcModel.setSortNbr(0L);
        sapMtcItfcModel.setStatus("1");
        sapMtcItfcModel.setDeletedFlag("0");
        sapMtcItfcModel.setOriginFlag("S");
        sapMtcItfcModel.setOriginApp("XN1.0");
        sapMtcItfcModel.setCompanyId(getCompanyId());
        sapMtcItfcModel.setFarmId(getFarmId());
        sapMtcItfcModel.setHanaLogId(rowId);
        // 分公司编号
        sapMtcItfcModel.setMtcbranch(mtcBranchID);
        // 业务日期
        sapMtcItfcModel.setMtcdocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:库存报废
        sapMtcItfcModel.setMtcobjCode(HanaConstants.MTC_ITFC_OBJ_CODE_9999);
        sapMtcItfcModel.setMtcdocNum(getCompanyCode());
        sapMtcItfcModel.setMtccreateTime(TimeUtil.getSysTimestamp());
        // 优先级
        sapMtcItfcModel.setMtcpriority(Long.valueOf(HanaConstants.MTC_ITFC_PRIORITY_1));
        // 处理区分
        sapMtcItfcModel.setMtctransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 同步状态
        sapMtcItfcModel.setMtcstatus(HanaConstants.MTC_ITFC_STATUS_N);
        // json 字符串过长,有明细表保存数据,不存数据库
        sapMtcItfcModel.setMtcdataFile("");
        // JSON文件长度
        sapMtcItfcModel.setMtcdataFileLen(Long.valueOf(jsonDataFile.length()));
        if (detaillist.size() != 0) {
            sapMtcItfcMapper.insert(sapMtcItfcModel);
        }
        List<SapMtcPgstatModel> records = new ArrayList<>();
        SapMtcPgstatModel sapMtcPgonhdModel = null;
        if (detaillist.size() == 0) {
            return;
        }
        for (HanaMtcPgstatDetail pgonhd : detaillist) {
            sapMtcPgonhdModel = new SapMtcPgstatModel();
            sapMtcPgonhdModel.setSortNbr(0L);
            sapMtcPgonhdModel.setStatus("1");
            sapMtcPgonhdModel.setDeletedFlag("0");
            sapMtcPgonhdModel.setOriginFlag("S");
            sapMtcPgonhdModel.setOriginApp("XN1.0");
            sapMtcPgonhdModel.setCompanyId(getCompanyId());
            sapMtcPgonhdModel.setFarmId(getFarmId());

            sapMtcPgonhdModel.setHouseId(Long.valueOf(pgonhd.getMtc_HouseId()));
            sapMtcPgonhdModel.setHanaLogId(rowId);
            sapMtcPgonhdModel.setItfcId(sapMtcItfcModel.getRowId());
            sapMtcPgonhdModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcPgonhdModel.setMtcbranchId(pgonhd.getMtc_BranchID());
            sapMtcPgonhdModel.setMtcdeptId(pgonhd.getMtc_DeptID());
            sapMtcPgonhdModel.setMtcunit(HanaUtil.getMTC_Unit(Long.valueOf(pgonhd.getMtc_HouseId())));
            sapMtcPgonhdModel.setMtcperiod(pgonhd.getMtc_Period());
            sapMtcPgonhdModel.setMtcpregStep1(pgonhd.getMtc_PregStep_1());
            sapMtcPgonhdModel.setMtcpregStep2(pgonhd.getMtc_PregStep_2());
            sapMtcPgonhdModel.setMtcpregStep3(pgonhd.getMtc_PregStep_3());
            records.add(sapMtcPgonhdModel);
        }
        if (records.size() > 0) {
            sapMtcPgstatMapper.inserts(records);
        }
        /******************************************************* XN部分 end *********************************************************************/

        /******************************************************* SAP部分 begin *********************************************************************/

        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(mtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:猪只销售(实时)
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_9999);
        // 猪场名称
        mtcITFC.setMTC_DocNum(getCompanyCode());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(TimeUtil.getSysTimestamp());
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));

        // JSON 文件
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
        if (detaillist.size() != 0) {
            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }
        /******************************************************* SAP部分 end **********************************************************************/
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9999");
        SecurityUtils.getSubject().getSession().removeAttribute("detaillist9999");
    }

    // 9998 XN_SAP 实际上传
    @SuppressWarnings("unchecked")
    private void editInsertXNfarmSapMtcPgtsdtl(String mtcBranchID, String mtcDeptID, Long rowId) throws Exception {
        String endDate = (String) SecurityUtils.getSubject().getSession().getAttribute("endDate9998");
        if (StringUtil.isBlank(endDate)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "存栏表动-上传的数据日期不能为空!");
        }
        List<HanaMtcPgtsdtlDetail> hanaMtcPgtsdtlDetailList = (List<HanaMtcPgtsdtlDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                "hanaMtcPgtsdtlDetailList9998");
        List<HanaMtcPgtsdtlDetail> hanaMtcPgtsdtlDetailHBList = (List<HanaMtcPgtsdtlDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                "hanaMtcPgtsdtlDetailHBList9998");

        HanaMtcPgtsdtl hanaMtcPgtsdtl = new HanaMtcPgtsdtl();
        hanaMtcPgtsdtl.setDetailList(hanaMtcPgtsdtlDetailList);
        // JSON文件 -- 商品猪
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMtcPgtsdtl);

        HanaMtcPgtsdtl hanaMtcPgtsdtlHB = new HanaMtcPgtsdtl();
        hanaMtcPgtsdtlHB.setDetailList(hanaMtcPgtsdtlDetailHBList);
        // JSON文件 -- 后备猪
        String jsonDataFile2 = HanaJacksonUtil.objectToJackson(hanaMtcPgtsdtlHB);
        /******************************************************* XN部分 begin *********************************************************************/
        SapMtcItfcModel sapMtcItfcModel = new SapMtcItfcModel();
        sapMtcItfcModel.setSortNbr(0L);
        sapMtcItfcModel.setStatus("1");
        sapMtcItfcModel.setDeletedFlag("0");
        sapMtcItfcModel.setOriginFlag("S");
        sapMtcItfcModel.setOriginApp("XN1.0");
        sapMtcItfcModel.setCompanyId(getCompanyId());
        sapMtcItfcModel.setFarmId(getFarmId());
        sapMtcItfcModel.setHanaLogId(rowId);
        // 分公司编号
        sapMtcItfcModel.setMtcbranch(mtcBranchID);
        // 业务日期
        sapMtcItfcModel.setMtcdocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:库存报废
        sapMtcItfcModel.setMtcobjCode(HanaConstants.MTC_ITFC_OBJ_CODE_9998);
        sapMtcItfcModel.setMtcdocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_SP);
        sapMtcItfcModel.setMtccreateTime(TimeUtil.getSysTimestamp());
        // 优先级
        sapMtcItfcModel.setMtcpriority(Long.valueOf(HanaConstants.MTC_ITFC_PRIORITY_1));
        // 处理区分
        sapMtcItfcModel.setMtctransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 同步状态
        sapMtcItfcModel.setMtcstatus(HanaConstants.MTC_ITFC_STATUS_N);
        // json 字符串过长,有明细表保存数据,不存数据库
        sapMtcItfcModel.setMtcdataFile("");
        // JSON文件长度
        sapMtcItfcModel.setMtcdataFileLen(Long.valueOf(jsonDataFile.length()));
        if (hanaMtcPgtsdtlDetailList.size() != 0) {
            sapMtcItfcMapper.insert(sapMtcItfcModel);
        }
        // -- 商品猪 9998
        List<SapMtcPgtsdtlModel> records = new ArrayList<>();
        SapMtcPgtsdtlModel sapMtcPgonhdModel = null;
        for (HanaMtcPgtsdtlDetail pgonhd : hanaMtcPgtsdtlDetailList) {
            sapMtcPgonhdModel = new SapMtcPgtsdtlModel();
            sapMtcPgonhdModel.setSortNbr(0L);
            sapMtcPgonhdModel.setStatus("1");
            sapMtcPgonhdModel.setDeletedFlag("0");
            sapMtcPgonhdModel.setOriginFlag("S");
            sapMtcPgonhdModel.setOriginApp("XN1.0");
            sapMtcPgonhdModel.setCompanyId(getCompanyId());
            sapMtcPgonhdModel.setFarmId(getFarmId());
            sapMtcPgonhdModel.setHouseId(Long.valueOf(pgonhd.getMtc_HouseId()));
            if (!StringUtil.isBlank(pgonhd.getMtc_RelaDeptID_FarmId())) {
                sapMtcPgonhdModel.setFarmidrela(Long.valueOf(pgonhd.getMtc_RelaDeptID_FarmId()));
            }
            if (!StringUtil.isBlank(pgonhd.getMtc_RelaUnit_HouseId())) {
                sapMtcPgonhdModel.setHouseidrela(Long.valueOf(pgonhd.getMtc_RelaUnit_HouseId()));
            }
            sapMtcPgonhdModel.setHanaLogId(rowId);
            sapMtcPgonhdModel.setItfcId(sapMtcItfcModel.getRowId());
            sapMtcPgonhdModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcPgonhdModel.setMtcbranchId(pgonhd.getMtc_BranchID());
            sapMtcPgonhdModel.setMtcdeptId(pgonhd.getMtc_DeptID());
            sapMtcPgonhdModel.setMtcunit(HanaUtil.getMTC_Unit(Long.valueOf(pgonhd.getMtc_HouseId())));
            sapMtcPgonhdModel.setMtcpigType(pgonhd.getMtc_PigType());
            sapMtcPgonhdModel.setMtcperiod(pgonhd.getMtc_Period());
            sapMtcPgonhdModel.setMtcdocDate(TimeUtil.parseDate(pgonhd.getMtc_DocDate()));
            sapMtcPgonhdModel.setMtctransType(pgonhd.getMtc_TransType());
            sapMtcPgonhdModel.setMtcrelaDeptId(pgonhd.getMtc_RelaDeptID());
            sapMtcPgonhdModel.setMtcrelaUnit(pgonhd.getMtc_RelaUnit());
            sapMtcPgonhdModel.setMtcinQty(pgonhd.getMtc_InQty());
            sapMtcPgonhdModel.setMtcinWeight(pgonhd.getMtc_InWeight());
            sapMtcPgonhdModel.setMtcoutQty(pgonhd.getMtc_OutQty());
            sapMtcPgonhdModel.setMtcoutWeight(pgonhd.getMtc_OutWeight());
            records.add(sapMtcPgonhdModel);
        }
        if (records.size() > 0) {
            sapMtcPgtsdtlMapper.inserts(records);
        }
        /******************************************************* XN部分 end *********************************************************************/

        /******************************************************* SAP部分 begin *********************************************************************/
        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(mtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:猪只销售(实时)
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_9998);
        // 猪场编码
        mtcITFC.setMTC_DocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_SP);
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(TimeUtil.getSysTimestamp());
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));

        // JSON 文件
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
        if (hanaMtcPgtsdtlDetailList.size() != 0) {
            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }
        /******************************************************* SAP部分 end **********************************************************************/

        /******************************************************* SAP部分 begin **********************************************************************/
        // -- 后备猪 9998
        // 猪场编码
        sapMtcItfcModel.setMtcdocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_HB);
        // JSON文件长度
        sapMtcItfcModel.setMtcdataFileLen(Long.valueOf(jsonDataFile2.length()));
        if (hanaMtcPgtsdtlDetailHBList.size() != 0) {
            sapMtcItfcMapper.insert(sapMtcItfcModel);
        }
        List<SapMtcPgtsdtlModel> recordsHB = new ArrayList<>();
        for (HanaMtcPgtsdtlDetail pgonhd : hanaMtcPgtsdtlDetailHBList) {
            sapMtcPgonhdModel = new SapMtcPgtsdtlModel();
            sapMtcPgonhdModel.setSortNbr(0L);
            sapMtcPgonhdModel.setStatus("1");
            sapMtcPgonhdModel.setDeletedFlag("0");
            sapMtcPgonhdModel.setOriginFlag("S");
            sapMtcPgonhdModel.setOriginApp("XN1.0");
            sapMtcPgonhdModel.setCompanyId(getCompanyId());
            sapMtcPgonhdModel.setFarmId(getFarmId());
            sapMtcPgonhdModel.setHouseId(Long.valueOf(pgonhd.getMtc_HouseId()));
            if (!StringUtil.isBlank(pgonhd.getMtc_RelaDeptID_FarmId())) {
                sapMtcPgonhdModel.setFarmidrela(Long.valueOf(pgonhd.getMtc_RelaDeptID_FarmId()));
            }
            if (!StringUtil.isBlank(pgonhd.getMtc_RelaUnit_HouseId())) {
                sapMtcPgonhdModel.setHouseidrela(Long.valueOf(pgonhd.getMtc_RelaUnit_HouseId()));
            }
            sapMtcPgonhdModel.setHanaLogId(rowId);
            sapMtcPgonhdModel.setItfcId(sapMtcItfcModel.getRowId());
            sapMtcPgonhdModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcPgonhdModel.setMtcbranchId(pgonhd.getMtc_BranchID());
            sapMtcPgonhdModel.setMtcdeptId(pgonhd.getMtc_DeptID());
            sapMtcPgonhdModel.setMtcunit(HanaUtil.getMTC_Unit(Long.valueOf(pgonhd.getMtc_HouseId())));
            sapMtcPgonhdModel.setMtcpigType(pgonhd.getMtc_PigType());
            sapMtcPgonhdModel.setMtcperiod(pgonhd.getMtc_Period());
            sapMtcPgonhdModel.setMtcdocDate(TimeUtil.parseDate(pgonhd.getMtc_DocDate()));
            sapMtcPgonhdModel.setMtctransType(pgonhd.getMtc_TransType());
            sapMtcPgonhdModel.setMtcrelaDeptId(pgonhd.getMtc_RelaDeptID());
            sapMtcPgonhdModel.setMtcrelaUnit(pgonhd.getMtc_RelaUnit());
            sapMtcPgonhdModel.setMtcinQty(pgonhd.getMtc_InQty());
            sapMtcPgonhdModel.setMtcinWeight(pgonhd.getMtc_InWeight());
            sapMtcPgonhdModel.setMtcoutQty(pgonhd.getMtc_OutQty());
            sapMtcPgonhdModel.setMtcoutWeight(pgonhd.getMtc_OutWeight());
            recordsHB.add(sapMtcPgonhdModel);
        }
        if (recordsHB.size() > 0) {
            sapMtcPgtsdtlMapper.inserts(recordsHB);
        }
        /******************************************************* XN部分 end *********************************************************************/
        /******************************************************* SAP部分 begin **********************************************************************/
        // 猪场编码
        mtcITFC.setMTC_DocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_HB);
        // JSON 文件
        mtcITFC.setMTC_DataFile(jsonDataFile2);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile2.length());
        if (hanaMtcPgtsdtlDetailHBList.size() != 0) {
            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }
        /******************************************************* SAP部分 end **********************************************************************/
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9998");
        SecurityUtils.getSubject().getSession().removeAttribute("hanaMtcPgtsdtlDetailList9998");
        SecurityUtils.getSubject().getSession().removeAttribute("hanaMtcPgtsdtlDetailHBList9998");
    }

    // 9997 XN_SAP 实际上传
    @SuppressWarnings("unchecked")
    private void editInsertXNfarmSapMtcPgonhd(String mtcBranchID, String mtcDeptID, Long rowId) throws Exception {
        String endDate = (String) SecurityUtils.getSubject().getSession().getAttribute("endDate9997");
        if (StringUtil.isBlank(endDate)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "存栏日报-上传的数据日期不能为空!");
        }
        List<HanaMtcPgonhdDetail> listHanaMtcPgonhd = (List<HanaMtcPgonhdDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                "listHanaMtcPgonhd9997");
        List<HanaMtcPgonhdDetail> listHanaMtcPgonhdHB = (List<HanaMtcPgonhdDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                "listHanaMtcPgonhdHB9997");

        HanaMtcPgonhd hanaMtcPgonhd = new HanaMtcPgonhd();
        hanaMtcPgonhd.setDetailList(listHanaMtcPgonhd);
        // JSON文件 -- 商品猪
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMtcPgonhd);

        HanaMtcPgonhd hanaMtcPgonhdHB = new HanaMtcPgonhd();
        hanaMtcPgonhdHB.setDetailList(listHanaMtcPgonhdHB);
        // JSON文件 -- 后备猪
        String jsonDataFile2 = HanaJacksonUtil.objectToJackson(hanaMtcPgonhdHB);
        /******************************************************* XN部分 begin *********************************************************************/
        SapMtcItfcModel sapMtcItfcModel = new SapMtcItfcModel();
        sapMtcItfcModel.setSortNbr(0L);
        sapMtcItfcModel.setStatus("1");
        sapMtcItfcModel.setDeletedFlag("0");
        sapMtcItfcModel.setOriginFlag("S");
        sapMtcItfcModel.setOriginApp("XN1.0");
        sapMtcItfcModel.setCompanyId(getCompanyId());
        sapMtcItfcModel.setFarmId(getFarmId());
        sapMtcItfcModel.setHanaLogId(rowId);
        // 分公司编号
        sapMtcItfcModel.setMtcbranch(mtcBranchID);
        // 业务日期
        sapMtcItfcModel.setMtcdocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:库存报废
        sapMtcItfcModel.setMtcobjCode(HanaConstants.MTC_ITFC_OBJ_CODE_9997);
        sapMtcItfcModel.setMtcdocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_SP);
        sapMtcItfcModel.setMtccreateTime(TimeUtil.getSysTimestamp());
        // 优先级
        sapMtcItfcModel.setMtcpriority(Long.valueOf(HanaConstants.MTC_ITFC_PRIORITY_1));
        // 处理区分
        sapMtcItfcModel.setMtctransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 同步状态
        sapMtcItfcModel.setMtcstatus(HanaConstants.MTC_ITFC_STATUS_N);
        // json 字符串过长,有明细表保存数据,不存数据库
        sapMtcItfcModel.setMtcdataFile("");
        // JSON文件长度
        sapMtcItfcModel.setMtcdataFileLen(Long.valueOf(jsonDataFile.length()));
        if (listHanaMtcPgonhd.size() != 0) {
            sapMtcItfcMapper.insert(sapMtcItfcModel);
        }
        // -- 商品猪 9997
        List<SapMtcPgonhdModel> records = new ArrayList<>();
        SapMtcPgonhdModel sapMtcPgonhdModel = null;
        for (HanaMtcPgonhdDetail pgonhd : listHanaMtcPgonhd) {
            sapMtcPgonhdModel = new SapMtcPgonhdModel();
            sapMtcPgonhdModel.setSortNbr(0L);
            sapMtcPgonhdModel.setStatus("1");
            sapMtcPgonhdModel.setDeletedFlag("0");
            sapMtcPgonhdModel.setOriginFlag("S");
            sapMtcPgonhdModel.setOriginApp("XN1.0");
            sapMtcPgonhdModel.setCompanyId(getCompanyId());
            sapMtcPgonhdModel.setFarmId(getFarmId());
            sapMtcPgonhdModel.setHouseId(Long.valueOf(pgonhd.getMtc_HouseId()));
            sapMtcPgonhdModel.setHanaLogId(rowId);
            sapMtcPgonhdModel.setItfcId(sapMtcItfcModel.getRowId());
            sapMtcPgonhdModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcPgonhdModel.setMtcbranchId(pgonhd.getMtc_BranchID());
            sapMtcPgonhdModel.setMtcdeptId(pgonhd.getMtc_DeptID());
            sapMtcPgonhdModel.setMtcunit(HanaUtil.getMTC_Unit(Long.valueOf(pgonhd.getMtc_HouseId())));
            sapMtcPgonhdModel.setMtcpigType(pgonhd.getMtc_PigType());
            sapMtcPgonhdModel.setMtcperiod(pgonhd.getMtc_Period());
            sapMtcPgonhdModel.setMtcdocDate(TimeUtil.parseDate(pgonhd.getMtc_DocDate()));
            sapMtcPgonhdModel.setMtcbegQty(pgonhd.getMtc_BegQty());
            sapMtcPgonhdModel.setMtcinQty(pgonhd.getMtc_InQty());
            sapMtcPgonhdModel.setMtcpurQty(pgonhd.getMtc_PurQty());
            sapMtcPgonhdModel.setMtcinQtyInner(pgonhd.getMtc_InQty_Inner());
            sapMtcPgonhdModel.setMtcinQtyNormal(pgonhd.getMtc_InQty_Normal());
            sapMtcPgonhdModel.setMtcoutQtyInner(pgonhd.getMtc_OutQty_Inner());
            sapMtcPgonhdModel.setMtcoutQtyNormal(pgonhd.getMtc_OutQty_Normal());
            sapMtcPgonhdModel.setMtctransToCf(pgonhd.getMtc_OutQty_Inner());
            sapMtcPgonhdModel.setMtctransToBy(pgonhd.getMtc_TransToBY());
            sapMtcPgonhdModel.setMtctransToPy(pgonhd.getMtc_TransToPY());
            sapMtcPgonhdModel.setMtctransToHb(pgonhd.getMtc_TransToHB());
            sapMtcPgonhdModel.setMtctransToYf(pgonhd.getMtc_TransToYF());
            sapMtcPgonhdModel.setMtcdieQty(pgonhd.getMtc_DieQty());
            sapMtcPgonhdModel.setMtckillQty(pgonhd.getMtc_KillQty());
            sapMtcPgonhdModel.setMtctransToSc(pgonhd.getMtc_TransToSC());
            sapMtcPgonhdModel.setMtcsalesQtyInner(pgonhd.getMtc_SalesQty_Inner());
            sapMtcPgonhdModel.setMtcsalesQtyNormar(pgonhd.getMtc_SalesQty_Normar());
            sapMtcPgonhdModel.setMtcadjQty(pgonhd.getMtc_AdjQty());
            sapMtcPgonhdModel.setMtcendQty(pgonhd.getMtc_EndQty());
            sapMtcPgonhdModel.setMtcdaySum(pgonhd.getMtc_DaySum());
            sapMtcPgonhdModel.setMtcweight(pgonhd.getMtc_Weight());
            records.add(sapMtcPgonhdModel);
        }
        if (records.size() > 0) {
            sapMtcPgonhdMapper.inserts(records);
        }

        /******************************************************* XN部分 end *********************************************************************/
        /******************************************************* SAP部分 begin *********************************************************************/

        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(mtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(endDate, TimeUtil.DATE_FORMAT));
        // 业务代码:库存报废
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_9997);
        // 猪场编码
        mtcITFC.setMTC_DocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_SP);
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(TimeUtil.getSysTimestamp());
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));

        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
        if (listHanaMtcPgonhd.size() != 0) {
            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }

        /******************************************************* SAP部分 end **********************************************************************/

        /******************************************************* XN部分 begin *********************************************************************/
        // -- 后备猪 9997
        // 猪场编码
        sapMtcItfcModel.setMtcdocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_HB);
        // JSON文件长度
        sapMtcItfcModel.setMtcdataFileLen(Long.valueOf(jsonDataFile2.length()));
        if (listHanaMtcPgonhdHB.size() != 0) {
            sapMtcItfcMapper.insert(sapMtcItfcModel);
        }
        List<SapMtcPgonhdModel> recordsHB = new ArrayList<>();
        for (HanaMtcPgonhdDetail pgonhd : listHanaMtcPgonhdHB) {
            sapMtcPgonhdModel = new SapMtcPgonhdModel();
            sapMtcPgonhdModel.setSortNbr(0L);
            sapMtcPgonhdModel.setStatus("1");
            sapMtcPgonhdModel.setDeletedFlag("0");
            sapMtcPgonhdModel.setOriginFlag("S");
            sapMtcPgonhdModel.setOriginApp("XN1.0");
            sapMtcPgonhdModel.setCompanyId(getCompanyId());
            sapMtcPgonhdModel.setFarmId(getFarmId());
            sapMtcPgonhdModel.setHouseId(Long.valueOf(pgonhd.getMtc_HouseId()));
            sapMtcPgonhdModel.setHanaLogId(rowId);
            sapMtcPgonhdModel.setItfcId(sapMtcItfcModel.getRowId());
            sapMtcPgonhdModel.setCreateTime(TimeUtil.getSysTimestamp());
            sapMtcPgonhdModel.setMtcbranchId(pgonhd.getMtc_BranchID());
            sapMtcPgonhdModel.setMtcdeptId(pgonhd.getMtc_DeptID());
            sapMtcPgonhdModel.setMtcunit(HanaUtil.getMTC_Unit(Long.valueOf(pgonhd.getMtc_HouseId())));
            sapMtcPgonhdModel.setMtcpigType(pgonhd.getMtc_PigType());
            sapMtcPgonhdModel.setMtcperiod(pgonhd.getMtc_Period());
            sapMtcPgonhdModel.setMtcdocDate(TimeUtil.parseDate(pgonhd.getMtc_DocDate()));
            sapMtcPgonhdModel.setMtcbegQty(pgonhd.getMtc_BegQty());
            sapMtcPgonhdModel.setMtcinQty(pgonhd.getMtc_InQty());
            sapMtcPgonhdModel.setMtcpurQty(pgonhd.getMtc_PurQty());
            sapMtcPgonhdModel.setMtcinQtyInner(pgonhd.getMtc_InQty_Inner());
            sapMtcPgonhdModel.setMtcinQtyNormal(pgonhd.getMtc_InQty_Normal());
            sapMtcPgonhdModel.setMtcoutQtyInner(pgonhd.getMtc_OutQty_Inner());
            sapMtcPgonhdModel.setMtcoutQtyNormal(pgonhd.getMtc_OutQty_Normal());
            sapMtcPgonhdModel.setMtctransToCf(pgonhd.getMtc_OutQty_Inner());
            sapMtcPgonhdModel.setMtctransToBy(pgonhd.getMtc_TransToBY());
            sapMtcPgonhdModel.setMtctransToPy(pgonhd.getMtc_TransToPY());
            sapMtcPgonhdModel.setMtctransToHb(pgonhd.getMtc_TransToHB());
            sapMtcPgonhdModel.setMtctransToYf(pgonhd.getMtc_TransToYF());
            sapMtcPgonhdModel.setMtcdieQty(pgonhd.getMtc_DieQty());
            sapMtcPgonhdModel.setMtckillQty(pgonhd.getMtc_KillQty());
            sapMtcPgonhdModel.setMtctransToSc(pgonhd.getMtc_TransToSC());
            sapMtcPgonhdModel.setMtcsalesQtyInner(pgonhd.getMtc_SalesQty_Inner());
            sapMtcPgonhdModel.setMtcsalesQtyNormar(pgonhd.getMtc_SalesQty_Normar());
            sapMtcPgonhdModel.setMtcadjQty(pgonhd.getMtc_AdjQty());
            sapMtcPgonhdModel.setMtcendQty(pgonhd.getMtc_EndQty());
            sapMtcPgonhdModel.setMtcdaySum(pgonhd.getMtc_DaySum());
            sapMtcPgonhdModel.setMtcweight(pgonhd.getMtc_Weight());
            recordsHB.add(sapMtcPgonhdModel);
        }
        if (recordsHB.size() > 0) {
            sapMtcPgonhdMapper.inserts(recordsHB);
        }
        /******************************************************* XN部分 end *********************************************************************/

        /******************************************************* SAP部分 begin *********************************************************************/
        // 猪场编码
        mtcITFC.setMTC_DocNum(getCompanyCode() + "-" + HanaConstants.MTC_PigType_HB);
        mtcITFC.setMTC_DataFile(jsonDataFile2);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile2.length());
        if (listHanaMtcPgonhdHB.size() != 0) {
            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }
        /******************************************************* SAP部分 end **********************************************************************/
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9997");
        SecurityUtils.getSubject().getSession().removeAttribute("listHanaMtcPgonhd9997");
        SecurityUtils.getSubject().getSession().removeAttribute("listHanaMtcPgonhdHB9997");
    }

    // 再次上传
    @Override
    public int editSendSAPHanaLimit() {
        SysHanaInsertLogModel sysHanaInsertLog = new SysHanaInsertLogModel();
        SqlCon sqlCon = new SqlCon();
        // String settingValueByCode = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SAP_MAX_DELAY");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ? ");
        sqlCon.addMainSql(" AND TO_SAP_AGIN = 'N' ");
        // sqlCon.addMainSql(" AND TIMESTAMPDIFF(DAY,TO_SAP_DATE,NOW()) BETWEEN 0 AND " + settingValueByCode);
        sqlCon.addMainSql(" ORDER BY TO_SAP_DATE DESC LIMIT 1 ");
        List<SysHanaInsertLogModel> list = getList(sysHanaInsertLogMapper, sqlCon);
        if (list.size() == 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "无可修改数据。。。");
        }
        int update = 0;
        for (SysHanaInsertLogModel syslist : list) {
            sysHanaInsertLog.setToSapAgin("Y");
            sysHanaInsertLog.setRowId(syslist.getRowId());
            update = sysHanaInsertLogMapper.update(sysHanaInsertLog);
        }
        return update;
    }

    @Override
    public BasePageInfo searchSendSAPHana() {
        setToPage();
        Map<String, String> map = new HashMap<>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT ROW_ID rowId , SORT_NBR sortNbr,IF(DELETED_FLAG = '0' ,'否','是') deletedFlag, COMPANY_ID, FARM_ID farmId, TO_SAP_DATE toSapDate , CONCAT(CREAT_TIME) creatTime, CONCAT(DELETED_TIME) deletedTime , TO_SAP_AGIN toSapAgin ,SUMMARY summary ");
        sqlCon.addMainSql(" FROM SYS_HANA_INSERT_LOG ");
        sqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ? ");
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        return getPageInfo(list);
    }

    // 清空session
    private void removeSessionAttribute() {
        SecurityUtils.getSubject().getSession().removeAttribute("list9997");
        SecurityUtils.getSubject().getSession().removeAttribute("list9998");
        SecurityUtils.getSubject().getSession().removeAttribute("list9999");
        SecurityUtils.getSubject().getSession().removeAttribute("list3030");
        SecurityUtils.getSubject().getSession().removeAttribute("infosOutToHouseChangeClassQTY9997");
        SecurityUtils.getSubject().getSession().removeAttribute("infosOutToHouseNotChangeClassQTY9997");
        SecurityUtils.getSubject().getSession().removeAttribute("checkPigOfTypeQty9997");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9997");
        SecurityUtils.getSubject().getSession().removeAttribute("listHanaMtcPgonhd9997");
        SecurityUtils.getSubject().getSession().removeAttribute("listHanaMtcPgonhdHB9997");
        SecurityUtils.getSubject().getSession().removeAttribute("summary10000RowList");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9997");
        SecurityUtils.getSubject().getSession().removeAttribute("summary10000RowList");
        SecurityUtils.getSubject().getSession().removeAttribute("detaillist3030");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate3030");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9999");
        SecurityUtils.getSubject().getSession().removeAttribute("detaillist9999");
        SecurityUtils.getSubject().getSession().removeAttribute("endDate9998");
        SecurityUtils.getSubject().getSession().removeAttribute("hanaMtcPgtsdtlDetailList9998");
        SecurityUtils.getSubject().getSession().removeAttribute("hanaMtcPgtsdtlDetailHBList9998");
    }

    // 生成上传SAP数据(包含汇总-明细数据)
    @SuppressWarnings({ "unused", "unchecked" })
    @Override
    public Object editToSapSummaryDetail(Long code) throws Exception {
        // 最后返回的结果集
        Object returnObject = null;

        List<Map<String, Object>> summaryRowList = new ArrayList<>();

        Map<String, String> startAndEndDate = getStartAndEndDate();
        String startDate = startAndEndDate.get("startDate");
        String endDate = startAndEndDate.get("endDate");
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        boolean isImplement = false;

        if (isToHana) {
            // 判断 是否允许 超出时间范围 上传数据
            // searchHanaLogSign(endDate);

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            if (code == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "缺少参数CODE,无法区分表名。。。");
            }

            // 日报 9997
            // Map<String, List<HanaMtcPgonhdDetail>> sendSummarySapMtcPgonhd = null;
            // 存栏变动 9998
            // Map<String, List<HanaMtcPgtsdtlDetail>> sendSummarySapMtcPgtsdtl = null;
            // sap妊娠母猪状态汇总 9999
            // List<HanaMtcPgstatDetail> sendSummarySapPgstat = null;
            // 生产猪每月存栏位置 3030
            // List<HanaMtcPgstatDtlDetail> sendSummarySapPgstatDtl = null;
            Object list9997, list9998, list9999, list3030;
            switch (Integer.valueOf(code.toString())) {
            case 9997:
                // 日报 9997
                Map<String, List<HanaMtcPgonhdDetail>> sendSummarySapMtcPgonhd = sendToSapMtcPgonhd(startDate, endDate, mtcBranchID, mtcDeptID, true,
                        true);
                list9997 = SecurityUtils.getSubject().getSession().getAttribute("list9997");
                list9998 = SecurityUtils.getSubject().getSession().getAttribute("list9998");
                list9999 = SecurityUtils.getSubject().getSession().getAttribute("list9999");
                list3030 = SecurityUtils.getSubject().getSession().getAttribute("list3030");
                if (list9997 != null && list9998 != null && list9999 != null && list3030 != null) {
                    isImplement = true;
                    returnObject = sendSummarySapMtcPgonhd;
                    SecurityUtils.getSubject().getSession().removeAttribute("list9997");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9998");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9999");
                    SecurityUtils.getSubject().getSession().removeAttribute("list3030");
                    break;
                }
                return sendSummarySapMtcPgonhd;
            case 9998:
                // 存栏变动 9998
                Map<String, List<HanaMtcPgtsdtlDetail>> sendSummarySapMtcPgtsdtl = sendToSapMtcPgtsdtl(startDate, endDate, mtcBranchID, mtcDeptID,
                        true);
                list9997 = SecurityUtils.getSubject().getSession().getAttribute("list9997");
                list9998 = SecurityUtils.getSubject().getSession().getAttribute("list9998");
                list9999 = SecurityUtils.getSubject().getSession().getAttribute("list9999");
                list3030 = SecurityUtils.getSubject().getSession().getAttribute("list3030");
                if (list9997 != null && list9998 != null && list9999 != null && list3030 != null) {
                    isImplement = true;
                    returnObject = sendSummarySapMtcPgtsdtl;
                    SecurityUtils.getSubject().getSession().removeAttribute("list9997");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9998");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9999");
                    SecurityUtils.getSubject().getSession().removeAttribute("list3030");
                    break;
                }
                return sendSummarySapMtcPgtsdtl;
            case 9999:
                // sap妊娠母猪状态汇总 9999
                List<HanaMtcPgstatDetail> sendSummarySapPgstat = sendToSapPgstat(endDate, mtcBranchIDAndMTC_DeptIDMap, true);
                list9997 = SecurityUtils.getSubject().getSession().getAttribute("list9997");
                list9998 = SecurityUtils.getSubject().getSession().getAttribute("list9998");
                list9999 = SecurityUtils.getSubject().getSession().getAttribute("list9999");
                list3030 = SecurityUtils.getSubject().getSession().getAttribute("list3030");
                if (list9997 != null && list9998 != null && list9999 != null && list3030 != null) {
                    isImplement = true;
                    returnObject = sendSummarySapPgstat;
                    SecurityUtils.getSubject().getSession().removeAttribute("list9997");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9998");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9999");
                    SecurityUtils.getSubject().getSession().removeAttribute("list3030");
                    break;
                }
                return sendSummarySapPgstat;
            case 3030:
                // 生产猪每月存栏位置 3030
                List<HanaMtcPgstatDtlDetail> sendSummarySapPgstatDtl = sendToSapPgstatDtl(endDate, mtcBranchID, mtcDeptID, true);
                list9997 = SecurityUtils.getSubject().getSession().getAttribute("list9997");
                list9998 = SecurityUtils.getSubject().getSession().getAttribute("list9998");
                list9999 = SecurityUtils.getSubject().getSession().getAttribute("list9999");
                list3030 = SecurityUtils.getSubject().getSession().getAttribute("list3030");
                if (list9997 != null && list9998 != null && list9999 != null && list3030 != null) {
                    isImplement = true;
                    returnObject = sendSummarySapPgstatDtl;
                    SecurityUtils.getSubject().getSession().removeAttribute("list9997");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9998");
                    SecurityUtils.getSubject().getSession().removeAttribute("list9999");
                    SecurityUtils.getSubject().getSession().removeAttribute("list3030");
                    break;
                }
                return sendSummarySapPgstatDtl;
            case 10000:
                sendSummarySapMtcPgonhd = sendToSapMtcPgonhd(startDate, endDate, mtcBranchID, mtcDeptID, true, true);
                sendSummarySapMtcPgtsdtl = sendToSapMtcPgtsdtl(startDate, endDate, mtcBranchID, mtcDeptID, true);
                sendSummarySapPgstat = sendToSapPgstat(endDate, mtcBranchIDAndMTC_DeptIDMap, true);
                sendSummarySapPgstatDtl = sendToSapPgstatDtl(endDate, mtcBranchID, mtcDeptID, true);
                isImplement = true;
                break;
            default:
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "没有这个类型。。。");
                break;
            }
            if (isImplement) {

                // 常规转 - 转状态
                List<Map<String, Object>> infosOutToHouseChangeClassQTY9997 = (List<Map<String, Object>>) SecurityUtils.getSubject().getSession()
                        .getAttribute("infosOutToHouseChangeClassQTY9997");
                // 内转 - 不转状态
                List<Map<String, Object>> infosOutToHouseNotChangeClassQTY9997 = (List<Map<String, Object>>) SecurityUtils.getSubject().getSession()
                        .getAttribute("infosOutToHouseNotChangeClassQTY9997");

                // 盘点表数据
                List<Map<String, Object>> checkPigOfTypeQty9997 = (List<Map<String, Object>>) SecurityUtils.getSubject().getSession().getAttribute(
                        "checkPigOfTypeQty9997");

                // 9997 肉猪
                List<HanaMtcPgonhdDetail> listHanaMtcPgonhd = (List<HanaMtcPgonhdDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                        "listHanaMtcPgonhd9997");
                // 9997 后备猪
                List<HanaMtcPgonhdDetail> listHanaMtcPgonhdHB = (List<HanaMtcPgonhdDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                        "listHanaMtcPgonhdHB9997");
                // 9998 肉猪
                List<HanaMtcPgtsdtlDetail> hanaMtcPgtsdtlDetailList = (List<HanaMtcPgtsdtlDetail>) SecurityUtils.getSubject().getSession()
                        .getAttribute("hanaMtcPgtsdtlDetailList9998");
                // 9998 后备猪
                List<HanaMtcPgtsdtlDetail> hanaMtcPgtsdtlDetailHBList = (List<HanaMtcPgtsdtlDetail>) SecurityUtils.getSubject().getSession()
                        .getAttribute("hanaMtcPgtsdtlDetailHBList9998");
                // 9999 sap妊娠母猪状态汇总
                List<HanaMtcPgstatDetail> detaillist = (List<HanaMtcPgstatDetail>) SecurityUtils.getSubject().getSession().getAttribute(
                        "detaillist9999");

                Map<String, Object> summaryRowMap = new HashMap<>();
                Long BRZZ = 0l, BY = 0l, YF = 0l, PY = 0l;

                // 期初部分
                Long begProductSowQty = 0l, begProductBoarQty = 0l, begHBMZQty = 0l, begHBGZQty = 0l, begRZQty = 0l;
                // 期末部分
                Long endProductSowQty = 0l, endProductBoarQty = 0l, endHBMZQty = 0l, endHBGZQty = 0l, endRZQty = 0l;
                Long endBRZZ = 0l, endBY = 0l, endYF = 0l, endPY = 0l;

                Long CFBirth = 0l, CFBY = 0l, BYYF = 0l, YFHB = 0l, HBProduct = 0l;
                Long CFCF = 0l, BYBY = 0l, YFYF = 0l, HBHB = 0l;

                Long saleNormorside = 0l, saleAllocation = 0l, saleInnerside = 0l, selfkill = 0l;
                Long CFDIE = 0l, BYDIE = 0l, YFDie = 0l, HBDie = 0l, PYDie = 0l;

                Long feed = 0l, veterinaryDrugs = 0l, turnoverMaterial = 0l, manufacturingExpenses = 0l, pigPurchase = 0l, insidePurchase = 0l,
                        outsidePurchase = 0l, GYLAllocation = 0l;

                Long CFAddQTY = 0l, CFReduceQTY = 0l, BYAddQTY = 0l, BYReduceQTY = 0l, YFAddQTY = 0l, YFReduceQTY = 0l, PYAddQTY = 0l,
                        PYReduceQTY = 0l, HBAddQTY = 0l, HBReduceQTY = 0l;

                Long step1 = 0l, step2 = 0l;
                // 常规转 - 转状态
                for (Map<String, Object> map : infosOutToHouseChangeClassQTY9997) {
                    Maps.getLong(map, "CF_QTY");
                    CFBY = CFBY + Maps.getLong(map, "BY_QTY");
                    YFHB = YFHB + Maps.getLong(map, "HB_QTY");
                    BYYF = BYYF + Maps.getLong(map, "YF_QTY") + Maps.getLong(map, "PY_QTY");
                    HBHB = HBHB + Maps.getLong(map, "HB_HB_QTY");
                }
                // 内转 - 不转状态
                for (Map<String, Object> map : infosOutToHouseNotChangeClassQTY9997) {
                    CFCF = CFCF + Maps.getLong(map, "CF_QTY");
                    BYBY = BYBY + Maps.getLong(map, "BY_QTY");
                    HBHB = HBHB + Maps.getLong(map, "HB_QTY");
                    YFYF = YFYF + Maps.getLong(map, "YF_QTY") + Maps.getLong(map, "PY_QTY");
                }

                // 月末种猪盘点数据
                /**
                 * 盘点猪只类型
                 * 1:生产公猪，3:生产母猪，
                 * 2:后备公猪，4:后备母猪，
                 * 5:肉猪
                 */
                for (Map<String, Object> checkPig : checkPigOfTypeQty9997) {
                    int checkPigType = Maps.getInt(checkPig, "CHECK_PIG_TYPE");
                    long checkPigQty = Maps.getLong(checkPig, "CHECK_QTY");
                    switch (checkPigType) {
                    case 5:
                        endRZQty = endRZQty + checkPigQty;
                        break;
                    case 4:
                        endHBMZQty = endHBMZQty + checkPigQty;
                        break;
                    case 3:
                        endProductSowQty = endProductSowQty + checkPigQty;
                        break;
                    case 2:
                        endHBGZQty = endHBGZQty + checkPigQty;
                        break;
                    case 1:
                        endProductBoarQty = endProductBoarQty + checkPigQty;
                        break;

                    default:
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "没有这个猪只盘点类型。。。");
                        break;
                    }
                }

                if (detaillist != null) {
                    // 计算重胎轻胎个xx头
                    for (HanaMtcPgstatDetail sapPgstatDetai : detaillist) {
                        step1 = step1 + Long.valueOf(sapPgstatDetai.getMtc_PregStep_1());
                        step2 = step2 + Long.valueOf(sapPgstatDetai.getMtc_PregStep_2());
                    }
                }

                // *********************************************************** 日报相关 begin***********************************************
                if (listHanaMtcPgonhdHB != null) {
                    // 9997 后备猪
                    for (HanaMtcPgonhdDetail sumPgonhd : listHanaMtcPgonhdHB) {
                        Map<String, String> house = CacheUtil.getData(getFarmId(), "PP_O_HOUSE", sumPgonhd.getMtc_HouseId());
                        // 期初
                        if (0 == TimeUtil.compareDate(startDate, sumPgonhd.getMtc_DocDate(), Calendar.DATE)) {
                            // 无法区分公猪母猪
                            begHBMZQty = begHBMZQty + Long.valueOf(sumPgonhd.getMtc_BegQty());
                            begHBGZQty = begHBGZQty + Long.valueOf(sumPgonhd.getMtc_BegQty());

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                                BRZZ = BRZZ + Long.valueOf(sumPgonhd.getMtc_BegQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                                BY = BY + Long.valueOf(sumPgonhd.getMtc_BegQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                                // if ("N".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                // YF = YF + Long.valueOf(sumPgonhd.getMtc_BegQty());
                                // } else
                                if ("Y".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                    PY = PY + Long.valueOf(sumPgonhd.getMtc_BegQty());
                                } else {
                                    YF = YF + Long.valueOf(sumPgonhd.getMtc_BegQty());
                                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                                }

                            }
                        }
                        // 月末
                        if (0 == TimeUtil.compareDate(endDate, sumPgonhd.getMtc_DocDate(), Calendar.DATE)) {
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                                endBRZZ = endBRZZ + Long.valueOf(sumPgonhd.getMtc_EndQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                                endBY = endBY + Long.valueOf(sumPgonhd.getMtc_EndQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                                // if ("N".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                // endYF = Long.valueOf(sumPgonhd.getMtc_EndQty());
                                // } else
                                if ("Y".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                    endPY = endPY + Long.valueOf(sumPgonhd.getMtc_EndQty());
                                } else {
                                    endYF = endYF + Long.valueOf(sumPgonhd.getMtc_EndQty());
                                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                                }
                            }
                        }

                    }
                }

                if (listHanaMtcPgonhd != null) {
                    // 9997 肉猪
                    for (HanaMtcPgonhdDetail sumPgonhd : listHanaMtcPgonhd) {
                        Map<String, String> house = CacheUtil.getData(getFarmId(), "PP_O_HOUSE", sumPgonhd.getMtc_HouseId());
                        // 期初
                        if (0 == TimeUtil.compareDate(startDate, sumPgonhd.getMtc_DocDate(), Calendar.DATE)) {

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                                BRZZ = BRZZ + Long.valueOf(sumPgonhd.getMtc_BegQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                                BY = BY + Long.valueOf(sumPgonhd.getMtc_BegQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                                // if ("N".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                // YF = YF + Long.valueOf(sumPgonhd.getMtc_BegQty());
                                // } else
                                if ("Y".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                    PY = PY + Long.valueOf(sumPgonhd.getMtc_BegQty());
                                } else {
                                    YF = YF + Long.valueOf(sumPgonhd.getMtc_BegQty());
                                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                                }
                            }

                        }

                        // 月末
                        if (0 == TimeUtil.compareDate(endDate, sumPgonhd.getMtc_DocDate(), Calendar.DATE)) {
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                                endBRZZ = endBRZZ + Long.valueOf(sumPgonhd.getMtc_EndQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                                endBY = endBY + Long.valueOf(sumPgonhd.getMtc_EndQty());
                            }

                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                                // if ("N".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                // endYF = Long.valueOf(sumPgonhd.getMtc_EndQty());
                                // } else
                                if ("Y".equals(sumPgonhd.getMtc_IS_FOSTER_HOUSE())) {
                                    endPY = endPY + Long.valueOf(sumPgonhd.getMtc_EndQty());
                                } else {
                                    endYF = endYF + Long.valueOf(sumPgonhd.getMtc_EndQty());
                                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                                }
                            }
                        }
                    }

                }
                // *********************************************************** 日报相关 end***********************************************

                // ***********************************存栏调整 begin ************************************************************
                if (hanaMtcPgtsdtlDetailHBList != null) {
                    // 存栏调整 9998 后备猪
                    for (HanaMtcPgtsdtlDetail sumPgtsdtl : hanaMtcPgtsdtlDetailHBList) {
                        // 猪舍
                        Map<String, String> house = CacheUtil.getData(getFarmId(), "PP_O_HOUSE", sumPgtsdtl.getMtc_HouseId());
                        // 关联猪舍
                        Map<String, String> relaHouse = new HashMap<>();
                        if (!StringUtil.isBlank(sumPgtsdtl.getMtc_RelaUnit_HouseId())) {
                            relaHouse = CacheUtil.getData(Long.valueOf(sumPgtsdtl.getMtc_RelaDeptID_FarmId()), "PP_O_HOUSE", sumPgtsdtl
                                    .getMtc_RelaUnit_HouseId());
                        }

                        if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                            CFReduceQTY = CFReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            CFAddQTY = CFAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                            BYReduceQTY = BYReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            BYAddQTY = BYAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                            // if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("N")) {
                            // YFReduceQTY = YFReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            // YFAddQTY = YFAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                            // } else
                            if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("Y")) {

                                PYReduceQTY = PYReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                PYAddQTY = PYAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                            } else {
                                YFReduceQTY = YFReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                YFAddQTY = YFAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                                // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                            }
                        }

                        // 后备入场
                        if ("3010".equals(sumPgtsdtl.getMtc_TransType())) {
                            HBAddQTY = HBAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        // 后备转生产记录
                        if ("3020".equals(sumPgtsdtl.getMtc_TransType())) {
                            HBProduct = HBProduct + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                        }
                        // 分娩记录
                        if ("3030".equals(sumPgtsdtl.getMtc_TransType())) {
                            CFBirth = CFBirth + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        // 死亡
                        if ("3060".equals(sumPgtsdtl.getMtc_TransType())) {
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                                CFDIE = CFDIE + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                                BYDIE = BYDIE + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                                // if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("N")) {
                                // YFDie = YFDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                // } else
                                if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("Y")) {
                                    PYDie = PYDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                } else {
                                    YFDie = YFDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                                }
                            }
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == PigConstants.HOUSE_CLASS_MOTHBALL) {
                                HBDie = HBDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                        // 自宰
                        if ("3070".equals(sumPgtsdtl.getMtc_TransType())) {
                            selfkill = selfkill + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_MOTHBALL)) {
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                        // 内销
                        if ("3080".equals(sumPgtsdtl.getMtc_TransType())) {
                            saleInnerside = saleInnerside + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_MOTHBALL)) {
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                        // 外销
                        if ("3090".equals(sumPgtsdtl.getMtc_TransType())) {
                            saleNormorside = saleNormorside + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_MOTHBALL)) {
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                    }

                }
                if (hanaMtcPgtsdtlDetailList != null) {
                    // 存栏调整 9998 肉猪
                    for (HanaMtcPgtsdtlDetail sumPgtsdtl : hanaMtcPgtsdtlDetailList) {
                        // 猪舍
                        Map<String, String> house = CacheUtil.getData(getFarmId(), "PP_O_HOUSE", sumPgtsdtl.getMtc_HouseId());
                        // 关联猪舍
                        Map<String, String> relaHouse = new HashMap<>();
                        if (!StringUtil.isBlank(sumPgtsdtl.getMtc_RelaUnit_HouseId())) {
                            relaHouse = CacheUtil.getData(Long.valueOf(sumPgtsdtl.getMtc_RelaDeptID_FarmId()), "PP_O_HOUSE", sumPgtsdtl
                                    .getMtc_RelaUnit_HouseId());
                        }

                        if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                            CFReduceQTY = CFReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            CFAddQTY = CFAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                            BYReduceQTY = BYReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            BYAddQTY = BYAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                            // if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("N")) {
                            // YFReduceQTY = YFReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            // YFAddQTY = YFAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                            // } else
                            if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("Y")) {
                                PYReduceQTY = PYReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                PYAddQTY = PYAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                            } else {
                                YFReduceQTY = YFReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                YFAddQTY = YFAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                                // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                            }
                        }

                        // 后备入场
                        if ("3010".equals(sumPgtsdtl.getMtc_TransType())) {
                            HBAddQTY = HBAddQTY + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        // 后备转生产记录
                        if ("3020".equals(sumPgtsdtl.getMtc_TransType())) {
                            HBProduct = HBProduct + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                        }
                        // 分娩记录
                        if ("3030".equals(sumPgtsdtl.getMtc_TransType())) {
                            CFBirth = CFBirth + Long.valueOf(sumPgtsdtl.getMtc_InQty());
                        }
                        // 死亡
                        if ("3060".equals(sumPgtsdtl.getMtc_TransType())) {
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_DELIVERY)) {
                                CFDIE = CFDIE + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_CAREPIG)) {
                                BYDIE = BYDIE + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_FATTEN)) {
                                // if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("N")) {
                                // YFDie = YFDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                // } else
                                if (Maps.getString(house, "IS_FOSTER_HOUSE", "").equals("Y")) {
                                    PYDie = PYDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                } else {
                                    YFDie = YFDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【育肥舍,其他】");
                                }
                            }
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == PigConstants.HOUSE_CLASS_MOTHBALL) {
                                HBDie = HBDie + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                        // 自宰
                        if ("3070".equals(sumPgtsdtl.getMtc_TransType())) {
                            selfkill = selfkill + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_MOTHBALL)) {
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                        // 内销
                        if ("3080".equals(sumPgtsdtl.getMtc_TransType())) {
                            saleInnerside = saleInnerside + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_MOTHBALL)) {
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                        // 外销
                        if ("3090".equals(sumPgtsdtl.getMtc_TransType())) {
                            saleNormorside = saleNormorside + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            if (Long.valueOf(Maps.getString(house, "HOUSE_TYPE", "")) == (PigConstants.HOUSE_CLASS_MOTHBALL)) {
                                HBReduceQTY = HBReduceQTY + Long.valueOf(sumPgtsdtl.getMtc_OutQty());
                            }
                        }
                    }

                }
                // ***********************************存栏调整 end ************************************************************

                Map<String, Object> summaryLineMap = new HashMap<>();
                int index = 1;
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                Long allPigQty = endProductSowQty + endProductBoarQty + endHBMZQty + endHBGZQty + endRZQty;
                summaryLineMap.put("endQty", allPigQty);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "明细表中无法获取期初及期间变动");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "pigStock");
                summaryLineMap.put("typeName", "猪场库存");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endProductSowQty);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "轻胎" + step1 + "头，重胎" + step2 + "头,明细表中无法获取期初及期间变动");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "productSow");
                summaryLineMap.put("typeName", "生产母猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endProductBoarQty);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "明细表中无法获取期初及期间变动");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "productBoar");
                summaryLineMap.put("typeName", "生产公猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", begHBMZQty);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", HBAddQTY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", HBReduceQTY);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endHBMZQty);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "后备猪无法区分公猪母猪,故非盘点值相等");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "HBMZ");
                summaryLineMap.put("typeName", "后备母猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", begHBGZQty);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", HBAddQTY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", HBReduceQTY);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endHBGZQty);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "后备猪无法区分公猪母猪,故非盘点值相等");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "HBGZ");
                summaryLineMap.put("typeName", "后备公猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", BRZZ);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", CFAddQTY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", CFReduceQTY);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endBRZZ);
                summaryLineMap.put("endMoney", 0);
                if (BRZZ + CFAddQTY - CFReduceQTY != endBRZZ) {
                    summaryLineMap.put("other", "【哺乳仔猪】【期初:" + BRZZ + "】【增加:" + CFAddQTY + "】【减少:" + CFReduceQTY + "】【期末:" + endBRZZ + "】等式不成立！");
                } else {
                    summaryLineMap.put("other", "");
                }
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "BRZZ");
                summaryLineMap.put("typeName", "哺乳仔猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", BY);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", BYAddQTY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", BYReduceQTY);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endBY);
                summaryLineMap.put("endMoney", 0);
                if (BY + BYAddQTY - BYReduceQTY != endBY) {
                    summaryLineMap.put("other", "【保育猪】【期初:" + BY + "】【增加:" + BYAddQTY + "】【减少:" + BYReduceQTY + "】【期末:" + endBY + "】等式不成立！");
                } else {
                    summaryLineMap.put("other", "");
                }
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "BY");
                summaryLineMap.put("typeName", "保育猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", YF);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", YFAddQTY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", YFReduceQTY);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endYF);
                summaryLineMap.put("endMoney", 0);
                if (YF + YFAddQTY - YFReduceQTY != endYF) {
                    summaryLineMap.put("other", "【育肥猪】【期初:" + YF + "】【增加:" + YFAddQTY + "】【减少:" + YFReduceQTY + "】【期末:" + endYF + "】等式不成立！");
                } else {
                    summaryLineMap.put("other", "");
                }
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "YF");
                summaryLineMap.put("typeName", "育肥猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", PY);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", PYAddQTY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", PYReduceQTY);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", endPY);
                summaryLineMap.put("endMoney", 0);
                if (PY + PYAddQTY - PYReduceQTY != endPY) {
                    summaryLineMap.put("other", "【培育猪】【期初:" + PY + "】【增加:" + PYAddQTY + "】【减少:" + PYReduceQTY + "】【期末:" + endPY + "】等式不成立！");
                } else {
                    summaryLineMap.put("other", "");
                }
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "PY");
                summaryLineMap.put("typeName", "培育猪");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", CFBirth);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "CFBirth");
                summaryLineMap.put("typeName", "产房出生");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", CFBY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "CFBY");
                summaryLineMap.put("typeName", "产房转保育");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", CFCF);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "CFCF");
                summaryLineMap.put("typeName", "产房内转");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", CFDIE);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "CFDIE");
                summaryLineMap.put("typeName", "产房死亡");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", BYYF);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "BYYF");
                summaryLineMap.put("typeName", "保育转育肥");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", BYBY);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "BYBY");
                summaryLineMap.put("typeName", "保育内转");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", BYDIE);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "BYDIE");
                summaryLineMap.put("typeName", "保育死亡");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", YFHB);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "YFHB");
                summaryLineMap.put("typeName", "育肥转后备");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", YFYF);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "YFYF");
                summaryLineMap.put("typeName", "育肥内转");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", YFDie);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "YFDie");
                summaryLineMap.put("typeName", "育肥死亡");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", HBProduct);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "HBProduct");
                summaryLineMap.put("typeName", "后备转生产");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", HBDie);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "HBDie");
                summaryLineMap.put("typeName", "后备死亡");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", saleNormorside);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "saleNormorside");
                summaryLineMap.put("typeName", "外销");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", saleAllocation);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "saleAllocation");
                summaryLineMap.put("typeName", "调拨");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", saleInnerside);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "saleInnerside");
                summaryLineMap.put("typeName", "内销");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", selfkill);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "selfkill");
                summaryLineMap.put("typeName", "自宰");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "feed");
                summaryLineMap.put("typeName", "饲料");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "veterinaryDrugs");
                summaryLineMap.put("typeName", "兽药");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "turnoverMaterial");
                summaryLineMap.put("typeName", "周转材料");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "manufacturingExpenses");
                summaryLineMap.put("typeName", "制造费用");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "pigPurchase");
                summaryLineMap.put("typeName", "猪只采购");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "insidePurchase");
                summaryLineMap.put("typeName", "内部采购");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "outsidePurchase");
                summaryLineMap.put("typeName", "外购");
                summaryRowList.add(summaryLineMap);

                summaryLineMap = new HashMap<>();
                summaryRowMap = new HashMap<String, Object>();
                summaryLineMap.put("begQty", 0);
                summaryLineMap.put("begMoney", 0);
                summaryLineMap.put("addQty", 0);
                summaryLineMap.put("addMoney", 0);
                summaryLineMap.put("reduceQty", 0);
                summaryLineMap.put("reduceMoney", 0);
                summaryLineMap.put("endQty", 0);
                summaryLineMap.put("endMoney", 0);
                summaryLineMap.put("other", "");
                summaryLineMap.put("lineNumber", index++);
                summaryLineMap.put("type", "GYLAllocation");
                summaryLineMap.put("typeName", "调拨");
                summaryRowList.add(summaryLineMap);

                SecurityUtils.getSubject().getSession().removeAttribute("infosOutToHouseChangeClassQTY9997");
                SecurityUtils.getSubject().getSession().removeAttribute("infosOutToHouseNotChangeClassQTY9997");
                SecurityUtils.getSubject().getSession().removeAttribute("checkPigOfTypeQty9997");

                // 验证数据的可靠性 调试用
                // for (int index2 = 0; index2 < summaryRowList.size(); index2++) {
                // Map<String, Object> map = summaryRowList.get(index2);
                // long begQty = Maps.getLong(map, "begQty");
                // long addQty = Maps.getLong(map, "addQty");
                // long reduceQty = Maps.getLong(map, "reduceQty");
                // long endQty = Maps.getLong(map, "endQty");
                // long lineNumber = Maps.getLong(map, "lineNumber");
                // if (endQty != begQty + addQty - reduceQty) {
                // System.err.println("第" + lineNumber + "行数据核对不上!");
                // // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + lineNumber + "行数据核对不上!");
                // }
                // }

                SecurityUtils.getSubject().getSession().setAttribute("summary10000RowList", summaryRowList);

                // 为空即为汇总,否则即为明细查询
                if (returnObject == null) {
                    returnObject = summaryRowList;
                }
            }
        }

        return returnObject;
    }

    // 9997 汇总表用
    public List<Map<String, Object>> getInfosOutToHouseSummaryQTY(String startDate, String endDate) {

        Map<String, String> sqlMap = new HashMap<String, String>();
        SqlCon sqlCon = new SqlCon();
        /********************************************* 常规转 - 转状态 begin *********************************************/
        // 只关心 转入后的状态
        // 转出到产房
        // 转出到保育
        // 转出到培育
        // 转出到后备
        // 转出到育肥
        sqlCon.addMainSql(
                " SELECT TT.HOUSE_ID, TT.CHANGE_HOUSE_DATE, SUM(TT.CF_QTY) CF_QTY, SUM(TT.BY_QTY) BY_QTY, SUM(TT.PY_QTY) PY_QTY, SUM(TT.HB_QTY) HB_QTY, SUM(TT.YF_QTY) YF_QTY, SUM(TT.HB_CF_QTY) HB_CF_QTY , SUM(HB_BY_QTY) HB_BY_QTY , SUM(HB_PY_QTY) HB_PY_QTY , SUM(HB_HB_QTY) HB_HB_QTY , SUM(HB_YF_QTY) HB_YF_QTY ");
        sqlCon.addMainSql(" FROM ( ");
        /* 场内 转舍转出 */
        sqlCon.addMainSql(" SELECT T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (12,13,14) ,1,0)) CF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        /* T2 -> T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1 ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T3 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.PIG_ID AND T1.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        // sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T4 ");
        // sqlCon.addMainSql(
        // " ON T1.FARM_ID = T4.FARM_ID AND T1.PIG_ID = T4.PIG_ID AND T2.BILL_ID = T4.BILL_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" INNER JOIN pp_L_pig T5 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID  = T5.FARM_ID AND T1.PIG_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' AND T5.ORIGIN <> '3' ");
        sqlCon.addMainSql(" INNER JOIN PP_O_HOUSE T6 ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T2.HOUSE_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE t1.DELETED_FLAG = '0' AND t1.STATUS = '1' ");
        // 排除后备情期鉴定 (状态:4)
        sqlCon.addMainSql(" AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4,12,13,14,15,16) ");
        // 去除 转入舍 = 转出舍
        sqlCon.addMainSql(" AND T1.HOUSE_ID <> T2.HOUSE_ID ");
        // 常规转 - 转状态
        sqlCon.addMainSql(" AND T3.PIG_ID IS NOT NULL ");
        sqlCon.addCondition(startDate, " AND t1.CHANGE_HOUSE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addCondition(getFarmId(), " AND t1.FARM_ID = ? ");
        sqlCon.addMainSql(" GROUP BY T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE ");

        /* 转状态转出 转出到后备且没有转舍记录的猪 (一般只有商品猪转后备) "); */
        sqlCon.addMainSql(" UNION ALL ");
        sqlCon.addMainSql(" SELECT T1.HOUSE_ID,T1.TOWORK_DATE CHANGE_HOUSE_DATE,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS IN (12,13,14),1,0)) CF_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS IN (1,3),1,0)) HB_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS IN (1,3),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        // T2 --> T1
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T1  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T2.ROW_ID = T1.CHANGE_PIG_CLASS_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.PIG_TYPE <> T2.PIG_TYPE ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.PIG_ID AND T1.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T5  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' AND T5.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T6  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T1.HOUSE_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T3.ROW_ID IS NULL  ");
        // 只查转后备的猪
        sqlCon.addMainSql(" AND T1.PIG_CLASS IN (1,3) ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T1.TOWORK_DATE BETWEEN ?  ");
        sqlCon.addCondition(endDate, " AND ?  ");
        sqlCon.addMainSql(" GROUP BY T1.TOWORK_DATE,T1.HOUSE_ID ");

        sqlCon.addMainSql(" UNION ALL ");
        /* 场间转出 包含转后备-转产房-转保育-转育肥-转培育 */
        sqlCon.addMainSql(" SELECT T1.HOUSE_ID,T1.LEAVE_DATE CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS IN (12,13,14),1,0)) CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        sqlCon.addMainSql(" FROM PP_L_PIG T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T1  ");
        sqlCon.addMainSql(
                " ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.SAP_SALE_TYPE = 3 AND T0.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2  ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T3   ");
        sqlCon.addMainSql(
                " ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T5   ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T6   ");
        sqlCon.addMainSql(
                " ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T7 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T8 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T8.FARM_ID AND T7.HOUSE_ID = T8.ROW_ID AND T8.DELETED_FLAG = '0' AND T8.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  ");
        sqlCon.addMainSql(" AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) = IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE)  ");
        // 转状态 - 转出
        sqlCon.addMainSql(" AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCon.addCondition(startDate, " AND T1.LEAVE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T1.HOUSE_ID,T1.LEAVE_DATE  ");
        sqlCon.addMainSql(" ) TT GROUP BY TT.HOUSE_ID,TT.CHANGE_HOUSE_DATE ");

        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosOutToHouseChangeClassQTY = paramMapper.getObjectInfos(sqlMap);
        /********************************************* 常规转 - 转状态 end *********************************************/
        sqlMap = new HashMap<String, String>();
        sqlCon = new SqlCon();
        /********************************************* 内转 - 不转状态 begin *********************************************/
        // 只关心 转入后的状态
        // 转出到产房
        // 转出到保育
        // 转出到培育
        // 转出到后备
        // 转出到育肥
        sqlCon.addMainSql(
                " SELECT TT.HOUSE_ID, TT.CHANGE_HOUSE_DATE, SUM(TT.CF_QTY) CF_QTY, SUM(TT.BY_QTY) BY_QTY, SUM(TT.PY_QTY) PY_QTY, SUM(TT.HB_QTY) HB_QTY, SUM(TT.YF_QTY) YF_QTY, SUM(TT.HB_CF_QTY) HB_CF_QTY , SUM(HB_BY_QTY) HB_BY_QTY , SUM(HB_PY_QTY) HB_PY_QTY , SUM(HB_HB_QTY) HB_HB_QTY , SUM(HB_YF_QTY) HB_YF_QTY ");
        sqlCon.addMainSql(" FROM ( ");
        /* 场内 转舍转出 */
        sqlCon.addMainSql(" SELECT T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (12,13,14) ,1,0)) CF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        /* T2 -> T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1 ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T3 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.PIG_ID AND T1.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        // sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T4 ");
        // sqlCon.addMainSql(
        // " ON T1.FARM_ID = T4.FARM_ID AND T1.PIG_ID = T4.PIG_ID AND T2.BILL_ID = T4.BILL_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" INNER JOIN pp_L_pig T5 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID  = T5.FARM_ID AND T1.PIG_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' AND T5.ORIGIN <> '3' ");
        sqlCon.addMainSql(" INNER JOIN PP_O_HOUSE T6 ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T2.HOUSE_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE t1.DELETED_FLAG = '0' AND t1.STATUS = '1' ");
        sqlCon.addMainSql(" AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4,12,13,14,15,16) ");
        // 纯转舍 - 内转
        sqlCon.addMainSql(" AND T1.HOUSE_ID <> T2.HOUSE_ID ");
        // 常规转 - 转状态
        sqlCon.addMainSql(" AND T3.PIG_ID IS NULL ");
        sqlCon.addCondition(startDate, " AND t1.CHANGE_HOUSE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addCondition(getFarmId(), " AND t1.FARM_ID = ? ");
        sqlCon.addMainSql(" GROUP BY T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE ");

        sqlCon.addMainSql(" UNION ALL ");
        /* 场间转出 包含转后备-转产房-转保育-转育肥-转培育 */
        sqlCon.addMainSql(" SELECT T1.HOUSE_ID,T1.LEAVE_DATE CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS IN (12,13,14),1,0)) CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        sqlCon.addMainSql(" FROM PP_L_PIG T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T1  ");
        sqlCon.addMainSql(
                " ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.SAP_SALE_TYPE = 3 AND T0.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2  ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T3   ");
        sqlCon.addMainSql(
                " ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T5   ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T6   ");
        sqlCon.addMainSql(
                " ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T7 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T8 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T8.FARM_ID AND T7.HOUSE_ID = T8.ROW_ID AND T8.DELETED_FLAG = '0' AND T8.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  ");
        sqlCon.addMainSql(" AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) = IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE)  ");
        // 内转
        sqlCon.addMainSql(" AND T0.ENTER_PIG_CLASS = T1.PIG_CLASS ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCon.addCondition(startDate, " AND T1.LEAVE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T1.HOUSE_ID,T1.LEAVE_DATE  ");
        sqlCon.addMainSql(" ) TT GROUP BY TT.HOUSE_ID,TT.CHANGE_HOUSE_DATE ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosOutToHouseNotChangeClassQTY = paramMapper.getObjectInfos(sqlMap);

        SecurityUtils.getSubject().getSession().setAttribute("infosOutToHouseChangeClassQTY9997", infosOutToHouseChangeClassQTY);
        SecurityUtils.getSubject().getSession().setAttribute("infosOutToHouseNotChangeClassQTY9997", infosOutToHouseNotChangeClassQTY);

        return getInfosOutToHouseQTY(startDate, endDate);
    }

    // 9997 明细
    public List<Map<String, Object>> getInfosOutToHouseQTY(String startDate, String endDate) {

        Map<String, String> sqlMap = new HashMap<String, String>();
        SqlCon sqlCon = new SqlCon();
        // 只关心 转入后的状态
        // 转出到产房
        // 转出到保育
        // 转出到培育
        // 转出到后备
        // 转出到育肥
        sqlCon.addMainSql(
                " SELECT TT.HOUSE_ID, TT.CHANGE_HOUSE_DATE, SUM(TT.CF_QTY) CF_QTY, SUM(TT.BY_QTY) BY_QTY, SUM(TT.PY_QTY) PY_QTY, SUM(TT.HB_QTY) HB_QTY, SUM(TT.YF_QTY) YF_QTY, SUM(TT.HB_CF_QTY) HB_CF_QTY , SUM(HB_BY_QTY) HB_BY_QTY , SUM(HB_PY_QTY) HB_PY_QTY , SUM(HB_HB_QTY) HB_HB_QTY , SUM(HB_YF_QTY) HB_YF_QTY ");
        sqlCon.addMainSql(" FROM ( ");
        /* 场内 转舍转出 */
        sqlCon.addMainSql(" SELECT T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (12,13,14) ,1,0)) CF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        /* T2 -> T1 */
        sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T1 ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T2 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T3 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.PIG_ID AND T1.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        // sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_TOWORK T4 ");
        // sqlCon.addMainSql(
        // " ON T1.FARM_ID = T4.FARM_ID AND T1.PIG_ID = T4.PIG_ID AND T2.BILL_ID = T4.BILL_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" INNER JOIN pp_L_pig T5 ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID  = T5.FARM_ID AND T1.PIG_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' AND T5.ORIGIN <> '3' ");
        sqlCon.addMainSql(" INNER JOIN PP_O_HOUSE T6 ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T2.HOUSE_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE t1.DELETED_FLAG = '0' AND t1.STATUS = '1' ");
        // 排除后备情期鉴定 (状态:4)
        sqlCon.addMainSql(" AND IFNULL(T3.PIG_CLASS,T1.PIG_CLASS) IN (1,3,4,12,13,14,15,16) ");
        // 去除 转入舍 = 转出舍
        sqlCon.addMainSql(" AND T1.HOUSE_ID <> T2.HOUSE_ID ");
        // // 常规转 - 转状态
        // sqlCon.addMainSql(" AND T3.PIG_ID IS NOT NULL ");
        sqlCon.addCondition(startDate, " AND t1.CHANGE_HOUSE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addCondition(getFarmId(), " AND t1.FARM_ID = ? ");
        sqlCon.addMainSql(" GROUP BY T2.HOUSE_ID,T1.CHANGE_HOUSE_DATE ");

        /* 转状态转出 转出到后备且没有转舍记录的猪 (一般只有商品猪转后备) "); */
        sqlCon.addMainSql(" UNION ALL ");
        sqlCon.addMainSql(" SELECT T1.HOUSE_ID,T1.TOWORK_DATE CHANGE_HOUSE_DATE,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS IN (12,13,14),1,0)) CF_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS IN (1,3),1,0)) HB_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE = 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS IN (1,3),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql("  SUM(IF(T2.PIG_TYPE <> 3 AND T1.PIG_CLASS = 16 AND T6.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        // T2 --> T1
        sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T1  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T2.ROW_ID = T1.CHANGE_PIG_CLASS_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.PIG_TYPE <> T2.PIG_TYPE ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3  ");
        sqlCon.addMainSql(
                " ON T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.PIG_ID AND T1.BILL_ID = T3.BILL_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1'  ");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T5  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1' AND T5.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T6  ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T1.HOUSE_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T3.ROW_ID IS NULL  ");
        // 只查转后备的猪
        sqlCon.addMainSql(" AND T1.PIG_CLASS IN (1,3) ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(startDate, " AND T1.TOWORK_DATE BETWEEN ?  ");
        sqlCon.addCondition(endDate, " AND ?  ");
        sqlCon.addMainSql(" GROUP BY T1.TOWORK_DATE,T1.HOUSE_ID ");

        sqlCon.addMainSql(" UNION ALL ");
        /* 场间转出 包含转后备-转产房-转保育-转育肥-转培育 */
        sqlCon.addMainSql(" SELECT T1.HOUSE_ID,T1.LEAVE_DATE CHANGE_HOUSE_DATE, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS IN (12,13,14),1,0)) CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 15,1,0)) BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'Y',1,0)) PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE = 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'N',1,0)) YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (12,13,14),1,0)) HB_CF_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 15,1,0)) HB_BY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'Y',1,0)) HB_PY_QTY ,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS IN (1,3,4),1,0)) HB_HB_QTY,  ");
        sqlCon.addMainSql(" SUM(IF(T1.PIG_TYPE <> 3 AND T0.ENTER_PIG_CLASS = 16 AND T8.IS_FOSTER_HOUSE = 'N',1,0)) HB_YF_QTY  ");
        sqlCon.addMainSql(" FROM PP_L_PIG T0   ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T1  ");
        sqlCon.addMainSql(
                " ON T0.RELATION_PIG_ID = T1.PIG_ID AND T0.FARM_ID <> T1.FARM_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.SAP_SALE_TYPE = 3 AND T0.ORIGIN <> 3  ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2  ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T3   ");
        sqlCon.addMainSql(
                " ON T2.SUP_COMPANY_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T2.ACCOUNT_COMPANY_CLASS NOT IN (2,4)   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T5   ");
        sqlCon.addMainSql(" ON T1.FARM_ID = T5.ROW_ID AND T5.DELETED_FLAG = '0' AND T5.STATUS = '1'   ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T6   ");
        sqlCon.addMainSql(
                " ON T5.SUP_COMPANY_ID = T6.ROW_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' AND T5.ACCOUNT_COMPANY_CLASS NOT IN (2,4)  ");
        sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T7 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T7.FARM_ID AND T0.ROW_ID = T7.PIG_ID AND T0.BILL_ID = T7.BILL_ID AND T7.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE T8 ");
        sqlCon.addMainSql(" ON T0.FARM_ID = T8.FARM_ID AND T7.HOUSE_ID = T8.ROW_ID AND T8.DELETED_FLAG = '0' AND T8.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  ");
        sqlCon.addMainSql(" AND IFNULL(T3.COMPANY_CODE,T2.COMPANY_CODE) = IFNULL(T6.COMPANY_CODE,T5.COMPANY_CODE)  ");
        // // 转状态 - 转出
        // sqlCon.addMainSql(" AND T0.ENTER_PIG_CLASS <> T1.PIG_CLASS ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?  ");
        sqlCon.addCondition(startDate, " AND T1.LEAVE_DATE BETWEEN ? ");
        sqlCon.addCondition(endDate, " AND ? ");
        sqlCon.addMainSql(" GROUP BY T1.HOUSE_ID,T1.LEAVE_DATE  ");
        sqlCon.addMainSql(" ) TT GROUP BY TT.HOUSE_ID,TT.CHANGE_HOUSE_DATE ");

        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infosOutToHouseChangeClassQTY = paramMapper.getObjectInfos(sqlMap);
        return infosOutToHouseChangeClassQTY;
    }

    @Override
    public void searchHanaLogSign(String endDate) throws Exception {

        Map<String, String> map = new HashMap<>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT * FROM SYS_HANA_INSERT_LOG ");
        sqlCon.addMainSql(" WHERE ");
        sqlCon.addCondition(getFarmId(), "  FARM_ID = ? ");
        sqlCon.addCondition(endDate, " AND TO_SAP_DATE = ? ");
        // 限制延时最大天数
        // sqlCon.addCondition(TimeUtil.getSysDate(), " AND TIMESTAMPDIFF(DAY,TO_SAP_DATE,?) ");
        // sqlCon.addCondition(getFarmId(), " BETWEEN 0 AND _getSettingValueByCode(?,'SAP_MAX_DELAY') ");
        sqlCon.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1 ");
        map.put("sql", sqlCon.getCondition());
        List<SysHanaInsertLogModel> info = sysHanaInsertLogMapper.operSql(map);
        for (SysHanaInsertLogModel inf : info) {
            String toSapAgin = inf.getToSapAgin();
            if ("N".equals(toSapAgin)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "上传设置项为[ N ],不能上传。。。");
            } else if ("Y".equals(toSapAgin)) {
                // 关账开关为关闭状态时,不做时间限制验证
                String settingValueByCodeSapOffClosing = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SAP_OFF_CLOSING");
                if ("OFF".equals(settingValueByCodeSapOffClosing)) {
                    // 移除session缓存
                    removeSessionAttribute();
                    return;
                }

                String settingValueByCode = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SAP_MAX_DELAY");
                String dateAddDay = TimeUtil.dateAddDay(endDate, Integer.valueOf(settingValueByCode));
                long compareDate = TimeUtil.compareDate(dateAddDay, TimeUtil.getSysDate(), Calendar.DATE);
                if (compareDate < 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "超出上传时间范围[" + endDate + "]~[" + dateAddDay + "],请联系管理员处理。。。");
                }
            } else {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "查询上传记录异常。。。");
            }
        }
        // 移除session缓存
        removeSessionAttribute();
    }

    @Override
    public Object searchSendToHanaLogtDetail(Integer tableCode, Long rowId) {
        SqlCon condition = new SqlCon();
        Object list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        Map<String, Object> mapList = new HashMap<>();
        switch (tableCode) {
        case 9997:
            condition = new SqlCon();
            condition.addMainSql(
                    " SELECT T1.row_id,T1.mtc_BranchID,T1.mtc_DeptID,T1.FARM_ID mtc_FarmId,T3.COMPANY_NAME mtc_FarmName,T1.mtc_Unit,t1.HOUSE_ID mtc_HouseId,T2.HOUSE_NAME mtc_HouseName,T1.mtc_PigType,T1.mtc_Period,T1.mtc_DocDate,T1.mtc_BegQty,T1.mtc_InQty,T1.mtc_PurQty,T1.mtc_InQty_Inner,T1.mtc_InQty_Normal,T1.mtc_OutQty_Inner,T1.mtc_OutQty_Normal,T1.mtc_TransToCF,T1.mtc_TransToBY,T1.mtc_TransToPY,T1.mtc_TransToHB,T1.mtc_TransToYF,T1.mtc_DieQty,T1.mtc_KillQty,T1.mtc_TransToSC,T1.mtc_SalesQty_Inner,T1.mtc_SalesQty_Normar,T1.mtc_AdjQty,T1.mtc_EndQty,T1.mtc_DaySum,T1.mtc_Weight  ");
            condition.addMainSql(" FROM SAP_MTC_PGONHD T1 ");
            condition.addMainSql(" LEFT JOIN PP_O_HOUSE T2 ");
            condition.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN HR_M_COMPANY T3 ");
            condition.addMainSql(" ON T1.FARM_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' ");
            condition.addConditionWithNull(getFarmId(), "WHERE T1.FARM_ID = ? ");
            condition.addConditionWithNull(rowId, " AND T1.HANA_LOG_ID = ? ");
            condition.addMainSql(" AND t1.MTC_PigType = 'H' ");
            map.put("sql", condition.getCondition());
            list = paramMapper.getObjectInfos(map);
            mapList.put("HB", list);

            condition = new SqlCon();
            condition.addMainSql(
                    " SELECT T1.row_id,T1.mtc_BranchID,T1.mtc_DeptID,T1.FARM_ID mtc_FarmId,T3.COMPANY_NAME mtc_FarmName,T1.mtc_Unit,t1.HOUSE_ID mtc_HouseId,T2.HOUSE_NAME mtc_HouseName,T1.mtc_PigType,T1.mtc_Period,T1.mtc_DocDate,T1.mtc_BegQty,T1.mtc_InQty,T1.mtc_PurQty,T1.mtc_InQty_Inner,T1.mtc_InQty_Normal,T1.mtc_OutQty_Inner,T1.mtc_OutQty_Normal,T1.mtc_TransToCF,T1.mtc_TransToBY,T1.mtc_TransToPY,T1.mtc_TransToHB,T1.mtc_TransToYF,T1.mtc_DieQty,T1.mtc_KillQty,T1.mtc_TransToSC,T1.mtc_SalesQty_Inner,T1.mtc_SalesQty_Normar,T1.mtc_AdjQty,T1.mtc_EndQty,T1.mtc_DaySum,T1.mtc_Weight  ");
            condition.addMainSql(" FROM SAP_MTC_PGONHD T1 ");
            condition.addMainSql(" LEFT JOIN PP_O_HOUSE T2 ");
            condition.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN HR_M_COMPANY T3 ");
            condition.addMainSql(" ON T1.FARM_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' ");
            condition.addConditionWithNull(getFarmId(), "WHERE T1.FARM_ID = ? ");
            condition.addConditionWithNull(rowId, " AND T1.HANA_LOG_ID = ? ");
            condition.addMainSql(" AND t1.MTC_PigType = 'S' ");
            // list = getList(sapMtcPgonhdMapper, condition);
            map.put("sql", condition.getCondition());
            list = paramMapper.getObjectInfos(map);
            mapList.put("SP", list);

            if (mapList.size() > 0) {
                return mapList;
            }
            break;
        case 9998:
            condition = new SqlCon();
            condition.addMainSql(
                    " SELECT T1.row_Id,T1.mtc_BranchID,T1.mtc_DeptID, T1.FARM_ID mtc_FarmId,T3.COMPANY_NAME mtc_FarmName,T1.mtc_Unit,T1.HOUSE_ID mtc_HouseId,t2.HOUSE_NAME mtc_HouseName,T1.mtc_PigType,T1.mtc_Period,T1.mtc_DocDate,T1.mtc_TransType,T1.mtc_RelaDeptID,T1.FARM_ID_RELA mtc_RelaDeptID_FarmId,t4.COMPANY_NAME mtc_RelaDeptID_FarmName,T1.mtc_RelaUnit,T1.HOUSE_ID_RELA mtc_RelaUnit_HouseId,T5.HOUSE_NAME mtc_RelaUnit_HouseName,T1.mtc_InQty,T1.mtc_InWeight,T1.mtc_OutQty,T1.mtc_OutWeight ");
            condition.addMainSql(" FROM SAP_MTC_PGTSDTL T1 ");
            condition.addMainSql(" LEFT JOIN PP_O_HOUSE T2 ");
            condition.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN HR_M_COMPANY T3 ");
            condition.addMainSql(" ON T1.FARM_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN HR_M_COMPANY T4 ");
            condition.addMainSql(" ON T1.MTC_RELADEPTID = T4.ROW_ID AND T4.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN PP_O_HOUSE T5 ");
            condition.addMainSql(" ON T1.HOUSE_ID_RELA = T5.ROW_ID AND T5.DELETED_FLAG = '0' ");
            condition.addConditionWithNull(getFarmId(), "WHERE T1.FARM_ID = ? ");
            condition.addConditionWithNull(rowId, " AND T1.HANA_LOG_ID = ? ");
            map.put("sql", condition.getCondition());
            list = paramMapper.getObjectInfos(map);
            // list = getList(sapMtcPgtsdtlMapper, condition);
            break;
        case 9999:
            condition = new SqlCon();
            condition.addMainSql(
                    " SELECT T1.row_Id,T1.mtc_BranchID,T1.mtc_DeptID, T1.FARM_ID mtc_FarmId,T3.COMPANY_NAME mtc_FarmName,T1.mtc_Unit,T1.HOUSE_ID mtc_HouseId,t2.HOUSE_NAME mtc_HouseName,T1.mtc_Period,T1.mtc_PregStep_1,T1.mtc_PregStep_2,T1.mtc_PregStep_3 ");
            condition.addMainSql(" FROM SAP_MTC_PGSTAT T1 ");
            condition.addMainSql(" LEFT JOIN PP_O_HOUSE T2 ");
            condition.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN HR_M_COMPANY T3 ");
            condition.addMainSql(" ON T1.FARM_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' ");
            condition.addConditionWithNull(getFarmId(), "WHERE T1.FARM_ID = ? ");
            condition.addConditionWithNull(rowId, " AND T1.HANA_LOG_ID = ? ");
            map.put("sql", condition.getCondition());
            list = paramMapper.getObjectInfos(map);
            // list = getList(sapMtcPgstatMapper, condition);
            break;
        case 3030:
            condition = new SqlCon();
            condition.addMainSql(
                    " SELECT T1.row_Id,T1.mtc_Period,T1.mtc_BranchID,T1.mtc_ItemCode,T1.mtc_ItemName,T1.mtc_DeptID,T1.FARM_ID mtc_FarmId,T3.COMPANY_NAME mtc_FarmName,T1.mtc_Unit,T1.HOUSE_ID mtc_HouseId,T2.HOUSE_NAME mtc_HouseName,T1.mtc_Parity,T1.mtc_PregStatus,T1.mtc_PregDate,T1.mtc_DaySum,T1.mtc_PregLevel ");
            condition.addMainSql(" FROM SAP_MTC_PGSTAT_DTL T1 ");
            condition.addMainSql(" LEFT JOIN PP_O_HOUSE T2 ");
            condition.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' ");
            condition.addMainSql(" LEFT JOIN HR_M_COMPANY T3 ");
            condition.addMainSql(" ON T1.FARM_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0' ");
            condition.addConditionWithNull(getFarmId(), "WHERE T1.FARM_ID = ? ");
            condition.addConditionWithNull(rowId, " AND T1.HANA_LOG_ID = ? ");
            map.put("sql", condition.getCondition());
            list = paramMapper.getObjectInfos(map);
            // list = getList(sapMtcPgstatDtlMapper, condition);
            break;
        case 10000: // 汇总
            condition = new SqlCon();
            condition.addMainSql(
                    " SELECT T1.ROW_ID rowId,T1.SORT_NBR,T1.STATUS,T1.DELETED_FLAG,T1.ORIGIN_FLAG,T1.ORIGIN_APP,T1.NOTES notes,T1.COMPANY_ID companyId,T1.FARM_ID farmId,T1.HANA_LOG_ID hanaLogId,T1.CREATE_TIME createTime,T1.LINE_NUMBER lineNumber,T1.TYPE,T1.BEG_QTY begQty,T1.BEG_MONEY begMoney,T1.ADD_QTY addQty,T1.ADD_MONEY addMoney,T1.REDUCE_QTY reduceQty,T1.REDUCE_MONEY reduceMoney,T1.END_QTY endQty,T1.END_MONEY endMoney,IFNULL(T1.OTHER,'') other,T2.CODE_NAME typeName ");
            condition.addMainSql(" FROM SAP_MTC_SUMMARY T1 ");
            condition.addMainSql(" LEFT JOIN CD_L_CODE_LIST T2 ");
            condition.addMainSql(" ON T1.TYPE = T2.CODE_VALUE AND T2.TYPE_CODE = 'SUMMERY_TYPE' AND T2.DELETED_FLAG = '0' ");
            condition.addConditionWithNull(getFarmId(), "WHERE T1.FARM_ID = ? ");
            condition.addConditionWithNull(rowId, " AND T1.HANA_LOG_ID = ? ");
            map.put("sql", condition.getCondition());
            list = paramMapper.getObjectInfos(map);
            // list = getList(sapMtcSummaryMapper, condition);
            break;
        default:
            break;
        }
        return list;
    }
}
