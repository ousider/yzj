package xn.pigfarm.util.enums;

import xn.core.data.enums.interfaces.ICodeEnum;

/**
 * @Description:获取下来cdcode名称
 * @author Zhangjc
 * @date 2016年5月13日 下午1:44:32
 */
public enum CodeEnum implements ICodeEnum {

    // 模板
    EMPLOYEE_TYPE("EMPLOYEE_TYPE", "employeeType", "employeeTypeName"),
    EMERGENCY_TYPE("EMERGENCY_TYPE", "emergencyType", "emergencyTypeName"),
    APPLY_TYPE("APPLY_TYPE", "applyType", "applyTypeName"),
    SUPPLYCHIAN_EVENT_CODE("SUPPLYCHIAN_EVENT_CODE", "supplychianEventCode","supplychianEventCode");

    private final String typeCode;
    private final String codeId;
    private final String codeName;

    CodeEnum(String typeCode, String id, String codeName) {
        this.codeId = id;
        this.typeCode = typeCode;
        this.codeName = codeName;
    }

    @Override
    public String getCodeId() {
        return codeId;
    }

    @Override
    public String getTypeCode() {
        return typeCode;
    }

    @Override
    public String getCodeName() {
        return codeName;
    }
}
