package xn.hana.model.common;

import java.io.Serializable;

public class MTC_OITM implements Serializable {

    private static final long serialVersionUID = -8812127464981252243L;

    // 猪只耳号
    private String MTC_ItemCode;

    // 猪只描述
    private String MTC_ItemName;

    // 猪只性别
    private String MTC_ItmsGrpCod;

    // 资产类型
    private String MTC_Type;

    // 转生产日龄
    private String MTC_DaysOld;

    // 猪场编号
    private String MTC_DeptID;

    // 棚舍编号
    private String MTC_Unit;

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

    public String getMTC_DeptID() {
        return MTC_DeptID;
    }

    public void setMTC_DeptID(String mTC_DeptID) {
        MTC_DeptID = mTC_DeptID;
    }

    public String getMTC_Unit() {
        return MTC_Unit;
    }

    public void setMTC_Unit(String mTC_Unit) {
        MTC_Unit = mTC_Unit;
    }

    public String getMTC_ItmsGrpCod() {
        return MTC_ItmsGrpCod;
    }

    public void setMTC_ItmsGrpCod(String mTC_ItmsGrpCod) {
        MTC_ItmsGrpCod = mTC_ItmsGrpCod;
    }

    public String getMTC_Type() {
        return MTC_Type;
    }

    public void setMTC_Type(String mTC_Type) {
        MTC_Type = mTC_Type;
    }

    public String getMTC_DaysOld() {
        return MTC_DaysOld;
    }

    public void setMTC_DaysOld(String mTC_DaysOld) {
        MTC_DaysOld = mTC_DaysOld;
    }

}
