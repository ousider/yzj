package xn.pigfarm.service.wechat.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.shiro.UserDetail;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.CodeListMapper;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.basicinfo.HouseStatusMapper;
import xn.pigfarm.model.backend.CodeListModel;
import xn.pigfarm.model.basicinfo.HouseStatusModel;
import xn.pigfarm.service.wechat.IWechatHouseStatusEveryDayService;
import xn.pigfarm.util.constants.WechatConstants;

@Service("wechatHouseStatusEveryDay")
public class WechatHouseStatusEveryDayServiceImpl extends BaseServiceImpl implements IWechatHouseStatusEveryDayService {

    @Autowired
    HouseStatusMapper houseStatusMapper;

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private CodeListMapper codeListMapper;

    @Override
    public List<Map<String, Object>> gethouse(Map<String, Object> paremMap) {

        Map<String, String> map = new HashMap<>();
        UserDetail userDetail = getUserDetail();
        Long employeeId = userDetail.getEmployeeId();
        SqlCon condition = new SqlCon();
        condition.addMainSql(" SELECT T6.ROW_ID houseId,T6.HOUSE_NAME houseName,T3.DEPT_ID AS deptId,T4.DEPT_NAME AS deptName, ");
        condition.addMainSql(
                " T7.FARM_ID farmId,T7.COLLECT_DATE collectDate, T7.PIG_NUM pigNum, T7.HOUSE_SATUS houseSatus, T8.CODE_NAME houseSatusName, T7.FEED_DOSE feedDose, T7.USE_DRUG useDrug, T7.IMMUNE immune, T7.MIN_TEMP minTemp, T7.MAX_TEMP maxTemp, T7.DAYAGE dayAge, T7.SWINERY_HEALTH swineryHealth, T9.CODE_NAME swineryHealthName,  T7.SOW_DIE_OBSOLETE sowDieObsolete, T7.DIE_NUM dieNum,T7.DIARRHEA_NUM diarrheaNum,T7.GASP_RATE gaspRate,T7.NOTES notes");
        // 人员
        condition.addMainSql(" FROM HR_O_EMPLOYEE T1 ");
        // 岗位人员关联表
        condition.addMainSql(" LEFT JOIN HR_R_EMPLOYEE_POST T2 ");
        condition.addMainSql(" ON (T1.ROW_ID = T2.EMPLOYEE_ID AND T2.STATUS='1' AND T2.DELETED_FLAG='0' ) ");
        // 岗位
        condition.addMainSql(" LEFT JOIN HR_O_POST T3  ");
        condition.addMainSql(" ON (T2.POST_ID = T3.ROW_ID AND T3.STATUS='1' AND T3.DELETED_FLAG='0' ) ");
        // 部门
        condition.addMainSql(" LEFT JOIN HR_O_DEPT T4 ");
        condition.addMainSql(" ON(T3.DEPT_ID = T4.ROW_ID AND T4.STATUS='1' AND T4.DELETED_FLAG='0' ) ");
        // 部门-猪舍对应表
        condition.addMainSql(" LEFT JOIN HR_R_DEPT_HOUSE T5 ");
        condition.addMainSql(" ON T1.FARM_ID = T5.FARM_ID AND T4.ROW_ID = T5.DEPT_ID AND T5.DELETED_FLAG = '0' ");
        // 猪舍表
        condition.addMainSql(" LEFT JOIN PP_O_HOUSE T6 ");
        condition.addMainSql(" ON T1.FARM_ID = T6.FARM_ID AND T6.DELETED_FLAG = '0' AND T6.STATUS = '1' ");
        condition.addMainSql(" LEFT JOIN CD_M_HOUSE_STATUS T7 ");
        condition.addMainSql(
                " ON T1.FARM_ID = T7.FARM_ID AND T6.ROW_ID = T7.HOUSE_ID AND T7.COLLECT_DATE = CURRENT_DATE() AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'  ");
        condition.addMainSql(" LEFT JOIN CD_L_CODE_LIST T8 ");
        condition.addMainSql(" ON T8.TYPE_CODE = 'WECHAT_HOUSE_STATUS'  AND T7.HOUSE_SATUS = T8.CODE_VALUE AND T8.DELETED_FLAG = '0' ");
        condition.addMainSql(" LEFT JOIN CD_L_CODE_LIST T9 ");
        condition.addMainSql(" ON T9.TYPE_CODE = 'WECHAT_SWINERY_HEALTH'  AND T7.SWINERY_HEALTH = T9.CODE_VALUE AND T9.DELETED_FLAG = '0' ");

        condition.addMainSql(" WHERE T1.STATUS IN ('1','0') AND T1.DELETED_FLAG='0' ");
        condition.addMainSql(" AND IFNULL(T6.STOP_DATE,DATE_ADD(NOW(),INTERVAL 1 DAY)) > NOW() ");
        condition.addMainSql(" AND T1.EMPLOYEE_TYPE <> '9' ");
        condition.addMainSql(" AND T1.EMPLOYEE_TYPE <> '3' ");
        condition.addMainSql(" AND IF(T1.EMPLOYEE_TYPE = '4'  , ' 1 = 1 ' , T5.HOUSE_ID = T6.ROW_ID ) ");
        condition.addMainSql(" AND T6.HOUSE_NAME NOT LIKE '虚拟公猪舍%' ");
        condition.addMainSql(" AND T6.HOUSE_TYPE IN (6,8,9) ");
        condition.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        condition.addCondition(employeeId, " AND T1.ROW_ID = ? ");
        condition.addMainSql(" ORDER BY T7.FARM_ID,T6.HOUSE_NAME ");
        map.put("sql", condition.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        return list;
    }

    @Override
    public List<HouseStatusModel> editInsertToWechatHouseStatus(Map<String, Object> map) throws Exception {

        UserDetail userDetail = getUserDetail();
        Long employeeId = userDetail.getEmployeeId();
        // 验证必要的入参
        HashMap<String, String> paremName = new HashMap<>();
        paremName.put("houseId", "猪舍Id");
        paremName.put("collectDate", "采集日期");
        paremName.put("houseSatus", "猪舍状态");

        if (!WechatConstants.HOUSE_STATUS.equals(Maps.getString(map, "houseSatus"))) {
            paremName.put("pigNum", "存栏数");
            paremName.put("feedDose", "采食量 (单位g)");
            paremName.put("useDrug", "用药");
            paremName.put("immune", "免疫");
            paremName.put("minTemp", "最低温度");
            paremName.put("maxTemp", "最高温度");
            paremName.put("dayAge", "日龄");
            paremName.put("swineryHealth", "设置猪群健康度");
            paremName.put("sowDieObsolete", "设置母猪死淘");
            paremName.put("dieNum", "死亡数量");
            if (Maps.getString(map, "swineryHealth") != null && WechatConstants.SWINERY_HEALTH_DIARRHEA.equals(Maps.getString(map,
                    "swineryHealth"))) {
                paremName.put("diarrheaNum", "腹泻窝数");
            }
            if (Maps.getString(map, "swineryHealth") != null && WechatConstants.SWINERY_HEALTH_GASP.equals(Maps.getString(map, "swineryHealth"))) {
                paremName.put("gaspRate", "喘气比例");
            }
        }
        Verify("编辑", map, paremName);

        map.put("myFarm", "true");
        List<Map<String, Object>> searchWechatHouseStatusByHouseIdAndDate = searchWechatHouseStatusByHouseIdAndDate(map);
        if (searchWechatHouseStatusByHouseIdAndDate.size() > 0) {
            for (Map<String, Object> sea : searchWechatHouseStatusByHouseIdAndDate) {
                houseStatusMapper.delete(Maps.getLong(sea, "rowId"));
            }
        }

        HouseStatusModel houseStatusModel = new HouseStatusModel();
        houseStatusModel.setSortNbr(1L);
        houseStatusModel.setStatus("1");
        houseStatusModel.setDeletedFlag("0");
        houseStatusModel.setOriginFlag("S");
        houseStatusModel.setOriginApp("XN1.0");
        houseStatusModel.setNotes(Maps.getString(map, "notes"));
        houseStatusModel.setFarmId(getFarmId());
        houseStatusModel.setCompanyId(getCompanyId());
        houseStatusModel.setHouseId(Maps.getLong(map, "houseId"));
        houseStatusModel.setCollectDate(Maps.getDate(map, "collectDate"));
        houseStatusModel.setHouseSatus(Maps.getLong(map, "houseSatus"));
        if (!WechatConstants.HOUSE_STATUS.equals(Maps.getString(map, "houseSatus"))) {
            houseStatusModel.setPigNum(Maps.getLong(map, "pigNum"));
            houseStatusModel.setFeedDose(Maps.getString(map, "feedDose"));
            houseStatusModel.setUseDrug(Maps.getString(map, "useDrug"));
            houseStatusModel.setImmune(Maps.getString(map, "immune"));
            houseStatusModel.setMinTemp(Maps.getDoubleClass(map, "minTemp"));
            houseStatusModel.setMaxTemp(Maps.getDoubleClass(map, "maxTemp"));
            houseStatusModel.setDayage(Maps.getLong(map, "dayAge"));
            houseStatusModel.setSwineryHealth(Maps.getLong(map, "swineryHealth"));
            houseStatusModel.setSowDieObsolete(Maps.getLong(map, "sowDieObsolete"));
            houseStatusModel.setDieNum(Maps.getLong(map, "dieNum"));
            if (Maps.getString(map, "swineryHealth") != null && WechatConstants.SWINERY_HEALTH_DIARRHEA.equals(Maps.getString(map,
                    "swineryHealth"))) {
                houseStatusModel.setDiarrheaNum(Maps.getLong(map, "diarrheaNum"));
            }
            if (Maps.getString(map, "swineryHealth") != null && WechatConstants.SWINERY_HEALTH_GASP.equals(Maps.getString(map, "swineryHealth"))) {
                houseStatusModel.setGaspRate(Maps.getString(map, "gaspRate"));
            }

        }
        houseStatusModel.setCollectId(employeeId);
        houseStatusModel.setCreateTime(new Date());
        houseStatusMapper.insert(houseStatusModel);

        return null;
    }

    @Override
    public List<Map<String, Object>> getHouse7DayByHouseIdAndDate(Map<String, Object> map) throws Exception {
        // 验证必要的入参
        HashMap<String, String> paremName = new HashMap<>();
        paremName.put("houseId", "猪舍ID");
        paremName.put("startDate", "开始日期");
        paremName.put("endDate", "结束日期");
        Verify("近7日数据", map, paremName);

        Long houseId = Maps.getLong(map, "houseId");
        String startDate = TimeUtil.format(Maps.getDate(map, "startDate"), TimeUtil.DATE_FORMAT);
        String endDate = TimeUtil.format(Maps.getDate(map, "endDate"), TimeUtil.DATE_FORMAT);

        Map<String, String> sqlMap = new HashMap<>();
        SqlCon condition = new SqlCon();
        condition.addMainSql(
                " SELECT T1.ROW_ID rowId, T1.SORT_NBR sortNbr, T1.NOTES notes, T1.HOUSE_ID houseId,T2.HOUSE_NAME houseName, T1.COLLECT_DATE collectDate, T1.PIG_NUM pigNum, T1.HOUSE_SATUS houseSatus, T1.FEED_DOSE feedDose, T1.USE_DRUG useDrug, T1.IMMUNE immune, T1.MIN_TEMP minTemp, T1.MAX_TEMP maxTemp,T1.MAX_TEMP - T1.MIN_TEMP tempDiff, T1.DAYAGE dayAge, T1.SWINERY_HEALTH swineryHealth, T1.SOW_DIE_OBSOLETE sowDieObsolete, T1.DIE_NUM dieNum, T1.COLLECT_ID collectId, T1.CREATE_TIME createTime ");
        condition.addMainSql(" FROM CD_M_HOUSE_STATUS T1 ");
        condition.addMainSql(" LEFT JOIN PP_O_HOUSE T2 ");
        condition.addMainSql(" ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' ");
        condition.addMainSql(" WHERE T1.DELETED_FLAG='0' ");

        // condition.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        condition.addCondition(houseId, " AND T1.HOUSE_ID = ? ");
        condition.addCondition(startDate, " AND T1.COLLECT_DATE BETWEEN ?  ");
        condition.addCondition(endDate, " AND ?  ");
        condition.addCondition(endDate, " AND IFNULL(t2.STOP_DATE,NOW()) >= ? ");
        sqlMap.put("sql", condition.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        return list;
    }

    @Override
    public List<Map<String, Object>> searchWechatHouseStatusByHouseIdAndDate(Map<String, Object> map) throws Exception {
        HashMap<String, String> paremName = new HashMap<>();
        UserDetail userDetail = getUserDetail();
        String farmIds = null;
        // houseId为null则查询整个猪场
        Long houseId = Maps.getLongClass(map, "houseId");
        paremName.put("collectDate", "采集日期");

        // 默认查询本厂
        if ("true".equals(Maps.getString(map, "myFarm"))) {
            farmIds = String.valueOf(getFarmId());
        } else {
            // 获取对应的farmid集
            farmIds = getFarmIds(Maps.getString(map, "companyMark"));
            paremName.put("companyMark", "公司标示");
        }

        // 验证必要的入参
        Verify("查询猪舍明细", map, paremName);

        String collectDate = TimeUtil.format(Maps.getDate(map, "collectDate"), TimeUtil.DATE_FORMAT);
        Map<String, String> sqlMap = new HashMap<>();
        SqlCon condition = new SqlCon();
        condition.addMainSql(" SELECT T7.ROW_ID rowId,T6.ROW_ID houseId,T6.HOUSE_NAME houseName, ");
        // condition.addMainSql(" T7.ROW_ID rowId, ");
        // condition.addMainSql(" T7.SORT_NBR sortNbr, ");
        // condition.addMainSql(" T7.STATUS STATUS, ");
        // condition.addMainSql(" T7.DELETED_FLAG deletedFlag, ");
        // condition.addMainSql(" T7.origin_flag originFlag, ");
        // condition.addMainSql(" T7.ORIGIN_APP originApp, ");
        // condition.addMainSql(" T7.COMPANY_ID companyId, ");
        condition.addMainSql(" T7.NOTES notes,  ");
        condition.addMainSql(" T7.FARM_ID farmId,  ");
        condition.addMainSql(" T7.HOUSE_ID houseId,  ");
        condition.addMainSql(" T7.COLLECT_DATE collectDate,  ");
        condition.addMainSql(" T7.PIG_NUM pigNum,  ");
        condition.addMainSql(" T7.HOUSE_SATUS houseSatus,  ");
        condition.addMainSql(" T8.CODE_NAME houseSatusName,  ");
        condition.addMainSql(" T7.FEED_DOSE feedDose,  ");
        condition.addMainSql(" T7.USE_DRUG useDrug,  ");
        condition.addMainSql(" T7.IMMUNE immune,  ");
        condition.addMainSql(" T7.MIN_TEMP minTemp,  ");
        condition.addMainSql(" T7.MAX_TEMP maxTemp,  ");
        condition.addMainSql(" T7.DAYAGE dayAge,  ");
        condition.addMainSql(" T7.SWINERY_HEALTH swineryHealth,  ");
        condition.addMainSql(" T9.CODE_NAME swineryHealthName,  ");
        condition.addMainSql(" T7.SOW_DIE_OBSOLETE sowDieObsolete,  ");
        condition.addMainSql(" T7.DIE_NUM dieNum,  ");
        condition.addMainSql(" T7.COLLECT_ID collectId,  ");
        condition.addMainSql(" T7.CREATE_TIME createTime, ");
        condition.addMainSql(" T7.DIARRHEA_NUM diarrheaNum,");
        condition.addMainSql(" T7.GASP_RATE gaspRate  ");

        condition.addMainSql(" FROM PP_O_HOUSE T6 ");
        condition.addMainSql(" LEFT JOIN CD_M_HOUSE_STATUS T7  ");
        condition.addMainSql(" ON T6.FARM_ID = T7.FARM_ID AND T6.ROW_ID = T7.HOUSE_ID AND T7.DELETED_FLAG = '0' AND T7.STATUS = '1'   ");
        condition.addCondition(collectDate, " AND IFNULL(T7.COLLECT_DATE, ? ) = ?  ");
        condition.addMainSql(" LEFT JOIN CD_L_CODE_LIST T8 ");
        condition.addMainSql(" ON T8.TYPE_CODE = 'WECHAT_HOUSE_STATUS'  AND T7.HOUSE_SATUS = T8.CODE_VALUE AND T8.DELETED_FLAG = '0' ");
        condition.addMainSql(" LEFT JOIN CD_L_CODE_LIST T9 ");
        condition.addMainSql(" ON T9.TYPE_CODE = 'WECHAT_SWINERY_HEALTH'  AND T7.SWINERY_HEALTH = T9.CODE_VALUE AND T9.DELETED_FLAG = '0' ");

        condition.addMainSql(" WHERE T6.DELETED_FLAG='0'  ");
        condition.addMainSql(" AND IFNULL(T6.STOP_DATE,DATE_ADD(NOW(),INTERVAL 1 DAY)) > NOW()  ");
        condition.addMainSql(" AND T6.HOUSE_NAME NOT LIKE '虚拟公猪舍%' ");
        condition.addMainSql(" AND T6.HOUSE_TYPE IN (6,8,9) ");
        condition.addCondition(farmIds, " AND T6.FARM_ID IN ( ? ) ");
        condition.addCondition(houseId, " AND T7.HOUSE_ID IN ( ? ) ");
        condition.addMainSql(" ORDER BY T6.FARM_ID,T6.HOUSE_TYPE,T6.HOUSE_NAME,IFNULL(T7.ROW_ID,99999999999999) ");

        if (map.get("pageSize") != null && map.get("page") != null) {
            condition.addCondition((Maps.getLong(map, "page") - 1) * Maps.getLong(map, "pageSize"), " LIMIT ? ");
            condition.addCondition(Maps.getLong(map, "pageSize"), " ,? ");
        }

        sqlMap.put("sql", condition.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        return list;
    }

    public static void Verify(String methodName, Map<String, Object> paremMap, Map<String, String> paremName) {
        for (String parem : paremName.keySet()) {
            Object p = paremMap.get(parem);
            if (p == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该方法:" + methodName + ",缺失参数:" + paremName.get(parem));
            }
        }
    }

    // 获取对应的farmId集
    public String getFarmIds(String companyMark) {
        // 非空时执行
        if (!StringUtil.isBlank(companyMark)) {
            Map<String, String> sqlMap = new HashMap<>();
            SqlCon condition = new SqlCon();
            condition.addMainSql(" SELECT GROUP_CONCAT(ROW_ID+'') farmIds FROM HR_M_COMPANY ");
            condition.addMainSql(" WHERE DELETED_FLAG = '0' ");
            condition.addMainSql(" AND COMPANY_MARK LIKE '" + companyMark + "%' ");
            sqlMap.put("sql", condition.getCondition());
            List<Map<String, String>> farmIds = paramMapper.getInfos(sqlMap);
            if (farmIds.size() > 0) {
                return Maps.getString(farmIds.get(0), "farmIds");
            }
        }
        return null;
    }

    @Override
    public List<CodeListModel> searchWechatHouseFinal(String wechatHouseFinal) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(wechatHouseFinal, " AND TYPE_CODE = ? ");
        List<CodeListModel> list = getList(codeListMapper, sqlCon);
        return list;
    }

}
