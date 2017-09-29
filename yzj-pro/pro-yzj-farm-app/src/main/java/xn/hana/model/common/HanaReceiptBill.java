package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

/**
 * @Description: 2025 - 采购应付发票单草稿 - 表头
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaReceiptBill implements Serializable {

    private static final long serialVersionUID = 8655895322467134935L;

    // 分公司编码
    private String MTC_BranchID;

    // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
    private String MTC_BaseEntry;

    // 增值税发票号
    private String MTC_FeeCode;

    // 业务伙伴编号
    private String MTC_CardCode;

    // 采购日期
    private Date MTC_DocDate;

    // 到货日期
    private Date MTC_DocDueDate;

    // 采购运费
    private String MTC_LogistCost;

    // 备注
    private String MTC_Comments;

    // 表行
    private List<HanaReceiptBillDetail> detailList;

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

    public String getMTC_FeeCode() {
        return MTC_FeeCode;
    }

    public void setMTC_FeeCode(String mTC_FeeCode) {
        MTC_FeeCode = mTC_FeeCode;
    }

    public String getMTC_CardCode() {
        return MTC_CardCode;
    }

    public void setMTC_CardCode(String mTC_CardCode) {
        MTC_CardCode = mTC_CardCode;
    }

    public String getMTC_DocDate() {
        if (MTC_DocDate == null) {
            return null;
        }
        return TimeUtil.format(MTC_DocDate);
    }

    public void setMTC_DocDate(Date mTC_DocDate) {
        MTC_DocDate = mTC_DocDate;
    }

    public String getMTC_DocDueDate() {
        if (MTC_DocDueDate == null) {
            return null;
        }
        return TimeUtil.format(MTC_DocDueDate);
    }

    public void setMTC_DocDueDate(Date mTC_DocDueDate) {
        MTC_DocDueDate = mTC_DocDueDate;
    }

    public String getMTC_LogistCost() {
        return MTC_LogistCost;
    }

    public void setMTC_LogistCost(String mTC_LogistCost) {
        MTC_LogistCost = mTC_LogistCost;
    }

    public String getMTC_Comments() {
        return MTC_Comments;
    }

    public void setMTC_Comments(String mTC_Comments) {
        MTC_Comments = mTC_Comments;
    }

    public List<HanaReceiptBillDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaReceiptBillDetail> detailList) {
        this.detailList = detailList;
    }

}
