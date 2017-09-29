package xn.pigfarm.service.wechat;

import java.util.Map;

public interface IWeChatAccessService {
    /**
     * @Description: 验证加密签名
     * @author Cress
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     * @throws Exception
     */
    public String checkSignature(String signature, String timestamp, String nonce, String echostr);

    /**
     * @Description:xml消息体转换成map
     * @author Cress
     * @param msg_signature
     * @param timestamp
     * @param nonce
     * @param postData
     * @return
     */
    public Map<String, String> messageDecode(String msg_signature, String timestamp, String nonce, String postData);

    /**
     * @Description:发送消息加密
     * @author Cress
     * @param msg_signature
     * @param timestamp
     * @param nonce
     * @param postData
     * @return
     */
    public String messageEncrypt(String timestamp, String nonce, String postData);

    /**
     * @Description:jsapi生成签名
     * @author Cress
     * @param noncestr
     * @param tsapiTicket
     * @param timestamp
     * @param url
     * @return
     */
    public String getJsSdkSign(String noncestr, String tsapiTicket, String timestamp, String url);

    /**
     * @Description: 移动端获取用户信息重定向URL
     * @author Cress
     */
    public String wechatRedirectUrl(String redirectUrl);

}
