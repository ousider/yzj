package xn.pigfarm.service.production.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.bigDeciml.BigDecimalUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaGatherInvoiceHeaderDetail;
import xn.hana.model.common.HanaGatherInvoiceRowsDetail;
import xn.hana.model.common.HanaPigProduct;
import xn.hana.model.common.HanaPigProductDetail;
import xn.hana.model.common.HanaSaleBill;
import xn.hana.model.common.HanaSaleBillDetail;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.model.common.MTC_OITM;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.basicInfo.BasicInfoException;
import xn.pigfarm.exception.production.ProductionException;
import xn.pigfarm.mapper.backend.PigClassMapper;
import xn.pigfarm.mapper.basicinfo.BoarMapper;
import xn.pigfarm.mapper.basicinfo.CompanyMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeMapper;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.basicinfo.MaterialMapper;
import xn.pigfarm.mapper.basicinfo.PigpenMapper;
import xn.pigfarm.mapper.basicinfo.PorkPigMapper;
import xn.pigfarm.mapper.basicinfo.SowMapper;
import xn.pigfarm.mapper.basicinfo.SpermMapper;
import xn.pigfarm.mapper.hana.SysHanaInsertLogMapper;
import xn.pigfarm.mapper.initialization.PpInputPigMapper;
import xn.pigfarm.mapper.production.BillBreedMapper;
import xn.pigfarm.mapper.production.BillMapper;
import xn.pigfarm.mapper.production.BoarObsoleteMapper;
import xn.pigfarm.mapper.production.ChangeHouseMapper;
import xn.pigfarm.mapper.production.ChildDieMapper;
import xn.pigfarm.mapper.production.DeliveryMapper;
import xn.pigfarm.mapper.production.EarCodeMapper;
import xn.pigfarm.mapper.production.LeaveMapper;
import xn.pigfarm.mapper.production.NurseMapper;
import xn.pigfarm.mapper.production.PigEventHisMapper;
import xn.pigfarm.mapper.production.PigEventMapper;
import xn.pigfarm.mapper.production.PigEventRelatesMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.mapper.production.PigSaleDetailMapper;
import xn.pigfarm.mapper.production.PigSaleMapper;
import xn.pigfarm.mapper.production.PorkCheckMapper;
import xn.pigfarm.mapper.production.ReLfeedbackMapper;
import xn.pigfarm.mapper.production.ReLindicatorSortMapper;
import xn.pigfarm.mapper.production.ReLproDetailMapper;
import xn.pigfarm.mapper.production.ReLproGroupMapper;
import xn.pigfarm.mapper.production.ReLsaleDetailMapper;
import xn.pigfarm.mapper.production.ReLweanDetailMapper;
import xn.pigfarm.mapper.production.ReMreportMapper;
import xn.pigfarm.mapper.production.SalePlanMapper;
import xn.pigfarm.mapper.production.SearchSemenMapper;
import xn.pigfarm.mapper.production.SemenMapper;
import xn.pigfarm.mapper.production.SemenSaleDetailMapper;
import xn.pigfarm.mapper.production.SemenSaleMapper;
import xn.pigfarm.mapper.production.SpermInfoMapper;
import xn.pigfarm.mapper.production.SweachValidPigMapper;
import xn.pigfarm.mapper.production.SwineryDayageChangeMapper;
import xn.pigfarm.mapper.production.SwineryMapper;
import xn.pigfarm.mapper.production.ToworkMapper;
import xn.pigfarm.mapper.supplychian.WarehouseMapper;
import xn.pigfarm.model.backend.PigClassModel;
import xn.pigfarm.model.basicinfo.BoarModel;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PigpenModel;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.basicinfo.SowModel;
import xn.pigfarm.model.basicinfo.SpermModel;
import xn.pigfarm.model.hana.SysHanaInsertLogModel;
import xn.pigfarm.model.initialization.PpInputPigModel;
import xn.pigfarm.model.production.BackfatView;
import xn.pigfarm.model.production.BillBreedModel;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.BoarObsoleteModel;
import xn.pigfarm.model.production.BreedView;
import xn.pigfarm.model.production.ChangeEarBandView;
import xn.pigfarm.model.production.ChangeHouseInfoView;
import xn.pigfarm.model.production.ChangeHouseView;
import xn.pigfarm.model.production.ChangeSwineryView;
import xn.pigfarm.model.production.ChildDieView;
import xn.pigfarm.model.production.CreatePorkPigView;
import xn.pigfarm.model.production.DeliveryModel;
import xn.pigfarm.model.production.DeliveryView;
import xn.pigfarm.model.production.EarCodeModel;
import xn.pigfarm.model.production.FosterView;
import xn.pigfarm.model.production.LeaveModel;
import xn.pigfarm.model.production.LeaveView;
import xn.pigfarm.model.production.NurseModel;
import xn.pigfarm.model.production.ParaPigMoveInModel;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigEventRelatesModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.PigMoveInView;
import xn.pigfarm.model.production.PigSaleDetailModel;
import xn.pigfarm.model.production.PigSaleModel;
import xn.pigfarm.model.production.PigView;
import xn.pigfarm.model.production.PorkCheckModel;
import xn.pigfarm.model.production.PregnancyCheckView;
import xn.pigfarm.model.production.ReLfeedbackModel;
import xn.pigfarm.model.production.ReLindicatorSortModel;
import xn.pigfarm.model.production.ReLproDetailModel;
import xn.pigfarm.model.production.ReLproGroupModel;
import xn.pigfarm.model.production.ReLsaleDetailModel;
import xn.pigfarm.model.production.ReLweanDetailModel;
import xn.pigfarm.model.production.ReMreportModel;
import xn.pigfarm.model.production.ReserveToProductView;
import xn.pigfarm.model.production.SalePlanModel;
import xn.pigfarm.model.production.SearchSemenModel;
import xn.pigfarm.model.production.SearchValidPigModel;
import xn.pigfarm.model.production.SemenBillModel;
import xn.pigfarm.model.production.SemenModel;
import xn.pigfarm.model.production.SemenSaleDetailModel;
import xn.pigfarm.model.production.SemenSaleModel;
import xn.pigfarm.model.production.SemenView;
import xn.pigfarm.model.production.SpermInfoModel;
import xn.pigfarm.model.production.SwineryDayageChangeModel;
import xn.pigfarm.model.production.SwineryModel;
import xn.pigfarm.model.production.ToChildView;
import xn.pigfarm.model.production.ToworkModel;
import xn.pigfarm.model.production.ValidPigModel;
import xn.pigfarm.model.production.ValidSemenModel;
import xn.pigfarm.model.production.WeanView;
import xn.pigfarm.service.hana.ISendToSAPProductionService;
import xn.pigfarm.service.production.IProductionService;
import xn.pigfarm.service.production.ISearchPigRemind;
import xn.pigfarm.util.constants.AccountConstants;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;
import xn.pigfarm.util.constants.SpermConstants;
import xn.pigfarm.util.constants.SupplyChianConstants;
import xn.pigfarm.util.enums.PigInfoEnum;

/**
 * @Description: 生产管理
 * @author fangc
 * @date 2016年4月25日 下午12:31:21
 */

@Service("ProductionService")
public class ProductionServiceImpl extends BaseServiceImpl implements IProductionService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PigEventMapper pigEventMapper;

    @Autowired
    private SwineryMapper swineryMapper;

    @Autowired
    private SemenMapper semenMapper;

    @Autowired
    private SearchSemenMapper searchSemenMapper;

    @Autowired
    private SweachValidPigMapper sweachValidPigMapper;

    @Autowired
    private SowMapper sowMapper;

    @Autowired
    private BoarMapper boarMapper;

    @Autowired
    private PorkPigMapper porkPigMapper;

    @Autowired
    private PigMapper pigMapper;

    @Autowired
    private PpInputPigMapper ppInputPigMapper;

    @Autowired
    private PigEventRelatesMapper pigEventRelatesMapper;

    @Autowired
    private BoarObsoleteMapper boarObsoleteMapper;

    @Autowired
    private PigEventHisMapper pigEventHisMapper;

    @Autowired
    private PigSaleMapper pigSaleMapper;

    @Autowired
    private PigSaleDetailMapper pigSaleDetailMapper;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private ToworkMapper toworkMapper;

    @Autowired
    private EarCodeMapper earCodeMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private ISearchPigRemind searchPigRemind;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private NurseMapper nurseMapper;

    @Autowired
    private ChangeHouseMapper changeHouseMapper;

    @Autowired
    private BillBreedMapper billBreedMapper;

    @Autowired
    private PigClassMapper pigClassMapper;

    @Autowired
    private PorkCheckMapper porkCheckMapper;

    @Autowired
    private SemenSaleMapper semenSaleMapper;

    @Autowired
    private SemenSaleDetailMapper semenSaleDetailMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private PigpenMapper pigpenMapper;

    @Autowired
    private SpermInfoMapper spermInfoMapper;

    @Autowired
    private ChildDieMapper childDieMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SpermMapper spermMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private SwineryDayageChangeMapper swineryDayageChangeMapper;

    @Autowired
    private SalePlanMapper salePlanMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private ReMreportMapper reMreportMapper;

    @Autowired
    private ReLproDetailMapper reLproDetailMapper;

    @Autowired
    private ReLproGroupMapper reLproGroupMapper;

    @Autowired
    private ReLsaleDetailMapper reLsaleDetailMapper;

    @Autowired
    private ReLweanDetailMapper reLweanDetailMapper;

    @Autowired
    private ReLindicatorSortMapper reLindicatorSortMapper;

    @Autowired
    private ReLfeedbackMapper reLfeedbackMapper;

    @Autowired
    private SysHanaInsertLogMapper sysHanaInsertLogMapper;

    @Autowired
    private ISendToSAPProductionService sendToSAPProductionService;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public BasePageInfo editValidPigToPage(SearchValidPigModel selectValidPigModel) throws Exception {

        List<ValidPigModel> selectValidPigModels = null;
        List<ValidPigModel> _selectValidPigModels = null;
        selectValidPigModel.setCompanyId(getCompanyId());
        selectValidPigModel.setFarmId(getFarmId());
        selectValidPigModel.setCreateId(getEmployeeId());
        selectValidPigModel.setOffset(Long.valueOf(getPageInfo().getSartRow()));
        selectValidPigModel.setPagesize(Long.valueOf(getPageInfo().getEndRow()));

        if (!StringUtil.isNumeric(selectValidPigModel.getHouseIds())) {
            selectValidPigModel.setHouseIds(null);
        }
        if (!StringUtil.isNumeric(selectValidPigModel.getPigpenIds())) {
            selectValidPigModel.setPigpenIds(null);
        }
        if (!StringUtil.isNumeric(selectValidPigModel.getSwineryIds())) {
            selectValidPigModel.setSwineryIds(null);
        }
        if (!StringUtil.isNumeric(selectValidPigModel.getBreedIds())) {
            selectValidPigModel.setBreedIds(null);
        }
        if (!StringUtil.isNumeric(selectValidPigModel.getPigClassIds())) {
            selectValidPigModel.setPigClassIds(null);
        }
        // 留种
        if (selectValidPigModel != null && EventConstants.SEED_PIG.equals(selectValidPigModel.getEventName())) {
            _selectValidPigModels = sweachValidPigMapper.searchSeedPigToList(selectValidPigModel);
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
            // 选种
        } else if (selectValidPigModel != null && EventConstants.SELECT_BREED_PIG.equals(selectValidPigModel.getEventName())) {
            if ("1".equals(selectValidPigModel.getGoodPigFlag())) {
                _selectValidPigModels = sweachValidPigMapper.searchBreedPigByGoodToList(selectValidPigModel);
            } else {
                _selectValidPigModels = sweachValidPigMapper.searchBreedPigToList(selectValidPigModel);
            }
            if (_selectValidPigModels != null && !_selectValidPigModels.isEmpty()) {
                for (ValidPigModel validPigModel : _selectValidPigModels) {
                    if (validPigModel.getSex() != null && PigConstants.PIG_SEX_MALE.equals(validPigModel.getSex())) {
                        validPigModel.setSexName("公");
                        validPigModel.setSelectBreedType(validPigModel.getSex());
                        validPigModel.setSelectBreedTypeName("种公猪");
                    } else if (validPigModel.getSex() != null && PigConstants.PIG_SEX_FEMALE.equals(validPigModel.getSex())) {
                        validPigModel.setSexName("母");
                        validPigModel.setSelectBreedType(validPigModel.getSex());
                        validPigModel.setSelectBreedTypeName("种母猪");
                    } else {
                        validPigModel.setSexName("混合");
                    }
                }
            }
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
            // 留种猪死亡
        } else if (selectValidPigModel != null && EventConstants.BREED_PIG_DIE.equals(selectValidPigModel.getEventName())
                && PigConstants.EAR_SHORT_CODE_TYPE.equals(selectValidPigModel.getEarCodeType())) {
            _selectValidPigModels = sweachValidPigMapper.searchSeedPigDieToList(selectValidPigModel);
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
            // 猪只销售
        } else if (selectValidPigModel != null && EventConstants.GOOD_PIG_SELL.equals(selectValidPigModel.getEventName())) {
            if ("1".equals(selectValidPigModel.getGoodPigFlag())) {
                _selectValidPigModels = sweachValidPigMapper.searchGoodPigByBreedToList(selectValidPigModel);
            } else {
                // 审批中猪只销售时不能选到（审批过的猪只状态已变，退回的猪只仍然可以选到）
                _selectValidPigModels = sweachValidPigMapper.searchGoodPigToList(selectValidPigModel);
            }
            if (_selectValidPigModels != null && !_selectValidPigModels.isEmpty()) {
                for (ValidPigModel validPigModel : _selectValidPigModels) {
                    // 后背公猪
                    if (Long.valueOf(PigConstants.PIG_CLASS_HBGZ).equals(validPigModel.getPigClass())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_RESERVE_BOARD_PIG));
                        // 生产公猪
                    } else if (Long.valueOf(PigConstants.PIG_CLASS_SCGZ).equals(validPigModel.getPigClass())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG));
                        // 后备母猪，后备待配
                    } else if (Long.valueOf(PigConstants.PIG_CLASS_HBMZ).equals(validPigModel.getPigClass()) || Long.valueOf(
                            PigConstants.PIG_CLASS_HBDP).equals(validPigModel.getPigClass())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_RESERVE_SOW_PIG));
                        // 返情，流产，空怀，断奶,妊娠母猪
                    } else if (Long.valueOf(PigConstants.PIG_CLASS_KH).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_FQ1)
                            .equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_LC).equals(validPigModel.getPigClass())
                            || Long.valueOf(PigConstants.PIG_CLASS_SOW_DN).equals(validPigModel.getPigClass()) || Long.valueOf(
                                    PigConstants.PIG_CLASS_RS).equals(validPigModel.getPigClass())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG));
                        // 留种公猪
                    } else if ((Long.valueOf(PigConstants.PIG_CLASS_YH).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BF)
                            .equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BRJZ).equals(validPigModel.getPigClass())
                            || Long.valueOf(PigConstants.PIG_CLASS_BRRZ).equals(validPigModel.getPigClass())) && PigConstants.PIG_SEX_MALE.equals(
                                    validPigModel.getSex()) && PigConstants.SEED_TYPE_YES.equals(validPigModel.getSeedFlag())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_BOAR_SEED));
                        // 留种母猪
                    } else if ((Long.valueOf(PigConstants.PIG_CLASS_YH).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BF)
                            .equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BRJZ).equals(validPigModel.getPigClass())
                            || Long.valueOf(PigConstants.PIG_CLASS_BRRZ).equals(validPigModel.getPigClass())) && PigConstants.PIG_SEX_FEMALE.equals(
                                    validPigModel.getSex()) && PigConstants.SEED_TYPE_YES.equals(validPigModel.getSeedFlag())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_SOW_SEED));
                        // 留种猪（混合）
                    } else if ((Long.valueOf(PigConstants.PIG_CLASS_YH).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BF)
                            .equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BRJZ).equals(validPigModel.getPigClass())
                            || Long.valueOf(PigConstants.PIG_CLASS_BRRZ).equals(validPigModel.getPigClass())) && PigConstants.PIG_SEX_MIX.equals(
                                    validPigModel.getSex()) && PigConstants.SEED_TYPE_YES.equals(validPigModel.getSeedFlag())) {
                        validPigModel.setSaleType(Long.valueOf(PigConstants.SELL_GOOD_SEED));
                    }
                }
            }
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
            // 精液销售
        } else if (selectValidPigModel != null && EventConstants.SALE_SEMEN.equals(selectValidPigModel.getEventName())) {
            _selectValidPigModels = sweachValidPigMapper.searchSaleSemenToList(selectValidPigModel);
            if (_selectValidPigModels != null && _selectValidPigModels.size() > 0) {
                List<ValidPigModel> validPigModels = new ArrayList<ValidPigModel>();
                for (ValidPigModel validPigModel : _selectValidPigModels) {
                    if (validPigModel.getSemenNum() <= 0) {
                        validPigModels.add(validPigModel);
                    }
                }
                _selectValidPigModels.removeAll(validPigModels);
                validPigModels.clear();
            }
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
            // 商品猪死亡
        } else if (selectValidPigModel != null && EventConstants.GOOD_PIG_DIE.equals(selectValidPigModel.getEventName())) {
            _selectValidPigModels = sweachValidPigMapper.searchGoodPigDieToList(selectValidPigModel);
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
            // 转保育
        } else if (selectValidPigModel != null && EventConstants.TO_CHILD_CARE.equals(selectValidPigModel.getEventName())) {
            _selectValidPigModels = sweachValidPigMapper.searchTochildPigToList(selectValidPigModel);
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
        } else if (selectValidPigModel != null && EventConstants.TO_CHILD_FATTEN.equals(selectValidPigModel.getEventName())) {
            _selectValidPigModels = sweachValidPigMapper.searchToFattenPigToList(selectValidPigModel);
            selectValidPigModel.setTotalCount(Long.valueOf(_selectValidPigModels.size()));
            if (_selectValidPigModels.size() < getPageInfo().getEndRow()) {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), _selectValidPigModels.size());
            } else {
                selectValidPigModels = _selectValidPigModels.subList(getPageInfo().getSartRow(), getPageInfo().getEndRow());
            }
        } else {
            selectValidPigModels = sweachValidPigMapper.searchValidPigToList(selectValidPigModel);
            if (!"0".equals(selectValidPigModel.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, selectValidPigModel.getErrorMessage());
            }
        }
        if (selectValidPigModels.isEmpty()) {
            String earBrand = StringUtil.isBlank(selectValidPigModel.getQuery()) ? selectValidPigModel.getEarBrand() : selectValidPigModel.getQuery();
            String eventName = selectValidPigModel.getEventName();

            // String searchNotFindPigRemind = searchPigRemind(eventName, earBrand);
        }

        // SqlCon sql = new SqlCon();
        // List<DeliveryModel> listDeliveryModel = getList(deliveryMapper, sql);

        if (selectValidPigModels != null && selectValidPigModels.size() > 0) {
            for (ValidPigModel validPigModel : selectValidPigModels) {
                // 带仔数量大于0
                if (validPigModel.getPigQty() > 0 && EventConstants.WEAN.equals(selectValidPigModel.getEventName()) && validPigModel
                        .getLastEventDate() != null) {
                    Date lastEventDate = TimeUtil.dateAddDay(validPigModel.getLastEventDate(), Calendar.DAY_OF_WEEK);
                    if (TimeUtil.compareDate(lastEventDate, new Date(), Calendar.DATE) > 0) {
                        validPigModel.setLastEventDate(new Date());
                    } else {
                        validPigModel.setLastEventDate(lastEventDate);
                    }
                }
            }
        }
        getPageInfo().setTotal(selectValidPigModel.getTotalCount());
        return getPageInfo(selectValidPigModels);
    }

    @Override
    public BasePageInfo editSemenToList(SearchSemenModel searchSemenModel) throws Exception {

        if (searchSemenModel.getBreedDate() == null) {
            // Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
        }
        searchSemenModel.setCreateId(getEmployeeId());
        searchSemenModel.setCompanyId(getCompanyId());
        searchSemenModel.setFarmId(getFarmId());
        searchSemenModel.setOffset(Long.valueOf(getPageInfo().getSartRow()));
        searchSemenModel.setPagesize(Long.valueOf(getPageInfo().getEndRow()));
        List<ValidSemenModel> selectSemenModels = searchSemenMapper.searchSemenToList(searchSemenModel);
        if (!"0".equals(searchSemenModel.getErrorCode())) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, searchSemenModel.getErrorMessage());
        }
        getPageInfo().setTotal(searchSemenModel.getTotalCount());
        return getPageInfo(selectSemenModels);
    }

    // 转入批次查询
    public BasePageInfo searchPorkToList(String eventName, Long houseId, Long pigpenId, Long swineryId, Long breedId, String changeType)
            throws Exception {
        Long[] pigClass = null;
        if ("TO_CHILD_CARE".equals(eventName)) {
            pigClass = new Long[] { 15L };
        } else if ("TO_CHILD_FATTEN".equals(eventName)) {
            pigClass = new Long[] { 16L };
        } else if ("GOOD_PIG_SELL".equals(eventName)) {
            pigClass = new Long[] { 14L, 15L, 16L, 17L, 18L };
        } else if ("GOOD_PIG_DIE".equals(eventName)) {
            pigClass = new Long[] { 14L, 15L, 16L, 17L, 18L };
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("farmId", getFarmId());
        map.put("houseId", houseId);
        map.put("pigpenId", pigpenId);
        map.put("swineryId", swineryId);
        map.put("breedId", breedId);
        map.put("pigClass", pigClass);
        List<ValidPigModel> searchPorkToList = sweachValidPigMapper.searchPorkToList(map);

        Map<String, String> mapSql = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T0.ROW_ID SWINERY_ID ,T0.SWINERY_NAME,BEGIN_DATE MIN_VALID_DATE,NOW() MAX_VALID_DATE ");
        sql.append("FROM PP_M_SWINERY T0 ");
        sql.append("WHERE T0.ROW_ID NOT IN ( ");
        // 没有猪只
        sql.append("    SELECT DISTINCT T1.SWINERY_ID FROM PP_L_PIG T1 ");
        sql.append("    WHERE T1.DELETED_FLAG = 0 AND T1.STATUS = 1 AND T1.FARM_ID = " + getFarmId() + " AND T1.SWINERY_ID IS NOT NULL ) ");
        sql.append("AND T0.ROW_ID IN ( ");
        // EXCEL创建
        sql.append("    SELECT DISTINCT T2.SWINERY_ID FROM PP_L_BILL_CHANGE_SWINERY T2 ");
        sql.append("    WHERE T2.DELETED_FLAG = 0 AND T2.STATUS = 1 AND T2.FARM_ID = " + getFarmId() + " AND T2.ORIGIN_APP = 'EXCEL' ) ");
        sql.append("AND T0.DELETED_FLAG = 0 AND T0.STATUS = 1 AND T0.FARM_ID = " + getFarmId() + " AND T0.CREATE_TYPE = 2 ");
        mapSql.put("sql", sql.toString());
        List<ValidPigModel> operSql = sweachValidPigMapper.operSql(mapSql);
        for (ValidPigModel validPigModel : operSql) {
            searchPorkToList.add(validPigModel);
        }
        return getPageInfo(searchPorkToList);
    }

    // 转出批次查询
    public BasePageInfo searchPorkToList2(String eventName, Long houseId, String pigpenId, Long swineryId, Long breedId, String changeType)
            throws Exception {
        Long[] pigClass = null;
        String[] pigpenIds = null;
        if ("TO_CHILD_CARE".equals(eventName)) {
            if (PigConstants.CHANGE_HOUSE_TYPE_CHI_SELF.equals(changeType)) {
                pigClass = new Long[] { 15L };
            } else {
                pigClass = new Long[] { 14l };
            }
        } else if ("TO_CHILD_FATTEN".equals(eventName)) {
            if (PigConstants.CHANGE_HOUSE_TYPE_FAT_SELF.equals(changeType)) {
                pigClass = new Long[] { 16L };
            } else {
                pigClass = new Long[] { 15l };
            }
        } else if ("GOOD_PIG_SELL".equals(eventName)) {
            pigClass = new Long[] { 14l, 15l, 16l, 17l, 18l };
        } else if ("GOOD_PIG_DIE".equals(eventName)) {
            // 商品猪死亡可以选择留种猪
            pigClass = new Long[] { 14l, 15l, 16l, 17l, 18l };
        }
        pigpenIds = pigpenId == null ? null : StringUtil.split(pigpenId, ",");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("farmId", getFarmId());
        map.put("houseId", houseId);
        map.put("pigpenIds", pigpenIds);
        map.put("swineryId", swineryId);
        map.put("breedId", breedId);
        map.put("pigClass", pigClass);

        return getPageInfo(sweachValidPigMapper.searchPorkToList(map));
    }

    @Override
    public void editPigMoveIn(ParaPigMoveInModel paraPigMoveInModel, String eventName, String pigMoveIn) throws Exception {
        // eventName = "PIG_MOVE_IN";
        // 单据日期不能为空
        if (paraPigMoveInModel.getEnterDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }

        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());

        paraPigMoveInModel.setOriginFlag("S");
        paraPigMoveInModel.setOriginApp("XN1.0");
        paraPigMoveInModel.setCompanyId(getCompanyId());
        paraPigMoveInModel.setFarmId(getFarmId());
        paraPigMoveInModel.setCreateId(getEmployeeId());
        paraPigMoveInModel.setBusinessCode(businessCode);
        paraPigMoveInModel.setEventCode(eventName);

        // 创建表单
        paraPigMoveInModel.setBillDate(paraPigMoveInModel.getEnterDate());

        // 验证是否可以操作 入参:enterDate 原因: 在后面的入场都是用的单据日期BillDate
        sapEventMasage(getFarmId(), paraPigMoveInModel.getBillDate());

        billMapper.insert(paraPigMoveInModel);
        long billId = paraPigMoveInModel.getRowId();
        List<PigMoveInView> pigMoveIns = null;
        List<ParaPigMoveInModel> porkMoveIns = null;

        long breedId = 0;
        String strain = "";
        Double birthWeight = 0.0;
        String bodyCondition = "";
        long leftTeatNum = 0;
        long rightTeatNum = 0;
        String sex = "";
        String earBrand = null;
        List<Map<String, Object>> swineryDayageMaps = new ArrayList<Map<String, Object>>();
        List<PigMoveInView> pigMoveInViews = new ArrayList<PigMoveInView>();
        Date createDate = new Date();
        //
        if (PigConstants.PIG_MOVE_IN_OPT_TYPE_EAR.equals(paraPigMoveInModel.getEnterType())) {

            // 耳号肉猪（销售单据入场）
            if (PigConstants.PIG_TYPE_PORK.equals(paraPigMoveInModel.getPigType())) {
                porkMoveIns = getJsonList(pigMoveIn, ParaPigMoveInModel.class);
                if (porkMoveIns.size() == 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "未添加明细不能提交-后台！");
                }
                Date date = new Date();
                for (Iterator<ParaPigMoveInModel> it = porkMoveIns.iterator(); it.hasNext();) {
                    Map<String, Object> swineryDayAgemap = new HashMap<String, Object>();
                    ParaPigMoveInModel porkMoveInModel = (ParaPigMoveInModel) it.next();
                    porkMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                    if (porkMoveInModel.getPigType() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只【类别】不能为空！");
                    }
                    if (porkMoveInModel.getTotalNum() == null || porkMoveInModel.getTotalNum() <= 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只数量不能小于或等于0！");
                    }
                    if (porkMoveInModel.getHouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只猪舍不能为空！");
                    }
                    if (porkMoveInModel.getSwineryId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只批次不能为空！");
                    }
                    if (porkMoveInModel.getMaterialId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只物料不能为空！");
                    }
                    if (porkMoveInModel.getPigClass() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只状态不能为空！");
                    }
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addConditionWithNull(porkMoveInModel.getMaterialId(), " AND MATERIAL_ID = ?");
                    sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                    PorkPigModel cdPorkPigModel = getModel(porkPigMapper, sqlCon);
                    breedId = cdPorkPigModel.getBreedId();
                    strain = cdPorkPigModel.getStrain();
                    birthWeight = cdPorkPigModel.getBirthWeight();
                    bodyCondition = cdPorkPigModel.getBodyCondition();
                    leftTeatNum = cdPorkPigModel.getLeftTeatNum();
                    rightTeatNum = cdPorkPigModel.getRightTeatNum();
                    sex = PigConstants.PIG_SEX_MIX;

                    SqlCon porkSql = new SqlCon();
                    porkSql.addMainSql("SELECT l.ROW_ID rowId,p.ROW_ID pigId,l.leave_date leaveDate ");
                    porkSql.addMainSql(" FROM PP_L_PIG P ");
                    porkSql.addMainSql(" INNER JOIN pp_l_bill_leave l ");
                    porkSql.addMainSql(" ON p.ROW_ID = l.PIG_ID AND l.DELETED_FLAG = 0 ");
                    porkSql.addMainSql(" WHERE p.DELETED_FLAG = 0 AND l.IS_MOVE_IN = 'N' ");
                    porkSql.addCondition(paraPigMoveInModel.getRelateBillId(), " AND l.BILL_ID = ? ");
                    porkSql.addCondition(porkMoveInModel.getPreHouseId(), " AND p.HOUSE_ID = ? ");
                    porkSql.addCondition(porkMoveInModel.getPreSwineryId(), " AND p.SWINERY_ID = ? ");
                    porkSql.addCondition(porkMoveInModel.getPreLineNumber(), " AND l.LINE_NUMBER = ? ");
                    Date birthDate = TimeUtil.dateAddDay(date, -porkMoveInModel.getDayAge());
                    Map<String, String> sqlMap = new HashMap<>();
                    sqlMap.put("sql", porkSql.getCondition());
                    List<Map<String, String>> porkList = paramMapper.getInfos(sqlMap);

                    List<LeaveModel> leaveList = new ArrayList<>();
                    for (Map<String, String> map : porkList) {
                        LeaveModel leaveModel = new LeaveModel();
                        leaveModel.setRowId(Maps.getLong(map, "rowId"));
                        leaveModel.setIsMoveIn("Y");
                        leaveList.add(leaveModel);
                        date = TimeUtil.compareDate(date, Maps.getDate(map, "leaveDate"), Calendar.DATE) < 0 ? date : Maps.getDate(map, "leaveDate");
                    }

                    if (TimeUtil.compareDate(date, paraPigMoveInModel.getBillDate(), Calendar.DATE) > 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场日期不能小于销售日期！");
                    }

                    if (!leaveList.isEmpty()) {
                        leaveMapper.updates(leaveList);
                    }
                    /******************************** 2017-2-16 yangy 入场批次日龄修改 ***************************************************/
                    swineryDayAgemap.put("preSwineryId", porkMoveInModel.getPreSwineryId());
                    swineryDayAgemap.put("swineryId", porkMoveInModel.getSwineryId());
                    swineryDayAgemap.put("totalNum", porkMoveInModel.getTotalNum());
                    swineryDayageMaps.add(swineryDayAgemap);
                    /******************************** 2017-2-16 yangy 入场批次日龄修改 ***************************************************/
                    for (int i = 0; i < porkMoveInModel.getTotalNum(); i++) {
                        Double totalPrice = porkMoveInModel.getAveragePrice() == null ? 0 : porkMoveInModel.getAveragePrice();
                        Double totalWeight = porkMoveInModel.getAverageWeight() == null ? 0 : porkMoveInModel.getAverageWeight();
                        Double saleNum = porkMoveInModel.getTotalNum() == null ? 0 : porkMoveInModel.getTotalNum();

                        Double leavePrice = BigDecimalUtil.bigDecimalDivide(totalPrice, saleNum, 2);
                        Double leaveWeight = BigDecimalUtil.bigDecimalDivide(totalWeight, saleNum, 2);
                        Double num = porkMoveInModel.getTotalNum() - 1;

                        // 入场view组装
                        PigMoveInView pigMoveInModel = new PigMoveInView();
                        if (i != num) {
                            pigMoveInModel.setEnterWeight(leaveWeight);
                            pigMoveInModel.setOnPrice(leavePrice);
                        } else {
                            Double price = BigDecimalUtil.bigDecimalMultiply(leavePrice, Double.valueOf(num));
                            Double weight = BigDecimalUtil.bigDecimalMultiply(leaveWeight, Double.valueOf(num));
                            pigMoveInModel.setEnterWeight(BigDecimalUtil.bigDecimalSubtract(totalWeight, weight));
                            pigMoveInModel.setOnPrice(BigDecimalUtil.bigDecimalSubtract(totalPrice, price));
                        }

                        // 获取耳号
                        // String earBrand = ParamUtil.getBCode("EAR_BRAND", getEmployeeId(), getCompanyId(), getFarmId());
                        pigMoveInModel.setOptType(paraPigMoveInModel.getEnterType());
                        pigMoveInModel.setLineNumber(porkMoveInModel.getPreLineNumber());
                        pigMoveInModel.setRowId(null);
                        pigMoveInModel.setBusinessCode(businessCode);
                        pigMoveInModel.setNotes(porkMoveInModel.getNotes());
                        pigMoveInModel.setOriginFlag("S");
                        pigMoveInModel.setOriginApp("XN1.0");
                        pigMoveInModel.setCompanyId(getCompanyId());
                        pigMoveInModel.setFarmId(getFarmId());
                        pigMoveInModel.setLineId(porkMoveInModel.getLineId());
                        pigMoveInModel.setSwineryId(porkMoveInModel.getSwineryId());
                        pigMoveInModel.setHouseId(porkMoveInModel.getHouseId());
                        pigMoveInModel.setPigPenId(porkMoveInModel.getPigpenId());
                        // pigMoveInModel.setEarBrand(earBrand);
                        pigMoveInModel.setEarShort("");
                        pigMoveInModel.setEarThorn("");
                        pigMoveInModel.setElectronicEarNo("");
                        pigMoveInModel.setFatherEar("");
                        pigMoveInModel.setMotherEar("");
                        pigMoveInModel.setBoardSowId(0l);
                        pigMoveInModel.setMaterialId(porkMoveInModel.getMaterialId());
                        pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                        pigMoveInModel.setSex(sex);
                        pigMoveInModel.setBreedId(breedId);
                        pigMoveInModel.setStrain(strain);
                        pigMoveInModel.setPigClass(porkMoveInModel.getPigClass());
                        pigMoveInModel.setBodyCondition(bodyCondition);
                        pigMoveInModel.setParity(0l);
                        pigMoveInModel.setBirthParity(0l);
                        pigMoveInModel.setHouseNum(0l);
                        pigMoveInModel.setBirthDate(birthDate);
                        pigMoveInModel.setBirthWeight(birthWeight);
                        pigMoveInModel.setEnterDate(paraPigMoveInModel.getBillDate());
                        // pigMoveInModel.setEnterWeight(porkMoveInModel.getAverageWeight() == null ? 0 : porkMoveInModel.getAverageWeight());
                        // pigMoveInModel.setOnPrice(porkMoveInModel.getAveragePrice() == null ? 0 : porkMoveInModel.getAveragePrice());
                        pigMoveInModel.setEnterParity(0l);
                        pigMoveInModel.setMewDayAge(0l);
                        pigMoveInModel.setMewWeight(0);
                        pigMoveInModel.setLeftTeatNum(leftTeatNum);
                        pigMoveInModel.setRightTeatNum(rightTeatNum);
                        pigMoveInModel.setSupplierId(porkMoveInModel.getSupplierId());
                        pigMoveInModel.setOrigin("2");
                        pigMoveInModel.setBillId(billId);
                        pigMoveInModel.setCreateId(getEmployeeId());
                        pigMoveInModel.setEventName(eventName);
                        pigMoveInModel.setWorker(getEmployeeId());
                        pigMoveInModel.setRelationPigId(Maps.getLong(porkList.get(i), "pigId"));
                        pigMoveInViews.add(pigMoveInModel);
                    }
                }
                /******************************** 2017-2-16 yangy 入场批次日龄修改 ***************************************************/
                List<Map<String, Object>> removeSwineryDayageMaps = new ArrayList<Map<String, Object>>();
                if (swineryDayageMaps != null && !swineryDayageMaps.isEmpty()) {
                    if (swineryDayageMaps.size() > 1) {
                        for (int i = 0; i < swineryDayageMaps.size(); i++) {
                            long preSwineryId = Maps.getLong(swineryDayageMaps.get(i), "preSwineryId");
                            long swineryId = Maps.getLong(swineryDayageMaps.get(i), "swineryId");
                            for (int j = i + 1; j < swineryDayageMaps.size(); j++) {
                                long _preSwineryId = Maps.getLong(swineryDayageMaps.get(j), "preSwineryId");
                                long _swineryId = Maps.getLong(swineryDayageMaps.get(j), "swineryId");
                                if (preSwineryId == _preSwineryId && swineryId == _swineryId) {
                                    long totalNum = Maps.getLong(swineryDayageMaps.get(i), "totalNum");
                                    long totalNum_ = Maps.getLong(swineryDayageMaps.get(j), "totalNum");
                                    swineryDayageMaps.get(i).put("totalNum", totalNum + totalNum_);
                                    removeSwineryDayageMaps.add(swineryDayageMaps.get(j));
                                }
                            }
                        }
                        swineryDayageMaps.removeAll(removeSwineryDayageMaps);
                    }
                    SwineryDayageChangeModel swineryDayageChangeModel = new SwineryDayageChangeModel();
                    SwineryDayageChangeModel fromSwineryDayageChangeModel = new SwineryDayageChangeModel();
                    int fromIndex = 0;
                    int toIndex = 0;
                    for (Map<String, Object> map : swineryDayageMaps) {
                        SwineryModel preswineryModel = swineryMapper.searchById(Maps.getLongClass(map, "preSwineryId"));
                        SwineryModel swineryModel = swineryMapper.searchById(Maps.getLongClass(map, "swineryId"));
                        SqlCon preSwienryInfoSqlCon = new SqlCon();
                        preSwienryInfoSqlCon.addMainSql(" SELECT  IFNULL(COUNT(*),0) porkPigNum FROM pp_l_pig T0 ");
                        preSwienryInfoSqlCon.addMainSql(
                                " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS IN (12,13,14,15,16) AND  T0.PIG_TYPE = '3'");
                        preSwienryInfoSqlCon.addCondition(getFarmId(), " AND T0.FARM_ID=? ");
                        preSwienryInfoSqlCon.addCondition(Maps.getLong(map, "swineryId"), " AND T0.SWINERY_ID=? ");
                        Map<String, String> sqlMap = new HashMap<String, String>();
                        sqlMap.put("sql", preSwienryInfoSqlCon.getCondition());
                        Map<String, Object> porkPigNumMap = paramMapper.getObjectInfos(sqlMap).get(0);
                        Date preLastOperateDate = preswineryModel.getLastOperateDate();
                        double preSwineryDayage = preswineryModel.getSwineryDayage();
                        Date lastOperateDate = swineryModel.getLastOperateDate();
                        double swineryDayage = swineryModel.getSwineryDayage();
                        double porkPigNum = Maps.getLong(porkPigNumMap, "porkPigNum");
                        double totalNum = Maps.getLongClass(map, "totalNum");
                        Date enterDate = paraPigMoveInModel.getEnterDate();
                        double newSwineryDayage = ((TimeUtil.getday(enterDate, lastOperateDate) + swineryDayage) * porkPigNum + (TimeUtil.getday(
                                enterDate, preLastOperateDate) + preSwineryDayage) * totalNum) / (porkPigNum + totalNum);
                        SqlCon sqlCon = new SqlCon();
                        sqlCon.addMainSql(" UPDATE pp_m_swinery SET ");
                        sqlCon.addCondition(newSwineryDayage, " SWINERY_DAYAGE=? ");
                        sqlCon.addCondition(enterDate, ", LAST_OPERATE_DATE='" + "?" + "' ");
                        sqlCon.addCondition(Maps.getLongClass(map, "swineryId"), " WHERE ROW_ID=? ");
                        setSql(swineryMapper, sqlCon);
                        /*********************************************** 2017-5-31 批次日龄修改 **************************************************/
                        double oldSwineryDayAge = ParamUtil.getSwineryAgeDouble(Maps.getLongClass(map, "preSwineryId"), enterDate);
                        swineryDayageChangeModel.setOldSwineryDayage(oldSwineryDayAge);
                        swineryDayageChangeModel.setOldLastOperateDate(enterDate);
                        /*********************************************** 2017-5-31 批次日龄修改 **************************************************/

                        swineryDayageChangeModel.setStatus(CommonConstants.STATUS);
                        swineryDayageChangeModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        swineryDayageChangeModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                        swineryDayageChangeModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                        swineryDayageChangeModel.setFarmId(getFarmId());
                        swineryDayageChangeModel.setCompanyId(getCompanyId());
                        swineryDayageChangeModel.setBillId(billId);
                        swineryDayageChangeModel.setSwineryId(Maps.getLongClass(map, "swineryId"));
                        // swineryDayageChangeModel.setOldSwineryDayage((long) preSwineryDayage);
                        // swineryDayageChangeModel.setOldLastOperateDate(preLastOperateDate);
                        swineryDayageChangeModel.setNewSwineryDayage(swineryDayage);
                        swineryDayageChangeModel.setNewLastOperateDate(lastOperateDate);
                        swineryDayageChangeModel.setPorkPigNum((long) porkPigNum);
                        swineryDayageChangeModel.setChangePigNum((long) totalNum);
                        swineryDayageChangeModel.setChangeType(1L);
                        swineryDayageChangeModel.setEventDate(enterDate);
                        swineryDayageChangeModel.setWorker(getEmployeeId());
                        swineryDayageChangeModel.setCreateId(getEmployeeId());
                        swineryDayageChangeModel.setCreateDate(new Date());
                        swineryDayageChangeMapper.insert(swineryDayageChangeModel);

                        /**************************************** 2017-9-6 转出批次记录猪只头数 *****************************************/
                        fromSwineryDayageChangeModel.setStatus(CommonConstants.STATUS);
                        fromSwineryDayageChangeModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        fromSwineryDayageChangeModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                        fromSwineryDayageChangeModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                        fromSwineryDayageChangeModel.setFarmId(getFarmId());
                        fromSwineryDayageChangeModel.setCompanyId(getCompanyId());
                        fromSwineryDayageChangeModel.setBillId(billId);
                        fromSwineryDayageChangeModel.setSwineryId(Maps.getLongClass(map, "preSwineryId"));
                        fromSwineryDayageChangeModel.setChangeType(2L);
                        fromSwineryDayageChangeModel.setRolloutPigNum((long) totalNum);
                        fromSwineryDayageChangeModel.setEventDate(enterDate);
                        fromSwineryDayageChangeModel.setWorker(getEmployeeId());
                        fromSwineryDayageChangeModel.setCreateId(getEmployeeId());
                        fromSwineryDayageChangeModel.setCreateDate(new Date());
                        swineryDayageChangeMapper.insert(fromSwineryDayageChangeModel);
                        /**************************************** 2017-9-6 转出批次记录猪只头数 *****************************************/
                        toIndex = fromIndex + ((int) Maps.getDouble(map, "totalNum"));
                        if (pigMoveInViews != null && !pigMoveInViews.isEmpty()) {
                            List<PigMoveInView> pigMoveInViewList = pigMoveInViews.subList(fromIndex, toIndex);
                            for (PigMoveInView pigMoveInModel : pigMoveInViewList) {
                                pigEventMapper.pigMoveIn(pigMoveInModel);
                                if (!"0".equals(pigMoveInModel.getErrorCode())) {
                                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
                                }
                            }
                        }
                        fromIndex = toIndex;
                    }
                }
                /******************************** 2017-2-16 yangy 入场批次日龄修改 ***************************************************/
            } else {
                pigMoveIns = getJsonList(pigMoveIn, PigMoveInView.class);
                if (pigMoveIns.size() == 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "未添加明细不能提交-后台！");
                }

                for (Iterator<PigMoveInView> it = pigMoveIns.iterator(); it.hasNext();) {
                    PigMoveInView pigMoveInModel = (PigMoveInView) it.next();
                    pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                    if (pigMoveInModel.getMaterialId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只物料不能为空！");
                    }
                    if (pigMoveInModel.getPigType() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只【类别】不能为空！");
                    }
                    if (pigMoveInModel.getHouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只猪舍不能为空！");
                    }
                    if (pigMoveInModel.getPigClass() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只状态不能为空！");
                    }
                    if (pigMoveInModel.getBirthDate() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只出生日期不能为空！");
                    }

                    if ("2".equals(pigMoveInModel.getPigType()) && pigMoveInModel.getParity() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只母猪，胎次不为空！");
                    }

                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addConditionWithNull(pigMoveInModel.getMaterialId(), " AND MATERIAL_ID = ? ");
                    sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");

                    // 留种标识 ，默认为N
                    if (pigMoveInModel.getSeedFlag() == null) {
                        pigMoveInModel.setSeedFlag("N");
                    }
                    if (PigConstants.PIG_TYPE_BOAR.equals(paraPigMoveInModel.getPigType()) && "N".equals(pigMoveInModel.getSeedFlag())) {
                        BoarModel boarModel = getModel(boarMapper, sqlCon);
                        Date tempDate = TimeUtil.dateAddDay(pigMoveInModel.getBirthDate(), boarModel.getProductionDayAge().intValue());
                        if (PigConstants.PIG_CLASS_SCGZ == pigMoveInModel.getPigClass() && TimeUtil.compareDate(createDate, tempDate,
                                Calendar.DATE) < 0) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "耳号为:" + pigMoveInModel.getEarBrand() + "的公猪未达到生产日龄!");
                        }

                        breedId = boarModel.getBreedId();
                        strain = boarModel.getStrain();
                        birthWeight = boarModel.getBirthWeight();
                        bodyCondition = boarModel.getBodyCondition();
                        leftTeatNum = boarModel.getLeftTeatNum();
                        rightTeatNum = boarModel.getRightTeatNum();
                        sex = PigConstants.PIG_SEX_MALE;
                        pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                    } else if (PigConstants.PIG_TYPE_SOW.equals(paraPigMoveInModel.getPigType()) && "N".equals(pigMoveInModel.getSeedFlag())) {
                        SowModel sowModel = getModel(sowMapper, sqlCon);
                        breedId = sowModel.getBreedId();
                        strain = sowModel.getStrain();
                        birthWeight = sowModel.getBirthWeight();
                        bodyCondition = sowModel.getBodyCondition();
                        leftTeatNum = sowModel.getLeftTeatNum();
                        rightTeatNum = sowModel.getRightTeatNum();
                        // 母猪入场胎次
                        pigMoveInModel.setEnterParity(pigMoveInModel.getParity());
                        sex = PigConstants.PIG_SEX_FEMALE;
                        pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                    } else if (PigConstants.PIG_TYPE_BOAR.equals(paraPigMoveInModel.getPigType()) && "Y".equals(pigMoveInModel.getSeedFlag())) {
                        PorkPigModel porkPigModel = getModel(porkPigMapper, sqlCon);
                        // 留种公猪(新增：如果选了种猪的pig_class,则默认做了选种，转成后备猪)
                        if (porkPigModel != null) {
                            // 如果没有种公猪主数据，但猪只状态选择了后备猪
                            if (porkPigModel.getStockBoarId() == null && PigConstants.PIG_CLASS_HBGZ == pigMoveInModel.getPigClass()) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请先选择【" + CacheUtil.getName(String.valueOf(porkPigModel
                                        .getMaterialId()), NameEnum.MATERIAL_NAME) + "】的肉猪主数据的种公猪种母猪！");
                            }

                            // 转成后备公猪
                            if (PigConstants.PIG_CLASS_HBGZ == pigMoveInModel.getPigClass()) {
                                SqlCon hbSql = new SqlCon();
                                hbSql.addCondition(porkPigModel.getStockBoarId(), " AND MATERIAL_ID = ?");
                                BoarModel hbModel = getModel(boarMapper, hbSql);
                                breedId = hbModel.getBreedId();
                                strain = hbModel.getStrain();
                                birthWeight = hbModel.getBirthWeight();
                                bodyCondition = hbModel.getBodyCondition();
                                leftTeatNum = hbModel.getLeftTeatNum();
                                rightTeatNum = hbModel.getRightTeatNum();
                                sex = PigConstants.PIG_SEX_MALE;
                                pigMoveInModel.setPigType(PigConstants.PIG_TYPE_BOAR);
                                pigMoveInModel.setMaterialId(porkPigModel.getStockBoarId());
                            } else if (PigConstants.PIG_CLASS_YH == pigMoveInModel.getPigClass()) {
                                // 留种公猪
                                breedId = porkPigModel.getBreedId();
                                strain = porkPigModel.getStrain();
                                birthWeight = porkPigModel.getBirthWeight();
                                bodyCondition = porkPigModel.getBodyCondition();
                                leftTeatNum = porkPigModel.getLeftTeatNum();
                                rightTeatNum = porkPigModel.getRightTeatNum();
                                sex = PigConstants.PIG_SEX_MALE;
                                pigMoveInModel.setPigType(PigConstants.PIG_TYPE_PORK);

                            }

                        } else {
                            // 选种公猪
                            BoarModel selectBoarModel = getModel(boarMapper, sqlCon);
                            breedId = selectBoarModel.getBreedId();
                            strain = selectBoarModel.getStrain();
                            birthWeight = selectBoarModel.getBirthWeight();
                            bodyCondition = selectBoarModel.getBodyCondition();
                            leftTeatNum = selectBoarModel.getLeftTeatNum();
                            rightTeatNum = selectBoarModel.getRightTeatNum();
                            sex = PigConstants.PIG_SEX_MALE;
                            pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                        }

                    } else if (PigConstants.PIG_TYPE_SOW.equals(paraPigMoveInModel.getPigType()) && "Y".equals(pigMoveInModel.getSeedFlag())) {
                        PorkPigModel porkPigModel = getModel(porkPigMapper, sqlCon);
                        // 留种母(新增：如果选了种猪的pig_class,则默认做了选种，转成后备猪)
                        if (porkPigModel != null) {
                            if (porkPigModel.getBroodSowId() == null && PigConstants.PIG_CLASS_HBMZ == pigMoveInModel.getPigClass()) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请先选择【" + CacheUtil.getName(String.valueOf(porkPigModel
                                        .getMaterialId()), NameEnum.MATERIAL_NAME) + "】的肉猪主数据的种公猪种母猪！");
                            }
                            // 转成后备母猪
                            if (PigConstants.PIG_CLASS_HBMZ == pigMoveInModel.getPigClass()) {
                                SqlCon hbSql = new SqlCon();
                                hbSql.addCondition(porkPigModel.getBroodSowId(), " AND MATERIAL_ID = ?");
                                SowModel hbModel = getModel(sowMapper, hbSql);
                                breedId = hbModel.getBreedId();
                                strain = hbModel.getStrain();
                                birthWeight = hbModel.getBirthWeight();
                                bodyCondition = hbModel.getBodyCondition();
                                leftTeatNum = hbModel.getLeftTeatNum();
                                rightTeatNum = hbModel.getRightTeatNum();
                                sex = PigConstants.PIG_SEX_MALE;
                                pigMoveInModel.setPigType(PigConstants.PIG_TYPE_SOW);
                                pigMoveInModel.setMaterialId(porkPigModel.getBroodSowId());
                            } else {
                                breedId = porkPigModel.getBreedId();
                                strain = porkPigModel.getStrain();
                                birthWeight = porkPigModel.getBirthWeight();
                                bodyCondition = porkPigModel.getBodyCondition();
                                leftTeatNum = porkPigModel.getLeftTeatNum();
                                rightTeatNum = porkPigModel.getRightTeatNum();
                                sex = PigConstants.PIG_SEX_FEMALE;
                                pigMoveInModel.setPigType(PigConstants.PIG_TYPE_PORK);
                            }

                        } else {
                            // 选种母
                            SowModel selectSowModel = getModel(sowMapper, sqlCon);
                            breedId = selectSowModel.getBreedId();
                            strain = selectSowModel.getStrain();
                            birthWeight = selectSowModel.getBirthWeight();
                            bodyCondition = selectSowModel.getBodyCondition();
                            leftTeatNum = selectSowModel.getLeftTeatNum();
                            rightTeatNum = selectSowModel.getRightTeatNum();
                            // 母猪入场胎次
                            pigMoveInModel.setEnterParity(pigMoveInModel.getParity());
                            sex = PigConstants.PIG_SEX_FEMALE;
                            pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                        }
                    }
                    // else {
                    // PorkPigModel porkPigModel = getModel(porkPigMapper, sqlCon);
                    // if (pigMoveInModel.getSwineryId() == null) {
                    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "肉猪入场猪群不能为空！");
                    // }
                    // breedId = porkPigModel.getBreedId();
                    // strain = porkPigModel.getStrain();
                    // birthWeight = porkPigModel.getBirthWeight();
                    // bodyCondition = porkPigModel.getBodyCondition();
                    // leftTeatNum = porkPigModel.getLeftTeatNum();
                    // rightTeatNum = porkPigModel.getRightTeatNum();
                    // sex = PigConstants.PIG_SEX_MIX;
                    // }
                    // 入场方式需要记录 后台 对应 OPT_TYPE
                    pigMoveInModel.setOptType(paraPigMoveInModel.getEnterType());
                    if (pigMoveInModel.getEarBrand() != null) {
                        pigMoveInModel.setEarBrand(pigMoveInModel.getEarBrand().toUpperCase());
                    }
                    if (pigMoveInModel.getEarShort() != null) {
                        pigMoveInModel.setEarShort(pigMoveInModel.getEarShort().toUpperCase());
                    }
                    if (pigMoveInModel.getFatherEar() != null) {
                        pigMoveInModel.setFatherEar(pigMoveInModel.getFatherEar().toUpperCase());
                    }
                    if (pigMoveInModel.getMotherEar() != null) {
                        pigMoveInModel.setMotherEar(pigMoveInModel.getMotherEar().toUpperCase());
                    }
                    pigMoveInModel.setCompanyId(getCompanyId());
                    pigMoveInModel.setFarmId(getFarmId());
                    pigMoveInModel.setBillId(billId);
                    pigMoveInModel.setBreedId(breedId);
                    pigMoveInModel.setStrain(strain);
                    pigMoveInModel.setBodyCondition(bodyCondition);
                    pigMoveInModel.setBirthParity(0l);
                    pigMoveInModel.setHouseNum(0l);
                    if (pigMoveInModel.getBirthWeight() == null || pigMoveInModel.getBirthWeight().compareTo(0D) == 0) {
                        pigMoveInModel.setBirthWeight(birthWeight);
                    }
                    if (pigMoveInModel.getLeftTeatNum() == null || pigMoveInModel.getLeftTeatNum().compareTo(0L) == 0) {
                        pigMoveInModel.setLeftTeatNum(leftTeatNum);
                    }
                    if (pigMoveInModel.getRightTeatNum() == null || pigMoveInModel.getRightTeatNum().compareTo(0L) == 0) {
                        pigMoveInModel.setRightTeatNum(rightTeatNum);
                    }
                    pigMoveInModel.setOrigin("2");
                    pigMoveInModel.setEnterDate(paraPigMoveInModel.getBillDate());
                    pigMoveInModel.setCreateId(getEmployeeId());
                    pigMoveInModel.setSex(sex);
                    pigMoveInModel.setWorker(getEmployeeId());
                    pigMoveInModel.setEventName(eventName);
                    pigMoveInModel.setLineNumber(pigMoveInModel.getPreLineNumber());

                    // 母猪和公猪 根据单据入场需要将swinery_id置空
                    if (paraPigMoveInModel.getRelateBillId() != null) {
                        pigMoveInModel.setSwineryId(null);
                        SqlCon leaveSql = new SqlCon();
                        leaveSql.addMainSql("UPDATE pp_l_bill_leave SET");
                        leaveSql.addMainSql(" IS_MOVE_IN = 'Y'");
                        leaveSql.addMainSql(" WHERE DELETED_FLAG = 0");
                        leaveSql.addCondition(pigMoveInModel.getRelationPigId(), " AND PIG_ID = ?");
                        setSql(leaveMapper, leaveSql);
                    }

                    pigEventMapper.pigMoveIn(pigMoveInModel);
                    if (!"0".equals(pigMoveInModel.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
                    }
                    // 特殊：如果是妊娠母猪的单据入场：需要虚拟公猪并补上配种记录
                    if (PigConstants.PIG_TYPE_SOW.equals(pigMoveInModel.getPigType()) && PigConstants.PIG_CLASS_RS == pigMoveInModel.getPigClass()) {
                        sowRSBillMoveIn(pigMoveInModel);
                    }
                }
            }
        }
        // 批次入场
        if (PigConstants.PIG_MOVE_IN_OPT_TYPE_FILE.equals(paraPigMoveInModel.getEnterType())) {
            if (paraPigMoveInModel.getPigType() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只【类别】不能为空！");
            }
            if (paraPigMoveInModel.getTotalNum() == null || paraPigMoveInModel.getTotalNum() <= 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只数量不能小于或等于0！");
            }
            if (paraPigMoveInModel.getHouseId() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只猪舍不能为空！");
            }
            if (paraPigMoveInModel.getMaterialId() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只物料不能为空！");
            }
            if (paraPigMoveInModel.getPigClass() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只状态不能为空！");
            }
            Date birthDate = TimeUtil.dateAddDay(paraPigMoveInModel.getBillDate(), -paraPigMoveInModel.getDayAge());
            /* Double totalWeight = paraPigMoveInModel.getTotalWeight();
             * Double totalNum = paraPigMoveInModel.getTotalNum();
             * Double totalPrice = paraPigMoveInModel.getTotalPrice();
             * Double enterWeight = totalWeight == null ? 0 : totalWeight / totalNum > 0 ? totalNum : 1;
             * Double onPrice = totalPrice == null ? 0 : totalPrice / totalNum; */
            SqlCon sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(paraPigMoveInModel.getMaterialId(), " AND MATERIAL_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            if (PigConstants.PIG_TYPE_BOAR.equals(paraPigMoveInModel.getPigType())) {
                BoarModel boarModel = getModel(boarMapper, sqlCon);
                breedId = boarModel.getBreedId();
                strain = boarModel.getStrain();
                birthWeight = boarModel.getBirthWeight();
                bodyCondition = boarModel.getBodyCondition();
                leftTeatNum = boarModel.getLeftTeatNum();
                rightTeatNum = boarModel.getRightTeatNum();
                sex = PigConstants.PIG_SEX_MALE;
            } else if (PigConstants.PIG_TYPE_SOW.equals(paraPigMoveInModel.getPigType())) {
                SowModel cdSowModel = getModel(sowMapper, sqlCon);
                breedId = cdSowModel.getBreedId();
                strain = cdSowModel.getStrain();
                birthWeight = cdSowModel.getBirthWeight();
                bodyCondition = cdSowModel.getBodyCondition();
                leftTeatNum = cdSowModel.getLeftTeatNum();
                rightTeatNum = cdSowModel.getRightTeatNum();
                sex = PigConstants.PIG_SEX_FEMALE;
            } else {
                PorkPigModel cdPorkPigModel = getModel(porkPigMapper, sqlCon);
                breedId = cdPorkPigModel.getBreedId();
                strain = cdPorkPigModel.getStrain();
                birthWeight = cdPorkPigModel.getBirthWeight();
                bodyCondition = cdPorkPigModel.getBodyCondition();
                leftTeatNum = cdPorkPigModel.getLeftTeatNum();
                rightTeatNum = cdPorkPigModel.getRightTeatNum();
                sex = PigConstants.PIG_SEX_MIX;
                /******************************** 2017-2-16 yangy 入场批次日龄修改 ***************************************************/
                SqlCon preSwienryInfoSqlCon = new SqlCon();
                preSwienryInfoSqlCon.addMainSql(" SELECT  IFNULL(COUNT(*),0) porkPigNum FROM pp_l_pig T0 ");
                preSwienryInfoSqlCon.addMainSql(
                        " WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS IN (12,13,14,15,16) AND  T0.PIG_TYPE = '3'");
                preSwienryInfoSqlCon.addCondition(getFarmId(), " AND T0.FARM_ID=? ");
                preSwienryInfoSqlCon.addCondition(paraPigMoveInModel.getSwineryId(), " AND T0.SWINERY_ID=? ");
                Map<String, String> sqlMap = new HashMap<String, String>();
                sqlMap.put("sql", preSwienryInfoSqlCon.getCondition());
                Map<String, Object> porkPigNumMap = paramMapper.getObjectInfos(sqlMap).get(0);
                SwineryDayageChangeModel swineryDayageChangeModel = new SwineryDayageChangeModel();
                long porkPigNum = 0L;
                double newSwineryDayage = 0.0;
                double totalNum = 0L;
                SwineryModel swineryModel = swineryMapper.searchById(paraPigMoveInModel.getSwineryId());
                Date lastOperateDate = swineryModel.getLastOperateDate();
                Date enterDate = paraPigMoveInModel.getEnterDate();
                double swineryDayage = swineryModel.getSwineryDayage();
                totalNum = paraPigMoveInModel.getTotalNum();
                porkPigNum = Maps.getLong(porkPigNumMap, "porkPigNum");
                newSwineryDayage = (((TimeUtil.getday(enterDate, lastOperateDate) + swineryDayage) * porkPigNum + paraPigMoveInModel.getDayAge()
                        * totalNum) / (porkPigNum + totalNum));
                SqlCon updateSwinerySqlCon = new SqlCon();
                updateSwinerySqlCon.addMainSql(" UPDATE pp_m_swinery SET ");
                updateSwinerySqlCon.addCondition(newSwineryDayage, " SWINERY_DAYAGE=? ");
                updateSwinerySqlCon.addCondition(enterDate, " ,LAST_OPERATE_DATE='" + "?" + "' ");
                updateSwinerySqlCon.addCondition(paraPigMoveInModel.getSwineryId(), " WHERE ROW_ID=? ");
                setSql(swineryMapper, updateSwinerySqlCon);
                swineryDayageChangeModel.setStatus(CommonConstants.STATUS);
                swineryDayageChangeModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                swineryDayageChangeModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                swineryDayageChangeModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                swineryDayageChangeModel.setFarmId(getFarmId());
                swineryDayageChangeModel.setCompanyId(getCompanyId());
                swineryDayageChangeModel.setBillId(billId);
                swineryDayageChangeModel.setSwineryId(paraPigMoveInModel.getSwineryId());
                swineryDayageChangeModel.setOldSwineryDayage((double) paraPigMoveInModel.getDayAge());
                swineryDayageChangeModel.setOldLastOperateDate(paraPigMoveInModel.getEnterDate());
                swineryDayageChangeModel.setNewSwineryDayage(swineryDayage);
                swineryDayageChangeModel.setNewLastOperateDate(lastOperateDate);
                swineryDayageChangeModel.setPorkPigNum(porkPigNum);
                swineryDayageChangeModel.setChangePigNum((long) totalNum);
                swineryDayageChangeModel.setChangeType(1L);
                swineryDayageChangeModel.setEventDate(enterDate);
                swineryDayageChangeModel.setWorker(getEmployeeId());
                swineryDayageChangeModel.setCreateId(getEmployeeId());
                swineryDayageChangeModel.setCreateDate(new Date());
                swineryDayageChangeMapper.insert(swineryDayageChangeModel);

                /******************************** 2017-2-16 yangy 入场批次日龄修改 ***************************************************/
            }

            for (int i = 0; i < paraPigMoveInModel.getTotalNum(); i++) {

                // 获取耳号
                // String earBrand = ParamUtil.getBCode("EAR_BRAND", getEmployeeId(), getCompanyId(), getFarmId());
                PigMoveInView pigMoveInModel = new PigMoveInView();
                pigMoveInModel.setOptType(paraPigMoveInModel.getEnterType());
                pigMoveInModel.setLineNumber(Long.valueOf(i) + 1);
                pigMoveInModel.setRowId(null);
                pigMoveInModel.setBusinessCode(businessCode);
                pigMoveInModel.setNotes("");
                pigMoveInModel.setOriginFlag("S");
                pigMoveInModel.setOriginApp("XN1.0");
                pigMoveInModel.setCompanyId(getCompanyId());
                pigMoveInModel.setFarmId(getFarmId());
                pigMoveInModel.setLineId(paraPigMoveInModel.getLineId());
                pigMoveInModel.setSwineryId(paraPigMoveInModel.getSwineryId());
                pigMoveInModel.setHouseId(paraPigMoveInModel.getHouseId());
                pigMoveInModel.setPigPenId(paraPigMoveInModel.getPigpenId());
                // pigMoveInModel.setEarBrand(earBrand);
                pigMoveInModel.setEarShort("");
                pigMoveInModel.setEarThorn("");
                pigMoveInModel.setElectronicEarNo("");
                pigMoveInModel.setFatherEar("");
                pigMoveInModel.setMotherEar("");
                pigMoveInModel.setBoardSowId(0l);
                pigMoveInModel.setMaterialId(paraPigMoveInModel.getMaterialId());
                pigMoveInModel.setPigType(paraPigMoveInModel.getPigType());
                pigMoveInModel.setSex(sex);
                pigMoveInModel.setBreedId(breedId);
                pigMoveInModel.setStrain(strain);
                pigMoveInModel.setPigClass(paraPigMoveInModel.getPigClass());
                pigMoveInModel.setBodyCondition(bodyCondition);
                pigMoveInModel.setParity(0l);
                pigMoveInModel.setBirthParity(0l);
                pigMoveInModel.setHouseNum(0l);
                pigMoveInModel.setBirthDate(birthDate);
                pigMoveInModel.setBirthWeight(birthWeight);
                pigMoveInModel.setEnterDate(paraPigMoveInModel.getBillDate());
                pigMoveInModel.setEnterWeight(paraPigMoveInModel.getAverageWeight() == null ? 0 : paraPigMoveInModel.getAverageWeight());
                pigMoveInModel.setOnPrice(paraPigMoveInModel.getAveragePrice() == null ? 0 : paraPigMoveInModel.getAveragePrice());
                pigMoveInModel.setEnterParity(0l);
                pigMoveInModel.setMewDayAge(0l);
                pigMoveInModel.setMewWeight(0);
                pigMoveInModel.setLeftTeatNum(leftTeatNum);
                pigMoveInModel.setRightTeatNum(rightTeatNum);
                pigMoveInModel.setSupplierId(paraPigMoveInModel.getSupplierId());
                pigMoveInModel.setOrigin("2");
                pigMoveInModel.setBillId(billId);
                pigMoveInModel.setCreateId(getEmployeeId());
                pigMoveInModel.setEventName(eventName);
                pigMoveInModel.setWorker(getEmployeeId());
                pigEventMapper.pigMoveIn(pigMoveInModel);
                if (!"0".equals(pigMoveInModel.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
                }
            }
        }

        // 往PigEventRelates插入数据
        SqlCon insertPigEventRelatesSqlCon = new SqlCon();
        insertPigEventRelatesSqlCon.addMainSql(
                "SELECT '1' AS status, 0 AS deletedFlag, FARM_ID AS farmId, COMPANY_ID AS companyId, SWINERY_ID AS swineryId, ROW_ID AS pigId, LINE_ID AS lineId, HOUSE_ID AS houseId, PIGPEN_ID AS pigpenId,(SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE STATUS = '1' AND DELETED_FLAG = '0' AND ROW_ID = EAR_CODE_ID) AS earBrand, PIG_TYPE AS pigType, SEX AS sex, PIG_CLASS AS pigClass, PARITY AS parity, BILL_ID AS billId,LAST_EVENT_DATE AS eventDate FROM PP_L_PIG");
        insertPigEventRelatesSqlCon.addMainSql(" WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        insertPigEventRelatesSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        insertPigEventRelatesSqlCon.addCondition(billId, " AND BILL_ID = ? ");

        List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, insertPigEventRelatesSqlCon);

        if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
            pigEventRelatesMapper.inserts(pigEventRelatesModelList);
        }
        // 往PigEventRelates插入数据(虚拟公猪)
        SqlCon xnSql = new SqlCon();
        xnSql.addMainSql(" SELECT row_id rowId FROM pp_l_pig WHERE DELETED_FLAG = 0");
        xnSql.addCondition(billId, " AND LAST_BILL_ID = ?");
        xnSql.addCondition(billId, " AND BILL_ID <> ?");
        xnSql.addCondition(getFarmId(), " AND FARM_ID = ?");
        List<PigModel> xnlist = setSql(pigMapper, xnSql);
        for (PigModel pigModel : xnlist) {
            this.insertPigEventRelates(pigModel.getRowId());
        }

        // System.currentTimeMillis();
        // double time = (System.currentTimeMillis() - begin) / 1000;
        // System.out.println("插入共花费时间" + time + "s");

        Date currentDate = new Date();
        if (paraPigMoveInModel.getRelateBillId() == null) {
            // START HANA
            // 采购单的isToHana
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            if (isToHana) {
                // 外购(无销售单)
                Map<String, String> branchAndDeptMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                String branchId = Maps.getString(branchAndDeptMap, HanaUtil.MTC_BranchID);
                String deptId = Maps.getString(branchAndDeptMap, HanaUtil.MTC_DeptID);

                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql("SELECT p.LINE_NUMBER lineNumber,p.HOUSE_ID houseId,p.BREED_ID breedId,p.SEX sex,p.PIG_CLASS pigClass,");
                sqlCon.addMainSql(" p.ENTER_WEIGHT enterWeight,p.ON_PRICE onPrice,p.SAP_FIXED_ASSETS_EARBRAND sapFixedAssetsEarbrand,");
                sqlCon.addMainSql(" p.SUPPLIER_ID supplierId,c.EAR_BRAND earBrand");
                sqlCon.addMainSql(" FROM pp_l_pig p");
                sqlCon.addMainSql(" INNER JOIN pp_l_ear_code c");
                sqlCon.addMainSql(" ON p.EAR_CODE_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
                sqlCon.addMainSql(" WHERE p.DELETED_FLAG = 0");
                sqlCon.addCondition(getFarmId(), " AND p.FARM_ID = ?");
                sqlCon.addCondition(billId, " AND p.LAST_BILL_ID = ?");
                Map<String, String> sqlMap = new HashMap<>();
                sqlMap.put("sql", sqlCon.getCondition());
                List<Map<String, String>> outPigList = paramMapper.getInfos(sqlMap);

                if (Maps.getLongClass(outPigList.get(0), "supplierId") == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "上传至SAP的供应商不能为空");
                }
                // 商品猪采购
                HanaSaleBill hanaPurchaseSaleBill = new HanaSaleBill();
                // 分公司编码
                hanaPurchaseSaleBill.setMTC_BranchID(branchId);
                // 新农+单据编号(采购单编号+"-"+销售单的ROW_ID)
                hanaPurchaseSaleBill.setMTC_BaseEntry(branchId + "-" + String.valueOf(billId) + "-" + paraPigMoveInModel.getBusinessCode());
                // 出库日期
                hanaPurchaseSaleBill.setMTC_DocDate(paraPigMoveInModel.getBillDate());
                // 表行
                List<HanaSaleBillDetail> hanaPurchaseSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
                hanaPurchaseSaleBill.setDetailList(hanaPurchaseSaleBillDetailList);
                // 业务伙伴编号
                if (Maps.getLongClass(outPigList.get(0), "supplierId") != null) {
                    hanaPurchaseSaleBill.setMTC_CardCode(HanaUtil.getMTC_SaleCardCode(Maps.getLong(outPigList.get(0), "supplierId")));
                }

                // 生产猪采购
                HanaPigProduct hanaPurchasePigProduct = new HanaPigProduct();
                // 分公司编码
                hanaPurchasePigProduct.setMTC_BranchID(branchId);
                // 新农+单据编号(采购单编号+"-"+销售单的ROW_ID)
                hanaPurchasePigProduct.setMTC_BaseEntry(branchId + "-" + String.valueOf(billId) + "-" + paraPigMoveInModel.getBusinessCode());
                // 业务伙伴编号
                if (Maps.getLongClass(outPigList.get(0), "supplierId") != null) {
                    hanaPurchasePigProduct.setMTC_CardCode(HanaUtil.getMTC_SaleCardCode(Maps.getLong(outPigList.get(0), "supplierId")));
                }
                // 出库日期
                hanaPurchasePigProduct.setMTC_DocDate(paraPigMoveInModel.getBillDate());
                // 表行
                List<HanaPigProductDetail> hanaPurchasePigProductDetailList = new ArrayList<HanaPigProductDetail>();
                hanaPurchasePigProduct.setDetailList(hanaPurchasePigProductDetailList);

                List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

                for (Map<String, String> pigMap : outPigList) {
                    if (isProductPig(Maps.getLong(pigMap, "pigClass"))) {
                        // 生产猪
                        // 生产猪采购
                        HanaPigProductDetail hanaPurchasePigProductDetail = new HanaPigProductDetail();
                        hanaPurchasePigProductDetail.setMTC_BranchID(branchId);
                        hanaPurchasePigProductDetail.setMTC_DeptID(deptId);
                        hanaPurchasePigProductDetail.setMTC_BaseEntry(String.valueOf(billId));
                        hanaPurchasePigProductDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
                        hanaPurchasePigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PP);
                        if (PigConstants.PIG_SEX_MALE.equals(Maps.getString(pigMap, "sex"))) {
                            hanaPurchasePigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR);
                        } else {
                            hanaPurchasePigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW);
                        }
                        hanaPurchasePigProductDetail.setMTC_BatchNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                        hanaPurchasePigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                        hanaPurchasePigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                        hanaPurchasePigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                        hanaPurchasePigProductDetail.setMTC_Qty("1");
                        hanaPurchasePigProductDetail.setMTC_Price(Maps.getString(pigMap, "onPrice"));
                        hanaPurchasePigProductDetail.setMTC_Total(Maps.getString(pigMap, "onPrice"));
                        hanaPurchasePigProductDetailList.add(hanaPurchasePigProductDetail);

                        // 生产猪卡片
                        MTC_OITM mtcOITM = new MTC_OITM();
                        mtcOITM.setMTC_ItemCode(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                        mtcOITM.setMTC_ItemName(CacheUtil.getName(Maps.getString(pigMap, "breedId"), NameEnum.BREED_NAME) + "." + Maps.getString(
                                pigMap, "earBrand"));
                        mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(Maps.getString(pigMap, "sex")));
                        mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(branchId, Maps.getString(pigMap, "sex")));
                        mtcOITM.setMTC_DeptID(deptId);
                        mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));

                        // 卡片
                        MTC_ITFC mtcCardITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcCardITFC.setMTC_Branch(hanaPurchaseSaleBill.getMTC_BranchID());
                        // 业务日期
                        mtcCardITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:生产猪采购 - B
                        mtcCardITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3021);
                        // 新农+单据编号
                        mtcCardITFC.setMTC_DocNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                        // 优先级
                        mtcCardITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcCardITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                        // 创建日期
                        mtcCardITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcCardITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcCardITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFileProduct = HanaJacksonUtil.objectToJackson(mtcOITM);
                        mtcCardITFC.setMTC_DataFile(jsonDataFileProduct);
                        // JSON文件长度
                        mtcCardITFC.setMTC_DataFileLen(jsonDataFileProduct.length());
                        mtcITFCList.add(mtcCardITFC);

                    } else {
                        // 商品猪采购
                        HanaSaleBillDetail hanaSaleBillDetail = new HanaSaleBillDetail();
                        hanaSaleBillDetail.setMTC_BranchID(branchId);
                        hanaSaleBillDetail.setMTC_DeptID(deptId);
                        hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(billId));
                        hanaSaleBillDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
                        hanaSaleBillDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PI);
                        hanaSaleBillDetail.setMTC_ItemCode(HanaUtil.getMTC_ItemCodeOfPigClass(Maps.getLong(pigMap, "pigClass"), getFarmId(), Maps
                                .getLong(pigMap, "houseId")));
                        hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                        hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                        hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                        hanaSaleBillDetail.setMTC_Qty("1");
                        hanaSaleBillDetail.setMTC_Weight(Maps.getString(pigMap, "enterWeight"));
                        hanaSaleBillDetail.setMTC_Amount(Maps.getString(pigMap, "onPrice"));
                        hanaPurchaseSaleBillDetailList.add(hanaSaleBillDetail);
                    }
                }

                if (!hanaPurchaseSaleBillDetailList.isEmpty()) {
                    // 采购
                    MTC_ITFC mtcPurhcaseITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcPurhcaseITFC.setMTC_Branch(hanaPurchaseSaleBill.getMTC_BranchID());
                    // 业务日期
                    mtcPurhcaseITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:猪只采购
                    mtcPurhcaseITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
                    // 新农+单据编号
                    mtcPurhcaseITFC.setMTC_DocNum(hanaPurchaseSaleBill.getMTC_BaseEntry());
                    // 优先级
                    mtcPurhcaseITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcPurhcaseITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcPurhcaseITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcPurhcaseITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcPurhcaseITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                    // JSON文件
                    String jsonDataFilePurchase = HanaJacksonUtil.objectToJackson(hanaPurchaseSaleBill);
                    mtcPurhcaseITFC.setMTC_DataFile(jsonDataFilePurchase);
                    // JSON文件长度
                    mtcPurhcaseITFC.setMTC_DataFileLen(jsonDataFilePurchase.length());
                    mtcITFCList.add(mtcPurhcaseITFC);

                }
                // 生产猪
                if (!hanaPurchasePigProductDetailList.isEmpty()) {
                    // 采购
                    MTC_ITFC mtcPurhcaseITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcPurhcaseITFC.setMTC_Branch(hanaPurchasePigProduct.getMTC_BranchID());
                    // 业务日期
                    mtcPurhcaseITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchasePigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:生产猪采购 - A
                    mtcPurhcaseITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3020);
                    // 新农+单据编号
                    mtcPurhcaseITFC.setMTC_DocNum(hanaPurchasePigProduct.getMTC_BaseEntry());
                    // 优先级
                    mtcPurhcaseITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcPurhcaseITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcPurhcaseITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcPurhcaseITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcPurhcaseITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                    // JSON文件
                    String jsonDataFilePurchase = HanaJacksonUtil.objectToJackson(hanaPurchasePigProduct);
                    mtcPurhcaseITFC.setMTC_DataFile(jsonDataFilePurchase);
                    // JSON文件长度
                    mtcPurhcaseITFC.setMTC_DataFileLen(jsonDataFilePurchase.length());
                    mtcITFCList.add(mtcPurhcaseITFC);

                }

                if (!mtcITFCList.isEmpty()) {
                    hanaCommonService.insertsMTC_ITFC(mtcITFCList);
                }
            }
        } else {

            // 销售单入场

            BillModel saleModel = billMapper.searchById(paraPigMoveInModel.getRelateBillId());

            SqlCon pigSaleSqlCon = new SqlCon();
            pigSaleSqlCon.addCondition(saleModel.getRowId(), " AND BILL_ID = ?");
            PigSaleModel pigSaleModel = getModel(pigSaleMapper, pigSaleSqlCon);

            SqlCon pigSaleDetailSqlCon = new SqlCon();
            pigSaleDetailSqlCon.addCondition(saleModel.getRowId(), " AND BILL_ID = ?");
            List<PigSaleDetailModel> pigSaleDetailModelList = getList(pigSaleDetailMapper, pigSaleDetailSqlCon);

            // 供应商ID
            Long supplierId = pigSaleModel.getFarmId();
            Map<String, String> supplierMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(supplierId);
            String supplierMtcBranchID = Maps.getString(supplierMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String supplierMtcDeptID = Maps.getString(supplierMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            // 客户ID
            Long customerId = pigSaleModel.getCustomerId();
            Map<String, String> customerMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(customerId);
            String customerMtcBranchID = Maps.getString(customerMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String customerMtcDeptID = Maps.getString(customerMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            // 销售单的isToHana
            boolean isSupplierIdToHana = HanaUtil.isToHanaCheck(supplierId);

            // 采购单的isToHana
            boolean isCustomerIdToHana = HanaUtil.isToHanaCheck(customerId);

            String sapSaleType = HanaUtil.getSapSaleType(supplierId, customerId);

            if ((isSupplierIdToHana || isCustomerIdToHana) && PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
                // 获取hana销售类型

                Long goodNumber = 0L;
                Long scNumber = 0L;

                // 商品猪销售
                HanaSaleBill hanaSaleBill = new HanaSaleBill();
                // 分公司编码
                hanaSaleBill.setMTC_BranchID(supplierMtcBranchID);
                // 新农+单据编号(销售单编号+"-"+采购单的ROW_ID)
                hanaSaleBill.setMTC_BaseEntry(supplierMtcBranchID + "-" + String.valueOf(saleModel.getRowId()) + "-" + saleModel.getBusinessCode()
                        + "-" + billId);
                // 业务伙伴编号
                hanaSaleBill.setMTC_CardCode(customerMtcBranchID);
                // 出库日期
                hanaSaleBill.setMTC_DocDate(saleModel.getBillDate());
                // 表行
                List<HanaSaleBillDetail> hanaSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
                hanaSaleBill.setDetailList(hanaSaleBillDetailList);

                String cardCode = HanaUtil.getMTC_SaleCardCode(supplierId);
                // 商品猪采购
                HanaSaleBill hanaPurchaseSaleBill = new HanaSaleBill();
                // 分公司编码
                hanaPurchaseSaleBill.setMTC_BranchID(customerMtcBranchID);
                // 新农+单据编号(采购单编号+"-"+销售单的ROW_ID)
                hanaPurchaseSaleBill.setMTC_BaseEntry(customerMtcBranchID + "-" + String.valueOf(billId) + "-" + paraPigMoveInModel.getBusinessCode()
                        + "-" + saleModel.getRowId());
                // 业务伙伴编号
                hanaPurchaseSaleBill.setMTC_CardCode(getPIBranchID(cardCode));
                // 出库日期
                hanaPurchaseSaleBill.setMTC_DocDate(paraPigMoveInModel.getBillDate());
                // 表行
                List<HanaSaleBillDetail> hanaPurchaseSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
                hanaPurchaseSaleBill.setDetailList(hanaPurchaseSaleBillDetailList);

                // 生产猪销售
                HanaPigProduct hanaPigProduct = new HanaPigProduct();
                // 分公司编码
                hanaPigProduct.setMTC_BranchID(supplierMtcBranchID);
                // 新农+单据编号(销售单编号+"-"+采购单的ROW_ID)
                hanaPigProduct.setMTC_BaseEntry(supplierMtcBranchID + "-" + String.valueOf(saleModel.getRowId()) + "-" + saleModel.getBusinessCode()
                        + "-" + billId);
                // 业务伙伴编号
                hanaPigProduct.setMTC_CardCode(customerMtcBranchID);
                // 出库日期
                hanaPigProduct.setMTC_DocDate(saleModel.getBillDate());
                // 表行
                List<HanaPigProductDetail> hanaPigProductDetailList = new ArrayList<HanaPigProductDetail>();
                hanaPigProduct.setDetailList(hanaPigProductDetailList);

                // 生产猪采购
                HanaPigProduct hanaPurchasePigProduct = new HanaPigProduct();
                // 分公司编码
                hanaPurchasePigProduct.setMTC_BranchID(customerMtcBranchID);
                // 新农+单据编号(采购单编号+"-"+销售单的ROW_ID)
                hanaPurchasePigProduct.setMTC_BaseEntry(customerMtcBranchID + "-" + String.valueOf(billId) + "-" + paraPigMoveInModel
                        .getBusinessCode() + "-" + saleModel.getRowId());
                // 业务伙伴编号
                hanaPurchasePigProduct.setMTC_CardCode(getPIBranchID(cardCode));
                // 出库日期
                hanaPurchasePigProduct.setMTC_DocDate(paraPigMoveInModel.getBillDate());
                // 表行
                List<HanaPigProductDetail> hanaPurchasePigProductDetailList = new ArrayList<HanaPigProductDetail>();
                hanaPurchasePigProduct.setDetailList(hanaPurchasePigProductDetailList);

                // 所有的生产猪
                List<Map<String, Object>> allPigModelList = new ArrayList<Map<String, Object>>();

                // 区分出商品猪和生产猪
                for (PigSaleDetailModel pigSaleDetailModel : pigSaleDetailModelList) {
                    if (!PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(pigSaleDetailModel.getSaleDescribe()) && !String.valueOf(
                            PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG).equals(pigSaleDetailModel.getSaleDescribe())) {
                        if (isSupplierIdToHana) {
                            // 商品猪销售
                            HanaSaleBillDetail hanaSaleBillDetail = this.createHanaSaleBillDetail(pigSaleDetailModel, supplierMtcBranchID,
                                    supplierMtcDeptID, saleModel.getRowId(), HanaConstants.MTC_SALES_TYPE_SI, pigSaleModel.getSalesman(), supplierId);
                            hanaSaleBillDetailList.add(hanaSaleBillDetail);
                        }
                        if (isCustomerIdToHana) {
                            // 商品猪采购
                            List<HanaSaleBillDetail> hanaPurchaseDetailList = this.createHanaPurchaseBillDetail(pigSaleDetailModel,
                                    customerMtcBranchID, customerMtcDeptID, billId, HanaConstants.MTC_SALES_TYPE_PI, pigSaleModel.getSalesman(),
                                    supplierId);
                            hanaPurchaseSaleBillDetailList.addAll(hanaPurchaseDetailList);
                        }
                        // 商品猪头数
                        goodNumber = goodNumber + pigSaleDetailModel.getSaleNum();
                    } else {
                        // 生产猪
                        SqlCon pigModelSqlCon = new SqlCon();
                        pigModelSqlCon.addMainSql("SELECT T1.ROW_ID AS pigId, T1.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand");
                        pigModelSqlCon.addMainSql(",T1.SEX AS sex, T1.BREED_ID AS breedId, T1.HOUSE_ID AS houseId");
                        pigModelSqlCon.addMainSql(",T2.LEAVE_WEIGHT AS leaveWeight, T2.LEAVE_PRICE AS leavePrice");
                        pigModelSqlCon.addMainSql(",T2.LINE_NUMBER AS lineNumber,T1.TO_PRODUCT_DAYAGE as toproductDayage");
                        pigModelSqlCon.addMainSql(" FROM PP_L_PIG T1");
                        pigModelSqlCon.addMainSql(" INNER JOIN");
                        pigModelSqlCon.addMainSql(" PP_L_BILL_LEAVE T2");
                        pigModelSqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID");
                        pigModelSqlCon.addCondition(supplierId, " AND T2.FARM_ID = ?)");
                        pigModelSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
                        pigModelSqlCon.addCondition(pigSaleDetailModel.getRowId(), " AND T2.SALE_ID = ?");

                        Map<String, String> pigModelSqlMap = new HashMap<String, String>();
                        pigModelSqlMap.put("sql", pigModelSqlCon.getCondition());

                        List<Map<String, Object>> pigModelList = paramMapper.getObjectInfos(pigModelSqlMap);

                        allPigModelList.addAll(pigModelList);
                        // 生产猪头数
                        scNumber = scNumber + pigSaleDetailModel.getSaleNum();
                    }
                }

                // 总头数
                Double totalNumber = (double) (goodNumber + scNumber);
                // 总费用
                Double totalOtherFee = Maps.getDouble(pigSaleModel.getData(), "otherFee", 0D);
                // 平均费用（保留两位小数）
                Double avgFee = BigDecimalUtil.bigDecimalDivide(totalOtherFee, totalNumber, 2);
                // 生产猪费用 = 平均费用*生产猪头数
                Double scFee = BigDecimalUtil.bigDecimalMultiply(avgFee, (double) scNumber);
                // 商品猪费用 = 总费用 - 生产猪费用
                Double goodFee = BigDecimalUtil.bigDecimalSubtract(totalOtherFee, scFee);

                String paymentDiff = Maps.getString(pigSaleModel.getData(), "paymentDiff");

                hanaSaleBill.setMTC_Fee(goodFee.toString());
                hanaPurchaseSaleBill.setMTC_Fee(goodFee.toString());
                hanaPigProduct.setMTC_Fee(scFee.toString());
                hanaPurchasePigProduct.setMTC_Fee(scFee.toString());

                // 如果有商品猪，把货款差异放入商品猪重
                if (!hanaSaleBillDetailList.isEmpty()) {
                    // 商品猪
                    hanaSaleBill.setMTC_AmtDiff(paymentDiff);
                    hanaPurchaseSaleBill.setMTC_AmtDiff(paymentDiff);
                    // 生产猪
                    hanaPigProduct.setMTC_AmtDiff("0");
                    hanaPurchasePigProduct.setMTC_AmtDiff("0");
                } else {
                    // 只有生产猪
                    hanaPigProduct.setMTC_AmtDiff(paymentDiff);
                    hanaPurchasePigProduct.setMTC_AmtDiff(paymentDiff);
                }

                List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

                // 如果有生产猪
                if (!allPigModelList.isEmpty()) {
                    for (Map<String, Object> pigMap : allPigModelList) {
                        // 生产猪销售
                        if (isSupplierIdToHana) {
                            HanaPigProductDetail hanaPigProductDetail = new HanaPigProductDetail();
                            hanaPigProductDetail.setMTC_BranchID(supplierMtcBranchID);
                            hanaPigProductDetail.setMTC_DeptID(supplierMtcDeptID);
                            hanaPigProductDetail.setMTC_BaseEntry(String.valueOf(saleModel.getRowId()));
                            hanaPigProductDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
                            hanaPigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PS);
                            if (PigConstants.PIG_SEX_MALE.equals(Maps.getString(pigMap, "sex"))) {
                                hanaPigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR);
                            } else {
                                hanaPigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW);
                            }
                            hanaPigProductDetail.setMTC_BatchNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                            hanaPigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                            hanaPigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                            hanaPigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                            hanaPigProductDetail.setMTC_Qty("1");
                            hanaPigProductDetail.setMTC_Price(Maps.getString(pigMap, "leavePrice"));
                            hanaPigProductDetail.setMTC_Total(Maps.getString(pigMap, "leavePrice"));
                            hanaPigProductDetailList.add(hanaPigProductDetail);
                        }
                        // 生产猪采购
                        if (isCustomerIdToHana) {
                            // 入场的猪只
                            SqlCon inputPigSqlCon = new SqlCon();
                            inputPigSqlCon.addMainSql("SELECT p.SAP_FIXED_ASSETS_EARBRAND sapFixedAssetsEarbrand,p.HOUSE_ID houseId,");
                            inputPigSqlCon.addMainSql(" c.EAR_BRAND earBrand FROM pp_l_pig p");
                            inputPigSqlCon.addMainSql(" INNER JOIN pp_l_ear_code c");
                            inputPigSqlCon.addMainSql(" ON p.EAR_CODE_ID = c.ROW_ID and c.DELETED_FLAG = 0");
                            inputPigSqlCon.addMainSql(" WHERE p.DELETED_FLAG = '0'");
                            inputPigSqlCon.addCondition(customerId, " AND p.FARM_ID = ?");
                            inputPigSqlCon.addCondition(Maps.getLong(pigMap, "pigId"), " AND p.RELATION_PIG_ID = ?");

                            Map<String, String> inputPigSqlMap = new HashMap<String, String>();
                            inputPigSqlMap.put("sql", inputPigSqlCon.getCondition());

                            Map<String, String> pigModel = paramMapper.getInfos(inputPigSqlMap).get(0);

                            // 生产猪采购
                            HanaPigProductDetail hanaPurchasePigProductDetail = new HanaPigProductDetail();
                            hanaPurchasePigProductDetail.setMTC_BranchID(customerMtcBranchID);
                            hanaPurchasePigProductDetail.setMTC_DeptID(customerMtcDeptID);
                            hanaPurchasePigProductDetail.setMTC_BaseEntry(String.valueOf(billId));
                            hanaPurchasePigProductDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
                            hanaPurchasePigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PP);
                            if (PigConstants.PIG_SEX_MALE.equals(Maps.getString(pigMap, "sex"))) {
                                hanaPurchasePigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR);
                            } else {
                                hanaPurchasePigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW);
                            }
                            hanaPurchasePigProductDetail.setMTC_BatchNum(Maps.getString(pigModel, "sapFixedAssetsEarbrand"));
                            hanaPurchasePigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigModel, "houseId")));
                            hanaPurchasePigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                            hanaPurchasePigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                            hanaPurchasePigProductDetail.setMTC_Qty("1");
                            hanaPurchasePigProductDetail.setMTC_Price(Maps.getString(pigMap, "leavePrice"));
                            hanaPurchasePigProductDetail.setMTC_Total(Maps.getString(pigMap, "leavePrice"));
                            hanaPurchasePigProductDetailList.add(hanaPurchasePigProductDetail);

                            // 生产猪卡片
                            MTC_OITM mtcOITM = new MTC_OITM();

                            mtcOITM.setMTC_ItemCode(Maps.getString(pigModel, "sapFixedAssetsEarbrand"));
                            mtcOITM.setMTC_ItemName(CacheUtil.getName(Maps.getString(pigMap, "breedId"), NameEnum.BREED_NAME) + "." + Maps.getString(
                                    pigModel, "earBrand"));
                            mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(Maps.getString(pigMap, "sex")));
                            mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(customerMtcBranchID, Maps.getString(pigMap, "sex")));
                            mtcOITM.setMTC_DeptID(customerMtcDeptID);
                            mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                            mtcOITM.setMTC_DaysOld(Maps.getString(pigMap, "toproductDayage"));

                            // 卡片
                            MTC_ITFC mtcCardITFC = new MTC_ITFC();
                            // 分公司编号
                            mtcCardITFC.setMTC_Branch(hanaPurchaseSaleBill.getMTC_BranchID());
                            // 业务日期
                            mtcCardITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                            // 业务代码:生产猪采购 - B
                            mtcCardITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3021);
                            // 新农+单据编号
                            mtcCardITFC.setMTC_DocNum(Maps.getString(pigModel, "sapFixedAssetsEarbrand"));
                            // 优先级
                            mtcCardITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                            // 处理区分
                            mtcCardITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                            // 创建日期
                            mtcCardITFC.setMTC_CreateTime(currentDate);
                            // 同步状态
                            mtcCardITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                            // DB
                            mtcCardITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(customerId));
                            // JSON文件
                            String jsonDataFileProduct = HanaJacksonUtil.objectToJackson(mtcOITM);
                            mtcCardITFC.setMTC_DataFile(jsonDataFileProduct);
                            // JSON文件长度
                            mtcCardITFC.setMTC_DataFileLen(jsonDataFileProduct.length());
                            mtcITFCList.add(mtcCardITFC);
                        }
                    }
                }

                // 商品猪采购单
                if (!hanaPurchaseSaleBillDetailList.isEmpty()) {
                    // 内销
                    // 商品猪采购
                    MTC_ITFC mtcPurhcaseITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcPurhcaseITFC.setMTC_Branch(hanaPurchaseSaleBill.getMTC_BranchID());
                    // 业务日期
                    mtcPurhcaseITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:猪只采购
                    mtcPurhcaseITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
                    // 新农+单据编号
                    mtcPurhcaseITFC.setMTC_DocNum(hanaPurchaseSaleBill.getMTC_BaseEntry());
                    // 优先级
                    mtcPurhcaseITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcPurhcaseITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcPurhcaseITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcPurhcaseITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcPurhcaseITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(customerId));
                    // JSON文件
                    String jsonDataFilePurchase = HanaJacksonUtil.objectToJackson(hanaPurchaseSaleBill);
                    mtcPurhcaseITFC.setMTC_DataFile(jsonDataFilePurchase);
                    // JSON文件长度
                    mtcPurhcaseITFC.setMTC_DataFileLen(jsonDataFilePurchase.length());
                    mtcITFCList.add(mtcPurhcaseITFC);
                }

                // 商品猪销售单
                if (PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
                    if (!hanaSaleBillDetailList.isEmpty()) {
                        // 商品猪销售
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(hanaSaleBill.getMTC_BranchID());
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:猪只销售
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2100);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaSaleBill.getMTC_BaseEntry());
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(supplierId));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);
                    }
                }

                // 生产猪采购单
                if (!hanaPurchasePigProductDetailList.isEmpty()) {
                    // 内销
                    // 生产猪采购
                    MTC_ITFC mtcPurhcaseITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcPurhcaseITFC.setMTC_Branch(hanaPurchasePigProduct.getMTC_BranchID());
                    // 业务日期
                    mtcPurhcaseITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchasePigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:生产猪采购 - A
                    mtcPurhcaseITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3020);
                    // 新农+单据编号
                    mtcPurhcaseITFC.setMTC_DocNum(hanaPurchasePigProduct.getMTC_BaseEntry());
                    // 优先级
                    mtcPurhcaseITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcPurhcaseITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcPurhcaseITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcPurhcaseITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcPurhcaseITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(customerId));
                    // JSON文件
                    String jsonDataFilePurchase = HanaJacksonUtil.objectToJackson(hanaPurchasePigProduct);
                    mtcPurhcaseITFC.setMTC_DataFile(jsonDataFilePurchase);
                    // JSON文件长度
                    mtcPurhcaseITFC.setMTC_DataFileLen(jsonDataFilePurchase.length());
                    mtcITFCList.add(mtcPurhcaseITFC);
                }

                // 生产猪销售单
                if (PigConstants.SAP_SALE_TYPE_INSIDE.equals(sapSaleType)) {
                    if (!hanaPigProductDetailList.isEmpty()) {
                        // 生产猪销售
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(hanaPigProduct.getMTC_BranchID());
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:生产猪淘汰销售
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaPigProduct.getMTC_BaseEntry());
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(supplierId));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigProduct);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);
                    }
                }

                if (!mtcITFCList.isEmpty()) {
                    hanaCommonService.insertsMTC_ITFC(mtcITFCList);
                }
            }
        }
        // END HANA
    }

    private boolean isProductPig(Long pigClass) {
        if (pigClass == PigConstants.PIG_CLASS_SCGZ || pigClass == PigConstants.PIG_CLASS_FQ1 || pigClass == PigConstants.PIG_CLASS_LC
                || pigClass == PigConstants.PIG_CLASS_KH || pigClass == PigConstants.PIG_CLASS_RS || pigClass == PigConstants.PIG_CLASS_SOW_DN) {
            return true;
        }
        return false;
    }

    // 内销：商品猪 销售单和采购单
    private void saleAndSI(Long relateBillId, Long farmId, Long supplierId) throws Exception {
        // 销售主表
        SqlCon pigSaleSql = new SqlCon();
        pigSaleSql.addCondition(relateBillId, " AND BILL_ID = ?");
        PigSaleModel pigSaleModel = getModel(pigSaleMapper, pigSaleSql);

        Map<String, Object> pigSaleMap = pigSaleModel.getData();

        // 销售细表
        SqlCon saleDetailSql = new SqlCon();
        saleDetailSql.addCondition(relateBillId, " AND BILL_ID = ?");
        List<PigSaleDetailModel> saleDetailList = getList(pigSaleDetailMapper, saleDetailSql);

        // 主表
        HanaSaleBill hanaSaleBill = new HanaSaleBill();
        // 分公司编码
        // hanaSaleBill.setMTC_BranchID(mtcBranchID);
        // // 新农+单据编号
        // hanaSaleBill.setMTC_BaseEntry(mtcBranchID + "-" + saleAccountModel.getSaleBillId() + "-" + Maps.getString(map, "billCode"));
        // // 业务伙伴编号
        //
        // hanaSaleBill.setMTC_CardCode(cardCode);
        // // 出库日期
        // hanaSaleBill.setMTC_DocDate(pigSaleModel.getCreateDate());
        // // 销售费用
        // hanaSaleBill.setMTC_Fee(Maps.getString(pigSaleMap, "otherFee"));
        // // 货款差异
        // hanaSaleBill.setMTC_AmtDiff(Maps.getString(pigSaleMap, "paymentDiff"));
        // // 表行
        // List<HanaSaleBillDetail> hanaSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
        // //
        // hanaSaleBill.setDetailList(hanaSaleBillDetailList);
        //
        // //
        // //
        // // 细表
        // HanaSaleBillDetail hanaSaleBillDetail = null;
        // for (PigSaleDetailModel model : hanaList) {
        // Map<String, Object> pigSaleDetailModelMap = model.getData();
        // hanaSaleBillDetail = new HanaSaleBillDetail();
        // // 分公司编码
        // hanaSaleBillDetail.setMTC_BranchID(mtcBranchID);
        // // 猪场编码
        // hanaSaleBillDetail.setMTC_DeptID(mtcDeptID);
        // // 新农+单据编号
        // hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(saleAccountModel.getSaleBillId()));
        // // 新农+单据行号
        // hanaSaleBillDetail.setMTC_BaseLine(String.valueOf(pigSaleDetailModel.getLineNumber()));
        // // 销售类型
        // hanaSaleBillDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_SO);
        // // 品名
        // hanaSaleBillDetail.setMTC_ItemCode(HanaUtil.getMTC_ItemCodeOfSale(pigSaleDetailModel.getSaleDescribe(), getFarmId(), pigSaleDetailModel
        // .getHouseId()));
        // // 猪舍编号
        // hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(pigSaleDetailModel.getHouseId()));
        // // 猪只等级
        // hanaSaleBillDetail.setMTC_Grade(HanaUtil.getMTC_Grade(pigSaleDetailModel.getSaleDescribe()));
        // // 猪只品种
        // hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigSaleDetailModel.getBreedId()));
        // // 猪只性别
        // hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(String.valueOf(pigSaleDetailModel.getSex())));
        // 销售数量(头数)
        // hanaSaleBillDetail.setMTC_Qty(Maps.getString(pigSaleDetailModelMap, "saleNum"));
        // // 销售重量
        // hanaSaleBillDetail.setMTC_Weight(Maps.getString(pigSaleDetailModelMap, "totalWeight"));
        // // 数量单价
        // hanaSaleBillDetail.setMTC_QtyPrice(Maps.getString(pigSaleDetailModelMap, "preUnitPrice"));
        // // 重量单价
        // hanaSaleBillDetail.setMTC_WeiPrice(Maps.getString(pigSaleDetailModelMap, "totalUnitPrice"));
        // // 底重
        // hanaSaleBillDetail.setMTC_BotWeight(Maps.getString(pigSaleDetailModelMap, "baseWeight"));
        // // 底重单价
        // hanaSaleBillDetail.setMTC_BotPrice(Maps.getString(pigSaleDetailModelMap, "baseUnitPrice"));
        // // 超重
        // hanaSaleBillDetail.setMTC_OverWeight(Maps.getString(pigSaleDetailModelMap, "overWeight"));
        // // 超重单价
        // hanaSaleBillDetail.setMTC_OverPrice(Maps.getString(pigSaleDetailModelMap, "overUnitPrice"));
        // // 总金额
        // hanaSaleBillDetail.setMTC_Amount(Maps.getString(pigSaleDetailModelMap, "totalPrice"));
        // // 业务员
        // hanaSaleBillDetail.setMTC_Sales(Maps.getString(pigSaleMap, "salesman") != null ? pigSaleModel.getSalesman() + "." + CacheUtil.getName(
        // String.valueOf(pigSaleModel.getSalesman()), NameEnum.EMPLOYEE_NAME) : null);
        //
        // hanaSaleBillDetailList.add(hanaSaleBillDetail);
        // }
    }

    // 内销：生产猪 淘汰销售和生产采购以及卡片
    private void saleAndPS(Long billId) {

    }

    @Override
    public void editChangeEarBand(BillModel billModel, String eventName, String changeEarBandList) throws Exception {

        // String eventName = "CHANGE_EAR_BAND";
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
        List<ChangeEarBandView> changeEarBandLists = null;
        if (changeEarBandList != null) {
            changeEarBandLists = getJsonList(changeEarBandList, ChangeEarBandView.class);
            if (changeEarBandLists.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<ChangeEarBandView> it = changeEarBandLists.iterator(); it.hasNext();) {
                ChangeEarBandView changeEarBandView = (ChangeEarBandView) it.next();
                if (changeEarBandView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if ("".equals(changeEarBandView.getNewEarBrand()) || changeEarBandView.getNewEarBrand() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新耳牌号不能为空！");
                }
                Date enterDate = changeEarBandView.getEnterDate();
                Date minDate = changeEarBandView.getMinValidDate();
                Date maxDate = changeEarBandView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, changeEarBandView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                changeEarBandView.setBillId(billId);
                changeEarBandView.setFarmId(getFarmId());
                changeEarBandView.setCompanyId(getCompanyId());
                changeEarBandView.setCreateId(getEmployeeId());
                changeEarBandView.setEventName(eventName);
                pigEventMapper.changeEarBand(changeEarBandView);
                if (!"0".equals(changeEarBandView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeEarBandView.getErrorMessage());
                }

                this.insertPigEventRelates(changeEarBandView.getPigId());

            }
        }
        // 刷新缓存
        CacheUtil.BasicTableCaheRefresh(getFarmId());
    }

    // 背膘测定
    @Override
    public void editBackfat(BillModel billModel, String eventName, String backfatList) throws Exception {

        // String eventName = "BACKFAT";
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
        List<BackfatView> backfatViews = null;
        if (backfatList != null) {
            backfatViews = getJsonList(backfatList, BackfatView.class);
            if (backfatViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<BackfatView> it = backfatViews.iterator(); it.hasNext();) {
                BackfatView backfatView = (BackfatView) it.next();
                if (backfatView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (backfatView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + backfatView.getLineNumber() + "行未填写测定日期不能提交！");
                }
                Date enterDate = backfatView.getEnterDate();
                Date minDate = backfatView.getMinValidDate();
                Date maxDate = backfatView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, backfatView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }
                if (backfatView.getBackfat() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + backfatView.getLineNumber() + "行未填写背膘（mm）不能提交！");
                }
                if (backfatView.getScore() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + backfatView.getLineNumber() + "行未填写评分不能提交！");
                }
                if (backfatView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + backfatView.getLineNumber() + "行未填写技术员不能提交！");
                }

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(backfatView.getPigId(), enterDate, billId);

                backfatView.setBillId(billId);
                backfatView.setFarmId(getFarmId());
                backfatView.setCompanyId(getCompanyId());
                backfatView.setCreateId(getEmployeeId());
                backfatView.setEventName(eventName);
                backfatView.setBackfatStage(PigConstants.BACKFAT_STAGE_BACKFAT);
                pigEventMapper.backfat(backfatView);
                if (!"0".equals(backfatView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, backfatView.getErrorMessage());
                }

                this.insertPigEventRelates(backfatView.getPigId());

            }
        }
    }

    // 上产房
    @Override
    public void editChangeLaborHouse(BillModel billModel, String eventName, String changeHouseList, String zcfxycbbFlag, String requiredPigpen)
            throws Exception {

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
        List<ChangeHouseView> changeHouseViews = null;
        if (changeHouseList != null) {
            changeHouseViews = getJsonList(changeHouseList, ChangeHouseView.class);
            if (changeHouseViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<ChangeHouseView> it = changeHouseViews.iterator(); it.hasNext();) {
                ChangeHouseView changeHouseView = (ChangeHouseView) it.next();
                if (changeHouseView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (changeHouseView.getChangeHouseType() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + changeHouseView.getLineNumber() + "行，转舍类型不能为空！");
                }

                Date enterDate = changeHouseView.getEnterDate();
                Date minDate = changeHouseView.getMinValidDate();
                Date maxDate = changeHouseView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, changeHouseView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), enterDate);
                String changeType = changeHouseView.getChangeHouseType();

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(changeHouseView.getPigId(), enterDate, billId);
                // 种猪转舍
                if (PigConstants.CHANGE_HOUSE_TYPE_SELF.equals(changeType)) {
                    if (changeHouseView.getInHouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + changeHouseView.getLineNumber() + "行，转入舍不能为空");
                    }
                } else {
                    // 上产房不能产房转产房
                    if (PigConstants.CHANGE_HOUSE_TYPE_DEL.equals(changeType)) {
                        long pigId = changeHouseView.getPigId();
                        PigModel pigModel = pigMapper.searchById(pigId);
                        HouseModel houseModel = houseMapper.searchById(pigModel.getHouseId());
                        if (PigConstants.HOUSE_CLASS_DELIVERY == houseModel.getHouseType().longValue()) {
                            Thrower.throwException(ProductionException.TO_DELI_DUPLICATE_ERROR, changeHouseView.getLineNumber(), changeHouseView
                                    .getEarBrand());
                        }
                    }
                    if (changeHouseView.getInHouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + changeHouseView.getLineNumber() + "行，转入产房不能为空");
                    }
                    if (changeHouseView.getInPigpenId() == null) {
                        if (requiredPigpen == null) {
                            requiredPigpen = "true";
                        }
                        if (requiredPigpen.equals("true")) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + changeHouseView.getLineNumber() + "行，转入产房栏位不能为空");
                        }
                    }
                }

                // 产房内转
                if (PigConstants.CHANGE_HOUSE_TYPE_ADV.equals(changeType)) {
                    PigModel pigModel = pigMapper.searchById(changeHouseView.getPigId());
                    // 哺乳
                    if (PigConstants.PIG_CLASS_BR == pigModel.getPigClass()) {
                        SqlCon deliverySqlCon = new SqlCon();
                        deliverySqlCon.addCondition(pigModel.getRowId(), " AND PIG_ID = ?");
                        deliverySqlCon.addCondition(pigModel.getParity(), " AND PARITY = ?");

                        List<DeliveryModel> deliveryModelList = getList(deliveryMapper, deliverySqlCon);
                        // 更改数据语句已经移入存储过程中，这里只做一下合格数据的CHECK（一个胎次只有一条分娩记录）
                        if (deliveryModelList.size() != 1) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + changeHouseView.getLineNumber() + "行，猪只【" + changeHouseView
                                    .getEarBrand() + "】不存在或存在多条分娩记录，请联系管理员。。。");
                        } else {
                            DeliveryModel deliveryModel = deliveryModelList.get(0);
                            // 批量插入整个单据的猪仔信息
                            SqlCon insertChildPigEventRelatesSqlCon = new SqlCon();
                            insertChildPigEventRelatesSqlCon.addMainSql("SELECT '1' AS status, 0 AS deletedFlag, FARM_ID AS farmId");
                            insertChildPigEventRelatesSqlCon.addMainSql(", COMPANY_ID AS companyId, SWINERY_ID AS swineryId");
                            insertChildPigEventRelatesSqlCon.addMainSql(", ROW_ID AS pigId, LINE_ID AS lineId, HOUSE_ID AS houseId");
                            insertChildPigEventRelatesSqlCon.addMainSql(", PIGPEN_ID AS pigpenId");
                            insertChildPigEventRelatesSqlCon.addMainSql(",(SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE STATUS = '1' ");
                            insertChildPigEventRelatesSqlCon.addMainSql(" AND DELETED_FLAG = '0' AND ROW_ID = EAR_CODE_ID) AS earBrand");
                            insertChildPigEventRelatesSqlCon.addMainSql(", PIG_TYPE AS pigType, SEX AS sex, PIG_CLASS AS pigClass");
                            insertChildPigEventRelatesSqlCon.addMainSql(", PARITY AS parity, BILL_ID AS billId, LAST_EVENT_DATE AS eventDate");
                            insertChildPigEventRelatesSqlCon.addMainSql(" FROM PP_L_PIG");
                            insertChildPigEventRelatesSqlCon.addMainSql(" WHERE STATUS = '1' AND DELETED_FLAG = '0'");
                            insertChildPigEventRelatesSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
                            insertChildPigEventRelatesSqlCon.addCondition(deliveryModel.getBillId(), " AND BILL_ID = ?");
                            insertChildPigEventRelatesSqlCon.addCondition(deliveryModel.getPigId(), " AND BOARD_SOW_ID = ?");
                            insertChildPigEventRelatesSqlCon.addCondition(deliveryModel.getParity(), " AND BIRTH_PARITY = ?");
                            // 2017-08-29改，产房内转需要转移死亡猪只，所以分娩的死亡猪只也需要插入Relates表
                            // 不保存死亡的猪只记录（减少记录的数据量）
                            // 修复以前没有插入分娩死亡猪只Relates的操作
                            insertChildPigEventRelatesSqlCon.addCondition(PigConstants.PIG_CLASS_SW, " AND PIG_CLASS = ?");
                            insertChildPigEventRelatesSqlCon.addMainSql(" AND NOT EXISTS(");
                            insertChildPigEventRelatesSqlCon.addMainSql(" SELECT 1 FROM PP_L_PIG_EVENT_RELATES");
                            insertChildPigEventRelatesSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                            insertChildPigEventRelatesSqlCon.addMainSql(" AND PIG_ID = PP_L_PIG.ROW_ID");
                            insertChildPigEventRelatesSqlCon.addMainSql(" AND BILL_ID = PP_L_PIG.BILL_ID)");

                            List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, insertChildPigEventRelatesSqlCon);

                            if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
                                pigEventRelatesMapper.inserts(pigEventRelatesModelList);
                            }
                        }
                    }
                }

                changeHouseView.setBillId(billId);
                changeHouseView.setFarmId(getFarmId());
                changeHouseView.setCompanyId(getCompanyId());
                changeHouseView.setEventName(eventName);
                changeHouseView.setCreateId(getEmployeeId());
                pigEventMapper.changeHouse(changeHouseView);
                if (!"0".equals(changeHouseView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                }

                // 当转产房需要测背膘设置开启，必须填入背膘
                if ("on".equalsIgnoreCase(zcfxycbbFlag) && PigConstants.CHANGE_HOUSE_TYPE_DEL.equals(changeType)) {
                    if (changeHouseView.getBackfat() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + changeHouseView.getLineNumber() + "行，背膘不能为空");
                    }
                }

                if (changeHouseView.getBackfat() != null) {
                    // 背膘
                    BackfatView backfatView = new BackfatView();
                    backfatView.setLineNumber(changeHouseView.getLineNumber());
                    backfatView.setBillId(billId);
                    backfatView.setFarmId(getFarmId());
                    backfatView.setCompanyId(getCompanyId());
                    backfatView.setEventName(eventName);
                    backfatView.setCreateId(getEmployeeId());
                    backfatView.setPigId(changeHouseView.getPigId());

                    backfatView.setEnterDate(changeHouseView.getEnterDate());
                    backfatView.setBackfat(changeHouseView.getBackfat());
                    backfatView.setScore(changeHouseView.getScore());
                    backfatView.setWorker(changeHouseView.getWorker());
                    backfatView.setBackfatStage(PigConstants.BACKFAT_STAGE_CHANGELABORHOUSE); // 上产房为1
                    pigEventMapper.backfat(backfatView);
                    if (!"0".equals(backfatView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, backfatView.getErrorMessage());
                    }
                }

                this.insertPigEventRelates(changeHouseView.getPigId());
            }

            // 批量插入整个单据的猪仔信息
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_CHANGE_HOUSE WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            getChildPigIdSqlCon.addMainSql(" AND PIG_TYPE = '3'");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }
        }
    }

    // 转群
    @Override
    public void editChangeSwinery(BillModel billModel, String eventName, String changeSwineryList) throws Exception {

        // String eventName = "CHANGE_SWINERY";
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
        List<ChangeSwineryView> changeSwineryViews = null;
        if (changeSwineryList != null) {
            changeSwineryViews = getJsonList(changeSwineryList, ChangeSwineryView.class);
            if (changeSwineryViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<ChangeSwineryView> it = changeSwineryViews.iterator(); it.hasNext();) {
                ChangeSwineryView changeSwineryView = (ChangeSwineryView) it.next();
                if (changeSwineryView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                Date enterDate = changeSwineryView.getEnterDate();
                Date minDate = changeSwineryView.getMinValidDate();
                Date maxDate = changeSwineryView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, changeSwineryView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                changeSwineryView.setBillId(billId);
                changeSwineryView.setFarmId(getFarmId());
                changeSwineryView.setCompanyId(getCompanyId());
                changeSwineryView.setEventName(eventName);
                pigEventMapper.changeSwinery(changeSwineryView);
                if (!"0".equals(changeSwineryView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeSwineryView.getErrorMessage());
                }

            }
        }
    }

    // 后备转生产公猪
    @Override
    public void editBoarReserveToProduct(BillModel billModel, String eventName, String boarReserveToProductList) throws Exception {

        // String eventName = "BOAR_RESERVE_TO_PRODUCT";
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
        List<ReserveToProductView> boarReserveToProductViews = null;
        if (boarReserveToProductList != null) {
            boarReserveToProductViews = getJsonList(boarReserveToProductList, ReserveToProductView.class);
            if (boarReserveToProductViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }

            // START HANA
            // SAP后备转生产（公猪）
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            Date currentDate = new Date();
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            List<MTC_ITFC> mtcOITM_MTC_ITFCList = new ArrayList<MTC_ITFC>();
            String mtcBranchID = null;
            String mtcDeptID = null;
            if (isToHana) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            }
            // END HANA

            for (Iterator<ReserveToProductView> it = boarReserveToProductViews.iterator(); it.hasNext();) {
                ReserveToProductView boarReserveToProductView = (ReserveToProductView) it.next();
                if (boarReserveToProductView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                /* if (StringUtils.isBlank(boarReserveToProductView.getNewEarBrand())) {
                 * Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新耳牌号不能为空！");
                 * }
                 * if (boarReserveToProductView.getNewHouseId() == null) {
                 * Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪舍名称不能为空！");
                 * } */
                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(boarReserveToProductView.getPigId(), boarReserveToProductView.getEnterDate(), billId);

                boarReserveToProductView.setBillId(billId);
                boarReserveToProductView.setFarmId(getFarmId());
                boarReserveToProductView.setCompanyId(getCompanyId());
                boarReserveToProductView.setCreateId(getEmployeeId());
                boarReserveToProductView.setEventName(eventName);
                Date enterDate = boarReserveToProductView.getEnterDate();
                Date minDate = boarReserveToProductView.getMinValidDate();
                Date maxDate = boarReserveToProductView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, boarReserveToProductView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }
                sapEventMasage(getFarmId(), enterDate);
                pigEventMapper.reserveToProduct(boarReserveToProductView);
                if (!"0".equals(boarReserveToProductView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, boarReserveToProductView.getErrorMessage());
                }

                // START HANA
                if (isToHana) {
                    // 后备转生产 - A 应收发票（公猪）
                    PigModel pigModel = pigMapper.searchById(boarReserveToProductView.getPigId());
                    MTC_ITFC mtcITFC = sendToSapChangeProductFor3010(mtcBranchID, mtcDeptID, currentDate, enterDate, pigModel, billModel,
                            boarReserveToProductView.getLineNumber());
                    mtcITFCList.add(mtcITFC);

                    // 后备转生产 - B 固定资产卡片
                    MTC_ITFC mtcOITM_MTC_ITFC = sendToSapMTC_OITM(mtcBranchID, mtcDeptID, currentDate, pigModel, billModel);
                    mtcOITM_MTC_ITFCList.add(mtcOITM_MTC_ITFC);
                }
                // END HANA

                this.insertPigEventRelates(boarReserveToProductView.getPigId());

            }

            // START HANA
            if (!mtcITFCList.isEmpty()) {
                mtcITFCList.addAll(mtcOITM_MTC_ITFCList);
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
            // END HANA
        }
    }

    // 采精
    @Override
    public void editSemen(SemenBillModel semenBillModel, String eventName, String semenList) throws Exception {
        // String eventName = "SEMEN";
        // 单据日期不能为空
        if (semenBillModel.getBillDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }
        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
        semenBillModel.setCompanyId(getCompanyId());
        semenBillModel.setFarmId(getFarmId());
        semenBillModel.setCreateId(getEmployeeId());
        semenBillModel.setBusinessCode(businessCode);
        semenBillModel.setEventCode(eventName);
        // 创建表单
        billMapper.insert(semenBillModel);
        long billId = semenBillModel.getRowId();
        Date createDate = new Date();
        List<SemenView> semenViews = null;
        if (semenList != null) {
            semenViews = getJsonList(semenList, SemenView.class);
            List<Map<String, Object>> semenMaps = getMapList(semenList);
            if (semenViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<SemenView> it = semenViews.iterator(); it.hasNext();) {
                SemenView semenView = (SemenView) it.next();
                if (EventConstants.SEMEN.equals(eventName)) {
                    if (semenView.getEnterDate() == null) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                    }
                    if (semenView.getAnalust() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "化验员不能为空！");
                    }
                    if (semenView.getSemenQty() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精液量不能为空！");
                    }
                    if (semenView.getWorker() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                    }

                    // 启用人工授精使用库存精液设置后,仓库不能为空
                    String pzqcjSetting = ParamUtil.getSettingValueByCode("PZQCJ");
                    if ("ON".equals(pzqcjSetting)) {
                        if (semenView.getEarBrand() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "耳牌号不能为空！");
                        }
                        if (semenView.getEnterDate() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "采精日期不能为空！");
                        }
                        if (semenView.getColor() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精液颜色不能为空！");
                        }
                        if (semenView.getOdour() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, " 精液气味不能为空！");
                        }
                        if (semenView.getCohesion() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "凝聚度不能为空！");
                        }
                        if (semenView.getAnhNum() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "稀释份数不能为空！");
                        }
                        if (semenView.getSpermMotility() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精子活力不能为空！");
                        }
                        if (semenView.getSpermDensity() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精子密度不能为空！");
                        }
                        if (semenView.getAbnormationRate() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "畸形率不能为空！");
                        }
                        if (semenView.getSpec() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "规格不能为空！");
                        }
                        if (semenView.getWarehouseId() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "仓库不能为空/若无精液仓库请新建！");
                        }
                    }

                    Date enterDate = semenView.getEnterDate();
                    Date minDate = semenView.getMinValidDate();
                    Date maxDate = semenView.getMaxValidDate();
                    if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, semenView.getLineNumber(), TimeUtil.format(enterDate,
                                TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate,
                                        TimeUtil.DATE_FORMAT));
                    }
                    if (semenView.getAnhNum() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "稀释分数不能小于1");
                    }

                    // 若存在审批中淘汰单，则自动退回
                    editObsoleteDefeatAuto(semenView.getPigId(), semenView.getEnterDate(), billId);

                    semenView.setBillId(billId);
                    semenView.setFarmId(getFarmId());
                    semenView.setCompanyId(getCompanyId());
                    semenView.setCreateId(getEmployeeId());
                    semenView.setSemenType("2");
                    semenView.setEventName(eventName);
                    // 获取精液有效天数
                    PigModel pigModel = pigMapper.searchById(semenView.getPigId());
                    EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql("SELECT SHELF_LIFE FROM CD_O_SPERM WHERE DELETED_FLAG = '0' AND STATUS = '1'");
                    sqlCon.addCondition(pigModel.getMaterialId(), " AND BOAR_ID  = ?");
                    List<SpermModel> spermModelList = setSql(spermMapper, sqlCon);
                    if (spermModelList.isEmpty()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + semenView.getLineNumber() + "行，耳牌号：【" + earCodeModel
                                .getEarBrand() + "】猪只，主数据未建立对应的精液主数据，不能采精！");
                    } else if (spermModelList.size() > 1) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + semenView.getLineNumber() + "行，耳牌号：【" + earCodeModel
                                .getEarBrand() + "】猪只，主数据建立了多条对应的精液主数据，请联系管理员！");
                    }
                    SpermModel spermModel = spermModelList.get(0);
                    semenView.setShelfLife(spermModel.getShelfLife());
                    semenView.setValidDate(TimeUtil.dateAddDay(enterDate, spermModel.getShelfLife().intValue()));
                    /****************** 2017-7-24 zj 修改采精日期 start ********************************/
                    semenView.setSemenDate(enterDate);
                    /****************** 2017-7-24 zj 修改采精日期 end ********************************/
                    pigEventMapper.semen(semenView);
                    if (!"0".equals(semenView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, semenView.getErrorMessage());
                    }

                    this.insertPigEventRelates(semenView.getPigId());

                } else if (EventConstants.SEMEN_INQUIRY.equals(eventName)) {
                    if (semenView.getMaterialId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "物料主数据不能为空！");
                    }
                    if (semenView.getEarBrand() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "耳牌号不能为空！");
                    }

                    if (semenView.getDayAge() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "日龄不能为空！");
                    }
                    if (semenView.getSemenBatchNo() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精液批号不能为空！");
                    }
                    if (semenView.getSpermNum() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "数量不能为空！");
                    }
                    if (semenView.getWarehouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "仓库不能为空/若无精液仓库请新建！");
                    }
                    if (semenView.getValidDate() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "有效日期不能为空！");
                    }
                    if (semenView.getInputDate() == null) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                    }
                    Date enterDate = semenView.getInputDate();
                    if (TimeUtil.compareDate(enterDate, semenView.getValidDate(), Calendar.DATE) > 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入库时间不能大于有效日期！");
                    }
                    if (TimeUtil.compareDate(enterDate, createDate, Calendar.DATE) > 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入库时间不能大于今天！");
                    }
                    if (semenView.getWorker() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "负责人不能为空！");
                    }
                    // Date minDate = semenView.getMinValidDate();
                    // Date maxDate = semenView.getMaxValidDate();
                    // if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    // Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, semenView.getLineNumber(), TimeUtil.format(enterDate,
                    // TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate,
                    // TimeUtil.DATE_FORMAT));
                    // }

                    // 精液主数据
                    SqlCon spermSqlCon = new SqlCon();
                    spermSqlCon.addMainSql("SELECT BOAR_ID,SHELF_LIFE,MATERIAL_ID FROM CD_O_SPERM WHERE DELETED_FLAG = '0' AND STATUS = '1'");
                    spermSqlCon.addCondition(semenView.getMaterialId(), " AND MATERIAL_ID  = ?");
                    List<SpermModel> spermModelList = setSql(spermMapper, spermSqlCon);
                    if (spermModelList.isEmpty()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + semenView.getLineNumber() + "行，耳牌号：【" + semenView.getEarBrand()
                                + "】猪只，主数据未建立对应的精液主数据，不能采精！");
                    } else if (spermModelList.size() > 1) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + semenView.getLineNumber() + "行，耳牌号：【" + semenView.getEarBrand()
                                + "】猪只，主数据建立了多条对应的精液主数据，请联系管理员！");
                    }
                    SpermModel spermModel = spermModelList.get(0);
                    semenView.setShelfLife(spermModel.getShelfLife());
                    if (PigConstants.SEMEN_ORIGIN_WG.equals(semenBillModel.getOrigin())) {
                        // 创建虚拟猪舍以及公猪
                        // Long pigId = createXNBoarAndHouse(semenBillModel, semenView, eventName);
                        // semenView.setPigId(pigId);
                        // semenView.setEarBrand(earBrand);
                        // 公猪主数据
                        semenView.setBoarMaterialId(spermModel.getBoarId());
                        // 精液采精日期
                        semenView.setSemenDate(TimeUtil.dateAddDay(semenView.getValidDate(), -spermModel.getShelfLife().intValue()));

                        semenView.setOrigin(semenBillModel.getOrigin());
                        semenView.setBillId(billId);
                        semenView.setFarmId(getFarmId());
                        semenView.setCompanyId(getCompanyId());
                        semenView.setCreateId(getEmployeeId());
                        semenView.setSemenType("2");
                        semenView.setEventName(eventName);
                        /****************** 2017-7-24 zj 修改精液入库日期 start ********************************/
                        semenView.setEnterDate(enterDate);
                        /****************** 2017-7-24 zj 修改精液入库日期 end ********************************/
                        pigEventMapper.semen(semenView);
                        if (!"0".equals(semenView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, semenView.getErrorMessage());
                        }

                    } else if (PigConstants.SEMEN_ORIGIN_PTN.equals(semenBillModel.getOrigin())) {
                        SemenSaleModel semenSaleModel = semenSaleMapper.searchById(semenView.getSemenSaleRowId());
                        // 入库日期在销售日期之前
                        if (semenSaleModel.getSaleDate().compareTo(semenView.getInputDate()) > 0) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + semenView.getLineNumber() + "行，销售日期：【" + TimeUtil.format(
                                    semenSaleModel.getSaleDate(), TimeUtil.DATE_FORMAT) + "】，入库日期不能早于销售日期。");
                        }
                        SemenModel originSemenModel = semenMapper.searchById(semenSaleModel.getSemenId());
                        // 生产厂家
                        CompanyModel manufacturerModel = companyMapper.searchById(originSemenModel.getManufacturerId());
                        Long pigId = null;
                        Long houseId = null;
                        Long breedId = null;
                        Long boarMaterialId = null;
                        if (manufacturerModel.getCompanyMark() == null) {
                            // 如果生产厂家不是平台内公司
                            // 采精不在平台内，不存在真实公猪
                            breedId = originSemenModel.getBreedId();
                            boarMaterialId = spermModel.getBoarId();
                        } else {
                            // 如果生产厂家是平台内公司
                            // 采精就在平台内，存在真实公猪
                            SqlCon pigSql = new SqlCon();
                            pigSql.addMainSql("SELECT * FROM PP_L_PIG T1");
                            pigSql.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
                            pigSql.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
                            pigSql.addMainSql(" AND EXISTS(SELECT 1 FROM PP_L_PIG");
                            pigSql.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
                            pigSql.addMainSql(" AND UNIQUE_PIG_FLAG = T1.UNIQUE_PIG_FLAG");
                            pigSql.addCondition(semenView.getPigId(), " AND ROW_ID = ?)");
                            List<PigModel> pigList = setSql(pigMapper, pigSql);
                            // 如果存在满足条件的公猪(虚拟或真实存在)，则不必在创
                            if (!pigList.isEmpty()) {
                                PigModel pigModel = pigList.get(0);
                                semenView.setPigId(pigModel.getRowId());
                                pigId = pigModel.getRowId();
                                houseId = pigModel.getHouseId();
                                breedId = pigModel.getBreedId();
                                boarMaterialId = pigModel.getMaterialId();
                            } else {
                                // 精液来源的真实公猪
                                PigModel realBoarPigModel = pigMapper.searchById(semenView.getPigId());
                                // 虚拟公猪入场
                                PigMoveInView pigMoveInModel = new PigMoveInView();
                                pigMoveInModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                                pigMoveInModel.setStatus(CommonConstants.STATUS);
                                pigMoveInModel.setLineNumber(semenView.getLineNumber());
                                pigMoveInModel.setPigType(PigConstants.PIG_TYPE_BOAR);
                                pigMoveInModel.setPigClass(realBoarPigModel.getPigClass());
                                pigMoveInModel.setBirthDate(realBoarPigModel.getBirthDate());
                                // 获取虚拟公猪舍id
                                houseId = getXNBoarHouseId();
                                pigMoveInModel.setHouseId(houseId);

                                SqlCon msql = new SqlCon();
                                msql.addMainSql("SELECT BREED_ID breedId,STRAIN strain,FARM_ID beforeFarmId FROM cd_o_boar WHERE DELETED_FLAG = 0");
                                msql.addCondition(realBoarPigModel.getMaterialId(), " AND MATERIAL_ID = ?");
                                Map<String, String> sqlMap = new HashMap<>();
                                sqlMap.put("sql", msql.getCondition());
                                Map<String, String> boarMap = paramMapper.getInfos(sqlMap).get(0);

                                BoarModel boarModel = getBoarMaterial(boarMap);

                                breedId = boarModel.getBreedId();
                                String strain = boarModel.getStrain();
                                Double birthWeight = boarModel.getBirthWeight();
                                String bodyCondition = boarModel.getBodyCondition();
                                Long leftTeatNum = boarModel.getLeftTeatNum();
                                Long rightTeatNum = boarModel.getRightTeatNum();
                                String sex = PigConstants.PIG_SEX_MALE;
                                boarMaterialId = boarModel.getMaterialId();
                                pigMoveInModel.setMaterialId(boarMaterialId);

                                // 入场方式需要记录 后台 对应 OPT_TYPE
                                pigMoveInModel.setOptType(PigConstants.PIG_MOVE_IN_OPT_TYPE_VIR);
                                // 原场耳牌号信息
                                EarCodeModel realBoarEarCodeModel = earCodeMapper.searchById(realBoarPigModel.getEarCodeId());
                                pigMoveInModel.setEarBrand(realBoarEarCodeModel.getEarBrand().toUpperCase());
                                if (realBoarEarCodeModel.getEarShort() != null) {
                                    pigMoveInModel.setEarShort(realBoarEarCodeModel.getEarShort().toUpperCase());
                                }
                                if (realBoarEarCodeModel.getFatherEarId() != null) {
                                    EarCodeModel realBoarFatherEarCodeModel = earCodeMapper.searchById(realBoarEarCodeModel.getFatherEarId());
                                    pigMoveInModel.setFatherEar(realBoarFatherEarCodeModel.getEarBrand().toUpperCase());
                                }
                                if (realBoarEarCodeModel.getMotherEarId() != null) {
                                    EarCodeModel realBoarMotherEarCodeModel = earCodeMapper.searchById(realBoarEarCodeModel.getMotherEarId());
                                    pigMoveInModel.setMotherEar(realBoarMotherEarCodeModel.getEarBrand().toUpperCase());
                                }

                                pigMoveInModel.setCompanyId(getCompanyId());
                                pigMoveInModel.setFarmId(getFarmId());
                                pigMoveInModel.setBillId(billId);
                                pigMoveInModel.setBreedId(breedId);
                                pigMoveInModel.setStrain(strain);
                                pigMoveInModel.setBodyCondition(bodyCondition);
                                pigMoveInModel.setBirthParity(0l);
                                pigMoveInModel.setHouseNum(0l);
                                pigMoveInModel.setBirthWeight(realBoarPigModel.getBirthWeight());
                                if (pigMoveInModel.getBirthWeight() == null || pigMoveInModel.getBirthWeight().compareTo(0D) == 0) {
                                    pigMoveInModel.setBirthWeight(birthWeight);
                                }
                                pigMoveInModel.setLeftTeatNum(realBoarPigModel.getLeftTeatNum());
                                if (pigMoveInModel.getLeftTeatNum() == null || pigMoveInModel.getLeftTeatNum().compareTo(0L) == 0) {
                                    pigMoveInModel.setLeftTeatNum(leftTeatNum);
                                }
                                pigMoveInModel.setRightTeatNum(realBoarPigModel.getRightTeatNum());
                                if (pigMoveInModel.getRightTeatNum() == null || pigMoveInModel.getRightTeatNum().compareTo(0L) == 0) {
                                    pigMoveInModel.setRightTeatNum(rightTeatNum);
                                }
                                pigMoveInModel.setOrigin("3");
                                pigMoveInModel.setEnterDate(semenBillModel.getBillDate());
                                pigMoveInModel.setCreateId(getEmployeeId());
                                pigMoveInModel.setSex(sex);
                                pigMoveInModel.setWorker(getEmployeeId());
                                pigMoveInModel.setEventName(eventName);
                                pigMoveInModel.setRelationPigId(semenView.getPigId());

                                pigEventMapper.pigMoveIn(pigMoveInModel);

                                if (!"0".equals(pigMoveInModel.getErrorCode())) {
                                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
                                }
                                // 存储过程没报错 PIG_ID = ErrorMessage
                                pigId = Long.valueOf(pigMoveInModel.getErrorMessage());
                            }
                        }

                        // 搜索销售的spermInfo原始信息
                        SqlCon spermInfoSqlCon = new SqlCon();
                        spermInfoSqlCon.addCondition(semenSaleModel.getFarmId(), " AND FARM_ID = ?");
                        spermInfoSqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM PP_L_BILL_SEMEN_SALE_DETAIL WHERE DELETED_FLAG = '0'");
                        spermInfoSqlCon.addCondition(semenSaleModel.getRowId(), " AND SEMEN_SALE_ROW_ID = ?");
                        spermInfoSqlCon.addMainSql(" AND SPERM_ID = PP_L_SPERM_INFO.ROW_ID)");

                        List<SpermInfoModel> spermInfoModelList = getList(spermInfoMapper, spermInfoSqlCon);

                        SemenModel semenModel = new SemenModel();
                        semenModel.setStatus(CommonConstants.STATUS);
                        semenModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        semenModel.setOriginFlag(semenView.getOriginFlag());
                        semenModel.setOriginApp(semenView.getOriginApp());
                        semenModel.setNotes(semenView.getNotes());
                        semenModel.setFarmId(getFarmId());
                        semenModel.setCompanyId(getCompanyId());
                        semenModel.setLineNumber(semenView.getLineNumber());
                        semenModel.setPigId(pigId);
                        semenModel.setHouseId(houseId);
                        semenModel.setBillId(billId);
                        semenModel.setWorker(semenView.getWorker());
                        semenModel.setCreateId(getEmployeeId());
                        semenModel.setCreateDate(createDate);
                        semenModel.setMaterialId(spermModel.getMaterialId());
                        semenModel.setDayAge(semenView.getDayAge().longValue());
                        semenModel.setSemenDate(semenView.getSemenDate());
                        // 假母台
                        semenModel.setSemenType("2");
                        semenModel.setSemenQty(semenView.getSemenQty());
                        semenModel.setAnhNum(semenView.getAnhNum());
                        semenModel.setAnalust(semenView.getAnalust());
                        semenModel.setSemenBatchNo(semenView.getSemenBatchNo());
                        semenModel.setSpermMotility(semenView.getSpermMotility());
                        semenModel.setSpermDensity(semenView.getSpermDensity());
                        semenModel.setAbnormationRate(semenView.getAbnormationRate());
                        semenModel.setCohesion(semenView.getCohesion());
                        semenModel.setOdour(semenView.getOdour());
                        semenModel.setColor(semenView.getColor());
                        semenModel.setSpec(semenView.getSpec());
                        semenModel.setValidDate(semenView.getValidDate());
                        // 平台内
                        semenModel.setOrigin("2");
                        semenModel.setSupplierId(semenView.getSupplierId());
                        semenModel.setManufacturerId(originSemenModel.getManufacturerId());
                        semenModel.setWarehouseId(semenView.getWarehouseId());
                        semenModel.setPrintNum(0L);
                        semenModel.setInputDate(semenView.getInputDate());
                        semenModel.setEarBrand(semenView.getEarBrand());
                        semenModel.setBoarMaterialId(boarMaterialId);
                        semenModel.setSemenSaleRowId(semenSaleModel.getRowId());
                        semenModel.setBreedId(breedId);
                        semenModel.setSpermNum(semenView.getSpermNum());

                        semenMapper.insert(semenModel);

                        for (SpermInfoModel spermInfoModel : spermInfoModelList) {
                            spermInfoModel.setStatus(CommonConstants.STATUS);
                            spermInfoModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                            spermInfoModel.setOriginFlag(semenView.getOriginFlag());
                            spermInfoModel.setOriginApp(semenView.getOriginApp());
                            spermInfoModel.setFarmId(getFarmId());
                            spermInfoModel.setCompanyId(getCompanyId());
                            spermInfoModel.setMaterialId(spermModel.getMaterialId());
                            spermInfoModel.setSemenId(semenModel.getRowId());
                            spermInfoModel.setWarehouseId(semenModel.getWarehouseId());
                            spermInfoModel.setBillId(billId);
                            spermInfoModel.setIsSale(0L);
                            spermInfoModel.setWorker(semenView.getWorker());
                        }
                        spermInfoMapper.inserts(spermInfoModelList);

                        // 不存在猪只，不需要创建历史信息
                        if (pigId != null) {
                            PigEventHisModel pigEventHisModel = new PigEventHisModel();
                            pigEventHisModel.setStatus(CommonConstants.STATUS);
                            pigEventHisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                            pigEventHisModel.setOriginFlag(semenView.getOriginFlag());
                            pigEventHisModel.setOriginApp(semenView.getOriginApp());
                            pigEventHisModel.setFarmId(getFarmId());
                            pigEventHisModel.setCompanyId(getCompanyId());
                            pigEventHisModel.setLineNumber(semenView.getLineNumber());
                            pigEventHisModel.setPigId(semenView.getPigId());
                            pigEventHisModel.setHouseId(houseId);
                            pigEventHisModel.setBillId(billId);
                            pigEventHisModel.setWorker(semenView.getWorker());
                            pigEventHisModel.setCreateId(getEmployeeId());
                            pigEventHisModel.setCreateDate(createDate);
                            pigEventHisModel.setEarBrand(semenView.getEarBrand());
                            pigEventHisModel.setPigType(PigConstants.PIG_TYPE_BOAR);
                            pigEventHisModel.setSex(PigConstants.PIG_SEX_MALE);
                            pigEventHisModel.setPigClass(PigConstants.PIG_CLASS_SCGZ);
                            pigEventHisModel.setEnterDate(semenView.getInputDate());
                            pigEventHisModel.setLastEventDate(semenView.getInputDate());
                            pigEventHisModel.setEventName(eventName);
                            // 供应商
                            Map<String, String> supplierInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(semenModel.getSupplierId()));
                            // 生产厂家
                            Map<String, String> manufacturerInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(semenModel
                                    .getManufacturerId()));
                            pigEventHisModel.setEventNotes("平台内-供应商：【" + Maps.getString(supplierInfoMap, "SORT_NAME") + "】，生产厂家：【" + Maps.getString(
                                    manufacturerInfoMap, "SORT_NAME") + "】，精液批号：【" + semenView.getSemenBatchNo() + "】，入库总数：【" + semenModel
                                            .getSpermNum() + "】");
                            pigEventHisModel.setTableName("pp_l_bill_semen");
                            pigEventHisModel.setTableRowId(semenModel.getRowId());

                            pigEventHisMapper.insert(pigEventHisModel);
                        }

                        // 精液销售单据置为已经入库
                        semenSaleModel.setIsEntry(SpermConstants.SPERM_INFO_IS_ENTRY_ENTRY);
                        semenSaleMapper.update(semenSaleModel);
                    }
                }
            }
        }
    }

    // 获取虚拟公猪舍id，没有则创建
    private Long getXNBoarHouseId() throws Exception {
        SqlCon houseSql = new SqlCon();
        houseSql.addCondition("虚拟公猪舍", " AND HOUSE_NAME = ?");
        houseSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        HouseModel houseModel = getModel(houseMapper, houseSql);
        PigpenModel pigpenModel = null;
        if (houseModel == null) {
            houseModel = new HouseModel();
            houseModel.setFarmId(getFarmId());
            houseModel.setCompanyId(getCompanyId());
            houseModel.setStatus(CommonConstants.STATUS);
            houseModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            houseModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            houseModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            // houseModel.set
            houseModel.setHouseName("虚拟公猪舍");
            houseModel.setHouseType(PigConstants.HOUSE_CLASS_BOAR);
            String houseCode = ParamUtil.getBCode("HOUSE_TYPE_B", getEmployeeId(), getCompanyId(), getFarmId());
            houseModel.setBusinessCode(houseCode);
            houseMapper.insert(houseModel);

            pigpenModel = new PigpenModel();
            pigpenModel.setFarmId(getFarmId());
            pigpenModel.setCompanyId(getCompanyId());
            pigpenModel.setStatus(CommonConstants.STATUS);
            pigpenModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            pigpenModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            pigpenModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            pigpenModel.setHouseId(houseModel.getRowId());
            pigpenModel.setPigNum(1000000L);
            pigpenModel.setPigpenName("1");
            pigpenModel.setBusinessCode(ParamUtil.getBCode("PIG_STY", getEmployeeId(), getCompanyId(), getFarmId()));
            pigpenMapper.insert(pigpenModel);
        }
        return houseModel.getRowId();
    }

    // 精液入库虚拟公猪入场
    private Long semenXNPigMoveIn(Map<String, Object> xnMap) throws Exception {
        PigMoveInView pigMoveInView = new PigMoveInView();
        // 业务编码
        BillModel billModel = new BillModel();
        String businessCode = ParamUtil.getBCode("PIG_MOVE_IN", getEmployeeId(), getCompanyId(), getFarmId());
        billModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        billModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        billModel.setStatus(CommonConstants.STATUS);
        // 虚拟公猪入场，事件查看中无法看到，并且无法撤销
        billModel.setDeletedFlag("2");
        billModel.setNotes("虚拟公猪入场，deleted_flag置为2，无法查看，无法撤销！");
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setCreateId(getEmployeeId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode("PIG_MOVE_IN");

        // 创建表单
        billModel.setBillDate(Maps.getDate(xnMap, "billDate"));
        billMapper.insert(billModel);
        String semenBatchNo = Maps.getString(xnMap, "semenBatchNo");
        int index = semenBatchNo.indexOf("-");
        String earBrand = "";
        if (index == -1) {
            earBrand = semenBatchNo;
        } else {
            earBrand = semenBatchNo.substring(0, semenBatchNo.indexOf("-"));
        }
        pigMoveInView.setEarBrand(earBrand);
        pigMoveInView.setOptType(PigConstants.PIG_MOVE_IN_OPT_TYPE_EAR);
        pigMoveInView.setLineNumber(Maps.getLongClass(xnMap, "lineNumber"));
        pigMoveInView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        pigMoveInView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        pigMoveInView.setCompanyId(getCompanyId());
        pigMoveInView.setFarmId(getFarmId());
        pigMoveInView.setHouseId(Maps.getLongClass(xnMap, "houseId"));
        pigMoveInView.setMaterialId(Maps.getLongClass(xnMap, "materialId"));
        pigMoveInView.setPigType(PigConstants.PIG_TYPE_BOAR);
        pigMoveInView.setSex(PigConstants.PIG_SEX_MALE);
        pigMoveInView.setBreedId(Maps.getLongClass(xnMap, "breedId"));
        pigMoveInView.setStrain(Maps.getString(xnMap, "strain"));
        pigMoveInView.setPigClass(PigConstants.PIG_CLASS_SCGZ);
        pigMoveInView.setBodyCondition(Maps.getString(xnMap, "bodyCondition"));
        pigMoveInView.setParity(0l);
        pigMoveInView.setBirthDate(Maps.getDate(xnMap, "birthDate"));
        pigMoveInView.setBirthWeight(Maps.getDoubleClass(xnMap, "birthWeight"));
        pigMoveInView.setEnterDate(Maps.getDate(xnMap, "enterDate"));
        pigMoveInView.setEnterWeight(0L);
        pigMoveInView.setOnPrice(0L);
        pigMoveInView.setSupplierId(Maps.getLongClass(xnMap, "supplierId"));
        pigMoveInView.setOrigin(PigConstants.PIG_ORIGIN_XN);
        pigMoveInView.setBillId(billModel.getRowId());
        pigMoveInView.setCreateId(getEmployeeId());
        pigMoveInView.setEventName(Maps.getString(xnMap, "eventName"));
        pigMoveInView.setWorker(getEmployeeId());

        pigEventMapper.pigMoveIn(pigMoveInView);
        if (!"0".equals(pigMoveInView.getErrorCode())) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInView.getErrorMessage());
        }
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
        SqlCon pigSql = new SqlCon();
        pigSql.addCondition(billModel.getRowId(), " AND BILL_ID = ?");
        pigSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        PigModel pigModel = getModel(pigMapper, pigSql);
        return pigModel.getRowId();
    }

    // 后备情期鉴定
    @Override
    public void editPrepubertalCheck(BillModel billModel, String eventName, String breedList) throws Exception {
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
        List<ReserveToProductView> reserveToProductViews = null;
        if (breedList != null) {
            reserveToProductViews = getJsonList(breedList, ReserveToProductView.class);
            if (reserveToProductViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<ReserveToProductView> it = reserveToProductViews.iterator(); it.hasNext();) {
                ReserveToProductView reserveToProductView = (ReserveToProductView) it.next();
                if (reserveToProductView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                /* if (StringUtils.isBlank(reserveToProductView.getNewEarBrand())) {
                 * Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新耳牌号不能为空！");
                 * }
                 * if (reserveToProductView.getNewHouseId() == null) {
                 * Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪舍名称不能为空！");
                 * } */
                Date enterDate = reserveToProductView.getEnterDate();
                Date minDate = reserveToProductView.getMinValidDate();
                Date maxDate = reserveToProductView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, reserveToProductView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), enterDate);

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(reserveToProductView.getPigId(), reserveToProductView.getEnterDate(), billId);

                reserveToProductView.setBillId(billId);
                reserveToProductView.setFarmId(getFarmId());
                reserveToProductView.setCompanyId(getCompanyId());
                reserveToProductView.setCreateId(getEmployeeId());
                reserveToProductView.setEventName(eventName);
                pigEventMapper.reserveToProduct(reserveToProductView);
                if (!"0".equals(reserveToProductView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, reserveToProductView.getErrorMessage());
                }

                this.insertPigEventRelates(reserveToProductView.getPigId());

            }
        }
    }

    // 配种
    @Override
    public void editBreed(BillModel billModel, String eventName, String breedList) throws Exception {
        // String eventName = "BREED";
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

        if (StringUtil.isBlank(breedList)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
        }
        List<BreedView> breedViews = getJsonList(breedList, BreedView.class);
        if (breedViews.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "必填的字段未填或下拉框字段未选中，请检查表单！");
        }

        // START HANA
        // SAP后备转生产(母猪)
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        Date currentDate = new Date();
        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
        List<MTC_ITFC> mtcOITMMTC_ITFCList = new ArrayList<MTC_ITFC>();
        String mtcBranchID = null;
        String mtcDeptID = null;
        if (isToHana) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
        }
        // END HANA
        for (Iterator<BreedView> it = breedViews.iterator(); it.hasNext();) {
            BreedView breedView = (BreedView) it.next();
            if (breedView.getWorker() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空!");
            }
            if (breedView.getBreedBoarFirst() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "配种公猪不能为空!");
            }
            if (breedView.getEnterDate() == null) {
                Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
            }
            Date enterDate = breedView.getEnterDate();
            Date minDate = breedView.getMinValidDate();
            Date maxDate = breedView.getMaxValidDate();
            if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, breedView.getLineNumber(), TimeUtil.format(enterDate,
                        TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
            }
            sapEventMasage(getFarmId(), enterDate);
            // START HANA
            Long beforeBreedPigClass = null;
            // 在配种前获取操作前PIG_CLASS是否是4（第一次配种，创建资产卡片）
            if (isToHana) {
                // 后备转生产
                // 必须在配种前查询
                PigModel pigModel = pigMapper.searchById(breedView.getPigId());
                beforeBreedPigClass = pigModel.getPigClass();

            }
            // END HANA

            breedView.setBillId(billId);
            breedView.setFarmId(getFarmId());
            breedView.setCompanyId(getCompanyId());
            breedView.setCreateId(getEmployeeId());
            breedView.setEventName(eventName);
            pigEventMapper.breed(breedView);
            if (!"0".equals(breedView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, breedView.getErrorMessage());
            }

            // 转舍
            if (!(breedView.getInHouseId() == null && breedView.getInPigpenId() == null)) {
                if (breedView.getInHouseId() == null && breedView.getInPigpenId() != null) {
                    Thrower.throwException(ProductionException.CHANGE_HOUSE_IS_NULL, breedView.getLineNumber());
                }
                ChangeHouseView changeHouseView = new ChangeHouseView();
                changeHouseView.setLineNumber(breedView.getLineNumber());
                changeHouseView.setBillId(billId);
                changeHouseView.setFarmId(getFarmId());
                changeHouseView.setCompanyId(getCompanyId());
                changeHouseView.setEventName(eventName);
                changeHouseView.setCreateId(getEmployeeId());
                changeHouseView.setPigId(breedView.getPigId());
                changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_OTH);
                changeHouseView.setInHouseId(breedView.getInHouseId());
                changeHouseView.setInPigpenId(breedView.getInPigpenId());
                changeHouseView.setEnterDate(breedView.getEnterDate());
                changeHouseView.setWorker(breedView.getWorker());
                pigEventMapper.changeHouse(changeHouseView);
                if (!"0".equals(changeHouseView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                }
            }

            // START HANA
            if (isToHana) {
                // 后备待配（第一次配种，创建资产卡片）
                if (beforeBreedPigClass.equals(PigConstants.PIG_CLASS_HBDP)) {
                    PigModel pigModel = pigMapper.searchById(breedView.getPigId());

                    // 后备转生产 - A 应收发票（母猪）
                    MTC_ITFC mtcITFC = sendToSapChangeProductFor3010(mtcBranchID, mtcDeptID, currentDate, enterDate, pigModel, billModel, breedView
                            .getLineNumber());
                    mtcITFCList.add(mtcITFC);

                    // 后备转生产 - B 固定资产卡片
                    MTC_ITFC mtcOITMMTC_ITFC = sendToSapMTC_OITM(mtcBranchID, mtcDeptID, currentDate, pigModel, billModel);
                    mtcOITMMTC_ITFCList.add(mtcOITMMTC_ITFC);

                }
            }
            // END HANA

            this.insertPigEventRelates(breedView.getPigId());
        }

        if (!mtcITFCList.isEmpty()) {
            mtcITFCList.addAll(mtcOITMMTC_ITFCList);
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
    }

    // 流产 || 妊娠检查
    @Override
    public void editPregnancyCheck(BillModel billModel, String eventName, String pregnancyCheckList) throws Exception {

        // String eventName = "PREGNANCY_CHECK";
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
        List<PregnancyCheckView> pregnancyCheckViews = null;
        if (pregnancyCheckList != null) {
            pregnancyCheckViews = getJsonList(pregnancyCheckList, PregnancyCheckView.class);
            if (pregnancyCheckViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<PregnancyCheckView> it = pregnancyCheckViews.iterator(); it.hasNext();) {
                PregnancyCheckView pregnancyCheckView = (PregnancyCheckView) it.next();
                if (pregnancyCheckView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                // 妊娠检查结果不能为空
                if ("".equals(pregnancyCheckView.getPregnancyResult()) || pregnancyCheckView.getPregnancyResult() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【检查结果】 不能为空！");
                }
                // 妊娠检查结果不能为空
                if ("".equals(pregnancyCheckView.getPregnancyType()) || pregnancyCheckView.getPregnancyType() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【检查方式】不能为空！");
                }
                // 妊娠检查技术员不能为空
                if (pregnancyCheckView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【技术员】不能为空！");
                }
                Date enterDate = pregnancyCheckView.getEnterDate();
                Date minDate = pregnancyCheckView.getMinValidDate();
                Date maxDate = pregnancyCheckView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, pregnancyCheckView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(pregnancyCheckView.getPigId(), pregnancyCheckView.getEnterDate(), billId);

                sapEventMasage(getFarmId(), enterDate);
                pregnancyCheckView.setBillId(billId);
                pregnancyCheckView.setFarmId(getFarmId());
                pregnancyCheckView.setCompanyId(getCompanyId());
                pregnancyCheckView.setCreateId(getEmployeeId());
                pregnancyCheckView.setEventName(eventName);
                pigEventMapper.pregnancyCheck(pregnancyCheckView);
                if (!"0".equals(pregnancyCheckView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pregnancyCheckView.getErrorMessage());
                }

                this.insertPigEventRelates(pregnancyCheckView.getPigId());

            }
        }
    }

    // 分娩
    @Override
    public void editDelivery(BillModel billModel, String eventName, String deliveryList, String FMSFLZ_flag) throws Exception {
        long totalNum = 0;

        // String eventName = "DELIVERY";
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
        List<DeliveryView> deliveryViews = null;
        if (deliveryList != null) {
            deliveryViews = getJsonList(deliveryList, DeliveryView.class);
            List<Map> list = getJsonList(deliveryList, Map.class);
            if (deliveryViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            int index = 0;
            for (Iterator<DeliveryView> it = deliveryViews.iterator(); it.hasNext(); index++) {
                DeliveryView deliveryView = (DeliveryView) it.next();
                if (deliveryView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                Date enterDate = deliveryView.getEnterDate();
                Date minDate = deliveryView.getMinValidDate();
                Date maxDate = deliveryView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, deliveryView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }
                if (deliveryView.getHealthyNum() == null) {
                    deliveryView.setHealthyNum(0L);
                }
                if (deliveryView.getWeakNum() == null) {
                    deliveryView.setWeakNum(0L);
                }

                if ("on".equalsIgnoreCase(FMSFLZ_flag)) {
                    if (deliveryView.getAliveLitterY() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "活仔公不能为空！");
                    }
                    if (deliveryView.getAliveLitterX() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "活仔母不能为空！");
                    }
                    if (deliveryView.getHealthyNum() != deliveryView.getAliveLitterY() + deliveryView.getAliveLitterX()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "健仔数不等于活仔公+活仔母！");
                    }
                }
                totalNum = deliveryView.getHealthyNum() + deliveryView.getWeakNum();
                if (totalNum < 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "数量不能小于0！");
                }
                /*********************************************** 新增验证 CB 2017-8-30 12:58:57 begin ***************************************/
                Long childPigQTY = 0L;
                Long healthyNum = deliveryView.getHealthyNum() == null ? 0 : deliveryView.getHealthyNum();
                Long weakNum = deliveryView.getWeakNum() == null ? 0 : deliveryView.getWeakNum();
                Long stillbirthNum = deliveryView.getStillbirthNum() == null ? 0 : deliveryView.getStillbirthNum();
                Long mutantNum = deliveryView.getMutantNum() == null ? 0 : deliveryView.getMutantNum();
                Long mummyNum = deliveryView.getMummyNum() == null ? 0 : deliveryView.getMummyNum();
                Long blackNum = deliveryView.getBlackNum() == null ? 0 : deliveryView.getBlackNum();
                childPigQTY = healthyNum + weakNum + stillbirthNum + mutantNum + mummyNum + blackNum;
                if (childPigQTY <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "仔猪数量不能全部为0或空！");
                }
                /*********************************************** 新增验证 CB 2017-8-30 12:58:57 end ***************************************/

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(deliveryView.getPigId(), deliveryView.getEnterDate(), billId);

                deliveryView.setBillId(billId);
                deliveryView.setFarmId(getFarmId());
                deliveryView.setCompanyId(getCompanyId());
                deliveryView.setEventName(eventName);
                deliveryView.setCreateId(getEmployeeId());

                sapEventMasage(getFarmId(), enterDate);
                pigEventMapper.delivery(deliveryView);

                if (!"0".equals(deliveryView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, deliveryView.getErrorMessage());
                } else {
                    // 创建仔猪
                    CreatePorkPigView createPorkPigView = new CreatePorkPigView();
                    // 自动生成所有仔猪
                    String actionType = "2";
                    createPorkPigView.setActionType(actionType);
                    createPorkPigView.setLineNumber(deliveryView.getLineNumber());
                    createPorkPigView.setFarmId(getFarmId());

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
                    createPorkPigView.setEventName("PIG_MOVE_IN");
                    createPorkPigView.setCreateId(getEmployeeId());
                    pigEventMapper.createPorkPig(createPorkPigView);
                    if (!"0".equals(createPorkPigView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, createPorkPigView.getErrorMessage());
                    }
                    /************************************************* 2017-3-9 begin 批次日龄修改 *****************************************************/
                    /* double newSwineryDayage = 0;
                     * SqlCon allSwineryDayageByBillId = new SqlCon();
                     * allSwineryDayageByBillId.addCondition(billId, " AND BILL_ID=? ");
                     * allSwineryDayageByBillId.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1 ");
                     * List<SwineryDayageChangeModel> swineryDayageChangeModelList = getList(swineryDayageChangeMapper, allSwineryDayageByBillId);
                     * if (swineryDayageChangeModelList != null && !swineryDayageChangeModelList.isEmpty()) {
                     * SwineryDayageChangeModel swineryDayageChangeModel = swineryDayageChangeModelList.get(0);
                     * long swineryId = swineryDayageChangeModel.getSwineryId();
                     * SwineryModel swineryModel = swineryMapper.searchById(swineryId);
                     * double swineryDayage = swineryModel.getSwineryDayage();
                     * Date lastOperateDate = swineryModel.getLastOperateDate();
                     * Date deliveryDate = swineryDayageChangeModel.getEventDate();
                     * long deliveryPigNum = swineryDayageChangeModel.getChangePigNum();
                     * 
                     * SqlCon sqlCon = new SqlCon();
                     * sqlCon.addMainSql(
                     * " SELECT COUNT(1) porkPigNum FROM pp_l_pig WHERE DELETED_FLAG=0 AND STATUS=1 AND PIG_TYPE=3 AND PIG_CLASS IN (12,13,14,15,16)"
                     * );
                     * sqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
                     * sqlCon.addCondition(swineryId, " AND SWINERY_ID=? ");
                     * Map<String, String> map = new HashMap<String, String>();
                     * map.put("sql", sqlCon.getCondition());
                     * Map<String, Object> porkPigNumMap = paramMapper.getObjectInfos(map).get(0);
                     * long porkPigNum = Maps.getLong(porkPigNumMap, "porkPigNum") - deliveryPigNum;
                     * if ((porkPigNum + deliveryPigNum) != 0) {
                     * newSwineryDayage = (TimeUtil.getday(new Date(), deliveryDate) * deliveryPigNum + (TimeUtil.getday(new Date(),
                     * lastOperateDate) + swineryDayage) * porkPigNum) / (porkPigNum + deliveryPigNum);
                     * 
                     * SqlCon UpdateSwineryDayage = new SqlCon();
                     * UpdateSwineryDayage.addMainSql(" UPDATE pp_m_swinery ");
                     * UpdateSwineryDayage.addCondition(deliveryDate, " SET LAST_OPERATE_DATE=NOW()");
                     * UpdateSwineryDayage.addCondition(newSwineryDayage, ",SWINERY_DAYAGE=? ");
                     * UpdateSwineryDayage.addMainSql(" WHERE DELETED_FLAG=0 AND STATUS=1 ");
                     * UpdateSwineryDayage.addCondition(swineryId, " AND ROW_ID=? ");
                     * setSql(swineryMapper, UpdateSwineryDayage);
                     * }
                     * } */

                    /************************************************* 2017-3-9 begin 批次日龄修改 *****************************************************/
                    // 注释原因:产房弱仔不算存栏不能算产房仔猪死亡,只有当弱仔算存栏时才算产房仔猪死亡
                    // // 判断设置项:弱仔不算存栏_是否打开
                    // String settingValueByCode = ParamUtil.getSettingValueByCode("RZBSCL");
                    // if("ON".equals(settingValueByCode)){
                    // // 仔猪死亡
                    // ChildDieView childDieView = new ChildDieView();
                    // childDieView.setLineNumber(deliveryView.getLineNumber());
                    // Long houseId = Long.valueOf(String.valueOf(list.get(index).get("houseId")));
                    // childDieView.setOriginFlag(childDieView.getOriginFlag());
                    // childDieView.setOriginApp(childDieView.getOriginApp());
                    // childDieView.setCompanyId(getCompanyId());
                    // childDieView.setFarmId(getFarmId());
                    // childDieView.setHouseId(houseId);
                    //
                    // childDieView.setBillId(billId);
                    // childDieView.setEventName(eventName);
                    // childDieView.setCreateId(getEmployeeId());
                    // childDieView.setPigId(deliveryView.getPigId());
                    // childDieView.setLeaveQty(deliveryView.getWeakNum());
                    // childDieView.setEnterDate(deliveryView.getEnterDate());
                    // childDieView.setWorker(deliveryView.getWorker());
                    // childDieView.setFlag("Y");
                    // childDieView.setLeaveReason("");
                    //
                    // pigEventMapper.childPigDie(childDieView);
                    // }

                }

                this.insertPigEventRelates(deliveryView.getPigId());

            }
            /* SqlCon sqlCon = new SqlCon();
             * sqlCon.addCondition(billId, " AND BILL_ID=? ");
             * sqlCon.addMainSql(" GROUP BY SWINERY_ID ");
             * List<SwineryDayageChangeModel> swineryDayageChangeModels = getList(swineryDayageChangeMapper, sqlCon);
             * if (swineryDayageChangeModels != null && !swineryDayageChangeModels.isEmpty()) {
             * for (SwineryDayageChangeModel swineryDayageChangeModel : swineryDayageChangeModels) {
             * SqlCon UpdateLastOperateDate = new SqlCon();
             * UpdateLastOperateDate.addMainSql(" UPDATE pp_m_swinery SET LAST_OPERATE_DATE=NOW() ");
             * UpdateLastOperateDate.addMainSql(" WHERE DELETED_FLAG=0 AND STATUS=1 ");
             * UpdateLastOperateDate.addCondition(swineryDayageChangeModel.getSwineryId(), " AND ROW_ID=? ");
             * setSql(swineryMapper, UpdateLastOperateDate);
             * }
             * } */
            // 批量插入整个单据的猪仔信息
            SqlCon insertChildPigEventRelatesSqlCon = new SqlCon();
            insertChildPigEventRelatesSqlCon.addMainSql(
                    "SELECT '1' AS status, 0 AS deletedFlag, FARM_ID AS farmId, COMPANY_ID AS companyId, SWINERY_ID AS swineryId, ROW_ID AS pigId, LINE_ID AS lineId, HOUSE_ID AS houseId, PIGPEN_ID AS pigpenId,(SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE STATUS = '1' AND DELETED_FLAG = '0' AND ROW_ID = EAR_CODE_ID) AS earBrand, PIG_TYPE AS pigType, SEX AS sex, PIG_CLASS AS pigClass, PARITY AS parity, BILL_ID AS billId,LAST_EVENT_DATE AS eventDate FROM PP_L_PIG");
            insertChildPigEventRelatesSqlCon.addMainSql(" WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            insertChildPigEventRelatesSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            insertChildPigEventRelatesSqlCon.addCondition(billId, " AND BILL_ID = ?");
            // 2017-08-29改，产房内转需要转移死亡猪只，所以分娩的死亡猪只也需要插入Relates表
            // 不保存死亡的猪只记录（减少记录的数据量）
            // insertChildPigEventRelatesSqlCon.addCondition(PigConstants.PIG_CLASS_SW, " AND PIG_CLASS <> ?");

            List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, insertChildPigEventRelatesSqlCon);

            if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
                pigEventRelatesMapper.inserts(pigEventRelatesModelList);
            }

        }
        // 刷新缓存
        CacheUtil.BasicTableCaheRefresh(getFarmId());
    }

    // 仔猪死亡
    @Override
    public void editChildPigDie(BillModel billModel, String eventName, String childPigDieList) throws Exception {

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
        List<ChildDieView> childDieViews = null;
        if (childPigDieList != null) {
            childDieViews = getJsonList(childPigDieList, ChildDieView.class);
            if (childDieViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<ChildDieView> it = childDieViews.iterator(); it.hasNext();) {
                ChildDieView childDieView = (ChildDieView) it.next();
                if (childDieView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (StringUtils.isBlank(childDieView.getLeaveReason())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死亡原因不能为空！");
                }
                if (childDieView.getLeaveQty() == null || childDieView.getLeaveQty() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死亡数量不能小于或等于0！");
                }
                if (childDieView.getLeaveQty() > childDieView.getPigQty()) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死亡数量不能超过带仔数！");
                }
                if (childDieView.getLeaveWeight() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死亡总重不能为空！");
                }
                if (childDieView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }
                Date enterDate = childDieView.getEnterDate();
                Date minDate = childDieView.getMinValidDate();
                Date maxDate = childDieView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, childDieView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), enterDate);

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(childDieView.getPigId(), childDieView.getEnterDate(), billId);

                childDieView.setBillId(billId);
                childDieView.setFarmId(getFarmId());
                childDieView.setCompanyId(getCompanyId());
                childDieView.setEventName(eventName);
                childDieView.setCreateId(getEmployeeId());
                childDieView.setFlag("Y");
                pigEventMapper.childPigDie(childDieView);
                if (!"0".equals(childDieView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
                }

                // 母猪
                this.insertPigEventRelates(childDieView.getPigId());

            }

            // 批量插入整个单据的猪仔信息
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_LEAVE WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    // 仔猪
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }

        }
    }

    // 寄养
    @Override
    public void editFoster(BillModel billModel, String eventName, String fosterList) throws Exception {

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
        List<FosterView> fosterViews = null;
        if (fosterList != null) {
            fosterViews = getJsonList(fosterList, FosterView.class);
            if (fosterViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            Set<Long> pigIdSet = new HashSet<Long>();
            Set<Long> boardSowIdSet = new HashSet<Long>();

            for (Iterator<FosterView> it = fosterViews.iterator(); it.hasNext();) {
                FosterView fosterView = (FosterView) it.next();
                pigIdSet.add(fosterView.getPigId());
                boardSowIdSet.add(fosterView.getBoardSowId());
            }

            // 一个单据中，寄养母猪不能成为其他母猪的代养母猪（跨行也不可以）
            for (Long boardSowId : boardSowIdSet) {
                if (pigIdSet.contains(boardSowId)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "一个单据中，寄出母猪不能成为寄入母猪，如需完成此业务，需要两张单据！");
                }
            }

            for (Iterator<FosterView> it = fosterViews.iterator(); it.hasNext();) {
                FosterView fosterView = (FosterView) it.next();
                if (fosterView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (StringUtils.isBlank(fosterView.getFosterReason())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "寄养原因不能为空！");
                }
                if (fosterView.getBoardSowId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "代养母猪不能为空！");
                }
                if (fosterView.getFosterQty() == null || fosterView.getFosterQty() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "寄养数量不能小于或等于0！");
                }
                if (fosterView.getFosterQty() > fosterView.getPigQty()) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "寄养数量不能超过带仔数！");
                }
                if (fosterView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }

                Date enterDate = fosterView.getEnterDate();
                Date minDate = fosterView.getMinValidDate();
                Date maxDate = fosterView.getMaxValidDate();
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, fosterView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), enterDate);

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(fosterView.getPigId(), fosterView.getEnterDate(), billId);

                fosterView.setBillId(billId);
                fosterView.setFarmId(getFarmId());
                fosterView.setCompanyId(getCompanyId());
                fosterView.setEventName(eventName);
                fosterView.setCreateId(getEmployeeId());
                pigEventMapper.foster(fosterView);
                if (!"0".equals(fosterView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, fosterView.getErrorMessage());
                }

                this.insertPigEventRelates(fosterView.getPigId());
                // 插入被寄养母猪
                this.insertPigEventRelates(fosterView.getBoardSowId());

            }

            // 批量插入整个单据的猪仔信息
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_FOSTER_DETAIL WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }

        }
    }

    // 断奶
    @Override
    public void editWean(BillModel billModel, String eventName, String weanList, String dnxycbbFlag, String autoFosterDieFlag) throws Exception {

        // String eventName = "WEAN";
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
        List<WeanView> weanViews = null;
        if (weanList != null) {
            weanViews = getJsonList(weanList, WeanView.class);
            if (weanViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            // 是否勾选自动寄养死亡 0 勾选 1：未勾选
            if ("1".equals(autoFosterDieFlag)) {
                int sumWeanQty = 0;
                int sumPigQty = 0;
                int sumDieQty = 0;
                for (WeanView weanView : weanViews) {
                    if (weanView.getWeanNum() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，断奶数量不能为空，若为零，请填写零！");
                    }
                    sumWeanQty += weanView.getWeanNum().intValue();
                    sumPigQty += weanView.getPigQty().intValue();
                }
                sumDieQty = sumPigQty - sumWeanQty;
                if (sumWeanQty > sumPigQty) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "断奶总数不能大于总带仔数！");
                }
                // 寄入母猪列表
                List<WeanView> posterPigList = new ArrayList<WeanView>();
                // 寄出母猪列表
                List<WeanView> feedPigList = new ArrayList<WeanView>();
                // 断奶母猪列表
                List<WeanView> weanPigList = new ArrayList<WeanView>();
                // 母猪列表
                List<WeanView> pigList = new ArrayList<WeanView>();
                int dieNum = 0;
                for (int i = 0; i < weanViews.size(); i++) {
                    if (weanViews.get(i).getEnterDate() == null) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                    }
                    if (weanViews.get(i).getWeanNum() == null || weanViews.get(i).getWeanNum() < 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanViews.get(i).getLineNumber() + "行，断奶数量不能为空或小于0！");
                    }
                    if (weanViews.get(i).getWeanWeight() == null || weanViews.get(i).getWeanWeight() < 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanViews.get(i).getLineNumber() + "行，断奶窝重不能为空或小于0！");
                    }
                    if (weanViews.get(i).getInHouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanViews.get(i).getLineNumber() + "行，断奶母猪转出舍不能为空！");
                    }
                    if (weanViews.get(i).getWorker() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanViews.get(i).getLineNumber() + "行，技术员不能为空！");
                    }
                    Date enterDate = weanViews.get(i).getEnterDate();
                    Date minDate = weanViews.get(i).getMinValidDate();
                    Date maxDate = weanViews.get(i).getMaxValidDate();
                    if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, weanViews.get(i).getLineNumber(), TimeUtil.format(enterDate,
                                TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate,
                                        TimeUtil.DATE_FORMAT));
                    }
                    sapEventMasage(getFarmId(), enterDate);

                    // 若存在审批中淘汰单，则自动退回
                    editObsoleteDefeatAuto(weanViews.get(i).getPigId(), weanViews.get(i).getEnterDate(), billId);

                    weanViews.get(i).setBillId(billId);
                    weanViews.get(i).setFarmId(getFarmId());
                    weanViews.get(i).setCompanyId(getCompanyId());
                    weanViews.get(i).setEventName(eventName);
                    weanViews.get(i).setCreateId(getEmployeeId());
                    weanViews.get(i).setSysInput("2");
                    if (sumDieQty > 0) {
                        if (weanViews.get(i).getPigQty() > weanViews.get(i).getWeanNum()) {
                            dieNum = weanViews.get(i).getPigQty().intValue() - weanViews.get(i).getWeanNum().intValue();
                            ChildDieView childDieView = new ChildDieView();
                            childDieView.setLineNumber(weanViews.get(i).getLineNumber());
                            childDieView.setHouseId(weanViews.get(i).getInHouseId());
                            childDieView.setBillId(billId);
                            childDieView.setFarmId(getFarmId());
                            childDieView.setCompanyId(getCompanyId());
                            childDieView.setEventName(eventName);
                            childDieView.setCreateId(getEmployeeId());
                            childDieView.setPigId(weanViews.get(i).getPigId());
                            childDieView.setEnterDate(weanViews.get(i).getEnterDate());
                            childDieView.setWorker(weanViews.get(i).getWorker());
                            childDieView.setFlag("Y");
                            if (sumDieQty >= dieNum) {
                                weanViews.get(i).setDieNum(Long.valueOf(dieNum));
                                weanViews.get(i).setPigQty(weanViews.get(i).getPigQty() - dieNum);
                                childDieView.setLeaveQty(Long.valueOf(dieNum));
                                sumDieQty = sumDieQty - dieNum;
                            } else {
                                weanViews.get(i).setDieNum(Long.valueOf(sumDieQty));
                                weanViews.get(i).setPigQty(weanViews.get(i).getPigQty() - sumDieQty);
                                childDieView.setLeaveQty(Long.valueOf(Long.valueOf(sumDieQty)));
                                sumDieQty = 0;
                            }
                            pigEventMapper.childPigDie(childDieView);
                            if (!"0".equals(childDieView.getErrorCode())) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
                            }
                        }
                    }
                    if (weanViews.get(i).getPigQty() > weanViews.get(i).getWeanNum()) {
                        feedPigList.add(weanViews.get(i));
                    } else if (weanViews.get(i).getPigQty() < weanViews.get(i).getWeanNum()) {
                        posterPigList.add(weanViews.get(i));
                    } else if (weanViews.get(i).getPigQty() == weanViews.get(i).getWeanNum()) {
                        weanPigList.add(weanViews.get(i));
                    }
                }
                // 寄入列表
                for (int k = 0; k < posterPigList.size(); k++) {
                    int posterNum = posterPigList.get(k).getWeanNum().intValue() - posterPigList.get(k).getPigQty().intValue();
                    // 寄出列表
                    for (int j = 0; j < feedPigList.size(); j++) {
                        int feedNum = feedPigList.get(j).getPigQty().intValue() - feedPigList.get(j).getWeanNum().intValue();
                        if (posterNum > 0 && feedNum > 0) {
                            FosterView fosterView = new FosterView();
                            fosterView.setLineNumber(feedPigList.get(j).getLineNumber());
                            fosterView.setPigType(feedPigList.get(j).getPigType());
                            fosterView.setPigId(feedPigList.get(j).getPigId());
                            fosterView.setEnterDate(feedPigList.get(j).getEnterDate());
                            fosterView.setEventName(eventName);
                            fosterView.setWorker(feedPigList.get(j).getWorker());
                            fosterView.setBillId(billId);
                            fosterView.setCreateId(feedPigList.get(j).getWorker());
                            fosterView.setMinValidDate(feedPigList.get(j).getMinValidDate());
                            fosterView.setMaxValidDate(feedPigList.get(j).getMaxValidDate());
                            // fosterView.setPigQty(posterPigList.get(k).getPigQty());
                            fosterView.setBoardSowId(posterPigList.get(k).getPigId());
                            // 寄养原因：产仔过多
                            fosterView.setFosterReason("1");
                            fosterView.setFlag("Y");
                            fosterView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                            fosterView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                            if (posterNum <= feedNum) {
                                fosterView.setFosterQty(Long.valueOf(posterNum));
                                posterPigList.get(k).setPigQty(posterPigList.get(k).getPigQty() + posterNum);
                                feedPigList.get(j).setPigQty(feedPigList.get(j).getPigQty() - posterNum);
                                posterNum = 0;
                            } else {
                                fosterView.setFosterQty(Long.valueOf(feedNum));
                                posterPigList.get(k).setPigQty(posterPigList.get(k).getPigQty() + feedNum);
                                feedPigList.get(j).setPigQty(feedPigList.get(j).getPigQty() - feedNum);
                                posterNum = posterNum - feedNum;
                            }
                            pigEventMapper.foster(fosterView);
                            if (!"0".equals(fosterView.getErrorCode())) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, fosterView.getErrorMessage());
                            }
                        }
                    }
                    if (posterNum == 0) {
                        posterPigList.get(k).setPigQty(posterPigList.get(k).getWeanNum());
                    }
                }
                pigList.addAll(feedPigList);
                pigList.addAll(posterPigList);
                pigList.addAll(weanPigList);
                // 断奶操作和其他操作
                if (pigList != null && pigList.size() > 0) {
                    for (WeanView weanView : pigList) {
                        // 仔猪已经死亡，确保断奶数加死亡数等于带仔数
                        weanView.setDieNum(0L);
                        pigEventMapper.wean(weanView);
                        if (!"0".equals(weanView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, weanView.getErrorMessage());
                        }
                        // 转舍
                        ChangeHouseView changeHouseView = new ChangeHouseView();
                        changeHouseView.setLineNumber(weanView.getLineNumber());
                        changeHouseView.setBillId(billId);
                        changeHouseView.setFarmId(getFarmId());
                        changeHouseView.setCompanyId(getCompanyId());
                        changeHouseView.setEventName(eventName);
                        changeHouseView.setCreateId(getEmployeeId());
                        changeHouseView.setPigId(weanView.getPigId());
                        changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_DOWN_DEL); // 下产房
                        changeHouseView.setInHouseId(weanView.getInHouseId());
                        changeHouseView.setInPigpenId(weanView.getInPigpenId());
                        changeHouseView.setEnterDate(weanView.getEnterDate());
                        changeHouseView.setBackfat(weanView.getBackfat());
                        changeHouseView.setScore(weanView.getScore());
                        changeHouseView.setWorker(weanView.getWorker());
                        pigEventMapper.changeHouse(changeHouseView);
                        if (!"0".equals(changeHouseView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                        }

                        // 当转产房需要测背膘设置开启，必须填入背膘
                        if ("on".equalsIgnoreCase(dnxycbbFlag)) {
                            if (weanView.getBackfat() == null) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，背膘不能为空");
                            }
                        }

                        if (weanView.getBackfat() != null) {
                            // 背膘
                            BackfatView backfatView = new BackfatView();
                            backfatView.setLineNumber(weanView.getLineNumber());
                            backfatView.setBillId(billId);
                            backfatView.setFarmId(getFarmId());
                            backfatView.setCompanyId(getCompanyId());
                            backfatView.setEventName(eventName);
                            backfatView.setCreateId(getEmployeeId());
                            backfatView.setPigId(weanView.getPigId());
                            backfatView.setEnterDate(weanView.getEnterDate());
                            backfatView.setBackfat(weanView.getBackfat());
                            backfatView.setScore(weanView.getScore());
                            backfatView.setWorker(weanView.getWorker());
                            backfatView.setBackfatStage(PigConstants.BACKFAT_STAGE_WEAN);
                            pigEventMapper.backfat(backfatView);
                            if (!"0".equals(backfatView.getErrorCode())) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, backfatView.getErrorMessage());
                            }
                        }
                        this.insertPigEventRelates(weanView.getPigId());
                    }
                }
            } else {
                for (Iterator<WeanView> it = weanViews.iterator(); it.hasNext();) {
                    WeanView weanView = (WeanView) it.next();
                    if (weanView.getEnterDate() == null) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                    }
                    if (weanView.getWeanNum() == null || weanView.getWeanNum() < 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，断奶数量不能为空或小于0！");
                    }
                    if (weanView.getWeanWeight() == null || weanView.getWeanWeight() < 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，断奶窝重不能为空或小于0！");
                    }
                    /** BEGIN 防止传入死亡数量为空，导致下面判断出现空指针异常 WCH **/
                    if (weanView.getDieNum() == null) {
                        weanView.setDieNum(0L);
                    }
                    /** END **/
                    if (weanView.getPigQty() < weanView.getDieNum() + weanView.getWeanNum()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，断奶数+死亡数量不能大于带仔数！");
                    }
                    if (weanView.getInHouseId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，断奶母猪转出舍不能为空！");
                    }
                    if (weanView.getWorker() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，技术员不能为空！");
                    }
                    Date enterDate = weanView.getEnterDate();
                    Date minDate = weanView.getMinValidDate();
                    Date maxDate = weanView.getMaxValidDate();
                    if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                        Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, weanView.getLineNumber(), TimeUtil.format(enterDate,
                                TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate,
                                        TimeUtil.DATE_FORMAT));
                    }

                    // 若存在审批中淘汰单，则自动退回
                    editObsoleteDefeatAuto(weanView.getPigId(), weanView.getEnterDate(), billId);

                    weanView.setBillId(billId);
                    weanView.setFarmId(getFarmId());
                    weanView.setCompanyId(getCompanyId());
                    weanView.setEventName(eventName);
                    weanView.setCreateId(getEmployeeId());
                    weanView.setSysInput("2");
                    // 先断奶 再仔猪死亡，不然影响母猪带仔数
                    pigEventMapper.wean(weanView);
                    if (!"0".equals(weanView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, weanView.getErrorMessage());
                    }
                    // 仔猪死亡
                    if (weanView.getDieNum() > 0) {
                        ChildDieView childDieView = new ChildDieView();
                        childDieView.setLineNumber(weanView.getLineNumber());
                        childDieView.setHouseId(weanView.getInHouseId());
                        childDieView.setBillId(billId);
                        childDieView.setFarmId(getFarmId());
                        childDieView.setCompanyId(getCompanyId());
                        childDieView.setEventName(eventName);
                        childDieView.setCreateId(getEmployeeId());
                        childDieView.setPigId(weanView.getPigId());
                        childDieView.setLeaveQty(weanView.getDieNum());
                        childDieView.setEnterDate(weanView.getEnterDate());
                        childDieView.setWorker(weanView.getWorker());
                        childDieView.setFlag("Y");
                        pigEventMapper.childPigDie(childDieView);
                        if (!"0".equals(childDieView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
                        }
                    }

                    // 转舍
                    ChangeHouseView changeHouseView = new ChangeHouseView();
                    changeHouseView.setLineNumber(weanView.getLineNumber());
                    changeHouseView.setBillId(billId);
                    changeHouseView.setFarmId(getFarmId());
                    changeHouseView.setCompanyId(getCompanyId());
                    changeHouseView.setEventName(eventName);
                    changeHouseView.setCreateId(getEmployeeId());
                    changeHouseView.setPigId(weanView.getPigId());
                    changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_DOWN_DEL); // 下产房
                    changeHouseView.setInHouseId(weanView.getInHouseId());
                    changeHouseView.setInPigpenId(weanView.getInPigpenId());
                    changeHouseView.setEnterDate(weanView.getEnterDate());
                    changeHouseView.setBackfat(weanView.getBackfat());
                    changeHouseView.setScore(weanView.getScore());
                    changeHouseView.setWorker(weanView.getWorker());
                    pigEventMapper.changeHouse(changeHouseView);
                    if (!"0".equals(changeHouseView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                    }

                    // 当转产房需要测背膘设置开启，必须填入背膘
                    if ("on".equalsIgnoreCase(dnxycbbFlag)) {
                        if (weanView.getBackfat() == null) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + weanView.getLineNumber() + "行，背膘不能为空");
                        }
                    }

                    if (weanView.getBackfat() != null) {
                        // 背膘
                        BackfatView backfatView = new BackfatView();
                        backfatView.setLineNumber(weanView.getLineNumber());
                        backfatView.setBillId(billId);
                        backfatView.setFarmId(getFarmId());
                        backfatView.setCompanyId(getCompanyId());
                        backfatView.setEventName(eventName);
                        backfatView.setCreateId(getEmployeeId());
                        backfatView.setPigId(weanView.getPigId());

                        backfatView.setEnterDate(weanView.getEnterDate());
                        backfatView.setBackfat(weanView.getBackfat());
                        backfatView.setScore(weanView.getScore());
                        backfatView.setWorker(weanView.getWorker());
                        backfatView.setBackfatStage(PigConstants.BACKFAT_STAGE_WEAN);
                        pigEventMapper.backfat(backfatView);
                        if (!"0".equals(backfatView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, backfatView.getErrorMessage());
                        }
                    }

                    this.insertPigEventRelates(weanView.getPigId());

                }
            }
            // 批量插入整个单据的猪仔信息
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_WEAN_DETAIL WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            getChildPigIdSqlCon.addMainSql(" UNION");
            getChildPigIdSqlCon.addMainSql(" SELECT PIG_ID AS rowId FROM PP_L_BILL_LEAVE WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }

        }
    }

    // 转保育
    @Override
    public void editToChildCare(BillModel billModel, String eventName, String toChildList) throws Exception {

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

        List<Map<String, Object>> toChildLists = getMapList(toChildList);
        StringBuilder inHouseSB = new StringBuilder();
        StringBuilder swineryIdSB = new StringBuilder();
        for (Map<String, Object> map : toChildLists) {
            String inHouseId = (String) map.get("inHouseId");
            if (inHouseId != null && !"".equals(inHouseId)) {
                inHouseSB.append(inHouseId);
                inHouseSB.append(",");
            }
            String swineryId = (String) map.get("swineryId");
            if (swineryId != null && !"".equals(swineryId)) {
                swineryIdSB.append(swineryId);
                swineryIdSB.append(",");
            }
        }
        // 产房转保育
        Long[] pigClassDel = new Long[] { 14L };
        // 保育内转
        Long[] pigClassCare = new Long[] { 15L };
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("farmId", getFarmId());
        map.put("houseId", inHouseSB.length() > 0 ? inHouseSB.substring(0, inHouseSB.length() - 1) : "");
        map.put("swineryId", swineryIdSB.length() > 0 ? swineryIdSB.substring(0, swineryIdSB.length() - 1) : "");
        map.put("pigClass", pigClassCare);
        List<ValidPigModel> validPigModelCare = sweachValidPigMapper.searchChildCareToList(map);
        map.put("pigClass", pigClassDel);
        List<ValidPigModel> validPigModelDel = sweachValidPigMapper.searchChildCareToList(map);
        List<ValidPigModel> validPigModels = null;

        /** START THL 2017-05-23 **/
        // 记录本次事件创建的批次
        Set<Long> createSwinerySet = new HashSet<Long>();
        /** END THL 2017-05-23 **/

        List<ToChildView> toChildViews = null;
        if (toChildList != null) {
            toChildViews = getJsonList(toChildList, ToChildView.class);
            if (toChildViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (ToChildView toChildView : toChildViews) {
                if (toChildView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (toChildView.getChildQty() == null || toChildView.getChildQty() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转保育猪只数量不能小于等于0！");
                }
                if (toChildView.getChildWeight() == null || toChildView.getChildWeight() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "总重量不能为空或小于等于0！");
                }
                if (toChildView.getOldSwineryId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转出猪群不能为空！");
                }
                if (toChildView.getInHouseId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转入猪舍不能为空！");
                }
                if (toChildView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转保育日期不能为空！");
                }
                if (toChildView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }
                if (toChildView.getChildQty() > toChildView.getPigQty()) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转保育数量不能大于猪只数量！");
                }

                /** START THL 2017-05-23 **/
                // 转入转出批次为同一批次
                if (PigConstants.SWINERY_MERGE.equals(toChildView.getMergeSwinery())) {
                    if (toChildView.getOldSwineryId() != null && toChildView.getSwineryId() != null) {
                        if (toChildView.getOldSwineryId().compareTo(toChildView.getSwineryId()) == 0) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber() + "行，转入转出批次不能相同！");
                        }
                    }
                }

                sapEventMasage(getFarmId(), toChildView.getEnterDate());

                // 依据批次合并规则
                this.swineryMergeCheck(toChildView, createSwinerySet);
                /** END THL 2017-05-23 **/

                Date enterDate = toChildView.getEnterDate();
                Date minDate = toChildView.getMinValidDate();
                Date maxDate = toChildView.getMaxValidDate();
                // 转出批次时间合格判断
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, toChildView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                // 转入批次时间合格判断
                if (PigConstants.CHANGE_HOUSE_TYPE_CHI_SELF.equals(toChildView.getChangeHouseType())) {
                    validPigModels = validPigModelCare;
                } else if (PigConstants.CHANGE_HOUSE_TYPE_CHI.equals(toChildView.getChangeHouseType())) {
                    validPigModels = validPigModelDel;
                }
                if (validPigModels != null && !validPigModels.isEmpty()) {
                    for (ValidPigModel validPigModel : validPigModels) {
                        if (toChildView.getSwineryId() != null) {
                            if (validPigModel.getSwineryId().equals(toChildView.getSwineryId())) {
                                Date newMinDate = validPigModel.getMinValidDate();
                                Date newMaxDate = validPigModel.getMaxValidDate();
                                Date newEnterDate = toChildView.getEnterDate();
                                // 转入猪舍时间判断
                                if (!(TimeUtil.daysBetween(newEnterDate, newMinDate) >= 0 && TimeUtil.daysBetween(newEnterDate, newMaxDate) <= 0)) {
                                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, toChildView.getLineNumber(), TimeUtil.format(
                                            newEnterDate, TimeUtil.DATE_FORMAT), TimeUtil.format(newMinDate, TimeUtil.DATE_FORMAT), TimeUtil.format(
                                                    newMaxDate, TimeUtil.DATE_FORMAT));
                                }
                            }
                        }
                    }
                }

                toChildView.setBillId(billId);
                toChildView.setFarmId(getFarmId());
                toChildView.setCreateId(getEmployeeId());
                toChildView.setCompanyId(getCompanyId());
                toChildView.setEventName(eventName);
                /********************************* yangy start转保育 批次修改 ***********************************/
                Long childQty = toChildView.getChildQty();
                String inPigpenIds = toChildView.getInPigpenId();
                if (toChildView.getInPigpenId() != null && toChildView.getInPigpenId().length() > 0) {
                    String[] inPigpenIds_ = inPigpenIds.split(",");
                    long pigpenNum = 0;
                    long lastPigpenNum = 0;
                    if (childQty % inPigpenIds_.length == 0) {
                        pigpenNum = childQty / inPigpenIds_.length;
                    } else {
                        pigpenNum = childQty / inPigpenIds_.length;
                        lastPigpenNum = childQty - (inPigpenIds_.length - 1) * (childQty / inPigpenIds_.length);
                    }
                    for (int i = 0; i < inPigpenIds_.length; i++) {
                        if (i == inPigpenIds_.length - 1 && lastPigpenNum > 0) {
                            toChildView.setInPigpenId(inPigpenIds_[i]);
                            toChildView.setChildQty(lastPigpenNum);
                        } else {
                            toChildView.setInPigpenId(inPigpenIds_[i]);
                            toChildView.setChildQty(pigpenNum);
                        }
                    }
                } else {
                    /********************************* chengb egin转保育 解决0栏位问题 2016-12-14 15:21 ***********************************/
                    // 当转入猪栏为空时，设置默认值为空
                    if (StringUtil.isBlank(toChildView.getInPigpenId())) {
                        toChildView.setInPigpenId(null);
                    }
                    /********************************* chengb end转保育 解决0栏位问题 2016-12-14 15:21 ***********************************/
                }

                pigEventMapper.toChild(toChildView);

                /** START THL 2017-05-23 **/
                // 如果传入批次为NULL
                if (toChildView.getSwineryId() == null) {
                    // 获得创建的批次
                    createSwinerySet.add(toChildView.getCreateSwineryId());
                }
                /** END THL 2017-05-23 **/

                if (!"0".equals(toChildView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, toChildView.getErrorMessage());
                }
                /********************************* yangy end转保育 批次修改 ***********************************/

            }
            // 插入肉猪的PigEventRelates
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_TO_CHILD WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }
        }
        // 刷新缓存
        CacheUtil.BasicTableCaheRefresh(getFarmId());
    }

    // 转育肥
    @Override
    public void editToChildFatten(BillModel billModel, String eventName, String weanList) throws Exception {

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

        List<Map<String, Object>> weanLists = getMapList(weanList);
        StringBuilder inHouseSB = new StringBuilder();
        StringBuilder swineryIdSB = new StringBuilder();
        for (Map<String, Object> map : weanLists) {
            String inHouseId = (String) map.get("inHouseId");
            if (inHouseId != null && !"".equals(inHouseId)) {
                inHouseSB.append(inHouseId);
                inHouseSB.append(",");
            }
            String swineryId = (String) map.get("swineryId");
            if (swineryId != null && !"".equals(swineryId)) {
                swineryIdSB.append(swineryId);
                swineryIdSB.append(",");
            }
        }
        Long[] pigClassFatten = new Long[] { 16L };
        Long[] pigClassCare = new Long[] { 15L };
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("farmId", getFarmId());
        map.put("houseId", inHouseSB.length() > 0 ? inHouseSB.substring(0, inHouseSB.length() - 1) : "");
        map.put("swineryId", swineryIdSB.length() > 0 ? swineryIdSB.substring(0, swineryIdSB.length() - 1) : "");
        map.put("pigClass", pigClassCare);
        List<ValidPigModel> validPigModelFatten = sweachValidPigMapper.searchChildCareToList(map);
        map.put("pigClass", pigClassFatten);
        List<ValidPigModel> validPigModelCare = sweachValidPigMapper.searchChildCareToList(map);
        List<ValidPigModel> validPigModels = null;

        /** START THL 2017-05-23 **/
        // 记录本次事件创建的批次
        Set<Long> createSwinerySet = new HashSet<Long>();
        /** END THL 2017-05-23 **/

        List<ToChildView> toChildViews = null;
        if (weanList != null) {
            toChildViews = getJsonList(weanList, ToChildView.class);
            if (toChildViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (ToChildView toChildView : toChildViews) {
                if (toChildView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (toChildView.getChildQty() == null || toChildView.getChildQty() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转育肥猪只数量不能小于等于0！");
                }
                if (toChildView.getChildWeight() == null || toChildView.getChildWeight() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "总重量不能为空或小于等于0！");
                }
                if (toChildView.getOldSwineryId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转出猪群不能为空！");
                }
                if (toChildView.getInHouseId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转入猪舍不能为空！");
                }
                if (toChildView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转育肥日期不能为空！");
                }
                if (toChildView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }
                if (toChildView.getChildQty() > toChildView.getPigQty()) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "转育肥数量不能大于猪只数量！");
                }

                /** START THL 2017-05-23 **/
                // 转入转出批次为同一批次
                if (PigConstants.SWINERY_MERGE.equals(toChildView.getMergeSwinery())) {
                    if (toChildView.getOldSwineryId() != null && toChildView.getSwineryId() != null) {
                        if (toChildView.getOldSwineryId().compareTo(toChildView.getSwineryId()) == 0) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber() + "行，转入转出批次不能相同！");
                        }
                    }
                }

                sapEventMasage(getFarmId(), toChildView.getEnterDate());

                // 依据批次合并规则
                this.swineryMergeCheck(toChildView, createSwinerySet);
                /** END THL 2017-05-23 **/

                Date enterDate = toChildView.getEnterDate();
                Date minDate = toChildView.getMinValidDate();
                Date maxDate = toChildView.getMaxValidDate();

                // 转出批次时间合格判断
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, toChildView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                // 转入批次时间合格判断
                if (PigConstants.CHANGE_HOUSE_TYPE_FAT_SELF.equals(toChildView.getChangeHouseType())) {
                    validPigModels = validPigModelCare;
                } else if (PigConstants.CHANGE_HOUSE_TYPE_FAT.equals(toChildView.getChangeHouseType())) {
                    validPigModels = validPigModelFatten;
                }
                if (validPigModels != null && !validPigModels.isEmpty()) {
                    for (ValidPigModel validPigModel : validPigModels) {
                        if (toChildView.getSwineryId() != null) {
                            if (validPigModel.getSwineryId().equals(toChildView.getSwineryId())) {
                                Date newMinDate = validPigModel.getMinValidDate();
                                Date newMaxDate = validPigModel.getMaxValidDate();
                                Date newEnterDate = toChildView.getEnterDate();
                                if (!(TimeUtil.daysBetween(newEnterDate, newMinDate) >= 0 && TimeUtil.daysBetween(newEnterDate, newMaxDate) <= 0)) {
                                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, toChildView.getLineNumber(), TimeUtil.format(
                                            newEnterDate, TimeUtil.DATE_FORMAT), TimeUtil.format(newMinDate, TimeUtil.DATE_FORMAT), TimeUtil.format(
                                                    newMaxDate, TimeUtil.DATE_FORMAT));
                                }
                            }
                        }
                    }
                }

                toChildView.setBillId(billId);
                toChildView.setFarmId(getFarmId());
                toChildView.setCreateId(getEmployeeId());
                toChildView.setCompanyId(getCompanyId());
                toChildView.setEventName(eventName);
                /********************************* yangy start转育肥 批次修改 ***********************************/
                Long childQty = toChildView.getChildQty();
                String inPigpenIds = toChildView.getInPigpenId();
                if (toChildView.getInPigpenId() != null && toChildView.getInPigpenId().length() > 0) {
                    String[] inPigpenIds_ = inPigpenIds.split(",");
                    long pigpenNum = 0;
                    long lastPigpenNum = 0;
                    if (childQty % inPigpenIds_.length == 0) {
                        pigpenNum = childQty / inPigpenIds_.length;
                    } else {
                        pigpenNum = childQty / inPigpenIds_.length;
                        lastPigpenNum = childQty - (inPigpenIds_.length - 1) * (childQty / inPigpenIds_.length);
                    }
                    for (int i = 0; i < inPigpenIds_.length; i++) {
                        if (i == inPigpenIds_.length - 1 && lastPigpenNum > 0) {
                            toChildView.setInPigpenId(inPigpenIds_[i]);
                            toChildView.setChildQty(lastPigpenNum);
                        } else {
                            toChildView.setInPigpenId(inPigpenIds_[i]);
                            toChildView.setChildQty(pigpenNum);
                        }

                    }
                } else {
                    /********************************* chengb begin转保育 解决0栏位问题 2016-12-14 15:21 ***********************************/
                    // 当转入猪栏为空时，设置默认值为空
                    if (StringUtil.isBlank(toChildView.getInPigpenId())) {
                        toChildView.setInPigpenId(null);
                    }
                    /********************************* chengb end转保育 解决0栏位问题 2016-12-14 15:21 ***********************************/
                }

                pigEventMapper.toChild(toChildView);

                /** START THL 2017-05-23 **/
                // 如果传入批次为NULL
                if (toChildView.getSwineryId() == null) {
                    // 获得创建的批次
                    createSwinerySet.add(toChildView.getCreateSwineryId());
                }
                /** END THL 2017-05-23 **/

                if (!"0".equals(toChildView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, toChildView.getErrorMessage());
                }
                /********************************* yangy end转育肥 批次修改 ***********************************/
            }
            // 插入肉猪的PigEventRelates
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_TO_CHILD WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }
        }
        // 刷新缓存
        CacheUtil.BasicTableCaheRefresh(getFarmId());
    }

    // 依据批次合并规则
    private void swineryMergeCheck(ToChildView toChildView, Set<Long> createSwinerySet) throws Exception {
        // 1.如已有存栏批次，录入转入数量>=20头且转出批次日龄与转入到的批次日龄差值<=7日龄时，猪只由转出批次合并到转入批次中，头数相加，日龄进行合并计算。
        // 2.如已有存栏批次，录入转入数量>=20头且转出批次日龄与转入到的批次日龄差值>7日龄时，按批次名称生成规则新增批次，新生成批次日龄不变。
        // 3.如已有存栏批次，录入转入数量<20时，批次合并，转出批次信息合并到转入批次信息中，头数相加，日龄进行合并计算。
        Date currentDate = new Date();

        String pchbtsxz = ParamUtil.getSettingValueByCode("PCHBTSXZ");
        Long childQtyFromSetting = null;
        try {
            childQtyFromSetting = Long.valueOf(pchbtsxz);
            if (childQtyFromSetting < 0L) {
                childQtyFromSetting = 0L;
            } else if (childQtyFromSetting > 9999L) {
                childQtyFromSetting = 9999L;
            }
        }
        catch (Exception e) {
            childQtyFromSetting = 9999L;
        }

        String pchbrlcxz = ParamUtil.getSettingValueByCode("PCHBRLCXZ");
        Long swineryAgeDiffFromSetting = null;
        try {
            swineryAgeDiffFromSetting = Long.valueOf(pchbrlcxz);
            if (swineryAgeDiffFromSetting < 0L) {
                swineryAgeDiffFromSetting = 0L;
            } else if (swineryAgeDiffFromSetting > 9999L) {
                swineryAgeDiffFromSetting = 9999L;
            }
        }
        catch (Exception e) {
            swineryAgeDiffFromSetting = 9999L;
        }

        // 原批次日龄
        Long oldSwineryAge = ParamUtil.getSwineryAge(toChildView.getOldSwineryId(), currentDate);

        // 如已有存栏批次,计算出最小的批次日龄差值
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT B.swineryId,B.swineryAgeDiff FROM (");
        sqlCon.addMainSql("SELECT T1.ROW_ID AS swineryId");
        sqlCon.addMainSql(",ABS(ROUND(IFNULL(_getSwineryAge(T1.ROW_ID");
        sqlCon.addCondition(TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT), ",?");
        sqlCon.addMainSql(",IF(T1.HOUSE_TYPE = 6,1,0)),0),0)");
        sqlCon.addCondition(oldSwineryAge, " - ?) AS swineryAgeDiff");
        sqlCon.addMainSql(" FROM PP_M_SWINERY T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM(");
        sqlCon.addMainSql(" SELECT SWINERY_ID FROM PP_L_PIG WHERE DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" AND PIG_CLASS <= 18");
        sqlCon.addCondition(PigConstants.PIG_TYPE_PORK, " AND PIG_TYPE = ?");
        sqlCon.addCondition(toChildView.getInHouseId(), " AND HOUSE_ID = ?");
        sqlCon.addMainSql(" GROUP BY SWINERY_ID");
        sqlCon.addMainSql(" )A WHERE A.SWINERY_ID = T1.ROW_ID)");
        sqlCon.addCondition(swineryAgeDiffFromSetting, " )B WHERE B.swineryAgeDiff <= ?");

        List<Map<String, Object>> swineryAgeDiffList = paramMapperSetSQL(paramMapper, sqlCon);

        if (swineryAgeDiffList.isEmpty()) {
            // 没有符合的批次，但是却填了批次
            if (toChildView.getSwineryId() != null) {
                // 转入批次日龄
                Long inSwineryAge = ParamUtil.getSwineryAge(toChildView.getSwineryId(), currentDate);

                // 转出批次日龄与转入到的批次日龄差值
                Long swineryAgeDiff = Math.abs(oldSwineryAge - inSwineryAge);

                // 录入转入数量 >= 设置值 并且 转出批次日龄与转入到的批次日龄差值 >设置值 日龄
                if (toChildView.getChildQty().compareTo(childQtyFromSetting) >= 0 && swineryAgeDiff.compareTo(swineryAgeDiffFromSetting) > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber() + "行,【转入数量>=" + childQtyFromSetting
                            + "】且转出批次日龄：【" + oldSwineryAge + "】转入批次日龄：【" + inSwineryAge + "】，【转出转入批次日龄差值>" + swineryAgeDiffFromSetting
                            + "】,请【合并批次】选【否】！");
                }
            } else {
                // 转入舍中没有批次
                // 创群
                toChildView.setSwineryId(null);
            }
        } else {
            if (PigConstants.SWINERY_MERGE.equals(toChildView.getMergeSwinery())) {
                // 合并批次
                if (swineryAgeDiffList.size() == 1 && createSwinerySet.contains(Maps.getLong(swineryAgeDiffList.get(0), "swineryId"))) {
                    // 如果查询到的合格批次只有一个，并且就是前面记录所创建的群
                    toChildView.setSwineryId(Maps.getLong(swineryAgeDiffList.get(0), "swineryId"));
                } else {
                    if (toChildView.getSwineryId() == null) {
                        // 转入批次为空
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber()
                                + "行，若转入猪舍有批次，请选择批次；若没有批次，【合并批次】请选【否】！");
                    } else {
                        // 转入批次不为空
                        // 转入批次日龄
                        Long inSwineryAge = ParamUtil.getSwineryAge(toChildView.getSwineryId(), currentDate);

                        // 转出批次日龄与转入到的批次日龄差值
                        Long swineryAgeDiff = Math.abs(oldSwineryAge - inSwineryAge);

                        // 录入转入数量 >= 设置值 并且 转出批次日龄与转入到的批次日龄差值 >设置值 日龄
                        if (toChildView.getChildQty().compareTo(childQtyFromSetting) >= 0 && swineryAgeDiff.compareTo(
                                swineryAgeDiffFromSetting) > 0) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber() + "行,【转入数量>="
                                    + childQtyFromSetting + "】且【转出转入批次日龄差值>" + swineryAgeDiffFromSetting + "】,请【合并批次】选【否】！");
                        }
                    }
                }
            } else {
                // 不合并批次
                // 存在批次
                if (toChildView.getChildQty().compareTo(childQtyFromSetting) < 0) {
                    // 录入转入数量 < 设置值
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber() + "行,【转入数量<" + childQtyFromSetting
                            + "】并且【转入猪舍中存在批次】,请【合并批次】选【是】并且选择【转入批次】！");
                } else {
                    // 录入转入数量 >= 设置值
                    // 转出批次日龄与转入舍存在的批次日龄差值
                    Long swineryAgeDiff = Maps.getLong(swineryAgeDiffList.get(0), "swineryAgeDiff");
                    // 转出批次日龄与转入舍存在的批次日龄差值 <= 设置值 日龄
                    if (swineryAgeDiff.compareTo(swineryAgeDiffFromSetting) <= 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + toChildView.getLineNumber() + "行,【转入数量>=" + childQtyFromSetting
                                + "】并且【转入舍中存在批次日龄与转入批次日龄相差<=" + swineryAgeDiffFromSetting + "】,请【合并批次】选【是】并且选择【转入批次】！");
                    } else {
                        // 转出批次日龄与转入舍存在的批次日龄差值 > 设置值 日龄
                        // 创群
                        toChildView.setSwineryId(null);
                    }
                }
            }
        }
    }

    // 商品猪死亡
    @Override
    public void editGoodPigDie(BillModel billModel, String eventName, String childPigDieList) throws Exception {

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
        List<ChildDieView> childDieViews = null;
        if (childPigDieList != null) {
            childDieViews = getJsonList(childPigDieList, ChildDieView.class);
            if (childDieViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }
            for (Iterator<ChildDieView> it = childDieViews.iterator(); it.hasNext();) {
                ChildDieView childDieView = (ChildDieView) it.next();
                if (childDieView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                Date enterDate = childDieView.getEnterDate();
                Date minDate = childDieView.getMinValidDate();
                Date maxDate = childDieView.getMaxValidDate();

                if (PigConstants.HOUSE_CLASS_DELIVERY == Long.valueOf(childDieView.getHouseType()) && childDieView.getPigpenId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第【" + childDieView.getLineNumber() + "】行，选择产房断奶仔猪时，栏位不能为空！");
                }
                if (childDieView.getSwineryId() == null || childDieView.getSwineryId() < 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第【" + childDieView.getLineNumber() + "】行，请选择对应批次！");
                }
                if (childDieView.getLeaveQty() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第【" + childDieView.getLineNumber() + "】行，死亡数量不能为空！");
                }
                if (childDieView.getLeaveQty() <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第【" + childDieView.getLineNumber() + "】行，死亡数量不能小于等于0！");
                }
                if (PigConstants.HOUSE_CLASS_DELIVERY == Long.valueOf(childDieView.getHouseType())) {
                    SqlCon pigCountSql = new SqlCon();
                    pigCountSql.addMainSql("SELECT COUNT(*) pigQty FROM PP_L_PIG WHERE DELETED_FLAG = 0");
                    pigCountSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                    pigCountSql.addCondition(childDieView.getHouseId(), " AND HOUSE_ID = ?");
                    pigCountSql.addCondition(childDieView.getPigpenId(), " AND PIGPEN_ID = ?");
                    pigCountSql.addCondition(PigConstants.PIG_CLASS_PORK_DN, " AND PIG_CLASS = ?");
                    Map<String, String> sqlMap = new HashMap<String, String>();
                    sqlMap.put("sql", pigCountSql.getCondition());

                    List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

                    if (Maps.getLong(list.get(0), "pigQty") < childDieView.getLeaveQty()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第【" + childDieView.getLineNumber() + "】行，死亡数量不能大于批次存栏数量！");
                    }
                }

                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, childDieView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), enterDate);

                childDieView.setBillId(billId);
                childDieView.setFarmId(getFarmId());
                childDieView.setCompanyId(getCompanyId());
                childDieView.setEventName(eventName);
                // 判断是否是转产房的断奶小猪
                if (PigConstants.HOUSE_CLASS_DELIVERY == Long.valueOf(childDieView.getHouseType())) {
                    SqlCon childSql = new SqlCon();
                    childSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                    childSql.addCondition(childDieView.getPigpenId(), " AND PIG_CLASS = 14 AND PIGPEN_ID = ?");
                    if (childDieView.getSwineryId() != null) {
                        childSql.addCondition(childDieView.getSwineryId(), " AND SWINERY_ID = ?");
                    }
                    List<PigModel> pigModelList = getList(pigMapper, childSql);
                    Set<Long> sowIds = new HashSet<Long>();
                    for (PigModel pigModel : pigModelList) {
                        sowIds.add(pigModel.getBoardSowId());
                    }
                    if (sowIds.size() > 1) {
                        Thrower.throwException(ProductionException.GOOD_PIG_DIE_HAVE_MULTIPLE_SOW, childDieView.getLineNumber(), CacheUtil.getName(
                                String.valueOf(childDieView.getPigpenId()), NameEnum.PIGPEN_NAME));
                    }
                    childDieView.setPigId(pigModelList.get(0).getBoardSowId());
                    childDieView.setEventName("CHILD_PIG_DIE");
                    childDieView.setFlag("N");
                }
                childDieView.setCreateId(getEmployeeId());
                pigEventMapper.childPigDie(childDieView);
                if (!"0".equals(childDieView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
                }
            }

            // 插入肉猪的PigEventRelates
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_LEAVE WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }

        }
    }

    // 商品猪销售
    @Override
    public void editGoodPigSell(Map<String, Object> inputMap, String sellList) throws Exception {
        BillModel billModel = getBean(BillModel.class, inputMap);
        PigSaleModel pigSaleModel = getBean(PigSaleModel.class, inputMap);
        long customerId = Maps.getLong(inputMap, "customerId");
        String leaveReason = Maps.getString(inputMap, "leaveReason");
        String eventName = Maps.getString(inputMap, "eventName");
        Date createDate = new Date();

        // 单据日期不能为空
        if (billModel.getBillDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }
        if (sellList == null) {
            return;
        }

        List<Map> saleList = getJsonList(sellList, Map.class);
        if (saleList == null || saleList.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
        }

        // 判断是否需要结算
        boolean isAccount = saleBillIsAccount(customerId);
        if (isAccount) {
            pigSaleModel.setSaleStatus(AccountConstants.SALE_STATUS_IS_ACCOUNT_FALSE);
        } else {
            pigSaleModel.setSaleStatus(AccountConstants.SALE_STATUS_NOT_ACCOUNT);
        }

        // START HANA
        // 获取hana销售类型
        String sapSaleType = HanaUtil.getSapSaleType(getFarmId(), customerId);
        // END HANA

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

        pigSaleModel.setBillId(billModel.getRowId());
        pigSaleModel.setCompanyId(getCompanyId());
        pigSaleModel.setCreateDate(createDate);
        pigSaleModel.setCreateId(getEmployeeId());
        pigSaleModel.setFarmId(getFarmId());
        pigSaleModel.setCustomerId(customerId);
        pigSaleModel.setSaleType(leaveReason);
        pigSaleModel.setSapSaleType(sapSaleType);
        pigSaleMapper.insert(pigSaleModel);

        long billId = billModel.getRowId();

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        // 调拨和内销:false,非调拨true
        boolean isSale = HanaUtil.isSaleToHana(sapSaleType);
        // 判断是否要传入HANA
        boolean isSaleToHana = isSaleToHana(isSale, isAccount, isToHana);

        HanaSaleBill hanaSaleBill = null;
        List<HanaSaleBillDetail> hanaSaleBillDetailList = null;
        HanaPigProduct hanaPigProduct = null;
        List<HanaPigProductDetail> hanaPigProductDetailList = null;
        String mtcBranchID = null;
        String mtcDeptID = null;
        String saleType = null;

        Map<String, Object> pigSaleMap = pigSaleModel.getData();
        if (isSaleToHana) {
            saleType = HanaUtil.getSaleType(sapSaleType);
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            String cardCode = HanaUtil.getMTC_SaleCardCode(customerId);

            // 商品猪
            hanaSaleBill = new HanaSaleBill();
            // 分公司编码
            hanaSaleBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaSaleBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(billId) + "-" + businessCode);
            // 业务伙伴编号
            hanaSaleBill.setMTC_CardCode(cardCode);
            // 出库日期
            hanaSaleBill.setMTC_DocDate(billModel.getBillDate());
            // 表行
            hanaSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();

            hanaSaleBill.setDetailList(hanaSaleBillDetailList);

            // 生产猪
            hanaPigProduct = new HanaPigProduct();
            // 分公司编码
            hanaPigProduct.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaPigProduct.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(billId) + "-" + businessCode);
            // 业务伙伴编号
            hanaPigProduct.setMTC_CardCode(cardCode);
            // 出库日期
            hanaPigProduct.setMTC_DocDate(billModel.getBillDate());
            // 表行
            hanaPigProductDetailList = new ArrayList<HanaPigProductDetail>();

            hanaPigProduct.setDetailList(hanaPigProductDetailList);

        }

        // 商品猪头数
        Long goodNumber = 0L;
        // 生产猪头数
        Long scNumber = 0L;
        // END HANA

        // 判断日期是否满足条件,满足则插入销售明细表
        for (Map map : saleList) {
            if (StringUtil.isBlank(Maps.getString(map, "saleDescribe"))) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售品名不能为空！");
            }
            // 肉猪的批次不能为空
            if (checkPorkSwineryIdIsNull(map)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "批次不能为空！");
            }
            if (StringUtil.isBlank(Maps.getString(map, "saleDate"))) {
                Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
            }
            if (TimeUtil.compareDate(TimeUtil.getSysDate(), Maps.getString(map, "saleDate"), Calendar.DATE) < 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售时间必须小于当前时间！");
            }
            Date enterDate = null;
            if (map.get("minValidDate") != null) {
                enterDate = Maps.getDate(map, "saleDate");
                Date minDate = Maps.getDate(map, "minValidDate");
                Date maxDate = Maps.getDate(map, "maxValidDate");
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, map.get("lineNumber"), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }
            }
            if (PigConstants.SALE_BILL_TYPE_OUTSIDE.equals(Maps.getString(inputMap, "saleBillType"))) {
                if (Maps.getDoubleClass(map, "totalWeight") == null || Maps.getDouble(map, "totalWeight") == 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "总重不能为空！");
                }
            }

            sapEventMasage(getFarmId(), Maps.getDate(map, "saleDate"));

            PigSaleDetailModel pigSaleDetailModel = new PigSaleDetailModel();
            pigSaleDetailModel.setBaseUnitPrice(Maps.getDoubleClass(map, "baseUnitPrice"));
            pigSaleDetailModel.setBaseWeight(Maps.getDoubleClass(map, "baseWeight"));
            pigSaleDetailModel.setLineNumber(Maps.getLongClass(map, "lineNumber"));
            pigSaleDetailModel.setBillId(billId);
            pigSaleDetailModel.setCompanyId(getCompanyId());
            pigSaleDetailModel.setCreateDate(createDate);
            pigSaleDetailModel.setCreateId(getEmployeeId());
            pigSaleDetailModel.setDeletedFlag("0");
            pigSaleDetailModel.setFarmId(getFarmId());
            pigSaleDetailModel.setHouseId(Maps.getLongClass(map, "houseId"));
            pigSaleDetailModel.setNotes(Maps.getString(map, "notes"));
            pigSaleDetailModel.setOverPrice(Maps.getDoubleClass(map, "overPrice"));
            pigSaleDetailModel.setOverUnitPrice(Maps.getDoubleClass(map, "overUnitPrice"));
            pigSaleDetailModel.setOverWeight(Maps.getDoubleClass(map, "overWeight"));
            pigSaleDetailModel.setPigpenId(Maps.getLongClass(map, "pigpenId"));
            pigSaleDetailModel.setPreUnitPrice(Maps.getDoubleClass(map, "preUnitPrice"));
            pigSaleDetailModel.setSaleDate(Maps.getDate(map, "saleDate"));
            pigSaleDetailModel.setSaleDescribe(Maps.getString(map, "saleDescribe"));
            pigSaleDetailModel.setSaleNum(Maps.getLongClass(map, "saleNum"));
            pigSaleDetailModel.setStatus("1");
            pigSaleDetailModel.setSwineryId(Maps.getLongClass(map, "swineryId"));
            pigSaleDetailModel.setTotalPrice(Maps.getDoubleClass(map, "totalPrice"));
            pigSaleDetailModel.setTotalUnitPrice(Maps.getDoubleClass(map, "totalUnitPrice"));
            pigSaleDetailModel.setTotalWeight(Maps.getDoubleClass(map, "totalWeight"));
            pigSaleDetailModel.setUnitPricePrice(Maps.getDoubleClass(map, "unitPricePrice"));
            pigSaleDetailModel.setWorker(Maps.getLongClass(map, "worker"));
            /*************************** 2016-12-22 yangy 添加品种性别 ************************/
            pigSaleDetailModel.setBreedId(Maps.getLongClass(map, "breedId"));
            pigSaleDetailModel.setSex(Maps.getLongClass(map, "sex"));
            /*************************** 2016-12-22 yangy 添加品种性别 ************************/
            pigSaleDetailModel.setSapSaleType(sapSaleType);
            pigSaleDetailMapper.insert(pigSaleDetailModel);

            LeaveModel leaveModel = new LeaveModel();
            String ids = Maps.getString(map, "pigIds");
            String obsoletes = Maps.getString(map, "obsoleteReasons");

            // START HANA
            // 商品猪
            if (isSaleToHana) {
                if (!PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(Maps.getString(map, "saleDescribe")) && !String.valueOf(
                        PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG).equals(Maps.getString(map, "saleDescribe"))) {
                    HanaSaleBillDetail hanaSaleBillDetail = this.createHanaSaleBillDetail(pigSaleDetailModel, mtcBranchID, mtcDeptID, billModel
                            .getRowId(), saleType, pigSaleModel.getSalesman(), getFarmId());
                    hanaSaleBillDetailList.add(hanaSaleBillDetail);

                    // 商品猪头数
                    goodNumber = goodNumber + pigSaleDetailModel.getSaleNum();
                } else {
                    // 生产猪头数
                    scNumber = scNumber + pigSaleDetailModel.getSaleNum();
                }
                // END HANA
            }

            // 种猪和肉猪分开做，有id的为种猪
            if (StringUtil.isNonBlank(ids)) {
                SqlCon pigSql = new SqlCon();
                pigSql.addMainSql("SELECT p.HOUSE_ID houseId,p.SWINERY_ID swineryId,p.PIGPEN_ID pigpenId,p.PIG_CLASS pigClass,");
                pigSql.addMainSql(" p.PIG_TYPE pigType,p.SEX sex,p.PARITY parity,c.EAR_BRAND earBrand");
                pigSql.addMainSql(", p.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand,p.BREED_ID breedId");
                pigSql.addMainSql(" FROM pp_l_pig p");
                pigSql.addMainSql(" INNER JOIN pp_l_ear_code c");
                pigSql.addMainSql(" ON p.EAR_CODE_ID = c.ROW_ID and c.DELETED_FLAG = 0");
                pigSql.addMainSql(" WHERE p.deleted_flag = 0");
                pigSql.addConditionWithNull(getFarmId(), " AND p.FARM_ID = ?");
                pigSql.addCondition(ids, " AND p.ROW_ID IN ?", false, true);
                pigSql.addMainSql(" ORDER BY FIELD(p.ROW_ID," + ids + ")");
                Map<String, String> sqlMap = new HashMap<>();
                sqlMap.put("sql", pigSql.getCondition());
                List<Map<String, String>> pigList = paramMapper.getInfos(sqlMap);

                List<String> pigIdList = Arrays.asList(ids.split(","));
                List<String> obsoleteReasons = Arrays.asList(obsoletes.split(","));
                for (int i = 0; i < pigList.size(); i++) {
                    Map<String, String> pigMap = pigList.get(i);

                    // 验证猪只最后一次事件日期是否满足要求
                    checkLastEventDate(Long.valueOf(pigIdList.get(i)), pigSaleDetailModel.getSaleDate());

                    leaveModel.setBillId(billId);
                    leaveModel.setCompanyId(getCompanyId());
                    leaveModel.setCreateDate(createDate);
                    leaveModel.setCreateId(getEmployeeId());
                    leaveModel.setCustomerId(customerId);
                    leaveModel.setDeletedFlag("0");
                    leaveModel.setStatus("1");
                    leaveModel.setOriginApp("XN1.0");
                    leaveModel.setOriginFlag("S");
                    leaveModel.setFarmId(getFarmId());
                    leaveModel.setHouseId(Maps.getLongClass(pigMap, "houseId"));
                    leaveModel.setLeaveDate(pigSaleDetailModel.getSaleDate());
                    Double totalPrice = pigSaleDetailModel.getTotalPrice() == null ? 0 : pigSaleDetailModel.getTotalPrice();
                    Double totalWeight = pigSaleDetailModel.getTotalWeight() == null ? 0 : pigSaleDetailModel.getTotalWeight();
                    Long saleNum = pigSaleDetailModel.getSaleNum() == null ? 0 : pigSaleDetailModel.getSaleNum();

                    Double leavePrice = BigDecimalUtil.bigDecimalDivide(totalPrice, Double.valueOf(saleNum), 2);
                    Double leaveWeight = BigDecimalUtil.bigDecimalDivide(totalWeight, Double.valueOf(saleNum), 2);
                    int num = pigIdList.size() - 1;
                    if (i != num) {
                        leaveModel.setLeavePrice(leavePrice);
                        leaveModel.setLeaveWeight(leaveWeight);
                    } else {
                        Double price = BigDecimalUtil.bigDecimalMultiply(leavePrice, Double.valueOf(num));
                        Double weight = BigDecimalUtil.bigDecimalMultiply(leaveWeight, Double.valueOf(num));
                        leaveModel.setLeavePrice(BigDecimalUtil.bigDecimalSubtract(totalPrice, price));
                        leaveModel.setLeaveWeight(BigDecimalUtil.bigDecimalSubtract(totalWeight, weight));
                    }

                    /************************* 2016-11-15 yangy 种猪销售不需要淘汰理由 begin ***********************/
                    if (!"2".equals(leaveReason)) {
                        leaveModel.setLeaveReason(obsoleteReasons.get(i));
                    }
                    /************************* 2016-11-15 yangy 种猪销售不需要淘汰理由 end ***********************/
                    leaveModel.setLeaveType("5");
                    leaveModel.setNotes(pigSaleDetailModel.getNotes());
                    leaveModel.setPigClass(Maps.getLongClass(pigMap, "pigClass"));
                    leaveModel.setPigId(Long.valueOf(pigIdList.get(i)));
                    leaveModel.setPigpenId(Maps.getLongClass(pigMap, "pigpenId"));
                    leaveModel.setPigType(Maps.getString(pigMap, "pigType"));
                    leaveModel.setSaleId(pigSaleDetailModel.getRowId());
                    leaveModel.setSex(Maps.getString(map, "sex"));
                    leaveModel.setSwineryId(Maps.getLongClass(pigMap, "swineryId"));
                    leaveModel.setWorker(pigSaleDetailModel.getWorker());
                    leaveModel.setParity(Maps.getLongClass(pigMap, "parity"));
                    leaveModel.setLineNumber(pigSaleDetailModel.getLineNumber());
                    leaveModel.setIsMoveIn("N");
                    leaveModel.setSapSaleType(sapSaleType);
                    leaveMapper.insert(leaveModel);

                    PigModel pigModel = new PigModel();
                    pigModel.setRowId(Long.valueOf(pigIdList.get(i)));
                    pigModel.setPigClass(PigConstants.PIG_CLASS_XS);
                    pigModel.setLastClassDate(pigSaleDetailModel.getSaleDate());
                    pigModel.setLastBillId(billId);
                    pigModel.setLastEventDate(pigSaleDetailModel.getSaleDate());
                    pigModel.setLeaveDate(pigSaleDetailModel.getSaleDate());
                    /********************************** 2016-12-21 yangy 留种猪性别为混合 **************************************************/
                    if (Long.valueOf(PigConstants.SELL_GOOD_SEED).equals(Maps.getLong(map, "saleDescribe"))) {
                        if (PigConstants.PIG_SEX_MIX.equals(Maps.getString(map, "sex"))) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "性别不能为混合");
                        } else {
                            pigModel.setSex(Maps.getString(map, "sex"));
                        }
                    }
                    /********************************** 2016-12-21 yangy 留种猪性别为混合 **************************************************/
                    pigMapper.update(pigModel);

                    // 关闭批次
                    if (pigModel.getSwineryId() != null) {
                        SqlCon swineryCloseCheckSqlCon = new SqlCon();
                        swineryCloseCheckSqlCon.addMainSql("SELECT COUNT(*) AS qty FROM pp_l_pig");
                        swineryCloseCheckSqlCon.addMainSql(" WHERE DELETED_FLAG = 0 AND PIG_CLASS <= 18 AND STATUS = 1");
                        swineryCloseCheckSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                        swineryCloseCheckSqlCon.addCondition(pigModel.getSwineryId(), " AND SWINERY_ID = ?");

                        Map<String, String> swineryCloseCheckSqlMap = new HashMap<String, String>();
                        swineryCloseCheckSqlMap.put("sql", swineryCloseCheckSqlCon.getCondition());

                        Map<String, Object> swineryCloseCheckMap = paramMapper.getObjectInfos(swineryCloseCheckSqlMap).get(0);
                        if (Maps.getLong(swineryCloseCheckMap, "qty") == 0L) {
                            SwineryModel swineryModel = new SwineryModel();
                            swineryModel.setRowId(pigModel.getSwineryId());
                            swineryModel.setStatus(PigConstants.SWINERY_STATUS_CLOSE);
                            swineryModel.setEndDate(Maps.getDate(map, "saleDate"));
                            swineryMapper.update(swineryModel);
                        }
                    }

                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setStatus("1");
                    toworkModel.setDeletedFlag("0");
                    toworkModel.setOriginApp("XN1.0");
                    toworkModel.setOriginFlag("S");
                    toworkModel.setBillId(leaveModel.getBillId());
                    toworkModel.setCompanyId(getCompanyId());
                    toworkModel.setCreateDate(createDate);
                    toworkModel.setCreateId(getEmployeeId());
                    toworkModel.setFarmId(getFarmId());
                    toworkModel.setHouseId(leaveModel.getHouseId());
                    toworkModel.setLineId(leaveModel.getLineId());
                    toworkModel.setLineNumber(leaveModel.getLineNumber());
                    toworkModel.setNotes(leaveModel.getNotes());
                    toworkModel.setParity(leaveModel.getParity());
                    toworkModel.setPigClass(25L);
                    toworkModel.setPigId(Long.valueOf(pigIdList.get(i)));
                    toworkModel.setPigpenId(leaveModel.getPigpenId());
                    toworkModel.setPigType(leaveModel.getPigType());
                    toworkModel.setSex(leaveModel.getSex());
                    toworkModel.setSwineryId(leaveModel.getSwineryId());
                    toworkModel.setToworkDate(leaveModel.getLeaveDate());
                    toworkModel.setWorker(leaveModel.getWorker());
                    ToworkModel mode = this.getChangeClass(getFarmId(), Long.valueOf(pigIdList.get(i)));
                    toworkModel.setChangePigClassId(mode.getChangePigClassId());
                    toworkModel.setSortNbr(mode.getSortNbr());
                    toworkModel.setLineNumber(pigSaleDetailModel.getLineNumber());
                    Long proNo = this.getProNo(Long.valueOf(pigIdList.get(i)), leaveModel.getParity());
                    toworkModel.setProNo(proNo);
                    toworkMapper.insert(toworkModel);

                    ToworkModel toworkUpdate = new ToworkModel();
                    toworkUpdate.setRowId(mode.getChangePigClassId());
                    toworkUpdate.setToworkDateOut(pigSaleDetailModel.getSaleDate());
                    toworkUpdate.setPigClassOut(PigConstants.PIG_CLASS_XS);
                    toworkMapper.update(toworkUpdate);

                    PigEventHisModel pigEventHisModel = new PigEventHisModel();
                    pigEventHisModel.setStatus("1");
                    pigEventHisModel.setDeletedFlag("0");
                    pigEventHisModel.setOriginFlag("S");
                    pigEventHisModel.setOriginApp("XN1.0");
                    pigEventHisModel.setCompanyId(getCompanyId());
                    pigEventHisModel.setFarmId(getFarmId());
                    pigEventHisModel.setBillId(billModel.getRowId());
                    pigEventHisModel.setCreateDate(createDate);
                    pigEventHisModel.setCreateId(getEmployeeId());
                    pigEventHisModel.setEarBrand(pigMap.get("earBrand"));
                    pigEventHisModel.setEnterDate(leaveModel.getLeaveDate());
                    pigEventHisModel.setEventName(eventName);
                    pigEventHisModel.setEventNotes("耳牌号为：" + pigMap.get("earBrand") + "已售出,客户是【" + CacheUtil.getName(String.valueOf(customerId),
                            NameEnum.COMPANY_NAME) + "】");
                    pigEventHisModel.setHouseId(Maps.getLongClass(pigMap, "houseId"));
                    pigEventHisModel.setLastEventDate(leaveModel.getLeaveDate());
                    pigEventHisModel.setNotes(leaveModel.getNotes());
                    pigEventHisModel.setPigClass(25L);
                    pigEventHisModel.setPigId(Long.valueOf(pigIdList.get(i)));
                    pigEventHisModel.setPigpenId(Maps.getLongClass(pigMap, "pigpenId"));
                    pigEventHisModel.setPigType(pigMap.get("pigType"));
                    pigEventHisModel.setSex(pigMap.get("sex"));
                    pigEventHisModel.setSwineryId(Maps.getLongClass(pigMap, "swineryId"));
                    pigEventHisModel.setTableName("PP_L_BILL_LEAVE");
                    pigEventHisModel.setTableRowId(leaveModel.getRowId());
                    pigEventHisModel.setWorker(leaveModel.getWorker());
                    pigEventHisModel.setParity(Maps.getLongClass(pigMap, "parity"));
                    pigEventHisModel.setLineNumber(pigSaleDetailModel.getLineNumber());
                    pigEventHisMapper.insert(pigEventHisModel);

                    // 销售设置状态为4
                    SqlCon saleSql = new SqlCon();
                    saleSql.addMainSql(" UPDATE PP_L_BILL_BOAR_OBSOLETE SET ");
                    saleSql.addCondition(PigConstants.SATAUS_OBSOLETE_SUCCESS_TO_SELL, " STATUS = ? ");
                    saleSql.addMainSql(" WHERE DELETED_FLAG = 0 ");
                    saleSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
                    saleSql.addCondition(PigConstants.SATAUS_OBSOLETE_SUCCESS, " AND STATUS = ? ");
                    saleSql.addCondition(Long.valueOf(pigIdList.get(i)), " AND PIG_ID = ? ");
                    setSql(boarObsoleteMapper, saleSql);
                    // 插入related
                    this.insertPigEventRelates(Long.valueOf(pigIdList.get(i)));

                    // START HANA
                    // 生产猪
                    if (isSaleToHana) {
                        if (PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(Maps.getString(map, "saleDescribe")) || String.valueOf(
                                PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG).equals(Maps.getString(map, "saleDescribe"))) {
                            HanaPigProductDetail hanaPigProductDetail = new HanaPigProductDetail();
                            hanaPigProductDetail.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(billId) + "-" + businessCode);
                            hanaPigProductDetail.setMTC_BaseLine(Maps.getString(map, "lineNumber"));
                            hanaPigProductDetail.setMTC_BatchNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                            hanaPigProductDetail.setMTC_BranchID(mtcBranchID);
                            hanaPigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                            hanaPigProductDetail.setMTC_DeptID(mtcDeptID);
                            // item_code只能根据销售品名去判断，不能根据pigClass判断，因为有淘汰猪
                            hanaPigProductDetail.setMTC_ItemCode(HanaUtil.getMTC_ItemCodeOfSale(Maps.getString(map, "saleDescribe"), getFarmId(), Maps
                                    .getLong(pigMap, "houseId")));
                            hanaPigProductDetail.setMTC_Price(Maps.getString(leaveModel.getData(), "leavePrice"));
                            hanaPigProductDetail.setMTC_Qty("1");
                            hanaPigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PS);
                            hanaPigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                            hanaPigProductDetail.setMTC_SalesAmt(Maps.getString(leaveModel.getData(), "leavePrice"));
                            hanaPigProductDetail.setMTC_Total(Maps.getString(leaveModel.getData(), "leavePrice"));
                            hanaPigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                            hanaPigProductDetailList.add(hanaPigProductDetail);
                        }
                        // END HANA
                    }
                }
            } else {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" SELECT COUNT(*) AS NOTES ");
                sqlCon.addMainSql(" FROM PP_L_PIG T0 WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
                sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16,17,18)");
                sqlCon.addCondition(Maps.getLongClass(map, "houseId"), " AND T0.HOUSE_ID = ? ");
                sqlCon.addCondition(Maps.getLongClass(map, "swineryId"), " AND T0.SWINERY_ID = ? ");
                sqlCon.addCondition(Maps.getString(map, "saleDate"), " AND T0.LAST_CLASS_DATE <=  ? ");
                List<PigModel> list1 = setSql(pigMapper, sqlCon);

                Long pigQty = Long.valueOf(list1.get(0).getNotes());
                if (Maps.getLongClass(map, "saleNum") == null) {
                    map.put("saleNum", 0L);
                }
                if (pigQty < Maps.getLong(map, "saleNum") || Maps.getLongClass(map, "saleNum") == 0L) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLongClass(map, "lineNumber") + "行，在" + Maps.getString(map,
                            "saleDate") + " 天，可销售数量" + pigQty + "头，实际销售数量" + Maps.getLongClass(map, "saleNum") + "头,请修改销售数量或销售日期！");
                }
                /******************************* 2016-12-22 yangy 肉猪销售选择猪只排序 *****************************/
                /* SqlCon sql = new SqlCon();
                 * sql.addMainSql(" SELECT * FROM pp_l_pig T0 WHERE ");
                 * sql.addMainSql("  T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
                 * sql.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
                 * sql.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16,17,18)");
                 * sql.addCondition(Maps.getLongClass(map, "houseId"), " AND HOUSE_ID = ? ");
                 * sql.addCondition(Maps.getLongClass(map, "swineryId"), " AND SWINERY_ID = ? ");
                 * sql.addMainSql(" ORDER BY ROW_ID DESC ");
                 * sql.addCondition(Maps.getLongClass(map, "saleNum"), " LIMIT ? ");
                 * sql.addMainSql(" FOR UPDATE ");
                 * List<PigModel> pigList = setSql(pigMapper, sql); */

                SqlCon sql = new SqlCon();
                sql.addMainSql(" SELECT T1.* FROM pp_l_pig T1 ");
                sql.addMainSql(" LEFT  JOIN( ");
                sql.addMainSql(" SELECT T0.BREED_ID,COUNT(T0.BREED_ID) totalNumByBreed FROM pp_l_pig T0 ");
                sql.addMainSql(" WHERE T0.DELETED_FLAG = 0 AND T0.STATUS = 1 AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN (14, 15, 16, 17, 18) ");
                sql.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
                sql.addCondition(Maps.getLongClass(map, "houseId"), " AND T0.HOUSE_ID = ? ");
                sql.addCondition(Maps.getLongClass(map, "swineryId"), " AND T0.SWINERY_ID = ? ");
                sql.addMainSql(" GROUP BY T0.BREED_ID ");
                sql.addMainSql(" ) T2 ON T1.BREED_ID = T2.BREED_ID ");
                sql.addMainSql(" WHERE T1.DELETED_FLAG = 0 AND T1.STATUS = 1 AND T1.PIG_TYPE = 3 AND T1.PIG_CLASS IN (14, 15, 16, 17, 18) ");
                sql.addConditionWithNull(getFarmId(), " AND T1.FARM_ID =?");
                sql.addCondition(Maps.getLongClass(map, "houseId"), " AND T1.HOUSE_ID = ? ");
                sql.addCondition(Maps.getLongClass(map, "swineryId"), " AND T1.SWINERY_ID = ? ");
                sql.addMainSql(" ORDER BY T1.SEED_FLAG ASC, T1.IS_BATCH_SEED DESC ");
                sql.addCondition(Maps.getLongClass(map, "saleNum"), " LIMIT ? ");
                sql.addMainSql(" FOR UPDATE ");
                List<PigModel> pigList = setSql(pigMapper, sql);
                /******************************* 2016-12-22 yangy 肉猪销售选择猪只排序 *****************************/
                int index = 0;
                for (PigModel pigModel : pigList) {

                    // 验证猪只最后一次事件日期是否满足要求
                    checkLastEventDate(pigModel.getRowId(), pigSaleDetailModel.getSaleDate());

                    leaveModel.setBillId(billId);
                    leaveModel.setCompanyId(getCompanyId());
                    leaveModel.setCreateDate(createDate);
                    leaveModel.setCreateId(getEmployeeId());
                    leaveModel.setCustomerId(customerId);
                    leaveModel.setDeletedFlag("0");
                    leaveModel.setStatus("1");
                    leaveModel.setOriginApp("XN1.0");
                    leaveModel.setOriginFlag("S");
                    leaveModel.setFarmId(getFarmId());
                    leaveModel.setHouseId(pigModel.getHouseId());
                    leaveModel.setLeaveDate(pigSaleDetailModel.getSaleDate());
                    Double totalPrice = pigSaleDetailModel.getTotalPrice() == null ? 0 : pigSaleDetailModel.getTotalPrice();
                    Double totalWeight = pigSaleDetailModel.getTotalWeight() == null ? 0 : pigSaleDetailModel.getTotalWeight();
                    Long saleNum = pigSaleDetailModel.getSaleNum() == null ? 0 : pigSaleDetailModel.getSaleNum();

                    Double leavePrice = BigDecimalUtil.bigDecimalDivide(totalPrice, Double.valueOf(saleNum), 2);
                    Double leaveWeight = BigDecimalUtil.bigDecimalDivide(totalWeight, Double.valueOf(saleNum), 2);
                    int num = pigList.size() - 1;
                    if (index != num) {
                        leaveModel.setLeavePrice(leavePrice);
                        leaveModel.setLeaveWeight(leaveWeight);
                    } else {
                        Double price = BigDecimalUtil.bigDecimalMultiply(leavePrice, Double.valueOf(num));
                        Double weight = BigDecimalUtil.bigDecimalMultiply(leaveWeight, Double.valueOf(num));
                        leaveModel.setLeavePrice(BigDecimalUtil.bigDecimalSubtract(totalPrice, price));
                        leaveModel.setLeaveWeight(BigDecimalUtil.bigDecimalSubtract(totalWeight, weight));
                    }
                    leaveModel.setLeaveType("5");
                    leaveModel.setNotes(pigSaleDetailModel.getNotes());
                    leaveModel.setPigClass(pigModel.getPigClass());
                    leaveModel.setPigId(pigModel.getRowId());
                    leaveModel.setPigpenId(pigModel.getPigpenId());
                    leaveModel.setPigType(pigModel.getPigType());
                    leaveModel.setSaleId(pigSaleDetailModel.getRowId());
                    leaveModel.setSex(pigModel.getSex());
                    leaveModel.setSwineryId(pigModel.getSwineryId());
                    leaveModel.setWorker(pigSaleDetailModel.getWorker());
                    leaveModel.setParity(pigModel.getParity());
                    leaveModel.setLineNumber(pigSaleDetailModel.getLineNumber());
                    leaveModel.setIsMoveIn("N");
                    leaveModel.setSapSaleType(sapSaleType);
                    leaveMapper.insert(leaveModel);

                    PigModel model = new PigModel();
                    model.setRowId(pigModel.getRowId());
                    model.setPigClass(25L);
                    model.setLastClassDate(leaveModel.getLeaveDate());
                    model.setLastBillId(billId);
                    model.setLastEventDate(leaveModel.getLeaveDate());
                    model.setLeaveDate(pigSaleDetailModel.getSaleDate());
                    pigMapper.update(model);

                    // 关闭批次
                    if (pigModel.getSwineryId() != null) {
                        SqlCon swineryCloseCheckSqlCon = new SqlCon();
                        swineryCloseCheckSqlCon.addMainSql("SELECT COUNT(*) AS qty FROM pp_l_pig");
                        swineryCloseCheckSqlCon.addMainSql(" WHERE DELETED_FLAG = 0 AND PIG_CLASS <= 18 AND STATUS = 1");
                        swineryCloseCheckSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                        swineryCloseCheckSqlCon.addCondition(pigModel.getSwineryId(), " AND SWINERY_ID = ?");

                        Map<String, String> swineryCloseCheckSqlMap = new HashMap<String, String>();
                        swineryCloseCheckSqlMap.put("sql", swineryCloseCheckSqlCon.getCondition());

                        Map<String, Object> swineryCloseCheckMap = paramMapper.getObjectInfos(swineryCloseCheckSqlMap).get(0);
                        if (Maps.getLong(swineryCloseCheckMap, "qty") == 0L) {
                            SwineryModel swineryModel = new SwineryModel();
                            swineryModel.setRowId(pigModel.getSwineryId());
                            swineryModel.setStatus(PigConstants.SWINERY_STATUS_CLOSE);
                            swineryModel.setEndDate(Maps.getDate(map, "saleDate"));
                            swineryMapper.update(swineryModel);
                        }
                    }

                    ToworkModel toworkModel = new ToworkModel();
                    toworkModel.setStatus("1");
                    toworkModel.setDeletedFlag("0");
                    toworkModel.setOriginApp("XN1.0");
                    toworkModel.setOriginFlag("S");
                    toworkModel.setBillId(leaveModel.getBillId());
                    toworkModel.setCompanyId(getCompanyId());
                    toworkModel.setCreateDate(createDate);
                    toworkModel.setCreateId(getEmployeeId());
                    toworkModel.setFarmId(getFarmId());
                    toworkModel.setHouseId(leaveModel.getHouseId());
                    toworkModel.setLineId(leaveModel.getLineId());
                    toworkModel.setLineNumber(leaveModel.getLineNumber());
                    toworkModel.setNotes(leaveModel.getNotes());
                    toworkModel.setParity(leaveModel.getParity());
                    toworkModel.setPigClass(25L);
                    toworkModel.setPigId(leaveModel.getPigId());
                    toworkModel.setPigpenId(leaveModel.getPigpenId());
                    toworkModel.setPigType(leaveModel.getPigType());
                    toworkModel.setSex(leaveModel.getSex());
                    toworkModel.setSwineryId(leaveModel.getSwineryId());
                    toworkModel.setToworkDate(leaveModel.getLeaveDate());
                    toworkModel.setWorker(leaveModel.getWorker());
                    ToworkModel mode = this.getChangeClass(getFarmId(), pigModel.getRowId());
                    toworkModel.setChangePigClassId(mode.getChangePigClassId());
                    toworkModel.setSortNbr(mode.getSortNbr());
                    toworkModel.setLineNumber(pigSaleDetailModel.getLineNumber());
                    Long proNo = this.getProNo(leaveModel.getPigId(), leaveModel.getParity());
                    toworkModel.setProNo(proNo);
                    toworkMapper.insert(toworkModel);

                    ToworkModel toworkUpdate = new ToworkModel();
                    toworkUpdate.setRowId(mode.getChangePigClassId());
                    toworkUpdate.setToworkDateOut(pigSaleDetailModel.getSaleDate());
                    toworkUpdate.setPigClassOut(PigConstants.PIG_CLASS_XS);
                    toworkMapper.update(toworkUpdate);

                    PigEventHisModel pigEventHisModel = new PigEventHisModel();
                    EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
                    pigEventHisModel.setStatus("1");
                    pigEventHisModel.setDeletedFlag("0");
                    pigEventHisModel.setOriginFlag("S");
                    pigEventHisModel.setOriginApp("XN1.0");
                    pigEventHisModel.setCompanyId(getCompanyId());
                    pigEventHisModel.setFarmId(getFarmId());
                    pigEventHisModel.setBillId(billId);
                    pigEventHisModel.setCreateDate(createDate);
                    pigEventHisModel.setCreateId(getEmployeeId());
                    pigEventHisModel.setEarBrand(earCodeModel.getEarBrand());
                    pigEventHisModel.setEnterDate(leaveModel.getLeaveDate());
                    pigEventHisModel.setEventName(eventName);
                    pigEventHisModel.setEventNotes("耳牌号为：" + earCodeModel.getEarBrand() + "已售出,客户是【" + CacheUtil.getName(String.valueOf(customerId),
                            NameEnum.COMPANY_NAME) + "】");
                    pigEventHisModel.setHouseId(pigModel.getHouseId());
                    pigEventHisModel.setLastEventDate(leaveModel.getLeaveDate());
                    pigEventHisModel.setNotes(leaveModel.getNotes());
                    pigEventHisModel.setPigClass(25L);
                    pigEventHisModel.setPigId(pigModel.getRowId());
                    pigEventHisModel.setPigpenId(pigModel.getPigpenId());
                    pigEventHisModel.setPigType(pigModel.getPigType());
                    pigEventHisModel.setSex(pigModel.getSex());
                    pigEventHisModel.setSwineryId(pigModel.getSwineryId());
                    pigEventHisModel.setTableName("PP_L_BILL_LEAVE");
                    pigEventHisModel.setTableRowId(leaveModel.getRowId());
                    pigEventHisModel.setWorker(leaveModel.getWorker());
                    pigEventHisModel.setParity(pigModel.getParity());
                    pigEventHisModel.setLineNumber(pigSaleDetailModel.getLineNumber());
                    pigEventHisMapper.insert(pigEventHisModel);

                    this.insertPigEventRelates(pigModel.getRowId());
                    index++;
                }

            }
        }

        // START HANA
        if (isSaleToHana) {
            // 总头数
            Double totalNumber = (double) (goodNumber + scNumber);
            // 总费用
            Double totalOtherFee = Maps.getDouble(pigSaleMap, "otherFee", 0D);
            // 平均费用（保留两位小数）
            Double avgFee = BigDecimalUtil.bigDecimalDivide(totalOtherFee, totalNumber, 2);
            // 生产猪费用 = 平均费用*生产猪头数
            Double scFee = BigDecimalUtil.bigDecimalMultiply(avgFee, (double) scNumber);
            // 商品猪费用 = 总费用 - 生产猪费用
            Double goodFee = BigDecimalUtil.bigDecimalSubtract(totalOtherFee, scFee);

            String paymentDiff = Maps.getString(pigSaleMap, "paymentDiff", "0.0");

            hanaSaleBill.setMTC_Fee(goodFee.toString());
            hanaPigProduct.setMTC_Fee(scFee.toString());

            // 如果有商品猪，把货款差异放入商品猪重
            if (!hanaSaleBillDetailList.isEmpty()) {
                // 商品猪
                hanaSaleBill.setMTC_AmtDiff(paymentDiff);
                // 生产猪
                hanaPigProduct.setMTC_AmtDiff("0.0");
                // 费用，货款差异均摊到每行明细
                setOtherFeeAndDiff2100(hanaSaleBillDetailList, hanaSaleBill.getMTC_Fee(), hanaSaleBill.getMTC_AmtDiff());
                setOtherFeeAndDiff3050(hanaPigProductDetailList, hanaPigProduct.getMTC_Fee(), hanaPigProduct.getMTC_AmtDiff());
            } else {
                // 商品猪
                hanaSaleBill.setMTC_AmtDiff("0.0");
                // 只有生产猪
                hanaPigProduct.setMTC_AmtDiff(paymentDiff);
                // 费用，货款差异均摊到每行明细
                setOtherFeeAndDiff2100(hanaSaleBillDetailList, hanaSaleBill.getMTC_Fee(), hanaSaleBill.getMTC_AmtDiff());
                setOtherFeeAndDiff3050(hanaPigProductDetailList, hanaPigProduct.getMTC_Fee(), hanaPigProduct.getMTC_AmtDiff());
            }
        }
        // END HANA

        // START HANA
        if (isSaleToHana) {
            List<MTC_ITFC> list = new ArrayList<>();

            if (!hanaSaleBillDetailList.isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码:猪只销售(实时)
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2100);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaSaleBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(createDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                list.add(mtcITFC);
            }

            if (!hanaPigProductDetailList.isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码:猪只销售(实时)
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaPigProduct.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(createDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigProduct);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                list.add(mtcITFC);
            }
            // if (HanaUtil.isSaleToAgain(sapSaleType)) {
            // Map<String, String> map = HanaUtil.getMTC_BranchIDAndMTC_DeptID(customerId);
            // String cusBranchID = Maps.getString(map, HanaUtil.MTC_BranchID);
            // String cusmtcDeptID = Maps.getString(map, HanaUtil.MTC_DeptID);
            // hanaSaleBill.setMTC_BranchID(cusBranchID);
            // hanaSaleBill.setMTC_BaseEntry(cusBranchID + "-" + String.valueOf(billId) + "-" + businessCode);
            //
            // mtcBranchID = getPIBranchID(mtcBranchID);
            // hanaSaleBill.setMTC_CardCode(mtcBranchID);
            // for (HanaSaleBillDetail model : hanaSaleBillDetailList) {
            // model.setMTC_BranchID(cusBranchID);
            // model.setMTC_DeptID(cusmtcDeptID);
            // model.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PI);
            // }
            //
            // MTC_ITFC mtcITFCAgain = new MTC_ITFC();
            // // 分公司编号
            // mtcITFCAgain.setMTC_Branch(hanaSaleBill.getMTC_BranchID());
            // // 业务日期
            // mtcITFCAgain.setMTC_DocDate(TimeUtil.parseDate(hanaSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
            // // 业务代码:猪只采购(实时)
            // mtcITFCAgain.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
            // // 新农+单据编号
            // mtcITFCAgain.setMTC_DocNum(hanaSaleBill.getMTC_BaseEntry());
            // // 优先级
            // mtcITFCAgain.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // // 处理区分
            // mtcITFCAgain.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // // 创建日期
            // mtcITFCAgain.setMTC_CreateTime(createDate);
            // // 同步状态
            // mtcITFCAgain.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // // JSON文件
            // String jsonDataFileAgain = HanaJacksonUtil.objectToJackson(hanaSaleBill);
            // mtcITFCAgain.setMTC_DataFile(jsonDataFileAgain);
            // // JSON文件长度
            // mtcITFCAgain.setMTC_DataFileLen(jsonDataFileAgain.length());
            // list.add(mtcITFCAgain);
            //
            // }

            hanaCommonService.insertsMTC_ITFC(list);
        }
        // END HANA
    }

    private void setOtherFeeAndDiff2100(List<HanaSaleBillDetail> list, String otherFee, String payDiff) {
        if (list.isEmpty()) {
            return;
        }
        BigDecimal otherFeeBigDecimal = new BigDecimal(otherFee);
        BigDecimal payDiffBigDecimal = new BigDecimal(payDiff);

        if (otherFeeBigDecimal.doubleValue() == 0D && payDiffBigDecimal.doubleValue() == 0D) {
            return;
        }
        double totalNum = 0;
        double lastNum = 0;
        for (int i = 0; i < list.size(); i++) {
            HanaSaleBillDetail model = list.get(i);
            totalNum += Double.parseDouble(model.getMTC_Qty());
            if (i == list.size() - 1) {
                lastNum = Double.parseDouble(model.getMTC_Qty());
            }
        }

        // 最后一行费用 = otherFee - 平均费用*(totalNum-lastNum)
        double avFee = BigDecimalUtil.bigDecimalDivide(otherFeeBigDecimal.doubleValue(), totalNum, 2);
        double tempFee = BigDecimalUtil.bigDecimalMultiply(avFee, totalNum - lastNum);
        double lastFee = BigDecimalUtil.bigDecimalSubtract(otherFeeBigDecimal.doubleValue(), tempFee);

        BigDecimal amountBigDecimal = null;
        double mtcFee = 0D;
        double tempAmt = 0D;
        double amt = 0D;
        for (int i = 0; i < list.size(); i++) {
            HanaSaleBillDetail detail = list.get(i);
            amountBigDecimal = new BigDecimal(detail.getMTC_SalesAmt());

            if (i == list.size() - 1) {
                detail.setMTC_Fee(String.valueOf(lastFee));
                detail.setMTC_AmtDiff(payDiffBigDecimal.toString());
                tempAmt = BigDecimalUtil.bigDecimalSubtract(amountBigDecimal.doubleValue(), lastFee);
                amt = BigDecimalUtil.bigDecimalAdd(tempAmt, payDiffBigDecimal.doubleValue());
                detail.setMTC_Amount(String.valueOf(amt));
            } else {
                mtcFee = BigDecimalUtil.bigDecimalMultiply(avFee, Double.parseDouble(detail.getMTC_Qty()));
                detail.setMTC_Fee(String.valueOf(mtcFee));
                amt = BigDecimalUtil.bigDecimalSubtract(amountBigDecimal.doubleValue(), mtcFee);
                detail.setMTC_Amount(String.valueOf(amt));
            }
        }
    }

    private void setOtherFeeAndDiff3050(List<HanaPigProductDetail> list, String otherFee, String payDiff) {
        if (list.isEmpty()) {
            return;
        }
        BigDecimal otherFeeBigDecimal = new BigDecimal(otherFee);
        BigDecimal payDiffBigDecimal = new BigDecimal(payDiff);

        if (otherFeeBigDecimal.doubleValue() == 0D && payDiffBigDecimal.doubleValue() == 0D) {
            return;
        }
        double totalNum = 0;
        double lastNum = 0;
        for (int i = 0; i < list.size(); i++) {
            HanaPigProductDetail model = list.get(i);
            totalNum += Double.parseDouble(model.getMTC_Qty());
            if (i == list.size() - 1) {
                lastNum = Double.parseDouble(model.getMTC_Qty());
            }
        }

        // 最后一行费用 = otherFee - 平均费用*(totalNum-lastNum)
        double avFee = BigDecimalUtil.bigDecimalDivide(otherFeeBigDecimal.doubleValue(), totalNum, 2);
        double tempFee = BigDecimalUtil.bigDecimalMultiply(avFee, totalNum - lastNum);
        double lastFee = BigDecimalUtil.bigDecimalSubtract(otherFeeBigDecimal.doubleValue(), tempFee);

        BigDecimal totalBigDecimal = null;
        double mtcFee = 0D;
        double tempAmt = 0D;
        double amt = 0D;
        for (int i = 0; i < list.size(); i++) {
            HanaPigProductDetail detail = list.get(i);
            totalBigDecimal = new BigDecimal(detail.getMTC_SalesAmt());

            if (i == list.size() - 1) {
                detail.setMTC_Fee(String.valueOf(lastFee));
                detail.setMTC_AmtDiff(payDiffBigDecimal.toString());
                tempAmt = BigDecimalUtil.bigDecimalSubtract(totalBigDecimal.doubleValue(), lastFee);
                amt = BigDecimalUtil.bigDecimalAdd(tempAmt, payDiffBigDecimal.doubleValue());
                detail.setMTC_Total(String.valueOf(amt));
            } else {
                mtcFee = BigDecimalUtil.bigDecimalMultiply(avFee, Double.parseDouble(detail.getMTC_Qty()));
                detail.setMTC_Fee(String.valueOf(mtcFee));
                amt = BigDecimalUtil.bigDecimalSubtract(totalBigDecimal.doubleValue(), mtcFee);
                detail.setMTC_Total(String.valueOf(amt));
            }
        }
    }

    private boolean checkPorkSwineryIdIsNull(Map saleMap) {
        // 如果不是空
        if (!Maps.isEmpty(saleMap, "swineryId")) {
            return false;
        }
        if (PigConstants.SELL_GOOD_HOG_PIG.equals(Maps.getString(saleMap, "saleDescribe"))) {
            return true;
        }
        if (PigConstants.SELL_GOOD_PIGGY_PIG.equals(Maps.getString(saleMap, "saleDescribe"))) {
            return true;
        }
        if (PigConstants.SELL_GOOD_IMPERFECT_PIG.equals(Maps.getString(saleMap, "saleDescribe"))) {
            return true;
        }
        return false;
    }

    private void checkLastEventDate(Long pigId, Date saleDate) throws Exception {
        PigModel pigModel = pigMapper.searchById(pigId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastEventDate = sdf.format(pigModel.getLastEventDate());
        if (TimeUtil.compareDate(saleDate, pigModel.getLastEventDate(), Calendar.DATE) < 0) {
            EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "耳号为：【" + earCodeModel.getEarBrand() + "】的猪只最后一次事件日期为:" + lastEventDate
                    + "销售日期必须大于该值！");
        }
    }

    // 创建HANA商品猪销售明细
    private HanaSaleBillDetail createHanaSaleBillDetail(PigSaleDetailModel pigSaleDetailModel, String mtcBranchID, String mtcDeptID,
            Long billModelRowId, String saleType, Long salesmanId, Long supplierId) throws Exception {
        Map<String, Object> pigSaleDetailModelMap = pigSaleDetailModel.getData();

        HanaSaleBillDetail hanaSaleBillDetail = new HanaSaleBillDetail();
        // 分公司编码
        hanaSaleBillDetail.setMTC_BranchID(mtcBranchID);
        // 猪场编码
        hanaSaleBillDetail.setMTC_DeptID(mtcDeptID);
        // 新农+单据编号
        hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(billModelRowId));
        // 新农+单据行号
        hanaSaleBillDetail.setMTC_BaseLine(Maps.getString(pigSaleDetailModelMap, "lineNumber"));
        // 销售类型
        hanaSaleBillDetail.setMTC_SalesType(saleType);
        // 品名
        hanaSaleBillDetail.setMTC_ItemCode(HanaUtil.getMTC_ItemCodeOfSale(pigSaleDetailModel.getSaleDescribe(), supplierId, pigSaleDetailModel
                .getHouseId()));
        // 猪舍编号
        hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(pigSaleDetailModel.getHouseId()));
        // 猪只等级
        hanaSaleBillDetail.setMTC_Grade(HanaUtil.getMTC_Grade(pigSaleDetailModel.getSaleDescribe()));
        // 猪只品种
        hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigSaleDetailModel.getBreedId()));
        // 猪只性别
        hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(String.valueOf(pigSaleDetailModel.getSex())));
        // 销售数量(头数)
        hanaSaleBillDetail.setMTC_Qty(Maps.getString(pigSaleDetailModelMap, "saleNum"));
        // 销售重量
        hanaSaleBillDetail.setMTC_Weight(Maps.getString(pigSaleDetailModelMap, "totalWeight"));
        // 数量单价
        hanaSaleBillDetail.setMTC_QtyPrice(Maps.getString(pigSaleDetailModelMap, "preUnitPrice"));
        // 重量单价
        hanaSaleBillDetail.setMTC_WeiPrice(Maps.getString(pigSaleDetailModelMap, "totalUnitPrice"));
        // 底重
        hanaSaleBillDetail.setMTC_BotWeight(Maps.getString(pigSaleDetailModelMap, "baseWeight"));
        // 底重单价
        hanaSaleBillDetail.setMTC_BotPrice(Maps.getString(pigSaleDetailModelMap, "baseUnitPrice"));
        // 超重
        hanaSaleBillDetail.setMTC_OverWeight(Maps.getString(pigSaleDetailModelMap, "overWeight"));
        // 超重单价
        hanaSaleBillDetail.setMTC_OverPrice(Maps.getString(pigSaleDetailModelMap, "overUnitPrice"));
        // 总金额
        hanaSaleBillDetail.setMTC_SalesAmt(Maps.getString(pigSaleDetailModelMap, "totalPrice"));
        hanaSaleBillDetail.setMTC_Amount(Maps.getString(pigSaleDetailModelMap, "totalPrice"));

        if (salesmanId != null) {
            // 业务员
            EmployeeModel employeeModel = employeeMapper.searchById(salesmanId);
            hanaSaleBillDetail.setMTC_Sales(employeeModel.getRowId() + "." + employeeModel.getEmployeeName());
        }

        return hanaSaleBillDetail;
    }

    // 创建HANA商品猪采购明细
    private List<HanaSaleBillDetail> createHanaPurchaseBillDetail(PigSaleDetailModel pigSaleDetailModel, String mtcBranchID, String mtcDeptID,
            Long billModelRowId, String saleType, Long salesmanId, Long supplierId) throws Exception {
        Map<String, Object> pigSaleDetailModelMap = pigSaleDetailModel.getData();

        SqlCon moveSql = new SqlCon();
        moveSql.addMainSql(" SELECT SUM(ON_PRICE) houseTotalPrice,SUM(ENTER_WEIGHT) houseTotalWeight,COUNT(1) houseNum,");
        moveSql.addMainSql(" ROUND(SUM(ON_PRICE)/SUM(ENTER_WEIGHT),2) houseUnitPrice,HOUSE_ID houseId,PIG_CLASS pigClass");
        moveSql.addMainSql(" FROM pp_l_pig WHERE DELETED_FLAG = 0");
        moveSql.addCondition(getFarmId(), " AND FARM_ID = ?");
        moveSql.addCondition(billModelRowId, " AND BILL_ID = ?");
        moveSql.addCondition(pigSaleDetailModel.getLineNumber(), " AND LINE_NUMBER = ?");
        moveSql.addMainSql(" GROUP BY HOUSE_ID");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", moveSql.getCondition());
        List<Map<String, String>> moveList = paramMapper.getInfos(sqlMap);

        List<HanaSaleBillDetail> list = new ArrayList<>();
        for (Map<String, String> pigMap : moveList) {
            HanaSaleBillDetail hanaSaleBillDetail = new HanaSaleBillDetail();
            // 分公司编码
            hanaSaleBillDetail.setMTC_BranchID(mtcBranchID);
            // 猪场编码
            hanaSaleBillDetail.setMTC_DeptID(mtcDeptID);
            // 新农+单据编号
            hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(billModelRowId));
            // 新农+单据行号
            hanaSaleBillDetail.setMTC_BaseLine(Maps.getString(pigSaleDetailModelMap, "lineNumber"));
            // 销售类型
            hanaSaleBillDetail.setMTC_SalesType(saleType);
            // 品名
            hanaSaleBillDetail.setMTC_ItemCode(HanaUtil.getMTC_ItemCodeOfSale(pigSaleDetailModel.getSaleDescribe(), supplierId, pigSaleDetailModel
                    .getHouseId(), Maps.getString(pigMap, "pigClass")));
            // 猪舍编号
            hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
            // 猪只等级
            hanaSaleBillDetail.setMTC_Grade(HanaUtil.getMTC_Grade(pigSaleDetailModel.getSaleDescribe()));
            // 猪只品种
            hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigSaleDetailModel.getBreedId()));
            // 猪只性别
            hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(String.valueOf(pigSaleDetailModel.getSex())));
            // 销售数量(头数)
            hanaSaleBillDetail.setMTC_Qty(Maps.getString(pigMap, "houseNum"));
            // 销售重量
            hanaSaleBillDetail.setMTC_Weight(Maps.getString(pigMap, "houseTotalWeight"));
            // 重量单价
            hanaSaleBillDetail.setMTC_WeiPrice(Maps.getString(pigMap, "houseUnitPrice"));
            // 总金额
            hanaSaleBillDetail.setMTC_Amount(Maps.getString(pigMap, "houseTotalPrice"));

            if (salesmanId != null) {
                // 业务员
                EmployeeModel employeeModel = employeeMapper.searchById(salesmanId);
                hanaSaleBillDetail.setMTC_Sales(employeeModel.getRowId() + "." + employeeModel.getEmployeeName());
            }
            list.add(hanaSaleBillDetail);
        }
        return list;
    }

    // 两张单据-内部采购单
    private String getPIBranchID(String mtcBranchID) {
        return mtcBranchID.replaceFirst("C", "S");
    }

    // 判断该客户的销售单是否需要结算
    private boolean saleBillIsAccount(Long customerId) {
        if (customerId.compareTo(getFarmId()) == 0) {
            return false;
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT IFNULL(IS_SALE_ACCOUNT,0) isSaleAccount FROM hr_r_srm");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = 0");
        sqlCon.addCondition(CompanyConstants.CUS_SUP_TYPE_CUS, " AND CUSSUP_TYPE = ?");
        sqlCon.addCondition(customerId, " AND CUSSUP_ID = ?");
        sqlCon.addMainSql(" AND CREATE_TYPE = 1");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

        if (list.isEmpty()) {
            return false;
        }
        if (list.size() > 1) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "存在" + list.size() + "条被同步数据，请联系管理员");
        }
        String isSaleAccount = Maps.getString(list.get(0), "isSaleAccount");
        if ("0".equals(isSaleAccount)) {
            return false;
        }
        if (CompanyConstants.SALE_ACCOUNT_SURE.equals(isSaleAccount)) {
            return true;
        }
        return false;
    }

    private boolean isSaleToHana(boolean isSaleToHana, boolean isAccount, boolean isHana) {
        if (isSaleToHana && !isAccount && isHana) {
            return true;
        }
        return false;
    }

    // 种猪淘汰
    public void editObsolete(BillModel billModel, String eventName, String leaveList) throws Exception {

        // String eventName = "LEAVE";
        // 单据日期不能为空
        if (billModel.getBillDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }

        if (StringUtil.isBlank(leaveList)) {
            return;
        }
        List<LeaveView> list = getJsonList(leaveList, LeaveView.class);

        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setCreateId(getEmployeeId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode(eventName);
        // 创建表单
        billMapper.insert(billModel);
        long billId = billModel.getRowId();

        // leaveViews = getJsonList(leaveList, LeaveView.class);
        if (list.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
        }

        for (Iterator<LeaveView> it = list.iterator(); it.hasNext();) {
            LeaveView leaveView = (LeaveView) it.next();
            Date enterDate = leaveView.getEnterDate();
            Date minDate = leaveView.getMinValidDate();
            Date maxDate = leaveView.getMaxValidDate();

            SqlCon sql = new SqlCon();
            sql.addMainSql("SELECT p.PIG_CLASS pigClass,c.EAR_BRAND earBrand,p.HOUSE_ID houseId,p.PIGPEN_ID pigpenId,p.PIG_TYPE pigType,p.SEX sex,");
            sql.addMainSql(" p.PARITY parity,p.SWINERY_ID swineryId");
            sql.addMainSql(" FROM pp_l_pig p");
            sql.addMainSql(" INNER JOIN pp_l_ear_code c");
            sql.addMainSql(" on p.ear_code_id = c.row_id and c.deleted_flag = 0");
            sql.addMainSql(" where p.deleted_flag = 0");
            sql.addCondition(getFarmId(), " and p.farm_id = ?");
            sql.addCondition(leaveView.getPigId(), " and p.row_id = ?");

            Map<String, String> sqlMap = new HashMap<>();
            sqlMap.put("sql", sql.getCondition());
            Map<String, Object> map = paramMapper.getObjectInfos(sqlMap).get(0);

            if (10L == Maps.getLong(map, "pigClass", 10L)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "当前选中的猪处于哺乳状态，不能淘汰！");
            }
            if (leaveView.getEnterDate() == null) {
                Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
            }

            if (StringUtils.isBlank(leaveView.getLeaveReason())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "淘汰原因不能为空！");
            }
            if (leaveView.getWorker() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
            }
            if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, leaveView.getLineNumber(), TimeUtil.format(enterDate,
                        TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
            }

            BoarObsoleteModel boarModel = new BoarObsoleteModel();
            boarModel.setStatus("1");
            boarModel.setDeletedFlag("0");
            // boarModel.setOriginFlag("S");
            // boarModel.setOriginApp("XN1.0");
            boarModel.setPigId(leaveView.getPigId());
            boarModel.setBillId(billId);
            boarModel.setFarmId(getFarmId());
            boarModel.setCompanyId(getCompanyId());
            boarModel.setPigId(leaveView.getPigId());
            boarModel.setCreateDate(billModel.getBillDate());
            boarModel.setObsoleteDate(leaveView.getEnterDate());
            boarModel.setObsoleteReason(leaveView.getLeaveReason());
            boarModel.setWorker(leaveView.getWorker());
            boarModel.setNotes(leaveView.getNotes());
            boarModel.setCreateId(getEmployeeId());
            boarModel.setLineNumber(leaveView.getLineNumber());
            boarObsoleteMapper.insert(boarModel);

            PigEventHisModel pigEventHisModel = new PigEventHisModel();
            pigEventHisModel.setStatus("1");
            pigEventHisModel.setDeletedFlag("0");
            pigEventHisModel.setOriginFlag("S");
            pigEventHisModel.setOriginApp("XN1.0");
            pigEventHisModel.setCompanyId(getCompanyId());
            pigEventHisModel.setFarmId(getFarmId());
            pigEventHisModel.setBillId(billId);
            pigEventHisModel.setCreateDate(new Date());
            pigEventHisModel.setCreateId(getEmployeeId());
            pigEventHisModel.setEarBrand(Maps.getString(map, "earBrand"));
            pigEventHisModel.setEnterDate(leaveView.getEnterDate());
            pigEventHisModel.setEventName(eventName);
            pigEventHisModel.setEventNotes("耳牌号为:" + Maps.getString(map, "earBrand") + "申请淘汰");
            pigEventHisModel.setHouseId(Maps.getLongClass(map, "houseId"));
            pigEventHisModel.setLastEventDate(leaveView.getEnterDate());
            pigEventHisModel.setNotes(boarModel.getNotes());
            pigEventHisModel.setPigClass(Maps.getLongClass(map, "pigClass"));
            pigEventHisModel.setPigId(boarModel.getPigId());
            pigEventHisModel.setPigpenId(Maps.getLongClass(map, "pigpenId"));
            pigEventHisModel.setPigType(Maps.getString(map, "pigType"));
            pigEventHisModel.setSex(Maps.getString(map, "sex"));
            pigEventHisModel.setParity(Maps.getLongClass(map, "parity"));
            pigEventHisModel.setSwineryId(Maps.getLongClass(map, "swineryId"));
            pigEventHisModel.setTableName("PP_L_BILL_BOAR_OBSOLETE");
            pigEventHisModel.setTableRowId(boarModel.getRowId());
            pigEventHisModel.setWorker(boarModel.getWorker());
            pigEventHisModel.setLineNumber(boarModel.getLineNumber());
            pigEventHisMapper.insert(pigEventHisModel);

            PigModel model = new PigModel();
            model.setRowId(Long.valueOf(boarModel.getPigId()));
            model.setLastBillId(billId);
            model.setLastEventDate(boarModel.getObsoleteDate());
            pigMapper.update(model);
            this.insertPigEventRelates(boarModel.getPigId());
        }
    }

    // @Override
    // public void editObsolete(BillModel billModel, String eventName, String leaveList) throws Exception {
    //
    // // String eventName = "LEAVE";
    // // 单据日期不能为空
    // if (billModel.getBillDate() == null) {
    // Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
    // }
    // // 业务编码
    // String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
    // billModel.setCompanyId(getCompanyId());
    // billModel.setFarmId(getFarmId());
    // billModel.setCreateId(getEmployeeId());
    // billModel.setBusinessCode(businessCode);
    // billModel.setEventCode(eventName);
    // // 创建表单
    // billModel.setBillDate(billModel.getBillDate());
    // billMapper.insert(billModel);
    // long billId = billModel.getRowId();
    // List<LeaveView> leaveViews = null;
    // if (leaveList != null) {
    // leaveViews = getJsonList(leaveList, LeaveView.class);
    // if (leaveViews.size() == 0) {
    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "未添加明细不能提交！");
    // }
    // for (Iterator<LeaveView> it = leaveViews.iterator(); it.hasNext();) {
    // LeaveView leaveView = (LeaveView) it.next();
    // Date enterDate = leaveView.getEnterDate();
    // Date minDate = leaveView.getMinValidDate();
    // Date maxDate = leaveView.getMaxValidDate();
    // if (leaveView.getEnterDate() == null) {
    // Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
    // }
    //
    // if (StringUtils.isBlank(leaveView.getLeaveReason())) {
    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "淘汰原因不能为空！");
    // }
    // if (leaveView.getWorker() == null) {
    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
    // }
    // if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
    // Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, leaveView.getLineNumber(), TimeUtil.format(enterDate,
    // TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
    // }
    // leaveView.setBillId(billId);
    // leaveView.setFarmId(getFarmId());
    // leaveView.setCompanyId(getCompanyId());
    // leaveView.setEventName(eventName);
    // leaveView.setCreateId(getEmployeeId());
    // pigEventMapper.leave(leaveView);
    // if (!"0".equals(leaveView.getErrorCode())) {
    // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, leaveView.getErrorMessage());
    // }
    //
    // this.insertPigEventRelates(leaveView.getPigId());
    //
    // }
    // }
    // }

    // 离场
    @Override
    public void editLeave(BillModel billModel, String eventName, String leaveList) throws Exception {

        // String eventName = "LEAVE";
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
        List<LeaveView> leaveViews = null;
        if (leaveList != null) {
            leaveViews = getJsonList(leaveList, LeaveView.class);
            if (leaveViews.size() == 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
            }

            // START HANA
            // SAP生产猪死亡
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            Date currentDate = new Date();
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            String mtcBranchID = null;
            String mtcDeptID = null;
            if (isToHana) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            }
            // END HANA

            for (Iterator<LeaveView> it = leaveViews.iterator(); it.hasNext();) {
                LeaveView leaveView = (LeaveView) it.next();
                Date enterDate = leaveView.getEnterDate();
                Date minDate = leaveView.getMinValidDate();
                Date maxDate = leaveView.getMaxValidDate();
                if (leaveView.getEnterDate() == null) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
                }
                if (leaveView.getLeaveWeight() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死亡体重不能为空！");
                }
                if (StringUtils.isBlank(leaveView.getLeaveReason())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "死亡原因不能为空！");
                }
                if (leaveView.getWorker() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "技术员不能为空！");
                }
                if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                    Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, leaveView.getLineNumber(), TimeUtil.format(enterDate,
                            TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
                }

                sapEventMasage(getFarmId(), enterDate);

                // 若存在审批中淘汰单，则自动退回
                editObsoleteDefeatAuto(leaveView.getPigId(), leaveView.getEnterDate(), billId);

                leaveView.setBillId(billId);
                leaveView.setFarmId(getFarmId());
                leaveView.setCompanyId(getCompanyId());
                leaveView.setEventName(eventName);
                leaveView.setCreateId(getEmployeeId());

                // START HANA
                if (isToHana) {
                    PigModel pigModel = pigMapper.searchById(leaveView.getPigId());
                    // 是否是生产猪
                    if (HanaUtil.isProductPig(pigModel.getPigClass())) {
                        // 死亡时为生产猪状态
                        // 生产猪死亡
                        MTC_ITFC mtcITFC = sendToSapChangeProductFor3040(mtcBranchID, mtcDeptID, currentDate, enterDate, pigModel, billModel,
                                leaveView.getLineNumber());
                        mtcITFCList.add(mtcITFC);

                    } else if (PigConstants.PIG_CLASS_TT == pigModel.getPigClass()) {
                        // 此逻辑不会运行
                        // 目前新农+系统 淘汰猪不允许直接死亡，必须撤销淘汰再死亡
                        // 死亡时为淘汰猪状态,寻找前一状态是否为生产猪
                        SqlCon sqlCon = new SqlCon();
                        sqlCon.addMainSql("SELECT PIG_CLASS AS pigClass FROM PP_L_BILL_TOWORK WHERE DELETED_FLAG = '0'");
                        sqlCon.addCondition(pigModel.getRowId(), " AND PIG_ID = ?");
                        sqlCon.addCondition(PigConstants.PIG_CLASS_TT, " AND PIG_CLASS <> ?");
                        sqlCon.addCondition(PigConstants.PIG_CLASS_TT, " AND PIG_CLASS_OUT = ?");
                        sqlCon.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1");

                        ToworkModel toworkModel = setSql(toworkMapper, sqlCon).get(0);
                        // 淘汰前的状态是否是生产猪
                        if (HanaUtil.isProductPig(toworkModel.getPigClass())) {
                            MTC_ITFC mtcITFC = sendToSapChangeProductFor3040(mtcBranchID, mtcDeptID, currentDate, enterDate, pigModel, billModel,
                                    leaveView.getLineNumber());
                            mtcITFCList.add(mtcITFC);
                        }
                    }
                }
                // END HANA

                if (PigConstants.PIG_TYPE_SOW.equals(leaveView.getPigType()) && Long.valueOf(PigConstants.PIG_CLASS_BR).equals(leaveView
                        .getPigClass())) {
                    if (leaveView.getWeanWeight() == null || leaveView.getWeanWeight() < 0) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "断奶窝重不能为空或小于0！");
                    }
                    WeanView weanView = new WeanView();
                    weanView.setLineNumber(leaveView.getLineNumber());
                    weanView.setPigType(leaveView.getPigType());
                    weanView.setPigId(leaveView.getPigId());
                    weanView.setEnterDate(leaveView.getEnterDate());
                    weanView.setEventName(EventConstants.WEAN);
                    weanView.setWorker(leaveView.getWorker());
                    weanView.setBillId(billId);
                    weanView.setCreateId(getEmployeeId());
                    weanView.setMinValidDate(leaveView.getMinValidDate());
                    weanView.setMaxValidDate(leaveView.getMaxValidDate());
                    weanView.setWeanNum(leaveView.getPigQty());
                    weanView.setPigQty(leaveView.getPigQty());
                    weanView.setDieNum(0L);
                    weanView.setSysInput("2");
                    weanView.setInHouseId(leaveView.getHouseId());
                    weanView.setInPigpenId(leaveView.getPigpenId());
                    weanView.setWeanWeight(leaveView.getWeanWeight());
                    weanView.setFlag("Y");
                    pigEventMapper.wean(weanView);
                }
                pigEventMapper.leave(leaveView);
                if (!"0".equals(leaveView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, leaveView.getErrorMessage());
                }

                this.insertPigEventRelates(leaveView.getPigId());

            }
            // 批量插入整个单据的猪仔信息
            SqlCon getChildPigIdSqlCon = new SqlCon();
            getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_WEAN_DETAIL WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
            List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
            if (pigIdList != null && !pigIdList.isEmpty()) {
                for (PigModel pigModel : pigIdList) {
                    this.insertPigEventRelates(pigModel.getRowId());
                }
            }

            if (!mtcITFCList.isEmpty()) {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
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

    // 种猪历史导入
    public void editInputPig() throws Exception {

        // 查询一头猪只
        List<PpInputPigModel> ppInputPigModelList = new ArrayList<PpInputPigModel>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT EAR_BRAND earBrand FROM pp_input_pig WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
        sqlCon.addMainSql(" ORDER BY PIG_TYPE LIMIT 1 ");
        String earBrand = "";
        if (!ppInputPigModelList.isEmpty()) {
            earBrand = ppInputPigModelList.get(0).getEarBrand();
        }
        if (StringUtil.isNonBlank(earBrand)) {
            // 入场
            List<PpInputPigModel> ppInputPigModels = new ArrayList<>();
            sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?");
            sqlCon.addConditionWithNull(earBrand, "  AND EAR_BRAND =  ?");
            ppInputPigModels = getList(ppInputPigMapper, sqlCon);
            PpInputPigModel ppInputPigModel = ppInputPigModels.get(0);

            PigMoveInView pigMoveInModel = new PigMoveInView();
            String eventName = "";
            eventName = "PIG_MOVE_IN";
            // 业务编码
            String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
            BillModel billModel = new BillModel();
            billModel.setCompanyId(getCompanyId());
            billModel.setFarmId(getFarmId());
            billModel.setCreateId(getEmployeeId());
            billModel.setBusinessCode(businessCode);
            billModel.setEventCode(eventName);
            billModel.setStatus("1");
            billModel.setDeletedFlag("0");
            // 创建表单
            billModel.setBillDate(TimeUtil.getSysTimestamp());
            billMapper.insert(billModel);

            sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(ppInputPigModel.getMaterialId(), "  AND MATERIAL_ID = ?");
            if ("1".equals(ppInputPigModel.getPigType())) {
                BoarModel boarModel = getModel(boarMapper, sqlCon);
                pigMoveInModel.setBreedId(boarModel.getBreedId());
                pigMoveInModel.setStrain(boarModel.getStrain());
                pigMoveInModel.setBirthWeight(boarModel.getBirthWeight());
                pigMoveInModel.setBodyCondition(boarModel.getBodyCondition());
                pigMoveInModel.setLeftTeatNum(boarModel.getLeftTeatNum());
                pigMoveInModel.setRightTeatNum(boarModel.getRightTeatNum());
                pigMoveInModel.setPigClass(1l);
                pigMoveInModel.setParity(0l);
                pigMoveInModel.setSex("1");
            } else if ("2".equals(ppInputPigModel.getPigType())) {
                SowModel sowModel = getModel(sowMapper, sqlCon);
                pigMoveInModel.setBreedId(sowModel.getBreedId());
                pigMoveInModel.setStrain(sowModel.getStrain());
                pigMoveInModel.setBirthWeight(sowModel.getBirthWeight());
                pigMoveInModel.setBodyCondition(sowModel.getBodyCondition());
                pigMoveInModel.setLeftTeatNum(sowModel.getLeftTeatNum());
                pigMoveInModel.setRightTeatNum(sowModel.getRightTeatNum());
                if (ppInputPigModel.getParity() > 0) {
                    pigMoveInModel.setParity(ppInputPigModel.getParity() - 1);
                } else {
                    pigMoveInModel.setParity(ppInputPigModel.getParity());
                }
                if (ppInputPigModel.getParity() == 0) {
                    pigMoveInModel.setPigClass(3l);
                } else {
                    pigMoveInModel.setPigClass(11l);
                }
                pigMoveInModel.setSex("2");
            } else {
                PorkPigModel porkPigModel = getModel(porkPigMapper, sqlCon);
                pigMoveInModel.setBreedId(porkPigModel.getBreedId());
                pigMoveInModel.setStrain(porkPigModel.getStrain());
                pigMoveInModel.setBirthWeight(porkPigModel.getBirthWeight());
                pigMoveInModel.setBodyCondition(porkPigModel.getBodyCondition());
                pigMoveInModel.setLeftTeatNum(porkPigModel.getLeftTeatNum());
                pigMoveInModel.setRightTeatNum(porkPigModel.getRightTeatNum());
                pigMoveInModel.setPigClass(ppInputPigModel.getPigClass());
                pigMoveInModel.setSex("3");
            }
            pigMoveInModel.setOptType("1");
            pigMoveInModel.setEarBrand(ppInputPigModel.getEarBrand());
            pigMoveInModel.setEarShort(ppInputPigModel.getEarShort());
            pigMoveInModel.setEarThorn(ppInputPigModel.getEarThorn());
            pigMoveInModel.setLineNumber(ppInputPigModel.getSortNbr());
            pigMoveInModel.setPigType(ppInputPigModel.getPigType());
            pigMoveInModel.setMaterialId(ppInputPigModel.getMaterialId());
            pigMoveInModel.setCompanyId(getCompanyId());
            pigMoveInModel.setFarmId(getFarmId());
            pigMoveInModel.setBirthParity(0l);
            pigMoveInModel.setHouseNum(0l);
            pigMoveInModel.setHouseId(ppInputPigModel.getHouseId());
            pigMoveInModel.setBillId(billModel.getRowId());

            pigMoveInModel.setEnterDate(ppInputPigModel.getEnterDate());
            pigMoveInModel.setBirthDate(ppInputPigModel.getBirthDate());
            pigMoveInModel.setCreateId(getEmployeeId());
            pigMoveInModel.setWorker(getEmployeeId());
            pigMoveInModel.setEventName(eventName);
            pigEventMapper.pigMoveIn(pigMoveInModel);
            if (!"0".equals(pigMoveInModel.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
            }

            // 根据耳牌号更新PIG_ID
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" UPDATE pp_input_pig t0,pp_v_pig t1 SET T0.PIG_ID = T1.PIG_ID");
            sqlCon.addMainSql(" WHERE T0.FARM_ID = T1.FARM_ID AND T0.EAR_BRAND = T1.EAR_BRAND AND T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
            sqlCon.addConditionWithNull(getFarmId(), "AND T0.FARM_ID = ?");
            sqlCon.addConditionWithNull(earBrand, "AND T0.EAR_BRAND = ?");
            setSql(ppInputPigMapper, sqlCon);

            // 转生产公猪
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" AND PIG_TYPE = 1 AND PIG_CLASS = 2 ");
            sqlCon.addConditionWithNull(getFarmId(), "AND FARM_ID = ?");
            sqlCon.addConditionWithNull(earBrand, "AND EAR_BRAND = ?");
            sqlCon.addMainSql("   LIMIT 1");
            if (!ppInputPigModelList.isEmpty()) {
                ReserveToProductView boarReserveToProductView = new ReserveToProductView();
                eventName = "BOAR_RESERVE_TO_PRODUCT";
                // 业务编码
                businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                // BillModel billModel = new BillModel();
                billModel.setCompanyId(getCompanyId());
                billModel.setFarmId(getFarmId());
                billModel.setCreateId(getEmployeeId());
                billModel.setBusinessCode(businessCode);
                billModel.setEventCode(eventName);
                billModel.setStatus("1");
                billModel.setDeletedFlag("0");
                // 创建表单
                billModel.setBillDate(TimeUtil.getSysTimestamp());
                billMapper.insert(billModel);

                boarReserveToProductView.setPigId(ppInputPigModelList.get(0).getPigId());
                boarReserveToProductView.setBillId(billModel.getRowId());
                boarReserveToProductView.setFarmId(getFarmId());
                boarReserveToProductView.setCompanyId(getCompanyId());
                boarReserveToProductView.setCreateId(getEmployeeId());
                boarReserveToProductView.setEventName(eventName);
                boarReserveToProductView.setLineNumber(ppInputPigModelList.get(0).getSortNbr());
                boarReserveToProductView.setEnterDate(TimeUtil.getSysTimestamp());
                pigEventMapper.reserveToProduct(boarReserveToProductView);
                if (!"0".equals(boarReserveToProductView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, boarReserveToProductView.getErrorMessage());
                }

                sqlCon = new SqlCon();
                sqlCon.addMainSql(" UPDATE pp_input_pig t0 SET T0.STATUS = 2 ");
                sqlCon.addConditionWithNull(ppInputPigModel.getRowId(), " WHERE T0.ROW_ID =  ?");
                setSql(ppInputPigMapper, sqlCon);
            }
            // 母猪事件录入
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" AND PIG_TYPE = 2 AND PIG_CLASS > 3 ");
            sqlCon.addConditionWithNull(getFarmId(), "  AND FARM_ID= ?");
            sqlCon.addConditionWithNull(earBrand, "  AND EAR_BRAND =  ?");
            sqlCon.addMainSql("  ORDER BY PARITY  ");
            ppInputPigModelList = getList(ppInputPigMapper, sqlCon);
            for (Iterator<PpInputPigModel> it = ppInputPigModelList.iterator(); it.hasNext();) {
                PpInputPigModel ppInputPig = (PpInputPigModel) it.next();
                if (ppInputPig.getBreedDate() != null) {
                    // 配种
                    eventName = "BREED";
                    // 业务编码
                    businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                    billModel.setCompanyId(getCompanyId());
                    billModel.setFarmId(getFarmId());
                    billModel.setCreateId(getEmployeeId());
                    billModel.setBusinessCode(businessCode);
                    billModel.setEventCode(eventName);
                    billModel.setStatus("1");
                    billModel.setDeletedFlag("0");
                    // 创建表单
                    billModel.setBillDate(TimeUtil.getSysTimestamp());
                    billMapper.insert(billModel);
                    BreedView breedView = new BreedView();
                    breedView.setBillId(billModel.getRowId());
                    breedView.setFarmId(getFarmId());
                    breedView.setCompanyId(getCompanyId());
                    breedView.setCreateId(getEmployeeId());
                    breedView.setBreedBoarFirst(ppInputPig.getBreedBoar());
                    breedView.setBreedType("2");
                    breedView.setEnterDate(ppInputPig.getBreedDate());
                    breedView.setPigId(ppInputPig.getPigId());
                    breedView.setEventName(eventName);
                    breedView.setLineNumber(ppInputPig.getSortNbr());
                    pigEventMapper.breed(breedView);
                    if (!"0".equals(breedView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, breedView.getErrorMessage());
                    }
                }
                // if (ppInputPig.getPregnancyDate() != null) {
                // // 妊娠检查
                // eventName = "PREGNANCY_CHECK";
                // // 业务编码
                // businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                // billModel.setCompanyId(getCompanyId());
                // billModel.setFarmId(getFarmId());
                // billModel.setCreateId(getEmployeeId());
                // billModel.setBusinessCode(businessCode);
                // billModel.setEventCode(eventName);
                // billModel.setStatus("1");
                // billModel.setDeletedFlag("0");
                // // 创建表单
                // billModel.setBillDate(TimeUtil.getSysTimestamp());
                // billMapper.insert(billModel);
                // ReserveToProductView reserveToProductView = new ReserveToProductView();
                // reserveToProductView.setBillId(billModel.getRowId());
                // reserveToProductView.setFarmId(getFarmId());
                // reserveToProductView.setCompanyId(getCompanyId());
                // reserveToProductView.setCreateId(getEmployeeId());
                // reserveToProductView.setEventName(eventName);
                // pigEventMapper.reserveToProduct(reserveToProductView);
                // if (!"0".equals(reserveToProductView.getErrorCode())) {
                // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, reserveToProductView.getErrorMessage());
                // }
                // }
                if (ppInputPig.getChangeHouseDate() != null) {
                    // 转产房
                    eventName = "CHANGE_LABOR_HOUSE";
                    // 业务编码
                    businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                    billModel.setCompanyId(getCompanyId());
                    billModel.setFarmId(getFarmId());
                    billModel.setCreateId(getEmployeeId());
                    billModel.setBusinessCode(businessCode);
                    billModel.setEventCode(eventName);
                    billModel.setStatus("1");
                    billModel.setDeletedFlag("0");
                    // 创建表单
                    billModel.setBillDate(TimeUtil.getSysTimestamp());
                    billMapper.insert(billModel);

                    ChangeHouseView changeHouseView = new ChangeHouseView();
                    changeHouseView.setBillId(billModel.getRowId());
                    changeHouseView.setFarmId(getFarmId());
                    changeHouseView.setCompanyId(getCompanyId());
                    changeHouseView.setEventName(eventName);
                    changeHouseView.setCreateId(getEmployeeId());
                    changeHouseView.setChangeHouseType("1");
                    changeHouseView.setInHouseId(ppInputPig.getChangeHouseId());
                    changeHouseView.setPigId(ppInputPig.getPigId());
                    changeHouseView.setLineNumber(ppInputPig.getSortNbr());
                    changeHouseView.setEnterDate(ppInputPig.getChangeHouseDate());
                    pigEventMapper.changeHouse(changeHouseView);
                    if (!"0".equals(changeHouseView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                    }
                }
                if (ppInputPig.getDeliveryDate() != null) {
                    // 分娩
                    eventName = "DELIVERY";
                    // 业务编码
                    businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                    billModel.setCompanyId(getCompanyId());
                    billModel.setFarmId(getFarmId());
                    billModel.setCreateId(getEmployeeId());
                    billModel.setBusinessCode(businessCode);
                    billModel.setEventCode(eventName);
                    billModel.setStatus("1");
                    billModel.setDeletedFlag("0");
                    // 创建表单
                    billModel.setBillDate(TimeUtil.getSysTimestamp());
                    billMapper.insert(billModel);

                    DeliveryView deliveryView = new DeliveryView();
                    deliveryView.setBillId(billModel.getRowId());
                    deliveryView.setFarmId(getFarmId());
                    deliveryView.setCompanyId(getCompanyId());
                    deliveryView.setEventName(eventName);
                    deliveryView.setCreateId(getEmployeeId());
                    deliveryView.setEnterDate(ppInputPig.getDeliveryDate());
                    deliveryView.setDeliveryType("1");
                    deliveryView.setHealthyNum(ppInputPig.getHealthyNum());
                    deliveryView.setWeakNum(ppInputPig.getWeakNum());
                    deliveryView.setStillbirthNum(ppInputPig.getStillbirthNum());
                    deliveryView.setMummyNum(ppInputPig.getMummyNum());
                    deliveryView.setPigId(ppInputPig.getPigId());
                    deliveryView.setLineNumber(ppInputPig.getSortNbr());

                    pigEventMapper.delivery(deliveryView);
                    if (!"0".equals(deliveryView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, deliveryView.getErrorMessage());
                    } else if (ppInputPig.getWeanDate() == null) {
                        // 创建仔猪
                        CreatePorkPigView createPorkPigView = new CreatePorkPigView();
                        // 自动生成所有仔猪
                        String actionType = "2";
                        createPorkPigView.setActionType(actionType);
                        createPorkPigView.setLineNumber(deliveryView.getLineNumber());
                        createPorkPigView.setFarmId(getFarmId());

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
                        pigEventMapper.createPorkPig(createPorkPigView);
                        if (!"0".equals(createPorkPigView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, createPorkPigView.getErrorMessage());
                        }
                    }
                }
                if (ppInputPig.getWeanDate() != null) {
                    // 断奶
                    eventName = "WEAN";
                    // 业务编码
                    businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                    billModel.setCompanyId(getCompanyId());
                    billModel.setFarmId(getFarmId());
                    billModel.setCreateId(getEmployeeId());
                    billModel.setBusinessCode(businessCode);
                    billModel.setEventCode(eventName);
                    billModel.setStatus("1");
                    billModel.setDeletedFlag("0");
                    // 创建表单
                    billModel.setBillDate(TimeUtil.getSysTimestamp());
                    billMapper.insert(billModel);
                    WeanView weanView = new WeanView();
                    weanView.setBillId(billModel.getRowId());
                    weanView.setFarmId(getFarmId());
                    weanView.setCompanyId(getCompanyId());
                    weanView.setCreateId(getEmployeeId());
                    weanView.setPigId(ppInputPig.getPigId());
                    weanView.setEnterDate(ppInputPig.getWeanDate());
                    weanView.setWeanNum(ppInputPig.getWeanNum());
                    weanView.setDieNum(ppInputPig.getHealthyNum() - ppInputPig.getWeanNum());
                    weanView.setLineNumber(ppInputPig.getSortNbr());
                    weanView.setSysInput("1"); // 系统导入数据
                    weanView.setEventName(eventName);
                    weanView.setWeanWeight(ppInputPig.getWeanWeight());
                    // 仔猪死亡
                    if (weanView.getDieNum() > 0) {
                        ChildDieView childDieView = new ChildDieView();
                        childDieView.setBillId(billModel.getRowId());
                        childDieView.setFarmId(getFarmId());
                        childDieView.setCompanyId(getCompanyId());
                        childDieView.setEventName("CHILD_PIG_DIE");
                        childDieView.setCreateId(getEmployeeId());
                        childDieView.setPigId(weanView.getPigId());
                        childDieView.setLeaveQty(weanView.getDieNum());
                        childDieView.setEnterDate(weanView.getEnterDate());
                        childDieView.setWorker(getEmployeeId());
                        childDieView.setFlag("Y");
                        pigEventMapper.childPigDie(childDieView);
                        if (!"0".equals(childDieView.getErrorCode())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
                        }
                    }
                    // 断奶
                    pigEventMapper.wean(weanView);
                    if (!"0".equals(weanView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, weanView.getErrorMessage());
                    }
                    // 转舍
                    ChangeHouseView changeHouseView = new ChangeHouseView();
                    changeHouseView.setBillId(billModel.getRowId());
                    changeHouseView.setFarmId(getFarmId());
                    changeHouseView.setCompanyId(getCompanyId());
                    changeHouseView.setEventName("CHANGE_HOUSE");
                    changeHouseView.setCreateId(getEmployeeId());
                    changeHouseView.setPigId(weanView.getPigId());
                    changeHouseView.setChangeHouseType("5");
                    changeHouseView.setInHouseId(ppInputPig.getHouseId());// 转入舍
                    changeHouseView.setEnterDate(weanView.getEnterDate());
                    changeHouseView.setBackfat(weanView.getBackfat());
                    changeHouseView.setScore(weanView.getScore());
                    changeHouseView.setWorker(getEmployeeId());
                    changeHouseView.setLineNumber(ppInputPig.getSortNbr());
                    pigEventMapper.changeHouse(changeHouseView);
                    if (!"0".equals(changeHouseView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                    }
                }
                sqlCon = new SqlCon();
                sqlCon.addMainSql(" UPDATE pp_input_pig t0 SET T0.STATUS = 2");
                sqlCon.addConditionWithNull(ppInputPig.getRowId(), " WHERE T0.ROW_ID =?");
                setSql(ppInputPigMapper, sqlCon);
            }
        }
    }

    // 查询单据明细
    @Override
    public BasePageInfo searchBillToPage(String businessCode, String createName, String billDate, String billDate2, String createDate,
            String createDate2, String eventCode) throws Exception {

        SqlCon condition = new SqlCon();
        condition.addMainSql(" DELETED_FLAG= '0' ");
        condition.addConditionWithNull(getFarmId(), " AND FARM_ID=? ");
        condition.addCondition(eventCode, " AND EVENT_CODE=?");
        condition.addCondition(businessCode, " AND BUSINESS_CODE LIKE ?", true);

        // 录入员
        // if (createName != null && createName != "") {
        // condition.addCondition("%" + createName + "%", "");
        // }

        condition.addCondition(billDate, " AND BILL_DATE >= ?");
        condition.addCondition(billDate2, " AND BILL_DATE <= ?");
        condition.addCondition(createDate, " AND CREATE_DATE >= ?");
        condition.addCondition(createDate2, " AND CREATE_DATE <= ?");

        condition.addMainSql(" ORDER BY BILL_DATE DESC , CREATE_DATE DESC");
        setToPage();
        List<BillModel> list = getAllList(billMapper, condition);
        for (BillModel billModel : list) {
            Map<String, Object> data = billModel.getData();
            data.put("createName", CacheUtil.getName(String.valueOf(billModel.getCreateId()), NameEnum.CREATE_NAME));
            data.put("eventNameChinese", CacheUtil.getName(billModel.getEventCode(), NameEnum.CODE_NAME, CodeEnum.EVENT_CODE));
            if (EventConstants.BILL_CANCEL.equals(billModel.getEventCode()) || EventConstants.PIG_EVENT_CANCEL.equals(billModel.getEventCode())
                    || EventConstants.EACH_RECORD_CANCEL.equals(billModel.getEventCode())) {
                if (billModel.getCanceledBillId() != null) {
                    SqlCon canceledBillIdBillModelSqlCon = new SqlCon();
                    canceledBillIdBillModelSqlCon.addMainSql("SELECT EVENT_CODE");
                    canceledBillIdBillModelSqlCon.addMainSql(" FROM PP_M_BILL WHERE ");
                    canceledBillIdBillModelSqlCon.addConditionWithNull(billModel.getCanceledBillId(), " ROW_ID = ?");
                    List<BillModel> canceledBillIdBillModelList = setSql(billMapper, canceledBillIdBillModelSqlCon);
                    if (!canceledBillIdBillModelList.isEmpty()) {
                        String cancelEventNameChinese = CacheUtil.getName(canceledBillIdBillModelList.get(0).getEventCode(), NameEnum.CODE_NAME,
                                CodeEnum.EVENT_CODE);
                        data.put("eventNameChinese", Maps.getString(data, "eventNameChinese") + " - " + cancelEventNameChinese);
                    }
                }
            }
        }
        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchPigMoveInToList(long billId) throws Exception {

        return getPageInfo(pigEventMapper.searchPigMoveInToList(billId));

    }

    // =======================================
    @Override
    public List<SwineryModel> searchSwineryToList(Map<String, Object> inputMap) throws Exception {
        long lineId = Maps.getLongClass(inputMap, "lineId");
        String pigType = Maps.getString(inputMap, "pigType");
        String eventName = Maps.getString(inputMap, "eventName");

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.SWINERY_ID ROW_ID,COUNT(*) NOTES FROM pp_l_pig T0");
        sqlCon.addMainSql(" WHERE  T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 AND T0.SWINERY_ID IS NOT NULL");
        sqlCon.addConditionWithNull(getFarmId(), "  AND T0.FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY T0.SWINERY_ID");
        List<SwineryModel> list1 = setSql(swineryMapper, sqlCon);

        sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        if (lineId != 0) {
            sqlCon.addConditionWithNull(lineId, " AND LINE_ID = ?");
        }
        if ("PIG_MOVE_IN".equals(eventName)) {
            sqlCon.addCondition(PigConstants.SWINERY_CREATE_TYPE_SELF, " AND CREATE_TYPE = ? ");
        }
        sqlCon.addCondition(pigType, " AND PIG_TYPE in ?", false, true);
        // 不显示关闭批次
        sqlCon.addMainSql(" AND STATUS = '1'");

        List<SwineryModel> list = CacheUtil.setName(getList(swineryMapper, sqlCon), NameEnum.LINE_NAME);

        for (SwineryModel model1 : list) {

            for (int i = 0; i < list1.size(); i++) {
                SwineryModel model2 = list1.get(i);
                if (model1.getRowId().equals(model2.getRowId())) {
                    model1.set("pigQty", model2.getNotes());
                    list1.remove(i);
                    i--;
                }
            }
        }

        return list;
    }

    @Override
    public BasePageInfo searchSwineryToPage(Map<String, Object> inputMap) throws Exception {
        String displayClose = Maps.getString(inputMap, "displayClose");
        String businessCode = Maps.getString(inputMap, "businessCode");
        String swineryName = Maps.getString(inputMap, "swineryName");
        String pigTypeName = Maps.getString(inputMap, "pigTypeName");
        String planHead = Maps.getString(inputMap, "planHead");
        String pigQty = Maps.getString(inputMap, "pigQty");
        String beginDate = Maps.getString(inputMap, "beginDate");
        String endDate = Maps.getString(inputMap, "endDate");
        String weekNum = Maps.getString(inputMap, "weekNum");
        String houseType = Maps.getString(inputMap, "houseType");

        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS rowId,SWINERY_NAME AS swineryName, DELETED_FLAG AS deletedFlag,STATUS AS status,");
        sqlCon.addMainSql(" BUSINESS_CODE AS businessCode, CREATE_TYPE AS createType, PIG_TYPE AS pigType, HOUSE_TYPE AS houseType");
        sqlCon.addMainSql(" , PLAN_HEAD AS planHead, BEGIN_DATE AS beginDate, END_DATE AS endDate, WEEK_NUM AS weekNum,");
        sqlCon.addMainSql(" NOTES AS notes,IFNULL(T2.pigQty,0) AS pigQty,IF(IFNULL(T2.pigQty,0)=0,0,ROUND(T2.totalDay/pigQty,0)) AS avgDate");
        sqlCon.addMainSql(" FROM PP_M_SWINERY T1");
        sqlCon.addMainSql(" LEFT OUTER JOIN");
        sqlCon.addMainSql(" (SELECT SWINERY_ID AS swineryId, COUNT(*) AS pigQty,SUM(DATEDIFF(NOW(), BIRTH_DATE)+1) AS totalDay FROM pp_l_pig ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND PIG_CLASS <= 18 AND SWINERY_ID IS NOT NULL");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY SWINERY_ID) T2");
        sqlCon.addMainSql(" ON(T1.ROW_ID = T2.swineryId)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG='0'");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(businessCode, " AND T1.BUSINESS_CODE LIKE ?", true);
        sqlCon.addCondition(swineryName, " AND T1.SWINERY_NAME LIKE ?", true);
        sqlCon.addCondition(pigTypeName, " AND T1.PIG_TYPE = ?");
        sqlCon.addCondition(planHead, " AND T1.PLAN_HEAD = ?");
        sqlCon.addCondition(beginDate, " AND T1.BEGIN_DATE >= ?");
        sqlCon.addCondition(endDate, " AND T1.END_DATE <= ?");
        sqlCon.addCondition(weekNum, " AND T1.WEEK_NUM = ?");
        sqlCon.addCondition(pigQty, " AND IFNULL(T2.pigQty,0) = ?");
        sqlCon.addCondition(houseType, " AND T1.HOUSE_TYPE = ?");
        // 是否显示关闭猪群
        if ("on".equals(displayClose)) {
            sqlCon.addCondition(PigConstants.SWINERY_STATUS_CLOSE, " AND T1.STATUS = ?");

            // sqlCon.addCondition(PigConstants.SWINERY_STATUS_CLOSE, " AND (T1.STATUS = ?");
            // // 由于系统建群目前没有关闭群的功能(状态不变)，只能额外加判断
            // sqlCon.addMainSql(" OR (create_type = 1 AND IFNULL(T2.pigQty,0) = 0))");
        } else {
            sqlCon.addCondition(PigConstants.SWINERY_STATUS_OPEN, " AND T1.STATUS = ?");
            // sqlCon.addMainSql(" AND ((CREATE_TYPE = '1' AND IFNULL(T2.pigQty,0) > 0) OR (CREATE_TYPE = '2'))");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : list) {
            map.put("pigTypeName", CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
            map.put("createSwineryTypeName", CacheUtil.getName(Maps.getString(map, "createType"), NameEnum.CODE_NAME, CodeEnum.CREATE_SWINERY_TYPE));
            // 猪只数量为0的系统建群 实际状态没改，所以显示的是 “假”状态
            // if (PigConstants.SWINERY_CREATE_TYPE_SYS == Maps.getLong(map, "createType") && Maps.getLong(map, "pigQty") == 0) {
            // map.put("statusName", PigConstants.SWINERY_STATUS_MAP.get(PigConstants.SWINERY_STATUS_CLOSE));
            // } else {
            map.put("statusName", PigConstants.SWINERY_STATUS_MAP.get(map.get("status")));
            // }
            map.put("houseTypeName", CacheUtil.getName(Maps.getString(map, "houseType"), NameEnum.HOUSE_TYPE_NAME));
        }

        return getPageInfo(list);
    }

    @Override
    public void editSwinery(Map<String, Object> inputMap) throws Exception {
        SwineryModel swineryModel = getBean(SwineryModel.class, inputMap);
        long companyId = getUserDetail().getCompanyId();
        long farmId = getUserDetail().getFarmId();
        swineryModel.setCompanyId(companyId);
        swineryModel.setFarmId(farmId);
        // 验证猪群代码是否重复
        // long businessIsExist = ParamUtil.isExist("pp_m_swinery", swineryModel.getRowId(), new String[] { swineryModel.getBusinessCode() },
        // new String[] { "BUSINESS_CODE" });
        long nameIsExist = ParamUtil.isExist("pp_m_swinery", swineryModel.getRowId(), swineryModel.getSwineryName() + "," + getFarmId(),
                "SWINERY_NAME,FARM_ID");
        // if (businessIsExist > 0) {
        // Thrower.throwException(BaseBusiException.CODE_DUPLICATE_ERROR, swineryModel.getBusinessCode());
        // }
        if (nameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, swineryModel.getSwineryName());
        }
        // 根据业务编码下的猪舍编码自动生成猪舍编码
        swineryModel.setBusinessCode(ParamUtil.getBCode("SWINERY_CODE", getEmployeeId(), getCompanyId(), getFarmId()));

        if (swineryModel.getRowId() == 0 && "0".equals(swineryModel.getDeletedFlag())) {
            swineryModel.setCreateType(String.valueOf(PigConstants.SWINERY_CREATE_TYPE_SELF));
            swineryModel.setSwineryDayage(0.0);
            swineryModel.setStartOperateDate(Maps.getDate(inputMap, "beginDate"));
            swineryModel.setLastOperateDate(Maps.getDate(inputMap, "beginDate"));
            swineryMapper.insert(swineryModel);
        } else {
            swineryMapper.update(swineryModel);
        }
    }

    @Override
    public void deleteSwinerys(long[] ids) throws Exception {
        boolean flag = ParamUtil.isExitDetail("PP_L_PIG", "SWINERY_ID", ids);

        if (flag) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪群已经有过猪只，不能删除！");
        }
        swineryMapper.deletes(ids);
    }

    // =======================================
    @Override
    public List<PigModel> searchPigToList(String pigType) throws Exception {
        return pigMapper.searchToList(getFarmId());
    }

    @Override
    public BasePageInfo searchPigToPage(Map<String, Object> requestMap) throws Exception {
        String moveOut = Maps.getString(requestMap, "moveOut");
        String earBrand = Maps.getString(requestMap, "earBrand");
        String materialId = Maps.getString(requestMap, "materialId");
        String pigClass = Maps.getString(requestMap, "pigClass");
        String orderByASC = Maps.getString(requestMap, "orderByASC");
        String parity = Maps.getString(requestMap, "parity");
        String swineryId = Maps.getString(requestMap, "swineryName");
        String houseId = Maps.getString(requestMap, "houseName");
        String pigType = Maps.getString(requestMap, "pigTypeName");
        String breedId = Maps.getString(requestMap, "breedName");

        SqlCon sql = new SqlCon();
        sql.addCondition(getFarmId(), " AND A.FARM_ID = ?");
        sql.addCondition(earBrand, " AND B.EAR_BRAND LIKE ? ", true);
        // 耳缺号
        // sql.addCondition(earShort, "OR B.EAR_SHORT = ?");
        // 猪群名称
        sql.addCondition(swineryId, " AND A.SWINERY_ID = ? ");
        sql.addCondition(houseId, " AND A.HOUSE_ID = ? ");
        sql.addCondition(materialId, " AND A.MATERIAL_ID = ? ");
        sql.addCondition(pigType, " AND A.PIG_TYPE = ? ");
        sql.addCondition(breedId, " AND A.BREED_ID = ? ");
        sql.addCondition(parity, " AND A.PARITY = ? ");
        sql.addMainSql(" AND (A.ORIGIN <> 3 OR A.ORIGIN IS NULL) ");

        if (moveOut != null) {
            sql.addCondition(pigClass, " AND A.PIG_CLASS = ? ");
            sql.addCondition(PigConstants.PIG_CLASS_LZM, " AND A.PIG_CLASS > '?' ");
            sql.addCondition(PigConstants.PIG_CLASS_TT, " AND A.PIG_CLASS <> '?' ");
        } else {
            // 如果选择的状态是离场状态，而没有在离场上 打钩
            if (pigClass != null) {
                Integer in = new Integer(pigClass);
                if (in > 18 && in != 24) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "当前选择的猪只状态【" + CacheUtil.getName(pigClass, NameEnum.PIG_CLASS_NAME)
                            + "】属于离场状态，请钩上离场！");
                }
            }
            sql.addCondition(pigClass, " AND A.PIG_CLASS = ? ");
            sql.addCondition(PigConstants.PIG_CLASS_LZM, " AND ( A.PIG_CLASS <='?' or A.PIG_CLASS = '" + PigConstants.PIG_CLASS_TT + "')");
        }

        // 模糊查询_按照准确度排序
        if (!StringUtil.isBlank(earBrand)) {
            sql.addCondition(earBrand, " ORDER BY LENGTH(REPLACE(B.EAR_BRAND, ? ,'_')) / LENGTH(B.EAR_BRAND) ");
        } else if (moveOut != null) {
            sql.addMainSql(" ORDER BY d.PIG_CLASS_NAME,TIMESTAMPDIFF(DAY, a.LAST_CLASS_DATE, NOW()) ");
        } else {
            sql.addMainSql(" ORDER BY d.PIG_CLASS_NAME,TIMESTAMPDIFF(DAY, a.LAST_CLASS_DATE, NOW()) ");
        }
        if (StringUtil.isBlank(orderByASC)) {
            sql.addMainSql(" DESC,B.EAR_BRAND ");
        } else {
            sql.addMainSql(" ASC,B.EAR_BRAND ");
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("condition", sql.getCondition());

        setToPage();
        List<PigView> list = new ArrayList<>();
        if (moveOut != null) {
            list = pigMapper.searchListByCon3(map);
        } else {
            list = pigMapper.searchListByCon2(map);
        }
        // List<PigView> list = getList(pigMapper, sql.getCondition());
        list = CacheUtil.setName(list, new NameEnum[] { NameEnum.HOUSE_NAME, NameEnum.SWINERY_NAME, NameEnum.MATERIAL_NAME, NameEnum.BREED_NAME,
                NameEnum.PIG_CLASS_NAME, NameEnum.CODE_NAME }, CodeEnum.PIG_SEX_NAME, CodeEnum.PIG_TYPE_NAME);
        list = CacheUtil.setName(list, new NameEnum[] { NameEnum.CODE_NAME, NameEnum.CREATE_NAME }, CodeEnum.EVENT_CODE);
        return getPageInfo(list);
    }

    @Override
    public void editPig(PigModel pigModel) throws Exception {
        long companyId = getUserDetail().getCompanyId();
        long farmId = getUserDetail().getFarmId();
        pigModel.setCompanyId(companyId);
        pigModel.setFarmId(farmId);

        if (pigModel.getRowId() == 0 && "0".equals(pigModel.getDeletedFlag())) {

        } else {
        }
    }

    @Override
    public void deletePigs(long[] ids) throws Exception {
        Date date = new Date();
        for (long id : ids) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("lineNumber", 0);
            map.put("notes", "");
            map.put("originFlag", "S");
            map.put("originApp", "XN1.0");
            map.put("pigId", id);
            map.put("enterDate", date);
            map.put("worker", getEmployeeId());
            map.put("billId", 0);
            map.put("createId", getEmployeeId());
            map.put("eventName", "DELETE_PIG");
            map.put("errorCode", "0");
            map.put("errorMessage", "");
            pigEventMapper.deletePig(map);
            if (!"0".equals(Maps.getString(map, "errorCode"))) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, Maps.getString(map, "errorMessage"));
            }
        }
    }

    @Override
    public List<BillModel> searchObsoleteBill() throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT * FROM PP_M_BILL T WHERE DELETED_FLAG = 0 AND EVENT_CODE = 'BREED_PIG_OBSOLETE' ");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addMainSql(" AND EXISTS (SELECT 1 FROM PP_L_BILL_BOAR_OBSOLETE T1 WHERE DELETED_FLAG = 0 ");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addMainSql(" AND STATUS = 1 AND BILL_ID = T.ROW_ID AND NOT EXISTS (SELECT 1 FROM PP_L_PIG WHERE DELETED_FLAG = 0");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addMainSql(" AND PIG_CLASS = 23 AND ROW_ID = T1.PIG_ID) ) ORDER BY BILL_DATE ,CREATE_DATE ");
        return setSql(billMapper, sql);
    }

    @Override
    public BasePageInfo searchBoarObsolete(Long billId) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT * FROM PP_L_BILL_BOAR_OBSOLETE T WHERE DELETED_FLAG = 0 ");
        sql.addConditionWithNull(billId, " AND BILL_ID = ? ");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addMainSql(" AND STATUS = 1 ");
        sql.addMainSql(" AND NOT EXISTS (SELECT 1 FROM PP_L_PIG WHERE DELETED_FLAG = 0 ");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addMainSql(" AND PIG_CLASS = 23 AND ROW_ID = T.PIG_ID) ");
        setToPage();
        List<BoarObsoleteModel> list = setSql(boarObsoleteMapper, sql);

        for (int i = 0; i < list.size(); i++) {
            Map<String, String> cacheMap = CacheUtil.getPigInfo(String.valueOf(list.get(i).getPigId()), PigInfoEnum.PIG_INFO_WITHID);
            Map<String, Object> obsoleteMap = list.get(i).getData();
            obsoleteMap.put("workerName", CacheUtil.getName(String.valueOf(list.get(i).getWorker()), NameEnum.WORKER_NAME));
            obsoleteMap.put("earBrand", cacheMap.get("earBrand"));
            obsoleteMap.put("earShort", cacheMap.get("earShort"));
            obsoleteMap.put("breedName", cacheMap.get("breedName"));
            obsoleteMap.put("pigClassName", cacheMap.get("pigClassName"));
            obsoleteMap.put("parity", cacheMap.get("parity"));
            SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
            obsoleteMap.put("birthDate", sdate.format(Maps.getDate(cacheMap, "birthDate")));
            obsoleteMap.put("pigpenName", cacheMap.get("pigpenName"));
            obsoleteMap.put("houseName", cacheMap.get("houseName"));
            obsoleteMap.put("obsoleteReason", CacheUtil.getName(list.get(i).getObsoleteReason(), NameEnum.CODE_NAME, CodeEnum.OBSOLETE_REASON));
        }
        return getPageInfo(list);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public void editBoarObsolete(Map<String, Object> maps) throws Exception {
        // public void editBoarObsolete(long ids[], String status, Long billId, List<String> pigIdsList) throws Exception {
        if (maps.get("billId") == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择单据");
        }
        if (maps.get("enterDate") == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择单据日期");
        }
        if (TimeUtil.compareDate(String.valueOf(maps.get("enterDate")), TimeUtil.getSysDate(), 5) > 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "单据日期不能大于今天");
        }
        List<Long> idsArrayList = new ArrayList<>();
        String strIds = "";
        List<String> pigIdsList = new ArrayList<>();
        String pigIdsString = "";
        try {
            idsArrayList = (ArrayList<Long>) maps.get("ids[]");
            strIds = getLongListToString(idsArrayList);
            pigIdsList = (List<String>) maps.get("pigIds[]");
            pigIdsString = getStringArray(pigIdsList);
        }
        catch (Exception e) {
            strIds = String.valueOf(maps.get("ids[]"));
            pigIdsString = String.valueOf(maps.get("pigIds[]"));
        }

        if (maps.get("ids[]") == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择单据明细");
        }

        Long billId = Long.valueOf(maps.get("billId").toString());
        String status = (String) maps.get("status");

        // 审批
        SqlCon sql1 = new SqlCon();
        sql1.addMainSql(" UPDATE PP_L_BILL_BOAR_OBSOLETE SET ");
        sql1.addCondition(status, " STATUS = ? ");
        sql1.addCondition(maps.get("enterDate"), " ,AUDIT_DATE = ?");
        sql1.addMainSql(" WHERE ");
        sql1.addCondition(strIds, " ROW_ID IN ? ", false, true);
        setSql(boarObsoleteMapper, sql1);

        // 查询已审批 单据明细
        SqlCon sql2 = new SqlCon();
        sql2.addMainSql(" SELECT * FROM PP_L_BILL_BOAR_OBSOLETE ");
        sql2.addMainSql(" WHERE DELETED_FLAG = 0 ");
        sql2.addCondition(strIds, " AND ROW_ID IN ? ", false, true);
        List<BoarObsoleteModel> allList = setSql(boarObsoleteMapper, sql2);

        // 插入his表
        for (BoarObsoleteModel boarModel : allList) {

            PigEventHisModel pigEventHisModel = new PigEventHisModel();
            Map<String, String> map = CacheUtil.getPigInfo(String.valueOf(boarModel.getPigId()), PigInfoEnum.PIG_INFO_WITHID);
            pigEventHisModel.setStatus("1");
            pigEventHisModel.setDeletedFlag("0");
            pigEventHisModel.setOriginFlag("S");
            pigEventHisModel.setOriginApp("XN1.0");
            pigEventHisModel.setCompanyId(getCompanyId());
            pigEventHisModel.setFarmId(getFarmId());
            pigEventHisModel.setBillId(boarModel.getBillId());
            pigEventHisModel.setCreateDate(new Date());
            pigEventHisModel.setCreateId(getEmployeeId());
            pigEventHisModel.setEarBrand(map.get("earBrand"));
            pigEventHisModel.setEnterDate(boarModel.getObsoleteDate());
            pigEventHisModel.setEventName(EventConstants.BREED_PIG_OBSOLETE);
            String eventNotes = null;
            if ("2".equals(status)) {
                eventNotes = "耳牌号为:" + map.get("earBrand") + "审批通过";
            } else {
                eventNotes = "耳牌号为:" + map.get("earBrand") + "审批未通过";
            }
            pigEventHisModel.setEventNotes(eventNotes);
            pigEventHisModel.setHouseId(Maps.getLongClass(map, "houseId"));
            pigEventHisModel.setLastEventDate(TimeUtil.parseDate(String.valueOf(maps.get("enterDate")), TimeUtil.DATE_FORMAT));
            pigEventHisModel.setNotes(boarModel.getNotes());
            /* 修改原因:退回不该走这些方法,时间:2016-10-8修改人:程彬,,审批退回时不执行改变猪只状态类方法*****************begin */
            if (PigConstants.SATAUS_OBSOLETE_SUCCESS.equals(status)) {
                pigEventHisModel.setPigClass(PigConstants.PIG_CLASS_TT);
            } else {
                pigEventHisModel.setPigClass(Maps.getLongClass(map, "pigClass"));
            }
            /* 修改原因:退回不该走这些方法,时间:2016-10-8修改人:程彬,,审批退回时不执行改变猪只状态类方法*****************end */
            pigEventHisModel.setPigId(boarModel.getPigId());
            pigEventHisModel.setPigpenId(Maps.getLongClass(map, "pigpenId"));
            pigEventHisModel.setPigType(map.get("pigType"));
            pigEventHisModel.setSex(map.get("sex"));
            pigEventHisModel.setParity(Long.valueOf(map.get("parity")));
            pigEventHisModel.setSwineryId(Maps.getLongClass(map, "swineryId"));
            pigEventHisModel.setTableName("PP_L_BILL_BOAR_OBSOLETE");
            pigEventHisModel.setTableRowId(boarModel.getRowId());
            pigEventHisModel.setWorker(boarModel.getWorker());
            pigEventHisMapper.insert(pigEventHisModel);
        }
        /* 修改原因:退回不该走这些方法,时间:2016-10-8修改人:程彬,,审批退回时不执行改变猪只状态类方法*****************begin */
        if (PigConstants.SATAUS_OBSOLETE_SUCCESS.equals(status)) {
            // 更新pp_l_pig状态
            SqlCon pigClassSql = new SqlCon();
            pigClassSql.addMainSql(" UPDATE PP_L_PIG SET ");
            pigClassSql.addCondition(PigConstants.PIG_CLASS_TT, " PIG_CLASS = ? ,");
            pigClassSql.addCondition(String.valueOf(maps.get("enterDate")), " LAST_CLASS_DATE = ? ");
            pigClassSql.addMainSql(" WHERE ");
            pigClassSql.addCondition(pigIdsString, " ROW_ID IN ? ", false, true);
            setSql(pigMapper, pigClassSql);

            // 更新pp_l_pig_event_relates状态
            SqlCon relatesPigClassSql = new SqlCon();
            relatesPigClassSql.addMainSql(" UPDATE PP_L_PIG_EVENT_RELATES SET ");
            relatesPigClassSql.addCondition(PigConstants.PIG_CLASS_TT, " PIG_CLASS = ? ");
            relatesPigClassSql.addMainSql(" WHERE ");
            relatesPigClassSql.addCondition(billId, " BILL_ID = ? ");
            relatesPigClassSql.addCondition(pigIdsString, " AND PIG_ID IN ? ", false, true);
            setSql(pigEventRelatesMapper, relatesPigClassSql);

            // SqlCon sqlCon = new SqlCon();
            // sqlCon.addMainSql("SELECT A.* FROM PP_L_BILL_TOWORK A LEFT JOIN PP_L_BILL_TOWORK B ");
            // sqlCon.addMainSql(
            // " ON A.DELETED_FLAG = B.DELETED_FLAG AND A.STATUS = B.STATUS AND A.FARM_ID=B.FARM_ID AND A.ROW_ID = B.CHANGE_PIG_CLASS_ID ");
            // sqlCon.addMainSql("WHERE A.DELETED_FLAG=0 AND A.STATUS=1 AND B.CHANGE_PIG_CLASS_ID IS NULL ");
            // sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ? ");
            // sqlCon.addConditionWithNull(pigIdsString, " AND A.PIG_ID in ? ", false, true);
            // List<ToworkModel> list = setSql(toWorkerMapper, sqlCon);

            // 插入状态记录表

            Date createDate = new Date();
            List<ToworkModel> toworkList = new ArrayList<>();
            List<ToworkModel> updateList = new ArrayList<>();
            for (BoarObsoleteModel boarModel : allList) {
                Map<String, String> map = CacheUtil.getPigInfo(String.valueOf(boarModel.getPigId()), PigInfoEnum.PIG_INFO_WITHID);
                ToworkModel toworkModel = new ToworkModel();
                toworkModel.setBillId(boarModel.getBillId());
                ToworkModel model = this.getChangeClass(getFarmId(), boarModel.getPigId());
                toworkModel.setChangePigClassId(model.getChangePigClassId());
                toworkModel.setCompanyId(getCompanyId());
                toworkModel.setCreateDate(createDate);
                toworkModel.setCreateId(getEmployeeId());
                toworkModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                toworkModel.setFarmId(getFarmId());
                toworkModel.setHouseId(Maps.getLong(map, "houseId"));
                toworkModel.setLineId(Maps.getLongClass(map, "lineId"));
                toworkModel.setLineNumber(boarModel.getLineNumber());
                toworkModel.setNotes(boarModel.getNotes());
                toworkModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                toworkModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                toworkModel.setParity(Maps.getLongClass(map, "parity"));
                toworkModel.setPigClass(PigConstants.PIG_CLASS_TT);
                toworkModel.setPigId(boarModel.getPigId());
                toworkModel.setPigpenId(Maps.getLongClass(map, "pigpenId"));
                toworkModel.setPigType(Maps.getString(map, "pigType"));
                Long proNo = this.getProNo(boarModel.getPigId(), Maps.getLongClass(map, "parity"));
                toworkModel.setProNo(proNo);
                toworkModel.setSex(Maps.getString(map, "sex"));
                toworkModel.setSortNbr(model.getSortNbr());
                toworkModel.setStatus(CommonConstants.STATUS);
                toworkModel.setSwineryId(Maps.getLongClass(map, "swineryId"));
                toworkModel.setToworkDate(TimeUtil.parseDate(String.valueOf(maps.get("enterDate")), TimeUtil.DATE_FORMAT));
                toworkModel.setWorker(boarModel.getWorker());
                toworkList.add(toworkModel);

                ToworkModel updateModel = new ToworkModel();
                updateModel.setPigClassOut(PigConstants.PIG_CLASS_TT);
                updateModel.setToworkDateOut(TimeUtil.parseDate(String.valueOf(maps.get("enterDate")), TimeUtil.DATE_FORMAT));
                updateModel.setRowId(model.getChangePigClassId());
                updateList.add(updateModel);
            }
            toworkMapper.inserts(toworkList);
            toworkMapper.updates(updateList);
        }
        /* 修改原因:退回不该走这些方法,时间:2016-10-8修改人:程彬,,审批退回时不执行改变猪只状态类方法*****************end */
    }

    @Override
    public List<BoarObsoleteModel> searchBoarObsoletePass(String pigIds) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT * FROM PP_L_BILL_BOAR_OBSOLETE T0");
        sql.addMainSql(" WHERE T0.DELETED_FLAG = 0 AND T0.STATUS = 2 ");
        if (pigIds.startsWith(",")) {
            pigIds = pigIds.substring(1, pigIds.length());
        }
        sql.addCondition(pigIds, " AND T0.PIG_ID NOT IN ?", false, true);
        sql.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ? ");
        sql.addMainSql(" ORDER BY T0.AUDIT_DATE");

        List<BoarObsoleteModel> list = setSql(boarObsoleteMapper, sql);

        List<BoarObsoleteModel> passList = new ArrayList<BoarObsoleteModel>();
        for (BoarObsoleteModel boarModel : list) {
            Map<String, Object> map = boarModel.getData();
            Map<String, String> cacheMap = CacheUtil.getPigInfo(String.valueOf(boarModel.getPigId()), PigInfoEnum.PIG_INFO_WITHID);
            Long pigClass = Maps.getLong(cacheMap, "pigClass");

            if (pigClass != null) {
                // 去除死亡状态猪只
                if (Maps.getLong(cacheMap, "pigClass", 23L) != 23L) {
                    SqlCon sqlLastPigClass = new SqlCon();
                    sqlLastPigClass.addMainSql(" SELECT T4.row_id rowId,T4.PIG_CLASS_NAME pigClassName");
                    sqlLastPigClass.addMainSql(" FROM pp_l_bill_towork T0 ");
                    sqlLastPigClass.addMainSql(
                            " INNER JOIN (SELECT T1.ROW_ID,T1.CHANGE_PIG_CLASS_ID,T1.BILL_ID,T1.PIG_ID FROM pp_l_bill_towork T1 WHERE DELETED_FLAG=0)T2 ");
                    sqlLastPigClass.addMainSql(" ON T2.CHANGE_PIG_CLASS_ID=T0.ROW_ID ");
                    sqlLastPigClass.addCondition(Maps.getLong(map, "billId"), "  AND T2.BILL_ID=?");
                    sqlLastPigClass.addCondition(Maps.getLong(map, "pigId"), "  AND T2.PIG_ID=?");
                    sqlLastPigClass.addMainSql(
                            " INNER JOIN (SELECT T3.ROW_ID,T3.PIG_CLASS_NAME FROM cd_l_pig_class T3  WHERE T3.DELETED_FLAG=0) T4 ");
                    sqlLastPigClass.addMainSql(" ON T0.PIG_CLASS= T4.ROW_ID ");
                    sqlLastPigClass.addMainSql(" WHERE T0.DELETED_FLAG=0 ");
                    Map<String, String> sqlMap = new HashMap<>();
                    sqlMap.put("sql", sqlLastPigClass.getCondition());
                    PigClassModel pigClassModel = pigClassMapper.operSql(sqlMap).get(0);

                    map.put("pigType", cacheMap.get("pigType"));
                    SimpleDateFormat sdate = new SimpleDateFormat("yyyy-MM-dd");
                    map.put("birthDate", sdate.format(Maps.getDate(cacheMap, "birthDate")));
                    map.put("earBrand", cacheMap.get("earBrand"));
                    map.put("pigClassName", cacheMap.get("pigClassName"));
                    map.put("lastPigClassName", pigClassModel.getPigClassName());
                    map.put("houseId", cacheMap.get("houseId"));
                    map.put("houseName", CacheUtil.getName(cacheMap.get("houseId"), NameEnum.HOUSE_NAME));
                    map.put("sex", cacheMap.get("sex"));
                    map.put("sexName", CacheUtil.getName(cacheMap.get("sex"), NameEnum.CODE_NAME, CodeEnum.PIG_SEX_NAME));
                    map.put("breedId", cacheMap.get("breedId"));
                    map.put("breedName", cacheMap.get("breedName"));
                    map.put("obsoleteReasonName", CacheUtil.getName(boarModel.getObsoleteReason(), NameEnum.CODE_NAME, CodeEnum.OBSOLETE_REASON));
                    map.put("pigIds", String.valueOf(boarModel.getPigId()));
                    // 后备公猪
                    if (Long.valueOf(PigConstants.PIG_CLASS_HBGZ).equals(pigClassModel.getRowId())) {
                        map.put("saleType", PigConstants.SELL_GOOD_RESERVE_BOARD_PIG);
                        // 生产公猪
                    } else if (Long.valueOf(PigConstants.PIG_CLASS_SCGZ).equals(pigClassModel.getRowId())) {
                        map.put("saleType", PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG);
                        // 后备母猪
                    } else if (Long.valueOf(PigConstants.PIG_CLASS_HBMZ).equals(pigClassModel.getRowId()) || Long.valueOf(PigConstants.PIG_CLASS_HBDP)
                            .equals(pigClassModel.getRowId())) {
                        map.put("saleType", PigConstants.SELL_GOOD_RESERVE_SOW_PIG);
                        // 生产母猪
                    } else if (Long.valueOf(PigConstants.PIG_CLASS_FQ1).equals(pigClassModel.getRowId()) || Long.valueOf(PigConstants.PIG_CLASS_LC)
                            .equals(pigClassModel.getRowId()) || Long.valueOf(PigConstants.PIG_CLASS_KH).equals(pigClassModel.getRowId()) || Long
                                    .valueOf(PigConstants.PIG_CLASS_SOW_DN).equals(pigClassModel.getRowId()) || Long.valueOf(
                                            PigConstants.PIG_CLASS_RS).equals(pigClassModel.getRowId())) {
                        map.put("saleType", PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG);
                    }
                    passList.add(boarModel);
                }
            }
        }
        return passList;
    }

    public String getStringArray(long ids[]) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            str.append(ids[i]);
            if (i < ids.length - 1) {
                str.append(",");
            }
        }
        return str.toString();
    }

    public String getStringArray(List<String> idsList) {
        StringBuffer str = new StringBuffer();
        if (idsList != null && !idsList.isEmpty()) {
            for (int i = 0; i < idsList.size(); i++) {
                str.append(idsList.get(i));
                if (i < idsList.size() - 1) {
                    str.append(",");
                }
            }
        }
        return str.toString();
    }

    public String getLongListToString(List<Long> idsList) {
        StringBuffer str = new StringBuffer();
        if (!idsList.isEmpty()) {
            for (int i = 0; i < idsList.size(); i++) {
                str.append(idsList.get(i));
                if (i < idsList.size() - 1) {
                    str.append(",");
                }
            }
        }
        return str.toString();
    }

    public ToworkModel getChangeClass(long farmId, long pigId) {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT IF(MAX(SORT_NBR) IS NULL,0,MAX(SORT_NBR)+1) SORT_NBR,MAX(ROW_ID) CHANGE_PIG_CLASS_ID ");
        sql.addMainSql(" FROM pp_l_bill_towork T0 WHERE T0.STATUS = 1 AND T0.DELETED_FLAG = 0 ");
        sql.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ? ");
        sql.addConditionWithNull(pigId, " AND T0.PIG_ID = ? ");
        List<ToworkModel> listWork = setSql(toworkMapper, sql);
        if (listWork.isEmpty()) {
            return null;
        }
        return listWork.get(0);
    }

    public Long getProNo(Long pigId, Long parity) {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT IFNULL(MAX(PRO_NO),1) proNo FROM pp_l_bill_breed");
        sql.addCondition(pigId, " WHERE PIG_ID = ?");
        sql.addCondition(parity, " AND PARITY = ?");
        List<BillBreedModel> listBreed = setSql(billBreedMapper, sql);
        if (listBreed.isEmpty()) {
            return null;
        }
        return listBreed.get(0).getProNo();
    }

    @Override
    public Map<String, String> searchBackfatScoreMap() throws Exception {
        return PigConstants.PIG_BACKFAT_MAP;
    }

    @Override
    public void editCloseSwinery(long[] ids) throws Exception {
        for (long id : ids) {
            SqlCon swineryPigQtySqlCon = new SqlCon();
            swineryPigQtySqlCon.addMainSql("SELECT COUNT(*) AS pigQty FROM PP_L_PIG WHERE DELETED_FLAG = '0' AND PIG_CLASS <= 18");
            swineryPigQtySqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            swineryPigQtySqlCon.addCondition(id, " AND SWINERY_ID = ?");

            Map<String, String> swineryPigQtySqlMap = new HashMap<String, String>();
            swineryPigQtySqlMap.put("sql", swineryPigQtySqlCon.getCondition());

            Map<String, Object> swineryPigQtyMap = paramMapper.getObjectInfos(swineryPigQtySqlMap).get(0);

            if (Maps.getLong(swineryPigQtyMap, "pigQty") != 0L) {
                Thrower.throwException(ProductionException.PIG_SWINERY_HAS_PIG, CacheUtil.getName(String.valueOf(id), NameEnum.SWINERY_NAME));
            }

            SqlCon getEndDateSqlCon = new SqlCon();
            getEndDateSqlCon.addMainSql("SELECT DATE_FORMAT(IFNULL(MAX(T1.END_DATE),NOW()),'%Y-%m-%d') AS endDate FROM (");
            getEndDateSqlCon.addMainSql(" SELECT MAX(CHANGE_SWINERY_DATE_OUT) AS END_DATE FROM PP_L_BILL_CHANGE_SWINERY WHERE DELETED_FLAG = '0'");
            getEndDateSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            getEndDateSqlCon.addCondition(id, " AND SWINERY_ID = ?");
            getEndDateSqlCon.addMainSql(" UNION ALL");
            getEndDateSqlCon.addMainSql(" SELECT MAX(LEAVE_DATE) AS END_DATE FROM PP_L_BILL_LEAVE WHERE DELETED_FLAG = '0'");
            getEndDateSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            getEndDateSqlCon.addCondition(id, " AND SWINERY_ID = ?");
            getEndDateSqlCon.addMainSql(" ) T1");

            Map<String, String> getEndDateSqlMap = new HashMap<String, String>();
            getEndDateSqlMap.put("sql", getEndDateSqlCon.getCondition());

            Map<String, Object> getEndDateMap = paramMapper.getObjectInfos(getEndDateSqlMap).get(0);

            SqlCon sql = new SqlCon();
            sql.addMainSql(" UPDATE PP_M_SWINERY SET");
            sql.addCondition(PigConstants.SWINERY_STATUS_CLOSE, " STATUS = ?");
            sql.addCondition(Maps.getString(getEndDateMap, "endDate"), ", END_DATE = ?");
            sql.addMainSql(" WHERE DELETED_FLAG = '0'");
            sql.addCondition(id, " AND ROW_ID = ?");
            setSql(swineryMapper, sql);
        }
    }

    @Override
    public void editOpenSwinery(long[] ids) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" UPDATE PP_M_SWINERY SET");
        sql.addCondition(PigConstants.SWINERY_STATUS_OPEN, " STATUS = ?");
        sql.addMainSql(", END_DATE = NULL");
        sql.addMainSql(" WHERE DELETED_FLAG = 0");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sql.addCondition(getStringArray(ids), " AND ROW_ID IN ?", false, true);
        setSql(swineryMapper, sql);
    }

    @Override
    public void editNurseChangeHouse(BillModel billModel, String eventName, String nurseChangeHouseViewList, String zcfxycbbFlag) throws Exception {
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
        Date createDate = new Date();

        List<Map<String, Object>> nurseList = getMapList(nurseChangeHouseViewList);
        if (nurseList == null || nurseList.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
        }

        List<FosterView> fosterViews = getJsonList(nurseChangeHouseViewList, FosterView.class);
        Set<Long> pigIdSet = new HashSet<Long>();
        Set<Long> boardSowIdSet = new HashSet<Long>();
        Set<Long> inPigIdSet = new HashSet<Long>();
        Set<Long> inPigpenSet = new HashSet<Long>();

        for (Iterator<FosterView> it = fosterViews.iterator(); it.hasNext();) {
            FosterView fosterView = (FosterView) it.next();
            pigIdSet.add(fosterView.getPigId());
            if (fosterView.getBoardSowId() != null) {
                boardSowIdSet.add(fosterView.getBoardSowId());
            }
            if (fosterView.getInPigId() != null) {
                inPigIdSet.add(fosterView.getInPigId());
            }
            if (fosterView.getInPigpenId() != null) {
                inPigpenSet.add(fosterView.getInPigpenId());
            }
        }

        if (inPigpenSet.size() < fosterViews.size()) {
            Thrower.throwException(ProductionException.NURSE_INPIGPEN_IS_SAME);
        }
        // 一个单据中，奶妈，代养母猪，原栏位母猪三者不能一样，跨行也不行
        for (Long boardSowId : boardSowIdSet) {
            if (pigIdSet.contains(boardSowId)) {
                Thrower.throwException(ProductionException.NURSE_CAN_NOT_BE_BOARDSOW);
            }
            if (inPigIdSet.contains(boardSowId)) {
                Thrower.throwException(ProductionException.BOARDSOW_CAN_NOT_BE_INPIG);
            }
        }

        for (Long inPigId : inPigIdSet) {
            if (pigIdSet.contains(inPigId)) {
                Thrower.throwException(ProductionException.NURSE_CAN_NOT_BE_INPIG);
            }
        }

        for (Map<String, Object> nurseMap : nurseList) {
            if (StringUtil.isBlank(Maps.getString(nurseMap, "enterDate"))) {
                Thrower.throwException(BaseBusiException.PP_EVENT_ERROR);
            }
            Date enterDate = Maps.getDate(nurseMap, "enterDate");
            Date minDate = Maps.getDate(nurseMap, "minValidDate");
            Date maxDate = Maps.getDate(nurseMap, "maxValidDate");
            if (!(TimeUtil.daysBetween(enterDate, minDate) >= 0 && TimeUtil.daysBetween(enterDate, maxDate) <= 0)) {
                Thrower.throwException(BaseBusiException.PP_EVENT_TIME_ERROR, nurseMap.get("lineNumber"), TimeUtil.format(enterDate,
                        TimeUtil.DATE_FORMAT), TimeUtil.format(minDate, TimeUtil.DATE_FORMAT), TimeUtil.format(maxDate, TimeUtil.DATE_FORMAT));
            }

            sapEventMasage(getFarmId(), enterDate);

            if (Maps.getLongClass(nurseMap, "weanNum") == null) {
                nurseMap.put("weanNum", 0L);
            }
            if (Maps.getLongClass(nurseMap, "dieNum") == null) {
                nurseMap.put("dieNum", 0L);
            }
            if (Maps.getLongClass(nurseMap, "fosterQty") == null) {
                nurseMap.put("fosterQty", 0L);
            }
            if (!Maps.getLongClass(nurseMap, "pigQty").equals(Maps.getLongClass(nurseMap, "weanNum") + Maps.getLongClass(nurseMap, "dieNum") + Maps
                    .getLongClass(nurseMap, "fosterQty"))) {
                Thrower.throwException(ProductionException.NURSE_CHANGE_HOUSE_PIGQTY_ERROR, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getDoubleClass(nurseMap, "weanWeight") == null) {
                Thrower.throwException(ProductionException.WEAN_WEIGHT_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLong(nurseMap, "fosterQty") != 0L && Maps.getLongClass(nurseMap, "boardSowId") == null) {
                Thrower.throwException(ProductionException.BOARD_SOW_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLongClass(nurseMap, "boardSowId") != null && Maps.getLong(nurseMap, "fosterQty") == 0L) {
                Thrower.throwException(ProductionException.NURSE_FOSTER_QTY_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLongClass(nurseMap, "inHouseId") == null) {
                Thrower.throwException(ProductionException.NURSE_CHANGE_HOUSE_INHOUSEID_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLongClass(nurseMap, "inPigpenId") == null) {
                Thrower.throwException(ProductionException.NURSE_CHANGE_HOUSE_INPIGPEN_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLongClass(nurseMap, "inPigId") != null && Maps.getLongClass(nurseMap, "boardSowId") != null && Maps.getLong(nurseMap,
                    "inPigId") == Maps.getLong(nurseMap, "boardSowId")) {
                Thrower.throwException(ProductionException.BOARDSOW_IS_SAME_AS_INPIG, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLongClass(nurseMap, "inPigId") != null && Maps.getLongClass(nurseMap, "inPigHouseId") == null) {
                Thrower.throwException(ProductionException.NURSE_CHANGE_HOUSE_INPIG_HOUSEID_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }
            if (Maps.getLongClass(nurseMap, "worker") == null) {
                Thrower.throwException(ProductionException.WORKER_IS_NULL, Maps.getLong(nurseMap, "lineNumber"));
            }

            // 若存在审批中淘汰单，则自动退回
            editObsoleteDefeatAuto(Maps.getLong(nurseMap, "pigId"), enterDate, billId);

            // 若断奶数不为0，则需要做断奶
            if (Maps.getLong(nurseMap, "weanNum") != 0L) {
                WeanView weanView = new WeanView();
                weanView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
                weanView.setEnterDate(enterDate);
                weanView.setWeanNum(Maps.getLong(nurseMap, "weanNum"));
                weanView.setBillId(billId);
                weanView.setCreateId(getEmployeeId());
                weanView.setCompanyId(getCompanyId());
                weanView.setEventName(eventName);
                weanView.setFarmId(getFarmId());
                weanView.setNotes(Maps.getString(nurseMap, "notes"));
                weanView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                weanView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                weanView.setPigId(Maps.getLong(nurseMap, "pigId"));
                weanView.setWeanWeight(Maps.getDouble(nurseMap, "weanWeight"));
                weanView.setWorker(Maps.getLong(nurseMap, "worker"));
                // 奶妈不能改变状态
                weanView.setFlag("N");
                weanView.setSysInput("2");
                // 先断奶 再仔猪死亡，不然影响母猪带仔数
                pigEventMapper.wean(weanView);
                if (!"0".equals(weanView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, weanView.getErrorMessage());
                }
            }
            // 若仔猪死亡数不为0，则需要做仔猪死亡
            if (Maps.getLong(nurseMap, "dieNum") != 0L) {
                ChildDieView childDieView = new ChildDieView();
                childDieView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
                childDieView.setBillId(billId);
                childDieView.setFarmId(getFarmId());
                childDieView.setCompanyId(getCompanyId());
                childDieView.setEventName(eventName);
                childDieView.setCreateId(getEmployeeId());
                childDieView.setPigId(Maps.getLong(nurseMap, "pigId"));
                childDieView.setLeaveQty(Maps.getLong(nurseMap, "dieNum"));
                childDieView.setEnterDate(enterDate);
                childDieView.setWorker(Maps.getLong(nurseMap, "worker"));
                childDieView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                childDieView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                childDieView.setNotes(Maps.getString(nurseMap, "notes"));
                childDieView.setFlag("Y");
                pigEventMapper.childPigDie(childDieView);
                if (!"0".equals(childDieView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, childDieView.getErrorMessage());
                }
            }
            // 若寄养数量不为0，则需要做寄养
            if (Maps.getLong(nurseMap, "fosterQty") != 0L) {
                FosterView fosterView = new FosterView();
                fosterView.setBillId(billId);
                fosterView.setBoardSowId(Maps.getLong(nurseMap, "boardSowId"));
                fosterView.setCompanyId(getCompanyId());
                fosterView.setCreateId(getEmployeeId());
                fosterView.setEnterDate(enterDate);
                fosterView.setEventName(eventName);
                fosterView.setFarmId(getFarmId());
                fosterView.setFosterQty(Maps.getLong(nurseMap, "fosterQty"));
                fosterView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
                fosterView.setNotes(Maps.getString(nurseMap, "notes"));
                fosterView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                fosterView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                fosterView.setPigId(Maps.getLong(nurseMap, "pigId"));
                fosterView.setWorker(Maps.getLong(nurseMap, "worker"));
                pigEventMapper.foster(fosterView);
                if (!"0".equals(fosterView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, fosterView.getErrorMessage());
                }
            }

            // 奶妈基本信息
            List<Long> ids = new ArrayList<Long>();
            ids.add(Maps.getLong(nurseMap, "pigId"));
            List<ChangeHouseInfoView> pigInfos = changeHouseMapper.searchChangeHouseInfo(ids);
            ChangeHouseInfoView pigInfo = pigInfos.get(0);

            // 插入nurse表
            NurseModel nurseModel = new NurseModel();
            nurseModel.setBillId(billId);
            nurseModel.setCompanyId(getCompanyId());
            nurseModel.setCreateDate(new Timestamp(createDate.getTime()));
            nurseModel.setCreateId(getEmployeeId());
            nurseModel.setFarmId(getFarmId());
            nurseModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            nurseModel.setFosterNum(Maps.getLong(nurseMap, "fosterQty"));
            nurseModel.setHouseId(Maps.getLong(nurseMap, "houseId"));
            nurseModel.setInHouseId(Maps.getLong(nurseMap, "inHouseId"));
            nurseModel.setInPigHouseId(Maps.getLongClass(nurseMap, "inPigHouseId"));
            nurseModel.setInPigId(Maps.getLongClass(nurseMap, "inPigId"));
            nurseModel.setInPigpenId(Maps.getLongClass(nurseMap, "inPigpenId"));
            nurseModel.setInPigPigpenId(Maps.getLongClass(nurseMap, "inPigPigpenId"));
            nurseModel.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
            nurseModel.setNotes(Maps.getString(nurseMap, "notes"));
            nurseModel.setPigId(Maps.getLongClass(nurseMap, "pigId"));
            nurseModel.setPigpenId(pigInfo.getPigPenId());
            nurseModel.setBoardSowId(Maps.getLongClass(nurseMap, "boardSowId"));
            nurseModel.setParity(pigInfo.getParity());
            nurseModel.setSwineryId(pigInfo.getSwineryId());
            nurseModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            nurseModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            nurseModel.setBabyNum(Maps.getLong(nurseMap, "pigQty"));
            nurseModel.setWeanWeight(Maps.getDouble(nurseMap, "weanWeight"));
            nurseModel.setChangeHouseDate(new Timestamp(Maps.getDate(nurseMap, "enterDate").getTime()));
            nurseModel.setStatus(CommonConstants.STATUS);
            nurseModel.setWeanNum(Maps.getLong(nurseMap, "weanNum"));
            nurseModel.setLeaveQty(Maps.getLong(nurseMap, "dieNum"));
            nurseModel.setWorker(Maps.getLong(nurseMap, "worker"));
            nurseMapper.insert(nurseModel);

            // 奶妈转舍
            ChangeHouseView nurseChangeHouseView = new ChangeHouseView();
            nurseChangeHouseView.setBillId(billId);
            nurseChangeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_OTH);
            nurseChangeHouseView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            nurseChangeHouseView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            nurseChangeHouseView.setPigId(Maps.getLong(nurseMap, "pigId"));
            nurseChangeHouseView.setInHouseId(Maps.getLong(nurseMap, "inHouseId"));
            nurseChangeHouseView.setInPigpenId(Maps.getLong(nurseMap, "inPigpenId"));
            nurseChangeHouseView.setEnterDate(enterDate);
            nurseChangeHouseView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
            nurseChangeHouseView.setNotes(Maps.getString(nurseMap, "notes"));
            nurseChangeHouseView.setWorker(Maps.getLong(nurseMap, "worker"));
            nurseChangeHouseView.setCreateId(getEmployeeId());
            nurseChangeHouseView.setEventName(eventName);
            pigEventMapper.changeHouse(nurseChangeHouseView);
            if (!"0".equals(nurseChangeHouseView.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, nurseChangeHouseView.getErrorMessage());
            }

            if (Maps.getLongClass(nurseMap, "inPigId") != null) {
                // 若原栏位母猪带仔为0，则不必在做寄养
                SqlCon pigSql = new SqlCon();
                pigSql.addMainSql("SELECT COUNT(*) pigQty FROM pp_l_pig WHERE");
                pigSql.addMainSql(" PIG_TYPE = 3 AND PIG_CLASS IN (12,13) AND STATUS = 1 AND DELETED_FLAG = 0");
                pigSql.addCondition(Maps.getLong(nurseMap, "inPigId"), " AND BOARD_SOW_ID = ?");
                Map<String, String> sqlMap = new HashMap<String, String>();
                sqlMap.put("sql", pigSql.getCondition());
                List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

                if (Maps.getLong(list.get(0), "pigQty") != 0) {
                    // 原母猪带仔全部寄养到奶妈
                    FosterView fosterView = new FosterView();
                    fosterView.setBillId(billId);
                    fosterView.setBoardSowId(Maps.getLong(nurseMap, "pigId"));
                    fosterView.setCompanyId(getCompanyId());
                    fosterView.setCreateId(getEmployeeId());
                    fosterView.setEnterDate(enterDate);
                    fosterView.setEventName(eventName);
                    fosterView.setFarmId(getFarmId());
                    // fosterView.setFosterQty(Maps.getLong(nurseMap, "fosterQty"));原母猪带仔全部寄养到奶妈
                    fosterView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
                    fosterView.setNotes(Maps.getString(nurseMap, "notes"));
                    fosterView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    fosterView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    fosterView.setPigId(Maps.getLong(nurseMap, "inPigId"));
                    fosterView.setWorker(Maps.getLong(nurseMap, "worker"));
                    fosterView.setFlag("N");
                    pigEventMapper.foster(fosterView);
                    if (!"0".equals(fosterView.getErrorCode())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, fosterView.getErrorMessage());
                    }
                }

                // 原栏位母猪断奶
                WeanView weanView = new WeanView();
                weanView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
                weanView.setEnterDate(enterDate);
                weanView.setWeanNum(0L);
                weanView.setBillId(billId);
                weanView.setCreateId(getEmployeeId());
                weanView.setCompanyId(getCompanyId());
                weanView.setEventName(eventName);
                weanView.setFarmId(getFarmId());
                weanView.setNotes(Maps.getString(nurseMap, "notes"));
                weanView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                weanView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                weanView.setPigId(Maps.getLong(nurseMap, "inPigId"));
                weanView.setWeanWeight(0.0);
                weanView.setWorker(Maps.getLong(nurseMap, "worker"));
                weanView.setSysInput("2");

                pigEventMapper.wean(weanView);
                if (!"0".equals(weanView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, weanView.getErrorMessage());
                }

                // 原栏位母猪转舍
                ChangeHouseView changeHouseView = new ChangeHouseView();
                changeHouseView.setBillId(billId);
                changeHouseView.setChangeHouseType(PigConstants.CHANGE_HOUSE_TYPE_DOWN_DEL);
                changeHouseView.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                changeHouseView.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                changeHouseView.setPigId(Maps.getLong(nurseMap, "inPigId"));
                changeHouseView.setInHouseId(Maps.getLong(nurseMap, "inPigHouseId"));
                changeHouseView.setInPigpenId(Maps.getLongClass(nurseMap, "inPigPigpenId"));
                changeHouseView.setEnterDate(enterDate);
                changeHouseView.setLineNumber(Maps.getLong(nurseMap, "lineNumber"));
                changeHouseView.setNotes(Maps.getString(nurseMap, "notes"));
                changeHouseView.setWorker(Maps.getLong(nurseMap, "worker"));
                changeHouseView.setCreateId(getEmployeeId());
                changeHouseView.setEventName(eventName);
                pigEventMapper.changeHouse(changeHouseView);
                if (!"0".equals(changeHouseView.getErrorCode())) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, changeHouseView.getErrorMessage());
                }
            }

            this.insertPigEventRelates(Maps.getLong(nurseMap, "pigId"));

            if (Maps.getLongClass(nurseMap, "boardSowId") != null) {
                this.insertPigEventRelates(Maps.getLongClass(nurseMap, "boardSowId"));
            }

            if (Maps.getLongClass(nurseMap, "inPigId") != null) {
                this.insertPigEventRelates(Maps.getLongClass(nurseMap, "inPigId"));
            }
        }
        // 批量插入整个单据的猪仔信息
        SqlCon getChildPigIdSqlCon = new SqlCon();
        getChildPigIdSqlCon.addMainSql("SELECT PIG_ID AS rowId FROM PP_L_BILL_WEAN_DETAIL WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
        getChildPigIdSqlCon.addMainSql(" UNION");
        getChildPigIdSqlCon.addMainSql(" SELECT PIG_ID AS rowId FROM PP_L_BILL_LEAVE WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
        getChildPigIdSqlCon.addMainSql(" UNION");
        getChildPigIdSqlCon.addMainSql(" SELECT PIG_ID AS rowId FROM PP_L_BILL_FOSTER_DETAIL WHERE STATUS = '1' AND DELETED_FLAG = '0'");
        getChildPigIdSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        getChildPigIdSqlCon.addCondition(billId, " AND BILL_ID = ?");
        List<PigModel> pigIdList = setSql(pigMapper, getChildPigIdSqlCon);
        if (pigIdList != null && !pigIdList.isEmpty()) {
            for (PigModel pigModel : pigIdList) {
                this.insertPigEventRelates(pigModel.getRowId());
            }
        }
    }

    @Override
    public List<Map<String, String>> searchValidPigByPigpenId(Long pigpenId) throws Exception {
        SqlCon pigSql = new SqlCon();
        pigSql.addMainSql("SELECT E.EAR_BRAND AS earBrand,P.ROW_ID AS pigId,P.PIG_CLASS AS pigClass FROM PP_L_PIG P");
        pigSql.addMainSql(" INNER JOIN PP_L_EAR_CODE E ON P.EAR_CODE_ID = E.ROW_ID AND E.DELETED_FLAG = 0");
        pigSql.addCondition(getFarmId(), " AND E.FARM_ID = ?");
        pigSql.addMainSql(" WHERE P.DELETED_FLAG = 0 AND P.PIG_TYPE = 2");
        pigSql.addConditionWithNull(getFarmId(), " AND P.FARM_ID = ?");
        pigSql.addCondition(pigpenId, " AND P.PIGPEN_ID = ?");
        pigSql.addMainSql(" AND P.PIG_CLASS IN (9,10)");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", pigSql.getCondition());

        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        if (list.size() > 1) {
            Thrower.throwException(ProductionException.PIGPEN_HAVE_MULTIPLE_SOW, CacheUtil.getName(String.valueOf(pigpenId), NameEnum.PIGPEN_NAME),
                    list.size());
        }
        if (list.size() == 0) {
            return null;
        }
        if (PigConstants.PIG_CLASS_RS == Maps.getLong(list.get(0), "pigClass")) {
            Thrower.throwException(ProductionException.NURSE_INPIGPEN_PIG_IS_RS, Maps.getString(list.get(0), "earBrand"));
        }
        return list;
    }

    @Override
    public BasePageInfo searchPorkCountBillList(String billId, String billDate, String checkOrgan, String accountUser) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT ROW_ID rowId,BILL_ID billId,SUM(CHECK_QTY) totalCheckQty,SUM(ACCOUNT_QTY) totalAccountQty,CHECK_ORGAN checkOrgan,ACCOUNT_USER accountUser,BILL_DATE billDate,NOTES notes, CHECK_DATE checkDate");
        sqlCon.addMainSql(" FROM PP_L_BILL_PORK_CHECK WHERE DELETED_FLAG=0 ");
        if (StringUtil.isNonBlank(billId)) {
            sqlCon.addCondition(billId, " AND BILL_ID like ? ", true);
        }
        if (StringUtil.isNonBlank(billDate)) {
            sqlCon.addCondition(billDate, " AND CHECK_DATE=? ");
        }
        if (StringUtil.isNonBlank(checkOrgan)) {
            sqlCon.addCondition(checkOrgan, " AND CHECK_ORGAN LIKE ? ", true);
        }
        if (StringUtil.isNonBlank(accountUser)) {
            sqlCon.addCondition(accountUser, " AND ACCOUNT_USER=? ");
        }

        sqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
        sqlCon.addMainSql(" GROUP BY BILL_ID ORDER BY CREATE_DATE DESC ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> porkCheckModelMaps = paramMapper.getObjectInfos(sqlMap);
        List<PorkCheckModel> porkCheckModels = new ArrayList<PorkCheckModel>();
        if (porkCheckModelMaps != null && !porkCheckModelMaps.isEmpty()) {
            for (Map<String, Object> map : porkCheckModelMaps) {
                PorkCheckModel porkCheckModel = new PorkCheckModel();
                porkCheckModel.setRowId((long) map.get("rowId"));
                porkCheckModel.setBillId((long) map.get("billId"));
                porkCheckModel.setAccountUser(Long.valueOf(map.get("accountUser").toString()));
                porkCheckModel.setCheckOrgan((String) map.get("checkOrgan"));
                porkCheckModel.setBillDate((Date) map.get("billDate"));
                porkCheckModel.setNotes((String) map.get("notes"));
                porkCheckModel.set("totalCheckQty", map.get("totalCheckQty"));
                porkCheckModel.set("totalAccountQty", map.get("totalAccountQty"));
                porkCheckModel.set("accountUserName", CacheUtil.getName(String.valueOf(map.get("accountUser")), NameEnum.WORKER_NAME));
                porkCheckModel.setCheckDate(Maps.getDate(map, "checkDate"));

                SqlCon totalWeightSql = new SqlCon();
                totalWeightSql.addMainSql(" SELECT SUM(T0.TOTAL_WEIGHT) totalWeight FROM  pp_l_bill_pork_check  T0 WHERE T0.DELETED_FLAG=0");
                totalWeightSql.addCondition(getFarmId(), " AND T0.FARM_ID=? ");
                totalWeightSql.addCondition(porkCheckModel.getBillId(), " AND T0.BILL_ID=? ");
                Map<String, String> totalWeightSqlMap = new HashMap<String, String>();
                totalWeightSqlMap.put("sql", totalWeightSql.getCondition());
                Map<String, Object> totalWeightMap = paramMapper.getObjectInfos(totalWeightSqlMap).get(0);
                porkCheckModel.set("totalWeight", totalWeightMap.get("totalWeight"));

                SqlCon houseNameSql = new SqlCon();
                houseNameSql.addMainSql(" SELECT T2.HOUSE_TYPE_NAME houseTypeName FROM pp_o_house T0 ");
                houseNameSql.addMainSql(" INNER JOIN pp_l_bill_pork_check T1 ON T0.ROW_ID=T1.HOUSE_ID ");
                houseNameSql.addMainSql(" INNER JOIN cd_l_pig_house T2 ON T0.HOUSE_TYPE=T2.ROW_ID ");
                houseNameSql.addCondition(getFarmId(), " AND T1.FARM_ID=? ");
                houseNameSql.addCondition(porkCheckModel.getBillId(), " AND T1.BILL_ID=? ");
                houseNameSql.addMainSql(" GROUP BY T2.ROW_ID ");
                Map<String, String> houseNameSqlMap = new HashMap<String, String>();
                houseNameSqlMap.put("sql", houseNameSql.getCondition());
                List<Map<String, Object>> houseNames = paramMapper.getObjectInfos(houseNameSqlMap);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < houseNames.size(); i++) {
                    if (i != houseNames.size() - 1) {
                        sb.append(houseNames.get(i).get("houseTypeName"));
                        sb.append(",");
                    } else {
                        sb.append(houseNames.get(i).get("houseTypeName"));
                    }
                }
                porkCheckModel.set("houseTypeName", sb.toString());
                porkCheckModels.add(porkCheckModel);
            }
        }
        return getPageInfo(porkCheckModels);
    }

    @Override
    public List<PorkCheckModel> searchPorkPigCountList(String billId, String billDate, String checkType) throws Exception {
        List<PorkCheckModel> porkCheckModels = new ArrayList<PorkCheckModel>();
        List<ValidPigModel> validPigModels = null;

        // 编辑肉猪盘点单据
        if (billId != null && !billId.isEmpty()) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
            sqlCon.addCondition(billId, " AND BILL_ID=? ");
            Map<String, String> sqlMap = new HashMap<String, String>();
            sqlMap.put("condition", sqlCon.getCondition());
            List<PorkCheckModel> porkCheckModels_ = porkCheckMapper.searchListByCon(sqlMap);
            if (porkCheckModels_ != null && !porkCheckModels_.isEmpty()) {
                String swineryName = null;
                for (PorkCheckModel porkCheckModel : porkCheckModels_) {
                    porkCheckModel.set("differQty", "0");
                    porkCheckModel.set("houseName", houseMapper.searchById(porkCheckModel.getHouseId()).getHouseName());
                    if (porkCheckModel.getSwineryId() == null) {
                        swineryName = "";
                    } else {
                        swineryName = swineryMapper.searchById(porkCheckModel.getSwineryId()).getSwineryName();
                    }
                    porkCheckModel.set("swineryName", swineryName);
                    if (1 == porkCheckModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "生产公猪");
                    } else if (2 == porkCheckModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "后备公猪");
                    } else if (3 == porkCheckModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "生产母猪");
                    } else if (4 == porkCheckModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "后备母猪");
                    } else if (5 == porkCheckModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "肉猪");
                    }
                    porkCheckModels.add(porkCheckModel);
                }
            }
            // 新增肉猪盘点数据
        } else {
            /**************************************** 2017-2-4 yangy 处理上个月肉猪盘点 ***********************************************/
            Date minDate = null;
            Date maxDate = null;
            Date billDateToDate = TimeUtil.parseDate(billDate);

            Date lastDate = TimeUtil.parse(TimeUtil.getLastDateOfMonth(billDate));
            if (TimeUtil.compareDate(lastDate, billDateToDate, Calendar.DATE) == 0) {
                minDate = lastDate;
            } else {
                minDate = TimeUtil.getLastMothByCurrent(billDateToDate);
            }
            maxDate = TimeUtil.dateAddDay(minDate, 5);
            if (StringUtil.isBlank(checkType)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择盘点类型");
            }
            if (TimeUtil.compareDate(billDateToDate, minDate, Calendar.DATE) < 0 || TimeUtil.compareDate(billDateToDate, maxDate,
                    Calendar.DATE) > 0) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "每月结算日期为上月最后一天到本月5号");
            }
            // 1：肉猪盘点，2：种猪盘点
            if ("1".equals(checkType)) {
                validPigModels = sweachValidPigMapper.searchPorkCheckByLastMonthToList(getFarmId(), TimeUtil.format(minDate));
                if (validPigModels != null && !validPigModels.isEmpty()) {
                    for (ValidPigModel validPigModel : validPigModels) {
                        validPigModel.setCheckPigType(Long.valueOf(5));
                    }
                }
            } else if ("2".equals(checkType)) {
                List<Map<String, Object>> porkCheckBoars = sweachValidPigMapper.searchPorkCheckByLastMonthBoarToList(getFarmId(), TimeUtil.format(
                        minDate));
                if (porkCheckBoars != null && !porkCheckBoars.isEmpty()) {
                    validPigModels = new ArrayList<ValidPigModel>();
                    for (Map<String, Object> map : porkCheckBoars) {
                        ValidPigModel validPigModelhbg = new ValidPigModel();
                        ValidPigModel validPigModelscg = new ValidPigModel();
                        ValidPigModel validPigModelhbm = new ValidPigModel();
                        ValidPigModel validPigModelscm = new ValidPigModel();

                        Long houseId = Maps.getLong(map, "houseId");
                        String houseName = Maps.getString(map, "houseName");
                        Long hbgPigQty = Maps.getLong(map, "hbgPigQty");
                        Long scgPigQty = Maps.getLong(map, "scgPigQty");
                        Long hbmPigQty = Maps.getLong(map, "hbmPigQty");
                        Long scmPigQty = Maps.getLong(map, "scmPigQty");
                        if (hbgPigQty > 0) {
                            validPigModelhbg.setPigQty(hbgPigQty.intValue());
                            validPigModelhbg.setHouseId(houseId);
                            validPigModelhbg.setHouseName(houseName);
                            validPigModelhbg.setCheckPigType(Long.valueOf(2));
                            validPigModelhbg.setSwineryAge(0D);
                            validPigModels.add(validPigModelhbg);
                        }
                        if (scgPigQty > 0) {
                            validPigModelscg.setPigQty(scgPigQty.intValue());
                            validPigModelscg.setHouseId(houseId);
                            validPigModelscg.setHouseName(houseName);
                            validPigModelscg.setCheckPigType(Long.valueOf(1));
                            validPigModelscg.setSwineryAge(0D);
                            validPigModels.add(validPigModelscg);
                        }
                        if (hbmPigQty > 0) {
                            validPigModelhbm.setPigQty(hbmPigQty.intValue());
                            validPigModelhbm.setHouseId(houseId);
                            validPigModelhbm.setHouseName(houseName);
                            validPigModelhbm.setCheckPigType(Long.valueOf(4));
                            validPigModelhbm.setSwineryAge(0D);
                            validPigModels.add(validPigModelhbm);
                        }
                        if (scmPigQty > 0) {
                            validPigModelscm.setPigQty(scmPigQty.intValue());
                            validPigModelscm.setHouseId(houseId);
                            validPigModelscm.setHouseName(houseName);
                            validPigModelscm.setCheckPigType(Long.valueOf(3));
                            validPigModelscm.setSwineryAge(0D);
                            validPigModels.add(validPigModelscm);
                        }
                    }
                }
            }

            /**************************************** 2017-2-4 yangy 处理上个月肉猪盘点 ***********************************************/
            if (validPigModels != null && !validPigModels.isEmpty()) {
                for (ValidPigModel validPigModel : validPigModels) {
                    PorkCheckModel porkCheckModel = new PorkCheckModel();
                    porkCheckModel.setHouseId(validPigModel.getHouseId());
                    porkCheckModel.setSwineryId(validPigModel.getSwineryId());
                    // porkCheckModel.setCheckQty(Long.valueOf(0));
                    porkCheckModel.setAccountQty(Long.valueOf(validPigModel.getPigQty()));
                    // porkCheckModel.setAvgWeight(0.0);
                    porkCheckModel.setTotalWeight(Long.valueOf(0));
                    porkCheckModel.set("houseName", validPigModel.getHouseName());
                    porkCheckModel.set("swineryName", validPigModel.getSwineryName());
                    porkCheckModel.set("differQty", "0");
                    porkCheckModel.setSwineryAge(validPigModel.getSwineryAge().longValue());
                    if (1 == validPigModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "生产公猪");
                    } else if (2 == validPigModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "后备公猪");
                    } else if (3 == validPigModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "生产母猪");
                    } else if (4 == validPigModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "后备母猪");
                    } else if (5 == validPigModel.getCheckPigType()) {
                        porkCheckModel.set("checkPigTypeName", "肉猪");
                    }
                    porkCheckModel.setCheckPigType(validPigModel.getCheckPigType());
                    porkCheckModels.add(porkCheckModel);
                }
            }
            /* if (validPigModels != null && !validPigModels.isEmpty()) {
             * List<PorkCheckModel> porkCheckModels_ = porkCheckMapper.searchToList(getFarmId());
             * for (ValidPigModel validPigModel : validPigModels) {
             * if (porkCheckModels_ != null && !porkCheckModels_.isEmpty()) {
             * for (PorkCheckModel porkCheckModel : porkCheckModels_) {
             * if(porkCheckModel.getCheckDate() != null){
             * int checkMonth = TimeUtil.getMonthByDate(porkCheckModel.getCheckDate());
             * int minMonth = TimeUtil.getMonthByDate(minDate);
             * if (validPigModel.getHouseId().equals(porkCheckModel.getHouseId()) && validPigModel.getSwineryId().equals(porkCheckModel
             * .getSwineryId()) && checkMonth == minMonth) {
             * checkflag = false;
             * break;
             * }
             * }
             * 
             * }
             * }
             * if (checkflag) {
             * PorkCheckModel porkCheckModel = new PorkCheckModel();
             * porkCheckModel.setHouseId(validPigModel.getHouseId());
             * porkCheckModel.setSwineryId(validPigModel.getSwineryId());
             * porkCheckModel.setCheckQty(Long.valueOf(0));
             * porkCheckModel.setAccountQty(Long.valueOf(validPigModel.getPigQty()));
             * porkCheckModel.setAvgWeight(0.0);
             * porkCheckModel.setTotalWeight(Long.valueOf(0));
             * porkCheckModel.set("houseName", validPigModel.getHouseName());
             * porkCheckModel.set("swineryName", validPigModel.getSwineryName());
             * porkCheckModel.set("differQty", "0");
             * porkCheckModel.setSwineryAge(validPigModel.getSwineryAge().longValue());
             * porkCheckModels.add(porkCheckModel);
             * }
             * checkflag = true;
             * }
             * } */
        }
        return porkCheckModels;
    }

    @Override
    public List<Map<String, Object>> selectSellBill(String pigType) throws Exception {
        SqlCon sellBillSql = new SqlCon();
        sellBillSql.addMainSql("SELECT m.ROW_ID billId,m.BUSINESS_CODE billCode,m.BILL_DATE billDate,COUNT(1) saleNum,");
        sellBillSql.addMainSql(" s.FARM_ID supplierId");
        sellBillSql.addMainSql(" FROM pp_m_bill m");
        sellBillSql.addMainSql(" INNER JOIN pp_l_bill_pig_sale s");
        sellBillSql.addMainSql(" ON m.ROW_ID = s.BILL_ID AND s.DELETED_FLAG = 0");
        sellBillSql.addMainSql(" INNER JOIN pp_l_bill_leave e1");
        sellBillSql.addMainSql(" ON m.ROW_ID = e1.BILL_ID AND e1.DELETED_FLAG = 0 AND e1.IS_MOVE_IN = 'N'");
        sellBillSql.addMainSql(" WHERE m.DELETED_FLAG = 0");
        sellBillSql.addCondition(getFarmId(), " AND s.CUSTOMER_ID = ?");
        if (PigConstants.PIG_TYPE_PORK.equals(pigType)) {
            sellBillSql.addCondition(PigConstants.SELL_TYPE_GOOD_PIG, " AND s.SALE_TYPE = ?");
        } else {
            sellBillSql.addCondition(PigConstants.SELL_TYPE_PRODUCTION_PIG, " AND s.SALE_TYPE = ?");
        }
        sellBillSql.addMainSql(" GROUP BY e1.BILL_ID ORDER BY m.BILL_DATE");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sellBillSql.getCondition());
        List<Map<String, Object>> sellBills = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : sellBills) {
            Long supplierId = Maps.getLong(map, "supplierId");
            map.put("supplierIdName", CacheUtil.getName(String.valueOf(supplierId), NameEnum.COMPANY_NAME));
        }
        return sellBills;
    }

    @Override
    public List<Map<String, Object>> selectPigListByBillId(Long billId, String pigType) throws Exception {
        SqlCon pigSql = new SqlCon();
        Map<String, String> sqlMap = new HashMap<>();

        if (PigConstants.PIG_TYPE_PORK.equals(pigType)) {
            pigSql.addMainSql("SELECT ROUND(IF(w.HOUSE_TYPE = 6,_getSwineryAge(w.ROW_ID,NOW(),1),_getSwineryAge(w.ROW_ID,NOW(),0)),0) dayAge,");
            pigSql.addMainSql(" DATE(L.LEAVE_DATE) leaveDate, p.FARM_ID farmId,h.HOUSE_NAME preHouseName,p.PIG_TYPE pigType,");
            pigSql.addMainSql(" p.HOUSE_ID preHouseId,p.SWINERY_ID preSwineryId,COUNT(1) sumNum,SUM(l.LEAVE_PRICE) sumPrice,");
            pigSql.addMainSql(" SUM(l.LEAVE_WEIGHT) sumWeight,");
            pigSql.addMainSql(" w.SWINERY_NAME preSwineryName,l.LINE_NUMBER lineNumber,COUNT(1) totalNum,p.BIRTH_DATE birthDate,");
            pigSql.addMainSql(" SUM(l.LEAVE_PRICE) averagePrice,SUM(l.LEAVE_WEIGHT) averageWeight,l.LINE_NUMBER preLineNumber,");
            pigSql.addMainSql(" c.BREED_ID breedId,c.STRAIN strain");
            pigSql.addMainSql(" FROM pp_l_pig p");
            pigSql.addMainSql(" INNER JOIN pp_l_bill_pig_sale s");
            pigSql.addMainSql(" ON p.LAST_BILL_ID = s.BILL_ID AND s.DELETED_FLAG = 0 AND p.FARM_ID = s.FARM_ID");
            pigSql.addMainSql(" INNER JOIN pp_l_bill_leave l");
            pigSql.addMainSql(" ON p.row_id = l.pig_id AND l.DELETED_FLAG = 0");
            pigSql.addMainSql(" INNER JOIN pp_m_swinery w");
            pigSql.addMainSql(" ON p.SWINERY_ID = w.ROW_ID AND w.DELETED_FLAG = 0");
            pigSql.addMainSql(" INNER JOIN pp_o_house h");
            pigSql.addMainSql(" ON p.HOUSE_ID = h.ROW_ID AND h.DELETED_FLAG = 0");
            pigSql.addMainSql(" INNER JOIN cd_o_pork_pig c");
            pigSql.addMainSql(" ON c.MATERIAL_ID = p.MATERIAL_ID AND c.DELETED_FLAG = 0");
            pigSql.addMainSql(" WHERE p.DELETED_FLAG = 0 AND l.IS_MOVE_IN = 'N'");
            pigSql.addCondition(getFarmId(), " AND s.CUSTOMER_ID = ?");
            pigSql.addCondition(PigConstants.SELL_TYPE_GOOD_PIG, " AND s.SALE_TYPE = ?");
            pigSql.addCondition(billId, " AND p.LAST_BILL_ID = ?");
            pigSql.addMainSql(" GROUP BY p.SWINERY_ID,l.LINE_NUMBER ORDER BY l.LINE_NUMBER");
            sqlMap.put("sql", pigSql.getCondition());
            List<Map<String, Object>> pigList = paramMapper.getObjectInfos(sqlMap);

            if (!pigList.isEmpty()) {
                checkMaterialIsExist(pigList, pigType);
            }
            return pigList;
        } else {
            // 种猪
            pigSql.addMainSql("SELECT p1.ROW_ID relationPigId,p1.FARM_ID farmId,p1.SWINERY_ID swineryId,p1.PIG_TYPE pigType,p1.SEX sex,");
            pigSql.addMainSql(" p1.PARITY parity,e2.LEAVE_PRICE onPrice,p1.LEFT_TEAT_NUM leftTeatNum,p1.RIGHT_TEAT_NUM rightTeatNum,");
            pigSql.addMainSql(" p1.BIRTH_DATE birthDate,p1.BIRTH_WEIGHT birthWeight,e2.LEAVE_WEIGHT enterWeight,p1.NOTES notes,");
            pigSql.addMainSql(" p1.MATERIAL_ID meterialId,c.EAR_BRAND earBrand,c.EAR_SHORT earShort,p1.SEED_FLAG seedFlag,");
            pigSql.addMainSql(" cd.BREED_ID breedId,cd.STRAIN strain,e2.LINE_NUMBER preLineNumber,");
            pigSql.addMainSql(" (SELECT e1.PIG_CLASS FROM pp_l_bill_leave e1 WHERE e1.PIG_ID = p1.ROW_ID AND e1.DELETED_FLAG = 0) pigClass,");
            pigSql.addMainSql(" (SELECT c1.EAR_BRAND FROM pp_l_ear_code c1 WHERE c1.ROW_ID = c.MOTHER_EAR_ID AND c1.DELETED_FLAG = 0) motherEar,");
            pigSql.addMainSql(" (SELECT c2.EAR_BRAND FROM pp_l_ear_code c2 WHERE c2.ROW_ID = c.FATHER_EAR_ID AND c2.DELETED_FLAG = 0) fatherEar");
            pigSql.addMainSql(" FROM pp_l_pig p1");
            pigSql.addMainSql(" INNER JOIN pp_l_ear_code c");
            pigSql.addMainSql(" ON p1.EAR_CODE_ID = c.ROW_ID AND c.DELETED_FLAG = 0 and c.STATUS = 1");
            pigSql.addMainSql(" INNER JOIN pp_l_bill_leave e2");
            pigSql.addMainSql(" ON p1.ROW_ID = e2.PIG_ID AND e2.DELETED_FLAG = 0 AND e2.IS_MOVE_IN = 'N'");
            if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
                pigSql.addMainSql(" INNER JOIN cd_o_boar cd");
            } else {
                pigSql.addMainSql(" INNER JOIN cd_o_sow cd");
            }
            pigSql.addMainSql(" ON p1.MATERIAL_ID = cd.MATERIAL_ID AND cd.DELETED_FLAG = 0");
            pigSql.addMainSql(" WHERE p1.DELETED_FLAG = 0 and p1.STATUS = 1");
            pigSql.addCondition(billId, " AND p1.LAST_BILL_ID = ?");
            pigSql.addCondition(pigType, " AND p1.PIG_TYPE = ?");

            pigSql.addMainSql(" UNION ALL ");
            // 留种猪
            pigSql.addMainSql("SELECT p1.ROW_ID relationPigId,p1.FARM_ID farmId,p1.SWINERY_ID swineryId,p1.PIG_TYPE pigType,p1.SEX sex,");
            pigSql.addMainSql(" p1.PARITY parity,e2.LEAVE_PRICE onPrice,p1.LEFT_TEAT_NUM leftTeatNum,p1.RIGHT_TEAT_NUM rightTeatNum,");
            pigSql.addMainSql(" p1.BIRTH_DATE birthDate,p1.BIRTH_WEIGHT birthWeight,e2.LEAVE_WEIGHT enterWeight,p1.NOTES notes,");
            pigSql.addMainSql(" p1.MATERIAL_ID meterialId,c.EAR_BRAND earBrand,c.EAR_SHORT earShort,p1.SEED_FLAG seedFlag,");
            pigSql.addMainSql(" cd.BREED_ID breedId,cd.STRAIN strain,e2.LINE_NUMBER preLineNumber,");
            pigSql.addMainSql(" (SELECT e1.PIG_CLASS FROM pp_l_bill_leave e1 WHERE e1.PIG_ID = p1.ROW_ID AND e1.DELETED_FLAG = 0) pigClass,");
            pigSql.addMainSql(" (SELECT c1.EAR_BRAND FROM pp_l_ear_code c1 WHERE c1.ROW_ID = c.MOTHER_EAR_ID AND c1.DELETED_FLAG = 0) motherEar,");
            pigSql.addMainSql(" (SELECT c2.EAR_BRAND FROM pp_l_ear_code c2 WHERE c2.ROW_ID = c.FATHER_EAR_ID AND c2.DELETED_FLAG = 0) fatherEar");
            pigSql.addMainSql(" FROM pp_l_pig p1");
            pigSql.addMainSql(" INNER JOIN pp_l_ear_code c");
            pigSql.addMainSql(" ON p1.EAR_CODE_ID = c.ROW_ID AND c.DELETED_FLAG = 0 and c.STATUS = 1");
            pigSql.addMainSql(" INNER JOIN pp_l_bill_leave e2");
            pigSql.addMainSql(" ON p1.ROW_ID = e2.PIG_ID AND e2.DELETED_FLAG = 0 AND e2.IS_MOVE_IN = 'N'");
            pigSql.addMainSql(" INNER JOIN cd_o_pork_pig cd");
            pigSql.addMainSql(" ON p1.MATERIAL_ID = cd.MATERIAL_ID AND cd.DELETED_FLAG = 0");
            pigSql.addMainSql(" WHERE p1.DELETED_FLAG = 0 AND p1.STATUS = 1 AND p1.SEED_FLAG = 'Y'");
            pigSql.addCondition(billId, " AND p1.LAST_BILL_ID = ?");
            pigSql.addCondition(pigType, " AND p1.SEX = ?");

            sqlMap.put("sql", pigSql.getCondition());
            List<Map<String, Object>> pigList = paramMapper.getObjectInfos(sqlMap);

            if (!pigList.isEmpty()) {
                checkMaterialIsExist(pigList, pigType);
            }
            return pigList;
        }

    }

    /**
     * @Description: 验证供应商是否建立
     * @author Administrator
     * @param supplierId
     * @throws Exception
     */
    public void checkSupplierIsExist(long supplierId) throws Exception {
        SqlCon supSql = new SqlCon();
        supSql.addMainSql("SELECT t1.ROW_ID rowId,t1.COMPANY_NAME companyName");
        supSql.addMainSql(" FROM HR_R_SRM t");
        supSql.addMainSql(" INNER JOIN HR_M_COMPANY t1");
        supSql.addMainSql(" ON t.CUSSUP_ID = t1.ROW_ID AND t1.DELETED_FLAG= 0");
        supSql.addMainSql(" WHERE t.DELETED_FLAG='0' AND t.CUSSUP_TYPE ='S'");
        supSql.addCondition(getFarmId(), " AND t.FARM_ID = ?");
        supSql.addCondition(supplierId, " AND t1.ROW_ID = ?");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", supSql.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        if (list.isEmpty()) {
            Thrower.throwException(BasicInfoException.SELL_SUP_NOT_CREATE, CacheUtil.getName(String.valueOf(supplierId), NameEnum.COMPANY_NAME));
        }
    }

    /**
     * @Description: 验证本场物料是否建立
     * @author Administrator
     * @param pigList
     * @param pigType
     * @throws Exception
     */
    public void checkMaterialIsExist(List<Map<String, Object>> pigList, String pigType) throws Exception {
        Long supplierId = Maps.getLongClass(pigList.get(0), "farmId");
        // 验证供应商是否建立
        checkSupplierIsExist(supplierId);

        // 验证物料主数据是否已在本场建立
        for (Map<String, Object> map : pigList) {
            if (PigConstants.PIG_TYPE_BOAR.equals(Maps.getString(map, "pigType"))) {
                SqlCon boarSql = new SqlCon();
                boarSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                boarSql.addCondition(Maps.getString(map, "breedId"), " AND BREED_ID = ?");
                boarSql.addCondition(Maps.getString(map, "strain"), " AND STRAIN = ?");
                BoarModel boarModel = getModel(boarMapper, boarSql);
                if (boarModel == null) {
                    Thrower.throwException(ProductionException.MATERIAL_IS_NOT_SAME, CacheUtil.getName(Maps.getString(map, "breedId"),
                            NameEnum.BREED_NAME), CacheUtil.getName(Maps.getString(map, "strain"), NameEnum.CODE_NAME, CodeEnum.STRAIN_NAME),
                            CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
                }
                map.put("materialId", boarModel.getMaterialId());
                map.put("materialIdName", CacheUtil.getName(String.valueOf(boarModel.getMaterialId()), NameEnum.MATERIAL_NAME));
            } else if (PigConstants.PIG_TYPE_SOW.equals(Maps.getString(map, "pigType"))) {
                SqlCon sowSql = new SqlCon();
                sowSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                sowSql.addCondition(Maps.getString(map, "breedId"), " AND BREED_ID = ?");
                sowSql.addCondition(Maps.getString(map, "strain"), " AND STRAIN = ?");
                SowModel sowModel = getModel(sowMapper, sowSql);
                if (sowModel == null) {
                    Thrower.throwException(ProductionException.MATERIAL_IS_NOT_SAME, CacheUtil.getName(Maps.getString(map, "breedId"),
                            NameEnum.BREED_NAME), CacheUtil.getName(Maps.getString(map, "strain"), NameEnum.CODE_NAME, CodeEnum.STRAIN_NAME),
                            CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
                }
                map.put("materialId", sowModel.getMaterialId());
                map.put("materialIdName", CacheUtil.getName(String.valueOf(sowModel.getMaterialId()), NameEnum.MATERIAL_NAME));
            } else if (PigConstants.PIG_TYPE_PORK.equals(Maps.getString(map, "pigType"))) {
                SqlCon porkSql = new SqlCon();
                porkSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                porkSql.addCondition(Maps.getString(map, "breedId"), " AND BREED_ID = ?");
                porkSql.addCondition(Maps.getString(map, "strain"), " AND STRAIN = ?");
                PorkPigModel porkModel = getModel(porkPigMapper, porkSql);
                if (porkModel == null) {
                    Thrower.throwException(ProductionException.MATERIAL_IS_NOT_SAME, CacheUtil.getName(Maps.getString(map, "breedId"),
                            NameEnum.BREED_NAME), CacheUtil.getName(Maps.getString(map, "strain"), NameEnum.CODE_NAME, CodeEnum.STRAIN_NAME),
                            CacheUtil.getName(Maps.getString(map, "pigType"), NameEnum.CODE_NAME, CodeEnum.PIG_TYPE_NAME));
                }
                map.put("materialId", porkModel.getMaterialId());
                map.put("materialIdName", CacheUtil.getName(String.valueOf(porkModel.getMaterialId()), NameEnum.MATERIAL_NAME));
            }
        }

        for (Map<String, Object> map : pigList) {
            String pigClass = Maps.getString(map, "pigClass");
            map.put("pigClassName", CacheUtil.getName(pigClass, NameEnum.PIG_CLASS_NAME));
            map.put("supplierId", supplierId);
            map.put("supplierIdName", CacheUtil.getName(String.valueOf(supplierId), NameEnum.COMPANY_NAME));
        }
    }

    @Override
    public void editPorkPigCountList(List<PorkCheckModel> porkCheckModels, String eventName, String billDate, String accountUser, String checkOrgan,
            String notes, String checkType) throws Exception {
        if (StringUtil.isBlank(billDate)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入单据日期");
        }
        if (StringUtil.isBlank(accountUser)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入盘点人");
        }
        if (StringUtil.isBlank(checkOrgan)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入核算单位");
        }
        if (porkCheckModels == null || porkCheckModels.size() <= 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "肉猪盘点数据为空");
        }
        /****************************************** 2017-6-6 肉猪盘点修改 *************************************************/
        Date checkDate = null;
        Date lastDate = TimeUtil.parse(TimeUtil.getLastDateOfMonth(billDate));
        if (TimeUtil.compareDate(TimeUtil.parseDate(billDate), lastDate, Calendar.DATE) == 0) {
            checkDate = lastDate;
        } else {
            checkDate = TimeUtil.getLastMothByCurrent(lastDate);
        }
        SqlCon getLastSqlCon = new SqlCon();
        getLastSqlCon.addCondition(TimeUtil.format(checkDate), " AND CHECK_DATE=? ");
        if ("1".equals(checkType)) {
            getLastSqlCon.addCondition("5", " AND CHECK_PIG_TYPE = ? ");
        } else {
            getLastSqlCon.addCondition("1,2,3,4", " AND CHECK_PIG_TYPE IN ? ", false, true);
        }
        getLastSqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
        Map<String, String> map = new HashMap<String, String>();
        map.put("condition", getLastSqlCon.getCondition());
        List<PorkCheckModel> porkCheckModelList = porkCheckMapper.searchListByCon(map);
        List<PorkCheckModel> porkCheckModelList_ = new ArrayList<PorkCheckModel>();
        if (porkCheckModelList != null && !porkCheckModelList.isEmpty()) {
            for (PorkCheckModel porkCheckModel : porkCheckModelList) {
                PorkCheckModel porkCheckModel_ = new PorkCheckModel();
                porkCheckModel_.setDeletedFlag("1");
                porkCheckModel_.setRowId(porkCheckModel.getRowId());
                porkCheckModelList_.add(porkCheckModel_);
            }
            porkCheckMapper.updates(porkCheckModelList_);
        }
        /****************************************** 2017-6-6 肉猪盘点修改 *************************************************/

        // 区分是编辑还是新增
        if (porkCheckModels.get(0).getBillId() == null) {
            BillModel billModel = new BillModel();
            String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
            billModel.setCompanyId(getCompanyId());
            billModel.setFarmId(getFarmId());
            billModel.setCreateId(getEmployeeId());
            billModel.setBusinessCode(businessCode);
            billModel.setCreateDate(new Date());
            billModel.setStatus(CommonConstants.STATUS);
            billModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            billModel.setBillDate(TimeUtil.parseDate(billDate));
            billModel.setEventCode(eventName);
            billModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            billModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            billModel.setNotes(notes);
            // 创建表单
            billModel.setBillDate(billModel.getBillDate());
            billMapper.insert(billModel);
            long billId = billModel.getRowId();
            for (PorkCheckModel porkCheckModel : porkCheckModels) {
                porkCheckModel.setCreateDate(new Date());
                porkCheckModel.setAccountUser(Long.valueOf(accountUser));
                porkCheckModel.setCheckOrgan(checkOrgan);
                porkCheckModel.setBillId(billId);
                porkCheckModel.setBillDate(TimeUtil.parseDate(billDate));
                porkCheckModel.setFarmId(getFarmId());
                porkCheckModel.setCompanyId(getCompanyId());
                porkCheckModel.setCheckDate(checkDate);
            }
            porkCheckMapper.inserts(porkCheckModels);
        } else {
            for (PorkCheckModel porkCheckModel : porkCheckModels) {
                porkCheckModel.setAccountUser(Long.valueOf(accountUser));
                porkCheckModel.setCheckOrgan(checkOrgan);
                porkCheckModel.setFarmId(getFarmId());
                porkCheckModel.setCompanyId(getCompanyId());
            }
            porkCheckMapper.updates(porkCheckModels);
        }

    }

    @Override
    public List<Map<String, Object>> searchLivestockByProduceToList(String farmId) throws Exception {
        SearchValidPigModel searchValidPigModel = new SearchValidPigModel();
        List<Map<String, Object>> validPigModelMaps = new ArrayList<Map<String, Object>>();
        String companyMark = companyMapper.searchById(farmId == null ? getFarmId() : Long.valueOf(farmId)).getCompanyMark();
        if (StringUtil.isNonBlank(companyMark)) {
            searchValidPigModel.setCompanyMark(companyMark);
        }
        List<ValidPigModel> validPigModels = sweachValidPigMapper.searchLivestockByProduceToList(searchValidPigModel);
        Map<String, Object> maps = new LinkedHashMap<String, Object>();
        int SCGZpigQty = 0;
        int HBGZpigQty = 0;
        int HBMZpigQty = 0;
        int DNZZpigQty = 0;
        int BYZpigQty = 0;
        int YDZpigQty = 0;
        int totalGZpigQty = 0;
        int totalMZpigQty = 0;
        int totalRZpigQty = 0;
        int CCMZpigQty = 0;
        int BRZZpigQty = 0;
        int LZGZpigQty = 0;
        int LZMXpigQty = 0;
        int TTZpigQty = 0;
        if (validPigModels != null && !validPigModels.isEmpty()) {
            // 生产公猪
            for (ValidPigModel validPigModel : validPigModels) {
                if (Long.valueOf(PigConstants.PIG_CLASS_SCGZ).equals(validPigModel.getPigClass())) {
                    SCGZpigQty += validPigModel.getPigQty();
                    // 后备公猪
                } else if (Long.valueOf(PigConstants.PIG_CLASS_HBGZ).equals(validPigModel.getPigClass())) {
                    HBGZpigQty += validPigModel.getPigQty();
                    // 经产母猪
                } else if (Long.valueOf(PigConstants.PIG_CLASS_FQ).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_FQ1)
                        .equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_LC).equals(validPigModel.getPigClass()) || Long
                                .valueOf(PigConstants.PIG_CLASS_KH).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_RS)
                                        .equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BR).equals(validPigModel
                                                .getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_SOW_DN).equals(validPigModel.getPigClass())) {
                    CCMZpigQty += validPigModel.getPigQty();
                    // 后备母猪
                } else if (Long.valueOf(PigConstants.PIG_CLASS_HBMZ).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_HBDP)
                        .equals(validPigModel.getPigClass())) {
                    HBMZpigQty += validPigModel.getPigQty();
                    // 哺乳仔猪
                } else if ((Long.valueOf(PigConstants.PIG_CLASS_BRJZ).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BRRZ)
                        .equals(validPigModel.getPigClass())) && PigConstants.SEED_TYPE_NO.equals(validPigModel.getSeedFlag())) {
                    BRZZpigQty += validPigModel.getPigQty();
                    // 断奶仔猪
                } else if (Long.valueOf(PigConstants.PIG_CLASS_PORK_DN).equals(validPigModel.getPigClass())) {
                    DNZZpigQty += validPigModel.getPigQty();
                    // 保育猪
                } else if (Long.valueOf(PigConstants.PIG_CLASS_BF).equals(validPigModel.getPigClass())) {
                    BYZpigQty += validPigModel.getPigQty();
                    // 育肥猪
                } else if (Long.valueOf(PigConstants.PIG_CLASS_YH).equals(validPigModel.getPigClass())) {
                    YDZpigQty += validPigModel.getPigQty();
                    // 留种公
                } else if ((Long.valueOf(PigConstants.PIG_CLASS_BRJZ).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BRRZ)
                        .equals(validPigModel.getPigClass())) && PigConstants.SEED_TYPE_YES.equals(validPigModel.getSeedFlag())
                        && PigConstants.PIG_SEX_MALE.equals(validPigModel.getSex())) {
                    LZGZpigQty += validPigModel.getPigQty();
                    // 留种母
                } else if ((Long.valueOf(PigConstants.PIG_CLASS_BRJZ).equals(validPigModel.getPigClass()) || Long.valueOf(PigConstants.PIG_CLASS_BRRZ)
                        .equals(validPigModel.getPigClass())) && PigConstants.SEED_TYPE_YES.equals(validPigModel.getSeedFlag())
                        && PigConstants.PIG_SEX_FEMALE.equals(validPigModel.getSex())) {
                    LZMXpigQty += validPigModel.getPigQty();
                } else if (Long.valueOf(PigConstants.PIG_CLASS_TT).equals(validPigModel.getPigClass())) {
                    TTZpigQty += validPigModel.getPigQty();
                }
            }
        }
        maps.put("SCGZpigQty", SCGZpigQty);
        maps.put("HBGZpigQty", HBGZpigQty);
        totalGZpigQty = SCGZpigQty + HBGZpigQty;
        maps.put("totalGZPigQty", totalGZpigQty);
        maps.put("CCMZpigQty", CCMZpigQty);
        maps.put("HBMZpigQty", HBMZpigQty);
        totalMZpigQty = CCMZpigQty + HBMZpigQty;
        maps.put("totalMZPigQty", totalMZpigQty);
        maps.put("BRZZpigQty", BRZZpigQty);
        maps.put("DNZZpigQty", DNZZpigQty);
        maps.put("BYZpigQty", BYZpigQty);
        maps.put("YFZpigQty", YDZpigQty);
        maps.put("LZGZpigQty", LZGZpigQty);
        maps.put("LZMXpigQty", LZMXpigQty);
        maps.put("TTZpigQty", TTZpigQty);
        totalRZpigQty = BRZZpigQty + DNZZpigQty + BYZpigQty + YDZpigQty + LZGZpigQty + LZMXpigQty + TTZpigQty;
        maps.put("totalRZPigQty", totalRZpigQty);
        validPigModelMaps.add(maps);
        return validPigModelMaps;
    }

    @Override
    public List<Map<String, Object>> searchProduceChangeToList(String farmId, String dateType) throws Exception {
        List<Map<String, Object>> totalMaps = new ArrayList<Map<String, Object>>();
        String startDate = null;
        String endDate = null;
        if (StringUtil.isNonBlank(dateType)) {
            // 本周
            if (dateType.equals("1")) {
                startDate = TimeUtil.getFirstDayOfWeek(TimeUtil.format(new Date()), TimeUtil.DATE_FORMAT);
                endDate = TimeUtil.getLastDayOfWeek(TimeUtil.format(new Date()), TimeUtil.DATE_FORMAT);
                // 本月
            } else if (dateType.equals("2")) {
                startDate = TimeUtil.getFirstDateOfMonth(TimeUtil.format(new Date()));
                endDate = TimeUtil.getLastDateOfMonth(TimeUtil.format(new Date()));
                // 本季度
            } else if (dateType.equals("3")) {
                startDate = TimeUtil.getFirstDayOfQuarter(TimeUtil.format(new Date()), TimeUtil.DATE_FORMAT);
                endDate = TimeUtil.getLastDayOfQuarter(TimeUtil.format(new Date()), TimeUtil.DATE_FORMAT);
                // 本年
            } else if (dateType.equals("4")) {
                startDate = TimeUtil.getYearFirst(TimeUtil.format(new Date()), TimeUtil.DATE_FORMAT);
                endDate = TimeUtil.getYearLast(TimeUtil.format(new Date()), TimeUtil.DATE_FORMAT);
            }
        }
        String currentStartDate = TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT);
        String currentEndDate = TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT);
        long pigQty = 0;
        String companyMark = companyMapper.searchById(farmId == null ? getFarmId() : Long.valueOf(farmId)).getCompanyMark();
        List<Map<String, Object>> breedMaps = sweachValidPigMapper.searchBreedChangeToList(companyMark, startDate, endDate);
        Map<String, Object> breedMap = new HashMap<String, Object>();
        long HBNum = 0;
        long FQNum = 0;
        long LCNum = 0;
        long KHNum = 0;
        long DNNum = 0;
        if (breedMaps != null && !breedMaps.isEmpty()) {
            for (Map<String, Object> map : breedMaps) {
                pigQty = (long) map.get("pigQty");
                if (map.get("lastStatus") != null) {
                    int lastStatus = (int) map.get("lastStatus");
                    // 后备配
                    if (lastStatus == 4) {
                        HBNum = pigQty;
                        // 返情配
                    } else if (lastStatus == 6) {
                        FQNum = pigQty;
                        // 流产配
                    } else if (lastStatus == 7) {
                        LCNum = pigQty;
                        // 空怀配
                    } else if (lastStatus == 8) {
                        KHNum = pigQty;
                        // 断奶配
                    } else if (lastStatus == 11) {
                        DNNum = pigQty;
                    }
                }
            }
        }
        breedMap.put("HBbreed", HBNum);
        breedMap.put("FQbreed", FQNum);
        breedMap.put("LCbreed", LCNum);
        breedMap.put("KHbreed", KHNum);
        breedMap.put("DNbreed", DNNum);

        List<Map<String, Object>> currentBreedMaps = sweachValidPigMapper.searchBreedChangeToList(companyMark, currentStartDate, currentEndDate);
        long todayHBNum = 0;
        long todayFQNum = 0;
        long todayLCNum = 0;
        long todayKHNum = 0;
        long todayDNNum = 0;
        if (currentBreedMaps != null && !currentBreedMaps.isEmpty()) {
            for (Map<String, Object> map : currentBreedMaps) {
                pigQty = (long) map.get("pigQty");
                if (map.get("lastStatus") != null) {
                    int lastStatus = (int) map.get("lastStatus");
                    // 后备配
                    if (lastStatus == 4) {
                        todayHBNum = pigQty;
                        // 返情配
                    } else if (lastStatus == 6) {
                        todayFQNum = pigQty;
                        // 流产配
                    } else if (lastStatus == 7) {
                        todayLCNum = pigQty;
                        // 空怀配
                    } else if (lastStatus == 8) {
                        todayKHNum = pigQty;
                        // 断奶配
                    } else if (lastStatus == 11) {
                        todayDNNum = pigQty;
                    }
                }
            }
        }
        breedMap.put("todayHBbreed", todayHBNum);
        breedMap.put("todayFQbreed", todayFQNum);
        breedMap.put("todayLCbreed", todayLCNum);
        breedMap.put("todayKHbreed", todayKHNum);
        breedMap.put("todayDNbreed", todayDNNum);

        breedMap.put("changeType", "breed");
        List<Map<String, Object>> pregnancyCheckMaps = sweachValidPigMapper.searchPregnancyCheckChangeToList(companyMark, startDate, endDate);
        Map<String, Object> pregnancyMap = new HashMap<String, Object>();
        long YXPNum = 0;
        long FQPNum = 0;
        long LCPNum = 0;
        long KHPNum = 0;
        if (pregnancyCheckMaps != null && !pregnancyCheckMaps.isEmpty()) {
            for (Map<String, Object> map : pregnancyCheckMaps) {
                pigQty = (long) map.get("pigQty");
                if (map.get("pregnancyResult") != null) {
                    String pregnancyResult = (String) map.get("pregnancyResult");
                    // 阴性
                    if ("2".equals(pregnancyResult)) {
                        YXPNum = pigQty;
                        // 返情
                    } else if ("4".equals(pregnancyResult)) {
                        FQPNum = pigQty;
                        // 流产
                    } else if ("5".equals(pregnancyResult)) {
                        LCPNum = pigQty;
                        // 假孕空怀
                    } else if ("6".equals(pregnancyResult)) {
                        KHPNum = pigQty;
                    }
                }
            }
        }
        pregnancyMap.put("YXpregnancy", YXPNum);
        // 返情
        pregnancyMap.put("FQpregnancy", FQPNum);
        // 流产
        pregnancyMap.put("LCpregnancy", LCPNum);
        // 假孕空怀
        pregnancyMap.put("KHpregnancy", KHPNum);

        List<Map<String, Object>> currentPregnancyCheckMaps = sweachValidPigMapper.searchPregnancyCheckChangeToList(companyMark, currentStartDate,
                currentEndDate);
        long todayYXPNum = 0;
        long todayFQPNum = 0;
        long todayLCPNum = 0;
        long todayKHPNum = 0;
        if (currentPregnancyCheckMaps != null && !currentPregnancyCheckMaps.isEmpty()) {
            for (Map<String, Object> map : currentPregnancyCheckMaps) {
                pigQty = (long) map.get("pigQty");
                if (map.get("pregnancyResult") != null) {
                    String pregnancyResult = (String) map.get("pregnancyResult");
                    // 阴性
                    if ("2".equals(pregnancyResult)) {
                        todayYXPNum = pigQty;
                        // 返情
                    } else if ("4".equals(pregnancyResult)) {
                        todayFQPNum = pigQty;
                        // 流产
                    } else if ("5".equals(pregnancyResult)) {
                        todayLCPNum = pigQty;
                        // 假孕空怀
                    } else if ("6".equals(pregnancyResult)) {
                        todayKHPNum = pigQty;
                    }
                }
            }
        }
        pregnancyMap.put("todayYXpregnancy", todayYXPNum);
        // 返情
        pregnancyMap.put("todayFQpregnancy", todayFQPNum);
        // 流产
        pregnancyMap.put("todayLCpregnancy", todayLCPNum);
        // 假孕空怀
        pregnancyMap.put("todayKHpregnancy", todayKHPNum);

        pregnancyMap.put("changeType", "pregnancy");
        List<Map<String, Object>> deliveryMaps = sweachValidPigMapper.searchDeliveryChangeToList(companyMark, startDate, endDate);
        Map<String, Object> deliveryMap = new HashMap<String, Object>();
        for (Map<String, Object> map : deliveryMaps) {
            deliveryMap.put("healthyNum", map.get("healthyNum"));
            deliveryMap.put("weakNum", map.get("weakNum"));
            deliveryMap.put("stillbirthNum", map.get("stillbirthNum"));
            deliveryMap.put("mutantNum", map.get("mutantNum"));
            deliveryMap.put("mummyNum", map.get("mummyNum"));
            deliveryMap.put("blackNum", map.get("blackNum"));
            deliveryMap.put("aliveLitterY", map.get("aliveLitterY"));
            deliveryMap.put("aliveLitterX", map.get("aliveLitterX"));
            deliveryMap.put("totalQty", map.get("totalQty"));
            deliveryMap.put("avgWeight", map.get("avgWeight"));
            deliveryMap.put("avgPigQty", map.get("avgPigQty"));
        }
        List<Map<String, Object>> currnetDeliveryMaps = sweachValidPigMapper.searchDeliveryChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currnetDeliveryMaps) {
            deliveryMap.put("todayHealthyNum", map.get("healthyNum"));
            deliveryMap.put("todayWeakNum", map.get("weakNum"));
            deliveryMap.put("todayStillbirthNum", map.get("stillbirthNum"));
            deliveryMap.put("todayMutantNum", map.get("mutantNum"));
            deliveryMap.put("todayMummyNum", map.get("mummyNum"));
            deliveryMap.put("todayBlackNum", map.get("blackNum"));
            deliveryMap.put("todayAliveLitterY", map.get("aliveLitterY"));
            deliveryMap.put("todayAliveLitterX", map.get("aliveLitterX"));
            deliveryMap.put("todayTotalQty", map.get("totalQty"));
            deliveryMap.put("todayAvgWeight", map.get("avgWeight"));
            deliveryMap.put("todayAvgPigQty", map.get("avgPigQty"));
        }
        deliveryMap.put("changeType", "delivery");
        List<Map<String, Object>> weanMaps = sweachValidPigMapper.searchWeanChangeToList(companyMark, startDate, endDate);
        Map<String, Object> weanMap = new HashMap<String, Object>();
        for (Map<String, Object> map : weanMaps) {
            weanMap.put("weanNum", map.get("weanNum"));
            weanMap.put("weanWeight", map.get("weanWeight"));
            weanMap.put("avgPigQty", map.get("avgPigQty"));
            weanMap.put("avgWeight", map.get("avgWeight"));
        }
        List<Map<String, Object>> currentWeanMaps = sweachValidPigMapper.searchWeanChangeToList(companyMark, currentStartDate, currentEndDate);
        for (Map<String, Object> map : currentWeanMaps) {
            weanMap.put("todayWeanNum", map.get("weanNum"));
            weanMap.put("todayWeanWeight", map.get("weanWeight"));
            weanMap.put("todayAvgPigQty", map.get("avgPigQty"));
            weanMap.put("todayAvgWeight", map.get("avgWeight"));
        }
        weanMap.put("changeType", "wean");
        List<Map<String, Object>> toChildCareMaps = sweachValidPigMapper.searchToChildCareChangeToList(companyMark, startDate, endDate);
        Map<String, Object> toChildCareMap = new HashMap<String, Object>();
        for (Map<String, Object> map : toChildCareMaps) {
            toChildCareMap.put("nestQty", map.get("nestQty"));
            toChildCareMap.put("childQty", map.get("childQty"));
            toChildCareMap.put("childWeight", map.get("childWeight"));
            toChildCareMap.put("avgChildWeight", map.get("avgChildWeight"));
            toChildCareMap.put("avgDateage", map.get("avgDateage"));
        }
        List<Map<String, Object>> currentToChildCareMaps = sweachValidPigMapper.searchToChildCareChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentToChildCareMaps) {
            toChildCareMap.put("todayNestQty", map.get("nestQty"));
            toChildCareMap.put("todayChildQty", map.get("childQty"));
            toChildCareMap.put("todayChildWeight", map.get("childWeight"));
            toChildCareMap.put("todayAvgChildWeight", map.get("avgChildWeight"));
            toChildCareMap.put("todayAvgDateage", map.get("avgDateage"));
        }
        toChildCareMap.put("changeType", "toChildCare");
        List<Map<String, Object>> toFattenMaps = sweachValidPigMapper.searchToFattenChangeToList(companyMark, startDate, endDate);
        Map<String, Object> toFattenMap = new HashMap<String, Object>();
        for (Map<String, Object> map : toFattenMaps) {
            toFattenMap.put("childQty", map.get("childQty"));
            toFattenMap.put("childWeight", map.get("childWeight"));
            toFattenMap.put("avgWeight", map.get("avgWeight"));
            toFattenMap.put("dateage", map.get("dateage"));
        }
        List<Map<String, Object>> currentToFattenMaps = sweachValidPigMapper.searchToFattenChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentToFattenMaps) {
            toFattenMap.put("todayChildQty", map.get("childQty"));
            toFattenMap.put("todayChildWeight", map.get("childWeight"));
            toFattenMap.put("todayAvgWeight", map.get("avgWeight"));
            toFattenMap.put("todayDateage", map.get("dateage"));
        }
        toFattenMap.put("changeType", "toFatten");
        List<Map<String, Object>> breedPigDieMaps = sweachValidPigMapper.searchBreedPigDieChangeToList(companyMark, startDate, endDate);
        Map<String, Object> breedPigDieMap = new HashMap<String, Object>();
        for (Map<String, Object> map : breedPigDieMaps) {
            breedPigDieMap.put("gttHbPigQty", map.get("gttHbPigQty"));
            breedPigDieMap.put("gttScPigQty", map.get("gttScPigQty"));
            breedPigDieMap.put("gswHbpigQty", map.get("gswHbpigQty"));
            breedPigDieMap.put("gswScPigQty", map.get("gswScPigQty"));
            breedPigDieMap.put("mttHbPigQty", map.get("mttHbPigQty"));
            breedPigDieMap.put("mttScPigQty", map.get("mttScPigQty"));
            breedPigDieMap.put("mswHbPigQty", map.get("mswHbPigQty"));
            breedPigDieMap.put("mswScPigQty", map.get("mswScPigQty"));
        }
        List<Map<String, Object>> currentBreedPigDieMaps = sweachValidPigMapper.searchBreedPigDieChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentBreedPigDieMaps) {
            breedPigDieMap.put("todayGttHbPigQty", map.get("gttHbPigQty"));
            breedPigDieMap.put("todayGttScPigQty", map.get("gttScPigQty"));
            breedPigDieMap.put("todayGswHbpigQty", map.get("gswHbpigQty"));
            breedPigDieMap.put("todayGswScPigQty", map.get("gswScPigQty"));
            breedPigDieMap.put("todayMttHbPigQty", map.get("mttHbPigQty"));
            breedPigDieMap.put("todayMttScPigQty", map.get("mttScPigQty"));
            breedPigDieMap.put("todayMswHbPigQty", map.get("mswHbPigQty"));
            breedPigDieMap.put("todayMswScPigQty", map.get("mswScPigQty"));
        }
        breedPigDieMap.put("changeType", "breedPigDie");
        List<Map<String, Object>> childPigDieMaps = sweachValidPigMapper.searchChildPigDieChangeToList(companyMark, startDate, endDate);
        Map<String, Object> childPigDieMap = new HashMap<String, Object>();
        for (Map<String, Object> map : childPigDieMaps) {
            childPigDieMap.put("pigQty", map.get("pigQty"));
        }
        List<Map<String, Object>> currentChildPigDieMaps = sweachValidPigMapper.searchChildPigDieChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentChildPigDieMaps) {
            childPigDieMap.put("todayPigQty", map.get("pigQty"));
        }
        childPigDieMap.put("changeType", "childPigDie");
        List<Map<String, Object>> childCareDieMaps = sweachValidPigMapper.searchChildCareDieChangeToList(companyMark, startDate, endDate);
        Map<String, Object> childCareDieMap = new HashMap<String, Object>();
        for (Map<String, Object> map : childCareDieMaps) {
            childCareDieMap.put("pigQty", map.get("pigQty"));
        }
        List<Map<String, Object>> currentChildCareDieMaps = sweachValidPigMapper.searchChildCareDieChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentChildCareDieMaps) {
            childCareDieMap.put("todayPigQty", map.get("pigQty"));
        }
        childCareDieMap.put("changeType", "childCareDie");
        List<Map<String, Object>> fattenDieMaps = sweachValidPigMapper.searchFattenDieChangeToList(companyMark, startDate, endDate);
        Map<String, Object> fattenDieMap = new HashMap<String, Object>();
        for (Map<String, Object> map : fattenDieMaps) {
            fattenDieMap.put("pigQty", map.get("pigQty"));
        }
        List<Map<String, Object>> currentFattenDieMaps = sweachValidPigMapper.searchFattenDieChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentFattenDieMaps) {
            fattenDieMap.put("todayPigQty", map.get("pigQty"));
        }
        fattenDieMap.put("changeType", "fattenDie");
        List<Map<String, Object>> childCareSaleMaps = sweachValidPigMapper.searchChildCareSaleChangeToList(companyMark, startDate, endDate);
        Map<String, Object> childCareSaleMap = new HashMap<String, Object>();
        for (Map<String, Object> map : childCareSaleMaps) {
            childCareSaleMap.put("saleNum", map.get("saleNum"));
            childCareSaleMap.put("totalWeight", map.get("totalWeight"));
            childCareSaleMap.put("avgWeight", map.get("avgWeight"));
            childCareSaleMap.put("totalPrice", map.get("totalPrice"));
        }
        List<Map<String, Object>> currentChildCareSaleMaps = sweachValidPigMapper.searchChildCareSaleChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentChildCareSaleMaps) {
            childCareSaleMap.put("todaySaleNum", map.get("saleNum"));
            childCareSaleMap.put("todayTotalWeight", map.get("totalWeight"));
            childCareSaleMap.put("todayAvgWeight", map.get("avgWeight"));
            childCareSaleMap.put("todayTotalPrice", map.get("totalPrice"));
        }
        childCareSaleMap.put("changeType", "childCareSale");
        List<Map<String, Object>> fattenSaleMaps = sweachValidPigMapper.searchFattenSaleChangeToList(companyMark, startDate, endDate);
        Map<String, Object> fattenSaleMap = new HashMap<String, Object>();
        for (Map<String, Object> map : fattenSaleMaps) {
            fattenSaleMap.put("saleNum", map.get("saleNum"));
            fattenSaleMap.put("totalWeight", map.get("totalWeight"));
            fattenSaleMap.put("avgWeight", map.get("avgWeight"));
            fattenSaleMap.put("totalPrice", map.get("totalPrice"));
        }
        List<Map<String, Object>> currentFattenSaleMaps = sweachValidPigMapper.searchFattenSaleChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentFattenSaleMaps) {
            fattenSaleMap.put("todaySaleNum", map.get("saleNum"));
            fattenSaleMap.put("todayTotalWeight", map.get("totalWeight"));
            fattenSaleMap.put("todayAvgWeight", map.get("avgWeight"));
            fattenSaleMap.put("todayTotalPrice", map.get("totalPrice"));
        }
        fattenSaleMap.put("changeType", "fattenSale");
        List<Map<String, Object>> defectsSaleMaps = sweachValidPigMapper.searchDefectsSaleChangeToList(companyMark, startDate, endDate);
        Map<String, Object> defectsSaleMap = new HashMap<String, Object>();
        for (Map<String, Object> map : defectsSaleMaps) {
            defectsSaleMap.put("saleNum", map.get("saleNum"));
            defectsSaleMap.put("totalWeight", map.get("totalWeight"));
            defectsSaleMap.put("totalPrice", map.get("totalPrice"));
        }
        List<Map<String, Object>> currentDefectsSaleMaps = sweachValidPigMapper.searchDefectsSaleChangeToList(companyMark, currentStartDate,
                currentEndDate);
        for (Map<String, Object> map : currentDefectsSaleMaps) {
            defectsSaleMap.put("todaySaleNum", map.get("saleNum"));
            defectsSaleMap.put("todayTotalWeight", map.get("totalWeight"));
            defectsSaleMap.put("todayTotalPrice", map.get("totalPrice"));
        }
        defectsSaleMap.put("changeType", "defectsSale");
        totalMaps.add(breedMap);
        totalMaps.add(pregnancyMap);
        totalMaps.add(deliveryMap);
        totalMaps.add(weanMap);
        totalMaps.add(toChildCareMap);
        totalMaps.add(toFattenMap);
        totalMaps.add(breedPigDieMap);
        totalMaps.add(childPigDieMap);
        totalMaps.add(childCareDieMap);
        totalMaps.add(fattenDieMap);
        totalMaps.add(childCareSaleMap);
        totalMaps.add(fattenSaleMap);
        totalMaps.add(defectsSaleMap);

        return totalMaps;
    }

    @Override
    public List<Map<String, Object>> searchPerformanceComparisonToList(String farmId, String dateContrast) throws Exception {
        String date[] = dateContrast.split("-");
        String year = date[0];
        String month = date[1];
        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql(
                "SELECT T1.CODE_NAME,t0.ASSESS_CONTENT,IFNULL(T0.ASSESS_INDEX,0) ASSESS_INDEX FROM cd_m_month_performance T0,cd_l_code_list T1 WHERE t1.CODE_VALUE = t0.ASSESS_CONTENT ");
        sqlCon.addMainSql("AND t1.TYPE_CODE = 'ASSESS_CONTENT' ");
        sqlCon.addMainSql("AND T0.DELETED_FLAG = '0' AND T1.DELETED_FLAG = '0' ");
        if (farmId == null) {
            sqlCon.addCondition(getFarmId(), " AND T0.farm_id = ? ");
        } else {
            sqlCon.addCondition(farmId, " AND T0.farm_id = ? ");
        }
        sqlCon.addCondition(year, " AND T0.year= ? ");
        sqlCon.addCondition(month, " AND T0.month= ? ");
        sqlCon.addMainSql("group by T0.ASSESS_CONTENT ");
        sqlCon.addMainSql("order by T0.ASSESS_CONTENT ");
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);
    }

    @Override
    public void editSaleSemenList(BillModel billModel, String saleSemenList, String customerId) throws Exception {
        if (billModel.getBillDate() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入单据日期");
        }
        if (customerId == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请输入客户");
        }
        // 业务编码
        String businessCode = ParamUtil.getBCode(EventConstants.SALE_SEMEN, getEmployeeId(), getCompanyId(), getFarmId());
        billModel.setCompanyId(getCompanyId());
        billModel.setFarmId(getFarmId());
        billModel.setCreateId(getEmployeeId());
        billModel.setBusinessCode(businessCode);
        billModel.setEventCode(EventConstants.SALE_SEMEN);
        // 创建表单
        billModel.setBillDate(billModel.getBillDate());
        billMapper.insert(billModel);
        long billId = billModel.getRowId();

        List<HashMap> semenSaleMapList = getJsonList(saleSemenList, HashMap.class);
        List<PigEventHisModel> pigEventHisModels = new ArrayList<PigEventHisModel>();
        if (semenSaleMapList == null || semenSaleMapList.isEmpty()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "后台-未添加明细不能提交！");
        } else {
            List<Map<String, Object>> saleSemenMaps = getMapList(saleSemenList);
            for (Map<String, Object> map : saleSemenMaps) {
                int semenNum = Maps.getInt(map, "semenNum");
                Integer saleNum = Maps.getIntClass(map, "saleNum");
                String worker = Maps.getString(map, "worker");
                String validDate = Maps.getString(map, "validDate");
                String saleDate = Maps.getString(map, "saleDate");
                String semenDate = Maps.getString(map, "semenDate");
                if (saleNum == null || saleNum <= 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售数量不能为零");
                }
                if (saleNum > semenNum) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售数量不能大于库存数量");
                }
                if (StringUtil.isBlank(worker)) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请填写技术员");
                }
                if (TimeUtil.compareDate(saleDate, validDate, Calendar.DATE) > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售日期必须小于有效日期");
                }
                if (TimeUtil.compareDate(saleDate, semenDate, Calendar.DATE) < 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售日期必须大于采精日期");
                }
            }

            Date currentDate = new Date();

            List<SemenSaleModel> semenSaleModelList = new ArrayList<SemenSaleModel>();
            List<SemenSaleDetailModel> semenSaleDetailModelList = new ArrayList<SemenSaleDetailModel>();
            List<SpermInfoModel> updateSpermModelList = new ArrayList<SpermInfoModel>();
            for (Map<String, Object> semenSaleMap : semenSaleMapList) {
                SemenSaleModel semenSaleModel = new SemenSaleModel();
                semenSaleModel.setStatus(CommonConstants.STATUS);
                semenSaleModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                semenSaleModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                semenSaleModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                semenSaleModel.setNotes(Maps.getString(semenSaleMap, "notes"));
                semenSaleModel.setFarmId(getFarmId());
                semenSaleModel.setCompanyId(getCompanyId());
                semenSaleModel.setBillId(billId);
                semenSaleModel.setLineNumber(Maps.getLong(semenSaleMap, "lineNumber"));
                semenSaleModel.setIsEntry(SpermConstants.SPERM_INFO_IS_ENTRY_UNENTRY);
                semenSaleModel.setCustomerId(Long.valueOf(customerId));
                semenSaleModel.setSemenId(Maps.getLong(semenSaleMap, "semenId"));
                semenSaleModel.setWarehouseId(Maps.getLong(semenSaleMap, "warehouseId"));
                semenSaleModel.setSaleNum(Maps.getLong(semenSaleMap, "saleNum"));
                semenSaleModel.setValidDate(Maps.getTimestamp(semenSaleMap, "validDate"));
                semenSaleModel.setSaleDate(Maps.getTimestamp(semenSaleMap, "saleDate"));
                semenSaleModel.setBoarId(Maps.getLongClass(semenSaleMap, "boarId"));
                semenSaleModel.setWorker(Maps.getLong(semenSaleMap, "worker"));
                semenSaleModel.setCreateId(getEmployeeId());
                semenSaleModel.setCreateDate(currentDate);
                semenSaleMapper.insert(semenSaleModel);

                List<HashMap> spermList = Maps.getList(semenSaleMap, "spermList");

                for (Map<String, Object> spermMap : spermList) {
                    SemenSaleDetailModel semenSaleDetailModel = new SemenSaleDetailModel();
                    semenSaleDetailModel.setStatus(CommonConstants.STATUS);
                    semenSaleDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    semenSaleDetailModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    semenSaleDetailModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    semenSaleDetailModel.setNotes(Maps.getString(semenSaleMap, "notes"));
                    semenSaleDetailModel.setSemenSaleRowId(semenSaleModel.getRowId());
                    semenSaleDetailModel.setSpermId(Maps.getLong(spermMap, "spermId"));
                    semenSaleDetailModelList.add(semenSaleDetailModel);

                    SqlCon spermSqlCon = new SqlCon();
                    spermSqlCon.addCondition(SpermConstants.SPERM_INFO_STATUS_UNUSE, " AND STATUS = ?");
                    spermSqlCon.addCondition(Maps.getLong(spermMap, "spermId"), " AND ROW_ID = ? FOR UPDATE");
                    List<SpermInfoModel> spermInfoModelList = getList(spermInfoMapper, spermSqlCon);
                    if (spermInfoModelList.isEmpty()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLong(semenSaleMap, "lineNumber")
                                + "行，精液信息发生改变，请重新选择精液（数量）！");
                    }
                    SpermInfoModel spermInfoModel = spermInfoModelList.get(0);
                    spermInfoModel.setStatus(SpermConstants.SPERM_INFO_STATUS_SALE);
                    spermInfoModel.setIsSale(SpermConstants.SPERM_INFO_IS_SALE_SALE);
                    updateSpermModelList.add(spermInfoModel);
                }
                // SqlCon sqlCon = new SqlCon();
                // sqlCon.addMainSql(" UPDATE PP_L_SPERM_INFO SET IS_SALE=1,STATUS=3 WHERE IS_SALE=0 ");
                // sqlCon.addCondition(semenSaleModel.getSemenId(), " AND SEMEN_ID=? ");
                // sqlCon.addCondition(semenSaleModel.getSaleNum(), " LIMIT ? ");
                // setSql(spermInfoMapper, sqlCon);
                // 采精就在平台内，存在真实公猪BoarId!=null,采精不在平台内，不存在真实公猪BoarId==null
                if (semenSaleModel.getBoarId() != null) {
                    PigEventHisModel pigEventHisModel = new PigEventHisModel();
                    pigEventHisModel.setStatus(CommonConstants.STATUS);
                    pigEventHisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    pigEventHisModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    pigEventHisModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    pigEventHisModel.setCompanyId(getCompanyId());
                    pigEventHisModel.setFarmId(getFarmId());
                    pigEventHisModel.setBillId(billId);
                    pigEventHisModel.setCreateDate(currentDate);
                    pigEventHisModel.setCreateId(getEmployeeId());
                    pigEventHisModel.setEnterDate(null);
                    pigEventHisModel.setEventName(EventConstants.SALE_SEMEN);
                    pigEventHisModel.setHouseId(null);
                    pigEventHisModel.setLastEventDate(billModel.getBillDate());
                    pigEventHisModel.setNotes(semenSaleModel.getNotes());
                    pigEventHisModel.setPigClass(null);
                    pigEventHisModel.setPigId(semenSaleModel.getBoarId());
                    pigEventHisModel.setPigpenId(null);
                    pigEventHisModel.setPigType(null);
                    pigEventHisModel.setSex(null);
                    pigEventHisModel.setSwineryId(null);
                    pigEventHisModel.setTableName("PP_L_BILL_SEMEN_SALE");
                    pigEventHisModel.setTableRowId(semenSaleModel.getRowId());
                    pigEventHisModel.setWorker(semenSaleModel.getWorker());
                    pigEventHisModel.setParity(null);
                    pigEventHisModel.setLineNumber(semenSaleModel.getLineNumber());
                    pigEventHisModel.setEarBrand(null);
                    Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", customerId);
                    pigEventHisModel.setEventNotes("精液批号：【" + Maps.getString(semenSaleMap, "semenBatchNo") + "】已经被销售【" + Maps.getLong(semenSaleMap,
                            "saleNum") + "】份至【" + Maps.getString(companyInfoMap, "SORT_NAME") + "】");
                    pigEventHisModels.add(pigEventHisModel);
                }
                // 精液销售与公猪无关
                // this.insertPigEventRelates(semenSaleModel.getBoarId());
            }

            if (semenSaleDetailModelList != null && semenSaleDetailModelList.size() > 0) {
                semenSaleDetailMapper.inserts(semenSaleDetailModelList);
            }
            if (updateSpermModelList != null && updateSpermModelList.size() > 0) {
                spermInfoMapper.updates(updateSpermModelList);
            }
            if (pigEventHisModels != null && pigEventHisModels.size() > 0) {
                pigEventHisMapper.inserts(pigEventHisModels);
            }
        }
    }

    @Override
    public List<Map<String, String>> editSemenBill() throws Exception {
        SqlCon semenSql = new SqlCon();
        semenSql.addMainSql("SELECT T1.ROW_ID billId,T1.BILL_DATE billDate,T1.BUSINESS_CODE billCode,T1.FARM_ID farmId");
        semenSql.addMainSql(",(SELECT SUM(SALE_NUM) FROM PP_L_BILL_SEMEN_SALE");
        semenSql.addMainSql(" WHERE DELETED_FLAG = '0'");
        semenSql.addCondition(SpermConstants.SPERM_INFO_IS_ENTRY_UNENTRY, " AND IS_ENTRY = ?");
        semenSql.addMainSql(" AND BILL_ID = T1.ROW_ID");
        semenSql.addCondition(getFarmId(), " AND CUSTOMER_ID = ?) saleNum ");
        semenSql.addMainSql(" FROM PP_M_BILL T1");
        semenSql.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        semenSql.addCondition(EventConstants.SALE_SEMEN, " AND T1.EVENT_CODE = ?");
        semenSql.addMainSql(" AND EXISTS(SELECT 1 FROM PP_L_BILL_SEMEN_SALE");
        semenSql.addMainSql(" WHERE DELETED_FLAG = '0'");
        semenSql.addMainSql(" AND BILL_ID = T1.ROW_ID");
        semenSql.addCondition(SpermConstants.SPERM_INFO_IS_ENTRY_UNENTRY, " AND IS_ENTRY = ?");
        semenSql.addCondition(getFarmId(), " AND CUSTOMER_ID = ?)");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", semenSql.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        for (Map<String, String> map : list) {
            map.put("supplierIdName", CacheUtil.getName(Maps.getString(map, "farmId"), NameEnum.COMPANY_NAME));
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> editSemenListByBillId(Long billId) throws Exception {
        SqlCon supSql = new SqlCon();
        supSql.addMainSql("SELECT s.BOAR_ID AS pigId, bs.BOAR_MATERIAL_ID boarMaterialId,s.SALE_NUM spermNum,bs.ANH_NUM as anhNum,");
        supSql.addMainSql(" Date(s.VALID_DATE) validDate,m.FARM_ID supplierId,s.notes saleNotes,");
        supSql.addMainSql("  bs.SEMEN_QTY AS semenQty, ROUND(bs.SEMEN_QTY/(bs.ANH_NUM*t2.SPEC),2) AS semenBatchNoSpec,T2.SPEC AS spec,");
        supSql.addMainSql(" bs.SEMEN_BATCH_NO semenBatchNo, bs.EAR_BRAND AS earBrand,Date(bs.SEMEN_DATE) as semenDate,");
        supSql.addMainSql(" T5.BREED_NAME as breedName,bs.DAY_AGE AS dayAge,");
        supSql.addMainSql(" T2.COLOR AS color,T2.COHESION AS cohesion, T2.SPERM_MOTILITY AS spermMotility,");
        supSql.addMainSql("  T2.SPERM_DENSITY AS spermDensity,T2.ABNORMATION_RATE AS abnormationRate, bs.ODOUR AS odour");
        supSql.addMainSql(" ,s.ROW_ID AS semenSaleRowId");
        supSql.addMainSql(" FROM pp_l_bill_semen_sale s");
        supSql.addMainSql(" INNER JOIN pp_m_bill m");
        supSql.addMainSql(" ON s.BILL_ID = m.ROW_ID AND m.DELETED_FLAG = 0");
        supSql.addMainSql(" INNER JOIN pp_l_bill_semen bs");
        supSql.addMainSql(" ON s.SEMEN_ID = bs.ROW_ID AND bs.DELETED_FLAG = 0 ");
        supSql.addMainSql(" INNER JOIN pp_l_sperm_info T2");
        supSql.addMainSql(" ON bs.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0' ");
        // supSql.addMainSql(" LEFT JOIN pp_l_pig T3 ON (bs.PIG_ID=T3.ROW_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1') ");
        // supSql.addMainSql(" LEFT JOIN pp_l_ear_code T4 ON (T3.EAR_CODE_ID=T4.ROW_ID AND T4.DELETED_FLAG='0') ");
        supSql.addMainSql("  INNER JOIN cd_l_breed T5 ON (bs.BREED_ID=T5.ROW_ID AND T5.DELETED_FLAG='0') ");
        supSql.addMainSql(" WHERE s.DELETED_FLAG = 0");
        supSql.addCondition(billId, " AND m.ROW_ID = ?");
        supSql.addCondition(SpermConstants.SPERM_INFO_IS_ENTRY_UNENTRY, " AND s.IS_ENTRY = ?");
        supSql.addMainSql(" GROUP BY S.ROW_ID ");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", supSql.getCondition());
        List<Map<String, Object>> semenList = paramMapper.getObjectInfos(sqlMap);

        Map<Long, MaterialModel> semenMap = checkSemenMaterial(semenList);

        List<Map<String, Object>> warehouseMapList = this.searchSpermWarehouseName(new HashMap<String, Object>());

        MaterialModel materialModel = null;
        for (Map<String, Object> map : semenList) {
            materialModel = semenMap.get(Maps.getLongClass(map, "boarMaterialId"));
            map.put("materialId", String.valueOf(materialModel.getRowId()));
            map.put("materialIdName", materialModel.getMaterialName());
            map.put("supplierIdName", CacheUtil.getName(Maps.getString(map, "supplierId"), NameEnum.COMPANY_NAME));
            map.put("houseId", "N");

            // 如果只有一个精液仓库，默认选择
            if (warehouseMapList.size() == 1) {
                Map<String, Object> warehouseMap = warehouseMapList.get(0);
                map.put("warehouseId", Maps.getLong(warehouseMap, "rowId"));
                map.put("warehouseIdName", Maps.getString(warehouseMap, "warehouseName"));
            }
        }

        return semenList;
    }

    private Map<Long, MaterialModel> checkSemenMaterial(List<Map<String, Object>> semenList) throws Exception {
        Long supplierId = Maps.getLongClass(semenList.get(0), "supplierId");
        // 验证本场对应供应商是否建立
        checkSupplierIsExist(supplierId);

        // 验证精液主数据以及公猪主数据 是否也已经建立
        Set<Long> boarMaterialIdSet = new HashSet<>();
        Map<Long, MaterialModel> semenMap = new HashMap<>();
        for (Map<String, Object> map : semenList) {
            boarMaterialIdSet.add(Maps.getLongClass(map, "boarMaterialId"));
        }
        for (Long boarMaterialId : boarMaterialIdSet) {
            // 查原场公猪的品种，品系
            SqlCon boarModelSqlCon = new SqlCon();
            boarModelSqlCon.addCondition(boarMaterialId, " AND MATERIAL_ID = ?");
            List<BoarModel> boarModelList = getList(boarMapper, boarModelSqlCon);
            if (boarModelList.isEmpty()) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "未找到相应公猪主数据！");
            } else if (boarModelList.size() > 1) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "相应公猪主数据出现多条，请联系管理员！");
            }
            BoarModel boarModel = boarModelList.get(0);
            boarModel.setRowId(null);

            SqlCon spermSql = new SqlCon();
            spermSql.addCondition(boarModel.getMaterialId(), " AND BOAR_ID = ?");
            SpermModel spermModel = getModel(spermMapper, spermSql);

            SqlCon materialSql = new SqlCon();
            materialSql.addCondition(boarModel.getMaterialId(), " AND (ROW_ID = ?");
            materialSql.addCondition(spermModel.getMaterialId(), " OR ROW_ID = ?)");
            List<MaterialModel> materialList = getList(materialMapper, materialSql);

            // 判断本场是否存在对应公猪以及精液主数据
            SqlCon checkBoarSql = new SqlCon();
            checkBoarSql.addCondition(boarModel.getBreedId(), " AND BREED_ID = ?");
            checkBoarSql.addCondition(boarModel.getStrain(), " AND STRAIN = ? ");
            checkBoarSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            BoarModel bmodel = getModel(boarMapper, checkBoarSql);
            if (bmodel == null) {
                MaterialModel materialboarModel = null;
                MaterialModel materialspermModel = null;
                for (MaterialModel model : materialList) {
                    if (model.getRowId().equals(boarModel.getMaterialId())) {
                        materialboarModel = model;
                        materialboarModel.setFarmId(getFarmId());
                        materialboarModel.setCompanyId(getCompanyId());
                        materialboarModel.setRowId(null);
                        materialboarModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        materialboarModel.setStatus(CommonConstants.STATUS);
                        materialboarModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                        materialboarModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                        materialMapper.insert(materialboarModel);
                    } else {
                        materialspermModel = model;
                        materialspermModel.setFarmId(getFarmId());
                        materialspermModel.setCompanyId(getCompanyId());
                        materialspermModel.setRowId(null);
                        materialspermModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        materialspermModel.setStatus(CommonConstants.STATUS);
                        materialspermModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                        materialspermModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                        materialMapper.insert(materialspermModel);
                    }
                }
                boarModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                boarModel.setStatus(CommonConstants.STATUS);
                boarModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                boarModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                boarModel.setFarmId(getFarmId());
                boarModel.setCompanyId(getCompanyId());
                boarModel.setMaterialId(materialboarModel.getRowId());
                boarMapper.insert(boarModel);

                spermModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                spermModel.setStatus(CommonConstants.STATUS);
                spermModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                spermModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                spermModel.setFarmId(getFarmId());
                spermModel.setCompanyId(getCompanyId());
                spermModel.setBoarId(materialboarModel.getRowId());
                spermModel.setMaterialId(materialspermModel.getRowId());
                spermMapper.insert(spermModel);

                semenMap.put(boarMaterialId, materialspermModel);
            } else {
                // 存在，就继续判断精液主数据是否建立
                SqlCon checkSpermSql = new SqlCon();
                checkSpermSql.addCondition(bmodel.getMaterialId(), " AND BOAR_ID = ?");
                SpermModel smodel = getModel(spermMapper, checkSpermSql);

                // 精液主数据不存在，则复制
                if (smodel == null) {
                    MaterialModel materialspermModel = null;
                    for (MaterialModel model : materialList) {
                        materialspermModel = model;
                        if (materialspermModel.getRowId().equals(spermModel.getMaterialId())) {
                            materialspermModel.setRowId(null);
                            materialspermModel.setFarmId(getFarmId());
                            materialspermModel.setCompanyId(getCompanyId());
                            materialspermModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                            materialspermModel.setStatus(CommonConstants.STATUS);
                            materialspermModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                            materialspermModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                            materialMapper.insert(materialspermModel);
                        }
                    }
                    spermModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    spermModel.setStatus(CommonConstants.STATUS);
                    spermModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    spermModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    spermModel.setFarmId(getFarmId());
                    spermModel.setCompanyId(getCompanyId());
                    spermModel.setBoarId(bmodel.getMaterialId());
                    spermModel.setMaterialId(materialspermModel.getRowId());
                    spermMapper.insert(spermModel);

                    semenMap.put(boarMaterialId, materialspermModel);
                } else {
                    SqlCon materialspermSql = new SqlCon();
                    materialspermSql.addCondition(smodel.getMaterialId(), " AND ROW_ID = ?");
                    MaterialModel materialspermModel = getModel(materialMapper, materialspermSql);
                    semenMap.put(boarMaterialId, materialspermModel);
                }
            }
        }
        return semenMap;
    }

    // 妊娠母猪销售单据入场
    private void sowRSBillMoveIn(PigMoveInView pigMoveInModel) throws Exception {

        // 获取入场后的母猪id
        SqlCon sowSql = new SqlCon();
        sowSql.addMainSql("SELECT row_id pigId FROM pp_l_pig WHERE DELETED_FLAG = 0");
        sowSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sowSql.addCondition(pigMoveInModel.getRelationPigId(), " AND RELATION_PIG_ID = ?");
        sowSql.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1");
        Map<String, String> sowMap = new HashMap<>();
        sowMap.put("sql", sowSql.getCondition());
        Map<String, String> sowModel = paramMapper.getInfos(sowMap).get(0);
        long sowId = Maps.getLong(sowModel, "pigId");

        // 获取原场母猪生产号，配种时的last_class_date
        SqlCon preSowSql = new SqlCon();
        preSowSql.addMainSql("SELECT p.PRO_NO,p.MATERIAL_ID, ");
        preSowSql.addMainSql(" (SELECT t1.TOWORK_DATE FROM pp_l_bill_towork t1");
        preSowSql.addMainSql(" INNER JOIN pp_l_bill_towork t2");
        preSowSql.addMainSql(" ON t1.ROW_ID = t2.CHANGE_PIG_CLASS_ID AND t2.DELETED_FLAG = 0 AND t1.FARM_ID = t2.FARM_ID ");
        preSowSql.addMainSql(" WHERE t1.PIG_ID = p.ROW_ID AND t1.DELETED_FLAG = 0");
        preSowSql.addMainSql(" ORDER BY t1.ROW_ID DESC LIMIT 1) LAST_CLASS_DATE");
        preSowSql.addMainSql(" FROM pp_l_pig p ");
        preSowSql.addMainSql(" WHERE p.DELETED_FLAG = 0");
        preSowSql.addCondition(pigMoveInModel.getRelationPigId(), " AND p.ROW_ID = ?");
        PigModel preSowModel = setSql(pigMapper, preSowSql).get(0);
        long preSowMaterialId = preSowModel.getMaterialId();
        preSowModel.setRowId(sowId);
        preSowModel.setMaterialId(null);
        // 跟新母猪的last_class_date为原场配种的，以及母猪的生产号
        pigMapper.update(preSowModel);

        // 原场配种记录
        SqlCon breedSql = new SqlCon();
        breedSql.addCondition(pigMoveInModel.getRelationPigId(), " AND PIG_ID = ?");
        breedSql.addCondition(pigMoveInModel.getParity(), " AND PARITY = ?");
        breedSql.addMainSql(" ORDER BY ROW_ID DESC LIMIT 1");
        BillBreedModel breedModel = getModel(billBreedMapper, breedSql);
        Long boarId = breedModel.getBreedBoarFirst();
        // 查原场公猪基本信息
        SqlCon xnSql = new SqlCon();
        xnSql.addMainSql("SELECT c.EAR_BRAND earBrand,c.EAR_SHORT earShort,p.MATERIAL_ID materialId,");
        xnSql.addMainSql(" p.BREED_ID breedId,p.STRAIN strain,p.FARM_ID beforeFarmId,p.BIRTH_DATE birthDate,p.ROW_ID pigId");
        xnSql.addMainSql(" FROM pp_l_pig p");
        xnSql.addMainSql(" INNER JOIN pp_l_ear_code c");
        xnSql.addMainSql(" ON p.EAR_CODE_ID = c.ROW_ID and c.DELETED_FLAG = 0");
        xnSql.addMainSql(" WHERE p.DELETED_FLAG = 0");
        xnSql.addCondition(boarId, " AND p.ROW_ID = ?");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", xnSql.getCondition());
        Map<String, String> boarMap = paramMapper.getInfos(sqlMap).get(0);

        // 查看本场是否存在符合条件的虚拟公猪，没有则复制
        SqlCon bcxnSql = new SqlCon();
        bcxnSql.addMainSql("SELECT p.* FROM pp_l_pig p");
        bcxnSql.addMainSql(" INNER JOIN pp_l_ear_code c");
        bcxnSql.addMainSql(" ON p.EAR_CODE_ID = c.ROW_ID and c.DELETED_FLAG = 0");
        bcxnSql.addMainSql(" WHERE p.DELETED_FLAG = 0");
        bcxnSql.addConditionWithNull(getFarmId(), " AND p.FARM_ID = ?");
        bcxnSql.addCondition(Maps.getString(boarMap, "earBrand"), " AND c.EAR_BRAND = ?");
        List<PigModel> pigList = setSql(pigMapper, bcxnSql);

        // 查看本场 是否有该公猪的主数据，没有则复制
        BoarModel boarModel = getBoarMaterial(boarMap);
        // check 本场对应肉猪主数据，没有则复制
        checkPorkMaterial(pigMoveInModel.getMaterialId(), boarModel.getMaterialId(), preSowMaterialId, Maps.getLong(boarMap, "materialId"));

        PigModel xnPigModel = null;
        if (pigList.isEmpty()) {
            PigMoveInView xnBoarModel = new PigMoveInView();
            Date createDate = new Date();
            long billId = editXnPigInputCreateBill("PIG_MOVE_IN", createDate);
            long houseId = getXNBoarHouseId();
            xnBoarModel.setBreedId(boarModel.getBreedId());
            xnBoarModel.setStrain(boarModel.getStrain());
            xnBoarModel.setBirthWeight(boarModel.getBirthWeight());
            xnBoarModel.setBodyCondition(boarModel.getBodyCondition());
            xnBoarModel.setLeftTeatNum(boarModel.getLeftTeatNum());
            xnBoarModel.setRightTeatNum(boarModel.getRightTeatNum());
            xnBoarModel.setPigClass(PigConstants.PIG_CLASS_SCGZ);
            xnBoarModel.setParity(0L);
            xnBoarModel.setSex(PigConstants.PIG_SEX_MALE);
            xnBoarModel.setOptType(PigConstants.PIG_MOVE_IN_OPT_TYPE_FILE);

            xnBoarModel.setEarBrand(Maps.getString(boarMap, "earBrand"));
            xnBoarModel.setEarShort(Maps.getString(boarMap, "earShort"));
            xnBoarModel.setLineNumber(pigMoveInModel.getLineNumber());
            xnBoarModel.setPigType(PigConstants.PIG_TYPE_BOAR);
            xnBoarModel.setMaterialId(boarModel.getMaterialId());
            xnBoarModel.setCompanyId(getCompanyId());
            xnBoarModel.setFarmId(getFarmId());
            xnBoarModel.setBirthParity(0L);
            xnBoarModel.setHouseNum(0L);
            xnBoarModel.setHouseId(houseId);
            xnBoarModel.setBillId(billId);
            xnBoarModel.setOriginApp(CommonConstants.ORIGIN_APP_EXCEL);
            xnBoarModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_INPUT);
            xnBoarModel.setEnterDate(createDate);
            xnBoarModel.setBirthDate(Maps.getDate(boarMap, "birthDate"));
            xnBoarModel.setCreateId(getEmployeeId());
            xnBoarModel.setWorker(getEmployeeId());
            xnBoarModel.setEventName("PIG_MOVE_IN");
            // Added by zhangjs for 增加猪只来源
            xnBoarModel.setOrigin(PigConstants.PIG_ORIGIN_XN);
            xnBoarModel.setRelationPigId(Maps.getLong(boarMap, "pigId"));

            pigEventMapper.pigMoveIn(xnBoarModel);
            if (!"0".equals(xnBoarModel.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, xnBoarModel.getErrorMessage());
            }

            // 往PigEventRelates插入数据
            SqlCon insertPigEventRelatesSqlCon = new SqlCon();
            insertPigEventRelatesSqlCon.addMainSql(
                    "SELECT '1' AS status, 0 AS deletedFlag, FARM_ID AS farmId, COMPANY_ID AS companyId, SWINERY_ID AS swineryId, ROW_ID AS pigId, LINE_ID AS lineId, HOUSE_ID AS houseId, PIGPEN_ID AS pigpenId,(SELECT EAR_BRAND FROM PP_L_EAR_CODE WHERE STATUS = '1' AND DELETED_FLAG = '0' AND ROW_ID = EAR_CODE_ID) AS earBrand, PIG_TYPE AS pigType, SEX AS sex, PIG_CLASS AS pigClass, PARITY AS parity, BILL_ID AS billId,LAST_EVENT_DATE AS eventDate FROM PP_L_PIG");
            insertPigEventRelatesSqlCon.addMainSql(" WHERE STATUS = '1' AND DELETED_FLAG = '0'");
            insertPigEventRelatesSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            insertPigEventRelatesSqlCon.addCondition(billId, " AND BILL_ID = ? ");

            List<PigEventRelatesModel> pigEventRelatesModelList = setSql(pigEventRelatesMapper, insertPigEventRelatesSqlCon);

            if (pigEventRelatesModelList != null && !pigEventRelatesModelList.isEmpty()) {
                pigEventRelatesMapper.inserts(pigEventRelatesModelList);
            }
            SqlCon pigSql = new SqlCon();
            pigSql.addCondition(getFarmId(), " AND FARM_ID = ?");
            pigSql.addCondition(billId, " AND BILL_ID = ?");
            xnPigModel = getModel(pigMapper, pigSql);
        } else {
            xnPigModel = pigList.get(0);
        }
        // 补配种记录
        breedModel.setBillId(pigMoveInModel.getBillId());
        breedModel.setPigId(sowId);
        breedModel.setBreedBoarFirst(xnPigModel.getRowId());
        breedModel.setCompanyId(getCompanyId());
        breedModel.setFarmId(getFarmId());
        Date createDate = new Date();
        breedModel.setCreateDate(createDate);
        breedModel.setCreateId(getEmployeeId());
        breedModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        breedModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        breedModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        breedModel.setBreedBoarSecond(null);
        breedModel.setBreedDateSecond(null);
        breedModel.setBreedBoarThird(null);
        breedModel.setBreedDateThird(null);
        billBreedMapper.insert(breedModel);
    }

    private BoarModel getBoarMaterial(Map<String, String> boarMap) throws Exception {
        // 查看本场 是否有该公猪的主数据，没有则复制
        SqlCon boarSql = new SqlCon();
        boarSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        boarSql.addCondition(Maps.getString(boarMap, "breedId"), " AND BREED_ID = ?");
        boarSql.addCondition(Maps.getString(boarMap, "strain"), " AND STRAIN = ?");
        BoarModel boarModel = getModel(boarMapper, boarSql);

        if (boarModel == null) {
            // 原场物料
            SqlCon beBoarSql = new SqlCon();
            beBoarSql.addConditionWithNull(Maps.getLong(boarMap, "beforeFarmId"), " AND FARM_ID = ?");
            beBoarSql.addCondition(Maps.getString(boarMap, "breedId"), " AND BREED_ID = ?");
            beBoarSql.addCondition(Maps.getString(boarMap, "strain"), " AND STRAIN = ?");
            BoarModel beBoarModel = getModel(boarMapper, beBoarSql);

            SqlCon beMaterialSql = new SqlCon();
            beMaterialSql.addConditionWithNull(Maps.getLong(boarMap, "beforeFarmId"), " AND FARM_ID = ?");
            beMaterialSql.addCondition(beBoarModel.getMaterialId(), " AND ROW_ID = ?");
            MaterialModel beMaterialModel = getModel(materialMapper, beMaterialSql);

            // 本场复制原场物料主数据
            beBoarModel.setRowId(null);

            beMaterialModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            beMaterialModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            beMaterialModel.setStatus(CommonConstants.STATUS);
            beMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            beMaterialModel.setRowId(null);
            beMaterialModel.setFarmId(getFarmId());
            beMaterialModel.setCompanyId(getCompanyId());
            materialMapper.insert(beMaterialModel);

            beBoarModel.setFarmId(getFarmId());
            beBoarModel.setCompanyId(getCompanyId());
            beBoarModel.setMaterialId(beMaterialModel.getRowId());
            boarMapper.insert(beBoarModel);
            return beBoarModel;
        } else {
            return boarModel;
        }
    }

    // 创建虚拟公猪单据
    private long editXnPigInputCreateBill(String eventName, Date billDate) throws Exception {
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
        // 虚拟公猪入场无法撤销，单据无法查看
        billModel.setDeletedFlag("2");
        // 创建表单
        billModel.setBillDate(billDate);
        billMapper.insert(billModel);
        return billModel.getRowId();
    }

    // 判断肉猪主数据
    private void checkPorkMaterial(Long sowMaterialId, Long boarMaterialId, Long preSowMaterialId, Long preBoarMaterialId) throws Exception {
        // 查看本场是否满足条件的物料主数据
        SqlCon porkSql = new SqlCon();
        porkSql.addCondition(sowMaterialId, " AND SOW_ID = ?");
        porkSql.addCondition(boarMaterialId, " AND BOAR_ID = ?");
        porkSql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        PorkPigModel porkModel = getModel(porkPigMapper, porkSql);

        if (porkModel == null) {
            // 如果没有，查看原场肉猪物料
            SqlCon prePorkSql = new SqlCon();
            prePorkSql.addCondition(preSowMaterialId, " AND SOW_ID = ?");
            prePorkSql.addCondition(preBoarMaterialId, " AND BOAR_ID = ?");
            PorkPigModel prePorkModel = getModel(porkPigMapper, prePorkSql);
            if (prePorkModel != null) {
                // 本场复制原场肉猪物料
                MaterialModel preMaterialModel = materialMapper.searchById(prePorkModel.getMaterialId());
                preMaterialModel.setRowId(null);
                preMaterialModel.setFarmId(getFarmId());
                preMaterialModel.setCompanyId(getCompanyId());
                preMaterialModel.setStatus(CommonConstants.STATUS);
                preMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);

                materialMapper.insert(preMaterialModel);

                prePorkModel.setFarmId(getFarmId());
                prePorkModel.setCompanyId(getCompanyId());
                prePorkModel.setSowId(sowMaterialId);
                prePorkModel.setBoarId(boarMaterialId);
                prePorkModel.setMaterialId(preMaterialModel.getRowId());
                porkPigMapper.insert(prePorkModel);
            } else {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "原场未找到对应的肉猪主数据！");
            }
        }
    }

    @Override
    public String getEarBrandByPigId(Long pigId) throws Exception {
        String result = null;
        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql(
                " SELECT T1.EAR_BRAND earBrand FROM pp_l_pig T0 LEFT JOIN pp_l_ear_code T1 ON T0.EAR_CODE_ID = T1.ROW_ID AND T1.STATUS = '1' AND T1.DELETED_FLAG = '0' WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        sqlCon.addCondition(pigId, " AND T0.ROW_ID = ?");
        map.put("sql", sqlCon.getCondition());
        List<Map<String, String>> infos = paramMapper.getInfos(map);
        result = infos.get(0).get("earBrand");
        return result;
    }

    @Override
    public List<Map<String, Object>> searchFarmHandByPlanType(Map<String, Object> inputMap) throws Exception {
        // String result = null;
        String type = null;
        String year = null;
        String month = null;
        String date = null;
        type = Maps.getString(inputMap, "type");
        if (type.equals("1")) {
            year = Maps.getString(inputMap, "year");
            date = year + "-01-01";
        } else if (type.equals("2")) {
            month = Maps.getString(inputMap, "month");
            year = Maps.getString(inputMap, "year");
            date = year + "-" + month + "-1";
        }
        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql(
                " SELECT t0.farm_id farmId,company_name farmName,T1.SORT_NAME sortName,SUM(t0.PIG_CLASS = 15) handChildPig ,SUM(t0.PIG_CLASS = 16) handFatPig "
                        + "FROM pp_l_bill_towork T0,hr_m_company T1 WHERE t0. DELETED_FLAG  = '0' AND t0. STATUS  = '1' "
                        + " AND t0.FARM_ID  = T1.ROW_ID");
        sqlCon.addCondition(date, " AND DATE(t0. TOWORK_DATE  )<= ? ");
        sqlCon.addCondition(date, "AND DATE(ifnull(t0. TOWORK_DATE_OUT ,DATE_ADD(CURRENT_DATE(),INTERVAL 1 DAY))) > ? ");
        sqlCon.addMainSql("AND t0. PIG_CLASS  IN (15,16)");
        sqlCon.addMainSql(" group by t0.farm_id,t1.company_name,T1.SORT_NAME");
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    @Override
    public void editNewPlan(String planType, String planMonth, String planYear, String detialListStr) throws Exception {
        Map<String, String> map = new HashMap<>();
        String type = null;
        if ("".equals(planType)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择计划类型");
        }
        type = planType;
        if (type.equals("1")) {
            if ("".equals(planYear)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择年份");
            }
        } else if (type.equals("2")) {
            if ("".equals(planYear)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择年份");
            }
            if ("".equals(planMonth)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择月份");
            }
        }

        List<SalePlanModel> salePlanModels = getJsonList(detialListStr, SalePlanModel.class);

        boolean flag = true;
        for (SalePlanModel model : salePlanModels) {
            if (model.getRowId() != null) {
                flag = false;
                break;
            } else {
                break;
            }
        }
        // 新增
        if (flag) {
            // 业务编码
            String eventName = "SALE_PLAN";
            String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
            BillModel billModel = new BillModel();
            billModel.setCompanyId(getCompanyId());
            billModel.setFarmId(getFarmId());
            billModel.setCreateId(getEmployeeId());
            billModel.setBusinessCode(businessCode);
            billModel.setEventCode(eventName);
            billModel.setStatus("1");
            billModel.setDeletedFlag("0");
            // 创建表单
            billModel.setBillDate(billModel.getBillDate());
            billMapper.insert(billModel);
            long billId = billModel.getRowId();
            for (SalePlanModel salePlanModel : salePlanModels) {
                salePlanModel.setStatus("1");
                salePlanModel.setDeletedFlag("0");
                salePlanModel.setPlanDateType(planType);
                salePlanModel.setPlanYear(Long.parseLong(planYear));
                if (type.equals("2")) {
                    salePlanModel.setPlanMonth(Long.parseLong(planMonth));
                }
                salePlanModel.setBillId(billId);
            }
            salePlanMapper.inserts(salePlanModels);

        } else {
            // 编辑
            for (SalePlanModel salePlanModel : salePlanModels) {
                salePlanModel.setStatus("1");
                salePlanModel.setDeletedFlag("0");
                salePlanModel.setPlanDateType(planType);
                salePlanModel.setPlanYear(Long.parseLong(planYear));
                if (type.equals("2")) {
                    salePlanModel.setPlanMonth(Long.parseLong(planMonth));
                }
            }
            salePlanMapper.updates(salePlanModels);

        }

        // return paramMapper.getObjectInfos(map);
    }

    public BasePageInfo searchPlanByALL(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql(" SELECT a.bill_id as billid,a.PLAN_DATE_TYPE AS planType,("
                + "SELECT b.code_name FROM cd_l_code_list b WHERE b.type_code='plantype' AND b.code_value=a.PLAN_DATE_TYPE ) AS planTypeName ,"
                + "a.plan_year AS planYear,IFNULL(a.plan_month,'') AS planMonth,CONCAT(a.plan_year,'年',IFNULL(a.plan_month,''),IF(a.plan_month IS NULL,'','月')) AS planDate,a.NOTES  FROM pp_o_sale_plan a GROUP BY a.BILL_ID");
        map.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> returnList = paramMapper.getObjectInfos(map);
        getPageInfo().setTotal(returnList.size());
        return getPageInfo(returnList);
    }

    @Override
    public List<Map<String, Object>> searchHistoryByPlanType(Map<String, Object> inputMap) throws Exception {
        // String result = null;
        String billid = null;
        billid = Maps.getString(inputMap, "billid");

        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql("   SELECT a.row_id rowId,a.farm_id farmId,b.company_name farmName,b.sort_name sortName,"
                + "a.FARM_CHILD_PIG farmChildPig, a.FARM_FAT_PIG farmFatPig, a.PRE_CHILD_PIG preChildPig,"
                + "a.PRE_FAT_PIG preFatPig,a.HAND_CHILD_PIG handChildPig,a.HAND_FAT_PIG handChildPig FROM pp_o_sale_plan a,hr_m_company b"
                + " WHERE a.FARM_ID  = b.ROW_ID ");
        sqlCon.addCondition(billid, " AND bill_id= ? ");
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    public void editSendSapDate(String endDate, String event) throws Exception {

        SqlCon condition = new SqlCon();
        condition.addCondition(endDate, " AND TO_SAP_DATE = ? ");
        condition.addCondition(getFarmId(), " AND FARM_ID = ? ");
        List<SysHanaInsertLogModel> list = getList(sysHanaInsertLogMapper, condition);
        if (list.size() <= 1) {
            if (list.size() == 1) {
                for (SysHanaInsertLogModel insertLog : list) {
                    if ("N".equals(insertLog.getToSapAgin())) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "截止" + endDate + "的数据已上传致SAP财务系统,若需要再次上传;请联系管理员开启权限。。。");
                    } else if ("Y".equals(insertLog.getToSapAgin())) {
                        insertLog.setDeletedFlag("1");
                        insertLog.setDeletedTime(TimeUtil.getSysTimestamp());
                        // 删除该数据
                        sysHanaInsertLogMapper.update(insertLog);
                    }
                }
            }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "已出现多次上传致SAP财务系统数据,请联系管理员纠正错误数据!!!");
        }

    }

    // 3010 后备转生产 - A 应收发票
    private MTC_ITFC sendToSapChangeProductFor3010(String mtcBranchID, String mtcDeptID, Date currentDate, Date enterDate, PigModel pigModel,
            BillModel billModel, Long lineNumber) throws Exception {

        return sendToSapChangeProduct(mtcBranchID, mtcDeptID, currentDate, enterDate, pigModel, billModel, lineNumber,
                HanaConstants.MTC_ITFC_OBJ_CODE_3010);
    }

    // 3040 生产猪死亡
    private MTC_ITFC sendToSapChangeProductFor3040(String mtcBranchID, String mtcDeptID, Date currentDate, Date enterDate, PigModel pigModel,
            BillModel billModel, Long lineNumber) throws Exception {

        return sendToSapChangeProduct(mtcBranchID, mtcDeptID, currentDate, enterDate, pigModel, billModel, lineNumber,
                HanaConstants.MTC_ITFC_OBJ_CODE_3040);
    }

    // 3010 SAP后备转生产 - A 应收发票 || 3040 生产猪死亡
    private MTC_ITFC sendToSapChangeProduct(String mtcBranchID, String mtcDeptID, Date currentDate, Date enterDate, PigModel pigModel,
            BillModel billModel, Long lineNumber, String mtcObjCode) throws Exception {

        // 3010 - 后备转生产【应收发票】 - 表头 || 3040 生产猪死亡
        String baseEntry = mtcBranchID + "-" + billModel.getRowId() + "-" + billModel.getBusinessCode();
        HanaGatherInvoiceHeaderDetail hanaGatherInvoiceHeaderDetail = new HanaGatherInvoiceHeaderDetail();
        // 分公司编码
        hanaGatherInvoiceHeaderDetail.setMTC_BranchID(mtcBranchID);
        // 新农+单据编号
        hanaGatherInvoiceHeaderDetail.setMTC_BaseEntry(baseEntry);
        // 业务伙伴编号
        hanaGatherInvoiceHeaderDetail.setMTC_CardCode(mtcBranchID);
        // 单据日期
        hanaGatherInvoiceHeaderDetail.setMTC_DocDate(enterDate);
        // 销售费用
        hanaGatherInvoiceHeaderDetail.setMTC_Fee("0");
        // 销售费用说明
        hanaGatherInvoiceHeaderDetail.setMTC_FeeRmk("0");
        // 货款差异
        hanaGatherInvoiceHeaderDetail.setMTC_AmtDiff("0");

        // 3010 - 后备转生产【应收发票】 - 表行 || 3040 生产猪死亡
        HanaGatherInvoiceRowsDetail hanaGatherInvoiceRowsDetail = new HanaGatherInvoiceRowsDetail();
        // 分公司编码
        hanaGatherInvoiceRowsDetail.setMTC_BranchID(mtcBranchID);
        // 猪场编码
        hanaGatherInvoiceRowsDetail.setMTC_DeptID(mtcDeptID);
        // 新农+单据编号
        hanaGatherInvoiceRowsDetail.setMTC_BaseEntry(String.valueOf(billModel.getRowId()));
        // 新农+单据行号
        hanaGatherInvoiceRowsDetail.setMTC_BaseLine(String.valueOf(lineNumber));

        // 销售类型
        String mtcSalesType = null;
        if (HanaConstants.MTC_ITFC_OBJ_CODE_3010.equals(mtcObjCode)) {
            // 销售类型：固定值：SP - 后备转生产
            mtcSalesType = HanaConstants.MTC_SALES_TYPE_SP;
        } else if (HanaConstants.MTC_ITFC_OBJ_CODE_3040.equals(mtcObjCode)) {
            // 销售类型：固定值：PD - 生产猪死亡
            mtcSalesType = HanaConstants.MTC_SALES_TYPE_PD;
        }
        hanaGatherInvoiceRowsDetail.setMTC_SalesType(mtcSalesType);

        // 品名
        String mtcItemCode = null;
        if (HanaConstants.MTC_ITFC_OBJ_CODE_3010.equals(mtcObjCode)) {
            // 品名：固定值：701005 - 商品猪 - 后备种猪
            mtcItemCode = HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;
        } else if (HanaConstants.MTC_ITFC_OBJ_CODE_3040.equals(mtcObjCode)) {
            // 品名：
            // 固定值： 702001 - 生产猪 - 生产公猪
            // 固定值： 702002 - 生产猪 - 生产母猪
            if (PigConstants.PIG_CLASS_SCGZ == pigModel.getPigClass()) {
                mtcItemCode = HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR;
            } else {
                mtcItemCode = HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW;
            }
        }

        hanaGatherInvoiceRowsDetail.setMTC_ItemCode(mtcItemCode);

        // 猪只耳号：初始耳号
        hanaGatherInvoiceRowsDetail.setMTC_BatchNum(pigModel.getSapFixedAssetsEarbrand());
        // 猪舍编号
        hanaGatherInvoiceRowsDetail.setMTC_Unit(HanaUtil.getMTC_Unit(pigModel.getHouseId()));
        // 猪只品种
        hanaGatherInvoiceRowsDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigModel.getBreedId()));
        // 猪只性别
        hanaGatherInvoiceRowsDetail.setMTC_Sex(HanaUtil.getMTC_Sex(pigModel.getSex()));
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
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(enterDate, TimeUtil.TIME_FORMAT));
        // 业务代码:3010 后备转生产 - A 应收发票 || 3040 生产猪死亡
        mtcITFC.setMTC_ObjCode(mtcObjCode);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(baseEntry);
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
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

        return mtcITFC;
    }

    // 3011 SAP后备转生产 - B 固定资产卡片
    private MTC_ITFC sendToSapMTC_OITM(String mtcBranchID, String mtcDeptID, Date currentDate, PigModel pigModel, BillModel billModel)
            throws Exception {
        EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
        MTC_OITM mtcOITM = new MTC_OITM();
        // 猪只耳号
        mtcOITM.setMTC_ItemCode(pigModel.getSapFixedAssetsEarbrand());
        // 猪只描述
        mtcOITM.setMTC_ItemName(CacheUtil.getName(pigModel.getBreedId().toString(), NameEnum.BREED_NAME) + "." + earCodeModel.getEarBrand());
        // 猪只性别
        mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(pigModel.getSex()));
        // 资产类型
        mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(mtcBranchID, pigModel.getSex()));
        // 转生产日龄
        if (pigModel.getToProductDayage() != null) {
            mtcOITM.setMTC_DaysOld(String.valueOf(pigModel.getToProductDayage()));
        }
        // 猪场编号
        mtcOITM.setMTC_DeptID(mtcDeptID);
        // 棚舍编号
        mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(pigModel.getHouseId()));

        // 3021 - 后备转生产【固定资产卡片 - OITM】的MTC_ITFC
        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(mtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(billModel.getBillDate());
        // 业务代码：后备转生产 - B 固定资产卡片
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3011);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(pigModel.getSapFixedAssetsEarbrand());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
        // JSON文件
        String jsonDataFile = HanaJacksonUtil.objectToJackson(mtcOITM);
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

        return mtcITFC;
    }

    public void sendProductSaleToHana() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT s.BILL_ID billId,b.BUSINESS_CODE notes,b.BILL_DATE createDate FROM pp_l_bill_pig_sale s");
        sqlCon.addMainSql(" INNER JOIN pp_m_bill b ON s.BILL_ID = b.ROW_ID AND b.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE s.DELETED_FLAG = 0 ");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM pp_l_bill_pig_sale_detail WHERE BILL_ID = s.BILL_ID AND SALE_DESCRIBE IN(1,2))");
        sqlCon.addCondition(getFarmId(), " AND s.FARM_ID = ?");
        sqlCon.addCondition("2017-04-01", " AND s.CREATE_DATE >= ?");
        List<PigSaleModel> list = setSql(pigSaleMapper, sqlCon);

        Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
        String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
        Date currentDate = TimeUtil.parseDate(new Date(), TimeUtil.DATE_FORMAT);

        List<MTC_ITFC> mtcList = new ArrayList<>();
        for (PigSaleModel model : list) {
            long billId = model.getBillId();
            String businessCode = model.getNotes();
            Date billDate = model.getCreateDate();
            SqlCon saleSql = new SqlCon();
            saleSql.addCondition(billId, " AND BILL_ID = ?");
            PigSaleModel pigSaleModel = getModel(pigSaleMapper, saleSql);
            String cardCode = HanaUtil.getMTC_SaleCardCode(pigSaleModel.getCustomerId());

            // 主表
            HanaPigProduct hanaPigProduct = new HanaPigProduct();
            hanaPigProduct.setMTC_BranchID(mtcBranchID);
            hanaPigProduct.setMTC_BaseEntry(mtcBranchID + "-" + billId + "-" + businessCode);
            hanaPigProduct.setMTC_CardCode(cardCode);
            hanaPigProduct.setMTC_DocDate(billDate);
            List<HanaPigProductDetail> hanaPigProductDetailList = new ArrayList<>();
            hanaPigProduct.setDetailList(hanaPigProductDetailList);

            // 细表
            SqlCon pigModelSqlCon = new SqlCon();
            pigModelSqlCon.addMainSql("SELECT T1.ROW_ID AS pigId, T1.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand");
            pigModelSqlCon.addMainSql(",T1.SEX AS sex, T1.BREED_ID AS breedId, T1.HOUSE_ID AS houseId");
            pigModelSqlCon.addMainSql(",T2.LEAVE_WEIGHT AS leaveWeight, T2.LEAVE_PRICE AS leavePrice");
            pigModelSqlCon.addMainSql(",T2.LINE_NUMBER AS lineNumber");
            pigModelSqlCon.addMainSql(" FROM PP_L_PIG T1");
            pigModelSqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T2");
            pigModelSqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID");
            pigModelSqlCon.addCondition(getFarmId(), " AND T2.FARM_ID = ?)");
            pigModelSqlCon.addMainSql(" INNER JOIN pp_l_bill_pig_sale_detail d");
            pigModelSqlCon.addMainSql(" ON d.ROW_ID = t2.SALE_ID AND d.DELETED_FLAG = 0");
            pigModelSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND d.SALE_DESCRIBE IN (1,2)");
            pigModelSqlCon.addCondition(billId, " AND T2.BILL_ID = ?");

            Map<String, String> pigModelSqlMap = new HashMap<String, String>();
            pigModelSqlMap.put("sql", pigModelSqlCon.getCondition());

            List<Map<String, Object>> pigModelList = paramMapper.getObjectInfos(pigModelSqlMap);

            for (Map<String, Object> pigMap : pigModelList) {
                HanaPigProductDetail hanaPigProductDetail = new HanaPigProductDetail();
                hanaPigProductDetail.setMTC_BranchID(mtcBranchID);
                hanaPigProductDetail.setMTC_DeptID(mtcDeptID);
                hanaPigProductDetail.setMTC_BaseEntry(String.valueOf(billId));
                hanaPigProductDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
                hanaPigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PS);
                if (PigConstants.PIG_SEX_MALE.equals(Maps.getString(pigMap, "sex"))) {
                    hanaPigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR);
                } else {
                    hanaPigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW);
                }
                hanaPigProductDetail.setMTC_BatchNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                hanaPigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                hanaPigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                hanaPigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                hanaPigProductDetail.setMTC_Qty("1");
                hanaPigProductDetail.setMTC_Price(Maps.getString(pigMap, "leavePrice"));
                hanaPigProductDetail.setMTC_Total(Maps.getString(pigMap, "leavePrice"));
                hanaPigProductDetailList.add(hanaPigProductDetail);
            }

            // 均摊费用和货款差异到明细行
            setOtherFeeAndDiff3050(hanaPigProductDetailList, hanaPigProduct.getMTC_Fee(), hanaPigProduct.getMTC_AmtDiff());
            // 销售
            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(hanaPigProduct.getMTC_BranchID());
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
            // 业务代码:生产猪淘汰销售
            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
            // 新农+单据编号
            mtcITFC.setMTC_DocNum(hanaPigProduct.getMTC_BaseEntry());
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // DB
            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigProduct);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
            mtcList.add(mtcITFC);
        }
        if (!mtcList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcList);
        }
    }

    @Override
    public void pigCheckLogo(long[] ids) throws Exception {
        String status = "2";// 查情标识
        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sqlCon = new SqlCon();
        // StringBuffer earBrand = new StringBuffer();
        sqlCon.addMainSql(
                "SELECT t0.pig_class pigClass,t1.ear_brand earBrand FROM pp_l_pig t0 INNER JOIN pp_l_ear_code t1 ON t0.ear_code_id = t1.row_id ");
        sqlCon.addCondition(rowIds, " WHERE t0.row_id IN ? AND t1.deleted_flag=0", false, true);
        Map<String, String> checkMap = new HashMap<>();
        checkMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> saleList = paramMapper.getInfos(checkMap);
        for (int i = 0; i < saleList.size(); i++) {
            if (PigConstants.PIG_CLASS_SCGZ != Maps.getLong(saleList.get(i), "pigClass")) {
                String earBrand = Maps.getString(saleList.get(i), "earBrand");
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "此" + earBrand + "耳牌号不是生产公猪");
            }
        }
        SqlCon sql = new SqlCon();
        sql.addMainSql("UPDATE pp_l_pig SET ");
        sql.addCondition(status, " STATUS = ? ");
        sql.addCondition(rowIds, "WHERE row_id IN ?", false, true);
        setSql(pigMapper, sql);
    }

    @Override
    public void pigCancelLogo(long[] ids) throws Exception {
        String status = "1";// 查情标识
        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sqlCon = new SqlCon();
        // StringBuffer earBrand = new StringBuffer();
        sqlCon.addMainSql(
                "SELECT t0.pig_class pigClass,t1.ear_brand earBrand FROM pp_l_pig t0 INNER JOIN pp_l_ear_code t1 ON t0.ear_code_id = t1.row_id ");
        sqlCon.addCondition(rowIds, " WHERE t0.row_id IN ? AND t1.deleted_flag=0", false, true);
        Map<String, String> checkMap = new HashMap<>();
        checkMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> saleList = paramMapper.getInfos(checkMap);
        for (int i = 0; i < saleList.size(); i++) {
            if (PigConstants.PIG_CLASS_SCGZ != Maps.getLong(saleList.get(i), "pigClass")) {
                String earBrand = Maps.getString(saleList.get(i), "earBrand");
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "此" + earBrand + "耳牌号不是生产公猪");
            }
        }
        SqlCon sql = new SqlCon();
        sql.addMainSql("UPDATE pp_l_pig SET ");
        sql.addCondition(status, " STATUS = ? ");
        sql.addCondition(rowIds, "WHERE row_id IN ?", false, true);
        setSql(pigMapper, sql);
    }

    public void sendSaleBillTotal() throws Exception {
        // sendOutSideSaleBill(153443L, 461L, 826L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(153445L, 461L, 823L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(153446L, 461L, 825L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(153660L, 135L, 135L, HanaConstants.MTC_SALES_TYPE_SZ);
        // sendOutSideSaleBill(153783L, 461L, 461L, HanaConstants.MTC_SALES_TYPE_SZ);
        // sendOutSideSaleBill(153843L, 135L, 889L, HanaConstants.MTC_SALES_TYPE_SO);

        // sendOutSideSaleBill(152120L, 248L, 812L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(154187L, 461L, 184L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(153445L, 461L, 184L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(153446L, 461L, 186L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(154298L, 129L, 184L, HanaConstants.MTC_SALES_TYPE_SO);

        // sendOutSideSaleBill(166342L, 135L, 26L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(177867L, 135L, 700L, HanaConstants.MTC_SALES_TYPE_SO);
        // cancelSale2100(191924L, 11L);
        // sendOutSideSaleBill(191924L, 11L, 240L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(181641L, 12L, 1052L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(190318L, 10L, 186L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(190834L, 11L, 240L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(194885L, 11L, 240L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(192658L, 8L, 1076L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(195978L, 12L, 1052L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(194468L, 179L, 184L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(196769L, 7L, 1116L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(196769L, 7L, 1116L, HanaConstants.MTC_SALES_TYPE_SO);
        sendOutSideSaleBill(198102L, 248L, 145L, HanaConstants.MTC_SALES_TYPE_SO);
        // sendOutSideSaleBill(198085L, 179L, 818L, HanaConstants.MTC_SALES_TYPE_SO);

    }

    public void sendOutSideSaleBill(Long billId, Long farmId, Long customerId, String saleType) throws Exception {

        Date currentDate = new Date();

        // 单据
        BillModel billModel = billMapper.searchById(billId);

        Map<String, String> farmMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
        String farmMtcBranchID = Maps.getString(farmMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        String farmMtcDeptID = Maps.getString(farmMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

        // 销售主表
        SqlCon pigSaleSql = new SqlCon();
        pigSaleSql.addCondition(billId, " AND BILL_ID = ?");
        PigSaleModel pigSaleModel = getModel(pigSaleMapper, pigSaleSql);

        Map<String, Object> pigSaleMap = pigSaleModel.getData();

        // 销售细表
        SqlCon saleDetailSql = new SqlCon();
        saleDetailSql.addCondition(billId, " AND BILL_ID = ?");
        List<PigSaleDetailModel> saleDetailList = getList(pigSaleDetailMapper, saleDetailSql);

        // 商品猪
        List<PigSaleDetailModel> goodDetailList = new ArrayList<>();

        // 生产猪
        List<PigSaleDetailModel> productDetailList = new ArrayList<>();

        for (PigSaleDetailModel model : saleDetailList) {
            if (PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(model.getSaleDescribe()) || PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG.equals(model
                    .getSaleDescribe())) {
                productDetailList.add(model);
            } else {
                goodDetailList.add(model);
            }
        }

        // 商品猪销售
        // 主表
        HanaSaleBill hanaSaleBill = new HanaSaleBill();
        // 分公司编码
        hanaSaleBill.setMTC_BranchID(farmMtcBranchID);
        // 新农+单据编号
        hanaSaleBill.setMTC_BaseEntry(farmMtcBranchID + "-" + billId + "-" + billModel.getBusinessCode());
        // 业务伙伴编号

        hanaSaleBill.setMTC_CardCode(HanaUtil.getMTC_SaleCardCode(customerId));
        // 出库日期
        hanaSaleBill.setMTC_DocDate(billModel.getBillDate());
        // 销售费用
        hanaSaleBill.setMTC_Fee(Maps.getString(pigSaleMap, "otherFee", "0.0"));
        // 货款差异
        hanaSaleBill.setMTC_AmtDiff(Maps.getString(pigSaleMap, "paymentDiff", "0.0"));
        // 表行
        List<HanaSaleBillDetail> hanaSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
        //
        hanaSaleBill.setDetailList(hanaSaleBillDetailList);

        Long salesmanId = pigSaleModel.getSalesman();
        for (PigSaleDetailModel pigSaleDetailModel : goodDetailList) {
            Map<String, Object> pigSaleDetailModelMap = pigSaleDetailModel.getData();
            HanaSaleBillDetail hanaSaleBillDetail = new HanaSaleBillDetail();
            // 分公司编码
            hanaSaleBillDetail.setMTC_BranchID(farmMtcBranchID);
            // 猪场编码
            hanaSaleBillDetail.setMTC_DeptID(farmMtcDeptID);
            // 新农+单据编号
            hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(billId));
            // 新农+单据行号
            hanaSaleBillDetail.setMTC_BaseLine(Maps.getString(pigSaleDetailModelMap, "lineNumber"));
            // 销售类型
            hanaSaleBillDetail.setMTC_SalesType(saleType);
            // 品名
            hanaSaleBillDetail.setMTC_ItemCode(getHouseInfoByDB(pigSaleDetailModel.getSaleDescribe(), getFarmId(), pigSaleDetailModel.getHouseId()));
            // 猪舍编号
            hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(pigSaleDetailModel.getHouseId()));
            // 猪只等级
            hanaSaleBillDetail.setMTC_Grade(HanaUtil.getMTC_Grade(pigSaleDetailModel.getSaleDescribe()));
            // 猪只品种
            hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigSaleDetailModel.getBreedId()));
            // 猪只性别
            hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(String.valueOf(pigSaleDetailModel.getSex())));
            // 销售数量(头数)
            hanaSaleBillDetail.setMTC_Qty(Maps.getString(pigSaleDetailModelMap, "saleNum"));
            // 销售重量
            hanaSaleBillDetail.setMTC_Weight(Maps.getString(pigSaleDetailModelMap, "totalWeight"));
            // 数量单价
            hanaSaleBillDetail.setMTC_QtyPrice(Maps.getString(pigSaleDetailModelMap, "preUnitPrice"));
            // 重量单价
            hanaSaleBillDetail.setMTC_WeiPrice(Maps.getString(pigSaleDetailModelMap, "totalUnitPrice"));
            // 底重
            hanaSaleBillDetail.setMTC_BotWeight(Maps.getString(pigSaleDetailModelMap, "baseWeight"));
            // 底重单价
            hanaSaleBillDetail.setMTC_BotPrice(Maps.getString(pigSaleDetailModelMap, "baseUnitPrice"));
            // 超重
            hanaSaleBillDetail.setMTC_OverWeight(Maps.getString(pigSaleDetailModelMap, "overWeight"));
            // 超重单价
            hanaSaleBillDetail.setMTC_OverPrice(Maps.getString(pigSaleDetailModelMap, "overUnitPrice"));
            // 总金额
            hanaSaleBillDetail.setMTC_SalesAmt(Maps.getString(pigSaleDetailModelMap, "totalPrice"));

            if (salesmanId != null) {
                // 业务员
                EmployeeModel employeeModel = employeeMapper.searchById(salesmanId);
                hanaSaleBillDetail.setMTC_Sales(employeeModel.getRowId() + "." + employeeModel.getEmployeeName());
            }
            hanaSaleBillDetailList.add(hanaSaleBillDetail);
        }

        // 均摊费用，货款差异到明细行
        setOtherFeeAndDiff2100(hanaSaleBillDetailList, hanaSaleBill.getMTC_Fee(), hanaSaleBill.getMTC_AmtDiff());
        // 销售
        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(hanaSaleBill.getMTC_BranchID());
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
        // 业务代码:猪只销售
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2100);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(hanaSaleBill.getMTC_BaseEntry());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
        // JSON文件
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

        hanaCommonService.insertMTC_ITFC(mtcITFC);
    }

    // 查询housetype不走缓存，直接查db
    private String getHouseInfoByDB(String saleDescribe, Long farmId, Long houseId) throws Exception {
        if (PigConstants.SELL_GOOD_RESERVE_BOARD_PIG.equals(saleDescribe) || PigConstants.SELL_GOOD_RESERVE_SOW_PIG.equals(saleDescribe)) {
            return HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;
        } else if (PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(saleDescribe)) {
            return HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR;
        } else if (PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG.equals(saleDescribe)) {
            return HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW;
        } else {
            return getMTC_ItemCodeOfHouseType(farmId, houseId);
        }
    }

    private String getMTC_ItemCodeOfHouseType(long farmId, long houseId) throws Exception {
        HouseModel houseModel = houseMapper.searchById(houseId);
        String houseType = String.valueOf(houseModel.getHouseType());
        String isFosterHouse = houseModel.getIsFosterHouse();
        String itemCode = null;
        if (String.valueOf(PigConstants.HOUSE_CLASS_DELIVERY).equals(houseType)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_PIGLET;
        } else if (String.valueOf(PigConstants.HOUSE_CLASS_CAREPIG).equals(houseType)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_NURSING_PIG;
        } else if (String.valueOf(PigConstants.HOUSE_CLASS_FATTEN).equals(houseType) && PigConstants.IS_FOSTER_HOUSE_FALSE.equals(isFosterHouse)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_FATTENING_PIG;
        } else if (String.valueOf(PigConstants.HOUSE_CLASS_FATTEN).equals(houseType) && PigConstants.IS_FOSTER_HOUSE_TRUE.equals(isFosterHouse)) {
            itemCode = HanaConstants.MTC_ITEM_CODE_CULTIVATE_PIG;
        }
        return itemCode;
    }

    public void sendPurchaseToHanaTotal() throws Exception {
        // sendPurchaseBill(163556L, 164593L, 179L, 129L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(163556L, 164593L, 179L, 129L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(169673L, 169679L, 10L, 129L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(169635L, 169639L, 10L, 129L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(163988L, 164007L, 179L, 135L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(163986L, 164003L, 179L, 135L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(163984L, 163998L, 179L, 135L, HanaConstants.MTC_SALES_TYPE_PI, null);
        // sendPurchaseBill(163981L, 163996L, 179L, 135L, HanaConstants.MTC_SALES_TYPE_PI, null);
        // sendPurchaseBill(154338L, 154339L, 8L, 137L, HanaConstants.MTC_SALES_TYPE_PI);
        // sendPurchaseBill(169673L, 169679L, 10L, 129L, HanaConstants.MTC_SALES_TYPE_PI, PigConstants.SELL_GOOD_RESERVE_BOARD_PIG);
        // sendPurchaseBill(169635L, 169639L, 10L, 129L, HanaConstants.MTC_SALES_TYPE_PI, PigConstants.SELL_GOOD_RESERVE_BOARD_PIG);
        cancelHanaPurchase2110(174667L, 174749L, 179L, 129L);
        sendPurchaseBill(174667L, 174749L, 179L, 129L, HanaConstants.MTC_SALES_TYPE_PI, PigConstants.SELL_GOOD_RESERVE_BOARD_PIG);

    }

    private void sendPurchaseBill(Long salebillId, Long moveBillId, Long saleFarmId, Long moveFarmId, String saleType, String describe)
            throws Exception {
        Date currentDate = TimeUtil.parseDate(new Date(), TimeUtil.DATE_FORMAT);
        // 单据
        BillModel billModel = billMapper.searchById(moveBillId);

        Map<String, String> farmMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(moveFarmId);
        String farmMtcBranchID = Maps.getString(farmMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        String farmMtcDeptID = Maps.getString(farmMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

        // 销售主表
        SqlCon pigSaleSql = new SqlCon();
        pigSaleSql.addCondition(salebillId, " AND BILL_ID = ?");
        PigSaleModel pigSaleModel = getModel(pigSaleMapper, pigSaleSql);

        Map<String, Object> pigSaleMap = pigSaleModel.getData();

        // 销售细表
        SqlCon saleDetailSql = new SqlCon();
        saleDetailSql.addCondition(salebillId, " AND BILL_ID = ?");
        List<PigSaleDetailModel> saleDetailList = getList(pigSaleDetailMapper, saleDetailSql);

        // 商品猪销售
        // 主表
        HanaSaleBill hanaSaleBill = new HanaSaleBill();
        // 分公司编码
        hanaSaleBill.setMTC_BranchID(farmMtcBranchID);
        // 新农+单据编号
        hanaSaleBill.setMTC_BaseEntry(farmMtcBranchID + "-" + moveBillId + "-" + billModel.getBusinessCode() + "-" + salebillId);
        // 业务伙伴编号

        String cardCode = HanaUtil.getMTC_SaleCardCode(saleFarmId);
        hanaSaleBill.setMTC_CardCode(getPIBranchID(cardCode));
        // 出库日期
        hanaSaleBill.setMTC_DocDate(billModel.getBillDate());
        // 销售费用
        hanaSaleBill.setMTC_Fee(Maps.getString(pigSaleMap, "otherFee"));
        // 货款差异
        hanaSaleBill.setMTC_AmtDiff(Maps.getString(pigSaleMap, "paymentDiff"));
        // 表行
        List<HanaSaleBillDetail> hanaSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
        //
        hanaSaleBill.setDetailList(hanaSaleBillDetailList);

        Long salesmanId = pigSaleModel.getSalesman();
        for (PigSaleDetailModel pigSaleDetailModel : saleDetailList) {

            // 入场
            SqlCon moveSql = new SqlCon();
            moveSql.addMainSql(" SELECT SUM(p.ON_PRICE) houseTotalPrice,SUM(p.ENTER_WEIGHT) houseTotalWeight,COUNT(1) houseNum,");
            moveSql.addMainSql(" ROUND(SUM(p.ON_PRICE)/SUM(p.ENTER_WEIGHT),2) houseUnitPrice,c.HOUSE_ID houseId");
            moveSql.addMainSql(" FROM pp_l_pig p");
            moveSql.addMainSql(" INNER JOIN pp_l_bill_change_house c");
            moveSql.addMainSql(" ON p.BILL_ID = c.BILL_ID AND p.ROW_ID = c.PIG_ID AND c.DELETED_FLAG = 0");
            moveSql.addMainSql(" WHERE p.DELETED_FLAG = 0");
            moveSql.addCondition(moveFarmId, " AND p.FARM_ID = ?");
            moveSql.addCondition(moveBillId, " AND p.BILL_ID = ?");
            moveSql.addCondition(pigSaleDetailModel.getLineNumber(), " AND p.LINE_NUMBER = ?");
            moveSql.addMainSql(" GROUP BY c.HOUSE_ID");

            Map<String, String> sqlMap = new HashMap<>();
            sqlMap.put("sql", moveSql.getCondition());
            List<Map<String, String>> moveList = paramMapper.getInfos(sqlMap);

            Map<String, Object> pigSaleDetailModelMap = pigSaleDetailModel.getData();
            String saleDescribe = describe == null ? pigSaleDetailModel.getSaleDescribe() : describe;

            for (Map<String, String> pigMap : moveList) {
                HanaSaleBillDetail hanaSaleBillDetail = new HanaSaleBillDetail();
                // 分公司编码
                hanaSaleBillDetail.setMTC_BranchID(farmMtcBranchID);
                // 猪场编码
                hanaSaleBillDetail.setMTC_DeptID(farmMtcDeptID);
                // 新农+单据编号
                hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(moveBillId));
                // 新农+单据行号
                hanaSaleBillDetail.setMTC_BaseLine(Maps.getString(pigSaleDetailModelMap, "lineNumber"));
                // 销售类型
                hanaSaleBillDetail.setMTC_SalesType(saleType);
                // 品名
                hanaSaleBillDetail.setMTC_ItemCode(getHouseInfoByDB(saleDescribe, saleFarmId, pigSaleDetailModel.getHouseId()));
                // 猪舍编号
                hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                // 猪只等级
                hanaSaleBillDetail.setMTC_Grade(HanaUtil.getMTC_Grade(pigSaleDetailModel.getSaleDescribe()));
                // 猪只品种
                hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigSaleDetailModel.getBreedId()));
                // 猪只性别
                hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(String.valueOf(pigSaleDetailModel.getSex())));
                // 销售数量(头数)
                hanaSaleBillDetail.setMTC_Qty(Maps.getString(pigMap, "houseNum"));
                // 销售重量
                hanaSaleBillDetail.setMTC_Weight(Maps.getString(pigMap, "houseTotalWeight"));
                // 重量单价
                hanaSaleBillDetail.setMTC_WeiPrice(Maps.getString(pigMap, "houseUnitPrice"));
                // 总金额
                hanaSaleBillDetail.setMTC_Amount(Maps.getString(pigMap, "houseTotalPrice"));

                if (salesmanId != null) {
                    // 业务员
                    EmployeeModel employeeModel = employeeMapper.searchById(salesmanId);
                    hanaSaleBillDetail.setMTC_Sales(employeeModel.getRowId() + "." + employeeModel.getEmployeeName());
                }
                hanaSaleBillDetailList.add(hanaSaleBillDetail);
            }

        }

        // 销售
        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(hanaSaleBill.getMTC_BranchID());
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaSaleBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
        // 业务代码:猪只采购
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(hanaSaleBill.getMTC_BaseEntry());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(moveFarmId));
        // JSON文件
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

        hanaCommonService.insertMTC_ITFC(mtcITFC);
    }

    @Override
    public void editProduceDataCollect(String selectFarm, String reportType, String startDate, String endDate, String searchMonth, String searchWeek,
            String searchQuarter, String searchYear, String runType) throws Exception {
        if ("".equals(selectFarm) || selectFarm == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪场不为空！");
        }
        if ("".equals(reportType) || reportType == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "报表类型不能为空！");
        }
        if ("".equals(startDate) || startDate == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "开始日期不能为空！");
        }
        if ("".equals(endDate) || endDate == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "结束日期不能为空！");
        }
        if (("".equals(searchMonth) || searchMonth == null) && ("".equals(searchWeek) || searchWeek == null) && ("".equals(searchQuarter)
                || searchQuarter == null) && ("".equals(searchYear) || searchYear == null)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "月或者周不能为空！");
        }
        if (TimeUtil.compareDate(TimeUtil.parseDate(endDate), new Date(), Calendar.DATE) > 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "结束时间不能大于当前时间！");
        }
        SqlCon getVersionSqlCon = new SqlCon();
        /************************************************* 2017-8-2 yangy start **********************************************/
        // 重新生成生产数据汇总报表，上个版本的报表设置隐藏
        // 上个版本的备注，自动更新到最新版本的备注
        getVersionSqlCon.addMainSql(
                " SELECT ROW_ID,VERSION,SALE_NOTES,PRODUCTION_NOTES,BIOLOGICAL_SAFETY_NOTES,POPULATION_PLANNING_NOTES FROM re_m_report  ");
        getVersionSqlCon.addMainSql(" WHERE ROW_ID = ");
        getVersionSqlCon.addMainSql(" ( ");
        getVersionSqlCon.addMainSql(" SELECT IFNULL(MAX(ROW_ID),0) ROW_ID FROM re_m_report WHERE DELETED_FLAG = '0' AND STATUS = '1' ");
        getVersionSqlCon.addCondition(selectFarm, " AND FARM_ID=? ");
        getVersionSqlCon.addCondition(startDate, " AND START_DATE='" + startDate + "'");
        getVersionSqlCon.addCondition(endDate, " AND END_DATE='" + endDate + "'");
        getVersionSqlCon.addMainSql(" ) ");
        List<ReMreportModel> reMreportModelList = setSql(reMreportMapper, getVersionSqlCon);
        Long version = 0L;
        String saleNotes = null;
        String productionNotes = null;
        String biologicalSafetyNotes = null;
        String populationPlanningNotes = null;
        if (reMreportModelList != null && reMreportModelList.size() > 0) {
            version = reMreportModelList.get(0).getVersion();
            saleNotes = reMreportModelList.get(0).getSaleNotes();
            productionNotes = reMreportModelList.get(0).getProductionNotes();
            biologicalSafetyNotes = reMreportModelList.get(0).getBiologicalSafetyNotes();
            populationPlanningNotes = reMreportModelList.get(0).getPopulationPlanningNotes();
            reMreportMapper.delete(reMreportModelList.get(0).getRowId());
        }
        /************************************************* 2017-8-2 yangy end **********************************************/
        ReMreportModel reMreportModel = new ReMreportModel();
        reMreportModel.setReportType(reportType);
        reMreportModel.setYear(Long.valueOf(startDate.substring(0, 4)));
        if (searchMonth != null) {
            reMreportModel.setMonth(Long.valueOf(searchMonth.substring(5, 7)));
        }
        if (searchWeek != null) {
            reMreportModel.setWeek(Long.valueOf(searchWeek.substring(5, 7)));
        }
        if (searchQuarter != null) {
            reMreportModel.setQuarter(Long.valueOf(searchQuarter));
        }
        reMreportModel.setStartDate(TimeUtil.parseDate(startDate));
        reMreportModel.setEndDate(TimeUtil.parseDate(endDate));
        reMreportModel.setFarmId(Long.valueOf(selectFarm));
        reMreportModel.setVersion(version + 1);
        reMreportModel.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
        reMreportModel.setCreateDate(new Timestamp(new Date().getTime()));
        reMreportModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        reMreportModel.setStatus(CommonConstants.STATUS);
        reMreportModel.setSaleNotes(saleNotes);
        reMreportModel.setProductionNotes(productionNotes);
        reMreportModel.setBiologicalSafetyNotes(biologicalSafetyNotes);
        reMreportModel.setPopulationPlanningNotes(populationPlanningNotes);
        if (reMreportModel != null) {
            reMreportMapper.insert(reMreportModel);
        }

        Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", selectFarm);
        String companyMark = Maps.getString(companyInfoMap, "COMPANY_MARK");
        String accountCompanyClass = Maps.getString(companyInfoMap, "ACCOUNT_COMPANY_CLASS");
        SqlCon getChildFarmSqlCon = new SqlCon();
        getChildFarmSqlCon.addMainSql(" SELECT COUNT(1) childFarmNum FROM HR_M_COMPANY WHERE DELETED_FLAG=0 ");
        getChildFarmSqlCon.addCondition(companyMark + ",%", " AND COMPANY_MARK LIKE ? ");
        Map<String, String> getChildFarmMap = new HashMap<String, String>();
        getChildFarmMap.put("sql", getChildFarmSqlCon.getCondition());
        Map<String, Object> childFarmMap = paramMapper.getObjectInfos(getChildFarmMap).get(0);
        int childFarmNum = Maps.getInt(childFarmMap, "childFarmNum");

        SqlCon getReportIdSqlCon = new SqlCon();
        getReportIdSqlCon.addMainSql(" SELECT MAX(ROW_ID) ROW_ID FROM re_m_report WHERE DELETED_FLAG='0' AND STATUS='1' ");
        long newReportId = setSql(reMreportMapper, getReportIdSqlCon).get(0).getRowId();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 存栏-生产母猪存栏
        Map<String, Object> livestockMap = reLproDetailMapper.searchProduceDataByLivestock(companyMark, startDate, endDate);
        insetReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_LIVESTOCK,
                EventConstants.PRODUCE_DATA_INDEX_FEMALE_LIVESTOCK, Maps.getLong(livestockMap, "SC_PIG_QTY"),
                EventConstants.INDICATOR_BUSINESS_CODE_JCMZCL, reportType, runType);
        // 存栏-后备母猪存栏
        insetReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_LIVESTOCK,
                EventConstants.PRODUCE_DATA_INDEX_HBFEMALE_LIVESTOCK, Maps.getLong(livestockMap, "HB_PIG_QTY"), null, reportType, runType);
        // 存栏-商品猪存栏
        insetReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_LIVESTOCK,
                EventConstants.PRODUCE_DATA_INDEX_GOODPIG_LIVESTOCK, Maps.getLong(livestockMap, "GOOD_PIG_QTY"),
                EventConstants.INDICATOR_BUSINESS_CODE_SPZCL, reportType, runType);

        // 上市-商品猪销售头数
        Map<String, Object> goodsSellMap = reLproDetailMapper.searchProduceDataByGoodsSell(companyMark, startDate, endDate);
        insetReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_BELIST,
                EventConstants.PRODUCE_DATA_INDEX_GOODSALE, Maps.getLong(goodsSellMap, "GOOD_PIG_QTY"),
                EventConstants.INDICATOR_BUSINESS_CODE_SPZXSTS, reportType, runType);
        // 上市-苗猪销售头数
        insetReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_BELIST,
                EventConstants.PRODUCE_DATA_INDEX_MIAOZHU_SALE, Maps.getLong(goodsSellMap, "MZ_PIG_QTY"),
                EventConstants.INDICATOR_BUSINESS_CODE_MZXSTS, reportType, runType);
        // 上市-肥猪销售头数
        insetReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_BELIST,
                EventConstants.PRODUCE_DATA_INDEX_FEIZHU_SALE, Maps.getLong(goodsSellMap, "FZ_PIG_QTY"),
                EventConstants.INDICATOR_BUSINESS_CODE_FZXSTS, reportType, runType);

        // 最优值
        double bestValue = 0;
        // 最优头数
        long maxPigQty = 0;
        // 最优猪场
        long maxFarmId = 0;
        // 最差值
        double worstValue = 0;
        // 最差头数
        long minPigQty = 0;
        // 最差猪场
        long minFarmId = 0;
        // 总计头数
        long sumPigQty = 0;
        // 实际生产
        double groupActual = 0;
        // 是否like companyMark 0: no like 1:like
        long isLike = 0;
        // 是否in accountCompanyClass 0:no in 1:in
        long isIn = 0;
        if (CompanyConstants.ACCOUNT_COMPANY_CLASS_1.equals(accountCompanyClass) || CompanyConstants.ACCOUNT_COMPANY_CLASS_2.equals(
                accountCompanyClass)) {
            isLike = 1;
            isIn = 1;
        } else if (CompanyConstants.ACCOUNT_COMPANY_CLASS_3.equals(accountCompanyClass)) {
            isLike = 1;
        } else if (CompanyConstants.ACCOUNT_COMPANY_CLASS_4.equals(accountCompanyClass)) {
            if (childFarmNum > 0) {
                isLike = 1;
            } else {
                isLike = 0;
            }
        } else if (CompanyConstants.ACCOUNT_COMPANY_CLASS_5.equals(accountCompanyClass)) {
            isLike = 0;
        }

        // 繁殖-配种数
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);
        List<Map<String, Object>> mapBreeds = reLproDetailMapper.searchProduceDataByBreed(paramMap);
        if (mapBreeds != null && !mapBreeds.isEmpty()) {
            maxPigQty = (long) mapBreeds.get(0).get("pigQty");
            maxFarmId = (long) mapBreeds.get(0).get("farmId");
            minPigQty = (long) mapBreeds.get(mapBreeds.size() - 1).get("pigQty");
            minFarmId = (long) mapBreeds.get(mapBreeds.size() - 1).get("farmId");
            for (Map<String, Object> map : mapBreeds) {
                sumPigQty = sumPigQty + (long) map.get("pigQty");
            }
            groupActual = sumPigQty;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MULTIPLY,
                    EventConstants.PRODUCE_DATA_INDEX_BREEDS, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_PZS, reportType,
                    runType);
            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MULTIPLY,
                    EventConstants.PRODUCE_DATA_INDEX_BREEDS, maxPigQty, maxPigQty, maxFarmId, minPigQty, minPigQty, minFarmId, mapBreeds, "pigQty",
                    runType);
        }

        // 繁殖-返+假+流
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);
        List<Map<String, Object>> mapFJJs = reLproDetailMapper.searchProduceDataByFJL(paramMap);
        if (mapFJJs != null && !mapFJJs.isEmpty()) {
            maxPigQty = (long) mapFJJs.get(0).get("pigQty");
            maxFarmId = (long) mapFJJs.get(0).get("farmId");
            minPigQty = (long) mapFJJs.get(mapFJJs.size() - 1).get("pigQty");
            minFarmId = (long) mapFJJs.get(mapFJJs.size() - 1).get("farmId");
            sumPigQty = 0;
            for (Map<String, Object> map : mapFJJs) {
                sumPigQty = sumPigQty + (long) map.get("pigQty");
            }
            groupActual = sumPigQty;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MULTIPLY,
                    EventConstants.PRODUCE_DATA_INDEX_FJL, sumPigQty, groupActual, null, reportType, runType);
            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MULTIPLY,
                    EventConstants.PRODUCE_DATA_INDEX_FJL, maxPigQty, maxPigQty, maxFarmId, minPigQty, minPigQty, minFarmId, mapFJJs, "pigQty",
                    runType);
        }
        // 死亡率
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);
        List<Map<String, Object>> totalMortality = reLproDetailMapper.searchProduceDataByTotalMortality(paramMap);
        List<Map<String, Object>> wholeDies = new ArrayList<Map<String, Object>>();
        if (totalMortality != null && !totalMortality.isEmpty()) {
            // 产房死亡率分场排行
            Collections.sort(totalMortality, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = Maps.getDouble(o1, "deliveryDieChildPigPercent");
                    Double groupActual_ = Maps.getDouble(o2, "deliveryDieChildPigPercent");
                    return groupActual.compareTo(groupActual_);
                }
            });
            bestValue = Maps.getDouble(totalMortality.get(0), "deliveryDieChildPigPercent");
            maxPigQty = Maps.getLong(totalMortality.get(0), "deliveryDieChildPigQty");
            maxFarmId = Maps.getLong(totalMortality.get(0), "farmId");

            worstValue = Maps.getDouble(totalMortality.get(totalMortality.size() - 1), "deliveryDieChildPigPercent");
            minPigQty = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "deliveryDieChildPigQty");
            minFarmId = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_DELIVERY_MORTALITY, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                    totalMortality, "deliveryDieChildPigPercent", runType);

            // 保育死亡率分场排行
            Collections.sort(totalMortality, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = Maps.getDouble(o1, "toCareDieChildPigPercent");
                    Double groupActual_ = Maps.getDouble(o2, "toCareDieChildPigPercent");
                    return groupActual.compareTo(groupActual_);
                }
            });
            bestValue = Maps.getDouble(totalMortality.get(0), "toCareDieChildPigPercent");
            maxPigQty = Maps.getLong(totalMortality.get(0), "toCareDieChildPigQty");
            maxFarmId = Maps.getLong(totalMortality.get(0), "farmId");

            worstValue = Maps.getDouble(totalMortality.get(totalMortality.size() - 1), "toCareDieChildPigPercent");
            minPigQty = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "toCareDieChildPigQty");
            minFarmId = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_CONSERVATION_MORTALITY, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                    totalMortality, "toCareDieChildPigPercent", runType);
            // 育肥死亡率分场排行
            Collections.sort(totalMortality, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = Maps.getDouble(o1, "toFatDieChildPigPercent");
                    Double groupActual_ = Maps.getDouble(o2, "toFatDieChildPigPercent");
                    return groupActual.compareTo(groupActual_);
                }
            });
            bestValue = Maps.getDouble(totalMortality.get(0), "toFatDieChildPigPercent");
            maxPigQty = Maps.getLong(totalMortality.get(0), "toFatDieChildPigQty");
            maxFarmId = Maps.getLong(totalMortality.get(0), "farmId");

            worstValue = Maps.getDouble(totalMortality.get(totalMortality.size() - 1), "toFatDieChildPigPercent");
            minPigQty = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "toFatDieChildPigQty");
            minFarmId = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_HOG_MORTALITY, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                    totalMortality, "toFatDieChildPigPercent", runType);
            // 全程死亡率分场排行
            Collections.sort(totalMortality, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = Maps.getDouble(o1, "allDieChildPigPercent");
                    Double groupActual_ = Maps.getDouble(o2, "allDieChildPigPercent");
                    return groupActual.compareTo(groupActual_);
                }
            });
            bestValue = Maps.getDouble(totalMortality.get(0), "allDieChildPigPercent");
            maxPigQty = Maps.getLong(totalMortality.get(0), "allDieChildPigQty");
            maxFarmId = Maps.getLong(totalMortality.get(0), "farmId");

            worstValue = Maps.getDouble(totalMortality.get(totalMortality.size() - 1), "allDieChildPigPercent");
            minPigQty = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "allDieChildPigQty");
            minFarmId = Maps.getLong(totalMortality.get(totalMortality.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_OVERALL_MORTALITY, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                    totalMortality, "allDieChildPigPercent", runType);

            // 死亡率总场
            double totalDeliveryDieChildPigQty = 0;
            double totalToCareDieChildPigQty = 0;
            double totalToFatDieChildPigQty = 0;
            double totalBirthChildPigQty = 0;
            double totalAllDieChildPigQty = 0;
            for (Map<String, Object> map : totalMortality) {
                totalDeliveryDieChildPigQty += Maps.getLong(map, "deliveryDieChildPigQty");
                totalToCareDieChildPigQty += Maps.getLong(map, "toCareDieChildPigQty");
                totalToFatDieChildPigQty += Maps.getLong(map, "toFatDieChildPigQty");
                totalBirthChildPigQty += Maps.getLong(map, "birthChildPigQty");
                // 各场关键指标对标排名，死亡率
                Map<String, Object> wholeDieMap = new HashMap<String, Object>();
                wholeDieMap.put("farmId", Maps.getLong(map, "farmId"));
                wholeDieMap.put("groupActual", Maps.getDouble(map, "allDieChildPigPercent"));
                wholeDies.add(wholeDieMap);
            }
            totalAllDieChildPigQty = totalDeliveryDieChildPigQty + totalToCareDieChildPigQty + totalToFatDieChildPigQty;

            // 产房总场死亡率
            sumPigQty = (int) totalDeliveryDieChildPigQty;
            groupActual = totalDeliveryDieChildPigQty / totalBirthChildPigQty * 100;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_DELIVERY_MORTALITY, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_CFSWL,
                    reportType, runType);

            // 保育总场死亡率
            sumPigQty = (int) totalToCareDieChildPigQty;
            groupActual = totalToCareDieChildPigQty / totalBirthChildPigQty * 100;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_CONSERVATION_MORTALITY, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_BYSWL,
                    reportType, runType);

            // 育肥总场死亡率
            sumPigQty = (int) totalToFatDieChildPigQty;
            groupActual = totalToFatDieChildPigQty / totalBirthChildPigQty * 100;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_HOG_MORTALITY, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_FZSWL, reportType,
                    runType);

            // 全程总场死亡率
            sumPigQty = (int) totalAllDieChildPigQty;
            groupActual = totalAllDieChildPigQty / totalBirthChildPigQty * 100;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_MORTALITY,
                    EventConstants.PRODUCE_DATA_INDEX_OVERALL_MORTALITY, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_QCSWL,
                    reportType, runType);
        }

        // 流程指标-(分场/总场)分娩率(月)
        if ("2".equals(reportType)) {
            long breedNum = 0;
            double totalBreedNUm = 0.0;
            paramMap.clear();
            paramMap.put("companyMark", companyMark);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            paramMap.put("isLike", isLike);
            paramMap.put("isIn", isIn);
            List<Map<String, Object>> deliveryRates = reLproDetailMapper.searchProduceDataByDeliveryRate(paramMap);
            if (deliveryRates != null && !deliveryRates.isEmpty()) {
                bestValue = Maps.getDouble(deliveryRates.get(0), "groupActual");
                maxPigQty = Maps.getLong(deliveryRates.get(0), "pigQty");
                maxFarmId = Maps.getLong(deliveryRates.get(0), "farmId");

                worstValue = Maps.getDouble(deliveryRates.get(deliveryRates.size() - 1), "groupActual");
                minPigQty = Maps.getLong(deliveryRates.get(deliveryRates.size() - 1), "pigQty");
                minFarmId = Maps.getLong(deliveryRates.get(deliveryRates.size() - 1), "farmId");
                for (Map<String, Object> deliveryRateMap : deliveryRates) {
                    breedNum += Maps.getLong(deliveryRateMap, "pigQty");
                    totalBreedNUm += Maps.getLong(deliveryRateMap, "totalPigQty");
                }
                // 分场分娩率(月)
                insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_DELIVERY_RATE, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                        deliveryRates, "groupActual", runType);
                // 总场分娩率(月)
                groupActual = breedNum / totalBreedNUm * 100;
                insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_DELIVERY_RATE, breedNum, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_FML,
                        reportType, runType);

            }

        } else {
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_DELIVERY_RATE, 0, 0, EventConstants.INDICATOR_BUSINESS_CODE_FML, reportType, runType);
            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_DELIVERY_RATE, 0, 0, Long.valueOf(selectFarm), 0, 0, Long.valueOf(selectFarm), null, null,
                    runType);
        }
        // 流程指标-(分场/总场)7030
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", 0);
        paramMap.put("isIn", 0);
        List<Map<String, Object>> total7030 = reLproDetailMapper.searchProduceDataBy7030(paramMap);
        if (total7030 != null && !total7030.isEmpty()) {
            sumPigQty = Maps.getLong(total7030.get(0), "pigQty");
            groupActual = Maps.getDouble(total7030.get(0), "groupActual");
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_7030, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_7030, reportType, runType);
        }
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);
        List<Map<String, Object>> total7030_ = reLproDetailMapper.searchProduceDataBy7030(paramMap);
        if (total7030_ != null && !total7030_.isEmpty()) {
            bestValue = Maps.getDouble(total7030_.get(0), "groupActual");
            maxPigQty = Maps.getLong(total7030_.get(0), "pigQty");
            maxFarmId = Maps.getLong(total7030_.get(0), "farmId");

            worstValue = Maps.getDouble(total7030_.get(total7030_.size() - 1), "groupActual");
            minPigQty = Maps.getLong(total7030_.get(total7030_.size() - 1), "pigQty");
            minFarmId = Maps.getLong(total7030_.get(total7030_.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_7030, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId, total7030_,
                    "groupActual", runType);
        }

        // 流程指标-(分场/总场)110KG上市日龄
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", 0);
        paramMap.put("isIn", 0);
        List<Map<String, Object>> total110SSRL = reLproDetailMapper.searchProduceDataByTotal110SSRL(paramMap);
        if (total110SSRL != null && !total110SSRL.isEmpty()) {
            sumPigQty = Maps.getLong(total110SSRL.get(0), "pigQty");
            groupActual = Maps.getDouble(total110SSRL.get(0), "groupActual");
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_110AGE, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_110CL, reportType,
                    runType);
        }
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);
        List<Map<String, Object>> total110SSRL_ = reLproDetailMapper.searchProduceDataByTotal110SSRL(paramMap);
        if (total110SSRL_ != null && !total110SSRL_.isEmpty()) {
            bestValue = Maps.getDouble(total110SSRL_.get(0), "groupActual");
            maxPigQty = Maps.getLong(total110SSRL_.get(0), "pigQty");
            maxFarmId = Maps.getLong(total110SSRL_.get(0), "farmId");

            worstValue = Maps.getDouble(total110SSRL_.get(total110SSRL_.size() - 1), "groupActual");
            minPigQty = Maps.getLong(total110SSRL_.get(total110SSRL_.size() - 1), "pigQty");
            minFarmId = Maps.getLong(total110SSRL_.get(total110SSRL_.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_110AGE, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId, total110SSRL_,
                    "groupActual", runType);
        }
        // 肥猪增重
        double fatPigDynamiting = 0;
        List<Map<String, Object>> FCFZLRBs = null;
        List<Map<String, Object>> removeList = new ArrayList<Map<String, Object>>();
        // 流程指标-肥猪料肉比(月) 总场
        if ("2".equals(reportType)) {
            paramMap.clear();
            Date lastMonthDate = TimeUtil.getLastMothByCurrent(TimeUtil.parseDate(startDate));
            paramMap.put("lastMonthDate", lastMonthDate);
            paramMap.put("companyMark", companyMark);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            paramMap.put("isLike", 0);
            paramMap.put("isIn", 0);
            List<Map<String, Object>> FZLRBs = reLproDetailMapper.searchProduceDataByFCFZLRB(paramMap);
            if (FZLRBs != null && !FZLRBs.isEmpty()) {
                sumPigQty = Maps.getLong(FZLRBs.get(0), "pigQty");
                fatPigDynamiting = sumPigQty;
                groupActual = Maps.getDouble(FZLRBs.get(0), "groupActual");
                insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_FEED_MEAT_RATIO, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_QCLRB,
                        reportType, runType);
            }
            paramMap.clear();
            paramMap.put("lastMonthDate", lastMonthDate);
            paramMap.put("companyMark", companyMark);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            paramMap.put("isLike", isLike);
            paramMap.put("isIn", isIn);
            FCFZLRBs = reLproDetailMapper.searchProduceDataByFCFZLRB(paramMap);
            // 将分场肥猪料肉比赋值新的list，进行遍历，之前的list算全程料肉比
            List<Map<String, Object>> FCFZLRBs_ = new ArrayList<Map<String, Object>>();
            FCFZLRBs_.addAll(FCFZLRBs);
            if (FCFZLRBs_ != null && !FCFZLRBs_.isEmpty()) {
                for (Map<String, Object> map : FCFZLRBs_) {
                    if (Maps.getDouble(map, "groupActual") <= 0) {
                        removeList.add(map);
                    }
                }
                FCFZLRBs_.removeAll(removeList);
                bestValue = Maps.getDouble(FCFZLRBs_.get(0), "groupActual");
                maxPigQty = Maps.getLong(FCFZLRBs_.get(0), "pigQty");
                maxFarmId = Maps.getLong(FCFZLRBs_.get(0), "farmId");

                worstValue = Maps.getDouble(FCFZLRBs_.get(FCFZLRBs_.size() - 1), "groupActual");
                minPigQty = Maps.getLong(FCFZLRBs_.get(FCFZLRBs_.size() - 1), "pigQty");
                minFarmId = Maps.getLong(FCFZLRBs_.get(FCFZLRBs_.size() - 1), "farmId");

                insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_FEED_MEAT_RATIO, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                        FCFZLRBs_, "groupActual", runType);
            }

        } else {
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_FEED_MEAT_RATIO, 0, 0, EventConstants.INDICATOR_BUSINESS_CODE_QCLRB, reportType, runType);
            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_FEED_MEAT_RATIO, 0, 0, Long.valueOf(selectFarm), 0, 0, Long.valueOf(selectFarm), null, null,
                    runType);
        }

        // 流程指标-全场料肉比(月) 总场
        List<Map<String, Object>> FCQCLRBs = null;
        if ("2".equals(reportType)) {
            paramMap.clear();
            paramMap.put("fatPigDynamiting", fatPigDynamiting);
            paramMap.put("companyMark", companyMark);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            List<Map<String, Object>> QCLRBs = reLproDetailMapper.searchProduceDataByQCLRB(paramMap);
            if (QCLRBs != null && !QCLRBs.isEmpty()) {
                sumPigQty = Maps.getLong(QCLRBs.get(0), "pigQty");
                groupActual = Maps.getDouble(QCLRBs.get(0), "groupActual");
                insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_FULL_FIELD_MEAT_RATIO, sumPigQty, groupActual, EventConstants.INDICATOR_BUSINESS_CODE_QCLRB,
                        reportType, runType);
            }
            paramMap.clear();
            paramMap.put("companyMark", companyMark);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            paramMap.put("isLike", isLike);
            paramMap.put("isIn", isIn);
            FCQCLRBs = reLproDetailMapper.searchProduceDataByFCQCLRB(paramMap);
            for (Map<String, Object> FCQCLRB : FCQCLRBs) {
                for (Map<String, Object> FCFZLRB : FCFZLRBs) {
                    if (Maps.getLong(FCQCLRB, "farmId") == Maps.getLong(FCFZLRB, "farmId")) {
                        double totalWeight = Maps.getDouble(FCFZLRB, "pigQty") + Maps.getDouble(FCQCLRB, "totalWeight");
                        FCQCLRB.put("totalWeight", totalWeight);
                    }
                }
            }
            for (Map<String, Object> FCQCLRB : FCQCLRBs) {
                if (Maps.getDouble(FCQCLRB, "totalWeight") > 0) {
                    double groupActual_ = Maps.getDouble(FCQCLRB, "outputQty") / Maps.getDouble(FCQCLRB, "totalWeight");
                    FCQCLRB.put("groupActual", groupActual_);
                } else {
                    FCQCLRB.put("groupActual", 0.0);
                }
            }
            Collections.sort(FCQCLRBs, new Comparator<Map<String, Object>>() {

                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = (Double) o1.get("groupActual");
                    Double groupActual_ = (Double) o2.get("groupActual");
                    return groupActual.compareTo(groupActual_);
                }
            });

            if (FCQCLRBs != null && !FCQCLRBs.isEmpty()) {
                removeList.clear();
                for (

                Map<String, Object> map : FCQCLRBs) {
                    if (Maps.getDouble(map, "groupActual") <= 0) {
                        removeList.add(map);
                    }
                }
                FCQCLRBs.removeAll(removeList);
                bestValue = Maps.getDouble(FCQCLRBs.get(0), "groupActual");
                maxPigQty = Maps.getLong(FCQCLRBs.get(0), "totalWeight");
                maxFarmId = Maps.getLong(FCQCLRBs.get(0), "farmId");

                worstValue = Maps.getDouble(FCQCLRBs.get(FCQCLRBs.size() - 1), "groupActual");
                minPigQty = Maps.getLong(FCQCLRBs.get(FCQCLRBs.size() - 1), "totalWeight");
                minFarmId = Maps.getLong(FCQCLRBs.get(FCQCLRBs.size() - 1), "farmId");

                insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_FULL_FIELD_MEAT_RATIO, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                        FCQCLRBs, "groupActual", runType);
            }
        } else {
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_FULL_FIELD_MEAT_RATIO, 0, 0, EventConstants.INDICATOR_BUSINESS_CODE_QCLRB, reportType, runType);
            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_FULL_FIELD_MEAT_RATIO, 0, 0, Long.valueOf(selectFarm), 0, 0, Long.valueOf(selectFarm), null,
                    null, runType);
        }

        // 销售猪只均重
        List<Map<String, Object>> FCXSZZJZs = null;
        if ("2".equals(reportType)) {
            // 分场
            paramMap.put("companyMark", companyMark);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            paramMap.put("isLike", isLike);
            paramMap.put("isIn", isIn);
            FCXSZZJZs = reLproDetailMapper.searchProduceDataByXSZZJZ(paramMap);
            if (FCXSZZJZs != null && !FCXSZZJZs.isEmpty()) {
                bestValue = Maps.getDouble(FCXSZZJZs.get(0), "groupActual");
                maxPigQty = Maps.getLong(FCXSZZJZs.get(0), "saleNum");
                maxFarmId = Maps.getLong(FCXSZZJZs.get(0), "farmId");

                worstValue = Maps.getDouble(FCXSZZJZs.get(FCXSZZJZs.size() - 1), "groupActual");
                minPigQty = Maps.getLong(FCXSZZJZs.get(FCXSZZJZs.size() - 1), "saleNum");
                minFarmId = Maps.getLong(FCXSZZJZs.get(FCXSZZJZs.size() - 1), "farmId");
                insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_AVG_SALE_WEIGHT, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                        FCXSZZJZs, "groupActual", runType);
                long saleNum = 0;
                double totalWeight = 0;
                for (Map<String, Object> map : FCXSZZJZs) {
                    saleNum = saleNum + Maps.getLong(map, "saleNum");
                    totalWeight = totalWeight + Maps.getDouble(map, "totalWeight");
                }
                if (saleNum > 0) {
                    groupActual = totalWeight / saleNum;
                } else {
                    groupActual = 0;
                }
                insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_AVG_SALE_WEIGHT, saleNum, groupActual, null, reportType, runType);
            }
        }

        // 库存猪只均重
        List<Map<String, Object>> FCKCZZJZs = null;
        if ("2".equals(reportType)) {
            // 分场
            paramMap.put("companyMark", companyMark);
            paramMap.put("endDate", endDate);
            paramMap.put("isLike", isLike);
            paramMap.put("isIn", isIn);
            FCKCZZJZs = reLproDetailMapper.searchProduceDataByKCZZJZ(paramMap);
            if (FCKCZZJZs != null && !FCKCZZJZs.isEmpty()) {
                bestValue = Maps.getDouble(FCKCZZJZs.get(0), "groupActual");
                maxPigQty = Maps.getLong(FCKCZZJZs.get(0), "pigQty");
                maxFarmId = Maps.getLong(FCKCZZJZs.get(0), "farmId");

                worstValue = Maps.getDouble(FCKCZZJZs.get(FCKCZZJZs.size() - 1), "groupActual");
                minPigQty = Maps.getLong(FCKCZZJZs.get(FCKCZZJZs.size() - 1), "pigQty");
                minFarmId = Maps.getLong(FCKCZZJZs.get(FCKCZZJZs.size() - 1), "farmId");
                insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_AVG_INVENTORY_WEIGHT, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                        FCKCZZJZs, "groupActual", runType);
                long pigQty = 0;
                double totalWeight = 0;
                for (Map<String, Object> map : FCKCZZJZs) {
                    pigQty = pigQty + Maps.getLong(map, "pigQty");
                    totalWeight = totalWeight + Maps.getDouble(map, "totalWeight");
                }
                if (pigQty > 0) {
                    groupActual = totalWeight / pigQty;
                } else {
                    groupActual = 0;
                }
                insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                        EventConstants.PRODUCE_DATA_INDEX_AVG_INVENTORY_WEIGHT, pigQty, groupActual, null, reportType, runType);
            }
        }

        // 商品猪销售数据(总场)
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", 0);
        paramMap.put("isIn", 0);

        List<ReLsaleDetailModel> goodPigSells = reLsaleDetailMapper.searchProduceDataByGoodPigSell(paramMap);
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);

        List<Map<String, Object>> goodPigSellNotes = reLsaleDetailMapper.searchProduceDataByGoodPigSellNote(paramMap);
        if (goodPigSells != null && !goodPigSells.isEmpty()) {
            goodPigSells.get(0).setIsTotalfield(0L);
            goodPigSells.get(0).setFarmId(Long.valueOf(selectFarm));
            goodPigSells.get(0).setReportId(newReportId);
            goodPigSells.get(0).setCreateId("1".equals(runType) ? 3L : getEmployeeId());
            goodPigSells.get(0).setCreateDate(new Date());
            goodPigSells.get(0).setDeletedFlag(CommonConstants.DELETED_FLAG);
            goodPigSells.get(0).setStatus(CommonConstants.STATUS);
            if (goodPigSellNotes != null && !goodPigSellNotes.isEmpty()) {
                long totalCCZXS = 0;
                long totalZCZZ = 0;
                String bestZCZZSortName = Maps.getString(goodPigSellNotes.get(0), "SORT_NAME");
                long bestZCZZNum = Maps.getLong(goodPigSellNotes.get(0), "ZCZZ");
                Collections.sort(goodPigSellNotes, new Comparator<Map<String, Object>>() {
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        Integer groupActual = Maps.getInt(o1, "CCZXS");
                        Integer groupActual_ = Maps.getInt(o2, "CCZXS");
                        return groupActual.compareTo(groupActual_);
                    }
                });
                String bestCCZXSSortName = Maps.getString(goodPigSellNotes.get(goodPigSellNotes.size() - 1), "SORT_NAME");
                long bestCCZXSNum = Maps.getLong(goodPigSellNotes.get(goodPigSellNotes.size() - 1), "CCZXS");
                for (Map<String, Object> map : goodPigSellNotes) {
                    totalCCZXS += Maps.getLong(map, "CCZXS");
                    totalZCZZ += Maps.getLong(map, "ZCZZ");
                }
                String str = "残次猪销售" + totalCCZXS + "头，猪场自宰" + totalZCZZ + "头，其中" + bestCCZXSSortName + "残次猪销售最多，" + "一共" + bestCCZXSNum + "头，" + "其中"
                        + bestZCZZSortName + "猪场自宰最多" + "一共" + bestZCZZNum + "头。";
                goodPigSells.get(0).setSaleNote(str);
            }

            reLsaleDetailMapper.insert(goodPigSells.get(0));
        }
        // 商品猪销售数据(分场)
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);

        List<ReLsaleDetailModel> goodPigSellList = reLsaleDetailMapper.searchProduceDataByGoodPigSell(paramMap);
        if (goodPigSellList != null && !goodPigSellList.isEmpty()) {
            for (ReLsaleDetailModel model : goodPigSellList) {
                model.setIsTotalfield(1L);
                model.setReportId(newReportId);
                model.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
                model.setCreateDate(new Date());
                model.setDeletedFlag(CommonConstants.DELETED_FLAG);
                model.setStatus(CommonConstants.STATUS);
            }
            reLsaleDetailMapper.inserts(goodPigSellList);
        }
        // 窝均产活仔数
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);

        List<Map<String, Object>> WJHZSList = reLproDetailMapper.searchProduceDataByWJHZS(paramMap);
        if (WJHZSList != null && !WJHZSList.isEmpty()) {
            bestValue = Maps.getDouble(WJHZSList.get(0), "groupActual");
            maxPigQty = Maps.getLong(WJHZSList.get(0), "pigQty");
            maxFarmId = Maps.getLong(WJHZSList.get(0), "farmId");

            worstValue = Maps.getDouble(WJHZSList.get(WJHZSList.size() - 1), "groupActual");
            minPigQty = Maps.getLong(WJHZSList.get(WJHZSList.size() - 1), "pigQty");
            minFarmId = Maps.getLong(WJHZSList.get(WJHZSList.size() - 1), "farmId");
            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_LITTER_SIZE, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId, WJHZSList,
                    "groupActual", runType);
            double nests = 0D;
            long birthPigQty = 0;
            double liveNum = 0D;
            for (Map<String, Object> map : WJHZSList) {
                nests += Maps.getDouble(map, "nests");
                birthPigQty += Maps.getDouble(map, "pigQty");
            }
            liveNum = birthPigQty / nests;
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_LITTER_SIZE, birthPigQty, liveNum, EventConstants.INDICATOR_BUSINESS_CODE_WJCHZS, reportType,
                    runType);
        }

        // 断奶数据（总场/分场）
        Map<Long, Object> farmMap = new HashMap<Long, Object>();
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", 0);
        paramMap.put("isIn", 0);

        List<ReLweanDetailModel> totalReLweanDetailModels = reLweanDetailMapper.searchProduceDataByWeanData(paramMap);
        if (totalReLweanDetailModels != null && !totalReLweanDetailModels.isEmpty()) {
            totalReLweanDetailModels.get(0).setIsTotalfield(0L);
            totalReLweanDetailModels.get(0).setFarmId(Long.valueOf(selectFarm));
            totalReLweanDetailModels.get(0).setReportId(newReportId);
            totalReLweanDetailModels.get(0).setCreateId("1".equals(runType) ? 3L : getEmployeeId());
            totalReLweanDetailModels.get(0).setCreateDate(new Date());
            totalReLweanDetailModels.get(0).setDeletedFlag(CommonConstants.DELETED_FLAG);
            totalReLweanDetailModels.get(0).setStatus(CommonConstants.STATUS);
            reLweanDetailMapper.insert(totalReLweanDetailModels.get(0));

            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_LITTER_WEANING_NUMBER, totalReLweanDetailModels.get(0).getWeanPigNum(), totalReLweanDetailModels
                            .get(0).getAvgWeanNum(), EventConstants.INDICATOR_BUSINESS_CODE_WJDNS, reportType, runType);
            insertProduceDateReLproDetailModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_WEANING_WEIGHT, totalReLweanDetailModels.get(0).getWeanPigNum(), totalReLweanDetailModels.get(0)
                            .getAvgWeanNum() * totalReLweanDetailModels.get(0).getWeanDayLitterWeight(), EventConstants.INDICATOR_BUSINESS_CODE_DNWZ,
                    reportType, runType);
        }
        paramMap.clear();
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);

        List<ReLweanDetailModel> reLweanDetailModels = reLweanDetailMapper.searchProduceDataByWeanData(paramMap);
        if (reLweanDetailModels != null && !reLweanDetailModels.isEmpty()) {
            for (ReLweanDetailModel reLweanDetailModel : reLweanDetailModels) {
                reLweanDetailModel.setIsTotalfield(1L);
                reLweanDetailModel.setReportId(newReportId);
                reLweanDetailModel.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
                reLweanDetailModel.setCreateDate(new Date());
                reLweanDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                reLweanDetailModel.setStatus(CommonConstants.STATUS);
            }
            reLweanDetailMapper.inserts(reLweanDetailModels);

            // 生产数据汇总
            List<Map<String, Object>> reLweanDetails = getMapList(reLweanDetailModels);
            // 生产数据汇总-窝均断奶数
            Collections.sort(reLweanDetails, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = (Double) o1.get("avgWeanNum");
                    Double groupActual_ = (Double) o2.get("avgWeanNum");
                    return groupActual_.compareTo(groupActual);
                }
            });
            bestValue = Maps.getDouble(reLweanDetails.get(0), "avgWeanNum");
            maxPigQty = Maps.getLong(reLweanDetails.get(0), "weanPigNum");
            maxFarmId = Maps.getLong(reLweanDetails.get(0), "farmId");

            worstValue = Maps.getDouble(reLweanDetails.get(reLweanDetails.size() - 1), "avgWeanNum");
            minPigQty = Maps.getLong(reLweanDetails.get(reLweanDetails.size() - 1), "weanPigNum");
            minFarmId = Maps.getLong(reLweanDetails.get(reLweanDetails.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_LITTER_WEANING_NUMBER, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                    reLweanDetails, "avgWeanNum", runType);
            for (Map<String, Object> weanDetailMap : reLweanDetails) {
                weanDetailMap.put("wean24DayLitterWeight", Maps.getDouble(weanDetailMap, "avgWeanNum") * Maps.getDouble(weanDetailMap,
                        "weanDayLitterWeight"));
            }
            // 生产数据汇总-断奶窝重
            Collections.sort(reLweanDetails, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Double groupActual = (Double) o1.get("wean24DayLitterWeight");
                    Double groupActual_ = (Double) o2.get("wean24DayLitterWeight");
                    return groupActual_.compareTo(groupActual);
                }
            });
            bestValue = Maps.getDouble(reLweanDetails.get(0), "wean24DayLitterWeight");
            maxPigQty = Maps.getLong(reLweanDetails.get(0), "weanPigNum");
            maxFarmId = Maps.getLong(reLweanDetails.get(0), "farmId");

            worstValue = Maps.getDouble(reLweanDetails.get(reLweanDetails.size() - 1), "wean24DayLitterWeight");
            minPigQty = Maps.getLong(reLweanDetails.get(reLweanDetails.size() - 1), "weanPigNum");
            minFarmId = Maps.getLong(reLweanDetails.get(reLweanDetails.size() - 1), "farmId");

            insertProduceDateReLproGroupModel(newReportId, Long.valueOf(selectFarm), EventConstants.PRODUCE_DATA_TYPE_PROCESS_INDEX,
                    EventConstants.PRODUCE_DATA_INDEX_WEANING_WEIGHT, bestValue, maxPigQty, maxFarmId, worstValue, minPigQty, minFarmId,
                    reLweanDetails, "wean24DayLitterWeight", runType);
        }

        // 各场关键指标对标排名
        farmMap.clear();
        // 各场关键指标对标排名-商品猪存栏数
        paramMap.clear();
        paramMap.put("isTotalField", 1);
        paramMap.put("selectFarm", selectFarm);
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);

        List<Map<String, Object>> sowLivestock = reLproDetailMapper.searchProduceDataBySowAndGoodPig(paramMap);
        if (sowLivestock != null && !sowLivestock.isEmpty()) {
            for (int m = 0; m < sowLivestock.size(); m++) {
                farmMap.put(Maps.getLong(sowLivestock.get(m), "farmId"), "farmId");
            }
        }
        // 各场关键指标对标排名-母猪存栏数
        paramMap.clear();
        paramMap.put("isTotalField", 2);
        paramMap.put("selectFarm", selectFarm);
        paramMap.put("companyMark", companyMark);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("isLike", isLike);
        paramMap.put("isIn", isIn);

        List<Map<String, Object>> goodLivestock = reLproDetailMapper.searchProduceDataBySowAndGoodPig(paramMap);
        if (goodLivestock != null && !goodLivestock.isEmpty()) {
            for (int n = 0; n < goodLivestock.size(); n++) {
                farmMap.put(Maps.getLong(goodLivestock.get(n), "farmId"), "farmId");
            }
        }
        if (wholeDies != null && !wholeDies.isEmpty()) {
            for (int i = 0; i < wholeDies.size(); i++) {
                wholeDies.get(i).put("ranking", i + 1);
                farmMap.put(Maps.getLong(wholeDies.get(i), "farmId"), "farmId");
            }
        }
        if (total7030_ != null && !total7030_.isEmpty()) {
            for (int j = 0; j < total7030_.size(); j++) {
                total7030_.get(j).put("ranking", j + 1);
                farmMap.put(Maps.getLong(total7030_.get(j), "farmId"), "farmId");
            }
        }
        if (total110SSRL_ != null && !total110SSRL_.isEmpty()) {
            for (int k = 0; k < total110SSRL_.size(); k++) {
                total110SSRL_.get(k).put("ranking", k + 1);
                farmMap.put(Maps.getLong(total110SSRL_.get(k), "farmId"), "farmId");
            }
        }
        if (FCFZLRBs != null && !FCFZLRBs.isEmpty()) {
            for (int k = 0; k < FCFZLRBs.size(); k++) {
                farmMap.put(Maps.getLong(FCFZLRBs.get(k), "farmId"), "farmId");
            }
        }
        if (FCQCLRBs != null && !FCQCLRBs.isEmpty()) {
            for (int k = 0; k < FCQCLRBs.size(); k++) {
                farmMap.put(Maps.getLong(FCQCLRBs.get(k), "farmId"), "farmId");
            }
        }
        if (FCXSZZJZs != null && !FCXSZZJZs.isEmpty()) {
            for (int k = 0; k < FCXSZZJZs.size(); k++) {
                farmMap.put(Maps.getLong(FCXSZZJZs.get(k), "farmId"), "farmId");
            }
        }
        if (FCKCZZJZs != null && !FCKCZZJZs.isEmpty()) {
            for (int k = 0; k < FCKCZZJZs.size(); k++) {
                farmMap.put(Maps.getLong(FCKCZZJZs.get(k), "farmId"), "farmId");
            }
        }

        if (farmMap != null && !farmMap.isEmpty()) {
            for (long key : farmMap.keySet()) {
                ReLindicatorSortModel reLindicatorSortModel = new ReLindicatorSortModel();
                if (sowLivestock != null && !sowLivestock.isEmpty()) {
                    for (Map<String, Object> map : sowLivestock) {
                        if (key == Maps.getLong(map, "farmId")) {
                            reLindicatorSortModel.setSowPigNum(Maps.getLong(map, "pigQty"));
                        }
                    }
                }
                if (goodLivestock != null && !goodLivestock.isEmpty()) {
                    for (Map<String, Object> map_ : goodLivestock) {
                        if (key == Maps.getLong(map_, "farmId")) {
                            reLindicatorSortModel.setGoodPigNum(Maps.getLong(map_, "pigQty"));
                        }
                    }
                }
                if (wholeDies != null && !wholeDies.isEmpty()) {
                    for (Map<String, Object> _map : wholeDies) {
                        if (key == Maps.getLong(_map, "farmId")) {
                            reLindicatorSortModel.setAllDeathRate(Maps.getDouble(_map, "groupActual"));
                            reLindicatorSortModel.setDeathRateSort(Maps.getLong(_map, "ranking"));
                        }
                    }
                }
                if (total7030_ != null && !total7030_.isEmpty()) {
                    for (Map<String, Object> map7030 : total7030_) {
                        if (key == Maps.getLong(map7030, "farmId")) {
                            reLindicatorSortModel.setWeight7030(Maps.getDouble(map7030, "groupActual"));
                            reLindicatorSortModel.setWeightSort7030(Maps.getLong(map7030, "ranking"));
                        }
                    }
                }
                if (total110SSRL_ != null && !total110SSRL_.isEmpty()) {
                    for (Map<String, Object> map110SSRL : total110SSRL_) {
                        if (key == Maps.getLong(map110SSRL, "farmId")) {
                            reLindicatorSortModel.setOutDayage110kg(Maps.getDouble(map110SSRL, "groupActual"));
                            reLindicatorSortModel.setOutDayageSort110kg(Maps.getLong(map110SSRL, "ranking"));
                        }
                    }
                }
                if (FCFZLRBs != null && !FCFZLRBs.isEmpty()) {
                    for (Map<String, Object> mapFCFZLRB : FCFZLRBs) {
                        if (key == Maps.getLong(mapFCFZLRB, "farmId")) {
                            reLindicatorSortModel.setMaterialMeatRateFat(Maps.getDouble(mapFCFZLRB, "groupActual"));
                        }
                    }
                }
                if (FCQCLRBs != null && !FCQCLRBs.isEmpty()) {
                    for (Map<String, Object> mapFCQCLRB : FCQCLRBs) {
                        if (key == Maps.getLong(mapFCQCLRB, "farmId")) {
                            reLindicatorSortModel.setMaterialMeatRateWhole(Maps.getDouble(mapFCQCLRB, "groupActual"));
                        }
                    }
                }
                if (FCXSZZJZs != null && !FCXSZZJZs.isEmpty()) {
                    for (Map<String, Object> mapFCXSZZJZ : FCXSZZJZs) {
                        if (key == Maps.getLong(mapFCXSZZJZ, "farmId")) {
                            reLindicatorSortModel.setAvgSalePigWeight(Maps.getDouble(mapFCXSZZJZ, "groupActual"));
                        }
                    }
                }
                if (FCKCZZJZs != null && !FCKCZZJZs.isEmpty()) {
                    for (Map<String, Object> mapFCKCZZJZ : FCKCZZJZs) {
                        if (key == Maps.getLong(mapFCKCZZJZ, "farmId")) {
                            reLindicatorSortModel.setAvgInventoryPigWeight(Maps.getDouble(mapFCKCZZJZ, "groupActual"));
                        }
                    }
                }

                reLindicatorSortModel.setFarmId(key);
                reLindicatorSortModel.setReportId(newReportId);
                reLindicatorSortModel.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
                reLindicatorSortModel.setCreateDate(new Date());
                reLindicatorSortModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                reLindicatorSortModel.setStatus(CommonConstants.STATUS);
                reLindicatorSortMapper.insert(reLindicatorSortModel);
            }
        }
    }

    public void insetReLproDetailModel(long newReportId, long selectFarm, long proType, long indicator, long pigQty, String businessCode,
            String reportType, String runType) {
        ReLproDetailModel reLproDetailModelLivestock = new ReLproDetailModel();
        List<Map<String, Object>> indicators = reLproDetailMapper.searchProduceDataByTarget(String.valueOf(selectFarm), reportType);
        long standardValue = 0;
        for (Map<String, Object> map : indicators) {
            if (businessCode != null && businessCode.equals(Maps.getString(map, "businessCode"))) {
                standardValue = Maps.getLong(map, "standardValue");
                break;
            }
        }
        reLproDetailModelLivestock.setReportId(newReportId);
        reLproDetailModelLivestock.setPigQty(pigQty);
        reLproDetailModelLivestock.setProType(proType);
        reLproDetailModelLivestock.setIndicator(indicator);
        reLproDetailModelLivestock.setGroupTarget(standardValue);
        reLproDetailModelLivestock.setGroupActual(0.0);
        reLproDetailModelLivestock.setFarmId(Long.valueOf(selectFarm));
        reLproDetailModelLivestock.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
        reLproDetailModelLivestock.setCreateDate(new Date());
        reLproDetailModelLivestock.setDeletedFlag(CommonConstants.DELETED_FLAG);
        reLproDetailModelLivestock.setStatus(CommonConstants.STATUS);
        if (reLproDetailModelLivestock != null) {
            reLproDetailMapper.insert(reLproDetailModelLivestock);
        }

    }

    public void insertProduceDateReLproDetailModel(long newReportId, long selectFarm, long proType, long indicator, long sumPigQty,
            double groupActual, String businessCode, String reportType, String runType) {
        ReLproDetailModel reLproDetailModel = new ReLproDetailModel();
        List<Map<String, Object>> indicators = reLproDetailMapper.searchProduceDataByTarget(String.valueOf(selectFarm), reportType);
        long standardValue = 0;
        for (Map<String, Object> map : indicators) {
            if (businessCode != null && businessCode.equals(Maps.getString(map, "businessCode"))) {
                standardValue = Maps.getLong(map, "standardValue");
                break;
            }
        }
        reLproDetailModel.setReportId(newReportId);
        reLproDetailModel.setFarmId(Long.valueOf(selectFarm));
        reLproDetailModel.setProType(proType);
        reLproDetailModel.setIndicator(indicator);
        reLproDetailModel.setPigQty(sumPigQty);
        reLproDetailModel.setGroupTarget(standardValue);// "去指标库数据"
        reLproDetailModel.setGroupActual(groupActual);
        reLproDetailModel.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
        reLproDetailModel.setCreateDate(new Date());
        reLproDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        reLproDetailModel.setStatus(CommonConstants.STATUS);

        if (reLproDetailModel != null) {
            reLproDetailMapper.insert(reLproDetailModel);
        }
    }

    public void insertProduceDateReLproGroupModel(long newReportId, long selectFarm, long proType, long indicator, double bestValue, long maxPigQty,
            long maxFarmId, double worstValue, long minPigQty, long minFarmId, List<Map<String, Object>> dataList, String key, String runType) {
        ReLproGroupModel reLproGroupModel = new ReLproGroupModel();

        reLproGroupModel.setReportId(newReportId);
        reLproGroupModel.setFarmId(Long.valueOf(selectFarm));
        reLproGroupModel.setProType(proType);
        reLproGroupModel.setIndicator(indicator);
        reLproGroupModel.setBestValue(bestValue);
        reLproGroupModel.setBestPigQty(maxPigQty);
        reLproGroupModel.setBestFarmId(maxFarmId);
        reLproGroupModel.setWorstValue(worstValue);
        reLproGroupModel.setWorstPigQty(minPigQty);
        reLproGroupModel.setWorstFarmId(minFarmId);
        reLproGroupModel.setCreateId("1".equals(runType) ? 3L : getEmployeeId());
        reLproGroupModel.setCreateDate(new Date());
        reLproGroupModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        reLproGroupModel.setStatus(CommonConstants.STATUS);

        if (reLproGroupModel != null) {
            reLproGroupMapper.insert(reLproGroupModel);
        }
        // 最优相同公司指标
        List<Map<String, Object>> bestIdenticalList = new ArrayList<Map<String, Object>>();
        // 最差相同公司指标
        List<Map<String, Object>> worstIdenticalList = new ArrayList<Map<String, Object>>();
        // 复制指标list
        List<Map<String, Object>> identicalList = new ArrayList<Map<String, Object>>();
        //
        StringBuffer sb = new StringBuffer();
        if (dataList != null && !dataList.isEmpty()) {
            identicalList.addAll(dataList);
            identicalList.remove(0);
            if (identicalList != null && !identicalList.isEmpty()) {
                identicalList.remove(identicalList.size() - 1);
            }
            if (identicalList != null && !identicalList.isEmpty()) {
                for (Map<String, Object> identicalListMap : identicalList) {
                    if (Maps.getDouble(identicalListMap, key) == bestValue) {
                        bestIdenticalList.add(identicalListMap);
                    }
                    if (Maps.getDouble(identicalListMap, key) == worstValue) {
                        worstIdenticalList.add(identicalListMap);
                    }
                }
            }
            if (bestIdenticalList != null && !bestIdenticalList.isEmpty()) {
                sb.append("最优猪场还有：");
                for (int i = 0; i < bestIdenticalList.size(); i++) {
                    if (i == bestIdenticalList.size() - 1) {
                        try {
                            Map<String, String> bestSortNameMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(bestIdenticalList.get(i),
                                    "farmId"));
                            String bestSortName = Maps.getString(bestSortNameMap, "SORT_NAME");
                            sb.append(bestSortName);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Map<String, String> bestSortNameMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(bestIdenticalList.get(i),
                                    "farmId"));
                            String bestSortName = Maps.getString(bestSortNameMap, "SORT_NAME");
                            sb.append(bestSortName);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        sb.append(",");
                    }
                }
            }
            if (worstIdenticalList != null && !worstIdenticalList.isEmpty()) {
                if (bestIdenticalList != null && !bestIdenticalList.isEmpty()) {
                    sb.append(",最差猪场还有：");
                } else {
                    sb.append("最差猪场还有：");
                }
                for (int j = 0; j < worstIdenticalList.size(); j++) {
                    if (j == worstIdenticalList.size() - 1) {
                        try {
                            Map<String, String> worstSortNameMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(worstIdenticalList.get(j),
                                    "farmId"));
                            String worstSortName = Maps.getString(worstSortNameMap, "SORT_NAME");
                            sb.append(worstSortName);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Map<String, String> worstSortNameMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(worstIdenticalList.get(j),
                                    "farmId"));
                            String worstSortName = Maps.getString(worstSortNameMap, "SORT_NAME");
                            sb.append(worstSortName);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        sb.append(",");
                    }
                }
            }
            if (StringUtil.isNonBlank(sb.toString())) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(sb.toString(), " UPDATE re_l_pro_group SET NOTES=? ");
                sqlCon.addCondition(newReportId, " WHERE DELETED_FLAG = '0' AND REPORT_ID=? ");
                sqlCon.addCondition(proType, " AND PRO_TYPE=? ");
                sqlCon.addCondition(indicator, " AND INDICATOR=? ");
                sqlCon.addCondition(selectFarm, " AND FARM_ID=? ");
                setSql(reLproDetailMapper, sqlCon);
            }
        }
    }

    @Override
    public BasePageInfo searchProduceDataCollectPage(String searchReportType, String startDate, String endDate, String version, String isHis)
            throws Exception {
        setToPage();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("farmId", getFarmId());
        if (StringUtil.isNonBlank(searchReportType)) {
            map.put("searchReportType", searchReportType);
        }
        if (StringUtil.isNonBlank(startDate)) {
            map.put("startDate", startDate);
        }
        if (StringUtil.isNonBlank(endDate)) {
            map.put("endDate", endDate);
        }
        if (StringUtil.isNonBlank(version)) {
            map.put("version", version);
        }
        if (StringUtil.isNonBlank(isHis)) {
            map.put("isHis", isHis);
        }
        List<Map<String, Object>> reMreportModelList = reMreportMapper.searchProductionCollectPage(map);
        if (reMreportModelList != null && !reMreportModelList.isEmpty()) {
            for (Map<String, Object> reMreportModelMap : reMreportModelList) {
                if ("1".equals(Maps.getString(reMreportModelMap, "reportType"))) {
                    reMreportModelMap.put("reportTypeName", "周报");
                } else if ("2".equals(Maps.getString(reMreportModelMap, "reportType"))) {
                    reMreportModelMap.put("reportTypeName", "月报");
                } else if ("3".equals(Maps.getString(reMreportModelMap, "reportType"))) {
                    reMreportModelMap.put("reportTypeName", "季度报");
                } else if ("4".equals(Maps.getString(reMreportModelMap, "reportType"))) {
                    reMreportModelMap.put("reportTypeName", "年报");
                }
                if (Maps.getString(reMreportModelMap, "createId") != null) {
                    EmployeeModel createEmployeeModel = employeeMapper.searchById(Maps.getLong(reMreportModelMap, "createId"));
                    if (createEmployeeModel != null) {
                        reMreportModelMap.put("createName", createEmployeeModel.getEmployeeName());
                    }
                }
                if (Maps.getString(reMreportModelMap, "notesId") != null) {
                    EmployeeModel noteEmployeeModel = employeeMapper.searchById(Maps.getLong(reMreportModelMap, "notesId"));
                    if (noteEmployeeModel != null) {
                        reMreportModelMap.put("notesName", noteEmployeeModel.getEmployeeName());
                    }
                }
            }
        }

        return getPageInfo(reMreportModelList);
    }

    @Override
    public List<Map<String, Object>> searchProduceDataCollectToList(String rowId, String searchType) throws Exception {
        if (StringUtil.isBlank(rowId)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "报表ID不能为空！");
        }
        if (StringUtil.isBlank(searchType)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "查询类型不能为空！");
        }
        List<Map<String, Object>> produceDataList = new ArrayList<Map<String, Object>>();
        Map<String, String> sqlMap = new HashMap<String, String>();
        Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(getFarmId()));
        String companyMark = Maps.getString(companyInfoMap, "COMPANY_MARK");
        if ("1".equals(searchType) || "2".equals(searchType)) {
            // 生产数据汇总
            SqlCon sqlCon1 = new SqlCon();
            sqlCon1.addMainSql(
                    " SELECT T0.ROW_ID rowId,T0.DELETED_FLAG deletedFlag,T0.REPORT_ID reportId,T0.FARM_ID farmId,T0.PRO_TYPE proType,T0.INDICATOR indicator,T0.PIG_QTY pigQty,T0.GROUP_TARGET groupTarget,ROUND(T0.GROUP_ACTUAL,2) groupActual,T1.CODE_NAME proTypeName,T2.CODE_NAME indicatorName, ");
            sqlCon1.addMainSql(
                    " ROUND(T3.BEST_PIG_QTY,2) bestPigQty,ROUND(T3.BEST_VALUE,2) bestValue,T4.SORT_NAME bestFarmName,ROUND(T3.WORST_PIG_QTY,2) worstPigQty,ROUND(T3.WORST_VALUE,2) worstValue,T5.SORT_NAME worstFarmName, T3.NOTES notes  ");
            sqlCon1.addMainSql(" FROM re_l_pro_detail T0 ");
            sqlCon1.addMainSql(" LEFT JOIN cd_l_code_list T1 ON T0.PRO_TYPE=T1.CODE_VALUE AND T1.TYPE_CODE='PRODUCE_DATA_TYPE' ");
            sqlCon1.addMainSql(" LEFT JOIN cd_l_code_list T2 ON T0.INDICATOR=T2.CODE_VALUE AND T2.TYPE_CODE='PRODUCE_DATA_INDEX' ");
            sqlCon1.addMainSql(
                    " LEFT JOIN re_l_pro_group T3 ON T0.REPORT_ID=T3.REPORT_ID AND T0.PRO_TYPE=T3.PRO_TYPE AND T0.INDICATOR=T3.INDICATOR AND T3.DELETED_FLAG='0' ");
            sqlCon1.addMainSql(" LEFT JOIN hr_m_company T4 ON T3.BEST_FARM_ID=T4.ROW_ID AND T4.DELETED_FLAG='0' ");
            sqlCon1.addMainSql(" LEFT JOIN hr_m_company T5 ON T3.WORST_FARM_ID=T5.ROW_ID AND T5.DELETED_FLAG='0' ");
            sqlCon1.addMainSql(" WHERE T0.DELETED_FLAG='0' ");
            sqlCon1.addCondition(getFarmId(), " AND T0.FARM_ID=? ");
            sqlCon1.addCondition(rowId, " AND T0.REPORT_ID=? ");
            sqlMap.put("sql", sqlCon1.getCondition());
            List<Map<String, Object>> proDetailList = paramMapper.getObjectInfos(sqlMap);

            if (proDetailList != null && !proDetailList.isEmpty()) {
                if ("1".equals(searchType)) {
                    for (Map<String, Object> map : proDetailList) {
                        if (Maps.getLong(map, "proType") == EventConstants.PRODUCE_DATA_TYPE_LIVESTOCK || Maps.getLong(map,
                                "proType") == EventConstants.PRODUCE_DATA_TYPE_BELIST) {
                            produceDataList.add(map);
                        }
                    }
                }
                if ("2".equals(searchType)) {
                    for (Map<String, Object> map : proDetailList) {
                        if (Maps.getLong(map, "proType") != EventConstants.PRODUCE_DATA_TYPE_LIVESTOCK && Maps.getLong(map,
                                "proType") != EventConstants.PRODUCE_DATA_TYPE_BELIST) {
                            produceDataList.add(map);
                        }
                    }
                }
            }
        } else if ("3".equals(searchType)) {
            SqlCon sqlCon2 = new SqlCon();
            sqlCon2.addMainSql(
                    " SELECT T0.ROW_ID rowId,T0.REPORT_ID reportId,T0.TOTAL_NUM totalNum,T0.MZ_NUM mzNum,T0.MZ_AVG_WEIGHT mzAvgWeight,T0.MZ_PRICE mzPrice,T0.MZ_TOTAL_PRICE mzTotalPrice,T0.RZ_NUM rzNum, ");
            sqlCon2.addMainSql(
                    " T0.RZ_AVG_WEIGHT rzAvgWeight,T0.RZ_PRICE rzPrice,T0.RZ_TOTAL_PRICE rzTotalPrice,T0.ZZ_NUM zzNum,T0.ZZ_AVG_WEIGHT zzAvgWeight,T0.ZZ_PRICE zzPrice,T0.ZZ_TOTAL_PRICE zzTotalPrice,T0.UNBLANCE_NUM unblanceNum,T0.YEAR_SALE_TARGET yearSaleTarget,T0.COMPLETE_RATE completeRate,T1.SORT_NAME farmName ");
            sqlCon2.addMainSql(" FROM re_l_sale_detail T0 ");
            sqlCon2.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID=T1.ROW_ID AND T1.DELETED_FLAG='0' ");
            sqlCon2.addMainSql(" WHERE T0.DELETED_FLAG='0' ");
            sqlCon2.addCondition(companyMark, " AND ( T1.COMPANY_MARK = ? ");
            sqlCon2.addCondition(companyMark + ",%", " OR T1.COMPANY_MARK LIKE ? )");
            sqlCon2.addCondition(rowId, " AND T0.REPORT_ID=? ");
            sqlMap.clear();
            sqlMap.put("sql", sqlCon2.getCondition());
            List<Map<String, Object>> saleDetailList = paramMapper.getObjectInfos(sqlMap);
            produceDataList.addAll(saleDetailList);

        } else if ("4".equals(searchType)) {
            SqlCon sqlCon3 = new SqlCon();
            sqlCon3.addMainSql(
                    " SELECT T0.ROW_ID rowId,T0.REPORT_ID reportId,T0.WEAN_TOTAL_SIZE weanTotalSize,T0.LIVE_NUM liveNum,ROUND(T0.BIRTH_AVG_WEIGHT,2) birthAvgWeight,ROUND(T0.WEAN_AVG_DAYAGE,2) weanAvgDayage,T0.WEAN_PIG_NUM weanPigNum,");
            sqlCon3.addMainSql(
                    " ROUND(T0.AVG_WEAN_NUM,2) avgWeanNum,ROUND(T0.AVG_WEAN_WEIGHT,2) avgWeanWeight,ROUND(T0.WEAN_LITTER_WEIGHT,2) weanLitterWeight,ROUND(T0.WEAN_DAY_LITTER_WEIGHT,2) weanDayLitterWeight,ROUND(T0.AVG_DRF_USED_NUM,2) avgDrfUsedNum,ROUND(T0.AVG_JCW_USED_NUM,2) avgJcwUsedNum, T1.SORT_NAME farmName ");
            sqlCon3.addMainSql(" FROM re_l_wean_detail T0 ");
            sqlCon3.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID=T1.ROW_ID AND T1.DELETED_FLAG='0' ");
            sqlCon3.addMainSql(" WHERE T0.DELETED_FLAG='0' ");
            sqlCon3.addCondition(companyMark, " AND ( T1.COMPANY_MARK = ? ");
            sqlCon3.addCondition(companyMark + ",%", " OR T1.COMPANY_MARK LIKE ? )");
            sqlCon3.addCondition(rowId, " AND T0.REPORT_ID=? ");
            sqlMap.clear();
            sqlMap.put("sql", sqlCon3.getCondition());
            List<Map<String, Object>> saleDetailList = paramMapper.getObjectInfos(sqlMap);
            produceDataList.addAll(saleDetailList);
        } else if ("5".equals(searchType)) {
            SqlCon sqlCon4 = new SqlCon();
            sqlCon4.addMainSql(
                    " SELECT T0.ROW_ID rowId,T0.REPORT_ID reportId,T0.SOW_PIG_NUM sowPigNum,T0.GOOD_PIG_NUM goodPigNum,T0.ALL_DEATH_RATE allDeathRate,T0.DEATH_RATE_SORT deathRateSort,T0.WEIGHT_7030 weight7030,T0.WEIGHT_SORT_7030 weightSort7030,");
            sqlCon4.addMainSql(
                    " T0.OUT_DAYAGE_110KG outDayage110kg,T0.OUT_DAYAGE_SORT_110KG outDayageSort110kg,T0.MATERIAL_MEAT_RATE_WHOLE materialMeatRateWhole,T0.MATERIAL_MEAT_RATE_FAT materialMeatRateFat,T1.SORT_NAME farmName ");
            sqlCon4.addMainSql(" FROM re_l_indicator_sort T0 ");
            sqlCon4.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID=T1.ROW_ID AND T1.DELETED_FLAG='0' ");
            sqlCon4.addMainSql(" WHERE T0.DELETED_FLAG='0' ");
            sqlCon4.addCondition(companyMark, " AND ( T1.COMPANY_MARK = ? ");
            sqlCon4.addCondition(companyMark + ",%", " OR T1.COMPANY_MARK LIKE ? )");
            sqlCon4.addCondition(rowId, " AND T0.REPORT_ID=? ");
            sqlMap.clear();
            sqlMap.put("sql", sqlCon4.getCondition());
            List<Map<String, Object>> saleDetailList = paramMapper.getObjectInfos(sqlMap);
            produceDataList.addAll(saleDetailList);
        } else if ("6".equals(searchType)) {
            SqlCon sqlCon5 = new SqlCon();
            sqlCon5.addMainSql(
                    " SELECT PRODUCTION_NOTES productionNotes,BIOLOGICAL_SAFETY_NOTES biologicalSafetyNotes,POPULATION_PLANNING_NOTES populationPlanningNotes,SALE_NOTES saleNotes FROM re_m_report WHERE ");
            sqlCon5.addCondition(rowId, "  ROW_ID=? ");
            sqlMap.clear();
            sqlMap.put("sql", sqlCon5.getCondition());
            List<Map<String, Object>> notesList = paramMapper.getObjectInfos(sqlMap);
            produceDataList.addAll(notesList);
        } else if ("7".equals(searchType)) {
            // 获取报表主表基本信息用于微信端填写分析显示
            SqlCon sqlCon6 = new SqlCon();
            sqlCon6.addMainSql(" SELECT T0.ROW_ID rowId,T0.FARM_ID farmId,T1.COMPANY_NAME companyName,T1.SORT_NAME sortName, ");
            sqlCon6.addMainSql(" T0.REPORT_TYPE reportType,T2.CODE_NAME reportTypeName, ");
            sqlCon6.addMainSql(" CONCAT(T1.SORT_NAME,'-',T2.CODE_NAME) displayTitle, ");
            sqlCon6.addMainSql(" DATE(T0.START_DATE) startDate,DATE(T0.END_DATE) endDate, ");
            sqlCon6.addMainSql(" CONCAT(DATE(T0.START_DATE),'~',DATE(T0.END_DATE)) displayDate, ");
            sqlCon6.addMainSql(" T0.PRODUCTION_NOTES productionNotes,T0.BIOLOGICAL_SAFETY_NOTES biologicalSafetyNotes, ");
            sqlCon6.addMainSql(" T0.POPULATION_PLANNING_NOTES populationPlanningNotes,T0.SALE_NOTES saleNotes ");
            sqlCon6.addMainSql(" FROM re_m_report T0 ");
            sqlCon6.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
            sqlCon6.addMainSql(" LEFT JOIN (SELECT cd.CODE_NAME,cd.CODE_VALUE FROM cd_l_code_list cd WHERE cd.DELETED_FLAG = '0' ");
            sqlCon6.addMainSql(" AND cd.STATUS = '1' AND cd.TYPE_CODE = 'REPORT_TYPE') T2 ON T2.CODE_VALUE = T0.REPORT_TYPE ");
            sqlCon6.addMainSql(" WHERE T0.DELETED_FLAG = '0'AND T0.STATUS = '1' ");
            sqlCon6.addCondition(rowId, " AND T0.ROW_ID=? ");
            sqlMap.clear();
            sqlMap.put("sql", sqlCon6.getCondition());
            List<Map<String, Object>> mainInfoList = paramMapper.getObjectInfos(sqlMap);
            produceDataList.addAll(mainInfoList);
        } else if ("8".equals(searchType)) {
            // 获取报表数据反馈明细用于场长微信端填写数据反馈内容
            SqlCon sqlCon7 = new SqlCon();
            sqlCon7.addMainSql(" SELECT T0.ROW_ID rowId,T0.FARM_ID farmId,T1.COMPANY_NAME companyName,T1.SORT_NAME sortName, ");
            sqlCon7.addMainSql(" T0.REPORT_TYPE reportType,T2.CODE_NAME reportTypeName, ");
            sqlCon7.addMainSql(" CONCAT(T1.SORT_NAME,'-',T2.CODE_NAME) displayTitle, ");
            sqlCon7.addMainSql(" DATE(T0.START_DATE) startDate,DATE(T0.END_DATE) endDate, ");
            sqlCon7.addMainSql(" CONCAT(DATE(T0.START_DATE),'~',DATE(T0.END_DATE)) displayDate, ");
            sqlCon7.addMainSql(" T3.ROW_ID feedbackId,T3.CONTENT content ");
            sqlCon7.addMainSql(" FROM re_m_report T0 ");
            sqlCon7.addMainSql(" LEFT JOIN hr_m_company T1 ON T0.FARM_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
            sqlCon7.addMainSql(" LEFT JOIN (SELECT cd.CODE_NAME,cd.CODE_VALUE FROM cd_l_code_list cd WHERE cd.DELETED_FLAG = '0' ");
            sqlCon7.addMainSql(" AND cd.STATUS = '1' AND cd.TYPE_CODE = 'REPORT_TYPE') T2 ON T2.CODE_VALUE = T0.REPORT_TYPE ");
            sqlCon7.addMainSql(" LEFT JOIN re_l_feedback T3 ON T0.ROW_ID = T3.REPORT_ID AND T3.DELETED_FLAG = '0' AND T3.STATUS = '1' ");
            sqlCon7.addCondition(getFarmId(), " AND T3.FARM_ID=? ");
            sqlCon7.addMainSql(" WHERE T0.DELETED_FLAG = '0'AND T0.STATUS = '1' ");
            sqlCon7.addCondition(rowId, " AND T0.ROW_ID=? ");
            sqlMap.clear();
            sqlMap.put("sql", sqlCon7.getCondition());
            List<Map<String, Object>> mainInfoList = paramMapper.getObjectInfos(sqlMap);
            produceDataList.addAll(mainInfoList);
        }
        return produceDataList;
    }

    @Override
    public void editProduceDataCollectNotes(Map<String, Object> inputMap) throws Exception {

        List<HashMap> listTableProData1 = getJsonList(Maps.getString(inputMap, "listTableProData1"), HashMap.class);
        List<HashMap> listTableProData2 = getJsonList(Maps.getString(inputMap, "listTableProData2"), HashMap.class);
        List<HashMap> listTableGoodPigSell = getJsonList(Maps.getString(inputMap, "listTableGoodPigSell"), HashMap.class);
        List<HashMap> listTableWeanData = getJsonList(Maps.getString(inputMap, "listTableWeanData"), HashMap.class);
        List<HashMap> listTableKeyIndicator = getJsonList(Maps.getString(inputMap, "listTableKeyIndicator"), HashMap.class);
        String productionNote = Maps.getString(inputMap, "productionNotesKey");
        String biologicalSafetyNotes = Maps.getString(inputMap, "biologicalSafetyNotesKey");
        String populationPlanningNotes = Maps.getString(inputMap, "populationPlanningNotesKey");
        String saleNotes = Maps.getString(inputMap, "saleNotesKey");
        // 场长反馈数据
        String feedbackContent = Maps.getString(inputMap, "feedbackContent");
        Long reportId = Maps.getLong(inputMap, "reportId");
        String feedbackId = Maps.getString(inputMap, "feedbackId");
        if (listTableProData1 != null && !listTableProData1.isEmpty()) {
            for (Map<String, Object> map : listTableProData1) {
                if (StringUtil.isNonBlank(Maps.getString(map, "notes"))) {
                    ReLproDetailModel reLproDetailModel = new ReLproDetailModel();
                    reLproDetailModel.setRowId(Maps.getLongClass(map, "rowId"));
                    reLproDetailModel.setNotes(Maps.getString(map, "notes"));
                    reLproDetailMapper.update(reLproDetailModel);
                }
            }
        }
        if (listTableProData2 != null && !listTableProData2.isEmpty()) {
            for (Map<String, Object> map : listTableProData2) {
                if (StringUtil.isNonBlank(Maps.getString(map, "notes"))) {
                    ReLproDetailModel reLproDetailModel = new ReLproDetailModel();
                    reLproDetailModel.setRowId(Maps.getLongClass(map, "rowId"));
                    reLproDetailModel.setNotes(Maps.getString(map, "notes"));
                    reLproDetailMapper.update(reLproDetailModel);
                }
            }
        }
        if (listTableGoodPigSell != null && !listTableGoodPigSell.isEmpty()) {
            for (Map<String, Object> map : listTableGoodPigSell) {
                if (StringUtil.isNonBlank(Maps.getString(map, "notes"))) {
                    ReLsaleDetailModel reLsaleDetailModel = new ReLsaleDetailModel();
                    reLsaleDetailModel.setRowId(Maps.getLongClass(map, "rowId"));
                    reLsaleDetailModel.setNotes(Maps.getString(map, "notes"));
                    reLsaleDetailMapper.update(reLsaleDetailModel);
                }
            }
        }
        if (listTableWeanData != null && !listTableWeanData.isEmpty()) {
            for (Map<String, Object> map : listTableWeanData) {
                if (StringUtil.isNonBlank(Maps.getString(map, "notes"))) {
                    ReLweanDetailModel reLweanDetailModel = new ReLweanDetailModel();
                    reLweanDetailModel.setRowId(Maps.getLongClass(map, "rowId"));
                    reLweanDetailModel.setNotes(Maps.getString(map, "notes"));
                    reLweanDetailMapper.update(reLweanDetailModel);
                }
            }
        }
        if (listTableKeyIndicator != null && !listTableKeyIndicator.isEmpty()) {
            for (Map<String, Object> map : listTableKeyIndicator) {
                if (StringUtil.isNonBlank(Maps.getString(map, "notes"))) {
                    ReLindicatorSortModel reLindicatorSortModel = new ReLindicatorSortModel();
                    reLindicatorSortModel.setRowId(Maps.getLongClass(map, "rowId"));
                    reLindicatorSortModel.setNotes(Maps.getString(map, "notes"));
                    reLindicatorSortMapper.update(reLindicatorSortModel);
                }
            }
        }
        if (reportId != null) {
            if (StringUtil.isNonBlank(feedbackContent)) {
                ReLfeedbackModel reLfeedbackModel = new ReLfeedbackModel();
                reLfeedbackModel.setReportId(reportId);
                reLfeedbackModel.setFarmId(getFarmId());
                reLfeedbackModel.setContent(feedbackContent);
                reLfeedbackModel.setCreateDate(TimeUtil.getSysTimestamp());
                reLfeedbackModel.setCreateId(getEmployeeId());
                if (feedbackId != null) {
                    reLfeedbackModel.setRowId(Long.parseLong(feedbackId));
                    reLfeedbackMapper.update(reLfeedbackModel);
                } else {
                    reLfeedbackModel.setStatus("1");
                    reLfeedbackModel.setDeletedFlag("0");
                    reLfeedbackMapper.insert(reLfeedbackModel);
                }
            }
            if (StringUtil.isNonBlank(productionNote) || StringUtil.isNonBlank(biologicalSafetyNotes) || StringUtil.isNonBlank(
                    populationPlanningNotes) || StringUtil.isNonBlank(saleNotes)) {
                SqlCon updateDataAnalysis = new SqlCon();
                updateDataAnalysis.addMainSql(" UPDATE re_m_report SET ");
                updateDataAnalysis.addCondition(getEmployeeId(), " NOTES_ID=? ");
                if (StringUtil.isNonBlank(productionNote)) {
                    updateDataAnalysis.addCondition(productionNote, " ,PRODUCTION_NOTES=? ");
                }
                if (StringUtil.isNonBlank(biologicalSafetyNotes)) {
                    updateDataAnalysis.addCondition(biologicalSafetyNotes, " ,BIOLOGICAL_SAFETY_NOTES=? ");
                }
                if (StringUtil.isNonBlank(populationPlanningNotes)) {
                    updateDataAnalysis.addCondition(populationPlanningNotes, " ,POPULATION_PLANNING_NOTES=? ");
                }
                if (StringUtil.isNonBlank(saleNotes)) {
                    updateDataAnalysis.addCondition(saleNotes, " ,SALE_NOTES=? ");
                }
                updateDataAnalysis.addCondition(reportId, " WHERE ROW_ID=? ");
                setSql(reMreportMapper, updateDataAnalysis);
            }
            if (StringUtil.isNonBlank(productionNote) || StringUtil.isNonBlank(biologicalSafetyNotes) || StringUtil.isNonBlank(
                    populationPlanningNotes) || StringUtil.isNonBlank(saleNotes)) {
                SqlCon updateDataAnalysis = new SqlCon();
                updateDataAnalysis.addMainSql(" UPDATE re_m_report SET ");
                updateDataAnalysis.addCondition(getEmployeeId(), " NOTES_ID=? ");
                if (StringUtil.isNonBlank(productionNote)) {
                    updateDataAnalysis.addCondition(productionNote, " ,PRODUCTION_NOTES=? ");
                }
                if (StringUtil.isNonBlank(biologicalSafetyNotes)) {
                    updateDataAnalysis.addCondition(biologicalSafetyNotes, " ,BIOLOGICAL_SAFETY_NOTES=? ");
                }
                if (StringUtil.isNonBlank(populationPlanningNotes)) {
                    updateDataAnalysis.addCondition(populationPlanningNotes, " ,POPULATION_PLANNING_NOTES=? ");
                }
                if (StringUtil.isNonBlank(saleNotes)) {
                    updateDataAnalysis.addCondition(saleNotes, " ,SALE_NOTES=? ");
                }
                updateDataAnalysis.addCondition(reportId, " WHERE ROW_ID=? ");
                setSql(reMreportMapper, updateDataAnalysis);
            }
        }
    }

    @Override
    public BasePageInfo searchSendSAPHana() {
        setToPage();
        Map<String, String> map = new HashMap<>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT ROW_ID, SORT_NBR,IF(DELETED_FLAG = '0' ,'否','是') DELETED_FLAG, NOTES, COMPANY_ID, FARM_ID, TO_SAP_DATE, CREAT_TIME, DELETED_TIME, TO_SAP_AGIN ");
        sqlCon.addMainSql(" FROM SYS_HANA_INSERT_LOG ");
        sqlCon.addCondition(getFarmId(), " WHERE FARM_ID = ? ");
        map.put("sql", sqlCon.getCondition());
        List<SysHanaInsertLogModel> list = sysHanaInsertLogMapper.operSql(map);
        return getPageInfo(list);
    }

    // 验证是否可以进行事件操作(关账-上传sap) 入参:enterDate
    public void sapEventMasage(Long farmId, Date enterDate) throws Exception {
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        if (!isToHana) {
            return;
        }

        Integer settingValueByCodeSapMaxDelay = 0;
        try {
            settingValueByCodeSapMaxDelay = Integer.valueOf(paramMapper.getSettingValueByCode(getCompanyId(), farmId, "SAP_MAX_DELAY"));
        }
        catch (Exception e) {
            settingValueByCodeSapMaxDelay = 0;
        }

        Map<String, String> startAndEndDate = sendToSAPProductionService.getStartAndEndDate();
        String endDate = Maps.getString(startAndEndDate, "endDate");
        String minDate = Maps.getString(startAndEndDate, "startDate");
        String maxDate = TimeUtil.dateAddDay(endDate, settingValueByCodeSapMaxDelay);

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

    /**
     * 精液管理分页查询
     */
    @Override
    public BasePageInfo searchSpermToPage(Map<String, Object> map) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT * FROM ( ");
        sqlCon.addMainSql("SELECT Date(t1.SEMEN_DATE) as semenDate,DATE(T1.VALID_DATE) AS validDate,t1.ROW_ID AS rowId, ");
        sqlCon.addMainSql(" t1.SEMEN_BATCH_NO AS semenBatchNo,ROUND(t1.SEMEN_QTY/(t1.ANH_NUM*t2.SPEC),2) AS semenBatchNoSpec,");
        sqlCon.addMainSql(" t1.SEMEN_QTY AS semenQty,t1.ANH_NUM AS anhNum,T2.SPEC AS spec,T1.PRINT_NUM as printNum, ");
        sqlCon.addMainSql(" T1.EAR_BRAND AS earBrand,CONCAT(T5.BREED_NAME,'-',T1.DAY_AGE) AS breedAndDayAge, ");
        sqlCon.addMainSql(" SUM(IF(T2.STATUS = 2,1,0)) hasUsed, SUM(IF(T2.STATUS = 5,1,0)) expired, ");
        sqlCon.addMainSql(" SUM(IF(T2.STATUS = 4,1,0)) scrapped, SUM(IF(T2.STATUS = 1,1,0)) inventoryQuantity, ");
        sqlCon.addMainSql(" SUM(IF(T2.STATUS = 3,1,0)) hasSaled, T1.SUPPLIER_ID AS supplierId,T1.MANUFACTURER_ID AS manufacturerId ");
        sqlCon.addMainSql(" FROM pp_l_bill_semen T1 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_sperm_info T2 ON (T1.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN CD_O_BOAR T3 ON (T1.BOAR_MATERIAL_ID=T3.MATERIAL_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1')");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_ear_code T4 ON (T3.EAR_CODE_ID=T4.ROW_ID AND T4.DELETED_FLAG='0')");
        sqlCon.addMainSql(" LEFT JOIN cd_l_breed T5 ON (T1.BREED_ID=T5.ROW_ID AND T5.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN cd_m_material T6 ON (T3.MATERIAL_ID=T6.ROW_ID AND T6.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN cd_o_sperm T7 ON (T6.ROW_ID=T7.BOAR_ID AND T7.DELETED_FLAG='0')");
        sqlCon.addMainSql(" WHERE  T1.DELETED_FLAG='0'");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getString(map, "earBrand"), " AND T1.EAR_BRAND LIKE ?", true, false);
        sqlCon.addCondition(Maps.getString(map, "breedName"), " AND T5.ROW_ID = ?");
        sqlCon.addCondition(Maps.getString(map, "semenBatchNo"), " AND T1.SEMEN_BATCH_NO LIKE ?", true, false);
        sqlCon.addCondition(Maps.getString(map, "semenDate"), " AND T1.SEMEN_DATE = ?");
        sqlCon.addCondition(Maps.getString(map, "validDate"), " AND T1.VALID_DATE = ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");
        if ("1".equals(Maps.getString(map, "ascending"))) {
            sqlCon.addMainSql(" ORDER BY t1.SEMEN_DATE ASC ");
        } else {
            sqlCon.addMainSql(" ORDER BY t1.SEMEN_DATE DESC ");
        }
        // sqlCon.addMainSql(" ORDER BY t1.SEMEN_DATE DESC ");
        sqlCon.addMainSql(" )T0 ");
        if ("0".equals(Maps.getString(map, "requestType"))) {
            sqlCon.addMainSql(" WHERE T0.inventoryQuantity >0 ");
        }
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> firmsMap : list) {
            Long supplierId = Maps.getLong(firmsMap, "supplierId");
            Long manufacturerId = Maps.getLong(firmsMap, "manufacturerId");
            firmsMap.put("supplierIdName", CacheUtil.getName(String.valueOf(supplierId), NameEnum.COMPANY_NAME));
            firmsMap.put("manufacturer", CacheUtil.getName(String.valueOf(manufacturerId), NameEnum.COMPANY_NAME));

        }

        return getPageInfo(list);
    }

    /**
     * 精液管理详情查看
     */
    @Override
    public BasePageInfo searchSpermDetailToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT T2.ROW_ID AS rowId,T2.STATUS AS status,T2.COLOR AS color,T2.COHESION AS cohesion,T2.SPERM_MOTILITY AS spermMotility, ");
        sqlCon.addMainSql(
                " T2.SPERM_DENSITY AS spermDensity,T2.ABNORMATION_RATE AS abnormationRate, CONCAT(T1.SEMEN_BATCH_NO,'-',T2.BUSINESS_CODE+1) AS semenBatchNo,");
        sqlCon.addMainSql(" t1.ODOUR AS odour, t2.SOW_PIG_ID AS sowPigId,DATE(t2.USE_DATE) AS breedDateFirst,");
        sqlCon.addMainSql(" t3.BREED_NAME AS breedName,DATE(t2.SCRAP_DATE )AS scrapDate, ");
        sqlCon.addMainSql("  DATE(T1.VALID_DATE) AS validDate,t6.EAR_BRAND AS earBrand, ");
        sqlCon.addMainSql("  DATE(t2.USE_DATE) AS useDate,DATE(t2.SEMEN_DATE) AS semenDate,DATE(t4.SALE_DATE) AS saleDate ");
        sqlCon.addMainSql(" FROM pp_l_bill_semen t1 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_sperm_info t2 ON( T1.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0')");
        sqlCon.addMainSql(" LEFT JOIN cd_l_breed T3 ON (T1.BREED_ID=T3.ROW_ID AND T3.DELETED_FLAG='0' AND  T3.STATUS='1')");
        sqlCon.addMainSql(
                " LEFT JOIN pp_l_bill_semen_sale T4 on( T1.ROW_ID = T4.SEMEN_ID and T2.IS_SALE = 1 and T4.DELETED_FLAG = '0' AND T4.STATUS = '1')");
        sqlCon.addMainSql(" LEFT JOIN pp_l_pig T5 ON(t2.SOW_PIG_ID = T5.ROW_ID AND T5.DELETED_FLAG='0' AND T5.STATUS='1')");
        sqlCon.addMainSql("  LEFT JOIN pp_l_ear_code t6 ON(t5.EAR_CODE_ID=t6.ROW_ID AND t6.DELETED_FLAG='0' AND t6.STATUS='1')");
        sqlCon.addMainSql(" WHERE  T1.DELETED_FLAG='0'");
        sqlCon.addCondition(getFarmId(), " AND t1.FARM_ID=? ");
        sqlCon.addCondition(Maps.getLong(inputMap, "rowId"), " AND T1.ROW_ID= ?");
        sqlCon.addMainSql(" GROUP BY T2.ROW_ID");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.put("semenColorName", CacheUtil.getName(Maps.getString(map, "color"), NameEnum.CODE_NAME, CodeEnum.SEMEN_COLOR));
            map.put("semenOdourName", CacheUtil.getName(Maps.getString(map, "odour"), NameEnum.CODE_NAME, CodeEnum.SEMEN_ODOUR));
            map.put("cohesionName", CacheUtil.getName(Maps.getString(map, "cohesion"), NameEnum.CODE_NAME, CodeEnum.COHESION));
            String str = CacheUtil.getName(Maps.getString(map, "cohesion"), NameEnum.CODE_NAME, CodeEnum.COHESION);
            map.put("statusName", SpermConstants.SPERM_INFO_STATUS_MAP.get(Maps.getString(map, "status")));
            // 1正常,2已使用,3销售,4已报废,5过期失效
            if (SpermConstants.SPERM_INFO_STATUS_UNUSE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "semenDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "未有异常状态");
            } else if (SpermConstants.SPERM_INFO_STATUS_USE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "useDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "已被使用");
            } else if (SpermConstants.SPERM_INFO_STATUS_SALE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "saleDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "销售离场日期");
            } else if (SpermConstants.SPERM_INFO_STATUS_SCRAP.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "scrapDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "手动报废");
            } else if (SpermConstants.SPERM_INFO_STATUS_LOSE_EFFECTIVE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "validDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "正常因达到失效日期自动失效,不可手动操作导致失效");
            }
        }
        return getPageInfo(list);
    }

    /**
     * 精液管理批量反报废
     */
    @Override
    public void editBatchTrunOverScrapEdit(Map<String, Object> inputMap) throws Exception {
        List<HashMap> spermInfoArry = getJsonList(Maps.getString(inputMap, "spermInfoArry"), HashMap.class);
        List<SpermInfoModel> updateSpermInfoModelList = new ArrayList<SpermInfoModel>();
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> spermInfoMap : spermInfoArry) {
            sb.append(Maps.getString(spermInfoMap, "rowId") + ",");
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" UPDATE pp_l_sperm_info SET STATUS='1' ");
        sqlCon.addMainSql(" , SCRAP_CAUSE=NULL ,SCRAP_DATE=NULL,SCRAP_BY=NULL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '4' ");
        sqlCon.addCondition(sb.toString(), " AND SEMEN_ID IN ?", false, true);
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
    }

    /**
     * 精液管理批量报废
     */
    @Override
    public void editBatchScrapEdit(Map<String, Object> inputMap) throws Exception {
        List<HashMap> spermInfoArry = getJsonList(Maps.getString(inputMap, "spermInfoArry"), HashMap.class);
        List<SpermInfoModel> updateSpermInfoModelList = new ArrayList<SpermInfoModel>();
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> spermInfoMap : spermInfoArry) {
            sb.append(Maps.getString(spermInfoMap, "rowId") + ",");

        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" UPDATE pp_l_sperm_info SET STATUS = '4',SCRAP_DATE=NOW() ");
        sqlCon.addCondition(Maps.getString(inputMap, "scrapReason"), " ,SCRAP_CAUSE =? ");
        sqlCon.addCondition(getEmployeeId(), " ,SCRAP_BY =? ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addCondition(sb.toString(), " AND SEMEN_ID IN ? ", false, true);
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

    }

    // 精液批号详情
    @Override
    public List<Map<String, Object>> searchSemenBatchNoDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                "SELECT T1.ROW_ID as mianId,T2.ROW_ID AS rowId,T2.STATUS AS status,CONCAT(T1.SEMEN_BATCH_NO,'-',T2.BUSINESS_CODE+1) AS semenCode, ");
        sqlCon.addMainSql(" DATE(t2.SCRAP_DATE )AS scrapDate, DATE(T1.VALID_DATE) AS validDate, ");
        sqlCon.addMainSql(" DATE(t2.USE_DATE) AS useDate,DATE(t2.SEMEN_DATE) AS semenDate,DATE(t3.SALE_DATE) AS saleDate, ");
        sqlCon.addMainSql(" T2.SCRAP_CAUSE AS scrapCause, T2.SCRAP_BY AS scrapBy");
        sqlCon.addMainSql(" FROM pp_l_bill_semen t1 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_sperm_info t2 ON( T1.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0')");
        sqlCon.addMainSql(
                " LEFT JOIN pp_l_bill_semen_sale T3 on( T1.ROW_ID = T3.SEMEN_ID and T2.IS_SALE = 1 and T3.DELETED_FLAG = '0' AND T3.STATUS = '1')");
        sqlCon.addMainSql(" WHERE  T1.DELETED_FLAG='0'");
        sqlCon.addCondition(getFarmId(), " AND t1.FARM_ID=? ");
        sqlCon.addCondition(Maps.getLong(inputMap, "rowId"), " AND T1.ROW_ID= ?");
        sqlCon.addMainSql(" GROUP BY T2.ROW_ID");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.put("statusName", SpermConstants.SPERM_INFO_STATUS_MAP.get(Maps.getString(map, "status")));
            // 1正常,2已使用,3销售,4已报废,5过期失效
            if (SpermConstants.SPERM_INFO_STATUS_UNUSE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "semenDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "未有异常状态");
            } else if (SpermConstants.SPERM_INFO_STATUS_USE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "useDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "已被使用");
            } else if (SpermConstants.SPERM_INFO_STATUS_SALE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "saleDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "销售离场日期");
            } else if (SpermConstants.SPERM_INFO_STATUS_SCRAP.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "scrapDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "手动报废");
                String scrapByName = CacheUtil.getName(Maps.getString(map, "scrapBy"), NameEnum.EMPLOYEE_NAME);
                map.put("scrapReason", scrapByName + "：" + Maps.getString(map, "scrapCause"));
            } else if (SpermConstants.SPERM_INFO_STATUS_LOSE_EFFECTIVE.equals(Maps.getString(map, "status"))) {
                map.put("statusDate", TimeUtil.format(Maps.getString(map, "validDate"), TimeUtil.DATE_FORMAT));
                map.put("statusReason", "正常因达到失效日期自动失效,不可手动操作导致失效");
            }
        }

        return list;
    }

    // 报废
    @Override
    public void editScrapEdit(Map<String, Object> inputMap) throws Exception {
        List<HashMap> spermInfoArry = getJsonList(Maps.getString(inputMap, "spermInfoArry"), HashMap.class);
        Date currentDate = new Date();
        List<SpermInfoModel> updateSpermInfoModelList = new ArrayList<SpermInfoModel>();
        for (Map<String, Object> spermInfoMap : spermInfoArry) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addConditionWithNull(SpermConstants.SPERM_INFO_STATUS_UNUSE, " AND STATUS = ?");
            sqlCon.addCondition(Maps.getLong(spermInfoMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            List<SpermInfoModel> spermInfoMapperModelList = getList(spermInfoMapper, sqlCon);
            if (spermInfoMapperModelList.isEmpty()) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精液编号：【" + Maps.getString(spermInfoMap, "semenCode")
                        + "】的状态不是【未使用】状态，如果页面显示不正确，请刷新页面。");
            }
            SpermInfoModel spermInfoModel = spermInfoMapperModelList.get(0);
            spermInfoModel.setStatus(SpermConstants.SPERM_INFO_STATUS_SCRAP);
            spermInfoModel.setScrapDate(currentDate);
            spermInfoModel.setScrapCause(Maps.getString(inputMap, "scrapReason"));
            spermInfoModel.setScrapBy(getEmployeeId());
            updateSpermInfoModelList.add(spermInfoModel);
        }

        if (!updateSpermInfoModelList.isEmpty()) {
            spermInfoMapper.updates(updateSpermInfoModelList);
        }

    }

    // 反报废
    @Override
    public void editTrunOverScrapEdit(Map<String, Object> inputMap) throws Exception {
        List<HashMap> spermInfoArry = getJsonList(Maps.getString(inputMap, "spermInfoArry"), HashMap.class);
        for (Map<String, Object> spermInfoMap : spermInfoArry) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addConditionWithNull(SpermConstants.SPERM_INFO_STATUS_SCRAP, " AND STATUS = ?");
            sqlCon.addCondition(Maps.getLong(spermInfoMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            List<SpermInfoModel> spermInfoMapperModelList = getList(spermInfoMapper, sqlCon);
            if (spermInfoMapperModelList.isEmpty()) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "精液编号：【" + Maps.getString(spermInfoMap, "semenCode")
                        + "】的状态不是【报废】状态，如果页面显示不正确，请刷新页面。");
            }

            // 进行反报废
            SqlCon sqlCon2 = new SqlCon();
            sqlCon2.addMainSql(" UPDATE pp_l_sperm_info");
            sqlCon2.addCondition(SpermConstants.SPERM_INFO_STATUS_UNUSE, " SET STATUS = ?");
            sqlCon2.addMainSql(" , SCRAP_CAUSE = NULL ,SCRAP_DATE = NULL ,SCRAP_BY = NULL");
            sqlCon2.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '4'");
            sqlCon2.addCondition(Maps.getLong(spermInfoMap, "rowId"), " AND ROW_ID = ?");
            paramMapperSetSQL(paramMapper, sqlCon2);
        }
    }

    // 精液管理打印
    @Override
    public void editBatchPrint(Map<String, Object> inputMap) throws Exception {
        StringBuilder sb = new StringBuilder();
        String printList = Maps.getString(inputMap, "printList");
        List<Map<String, Object>> mapList = getMapList(printList);
        for (Map<String, Object> map2 : mapList) {
            String inventoryQuantity = Maps.getString(map2, "inventoryQuantity");
            long rowid = Maps.getLong(map2, "id");
            sb.append(rowid + ",");
            // if (inventoryQuantity.equals("0") || inventoryQuantity.equals("NULL")) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "所选记录必须有库存精液！");
            // }
        }
        // String ids = sb.substring(0, sb.length() - 1);
        SqlCon sqlCon2 = new SqlCon();
        Map<String, String> sqlMap2 = new HashMap<String, String>();
        sqlCon2.addMainSql(" UPDATE PP_L_BILL_SEMEN SET PRINT_NUM=(IFNULL(PRINT_NUM,0)+1) ");
        sqlCon2.addMainSql(" WHERE DELETED_FLAG = '0' ");
        sqlCon2.addCondition(sb.toString(), " AND ROW_ID IN ?", false, true);
        sqlMap2.put("sql", sqlCon2.getCondition());
        List<Map<String, Object>> list2 = paramMapper.getObjectInfos(sqlMap2);
    }

    // 精液销售选择精液
    @Override
    public BasePageInfo selectSpermSearchToPage(Map<String, Object> map) throws Exception {
        setToPage();
        // SUM(IF(T2.STATUS = 1,1,0)) inventoryQuantity
        SqlCon sqlCon = new SqlCon();
        String rowIds = (String) map.get("rowIds");
        sqlCon.addMainSql("SELECT * FROM (");
        sqlCon.addMainSql("SELECT Date(t1.SEMEN_DATE) as semenDate,t1.ROW_ID AS rowId, t1.SEMEN_BATCH_NO AS semenBatchNo, ");
        sqlCon.addMainSql(" SUM(IF(T2.STATUS = 1,1,0)) numberOfSales,t1.HOUSE_ID AS houseId,T1.PIG_ID AS pigId, ");
        sqlCon.addMainSql(" T1.EAR_BRAND AS earBrand,CONCAT(T5.BREED_NAME,'-',T1.DAY_AGE) AS breedAndDayAge, ");
        sqlCon.addMainSql(" SUM(IF(T2.STATUS = 1,1,0)) semenNum,T2.WAREHOUSE_ID AS warehouseId,Date(T1.VALID_DATE) AS validDate ");
        sqlCon.addMainSql(" FROM pp_l_bill_semen T1 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_sperm_info T2 ON (T1.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0' )");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_pig T3 ON (T1.PIG_ID=T3.ROW_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1')");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_ear_code T4 ON (T3.EAR_CODE_ID=T4.ROW_ID AND T4.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN CD_O_BOAR T3 ON (T1.BOAR_MATERIAL_ID=T3.MATERIAL_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1')");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_ear_code T4 ON (T3.EAR_CODE_ID=T4.ROW_ID AND T4.DELETED_FLAG='0')");
        sqlCon.addMainSql(" LEFT JOIN cd_l_breed T5 ON (T1.BREED_ID=T5.ROW_ID AND T5.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN cd_m_material T6 ON (T3.MATERIAL_ID=T6.ROW_ID AND T6.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN cd_o_sperm T7 ON (T6.ROW_ID=T7.BOAR_ID AND T7.DELETED_FLAG='0')");
        sqlCon.addMainSql(" WHERE  T1.DELETED_FLAG='0'");
        sqlCon.addCondition(rowIds, " AND T1.ROW_ID NOT IN ? ", false, true);
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getString(map, "earBrand"), " AND T1.EAR_BRAND LIKE ?", true, false);
        sqlCon.addCondition(Maps.getString(map, "breedName"), " AND T5.ROW_ID = ?");
        sqlCon.addCondition(Maps.getString(map, "semenBatchNo"), " AND T1.SEMEN_BATCH_NO LIKE ?", true, false);
        sqlCon.addCondition(Maps.getString(map, "semenDate"), " AND T1.SEMEN_DATE = ?");
        sqlCon.addCondition(Maps.getString(map, "validDate"), " AND T1.VALID_DATE = ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID ) T0 ");
        sqlCon.addMainSql(" WHERE T0.numberOfSales>0 ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> saleMap : list) {
            Map<String, String> saleInfoMap = CacheUtil.getData("SC_M_WAREHOUSE", Maps.getString(saleMap, "warehouseId"));
            saleMap.put("warehouseName", Maps.getString(saleInfoMap, "WAREHOUSE_NAME"));
        }
        return getPageInfo(list);

    }

    public void editXNPigMoveIn(ParaPigMoveInModel paraPigMoveInModel, String eventName, String pigMoveIn) throws Exception {
        // eventName = "PIG_MOVE_IN";
        // 单据日期不能为空
        if (paraPigMoveInModel.getEnterDate() == null) {
            Thrower.throwException(BaseBusiException.PP_BILL_ERROR);
        }

        // 业务编码
        String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());

        paraPigMoveInModel.setOriginFlag("S");
        paraPigMoveInModel.setOriginApp("XN1.0");
        paraPigMoveInModel.setCompanyId(getCompanyId());
        paraPigMoveInModel.setFarmId(getFarmId());
        paraPigMoveInModel.setCreateId(getEmployeeId());
        paraPigMoveInModel.setBusinessCode(businessCode);
        paraPigMoveInModel.setEventCode(eventName);

        // 创建表单
        paraPigMoveInModel.setBillDate(paraPigMoveInModel.getEnterDate());

        billMapper.insert(paraPigMoveInModel);
        long billId = paraPigMoveInModel.getRowId();
        List<PigMoveInView> pigMoveIns = null;

        long breedId = 0;
        String strain = "";
        Double birthWeight = 0.0;
        String bodyCondition = "";
        long leftTeatNum = 0;
        long rightTeatNum = 0;
        String sex = "";
        pigMoveIns = getJsonList(pigMoveIn, PigMoveInView.class);
        if (pigMoveIns.size() == 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "未添加明细不能提交-后台！");
        }

        for (Iterator<PigMoveInView> it = pigMoveIns.iterator(); it.hasNext();) {
            PigMoveInView pigMoveInModel = (PigMoveInView) it.next();
            pigMoveInModel.setPigType(PigConstants.PIG_TYPE_BOAR);
            if (pigMoveInModel.getMaterialId() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只物料不能为空！");
            }
            if (pigMoveInModel.getHouseId() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只猪舍不能为空！");
            }
            if (pigMoveInModel.getPigClass() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只状态不能为空！");
            }
            if (pigMoveInModel.getBirthDate() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "入场猪只出生日期不能为空！");
            }

            SqlCon msql = new SqlCon();
            msql.addMainSql("SELECT BREED_ID breedId,STRAIN strain,FARM_ID beforeFarmId FROM cd_o_boar WHERE DELETED_FLAG = 0");
            msql.addCondition(pigMoveInModel.getMaterialId(), " AND MATERIAL_ID = ?");
            Map<String, String> sqlMap = new HashMap<>();
            sqlMap.put("sql", msql.getCondition());
            Map<String, String> boarMap = paramMapper.getInfos(sqlMap).get(0);

            BoarModel boarModel = getBoarMaterial(boarMap);

            breedId = boarModel.getBreedId();
            strain = boarModel.getStrain();
            birthWeight = boarModel.getBirthWeight();
            bodyCondition = boarModel.getBodyCondition();
            leftTeatNum = boarModel.getLeftTeatNum();
            rightTeatNum = boarModel.getRightTeatNum();
            sex = PigConstants.PIG_SEX_MALE;
            pigMoveInModel.setMaterialId(boarModel.getMaterialId());

            // 入场方式需要记录 后台 对应 OPT_TYPE
            pigMoveInModel.setOptType(PigConstants.PIG_MOVE_IN_OPT_TYPE_VIR);
            if (pigMoveInModel.getEarBrand() != null) {
                pigMoveInModel.setEarBrand(pigMoveInModel.getEarBrand().toUpperCase());
            }
            if (pigMoveInModel.getEarShort() != null) {
                pigMoveInModel.setEarShort(pigMoveInModel.getEarShort().toUpperCase());
            }
            if (pigMoveInModel.getFatherEar() != null) {
                pigMoveInModel.setFatherEar(pigMoveInModel.getFatherEar().toUpperCase());
            }
            if (pigMoveInModel.getMotherEar() != null) {
                pigMoveInModel.setMotherEar(pigMoveInModel.getMotherEar().toUpperCase());
            }
            pigMoveInModel.setCompanyId(getCompanyId());
            pigMoveInModel.setFarmId(getFarmId());
            pigMoveInModel.setBillId(billId);
            pigMoveInModel.setBreedId(breedId);
            pigMoveInModel.setStrain(strain);
            pigMoveInModel.setBodyCondition(bodyCondition);
            pigMoveInModel.setBirthParity(0l);
            pigMoveInModel.setHouseNum(0l);
            if (pigMoveInModel.getBirthWeight() == null || pigMoveInModel.getBirthWeight().compareTo(0D) == 0) {
                pigMoveInModel.setBirthWeight(birthWeight);
            }
            if (pigMoveInModel.getLeftTeatNum() == null || pigMoveInModel.getLeftTeatNum().compareTo(0L) == 0) {
                pigMoveInModel.setLeftTeatNum(leftTeatNum);
            }
            if (pigMoveInModel.getRightTeatNum() == null || pigMoveInModel.getRightTeatNum().compareTo(0L) == 0) {
                pigMoveInModel.setRightTeatNum(rightTeatNum);
            }
            pigMoveInModel.setOrigin("3");
            pigMoveInModel.setEnterDate(paraPigMoveInModel.getBillDate());
            pigMoveInModel.setCreateId(getEmployeeId());
            pigMoveInModel.setSex(sex);
            pigMoveInModel.setWorker(getEmployeeId());
            pigMoveInModel.setEventName(eventName);

            pigEventMapper.pigMoveIn(pigMoveInModel);
            if (!"0".equals(pigMoveInModel.getErrorCode())) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, pigMoveInModel.getErrorMessage());
            }
        }
    }

    @Override
    public List<Map<String, Object>> selectUsableSpermToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T2.ROW_ID AS spermId");
        sqlCon.addMainSql(",CONCAT(T1.SEMEN_BATCH_NO,'-',(T2.BUSINESS_CODE+1)) AS spermBatchNo");
        sqlCon.addMainSql(" FROM PP_L_BILL_SEMEN T1");
        sqlCon.addMainSql(" INNER JOIN PP_L_SPERM_INFO T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T1.ROW_ID = T2.SEMEN_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T2.STATUS = '1'");
        sqlCon.addConditionWithNull(Maps.getLongClass(inputMap, "semenId"), " AND T1.ROW_ID = ?");

        List<Map<String, Object>> list = paramMapperSetSQL(paramMapper, sqlCon);
        return list;
    }

    @Override
    public BasePageInfo searchXNpigToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        if (Maps.isEmpty(inputMap, "earBrand") && Maps.isEmpty(inputMap, "farmId")) {
            return null;
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID pigId,PIG_TYPE pigType,EAR_BRAND earBrand,PIG_INFO pigInfo,BIRTH_DATE birthDate, SEX_NAME sexName,");
        sqlCon.addMainSql(" EAR_SHORT earShort,MATERIAL_ID materialId,ON_PRICE onPrice,FATHER_EAR_BRAND fatherEarBrand,");
        sqlCon.addMainSql(" MOTHER_EAR_BRAND motherEarBrand,PIG_CLASS pigClass,LEFT_TEAT_NUM leftTeatNum,ROW_ID relationPigId,");
        sqlCon.addMainSql(" RIGHT_TEAT_NUM rightTeatNum,parity parity,BIRTH_DATE birthDate,ENTER_WEIGHT enterWeight,SUPPLIER_ID supplierId,");
        sqlCon.addMainSql(" PIG_CLASS_NAME pigClassName,SUPPLIER_NAME supplierName,MATERIAL_NAME materialName,BIRTH_WEIGHT birthWeight");
        sqlCon.addMainSql(" FROM pp_v_pig T0");
        sqlCon.addMainSql(" WHERE PIG_CLASS = 2 AND ORIGIN <> 3 AND NOT EXISTS (");
        sqlCon.addMainSql(" SELECT 1 FROM pp_l_pig WHERE ORIGIN = 3 AND T0.UNIQUE_PIG_FLAG = UNIQUE_PIG_FLAG");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?)");
        sqlCon.addCondition(Maps.getString(inputMap, "earBrand"), " AND EAR_BRAND LIKE ?", true);
        sqlCon.addCondition(Maps.getLongClass(inputMap, "farmId"), " AND FARM_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "pigIds"), " AND ROW_ID NOT IN ?", false, true);

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        return getPageInfo(list);
    }

    @Override
    public List<Map<String, String>> searchXNFarm() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT c.ROW_ID rowId,c.SORT_NAME farmName FROM hr_m_company c");
        sqlCon.addMainSql(" WHERE c.DELETED_FLAG = 0 AND c.ROW_ID <> 469 AND EXISTS(SELECT * FROM pp_l_pig WHERE FARM_ID = c.ROW_ID");
        sqlCon.addMainSql(" AND DELETED_FLAG = 0 AND ORIGIN <> 3");
        sqlCon.addCondition(PigConstants.PIG_CLASS_SCGZ, " AND PIG_CLASS = ?)");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        return list;
    }

    @Override
    public void deleteProductionCollect(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            reMreportMapper.deletes(ids);
        }
    }

    public void sendToHanaProductSell(long billId, long farmId) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT s.BILL_ID billId,b.BUSINESS_CODE notes,b.BILL_DATE createDate,s.PAYMENT_DIFF paymentDiff,s.OTHER_FEE otherFee,");
        sqlCon.addMainSql(" s.CUSTOMER_ID customerId");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale s");
        sqlCon.addMainSql(" INNER JOIN pp_m_bill b ON s.BILL_ID = b.ROW_ID AND b.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE s.DELETED_FLAG = 0 ");
        sqlCon.addCondition(billId, " AND BILL_ID = ?");
        PigSaleModel model = setSql(pigSaleMapper, sqlCon).get(0);
        Map<String, Object> pigSaleMap = model.getData();

        String businessCode = model.getNotes();
        Date billDate = model.getCreateDate();// createDate被赋值为单据日期billDate
        String cardCode = HanaUtil.getMTC_SaleCardCode(model.getCustomerId());

        Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
        String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
        Date currentDate = new Date();

        // 主表
        HanaPigProduct hanaPigProduct = new HanaPigProduct();
        hanaPigProduct.setMTC_BranchID(mtcBranchID);
        hanaPigProduct.setMTC_BaseEntry(mtcBranchID + "-" + billId + "-" + businessCode);
        hanaPigProduct.setMTC_CardCode(cardCode);
        hanaPigProduct.setMTC_DocDate(billDate);
        hanaPigProduct.setMTC_AmtDiff(Maps.getString(pigSaleMap, "paymentDiff", "0.0"));
        hanaPigProduct.setMTC_Fee(Maps.getString(pigSaleMap, "otherFee", "0.0"));
        List<HanaPigProductDetail> hanaPigProductDetailList = new ArrayList<>();
        hanaPigProduct.setDetailList(hanaPigProductDetailList);

        // 细表
        SqlCon pigModelSqlCon = new SqlCon();
        pigModelSqlCon.addMainSql("SELECT T1.ROW_ID AS pigId, T1.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand");
        pigModelSqlCon.addMainSql(",T1.SEX AS sex, T1.BREED_ID AS breedId, T1.HOUSE_ID AS houseId");
        pigModelSqlCon.addMainSql(",T2.LEAVE_WEIGHT AS leaveWeight, T2.LEAVE_PRICE AS leavePrice");
        pigModelSqlCon.addMainSql(",T2.LINE_NUMBER AS lineNumber");
        pigModelSqlCon.addMainSql(" FROM PP_L_PIG T1");
        pigModelSqlCon.addMainSql(" INNER JOIN PP_L_BILL_LEAVE T2");
        pigModelSqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID)");
        pigModelSqlCon.addMainSql(" INNER JOIN pp_l_bill_pig_sale_detail d");
        pigModelSqlCon.addMainSql(" ON d.ROW_ID = t2.SALE_ID AND d.DELETED_FLAG = 0");
        pigModelSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND d.SALE_DESCRIBE IN (1,2)");
        pigModelSqlCon.addCondition(billId, " AND T2.BILL_ID = ?");

        Map<String, String> pigModelSqlMap = new HashMap<String, String>();
        pigModelSqlMap.put("sql", pigModelSqlCon.getCondition());

        List<Map<String, Object>> pigModelList = paramMapper.getObjectInfos(pigModelSqlMap);

        for (Map<String, Object> pigMap : pigModelList) {
            HanaPigProductDetail hanaPigProductDetail = new HanaPigProductDetail();
            hanaPigProductDetail.setMTC_BranchID(mtcBranchID);
            hanaPigProductDetail.setMTC_DeptID(mtcDeptID);
            hanaPigProductDetail.setMTC_BaseEntry(String.valueOf(billId));
            hanaPigProductDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
            hanaPigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PS);
            if (PigConstants.PIG_SEX_MALE.equals(Maps.getString(pigMap, "sex"))) {
                hanaPigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR);
            } else {
                hanaPigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW);
            }
            hanaPigProductDetail.setMTC_BatchNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
            hanaPigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
            hanaPigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
            hanaPigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
            hanaPigProductDetail.setMTC_Qty("1");
            hanaPigProductDetail.setMTC_Price(Maps.getString(pigMap, "leavePrice"));
            hanaPigProductDetail.setMTC_SalesAmt(Maps.getString(pigMap, "leavePrice"));
            hanaPigProductDetailList.add(hanaPigProductDetail);
        }
        // 明细
        setOtherFeeAndDiff3050(hanaPigProductDetailList, hanaPigProduct.getMTC_Fee(), hanaPigProduct.getMTC_AmtDiff());
        // 销售
        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(hanaPigProduct.getMTC_BranchID());
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
        // 业务代码:生产猪淘汰销售
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(hanaPigProduct.getMTC_BaseEntry());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);

        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
        // JSON文件
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigProduct);
        mtcITFC.setMTC_DataFile(jsonDataFile);
        // JSON文件长度
        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

        hanaCommonService.insertMTC_ITFC(mtcITFC);
    }

    @Override
    public void sendToHanaProductSellTotal() throws Exception {
        // billId,farmId
        // sendToHanaProductSell(174584, 137);
        // sendToHanaProductSell(190834, 11);
        // sendToHanaProductSell(195978, 8);
        sendToHanaProductSell(198102, 248);
    }

    public void cancelHanaPurchase2110(Long salebillId, Long moveBillId, Long saleFarmId, Long moveFarmId) throws Exception {

        // 客户ID
        Map<String, String> customerMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(moveFarmId);
        String customerMtcBranchID = Maps.getString(customerMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        Date currentDate = new Date();

        BillModel billModel = billMapper.searchById(moveBillId);

        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(customerMtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
        // 业务代码:商品猪采购(实时)
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2110);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + moveBillId + "-" + billModel.getBusinessCode() + "-" + salebillId);
        // 原始单据编号
        mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + moveBillId + "-" + billModel.getBusinessCode() + "-" + salebillId);
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

        hanaCommonService.insertMTC_ITFC(mtcITFC);

    }

    @Override
    public int editSendSAPHanaLimit() {
        SysHanaInsertLogModel sysHanaInsertLog = new SysHanaInsertLogModel();
        SqlCon sqlCon = new SqlCon();
        String settingValueByCode = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SAP_MAX_DELAY");
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

    /**
     * 采精事件仓库默认填写精液仓库
     */
    @Override
    public List<Map<String, Object>> searchSpermWarehouseName(Map<String, Object> map) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, WAREHOUSE_NAME AS warehouseName,WAREHOUSE_ADDRESS AS warehouseAddress FROM SC_M_WAREHOUSE ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG= '0'");
        sqlCon.addCondition(SupplyChianConstants.WAREHOUSE_STATUS_USING, " AND STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.WAREHOUSE_CATEGORY_SPERM, " AND WAREHOUSE_CATEGORY = ?");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ? ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        return list;

    }

    /**
     * 选择精液销售
     */
    @Override
    public List<Map<String, Object>> searchSpermToSaleToList(Map<String, Object> map) throws Exception {
        String mainIds = Maps.getString(map, "mainIds");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT * FROM (");
        sqlCon.addMainSql("SELECT Date(T1.SEMEN_DATE) as semenDate,t1.ROW_ID AS rowId, t1.SEMEN_BATCH_NO AS semenBatchNo, ");
        sqlCon.addMainSql(" SUM(IF(T2.STATUS = 1,1,0)) numberOfSales,t1.HOUSE_ID AS houseId,T1.PIG_ID AS pigId,T1.PIG_ID AS boarId, ");
        sqlCon.addMainSql(" T1.EAR_BRAND AS earBrand,CONCAT(T5.BREED_NAME,'-',T1.DAY_AGE) AS breedAndDayAge,SUM(IF(T2.STATUS = 1,1,0)) semenNum, ");
        sqlCon.addMainSql(" T2.WAREHOUSE_ID AS warehouseId,DATE(T1.VALID_DATE) AS validDate,t1.ROW_ID AS semenId ");
        sqlCon.addMainSql(" FROM pp_l_bill_semen T1 ");
        sqlCon.addMainSql(" LEFT JOIN pp_l_sperm_info T2 ON (T1.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0' )");
        // sqlCon.addMainSql(" LEFT JOIN CD_O_BOAR T3 ON (T1.BOAR_MATERIAL_ID=T3.MATERIAL_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1')");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_ear_code T4 ON (T3.EAR_CODE_ID=T4.ROW_ID AND T4.DELETED_FLAG='0')");
        sqlCon.addMainSql(" LEFT JOIN cd_l_breed T5 ON (T1.BREED_ID=T5.ROW_ID AND T5.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN cd_m_material T6 ON (T3.MATERIAL_ID=T6.ROW_ID AND T6.DELETED_FLAG='0')");
        // sqlCon.addMainSql(" LEFT JOIN cd_o_sperm T7 ON (T6.ROW_ID=T7.BOAR_ID AND T7.DELETED_FLAG='0')");
        sqlCon.addMainSql(" WHERE  T1.DELETED_FLAG='0'");
        sqlCon.addCondition(mainIds, " AND T1.ROW_ID IN ? ", false, true);
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID ) T0 ");
        sqlCon.addMainSql(" WHERE T0.numberOfSales>0 ");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> saleMap : list) {
            Map<String, String> saleInfoMap = CacheUtil.getData("SC_M_WAREHOUSE", Maps.getString(saleMap, "warehouseId"));
            saleMap.put("warehouseName", Maps.getString(saleInfoMap, "WAREHOUSE_NAME"));
        }
        List<Map<String, Object>> list2 = paramMapper.getObjectInfos(sqlMap);
        return list2;

    }

    @Override
    public long searchReportIdByFarmIdAndDate(Map<String, Object> map) throws Exception {
        long farmId = Maps.getLong(map, "farmId");
        String year = Maps.getString(map, "year");
        String month = Maps.getString(map, "month");
        String week = Maps.getString(map, "week");
        String quarter = Maps.getString(map, "quarter");
        long reportId = 0;
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT * FROM re_m_report WHERE DELETED_FLAG = '0' ");
        sqlCon.addCondition(farmId, " AND FARM_ID = ? ");
        if (year != null) {
            sqlCon.addCondition(year, " AND YEAR = ? ");
        }
        if (month != null) {
            sqlCon.addCondition(month, " AND MONTH = ? ");
        }
        if (week != null) {
            sqlCon.addCondition(week, " AND WEEK = ? ");
        }
        if (quarter != null) {
            sqlCon.addCondition(quarter, " AND QUARTER = ? ");
        }
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        if (list.size() > 0) {
            reportId = Maps.getLong(list.get(0), "ROW_ID");
        }
        return reportId;

    }

    @Override
    public List<Map<String, Object>> searchSemenListById(Map<String, Object> map) throws Exception {
        SqlCon semenSql = new SqlCon();
        semenSql.addMainSql(" SELECT T0.ROW_ID semenId,T0.STATUS status,CONCAT(T1.SEMEN_BATCH_NO,'-',T0.BUSINESS_CODE+1) semenBatchNo ");
        semenSql.addMainSql(" FROM pp_l_sperm_info T0 ");
        semenSql.addMainSql(
                " INNER JOIN pp_l_bill_semen T1 ON T0.SEMEN_ID = T1.ROW_ID AND T1.FARM_ID = t0.FARM_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
        semenSql.addMainSql(" WHERE T0.DELETED_FLAG = 0 ");
        semenSql.addCondition(getFarmId(), " AND T0.FARM_ID = ?");
        semenSql.addCondition(Maps.getLong(map, "semenId"), " AND T0.ROW_ID = ?");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", semenSql.getCondition());
        List<Map<String, Object>> semenList = paramMapper.getObjectInfos(sqlMap);
        return semenList;
    }

    // 种猪淘汰自动退回
    public void editObsoleteDefeatAuto(Long pigId, Date enterDate, Long billId) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql("SELECT ROW_ID rowId,(SELECT p.EAR_BRAND FROM pp_v_pig p WHERE p.ROW_ID = o.PIG_ID) earBrand ");
        sql.addMainSql(" FROM pp_l_bill_boar_obsolete o WHERE o.DELETED_FLAG = 0");
        sql.addCondition(getFarmId(), " AND o.FARM_ID = ?");
        sql.addCondition(PigConstants.SATAUS_OBSOLETE_APPLY, " AND o.STATUS = ?");
        sql.addCondition(pigId, " AND o.PIG_ID = ?");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sql.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        if (list.isEmpty()) {
            return;
        }
        if (list.size() > 1) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "耳号为：【" + Maps.getString(list.get(0), "earBrand")
                    + "】的猪只，存在多条淘汰申请记录，数据异常，请联系管理员！");
        }

        // 审批 自动退回
        Map<String, String> obsoleteMap = list.get(0);
        SqlCon sql1 = new SqlCon();
        sql1.addMainSql(" UPDATE PP_L_BILL_BOAR_OBSOLETE SET ");
        sql1.addCondition(PigConstants.SATAUS_OBSOLETE_FAIL, " STATUS = ? ");
        sql1.addCondition(TimeUtil.format(enterDate), " ,AUDIT_DATE = ?");
        sql1.addMainSql(" WHERE ");
        sql1.addCondition(Maps.getLong(obsoleteMap, "rowId"), " ROW_ID = ? ");
        setSql(boarObsoleteMapper, sql1);

        // 插入his表
        PigEventHisModel pigEventHisModel = new PigEventHisModel();
        Map<String, String> map = CacheUtil.getPigInfo(String.valueOf(pigId), PigInfoEnum.PIG_INFO_WITHID);
        pigEventHisModel.setStatus("1");
        pigEventHisModel.setDeletedFlag("0");
        pigEventHisModel.setOriginFlag("S");
        pigEventHisModel.setOriginApp("XN1.0");
        pigEventHisModel.setCompanyId(getCompanyId());
        pigEventHisModel.setFarmId(getFarmId());
        pigEventHisModel.setBillId(billId);
        pigEventHisModel.setCreateDate(new Date());
        pigEventHisModel.setCreateId(getEmployeeId());
        pigEventHisModel.setEarBrand(map.get("earBrand"));
        pigEventHisModel.setEnterDate(enterDate);
        pigEventHisModel.setEventName(EventConstants.BREED_PIG_OBSOLETE);
        String eventNotes = "耳牌号为:" + map.get("earBrand") + "审批未通过";
        pigEventHisModel.setEventNotes(eventNotes);
        pigEventHisModel.setHouseId(Maps.getLongClass(map, "houseId"));
        pigEventHisModel.setLastEventDate(enterDate);
        pigEventHisModel.setNotes("猪只已经发生其他事件，淘汰审批自动退回");
        /* 修改原因:退回不该走这些方法,时间:2016-10-8修改人:程彬,,审批退回时不执行改变猪只状态类方法*****************begin */
        pigEventHisModel.setPigClass(Maps.getLongClass(map, "pigClass"));
        /* 修改原因:退回不该走这些方法,时间:2016-10-8修改人:程彬,,审批退回时不执行改变猪只状态类方法*****************end */
        pigEventHisModel.setPigId(pigId);
        pigEventHisModel.setPigpenId(Maps.getLongClass(map, "pigpenId"));
        pigEventHisModel.setPigType(map.get("pigType"));
        pigEventHisModel.setSex(map.get("sex"));
        pigEventHisModel.setParity(Long.valueOf(map.get("parity")));
        pigEventHisModel.setSwineryId(Maps.getLongClass(map, "swineryId"));
        pigEventHisModel.setTableName("PP_L_BILL_BOAR_OBSOLETE");
        pigEventHisModel.setTableRowId(Maps.getLong(obsoleteMap, "rowId"));
        pigEventHisMapper.insert(pigEventHisModel);
    }

    @Override
    public void sendToHanaProductPurchaseTotal3020() throws Exception {
        // TODO Auto-generated method stub
        // sendToHanaProductPurchase3020(192090L, 137L);
        cancelProductPurchase3020(197340L, 137L);
        sendToHanaProductPurchase3020(197340L, 137L);
    }

    private void sendToHanaProductPurchase3020(Long billId, Long farmId) throws Exception {
        // 外购(无销售单)
        Map<String, String> branchAndDeptMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
        String branchId = Maps.getString(branchAndDeptMap, HanaUtil.MTC_BranchID);
        String deptId = Maps.getString(branchAndDeptMap, HanaUtil.MTC_DeptID);

        BillModel billModel = billMapper.searchById(billId);

        Date currentDate = new Date();

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT p.LINE_NUMBER lineNumber,p.HOUSE_ID houseId,p.BREED_ID breedId,p.SEX sex,p.PIG_CLASS pigClass,");
        sqlCon.addMainSql(" p.ENTER_WEIGHT enterWeight,p.ON_PRICE onPrice,p.SAP_FIXED_ASSETS_EARBRAND sapFixedAssetsEarbrand,");
        sqlCon.addMainSql(" p.SUPPLIER_ID supplierId,c.EAR_BRAND earBrand");
        sqlCon.addMainSql(" FROM pp_l_pig p");
        sqlCon.addMainSql(" INNER JOIN pp_l_ear_code c");
        sqlCon.addMainSql(" ON p.EAR_CODE_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE p.DELETED_FLAG = 0");
        sqlCon.addCondition(farmId, " AND p.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND p.LAST_BILL_ID = ?");
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> outPigList = paramMapper.getInfos(sqlMap);

        // 生产猪采购
        HanaPigProduct hanaPurchasePigProduct = new HanaPigProduct();
        // 分公司编码
        hanaPurchasePigProduct.setMTC_BranchID(branchId);
        // 新农+单据编号(采购单编号+"-"+销售单的ROW_ID)
        hanaPurchasePigProduct.setMTC_BaseEntry(branchId + "-" + String.valueOf(billId) + "-" + billModel.getBusinessCode());
        // 业务伙伴编号
        if (Maps.getLongClass(outPigList.get(0), "supplierId") != null) {
            hanaPurchasePigProduct.setMTC_CardCode(HanaUtil.getMTC_SaleCardCode(Maps.getLong(outPigList.get(0), "supplierId")));
        }
        // 出库日期
        hanaPurchasePigProduct.setMTC_DocDate(billModel.getBillDate());
        // 表行
        List<HanaPigProductDetail> hanaPurchasePigProductDetailList = new ArrayList<HanaPigProductDetail>();
        hanaPurchasePigProduct.setDetailList(hanaPurchasePigProductDetailList);

        for (Map<String, String> pigMap : outPigList) {
            // 生产猪
            // 生产猪采购
            if (isProductPig(Maps.getLong(pigMap, "pigClass"))) {
                HanaPigProductDetail hanaPurchasePigProductDetail = new HanaPigProductDetail();
                hanaPurchasePigProductDetail.setMTC_BranchID(branchId);
                hanaPurchasePigProductDetail.setMTC_DeptID(deptId);
                hanaPurchasePigProductDetail.setMTC_BaseEntry(String.valueOf(billId));
                hanaPurchasePigProductDetail.setMTC_BaseLine(Maps.getString(pigMap, "lineNumber"));
                hanaPurchasePigProductDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_PP);
                if (PigConstants.PIG_SEX_MALE.equals(Maps.getString(pigMap, "sex"))) {
                    hanaPurchasePigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_BOAR);
                } else {
                    hanaPurchasePigProductDetail.setMTC_ItemCode(HanaConstants.MTC_ITEM_CODE_PRODUCT_SOW);
                }
                hanaPurchasePigProductDetail.setMTC_BatchNum(Maps.getString(pigMap, "sapFixedAssetsEarbrand"));
                hanaPurchasePigProductDetail.setMTC_Unit(HanaUtil.getMTC_Unit(Maps.getLong(pigMap, "houseId")));
                hanaPurchasePigProductDetail.setMTC_Breed(HanaUtil.getMTC_Breed(Maps.getLong(pigMap, "breedId")));
                hanaPurchasePigProductDetail.setMTC_Sex(HanaUtil.getMTC_Sex(Maps.getString(pigMap, "sex")));
                hanaPurchasePigProductDetail.setMTC_Qty("1");
                hanaPurchasePigProductDetail.setMTC_Price(Maps.getString(pigMap, "onPrice"));
                hanaPurchasePigProductDetail.setMTC_Total(Maps.getString(pigMap, "onPrice"));
                hanaPurchasePigProductDetailList.add(hanaPurchasePigProductDetail);
            }
        }
        // 采购
        MTC_ITFC mtcPurhcaseITFC = new MTC_ITFC();
        // 分公司编号
        mtcPurhcaseITFC.setMTC_Branch(hanaPurchasePigProduct.getMTC_BranchID());
        // 业务日期
        mtcPurhcaseITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchasePigProduct.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
        // 业务代码:生产猪采购 - A
        mtcPurhcaseITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3020);
        // 新农+单据编号
        mtcPurhcaseITFC.setMTC_DocNum(hanaPurchasePigProduct.getMTC_BaseEntry());
        // 优先级
        mtcPurhcaseITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcPurhcaseITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
        // 创建日期
        mtcPurhcaseITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcPurhcaseITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcPurhcaseITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
        // JSON文件
        String jsonDataFilePurchase = HanaJacksonUtil.objectToJackson(hanaPurchasePigProduct);
        mtcPurhcaseITFC.setMTC_DataFile(jsonDataFilePurchase);
        // JSON文件长度
        mtcPurhcaseITFC.setMTC_DataFileLen(jsonDataFilePurchase.length());

        hanaCommonService.insertMTC_ITFC(mtcPurhcaseITFC);

    }

    private void cancelProductPurchase3020(Long billId, Long farmId) throws Exception {

        Date currentDate = new Date();
        // 客户ID
        Map<String, String> customerMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
        String customerMtcBranchID = Maps.getString(customerMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

        BillModel billModel = billMapper.searchById(billId);

        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(customerMtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
        // 业务代码:生产猪采购(实时)
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3020);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + billId + "-" + billModel.getBusinessCode());
        // 原始单据编号
        mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + billModel.getBusinessCode());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
        // 创建日期
        mtcITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
        // JSON文件
        HanaPigProduct hanaProductBill = new HanaPigProduct();
        hanaProductBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaProductBill);
        mtcITFC.setMTC_DataFile(jsonDataFile);
        hanaCommonService.insertMTC_ITFC(mtcITFC);
    }

    private void cancelSale2100(Long billId, Long farmId) throws Exception {

        Date currentDate = new Date();
        // 客户ID
        Map<String, String> customerMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
        String customerMtcBranchID = Maps.getString(customerMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

        BillModel billModel = billMapper.searchById(billId);

        MTC_ITFC mtcITFC = new MTC_ITFC();
        // 分公司编号
        mtcITFC.setMTC_Branch(customerMtcBranchID);
        // 业务日期
        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
        // 业务代码:猪只销售(实时)
        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2100);
        // 新农+单据编号
        mtcITFC.setMTC_DocNum(customerMtcBranchID + "-" + billId + "-" + billModel.getBusinessCode());
        // 原始单据编号
        mtcITFC.setMTC_BaseEntry(customerMtcBranchID + "-" + billId + "-" + billModel.getBusinessCode());
        // 优先级
        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
        // 处理区分
        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
        // 创建日期
        mtcITFC.setMTC_CreateTime(currentDate);
        // 同步状态
        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
        // DB
        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
        // JSON文件
        HanaSaleBill hanaSaleBill = new HanaSaleBill();
        hanaSaleBill.setMTC_BaseEntry(mtcITFC.getMTC_BaseEntry());
        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaSaleBill);
        mtcITFC.setMTC_DataFile(jsonDataFile);
        hanaCommonService.insertMTC_ITFC(mtcITFC);
    }
}
