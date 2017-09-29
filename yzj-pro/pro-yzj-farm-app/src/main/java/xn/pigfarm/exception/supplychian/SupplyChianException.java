package xn.pigfarm.exception.supplychian;

import xn.core.exception.IException;

/**
 * @Description: 供应链业务逻辑异常
 * @author THL
 * @date 2016年9月7日 下午16:20:49
 */
public enum SupplyChianException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    NOT_HAVE_EFFECTIVE_DETAIL("后台-未添加有效明细不能提交！", 100001),
    SELF_DEFINITION_ERROR("%s", 100002),
    NUMBER_IS_ZERO("第【%s】行【%s】不能小于等于零！", 100003),
    SPEC_IS_DIFFERENT("第【%s】行规格不同，无法入库！", 100004),
    EFFECTIVE_DATE_IS_NULL("第【%s】行生产/有效日期不能为空！", 100005),
    OUTPUT_QTY_MORE_THAN_EXISTS_QTY("物料名称【%s】的【实际库存数量】不够，超出库存【%s】，请拆分后，再填写！", 100006),
    AllOT_QTY_IS_WRONG("第【%s】行的入库数量超出调拨猪场的库存数量，请联系调拨猪场，重新填写调拨单！", 100007),
    PURCHASE_QTY_LESS_THAN_ARRIVE_QTY("第【%s】行的采购数量不能小于已入库数量！", 100008),
    RECEIPT_NUMBER_IS_NULL("发票号还未填写完，无法完成！", 100009),
    WAREHOUSE_IS_NULL("第【%s】行仓库不能为空！", 100010),
    ALLOT_FARM_HAS_NOT_THIS_MATERIAL("第【%s】行的物料没有同步给调入（采购）公司，请联系集团采购！", 100011),
    QTY_IS_NOT_ENOUGH("第【%s】行的【实际库存数量】不够，请刷新页面重新操作！", 100012),
    QTY_HAS_CHANGED("第【%s】行的【数据数量】已被修改，请刷新页面重新操作。。。！", 100013),
    INFOS_IS_ERROR("数据异常，请刷新页面，重新操作。。。！", 100014),
    PURCHASE_OR_FREE_IS_NULL("第【%s】行购/赠料不能为空！", 100015),
    PURCHASE_MUST_BE_ONLY("购料必须只存在一个，赠料可以有多个！", 100016),
    PURCHASE_QTY_MUST_BE_INTEGER("第【%s】行采购数量必须为整数！", 100017),
    PURCHASE_QTY_MUST_DIVIDED_WITH_NO_REMAINDER("第【%s】行赠料比例为【%s】，采购数量必须整除【%s】！", 100018),
    FREE_QTY_MUST_BE_INTEGER("第【%s】行采购物料中，第【%s】行的赠料，采购数量必须为整数！", 100019),
    INPUT_QTY_MUST_LESS_THAN_PURCHASE_QTY("第【%s】行的【入库数量】+【已入库数量】比【采购数量】多了【%s】, 若入库单错误，请重新做入库单，若采购单错误，请联系采购员调整数量！", 100020),
    NOT_PURCHASE_USER("您登陆的账号不是采购员账号！", 100021),
    PURCHASE_TYPE_NOT_NULL("采购类型不能为空！", 100022),
    INPUT_QTY_CAN_NOT_MORE_THAN_OUTPUT_QTY("第【%s】的【入库数量】不能大于【可入库数量】！", 100023),
    ADD_MATERIAL_FEED_REMAINDER_TWO_THOUSAND("第【%s】的【%s】是添加药饲料，必须申报【2000】KG的倍数！", 100024),
    INPUT_RECORD_LIST_IS_EMPTY("入库明细不能为空！", 100025), 
    RECEIPT_RECORD_LIST_IS_EMPTY("发票明细不能为空,若无【真实发票】，请勾选【无发票勾选】！", 100026), 
    RECEIPT_RECORD_RECEIPTNUMBER_IS_EMPTY("发票明细中,第【%s】行的【发票号】不能为空！", 100027),
    RECEIPT_RECORD_PRICE_IS_EMPTY("发票明细中,第【%s】行的【金额】必须大于零！", 100028),
    RECEIPT_RECORD_TRANSPORTPRICE_IS_EMPTY("发票明细中,第【%s】行的【运费】不能小于零！", 100029), 
    RECEIPT_RECORD_FILE_IS_EMPTY("发票明细中,第【%s】行的【上传附件】不能为空！", 100030),
    PRICE_UNEQUAL("【发票总金额(不含运费)】和【入库物料总金额】不相等！", 100031),
    PIG_HOUSE_IS_NULL("领用去向为【%s】,第【%s】的【猪舍】不能为空！", 100032),
    PIG_SWINERY_IS_NULL("领用去向为【%s】,第【%s】的【批次】不能为空！", 100033), 
    USE_QTY_IS_NOT_ENOUGH("物料名称【%s】的【实际库存数量】不够，超出库存【%s】！", 100034),
    OUTPUT_MIN_QTY_ERROR("第【%s】行,物料【%s】领用数量输入不符合要求，不是最小领用量：【%s】的整数倍,请重新输入！", 100035),
    MATERIAL_ID_IS_NULL("第【%s】行【物料名称】不能为空！", 100036),
    CAN_NOT_MERGE("两个物料不是拆分所得，无法合并！", 100037),
    WAITING_OUTPUT_CAN_NOT_SPLIT("已经完成备料的物料无法拆分！", 100038),
    WAITING_OUTPUT_CAN_NOT_MERGE("已经完成备料的物料无法合并！", 100039), 
    COMFIRM_QTY_IS_NOT_EQUAL_EXISTS_QTY("第【%s】行的【库存数量】必须和【确认数量】相等！", 100040),
    COMFIRM_QTY_IS_NULL("第【%s】行的【确认数量】不能为空！", 100041), 
    BEFORE_CHECK_START_DATE("日期不能小于【%s】，如需修改，请联系管理员！", 100042), 
    AFTER_CHECK_END_DATE("日期不能大于【%s】！", 100043), 
    PURCHASE_BILL_DATE_AND_INPUT_BILL_DATE_IS_NOT_A_SAME_MONTH("【入库日期】与【采购日期】不在同一个月份，请联系管理员！", 100044),
    PURCHASE_PRICE_CHANGE_AT_MID_MONTH("【%s】在【月中】存在价格变动，入库日期为【%s】，请选择【%s】的采购单据，如果不存在这样的单据，请联系管理员！", 100045),
    ALLOT_INPUT_QTY_CAN_NOT_MORE_THAN_OUTPUT_QTY("第【%s】的【入库数量】必须等于【可入库数量】！", 100046),
    SUPPLYID_IS_NULL("供应商不能为空！", 100047),
    MONTH_IS_NULL("月份不能为空！", 100048),
    MONTHSPLIT_IS_NULL("日期范围不能为空！", 100049),
    DETAILPRICE_IS_NULL("详情价格不能为空！", 100050),
    SUPPLYMONTHSPLIT_IS_NULL("区间已存在价格,请删除后重新添加！",100051),
    SHOW_PROMPT_MESSAGE("SHOW_PROMPT_MESSAGE", 100052), 
    PRODUCTION_DATE_AFTER_BILL_DATE("第【%s】行的【生产日期】必须小于等于【入库日期】！", 100053),
    EFFECTIVE_DATE_BEFORE_BILL_DATE("第【%s】行的【有效日期】必须大于等于【入库日期】！", 100054),
    REQUIRE_FARM_CAN_NOT_NULL("第【%s】行的【需求公司】不能为空！", 100055),
    INPUT_DATE_NOT_AFTER_OUTPUT_DATE("第【%s】行的【入库日期】必须小于等于【出库日期】！", 100056),
    PURCHASE_BILL_HAVE_BEEN_LOCKED("采购订单存在【未提交】的【入库单】，请先将【未提交】的入库单【提交】或【作废】！", 100057),
    PURCHASE_BILL_HAVE_BEEN_LOCKED_WITH_LINENUMBER("第【%s】行的采购订单存在【未提交】的【入库单】，请先将【未提交】的入库单【提交】或【作废】！", 100058),
    USE_MATERIAL_PIG_TYPE_IS_NULL("第【%s】行的【猪只品名】不能为空！", 100059),
    REBATE_REASON_IS_NULL("第【%s】行的【单价】为【0元】，【0元原因】不能为空！", 100060),
    RECEIPT_QTY_CAN_NOT_BE_ZERO("物料明细中，第【%s】行的【开票数量】不能为【0】！", 100061),
    RECEIPT_QTY_MUST_LESS_THAN_INPUTQTY("物料明细中，第【%s】行的【开票数量】不能大于【入库数量-已开票数量】！", 100062)
    ;
    
    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    SupplyChianException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    SupplyChianException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    SupplyChianException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    SupplyChianException(String message, ExceptionLevel level, long errorCode) {
        this.message = message;
        this.level = level;
        this.errorCode = errorCode;
    }

    @Override
    public String getCode() {
        return toString();
    }

    @Override
    public ExceptionLevel getLevel() {
        return this.level;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public long getErrorCode() {
        return this.errorCode;
    }

}
