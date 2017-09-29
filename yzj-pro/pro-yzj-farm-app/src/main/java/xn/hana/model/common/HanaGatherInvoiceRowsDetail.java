package xn.hana.model.common;

import java.io.Serializable;

public class HanaGatherInvoiceRowsDetail implements Serializable {

    private static final long serialVersionUID = -4377864293970070175L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编码
    private String MTC_DeptID;

    // 新农+单据编号
    private String MTC_BaseEntry;

    // 新农+单据行号
    private String MTC_BaseLine;

    // 销售类型
    /* SO - 常规销售
     * SI - 内部销售
     * SZ - 自宰
     * SP - 后备转生产
     * PD - 生产猪死亡
     * PS - 生产猪销售 */
    private String MTC_SalesType;

    // 品名
    private String MTC_ItemCode;

    // 耳号
    private String MTC_BatchNum;

    // 猪舍编号
    private String MTC_Unit;

    // 猪只品种
    private String MTC_Breed;

    // 猪只性别
    private String MTC_Sex;

    // 转生产头数
    private String MTC_Qty;

    // 价格
    private String MTC_Price;

    // 金额
    private String MTC_Total;

    public String getMTC_BranchID() {
        return MTC_BranchID;
    }

    public void setMTC_BranchID(String mTC_BranchID) {
        MTC_BranchID = mTC_BranchID;
    }

    public String getMTC_DeptID() {
        return MTC_DeptID;
    }

    public void setMTC_DeptID(String mTC_DeptID) {
        MTC_DeptID = mTC_DeptID;
    }

    public String getMTC_BaseEntry() {
        return MTC_BaseEntry;
    }

    public void setMTC_BaseEntry(String mTC_BaseEntry) {
        MTC_BaseEntry = mTC_BaseEntry;
    }

    public String getMTC_BaseLine() {
        return MTC_BaseLine;
    }

    public void setMTC_BaseLine(String mTC_BaseLine) {
        MTC_BaseLine = mTC_BaseLine;
    }

    public String getMTC_SalesType() {
        return MTC_SalesType;
    }

    public void setMTC_SalesType(String mTC_SalesType) {
        MTC_SalesType = mTC_SalesType;
    }

    public String getMTC_ItemCode() {
        return MTC_ItemCode;
    }

    public void setMTC_ItemCode(String mTC_ItemCode) {
        MTC_ItemCode = mTC_ItemCode;
    }

    public String getMTC_BatchNum() {
        return MTC_BatchNum;
    }

    public void setMTC_BatchNum(String mTC_BatchNum) {
        MTC_BatchNum = mTC_BatchNum;
    }

    public String getMTC_Unit() {
        return MTC_Unit;
    }

    public void setMTC_Unit(String mTC_Unit) {
        MTC_Unit = mTC_Unit;
    }

    public String getMTC_Breed() {
        return MTC_Breed;
    }

    public void setMTC_Breed(String mTC_Breed) {
        MTC_Breed = mTC_Breed;
    }

    public String getMTC_Sex() {
        return MTC_Sex;
    }

    public void setMTC_Sex(String mTC_Sex) {
        MTC_Sex = mTC_Sex;
    }

    public String getMTC_Qty() {
        return MTC_Qty;
    }

    public void setMTC_Qty(String mTC_Qty) {
        MTC_Qty = mTC_Qty;
    }

    public String getMTC_Price() {
        return MTC_Price;
    }

    public void setMTC_Price(String mTC_Price) {
        MTC_Price = mTC_Price;
    }

    public String getMTC_Total() {
        return MTC_Total;
    }

    public void setMTC_Total(String mTC_Total) {
        MTC_Total = mTC_Total;
    }

}
