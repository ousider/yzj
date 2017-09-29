package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 2025 - 采购应付发票单草稿 - 表行
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaReceiptBillDetail implements Serializable {

    private static final long serialVersionUID = -6235816385779762160L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编码
    private String MTC_DeptID;

    // 新农+单据编号:RowId
    private String MTC_BaseEntry;

    // 新农+单据行号
    private String MTC_BaseLine;

    // 新农+采购收货单编号（入库单）
    private String MTC_OrignEntry;

    // 新农+采购收货单行号（入库单）
    private String MTC_OrignLine;

    // 采购类型
    private String MTC_PurType;

    // 业务类型(PO - 常规采购 PI - 内部采购)
    private String MTC_SalesType;

    // 物料编号
    private String MTC_ItemCode;

    // 仓库编号
    private String MTC_WhsCode;

    // 结账数量
    private String MTC_Qty;

    // 结账金额
    private String MTC_Amount;

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

    public String getMTC_OrignEntry() {
        return MTC_OrignEntry;
    }

    public void setMTC_OrignEntry(String mTC_OrignEntry) {
        MTC_OrignEntry = mTC_OrignEntry;
    }

    public String getMTC_OrignLine() {
        return MTC_OrignLine;
    }

    public void setMTC_OrignLine(String mTC_OrignLine) {
        MTC_OrignLine = mTC_OrignLine;
    }

    public String getMTC_PurType() {
        return MTC_PurType;
    }

    public void setMTC_PurType(String mTC_PurType) {
        MTC_PurType = mTC_PurType;
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

    public String getMTC_WhsCode() {
        return MTC_WhsCode;
    }

    public void setMTC_WhsCode(String mTC_WhsCode) {
        MTC_WhsCode = mTC_WhsCode;
    }

    public String getMTC_Qty() {
        return MTC_Qty;
    }

    public void setMTC_Qty(String mTC_Qty) {
        MTC_Qty = mTC_Qty;
    }

    public String getMTC_Amount() {
        return MTC_Amount;
    }

    public void setMTC_Amount(String mTC_Amount) {
        MTC_Amount = mTC_Amount;
    }

}
