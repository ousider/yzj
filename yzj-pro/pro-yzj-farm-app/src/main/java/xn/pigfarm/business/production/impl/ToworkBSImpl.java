package xn.pigfarm.business.production.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import xn.core.data.SqlCon;
import xn.core.data.enums.NameEnum;
import xn.core.data.enums.SeqEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.SeqUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.pigfarm.business.log.ILogBS;
import xn.pigfarm.business.production.IToWorkBS;
import xn.pigfarm.exception.production.ProductionException;
import xn.pigfarm.mapper.production.ToworkMapper;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.ToworkModel;
import xn.pigfarm.service.basicinfo.impl.BasicInfoServiceImpl;
import xn.pigfarm.util.constants.PigConstants;

public class ToworkBSImpl extends BasicInfoServiceImpl implements IToWorkBS {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private ToworkMapper toworkMapper;

    @Autowired
    private ILogBS logBSImpl;

    @Override
    public void towork(List<ToworkModel> toworkModels, List<PigModel> pigModelList, String eventName, long billId) throws Exception {

        List<Long> pigIds = new ArrayList<Long>();
        for (ToworkModel model : toworkModels) {
            pigIds.add(model.getPigId());
        }
        SqlCon toworkSql = new SqlCon();
        toworkSql.addMainSql("SELECT P.ROW_ID pigId,P.COMPANY_ID companyId,P.FARM_ID farmId,P.SWINERY_ID swineryId,P.PIG_TYPE pigType,P.SEX sex,");
        toworkSql.addMainSql(" P.LINE_ID lineId,P.HOUSE_ID houseId,P.PIGPEN_ID pigpenId,P.PIG_CLASS pigClass,P.PARITY parity,C.EAR_BRAND earBrand,");
        toworkSql.addMainSql(" (SELECT IFNULL(MAX(SORT_NBR),0)+1 FROM PP_L_BILL_TOWORK WHERE DELETED_FLAG = 0 AND STATUS = 1 AND PIG_ID = P.ROW_ID");
        toworkSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        toworkSql.addMainSql(" ) AS lastTableSortNbr,");
        toworkSql.addMainSql(" (SELECT MAX(ROW_ID) FROM PP_L_BILL_TOWORK WHERE DELETED_FLAG = 0 AND STATUS = 1 AND PIG_ID = P.ROW_ID");
        toworkSql.addCondition(getFarmId(), " AND FARM_ID = ?");
        toworkSql.addMainSql(" ) AS lastTableId,");
        toworkSql.addMainSql(" (SELECT IFNULL(MAX(E.SORT_NBR),0)+1 hisSortNbr FROM PP_L_PIG_EVENT_HIS WHERE DELETED_FLAG = 0 AND STATUS = 1");
        toworkSql.addCondition(getFarmId(), "  AND PIG_ID = P.ROW_ID AND FARM_ID = ?");
        toworkSql.addMainSql(" ) AS hisSortNbr,");
        toworkSql.addMainSql(" (SELECT IFNULL(MAX(PRO_NO),1) FROM pp_l_bill_breed WHERE DELETED_FLAG = 0 AND PIG_ID = P.ROW_ID ");
        toworkSql.addMainSql(" AND PARITY = P.PARITY) AS proNo");
        toworkSql.addMainSql(" FROM PP_L_PIG P");
        toworkSql.addMainSql(" INNER JOIN PP_L_EAR_CODE C");
        toworkSql.addMainSql(" ON P.EAR_CODE_ID = C.ROW_ID AND C.DELETED_FLAG = 0");
        toworkSql.addMainSql(" WHERE P.STATUS = 1 AND P.DELETED_FLAG = 0");
        toworkSql.addCondition(StringUtil.listLongToString(pigIds), " AND P.ROW_ID IN ?", false, true);
        toworkSql.addCondition(StringUtil.listLongToString(pigIds), " ORDER BY FIELD ", false, true);

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", toworkSql.getCondition());

        List<Map<String, Object>> pigInfos = paramMapper.getObjectInfos(sqlMap);

        // 验证
        this.checkWork(toworkModels, pigInfos);
        Date createDate = new Date();
        List<ToworkModel> updateWorkList = new ArrayList<>();
        List<PigEventHisModel> eventHisList = new ArrayList<>();

        for (int i = 0; i < toworkModels.size(); i++) {
            ToworkModel toworkModel = toworkModels.get(i);
            Map<String, Object> pigInfo = pigInfos.get(i);
            PigModel pigModel = pigModelList.get(i);
            long rowId = SeqUtil.getSeq(SeqEnum.TOWORK);
            toworkModel.setRowId(rowId);
            toworkModel.setBillId(billId);
            toworkModel.setCompanyId(getCompanyId());
            toworkModel.setCreateDate(createDate);
            toworkModel.setCreateId(getEmployeeId());
            toworkModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            toworkModel.setFarmId(getFarmId());
            toworkModel.setHouseId(Maps.getLong(pigInfo, "houseId"));
            toworkModel.setParity(Maps.getLong(pigInfo, "parity"));
            toworkModel.setPigpenId(Maps.getLongClass(pigInfo, "pigpenId"));
            toworkModel.setPigType(Maps.getString(pigInfo, "pigType"));
            toworkModel.setProNo(Maps.getLongClass(pigInfo, "proNo"));
            toworkModel.setSex(Maps.getString(pigInfo, "sex"));
            toworkModel.setSortNbr(Maps.getLongClass(pigInfo, "lastTableSortNbr"));
            toworkModel.setStatus(CommonConstants.STATUS);
            toworkModel.setSwineryId(Maps.getLongClass(pigInfo, "swineryId"));

            // 跟新猪只
            pigModel.setPigClass(toworkModel.getPigClass());
            pigModel.setLastClassDate(toworkModel.getToworkDate());

            // 跟新上一条记录
            ToworkModel updateModel = new ToworkModel();
            updateModel.setRowId(Maps.getLongClass(pigInfo, "lastTableId"));
            updateModel.setToworkDateOut(toworkModel.getToworkDate());
            updateModel.setPigClassOut(toworkModel.getPigClass());
            updateWorkList.add(updateModel);

            // 创建历史记录
            PigEventHisModel hisModel = new PigEventHisModel();
            hisModel.setBillId(billId);
            hisModel.setCompanyId(getCompanyId());
            hisModel.setCreateDate(createDate);
            hisModel.setCreateId(getEmployeeId());
            hisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            hisModel.setEarBrand(Maps.getString(pigInfo, "earBrand"));
            hisModel.setEnterDate(toworkModel.getToworkDate());
            hisModel.setEventName(eventName);
            hisModel.setEventNotes("耳牌号为：" + Maps.getString(pigInfo, "earBrand") + "从【" + CacheUtil.getName(Maps.getString(pigInfo, "pigClass"),
                    NameEnum.PIG_CLASS_NAME) + "】状态转到【" + toworkModel.getPigClass() + "】状态");
            hisModel.setFarmId(getFarmId());
            hisModel.setHouseId(Maps.getLong(pigInfo, "houseId"));
            hisModel.setLastEventDate(toworkModel.getToworkDate());
            hisModel.setLineNumber(toworkModel.getLineNumber());
            hisModel.setNotes(toworkModel.getNotes());
            hisModel.setOriginApp(toworkModel.getOriginApp());
            hisModel.setOriginFlag(toworkModel.getOriginFlag());
            hisModel.setParity(Maps.getLong(pigInfo, "parity"));
            hisModel.setPigClass(toworkModel.getPigClass());
            hisModel.setPigId(toworkModel.getPigId());
            hisModel.setPigpenId(Maps.getLongClass(pigInfo, "pigpenId"));
            hisModel.setPigType(Maps.getString(pigInfo, "pigType"));
            hisModel.setSex(Maps.getString(pigInfo, "sex"));
            hisModel.setSortNbr(Maps.getLongClass(pigInfo, "hisSortNbr"));
            hisModel.setStatus(CommonConstants.STATUS);
            hisModel.setSwineryId(Maps.getLongClass(pigInfo, "swineryId"));
            hisModel.setTableName("pp_l_bill_towork");
            hisModel.setTableRowId(rowId);
            hisModel.setWorker(toworkModel.getWorker());
            eventHisList.add(hisModel);
        }
        toworkMapper.inserts(toworkModels);
        toworkMapper.updates(updateWorkList);
        logBSImpl.createPigEventHises(eventHisList, pigModelList, null);

    }

    public void checkWork(List<ToworkModel> toworkModels, List<Map<String, Object>> pigInfos) {
        for (int i = 0; i < toworkModels.size(); i++) {
            ToworkModel toworkModel = toworkModels.get(i);
            Map<String, Object> pigInfo = pigInfos.get(i);
            if (toworkModel.getPigClass().equals(Maps.getLong(pigInfo, "pigClass")) && Maps.getLongClass(pigInfo, "lastTableId") == null) {
                Thrower.throwException(ProductionException.PIG_CLASS_IS_SAME, toworkModel.getLineNumber(), Maps.getString(pigInfo, "earBrand"),
                        toworkModel.getPigClass());
            }
            if (toworkModel.getToworkDate() == null) {
                Thrower.throwException(ProductionException.TOWORK_DATE_CAN_NOT_BE_NULL, toworkModel.getLineNumber(), Maps.getString(pigInfo,
                        "earBrand"));
            }
            if (PigConstants.PIG_CLASS_SCGZ == toworkModel.getPigClass() && !PigConstants.PIG_TYPE_BOAR.equals(Maps.getString(pigInfo, "pigType"))) {
                Thrower.throwException(ProductionException.BOAR_RESERVE_TOPRODUCT_IS_NOT_BOAR, toworkModel.getLineNumber(), Maps.getString(pigInfo,
                        "earBrand"));
            }
            if (PigConstants.PIG_CLASS_HBDP == toworkModel.getPigClass() && !PigConstants.PIG_TYPE_SOW.equals(Maps.getString(pigInfo, "pigType"))) {
                Thrower.throwException(ProductionException.SOW_RESERVE_IS_NOT_SOW, toworkModel.getLineNumber(), Maps.getString(pigInfo, "earBrand"));
            }
            if (PigConstants.PIG_CLASS_BF == toworkModel.getPigClass() && !PigConstants.PIG_TYPE_PORK.equals(Maps.getString(pigInfo, "pigType"))) {
                Thrower.throwException(ProductionException.TO_CHILD_CARE_IS_NOT_PORK, toworkModel.getLineNumber(), Maps.getString(pigInfo,
                        "earBrand"));
            }
            if (PigConstants.PIG_CLASS_YH == toworkModel.getPigClass() && !PigConstants.PIG_TYPE_PORK.equals(Maps.getString(pigInfo, "pigType"))) {
                Thrower.throwException(ProductionException.TO_CHILD_CARE_IS_NOT_PORK, toworkModel.getLineNumber(), Maps.getString(pigInfo,
                        "earBrand"));
            }
        }
    }
}
