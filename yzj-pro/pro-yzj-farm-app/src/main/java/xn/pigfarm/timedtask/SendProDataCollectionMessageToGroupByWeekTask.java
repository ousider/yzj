package xn.pigfarm.timedtask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.data.PropertiesConfig;
import xn.core.data.SqlCon;
import xn.core.data.SysCfg;
import xn.core.util.SpringContextUtil;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.backend.WeekMapper;
import xn.pigfarm.model.backend.WeekModel;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.service.production.IProductionService;
import xn.pigfarm.util.constants.WechatConstants;

public class SendProDataCollectionMessageToGroupByWeekTask implements Job {
    private static Logger log = Logger.getLogger(SendProDataCollectionAnaMessageByMonthTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行SendProDataCollectionMessageToGroupByWeekTask...........");
        WeekMapper weekMapper = SpringContextUtil.getBean("weekMapper", WeekMapper.class);
        IProductionService productionService = SpringContextUtil.getBean("ProductionService", IProductionService.class);

        try {
            String startDate = "";
            String endDate = "";
            String year = null;
            String week = null;
            startDate = TimeUtil.dateAddDay(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), -9);
            endDate = TimeUtil.dateAddDay(TimeUtil.format(new Date(), TimeUtil.DATE_FORMAT), -3);
            SqlCon sqlConWeek = new SqlCon();
            sqlConWeek.addCondition(startDate, " AND START_DATE=? ");
            sqlConWeek.addCondition(endDate, " AND END_DATE=? ");
            sqlConWeek.addCondition(WechatConstants.REPORT_FARM_ID, " AND FARM_ID=? ");
            Map<String, String> SqlMap = new HashMap<String, String>();
            SqlMap.put("condition", sqlConWeek.getCondition());
            List<WeekModel> weekModelList = weekMapper.searchListByCon(SqlMap);
            if (weekModelList != null && !weekModelList.isEmpty()) {
                year = weekModelList.get(0).getYear().toString();
                week = weekModelList.get(0).getWeek().toString();
            }
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("farmId", WechatConstants.REPORT_FARM_ID);
            searchMap.put("year", year);
            searchMap.put("week", week);
            long reportId = productionService.searchReportIdByFarmIdAndDate(searchMap);
            if (reportId != 0) {
                this.SendProductionDataCollectionAnaMessage(reportId, WechatConstants.MESSAGE_TYPE_PRODUCTION_COLLECTION_TO_GROUP);
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("结束执行SendProDataCollectionMessageToGroupByWeekTask...........");
    }

    public void SendProductionDataCollectionAnaMessage(long reportId, String reportType) {
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
                PropertiesConfig cfg = null;
                InputStream in = SysCfg.class.getClassLoader().getResourceAsStream("syscfg.properties");
                cfg = new PropertiesConfig(in);
                Map<String, String> data = new HashMap<>();
                data = cfg.getProperties();
                String finereportUrl = data.get("finereport.url");
                String finereportUsername = data.get("finereport.username");
                String contentUrl = Maps.getString(messageMap.get(0), "contentUrl") + "&dbUrl=" + finereportUrl + "&dbUserName=" + finereportUsername
                        + "&farmId=" + WechatConstants.REPORT_FARM_ID + "&farmName=" + WechatConstants.REPORT_FARM_NAME + "&userName="
                        + WechatConstants.REPORT_EMPLOYEE_NAME + "&reportId=" + reportId + "&op=h5&__bypagesize__=false";
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
