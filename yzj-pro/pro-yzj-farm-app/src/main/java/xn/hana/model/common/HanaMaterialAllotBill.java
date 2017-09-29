package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

/**
 * @Description: 2090 - 库存调拨 - 表头
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaMaterialAllotBill implements Serializable {

    private static final long serialVersionUID = 3660235747474059709L;

    // 分公司编码
    private String MTC_BranchID;

    // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
    private String MTC_BaseEntry;

    // 调拨日期
    private Date MTC_DocDate;

    // 调出仓库
    private String MTC_FromWhsCode;

    // 调入仓库
    private String MTC_ToWhsCode;

    // 调拨人
    private String MTC_EmpName;

    // 表行
    private List<HanaMaterialAllotBillDetail> detailList;

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

    public String getMTC_DocDate() {
        if (MTC_DocDate == null) {
            return null;
        }
        return TimeUtil.format(MTC_DocDate);
    }

    public void setMTC_DocDate(Date mTC_DocDate) {
        MTC_DocDate = mTC_DocDate;
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

    public String getMTC_EmpName() {
        return MTC_EmpName;
    }

    public void setMTC_EmpName(String mTC_EmpName) {
        MTC_EmpName = mTC_EmpName;
    }

    public List<HanaMaterialAllotBillDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaMaterialAllotBillDetail> detailList) {
        this.detailList = detailList;
    }

}
