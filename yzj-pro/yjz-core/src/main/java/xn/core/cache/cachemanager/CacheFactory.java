package xn.core.cache.cachemanager;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class CacheFactory {

    private static Logger log = Logger.getLogger(CacheFactory.class);

    private static final Map<Class, IReadOnlyCache> ROCACHES = new HashMap<Class, IReadOnlyCache>();

    private static List<CacheXml.ReadOnlyCacheItem> readonlyCacheItems;

    private static final Set<String> ROCACHE_CLAZZNAME = new HashSet<String>();

    private static final Set<String> ROCACHE_NEEDINIT = new HashSet<String>();

    // TODO 定时任务定时刷新
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static Scheduler scheduler = null;

    /**
     * 静态加载初始化方法
     */
    static {

        CacheXml cacheXml = CacheXml.getInstance();
        readonlyCacheItems = cacheXml.getReadOnlyCacheItems();
        initReadOnlyCaches(readonlyCacheItems);
    }

    /**
     * @Description: 获取只读缓存
     * @author Zhangjc
     * @param clazz
     * @return
     * @throws Exception
     */
    public static IReadOnlyCache getReadOnlyCache(Class clazz) throws Exception {

        if (!ROCACHE_CLAZZNAME.contains(clazz.getName())) {
            log.error("缓存类在配置文件中未定义!" + clazz.getName());
            return null;
        }

        IReadOnlyCache cache = (IReadOnlyCache) ROCACHES.get(clazz);

        if (cache == null) {

            synchronized (clazz) {

                long start = System.currentTimeMillis();
                cache = (IReadOnlyCache) clazz.newInstance();
                cache.setClassName(clazz.getName());
                cache.refresh();
                ROCACHES.put(clazz, cache);

                log.info("ReadOnlyCache:" + clazz.getName() + "，加载数据:" + cache.size() + "条，耗时:" + (System.currentTimeMillis() - start) + "毫秒");
            }
        }

        return cache;
    }

    private static void initReadOnlyCaches(List<CacheXml.ReadOnlyCacheItem> items) {

        for (CacheXml.ReadOnlyCacheItem item : items) {
            ROCACHE_CLAZZNAME.add(item.className);
            if (item.isInitial) {
                ROCACHE_NEEDINIT.add(item.className);
            }

            // 定时任务刷新缓存
            try {
                Class clazz = Class.forName(item.className);

                if (item.cronExpr != null) {

                }

                if (null != item.cronExpr) {
                    startSchedulerIfNotStarted();

                    JobDetail jobDetail = new JobDetail("refresh_" + item.className + "_job", "CacheRefreshGroup", CacheAutoRefreshJob.class);
                    jobDetail.getJobDataMap().put("CACHE_TYPE", "READONLY_CACHE");
                    jobDetail.getJobDataMap().put("CACHE_NAME", clazz);
                    CronTrigger trigger = new CronTrigger("refresh_" + item.className + "_trigger", "refresh_" + item.className + "_group");
                    try {
                        trigger.setCronExpression(item.cronExpr);
                        scheduler.scheduleJob(jobDetail, trigger);
                    }
                    catch (ParseException e) {
                        log.error(e);
                    }
                    catch (SchedulerException e) {
                        log.error(e);
                    }
                }
            }
            catch (Exception e) {
                log.error("ReadOnlyCache配置加载出错! " + item.className, e);
            }
        }
    }

    /**
     * @Description: 初始化缓存工厂类
     * @author zhangjs5
     */
    public static void init() {

        synchronized (CacheFactory.class) {
            try {
                for (String clazzName : ROCACHE_NEEDINIT) {
                    long start = System.currentTimeMillis();

                    Class<?> clazz = Class.forName(clazzName);
                    IReadOnlyCache cache = (IReadOnlyCache) clazz.newInstance();
                    cache.setClassName(clazzName);
                    cache.refresh();
                    ROCACHES.put(clazz, cache);

                    log.info("ReadOnlyCache:" + clazz.getName() + "，加载数据:" + cache.size() + "条，耗时:" + (System.currentTimeMillis() - start) + "毫秒");
                }
            }
            catch (Exception e) {
                log.error("本地只读缓存初始化时发生错误!", e);
            }
        }
    }

    /**
     * @Description: 缓存工厂类销毁方法
     * @author Zhangjc
     */
    public static void destroyed() {
        ROCACHES.clear();
        readonlyCacheItems.clear();
        ROCACHE_CLAZZNAME.clear();
        ROCACHE_NEEDINIT.clear();
    }

    /**
     * @Description: 定时任务定时刷新 获取调度者
     * @author Zhangjc
     */
    private static final void startSchedulerIfNotStarted() {
        if (null != scheduler) {
            return;
        }
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        }
        catch (SchedulerException e) {
            log.error("缓存定时刷新调度器初始化失败! " + e);
        }
    }
}
