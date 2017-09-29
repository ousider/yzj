package xn.pigfarm.service.production.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import xn.core.util.ParamUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;
import xn.hana.util.HanaUtil;
import xn.pigfarm.business.production.IChangeHouseBS;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.basicinfo.PorkPigMapper;
import xn.pigfarm.mapper.hana.SysHanaInsertLogMapper;
import xn.pigfarm.mapper.production.BillMapper;
import xn.pigfarm.mapper.production.ChangeEarbrandMapper;
import xn.pigfarm.mapper.production.ChangeSwineryMapper;
import xn.pigfarm.mapper.production.EarCodeMapper;
import xn.pigfarm.mapper.production.PigEventHisMapper;
import xn.pigfarm.mapper.production.PigEventRelatesMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.mapper.production.SeedDetailMapper;
import xn.pigfarm.mapper.production.SelectBreedMapper;
import xn.pigfarm.mapper.production.SwineryMapper;
import xn.pigfarm.mapper.production.ToworkMapper;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.hana.SysHanaInsertLogModel;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.ChangeEarbrandModel;
import xn.pigfarm.model.production.ChangeHouseView;
import xn.pigfarm.model.production.ChangeSwineryModel;
import xn.pigfarm.model.production.EarCodeModel;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigEventRelatesModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.SeedDetailModel;
import xn.pigfarm.model.production.SelectBreedModel;
import xn.pigfarm.model.production.ToworkModel;
import xn.pigfarm.service.hana.ISendToSAPProductionService;
import xn.pigfarm.service.production.IEventService;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * @Description: 留种选种
 * @author yangy
 * @date 2016年10月10日 下午12:31:21
 */

@Service("EventService")
public class EventServiceImpl extends BaseServiceImpl implements IEventService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PigMapper pigMapper;

    @Autowired
    private EarCodeMapper earCodeMapper;

    @Autowired
    private PigEventHisMapper pigEventHisMapper;

    @Autowired
    private PigEventRelatesMapper pigEventRelatesMapper;

    @Autowired
    private SeedDetailMapper seedDetailMapper;

    @Autowired
    private ToworkMapper toWorkerMapper;

    @Autowired
    private IChangeHouseBS IChangeHouseBS;

    @Autowired
    private SelectBreedMapper selectBreedMapper;

    @Autowired
    private PorkPigMapper porkPigMapper;

    @Autowired
    private ChangeEarbrandMapper ChangeEarbrandMapper;

    @Autowired
    private SwineryMapper swineryMapper;

    @Autowired
    private ChangeSwineryMapper changeSwineryMapper;

    @Autowired
    private SysHanaInsertLogMapper sysHanaInsertLogMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private ISendToSAPProductionService sendToSAPProductionService;

    @Override
    public void editSeedPig(BillModel billModel, String eventName, String seedList) throws Exception {

        // 单据日期不能为空
        if (billModel.getBillDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }
        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setCreateId(getEmployeeId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode(eventName);
        // 创建表单
        billModel.setBillDate(billModel.getBillDate());
        billMapper.insert(billModel);
        long billId = billModel.getRowId();
        // 仔猪集合
        Map<Long, Map<String, List<PigModel>>> seedPigMapList = null;
        Map<String, List<PigModel>> sexMaps = null;
        // 公仔猪集合
        List<PigModel> pigMaleList = null;
        // 母仔猪集合
        List<PigModel> pigFemaleList = null;
        // 混合猪集合
        List<PigModel> pigMixList = null;
        // 留种事件集合
        List<SeedDetailModel> seedDetailModelList = null;
        // 留种日期
        Date enterDate = null;
        String settingValueByCode = ParamUtil.getSettingValueByCode("FMSFLZ");
        if (seedList != null) {
            List<HashMap> seedMapList = getJsonList(seedList, HashMap.class);
            if (seedMapList.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            List<Long> boardSowIds = new ArrayList<Long>();
            // 仔猪耳缺号集合
            List<Map<String, Object>> childPigMapList = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> seedMap : seedMapList) {
                PigModel pigModel = getBean(PigModel.class, seedMap);
                if (pigModel.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种日期不能为空！");
                }
                if (pigModel.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }
                Map<String, Object> childPigMap = new HashMap<String, Object>();
                Long pigQty = Maps.getLong(seedMap, "pigQty");
                Long aliveLitterX = Maps.getLongClass(seedMap, "aliveLitterX") == null ? 0 : Maps.getLongClass(seedMap, "aliveLitterX");
                Long aliveLitterY = Maps.getLongClass(seedMap, "aliveLitterY") == null ? 0 : Maps.getLongClass(seedMap, "aliveLitterY");
                Long seedMale = seedMap.get("seedMale") == null ? 0 : Maps.getLong(seedMap, "seedMale");
                List<Map<String, Object>> maleEarShortList = Maps.getList(seedMap, "maleEarShortList");
                Long seedFemale = seedMap.get("seedFemale") == null ? 0 : Maps.getLong(seedMap, "seedFemale");
                List<Map<String, Object>> femaleEarShortList = Maps.getList(seedMap, "femaleEarShortList");
                if ((seedMale + seedFemale) > (aliveLitterX + aliveLitterY) && "ON".equals(settingValueByCode)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种公，母的总数不能大于活仔公，母总数！");
                }
                if (pigQty == null || pigQty.equals(0L)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "带仔数不能为空！");
                }
                if (maleEarShortList == null && !seedMale.equals(0L)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种公数量和填入耳缺号数量不一致！");
                } else if (maleEarShortList != null && !seedMale.equals((Long.valueOf(maleEarShortList.size())))) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种公数量和填入耳缺号数量不一致！");
                }
                if (femaleEarShortList == null && !seedFemale.equals(0L)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种母数量和填入耳缺号数量不一致！");
                } else if (femaleEarShortList != null && !seedFemale.equals(Long.valueOf(femaleEarShortList.size()))) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种母数量和填入耳缺号数量不一致！");
                }
                if (femaleEarShortList == null && maleEarShortList == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "留种母,留种母耳缺号不能都为空！");
                }
                Date minDate = Maps.getDate(seedMap, "minValidDate", new Date());
                Date maxDate = Maps.getDate(seedMap, "maxValidDate", new Date());

                if (!(TimeUtil.daysBetween(pigModel.getEnterDate(), minDate) >= 0 && TimeUtil.daysBetween(pigModel.getEnterDate(), maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, pigModel.getLineNumber(), TimeUtil.format(pigModel.getEnterDate(),
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }
                childPigMap.put("enterDate", pigModel.getEnterDate());
                childPigMap.put("boardSowId", pigModel.getRowId());
                childPigMap.put("parity", pigModel.getParity());
                childPigMap.put("maleEarShortList", maleEarShortList);
                childPigMap.put("femaleEarShortList", femaleEarShortList);
                childPigMapList.add(childPigMap);
                boardSowIds.add(pigModel.getRowId());
            }

            String boardSowIdsString = StringUtil.listLongToString(boardSowIds);

            SqlCon sql = new SqlCon();
            sql.addCondition(boardSowIdsString, " AND BOARD_SOW_ID IN ?", false, true);
            /* sql.addCondition(PigConstants.SEED_TYPE_NO, " AND (SEED_FLAG = ?"); */
            sql.addMainSql(" AND SEED_FLAG='Y' AND IS_BATCH_SEED='Y' AND PIG_CLASS IN (12,13)");

            // 仔猪集合
            List<PigModel> pigModels = getList(pigMapper, sql);
            // 遍历仔猪集合并按照性别分类
            seedPigMapList = new HashMap<Long, Map<String, List<PigModel>>>();
            for (int i = 0; i < pigModels.size(); i++) {
                PigModel pigModel = pigModels.get(i);
                if (!seedPigMapList.containsKey(pigModel.getBoardSowId())) {
                    sexMaps = new HashMap<String, List<PigModel>>();
                    sexMaps.put(PigConstants.PIG_SEX_MALE, new ArrayList<PigModel>());
                    sexMaps.put(PigConstants.PIG_SEX_FEMALE, new ArrayList<PigModel>());
                    sexMaps.put(PigConstants.PIG_SEX_MIX, new ArrayList<PigModel>());
                    seedPigMapList.put(pigModel.getBoardSowId(), sexMaps);

                    sexMaps.get(String.valueOf(pigModel.getSex())).add(pigModel);
                } else {
                    sexMaps = seedPigMapList.get(pigModel.getBoardSowId());
                    sexMaps.get(String.valueOf(pigModel.getSex())).add(pigModel);
                }
            }
            List<PigModel> childPigModelList = new ArrayList<PigModel>();
            List<EarCodeModel> childPigEarCodeModelList = new ArrayList<EarCodeModel>();
            for (Map<String, Object> childPigMap : childPigMapList) {
                Long boardSowId = Maps.getLong(childPigMap, "boardSowId");
                enterDate = Maps.getDate(childPigMap, "enterDate");
                List<Map<String, Object>> maleEarShortList = Maps.getList(childPigMap, "maleEarShortList");
                List<Map<String, Object>> femaleEarShortList = Maps.getList(childPigMap, "femaleEarShortList");

                sexMaps = seedPigMapList.get(boardSowId) == null ? null : seedPigMapList.get(boardSowId);
                pigMaleList = sexMaps.get(PigConstants.PIG_SEX_MALE) == null ? null : sexMaps.get(PigConstants.PIG_SEX_MALE);
                pigFemaleList = sexMaps.get(PigConstants.PIG_SEX_FEMALE) == null ? null : sexMaps.get(PigConstants.PIG_SEX_FEMALE);
                pigMixList = sexMaps.get(PigConstants.PIG_SEX_MIX) == null ? null : sexMaps.get(PigConstants.PIG_SEX_MIX);
                if (maleEarShortList != null && !maleEarShortList.isEmpty()) {
                    for (Map<String, Object> earShortMap : maleEarShortList) {
                        List<PigModel> list = null;
                        if (!pigMaleList.isEmpty()) {
                            list = pigMaleList;
                        } else if (!pigMixList.isEmpty()) {
                            list = pigMixList;
                        } else if (!pigFemaleList.isEmpty()) {
                            list = pigFemaleList;
                        }
                        if (list == null || list.isEmpty()) {
                            return;
                        }
                        PigModel pigModel = list.get(0);
                        // 删除已经操作过的数据
                        list.remove(pigModel);
                        /* Long lastBillId = pigModel.getBillId();
                         * Date lastEventDate = pigModel.getEnterDate(); */

                        pigModel.setSex(PigConstants.PIG_SEX_MALE);
                        // pigModel.setBillId(billId);
                        pigModel.setLastBillId(billId);
                        pigModel.setLastEventDate(enterDate);
                        pigModel.setSeedFlag(PigConstants.SEED_TYPE_YES);
                        childPigModelList.add(pigModel);

                        long earShortIsExist = ParamUtil.isExist("pp_l_ear_code", null, Maps.getString(earShortMap, "earShort") + "," + getFarmId(),
                                "EAR_SHORT,FARM_ID");
                        if (earShortIsExist > 0) {
                            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, Maps.getString(earShortMap, "earShort"));
                        }
                        EarCodeModel earCodeModel = new EarCodeModel();
                        earCodeModel.setRowId(pigModel.getEarCodeId());
                        earCodeModel.setEarShort(Maps.getString(earShortMap, "earShort").toUpperCase());
                        childPigEarCodeModelList.add(earCodeModel);
                    }
                }
                if (femaleEarShortList != null && !femaleEarShortList.isEmpty()) {
                    for (Map<String, Object> earShortMap : femaleEarShortList) {
                        List<PigModel> list = null;
                        if (!pigFemaleList.isEmpty()) {
                            list = pigFemaleList;
                        } else if (!pigMixList.isEmpty()) {
                            list = pigMixList;
                        } else if (!pigMaleList.isEmpty()) {
                            list = pigMaleList;
                        }
                        if (list == null || list.isEmpty()) {
                            return;
                        }
                        PigModel pigModel = list.get(0);
                        // 删除已经操作过的数据
                        list.remove(pigModel);
                        /* Long lastBillId = pigModel.getBillId();
                         * Date lastEventDate = pigModel.getEnterDate(); */
                        // 删除已经操作过的数据
                        list.remove(pigModel);
                        pigModel.setSex(PigConstants.PIG_SEX_FEMALE);
                        // pigModel.setBillId(billId);
                        pigModel.setLastBillId(billId);
                        pigModel.setLastEventDate(enterDate);
                        pigModel.setSeedFlag(PigConstants.SEED_TYPE_YES);
                        childPigModelList.add(pigModel);

                        long earShortIsExist = ParamUtil.isExist("pp_l_ear_code", null, Maps.getString(earShortMap, "earShort") + "," + getFarmId(),
                                "EAR_SHORT,FARM_ID");
                        if (earShortIsExist > 0) {
                            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, Maps.getString(earShortMap, "earShort"));
                        }
                        EarCodeModel earCodeModel = new EarCodeModel();
                        earCodeModel.setRowId(pigModel.getEarCodeId());
                        earCodeModel.setEarShort(Maps.getString(earShortMap, "earShort").toUpperCase());
                        childPigEarCodeModelList.add(earCodeModel);
                    }
                }

            }
            if (childPigEarCodeModelList != null && !childPigEarCodeModelList.isEmpty()) {
                earCodeMapper.updates(childPigEarCodeModelList);
            }

            List<PigEventHisModel> pigEventHisModels = null;
            Long parity = null;
            if (childPigModelList != null && !childPigModelList.isEmpty()) {
                pigMapper.updates(childPigModelList);
                pigEventHisModels = new ArrayList<PigEventHisModel>();
                seedDetailModelList = new ArrayList<SeedDetailModel>();
                for (PigModel pigModel : childPigModelList) {
                    EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
                    for (Map<String, Object> childPigMap : childPigMapList) {
                        if (((Long) Maps.getLong(childPigMap, "boardSowId")).equals(pigModel.getBoardSowId())) {
                            parity = Maps.getLong(childPigMap, "parity");
                        }
                    }
                    SeedDetailModel seedDetailModel = new SeedDetailModel();
                    seedDetailModel.setStatus(CommonConstants.STATUS);
                    seedDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    seedDetailModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    seedDetailModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    seedDetailModel.setFarmId(getFarmId());
                    seedDetailModel.setCompanyId(getCompanyId());
                    seedDetailModel.setSowId(pigModel.getBoardSowId());
                    seedDetailModel.setSowParity(parity);
                    seedDetailModel.setPigId(pigModel.getRowId());
                    seedDetailModel.setLineId(pigModel.getLineId());
                    seedDetailModel.setSwineryId(pigModel.getSwineryId());
                    seedDetailModel.setEarShort(earCodeModel.getEarShort());
                    seedDetailModel.setHouseId(pigModel.getHouseId());
                    seedDetailModel.setPigpenId(pigModel.getPigpenId());
                    seedDetailModel.setBillId(pigModel.getLastBillId());
                    seedDetailModel.setSex(pigModel.getSex());
                    seedDetailModel.setWorker(pigModel.getWorker());
                    seedDetailModel.setSeedDate(pigModel.getLastEventDate());
                    seedDetailModel.setCreateDate(new Date());
                    seedDetailModel.setCreateId(getEmployeeId());
                    seedDetailModelList.add(seedDetailModel);

                    PigEventHisModel pigEventHisModel = new PigEventHisModel();
                    pigEventHisModel.setStatus(CommonConstants.STATUS);
                    pigEventHisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    pigEventHisModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    pigEventHisModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    pigEventHisModel.setCompanyId(getCompanyId());
                    pigEventHisModel.setFarmId(getFarmId());
                    pigEventHisModel.setBillId(pigModel.getLastBillId());
                    pigEventHisModel.setCreateDate(new Date());
                    pigEventHisModel.setCreateId(getEmployeeId());
                    pigEventHisModel.setEnterDate(pigModel.getEnterDate());
                    pigEventHisModel.setEventName(eventName);
                    pigEventHisModel.setHouseId(pigModel.getHouseId());
                    pigEventHisModel.setLastEventDate(billModel.getBillDate());
                    pigEventHisModel.setNotes(pigModel.getNotes());
                    pigEventHisModel.setPigClass(pigModel.getPigClass());
                    pigEventHisModel.setPigId(pigModel.getRowId());
                    pigEventHisModel.setPigpenId(pigModel.getPigpenId());
                    pigEventHisModel.setPigType(pigModel.getPigType());
                    pigEventHisModel.setSex(pigModel.getSex());
                    pigEventHisModel.setSwineryId(pigModel.getSwineryId());
                    pigEventHisModel.setTableName("PP_L_BILL_SEED_DETAIL");
                    pigEventHisModel.setTableRowId(pigModel.getRowId());
                    pigEventHisModel.setWorker(pigModel.getWorker());
                    pigEventHisModel.setParity(pigModel.getParity());
                    pigEventHisModel.setLineNumber(pigModel.getLineNumber());
                    pigEventHisModel.setEarBrand(earCodeModel.getEarBrand());
                    pigEventHisModel.setEventNotes("耳牌号为：" + earCodeModel.getEarBrand() + "已留种");
                    pigEventHisModels.add(pigEventHisModel);

                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }
            if (pigEventHisModels != null && !pigEventHisModels.isEmpty()) {
                pigEventHisMapper.inserts(pigEventHisModels);
            }
            if (seedDetailModelList != null && !seedDetailModelList.isEmpty()) {
                seedDetailMapper.inserts(seedDetailModelList);
            }
        }
    }

    @Override
    public void editSelectionPig(BillModel billModel, String eventName, String breedViewList) throws Exception {
        // 单据日期不能为空
        if (billModel.getBillDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }
        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setCreateId(getEmployeeId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode(eventName);
        // 创建表单
        billModel.setBillDate(billModel.getBillDate());
        billMapper.insert(billModel);
        long billId = billModel.getRowId();
        String earBrand = null;
        if (breedViewList != null) {
            List<HashMap> breedMapList = getJsonList(breedViewList, HashMap.class);
            if (breedMapList.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            List<PigModel> pigModels = new ArrayList<PigModel>();
            List<EarCodeModel> earCodeModels = new ArrayList<EarCodeModel>();
            List<PigEventHisModel> pigEventHisModels = new ArrayList<PigEventHisModel>();
            List<ToworkModel> toworkModels = new ArrayList<ToworkModel>();
            List<ToworkModel> toworkModelsForUpdate = new ArrayList<ToworkModel>();
            List<Long> pigIds = new ArrayList<Long>();
            List<Long> earCodeIds = new ArrayList<Long>();
            List<ChangeHouseView> changeHouseViews = new ArrayList<ChangeHouseView>();
            List<SelectBreedModel> selectBreedModels = new ArrayList<SelectBreedModel>();
            List<PigEventRelatesModel> pigEventRelatesModels = new ArrayList<PigEventRelatesModel>();
            List<ChangeEarbrandModel> changeEarbrandModels = new ArrayList<ChangeEarbrandModel>();
            List<Long> swineryIds = new ArrayList<Long>();
            List<ChangeSwineryModel> changeSwineryModels = new ArrayList<ChangeSwineryModel>();
            for (Map<String, Object> breedMap : breedMapList) {
                PigModel pigModel = getBean(PigModel.class, breedMap);
                PigModel pigModel_ = new PigModel();
                EarCodeModel earCodeModel = new EarCodeModel();
                ChangeEarbrandModel changeEarbrandModel = new ChangeEarbrandModel();
                PigEventHisModel pigEventHisModel = new PigEventHisModel();
                ChangeHouseView changeHouseView = new ChangeHouseView();
                ChangeSwineryModel changeSwineryModel = new ChangeSwineryModel();
                String newEarBrand = Maps.getString(breedMap, "newEarBrand");
                String inHouseId = Maps.getString(breedMap, "inHouseId");
                String inPigpenId = Maps.getString(breedMap, "inPigpenId");
                Date minValidDate = Maps.getDate(breedMap, "minValidDate", new Date());
                Date maxValidDate = Maps.getDate(breedMap, "maxValidDate", new Date());
                Double weight = Maps.getDouble(breedMap, "weight");
                String notes = Maps.getString(breedMap, "notes");
                if (pigModel.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "选种日期不能为空！");
                }
                if (pigModel.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }
                if (!(TimeUtil.daysBetween(pigModel.getEnterDate(), minValidDate) >= 0 && TimeUtil.daysBetween(pigModel.getEnterDate(),
                        maxValidDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, pigModel.getLineNumber(), TimeUtil.format(pigModel.getEnterDate(),
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minValidDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxValidDate,
                                    TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), pigModel.getEnterDate());

                if (StringUtil.isNonBlank(newEarBrand)) {
                    long earBrandIsExist = ParamUtil.isExist("pp_l_ear_code", null, new String[] { String.valueOf(getFarmId()), newEarBrand },
                            "FARM_ID,EAR_BRAND");
                    if (earBrandIsExist > 0) {
                        Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, newEarBrand);
                    }
                    earCodeModel.setRowId(pigModel.getEarCodeId());
                    earCodeModel.setEarBrand(newEarBrand.toUpperCase());
                    earCodeMapper.update(earCodeModel);
                    // earCodeModels.add(earCodeModel);

                    SqlCon lastChangeBrandSqlCon = new SqlCon();
                    lastChangeBrandSqlCon.addMainSql(
                            " SELECT IFNULL(MAX(SORT_NBR) + 1,1) sortNbr,MAX(ROW_ID) changeEarbrandId FROM pp_l_bill_change_earbrand T0 ");
                    lastChangeBrandSqlCon.addMainSql(" WHERE T0.STATUS=1 AND DELETED_FLAG=0 ");
                    lastChangeBrandSqlCon.addCondition(getFarmId(), " AND T0. FARM_ID=? ");
                    lastChangeBrandSqlCon.addCondition(pigModel.getRowId(), " AND T0.PIG_ID=? ");
                    ChangeEarbrandModel changeEarbrandModel_ = setSql(ChangeEarbrandMapper, lastChangeBrandSqlCon).get(0);

                    changeEarbrandModel.setSortNbr(changeEarbrandModel_.getSortNbr());
                    changeEarbrandModel.setStatus(CommonConstants.STATUS);
                    changeEarbrandModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    changeEarbrandModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    changeEarbrandModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    changeEarbrandModel.setNotes(notes);
                    changeEarbrandModel.setFarmId(getFarmId());
                    changeEarbrandModel.setCompanyId(getCompanyId());
                    changeEarbrandModel.setLineNumber(pigModel.getLineNumber());
                    changeEarbrandModel.setSwineryId(pigModel.getSwineryId());
                    changeEarbrandModel.setPigId(pigModel.getRowId());
                    changeEarbrandModel.setBillId(billId);
                    changeEarbrandModel.setWorker(pigModel.getWorker());
                    changeEarbrandModel.setCreateId(pigModel.getCreateId());
                    changeEarbrandModel.setCreateDate(new Date());
                    changeEarbrandModel.setEarBrand(newEarBrand);
                    changeEarbrandModel.setChangeEarbrandDate(new Date());
                    changeEarbrandModel.setChangeEarbrandId(changeEarbrandModel_.getChangeEarbrandId());
                    changeEarbrandModels.add(changeEarbrandModel);

                    earBrand = newEarBrand;
                } else {
                    EarCodeModel earCodeModel_ = earCodeMapper.searchById(Maps.getLong(breedMap, "earCodeId"));
                    earBrand = earCodeModel_.getEarBrand();
                }
                pigIds.add(pigModel.getRowId());
                earCodeIds.add(pigModel.getEarCodeId());
                // 修改选种猪的物料主数据
                SqlCon MaterialSqlCon = new SqlCon();
                MaterialSqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
                MaterialSqlCon.addCondition(pigModel.getMaterialId(), " AND MATERIAL_ID=? ");
                PorkPigModel porkPigModel = getList(porkPigMapper, MaterialSqlCon).get(0);
                if (PigConstants.PIG_SEX_MALE.equals(pigModel.getSex())) {
                    pigModel_.setSex(PigConstants.PIG_SEX_MALE);
                    pigModel_.setPigType(PigConstants.PIG_TYPE_BOAR);
                    pigModel_.setPigClass(PigConstants.PIG_CLASS_HBGZ);
                    if (porkPigModel.getStockBoarId() == null) {

                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请设置留种公物料主数据！");
                    }
                    pigModel_.setMaterialId(porkPigModel.getStockBoarId());
                } else if (PigConstants.PIG_SEX_FEMALE.equals(pigModel.getSex())) {
                    pigModel_.setSex(PigConstants.PIG_SEX_FEMALE);
                    pigModel_.setPigType(PigConstants.PIG_TYPE_SOW);
                    pigModel_.setPigClass(PigConstants.PIG_CLASS_HBMZ);
                    if (porkPigModel.getBroodSowId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请设置留种母物料主数据！");
                    }
                    pigModel_.setMaterialId(porkPigModel.getBroodSowId());
                } else if (PigConstants.PIG_SEX_MIX.equals(pigModel.getSex())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择选种类型！");
                }
                pigModel_.setRowId(pigModel.getRowId());
                pigModel_.setLastBillId(billId);
                pigModel_.setLastEventDate(pigModel.getEnterDate());
                pigModel_.setLastClassDate(pigModel.getEnterDate());
                // 所在猪群清空该猪只
                // pigModel_.setSwineryId(Long.valueOf("NULL"));
                swineryIds.add(pigModel_.getRowId());
                pigModels.add(pigModel_);

                // 当转舍不为空时，调用转舍
                if (inHouseId != null && !StringUtil.isBlank(inHouseId)) {
                    changeHouseView.setLineNumber(pigModel.getLineNumber());
                    changeHouseView.setPigType(pigModel_.getPigType());
                    changeHouseView.setPigId(pigModel.getRowId());
                    changeHouseView.setEnterDate(pigModel.getEnterDate());
                    changeHouseView.setEventName(EventConstants.SELECT_BREED_PIG);
                    changeHouseView.setWorker(pigModel.getWorker());
                    changeHouseView.setBillId(billId);
                    changeHouseView.setCreateId(pigModel.getCreateId());
                    changeHouseView.setMinValidDate(minValidDate);
                    changeHouseView.setMaxValidDate(maxValidDate);
                    // 选种转舍，转后备
                    changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_FAT_HB);
                    changeHouseView.setInHouseId(Long.valueOf(inHouseId));
                    if (!StringUtil.isBlank(inPigpenId)) {
                        changeHouseView.setInPigpenId(Long.valueOf(inPigpenId));
                    } else {
                        changeHouseView.setInPigpenId(null);
                    }
                    // changeHouseView.setInPigpenId(Long.valueOf(inPigpenId == "" ? "0" : inPigpenId));
                    changeHouseView.setChangeWeight(pigModel.getEnterWeight());
                    changeHouseView.setBackfat(0.0);
                    changeHouseView.setScore(null);
                    changeHouseView.setWeighType(null);
                    changeHouseView.setEarBrand(earBrand.toUpperCase());
                    changeHouseView.setInSwineryId(pigModel.getSwineryId());
                    changeHouseViews.add(changeHouseView);
                }

                pigEventHisModel.setStatus(CommonConstants.STATUS);
                pigEventHisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                pigEventHisModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                pigEventHisModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                pigEventHisModel.setCompanyId(getCompanyId());
                pigEventHisModel.setFarmId(getFarmId());
                pigEventHisModel.setBillId(billId);
                pigEventHisModel.setCreateDate(new Date());
                pigEventHisModel.setCreateId(getEmployeeId());
                pigEventHisModel.setEnterDate(pigModel.getEnterDate());
                pigEventHisModel.setEventName(eventName);
                pigEventHisModel.setHouseId((StringUtil.isBlank(inHouseId)) ? pigModel.getHouseId() : Long.valueOf(inHouseId));
                pigEventHisModel.setLastEventDate(pigModel.getEnterDate());
                pigEventHisModel.setNotes(pigModel.getNotes());
                pigEventHisModel.setPigClass(pigModel_.getPigClass());
                pigEventHisModel.setPigId(pigModel.getRowId());
                pigEventHisModel.setPigpenId((StringUtil.isBlank(inPigpenId)) ? pigModel.getPigpenId() : Long.valueOf(inPigpenId));
                /* if (inPigpenId == null || StringUtil.isBlank(inPigpenId)) {
                 * if (pigModel.getPigpenId() != null) {
                 * pigEventHisModel.setPigpenId(pigModel.getPigpenId());
                 * }
                 * } else {
                 * pigEventHisModel.setPigpenId(Long.parseLong(inPigpenId));
                 * } */
                // pigEventHisModel.setPigpenId((inPigpenId == null || StringUtil.isBlank(inPigpenId)) ? pigModel.getPigpenId()
                // : Long.parseLong(inPigpenId));
                pigEventHisModel.setPigType(pigModel_.getPigType());
                pigEventHisModel.setSex(pigModel_.getSex());
                pigEventHisModel.setSwineryId(pigModel.getSwineryId());
                pigEventHisModel.setTableName("PP_L_BILL_SELECT_BREED");
                pigEventHisModel.setTableRowId(pigModel.getRowId());
                pigEventHisModel.setWorker(pigModel.getWorker());
                pigEventHisModel.setParity(pigModel.getParity());
                pigEventHisModel.setLineNumber(pigModel.getLineNumber());
                pigEventHisModel.setEarBrand(earBrand);
                pigEventHisModel.setEventNotes("耳牌号为：" + earBrand.toUpperCase() + "已选种");
                pigEventHisModels.add(pigEventHisModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setStatus(CommonConstants.STATUS);
                toworkModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                toworkModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                toworkModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                toworkModel.setBillId(billId);
                toworkModel.setCompanyId(getCompanyId());
                toworkModel.setCreateDate(new Date());
                toworkModel.setCreateId(getEmployeeId());
                toworkModel.setFarmId(getFarmId());
                toworkModel.setHouseId((StringUtil.isBlank(inHouseId)) ? pigModel.getHouseId() : Long.valueOf(inHouseId));
                toworkModel.setLineId(pigModel.getLineId());
                toworkModel.setLineNumber(pigModel.getLineNumber());
                toworkModel.setNotes(pigModel.getNotes());
                toworkModel.setParity(pigModel.getParity());
                toworkModel.setPigClass(pigModel_.getPigClass());
                toworkModel.setPigId(pigModel.getRowId());
                toworkModel.setPigpenId((StringUtil.isBlank(inPigpenId)) ? pigModel.getPigpenId() : Long.valueOf(inPigpenId));
                /* if (inPigpenId == null || StringUtil.isBlank(inPigpenId)) {
                 * if (pigModel.getPigpenId() != null) {
                 * toworkModel.setPigpenId(pigModel.getPigpenId());
                 * }
                 * } else {
                 * toworkModel.setPigpenId(Long.parseLong(inPigpenId));
                 * } */
                toworkModel.setPigType(pigModel_.getPigType());
                toworkModel.setSex(pigModel_.getSex());
                toworkModel.setSwineryId(pigModel.getSwineryId());
                toworkModel.setToworkDate(pigModel.getEnterDate());
                toworkModel.setWorker(pigModel.getWorker());
                ToworkModel mode = this.getChangeClass(getFarmId(), pigModel.getRowId());
                toworkModel.setChangePigClassId(mode.getChangePigClassId());
                toworkModel.setSortNbr(mode.getSortNbr());

                toworkModels.add(toworkModel);

                /********* 补充 CB 2017-1-13 15:24 begin ********/
                toworkModel = new ToworkModel();
                if (1 == Integer.valueOf(pigModel_.getPigType())) {
                    toworkModel.setPigClassOut(1L);
                } else {
                    toworkModel.setPigClassOut(3L);
                }
                toworkModel.setToworkDateOut(pigModel.getEnterDate());
                toworkModel.setRowId(mode.getChangePigClassId());
                toworkModelsForUpdate.add(toworkModel);

                /********* 补充 CB 2017-1-13 15:24 begin ********/

                SelectBreedModel selectBreedModel = new SelectBreedModel();
                selectBreedModel.setStatus(CommonConstants.STATUS);
                selectBreedModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                selectBreedModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                selectBreedModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                selectBreedModel.setNotes(notes);
                selectBreedModel.setFarmId(getFarmId());
                selectBreedModel.setCompanyId(getCompanyId());
                selectBreedModel.setLineNumber(pigModel.getLineNumber());
                selectBreedModel.setPigId(pigModel.getRowId());
                selectBreedModel.setPigSex(pigModel_.getSex());
                selectBreedModel.setSwineryId(pigModel.getSwineryId());
                selectBreedModel.setEarBrand(earBrand.toUpperCase());
                selectBreedModel.setHouseId(pigModel.getHouseId());
                selectBreedModel.setPigpenId(pigModel.getPigpenId());
                selectBreedModel.setInHouseId((StringUtil.isBlank(inHouseId)) ? null : Long.valueOf(inHouseId));
                selectBreedModel.setInPigpenId((StringUtil.isBlank(inPigpenId)) ? null : Long.valueOf(inPigpenId));
                /* if (inPigpenId == null || StringUtil.isBlank(inPigpenId)) {
                 * if (pigModel.getPigpenId() != null) {
                 * selectBreedModel.setInPigpenId(pigModel.getPigpenId());
                 * }
                 * } else {
                 * selectBreedModel.setInPigpenId(Long.parseLong(inPigpenId));
                 * } */
                // selectBreedModel.setInPigpenId((inPigpenId == null || StringUtil.isBlank(inPigpenId)) ? 0 : Long.parseLong(inPigpenId));
                selectBreedModel.setBillId(billId);
                selectBreedModel.setLastMaterialId(pigModel.getMaterialId());
                selectBreedModel.setWeight(weight);
                selectBreedModel.setPigType(pigModel_.getPigType());
                selectBreedModel.setWorker(pigModel.getWorker());
                selectBreedModel.setSeletcDate(pigModel.getEnterDate());
                selectBreedModel.setCreateId(getEmployeeId());
                selectBreedModel.setCreateDate(new Date());
                selectBreedModels.add(selectBreedModel);

                /* PigEventRelatesModel pigEventRelatesModel = new PigEventRelatesModel();
                 * pigEventRelatesModel.setStatus(CommonConstants.STATUS);
                 * pigEventRelatesModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                 * pigEventRelatesModel.setFarmId(getFarmId());
                 * pigEventRelatesModel.setCompanyId(getCompanyId());
                 * pigEventRelatesModel.setSwineryId(pigModel.getSwineryId());
                 * pigEventRelatesModel.setPigId(pigModel.getRowId());
                 * pigEventRelatesModel.setLineId(pigModel.getLineId());
                 * pigEventRelatesModel.setHouseId(StringUtil.isBlank(inHouseId) ? pigModel.getHouseId() : Long.valueOf(inHouseId));
                 * pigEventRelatesModel.setPigpenId(StringUtil.isBlank(inPigpenId) ? pigModel.getPigpenId() : Long.valueOf(inPigpenId));
                 * if (inPigpenId == null || StringUtil.isBlank(inPigpenId)) {
                 * if (pigModel.getPigpenId() != null) {
                 * pigEventRelatesModel.setPigpenId(pigModel.getPigpenId());
                 * }
                 * } else {
                 * pigEventRelatesModel.setPigpenId(Long.parseLong(inPigpenId));
                 * }
                 * // pigEventRelatesModel.setPigpenId((inPigpenId == null || StringUtil.isBlank(inPigpenId)) ? pigModel.getPigpenId():
                 * // Long.parseLong(inPigpenId));
                 * pigEventRelatesModel.setEarBrand(earBrand.toUpperCase());
                 * pigEventRelatesModel.setPigType(pigModel_.getPigType());
                 * pigEventRelatesModel.setSex(pigModel_.getSex());
                 * pigEventRelatesModel.setPigClass(pigModel_.getPigClass());
                 * pigEventRelatesModel.setParity(pigModel.getParity());
                 * pigEventRelatesModel.setBillId(billId);
                 * pigEventRelatesModel.setEventDate(pigModel.getEnterDate());
                 * SqlCon sqlCon = new SqlCon();
                 * sqlCon.addMainSql(" SELECT T0.BILL_ID AS lastBillId,T0.EVENT_DATE AS lastEventDate FROM PP_L_PIG_EVENT_RELATES T0 ");
                 * sqlCon.addMainSql(
                 * " INNER JOIN pp_l_pig T1 ON T1.ROW_ID = T0.PIG_ID AND T1.STATUS = T0.STATUS AND T1.DELETED_FLAG = T0.DELETED_FLAG AND T1.FARM_ID= T0.FARM_ID "
                 * );
                 * sqlCon.addCondition(getFarmId(), " WHERE T0.STATUS=1 AND T0.DELETED_FLAG=0 AND T0.FARM_ID= ? ");
                 * sqlCon.addCondition(pigModel.getRowId(), " AND T0.PIG_ID= ? ");
                 * sqlCon.addMainSql(" ORDER BY T0.ROW_ID DESC LIMIT 1 ");
                 * List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, sqlCon);
                 * if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
                 * pigEventRelatesModel.setLastBillId(pigEventRelatesModelList.get(0).getLastBillId());
                 * pigEventRelatesModel.setLastEventDate(pigEventRelatesModelList.get(0).getEventDate());
                 * }
                 * pigEventRelatesModels.add(pigEventRelatesModel); */
                /*************************** 2017-1-16 yangy 选种批次清空 ***************************************/
                SqlCon lastChangeSwinerySqlCon = new SqlCon();
                lastChangeSwinerySqlCon.addMainSql(
                        " SELECT IFNULL(MAX(SORT_NBR),0)+1 sortNbr, MAX(ROW_ID) changeSwineryId FROM pp_l_bill_change_swinery ");
                lastChangeSwinerySqlCon.addMainSql(" WHERE DELETED_FLAG = 0 AND STATUS = 1 ");
                lastChangeSwinerySqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
                lastChangeSwinerySqlCon.addCondition(pigModel.getRowId(), " AND PIG_ID=? ");
                ChangeSwineryModel lastChangeSwineryModel = setSql(changeSwineryMapper, lastChangeSwinerySqlCon).get(0);
                changeSwineryModel.setSortNbr(lastChangeSwineryModel.getSortNbr());
                changeSwineryModel.setStatus(CommonConstants.STATUS);
                changeSwineryModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                changeSwineryModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                changeSwineryModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                changeSwineryModel.setNotes(notes);
                changeSwineryModel.setFarmId(getFarmId());
                changeSwineryModel.setCompanyId(getCompanyId());
                changeSwineryModel.setLineNumber(pigModel.getLineNumber());
                changeSwineryModel.setSwineryId(null);
                changeSwineryModel.setPigId(pigModel.getRowId());
                changeSwineryModel.setLineId(pigModel.getLineId());
                changeSwineryModel.setHouseId(pigModel.getHouseId());
                changeSwineryModel.setPigpenId(pigModel.getPigpenId());
                changeSwineryModel.setBillId(billId);
                changeSwineryModel.setWorker(pigModel.getWorker());
                changeSwineryModel.setCreateId(getEmployeeId());
                changeSwineryModel.setCreateDate(new Date());
                changeSwineryModel.setPigType(pigModel_.getPigType());
                changeSwineryModel.setSex(pigModel.getSex());
                changeSwineryModel.setChangeSwineryDate(pigModel.getEnterDate());
                changeSwineryModel.setPigClass(pigModel_.getPigClass());
                changeSwineryModel.setChangeSwineryId(lastChangeSwineryModel.getChangeSwineryId());
                changeSwineryModels.add(changeSwineryModel);
                /*************************** 2017-1-16 yangy 选种批次清空 ***************************************/
                /*************************** 2017-5-9 yangy 转批次记录更新上一条change_swinery_date_out ***************************************/
                ChangeSwineryModel changeSwineryModel_ = new ChangeSwineryModel();
                changeSwineryModel_.setChangeSwineryDateOut(pigModel.getEnterDate());
                changeSwineryModel_.setRowId(lastChangeSwineryModel.getChangeSwineryId());
                changeSwineryMapper.update(changeSwineryModel_);
                /*************************** 2017-5-9 yangy 转批次记录更新上一条change_swinery_date_out ***************************************/
            }
            if (changeHouseViews != null && !changeHouseViews.isEmpty()) {
                IChangeHouseBS.changeHouse(changeHouseViews, EventConstants.SELECT_BREED_PIG, billId);
            }
            /* if (earCodeModels != null && !earCodeModels.isEmpty()) {
             * earCodeMapper.updates(earCodeModels);
             * } */
            if (changeEarbrandModels != null && !changeEarbrandModels.isEmpty()) {
                ChangeEarbrandMapper.inserts(changeEarbrandModels);
            }
            if (pigModels != null && !pigModels.isEmpty()) {
                pigMapper.updates(pigModels);
            }
            if (toworkModels != null && !toworkModels.isEmpty()) {
                /********* 补充 CB 2016-12-09 13:24 begin ********/
                toWorkerMapper.updates(toworkModelsForUpdate);
                /********* 补充 CB 2016-12-09 13:24 end ********/

                toWorkerMapper.inserts(toworkModels);
            }
            /******************************** 2017-9-4 添加tableRowId **************************************/
            /* if (selectBreedModels != null && !selectBreedModels.isEmpty()) {
             * selectBreedMapper.inserts(selectBreedModels);
             * } */
            if (pigEventHisModels != null && !pigEventHisModels.isEmpty() && selectBreedModels != null && !selectBreedModels.isEmpty()) {
                for (SelectBreedModel selectBreedModel : selectBreedModels) {
                    selectBreedMapper.insert(selectBreedModel);
                    for (PigEventHisModel pigEventHisModel : pigEventHisModels) {
                        if (pigEventHisModel.getPigId().equals(selectBreedModel.getPigId())) {
                            pigEventHisModel.setTableRowId(selectBreedModel.getRowId());
                            break;
                        }
                    }
                }
                /******************************** 2017-9-4 添加tableRowId **************************************/
                pigEventHisMapper.inserts(pigEventHisModels);
            }
            /* if (pigEventRelatesModels != null && !pigEventRelatesModels.isEmpty()) {
             * pigEventRelatesMapper.inserts(pigEventRelatesModels);
             * } */
            /*************************** 2017-1-16 yangy 选种批次清空 ***************************************/
            if (swineryIds != null && !swineryIds.isEmpty()) {
                String arraySwineryIds = StringUtil.listLongToString(swineryIds);
                SqlCon updateSwinerySqlCon = new SqlCon();
                updateSwinerySqlCon.addMainSql(" UPDATE PP_L_PIG SET SWINERY_ID=NULL ");
                updateSwinerySqlCon.addCondition(arraySwineryIds, " WHERE ROW_ID IN ? ", false, true);
                setSql(swineryMapper, updateSwinerySqlCon);
            }
            if (changeSwineryModels != null && !changeSwineryModels.isEmpty()) {
                changeSwineryMapper.inserts(changeSwineryModels);
            }
            /*************************** 2017-1-16 yangy 选种批次清空 ***************************************/
            /*************************** 2017-5-17 yangy 选种批次清空 ***************************************/
            for (Map<String, Object> breedMap : breedMapList) {
                long pigId = Maps.getLong(breedMap, "pigId");
                insertPigEventRelates(pigId);
            }
            /*************************** 2017-5-17 yangy 选种批次清空 ***************************************/

        }
    }

    // 插入PigEventRelates
    private void insertPigEventRelates(Long pigId) {
        SqlCon insertPigEventRelatesSqlCon = new SqlCon();
        insertPigEventRelatesSqlCon.addMainSql(
                "SELECT '1' AS status, '0' AS deletedFlag, T1.FARM_ID AS farmId, T1.COMPANY_ID AS companyId, T1.SWINERY_ID AS swineryId, T1.ROW_ID AS pigId, T1.LINE_ID AS lineId, T1.HOUSE_ID AS houseId, T1.PIGPEN_ID AS pigpenId");
        insertPigEventRelatesSqlCon.addMainSql(
                ", (SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE STATUS = '1' AND DELETED_FLAG = '0' AND ROW_ID = T1.EAR_CODE_ID) AS earBrand, T1.PIG_TYPE AS pigType");
        insertPigEventRelatesSqlCon.addMainSql(
                ", T1.SEX AS sex, T1.PIG_CLASS AS pigClass, T1.PARITY AS parity, T1.LAST_BILL_ID AS billId, T1.LAST_EVENT_DATE AS eventDate, T2.billId AS lastBillId, T2.eventDate AS lastEventDate FROM PP_L_PIG T1");
        insertPigEventRelatesSqlCon.addMainSql(
                " INNER JOIN (SELECT PIG_ID AS pigId,FARM_ID AS farmId, BILL_ID AS billId, EVENT_DATE AS eventDate FROM PP_L_PIG_EVENT_RELATES ");
        insertPigEventRelatesSqlCon.addMainSql(" WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        insertPigEventRelatesSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        insertPigEventRelatesSqlCon.addCondition(pigId, " AND PIG_ID = ?");
        insertPigEventRelatesSqlCon.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1) T2");
        insertPigEventRelatesSqlCon.addMainSql(" ON(T1.ROW_ID = T2.pigId AND T1.FARM_ID = T2.farmId)");
        insertPigEventRelatesSqlCon.addMainSql(" WHERE T1.STATUS = '1' AND T1.DELETED_FLAG = '0'");
        insertPigEventRelatesSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        insertPigEventRelatesSqlCon.addCondition(pigId, " AND T1.ROW_ID = ?");

        List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, insertPigEventRelatesSqlCon);

        if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
            pigEventRelatesMapper.inserts(pigEventRelatesModelList);
        }
    }

    public ToworkModel getChangeClass(long farmId, long pigId) {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT IF(MAX(SORT_NBR) IS NULL,0,MAX(SORT_NBR)+1) SORT_NBR,MAX(ROW_ID) CHANGE_PIG_CLASS_ID ");
        sql.addMainSql(" FROM pp_l_bill_towork T0 WHERE T0.STATUS = 1 AND T0.DELETED_FLAG = 0 ");
        sql.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ? ");
        sql.addConditionWithNull(pigId, " AND T0.PIG_ID = ? ");
        List<ToworkModel> listWork = setSql(toWorkerMapper, sql);
        return listWork.get(0);
    }

    // 验证是否可以进行事件操作(关账-上传sap) 入参:enterDate
    public void sapEventMasage(Long farmId, Date enterDate) throws Exception {
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        if (!isToHana) {
            return;
        }
        String settingValueByCodeSapMaxDelay = paramMapper.getSettingValueByCode(getCompanyId(), farmId, "SAP_MAX_DELAY");
        Map<String, String> startAndEndDate = sendToSAPProductionService.getStartAndEndDate();
        String endDate = Maps.getString(startAndEndDate, "endDate");
        String minDate = Maps.getString(startAndEndDate, "startDate");
        String maxDate = TimeUtil.dateAddDay(endDate, Integer.valueOf(settingValueByCodeSapMaxDelay));

        String settingValueByCodeSapOffClosing = paramMapper.getSettingValueByCode(getCompanyId(), farmId, "SAP_OFF_CLOSING");

        // 获取最后上传SAP数据记录
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(farmId, " AND FARM_ID = ? ");
        sqlCon.addCondition(endDate, " AND TO_SAP_DATE = DATE(?) ");
        sqlCon.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1 ");
        List<SysHanaInsertLogModel> list = getList(sysHanaInsertLogMapper, sqlCon);

        long compareDate1 = TimeUtil.compareDate(enterDate, TimeUtil.parseDate(minDate), Calendar.DATE);
        long compareDate2 = TimeUtil.compareDate(TimeUtil.parseDate(maxDate), enterDate, Calendar.DATE);
        long compareDate3 = TimeUtil.compareDate(enterDate, TimeUtil.parseDate(endDate), Calendar.DATE);
        if ("ON".equals(settingValueByCodeSapOffClosing)) {
            if (list.size() > 0) {
                if (compareDate3 <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "不允许修改 [" + endDate + "] 已盘存上传SAP的数据！");
                }
                // 事件日期 > 最大延时日期
            } else if (compareDate2 < 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请上传SAP后,再操作[" + maxDate + "]后的数据,请联系财务或管理员及时上传SAP！");
                // 事件日期 < 最小日期
            } else if (compareDate1 < 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "不能操作[" + minDate + "]前,已关账的数据！");
            }
        } else {
            // 不关账
            // if (list.size() > 0 && compareDate2 < 0) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请上传SAP后再操作[" + maxDate + "]后的数据！");
            // }
        }
    }
}
