package xn.core.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import xn.core.model.BaseDataModel;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;

public class BeanUtil {

    private static Logger log = Logger.getLogger(BeanUtil.class);

    /**
     * @Description: 将javabean转化成Map
     * @author Zhangjc
     * @param bean
     * @return
     * @throws Exception
     */
    public static Map<String, Object> convertBean(Object bean) throws Exception {

        Class type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

    /**
     * @Description: Map转化成bean
     * @author Zhangjc
     * @param <T>
     * @param clazz
     * @param map
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IntrospectionException
     * @throws Exception
     */
    public static <T> T convertMap(Class<T> clazz, Map<String, Object> map) throws IntrospectionException {

        // 创建 JavaBean 对象
        T t;
        try {
            t = clazz.newInstance();
        }
        catch (InstantiationException e1) {
            log.error("获取实现类出错" + e1);
            return null;
        }
        catch (IllegalAccessException e1) {
            log.error("获取实现类出错" + e1);
            return null;
        }

        // 获取类属性
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String key = descriptor.getName();

            if (!map.containsKey(key)) {
                continue;
            }
            // 这样当一个属性赋值失败的时候就不会影响其他属性赋值。
            try {
                Object value = map.get(key);
                Method setter = descriptor.getWriteMethod();
                Class[] types = setter.getParameterTypes();
                if (types.length == 1) {
                    String setterType = types[0].getSimpleName();
                    invokeSetter(setterType, setter, t, value);
                } else {
                    setter.invoke(t, value);
                }
            }
            catch (Exception e) {
                log.error("转化出现错误" + e);
            }
        }
        return t;
    }

    /**
     * @Description:执行setter方法
     * @author Zhangjc
     * @param setterType
     * @param setter
     * @param obj
     * @param value
     * @throws Exception
     */
    private static void invokeSetter(String setterType, Method setter, Object obj, Object value) throws Exception {

        // 第一个参数:从中调用基础方法的对象 第二个参数:用于方法调用的参数
        if ("int".equals(setterType) || "Integer".equals(setterType)) {
            setter.invoke(obj, new Object[] { new Integer("" + value) });
        } else if ("double".equals(setterType) || "Double".equals(setterType)) {
            setter.invoke(obj, new Object[] { new Double("" + value) });
        } else if ("float".equals(setterType) || "Float".equals(setterType)) {
            setter.invoke(obj, new Object[] { new Float("" + value) });
        } else if ("long".equals(setterType) || "Long".equals(setterType)) {
            setter.invoke(obj, new Object[] { new Long("" + value) });
        } else if ("boolean".equals(setterType) || "Boolean".equals(setterType)) {
            setter.invoke(obj, new Object[] { Boolean.valueOf("" + value) });
        } else if ("BigDecimal".equals(setterType)) {
            setter.invoke(obj, new Object[] { new BigDecimal("" + value) });
        } else if ("Date".equals(setterType)) {
            Date date;
            if (value instanceof Date) {
                date = (Date) value;
            } else {
                date = TimeUtil.parse((String) value);
            }
            if (date != null) {
                setter.invoke(obj, new Object[] { date });
            }
        } else if ("byte[]".equals(setterType)) {
            setter.invoke(obj, new Object[] { new String(value + "").getBytes() });
        }
        // 包括String类型的
        else {
            setter.invoke(obj, new Object[] { value });
        }
    }

    /**
     * @Description: 根据Json获取对应的List
     * @author Zhangjc
     * @param json
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T extends BaseDataModel> List<T> getModelList(String json, Class<T> clazz) throws Exception {

        List<T> result = new ArrayList<>();
        List<Map<String, Object>> list = JacksonUtil.jacksonToCollection(json, ArrayList.class, HashMap.class);

        if (StringUtil.isBlank(json) || list == null || list.isEmpty()) {
            return result;
        }

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            T t = clazz.newInstance();
            t.set("status", "1");
            t.set("deletedFlag", "0");
            t.set("originApp", "XN1.0");
            t.set("originFlag", "S");
            List<String> proper = t.getPropertes();
            for (String str : map.keySet()) {
                if (proper.contains(str)) {
                    t.set(str, map.get(str));
                }
            }
            result.add(t);
        }
        return result;
    }

    /**
     * @Description: 继承BaseDataModel的bean
     * @author Zhangjc
     * @param type
     * @return
     * @throws Exception
     */
    public static <T extends BaseDataModel> T getBean(Class<T> clazz, Map<String, Object> inMap) throws Exception {

        T t = clazz.newInstance();
        t.set("status", "1");
        t.set("deletedFlag", "0");
        t.set("originApp", "XN1.0");
        t.set("originFlag", "S");
        List<String> list = t.getPropertes();
        Map<String, Object> map = new HashMap<>();
        map.putAll(inMap);

        for (String str : map.keySet()) {
            if (list.contains(str)) {
                t.set(str, map.get(str));
            }
        }
        return t;
    }

    /**
     * @Description:获取Model List的Map的list
     * @author Zhangjc
     * @param list
     * @return
     * @throws Exception
     */
    public static <T extends BaseDataModel> List<Map<String, Object>> getMapList(List<T> list) throws Exception {

        List<Map<String, Object>> result = new ArrayList<>();
        if (list == null || list.isEmpty()) {
            return result;
        }

        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            result.add(t.getData());
        }
        return result;
    }
}
