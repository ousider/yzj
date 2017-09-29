package xn.core.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * @description 
 */
public class MySessionRegistryImpl extends SessionRegistryImpl {

	public synchronized void registerNewSession(String sessionId, Object principal) {
		if(!(principal instanceof MyPrincipal)){
			principal = new MyPrincipal(SecurityContextHolder.getContext().getAuthentication());
		}
		super.registerNewSession(sessionId, principal);
	}
	
	
}
