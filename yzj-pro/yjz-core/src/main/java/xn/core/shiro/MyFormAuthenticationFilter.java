package xn.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;


public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	

	//公司编码
	public static final String COMPANYCODE = "companycode";
	
	private String companycodeParam = COMPANYCODE;
	

	/**
	 * 登录验证
	 * @throws Exception 
	 */
	@Override
	protected boolean executeLogin(ServletRequest request,ServletResponse response) throws Exception{
		CompanycodeUsernamePasswordToken token = createToken(request,response);
		
		try{
			Subject subject = getSubject(request,response);
			subject.login(token);//正常认证
			return onLoginSuccess(token, subject, request, response);
		}catch(AuthenticationException e){
			return onLoginFailure(token, e, request, response);
		}
	}
		
	@Override
	protected CompanycodeUsernamePasswordToken createToken(ServletRequest request,ServletResponse response){
		String username = getUsername(request);
		String password = getPassword(request);
		String companycode = getCompanycode(request);
		
		return new CompanycodeUsernamePasswordToken(companycode,username,password);
	}
	
	@Override
	protected void setFailureAttribute(ServletRequest request,AuthenticationException ae){
		request.setAttribute(getFailureKeyAttribute(), ae);
	}

	public String getCompanycodeParam() {
		return companycodeParam;
	}

	public void setCompanycodeParam(String companycodeParam) {
		this.companycodeParam = companycodeParam;
	}
	
	protected String getCompanycode(ServletRequest request){
		return WebUtils.getCleanParam(request, getCompanycodeParam());
	}
	
	public MyFormAuthenticationFilter(){
		
	}
	
}
