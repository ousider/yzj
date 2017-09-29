package xn.core.service.portal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.mapper.portal.CoreRoleMapper;
import xn.core.mapper.portal.CoreUserMapper;
import xn.core.model.portal.CoreRoleModel;
import xn.core.model.portal.CoreUserModel;
import xn.core.service.portal.ISecurityLoginService;

@Service("SecurityLoginService")
public class SecurityLoginServiceImpl implements ISecurityLoginService {

	@Autowired
    private CoreRoleMapper cdRoleMapper;

	@Autowired
    private CoreUserMapper hrUserMapper;

	@Override
    public CoreUserModel userLogin(CoreUserModel entity) {
        return hrUserMapper.userLogin(entity);
	}

	@Override
    public CoreUserModel searchUserByName(String userName) {
		return hrUserMapper.searchUserByName(userName);
	}

	@Override
    public List<CoreRoleModel> searchUserByResources() {
		return cdRoleMapper.searchUserByResources();
	}

	@Override
    public List<CoreRoleModel> searchUserByRole(String userName) {
		return cdRoleMapper.searchUserByRole(userName);
	}
}
