package xn.core.cache.readonlycache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xn.core.cache.cachemanager.BaseReadOnlyCache;

public class CodeListReadOnlyCache extends BaseReadOnlyCache {

    public Map<String, Object> loadData() throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        try {

            // String sql = "SELECT TYPE_CODE as typeCode,CODE_VALUE as codeValue,IS_DEFAULT as isDefault,CODE_NAME as codeName,LINK_VALUE as
            // linkValue FROM CD_L_CODE_LIST WHERE STATUS ='1' AND DELETED_FLAG='0'";
            // result = loadTableData(sql, "typeCode");

        }
        catch (Exception e) {
        }
        return result;
    }

    public List<Map<String, String>> getList(String code) throws Exception {

        return (List<Map<String, String>>) get(code);
    }

    @Override
    public Map<Long, Object> loadDataByFarm() throws Exception {
        return null;
    }

    @Override
    public Object loadDataByFarm(long farmId) throws Exception {
        return null;
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
