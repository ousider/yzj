package xn.pigfarm.exception.hana;

import xn.core.exception.IException;

public enum HanaPropertyException implements IException {
    BEAN_NAME_IS_NULL("数据源名称不能为空！", 1),
    IP_AND_PORT_NULL("数据源地址及端口号不能为空！", 2),
    DB_NAME_IS_NULL("DB名】不能为空！", 3),
    DB_USER_NAME_IS_NULL("数据库账号不能为空！", 4),
    DB_PASSWORD_IS_NULL("密码不能为空！", 5),
    BEAN_NAME_DUPLICATE_ERROR("数据源名称存在重复，请重新输入！", 6),
    BEAN_NAME_DATASOURCE("数据源名称不能以dataSource为名称，请重新输入！", 7);

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    HanaPropertyException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    HanaPropertyException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    HanaPropertyException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    HanaPropertyException(String message, ExceptionLevel level, long errorCode) {
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
