package xn.core.security;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import xn.core.model.portal.CoreUserModel;
import xn.core.service.portal.ISecurityLoginService;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	//用户名
	public static final String USERNAME = "username";
	//密码
    public static final String PWD = "password";
	//公司编码
	public static final String COMPANYCODE = "companycode";
	//用户对象
    public static final CoreUserModel USER = new CoreUserModel();
	//MD5加密
	public static final Md5PasswordEncoder MD5 = new Md5PasswordEncoder();

	@Autowired
	private ISecurityLoginService iSecurityLogin;

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("请填写登录信息！");
		}

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String companycode = obtainCompanyCode(request);
        boolean isUseCookie = obtainIsUseCookie(request);

        try {
            request.setCharacterEncoding("utf-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new AuthenticationServiceException("编码设置错误！");
        }

        // 首先判断用户是否选择了记住登录状态
        if (isUseCookie) {
            Cookie companycodeCookie = new Cookie("companycode", companycode);
            companycodeCookie.setSecure(true);
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setSecure(true);
            Cookie passwordCookie = new Cookie("password", password);
            passwordCookie.setSecure(true);
            usernameCookie.setMaxAge(864000);
            // 设置最大生存期限为10天
            passwordCookie.setMaxAge(864000);
            // 设置最大生存期限为10天
            companycodeCookie.setMaxAge(864000);
            response.addCookie(usernameCookie);
            response.addCookie(passwordCookie);
            response.addCookie(companycodeCookie);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("username") || c.getName().equals("password") || c.getName().equals("companycode")) {
                        // 设置Cookie失效
                        c.setMaxAge(0);
                        // 重新保存。
                        response.addCookie(c);
                    }
                }
            }
        }

		username = username.trim();		
		companycode = companycode.trim();
		
		USER.setCompanyCode(companycode);		
		USER.setUserName(username);
		
		MD5.setEncodeHashAsBase64(false);
		
        CoreUserModel user = this.iSecurityLogin.userLogin(USER);
		
		if ((user == null) || !MD5.isPasswordValid(user.getPassword(), password, null)) {	
			throw new AuthenticationServiceException("用户名或密码错误！");
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username+","+companycode, password);
				
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
        Object obj = request.getParameter(PWD);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainCompanyCode(HttpServletRequest request) {
		Object obj = request.getParameter(COMPANYCODE);
		return null == obj ? "" : obj.toString();
	}

    protected boolean obtainIsUseCookie(HttpServletRequest request) {

        Object obj = request.getParameter("isUseCookie");

        boolean flag = false;

        if (obj != null) {
            String str = obj.toString();
            if (str.equalsIgnoreCase("on")) {
                flag = true;
            }
        }
        return flag;
    }

}
