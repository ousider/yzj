package xn.core.exception;

/**
 * @Description: 缓存异常
 * @author zhangjs5
 * @date 2016年5月4日 上午9:48:25
 */
public enum TimeTaskException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    HAS_EXECUTE_TASK_ERROR("【%s】存在正在执行的工单", 119801),
    PRC_EXECUTING_ERROR("工单执行出错:【%s】", 119802);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    TimeTaskException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    TimeTaskException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    TimeTaskException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    TimeTaskException(String message, ExceptionLevel level, long errorCode) {
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
