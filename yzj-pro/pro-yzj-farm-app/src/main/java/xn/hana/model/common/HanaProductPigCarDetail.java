package xn.hana.model.common;

public class HanaProductPigCarDetail {
    // 分公司编码
    private String MTC_BranchID;

    // 猪只耳号
    private String MTC_ItemCode;

    // 猪只描述
    private String MTC_ItemName;

    // 性别
    private String MTC_Sex;

    // 棚舍编号
    private String MTC_Unit;

    public String getMTC_BranchID() {
        return MTC_BranchID;
    }

    public void setMTC_BranchID(String mTC_BranchID) {
        MTC_BranchID = mTC_BranchID;
    }

    public String getMTC_ItemCode() {
        return MTC_ItemCode;
    }

    public void setMTC_ItemCode(String mTC_ItemCode) {
        MTC_ItemCode = mTC_ItemCode;
    }

    public String getMTC_ItemName() {
        return MTC_ItemName;
    }

    public void setMTC_ItemName(String mTC_ItemName) {
        MTC_ItemName = mTC_ItemName;
    }

    public String getMTC_Sex() {
        return MTC_Sex;
    }

    public void setMTC_Sex(String mTC_Sex) {
        MTC_Sex = mTC_Sex;
    }

    public String getMTC_Unit() {
        return MTC_Unit;
    }

    public void setMTC_Unit(String mTC_Unit) {
        MTC_Unit = mTC_Unit;
    }

}
