package xn.pigfarm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;

import xn.core.model.system.MenuView;
import xn.core.util.JacksonUtil;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.model.basicinfo.DeptModel;

/**
 * @Description: 建立树形结构
 * @author Zhangjc
 * @date 2016年5月3日 上午9:00:58
 */
public class TreeBuildUtil {

	/**
     * @Description: 获取菜单树，只能获取三级的菜单树
     * @author Zhangjc
     * @param list
     * @return list
     * @throws JsonProcessingException
     */
	public static List<Map<String, Object>> getMenuTreeList(List<MenuView> list) throws JsonProcessingException {
		System.out.println(TimeUtil.getSysTimestamp() + "getTreeForMenu begin");

		int i = 0;
        // 一级菜单
		List<Map<String, Object>> fristLevelList = new ArrayList<Map<String, Object>>();
		Map<String, Object> fristLevel = null;

        // 二级菜单
		List<Map<String, Object>> secondLevelList = new ArrayList<Map<String, Object>>();
		Map<String, Object> secondLevel = null;

        // 三级菜单
		List<Map<String, Object>> thirdLevelList = new ArrayList<Map<String, Object>>();
		Map<String, Object> thirdLevel = null;

        // 循环List分解到三个菜单中
		if (list != null && list.size() > 0) {
			for (MenuView cdModel : list) {
				i++;
				if (1 == cdModel.getLevelNum()) {
					fristLevel = new HashMap<String, Object>();
					fristLevel.put("id", cdModel.getModuleId());
					fristLevel.put("text", cdModel.getModuleName());
					fristLevel.put("moduleName", cdModel.getModuleName());
					fristLevel.put("url", cdModel.getModuleUrl());
                    fristLevel.put("leaveNum", cdModel.getLevelNum());
                    fristLevel.put("parentId", 1L);
					fristLevelList.add(fristLevel);
				}

				if (2 == cdModel.getLevelNum()) {
					secondLevel = new HashMap<String, Object>();
					secondLevel.put("id", cdModel.getModuleId());
					secondLevel.put("text", cdModel.getModuleName());
					secondLevel.put("moduleName", cdModel.getModuleName());
					secondLevel.put("url", cdModel.getModuleUrl());
					secondLevel.put("parentId", cdModel.getParentId());
                    secondLevel.put("leaveNum", cdModel.getLevelNum());
					secondLevelList.add(secondLevel);
				}

				if (3 == cdModel.getLevelNum()) {
					thirdLevel = new HashMap<String, Object>();
					thirdLevel.put("id", cdModel.getModuleId());
					thirdLevel.put("text", cdModel.getModuleName());
					thirdLevel.put("moduleName", cdModel.getModuleName());
					thirdLevel.put("url", cdModel.getModuleUrl());
					thirdLevel.put("parentId", cdModel.getParentId());
                    thirdLevel.put("leaveNum", cdModel.getLevelNum());
					thirdLevelList.add(thirdLevel);
				}
			}
		} else {
            // TODO 抛出去异常
		}

		int thirdSize = thirdLevelList.size();
		int secondSize = secondLevelList.size();
		int firstSize = fristLevelList.size();

        // 三级菜单加到二级菜单中
		if (thirdSize > 0 && secondSize > 0) {
			i = i + buildTree(secondLevelList, thirdLevelList);
		}

        // 二级菜单加到一级菜单中
		if (firstSize > 0 && secondSize > 0) {
			i += buildTree(fristLevelList, secondLevelList);
		}

		return fristLevelList;
	}

	/**
     * @Description: 获取菜单树，只能获取三级的菜单树
     * @author Zhangjc
     * @param list
     * @return json string
     * @throws JsonProcessingException
     */
	public static String getMenuTreeStr(List<MenuView> list) throws JsonProcessingException {

		return JacksonUtil.objectToJackson(getMenuTreeList(list));
	}

	/**
     * @Description: 将子结点数据传入父节点中
     * @author Zhangjc
     * @param secondLevelList
     * @param thirdLevelList
     * @return
     */
	public static int buildTree(List<Map<String, Object>> secondLevelList, List<Map<String, Object>> thirdLevelList) {

		int r = 0;
		int secondSize = secondLevelList.size();
		for (int i = 0; i < secondSize; i++) {
			List<Map<String, Object>> secondChildList = new ArrayList<Map<String, Object>>();

			int secondId = Maps.getInt(secondLevelList.get(i), "id");

			for (int j = 0; j < thirdLevelList.size(); j++) {
				r++;
				int thirdParentId = Maps.getInt(thirdLevelList.get(j), "parentId");
				if (thirdParentId == secondId) {
					secondChildList.add(thirdLevelList.get(j));
					thirdLevelList.remove(j);
					j--;
				}
			}

			if (secondChildList.size() > 0) {
				secondLevelList.get(i).put("children", secondChildList);
			}
		}
		return r;
	}

	/**
     * @Description: 获取菜单数据，任意几级菜单
     * @author Zhangjc
     * @param menusList
     * @return
     */
	public static List<Map<String, Object>> getMenuTreeAll(List<MenuView> menusList) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Iterator<MenuView> it = menusList.iterator(); it.hasNext();) {
			MenuView cdMenusModel = (MenuView) it.next();
			if (cdMenusModel.getParentId() == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rowId", cdMenusModel.getRowId());
				map.put("moduleId", cdMenusModel.getModuleId());
				map.put("moduleName", cdMenusModel.getModuleName());
				list.add(map);
			}
		}

		List<Map<String, Object>> todoList = new ArrayList<Map<String, Object>>();
		todoList.addAll(list);

		while (todoList.size() != 0) {
            // 获取第一个节点
			Map<String, Object> node = todoList.get(0);
            // 删除第一个节点
			todoList.remove(0);
			for (Iterator<MenuView> it = menusList.iterator(); it.hasNext();) {

				MenuView cdMenusModel = (MenuView) it.next();

				if (cdMenusModel.getParentId() == Maps.getInt(node, "moduleId")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("rowId", cdMenusModel.getRowId());
					map.put("moduleId", cdMenusModel.getModuleId());
					map.put("moduleName", cdMenusModel.getModuleName());
					if (node.get("children") == null) {
						List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
						childList.add(map);
						node.put("children", childList);
					} else {
						((List<Map<String, Object>>) node.get("children")).add(map);
					}
					todoList.add(map);
				}
			}
		}
		return list;
	}

    // ********************************组织架构树结构BEGIN********************************************//
    /**
     * @Description: 获取rootId的树
     * @author zhangjs
     * @param list
     * @param farmId
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getTree(List<Map<String, Object>> list, Long rootId) throws JsonProcessingException {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            // 获取根节点
            Long perId = Maps.getLongClass(map, "id");
            if (rootId.equals(perId)) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("id", perId);
                resultMap.put("parentId", null);
                resultMap.put("text", Maps.getString(map, "text"));
                resultMap.put("companyMark", Maps.getString(map, "companyMark"));
                resultMap.put("children", getTrees(list, perId));

                result.add(resultMap);
                break;
            }
        }
        return result;
    }

    /**
     * @Description: 遍历获取树
     * @author zhangjs
     * @param list
     * @param parentId
     * @param pNum
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getTrees(List<Map<String, Object>> list, Long parentId) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> model : list) {

            Long pid = Maps.getLongClass(model, "parentId");
            // 名称
            String name = Maps.getString(model, "text");
            String companyMark = Maps.getString(model, "companyMark");
            if (parentId.equals(pid)) {
                Map<String, Object> map = new HashMap<String, Object>();
                Long id = Maps.getLongClass(model, "id");
                map.put("id", id);
                map.put("parentId", parentId);
                map.put("text", name);
                map.put("companyMark", companyMark);
                map.put("children", getTrees(list, id));
                result.add(map);
            }
        }
        return result;
    }

    /**
     * @Description: 组织架构树形结构
     * @author zhangjs
     * @param list
     * @param farmId
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getCompanyTree(List<Map<String, Object>> list, Long farmId) throws JsonProcessingException {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> map : list) {
            // 获取根节点
            Long perFarmId = Maps.getLongClass(map, "id");
            if (farmId.equals(perFarmId)) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("farmId", farmId);
                resultMap.put("id", farmId);
                resultMap.put("parentId", null);
                resultMap.put("text", Maps.getString(map, "text"));
                resultMap.put("isBloc", Maps.getString(map, "isBloc"));
                resultMap.put("type", "公司");
                resultMap.put("iconCls", "treeCompany");
                resultMap.put("companyMark", Maps.getString(map, "companyMark"));
                resultMap.put("children", new ArrayList<Map<String, Object>>());
                resultMap.put("companyCode", Maps.getString(map, "companyCode"));

                // 1、取公司
                Maps.getList(resultMap, "children").addAll(getCompanyTrees(list, farmId));
                // 2、取部门
                if (map.get("deptModel") != null) {
                    List<DeptModel> deptList = Maps.getList(map, "deptModel");
                    Maps.getList(resultMap, "children").addAll(getDepTree(deptList, null, farmId, Maps.getString(map, "text")));
                }
                result.add(resultMap);
                break;
            }
        }
        return result;
    }

    /**
     * @Description: 组织架构树形结构
     * @author zhangjs
     * @param list
     * @param parentId
     * @param pNum
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getCompanyTrees(List<Map<String, Object>> list, Long parentId) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> model : list) {

            Long pid = Maps.getLongClass(model, "parentId");
            /* 1、取子公司 */
            // 公司名称
            String name = Maps.getString(model, "text");
            if (parentId.equals(pid)) {
                Map<String, Object> map = new HashMap<String, Object>();
                Long id = Maps.getLongClass(model, "id");
                map.put("id", id);
                map.put("parentId", parentId);
                map.put("farmId", Maps.getLongClass(model, "farmId"));
                map.put("text", name);
                map.put("type", "公司");
                map.put("isBloc", Maps.getString(model, "isBloc"));
                map.put("iconCls", "treeCompany");
                map.put("companyMark", Maps.getString(model, "companyMark"));
                map.put("children", new ArrayList<Map<String, Object>>());
                map.put("companyCode", Maps.getString(model, "companyCode"));
                result.add(map);
                Maps.getList(map, "children").addAll(getCompanyTrees(list, id));
                /* 2、取部门 */
                if (model.get("deptModel") != null) {
                    List<DeptModel> deptList = Maps.getList(model, "deptModel");
                    if (deptList.size() > 0) {
                        Maps.getList(map, "children").addAll(getDepTree(deptList, null, id, name));
                    }

                }
            }
        }
        return result;
    }

    /**
     * @Description: 组织架构树形结构 获取部门明细
     * @author zhangjs
     * @param list
     * @param parentId
     * @param pNum
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getDepTree(List<DeptModel> list, Long parentId, Long companyId, String companyName) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (DeptModel model : list) {
            Map<String, Object> map = new HashMap<>();

            Long deptId = model.getRowId();
            Long supDeptId = model.getSupDeptId();

            // 第一条记录
            if (parentId == null) {
                if (supDeptId == null) {
                    map.put("id", "dep" + model.getRowId());
                    // parentId==null 则为公司下直属部门
                    map.put("parentId", companyId);
                    map.put("deptId", model.getRowId());
                    map.put("text", model.getDeptName());
                    map.put("farmId", model.getFarmId());
                    map.put("type", "部门");
                    map.put("iconCls", "treeDept");
                    map.put("companyName", companyName);
                    List<Map<String, Object>> children = getDepTree(list, deptId, companyId, companyName);
                    map.put("children", children);
                    result.add(map);
                }
            } else if (parentId.equals(supDeptId)) {
                map.put("id", "dep" + model.getRowId());
                // parentId==null 则为公司下直属部门
                map.put("parentId", "dep" + parentId);
                map.put("deptId", model.getRowId());
                map.put("text", model.getDeptName());
                map.put("farmId", model.getFarmId());
                map.put("type", "部门");
                map.put("iconCls", "treeDept");
                map.put("companyName", companyName);
                List<Map<String, Object>> children = getDepTree(list, deptId, companyId, companyName);
                map.put("children", children);
                result.add(map);
            }
        }
        return result;
    }


    /**
     * @Description: 获取去除id下面的所有树
     * @author zhangjs
     * @param list
     * @param parentId
     * @param pNum
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getTreeByEndId(List<Map<String, Object>> list, String key, long id) {

        for (Map<String, Object> map : list) {
            long keyId = Maps.getLong(map, key);
            if (keyId == id) {
                list.remove(map);
                break;
            }else{
                List<Map<String, Object>> children = Maps.getList(map, "children");
                if(children!=null){
                    getTreeByEndId(children, key, id);
                }
            }
        }
        return list;
    }
    // *********************************组织架构树结构END*****************************************//

}
