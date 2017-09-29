package xn.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import xn.core.model.portal.CoreRoleModel;
import xn.core.model.system.MenuView;
import xn.core.service.portal.ISecurityLoginService;

public class MySecurityMetadateSoure implements FilterInvocationSecurityMetadataSource {

	private ISecurityLoginService iSecurityLogin;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	private RequestMatcher pathMatcher;

	// 返回请求资源需要的权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			pathMatcher = new AntPathRequestMatcher(entry.getKey());
			if (pathMatcher.matches(request)) {
				return entry.getValue();
			}
		}
		// 返回null表示不会验证
		return null;
	}

	// 加载角色对应模块
	private void loadResourceDefine() {

		// 创建角色对应模块Map
        List<CoreRoleModel> cdomodules = this.iSecurityLogin.searchUserByResources();
		
		if (cdomodules != null) {
            for (CoreRoleModel CdRoleModel : cdomodules) {
				// 创建一个spring的secuity的 list
				Collection<ConfigAttribute> configattributes = new ArrayList<ConfigAttribute>();
				// 获取角色对应的模块
                for (Iterator<MenuView> iterator = CdRoleModel.getCdModule().iterator(); iterator.hasNext();) {
                    MenuView modulEntity = (MenuView) iterator.next();
					// 以角色对应模块添加进 Map 对象
					if ((modulEntity.getModuleUrl() != null) && !"".equals(modulEntity.getModuleUrl())) {
						// 以模块名封装spring的secuity object
						ConfigAttribute configattribute = new SecurityConfig(modulEntity.getModuleName());
						// 添加进spring的secuity的 list
						configattributes.add(configattribute);
						resourceMap.put(modulEntity.getModuleUrl(), configattributes);
					}
				}
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	public MySecurityMetadateSoure(ISecurityLoginService iSecurityLogin) {
		this.iSecurityLogin = iSecurityLogin;
		loadResourceDefine();
	}

	public ISecurityLoginService getiSecurityLogin() {
		return iSecurityLogin;
	}

	public void setiSecurityLogin(ISecurityLoginService iSecurityLogin) {
		this.iSecurityLogin = iSecurityLogin;
	}

}
