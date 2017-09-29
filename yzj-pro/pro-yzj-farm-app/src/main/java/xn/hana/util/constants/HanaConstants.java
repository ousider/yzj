package xn.hana.util.constants;

public class HanaConstants {
    private HanaConstants() {

    }

    // 业务代码：主数据 - 分公司
    public static final String MTC_ITFC_OBJ_CODE_1010 = "1010";

    // 业务代码：主数据 - 部门/猪场
    public static final String MTC_ITFC_OBJ_CODE_1020 = "1020";

    // 业务代码：主数据 - 栋舍
    public static final String MTC_ITFC_OBJ_CODE_1030 = "1030";

    // 业务代码：主数据 - 猪只品种清单
    public static final String MTC_ITFC_OBJ_CODE_1040 = "1040";

    // 业务代码：主数据 - 仓库
    public static final String MTC_ITFC_OBJ_CODE_1050 = "1050";

    // 业务代码：主数据 - 物料
    public static final String MTC_ITFC_OBJ_CODE_1060 = "1060";

    // 业务代码：主数据 - 客户
    public static final String MTC_ITFC_OBJ_CODE_1070 = "1070";

    // 业务代码：主数据 - 供应商
    public static final String MTC_ITFC_OBJ_CODE_1080 = "1080";

    // 业务代码：采购订单
    public static final String MTC_ITFC_OBJ_CODE_2010 = "2010";

    // 业务代码：采购入库 - 物料
    public static final String MTC_ITFC_OBJ_CODE_2020 = "2020";

    // 业务代码：采购发票
    public static final String MTC_ITFC_OBJ_CODE_2025 = "2025";

    // 业务代码：采购退货
    public static final String MTC_ITFC_OBJ_CODE_2030 = "2030";

    // 业务代码：生产领用
    public static final String MTC_ITFC_OBJ_CODE_2040 = "2040";

    // 业务代码：生产领用退料
    public static final String MTC_ITFC_OBJ_CODE_2050 = "2050";

    // 业务代码：库存报废
    public static final String MTC_ITFC_OBJ_CODE_2060 = "2060";

    // 业务代码：库存反报废
    public static final String MTC_ITFC_OBJ_CODE_2070 = "2070";

    // 业务代码：库存盘点
    // public static final String MTC_ITFC_OBJ_CODE_2080 = "2080";

    // 业务代码：库存调拨
    public static final String MTC_ITFC_OBJ_CODE_2090 = "2090";

    // 业务代码：猪只销售(实时)
    public static final String MTC_ITFC_OBJ_CODE_2100 = "2100";

    // 业务代码：猪只采购(实时)
    public static final String MTC_ITFC_OBJ_CODE_2110 = "2110";

    // 业务代码：后备转生产 - A 应收发票
    public static final String MTC_ITFC_OBJ_CODE_3010 = "3010";

    // 业务代码：后备转生产 - B 固定资产卡片
    public static final String MTC_ITFC_OBJ_CODE_3011 = "3011";

    // 业务代码：生产猪采购 - A 应收发票
    public static final String MTC_ITFC_OBJ_CODE_3020 = "3020";

    // 业务代码：生产猪采购 - B 固定资产卡片
    public static final String MTC_ITFC_OBJ_CODE_3021 = "3021";

    // 业务代码：生产猪每月存栏位置
    public static final String MTC_ITFC_OBJ_CODE_3030 = "3030";

    // 业务代码：生产猪死亡
    public static final String MTC_ITFC_OBJ_CODE_3040 = "3040";

    // 业务代码：生产猪淘汰销售
    public static final String MTC_ITFC_OBJ_CODE_3050 = "3050";

    // 业务代码：商品猪存栏日报
    public static final String MTC_ITFC_OBJ_CODE_9997 = "9997";

    // 业务代码：期间存栏变动汇总
    public static final String MTC_ITFC_OBJ_CODE_9998 = "9998";

    // 业务代码：妊娠母猪状态汇总
    public static final String MTC_ITFC_OBJ_CODE_9999 = "9999";

    // 优先级(越小等级越高)：1
    public static final int MTC_ITFC_PRIORITY_1 = 1;

    // 优先级(越小等级越高)：2
    public static final int MTC_ITFC_PRIORITY_2 = 2;

    // 优先级(越小等级越高)：3
    public static final int MTC_ITFC_PRIORITY_3 = 3;

    // 优先级(越小等级越高)：4
    public static final int MTC_ITFC_PRIORITY_4 = 4;

    // 优先级(越小等级越高)：5
    public static final int MTC_ITFC_PRIORITY_5 = 5;

    // 处理区分：A - 新增
    public static final String MTC_ITFC_TRANS_TYPE_A = "A";

    // 处理区分：U - 更新
    public static final String MTC_ITFC_TRANS_TYPE_U = "U";

    // 处理区分：D - 取消
    public static final String MTC_ITFC_TRANS_TYPE_D = "D";

    // 处理区分：C - 关闭
    public static final String MTC_ITFC_TRANS_TYPE_C = "C";

    // 原始单据编号：D 时，写入被取消的单据编号
    public static final String MTC_ITFC_BASE_ENTRY_D = "D";

    // 同步状态：N – 未同步
    public static final String MTC_ITFC_STATUS_N = "N";

    // // 同步状态：P – 处理中
    // public static final String MTC_ITFC_STATUS_P = "P";
    //
    // // 同步状态：Y – 已同步
    // public static final String MTC_ITFC_STATUS_Y = "Y";
    //
    // // 同步状态：E – 已同步
    // public static final String MTC_ITFC_STATUS_E = "E";

    // 业务类型：PO - 常规采购
    public static final String MTC_ITFC_SALES_TYPE_PO = "PO";

    // 业务类型：PI - 内部采购
    public static final String MTC_ITFC_SALES_TYPE_PI = "PI";

    // 核算对象：A - 商品猪
    public static final String MTC_COLLECTION_A = "A";

    // 核算对象：B - 生产猪
    public static final String MTC_COLLECTION_B = "B";

    // 核算对象：C - 后备猪
    public static final String MTC_COLLECTION_C = "C";

    // 销售类型：SO - 常规销售
    public static final String MTC_SALES_TYPE_SO = "SO";

    // 销售类型：SI - 内部销售
    public static final String MTC_SALES_TYPE_SI = "SI";

    // 销售类型：PI - 内部采购
    public static final String MTC_SALES_TYPE_PI = "PI";

    // 销售类型：SZ - 自宰
    public static final String MTC_SALES_TYPE_SZ = "SZ";

    // 销售类型: PP - 生产猪采购
    public static final String MTC_SALES_TYPE_PP = "PP";

    // 销售类型: PS - 生产猪淘汰/销售
    public static final String MTC_SALES_TYPE_PS = "PS";

    // 销售类型: SP - 后备转生产
    public static final String MTC_SALES_TYPE_SP = "SP";

    // 销售类型: PD - 生产猪死亡
    public static final String MTC_SALES_TYPE_PD = "PD";

    // 苗猪
    public static final String MTC_GRADE_PIGGY_PIG = "A";

    // 肥猪
    public static final String MTC_GRADE_HOG_PIG = "B";

    // 残次猪
    public static final String MTC_GRADE_IMPERFECT_PIG = "C";

    // 701001 - 商品猪 - 仔猪
    public static final String MTC_ITEM_CODE_PIGLET = "701001";

    // 701002 - 商品猪 - 保育猪
    public static final String MTC_ITEM_CODE_NURSING_PIG = "701002";

    // 701003 - 商品猪 - 育肥猪
    public static final String MTC_ITEM_CODE_FATTENING_PIG = "701003";

    // 701004 - 商品猪 - 培育猪
    public static final String MTC_ITEM_CODE_CULTIVATE_PIG = "701004";

    // 701005 - 商品猪 - 后备种猪
    public static final String MTC_ITEM_CODE_RESERVE_PIG = "701005";

    // 702001 - 生产猪 - 生产公猪
    public static final String MTC_ITEM_CODE_PRODUCT_BOAR = "702001";

    // 702002 - 生产猪 - 生产母猪
    public static final String MTC_ITEM_CODE_PRODUCT_SOW = "702002";

    // 猪只性别固定值 - 公猪
    public static final String MTC_ITEM_SEX_BOAR = "131";

    // 猪只性别固定值 - 母猪
    public static final String MTC_ITEM_SEX_SOW = "132";

    // 资产类型 - 公猪
    public static final String MTC_TYPE_BOAR = "Z162101";

    // 资产类型 - 母猪
    public static final String MTC_TYPE_SOW = "Z162102";

    // 商品猪
    public static final String MTC_PigType_SP = "S";

    // 后备猪
    public static final String MTC_PigType_HB = "H";

    // 采购类型：购买(有价格)：A
    public static final String MTC_PURTYPE_A = "A";

    // 采购类型：赠送(无价格)：B
    public static final String MTC_PURTYPE_B = "B";

    // 采购类型：促销返利(无价格)：C
    public static final String MTC_PURTYPE_C = "C";

}
