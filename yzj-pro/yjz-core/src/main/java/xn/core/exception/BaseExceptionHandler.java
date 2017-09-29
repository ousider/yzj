package xn.core.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import xn.core.util.JacksonUtil;
import xn.core.util.time.TimeUtil;
import xn.core.util.unityreturn.BuildReturnMapUtil;

/**
 * @Description: 异常统一处理类
 * @author Zhangjc
 * @date 2016年4月19日 上午11:37:28
 */
@ControllerAdvice
public class BaseExceptionHandler {
    private static Logger logger = Logger.getLogger(BaseExceptionHandler.class);

    /**
     * @Description: 系统异常处理
     * @author Zhangjc
     * @param ex
     * @param request
     * @return
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    public void operateExp(Exception ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage(), ex);
        logger.info("************* ------ 系统异常处理（" + TimeUtil.getSysTimestamp() + "） ------- ***********");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String str = JacksonUtil.objectToJackson(BuildReturnMapUtil.getReturnMap(ex));
        response.getWriter().write(str);
    }

    /**
     * @Description: 自定义异常信息处理
     * @author Zhangjc
     * @param ex
     * @param response
     * @throws IOException
     * @throws Exception
     */
    @ExceptionHandler(BaseException.class)
    public void operateExpception(BaseException ex, HttpServletResponse response) throws IOException {
        logger.error(ex.getMessage(), ex);
        logger.info("************* ------ 自定义异常信息处理（" + TimeUtil.getSysTimestamp() + "） ------- ***********");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String str = JacksonUtil.objectToJackson(BuildReturnMapUtil.getReturnMap(ex));
        response.getWriter().write(str);
    }
}
