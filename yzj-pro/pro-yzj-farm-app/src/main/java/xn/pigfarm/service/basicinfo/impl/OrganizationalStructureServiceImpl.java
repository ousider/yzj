package xn.pigfarm.service.basicinfo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.data.enums.SeqEnum;
import xn.core.exception.Thrower;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.SeqUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.hana.model.common.HanaBranch;
import xn.hana.model.common.HanaPigFarm;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.business.production.IOpenAccountBS;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.basicInfo.BasicInfoException;
import xn.pigfarm.mapper.basicinfo.CompanyMapper;
import xn.pigfarm.mapper.basicinfo.DeptHouseMapper;
import xn.pigfarm.mapper.basicinfo.DeptMapper;
import xn.pigfarm.mapper.basicinfo.EmployeePostMapper;
import xn.pigfarm.mapper.basicinfo.PostMapper;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.DeptHouseModel;
import xn.pigfarm.model.basicinfo.DeptModel;
import xn.pigfarm.model.basicinfo.EmployeePostModel;
import xn.pigfarm.model.basicinfo.PostModel;
import xn.pigfarm.service.basicinfo.IOrganizationalStructureService;
import xn.pigfarm.util.TreeBuildUtil;

@Service("OrganizationalStructureService")
public class OrganizationalStructureServiceImpl extends BaseServiceImpl implements IOrganizationalStructureService {

	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private DeptMapper deptMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private EmployeePostMapper employeePostMapper;
    @Autowired
    private DeptHouseMapper deptHouseMapper;

    @Autowired
    private IOpenAccountBS openAccountBS;

    @Autowired
    private IhanaCommonService hanaCommonService;

	@Override
    public List<Map<String, Object>> searchCompanyByFarmId() throws Exception {
        // Commented by zhangjs for 查询下属公司
        // List<CompanyModel> companyList = companyMapper.searchToList();
        // Added by zhangjs for 查询下属公司
        List<CompanyModel> companyList = searchSubordinateCompanyByMarkWithSelf(getCompanyMark());
        
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
        for (CompanyModel model : companyList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", model.getRowId());
            map.put("parentId", model.getSupCompanyId());
            map.put("text", model.getCompanyName());
            map.put("farmId", model.getRowId());
            map.put("isBloc", model.getIsBloc());
            map.put("iconCls", "treeCompany");
            map.put("companyMark", model.getCompanyMark());
            List<DeptModel> listDept = deptMapper.searchToList(model.getRowId());
            map.put("deptModel", listDept);
            mapList.add(map);
        }

        return TreeBuildUtil.getCompanyTree(mapList, getFarmId());
	}

    @Override
    public List<Map<String, Object>> searchCompanyTreeExceptDept() throws Exception {
        // Commented by zhangjs for 查询下属公司
        // List<CompanyModel> companyList = companyMapper.searchToList();
        // Added by zhangjs for 查询下属公司
        List<CompanyModel> companyList = searchSubordinateCompanyByMarkWithSelf(getCompanyMark());

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (CompanyModel model : companyList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", model.getRowId());
            map.put("parentId", model.getSupCompanyId());
            map.put("text", model.getCompanyName());
            map.put("farmId", model.getRowId());
            map.put("isBloc", model.getIsBloc());
            map.put("iconCls", "treeCompany");
            map.put("companyMark", model.getCompanyMark());
            map.put("companyCode", model.getCompanyCode());
            /* List<DeptModel> listDept = deptMapper.searchToList(model.getRowId());
             * map.put("deptModel", listDept); */
            mapList.add(map);
        }

        return TreeBuildUtil.getCompanyTree(mapList, getFarmId());
    }
	@Override
    public void editCompany(CompanyModel entity, String oldCompanyMark, String isCreate) throws Exception {
        if (entity == null) {
            return;
        }
        long flag = ParamUtil.isExist("hr_m_company", entity.getRowId(), entity.getCompanyName() + "," + entity.getSupCompanyId(),
                "COMPANY_NAME,SUP_COMPANY_ID");
        if (flag > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, entity.getCompanyName());
        }
        if(entity.getLatitude()>=90){
            Thrower.throwException(BaseBusiException.LATITUDE_BEYOND_SCOPE, entity.getLatitude());
        }
        if (entity.getRowId() != null) {
            // Begin added by zhangjs 增加CompanyMark，方便查询下属公司
            
            String companyMark = entity.getCompanyMark();
            // 1、supCompanyId不能为空
            if (StringUtil.isBlank(companyMark)) {
                Thrower.throwException(BasicInfoException.NO_SUPER_COMPANY_ERROR);
            }
            
            // 2、oldSupCompanyId与supCompanyId不相等 需要修改其下所有公司的companyMark
            if (!companyMark.equals(oldCompanyMark)) {
                List<CompanyModel> list = searchSubordinateCompanyByMark(oldCompanyMark);
                if (!list.isEmpty()) {
                    setCompanyMark(list, entity.getRowId(), companyMark);
                    companyMapper.updates(list);
                }
            }
            // End added by zhangjs 增加CompanyMark，方便查询下属公司
            companyMapper.update(entity);
            if ("on".equals(isCreate)) {
                openAccountBS.createWeek(entity.getRowId(), entity.getSupCompanyId());
            }
		} else {
            // 业务编码
            long companyId = SeqUtil.getSeq(SeqEnum.COMPANY);
            String businessCode = ParamUtil.getBCode("COMPANY_CODE", getEmployeeId(), getCompanyId(), getFarmId());
            entity.setRowId(companyId);
            entity.setCompanyMark(entity.getCompanyMark() + "," + companyId);
            entity.setCompanyCode(businessCode);
            entity.setRegisterTime(TimeUtil.getSysTimestamp());
            entity.setOpenTime(TimeUtil.getSysTimestamp());
            companyMapper.insert(entity);
            // Begin commented by zhangjs for 储存过程搬入java中实现
            // 生成公司帐套
            // OpenAccountView openAccountView = new OpenAccountView();
            // openAccountView.setCompanyClass(entity.getCompanyClass());
            // openAccountView.setCompanyCode(entity.getCompanyCode());
            // openAccountView.setCompanyType(entity.getCompanyType());
            // openAccountView.setCompanyId(entity.getSupCompanyId());
            // openAccountView.setFarmId(entity.getRowId());
            // companyMapper.OpenAccount(openAccountView);
            // if (!"0".equals(openAccountView.getErrorCode())) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, openAccountView.getErrorMessage());
            // }
            // End commented by zhangjs for 储存过程搬入java中实现
            // Added by zhangjs for 储存过程搬入java中实现
            openAccountBS.openAccount(entity.getCompanyClass(), entity.getCompanyType(), entity.getCompanyCode(), entity.getSupCompanyId(), entity
                    .getRowId(), getEmployeeId());
		}
	}

	@Override
	public CompanyModel searchCompanyById(long id) throws Exception {
		return companyMapper.searchById(id);
	}

	@Override
    public void editDept(Map<String, Object> inputMap, String jsonObj) throws Exception {
        DeptModel deptModel = getBean(DeptModel.class, inputMap);
        DeptHouseModel deptHouseModel = getBean(DeptHouseModel.class, inputMap);
        String ids = Maps.getString(inputMap, "houseIds");
        String[] houseIds = null;
        if (StringUtil.isNonBlank(ids)) {
            houseIds = ids.split(",");
        }

        if (deptModel == null) {
            return;
        }

        long flag = ParamUtil.isExist("hr_o_dept", deptModel.getRowId(), new String[] { deptModel.getDeptName(), deptModel.getFarmId() + "" },
                "DEPT_NAME,FARM_ID");
        if (flag > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, deptModel.getDeptName(), deptModel.getDeptName());
        }
        if (deptModel.getRowId() != null) {
            deptMapper.update(deptModel);
		} else {
            // 业务编码
            String businessCode = ParamUtil.getBCode("BUSINESS_CODE", getEmployeeId(), getCompanyId(), getFarmId());
            deptModel.setBusinessCode(businessCode);
            deptModel.setCompanyId(getCompanyId());
            deptMapper.insert(deptModel);

		}

        SqlCon sql = new SqlCon();
        sql.addMainSql(" UPDATE HR_R_DEPT_HOUSE SET DELETED_FLAG = 1 WHERE STATUS = 1 ");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ");
        sql.addCondition(deptModel.getRowId(), " AND DEPT_ID = ? ");
        setSql(deptHouseMapper, sql);

        if (houseIds != null) {

            for (int i = 1; i < houseIds.length; i++) {
                long houseId = Long.parseLong(houseIds[i]);
                deptHouseModel.setFarmId(getFarmId());
                deptHouseModel.setCompanyId(getCompanyId());
                deptHouseModel.setHouseId(houseId);
                deptHouseModel.setDeptId(deptModel.getRowId());
                deptHouseMapper.insert(deptHouseModel);
            }
        }
	}

	@Override
	public DeptModel searchDeptById(long id) throws Exception {
        DeptModel deptModel = deptMapper.searchById(id);
        SqlCon sql = new SqlCon();

        sql.addCondition(getFarmId(), " AND FARM_ID = ? ");
        sql.addCondition(id, " AND DEPT_ID = ? ");
        List<DeptHouseModel> list = getList(deptHouseMapper, sql);
        StringBuffer houseId = new StringBuffer();
        StringBuffer houseNames =  new StringBuffer();
        if (list != null) {
            for (DeptHouseModel deptHouseModel : list) {
                houseId.append(",");
                houseId.append(deptHouseModel.getHouseId());
                houseNames.append(",");
                houseNames.append(CacheUtil.getName(String.valueOf(deptHouseModel.getHouseId()), NameEnum.HOUSE_NAME));
            }
        }
        deptModel.getData().put("houseIds", houseId.toString());
        deptModel.getData().put("houseNames", houseNames.toString());
        return deptModel;
	}

	@Override
	public void deletesDept(long[] ids) throws Exception {

		deptMapper.deletes(ids);
	}

	@Override
	public void deletesCompany(long[] ids) throws Exception {

		companyMapper.deletes(ids);
	}

	@Override
	public List<PostModel> searchPostToList(long deptId) throws Exception {

		return postMapper.searchByDeptId(deptId);
	}

	@Override
	public BasePageInfo searchPostByPage(long deptId) throws Exception {
		
		setToPage();
		return getPageInfo(postMapper.searchByDeptId(deptId));
	}

	@Override
	public void editPost(PostModel entity, String jsonObj) throws Exception {
        if (entity == null) {
            return;
        }
        long flag = ParamUtil.isExist("hr_o_post", entity.getRowId(), new String[] { entity.getJobTitle(), entity.getDeptId()+""}, "JOB_TITLE,DEPT_ID");
        if (flag > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, entity.getJobTitle(), entity.getJobTitle());
        }
        if (entity.getRowId() != null) {
			 postMapper.update(entity);
		} else {
            entity.setFarmId(getFarmId());
            entity.setCompanyId(getCompanyId());
            postMapper.insert(entity);
		}
	}

	@Override
	public void deletesPost(long[] ids) throws Exception {
        String postIds = getStringArray(ids);
        SqlCon sql = new SqlCon();
        sql.addCondition(postIds, " AND POST_ID IN ? ", false, true);
        List<EmployeePostModel> list = getList(employeePostMapper, sql);
        if (list.isEmpty()) {
            postMapper.deletes(ids);
        } else {
            Thrower.throwException(BasicInfoException.DELETE_EMPLOYEE_POST_ERROR);
        }

	}

    public String getStringArray(long ids[]) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            str.append(ids[i]);
            if (i < ids.length - 1) {
                str.append(",");
            }
        }
        return str.toString();
    }
    @Override
    public List<Map<String, Object>> searchDepTree(Long farmId, Long endDepartId) throws Exception {
        farmId = farmId == null ? getFarmId() : farmId;
        List<DeptModel> listDept = deptMapper.searchToList(farmId);
        String farmName = getUserDetail().getFarmName();
        List<Map<String, Object>> list = TreeBuildUtil.getDepTree(listDept, null, farmId,farmName );
        if (endDepartId != null) {
            list = TreeBuildUtil.getTreeByEndId(list, "deptId", endDepartId);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchOnlyCompanyTree(Long endCompanyId) throws Exception {

        List<CompanyModel> companyList = companyMapper.searchToList();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (CompanyModel model : companyList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", model.getRowId());
            map.put("parentId", model.getSupCompanyId());
            map.put("text", model.getCompanyName());
            map.put("farmId", model.getRowId());
            map.put("companyMark", model.getCompanyMark());
            mapList.add(map);
        }
        List<Map<String, Object>> list = TreeBuildUtil.getTree(mapList, getFarmId());
        if (endCompanyId != null) {
            list = TreeBuildUtil.getTreeByEndId(list, "id", endCompanyId);
        }
        return list;
    }

    @Override
    public void deleteDept(long deptId) throws Exception {
        List<PostModel> postList = postMapper.searchByDeptId(deptId);
        if (postList != null && !postList.isEmpty()) {
            Thrower.throwException(BasicInfoException.DELETE_DEPT_POST_ERROR);
        }
        deptMapper.delete(deptId);
        setDeletes(deptHouseMapper, String.valueOf(deptId), "DEPT_ID");
    }

    @Override
    public void deleteCompany(long companyId) throws Exception {

        companyMapper.delete(companyId);

    }

    @Override
    public List<CompanyModel> searchSubordinateCompanyByMark(String companyMark) throws Exception {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" AND COMPANY_MARK LIKE '");
        sql.addMainSql(companyMark.trim());
        sql.addMainSql(",%' ");
        return getList(companyMapper, sql);
    }

    @Override
    public List<CompanyModel> searchSubordinateCompanyByMarkWithSelf(String companyMark) throws Exception {
        List<CompanyModel> list = searchSubordinateCompanyByMark(companyMark);

        CompanyModel company = new CompanyModel();
        company.setRowId(getFarmId());
        company.setSupCompanyId(getUserDetail().getCompanyId());
        company.setCompanyName(getUserDetail().getFarmName());
        company.setIsBloc(getUserDetail().getIsBloc());
        company.setCompanyMark(getCompanyMark());

        list.add(company);
        return list;
    }

    /**
     * @Description: 递归为子公司重新赋值CompanyMark
     * @author zhangjs
     * @param list
     * @param companyId
     * @param newCompanyMark
     */
    private void setCompanyMark(List<CompanyModel> list, Long companyId, String newCompanyMark) {
        for (CompanyModel model : list) {
            Long supCompanyId = model.getSupCompanyId();
            if (companyId.equals(supCompanyId)) {
                String newSupCompanyMark = newCompanyMark + "," + model.getRowId();
                model.setCompanyMark(newSupCompanyMark);
                setCompanyMark(list, model.getRowId(), newSupCompanyMark);
            }
        }
    }
}
