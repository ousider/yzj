package xn.pigfarm.exception.initialization;

import xn.core.exception.IException;

public enum InputHouseException implements IException{
    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    HAS_NO_INPUT_HOUSE("无初始化数据，请先导入！", 111201),
    INPUT_HOUSE_TYPE_NULL("第【%S】行,猪舍类型未填！",111202),
    INPUT_HOUSE_NAME_NULL("第【%S】行,猪舍名称未填！",111203),
    INPUT_HOUSE_NAME_REPEAT("第【%S】行,猪舍名称已存在！",111204),
    INPUT_PIGPEN_NAME_NULL("第【%S】行,猪栏名称未填",111205),
    INPUT_PIGPEN_NAME_REPEAT("第【%S】行,同一猪舍猪栏名称重复",111206),
    INPUT_HOUSE_ERROR("%S",11120);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    InputHouseException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    InputHouseException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    InputHouseException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    InputHouseException(String message, ExceptionLevel level, long errorCode) {
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
