package xn.core.data;

import java.util.Date;

import org.springframework.web.util.JavaScriptUtils;

import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;

/**
 * @Description:sql的工具类
 * @author zhangjs
 * @date 2016年7月18日 上午11:44:50
 */
public class SqlCon {

    // 存放对应SQL。
    private StringBuilder sql = new StringBuilder("");


    /**
     * @Description:添加SQL条件
     * @author zhangjs
     * @param value
     * @param condition
     * @return
     */
    public boolean addCondition(Object value, String condition) {

        return addCondition(value, condition, false);
    }

    /**
     * @Description: 添加SQL条件
     * @author zhangjs
     * @param value
     * @param condition
     * @param isLike 为true时 ，自动加上 %%
     * @return
     */
    public boolean addCondition(Object value, String condition, boolean isLike) {

        return addCondition(value, condition, isLike, false);
    }

    /**
     * @Description: 添加sql主体 如SELECT * FROM TABLE_NAME WHERE CODE IS NULL
     * @author zhangjs
     * @param sql
     * @return
     */
    public boolean addMainSql(String sql) {
        this.sql.append(sql);
        return true;
    }
    
    public String getCondition() {
        return sql.toString();
    }

    /**
     * @Description:转化入参 为Str添加单引号
     * @author zhangjs
     * @param obj
     * @return
     */
    private static String toString(Object obj) {
        return toString(obj, true);
    }
    
    /**
     * @Description: 为In型转化入参
     * @author zhangjs
     * @param obj
     * @param addQuo 是否为Str添加单引号
     * @return
     */
    private static String toStringIn(Object obj) {
        StringBuffer stringBuffer = new StringBuffer("(");
        if (obj instanceof String || obj instanceof Character) {
            String in = ((String) obj).trim();
            String[] strs = in.split(",");

            for (int i = 0; i < strs.length; i++) {
                String s = strs[i];
                if (i != 0) {
                    stringBuffer.append(",");
                }
                if (!StringUtil.isNumeric(s)) {
                    stringBuffer.append("'" + s + "'");
                } else {
                    s = JavaScriptUtils.javaScriptEscape(s);
                    stringBuffer.append(s);
                }
            }
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

    /**
     * @Description: 转化入参
     * @author zhangjs
     * @param obj
     * @param addQuo 是否为Str添加单引号
     * @return
     */
    private static String toString(Object obj, boolean addQuo) {
        if (obj instanceof String || obj instanceof Character) {
           String str = ((String) obj).trim();
           str=   JavaScriptUtils.javaScriptEscape(str);
            if (addQuo) {
                str = "'" + str + "'";
            }
            return str;
        } else if (obj instanceof Date) {
            return TimeUtil.format((Date) obj);
        }
        return obj.toString();
    }

    /**
     * @Description: 直接添加sql
     * @author Zhangjc
     * @param sql
     * @return
     */
    // public boolean addCondition(String sql) {
    //
    // this.sql.append(sql);
    // return true;
    // }

    /**
     * @Description: 根据Map加条件
     * @author Zhangjc
     * @param map
     * @param key
     * @param sql
     * @return
     */
    // public boolean addCondition(Map<String, Object> map, String key, String sql) {
    //
    // Object obj = map.get(key);
    // if (obj == null)
    // return false;
    //
    // sql = StringUtil.replace(sql, "?", obj + " ");
    // this.sql.append(sql);
    // return true;
    // }

    /**
     * @Description: 添加条件
     * @author zhangjs
     * @param value
     * @param condition
     * @param isLike LIKE类型条件
     * @param isIn in类型条件
     * @return
     */
    public boolean addCondition(Object value, String condition, boolean isLike, boolean isIn) {

        if (value == null)
            return false;

        String str;
        if (isLike) {
            // 排除空字符串 like %% 很影响sql性能
            if (value instanceof String || value instanceof Character) {
                str = ((String) value).trim();
                if (StringUtil.isBlank(str)) {
                    return false;
                }
            }
            str = toString("%" + value + "%");
        } else {
            if (isIn) {
                str = toStringIn(value);
            } else {
                str = toString(value);
            }
        }
        String condStr = StringUtil.replace(condition, "?", str);
        this.sql.append(condStr);
        return true;
    }

    /**
     * @Description: 添加SQL条件 条件为NULL，也加上条件
     * @author zhangjs
     * @param value
     * @param condition
     * @return
     */
    public boolean addConditionWithNull(Object value, String condition) {
        return addConditionWithNull(value, condition, false);
    }

    /**
     * @Description: 添加SQL条件 条件为NULL，也加上条件
     * @author zhangjs
     * @param value
     * @param condition
     * @param isLike
     * @return
     */
    public boolean addConditionWithNull(Object value, String condition, boolean isLike) {
        return addConditionWithNull(value, condition, isLike, false);
    }

    /**
     * @Description: 添加SQL条件 条件为NULL，也加上条件
     * @author zhangjs
     * @param value
     * @param condition
     * @param isLike
     * @param isIn
     * @return
     */
    public boolean addConditionWithNull(Object value, String condition, boolean isLike, boolean isIn) {
        if (value == null) {
            String str = "NULL";
            if (isLike) {
                str = "%NULL%";
            } else {
                if (isIn) {
                    str = "(NULL)";
                }
            }
            String condStr = StringUtil.replace(condition, "?", str);
            this.sql.append(condStr);
            return true;
        } else {
            addCondition(value, condition, isLike, isIn);
        }
        return false;
    }

}

