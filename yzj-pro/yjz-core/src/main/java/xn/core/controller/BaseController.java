package xn.core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import xn.core.cache.controllersession.ServiceContext;
import xn.core.model.BaseDataModel;
import xn.core.model.BaseModel;
import xn.core.shiro.UserDetail;
import xn.core.util.BeanUtil;
import xn.core.util.JacksonUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.unityreturn.BuildReturnMapUtil;

/**
 * @Description: 控制基类
 * @author Tony.Fang
 * @date 2016年4月13日 下午4:18:58
 */

public abstract class BaseController {
    public static Logger log = Logger.getLogger(BaseController.class);

    private static final String GRID_LIST = "gridList";

    /**
     * @Description: binder用于bean属性的设置
     * @author Tony.Fang
     * @param binder
     */
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        log.info("initBinder");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
     * @Description: 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
     * @author Tony.Fang
     * @param model
     */
	@ModelAttribute
    public void init(ModelMap model) {

		model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}

    /**
     * @Description: 获取分页信息
     * @author Zhangjc
     * @return
     */
    public BasePageInfo getPageInfo() {

        return ServiceContext.getPageInfo();
    }

    /**
     * @Description: 传入List获取分页信息
     * @author Zhangjc
     * @param list
     * @return
     */
    public BasePageInfo getPageInfo(List list) {

        getPageInfo().setList(list);
        return getPageInfo();
    }

    // *****************登录用户相关信息 BEGIN***************************//
    /**
     * @Description:获取SESSION用户信息
     * @author Zhangjc
     * @return
     */
    public UserDetail getUserDetail() {

        return (UserDetail) SecurityUtils.getSubject().getSession().getAttribute(CommonConstants.USER_DETAIL);
    }

    /**
     * @Description:获取SESSION猪场ID
     * @author Zhangjc
     * @return
     */
    public Long getFarmId() {
        return getUserDetail().getFarmId();
    }

    /**
     * @Description:获取SESSION公司ID
     * @author Zhangjc
     * @return
     */
    public Long getCompanyId() {
        return getUserDetail().getCompanyId();
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return getUserDetail().getPassword();
    }

    /**
     * @Description:获取10000账号的ID
     * @author Zhangjc
     * @return
     */
    public Long get10000FarmId() {
        return 2L;
    }

    /**
     * @Description:获取10000账号的公司ID
     * @author Zhangjc
     * @return
     */
    public Long get10000CompanyId() {
        return 1L;
    }

    // *****************登录用户相关信息 END***************************//
    /**
     * @Description: 返回
     * @author Zhangjc
     * @param obj
     * @return
     */
    public Map<String, Object> getReturnMap(Object obj) {

        return BuildReturnMapUtil.getReturnMap(obj);
    }

    /**
     * @Description: 返回
     * @author Zhangjc
     * @return
     */
    public Map<String, Object> getReturnMap() {

        return BuildReturnMapUtil.getReturnMap();
    }


    /**
     * @Description: 返回
     * @author THL
     * @return
     * @throws IOException
     */
    public void getReturnMapToXML(HttpServletResponse response, List<Map<String, Object>> list) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("LIST");
        for (Map<String, Object> map : list) {
            Element mapElement = root.addElement("MAP");
            for (Entry<String, Object> entry : map.entrySet()) {
                Element id1 = mapElement.addElement(entry.getKey());
                id1.setText(String.valueOf(entry.getValue()));
            }
        }

        // 创建字符串缓冲区
        response.setContentType("text/xml; charset=utf-8");
        PrintWriter out = response.getWriter();
        // 设置文件编码
        OutputFormat xmlFormat = new OutputFormat();
        xmlFormat.setEncoding("UTF-8");
        // 设置换行
        // xmlFormat.setNewlines(true);
        // 生成缩进
        // xmlFormat.setIndent(true);
        // 使用4个空格进行缩进, 可以兼容文本编辑器
        // xmlFormat.setIndent(" ");

        // 创建写文件方法
        XMLWriter xmlWriter = new XMLWriter(out, xmlFormat);
        // 写入文件
        xmlWriter.write(document);
        // 完成读写，清空缓存区域
        xmlWriter.flush();
        // 关闭
        xmlWriter.close();
    }

    /**
     * @Description: 根据Json获取对应的List
     * @author Zhangjc
     * @param json
     * @param clazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public <T> List<T> getJsonList(String json, Class<T> clazz) throws IOException {

        if (json != null && json.length() > 0) {
            List<T> list = JacksonUtil.jacksonToCollection(json, ArrayList.class, clazz);
            return list;
        }
        return new ArrayList<>();
    }

    // **********************************************获取参数方法 BEGIN*******************************************//
    /**
     * @Description: 获取入参
     * @author Zhangjc
     * @param request
     * @return Map
     */
    public Map<String, Object> getMap() {

        return ServiceContext.getData() == null ? new HashMap<String, Object>() : ServiceContext.getData();
    }

    /**
     * @Description: 获取String[]
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public String[] getStrings(String name) throws Exception {

        List<String> list = getStrList(name);
        if (list == null || list.isEmpty()) {
            return new String[0];
        }
        String[] ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (StringUtil.isNonBlank(list.get(i))) {
                ids[i] = list.get(i);
            }
        }
        return ids;
    }

    /**
     * @Description: 获取NAME的long入参
     * @author Zhangjc
     * @param name
     * @return
     */
    public Long getLong(String name) {

        return Maps.getLongClass(getMap(), name);
    }

    /**
     * @Description: 获取NAME的long入参 有默认值
     * @author Zhangjc
     * @param name
     * @param defaultValue
     * @return
     */
    public long getLong(String name, long defaultValue) {

        Map<String, Object> map = getMap();
        return Maps.getLong(map, name, defaultValue);
    }

    /**
     * @Description: 获取NAME的入参
     * @author Zhangjc
     * @param name
     * @return
     */
    public String getString(String name) {

        return getString(name, null);
    }

    /**
     * @Description: 获取NAME的入参
     * @author Zhangjc
     * @param name
     * @return
     */
    public String getString(String name, String defaultValue) {

        Map<String, Object> map = getMap();
        return Maps.getString(map, name, defaultValue);
    }

    /**
     * @Description: 获取入参
     * @author Zhangjc
     * @param <T>
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public long[] getIds() {
        List<String> list = getIdList();

        if (list == null || list.isEmpty()) {
            return new long[0];
        }

        long[] ids = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            if (StringUtil.isNonBlank(list.get(i))) {
                ids[i] = Long.parseLong(list.get(i));
            }
        }
        return ids;
    }

    /**
     * @Description: 获取入参 ids
     * @author Zhangjc
     * @param <T>
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    public List<String> getIdList() {

        return getStrList("ids");
    }

    /**
     * @Description: 获取入参
     * @author Zhangjc
     * @param name
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<String> getStrList(String name) {

        Map<String, Object> map = getMap();
        Object obj = map.get(name + "[]");
        if (obj instanceof List) {
            return (List<String>) obj;
        }
        if (obj instanceof String) {
            List<String> result = new ArrayList<>();
            String str = (String) obj;
            if (StringUtil.isNonBlank(str)) {
                String[] strs = str.split(",");
                for (String perStr : strs) {
                    result.add(perStr);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }

    /**
     * @Description: 获取细表的List 继承BaseDataModel
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public String getDetialListStr() {

        return getString(GRID_LIST);
    }

    /**
     * @Description: 获取细表的List 继承BaseDataModel
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public <T extends BaseDataModel> List<T> getDetialList(Class<T> clazz) throws Exception {

        return getDetialList(clazz, GRID_LIST);
    }

    /**
     * @Description: 获取细表的List 继承BaseDataModel
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public <T extends BaseDataModel> List<T> getDetialList(Class<T> clazz, String string) throws Exception {

        String gridList = getString(string);
        return getModelList(gridList, clazz);
    }

    /**
     * @Description: 获取细表的List 未继承BaseDataModel
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public <T extends BaseDataModel> List<T> getDetialListCom(Class<T> clazz) throws Exception {

        return getDetialList(clazz, GRID_LIST);
    }

    /**
     * @Description: 获取细表的List 未继承BaseDataModel
     * @author Zhangjc
     * @return
     * @throws IOException
     * @throws Exception
     */
    public <T extends BaseDataModel> List<T> getDetialListCom(Class<T> clazz, String string) throws IOException {

        String gridList = getString(string);
        return getJsonList(gridList, clazz);
    }

    /**
     * @Description: 根据Json获取对应的List
     * @author Zhangjc
     * @param json
     * @param clazz
     * @return
     * @throws Exception
     */
    public <T extends BaseDataModel> List<T> getModelList(String json, Class<T> clazz) throws Exception {

        return BeanUtil.getModelList(json, clazz);
    }

    /**
     * @Description: 继承了BaseModel的bean
     * @author Zhangjc
     * @param type
     * @return
     * @throws Exception
     */
    public <T extends BaseModel> T getBeanBaseModel(Class<T> type) throws Exception {

        T t = BeanUtil.convertMap(type, getMap());
        BaseModel model = BeanUtil.convertMap(BaseModel.class, getMap());
        t.setCompanyId(model.getCompanyId());
        t.setRowId(model.getRowId());
        t.setDeletedFlag(model.getDeletedFlag());
        t.setLineNumber(model.getLineNumber());
        t.setNotes(model.getNotes());
        t.setOriginApp(model.getOriginApp());
        t.setOriginFlag(model.getOriginFlag());
        t.setSortNbr(model.getSortNbr());
        t.setStatus(model.getStatus());
        t.setFarmId(model.getFarmId());

        return t;
    }

    public Object getBaseModel() throws Exception {

        return BeanUtil.convertMap(BaseModel.class, getMap());
    }

    /**
     * @Description: 继承BaseDataModel的bean
     * @author Zhangjc
     * @param type
     * @return
     * @throws Exception
     */
    public <T extends BaseDataModel> T getBean(Class<T> clazz) throws Exception {

        return BeanUtil.getBean(clazz, getMap());
    }

    /**
     * @Description: 无继承普通bean
     * @author Zhangjc
     * @param type
     * @return
     * @throws Exception
     */
    public <T> T getBeanCom(Class<T> type) throws Exception {

        return BeanUtil.convertMap(type, getMap());
    }
    // **********************************************获取参数方法 END*******************************************//

    // **********************************************刷新缓存***************************************************//
    /**
     * @Description: 刷新缓存表中缓存
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public void basicRefresh() throws Exception {
        basicRefresh(true);
    }

    /**
     * @Description: 刷新猪只信息缓存 PP_L_EAR_CODE CD_L_PIG_CLASS CD_L_BREED CD_L_CODE_LIST 这些表需要修改
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public void pigInfoRefresh() throws Exception {
        pigInfoRefresh(true);
    }

    /**
     * @Description: 刷新所有缓存
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public void cacheRefresh() throws Exception {
        cacheRefresh(true);
    }

    /**
     * @Description: 刷新缓存表中缓存
     * @author zhangjs
     * @param isFarm
     * @throws Exception
     */
    public void basicRefresh(boolean isFarm) throws Exception {
        if (isFarm) {
            CacheUtil.BasicTableCaheRefresh(getFarmId());
        } else {
            CacheUtil.BasicTableCaheRefresh();
        }
    }

    /**
     * @Description: 刷新猪只信息缓存 PP_L_EAR_CODE CD_L_PIG_CLASS CD_L_BREED CD_L_CODE_LIST 这些表需要修改
     * @author Zhangjc
     * @param isFarm
     * @throws Exception
     */
    public void pigInfoRefresh(boolean isFarm) throws Exception {
        if (isFarm) {
            CacheUtil.PigInfoCaheRefresh(getFarmId());
        } else {
            CacheUtil.PigInfoCaheRefresh();
        }
    }

    /**
     * @Description: 刷新所有缓存
     * @author Zhangjc
     * @param isFarm
     * @throws Exception
     */
    public void cacheRefresh(boolean isFarm) throws Exception {
        basicRefresh(isFarm);
        pigInfoRefresh(isFarm);
    }
    
    // ***********************************刷新缓存********************************************//
    public void MaterialInfoCacheRefresh() throws Exception {
        CacheUtil.MaterialInfoCacheRefresh();
    }

}
