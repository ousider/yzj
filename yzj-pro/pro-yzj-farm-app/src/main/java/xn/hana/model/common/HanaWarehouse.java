package xn.hana.model.common;

import java.io.Serializable;

public class HanaWarehouse implements Serializable{

    private static final long serialVersionUID = 4308234445543014672L;

    // 分公司编号
    private String MTC_BranchID;

    // 分分公司名称

    private String MTC_BranchName;

    // 分仓库编号
    private String MTC_WhsCode;

    // 仓库名称
    private String MTC_WhsName;

    public String getMTC_BranchID() {
        return MTC_BranchID;
    }

    public void setMTC_BranchID(String mTC_BranchID) {
        MTC_BranchID = mTC_BranchID;
    }

    public String getMTC_BranchName() {
        return MTC_BranchName;
    }

    public void setMTC_BranchName(String mTC_BranchName) {
        MTC_BranchName = mTC_BranchName;
    }

    public String getMTC_WhsCode() {
        return MTC_WhsCode;
    }

    public void setMTC_WhsCode(String mTC_WhsCode) {
        MTC_WhsCode = mTC_WhsCode;
    }

    public String getMTC_WhsName() {
        return MTC_WhsName;
    }

    public void setMTC_WhsName(String mTC_WhsName) {
        MTC_WhsName = mTC_WhsName;
    }

}
