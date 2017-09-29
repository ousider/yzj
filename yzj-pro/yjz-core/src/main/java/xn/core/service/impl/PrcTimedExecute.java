package xn.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.exception.TimeTaskException;
import xn.core.mapper.system.PrcOrderMapper;
import xn.core.model.system.PrcOrderModel;
import xn.core.service.IPrcTimedExecute;
import xn.core.util.TimedTaskUtil;
import xn.core.util.time.TimeUtil;

/**
 * @Description: 存储过程的定时任务
 * @author Zhangjc
 * @date 2016年6月14日 上午9:39:54
 */
@Service("PrcTimedExecute")
public class PrcTimedExecute extends BaseServiceImpl implements IPrcTimedExecute {

    private static Logger log = Logger.getLogger(PrcTimedExecute.class);

    @Autowired
    private PrcOrderMapper prcOrderMapper;

    public void beforeExecute(PrcOrderModel model) throws Exception {
        log.info("执行PrcTimedExecute.beforeExecute方法......");
        String orderNo = model.getOrderNo();
        // String condition = " DELETED_FLAG='0' AND STATUS='2' AND ORDER_NO= " + orderNo;
        SqlCon condition = new SqlCon();
        condition.addMainSql(" DELETED_FLAG='0' AND STATUS='2' AND ORDER_NO= " + orderNo);

        List<PrcOrderModel> list = searchPrcOrder(condition);
        // 存在执行中的工单抛异常
        if (list.size() > 0) {
            // 1、正常情况只有1条
            if (list.size() == 1) {
                PrcOrderModel executeModel = list.get(0);
                Date executeDate = executeModel.getExecuteDate();
                Date sysDate = TimeUtil.getSysTimestamp();
                // 1.1、无执行时间则将其置为错误工单
                if (executeDate == null) {
                    setErrorOrder(executeModel, "无执行时间");
                }
                // 1.2、执行时间大于1小时则将其置为错误工单
                if (TimeUtil.compareDate(sysDate, executeDate, 12) >= 60) {
                    setErrorOrder(executeModel, "执行时间超过一小时");
                }
                // 1.3、执行时间在1小时以内
                else {
                    Thrower.throwException(TimeTaskException.HAS_EXECUTE_TASK_ERROR, orderNo);
                }
            }
            // 2、异常情况讲所有工单置为错误工单。这种情况应该不存在
            else {
                setErrorOrders(list, "异常工单");
            }
        }
    }

    @Override
    public void executePrcTimedTask(Map<String, Object> inParam) throws Exception {

    }

    /**
     * @Description: 执行任务
     * @author Zhangjc
     * @param inParam
     * @param orderNo
     * @param orderId
     * @throws Exception
     */
    public void editPrcTimedTask(PrcOrderModel model) throws Exception {
        String inParam = model.getInParam();
        Map<String, Object> map = TimedTaskUtil.parseToMap(inParam);
        executePrcTimedTask(map);
        setSuccessOrder(model);
    }

    // **************************************操作数据库BEGIN***********************************************//
    public List<PrcOrderModel> searchPreFirstPrcOrder() throws Exception {

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T.* FROM  (");
        sqlCon.addMainSql(" SELECT * FROM SYS_L_PRC_ORDER WHERE STATUS='1' AND DELETED_FLAG='0'");
        sqlCon.addMainSql(" ORDER BY CREATE_DATE) T");
        sqlCon.addMainSql(" GROUP BY T.ORDER_NO ");
        return setSql(prcOrderMapper, sqlCon);
    }

    public int editPrcOrder(PrcOrderModel model) throws Exception {

        return prcOrderMapper.update(model);
    }

    public List<PrcOrderModel> searchPrcOrder(SqlCon condition) throws Exception {

        return getAllList(prcOrderMapper, condition);
    }

    @Override
    public void resetOrder(PrcOrderModel model) throws Exception {
        log.info("执行PrcTimedExecute.resetOrder方法......");
        model.setStatus("1");
        editPrcOrder(model);
    }

    @Override
    public void setExecuteOrder(PrcOrderModel model) throws Exception {
        log.info("执行PrcTimedExecute.setExecuteOrder方法......");
        model.setStatus("2");
        model.setExecuteDate(TimeUtil.getSysTimestamp());
        editPrcOrder(model);
        log.info("更改状态为执行中....................");
    }

    @Override
    public void setSuccessOrder(PrcOrderModel model) throws Exception {
        log.info("执行PrcTimedExecute.setSuccessOrder方法......");
        model.setStatus("3");
        editPrcOrder(model);
    }

    @Override
    public void setErrorOrder(PrcOrderModel model, String errorMessage) throws Exception {
        log.info("执行PrcTimedExecute.setErrorOrder方法......");
        model.setStatus("4");
        model.setNotes(errorMessage);
        editPrcOrder(model);
    }

    public void setErrorOrders(List<PrcOrderModel> list, String errorMessage) throws Exception {
        log.info("执行PrcTimedExecute.setErrorOrders 批量设置错误订单方法......");
        for (PrcOrderModel model : list) {
            model.setStatus("4");
            model.setNotes(errorMessage);
        }
        prcOrderMapper.updates(list);
    }

    @Override
    public int insertOrder(String inParam, String orderNo, String orederName, String serviceName) throws Exception {
        PrcOrderModel model = new PrcOrderModel();
        model.setCompanyId(getCompanyId());
        model.setFarmId(getFarmId());
        model.setCreateDate(TimeUtil.getSysTimestamp());
        model.setCreateId(getEmployeeId());
        model.setDeletedFlag("0");
        model.setStatus("1");
        model.setOperaClass(serviceName);
        model.setOrderNo(orderNo);
        model.setOrderName(orederName);
        model.setInParam(inParam);
        return prcOrderMapper.insert(model);
    }

    // **************************************操作数据库END***********************************************//
}
