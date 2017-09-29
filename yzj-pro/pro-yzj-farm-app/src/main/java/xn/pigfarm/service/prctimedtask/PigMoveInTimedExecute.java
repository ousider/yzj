package xn.pigfarm.service.prctimedtask;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.exception.Thrower;
import xn.core.exception.TimeTaskException;
import xn.core.mapper.system.PrcOrderMapper;
import xn.core.model.system.PrcOrderModel;
import xn.core.service.impl.PrcTimedExecute;

@Service("PigMoveInTimedExecute")
public class PigMoveInTimedExecute extends PrcTimedExecute {

    private static Logger log = Logger.getLogger(PigMoveInTimedExecute.class);

    @Autowired
    private PrcOrderMapper mapper;


    @Override
    public void executePrcTimedTask(Map<String, Object> inParam) throws Exception {
        log.info("执行PigMoveInTimedExecute...................");

        PrcOrderModel model = new PrcOrderModel();
        model.setCompanyId(100l);
        model.setNotes("测试");
        model.setStatus("2");
        model.setDeletedFlag("1");
        // PrcOrderMapper mapper = SpringContextUtil.getMapper(PrcOrderMapper.class);
        mapper.insert(model);
        Thrower.throwException(TimeTaskException.PRC_EXECUTING_ERROR, "测试PigMoveInTimedExecute");
    }
}
