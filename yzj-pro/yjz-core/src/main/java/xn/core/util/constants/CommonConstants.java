package xn.core.util.constants;

/**
 * @Description: 公司相关常量
 * @author zhangjs
 * @date 2016年8月16日 下午4:53:01
 */
public class CommonConstants {
    private CommonConstants() {
        
    }

    /* 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。 */
    // (Input)系统录入
    public static final String ORIGIN_FLAG_INPUT = "I";

    // (Out)外部接口导入
    public static final String ORIGIN_FLAG_OUT = "O";

    // (System)系统保留
    public static final String ORIGIN_FLAG_SYSTEM = "S";

    /* 数据来源应用的代码 */
    // 系统内部编码
    public static final String ORIGIN_APP_INSIDE = "XN1.0";

    // EXCEL导入
    public static final String ORIGIN_APP_EXCEL = "EXCEL";

    // [0]-未删除;[1]-逻辑删除
    public static final String DELETED_FLAG = "0";

    // 表示该对象实例的业务状态 ,通常用“1/2”表示其是否有效，其他的状态相对复杂。
    public static final String STATUS = "1";

    // 用户相关信息名称
    public static final String USER_DETAIL = "userDetail";

    // 事件名称
    public static final String EVENT_NAME = "eventName";

}
