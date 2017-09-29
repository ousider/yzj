package xn.core.data.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import xn.core.cache.cachemanager.CacheFactory;
import xn.core.data.SysCfg;
import xn.core.datasource.DynamicDataSource;
import xn.core.timedtask.factory.TimedTaskFactory;

public class PrepareContextListener implements ServletContextListener {
    private static Logger log = Logger.getLogger(PrepareContextListener.class);

    public PrepareContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // 加载缓存
        log.info("本地缓存初始化失败...");
        System.setProperty("org.terracotta.quartz.skipUpdateCheck", "true");
        try {
            CacheFactory.init();
        }
        catch (Exception e) {
            log.error("本地缓存初始化失败", e);
        }

        // 加载系统配置信息
        log.info("加载系统配置信息...");
        try {
            SysCfg.getInstance(event);
        }
        catch (Exception e) {
            log.error("加载系统配置信息...", e);
        }

        // 加载定时任务
        log.info("加载定时任务...");
        try {
            TimedTaskFactory.init();
        }
        catch (Exception e) {
            log.error("本地缓存初始化失败", e);
        }

        // 加载定时任务
        log.info("加载HANA DB PROPERTIES开始...");
        try {
            DynamicDataSource.init(event);
            log.info("加载HANA DB PROPERTIES成功...");
        }
        catch (Exception e) {
            log.error("加载HANA DB PROPERTIES失败", e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        // 销毁缓存
        // log.info("销毁本地缓存...");
        // CacheFactory.destroyed();
    }

}
