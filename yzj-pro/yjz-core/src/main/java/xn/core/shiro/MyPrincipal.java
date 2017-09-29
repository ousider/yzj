package xn.core.shiro;

import java.io.Serializable;

/**
 * @Description: 自定义shiro的Principal
 * @author zhangjs
 * @date 2016年7月21日 上午9:09:51
 */
public class MyPrincipal implements Serializable {

    private static final long serialVersionUID = 6464106730095953209L;

    private String userName;
	private String companyCode;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
    /**
     * 构造方法
     * 
     * @param companyCode
     * @param userName
     */
	public MyPrincipal(String companyCode,String userName){
		this.userName = userName;
		this.companyCode = companyCode;
	}

}
