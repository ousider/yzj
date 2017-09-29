package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;

import xn.core.util.time.TimeUtil;

public class HanaMtcPgstatDtlDetail implements Serializable {

    // 生产猪每月存栏位置
    private static final long serialVersionUID = -3256836099285768000L;

    // 期间
    private Date mtc_Period;

    // 分公司编码
    private String mtc_BranchID;

    // 猪只耳号
    private String mtc_ItemCode;

    // 猪只描述
    private String mtc_ItemName;

    // 猪场编号
    private String mtc_DeptID;

    // 公司id
    private String mtc_FarmId;

    // 公司名称
    private String mtc_FarmName;

    // 棚舍编号
    private String mtc_Unit;

    // 棚舍id
    private String mtc_HouseId;

    // 棚舍名称
    private String mtc_HouseName;

    // 胎次
    private String mtc_Parity;

    // 母猪状态
    private String mtc_PregStatus;

    // 状态日期
    private Date mtc_PregDate;

    // 状态持续天数
    private String mtc_DaySum;

    // 妊娠阶段
    private String mtc_PregLevel;

    public String getMtc_Period() {
        return TimeUtil.format(mtc_Period, "yyyy-MM");
    }

    public void setMtc_Period(Date mtc_Period) {
        this.mtc_Period = mtc_Period;
    }

    public String getMtc_BranchID() {
        return mtc_BranchID;
    }

    public void setMtc_BranchID(String mtc_BranchID) {
        this.mtc_BranchID = mtc_BranchID;
    }

    public String getMtc_ItemCode() {
        return mtc_ItemCode;
    }

    public void setMtc_ItemCode(String mtc_ItemCode) {
        this.mtc_ItemCode = mtc_ItemCode;
    }

    public String getMtc_ItemName() {
        return mtc_ItemName;
    }

    public void setMtc_ItemName(String mtc_ItemName) {
        this.mtc_ItemName = mtc_ItemName;
    }

    public String getMtc_DeptID() {
        return mtc_DeptID;
    }

    public void setMtc_DeptID(String mtc_DeptID) {
        this.mtc_DeptID = mtc_DeptID;
    }

    public String getMtc_FarmId() {
        return mtc_FarmId;
    }

    public void setMtc_FarmId(String mtc_FarmId) {
        this.mtc_FarmId = mtc_FarmId;
    }

    public String getMtc_FarmName() {
        return mtc_FarmName;
    }

    public void setMtc_FarmName(String mtc_FarmName) {
        this.mtc_FarmName = mtc_FarmName;
    }

    public String getMtc_Unit() {
        return mtc_Unit;
    }

    public void setMtc_Unit(String mtc_Unit) {
        this.mtc_Unit = mtc_Unit;
    }

    public String getMtc_HouseId() {
        return mtc_HouseId;
    }

    public void setMtc_HouseId(String mtc_HouseId) {
        this.mtc_HouseId = mtc_HouseId;
    }

    public String getMtc_HouseName() {
        return mtc_HouseName;
    }

    public void setMtc_HouseName(String mtc_HouseName) {
        this.mtc_HouseName = mtc_HouseName;
    }

    public String getMtc_Parity() {
        return mtc_Parity;
    }

    public void setMtc_Parity(String mtc_Parity) {
        this.mtc_Parity = mtc_Parity;
    }

    public String getMtc_PregStatus() {
        return mtc_PregStatus;
    }

    public void setMtc_PregStatus(String mtc_PregStatus) {
        this.mtc_PregStatus = mtc_PregStatus;
    }

    public String getMtc_PregDate() {
        return TimeUtil.format(mtc_PregDate, TimeUtil.DATE_FORMAT);
    }

    public void setMtc_PregDate(Date mtc_PregDate) {
        this.mtc_PregDate = mtc_PregDate;
    }

    public String getMtc_DaySum() {
        return mtc_DaySum;
    }

    public void setMtc_DaySum(String mtc_DaySum) {
        this.mtc_DaySum = mtc_DaySum;
    }

    public String getMtc_PregLevel() {
        return mtc_PregLevel;
    }

    public void setMtc_PregLevel(String mtc_PregLevel) {
        this.mtc_PregLevel = mtc_PregLevel;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
