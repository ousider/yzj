package xn.hana.model.common;

import java.io.Serializable;
import java.util.List;

public class HanaMtcPgstatDtl implements Serializable {
    // 表行
    private static final long serialVersionUID = -2490271176122326314L;

    private List<HanaMtcPgstatDtlDetail> detailList;

    public List<HanaMtcPgstatDtlDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaMtcPgstatDtlDetail> detailList) {
        this.detailList = detailList;
    }
}
