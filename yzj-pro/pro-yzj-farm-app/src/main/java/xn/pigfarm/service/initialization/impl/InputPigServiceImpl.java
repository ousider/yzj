package xn.pigfarm.service.initialization.impl;

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
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.util.HanaUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.initialization.InputPigException;
import xn.pigfarm.mapper.basicinfo.BoarMapper;
import xn.pigfarm.mapper.basicinfo.PorkPigMapper;
import xn.pigfarm.mapper.basicinfo.SettingMapper;
import xn.pigfarm.mapper.basicinfo.SowMapper;
import xn.pigfarm.mapper.hana.SysHanaInsertLogMapper;
import xn.pigfarm.mapper.initialization.PpInputPigMapper;
import xn.pigfarm.mapper.production.BillMapper;
import xn.pigfarm.mapper.production.PigEventMapper;
import xn.pigfarm.mapper.production.PigEventRelatesMapper;
import xn.pigfarm.model.basicinfo.BoarModel;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.basicinfo.SowModel;
import xn.pigfarm.model.hana.SysHanaInsertLogModel;
import xn.pigfarm.model.initialization.PpInputPigModel;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.BreedView;
import xn.pigfarm.model.production.ChangeHouseView;
import xn.pigfarm.model.production.CreatePorkPigView;
import xn.pigfarm.model.production.DeliveryView;
import xn.pigfarm.model.production.PigEventRelatesModel;
import xn.pigfarm.model.production.PigMoveInView;
import xn.pigfarm.model.production.PregnancyCheckView;
import xn.pigfarm.model.production.ReserveToProductView;
import xn.pigfarm.model.production.WeanView;
import xn.pigfarm.service.hana.ISendToSAPProductionService;
import xn.pigfarm.service.initialization.IInputPigService;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.ExpImpConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * @Description: 初始化猪只导入
 * @author zhangjs
 * @date 2016年8月16日 下午4:14:04
 */
@Service("InputService")
public class InputPigServiceImpl extends BaseServiceImpl implements IInputPigService {

    @Autowired
    private PpInputPigMapper ppInputPigMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private SowMapper sowMapper;

    @Autowired
    private BoarMapper boarMapper;

    @Autowired
    private PigEventMapper pigEventMapper;

    @Autowired
    private PigEventRelatesMapper pigEventRelatesMapper;

    @Autowired
    private PorkPigMapper porkPigMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private SysHanaInsertLogMapper sysHanaInsertLogMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private ISendToSAPProductionService sendToSAPProductionService;

    @Override
    public List<PpInputPigModel> editAndSearchInputPig() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        sqlCon.addConditionWithNull(ExpImpConstants.STATUS_UNEXC, "  AND STATUS = ?");
        List<PpInputPigModel> list = getList(ppInputPigMapper, sqlCon);
        // 改为正在执行状态 目前初始化只一人执行，不需要这步，如果多人操作，上面的查询语句也许加上for update
        // ppInputPigMapper.batchUpdateStatus(PigConstants.PIG_INIT_STATUS_EXC, list);
        return list;
    }

    @Override
    public int editInputPigStatus(List<PpInputPigModel> list, String status) throws Exception {
        return ppInputPigMapper.batchUpdateStatus(status, list);
    }

    @Override
    public void executeInputSow(List<PpInputPigModel> pigList) throws Exception {
        PpInputPigModel ppInputModel = pigList.get(0);
        // 入场
        Map<String, Object> map = editPigInputPigMoveIn(ppInputModel, PigConstants.PIG_TYPE_SOW);
        Long pigId = Maps.getLongClass(map, "PIG_ID");

        // 修改INPUT表的pigId
        editInputPigUpdatePigId(pigList, pigId);

        for (PpInputPigModel pigModel : pigList) {

            // 配种
            editInputPigBreed(pigModel, map);

            // 妊娠检查
            editPregnancyCheck(pigModel, map);

            // 上产房
            editInputPigChangeHouseDel(pigModel, map);

            // 分娩
            editInputPigDelivery(pigModel, map);

            // 断奶
            editInputPigWean(pigModel, map);
        }
        /* 转舍 */
        ppInputModel = pigList.get(pigList.size() - 1);
        editInputPigChangeHouse(ppInputModel, Maps.getDate(map, "ENTER_DATE", TimeUtil.getSysTimestamp()));

        // 改input状态
        ppInputPigMapper.batchUpdateStatus(PigConstants.PIG_INIT_STATUS_SUC, pigList);
    }

    @Override
    public void executeInputBoar(PpInputPigModel ppInputPigModel) throws Exception {
        // 入场
        Map<String, Object> map = editPigInputPigMoveIn(ppInputPigModel, PigConstants.PIG_TYPE_BOAR);
        Long pigId = Maps.getLongClass(map, "PIG_ID");

        // 修改INPUT表的pigId
        editInputPigUpdatePigId(ppInputPigModel, pigId);
        // 转生产公猪直接入场为生产公猪
        editInputPigReserveToProduct(ppInputPigModel, map);
        // 转舍
        editInputPigChangeHouse(ppInputPigModel, Maps.getDate(map, "ENTER_DATE", TimeUtil.getSysTimestamp()));
        // 改input状态
        ppInputPigMapper.updateStatus(PigConstants.PIG_INIT_STATUS_SUC, ppInputPigModel);
    }

    /**
     * @Description: 创建工单
     * @author zhangjs
     * @param eventName
     * @return
     * @throws Exception
     */
    private long editPigInputCreateBill(String eventName, Date billDate) throws Exception {
        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());

        /* 1、创建工单记录 */
        BillModel billModel = new BillModel();
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setCreateId(getEmployeeId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode(eventName);
        billModel.setStatus(PigConstants.STATUS_NOR);
        billModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
        // 创建表单
        billModel.setBillDate(billDate);
        billMapper.insert(billModel);
        return billModel.getRowId();
    }

    /**
     * @Description: 入场
     * @author zhangjs
     * @param ppInputPigModel
     * @param pigType
     * @throws Exception
     */
    private Map<String, Object> editPigInputPigMoveIn(PpInputPigModel ppInputPigModel, String pigType) throws Exception {

        sapEventMasage(getFarmId(), ppInputPigModel.getBirthDate());

        Map<String, Object> result = new HashMap<>();

        PigMoveInView pigMoveInModel = new PigMoveInView();
        String eventName = EventConstants.PIG_MOVE_IN;
        // 创建工单
        long billId = editPigInputCreateBill(eventName, ppInputPigModel.getBirthDate());

        /* 2、查询物料 */
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(ppInputPigModel.getMaterialId(), "  AND MATERIAL_ID = ?");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        // 公猪
        if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
            BoarModel boarModel = getModel(boarMapper, sqlCon);
            pigMoveInModel.setBreedId(boarModel.getBreedId());
            pigMoveInModel.setStrain(boarModel.getStrain());
            pigMoveInModel.setBirthWeight(boarModel.getBirthWeight());
            pigMoveInModel.setBodyCondition(boarModel.getBodyCondition());
            pigMoveInModel.setLeftTeatNum(boarModel.getLeftTeatNum());
            pigMoveInModel.setRightTeatNum(boarModel.getRightTeatNum());
            pigMoveInModel.setPigClass(PigConstants.PIG_CLASS_HBGZ);
            pigMoveInModel.setParity(0L);
            pigMoveInModel.setSex(PigConstants.PIG_SEX_MALE);

            // 公猪计算转生产日期要用
            result.put("PRODUCTION_DAY_AGE", boarModel.getProductionDayAge());
        }
        // 母猪
        else if (PigConstants.PIG_TYPE_SOW.equals(pigType)) {
            SowModel sowModel = getModel(sowMapper, sqlCon);
            pigMoveInModel.setBreedId(sowModel.getBreedId());
            pigMoveInModel.setStrain(sowModel.getStrain());
            pigMoveInModel.setBirthWeight(sowModel.getBirthWeight());
            pigMoveInModel.setBodyCondition(sowModel.getBodyCondition());
            pigMoveInModel.setLeftTeatNum(sowModel.getLeftTeatNum());
            pigMoveInModel.setRightTeatNum(sowModel.getRightTeatNum());
            if (ppInputPigModel.getParity() > 0) {
                pigMoveInModel.setParity(ppInputPigModel.getParity() - 1);
                pigMoveInModel.setPigClass(PigConstants.PIG_CLASS_SOW_DN);
            } else {
                pigMoveInModel.setParity(0L);
                pigMoveInModel.setPigClass(PigConstants.PIG_CLASS_HBDP);
            }

            pigMoveInModel.setSex(PigConstants.PIG_SEX_FEMALE);
        }
        pigMoveInModel.setOptType(PigConstants.PIG_MOVE_IN_OPT_TYPE_FILE);
        pigMoveInModel.setEarBrand(ppInputPigModel.getEarBrand());
        pigMoveInModel.setEarShort(ppInputPigModel.getEarShort());
        pigMoveInModel.setEarThorn(ppInputPigModel.getEarThorn());
        pigMoveInModel.setLineNumber(ppInputPigModel.getSortNbr());
        pigMoveInModel.setPigType(ppInputPigModel.getPigType());
        pigMoveInModel.setMaterialId(ppInputPigModel.getMaterialId());
        pigMoveInModel.setCompanyId(getCompanyId());
        pigMoveInModel.setFarmId(getFarmId());
        pigMoveInModel.setBirthParity(0L);
        pigMoveInModel.setHouseNum(0L);
        // pigMoveInModel.setHouseId(ppInputPigModel.getHouseId());
        pigMoveInModel.setBillId(billId);
        pigMoveInModel.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
        pigMoveInModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
        pigMoveInModel.setEnterDate(ppInputPigModel.getBirthDate());
        pigMoveInModel.setBirthDate(ppInputPigModel.getBirthDate());
        pigMoveInModel.setCreateId(getEmployeeId());
        pigMoveInModel.setWorker(getEmployeeId());
        pigMoveInModel.setEventName(eventName);
        // Added by zhangjs for 增加猪只来源
        pigMoveInModel.setOrigin(PigConstants.PIG_ORIGIN_HC);

        pigEventMapper.pigMoveIn(pigMoveInModel);
        if (!"0".equals(pigMoveInModel.getErrorCode())) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
        }

        result.put("PIG_ID", pigMoveInModel.getErrorMessage());
        result.put("ENTER_DATE", ppInputPigModel.getBirthDate());

        insertPigEventRelates(Long.parseLong(pigMoveInModel.getErrorMessage()));
        return result;
    }

    /**
     * @Description: 修改INPUT表的pigId
     * @author zhangjs
     * @param ppInputPigModel
     * @throws Exception
     */
    private void editInputPigUpdatePigId(List<PpInputPigModel> pigList, Long pigId) throws Exception {
        for (PpInputPigModel ppInputPigModel : pigList) {
            ppInputPigModel.setPigId(pigId);
        }
        ppInputPigMapper.updates(pigList);
    }

    /**
     * @Description: 修改INPUT表的pigId
     * @author zhangjs
     * @param ppInputPigModel
     * @throws Exception
     */
    private void editInputPigUpdatePigId(PpInputPigModel ppInputPigModel, Long pigId) throws Exception {
        ppInputPigModel.setPigId(pigId);
        ppInputPigMapper.update(ppInputPigModel);
    }

    /**
     * @Description: 转生产公猪
     * @author zhangjs
     * @param ppInputPigModel
     * @throws Exception
     */
    private void editInputPigReserveToProduct(PpInputPigModel ppInputPigModel, Map<String, Object> map) throws Exception {

        if (ppInputPigModel.getProductDate() != null) {
            map.put("ENTER_DATE", ppInputPigModel.getProductDate());
            String eventName = EventConstants.BOAR_RESERVE_TO_PRODUCT;
            // 创建工单
            long billId = editPigInputCreateBill(eventName, ppInputPigModel.getProductDate());

            ReserveToProductView boarReserveToProductView = new ReserveToProductView();
            boarReserveToProductView.setPigId(ppInputPigModel.getPigId());
            boarReserveToProductView.setBillId(billId);
            boarReserveToProductView.setFarmId(getFarmId());
            boarReserveToProductView.setCompanyId(getCompanyId());
            boarReserveToProductView.setCreateId(getEmployeeId());
            boarReserveToProductView.setWorker(getEmployeeId());
            boarReserveToProductView.setEventName(eventName);
            boarReserveToProductView.setLineNumber(ppInputPigModel.getSortNbr());
            boarReserveToProductView.setEnterDate(ppInputPigModel.getProductDate());
            boarReserveToProductView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            boarReserveToProductView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
            pigEventMapper.reserveToProduct(boarReserveToProductView);
            if (!"0".equals(boarReserveToProductView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, boarReserveToProductView.getErrorMessage());
            }
            insertPigEventRelates(ppInputPigModel.getPigId());
        }
    }

    /**
     * @Description: 配种
     * @author zhangjs
     * @param list
     * @throws Exception
     */
    private void editInputPigBreed(PpInputPigModel ppInputPig, Map<String, Object> map) throws Exception {
        if (ppInputPig.getBreedDate() != null) {
            map.put("ENTER_DATE", ppInputPig.getBreedDate());
            String eventName = EventConstants.BREED;
            // 创建工单
            long billId = editPigInputCreateBill(eventName, ppInputPig.getBreedDate());

            BreedView breedView = new BreedView();
            breedView.setBillId(billId);
            breedView.setFarmId(getFarmId());
            breedView.setCompanyId(getCompanyId());
            breedView.setCreateId(getEmployeeId());
            breedView.setWorker(getEmployeeId());
            breedView.setBreedBoarFirst(ppInputPig.getBreedBoar());
            breedView.setBreedType(PigConstants.PIG_BREED_TYPE_NAT);
            breedView.setEnterDate(ppInputPig.getBreedDate());
            breedView.setPigId(ppInputPig.getPigId());
            breedView.setEventName(eventName);
            breedView.setLineNumber(ppInputPig.getSortNbr());
            breedView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            breedView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
            pigEventMapper.breed(breedView);
            if (!"0".equals(breedView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, breedView.getErrorMessage());
            }

            insertPigEventRelates(ppInputPig.getPigId());
        }
    }

    /**
     * @Description: 妊娠检查
     * @author zhangjs
     * @param billModel
     * @param eventName
     * @param pregnancyCheckList
     * @throws Exception
     */
    private void editPregnancyCheck(PpInputPigModel ppInputPig, Map<String, Object> map) throws Exception {

        if (ppInputPig.getPregnancyDate() != null) {
            map.put("ENTER_DATE", ppInputPig.getPregnancyDate());
            String eventName = EventConstants.PREGNANCY_CHECK;
            // 创建工单
            long billId = editPigInputCreateBill(eventName, ppInputPig.getPregnancyDate());
            PregnancyCheckView checkView = new PregnancyCheckView();

            checkView.setBillId(billId);
            checkView.setFarmId(getFarmId());
            checkView.setCompanyId(getCompanyId());
            checkView.setCreateId(getEmployeeId());
            checkView.setWorker(getEmployeeId());
            checkView.setEventName(eventName);
            checkView.setEnterDate(ppInputPig.getPregnancyDate());
            checkView.setPregnancyType(PigConstants.PIG_PREGNANCY_TYPE_B);
            checkView.setPregnancyResult(ppInputPig.getPregnancyResult());
            checkView.setPigId(ppInputPig.getPigId());
            checkView.setLineNumber(ppInputPig.getSortNbr());
            checkView.setCreateId(getEmployeeId());
            checkView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            checkView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);

            pigEventMapper.pregnancyCheck(checkView);
            if (!"0".equals(checkView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, checkView.getErrorMessage());
            }

            insertPigEventRelates(ppInputPig.getPigId());
        }
    }

    /**
     * @Description: 转产房
     * @author zhangjs
     * @param ppInputPig
     * @throws Exception
     */
    private void editInputPigChangeHouseDel(PpInputPigModel ppInputPig, Map<String, Object> map) throws Exception {

        if (ppInputPig.getChangeHouseDate() != null) {
            map.put("ENTER_DATE", ppInputPig.getChangeHouseDate());
            // 转产房
            String eventName = EventConstants.CHANGE_LABOR_HOUSE;
            // 创建工单
            long billId = editPigInputCreateBill(eventName, ppInputPig.getChangeHouseDate());

            ChangeHouseView changeHouseView = new ChangeHouseView();
            changeHouseView.setBillId(billId);
            changeHouseView.setFarmId(getFarmId());
            changeHouseView.setCompanyId(getCompanyId());
            changeHouseView.setEventName(eventName);
            changeHouseView.setCreateId(getEmployeeId());
            changeHouseView.setWorker(getEmployeeId());
            changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_DEL);
            changeHouseView.setInHouseId(ppInputPig.getChangeHouseId());
            changeHouseView.setInPigpenId(ppInputPig.getChangePigpenId());
            changeHouseView.setPigId(ppInputPig.getPigId());
            changeHouseView.setLineNumber(ppInputPig.getSortNbr());
            changeHouseView.setEnterDate(ppInputPig.getChangeHouseDate());
            changeHouseView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            changeHouseView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
            pigEventMapper.changeHouse(changeHouseView);
            // if (!"0".equals(changeHouseView.getErrorCode())) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
            // }
            if ("0".equals(changeHouseView.getErrorCode())) {
                insertPigEventRelates(ppInputPig.getPigId());
            }
        }
    }

    /**
     * @Description: 分娩
     * @author zhangjs
     * @param ppInputPig
     * @throws Exception
     */
    private void editInputPigDelivery(PpInputPigModel ppInputPig, Map<String, Object> map) throws Exception {
        if (ppInputPig.getDeliveryDate() != null) {
            map.put("ENTER_DATE", ppInputPig.getDeliveryDate());
            // 分娩
            String eventName = EventConstants.DELIVERY;
            // 创建工单
            long billId = editPigInputCreateBill(eventName, ppInputPig.getDeliveryDate());

            DeliveryView deliveryView = new DeliveryView();
            deliveryView.setBillId(billId);
            deliveryView.setFarmId(getFarmId());
            deliveryView.setCompanyId(getCompanyId());
            deliveryView.setEventName(eventName);
            deliveryView.setCreateId(getEmployeeId());
            deliveryView.setWorker(getEmployeeId());
            deliveryView.setEnterDate(ppInputPig.getDeliveryDate());
            deliveryView.setDeliveryType(PigConstants.DELIVERY_TYPE_EUT);
            deliveryView.setHealthyNum(ppInputPig.getHealthyNum());
            deliveryView.setWeakNum(ppInputPig.getWeakNum());
            deliveryView.setStillbirthNum(ppInputPig.getStillbirthNum());
            deliveryView.setMummyNum(ppInputPig.getMummyNum());
            deliveryView.setMutantNum(ppInputPig.getMutantNum());
            deliveryView.setPigId(ppInputPig.getPigId());
            deliveryView.setLineNumber(ppInputPig.getSortNbr());
            deliveryView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            deliveryView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);

            pigEventMapper.delivery(deliveryView);
            if (!"0".equals(deliveryView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, deliveryView.getErrorMessage());
            }
            insertPigEventRelates(ppInputPig.getPigId());

            /* 有分娩记录无断奶记录才产生仔猪 */
            if (ppInputPig.getWeanDate() == null) {
                // 创建仔猪
                CreatePorkPigView createPorkPigView = new CreatePorkPigView();
                // 自动生成所有仔猪
                createPorkPigView.setActionType(PigConstants.ACTION_TYPE_AUTO);
                createPorkPigView.setLineNumber(deliveryView.getLineNumber());
                createPorkPigView.setFarmId(getFarmId());
                createPorkPigView.setCompanyId(getCompanyId());

                createPorkPigView.setSowId(deliveryView.getPigId());
                createPorkPigView.setBirthDate(deliveryView.getEnterDate());
                createPorkPigView.setAliveLitterWeight(deliveryView.getAliveLitterWeight());
                createPorkPigView.setHealthyNum(deliveryView.getHealthyNum());
                createPorkPigView.setWeakNum(deliveryView.getWeakNum());
                createPorkPigView.setStillbirthNum(deliveryView.getStillbirthNum());
                createPorkPigView.setMutantNum(deliveryView.getMutantNum());
                createPorkPigView.setMummyNum(deliveryView.getMummyNum());
                createPorkPigView.setBlackNum(deliveryView.getBlackNum());
                createPorkPigView.setWorker(deliveryView.getWorker());
                createPorkPigView.setBillId(deliveryView.getBillId());
                createPorkPigView.setEventName(deliveryView.getEventName());
                createPorkPigView.setCreateId(getEmployeeId());
                createPorkPigView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
                createPorkPigView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
                pigEventMapper.createPorkPig(createPorkPigView);
                if (!"0".equals(createPorkPigView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, createPorkPigView.getErrorMessage());
                }
                insertPigMoveInRelates(deliveryView.getBillId());
            }
        }
    }

    /**
     * @Description: 断奶
     * @author zhangjs
     * @param ppInputPig
     * @throws Exception
     */
    private void editInputPigWean(PpInputPigModel ppInputPig, Map<String, Object> map) throws Exception {
        if (ppInputPig.getWeanDate() != null) {
            map.put("ENTER_DATE", ppInputPig.getWeanDate());
            // 转产房
            String eventName = EventConstants.WEAN;
            // 创建工单
            long billId = editPigInputCreateBill(eventName, ppInputPig.getWeanDate());

            WeanView weanView = new WeanView();
            weanView.setBillId(billId);
            weanView.setFarmId(getFarmId());
            weanView.setCompanyId(getCompanyId());
            weanView.setCreateId(getEmployeeId());
            weanView.setWorker(getEmployeeId());
            weanView.setPigId(ppInputPig.getPigId());
            weanView.setEnterDate(ppInputPig.getWeanDate());
            weanView.setWeanNum(ppInputPig.getWeanNum());
            weanView.setDieNum(ppInputPig.getHealthyNum() - ppInputPig.getWeanNum());
            weanView.setLineNumber(ppInputPig.getSortNbr());
            // 系统导入数据
            weanView.setSysInput(PigConstants.SYS_INPUT_IMP);
            weanView.setEventName(eventName);
            weanView.setWeanWeight(ppInputPig.getWeanWeight());
            weanView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            weanView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
            /* 仔猪死亡 */
            // if (weanView.getDieNum() > 0) {
            // ChildDieView childDieView = new ChildDieView();
            // childDieView.setBillId(billId);
            // childDieView.setFarmId(getFarmId());
            // childDieView.setCompanyId(getCompanyId());
            // childDieView.setEventName(EventConstants.CHILD_PIG_DIE);
            // childDieView.setCreateId(getEmployeeId());
            // childDieView.setPigId(weanView.getPigId());
            // childDieView.setLeaveQty(weanView.getDieNum());
            // childDieView.setEnterDate(weanView.getEnterDate());
            // childDieView.setWorker(getEmployeeId());
            // pigEventMapper.childPigDie(childDieView);
            // if (!"0".equals(childDieView.getErrorCode())) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
            // }
            // }
            // 断奶
            pigEventMapper.wean(weanView);
            if (!"0".equals(weanView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, weanView.getErrorMessage());
            }

            insertPigEventRelates(ppInputPig.getPigId());
        }
    }

    /**
     * @Description: 种猪转舍
     * @author zhangjs
     * @param ppInputPig
     * @throws Exception
     */
    private void editInputPigChangeHouse(PpInputPigModel ppInputPig, Date enterDate) throws Exception {

        // 转产房
        String eventName = EventConstants.BREED_PIG_CHANGE_HOUSE;
        // 创建工单
        long billId = editPigInputCreateBill(eventName, TimeUtil.getSysTimestamp());
        // 转舍
        ChangeHouseView changeHouseView = new ChangeHouseView();
        changeHouseView.setBillId(billId);
        changeHouseView.setFarmId(getFarmId());
        changeHouseView.setCompanyId(getCompanyId());
        changeHouseView.setEventName(eventName);
        changeHouseView.setCreateId(getEmployeeId());
        changeHouseView.setPigId(ppInputPig.getPigId());
        changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_OTH);
        // 转入舍
        changeHouseView.setInHouseId(ppInputPig.getHouseId());
        changeHouseView.setInPigpenId(ppInputPig.getPigpenId());
        changeHouseView.setEnterDate(enterDate);
        changeHouseView.setWorker(getEmployeeId());
        changeHouseView.setLineNumber(ppInputPig.getSortNbr());
        changeHouseView.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
        changeHouseView.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
        pigEventMapper.changeHouse(changeHouseView);

        if ("P04001".equals(changeHouseView.getErrorCode())) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
        }

        // 防止转舍转到原来猪舍，不抛异常
        // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
        if ("0".equals(changeHouseView.getErrorCode())) {
            insertPigEventRelates(ppInputPig.getPigId());
        }
    }

    @Override
    public BasePageInfo searchInputPig(String status) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addConditionWithNull(status, " AND STATUS = ? ");
        sql.addMainSql(" ORDER BY CREATE_DATE DESC");
        setToPage();
        List<PpInputPigModel> list = getList(ppInputPigMapper, sql);
        if (list.isEmpty()) {
            return getPageInfo();
        }
        for (PpInputPigModel model : list) {
            Map<String, Object> map = model.getData();
            map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
            map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
            map.put("pigpenName", CacheUtil.getName(Maps.getString(map, "pigpenId"), NameEnum.PIGPEN_NAME));
            map.put("pigTypeName", CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
            map.put("pregnancyResultName", CacheUtil.getName(Maps.getString(map, "pregnancyResult"), NameEnum.CODE_NAME, CodeEnum.PREGNANCY_RESULT));
            map.put("changeHouseName", CacheUtil.getName(Maps.getString(map, "changeHouseId"), NameEnum.HOUSE_NAME));
            map.put("changePigpenName", CacheUtil.getName(Maps.getString(map, "changePigpenId"), NameEnum.PIGPEN_NAME));
        }

        return getPageInfo(list);
    }

    @Override
    public void deleteInputPigs(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            ppInputPigMapper.deletes(ids);
        }
    }

    /**
     * @Description: 猪只入场插入事件关联表 根据BILL_ID插入,只是入场。
     * @author zhangjs
     * @param billId
     */
    private void insertPigMoveInRelates(Long billId) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT '1' AS STATUS, '0' AS deletedFlag, T1.FARM_ID AS farmId, T1.COMPANY_ID AS companyId, T1.SWINERY_ID, ");
        sqlCon.addMainSql(" T1.ROW_ID AS pigId, T1.LINE_ID AS lineId, T1.HOUSE_ID AS houseId, T1.PIGPEN_ID AS pigpenId");
        sqlCon.addMainSql(" , T2.ear_Brand, T1.PIG_TYPE AS pigType");
        sqlCon.addMainSql(", T1.SEX, T1.PIG_CLASS AS pigClass, T1.PARITY, T1.LAST_BILL_ID AS billId, T1.LAST_EVENT_DATE AS eventDate");
        sqlCon.addMainSql(" FROM PP_L_PIG T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T2 ON T2.ROW_ID=T1.EAR_CODE_ID AND T2.`FARM_ID`=T1.`FARM_ID` AND T2.`DELETED_FLAG`='0'");
        sqlCon.addMainSql(" WHERE T1.STATUS = '1' AND T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND T1.BILL_ID = ?");
        List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, sqlCon);
        if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
            pigEventRelatesMapper.inserts(pigEventRelatesModelList);
        }
    }

    /**
     * @Description: 插入事件关联表，为事件返销准备 根据PIG_ID操作
     * @author zhangjs
     * @param pigId
     */
    private void insertPigEventRelates(Long pigId) {
        SqlCon sqlCon = new SqlCon();

        sqlCon.addMainSql(" SELECT '1' AS STATUS, '0' AS deletedFlag, T1.FARM_ID AS farmId, T1.COMPANY_ID AS companyId, T1.SWINERY_ID, ");
        sqlCon.addMainSql(" T1.ROW_ID AS pigId, T1.LINE_ID AS lineId, T1.HOUSE_ID AS houseId, T1.PIGPEN_ID AS pigpenId");
        sqlCon.addMainSql(" , T2.ear_Brand, T1.PIG_TYPE AS pigType, T3.bill_Id AS lastBillId, T3.event_Date AS lastEventDate ");
        sqlCon.addMainSql(", T1.SEX, T1.PIG_CLASS AS pigClass, T1.PARITY, T1.LAST_BILL_ID AS billId, T1.LAST_EVENT_DATE AS eventDate");
        sqlCon.addMainSql(" FROM PP_L_PIG T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_EAR_CODE T2 ON T2.ROW_ID=T1.EAR_CODE_ID AND T2.`FARM_ID`=T1.`FARM_ID` AND T2.`DELETED_FLAG`='0'");
        sqlCon.addMainSql(" LEFT JOIN PP_L_PIG_EVENT_RELATES T3 ON T3.`PIG_ID`=T1.`ROW_ID` AND T3.`FARM_ID`=T1.`FARM_ID` AND T3.`DELETED_FLAG`='0'");
        sqlCon.addMainSql(" WHERE T1.STATUS = '1' AND T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
        sqlCon.addCondition(pigId, " AND T1.ROW_ID = ? ");
        sqlCon.addMainSql("  ORDER BY T3.`ROW_ID` DESC LIMIT 1 ");

        List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, sqlCon);
        if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
            pigEventRelatesMapper.inserts(pigEventRelatesModelList);
        }
    }

    @Override
    public void excuteInputPorker(List<Map> uploadData) throws Exception {
        if (uploadData.isEmpty()) {
            Thrower.throwException(InputPigException.HAS_NO_INPUT_PIG);
        }
        long index = 1;
        for (Map model : uploadData) {
            Date birthDate = TimeUtil.dateAddDay(Maps.getDate(model, "countDate"), -Maps.getInt(model, "dayAge"));

            // 业务编码
            String businessCode = ParamUtil.getBCode(EventConstants.PIG_MOVE_IN, getEmployeeId(), getCompanyId(), getFarmId());

            /* 1、创建工单记录 */
            BillModel billModel = new BillModel();
            billModel.setCompanyId(getCompanyId());
            billModel.setFarmId(getFarmId());
            billModel.setCreateId(getEmployeeId());
            billModel.setBusinessCode(businessCode);
            billModel.setEventCode(EventConstants.PIG_MOVE_IN);
            billModel.setStatus(PigConstants.STATUS_NOR);
            billModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
            // 创建表单
            billModel.setBillDate(Maps.getDate(model, "countDate"));
            billMapper.insert(billModel);
            // 物料主数据
            SqlCon sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(Maps.getLong(model, "materialId"), " AND MATERIAL_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            PorkPigModel cdPorkPigModel = getModel(porkPigMapper, sqlCon);

            Long breedId = cdPorkPigModel.getBreedId();
            String strain = cdPorkPigModel.getStrain();
            Double birthWeight = cdPorkPigModel.getBirthWeight();
            String bodyCondition = cdPorkPigModel.getBodyCondition();
            Long leftTeatNum = cdPorkPigModel.getLeftTeatNum();
            Long rightTeatNum = cdPorkPigModel.getRightTeatNum();
            String sex = PigConstants.PIG_SEX_MIX;

            for (int i = 0; i < Maps.getLong(model, "totalNum"); i++) {

                sapEventMasage(getFarmId(), Maps.getDate(model, "countDate"));

                PigMoveInView pigMoveInModel = new PigMoveInView();
                pigMoveInModel.setOptType(PigConstants.PIG_MOVE_IN_OPT_TYPE_FILE);
                pigMoveInModel.setLineNumber(index);
                pigMoveInModel.setBusinessCode(businessCode);
                pigMoveInModel.setOrigin(CommonConstants.ORIGIN_FLAG_INPUT);
                pigMoveInModel.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
                pigMoveInModel.setCompanyId(getCompanyId());
                pigMoveInModel.setFarmId(getFarmId());
                pigMoveInModel.setSwineryId(Maps.getLong(model, "swineryId"));
                pigMoveInModel.setHouseId(Maps.getLong(model, "houseId"));
                pigMoveInModel.setPigPenId(Maps.getLongClass(model, "pigpenId"));
                pigMoveInModel.setBoardSowId(0l);
                pigMoveInModel.setMaterialId(Maps.getLong(model, "materialId"));
                pigMoveInModel.setPigType(PigConstants.PIG_TYPE_PORK);
                pigMoveInModel.setSex(sex);
                pigMoveInModel.setBreedId(breedId);
                pigMoveInModel.setStrain(strain);
                pigMoveInModel.setPigClass(Maps.getLong(model, "pigClass"));
                pigMoveInModel.setBodyCondition(bodyCondition);
                pigMoveInModel.setParity(0l);
                pigMoveInModel.setBirthParity(0l);
                pigMoveInModel.setHouseNum(0l);
                pigMoveInModel.setBirthDate(birthDate);
                pigMoveInModel.setBirthWeight(birthWeight);
                pigMoveInModel.setEnterDate(Maps.getDate(model, "countDate"));
                pigMoveInModel.setEnterWeight(Maps.getDoubleClass(model, "avWeight") == null ? 0 : Maps.getLongClass(model, "avWeight"));
                pigMoveInModel.setOnPrice(Maps.getDoubleClass(model, "avPrice") == null ? 0 : Maps.getDoubleClass(model, "avPrice"));
                pigMoveInModel.setEnterParity(0l);
                pigMoveInModel.setMewDayAge(0l);
                pigMoveInModel.setMewWeight(0);
                pigMoveInModel.setLeftTeatNum(leftTeatNum);
                pigMoveInModel.setRightTeatNum(rightTeatNum);
                pigMoveInModel.setSupplierId(Maps.getLongClass(model, "supplierId"));
                pigMoveInModel.setBillId(billModel.getRowId());
                pigMoveInModel.setCreateId(getEmployeeId());
                pigMoveInModel.setEventName(EventConstants.PIG_MOVE_IN);
                pigMoveInModel.setWorker(getEmployeeId());
                pigEventMapper.pigMoveIn(pigMoveInModel);
                if (!"0".equals(pigMoveInModel.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
                }
            }

            index++;
            // 往PigEventRelates插入数据
            SqlCon insertPigEventRelatesSqlCon = new SqlCon();
            insertPigEventRelatesSqlCon.addMainSql(
                    "SELECT '1' AS status, 0 AS deletedFlag, FARM_ID AS farmId, COMPANY_ID AS companyId, SWINERY_ID AS swineryId, ROW_ID AS pigId, LINE_ID AS lineId, HOUSE_ID AS houseId, PIGPEN_ID AS pigpenId,(SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE STATUS = '1' AND DELETED_FLAG = '0' AND ROW_ID = EAR_CODE_ID) AS earBrand, PIG_TYPE AS pigType, SEX AS sex, PIG_CLASS AS pigClass, PARITY AS parity, BILL_ID AS billId,LAST_EVENT_DATE AS eventDate FROM PP_L_PIG");
            insertPigEventRelatesSqlCon.addMainSql(" WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            insertPigEventRelatesSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            insertPigEventRelatesSqlCon.addCondition(billModel.getRowId(), " AND BILL_ID = ? ");

            List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, insertPigEventRelatesSqlCon);

            if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
                pigEventRelatesMapper.inserts(pigEventRelatesModelList);
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
}
