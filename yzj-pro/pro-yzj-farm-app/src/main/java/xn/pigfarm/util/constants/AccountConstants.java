package xn.pigfarm.util.constants;

/**
 * @Description: 销售结算单常量
 * @author Administrator
 * @date 2017年3月13日 上午11:06:22
 */
public class AccountConstants {

    private AccountConstants() {

    }

    /* ============== 项目类型BEGIN ============== */
    // 扣款明细
    public static final String ITEM_TYPE_DETAILED_DEDUCTION = "1";

    // 屠宰质量
    public static final String ITEM_TYPE_SLAUGHTER_QUALITY = "2";

    // 其他
    public static final String ITEM_TYPE_OTHER = "3";
    /* ============== 项目类型END ============== */

    /* ============== 项目阶段BEGIN ============== */
    // 宰前
    public static final String ITEM_STAGE_PRE_SLAUGHTER = "1";

    // 宰后
    public static final String ITEM_STAGE_POST_SLAUGHTER = "2";

    /* ============== 项目阶段END ============== */

    // BCODE:销售结算单编码
    public static final String SALE_ACCOUNT = "SALE_ACCOUNT";

    /* =================销售单状态BEGIN===================== */

    // 销售未结算
    public static final String SALE_STATUS_IS_ACCOUNT_FALSE = "1";

    // 销售已结算
    public static final String SALE_STATUS_IS_ACCOUNT_TRUE = "2";

    // 无需销售结算
    public static final String SALE_STATUS_NOT_ACCOUNT = "3";

    /* =================销售单状态END======================= */

}
