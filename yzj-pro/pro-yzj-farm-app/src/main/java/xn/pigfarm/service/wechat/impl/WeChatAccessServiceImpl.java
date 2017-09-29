package xn.pigfarm.service.wechat.impl;

import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import xn.pigfarm.service.wechat.IWeChatAccessService;
import xn.pigfarm.util.wechat.AesException;
import xn.pigfarm.util.wechat.WXBizMsgCrypt;

@Service("weChatAccessService")
public class WeChatAccessServiceImpl implements IWeChatAccessService {
    private static final String token = "bxKkA9qkPYXg0aoBJtdmJv";

    private static final String corpId = "wxc51ba25ee88a81b1";

    private static final String encodingAESKey = "dTTyf37e0DuMQVr2acMydw2PdGWnt7xXwduBPLSSGsu";

    private static final String wechatRedirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    @Override
    public String checkSignature(String msg_signature, String timestamp, String nonce, String echostr) {
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
        }
        catch (AesException e) {
            e.printStackTrace();
        }
        if (result == null) {
            result = token;
        }
        return result;
    }

    @Override
    public Map<String, String> messageDecode(String msg_signature, String timestamp, String nonce, String postData) {
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        Map<String, String> map = new HashMap<String, String>();
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            String sMsg = wxcpt.DecryptMsg(msg_signature, timestamp, nonce, postData);

            StringReader sr = new StringReader(sMsg);
            InputSource is = new InputSource(sr);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
        }
        catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String messageEncrypt(String timestamp, String nonce, String postData) {
        String result = null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpId);
            result = wxcpt.EncryptMsg(postData, timestamp, nonce);
        }
        catch (AesException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getJsSdkSign(String noncestr, String tsapiTicket, String timestamp, String url) {
        String content = "jsapi_ticket=" + tsapiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    public static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    @Override
    public String wechatRedirectUrl(String redirectUrl) {
        String url = wechatRedirectUrl.replace("CORPID", corpId).replace("REDIRECT_URI", redirectUrl).replace("SCOPE", "snsapi_base").replace("STATE",
                token);
        return url;
    }
}
