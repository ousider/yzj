package xn.pigfarm.service.production.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.data.enums.PigInfoEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.production.BackfatMapper;
import xn.pigfarm.mapper.production.BillBreedMapper;
import xn.pigfarm.mapper.production.BoarObsoleteMapper;
import xn.pigfarm.mapper.production.ChildDieMapper;
import xn.pigfarm.mapper.production.DeliveryMapper;
import xn.pigfarm.mapper.production.EarCodeMapper;
import xn.pigfarm.mapper.production.FosterCareMapper;
import xn.pigfarm.mapper.production.LeaveMapper;
import xn.pigfarm.mapper.production.NurseMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.mapper.production.PigSaleDetailMapper;
import xn.pigfarm.mapper.production.PigSaleMapper;
import xn.pigfarm.mapper.production.PorkCheckMapper;
import xn.pigfarm.mapper.production.PregnancyCheckMapper;
import xn.pigfarm.mapper.production.SeedDetailMapper;
import xn.pigfarm.mapper.production.SelectBreedMapper;
import xn.pigfarm.mapper.production.SemenMapper;
import xn.pigfarm.mapper.production.SemenSaleMapper;
import xn.pigfarm.mapper.production.SpermInfoMapper;
import xn.pigfarm.mapper.production.ToChildMapper;
import xn.pigfarm.model.production.BackfatModel;
import xn.pigfarm.model.production.BillBreedModel;
import xn.pigfarm.model.production.BoarObsoleteModel;
import xn.pigfarm.model.production.ChildDieModel;
import xn.pigfarm.model.production.DeliveryModel;
import xn.pigfarm.model.production.EarCodeModel;
import xn.pigfarm.model.production.FosterCareModel;
import xn.pigfarm.model.production.LeaveModel;
import xn.pigfarm.model.production.NurseModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.PigSaleDetailModel;
import xn.pigfarm.model.production.PigSaleModel;
import xn.pigfarm.model.production.PigView;
import xn.pigfarm.model.production.PorkCheckModel;
import xn.pigfarm.model.production.PregnancyCheckModel;
import xn.pigfarm.model.production.SeedDetailModel;
import xn.pigfarm.model.production.SelectBreedModel;
import xn.pigfarm.model.production.SemenModel;
import xn.pigfarm.model.production.SpermInfoModel;
import xn.pigfarm.model.production.ToChildModel;
import xn.pigfarm.service.production.IBillViewService;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * @Description: 事件查询
 * @author Zhangjc
 * @date 2016年6月2日 下午2:19:50
 */
@Service("BillViewService")
public class BillViewServiceImpl extends BaseServiceImpl implements IBillViewService {

	@Autowired
	private PigMapper pigMapper;

	@Autowired
	private BillBreedMapper billBreedMapper;

	@Autowired
	private BackfatMapper backfatMapper;

	@Autowired
	private PregnancyCheckMapper pregnancyCheckMapper;

	@Autowired
	private DeliveryMapper deliveryMapper;

	@Autowired
	private FosterCareMapper fosterCareMapper;

	@Autowired
	private LeaveMapper leaveMapper;

	@Autowired
	private ChildDieMapper childDieMapper;

	@Autowired
	private ToChildMapper toChildMapper;

	@Autowired
	private BoarObsoleteMapper boarObsoleteMapper;

	@Autowired
	private PigSaleMapper pigSaleMapper;

	@Autowired
	private PigSaleDetailMapper pigSaleDetailMapper;

	@Autowired
    private IParamMapper paramMapper;

    @Autowired
    private NurseMapper nurseMapper;

    @Autowired
    private SelectBreedMapper selectBreedMapper;

    @Autowired
    private SeedDetailMapper seedDetailMapper;

    @Autowired
    private PorkCheckMapper porkCheckMapper;

    @Autowired
    private SemenMapper semenMapper;

    @Autowired
    private SemenSaleMapper semenSaleMapper;

    @Autowired
    private SpermInfoMapper spermInfoMapper;

    @Autowired
    private EarCodeMapper earCodeMapper;

    /**
     * @Description: 根据sql操作数据库
     * @author Zhangjc
     * @param sql
     * @return
     */
	private List<Map<String, Object>> setSql(String sql) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		return paramMapper.getObjectInfos(map);
	}

	@Override
    public List<Map<String, Object>> searchDetailBillToList(Long cancelBillId, String eventCode, Long billId, String oldEventCode)
			throws Exception {
        // 猪只入场
		if (EventConstants.PIG_MOVE_IN.equals(eventCode)) {
            return searchPigMoveIn(billId);
		}
        // 更换耳号
		else if (EventConstants.CHANGE_EAR_BAND.equals(eventCode)) {
            return  searchChangeEarBand(billId);
		}
        // 背膘测定
		else if (EventConstants.BACKFAT.equals(eventCode)) {
            return  searchBackfat(billId);
		}
        // 后备转生产
		else if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventCode)
				|| EventConstants.PREPUBERTAL_CHECK.equals(eventCode)) {
            return  searchToProduct(billId);
		}
        // 采精
		else if (EventConstants.SEMEN.equals(eventCode)) {
            return  searchSemen(billId);
		}
        // 转产房
		else if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventCode)
				|| EventConstants.BREED_PIG_CHANGE_HOUSE.equals(eventCode)) {
            return  searchChangeLaborHouse(billId);
		}
        // 配种
		else if (EventConstants.BREED.equals(eventCode)) {
            return  searchBreed(billId);
		}
        // 妊娠检查 流产
		else if (EventConstants.PREGNANCY_CHECK.equals(eventCode) || EventConstants.MISSCARRY.equals(eventCode)) {
            return  searchPregnancyCheck(billId);
		}
        // 分娩
		else if (EventConstants.DELIVERY.equals(eventCode)) {
            return  searchDelivery(billId);
		}
        // 寄养
		else if (EventConstants.FOSTER.equals(eventCode)) {
            return  searchFoster(billId);
		}
        // 断奶
		else if (EventConstants.WEAN.equals(eventCode)) {
            return  searchWean(billId);
		}
        // 种猪淘汰
		else if (EventConstants.BREED_PIG_OBSOLETE.equals(eventCode)) {
            return searchBreedPigObsolete(billId);
		}
        // 种猪死亡
		else if (EventConstants.BREED_PIG_DIE.equals(eventCode)) {
            return  searchBreedPigDie(billId);
		}
        // 仔猪死亡 商品死亡
		else if (EventConstants.CHILD_PIG_DIE.equals(eventCode) || EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
            return  searchChildPigDie(billId, CodeEnum.GOOD_DIE_REASON, eventCode);
		}
        // 猪只销售
		else if (EventConstants.GOOD_PIG_SELL.equals(eventCode)) {
            return  searchGoodPigSell(billId);
		}
        // 转保育
		else if (EventConstants.TO_CHILD_CARE.equals(eventCode)) {
            return  searchToChildMapper(billId, CodeEnum.CHILD_CARE_CHANGE_TYPE);
		}
        // 转育肥
		else if (EventConstants.TO_CHILD_FATTEN.equals(eventCode)) {
            return  searchToChildMapper(billId, CodeEnum.FATTEN_CHANGE_TYPE);
        }
        // 奶妈转舍
        else if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventCode)) {
            return  searchNurseChangeHouse(billId);
        }
        // 单据撤销
        else if (EventConstants.BILL_CANCEL.equals(eventCode)) {
            return searchBillCancel(billId, oldEventCode, cancelBillId);
            // 猪只事件撤销
		} else if (EventConstants.PIG_EVENT_CANCEL.equals(eventCode)) {
            return searchPigEventCancel(billId, oldEventCode, cancelBillId);
            // 单条记录撤销
		} else if (EventConstants.EACH_RECORD_CANCEL.endsWith(eventCode)) {
            return searchEachRecordCancel(billId, oldEventCode, cancelBillId);
            // 留种
        } else if (EventConstants.SEED_PIG.equals(eventCode)) {
            return  searchSeedPig(billId);
            // 选种
        } else if (EventConstants.SELECT_BREED_PIG.equals(eventCode)) {
            return  selectBreedPig(billId);
            // 肉猪盘点
        } else if (EventConstants.PORK_PIG_CHECK.equals(eventCode)) {
            return  searchPorkPigCheck(billId);
            // 精液入库
        } else if (EventConstants.SEMEN_INQUIRY.equals(eventCode)) {
            return searchSemenInquiry(billId);
            // 精液销售
        } else if (EventConstants.SALE_SEMEN.equals(eventCode)) {
            return searchSaleSemen(billId);
		}
        return null;
	}

    /**
     * @Description: 留种
     * @author yangy
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> searchSeedPig(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<SeedDetailModel> list = getList(seedDetailMapper, sql);
        List<Map<String, Object>> result = new ArrayList<>();
        List<Long> boardSowIds = new ArrayList<Long>();
        if (list != null && !list.isEmpty()) {
            for (SeedDetailModel seedDetailModel : list) {
                Long boardSowId = seedDetailModel.getSowId();
                boardSowIds.add(boardSowId);
            }
            String boardSowIdsString = StringUtil.listLongToString(boardSowIds);
            SqlCon sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(boardSowIdsString, " AND ROW_ID IN ? ", false, true);
            // 母猪信息
            List<PigModel> pigModels = getList(pigMapper, sqlCon);
            for (PigModel pigModel : pigModels) {
                int seedMale = 0;
                int seedFemale = 0;
                Map<String, Object> map = pigModel.getData();
                Map<String, String> cacheMap = CacheUtil.getPigInfo(String.valueOf(pigModel.getRowId()),
                        xn.pigfarm.util.enums.PigInfoEnum.PIG_INFO_WITHID);
                map.put("earBrand", cacheMap.get("earBrand"));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("pigpenName", CacheUtil.getName(Maps.getString(map, "pigpenId"), NameEnum.PIGPEN_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                for (SeedDetailModel seedDetailModel : list) {
                    if (seedDetailModel.getSowId().equals(pigModel.getRowId())) {
                        map.put("createDate", TimeUtil.format(seedDetailModel.getCreateDate(), TimeUtil.DATE_FORMAT));
                        map.put("seedDate", TimeUtil.format(seedDetailModel.getSeedDate(), TimeUtil.DATE_FORMAT));
                        if (PigConstants.PIG_SEX_MALE.equals(seedDetailModel.getSex())) {
                            seedMale++;
                        } else if (PigConstants.PIG_SEX_FEMALE.equals(seedDetailModel.getSex())) {
                            seedFemale++;
                        }
                    }
                }
                map.put("seedMale", seedMale);
                map.put("seedFemale", seedFemale);
                result.add(map);
            }
        }
        return result;
    }

    /**
     * @Description: 选种
     * @author yangy
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> selectBreedPig(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<SelectBreedModel> selectBreedModels = getList(selectBreedMapper, sql);
        List<Map<String, Object>> result = new ArrayList<>();
        for (SelectBreedModel selectBreedModel : selectBreedModels) {
            Map<String, Object> map = selectBreedModel.getData();
            Map<String, String> cacheMap = CacheUtil.getPigInfo(Maps.getString(map, "pigId"),
                    xn.pigfarm.util.enums.PigInfoEnum.PIG_INFO_WITHID);
            map.put("earShort", cacheMap.get("earShort"));
            map.put("selectBreedTypeName", (PigConstants.PIG_SEX_MALE).equals(Maps.getString(map, "pigType")) ? PigConstants.SELECT_BREED_MALE_TYPE
                    : PigConstants.SELECT_BREED_FEMALE_TYPE);
            map.put("sexName", PigConstants.PIG_SEX_MALE.equals(Maps.getString(map, "pigType")) ? "公" : "母");
            map.put("inHouseIdName", CacheUtil.getName(Maps.getString(map, "inHouseId"), NameEnum.HOUSE_NAME));
            map.put("inPigpenIdName", CacheUtil.getName(Maps.getString(map, "inPigpenId"), NameEnum.PIGPEN_NAME));
            map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
            result.add(map);
        }
        return result;
    }

    /**
     * @Description: 肉猪盘点
     * @author yangy
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> searchPorkPigCheck(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<PorkCheckModel> porkCheckModels = getList(porkCheckMapper, sql);
        List<Map<String, Object>> result = new ArrayList<>();
        for (PorkCheckModel porkCheckModel : porkCheckModels) {
            Map<String, Object> map = porkCheckModel.getData();
            long houseId = (long) map.get("houseId");
            long swineryId = (long) map.get("swineryId");
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(houseId, " AND HOUSE_ID=? ");
            sqlCon.addCondition(swineryId, " AND SWINERY_ID=? ");
            PigModel pigModel = getList(pigMapper, sqlCon).get(0);
            if (pigModel != null) {
                Map<String, String> cacheMap = CacheUtil.getPigInfo(String.valueOf(pigModel.getRowId()),
                        xn.pigfarm.util.enums.PigInfoEnum.PIG_INFO_WITHID);
                map.put("houseName", CacheUtil.getName(String.valueOf(houseId), NameEnum.HOUSE_NAME));
                map.put("swineryName", CacheUtil.getName(String.valueOf(swineryId), NameEnum.SWINERY_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                result.add(map);
            }
            }
        return result;
    }

    /**
     * @Description: 查询猪只入场信息
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> searchPigMoveIn(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND (BILL_ID= ?");
        sql.addConditionWithNull(billId, " OR LAST_BILL_ID = ?)");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<PigModel> list = getList(pigMapper, sql);
        
        List<Map<String, Object>> result = new ArrayList<>();

        if (list != null && !list.isEmpty()) {
            for (PigModel model : list) {
                Map<String, Object> map = model.getData();
                map.put("pigId", model.getRowId());
				if (PigConstants.PIG_TYPE_PORK.equals(model.getPigType())) {
					Map<String, String> sqlMap = new HashMap<>();
                    sqlMap.put("condition", " AND A.ROW_ID = " + model.getRowId());
					List<PigView> listByCon2 = pigMapper.searchListByCon2(sqlMap);
                    map.put("earBrand", listByCon2.get(0).getEarBrand());
				} else {
                    map.putAll(CacheUtil.getPigInfo(String.valueOf(model.getRowId()), PigInfoEnum.EAR_BRAND));
				}
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("pigClassName", CacheUtil.getName(Maps.getString(map, "enterPigClass"), NameEnum.PIG_CLASS_NAME));
                map.put("pigTypeName", CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
                map.put("breedName", CacheUtil.getName(Maps.getString(map, "breedId"), NameEnum.BREED_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                result.add(map);
			}
		}
        return result;
	}

    /**
     * @Description: 根据耳号信息
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchChangeEarBand(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addMainSql(
                " SELECT T.PIG_ID pigId,T2.HOUSE_ID houseId,T1.EAR_BRAND earBrand,T1.EAR_SHORT earShort,T1.EAR_THORN earThorn,T1.ELECTRONIC_EAR_NO electronicEarNo,");
		sql.addMainSql(
				" T.SWINERY_ID swineryId,T.WORKER worker,T.CHANGE_EARBRAND_DATE changeEarbrandDate,T.CREATE_DATE createDate,");
		sql.addMainSql(
				" T.CREATE_ID createId,T.EAR_BRAND earBrandNew ,T.EAR_SHORT earShortNew,T.EAR_THORN earThornNew,");
		sql.addMainSql(" T.ELECTRONIC_EAR_NO electronicEarNoNew ");
		sql.addMainSql(" FROM PP_L_BILL_CHANGE_EARBRAND T");
		sql.addMainSql(" INNER JOIN PP_L_BILL_CHANGE_EARBRAND T1 ON T.CHANGE_EARBRAND_ID=T1.ROW_ID");
        sql.addMainSql(" INNER JOIN PP_L_PIG T2 ON T.PIG_ID=T2.ROW_ID");
		sql.addMainSql(" WHERE T.DELETED_FLAG='0' AND T.STATUS = '1' AND T1.DELETED_FLAG='0' AND T1.STATUS = '1'");
		sql.addMainSql(" AND T.BILL_ID=" + billId);
        sql.addMainSql(" AND T.FARM_ID=" + getFarmId());
        List<Map<String, Object>> result = setSql(sql.getCondition());
		if (result != null && result.size() > 0) {
			Map<String, Object> map = null;
			for (int i = 0; i < result.size(); i++) {
				map = result.get(i);
				String createDate = Maps.getString(map, "createDate");
				if (StringUtil.isNonBlank(createDate)) {
					createDate = TimeUtil.format(createDate, TimeUtil.DATE_FORMAT);
				}
				map.put("createDate", createDate);
				map.put(NameEnum.SWINERY_NAME.getName(),
 CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
				map.put(NameEnum.CREATE_NAME.getName(),
 CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
				map.put(NameEnum.WORKER_NAME.getName(),
 CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
			}
		}
		return result;
	}

    /**
     * @Description: 背膘测定
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchBackfat(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addConditionWithNull(billId, " AND BILL_ID= ?");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
		List<BackfatModel> list = getList(backfatMapper, sql);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (BackfatModel model : list) {
				Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
				// map.put("materialName", CacheUtil.getName(Maps.getString(map,
                // "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
				result.add(map);
			}
		}
		return result;
	}

    /**
     * @Description: 后备转生产
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchToProduct(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addMainSql(
				" SELECT T.PIG_ID pigId,T.CREATE_ID createId,T.CREATE_DATE createDate,T.HOUSE_ID houseId,T.LINE_ID lineId,T.NOTES notes,");
		sql.addMainSql(" T.WORKER worker,T.PRODUCT_DATE productDate,T.PIGPEN_ID pigpenId,T.SWINERY_ID swineryId,");
		sql.addMainSql(
				" T2.EAR_BRAND earBrand,T2.EAR_SHORT earShort,T2.EAR_THORN earThorn,T2.ELECTRONIC_EAR_NO electronicEarNo,");
		sql.addMainSql(" T1.EAR_BRAND earBrandNew ,T1.EAR_SHORT earShortNew,T1.EAR_THORN earThornNew,");
		sql.addMainSql(" T1.ELECTRONIC_EAR_NO electronicEarNoNew");
		sql.addMainSql(" FROM PP_L_BILL_RESERVE_PRODUCT T");
		sql.addMainSql(
				" LEFT JOIN PP_L_BILL_CHANGE_EARBRAND T1 ON T.PIG_ID=T1.PIG_ID AND T.BILL_ID=T1.BILL_ID AND T1.DELETED_FLAG='0' AND T1.STATUS = '1'");
		sql.addMainSql(
				" LEFT JOIN PP_L_BILL_CHANGE_EARBRAND T2 ON T1.CHANGE_EARBRAND_ID=T2.ROW_ID AND T1.DELETED_FLAG='0' AND T1.STATUS = '1'");
		sql.addMainSql(" WHERE T.DELETED_FLAG='0' AND T.STATUS = '1'");
		sql.addConditionWithNull(billId, " AND T.BILL_ID= ?");
		sql.addConditionWithNull(getFarmId(), " AND T.FARM_ID= ?");
        List<Map<String, Object>> result = setSql(sql.getCondition());

		if (result != null && result.size() > 0) {
			for (Map<String, Object> map : result) {

                // 如果earBrand无值，则说明未换耳号
				String earBrand = Maps.getString(map, "earBrand");
				Map<String, String> pigInfo = CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW);
				if (StringUtil.isBlank(earBrand)) {
					map.putAll(pigInfo);
				}
                // 格式化时间
				String createDate = Maps.getString(map, "createDate");
				if (StringUtil.isNonBlank(createDate)) {
					createDate = TimeUtil.format(createDate, TimeUtil.DATE_FORMAT);
				}
				map.put("createDate", createDate);

				String productDate = Maps.getString(map, "productDate");
				if (StringUtil.isNonBlank(productDate)) {
					productDate = TimeUtil.format(productDate, TimeUtil.DATE_FORMAT);
				}
				map.put("productDate", productDate);

                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(pigInfo, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
			}
		}
		return result;
	}

    /**
     * @Description: 采精
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> searchSemen(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<SemenModel> list = getList(semenMapper, sql);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (SemenModel model : list) {
                Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("semenColorName", CacheUtil.getName(Maps.getString(map, "color"), NameEnum.CODE_NAME, CodeEnum.SEMEN_COLOR));
                map.put("semenOdourName", CacheUtil.getName(Maps.getString(map, "odour"), NameEnum.CODE_NAME, CodeEnum.SEMEN_ODOUR));
                map.put("cohesionName", CacheUtil.getName(Maps.getString(map, "cohesion"), NameEnum.CODE_NAME, CodeEnum.COHESION));
                result.add(map);
            }
        }
        return result;
    }

    /**
     * @Description: 转产房
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchChangeLaborHouse(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addMainSql(
				" SELECT T.PIG_ID pigId,T.CREATE_ID createId,T.CREATE_DATE createDate,T.HOUSE_ID houseId,T.LINE_ID lineId,T.NOTES notes,");
		sql.addMainSql(
				" T.WORKER worker,T.PIG_CLASS pigClass,T.CHANGE_HOUSE_DATE changeHouseDate,T.PIGPEN_ID pigpenId,T.SWINERY_ID swineryId, ");
		sql.addMainSql(" T1.BACKFAT backfat,T1.SCORE score,T.CHANGE_TYPE changeType");
		sql.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T");
		sql.addMainSql(
				" LEFT JOIN PP_L_BILL_BACKFAT T1 ON T.PIG_ID=T1.PIG_ID AND T.BILL_ID=T1.BILL_ID AND T1.DELETED_FLAG='0' AND T1.STATUS = '1'");
		sql.addMainSql(" WHERE T.DELETED_FLAG='0' AND T.STATUS = '1'");
        sql.addCondition(billId, " AND T.BILL_ID = ?");
        sql.addConditionWithNull(getFarmId(), " AND T.FARM_ID = ?");
        // 修改种猪转舍事件查看查看不了公猪 修改人：汤鸿梁 修改日期：2016-10-09 --begin
        sql.addCondition(PigConstants.PIG_TYPE_BOAR, " AND (T.PIG_TYPE = ?");
        sql.addCondition(PigConstants.PIG_TYPE_SOW, " OR T.PIG_TYPE = ?)");
        // 修改种猪转舍事件查看查看不了公猪 修改人：汤鸿梁 修改日期：2016-10-09 --end
        List<Map<String, Object>> result = setSql(sql.getCondition());

		if (result != null && result.size() > 0) {
			for (Map<String, Object> map : result) {

                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                // 格式化时间
				String createDate = Maps.getString(map, "createDate");
				if (StringUtil.isNonBlank(createDate)) {
					createDate = TimeUtil.format(createDate, TimeUtil.DATE_FORMAT);
				}
				map.put("createDate", createDate);

				String changeHouseDate = Maps.getString(map, "changeHouseDate");
				if (StringUtil.isNonBlank(changeHouseDate)) {
					changeHouseDate = TimeUtil.format(changeHouseDate, TimeUtil.DATE_FORMAT);
				}
				map.put("changeHouseDate", changeHouseDate);

                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("pigpenName", CacheUtil.getName(Maps.getString(map, "pigpenId"), NameEnum.PIGPEN_NAME));
                map.put("pigClassName", CacheUtil.getName(Maps.getString(map, "pigClass"), NameEnum.PIG_CLASS_NAME));
				map.put("changeTypeName", CacheUtil.getName(Maps.getString(map, "changeType"), NameEnum.CODE_NAME,
 CodeEnum.LABER_CHANGE_TYPE));
			}
		}
		return result;
	}

    /**
     * @Description: 配种
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> searchBreed(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<BillBreedModel> list = getList(billBreedMapper, sql);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (BillBreedModel model : list) {
                Map<String, Object> map = model.getData();
                PigModel pigModel = pigMapper.searchById(Maps.getLong(map, "pigId"));
                EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
                map.put("earBrand", earCodeModel.getEarBrand());
                map.put("swineryId", pigModel.getSwineryId());
                map.put("materialId", pigModel.getMaterialId());
                map.put("houseId", pigModel.getHouseId());
                SpermInfoModel spermInfoModel01 = spermInfoMapper.searchById(model.getBreedBoarFirstSpermRowId());
                SemenModel semenModel01 = semenMapper.searchById(spermInfoModel01.getSemenId());
                map.put("breedBoarFirst", semenModel01.getEarBrand());
                if(model.getBreedBoarSecondSpermRowId() != null){
                    SpermInfoModel spermInfoModel02 = spermInfoMapper.searchById(model.getBreedBoarSecondSpermRowId());
                    SemenModel semenModel02 = semenMapper.searchById(spermInfoModel02.getSemenId());
                    map.put("breedBoarSecond", semenModel02.getEarBrand());
                }
                if (model.getBreedBoarThirdSpermRowId() != null) {
                    SpermInfoModel spermInfoModel03 = spermInfoMapper.searchById(model.getBreedBoarThirdSpermRowId());
                    SemenModel semenModel03 = semenMapper.searchById(spermInfoModel03.getSemenId());
                    map.put("breedBoarThird", semenModel03.getEarBrand());
                }
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("breedTypeName", CacheUtil.getName(Maps.getString(map, "breedType"), NameEnum.CODE_NAME, CodeEnum.BREED_TYPE));
                result.add(map);
            }
        }
        return result;
    }

    /**
     * @Description: 妊娠检查
     * @author Zhangjc
     * @param billId
     * @return
     */
    private List<Map<String, Object>> searchPregnancyCheck(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<PregnancyCheckModel> list = getList(pregnancyCheckMapper, sql);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (PregnancyCheckModel model : list) {
                Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("pregnancyResultName", CacheUtil.getName(Maps.getString(map, "pregnancyResult"), NameEnum.CODE_NAME,
                        CodeEnum.PREGNANCY_RESULT));
                map.put("pregnancyTypeName", CacheUtil.getName(Maps.getString(map, "pregnancyType"), NameEnum.CODE_NAME, CodeEnum.PREGNANCY_TYPE));
                result.add(map);
            }
        }
        return result;
    }

    /**
     * @Description: 分娩
     * @author Zhangjc
     * @param billId
     * @return
     */
	private List<Map<String, Object>> searchDelivery(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addConditionWithNull(billId, " AND BILL_ID= ?");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
		List<DeliveryModel> list = getList(deliveryMapper, sql);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (DeliveryModel model : list) {
				Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                String deliveryTypeName = Maps.getString(map, "deliveryType", "").equalsIgnoreCase("Y") ? "是" : "否";
				map.put("deliveryTypeName", deliveryTypeName);
				result.add(map);
			}
		}
		return result;
	}

    /**
     * @Description: 寄养
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchFoster(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<FosterCareModel> list = getList(fosterCareMapper, sql);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (FosterCareModel model : list) {
                Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("sexName", CacheUtil.getName(Maps.getString(map, "sex"), NameEnum.CODE_NAME, CodeEnum.PIG_SEX_NAME));
                map.put("pigTypeName", CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
                map.put("fosterReasonName", CacheUtil.getName(Maps.getString(map, "fosterReason"), NameEnum.CODE_NAME, CodeEnum.FOSTER_REASON));
                // 代养母猪耳牌号
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "boardSowId"), PigInfoEnum.EAR_BRAND, "earBrandFoster"));
                result.add(map);
            }
        }
        return result;
    }

    /**
     * @Description: 断奶
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchWean(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addMainSql(
				" SELECT T.PIG_ID pigId,T.CREATE_ID createId,T.CREATE_DATE createDate,T.HOUSE_ID houseId,T.PIGPEN_ID pigpenId,");
		sql.addMainSql(" T.LINE_ID lineId,T.NOTES notes,T.WORKER worker,T.SWINERY_ID swineryId,T.PRO_NO proNo,");
		sql.addMainSql(
				" T.PARITY parity,T.WEAN_DATE weanDate,T.WEAN_NUM weanNum,T.WEAN_WEIGHT weanWeight,T1.LEAVE_QTY leaveQty,");
		sql.addMainSql(" T2.BACKFAT backfat,T2.SCORE score,T3.HOUSE_ID inHouseId,T3.PIGPEN_ID  inPigpenId");
		sql.addMainSql(" FROM PP_L_BILL_WEAN T");
		sql.addMainSql(
				" LEFT JOIN PP_L_BILL_CHILD_DIE T1 ON T.PIG_ID=T1.PIG_ID AND T.BILL_ID=T1.BILL_ID AND T1.DELETED_FLAG='0' AND T1.STATUS = '1'");
		sql.addMainSql(" LEFT JOIN PP_L_BILL_BACKFAT T2 ON T.PIG_ID=T2.PIG_ID AND T.BILL_ID=T2.BILL_ID ");
		sql.addMainSql(" AND T2.DELETED_FLAG='0' AND T2.STATUS = '1'");
		sql.addMainSql(" LEFT JOIN PP_L_BILL_CHANGE_HOUSE T3 ON T.PIG_ID=T3.PIG_ID AND T.BILL_ID=T3.BILL_ID ");
		sql.addMainSql(" AND T3.DELETED_FLAG='0' AND T3.STATUS = '1'");
		sql.addMainSql(" WHERE T.DELETED_FLAG='0' AND T.STATUS = '1'");
		sql.addMainSql(" AND T.BILL_ID=" + billId);
        sql.addMainSql(" AND T.FARM_ID=" + getFarmId());
        List<Map<String, Object>> result = setSql(sql.getCondition());

		if (result != null && result.size() > 0) {
			for (Map<String, Object> map : result) {

                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                // 格式化时间
				String createDate = Maps.getString(map, "createDate");
				if (StringUtil.isNonBlank(createDate)) {
					createDate = TimeUtil.format(createDate, TimeUtil.DATE_FORMAT);
				}
				map.put("createDate", createDate);

				String weanDate = Maps.getString(map, "weanDate");
				if (StringUtil.isNonBlank(weanDate)) {
					weanDate = TimeUtil.format(weanDate, TimeUtil.DATE_FORMAT);
				}
				map.put("weanDate", weanDate);

                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
                map.put("pigpenName", CacheUtil.getName(Maps.getString(map, "pigpenId"), NameEnum.PIGPEN_NAME));
                map.put("inHouseName", CacheUtil.getName(Maps.getString(map, "inHouseId"), NameEnum.HOUSE_NAME));
                map.put("inPigpenName", CacheUtil.getName(Maps.getString(map, "inPigpenId"), NameEnum.PIGPEN_NAME));
			}
		}
		return result;
	}

    /**
     * @Description: 种猪淘汰
     * @author WCH
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchBreedPigObsolete(Long billId) throws Exception {
		SqlCon sql = new SqlCon();
		sql.addMainSql(" SELECT * FROM pp_l_bill_boar_obsolete ");
		sql.addMainSql(" WHERE DELETED_FLAG = 0 AND STATUS IN (1,2,3,4) ");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
		sql.addCondition(billId, " AND BILL_ID = ? ");

		List<BoarObsoleteModel> boarList = setSql(boarObsoleteMapper, sql);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (BoarObsoleteModel boarModel : boarList) {
			Map<String, String> cacheMap = CacheUtil.getPigInfo(String.valueOf(boarModel.getPigId()),
					xn.pigfarm.util.enums.PigInfoEnum.PIG_INFO_WITHID);
			Map<String, Object> map = boarModel.getData();
            map.put("houseName", cacheMap.get("houseName"));
            map.put("sexName", CacheUtil.getName(cacheMap.get("sex"), NameEnum.CODE_NAME, CodeEnum.PIG_SEX_NAME));
            map.put("pigpenName", cacheMap.get("pigpenName"));
            map.put("parity", cacheMap.get("parity"));
            map.put("earBrand", cacheMap.get("earBrand"));
            map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
            map.put("pigTypeName", CacheUtil.getName(String.valueOf(cacheMap.get("pigType")), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
            map.put("obsoleteReasonName", CacheUtil.getName(boarModel.getObsoleteReason(), NameEnum.CODE_NAME, CodeEnum.OBSOLETE_REASON));
            map.put("workerName", CacheUtil.getName(String.valueOf(boarModel.getWorker()), NameEnum.WORKER_NAME));
			if ("1".equals(boarModel.getStatus())) {
                map.put("statusName", "审批中");
			} else if ("2".equals(boarModel.getStatus())) {
                map.put("statusName", "审批通过");
			} else if ("3".equals(boarModel.getStatus())) {
                map.put("statusName", "审批未通过");
			} else if ("4".equals(boarModel.getStatus())) {
                map.put("statusName", "已被销售");
			}

			result.add(map);
		}

		return result;
	}

    /**
     * @Description: 种猪死亡
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchBreedPigDie(Long billId) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addConditionWithNull(billId, " AND BILL_ID= ?");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
		List<LeaveModel> list = getList(leaveMapper, sql);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (LeaveModel model : list) {
				Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
				map.put("sexName",
 CacheUtil.getName(Maps.getString(map, "sex"), NameEnum.CODE_NAME, CodeEnum.PIG_SEX_NAME));
				map.put("pigTypeName",
 CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
				map.put("dieReasonName", CacheUtil.getName(Maps.getString(map, "leaveReason"), NameEnum.CODE_NAME,
 CodeEnum.BOAR_DIE_REASON));
				result.add(map);
			}
		}
		return result;
	}

    /**
     * @Description: 仔猪死亡 商品死亡
     * @author Zhangjc
     * @param billId
     * @param eventCode
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchChildPigDie(Long billId, CodeEnum reasonCode, String eventCode)
			throws Exception {

		SqlCon sql = new SqlCon();
		sql.addConditionWithNull(billId, " AND BILL_ID= ?");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
		List<ChildDieModel> list = getList(childDieMapper, sql);
		List<LeaveModel> leaveList = null;
		if (EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
			leaveList = getList(leaveMapper, sql);
		}

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		if (list != null && list.size() > 0) {
			for (ChildDieModel model : list) {
				Map<String, Object> map = model.getData();
                map.putAll(CacheUtil.getPigInfo(Maps.getString(map, "pigId") + "", PigInfoEnum.EAR_BRAND));
                map.put("swineryName", CacheUtil.getName(Maps.getString(map, "inSwineryId"), NameEnum.SWINERY_NAME));
                map.put("materialName", CacheUtil.getName(Maps.getString(map, "materialId"), NameEnum.MATERIAL_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("houseName", CacheUtil.getName(Maps.getString(map, "inHouseId"), NameEnum.HOUSE_NAME));
                map.put("pigpenName", CacheUtil.getName(Maps.getString(map, "pigpenId"), NameEnum.PIGPEN_NAME));
                map.put("sexName", CacheUtil.getName(Maps.getString(map, "sex"), NameEnum.CODE_NAME, CodeEnum.PIG_SEX_NAME));
                map.put("pigTypeName", CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
                map.put("customerName", CacheUtil.getName(Maps.getString(map, "customerId"), NameEnum.COMPANY_NAME));
                map.put("leaveReasonName", CacheUtil.getName(Maps.getString(map, "leaveReason"), NameEnum.CODE_NAME, reasonCode));
				if (EventConstants.GOOD_PIG_DIE.equals(eventCode)) {
					List<Long> pigIds = new ArrayList<Long>();
					if (leaveList != null && !leaveList.isEmpty()) {
						for (int i = 0; i < leaveList.size(); i++) {
							if (Maps.getLongClass(map, "lineNumber").compareTo(leaveList.get(i).getLineNumber()) == 0) {
                                pigIds.add(leaveList.get(i).getPigId());
							}
						}
					}
					map.put("pigIds", pigIds);
				}
				result.add(map);
			}
		}
		return result;
	}

    /**
     * @Description: 猪只销售
     * @author WCH
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchGoodPigSell(Long billId) throws Exception {
		SqlCon sql = new SqlCon();
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
		sql.addCondition(billId, " AND BILL_ID = ? ");
		List<PigSaleDetailModel> sdlist = getList(pigSaleDetailMapper, sql);

		SqlCon sql1 = new SqlCon();
		sql1.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
		sql1.addCondition(billId, " AND BILL_ID = ? ");
		List<PigSaleModel> slist = getList(pigSaleMapper, sql1);
		long customerId = slist.get(0).getCustomerId();
		String saleType = slist.get(0).getSaleType();
		String saleBillType = CacheUtil.getName(String.valueOf(slist.get(0).getSaleBillType()), NameEnum.CODE_NAME,CodeEnum.SALE_BILL_TYPE);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (PigSaleDetailModel pigSaleDetailModel : sdlist) {
			Map<String, Object> map = pigSaleDetailModel.getData();
            map.put("pigpenName", CacheUtil.getName(Maps.getString(map, "pigpenId"), NameEnum.PIGPEN_NAME));
            map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
			map.put("workerName",CacheUtil.getName(Maps.getString(map,"worker"), NameEnum.WORKER_NAME));
			map.put("saleName",CacheUtil.getName(Maps.getString(map, "saleDescribe"), NameEnum.CODE_NAME, CodeEnum.SELL_GOOD));
            map.put("leaveReasonName", CacheUtil.getName(saleType, NameEnum.CODE_NAME, CodeEnum.SELL_TYPE));
			map.put("swineryName",CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
			map.put("houseName", CacheUtil.getName(Maps.getString(map, "houseId"), NameEnum.HOUSE_NAME));
            map.put("customerName", CacheUtil.getName(String.valueOf(customerId), NameEnum.COMPANY_NAME));
            map.put("saleBillTypeName",saleBillType);
			result.add(map);
		}

		return result;
	}

    /**
     * @Description:转保育 转育肥
     * @author Zhangjc
     * @param billId
     * @return
     * @throws Exception
     */
	private List<Map<String, Object>> searchToChildMapper(Long billId, CodeEnum codeEnum) throws Exception {

		SqlCon sql = new SqlCon();
		sql.addConditionWithNull(billId, " AND BILL_ID= ?");
		sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
		List<ToChildModel> list = getList(toChildMapper, sql);

		List<Map<String, Object>> result = new ArrayList<>();
        // 用于去掉重复行
		Set<Long> lineNumber = new HashSet<Long>();

		if (list != null && list.size() > 0) {
			for (ToChildModel model : list) {
				if (!lineNumber.contains(model.getLineNumber())) {
                    lineNumber.add(model.getLineNumber());
					Map<String, Object> map = new HashMap<String, Object>();
					List<Long> pigIds = new ArrayList<Long>();
                    pigIds.add(model.getPigId());
					map.put("pigIds", pigIds);
                    map.put("childQty", model.getChildQty());
                    /**
                     * 修改 原因: 事件查看 转育肥,保育 肉猪重量 统计重量错误 ****需改人:程彬
                     * 修改时间:2016-10-8 ************************ begin
                     */
                    // 原代码
					// map.put("childWeight", model.getChildWeight() *
                    // model.getChildQty());
                    Double childQty = 0d;
					for (ToChildModel model2 : list) {
						if (model.getLineNumber() == model2.getLineNumber()) {
						    childQty += (model2.getChildWeight() * 100);
						}
					}
					map.put("childWeight", childQty / 100);
                    /**
                     * 修改 原因: 事件查看 转育肥,保育 肉猪重量 统计重量错误 ****需改人:程彬
                     * 修改时间:2016-10-8 ************************ end
                     */
                    map.put("childDate", TimeUtil.format(model.getChildDate(), TimeUtil.DATE_FORMAT));
                    map.put("createDate", TimeUtil.format(model.getCreateDate(), TimeUtil.DATE_FORMAT));
                    map.put("notes", model.getNotes());
                    map.put("swineryName", CacheUtil.getName(model.getSwineryId().toString(), NameEnum.SWINERY_NAME));
                    map.put("createName", CacheUtil.getName(model.getCreateId().toString(), NameEnum.CREATE_NAME));
					if (model.getWorker() != null) {
                        map.put("workerName", CacheUtil.getName(model.getWorker().toString(), NameEnum.WORKER_NAME));
					}
                    map.put("houseName", CacheUtil.getName(model.getHouseId().toString(), NameEnum.HOUSE_NAME));
					if (model.getPigpenId() != null) {
                        map.put("pigpenName", CacheUtil.getName(model.getPigpenId().toString(), NameEnum.PIGPEN_NAME));
					}
                    map.put("inHouseName", CacheUtil.getName(model.getInHouseId().toString(), NameEnum.HOUSE_NAME));
                    if (model.getInPigpenId() != null) {
                        map.put("inPigpenName", CacheUtil.getName(model.getInPigpenId().toString(), NameEnum.PIGPEN_NAME));
                    }
                    map.put("changeHouseTypeName", CacheUtil.getName(model.getChangeHouseType().toString(), NameEnum.CODE_NAME, codeEnum));
                    result.add(map);
				} else {
					Map<String, Object> map = result.get(result.size() - 1);
					List<Long> pigIds = (ArrayList<Long>) map.get("pigIds");
                    pigIds.add(model.getPigId());
				}
			}
		}
		return result;
	}

    private List<Map<String, Object>> searchNurseChangeHouse(Long billId) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(billId, " AND BILL_ID= ?");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID= ?");
        List<NurseModel> list = getList(nurseMapper, sql);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        if (list != null && list.size() > 0) {
            for (NurseModel model : list) {
                Map<String, Object> map = model.getData();
                Map<String, String> nurseMap = CacheUtil.getPigInfo(Maps.getString(map, "pigId"), PigInfoEnum.BILL_VIEW);
                Map<String, String> sowMap = CacheUtil.getPigInfo(Maps.getString(map, "boardSowId"), PigInfoEnum.BILL_VIEW);
                Map<String, String> inPigMap = CacheUtil.getPigInfo(Maps.getString(map, "inPigId"), PigInfoEnum.BILL_VIEW);
                map.put("earBrand", nurseMap.get("earBrand"));
                map.put("boardSowEarBrand", sowMap.get("earBrand"));
                map.put("inPigEarBrand", inPigMap.get("earBrand"));
                map.put("inHouseName", CacheUtil.getName(Maps.getString(map, "inHouseId"), NameEnum.HOUSE_NAME));
                map.put("inPigpenName", CacheUtil.getName(Maps.getString(map, "inPigpenId"), NameEnum.PIGPEN_NAME));
                map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.CREATE_NAME));
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("inPigHouseName", CacheUtil.getName(Maps.getString(map, "inPigHouseId"), NameEnum.HOUSE_NAME));
                map.put("inPigPigpenName", CacheUtil.getName(Maps.getString(map, "inPigPigpenId"), NameEnum.PIGPEN_NAME));
                result.add(map);
            }
        }
        return result;
    }

    private List<Map<String, Object>> searchBillCancel(Long billId, String oldEventCode, Long cancelBillId) throws Exception {
		SqlCon sql = new SqlCon();
        if (!EventConstants.TO_CHILD_CARE.equals(oldEventCode) && !EventConstants.TO_CHILD_FATTEN.equals(oldEventCode) && !EventConstants.GOOD_PIG_DIE
                .equals(oldEventCode) && !EventConstants.GOOD_PIG_SELL.equals(oldEventCode) && !EventConstants.SALE_SEMEN.equals(oldEventCode)
                && !EventConstants.SEMEN_INQUIRY.equals(oldEventCode) && !EventConstants.SEED_PIG.equals(oldEventCode)) {
			sql.addMainSql("SELECT T2.BUSINESS_CODE AS businessCode, T2.EVENT_CODE AS eventCode");
			sql.addMainSql(", (SELECT CREATE_ID FROM PP_M_BILL WHERE ROW_ID = T1.DELETED_BILL_ID) AS employeeId");
			sql.addMainSql(", (SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE ROW_ID = T3.EAR_CODE_ID) AS earBrand");
			sql.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
			sql.addMainSql(" INNER JOIN PP_M_BILL T2");
			sql.addMainSql(" ON(T1.BILL_ID = T2.ROW_ID");
			sql.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
			sql.addMainSql(" AND T2.DELETED_FLAG = '2'");
			sql.addCondition(billId, " AND T2.ROW_ID = ?)");
			sql.addMainSql(" INNER JOIN PP_L_PIG T3");
			sql.addMainSql(" ON(T1.PIG_ID = T3.ROW_ID");
			sql.addMainSql(" AND T1.FARM_ID = T3.FARM_ID)");
			sql.addCondition(PigConstants.PIG_TYPE_PORK, " WHERE T1.DELETED_FLAG = '2' AND T1.PIG_TYPE <> ?");
			sql.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
            sql.addCondition(cancelBillId, " AND T1.DELETED_BILL_ID = ?");
			if (EventConstants.FOSTER.equals(oldEventCode)) {
                // 排除寄入母猪
				sql.addMainSql(
						" AND EXISTS(SELECT 1 FROM PP_L_BILL_FOSTER_CARE WHERE FARM_ID = T1.FARM_ID AND PIG_ID = T1.PIG_ID AND BILL_ID = T1.BILL_ID AND DELETED_FLAG = '2')");
			}
			sql.addMainSql(" ORDER BY T1.ROW_ID");

		} else if (EventConstants.GOOD_PIG_SELL.equals(oldEventCode)) {

			sql.addMainSql(
					"SELECT T4.SALE_DESCRIBE AS saleDescribe, T4.SWINERY_ID AS swineryId, T4.TOTAL_PRICE AS totalPrice, T4.SALE_NUM AS saleNum");
			sql.addMainSql(
					" , T3.businessCode AS businessCode, T3.eventCode AS eventCode, T3.employeeId AS employeeId FROM");
			sql.addMainSql(
					" (SELECT T2.ROW_ID AS billId, T2.FARM_ID AS farmId, T2.BUSINESS_CODE AS businessCode, T2.EVENT_CODE AS eventCode");
			sql.addMainSql(" , (SELECT CREATE_ID FROM PP_M_BILL WHERE ROW_ID = T1.DELETED_BILL_ID) AS employeeId");
			sql.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
			sql.addMainSql(" INNER JOIN PP_M_BILL T2");
			sql.addMainSql(" ON(T1.BILL_ID = T2.ROW_ID AND T1.FARM_ID = T2.FARM_ID");
			sql.addMainSql(" AND T2.DELETED_FLAG = '2'");
			sql.addCondition(billId, " AND T2.ROW_ID = ?)");
            sql.addCondition(getFarmId(), " AND T1.FARM_ID = ? AND T1.DELETED_FLAG = '2'");
            sql.addCondition(cancelBillId, " AND T1.DELETED_BILL_ID = ?");
            sql.addMainSql(" GROUP BY T2.ROW_ID ) T3");
			sql.addMainSql(" INNER JOIN PP_L_BILL_PIG_SALE_DETAIL T4");
			sql.addMainSql(" ON(T3.farmId = T4.FARM_ID");
			sql.addMainSql(" AND T3.billId = T4.BILL_ID");
			sql.addMainSql(" AND T4.DELETED_FLAG = '2')");

		}else if(EventConstants.SALE_SEMEN.equals(oldEventCode)){
            sql.addMainSql("SELECT T2.SEMEN_BATCH_NO AS semenBatchNo, T2.EAR_BRAND AS earBrand");
            sql.addMainSql(" ,DATE(T2.VALID_DATE) AS validDate, T2.WAREHOUSE_ID AS warehouseId");
            sql.addMainSql(" ,T1.SALE_NUM AS saleNum, DATE(T1.SALE_DATE) AS saleDate");
            sql.addMainSql(" ,T1.WORKER AS worker, T1.NOTES AS notes");
            sql.addMainSql(" FROM PP_L_BILL_SEMEN_SALE T1");
            sql.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T2");
            sql.addMainSql(" ON (T1.SEMEN_ID = T2.ROW_ID)");
            sql.addMainSql(" WHERE T1.DELETED_FLAG = '2'");
            sql.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
            sql.addConditionWithNull(billId, " AND T1.BILL_ID= ?");
            sql.addConditionWithNull(cancelBillId, " AND T1.DELETED_BILL_ID = ?");

        } else if (EventConstants.SEMEN_INQUIRY.equals(oldEventCode)) {
            sql.addMainSql("SELECT s.SEMEN_BATCH_NO semenBatchNo,s.EAR_BRAND AS earBrand");
            sql.addMainSql(" ,m.MATERIAL_NAME materialIdName,s.SPERM_NUM anhNum,s.WAREHOUSE_ID warehouseId");
            sql.addMainSql(" ,Date(s.VALID_DATE) validDate, Date(s.INPUT_DATE) inputDate");
            sql.addMainSql(" ,s.WORKER worker,s.NOTES notes,s.ROW_ID AS semenId");
            sql.addMainSql(" FROM pp_l_bill_semen s");
            sql.addMainSql(" INNER JOIN cd_m_material m");
            sql.addMainSql(" ON (s.MATERIAL_ID = m.ROW_ID AND m.DELETED_FLAG = 0)");
            sql.addMainSql(" WHERE s.DELETED_FLAG = '2'");
            sql.addCondition(getFarmId(), " AND s.FARM_ID = ?");
            sql.addConditionWithNull(billId, " AND s.BILL_ID= ?");
            sql.addConditionWithNull(cancelBillId, " AND s.DELETED_BILL_ID = ?");

		}else {
			sql.addMainSql("SELECT T2.BUSINESS_CODE AS businessCode, T2.EVENT_CODE AS eventCode");
			sql.addMainSql(", (SELECT CREATE_ID FROM PP_M_BILL WHERE ROW_ID = T1.DELETED_BILL_ID) AS employeeId");
			sql.addMainSql(", T3.SWINERY_ID AS swineryId, COUNT(*) AS pigQty");
			sql.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
			sql.addMainSql(" INNER JOIN PP_M_BILL T2");
			sql.addMainSql(" ON(T1.BILL_ID = T2.ROW_ID AND T1.FARM_ID = T2.FARM_ID AND T2.DELETED_FLAG = '2'");
			sql.addCondition(billId, " AND T2.ROW_ID = ?)");
			sql.addMainSql(" INNER JOIN PP_L_PIG T3");
			sql.addMainSql(" ON(T1.PIG_ID = T3.ROW_ID AND T1.FARM_ID = T3.FARM_ID)");
			sql.addCondition(getFarmId(), " WHERE T1.DELETED_FLAG = '2' AND T1.FARM_ID = ?");
            sql.addCondition(cancelBillId, " AND T1.DELETED_BILL_ID = ?");
			sql.addMainSql(" GROUP BY T3.SWINERY_ID");
			sql.addMainSql(" ORDER BY T1.ROW_ID");
		}

        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sql.getCondition());

        List<Map<String, Object>> infosList = paramMapper.getObjectInfos(map);

        for (Map<String, Object> infosMap : infosList) {
            if (EventConstants.SALE_SEMEN.equals(oldEventCode) || EventConstants.SEMEN_INQUIRY.equals(oldEventCode)) {
                infosMap.put("workerName", CacheUtil.getName(Maps.getString(infosMap, "worker"), NameEnum.WORKER_NAME));
                Map<String, String> saleInfoMap = CacheUtil.getData("SC_M_WAREHOUSE", Maps.getString(infosMap, "warehouseId"));
                infosMap.put("warehouseName", Maps.getString(saleInfoMap, "WAREHOUSE_NAME"));
                infosMap.put("eventName", CacheUtil.getName(oldEventCode, NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
            } else {
                infosMap.put("employeeName", CacheUtil.getName(Maps.getString(infosMap, "employeeId"), NameEnum.EMPLOYEE_NAME));
                infosMap.put("eventName", CacheUtil.getName(Maps.getString(infosMap, "eventCode"), NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
                if (!Maps.isEmpty(infosMap, "swineryId")) {
                    infosMap.put("swineryName", CacheUtil.getName(Maps.getString(infosMap, "swineryId"), NameEnum.SWINERY_NAME));
                    if (Maps.isEmpty(infosMap, "swineryName")) {
                        infosMap.put("swineryName", "ID：" + Maps.getLongClass(infosMap, "swineryId") + "的群号已被删除");
                    }
                }
                infosMap.put("saleName", CacheUtil.getName(Maps.getString(infosMap, "saleDescribe"), NameEnum.CODE_NAME, CodeEnum.SELL_GOOD));
            }
        }

		return infosList;
	}

    private List<Map<String, Object>> searchPigEventCancel(Long billId, String oldEventCode, Long cancelBillId) throws Exception {
		SqlCon sql = new SqlCon();
		sql.addMainSql("SELECT T2.BUSINESS_CODE AS businessCode, T2.EVENT_CODE AS eventCode");
		sql.addMainSql(", (SELECT CREATE_ID FROM PP_M_BILL WHERE ROW_ID = T1.DELETED_BILL_ID) AS employeeId");
		sql.addMainSql(", (SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE ROW_ID = T3.EAR_CODE_ID) AS earBrand");
		sql.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
		sql.addMainSql(" INNER JOIN PP_M_BILL T2");
		sql.addMainSql(" ON(T1.BILL_ID = T2.ROW_ID");
		sql.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
        sql.addCondition(billId, " AND T2.ROW_ID = ?");
		sql.addMainSql(" AND (T2.DELETED_FLAG = '0' OR T2.DELETED_FLAG = '2'))");
		sql.addMainSql(" INNER JOIN PP_L_PIG T3");
		sql.addMainSql(" ON(T1.PIG_ID = T3.ROW_ID");
		sql.addMainSql(" AND T1.FARM_ID = T3.FARM_ID)");
		sql.addCondition(getFarmId(), " WHERE T1.DELETED_FLAG = '2' AND T1.FARM_ID = ?");
        sql.addCondition(cancelBillId, " AND T1.DELETED_BILL_ID = ?");
		if (EventConstants.DELIVERY.equals(oldEventCode) || EventConstants.WEAN.equals(oldEventCode)
				|| EventConstants.CHILD_PIG_DIE.equals(oldEventCode) || EventConstants.FOSTER.equals(oldEventCode)) {
			sql.addCondition(PigConstants.PIG_TYPE_PORK, " AND T1.PIG_TYPE <> ?");
		}

		if (EventConstants.FOSTER.equals(oldEventCode)) {
			sql.addMainSql(" AND EXISTS (");
			sql.addMainSql(" SELECT 1 FROM PP_L_BILL_FOSTER_DETAIL");
			sql.addMainSql(
					" WHERE FARM_ID = T1.FARM_ID AND BILL_ID = T1.BILL_ID AND LAST_SOW_ID = T1.PIG_ID AND DELETED_FLAG = '2')");
		}

		Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sql.getCondition());

		List<Map<String, Object>> infosList = paramMapper.getObjectInfos(map);

		if (infosList != null && !infosList.isEmpty()) {
            for (Map<String, Object> infosMap : infosList) {
                infosMap.put("employeeName", CacheUtil.getName(Maps.getString(infosMap, "employeeId"), NameEnum.EMPLOYEE_NAME));
                infosMap.put("eventName", CacheUtil.getName(Maps.getString(infosMap, "eventCode"), NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
			}
		}

		return infosList;
	}

    private List<Map<String, Object>> searchEachRecordCancel(Long billId, String oldEventCode, Long cancelBillId) throws Exception {
        List<Map<String, Object>> infosList = null;
        if (EventConstants.SALE_SEMEN.equals(oldEventCode)) {
            SqlCon sql = new SqlCon();
            sql.addMainSql("SELECT T2.SEMEN_BATCH_NO AS semenBatchNo, T2.EAR_BRAND AS earBrand");
            sql.addMainSql(" ,DATE(T2.VALID_DATE) AS validDate, T2.WAREHOUSE_ID AS warehouseId");
            sql.addMainSql(" ,T1.SALE_NUM AS saleNum, DATE(T1.SALE_DATE) AS saleDate");
            sql.addMainSql(" ,T1.WORKER AS worker, T1.NOTES AS notes");
            sql.addMainSql(" FROM PP_L_BILL_SEMEN_SALE T1");
            sql.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T2");
            sql.addMainSql(" ON (T1.SEMEN_ID = T2.ROW_ID)");
            sql.addMainSql(" WHERE T1.DELETED_FLAG = '2'");
            sql.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
            sql.addConditionWithNull(billId, " AND T1.BILL_ID= ?");
            sql.addConditionWithNull(cancelBillId, " AND T1.DELETED_BILL_ID = ?");

            Map<String, String> map = new HashMap<String, String>();
            map.put("sql", sql.getCondition());

            infosList = paramMapper.getObjectInfos(map);

            for (Map<String, Object> infosMap : infosList) {
                infosMap.put("workerName", CacheUtil.getName(Maps.getString(infosMap, "worker"), NameEnum.WORKER_NAME));
                Map<String, String> saleInfoMap = CacheUtil.getData("SC_M_WAREHOUSE", Maps.getString(infosMap, "warehouseId"));
                infosMap.put("warehouseName", Maps.getString(saleInfoMap, "WAREHOUSE_NAME"));
                infosMap.put("eventName", CacheUtil.getName(oldEventCode, NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
            }

        } else if (EventConstants.SEMEN_INQUIRY.equals(oldEventCode)) {
            SqlCon sql = new SqlCon();
            sql.addMainSql("SELECT s.SEMEN_BATCH_NO semenBatchNo,s.EAR_BRAND AS earBrand");
            sql.addMainSql(" ,m.MATERIAL_NAME materialIdName,s.SPERM_NUM anhNum,s.WAREHOUSE_ID warehouseId");
            sql.addMainSql(" ,Date(s.VALID_DATE) validDate, Date(s.INPUT_DATE) inputDate");
            sql.addMainSql(" ,s.WORKER worker,s.NOTES notes,s.ROW_ID AS semenId");
            sql.addMainSql(" FROM pp_l_bill_semen s");
            sql.addMainSql(" INNER JOIN cd_m_material m");
            sql.addMainSql(" ON (s.MATERIAL_ID = m.ROW_ID AND m.DELETED_FLAG = 0)");
            sql.addMainSql(" WHERE s.DELETED_FLAG = '2'");
            sql.addCondition(getFarmId(), " AND s.FARM_ID = ?");
            sql.addConditionWithNull(billId, " AND s.BILL_ID= ?");
            sql.addConditionWithNull(cancelBillId, " AND s.DELETED_BILL_ID = ?");

            Map<String, String> map = new HashMap<String, String>();
            map.put("sql", sql.getCondition());

            infosList = paramMapper.getObjectInfos(map);

            for (Map<String, Object> infosMap : infosList) {
                infosMap.put("workerName", CacheUtil.getName(Maps.getString(infosMap, "worker"), NameEnum.WORKER_NAME));
                Map<String, String> saleInfoMap = CacheUtil.getData("SC_M_WAREHOUSE", Maps.getString(infosMap, "warehouseId"));
                infosMap.put("warehouseName", Maps.getString(saleInfoMap, "WAREHOUSE_NAME"));
                infosMap.put("eventName", CacheUtil.getName(oldEventCode, NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
            }

        }else{
            SqlCon sql = new SqlCon();
            sql.addMainSql("SELECT T1.SWINERY_ID AS swineryId, T2.BUSINESS_CODE AS businessCode, T2.EVENT_CODE AS eventCode");
            sql.addMainSql(", (SELECT CREATE_ID FROM PP_M_BILL WHERE ROW_ID = T1.DELETED_BILL_ID) AS employeeId");
            sql.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
            sql.addMainSql(" INNER JOIN PP_M_BILL T2");
            sql.addMainSql(" ON(T1.BILL_ID = T2.ROW_ID");
            sql.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
            sql.addMainSql(" AND (T2.DELETED_FLAG = '0' OR T2.DELETED_FLAG = '2'))");
            sql.addMainSql(" INNER JOIN PP_L_PIG T3");
            sql.addMainSql(" ON(T1.PIG_ID = T3.ROW_ID");
            sql.addMainSql(" AND T1.FARM_ID = T3.FARM_ID)");
            sql.addCondition(getFarmId(), " WHERE T1.DELETED_FLAG = '2' AND T1.FARM_ID = ?");
            sql.addCondition(cancelBillId, " AND T1.DELETED_BILL_ID = ?");
            sql.addMainSql(" LIMIT 1");
    
            Map<String, String> map = new HashMap<String, String>();
            map.put("sql", sql.getCondition());
    
            infosList = paramMapper.getObjectInfos(map);
    
            for (Map<String, Object> infosMap : infosList) {
                if (!Maps.isEmpty(infosMap, "swineryId")) {
                    infosMap.put("swineryName", CacheUtil.getName(Maps.getString(infosMap, "swineryId"), NameEnum.SWINERY_NAME));
                    if (Maps.isEmpty(infosMap, "swineryName")) {
                        infosMap.put("swineryName", "ID：" + Maps.getLongClass(infosMap, "swineryId") + "的群号已被删除");
                    }
                }
                infosMap.put("employeeName", CacheUtil.getName(Maps.getString(infosMap, "employeeId"), NameEnum.EMPLOYEE_NAME));
                infosMap.put("eventName", CacheUtil.getName(Maps.getString(infosMap, "eventCode"), NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
            }
        }
        return infosList;
    }

	@Override
	public List<Map<String, Object>> searchWhichEventCancel(Long billId) throws Exception {
        SqlCon getEventCodeSqlCon = new SqlCon();
        getEventCodeSqlCon.addMainSql("SELECT T1.ROW_ID AS billId, T1.EVENT_CODE AS eventCode");
        getEventCodeSqlCon.addMainSql(" FROM PP_M_BILL T1");
        getEventCodeSqlCon.addMainSql(" WHERE EXISTS(");
        getEventCodeSqlCon.addMainSql(" SELECT 1 FROM PP_M_BILL T2 WHERE T2.DELETED_FLAG = '0'");
        getEventCodeSqlCon.addMainSql(" AND T2.CANCELED_BILL_ID = T1.ROW_ID");
        getEventCodeSqlCon.addCondition(billId, " AND T2.ROW_ID = ?)");

        // getEventCodeSqlCon.addMainSql("SELECT T2.ROW_ID AS billId, T2.EVENT_CODE AS eventCode");
        // getEventCodeSqlCon.addMainSql(" FROM PP_L_PIG_EVENT_RELATES T1");
        // getEventCodeSqlCon.addMainSql(" INNER JOIN PP_M_BILL T2");
        // getEventCodeSqlCon.addMainSql(" ON(T1.BILL_ID = T2.ROW_ID");
        // getEventCodeSqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
        // getEventCodeSqlCon.addMainSql(" AND T2.DELETED_FLAG = '2')");
        // getEventCodeSqlCon.addCondition(getFarmId(), " WHERE T1.DELETED_FLAG = '2' AND T1.FARM_ID = ?");
        // getEventCodeSqlCon.addCondition(billId, " AND T1.DELETED_BILL_ID = ? LIMIT 1");
		Map<String, String> getEventCodeSqlMap = new HashMap<String, String>();
        getEventCodeSqlMap.put("sql", getEventCodeSqlCon.getCondition());

		List<Map<String, Object>> getEventCodeInfosList = paramMapper.getObjectInfos(getEventCodeSqlMap);

		if (getEventCodeInfosList == null || getEventCodeInfosList.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "异常！单据没有搜索到信息。。。");
		}

		return getEventCodeInfosList;
	}

    public List<Map<String, Object>> searchSemenInquiry(Long billId) throws Exception {
        SqlCon semenSql = new SqlCon();
        semenSql.addMainSql("SELECT s.SEMEN_BATCH_NO semenBatchNo,s.EAR_BRAND AS earBrand");
        semenSql.addMainSql(" ,m.MATERIAL_NAME materialIdName,s.SPERM_NUM anhNum,s.WAREHOUSE_ID warehouseId");
        semenSql.addMainSql(" ,Date(s.VALID_DATE) validDate, Date(s.INPUT_DATE) inputDate");
        semenSql.addMainSql(" ,s.WORKER worker,s.NOTES notes,s.ROW_ID AS semenId");
        semenSql.addMainSql(" FROM pp_l_bill_semen s");
        semenSql.addMainSql(" INNER JOIN cd_m_material m");
        semenSql.addMainSql(" ON (s.MATERIAL_ID = m.ROW_ID AND m.DELETED_FLAG = 0)");
        semenSql.addMainSql(" WHERE s.DELETED_FLAG = '0'");
        semenSql.addCondition(getFarmId(), " AND s.FARM_ID = ?");
        semenSql.addCondition(billId, " AND s.BILL_ID = ?");

        Map<String,String> sqlMap = new HashMap<>();
        sqlMap.put("sql", semenSql.getCondition());

        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);

        if (result != null && result.size() > 0) {
            for (Map<String, Object> map : result) {
                map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
                map.put("warehouseName", CacheUtil.getName(Maps.getString(map, "warehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            }
        }
        return result;
    }
    
    /**
     * @Description: 精液销售
     * @author yangy
     * @param billId
     * @return
     * @throws Exception
     */
    private List<Map<String, Object>> searchSaleSemen(Long billId) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql("SELECT T2.SEMEN_BATCH_NO AS semenBatchNo, T2.EAR_BRAND AS earBrand");
        sql.addMainSql(" ,DATE(T2.VALID_DATE) AS validDate, T2.WAREHOUSE_ID AS warehouseId");
        sql.addMainSql(" ,T1.SALE_NUM AS saleNum, DATE(T1.SALE_DATE) AS saleDate");
        sql.addMainSql(" ,T1.WORKER AS worker, T1.NOTES AS notes, T1.SEMEN_ID AS semenId");
        sql.addMainSql(" FROM PP_L_BILL_SEMEN_SALE T1");
        sql.addMainSql(" INNER JOIN PP_L_BILL_SEMEN T2");
        sql.addMainSql(" ON (T1.SEMEN_ID = T2.ROW_ID)");
        sql.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sql.addConditionWithNull(getFarmId(), " AND T1.FARM_ID= ?");
        sql.addConditionWithNull(billId, " AND T1.BILL_ID= ?");

        Map<String,String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sql.getCondition());

        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : result) {
            map.put("workerName", CacheUtil.getName(Maps.getString(map, "worker"), NameEnum.WORKER_NAME));
            Map<String, String> saleInfoMap = CacheUtil.getData("SC_M_WAREHOUSE", Maps.getString(map, "warehouseId"));
            map.put("warehouseName", Maps.getString(saleInfoMap, "WAREHOUSE_NAME"));
        }
        return result;
    }
}
