package xn.pigfarm.controller.basicinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.service.basicinfo.IUserManage;

@Controller
@RequestMapping("/UserManage")
public class UserManageController extends BaseController {

	@Autowired
	private IUserManage iUserManage;

	/**
     * 人员管理 查询集合 (分页)
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchEmployeeToPage")
	@ResponseBody
    public Map<String, Object> searchEmployeeToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(iUserManage.searchEmployeeToPage(getLong("farmId"), getMap()));
	}

    // =========================
    @RequestMapping("/searchEmployeeToList")
    @ResponseBody
    public Map<String, Object> searchEmployeeToList(HttpServletRequest request) throws Exception {
        return getReturnMap(iUserManage.searchEmployeeToList());
    }

    @RequestMapping("/searchEmployeeByIdToList")
    @ResponseBody
    public Map<String, Object> searchEmployeeByIdToList() throws Exception {
        return getReturnMap(iUserManage.searchEmployeeByIdToList(getString("houseId")));
    }
	
    /**
     * 人员管理 CU操作
     * 
     * @param entity
     * @param jsonObj
     * @return
     * @throws Exception
     */
    @RequestMapping("/editUserEmployee")
	@ResponseBody
    public Map<String, Object> editUserEmployee(HttpServletRequest request) throws Exception {

        iUserManage.editUserByEmployee(getMap());
        basicRefresh();
		return getReturnMap();
	}

	@RequestMapping("/deletesEmployee")
	@ResponseBody
	public Map<String, Object> deletesEmployee(HttpServletRequest request) throws Exception {

		iUserManage.deletesEmployee(getIds());
        basicRefresh();
		return getReturnMap();
	}
	
    @RequestMapping("/editUser")
	@ResponseBody
    public Map<String, Object> editUser(HttpServletRequest request) throws Exception {
		
        iUserManage.editUser(getMap());
        basicRefresh();
		return getReturnMap();
	}
	
	
	
    // @RequestMapping("/searchEmpByRole")
    // @ResponseBody
    // public Map<String, Object> searchEmpByRole(HttpServletRequest request) throws Exception {
    //
    // return getReturnMap(iUserManage.searchEmpByRole(getLong("farmId")));
    // }
	
	@RequestMapping("/searchEmpByRoleId")
	@ResponseBody
	public Map<String, Object> searchEmpByRoleId(HttpServletRequest request) throws Exception{
		
        return getReturnMap(iUserManage.searchEmpByRoleId(getLong("mainId")));
	}
	
    @RequestMapping("/saerchEmployeeByConditionToPage")
    @ResponseBody
    public Map<String, Object> saerchEmployeeByConditionToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(iUserManage.saerchEmployeeByConditionToPage(getLong("farmId"), getMap()));
    }

    @RequestMapping("/saerchUserInfoByEmployeeId")
    @ResponseBody
    public Map<String, Object> saerchUserInfoByEmployeeId(HttpServletRequest request) throws Exception {

        return getReturnMap(iUserManage.saerchUserInfoByEmployeeId(getMap()));
    }

    @RequestMapping("/searchUserIsExists")
    @ResponseBody
    public Map<String, Object> searchUserIsExists(HttpServletRequest request) throws Exception {

        return getReturnMap(iUserManage.searchUserIsExists(getMap()));
    }

    @RequestMapping("/editUserResetPassword")
    @ResponseBody
    public Map<String, Object> editUserResetPassword(HttpServletRequest request) throws Exception {

        iUserManage.editUserResetPassword(getMap());
        return getReturnMap();
    }

    @RequestMapping("/editDisablesEmployee")
    @ResponseBody
    public Map<String, Object> editDisablesEmployee(HttpServletRequest request) throws Exception {

        iUserManage.editDisablesEmployee(getIds());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/editEnablesEmployee")
    @ResponseBody
    public Map<String, Object> editEnablesEmployee(HttpServletRequest request) throws Exception {

        iUserManage.editEnablesEmployee(getIds());
        basicRefresh();
        return getReturnMap();
    }

}
