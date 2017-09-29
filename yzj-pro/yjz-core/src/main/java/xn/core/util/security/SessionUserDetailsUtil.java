package xn.core.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import xn.core.security.MyUserDetails;


 /**
 * @Description: 登陆用户信息工具类
 * @author lz
 * @date 2016年4月25日 下午2:33:53
 */
public class SessionUserDetailsUtil {

	/**
	 * 得到当前session中的用户，如果没有返回null
	 * 
	 * @return UserDetails
	 */
	public static MyUserDetails getUserDetails() {
		MyUserDetails userDetails = null;
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication ac = sc.getAuthentication();
		if (ac != null) {
			userDetails = (MyUserDetails) ac.getPrincipal();
		}
		return userDetails;
	}

	/**
	 * 得到当前登录用户，如果没有返回null
	 * 
	 * @return loginId
	 */
	public static String getLoginUserName() {
		String loginId = null;
		UserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			loginId = userDetails.getUsername();
		}
		return loginId;
	}

	/**
	 * 判断用户是否登陆
	 *
	 */
	public static boolean isLogined() {
		boolean flag = false;
		if(getLoginUserName() != null) flag = true;
		return flag;
	}

}
