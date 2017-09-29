package xn.pigfarm.service.production.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import xn.core.util.bigDeciml.BigDecimalUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaPigProduct;
import xn.hana.model.common.HanaPigProductDetail;
import xn.hana.model.common.HanaSaleBill;
import xn.hana.model.common.HanaSaleBillDetail;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.production.BillMapper;
import xn.pigfarm.mapper.production.LeaveMapper;
import xn.pigfarm.mapper.production.PigSaleDetailMapper;
import xn.pigfarm.mapper.production.PigSaleMapper;
import xn.pigfarm.mapper.production.SaleAccountDetailMapper;
import xn.pigfarm.mapper.production.SaleAccountMapper;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.LeaveModel;
import xn.pigfarm.model.production.PigSaleDetailModel;
import xn.pigfarm.model.production.PigSaleModel;
import xn.pigfarm.model.production.SaleAccountDetailModel;
import xn.pigfarm.model.production.SaleAccountModel;
import xn.pigfarm.service.production.IAccountService;
import xn.pigfarm.util.constants.AccountConstants;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * @Description: 销售结算单
 * @author Administrator
 * @date 2017年3月6日 下午4:01:35
 */
@Service("accountService")
public class AccountServiceImpl extends BaseServiceImpl implements IAccountService {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private SaleAccountMapper saleAccountMapper;


    @Autowired
    private SaleAccountDetailMapper saleAccountDetailMapper;

    @Autowired
    private PigSaleMapper pigSaleMapper;

    @Autowired
    private PigSaleDetailMapper pigSaleDetailMapper;

    @Autowired
    private LeaveMapper leaveMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private BillMapper billMapper;

    // 查询销售结算单
    @Override
    public BasePageInfo searchSaleAccountToPage() throws Exception {

        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT s.ROW_ID rowId,s.CUSTOMER_ID customerId,s.SALE_ACCOUNT_DATE saleAccountDate,");
        sqlCon.addMainSql(" m.ROW_ID saleBillId,m.BUSINESS_CODE billCode,s.BUSINESS_CODE businessCode,");
        sqlCon.addMainSql(" s.TOTAL_ACCOUNT_PRICE totalAccountPrice,s.TOTAL_ITEM_PRICE totalItemPrice,");
        sqlCon.addMainSql(" s.PIG_NUM pigNum,s.PIG_WEIGHT pigWeight,");
        sqlCon.addMainSql(" s.NOMINAL_PRICE nominalPrice,s.CARCASS_TOTAL_WEIGHT carcassTotalWeight,s.FAT_RATE fatRate,");
        sqlCon.addMainSql(" s.TOTAL_ACCOUNT totalAccount,s.BILL_CODE accountBillCode,m.BILL_DATE saleDate,");
        sqlCon.addMainSql(" (SELECT SUM(TOTAL_PRICE) FROM pp_l_bill_pig_sale_detail WHERE BILL_ID = s.SALE_BILL_ID) saleBillTotalPrice,");
        sqlCon.addMainSql(" (SELECT SUM(SALE_NUM) FROM pp_l_bill_pig_sale_detail WHERE BILL_ID = s.SALE_BILL_ID) totalNum,");
        sqlCon.addMainSql(" (SELECT SUM(TOTAL_WEIGHT) FROM pp_l_bill_pig_sale_detail WHERE BILL_ID = s.SALE_BILL_ID) totalWeight,");
        sqlCon.addMainSql(" c.COMPANY_NAME farmName");
        sqlCon.addMainSql(" FROM pp_l_bill_sale_account s");
        sqlCon.addMainSql(" INNER JOIN pp_m_bill m");
        sqlCon.addMainSql(" ON s.SALE_BILL_ID = m.ROW_ID AND m.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON s.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE s.DELETED_FLAG = 0");
        sqlCon.addConditionWithNull(getFarmId(), " AND s.FARM_ID = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

        for (Map<String, String> map : list) {
            map.put("customerName", CacheUtil.getName(Maps.getString(map, "customerId"), NameEnum.COMPANY_NAME));
        }
        return getPageInfo(list);
    }

    @Override
    public List<Map<String, String>> searchSaleAccountDetailToList(Long mainId, String itemType) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ITEM_ID itemId,PIG_NUM pigNum,ITEM_VALUE itemValue,ITEM_NAME itemName,ITEM_TYPE itemType,ITEM_STAGE itemStage");
        sqlCon.addMainSql(" FROM pp_l_bill_sale_account_detail d");
        sqlCon.addMainSql(" INNER JOIN cd_o_sale_account_item i");
        sqlCon.addMainSql(" ON d.ITEM_ID = i.ROW_ID AND i.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE d.DELETED_FLAG = 0");
        sqlCon.addCondition(itemType, " AND ITEM_TYPE IN ?", false, true);
        sqlCon.addCondition(mainId, " AND SALE_ACCOUNT_ID = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

        for (Map<String, String> map : list) {
            map.put("itemTypeName", CacheUtil.getName(Maps.getString(map, "itemType"), NameEnum.CODE_NAME, CodeEnum.ITEM_TYPE));
            map.put("itemStageName", CacheUtil.getName(Maps.getString(map, "itemStage"), NameEnum.CODE_NAME, CodeEnum.ITEM_STAGE));
        }
        return list;
    }

    @Override
    public List<Map<String, String>> searchCustomerSaleAccountToList() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT c.row_id rowId,c.deleted_flag deletedFlag,c.company_name companyName,c.notes notes,");
        sqlCon.addMainSql(" c.company_code companyCode");
        sqlCon.addMainSql(" FROM hr_m_company c");
        sqlCon.addMainSql(" INNER JOIN hr_r_srm s");
        sqlCon.addMainSql(" ON c.ROW_ID = s.CUSSUP_ID AND s.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE c.DELETED_FLAG = 0");
        sqlCon.addCondition(getFarmId(), " AND s.FARM_ID = ?");
        sqlCon.addCondition(CompanyConstants.SALE_ACCOUNT_SURE, " AND s.IS_SALE_ACCOUNT = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        return list;
    }

    @Override
    public List<Map<String, String>> selectSaleBillByCustomer(Map<String, Object> inputMap) throws Exception {

        Long customerId = Maps.getLongClass(inputMap, "customerId");
        Long billId = Maps.getLongClass(inputMap, "billId");
        String saleStatus = Maps.getString(inputMap, "saleStatus");
        String dateType = Maps.getString(inputMap, "dateType");

        // 新增的时候需要日期条件，查看的时候不需要条件
        String beginDate = null;
        String endDate = null;
        if (!"all".equals(dateType)) {
            Date currentDate = new Date();
            Date date = TimeUtil.getLastMonthOfDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            beginDate = sdf.format(date);
            endDate = sdf.format(currentDate);
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT b.ROW_ID saleBillId,b.BILL_DATE saleDate,b.BUSINESS_CODE billCode,SUM(d.sale_num) totalNum,");
        sqlCon.addMainSql(" IFNULL(SUM(d.TOTAL_WEIGHT),0) totalWeight,IFNULL(SUM(d.TOTAL_PRICE),0) saleBillTotalPrice,");
        sqlCon.addMainSql(" s.SALE_STATUS saleStatus,c.COMPANY_NAME farmName");
        sqlCon.addMainSql(" FROM pp_l_bill_pig_sale s");
        sqlCon.addMainSql(" INNER JOIN pp_l_bill_pig_sale_detail d");
        sqlCon.addMainSql(" ON s.BILL_ID = d.BILL_ID AND d.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN pp_m_bill b");
        sqlCon.addMainSql(" ON s.BILL_ID = b.ROW_ID AND b.DELETED_FLAG = 0");
        sqlCon.addMainSql(" INNER JOIN hr_m_company c");
        sqlCon.addMainSql(" ON s.FARM_ID = c.ROW_ID AND c.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE s.DELETED_FLAG = 0");
        sqlCon.addCondition(customerId, " AND s.CUSTOMER_ID = ?");
        sqlCon.addCondition(getFarmId(), " AND s.FARM_ID = ?");
        sqlCon.addCondition(billId, " AND s.BILL_ID = ?");
        sqlCon.addCondition(saleStatus, " AND s.SALE_STATUS IN ?", false, true);
        sqlCon.addCondition(beginDate, " AND d.SALE_DATE BETWEEN ?");
        sqlCon.addCondition(endDate, " AND ?");

        sqlCon.addMainSql(" GROUP BY b.ROW_ID");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

        for (Map<String, String> map : list) {
            map.put("saleStatusName", CacheUtil.getName(Maps.getString(map, "saleStatus"), NameEnum.CODE_NAME, CodeEnum.SALE_STATUS));
        }
        return list;
    }

    @Override
    public List<Map<String, String>> searchCusSaleItem(Long customerId, String itemType) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT row_id itemId,item_type itemType,item_stage itemStage,item_name itemName,");
        sqlCon.addMainSql(" reward_punish_standard rewardPunishStandard");
        sqlCon.addMainSql(" FROM cd_o_sale_account_item");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = 0");
        sqlCon.addCondition(itemType, " AND ITEM_TYPE IN ?", false, true);
        sqlCon.addCondition(customerId, " AND CUSTOMER_ID = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        for (Map<String, String> map : list) {
            map.put("itemTypeName", CacheUtil.getName(Maps.getString(map, "itemType"), NameEnum.CODE_NAME, CodeEnum.ITEM_TYPE));
            map.put("itemStageName", CacheUtil.getName(Maps.getString(map, "itemStage"), NameEnum.CODE_NAME, CodeEnum.ITEM_STAGE));
        }
        return list;
    }

    @Override
    public void editSaleAccount(Map<String, Object> map, SaleAccountModel saleAccountModel, String detailListString) throws Exception {

        // 若果销售单据已经结算过，则需要删除
        deleteSaleAccount(saleAccountModel.getSaleBillId(), Maps.getString(map, "billCode"));

        // 1 表头
        String bcodeOnly = ParamUtil.getBCode(AccountConstants.SALE_ACCOUNT, getEmployeeId(), get10000CompanyId(), get10000FarmId());

        Date createDate = new Date();
        saleAccountModel.setFarmId(getFarmId());
        saleAccountModel.setCompanyId(getCompanyId());
        saleAccountModel.setCreateDate(createDate);
        saleAccountModel.setBillCode(bcodeOnly);
        saleAccountMapper.insert(saleAccountModel);
        // 2 明细
        // 屠宰 + 扣款
        List<SaleAccountDetailModel> list = getJsonList(detailListString, SaleAccountDetailModel.class);
        for (SaleAccountDetailModel model : list) {
            model.setSaleAccountId(saleAccountModel.getRowId());
            model.setFarmId(getFarmId());
            model.setCompanyId(getCompanyId());
            model.setDeletedFlag(CommonConstants.DELETED_FLAG);
            model.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            model.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            model.setStatus(CommonConstants.STATUS);
        }
        saleAccountDetailMapper.inserts(list);
        // 3 修改销售单

        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(saleAccountModel.getSaleBillId(), " AND BILL_ID = ?");
        List<PigSaleDetailModel> saleList = getList(pigSaleDetailMapper, sqlCon);

        // 结算总价
        Double accountTotalPrice = saleAccountModel.getTotalAccountPrice();

        // 毛猪总重
        Double pigWeight = saleAccountModel.getPigWeight();

        // 销售总重
        Double totalSaleWeight = Maps.getDouble(map, "totalWeight");
        if (totalSaleWeight == 0D) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "销售单据总重为0，不能做结算！");
        }

        String xssfgxzlFlag = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "XSSFGXZL");

        PigSaleDetailModel pigSaleDetailModel = null;
        Double percent = 0D;
        Double totalPrice = 0D;
        Double totalWeight = 0D;
        Double lastPrice = 0D;
        Double lastWeight = 0D;
        Double unit = 0D;

        // 销售结算需要更新销售单明细的重量
        if ("ON".equals(xssfgxzlFlag)) {

            unit = BigDecimalUtil.bigDecimalDivide(accountTotalPrice, pigWeight, 2);
            for (int i = 0; i < saleList.size(); i++) {
                pigSaleDetailModel = saleList.get(i);
                if (i < saleList.size() - 1) {
                    percent = BigDecimalUtil.bigDecimalDivide(pigSaleDetailModel.getTotalWeight(), totalSaleWeight, 2);
                    totalWeight = doubleRound(BigDecimalUtil.bigDecimalMultiply(pigWeight, percent));
                    lastWeight = BigDecimalUtil.bigDecimalAdd(lastWeight, totalWeight);
                    pigSaleDetailModel.setTotalWeight(totalWeight);
                } else {
                    totalWeight = BigDecimalUtil.bigDecimalSubtract(pigWeight, lastWeight);
                    pigSaleDetailModel.setTotalWeight(totalWeight);
                }
                SqlCon leaveSql = new SqlCon();
                leaveSql.addCondition(pigSaleDetailModel.getRowId(), " AND SALE_ID = ?");
                leaveSql.addCondition(getFarmId(), " AND FARM_ID = ?");
                List<LeaveModel> leaveList = getList(leaveMapper, leaveSql);
                Double leaveWeight = BigDecimalUtil.bigDecimalDivide(pigWeight, Double.valueOf(pigSaleDetailModel.getSaleNum()), 2);

                for (int y = 0; y < leaveList.size(); y++) {
                    LeaveModel leaveModel = leaveList.get(y);
                    int num = leaveList.size() - 1;
                    if (y != num) {
                        leaveModel.setLeaveWeight(leaveWeight);
                    } else {
                        Double price = BigDecimalUtil.bigDecimalMultiply(leaveWeight, Double.valueOf(num));
                        leaveModel.setLeaveWeight(BigDecimalUtil.bigDecimalSubtract(pigWeight, price));
                    }
                }
                // 跟新leave表离场总重
                leaveMapper.updates(leaveList);
            }
        } else {
            unit = BigDecimalUtil.bigDecimalDivide(accountTotalPrice, totalSaleWeight, 2);
        }

        // 销售结算更新销售单明细的总价
        for (int i = 0; i < saleList.size(); i++) {
            pigSaleDetailModel = saleList.get(i);
            if (i < saleList.size() - 1) {
                totalPrice = doubleRound(BigDecimalUtil.bigDecimalMultiply(pigSaleDetailModel.getTotalWeight(), unit));
                pigSaleDetailModel.setTotalUnitPrice(unit);
                pigSaleDetailModel.setTotalPrice(totalPrice);
                lastPrice = BigDecimalUtil.bigDecimalAdd(lastPrice, totalPrice);
            } else {
                // 实际的
                totalPrice = BigDecimalUtil.bigDecimalSubtract(accountTotalPrice, lastPrice);
                pigSaleDetailModel.setTotalUnitPrice(unit);
                pigSaleDetailModel.setTotalPrice(totalPrice);
            }
            SqlCon leaveSql = new SqlCon();
            leaveSql.addCondition(pigSaleDetailModel.getRowId(), " AND SALE_ID = ?");
            leaveSql.addCondition(getFarmId(), " AND FARM_ID = ?");
            List<LeaveModel> leaveList = getList(leaveMapper, leaveSql);
            Double leavePrice = BigDecimalUtil.bigDecimalDivide(totalPrice, Double.valueOf(pigSaleDetailModel.getSaleNum()), 2);

            for (int y = 0; y < leaveList.size(); y++) {
                LeaveModel leaveModel = leaveList.get(y);
                int num = leaveList.size() - 1;
                if (y != num) {
                    leaveModel.setLeavePrice(leavePrice);
                } else {
                    Double price = BigDecimalUtil.bigDecimalMultiply(leavePrice, Double.valueOf(num));
                    leaveModel.setLeavePrice(BigDecimalUtil.bigDecimalSubtract(totalPrice, price));
                }
            }
            // 跟新leave表离场价格
            leaveMapper.updates(leaveList);

        }

        SqlCon saleSql = new SqlCon();
        saleSql.addMainSql("UPDATE pp_l_bill_pig_sale SET");
        saleSql.addCondition(AccountConstants.SALE_STATUS_IS_ACCOUNT_TRUE, " SALE_STATUS = ?");
        saleSql.addMainSql(" WHERE DELETED_FLAG = 0");
        saleSql.addCondition(saleAccountModel.getSaleBillId(), " AND BILL_ID = ?");
        setSql(pigSaleMapper, saleSql);

        pigSaleDetailMapper.updates(saleList);

        // START_HANA

        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        if (!isToHana) {
            return;
        }

        Long goodNumber = 0L;
        Long scNumber = 0L;
        
        // bill表
        BillModel billModel = billMapper.searchById(saleAccountModel.getSaleBillId());
        // 销售主表
        SqlCon pigSaleSql = new SqlCon();
        pigSaleSql.addCondition(saleAccountModel.getSaleBillId(), " AND BILL_ID = ?");
        PigSaleModel pigSaleModel = getModel(pigSaleMapper, pigSaleSql);

        Map<String, Object> pigSaleMap = pigSaleModel.getData();

        // 销售细表
        SqlCon saleDetailSql = new SqlCon();
        saleDetailSql.addCondition(saleAccountModel.getSaleBillId(), " AND BILL_ID = ?");
        List<PigSaleDetailModel> saleDetailList = getList(pigSaleDetailMapper, saleDetailSql);
        List<PigSaleDetailModel> hanaList = new ArrayList<>();
        List<PigSaleDetailModel> productList = new ArrayList<>();
        List<MTC_ITFC> mtcList = new ArrayList<>();
        for (PigSaleDetailModel model : saleDetailList) {
            if (!PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(model.getSaleDescribe()) && !PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG.equals(
                    model.getSaleDescribe())) {
                hanaList.add(model);
                goodNumber = goodNumber + pigSaleDetailModel.getSaleNum();
            } else {
                productList.add(model);
                scNumber = scNumber + pigSaleDetailModel.getSaleNum();
            }
        }
        Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
        String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
        String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
        String cardCode = HanaUtil.getMTC_SaleCardCode(pigSaleModel.getCustomerId());

        // 商品猪销售
        // 主表
        HanaSaleBill hanaSaleBill = new HanaSaleBill();
        // 分公司编码
        hanaSaleBill.setMTC_BranchID(mtcBranchID);
        // 新农+单据编号
        hanaSaleBill.setMTC_BaseEntry(mtcBranchID + "-" + saleAccountModel.getSaleBillId() + "-" + Maps.getString(map, "billCode"));
        // 业务伙伴编号

        hanaSaleBill.setMTC_CardCode(cardCode);
        // 出库日期
        hanaSaleBill.setMTC_DocDate(billModel.getBillDate());
        // 表行
        List<HanaSaleBillDetail> hanaSaleBillDetailList = new ArrayList<HanaSaleBillDetail>();
        //
        hanaSaleBill.setDetailList(hanaSaleBillDetailList);

        // 生产猪淘汰销售
        HanaPigProduct hanaPigProduct = new HanaPigProduct();
        // 分公司编码
        hanaPigProduct.setMTC_BranchID(mtcBranchID);
        // 新农+单据编号(销售单编号+"-"+采购单的ROW_ID)
        hanaPigProduct.setMTC_BaseEntry(mtcBranchID + "-" + saleAccountModel.getSaleBillId() + "-" + Maps.getString(map, "billCode"));
        // 业务伙伴编号
        hanaPigProduct.setMTC_CardCode(cardCode);
        // 出库日期
        hanaPigProduct.setMTC_DocDate(billModel.getBillDate());
        // 表行
        List<HanaPigProductDetail> hanaPigProductDetailList = new ArrayList<HanaPigProductDetail>();
        hanaPigProduct.setDetailList(hanaPigProductDetailList);

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
        hanaPigProduct.setMTC_Fee(scFee.toString());

        // 如果有商品猪，把货款差异放入商品猪重
        if (!hanaSaleBillDetailList.isEmpty()) {
            // 商品猪
            hanaSaleBill.setMTC_AmtDiff(paymentDiff);
            // 生产猪
            hanaPigProduct.setMTC_AmtDiff("0");
        } else {
            // 只有生产猪
            hanaPigProduct.setMTC_AmtDiff(paymentDiff);
        }
        //
        //
        // 商品猪销售细表
        HanaSaleBillDetail hanaSaleBillDetail = null;
        for (PigSaleDetailModel model : hanaList) {
            Map<String, Object> pigSaleDetailModelMap = model.getData();
            hanaSaleBillDetail = new HanaSaleBillDetail();
            // 分公司编码
            hanaSaleBillDetail.setMTC_BranchID(mtcBranchID);
            // 猪场编码
            hanaSaleBillDetail.setMTC_DeptID(mtcDeptID);
            // 新农+单据编号
            hanaSaleBillDetail.setMTC_BaseEntry(String.valueOf(saleAccountModel.getSaleBillId()));
            // 新农+单据行号
            hanaSaleBillDetail.setMTC_BaseLine(String.valueOf(model.getLineNumber()));
            // 销售类型
            hanaSaleBillDetail.setMTC_SalesType(HanaConstants.MTC_SALES_TYPE_SO);
            // 品名
            hanaSaleBillDetail.setMTC_ItemCode(HanaUtil.getMTC_ItemCodeOfSale(model.getSaleDescribe(), getFarmId(), model
                    .getHouseId()));
            // 猪舍编号
            hanaSaleBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(model.getHouseId()));
            // 猪只等级
            hanaSaleBillDetail.setMTC_Grade(HanaUtil.getMTC_Grade(model.getSaleDescribe()));
            // 猪只品种
            hanaSaleBillDetail.setMTC_Breed(HanaUtil.getMTC_Breed(model.getBreedId()));
            // 猪只性别
            hanaSaleBillDetail.setMTC_Sex(HanaUtil.getMTC_Sex(String.valueOf(model.getSex())));
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
            hanaSaleBillDetail.setMTC_Amount(Maps.getString(pigSaleDetailModelMap, "totalPrice"));
            // 业务员
            hanaSaleBillDetail.setMTC_Sales(Maps.getString(pigSaleMap, "salesman") != null ? pigSaleModel.getSalesman() + "." + CacheUtil.getName(
                    String.valueOf(pigSaleModel.getSalesman()), NameEnum.EMPLOYEE_NAME) : null);

            hanaSaleBillDetailList.add(hanaSaleBillDetail);
        }

        if (!hanaSaleBillDetailList.isEmpty()) {
            // MTC_ITFC
            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(hanaSaleBill.getMTC_BranchID());
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
            mtcList.add(mtcITFC);
        }

        List<Map<String, Object>> allPigModelList = new ArrayList<>();
        // 生产猪淘汰销售细表
        for (PigSaleDetailModel model : productList) {
            SqlCon pigModelSqlCon = new SqlCon();
            pigModelSqlCon.addMainSql("SELECT T1.ROW_ID AS pigId, T1.SAP_FIXED_ASSETS_EARBRAND AS sapFixedAssetsEarbrand");
            pigModelSqlCon.addMainSql(",T1.SEX AS sex, T1.BREED_ID AS breedId, T1.HOUSE_ID AS houseId");
            pigModelSqlCon.addMainSql(",T2.LEAVE_WEIGHT AS leaveWeight, T2.LEAVE_PRICE AS leavePrice");
            pigModelSqlCon.addMainSql(",T2.LINE_NUMBER AS lineNumber");
            pigModelSqlCon.addMainSql(" FROM PP_L_PIG T1");
            pigModelSqlCon.addMainSql(" INNER JOIN");
            pigModelSqlCon.addMainSql(" PP_L_BILL_LEAVE T2");
            pigModelSqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID");
            pigModelSqlCon.addCondition(getFarmId(), " AND T2.FARM_ID = ?)");
            pigModelSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
            pigModelSqlCon.addCondition(model.getRowId(), " AND T2.SALE_ID = ?");

            Map<String, String> pigModelSqlMap = new HashMap<String, String>();
            pigModelSqlMap.put("sql", pigModelSqlCon.getCondition());

            List<Map<String, Object>> pigModelList = paramMapper.getObjectInfos(pigModelSqlMap);

            allPigModelList.addAll(pigModelList);
        }

        // 如果有生产猪
        if (!allPigModelList.isEmpty()) {
            for (Map<String, Object> pigMap : allPigModelList) {
                // 生产猪销售
                HanaPigProductDetail hanaPigProductDetail = new HanaPigProductDetail();
                hanaPigProductDetail.setMTC_BranchID(mtcBranchID);
                hanaPigProductDetail.setMTC_DeptID(mtcDeptID);
                hanaPigProductDetail.setMTC_BaseEntry(String.valueOf(saleAccountModel.getSaleBillId()));
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
            // 生产猪淘汰销售
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
            mtcList.add(mtcITFC);
        }

        hanaCommonService.insertsMTC_ITFC(mtcList);

        // endHana
    }

    private void deleteSaleAccount(Long saleBillId, String billCode) throws Exception {

        if (saleBillId == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择销售单据！");
        }
        // 删除主表
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID FROM pp_l_bill_sale_account");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = 0");
        sqlCon.addCondition(saleBillId, " AND SALE_BILL_ID = ?");
        List<SaleAccountModel> list = setSql(saleAccountMapper, sqlCon);
        if (list.isEmpty()) {
            return;
        }
        SaleAccountModel model = list.get(0);
        saleAccountMapper.delete(model.getRowId());

        // 删除细表
        SqlCon detailSql = new SqlCon();
        detailSql.addMainSql("SELECT ROW_ID FROM pp_l_bill_sale_account_detail");
        detailSql.addMainSql(" WHERE DELETED_FLAG = 0");
        detailSql.addCondition(model.getRowId(), " AND SALE_ACCOUNT_ID = ?");
        List<SaleAccountDetailModel> detailList = setSql(saleAccountDetailMapper, detailSql);

        if (detailList.isEmpty()) {
            return;
        }
        long[] ids = new long[detailList.size()];
        for (int i = 0; i < detailList.size(); i++) {
            SaleAccountDetailModel detailModel = detailList.get(i);
            ids[i] = detailModel.getRowId();
        }
        saleAccountDetailMapper.deletes(ids);

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());

        if (isToHana) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

            Date currentDate = new Date();

            List<MTC_ITFC> mtclist = new ArrayList<>();

            // 销售单是否有生产猪，商品猪
            SqlCon leaveSql = new SqlCon();
            leaveSql.addMainSql("SELECT SALE_DESCRIBE saleDescribe FROM pp_l_bill_pig_sale_detail WHERE DELETED_FLAG = 0");
            leaveSql.addCondition(saleBillId, " AND BILL_ID = ?");
            leaveSql.addMainSql(" GROUP BY SALE_DESCRIBE");
            Map<String, String> classMap = new HashMap<>();
            classMap.put("sql", leaveSql.getCondition());
            List<Map<String, String>> pigClassList = paramMapper.getInfos(classMap);

            boolean goodFlag = false;
            boolean scFlag = false;
            for (Map<String, String> map : pigClassList) {
                if (PigConstants.SELL_GOOD_PRODUCTION_BOARD_PIG.equals(Maps.getString(map, "saleDescribe"))
                        || PigConstants.SELL_GOOD_PRODUCTION_SOW_PIG.equals(Maps.getString(map, "saleDescribe"))) {
                    scFlag = true;
                } else {
                    goodFlag = true;
                }
                if (goodFlag && scFlag) {
                    break;
                }
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
                mtcITFC.setMTC_DocNum(mtcBranchID + "-" + saleBillId + "-" + billCode);
                // 原始单据编号
                mtcITFC.setMTC_BaseEntry(mtcBranchID + "-" + saleBillId + "-" + billCode);
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
                // 业务代码:猪只淘汰销售(实时)
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(mtcBranchID + "-" + saleBillId + "-" + billCode);
                // 原始单据编号
                mtcITFC.setMTC_BaseEntry(mtcBranchID + "-" + saleBillId + "-" + billCode);
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
            hanaCommonService.insertsMTC_ITFC(mtclist);
            // END HANA
        }

    }

    private Double doubleRound(Double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        String str = df.format(d);
        return Double.parseDouble(str);
    }

}
