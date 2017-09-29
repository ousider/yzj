package xn.pigfarm.service.production;

import java.util.Map;

/**
 * @Description: 撤销事件
 * @author thl
 * @date 2016年7月10日 下午12:06:08
 */
public interface ICancelService {


    /**
     * @Description: 单据、猪只撤销
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editEventCancel(Map<String, Object> inputMap) throws Exception;

}
