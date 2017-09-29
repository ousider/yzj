package xn.core.cache.controllersession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;

/**
 * @Description: 请求缓存处理
 * @author Zhangjc
 * @date 2016年5月6日 上午10:44:25
 */
public class ServiceContext {

	private static final ThreadLocal<Stack<SessionData>> local = new ThreadLocal<Stack<SessionData>>() {

		protected Stack<SessionData> initialValue() {
			return new Stack<SessionData>();
		};
	};
	
	public static int getLayerSize() {

		return local.get().size();
	}
	
    public static void push(HttpServletRequest request) {

        Map<String, String[]> requestMap = request.getParameterMap();
        
        Map<String, Object> data = new HashMap<String, Object>();
        if (requestMap != null) {
            for (String key : requestMap.keySet()) {
                String[] value = requestMap.get(key);
                if (value.length > 1) {
                    List<String> list = new ArrayList<String>();
                    for (String str : value) {
                        list.add(str);
                    }
                    data.put(key, list);
                } else {
                    String str = value[0];
                    if (StringUtil.isBlank(str)) {
                        str = null;
                    }
                    data.put(key, str);
                }
            }
        }
        BasePageInfo pageInfo = new BasePageInfo(data);
        push(pageInfo, data, false);
    }

    public static void push(BasePageInfo pageInfo) {

        push(pageInfo, new HashMap<String, Object>(), false);
    }

    public static void push(BasePageInfo pageInfo, Map<String, Object> data) {

        push(pageInfo, data, false);
	}
	
	@SuppressWarnings("unchecked")
    public static void push(BasePageInfo pageInfo, Map<String, Object> data, boolean startTransaction) {

        local.get().push(new SessionData(pageInfo, data, startTransaction));
	}
	
	public static SessionData pop() {

		Stack<SessionData> stack = local.get();
		if (!stack.isEmpty()) {
			return stack.pop();
		} else { 
			return null;
		}
	}
	
	private static SessionData getSessionData() {

		Stack<SessionData> stack = local.get();
		if (!stack.isEmpty())
			return stack.peek();
		return null;
	}
	
    public static BasePageInfo getPageInfo() {

		SessionData data = getSessionData();
		return data == null ? null : data.getPagin();
	}
	
    public static Map<String, Object> getData() {

		SessionData data = getSessionData();
		return data == null ? null : data.getData();
	}
}
