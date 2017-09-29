package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

/**
 * @Description: 2020 - 采购入库单 - 表头
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaInputBill implements Serializable {

    private static final long serialVersionUID = 3128724926838897993L;

    // 分公司编码
    private String MTC_BranchID;

    // 新农+单据编号：分公司编码-单据ROW_ID-单据单号
    private String MTC_BaseEntry;

    // 参考号(合同号)
    private String MTC_NumAtCard;

    // 销售出库单号
    private String MTC_SalesEntry;

    // 业务伙伴编号
    private String MTC_CardCode;

    // 采购日期
    private Date MTC_DocDate;

    // 到货日期
    private Date MTC_DocDueDate;

    // 表行
    private List<HanaInputBillDetail> detailList;

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

    public String getMTC_NumAtCard() {
        return MTC_NumAtCard;
    }

    public void setMTC_NumAtCard(String mTC_NumAtCard) {
        MTC_NumAtCard = mTC_NumAtCard;
    }

    public String getMTC_SalesEntry() {
        return MTC_SalesEntry;
    }

    public void setMTC_SalesEntry(String mTC_SalesEntry) {
        MTC_SalesEntry = mTC_SalesEntry;
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

    public List<HanaInputBillDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaInputBillDetail> detailList) {
        this.detailList = detailList;
    }

}
