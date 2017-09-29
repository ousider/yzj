package xn.pigfarm.timedtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.data.SqlCon;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.BeanUtil;
import xn.core.util.SpringContextUtil;
import xn.core.util.data.Maps;
import xn.pigfarm.model.backend.MonthPerformanceModel;
import xn.pigfarm.service.backend.IBackEndService;

/**
 * @Description: 定时任务：生产数据汇总周报
 * @author YY
 * @date 2017年8月23日 下午3:08:54
 */
public class PerformanceCalculateTask implements Job {

    private static Logger log = Logger.getLogger(PerformanceCalculateTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行PerformanceCalculateTask...........");
        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);
        IBackEndService backEndService = SpringContextUtil.getBean("backEndService", IBackEndService.class);
        SqlCon getFarmIdSqlCon = new SqlCon();
        getFarmIdSqlCon.addMainSql(
                " SELECT FARM_ID farmId,2 roleType FROM cd_m_month_performance_module WHERE DELETED_FLAG=0 AND IS_START=1 AND ASSESS_REGION <>5 ");
        getFarmIdSqlCon.addMainSql(" GROUP BY FARM_ID ");
        getFarmIdSqlCon.addMainSql(" UNION ALL ");
        getFarmIdSqlCon.addMainSql(
                " SELECT FARM_ID farmId,1 roleType FROM cd_m_month_performance_module WHERE DELETED_FLAG=0 AND IS_START=1 AND ASSESS_REGION =5 ");
        getFarmIdSqlCon.addMainSql(" GROUP BY FARM_ID ");
        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", getFarmIdSqlCon.getCondition());
        List<Map<String, Object>> farmList = paramMapper.getObjectInfos(map);
        if (farmList != null && !farmList.isEmpty()) {
            for (Map<String, Object> farmMap : farmList) {
                try {
                    List<Map<String, Object>> performanceList = backEndService.performanceCalculateToList(farmMap);
                    List<MonthPerformanceModel> monthPerformanceModelList = new ArrayList<MonthPerformanceModel>();
                    if (performanceList != null && !performanceList.isEmpty()) {
                        for (Map<String, Object> performanceMap : performanceList) {
                            MonthPerformanceModel monthPerformanceModel = BeanUtil.getBean(MonthPerformanceModel.class, performanceMap);
                            monthPerformanceModelList.add(monthPerformanceModel);
                        }
                        backEndService.editPerformanceCalculate(monthPerformanceModelList, Maps.getString(farmMap, "roleType"));
                    }
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        log.info("结束执行PerformanceCalculateTask...........");
    }
}
