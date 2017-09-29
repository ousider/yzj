package xn.core.mapper.portal;

import java.util.List;

import xn.core.model.portal.CoreRoleModel;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-4-9 Time:14:03:08
 */
public interface CoreRoleMapper {
	
	/**
	 * 查询角色对应菜单
	 * @return
	 */
	List<CoreRoleModel> searchUserByResources();

	/**
	 * 根据用户名查询角色对应菜单
	 * @param userName
	 * @return
	 */
	List<CoreRoleModel> searchUserByRole(String userName);
}
