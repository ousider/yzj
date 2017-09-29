package xn.hana.model.common;

import java.io.Serializable;
import java.util.List;

public class HanaGatherInvoiceRows implements Serializable {

    private static final long serialVersionUID = -7953883363643229279L;

    // 表行
    private List<HanaGatherInvoiceRowsDetail> detailList;

    public List<HanaGatherInvoiceRowsDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaGatherInvoiceRowsDetail> detailList) {
        this.detailList = detailList;
    }
}
