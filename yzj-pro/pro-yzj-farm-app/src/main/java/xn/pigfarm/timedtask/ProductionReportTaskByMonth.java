package xn.pigfarm.timedtask;

import java.util.ArrayList;
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
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.basicinfo.CompanyMapper;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.service.production.IProductionService;
import xn.pigfarm.util.constants.WechatConstants;

/**
 * @Description: 定时任务：生产数据汇总周报
 * @author YY
 * @date 2017年7月7日 下午3:08:54
 */
public class ProductionReportTaskByMonth implements Job {

    private static Logger log = Logger.getLogger(ProductionReportTaskByMonth.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行ProductionReportTaskByMonth...........");
        CompanyMapper companyMapper = SpringContextUtil.getBean("companyMapper", CompanyMapper.class);
        IProductionService productionService = SpringContextUtil.getBean("ProductionService", IProductionService.class);
        try {
            String startDate = null;
            String endDate = null;
            String searchDate = null;
            endDate = TimeUtil.dateAddDay(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), -1);
            startDate = TimeUtil.getFirstDateOfMonth(endDate);
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition("1,2,3", " AND (COMPANY_MARK=? ");
            sqlCon.addCondition("1,2,3,%", " OR COMPANY_MARK LIKE ?) ");
            Map<String, String> SqlMap = new HashMap<String, String>();
            SqlMap.put("condition", sqlCon.getCondition());
            List<CompanyModel> companyModelList = companyMapper.searchListByCon(SqlMap);

            if (companyModelList != null && !companyModelList.isEmpty()) {
                searchDate = startDate.substring(0, 7);
                for (CompanyModel companyModel : companyModelList) {
                    productionService.editProduceDataCollect(companyModel.getRowId().toString(), "2", startDate, endDate, searchDate, null, null,
                            null, "1");
                    if (companyModel.getRowId() == 3) {
                        Map<String, Object> searchMap = new HashMap<String, Object>();
                        searchMap.put("farmId", companyModel.getRowId());
                        searchMap.put("year", startDate.substring(0, 4));
                        searchMap.put("month", searchDate.substring(5, 7));
                        long reportId = productionService.searchReportIdByFarmIdAndDate(searchMap);
                        if (reportId != 0) {
                            this.sendProductionDailyMessge(reportId, WechatConstants.MESSAGE_TYPE_PRODUCTION_COLLECTION_ANA_1);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        log.info("结束执行ProductionReportTaskByMonth...........");
    }

    public void sendProductionDailyMessge(long reportId, String reportType) {
        IBackEndService backEndServiceImpl = SpringContextUtil.getBean("backEndService", IBackEndService.class);

        List<Map<String, Object>> messageMap = new ArrayList<>();
        try {
            messageMap = backEndServiceImpl.searchMessageByCode(reportType);
            if (messageMap.size() > 0) {
                List<Map<String, Object>> userList = backEndServiceImpl.searchUserIdByMessageId(Maps.getLong(messageMap.get(0), "rowId"));
                String userString = "";
                for (int i = 0; i < userList.size(); i++) {
                    if (i < userList.size() - 1) {
                        userString += Maps.getString(userList.get(i), "QY_USER_ID") + "|";
                    } else {
                        userString += Maps.getString(userList.get(i), "QY_USER_ID");
                    }
                }
                String title = Maps.getString(messageMap.get(0), "messageTitle");
                String picUrl = Maps.getString(messageMap.get(0), "picUrl");
                String contentUrl = Maps.getString(messageMap.get(0), "contentUrl") + "&reportId=" + reportId;
                String description = Maps.getString(messageMap.get(0), "description");
                SendWechatMessageTask sendWechatMessageTask = new SendWechatMessageTask();
                sendWechatMessageTask.sendNewsMessageToWechatUser(userString, "", "", title, description, picUrl, contentUrl);
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
