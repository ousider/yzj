package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 主数据猪场
 * @author 123456
 * @date 2017年3月24日 上午8:53:39
 */
public class HanaPigFarm implements Serializable {

    private static final long serialVersionUID = 3799489515893131697L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编号
    private String MTC_UnitID;

    // 猪场名称
    private String MTC_UnitName;

    public String getMTC_BranchID() {
        return MTC_BranchID;
    }

    public void setMTC_BranchID(String mTC_BranchID) {
        MTC_BranchID = mTC_BranchID;
    }

    public String getMTC_UnitID() {
        return MTC_UnitID;
    }

    public void setMTC_UnitID(String mTC_UnitID) {
        MTC_UnitID = mTC_UnitID;
    }

    public String getMTC_UnitName() {
        return MTC_UnitName;
    }

    public void setMTC_UnitName(String mTC_UnitName) {
        MTC_UnitName = mTC_UnitName;
    }

}
