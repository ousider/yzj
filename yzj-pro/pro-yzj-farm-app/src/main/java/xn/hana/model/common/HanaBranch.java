package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 主数据 - 分公司
 * @author 123456
 * @date 2017年3月23日 上午9:23:23
 */
public class HanaBranch implements Serializable {

    private static final long serialVersionUID = -6333334596092097275L;

    // 分公司编码
    private String MTC_BranchID;

    // 分公司名称
    private String MTC_BranchName;

    // 分公司简称
    private String MTC_BranchSipName;

    // 省份
    private String MTC_Province;

    // 城市
    private String MTC_City;

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

    public String getMTC_BranchSipName() {
        return MTC_BranchSipName;
    }

    public void setMTC_BranchSipName(String mTC_BranchSipName) {
        MTC_BranchSipName = mTC_BranchSipName;
    }

    public String getMTC_Province() {
        return MTC_Province;
    }

    public void setMTC_Province(String mTC_Province) {
        MTC_Province = mTC_Province;
    }

    public String getMTC_City() {
        return MTC_City;
    }

    public void setMTC_City(String mTC_City) {
        MTC_City = mTC_City;
    }

}
