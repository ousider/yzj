package xn.pigfarm.util.wechat;

import java.io.IOException;

import xn.pigfarm.model.wechat.AccessToken;

public class Test {
    public static void main(String[] args) {
        AccessToken token = new AccessToken();
        token = WeChatUtil.getAccessToken();
        System.out.println("票据：" + token.getToken());
        System.out.println("有效时间" + token.getExpiresIn());
        String path = "C:/Users/Cress/Desktop/桌面文件/logo11_03.png";
        try {
            String mediaId = WeChatUtil.upload(path, token.getToken(), "image");
            System.out.println(mediaId);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
