package xn.pigfarm.timedtask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.sf.json.JSONObject;
import xn.core.util.SpringContextUtil;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.util.constants.WechatConstants;
import xn.pigfarm.util.wechat.GetToken;
import xn.pigfarm.util.wechat.WeChatUtil;

public class SendWechatMessageTask implements Job {

    private static Logger log = Logger.getLogger(SendWechatMessageTask.class);
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行SendWechatMessageTask...........");
        this.sendUpdateMessge();
    }
    public void sendUpdateMessge() {
        IBackEndService backEndServiceImpl = SpringContextUtil.getBean("backEndService", IBackEndService.class);
        StringBuffer content = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); // 得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = df.format(date);
        List<Map<String, Object>> messageMap = new ArrayList<>();
        try {
            messageMap = backEndServiceImpl.getUpdateContentByDate(newDate);
            if (messageMap.size() > 0) {
                String updateTime = messageMap.get(0).get("updateTime").toString();
                String version = messageMap.get(0).get("version").toString();
                String updateLog = messageMap.get(0).get("updateLog").toString();
                content.append("更新时间：" + updateTime + "\n");
                content.append("版本号：" + version + "\n");
                content.append("更新内容：\n");
                content.append(updateLog);
            }
            this.sendTextMessageToWechatUser("@all", "", "", content.toString());
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public String sendTextMessageToWechatUser(String touser, String toparty, String totag, String content) {
        String accessToken = GetToken.token;
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"touser\":" + "\"" + touser + "\",");
        sb.append("\"toparty\":" + "\"" + toparty + "\",");
        sb.append("\"totag\":" + "\"" + totag + "\",");
        sb.append("\"msgtype\":" + "\"" + "text" + "\",");
        sb.append("\"agentid\":" + "\"" + WeChatUtil.getAgentId() + "\",");
        sb.append("\"text\":" + "{");
        sb.append("\"content\":" + "\"" + content + "\" ");
        sb.append("}}");
        // 请求链接
        String url = WechatConstants.SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject1 = WeChatUtil.doPostStr(url, sb.toString());
        return jsonObject1.toString();
    }

    public String sendNewsMessageToWechatUser(String touser, String toparty, String totag, String title, String description, String picUrl,
            String contentUrl) {
        String accessToken = GetToken.token;
        // WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContext servletContext = webApplicationContext.getServletContext();
        // String path = servletContext.getContextPath();

        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("\"touser\":" + "\"" + touser + "\",");
        sb.append("\"toparty\":" + "\"" + toparty + "\",");
        sb.append("\"totag\":" + "\"" + totag + "\",");
        sb.append("\"msgtype\":" + "\"" + "news" + "\",");
        sb.append("\"agentid\":" + "\"" + WeChatUtil.getAgentId() + "\",");
        sb.append("\"news\":" + "{");
        sb.append("\"articles\":" + "[{");
        sb.append("\"title\":" + "\"" + title + "\",");
        sb.append("\"description\":" + "\"" + description + "\",");
        sb.append("\"url\":" + "\"" + contentUrl + "\",");
        sb.append("\"picurl\":" + "\"" + picUrl + "\"");
        sb.append("}]}}");
        // 请求链接
        String url = WechatConstants.SEND_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject1 = WeChatUtil.doPostStr(url, sb.toString());
        return jsonObject1.toString();
    }
}
