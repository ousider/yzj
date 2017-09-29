package xn.hana.model.common;

import java.io.Serializable;

/**
 * @Description: 主数据栋舍
 * @author 123456
 * @date 2017年3月24日 上午8:55:01
 */
public class HanaPigHouse implements Serializable {

    private static final long serialVersionUID = -6099569255902425292L;

    // 猪舍编号
    private String MTC_UnitID;

    public String getMTC_UnitID() {
        return MTC_UnitID;
    }

    public void setMTC_UnitID(String mTC_UnitID) {
        MTC_UnitID = mTC_UnitID;
    }

}
