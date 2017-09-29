package xn.pigfarm.mapper.production;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.production.PigEventHisModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-27 13:45:18
 */
public interface PigEventHisMapper extends IMapper<PigEventHisModel> {
  
    // 调用P_PigEventHis存储过程
    public void pigEventHis(PigEventHisModel pigEventHisModel) throws Exception;

}
