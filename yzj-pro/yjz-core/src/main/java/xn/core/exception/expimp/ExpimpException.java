package xn.core.exception.expimp;

import xn.core.exception.IException;

/**
 * @Description:猪只初始化异常类
 * @author zhangjs
 * @date 2016年8月16日 下午5:02:23
 */
public enum ExpimpException implements IException {

    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    NO_EXCEL_FILE_EXPORT("请先选择需要导入的EXCEL文件！", 111301),
    NOT_FORMAT_EXCEL_FILE_EXPORT("请下载系统生成的EXCLE模板文件进行导入！", 111302);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    ExpimpException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    ExpimpException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    ExpimpException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    ExpimpException(String message, ExceptionLevel level, long errorCode) {
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
