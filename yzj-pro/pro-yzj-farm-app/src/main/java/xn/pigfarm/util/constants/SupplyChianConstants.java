package xn.pigfarm.util.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 供应链常量
 * @author THL
 * @date 2016年09月07日 下午4:53:01
 */
public class SupplyChianConstants {
    private SupplyChianConstants() {
        
    }

    // 1.供应链单据事件类型：物料需求
    public static final String EVENT_CODE_MATERIAL_REQUIRE = "MATERIAL_REQUIRE";

    // 2.供应链单据事件类型：猪只需求
    public static final String EVENT_CODE_PIG_REQUIRE = "PIG_REQUIRE";

    // 3.供应链单据事件类型：物料采购
    public static final String EVENT_CODE_MATERIAL_PURCHASE = "MATERIAL_PURCHASE";

    // 4.供应链单据事件类型：饲料采购
    public static final String EVENT_CODE_FEED_PURCHASE = "FEED_PURCHASE";

    // 5.供应链单据事件类型：猪只采购
    public static final String EVENT_CODE_PIG_PURCHASE = "PIG_PURCHASE";

    // 6.供应链单据事件类型：物料领用出库
    public static final String EVENT_CODE_OUTPUT_USE = "OUTPUT_USE";

    // 7.供应链单据事件类型：物料调拨出库
    public static final String EVENT_CODE_OUTPUT_ALLOT = "OUTPUT_ALLOT";

    // 8.供应链单据事件类型：物料报废出库
    public static final String EVENT_CODE_OUTPUT_SCRAP = "OUTPUT_SCRAP";

    // 9.供应链单据事件类型：物料采购退货
    public static final String EVENT_CODE_OUTPUT_PURCHASE = "OUTPUT_PURCHASE";

    // 10.供应链单据事件类型：物料到货入库(已经停用)
    public static final String EVENT_CODE_INPUT_ARRIVE = "INPUT_ARRIVE";

    // 11.供应链单据事件类型：物料退货入库
    public static final String EVENT_CODE_INPUT_OUTPUT = "INPUT_OUTPUT";

    // 12.供应链单据事件类型：物料反报废入库
    public static final String EVENT_CODE_INPUT_RESCRAP = "INPUT_RESCRAP";

    // 13.供应链单据事件类型：物料调拨入库
    public static final String EVENT_CODE_INPUT_ALLOT = "INPUT_ALLOT";

    // 14.供应链单据事件类型：物料非订单入库
    public static final String EVENT_CODE_INPUT_NOT_BILL = "INPUT_NOT_BILL";

    // 15.供应链单据事件类型：物料初始化入库
    public static final String EVENT_CODE_WAREHOUSE_INITIALIZE = "WAREHOUSE_INITIALIZE";

    // 16.供应链单据事件类型：发票填写
    public static final String EVENT_CODE_RECEIPT_FILL_IN = "RECEIPT_FILL_IN";

    // 17.供应链单据事件类型：物料盘点
    public static final String EVENT_CODE_MONTH_ACCOUNT = "MONTH_ACCOUNT";

    // 18.供应链单据事件类型：物料到货入库
    public static final String EVENT_CODE_MATERIAL_INPUT_ARRIVE = "MATERIAL_INPUT_ARRIVE";

    // 19.供应链单据事件类型：饲料到货入库
    public static final String EVENT_CODE_FEED_INPUT_ARRIVE = "FEED_INPUT_ARRIVE";

    // 20.供应链单据事件类型：内销出库
    public static final String EVENT_CODE_OUTPUT_SALE = "OUTPUT_SALE";

    // 21.供应链单据事件类型：内销入库
    public static final String EVENT_CODE_INPUT_SALE = "INPUT_SALE";

    // 1.BCODE:需求单据编码
    public static final String BCODE_REQUIRE_STORE = "REQUIRE_STORE";

    // 2.BCODE:采购单据编码
    public static final String BCODE_PURCHASE_STORE = "PURCHASE_STORE";

    // 3.BCODE:入库单据编码
    public static final String BCODE_INPUT_STORE = "INPUT_STORE";

    // 4.BCODE:出库单据编码
    public static final String BCODE_OUTPUT_STORE = "OUTPUT_STORE";

    // 5.BCODE:物料批次号
    public static final String BCODE_MATERIAL_BATCH_NUMBER = "MATERIAL_BATCH_NUMBER";

    // 6.BCODE:物料生成号
    public static final String BCODE_MATERIAL_ONLY_NUMBER = "MATERIAL_ONLY_NUMBER";

    // 7.BCODE:物料拆分号
    public static final String BCODE_MATERIAL_SPLIT_NUMBER = "MATERIAL_SPLIT_NUMBER";

    // 8.BCODE:发票填写
    public static final String BCODE_MATERIAL_RECEIPT_FILL_IN = "RECEIPT_FILL_IN";

    // 1.物料生成号：初始号(需求单)
    public static final String MATERIAL_ONLY_NUMBER_START = "MON00000000";

    // 1.物料拆分号：初始号
    public static final String MATERIAL_SPLIT_NUMBER_START = "MSN00000000";

    // 1.日常用料状态：未统计成需求单
    public static final String DAILY_RECORD_STATUS_UNCOMPLETED = "1";

    // 2.日常用料状态：已统计成需求单
    public static final String DAILY_RECORD_STATUS_COMPLETED = "2";

    // 3.日常用料状态：库存足够，无需采购
    public static final String DAILY_RECORD_STATUS_WAREHOUSE_ENOUGH = "3";

    // 1.日常用料状态NAME：未统计成需求单
    public static final String DAILY_RECORD_STATUS_UNCOMPLETED_NAME = "未统计成需求单";

    // 2.日常用料状态NAME：已统计成需求单
    public static final String DAILY_RECORD_STATUS_COMPLETED_NAME = "已统计成需求单";

    // 3.日常用料状态NAME：库存足够，无需采购
    public static final String DAILY_RECORD_STATUS_WAREHOUSE_ENOUGH_NAME = "库存足够，无需采购";

    public static final Map<String, String> DAILY_RECORD_STATUS_MAP = new HashMap<String, String>();
    static {
        DAILY_RECORD_STATUS_MAP.put(DAILY_RECORD_STATUS_UNCOMPLETED, DAILY_RECORD_STATUS_UNCOMPLETED_NAME);
        DAILY_RECORD_STATUS_MAP.put(DAILY_RECORD_STATUS_COMPLETED, DAILY_RECORD_STATUS_COMPLETED_NAME);
        DAILY_RECORD_STATUS_MAP.put(DAILY_RECORD_STATUS_WAREHOUSE_ENOUGH, DAILY_RECORD_STATUS_WAREHOUSE_ENOUGH_NAME);
    }

    // 1.允许采购的物料
    public static final String IS_PURCHASE = "Y";

    // 2.不允许采购的物料
    public static final String IS_NOT_PURCHASE = "N";

    // 1.允许销售的物料
    public static final String IS_SELL = "Y";

    // 2.不允许销售的物料
    public static final String IS_NOT_SELL = "N";

    // 0.需求单状态：未提交
    public static final String REQUIRE_STORE_STATUS_NOT_SUBMIT = "0";

    // 1.需求单状态：待审批
    public static final String REQUIRE_STORE_STATUS_PENDING = "1";

    // 2.需求单状态：未通过审批
    public static final String REQUIRE_STORE_STATUS_UNPASS = "2";

    // 3.需求单状态：通过审批
    public static final String REQUIRE_STORE_STATUS_PASS = "3";

    // 4.需求单状态：部分完成订单
    public static final String REQUIRE_STORE_STATUS_PART_COMPLETED = "4";

    // 5.需求单状态：已完成订单
    public static final String REQUIRE_STORE_STATUS_COMPLETED = "5";

    // 6.需求单状态：作废
    public static final String REQUIRE_STORE_STATUS_SCRAP = "6";

    // 0.需求单状态NAME：未提交
    public static final String REQUIRE_STORE_STATUS_NOT_SUBMIT_NAME = "未提交";

    // 1.需求单状态NAME：待审批
    public static final String REQUIRE_STORE_STATUS_PENDING_NAME = "待审批";

    // 2.需求单状态NAME：未通过审批
    public static final String REQUIRE_STORE_STATUS_UNPASS_NAME = "未通过审批";

    // 3.需求单状态NAME：通过审批
    public static final String REQUIRE_STORE_STATUS_PASS_NAME = "通过审批";

    // 4.需求单状态NAME：部分完成订单
    public static final String REQUIRE_STORE_STATUS_PART_COMPLETED_NAME = "部分完成订单";

    // 5.需求单状态NAME：已完成订单
    public static final String REQUIRE_STORE_STATUS_COMPLETED_NAME = "已完成订单";

    // 6.需求单状态NAME：作废
    public static final String REQUIRE_STORE_STATUS_SCRAP_NAME = "作废";

    public static final Map<String, String> REQUIRE_STORE_STATUS_MAP = new HashMap<String, String>();
    static {
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_NOT_SUBMIT, REQUIRE_STORE_STATUS_NOT_SUBMIT_NAME);
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_PENDING, REQUIRE_STORE_STATUS_PENDING_NAME);
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_UNPASS, REQUIRE_STORE_STATUS_UNPASS_NAME);
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_PASS, REQUIRE_STORE_STATUS_PASS_NAME);
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_PART_COMPLETED, REQUIRE_STORE_STATUS_PART_COMPLETED_NAME);
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_COMPLETED, REQUIRE_STORE_STATUS_COMPLETED_NAME);
        REQUIRE_STORE_STATUS_MAP.put(REQUIRE_STORE_STATUS_SCRAP, REQUIRE_STORE_STATUS_SCRAP_NAME);
    }

    // 1.领用单状态：仓管备料中
    public static final String OUTPUT_USE_STATUS_PREPARING = "1";

    // 2.领用单状态：待领用
    public static final String OUTPUT_USE_STATUS_WAITING = "2";

    // 3.领用单状态：已领用
    public static final String OUTPUT_USE_STATUS_USE = "3";

    // 4.领用单状态：未领用
    public static final String OUTPUT_USE_STATUS_UNUSE = "4";

    // 4.领用单状态：部分领用
    public static final String OUTPUT_USE_STATUS_PART_USE = "5";

    // 1.领用单状态NAME：仓管备料中
    public static final String OUTPUT_USE_STATUS_PREPARING_NAME = "仓管备料中";

    // 2.领用单状态NAME：待领用
    public static final String OUTPUT_USE_STATUS_WAITING_NAME = "待领用";

    // 3.领用单状态NAME：已领用
    public static final String OUTPUT_USE_STATUS_USE_NAME = "已领用";

    // 4.领用单状态NAME：未领用
    public static final String OUTPUT_USE_STATUS_UNUSE_NAME = "未领用";

    // 5.领用单状态NAME：部分领用
    public static final String OUTPUT_USE_STATUS_PART_USE_NAME = "部分领用";

    public static final Map<String, String> OUTPUT_USE_STATUS_MAP = new HashMap<String, String>();
    static {
        OUTPUT_USE_STATUS_MAP.put(OUTPUT_USE_STATUS_PREPARING, OUTPUT_USE_STATUS_PREPARING_NAME);
        OUTPUT_USE_STATUS_MAP.put(OUTPUT_USE_STATUS_WAITING, OUTPUT_USE_STATUS_WAITING_NAME);
        OUTPUT_USE_STATUS_MAP.put(OUTPUT_USE_STATUS_USE, OUTPUT_USE_STATUS_USE_NAME);
        OUTPUT_USE_STATUS_MAP.put(OUTPUT_USE_STATUS_UNUSE, OUTPUT_USE_STATUS_UNUSE_NAME);
        OUTPUT_USE_STATUS_MAP.put(OUTPUT_USE_STATUS_PART_USE, OUTPUT_USE_STATUS_PART_USE_NAME);
    }

    // 1.调拨出库单据去向：本场
    public static final String OUTPUT_ALLOT_TRANSDESTINATION_SAME_FARM = "1";

    // 2.调拨出库单据去向：外场
    public static final String OUTPUT_ALLOT_TRANSDESTINATION_OTHER_FARM = "2";

    // 1.调拨出库单据状态：待审批
    public static final String OUTPUT_ALLOT_STATUS_PENDING = "1";

    // 2.调拨出库单据状态：未通过审批
    public static final String OUTPUT_ALLOT_STATUS_UNPASS = "2";

    // 3.调拨出库单据状态：通过审批
    public static final String OUTPUT_ALLOT_STATUS_PASS = "3";

    // 4.调拨出库单据状态：已完成入库
    public static final String OUTPUT_ALLOT_STATUS_COMPLETED = "4";

    // 5.调拨出库单据状态：作废
    public static final String OUTPUT_ALLOT_STATUS_SCRAP = "5";

    // 1.销售出库单据状态：待审批
    public static final String OUTPUT_SALE_STATUS_PENDING = "1";

    // 2.销售出库单据状态：未通过审批
    public static final String OUTPUT_SALE_STATUS_UNPASS = "2";

    // 3.销售出库单据状态：通过审批
    public static final String OUTPUT_SALE_STATUS_PASS = "3";

    // 4.销售出库单据状态：已完成入库
    public static final String OUTPUT_SALE_STATUS_COMPLETED = "4";

    // 5.调拨出库单据状态：作废
    public static final String OUTPUT_SALE_STATUS_SCRAP = "5";

    // 1.调拨出库单据状态NAME：待审批
    public static final String OUTPUT_ALLOT_STATUS_PENDING_NAME = "待审批";

    // 2.调拨出库单据状态NAME：未通过审批
    public static final String OUTPUT_ALLOT_STATUS_UNPASS_NAME = "未通过审批";

    // 3.调拨出库单据状态NAME：通过审批
    public static final String OUTPUT_ALLOT_STATUS_PASS_NAME = "通过审批";

    // 4.调拨出库单据状态NAME：已完成入库
    public static final String OUTPUT_ALLOT_STATUS_COMPLETED_NAME = "完成入库";

    // 5.调拨出库单据状态NAME：作废
    public static final String OUTPUT_ALLOT_STATUS_SCRAP_NAME = "作废";

    public static final Map<String, String> OUTPUT_ALLOT_STATUS_MAP = new HashMap<String, String>();
    static {
        OUTPUT_ALLOT_STATUS_MAP.put(OUTPUT_ALLOT_STATUS_PENDING, OUTPUT_ALLOT_STATUS_PENDING_NAME);
        OUTPUT_ALLOT_STATUS_MAP.put(OUTPUT_ALLOT_STATUS_UNPASS, OUTPUT_ALLOT_STATUS_UNPASS_NAME);
        OUTPUT_ALLOT_STATUS_MAP.put(OUTPUT_ALLOT_STATUS_PASS, OUTPUT_ALLOT_STATUS_PASS_NAME);
        OUTPUT_ALLOT_STATUS_MAP.put(OUTPUT_ALLOT_STATUS_COMPLETED, OUTPUT_ALLOT_STATUS_COMPLETED_NAME);
        OUTPUT_ALLOT_STATUS_MAP.put(OUTPUT_ALLOT_STATUS_SCRAP, OUTPUT_ALLOT_STATUS_SCRAP_NAME);
    }

    // 1.申报类型：饲料
    public static final String APPLY_TYPE_FEED = "1";

    // 2.申报类型：兽药
    public static final String APPLY_TYPE_ANIMAL_DRUGS = "2";

    // 3.申报类型：物质
    public static final String APPLY_TYPE_GOODS = "3";

    // 4.申报类型：原料
    public static final String APPLY_TYPE_RAW_MATERIAL = "4";

    // 1.紧急程度：普通
    public static final String EMERGENCY_TYPE_GENERAL = "1";

    // 2.紧急程度：紧急
    public static final String EMERGENCY_TYPE_HIGH = "2";

    // 1.集团或个人采购：集团
    public static final String GROUP_OR_SELF_GROUP = "1";

    // 2.集团或个人采购：个人
    public static final String GROUP_OR_SELF_SELF = "2";

    // 1.采购单据状态：待审批
    public static final String PURCHASE_STORE_STATUS_PENDING = "1";

    // 2.采购单据状态：未通过审批
    public static final String PURCHASE_STORE_STATUS_UNPASS = "2";

    // 3.采购单据状态：通过审批
    public static final String PURCHASE_STORE_STATUS_PASS = "3";

    // 4.采购单据状态：入库中
    public static final String PURCHASE_STORE_STATUS_INPUTING = "4";

    // 5.采购单据状态：已完成入库
    public static final String PURCHASE_STORE_STATUS_COMPLETED = "5";

    // 6.采购单据状态：作废
    public static final String PURCHASE_STORE_STATUS_SCRAP = "6";

    // 7.采购单据状态：完成
    public static final String PURCHASE_STORE_STATUS_FINISHED = "7";

    // 8.采购单据状态：手动完结
    public static final String PURCHASE_STORE_STATUS_OVER = "8";

    // 9.采购单据状态：系统自动完结
    public static final String PURCHASE_STORE_STATUS_AUTO_OVER = "9";

    // 1.采购单据状态NAME：待审批
    public static final String PURCHASE_STORE_STATUS_PENDING_NAME = "待审批";

    // 2.采购单据状态NAME：未通过审批
    public static final String PURCHASE_STORE_STATUS_UNPASS_NAME = "未通过审批";

    // 3.采购单据状态NAME：通过审批
    public static final String PURCHASE_STORE_STATUS_PASS_NAME = "通过审批";

    // 4.采购单据状态NAME：入库中
    public static final String PURCHASE_STORE_STATUS_INPUTING_NAME = "入库中";

    // 5.采购单据状态NAME：已完成入库
    public static final String PURCHASE_STORE_STATUS_COMPLETED_NAME = "完成入库";

    // 6.采购单据状态NAME：作废
    public static final String PURCHASE_STORE_STATUS_SCRAP_NAME = "作废";

    // 7.采购单据状态NAME：完成
    public static final String PURCHASE_STORE_STATUS_FINISHED_NAME = "订单完成";

    // 8.采购单据状态NAME：手动完结
    public static final String PURCHASE_STORE_STATUS_OVER_NAME = "手动完结";

    // 9.采购单据状态NAME：系统自动完结
    public static final String PURCHASE_STORE_STATUS_AUTO_OVER_NAME = "系统自动完结";

    public static final Map<String, String> PURCHASE_STORE_STATUS_MAP = new HashMap<String, String>();
    static {
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_PENDING, PURCHASE_STORE_STATUS_PENDING_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_UNPASS, PURCHASE_STORE_STATUS_UNPASS_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_PASS, PURCHASE_STORE_STATUS_PASS_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_INPUTING, PURCHASE_STORE_STATUS_INPUTING_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_COMPLETED, PURCHASE_STORE_STATUS_COMPLETED_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_SCRAP, PURCHASE_STORE_STATUS_SCRAP_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_FINISHED, PURCHASE_STORE_STATUS_FINISHED_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_OVER, PURCHASE_STORE_STATUS_OVER_NAME);
        PURCHASE_STORE_STATUS_MAP.put(PURCHASE_STORE_STATUS_AUTO_OVER, PURCHASE_STORE_STATUS_AUTO_OVER_NAME);
    }

    // 1.采购单据类型：物料采购
    public static final String PURCHASE_STORE_BILL_TYPE_MATERIAL = "1";

    // 2.采购单据类型：猪只采购
    public static final String PURCHASE_STORE_BILL_TYPE_PIG = "2";

    // 1.采购单据类型NAME：物料采购
    public static final String PURCHASE_STORE_BILL_TYPE_MATERIAL_NAME = "物料采购";

    // 2.采购单据类型NAME：猪只采购
    public static final String PURCHASE_STORE_BILL_TYPE_PIG_NAME = "猪只采购";

    public static final Map<String, String> PURCHASE_STORE_BILL_TYPE_MAP = new HashMap<String, String>();
    static {
        PURCHASE_STORE_BILL_TYPE_MAP.put(PURCHASE_STORE_BILL_TYPE_MATERIAL, PURCHASE_STORE_BILL_TYPE_MATERIAL_NAME);
        PURCHASE_STORE_BILL_TYPE_MAP.put(PURCHASE_STORE_BILL_TYPE_PIG, PURCHASE_STORE_BILL_TYPE_PIG_NAME);
    }

    // 1.采购物料购料/赠料：购料
    public static final String PURCHASE_OR_FREE_PURCHASE = "购料";

    // 2.采购物料购料/赠料：赠料
    public static final String PURCHASE_OR_FREE_FREE = "赠料";

    // 1.仓库状态：未初始化
    public static final String WAREHOUSE_STATUS_INITIALIZE = "1";

    // 2.仓库状态：使用中
    public static final String WAREHOUSE_STATUS_USING = "2";

    // 3.仓库状态：已禁用
    public static final String WAREHOUSE_STATUS_FORBIDDEN = "3";

    // 仓库状态：未初始化
    public static final String WAREHOUSE_STATUS_INITIALIZE_NAME = "未初始化";

    // 仓库状态：使用中
    public static final String WAREHOUSE_STATUS_USING_NAME = "使用中";

    // 仓库状态：已禁用
    public static final String WAREHOUSE_STATUS_FORBIDDEN_NAME = "已禁用";

    // 仓库类型：实体
    public static final String WAREHOUSE_TYPE_ENTITY_NAME = "实体型";

    // 仓库类型：虚拟
    public static final String WAREHOUSE_TYPE_FICTITIOUS_NAME = "虚拟型";

    // 1.仓库类型：实体
    public static final String WAREHOUSE_TYPE_ENTITY = "1";

    // 2.仓库类型：虚拟
    public static final String WAREHOUSE_TYPE_FICTITIOUS = "2";

    public static final Map<String, Map<String, String>> EVENT_CODE_AND_BILL_STATUS = new HashMap<String, Map<String, String>>();
    static {
        // 物料需求和状态
        EVENT_CODE_AND_BILL_STATUS.put(EVENT_CODE_MATERIAL_REQUIRE, REQUIRE_STORE_STATUS_MAP);
        // 物料领用出库和状态
        EVENT_CODE_AND_BILL_STATUS.put(EVENT_CODE_OUTPUT_USE, OUTPUT_USE_STATUS_MAP);
        // 物料调拨出库和状态
        EVENT_CODE_AND_BILL_STATUS.put(EVENT_CODE_OUTPUT_ALLOT, OUTPUT_ALLOT_STATUS_MAP);
        // 物料采购单据和状态
        EVENT_CODE_AND_BILL_STATUS.put(EVENT_CODE_MATERIAL_PURCHASE, PURCHASE_STORE_STATUS_MAP);
        // 饲料采购单据和状态
        EVENT_CODE_AND_BILL_STATUS.put(EVENT_CODE_FEED_PURCHASE, PURCHASE_STORE_STATUS_MAP);
    }

    // 1.领用去向:猪场全场
    public static final String OUTPUT_DESTINATION_FARM = "farm";

    // 2.领用去向:批次
    public static final String OUTPUT_DESTINATION_SWINERY = "swinery";

    // 3.领用去向:猪舍
    public static final String OUTPUT_DESTINATION_PIGHOUSE = "pigHouse";

    // 4.领用去向:部门
    public static final String OUTPUT_DESTINATION_DEPT = "dept";

    // 1.领用去向名称:猪场全场
    public static final String OUTPUT_DESTINATION_FARM_NAME = "猪场全场";

    // 2.领用去向名称:批次
    public static final String OUTPUT_DESTINATION_SWINERY_NAME = "批次";

    // 3.领用去向名称:猪舍
    public static final String OUTPUT_DESTINATION_PIGHOUSE_NAME = "猪舍";

    // 4.领用去向名称:部门
    public static final String OUTPUT_DESTINATION_DEPT_NAME = "部门";

    public static final Map<String, String> OUTPUT_DESTINATION_MAP = new HashMap<String, String>();
    static {
        // 领用去向:猪场全场
        OUTPUT_DESTINATION_MAP.put(OUTPUT_DESTINATION_FARM, OUTPUT_DESTINATION_FARM_NAME);
        // 领用去向:批次
        OUTPUT_DESTINATION_MAP.put(OUTPUT_DESTINATION_SWINERY, OUTPUT_DESTINATION_SWINERY_NAME);
        // 领用去向:猪舍
        OUTPUT_DESTINATION_MAP.put(OUTPUT_DESTINATION_PIGHOUSE, OUTPUT_DESTINATION_PIGHOUSE_NAME);
        // 领用去向:部门
        OUTPUT_DESTINATION_MAP.put(OUTPUT_DESTINATION_DEPT, OUTPUT_DESTINATION_DEPT_NAME);
    }

    // 1.发票相对路径
    public static final String SUPPLYCHAIN_RECEIPT_RELATIVEPATH = "supplyChain_receipt_relativePath";

    // 2.发票绝对路径
    public static final String SUPPLYCHAIN_RECEIPT_ABSOLUTEPATH = "supplyChain_receipt_absolutePath";

    // 发票后5位0-Z随机数
    public static final String[] FILE_NAME_RANDOM = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    // 大丰模式:总公司
    public static final String DAFENG_MODEL_PARENT = "1";

    // 大丰模式:子公司
    public static final String DAFENG_MODEL_CHILD = "2";

    // 大丰模式:非大丰模式
    public static final String DAFENG_MODEL_NOT = "-1";

    // 时间范围:开始时间
    public static final String START_DATE = "startDate";

    // 时间范围:结束时间
    public static final String END_DATE = "endDate";

    // 时间范围:月末时间
    public static final String MONTH_END_DATE = "monthEndDate";

    // 核算类别:1-集团公司
    public static final String ACCOUNT_COMPANY_CLASS_1 = "1";

    // 核算类别:2-企业
    public static final String ACCOUNT_COMPANY_CLASS_2 = "2";

    // 核算类别:3-猪场
    public static final String ACCOUNT_COMPANY_CLASS_3 = "3";

    // 核算类别:4-企业+猪场
    public static final String ACCOUNT_COMPANY_CLASS_4 = "4";

    // 核算类别:5-子猪场
    public static final String ACCOUNT_COMPANY_CLASS_5 = "5";

    // 核算类别:6-虚拟测试场
    public static final String ACCOUNT_COMPANY_CLASS_6 = "6";

    // 财务一级分类：药物
    public static final String FIRST_FINANCE_MATERIAL_TYPE_FINANCE_MEDICINE = "FINANCE_MEDICINE";

    // 财务一级分类：制造费用
    public static final String FIRST_FINANCE_MATERIAL_TYPE_FINANCE_PRODUCTION_COST = "FINANCE_PRODUCTION_COST";

    // 财务一级分类：饲料
    public static final String FIRST_FINANCE_MATERIAL_TYPE_FINANCE_FIRST_FEED = "FINANCE_FIRST_FEED";

    // 1.入库单状态：未提交
    public static final String ARRIVE_INPUT_STATUS_UNCOMMIT = "0";

    // 2.入库单状态：已提交
    public static final String ARRIVE_INPUT_STATUS_COMMITTED = "1";

    // 3.入库单状态：作废
    public static final String ARRIVE_INPUT_STATUS_SCRAP = "2";

    // 1.入库单状态：未提交
    public static final String ARRIVE_INPUT_STATUS_UNCOMMIT_NAME = "未提交";

    // 2.入库单状态：已提交
    public static final String ARRIVE_INPUT_STATUS_COMMITTED_NAME = "已提交";

    // 3.入库单状态：作废
    public static final String ARRIVE_INPUT_STATUS_SCRAP_NAME = "作废";

    public static final Map<String, String> ARRIVE_INPUT_STATUS_MAP = new HashMap<String, String>();
    static {
        // 入库单状态:猪场全场
        ARRIVE_INPUT_STATUS_MAP.put(ARRIVE_INPUT_STATUS_UNCOMMIT, ARRIVE_INPUT_STATUS_UNCOMMIT_NAME);
        // 入库单状态:批次
        ARRIVE_INPUT_STATUS_MAP.put(ARRIVE_INPUT_STATUS_COMMITTED, ARRIVE_INPUT_STATUS_COMMITTED_NAME);
        // 入库单状态:猪舍
        ARRIVE_INPUT_STATUS_MAP.put(ARRIVE_INPUT_STATUS_SCRAP, ARRIVE_INPUT_STATUS_SCRAP_NAME);
    }

    // 物料大类:大宗原料
    public static final String MATERIAL_FIRST_CLASS_10 = "10";

    // 物料大类:添加剂
    public static final String MATERIAL_FIRST_CLASS_11 = "11";

    // 物料大类:包装物
    public static final String MATERIAL_FIRST_CLASS_12 = "12";

    // 物料大类:半成品
    public static final String MATERIAL_FIRST_CLASS_15 = "15";

    // 物料大类:预混料
    public static final String MATERIAL_FIRST_CLASS_20 = "20";

    // 物料大类:配合料
    public static final String MATERIAL_FIRST_CLASS_21 = "21";

    // 物料大类:浓缩料
    public static final String MATERIAL_FIRST_CLASS_22 = "22";

    // 物料大类:药物
    public static final String MATERIAL_FIRST_CLASS_30 = "30";

    // 物料大类:周转材料
    public static final String MATERIAL_FIRST_CLASS_40 = "40";

    // 物料大类:制造费用
    public static final String MATERIAL_FIRST_CLASS_50 = "50";

    // 物料大类:精液
    public static final String MATERIAL_FIRST_CLASS_60 = "60";

    // 物料大类:备品备件
    public static final String MATERIAL_FIRST_CLASS_80 = "80";

    // 物料大类:固定资产
    public static final String MATERIAL_FIRST_CLASS_90 = "90";

    // 0元原因:购买
    public static final String REBATE_REASON_A = "A";

    // 0元原因:赠送
    public static final String REBATE_REASON_B = "B";

    // 0元原因:促销返利
    public static final String REBATE_REASON_C = "C";

    // 0元原因:购买（无需填写为空白）
    public static final String REBATE_REASON_A_NAME = "";

    // 0元原因:赠送
    public static final String REBATE_REASON_B_NAME = "赠送";

    // 0元原因:促销返利
    public static final String REBATE_REASON_C_NAME = "促销返利";

    public static final Map<String, String> REBATE_REASON_MAP = new HashMap<String, String>();
    static {
        // 0元原因:购买
        REBATE_REASON_MAP.put(REBATE_REASON_A, REBATE_REASON_A_NAME);
        // 0元原因:赠送
        REBATE_REASON_MAP.put(REBATE_REASON_B, REBATE_REASON_B_NAME);
        // 0元原因:促销返利
        REBATE_REASON_MAP.put(REBATE_REASON_C, REBATE_REASON_C_NAME);
    }

    // 仓库类别:兽药仓库
    public static final String WAREHOUSE_CATEGORY_MATERIAL = "1";

    // 仓库类别:饲料仓库
    public static final String WAREHOUSE_CATEGORY_FEED = "2";

    // 仓库类别:添加药仓库
    public static final String WAREHOUSE_CATEGORY_ADDITIVE = "3";

    // 仓库类别:精液仓库
    public static final String WAREHOUSE_CATEGORY_SPERM = "4";

    // 仓库类别:实验室仓库
    public static final String WAREHOUSE_CATEGORY_LABORATORY = "5";

}
