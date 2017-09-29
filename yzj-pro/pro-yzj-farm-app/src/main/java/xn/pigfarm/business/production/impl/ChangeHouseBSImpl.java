package xn.pigfarm.business.production.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xn.core.data.SqlCon;
import xn.core.data.enums.NameEnum;
import xn.core.data.enums.PigInfoEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.pigfarm.business.log.ILogBS;
import xn.pigfarm.business.production.IChangeHouseBS;
import xn.pigfarm.business.production.ICommonBS;
import xn.pigfarm.exception.production.ProductionException;
import xn.pigfarm.mapper.basicinfo.SettingMapper;
import xn.pigfarm.mapper.production.ChangeHouseMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.model.basicinfo.SettingModel;
import xn.pigfarm.model.production.ChangeHouseInfoView;
import xn.pigfarm.model.production.ChangeHouseModel;
import xn.pigfarm.model.production.ChangeHouseView;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.util.constants.PigConstants;


/**
 * @Description: 转舍BS层
 * @author zhangjs
 * @date 2016年8月23日 下午1:31:35
 */
@Component("changeHouseBS")
public class ChangeHouseBSImpl extends BaseServiceImpl implements IChangeHouseBS {

    @Autowired
    private PigMapper pigMapper;

    @Autowired
    private ChangeHouseMapper changeHouseMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private ILogBS logBSImpl;

    @Autowired
    private ICommonBS commonBSImpl;


    @Override
    public void changeHouse(List<ChangeHouseView> changeHouseViews, String eventName, long billId) throws Exception {
        
        List<Long> ids = new ArrayList<Long>();
        List<Long> inPigpenIds = new ArrayList<Long>();
        Map<Long, ChangeHouseView> boardSowMap = new HashMap<Long, ChangeHouseView>();
        List<Long> sowIds = new ArrayList<Long>();

        for (ChangeHouseView changeHouseView : changeHouseViews) {
            ids.add(changeHouseView.getPigId());
            inPigpenIds.add(changeHouseView.getInPigpenId());
        }

        if ("CHANGE_LABOR_HOUSE".equals(eventName)) {
            // 上产房判断猪栏是否已经有猪
            checkHousePigpen(inPigpenIds);
            // 哺乳母猪id
            for (ChangeHouseView view : changeHouseViews) {
                if (PigConstants.CHANGE_HOUSE_TYPE_ADV.equals(view.getChangeHouseType()) && PigConstants.PIG_CLASS_BR == view.getPigClass()) {
                    boardSowMap.put(view.getPigId(), view);
                    sowIds.add(view.getPigId());
                }
            }
        }

        // 根据前台的views创建数量相同的pigModel
        List<PigModel> pigModelList = commonBSImpl.createPigModelList(changeHouseViews);

        // 查询种猪的基本信息
        List<Map<String, Object>> changeHouseInfos = commonBSImpl.getPigBaseInfo(ids, "pp_l_bill_change_house");

        // 查询仔猪的基本信息
        if (!sowIds.isEmpty()) {
            List<Map<String, Object>> childList = commonBSImpl.getChildBaseInfo(sowIds, "pp_l_bill_change_house");
            for (Map<String, Object> childMap : childList) {
                for (Map.Entry<Long, ChangeHouseView> entry : boardSowMap.entrySet()) {
                    if (entry.getKey().equals(Maps.getLong(childMap, "boardSowId"))) {
                        ChangeHouseView changeHouseView = new ChangeHouseView();
                        ChangeHouseView boardSowView = entry.getValue();
                        changeHouseView.setBillId(billId);
                        changeHouseView.setChangeHouseType(boardSowView.getChangeHouseType());
                        changeHouseView.setCompanyId(getCompanyId());
                        changeHouseView.setFarmId(getFarmId());
                        changeHouseView.setCreateId(getEmployeeId());
                        changeHouseView.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        changeHouseView.setEarBrand(Maps.getString(childMap, "earBrand"));
                        changeHouseView.setEnterDate(boardSowView.getEnterDate());
                        changeHouseView.setEventName(eventName);
                        changeHouseView.setInHouseId(boardSowView.getInHouseId());
                        changeHouseView.setInPigpenId(boardSowView.getInPigpenId());
                        changeHouseView.setLineNumber(boardSowView.getLineNumber());
                        changeHouseView.setNotes(boardSowView.getNotes());
                        changeHouseView.setOriginApp(boardSowView.getOriginApp());
                        changeHouseView.setOriginFlag(boardSowView.getOriginFlag());
                        changeHouseView.setPigId(Maps.getLong(childMap, "pigId"));
                        changeHouseView.setPigType(Maps.getString(childMap, "pigType"));
                        changeHouseView.setStatus(CommonConstants.STATUS);
                        changeHouseView.setWorker(boardSowView.getWorker());
                        changeHouseViews.add(changeHouseView);

                        Map<String, Object> changeHouseMap = new HashMap<>();
                        changeHouseMap.put("pigId", Maps.getLong(childMap, "pigId"));
                        changeHouseMap.put("swineryId", Maps.getLongClass(childMap, "swineryId"));
                        changeHouseMap.put("pigType", Maps.getString(childMap, "pigType"));
                        changeHouseMap.put("sex", Maps.getString(childMap, "sex"));
                        changeHouseMap.put("lineId", Maps.getLongClass(childMap, "lineId"));
                        changeHouseMap.put("houseId", Maps.getLong(childMap, "houseId"));
                        changeHouseMap.put("pigpenId", Maps.getLongClass(childMap, "pigpenId"));
                        changeHouseMap.put("pigClass", Maps.getLong(childMap, "pigClass"));
                        changeHouseMap.put("parity", Maps.getLong(childMap, "parity"));
                        changeHouseMap.put("lastTableId", Maps.getLongClass(childMap, "lastTableId"));
                        changeHouseMap.put("lastTableSortNbr", Maps.getLongClass(childMap, "lastTableSortNbr"));
                        changeHouseMap.put("proNo", Maps.getLongClass(childMap, "proNo"));
                        changeHouseMap.put("hisSortNbr", Maps.getLongClass(childMap, "hisSortNbr"));
                        changeHouseInfos.add(changeHouseMap);

                        PigModel pigModel = new PigModel();
                        pigModel.setRowId(Maps.getLong(childMap, "pigId"));
                        pigModel.setPigType(PigConstants.PIG_TYPE_PORK);
                        pigModelList.add(pigModel);
                    }
                }
            }
        }

        /** ===========获取setting============================== */
        List<SettingModel> settingList = settingMapper.searchToList(getFarmId());
        Map<String, String> settingMap = new HashMap<String, String>();
        for (SettingModel settingModel : settingList) {
            if ("ZZCSJQDL".equals(settingModel.getSettingCode())) {
                settingMap.put("ZZCSJQDL", settingModel.getSettingValue());
            }
            if ("YXCCZSRL".equals(settingModel.getSettingCode())) {
                settingMap.put("YXCCZSRL", settingModel.getSettingValue());
            }
        }

        // 不是入场，判断是否开启了种猪转舍精确到栏功能
        if (!"PIG_MOVE_IN".equals(eventName)) {
            if ("ON".equals(settingMap.get("ZZCSJQDL"))) {
                checkAccurateToPigpen(changeHouseViews, changeHouseInfos);
            }

            // 判断猪只是否已在该舍
            for (int i = 0; i < changeHouseViews.size(); i++) {
                ChangeHouseView changeHouseView = changeHouseViews.get(i);
                Map<String, Object> changeHouseInfo = changeHouseInfos.get(i);
                if (PigConstants.CHANGE_HOUSE_TYPE_CHI_SELF.equals(changeHouseView.getChangeHouseType()) || PigConstants.CHANGE_HOUSE_TYPE_FAT_SELF
                        .equals(changeHouseView.getChangeHouseType())) {
                    if (changeHouseView.getInHouseId().equals(Maps.getLong(changeHouseInfo, "houseId")) && Maps.getLongClass(changeHouseInfo,
                            "pigpenId") == null && changeHouseView.getInPigpenId() == null && Maps.getLongClass(changeHouseInfo, "pigpenId") == null
                            && changeHouseView.getInSwineryId() == null && Maps.getLongClass(changeHouseInfo, "lastTableId") != null) {
                        Thrower.throwException(ProductionException.CHANGE_HOUSE_PIG_EXISTS_ERROR, changeHouseView.getLineNumber(), changeHouseView
                                .getEarBrand(), CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME));
                    }
                    if (changeHouseView.getInHouseId().equals(Maps.getLong(changeHouseInfo, "houseId")) && Maps.getLongClass(changeHouseInfo,
                            "pigpenId") != null && Maps.getLongClass(changeHouseInfo, "pigpenId").equals(changeHouseView.getInPigpenId()) && Maps
                                    .getLongClass(changeHouseInfo, "swineryId") != null && Maps.getLongClass(changeHouseInfo, "swineryId").equals(
                                            changeHouseView.getInSwineryId()) && Maps.getLongClass(changeHouseInfo, "lastTableId") != null) {
                        Thrower.throwException(ProductionException.CHANGE_HOUSE_PIG_EXISTS_ERROR, changeHouseView.getLineNumber(), changeHouseView
                                .getEarBrand(), CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME)
                                        + "->" + CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "pigpenId")), NameEnum.PIGPEN_NAME));
                    }
                } else {
                    if (changeHouseView.getInHouseId().equals(Maps.getLong(changeHouseInfo, "houseId")) && Maps.getLongClass(changeHouseInfo,
                            "pigpenId") == null && changeHouseView.getInPigpenId() == null && Maps.getLongClass(changeHouseInfo,
                                    "lastTableId") != null) {
                        Thrower.throwException(ProductionException.CHANGE_HOUSE_PIG_EXISTS_ERROR, changeHouseView.getLineNumber(), changeHouseView
                                .getEarBrand(), CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME));
                    }
                    if (Maps.getLongClass(changeHouseInfo, "houseId").equals(changeHouseView.getInHouseId()) && Maps.getLongClass(changeHouseInfo,
                            "pigpenId") != null && Maps.getLongClass(changeHouseInfo, "pigpenId").equals(changeHouseView.getInPigpenId()) && Maps
                                    .getLongClass(changeHouseInfo, "lastTableId") != null) {
                        Thrower.throwException(ProductionException.CHANGE_HOUSE_PIG_EXISTS_ERROR, changeHouseView.getLineNumber(), changeHouseView
                                .getEarBrand(), CacheUtil.getName(String.valueOf(Maps.getLongClass(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME)
                                        + "->" + CacheUtil.getName(String.valueOf(Maps.getLongClass(changeHouseInfo, "pigpenId")),
                                                NameEnum.PIGPEN_NAME));
                    }
                    changeHouseView.setInSwineryId(Maps.getLongClass(changeHouseInfo, "swineryId"));
                }

            }
        }

        // 判断是否超出猪舍容量
        if ("OFF".equals(settingMap.get("YXCCZSRL"))) {
            checkHouseQty(changeHouseViews);
        }

        List<ChangeHouseModel> changeHouseModels = new ArrayList<ChangeHouseModel>();
        List<PigEventHisModel> pigEventHisModels = new ArrayList<PigEventHisModel>();
        List<ChangeHouseModel> updateLastModels = new ArrayList<>();

        Date createDate = new Date();

        for (int i = 0; i < changeHouseInfos.size(); i++) {

            ChangeHouseModel changeHouseModel = new ChangeHouseModel();
            ChangeHouseView changeHouseView = changeHouseViews.get(i);
            Map<String, Object> changeHouseInfo = changeHouseInfos.get(i);
            PigModel pigModel = pigModelList.get(i);

            // long rowId = SeqUtil.getSeq(SeqEnum.HOUSE);
            // changeHouseModel.setRowId(rowId);
            changeHouseModel.setBillId(billId);
            changeHouseModel.setChangeHouseDate(changeHouseView.getEnterDate());
            changeHouseModel.setChangeType(changeHouseView.getChangeHouseType());
            changeHouseModel.setChangeWeight(changeHouseView.getChangeWeight());
            changeHouseModel.setHouseId(changeHouseView.getInHouseId());
            changeHouseModel.setLineNumber(changeHouseView.getLineNumber());
            changeHouseModel.setNotes(changeHouseView.getNotes());
            changeHouseModel.setPigId(changeHouseView.getPigId());
            changeHouseModel.setPigpenId(changeHouseView.getInPigpenId());
            changeHouseModel.setWeighType(changeHouseView.getWeighType());
            changeHouseModel.setWorker(changeHouseView.getWorker());
            changeHouseModel.setSwineryId(changeHouseView.getInSwineryId());
            changeHouseModel.setOriginApp(changeHouseView.getOriginApp());
            changeHouseModel.setOriginFlag(changeHouseView.getOriginFlag());

            changeHouseModel.setCompanyId(getCompanyId());
            changeHouseModel.setCreateDate(createDate);
            changeHouseModel.setCreateId(getEmployeeId());
            changeHouseModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            changeHouseModel.setStatus(CommonConstants.STATUS);
            changeHouseModel.setFarmId(getFarmId());

            changeHouseModel.setLineId(Maps.getLongClass(changeHouseInfo, "lineId"));
            changeHouseModel.setParity(Maps.getLong(changeHouseInfo, "parity"));
            changeHouseModel.setPigClass(Maps.getLong(changeHouseInfo, "pigClass"));
            changeHouseModel.setPigType(Maps.getString(changeHouseInfo, "pigType"));
            changeHouseModel.setProNo(Maps.getLongClass(changeHouseInfo, "proNo"));
            changeHouseModel.setSortNbr(Maps.getLongClass(changeHouseInfo, "lastTableSortNbr"));
            changeHouseModel.setChangeHouseId(Maps.getLongClass(changeHouseInfo, "lastTableId"));
            changeHouseModels.add(changeHouseModel);

            PigEventHisModel pigEventHisModel = new PigEventHisModel();
            pigEventHisModel.setBillId(billId);
            pigEventHisModel.setCompanyId(getCompanyId());
            pigEventHisModel.setCreateDate(createDate);
            pigEventHisModel.setCreateId(getEmployeeId());
            pigEventHisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            pigEventHisModel.setEarBrand(changeHouseView.getEarBrand());
            pigEventHisModel.setEnterDate(changeHouseView.getEnterDate());
            pigEventHisModel.setEventName(eventName);
            if (changeHouseView.getInPigpenId() != null && Maps.getLongClass(changeHouseInfo, "pigpenId") != null) {
                pigEventHisModel.setEventNotes("从【" + CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME)
                        + "->" + CacheUtil.getName(String.valueOf(Maps.getLongClass(changeHouseInfo, "pigpenId")), NameEnum.PIGPEN_NAME) + "】舍转到【"
                        + CacheUtil.getName(String.valueOf(changeHouseView.getInHouseId()), NameEnum.HOUSE_NAME) + "->" + CacheUtil.getName(String
                                .valueOf(changeHouseView.getInPigpenId()), NameEnum.PIGPEN_NAME) + "】！");
            } else if (changeHouseView.getInPigpenId() != null) {
                pigEventHisModel.setEventNotes("从【" + CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME)
                        + "】舍转到【" + CacheUtil.getName(String.valueOf(changeHouseView.getInHouseId()), NameEnum.HOUSE_NAME) + "->" + CacheUtil.getName(
                                String.valueOf(changeHouseView.getInPigpenId()), NameEnum.PIGPEN_NAME) + "】！");
            } else if (Maps.getLongClass(changeHouseInfo, "pigpenId") != null) {
                pigEventHisModel.setEventNotes("从【" + CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME)
                        + "->" + CacheUtil.getName(String.valueOf(Maps.getLongClass(changeHouseInfo, "pigpenId")), NameEnum.PIGPEN_NAME) + "】舍转到【"
                        + CacheUtil.getName(String.valueOf(changeHouseView.getInHouseId()), NameEnum.HOUSE_NAME) + "】！");
            } else {
                pigEventHisModel.setEventNotes("从【" + CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME)
                        + "】舍转到【" + CacheUtil.getName(String.valueOf(changeHouseView.getInHouseId()), NameEnum.HOUSE_NAME) + "】！");
            }
            pigEventHisModel.setFarmId(getFarmId());
            pigEventHisModel.setHouseId(changeHouseView.getInHouseId());
            pigEventHisModel.setLastEventDate(changeHouseView.getEnterDate());
            pigEventHisModel.setLineNumber(changeHouseView.getLineNumber());
            pigEventHisModel.setNotes(changeHouseView.getNotes());
            pigEventHisModel.setPigId(changeHouseView.getPigId());
            pigEventHisModel.setPigpenId(changeHouseView.getInPigpenId());

            pigEventHisModel.setLineId(Maps.getLongClass(changeHouseInfo, "lineId"));
            pigEventHisModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            pigEventHisModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            pigEventHisModel.setParity(Maps.getLong(changeHouseInfo, "parity"));
            pigEventHisModel.setPigClass(Maps.getLong(changeHouseInfo, "pigClass"));

            pigEventHisModel.setPigType(Maps.getString(changeHouseInfo, "pigType"));
            pigEventHisModel.setSex(Maps.getString(changeHouseInfo, "sex"));
            pigEventHisModel.setSortNbr(Maps.getLongClass(changeHouseInfo, "hisSortNbr"));
            pigEventHisModel.setStatus(CommonConstants.STATUS);
            pigEventHisModel.setSwineryId(changeHouseView.getInSwineryId());
            pigEventHisModel.setTableName("pp_l_bill_change_house");
            // pigEventHisModel.setTableRowId(rowId);
            pigEventHisModel.setWorker(changeHouseView.getWorker());
            pigEventHisModels.add(pigEventHisModel);

            // 跟新猪只信息
            pigModel.setHouseId(changeHouseModel.getHouseId());
            pigModel.setPigpenId(changeHouseModel.getPigpenId());

            // 跟新上一次转舍的三个out
            ChangeHouseModel updateModel = new ChangeHouseModel();
            updateModel.setRowId(Maps.getLongClass(changeHouseInfo, "lastTableId"));
            updateModel.setChangeHouseDateOut(changeHouseView.getEnterDate());
            updateModel.setHouseIdOut(changeHouseView.getInHouseId());
            updateModel.setPigpenIdOut(changeHouseView.getInPigpenId());
            updateLastModels.add(updateModel);
        }
        /************************************** 2017-9-4 转舍添加tableRowID ************************************/
        List<Map<String, Object>> changeHouseList = new ArrayList<Map<String, Object>>();
        if (changeHouseModels != null && !changeHouseModels.isEmpty()) {
            for (ChangeHouseModel changeHouseModel : changeHouseModels) {
                changeHouseMapper.insert(changeHouseModel);
                Map<String, Object> changeHouseMap = new HashMap<String, Object>();
                changeHouseMap.put("pigId", changeHouseModel.getPigId());
                changeHouseMap.put("changeHouseRowId", changeHouseModel.getRowId());
                changeHouseList.add(changeHouseMap);
            }
        }
        /* changeHouseMapper.inserts(changeHouseModels); */
        /*************************************** 2017-9-4 转舍添加tableRowID ***********************************/
        changeHouseMapper.updates(updateLastModels);
        logBSImpl.createPigEventHises(pigEventHisModels, pigModelList, changeHouseList);
    }

    @Override
    public List<ChangeHouseInfoView> getChangeHouseInfo(List<Long> ids) {

        return changeHouseMapper.searchChangeHouseInfo(ids);
    }

    public void checkHousePigpen(List<Long> inPigpenIds) throws Exception {

        for (int i = 0; i < inPigpenIds.size() - 1; i++) {
            for (int j = i + 1; j < inPigpenIds.size(); j++) {
                if (inPigpenIds.get(i).equals(inPigpenIds.get(j))) {
                    Thrower.throwException(ProductionException.CHANGE_HOUSE_IS_SAME, i + 1, j + 1);
                }
            }
        }

        SqlCon pigpenSql = new SqlCon();
        pigpenSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        pigpenSql.addCondition(getLongListToString(inPigpenIds), " AND PIGPEN_ID IN ?", false, true);
        pigpenSql.addMainSql(" AND PIG_CLASS BETWEEN 1 AND 18 AND PIG_TYPE = '2'");
        pigpenSql.addMainSql(" GROUP BY PIGPEN_ID ORDER BY FIELD(PIGPEN_ID," + getLongListToString(inPigpenIds) + ")");
        List<PigModel> pigList = getList(pigMapper, pigpenSql);

        if (pigList.size() > 0) {
            for (PigModel pigModel : pigList) {
                Thrower.throwException(ProductionException.CHANGE_HOUSE_PIG_PIGPEN_ERROR, inPigpenIds.indexOf(pigModel.getPigpenId()) + 1, CacheUtil
                        .getPigInfo(String.valueOf(pigModel.getRowId()), PigInfoEnum.EAR_BRAND).get("earBrand"), CacheUtil.getName(String.valueOf(
                                pigModel.getPigpenId()), NameEnum.PIGPEN_NAME));
            }
        }

    }

    public void checkHouseQty(List<ChangeHouseView> changeHouseViews) throws Exception {

        Set<Long> houseIds = new LinkedHashSet<Long>();
        Map<Long, Map<String, Object>> houseMaps = new LinkedHashMap<Long, Map<String, Object>>();
        for (ChangeHouseView changeHouseView : changeHouseViews) {
            Map<String, Object> houseMap = null;
            if (!houseIds.contains(changeHouseView.getInHouseId())) {
                houseMap = new HashMap<String, Object>();
                houseIds.add(changeHouseView.getInHouseId());
                houseMap.put("houseId", changeHouseView.getInHouseId());
                houseMap.put("houseCount", 1L);
                houseMaps.put(changeHouseView.getInHouseId(), houseMap);
            } else {
                houseMap = houseMaps.get(changeHouseView.getInHouseId());
                houseMap.put("houseCount", Maps.getLong(houseMap, "houseCount") + 1L);
            }
        }
        SqlCon houseSql = new SqlCon();
        houseSql.addMainSql("SELECT SUM(IF(P.HOUSE_ID IS NULL,0,1)) AS pigQty");
        houseSql.addMainSql(" FROM pp_o_house H");
        houseSql.addMainSql(" LEFT JOIN pp_l_pig P");
        houseSql.addMainSql(" ON H.ROW_ID = P.HOUSE_ID AND H.DELETED_FLAG = P.DELETED_FLAG AND H.FARM_ID = P.FARM_ID");
        houseSql.addMainSql(" AND H.STATUS = P.STATUS AND P.PIG_Class BETWEEN 1 AND 18 WHERE");
        houseSql.addConditionWithNull(getFarmId(), " H.FARM_ID = ?");
        houseSql.addCondition(getLongListToString(houseIds), " AND H.ROW_ID IN ?", false, true);
        houseSql.addMainSql(" AND H.STATUS = 1 AND H.DELETED_FLAG = 0");
        houseSql.addMainSql(" GROUP BY H.ROW_ID ORDER BY FIELD(H.ROW_ID," + getLongListToString(houseIds) + ")");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", houseSql.getCondition());
        List<Map<String, String>> pigQtys = paramMapper.getInfos(sqlMap);

        SqlCon houseSetSql = new SqlCon();
        houseSetSql.addMainSql("SELECT IFNULL(SUM(T1.PIG_NUM),0) setPigQty FROM pp_o_house T0");
        houseSetSql.addMainSql(" LEFT JOIN pp_o_pigpen T1");
        houseSetSql.addMainSql(" ON T0.ROW_ID = T1.HOUSE_ID AND T1.STATUS = 1 AND T1.DELETED_FLAG = 0 WHERE");
        houseSetSql.addMainSql(" T0.STATUS = 1 AND T0.DELETED_FLAG = 0");
        houseSetSql.addCondition(getLongListToString(houseIds), " AND T0.ROW_ID IN ?", false, true);
        houseSetSql.addMainSql(" GROUP BY T0.ROW_ID ORDER BY FIELD(T0.ROW_ID," + getLongListToString(houseIds) + ")");
        Map<String, String> setSqlMap = new HashMap<String, String>();
        setSqlMap.put("sql", houseSetSql.getCondition());
        List<Map<String, String>> setPigQtys = paramMapper.getInfos(setSqlMap);

        int index = 0;
        for (Map.Entry<Long, Map<String, Object>> houseMap : houseMaps.entrySet()) {
            Long pigQty = Maps.getLong(pigQtys.get(index), "pigQty") + Maps.getLong(houseMap.getValue(), "houseCount");
            Long pigSetQty = Maps.getLong(setPigQtys.get(index), "setPigQty");
            if (pigQty > pigSetQty) {
                Thrower.throwException(ProductionException.CHANGE_HOUSE_BEYOND_PROHIBITION_ERROR, CacheUtil.getName(String.valueOf(houseMap.getKey()),
                        NameEnum.HOUSE_NAME), pigSetQty, pigQty);
            }
            index++;
        }
    }

    public void checkAccurateToPigpen(List<ChangeHouseView> changeHouseViews, List<Map<String, Object>> changeHouseInfos) throws Exception {
        for (int i = 0; i < changeHouseViews.size(); i++) {
            ChangeHouseView changeHouseView = changeHouseViews.get(i);
            Map<String, Object> changeHouseInfo = changeHouseInfos.get(i);
            if (changeHouseView.getInPigpenId() == null && (PigConstants.PIG_TYPE_BOAR.equals(Maps.getLong(changeHouseInfo, "pigType"))
                    || PigConstants.PIG_TYPE_SOW.equals(Maps.getLong(changeHouseInfo, "pigType")))) {
                Thrower.throwException(ProductionException.CHANGE_HOUSE_PIGPEN_PRECISE_ERROR, changeHouseView.getLineNumber(), changeHouseView
                        .getEarBrand(), CacheUtil.getName(String.valueOf(Maps.getLong(changeHouseInfo, "houseId")), NameEnum.HOUSE_NAME), CacheUtil
                                .getName(String.valueOf(changeHouseView.getInHouseId()), NameEnum.HOUSE_NAME));
            }
        }
    }

    public String getLongListToString(Set<Long> idsList) {
        StringBuffer str = new StringBuffer();
        long i = 0;
        if (!idsList.isEmpty()) {
            for (Long id : idsList) {
                str.append(id);
                if (i < idsList.size() - 1) {
                    str.append(",");
                }
                i++;
            }
        }
        return str.toString();
    }

    public String getLongListToString(List<Long> idsList) {
        StringBuffer str = new StringBuffer();
        long i = 0;
        if (!idsList.isEmpty()) {
            for (Long id : idsList) {
                str.append(id);
                if (i < idsList.size() - 1) {
                    str.append(",");
                }
                i++;
            }
        }
        return str.toString();
    }

    private String longArrayListToString(List<Long> longList) {
        if (longList != null && !longList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < longList.size(); i++) {
                stringBuffer.append(longList.get(i).toString());
                if (i != longList.size() - 1) {
                    stringBuffer.append(",");
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }
}
