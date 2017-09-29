package xn.pigfarm.business.production;

import java.util.List;

import xn.pigfarm.model.production.BackfatView;

/**
 * @Description: 背膘测定BS层
 * @author Administrator
 * @date 2016年10月31日 上午10:22:46
 */
public interface IBackfatBS {

    public void backFat(List<BackfatView> backfatViews, String eventName, long billId) throws Exception;
}
