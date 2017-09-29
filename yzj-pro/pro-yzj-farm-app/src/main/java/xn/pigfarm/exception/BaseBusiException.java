package xn.pigfarm.exception;

import xn.core.exception.IException;

/**
 * @Description: 通用业务逻辑异常
 * @author Zhangjc
 * @date 2016年4月13日 下午6:20:49
 */
public enum BaseBusiException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    NAME_DUPLICATE_ERROR("名字【%s】存在重复，请重新输入！", 100001),
    CODE_DUPLICATE_ERROR("编码【%s】存在重复，请重新输入！", 100002),
    NAME_DUPLICATE_ROW_ERROR("第【%s】行，名字【%s】存在重复，请重新输入！", 100003),
    CODE_DUPLICATE_ROW_ERROR("第【%s】行，编码【%s】存在重复，请重新输入！", 100004),
    ERROR_MESSAGE("【%s】", 100005),
    PP_EVENT_TIME_ERROR("第【%s】行，事件日期：【%s】 不在有效范围 【%s】 和 【%s】 区间", 100006),
    PP_BILL_ERROR("单据日期不能为空！", 100007),
    PP_EVENT_ERROR("事件明细日期不能为空！", 100008),
    NOT_LAST_EVENT_ERROR("单据无法撤销，耳牌号【%s】的猪只进行了其他事件", 100009),
    LAST_EAR_BRAND_HAS_BEEN_USED("单据无法撤销，耳牌号【%s】的猪只的原耳牌号【%s】已被使用", 100010),
    LAST_HOUSE_CAPACITY_IS_NOT_ENOUGH("单据无法撤销，原猪舍【%s】没有足够的空间", 100011),
    CHILD_PIG_HAS_DONE_OTHER_THINGS("单据无法撤销，耳牌号【%s】的仔猪已经进行了其他事件", 100012),
    SPERM_HAS_BEEN_USED("单据无法撤销，公猪的精液编号【%s】已被耳牌号【%s】的母猪使用", 100013),
    SIZE_NOT_SAME_ERROR("数组长度不一致！", 100014),
    CUD_OPERATION_ERROR("CUD操作失败", 100015),
    NOTFOUND_HIDDEN_DATA("excel模板导出，【%s】下拉框数据不能为空！", 100016),
    EXCEL_IMPORT_DATA_ERROR("第【%s】行数据有误【%s】！", 100017),
    EXCEL_IMPORT_FILE_ERROR("上传的文件没有需要的数据！", 100018),
    MORE_THAN_ONE_OLD_BOARD_PIG("该母猪为寄入母猪，请选择耳牌号为【%s】的寄出母猪操作撤销！", 100019),
    CUSSUP_NAME_DUPLICATE_ERROR("该全称存在重复，请重新输入！", 100020),
    MATERIAL_IS_NOT_ONLY_GROUP_HAVE("物料名称【%s】，已经同步给集团下公司使用，请先收回主数据再删除！", 100021),
    MATERIAL_HAS_BEEN_USED("物料名称【%s】已经被使用，无法删除！", 100022),
    MATERIAL_IS_BOAR_OR_SOW("物料名称【%s】，已经是物料名称【%s】的%s！", 100023),
    BOAR_HAS_BREED_WITH_SOW("公猪【%s】已与母猪【%s】配种，无法撤销【后备转生产】！", 100024),
    CANCEL_TO_DELIVERY_HOUSE_PIGPEN_HAS_SOW_PIG("母猪【%s】撤销回的产房舍【%s】栏位【%s】，已经存在母猪【%s】，一个产房栏只能存在一只母猪，无法撤销！", 100025),
    CHILD_CAN_NOT_DEL_GROUP_CREATE("物料名称【%s】由集团所创建，无法自己删除！", 100026),
    LATITUDE_BEYOND_SCOPE("维度【%s】超出范围，请重新输入！", 100027),
    SAP_CODE_DUPLICATE_ERROR("【%s】单元号和栋号存在重复，请重新输入！", 100028),
    SEMEN_SALE_ERROR("精液批号【%s】,存在已经销售的精液，不能撤销", 100029),
    SPERM_HAS_BEEN_SALE("单据无法撤销，精液编号【%s】已经被【销售】", 100030);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    BaseBusiException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    BaseBusiException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    BaseBusiException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    BaseBusiException(String message, ExceptionLevel level, long errorCode) {
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
