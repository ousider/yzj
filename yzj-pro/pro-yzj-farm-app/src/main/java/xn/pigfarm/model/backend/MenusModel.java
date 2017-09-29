package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:15:42
 *       表：CD_R_MENUS
 */
public class MenusModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 617868731772418664L;

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 关联ID
    private static final String D_CorrelationId="correlationId";

    // 根目录编号
    private static final String D_SubsystemId="subsystemId";

    // 菜单级别
    private static final String D_LevelNum="levelNum";

    // 上级菜单ID
    private static final String D_ParentId="parentId";
	 //moduleId 
    private static final String D_ModuleId="moduleId";
	 //templateId 
    private static final String D_TemplateId="templateId";
	

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
     * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @param ORIGIN_FLAG
     */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
    /**
     * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @return ORIGIN_FLAG
     */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
     * 设置数据来源应用的代码
     * 
     * @param ORIGIN_APP
     */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
    /**
     * 获取数据来源应用的代码
     * 
     * @return ORIGIN_APP
     */
    public String getOriginApp() {
        return getString(D_OriginApp);
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
     * 设置关联ID
     * 
     * @param CORRELATION_ID
     */
	public void setCorrelationId(Long value) {
        set(D_CorrelationId,value);
    }
	
	/**
     * 获取关联ID
     * 
     * @return CORRELATION_ID
     */
    public Long getCorrelationId() {
        return getLong(D_CorrelationId);
    }

    /**
     * 设置根目录编号
     * 
     * @param SUBSYSTEM_ID
     */
	public void setSubsystemId(Long value) {
        set(D_SubsystemId,value);
    }
	
	/**
     * 获取根目录编号
     * 
     * @return SUBSYSTEM_ID
     */
    public Long getSubsystemId() {
        return getLong(D_SubsystemId);
    }

    /**
     * 设置菜单级别
     * 
     * @param LEVEL_NUM
     */
	public void setLevelNum(Long value) {
        set(D_LevelNum,value);
    }
	
	/**
     * 获取菜单级别
     * 
     * @return LEVEL_NUM
     */
    public Long getLevelNum() {
        return getLong(D_LevelNum);
    }

    /**
     * 设置上级菜单ID
     * 
     * @param PARENT_ID
     */
	public void setParentId(Long value) {
        set(D_ParentId,value);
    }
	
	/**
     * 获取上级菜单ID
     * 
     * @return PARENT_ID
     */
    public Long getParentId() {
        return getLong(D_ParentId);
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
     * 设置templateId
     * 
     * @param TEMPLATE_ID
     */
	public void setTemplateId(Long value) {
        set(D_TemplateId,value);
    }
	
	/**
     * 获取templateId
     * 
     * @return TEMPLATE_ID
     */
    public Long getTemplateId() {
        return getLong(D_TemplateId);
    }
	
	
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_SortNbr);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_OriginFlag);
	        setPropertes(D_OriginApp);
	        setPropertes(D_Notes);
	        setPropertes(D_CorrelationId);
	        setPropertes(D_SubsystemId);
	        setPropertes(D_LevelNum);
	        setPropertes(D_ParentId);
	        setPropertes(D_ModuleId);
	        setPropertes(D_TemplateId);
	    }
	    return super.getPropertes();
	}

}




