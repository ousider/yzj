package xn.core.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.bitwalker.useragentutils.UserAgent;
import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;
import xn.core.model.portal.CoreUserModel;
import xn.core.model.system.ActionTrackModel;
import xn.core.service.IActionTrackService;
import xn.core.service.portal.ISecurityLoginService;
import xn.core.shiro.CompanycodeUsernamePasswordToken;
import xn.core.util.AddressUtil;
import xn.core.util.MD5Util;
import xn.core.util.time.TimeUtil;

@Component
@Controller
@RequestMapping("/login")
public class LoginCheckController extends BaseController {

    @Autowired
    private IActionTrackService actionTrackService;

    @Autowired
    private ISecurityLoginService iSecurityLogin;

    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static final String APPID = "wx8dc0eaa24e4aadb6";

    public static final String SECRET = "f26c49d2dda4a5aac06cce0863f80fd4";
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        // if (!request.getMethod().equals("POST")) {
        // request.setAttribute("message", "请填写登录信息");
        // throw new AuthenticationException("请填写登录信息！");
        // }
        try {
            request.setCharacterEncoding("utf-8");
        }
        catch (UnsupportedEncodingException e) {
            Thrower.throwException(CoreBusiException.LOGIN_CODE_ERROR);
        }

        Subject subject = SecurityUtils.getSubject();

        String companycode = getString("companycode");
        String username = getString("username");
        String password = getString("password");
        String isCheckPassword = getString("isCheckPassword") == null ? "Y" : getString("isCheckPassword");
        if (password == null) {
            Thrower.throwException(CoreBusiException.LOGIN_PASSWORD_ERROR);
        }
        if ("Y".equals(isCheckPassword)) {
            password = MD5Util.generateMD5code(password);
        }
        boolean isUseCookie = obtainIsUseCookie(request);

        CompanycodeUsernamePasswordToken token = new CompanycodeUsernamePasswordToken(companycode, username, password);
        // 是否记住登录状态（只记录公司名、用户名）
        token.setRememberMe(isUseCookie);
        try {
            // shiro核心 认证方法
            subject.login(token);
            if (!subject.isAuthenticated()) {
                token.clear();
            }

            ActionTrackModel actionTrackModel = getBean(ActionTrackModel.class);
            
            actionTrackModel.setScreenWidth(getString("width"));
            actionTrackModel.setScreenHeight(getString("height"));
            actionTrackModel.setLanguage(request.getLocale().getLanguage());
            actionTrackModel.setStartTime(TimeUtil.getSysTimestamp());
            actionTrackModel.setIp(AddressUtil.getIpAddr(request));
            actionTrackModel.setActobjId(getUserDetail().getUserId());
            actionTrackModel.setFarmId(getFarmId());
            String head = request.getHeader("User-Agent");
            try {
                UserAgent userAgent = new UserAgent(head);
                actionTrackModel.setClient(userAgent.getOperatingSystem().getName());
                actionTrackModel.setExplorer(userAgent.getBrowser().getName());
                actionTrackModel.setExplorerVer((userAgent.getBrowserVersion().getVersion()));
            }
            catch (Exception e) {
                log.error(e.getMessage());
            }

            // 从http://whois.pconline.com.cn取得IP所在的省市区信息
            String ip = AddressUtil.getIpAddr(request);
            actionTrackService.saveGuestInfro(ip, actionTrackModel);
            // response.sendRedirect(request.getContextPath() + "/jsp/Main.jsp");
        }
        catch (IncorrectCredentialsException e) {
            Thrower.throwException(CoreBusiException.LOGIN_PASSWORD_ERROR);
        }
        catch (AuthenticationException e) {
            Thrower.throwException(CoreBusiException.LOGIN_NAME_ERROR);
        }

        // 使用JSONP使用下面这个方法
        // response.setHeader("Access-Control-Allow-Origin", "*");
        // String callbackparam = getString("callbackparam");
        // return new JSONPObject(callbackparam, getReturnMap());

        return getReturnMap();
    }


    @RequestMapping("/checkOpenId")
    @ResponseBody
    public Map<String, Object> checkOpenId(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String openID = getString("OpenID");
        if (openID == null) {
            Thrower.throwException(CoreBusiException.OPENID_EXCEPTION);
        }
        String checkType = getString("checkType");
        Map<String, Object> returnMap = getReturnMap();
        List<Map<String, Object>> list = actionTrackService.checkOpenId(openID, checkType);
        if (list.size() < 1) {
            returnMap.put("loginResult", "N");
        } else {
            returnMap.put("loginResult", "Y");
            returnMap.put("companyCode", list.get(0).get("COMPANY_CODE"));
            returnMap.put("userName", list.get(0).get("USER_NAME"));
            returnMap.put("password", list.get(0).get("PASSWORD"));
        }
        return returnMap;
    }

    @RequestMapping("/bindAccount")
    @ResponseBody
    public Map<String, Object> bindAccount(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String openID = getString("openId");
        String bindType = getString("bindType");
        if (openID == null) {
            Thrower.throwException(CoreBusiException.OPENID_EXCEPTION);
        }
        String companycode = getString("companycode");
        String username = getString("username");
        String password = getString("password");
        password = MD5Util.generateMD5code(password);
        CoreUserModel cuser = new CoreUserModel();

        cuser.setCompanyCode(companycode);
        cuser.setUserName(username);
        // 调用认证方法（查用户信息）
        CoreUserModel userModel = iSecurityLogin.userLogin(cuser);
        if (userModel != null) {
            if (password.equals(userModel.getPassword())) {
                actionTrackService.updateOpenId(userModel.getRowId(), openID, bindType);
            } else {
                Thrower.throwException(CoreBusiException.LOGIN_PASSWORD_ERROR);
            }
        } else {
            Thrower.throwException(CoreBusiException.LOGIN_NAME_ERROR);
        }
        return getReturnMap();
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map<String, Object> login_check() {
        if (SecurityUtils.getSubject().getSession() != null) {
            SecurityUtils.getSubject().logout();
        }
        // return "login";
        return getReturnMap();
    }
    @RequestMapping("/editPassword")
    @ResponseBody
    public Map<String, Object> editPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oldPassword = MD5Util.generateMD5code(getString("oldPassword"));
        String newPassword = MD5Util.generateMD5code(getString("newPassword"));
        String surePassword = MD5Util.generateMD5code(getString("surePassword"));

        if (!getUserDetail().getPassword().equals(oldPassword)) {
            throw new Exception("旧密码不正确！");
        }
        if (!newPassword.equals(surePassword)) {
            throw new Exception("两次密码不一致！");
        }
        if (newPassword.equals(oldPassword)) {
            throw new Exception("新密码不能和旧密码一样！");
        }
        // 同步session，同步数据库
        getUserDetail().setPassword(newPassword);
        getUserDetail().setIsInitPw("N");
        actionTrackService.eidtPassword(newPassword);

        return getReturnMap();
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
