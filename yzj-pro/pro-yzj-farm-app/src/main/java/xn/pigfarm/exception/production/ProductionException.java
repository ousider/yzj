package xn.pigfarm.exception.production;

import xn.core.exception.IException;

/**
 * @Description: 生产管理异常类 模块04
 * @author Administrator
 * @date 2016年8月24日 上午10:38:51
 */
public enum ProductionException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    TO_DELI_DUPLICATE_ERROR("第【%s】行，母猪【%s】已在产房，请选择内转！", 1004001),
    CHANGE_HOUSE_PIG_PIGPEN_ERROR("产房中一个猪栏只能存放一头母猪，第【%s】行，耳牌号:【%s】的猪只,已存在于【%s】的猪栏中！",1004002),
    CHANGE_HOUSE_PIGPEN_PRECISE_ERROR("系统开启了【种猪转舍精确到栏】功能，第【%s】行，耳牌号：【%s】从【%s】转舍到【%s】栏号不能为空！",1004003),
    CHANGE_HOUSE_BEYOND_PROHIBITION_ERROR("系统未开启【允许超出猪舍存栏】功能，【%s】设定存栏：%s，转入后存栏：%s",1004004),
    CHANGE_HOUSE_PIG_EXISTS_ERROR("第【%s】行，耳牌号：【%s】猪只已在【%s】",1004005),
    CHANGE_HOUSE_IS_NULL("第【%s】行，转入猪栏已填，转入猪舍不能为空！",1004006),
    CHANGE_HOUSE_IS_SAME("第【%s】行转入猪栏和第【%s】行相同，请修改！",1004007),
    GOOD_PIG_DIE_HAVE_MULTIPLE_SOW("第【%s】行，【%s】栏位的断奶仔猪存在多个代养母猪！",1004008),
    PIGPEN_HAVE_MULTIPLE_SOW("【%s】栏位存在【%s】头母猪！",1004009),
    NURSE_CHANGE_HOUSE_PIGQTY_ERROR("第【%s】行，带仔数不等于断奶数+死亡数+寄养数！",1004010),
    WEAN_WEIGHT_IS_NULL("第【%s】行，断奶窝重不能为空！",1004011),
    BOARD_SOW_IS_NULL("第【%s】行，寄养数量已填，代养母猪不能为空！",1004012),
    NURSE_CHANGE_HOUSE_INHOUSEID_IS_NULL("第【%s】行，奶妈转入猪舍不能为空！",1004013),
    NURSE_CHANGE_HOUSE_INPIGPEN_IS_NULL("第【%s】行，奶妈转入猪栏不能为空!",1004014),
    NURSE_CHANGE_HOUSE_INPIG_HOUSEID_IS_NULL("第【%s】行，奶妈转入栏位存在母猪，必须转出，原转入猪舍不能为空！",1004015),
    WORKER_IS_NULL("第【%s】行，技术员不能为空！",1004016),
    BOARDSOW_IS_SAME_AS_INPIG("第【%s】行，原栏位母猪不能和代养母猪一样，请选择其他栏位",1004017),
    NURSE_CAN_NOT_BE_INPIG("一个单据中，奶妈不能成为原栏位母猪，如需完成此业务，需要两张单据！",1004018),
    BOARDSOW_CAN_NOT_BE_INPIG("一个单据中，代养母猪不能成为原栏位母猪，如需完成此业务，需要两张单据！",1004019),
    NURSE_CAN_NOT_BE_BOARDSOW("一个单据中，奶妈不能成为代养母猪，如需完成此业务，需要两张单据！",1004020),
    NURSE_FOSTER_QTY_IS_NULL("第【%s】行，代养母猪已填，寄养数量不能为空或不能为0！",1004021),
    NURSE_INPIGPEN_IS_SAME("一个单据中,奶妈转入栏位不能相同",1004022),
    NURSE_INPIGPEN_PIG_IS_RS("原栏位的母猪【%s】为妊娠状态，无法做奶妈转舍",1004023),
    SOW_CAN_NOT_FOSTER("第【%s】行，耳牌号：【%s】猪只，未分娩，不能做寄养!", 1004024), 
	SOW_HAS_NO_BABY("第【%s】行，耳牌号为【%s】的猪只，带仔数小于或等于0不能做寄养！", 1004025), 
	FOSETER_QTY_MORE_THAN_PIGQTY("第【%s】行，耳牌号：【%s】猪只，带仔数小于寄养数量，不能做寄养！", 1004026), 
	FOSTER_QTY_LESS_THAN_ZERO("第【%s】行，耳牌号：【%s】猪只，寄养数量不大于0！", 1004027),
	MATERIAL_IS_NOT_SAME("【%s】品种,【%s】品系的【%s】的主数据,未在本场建立，请先建立！",1004028),
	PIG_CLASS_IS_SAME("第【%s】行，耳牌号：【%s】的猪只，已为【%s】不需要重复操作！",1004029),
	TOWORK_DATE_CAN_NOT_BE_NULL("第【%s】行，耳牌号：【%s】猪只，转生产日期不能为空！",1004030),
	BOAR_RESERVE_TOPRODUCT_IS_NOT_BOAR("第【%s】行，耳牌号：【%s】猪只，不为公猪，不能转生产公猪！",1004031),
    SOW_RESERVE_IS_NOT_SOW("第【%s】行，耳牌号：【%s】猪只，不为母猪，不能转后备待配！",1004032),
    TO_CHILD_CARE_IS_NOT_PORK("第【%s】行，耳牌号：【%s】猪只，不为肉猪，不能转保育！",1004033),
    TO_FATTEN_IS_NOT_PORK("第【%s】行，耳牌号：【%s】猪只，不为肉猪，不能转育肥！", 1004034), 
    PIG_SWINERY_IS_CLOSE("批次【%s】已经【关闭】，请先打开批次！", 1004035), 
    PIG_SWINERY_HAS_PIG("批次【%s】中尚有猪只，无法关闭，请刷新页面！", 1004036);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    ProductionException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    ProductionException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    ProductionException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    ProductionException(String message, ExceptionLevel level, long errorCode) {
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
