package xn.core.service.portal;

import java.util.List;

import xn.core.model.portal.CoreRoleModel;
import xn.core.model.portal.CoreUserModel;

public interface ISecurityLoginService {
	
	/**
     * 根据用户名和公司编码登录
     * 
     * @param entity
     * @return
     */
    CoreUserModel userLogin(CoreUserModel entity);

	/**
     * 根据用户名查询所在公司
     * 
     * @param userName
     * @return
     */
    CoreUserModel searchUserByName(String userName);

	/**
     * 查询角色对应菜单
     * 
     * @return
     */
    List<CoreRoleModel> searchUserByResources();

	/**
     * 根据用户名查询角色对应菜单
     * 
     * @param userName
     * @return
     */
    List<CoreRoleModel> searchUserByRole(String userName);

}
