package xn.hana.model.common;

import java.io.Serializable;
import java.util.List;

public class HanaProductPigCar implements Serializable {

    private static final long serialVersionUID = 7289434938329600102L;

    // 表行
    private List<HanaProductPigCarDetail> detailList;

    public List<HanaProductPigCarDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaProductPigCarDetail> detailList) {
        this.detailList = detailList;
    }
}
