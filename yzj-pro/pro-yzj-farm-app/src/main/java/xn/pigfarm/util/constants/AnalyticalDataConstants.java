package xn.pigfarm.util.constants;

public class AnalyticalDataConstants {
    private AnalyticalDataConstants() {
        
    }

    // 新农客户分类
    public static final String ORIGIN_TYPE = "origin";

    // 新农 CompanyMark
    public static final String COMPANY_MARK_XNFEED = "1,2,3";
    
    // 新农 farmId
    public static final String FARM_ID_XNFEED = "3";

    // 平台 CompanyMark
    public static final String COMPANY_MARK_OTHER = "1,2";

    // 需要排除的FARM_ID
    public static final String EXCLUDE_FARM_ID = "1,2,3,469";

    // 新农
    public static final String ORIGIN_TYPE_XNFEED = "xnfeed";

    // 客户
    public static final String ORIGIN_TYPE_CUSTOMER = "customer";

    // 日期分类：年
    public static final String DATE_TYPE_YEAR = "year";
    
    // 日期分类：月
    public static final String DATE_TYPE_MONTH = "month";
    
    // 日期分类：周
    public static final String DATE_TYPE_WEEK = "week";
    
    // 日期分类：日
    public static final String DATE_TYPE_DAY = "day";

    // 传入的CompanyMark
    public static final String INPUT_COMPANY_MARK = "inputCompanyMark";

    // 最小日期
    public static final String MIN_DATE = "minDate";

    // 最大日期
    public static final String MAX_DATE = "maxDate";

    // PIG_TYPE:分娩
    public static final String PIG_TYPE_DELIVERY = "delivery";

    // PIG_TYPE:转保育
    public static final String PIG_TYPE_TO_CARE = "toCare";

    // PIG_TYPE:转育肥
    public static final String PIG_TYPE_TO_FAT = "toFat";

    // PIG_TYPE:配种后备
    public static final String PIG_TYPE_BREED_RESERVE = "breedReserve";

    // PIG_TYPE:配种生产
    public static final String PIG_TYPE_BREED_PRODUCT = "breedProduct";

    // PIG_TYPE:妊检返情
    public static final String PIG_TYPE_CHECK_BACK = "checkBack";

    // PIG_TYPE:妊检空话
    public static final String PIG_TYPE_CHECK_EMPTY = "checkEmpty";

    // PIG_TYPE:妊检流产
    public static final String PIG_TYPE_CHECK_MISCARRY = "checkMiscarry";

    // PIG_TYPE:分娩窝数
    public static final String PIG_TYPE_DELIVERY_NUM = "deliveryNum";

    // PIG_TYPE:健仔总数
    public static final String PIG_TYPE_HEALTHY_NUM = "healthyNum";

    // PIG_TYPE:窝均健仔
    public static final String PIG_TYPE_AVG_HEALTH_NUM = "avgHealthNum";

    // PIG_TYPE:初生重
    public static final String PIG_TYPE_AVG_ALIVE_WEIGHT = "avgAliveWeight";

    // PIG_TYPE:弱仔
    public static final String PIG_TYPE_WEEK_NUM = "weekNum";

    // PIG_TYPE:死胎
    public static final String PIG_TYPE_STILL_BIRTH_NUM = "stillBirthNum";

    // PIG_TYPE:木乃伊
    public static final String PIG_TYPE_MUMMY_NUM = "mummyNum";

    // PIG_TYPE:畸形
    public static final String PIG_TYPE_MUTANT_NUM = "mutantNum";

    // PIG_TYPE:留种公
    public static final String PIG_TYPE_ALIVE_LITTER_Y = "aliveLitterY";

    // PIG_TYPE:留种母
    public static final String PIG_TYPE_ALIVE_LITTER_X = "aliveLitterX";

    // 核算分类：企业
    public static final String ACCOUNT_COMPANY_CLASS_COMPANY = "2,4";

    // 核算分类：猪场
    public static final String ACCOUNT_COMPANY_CLASS_FARM = "3,4";

    // 核算分类：含有猪的猪场
    public static final String ACCOUNT_COMPANY_CLASS_HAVE_PIG_FARM = "3,4,5";

    // 日期开始时间
    public static final String START_DATE = "startDate";

    // 日期结束时间
    public static final String END_DATE = "endDate";

    // 是否是集团
    public static final String IS_GROUP = "isGroup";

    // SAP销售类型
    public static final String SAP_SALE_TYPE = "sapSaleType";

    // sap内销（供应商和客户同是新农并且法人不同）
    public static final String SAP_SALE_TYPE_INSIDE = "1";

    // sap外销（客户：非新农）
    public static final String SAP_SALE_TYPE_OUTSIDE = "2";

    // sap调拨（供应商和客户是同一法人）
    public static final String SAP_SALE_TYPE_ALLOCATION = "3";

    // sap自宰
    public static final String SAP_SALE_TYPE_SELF_KILL = "4";
}
