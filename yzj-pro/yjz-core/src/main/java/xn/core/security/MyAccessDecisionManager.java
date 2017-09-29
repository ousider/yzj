package xn.core.security;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configattribute)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (authentication == null) {
			return;
		}
		// 所请求的资源拥有的权限(一个资源对多个权限)
		for (ConfigAttribute configAttribute : configattribute) {
			// 访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needPermission.equals(ga.getAuthority())) {
					return;
				}

			}
		}
		throw new AccessDeniedException("没有权限访问!");

	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}