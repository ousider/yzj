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

import xn.core.data.SqlCon;
import xn.core.data.enums.SeqEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.SeqUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.pigfarm.business.log.ILogBS;
import xn.pigfarm.business.production.IChangeHouseBS;
import xn.pigfarm.business.production.IFosterBS;
import xn.pigfarm.exception.production.ProductionException;
import xn.pigfarm.mapper.production.FosterCareMapper;
import xn.pigfarm.mapper.production.FosterDetailMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.model.production.ChangeHouseView;
import xn.pigfarm.model.production.FosterCareModel;
import xn.pigfarm.model.production.FosterDetailModel;
import xn.pigfarm.model.production.FosterView;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.service.basicinfo.impl.BasicInfoServiceImpl;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;

public class FosterBSImpl extends BasicInfoServiceImpl implements IFosterBS {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private FosterCareMapper fosterCareMapper;

    @Autowired
    private FosterDetailMapper fosterDetailMapper;

    @Autowired
    private ILogBS logBSImpl;

    @Autowired
    private IChangeHouseBS changeHouseBSImpl;

    @Autowired
    private PigMapper pigMapper;

    @Override
    public void foster(List<FosterView> fosterViews, String eventName, long billId) throws Exception {
        List<Long> sowIds = new ArrayList<>();
        Set<Long> boardSowIds = new LinkedHashSet<>();
        Map<Long, Date> boardDateMap = new LinkedHashMap<>();
        String flag = "Y";
        for (FosterView fosterView : fosterViews) {
            sowIds.add(fosterView.getPigId());
            boardSowIds.add(fosterView.getBoardSowId());
            boardDateMap.put(fosterView.getBoardSowId(), fosterView.getEnterDate());
        }
        SqlCon pigSql = new SqlCon();
        pigSql.addMainSql("SELECT P.ROW_ID pigId,P.SWINERY_ID swineryId,P.PIG_TYPE pigType,P.SEX sex,");
        pigSql.addMainSql(" P.LINE_ID lineId,P.HOUSE_ID houseId,P.PIGPEN_ID pigpenId,P.PIG_CLASS pigClass,P.PARITY parity,");
        pigSql.addMainSql(" IFNULL(MAX(E.SORT_NBR),0)+1 hisSortNbr");
        pigSql.addMainSql(" FROM PP_L_PIG P");
        pigSql.addMainSql(" LEFT JOIN PP_L_PIG_EVENT_HIS E ON P.ROW_ID = E.PIG_ID AND E.STATUS = 1 AND E.DELETED_FLAG = 0");
        pigSql.addMainSql(" WHERE P.STATUS = 1 AND P.DELETED_FLAG = 0");
        pigSql.addCondition(StringUtil.listLongToString(sowIds) + "," + setLongToString(boardSowIds), " AND P.ROW_ID IN ?", false, true);
        pigSql.addMainSql(" GROUP BY P.ROW_ID");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", pigSql.getCondition());
        List<Map<String, Object>> sowList = paramMapper.getObjectInfos(sqlMap);

        SqlCon childSql = new SqlCon();
        childSql.addMainSql("SELECT P.ROW_ID pigId,P.ENTER_WEIGHT enterWeight,C.EAR_BRAND earBrand,P.BOARD_SOW_ID boardSowId,");
        childSql.addMainSql(" P.SWINERY_ID swineryId,P.PIG_TYPE pigType,P.SEX sex,IFNULL(MAX(E.SORT_NBR),0)+1 hisSortNbr,");
        childSql.addMainSql(" P.LINE_ID lineId,P.HOUSE_ID houseId,P.PIGPEN_ID pigpenId,P.PIG_CLASS pigClass,P.PARITY parity");
        childSql.addMainSql(" FROM PP_L_PIG P");
        childSql.addMainSql(" INNER JOIN PP_L_EAR_CODE C ON P.EAR_CODE_ID = C.ROW_ID AND C.DELETED_FLAG = 0 AND C.STATUS = 1");
        childSql.addMainSql(" LEFT JOIN PP_L_PIG_EVENT_HIS E ON P.ROW_ID = E.PIG_ID AND E.STATUS = 1 AND E.DELETED_FLAG = 0");
        childSql.addMainSql(" WHERE P.STATUS = 1 AND P.DELETED_FLAG = 0");
        childSql.addCondition(StringUtil.listLongToString(sowIds), " AND P.BOARD_SOW_ID IN ?", false, true);
        childSql.addCondition(PigConstants.PIG_TYPE_PORK, " AND P.PIG_TYPE = ?");
        childSql.addCondition(PigConstants.PIG_CLASS_BRJZ + "," + PigConstants.PIG_CLASS_BRRZ, " AND P.PIG_CLASS IN ?", false, true);
        childSql.addMainSql(" ORDER BY P.BIRTH_DATE");
        Map<String, String> csqlMap = new HashMap<>();
        csqlMap.put("sql", pigSql.getCondition());
        List<Map<String, Object>> childList = paramMapper.getObjectInfos(csqlMap);

        Map<Long, Map<String, Object>> sowMap = new HashMap<>();
        for (Map<String, Object> map : sowList) {
            sowMap.put(Maps.getLong(map, "pigId"), map);
        }
        Map<String, List<Map<String, Object>>> childMap = new HashMap<String, List<Map<String, Object>>>();
        for (Map<String, Object> map : childList) {
            String key = Maps.getString(map, "boardSowId");
            if (childMap.containsKey(key)) {
                childMap.get(key).add(map);
            } else {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                list.add(map);
                childMap.put(key, list);
            }
        }

        // 验证猪只是否满足要求
        checkSow(fosterViews, sowMap, childMap);

        // 如果是奶妈转舍，则寄养数等于带仔数（断奶+死亡后的带仔数）
        if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventName)) {
            for (FosterView fosterView : fosterViews) {
                Long fosterQty = Long.valueOf(childMap.get(fosterView.getPigId()).size());
                fosterView.setFosterQty(fosterQty);
            }
        }
        Date createDate = new Date();
        List<FosterCareModel> fostList = new ArrayList<>();
        List<ChangeHouseView> changeHouseViews = new ArrayList<>();
        List<PigEventHisModel> hisList = new ArrayList<>();
        List<FosterDetailModel> fostDetailList = new ArrayList<>();
        List<PigModel> pigModelList = new ArrayList<>();
        for (FosterView fosterView : fosterViews) {
            Map<String,Object> sow = sowMap.get(fosterView.getPigId());
            Map<String,Object> boardSow = sowMap.get(fosterView.getBoardSowId());
            List<Map<String,Object>> children = childMap.get(fosterView.getPigId());
            if (StringUtil.isBlank(fosterView.getFlag())) {
                fosterView.setFlag("Y");// 用于区分奶妈转舍仔猪是否需要做转舍，默认为Y
            }

            FosterCareModel fosterCareModel = new FosterCareModel();
            long rowId = SeqUtil.getSeq(SeqEnum.FOSTER);
            fosterCareModel.setRowId(rowId);
            fosterCareModel.setBillId(billId);
            fosterCareModel.setBoardSowId(fosterView.getBoardSowId());
            fosterCareModel.setCompanyId(getCompanyId());
            fosterCareModel.setCreateDate(createDate);
            fosterCareModel.setCreateId(getEmployeeId());
            fosterCareModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            fosterCareModel.setFarmId(getFarmId());
            fosterCareModel.setFosterDate(fosterView.getEnterDate());
            fosterCareModel.setFosterQty(fosterView.getFosterQty());
            fosterCareModel.setFosterReason(fosterView.getFosterReason());
            fosterCareModel.setFosterWeight(fosterView.getFosterWeight());
            fosterCareModel.setHouseId(Maps.getLong(sow, "houseId"));
            fosterCareModel.setLineNumber(fosterView.getLineNumber());
            fosterCareModel.setNotes(fosterView.getNotes());
            fosterCareModel.setOriginApp(fosterView.getOriginApp());
            fosterCareModel.setOriginFlag(fosterView.getOriginFlag());
            fosterCareModel.setParity(Maps.getLong(sow, "parity"));
            fosterCareModel.setPigId(fosterView.getPigId());
            fosterCareModel.setPigpenId(Maps.getLongClass(sow, "pigpenId"));
            fosterCareModel.setPigType(Maps.getString(sow, "pigType"));
            fosterCareModel.setSex(Maps.getString(sow, "sex"));
            fosterCareModel.setSortNbr(0L);
            fosterCareModel.setStatus(CommonConstants.STATUS);
            fosterCareModel.setSwineryId(Maps.getLongClass(sow, "swineryId"));
            fosterCareModel.setWorker(fosterView.getWorker());
            fostList.add(fosterCareModel);

            for (int i = 0; i < fosterView.getFosterQty(); i++) {
                Map<String, Object> child = children.get(i);
                if ("Y".equals(fosterView.getFlag())) {
                    ChangeHouseView changeHouseView = new ChangeHouseView();
                    changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_ADV);
                    changeHouseView.setLineNumber(fosterView.getLineNumber());
                    changeHouseView.setNotes(fosterView.getNotes());
                    changeHouseView.setOriginApp(fosterView.getOriginApp());
                    changeHouseView.setOriginFlag(fosterView.getOriginFlag());
                    changeHouseView.setPigId(Maps.getLong(child, "pigId"));
                    changeHouseView.setInHouseId(Maps.getLong(boardSow, "houseId"));
                    changeHouseView.setInPigpenId(Maps.getLong(boardSow, "pigpenId"));
                    changeHouseView.setChangeWeight(Maps.getDouble(child, "enterWeight"));
                    changeHouseView.setEnterDate(fosterView.getEnterDate());
                    changeHouseView.setWorker(fosterView.getWorker());
                    changeHouseView.setBillId(billId);
                    changeHouseView.setCreateId(getEmployeeId());
                    changeHouseView.setEventName(eventName);
                    changeHouseView.setEarBrand(Maps.getString(child, "earBrand"));
                    changeHouseViews.add(changeHouseView);

                    PigEventHisModel hisModel = new PigEventHisModel();
                    hisModel.setBillId(billId);
                    hisModel.setCompanyId(getCompanyId());
                    hisModel.setCreateDate(createDate);
                    hisModel.setCreateId(getEmployeeId());
                    hisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    hisModel.setEarBrand(Maps.getString(child, "earBrand"));
                    hisModel.setEnterDate(fosterView.getEnterDate());
                    hisModel.setEventName(eventName);
                    hisModel.setEventNotes("仔猪" + Maps.getString(child, "earBrand") + "从" + fosterView.getEarBrand() + "母猪寄养到" + fosterView
                            .getBoardSowEarBrand());
                    hisModel.setFarmId(getFarmId());
                    hisModel.setHouseId(Maps.getLong(boardSow, "houseId"));
                    hisModel.setLastEventDate(fosterView.getEnterDate());
                    hisModel.setLineNumber(fosterView.getLineNumber());
                    hisModel.setNotes(fosterView.getNotes());
                    hisModel.setOriginApp(fosterView.getOriginApp());
                    hisModel.setOriginFlag(fosterView.getOriginFlag());
                    hisModel.setParity(Maps.getLong(child, "parity"));
                    hisModel.setPigClass(Maps.getLong(child, "pigClass"));
                    hisModel.setPigId(Maps.getLong(child, "pigId"));
                    hisModel.setPigpenId(Maps.getLongClass(child, "pigpenId"));
                    hisModel.setPigType(Maps.getString(child, "pigType"));
                    hisModel.setSex(Maps.getString(child, "sex"));
                    hisModel.setSortNbr(Maps.getLong(child, "hisSortNbr"));
                    hisModel.setStatus(CommonConstants.STATUS);
                    hisModel.setSwineryId(Maps.getLong(child, "swineryId"));
                    hisModel.setTableName("pp_l_bill_foster_care");
                    hisModel.setTableRowId(rowId);
                    hisList.add(hisModel);

                    FosterDetailModel fosterDetailModel = new FosterDetailModel();
                    fosterDetailModel.setBillId(billId);
                    fosterDetailModel.setCompanyId(getCompanyId());
                    fosterDetailModel.setCreateDate(createDate);
                    fosterDetailModel.setCreateId(getEmployeeId());
                    fosterDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    fosterDetailModel.setFarmId(getFarmId());
                    fosterDetailModel.setLastSowId(fosterView.getPigId());
                    fosterDetailModel.setPigId(Maps.getLong(child, "pigId"));
                    fosterDetailModel.setStatus(CommonConstants.STATUS);
                    fostDetailList.add(fosterDetailModel);

                    // 仔猪
                    PigModel pigModel = new PigModel();
                    pigModel.setRowId(Maps.getLong(child, "pigId"));
                    pigModel.setBoardSowId(fosterView.getBoardSowId());
                    pigModelList.add(pigModel);
                }
            }

            PigEventHisModel hisModel = new PigEventHisModel();
            hisModel.setBillId(billId);
            hisModel.setCompanyId(getCompanyId());
            hisModel.setCreateDate(createDate);
            hisModel.setCreateId(getEmployeeId());
            hisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            hisModel.setEarBrand(fosterView.getEarBrand());
            hisModel.setEnterDate(fosterView.getEnterDate());
            hisModel.setEventName(eventName);
            hisModel.setEventNotes("耳牌号为：" + fosterView.getEarBrand() + "寄养【" + fosterView.getFosterQty() + "】头仔猪到" + fosterView.getBoardSowEarBrand()
                    + ",现存仔猪【" + (children.size() - fosterView.getFosterQty()) + "】头");
            hisModel.setFarmId(getFarmId());
            hisModel.setHouseId(Maps.getLong(sow, "houseId"));
            hisModel.setLastEventDate(fosterView.getEnterDate());
            hisModel.setLineNumber(fosterView.getLineNumber());
            hisModel.setNotes(fosterView.getNotes());
            hisModel.setOriginApp(fosterView.getOriginApp());
            hisModel.setOriginFlag(fosterView.getOriginFlag());
            hisModel.setParity(Maps.getLong(sow, "parity"));
            hisModel.setPigClass(Maps.getLong(sow, "pigClass"));
            hisModel.setPigId(Maps.getLong(sow, "pigId"));
            hisModel.setPigpenId(Maps.getLongClass(sow, "pigpenId"));
            hisModel.setPigType(Maps.getString(sow, "pigType"));
            hisModel.setSex(Maps.getString(sow, "sex"));
            hisModel.setSortNbr(Maps.getLong(sow, "hisSortNbr"));
            hisModel.setStatus(CommonConstants.STATUS);
            hisModel.setSwineryId(Maps.getLong(sow, "swineryId"));
            hisModel.setTableName("pp_l_bill_foster_care");
            hisModel.setTableRowId(rowId);
            hisList.add(hisModel);

            // 母猪
            PigModel pigModel = new PigModel();
            pigModel.setRowId(Maps.getLong(sow, "pigId"));

        }
        fosterCareMapper.inserts(fostList);
        fosterDetailMapper.inserts(fostDetailList);
        // 仔猪转舍
        changeHouseBSImpl.changeHouse(changeHouseViews, EventConstants.FOSTER, billId);
        logBSImpl.createPigEventHises(hisList, pigModelList, null);
        // 被寄养母猪一样要改变最后事件
        List<PigModel> boardSowList = new ArrayList<>();
        for (Long boardSowId : boardSowIds) {
            Date enterDate = boardDateMap.get(boardSowId);
            PigModel boardSowModel = new PigModel();
            boardSowModel.setRowId(boardSowId);
            boardSowModel.setLastBillId(billId);
            boardSowModel.setLastEventDate(enterDate);
            boardSowList.add(boardSowModel);
        }
        pigMapper.updates(boardSowList);
    }

    public void checkSow(List<FosterView> fosterViews, Map<Long, Map<String, Object>> sowMap, Map<String, List<Map<String, Object>>> childMap) {
        for (FosterView fosterView : fosterViews) {
            Map<String, Object> sow = sowMap.get(fosterView.getPigId());
            int babyNum = childMap.get(fosterView.getPigId()).size();
            if (PigConstants.PIG_CLASS_BR != (Maps.getLong(sow, "pigClass"))) {
                Thrower.throwException(ProductionException.SOW_CAN_NOT_FOSTER, fosterView.getLineNumber(), fosterView.getEarBrand());
            }
            if (babyNum <= 0) {
                Thrower.throwException(ProductionException.SOW_HAS_NO_BABY, fosterView.getLineNumber(), fosterView.getEarBrand());
            }
            if (babyNum < fosterView.getFosterQty()) {
                Thrower.throwException(ProductionException.FOSETER_QTY_MORE_THAN_PIGQTY, fosterView.getLineNumber(), fosterView.getEarBrand());
            }
            if(fosterView.getFosterQty()<=0){
                Thrower.throwException(ProductionException.FOSTER_QTY_LESS_THAN_ZERO, fosterView.getLineNumber(), fosterView.getEarBrand());
            }
        }
    }

    public String setLongToString(Set<Long> set) {
        StringBuffer ids = new StringBuffer();
        boolean flag = false;
        for (Long id : set) {
            if (flag) {
                ids.append(id);
            } else {
                flag = true;
                ids.append(",");
                ids.append(id);
            }
        }
        return ids.toString();
    }
}
