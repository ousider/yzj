package xn.pigfarm.exception.basicInfo;

import xn.core.exception.IException;

/**
 * @Description: 基础信息模块异常 对应模块 02
 * @author zhangjs
 * @date 2016年8月21日 下午12:35:00
 */
public enum BasicInfoException implements IException {
    
    /**
     * 错误编码示例：AABBCC
     * AA:10-前台;11-后台
     * BB:对应模块 通用00 backend 01
     * CC:错误序号
     */
    UPDATE_CUSSUP_CREATE_TYPE_ERROR("只可修改自建的【%s】", 100201), DELETE_CUSSUP_NO_ID_ERROR("未选需要删除记录", 100202), DELETE_CUSSUP_HAS_RECORDS_ERROR(
            "编码【%s】的自建【%s】不可删除，需先去【%s】页面删除对应的【%s】", 100203), DELETE_EMPLOYEE_POST_ERROR("该岗位下尚有人员，请先删除该岗位下所有人员", 100204), DELETE_DEPT_POST_ERROR(
                    "该部门下尚有岗位，请先删除该部门下的所有岗位", 100205), ROLE_OF_ENTERPRISE_IS_EXISTS("系统角色已存在！", 100206), NO_SUPER_COMPANY_ERROR("上级公司必须选择！", 100207),
    SELL_SUP_NOT_CREATE("该销售单据的供应商【%s】未在本场建立供应商信息，请先添加！"), PARPER_RECORD_FILE_IS_EMPTY("证照信息中,第【%s】行的【上传文件】不能为空！", 100030);
    

    private ExceptionLevel level = null;

    private String message = null;

    private long errorCode;

    BasicInfoException(String message) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
    }

    BasicInfoException(String message, ExceptionLevel level) {
        this.message = message;
        this.level = level;
    }

    BasicInfoException(String message, long errorCode) {
        this.message = message;
        this.level = ExceptionLevel.ERROR;
        this.errorCode = errorCode;
    }

    BasicInfoException(String message, ExceptionLevel level, long errorCode) {
        this.message = message;
        this.level = level;
        this.errorCode = errorCode;
    }

    @Override
    public String getCode() {
        return toString();
    }

    @Override
    public ExceptionLevel getLevel() {
        return this.level;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public long getErrorCode() {
        return this.errorCode;
    }

}
