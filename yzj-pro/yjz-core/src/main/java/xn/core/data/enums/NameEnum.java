package xn.core.data.enums;

import xn.core.data.enums.interfaces.INameEnum;

/**
 * @Description:需要赋值名称的枚举
 * @author Zhangjc
 * @date 2016年5月13日 下午1:44:32
 */
public enum NameEnum implements INameEnum {

    /* 不需要区分猪场ID */
    PIG_CLASS_NAME("pigClass", "pigClassName", "CD_L_PIG_CLASS", "PIG_CLASS_NAME", false,""),
    HOUSE_TYPE_NAME("houseType", "houseTypeName", "CD_L_PIG_HOUSE", "HOUSE_TYPE_NAME", false,""), 
    BREED_NAME("breedId", "breedName", "CD_L_BREED", "BREED_NAME", false, ""),
    COMPANY_NAME("companyId", "companyName", "HR_M_COMPANY", "COMPANY_NAME", false, ""),
    FARM_NAME("farmId", "farmName", "HR_M_COMPANY", "SORT_NAME", false, ""),
    BCODE_NAME("bcodeTypeId", "bcodeName", "CD_L_BCODE_TYPE", "BCODE_NAME", false, ""),
    MODULE_NAME("moduleId", "moduleName", "CD_O_MODULE", "MODULE_NAME", false, ""),
    TEMPLATE_NAME("templateId", "templateName", "CD_O_TEMPLATE", "TEMPLATE_NAME", false, ""),
    // 特殊，显示名称name在CodeEnum中配置
    CODE_NAME("codeValue", "", "CD_L_CODE_LIST", "codeName", false, "typeCode,codeValue"),
    
    /* 需要区分猪场ID */
    EMPLOYEE_NAME("worker,createId", "workerName,createName", "HR_O_EMPLOYEE", "EMPLOYEE_NAME", true, ""),
    WORKER_NAME("worker", "workerName", "HR_O_EMPLOYEE", "EMPLOYEE_NAME", true, ""), 
    CREATE_NAME("createId", "createName", "HR_O_EMPLOYEE","EMPLOYEE_NAME", true, ""),
    HOUSE_NAME("houseId", "houseName", "PP_O_HOUSE", "HOUSE_NAME", true,""), 
    LINE_NAME("lineId", "lineName", "PP_O_LINE", "LINE_NAME", true,""), 
    SWINERY_NAME("swineryId", "swineryName", "PP_M_SWINERY", "SWINERY_NAME", true,""),
    MATERIAL_NAME("materialId", "materialName", "CD_M_MATERIAL", "MATERIAL_NAME", true,""), 
    /* EAR_BRAND("earCodeId", "earBrand", "PP_L_EAR_CODE", "EAR_BRAND", true,""), */
    DEPT_NAME("deptId", "deptName", "HR_O_DEPT", "DEPT_NAME", true, ""),
    POST_NAME("postId", "postName", "HR_O_POST", "JOB_TITLE", true, ""),
    PIGPEN_NAME("pigpenId", "pigpenName", "PP_O_PIGPEN", "PIGPEN_NAME", true, "");

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
