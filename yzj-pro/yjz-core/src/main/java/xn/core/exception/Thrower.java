package xn.core.exception;

import java.util.Map;

import org.apache.log4j.Logger;

import xn.core.exception.IException.ExceptionLevel;
import xn.core.service.IParamService;
import xn.core.util.ParamUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;

/**
 * @Description: 自定义异常抛出
 * @author Zhangjc
 * @date 2016年4月13日 下午6:30:51
 */
public class Thrower {

    private static Logger log = Logger.getLogger(Thrower.class);

    public static String throwException(IException e, Object... param) throws RuntimeException {
        String msg = String.format(e.getMessage(), param);
        return throwException(e.getCode(), e.getLevel(), msg, e.getErrorCode());
	}
	
    /**
     * @Description:异常的实现
     * @author Zhangjc
     * @param code
     * @param level
     * @param msg
     * @return
     * @throws Exception
     */
    private static String throwException(String code, ExceptionLevel level, String msg, long errorCode) throws RuntimeException {
		
        // 异常统一以XNJIA_开头，以后外围接口时可以准确定位错误来源
		String acctCode = code;
        if (!code.startsWith("XNJIA_")) {
            acctCode = "XNJIA_" + code;
		}
		
		String message = code + ":" + msg;
		if (level == ExceptionLevel.WARN) {
            log.warn(message);
		} else if (level == ExceptionLevel.ERROR) {
            log.error(message);
		}

        Map<String, Object> inputMap = ParamUtil.getInputMap();
        String eventName = Maps.getString(inputMap, CommonConstants.EVENT_NAME);
        if (StringUtil.isNonBlank(eventName)) {
            IParamService paramService = ParamUtil.getParamService();
            try {
                paramService.editSYS_L_ERROR(eventName, String.valueOf(errorCode), msg);
            }
            catch (Exception e) {
            }
        }

        // String[] info = new String[] { acctCode, msg };
		
        String appCode = acctCode;
        if (!StringUtil.isNumeric(appCode)) {
			appCode = "-1";
		}
        // msg = info[1];
        BaseException ex = new BaseException(appCode, msg);
		
        throw ex;
	}
	
}
