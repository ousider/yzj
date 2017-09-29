package xn.core.cache.cachemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import xn.core.util.xml.XML;
import xn.core.util.xml.XMLElement;

public class CacheXml {
    private static Logger log = Logger.getLogger(CacheFactory.class);

    private static final String CACHE_FILENAME = "localcache.xml";

    private static CacheXml instance = new CacheXml();

    public static final CacheXml getInstance() {
        return instance;
    }

    public List<ReadOnlyCacheItem> getReadOnlyCacheItems() {

        log.info("开始执行CacheXml的getReadOnlyCacheItems....");

        List<ReadOnlyCacheItem> rtn = new ArrayList<ReadOnlyCacheItem>();

        String url = CacheXml.class.getResource("/").getFile();

        // 解析菜单XML文件
        try {
            XML xml = XML.getInstance(new File(url + "/" + CACHE_FILENAME));
            XMLElement root = xml.getRootElement();

            for (XMLElement folder : root.getChildren()) {
                String className = folder.getAttributeValue("className");
                String init = folder.getAttributeValue("init");
                String cronExpr = folder.getAttributeValue("cronExpr");

                ReadOnlyCacheItem item = new ReadOnlyCacheItem(className, init, cronExpr);
                rtn.add(item);
            }
        }
        catch (Exception e) {
            log.error("读取localcache.xml发生错误");
        }

        return rtn;
    }

    public class ReadOnlyCacheItem {

        public String className;

        public boolean isInitial;

        public String cronExpr;

        public ReadOnlyCacheItem(String className, String init, String cronExpr) {
            if (null == className) {
                throw new IllegalArgumentException("只读缓存配置错误：className不可为空!");
            }

            if ((!"true".equals(init)) && (!"false".equals(init))) {
                throw new IllegalArgumentException("只读缓存配置错误：init参数只能为true或false: " + className);
            }


            // cronExpr 配置为：秒 分 小时 日 月 周
            this.cronExpr = cronExpr;
            // this.cronExpr = null;
            // String[] items;
            // if (!"".equals(cronExpr)) {
            // items = cronExpr.split(" ");
                // if (5 != items.length) {
                // throw new IllegalArgumentException("读写缓存配置错误：cronExpr 只可配置：分 小时 日 月 周");
                // }
                // this.cronExpr = ("0 " + cronExpr);
            // }

            this.className = className;
            isInitial = Boolean.parseBoolean(init);
        }
    }
}
