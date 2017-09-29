package xn.core.cache.readonlycache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import xn.core.cache.cachemanager.AbstractReadOnlyCache;
import xn.core.cache.cachemanager.CacheTable;
import xn.core.model.system.CacheTablesModel;
import xn.core.util.ParamUtil;
import xn.core.util.data.Maps;

/**
 * @Description: 缓存加载数据的类
 * @author zhangjs5
 * @date 2016年5月4日 下午4:41:48
 */
public class BasicTableCache extends AbstractReadOnlyCache {
    private static Logger log = Logger.getLogger(BasicTableCache.class);

    @Override
    public Map<String, Object> loadData() throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, List<Map<String, String>>> result = ParamUtil.getCacheTableData(false);
        List<CacheTablesModel> modelList = ParamUtil.getCacheTableConfig(false);

        if (result != null && modelList != null) {
            for (CacheTablesModel model : modelList) {
                try {
                    CacheTable ct = new CacheTable(model.getIndexes(), model.getPrimaryColumns());
                    ct.loadData(result.get(model.getTableName()));
                    map.put(model.getTableName().toUpperCase(), ct);
                }
                catch (Exception e) {
                }
            }
        }
        return map;
    }

    public CacheTable getCacheTable(String tableName) throws Exception {

        return (CacheTable) get(tableName);
    }

    public CacheTable getCacheTable(long farmId, String tableName) throws Exception {

        Map<String, CacheTable> map = (Map<String, CacheTable>) get(farmId);
        return map.get(tableName);
    }

    @Override
    public Map<Long, Object> loadDataByFarm() throws Exception {

        Map<Long, Object> map = new HashMap<Long, Object>();

        Map<String, List<Map<String, String>>> result = ParamUtil.getCacheTableData(true);
        List<CacheTablesModel> modelList = ParamUtil.getCacheTableConfig(true);

        if (result != null && modelList != null) {
            // 先处理结果，以FarmId区分开
            Map<Long, Map<String, List<Map<String, String>>>> farmMap = new HashMap<Long, Map<String, List<Map<String, String>>>>();
            for (String tableName : result.keySet()) {
                try {
                    // 根据TABLE_NAME获取对应数据
                    List<Map<String, String>> resultList = result.get(tableName);

                    for (Map<String, String> resultMap : resultList) {
                        // 根据猪场ID重新分配数据
                        long farmId = 0l;
                        try {
                            farmId = Maps.getLong(resultMap, "FARM_ID");
                        }
                        catch (Exception e) {
                            log.error("根据猪场ID分解数据出错" + e);
                        }

                        if (farmMap.get(farmId) == null) {
                            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                            list.add(resultMap);
                            Map<String, List<Map<String, String>>> farmChildMap = new HashMap<String, List<Map<String, String>>>();
                            farmChildMap.put(tableName, list);
                            farmMap.put(farmId, farmChildMap);
                        } else {
                            if (farmMap.get(farmId).get(tableName) == null) {
                                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                                list.add(resultMap);
                                Map<String, List<Map<String, String>>> farmChildMap = farmMap.get(farmId);
                                farmChildMap.put(tableName, list);
                            } else {
                                farmMap.get(farmId).get(tableName).add(resultMap);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    log.error("根据猪场ID分解数据出错" + e);
                }
            }

            // 转化成Map方便处理
            Map<String, CacheTablesModel> modelMap = new HashMap<String, CacheTablesModel>();
            for (CacheTablesModel model : modelList) {
                modelMap.put(model.getTableName().toUpperCase(), model);
            }

            for (Long farmId : farmMap.keySet()) {
                try {
                    Map<String, List<Map<String, String>>> farmChildMap = farmMap.get(farmId);
                    for (String key : farmChildMap.keySet()) {
                        // 确认是这个TABLE的配置信息
                        CacheTablesModel model = modelMap.get(key.toUpperCase());
                        // 重新组织TABLE数据
                        CacheTable ct = new CacheTable(model.getIndexes(), model.getPrimaryColumns());
                        ct.loadData(farmChildMap.get(key));

                        if (map.get(farmId) == null) {
                            Map<String, CacheTable> childMap = new HashMap<String, CacheTable>();
                            childMap.put(key.toUpperCase(), ct);
                            map.put(farmId, childMap);
                        } else {
                            ((Map<String, CacheTable>) map.get(farmId)).put(key.toUpperCase(), ct);
                        }
                    }
                }
                catch (Exception e) {
                }
            }
        }
        return map;
    }

    @Override
    public Object loadDataByFarm(long farmId) throws Exception {

        Map<String, CacheTable> map = new HashMap<String, CacheTable>();

        Map<String, List<Map<String, String>>> result = ParamUtil.getCacheTableData(true, farmId);
        List<CacheTablesModel> modelList = ParamUtil.getCacheTableConfig(true);

        if (result != null && modelList != null) {
            for (CacheTablesModel model : modelList) {
                try {
                    CacheTable ct = new CacheTable(model.getIndexes(), model.getPrimaryColumns());
                    ct.loadData(result.get(model.getTableName()));
                    map.put(model.getTableName().toUpperCase(), ct);
                }
                catch (Exception e) {
                }
            }
        }
        return map;
    }

    @Override
    protected Map<String, Map<String, String>> getDataInfosFromInstance(long farmId) throws Exception {
        return null;
    }

    @Override
    protected Map<String, String> getDataInfoFromInstance(Long farmId, String key) throws Exception {
        return null;
    }

}
