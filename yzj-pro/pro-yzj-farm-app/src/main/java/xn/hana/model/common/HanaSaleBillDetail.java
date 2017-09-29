package xn.hana.model.common;

import java.io.Serializable;

public class HanaSaleBillDetail implements Serializable {

    private static final long serialVersionUID = 5300066877935860994L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编码
    private String MTC_DeptID;

    // 新农+单据编号
    private String MTC_BaseEntry;

    // 新农+单据行号
    private String MTC_BaseLine;

    // 销售类型
    private String MTC_SalesType;

    // 品名
    private String MTC_ItemCode;

    // 猪舍编号
    private String MTC_Unit;

    // 猪只等级
    private String MTC_Grade;

    // 猪只品种
    private String MTC_Breed;

    // 猪只性别
    private String MTC_Sex;

    // 销售数量(头数)
    private String MTC_Qty;

    // 销售重量
    private String MTC_Weight;

    // 数量单价
    private String MTC_QtyPrice;

    // 重量单价
    private String MTC_WeiPrice;

    // 底重
    private String MTC_BotWeight;

    // 底重单价
    private String MTC_BotPrice;

    // 超重
    private String MTC_OverWeight;

    // 超重单价
    private String MTC_OverPrice;

    // 总金额
    private String MTC_Amount;

    // 业务员
    private String MTC_Sales;

    // 销售金额
    private String MTC_SalesAmt;

    // 销售费用
    private String MTC_Fee;

    // 货款差异
    private String MTC_AmtDiff;

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

    public String getMTC_Unit() {
        return MTC_Unit;
    }

    public void setMTC_Unit(String mTC_Unit) {
        MTC_Unit = mTC_Unit;
    }

    public String getMTC_Grade() {
        return MTC_Grade;
    }

    public void setMTC_Grade(String mTC_Grade) {
        MTC_Grade = mTC_Grade;
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

    public String getMTC_Weight() {
        return MTC_Weight;
    }

    public void setMTC_Weight(String mTC_Weight) {
        MTC_Weight = mTC_Weight;
    }

    public String getMTC_QtyPrice() {
        return MTC_QtyPrice;
    }

    public void setMTC_QtyPrice(String mTC_QtyPrice) {
        MTC_QtyPrice = mTC_QtyPrice;
    }

    public String getMTC_WeiPrice() {
        return MTC_WeiPrice;
    }

    public void setMTC_WeiPrice(String mTC_WeiPrice) {
        MTC_WeiPrice = mTC_WeiPrice;
    }

    public String getMTC_BotWeight() {
        return MTC_BotWeight;
    }

    public void setMTC_BotWeight(String mTC_BotWeight) {
        MTC_BotWeight = mTC_BotWeight;
    }

    public String getMTC_BotPrice() {
        return MTC_BotPrice;
    }

    public void setMTC_BotPrice(String mTC_BotPrice) {
        MTC_BotPrice = mTC_BotPrice;
    }

    public String getMTC_OverWeight() {
        return MTC_OverWeight;
    }

    public void setMTC_OverWeight(String mTC_OverWeight) {
        MTC_OverWeight = mTC_OverWeight;
    }

    public String getMTC_OverPrice() {
        return MTC_OverPrice;
    }

    public void setMTC_OverPrice(String mTC_OverPrice) {
        MTC_OverPrice = mTC_OverPrice;
    }

    public String getMTC_Amount() {
        return MTC_Amount;
    }

    public void setMTC_Amount(String mTC_Amount) {
        MTC_Amount = mTC_Amount;
    }

    public String getMTC_Sales() {
        return MTC_Sales;
    }

    public void setMTC_Sales(String mTC_Sales) {
        MTC_Sales = mTC_Sales;
    }

    public String getMTC_SalesAmt() {
        return MTC_SalesAmt;
    }

    public void setMTC_SalesAmt(String mTC_SalesAmt) {
        MTC_SalesAmt = mTC_SalesAmt;
    }

    public String getMTC_Fee() {
        return MTC_Fee;
    }

    public void setMTC_Fee(String mTC_Fee) {
        MTC_Fee = mTC_Fee;
    }

    public String getMTC_AmtDiff() {
        return MTC_AmtDiff;
    }

    public void setMTC_AmtDiff(String mTC_AmtDiff) {
        MTC_AmtDiff = mTC_AmtDiff;
    }

}
