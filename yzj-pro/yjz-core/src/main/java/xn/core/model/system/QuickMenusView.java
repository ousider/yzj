package xn.core.model.system;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-6-29 14:02:09
 *       remarks :根据用户ID查询快捷菜单
 */
public class QuickMenusView  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 4541965419665181423L;

    // 行号: 系统保留字段，标识一条数据记录。
    private final String D_RowId="rowId";

    // 排序号
    private final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private final String D_DeletedFlag="deletedFlag";

    // 备注
    private final String D_Notes="notes";

    // 对应的公司ID
    private final String D_FarmId="farmId";
	 //companyId 
    private final String D_CompanyId="companyId";
	 //userId 
    private final String D_UserId="userId";
	 //moduleId 
    private final String D_ModuleId="moduleId";

    // 功能模块名
    private final String D_Component="component";

    // 模块名：能够描述此模块信息的名称。
    private final String D_ModuleName="moduleName";

    // 模块访问地址
    private final String D_ModuleUrl="moduleUrl";

    // 图标地址
    private final String D_IconCls="iconCls";
	

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
     * 获取行号: 系统保留字段，标识一条数据记录。
     * 
     * @return ROW_ID
     */
    public Long getRowId() {
        return getLong(D_RowId);
    }

    /**
     * 设置排序号
     * 
     * @param SORT_NBR
     */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
     * 获取排序号
     * 
     * @return SORT_NBR
     */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
     * 设置表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置[0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取[0]-未删除;[1]-逻辑删除
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
     * 设置备注
     * 
     * @param NOTES
     */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
    /**
     * 获取备注
     * 
     * @return NOTES
     */
    public String getNotes() {
        return getString(D_Notes);
    }

    /**
     * 设置对应的公司ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取对应的公司ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置companyId
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取companyId
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置userId
     * 
     * @param USER_ID
     */
	public void setUserId(Long value) {
        set(D_UserId,value);
    }
	
	/**
     * 获取userId
     * 
     * @return USER_ID
     */
    public Long getUserId() {
        return getLong(D_UserId);
    }

    /**
     * 设置moduleId
     * 
     * @param MODULE_ID
     */
	public void setModuleId(Long value) {
        set(D_ModuleId,value);
    }
	
	/**
     * 获取moduleId
     * 
     * @return MODULE_ID
     */
    public Long getModuleId() {
        return getLong(D_ModuleId);
    }

	/**
     * 设置功能模块名
     * 
     * @param COMPONENT
     */
	public void setComponent(String value) {
        set(D_Component,value);
    }
	
    /**
     * 获取功能模块名
     * 
     * @return COMPONENT
     */
    public String getComponent() {
        return getString(D_Component);
    }

	/**
     * 设置模块名：能够描述此模块信息的名称。
     * 
     * @param MODULE_NAME
     */
	public void setModuleName(String value) {
        set(D_ModuleName,value);
    }
	
    /**
     * 获取模块名：能够描述此模块信息的名称。
     * 
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return getString(D_ModuleName);
    }

	/**
     * 设置模块访问地址
     * 
     * @param MODULE_URL
     */
	public void setModuleUrl(String value) {
        set(D_ModuleUrl,value);
    }
	
    /**
     * 获取模块访问地址
     * 
     * @return MODULE_URL
     */
    public String getModuleUrl() {
        return getString(D_ModuleUrl);
    }

	/**
     * 设置图标地址
     * 
     * @param ICON_CLS
     */
	public void setIconCls(String value) {
        set(D_IconCls,value);
    }
	
    /**
     * 获取图标地址
     * 
     * @return ICON_CLS
     */
    public String getIconCls() {
        return getString(D_IconCls);
    }
	
    public List<String> getPropertes() {
        if (super.getPropertes() == null || super.getPropertes().isEmpty()) {

            setPropertes(D_RowId);
            setPropertes(D_SortNbr);
            setPropertes(D_Status);
            setPropertes(D_DeletedFlag);
            setPropertes(D_Notes);
            setPropertes(D_FarmId);
            setPropertes(D_CompanyId);
            setPropertes(D_UserId);
            setPropertes(D_ModuleId);
            setPropertes(D_Component);
            setPropertes(D_ModuleName);
            setPropertes(D_ModuleUrl);
            setPropertes(D_IconCls);

        }
        return super.getPropertes();
    }

}




