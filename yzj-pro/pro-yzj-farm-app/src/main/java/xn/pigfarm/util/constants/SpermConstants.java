package xn.pigfarm.util.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 猪只常量
 * @author zhangjs
 * @date 2016年8月16日 下午4:53:01
 */
public class SpermConstants {
    private SpermConstants() {

    }

    /* =============BEGIN精液状态================= */
    // 精液状态:未使用
    public static final String SPERM_INFO_STATUS_UNUSE = "1";

    // 精液状态：已使用
    public static final String SPERM_INFO_STATUS_USE = "2";

    // 精液状态：销售
    public static final String SPERM_INFO_STATUS_SALE = "3";

    // 精液状态：报废
    public static final String SPERM_INFO_STATUS_SCRAP = "4";

    // 精液状态：过期失效
    public static final String SPERM_INFO_STATUS_LOSE_EFFECTIVE = "5";

    // 精液状态名称:未使用
    public static final String SPERM_INFO_STATUS_UNUSE_NAME = "未使用";

    // 精液状态名称：已使用
    public static final String SPERM_INFO_STATUS_USE_NAME = "已使用";

    // 精液状态名称：销售
    public static final String SPERM_INFO_STATUS_SALE_NAME = "销售";

    // 精液状态名称：报废
    public static final String SPERM_INFO_STATUS_SCRAP_NAME = "报废";

    // 精液状态名称：过期失效
    public static final String SPERM_INFO_STATUS_LOSE_EFFECTIVE_NAME = "过期失效";

    public static final Map<String, String> SPERM_INFO_STATUS_MAP = new HashMap<String, String>();

    static {
        SPERM_INFO_STATUS_MAP.put(SPERM_INFO_STATUS_UNUSE, SPERM_INFO_STATUS_UNUSE_NAME);
        SPERM_INFO_STATUS_MAP.put(SPERM_INFO_STATUS_USE, SPERM_INFO_STATUS_USE_NAME);
        SPERM_INFO_STATUS_MAP.put(SPERM_INFO_STATUS_SALE, SPERM_INFO_STATUS_SALE_NAME);
        SPERM_INFO_STATUS_MAP.put(SPERM_INFO_STATUS_SCRAP, SPERM_INFO_STATUS_SCRAP_NAME);
        SPERM_INFO_STATUS_MAP.put(SPERM_INFO_STATUS_LOSE_EFFECTIVE, SPERM_INFO_STATUS_LOSE_EFFECTIVE_NAME);
    }
    /* =============END精液状态================= */

    // 精液销售状态：未销售
    public static final Long SPERM_INFO_IS_SALE_UNSALE = 0L;

    // 精液销售状态：已销售
    public static final Long SPERM_INFO_IS_SALE_SALE = 1L;

    // 精液销售状态：未入库
    public static final Long SPERM_INFO_IS_ENTRY_UNENTRY = 0L;

    // 精液销售状态：已入库
    public static final Long SPERM_INFO_IS_ENTRY_ENTRY = 1L;

}
