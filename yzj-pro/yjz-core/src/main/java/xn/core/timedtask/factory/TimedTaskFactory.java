package xn.core.timedtask.factory;

import java.util.List;

import org.apache.log4j.Logger;

import xn.core.timedtask.manager.TimedTaskManager;

/**
 * @Description: 定时任务工厂类
 * @author Zhangjc
 * @date 2016年6月13日 上午11:18:05
 */
public class TimedTaskFactory {

    private TimedTaskFactory() {

    }

    // 日志
    private static Logger log = Logger.getLogger(TimedTaskFactory.class);

    private static List<TimedTaskXml.TimedTaskItem> timedTaskItems;


    static {
        TimedTaskXml timedTaskXml = TimedTaskXml.getInstance();
        timedTaskItems = timedTaskXml.getTimedTaskItems();
    }

    /**
     * @Description: 初始化方法
     * @author Zhangjc
     */
    public static void init() {
        log.info("初始化TimedTaskFactory.........");
        synchronized (TimedTaskFactory.class) {
            if (timedTaskItems != null && !timedTaskItems.isEmpty()) {
                for (TimedTaskXml.TimedTaskItem item : timedTaskItems) {
                    // 需要初始化的定时任务才去执行
                    if (item.isInitial) {
                        TimedTaskManager.addJob(item.className, item.cronExpr, item.isSimple);
                    }
                }
                // TimedTaskManager.startSchedulerIfNotStarted();
            }
        }
    }

}
