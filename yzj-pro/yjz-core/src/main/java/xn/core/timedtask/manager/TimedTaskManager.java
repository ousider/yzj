package xn.core.timedtask.manager;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Description: 定时任务管理类
 * @author Zhangjc
 * @date 2016年6月13日 下午5:03:18
 */
public class TimedTaskManager {

    // 日志
    private static Logger log = Logger.getLogger(TimedTaskManager.class);

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private static Scheduler scheduler;

    static {
        initSchedulerIfNotStarted();
    }

    /**
     * @Description: 初始化定时器
     * @author Zhangjc
     */
    private static final void initSchedulerIfNotStarted() {
        if (scheduler != null) {
            return;
        }
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        }
        catch (SchedulerException e) {
            log.error("TimedTaskManager缓存定时刷新调度器初始化失败! " + e);
        }
    }

    // /**
    // * @Description: 启动定时器
    // * @author Zhangjc
    // */
    // public static final void startSchedulerIfNotStarted() {
    // if (scheduler == null) {
    // return;
    // }
    // try {
    // scheduler.start();
    // }
    // catch (SchedulerException e) {
    // log.error("TimedTaskManager缓存定时刷新调度器初始化失败! " + e);
    // }
    // }

    /**
     * @Description: 添加定时任务
     * @author Zhangjc
     * @param className
     * @param cronExpr
     */
    public static void addJob(String className, String cronExpr, boolean isSimple) {

        try {
            Class<?> clazz = Class.forName(className);
            String simpleName = clazz.getSimpleName();

            // 获取定时器
            // scheduler = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = new JobDetail(simpleName + "_job", simpleName + "_job_group", clazz);

            if (isSimple) {
                // 定义调度触发规则，比如每1秒运行一次，共运行8次
                SimpleTrigger simpleTrigger = new SimpleTrigger(simpleName + "_trigger", simpleName + "_trigger_group");
                // 马上启动
                simpleTrigger.setStartTime(new Date());
                long repeatInterval = 0L;
                try{
                    repeatInterval = Long.valueOf(cronExpr);
                }catch(Exception e){
                    repeatInterval = 1000000L;
                }
                // 间隔时间
                simpleTrigger.setRepeatInterval(repeatInterval);
                // 运行次数
                simpleTrigger.setRepeatCount(-1);

                scheduler.scheduleJob(jobDetail, simpleTrigger);
            } else {
                // 触发器名,触发器组
                CronTrigger cronTrigger = new CronTrigger(simpleName + "_trigger", simpleName + "_trigger_group");

                // 触发器时间设定
                cronTrigger.setCronExpression(cronExpr);
                // trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
                scheduler.scheduleJob(jobDetail, cronTrigger);
            }

            // if (!scheduler.isShutdown()) {
            // scheduler.start();
            // }
        }
        catch (ClassNotFoundException e) {
            log.error("定时任务初始化失败 " + className, e);
        }
        catch (SchedulerException e) {
            log.error("定时任务初始化失败 " + className, e);
        }
        catch (ParseException e) {
            log.error("定时任务初始化失败 " + className, e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间
     * @author Zhangjc
     * @param className
     * @param cronExpr
     */
    public static void editJob(String className, String cronExpr) {

        try {
            Class<?> clazz = Class.forName(className);
            String simpleName = clazz.getSimpleName();
            Trigger trigger = scheduler.getTrigger(simpleName + "_trigger", simpleName + "_trigger_group");
            if (trigger != null) {
                CronTrigger ct = (CronTrigger) trigger;
                ct.setCronExpression(cronExpr);
                // 重新启动定时任务
                scheduler.resumeTrigger(simpleName + "_trigger", simpleName + "_trigger_group");
            }
        }
        catch (ClassNotFoundException e) {
            log.error("修改任务的触发时间失败" + className, e);
        }
        catch (SchedulerException e) {
            log.error("修改任务的触发时间失败" + className, e);
        }
        catch (ParseException e) {
            log.error("修改任务的触发时间失败" + className, e);
        }
    }

    /**
     * @Description: 移除一个任务
     * @author Zhangjc
     * @param className
     * @param cronExpr
     */
    public static void deleteJob(String className, String cronExpr) {

        try {
            Class<?> clazz = Class.forName(className);
            String simpleName = clazz.getSimpleName();
            // 停止触发器
            scheduler.pauseTrigger(simpleName + "_trigger", simpleName + "_trigger_group");
            // 移除触发器
            scheduler.unscheduleJob(simpleName + "_trigger", simpleName + "_trigger_group");
            // 删除任务
            scheduler.deleteJob(simpleName + "_job", simpleName + "_job_group");
        }
        catch (ClassNotFoundException e) {
            log.error("修改任务的触发时间失败" + className, e);
        }
        catch (SchedulerException e) {
            log.error("修改任务的触发时间失败" + className, e);
        }
    }

}

