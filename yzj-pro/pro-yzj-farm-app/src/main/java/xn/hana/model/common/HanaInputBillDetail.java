package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;

import xn.core.util.time.TimeUtil;

/**
 * @Description: 2020 - 采购入库单 - 表行
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaInputBillDetail implements Serializable {

    private static final long serialVersionUID = -1220829159598583291L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编码
    private String MTC_DeptID;

    // 新农+单据编号：入库单RowID
    private String MTC_BaseEntry;

    // 新农+单据行号:入库单行号
    private String MTC_BaseLine;

    // 新农+单据编号：采购单RowID
    private String MTC_OrignEntry;

    // 新农+单据行号:采购单购料行号*赠料行号
    private String MTC_OrignLine;

    // 采购类型:购买(有价格)：A 赠送(无价格)：B 促销返利(无价格)：C
    private String MTC_PurType;

    // 返利金额
    private String MTC_Rebate;

    // 业务类型:PO - 常规采购 PI - 内部采购
    private String MTC_SalesType;

    // 物料编号
    private String MTC_ItemCode;

    // 仓库编号
    private String MTC_WhsCode;

    // 入库数量
    private String MTC_Qty;

    // 入库单价
    private String MTC_QtyPrice;

    // 入库金额
    private String MTC_Amount;

    // 批次编号
    private String MTC_BatchNum;

    // 生产日期
    private Date MTC_ProDate;

    // 有效日期
    private Date MTC_ValidDate;

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

    public String getMTC_BatchNum() {
        return MTC_BatchNum;
    }

    public void setMTC_BatchNum(String mTC_BatchNum) {
        MTC_BatchNum = mTC_BatchNum;
    }

    public String getMTC_ProDate() {
        if (MTC_ProDate == null) {
            return null;
        }
        return TimeUtil.format(MTC_ProDate);
    }

    public void setMTC_ProDate(Date mTC_ProDate) {
        MTC_ProDate = mTC_ProDate;
    }

    public String getMTC_ValidDate() {
        if (MTC_ValidDate == null) {
            return null;
        }
        return TimeUtil.format(MTC_ValidDate);
    }

    public void setMTC_ValidDate(Date mTC_ValidDate) {
        MTC_ValidDate = mTC_ValidDate;
    }

}
