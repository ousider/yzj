package xn.core.cache.cachemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import xn.core.exception.CacheBusiException;
import xn.core.exception.Thrower;
import xn.core.util.ParamUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;

public abstract class BaseReadOnlyCache extends AbstractReadOnlyCache {

    protected static Logger log = Logger.getLogger(BaseReadOnlyCache.class);

    protected List<Map<String, String>> loadTableData(String sql) throws Exception {

        log.debug("开始加载数据......");
        List<Map<String, String>> list = ParamUtil.getCacheDataBySql(sql);
        return list;
    }

    protected Map<String, Object> loadTableData(String sql, String column) throws Exception {

        if (column == null) {
            return null;
        }
        String[] columns = StringUtil.split(column, ",");
        return loadTableData(sql, columns);
    }

    protected Map<String, Object> loadTableData(String sql, String[] columns) throws Exception {

        Map<String, Object> indexCacheList = new HashMap<String, Object>();

        if (columns == null || columns.length == 0) {
            return indexCacheList;
        }
        List<Map<String, String>> cacheData = loadTableData(sql);

        // 循环数据，根据主键区隔数据
        for (int i = 0; i < cacheData.size(); i++) {
            Map<String, String> map = cacheData.get(i);

            StringBuffer columnsValue = new StringBuffer();
            // 查询结果不包含此结果集报错
            for (int j = 0; j < columns.length; j++) {
                if (!map.keySet().contains(columns[j])) {
                    Thrower.throwException(CacheBusiException.CACHE_TABLE_LOADDATA_ERROR);
                }
                if (j == 0) {
                    columnsValue.append(Maps.getString(map, columns[j]));
                } else {
                    columnsValue.append("," + Maps.getString(map, columns[j]));
                }
            }
            if (indexCacheList.get(columnsValue.toString()) != null) {
                ((List<Map<String, String>>) indexCacheList.get(columnsValue.toString())).add(map);
            } else {
                List<Map<String, String>> preList = new ArrayList<Map<String, String>>();
                preList.add(map);
                indexCacheList.put(columnsValue.toString(), preList);
            }
        }
        return indexCacheList;
    }

    // protected Map<String, Object> loadTableData(String sql, String primaryKey) throws Exception {
    //
    // log.debug("开始处理数据......");
    // List<Map<String, String>> list = loadTableData(sql);
    //
    // Map<String, Object> primaryCacheList = new HashMap<String, Object>();
    // // 循环数据，根据主键区隔数据
    // for (int i = 0; i < list.size(); i++) {
    // Map<String, String> map = list.get(i);
    // // 查询结果不包含此结果集报错
    // if (!map.keySet().contains(primaryKey)) {
    // Thrower.throwException(CacheBusiException.CACHE_TABLE_LOADDATA_ERROR);
    // }
    //
    // String keyValue = Maps.getString(map, primaryKey);
    // if (primaryCacheList.get(keyValue) != null) {
    // ((List<Map<String, String>>) primaryCacheList.get(keyValue)).add(map);
    // } else {
    // List<Map<String, String>> preList = new ArrayList<Map<String, String>>();
    // preList.add(map);
    // primaryCacheList.put(keyValue, preList);
    // }
    // }
    //
    // return primaryCacheList;
    // }
    //
    // protected Map<String, Object> loadTableData(String sql, String column1, String column2) throws Exception {
    //
    // log.debug("开始处理数据......");
    // List<Map<String, String>> list = loadTableData(sql);
    //
    // Map<String, Object> primaryCacheList = new HashMap<String, Object>();
    // // 循环数据，根据主键区隔数据
    // for (int i = 0; i < list.size(); i++) {
    // Map<String, String> map = list.get(i);
    // // 查询结果不包含此结果集报错
    // if (!(map.keySet().contains(column1) && map.keySet().contains(column2))) {
    // Thrower.throwException(CacheBusiException.CACHE_TABLE_LOADDATA_ERROR);
    // }
    //
    // String columnValue1 = Maps.getString(map, column1);
    // String columnValue2 = Maps.getString(map, column2);
    // if (primaryCacheList.get(columnValue1 + "," + columnValue2) != null) {
    // ((List<Map<String, String>>) primaryCacheList.get(columnValue1 + "," + columnValue2)).add(map);
    // } else {
    // List<Map<String, String>> preList = new ArrayList<Map<String, String>>();
    // preList.add(map);
    // primaryCacheList.put(columnValue1 + "," + columnValue2, preList);
    // }
    // }
    //
    // return primaryCacheList;
    // }
}
