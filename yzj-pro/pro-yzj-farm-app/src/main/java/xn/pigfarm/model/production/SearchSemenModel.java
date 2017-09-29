package xn.pigfarm.model.production;

import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * 表：
 */
public class SearchSemenModel extends BaseDataModel implements java.io.Serializable {

    private static final long serialVersionUID = -8722033129640546819L;

    // 查询类型： 1.模糊查询且分页（只有在输入耳号时执行） 2.条件查询
    private final String D_QueryType = "queryType";

    // 模糊查询参数; ctrl + v 时将耳牌号以逗号分隔
    private final String D_Query = "query";

    // 用于筛选已选定精液批号
    private final String D_SememIds = "sememIds";

    // 猪场ID
    private final String D_FarmId = "farmId";

    // 配种方式 1.人工授精 2.本交
    private final String D_BreedType = "breedType";

    // 配种日期
    private final String D_BreedDate = "breedDate";

    // 创建人
    private final String D_CreateId = "createId";

    // 起始页码
    private final String D_Offset = "offset";

    // 每页显示记录数，为1时为所有
    private final String D_Pagesize = "pagesize";

    // 事件名称
    private final String D_EventName = "eventName";

    // 总数
    private final String D_TotalCount = "totalCount";

    // 错误代码
    private final String D_ErrorCode = "errorCode";

    // 错误信息
    private final String D_ErrorMessage = "errorMessage";

    // 公司ID
    private final String D_CompanyId = "companyId";

    public String getQueryType() {
        return getString(D_QueryType);
    }

    public void setQueryType(String queryType) {
        set(D_QueryType, queryType);
    }

    public String getQuery() {
        return getString(D_Query);
    }

    public void setQuery(String query) {
        set(D_Query, query);
    }

    public String getSememIds() {
        return getString(D_SememIds);
    }

    public void setSememIds(String sememIds) {
        set(D_SememIds, sememIds);
    }

    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    public void setFarmId(Long farmId) {
        set(D_FarmId, farmId);
    }

    public String getBreedType() {
        return getString(D_BreedType);
    }

    public void setBreedType(String breedType) {
        set(D_BreedType, breedType);
    }

    public Date getBreedDate() {
        return getDate(D_BreedDate);
    }

    public void setBreedDate(Date breedDate) {
        set(D_BreedDate, breedDate);
    }

    public Long getCreateId() {
        return getLong(D_CreateId);
    }

    public void setCreateId(Long createId) {
        set(D_CreateId, createId);
    }

    public Long getOffset() {
        return getLong(D_Offset);
    }

    public void setOffset(Long offset) {
        set(D_Offset, offset);
    }

    public Long getPagesize() {
        return getLong(D_Pagesize);
    }

    public void setPagesize(Long pagesize) {
        set(D_Pagesize, pagesize);
    }

    public String getEventName() {
        return getString(D_EventName);
    }

    public void setEventName(String eventName) {
        set(D_EventName, eventName);
    }

    public Long getTotalCount() {
        return getLong(D_TotalCount);
    }

    public void setTotalCount(Long totalCount) {
        set(D_TotalCount, totalCount);
    }

    public String getErrorCode() {
        return getString(D_ErrorCode);
    }

    public void setErrorCode(String errorCode) {
        set(D_ErrorCode, errorCode);
    }

    public String getErrorMessage() {
        return getString(D_ErrorMessage);
    }

    public void setErrorMessage(String errorMessage) {
        set(D_ErrorMessage, errorMessage);
    }

    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    public void setCompanyId(Long companyId) {
        set(D_CompanyId, companyId);
    }

    @Override
    public List<String> getPropertes() {
        if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
            setPropertes(D_QueryType);
            setPropertes(D_Query);
            setPropertes(D_SememIds);
            setPropertes(D_FarmId);
            setPropertes(D_BreedType);
            setPropertes(D_BreedDate);
            setPropertes(D_CreateId);
            setPropertes(D_Offset);
            setPropertes(D_EventName);
            setPropertes(D_TotalCount);
            setPropertes(D_ErrorCode);
            setPropertes(D_ErrorMessage);
            setPropertes(D_CompanyId);
        }
        return super.getPropertes();
    }

}
