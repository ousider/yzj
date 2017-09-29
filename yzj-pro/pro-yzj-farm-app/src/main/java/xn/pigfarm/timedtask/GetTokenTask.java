package xn.pigfarm.timedtask;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.sf.json.JSONObject;
import xn.pigfarm.util.wechat.GetToken;
import xn.pigfarm.util.wechat.WeChatUtil;

public class GetTokenTask implements Job {
    private static final String CORPID = "wxc51ba25ee88a81b1";

    private static final String CORPSECRET = "z9QSa76UUlEATGM3TLwA6h7BnC13fp1HhoDW4CUz7_Bq9FR5OO_KwcpY8Bdapdj1";

    private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

    private static final String JSAPI_TICKET_URL = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKE";

    private static Logger log = Logger.getLogger(GetTokenTask.class);
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

        log.info("开始执行PrcTimedTask...........");
        String url = ACCESS_TOKEN_URL.replace("CORPID", CORPID).replace("CORPSECRET", CORPSECRET);
        JSONObject jsonObject = WeChatUtil.doGetStr(url);
        if (jsonObject != null) {
            GetToken.token = jsonObject.getString("access_token");
            String jspai_url = JSAPI_TICKET_URL.replace("ACCESS_TOKE", GetToken.token);
            JSONObject jspai_jsonObject = WeChatUtil.doGetStr(jspai_url);
            if (jspai_jsonObject != null) {
                GetToken.jsapi_ticket = jspai_jsonObject.getString("ticket");
            }
            System.out.println(GetToken.token + "" + GetToken.jsapi_ticket);
        }
    }

}
