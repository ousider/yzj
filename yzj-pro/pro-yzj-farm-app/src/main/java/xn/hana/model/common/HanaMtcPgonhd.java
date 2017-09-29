package xn.hana.model.common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xn.core.util.time.TimeUtil;

public class HanaMtcPgonhd implements Serializable {

    private static final long serialVersionUID = 4162129534345627500L;

    // 表行
    private List<HanaMtcPgonhdDetail> detailList;

    public List<HanaMtcPgonhdDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<HanaMtcPgonhdDetail> detailList) {
        this.detailList = detailList;
    }


}
