package xn.pigfarm.business.production;

import java.util.List;

import xn.pigfarm.model.production.FosterView;

public interface IFosterBS {

    /**
     * @Description:
     * @author Administrator
     * @param fosterViews
     * @param eventName
     * @param billId
     */
    public void foster(List<FosterView> fosterViews, String eventName, long billId) throws Exception;
}
