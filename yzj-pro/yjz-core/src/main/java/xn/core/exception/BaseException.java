package xn.core.exception;

import xn.core.util.data.StringUtil;

/**
 * @Description: 自定义的基础异常类
 * @author Zhangjc
 * @date 2016年4月13日 下午6:36:13
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -1260391665754589322L;

    // private static final Pattern PARAM_PATTERN = Pattern.compile("\\{\\}");

    private String exceptionCode = "";

    private String exceptionMessage = "";

    // private String exceptionStack = "";

    // private Object[] exceptionParams;

    public BaseException(String exceptionCode, String exceptionMessage) {

        this.exceptionCode = exceptionCode;
        this.exceptionMessage = StringUtil.substring(exceptionMessage.trim(), 0, 200);
   }

    // public BaseException(String exceptionCode, String exceptionMessage, String exceptionStack) {
    //
    // this.exceptionCode = exceptionCode;
    // this.exceptionMessage = exceptionMessage;
    // this.exceptionStack = exceptionStack;
    // }
    //
    // public BaseException(String exceptionCode, String exceptionMessage, Object[] exceptionParams) {
    //
    // this.exceptionCode = exceptionCode;
    // this.exceptionMessage = exceptionMessage;
    // this.exceptionParams = exceptionParams;
    // }

    /**
     * @Description:异常编码
     * @author Zhangjc
     * @return
     */
    public String getExceptionCode() {

        return exceptionCode;
    }

    /**
     * @Description: 异常信息
     * @author Zhangjc
     * @return
     */
    public String getMessage() {

        // if (exceptionParams != null) {
        // Matcher matcher = PARAM_PATTERN.matcher(exceptionMessage);
        //
        // StringBuffer sb = new StringBuffer();
        // int index = 0;
        // while (matcher.find()) {
        // Object value = exceptionParams[(index++)];
        // if ((value != null) && ((value instanceof Class))) {
        // value = ((Class) value).getName();
        // }
        //
        // String text = "";
        // if (value != null) {
        // text = StringUtil.substring(value.toString().trim(), 0, 200);
        // }
        // matcher.appendReplacement(sb, text);
        // }
        // matcher.appendTail(sb);
        // exceptionMessage = sb.toString();
        // }
        return exceptionMessage;
    }

    public String getExceptionMessage() {

        return "BaseException [exceptionCode=" + exceptionCode + ", exceptionMessage=" + getExceptionMessage() + "]";
    }
}
