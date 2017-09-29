package xn.hana.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 无表头共用表头
 * @author WC
 * @date 2017年4月27日 上午11:02:26
 */
public class HanaCommitList implements Serializable {

    private static final long serialVersionUID = 4482200612566576869L;

    // 明细
    private List<Object> detailList;

    public List<Object> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Object> detailList) {
        this.detailList = detailList;
    }

}
