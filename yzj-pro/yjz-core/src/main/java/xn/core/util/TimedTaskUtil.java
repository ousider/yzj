package xn.core.util;

import java.util.HashMap;
import java.util.Map;

import xn.core.service.IPrcTimedExecute;
import xn.core.util.data.StringUtil;

/**
 * @Description: 定时任务工具类
 * @author Zhangjc
 * @date 2016年6月15日 上午8:47:20
 */
public class TimedTaskUtil {

    /**
     * @Description: 将入参的Map转化成String存到数据库
     * @author Zhangjc
     * @param map
     * @return
     */
    public static String parseToString(Map<String, Object> map) {

        StringBuilder str = new StringBuilder("");
        if (map != null && !map.isEmpty()) {
            for (String key : map.keySet()) {
                str.append(key + ":" + map.get(key) + ",");
            }
        }
        return str.toString();
    }

    /**
     * @Description: 将数据库的工单存的入参String转为Map
     * @author Zhangjc
     * @param inParam
     * @return
     */
    public static Map<String, Object> parseToMap(String inParam) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isNonBlank(inParam)) {
            String[] inParams = inParam.split(",");
            for (String str : inParams) {
                if (StringUtil.isNonBlank(str)) {
                    String[] value = str.split(":");
                    if (value.length == 1) {
                        map.put(null, value[0]);
                    } else {
                        map.put(value[0], value[1]);
                    }
                }
            }
        }
        return map;
    }

    /**
     * @Description: 插入存储过程的工单
     * @author Zhangjc
     * @param serviceName
     * @param orderNo
     * @param inParam
     * @return
     * @throws Exception
     */
    public static int insertPrcTimedTask(String serviceName, String orderNo, String orderName, Map<String, Object> inParam) throws Exception {

        IPrcTimedExecute prcTimedExecute=SpringContextUtil.getBean("PrcTimedExecute", IPrcTimedExecute.class);
        return prcTimedExecute.insertOrder(parseToString(inParam), orderNo, orderName, serviceName);
    }
}
