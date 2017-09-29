package xn.core.exception;

/**
 * @Description: 定义异常的枚举
 * @author Zhangjc
 * @date 2016年4月15日 下午2:30:24
 */
public interface IException {

    public enum ExceptionLevel {
		INFO, WARN, ERROR, FAIL
	}
	
    // 异常编码
    String getCode();
	
    // 异常等级
	ExceptionLevel getLevel();
	
    // 异常信息
	String getMessage();

    // 获取错误代码
    long getErrorCode();
}
