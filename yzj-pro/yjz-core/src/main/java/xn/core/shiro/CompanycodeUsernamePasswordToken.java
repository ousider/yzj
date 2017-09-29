package xn.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Description:
 * @author zhangjs
 * @date 2016年7月18日 下午2:36:55
 */
public class CompanycodeUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	
	private MyPrincipal myPrincipal;
	
	public CompanycodeUsernamePasswordToken(String companycode,String username,String password){
		super(username,password);
		myPrincipal=new MyPrincipal(companycode,username);
		
	}

	@Override
    public MyPrincipal getPrincipal() {
		return myPrincipal;
	}
}
