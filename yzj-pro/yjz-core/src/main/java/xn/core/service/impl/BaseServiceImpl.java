package xn.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.pagehelper.PageHelper;

import xn.core.cache.controllersession.ServiceContext;
import xn.core.data.SqlCon;
import xn.core.mapper.base.IBaseDataMapper;
import xn.core.mapper.base.IMapper;
import xn.core.mapper.base.IParamMapper;
import xn.core.model.BaseDataModel;
import xn.core.shiro.UserDetail;
import xn.core.util.BeanUtil;
import xn.core.util.JacksonUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;

/**
 * @Description: 控制基类
 * @author Tony.Fang
 * @date 2016年4月13日 下午4:18:58
 */
public abstract class BaseServiceImpl {
    protected static Logger log = Logger.getLogger(BaseServiceImpl.class);

    // *************************************处理数据BEGIN*****************************************************//
    /**
     * @Description: 继承BaseDataModel的bean
     * @author zhangjs
     * @param clazz
     * @param inMap
     * @return
     * @throws Exception
     */
    public <T extends BaseDataModel> T getBean(Class<T> clazz, Map<String, Object> inMap) throws Exception {

        return BeanUtil.getBean(clazz, inMap);
    }

    /**
     * @Description: :获取Model List的Map的list
     * @author Zhangjc
     * @param list
     * @return
     * @throws Exception
     */
    public static <T extends BaseDataModel> List<Map<String, Object>> getMapList(List<T> list) throws Exception {

        return BeanUtil.getMapList(list);
    }
    // ***************************************处理数据END****************************************//

    // *******************************************操作分页BEGIN************************************************************************//
    /**
     * @Description: 获取分页信息
     * @author Zhangjc
     * @return
     */
    public BasePageInfo getPageInfo() {

        return ServiceContext.getPageInfo();
    }

    /**
     * @Description: 获取分页信息
     * @author Zhangjc
     * @param list
     * @return
     */
    public BasePageInfo getPageInfo(List list) {

        ServiceContext.getPageInfo().setList(list);
        return ServiceContext.getPageInfo();
    }

    /**
     * @Description: 分页查询工具
     * @author Zhangjc
     */
    public void setToPage(){

        PageHelper.startPage(getPageInfo().getPage(), getPageInfo().getRows());
    }
    // *******************************************操作分页END************************************************************************//

    // *******************************************获取缓存方法BEGIN************************************************************************//
    /**
     * @Description: 获取SESSION中的用户信息
     * @author Zhangjc
     * @return
     */
    public UserDetail getUserDetail() {
        return (UserDetail) SecurityUtils.getSubject().getSession().getAttribute(CommonConstants.USER_DETAIL);
    }

    /**
     * @Description: 猪场ID
     * @author zhangjs5
     * @return
     */
    public long getFarmId() {

        return getUserDetail().getFarmId();
    }

    /**
     * userId
     */
    public long getUserId() {

        return getUserDetail().getUserId();
    }

    /**
     * @Description: 集团ID
     * @author zhangjs5
     * @return
     */
    public long getCompanyId() {

        return getUserDetail().getCompanyId();
    }

    /**
     * @Description: 员工ID
     * @author zhangjs5
     * @return
     */
    public long getEmployeeId() {

        return getUserDetail().getEmployeeId();
    }

    /**
     * @Description: 是否初始化密码
     * @author Administrator
     * @return
     */
    public String getIsInitPw() {

        return getUserDetail().getIsInitPw();
    }

    /**
     * @Description: 是否初始化密码
     * @author Administrator
     * @return
     */
    public String getOaUsername() {

        return getUserDetail().getOaUsername();
    }

    /**
     * @Description:获取10000账号的猪场ID
     * @author Zhangjc
     * @return
     */
    public long get10000FarmId() {
        return 2L;
    }

    /**
     * @Description:获取10000账号的公司ID
     * @author Zhangjc
     * @return
     */
    public long get10000CompanyId() {
        return 1L;
    }

    /**
     * @Description: 获取公司标识，方便查询下属公司
     * @author zhangjs
     * @return
     */
    public String getCompanyMark() {
        return getUserDetail().getCompanyMark();
    }

    /**
     * @Description: 获取公司编码
     * @author Cress
     * @return
     */
    public String getCompanyCode() {
        return getUserDetail().getCompanyCode();
    }

    /**
     * @Description:获取上级公司ID
     * @author Cress
     * @return
     */
    public long getSupCompanyId() {
        return getUserDetail().getSupCompanyId();
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
    public <T> List<T> getJsonList(String json, Class<T> clazz) {

        if (json != null && json.length() > 0) {
            List<T> list;
            try {
                list = JacksonUtil.jacksonToCollection(json, ArrayList.class, clazz);
                return list;
            }
            catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return new ArrayList<>();
    }


    /**
     * @Description: 根据Json获取对应的MapList
     * @author zhangjs
     * @param json
     * @return
     */
    public List<Map<String, Object>> getMapList(String json) {

        if (json != null && json.length() > 0) {
            List<Map<String, Object>> list;
            try {
                list = JacksonUtil.jacksonToCollection(json, ArrayList.class, HashMap.class);
                return list;
            }
            catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return new ArrayList<>();
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

        List<T> result = new ArrayList<>();
        if (StringUtil.isBlank(json)) {
            return result;
        }

        List<Map<String, Object>> list = JacksonUtil.jacksonToCollection(json, ArrayList.class, HashMap.class);
        if (list == null || list.isEmpty()) {
            return result;
        }

        T t = clazz.newInstance();
        List<String> proper = t.getPropertes();

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            t = clazz.newInstance();
            for (Entry<String, Object> set : map.entrySet()) {
                if (proper.contains(set.getKey())) {
                    t.set(set.getKey(), set.getValue());
                }
            }
            result.add(t);
        }
        return result;
    }
    // *******************************************获取缓存方法END************************************************************************//

    // *******************************************操作数据库方法 BEGIN************************************************************************//
    /**
     * @Description:直接传入参数操作
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     */
    public <T> List<T> setSql(IBaseDataMapper<T> mapper, SqlCon condition) {

        Map<String, String> map = new HashMap<>();
        map.put("sql", condition.getCondition());
        List<T> list = mapper.operSql(map);
        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * @Description: 根据条件查询有效数据
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     * @throws Exception
     */
    public <T> List<T> getList(IBaseDataMapper<T> mapper, SqlCon condition) {

        Map<String, String> map = new HashMap<>();
        map.put("condition", condition.getCondition());
        List<T> list = mapper.searchListByCon(map);

        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * @Description: 根据条件查询有效Model
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     * @throws Exception
     */
    public <T> T getModel(IBaseDataMapper<T> mapper, SqlCon condition) throws Exception {

        T t = null;
        List<T> list = getList(mapper, condition);
        if (!list.isEmpty()) {
            t = list.get(0);
        }

        return t;
    }

    /**
     * @Description: 根据条件查询有效Model
     * @author zhangjs
     * @param mapper
     * @param condition
     * @param c
     * @return
     * @throws Exception
     */
    public <T> T getModel(IBaseDataMapper<T> mapper, SqlCon condition, Class<T> c) throws Exception {

        T t = null;
        List<T> list = getList(mapper, condition);
        if (!list.isEmpty()) {
            t = list.get(0);
        }

        if (t == null) {
            t = c.newInstance();
        }
        return t;
    }

    /**
     * @Description: 根据条件查询所有数据
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     */
    public <T> List<T> getAllList(IBaseDataMapper<T> mapper, SqlCon condition) {

        Map<String, String> map = new HashMap<>();
        map.put("condition", condition.getCondition());

        List<T> list = mapper.searchAllListByCon(map);
        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param id
     * @param condition
     * @return
     */
    public <T> int setDeletes(IBaseDataMapper<T> mapper, List<Long> ids, String condition) {

        List<Map<String, Object>> list = new ArrayList<>();

        if (ids != null && !ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("RECORD_CON", condition);
                map.put("RECORD_VALUES", ids.get(i));
                list.add(map);
            }
            return mapper.deletesByCon(list, getFarmId());
        }
        return 0;
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param id
     * @param condition
     * @return
     */
    public <T> int setDeletes(IBaseDataMapper<T> mapper, long id, String condition) {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("RECORD_CON", condition);
        map.put("RECORD_VALUES", id);
        list.add(map);
        return mapper.deletesByCon(list, getFarmId());
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param ids
     * @param condition
     * @return
     */
    public <T> int setDeletes(IBaseDataMapper<T> mapper, long[] ids, String condition) {

        List<Map<String, Object>> list = new ArrayList<>();

        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("RECORD_CON", condition);
                map.put("RECORD_VALUES", ids[i]);
                list.add(map);
            }
            return mapper.deletesByCon(list, getFarmId());
        }
        return 0;
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param id
     * @param condition
     * @return
     */
    public <T> int setDeletes(IBaseDataMapper<T> mapper, String id, String condition) {

        String[] ids = StringUtil.split(id, ",");
        return setDeletes(mapper, ids, condition);
    }

    /**
     * @Description: 根据条件删除数据
     * @author Zhangjc
     * @param mapper
     * @param ids
     * @param condition
     * @return
     */
    public <T> int setDeletes(IBaseDataMapper<T> mapper, String[] ids, String condition) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("RECORD_CON", condition);
                map.put("RECORD_VALUES", ids[i]);
                list.add(map);
            }
            return mapper.deletesByCon(list, getFarmId());
        }
        return 0;
    }

    /**
     * @Description: 根据多个条件删除数据
     * @author Zhangjc
     * @param mapper
     * @param ids
     * @param condition
     * @return
     */
    public <T> int setDeletesM(IBaseDataMapper<T> mapper, List<SqlCon> sqlCons) {

        List<String> list = new ArrayList<String>();

        if (sqlCons != null && !sqlCons.isEmpty()) {
            for (int i = 0; i < sqlCons.size(); i++) {
                list.add(sqlCons.get(i).getCondition());
            }
            return mapper.deletesByCons(list);
        }
        return 0;
    }

    /**
     * @Description:直接传入参数操作
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     */
    public <T> List<T> setSql(IMapper<T> mapper, SqlCon condition) {
        
        Map<String, String> map = new HashMap<>();
        map.put("sql", condition.getCondition());
        List<T> list = mapper.operSql(map);
        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * @Description: 根据条件查询有效数据
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     * @throws Exception
     */
    public <T> List<T> getList(IMapper<T> mapper, SqlCon condition) {

        Map<String, String> map = new HashMap<>();
        map.put("condition", condition.getCondition());
        List<T> list = mapper.searchListByCon(map);

        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * @Description: 根据条件查询有效Model
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     * @throws Exception
     */
    public <T> T getModel(IMapper<T> mapper, SqlCon condition) throws Exception {

        T t = null;
        List<T> list = getList(mapper, condition);
        if (!list.isEmpty()) {
            t = list.get(0);
        }

        return t;
    }

    /**
     * @Description: 根据条件查询有效Model
     * @author zhangjs
     * @param mapper
     * @param condition
     * @param c
     * @return
     * @throws Exception
     */
    public <T> T getModel(IMapper<T> mapper, SqlCon condition, Class<T> c) throws Exception {

        T t = null;
        List<T> list = getList(mapper, condition);
        if (!list.isEmpty()) {
            t = list.get(0);
        }

        if (t == null) {
            t = c.newInstance();
        }
        return t;
    }

    /**
     * @Description: 根据条件查询所有数据
     * @author Zhangjc
     * @param mapper
     * @param condition
     * @return
     */
    public <T> List<T> getAllList(IMapper<T> mapper, SqlCon condition) {

        Map<String, String> map = new HashMap<>();
        map.put("condition", condition.getCondition());

        List<T> list = mapper.searchAllListByCon(map);
        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param id
     * @param condition
     * @return
     */
    public <T> int setDeletes(IMapper<T> mapper, List<Long> ids, String condition) {

        List<Map<String, Object>> list = new ArrayList<>();

        if (ids != null && !ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("RECORD_CON", condition);
                map.put("RECORD_VALUES", ids.get(i));
                list.add(map);
            }
            return mapper.deletesByCon(list, getFarmId());
        }
        return 0;
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param id
     * @param condition
     * @return
     */
    public <T> int setDeletes(IMapper<T> mapper, long id, String condition) {

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("RECORD_CON", condition);
        map.put("RECORD_VALUES", id);
        list.add(map);
        return mapper.deletesByCon(list, getFarmId());
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param ids
     * @param condition
     * @return
     */
    public <T> int setDeletes(IMapper<T> mapper, long[] ids, String condition) {

        List<Map<String, Object>> list = new ArrayList<>();
        
        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("RECORD_CON", condition);
                map.put("RECORD_VALUES", ids[i]);
                list.add(map);
            }
            return mapper.deletesByCon(list, getFarmId());
        }
        return 0;
    }

    /**
     * @Description: 根据条件删除数据
     * @author zhangjs
     * @param mapper
     * @param id
     * @param condition
     * @return
     */
    public <T> int setDeletes(IMapper<T> mapper, String id, String condition) {

        String[] ids = StringUtil.split(id, ",");
        return setDeletes(mapper, ids, condition);
    }

    /**
     * @Description: 根据条件删除数据
     * @author Zhangjc
     * @param mapper
     * @param ids
     * @param condition
     * @return
     */
    public <T> int setDeletes(IMapper<T> mapper, String[] ids, String condition) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (ids != null && ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("RECORD_CON", condition);
                map.put("RECORD_VALUES", ids[i]);
                list.add(map);
            }
            return mapper.deletesByCon(list, getFarmId());
        }
        return 0;
    }

    /**
     * @Description: 根据多个条件删除数据
     * @author Zhangjc
     * @param mapper
     * @param ids
     * @param condition
     * @return
     */
    public <T> int setDeletesM(IMapper<T> mapper, List<SqlCon> sqlCons) {

        List<String> list = new ArrayList<String>();

        if (sqlCons != null && !sqlCons.isEmpty()) {
            for (int i = 0; i < sqlCons.size(); i++) {
                list.add(sqlCons.get(i).getCondition());
            }
            return mapper.deletesByCons(list);
        }
        return 0;
    }

    public List<Map<String, Object>> paramMapperSetSQL(IParamMapper paramMapper, SqlCon sqlCon) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        if (list == null || list.isEmpty() || (list.size() == 1 && list.get(0) == null)) {
            list = new ArrayList<>();
        }
        return list;
    }
    // *******************************************操作数据库方法 END************************************************************************//
}
