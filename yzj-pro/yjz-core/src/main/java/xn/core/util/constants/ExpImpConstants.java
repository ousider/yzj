package xn.core.util.constants;

/**
 * @Description: 导入导出常量
 * @author zhangjs
 * @date 2016年8月16日 下午4:53:01
 */
public class ExpImpConstants {

    private ExpImpConstants() {

    }

    /* ==========BEGIN excel模板相关常量=============== */
    // 记录查询隐藏域的ID
    public static final String HIDDEN_ID = "id";

    // 记录查询隐藏域的NAME
    public static final String HIDDEN_NAME = "name";

    // 记录查询隐藏域的上级ID
    public static final String HIDDEN_SUP_ID = "supId";

    /* COL类型 */
    // 数字 需要加format属性
    public static final String EXCEL_TYPE_0 = "0";

    // String 字符串
    public static final String EXCEL_TYPE_1 = "1";

    // 下拉框 class属性 id:表示隐藏的id列 name:表示显示的name列
    public static final String EXCEL_TYPE_2 = "2";

    // 空
    public static final String EXCEL_TYPE_3 = "3";

    // 布尔
    public static final String EXCEL_TYPE_4 = "4";

    // 错误
    public static final String EXCEL_TYPE_5 = "5";

    // 时间 需要加format属性
    public static final String EXCEL_TYPE_6 = "6";

    /* EXCEL模板XML文件对应字段 */
    // 根节点信息
    public static final String EXCEL_ROOT = "excel";

    // 头信息
    public static final String EXCEL_THEAD = "thead";
    
    // 列信息
    public static final String EXCEL_COL = "col";

    // 用于文件名
    public static final String EXCEL_NAME = "excelName";

    // 放入服务端的文件名
    public static final String EXCEL_FILE_NAME = "file";

    // SHEET名称
    public static final String EXCEL_SHEET_NAME = "name";

    // index 用于下拉框的命名和设置下拉框的位置 所以需要和实际导出的excle模板保持一致
    public static final String EXCEL_COL_INDEX = "index";

    // 列宽
    public static final String EXCEL_COL_WIDTH = "width";

    // 列的类型
    public static final String EXCEL_COL_TYPE = "type";

    // 列名
    public static final String EXCEL_COL_KEY = "key";

    // 列的中文名
    public static final String EXCEL_COL_VALUE = "value";

    // 格式化 TYPE=0
    public static final String EXCEL_COL_FORMAT = "format";

    // 格式化 TYPE=0 format为#的时候最小整数
    public static final String EXCEL_COL_FORMAT_MIN = "formatMin";

    // 格式化 TYPE=0 format为#的时候最大整数
    public static final String EXCEL_COL_FORMAT_MAX = "formatMax";

    // 下拉框为NAME 还是作为ID
    public static final String EXCEL_COL_CLASS = "class";

    // 下拉框为NAME
    public static final String EXCEL_COL_CLASS_NAME = "name";

    // 下拉框为NAMEID
    public static final String EXCEL_COL_CLASS_ID = "id";

    // 级联下拉框 上级
    public static final String EXCEL_COL_SUP = "sup";

    // 级联下拉框
    public static final String EXCEL_COL_SON = "son";

    // 级联下拉框
    public static final String EXCEL_COL_NAME = "name";

    // 隐藏存放下拉框数据的sheet名称
    public static final String EXCEL_HIDDEN_SHEET = "DataDictionary";

    // 隐藏存放级联下拉框数据的sheet名称
    public static final String EXCEL_CASCADE_HIDDEN_SHEET = "SupAndSon";

    // type 为2时，COMBOBOX的ID
    public static final String EXCEL_COL_COMBONAME = "comboName";

    // 级联下拉框会加上该字段，存放的值为二级下拉框的上级下拉框的INDEX
    public static final String EXCEL_COL_CASCADESUP = "cascadeSup";

    // 级联隐藏域合并列中的分隔符区隔
    public static final String EXCEL_SPLIT_CASCADE = "卐";

    /* ==========END excel模板相关常量================ */
}
