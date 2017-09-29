package xn.pigfarm.timedtask;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.data.SqlCon;
import xn.core.util.SpringContextUtil;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.basicinfo.CompanyMapper;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.service.production.IProductionService;

/**
 * @Description: 定时任务：生产数据汇总季报
 * @author YY
 * @date 2017年7月7日 下午3:08:54
 */
public class ProductionReportTaskByYear implements Job {

    private static Logger log = Logger.getLogger(ProductionReportTaskByYear.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行ProductionReportTaskByYear...........");
        CompanyMapper companyMapper = SpringContextUtil.getBean("companyMapper", CompanyMapper.class);
        IProductionService productionService = SpringContextUtil.getBean("ProductionService", IProductionService.class);
        try {
            String startDate = null;
            String endDate = null;
            startDate = TimeUtil.getYearFirst(TimeUtil.dateAddDay(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), -1), TimeUtil.DATE_FORMAT);
            endDate = TimeUtil.getYearLast(TimeUtil.dateAddDay(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), -1), TimeUtil.DATE_FORMAT);
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition("1,2,3", " AND (COMPANY_MARK=? ");
            sqlCon.addCondition("1,2,3,%", " OR COMPANY_MARK LIKE ?) ");
            Map<String, String> SqlMap = new HashMap<String, String>();
            SqlMap.put("condition", sqlCon.getCondition());
            List<CompanyModel> companyModelList = companyMapper.searchListByCon(SqlMap);

            if (companyModelList != null && !companyModelList.isEmpty()) {
                for (CompanyModel companyModel : companyModelList) {
                    productionService.editProduceDataCollect(companyModel.getRowId().toString(), "4", startDate, endDate, null, null, null, startDate,
                            "1");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        log.info("结束执行ProductionReportTaskByYear...........");
    }

}
