package xn.pigfarm.model.basicinfo;

import java.io.Serializable;

import xn.core.model.BaseDataModel;

/**
 * @Description: 开户
 * @author fangc
 * @date 2016年5月31日 上午11:35:54
 */
public class OpenAccountView  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 3102421207674749244L;

    // 公司类别
    private String companyClass;

    // 公司类型
    private String companyType;

    // 公司代码
    private String companyCode;

    // 公司ID
    private Long companyId;

    // 猪场ID
    private Long farmId;

    // 错误代码
    private String errorCode;

    // 错误信息
    private String errorMessage;

    public String getCompanyClass() {
        return companyClass;
    }

    public void setCompanyClass(String companyClass) {
        this.companyClass = companyClass;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}




