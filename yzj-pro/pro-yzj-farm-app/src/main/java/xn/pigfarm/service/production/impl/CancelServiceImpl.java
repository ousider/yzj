package xn.pigfarm.service.production.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaGatherInvoiceHeaderDetail;
import xn.hana.model.common.HanaGatherInvoiceRowsDetail;
import xn.hana.model.common.HanaPigProduct;
import xn.hana.model.common.HanaSaleBill;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.model.common.MTC_OITM;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.production.ProductionException;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.hana.SysHanaInsertLogMapper;
import xn.pigfarm.mapper.production.BackfatMapper;
import xn.pigfarm.mapper.production.BillMapper;
import xn.pigfarm.mapper.production.BoarObsoleteMapper;
import xn.pigfarm.mapper.production.ChangeEarbrandMapper;
import xn.pigfarm.mapper.production.ChangeHouseMapper;
import xn.pigfarm.mapper.production.ChangeSwineryMapper;
import xn.pigfarm.mapper.production.ChildDieMapper;
import xn.pigfarm.mapper.production.DeliveryMapper;
import xn.pigfarm.mapper.production.EarCodeMapper;
import xn.pigfarm.mapper.production.FosterCareMapper;
import xn.pigfarm.mapper.production.FosterDetailMapper;
import xn.pigfarm.mapper.production.LeaveMapper;
import xn.pigfarm.mapper.production.NurseMapper;
import xn.pigfarm.mapper.production.ParityMapper;
import xn.pigfarm.mapper.production.PigEventHisMapper;
import xn.pigfarm.mapper.production.PigEventRelatesMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.mapper.production.PigSaleDetailMapper;
import xn.pigfarm.mapper.production.PigSaleMapper;
import xn.pigfarm.mapper.production.SemenMapper;
import xn.pigfarm.mapper.production.SemenSaleDetailMapper;
import xn.pigfarm.mapper.production.SemenSaleMapper;
import xn.pigfarm.mapper.production.SpermInfoMapper;
import xn.pigfarm.mapper.production.SwineryDayageChangeMapper;
import xn.pigfarm.mapper.production.SwineryMapper;
import xn.pigfarm.mapper.production.ToChildMapper;
import xn.pigfarm.mapper.production.ToworkMapper;
import xn.pigfarm.mapper.production.WeanDetailMapper;
import xn.pigfarm.mapper.production.WeanMapper;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.hana.SysHanaInsertLogModel;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.ChangeHouseModel;
import xn.pigfarm.model.production.ChangeSwineryModel;
import xn.pigfarm.model.production.ChildDieModel;
import xn.pigfarm.model.production.DeliveryModel;
import xn.pigfarm.model.production.EarCodeModel;
import xn.pigfarm.model.production.FosterCareModel;
import xn.pigfarm.model.production.LeaveModel;
import xn.pigfarm.model.production.NurseModel;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigEventRelatesModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.PigSaleModel;
import xn.pigfarm.model.production.SemenModel;
import xn.pigfarm.model.production.SemenSaleDetailModel;
import xn.pigfarm.model.production.SemenSaleModel;
import xn.pigfarm.model.production.SpermInfoModel;
import xn.pigfarm.model.production.SwineryDayageChangeModel;
import xn.pigfarm.model.production.SwineryModel;
import xn.pigfarm.model.production.ToChildModel;
import xn.pigfarm.model.production.ToworkModel;
import xn.pigfarm.service.hana.ISendToSAPProductionService;
import xn.pigfarm.service.production.ICancelService;
import xn.pigfarm.util.constants.AccountConstants;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;
import xn.pigfarm.util.constants.SpermConstants;
import xn.pigfarm.util.enums.PigInfoEnum;

/**
 * @Description: 撤销管理
 * @author thl
 * @date 2016年7月20日 上午13:02:58
 */

@Service("CancelService")
public class CancelServiceImpl extends BaseServiceImpl implements ICancelService {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private ToworkMapper toworkMapper;

    @Autowired
    private SwineryMapper swineryMapper;

    @Autowired
    private PigMapper pigMapper;

    @Autowired
    private ChangeHouseMapper changeHouseMapper;

    @Autowired
    private ChangeSwineryMapper changeSwineryMapper;

    @Autowired
    private ChangeEarbrandMapper changeEarbrandMapper;

    @Autowired
    private EarCodeMapper earCodeMapper;

    @Autowired
    private PigEventRelatesMapper pigEventRelatesMapper;

    @Autowired
    private PigEventHisMapper pigEventHisMapper;

    @Autowired
    private ChildDieMapper childDieMapper;

    @Autowired
    private SpermInfoMapper spermInfoMapper;

    @Autowired
    private ParityMapper parityMapper;

    @Autowired
    private SemenMapper semenMapper;

    @Autowired
    private BackfatMapper backfatMapper;

    @Autowired
    private WeanMapper weanMapper;

    @Autowired
    private WeanDetailMapper weanDetailMapper;

    @Autowired
    private FosterCareMapper fosterCareMapper;

    @Autowired
    private FosterDetailMapper fosterDetailMapper;

    @Autowired
    private PigSaleMapper pigSaleMapper;

    @Autowired
    private PigSaleDetailMapper pigSaleDetailMapper;

    @Autowired
    private BoarObsoleteMapper boarObsoleteMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private NurseMapper nurseMapper;

    @Autowired
    private SemenSaleMapper semenSaleMapper;

    @Autowired
    private SemenSaleDetailMapper semenSaleDetailMapper;

    @Autowired
    private SwineryDayageChangeMapper swineryDayageChangeMapper;

    @Autowired
    private ToChildMapper toChildMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private SysHanaInsertLogMapper sysHanaInsertLogMapper;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private ISendToSAPProductionService sendToSAPProductionService;

    @Override
    public void editEventCancel(Map<String, Object> inputMap) throws Exception {
        Long billId = Maps.getLong(inputMap, "billId");
        String eventCode = Maps.getString(inputMap, "eventCode");
        String eventNameChinese = Maps.getString(inputMap, "eventNameChinese");
        Long pigId = Maps.getLongClass(inputMap, "pigId");
        Long inPigId = Maps.getLongClass(inputMap, "inPigId");
        List<String> pigIds = this.getStrList(inputMap, "pigIds");
        Long lineNumber = Maps.getLongClass(inputMap, "lineNumber");
        // 精液销售和入库用SEMEN_ID
        Long semenId = Maps.getLongClass(inputMap, "semenId");

        // 撤销事件无法撤销
        if (EventConstants.BILL_CANCEL.equals(eventCode) || EventConstants.PIG_EVENT_CANCEL.equals(eventCode) || EventConstants.EACH_RECORD_CANCEL
                .equals(eventCode)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "撤销事件无法撤销！");
        }

        Map<String, Object> commonInfos = new HashMap<String, Object>();
        commonInfos.put("eventCode", eventCode);
        commonInfos.put("billId", billId);
        commonInfos.put("eventNameChinese", eventNameChinese);
        commonInfos.put("pigId", pigId);
        commonInfos.put("inPigId", inPigId);
        commonInfos.put("pigIds", pigIds);
        commonInfos.put("lineNumber", lineNumber);
        commonInfos.put("semenId", semenId);

        // 撤销事件名称
        String eventName = null;
        // 是否撤销pp_m_bill
        boolean cancelBillFlag = true;
        if (EventConstants.SALE_SEMEN.equals(eventCode) || EventConstants.SEMEN_INQUIRY.equals(eventCode)) {
            if (semenId == null) {
                // 单据撤销
                eventName = EventConstants.BILL_CANCEL;

            } else {
                // 单条精液撤销
                eventName = EventConstants.EACH_RECORD_CANCEL;

            }

        } else {
            if (pigId != null) {
                // 猪只销售
                if (EventConstants.GOOD_PIG_SELL.equals(eventCode)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【猪只销售】事件无法通过【猪只】撤销，请通过【事件查看】撤销单据！");
                }
    
                // 猪只事件撤销
                eventName = EventConstants.PIG_EVENT_CANCEL;
                cancelBillFlag = this.lastPigInBillCheck(commonInfos);
                PigModel pigModel = pigMapper.searchById(pigId);
                if (PigConstants.PIG_TYPE_PORK.equals(pigModel.getPigType())) {
                    // 公共事件允许选中肉猪
                    if (!"CHANGE_EAR_BAND".equals(eventCode) && !"PIG_MOVE_IN".equals(eventCode)) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "无法以肉猪个体为单位去撤销事件！");
                    }
                }
    
            } else if (pigIds != null && !pigIds.isEmpty()) {
                // 以猪群为单位的单条记录撤销
                eventName = EventConstants.EACH_RECORD_CANCEL;
                cancelBillFlag = this.lastPigInBillCheck(commonInfos);
    
            } else {
                // 单据撤销
                eventName = EventConstants.BILL_CANCEL;
    
            }
        }

        // CHECK这个事件是否是猪只的最后一个事件
        this.lastEventCheck(commonInfos);

        Date currentDate = new Date();

        long cancelEventBillId = this.getCancelEventBillId(eventName, billId, currentDate);

        commonInfos.put("currentDate", currentDate);
        commonInfos.put("cancelEventBillId", cancelEventBillId);
        commonInfos.put("eventName", eventName);
        commonInfos.put("cancelBillFlag", cancelBillFlag);

        // 入场
        if (EventConstants.PIG_MOVE_IN.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PIG_MOVE_IN");
            this.cancelPigMoveIn(commonInfos);

            // 仔猪死亡 || 商品猪死亡
        } else if (EventConstants.CHILD_PIG_DIE.equals(eventCode) || EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_CHILD_DIE");
            this.cancelChildDie(commonInfos);

            // 种猪死亡
        } else if (EventConstants.BREED_PIG_DIE.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_LEAVE");
            this.cancelLeave(commonInfos);

            // 转保育，转育肥
        } else if (EventConstants.TO_CHILD_CARE.equals(eventCode) || EventConstants.TO_CHILD_FATTEN.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_TO_CHILD");
            this.cancelToChild(commonInfos);

            // 换耳号
        } else if (EventConstants.CHANGE_EAR_BAND.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_CHANGE_EARBRAND");
            this.cancelChangeEarBrand(commonInfos);

            // 后备转生产公猪 || 后备情期鉴定
        } else if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventCode) || EventConstants.PREPUBERTAL_CHECK.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_RESERVE_PRODUCT");
            this.cancelReserveProduct(commonInfos);

            // 配种
        } else if (EventConstants.BREED.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_BREED");
            this.cancelBreed(commonInfos);

            // 妊娠检查 || 流产
        } else if (EventConstants.PREGNANCY_CHECK.equals(eventCode) || EventConstants.MISSCARRY.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_PREGNANCY_CHECK");
            this.cancelPregnancyCheck(commonInfos);

            // 转产房 || 种猪转舍
        } else if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode) || EventConstants.BREED_PIG_CHANGE_HOUSE.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_CHANGE_HOUSE");
            this.cancelChangeHouse(commonInfos);

            // 分娩
        } else if (EventConstants.DELIVERY.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_DELIVERY");
            this.cancelDelivery(commonInfos);

            // 断奶
        } else if (EventConstants.WEAN.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_WEAN");
            this.cancelWean(commonInfos);

            // 寄养
        } else if (EventConstants.FOSTER.equals(eventCode)) {
            commonInfos.put("eventTable", "PP_L_BILL_FOSTER_CARE");
            this.cancelFosterCare(commonInfos);

            // 背膘测定
        } else if (EventConstants.BACKFAT.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_BACKFAT");
            this.cancelBackfat(commonInfos);

            // 采精
        } else if (EventConstants.SEMEN.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_SEMEN");
            this.cancelSemen(commonInfos);

            // 种猪淘汰申请
        } else if (EventConstants.BREED_PIG_OBSOLETE.equals(eventCode)) {
            // 撤销所用主表
            commonInfos.put("eventTable", "PP_L_BILL_BOAR_OBSOLETE");
            this.cancelObsolete(commonInfos);

            // 猪只销售
        } else if (EventConstants.GOOD_PIG_SELL.equals(eventCode)) {

            commonInfos.put("eventTable", "PP_L_BILL_LEAVE");
            this.cancelGoodPigSell(commonInfos);
            // 留种
        } else if (EventConstants.SEED_PIG.equals(eventCode)) {
            commonInfos.put("eventTable", "PP_L_BILL_SEED_DETAIL");
            this.cancelSeedPigSell(commonInfos);
            // 奶妈转舍
        } else if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            commonInfos.put("eventTable", "PP_L_BILL_NURSE");
            this.cancelNurseChangeHouse(commonInfos);
            // 选种
        } else if (EventConstants.SELECT_BREED_PIG.equals(eventCode)) {
            commonInfos.put("eventTable", "PP_L_BILL_SELECT_BREED");
            this.cancelSelectBreed(commonInfos);
        } else if (EventConstants.PORK_PIG_CHECK.equals(eventCode)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "肉猪盘点不能撤销事件！");
        } else if (EventConstants.SEMEN_INQUIRY.equals(eventCode)) {
            commonInfos.put("eventTable", "PP_L_BILL_SEMEN");
            this.cancelSemenInquiry(commonInfos);
            // 精液销售
        } else if (EventConstants.SALE_SEMEN.equals(eventCode)) {
            commonInfos.put("eventTable", "PP_L_BILL_SEMEN_SALE");
            this.cancelSaleSemen(commonInfos);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该单据撤销功能正在开发！");
        }
    }

    // 留种
    private void cancelSeedPigSell(Map<String, Object> commonInfos) throws Exception {
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {
            long[] seedIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                seedIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModel.setSex(Maps.getString(infoMap, "sex"));
                pigModel.setLastBillId(Maps.getLong(infoMap, "lastBillId"));
                pigModel.setLastEventDate(Maps.getDate(infoMap, "lastEventDate"));
                pigModel.setSeedFlag("N");
                pigModelList.add(pigModel);

                SqlCon sql = new SqlCon();
                sql.addMainSql(" UPDATE PP_L_EAR_CODE SET EAR_SHORT = NULL");
                sql.addCondition(Maps.getString(infoMap, "earBrand"), " WHERE EAR_BRAND = ?");
                sql.addCondition(getFarmId(), " AND FARM_ID = ? ");
                setSql(earCodeMapper, sql);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);
            }
            this.commonExecute(seedIds, commonInfos, pigModelList, null, null, pigEventHisModelList, null, null, null, null, null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 选种
    private void cancelSelectBreed(Map<String, Object> commonInfos) throws Exception {
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {
            long[] seedBreedIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            List<EarCodeModel> earCodeModels = new ArrayList<EarCodeModel>();

            List<ToworkModel> toworkModels = new ArrayList<ToworkModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();

            List<PigModel> swineryIdsList = new ArrayList<PigModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            // 还原上一条CHANGE_SWINERY的CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT
            List<ChangeSwineryModel> updateChangeSwineryModelList = new ArrayList<ChangeSwineryModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                seedBreedIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, pigpenIdsList, swineryIdsList);
                pigModel.setSex(Maps.getString(infoMap, "sex"));
                pigModel.setPigType(Maps.getString(infoMap, "pigType"));
                pigModel.setMaterialId(Maps.getLong(infoMap, "lastMaterialId"));
                pigModelList.add(pigModel);

                EarCodeModel earCodeModel = new EarCodeModel();
                earCodeModel.setRowId(Maps.getLong(infoMap, "earCodeId"));
                earCodeModel.setEarBrand(Maps.getString(infoMap, "earBrand"));
                earCodeModels.add(earCodeModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                toworkModels.add(toworkModel);
                /************************************************** 2017-7-3 撤销修改 start *****************************************************/
                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                ChangeSwineryModel changeSwineryModel = new ChangeSwineryModel();
                changeSwineryModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeSwineryModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeSwineryModelList.add(changeSwineryModel);
                /* changeHouseModel.setBillId(Maps.getLongClass(infoMap, "lastBillId"));
                 * SqlCon deleteChangeHouseSqlCon = new SqlCon();
                 * deleteChangeHouseSqlCon.addMainSql("UPDATE PP_L_BILL_CHANGE_HOUSE SET DELETED_FLAG = '2' WHERE DELETED_FLAG <> '2'");
                 * deleteChangeHouseSqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
                 * deleteChangeHouseSqlCon.addCondition(Maps.getLong(commonInfos, "billId"), " AND BILL_ID=? ");
                 * setSql(changeHouseMapper, deleteChangeHouseSqlCon); */
                /************************************************** 2017-7-3 撤销修改 start *****************************************************/
                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);
            }
            this.commonExecute(seedBreedIds, commonInfos, pigModelList, earCodeModels, null, pigEventHisModelList, pigpenIdsList, swineryIdsList,
                    toworkModels, updateChangeHouseModelList, updateChangeSwineryModelList, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }
    }

    // 精液销售
    private void cancelSaleSemen(Map<String, Object> commonInfos) throws Exception {
        long billId = Maps.getLong(commonInfos, "billId");
        Long semenId = Maps.getLongClass(commonInfos, "semenId");
        String eventName = Maps.getString(commonInfos, "eventName");
        Long cancelEventBillId = Maps.getLong(commonInfos, "cancelEventBillId");
        boolean cancelBillFlag = false;
        if (EventConstants.BILL_CANCEL.equals(eventName)) {
            cancelBillFlag = true;
        } else if (EventConstants.EACH_RECORD_CANCEL.equals(eventName)) {
            // 除了本条撤销信息，还存在没有撤销的单条记录
            SqlCon existsNotCancelRecord = new SqlCon();
            existsNotCancelRecord.addMainSql("SELECT ROW_ID FROM PP_L_BILL_SEMEN_SALE WHERE DELETED_FLAG = '0'");
            existsNotCancelRecord.addCondition(getFarmId(), " AND FARM_ID = ?");
            existsNotCancelRecord.addCondition(billId, " AND BILL_ID = ?");
            existsNotCancelRecord.addCondition(semenId, " AND SEMEN_ID <> ?");
            existsNotCancelRecord.addMainSql(" LIMIT 1");

            List<Map<String, Object>> existsNotCancelRecordList = paramMapperSetSQL(paramMapper, existsNotCancelRecord);
            // 加上本次撤销，已全部撤销
            if (existsNotCancelRecordList.isEmpty()) {
                cancelBillFlag = true;
            }
        }
        commonInfos.put("cancelBillFlag", cancelBillFlag);

        SqlCon saleNumSqlCon = new SqlCon();
        saleNumSqlCon.addCondition(getFarmId(), " AND FARM_ID= ? ");
        saleNumSqlCon.addCondition(billId, " AND BILL_ID = ? ");
        saleNumSqlCon.addCondition(semenId, " AND SEMEN_ID = ? ");
        List<SemenSaleModel> semenSaleModels = getList(semenSaleMapper, saleNumSqlCon);

        List<PigEventHisModel> pigEventHisModels = new ArrayList<PigEventHisModel>();
        List<SpermInfoModel> updateSpermInfoModelList = new ArrayList<SpermInfoModel>();
        List<SemenSaleDetailModel> cancelSemenSaleDetailModelList = new ArrayList<SemenSaleDetailModel>();
        if (semenSaleModels != null && semenSaleModels.size() > 0) {
            for (SemenSaleModel semenSaleModel : semenSaleModels) {
                // 撤销的单据号
                semenSaleModel.setDeletedBillId(cancelEventBillId);

                SemenModel semenModel = semenMapper.searchById(semenSaleModel.getSemenId());
                if (semenSaleModel.getIsEntry().equals(SpermConstants.SPERM_INFO_IS_ENTRY_ENTRY)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精液批号：【" + semenModel.getSemenBatchNo() + "】,精液已入库，不能再撤销！");
                } else {
                    // 撤销明细和销售数量一致
                    SqlCon semenSaleDetailSqlCon = new SqlCon();
                    semenSaleDetailSqlCon.addCondition(semenSaleModel.getRowId(), " AND SEMEN_SALE_ROW_ID = ?");
                    List<SemenSaleDetailModel> semenSaleDetailModelList = getList(semenSaleDetailMapper, semenSaleDetailSqlCon);
                    if (semenSaleDetailModelList.size() == semenSaleModel.getSaleNum().intValue()) {
                        for (SemenSaleDetailModel semenSaleDetailModel : semenSaleDetailModelList) {
                            cancelSemenSaleDetailModelList.add(semenSaleDetailModel);
                            // 修改精液信息为未使用
                            SpermInfoModel spermInfoModel = spermInfoMapper.searchById(semenSaleDetailModel.getSpermId());
                            if (SpermConstants.SPERM_INFO_STATUS_SALE.equals(spermInfoModel.getStatus()) && SpermConstants.SPERM_INFO_IS_SALE_SALE
                                    .equals(spermInfoModel.getIsSale())) {
                                spermInfoModel.setStatus(SpermConstants.SPERM_INFO_STATUS_UNUSE);
                                spermInfoModel.setIsSale(SpermConstants.SPERM_INFO_IS_SALE_UNSALE);
                                updateSpermInfoModelList.add(spermInfoModel);
                            } else {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统异常，精液批号：【" + semenModel.getSemenBatchNo()
                                        + "】,精液撤销数量和销售数量不一致，请联系管理员。");
                            }
                        }
                    }else{
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统异常，精液批号：【" + semenModel.getSemenBatchNo()
                        + "】,精液撤销明细数量和销售数量不一致，请联系管理员。");
                    }
                    if (semenModel.getPigId() != null) {
                        Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", semenSaleModel.getCustomerId().toString());
                        String eventNotes = "精液批号：【" + semenModel.getSemenBatchNo() + "】销售【" + semenSaleModel.getSaleNum() + "】份至【" + Maps.getString(companyInfoMap, "SORT_NAME") + "】已经被撤销。";
                        PigEventHisModel pigEventHisModel = createPigEventHisModelForSperm(semenModel.getPigId(), commonInfos, "PP_L_BILL_SEMEN_SALE", semenSaleModel.getRowId(), eventNotes);
                        pigEventHisModels.add(pigEventHisModel);
                    }
                }
            }
        }

        if (cancelBillFlag) {
            billMapper.deleteForCancel(billId);
        }

        if(!semenSaleModels.isEmpty()){
            semenSaleMapper.deletesForCancel(semenSaleModels);
        }
        if (!updateSpermInfoModelList.isEmpty()) {
            spermInfoMapper.updates(updateSpermInfoModelList);
        }
        if (!cancelSemenSaleDetailModelList.isEmpty()) {
            semenSaleDetailMapper.deletesForCancel(cancelSemenSaleDetailModelList);
        }
        if(!pigEventHisModels.isEmpty()){
            pigEventHisMapper.inserts(pigEventHisModels);
        }
        
    }

    // 奶妈转舍
    private void cancelNurseChangeHouse(Map<String, Object> commonInfos) throws Exception {

        // 获得寄出母猪的信息
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        // 获得寄入母猪的信息
        List<Map<String, Object>> boardInfos = this.getBoardInfos(commonInfos);

        if (boardInfos != null && !boardInfos.isEmpty()) {
            for (int i = 0; i < boardInfos.size(); i++) {
                Map<String, Object> boardInfosMap = boardInfos.get(i);
                for (Map<String, Object> infosMap : infos) {
                    if (Maps.getLong(boardInfosMap, "pigId") == Maps.getLong(infosMap, "pigId")) {
                        boardInfos.remove(boardInfosMap);
                        i--;
                    }
                }
            }
        }

        if (infos != null && !infos.isEmpty()) {

            Set<Long> nurseIdsSet = new HashSet<Long>();
            List<Long> nurseIdsList = new ArrayList<Long>();

            List<PigModel> updatePigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                if (!nurseIdsSet.contains(Maps.getLong(infoMap, "mainTableId"))) {
                    nurseIdsSet.add(Maps.getLong(infoMap, "mainTableId"));
                    nurseIdsList.add(Maps.getLong(infoMap, "mainTableId"));
                }

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                updatePigModelList.add(pigModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            // 判断猪栏是否还原为NULL
            Long flag = -99L;

            List<Map<String, Object>> childPigMapList = this.createNurseChildPigModel(commonInfos);

            // 判断奶妈，代养母猪，原栏位母猪，以及所有相关仔猪，是否已经做过其他事件
            checkLastEventPigIds(infos, boardInfos, childPigMapList, Maps.getLong(commonInfos, "billId"));

            List<Long> leaveIds = new ArrayList<>();
            for (Map<String, Object> childPigMap : childPigMapList) {

                if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                    if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) == 0) {
                        childPigMap.remove("pigpenId");
                        PigModel pigpenIdsPigModel = new PigModel();
                        pigpenIdsPigModel.setRowId(Maps.getLong(childPigMap, "rowId"));
                        pigpenIdsList.add(pigpenIdsPigModel);
                    }
                }

                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                this.swineryIsCloseCheck(Maps.getLong(childPigMap, "swineryId"));

                PigModel childPigModel = getBean(PigModel.class, childPigMap);
                updatePigModelList.add(childPigModel);

                PigEventHisModel childPigEventHisModel = null;
                if (!Maps.isEmpty(childPigMap, "fosterMainTableId")) {
                    childPigMap.put("mainTableId", Maps.getLong(childPigMap, "fosterMainTableId"));
                    childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_foster_detail");

                    ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                    changeHouseModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                    changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateChangeHouseModelList.add(changeHouseModel);

                } else if (!Maps.isEmpty(childPigMap, "leaveMainTableId")) {
                    leaveIds.add(Maps.getLong(childPigMap, "pigId"));

                    childPigMap.put("mainTableId", Maps.getLong(childPigMap, "leaveMainTableId"));
                    childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_leave");

                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                } else if (!Maps.isEmpty(childPigMap, "weanMainTableId")) {
                    childPigMap.put("mainTableId", Maps.getLong(childPigMap, "weanMainTableId"));
                    childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_wean_detail");

                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                }

                pigEventHisModelList.add(childPigEventHisModel);
            }
            if (!leaveIds.isEmpty()) {
                // 撤销leave_date
                SqlCon leaveSetNullSql = new SqlCon();
                leaveSetNullSql.addMainSql("UPDATE pp_l_pig SET LEAVE_DATE = NULL");
                leaveSetNullSql.addCondition(StringUtil.listLongToString(leaveIds), " WHERE ROW_ID IN ?", false, true);
                setSql(pigMapper, leaveSetNullSql);
            }

            // 添加寄入母猪到List中
            if (!Maps.isEmpty(commonInfos, "pigId")) {
                // 猪只撤销
                if (boardInfos != null && !boardInfos.isEmpty()) {
                    // 特殊点：同一单据中，一个母猪被多次寄养
                    // 猪只撤销只有一条数据
                    Map<String, Object> boardInfoMaps = boardInfos.get(0);
                    PigModel boardPigModel = this.createPigModel(boardInfoMaps, null, null);
                    pigMapper.update(boardPigModel);
                    Map<String, Object> deletesByConMap = new HashMap<String, Object>();
                    deletesByConMap.put("condition", " AND ROW_ID = " + Maps.getLong(boardInfoMaps, "relatesId"));
                    deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));
                    pigEventRelatesMapper.deletesByCon2(deletesByConMap);
                }
            } else {
                // 单据撤销
                if (boardInfos != null && !boardInfos.isEmpty()) {
                    for (int i = 0; i < boardInfos.size(); i++) {
                        Map<String, Object> boardInfoMaps = boardInfos.get(i);
                        PigModel boardPigModel = this.createPigModel(boardInfoMaps, null, null);
                        updatePigModelList.add(boardPigModel);
                    }
                }
            }

            long[] nurseIds = longArrayFromList(nurseIdsList);

            this.commonExecute(nurseIds, commonInfos, updatePigModelList, null, null, pigEventHisModelList, pigpenIdsList, null,
                    updateToworkModelList, updateChangeHouseModelList, null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    private void checkLastEventPigIds(List<Map<String, Object>> infos, List<Map<String, Object>> boardInfos,
            List<Map<String, Object>> childPigMapList, Long billId) {
        List<Long> pigIds = new ArrayList<>();
        for (Map<String, Object> map : infos) {
            pigIds.add(Maps.getLong(map, "pigId"));
        }
        for (Map<String, Object> map : boardInfos) {
            pigIds.add(Maps.getLong(map, "pigId"));
        }
        for (Map<String, Object> map : childPigMapList) {
            pigIds.add(Maps.getLong(map, "pigId"));
        }

        SqlCon lastEventCheckSqlCon = new SqlCon();
        lastEventCheckSqlCon.addMainSql(
                "SELECT (SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE DELETED_FLAG = '0' AND ROW_ID = T2.EAR_CODE_ID) AS earBrand ");
        lastEventCheckSqlCon.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1 ");
        lastEventCheckSqlCon.addMainSql(" INNER JOIN PP_L_PIG T2 ");
        lastEventCheckSqlCon.addMainSql(" ON( T2.ROW_ID = T1.PIG_ID ");
        lastEventCheckSqlCon.addMainSql(" AND T2.FARM_ID = T1.FARM_ID");
        lastEventCheckSqlCon.addMainSql(" AND T2.LAST_BILL_ID <> T1.BILL_ID ");
        lastEventCheckSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        lastEventCheckSqlCon.addMainSql(" AND T1.DELETED_FLAG = '0'");
        lastEventCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        lastEventCheckSqlCon.addCondition(billId, " AND T1.BILL_ID = ?");

        if (pigIds != null && !pigIds.isEmpty()) {
            lastEventCheckSqlCon.addMainSql(" AND T1.PIG_ID IN (");
            for (int i = 0; i < pigIds.size(); i++) {
                lastEventCheckSqlCon.addMainSql(pigIds.get(i).toString());
                if (i != pigIds.size() - 1) {
                    lastEventCheckSqlCon.addMainSql(",");
                }
            }
            lastEventCheckSqlCon.addMainSql(")");
        }
        lastEventCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> lastEventCheckSqlMap = new HashMap<String, String>();
        lastEventCheckSqlMap.put("sql", lastEventCheckSqlCon.getCondition());

        List<Map<String, Object>> lastEventCheckInfos = paramMapper.getObjectInfos(lastEventCheckSqlMap);

        if (lastEventCheckInfos != null && !lastEventCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOT_LAST_EVENT_ERROR, Maps.getString(lastEventCheckInfos.get(0), "earBrand"));
        }

    }

    // 入场
    private void cancelPigMoveIn(Map<String, Object> commonInfos) throws Exception {
        Long billId = Maps.getLongClass(commonInfos, "billId");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        boolean cancelBillFlag = Maps.getBoolean(commonInfos, "cancelBillFlag");

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT T1.ROW_ID AS pigId, T1.EAR_CODE_ID AS earCodeId, T1.COMPANY_ID AS companyId, T1.FARM_ID AS farmId, T1.LINE_ID AS lineId");
        sqlCon.addMainSql(
                ", T1.SWINERY_ID AS swineryId, T1.HOUSE_ID AS houseId, T1.PIGPEN_ID AS pigpenId, T1.PIG_TYPE AS pigType, T1.SEX AS sex,T1.LAST_EVENT_DATE eventHisLastEventDate");
        sqlCon.addMainSql(
                ", T1.PIG_CLASS AS pigClass, T1.PARITY AS parity, T1.LAST_EVENT_DATE AS lastEventDate, T2.EAR_BRAND AS earBrand, T2.FATHER_EAR_ID AS fatherEarId, T2.MOTHER_EAR_ID AS motherEarId");
        sqlCon.addMainSql(" FROM PP_L_PIG T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T2");
        sqlCon.addMainSql(" ON(T1.EAR_CODE_ID = T2.ROW_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND T1.LAST_BILL_ID = ?");
        sqlCon.addCondition(pigId, " AND T1.ROW_ID = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);

        // 采购单是否有生产猪，商品猪
        SqlCon classSql = new SqlCon();
        classSql.addMainSql("SELECT p.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand,p.PIG_CLASS pigClass");
        classSql.addMainSql(" FROM pp_l_pig p");
        classSql.addCondition(billId, " WHERE LAST_BILL_ID = ?");
        classSql.addCondition(getFarmId(), " AND FARM_ID = ?");
        Map<String, String> classMap = new HashMap<>();
        classMap.put("sql", classSql.getCondition());
        List<Map<String, String>> pigClassList = paramMapper.getInfos(classMap);

        List<PigEventHisModel> pigEventHisList = new ArrayList<>();

        if (infos != null && !infos.isEmpty()) {

            long[] pigIds = new long[infos.size()];

            List<Long> earBrandIds = new ArrayList<Long>();

            List<Long> fatherOrMotherEarBrandIds = new ArrayList<Long>();

            Map<Long, Long> earBrandMap = new HashMap<>();

            Map<Long, Long> fatherOrMotherEarBrandMap = new HashMap<>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                pigIds[i] = Maps.getLong(infoMap, "pigId");
                earBrandIds.add(Maps.getLong(infoMap, "earCodeId"));
                earBrandMap.put(pigIds[i], Maps.getLong(infoMap, "earCodeId"));

                if (!Maps.isEmpty(infoMap, "fatherEarId")) {
                    // 得到这个猪只的父亲或母亲耳号，如果只是这只猪入场时创建的，一起删除
                    boolean fatherEarBrandDeleteFlag = getFatherOrMotherEarBrandIds(earBrandIds, Maps.getLong(infoMap, "fatherEarId"), "father");
                    if (fatherEarBrandDeleteFlag) {
                        fatherOrMotherEarBrandIds.add(Maps.getLong(infoMap, "fatherEarId"));
                    }
                }

                if (!Maps.isEmpty(infoMap, "motherEarId")) {
                    boolean motherEarBrandDeleteFlag = getFatherOrMotherEarBrandIds(earBrandIds, Maps.getLong(infoMap, "motherEarId"), "mother");
                    if (motherEarBrandDeleteFlag) {
                        fatherOrMotherEarBrandIds.add(Maps.getLong(infoMap, "motherEarId"));
                    }
                }
                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisList.add(pigEventHisModel);
            }

            SqlCon deletesByConSqlCon = new SqlCon();
            deletesByConSqlCon.addCondition(billId, " AND BILL_ID = ?");
            deletesByConSqlCon.addCondition(pigId, " AND PIG_ID = ?");

            Map<String, Object> deletesByConMap = new HashMap<String, Object>();
            deletesByConMap.put("condition", deletesByConSqlCon.getCondition());
            deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));

            if (cancelBillFlag) {
                billMapper.deleteForCancel(billId);
            }
            // 新增：入场只能做单据撤销
            if (pigId != null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪只入场只能做单据撤销！");
            }
            // 销售单据入场撤销，需把原厂 离场表is_move_in重新置为N
            SqlCon leaveSql = new SqlCon();
            leaveSql.addMainSql("SELECT l.ROW_ID rowId FROM pp_l_pig p");
            leaveSql.addMainSql(" INNER JOIN pp_l_bill_leave l");
            leaveSql.addMainSql(" ON p.RELATION_PIG_ID = l.PIG_ID AND l.DELETED_FLAG = 0");
            leaveSql.addMainSql(" WHERE p.DELETED_FLAG = 0");
            leaveSql.addCondition(billId, " AND p.LAST_BILL_ID = ?");
            leaveSql.addCondition(pigId, " AND P.ROW_ID = ?");
            List<LeaveModel> leaveList = setSql(leaveMapper, leaveSql);

            for (LeaveModel leaveModel : leaveList) {
                leaveModel.setIsMoveIn("N");
            }
            if (!leaveList.isEmpty()) {
                leaveMapper.updates(leaveList);
            }
            // 妊娠母猪，还需撤销配种表
            SqlCon breedSql = new SqlCon();
            breedSql.addMainSql("UPDATE pp_l_bill_breed SET DELETED_FLAG = 2");
            breedSql.addConditionWithNull(getFarmId(), " WHERE FARM_ID = ?");
            breedSql.addCondition(billId, " AND BILL_ID = ?");
            setSql(billMapper, breedSql);

            List<PigEventRelatesModel> relateList = new ArrayList<>();

            // 再次入场撤销
            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            for (long id : pigIds) {

                SqlCon relateSql = new SqlCon();
                relateSql.addMainSql("SELECT r1.* FROM pp_l_pig_event_relates r1");
                relateSql.addMainSql(" INNER JOIN pp_l_pig_event_relates r2");
                relateSql.addMainSql(" ON r1.BILL_ID = r2.LAST_BILL_ID AND r1.PIG_ID =r2.PIG_ID AND r2.DELETED_FLAG = 0");
                relateSql.addMainSql(" WHERE r1.deleted_flag = 0");
                relateSql.addCondition(id, " AND r1.pig_id = ?");
                relateSql.addMainSql(" ORDER BY r1.ROW_ID DESC LIMIT 1");

                relateList = setSql(pigEventRelatesMapper, relateSql);

                // 非虚拟公猪入场
                if (relateList.isEmpty()) {
                    SqlCon cancelSql = new SqlCon();
                    cancelSql.addMainSql("UPDATE pp_l_pig SET DELETED_FLAG = 2");
                    cancelSql.addMainSql(" WHERE DELETED_FLAG = 0");
                    cancelSql.addCondition(id, " AND ROW_ID = ?");
                    setSql(pigMapper, cancelSql);

                    // 新增：如果是再次入场的猪只，耳号不能删除，因为原来猪只仍然占用
                    earBrandMap.remove(id);
                } else {
                    // 虚拟公猪入场
                    PigEventRelatesModel relateModel = relateList.get(0);
                    PigModel pigModel = new PigModel();
                    pigModel.setRowId(id);
                    pigModel.setLastBillId(relateModel.getBillId());
                    pigModel.setLastEventDate(relateModel.getEventDate());
                    pigModel.setOrigin("3");
                    pigModel.setHouseId(relateModel.getHouseId());
                    pigModel.setPigpenId(relateModel.getPigpenId());
                    pigModel.setPigClass(relateModel.getPigClass());
                    pigModel.setParity(relateModel.getParity());
                    pigModel.setLastClassDate(getLastClassDate(getFarmId(), id, billId));
                    pigModel.setSapFixedAssetsEarbrand("null");
                    pigMapper.update(pigModel);
                    earBrandMap.remove(id);

                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(id);
                    toworkModel.setBillId(billId);
                    updateToworkModelList.add(toworkModel);

                    ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                    changeHouseModel.setPigId(id);
                    changeHouseModel.setBillId(billId);
                    updateChangeHouseModelList.add(changeHouseModel);

                }
            }
            List<Long> earList = new ArrayList<>();
            for (Map.Entry<Long, Long> entry : earBrandMap.entrySet()) {
                earList.add(entry.getValue());
            }
            earList.addAll(fatherOrMotherEarBrandIds);
            setDeletes(earCodeMapper, earList, "ROW_ID");
            changeHouseMapper.deletesByCon2(deletesByConMap);
            changeSwineryMapper.deletesByCon2(deletesByConMap);
            toworkMapper.deletesByCon2(deletesByConMap);
            parityMapper.deletesByCon2(deletesByConMap);
            changeEarbrandMapper.deletesByCon2(deletesByConMap);

            pigEventRelatesMapper.deletesByCon2(deletesByConMap);

            // 上次记录两个out值为空
            if (!relateList.isEmpty()) {
                toworkMapper.updatesToworkDateOutAndPigClassOutToNULL(updateToworkModelList);
                changeHouseMapper.updatesOutFieldToNULL(updateChangeHouseModelList);
                pigEventHisMapper.inserts(pigEventHisList);
            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }
        /************************************ 2016-2-13 yangy 批次日龄撤销 *************************************************/
        if (infos != null && !infos.isEmpty() && PigConstants.PIG_TYPE_PORK.equals(infos.get(0).get("pigType"))) {
            if (pigId == null) {
                SqlCon sqlConCancelInfo = new SqlCon();
                sqlConCancelInfo.addMainSql(" SELECT t0.* FROM pp_l_bill_swinery_dayage_change t0 INNER JOIN ");
                sqlConCancelInfo.addMainSql(" (SELECT ROW_ID,SWINERY_ID FROM pp_l_bill_swinery_dayage_change WHERE ");
                sqlConCancelInfo.addCondition(Maps.getLong(commonInfos, "billId"), " BILL_ID=? ");
                sqlConCancelInfo.addMainSql(" ORDER BY ROW_ID DESC) t1 ON t0.ROW_ID=t1.ROW_ID ");
                sqlConCancelInfo.addMainSql(" WHERE t0.DELETED_FLAG=0 AND t0.STATUS=1 ");
                sqlConCancelInfo.addCondition(Maps.getLong(commonInfos, "billId"), " AND t0.BILL_ID=? ");
                sqlConCancelInfo.addMainSql(" GROUP BY t1.SWINERY_ID ORDER BY t0.ROW_ID ");
                List<SwineryDayageChangeModel> swineryDayageChangeModelList = setSql(swineryDayageChangeMapper, sqlConCancelInfo);
                if (swineryDayageChangeModelList != null && !swineryDayageChangeModelList.isEmpty()) {
                    for (SwineryDayageChangeModel swineryDayageChangeModel : swineryDayageChangeModelList) {
                        // 撤销转入批次日龄记录,转入批次日龄记录
                        SqlCon sqlConDeletesSwinery = new SqlCon();
                        Map<String, Object> DeletesSwineryMap = new HashMap<String, Object>();
                        sqlConDeletesSwinery.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND BILL_ID=? ");
                        sqlConDeletesSwinery.addCondition(swineryDayageChangeModel.getSwineryId(), " AND SWINERY_ID=? ");
                        DeletesSwineryMap.put("condition", sqlConDeletesSwinery.getCondition());
                        swineryDayageChangeMapper.deletesByCon2(DeletesSwineryMap);
                    }
                }
            } else {
                SqlCon sqlConCancelInfo = new SqlCon();
                sqlConCancelInfo.addMainSql(" SELECT t0.* FROM pp_l_bill_swinery_dayage_change t0 INNER JOIN ");
                sqlConCancelInfo.addMainSql(" (SELECT ROW_ID,SWINERY_ID FROM pp_l_bill_swinery_dayage_change WHERE ");
                sqlConCancelInfo.addCondition(Maps.getLong(commonInfos, "billId"), " BILL_ID=? ");
                sqlConCancelInfo.addMainSql(" ORDER BY ROW_ID DESC) t1 ON t0.ROW_ID=t1.ROW_ID ");
                sqlConCancelInfo.addMainSql(" WHERE t0.DELETED_FLAG=0 AND t0.STATUS=1 ");
                sqlConCancelInfo.addCondition(Maps.getLong(commonInfos, "billId"), " AND t0.BILL_ID=? ");
                sqlConCancelInfo.addCondition(Maps.getLong(infos.get(0), "swineryId"), " AND t0.SWINERY_ID=? ");
                sqlConCancelInfo.addMainSql(" GROUP BY t1.SWINERY_ID ORDER BY t0.ROW_ID ");
                SwineryDayageChangeModel swineryDayageChangeModel = setSql(swineryDayageChangeMapper, sqlConCancelInfo).get(0);

                SqlCon sqlConUpdate = new SqlCon();
                sqlConUpdate.addMainSql(" UPDATE pp_l_bill_swinery_dayage_change SET CHANGE_PIG_NUM = CHANGE_PIG_NUM -1 ");
                sqlConUpdate.addCondition(swineryDayageChangeModel.getRowId(), " WHERE ROW_ID=? ");
                setSql(swineryDayageChangeMapper, sqlConUpdate);
            }
        }

        /************************************ 2016-2-13 yangy 批次日龄撤销 *************************************************/

        // START_HANA
        pigMoveInHanaCancel(billId, pigClassList);
        // END_HANA
    }

    private Date getLastClassDate(Long farmId, Long pigId, Long billId) {
        SqlCon lastClassSql = new SqlCon();
        lastClassSql.addMainSql("SELECT B.TOWORK_DATE lastClassDate FROM PP_L_BILL_TOWORK A ");
        lastClassSql.addMainSql(" INNER JOIN PP_L_BILL_TOWORK B");
        lastClassSql.addMainSql(" ON(A.FARM_ID = B.FARM_ID");
        lastClassSql.addMainSql(" AND A.CHANGE_PIG_CLASS_ID = B.ROW_ID");
        lastClassSql.addMainSql(" AND B.DELETED_FLAG = '0')");
        lastClassSql.addMainSql(" WHERE A.DELETED_FLAG = '0'");
        lastClassSql.addCondition(farmId, " AND A.FARM_ID = ?");
        lastClassSql.addCondition(pigId, " AND A.PIG_ID = ?");
        lastClassSql.addCondition(billId, " AND A.BILL_ID = ?");
        lastClassSql.addMainSql(" ORDER BY B.TOWORK_DATE ASC LIMIT 1");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", lastClassSql.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        if (list.isEmpty()) {
            return null;
        }
        return Maps.getDate(list.get(0), "lastClassDate");
    }

    private void pigMoveInHanaCancel(Long billId, List<Map<String, String>> pigClassList) throws Exception {
        Date currentDate = new Date();
        SqlCon billSql = new SqlCon();
        billSql.addMainSql("SELECT s.SAP_SALE_TYPE sapSaleType,b.ROW_ID billId,b.BUSINESS_CODE billCode,b.FARM_ID farmId,bs.ROW_ID saleBillId,");
        billSql.addMainSql(" bs.BUSINESS_CODE saleBillCode,bs.FARM_ID saleFarmId");
        billSql.addMainSql(" FROM pp_m_bill b");
        billSql.addMainSql(" LEFT JOIN pp_m_bill bs");
        billSql.addMainSql(" ON b.RELATE_BILL_ID = bs.ROW_ID");
        billSql.addMainSql(" LEFT JOIN pp_l_bill_pig_sale s");
        billSql.addMainSql(" ON bs.ROW_ID = s.BILL_ID AND s.SAP_SALE_TYPE = 1");
        billSql.addCondition(billId, " WHERE b.ROW_ID = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", billSql.getCondition());
        Map<String, String> billMap = paramMapper.getInfos(sqlMap).get(0);

        // 采购单的isToHana
        boolean isCustomerIdToHana = HanaUtil.isToHanaCheck(getFarmId());

        // 客户ID
        Map<String, String> customerMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
        String customerMtcBranchID = Maps.getString(customerMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        List<MTC_ITFC> mtcList = new ArrayList<>();

        // 采购单是否有生产猪，商品猪
        // SqlCon sqlCon = new SqlCon();
        // sqlCon.addMainSql("SELECT p.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand,p.PIG_CLASS pigClass");
        // sqlCon.addMainSql(" FROM pp_l_pig p");
        // sqlCon.addCondition(billId, " WHERE LAST_BILL_ID = ?");
        // sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        // Map<String, String> classMap = new HashMap<>();
        // classMap.put("sql", sqlCon.getCondition());
        // List<Map<String, String>> pigClassList = paramMapper.getInfos(classMap);

        boolean goodFlag = false;
        boolean scFlag = false;
        List<Map<String, String>> productList = new ArrayList<>();
        for (Map<String, String> map : pigClassList) {
            Long pigClass = Maps.getLong(map, "pigClass");
            if (pigClass == PigConstants.PIG_CLASS_SCGZ || pigClass == PigConstants.PIG_CLASS_FQ1 || pigClass == PigConstants.PIG_CLASS_LC
                    || pigClass == PigConstants.PIG_CLASS_KH || pigClass == PigConstants.PIG_CLASS_RS || pigClass == PigConstants.PIG_CLASS_SOW_DN) {
                scFlag = true;
                productList.add(map);
            } else {
                goodFlag = true;
            }
            if (goodFlag && scFlag) {
                break;
            }
        }
        if (Maps.getString(billMap, "saleBillId") == null) {
            // 外购
            if (isCustomerIdToHana) {

                // 商品猪采购
                if (goodFlag) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(customerMtcBranchID);
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                    // 业务代码:猪只销售(实时)
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode"));
                    // 原始单据编号
                    mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode"));
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                    // JSON文件
                    HanaSaleBill hanaSaleBill = new HanaSaleBill();
                    hanaSaleBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    mtcList.add(mtcITFC);
                }
                // 生产猪采购
                if (scFlag) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(customerMtcBranchID);
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                    // 业务代码:生产猪采购(实时)
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3020);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode"));
                    // 原始单据编号
                    mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode"));
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                    // JSON文件
                    HanaPigProduct hanaProductBill = new HanaPigProduct();
                    hanaProductBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaProductBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    mtcList.add(mtcITFC);
                }
                // 生产猪采购卡片
                if (scFlag) {
                    for (Map<String, String> map : productList) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(customerMtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 业务代码:生产猪采购卡片(实时)
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3021);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(Maps.getString(map, "sapFixedAssetsEarbrand"));
                        // 原始单据编号
                        mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode"));
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        MTC_OITM mtcOITM = new MTC_OITM();
                        mtcOITM.setMTC_ItemCode(Maps.getString(map, "sapFixedAssetsEarbrand"));
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(mtcOITM);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        mtcList.add(mtcITFC);
                    }
                }
            }
        } else {
            // 内销
            // 销售单的isToHana
            boolean isSupplierIdToHana = HanaUtil.isToHanaCheck(Maps.getLong(billMap, "saleFarmId"));
            // 供应商ID
            Map<String, String> supplierMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLong(billMap, "saleFarmId"));
            String supplierMtcBranchID = Maps.getString(supplierMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

            String sapSaleType = HanaUtil.getSapSaleType(getFarmId(), Maps.getLong(billMap, "saleFarmId"));
            // 排除调拨
            if (PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
                if (isCustomerIdToHana) {

                    // 商品猪采购
                    if (goodFlag) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(customerMtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 业务代码:商品猪采购(实时)
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode") + "-" + Maps.getString(
                                billMap, "saleBillId"));
                        // 原始单据编号
                        mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode") + "-" + Maps
                                .getString(billMap, "saleBillId"));
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        HanaSaleBill hanaPurchaseBill = new HanaSaleBill();
                        hanaPurchaseBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        mtcList.add(mtcITFC);

                    }
                    // 生产猪采购
                    if (scFlag) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(customerMtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 业务代码:生产猪采购(实时)
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3020);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode") + "-" + Maps.getString(
                                billMap, "saleBillId"));
                        // 原始单据编号
                        mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode") + "-" + Maps
                                .getString(billMap, "saleBillId"));
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        HanaPigProduct hanaProductBill = new HanaPigProduct();
                        hanaProductBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaProductBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        mtcList.add(mtcITFC);
                    }
                    // 生产猪采购卡片
                    if (scFlag) {
                        for (Map<String, String> map : productList) {
                            MTC_ITFC mtcITFC = new MTC_ITFC();
                            // 分公司编号
                            mtcITFC.setMTC_Branch(customerMtcBranchID);
                            // 业务日期
                            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                            // 业务代码:生产猪采购卡片(实时)
                            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3021);
                            // 新农+单据编号
                            mtcITFC.setMTC_DocNum(Maps.getString(map, "sapFixedAssetsEarbrand"));
                            // 原始单据编号
                            mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + Maps.getString(billMap, "billCode") + "-" + Maps
                                    .getString(billMap, "saleBillId"));
                            // 优先级
                            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                            // 处理区分
                            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                            // 创建日期
                            mtcITFC.setMTC_CreateTime(currentDate);
                            // 同步状态
                            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                            // DB
                            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                            // JSON文件
                            MTC_OITM mtcOITM = new MTC_OITM();
                            mtcOITM.setMTC_ItemCode(Maps.getString(map, "sapFixedAssetsEarbrand"));
                            String jsonDataFile = HanaJacksonUtil.objectToJackson(mtcOITM);
                            mtcITFC.setMTC_DataFile(jsonDataFile);
                            mtcList.add(mtcITFC);
                        }

                    }

                }
                if (isSupplierIdToHana) {
                    if (goodFlag) {
                        // 商品猪销售
                        MTC_ITFC mtcSaleITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcSaleITFC.setMTC_Branch(supplierMtcBranchID);
                        // 业务日期
                        mtcSaleITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 业务代码:猪只销售(实时)
                        mtcSaleITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2100);
                        // 新农+单据编号
                        mtcSaleITFC.setMTC_DocNum(supplierMtcBranchID + "-" + Maps.getString(billMap, "saleBillId") + "-" + Maps.getString(billMap,
                                "saleBillCode") + "-" + billId);
                        // 原始单据编号
                        mtcSaleITFC.setMTC_BaseEntry(supplierMtcBranchID + "-" + Maps.getString(billMap, "saleBillId") + "-" + Maps.getString(billMap,
                                "saleBillCode") + "-" + billId);
                        // 优先级
                        mtcSaleITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcSaleITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcSaleITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcSaleITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcSaleITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(Maps.getLong(billMap, "saleFarmId")));
                        // JSON文件
                        HanaSaleBill hanaSaleBill = new HanaSaleBill();
                        hanaSaleBill.setMTC_BaseEntry(mtcSaleITFC.getMTC_BaseEntry());
                        String jsonDataFileSale = HanaJacksonUtil.objectToJackson(hanaSaleBill);
                        mtcSaleITFC.setMTC_DataFile(jsonDataFileSale);
                        mtcList.add(mtcSaleITFC);
                    }
                    // 生产销售（淘汰销售）
                    if (scFlag) {
                        MTC_ITFC mtcSaleITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcSaleITFC.setMTC_Branch(supplierMtcBranchID);
                        // 业务日期
                        mtcSaleITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 业务代码:淘汰销售(实时)
                        mtcSaleITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
                        // 新农+单据编号
                        mtcSaleITFC.setMTC_DocNum(supplierMtcBranchID + "-" + Maps.getString(billMap, "saleBillId") + "-" + Maps.getString(billMap,
                                "saleBillCode") + "-" + billId);
                        // 原始单据编号
                        mtcSaleITFC.setMTC_BaseEntry(supplierMtcBranchID + "-" + Maps.getString(billMap, "saleBillId") + "-" + Maps.getString(billMap,
                                "saleBillCode") + "-" + billId);
                        // 优先级
                        mtcSaleITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcSaleITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcSaleITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcSaleITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcSaleITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(Maps.getLong(billMap, "saleFarmId")));
                        // JSON文件
                        HanaPigProduct hanaPigProductSale = new HanaPigProduct();
                        hanaPigProductSale.setMTC_BaseEntry(mtcSaleITFC.getMTC_BaseEntry());
                        String jsonDataFileSale = HanaJacksonUtil.objectToJackson(hanaPigProductSale);
                        mtcSaleITFC.setMTC_DataFile(jsonDataFileSale);
                        mtcList.add(mtcSaleITFC);

                    }
                }
            }
        }
        if (!mtcList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcList);
        }
    }

    // 撤销仔猪死亡 || 商品猪死亡
    private void cancelChildDie(Map<String, Object> commonInfos) throws Exception {
        String eventCode = Maps.getString(commonInfos, "eventCode");
        List<Map<String, Object>> infos = null;
        if (EventConstants.CHILD_PIG_DIE.equals(eventCode)) {
            infos = this.getMainInfos(commonInfos);
        } else {
            Long billId = Maps.getLong(commonInfos, "billId");
            Long lineNumber = Maps.getLongClass(commonInfos, "lineNumber");
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(billId, " AND BILL_ID = ?");
            sqlCon.addCondition(lineNumber, " AND LINE_NUMBER = ?");
            Map<String, String> map = new HashMap<String, String>();
            map.put("condition", sqlCon.getCondition());
            List<ChildDieModel> childDieModelList = childDieMapper.searchListByCon(map);
            if (childDieModelList != null && !childDieModelList.isEmpty()) {
                List<Map<String, Object>> infos01 = new ArrayList<Map<String, Object>>();
                for (ChildDieModel childDieModel : childDieModelList) {
                    Map<String, Object> infos01Map = childDieModel.getData();
                    infos01Map.put("mainTableId", Maps.getLong(infos01Map, "rowId"));
                    infos01Map.put("eventHisLastEventDate", Maps.getDate(infos01Map, "leaveDate"));
                    infos01.add(infos01Map);
                }
                infos = infos01;
            }
        }

        if (infos != null && !infos.isEmpty()) {
            long[] childDieIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();

            // 死去的猪只，还原LEAVE_DATE为NULL
            List<PigModel> diePigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                childDieIds[i] = Maps.getLong(infoMap, "mainTableId");

                // 排除断奶仔猪死亡的也会存母猪id的情况
                if (!Maps.isEmpty(infoMap, "pigId") && EventConstants.CHILD_PIG_DIE.equals(eventCode)) {
                    PigModel pigModel = this.createPigModel(infoMap, null, null);
                    pigModelList.add(pigModel);
                }

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);
            }

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 判断猪栏是否还原为NULL
            Long flag = -99L;

            List<Map<String, Object>> childPigMapList = this.createChildPigModel(commonInfos);
            for (Map<String, Object> childPigMap : childPigMapList) {

                if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                    if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) == 0) {
                        childPigMap.remove("pigpenId");
                        PigModel pigpenIdsPigModel = new PigModel();
                        pigpenIdsPigModel.setRowId(Maps.getLong(childPigMap, "rowId"));
                        pigpenIdsList.add(pigpenIdsPigModel);
                    }
                }

                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                this.swineryIsCloseCheck(Maps.getLong(childPigMap, "swineryId"));

                PigModel childPigModel = getBean(PigModel.class, childPigMap);
                pigModelList.add(childPigModel);
                diePigModelList.add(childPigModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                PigEventHisModel childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_leave");
                pigEventHisModelList.add(childPigEventHisModel);
            }

            this.commonExecute(childDieIds, commonInfos, pigModelList, null, null, pigEventHisModelList, pigpenIdsList, null, updateToworkModelList,
                    null, null, diePigModelList, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销种猪死亡
    private void cancelLeave(Map<String, Object> commonInfos) throws Exception {

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] leaveIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();
            // 死去的猪只，还原LEAVE_DATE为NULL
            List<PigModel> diePigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            Date currentDate = Maps.getDate(commonInfos, "currentDate");
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            String mtcBranchID = null;
            String mtcDeptID = null;
            BillModel billModel = null;
            if (isToHana) {
                // 所撤销的单据
                billModel = billMapper.searchById(Maps.getLong(commonInfos, "billId"));
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            }
            // END HANA

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                leaveIds[i] = Maps.getLong(infoMap, "mainTableId");

                // START HANA
                if (isToHana) {
                    // 死亡前状态是否为生产猪
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql("SELECT PIG_CLASS AS pigClass FROM PP_L_BILL_TOWORK WHERE DELETED_FLAG = '0'");
                    sqlCon.addCondition(Maps.getLong(infoMap, "pigId"), " AND PIG_ID = ?");
                    sqlCon.addCondition(PigConstants.PIG_CLASS_SW, " AND PIG_CLASS_OUT = ?");
                    sqlCon.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1");

                    ToworkModel toworkModel = setSql(toworkMapper, sqlCon).get(0);

                    if (HanaUtil.isProductPig(toworkModel.getPigClass())) {
                        // 生产猪死亡
                        PigModel sapPigModel = pigMapper.searchById(Maps.getLong(infoMap, "pigId"));

                        // 3040 - 生产猪死亡 - 表头
                        String baseEntry = mtcBranchID + "-" + billModel.getRowId() + "-" + billModel.getBusinessCode();
                        HanaGatherInvoiceHeaderDetail hanaGatherInvoiceHeaderDetail = new HanaGatherInvoiceHeaderDetail();
                        // 分公司编码
                        hanaGatherInvoiceHeaderDetail.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号
                        hanaGatherInvoiceHeaderDetail.setMTC_BaseEntry(baseEntry);
                        // 业务伙伴编号
                        hanaGatherInvoiceHeaderDetail.setMTC_CardCode(mtcBranchID);
                        // 单据日期
                        hanaGatherInvoiceHeaderDetail.setMTC_DocDate(billModel.getBillDate());
                        // 销售费用
                        hanaGatherInvoiceHeaderDetail.setMTC_Fee("0");
                        // 销售费用说明
                        hanaGatherInvoiceHeaderDetail.setMTC_FeeRmk("0");
                        // 货款差异
                        hanaGatherInvoiceHeaderDetail.setMTC_AmtDiff("0");

                        // 3040 - 生产猪死亡 - 表行
                        HanaGatherInvoiceRowsDetail hanaGatherInvoiceRowsDetail = new HanaGatherInvoiceRowsDetail();
                        // 分公司编码
                        hanaGatherInvoiceRowsDetail.setMTC_BranchID(mtcBranchID);
                        // 猪场编码
                        hanaGatherInvoiceRowsDetail.setMTC_DeptID(mtcDeptID);
                        // 新农+单据编号
                        hanaGatherInvoiceRowsDetail.setMTC_BaseEntry(String.valueOf(billModel.getRowId()));
                        // 新农+单据行号
                        // hanaGatherInvoiceRowsDetail.setMTC_BaseLine(String.valueOf(lineNumber));
                        // 销售类型：固定值：SP - 后备转生产
                        // hanaGatherInvoiceRowsDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_SP);
                        // 品名：固定值：701004 - 商品猪 - 后备种猪
                        // hanaGatherInvoiceRowsDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_RESERVE_PIG);
                        // 猪只耳号：初始耳号
                        hanaGatherInvoiceRowsDetail.setMTC_BatchNum(sapPigModel.getSapFixedAssetsEarbrand());
                        // 猪舍编号
                        hanaGatherInvoiceRowsDetail.setMTC_Unit(HanaUtil.getMTC_Unit(sapPigModel.getHouseId()));
                        // 猪只品种
                        hanaGatherInvoiceRowsDetail.setMTC_Breed(HanaUtil.getMTC_Breed(sapPigModel.getBreedId()));
                        // 猪只性别
                        hanaGatherInvoiceRowsDetail.setMTC_Sex(HanaUtil.getMTC_Sex(sapPigModel.getSex()));
                        // 转生产头数
                        hanaGatherInvoiceRowsDetail.setMTC_Qty("1");
                        // 价格
                        hanaGatherInvoiceRowsDetail.setMTC_Price("0");
                        // 金额
                        hanaGatherInvoiceRowsDetail.setMTC_Total("0");

                        List<HanaGatherInvoiceRowsDetail> listRows = new ArrayList<HanaGatherInvoiceRowsDetail>();
                        listRows.add(hanaGatherInvoiceRowsDetail);
                        hanaGatherInvoiceHeaderDetail.setDetailList(listRows);

                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(billModel.getBillDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:生产猪死亡
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3040);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(baseEntry);
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaGatherInvoiceHeaderDetail);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);

                    }
                }
                // END HANA

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);
                diePigModelList.add(pigModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

                SqlCon deleteWeanSqlCon = new SqlCon();
                deleteWeanSqlCon.addMainSql("UPDATE PP_L_BILL_WEAN SET DELETED_FLAG = '2' WHERE DELETED_FLAG <> '2' AND LAST_STATUS=10");
                deleteWeanSqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
                deleteWeanSqlCon.addCondition(Maps.getLong(infoMap, "pigId"), " AND PIG_ID=? ");
                setSql(weanMapper, deleteWeanSqlCon);
            }
            List<Map<String, Object>> childPigMapList = this.createChildPigModel(commonInfos);
            // 判断猪栏是否还原为NULL
            Long flag = -99L;
            List<Long> sowPigIds = new ArrayList<Long>();
            for (Map<String, Object> childPigMap : childPigMapList) {
                if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                    if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) == 0) {
                        childPigMap.remove("pigpenId");
                        PigModel pigpenIdsPigModel = new PigModel();
                        pigpenIdsPigModel.setRowId(Maps.getLong(childPigMap, "rowId"));
                        pigpenIdsList.add(pigpenIdsPigModel);
                    }
                }

                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                this.swineryIsCloseCheck(Maps.getLong(childPigMap, "swineryId"));

                PigModel childPigModel = getBean(PigModel.class, childPigMap);
                pigModelList.add(childPigModel);

                childPigMap.put("mainTableId", Maps.getLong(childPigMap, "weanMainTableId"));
                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(childPigMap, "lastBillId"));
                updateToworkModelList.add(toworkModel);

                PigEventHisModel childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_wean_detail");
                pigEventHisModelList.add(childPigEventHisModel);

                sowPigIds.add(Maps.getLong(childPigMap, "pigId"));
            }
            SqlCon deleteWeanDetailSqlCon = new SqlCon();
            deleteWeanDetailSqlCon.addMainSql("UPDATE PP_L_BILL_WEAN_DETAIL SET DELETED_FLAG = '2' WHERE DELETED_FLAG <> '2'");
            deleteWeanDetailSqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
            deleteWeanDetailSqlCon.addCondition(StringUtil.listLongToString(sowPigIds), " AND PIG_ID IN ? ", false, true);
            setSql(weanDetailMapper, deleteWeanDetailSqlCon);

            this.commonExecute(leaveIds, commonInfos, pigModelList, null, null, pigEventHisModelList, pigpenIdsList, null, updateToworkModelList,
                    null, null, diePigModelList, null);

            // START HANA
            if (!mtcITFCList.isEmpty()) {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
            // END HANA
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销转保育,转育肥
    private void cancelToChild(Map<String, Object> commonInfos) throws Exception {

        this.houseCapacityCheck(commonInfos);

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);
        List<ToChildModel> toChildModels_ = null;
        List<ToChildModel> toChildModels = new ArrayList<ToChildModel>();

        if (infos != null && !infos.isEmpty()) {

            long[] toChildIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();

            List<PigModel> swineryIdsList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 用于删除或关闭批次
            Set<Long> swineryIdSet = new HashSet<Long>();
            List<SwineryModel> swineryModelList = new ArrayList<SwineryModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            // 还原上一条CHANGE_SWINERY的CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT
            List<ChangeSwineryModel> updateChangeSwineryModelList = new ArrayList<ChangeSwineryModel>();

            Long flag = -99L;

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                toChildIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, pigpenIdsList, swineryIdsList);
                pigModelList.add(pigModel);

                if (!Maps.isEmpty(infoMap, "cancelSwineryId")) {
                    if (flag.compareTo(Maps.getLong(infoMap, "cancelSwineryId")) != 0) {
                        if (!swineryIdSet.contains(Maps.getLong(infoMap, "cancelSwineryId"))) {
                            swineryIdSet.add(Maps.getLong(infoMap, "cancelSwineryId"));
                            SwineryModel swineryModel = new SwineryModel();
                            swineryModel.setRowId(Maps.getLong(infoMap, "cancelSwineryId"));
                            swineryModelList.add(swineryModel);
                        }
                    }
                }

                // PIG_CLASS 发生改变
                if (!Maps.getString(infoMap, "cancelPigClass").equals(Maps.getString(infoMap, "pigClass"))) {
                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                }

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                ChangeSwineryModel changeSwineryModel = new ChangeSwineryModel();
                changeSwineryModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeSwineryModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeSwineryModelList.add(changeSwineryModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }
            SqlCon getBillInfoSqlCon = new SqlCon();
            getBillInfoSqlCon.addMainSql(" SELECT t0.ROW_ID,t0.SWINERY_ID,t0.IN_SWINERY_ID FROM pp_l_bill_to_child t0 INNER JOIN ");
            getBillInfoSqlCon.addMainSql(" (SELECT A.ROW_ID,A.SWINERY_ID,A.IN_SWINERY_ID FROM pp_l_bill_to_child A WHERE ");
            getBillInfoSqlCon.addCondition(Maps.getLong(commonInfos, "billId"), " A.BILL_ID=? ");
            getBillInfoSqlCon.addMainSql(" ORDER BY ROW_ID DESC) t1 ON (t0.ROW_ID=t1.ROW_ID) ");
            getBillInfoSqlCon.addMainSql(" WHERE t0.DELETED_FLAG='0' AND t0.STATUS='1' ");
            getBillInfoSqlCon.addCondition(Maps.getLong(commonInfos, "billId"), " AND t0.BILL_ID=? ");
            getBillInfoSqlCon.addMainSql(" GROUP BY t1.SWINERY_ID,t1.IN_SWINERY_ID ORDER BY t0.ROW_ID ");
            toChildModels_ = setSql(toChildMapper, getBillInfoSqlCon);
            List<String> pigIds = Maps.getList(commonInfos, "pigIds");
            if (pigIds != null && !pigIds.isEmpty() && infos != null) {
                long swineryId = Maps.getLong(infos.get(0), "swineryId");
                for (ToChildModel toChildModel : toChildModels_) {
                    if (toChildModel.getSwineryId() == swineryId) {
                        toChildModels.add(toChildModel);
                    }
                }
            } else {
                toChildModels.addAll(toChildModels_);
            }
            this.commonExecute(toChildIds, commonInfos, pigModelList, null, swineryModelList, pigEventHisModelList, pigpenIdsList, swineryIdsList,
                    updateToworkModelList, updateChangeHouseModelList, updateChangeSwineryModelList, null, null);

        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }
        /************************************ 2016-2-13 yangy 批次日龄撤销 *************************************************/

        if (toChildModels != null && !toChildModels.isEmpty()) {
            for (ToChildModel toChildModel : toChildModels) {
                SqlCon sqlConCancelInfo = new SqlCon();
                sqlConCancelInfo.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND BILL_ID=? ");
                sqlConCancelInfo.addCondition(toChildModel.getSwineryId(), " AND OLD_SWINERY_ID=? ");
                sqlConCancelInfo.addCondition(toChildModel.getInSwineryId(), " AND SWINERY_ID=? ");
                Map<String, String> searchSwineryMap = new HashMap<String, String>();
                searchSwineryMap.put("condition", sqlConCancelInfo.getCondition());
                // 需要被撤销的批次记录
                SwineryDayageChangeModel cancelSwineryDayageChangeModel = null;
                List<SwineryDayageChangeModel> cancelSwineryDayageChangeModels = swineryDayageChangeMapper.searchListByCon(searchSwineryMap);
                if (cancelSwineryDayageChangeModels != null && !cancelSwineryDayageChangeModels.isEmpty()) {
                    cancelSwineryDayageChangeModel = cancelSwineryDayageChangeModels.get(0);
                    // 撤销转入批次日龄记录
                    SqlCon sqlConDeletesSwinery = new SqlCon();
                    Map<String, Object> DeletesSwineryMap = new HashMap<String, Object>();
                    sqlConDeletesSwinery.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND BILL_ID=? ");
                    sqlConDeletesSwinery.addCondition(toChildModel.getSwineryId(), " AND OLD_SWINERY_ID=? ");
                    sqlConDeletesSwinery.addCondition(cancelSwineryDayageChangeModel.getSwineryId(), " AND SWINERY_ID = ? ");
                    sqlConDeletesSwinery.addMainSql(" AND CHANGE_TYPE = 1 ");
                    DeletesSwineryMap.put("condition", sqlConDeletesSwinery.getCondition());
                    swineryDayageChangeMapper.deletesByCon2(DeletesSwineryMap);
                    // 撤销转出批次日龄记录
                    SqlCon fromSqlConDeletesSwinery = new SqlCon();
                    Map<String, Object> fromDeletesSwineryMap = new HashMap<String, Object>();
                    fromSqlConDeletesSwinery.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND BILL_ID=? ");
                    fromSqlConDeletesSwinery.addCondition(toChildModel.getSwineryId(), " AND SWINERY_ID = ? ");
                    fromSqlConDeletesSwinery.addMainSql(" AND CHANGE_TYPE = 2 ");
                    fromDeletesSwineryMap.put("condition", fromSqlConDeletesSwinery.getCondition());
                    swineryDayageChangeMapper.deletesByCon2(fromDeletesSwineryMap);
                }
            }

        }
        /************************************ 2016-2-13 yangy 批次日龄撤销 *************************************************/

    }

    // 撤销换耳号事件
    private void cancelChangeEarBrand(Map<String, Object> commonInfos) throws Exception {
        // CHECK上一次的耳牌记录中的耳号(耳牌号,耳缺号,耳刺号,电子耳号)是否被其他猪只在使用
        SqlCon earBrandCheckSqlCon = new SqlCon();
        earBrandCheckSqlCon.addMainSql("SELECT T1.EAR_BRAND AS earBrand,T2.EAR_BRAND AS oldEarBrand FROM");
        earBrandCheckSqlCon.addMainSql(" PP_L_BILL_CHANGE_EARBRAND T1 INNER JOIN PP_L_BILL_CHANGE_EARBRAND T2");
        earBrandCheckSqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID");
        earBrandCheckSqlCon.addMainSql(" AND T1.CHANGE_EARBRAND_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0')");
        earBrandCheckSqlCon.addMainSql(" INNER JOIN PP_L_PIG T3");
        earBrandCheckSqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T1.PIG_ID = T3.ROW_ID)");
        earBrandCheckSqlCon.addMainSql(" WHERE EXISTS (SELECT 1 FROM PP_L_EAR_CODE");
        earBrandCheckSqlCon.addMainSql(" WHERE FARM_ID = T1.FARM_ID AND DELETED_FLAG = '0' AND(");
        earBrandCheckSqlCon.addMainSql(" (T2.EAR_BRAND IS NOT NULL AND T2.EAR_BRAND <> '' AND EAR_BRAND = T2.EAR_BRAND)");
        earBrandCheckSqlCon.addMainSql(" OR (T2.EAR_SHORT IS NOT NULL AND T2.EAR_SHORT <> '' AND EAR_SHORT = T2.EAR_SHORT");
        earBrandCheckSqlCon.addMainSql(" AND ROW_ID <> T3.EAR_CODE_ID)");
        earBrandCheckSqlCon.addMainSql(" OR (T2.EAR_THORN IS NOT NULL AND T2.EAR_THORN <> '' AND EAR_THORN = T2.EAR_THORN)");
        earBrandCheckSqlCon.addMainSql(
                " OR (T2.ELECTRONIC_EAR_NO IS NOT NULL AND T2.ELECTRONIC_EAR_NO <> '' AND ELECTRONIC_EAR_NO = T2.ELECTRONIC_EAR_NO))");
        earBrandCheckSqlCon.addMainSql(" LIMIT 1)");
        earBrandCheckSqlCon.addMainSql(" AND T1.DELETED_FLAG = '0'");
        earBrandCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        earBrandCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
        earBrandCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND T1.PIG_ID = ?");
        earBrandCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> earBrandCheckSqlMap = new HashMap<String, String>();
        earBrandCheckSqlMap.put("sql", earBrandCheckSqlCon.getCondition());

        List<Map<String, Object>> earBrandCheckInfos = paramMapper.getObjectInfos(earBrandCheckSqlMap);

        if (earBrandCheckInfos != null && !earBrandCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.LAST_EAR_BRAND_HAS_BEEN_USED, Maps.getString(earBrandCheckInfos.get(0), "earBrand"), Maps
                    .getString(earBrandCheckInfos.get(0), "oldEarBrand"));
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT T1.ROW_ID AS mainTableId, T2.EAR_BRAND AS earBrand, T2.EAR_SHORT AS earShort, T2.EAR_THORN AS earThorn, T2.ELECTRONIC_EAR_NO AS electronicEarNo");
        sqlCon.addMainSql(
                ", T3.ROW_ID AS pigId, T3.COMPANY_ID AS companyId, T3.FARM_ID AS farmId, T3.LINE_ID AS lineId, T3.SWINERY_ID AS swineryId, T3.HOUSE_ID AS houseId, T3.PIGPEN_ID AS pigpenId, T3.PIG_TYPE AS pigType, T3.SEX AS sex, T3.PIG_CLASS AS pigClass, T3.PARITY AS parity");
        sqlCon.addMainSql(
                ", T4.ROW_ID AS earCodeId ,T5.LAST_BILL_ID AS lastBillId ,T5.LAST_EVENT_DATE AS lastEventDate, T5.EVENT_DATE AS eventHisLastEventDate");
        sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_EARBRAND T1 INNER JOIN PP_L_BILL_CHANGE_EARBRAND T2");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID AND T1.PIG_ID = T2.PIG_ID AND T1.CHANGE_EARBRAND_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T3");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T3.FARM_ID AND T1.PIG_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T4");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T4.FARM_ID AND T3.EAR_CODE_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T5");
        sqlCon.addMainSql("  ON(T1.FARM_ID = T5.FARM_ID AND T1.PIG_ID = T5.PIG_ID AND T1.BILL_ID = T5.BILL_ID AND T5.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND T1.PIG_ID = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);

        if (infos != null && !infos.isEmpty()) {
            long[] changeEarbrandIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<EarCodeModel> earCodeModelList = new ArrayList<EarCodeModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                changeEarbrandIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);

                EarCodeModel earCodeModel = this.createEarCodeModel(infoMap, false);
                earCodeModelList.add(earCodeModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            this.commonExecute(changeEarbrandIds, commonInfos, pigModelList, earCodeModelList, null, pigEventHisModelList, null, null, null, null,
                    null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销后备转生产公猪 || 后备情期鉴定
    private void cancelReserveProduct(Map<String, Object> commonInfos) throws Exception {

        this.earBrandCheck(commonInfos);

        this.houseCapacityCheck(commonInfos);

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {
            long[] reserveProductIds = new long[infos.size()];

            List<EarCodeModel> earCodeModelList = new ArrayList<EarCodeModel>();

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            // 撤销清空资产卡片号SAP_FIXED_ASSETS_EARBRAND
            List<PigModel> updateSapFixedAssetsEarbrandList = new ArrayList<PigModel>();

            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            Date currentDate = Maps.getDate(commonInfos, "currentDate");
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            List<MTC_ITFC> mtcOITMMTC_ITFCList = new ArrayList<MTC_ITFC>();
            String mtcBranchID = null;
            String mtcDeptID = null;
            BillModel billModel = null;
            if (isToHana) {
                // 所撤销的单据
                billModel = billMapper.searchById(Maps.getLong(commonInfos, "billId"));
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            }
            // END HANA

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                if (PigConstants.PIG_TYPE_BOAR.equals(Maps.getString(infoMap, "pigType"))) {
                    this.breedCheck(infoMap);

                    // START HANA
                    if (isToHana) {
                        // 后备转生产公猪（公猪）
                        PigModel sapPigModel = pigMapper.searchById(Maps.getLong(infoMap, "pigId"));

                        // 3010 - 后备转生产【应收发票】 - 表头
                        String baseEntry = mtcBranchID + "-" + billModel.getRowId() + "-" + billModel.getBusinessCode();
                        HanaGatherInvoiceHeaderDetail hanaGatherInvoiceHeaderDetail = new HanaGatherInvoiceHeaderDetail();
                        // 分公司编码
                        hanaGatherInvoiceHeaderDetail.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号
                        hanaGatherInvoiceHeaderDetail.setMTC_BaseEntry(baseEntry);
                        // 业务伙伴编号
                        hanaGatherInvoiceHeaderDetail.setMTC_CardCode(mtcBranchID);
                        // 单据日期
                        hanaGatherInvoiceHeaderDetail.setMTC_DocDate(billModel.getBillDate());
                        // 销售费用
                        hanaGatherInvoiceHeaderDetail.setMTC_Fee("0");
                        // 销售费用说明
                        hanaGatherInvoiceHeaderDetail.setMTC_FeeRmk("0");
                        // 货款差异
                        hanaGatherInvoiceHeaderDetail.setMTC_AmtDiff("0");

                        // 3010 - 后备转生产【应收发票】 - 表行
                        HanaGatherInvoiceRowsDetail hanaGatherInvoiceRowsDetail = new HanaGatherInvoiceRowsDetail();
                        // 分公司编码
                        hanaGatherInvoiceRowsDetail.setMTC_BranchID(mtcBranchID);
                        // 猪场编码
                        hanaGatherInvoiceRowsDetail.setMTC_DeptID(mtcDeptID);
                        // 新农+单据编号
                        hanaGatherInvoiceRowsDetail.setMTC_BaseEntry(String.valueOf(billModel.getRowId()));
                        // 新农+单据行号
                        // hanaGatherInvoiceRowsDetail.setMTC_BaseLine(String.valueOf(lineNumber));
                        // 销售类型：固定值：SP - 后备转生产
                        hanaGatherInvoiceRowsDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_SP);
                        // 品名：固定值：701004 - 商品猪 - 后备种猪
                        hanaGatherInvoiceRowsDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_RESERVE_PIG);
                        // 猪只耳号：初始耳号
                        hanaGatherInvoiceRowsDetail.setMTC_BatchNum(sapPigModel.getSapFixedAssetsEarbrand());
                        // 猪舍编号
                        hanaGatherInvoiceRowsDetail.setMTC_Unit(HanaUtil.getMTC_Unit(sapPigModel.getHouseId()));
                        // 猪只品种
                        hanaGatherInvoiceRowsDetail.setMTC_Breed(HanaUtil.getMTC_Breed(sapPigModel.getBreedId()));
                        // 猪只性别
                        hanaGatherInvoiceRowsDetail.setMTC_Sex(HanaUtil.getMTC_Sex(sapPigModel.getSex()));
                        // 转生产头数
                        hanaGatherInvoiceRowsDetail.setMTC_Qty("1");
                        // 价格
                        hanaGatherInvoiceRowsDetail.setMTC_Price("0");
                        // 金额
                        hanaGatherInvoiceRowsDetail.setMTC_Total("0");

                        List<HanaGatherInvoiceRowsDetail> listRows = new ArrayList<HanaGatherInvoiceRowsDetail>();
                        listRows.add(hanaGatherInvoiceRowsDetail);
                        hanaGatherInvoiceHeaderDetail.setDetailList(listRows);

                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(billModel.getBillDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:后备转生产 - A 应收发票
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3010);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(baseEntry);
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaGatherInvoiceHeaderDetail);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);

                        MTC_OITM mtcOITM = new MTC_OITM();
                        // 猪只耳号
                        mtcOITM.setMTC_ItemCode(sapPigModel.getSapFixedAssetsEarbrand());
                        // 猪只描述
                        // mtcOITM.setMTC_ItemName(CacheUtil.getName(sapPigModel.getBreedId().toString(), NameEnum.BREED_NAME) + "." + earCodeModel
                        // .getEarBrand());
                        // 猪只性别
                        mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(sapPigModel.getSex()));
                        // 资产类型
                        mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(mtcBranchID, sapPigModel.getSex()));
                        // 猪场编号
                        mtcOITM.setMTC_DeptID(mtcDeptID);
                        // 棚舍编号
                        mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(sapPigModel.getHouseId()));

                        // 3021 - 后备转生产【固定资产卡片 - OITM】的MTC_ITFC
                        MTC_ITFC mtcOITMMTC_ITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcOITMMTC_ITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcOITMMTC_ITFC.setMTC_DocDate(billModel.getBillDate());
                        // 业务代码：后备转生产 - B 固定资产卡片
                        mtcOITMMTC_ITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3011);
                        // 新农+单据编号
                        mtcOITMMTC_ITFC.setMTC_DocNum(sapPigModel.getSapFixedAssetsEarbrand());
                        // 优先级
                        mtcOITMMTC_ITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcOITMMTC_ITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcOITMMTC_ITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcOITMMTC_ITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcOITMMTC_ITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFileMtcOITM = HanaJacksonUtil.objectToJackson(mtcOITM);
                        mtcOITMMTC_ITFC.setMTC_DataFile(jsonDataFileMtcOITM);
                        // JSON文件长度
                        mtcOITMMTC_ITFC.setMTC_DataFileLen(jsonDataFileMtcOITM.length());

                        mtcOITMMTC_ITFCList.add(mtcOITMMTC_ITFC);
                    }
                    // END HANA
                }

                reserveProductIds[i] = Maps.getLong(infoMap, "mainTableId");

                EarCodeModel earCodeModel = this.createEarCodeModel(infoMap, true);
                earCodeModelList.add(earCodeModel);

                PigModel pigModel = this.createPigModel(infoMap, pigpenIdsList, null);
                pigModelList.add(pigModel);
                updateSapFixedAssetsEarbrandList.add(pigModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            this.commonExecute(reserveProductIds, commonInfos, pigModelList, earCodeModelList, null, pigEventHisModelList, pigpenIdsList, null,
                    updateToworkModelList, updateChangeHouseModelList, null, null, updateSapFixedAssetsEarbrandList);

            // START HANA
            if (!mtcITFCList.isEmpty()) {
                mtcITFCList.addAll(mtcOITMMTC_ITFCList);
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
            // END HANA
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销配种
    private void cancelBreed(Map<String, Object> commonInfos) throws Exception {

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] breedIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> swineryIdsList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            List<SpermInfoModel> updateSpermInfoModelList = new ArrayList<SpermInfoModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            // 还原上一条CHANGE_SWINERY的CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT
            List<ChangeSwineryModel> updateChangeSwineryModelList = new ArrayList<ChangeSwineryModel>();

            // 撤销清空资产卡片号SAP_FIXED_ASSETS_EARBRAND
            List<PigModel> updateSapFixedAssetsEarbrandList = new ArrayList<PigModel>();

            long[] deleteSpermInfoIds = new long[infos.size()];

            Map<Long, Double> semenMap = new HashMap<>();

            // 用于去掉重复的猪群
            Set<Long> swineryIdSet = new HashSet<Long>();
            List<SwineryModel> swineryModelList = new ArrayList<SwineryModel>();

            Long flag = -99L;

            // 人工授精使用库存精液（不自动采精）
            String pzqcjFlag = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "PZQCJ");

            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            Date currentDate = Maps.getDate(commonInfos, "currentDate");
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            List<MTC_ITFC> mtcOITMMTC_ITFCList = new ArrayList<MTC_ITFC>();
            String mtcBranchID = null;
            String mtcDeptID = null;
            BillModel billModel = null;
            if (isToHana) {
                // 所撤销的单据
                billModel = billMapper.searchById(Maps.getLong(commonInfos, "billId"));
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            }
            // END HANA

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                breedIds[i] = Maps.getLong(infoMap, "mainTableId");

                // START HANA
                if (isToHana) {
                    // 原类型是后备母猪（第一次配种）
                    if (Maps.getLong(infoMap, "pigClass") == PigConstants.PIG_CLASS_HBDP) {
                        // 后备转生产公猪（母猪）
                        PigModel sapPigModel = pigMapper.searchById(Maps.getLong(infoMap, "pigId"));

                        // 3010 - 后备转生产【应收发票】 - 表头
                        String baseEntry = mtcBranchID + "-" + billModel.getRowId() + "-" + billModel.getBusinessCode();
                        HanaGatherInvoiceHeaderDetail hanaGatherInvoiceHeaderDetail = new HanaGatherInvoiceHeaderDetail();
                        // 分公司编码
                        hanaGatherInvoiceHeaderDetail.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号
                        hanaGatherInvoiceHeaderDetail.setMTC_BaseEntry(baseEntry);
                        // 业务伙伴编号
                        hanaGatherInvoiceHeaderDetail.setMTC_CardCode(mtcBranchID);
                        // 单据日期
                        hanaGatherInvoiceHeaderDetail.setMTC_DocDate(billModel.getBillDate());
                        // 销售费用
                        hanaGatherInvoiceHeaderDetail.setMTC_Fee("0");
                        // 销售费用说明
                        hanaGatherInvoiceHeaderDetail.setMTC_FeeRmk("0");
                        // 货款差异
                        hanaGatherInvoiceHeaderDetail.setMTC_AmtDiff("0");

                        // 3010 - 后备转生产【应收发票】 - 表行
                        HanaGatherInvoiceRowsDetail hanaGatherInvoiceRowsDetail = new HanaGatherInvoiceRowsDetail();
                        // 分公司编码
                        hanaGatherInvoiceRowsDetail.setMTC_BranchID(mtcBranchID);
                        // 猪场编码
                        hanaGatherInvoiceRowsDetail.setMTC_DeptID(mtcDeptID);
                        // 新农+单据编号
                        hanaGatherInvoiceRowsDetail.setMTC_BaseEntry(String.valueOf(billModel.getRowId()));
                        // 新农+单据行号
                        // hanaGatherInvoiceRowsDetail.setMTC_BaseLine(String.valueOf(lineNumber));
                        // 销售类型：固定值：SP - 后备转生产
                        hanaGatherInvoiceRowsDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_SP);
                        // 品名：固定值：701004 - 商品猪 - 后备种猪
                        hanaGatherInvoiceRowsDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_RESERVE_PIG);
                        // 猪只耳号：初始耳号
                        hanaGatherInvoiceRowsDetail.setMTC_BatchNum(sapPigModel.getSapFixedAssetsEarbrand());
                        // 猪舍编号
                        hanaGatherInvoiceRowsDetail.setMTC_Unit(HanaUtil.getMTC_Unit(sapPigModel.getHouseId()));
                        // 猪只品种
                        hanaGatherInvoiceRowsDetail.setMTC_Breed(HanaUtil.getMTC_Breed(sapPigModel.getBreedId()));
                        // 猪只性别
                        hanaGatherInvoiceRowsDetail.setMTC_Sex(HanaUtil.getMTC_Sex(sapPigModel.getSex()));
                        // 转生产头数
                        hanaGatherInvoiceRowsDetail.setMTC_Qty("1");
                        // 价格
                        hanaGatherInvoiceRowsDetail.setMTC_Price("0");
                        // 金额
                        hanaGatherInvoiceRowsDetail.setMTC_Total("0");

                        List<HanaGatherInvoiceRowsDetail> listRows = new ArrayList<HanaGatherInvoiceRowsDetail>();
                        listRows.add(hanaGatherInvoiceRowsDetail);
                        hanaGatherInvoiceHeaderDetail.setDetailList(listRows);

                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(billModel.getBillDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:后备转生产 - A 应收发票
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3010);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(baseEntry);
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaGatherInvoiceHeaderDetail);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);

                        MTC_OITM mtcOITM = new MTC_OITM();
                        // 猪只耳号
                        mtcOITM.setMTC_ItemCode(sapPigModel.getSapFixedAssetsEarbrand());
                        // 猪只描述
                        // mtcOITM.setMTC_ItemName(CacheUtil.getName(sapPigModel.getBreedId().toString(), NameEnum.BREED_NAME) + "." + earCodeModel
                        // .getEarBrand());
                        // 猪只性别
                        mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(sapPigModel.getSex()));
                        // 资产类型
                        mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(mtcBranchID, sapPigModel.getSex()));
                        // 猪场编号
                        mtcOITM.setMTC_DeptID(mtcDeptID);
                        // 棚舍编号
                        mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(sapPigModel.getHouseId()));

                        // 3021 - 后备转生产【固定资产卡片 - OITM】的MTC_ITFC
                        MTC_ITFC mtcOITMMTC_ITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcOITMMTC_ITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcOITMMTC_ITFC.setMTC_DocDate(billModel.getBillDate());
                        // 业务代码：后备转生产 - B 固定资产卡片
                        mtcOITMMTC_ITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3011);
                        // 新农+单据编号
                        mtcOITMMTC_ITFC.setMTC_DocNum(sapPigModel.getSapFixedAssetsEarbrand());
                        // 优先级
                        mtcOITMMTC_ITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcOITMMTC_ITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 创建日期
                        mtcOITMMTC_ITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcOITMMTC_ITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcOITMMTC_ITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFileMtcOITM = HanaJacksonUtil.objectToJackson(mtcOITM);
                        mtcOITMMTC_ITFC.setMTC_DataFile(jsonDataFileMtcOITM);
                        // JSON文件长度
                        mtcOITMMTC_ITFC.setMTC_DataFileLen(jsonDataFileMtcOITM.length());

                        mtcOITMMTC_ITFCList.add(mtcOITMMTC_ITFC);
                    }
                    // END HANA
                }

                PigModel pigModel = this.createPigModel(infoMap, null, swineryIdsList);
                // 胎次
                pigModel.setParity(Maps.getLong(infoMap, "parity"));
                // 原类型是后备母猪（第一次配种）
                if (Maps.getLong(infoMap, "pigClass") == PigConstants.PIG_CLASS_HBDP) {
                    updateSapFixedAssetsEarbrandList.add(pigModel);
                }
                pigModelList.add(pigModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                ChangeSwineryModel changeSwineryModel = new ChangeSwineryModel();
                changeSwineryModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeSwineryModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeSwineryModelList.add(changeSwineryModel);

                if (!Maps.isEmpty(infoMap, "cancelSwineryId")) {
                    if (flag.compareTo(Maps.getLong(infoMap, "cancelSwineryId")) != 0) {
                        if (!swineryIdSet.contains(Maps.getLong(infoMap, "cancelSwineryId"))) {
                            swineryIdSet.add(Maps.getLong(infoMap, "cancelSwineryId"));
                            SwineryModel swineryModel = new SwineryModel();
                            swineryModel.setRowId(Maps.getLong(infoMap, "cancelSwineryId"));
                            swineryModelList.add(swineryModel);
                        }
                    }
                }

                // 获取精液信息
                List<Map<String, Object>> spermInfoList = getSpermInfoList(infoMap);

                // 人工授精(不采精)
                if (PigConstants.PIG_BREED_TYPE_SPE.equals(Maps.getString(infoMap, "breedType")) && "ON".equals(pzqcjFlag)) {

                    for (Map<String, Object> spermInfoMap : spermInfoList) {
                        SpermInfoModel spermInfoModel = new SpermInfoModel();
                        spermInfoModel.setRowId(Maps.getLong(spermInfoMap, "spermInfoId"));
                        updateSpermInfoModelList.add(spermInfoModel);
                    }

                    // 本交 || 人工授精（采精）
                } else if (PigConstants.PIG_BREED_TYPE_NAT.equals(Maps.getString(infoMap, "breedType")) || (PigConstants.PIG_BREED_TYPE_SPE.equals(
                        Maps.getString(infoMap, "breedType")) && "OFF".equals(pzqcjFlag))) {
                    Map<String, Object> spermInfoMap = spermInfoList.get(0);
                    // semen表的特殊操作，计算anhNum，如果等于0则是删除，否则是更新
                    if (!semenMap.containsKey(Maps.getLong(spermInfoMap, "semenId"))) {
                        // 不同的semenId
                        semenMap.put(Maps.getLong(spermInfoMap, "semenId"), Maps.getDouble(spermInfoMap, "anhNum") - 1);
                    } else {
                        // 相同的semenId,减少数量
                        semenMap.put(Maps.getLong(spermInfoMap, "semenId"), semenMap.get(Maps.getLong(spermInfoMap, "semenId")) - 1);
                    }

                    deleteSpermInfoIds[i] = Maps.getLong(spermInfoMap, "spermInfoId");

                }

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            List<SemenModel> updateSemenModelList = new ArrayList<SemenModel>();
            List<Long> deleteSemenModelList = new ArrayList<Long>();

            for (Entry<Long, Double> entry : semenMap.entrySet()) {
                if (entry.getValue().compareTo(0D) == 0) {
                    deleteSemenModelList.add(entry.getKey());
                } else {
                    SemenModel semenModel = new SemenModel();
                    semenModel.setRowId(entry.getKey());
                    semenModel.setAnhNum(entry.getValue());
                    updateSemenModelList.add(semenModel);
                }
            }

            List<SemenModel> cancelSemenModelList = new ArrayList<SemenModel>();
            for (Long semenId : deleteSemenModelList) {
                SemenModel semenModel = new SemenModel();
                semenModel.setRowId(semenId);
                semenModel.setDeletedBillId(Maps.getLongClass(commonInfos, "cancelEventBillId"));
                cancelSemenModelList.add(semenModel);
            }

            if (!cancelSemenModelList.isEmpty()) {
                semenMapper.deletesForCancel(cancelSemenModelList);
            }

            if (updateSemenModelList != null && !updateSemenModelList.isEmpty()) {
                semenMapper.updates(updateSemenModelList);
            }

            if (updateSpermInfoModelList != null && !updateSpermInfoModelList.isEmpty()) {
                SqlCon updateSpermInfoSqlCon = new SqlCon();
                updateSpermInfoSqlCon.addMainSql("UPDATE PP_L_SPERM_INFO SET STATUS = '1', SOW_PIG_ID = NULL, USE_DATE = NULL");
                updateSpermInfoSqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ? AND STATUS = '2' AND DELETED_FLAG = '0' ");
                updateSpermInfoSqlCon.addMainSql(" AND ROW_ID IN (");
                for (int i = 0; i < updateSpermInfoModelList.size(); i++) {
                    updateSpermInfoSqlCon.addMainSql(updateSpermInfoModelList.get(i).getRowId().toString());
                    if (i != updateSpermInfoModelList.size() - 1) {
                        updateSpermInfoSqlCon.addMainSql(",");
                    }
                }
                updateSpermInfoSqlCon.addMainSql(")");
                setSql(spermInfoMapper, updateSpermInfoSqlCon);
            }

            if (deleteSpermInfoIds.length > 0) {
                spermInfoMapper.deletes(deleteSpermInfoIds);
            }

            this.commonExecute(breedIds, commonInfos, pigModelList, null, swineryModelList, pigEventHisModelList, null, swineryIdsList,
                    updateToworkModelList, updateChangeHouseModelList, updateChangeSwineryModelList, null, updateSapFixedAssetsEarbrandList);

            // START HANA
            if (!mtcITFCList.isEmpty()) {
                mtcITFCList.addAll(mtcOITMMTC_ITFCList);
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
            // END HANA
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销妊娠检查、流产
    private void cancelPregnancyCheck(Map<String, Object> commonInfos) throws Exception {

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] pregnancyCheckIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                pregnancyCheckIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);

                // PIG_CLASS 发生改变
                if (!Maps.getString(infoMap, "cancelPigClass").equals(Maps.getString(infoMap, "pigClass"))) {
                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                }

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            this.commonExecute(pregnancyCheckIds, commonInfos, pigModelList, null, null, pigEventHisModelList, null, null, updateToworkModelList,
                    null, null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销转产房 || 种猪转舍
    private void cancelChangeHouse(Map<String, Object> commonInfos) throws Exception {
        String eventCode = Maps.getString(commonInfos, "eventCode");
        Long billId = Maps.getLong(commonInfos, "billId");

        /* BEGIN 转产房内带小猪修改 */
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            List<Long> changeHouseIdsList = new ArrayList<Long>();

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();

            // 用于删除和关闭批次
            Set<Long> swineryIdSet = new HashSet<Long>();
            List<SwineryModel> swineryModelList = new ArrayList<SwineryModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            //还原上一条CHANGE_SWINERY的CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT
            List<ChangeSwineryModel> updateChangeSwineryModelList = new ArrayList<ChangeSwineryModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            // 还原LEAVE表
            List<LeaveModel> updateLeaveModelList = new ArrayList<LeaveModel>();

            // 还原CHILD_DIE表
            List<ChildDieModel> updateChildDieModelList = new ArrayList<ChildDieModel>();
            
            // 还原DELIVERY表
            List<DeliveryModel> updateDeliveryModelList = new ArrayList<DeliveryModel>();


            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);

                // 母猪撤销转舍，上一个舍是产房时，CHECK那个猪栏是否被母猪占用，一个产房猪栏只能有一个母猪
                if (PigConstants.PIG_TYPE_SOW.equals(Maps.getString(infoMap, "pigType"))) {
                    if (!Maps.isEmpty(infoMap, "houseId") && !Maps.isEmpty(infoMap, "pigpenId")) {
                        HouseModel housemodel = houseMapper.searchById(Maps.getLongClass(infoMap, "houseId"));
                        if (housemodel.getHouseType() == PigConstants.HOUSE_CLASS_DELIVERY) {
                            this.deliveryHousePigpenHasOtherSowPigCheck(Maps.getLong(infoMap, "pigId"), Maps.getLong(infoMap, "houseId"), Maps
                                    .getLong(infoMap, "pigpenId"));
                            }
                        }
                }

                changeHouseIdsList.add(Maps.getLong(infoMap, "mainTableId"));

                PigModel pigModel = this.createPigModel(infoMap, pigpenIdsList, null);
                pigModelList.add(pigModel);

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

                // 撤销事件为转产房
                if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)) {
                    // 上一个哺乳状态，产房内转时，撤销同时转舍转群仔猪（包括死仔猪--分娩死亡和仔猪死亡）
                    if (PigConstants.PIG_CLASS_BR == Maps.getLong(infoMap, "pigClass")) {
                        // 判断猪栏猪群是否还原为NULL
                        Long flag = -99L;

                        // 母猪ID
                        Long pigId = Maps.getLong(infoMap, "pigId");

                        PigModel sowPigModel = pigMapper.searchById(pigId);

                        SqlCon deliverySqlCon = new SqlCon();
                        deliverySqlCon.addCondition(sowPigModel.getRowId(), " AND PIG_ID = ?");
                        deliverySqlCon.addCondition(sowPigModel.getParity(), " AND PARITY = ?");

                        List<DeliveryModel> deliveryModelList = getList(deliveryMapper, deliverySqlCon);

                        if (deliveryModelList.size() != 1) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪只【" + Maps.getString(infoMap, "earBrand")
                                    + "】不存在或存在多条分娩记录，请联系管理员。。。");
                        }

                        DeliveryModel deliveryModel = deliveryModelList.get(0);

                        // 仔猪原来的批次
                        Long childSwineryId = null;

                        List<Map<String, Object>> childPigMapList = this.createChildPigModel(eventCode, pigId, null, billId);

                        if (childPigMapList != null && !childPigMapList.isEmpty()) {
                            for (Map<String, Object> childPigMap : childPigMapList) {

                                if (!Maps.isEmpty(childPigMap, "cancelSwineryId")) {
                                    if (flag.compareTo(Maps.getLong(childPigMap, "cancelSwineryId")) != 0) {
                                        if (!swineryIdSet.contains(Maps.getLong(childPigMap, "cancelSwineryId"))) {
                                            swineryIdSet.add(Maps.getLong(childPigMap, "cancelSwineryId"));
                                            SwineryModel swineryModel = new SwineryModel();
                                            swineryModel.setRowId(Maps.getLong(childPigMap, "cancelSwineryId"));
                                            swineryModelList.add(swineryModel);
                                        }
                                    }
                                }

                                if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                                    if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) == 0) {
                                        childPigMap.remove("pigpenId");
                                        PigModel pigpenIdsPigModel = new PigModel();
                                        pigpenIdsPigModel.setRowId(Maps.getLong(childPigMap, "rowId"));
                                        pigpenIdsList.add(pigpenIdsPigModel);
                                    }
                                }

                                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                                this.swineryIsCloseCheck(Maps.getLong(childPigMap, "swineryId"));

                                if(childSwineryId == null){
                                    childSwineryId = Maps.getLong(childPigMap, "swineryId");
                                }

                                changeHouseIdsList.add(Maps.getLong(childPigMap, "mainTableId"));

                                PigModel childPigModel = getBean(PigModel.class, childPigMap);
                                pigModelList.add(childPigModel);

                                ChangeHouseModel childChangeHouseModel = new ChangeHouseModel();
                                childChangeHouseModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                                childChangeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                                updateChangeHouseModelList.add(childChangeHouseModel);

                                ChangeSwineryModel childChangeSwineryModel = new ChangeSwineryModel();
                                childChangeSwineryModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                                childChangeSwineryModel.setBillId(Maps.getLong(commonInfos, "billId"));
                                updateChangeSwineryModelList.add(childChangeSwineryModel);

                                PigEventHisModel childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_change_house");
                                pigEventHisModelList.add(childPigEventHisModel);

                                // 如果内转前就是死亡的猪只,还原死亡记录
                                if (PigConstants.PIG_CLASS_SW == Maps.getLong(childPigMap, "pigClass")) {
                                    SqlCon leaveSqlCon = new SqlCon();
                                    leaveSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
                                    leaveSqlCon.addCondition(Maps.getLong(childPigMap, "pigId"), " AND PIG_ID = ?");
                                    List<LeaveModel> leaveModelList = getList(leaveMapper, leaveSqlCon);
                                    if (leaveModelList.size() != 1) {
                                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪只【" + Maps.getString(infoMap, "earBrand")
                                                + "】不存在或存在多条死亡记录，请联系管理员。。。");
                                    }
                                    LeaveModel leaveModel = leaveModelList.get(0);
                                    leaveModel.setHouseId(Maps.getLong(childPigMap, "houseId"));
                                    if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                                        if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) != 0) {
                                            leaveModel.setPigpenId(Maps.getLong(childPigMap, "pigpenId"));
                                        } else {
                                            leaveModel.setPigpenId(0L);
                                        }
                                    } else {
                                        leaveModel.setPigpenId(0L);
                                    }
                                    leaveModel.setSwineryId(Maps.getLong(childPigMap, "swineryId"));
                                    leaveModel.setPerformanceHouseId(Maps.getLong(childPigMap, "houseId"));
                                    updateLeaveModelList.add(leaveModel);

                                    if (leaveModel.getBillId() != deliveryModel.getBillId()) {
                                        // 仔猪死亡（非分娩死亡），修改pp_l_bill_child_die
                                        SqlCon childDieSqlCon = new SqlCon();
                                        childDieSqlCon.addCondition(leaveModel.getBillId(), " AND BILL_ID = ?");
                                        childDieSqlCon.addCondition(sowPigModel.getRowId(), " AND PIG_ID = ?");
                                        childDieSqlCon.addCondition(sowPigModel.getParity(), " AND PARITY = ?");

                                        List<ChildDieModel> childDieModelList = getList(childDieMapper, childDieSqlCon);
                                        for (ChildDieModel childDieModel : childDieModelList) {
                                            childDieModel.setHouseId(Maps.getLongClass(infoMap, "houseId"));
                                            if (!Maps.isEmpty(infoMap, "pigpenId")) {
                                                if (flag.compareTo(Maps.getLong(infoMap, "pigpenId")) != 0) {
                                                    childDieModel.setPigpenId(Maps.getLong(infoMap, "pigpenId"));
                                                } else {
                                                    childDieModel.setPigpenId(0L);
                                                }
                                            } else {
                                                childDieModel.setPigpenId(0L);
                                            }
                                            updateChildDieModelList.add(childDieModel);
                                        }
                                    }
                                }
                            }
                        }

                        // cancelPorkSwineryId
                        Long cancelPorkSwineryId = deliveryModel.getPorkSwineryId();

                        // 还原回分娩记录
                        deliveryModel.setHouseId(Maps.getLong(infoMap, "houseId"));
                        if (!Maps.isEmpty(infoMap, "pigpenId")) {
                            if (flag.compareTo(Maps.getLong(infoMap, "pigpenId")) != 0) {
                                deliveryModel.setPigpenId(Maps.getLong(infoMap, "pigpenId"));
                            } else {
                                // 防止特殊情况，一般情况分娩记录必须有栏位
                                deliveryModel.setPigpenId(0L);
                            }
                        } else {
                            // 防止特殊情况，一般情况分娩记录必须有栏位
                            deliveryModel.setPigpenId(0L);
                        }
                        deliveryModel.setPerformanceHouseId(Maps.getLong(infoMap, "houseId"));
                        deliveryModel.setPorkSwineryId(childSwineryId);
                        updateDeliveryModelList.add(deliveryModel);

                        // 还原pp_l_bill_swinery_dayage_change数据
                        SqlCon porkSwineryDayageChangeSqlCon = new SqlCon();
                        porkSwineryDayageChangeSqlCon.addMainSql("UPDATE PP_L_BILL_SWINERY_DAYAGE_CHANGE SET DELETED_FLAG = '0'");
                        porkSwineryDayageChangeSqlCon.addMainSql(" ,NOTES = NULL");
                        // 产房内转，原日龄记录设置为DELETED_FLAG设为3
                        porkSwineryDayageChangeSqlCon.addMainSql(" WHERE DELETED_FLAG = '3'");
                        porkSwineryDayageChangeSqlCon.addCondition(deliveryModel.getPigId(), " AND PIG_ID = ?");
                        porkSwineryDayageChangeSqlCon.addCondition(childSwineryId, " AND SWINERY_ID = ?");

                        paramMapperSetSQL(paramMapper, porkSwineryDayageChangeSqlCon);

                        // 撤销产房内转pp_l_bill_swinery_dayage_change数据
                        SqlCon cancelPorkSwineryDayageChangeSqlCon = new SqlCon();
                        cancelPorkSwineryDayageChangeSqlCon.addMainSql("UPDATE PP_L_BILL_SWINERY_DAYAGE_CHANGE SET DELETED_FLAG = '2'");
                        cancelPorkSwineryDayageChangeSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                        cancelPorkSwineryDayageChangeSqlCon.addCondition(deliveryModel.getPigId(), " AND PIG_ID = ?");
                        cancelPorkSwineryDayageChangeSqlCon.addCondition(cancelPorkSwineryId, " AND SWINERY_ID = ?");

                        paramMapperSetSQL(paramMapper, cancelPorkSwineryDayageChangeSqlCon);
                    }
                }

            }

            // 还原仔猪LEAVE表
            if (!updateLeaveModelList.isEmpty()) {
                leaveMapper.updates(updateLeaveModelList);
            }
            // 还原仔猪死亡CHILDDIE表
            if (!updateChildDieModelList.isEmpty()) {
                childDieMapper.updates(updateChildDieModelList);
            }
            // 还原分娩DELIVERY表
            if (!updateDeliveryModelList.isEmpty()) {
                deliveryMapper.updates(updateDeliveryModelList);
            }

            long[] changeHouseIds = longArrayFromList(changeHouseIdsList);
            /* END 转产房内带小猪修改 */

            this.commonExecute(changeHouseIds, commonInfos, pigModelList, null, swineryModelList, pigEventHisModelList, pigpenIdsList, null, null,
                    updateChangeHouseModelList, updateChangeSwineryModelList, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销分娩
    private void cancelDelivery(Map<String, Object> commonInfos) throws Exception {

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] deliveryIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                deliveryIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);
            }

            List<Map<String, Object>> childPigMapList = this.createChildPigModel(commonInfos);

            if (childPigMapList != null && !childPigMapList.isEmpty()) {
                long[] deleteChildPigModelIds = new long[childPigMapList.size()];
                long[] earCodeIds = new long[childPigMapList.size()];
                for (int i = 0; i < childPigMapList.size(); i++) {
                    Map<String, Object> childPigMap = childPigMapList.get(i);
                    deleteChildPigModelIds[i] = Maps.getLongClass(childPigMap, "rowId");
                    earCodeIds[i] = Maps.getLongClass(childPigMap, "earCodeId");
                }

                if (deleteChildPigModelIds.length > 0) {
                    pigMapper.deletesForCancel(deleteChildPigModelIds);
                }

                if (earCodeIds.length > 0) {
                    earCodeMapper.deletes(earCodeIds);
                }
                // 猪只或者单条记录删除
                Long billId = Maps.getLongClass(commonInfos, "billId");
                Long pigId = Maps.getLongClass(commonInfos, "pigId");
                if (pigId != null) {
                    SqlCon deletesByConSqlCon = new SqlCon();
                    deletesByConSqlCon.addCondition(billId, " AND BILL_ID = ?");
                    deletesByConSqlCon.addMainSql(" AND PIG_ID IN (");
                    for (int i = 0; i < childPigMapList.size(); i++) {
                        deletesByConSqlCon.addMainSql(String.valueOf(Maps.getLongClass(childPigMapList.get(i), "rowId")));
                        if (i != childPigMapList.size() - 1) {
                            deletesByConSqlCon.addMainSql(",");
                        }
                    }
                    deletesByConSqlCon.addMainSql(")");

                    Map<String, Object> deletesByConMap = new HashMap<String, Object>();
                    deletesByConMap.put("condition", deletesByConSqlCon.getCondition());
                    deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));

                    leaveMapper.deletesByCon2(deletesByConMap);
                    toworkMapper.deletesByCon2(deletesByConMap);
                    changeHouseMapper.deletesByCon2(deletesByConMap);
                    changeSwineryMapper.deletesByCon2(deletesByConMap);
                    changeEarbrandMapper.deletesByCon2(deletesByConMap);
                    pigEventRelatesMapper.deletesByCon2(deletesByConMap);
                }
            }

            this.commonExecute(deliveryIds, commonInfos, pigModelList, null, null, pigEventHisModelList, null, null, updateToworkModelList, null,
                    null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }
        for (int i = 0; i < infos.size(); i++) {
            Map<String, Object> infoMap = infos.get(i);
            /************************************ 2016-2-13 yangy 批次日龄撤销 *************************************************/
            SqlCon sqlConCancelInfo = new SqlCon();
            sqlConCancelInfo.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND BILL_ID=? ");
            sqlConCancelInfo.addCondition(Maps.getLong(infoMap, "pigId"), " AND PIG_ID=? ");
            Map<String, String> searchSwineryMap = new HashMap<String, String>();
            searchSwineryMap.put("condition", sqlConCancelInfo.getCondition());
            // 需要被撤销的批次记录
            SwineryDayageChangeModel cancelSwineryDayageChangeModel = null;
            List<SwineryDayageChangeModel> cancelSwineryDayageChangeModels = swineryDayageChangeMapper.searchListByCon(searchSwineryMap);
            if (cancelSwineryDayageChangeModels != null && !cancelSwineryDayageChangeModels.isEmpty()) {
                cancelSwineryDayageChangeModel = cancelSwineryDayageChangeModels.get(0);
                // 撤销该批次日龄记录
                SqlCon sqlConDeletesSwinery = new SqlCon();
                Map<String, Object> DeletesSwineryMap = new HashMap<String, Object>();
                sqlConDeletesSwinery.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND BILL_ID=? ");
                sqlConDeletesSwinery.addCondition(cancelSwineryDayageChangeModel.getSwineryId(), " AND SWINERY_ID=? ");
                sqlConDeletesSwinery.addCondition(Maps.getLong(infoMap, "pigId"), " AND PIG_ID=? ");
                DeletesSwineryMap.put("condition", sqlConDeletesSwinery.getCondition());
                swineryDayageChangeMapper.deletesByCon2(DeletesSwineryMap);
            }
            /************************************ 2016-2-13 yangy 批次日龄撤销 *************************************************/
        }

    }

    // 撤销断奶
    private void cancelWean(Map<String, Object> commonInfos) throws Exception {

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);
        // 获得寄入母猪的信息
        List<Map<String, Object>> boardInfos = this.getBoardInfos(commonInfos);
        /********************************* 2017-8-11 自动寄养死亡不能单个撤销 ********************************/
        if (Maps.getString(commonInfos, "pigId") != null) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT COUNT(1) autoFosterDieNum FROM pp_l_bill_foster_care WHERE DELETED_FLAG='0' ");
            sqlCon.addCondition(getFarmId(), " AND FARM_ID=?  ");
            sqlCon.addCondition(Maps.getLong(commonInfos, "billId"), " AND BILL_ID=? ");
            Map<String, String> sqlConMap = new HashMap<String, String>();
            sqlConMap.put("sql", sqlCon.getCondition());
            List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlConMap);
            if (list != null && !list.isEmpty()) {
                long autoFosterDieNum = Maps.getLong(list.get(0), "autoFosterDieNum");
                if (autoFosterDieNum > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！断奶自动寄养死亡不能撤销单个猪只。。。");
                }
            }
        }
        /********************************* 2017-8-11 自动寄养死亡不能单个撤销 ********************************/
        if (infos != null && !infos.isEmpty()) {

            long[] weanIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            // 死亡仔猪
            List<PigModel> diePigModels = new ArrayList<PigModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);

                // 母猪撤销断奶，上一个舍是产房时，CHECK那个猪栏是否被母猪占用，一个产房猪栏只能有一个母猪
                if (!Maps.isEmpty(infoMap, "houseId") && !Maps.isEmpty(infoMap, "pigpenId")) {
                    HouseModel housemodel = houseMapper.searchById(Maps.getLongClass(infoMap, "houseId"));
                    if (housemodel.getHouseType() == PigConstants.HOUSE_CLASS_DELIVERY) {
                        this.deliveryHousePigpenHasOtherSowPigCheck(Maps.getLong(infoMap, "pigId"), Maps.getLong(infoMap, "houseId"), Maps.getLong(
                                infoMap, "pigpenId"));
                    }
                }

                weanIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);

                // PIG_CLASS 发生改变
                if (!Maps.getString(infoMap, "cancelPigClass").equals(Maps.getString(infoMap, "pigClass"))) {
                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                }

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(infoMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            // 判断猪栏是否还原为NULL
            Long flag = -99L;

            List<Map<String, Object>> childPigMapList = this.createChildPigModel(commonInfos);
            for (Map<String, Object> childPigMap : childPigMapList) {

                if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                    if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) == 0) {
                        childPigMap.remove("pigpenId");
                        PigModel pigpenIdsPigModel = new PigModel();
                        pigpenIdsPigModel.setRowId(Maps.getLong(childPigMap, "rowId"));
                        pigpenIdsList.add(pigpenIdsPigModel);
                    }
                }

                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                this.swineryIsCloseCheck(Maps.getLong(childPigMap, "swineryId"));

                PigModel childPigModel = getBean(PigModel.class, childPigMap);
                pigModelList.add(childPigModel);
                diePigModels.add(childPigModel);
                PigEventHisModel childPigEventHisModel = null;

                if (!Maps.isEmpty(childPigMap, "weanMainTableId")) {
                    childPigMap.put("mainTableId", Maps.getLong(childPigMap, "weanMainTableId"));
                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                    childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_wean_detail");
                } else if (!Maps.isEmpty(childPigMap, "leaveMainTableId")) {
                    childPigMap.put("mainTableId", Maps.getLong(childPigMap, "leaveMainTableId"));
                    childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_leave");

                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                }
                pigEventHisModelList.add(childPigEventHisModel);
            }

            // 添加寄入母猪到List中
            if (!Maps.isEmpty(commonInfos, "pigId")) {
                // 猪只撤销
                if (boardInfos != null && !boardInfos.isEmpty()) {
                    // 特殊点：同一单据中，一个母猪被多次寄养
                    // 猪只撤销只有一条数据
                    Map<String, Object> boardInfoMaps = boardInfos.get(0);
                    PigModel boardPigModel = this.createPigModel(boardInfoMaps, null, null);
                    pigMapper.update(boardPigModel);
                    Map<String, Object> deletesByConMap = new HashMap<String, Object>();
                    deletesByConMap.put("condition", " AND ROW_ID = " + Maps.getLong(boardInfoMaps, "relatesId"));
                    deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));
                    pigEventRelatesMapper.deletesByCon2(deletesByConMap);
                }
            } else {
                // 单据撤销
                if (boardInfos != null && !boardInfos.isEmpty()) {
                    for (int i = 0; i < boardInfos.size(); i++) {
                        Map<String, Object> boardInfoMaps = boardInfos.get(i);
                        PigModel boardPigModel = this.createPigModel(boardInfoMaps, null, null);
                        pigModelList.add(boardPigModel);
                    }
                }
            }
            this.commonExecute(weanIds, commonInfos, pigModelList, null, null, pigEventHisModelList, pigpenIdsList, null, updateToworkModelList,
                    updateChangeHouseModelList, null, diePigModels, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销寄养
    private void cancelFosterCare(Map<String, Object> commonInfos) throws Exception {

        // 获得寄出母猪的信息
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        // 获得寄入母猪的信息
        List<Map<String, Object>> boardInfos = this.getBoardInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] fosterCareIds = new long[infos.size()];

            List<PigModel> updatePigModelList = new ArrayList<PigModel>();

            List<PigModel> pigpenIdsList = new ArrayList<>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条CHANGE_HOUSE的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT
            List<ChangeHouseModel> updateChangeHouseModelList = new ArrayList<ChangeHouseModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                fosterCareIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                updatePigModelList.add(pigModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            // 判断猪栏是否还原为NULL
            Long flag = -99L;

            List<Map<String, Object>> childPigMapList = this.createChildPigModel(commonInfos);
            for (Map<String, Object> childPigMap : childPigMapList) {

                if (!Maps.isEmpty(childPigMap, "pigpenId")) {
                    if (flag.compareTo(Maps.getLong(childPigMap, "pigpenId")) == 0) {
                        childPigMap.remove("pigpenId");
                        PigModel pigpenIdsPigModel = new PigModel();
                        pigpenIdsPigModel.setRowId(Maps.getLong(childPigMap, "rowId"));
                        pigpenIdsList.add(pigpenIdsPigModel);
                    }
                }

                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                this.swineryIsCloseCheck(Maps.getLong(childPigMap, "swineryId"));

                PigModel childPigModel = getBean(PigModel.class, childPigMap);
                updatePigModelList.add(childPigModel);

                ChangeHouseModel changeHouseModel = new ChangeHouseModel();
                changeHouseModel.setPigId(Maps.getLong(childPigMap, "pigId"));
                changeHouseModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateChangeHouseModelList.add(changeHouseModel);

                PigEventHisModel childPigEventHisModel = this.createPigEventHisModel(childPigMap, commonInfos, "pp_l_bill_foster_detail");
                pigEventHisModelList.add(childPigEventHisModel);
            }

            // 添加寄入母猪到List中
            if (!Maps.isEmpty(commonInfos, "pigId")) {
                // 猪只撤销
                if (boardInfos != null && !boardInfos.isEmpty()) {
                    // 特殊点：同一单据中，一个母猪被多次寄养
                    // 猪只撤销只有一条数据
                    Map<String, Object> boardInfoMaps = boardInfos.get(0);
                    PigModel boardPigModel = this.createPigModel(boardInfoMaps, null, null);
                    pigMapper.update(boardPigModel);
                    Map<String, Object> deletesByConMap = new HashMap<String, Object>();
                    deletesByConMap.put("condition", " AND ROW_ID = " + Maps.getLong(boardInfoMaps, "relatesId"));
                    deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));
                    pigEventRelatesMapper.deletesByCon2(deletesByConMap);
                }
            } else {
                // 单据撤销
                if (boardInfos != null && !boardInfos.isEmpty()) {
                    for (int i = 0; i < boardInfos.size(); i++) {
                        Map<String, Object> boardInfoMaps = boardInfos.get(i);
                        PigModel boardPigModel = this.createPigModel(boardInfoMaps, null, null);
                        updatePigModelList.add(boardPigModel);
                    }
                }
            }

            this.commonExecute(fosterCareIds, commonInfos, updatePigModelList, null, null, pigEventHisModelList, pigpenIdsList, null, null,
                    updateChangeHouseModelList, null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 撤销背膘测试
    private void cancelBackfat(Map<String, Object> commonInfos) throws Exception {

        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] backfatIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                backfatIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            this.commonExecute(backfatIds, commonInfos, pigModelList, null, null, pigEventHisModelList, null, null, null, null, null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 采精
    private void cancelSemen(Map<String, Object> commonInfos) throws Exception {
        Long billId = Maps.getLongClass(commonInfos, "billId");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        boolean cancelBillFlag = Maps.getBoolean(commonInfos, "cancelBillFlag");

        // 查询是否存在精液已经被使用过，使用过，无法撤销
        SqlCon spermInfoUsedCheckSqlCon = new SqlCon();
        spermInfoUsedCheckSqlCon.addMainSql("SELECT T2.EAR_BRAND AS earBrand");
        spermInfoUsedCheckSqlCon.addMainSql(",CONCAT(T4.SEMEN_BATCH_NO,'-',(T3.BUSINESS_CODE + 1)) AS spermBatchNo");
        spermInfoUsedCheckSqlCon.addMainSql(" FROM PP_L_PIG T1");
        spermInfoUsedCheckSqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T2");
        spermInfoUsedCheckSqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.EAR_CODE_ID = T2.ROW_ID)");
        spermInfoUsedCheckSqlCon.addMainSql(" INNER JOIN PP_L_SPERM_INFO T3");
        spermInfoUsedCheckSqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T1.ROW_ID = T3.SOW_PIG_ID");
        spermInfoUsedCheckSqlCon.addCondition(SpermConstants.SPERM_INFO_STATUS_USE, " AND T3.STATUS = ?)");
        spermInfoUsedCheckSqlCon.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T4");
        spermInfoUsedCheckSqlCon.addMainSql(" ON(T4.DELETED_FLAG = '0' AND T3.SEMEN_ID = T4.ROW_ID)");
        spermInfoUsedCheckSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        spermInfoUsedCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        spermInfoUsedCheckSqlCon.addCondition(billId, " AND T3.BILL_ID = ?");
        spermInfoUsedCheckSqlCon.addCondition(pigId, " AND T4.PIG_ID = ?");
        spermInfoUsedCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> spermInfoUsedCheckSqlMap = new HashMap<String, String>();
        spermInfoUsedCheckSqlMap.put("sql", spermInfoUsedCheckSqlCon.getCondition());

        List<Map<String, Object>> spermInfoUsedCheckInfos = paramMapper.getObjectInfos(spermInfoUsedCheckSqlMap);

        if (spermInfoUsedCheckInfos != null && !spermInfoUsedCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.SPERM_HAS_BEEN_USED, Maps.getString(spermInfoUsedCheckInfos.get(0), "spermBatchNo"), Maps
                    .getString(spermInfoUsedCheckInfos.get(0), "earBrand"));
        }

        // 采精有精液销售不能撤销START
        SqlCon semenToSaleCherkSql = new SqlCon();
        semenToSaleCherkSql.addMainSql(" SELECT T2.SEMEN_BATCH_NO AS semenBatchNo FROM PP_L_SPERM_INFO T1");
        semenToSaleCherkSql.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T2");
        semenToSaleCherkSql.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.SEMEN_ID = T2.ROW_ID)");
        semenToSaleCherkSql.addMainSql(" WHERE T1.DELETED_FLAG='0'");
        semenToSaleCherkSql.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        semenToSaleCherkSql.addCondition(SpermConstants.SPERM_INFO_STATUS_SALE, " AND T1.STATUS = ?");
        semenToSaleCherkSql.addCondition(SpermConstants.SPERM_INFO_IS_SALE_SALE, " AND T1.IS_SALE = ?");
        semenToSaleCherkSql.addCondition(billId, " AND T1.BILL_ID = ?");
        semenToSaleCherkSql.addCondition(pigId, " AND T2.PIG_ID = ?");
        semenToSaleCherkSql.addMainSql(" LIMIT 1");
        Map<String, String> semenToSaleCherkSqlSqlMap = new HashMap<String, String>();
        semenToSaleCherkSqlSqlMap.put("sql", semenToSaleCherkSql.getCondition());
        List<Map<String, Object>> semenToSaleCherkSqlInfos = paramMapper.getObjectInfos(semenToSaleCherkSqlSqlMap);
        if (semenToSaleCherkSqlInfos != null && !semenToSaleCherkSqlInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.SEMEN_SALE_ERROR, Maps.getString(semenToSaleCherkSqlInfos.get(0), "semenBatchNo"));
        }
        // 采精有精液销售不能撤销
        List<Map<String, Object>> pigInfos = this.getMainInfos(commonInfos);

        if (pigInfos != null && !pigInfos.isEmpty()) {

            List<SemenModel> cancelsemenModelList = new ArrayList<SemenModel>();

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            for (int i = 0; i < pigInfos.size(); i++) {
                Map<String, Object> pigInfoMap = pigInfos.get(i);

                SemenModel semenModel = new SemenModel();
                semenModel.setRowId(Maps.getLong(pigInfoMap, "mainTableId"));
                semenModel.setDeletedBillId(Maps.getLongClass(commonInfos, "cancelEventBillId"));
                cancelsemenModelList.add(semenModel);

                PigModel pigModel = this.createPigModel(pigInfoMap, null, null);
                pigModelList.add(pigModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(pigInfoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);
            }

            List<Map<String, Object>> infos = getSemenInfos(pigId, billId);

            if (infos != null && !infos.isEmpty()) {

                List<SpermInfoModel> cancelSpermInfoModelList = new ArrayList<SpermInfoModel>();

                for (int i = 0; i < infos.size(); i++) {
                    Map<String, Object> infoMap = infos.get(i);
                    SpermInfoModel spermInfoModel = new SpermInfoModel();
                    spermInfoModel.setRowId(Maps.getLong(infoMap, "spermInfoId"));
                    cancelSpermInfoModelList.add(spermInfoModel);
                }

                pigMapper.updates(pigModelList);

                if (!cancelsemenModelList.isEmpty()) {
                    semenMapper.deletesForCancel(cancelsemenModelList);
                }

                if (!cancelSpermInfoModelList.isEmpty()) {
                    spermInfoMapper.deletesForCancel(cancelSpermInfoModelList);
                }

                SqlCon deletesByConSqlCon = new SqlCon();
                deletesByConSqlCon.addCondition(billId, " AND BILL_ID = ?");
                deletesByConSqlCon.addCondition(pigId, " AND PIG_ID = ?");

                Map<String, Object> deletesByConMap = new HashMap<String, Object>();
                deletesByConMap.put("condition", deletesByConSqlCon.getCondition());
                deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));

                if (cancelBillFlag) {
                    billMapper.deleteForCancel(billId);
                }
                pigEventRelatesMapper.deletesByCon2(deletesByConMap);

                pigEventHisMapper.inserts(pigEventHisModelList);

            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 种猪淘汰申请撤销
    private void cancelObsolete(Map<String, Object> commonInfos) throws Exception {
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if ((infos != null && !infos.isEmpty())) {

            long[] boarObsoleteIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                boarObsoleteIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModelList.add(pigModel);

                // PIG_CLASS 发生改变
                if (!Maps.getString(infoMap, "cancelPigClass").equals(Maps.getString(infoMap, "pigClass"))) {
                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                    toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                    updateToworkModelList.add(toworkModel);
                }

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }

            this.commonExecute(boarObsoleteIds, commonInfos, pigModelList, null, null, pigEventHisModelList, null, null, updateToworkModelList, null,
                    null, null, null);
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }
    }

    // 猪只销售
    private void cancelGoodPigSell(Map<String, Object> commonInfos) throws Exception {
        List<Map<String, Object>> infos = this.getMainInfos(commonInfos);

        if (infos != null && !infos.isEmpty()) {

            long[] leaveIds = new long[infos.size()];

            List<PigModel> pigModelList = new ArrayList<PigModel>();

            // 死去的猪只，还原LEAVE_DATE为NULL
            List<PigModel> diePigModelList = new ArrayList<PigModel>();

            List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();

            // 还原上一条TO_WORK的TOWORK_DATE_OUT和PIG_CLASS_OUT
            List<ToworkModel> updateToworkModelList = new ArrayList<ToworkModel>();

            for (int i = 0; i < infos.size(); i++) {
                Map<String, Object> infoMap = infos.get(i);
                leaveIds[i] = Maps.getLong(infoMap, "mainTableId");

                PigModel pigModel = this.createPigModel(infoMap, null, null);
                pigModel.setSex(Maps.getString(infoMap, "sex"));
                pigModelList.add(pigModel);
                diePigModelList.add(pigModel);

                // 如果是种猪 还原淘汰表
                if (!PigConstants.PIG_TYPE_PORK.equals(pigModel.getPigType())) {
                    SqlCon boarObsoleteSql = new SqlCon();
                    boarObsoleteSql.addMainSql(" UPDATE PP_L_BILL_BOAR_OBSOLETE");
                    boarObsoleteSql.addCondition(PigConstants.SATAUS_OBSOLETE_SUCCESS, " SET STATUS = ?");
                    boarObsoleteSql.addCondition(getFarmId(), " WHERE DELETED_FLAG = '0' AND FARM_ID = ? ");
                    boarObsoleteSql.addCondition(pigModel.getLastBillId(), " AND BILL_ID = ? ");
                    boarObsoleteSql.addCondition(pigModel.getRowId(), " AND PIG_ID = ? ");
                    setSql(boarObsoleteMapper, boarObsoleteSql);
                }

                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setPigId(Maps.getLong(infoMap, "pigId"));
                toworkModel.setBillId(Maps.getLong(commonInfos, "billId"));
                updateToworkModelList.add(toworkModel);

                PigEventHisModel pigEventHisModel = this.createPigEventHisModel(infoMap, commonInfos);
                pigEventHisModelList.add(pigEventHisModel);

            }
            // 判断该销售单据 是否已经做过入场
            SqlCon leaveSql = new SqlCon();
            leaveSql.addMainSql("SELECT COUNT(1) AS rowId FROM pp_l_bill_leave");
            leaveSql.addMainSql(" WHERE DELETED_FLAG = 0 AND IS_MOVE_IN = 'Y'");
            leaveSql.addCondition(StringUtil.arrayToString(leaveIds), " AND ROW_ID IN ?", false, true);
            LeaveModel leaveModel = setSql(leaveMapper, leaveSql).get(0);
            if (leaveModel.getRowId() > 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该销售单据的猪已经在客户场做过入场，无法撤销！");
            }

            // 已经结算的销售单 不可以撤销
            SqlCon saleSql = new SqlCon();
            saleSql.addMainSql("SELECT COUNT(1) as rowId FROM pp_l_bill_pig_sale");
            saleSql.addMainSql(" WHERE DELETED_FLAG = 0");
            saleSql.addCondition(AccountConstants.SALE_STATUS_IS_ACCOUNT_TRUE, " AND SALE_STATUS = ?");
            saleSql.addCondition(Maps.getLong(commonInfos, "billId"), " AND BILL_ID = ?");
            PigSaleModel saleModel = setSql(pigSaleMapper, saleSql).get(0);
            if (saleModel.getRowId() > 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该销售单据已经做过结算，无法撤销！");
            }

            this.commonExecute(leaveIds, commonInfos, pigModelList, null, null, pigEventHisModelList, null, null, updateToworkModelList, null, null,
                    diePigModelList, null);

            // HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            if (!isToHana) {
                return;
            }
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT b.BUSINESS_CODE businessCode,s.CUSTOMER_ID customerId,d.sale_describe saleDescribe");
            sqlCon.addMainSql(" FROM pp_m_bill b");
            sqlCon.addMainSql(" INNER JOIN pp_l_bill_pig_sale s");
            sqlCon.addMainSql(" ON b.ROW_ID = s.BILL_ID");
            sqlCon.addMainSql(" inner join pp_l_bill_pig_sale_detail d");
            sqlCon.addMainSql(" ON s.bill_id = d.bill_id");
            sqlCon.addMainSql(" WHERE s.SAP_SALE_TYPE IN (2,4)");
            sqlCon.addCondition(getFarmId(), " AND b.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(commonInfos, "billId"), " AND b.ROW_ID = ?");
            // 销售撤销只需要撤销不需要结算的销售单
            sqlCon.addCondition(AccountConstants.SALE_STATUS_NOT_ACCOUNT, " AND s.SALE_STATUS = ?");
            sqlCon.addMainSql(" GROUP BY d.sale_describe");

            Map<String, String> sqlMap = new HashMap<>();
            sqlMap.put("sql", sqlCon.getCondition());
            List<Map<String, Object>> saleList = paramMapper.getObjectInfos(sqlMap);
            // 销售撤销，只传外销和自宰，内销在入场做
            if (saleList.isEmpty()) {
                return;
            }

            Map<String, Object> saleMap = saleList.get(0);
            long customerId = Maps.getLong(saleMap, "customerId");
            String businessCode = Maps.getString(saleMap, "businessCode");

            boolean goodFlag = false;
            boolean scFlag = false;

            for (Map<String, Object> map : saleList) {
                if (PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(Maps.getString(map, "saleDescribe"))
                        || PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG.equals(Maps.getString(map, "saleDescribe"))) {
                    scFlag = true;
                } else {
                    goodFlag = true;
                }

                if (scFlag && goodFlag) {
                    break;
                }
            }

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

            String sapSaleType = HanaUtil.getSapSaleType(getFarmId(), customerId);
            boolean isSaleHana = HanaUtil.isSaleToHana(sapSaleType);

            // boolean isSaleAgain = HanaUtil.isSaleToAgain(sapSaleType);

            Date currentDate = new Date();

            List<MTC_ITFC> mtclist = new ArrayList<>();

            // START HANA
            if (!isSaleHana) {
                return;
            }

            if (goodFlag) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                // 业务代码:猪只销售(实时)
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2100);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(mtcBranchID + "-" + Maps.getString(commonInfos, "billId") + "-" + businessCode);
                // 原始单据编号
                mtcITFC.setMTC_BaseEntry(mtcBranchID + "-" + Maps.getString(commonInfos, "billId") + "-" + businessCode);
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                // JSON文件
                HanaSaleBill hanaSaleBill = new HanaSaleBill();
                hanaSaleBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                mtclist.add(mtcITFC);
            }
            if (scFlag) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
                // 业务代码:生产猪淘汰销售
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(mtcBranchID + "-" + Maps.getString(commonInfos, "billId") + "-" + businessCode);
                // 原始单据编号
                mtcITFC.setMTC_BaseEntry(mtcBranchID + "-" + Maps.getString(commonInfos, "billId") + "-" + businessCode);
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                // JSON文件
                HanaSaleBill hanaSaleBill = new HanaSaleBill();
                hanaSaleBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                mtclist.add(mtcITFC);
            }

            // 若有生产猪，则还需

            // 若isSaleAgain为true,则需要传两张单据到hana
            // if (isSaleAgain) {
            // MTC_ITFC mtcITFCAgain = new MTC_ITFC();
            // // 分公司编号
            // String cusBranchId = HanaUtil.getMTC_SaleCardCode(customerId);
            // mtcITFCAgain.setMTC_Branch(cusBranchId);
            // // 业务日期
            // mtcITFCAgain.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
            // // 业务代码:猪只采购(实时)
            // mtcITFCAgain.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
            // // 新农+单据编号
            // mtcITFCAgain.setMTC_DocNum(cusBranchId + "-" + Maps.getString(commonInfos, "billId") + "-" + businessCode);
            // // 原始单据编号
            // mtcITFCAgain.setMTC_BaseEntry(cusBranchId + "-" + Maps.getString(commonInfos, "billId") + "-" + businessCode);
            // // 优先级
            // mtcITFCAgain.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // // 处理区分
            // mtcITFCAgain.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
            // // 创建日期
            // mtcITFCAgain.setMTC_CreateTime(currentDate);
            // // 同步状态
            // mtcITFCAgain.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // // JSON文件
            // HanaSaleBill hanaSaleBillAgain = new HanaSaleBill();
            // hanaSaleBillAgain.setMTC_BaseEntry(mtcITFCAgain.getMTC_BaseEntry());
            // String jsonDataFileAgain = HanaJacksonUtil.objectToJackson(hanaSaleBillAgain);
            // mtcITFCAgain.setMTC_DataFile(jsonDataFileAgain);
            // mtclist.add(mtcITFCAgain);
            // }

            hanaCommonService.insertsMTC_ITFC(mtclist);
            // END HANA
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

    }

    // 判断这个猪只是不是这个单据唯一一只存在的猪，如果是撤销猪只事件时同时撤销单据
    private boolean lastPigInBillCheck(Map<String, Object> commonInfos) throws Exception {
        String eventCode = Maps.getString(commonInfos, "eventCode");
        if (EventConstants.FOSTER.equals(eventCode)) {
            // 选择的是这个事件的寄入母猪
            // 如果只有一个寄出母猪，改pigId改为寄出母猪
            // 如果有几个寄出母猪,提示请选择寄出母猪
            this.boardPigToOldBoardPig(commonInfos);
        }
        if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            this.lastPigIsNurse(commonInfos);
        }

        Long billId = Maps.getLongClass(commonInfos, "billId");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        List<String> pigIds = Maps.getList(commonInfos, "pigIds");

        SqlCon lastPigInBillCheckSqlCon = new SqlCon();
        lastPigInBillCheckSqlCon.addCondition(billId, " AND BILL_ID = ?");
        if (!EventConstants.CHANGE_EAR_BAND.equals(eventCode) && !EventConstants.PIG_MOVE_IN.equals(eventCode) && !EventConstants.GOOD_PIG_DIE.equals(
                eventCode) && !EventConstants.TO_CHILD_CARE.equals(eventCode) && !EventConstants.TO_CHILD_FATTEN.equals(eventCode)) {
            lastPigInBillCheckSqlCon.addMainSql(" AND PIG_TYPE <> 3");
        }
        if (pigId != null) {
            if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
                // 奶妈转舍比较特殊，一条记录有多个母猪
                SqlCon nurseSql = new SqlCon();
                nurseSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                nurseSql.addCondition(pigId, " AND PIG_ID = ?");
                NurseModel nurseModel = getModel(nurseMapper, nurseSql);
                List<Long> idsList = new ArrayList<Long>();
                idsList.add(pigId);
                if (nurseModel.getInPigId() != null) {
                    idsList.add(nurseModel.getInPigId());
                }
                if (nurseModel.getBoardSowId() != null) {
                    idsList.add(nurseModel.getBoardSowId());
                }
                lastPigInBillCheckSqlCon.addCondition(StringUtil.listLongToString(idsList), " AND PIG_ID NOT IN ?", false, true);
            } else {
                lastPigInBillCheckSqlCon.addCondition(pigId, " AND PIG_ID <> ?");
            }
            if (EventConstants.FOSTER.equals(eventCode)) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
                sqlCon.addCondition(billId, " AND BILL_ID = ?");
                sqlCon.addCondition(pigId, " AND PIG_ID = ?");
                Map<String, String> sqlMap = new HashMap<String, String>();
                sqlMap.put("condition", sqlCon.getCondition());
                List<FosterCareModel> fosterCareModelList = fosterCareMapper.searchListByCon(sqlMap);
                lastPigInBillCheckSqlCon.addCondition(fosterCareModelList.get(0).getBoardSowId(), " AND PIG_ID <> ?");
            }
        } else if (pigIds != null && !pigIds.isEmpty()) {
            lastPigInBillCheckSqlCon.addMainSql(" AND PIG_ID NOT IN (");
            for (int i = 0; i < pigIds.size(); i++) {
                lastPigInBillCheckSqlCon.addMainSql(pigIds.get(i));
                if (i != pigIds.size() - 1) {
                    lastPigInBillCheckSqlCon.addMainSql(",");
                }
            }
            lastPigInBillCheckSqlCon.addMainSql(")");
        }
        lastPigInBillCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> lastPigInBillCheckMap = new HashMap<String, String>();
        lastPigInBillCheckMap.put("condition", lastPigInBillCheckSqlCon.getCondition());

        List<PigEventRelatesModel> pigEventRelatesList = pigEventRelatesMapper.searchListByCon(lastPigInBillCheckMap);

        if (pigEventRelatesList != null && !pigEventRelatesList.isEmpty()) {
            // 存在其他猪只，不能撤销单据
            return false;
        }
        // 不存在其他猪只，同时撤销单据
        return true;
    }

    // CHECK这个事件是否是猪只的最后一个事件
    private void lastEventCheck(Map<String, Object> commonInfos) throws Exception {
        if (Maps.isEmpty(commonInfos, "billId")) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "单据号（billId）不能为空！。。。");
        }
        Long billId = Maps.getLong(commonInfos, "billId");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        List<String> pigIds = Maps.getList(commonInfos, "pigIds");

        SqlCon lastEventCheckSqlCon = new SqlCon();
        lastEventCheckSqlCon.addMainSql(
                "SELECT (SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE DELETED_FLAG = '0' AND ROW_ID = T2.EAR_CODE_ID) AS earBrand ");
        lastEventCheckSqlCon.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1 ");
        lastEventCheckSqlCon.addMainSql(" INNER JOIN PP_L_PIG T2 ");
        lastEventCheckSqlCon.addMainSql(" ON( T2.ROW_ID = T1.PIG_ID ");
        lastEventCheckSqlCon.addMainSql(" AND T2.FARM_ID = T1.FARM_ID");
        lastEventCheckSqlCon.addMainSql(" AND T2.LAST_BILL_ID <> T1.BILL_ID ");
        lastEventCheckSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        lastEventCheckSqlCon.addMainSql(" AND T1.DELETED_FLAG = '0'");
        lastEventCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        lastEventCheckSqlCon.addCondition(billId, " AND T1.BILL_ID = ?");
        if (pigId != null) {
            lastEventCheckSqlCon.addCondition(pigId, " AND T1.PIG_ID = ?");
        } else if (pigIds != null && !pigIds.isEmpty()) {
            lastEventCheckSqlCon.addMainSql(" AND T1.PIG_ID IN (");
            for (int i = 0; i < pigIds.size(); i++) {
                lastEventCheckSqlCon.addMainSql(pigIds.get(i));
                if (i != pigIds.size() - 1) {
                    lastEventCheckSqlCon.addMainSql(",");
                }
            }
            lastEventCheckSqlCon.addMainSql(")");
        }
        lastEventCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> lastEventCheckSqlMap = new HashMap<String, String>();
        lastEventCheckSqlMap.put("sql", lastEventCheckSqlCon.getCondition());

        List<Map<String, Object>> lastEventCheckInfos = paramMapper.getObjectInfos(lastEventCheckSqlMap);

        if (lastEventCheckInfos != null && !lastEventCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOT_LAST_EVENT_ERROR, Maps.getString(lastEventCheckInfos.get(0), "earBrand"));
        }

    }

    // CHECK是否修改过耳号，如果修改过耳号，过去的耳号是否被其他猪只所使用
    private void earBrandCheck(Map<String, Object> commonInfos) throws Exception {
        SqlCon earBrandCheckSqlCon = new SqlCon();
        earBrandCheckSqlCon.addMainSql("SELECT T3.earBrand, T3.lastEarBrand FROM");
        earBrandCheckSqlCon.addMainSql(
                " (SELECT T1.FARM_ID AS farmId, T1.EAR_BRAND AS earBrand, T2.EAR_BRAND AS lastEarBrand FROM PP_L_PIG_EVENT_RELATES T1");
        earBrandCheckSqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T2");
        earBrandCheckSqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID");
        earBrandCheckSqlCon.addMainSql(" AND T1.PIG_ID = T2.PIG_ID");
        earBrandCheckSqlCon.addMainSql(" AND T1.LAST_BILL_ID = T2.BILL_ID");
        earBrandCheckSqlCon.addMainSql(" AND T1.EAR_BRAND <> T2.EAR_BRAND");
        earBrandCheckSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        earBrandCheckSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        earBrandCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        earBrandCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
        earBrandCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND T1.PIG_ID = ?");
        earBrandCheckSqlCon.addMainSql(" GROUP BY T1.ROW_ID ) T3");
        earBrandCheckSqlCon.addMainSql(" WHERE EXISTS (SELECT 1 FROM PP_L_EAR_CODE");
        earBrandCheckSqlCon.addMainSql(" WHERE FARM_ID = T3.farmId AND DELETED_FLAG = '0' AND");
        earBrandCheckSqlCon.addMainSql(" T3.lastEarBrand IS NOT NULL AND T3.lastEarBrand <> '' AND EAR_BRAND = T3.lastEarBrand LIMIT 1)");
        earBrandCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> earBrandCheckSqlMap = new HashMap<String, String>();
        earBrandCheckSqlMap.put("sql", earBrandCheckSqlCon.getCondition());

        List<Map<String, Object>> earBrandCheckInfos = paramMapper.getObjectInfos(earBrandCheckSqlMap);

        if (earBrandCheckInfos != null && !earBrandCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.LAST_EAR_BRAND_HAS_BEEN_USED, Maps.getString(earBrandCheckInfos.get(0), "earBrand"), Maps
                    .getString(earBrandCheckInfos.get(0), "lastEarBrand"));
        }

    }

    // CHECK原来的猪舍是否有容量装下还原的猪只数量
    private void houseCapacityCheck(Map<String, Object> commonInfos) throws Exception {

        // 是否允许超出猪舍容量
        String yxcczsrlFlag = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "YXCCZSRL");

        // 不允许超出猪舍容量
        if ("OFF".equalsIgnoreCase(yxcczsrlFlag)) {
            SqlCon houseCapacityCheckSqlCon = new SqlCon();
            houseCapacityCheckSqlCon.addMainSql("SELECT (SELECT HOUSE_NAME FROM PP_O_HOUSE WHERE ROW_ID = T3.houseId) AS houseName FROM");
            houseCapacityCheckSqlCon.addMainSql(" (SELECT T2.HOUSE_ID AS houseId,COUNT(T2.HOUSE_ID) AS houseCount FROM");
            houseCapacityCheckSqlCon.addMainSql(" PP_L_PIG_EVENT_RELATES T1 INNER JOIN PP_L_PIG_EVENT_RELATES T2");
            houseCapacityCheckSqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID");
            houseCapacityCheckSqlCon.addMainSql(" AND T1.PIG_ID = T2.PIG_ID");
            houseCapacityCheckSqlCon.addMainSql(" AND T1.LAST_BILL_ID = T2.BILL_ID");
            houseCapacityCheckSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
            houseCapacityCheckSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
            houseCapacityCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
            houseCapacityCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
            houseCapacityCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND T1.PIG_ID = ?");
            houseCapacityCheckSqlCon.addMainSql(" GROUP BY T2.HOUSE_ID ) T3");
            houseCapacityCheckSqlCon.addMainSql(" WHERE EXISTS ( SELECT 1 FROM");
            houseCapacityCheckSqlCon.addMainSql(" (SELECT T4.houseId,T4.totalHouseCount - T5.currentHouseCount AS restPartCount FROM");
            houseCapacityCheckSqlCon.addMainSql(" (SELECT HOUSE_ID AS houseId, SUM(PIG_NUM) AS totalHouseCount FROM PP_O_PIGPEN");
            houseCapacityCheckSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            houseCapacityCheckSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            houseCapacityCheckSqlCon.addMainSql(" GROUP BY HOUSE_ID");
            houseCapacityCheckSqlCon.addMainSql(" )T4 INNER JOIN");
            houseCapacityCheckSqlCon.addMainSql(" (SELECT HOUSE_ID AS houseId,COUNT(HOUSE_ID) AS currentHouseCount FROM PP_L_PIG");
            houseCapacityCheckSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND PIG_CLASS <=18");
            houseCapacityCheckSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            houseCapacityCheckSqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND ROW_ID = ?");
            houseCapacityCheckSqlCon.addMainSql(" GROUP BY HOUSE_ID) T5");
            houseCapacityCheckSqlCon.addMainSql(" ON T4.houseId = T5.houseId ) T6");
            houseCapacityCheckSqlCon.addMainSql(" WHERE T3.houseId = T6.houseId AND T6.restPartCount - T3.houseCount < 0");
            houseCapacityCheckSqlCon.addMainSql(" LIMIT 1 ) LIMIT 1");

            Map<String, String> houseCapacityCheckSqlMap = new HashMap<String, String>();
            houseCapacityCheckSqlMap.put("sql", houseCapacityCheckSqlCon.getCondition());

            List<Map<String, Object>> houseCapacityCheckInfos = paramMapper.getObjectInfos(houseCapacityCheckSqlMap);

            if (houseCapacityCheckInfos != null && !houseCapacityCheckInfos.isEmpty()) {
                Thrower.throwException(BaseBusiException.LAST_HOUSE_CAPACITY_IS_NOT_ENOUGH, Maps.getString(houseCapacityCheckInfos.get(0),
                        "houseName"));
            }
        }

    }

    // 检查公猪有没有配种，配种后无法撤销后备转生产
    private void breedCheck(Map<String, Object> infoMap) throws Exception {
        Long boarPigId = Maps.getLong(infoMap, "pigId");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT PIG_ID AS pigId FROM pp_l_bill_breed WHERE DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(boarPigId, " AND (BREED_BOAR_FIRST = ?");
        sqlCon.addCondition(boarPigId, " OR BREED_BOAR_SECOND = ?");
        sqlCon.addCondition(boarPigId, " OR BREED_BOAR_THIRD = ?) LIMIT 1");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);

        if (infos != null && !infos.isEmpty()) {
            Map<String, Object> map = infos.get(0);
            Map<String, String> sowPigInfoMap = CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.PIG_INFO);
            Map<String, String> boarPigInfoMap = CacheUtil.getPigInfo(Maps.getString(infoMap, "pigId"), PigInfoEnum.PIG_INFO);
            Thrower.throwException(BaseBusiException.BOAR_HAS_BREED_WITH_SOW, Maps.getString(boarPigInfoMap, "earBrand"), Maps.getString(
                    sowPigInfoMap, "earBrand"));
        }

    }

    // 母猪撤销转舍，上一个舍是产房时，CHECK那个猪栏是否被母猪占用，一个产房猪栏只能有一个母猪
    private void deliveryHousePigpenHasOtherSowPigCheck(long pigId, long houseId, long pigpenId) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID FROM PP_L_PIG WHERE DELETED_FLAG = '0' AND PIG_CLASS BETWEEN 1 AND 18 AND PIG_TYPE = '2'");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(houseId, " AND HOUSE_ID = ?");
        sqlCon.addCondition(pigpenId, " AND PIGPEN_ID = ? LIMIT 1");
        List<PigModel> pigModelList = setSql(pigMapper, sqlCon);

        if (pigModelList != null && !pigModelList.isEmpty()) {
            Map<String, String> oldpigInfoMap = CacheUtil.getPigInfo(String.valueOf(pigId), PigInfoEnum.PIG_INFO);
            Map<String, String> pigInfoMap = CacheUtil.getPigInfo(String.valueOf(pigModelList.get(0).getRowId()), PigInfoEnum.PIG_INFO);
            String houseName = CacheUtil.getName(String.valueOf(houseId), NameEnum.HOUSE_NAME);
            String pigpenName = CacheUtil.getName(String.valueOf(pigpenId), NameEnum.PIGPEN_NAME);
            Thrower.throwException(BaseBusiException.CANCEL_TO_DELIVERY_HOUSE_PIGPEN_HAS_SOW_PIG, Maps.getString(oldpigInfoMap, "earBrand"),
                    houseName, pigpenName, Maps.getString(pigInfoMap, "earBrand"));
        }
    }

    // 选择的是这个事件的寄入母猪
    // 如果只有一个寄出母猪，改pigId改为寄出母猪
    // 如果有几个寄出母猪,提示请选择寄出母猪
    private void boardPigToOldBoardPig(Map<String, Object> commonInfos) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT PIG_ID AS pigId,(SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE DELETED_FLAG = '0' AND ROW_ID = T2.EAR_CODE_ID) AS earBrand");
        sqlCon.addMainSql(" FROM PP_L_BILL_FOSTER_CARE T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG T2");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND T1.PIG_ID = T2.ROW_ID");
        sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND T1.BOARD_SOW_ID = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);

        // 如果选择的猪只时代养母猪，将pigId改为原代养母猪
        if (infos != null && !infos.isEmpty()) {
            if (infos.size() == 1) {
                commonInfos.put("pigId", infos.get(0).get("pigId"));
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < infos.size(); i++) {
                    stringBuffer.append(Maps.getString(infos.get(i), "earBrand"));
                    if (i != infos.size() - 1) {
                        stringBuffer.append("，");
                    }
                }
                Thrower.throwException(BaseBusiException.MORE_THAN_ONE_OLD_BOARD_PIG, stringBuffer.toString());
            }
        }

    }

    // 获得生成的cancelEventBillId(billId为被撤销的单据)
    private long getCancelEventBillId(String eventName, Long billId, Date currentDate) throws Exception {
        // 业务编码
        BillModel billModel = new BillModel();
        String businessCode = ParamUtil.getBCode(eventName + "_CODE", getEmployeeId(), getCompanyId(), getFarmId());
        billModel.setStatus("1");
        billModel.setDeletedFlag("0");
        billModel.setOriginFlag("S");
        billModel.setOriginApp("XN1.0");
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode(eventName);
        billModel.setCreateId(getEmployeeId());
        billModel.setCreateDate(currentDate);
        // 创建表单
        billModel.setBillDate(currentDate);
        billModel.setCanceledBillId(billId);
        billMapper.insert(billModel);
        return billModel.getRowId();
    }

    // 获取猪仔信息
    private List<Map<String, Object>> createChildPigModel(Map<String, Object> commonInfos) throws Exception {
        String eventCode = Maps.getString(commonInfos, "eventCode");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        List<String> pigIds = Maps.getList(commonInfos, "pigIds");
        Long billId = Maps.getLong(commonInfos, "billId");
        return this.createChildPigModel(eventCode, pigId, pigIds, billId);
    }

    // 获取猪仔信息
    private List<Map<String, Object>> createChildPigModel(String eventCode, Long pigId, List<String> pigIds, Long billId) throws Exception {
        SqlCon childHadDoneOtherEventSqlCon = new SqlCon();
        if (EventConstants.WEAN.equals(eventCode)) {
            childHadDoneOtherEventSqlCon.addMainSql(
                    "SELECT (SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE DELETED_FLAG = '0' AND ROW_ID = T2.EAR_CODE_ID) AS earBrand ");
            childHadDoneOtherEventSqlCon.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1 ");
            childHadDoneOtherEventSqlCon.addMainSql(" INNER JOIN PP_L_PIG T2 ");
            childHadDoneOtherEventSqlCon.addMainSql(" ON( T2.ROW_ID = T1.PIG_ID ");
            childHadDoneOtherEventSqlCon.addMainSql(" AND T2.FARM_ID = T1.FARM_ID ");
            childHadDoneOtherEventSqlCon.addMainSql(" AND T2.LAST_BILL_ID <> T1.BILL_ID ");
            childHadDoneOtherEventSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0') ");
            childHadDoneOtherEventSqlCon.addMainSql(" AND T1.DELETED_FLAG = '0' ");
            childHadDoneOtherEventSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
            childHadDoneOtherEventSqlCon.addCondition(billId, " AND T1.BILL_ID = ? ");
            childHadDoneOtherEventSqlCon.addCondition(pigId, "  AND T1.PIG_ID = ? ");
            childHadDoneOtherEventSqlCon.addMainSql(" LIMIT 1");
        } else {
            childHadDoneOtherEventSqlCon.addMainSql("SELECT EAR_BRAND AS earBrand FROM PP_L_PIG_EVENT_RELATES WHERE DELETED_FLAG = '0'");
            childHadDoneOtherEventSqlCon.addCondition(billId, " AND  LAST_BILL_ID = ?");
            childHadDoneOtherEventSqlCon.addMainSql(" AND PIG_ID IN (SELECT B.PIG_ID FROM PP_L_PIG A INNER JOIN PP_L_PIG_EVENT_RELATES B");
            childHadDoneOtherEventSqlCon.addMainSql(" ON(B.DELETED_FLAG = '0' AND A.ROW_ID = B.PIG_ID");
            childHadDoneOtherEventSqlCon.addCondition(billId, " AND B.BILL_ID = ?)");
            childHadDoneOtherEventSqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");
            childHadDoneOtherEventSqlCon.addCondition(pigId, "  AND A.BOARD_SOW_ID = ?");
            childHadDoneOtherEventSqlCon.addMainSql(") LIMIT 1");
        }

        Map<String, String> childHadDoneOtherEventSqlMap = new HashMap<>();
        childHadDoneOtherEventSqlMap.put("sql", childHadDoneOtherEventSqlCon.getCondition());

        List<Map<String, Object>> childHadDoneOtherEventList = paramMapper.getObjectInfos(childHadDoneOtherEventSqlMap);

        if (childHadDoneOtherEventList != null && !childHadDoneOtherEventList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOT_LAST_EVENT_ERROR, Maps.getString(childHadDoneOtherEventList.get(0), "earBrand"));
        }

        SqlCon sqlCon = new SqlCon();
        if (EventConstants.DELIVERY.equals(eventCode)) {
            sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.EAR_CODE_ID AS earCodeId ");
        } else if (EventConstants.WEAN.equals(eventCode) || EventConstants.CHILD_PIG_DIE.equals(eventCode) || EventConstants.GOOD_PIG_DIE.equals(
                eventCode) || EventConstants.FOSTER.equals(eventCode) || EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)
                || EventConstants.BREED_PIG_DIE.equals(eventCode)) {
            sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, IFNULL(T1.SWINERY_ID,-99) AS cancelSwineryId");
            sqlCon.addMainSql(", T3.PIG_CLASS AS pigClass, T3.BILL_ID AS lastBillId, T3.EVENT_DATE AS lastEventDate");
            sqlCon.addMainSql(", T2.EVENT_DATE AS eventHisLastEventDate");
            sqlCon.addMainSql(", T3.PIG_ID AS pigId, T3.COMPANY_ID AS companyId, T3.FARM_ID AS farmId, T3.LINE_ID AS lineId");
            sqlCon.addMainSql(", IFNULL(T3.SWINERY_ID,-99) AS swineryId, T3.HOUSE_ID AS houseId, IFNULL(T3.PIGPEN_ID,-99) AS pigpenId");
            sqlCon.addMainSql(", T3.PIG_TYPE AS pigType, T3.SEX AS sex, T3.PARITY AS parity, T3.EAR_BRAND AS earBrand");
            sqlCon.addMainSql(", (SELECT B.TOWORK_DATE FROM PP_L_BILL_TOWORK A ");
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK B");
            sqlCon.addMainSql(" ON(A.FARM_ID = B.FARM_ID");
            sqlCon.addMainSql(" AND A.CHANGE_PIG_CLASS_ID = B.ROW_ID");
            sqlCon.addMainSql(" AND B.DELETED_FLAG = '0')");
            sqlCon.addMainSql(
                    " WHERE A.DELETED_FLAG = '0' AND A.FARM_ID = T1.FARM_ID AND A.PIG_ID = T1.ROW_ID AND A.BILL_ID = T1.LAST_BILL_ID ) AS lastClassDate");

            if (EventConstants.FOSTER.equals(eventCode) || EventConstants.WEAN.equals(eventCode)) {
                sqlCon.addMainSql(", T4.ROW_ID AS mainTableId, T4.LAST_SOW_ID AS boardSowId");
            }

            if (EventConstants.CHILD_PIG_DIE.equals(eventCode) || EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
                sqlCon.addMainSql(", T5.ROW_ID AS mainTableId");
            }

            if (EventConstants.WEAN.equals(eventCode) || EventConstants.BREED_PIG_DIE.equals(eventCode)) {
                sqlCon.addMainSql(", T6.ROW_ID AS weanMainTableId , E.ROW_ID AS leaveMainTableId");
            }

            if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)) {
                sqlCon.addMainSql(", T7.ROW_ID AS mainTableId");
            }

        }

        sqlCon.addMainSql(" FROM PP_L_PIG T1");

        if (!EventConstants.DELIVERY.equals(eventCode)) {
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T2");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T2.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T2.BILL_ID");
            sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T3");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T3.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T3.FARM_ID");
            sqlCon.addMainSql(" AND T2.LAST_BILL_ID = T3.BILL_ID");
            sqlCon.addMainSql(" AND T3.DELETED_FLAG = '0')");
        }

        if (EventConstants.WEAN.equals(eventCode)) {
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_FOSTER_DETAIL T4");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T4.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T4.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T4.BILL_ID");
            sqlCon.addCondition(pigId, " AND T4.LAST_SOW_ID = ?");
            sqlCon.addMainSql(" AND T4.DELETED_FLAG = '0')");
        }

        if (EventConstants.FOSTER.equals(eventCode)) {
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_FOSTER_DETAIL T4");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T4.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T4.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T4.BILL_ID");
            sqlCon.addCondition(pigId, " AND T4.LAST_SOW_ID = ?");
            sqlCon.addMainSql(" AND T4.DELETED_FLAG = '0')");
        }

        if (EventConstants.CHILD_PIG_DIE.equals(eventCode) || EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T5");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T5.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T5.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T5.BILL_ID");
            sqlCon.addMainSql(" AND T5.DELETED_FLAG = '0')");
        }

        if (EventConstants.WEAN.equals(eventCode) || EventConstants.BREED_PIG_DIE.equals(eventCode)) {
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_WEAN_DETAIL T6");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T6.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T6.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T6.BILL_ID");
            sqlCon.addMainSql(" AND T6.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_LEAVE E");
            sqlCon.addMainSql(" ON(T1.ROW_ID = E.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = E.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = E.BILL_ID");
            sqlCon.addMainSql(" AND E.DELETED_FLAG = 0)");
        }

        if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)) {
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_HOUSE T7");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T7.PIG_ID");
            sqlCon.addMainSql(" AND T1.FARM_ID = T7.FARM_ID");
            sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T7.BILL_ID");
            sqlCon.addMainSql(" AND T7.DELETED_FLAG = '0')");
        }
        sqlCon.addMainSql(" WHERE T1.PIG_TYPE = '3' AND T1.DELETED_FLAG = '0'");
        if (EventConstants.DELIVERY.equals(eventCode) || EventConstants.WEAN.equals(eventCode) || EventConstants.CHILD_PIG_DIE.equals(eventCode)
                || EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)) {
            sqlCon.addCondition(pigId, " AND T1.BOARD_SOW_ID = ?");

            if (EventConstants.DELIVERY.equals(eventCode)) {
                sqlCon.addCondition(billId, " AND T1.BILL_ID = ?");
            }

        }
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND T1.LAST_BILL_ID = ?");

        if ("GOOD_PIG_DIE".equals(eventCode)) {
            if (pigIds != null && !pigIds.isEmpty()) {
                sqlCon.addMainSql(" AND T1.ROW_ID IN (");
                for (int i = 0; i < pigIds.size(); i++) {
                    sqlCon.addMainSql(pigIds.get(i));
                    if (i != pigIds.size() - 1) {
                        sqlCon.addMainSql(",");
                    }
                }
                sqlCon.addMainSql(")");
            }
        }

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    // 获取奶妈转舍猪仔信息
    private List<Map<String, Object>> createNurseChildPigModel(Map<String, Object> commonInfos) throws Exception {
        String eventCode = Maps.getString(commonInfos, "eventCode");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        Long inPigId = Maps.getLongClass(commonInfos, "inPigId");
        Long billId = Maps.getLong(commonInfos, "billId");

        SqlCon childHadDoneOtherEventSqlCon = new SqlCon();
        childHadDoneOtherEventSqlCon.addMainSql("SELECT EAR_BRAND AS earBrand FROM PP_L_PIG_EVENT_RELATES WHERE DELETED_FLAG = '0'");
        childHadDoneOtherEventSqlCon.addCondition(billId, " AND  LAST_BILL_ID = ?");
        childHadDoneOtherEventSqlCon.addMainSql(" AND PIG_ID IN (SELECT B.PIG_ID FROM PP_L_PIG A INNER JOIN PP_L_PIG_EVENT_RELATES B");
        childHadDoneOtherEventSqlCon.addMainSql(" ON(B.DELETED_FLAG = '0' AND A.ROW_ID = B.PIG_ID");
        childHadDoneOtherEventSqlCon.addCondition(billId, " AND B.BILL_ID = ?)");
        childHadDoneOtherEventSqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");
        childHadDoneOtherEventSqlCon.addCondition(pigId, "  AND A.BOARD_SOW_ID = ?");
        childHadDoneOtherEventSqlCon.addMainSql(") LIMIT 1");

        Map<String, String> childHadDoneOtherEventSqlMap = new HashMap<>();
        childHadDoneOtherEventSqlMap.put("sql", childHadDoneOtherEventSqlCon.getCondition());

        List<Map<String, Object>> childHadDoneOtherEventList = paramMapper.getObjectInfos(childHadDoneOtherEventSqlMap);

        if (childHadDoneOtherEventList != null && !childHadDoneOtherEventList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NOT_LAST_EVENT_ERROR, Maps.getString(childHadDoneOtherEventList.get(0), "earBrand"));
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT T1.ROW_ID AS rowId, T3.PIG_CLASS AS pigClass, T3.BILL_ID AS lastBillId, T3.EVENT_DATE AS lastEventDate, T2.EVENT_DATE AS eventHisLastEventDate");
        sqlCon.addMainSql(", T3.PIG_ID AS pigId, T3.COMPANY_ID AS companyId, T3.FARM_ID AS farmId, T3.LINE_ID AS lineId");
        sqlCon.addMainSql(", IFNULL(T3.SWINERY_ID,-99) AS swineryId, T3.HOUSE_ID AS houseId, IFNULL(T3.PIGPEN_ID,-99) AS pigpenId");
        sqlCon.addMainSql(", T3.PIG_TYPE AS pigType, T3.SEX AS sex, T3.PARITY AS parity, T3.EAR_BRAND AS earBrand");
        sqlCon.addMainSql(", (SELECT B.TOWORK_DATE FROM PP_L_BILL_TOWORK A ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK B");
        sqlCon.addMainSql(" ON(A.FARM_ID = B.FARM_ID");
        sqlCon.addMainSql(" AND A.CHANGE_PIG_CLASS_ID = B.ROW_ID");
        sqlCon.addMainSql(" AND B.DELETED_FLAG = '0')");
        sqlCon.addMainSql(
                " WHERE A.DELETED_FLAG = '0' AND A.FARM_ID = T1.FARM_ID AND A.PIG_ID = T1.ROW_ID AND A.BILL_ID = T1.LAST_BILL_ID ORDER BY B.TOWORK_DATE DESC LIMIT 1) AS lastClassDate");
        sqlCon.addMainSql(", T4.ROW_ID AS fosterMainTableId, T4.LAST_SOW_ID AS boardSowId");
        sqlCon.addMainSql(", T5.ROW_ID AS leaveMainTableId");
        sqlCon.addMainSql(", T6.ROW_ID AS weanMainTableId");

        sqlCon.addMainSql(" FROM PP_L_PIG T1");

        sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T2");
        sqlCon.addMainSql(" ON(T1.ROW_ID = T2.PIG_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T2.BILL_ID");
        sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T3");
        sqlCon.addMainSql(" ON(T1.ROW_ID = T3.PIG_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T3.FARM_ID");
        sqlCon.addMainSql(" AND T2.LAST_BILL_ID = T3.BILL_ID");
        sqlCon.addMainSql(" AND T3.DELETED_FLAG = '0')");

        sqlCon.addMainSql(" LEFT OUTER JOIN PP_L_BILL_FOSTER_DETAIL T4");
        sqlCon.addMainSql(" ON(T1.ROW_ID = T4.PIG_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T4.FARM_ID");
        sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T4.BILL_ID");
        sqlCon.addMainSql(" AND T4.DELETED_FLAG = '0')");

        sqlCon.addMainSql(" LEFT OUTER JOIN PP_L_BILL_LEAVE T5");
        sqlCon.addMainSql(" ON(T1.ROW_ID = T5.PIG_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T5.FARM_ID");
        sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T5.BILL_ID");
        sqlCon.addMainSql(" AND T5.DELETED_FLAG = '0')");

        sqlCon.addMainSql(" LEFT OUTER JOIN PP_L_BILL_WEAN_DETAIL T6");
        sqlCon.addMainSql(" ON(T1.ROW_ID = T6.PIG_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T6.FARM_ID");
        sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T6.BILL_ID");
        sqlCon.addMainSql(" AND T6.DELETED_FLAG = '0')");

        sqlCon.addMainSql(" WHERE T1.PIG_TYPE = '3' AND T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND T1.LAST_BILL_ID = ?");
        if (inPigId == null) {
            sqlCon.addCondition(pigId, " AND (T4.LAST_SOW_ID = ?");
        } else {
            sqlCon.addCondition(pigId, " AND ((T4.LAST_SOW_ID = ?");
            sqlCon.addCondition(inPigId, " OR T4.LAST_SOW_ID = ?)");
        }
        sqlCon.addCondition(pigId, " OR T1.BOARD_SOW_ID = ?)");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    // 获取主要数据（除换耳号,入场,采精）
    private List<Map<String, Object>> getMainInfos(Map<String, Object> commonInfos) throws Exception {
        String eventCode = Maps.getString(commonInfos, "eventCode");
        String eventTable = Maps.getString(commonInfos, "eventTable");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        Long inPigId = Maps.getLongClass(commonInfos, "inPigId");
        List<String> pigIds = Maps.getList(commonInfos, "pigIds");

        // 猪群和栏位用-99来标识NULL
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT T2.PIG_ID AS pigId,DATE(T1.EVENT_DATE) eventDate,T2.COMPANY_ID AS companyId, T2.FARM_ID AS farmId, T2.LINE_ID AS lineId");
        sqlCon.addMainSql(", IFNULL(T2.SWINERY_ID,-99) AS swineryId, T2.HOUSE_ID AS houseId, IFNULL(T2.PIGPEN_ID,-99) AS pigpenId");
        sqlCon.addMainSql(", IFNULL(T1.SWINERY_ID,-99) AS cancelSwineryId");
        sqlCon.addMainSql(", T2.PIG_TYPE AS pigType, T2.SEX AS sex, T2.PARITY AS parity, T2.EAR_BRAND AS earBrand, T1.PIG_CLASS AS cancelPigClass");
        sqlCon.addMainSql(", T2.PIG_CLASS AS pigClass, T2.EVENT_DATE AS lastEventDate, T2.BILL_ID AS lastBillId, T3.ROW_ID AS mainTableId");
        sqlCon.addMainSql(", (SELECT B.TOWORK_DATE FROM PP_L_BILL_TOWORK A ");
        sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK B");
        sqlCon.addMainSql(" ON(A.FARM_ID = B.FARM_ID");
        sqlCon.addMainSql(" AND A.CHANGE_PIG_CLASS_ID = B.ROW_ID");
        sqlCon.addMainSql(" AND B.DELETED_FLAG = '0')");
        sqlCon.addMainSql(
                " WHERE A.DELETED_FLAG = '0' AND A.FARM_ID = T2.FARM_ID AND A.PIG_ID = T1.PIG_ID AND A.BILL_ID = T1.BILL_ID ORDER BY B.TOWORK_DATE ASC LIMIT 1) AS lastClassDate");
        // 配种方式
        if (EventConstants.BREED.equals(eventCode)) {
            sqlCon.addMainSql(
                    ", T3.BREED_TYPE AS breedType, T3.BREED_BOAR_FIRST AS breedBoarFirst, T3.BREED_BOAR_SECOND AS breedBoarSecond, T3.BREED_BOAR_THIRD AS breedBoarThird");
        }

        // 寄养
        if (EventConstants.FOSTER.equals(eventCode)) {
            sqlCon.addMainSql(", T3.PIG_ID AS BOARD_SOW_ID");
        }

        if (EventConstants.BACKFAT.equals(eventCode)) {
            // // 背膘不改变PP_L_PIG的LAST_EVENT_DATE，需要PIG_EVENT_HIS取得LAST_EVENT_DATE，当做这个撤销单据的事件时间，猪只卡片的事件事件可连续
            sqlCon.addMainSql(", T6.LAST_EVENT_DATE AS eventHisLastEventDate");
        } else {
            // LAST_EVENT_DATE，当做这个撤销单据的事件时间，猪只卡片的事件事件可连续
            sqlCon.addMainSql(", T1.EVENT_DATE AS eventHisLastEventDate");
        }

        // 选种
        if (EventConstants.SELECT_BREED_PIG.equals(eventCode)) {
            sqlCon.addMainSql(", T3.LAST_MATERIAL_ID AS lastMaterialId ");
        }

        // 需要换耳号事件
        if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventCode) || EventConstants.PREPUBERTAL_CHECK.equals(eventCode)
                || EventConstants.SELECT_BREED_PIG.equals(eventCode)) {
            sqlCon.addMainSql(", T5.EAR_CODE_ID AS earCodeId");
        }

        sqlCon.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T2");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND T1.PIG_ID = T2.PIG_ID");
        sqlCon.addMainSql(" AND T1.LAST_BILL_ID = T2.BILL_ID");
        sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" INNER JOIN " + eventTable + " T3");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T3.FARM_ID");
        if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            sqlCon.addMainSql(" AND (T1.PIG_ID = T3.PIG_ID");
            sqlCon.addMainSql(" OR T1.PIG_ID = T3.IN_PIG_ID)");
        } else {
            sqlCon.addMainSql(" AND T1.PIG_ID = T3.PIG_ID");
        }
        sqlCon.addMainSql(" AND T1.BILL_ID = T3.BILL_ID");
        sqlCon.addMainSql(" AND T3.DELETED_FLAG = '0')");

        // 需要换耳号事件
        if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventCode) || EventConstants.PREPUBERTAL_CHECK.equals(eventCode)
                || EventConstants.SELECT_BREED_PIG.equals(eventCode)) {
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG T5");
            sqlCon.addMainSql(" ON(T1.FARM_ID = T5.FARM_ID");
            sqlCon.addMainSql(" AND T1.PIG_ID = T5.ROW_ID");
            sqlCon.addMainSql(" AND T5.DELETED_FLAG = '0')");
        }

        // 背膘不改变PP_L_PIG的LAST_EVENT_DATE，需要PIG_EVENT_HIS取得LAST_EVENT_DATE，当做这个撤销单据的事件时间，猪只卡片的事件事件可连续
        if (EventConstants.BACKFAT.equals(eventCode)) {
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_HIS T6");
            sqlCon.addMainSql(" ON(T1.FARM_ID = T6.FARM_ID");
            sqlCon.addMainSql(" AND T1.PIG_ID = T6.PIG_ID");
            sqlCon.addMainSql(" AND T1.BILL_ID = T6.BILL_ID");
            sqlCon.addMainSql(" AND T6.DELETED_FLAG = '0')");
        }

        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");

        if (!EventConstants.TO_CHILD_CARE.equals(eventCode) && !EventConstants.TO_CHILD_FATTEN.equals(eventCode) && !EventConstants.GOOD_PIG_SELL
                .equals(eventCode) && !EventConstants.SEED_PIG.equals(eventCode)) {
            if (!EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
                sqlCon.addMainSql(" AND T1.PIG_TYPE <> 3");
            }
        }

        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
        if (pigId != null) {
            if (inPigId == null) {
                sqlCon.addCondition(pigId, " AND T1.PIG_ID = ?");
            } else {
                sqlCon.addCondition(pigId, " AND (T1.PIG_ID = ?");
                sqlCon.addCondition(inPigId, " OR T1.PIG_ID = ?)");
            }
        } else if (pigIds != null && !pigIds.isEmpty()) {
            if (EventConstants.TO_CHILD_CARE.equals(eventCode) || EventConstants.TO_CHILD_FATTEN.equals(eventCode)) {
                sqlCon.addMainSql(" AND T1.PIG_ID IN (");
                for (int i = 0; i < pigIds.size(); i++) {
                    sqlCon.addMainSql(pigIds.get(i));
                    if (i != pigIds.size() - 1) {
                        sqlCon.addMainSql(",");
                    }
                }
                sqlCon.addMainSql(")");
            }
        }
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : infos) {
            Date lastEventDate = Maps.getDate(map, "eventDate");
            // 如果有转舍 或 转状态的猪 即 判断是否限制
            if (!StringUtil.isBlank(Maps.getString(map, "houseId")) || !StringUtil.isBlank(Maps.getString(map, "lastClassDate"))) {
                sapEventMasage(getFarmId(), lastEventDate);
            }
        }

        return infos;
    }

    // 获得父亲或母亲耳牌号，如果耳牌号没有被关联的猪只，其他猪也没有当做父亲或母亲耳牌号，
    private boolean getFatherOrMotherEarBrandIds(List<Long> earBrandIds, Long fatherOrMotherId, String fatherOrMotherFlag) {
        // CHECK1 判断这个耳牌号是否是有其他猪只做父母耳号，如果有，则不删除
        SqlCon check1 = new SqlCon();
        check1.addMainSql("SELECT ROW_ID FROM pp_l_ear_code");
        check1.addCondition(getFarmId(), " WHERE DELETED_FLAG = '0' AND FARM_ID = ?");
        check1.addMainSql(" AND ROW_ID NOT IN (");
        for (int i = 0; i < earBrandIds.size(); i++) {
            check1.addMainSql(String.valueOf(earBrandIds.get(i)));
            if (i != earBrandIds.size() - 1) {
                check1.addMainSql(",");
            }
        }
        check1.addMainSql(")");

        if ("father".equals(fatherOrMotherFlag)) {
            check1.addCondition(fatherOrMotherId, " AND FATHER_EAR_ID = ?");
        } else {
            check1.addCondition(fatherOrMotherId, " AND MOTHER_EAR_ID = ?");
        }
        check1.addMainSql(" LIMIT 1");

        List<EarCodeModel> existsFlag01 = setSql(earCodeMapper, check1);

        if (existsFlag01 != null && !existsFlag01.isEmpty()) {
            return false;
        }

        // CHECK2 判断这个耳牌号是否是有关联猪只
        SqlCon check2 = new SqlCon();
        check2.addMainSql("SELECT ROW_ID FROM pp_l_pig WHERE DELETED_FLAG = '0'");
        check2.addCondition(getFarmId(), " AND FARM_ID = ?");
        check2.addCondition(fatherOrMotherId, " AND EAR_CODE_ID = ?");

        List<PigModel> existsFlag02 = setSql(pigMapper, check2);

        if (existsFlag02 != null && !existsFlag02.isEmpty()) {
            return false;
        }

        return true;
    }

    // 获得被寄养母猪的信息
    private List<Map<String, Object>> getBoardInfos(Map<String, Object> commonInfos) {
        SqlCon sqlCon = new SqlCon();
        if (!Maps.isEmpty(commonInfos, "pigId")) {
            // 猪只撤销
            // 特殊点：同一单据中，一个母猪被多次寄养
            sqlCon.addMainSql(
                    "SELECT MAX(T2.ROW_ID) AS relatesId,T2.PIG_ID AS pigId, MAX(T2.LAST_BILL_ID) AS lastBillId, MAX(T2.LAST_EVENT_DATE) AS lastEventDate");
        } else {
            // 单据撤销
            sqlCon.addMainSql("SELECT T2.PIG_ID AS pigId, MIN(T2.LAST_BILL_ID) AS lastBillId, MIN(T2.LAST_EVENT_DATE) AS lastEventDate");
        }

        sqlCon.addMainSql(" FROM PP_L_BILL_FOSTER_CARE T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T2");
        sqlCon.addMainSql(" ON( T1.FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND T1.BOARD_SOW_ID = T2.PIG_ID");
        sqlCon.addMainSql(" AND T1.BILL_ID = T2.BILL_ID");
        sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "billId"), " AND T1.BILL_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(commonInfos, "pigId"), " AND T1.PIG_ID = ?");
        sqlCon.addMainSql(" GROUP BY T2.PIG_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    // 获得公猪的精液信息
    private List<Map<String, Object>> getSemenInfos(Long pigId, Long billId) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS spermInfoId");
        sqlCon.addMainSql(" FROM PP_L_SPERM_INFO T1 INNER JOIN PP_L_BILL_SEMEN T2");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND T1.SEMEN_ID = T2.ROW_ID");
        sqlCon.addCondition(pigId, " AND T2.PIG_ID = ?");
        sqlCon.addMainSql(" AND T2.STATUS = '1' AND T2.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" INNER JOIN PP_L_PIG_EVENT_RELATES T3");
        sqlCon.addMainSql(" ON(T1.FARM_ID = T3.FARM_ID");
        sqlCon.addMainSql(" AND T1.BILL_ID = T3.BILL_ID");
        sqlCon.addMainSql(" AND T2.PIG_ID = T3.PIG_ID");
        sqlCon.addMainSql(" AND T3.DELETED_FLAG = '0')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND T1.BILL_ID = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    // 获得精液信息
    private List<Map<String, Object>> getSpermInfoList(Map<String, Object> infoMap) {
        SqlCon getSpermInfoSqlCon = new SqlCon();
        getSpermInfoSqlCon.addMainSql("SELECT T1.ROW_ID AS spermInfoId, T2.ROW_ID AS semenId, T2.ANH_NUM AS anhNum");
        getSpermInfoSqlCon.addMainSql(" FROM PP_L_SPERM_INFO T1");
        getSpermInfoSqlCon.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T2");
        getSpermInfoSqlCon.addMainSql(" ON(T1.FARM_ID = T2.FARM_ID");
        getSpermInfoSqlCon.addMainSql(" AND T1.SEMEN_ID = T2.ROW_ID");
        getSpermInfoSqlCon.addMainSql(" AND T2.DELETED_FLAG = '0'");
        getSpermInfoSqlCon.addMainSql(" AND T2.PIG_ID IN (");
        getSpermInfoSqlCon.addMainSql(Maps.getString(infoMap, "breedBoarFirst"));
        // 人工授精
        if ("1".equals(Maps.getString(infoMap, "breedType"))) {
            if (!Maps.isEmpty(infoMap, "breedBoarSecond")) {
                getSpermInfoSqlCon.addMainSql("," + Maps.getString(infoMap, "breedBoarSecond"));
            }
            if (!Maps.isEmpty(infoMap, "breedBoarThird")) {
                getSpermInfoSqlCon.addMainSql("," + Maps.getString(infoMap, "breedBoarThird"));
            }
        }
        getSpermInfoSqlCon.addMainSql(" ))");
        getSpermInfoSqlCon.addMainSql(" WHERE T1.STATUS = '2' AND T1.DELETED_FLAG = '0'");
        getSpermInfoSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        getSpermInfoSqlCon.addCondition(Maps.getLong(infoMap, "pigId"), " AND T1.SOW_PIG_ID = ?");
        getSpermInfoSqlCon.addCondition(Maps.getDate(infoMap, "eventHisLastEventDate"), " AND T1.USE_DATE = '?'");

        Map<String, String> getSpermInfoSqlMap = new HashMap<String, String>();
        getSpermInfoSqlMap.put("sql", getSpermInfoSqlCon.getCondition());

        return paramMapper.getObjectInfos(getSpermInfoSqlMap);
    }

    // 创建撤销事件的PigModel对象
    private PigModel createPigModel(Map<String, Object> infoMap, List<PigModel> pigpenIdsList, List<PigModel> swineryIdsList) {
        Long flag = -99L;

        PigModel pigModel = new PigModel();
        pigModel.setRowId(Maps.getLong(infoMap, "pigId"));
        if (!Maps.isEmpty(infoMap, "pigClass")) {
            pigModel.setPigClass(Maps.getLong(infoMap, "pigClass"));
        }
        if (!Maps.isEmpty(infoMap, "lastClassDate")) {
            pigModel.setLastClassDate(Maps.getDate(infoMap, "lastClassDate"));
        }
        if (!Maps.isEmpty(infoMap, "houseId")) {
            pigModel.setHouseId(Maps.getLong(infoMap, "houseId"));
        }

        if (!Maps.isEmpty(infoMap, "pigpenId")) {
            if (flag.compareTo(Maps.getLong(infoMap, "pigpenId")) != 0) {
                pigModel.setPigpenId(Maps.getLong(infoMap, "pigpenId"));
            } else {
                if (pigpenIdsList != null) {
                    PigModel pigpenIdsModel = new PigModel();
                    pigpenIdsModel.setRowId(Maps.getLong(infoMap, "pigId"));
                    pigpenIdsList.add(pigpenIdsModel);
                }
            }
        }

        if (!Maps.isEmpty(infoMap, "swineryId")) {
            if (flag.compareTo(Maps.getLong(infoMap, "swineryId")) != 0) {
                pigModel.setSwineryId(Maps.getLong(infoMap, "swineryId"));

                // 判断批次是否关闭，如果已关闭，提示用户自己打开批次
                this.swineryIsCloseCheck(Maps.getLong(infoMap, "swineryId"));

            } else {
                if (swineryIdsList != null) {
                    PigModel swineryIdsModel = new PigModel();
                    swineryIdsModel.setRowId(Maps.getLong(infoMap, "pigId"));
                    swineryIdsList.add(swineryIdsModel);
                }
            }
        }

        pigModel.setLastEventDate(Maps.getDate(infoMap, "lastEventDate"));
        pigModel.setLastBillId(Maps.getLong(infoMap, "lastBillId"));
        return pigModel;
    }

    // 创建撤销事件的EarCodeModel对象
    private EarCodeModel createEarCodeModel(Map<String, Object> infoMap, boolean onlyChangeEarBrand) {
        EarCodeModel earCodeModel = new EarCodeModel();
        earCodeModel.setRowId(Maps.getLong(infoMap, "earCodeId"));
        earCodeModel.setEarBrand(Maps.getString(infoMap, "earBrand"));
        if (!onlyChangeEarBrand) {
            if (!Maps.isEmpty(infoMap, "earShort")) {
                earCodeModel.setEarShort(Maps.getString(infoMap, "earShort"));
            } else {
                earCodeModel.setEarShort("");
            }
            if (!Maps.isEmpty(infoMap, "earThorn")) {
                earCodeModel.setEarThorn(Maps.getString(infoMap, "earThorn"));
            } else {
                earCodeModel.setEarThorn("");
            }
            if (!Maps.isEmpty(infoMap, "electronicEarNo")) {
                earCodeModel.setElectronicEarNo(Maps.getString(infoMap, "electronicEarNo"));
            } else {
                earCodeModel.setElectronicEarNo("");
            }
        }
        return earCodeModel;
    }

    // 判断批次是否关闭，如果已关闭，提示用户手动打开猪群
    private void swineryIsCloseCheck(long swineryId) {
        SwineryModel swineryModel = swineryMapper.searchById(swineryId);
        if (swineryModel != null) {
            if (PigConstants.SWINERY_STATUS_CLOSE.equals(swineryModel.getStatus())) {
                Thrower.throwException(ProductionException.PIG_SWINERY_IS_CLOSE, swineryModel.getSwineryName());
            }
        }
    }

    // 创建撤销事件的PigEventHisModel对象,精液销售，精液入库用
    private PigEventHisModel createPigEventHisModelForSperm(Long pigId, Map<String, Object> commonInfos, String eventTable, Long eventTableId,
            String eventNotes) {
        boolean cancelBillFlag = Maps.getBoolean(commonInfos, "cancelBillFlag");

        PigModel pigModel = pigMapper.searchById(pigId);
        EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());

        PigEventHisModel pigEventHisModel = new PigEventHisModel();
        pigEventHisModel.setStatus(CommonConstants.STATUS);
        pigEventHisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        pigEventHisModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        pigEventHisModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        pigEventHisModel.setCompanyId(pigModel.getCompanyId());
        pigEventHisModel.setFarmId(pigModel.getFarmId());
        pigEventHisModel.setLineId(pigModel.getLineId());
        pigEventHisModel.setSwineryId(pigModel.getSwineryId());
        pigEventHisModel.setHouseId(pigModel.getHouseId());
        pigEventHisModel.setPigpenId(pigModel.getPigpenId());
        pigEventHisModel.setPigId(pigModel.getRowId());
        pigEventHisModel.setEarBrand(earCodeModel.getEarBrand());
        pigEventHisModel.setPigType(pigModel.getPigType());
        pigEventHisModel.setSex(pigModel.getPigType());
        pigEventHisModel.setPigClass(pigModel.getPigClass());
        pigEventHisModel.setParity(pigModel.getParity());
        pigEventHisModel.setEnterDate(Maps.getDate(commonInfos, "currentDate"));
        pigEventHisModel.setWorker(getEmployeeId());
        pigEventHisModel.setBillId(Maps.getLongClass(commonInfos, "cancelEventBillId"));
        pigEventHisModel.setLastEventDate(pigModel.getLastEventDate());
        pigEventHisModel.setEventName(Maps.getString(commonInfos, "eventName"));
        if (cancelBillFlag) {
            eventNotes = eventNotes + ",同时撤销了单据ID：" + Maps.getLongClass(commonInfos, "billId") + "的单据";
        }
        pigEventHisModel.setEventNotes(eventNotes);

        pigEventHisModel.setTableName(eventTable.toLowerCase());
        pigEventHisModel.setTableRowId(eventTableId);
        pigEventHisModel.setCreateId(getEmployeeId());
        pigEventHisModel.setCreateDate(Maps.getDate(commonInfos, "currentDate"));
        return pigEventHisModel;
    }

    // 创建撤销事件的PigEventHisModel对象
    private PigEventHisModel createPigEventHisModel(Map<String, Object> infoMap, Map<String, Object> commonInfos) {
        return this.createPigEventHisModel(infoMap, commonInfos, null);
    }

    // 创建撤销事件的PigEventHisModel对象(仔猪使用,主要表需要传值)
    private PigEventHisModel createPigEventHisModel(Map<String, Object> infoMap, Map<String, Object> commonInfos, String eventTable) {
        Long flag = -99L;
        String eventName = Maps.getString(commonInfos, "eventName");
        boolean cancelBillFlag = Maps.getBoolean(commonInfos, "cancelBillFlag");

        PigEventHisModel pigEventHisModel = new PigEventHisModel();
        pigEventHisModel.setStatus("1");
        pigEventHisModel.setDeletedFlag("0");
        pigEventHisModel.setOriginFlag("S");
        pigEventHisModel.setOriginApp("XN1.0");
        pigEventHisModel.setCompanyId(Maps.getLongClass(infoMap, "companyId"));
        pigEventHisModel.setFarmId(Maps.getLongClass(infoMap, "farmId"));
        pigEventHisModel.setLineId(Maps.getLongClass(infoMap, "lineId"));
        if (!Maps.isEmpty(infoMap, "swineryId")) {
            if (flag.compareTo(Maps.getLongClass(infoMap, "swineryId")) != 0) {
                pigEventHisModel.setSwineryId(Maps.getLongClass(infoMap, "swineryId"));
            }
        }
        pigEventHisModel.setHouseId(Maps.getLongClass(infoMap, "houseId"));
        if (!Maps.isEmpty(infoMap, "pigpenId")) {
            if (flag.compareTo(Maps.getLongClass(infoMap, "pigpenId")) != 0) {
                pigEventHisModel.setPigpenId(Maps.getLongClass(infoMap, "pigpenId"));
            }
        }
        pigEventHisModel.setPigId(Maps.getLongClass(infoMap, "pigId"));
        pigEventHisModel.setEarBrand(Maps.getString(infoMap, "earBrand"));
        pigEventHisModel.setPigType(Maps.getString(infoMap, "pigType"));
        pigEventHisModel.setSex(Maps.getString(infoMap, "sex"));
        pigEventHisModel.setPigClass(Maps.getLongClass(infoMap, "pigClass"));
        pigEventHisModel.setParity(Maps.getLongClass(infoMap, "parity"));
        pigEventHisModel.setEnterDate(Maps.getDate(commonInfos, "currentDate"));
        pigEventHisModel.setWorker(getEmployeeId());
        pigEventHisModel.setBillId(Maps.getLongClass(commonInfos, "cancelEventBillId"));
        pigEventHisModel.setLastEventDate(Maps.getDate(infoMap, "eventHisLastEventDate"));
        pigEventHisModel.setEventName(Maps.getString(commonInfos, "eventName"));
        if (eventTable == null) {
            // 非仔猪事件记录
            if (EventConstants.BILL_CANCEL.equals(eventName)) {
                pigEventHisModel.setEventNotes("单据ID：" + Maps.getLongClass(commonInfos, "billId") + "的【" + Maps.getString(commonInfos,
                        "eventNameChinese") + "】事件被撤销");
            } else {
                String eventNotes = "耳牌号为：【" + Maps.getString(infoMap, "earBrand") + "】的【" + Maps.getString(commonInfos, "eventNameChinese")
                        + "】事件被撤销";
                if (cancelBillFlag) {
                    eventNotes = eventNotes + ",同时撤销了单据ID：" + Maps.getLongClass(commonInfos, "billId") + "的单据";
                }
                pigEventHisModel.setEventNotes(eventNotes);
            }
        } else if ("pp_l_bill_wean_detail".equals(eventTable)) {
            String eventNotes = "耳牌号为：【" + Maps.getString(infoMap, "earBrand") + "】的【断奶】事件被撤销";
            pigEventHisModel.setEventNotes(eventNotes);
        } else {
            // 仔猪事件记录
            String eventNotes = "耳牌号为：【" + Maps.getString(infoMap, "earBrand") + "】的【" + Maps.getString(commonInfos, "eventNameChinese") + "】事件被撤销";
            pigEventHisModel.setEventNotes(eventNotes);
        }

        if (eventTable == null) {
            pigEventHisModel.setTableName(Maps.getString(commonInfos, "eventTable").toLowerCase());
        } else {
            pigEventHisModel.setTableName(eventTable.toLowerCase());
        }
        pigEventHisModel.setTableRowId(Maps.getLongClass(infoMap, "mainTableId"));
        pigEventHisModel.setCreateId(getEmployeeId());
        pigEventHisModel.setCreateDate(Maps.getDate(commonInfos, "currentDate"));
        return pigEventHisModel;
    }

    // mainTableIds 主表ID
    // commonInfos 页面传入的公共信息
    // pigModelList 需要还原的猪只信息
    // earCodeModelList 需要还原的耳号信息
    // swineryModelList 撤销之前的批次，如果次批次没有存在转批次记录，则删除批次（只删除系统自建的），如果存在转批次记录但是没有存在猪只，那么关闭猪群（关闭系统自建和人为手动建）
    // pigEventHisModelList 撤销事件插入猪只历史事件表
    // pigpenIdsList 将猪只的猪栏还原为NULL
    // swineryIdsList 将猪只的猪群还原为NULL
    // updateToworkModelList 将上一条的TOWORK_DATE_OUT和PIG_CLASS_OUT还原为NULL
    // updateChangeHouseModelList 将上一条的HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT还原为NULL
    // updateChangeSwineryModelList 将上一条的CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT还原为NULL
    // diePigModelList 将离场的猪只的LEAVE_DATE为NULL
    // updateSapFixedAssetsEarbrandList 将公猪后备转生产，母猪第一次配种生成的SapFixedAssetsEarbrand清空
    private void commonExecute(long[] mainTableIds, Map<String, Object> commonInfos, List<PigModel> pigModelList, List<EarCodeModel> earCodeModelList,
            List<SwineryModel> swineryModelList, List<PigEventHisModel> pigEventHisModelList, List<PigModel> pigpenIdsList,
            List<PigModel> swineryIdsList, List<ToworkModel> updateToworkModelList, List<ChangeHouseModel> updateChangeHouseModelList,
            List<ChangeSwineryModel> updateChangeSwineryModelList, List<PigModel> diePigModelList, List<PigModel> updateSapFixedAssetsEarbrandList) {

        Long billId = Maps.getLongClass(commonInfos, "billId");
        Long pigId = Maps.getLongClass(commonInfos, "pigId");
        List<String> pigIds = Maps.getList(commonInfos, "pigIds");

        String eventTable = Maps.getString(commonInfos, "eventTable");
        String eventCode = Maps.getString(commonInfos, "eventCode");
        Long cancelEventBillId = Maps.getLongClass(commonInfos, "cancelEventBillId");
        boolean cancelBillFlag = Maps.getBoolean(commonInfos, "cancelBillFlag");

        SqlCon deletesByConSqlCon = new SqlCon();
        deletesByConSqlCon.addCondition(billId, " AND BILL_ID = ?");
        // 猪只或者单条记录删除
        if (pigId != null || (pigIds != null && !pigIds.isEmpty())) {
            deletesByConSqlCon.addMainSql(" AND PIG_ID IN (");
            for (int i = 0; i < pigModelList.size(); i++) {
                deletesByConSqlCon.addMainSql(String.valueOf(pigModelList.get(i).getRowId()));
                if (i != pigModelList.size() - 1) {
                    deletesByConSqlCon.addMainSql(",");
                }
            }
            deletesByConSqlCon.addMainSql(")");
        }

        Map<String, Object> deletesByConMap = new HashMap<String, Object>();
        deletesByConMap.put("condition", deletesByConSqlCon.getCondition());
        deletesByConMap.put("cancelEventBillId", Maps.getLong(commonInfos, "cancelEventBillId"));

        if (cancelBillFlag) {
            billMapper.deleteForCancel(billId);
        }

        // 撤销事件主表
        SqlCon deleteMainTableSqlCon = new SqlCon();
        deleteMainTableSqlCon.addMainSql("UPDATE " + eventTable + " SET DELETED_FLAG = '2' WHERE DELETED_FLAG <> '2'");
        if (mainTableIds != null && mainTableIds.length > 0) {
            deleteMainTableSqlCon.addMainSql(" AND ROW_ID IN(");
            for (int i = 0; i < mainTableIds.length; i++) {
                deleteMainTableSqlCon.addMainSql(String.valueOf(mainTableIds[i]));
                if (i != mainTableIds.length - 1) {
                    deleteMainTableSqlCon.addMainSql(",");
                }
            }
            deleteMainTableSqlCon.addMainSql(")");
        }

        // 任意一个Mapper执行SQL
        setSql(pigMapper, deleteMainTableSqlCon);

        pigEventRelatesMapper.deletesByCon2(deletesByConMap);

        pigMapper.updates(pigModelList);

        pigEventHisMapper.inserts(pigEventHisModelList);

        if (pigpenIdsList != null && !pigpenIdsList.isEmpty()) {
            SqlCon pig_pigpenIdsSqlCon = new SqlCon();
            pig_pigpenIdsSqlCon.addMainSql("UPDATE PP_L_PIG SET PIGPEN_ID = NULL");
            pig_pigpenIdsSqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ?");
            pig_pigpenIdsSqlCon.addMainSql(" AND DELETED_FLAG = '0'");
            pig_pigpenIdsSqlCon.addMainSql(" AND ROW_ID IN(");

            SqlCon pigEvent_pigpenIdsSqlCon = new SqlCon();
            pigEvent_pigpenIdsSqlCon.addMainSql("UPDATE PP_L_PIG_EVENT_HIS SET PIGPEN_ID = NULL");
            pigEvent_pigpenIdsSqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ?");
            pigEvent_pigpenIdsSqlCon.addCondition(cancelEventBillId, " AND BILL_ID = ?");
            pigEvent_pigpenIdsSqlCon.addMainSql(" AND PIG_ID IN(");

            for (int i = 0; i < pigpenIdsList.size(); i++) {
                pig_pigpenIdsSqlCon.addMainSql(pigpenIdsList.get(i).getRowId().toString());
                pigEvent_pigpenIdsSqlCon.addMainSql(pigpenIdsList.get(i).getRowId().toString());
                if (i != pigpenIdsList.size() - 1) {
                    pig_pigpenIdsSqlCon.addMainSql(",");
                    pigEvent_pigpenIdsSqlCon.addMainSql(",");
                }
            }
            pig_pigpenIdsSqlCon.addMainSql(")");
            pigEvent_pigpenIdsSqlCon.addMainSql(")");

            setSql(pigMapper, pig_pigpenIdsSqlCon);
            setSql(pigEventHisMapper, pigEvent_pigpenIdsSqlCon);

        }

        if (swineryIdsList != null && !swineryIdsList.isEmpty()) {
            SqlCon pig_swineryIdsSqlCon = new SqlCon();
            pig_swineryIdsSqlCon.addMainSql("UPDATE PP_L_PIG SET SWINERY_ID = NULL");
            pig_swineryIdsSqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ?");
            pig_swineryIdsSqlCon.addMainSql(" AND DELETED_FLAG = '0'");
            pig_swineryIdsSqlCon.addMainSql(" AND ROW_ID IN(");

            SqlCon pigEvent_pigpenIdsSqlCon = new SqlCon();
            pigEvent_pigpenIdsSqlCon.addMainSql("UPDATE PP_L_PIG_EVENT_HIS SET SWINERY_ID = NULL");
            pigEvent_pigpenIdsSqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ?");
            pigEvent_pigpenIdsSqlCon.addCondition(cancelEventBillId, " AND BILL_ID = ?");
            pigEvent_pigpenIdsSqlCon.addMainSql(" AND PIG_ID IN(");

            for (int i = 0; i < swineryIdsList.size(); i++) {
                pig_swineryIdsSqlCon.addMainSql(swineryIdsList.get(i).getRowId().toString());
                pigEvent_pigpenIdsSqlCon.addMainSql(swineryIdsList.get(i).getRowId().toString());
                if (i != swineryIdsList.size() - 1) {
                    pig_swineryIdsSqlCon.addMainSql(",");
                    pigEvent_pigpenIdsSqlCon.addMainSql(",");
                }
            }
            pig_swineryIdsSqlCon.addMainSql(")");
            pigEvent_pigpenIdsSqlCon.addMainSql(")");

            setSql(pigMapper, pig_swineryIdsSqlCon);
            setSql(pigEventHisMapper, pigEvent_pigpenIdsSqlCon);

        }

        if (!EventConstants.CHANGE_EAR_BAND.equals(eventCode) && !EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)
                && !EventConstants.BREED_PIG_CHANGE_HOUSE.equals(eventCode) && !EventConstants.FOSTER.equals(eventCode) && !EventConstants.BACKFAT
                        .equals(eventCode)) {
            toworkMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventCode) || EventConstants.DELIVERY.equals(eventCode) || EventConstants.PREPUBERTAL_CHECK
                .equals(eventCode) || EventConstants.SELECT_BREED_PIG.equals(eventCode)) {
            changeEarbrandMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.TO_CHILD_CARE.equals(eventCode) || EventConstants.TO_CHILD_FATTEN.equals(eventCode)
                || EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventCode) || EventConstants.PREPUBERTAL_CHECK.equals(eventCode)
                || EventConstants.WEAN.equals(eventCode) || EventConstants.DELIVERY.equals(eventCode) || EventConstants.FOSTER.equals(eventCode)
                || EventConstants.BREED.equals(eventCode) || EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode) || EventConstants.SELECT_BREED_PIG
                        .equals(eventCode)) {
            changeHouseMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.TO_CHILD_CARE.equals(eventCode) || EventConstants.TO_CHILD_FATTEN.equals(eventCode) || EventConstants.BREED.equals(
                eventCode) || EventConstants.DELIVERY.equals(eventCode) || EventConstants.SELECT_BREED_PIG.equals(eventCode)
                || EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)) {
            changeSwineryMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.BREED.equals(eventCode)) {
            cancelBreedProNo(pigModelList);
            parityMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode) || EventConstants.BREED_PIG_CHANGE_HOUSE.equals(eventCode) || EventConstants.WEAN
                .equals(eventCode)) {
            backfatMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.WEAN.equals(eventCode) || EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            weanDetailMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.WEAN.equals(eventCode) || EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            childDieMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.CHILD_PIG_DIE.equals(eventCode) || EventConstants.GOOD_PIG_DIE.equals(eventCode) || EventConstants.WEAN.equals(eventCode)
                || EventConstants.DELIVERY.equals(eventCode) || EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            leaveMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.FOSTER.equals(eventCode) || EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode) || EventConstants.WEAN.equals(eventCode)) {
            fosterDetailMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode) || EventConstants.WEAN.equals(eventCode)) {
            fosterCareMapper.deletesByCon2(deletesByConMap);
            weanMapper.deletesByCon2(deletesByConMap);
        }

        if (EventConstants.GOOD_PIG_SELL.equals(eventCode)) {
            pigSaleMapper.deletesByCon2(deletesByConMap);
            pigSaleDetailMapper.deletesByCon2(deletesByConMap);
        }

        if (earCodeModelList != null && !earCodeModelList.isEmpty()) {
            earCodeMapper.updates(earCodeModelList);
        }

        // 修改上一条TOWORK_DATE_OUT和PIG_CLASS_OUT为NULL
        if (updateToworkModelList != null && !updateToworkModelList.isEmpty()) {
            toworkMapper.updatesToworkDateOutAndPigClassOutToNULL(updateToworkModelList);
        }

        // 修改上一条HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT为NULL
        if (updateChangeHouseModelList != null && !updateChangeHouseModelList.isEmpty()) {
            changeHouseMapper.updatesOutFieldToNULL(updateChangeHouseModelList);
        }

        // 修改上一条CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT为NULL
        if (updateChangeSwineryModelList != null && !updateChangeSwineryModelList.isEmpty()) {
            changeSwineryMapper.updatesOutFieldToNULL(updateChangeSwineryModelList);
        }

        // 修改猪只表LEAVE_DATE为NULL
        if (diePigModelList != null && !diePigModelList.isEmpty()) {
            pigMapper.updatesLeaveDateToNULL(diePigModelList);
        }

        // 修改猪只表SAP_FIXED_ASSETS_EARBRAND为NULL
        if (updateSapFixedAssetsEarbrandList != null && !updateSapFixedAssetsEarbrandList.isEmpty()) {
            pigMapper.updatesSapFixedAssetsEarbrandToNULL(updateSapFixedAssetsEarbrandList);
        }

        // 判断猪群是否需要删除
        if (swineryModelList != null && !swineryModelList.isEmpty()) {
            for (SwineryModel swineryModel : swineryModelList) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql("SELECT T1.ROW_ID FROM PP_L_BILL_CHANGE_SWINERY T1");
                sqlCon.addMainSql(" INNER JOIN PP_M_SWINERY T2");
                sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0'");
                sqlCon.addMainSql(" AND T1.SWINERY_ID = T2.ROW_ID)");
                sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
                sqlCon.addConditionWithNull(swineryModel.getRowId(), " AND SWINERY_ID = ? LIMIT 1");

                List<Map<String, Object>> swineryExistsFlag = paramMapperSetSQL(paramMapper, sqlCon);
                // 已经被撤销空,没有被使用的批次
                if (swineryExistsFlag.isEmpty()) {
                    swineryModel = swineryMapper.searchById(swineryModel.getRowId());
                    // 只删除自建的
                    if (PigConstants.SWINERY_CREATE_TYPE_SYS.equals(swineryModel.getCreateType())) {
                        swineryMapper.delete(swineryModel.getRowId());
                    }
                } else {
                    // 如果有转舍记录，但是群中无活猪，则关闭群。
                    SqlCon closeSwineryFlagSqlCon = new SqlCon();
                    closeSwineryFlagSqlCon.addMainSql("SELECT ROW_ID FROM PP_L_PIG WHERE DELETED_FLAG = '0'");
                    closeSwineryFlagSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
                    closeSwineryFlagSqlCon.addCondition(swineryModel.getRowId(), " AND SWINERY_ID = ?");
                    closeSwineryFlagSqlCon.addMainSql(" AND (PIG_CLASS <= 18 OR PIG_CLASS = 24) LIMIT 1");
                    List<Map<String, Object>> swineryCloseFlag = paramMapperSetSQL(paramMapper, closeSwineryFlagSqlCon);
                    if (swineryCloseFlag.isEmpty()) {
                        // 群中无活猪
                        // 获取猪群关闭时间
                        SqlCon getEndDateSqlCon = new SqlCon();
                        getEndDateSqlCon.addMainSql("SELECT DATE_FORMAT(IFNULL(MAX(T1.END_DATE),NOW()),'%Y-%m-%d') AS endDate FROM (");
                        getEndDateSqlCon.addMainSql(
                                " SELECT MAX(CHANGE_SWINERY_DATE_OUT) AS END_DATE FROM PP_L_BILL_CHANGE_SWINERY WHERE DELETED_FLAG = '0'");
                        getEndDateSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                        getEndDateSqlCon.addCondition(swineryModel.getRowId(), " AND SWINERY_ID = ?");
                        getEndDateSqlCon.addMainSql(" UNION ALL");
                        getEndDateSqlCon.addMainSql(" SELECT MAX(LEAVE_DATE) AS END_DATE FROM PP_L_BILL_LEAVE WHERE DELETED_FLAG = '0'");
                        getEndDateSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                        getEndDateSqlCon.addCondition(swineryModel.getRowId(), " AND SWINERY_ID = ?");
                        getEndDateSqlCon.addMainSql(" ) T1");

                        Map<String, String> getEndDateSqlMap = new HashMap<String, String>();
                        getEndDateSqlMap.put("sql", getEndDateSqlCon.getCondition());

                        Map<String, Object> getEndDateMap = paramMapper.getObjectInfos(getEndDateSqlMap).get(0);

                        // 关闭猪群
                        SqlCon sql = new SqlCon();
                        sql.addMainSql(" UPDATE PP_M_SWINERY SET");
                        sql.addCondition(PigConstants.SWINERY_STATUS_CLOSE, " STATUS = ?");
                        sql.addCondition(Maps.getString(getEndDateMap, "endDate"), ", END_DATE = ?");
                        sql.addMainSql(" WHERE DELETED_FLAG = '0'");
                        sql.addCondition(swineryModel.getRowId(), " AND ROW_ID = ?");
                    }
                }
            }
        }
    }

    private void lastPigIsNurse(Map<String, Object> commonInfos) throws Exception {
        SqlCon nurseSql = new SqlCon();
        nurseSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        nurseSql.addCondition(commonInfos.get("billId"), " AND BILL_ID = ?");
        nurseSql.addCondition(commonInfos.get("pigId"), " AND (BOARD_SOW_ID = ?");
        nurseSql.addCondition(commonInfos.get("pigId"), " OR IN_PIG_ID = ?) LIMIT 1");
        List<NurseModel> list = getList(nurseMapper, nurseSql);
        if (!list.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "奶妈事件撤销，只能用本次记录中奶妈【" + CacheUtil.getPigInfo(String.valueOf(list.get(0)
                    .getPigId()), PigInfoEnum.PIG_INFO).get("earBrand") + "】做猪只事件撤销");
        }

    }

    private long[] longArrayFromList(List<Long> longList) {
        if (longList != null && !longList.isEmpty()) {
            long[] longArray = new long[longList.size()];
            for (int i = 0; i < longList.size(); i++) {
                longArray[i] = longList.get(i);
            }
            return longArray;
        }
        return new long[0];
    }

    private String stringArrayListToString(List<String> stringList) {
        if (stringList != null && !stringList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < stringList.size(); i++) {
                stringBuffer.append(stringList.get(i).toString());
                if (i != stringList.size() - 1) {
                    stringBuffer.append(",");
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }

    // 精液入库
    private void cancelSemenInquiry(Map<String, Object> commonInfos) throws Exception {
        Long billId = Maps.getLongClass(commonInfos, "billId");
        Long semenId = Maps.getLongClass(commonInfos, "semenId");
        String eventName = Maps.getString(commonInfos, "eventName");
        Long cancelEventBillId = Maps.getLong(commonInfos, "cancelEventBillId");
        boolean cancelBillFlag = false;
        if (EventConstants.BILL_CANCEL.equals(eventName)) {
            cancelBillFlag = true;
        } else if (EventConstants.EACH_RECORD_CANCEL.equals(eventName)) {
            // 除了本条撤销信息，还存在没有撤销的单条记录
            SqlCon existsNotCancelRecord = new SqlCon();
            existsNotCancelRecord.addMainSql("SELECT ROW_ID FROM PP_L_BILL_SEMEN WHERE DELETED_FLAG = '0'");
            existsNotCancelRecord.addCondition(getFarmId(), " AND FARM_ID = ?");
            existsNotCancelRecord.addCondition(billId, " AND BILL_ID = ?");
            existsNotCancelRecord.addCondition(semenId, " AND ROW_ID <> ?");
            existsNotCancelRecord.addMainSql(" LIMIT 1");

            List<Map<String, Object>> existsNotCancelRecordList = paramMapperSetSQL(paramMapper, existsNotCancelRecord);
            // 加上本次撤销，已全部撤销
            if (existsNotCancelRecordList.isEmpty()) {
                cancelBillFlag = true;
            }
        }
        commonInfos.put("cancelBillFlag", cancelBillFlag);

        // 查询是否存在精液已经被使用过，使用过，无法撤销
        SqlCon spermInfoUsedCheckSqlCon = new SqlCon();
        spermInfoUsedCheckSqlCon.addMainSql("SELECT T2.EAR_BRAND AS earBrand");
        spermInfoUsedCheckSqlCon.addMainSql(",CONCAT(T4.SEMEN_BATCH_NO,'-',(T3.BUSINESS_CODE + 1)) AS spermBatchNo");
        spermInfoUsedCheckSqlCon.addMainSql(" FROM PP_L_PIG T1");
        spermInfoUsedCheckSqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T2");
        spermInfoUsedCheckSqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.EAR_CODE_ID = T2.ROW_ID)");
        spermInfoUsedCheckSqlCon.addMainSql(" INNER JOIN PP_L_SPERM_INFO T3");
        spermInfoUsedCheckSqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T1.ROW_ID = T3.SOW_PIG_ID");
        spermInfoUsedCheckSqlCon.addCondition(SpermConstants.SPERM_INFO_STATUS_USE, " AND T3.STATUS = ?)");
        spermInfoUsedCheckSqlCon.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T4");
        spermInfoUsedCheckSqlCon.addMainSql(" ON(T4.DELETED_FLAG = '0' AND T3.SEMEN_ID = T4.ROW_ID)");
        spermInfoUsedCheckSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        spermInfoUsedCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        spermInfoUsedCheckSqlCon.addCondition(billId, " AND T3.BILL_ID = ?");
        spermInfoUsedCheckSqlCon.addCondition(semenId, " AND T4.ROW_ID = ?");
        spermInfoUsedCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> spermInfoUsedCheckSqlMap = new HashMap<String, String>();
        spermInfoUsedCheckSqlMap.put("sql", spermInfoUsedCheckSqlCon.getCondition());

        List<Map<String, Object>> spermInfoUsedCheckInfos = paramMapper.getObjectInfos(spermInfoUsedCheckSqlMap);

        if (spermInfoUsedCheckInfos != null && !spermInfoUsedCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.SPERM_HAS_BEEN_USED, Maps.getString(spermInfoUsedCheckInfos.get(0), "spermBatchNo"), Maps
                    .getString(spermInfoUsedCheckInfos.get(0), "earBrand"));
        }

        // 查询是否存在精液已经被销售过，销售过，无法撤销
        SqlCon spermInfoSaleCheckSqlCon = new SqlCon();
        spermInfoSaleCheckSqlCon.addMainSql("SELECT CONCAT(T2.SEMEN_BATCH_NO,'-',(T1.BUSINESS_CODE + 1)) AS spermBatchNo");
        spermInfoSaleCheckSqlCon.addMainSql(" FROM PP_L_SPERM_INFO T1");
        spermInfoSaleCheckSqlCon.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T2");
        spermInfoSaleCheckSqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.SEMEN_ID = T2.ROW_ID)");
        spermInfoSaleCheckSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        spermInfoSaleCheckSqlCon.addCondition(SpermConstants.SPERM_INFO_STATUS_SALE, " AND T1.STATUS = ?");
        spermInfoSaleCheckSqlCon.addCondition(SpermConstants.SPERM_INFO_IS_SALE_SALE, " AND T1.IS_SALE = ?");
        spermInfoSaleCheckSqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        spermInfoSaleCheckSqlCon.addCondition(billId, " AND T1.BILL_ID = ?");
        spermInfoSaleCheckSqlCon.addCondition(semenId, " AND T2.ROW_ID = ?");
        spermInfoSaleCheckSqlCon.addMainSql(" LIMIT 1");

        Map<String, String> spermInfoSaleCheckSqlMap = new HashMap<String, String>();
        spermInfoSaleCheckSqlMap.put("sql", spermInfoSaleCheckSqlCon.getCondition());

        List<Map<String, Object>> spermInfoSaleCheckInfos = paramMapper.getObjectInfos(spermInfoSaleCheckSqlMap);

        if (spermInfoSaleCheckInfos != null && !spermInfoSaleCheckInfos.isEmpty()) {
            Thrower.throwException(BaseBusiException.SPERM_HAS_BEEN_SALE, Maps.getString(spermInfoSaleCheckInfos.get(0), "spermBatchNo"));
        }

        SqlCon semenSqlCon = new SqlCon();
        semenSqlCon.addCondition(billId, " AND BILL_ID = ?");
        semenSqlCon.addCondition(semenId, " AND ROW_ID = ? FOR UPDATE");
        List<SemenModel> semenModelList = getList(semenMapper, semenSqlCon);
        List<SemenSaleModel> updateSemenSaleModelList = new ArrayList<SemenSaleModel>();
        List<SpermInfoModel> cancelSpermInfoModelList = new ArrayList<SpermInfoModel>();
        List<PigEventHisModel> pigEventHisModelList = new ArrayList<PigEventHisModel>();
        if(!semenModelList.isEmpty()){
            for (SemenModel semenModel : semenModelList) {
                semenModel.setDeletedBillId(cancelEventBillId);

                SqlCon spermInfoSqlCon = new SqlCon();
                spermInfoSqlCon.addCondition(semenModel.getRowId(), " AND SEMEN_ID = ?");
                List<SpermInfoModel> spermInfoModelList = getList(spermInfoMapper, spermInfoSqlCon);
                if(spermInfoModelList.size() != semenModel.getSpermNum().intValue()){
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统异常，精液批号：【" + semenModel.getSemenBatchNo()
                    + "】,精液撤销数量和入库数量不一致，请联系管理员。");
                }
                cancelSpermInfoModelList.addAll(spermInfoModelList);

                SqlCon semsenSaleSqlCon = new SqlCon();
                semsenSaleSqlCon.addCondition(semenModel.getSemenSaleRowId(), " AND ROW_ID = ? FOR UPDATE");

                List<SemenSaleModel> semenSaleModelList = getList(semenSaleMapper, semsenSaleSqlCon);
                for (SemenSaleModel semenSaleModel : semenSaleModelList) {
                    // 还原IS_ENTRY
                    semenSaleModel.setIsEntry(SpermConstants.SPERM_INFO_IS_ENTRY_UNENTRY);
                    updateSemenSaleModelList.add(semenSaleModel);
                }

                if (semenModel.getPigId() != null) {
                    // 供应商
                    Map<String, String> supplierInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(semenModel.getSupplierId()));
                    // 生产厂家
                    Map<String, String> manufacturerInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(semenModel.getManufacturerId()));
                    String eventNotes = "平台内-供应商：【" + Maps.getString(supplierInfoMap, "SORT_NAME") + "】，生产厂家：【" + Maps.getString(manufacturerInfoMap,
                            "SORT_NAME") + "】，精液批号：【" + semenModel.getSemenBatchNo() + "】，入库总数：【" + semenModel.getSpermNum() + "】被撤销";
                    PigEventHisModel pigEventHisModel = createPigEventHisModelForSperm(semenModel.getPigId(), commonInfos, "PP_L_BILL_SEMEN",
                            semenModel.getRowId(), eventNotes);
                    pigEventHisModelList.add(pigEventHisModel);
                }
            }
        }else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
        }

        if (cancelBillFlag) {
            billMapper.deleteForCancel(billId);
        }

        if (!semenModelList.isEmpty()) {
            semenMapper.deletesForCancel(semenModelList);
        }
        if (!cancelSpermInfoModelList.isEmpty()) {
            spermInfoMapper.deletesForCancel(cancelSpermInfoModelList);
        }
        if(!updateSemenSaleModelList.isEmpty()){
            semenSaleMapper.updates(updateSemenSaleModelList);
        }
        if (!pigEventHisModelList.isEmpty()) {
            pigEventHisMapper.inserts(pigEventHisModelList);
        }
    }

    // 撤销生产号
    private void cancelBreedProNo(List<PigModel> pigModelList) {
        List<Long> setNullIds = new ArrayList<>();
        List<PigModel> pigList = new ArrayList<>();
        for (PigModel pigModel : pigModelList) {
            if (pigModel.getParity() == 0) {
                setNullIds.add(pigModel.getRowId());
            } else {
                pigList.add(pigModel);
            }
        }
        if (!setNullIds.isEmpty()) {
            SqlCon setNullSql = new SqlCon();
            setNullSql.addMainSql("UPDATE pp_l_pig SET PRO_NO = NULL WHERE DELETED_FLAG = 0");
            setNullSql.addCondition(StringUtil.listLongToString(setNullIds), " AND ROW_ID IN ?", false, true);
            setSql(pigMapper, setNullSql);
        }
        if (!pigList.isEmpty()) {
            SqlCon pigSql = new SqlCon();
            pigSql.addMainSql("SELECT PIG_ID AS ROW_ID,PRO_NO FROM pp_l_bill_breed WHERE DELETED_FLAG = 0");
            for (int i = 0; i < pigList.size(); i++) {
                PigModel model = pigList.get(i);
                if (i == 0) {
                    pigSql.addCondition(model.getParity(), " AND ((PARITY = ?");
                    pigSql.addCondition(model.getRowId(), " AND PIG_ID = ?)");
                } else {
                    pigSql.addCondition(model.getParity(), " OR (PARITY = ?");
                    pigSql.addCondition(model.getRowId(), " AND PIG_ID = ?)");
                }
            }
            pigSql.addMainSql(")");
            List<PigModel> pigProNoList = setSql(pigMapper, pigSql);
            if (!pigProNoList.isEmpty()) {
                pigMapper.updates(pigProNoList);
            }
        }
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

    private List<String> getStrList(Map<String, Object> inputMap, String name) {
        Object obj = inputMap.get(name + "[]");
        if (obj instanceof List) {
            return (List<String>) obj;
        }
        if (obj instanceof String) {
            List<String> result = new ArrayList<>();
            String str = (String) obj;
            if (StringUtil.isNonBlank(str)) {
                String[] strs = str.split(",");
                for (String perStr : strs) {
                    result.add(perStr);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
}
