package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;

import xn.core.util.time.TimeUtil;

public class HanaMtcPgstatDetail implements Serializable {

    // 妊娠母猪状态汇总
    private static final long serialVersionUID = 6725375507080693985L;

    // 分公司编码
    private String mtc_BranchID;

    // 猪场编码
    private String mtc_DeptID;

    // 猪场id
    private String mtc_FarmId;

    // 猪场名称
    private String mtc_FarmName;

    // 猪舍编号
    private String mtc_Unit;

    // 猪舍id
    private String mtc_HouseId;

    // 猪舍名称
    private String mtc_HouseName;

    // 期间
    private Date mtc_Period;

    // 妊娠阶段1
    private String mtc_PregStep_1;

    // 妊娠阶段2
    private String mtc_PregStep_2;

    // 妊娠阶段3
    private String mtc_PregStep_3;

    public String getMtc_BranchID() {
        return mtc_BranchID;
    }

    public void setMtc_BranchID(String mtc_BranchID) {
        this.mtc_BranchID = mtc_BranchID;
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

    public String getMtc_Period() {
        return TimeUtil.format(mtc_Period, "yyyy-MM");
    }

    public void setMtc_Period(Date mtc_Period) {
        this.mtc_Period = mtc_Period;
    }

    public String getMtc_PregStep_1() {
        return mtc_PregStep_1;
    }

    public void setMtc_PregStep_1(String mtc_PregStep_1) {
        this.mtc_PregStep_1 = mtc_PregStep_1;
    }

    public String getMtc_PregStep_2() {
        return mtc_PregStep_2;
    }

    public void setMtc_PregStep_2(String mtc_PregStep_2) {
        this.mtc_PregStep_2 = mtc_PregStep_2;
    }

    public String getMtc_PregStep_3() {
        return mtc_PregStep_3;
    }

    public void setMtc_PregStep_3(String mtc_PregStep_3) {
        this.mtc_PregStep_3 = mtc_PregStep_3;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
