package xn.pigfarm.util.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import xn.pigfarm.model.wechat.Image;
import xn.pigfarm.model.wechat.ImageMessage;
import xn.pigfarm.model.wechat.News;
import xn.pigfarm.model.wechat.NewsMessage;
import xn.pigfarm.model.wechat.TextMessage;

public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";

    public static final String MESSAGE_NEWS = "news";

    public static final String MESSAGE_IMAGE = "image";

    public static final String MESSAGE_VOICE = "voice";

    public static final String MESSAGE_VIDEO = "video";

    public static final String MESSAGE_LINK = "link";

    public static final String MESSAGE_LOCATION = "location";

    public static final String MESSAGE_EVENT = "event";

    public static final String MESSAGE_SUBSCRIBE = "subscribe";

    public static final String MESSAGE_SCAN = "SCAN";

    public static final String MESSAGE_CLICK = "click";

    public static final String MESSAGE_VIEW = "VIEW";
    
    /**
     * @Description: XML转MAP集合
     * @author Cress
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();

        List<Element> list = root.elements();

        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * @Description:text转Xml
     * @author Cress
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * @Description: 初始化文本消息
     * @author Cress
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }
    
    /**
     * @Description: 主菜单
     * @author Cress
     * @return
     */
    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作:\n\n");
        sb.append("1、新手引导\n");
        sb.append("2、数据录入\n\n");
        sb.append("回复?调出主菜单。");
        return sb.toString();
    }

    public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("新手引导");
        return sb.toString();
    }

    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("数据录入");
        return sb.toString();
    }

    /**
     * @Description:图文消息转Xml
     * @author Cress
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * @Description: 图文消息初始化
     * @author Cress
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName, String fromUserName) {
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("测试");
        news.setDescription("测试图文消息");
        news.setPicUrl("http://xnplus-wechat.ittun.com/pigfarm-webchat/img/xn+_logo.png");
        news.setUrl("http://www.baidu.com");

        newsList.add(news);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }

    /**
     * @Description:图片消息转Xml
     * @author Cress
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * @Description: 图片消息初始化
     * @author Cress
     * @param toUserName
     * @param fromUserName
     * @return
     * @throws IOException
     */
    public static String initImageMessage(String toUserName, String fromUserName) throws IOException {
        String message = null;
        Image image = new Image();
        String path = "C:/Users/Cress/Desktop/桌面文件/logo11_03.png";
        String mediaId = WeChatUtil.upload(path, GetToken.token, "image");
        image.setMediaId(mediaId);
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        message = imageMessageToXml(imageMessage);
        return message;

    }
}
