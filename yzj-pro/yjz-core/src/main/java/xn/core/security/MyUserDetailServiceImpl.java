package xn.core.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import xn.core.model.portal.CoreRoleModel;
import xn.core.model.portal.CoreUserModel;
import xn.core.model.system.MenuView;
import xn.core.service.portal.ISecurityLoginService;

@SuppressWarnings("deprecation")
public class MyUserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private ISecurityLoginService iSecurityLogin;

	private String[] nameByCompanyCode = new String[2];

    private CoreUserModel userModel = new CoreUserModel();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		nameByCompanyCode = username.split(",");
		userModel.setUserName(nameByCompanyCode[0]);
		userModel.setCompanyCode(nameByCompanyCode[1]);
		userModel = iSecurityLogin.userLogin(userModel);
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(userModel.getUserName());
        // 启用
		boolean enabled = true;
        // 账户不过期
		boolean accountNonExpired = true;
        // 凭证不过期
		boolean credentialsNonExpired = true;
        // 非锁定账户
		boolean accountNonLocked = true;
		
		return null;
        // MyUserDetails userdetail = new MyUserDetails(userModel.getHrCompany().getSupCompanyId(), userModel.getRowId(), userModel.getEmployeeId(),
        // userModel.getHrCompany().getRowId(), userModel.getUserName(), userModel.getPassword(), enabled, accountNonExpired,
        // credentialsNonExpired, accountNonLocked, grantedAuths);
        // userdetail.setFarmName(userModel.getHrCompany().getCompanyName());
        // // 查询用户名称
        // userdetail.setEmployName(userModel.getNickName());
        // userdetail.setCompanyCode(nameByCompanyCode[1]);
        // return userdetail;
        
	}

    // 取得用户角色
	private Set<GrantedAuthority> obtionGrantedAuthorities(String name) {

		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        List<CoreRoleModel> rolelist = iSecurityLogin.searchUserByRole(name);
        for (CoreRoleModel CoreRoleModel : rolelist) {

            Iterator<MenuView> iterator = CoreRoleModel.getCdModule().iterator();
			while (iterator.hasNext()) {
                MenuView modulEntity = (MenuView) iterator.next();
				authSet.add(new GrantedAuthorityImpl(modulEntity.getModuleName()));
			}
		}
		return authSet;
	}

}
