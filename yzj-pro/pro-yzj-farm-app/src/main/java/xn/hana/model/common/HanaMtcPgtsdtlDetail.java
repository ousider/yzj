package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;

import xn.core.util.time.TimeUtil;

public class HanaMtcPgtsdtlDetail implements Serializable {
    // 期间存栏变动汇总

    private static final long serialVersionUID = -3336293853273683002L;

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

    // 猪只类型
    private String mtc_PigType;

    // 期间
    private Date mtc_Period;

    // 日期
    private Date mtc_DocDate;

    // 业务类型
    private String mtc_TransType;

    // 关联猪场
    private String mtc_RelaDeptID;

    // 关联猪场id
    private String mtc_RelaDeptID_FarmId;

    // 关联猪场名称
    private String mtc_RelaDeptID_FarmName;

    // 关联猪舍
    private String mtc_RelaUnit;

    // 关联猪舍id
    private String mtc_RelaUnit_HouseId;

    // 关联猪舍名称
    private String mtc_RelaUnit_HouseName;

    // 调入数量
    private String mtc_InQty;

    // 调入重量
    private String mtc_InWeight;

    // 调出数量
    private String mtc_OutQty;

    // 调出重量
    private String mtc_OutWeight;

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

    public String getMtc_PigType() {
        return mtc_PigType;
    }

    public void setMtc_PigType(String mtc_PigType) {
        this.mtc_PigType = mtc_PigType;
    }

    public String getMtc_Period() {
        return TimeUtil.format(mtc_Period, "yyyy-MM");
    }

    public void setMtc_Period(Date mtc_Period) {
        this.mtc_Period = mtc_Period;
    }

    public String getMtc_DocDate() {
        return TimeUtil.format(mtc_DocDate, TimeUtil.DATE_FORMAT);
    }

    public void setMtc_DocDate(Date mtc_DocDate) {
        this.mtc_DocDate = mtc_DocDate;
    }

    public String getMtc_TransType() {
        return mtc_TransType;
    }

    public void setMtc_TransType(String mtc_TransType) {
        this.mtc_TransType = mtc_TransType;
    }

    public String getMtc_RelaDeptID() {
        return mtc_RelaDeptID;
    }

    public void setMtc_RelaDeptID(String mtc_RelaDeptID) {
        this.mtc_RelaDeptID = mtc_RelaDeptID;
    }

    public String getMtc_RelaDeptID_FarmId() {
        return mtc_RelaDeptID_FarmId;
    }

    public void setMtc_RelaDeptID_FarmId(String mtc_RelaDeptID_FarmId) {
        this.mtc_RelaDeptID_FarmId = mtc_RelaDeptID_FarmId;
    }

    public String getMtc_RelaDeptID_FarmName() {
        return mtc_RelaDeptID_FarmName;
    }

    public void setMtc_RelaDeptID_FarmName(String mtc_RelaDeptID_FarmName) {
        this.mtc_RelaDeptID_FarmName = mtc_RelaDeptID_FarmName;
    }

    public String getMtc_RelaUnit() {
        return mtc_RelaUnit;
    }

    public void setMtc_RelaUnit(String mtc_RelaUnit) {
        this.mtc_RelaUnit = mtc_RelaUnit;
    }

    public String getMtc_RelaUnit_HouseId() {
        return mtc_RelaUnit_HouseId;
    }

    public void setMtc_RelaUnit_HouseId(String mtc_RelaUnit_HouseId) {
        this.mtc_RelaUnit_HouseId = mtc_RelaUnit_HouseId;
    }

    public String getMtc_RelaUnit_HouseName() {
        return mtc_RelaUnit_HouseName;
    }

    public void setMtc_RelaUnit_HouseName(String mtc_RelaUnit_HouseName) {
        this.mtc_RelaUnit_HouseName = mtc_RelaUnit_HouseName;
    }

    public String getMtc_InQty() {
        return mtc_InQty;
    }

    public void setMtc_InQty(String mtc_InQty) {
        this.mtc_InQty = mtc_InQty;
    }

    public String getMtc_InWeight() {
        return mtc_InWeight;
    }

    public void setMtc_InWeight(String mtc_InWeight) {
        this.mtc_InWeight = mtc_InWeight;
    }

    public String getMtc_OutQty() {
        return mtc_OutQty;
    }

    public void setMtc_OutQty(String mtc_OutQty) {
        this.mtc_OutQty = mtc_OutQty;
    }

    public String getMtc_OutWeight() {
        return mtc_OutWeight;
    }

    public void setMtc_OutWeight(String mtc_OutWeight) {
        this.mtc_OutWeight = mtc_OutWeight;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
