package xn.core.timedtask.prctimedtask;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.model.system.PrcOrderModel;
import xn.core.service.IPrcTimedExecute;
import xn.core.util.SpringContextUtil;
import xn.core.util.data.StringUtil;

/**
 * @Description: 存储过程的定时任务
 * @author Zhangjc
 * @date 2016年6月14日 上午9:39:54
 */
public class PrcTimedTask implements Job {

    private static Logger log = Logger.getLogger(PrcTimedTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {

        log.info("开始执行PrcTimedTask...........");
        // 取数据库工单
        List<PrcOrderModel> list = null;
        try {
            list = SpringContextUtil.getBean("PrcTimedExecute", IPrcTimedExecute.class).searchPreFirstPrcOrder();
        }
        catch (Exception e) {
            log.error("PrcTimedTask获取工单数据失败..........." + e);
        }
        // 无工单直接结束
        if (list == null || list.size() == 0) {
            return;
        }
        // 执行数据存储过程
        for (PrcOrderModel model : list) {
            Thread prcTimedTaskThread = new PrcTimedTaskThread(model);
            prcTimedTaskThread.start();
        }
    }

    /**
     * @Description: 工单多线程，一种工单一次一个线程
     * @author Zhangjc
     * @date 2016年6月16日 上午11:43:26
     */
    private class PrcTimedTaskThread extends Thread {
        private PrcOrderModel model;

        private PrcTimedTaskThread(PrcOrderModel model) {
            this.model = model;
        }

        public void run() {
            IPrcTimedExecute exeute = null;
            try {
                exeute = (IPrcTimedExecute) SpringContextUtil.getBean(model.getOperaClass());
                exeute.beforeExecute(model);
            }
            catch (Exception e) {
                log.error("PrcTimedTask,获取" + model.getOperaClass() + "或者beforeExecute出错：" + e);
                return;
            }
            try {
                exeute.setExecuteOrder(model);
                exeute.editPrcTimedTask(model);
            }
            catch (Exception e) {
                try {
                    String message = StringUtil.substring(e.getMessage(), 0, 150);
                    exeute.setErrorOrder(model, message);
                    log.error("PrcTimedTask" + model.getOperaClass() + "执行失败..........Exception." + e);
                }
                catch (Exception e1) {
                    log.error("PrcTimedTask" + model.getOperaClass() + "设置为错误工单失败." + e1);
                }
            }
        }
    }
}
