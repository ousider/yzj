package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

/**
 * @Description: 销售主表
 * @author Administrator
 * @date 2017年3月23日 上午11:57:03
 */
public class HanaSaleBill implements Serializable {

    private static final long serialVersionUID = -4574380069867197342L;

    // 分公司编码
    private String MTC_BranchID;

    // 新农+单据编号
    private String MTC_BaseEntry;

    // 业务伙伴编号
    private String MTC_CardCode;

    // 出库日期
    private Date MTC_DocDate;

    // 销售费用
    private String MTC_Fee;

    // 销售费用说明
    private String MTC_FeeRmk;

    // 货款差异
    private String MTC_AmtDiff;

    // 表行
    private List<HanaSaleBillDetail> detailList;

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
        return TimeUtil.format(MTC_DocDate, TimeUtil.TIME_FORMAT);
    }

    public void setMTC_DocDate(Date mTC_DocDate) {
        MTC_DocDate = mTC_DocDate;
    }

    public String getMTC_Fee() {
        return MTC_Fee;
    }

    public void setMTC_Fee(String mTC_Fee) {
        MTC_Fee = mTC_Fee;
    }

    public String getMTC_FeeRmk() {
        return MTC_FeeRmk;
    }

    public void setMTC_FeeRmk(String mTC_FeeRmk) {
        MTC_FeeRmk = mTC_FeeRmk;
    }

    public String getMTC_AmtDiff() {
        return MTC_AmtDiff;
    }

    public void setMTC_AmtDiff(String mTC_AmtDiff) {
        MTC_AmtDiff = mTC_AmtDiff;
    }

    public List<HanaSaleBillDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaSaleBillDetail> detailList) {
        this.detailList = detailList;
    }
}
