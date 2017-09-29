package xn.pigfarm.business.log;

import java.util.List;
import java.util.Map;

import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;

/**
 * @Description: 事件历史记录BS层接口
 * @author Administrator
 * @date 2016年9月22日 下午3:38:07
 */
public interface ILogBS {

    /**
     * @Description: 批量添加历史记录
     * @author Administrator
     * @param pigEventHisModels
     */
    public void createPigEventHises(List<PigEventHisModel> pigEventHisModels, List<PigModel> pigModelList, List<Map<String, Object>> changeHouseList);
}
