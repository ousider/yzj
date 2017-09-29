package xn.pigfarm.util.constants;

import java.util.HashMap;
import java.util.Map;

public class MaterialConstants {

    public static final Map<String, String> MATERIAL_MAP = new HashMap<String, String>();
    static {
        MATERIAL_MAP.put("8", "Vaccine");
        MATERIAL_MAP.put("4", "Sperm");
        MATERIAL_MAP.put("2", "Sow");
        MATERIAL_MAP.put("6", "RawMaterial");
        MATERIAL_MAP.put("13", "Ppe");
        MATERIAL_MAP.put("3", "PorkPig");
        MATERIAL_MAP.put("11", "Hardware");
        MATERIAL_MAP.put("5", "Feed");
        MATERIAL_MAP.put("7", "Drug");
        MATERIAL_MAP.put("9", "Disinfector");
        MATERIAL_MAP.put("10", "Device");
        MATERIAL_MAP.put("12", "Consumable");
        MATERIAL_MAP.put("1", "Boar");
    }

    // 疫苗
    public static final String MATERIAL_VACCINE = "Vaccine";
    // 精液
    public static final String MATERIAL_SPERM = "Sperm";
    // 母猪
    public static final String MATERIAL_SOW = "Sow";
    // 原料
    public static final String MATERIAL_RAW_MATERIAL = "RawMaterial";
    // 劳保用品
    public static final String MATERIAL_PPE = "Ppe";
    // 肉猪
    public static final String MATERIAL_PORK_PIG = "PorkPig";
    // 五金
    public static final String MATERIAL_HARDWARE = "Hardware";
    // 饲料
    public static final String MATERIAL_FEED = "Feed";
    // 药品
    public static final String MATERIAL_DRUG = "Drug";
    // 消毒济
    public static final String MATERIAL_DISINFECTOR = "Disinfector";
    // 设备
    public static final String MATERIAL_DEVICE = "Device";
    // 易耗品
    public static final String MATERIAL_CONSUMABLE = "Consumable";
    // 公猪
    public static final String MATERIAL_BOAR = "Boar";

}
