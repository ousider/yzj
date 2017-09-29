package xn.pigfarm.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.constants.CommonConstants;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.system.ActionUrlMapper;
import xn.pigfarm.model.system.ActionUrlModel;
import xn.pigfarm.service.system.ILogService;

@Service("logService")
public class LogServiceImpl extends BaseServiceImpl implements ILogService {

    @Autowired
    private ActionUrlMapper actionUrlMapper;

    @Override
    public void editActionUrl(Long id, String url, String name) throws Exception {
        ActionUrlModel model = new ActionUrlModel();
        model.setActioner(getEmployeeId());
        model.setActionTime(TimeUtil.getSysTimestamp());
        model.setModuleId(id);
        model.setModuleUrl(url);
        model.setModuleName(name);
        model.setCompanyId(getCompanyId());
        model.setDeletedFlag(CommonConstants.DELETED_FLAG);
        model.setStatus(CommonConstants.STATUS);
        model.setFarmId(getFarmId());

        actionUrlMapper.insert(model);
    }

}
