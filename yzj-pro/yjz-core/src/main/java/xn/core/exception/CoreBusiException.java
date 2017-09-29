package xn.core.exception;

/**
 * @Description: 通用业务逻辑异常
 * @author Zhangjc
 * @date 2016年4月13日 下午6:20:49
 */
public enum CoreBusiException implements IException {

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
    PP_BILL_ERROR("单据日期不能为空！", 100007), PP_EVENT_ERROR("事件明细日期不能为空！", 100007),
    SIZE_NOT_SAME_ERROR("数组长度不一致！", 110000), CUD_OPERATION_ERROR("CUD操作失败", 110100),
    LOAD_PROPERTIES_ERROR("加载PROPERTIES文件错误，【%s】", 110001),
    UPLOAD_REQUERST_ERROR("上传请求错误。", 110002), LOGIN_CODE_ERROR("编码设置错误。", 110003),
    LOGIN_PASSWORD_ERROR("输入密码不正确。", 110004), LOGIN_NAME_ERROR("用户名或公司编码不正确。", 110005),
    NOTFOUND_HIDDEN_DATA("excel模板导出，【%s】不能为空！", 110006), XMLFARMAT_OR_XMLPATH("xml格式或路径错误！", 110007),
    EXCEPTION_DATA("【%s】！", 110008), XML_EXCEPTION("XML配置文件【%s】出错！", 110009), OPENID_EXCEPTION("登录出错", 110010);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    CoreBusiException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    CoreBusiException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    CoreBusiException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    CoreBusiException(String message, ExceptionLevel level, long errorCode) {
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

    @Override
    public long getErrorCode() {
        return this.errorCode;
    }

}
