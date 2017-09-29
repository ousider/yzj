package xn.pigfarm.util.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 导出导入常量
 * @author zhangjs
 * @date 2016年8月24日 下午4:45:43
 */
public class ExpImpConstants {
    private ExpImpConstants() {
        
    }

    /* 导入执行状态 */
    // 未执行
    public static final String STATUS_UNEXC = "1";

    // 执行成功
    public static final String STATUS_SUC = "3";

    // 执行失败
    public static final String STATUS_ERROR = "4";

    // 猪只入场导出模板
    public static final String EAR_BLAND_INITIALIZE = "EAR_BLAND_INITIALIZE";

    // 猪只初始化导出模板
    public static final String INPUT_PIG = "INPUT_PIG";

    // 猪舍初始化导出模板
    public static final String INPUT_HOUSE = "INPUT_HOUSE";

    // 肉猪初始化导出模板
    public static final String INPUT_PORK = "INPUT_PORK";
    
    // 肉猪主数据初始化导出模板
    public static final String MATERIAL_TEMPLATE_PORK = "MATERIAL_TEMPLATE_PORK";

    /* ===============BEGIN猪只导入错误编码 ===================== */
    // 耳号未填
    public static final String INPUT_PIG_EB_NULL = "EAR_BRAND_NULL";

    // 物料主数据未填
    public static final String INPUT_PIG_MATIRAL_NULL = "MATIRAL_NULL";

    // 猪舍未填
    public static final String INPUT_PIG_HOUSE_NULL = "PIG_HOUSE_NULL";

    // 物料与系统中物料不匹配
    public static final String INPUT_PIG_MATIRAL_NOT_MATCH = "MATIRAL_NOT_MATCH";

    // 猪舍与系统中猪舍不匹配
    public static final String INPUT_PIG_HOUSE_NOT_MATCH = "HOUSE_NOT_MATCH";

    // 猪栏与系统中猪栏不匹配
    public static final String INPUT_PIG_PIGPEN_NOT_MATCH = "PIGPEN_NOT_MATCH";

    // 批次与系统中批次不匹配
    public static final String INPUT_PIG_SWINERY_NOT_MATCH = "SWINERY_NOT_MATCH";

    // 供应商与系统中供应商不匹配
    public static final String INPUT_PIG_SUPPLIER_NOT_MATCH = "SUPPLIER_NOT_MATCH";

    // 导入单据中耳号不可以重复
    public static final String INPUT_PIG_EAR_BRAND_REPEAT = "EAR_BRAND_REPEAT";

    // 公猪出生日期必填
    public static final String INPUT_PIG_BOAR_BIRTH = "BOAR_BIRTH";

    // 母猪胎次未填
    public static final String INPUT_PIG_SOW_PARITY_NULL = "SOW_PARITY_NULL";

    // 母猪胎次重复
    public static final String INPUT_PIG_SOW_PARITY_REPEAT = "SOW_PARITY_REPEAT";

    // 母猪出生日期和配种日期至少填其中一个
    public static final String INPUT_PIG_SOW_BIRTH_BREED = "SOW_BIRTH_BREED";

    // 配种日期填了，配种公猪必填
    public static final String INPUT_PIG_SOW_BREED = "SOW_BREED";

    // 配种日期必须大于出生日期
    public static final String INPUT_PIG_SOW_BREED_DATE = "SOW_BREED_DATE";

    // 转产日期填了，产房必填
    public static final String INPUT_PIG_SOW_CHANGE_HOUSE = "SOW_CHANGE_HOUSE";
    
    // 转产日期填了，栏位必填
    public static final String INPUT_PIG_SOW_CHANGE_HOUSE_COLUMN = "SOW_CHANGE_HOUSE_COLUMN";

    // 转产房日期必须大于配种日期
    public static final String INPUT_PIG_SOW_CHANGE_HOUSE_DATE = "SOW_CHANGE_HOUSE_DATE";
    
    // 分娩日期必须大于转产日期
    public static final String INPUT_PIG_SOW_DELIVERY_DATE= "SOW_DELIVERY_DATE";

    // 断奶日期填了，分娩日期必须填
    public static final String INPUT_PIG_SOW_WEAN_DELIVERY = "SOW_WEAN_DELIVERY";

    // 断奶日期填了,断奶窝重、断奶数必须填
    public static final String INPUT_PIG_SOW_WEAN = "SOW_WEAN";

    // 断奶日期必须大于分娩日期
    public static final String INPUT_PIG_SOW_WEAN_DATE = "SOW_WEAN_DATE";

    public static final Map<String, String> INPUT_PIG_ERROR_MAP = new HashMap<>();
    static {
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_WEAN, "断奶日期已填,断奶窝重、断奶数必填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_WEAN_DELIVERY, "断奶日期已填，分娩日期必填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_CHANGE_HOUSE, "转产日期已填，产房必填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_CHANGE_HOUSE_COLUMN, "转产日期填了，栏位必填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_BREED, "配种日期已填，配种公猪必填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_BIRTH_BREED, "母猪出生日期和配种日期至少填一个");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_PARITY_REPEAT, "同一耳号母猪胎次重复");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_PARITY_NULL, "母猪胎次未填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_EAR_BRAND_REPEAT, "导入耳号不可重复");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_PIGPEN_NOT_MATCH, "猪栏与系统中猪栏不匹配");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_HOUSE_NOT_MATCH, "猪舍与系统中猪舍不匹配");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SWINERY_NOT_MATCH, "批次与系统中批次不匹配");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SUPPLIER_NOT_MATCH, "供应商与系统中供应商不匹配");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_MATIRAL_NOT_MATCH, "物料与系统中物料不匹配");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_HOUSE_NULL, "猪舍未填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_MATIRAL_NULL, "物料主数据未填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_EB_NULL, "耳号未填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_BOAR_BIRTH, "公猪出生日期必填");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_BREED_DATE, "配种日期必须大于出生日期");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_CHANGE_HOUSE_DATE, "转产房日期必须大于配种日期");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_DELIVERY_DATE, "分娩日期必须大于转产日期");
        INPUT_PIG_ERROR_MAP.put(INPUT_PIG_SOW_WEAN_DATE, "断奶日期必须大于分娩日期");
    }

    /* ===============END猪只导入错误编码 ===================== */


}
