package xn.pigfarm.business.production;

import java.util.List;

import xn.pigfarm.model.production.ChangeHouseInfoView;
import xn.pigfarm.model.production.ChangeHouseView;

/**
 * @Description: 转舍BS层
 * @author zhangjs
 * @date 2016年8月23日 下午1:36:45
 */
public interface IChangeHouseBS {

    /**
     * @Description: 转舍
     * @author Administrator
     */

    public List<ChangeHouseInfoView> getChangeHouseInfo(List<Long> ids);

    public void changeHouse(List<ChangeHouseView> changeHouseViews, String EventName, long billId) throws Exception;

    public void checkHouseQty(List<ChangeHouseView> changeHouseViews) throws Exception;

    public void checkHousePigpen(List<Long> inPigpenIds) throws Exception;
}
