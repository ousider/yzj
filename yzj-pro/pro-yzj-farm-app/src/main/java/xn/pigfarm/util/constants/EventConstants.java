package xn.pigfarm.util.constants;

/**
 * @Description: 事件名称
 * @author Zhangjc
 * @date 2016年6月3日 上午8:57:41
 */
public class EventConstants {

    // 猪只入场
    public static final String PIG_MOVE_IN = "PIG_MOVE_IN";

    // 更换耳号
    public static final String CHANGE_EAR_BAND = "CHANGE_EAR_BAND";

    // 背膘测定
    public static final String BACKFAT = "BACKFAT";

    // 后备转生产
    public static final String BOAR_RESERVE_TO_PRODUCT = "BOAR_RESERVE_TO_PRODUCT";

    // 采精
    public static final String SEMEN = "SEMEN";

    // 精液检查
    public static final String SEMEN_CHECK = "SEMEN_CHECK";

    // 后备情期鉴定
    public static final String PREPUBERTAL_CHECK = "PREPUBERTAL_CHECK";

    // 转产房
    public static final String CHANGE_LABOR_HOUSE = "CHANGE_LABOR_HOUSE";

    // 种猪转舍
    public static final String BREED_PIG_CHANGE_HOUSE = "BREED_PIG_CHANGE_HOUSE";

    // 配种
    public static final String BREED = "BREED";

    // 母猪复配
    public static final String SOW_REPEAT = "SOW_REPEAT";

    // 妊娠检查
    public static final String PREGNANCY_CHECK = "PREGNANCY_CHECK";

    // 流产
    public static final String MISSCARRY = "MISSCARRY";

    // 分娩
    public static final String DELIVERY = "DELIVERY";

    // 寄养
    public static final String FOSTER = "FOSTER";

    // 断奶
    public static final String WEAN = "WEAN";

    // 种猪淘汰申请
    public static final String BREED_PIG_OBSOLETE = "BREED_PIG_OBSOLETE";

    // 种猪死亡
    public static final String BREED_PIG_DIE = "BREED_PIG_DIE";

    // 仔猪死亡
    public static final String CHILD_PIG_DIE = "CHILD_PIG_DIE";

    // 转保育
    public static final String TO_CHILD_CARE = "TO_CHILD_CARE";

    // 转育肥
    public static final String TO_CHILD_FATTEN = "TO_CHILD_FATTEN";

    // 商品猪死亡
    public static final String GOOD_PIG_DIE = "GOOD_PIG_DIE";

    // 商品猪销售
    public static final String GOOD_PIG_SELL = "GOOD_PIG_SELL";

    // 单据撤销
    public static final String BILL_CANCEL = "BILL_CANCEL";

    // 猪只事件撤销
    public static final String PIG_EVENT_CANCEL = "PIG_EVENT_CANCEL";

    // 单条记录撤销
    public static final String EACH_RECORD_CANCEL = "EACH_RECORD_CANCEL";

    // 转舍
    public static final String CHANGE_HOUSE = "CHANGE_HOUSE";

    // 留种
    public static final String SEED_PIG = "SEED_PIG";

    // 选种
    public static final String SELECT_BREED_PIG = "SELECT_BREED_PIG";

    // 肉猪盘点
    public static final String PORK_PIG_CHECK = "PORK_PIG_CHECK";

    // 精液销售
    public static final String SALE_SEMEN = "SALE_SEMEN";

    // 奶妈转舍
    public static final String NURSE_CHANGE_HOUSE = "NURSE_CHANGE_HOUSE";

    // 精液入库
    public static final String SEMEN_INQUIRY = "SEMEN_INQUIRY";

    // 未删除 各种BILL所用DELETED_FLAG为0
    public static final String DELETED_FLAG_NOT = "0";

    // 删除 各种BILL所用DELETED_FLAG为1
    public static final String DELETED_FLAG_DEL = "1";

    // 撤销 各种BILL所用DELETED_FLAG为2
    public static final String DELETED_FLAG_CANCEL = "2";

    // 产活率（头）
    public static final String ASSESS_CONTENT_CHS = "1";

    // 分娩率（%）
    public static final String ASSESS_CONTENT_FML = "2";

    // 膘情(13-18)(非后备)
    public static final String ASSESS_CONTENT_BQ = "3";

    // 窝均断奶窝重（KG）
    public static final String ASSESS_CONTENT_WJDNZ = "4";

    // 保育成活率（%）
    public static final String ASSESS_CONTENT_BYCHL = "5";

    // 保育7030（KG）
    public static final String ASSESS_CONTENT_BL7030 = "6";

    // 育肥110公斤出栏日龄
    public static final String ASSESS_CONTENT_YF110CLRL = "7";

    // 育肥成活率(%)
    public static final String ASSESS_CONTENT_YFCHL = "8";

    // 窝均断奶头数(头)
    public static final String ASSESS_CONTENT_WJDNT = "9";

    // 全程死亡率
    public static final String ASSESS_CONTENT_QCSW = "10";

    // 上产房背膘
    public static final String ASSESS_CONTENT_SCFBB = "11";

    // 断奶背膘
    public static final String ASSESS_CONTENT_DNBB = "12";

    // 上月25号到本月26号
    public static final long PERFORMANCE_DATE_AREA_TYPE_A = 1;

    // 本月第一天到本月最后一天
    public static final long PERFORMANCE_DATE_AREA_TYPE_B = 2;

    // 场长
    public static final String PERFORMANCE_ROLE_FARM = "1";

    // 技术员
    public static final String PERFORMANCE_ROLE_TECH = "2";

    // double类型为空标识
    public static final double NULL_TYPE_DOUBLE = -99;

    /***************************************** 生产数据汇总 ************************************************/
    /******** 分类 ********/
    // 存栏
    public static final long PRODUCE_DATA_TYPE_LIVESTOCK = 1;

    // 上市
    public static final long PRODUCE_DATA_TYPE_BELIST = 2;

    // 繁殖
    public static final long PRODUCE_DATA_TYPE_MULTIPLY = 3;

    // 死亡率
    public static final long PRODUCE_DATA_TYPE_MORTALITY = 4;

    // 流程指标
    public static final long PRODUCE_DATA_TYPE_PROCESS_INDEX = 5;

    // 成本预告
    public static final long PRODUCE_DATA_TYPE_COST_ESTIMATE = 6;

    /******** 指标 ********/
    // 生产母猪存栏
    public static final long PRODUCE_DATA_INDEX_FEMALE_LIVESTOCK = 1;

    // 商品猪销售头数
    public static final long PRODUCE_DATA_INDEX_GOODSALE = 2;

    // 配种数
    public static final long PRODUCE_DATA_INDEX_BREEDS = 3;

    // 返+假+流
    public static final long PRODUCE_DATA_INDEX_FJL = 4;

    // 全程死亡率%
    public static final long PRODUCE_DATA_INDEX_OVERALL_MORTALITY = 5;

    // 肥猪死亡率%
    public static final long PRODUCE_DATA_INDEX_HOG_MORTALITY = 6;

    // 保育死亡率%
    public static final long PRODUCE_DATA_INDEX_CONSERVATION_MORTALITY = 7;

    // 产房死亡率%
    public static final long PRODUCE_DATA_INDEX_DELIVERY_MORTALITY = 8;

    // 窝均产活仔数
    public static final long PRODUCE_DATA_INDEX_LITTER_SIZE = 9;

    // 窝均断奶数
    public static final long PRODUCE_DATA_INDEX_LITTER_WEANING_NUMBER = 10;

    // 断奶窝重
    public static final long PRODUCE_DATA_INDEX_WEANING_WEIGHT = 11;

    // 分娩率
    public static final long PRODUCE_DATA_INDEX_DELIVERY_RATE = 12;

    // 7030
    public static final long PRODUCE_DATA_INDEX_7030 = 13;

    // 110KG上市日龄
    public static final long PRODUCE_DATA_INDEX_110AGE = 14;

    // 全场料肉比(月)
    public static final long PRODUCE_DATA_INDEX_FULL_FIELD_MEAT_RATIO = 15;

    // 肥猪料肉比(月)
    public static final long PRODUCE_DATA_INDEX_FEED_MEAT_RATIO = 16;

    // 出栏成本预估
    public static final long PRODUCE_DATA_INDEX_SLAUGHTER_COST_ESTIMATE = 17;

    // 出栏成本预估
    public static final long PRODUCE_DATA_INDEX_INVENTORY_COST_ESTIMATE = 18;

    // 后备母猪存栏
    public static final long PRODUCE_DATA_INDEX_HBFEMALE_LIVESTOCK = 19;

    // 商品猪存栏
    public static final long PRODUCE_DATA_INDEX_GOODPIG_LIVESTOCK = 20;

    // 苗猪销售
    public static final long PRODUCE_DATA_INDEX_MIAOZHU_SALE = 21;

    // 肥猪销售
    public static final long PRODUCE_DATA_INDEX_FEIZHU_SALE = 22;

    // 销售猪只均重
    public static final long PRODUCE_DATA_INDEX_AVG_SALE_WEIGHT = 23;

    // 库存猪只均重
    public static final long PRODUCE_DATA_INDEX_AVG_INVENTORY_WEIGHT = 24;

    /***************************************** 生产数据汇总 ************************************************/

    /***************************************** 生产数据汇总目标 ************************************************/
    // 基础母猪存栏
    public static final String INDICATOR_BUSINESS_CODE_JCMZCL = "Z001";

    // 商品猪存栏
    public static final String INDICATOR_BUSINESS_CODE_SPZCL = "Z057";

    // 商品猪销售头数
    public static final String INDICATOR_BUSINESS_CODE_SPZXSTS = "Z058";

    // 苗猪销售头数
    public static final String INDICATOR_BUSINESS_CODE_MZXSTS = "Z059";

    // 肥猪销售头数
    public static final String INDICATOR_BUSINESS_CODE_FZXSTS = "Z060";

    // 配种数
    public static final String INDICATOR_BUSINESS_CODE_PZS = "Z061";

    // 产房死亡率%
    public static final String INDICATOR_BUSINESS_CODE_CFSWL = "Z062";

    // 保育死亡率%
    public static final String INDICATOR_BUSINESS_CODE_BYSWL = "Z042";

    // 肥猪死亡率%
    public static final String INDICATOR_BUSINESS_CODE_FZSWL = "Z046";

    // 全程死亡率%
    public static final String INDICATOR_BUSINESS_CODE_QCSWL = "Z063";

    // 分娩率%（月）
    public static final String INDICATOR_BUSINESS_CODE_FML = "Z022";

    // 7030
    public static final String INDICATOR_BUSINESS_CODE_7030 = "Z064";

    // 110kg出栏
    public static final String INDICATOR_BUSINESS_CODE_110CL = "Z055";

    // 肥猪类肉比（月）
    public static final String INDICATOR_BUSINESS_CODE_FZLRB = "Z065";

    // 全场类肉比（月）
    public static final String INDICATOR_BUSINESS_CODE_QCLRB = "Z066";

    // 窝均产活仔数
    public static final String INDICATOR_BUSINESS_CODE_WJCHZS = "Z025";

    // 窝均断奶数
    public static final String INDICATOR_BUSINESS_CODE_WJDNS = "Z067";

    // 断奶窝重
    public static final String INDICATOR_BUSINESS_CODE_DNWZ = "Z068";

    // 产房存栏系数
    public static final String INDICATOR_BUSINESS_CODE_CFCLXS = "Z069";

    // 保育存栏系数
    public static final String INDICATOR_BUSINESS_CODE_BYCLXS = "Z070";

    // 育肥存栏系数
    public static final String INDICATOR_BUSINESS_CODE_YFCLXS = "Z071";

    // 大丰育肥场系数标准存栏
    public static final String INDICATOR_BUSINESS_CODE_DFYFXS = "Z072";

    /***************************************** 生产数据汇总目标 ************************************************/

}
