package xn.pigfarm.util.constants;

/**
 * @Description:微信常量
 * @author Cress
 * @date 2017年7月13日 下午1:57:13
 */
public class WechatConstants {
    private WechatConstants() {

    }

    // 发送消息地址
    public static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    // 应用ID测试30，正式29
    public static final String AGENT_ID_TEST = "30";

    public static final String AGENT_ID = "29";

    // 消息推送类型-生产日报
    public static final String MESSAGE_TYPE_PRODUCTION_DAILY = "PRODUCTION_DAILY";

    // 消息推送类型-生产数据汇总数据分析-大生物安全、种群规划、销售分析填写
    public static final String MESSAGE_TYPE_PRODUCTION_COLLECTION_ANA_1 = "PRODUCTION_COLLECTION_ANA_1";

    // 消息推送类型-生产数据汇总数据分析-生产数据分析填写
    public static final String MESSAGE_TYPE_PRODUCTION_COLLECTION_ANA_2 = "PRODUCTION_COLLECTION_ANA_2";

    // 消息推送类型-生产数据汇总数据反馈
    public static final String MESSAGE_TYPE_PRODUCTION_COLLECTION_BACK = "PRODUCTION_COLLECTION_BACK";

    // 消息推送类型-生产数据汇总数据反馈到集团
    public static final String MESSAGE_TYPE_PRODUCTION_COLLECTION_TO_GROUP = "PRODUCTION_COLLECTION_TO_GROUP";

    // 周月报推送猪场ID
    public static final long REPORT_FARM_ID = 3;

    // 周月报推送猪场名称
    public static final String REPORT_FARM_NAME = "上海新农饲料股份有限公司";

    // 周月报推送报表查看人员名称
    public static final String REPORT_EMPLOYEE_NAME = "消息推送入口查看";

    // 猪舍每日情况清棚
    public static final String HOUSE_STATUS = "4";

    // 微信猪群健康状况
    public static final String WECHAT_SWINERY_HEALTH = "WECHAT_SWINERY_HEALTH";

    // 微信猪舍状态
    public static final String WECHAT_HOUSE_STATUS = "WECHAT_HOUSE_STATUS";

    // 微信猪群健康度为腹泻
    public static final String SWINERY_HEALTH_DIARRHEA = "2";

    // 微信猪群健康度为喘气
    public static final String SWINERY_HEALTH_GASP = "3";
}
