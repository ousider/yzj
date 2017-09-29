package xn.pigfarm.business.production;

import java.util.List;

import xn.pigfarm.model.production.LeaveModel;

public interface ILeaveBS {

    public void leave(List<LeaveModel> leaveModels, String eventName, long billId);
}
