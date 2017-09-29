package xn.core.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import xn.core.model.portal.CoreUserModel;
import xn.core.service.portal.ISecurityLoginService;
import xn.core.util.constants.CommonConstants;

public class CustomerRealm extends AuthorizingRealm{

    // 用户对象
    public static final CoreUserModel USER = new CoreUserModel();
	
    @Autowired
	private ISecurityLoginService iSecurityLogin;
	
	@Override
    // 授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
    // 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		CompanycodeUsernamePasswordToken cuptoken = (CompanycodeUsernamePasswordToken)token;
        MyPrincipal principal = cuptoken.getPrincipal();
        if (cuptoken.getPassword() == null) {
            throw new IncorrectCredentialsException();
        }

		CoreUserModel cuser = new CoreUserModel();
		
		cuser.setCompanyCode(principal.getCompanyCode());
		cuser.setUserName(principal.getUserName());
		
        // 调用认证方法（查用户信息）
		CoreUserModel userModel = iSecurityLogin.userLogin(cuser);
		if(userModel != null){
            UserDetail detail = new UserDetail(userModel);
            // 认证成功，将用户信息存入session
            SecurityUtils.getSubject().getSession().setAttribute(CommonConstants.USER_DETAIL, detail);

            // 并返还认证信息给shiro，shiro帮我们做密码的校验
            return new SimpleAuthenticationInfo(cuptoken.getPrincipal(), userModel.getPassword(), userModel.getNickName());

		}else{
            throw new AuthenticationException();
		}
	}
}
