package xn.core.data.enums;

/**
 * @Description: 序列号TABLE_NAME
 * @author zhangjs
 * @date 2016年8月18日 下午6:27:45
 */
public enum SeqEnum {

    PIG("PP_L_PIG"),
    BILL("PP_M_BILL"),
    COMPANY("HR_M_COMPANY"),
    HOUSE("PP_L_BILL_CHANGE_HOUSE"),
    BACKFAT("PP_L_BILL_BACKFAT"),
    FOSTER("PP_L_BILL_FOSTER_CARE"),
    TOWORK("PP_L_BILL_TOWORK");
    
    
    private final String tableName;

    SeqEnum(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
