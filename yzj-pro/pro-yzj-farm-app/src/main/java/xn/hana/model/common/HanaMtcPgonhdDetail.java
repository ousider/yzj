package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;

import xn.core.util.time.TimeUtil;

public class HanaMtcPgonhdDetail implements Serializable {
    // 商品猪存栏日报

    private static final long serialVersionUID = -1987296584242981411L;

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

    // 是否培育舍
    private String mtc_IS_FOSTER_HOUSE;

    // 猪舍名称
    private String mtc_HouseName;

    // 猪只类型 S : 商品猪, H : 后备猪
    private String mtc_PigType;

    // 期间
    private Date mtc_Period;

    // 日期
    private Date mtc_DocDate;

    // 期初存栏
    private String mtc_BegQty;

    // 初生仔猪
    private String mtc_InQty;

    // 新购(后备)入场
    private String mtc_PurQty;

    // 转群转入 - 内转
    private String mtc_InQty_Inner;

    // 转群转入 - 常规
    private String mtc_InQty_Normal;

    // 转群转出 - 内转
    private String mtc_OutQty_Inner;

    // 转群转出 - 常规
    private String mtc_OutQty_Normal;

    // 转出到产房
    private String mtc_TransToCF;

    // 转出到保育
    private String mtc_TransToBY;

    // 转出到培育
    private String mtc_TransToPY;

    // 转出到后备
    private String mtc_TransToHB;

    // 转出到育肥
    private String mtc_TransToYF;

    // 死亡
    private String mtc_DieQty;

    // 自宰
    private String mtc_KillQty;

    // 后备转生产
    private String mtc_TransToSC;

    // 内部销售
    private String mtc_SalesQty_Inner;

    // 外部销售
    private String mtc_SalesQty_Normar;

    // 存栏调整
    private String mtc_AdjQty;

    // 期末存栏
    private String mtc_EndQty;

    // 日龄
    private String mtc_DaySum;

    // 估重
    private String mtc_Weight;

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

    public String getMtc_IS_FOSTER_HOUSE() {
        return mtc_IS_FOSTER_HOUSE;
    }

    public void setMtc_IS_FOSTER_HOUSE(String mtc_IS_FOSTER_HOUSE) {
        this.mtc_IS_FOSTER_HOUSE = mtc_IS_FOSTER_HOUSE;
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

    public String getMtc_BegQty() {
        return mtc_BegQty;
    }

    public void setMtc_BegQty(String mtc_BegQty) {
        this.mtc_BegQty = mtc_BegQty;
    }

    public String getMtc_InQty() {
        return mtc_InQty;
    }

    public void setMtc_InQty(String mtc_InQty) {
        this.mtc_InQty = mtc_InQty;
    }

    public String getMtc_PurQty() {
        return mtc_PurQty;
    }

    public void setMtc_PurQty(String mtc_PurQty) {
        this.mtc_PurQty = mtc_PurQty;
    }

    public String getMtc_InQty_Inner() {
        return mtc_InQty_Inner;
    }

    public void setMtc_InQty_Inner(String mtc_InQty_Inner) {
        this.mtc_InQty_Inner = mtc_InQty_Inner;
    }

    public String getMtc_InQty_Normal() {
        return mtc_InQty_Normal;
    }

    public void setMtc_InQty_Normal(String mtc_InQty_Normal) {
        this.mtc_InQty_Normal = mtc_InQty_Normal;
    }

    public String getMtc_OutQty_Inner() {
        return mtc_OutQty_Inner;
    }

    public void setMtc_OutQty_Inner(String mtc_OutQty_Inner) {
        this.mtc_OutQty_Inner = mtc_OutQty_Inner;
    }

    public String getMtc_OutQty_Normal() {
        return mtc_OutQty_Normal;
    }

    public void setMtc_OutQty_Normal(String mtc_OutQty_Normal) {
        this.mtc_OutQty_Normal = mtc_OutQty_Normal;
    }

    public String getMtc_TransToCF() {
        return mtc_TransToCF;
    }

    public void setMtc_TransToCF(String mtc_TransToCF) {
        this.mtc_TransToCF = mtc_TransToCF;
    }

    public String getMtc_TransToBY() {
        return mtc_TransToBY;
    }

    public void setMtc_TransToBY(String mtc_TransToBY) {
        this.mtc_TransToBY = mtc_TransToBY;
    }

    public String getMtc_TransToPY() {
        return mtc_TransToPY;
    }

    public void setMtc_TransToPY(String mtc_TransToPY) {
        this.mtc_TransToPY = mtc_TransToPY;
    }

    public String getMtc_TransToHB() {
        return mtc_TransToHB;
    }

    public void setMtc_TransToHB(String mtc_TransToHB) {
        this.mtc_TransToHB = mtc_TransToHB;
    }

    public String getMtc_TransToYF() {
        return mtc_TransToYF;
    }

    public void setMtc_TransToYF(String mtc_TransToYF) {
        this.mtc_TransToYF = mtc_TransToYF;
    }

    public String getMtc_DieQty() {
        return mtc_DieQty;
    }

    public void setMtc_DieQty(String mtc_DieQty) {
        this.mtc_DieQty = mtc_DieQty;
    }

    public String getMtc_KillQty() {
        return mtc_KillQty;
    }

    public void setMtc_KillQty(String mtc_KillQty) {
        this.mtc_KillQty = mtc_KillQty;
    }

    public String getMtc_TransToSC() {
        return mtc_TransToSC;
    }

    public void setMtc_TransToSC(String mtc_TransToSC) {
        this.mtc_TransToSC = mtc_TransToSC;
    }

    public String getMtc_SalesQty_Inner() {
        return mtc_SalesQty_Inner;
    }

    public void setMtc_SalesQty_Inner(String mtc_SalesQty_Inner) {
        this.mtc_SalesQty_Inner = mtc_SalesQty_Inner;
    }

    public String getMtc_SalesQty_Normar() {
        return mtc_SalesQty_Normar;
    }

    public void setMtc_SalesQty_Normar(String mtc_SalesQty_Normar) {
        this.mtc_SalesQty_Normar = mtc_SalesQty_Normar;
    }

    public String getMtc_AdjQty() {
        return mtc_AdjQty;
    }

    public void setMtc_AdjQty(String mtc_AdjQty) {
        this.mtc_AdjQty = mtc_AdjQty;
    }

    public String getMtc_EndQty() {
        return mtc_EndQty;
    }

    public void setMtc_EndQty(String mtc_EndQty) {
        this.mtc_EndQty = mtc_EndQty;
    }

    public String getMtc_DaySum() {
        return mtc_DaySum;
    }

    public void setMtc_DaySum(String mtc_DaySum) {
        this.mtc_DaySum = mtc_DaySum;
    }

    public String getMtc_Weight() {
        return mtc_Weight;
    }

    public void setMtc_Weight(String mtc_Weight) {
        this.mtc_Weight = mtc_Weight;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
