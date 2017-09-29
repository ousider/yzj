package xn.core.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description: 获取spring容器中的bean
 * @author Zhangjc
 * @date 2016年4月15日 下午1:59:53
 */
public class SpringContextRegister implements ApplicationContextAware {

    private static Logger log = Logger.getLogger(SpringContextRegister.class);

    /**
     * @Description: ApplicationContextAware接口的回调方法，设置上下文环境
     * @author Zhangjc
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtil.setApplicationContext(applicationContext);
        log.debug("ApplicationContext registed");
        // SpringContextUtil.applicationContext = applicationContext;
    }

}
