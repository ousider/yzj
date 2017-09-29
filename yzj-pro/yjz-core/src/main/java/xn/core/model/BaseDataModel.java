package xn.core.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;

/**
 * @Description: 为Map的BaseModel
 * @author Zhangjc
 * @date 2016年5月10日 下午2:19:51
 */
public class BaseDataModel implements Serializable {

    private static final long serialVersionUID = 735926945292702830L;

    private Map<String, Object> data;

    private List<String> propertes;

    public void set(String name, Object value) {

        if (data == null) {
            data = new HashMap<String, Object>();
        }
        if (value instanceof Timestamp) {
            Date date = (Date) value;
            try {
                value = TimeUtil.format(date, TimeUtil.TIME_FORMAT);
            }
            catch (Exception e) {
                value = null;
            }
        } else if (value instanceof Date) {
            Date date = (Date) value;
            try {
                value = TimeUtil.parseToSqlDate(date);
            }
            catch (Exception e) {
                value = null;
            }
        }
        if (value instanceof String) {
            String str = (String) value;
            if (StringUtil.isBlank(str)) {
                value = null;
            }
        }
        // 为空时不塞值
        // if (value != null) {
        // }
        data.put(name, value);

    }

    public void setTimestamp(String name, Object value) {

        if (data == null) {
            data = new HashMap<String, Object>();
        }

        if (value instanceof Date) {
            Date date = (Date) value;
            try {
                value = TimeUtil.format(date);
            }
            catch (Exception e) {
                value = null;
            }
        }
        if (value instanceof String) {
            String str = (String) value;
            if (StringUtil.isBlank(str)) {
                value = null;
            }
        }
        data.put(name, value);
    }

    public Object get(String name) {
        if (data != null && data.containsKey(name)) {
            return data.get(name);
        }
        return null;
    }

    public Long getLong(String name) {
        if (data == null)
            return null;
        try {
            return Maps.getLong(data, name);
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public Boolean getBoolean(String name) {
        if (data == null)
            return null;

        return Maps.getBoolean(data, name, false);
    }

    public Integer getInteger(String name) {
        if (data == null)
            return null;
        try {
            return Maps.getInt(data, name);
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public Date getDate(String name) {
        if (data == null)
            return null;

        return Maps.getDate(data, name);
    }

    public String getString(String name) {
        if (data == null)
            return null;

        return Maps.getString(data, name);
    }

    public Double getDouble(String name) {
        if (data == null)
            return null;
        try {
            return Maps.getDouble(data, name);
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public Float getFloat(String name) {
        if (data == null)
            return null;

        try {
            return Maps.getFloat(data, name);
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public Timestamp getTimestamp(String name) {
        if (data == null)
            return null;

        return Maps.getTimestamp(data, name);
    }

    public <T> List<T> getList(String name) {

        return Maps.getList(data, name);
    }

    public Map<String, Object> getData() {
        return data == null ? new HashMap<String, Object>() : data;
    }

    public void setData(Map<String, Object> map) {
        this.data = map;
    }

    public List<String> getPropertes() {
        return propertes;
    }

    public void setPropertes(String property) {
        if (propertes == null) {
            propertes = new ArrayList<String>();
        }
        propertes.add(property);
    }

}
