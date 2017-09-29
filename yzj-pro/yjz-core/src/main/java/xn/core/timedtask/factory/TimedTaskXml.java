package xn.core.timedtask.factory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import xn.core.cache.cachemanager.CacheFactory;
import xn.core.util.data.StringUtil;
import xn.core.util.xml.XML;
import xn.core.util.xml.XMLElement;

/**
 * @Description: 定时任务读取XML
 * @author Zhangjc
 * @date 2016年6月13日 上午11:18:05
 */
public class TimedTaskXml {

    // 记录日志
    private static Logger log = Logger.getLogger(CacheFactory.class);
    
    private static final String TIMED_TASK_FILDNAME = "timedtask.xml";

    private static TimedTaskXml timedTaskXml = new TimedTaskXml();

    /**
     * @Description: 实现单列
     * @author Zhangjc
     * @return
     */
    public static TimedTaskXml getInstance() {
        return timedTaskXml;
    }

    private TimedTaskXml() {
    }

    /**
     * @Description: 需要运行的定时任务
     * @author Zhangjc
     * @return
     */
    public List<TimedTaskItem> getTimedTaskItems() {

        log.info("开始执行TimedTaskItem的getTimedTaskItems....");
        List<TimedTaskItem> list = new ArrayList<TimedTaskItem>();
        String url = TimedTaskXml.class.getResource("/").getFile();

        try {
            XML xml = XML.getInstance(new File(url + "/" + TIMED_TASK_FILDNAME));
            XMLElement root = xml.getRootElement();

            for (XMLElement folder : root.getChildren()) {

                String className = folder.getAttributeValue("className");
                String cronExpr = folder.getAttributeValue("cronExpr");
                String init = folder.getAttributeValue("init");
                String isSimple = folder.getAttributeValue("isSimple");
                TimedTaskItem item = new TimedTaskItem(className, cronExpr, init, isSimple);

                list.add(item);
            }
        }
        catch (Exception e) {
            log.error("读取timedtask.xml发生错误");
        }
        return list;
    }

    public class TimedTaskItem {

        // 定时任务名称
        public String className;

        // 定时执行间隔
        public String cronExpr;

        // 是否执行该定时任务
        public boolean isInitial;

        // true:SimpleTrigger false:CronTrigger
        public boolean isSimple = false;

        public TimedTaskItem(String className, String cronExpr, String init, String isSimple) {
            if (null == className) {
                throw new IllegalArgumentException("定时任务配置错误：className不可为空!");
            }

            if ((!"true".equals(init)) && (!"false".equals(init))) {
                throw new IllegalArgumentException("定时任务配置错误：init参数只能为true或false: " + className);
            }
            this.className = className;
            this.cronExpr = cronExpr;
            this.isInitial = Boolean.parseBoolean(init);
            if (StringUtil.isNonBlank(isSimple)) {
                this.isSimple = Boolean.parseBoolean(isSimple);
            }
        }
    }
}
