package xn.pigfarm.service.basicinfo;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.backend.CdCodeListModel;
import xn.pigfarm.model.backend.TemplateModel;
import xn.pigfarm.model.basicinfo.RoleModel;

public interface IRoleManage {
    // /**
    // * 根据公司id查询角色
    // * @param id
    // * @return
    // */
    // public List<RoleManageView> searchRoleByFarmId();
	
	/**
	 * 根据公司id查询角色 （分页）
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BasePageInfo  searchRoleByFarmIdPage() throws Exception;
	
    /**
     * 人员管理中所用 根据公司id查询角色 （不分页）
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchRoleByFarmIdToList() throws Exception;

	/**
	 * 角色CU操作 
	 * @param employee
	 * @param usrMagView
	 * @throws Exception
	 */
	public  void  editRole(RoleModel roleModle,String jsonObj) throws Exception;
	
	
	
	/**
	 * 角色批量删除
	 * @param ids
	 * @throws Exception
	 */
	public  void  deletesRole(long[] ids) throws Exception;
	
	
	/***
	 * 根据公司id,typeCode查询
	 * @param typeCode
	 * @param farmId
	 * @return
	 */
	public List<CdCodeListModel>  searchRoleType(String typeCode, long farmId) throws Exception;
	
    /**
     * @Description: 为角色管理查询模板类型下拉框
     * @author zhangjs
     * @param inputMap
     * @param farmId
     * @param roleType
     * @return
     * @throws Exception
     */
    public List<TemplateModel> searchTemplateTypeForRole(Map<String, Object> inputMap) throws Exception;
	
	/**
	 * 根据farmId,roleType查菜单
	 * @param farmId
	 * @param roleType
	 * @return
	 */
	public List<Map<String, Object>> searchMenuView(long farmId,long roleId,long templateId) throws Exception;
	
	
	
	/**
	 * 设置权限
	 * @param farmId
	 * @param roleType
	 * @return
	 */
    public void editLimit(long roleId, String jsonObj) throws Exception;
}
