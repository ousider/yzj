package xn.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import xn.core.cache.controllersession.ServiceContext;
import xn.core.controller.BaseController;

/**
 * @Description: 拦截器 ，解析请求
 * @author Zhangjc
 * @date 2016年5月6日 上午10:41:55
 */
public class ControllerInterceptor implements HandlerInterceptor {

    private static Logger log = Logger.getLogger(BaseController.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("加载缓存......");
        ServiceContext.push(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        log.debug("清理缓存......");
        ServiceContext.pop();
    }

}
