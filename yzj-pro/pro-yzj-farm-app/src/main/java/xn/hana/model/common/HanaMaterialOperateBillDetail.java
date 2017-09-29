package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 2040-2080 - 采购退货，生产领用，生产退料，库存报废，库存反报废，库存盘点 - 表行
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaMaterialOperateBillDetail implements Serializable {

    private static final long serialVersionUID = 7826274977754220348L;

    // 分公司编码
    private String MTC_BranchID;

    // 猪场编码：MTC_DeptID
    private String MTC_DeptID;

    // 猪舍编号:ROW_ID
    private String MTC_Unit;

    // 新农+单据编号:RowId
    private String MTC_BaseEntry;

    // 新农+单据行号
    private String MTC_BaseLine;

    // 物料编号
    private String MTC_ItemCode;

    // 批次编号
    private String MTC_BatchNum;

    // 仓库编号
    private String MTC_WhsCode;

    // 领用数量
    private String MTC_Qty;

    // 核算对象：A - 商品猪 B - 生产猪
    private String MTC_Collection;

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

    public String getMTC_Unit() {
        return MTC_Unit;
    }

    public void setMTC_Unit(String mTC_Unit) {
        MTC_Unit = mTC_Unit;
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

    public String getMTC_Collection() {
        return MTC_Collection;
    }

    public void setMTC_Collection(String mTC_Collection) {
        MTC_Collection = mTC_Collection;
    }

}
