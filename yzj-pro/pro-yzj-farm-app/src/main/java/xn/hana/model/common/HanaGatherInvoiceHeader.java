package xn.hana.model.common;

import java.io.Serializable;
import java.util.List;

public class HanaGatherInvoiceHeader implements Serializable {

    private static final long serialVersionUID = -4390060381553914897L;

    // 表行
    private List<HanaGatherInvoiceHeaderDetail> detailList;

    public List<HanaGatherInvoiceHeaderDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaGatherInvoiceHeaderDetail> detailList) {
        this.detailList = detailList;
    }

}
