package xn.pigfarm.controller.basicinfo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.model.backend.CdCodeListModel;
import xn.pigfarm.model.basicinfo.RoleModel;
import xn.pigfarm.service.basicinfo.IRoleManage;

@Controller
@RequestMapping("/RoleManage")
public class RoleManageController extends BaseController {

	@Autowired
	private IRoleManage iRoleManage;

	/**
	 * 角色分页查询
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchRoleByFarmIdPage")
	@ResponseBody
	public Map<String, Object> searchRoleByFarmIdPage(HttpServletRequest request) throws Exception {

		return getReturnMap(iRoleManage.searchRoleByFarmIdPage());
	}

	   /**
     * 角色不分页查询
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchRoleByFarmIdToList")
    @ResponseBody
    public Map<String, Object> searchRoleByFarmIdToList(HttpServletRequest request) throws Exception {

        return getReturnMap(iRoleManage.searchRoleByFarmIdToList());
    }

    
	

	/**
	 * 角色CU操作
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editRole")
	@ResponseBody
	public Map<String, Object> editRole(HttpServletRequest request) throws Exception {

        iRoleManage.editRole(getBean(RoleModel.class), getDetialListStr());
        basicRefresh();
		return getReturnMap();
	}

	/**
	 * 角色批量删除
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deletesRole")
	@ResponseBody
	public Map<String, Object> deletesRole(HttpServletRequest request) throws Exception {

		iRoleManage.deletesRole(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
	 * 根据公司Id查询角色类型
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchRoleType")
	@ResponseBody
	public List<CdCodeListModel> searchRoleType(HttpServletRequest request) throws Exception{

		return iRoleManage.searchRoleType(getString("typeCode"), getLong("farmId"));
	}
	
    /**
     * @Description: 为角色管理查询模板类型下拉框
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchTemplateTypeForRole")
    @ResponseBody
    public Map<String, Object> searchTemplateTypeForRole(HttpServletRequest request) throws Exception {

        return getReturnMap(iRoleManage.searchTemplateTypeForRole(getMap()));
    }
	
	
	@RequestMapping("/searchMenuView")
	@ResponseBody
	public List<Map<String, Object>> searchMenuView(HttpServletRequest request) throws Exception {
		
		return iRoleManage.searchMenuView(getLong("farmId"), getLong("roleId"),getLong("templateId"));
	}
	
	
	@RequestMapping("/setLimit")
	@ResponseBody
	public Map<String, Object> setLimit(HttpServletRequest request) throws Exception {
		
        iRoleManage.editLimit(getLong("roleId"), getString("nodes"));
        basicRefresh();
		return getReturnMap();
	}
}
