package xn.pigfarm.util;

import java.util.Map;

import xn.core.util.TimedTaskUtil;
import xn.pigfarm.util.enums.PrcTimedTaskEnum;

/**
 * @Description: 工单工具类
 * @author Zhangjc
 * @date 2016年6月16日 下午12:52:31
 */
public class TaskUtil {

    public static int insertPrcTimedTask(PrcTimedTaskEnum taskEnum) throws Exception {

        return insertPrcTimedTask(taskEnum, null);
    }

    public static int insertPrcTimedTask(PrcTimedTaskEnum taskEnum, Map<String, Object> inParam) throws Exception {

        return TimedTaskUtil.insertPrcTimedTask(taskEnum.getServiceName(), taskEnum.getOrderCode(), taskEnum.getOrderName(), inParam);
    }
}
