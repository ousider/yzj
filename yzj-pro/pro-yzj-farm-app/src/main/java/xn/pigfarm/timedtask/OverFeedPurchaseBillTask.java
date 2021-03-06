package xn.pigfarm.timedtask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;

import xn.core.data.SqlCon;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.ParamUtil;
import xn.core.util.SpringContextUtil;
import xn.core.util.bigDeciml.BigDecimalUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaPurchaseBill;
import xn.hana.model.common.HanaPurchaseBillDetail;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.mapper.supplychian.PurchaseDetailMapper;
import xn.pigfarm.mapper.supplychian.PurchaseMapper;
import xn.pigfarm.model.supplychian.PurchaseDetailModel;
import xn.pigfarm.model.supplychian.PurchaseModel;
import xn.pigfarm.util.constants.SupplyChianConstants;

/**
 * @Description: 定时任务：每个月的第一天完结上个月的所有订单，并且生成上个月最后一周的未到货订单
 * @author THL
 * @date 2016年6月14日 上午9:39:54
 */
public class OverFeedPurchaseBillTask implements Job {

    private static Logger log = Logger.getLogger(OverFeedPurchaseBillTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行OverFeedPurchaseBillTask...........");
        Calendar calendar = Calendar.getInstance();

        Date currentDate = calendar.getTime();

        // 单据日期
        Date billDate = null;
        try {
            billDate = TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT);
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }

        String billDateString = TimeUtil.format(billDate, TimeUtil.DATE_FORMAT);

        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = TimeUtil.format(calendar.getTime(), TimeUtil.DATE_FORMAT);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        String endDate = TimeUtil.format(calendar.getTime(), TimeUtil.DATE_FORMAT);

        calendar.add(Calendar.DAY_OF_MONTH, -7);

        String createStartDate = TimeUtil.format(calendar.getTime(), TimeUtil.DATE_FORMAT);

        // 自动在下月生成未到货生成订单
        SqlCon purchaseBillSqlCon = new SqlCon();
        purchaseBillSqlCon.addMainSql("SELECT ROW_ID,NOTES,FARM_ID,COMPANY_ID,BILL_CODE,BILL_DATE,EVENT_CODE,SUPPLIER_ID,");
        purchaseBillSqlCon.addMainSql("PURCHASER_ID,GROUP_OR_SELF,OA_USERNAME");
        purchaseBillSqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE");
        purchaseBillSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        purchaseBillSqlCon.addMainSql(" AND GROUP_OR_SELF = '1'");
        purchaseBillSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_PURCHASE, " AND EVENT_CODE = ?");
        purchaseBillSqlCon.addMainSql(" AND FARM_ID = 3");
        purchaseBillSqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS, " AND ( STATUS = ?");
        purchaseBillSqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " OR STATUS = ? )");
        purchaseBillSqlCon.addMainSql(" AND ORIGINAL_PURCHASE_ID IS NULL");
        purchaseBillSqlCon.addCondition(createStartDate, " AND BILL_DATE >= ?");
        purchaseBillSqlCon.addCondition(endDate, " AND BILL_DATE <= ?");
        purchaseBillSqlCon.addMainSql(" AND EXISTS(");
        purchaseBillSqlCon.addMainSql(" SELECT 1 FROM (");
        purchaseBillSqlCon.addMainSql(" SELECT PURCHASE_ID, SUM(PURCHASE_QTY)-SUM(ARRIVE_QTY) AS PURCHASE_QTY");
        purchaseBillSqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL");
        purchaseBillSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        purchaseBillSqlCon.addMainSql(" AND STATUS <> '6'");
        purchaseBillSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_PURCHASE, " AND EVENT_CODE = ?");
        purchaseBillSqlCon.addMainSql(" AND FARM_ID = 3");
        purchaseBillSqlCon.addCondition(createStartDate, " AND BILL_DATE >= ?");
        purchaseBillSqlCon.addCondition(endDate, " AND BILL_DATE <= ?");
        purchaseBillSqlCon.addMainSql(" GROUP BY PURCHASE_ID, PURCHASE_LN, FREE_LN ) A");
        purchaseBillSqlCon.addMainSql(" WHERE A.PURCHASE_ID = SC_M_BILL_PURCHASE.ROW_ID AND A.PURCHASE_QTY > 0 LIMIT 1)");
        purchaseBillSqlCon.addMainSql(" ORDER BY BILL_DATE DESC, ROW_ID DESC");

        Map<String, String> purchaseBillSqlMap = new HashMap<String, String>();
        purchaseBillSqlMap.put("sql", purchaseBillSqlCon.getCondition());

        PurchaseMapper purchaseMapper = SpringContextUtil.getBean("purchaseMapper", PurchaseMapper.class);

        PurchaseDetailMapper purchaseDetailMapper = SpringContextUtil.getBean("purchaseDetailMapper", PurchaseDetailMapper.class);

        List<PurchaseModel> purchaseModelList = purchaseMapper.operSql(purchaseBillSqlMap);

        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);

        // START HANA
        Map<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMap = new HashMap<String, Map<String, HanaPurchaseBill>>();
        // END HANA

        for (PurchaseModel oldPurchaseModel : purchaseModelList) {
            // 购料
            SqlCon purchaseDetailSqlCon = new SqlCon();
            purchaseDetailSqlCon.addMainSql("SELECT * FROM (");
            purchaseDetailSqlCon.addMainSql("SELECT NOTES,FARM_ID,COMPANY_ID,PURCHASE_ID,PURCHASE_LN,FREE_LN,MATERIAL_ID,ACTUAL_PRICE");
            purchaseDetailSqlCon.addMainSql(",ACTUAL_FREE_PERCENT,(SUM(PURCHASE_QTY)-SUM(ARRIVE_QTY)) AS PURCHASE_QTY,IS_PACKAGE");
            purchaseDetailSqlCon.addMainSql(",GROUP_OR_SELF,SUPPLIER_ID,REQUIRE_FARM,REBATE_REASON");
            purchaseDetailSqlCon.addMainSql(",PURCHASER_ID,ARRIVE_DATE,BILL_CODE,BILL_DATE,EVENT_CODE");
            purchaseDetailSqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL");
            purchaseDetailSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS <> 6");
            purchaseDetailSqlCon.addCondition(oldPurchaseModel.getRowId(), " AND PURCHASE_ID = ?");
            purchaseDetailSqlCon.addMainSql(" GROUP BY PURCHASE_ID, PURCHASE_LN, FREE_LN");
            purchaseDetailSqlCon.addMainSql(" ) A");
            purchaseDetailSqlCon.addMainSql(" WHERE A.PURCHASE_QTY > 0");
            purchaseDetailSqlCon.addMainSql(" ORDER BY A.PURCHASE_ID, A.PURCHASE_LN, A.FREE_LN");

            Map<String, String> purchaseDetailSqlMap = new HashMap<String, String>();
            purchaseDetailSqlMap.put("sql", purchaseDetailSqlCon.getCondition());

            List<PurchaseDetailModel> purchaseDetailModelList = purchaseDetailMapper.operSql(purchaseDetailSqlMap);

            PurchaseModel purchaseModel = new PurchaseModel();
            purchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS);
            purchaseModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            if (Maps.isEmpty(oldPurchaseModel.getData(), "notes")) {
                purchaseModel.setNotes("【" + oldPurchaseModel.getBillCode() + "】单据号，上月订单完结未到货物料，自动生成的订单。");
            } else {
                purchaseModel.setNotes(oldPurchaseModel.getNotes() + "--【" + oldPurchaseModel.getBillCode() + "】单据号，上月订单完结未到货物料，自动生成的订单。");
            }
            purchaseModel.setFarmId(oldPurchaseModel.getFarmId());
            purchaseModel.setCompanyId(oldPurchaseModel.getCompanyId());
            purchaseModel.setPurchaserId(oldPurchaseModel.getPurchaserId());

            // 新的BillCode
            String billCode = null;
            try {
                billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_PURCHASE_STORE, purchaseModel.getPurchaserId(), purchaseModel.getCompanyId(),
                        purchaseModel.getFarmId());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            purchaseModel.setBillCode(billCode);
            purchaseModel.setBillDate(billDate);
            purchaseModel.setEventCode(oldPurchaseModel.getEventCode());
            purchaseModel.setBillType(oldPurchaseModel.getBillType());
            purchaseModel.setSupplierId(oldPurchaseModel.getSupplierId());
            purchaseModel.setArriveDate(billDate);
            purchaseModel.setGroupOrSelf(oldPurchaseModel.getGroupOrSelf());
            purchaseModel.setOriginalPurchaseId(oldPurchaseModel.getRowId());
            purchaseModel.setCreateId(2L);
            purchaseModel.setCreateDate(currentDate);
            purchaseModel.setOaUsername(oldPurchaseModel.getOaUsername());
            purchaseMapper.insert(purchaseModel);

            for (int i = 0; i < purchaseDetailModelList.size(); i++) {
                PurchaseDetailModel purchaseDetailModel = purchaseDetailModelList.get(i);
                purchaseDetailModel.setStatus(CommonConstants.STATUS);
                purchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                purchaseDetailModel.setPurchaseId(purchaseModel.getRowId());
                // 做到全平台共用，所以选用10000账套
                String materialOnly = null;
                try {
                    materialOnly = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_ONLY_NUMBER, purchaseModel.getPurchaserId(), 1L, 2L);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                purchaseDetailModel.setMaterialOnly(materialOnly);
                purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);

                // 查询单价表是否存在单价
                SqlCon actualPriceSqlCon = new SqlCon();
                actualPriceSqlCon.addMainSql("SELECT T1.PRICE AS actualPrice FROM SC_M_BILL_PRICE_LIST_DETAIL T1");
                actualPriceSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
                actualPriceSqlCon.addCondition(billDateString, " AND T1.START_DATE <= ?");
                actualPriceSqlCon.addCondition(billDateString, " AND T1.END_DATE >= ?");
                actualPriceSqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_M_MATERIAL A WHERE A.DELETED_FLAG = '0'");
                actualPriceSqlCon.addMainSql(" AND T1.MATERIAL_ID = A.RELATED_ID");
                actualPriceSqlCon.addCondition(purchaseDetailModel.getMaterialId(), " AND A.ROW_ID = ? LIMIT 1)");

                Map<String, String> actualPriceSqlMap = new HashMap<String, String>();
                actualPriceSqlMap.put("sql", actualPriceSqlCon.getCondition());

                List<Map<String, Object>> actualPriceList = paramMapper.getObjectInfos(actualPriceSqlMap);

                Double actualPrice = 0D;
                if (actualPriceList != null && !actualPriceList.isEmpty()) {
                    actualPrice = Maps.getDouble(actualPriceList.get(0), "actualPrice", 0D);
                }

                purchaseDetailModel.setActualPrice(actualPrice);
                purchaseDetailModel.setPassQty(purchaseDetailModel.getPurchaseQty());
                purchaseDetailModel.setArriveQty(0D);
                purchaseDetailModel.setRebatePrice(0D);
                purchaseDetailModel.setArriveDate(purchaseModel.getArriveDate());
                purchaseDetailModel.setBillCode(purchaseModel.getBillCode());
                purchaseDetailModel.setBillDate(purchaseModel.getBillDate());
                purchaseDetailModel.setEventCode(purchaseModel.getEventCode());
                purchaseDetailModel.setCreateId(2L);
                purchaseDetailModel.setCreateDate(currentDate);
                purchaseDetailMapper.insert(purchaseDetailModel);

                // START HANA
                boolean isToHana = false;
                try {
                    isToHana = HanaUtil.isToHanaCheck(purchaseDetailModel.getRequireFarm());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                if (isToHana) {
                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = null;
                    try {
                        mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(purchaseDetailModel.getRequireFarm());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                    String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                    String mtcCardCode = null;
                    try {
                        mtcCardCode = HanaUtil.getMTC_CardCode(purchaseModel.getSupplierId());

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    // DB NAME
                    String dbBeanName = null;
                    try {
                        dbBeanName = HanaUtil.getDbBeanName(purchaseDetailModel.getRequireFarm());
                    }
                    catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    // 单据
                    String baseEntry = mtcBranchID + "-" + String.valueOf(purchaseModel.getRowId()) + "-" + purchaseModel.getBillCode();

                    List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = null;

                    // 如果dbBeanName不存在
                    if (!dbBeanNameHanaPurchaseBillMap.containsKey(dbBeanName)) {
                        Map<String, HanaPurchaseBill> hanaPurchaseBillMap = new HashMap<String, HanaPurchaseBill>();

                        HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                        // 分公司编码
                        hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                        // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                        // 业务伙伴编号
                        hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                        // 采购日期
                        hanaPurchaseBill.setMTC_DocDate(purchaseModel.getBillDate());
                        // 到货日期
                        hanaPurchaseBill.setMTC_DocDueDate(purchaseModel.getArriveDate());
                        // 表行
                        hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                        hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                        hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                        dbBeanNameHanaPurchaseBillMap.put(dbBeanName, hanaPurchaseBillMap);
                    } else {
                        // 如果dbBeanName存在,获取hanaPurchaseBillMap
                        Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMap.get(dbBeanName);

                        // 如果baseEntry不存在
                        if (!hanaPurchaseBillMap.containsKey(baseEntry)) {
                            HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                            // 分公司编码
                            hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                            // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                            // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                            // 业务伙伴编号
                            hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                            // 采购日期
                            hanaPurchaseBill.setMTC_DocDate(purchaseModel.getBillDate());
                            // 到货日期
                            hanaPurchaseBill.setMTC_DocDueDate(purchaseModel.getArriveDate());
                            // 表行
                            hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                            hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                            hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                        } else {
                            // 如果baseEntry存在
                            hanaPurchaseBillDetailList = hanaPurchaseBillMap.get(baseEntry).getDetailList();
                        }
                    }

                    String mtcItemCode = null;
                    try {
                        mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(purchaseDetailModel.getMaterialId());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    HanaPurchaseBillDetail hanaPurchaseBillDetail = new HanaPurchaseBillDetail();
                    // 分公司编码
                    hanaPurchaseBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaPurchaseBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号
                    hanaPurchaseBillDetail.setMTC_BaseEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                    // 新农+单据行号
                    hanaPurchaseBillDetail.setMTC_BaseLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" + String.valueOf(
                            purchaseDetailModel.getFreeLn()));
                    // 采购类型
                    hanaPurchaseBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                    // 物料编号
                    hanaPurchaseBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 采购数量
                    hanaPurchaseBillDetail.setMTC_Qty(String.valueOf(purchaseDetailModel.getPurchaseQty()));
                    // 采购价格
                    hanaPurchaseBillDetail.setMTC_QtyPrice(String.valueOf(purchaseDetailModel.getActualPrice()));
                    // 采购金额
                    Double totalPrice = BigDecimalUtil.bigDecimalMultiply(purchaseDetailModel.getPurchaseQty(), purchaseDetailModel.getActualPrice());
                    hanaPurchaseBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                    hanaPurchaseBillDetailList.add(hanaPurchaseBillDetail);
                }
                // END HANA
            }
        }

        // START HANA
        // 完结的采购订单
        Map<String, Map<String, HanaPurchaseBill>> closeDbBeanNameHanaPurchaseBillMap = new HashMap<String, Map<String, HanaPurchaseBill>>();
        // END HANA

        // 自动完结上月订单
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID FROM SC_M_BILL_PURCHASE T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T2.GROUP_OR_SELF = '1' AND T1.ROW_ID = T2.PURCHASE_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_PURCHASE, " AND T1.EVENT_CODE = ?");
        sqlCon.addMainSql(" AND T1.FARM_ID = 3");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS, " AND ( T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " OR T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_COMPLETED, " OR T1.STATUS = ? )");
        // sqlCon.addCondition(startDate, " AND T1.BILL_DATE >= ?");
        sqlCon.addCondition(endDate, " AND T1.BILL_DATE <= ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<PurchaseModel> overPurchaseModelList = purchaseMapper.operSql(sqlMap);
        if (overPurchaseModelList != null && !overPurchaseModelList.isEmpty()) {
            for (PurchaseModel overPurchaseModel : overPurchaseModelList) {
                overPurchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_AUTO_OVER);

                // START HANA
                // SAP采购单完结
                SqlCon purchaseDetailSqlCon = new SqlCon();
                purchaseDetailSqlCon.addMainSql("SELECT T1.REQUIRE_FARM,T1.SUPPLIER_ID,T1.PURCHASE_ID,T1.BILL_CODE,T1.BILL_DATE");
                purchaseDetailSqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL T1");
                purchaseDetailSqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T2");
                purchaseDetailSqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0'");
                purchaseDetailSqlCon.addMainSql(" AND T1.MATERIAL_ID = T2.ROW_ID");
                purchaseDetailSqlCon.addMainSql(" AND EXISTS( SELECT 1 FROM CD_M_MATERIAL T3");
                purchaseDetailSqlCon.addMainSql(" WHERE T3.DELETED_FLAG = '0' AND T2.RELATED_ID = T3.ROW_ID ");
                purchaseDetailSqlCon.addMainSql(" AND T3.MATERIAL_CLASS_NUMBER IS NOT NULL LIMIT 1 ))");
                purchaseDetailSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS <> 6");
                purchaseDetailSqlCon.addCondition(overPurchaseModel.getRowId(), " AND T1.PURCHASE_ID = ?");
                purchaseDetailSqlCon.addMainSql(" GROUP BY T1.REQUIRE_FARM,T1.SUPPLIER_ID,T1.PURCHASE_ID,T1.BILL_CODE");

                Map<String, String> purchaseDetailSqlMap = new HashMap<String, String>();
                purchaseDetailSqlMap.put("sql", purchaseDetailSqlCon.getCondition());

                List<PurchaseDetailModel> purchaseDetailModelList = purchaseDetailMapper.operSql(purchaseDetailSqlMap);

                for (PurchaseDetailModel purchaseDetailModel : purchaseDetailModelList) {

                    boolean isToHana = false;
                    try {
                        isToHana = HanaUtil.isToHanaCheck(purchaseDetailModel.getRequireFarm());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (isToHana) {
                        Map<String, String> mtcBranchIDAndMTC_DeptIDMap = null;
                        try {
                            mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(purchaseDetailModel.getRequireFarm());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                        // String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                        // String mtcCardCode = null;
                        // try {
                        // mtcCardCode = HanaUtil.getMTC_CardCode(purchaseDetailModel.getSupplierId());
                        // }
                        // catch (Exception e) {
                        // e.printStackTrace();
                        // }

                        // DB NAME
                        String dbBeanName = null;
                        try {
                            dbBeanName = HanaUtil.getDbBeanName(purchaseDetailModel.getRequireFarm());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 单据
                        String baseEntry = mtcBranchID + "-" + String.valueOf(purchaseDetailModel.getPurchaseId()) + "-" + purchaseDetailModel
                                .getBillCode();

                        // 如果dbBeanName不存在
                        if (!closeDbBeanNameHanaPurchaseBillMap.containsKey(dbBeanName)) {
                            Map<String, HanaPurchaseBill> closeHanaPurchaseBillMap = new HashMap<String, HanaPurchaseBill>();

                            HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                            // 分公司编码
                            hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                            // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                            // 采购日期
                            hanaPurchaseBill.setMTC_DocDate(purchaseDetailModel.getBillDate());

                            closeHanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                            closeDbBeanNameHanaPurchaseBillMap.put(dbBeanName, closeHanaPurchaseBillMap);
                        } else {
                            // 如果dbBeanName存在,获取hanaPurchaseBillMap
                            Map<String, HanaPurchaseBill> closeHanaPurchaseBillMap = closeDbBeanNameHanaPurchaseBillMap.get(dbBeanName);

                            // 如果baseEntry不存在
                            if (!closeHanaPurchaseBillMap.containsKey(baseEntry)) {
                                HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                                // 分公司编码
                                hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                                // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                                hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                                // 采购日期
                                hanaPurchaseBill.setMTC_DocDate(purchaseDetailModel.getBillDate());

                                closeHanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                            }
                        }
                    }
                }
                // END HANA
            }
            purchaseMapper.updates(overPurchaseModelList);
        }

        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
        for (Entry<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMapEntry : dbBeanNameHanaPurchaseBillMap.entrySet()) {
            String dbBeanName = dbBeanNameHanaPurchaseBillMapEntry.getKey();
            Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMapEntry.getValue();
            for (Entry<String, HanaPurchaseBill> hanaPurchaseBillMapEntry : hanaPurchaseBillMap.entrySet()) {
                HanaPurchaseBill hanaPurchaseBill = hanaPurchaseBillMapEntry.getValue();
                if (!hanaPurchaseBill.getDetailList().isEmpty()) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(hanaPurchaseBill.getMTC_BranchID());
                    // 业务日期
                    try {
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 业务代码:采购订单
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(hanaPurchaseBill.getMTC_BaseEntry());
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(dbBeanName);
                    // JSON文件
                    String jsonDataFile = null;
                    try {
                        jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                    }
                    catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                    mtcITFCList.add(mtcITFC);
                }
            }
        }

        for (Entry<String, Map<String, HanaPurchaseBill>> closeDbBeanNameHanaPurchaseBillMapEntry : closeDbBeanNameHanaPurchaseBillMap.entrySet()) {
            String dbBeanName = closeDbBeanNameHanaPurchaseBillMapEntry.getKey();
            Map<String, HanaPurchaseBill> closeHanaPurchaseBillMap = closeDbBeanNameHanaPurchaseBillMapEntry.getValue();
            for (Entry<String, HanaPurchaseBill> closeHanaPurchaseBillMapEntry : closeHanaPurchaseBillMap.entrySet()) {
                HanaPurchaseBill hanaPurchaseBill = closeHanaPurchaseBillMapEntry.getValue();
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaPurchaseBill.getMTC_BranchID());
                // 业务日期
                try {
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                // 业务代码:采购订单
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaPurchaseBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_C);
                // 原始单据编号
                mtcITFC.setMTC_BaseEntry(hanaPurchaseBill.getMTC_BaseEntry());
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(dbBeanName);
                // JSON文件
                String jsonDataFile = null;
                try {
                    jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                mtcITFCList.add(mtcITFC);
            }
        }

        if (!mtcITFCList.isEmpty()) {
            IhanaCommonService hanaCommonService = SpringContextUtil.getBean("HANACommonService", IhanaCommonService.class);
            try {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        log.info("结束执行OverFeedPurchaseBillTask...........");
    }

}
