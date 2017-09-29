package xn.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
//        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // if (savedRequest == null) {
        // System.out.println("savedRequest is null ");
        // // 用户判断是否要使用上次通过session里缓存的回调URL地址
        // int flag = 0;
        // // 通过提交登录请求传递需要回调的URL callCustomRediretUrl
        // if (request.getSession().getAttribute("callCustomRediretUrl") != null && !"".equals(request.getSession().getAttribute(
        // "callCustomRediretUrl"))) {
        // String url = String.valueOf(request.getSession().getAttribute("callCustomRediretUrl"));
        // // 若session 存在则需要使用自定义回调的URL 而不是缓存的URL
        // super.setDefaultTargetUrl(url);
        // super.setAlwaysUseDefaultTargetUrl(true);
        // flag = 1;
        // request.getSession().setAttribute("callCustomRediretUrl", "");
        // }
        // // 重设置默认URL为主页地址
        // if (flag == 0) {
        // super.setDefaultTargetUrl("/jsp/Main.jsp");
        // }
        // super.onAuthenticationSuccess(request, response, authentication);
        //
        // return;
        // }
        // targetUrlParameter 是否存在
        // String targetUrlParameter = getTargetUrlParameter();
        // if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
        // requestCache.removeRequest(request, response);
        // super.setAlwaysUseDefaultTargetUrl(false);
        // super.setDefaultTargetUrl("/");
        // super.onAuthenticationSuccess(request, response, authentication);
        // return;
        // }

        // Use the DefaultSavedRequest URL
        // String targetUrl = "/jsp/Main.jsp";
        // logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        // if (targetUrl != null && "".equals(targetUrl)) {
        // targetUrl = "/jsp/Main.jsp";
        // }
        /* 只要 */
        // 清除属性
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, getDefaultTargetUrl());
    }

}  