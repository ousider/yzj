package xn.core.exception;

/**
 * @Description: 缓存异常
 * @author zhangjs5
 * @date 2016年5月4日 上午9:48:25
 */
public enum CacheBusiException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    COL_VAL_SIZE_ERROR("入参的列(cols)和对应的值(values)长度不一致", 119901),
    COL_VAL_NONE_ERROR("入参的列(cols)和对应的值(values)不能一个为空，一个不为空", 119902),
    CONFIG_TABLE_CAHCE_ERROR("未配置改缓存对象", 119903),
    CACHE_TABLE_LOADDATA_ERROR("INPUTCacheTable方法loadData入参为空",119905),
    CACHE_TABLE_LOADDATA_NO_PRIMARY_ERROR("INPUTCacheTable方法loadData入参不包含主键", 119906),
    CACHE_TABLE_NO_DATA_ERROR("根据表名[%s]和主键[%s]未查到相应缓存数据", 119907),
    COMMON_COL_VAL_SIZE_ERROR("入参[%s]和[%s]值长度不一致", 119908),
    SET_NAME_TYPE_CODE_ERROR("赋值codeName，CodeEnum必须穿", 119909),
    PARAM_NOT_NULL_ERROR("入参【%s】不能为空", 119910);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    CacheBusiException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    CacheBusiException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    CacheBusiException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    CacheBusiException(String message, ExceptionLevel level, long errorCode) {
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
