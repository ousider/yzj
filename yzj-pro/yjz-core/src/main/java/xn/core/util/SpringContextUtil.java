package xn.core.util;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;

/**
 * @Description: 获取spring容器中的bean
 * @author Zhangjc
 * @date 2016年4月15日 下午1:59:53
 */
public class SpringContextUtil {

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    private static Logger log = Logger.getLogger(SpringContextUtil.class);

    public static void setApplicationContext(ApplicationContext applicationContext) {

        synchronized (SpringContextUtil.class) {
            log.debug("setApplicationContext, notifyAll");
            SpringContextUtil.applicationContext = applicationContext;
            SpringContextUtil.class.notifyAll();
        }
    }

    public static ApplicationContext getApplicationContext() {

        synchronized (SpringContextUtil.class) {
            while (applicationContext == null) {
                try {
                    log.debug("getApplicationContext, wait...");
                    SpringContextUtil.class.wait(600);
                    if (applicationContext == null) {
                        log.warn("Have been waiting for ApplicationContext to be set for 1 minute", new Exception());
                    }
                }
                catch (InterruptedException ex) {
                    log.error("getApplicationContext, wait interrupted");
                }
            }
            return applicationContext;
        }
    }

    public static Object getBean(String name) {

        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clzz) {

        return getApplicationContext().getBean(name, clzz);
    }

    public static <T> T getBean(Class<T> clzz) {
        return getApplicationContext().getBean(clzz);
    }

    /**
     * @Description: 获取对应的mapper对象
     * @author Zhangjc
     * @param clzz
     * @return
     */
    public static SqlSessionTemplate getSqlSession() {

        return getApplicationContext().getBean("sqlSession", SqlSessionTemplate.class);
    }

    /**
     * @Description: 获取对应的mapper对象
     * @author Zhangjc
     * @param clzz
     * @return
     */
    public static <T> T getMapper(Class<T> clzz) {

        SqlSessionTemplate sqlSession;
        sqlSession = getApplicationContext().getBean("sqlSession", SqlSessionTemplate.class);
        return sqlSession.getMapper(clzz);
    }
}
