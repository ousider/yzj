package xn.pigfarm.util.enums;

/**
 * @Description: 储存过程的定时任务的枚举
 * @author Zhangjc
 * @date 2016年6月16日 下午12:49:07
 */
public enum PrcTimedTaskEnum {

    /**
     * orderName 工单名称
     * orderCode 工单编码
     * serviceName 存放要处理的service的beanId
     */
    PIG_MOVE_IN("猪只入场的定时任务", "1000", "PigMoveInTimedExecute");

    private final String orderName;

    private final String orderCode;

    private final String serviceName;

    /**
     * @param orderName 工单名称
     * @param orderCode 工单编码
     * @param serviceName 存放要处理的service的beanId
     */
    PrcTimedTaskEnum(String orderName, String orderCode, String serviceName) {
        this.orderName = orderName;
        this.orderCode = orderCode;
        this.serviceName = serviceName;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getServiceName() {
        return serviceName;
    }

}
