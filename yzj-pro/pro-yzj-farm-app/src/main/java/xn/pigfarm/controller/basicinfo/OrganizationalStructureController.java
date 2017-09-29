package xn.pigfarm.controller.basicinfo;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.PostModel;
import xn.pigfarm.service.basicinfo.IOrganizationalStructureService;

@Controller
@RequestMapping("/OrganizationalStructure")
public class OrganizationalStructureController extends BaseController {

	@Autowired
	private IOrganizationalStructureService iOrganizationalStructureService;

	/**
     * 组织结构： 根据farmId获取公司树
     * 
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCompanyTree")
	@ResponseBody
    public Map<String, Object> searchCompanyByFarmId(HttpServletRequest request) throws Exception {
		
        return getReturnMap(iOrganizationalStructureService.searchCompanyByFarmId());
	}

    /**
     * @Description: 根据farmId获取公司树不包含部门
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchCompanyTreeExceptDept")
    @ResponseBody
    public Map<String, Object> searchCompanyTreeExceptDept(HttpServletRequest request) throws Exception {

        return getReturnMap(iOrganizationalStructureService.searchCompanyTreeExceptDept());
    }

    /**
     * 组织结构： 公司CU操作
     * 
     * @param entity
     * @param jsonObj
     * @return
     * @throws Exception
     */
	@RequestMapping("/editCompany")
	@ResponseBody
	public Map<String, Object> editCompany(HttpServletRequest request) throws Exception {

        iOrganizationalStructureService.editCompany(getBean(CompanyModel.class), getString("oldCompanyMark"), getString("isCreate"));
        basicRefresh(false);
		return getReturnMap();
	}

	/**
     * 组织结构： 根据ID 获取公司
     * 
     * @param id
     * @throws Exception
     */
	@RequestMapping("/searchCompanyById")
	@ResponseBody
    public Map<String, Object> searchCompanyById(HttpServletRequest request) throws Exception {

        return getReturnMap(iOrganizationalStructureService.searchCompanyById(getLong("id")));
	};

	/**
     * 组织结构： 部门CU操作
     * 
     * @param entity
     * @param ids
     * @param jsonObj
     * @throws Exception
     */
	@RequestMapping("/editDept")
	@ResponseBody
	public Map<String, Object> editDept(HttpServletRequest request) throws Exception {
		
        iOrganizationalStructureService.editDept(getMap(), getString("jsonObj"));
        basicRefresh();
		return getReturnMap();

	};

	/**
     * 组织结构： 部门ID 获取公司
     * 
     * @param id
     * @throws Exception
     */
	@RequestMapping("/searchDeptById")
	@ResponseBody
    public Map<String, Object> searchDeptById(HttpServletRequest request) throws Exception {

        return getReturnMap(iOrganizationalStructureService.searchDeptById(getLong("id")));
	};

    /**
     * @Description:组织架构，删除公司
     * @author Administrator
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteCompany")
    @ResponseBody
    public Map<String, Object> deleteCompany(HttpServletRequest request) throws Exception {

        iOrganizationalStructureService.deleteCompany(getLong("companyId"));
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 组织架构，删除部门
     * @author Administrator
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteDept")
    @ResponseBody
    public Map<String, Object> deleteDept(HttpServletRequest request) throws Exception {

        iOrganizationalStructureService.deleteDept(getLong("deptId"));
        basicRefresh();
        return getReturnMap();
    }
	
    /**
     * 组织结构： 部门批量删除
     * 
     * @param entity
     * @param jsonObj
     * @throws Exception
     */
	@RequestMapping("/deletesDept")
	@ResponseBody
	public Map<String, Object> deletesDept(HttpServletRequest request) throws Exception {

		iOrganizationalStructureService.deletesDept(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * 组织结构： 岗位 集合查询 分页 (根据部门id)
     * 
     * @param deptId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchPostByPage")
	@ResponseBody
	public  Map<String, Object> searchPostByPage(HttpServletRequest request) throws Exception{
		
		return getReturnMap(iOrganizationalStructureService.searchPostByPage(getLong("deptId")));
	}
	
	/**
     * 组织结构： 岗位 集合查询 (根据部门id)
     * 
     * @param deptId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchByDeptId")
	@ResponseBody
	public List<PostModel> searchByDeptId(HttpServletRequest request) throws Exception{
		
		return iOrganizationalStructureService.searchPostToList(getLong("deptId"));
	}
	
	
	/**
     * 组织结构：岗位 CU操作
     * 
     * @param deptId
     * @return
     * @throws Exception
     */
	@RequestMapping("/editPost")
	@ResponseBody
	public  Map<String, Object> editPost(HttpServletRequest request) throws Exception{
		
		iOrganizationalStructureService.editPost(getBean(PostModel.class), getString("jsonObj"));
		return getReturnMap() ;
	}
	
	/**
     * 组织结构： 岗位 批量删除操作
     * 
     * @param ids
     * @return
     * @throws Exception
     */
	@RequestMapping("/deletesPost")
	@ResponseBody
	public Map<String, Object> deletesPost(HttpServletRequest request)throws Exception{
		
		iOrganizationalStructureService.deletesPost(getIds());
        basicRefresh();
		return getReturnMap() ;
	}

    /**
     * @Description: 获取部门树
     * @author Zhangjc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchDepTree")
    @ResponseBody
    public Map<String, Object> searchDepTree(HttpServletRequest request) throws Exception {
       
        return getReturnMap(iOrganizationalStructureService.searchDepTree(getLong("farmId"), getLong("endDeptId")));
    }

    /**
     * @Description: 获取公司树
     * @author Zhangjc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchOnlyCompanyTree")
    @ResponseBody
    public Map<String, Object> searchOnlyCompanyTree(HttpServletRequest request) throws Exception {

        return getReturnMap(iOrganizationalStructureService.searchOnlyCompanyTree(getLong("endCompanyId")));
    }
}
