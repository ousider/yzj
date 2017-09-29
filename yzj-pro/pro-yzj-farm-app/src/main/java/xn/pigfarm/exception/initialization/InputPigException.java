package xn.pigfarm.exception.initialization;

import xn.core.exception.IException;

/**
 * @Description:猪只初始化异常类
 * @author zhangjs
 * @date 2016年8月16日 下午5:02:23
 */
public enum InputPigException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    HAS_NO_INPUT_PIG("无初始化数据，请先导入！", 111201),
    HAS_NO_HOUSE("系统无猪舍无法初始化！", 111202),
    HAS_NO_MATERIAL("系统无物料主数据无法初始化！", 111203),
    INPUT_PIG_ERROR("%S",111204),
    MATERIAL_IS_NULL("第【%s】行，物料主数据不能为空！",111205),
    HOUSE_IS_NULL("第【%s】行，猪舍不能为空！",111206),
    SWINERY_IS_NULL("第【%s】行，批次不能为空！",111207),
    PIG_CLASS_IS_NULL("第【%s】行，猪只状态不能为空！",111208),
    ENTER_DATE_IS_NULL("第【%s】行，统计日期不能为空！",111209),
    DAY_AGE_IS_NULL("第【%s】行，日龄不能为空！",111210),
    TOTAL_NUM_IS_NULL("第【%s】行，数量不能为空！",111211),
    AV_WEIGHT_IS_NULL("第【%s】行，均重不能为空！",111212);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    InputPigException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    InputPigException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    InputPigException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    InputPigException(String message, ExceptionLevel level, long errorCode) {
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
