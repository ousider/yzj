package xn.core.cache.cachemanager;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CacheAutoRefreshJob implements Job {
    private static Logger log = Logger.getLogger(CacheAutoRefreshJob.class);

    public static final String CACHE_NAME = "CACHE_NAME";

    public static final String CACHE_TYPE = "CACHE_TYPE";

    public static final String READONLY_CACHE = "READONLY_CACHE";

    public static final String READWRITE_CACHE = "READWRITE_CACHE";

    public void execute(JobExecutionContext ctx) throws JobExecutionException {

        log.info("执行缓存刷新定时任务.....");
        JobDataMap map = ctx.getJobDetail().getJobDataMap();
        String cacheType = map.getString("CACHE_TYPE");

        if ("READONLY_CACHE".equals(cacheType)) {
            Class clazz = (Class) map.get("CACHE_NAME");
            try {
                IReadOnlyCache cache = CacheFactory.getReadOnlyCache(clazz);
                if (cache != null) {
                    int oldSize = cache.size();
                    cache.refresh();
                    int newSize = cache.size();
                    log.info("本地只读缓存自动刷新成功! " + clazz.getName() + ",刷新前:" + oldSize + "条数据，刷新后:" + newSize + "条数据。");
                }
            }
            catch (Exception e) {
                log.error("本地只读缓存自动刷新失败! " + clazz.getName() + e);
            }
        }
    }
}
