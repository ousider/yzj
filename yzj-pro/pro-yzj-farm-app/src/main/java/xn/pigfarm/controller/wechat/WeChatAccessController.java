package xn.pigfarm.controller.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import xn.core.controller.BaseController;
import xn.pigfarm.model.wechat.AccessToken;
import xn.pigfarm.model.wechat.Menu;
import xn.pigfarm.service.wechat.IWeChatAccessService;
import xn.pigfarm.util.wechat.GetToken;
import xn.pigfarm.util.wechat.MessageUtil;
import xn.pigfarm.util.wechat.WeChatUtil;

/**
 * @Description: 微信接入验证
 * @author Cress
 * @date 2016年8月2日 上午10:39:43
 */
@Component
@Controller
@RequestMapping("/wechat")
public class WeChatAccessController extends BaseController {
    @Autowired
    private IWeChatAccessService weChatAccessService;

    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    public static final String APPID = "wxc301eea2e26d7e75";

    public static final String SECRET = "2f44829f80786be46f8285fce8c5377e";

    public static final String QY_GET_USER_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

    public static final String USERID_TO_OPENID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=ACCESS_TOKEN";
    @RequestMapping(
        method = RequestMethod.GET)
    @ResponseBody
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String msg_signature = request.getParameter("msg_signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        
        PrintWriter out = response.getWriter();
        String result = weChatAccessService.checkSignature(msg_signature, timestamp, nonce, echostr);
        String str = msg_signature + " " + timestamp + " " + nonce + " " + echostr;
        System.out.println("Exception:" + result + " " + request.getRequestURL() + " " + "FourParames:" + str);
        out.print(result);
        out.close();
        out = null;
    };

    @RequestMapping(
        method = RequestMethod.POST)
    @ResponseBody
    public void getMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String msg_signature = request.getParameter("msg_signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        InputStream inputStream = request.getInputStream();    
        String postData = IOUtils.toString(inputStream, "UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = weChatAccessService.messageDecode(msg_signature, timestamp, nonce, postData);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;

            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                } else if ("2".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
                    // message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                } else if ("3".equals(content)) {
                    message = MessageUtil.initImageMessage(toUserName, fromUserName);
                } else if ("?".equals(content) || "？".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                } else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
                    String click_key = map.get("EventKey");
                    if (click_key.equals("version")) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("版本号：v1.0");
                        message = MessageUtil.initText(toUserName, fromUserName, sb.toString());
                    } else if (click_key.equals("tel")) {
                        StringBuffer sb = new StringBuffer();
                        sb.append("联系电话：021-6774 1118\n");
                        sb.append("邮箱：xinnongfeed@163.com\n");
                        sb.append("地址：上海市松江区松江工业区江田东路128号\n");
                        sb.append("邮编： 201613\n");
                        message = MessageUtil.initText(toUserName, fromUserName, sb.toString());
                    }
                }
            } else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
                String label = map.get("Label");
                message = MessageUtil.initText(toUserName, fromUserName, label);
            }
            if (message != null) {
                message = weChatAccessService.messageEncrypt(timestamp, nonce, message);
            }
            out.print(message);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            out.close();
        }
    };

    @RequestMapping("getMediaId")
    @ResponseBody
    public void getMediaId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AccessToken token = new AccessToken();
        token = WeChatUtil.getAccessToken();
        System.out.println("票据：" + token.getToken());
        System.out.println("有效时间" + token.getExpiresIn());
        int deletedFlag = WeChatUtil.deleteMenu(token.getToken());
        if (deletedFlag == 0) {
            System.out.println("菜单删除成功");
            Menu menu = WeChatUtil.initMenu();
            int reslut = WeChatUtil.createMenu(token.getToken(), JSONObject.fromObject(menu).toString());
            if (reslut == 0) {
                System.out.println("菜单创建成功");
            } else {
                System.out.println(reslut);
            }
        } else {
            System.out.println(deletedFlag);
        }

    };
    
    /**
     * 微信登录认证方法开始---------
     */
    @RequestMapping("/getWechatOpenId")
    @ResponseBody
    public JSONObject getWechatOpenId(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String code = getString("code");
        String url = GET_ACCESS_TOKEN_URL.replace("APPID", APPID).replace("SECRET", SECRET).replace("CODE", code);

        JSONObject jsonObject = WeChatUtil.doGetStr(url);

        return jsonObject;
    }

    @RequestMapping("/getWechatUserInfo")
    @ResponseBody
    public JSONObject getWechatUserInfo(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String openId = getString("openId");
        String accessToken = getString("accessToken");
        String url = GET_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);

        JSONObject jsonObject = WeChatUtil.doGetStr(url);

        return jsonObject;
    }
    
    /**
     * 微信登录认证方法结束---------
     */
    @RequestMapping("/getJsapiSignature")
    @ResponseBody
    public Map<String, String> getJsapiSignature(HttpServletResponse response, HttpServletRequest request) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        // 随机字符串
        String noncestr = "Wm3WZYTPz0wzccnW";
        String jsapi_ticket= GetToken.jsapi_ticket;
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String url = request.getParameter("URL");
        String signature = weChatAccessService.getJsSdkSign(noncestr, jsapi_ticket, timestamp, url);

        resultMap.put("noncestr", noncestr);
        resultMap.put("timestamp", timestamp);
        resultMap.put("signature", signature);
        return resultMap;
    }
    
    @RequestMapping("/getUserInfoByCode")
    @ResponseBody
    public Map<String, String> getCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, String> resultMap = new HashMap<String, String>();
        String code = request.getParameter("code");
        String url = QY_GET_USER_INFO_URL.replace("ACCESS_TOKEN", GetToken.token).replace("CODE", code);
        JSONObject jsonObject = WeChatUtil.doGetStr(url);
        String UserId = null, OpenId = null;
        if (jsonObject.has("UserId")) {
            UserId = jsonObject.getString("UserId");
            String toOpenIdUrl = USERID_TO_OPENID_URL.replace("ACCESS_TOKEN", GetToken.token);
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("userid", UserId);
            String parmas = JSONObject.fromObject(paramMap).toString();
            JSONObject jsonObject1 = WeChatUtil.doPostStr(toOpenIdUrl, parmas);
            OpenId = jsonObject1.getString("openid");
        } else if (jsonObject.has("OpenId")) {
            OpenId = jsonObject.getString("OpenId");
        }
        resultMap.put("OpenId", OpenId);
        return resultMap;
    }
}
