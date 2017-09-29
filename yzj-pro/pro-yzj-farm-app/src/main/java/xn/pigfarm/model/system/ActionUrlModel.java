package xn.pigfarm.model.system;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-10-11 11:26:52
 *       表：SYS_L_ACTION_URL
 */
public class ActionUrlModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -9058950946588106292L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 页面ID
    private static final String D_ModuleId="moduleId";

    // 页面名称
    private static final String D_ModuleName="moduleName";

    // 页面地址
    private static final String D_ModuleUrl="moduleUrl";

    // 访问人员
    private static final String D_Actioner="actioner";

    // 访问时间
    private static final String D_ActionTime="actionTime";

    // 猪场ID
    private static final String D_FarmId="farmId";

    // 公司ID
    private static final String D_CompanyId="companyId";
	

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
     * 设置页面ID
     * 
     * @param MODULE_ID
     */
	public void setModuleId(Long value) {
        set(D_ModuleId,value);
    }
	
	/**
     * 获取页面ID
     * 
     * @return MODULE_ID
     */
    public Long getModuleId() {
        return getLong(D_ModuleId);
    }

	/**
     * 设置页面名称
     * 
     * @param MODULE_NAME
     */
	public void setModuleName(String value) {
        set(D_ModuleName,value);
    }
	
    /**
     * 获取页面名称
     * 
     * @return MODULE_NAME
     */
    public String getModuleName() {
        return getString(D_ModuleName);
    }

	/**
     * 设置页面地址
     * 
     * @param MODULE_URL
     */
	public void setModuleUrl(String value) {
        set(D_ModuleUrl,value);
    }
	
    /**
     * 获取页面地址
     * 
     * @return MODULE_URL
     */
    public String getModuleUrl() {
        return getString(D_ModuleUrl);
    }

    /**
     * 设置访问人员
     * 
     * @param ACTIONER
     */
	public void setActioner(Long value) {
        set(D_Actioner,value);
    }
	
	/**
     * 获取访问人员
     * 
     * @return ACTIONER
     */
    public Long getActioner() {
        return getLong(D_Actioner);
    }

	/**
     * 设置访问时间
     * 
     * @param ACTION_TIME
     */
	public void setActionTime(Timestamp value) {
        set(D_ActionTime,value);
    }
	
    /**
     * 获取访问时间
     * 
     * @return ACTION_TIME
     */
    public Timestamp getActionTime() {
        return getTimestamp(D_ActionTime);
    }

    /**
     * 设置猪场ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取猪场ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置公司ID
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取公司ID
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("moduleId");
        propertes.add("moduleName");
        propertes.add("moduleUrl");
        propertes.add("actioner");
        propertes.add("actionTime");
        propertes.add("farmId");
        propertes.add("companyId");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




