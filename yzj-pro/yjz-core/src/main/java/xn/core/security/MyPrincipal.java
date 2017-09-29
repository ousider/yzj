package xn.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

/**
 * @description 保存用户信息(主要是IP地址)
 */
public class MyPrincipal {

	private String username;  //用户名
	private String ip;   //ip地址
	
    public MyPrincipal(String username, String ip) {
        Assert.notNull(username,"username cannot be null (violation of interface contract)");
        Assert.notNull(ip,"username cannot be null (violation of interface contract)");
        this.username = username;
        this.ip = ip;
    }

    public MyPrincipal(Authentication authentication) {
        Assert.notNull(authentication,"authentication cannot be null (violation of interface contract)");

        String username = null;

        if (authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            username = (String) authentication.getPrincipal();
        }

        String ip = ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
        this.username = username;
        this.ip = ip;
    }

    public boolean equalsIp(MyPrincipal smartPrincipal) {
        return this.ip.equals(smartPrincipal.ip);
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj != null) {
	    	if(  obj.getClass() == MyPrincipal.class){
	    		return true;
	    	}
	        if (obj instanceof MyPrincipal) {
	        	MyPrincipal smartPrincipal = (MyPrincipal) obj;
	            return username.equals(smartPrincipal.username);
	        }
		}
        return false;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "SmartPrincipal:{username=" + username + ",ip=" + ip + "}";
    }
}
