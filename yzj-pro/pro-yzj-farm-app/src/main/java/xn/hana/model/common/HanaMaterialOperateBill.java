package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

/**
 * @Description: 2040-2080 - 采购退货，生产领用，生产退料，库存报废，库存反报废，库存盘点 - 表头
 * @author THL
 * @date 2017年3月9日 下午3:36:18
 */
public class HanaMaterialOperateBill implements Serializable {

    private static final long serialVersionUID = -5660013970386977156L;

    // 分公司编码
    private String MTC_BranchID;

    // 新农+单据编号：分公司编码-单据ROW_ID-单据单号
    private String MTC_BaseEntry;

    // 出库日期
    private Date MTC_DocDate;

    // 领用人
    private String MTC_EmpName;

    // 表行
    private List<HanaMaterialOperateBillDetail> detailList;

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

    public String getMTC_EmpName() {
        return MTC_EmpName;
    }

    public void setMTC_EmpName(String mTC_EmpName) {
        MTC_EmpName = mTC_EmpName;
    }

    public List<HanaMaterialOperateBillDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaMaterialOperateBillDetail> detailList) {
        this.detailList = detailList;
    }

}
