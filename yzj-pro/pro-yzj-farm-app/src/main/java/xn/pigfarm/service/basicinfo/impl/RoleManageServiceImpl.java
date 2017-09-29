package xn.pigfarm.service.basicinfo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import xn.core.data.SqlCon;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.CdCodeMapper;
import xn.pigfarm.mapper.backend.MenusMapper;
import xn.pigfarm.mapper.backend.TemplateMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeRoleMapper;
import xn.pigfarm.mapper.basicinfo.LimitMapper;
import xn.pigfarm.mapper.basicinfo.RoleMapper;
import xn.pigfarm.model.backend.ButtonView;
import xn.pigfarm.model.backend.CdCodeListModel;
import xn.pigfarm.model.backend.MenusView;
import xn.pigfarm.model.backend.TemplateModel;
import xn.pigfarm.model.basicinfo.EmployeeRoleModel;
import xn.pigfarm.model.basicinfo.LimitModel;
import xn.pigfarm.model.basicinfo.RoleModel;
import xn.pigfarm.service.basicinfo.IRoleManage;

/**
 * @Description: 角色管理服务类
 * @author zhangjs
 * @date 2016年7月19日 上午8:49:02
 */
@Service("RoleManageService")
public class RoleManageServiceImpl extends BaseServiceImpl implements IRoleManage {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private EmployeeRoleMapper employeeRoleMapper;

	@Autowired
	private CdCodeMapper cdCodeMapper;

	@Autowired
	private TemplateMapper templateMapper;
	
	@Autowired
	private MenusMapper menusMapper;
	
	@Autowired
	private LimitMapper limitMapper;

    @Autowired
    private IParamMapper paramMapper;

    // @Override
    // public List<RoleManageView> searchRoleByFarmId() {
    //
    // return roleMapper.searchRoleByFarmId(getFarmId());
    // }

	@Override
	public BasePageInfo searchRoleByFarmIdPage() throws Exception {

        setToPage();
        SqlCon sqlCon=new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID=?");
        sqlCon.addMainSql(" AND INSTR('1,2,3',ROLE_TYPE)<=0 ");
        List<RoleModel> roleList = getList(roleMapper, sqlCon);
        CacheUtil.setName(roleList, NameEnum.TEMPLATE_NAME);
        return getPageInfo(roleList);
	}

    @Override
    public List<Map<String, Object>> searchRoleByFarmIdToList() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT T1.ROW_ID AS rowId, T1.ROLE_NAME AS roleName, T3.ROW_ID AS moduleId, T3.MODULE_NAME AS moduleName,T4.ROW_ID AS wechatModuleId,T4.MODULE_NAME AS wechatModuleName");
        sqlCon.addMainSql(" FROM CD_O_ROLE T1 INNER JOIN CD_O_TEMPLATE T2 ");
        sqlCon.addMainSql(" ON (T1.TEMPLATE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1') ");
        sqlCon.addMainSql(" INNER JOIN CD_O_MODULE T3  ON (T2.MODULE_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0'  AND T3.STATUS = '1')");
        sqlCon.addMainSql(" LEFT JOIN CD_O_MODULE T4  ON (T2.WECHAT_MODULE_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0'  AND T4.STATUS = '1')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND INSTR('1,2,3,5',T1.ROLE_TYPE) <= 0 ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID =?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

	@Override
	public void editRole(RoleModel roleModle, String jsonObj) throws Exception {
		roleModle.setFarmId(getFarmId());
        roleModle.setCompanyId(getCompanyId());
        long roleNameIsExist = ParamUtil.isExist("cd_o_role", roleModle.getRowId(), roleModle.getRoleName() + "," + getFarmId(), "ROLE_NAME,FARM_ID");
        if (roleNameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, roleModle.getRoleName());
        }
        if (roleModle.getTemplateId() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "菜单模板不能为空！");
        }
		if (roleModle.getRowId() == null || roleModle.getRowId() == 0) {
            // 业务编码
            String businessCode = ParamUtil.getBCode("ROLE", getEmployeeId(), getCompanyId(), getFarmId());
            roleModle.setBusinessCode(businessCode);
			roleMapper.insert(roleModle);

            // List<HashMap> list = getJsonList(jsonObj, HashMap.class);
            // if (jsonObj == null || list.isEmpty()) {
            // return;
            // }
            //
            // List<EmployeeRoleModel> listemprole = new ArrayList<>();
            // for (HashMap<String, Object> employeeModel : list) {
            // String operate = (String) employeeModel.get("operate");
            // if ("C".equals(operate)) {
            // EmployeeRoleModel empRole = new EmployeeRoleModel();
            // empRole.setDeletedFlag("0");
            // empRole.setStatus("1");
            // empRole.setRoleId(roleModle.getRowId());
            // empRole.setEmployeeId(Long.parseLong(employeeModel.get("rowId").toString()));
            // listemprole.add(empRole);
            // }
            // }
            // employeeRoleMapper.inserts(listemprole);
		} else {
			roleMapper.update(roleModle);
            // List<Map<String, Object>> list = getMapList(jsonObj);
            // if (jsonObj == null || list.isEmpty()) {
            // return;
            // }
            //
            // for (Map<String, Object> employeeModel : list) {
            // String operate = (String) employeeModel.get("operate");
            // if ("D".equals(operate)) {
            // SqlCon sqlCon=new SqlCon();
            // sqlCon.addConditionWithNull(employeeModel.get("rowId"), " AND EMPLOYEE_ID=?");
            // sqlCon.addConditionWithNull(roleModle.getRowId(), " AND ROLE_ID==?");
            // List<EmployeeRoleModel> listempRole = getList(employeeRoleMapper, sqlCon);
            // if (listempRole.size() == 1) {
            // employeeRoleMapper.delete(listempRole.get(0).getRowId());
            // }
            // } else {
            // EmployeeRoleModel empRole = new EmployeeRoleModel();
            // empRole.setDeletedFlag("0");
            // empRole.setStatus("1");
            // empRole.setRoleId(roleModle.getRowId());
            // empRole.setEmployeeId(Maps.getLongClass(employeeModel, "rowId"));
            // employeeRoleMapper.insert(empRole);
            // }
            // }
        }
	}

	@Override
	public void deletesRole(long[] ids) throws Exception {

	    SqlCon employeeRoleSql = new SqlCon();
	    employeeRoleSql.addCondition(StringUtil.arrayToString(ids), " AND ROLE_ID IN ?",false,true);
	    List<EmployeeRoleModel> employeeRoleList = getList(employeeRoleMapper,employeeRoleSql);
        if (employeeRoleList.isEmpty()) {
            roleMapper.deletes(ids);

            setDeletes(limitMapper, ids, "ROLE_ID");
        } else {
            Map<Long, List<Long>> map = new HashMap<>();

            for (EmployeeRoleModel model : employeeRoleList) {
                Long key = model.getRoleId();
                if (map.containsKey(key)) {
                    map.get(key).add(model.getEmployeeId());
                } else {
                    List<Long> list = new ArrayList<>();
                    list.add(model.getEmployeeId());
                    map.put(key, list);
                }
            }
            for (Map.Entry<Long, List<Long>> entry : map.entrySet()) {
                List<Long> entryList = entry.getValue();
                StringBuffer errorBuffer = new StringBuffer();
                for (int i = 0; i < entryList.size(); i++) {
                    if (i == 0) {
                        errorBuffer.append("人员：【");
                        errorBuffer.append(CacheUtil.getName(String.valueOf(entryList.get(i)), NameEnum.EMPLOYEE_NAME));
                    } else {
                        errorBuffer.append(",");
                        errorBuffer.append(CacheUtil.getName(String.valueOf(entryList.get(i)), NameEnum.EMPLOYEE_NAME));
                    }
                    if (i == entryList.size() - 1) {
                        errorBuffer.append("】正在使用角色：【" + CacheUtil.getName(String.valueOf(entry.getKey()), xn.pigfarm.util.enums.NameEnum.ROLE_NAME));
                        errorBuffer.append("】,无法删除!");
                    }
                }
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, errorBuffer.toString());
            }
	    }

	}

	@Override
	public List<CdCodeListModel> searchRoleType(String typeCode, long farmId) throws Exception {

		return cdCodeMapper.searchRoleType(typeCode, farmId == 0 ? getFarmId() : farmId);
	}

    @Override
    public List<TemplateModel> searchTemplateTypeForRole(Map<String, Object> inputMap) throws Exception {

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" AND INSTR('1,2,3',ROLE_TYPE)<=0");
        sqlCon.addCondition(Maps.getString(inputMap, "codeValue"), " AND ROLE_TYPE = ?");
        return getList(templateMapper, sqlCon);
    }

	@Override
	public List<Map<String, Object>> searchMenuView(long farmId, long roleId,long templateId) throws Exception {
		
		List<MenusView> listmuf = menusMapper.searchMenuView(farmId, roleId);
		List<MenusView> listmu=menusMapper.searchMenuByTempId(templateId);
		List<ButtonView> listbtn=menusMapper.searchMenuByBtn(farmId, roleId);
			
		for (MenusView MenusView : listmuf) {
            List<ButtonView> listbtns = new ArrayList<>();
			for (ButtonView buttonView : listbtn) {				
				if (MenusView.getModuleId().equals(buttonView.getModuleId()) ) {
					listbtns.add(buttonView);		
				}
			}
            if (!listbtns.isEmpty()) {
				MenusView.setBtns(listbtns);
			}
		}
	
		for (MenusView menusViews : listmu) {
			for (MenusView menusView : listmuf) {
                if (menusViews.getRowId().equals(menusView.getRowId())) {
					if(menusView.getBtns() != null){
						for (ButtonView buttonViews : menusViews.getBtns()) {
							for (ButtonView buttonView : menusView.getBtns()) {
								if(buttonViews.getRowId().equals(buttonView.getRowId())){
									buttonViews.setChecked(true);
									buttonViews.setLimitId(buttonView.getLimitId());
								}
							}
						}
                    }
                    menusViews.setChecked(true);
                    menusViews.setLimitId(menusView.getLimitId());
				}
			}
		}
		return  getMenuTreeList(listmu);
	}
	
	
	
	public static List<Map<String, Object>> getMenuTreeList(List<MenusView> listmu) throws JsonProcessingException {
		int j=0;
		
        List<Map<String, Object>> mu1 = new ArrayList<>();
        List<Map<String, Object>> mu2 = new ArrayList<>();
        List<Map<String, Object>> mu3 = new ArrayList<>();
		for (MenusView menusView : listmu) {
			j++;
			
			if (menusView.getLevelNum() == 1) {
                Map<String, Object> m1 = new HashMap<>();
				m1.put("id", menusView.getModuleId());
				m1.put("text", menusView.getModuleName());
				m1.put("moduleName", menusView.getModuleName());
				m1.put("checked", menusView.isChecked());
				m1.put("parentId", menusView.getParentId());
				m1.put("limitId", menusView.getLimitId());
				m1.put("objType", menusView.getObjType());
                if (!menusView.getBtns().isEmpty()) {
                    List<Map<String, Object>> btns = new ArrayList<>();
					for (int i = 0; i < menusView.getBtns().size(); i++) {
                        Map<String, Object> btn = new HashMap<>();
						btn.put("id", menusView.getModuleId());
						btn.put("btnId", menusView.getBtns().get(i).getRowId());
                        btn.put("text", "【按钮】——" + menusView.getBtns().get(i).getBtnName());
						btn.put("checked", menusView.getBtns().get(i).isChecked());
						btn.put("parentId", menusView.getModuleId());
						btn.put("limitId", menusView.getBtns().get(i).getLimitId());
						btn.put("objType", "BTN");
						btns.add(btn);
					}
					m1.put("children", btns);
				}
				mu1.add(m1);
			} else if (menusView.getLevelNum() == 2) {
                Map<String, Object> m2 = new HashMap<>();
				m2.put("id", menusView.getModuleId());
				m2.put("text", menusView.getModuleName());
				m2.put("moduleName", menusView.getModuleName());
				m2.put("parentId", menusView.getParentId());
				m2.put("checked", menusView.isChecked());
				m2.put("limitId", menusView.getLimitId());
				m2.put("objType", menusView.getObjType());
                if (!menusView.getBtns().isEmpty()) {
                    List<Map<String, Object>> btns = new ArrayList<>();
					for (int i = 0; i < menusView.getBtns().size(); i++) {
                        Map<String, Object> btn = new HashMap<>();
						btn.put("id", menusView.getModuleId());
						btn.put("btnId", menusView.getBtns().get(i).getRowId());
                        btn.put("text", "【按钮】——" + menusView.getBtns().get(i).getBtnName());
						btn.put("checked", menusView.getBtns().get(i).isChecked());
						btn.put("parentId", menusView.getModuleId());
						btn.put("limitId", menusView.getBtns().get(i).getLimitId());
						btn.put("objType", "BTN");
						btns.add(btn);
					}
					m2.put("children", btns);
				}
				mu2.add(m2);
			} else {
                Map<String, Object> m3 = new HashMap<>();
				m3.put("id", menusView.getModuleId());
				m3.put("text", menusView.getModuleName());
				m3.put("moduleName", menusView.getModuleName());
				m3.put("parentId", menusView.getParentId());
				m3.put("checked", menusView.isChecked());
				m3.put("limitId", menusView.getLimitId());
				m3.put("objType", menusView.getObjType());
                if (!menusView.getBtns().isEmpty()) {
                    List<Map<String, Object>> btns = new ArrayList<>();
					for (int i = 0; i < menusView.getBtns().size(); i++) {
                        Map<String, Object> btn = new HashMap<>();
						btn.put("id", menusView.getModuleId());
						btn.put("btnId", menusView.getBtns().get(i).getRowId());
                        btn.put("text", "【按钮】——" + menusView.getBtns().get(i).getBtnName());
						btn.put("checked",  menusView.getBtns().get(i).isChecked());
						btn.put("parentId", menusView.getModuleId());
						btn.put("limitId", menusView.getBtns().get(i).getLimitId());
						btn.put("objType", "BTN");
						btns.add(btn);
					}
					m3.put("children", btns);
				}
				mu3.add(m3);
			}
		}
		
		
		
		int thirdSize = mu3.size();
		int secondSize = mu2.size();
		int firstSize = mu1.size();

        // 三级菜单加到二级菜单中
		if (thirdSize > 0 && secondSize > 0) {
			j = j + buildTree(mu2, mu3);
		}

        // 二级菜单加到一级菜单中
		if (firstSize > 0 && secondSize > 0) {
			j += buildTree(mu1, mu2);
		}

		return mu1;
	}

	public static int buildTree(List<Map<String, Object>> mu1, List<Map<String, Object>> mu2) {
		int r = 0;
		List<Map<String, Object>> secondChildList = null;
		int secondSize = mu1.size();
		for (int i = 0; i < secondSize; i++) {
			if (mu1.get(i).get("children") != null) {
				secondChildList = (List<Map<String, Object>>) mu1.get(i).get("children");
			} else {
				secondChildList = new ArrayList<Map<String, Object>>();
			}
			int secondId = Maps.getInt(mu1.get(i), "id");

			for (int j = 0; j < mu2.size(); j++) {
				r++;
				
				int thirdParentId = Maps.getInt(mu2.get(j), "parentId");
				if (thirdParentId == secondId) {
					secondChildList.add(mu2.get(j));
					mu2.remove(j);
					j--;
				}
			}

			if (secondChildList.size() > 0) {
				mu1.get(i).put("children", secondChildList);
			}
		}
		return r;
	}
	
	@Override
    public void editLimit(long roleId, String jsonObj) throws Exception {
		
		if (jsonObj != null) {
			List<HashMap> list = getJsonList(jsonObj, HashMap.class);
			List<LimitModel> listmode=new ArrayList<LimitModel>();
			for (HashMap<String, Object> map : list) {
				if(map.get("limitId") == null && (Boolean)map.get("checked")){
					LimitModel limit=new LimitModel();
					limit.setStatus("1");
					limit.setDeletedFlag("0");
					limit.setOriginFlag("S");
					limit.setOriginApp("XN1.0");
					limit.setRoleId(roleId);
					if((map.get("objType") != null) && map.get("objType").equals("BTN")){
						
						limit.setObjId(Long.parseLong(map.get("btnId").toString()) );
						limit.setObjType((String) map.get("objType"));
					}else {
						limit.setObjId(Long.parseLong(map.get("id").toString()) );
						limit.setObjType("URL");
					}					
					listmode.add(limit);
				} 
				if(map.get("limitId") != null && !(Boolean)map.get("checked") ){
					limitMapper.delete(Long.parseLong(map.get("limitId").toString()));
				}
			}
			if(listmode .size() > 0){
				limitMapper.inserts(listmode);
			}
			
		}
		
	}
}
