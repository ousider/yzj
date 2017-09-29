package xn.core.shiro;

import xn.core.model.portal.CoreUserModel;

/**
 * @Description: 登录用户信息
 * @author zhangjs
 * @date 2016年7月18日 下午2:42:41
 */
public class UserDetail {
	
	private String username;
	
	private String password;
	
	private long companyId;

    private long userId;

	private long employeeId;

    private long farmId;

    private String employName;

    private String farmName;

    private String companyCode;

    private String isInitPw;

    private String companyMark;

    private String isBloc;

    private long supCompanyId;
	
    private String oaUsername;

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public long getFarmId() {
		return farmId;
	}

	public void setFarmId(long farmId) {
		this.farmId = farmId;
	}

	public String getEmployName() {
		return employName;
	}

	public void setEmployName(String employName) {
		this.employName = employName;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

    public String getIsInitPw() {
        return isInitPw;
    }

    public void setIsInitPw(String isInitPw) {
        this.isInitPw = isInitPw;
    }

    public String getCompanyMark() {
        return companyMark;
    }

    public void setCompanyMark(String companyMark) {
        this.companyMark = companyMark;
    }

    public String getIsBloc() {
        return isBloc;
    }

    public void setIsBloc(String isBloc) {
        this.isBloc = isBloc;
    }

    public long getSupCompanyId() {
        return supCompanyId;
    }

    public void setSupCompanyId(long supCompanyId) {
        this.supCompanyId = supCompanyId;
    }

    public String getOaUsername() {
        return oaUsername;
    }

    public void setOaUsername(String oaUsername) {
        this.oaUsername = oaUsername;
    }

    public UserDetail(String username, String password, long companyId, long userId, long employeeId, long farmId, String employName, String farmName,
            String companyCode, String isInitPw) {
        super();
        this.username = username;
        this.password = password;
        this.companyId = companyId;
        this.userId = userId;
        this.employeeId = employeeId;
        this.farmId = farmId;
        this.employName = employName;
        this.farmName = farmName;
        this.companyCode = companyCode;
        this.isInitPw = isInitPw;
    }

    public UserDetail(CoreUserModel userModel) {
        this.username = userModel.getUserName();
        this.password = userModel.getPassword();
        this.companyId = userModel.getHrCompany().getSupCompanyId();
        this.userId = userModel.getRowId();
        this.employeeId = userModel.getEmployeeId();
        this.farmId = userModel.getHrCompany().getRowId();
        this.employName = userModel.getNickName();
        this.farmName = userModel.getHrCompany().getCompanyName();
        this.companyCode = userModel.getCompanyCode();
        this.isInitPw = userModel.getIsInitPw();
        this.companyMark = userModel.getHrCompany().getCompanyMark();
        this.isBloc = userModel.getHrCompany().getIsBloc();
        this.supCompanyId = userModel.getHrCompany().getSupCompanyId();
        this.oaUsername = userModel.getOaUsername();
    }

	public UserDetail() {
    }

}
