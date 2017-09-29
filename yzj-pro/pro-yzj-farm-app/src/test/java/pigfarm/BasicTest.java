package pigfarm;

import org.apache.shiro.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xn.core.util.SpringContextUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class BasicTest {

    @Before
    public void before() {
        org.apache.shiro.mgt.SecurityManager securityManager = (org.apache.shiro.mgt.SecurityManager) SpringContextUtil.getBean("securityManager");
        SecurityUtils.setSecurityManager(securityManager);
    }

	@Test
	public void test() {
	}
}
