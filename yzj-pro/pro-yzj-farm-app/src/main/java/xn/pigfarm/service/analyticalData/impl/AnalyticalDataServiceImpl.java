package xn.pigfarm.service.analyticalData.impl;

import static java.util.Calendar.YEAR;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.bigDeciml.BigDecimalUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.MonthPerformanceMapper;
import xn.pigfarm.mapper.backend.SysReportProductionDailyMapper;
import xn.pigfarm.mapper.backend.WeekMapper;
import xn.pigfarm.model.backend.MonthPerformanceModel;
import xn.pigfarm.model.backend.WeekModel;
import xn.pigfarm.service.analyticalData.IAnalyticalDataService;
import xn.pigfarm.util.constants.AnalyticalDataConstants;
import xn.pigfarm.util.constants.PigConstants;
import xn.pigfarm.util.constants.SupplyChianConstants;

@Service("analyticalDataService")
public class AnalyticalDataServiceImpl extends BaseServiceImpl implements IAnalyticalDataService {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private WeekMapper weekMapper;

    @Autowired
    private MonthPerformanceMapper monthPerformanceMapper;

    @Autowired
    private SysReportProductionDailyMapper sysReportProductionDailyMapper;

    // 获得companyMark,minDate,maxDate
    private Map<String, String> getCommonInfo(Map<String, Object> inputMap) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        // 传入的分类和传入的COMPANY_MARK
        map.put(AnalyticalDataConstants.ORIGIN_TYPE, Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE));
        map.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, Maps.getString(inputMap, "companyMark"));

        // 获取最大最小日期
        Map<String, String> dateMap = getDateByParam(Maps.getString(inputMap, "dateType"), Maps.getString(inputMap, "dateRatio"));

        map.putAll(dateMap);

        return map;
    }

    private Map<String, String> getDateByParam(String dateType, String dateRatio) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String minDate = null;
        String maxDate = null;
        // 近一年
        if (AnalyticalDataConstants.DATE_TYPE_YEAR.equals(dateType)) {
            minDate = dateRatio + "-1-1";
            maxDate = dateRatio + "-12-31";
            // 判断是否使用系统日期
            long compareDate = TimeUtil.compareDate(maxDate, TimeUtil.getSysDate(TimeUtil.DATE_FORMAT), Calendar.getInstance().DATE);
            if (compareDate > 0) {
                maxDate = TimeUtil.getSysDate(TimeUtil.DATE_FORMAT);
            }

        } else if (AnalyticalDataConstants.DATE_TYPE_MONTH.equals(dateType)) {
            // 某月

            // 拼接时间
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(YEAR);
            calendar.set(year, Integer.valueOf(dateRatio) - 1, 1);
            // 设置时间
            minDate = TimeUtil.format(calendar.getTime(), TimeUtil.DATE_FORMAT);
            maxDate = TimeUtil.getLastDateOfMonth(minDate);
            // 判断
            long compareDate = TimeUtil.compareDate(maxDate, TimeUtil.getSysDate(TimeUtil.DATE_FORMAT), calendar.DATE);
            if (compareDate > 0) {
                maxDate = TimeUtil.getSysDate(TimeUtil.DATE_FORMAT);
            }

        } else if (AnalyticalDataConstants.DATE_TYPE_WEEK.equals(dateType)) {
            // 某一周
            HashMap<String, String> map = new HashMap<String, String>();
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(getFarmId(), "AND FARM_ID = ? ");
            sqlCon.addCondition(Integer.valueOf(dateRatio), "AND WEEK = ? ");
            sqlCon.addCondition(TimeUtil.getCurrYearFirst(), "AND START_DATE >= ? ");
            sqlCon.addCondition(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), "AND START_DATE <=  ? ");

            map.put("condition", sqlCon.getCondition());
            List<WeekModel> searchListByCon = weekMapper.searchListByCon(map);
            minDate = String.valueOf(searchListByCon.get(0).get("startDate"));
            maxDate = String.valueOf(searchListByCon.get(0).get("endDate"));
            // 判断
            Calendar calendar = Calendar.getInstance();
            long compareDate = TimeUtil.compareDate(maxDate, TimeUtil.getSysDate(TimeUtil.DATE_FORMAT), calendar.DATE);
            if (compareDate > 0) {
                maxDate = TimeUtil.getSysDate(TimeUtil.DATE_FORMAT);
            }
        } else if (AnalyticalDataConstants.DATE_TYPE_DAY.equals(dateType)) {
            minDate = dateRatio;
            maxDate = dateRatio;
        }

        hashMap.put(AnalyticalDataConstants.MIN_DATE, minDate);
        hashMap.put(AnalyticalDataConstants.MAX_DATE, maxDate);
        return hashMap;
    }

    private List<Map<String, Object>> getList(SqlCon sqlCon) {

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    // 种猪死亡数量
    @Override
    public List<Map<String, Object>> searchBoarAndSowDieTotalToList(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = this.getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT COUNT(1) AS dieTotal FROM PP_L_PIG T1");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T1.FARM_ID = T2.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T1.PIG_TYPE IN (1,2)");
        sqlCon.addMainSql(" AND T1.PIG_CLASS = 23 ");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.LEAVE_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.LEAVE_DATE <= ?");
        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        List<Map<String, Object>> list = this.getList(sqlCon);

        return list;
    }

    // 种猪淘汰数量
    @Override
    public List<Map<String, Object>> searchBoarAndSowObsoleteTotalToList(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = this.getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IFNULL(SUM(T1.SALE_NUM),0) obsoleteTotal ");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ON T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2 ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.SALE_TYPE = 3 AND T0.SAP_SALE_TYPE IN('1','2') ");
        sqlCon.addMainSql(" AND T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.SALE_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.SALE_DATE <= ?");
        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        List<Map<String, Object>> list = this.getList(sqlCon);

        return list;
    }

    /** 死淘率 **/

    // 产房死淘率
    @Override
    public List<Map<String, Object>> searchDiePercentOfDelivery(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        inputMap.put("houseType", PigConstants.HOUSE_CLASS_DELIVERY);
        inputMap.put("leaveType", PigConstants.PIG_SON_DIE);

        return searchDiePercent(inputMap);
    }

    // 保育死淘率
    @Override
    public List<Map<String, Object>> searchDiePercentOfChlidCare(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        inputMap.put("houseType", PigConstants.HOUSE_CLASS_CAREPIG);
        inputMap.put("leaveType", PigConstants.PIG_BOAR_DID);

        return searchDiePercent(inputMap);
    }

    // 育肥死淘率
    @Override
    public List<Map<String, Object>> searchDiePercentOfToFat(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        inputMap.put("houseType", PigConstants.HOUSE_CLASS_FATTEN);
        inputMap.put("leaveType", PigConstants.PIG_BOAR_DID);

        return searchDiePercent(inputMap);
    }

    // 全程死淘率
    @Override
    public List<Map<String, Object>> searchDiePercentOfAll(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        String houseType = PigConstants.HOUSE_CLASS_DELIVERY + "," + PigConstants.HOUSE_CLASS_CAREPIG + "," + PigConstants.HOUSE_CLASS_FATTEN;
        String leaveType = PigConstants.PIG_SON_DIE + "," + PigConstants.PIG_BOAR_DID;
        inputMap.put("houseType", houseType);
        inputMap.put("leaveType", leaveType);

        return searchDiePercent(inputMap);
    }

    // 全程死亡头数和产活仔
    @Override
    public List<Map<String, Object>> searchDieNumOfAll(Map<String, Object> inputMap) throws Exception {
        String leaveType = PigConstants.PIG_SON_DIE + "," + PigConstants.PIG_BOAR_DID;
        inputMap.put("leaveType", leaveType);

        return searchDieTotalNum(inputMap);
    }

    private List<Map<String, Object>> searchDiePercent(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = getCommonInfo(inputMap);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT IFNULL(t1.dieQty,0) dieQty,IFNULL(t2.healthyNum,0) healthyNum,");
        sqlCon.addMainSql(" CONCAT(IFNULL(ROUND((t1.dieQty/t2.healthyNum)*100,2),0),'%') dieRate");
        sqlCon.addMainSql(" FROM");
        sqlCon.addMainSql(" (SELECT COUNT(1) dieQty FROM pp_l_bill_leave l");
        sqlCon.addMainSql(" INNER JOIN pp_o_house h");
        sqlCon.addMainSql(" ON l.HOUSE_ID = h.ROW_ID AND h.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON l.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE l.DELETED_FLAG = 0");
        sqlCon.addCondition(Maps.getString(inputMap, "houseType"), " AND h.HOUSE_TYPE IN ?", false, true);

        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( c.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" AND l.PIG_CLASS IN (12,IF((SELECT A.SETTING_VALUE FROM CD_M_SETTING A");
        sqlCon.addMainSql(" WHERE A.SETTING_CODE = 'RZBSCL' AND A.FARM_ID= c.ROW_ID ) = 'ON',12,13),14,15,16)");
        sqlCon.addCondition(Maps.getString(inputMap, "startDate"), " AND l.LEAVE_DATE BETWEEN ?");
        sqlCon.addCondition(Maps.getString(inputMap, "endDate"), " AND ?");
        sqlCon.addCondition(Maps.getString(inputMap, "leaveType"), " AND l.LEAVE_TYPE IN ?) AS T1,", false, true);
        sqlCon.addMainSql("(SELECT SUM(IF(d.HEALTHY_NUM IS NULL,0,d.HEALTHY_NUM)) +");
        sqlCon.addMainSql(" IF((SELECT A.SETTING_VALUE FROM CD_M_SETTING A");
        sqlCon.addMainSql(" WHERE A.SETTING_CODE = 'RZBSCL' AND A.FARM_ID = c.ROW_ID ) = 'ON',0,");
        sqlCon.addMainSql(" SUM(IF(d.WEAK_NUM IS NULL,0,d.WEAK_NUM))) healthyNum");
        sqlCon.addMainSql(" FROM pp_l_bill_delivery d");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON d.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE d.DELETED_FLAG = 0");

        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).indexOf("1,2,3,5,") != -1) {
                sqlCon.addMainSql(" AND ( c.COMPANY_MARK = '1,2,3,5'  OR c.COMPANY_MARK LIKE '1,2,3,5,%') ");
            } else {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( c.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ? )");
            }

        }
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND d.DELIVERY_DATE BETWEEN ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND ?) AS T2");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        if (list.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死淘率error！");
        }
        return list;
    }

    private List<Map<String, Object>> searchDieTotalNum(Map<String, Object> inputMap) throws Exception {
        Map<String, String> commonInfo = getCommonInfo(inputMap);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT COUNT(1) dieTotalNum FROM pp_l_bill_leave l");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c ON l.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE l.DELETED_FLAG = 0");
        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
        }

        sqlCon.addMainSql(" AND l.PIG_CLASS IN (12,IF((SELECT A.SETTING_VALUE FROM CD_M_SETTING A");
        sqlCon.addMainSql(" WHERE A.SETTING_CODE = 'RZBSCL' AND A.FARM_ID= c.ROW_ID ) = 'ON',12,13),14,15,16)");
        sqlCon.addCondition(Maps.getString(commonInfo, AnalyticalDataConstants.MIN_DATE), " AND l.LEAVE_DATE BETWEEN ?");
        sqlCon.addCondition(Maps.getString(commonInfo, AnalyticalDataConstants.MAX_DATE), " AND ?");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, "leaveType"), " AND l.LEAVE_TYPE IN ?", false, true);

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(sqlMap);
    }

    // 存栏分析
    @Override
    public Map<String, String> searchPopulationAnalysis(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 1, 1, 0)) PIG_HBGZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 2, 1, 0)) PIG_SCGZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS IN (3,4), 1, 0)) PIG_HBMZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS IN (5, 6, 7, 8,11),1,0)) PIG_DP_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 9,1,0)) PIG_YP_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 10,1,0)) PIG_BR_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS IN (12, 13) AND B0.SEED_FLAG = 'N', 1, 0)) PIG_BRZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 14 AND B0.SEED_FLAG = 'N', 1, 0)) PIG_DNZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 15 AND B0.SEED_FLAG = 'N', 1, 0)) PIG_BY_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 16 AND B0.SEED_FLAG = 'N', 1, 0)) PIG_YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.SEED_FLAG = 'Y' AND B0.SEX = '1', 1, 0)) PIG_LZG_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.SEED_FLAG = 'Y' AND B0.SEX = '2', 1, 0)) PIG_LZM_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 24 AND B0.PIG_TYPE = 1, 1, 0)) PIG_TTG_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 24 AND B0.PIG_TYPE = 2, 1, 0)) PIG_TTM_QTY, ");
        sqlCon.addMainSql(" COUNT(B0.PIG_CLASS) PIG_ALL_QTY ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql(" IF(T2.PIG_CLASS IS NULL,T0.PIG_CLASS,T2.PIG_CLASS) PIG_CLASS, ");
        sqlCon.addMainSql(" T0.SEED_FLAG, ");
        sqlCon.addMainSql(" T0.SEX, ");
        sqlCon.addMainSql(" T0.PIG_TYPE  ");
        sqlCon.addMainSql(" FROM pp_l_pig T0 ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.STATUS = '1' AND T1.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" LEFT JOIN ( ");
        sqlCon.addMainSql(" SELECT A0.* ");
        sqlCon.addMainSql(" FROM pp_l_bill_towork A0 ");
        sqlCon.addMainSql(" WHERE A0.STATUS = '1'  AND A0.DELETED_FLAG = '0' ");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A0.TOWORK_DATE <= ? ");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE),
                " AND (A0.TOWORK_DATE_OUT > ? OR A0.TOWORK_DATE_OUT IS NULL) ");
        sqlCon.addMainSql(" ) T2 ");
        sqlCon.addMainSql(" ON T2.PIG_ID = T0.ROW_ID ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND (T0.ORIGIN <> 3 OR T0.ORIGIN IS NULL)");

        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
        }

        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.ENTER_DATE <= ? ");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND (T0.LEAVE_DATE > ? OR T0.LEAVE_DATE IS NULL) ");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " ORDER BY T0.ROW_ID ");
        sqlCon.addMainSql(" )B0 ");

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, String>> infos = paramMapper.getInfos(map);

        return infos.get(0);
    }

    @Override
    public Object searchPopulationAnalysis2(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T5.ROW_ID FARM_ID, T5.SORT_NAME AS FARM_NAME, T5.COMPANY_MARK AS companyMark,");
        sqlCon.addMainSql(" IF(COUNT(1) = 0,0,SUM(IF(T0.PIG_CLASS IN (2), 1, 0))) GZ_PIG,");
        sqlCon.addMainSql(" IF(COUNT(1) = 0,0,SUM(IF(T0.PIG_CLASS IN (5,6,7,8,9,10,11), 1, 0))) MZ_PIG,");
        sqlCon.addMainSql(" IF(COUNT(1)=0,0,SUM(IF(T0.PIG_CLASS IN (12,13,14,15,16), 1, 0))) RZ_PIG,");
        sqlCon.addMainSql(" IF(COUNT(1) = 0,0,SUM(IF(T0.PIG_CLASS IN (1,3,4), 1, 0))) HB_PIG,");
        sqlCon.addMainSql(" COUNT(1) ALL_PIG ");
        sqlCon.addMainSql(" FROM pp_l_bill_towork T0 ");
        sqlCon.addMainSql(
                " LEFT JOIN pp_l_pig T3 ON T0.FARM_ID=T3.FARM_ID AND T0.DELETED_FLAG = T3.DELETED_FLAG AND T0.STATUS = T3.STATUS AND T0.PIG_ID = T3.ROW_ID ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T4  ON (T4.DELETED_FLAG = '0' AND T4.STATUS = '1' AND T0.FARM_ID = T4.ROW_ID) ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T5 ON (T5.DELETED_FLAG = '0' AND T5.STATUS = '1' ");
        sqlCon.addMainSql(" AND (T4.COMPANY_MARK = T5.COMPANY_MARK OR T4.COMPANY_MARK LIKE CONCAT(T5.COMPANY_MARK, ',%')))  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), "AND DATE(T0.TOWORK_DATE) <= ? ");
        sqlCon.addMainSql(" AND (T3.ORIGIN <> 3 OR T3.ORIGIN IS NULL) ");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE),
                " AND IFNULL(DATE(T0.TOWORK_DATE_OUT),IFNULL(DATE(T3.LEAVE_DATE),DATE(DATE_ADD(NOW(),INTERVAL 1 DAY)))) > ?");

        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T5.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T5.COMPANY_MARK LIKE ? )");
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                    if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T5.COMPANY_MARK = ? ");
                        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%",
                                " OR T5.COMPANY_MARK LIKE ? )");
                    }
                }
            } else {
                if (Maps.isEmpty(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK)) {
                    if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
                        sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T5.COMPANY_MARK LIKE ?");
                    } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
                        sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T5.COMPANY_MARK NOT LIKE ?");
                        sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T5.COMPANY_MARK LIKE ?");
                        sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T5.ROW_ID NOT IN ?", false, true);
                    }
                    sqlCon.addCondition(AnalyticalDataConstants.ACCOUNT_COMPANY_CLASS_COMPANY, " AND T5.ACCOUNT_COMPANY_CLASS IN ?", false, true);
                    // sqlCon.addCondition(AnalyticalDataConstants.ACCOUNT_COMPANY_CLASS_FARM, " AND T5.ACCOUNT_COMPANY_CLASS IN ?", false, true);
                } else {
                    // sqlCon.addCondition(Maps.getString(commonInfo, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T5.COMPANY_MARK = ? ");
                    // sqlCon.addCondition(Maps.getString(commonInfo, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T5.COMPANY_MARK LIKE ?
                    // )");
                    sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " AND T5.COMPANY_MARK LIKE ?");
                }
            }
        }
        sqlCon.addMainSql(" GROUP BY T5.ROW_ID");
        sqlCon.addMainSql(" ORDER BY T5.SORT_NBR");

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, String>> infos = paramMapper.getInfos(map);

        return infos;
    }

    // 各场死亡率
    @Override
    public List<Map<String, Object>> searchEachPigfarmDiePercentOfAll(Map<String, Object> inputMap) throws Exception {
        // Map<String, String> commonInfo = this.getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T2.ROW_ID AS farmId, T2.SORT_NAME AS farmSortName, T2.COMPANY_MARK AS companyMark,");
        sqlCon.addMainSql(" IFNULL(T2.deliveryDieChildPigQty,0) AS deliveryDieChildPigQty,");
        sqlCon.addMainSql(" IFNULL(T2.toCareDieChildPigQty,0) AS toCareDieChildPigQty,");
        sqlCon.addMainSql(" IFNULL(T2.toFatDieChildPigQty,0) AS toFatDieChildPigQty,");
        sqlCon.addMainSql(" IFNULL(T2.birthChildPigQty,0) AS birthChildPigQty,");
        sqlCon.addMainSql(" CONCAT(ROUND(IFNULL(deliveryDieChildPigQty/birthChildPigQty,0)*100,2),'%') AS deliveryDieChildPigPercent,");
        sqlCon.addMainSql(" CONCAT(ROUND(IFNULL(toCareDieChildPigQty/birthChildPigQty,0)*100,2),'%') AS toCareDieChildPigPercent,");
        sqlCon.addMainSql(" CONCAT(ROUND(IFNULL(toFatDieChildPigQty/birthChildPigQty,0)*100,2),'%') AS toFatDieChildPigPercent,");
        sqlCon.addMainSql(" CONCAT(ROUND(IFNULL((deliveryDieChildPigQty + toCareDieChildPigQty");
        sqlCon.addMainSql(" + toFatDieChildPigQty)/birthChildPigQty,0)*100,2),'%') AS allDieChildPigPercent");
        sqlCon.addMainSql(" FROM");
        sqlCon.addMainSql(" (SELECT T1.ROW_ID, T1.SORT_NAME, T1.COMPANY_MARK, ");

        sqlCon.addMainSql(" (SELECT COUNT(1) AS dieChildPigQty FROM PP_L_BILL_LEAVE A LEFT JOIN PP_O_HOUSE B");
        sqlCon.addMainSql(" ON ( B.DELETED_FLAG = '0' AND B.STATUS = '1' AND A.FARM_ID = B.FARM_ID AND A.HOUSE_ID = B.ROW_ID ) ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY C");
        sqlCon.addMainSql(" ON ( C.DELETED_FLAG = '0' AND A.FARM_ID = C.ROW_ID ) ");
        sqlCon.addMainSql(" INNER JOIN PP_M_BILL D");
        sqlCon.addMainSql(" ON ( C.DELETED_FLAG = '0' AND A.BILL_ID = D.ROW_ID AND D.EVENT_CODE <> 'DELIVERY' ) ");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0' AND A.STATUS = '1' ");
        sqlCon.addMainSql(" AND A.PIG_CLASS IN (12,13,14)");
        sqlCon.addMainSql(" AND A.LEAVE_TYPE IN (3,4)");
        sqlCon.addMainSql(" AND B.HOUSE_TYPE = 6 ");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND A.LEAVE_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A.LEAVE_DATE <= ?");

        sqlCon.addMainSql(" AND (C.COMPANY_MARK = T1.COMPANY_MARK OR C.COMPANY_MARK LIKE CONCAT(T1.COMPANY_MARK,',%'))");

        sqlCon.addMainSql(" ) AS deliveryDieChildPigQty,");

        sqlCon.addMainSql(" (SELECT COUNT(1) AS dieChildPigQty FROM");
        sqlCon.addMainSql(" PP_L_BILL_LEAVE A");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE B");
        sqlCon.addMainSql(" ON ( B.DELETED_FLAG = '0' AND B.STATUS = '1' AND A.FARM_ID = B.FARM_ID AND A.HOUSE_ID = B.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY C");
        sqlCon.addMainSql(" ON (C.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND A.FARM_ID = C.ROW_ID)");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND A.STATUS = '1'");
        sqlCon.addMainSql(" AND A.PIG_CLASS = 15");
        sqlCon.addMainSql(" AND A.LEAVE_TYPE IN (3,4)");
        sqlCon.addMainSql(" AND B.HOUSE_TYPE = 8");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND A.LEAVE_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A.LEAVE_DATE <= ?");

        sqlCon.addMainSql(" AND (C.COMPANY_MARK = T1.COMPANY_MARK OR C.COMPANY_MARK LIKE CONCAT(T1.COMPANY_MARK,',%'))");

        sqlCon.addMainSql(" ) AS toCareDieChildPigQty,");

        sqlCon.addMainSql(" (SELECT COUNT(1) AS dieChildPigQty FROM");
        sqlCon.addMainSql(" PP_L_BILL_LEAVE A");
        sqlCon.addMainSql(" LEFT JOIN PP_O_HOUSE B");
        sqlCon.addMainSql(" ON ( B.DELETED_FLAG = '0' AND B.STATUS = '1' AND A.FARM_ID = B.FARM_ID AND A.HOUSE_ID = B.ROW_ID ) ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY C ");
        sqlCon.addMainSql(" ON ( C.DELETED_FLAG = '0' AND A.FARM_ID = C.ROW_ID )");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND A.STATUS = '1' ");
        sqlCon.addMainSql(" AND A.PIG_CLASS = 16");
        sqlCon.addMainSql(" AND A.LEAVE_TYPE IN (3,4)");
        sqlCon.addMainSql(" AND B.HOUSE_TYPE = 9");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND A.LEAVE_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A.LEAVE_DATE <= ?");

        sqlCon.addMainSql(" AND (C.COMPANY_MARK = T1.COMPANY_MARK OR C.COMPANY_MARK LIKE CONCAT(T1.COMPANY_MARK,',%'))");

        sqlCon.addMainSql(" ) AS toFatDieChildPigQty,");

        sqlCon.addMainSql(" (SELECT SUM(IFNULL(HEALTHY_NUM, 0))");
        sqlCon.addMainSql("  + IF((SELECT SETTING_VALUE FROM CD_M_SETTING ");
        sqlCon.addMainSql(" WHERE SETTING_CODE = 'RZBSCL' ");
        sqlCon.addMainSql(" AND FARM_ID = A.FARM_ID) = 'ON',0,SUM(IF(WEAK_NUM IS NULL, 0, WEAK_NUM)))");
        sqlCon.addMainSql(" FROM PP_L_BILL_DELIVERY A");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY B ");
        sqlCon.addMainSql(" ON ( B.DELETED_FLAG = '0'AND B.ROW_ID = A.FARM_ID ) ");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND A.DELIVERY_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A.DELIVERY_DATE <= ?");
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.INPUT_COMPANY_MARK).indexOf("1,2,3,5,") != -1) {
            sqlCon.addMainSql(" AND (B.COMPANY_MARK = '1,2,3,5' OR B.COMPANY_MARK LIKE CONCAT('1,2,3,5',',%'))");
        } else {
            sqlCon.addMainSql(" AND (B.COMPANY_MARK = T1.COMPANY_MARK OR B.COMPANY_MARK LIKE CONCAT(T1.COMPANY_MARK,',%'))");
        }
        sqlCon.addMainSql(" ) AS birthChildPigQty");

        sqlCon.addMainSql(" FROM");
        sqlCon.addMainSql(" HR_M_COMPANY T1 ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");

        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.ACCOUNT_COMPANY_CLASS_FARM, " AND T1.ACCOUNT_COMPANY_CLASS IN ?", false, true);
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }

        sqlCon.addMainSql(" ) T2");
        return getList(sqlCon);
    }

    @Override
    public List<Map<String, String>> searchStockDetails(Map<String, Object> inputMap) throws Exception {

        String inputCompanyMark = Maps.getString(inputMap, "companyMark");
        long pigType = Maps.getLong(inputMap, "pigType");

        // Map<String, String> DateByParam = getDateByParam(Maps.getString(inputMap, "dateType"), Maps.getString(inputMap, "dateRatio"));
        String maxDate = Maps.getString(inputMap, "endDate");

        SqlCon sqlCon = new SqlCon();

        String sysDate = TimeUtil.getSysDate();
        if (sysDate.equals(maxDate)) {
            sqlCon.addMainSql(
                    "SELECT N0.HOUSE_ID,N0.HOUSE_NAME,N0.PIG_QTY,N0.AVG_DAY_AGE,N1.LENGTH,N1.WIDTH,SUM(N1.LENGTH * N1.WIDTH)/N0.PIG_QTY/1000 DENSITY,N0.HB_G,N0.HB_M ");
            sqlCon.addMainSql("FROM (");
            sqlCon.addMainSql(
                    "SELECT T0.ROW_ID HOUSE_ID,T0.HOUSE_NAME,COUNT(1) PIG_QTY ,AVG(TIMESTAMPDIFF(DAY,T1.BIRTH_DATE,NOW())) AVG_DAY_AGE ,T0.FARM_ID, ");
            sqlCon.addMainSql("IFNULL(SUM(IF(T1.PIG_TYPE = 1,1,0)),0) HB_G,IFNULL(SUM(IF(T1.PIG_TYPE = 2,1,0)),0) HB_M ");
            sqlCon.addMainSql("FROM PP_O_HOUSE T0 ");
            sqlCon.addMainSql("INNER JOIN PP_L_PIG T1 ");
            sqlCon.addMainSql(
                    "ON T0.FARM_ID = T1.FARM_ID AND T0.DELETED_FLAG = T1.DELETED_FLAG AND T0.STATUS = T1.STATUS AND T0.ROW_ID = T1.HOUSE_ID ");
            sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T3");
            sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T0.FARM_ID = T3.ROW_ID)");
            sqlCon.addMainSql("WHERE t0.DELETED_FLAG = 0 AND t0.STATUS = 1 ");
            sqlCon.addMainSql("AND (t1.PIG_CLASS <= 18 OR t1.`PIG_CLASS` = 24 )  AND (T1.ORIGIN <> 3 OR T1.ORIGIN IS NULL) ");
            sqlCon.addCondition(inputCompanyMark, " AND ( T3.COMPANY_MARK = ? ");
            sqlCon.addCondition(inputCompanyMark + ",%", " OR T3.COMPANY_MARK LIKE ? )");
            if (pigType == 4) {
                sqlCon.addMainSql(" AND T1.PIG_CLASS IN ('1','3','4') ");
            } else {
                sqlCon.addConditionWithNull(pigType, "AND T1.PIG_TYPE =  ?  ");
                sqlCon.addMainSql(" AND T1.PIG_CLASS NOT IN ('1','3','4') ");
            }
            sqlCon.addMainSql("GROUP BY t0.ROW_ID ");
            sqlCon.addMainSql(") N0");
            sqlCon.addMainSql(" LEFT JOIN pp_o_pigpen N1 ON N0.HOUSE_ID = N1.HOUSE_ID AND (N1.DELETED_FLAG = '0' AND N1.FARM_ID = N0.FARM_ID)");
            sqlCon.addMainSql(" GROUP BY N0.HOUSE_ID");
            sqlCon.addMainSql(" ORDER BY N0.HOUSE_NAME");
        } else {
            sqlCon.addMainSql("SELECT N0.HOUSE_ID,N0.HOUSE_NAME,N0.PIG_QTY,N0.AVG_DAY_AGE,N1.LENGTH,");
            sqlCon.addMainSql("N1.WIDTH,SUM(N1.LENGTH * N1.WIDTH)/N0.PIG_QTY/1000 DENSITY,N0.HB_G,N0.HB_M ");
            sqlCon.addMainSql("FROM (");
            sqlCon.addMainSql("SELECT IFNULL(T0.HOUSE_ID,T3.HOUSE_ID) HOUSE_ID,T5.HOUSE_NAME,");
            sqlCon.addMainSql("COUNT(1) PIG_QTY,AVG(TIMESTAMPDIFF(DAY,T3.BIRTH_DATE,NOW())) AVG_DAY_AGE ,T0.FARM_ID,  ");
            sqlCon.addMainSql("IFNULL(SUM(IF(T0.PIG_TYPE = 1,1,0)),0) HB_G,IFNULL(SUM(IF(T0.PIG_TYPE = 2,1,0)),0) HB_M ");
            sqlCon.addMainSql("FROM pp_l_bill_towork T0  ");
            sqlCon.addMainSql("LEFT JOIN pp_l_pig T3 ");
            sqlCon.addMainSql("ON T0.FARM_ID=T3.FARM_ID AND T0.DELETED_FLAG = T3.DELETED_FLAG AND T0.STATUS = T3.STATUS AND T0.PIG_ID = T3.ROW_ID  ");
            sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T4 ");
            sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0' AND T4.STATUS = '1' AND T0.FARM_ID = T4.ROW_ID) ");
            sqlCon.addMainSql(" INNER JOIN PP_O_HOUSE T5");
            sqlCon.addMainSql(
                    " ON IFNULL(T0.HOUSE_ID,T3.HOUSE_ID) = T5.ROW_ID AND T0.FARM_ID = T5.FARM_ID AND T5.DELETED_FLAG = T0.DELETED_FLAG AND T5.STATUS = T0.STATUS ");
            sqlCon.addMainSql("WHERE T0.DELETED_FLAG = 0 AND T0.STATUS = 1 ");
            sqlCon.addCondition(inputCompanyMark, " AND ( T4.COMPANY_MARK = ? ");
            sqlCon.addCondition(inputCompanyMark + ",%", " OR T4.COMPANY_MARK LIKE ? )");
            if (pigType == 4) {
                sqlCon.addMainSql(" AND T0.PIG_CLASS IN ('1','3','4') ");
            } else {
                sqlCon.addConditionWithNull(pigType, "AND T3.PIG_TYPE =  ?  ");
                sqlCon.addMainSql(" AND T0.PIG_CLASS NOT IN ('1','3','4') ");
            }
            sqlCon.addMainSql(" AND (T3.ORIGIN <> 3 OR T3.ORIGIN IS NULL)   ");
            sqlCon.addConditionWithNull(maxDate, "AND DATE(T0.TOWORK_DATE) <=  ?  ");
            sqlCon.addConditionWithNull(maxDate,
                    "AND IFNULL(DATE(T0.TOWORK_DATE_OUT),IFNULL(DATE(T3.LEAVE_DATE),DATE(DATE_ADD(NOW(),INTERVAL 1 DAY)))) > ? ");
            sqlCon.addMainSql(" GROUP BY IFNULL(T0.HOUSE_ID,T3.HOUSE_ID) ");
            sqlCon.addMainSql(") N0");
            sqlCon.addMainSql(" LEFT JOIN pp_o_pigpen N1 ON N0.HOUSE_ID = N1.HOUSE_ID AND (N1.DELETED_FLAG = '0' AND N1.FARM_ID = N0.FARM_ID)");
            sqlCon.addMainSql(" GROUP BY N0.HOUSE_ID");
            sqlCon.addMainSql(" ORDER BY N0.HOUSE_NAME");
        }
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, String>> infos = paramMapper.getInfos(map);

        return infos;

    }

    @Override
    public List<Map<String, String>> searchStockDetailsForFarmId(Map<String, Object> inputMap) throws Exception {

        String inputCompanyMark = Maps.getString(inputMap, "companyMark");

        // Map<String, String> DateByParam = getDateByParam(Maps.getString(inputMap, "dateType"), Maps.getString(inputMap, "dateRatio"));
        String maxDate = Maps.getString(inputMap, "endDate");

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 1, 1, 0)) PIG_HBGZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 2, 1, 0)) PIG_SCGZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 3, 1, 0)) PIG_HBMZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS IN (4, 5, 6, 7, 8,11),1,0)) PIG_DP_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 10,1,0)) PIG_YP_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 9,1,0)) PIG_BR_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS IN (12, 13) AND B0.SEED_FLAG = 'N', 1, 0)) PIG_BRZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 14 AND B0.SEED_FLAG = 'N', 1, 0)) PIG_DNZ_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 15 AND B0.SEED_FLAG = 'N', 1, 0)) PIG_BY_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 16 AND B0.SEED_FLAG = 'N', 1, 0)) PIG_YF_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.SEED_FLAG = 'Y' AND B0.SEX = '1', 1, 0)) PIG_LZG_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.SEED_FLAG = 'Y' AND B0.SEX = '2', 1, 0)) PIG_LZM_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 24 AND B0.PIG_TYPE = 1, 1, 0)) PIG_TTG_QTY, ");
        sqlCon.addMainSql(" SUM(IF(B0.PIG_CLASS = 24 AND B0.PIG_TYPE = 2, 1, 0)) PIG_TTM_QTY, ");
        sqlCon.addMainSql(" COUNT(B0.PIG_CLASS) PIG_ALL_QTY ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql("     SELECT  ");
        sqlCon.addMainSql("         IF(T2.PIG_CLASS IS NULL,T0.PIG_CLASS,T2.PIG_CLASS) PIG_CLASS, ");
        sqlCon.addMainSql("         T0.SEED_FLAG, ");
        sqlCon.addMainSql("         T0.SEX, ");
        sqlCon.addMainSql("         T0.PIG_TYPE  ");
        sqlCon.addMainSql("         FROM pp_l_pig T0 ");
        sqlCon.addMainSql("         LEFT JOIN ( ");
        sqlCon.addMainSql("         SELECT A0.* ");
        sqlCon.addMainSql("         FROM pp_l_bill_towork A0 ");
        sqlCon.addMainSql("         WHERE A0.STATUS = '1' ");
        sqlCon.addMainSql("         AND A0.DELETED_FLAG = '0' ");
        sqlCon.addConditionWithNull(maxDate, "         AND A0.TOWORK_DATE <= ? ");
        sqlCon.addConditionWithNull(maxDate, "         AND (A0.TOWORK_DATE_OUT > ? OR A0.TOWORK_DATE_OUT IS NULL) ");
        sqlCon.addMainSql("     ) T2 ");
        sqlCon.addMainSql("     ON T2.PIG_ID = T0.ROW_ID ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T0.FARM_ID = T3.ROW_ID)");
        sqlCon.addMainSql("     WHERE T0.DELETED_FLAG = '0'   AND T0.STATUS = '1'  AND (T0.ORIGIN <> 3 OR T0.ORIGIN IS NULL)");
        sqlCon.addCondition(inputCompanyMark, " AND ( T3.COMPANY_MARK = ? ");
        sqlCon.addCondition(inputCompanyMark + ",%", " OR T3.COMPANY_MARK LIKE ? )");
        sqlCon.addConditionWithNull(maxDate, "     AND T0.ENTER_DATE <= ? ");
        sqlCon.addConditionWithNull(maxDate, "     AND (T0.LEAVE_DATE > ? OR T0.LEAVE_DATE IS NULL) ");
        sqlCon.addConditionWithNull(maxDate, "     ORDER BY T0.ROW_ID ");
        sqlCon.addMainSql(" )B0 ");

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, String>> infos = paramMapper.getInfos(map);

        return infos;

    }

    // 销售总计
    @Override
    public List<Map<String, Object>> searchSaleTotal(Map<String, Object> inputMap) throws Exception {

        return searchSaleTotalMethod(inputMap);
    }

    // 销售苗猪总计
    @Override
    public List<Map<String, Object>> searchSalePiggyPigTotal(Map<String, Object> inputMap) throws Exception {

        inputMap.put("saleDescribe", PigConstants.SELL_GOOD_PIGGY_PIG);

        return searchMulSaleTotal(inputMap);
    }

    // 销售肥猪总计
    @Override
    public List<Map<String, Object>> searchSaleHogPigTotal(Map<String, Object> inputMap) throws Exception {

        inputMap.put("saleDescribe", PigConstants.SELL_GOOD_HOG_PIG);

        return searchMulSaleTotal(inputMap);
    }

    // 销售种猪总计
    @Override
    public List<Map<String, Object>> searchSaleBoarTotal(Map<String, Object> inputMap) throws Exception {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG);
        stringBuffer.append(",");
        stringBuffer.append(PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG);
        stringBuffer.append(",");
        stringBuffer.append(PigConstants.SELL_GOOD_RESERVE_BOARD_PIG);
        stringBuffer.append(",");
        stringBuffer.append(PigConstants.SELL_GOOD_RESERVE_SOW_PIG);
        stringBuffer.append(",");
        stringBuffer.append(PigConstants.SELL_GOOD_SOW_SEED);
        stringBuffer.append(",");
        stringBuffer.append(PigConstants.SELL_GOOD_BOAR_SEED);
        stringBuffer.append(",");
        stringBuffer.append(PigConstants.SELL_GOOD_SEED);
        String saleDescribe = stringBuffer.toString();

        inputMap.put("saleDescribe", saleDescribe);

        return searchMulSaleTotal(inputMap);
    }

    // 销售其他类型总计
    @Override
    public List<Map<String, Object>> searchSaleOtherTotal(Map<String, Object> inputMap) throws Exception {

        inputMap.put("saleDescribe", PigConstants.SELL_GOOD_IMPERFECT_PIG);

        return searchMulSaleTotal(inputMap);
    }

    private List<Map<String, Object>> searchMulSaleTotal(Map<String, Object> inputMap) throws Exception {
        // 特殊 ：销售单的费用和货款差异 算的时候要取平均
        // Map<String, String> commonInfo = getCommonInfo(inputMap);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT SUM(b.saleNum) saleNum,ROUND(SUM(b.price)/SUM(b.saleNum),0) avPrice,");
        sqlCon.addMainSql(" ROUND(SUM(b.weight)/SUM(b.saleNum) ,0) avWeight FROM (");
        sqlCon.addMainSql(" SELECT s.BILL_ID,SUM(d.SALE_NUM) saleNum,IFNULL(SUM(d.TOTAL_PRICE),0)-");
        sqlCon.addMainSql(" (IFNULL(S.OTHER_FEE,0)-IFNULL(S.PAYMENT_DIFF,0))*");
        sqlCon.addMainSql(" SUM(d.SALE_NUM)/(SELECT SUM(SALE_NUM) FROM pp_l_bill_pig_sale_detail");
        sqlCon.addMainSql(" WHERE BILL_ID = s.BILL_ID) price,");
        sqlCon.addMainSql(" SUM(IFNULL(d.TOTAL_WEIGHT,0)) weight");
        sqlCon.addMainSql(" FROM PP_L_BILL_PIG_SALE S");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_PIG_SALE_DETAIL D");
        sqlCon.addMainSql(" ON S.BILL_ID = D.BILL_ID AND d.DELETED_FLAG = 0 AND s.FARM_ID = d.FARM_ID");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON s.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE s.DELETED_FLAG = 0");
        sqlCon.addMainSql(" AND s.SAP_SALE_TYPE IN ('1','2')");
        // sqlCon.addCondition(PigConstants.SALE_BILL_TYPE_OUTSIDE, " AND s.SALE_BILL_TYPE = ?");

        if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE)) || "allCustomer"
                .equals(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
            // sqlCon.addCondition(AnalyticalDataConstants.ACCOUNT_COMPANY_CLASS_COMPANY, " AND c.ACCOUNT_COMPANY_CLASS IN ?", false, true);
        } else {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND (c.COMPANY_MARK = ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ?)");
        }

        sqlCon.addCondition(Maps.getString(inputMap, "saleDescribe"), " AND d.SALE_DESCRIBE IN ?", false, true);
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND d.SALE_DATE BETWEEN ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND ?");
        sqlCon.addMainSql(" GROUP BY S.BILL_ID) b");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        return list;
    }

    private List<Map<String, Object>> searchSaleTotalMethod(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = getCommonInfo(inputMap);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT ROUND(IFNULL(SUM(b.saleNum),0)/10000,2) saleTotalNum,ROUND(IFNULL(SUM(b.price),0)/100000000,2) saleTotalPrice FROM (");
        sqlCon.addMainSql(" SELECT s.BILL_ID,SUM(d.SALE_NUM) saleNum,IFNULL(SUM(d.TOTAL_PRICE),0)-");
        sqlCon.addMainSql(" IFNULL(S.OTHER_FEE,0) - IFNULL(S.PAYMENT_DIFF,0) price,");
        sqlCon.addMainSql(" SUM(IFNULL(d.TOTAL_WEIGHT,0)) weight");
        sqlCon.addMainSql(" FROM PP_L_BILL_PIG_SALE S");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_PIG_SALE_DETAIL D");
        sqlCon.addMainSql(" ON S.BILL_ID = D.BILL_ID AND d.DELETED_FLAG = 0 AND s.FARM_ID = d.FARM_ID");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON s.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE s.DELETED_FLAG = 0");
        sqlCon.addMainSql(" AND s.SAP_SALE_TYPE IN ('1','2')");
        // sqlCon.addCondition(PigConstants.SALE_BILL_TYPE_OUTSIDE, " AND s.SALE_BILL_TYPE = ?");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE)) || "allCustomer"
                    .equals(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK))) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
                // sqlCon.addCondition(AnalyticalDataConstants.ACCOUNT_COMPANY_CLASS_COMPANY, " AND c.ACCOUNT_COMPANY_CLASS IN ?", false, true);
            } else {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND (c.COMPANY_MARK = ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ?)");
            }
        } else {
            sqlCon.addCondition(getCompanyMark(), " AND (c.COMPANY_MARK = ?");
            sqlCon.addCondition(getCompanyMark() + ",%", " OR c.COMPANY_MARK LIKE ?)");
        }

        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND d.SALE_DATE BETWEEN ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND ?");
        sqlCon.addMainSql(" GROUP BY S.BILL_ID) b");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        return list;
    }

    // 可上市肥猪头数
    @Override
    public List<Map<String, Object>> searchGoPublicHogPig(Map<String, Object> inputMap) throws Exception {

        // Map<String, String> commonInfo = getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT SUM(IF(DATEDIFF (");
        sqlCon.addCondition(Maps.getString(inputMap, "endDate"), " ?,");
        sqlCon.addMainSql(" DATE(s.LAST_OPERATE_DATE))+s.SWINERY_DAYAGE >= 180,1,0)) pigQty");
        sqlCon.addMainSql(" FROM pp_l_pig p");
        sqlCon.addMainSql(" INNER JOIN pp_m_swinery s");
        sqlCon.addMainSql(" ON p.FARM_ID = s.FARM_ID AND p.SWINERY_ID = s.ROW_ID AND s.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON p.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE p.DELETED_FLAG= 0");
        sqlCon.addCondition(PigConstants.PIG_CLASS_YH, " AND p.PIG_CLASS = ?");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE)) || "allCustomer"
                    .equals(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK))) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
            } else {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND (c.COMPANY_MARK = ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ?)");
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (!Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( c.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ? )");
            }
        }
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public List<Map<String, Object>> searchDieNumOfHouse(Map<String, Object> inputMap) throws Exception {
        Map<String, String> map = getCommonInfo(inputMap);
        SqlCon sqlCon = new SqlCon();
        // sqlCon.addMainSql(sql);
        return null;
    }

    @Override
    public List<Map<String, Object>> searchFarmByOrigin(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT COMPANY_MARK companyMark,ROW_ID farmId, SORT_NAME farmName FROM hr_m_company WHERE DELETED_FLAG = 0 ");

        Map<String, Object> map = new HashMap<>();
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            map.put("companyMark", getCompanyMark());
            map.put("farmId", getFarmId());
            map.put("farmName", "全部");
            sqlCon.addCondition(getCompanyMark(), " AND ( COMPANY_MARK = ?");
            sqlCon.addCondition(getCompanyMark() + ",%", " OR COMPANY_MARK LIKE ? )");
        } else {
            if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
                map.put("companyMark", AnalyticalDataConstants.COMPANY_MARK_XNFEED);
                map.put("farmId", AnalyticalDataConstants.FARM_ID_XNFEED);
                map.put("farmName", "全部");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED, " AND ( COMPANY_MARK = ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " OR COMPANY_MARK LIKE ? )");
            } else {
                map.put("companyMark", "allCustomer");
                map.put("farmId", "allCustomerId");
                map.put("farmName", "全部");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND COMPANY_MARK LIKE ?");
            }
        }

        if (true == Boolean.valueOf(Maps.getString(inputMap, "isGroupFarm", "true"))) {
            sqlCon.addCondition(AnalyticalDataConstants.ACCOUNT_COMPANY_CLASS_FARM, " AND ACCOUNT_COMPANY_CLASS IN ?", false, true);
        } else {
            sqlCon.addMainSql(" ORDER BY SORT_NAME  ");
        }

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        list.add(0, map);

        return list;
    }

    @Override
    public List<Map<String, Object>> searchBoarDieTotal(Map<String, Object> inputMap) throws Exception {
        // Map<String, String> commonInfo = getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT c.SORT_NAME farmName,COUNT(1) pigQty,DATE(l.LEAVE_DATE) leaveDate,");
        sqlCon.addMainSql(" l.LEAVE_REASON leaveReason");
        sqlCon.addMainSql(" FROM pp_l_bill_leave l");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON l.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0 ");
        sqlCon.addMainSql(" WHERE l.DELETED_FLAG = 0 ");
        sqlCon.addCondition(PigConstants.PIG_BOAR_DIE, " AND l.LEAVE_TYPE = ?");

        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND l.LEAVE_DATE BETWEEN ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND ?");

        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND c.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND c.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND c.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( c.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" GROUP BY c.SORT_NAME,l.LEAVE_DATE,l.LEAVE_REASON");
        sqlCon.addMainSql(" ORDER BY c.ROW_ID");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : list) {
            String reason = CacheUtil.getName(Maps.getString(map, "leaveReason"), xn.core.data.enums.NameEnum.CODE_NAME, CodeEnum.BOAR_DIE_REASON);
            map.put("leaveReason", reason);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchBoarObsoleteTotal(Map<String, Object> inputMap) throws Exception {
        // Map<String, String> commonInfo = getCommonInfo(inputMap);
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T2.SORT_NAME farmName ,COUNT(1) pigQty,T1.SALE_DATE auditDate,T4.OBSOLETE_REASON obsoleteReason ");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0  ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ON T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T2 ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(
                " LEFT JOIN pp_l_bill_leave T3 ON  T0.BILL_ID = T3.BILL_ID AND T3.LINE_NUMBER = T1.LINE_NUMBER AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN  pp_l_bill_boar_obsolete T4 ON T3.PIG_ID = T4.PIG_ID AND T4.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" WHERE T0.SALE_TYPE = 3 AND T0.SAP_SALE_TYPE IN ('1','2')  AND T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");

        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.SALE_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.SALE_DATE <= ?");

        if (AnalyticalDataConstants.ORIGIN_TYPE_XNFEED.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
        } else if (AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER.equals(Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE))) {
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
            sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" GROUP BY T2.SORT_NAME,T1.SALE_DATE,T4.OBSOLETE_REASON ");
        sqlCon.addMainSql(" ORDER BY T2.ROW_ID ");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : list) {
            String reason = CacheUtil.getName(Maps.getString(map, "obsoleteReason"), xn.core.data.enums.NameEnum.CODE_NAME, CodeEnum.OBSOLETE_REASON);
            map.put("obsoleteReason", reason);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchFarmHouseDieTotal(Map<String, Object> inputMap) throws Exception {
        // Map<String, String> commonInfo = getCommonInfo(inputMap);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT house_name houseName,COUNT(1) pigQty,");
        sqlCon.addMainSql(" ROUND(SUM(DATEDIFF(DATE(l.LEAVE_DATE),p.birth_date))/COUNT(1),0) dayAge,");
        sqlCon.addMainSql(" ROUND(IFNULL(h.AREA/(SELECT COUNT(1) FROM pp_l_pig");
        sqlCon.addMainSql(" WHERE FARM_ID = c.ROW_ID AND PIG_CLASS IN (10,12,13,14,15,16)");
        sqlCon.addMainSql(" AND HOUSE_ID = h.ROW_ID),0),1) density");
        sqlCon.addMainSql(" FROM pp_l_pig p");
        sqlCon.addMainSql(" INNER JOIN pp_l_bill_leave l");
        sqlCon.addMainSql(" ON p.ROW_ID = l.PIG_ID AND l.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON p.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN pp_o_house h");
        sqlCon.addMainSql(" ON l.HOUSE_ID = h.ROW_ID AND h.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE p.DELETED_FLAG = 0 ");

        Long houseType = null;
        if (AnalyticalDataConstants.PIG_TYPE_DELIVERY.equals(Maps.getString(inputMap, "pigType"))) {
            houseType = PigConstants.HOUSE_CLASS_DELIVERY;
        } else if (AnalyticalDataConstants.PIG_TYPE_TO_CARE.equals(Maps.getString(inputMap, "pigType"))) {
            houseType = PigConstants.HOUSE_CLASS_CAREPIG;
        } else if (AnalyticalDataConstants.PIG_TYPE_TO_FAT.equals(Maps.getString(inputMap, "pigType"))) {
            houseType = PigConstants.HOUSE_CLASS_FATTEN;
        }
        sqlCon.addCondition(houseType, " AND h.HOUSE_TYPE = ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND (c.COMPANY_MARK = ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ?)");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND l.LEAVE_DATE between ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND ?");
        sqlCon.addMainSql(" AND l.LEAVE_TYPE IN (3,4)");
        sqlCon.addMainSql(" AND l.PIG_CLASS IN (12,IF((SELECT SETTING_VALUE FROM CD_M_SETTING");
        sqlCon.addMainSql(" WHERE SETTING_CODE = 'RZBSCL' AND FARM_ID = p.FARM_ID) = 'ON',12,13),14,15,16)");
        sqlCon.addMainSql(" GROUP BY h.ROW_ID");
        return getList(sqlCon);
    }

    @Override
    public List<Map<String, Object>> searchLeaveReasonDieTotal(Map<String, Object> inputMap) throws Exception {
        // Map<String, String> commonInfo = getCommonInfo(inputMap);

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT l.LEAVE_REASON leaveReason,COUNT(1) pigQty,");
        sqlCon.addMainSql(" ROUND(SUM(DATEDIFF(DATE(l.LEAVE_DATE),p.birth_date))/COUNT(1),0) dayAge");
        sqlCon.addMainSql(" FROM pp_l_pig p");
        sqlCon.addMainSql(" INNER JOIN pp_l_bill_leave l");
        sqlCon.addMainSql(" ON p.ROW_ID = l.PIG_ID AND l.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON p.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN pp_o_house h");
        sqlCon.addMainSql(" ON l.HOUSE_ID = h.ROW_ID AND h.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE p.DELETED_FLAG = 0 ");
        sqlCon.addCondition(Maps.getString(inputMap, "houseId"), " AND h.ROW_ID = ?");

        Long houseType = null;
        if (AnalyticalDataConstants.PIG_TYPE_DELIVERY.equals(Maps.getString(inputMap, "pigType"))) {
            houseType = PigConstants.HOUSE_CLASS_DELIVERY;
        } else if (AnalyticalDataConstants.PIG_TYPE_TO_CARE.equals(Maps.getString(inputMap, "pigType"))) {
            houseType = PigConstants.HOUSE_CLASS_CAREPIG;
        } else if (AnalyticalDataConstants.PIG_TYPE_TO_FAT.equals(Maps.getString(inputMap, "pigType"))) {
            houseType = PigConstants.HOUSE_CLASS_FATTEN;
        }
        sqlCon.addCondition(houseType, " AND h.HOUSE_TYPE = ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND (c.COMPANY_MARK = ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR c.COMPANY_MARK LIKE ?)");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND l.LEAVE_DATE between ?");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND ?");
        sqlCon.addMainSql(" AND l.LEAVE_TYPE IN (3,4)");
        sqlCon.addMainSql(" AND l.PIG_CLASS IN (12,IF((SELECT SETTING_VALUE FROM CD_M_SETTING");
        sqlCon.addMainSql(" WHERE SETTING_CODE = 'RZBSCL' AND FARM_ID = p.FARM_ID) = 'ON',12,13),14,15,16)");
        sqlCon.addMainSql(" GROUP BY l.LEAVE_REASON");

        List<Map<String, Object>> list = getList(sqlCon);
        for (Map<String, Object> map : list) {
            map.put("leaveReasonName", CacheUtil.getName(Maps.getString(map, "leaveReason"), NameEnum.CODE_NAME, CodeEnum.GOOD_DIE_REASON));
        }
        return list;
    }

    @Override
    public Map<String, Object> searchGroupHomeProData(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("breedNum", getBreedNum(inputMap).get("breedNum"));
        Map<String, Object> deliveryMap = getDeliveryNum(inputMap);
        resultMap.put("deliveryNum", deliveryMap.get("deliveryNum"));
        resultMap.put("avgHealthyNum", deliveryMap.get("avgHealthyNum"));
        resultMap.put("avgWeanNum", getWeanNum(inputMap).get("avgWeanNum"));
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String beforeDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        if (beforeDate == null) {
            String startDateArry[] = startDate.split("-");
            beforeDate = startDateArry[0] + "-" + startDateArry[1] + "-01";
        }
        // 窝均断奶重
        // MonthPerformanceModel mnthPerformanceModel_ = monthPerformanceMapper.searchMinPorkBirthDateGroup(beforeDate, startDate, Maps.getString(
        // inputMap, AnalyticalDataConstants.ORIGIN_TYPE), PigConstants.CHANGE_HOUSE_TYPE_CHI, PigConstants.HOUSE_CLASS_DELIVERY);
        // if (mnthPerformanceModel_ == null) {
        // resultMap.put("24DayWeanWeight", 0);
        // } else {
        // MonthPerformanceModel monthPerformanceModel1 = monthPerformanceMapper.searchProductionByWeanNumGroup(beforeDate, startDate, Maps
        // .getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE), mnthPerformanceModel_.getNotes());
        // MonthPerformanceModel monthPerformanceModel2 = monthPerformanceMapper.searchProductionByWeanGroup(beforeDate, startDate, Maps.getString(
        // inputMap, AnalyticalDataConstants.ORIGIN_TYPE), getFarmId(), monthPerformanceModel1.getSystemIndex());
        // resultMap.put("24DayWeanWeight", monthPerformanceModel2.getSystemIndex());
        // }
        // 7030
        MonthPerformanceModel mnthPerformanceModel3 = new MonthPerformanceModel();
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            mnthPerformanceModel3 = monthPerformanceMapper.searchProductionByChild(beforeDate, startDate, getCompanyMark(), getFarmId());
        } else {
            mnthPerformanceModel3 = monthPerformanceMapper.searchProductionByChildGroup(beforeDate, startDate, Maps.getString(inputMap,
                    AnalyticalDataConstants.ORIGIN_TYPE), getFarmId());
        }

        if (mnthPerformanceModel3 == null) {
            resultMap.put("7030", 0);
        } else {
            resultMap.put("7030", mnthPerformanceModel3.getSystemIndex());
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchGroupHomeSaleData(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> saleMap = getSaleNum(inputMap);
        resultMap.put("mzNum", saleMap.get("mzNum"));
        resultMap.put("fzNum", saleMap.get("fzNum"));
        resultMap.put("zzNum", saleMap.get("zzNum"));
        resultMap.put("salePrice", saleMap.get("salePrice"));
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String beforeDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        if (beforeDate == null) {
            String startDateArry[] = startDate.split("-");
            beforeDate = startDateArry[0] + "-" + startDateArry[1] + "-01";
        }
        MonthPerformanceModel mnthPerformanceModel = new MonthPerformanceModel();
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            monthPerformanceMapper.searchProductionByFatField(beforeDate, startDate, getCompanyMark(), getFarmId());
        } else {
            monthPerformanceMapper.searchProductionByFatFieldGroup(beforeDate, startDate, Maps.getString(inputMap,
                    AnalyticalDataConstants.ORIGIN_TYPE), getFarmId());
        }
        if (mnthPerformanceModel == null || mnthPerformanceModel.getSystemIndex() == null) {
            resultMap.put("110KGOut", 0);
        } else {
            resultMap.put("110KGOut", mnthPerformanceModel.getSystemIndex());
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> searchGroupHomeSafetyData(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> dieMap = getDieNum(inputMap);
        Map<String, Object> breedPigDie = getBreedPigDieNum(inputMap);
        resultMap.put("cfDieNum", dieMap.get("cfDieNum"));
        resultMap.put("byDieNum", dieMap.get("byDieNum"));
        resultMap.put("yfDieNum", dieMap.get("yfDieNum"));
        resultMap.put("gzDieNum", breedPigDie.get("gzDieNum"));
        resultMap.put("mzDieNum", breedPigDie.get("mzDieNum"));
        resultMap.put("allZZDieNum", dieMap.get("allDieNum"));
        int allDieNum = Integer.parseInt(dieMap.get("allDieNum").toString());
        int healthyNum = Integer.parseInt(getDeliveryNum(inputMap).get("healthyNum").toString());
        if (healthyNum == 0) {
            resultMap.put("allDieRate", "0.00");
        } else {
            double allDieRate = (double) allDieNum / (double) healthyNum * 100;
            BigDecimal allDieRateR = new BigDecimal(allDieRate).setScale(2, RoundingMode.HALF_UP);
            resultMap.put("allDieRate", allDieRateR.doubleValue());
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchGroupChangeCondition(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("toCareNum", getChildNum(inputMap).get("toCareNum"));
        resultMap.put("toFatNum", getChildNum(inputMap).get("toFatNum"));
        resultMap.put("seedNum", getSeedNum(inputMap).get("seedNum"));
        resultMap.put("selectNum", getSelectNum(inputMap).get("selectNum"));
        return resultMap;
    }

    @Override
    public Map<String, Object> searchGroupIndicator(Map<String, Object> inputMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int sowSum = Integer.parseInt(getPSY(inputMap).get("sowSum").toString());
        int dayLength = Integer.parseInt(getPSY(inputMap).get("dayLength").toString());
        resultMap.put("psy", getDieNum(inputMap).get("allDieNum"));
        return resultMap;
    }

    @Override
    public Map<String, Object> searchGroupReproductionCondition(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> breedMap = getBreedCondition(inputMap);
        Map<String, Object> pregnancyMap = getPregnancyCondition(inputMap);
        resultMap.put("hbBreed", breedMap.get("hbBreed"));
        resultMap.put("scBreed", breedMap.get("scBreed"));
        resultMap.put("fqNum", pregnancyMap.get("fqNum"));
        resultMap.put("lcNum", pregnancyMap.get("lcNum"));
        resultMap.put("khNum", pregnancyMap.get("khNum"));
        return resultMap;
    }

    @Override
    public Map<String, Object> searchGroupBirthCondition(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        Map<String, Object> deliveryMap = getDeliveryNum(inputMap);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("deliveryNum", deliveryMap.get("deliveryNum"));
        resultMap.put("healthyNum", deliveryMap.get("healthyNum"));
        resultMap.put("avgHealthyNum", deliveryMap.get("avgHealthyNum"));
        resultMap.put("avgAliveWeight", deliveryMap.get("avgAliveWeight"));
        resultMap.put("weekNum", deliveryMap.get("weekNum"));
        resultMap.put("stillBirthNum", deliveryMap.get("stillBirthNum"));
        resultMap.put("mummyNum", deliveryMap.get("mummyNum"));
        resultMap.put("mutantNum", deliveryMap.get("mutantNum"));
        Map<String, Object> seedConditionMap = getSeedCondition(inputMap);
        resultMap.put("gSeed", seedConditionMap.get("gSeed"));
        resultMap.put("mSeed", seedConditionMap.get("mSeed"));
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> searchGroupBreedDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
        if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_BREED_RESERVE)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.LAST_STATUS = '4',1,0)),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_BREED_PRODUCT)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.LAST_STATUS <> '4',1,0)),0) pigQty ");
        }
        sqlCon.addMainSql(" FROM cd_o_every_day T0");
        sqlCon.addMainSql(" LEFT JOIN( SELECT T1.LAST_STATUS,DATE(T1.BREED_DATE_FIRST) BREED_DATE_FIRST");
        sqlCon.addMainSql(" FROM pp_l_bill_breed T1  ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.BREED_DATE_FIRST ");
        sqlCon.addMainSql(" WHERE 1 = 1  ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
        }
        sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupPregnancyCheckDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
        if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_CHECK_BACK)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.PREGNANCY_RESULT = '4',1,0)),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_CHECK_MISCARRY)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.PREGNANCY_RESULT = '5',1,0)),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_CHECK_EMPTY)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.PREGNANCY_RESULT = '6',1,0)),0) pigQty ");
        }
        sqlCon.addMainSql(" FROM cd_o_every_day T0");
        sqlCon.addMainSql(" LEFT JOIN( SELECT T1.PREGNANCY_RESULT,DATE(T1.PREGNANCY_DATE) PREGNANCY_DATE");
        sqlCon.addMainSql(" FROM pp_l_bill_pregnancy_check T1  ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.PREGNANCY_DATE ");
        sqlCon.addMainSql(" WHERE 1 = 1  ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
        }
        sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupDeliveryDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
        if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_DELIVERY_NUM)) {
            sqlCon.addMainSql(" COUNT(T3.ROW_ID) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_HEALTHY_NUM)) {
            sqlCon.addMainSql(" IFNULL(SUM(T3.HEALTHY_NUM),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_AVG_HEALTH_NUM)) {
            sqlCon.addMainSql(" IFNULL(ROUND(SUM(T3.HEALTHY_NUM)/COUNT(T3.ROW_ID),2),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_AVG_ALIVE_WEIGHT)) {
            sqlCon.addMainSql(
                    " IFNULL(ROUND(SUM(T3.ALIVE_LITTER_WEIGHT)/IF(IFNULL(T3.SETTING_VALUE, ( SELECT SETTING_VALUE  FROM cd_m_setting_module  WHERE SETTING_CODE = 'RZBSCL' AND DELETED_FLAG = '0' AND STATUS = '1')) = 'ON',SUM(T3.HEALTHY_NUM)+SUM(T3.WEAK_NUM),SUM(T3.HEALTHY_NUM)),2),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_WEEK_NUM)) {
            sqlCon.addMainSql(" IFNULL(SUM(T3.WEAK_NUM),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_STILL_BIRTH_NUM)) {
            sqlCon.addMainSql(" IFNULL(SUM(T3.STILLBIRTH_NUM),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_MUMMY_NUM)) {
            sqlCon.addMainSql(" IFNULL(SUM(T3.MUMMY_NUM),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_MUTANT_NUM)) {
            sqlCon.addMainSql(" IFNULL(SUM(T3.MUTANT_NUM),0) pigQty ");
        }
        sqlCon.addMainSql(" FROM cd_o_every_day T0");
        sqlCon.addMainSql(
                " LEFT JOIN( SELECT T1.ROW_ID,T1.HEALTHY_NUM,T1.ALIVE_LITTER_WEIGHT,T4.SETTING_VALUE,T1.WEAK_NUM,T1.STILLBIRTH_NUM,T1.MUMMY_NUM,T1.MUTANT_NUM,DATE(T1.DELIVERY_DATE) DELIVERY_DATE");
        sqlCon.addMainSql(" FROM pp_l_bill_delivery T1  ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(
                " LEFT JOIN cd_m_setting T4 ON T1.FARM_ID = T4.FARM_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' AND T4.SETTING_CODE = 'RZBSCL'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.DELIVERY_DATE ");
        sqlCon.addMainSql(" WHERE 1 = 1  ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
        }
        sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupSeedPigDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
        if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_ALIVE_LITTER_Y)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.SEX = '1',1,0)),0) pigQty ");
        } else if (Maps.getString(inputMap, "searchType").equals(AnalyticalDataConstants.PIG_TYPE_ALIVE_LITTER_X)) {
            sqlCon.addMainSql(" IFNULL(SUM(IF(T3.SEX = '2',1,0)),0) pigQty ");
        }
        sqlCon.addMainSql(" FROM cd_o_every_day T0");
        sqlCon.addMainSql(" LEFT JOIN( SELECT T1.SEX,DATE(T1.SEED_DATE) SEED_DATE");
        sqlCon.addMainSql(" FROM pp_l_bill_seed_detail T1  ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.SEED_DATE ");
        sqlCon.addMainSql(" WHERE 1 = 1  ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
        }
        sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupDieDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        if (Maps.getString(inputMap, "searchType").equals("labor") || Maps.getString(inputMap, "searchType").equals("toChild") || Maps.getString(
                inputMap, "searchType").equals("toFatten")) {
            sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
            if (Maps.getString(inputMap, "searchType").equals("labor")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF((T4.LEAVE_TYPE = 3 AND  T4.PIG_CLASS = '12' ");
                sqlCon.addMainSql(" OR (T4.PIG_CLASS = '13' AND _getSettingValueByCode(T4.FARM_ID,'RZBSCL') = 'OFF')) ");
                sqlCon.addMainSql(" OR (T4.LEAVE_TYPE = 3 AND T4.PIG_CLASS = '14'),1,0)),0) pigQty ");
            } else if (Maps.getString(inputMap, "searchType").equals("toChild")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF(T4.LEAVE_TYPE = 4 AND T4.PIG_CLASS = '15',1,0)),0) pigQty ");
            } else if (Maps.getString(inputMap, "searchType").equals("toFatten")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF(T4.LEAVE_TYPE = 4 AND T4.PIG_CLASS = '16',1,0)),0) pigQty ");
            }
            sqlCon.addMainSql(" FROM cd_o_every_day T0");
            sqlCon.addMainSql(" LEFT JOIN( SELECT T1.FARM_ID,T1.PIG_CLASS,T1.LEAVE_TYPE,T1.LEAVE_DATE ");
            sqlCon.addMainSql(" FROM pp_l_bill_leave T1  ");
            sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.LEAVE_TYPE IN ('3','4')");
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
            sqlCon.addMainSql(" )T4 ON T0.EVERY_DAY = T4.LEAVE_DATE ");
            sqlCon.addMainSql(" WHERE 1 = 1  ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) == null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
            }
            sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        } else {
            sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
            if (Maps.getString(inputMap, "searchType").equals("boar")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF(T3.PIG_TYPE = '1' ,1,0)),0) pigQty ");
            } else if (Maps.getString(inputMap, "searchType").equals("sow")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF(T3.PIG_TYPE = '2' ,1,0)),0) pigQty ");
            }
            sqlCon.addMainSql(" FROM cd_o_every_day T0");
            sqlCon.addMainSql(" LEFT JOIN( SELECT T1.PIG_TYPE,DATE(T1.LEAVE_DATE) LEAVE_DATE");
            sqlCon.addMainSql(" FROM pp_l_bill_leave T1  ");
            sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.LEAVE_TYPE = '2'");
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
            sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.LEAVE_DATE ");
            sqlCon.addMainSql(" WHERE 1 = 1  ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) == null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
            }
            sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        }

        List<Map<String, Object>> list = getList(sqlCon);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupChangeConditionDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        if (Maps.getString(inputMap, "searchType").equals("toChild") || Maps.getString(inputMap, "searchType").equals("toFatten")) {
            sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
            if (Maps.getString(inputMap, "searchType").equals("toChild")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF(T3.CHANGE_HOUSE_TYPE = '4',1,0)),0) pigQty ");
            } else if (Maps.getString(inputMap, "searchType").equals("toFatten")) {
                sqlCon.addMainSql(" IFNULL(SUM(IF(T3.CHANGE_HOUSE_TYPE = '6',1,0)),0) pigQty ");
            }
            sqlCon.addMainSql(" FROM cd_o_every_day T0");
            sqlCon.addMainSql(" LEFT JOIN( SELECT T1.CHANGE_HOUSE_TYPE,DATE(T1.CHILD_DATE) CHILD_DATE ");
            sqlCon.addMainSql(" FROM pp_l_bill_to_child T1  ");
            sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
            sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.CHILD_DATE ");
            sqlCon.addMainSql(" WHERE 1 = 1  ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) == null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
            }
            sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        } else if (Maps.getString(inputMap, "searchType").equals("seedPig")) {
            sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
            sqlCon.addMainSql(" COUNT(T3.ROW_ID) pigQty ");
            sqlCon.addMainSql(" FROM cd_o_every_day T0");
            sqlCon.addMainSql(" LEFT JOIN( SELECT T1.ROW_ID,DATE(T1.SEED_DATE) SEED_DATE");
            sqlCon.addMainSql(" FROM pp_l_bill_seed_detail T1  ");
            sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
            sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.SEED_DATE ");
            sqlCon.addMainSql(" WHERE 1 = 1  ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) == null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
            }
            sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        } else if (Maps.getString(inputMap, "searchType").equals("selectPig")) {
            sqlCon.addMainSql(" SELECT T0.EVERY_DAY everyDay, ");
            sqlCon.addMainSql(" COUNT(T3.ROW_ID) pigQty ");
            sqlCon.addMainSql(" FROM cd_o_every_day T0");
            sqlCon.addMainSql(" LEFT JOIN( SELECT T1.ROW_ID,DATE(T1.SELETC_DATE) SELETC_DATE");
            sqlCon.addMainSql(" FROM pp_l_bill_select_breed T1  ");
            sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
            sqlCon.addMainSql(" )T3 ON T0.EVERY_DAY = T3.SELETC_DATE ");
            sqlCon.addMainSql(" WHERE 1 = 1  ");
            if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) == null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY = ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.EVERY_DAY >= ?");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.EVERY_DAY <= ?");
            }
            sqlCon.addMainSql(" GROUP BY T0.EVERY_DAY ");
        }

        List<Map<String, Object>> list = getList(sqlCon);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupSupplyMonthInput(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T2.MATERIAL_FIRST_CLASS materialFirstClass, ");
        sqlCon.addMainSql(" (SUM(IFNULL(T0.INPUT_QTY,0) * IFNULL(T4.RECEIPT_ACTUAL_PRICE, T3.ACTUAL_PRICE) ) /10000) totalPrice");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T0 ");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY T1 ON (T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0') ");
        sqlCon.addMainSql(" LEFT OUTER JOIN CD_M_MATERIAL T2 ON (T0.MATERIAL_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0') ");
        sqlCon.addMainSql(" LEFT OUTER JOIN ");
        sqlCon.addMainSql(" (SELECT MAX(ACTUAL_PRICE) AS ACTUAL_PRICE,MATERIAL_ONLY FROM SC_M_BILL_PURCHASE_DETAIL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS <> '6' GROUP BY MATERIAL_ONLY ) T3");
        sqlCon.addMainSql(" ON (T0.MATERIAL_ONLY = T3.MATERIAL_ONLY) ");
        sqlCon.addMainSql(" LEFT OUTER JOIN ");
        sqlCon.addMainSql(" (SELECT MATERIAL_ID,MATERIAL_ONLY,MATERIAL_BATCH,RECEIPT_PRICE AS RECEIPT_ACTUAL_PRICE ");
        sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT T1 ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM( SELECT MIN(ROW_ID) AS ROW_ID");
        sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT WHERE DELETED_FLAG = '0' GROUP BY MATERIAL_ONLY, MATERIAL_BATCH");
        sqlCon.addMainSql(" ) A WHERE A.ROW_ID = T1.ROW_ID )) T4");
        sqlCon.addMainSql(" ON(T0.MATERIAL_ID = T4.MATERIAL_ID");
        sqlCon.addMainSql(" AND T0.MATERIAL_ONLY = T4.MATERIAL_ONLY");
        sqlCon.addMainSql(" AND T0.MATERIAL_BATCH = T4.MATERIAL_BATCH)");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " AND (T0.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T0.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_NOT_BILL, " OR T0.EVENT_CODE = ?)");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_21, " AND T2.MATERIAL_FIRST_CLASS BETWEEN ?");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_50, " AND ?");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);

            } else {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
            }

        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.BILL_DATE >= ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.BILL_DATE <= ? ");
        }
        sqlCon.addMainSql(" GROUP BY T2.MATERIAL_FIRST_CLASS");

        List<Map<String, Object>> materialFirstClassList = getList(sqlCon);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 饲料
        Map<String, Object> financeFirstFeed = new HashMap<String, Object>();
        financeFirstFeed.put("materialType", "FINANCE_FIRST_FEED");
        financeFirstFeed.put("totalPrice", 0D);
        list.add(financeFirstFeed);
        // 药物
        Map<String, Object> financeMedicine = new HashMap<String, Object>();
        financeMedicine.put("materialType", "FINANCE_MEDICINE");
        financeMedicine.put("totalPrice", 0D);
        list.add(financeMedicine);
        // 周转材料
        Map<String, Object> financeTurnoverCost = new HashMap<String, Object>();
        financeTurnoverCost.put("materialType", "FINANCE_TURNOVER_COST");
        financeTurnoverCost.put("totalPrice", 0D);
        list.add(financeTurnoverCost);
        // 制造费用
        Map<String, Object> financeProductionCost = new HashMap<String, Object>();
        financeProductionCost.put("materialType", "FINANCE_PRODUCTION_COST");
        financeProductionCost.put("totalPrice", 0D);
        list.add(financeProductionCost);

        for (Map<String, Object> materialFirstClassMap : materialFirstClassList) {
            if (SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))
                    || SupplyChianConstants.MATERIAL_FIRST_CLASS_22.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:配合料 || 物料大类:浓缩料
                Map<String, Object> map = list.get(0);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:药物
                Map<String, Object> map = list.get(1);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_40.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:周转材料
                Map<String, Object> map = list.get(2);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_50.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:制造费用
                Map<String, Object> map = list.get(3);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            }
        }

        for (Map<String, Object> map : list) {
            map.put("totalPrice", new BigDecimal(Maps.getDouble(map, "totalPrice")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupSupplyMonthUsed(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.MATERIAL_FIRST_CLASS materialFirstClass, ");
        sqlCon.addMainSql(" (SUM(IFNULL(T0.OUTPUT_QTY,0) * IFNULL(T3.RECEIPT_ACTUAL_PRICE, T2.ACTUAL_PRICE) ) /10000) totalPrice");
        sqlCon.addMainSql(" FROM (");
        sqlCon.addMainSql(" SELECT A1.FARM_ID, A1.MATERIAL_ID, A1.MATERIAL_ONLY, A1.MATERIAL_BATCH,");
        sqlCon.addMainSql(" SUM(A1.OUTPUT_QTY) AS OUTPUT_QTY, A2.MATERIAL_FIRST_CLASS");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT_DETAIL A1");
        sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL A2");
        sqlCon.addMainSql(" ON(A2.DELETED_FLAG = '0' AND A1.MATERIAL_ID = A2.ROW_ID)");
        sqlCon.addMainSql(" WHERE A1.DELETED_FLAG = '0' AND A1.EVENT_CODE = 'OUTPUT_USE' AND A1.STATUS = '3'");
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND A1.BILL_DATE >= ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A1.BILL_DATE <= ? ");
        }
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_21, " AND A2.MATERIAL_FIRST_CLASS BETWEEN ?");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_50, " AND ?");
        sqlCon.addMainSql(" GROUP BY A1.OUTPUT_ID, A1.OUTPUT_LN, A1.MATERIAL_ONLY, A1.MATERIAL_BATCH");
        sqlCon.addMainSql(" UNION ALL");
        sqlCon.addMainSql(" SELECT A2.FARM_ID, A2.MATERIAL_ID, A2.MATERIAL_ONLY, A2.MATERIAL_BATCH,");
        sqlCon.addMainSql(" -A2.INPUT_QTY AS OUTPUT_QTY, A3.MATERIAL_FIRST_CLASS");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL A1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_OUTPUT_DETAIL A2");
        sqlCon.addMainSql(" ON(A2.DELETED_FLAG = '0' AND A1.INPUT_ID = A2.INPUT_ID AND A1.INPUT_LN = A2.INPUT_LN)");
        sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL A3");
        sqlCon.addMainSql(" ON(A3.DELETED_FLAG = '0' AND A2.MATERIAL_ID = A3.ROW_ID)");
        sqlCon.addMainSql(" WHERE A1.DELETED_FLAG = '0' AND A1.EVENT_CODE = 'INPUT_OUTPUT'");
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND A1.BILL_DATE >= ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND A1.BILL_DATE <= ? ");
        }
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_21, " AND A3.MATERIAL_FIRST_CLASS BETWEEN ?");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_50, " AND ?");
        sqlCon.addMainSql(" ) T0");
        sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T1 ON (T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0') ");
        sqlCon.addMainSql(" LEFT OUTER JOIN ");
        sqlCon.addMainSql(" (SELECT MAX(ACTUAL_PRICE) AS ACTUAL_PRICE,MATERIAL_ONLY FROM SC_M_BILL_PURCHASE_DETAIL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS <> '6' GROUP BY MATERIAL_ONLY ) T2");
        sqlCon.addMainSql(" ON (T0.MATERIAL_ONLY = T2.MATERIAL_ONLY) ");
        sqlCon.addMainSql(" LEFT OUTER JOIN ");
        sqlCon.addMainSql(" (SELECT MATERIAL_ID,MATERIAL_ONLY,MATERIAL_BATCH,RECEIPT_PRICE AS RECEIPT_ACTUAL_PRICE ");
        sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT T1 ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM( SELECT MIN(ROW_ID) AS ROW_ID");
        sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT WHERE DELETED_FLAG = '0' GROUP BY MATERIAL_ONLY, MATERIAL_BATCH");
        sqlCon.addMainSql(" ) A WHERE A.ROW_ID = T1.ROW_ID )) T3");
        sqlCon.addMainSql(" ON(T0.MATERIAL_ID = T3.MATERIAL_ID");
        sqlCon.addMainSql(" AND T0.MATERIAL_ONLY = T3.MATERIAL_ONLY");
        sqlCon.addMainSql(" AND T0.MATERIAL_BATCH = T3.MATERIAL_BATCH)");
        sqlCon.addMainSql(" WHERE 1=1");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);

            } else {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
            }
        }
        sqlCon.addMainSql("  GROUP BY T0.MATERIAL_FIRST_CLASS ");

        List<Map<String, Object>> materialFirstClassList = getList(sqlCon);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 饲料
        Map<String, Object> financeFirstFeed = new HashMap<String, Object>();
        financeFirstFeed.put("materialType", "FINANCE_FIRST_FEED");
        financeFirstFeed.put("totalPrice", 0D);
        list.add(financeFirstFeed);
        // 药物
        Map<String, Object> financeMedicine = new HashMap<String, Object>();
        financeMedicine.put("materialType", "FINANCE_MEDICINE");
        financeMedicine.put("totalPrice", 0D);
        list.add(financeMedicine);
        // 周转材料
        Map<String, Object> financeTurnoverCost = new HashMap<String, Object>();
        financeTurnoverCost.put("materialType", "FINANCE_TURNOVER_COST");
        financeTurnoverCost.put("totalPrice", 0D);
        list.add(financeTurnoverCost);
        // 制造费用
        Map<String, Object> financeProductionCost = new HashMap<String, Object>();
        financeProductionCost.put("materialType", "FINANCE_PRODUCTION_COST");
        financeProductionCost.put("totalPrice", 0D);
        list.add(financeProductionCost);

        for (Map<String, Object> materialFirstClassMap : materialFirstClassList) {
            if (SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))
                    || SupplyChianConstants.MATERIAL_FIRST_CLASS_22.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:配合料 || 物料大类:浓缩料
                Map<String, Object> map = list.get(0);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:药物
                Map<String, Object> map = list.get(1);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_40.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:周转材料
                Map<String, Object> map = list.get(2);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_50.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:制造费用
                Map<String, Object> map = list.get(3);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            }
        }

        for (Map<String, Object> map : list) {
            map.put("totalPrice", new BigDecimal(Maps.getDouble(map, "totalPrice")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchGroupSupplyMonthStore(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        String endDate = Maps.getString(inputMap, "endDate");
        String sysDate = TimeUtil.getSysDate();
        if (sysDate.equals(endDate)) {
            sqlCon.addMainSql(" SELECT T2.MATERIAL_FIRST_CLASS materialFirstClass, ");
            sqlCon.addMainSql(" (SUM(IFNULL(T0.ACTUAL_QTY,0) * IFNULL(T4.RECEIPT_ACTUAL_PRICE, T3.ACTUAL_PRICE) ) /10000) totalPrice");
            sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL T0 ");
            sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T1 ON (T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0') ");
            sqlCon.addMainSql(" LEFT JOIN CD_M_MATERIAL T2 ON (T0.MATERIAL_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0') ");
            sqlCon.addMainSql(" LEFT OUTER JOIN ");
            sqlCon.addMainSql(" (SELECT MAX(ACTUAL_PRICE) AS ACTUAL_PRICE,MATERIAL_ONLY FROM SC_M_BILL_PURCHASE_DETAIL");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS <> '6' GROUP BY MATERIAL_ONLY ) T3");
            sqlCon.addMainSql(" ON (T0.MATERIAL_ONLY = T3.MATERIAL_ONLY) ");
            sqlCon.addMainSql(" LEFT OUTER JOIN ");
            sqlCon.addMainSql(" (SELECT MATERIAL_ID,MATERIAL_ONLY,MATERIAL_BATCH,RECEIPT_PRICE AS RECEIPT_ACTUAL_PRICE ");
            sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT T1 ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND EXISTS(");
            sqlCon.addMainSql(" SELECT 1 FROM( SELECT MIN(ROW_ID) AS ROW_ID");
            sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT WHERE DELETED_FLAG = '0' GROUP BY MATERIAL_ONLY, MATERIAL_BATCH");
            sqlCon.addMainSql(" ) A WHERE A.ROW_ID = T1.ROW_ID )) T4");
            sqlCon.addMainSql(" ON(T0.MATERIAL_ID = T4.MATERIAL_ID");
            sqlCon.addMainSql(" AND T0.MATERIAL_ONLY = T4.MATERIAL_ONLY");
            sqlCon.addMainSql(" AND T0.MATERIAL_BATCH = T4.MATERIAL_BATCH)");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' ");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_21, " AND T2.MATERIAL_FIRST_CLASS BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_50, " AND ?");

            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);

                } else {
                    sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
                    sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
                }

            }
            sqlCon.addMainSql(" GROUP BY T2.MATERIAL_FIRST_CLASS ");
        } else {
            sqlCon.addMainSql(" SELECT T2.MATERIAL_FIRST_CLASS materialFirstClass, ");
            sqlCon.addMainSql(" (SUM(IFNULL(T0.END_QTY,0) * IFNULL(T4.RECEIPT_ACTUAL_PRICE, T3.ACTUAL_PRICE) ) /10000) totalPrice");
            sqlCon.addMainSql(" FROM SC_M_MONTH_ACCOUNT T0 ");
            sqlCon.addMainSql(" LEFT JOIN HR_M_COMPANY T1 ON (T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0') ");
            sqlCon.addMainSql(" LEFT JOIN CD_M_MATERIAL T2 ON (T0.MATERIAL_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0') ");
            sqlCon.addMainSql(" LEFT OUTER JOIN ");
            sqlCon.addMainSql(" (SELECT MAX(ACTUAL_PRICE) AS ACTUAL_PRICE,MATERIAL_ONLY FROM SC_M_BILL_PURCHASE_DETAIL");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS <> '6' GROUP BY MATERIAL_ONLY ) T3");
            sqlCon.addMainSql(" ON (T0.MATERIAL_ONLY = T3.MATERIAL_ONLY) ");
            sqlCon.addMainSql(" LEFT OUTER JOIN ");
            sqlCon.addMainSql(" (SELECT MATERIAL_ID,MATERIAL_ONLY,MATERIAL_BATCH,RECEIPT_PRICE AS RECEIPT_ACTUAL_PRICE ");
            sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT T1 ");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND EXISTS(");
            sqlCon.addMainSql(" SELECT 1 FROM( SELECT MIN(ROW_ID) AS ROW_ID");
            sqlCon.addMainSql(" FROM SC_R_RECEIPT_INPUT WHERE DELETED_FLAG = '0' GROUP BY MATERIAL_ONLY, MATERIAL_BATCH");
            sqlCon.addMainSql(" ) A WHERE A.ROW_ID = T1.ROW_ID )) T4");
            sqlCon.addMainSql(" ON(T0.MATERIAL_ID = T4.MATERIAL_ID");
            sqlCon.addMainSql(" AND T0.MATERIAL_ONLY = T4.MATERIAL_ONLY");
            sqlCon.addMainSql(" AND T0.MATERIAL_BATCH = T4.MATERIAL_BATCH)");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' ");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_21, " AND T2.MATERIAL_FIRST_CLASS BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_50, " AND ?");
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
                if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
                } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ? ");
                    sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ? ");
                    sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ? ", false, true);
                }
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
            }
            if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                    AnalyticalDataConstants.END_DATE) != null) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.START_DATE = ? ");
            }
            sqlCon.addMainSql(" GROUP BY T2.MATERIAL_FIRST_CLASS ");
        }
        List<Map<String, Object>> materialFirstClassList = getList(sqlCon);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 饲料
        Map<String, Object> financeFirstFeed = new HashMap<String, Object>();
        financeFirstFeed.put("materialType", "FINANCE_FIRST_FEED");
        financeFirstFeed.put("totalPrice", 0D);
        list.add(financeFirstFeed);
        // 药物
        Map<String, Object> financeMedicine = new HashMap<String, Object>();
        financeMedicine.put("materialType", "FINANCE_MEDICINE");
        financeMedicine.put("totalPrice", 0D);
        list.add(financeMedicine);
        // 周转材料
        Map<String, Object> financeTurnoverCost = new HashMap<String, Object>();
        financeTurnoverCost.put("materialType", "FINANCE_TURNOVER_COST");
        financeTurnoverCost.put("totalPrice", 0D);
        list.add(financeTurnoverCost);
        // 制造费用
        Map<String, Object> financeProductionCost = new HashMap<String, Object>();
        financeProductionCost.put("materialType", "FINANCE_PRODUCTION_COST");
        financeProductionCost.put("totalPrice", 0D);
        list.add(financeProductionCost);

        for (Map<String, Object> materialFirstClassMap : materialFirstClassList) {
            if (SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))
                    || SupplyChianConstants.MATERIAL_FIRST_CLASS_22.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:配合料 || 物料大类:浓缩料
                Map<String, Object> map = list.get(0);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:药物
                Map<String, Object> map = list.get(1);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_40.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:周转材料
                Map<String, Object> map = list.get(2);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            } else if (SupplyChianConstants.MATERIAL_FIRST_CLASS_50.equals(Maps.getString(materialFirstClassMap, "materialFirstClass"))) {
                // 物料大类:制造费用
                Map<String, Object> map = list.get(3);
                map.put("totalPrice", BigDecimalUtil.bigDecimalAdd(Maps.getDouble(map, "totalPrice"), Maps.getDouble(materialFirstClassMap,
                        "totalPrice")));
            }
        }

        for (Map<String, Object> map : list) {
            map.put("totalPrice", new BigDecimal(Maps.getDouble(map, "totalPrice")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        return list;
    }

    public Map<String, Object> getBreedNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT COUNT(T0.ROW_ID) breedNum");
        sqlCon.addMainSql(" FROM pp_l_bill_breed T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.BREED_DATE_FIRST) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.BREED_DATE_FIRST >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.BREED_DATE_FIRST <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getDeliveryNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT COUNT(T0.ROW_ID) deliveryNum,IFNULL(ROUND(SUM(T0.HEALTHY_NUM)/COUNT(T0.ROW_ID),2),0) avgHealthyNum,IFNULL(SUM(T0.HEALTHY_NUM),0) healthyNum,");
        sqlCon.addMainSql(" IFNULL(ROUND(SUM(T0.ALIVE_LITTER_WEIGHT)/IF(IFNULL(T2.SETTING_VALUE, (");
        sqlCon.addMainSql(" SELECT SETTING_VALUE  FROM cd_m_setting_module  WHERE SETTING_CODE = 'RZBSCL' AND DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" )) = 'ON',SUM(T0.HEALTHY_NUM)+SUM(T0.WEAK_NUM),SUM(T0.HEALTHY_NUM)),2),0) avgAliveWeight,");
        sqlCon.addMainSql(
                " IFNULL(SUM(T0.WEAK_NUM),0) weekNum,IFNULL(SUM(T0.STILLBIRTH_NUM),0) stillBirthNum,IFNULL(SUM(T0.MUMMY_NUM),0) mummyNum,IFNULL(SUM(T0.MUTANT_NUM),0) mutantNum");
        sqlCon.addMainSql(" FROM pp_l_bill_delivery T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(
                " LEFT JOIN cd_m_setting T2 ON T0.FARM_ID = T2.FARM_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T2.SETTING_CODE = 'RZBSCL'");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.DELIVERY_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.DELIVERY_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.DELIVERY_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getWeanNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT COUNT(T0.ROW_ID) weanNum,IFNULL(ROUND(SUM(T0.WEAN_NUM)/COUNT(T0.ROW_ID),2),0) avgWeanNum");
        sqlCon.addMainSql(" FROM pp_l_bill_wean T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.WEAN_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.WEAN_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.WEAN_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getSaleNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.SALE_NUM,0)),0) mzNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.SALE_NUM,0)),0) fzNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('6','7','8','9','10') ,T1.SALE_NUM,0)),0) zzNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(SUM(T1.TOTAL_PRICE)/10000,0),2) salePrice ");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ON T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  ");
        sqlCon.addMainSql(" AND (T0.SALE_TYPE = '4' OR T0.SAP_SALE_TYPE IN ('1','2')) ");
        sqlCon.addMainSql(" AND (T0.SALE_STATUS <> '1' OR T0.SALE_STATUS IS NULL) ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T1.SALE_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.SALE_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.SALE_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getDieNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        SqlCon getFarmIdSqlCon = new SqlCon();
        StringBuffer sb = new StringBuffer();
        List<Map<String, Object>> companyModelList = null;
        int i = 0;
        sqlCon.addMainSql(" SELECT N0.cfDieNum,N0.byDieNum,N0.yfDieNum,N0.cfDieNum+N0.byDieNum+N0.yfDieNum allDieNum ");
        sqlCon.addMainSql(" FROM( SELECT  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF((T0.LEAVE_TYPE = 3 AND  T0.PIG_CLASS = '12' ");
        sqlCon.addMainSql(" OR (T0.PIG_CLASS = '13' AND _getSettingValueByCode(T0.FARM_ID,'RZBSCL') = 'OFF')) ");
        sqlCon.addMainSql(" OR (T0.LEAVE_TYPE = 3 AND T0.PIG_CLASS = '14'),1,0)),0) cfDieNum , ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.LEAVE_TYPE = 4 AND T0.PIG_CLASS = '15',1,0)),0) byDieNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.LEAVE_TYPE = 4 AND T0.PIG_CLASS = '16',1,0)),0) yfDieNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_leave T0  ");
        /* sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' "); */
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0'AND T0.STATUS = '1'AND T0.LEAVE_TYPE IN ('3','4')  ");
        getFarmIdSqlCon.addMainSql(" SELECT ROW_ID FROM hr_m_company WHERE DELETED_FLAG='0' AND STATUS='1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                /* sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?"); */
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND COMPANY_MARK LIKE ?");
                companyModelList = getList(getFarmIdSqlCon);
                if (companyModelList != null && !companyModelList.isEmpty()) {
                    for (i = 0; i < companyModelList.size(); i++) {
                        sb.append(Maps.getString(companyModelList.get(i), "ROW_ID"));
                        if (i != companyModelList.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                sqlCon.addCondition(sb.toString(), " AND T0.FARM_ID IN ? ", false, true);
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                /* sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                 * sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                 * sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true); */
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND COMPANY_MARK NOT LIKE ?");
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND COMPANY_MARK LIKE ?");
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND ROW_ID NOT IN ?", false, true);
                companyModelList = getList(getFarmIdSqlCon);
                if (companyModelList != null && !companyModelList.isEmpty()) {
                    for (i = 0; i < companyModelList.size(); i++) {
                        sb.append(Maps.getString(companyModelList.get(i), "ROW_ID"));
                        if (i != companyModelList.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                sqlCon.addCondition(sb.toString(), " AND T0.FARM_ID IN ? ", false, true);
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            /* sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
             * sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )"); */
            /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
            getFarmIdSqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( COMPANY_MARK = ? ");
            getFarmIdSqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR COMPANY_MARK LIKE ? )");
            companyModelList = getList(getFarmIdSqlCon);
            if (companyModelList != null && !companyModelList.isEmpty()) {
                for (i = 0; i < companyModelList.size(); i++) {
                    sb.append(Maps.getString(companyModelList.get(i), "ROW_ID"));
                    if (i != companyModelList.size() - 1) {
                        sb.append(",");
                    }
                }
            }
            sqlCon.addCondition(sb.toString(), " AND T0.FARM_ID IN ? ", false, true);
            /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.LEAVE_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.LEAVE_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.LEAVE_DATE <= ?");
        }
        sqlCon.addMainSql(" )N0 ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getChildNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT IFNULL(SUM(IF(T0.CHANGE_HOUSE_TYPE = 4,1,0)),0) toCareNum,IFNULL(SUM(IF(T0.CHANGE_HOUSE_TYPE = 6,1,0)),0) toFatNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_to_child T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.CHILD_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.CHILD_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.CHILD_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getSeedNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT COUNT(T0.ROW_ID) seedNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_seed_detail T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.SEED_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.SEED_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.SEED_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getSelectNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT COUNT(T0.ROW_ID) selectNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_select_breed T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.SELETC_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.SELETC_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.SELETC_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getBreedPigDieNum(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        SqlCon getFarmIdSqlCon = new SqlCon();
        StringBuffer sb = new StringBuffer();
        List<Map<String, Object>> companyModelList = null;
        int i = 0;
        sqlCon.addMainSql(" SELECT IFNULL(SUM(IF(T0.PIG_TYPE = 1, 1, 0)), 0) gzDieNum,IFNULL(SUM(IF(T0.PIG_TYPE = 2, 1, 0)), 0) mzDieNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_leave T0");
        /* sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' "); */
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0'AND T0.STATUS = '1'AND T0.LEAVE_TYPE = 2");
        getFarmIdSqlCon.addMainSql(" SELECT ROW_ID FROM hr_m_company WHERE DELETED_FLAG='0' AND STATUS='1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                /* sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?"); */
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND COMPANY_MARK LIKE ?");
                companyModelList = getList(getFarmIdSqlCon);
                if (companyModelList != null && !companyModelList.isEmpty()) {
                    for (i = 0; i < companyModelList.size(); i++) {
                        sb.append(Maps.getString(companyModelList.get(i), "ROW_ID"));
                        if (i != companyModelList.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                sqlCon.addCondition(sb.toString(), " AND T0.FARM_ID IN ? ", false, true);
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                /* sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                 * sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                 * sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true); */
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND COMPANY_MARK NOT LIKE ?");
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND COMPANY_MARK LIKE ?");
                getFarmIdSqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND ROW_ID NOT IN ?", false, true);
                companyModelList = getList(getFarmIdSqlCon);
                if (companyModelList != null && !companyModelList.isEmpty()) {
                    for (i = 0; i < companyModelList.size(); i++) {
                        sb.append(Maps.getString(companyModelList.get(i), "ROW_ID"));
                        if (i != companyModelList.size() - 1) {
                            sb.append(",");
                        }
                    }
                }
                sqlCon.addCondition(sb.toString(), " AND T0.FARM_ID IN ? ", false, true);
                /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            /* sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
             * sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )"); */
            getFarmIdSqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( COMPANY_MARK = ? ");
            getFarmIdSqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR COMPANY_MARK LIKE ? )");
            companyModelList = getList(getFarmIdSqlCon);
            if (companyModelList != null && !companyModelList.isEmpty()) {
                for (i = 0; i < companyModelList.size(); i++) {
                    sb.append(Maps.getString(companyModelList.get(i), "ROW_ID"));
                    if (i != companyModelList.size() - 1) {
                        sb.append(",");
                    }
                }
            }
            sqlCon.addCondition(sb.toString(), " AND T0.FARM_ID IN ? ", false, true);
            /********************************** 2017-8-17 大生物安全sql优化 *******************************************/
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.LEAVE_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.LEAVE_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.LEAVE_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getPSY(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IFNULL(SUM(T0.WEAN_NUM),0) sowSum,TIMESTAMPDIFF(DAY,DATE_ADD(DATE(NOW()),INTERVAL -1 YEAR),NOW()) dayLength");
        sqlCon.addMainSql(" FROM PP_L_BILL_WEAN T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.WEAN_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.WEAN_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.WEAN_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getBreedCondition(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IFNULL(SUM(IF(T0.LAST_STATUS = '4',1,0)),0) hbBreed,IFNULL(SUM(IF(T0.LAST_STATUS <> '4',1,0)),0) scBreed ");
        sqlCon.addMainSql(" FROM pp_l_bill_breed T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.BREED_DATE_FIRST) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.BREED_DATE_FIRST >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.BREED_DATE_FIRST <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getPregnancyCondition(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT IFNULL(SUM(IF(T0.PREGNANCY_RESULT = '4',1,0)),0) fqNum,IFNULL(SUM(IF(T0.PREGNANCY_RESULT = '5',1,0)),0) lcNum,IFNULL(SUM(IF(T0.PREGNANCY_RESULT = '6',1,0)),0) khNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_pregnancy_check T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.PREGNANCY_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.PREGNANCY_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.PREGNANCY_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    public Map<String, Object> getSeedCondition(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IFNULL(SUM(IF(T0.SEX = '1',1,0)),0) gSeed,IFNULL(SUM(IF(T0.SEX = '2',1,0)),0) mSeed ");
        sqlCon.addMainSql(" FROM pp_l_bill_seed_detail T0");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.SEED_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.SEED_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.SEED_DATE <= ?");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public Map<String, Object> searchPerformanceAliveNum(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 产活仔
        MonthPerformanceModel monthPerformanceModel = monthPerformanceMapper.searchProductionBySurvive(startDate, endDate, getCompanyMark());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("name", "aliveNum");
        if (monthPerformanceModel != null) {
            resultMap.put("sysIndex", monthPerformanceModel.getSystemIndex());
            resultMap.put("notes", monthPerformanceModel.getNotes());
        } else {
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformanceDeliveryRate(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 分娩率
        MonthPerformanceModel monthPerformanceModel = new MonthPerformanceModel();
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            monthPerformanceModel = monthPerformanceMapper.searchProductionByDeliver(startDate, endDate, getCompanyMark());
        } else {
            monthPerformanceModel = monthPerformanceMapper.searchProductionByDeliverGroup(startDate, endDate, Maps.getString(inputMap,
                    AnalyticalDataConstants.ORIGIN_TYPE));
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("name", "deliveryRate");
        if (monthPerformanceModel != null) {
            resultMap.put("sysIndex", monthPerformanceModel.getSystemIndex());
            resultMap.put("notes", monthPerformanceModel.getNotes());
        } else {
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformanceBackFat(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 膘情
        MonthPerformanceModel monthPerformanceModel = monthPerformanceMapper.searchProductionByFatness(startDate, endDate, getCompanyMark());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("name", "backFat");
        if (monthPerformanceModel != null) {
            resultMap.put("sysIndex", monthPerformanceModel.getSystemIndex());
            resultMap.put("notes", monthPerformanceModel.getNotes());
        } else {
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformanceAvgWeanNum(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        /********************************** 2017-8-9 微信端断奶头数修改 start **************************************/
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("startDate", startDate);
        parameterMap.put("endDate", endDate);
        parameterMap.put("companyMark", getCompanyMark());
        Map<String, Object> resultWjdnsAndWjdnwzMap = monthPerformanceMapper.searchProductionByWjdnsAndWjdnwz(parameterMap);
        if (resultWjdnsAndWjdnwzMap == null) {
            resultMap.put("name", "avgWeanNum");
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        } else {
            resultMap.put("name", "avgWeanNum");
            resultMap.put("sysIndex", Maps.getDouble(resultWjdnsAndWjdnwzMap, "WEAN_PIG_NUM"));
            resultMap.put("notes", Maps.getString(resultWjdnsAndWjdnwzMap, "DN_NOTES"));
        }

        /********************************** 2017-8-9 微信端断奶头数修改 start **************************************/
        // 窝均断奶头数
        /* MonthPerformanceModel mnthPerformanceModel_ = monthPerformanceMapper.searchMinPorkBirthDate(startDate, endDate, getCompanyMark(),
         * PigConstants.CHANGE_HOUSE_TYPE_CHI, PigConstants.HOUSE_CLASS_DELIVERY);
         * Map<String, Object> resultMap = new HashMap<String, Object>();
         * if (mnthPerformanceModel_ == null) {
         * resultMap.put("name", "avgWeanNum");
         * resultMap.put("sysIndex", 0);
         * resultMap.put("notes", "");
         * } else {
         * MonthPerformanceModel monthPerformanceModel = monthPerformanceMapper.searchProductionByWeanNum(startDate, endDate, getCompanyMark(),
         * mnthPerformanceModel_.getNotes());
         * resultMap.put("name", "avgWeanNum");
         * resultMap.put("sysIndex", monthPerformanceModel.getSystemIndex());
         * resultMap.put("notes", monthPerformanceModel.getNotes());
         * } */
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformanceAvgWeanWeight(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        /********************************** 2017-8-9 微信端断奶窝重修改 start **************************************/
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        Map<String, Object> resultWjdnsAndWjdnwzMap = null;
        parameterMap.put("startDate", startDate);
        parameterMap.put("endDate", endDate);
        parameterMap.put("companyMark", getCompanyMark());
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            parameterMap.put("origin", Maps.getMap(inputMap, AnalyticalDataConstants.ORIGIN_TYPE));
            resultWjdnsAndWjdnwzMap = monthPerformanceMapper.searchProductionByWjdnsAndWjdnwzGroup(parameterMap);
            resultMap.put("name", "avgWeanWeight");
            resultMap.put("sysIndex", Maps.getDouble(resultWjdnsAndWjdnwzMap, "WEAN_DN_WEIGHT"));
            resultMap.put("notes", Maps.getString(resultWjdnsAndWjdnwzMap, "DNW_NOTES"));
        } else {
            resultWjdnsAndWjdnwzMap = monthPerformanceMapper.searchProductionByWjdnsAndWjdnwz(parameterMap);
            resultMap.put("name", "avgWeanWeight");
            resultMap.put("sysIndex", Maps.getDouble(resultWjdnsAndWjdnwzMap, "WEAN_DN_WEIGHT"));
            resultMap.put("notes", Maps.getString(resultWjdnsAndWjdnwzMap, "DNW_NOTES"));
        }
        /********************************** 2017-8-9 微信端断奶窝重修改 start **************************************/

        // 窝均断奶重
        // MonthPerformanceModel mnthPerformanceModel_ = new MonthPerformanceModel();
        // if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
        // mnthPerformanceModel_ = monthPerformanceMapper.searchMinPorkBirthDateGroup(startDate, endDate, Maps.getString(inputMap,
        // AnalyticalDataConstants.ORIGIN_TYPE), PigConstants.CHANGE_HOUSE_TYPE_CHI, PigConstants.HOUSE_CLASS_DELIVERY);
        // } else {
        // mnthPerformanceModel_ = monthPerformanceMapper.searchMinPorkBirthDate(startDate, endDate, getCompanyMark(),
        // PigConstants.CHANGE_HOUSE_TYPE_CHI, PigConstants.HOUSE_CLASS_DELIVERY);
        // }
        // Map<String, Object> resultMap = new HashMap<String, Object>();
        // if (mnthPerformanceModel_ == null) {
        // resultMap.put("name", "avgWeanWeight");
        // resultMap.put("sysIndex", 0);
        // resultMap.put("notes", "");
        // } else {
        // if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
        // MonthPerformanceModel monthPerformanceModel1 = monthPerformanceMapper.searchProductionByWeanNumGroup(startDate, endDate, Maps
        // .getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE), mnthPerformanceModel_.getNotes());
        // MonthPerformanceModel monthPerformanceModel2 = monthPerformanceMapper.searchProductionByWeanGroup(startDate, endDate, Maps.getString(
        // inputMap, AnalyticalDataConstants.ORIGIN_TYPE), getFarmId(), monthPerformanceModel1.getSystemIndex());
        // resultMap.put("name", "avgWeanWeight");
        // resultMap.put("sysIndex", monthPerformanceModel2.getSystemIndex());
        // resultMap.put("notes", monthPerformanceModel2.getNotes());
        // } else {
        // MonthPerformanceModel monthPerformanceModel3 = monthPerformanceMapper.searchProductionByWeanNum(startDate, endDate, getCompanyMark(),
        // mnthPerformanceModel_.getNotes());
        // MonthPerformanceModel monthPerformanceModel4 = monthPerformanceMapper.searchProductionByWean(startDate, endDate, getCompanyMark(),
        // getFarmId(), monthPerformanceModel3.getSystemIndex());
        // resultMap.put("name", "avgWeanWeight");
        // resultMap.put("sysIndex", monthPerformanceModel4.getSystemIndex());
        // resultMap.put("notes", monthPerformanceModel4.getNotes());
        // }
        // }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformanceToCareAliveRate(Map<String, Object> inputMap) throws Exception {
        String companyMark = getCompanyMark();
        String supCompanyMark = null;
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 保育成活率
        String companyCode = getCompanyCode();
        if ("C1034".equals(companyCode)) {
            Map<String, String> supCompanyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(getSupCompanyId()));
            supCompanyMark = Maps.getString(supCompanyInfoMap, "COMPANY_MARK");
        } else {
            supCompanyMark = companyMark;
        }
        MonthPerformanceModel mnthPerformanceModel = monthPerformanceMapper.searchProductionByChildSurvive(startDate, endDate, supCompanyMark,
                companyMark);

        Map<String, Object> resultMap = new HashMap<String, Object>();

        resultMap.put("name", "toCareAliveRate");
        if (mnthPerformanceModel != null) {
            resultMap.put("sysIndex", mnthPerformanceModel.getSystemIndex());
            resultMap.put("notes", mnthPerformanceModel.getNotes());
        } else {
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformance7030(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 7030
        MonthPerformanceModel mnthPerformanceModel = new MonthPerformanceModel();
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            mnthPerformanceModel = monthPerformanceMapper.searchProductionByChildGroup(startDate, endDate, Maps.getString(inputMap,
                    AnalyticalDataConstants.ORIGIN_TYPE), getFarmId());
        } else {
            mnthPerformanceModel = monthPerformanceMapper.searchProductionByChild(startDate, endDate, getCompanyMark(), getFarmId());
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (mnthPerformanceModel == null) {
            resultMap.put("name", "7030");
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        } else {
            resultMap.put("name", "7030");
            resultMap.put("sysIndex", mnthPerformanceModel.getSystemIndex());
            resultMap.put("notes", mnthPerformanceModel.getNotes());
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformanceToFattenAliveRate(Map<String, Object> inputMap) throws Exception {
        String companyMark = getCompanyMark();
        String supCompanyMark = null;
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 育肥成活率
        String companyCode = getCompanyCode();
        if ("C1036".equals(companyCode)) {
            Map<String, String> supCompanyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(getSupCompanyId()));
            supCompanyMark = Maps.getString(supCompanyInfoMap, "COMPANY_MARK");
        } else {
            supCompanyMark = companyMark;
        }
        MonthPerformanceModel mnthPerformanceModel = monthPerformanceMapper.searchProductionByFatSurvive(startDate, endDate, supCompanyMark,
                companyMark);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("name", "toFattenAliveRate");
        if (mnthPerformanceModel != null) {
            resultMap.put("sysIndex", mnthPerformanceModel.getSystemIndex());
            resultMap.put("notes", mnthPerformanceModel.getNotes());
        } else {
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> searchPerformance110KGOut(Map<String, Object> inputMap) throws Exception {
        String startDate = Maps.getString(inputMap, AnalyticalDataConstants.START_DATE);
        String endDate = Maps.getString(inputMap, AnalyticalDataConstants.END_DATE);
        // 110KG出栏
        MonthPerformanceModel mnthPerformanceModel = new MonthPerformanceModel();
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
            mnthPerformanceModel = monthPerformanceMapper.searchProductionByFatField(startDate, endDate, getCompanyMark(), getFarmId());
        } else {
            mnthPerformanceModel = monthPerformanceMapper.searchProductionByFatFieldGroup(startDate, endDate, Maps.getString(inputMap,
                    AnalyticalDataConstants.ORIGIN_TYPE), getFarmId());
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("name", "110KGOut");
        if (mnthPerformanceModel != null) {
            resultMap.put("sysIndex", mnthPerformanceModel.getSystemIndex());
            resultMap.put("notes", mnthPerformanceModel.getNotes());
        } else {
            resultMap.put("sysIndex", 0);
            resultMap.put("notes", "");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> search7DayWeanBreedRate(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT IF(T5.allPig IS NULL,0,ROUND(T5.hasBreedNum/T5.allPig*100,2)) weanBreedRate ");
        sqlCon.addMainSql(" FROM ( SELECT ");
        sqlCon.addMainSql(
                " SUM(IF(DATEDIFF(T4.BREED_DATE_FIRST, T3.WEAN_DATE) <= 7 AND DATEDIFF(T4.BREED_DATE_FIRST, T3.WEAN_DATE) > 0,1,0)) hasBreedNum, ");
        sqlCon.addMainSql(" COUNT(DISTINCT(T3.PIG_ID)) allPig ");
        sqlCon.addMainSql(" FROM ( SELECT T0.PIG_ID,T0.WEAN_DATE ");
        sqlCon.addMainSql(" FROM pp_l_bill_wean T0 ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG ='0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T0.WEAN_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T0.WEAN_DATE >= DATE_SUB(?,INTERVAL 7 DAY)");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T0.WEAN_DATE <= DATE_SUB(?,INTERVAL 7 DAY)");
        }
        sqlCon.addMainSql(" )T3 ");
        sqlCon.addMainSql(" INNER JOIN pp_l_bill_breed T4 ON T3.PIG_ID = T4.PIG_ID AND T4.DELETED_FLAG = '0' AND T4.STATUS = '1' ");
        sqlCon.addMainSql(" )T5 ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public Map<String, Object> searchSaleCondition(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();

        sqlCon.addMainSql(" SELECT ");
        // 苗猪销售
        sqlCon.addMainSql(" N0.mzSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalWeight/N0.mzSaleNum,0),2) mzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalPrice/N0.mzSaleNum,0),2) mzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalPrice/N0.mzTotalWeight,0),2) mzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalPrice/10000,0),2) mzTotalPrice, ");
        // 肥猪销售
        sqlCon.addMainSql(" N0.fzSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalWeight/N0.fzSaleNum,0),2) fzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalPrice/N0.fzSaleNum,0),2) fzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalPrice/N0.fzTotalWeight,0),2) fzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalPrice/10000,0),2) fzTotalPrice, ");
        // 残次猪销售
        sqlCon.addMainSql(" N0.cczSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.cczTotalWeight/N0.cczSaleNum,0),2) cczAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.cczTotalPrice/N0.cczSaleNum,0),2) cczAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.cczTotalPrice/N0.cczTotalWeight,0),2) cczAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.cczTotalPrice/10000,0),2) cczTotalPrice, ");
        // 种猪销售
        sqlCon.addMainSql(" N0.zzSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalWeight/N0.zzSaleNum,0),2) zzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalPrice/N0.zzSaleNum,0),2) zzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalPrice/N0.zzTotalWeight,0),2) zzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalPrice/10000,0),2) zzTotalPrice, ");
        // 公猪淘汰
        sqlCon.addMainSql(" N0.gzObsoleteNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.gzTotalWeight/N0.gzObsoleteNum,0),2) gzObsoleteAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.gzTotalPrice/N0.gzObsoleteNum,0),2) gzObsoleteAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.gzTotalPrice/N0.gzTotalWeight,0),2) gzObsoleteAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.gzTotalPrice/10000,0),2) gzObsoleteTotalPrice, ");
        // 母猪淘汰
        sqlCon.addMainSql(" N0.mzObsoleteNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzObsoleteTotalWeight/N0.mzObsoleteNum,0),2) mzObsoleteAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzObsoleteTotalPrice/N0.mzObsoleteNum,0),2) mzObsoleteAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzObsoleteTotalPrice/N0.mzObsoleteTotalWeight,0),2) mzObsoleteAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzObsoleteTotalPrice/10000,0),2) mzObsoleteTotalPrice, ");
        // 后备淘汰
        sqlCon.addMainSql(" N0.hbObsoleteNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbObsoleteTotalWeight/N0.hbObsoleteNum,0),2) hbObsoleteAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbObsoleteTotalPrice/N0.hbObsoleteNum,0),2) hbObsoleteAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbObsoleteTotalPrice/N0.hbObsoleteTotalWeight,0),2) hbObsoleteAvgWeightrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbObsoleteTotalPrice/10000,0),2) hbObsoleteTotalPrice, ");
        // 自宰
        sqlCon.addMainSql(" N0.selfNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.selfTotalWeight/N0.selfNum,0),2) selfAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.selfTotalPrice/N0.selfNum,0),2) selfAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.selfTotalPrice/N0.selfTotalWeight,0),2) selfAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.selfTotalPrice/10000,0),2) selfTotalPrice, ");
        // 合计
        sqlCon.addMainSql(" IFNULL(N0.mzSaleNum +  N0.fzSaleNum + N0.cczSaleNum + N0.zzSaleNum,0) saleTotalNum, ");
        sqlCon.addMainSql(" IFNULL(N0.gzObsoleteNum +  N0.mzObsoleteNum + N0.hbObsoleteNum + N0.selfNum,0) obsoleteTotalNum, ");
        sqlCon.addMainSql(" ROUND(N0.totalNum/10000,2) totalNum, ");
        sqlCon.addMainSql(" ROUND(N0.totalPrice/100000000,2) totalPrice ");

        sqlCon.addMainSql(" FROM( ");
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.SALE_NUM,0)),0) mzSaleNum,  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.TOTAL_WEIGHT,0)),0) mzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.TOTAL_PRICE,0)),0) mzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.SALE_NUM,0)),0) fzSaleNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.TOTAL_WEIGHT,0)),0) fzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.TOTAL_PRICE,0)),0) fzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '5',T1.SALE_NUM,0)),0) cczSaleNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '5',T1.TOTAL_WEIGHT,0)),0) cczTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '5',T1.TOTAL_PRICE,0)),0) cczTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('8','9','10'),T1.SALE_NUM,0)),0) zzSaleNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('8','9','10'),T1.TOTAL_WEIGHT,0)),0) zzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('8','9','10'),T1.TOTAL_PRICE,0)),0) zzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '1',T1.SALE_NUM,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '1',T1.SALE_NUM,0)),0) gzObsoleteNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_WEIGHT,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_WEIGHT,0)),0) gzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_PRICE,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_PRICE,0)),0) gzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '2',T1.SALE_NUM,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '2',T1.SALE_NUM,0)),0) mzObsoleteNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_WEIGHT,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_WEIGHT,0)),0) mzObsoleteTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_PRICE,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_PRICE,0)),0) mzObsoleteTotalPrice,");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE IN ('6','7'),T1.SALE_NUM,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('6','7'),T1.SALE_NUM,0)),0) hbObsoleteNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE IN ('6','7'),T1.TOTAL_WEIGHT,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('6','7'),T1.TOTAL_WEIGHT,0)),0) hbObsoleteTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE IN ('6','7'),T1.TOTAL_PRICE,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('6','7'),T1.TOTAL_PRICE,0)),0) hbObsoleteTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '4' ,T1.SALE_NUM,0)),0) selfNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '4' ,T1.TOTAL_WEIGHT,0)),0) selfTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '4' ,T1.TOTAL_PRICE,0)),0) selfTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(T1.SALE_NUM),0) totalNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(T1.TOTAL_PRICE),0) totalPrice ");

        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ON T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND (T0.SALE_TYPE = '4' OR T0.SAP_SALE_TYPE = 2 ) ");
        sqlCon.addMainSql(" AND (T0.SALE_STATUS <> '1' OR T0.SALE_STATUS IS NULL) ");

        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (!Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T1.SALE_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.SALE_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.SALE_DATE <= ? ");
        }
        sqlCon.addMainSql(" ) N0 ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public Map<String, Object> searchSaleConditionInsideAndAllocation(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT  N0.mzSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalWeight/N0.mzSaleNum,0),2) mzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalPrice/N0.mzSaleNum,0),2) mzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalPrice/N0.mzTotalWeight,0),2) mzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.mzTotalPrice/10000,0),2) mzTotalPrice, ");
        sqlCon.addMainSql(" N0.fzSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalWeight/N0.fzSaleNum,0),2) fzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalPrice/N0.fzSaleNum,0),2) fzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalPrice/N0.fzTotalWeight,0),2) fzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.fzTotalPrice/10000,0),2) fzTotalPrice, ");
        sqlCon.addMainSql(" N0.zzSaleNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalWeight/N0.zzSaleNum,0),2) zzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalPrice/N0.zzSaleNum,0),2) zzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalPrice/N0.zzTotalWeight,0),2) zzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.zzTotalPrice/10000,0),2) zzTotalPrice, ");
        sqlCon.addMainSql(" N0.scgzNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scgzTotalWeight/N0.scgzNum,0),2) scgzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scgzTotalPrice/N0.scgzNum,0),2) scgzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scgzTotalPrice/N0.scgzTotalWeight,0),2) scgzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scgzTotalPrice/10000,0),2) scgzTotalPrice, ");
        sqlCon.addMainSql(" N0.scmzNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scmzTotalWeight/N0.scmzNum,0),2) scmzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scmzTotalPrice/N0.scmzNum,0),2) scmzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scmzTotalPrice/N0.scmzTotalWeight,0),2) scmzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.scmzTotalPrice/10000,0),2) scmzTotalPrice, ");
        sqlCon.addMainSql(" N0.hbgzNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbgzTotalWeight/N0.hbgzNum,0),2) hbgzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbgzTotalPrice/N0.hbgzNum,0),2) hbgzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbgzTotalPrice/N0.hbgzTotalWeight,0),2) hbgzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbgzTotalPrice/10000,0),2) hbgzTotalPrice, ");
        sqlCon.addMainSql(" N0.hbmzNum, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbmzTotalWeight/N0.hbmzNum,0),2) hbmzAvgWeight, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbmzTotalPrice/N0.hbmzNum,0),2) hbmzAvgNumPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbmzTotalPrice/N0.hbmzTotalWeight,0),2) hbmzAvgWeightPrice, ");
        sqlCon.addMainSql(" ROUND(IFNULL(N0.hbmzTotalPrice/10000,0),2) hbmzTotalPrice ");
        sqlCon.addMainSql(" FROM(  ");
        sqlCon.addMainSql(" SELECT  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.SALE_NUM,0)),0) mzSaleNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.TOTAL_WEIGHT,0)),0) mzTotalWeight,  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '4',T1.TOTAL_PRICE,0)),0) mzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.SALE_NUM,0)),0) fzSaleNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.TOTAL_WEIGHT,0)),0) fzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '1' AND T1.SALE_DESCRIBE = '3',T1.TOTAL_PRICE,0)),0) fzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('8','9','10'),T1.SALE_NUM,0)),0) zzSaleNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('8','9','10'),T1.TOTAL_WEIGHT,0)),0) zzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE IN ('8','9','10'),T1.TOTAL_PRICE,0)),0) zzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '1',T1.SALE_NUM,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '1',T1.SALE_NUM,0)),0) scgzNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_WEIGHT,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_WEIGHT,0)),0) scgzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_PRICE,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '1',T1.TOTAL_PRICE,0)),0) scgzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '2',T1.SALE_NUM,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '2',T1.SALE_NUM,0)),0) scmzNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_WEIGHT,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_WEIGHT,0)),0) scmzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_PRICE,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '2',T1.TOTAL_PRICE,0)),0) scmzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '6' ,T1.SALE_NUM,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '6' ,T1.SALE_NUM,0)),0) hbgzNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '6' ,T1.TOTAL_WEIGHT,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '6' ,T1.TOTAL_WEIGHT,0)),0) hbgzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '6' ,T1.TOTAL_PRICE,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '6' ,T1.TOTAL_PRICE,0)),0) hbgzTotalPrice, ");

        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '7' ,T1.SALE_NUM,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '7' ,T1.SALE_NUM,0)),0) hbmzNum, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '7' ,T1.TOTAL_WEIGHT,0)),0) +  ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '7' ,T1.TOTAL_WEIGHT,0)),0) hbmzTotalWeight, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '3' AND T1.SALE_DESCRIBE = '7' ,T1.TOTAL_PRICE,0)),0) + ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(T0.SALE_TYPE = '2' AND T1.SALE_DESCRIBE = '7' ,T1.TOTAL_PRICE,0)),0) hbmzTotalPrice ");

        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ON T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1'  ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T0.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'  ");
        sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.SAP_SALE_TYPE), " AND  T0.SAP_SALE_TYPE = ?");
        sqlCon.addMainSql(" AND (T0.SALE_STATUS <> '1' OR T0.SALE_STATUS IS NULL)  ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (!Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T1.SALE_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.SALE_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.SALE_DATE <= ? ");
        }
        sqlCon.addMainSql(" ) N0 ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public Map<String, Object> searchSaleNotBalanceNum(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        } else {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) == null) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT SUM(T1.SALE_NUM) notBalanceNum ");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale T0 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_bill_pig_sale_detail T1 ON T0.BILL_ID = T1.BILL_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T2 ON T1.FARM_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(
                " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.SAP_SALE_TYPE = '2' AND T0.SALE_STATUS = '1' AND T0.SALE_TYPE = '1' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            if (!Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK).equals("allCustomer")) {
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
                sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) == null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND DATE(T1.SALE_DATE) = ?");
        } else if (Maps.getString(inputMap, AnalyticalDataConstants.START_DATE) != null && Maps.getString(inputMap,
                AnalyticalDataConstants.END_DATE) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.START_DATE), " AND T1.SALE_DATE >= ?");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.END_DATE), " AND T1.SALE_DATE <= ? ");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public Map<String, Object> searchSupplyOverdueRemind(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT  IFNULL(SUM(IF(DATEDIFF(T0.EFFECTIVE_DATE,NOW()) <= 60 AND DATEDIFF(T0.EFFECTIVE_DATE,NOW()) >= 0,( ");
        sqlCon.addMainSql(" T0.ACTUAL_QTY * (IFNULL((SELECT A0.ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL A0 ");
        sqlCon.addMainSql(
                " WHERE A0.DELETED_FLAG = '0' AND A0.STATUS = '1' AND A0.MATERIAL_ONLY = T0.MATERIAL_ONLY LIMIT 1 ),0))),0)),0) less60TotalPrice, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(DATEDIFF(T0.EFFECTIVE_DATE,NOW()) > 60 AND  DATEDIFF(T0.EFFECTIVE_DATE,NOW()) <= 90,( ");
        sqlCon.addMainSql(" T0.ACTUAL_QTY * (IFNULL((SELECT A0.ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL A0 ");
        sqlCon.addMainSql(
                " WHERE A0.DELETED_FLAG = '0' AND A0.STATUS = '1' AND A0.MATERIAL_ONLY = T0.MATERIAL_ONLY LIMIT 1),0))),0)),0) less90TotalPrice, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF(DATEDIFF(T0.EFFECTIVE_DATE,NOW()) < 0,(");
        sqlCon.addMainSql(
                " T0.ACTUAL_QTY * (IFNULL((SELECT A0.ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL A0 WHERE A0.DELETED_FLAG = '0' AND A0.STATUS = '1' AND A0.MATERIAL_ONLY = T0.MATERIAL_ONLY LIMIT 1),0))),0)),0) hasTotalPrice ");
        sqlCon.addMainSql(" FROM sc_m_store_material T0 ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public List<Map<String, Object>> searchSupplyOverdueRemindDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT B.materialName,B.actualQty,IFNULL(B.actualQty * B.actualPrice,0) totalPrice,B.inputDate,B.effectiveDate ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql(" SELECT T2.MATERIAL_NAME materialName,T0.ACTUAL_QTY actualQty, ");
        sqlCon.addMainSql(" (SELECT A0.ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL A0 WHERE A0.DELETED_FLAG = '0' AND A0.STATUS = '1' ");
        sqlCon.addMainSql(" AND A0.MATERIAL_ONLY = T0.MATERIAL_ONLY LIMIT 1) AS actualPrice, ");
        sqlCon.addMainSql(" (SELECT MIN(BILL_DATE) FROM SC_M_BILL_INPUT_DETAIL WHERE DELETED_FLAG = '0' AND MATERIAL_ONLY = T0.MATERIAL_ONLY  ");
        sqlCon.addMainSql(" AND MATERIAL_BATCH = T0.MATERIAL_BATCH AND INPUT_WAREHOUSE_ID = T0.WAREHOUSE_ID ");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE, " AND (EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " OR EVENT_CODE = ?)");
        sqlCon.addMainSql(" ) AS inputDate, ");
        sqlCon.addMainSql(" T0.EFFECTIVE_DATE effectiveDate");
        sqlCon.addMainSql(" FROM sc_m_store_material T0 ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" LEFT JOIN cd_m_material T2 ON T0.MATERIAL_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.ACTUAL_QTY > 0 ");
        if (Maps.getString(inputMap, "remianType").equals("less60")) {
            sqlCon.addMainSql(" AND DATEDIFF(T0.EFFECTIVE_DATE,NOW()) >= 0 ");
            sqlCon.addMainSql(" AND DATEDIFF(T0.EFFECTIVE_DATE,NOW()) <= 60 ");
        } else if (Maps.getString(inputMap, "remianType").equals("less90")) {
            sqlCon.addMainSql(" AND DATEDIFF(T0.EFFECTIVE_DATE,NOW()) > 60 ");
            sqlCon.addMainSql(" AND DATEDIFF(T0.EFFECTIVE_DATE,NOW()) <= 90 ");
        } else if (Maps.getString(inputMap, "remianType").equals("hasOver")) {
            sqlCon.addMainSql(" AND DATEDIFF(T0.EFFECTIVE_DATE,NOW()) < 0 ");
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T1.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T1.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T1.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T1.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T1.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" )B ");
        return getList(sqlCon);
    }

    @Override
    public Map<String, Object> searchSupplyUsedRemind(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT  IFNULL(SUM(IF (B.diff >= 60 AND B.diff < 90,B.batchTotalPrice,0)),0) over60TotalPrice, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF (B.diff >= 90 AND B.diff < 180,B.batchTotalPrice,0)),0) over90TotalPrice, ");
        sqlCon.addMainSql(" IFNULL(SUM(IF (B.diff >= 180,B.batchTotalPrice,0)),0) over180TotalPrice ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql(" SELECT A.materialId,MAX(A.lastUseDate) AS lastUseDate,MIN(A.inputDate) AS inputDate,SUM(A.actualQty) AS actualQty, ");
        sqlCon.addMainSql(" SUM(A.batchTotalPrice) AS batchTotalPrice,DATEDIFF(CURDATE(),IFNULL(MAX(A.lastUseDate),MIN(A.inputDate))) AS diff ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql(" SELECT  (SELECT MAX(BILL_DATE) FROM SC_M_BILL_OUTPUT_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '3' ");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH  ");
        sqlCon.addMainSql(" AND OUTPUT_WAREHOUSE_ID = T1.WAREHOUSE_ID) AS lastUseDate  ");
        sqlCon.addMainSql(" ,(SELECT MIN(BILL_DATE) FROM SC_M_BILL_INPUT_DETAIL ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND MATERIAL_ONLY = T1.MATERIAL_ONLY ");
        sqlCon.addMainSql(" AND MATERIAL_BATCH = T1.MATERIAL_BATCH AND INPUT_WAREHOUSE_ID = T1.WAREHOUSE_ID ");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE, " AND (EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " OR EVENT_CODE = ?)");
        sqlCon.addMainSql(" ) AS inputDate,T1.MATERIAL_ID AS materialId, ");
        sqlCon.addMainSql(" T1.ACTUAL_QTY AS actualQty,(T1.ACTUAL_QTY*T3.ACTUAL_PRICE) AS batchTotalPrice ");
        sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL T1 ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T2 ON(T2.DELETED_FLAG = '0' AND T1.FARM_ID = T2.ROW_ID) ");
        sqlCon.addMainSql(" LEFT OUTER JOIN (SELECT MAX(ACTUAL_PRICE) AS ACTUAL_PRICE ,MATERIAL_ONLY ");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS <> '6' GROUP BY MATERIAL_ONLY) T3 ");
        sqlCon.addMainSql(" ON (T1.MATERIAL_ONLY = T3.MATERIAL_ONLY) ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.ACTUAL_QTY <> 0 ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" ) A ");
        sqlCon.addMainSql(" GROUP BY A.materialId ");
        sqlCon.addMainSql(" ) B ");
        List<Map<String, Object>> list = getList(sqlCon);
        return list.get(0);
    }

    @Override
    public List<Map<String, Object>> searchSupplyUsedRemindDetail(Map<String, Object> inputMap) throws Exception {
        if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.IS_GROUP).equals("false")) {
                inputMap.put(AnalyticalDataConstants.INPUT_COMPANY_MARK, getCompanyMark());
            }
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT B1.MATERIAL_NAME materialName,B.* ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql(" SELECT A.materialId ");
        sqlCon.addMainSql(" ,MAX(A.lastUseDate) AS lastUseDate ");
        sqlCon.addMainSql(" ,MIN(A.inputDate) AS inputDate ");
        sqlCon.addMainSql(" ,SUM(A.actualQty) AS actualQty ");
        sqlCon.addMainSql(" ,SUM(A.batchTotalPrice) AS batchTotalPrice ");
        sqlCon.addMainSql(" ,DATEDIFF(CURDATE(),IFNULL(MAX(A.lastUseDate),MIN(A.inputDate))) AS diff ");
        sqlCon.addMainSql(" FROM ( ");
        sqlCon.addMainSql(" SELECT ");
        sqlCon.addMainSql(" (SELECT MAX(BILL_DATE) FROM SC_M_BILL_OUTPUT_DETAIL ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" AND STATUS = '3' ");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY ");
        sqlCon.addMainSql(" AND MATERIAL_BATCH = T1.MATERIAL_BATCH ");
        sqlCon.addMainSql(" AND OUTPUT_WAREHOUSE_ID = T1.WAREHOUSE_ID) AS lastUseDate ");
        sqlCon.addMainSql(" ,(SELECT MIN(BILL_DATE) FROM SC_M_BILL_INPUT_DETAIL ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY ");
        sqlCon.addMainSql(" AND MATERIAL_BATCH = T1.MATERIAL_BATCH ");
        sqlCon.addMainSql(" AND INPUT_WAREHOUSE_ID = T1.WAREHOUSE_ID ");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE, " AND (EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " OR EVENT_CODE = ?) ");
        sqlCon.addMainSql(" ) AS inputDate ");
        sqlCon.addMainSql(" ,T1.MATERIAL_ID AS materialId ");
        sqlCon.addMainSql(" ,T1.ACTUAL_QTY AS actualQty ");
        sqlCon.addMainSql(" ,(T1.ACTUAL_QTY*T3.ACTUAL_PRICE) AS batchTotalPrice ");
        sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL T1 ");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T2 ");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" AND T1.FARM_ID = T2.ROW_ID) ");
        sqlCon.addMainSql(" LEFT OUTER JOIN ");
        sqlCon.addMainSql(" (SELECT MAX(ACTUAL_PRICE) AS ACTUAL_PRICE ,MATERIAL_ONLY ");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" AND STATUS <> '6' ");
        sqlCon.addMainSql(" GROUP BY MATERIAL_ONLY ");
        sqlCon.addMainSql(" ) T3 ");
        sqlCon.addMainSql(" ON (T1.MATERIAL_ONLY = T3.MATERIAL_ONLY) ");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' ");
        sqlCon.addMainSql(" AND T1.ACTUAL_QTY <> 0 ");
        if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE) != null) {
            if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_XNFEED)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK LIKE ?");
            } else if (Maps.getString(inputMap, AnalyticalDataConstants.ORIGIN_TYPE).equals(AnalyticalDataConstants.ORIGIN_TYPE_CUSTOMER)) {
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_XNFEED + ",%", " AND T2.COMPANY_MARK NOT LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.COMPANY_MARK_OTHER + ",%", " AND T2.COMPANY_MARK LIKE ?");
                sqlCon.addCondition(AnalyticalDataConstants.EXCLUDE_FARM_ID, " AND T2.ROW_ID NOT IN ?", false, true);
            }
        }
        if (Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) != null) {
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK), " AND ( T2.COMPANY_MARK = ? ");
            sqlCon.addCondition(Maps.getString(inputMap, AnalyticalDataConstants.INPUT_COMPANY_MARK) + ",%", " OR T2.COMPANY_MARK LIKE ? )");
        }
        sqlCon.addMainSql(" ) A ");
        sqlCon.addMainSql(" GROUP BY A.materialId ");
        sqlCon.addMainSql(" ) B ");
        sqlCon.addMainSql(" LEFT JOIN cd_m_material B1 ON B.materialId = B1.ROW_ID AND B1.DELETED_FLAG = '0' AND B1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE 1=1 ");
        if (Maps.getString(inputMap, "remianType").equals("over60")) {
            sqlCon.addMainSql(" AND B.diff >=60 ");
            sqlCon.addMainSql(" AND B.diff <90 ");
        } else if (Maps.getString(inputMap, "remianType").equals("over90")) {
            sqlCon.addMainSql(" AND B.diff >=90 ");
            sqlCon.addMainSql(" AND B.diff <180 ");
        } else if (Maps.getString(inputMap, "remianType").equals("hasOver")) {
            sqlCon.addMainSql(" AND B.diff >=180 ");
        }
        return getList(sqlCon);
    }

    @Override
    public List<Map<String, Object>> searchProductionDaily(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT T0.*,T1.COMPANY_NAME FARM_NAME,T1.SORT_NAME FARM_SORT_NAME,IF(T0.FtriggerFlag = '1','已确认','未确认') FtriggerFlagName ");
        sqlCon.addMainSql(" FROM sys_report_production_daily T0 ");
        sqlCon.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), "AND T0.FARM_ID = ? ");
        sqlCon.addCondition(Maps.getString(inputMap, "reportDate"), "AND T0.REPORT_DATE = ? ");
        return getList(sqlCon);
    }

    @Override
    public void editProductionDailySure(Map<String, Object> inputMap) throws Exception {
        List<Map<String, Object>> productionDailyList = searchProductionDaily(inputMap);
        if (productionDailyList.size() != 1) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请联系系统管理员！");
        } else {
            String time = TimeUtil.getSysTimestamp().toString();
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("UPDATE sys_report_production_daily SET FtriggerFlag = '1', ");
            sqlCon.addCondition(time, "CONFIRM_DATE = ? ,");
            sqlCon.addCondition(getEmployeeId(), "requestid = ? ");
            sqlCon.addMainSql("WHERE STATUS='1' AND DELETED_FLAG='0' ");
            sqlCon.addCondition(Maps.getString(inputMap, "reportDate"), "AND REPORT_DATE = ?");
            sqlCon.addCondition(getFarmId(), "AND FARM_ID = ?");
            setSql(sysReportProductionDailyMapper, sqlCon);
        }
    }
}
