package xn.pigfarm.util.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 猪只常量
 * @author zhangjs
 * @date 2016年8月16日 下午4:53:01
 */
public class PigConstants {
    private PigConstants() {

    }

    /* =============BEGIN猪类型================= */
    // 公猪
    public static final String PIG_TYPE_BOAR = "1";

    // 母猪
    public static final String PIG_TYPE_SOW = "2";

    // 肉猪
    public static final String PIG_TYPE_PORK = "3";

    public static final Map<String, String> PIG_TYPE_MAP = new HashMap<String, String>();
    static {
        PIG_TYPE_MAP.put(PIG_TYPE_BOAR, "公猪");
        PIG_TYPE_MAP.put(PIG_TYPE_SOW, "母猪");
        PIG_TYPE_MAP.put(PIG_TYPE_PORK, "肉猪");
    }
    /* =============END猪类型================= */

    /* 初始化猪只执行状态 */
    // 1:未执行
    public static final String PIG_INIT_STATUS_NON = "1";

    // 2:执行中
    public static final String PIG_INIT_STATUS_EXC = "2";

    // 3:执行成功
    public static final String PIG_INIT_STATUS_SUC = "3";

    // 4:执行失败
    public static final String PIG_INIT_STATUS_ERROR = "4";

    /* 猪只入场操作类型 */
    // 1 建立档案（批次入场）
    public static final String PIG_MOVE_IN_OPT_TYPE_FILE = "1";

    // 2 耳号入场
    public static final String PIG_MOVE_IN_OPT_TYPE_EAR = "2";

    // 3 分娩入场
    public static final String PIG_MOVE_IN_OPT_TYPE_DEL = "3";

    // 4 虚拟公猪入场
    public static final String PIG_MOVE_IN_OPT_TYPE_VIR = "4";

    /* 猪只性别 */
    // 公
    public static final String PIG_SEX_MALE = "1";

    // 母
    public static final String PIG_SEX_FEMALE = "2";

    // 混合
    public static final String PIG_SEX_MIX = "3";

    /* 配种方式 */
    // 人工授精
    public static final String PIG_BREED_TYPE_SPE = "1";

    // 本交
    public static final String PIG_BREED_TYPE_NAT = "2";

    // 外购精液
    public static final String PIG_BREED_TYPE_OUT_SPE = "3";

    /* 妊娠方式 */
    // B超
    public static final String PIG_PREGNANCY_TYPE_B = "1";

    // 公猪试情
    public static final String PIG_PREGNANCY_TYPE_BOAR = "2";

    // 人工检查
    public static final String PIG_PREGNANCY_TYPE_ARTI = "3";

    /* 转舍类型 */
    // 上产房内转
    public static final String CHANGE_HOUSE_TYPE_ADV = "1";

    // 上产房
    public static final String CHANGE_HOUSE_TYPE_DEL = "2";

    // 保育内转
    public static final String CHANGE_HOUSE_TYPE_CHI_SELF = "3";

    // 产房转保育
    public static final String CHANGE_HOUSE_TYPE_CHI = "4";

    // 育肥内转
    public static final String CHANGE_HOUSE_TYPE_FAT_SELF = "5";

    // 保育转育肥
    public static final String CHANGE_HOUSE_TYPE_FAT = "6";

    // 种猪转舍
    public static final String CHANGE_HOUSE_TYPE_SELF = "7";

    // 下产房
    public static final String CHANGE_HOUSE_TYPE_DOWN_DEL = "8";

    // 其他情况转舍
    public static final String CHANGE_HOUSE_TYPE_OTH = "9";

    // 选种转后备
    public static final String CHANGE_HOUSE_TYPE_FAT_HB = "10";

    /* 分娩类型 */
    // 顺产
    public static final String DELIVERY_TYPE_EUT = "N";

    // 助产
    public static final String DELIVERY_TYPE_ACC = "Y";

    /* 状态 */
    // 正常状态
    public static final String STATUS_NOR = "1";

    /* 删除情况 */
    // 未删除
    public static final String DELETED_FLAG_NOT = "0";

    // 删除
    public static final String DELETED_FLAG_DEL = "1";

    /* 数据录入系统 */
    // 1 数据导入
    public static final String SYS_INPUT_IMP = "1";

    // 2：正常录入
    public static final String SYS_INPUT_NOR = "2";

    /* 生成仔猪方式 */
    // 1:生成仔猪;
    public static final String ACTION_TYPE_NOR = "1";

    // 2:自动生成并保存所有仔猪
    public static final String ACTION_TYPE_AUTO = "2";

    /******************** 猪只状态 begin ***************************/
    // 后备公猪
    public static final long PIG_CLASS_HBGZ = 1;

    // 生产公猪
    public static final long PIG_CLASS_SCGZ = 2;

    // 后备母猪
    public static final long PIG_CLASS_HBMZ = 3;

    // 后备待配
    public static final long PIG_CLASS_HBDP = 4;

    // 发情
    public static final long PIG_CLASS_FQ = 5;

    // 返情
    public static final long PIG_CLASS_FQ1 = 6;

    // 流产
    public static final long PIG_CLASS_LC = 7;

    // 空怀
    public static final long PIG_CLASS_KH = 8;

    // 妊娠
    public static final long PIG_CLASS_RS = 9;

    // 哺乳
    public static final long PIG_CLASS_BR = 10;

    // 断奶
    public static final long PIG_CLASS_SOW_DN = 11;

    // 哺乳-健仔
    public static final long PIG_CLASS_BRJZ = 12;

    // 哺乳-弱仔
    public static final long PIG_CLASS_BRRZ = 13;

    // 断奶
    public static final long PIG_CLASS_PORK_DN = 14;

    // 保育
    public static final long PIG_CLASS_BF = 15;

    // 育肥
    public static final long PIG_CLASS_YH = 16;

    // 留种公
    public static final long PIG_CLASS_LZG = 17;

    // 留种母
    public static final long PIG_CLASS_LZM = 18;

    // 死胎
    public static final long PIG_CLASS_ST = 19;

    // 木乃伊
    public static final long PIG_CLASS_MNY = 20;

    // 畸形
    public static final long PIG_CLASS_JX = 21;

    // 黑胎
    public static final long PIG_CLASS_HT = 22;

    // 死亡
    public static final long PIG_CLASS_SW = 23;

    // 淘汰
    public static final long PIG_CLASS_TT = 24;

    // 销售
    public static final long PIG_CLASS_XS = 25;

    /******************** 猪只状态 end ***************************/

    // 淘汰申请状态（审批中状态）
    public static final String SATAUS_OBSOLETE_APPLY = "1";

    // 淘汰通过状态
    public static final String SATAUS_OBSOLETE_SUCCESS = "2";

    // 淘汰不通过状态
    public static final String SATAUS_OBSOLETE_FAIL = "3";

    // 淘汰通过已销售状态
    public static final String SATAUS_OBSOLETE_SUCCESS_TO_SELL = "4";

    /******************** 猪舍类型 -table：cd_l_pig_house ***************************/

    // 隔离舍
    public static final long HOUSE_CLASS_ISOLATION = 1;

    // 后备舍
    public static final long HOUSE_CLASS_MOTHBALL = 2;

    // 空怀舍
    public static final long HOUSE_CLASS_NONPREGNANT = 3;

    // 重怀舍
    public static final long HOUSE_CLASS_AGMINPREGNANCY = 4;

    // 妊娠舍
    public static final long HOUSE_CLASS_PREGNANCY = 5;

    // 产房舍
    public static final long HOUSE_CLASS_DELIVERY = 6;

    // 公猪舍
    public static final long HOUSE_CLASS_BOAR = 7;

    // 保育舍
    public static final long HOUSE_CLASS_CAREPIG = 8;

    // 育肥舍
    public static final long HOUSE_CLASS_FATTEN = 9;

    // 采精台
    public static final long HOUSE_CLASS_MAKESEMEN = 10;

    // 卖猪台
    public static final long HOUSE_CLASS_SALEPIG = 11;

    // 排粪池
    public static final long HOUSE_CLASS_CESSPOOL = 12;

    // 化尸池
    public static final long HOUSE_CLASS_CINERATOR = 13;

    // 消毒池
    public static final long HOUSE_CLASS_DISINFECTING = 14;

    /******************** 猪舍类型 end ***************************/
    /******************** 建群方式 -table：pp_m_swinery ***************************/
    // 自己建群
    public static final String SWINERY_CREATE_TYPE_SELF = "2";

    // 系统建群
    public static final String SWINERY_CREATE_TYPE_SYS = "1";

    // 猪群状态
    public static final String SWINERY_STATUS_OPEN = "1";

    public static final String SWINERY_STATUS_CLOSE = "2";

    public static final Map<String, String> SWINERY_STATUS_MAP = new HashMap<String, String>();
    static {
        SWINERY_STATUS_MAP.put(SWINERY_STATUS_OPEN, "开启");
        SWINERY_STATUS_MAP.put(SWINERY_STATUS_CLOSE, "关闭");
    }

    /******************** 建群方式 end ***************************/
    /******************** 离场类型 begin table=cd_l_code_list ***************************/
    // 种猪淘汰
    public static final String PIG_BOAR_OUT = "1";

    // 种猪死亡
    public static final String PIG_BOAR_DIE = "2";

    // 仔猪死亡
    public static final String PIG_SON_DIE = "3";

    // 商品猪死亡
    public static final String PIG_BOAR_DID = "4";

    // 商品猪销售
    public static final String PIG_GOOD_SALE = "5";

    /******************** 离场类型 end ***************************/

    /* 查询配种公猪方式 */
    // 1.模糊查询且分页（只有在输入耳号时执行）
    public static final String SEARCH_SEMEN_QUERY_TYPE_LIKE = "1";

    // 2.条件查询
    public static final String SEARCH_SEMEN_QUERY_TYPE_CON = "2";

    // 1.测试背膘STAGE：上产房
    public static final String BACKFAT_STAGE_CHANGELABORHOUSE = "1";

    // 2.测试背膘STAGE：断奶
    public static final String BACKFAT_STAGE_WEAN = "2";

    // 3.测试背膘STAGE：背膘
    public static final String BACKFAT_STAGE_BACKFAT = "3";

    // 1.合并批次：是
    public static final String SWINERY_MERGE = "Y";

    // 2.合并批次：否
    public static final String SWINERY_NOT_MERGE = "N";

    // 背标评分
    public static final Map<String, String> PIG_BACKFAT_MAP = new HashMap<String, String>();
    static {
        // 背标12以下 评分 2
        // 背标12-13 评分 2.5
        // 背标14-15 评分 3
        // 背标16-17 评分 3.5
        // 背标18-19 评分 4
        // 背标20以上 评分 4.5
        PIG_BACKFAT_MAP.put("LVL1", "2");
        PIG_BACKFAT_MAP.put("LVL2", "2.5");
        PIG_BACKFAT_MAP.put("LVL3", "3");
        PIG_BACKFAT_MAP.put("LVL4", "3.5");
        PIG_BACKFAT_MAP.put("LVL5", "4");
        PIG_BACKFAT_MAP.put("LVL6", "4.5");
    }

    /******************** 销售类型 begin ***************************/
    // SELL_TYPE 销售类型 1 商品猪
    public static final String SELL_TYPE_GOOD_PIG = "1";

    // SELL_TYPE 销售类型 2 种猪销售
    public static final String SELL_TYPE_PRODUCTION_PIG = "2";

    // SELL_TYPE 销售类型 3 种猪淘汰
    public static final String SELL_TYPE_PRODUCTION_OUT_PIG = "3";

    // SELL_TYPE 销售类型 4 猪场自宰
    public static final String SELL_TYPE_FARM_SELF_KILL_PIG = "4";

    /******************** 销售类型 end ***************************/

    /******************** 销售品名 begin ***************************/
    // 1 生产公猪
    public static final String SELL_GOOD_PRODUCTION_BOARD_PIG = "1";

    // 2生产母猪
    public static final String SELL_GOOD_PRODUCTION_SOW_PIG = "2";

    // 3肥猪
    public static final String SELL_GOOD_HOG_PIG = "3";

    // 4 苗猪
    public static final String SELL_GOOD_PIGGY_PIG = "4";

    // 5 残次猪
    public static final String SELL_GOOD_IMPERFECT_PIG = "5";

    // 6 后备公猪
    public static final String SELL_GOOD_RESERVE_BOARD_PIG = "6";

    // 7 后备母猪
    public static final String SELL_GOOD_RESERVE_SOW_PIG = "7";

    // 8 留种公猪
    public static final String SELL_GOOD_BOAR_SEED = "8";

    // 9 留种母猪
    public static final String SELL_GOOD_SOW_SEED = "9";

    // 10 留种猪
    public static final String SELL_GOOD_SEED = "10";

    /******************** 销售品名 end ***************************/
    /******************** 妊娠检查结果 begin ***************************/
    // 妊娠检查结果 1 阳性
    public static final String PREGNANCY_RESULT_POSITIVE = "1";

    // 妊娠检查结果 2 阴性
    public static final String PREGNANCY_RESULT_NEGATIVE = "2";

    // 妊娠检查结果 3 疑似
    public static final String PREGNANCY_RESULT_SEEMINGLY = "3";

    // 妊娠检查结果 4 返情
    public static final String PREGNANCY_RESULT_ESTRUS = "4";

    // 妊娠检查结果 5 流产
    public static final String PREGNANCY_RESULT_ABORTION = "5";

    // 妊娠检查结果 6 假孕
    public static final String PREGNANCY_RESULT_PSEUDOPREGNANCY = "6";

    /******************** 妊娠检查结果 end ***************************/

    /******************** 猪按照不同阶段分类 begin ***************************/

    // 生产种猪
    public static final String PRODUCTION_PIG = "productionPig";

    // 哺乳猪
    public static final String LACTATION_PIG = "lactationPig";

    // 保育
    public static final String NURSING_PIG = "nursingPig";

    // 育肥
    public static final String FATTENING_PIG = "fatteningPig";

    /******************** 猪按照不同阶段分类 end ***************************/
    // 是否留种 否
    public static final String SEED_TYPE_NO = "N";

    // 是否留种 是
    public static final String SEED_TYPE_YES = "Y";

    /************************* 猪只来源 Origin BEGIN *********************************/
    // 本场
    public static final String PIG_ORIGIN_HC = "1";

    // 外购
    public static final String PIG_ORIGIN_OS = "2";

    // 虚拟
    public static final String PIG_ORIGIN_XN = "3";

    /************************* 猪只来源 Origin END *********************************/

    /************************* 校正类型 Origin BEGIN *********************************/
    // 产房转保育 用于校正24日断奶体重
    public static final String WCM_001 = "WCM_001";

    // 保育转育肥 用于校正7030体重
    public static final String WCM_002 = "WCM_002";

    // 销售 用于110kg 出栏日龄校验
    public static final String WCM_003 = "WCM_003";

    /************************* 校正类型 Origin END *********************************/

    /************************* 选种类型 Origin BEGIN *********************************/
    public static final String SELECT_BREED_MALE_TYPE = "种公猪";

    public static final String SELECT_BREED_FEMALE_TYPE = "种母猪";

    /************************* 选种类型 Origin END *********************************/

    /************************* 耳牌号，耳缺号 Origin BEGIN *********************************/
    public static final String EAR_BRAND_CODE_TYPE = "earBrand";

    public static final String EAR_SHORT_CODE_TYPE = "earShort";

    /************************* 耳牌号，耳缺号 Origin END *********************************/

    /************************* 销售单据类型BEGIN ****************************/
    // 内销
    public static final String SALE_BILL_TYPE_INSIDE = "1";

    // 外销
    public static final String SALE_BILL_TYPE_OUTSIDE = "2";

    /************************* 销售单据类型BEGIN ****************************/

    /*************************** 精液来源 BEGIN **********************************/

    // 本场
    public static final String SEMEN_ORIGIN_BC = "1";

    // 平台内
    public static final String SEMEN_ORIGIN_PTN = "2";

    // 外购
    public static final String SEMEN_ORIGIN_WG = "3";

    /*************************** 精液来源 BEGIN **********************************/

    /********************* 培育舍BEGIN ************************/
    // 是培育舍
    public static final String IS_FOSTER_HOUSE_TRUE = "Y";

    // 不是培育舍
    public static final String IS_FOSTER_HOUSE_FALSE = "N";

    /********************* 培育舍END ************************/

    /********************* sap的销售类型BEGIN *************************/
    // sap内销（供应商和客户同是新农并且法人不同）
    public static final String SAP_SALE_TYPE_INSIDE = "1";

    // sap外销（客户：非新农）
    public static final String SAP_SALE_TYPE_OUTSIDE = "2";

    // sap调拨（供应商和客户是同一法人）
    public static final String SAP_SALE_TYPE_ALLOCATION = "3";

    // sap自宰
    public static final String SAP_SALE_TYPE_SELF_KILL = "4";

    /********************* hana的销售类型END *************************/

    /********************* 生产猪阶段 BEGIN *************************/
    // 生产猪阶段 <= 84 轻胎,否则重胎
    public static final long PRODUCTION_STAGE = 84;

    /********************* 生产猪阶段 END *************************/

}
