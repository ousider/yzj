package xn.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import xn.core.model.TreeNode;
import xn.core.model.portal.CoreCompanyModel;
import xn.core.model.portal.CoreDeptModel;
import xn.core.model.system.MenuView;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;

/**
 * @Description: 建立树形结构
 * @author Zhangjc
 * @date 2016年5月3日 上午9:00:58
 */
public class TreeUtil {

    private static Logger log = Logger.getLogger(TreeUtil.class);


	private TreeNode treeNode;

	/**
     * @Description: 获取菜单树，只能获取三级的菜单树
     * @author Zhangjc
     * @param list
     * @return list
     * @throws JsonProcessingException
     */
    public static List<Map<String, Object>> getMenuTreeList(List<MenuView> list) throws JsonProcessingException {
        log.info(TimeUtil.getSysTimestamp() + "getTreeForMenu begin");

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
                    fristLevel.put("iconCls", cdModel.getIconCls());
					fristLevelList.add(fristLevel);
				}

				if (2 == cdModel.getLevelNum()) {
					secondLevel = new HashMap<String, Object>();
					secondLevel.put("id", cdModel.getModuleId());
					secondLevel.put("text", cdModel.getModuleName());
					secondLevel.put("moduleName", cdModel.getModuleName());
					secondLevel.put("url", cdModel.getModuleUrl());
					secondLevel.put("parentId", cdModel.getParentId());
                    secondLevel.put("component", cdModel.getComponent());
                    secondLevel.put("iconCls", cdModel.getIconCls());
					secondLevelList.add(secondLevel);
				}

				if (3 == cdModel.getLevelNum()) {
					thirdLevel = new HashMap<String, Object>();
					thirdLevel.put("id", cdModel.getModuleId());
					thirdLevel.put("text", cdModel.getModuleName());
					thirdLevel.put("moduleName", cdModel.getModuleName());
					thirdLevel.put("url", cdModel.getModuleUrl());
					thirdLevel.put("parentId", cdModel.getParentId());
                    thirdLevel.put("component", cdModel.getComponent());
                    thirdLevel.put("iconCls", cdModel.getIconCls());
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

	/* public static <T> List<Map<String, Object>> getTreeAll(List<T> allList,
     * String supId, String name) {
     * 
     * List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
     * long _supId = 0; long _rowId = 0; try { for (Iterator<T> it =
     * allList.iterator(); it.hasNext();) {
     * 
     * T t = (T) it.next(); Field supF = t.getClass().getDeclaredField(supId);
     * supF.setAccessible(true); _supId = (long) supF.get(t); if (_supId == 0) {
     * Map<String, Object> map = new HashMap<String, Object>(); Field rowIdF =
     * t.getClass().getSuperclass().getDeclaredField("rowId"); Field nameF =
     * t.getClass().getDeclaredField(name); rowIdF.setAccessible(true);
     * nameF.setAccessible(true); _rowId = (long) rowIdF.get(t);
     * map.put("rowId", _rowId); map.put("ParentId", _supId); map.put("Name",
     * nameF.get(t)); list.add(map); }
     * 
     * }
     * 
     * List<Map<String, Object>> todoList = new ArrayList<Map<String,
     * Object>>(); todoList.addAll(list);
     * 
     * while (todoList.size() != 0) { // 获取第一个节点 Map<String, Object> node =
     * todoList.get(0); // 删除第一个节点 todoList.remove(0); for (Iterator<T> it =
     * allList.iterator(); it.hasNext();) {
     * 
     * T t = (T) it.next(); Field supF = t.getClass().getDeclaredField(supId);
     * supF.setAccessible(true); _supId = (long) supF.get(t); if (_supId ==
     * Maps.getLong(node, "ParentId")) { Map<String, Object> map = new
     * HashMap<String, Object>(); Field rowIdF =
     * t.getClass().getSuperclass().getDeclaredField("rowId"); Field nameF =
     * t.getClass().getDeclaredField(name); rowIdF.setAccessible(true);
     * nameF.setAccessible(true); _rowId = (long) rowIdF.get(t);
     * map.put("rowId", _rowId); map.put("ParentId", _supId); map.put("Name",
     * nameF.get(t)); if (node.get("children") == null) { List<Map<String,
     * Object>> childList = new ArrayList<Map<String, Object>>();
     * childList.add(map); node.put("children", childList); } else {
     * ((List<Map<String, Object>>) node.get("children")).add(map); }
     * todoList.add(map); }
     * 
     * } }
     * 
     * } catch (NoSuchFieldException e) { e.printStackTrace(); } catch
     * (SecurityException e) { e.printStackTrace(); } catch
     * (IllegalArgumentException e) { e.printStackTrace(); } catch
     * (IllegalAccessException e) { e.printStackTrace(); } return list; } */
	/**
     * 获取父
     * 
     * @param list
     * @param rowId
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
	public static <T> T indexOfList(List<T> list, long rowId)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		long _rowId = 0;
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			Field rowIdF = t.getClass().getDeclaredField("cid");
			rowIdF.setAccessible(true);
			_rowId = (long) rowIdF.get(t);
			if (rowId == _rowId) {
				return t;
			}
		}

		return null;

	}

	/**
     * 获取子
     * 
     * @param list
     * @param rowId
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
	public static <T> List<T> indexOfLists(List<T> list, long rowId)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		long _supId = 0;
		List<T> lists = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			T t = list.get(i);
			Field supIdF = t.getClass().getDeclaredField("pid");
			supIdF.setAccessible(true);
			_supId = (long) supIdF.get(t);
			if (rowId == _supId) {
				lists.add(t);
			}
		}

		return lists;
	}

	/**
     * 递归树
     * 
     * @param list
     * @param rowid
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
	public static <T> T recursiveTree(List<T> list, long rowid, String deptName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		long _rowId = 0;
		long randomNub = (long) -(1 + Math.random() * (1000 - 1 + 1));
		List<T> childrenList = new ArrayList<T>();

		T father = TreeUtil.indexOfList(list, rowid);

		List<T> childTreeNodes = TreeUtil.indexOfLists(list, rowid);

		for (T entity : childTreeNodes) {

			Field rowIdF = entity.getClass().getDeclaredField("cid");
			rowIdF.setAccessible(true);
			_rowId = (long) rowIdF.get(entity);
			if (_rowId != 0) {

				T n = recursiveTree(list, _rowId, deptName);

				if (deptName.equals("dept")) {
					Field _deptCIdF = n.getClass().getDeclaredField("deptCId");
					_deptCIdF.setAccessible(true);
					Field _deptPIdF = n.getClass().getDeclaredField("deptPId");
					_deptPIdF.setAccessible(true);
					Field _CIdF = n.getClass().getDeclaredField("cid");
					_CIdF.setAccessible(true);
					Field _PIdF = n.getClass().getDeclaredField("pid");
					_PIdF.setAccessible(true);
					_deptCIdF.set(n, _CIdF.get(n));
					_deptPIdF.set(n, _PIdF.get(n));
					_CIdF.set(n, randomNub++);
					_PIdF.set(n, randomNub++);
					childrenList.add(n);
				} else {
					childrenList.add(n);
				}
			}
		}
		Field parentTreeF = father.getClass().getDeclaredField("children");
		parentTreeF.setAccessible(true);
		parentTreeF.set(father, childrenList);

		return father;

	}

	/**
     * 格式化list
     * 
     * @param list
     * @param name
     * @param fathertName
     * @param hrDept
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
	public static <T> List<TreeNode> formatList(List<T> list, String[] name, String[] fathertName, String hrDept)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		List<TreeNode> treeNodelist = new ArrayList<TreeNode>();
		List<TreeNode> HrDeptList = new ArrayList<TreeNode>();
		treeNodelist = formatTreeNodes(list, name[0], fathertName[0]);
		if (!"".equals(hrDept)) {
            for (Iterator<TreeNode> iterator = treeNodelist.iterator(); iterator.hasNext();) {
				TreeNode treeNode = (TreeNode) iterator.next();
                for (Iterator<T> iterator1 = list.iterator(); iterator1.hasNext();) {
                    T t = iterator1.next();
					Field _deptF = t.getClass().getDeclaredField(hrDept);
					_deptF.setAccessible(true);
					List<?> deptList = (List<?>) _deptF.get(t);
					if ((deptList != null) && deptList.size() > 0) {
						T deptEntity = (T) deptList.get(0);
						Field _deptEntityF = deptEntity.getClass().getSuperclass().getDeclaredField("farmId");
						_deptEntityF.setAccessible(true);
						if (_deptEntityF.get(deptEntity).equals(treeNode.getCid())) {
							HrDeptList = formatTreeNodes(deptList, name[1], fathertName[1]);
							treeNode.setHrDept(HrDeptList);
						}

					}
				}
			}
		}

		return treeNodelist;

	}

	/**
     * @param list
     *            集合
     * @param name
     *            获取名称
     * @param fathertName
     *            上级id名称
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
	public static <T> List<TreeNode> formatTreeNodes(List<T> list, String name, String fathertName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		List<TreeNode> treeNodelist = new ArrayList<TreeNode>();

		long _rowId = 0;
		long _supId = 0;
		for (int i = 0; i < list.size(); i++) {
			T t = (T) list.get(i);
			Field _rowIdF = t.getClass().getSuperclass().getDeclaredField("rowId");
			_rowIdF.setAccessible(true);
			_rowId = (Long) _rowIdF.get(t);
			Field _supIdF = t.getClass().getDeclaredField(fathertName);
			_supIdF.setAccessible(true);
			_supId = _supIdF.get(t) == null ? 0 : (long) _supIdF.get(t);
			Field _nameF = t.getClass().getDeclaredField(name);
			_nameF.setAccessible(true);
			TreeNode treeNodes = new TreeNode();
			treeNodes.setCid(_rowId);
			treeNodes.setPid(_supId);
			treeNodes.setCname(_nameF.get(t).toString());
			if (name.equals("deptName")) {
				Field _farmIdF = t.getClass().getSuperclass().getDeclaredField("farmId");
				_farmIdF.setAccessible(true);
				treeNodes.setFarmId(((long) _farmIdF.get(t)));
                treeNodes.setType("部门");
			} else {

			}
			treeNodelist.add(treeNodes);
		}

		return treeNodelist;
	}

	/**
     * 获取树 格式为(Json)
     * 
     * @param list
     *            集合
     * @param rowid
     *            根节点Id
     * @param name
     *            获取的名称 [0]=主表 [1]=子表
     * @param formerName
     *            上级Id名称 [0]=主表 [1]=子表
     * @param dept
     *            子表名称
     * @param ids
     *            子表分组名称
     * @return
     */

	public static <T> String getTree(List<T> list, long rowid, String[] name, String[] formerName, String dept,
			List<long[]> ids) {

		StringBuffer jsonRootNode = new StringBuffer();
		try {
			if (name.length > 1 && formerName.length > 1) {
				List<TreeNode> treeNodelist = formatList(list, name, formerName, dept);

				TreeNode rootNode = (TreeNode) recursiveTree(treeNodelist, rowid, "");
				rootNode = recursiveFormatTree(rootNode, ids);
				jsonRootNode.append("[" + JacksonUtil.objectToJackson(rootNode) + "]");
			}

		} catch (NoSuchFieldException e) {
            log.error(e.getMessage());
		} catch (SecurityException e) {
            log.error(e.getMessage());
		} catch (IllegalArgumentException e) {
            log.error(e.getMessage());
		} catch (IllegalAccessException e) {
            log.error(e.getMessage());
		} catch (JsonProcessingException e) {
            log.error(e.getMessage());
		}

		return jsonRootNode.toString();
	}

	/**
     * 递归格式化好的树
     * 
     * @param treeNode
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
	public static TreeNode recursiveFormatTree(TreeNode treeNode, List<long[]> ids)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		TreeNode treeNodes = treeNode;
		long n = (long) -(1 + Math.random() * (10000 - 1 + 1));
		List<TreeNode> hrDeptList = new ArrayList<TreeNode>();
		
		if (treeNode.getHrDept().size() > 0 && treeNode.getCid() == 0) {
			hrDeptList=(List<TreeNode>) treeNode.getChildren();
			TreeNode deptTree = new TreeNode();
			deptTree.setCid(n++);
            deptTree.setCname("部门");
			deptTree.setPid(n++);
			deptTree.setType(null);
			List<TreeNode> rootDeptTreeList = (List<TreeNode>) deptTree.getChildren();
			for (int j = 0; j < ids.get(0).length; j++) {
				TreeNode rootNodeTree = (TreeNode) recursiveTree(treeNode.getHrDept(), ids.get(0)[j], "dept");
				rootNodeTree.setDeptCId(rootNodeTree.getCid());
				rootNodeTree.setDeptPId(rootNodeTree.getPid());
				rootNodeTree.setCid(n++);
				rootNodeTree.setPid(n++);
				rootDeptTreeList.add(rootNodeTree);
			}
			hrDeptList.add(deptTree);
			treeNode.setChildren(hrDeptList);
			treeNode.getHrDept().clear();
			ids.remove(0);
		}

		for (Object treechildren : treeNode.getChildren()) {
			TreeNode tree = (TreeNode) treechildren;
			hrDeptList = (List<TreeNode>) tree.getChildren();

			if (tree.getHrDept().size() > 0) {

				TreeNode deptTree = new TreeNode();
				deptTree.setCid(n++);
                deptTree.setCname("部门");
				deptTree.setPid(n++);
				deptTree.setType(null);
				List<TreeNode> deptTreeList = (List<TreeNode>) deptTree.getChildren();

				for (int i = 0; i < tree.getHrDept().size(); i++) {
					TreeNode deptNode = (TreeNode) tree.getHrDept().get(i);
					for (int k = 0; k < ids.size(); k++) {
						for (int j = 0; j < ids.get(k).length; j++) {
							if (deptNode.getCid() == ids.get(k)[j]) {
								TreeNode rootNodeTree = (TreeNode) recursiveTree(tree.getHrDept(), ids.get(k)[j],"dept");
								rootNodeTree.setDeptCId(rootNodeTree.getCid());
								rootNodeTree.setDeptPId(rootNodeTree.getPid());
								rootNodeTree.setCid(n++);
								rootNodeTree.setPid(n++);
								deptTreeList.add(rootNodeTree);
							}
						}
					}
				}


				hrDeptList.add(deptTree);

				tree.setChildren(hrDeptList);
				tree.getHrDept().clear();

			}
			recursiveFormatTree(tree, ids);
		}
		return treeNode;
	}

    public static List<Map<String, Object>> getTree(List<Map<String, Object>> list, int parentId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        
        for(Map<String, Object> map: list){
           
            int companyId=Maps.getInt(map, "company_id");
            int supCompanyId=Maps.getInt(map, "sup_company_id");
            if (parentId == supCompanyId) {
                List<Map<String, Object>> children = getTree(list, companyId);
                map.put("children", children);
                result.add(map);
            }
        }
        return result;
    }

    public static List<Map<String, Object>> getTreeTest(List<CoreCompanyModel> list, long parentId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        for (CoreCompanyModel model : list) {
            Map<String, Object> map = new HashMap<String, Object>();

            long companyId = model.getCompanyId();
            long supCompanyId = model.getSupCompanyId();
            map.put("companyId", companyId);
            map.put("supCompanyId", supCompanyId);
            map.put("companyName", model.getCompanyName());
            List<CoreDeptModel> depetList=model.getHrDept();
            if (parentId == supCompanyId) {
                List children = getTreeTest(list, companyId);
                // map.put("children", children);
                // if (depetList != null && depetList.size() > 0) {
                // getDet(list, model);
                // }
                result.add(map);
            }
        }
        return result;
    }

    public static List<Map<String, Object>> getDet(List<CoreDeptModel> list, long parentId) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        for (CoreDeptModel model : list) {
            Map<String, Object> map = new HashMap<String, Object>();

            long companyId = model.getRowId();
            long supCompanyId = model.getSupDeptId();
            map.put("companyId", companyId);
            map.put("supCompanyId", supCompanyId);
            map.put("companyName", model.getDeptName());
            if (parentId == supCompanyId) {
                List children = getDet(list, companyId);
                map.put("children", children);
                result.add(map);
            }
        }
        return result;
    }
}
