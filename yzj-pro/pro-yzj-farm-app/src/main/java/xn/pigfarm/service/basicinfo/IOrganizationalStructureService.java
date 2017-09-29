package xn.pigfarm.service.basicinfo;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.DeptModel;
import xn.pigfarm.model.basicinfo.PostModel;

public interface IOrganizationalStructureService {



	/**
     * 组织结构： 根据farmId获取一个组织结构
     * 
     * @param farmId
     * @return
     */
    public List<Map<String, Object>> searchCompanyByFarmId() throws Exception;

    /**
     * @Description: 根据farmId获取一个组织结构（不包含部门）
     * @author Cress
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchCompanyTreeExceptDept() throws Exception;

	/**
     * 组织结构： 公司CU操作
     * 
     * @param entity
     * @param ids
     * @param jsonObj
     * @throws Exception
     */
    public void editCompany(CompanyModel entity, String oldCompanyMark, String isCreate) throws Exception;
	
    /**
     * @Description: 组织架构，删除公司
     * @author Administrator
     * @param companyId
     * @throws Exception
     */
    public void deleteCompany(long companyId) throws Exception;

    /**
     * @Description: 组织架构，删除部门
     * @author Administrator
     * @throws Exception
     */
    public void deleteDept(long deptId) throws Exception;

	/**
     * 组织结构： 公司批量删除操作
     * 
     * @param entity
     * @param ids
     * @param jsonObj
     * @throws Exception
     */
	public void deletesCompany(long[] ids) throws Exception;
	
	/**
     * 组织结构： 根据ID 获取公司
     * 
     * @param id
     * @throws Exception
     */
	public CompanyModel searchCompanyById(long id)throws Exception;
	
	
	/**
     * 组织结构： 部门CU操作
     * 
     * @param entity
     * @param ids
     * @param jsonObj
     * @throws Exception
     */
    public void editDept(Map<String, Object> map, String jsonObj) throws Exception;
	
	/**
     * 组织结构： 部门ID 获取公司
     * 
     * @param id
     * @throws Exception
     */
	public DeptModel searchDeptById(long id)throws Exception;
	
	/**
     * 组织结构： 部门批量删除
     * 
     * @param entity
     * @param jsonObj
     * @throws Exception
     */
	public void deletesDept(long[] ids) throws Exception;
	
	/**
     * 组织结构： 岗位 集合查询 (根据部门id)
     * 
     * @return
     * @throws Exception
     */
	public  List<PostModel> searchPostToList(long deptId) throws Exception;
	
	

	/***
     * 组织结构： 岗位 集合查询 分页 (根据部门id)
     * 
     * @return
     * @throws Exception
     */
	public  BasePageInfo searchPostByPage(long deptId) throws Exception;
	
	
	
	/***
     * 组织结构：岗位 CU操作
     * 
     * @return
     * @throws Exception
     */
	public  void editPost(PostModel entity, String jsonObj) throws Exception;
	
	
	/***
     * 组织结构： 岗位 批量删除操作
     * 
     * @return
     * @throws Exception
     */
	public void deletesPost(long[] ids)throws Exception;

    /**
     * @Description: 查询公司的部门树 ,并且去除endDepartId下面的树
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDepTree(Long farmId, Long endDepartId) throws Exception;

    /**
     * @Description: 查询公司树（无公司） ,并且去除endCompanyId下面的树
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchOnlyCompanyTree(Long endCompanyId) throws Exception;

    /**
     * @Description: 根据CompanyMark查询所有下属公司
     * @author zhangjs
     * @param companyMark
     * @return
     * @throws Exception
     */
    public List<CompanyModel> searchSubordinateCompanyByMark(String companyMark) throws Exception;

    /**
     * @Description: 根据CompanyMark查询所有下属公司包含自己公司
     * @author zhangjs
     * @param companyMark
     * @return
     * @throws Exception
     */
    public List<CompanyModel> searchSubordinateCompanyByMarkWithSelf(String companyMark) throws Exception;
}
