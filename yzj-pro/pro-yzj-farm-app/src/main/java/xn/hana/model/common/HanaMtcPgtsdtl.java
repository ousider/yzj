package xn.hana.model.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

public class HanaMtcPgtsdtl implements Serializable {
    // 表行
    private List<HanaMtcPgtsdtlDetail> detailList;

    public List<HanaMtcPgtsdtlDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaMtcPgtsdtlDetail> detailList) {
        this.detailList = detailList;
    }
}
