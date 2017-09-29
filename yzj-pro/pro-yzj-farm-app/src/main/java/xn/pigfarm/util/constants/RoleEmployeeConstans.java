package xn.pigfarm.util.constants;

import xn.core.util.MD5Util;

/**
 * @Description: 角色相关常量
 * @author Administrator
 * @date 2016年9月12日 下午1:35:13
 */
public class RoleEmployeeConstans {
    private RoleEmployeeConstans() {

    }

    /* =================角色类型=================== */
    // 开发者
    public static final String ROLE_OF_DEVELOPER = "1";

    // 平台系统管理员
    public static final String ROLE_OF_PLATFORM = "2";

    // 企业系统管理员
    public static final String ROLE_OF_ENTERPRISE = "3";

    // 普通角色
    public static final String ROLE_OF_COMMON = "4";

    // 同步角色
    public static final String ROLE_OF_ANSYS = "5";

    /* =================角色类型end================ */
    /* =================管理员信息================= */
    // 管理员账号
    public static final String ADMIN_USERNAME = "admin";

    // 管理员昵称
    public static final String ADMIN_NICKNAME = "系统管理员";

    // 管理员初始密码
    public static final String ADMIN_PASSWORD = MD5Util.generateMD5code("123456");

    // 管理员模板id
    public static final Long ADMIN_MODULE_ID = 59L;

    // 10000管理员角色id
    public static final Long ADMIN_ROLE_ID = 3L;

    // 10000管理员人员id
    public static final Long ADMIN_EMPLOYEE_ID = 3L;
    /* =================管理员信息end=============== */

    // 初始化密码
    public static final String INIT_PASSWORD = "Y";

    // 不初始化密码
    public static final String NOT_INIT_PASSWORD = "N";

    /* ==================人员类型==================== */
    // 内部员工
    public static final String EMPLOYEE_OF_INSIDER = "1";

    // 外部员工
    public static final String EMPLOYEE_OF_OUTSIDER = "2";

    // 其他员工
    public static final String EMPLOYEE_OF_OTHERS = "3";

    // 同步员工
    public static final String EMPLOYEE_OF_ANSYS = "4";

    // 管理员
    public static final String EMPLOYEE_OF_ADMIN = "9";
    /* ==================人员类型end================= */
}
