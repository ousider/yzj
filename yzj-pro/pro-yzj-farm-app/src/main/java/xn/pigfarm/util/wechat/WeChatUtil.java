package xn.pigfarm.util.wechat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
import xn.core.util.SpringContextUtil;
import xn.pigfarm.model.wechat.AccessToken;
import xn.pigfarm.model.wechat.Button;
import xn.pigfarm.model.wechat.ClickButton;
import xn.pigfarm.model.wechat.Menu;
import xn.pigfarm.model.wechat.ViewButton;
import xn.pigfarm.util.constants.WechatConstants;

public class WeChatUtil {
    public static final String AGENTID = "29";

    public static final String UPLOAD_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    public static final String CREATE_MENU_URL = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";
    
    public static final String DELETED_MENU_URL = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN&agentid=AGENTID";
    
    /**
     * @Description: Get请求
     * @author Cress
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * @Description: Post请求
     * @author Cress
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * @Description:获取access_token
     * @author Cress
     * @return
     */
    public static AccessToken getAccessToken() {
        AccessToken accessToken = new AccessToken();
        accessToken.setExpiresIn(GetToken.expiresIn);
        accessToken.setToken(GetToken.token);
        return accessToken;
    }

    public static String upload(String filePath, String accessToken, String type) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
        
        URL urlObj = new URL(url);
        // 连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);

        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }

    /**
     * @Description: 组装菜单
     * @author Cress
     * @return
     */
    public static Menu initMenu() {
        Menu menu = new Menu();

        ViewButton button11 = new ViewButton();
        button11.setName("猪场管理");
        button11.setType("view");
        button11.setUrl("http://xnplus-wechat.ittun.com/pigfarm-webchat");

        ViewButton button21 = new ViewButton();
        button21.setName("猪场查询");
        button21.setType("view");
        button21.setUrl("http://www.baidu.com");


        // ClickButton button31 = new ClickButton();
        // button31.setName("扫码事件");
        // button31.setType("click");
        // button31.setKey("31");
        //
        // ClickButton button32 = new ClickButton();
        // button32.setName("地理位置");
        // button32.setType("location_select");
        // button32.setKey("32");
        ClickButton button31 = new ClickButton();
        button31.setName("帮助文档");
        button31.setType("click");
        button31.setKey("helpDoc");

        ClickButton button32 = new ClickButton();
        button32.setName("平台介绍");
        button32.setType("click");
        button32.setKey("introduction");

        ClickButton button33 = new ClickButton();
        button33.setName("联系我们");
        button33.setType("click");
        button33.setKey("connectUs");

        Button button = new Button();
        button.setName("使用帮助");
        button.setSub_button(new Button[] { button31, button32, button33 });
        menu.setButton(new Button[] { button11, button21, button });
        return menu;

    }

    /**
     * @Description: 创建菜单
     * @author Cress
     * @param accessToken
     * @param menu
     * @return
     */
    public static int createMenu(String accessToken, String menu) {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken).replace("AGENTID", AGENTID);
        JSONObject jsonObject = doPostStr(url, menu);
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    /**
     * @Description: 删除菜单
     * @author Cress
     * @param accessToken
     * @param menu
     * @return
     */
    public static int deleteMenu(String accessToken) {
        int result = 0;
        String url = DELETED_MENU_URL.replace("ACCESS_TOKEN", accessToken).replace("AGENTID", AGENTID);
        JSONObject jsonObject = doGetStr(url);
        if (jsonObject != null) {
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    // 获得AGENT_ID
    public static String getAgentId() {
        BasicDataSource dataSource = (BasicDataSource) SpringContextUtil.getBean("dataSource");
        String dataSourceUrl = dataSource.getUrl();
        if (dataSourceUrl.indexOf("jdbc:mysql://192.168.1.253:3306/xnjia_farm?") >= 0) {
            // 正式
            return WechatConstants.AGENT_ID;
        } else {
            // 测试
            return WechatConstants.AGENT_ID_TEST;
        }
    }
}
