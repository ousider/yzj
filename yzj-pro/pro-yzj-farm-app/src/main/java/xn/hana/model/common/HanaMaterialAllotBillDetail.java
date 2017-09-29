package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 2040-2080 - 采购退货，生产领用，生产退料，库存报废，库存反报废，库存盘点 - 表行
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaMaterialAllotBillDetail implements Serializable {

    private static final long serialVersionUID = 5211543687476085337L;

    // 分公司编码
    private String MTC_BranchID;

    // 新农+单据编号:RowId
    private String MTC_BaseEntry;

    // 新农+单据行号
    private String MTC_BaseLine;

    // 物料编号
    private String MTC_ItemCode;

    // 批次编号
    private String MTC_BatchNum;

    // 调出仓库编号
    private String MTC_FromWhsCode;

    // 调入仓库编号
    private String MTC_ToWhsCode;

    // 调拨数量
    private String MTC_Qty;

    public String getMTC_BranchID() {
        return MTC_BranchID;
    }

    public void setMTC_BranchID(String mTC_BranchID) {
        MTC_BranchID = mTC_BranchID;
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

    public String getMTC_FromWhsCode() {
        return MTC_FromWhsCode;
    }

    public void setMTC_FromWhsCode(String mTC_FromWhsCode) {
        MTC_FromWhsCode = mTC_FromWhsCode;
    }

    public String getMTC_ToWhsCode() {
        return MTC_ToWhsCode;
    }

    public void setMTC_ToWhsCode(String mTC_ToWhsCode) {
        MTC_ToWhsCode = mTC_ToWhsCode;
    }

    public String getMTC_Qty() {
        return MTC_Qty;
    }

    public void setMTC_Qty(String mTC_Qty) {
        MTC_Qty = mTC_Qty;
    }

}
