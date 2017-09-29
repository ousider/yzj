package xn.core.service;

import java.util.List;
import java.util.Map;

import xn.core.data.SqlCon;
import xn.core.model.system.PrcOrderModel;

/**
 * @Description: 存储过程的定时任务接口
 * @author Zhangjc
 * @date 2016年6月14日 上午9:39:54
 */
public interface IPrcTimedExecute {

    /**
     * @Description: 查询该工单是否有正在执行的任务，有 ： 抛出异常
     * @author Zhangjc
     * @throws Exception
     */
    public void beforeExecute(PrcOrderModel model) throws Exception;

    /**
     * @Description: 工单执行的方法
     * @author Zhangjc
     * @param inParam
     * @throws Exception
     */
    public void executePrcTimedTask(Map<String, Object> inParam) throws Exception;

    /**
     * @Description: 执行任务
     * @author Zhangjc
     * @param inParam
     * @param orderNo
     * @param orderId
     * @throws Exception
     */
    public void editPrcTimedTask(PrcOrderModel model) throws Exception;

    // **************************************操作数据库BEGIN***********************************************//
    /**
     * @Description:获取存储过程的工单 ,每种工单按时间排序取第一条
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public List<PrcOrderModel> searchPreFirstPrcOrder() throws Exception;

    /**
     * @Description: 获取存储过程的工单
     * @author Zhangjc
     * @param condition
     * @return
     * @throws Exception
     */
    public List<PrcOrderModel> searchPrcOrder(SqlCon condition) throws Exception;

    /**
     * @Description: 改存储过程的工单状态
     * @author Zhangjc
     * @param condition
     * @return
     * @throws Exception
     */
    public int editPrcOrder(PrcOrderModel model) throws Exception;

    /**
     * @Description: 重置工单
     * @author Zhangjc
     * @throws Exception
     */
    public void resetOrder(PrcOrderModel model) throws Exception;

    /**
     * @Description: 设置执行中工单
     * @author Zhangjc
     * @throws Exception
     */
    public void setExecuteOrder(PrcOrderModel model) throws Exception;

    /**
     * @Description: 设置执行失败工单
     * @author Zhangjc
     * @throws Exception
     */
    public void setErrorOrder(PrcOrderModel model, String errorMessage) throws Exception;

    /**
     * @Description: 设置执行成功工单
     * @author Zhangjc
     * @throws Exception
     */
    public void setSuccessOrder(PrcOrderModel model) throws Exception;

    /**
     * @Description: 插入工单
     * @author Zhangjc
     * @throws Exception
     */
    public int insertOrder(String inParam, String orderNo, String orderName, String serviceName) throws Exception;

    // **************************************操作数据库END***********************************************//
}
