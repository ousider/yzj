package xn.core.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;

public class PropertiesConfig {
    private Properties props = null;

    public PropertiesConfig(InputStream in) throws Exception {
        props = new Properties();
        try {
            props.load(in);
        }
        catch (IOException e) {
            Thrower.throwException(CoreBusiException.LOAD_PROPERTIES_ERROR, e.getMessage());
        }

        if (props.isEmpty()) {
            Thrower.throwException(CoreBusiException.LOAD_PROPERTIES_ERROR, "文件数据为空");
        }
    }

    public PropertiesConfig(String file) throws Exception {
        this(PropertiesConfig.class.getClassLoader().getResourceAsStream(file));
    }

    public PropertiesConfig(File file) throws Exception {
        props = new Properties();
        try {
            InputStream in = new FileInputStream(file);
            props.load(in);
        }
        catch (Exception e) {
            Thrower.throwException(CoreBusiException.LOAD_PROPERTIES_ERROR, e.getMessage());
        }
    }

    public String getProperty(String prop) {
        String value = props.getProperty(prop);
        try {
            return new String(value.getBytes("ISO-8859-1"));
        }
        catch (UnsupportedEncodingException e) {
        }

        return value;
    }

    public String getProperty(String prop, String defval) {
        String value = getProperty(prop);
        if (value == null) {
            return defval;
        }
        return value;
    }

    public Map<String, String> getProperties() {

        Map<String, String> data = new HashMap();

        Enumeration<Object> e = props.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            data.put(key, getProperty(key));
        }

        return data;
    }
}

