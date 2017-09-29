package xn.pigfarm.business.production;

import java.util.List;

import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.ToworkModel;

public interface IToWorkBS {

    public void towork(List<ToworkModel> toworkModels, List<PigModel> pigModelList, String eventName, long billId) throws Exception;
}
