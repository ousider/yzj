package xn.hana.model.common;

import java.util.List;

public class HanaMtcPgstat {

    // 表行
    private List<HanaMtcPgstatDetail> detailList;

    public List<HanaMtcPgstatDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaMtcPgstatDetail> detailList) {
        this.detailList = detailList;
    }

}
