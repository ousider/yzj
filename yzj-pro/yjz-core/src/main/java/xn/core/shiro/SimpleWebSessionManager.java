package xn.core.shiro;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

public class SimpleWebSessionManager extends DefaultWebSessionManager {
 
    private static Logger log = Logger.getLogger(SimpleWebSessionManager.class);
 
    // private CacheManager cacheManager;

    public SimpleWebSessionManager() {
        super();
    }
 
    @Override
    protected Session retrieveSession(SessionKey sessionKey) {
        Serializable sessionId = this.getSessionId(sessionKey);
        if (sessionId == null) {
            log.debug("Unable to resolve session ID from SessionKey [{}]. Returning null to indicate a session could not be found." + sessionKey);
            return null;
        }

        Session s = null;
        try {
            s = this.retrieveSessionFromDataSource(sessionId);
        }
        catch (UnknownSessionException e) {
            // if (cacheManager != null) {
            // Cache<Serializable, ?> cache = cacheManager.getCache("JSESSIONID");
            // if (cache != null && cache.get(sessionId) != null) {
            // cache.remove(sessionId);
            // }
            // }
            String msg = "Could not find session with ID [" + sessionId + "]";
            log.debug(msg);
            return null;
        }
        if (s == null) {
            String msg = "Could not find session with ID [" + sessionId + "]";
            log.debug(msg);
            return null;
        } else {
            return s;
        }
    }

    // public void validateSessions() {
    // if (log.isInfoEnabled())
    // log.info("Validating all active sessions...");
    // int invalidCount = 0;
    // Collection<?> activeSessions = getActiveSessions();
    // if (activeSessions != null && !activeSessions.isEmpty()) {
    // for (Iterator<?> i$ = activeSessions.iterator(); i$.hasNext();) {
    // Session session = (Session) i$.next();
    // try {
    // SessionKey key = new DefaultSessionKey(session.getId());
    // validate(session, key);
    // }
    // catch (InvalidSessionException e) {
    // if (cacheManager != null) {
    // SimpleSession s = (SimpleSession) session;
    // if (s.getAttribute("JSESSIONID") != null)
    // cacheManager.getCache(null).remove(s.getAttribute("JSESSIONID"));
    // }
    // if (log.isDebugEnabled()) {
    // boolean expired = e instanceof ExpiredSessionException;
    // String msg = (new StringBuilder()).append("Invalidated session with id [").append(session.getId()).append("]").append(expired
    // ? " (expired)" : " (stopped)").toString();
    // log.debug(msg);
    // }
    // invalidCount++;
    // }
    // }
    // }
    // if (log.isInfoEnabled()) {
    // String msg = "Finished session validation.";
    // if (invalidCount > 0)
    // msg = (new StringBuilder()).append(msg).append(" [").append(invalidCount).append("] sessions were stopped.").toString();
    // else
    // msg = (new StringBuilder()).append(msg).append(" No sessions were stopped.").toString();
    // log.info(msg);
    // }
    // }

    // public void setCacheManager(CacheManager cacheManager) {
    // this.cacheManager = cacheManager;
    // }
 
}
