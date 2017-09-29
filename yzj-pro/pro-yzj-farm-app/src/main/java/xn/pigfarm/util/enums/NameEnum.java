package xn.pigfarm.util.enums;

import xn.core.data.enums.interfaces.INameEnum;

public enum NameEnum implements INameEnum {

    /* 不需要区分猪场ID 模板 */
    PIG_CLASS_NAME("pigClass", "pigClassName", "CD_L_PIG_CLASS", "PIG_CLASS_NAME", false, ""),
    
    /* 需要区分猪场ID 模板 */
    EMPLOYEE_NAME("worker,createId", "workerName,createName", "HR_O_EMPLOYEE", "EMPLOYEE_NAME", true, ""),

    WAREHOUSE_NAME("warehouseId", "warehouseName", "SC_M_WAREHOUSE", "WAREHOUSE_NAME", false, ""),

    ROLE_NAME("roleId", "roleName", "CD_O_ROLE", "ROLE_NAME", false, "");

    private final String id;

    // 默认页面显示名称
    private final String name;

    // 需要查询的表 对应sys_l_cache_tables表中TABLE_NAME
    private final String tableName;

    // 需要查询对应的名称 一般为sys_l_cache_tables中CACHE_COLUMNS的_NAME
    private final String columnName;

    // 表缓存时是否根据farmId进行分隔 sys_l_cache_tables中FARM_FLAG
    private final boolean farmFlag;

    // 查询索引，codeName特殊，是根据索引查询 sys_l_cache_tables中INDEXES其中一个
    private final String indexe;

    NameEnum(String id, String name, String tableName, String columnName, boolean farmFlag, String indexe) {
        this.id = id;
        this.name = name;
        this.tableName = tableName;
        this.columnName = columnName;
        this.farmFlag = farmFlag;
        this.indexe = indexe;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public boolean getFarmFlag() {
        return farmFlag;
    }

    @Override
    public String getIndexe() {
        return indexe;
    }


}
