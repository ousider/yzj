package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 2010 - 采购订单 - 表行
 * @author THL
 * @date 2017年3月9日 下午3:35:58
 */
public class HanaPurchaseBillDetail implements Serializable {

    private static final long serialVersionUID = 805520340439443673L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编码
    private String MTC_DeptID;

    // 新农+单据编号:RowId
    private String MTC_BaseEntry;

    // 新农+单据行号:购料行号*赠料行号
    private String MTC_BaseLine;

    // 采购类型
    private String MTC_PurType;

    // 返利金额
    private String MTC_Rebate;

    // 业务类型:PO - 常规采购 PI - 内部采购
    private String MTC_SalesType;

    // 物料编号
    private String MTC_ItemCode;

    // 采购数量
    private String MTC_Qty;

    // 采购价格
    private String MTC_QtyPrice;

    // 采购金额
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

    public String getMTC_PurType() {
        return MTC_PurType;
    }

    public void setMTC_PurType(String mTC_PurType) {
        MTC_PurType = mTC_PurType;
    }

    public String getMTC_Rebate() {
        return MTC_Rebate;
    }

    public void setMTC_Rebate(String mTC_Rebate) {
        MTC_Rebate = mTC_Rebate;
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

    public String getMTC_Qty() {
        return MTC_Qty;
    }

    public void setMTC_Qty(String mTC_Qty) {
        MTC_Qty = mTC_Qty;
    }

    public String getMTC_QtyPrice() {
        return MTC_QtyPrice;
    }

    public void setMTC_QtyPrice(String mTC_QtyPrice) {
        MTC_QtyPrice = mTC_QtyPrice;
    }

    public String getMTC_Amount() {
        return MTC_Amount;
    }

    public void setMTC_Amount(String mTC_Amount) {
        MTC_Amount = mTC_Amount;
    }

}
