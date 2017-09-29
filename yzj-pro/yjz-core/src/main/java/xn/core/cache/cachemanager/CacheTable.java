package xn.core.cache.cachemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import xn.core.exception.CacheBusiException;
import xn.core.exception.Thrower;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;

/**
 * @Description: 数据缓存表存放信息（不分猪场去查询）
 * @author Zhangjc
 * @date 2016年5月16日 下午1:20:17
 */
public class CacheTable {

    private static Logger log = Logger.getLogger(CacheTable.class);

    // 主键
    private String primaryKey;

    // 索引及索引对应的下标数
    private Map<String, Integer> indexesKey;

    // 表的全量数据
    private List<Map<String, String>> cacheData = null;
    
    // 根据索引全对应的数据
    private Map<String, List<Map<String, String>>>[] indexCacheData = null;

    /**
     * 构造函数
     * @param primaryKey
     */
    public CacheTable(String indexes, String primaryKey) {

        indexesKey = new HashMap<String, Integer>();
        // 如果索引中不包含主键，主键存放在 indexesKey中的位置
        int k = 0;
        if (StringUtil.isNonBlank(indexes)) {
            String[] indexesStrs = StringUtil.split(indexes, "|");
            k = indexesStrs.length;

            // 拆分索引
            for (int i = 0; i < indexesStrs.length; i++) {
                indexesKey.put(indexesStrs[i].trim(), i);
            }
        }

        // 索引中不包含主键，将主键添加进去
        if (StringUtil.isNonBlank(primaryKey)) {
            this.primaryKey = primaryKey;
            if (!(StringUtil.isNonBlank(indexes)&&indexes.contains(primaryKey))) {
                indexesKey.put(primaryKey.trim(), k);
            }
        }
    }

    /**
     * @Description: 加载数据
     * @author zhangjs5
     * @param datas
     * @throws Exception
     */
    public void loadData(List<Map<String, String>> datas) throws Exception {
        
        if(datas==null){
            Thrower.throwException(CacheBusiException.CACHE_TABLE_LOADDATA_ERROR);
        }

        cacheData = datas;
        if (indexesKey == null || indexesKey.size() == 0) {
            return;
        }

        indexCacheData = new HashMap[indexesKey.size()];

        for (String str : indexesKey.keySet()) {
            if(StringUtil.isBlank(str)){
                continue;
            }
            String[] indexes = StringUtil.split(str, ",");
            Map<String, List<Map<String, String>>> indexCacheList = handleCacheData(indexes);
            indexCacheData[indexesKey.get(str)] = indexCacheList;
        }
    }

    /**
     * @Description: 根据索引查询相应条件
     * @author Zhangjc
     * @param indexes
     * @param indexeValue
     * @return
     */
    public List<Map<String, String>> getList(String index, String indexeValue) {

        if (indexesKey.get(index) != null) {
            return indexCacheData[indexesKey.get(index)].get(indexeValue);
        }
        log.debug("未找到对应的缓存信息");
        return null;
    }

    public Map<String, String> getMap(long primaryValue) {

        return getMap(String.valueOf(primaryValue));
    }
    /**
     * @Description: 根据主键查询对应的数据
     * @author Zhangjc
     * @param primaryValue
     * @return
     */
    public Map<String, String> getMap(String primaryValue) {

        if (indexesKey.get(primaryKey) != null) {
            int i = indexesKey.get(primaryKey);
            List<Map<String, String>> list = indexCacheData[i].get(primaryValue.trim());
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        }
        log.debug("未找到对应的缓存信息");
        return null;
    }

    /**
     * @Description:根据单个索引处理数据
     * @author Zhangjc
     * @param columns
     * @return
     * @throws Exception
     */
    private Map<String, List<Map<String, String>>> handleCacheData(String[] columns) throws Exception {

        Map<String, List<Map<String, String>>> indexCacheList = new HashMap<String, List<Map<String, String>>>();

        if (columns == null || columns.length == 0) {
            return indexCacheList;
        }

        // 循环数据，根据主键区隔数据
        for (int i = 0; i < cacheData.size(); i++) {
            Map<String, String> map = cacheData.get(i);

            StringBuffer columnsValue = new StringBuffer();
            // 查询结果不包含此结果集报错
            for (int j = 0; j < columns.length; j++) {
                String col = columns[j].trim();
                // 不做判断
                // if (!map.keySet().contains(col)) {
                // Thrower.throwException(CacheBusiException.CACHE_TABLE_LOADDATA_ERROR);
                // }
                if (j == 0) {
                    columnsValue.append(Maps.getString(map, col, ""));
                } else {
                    columnsValue.append("," + Maps.getString(map, col, ""));
                }
            }
            if (indexCacheList.get(columnsValue.toString()) != null) {
                indexCacheList.get(columnsValue.toString()).add(map);
            } else {
                List<Map<String, String>> preList = new ArrayList<Map<String, String>>();
                preList.add(map);
                indexCacheList.put(columnsValue.toString(), preList);
            }
        }
        return indexCacheList;
    }

}
