package xn.core.cache.readonlycache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xn.core.cache.cachemanager.BaseReadOnlyCache;
import xn.core.data.SqlCon;
import xn.core.util.data.Maps;

/**
 * @Description: 查询猪只详细数据
 * @author Zhangjc
 * @date 2016年6月3日 下午3:18:46
 */
public class PigInfoReadOnlyCache extends BaseReadOnlyCache {

    public Map<String, Object> loadData() throws Exception {
        return null;
    }

    /**
     * @Description:获取一个猪场的所有的猪只详情
     * @author Zhangjc
     * @param farmId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Map<String, String>> getDataInfosFromInstance(long farmId) throws Exception {

        Map<String, Map<String, String>> data = (Map<String, Map<String, String>>) get(farmId);
        return data;
    }

    /**
     * @Description: 根据猪场，猪只ID获取猪只详情
     * @author Zhangjc
     * @param farmId
     * @param pigId
     * @return
     * @throws Exception
     */
    @Override
    protected Map<String, String> getDataInfoFromInstance(Long farmId, String pigId) throws Exception {

        Map<String, Map<String, String>> data = (Map<String, Map<String, String>>) get(farmId);
        data = data == null ? new HashMap<String, Map<String, String>>() : data;
        Map<String, String> map = data.get(pigId) == null ? new HashMap<String, String>() : data.get(pigId);
        return map;
    }

    @Override
    public Map<Long, Object> loadDataByFarm() throws Exception {
        
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<Long, Object> map = new HashMap<Long, Object>();
        try{
            list = loadTableData(getSql(null));
        }catch(Exception e){
            return map;
        }

        // 循环List 组织成满足自己要求的数据
        Map<String, Map<String, String>> formattMap = null;

        for (int i = 0; i < list.size(); i++) {
            Map<String, String> data = list.get(i);
            Long farmId = Maps.getLongClass(data, "farmId");
            String pigId = Maps.getString(data, "pigId", "");
            if (map.get(farmId) != null) {
                formattMap = (Map<String, Map<String, String>>) map.get(farmId);
                formattMap.put(pigId, data);
            } else {
                formattMap = new HashMap<String, Map<String, String>>();
                formattMap.put(pigId, data);
                map.put(farmId, formattMap);
            }
        }
        return map;
    }

    @Override
    public Object loadDataByFarm(long farmId) throws Exception {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        try {
            list = loadTableData(getSql(farmId));
        }
        catch (Exception e) {
            return map;
        }
        for(int i=0;i<list.size();i++){
            Map<String, String> data = list.get(i);
            map.put(Maps.getString(data, "pigId"), data);
        }
        return map;
    }

    /**
     * @Description: 当种猪入场、配种、转舍、转群、上产房需要刷新缓存
     * @author zhangjs
     * @param farmId
     * @return
     */
    public static String getSql(Long farmId) {

        // SqlCon sql = new SqlCon();
        // sql.addMainSql(" SELECT T1.ROW_ID pigId,T1.PIG_CLASS pigClass,T5.PIG_CLASS_NAME pigClassName,");
        // sql.addMainSql(" T1.PIG_TYPE pigType,T8.CODE_NAME pigTypeName,T1.SEX sex,T7.CODE_NAME sexName,T1.COMPANY_ID companyId,");
        // sql.addMainSql(" T1.BILL_ID billId,T1.BREED_ID breedId,T6.BREED_NAME breedName,T1.COEFFICIENT_INBRED coefficientInbred,");
        // sql.addMainSql(" T1.FARM_ID farmId,T2.EAR_BRAND earBrand,T2.EAR_SHORT earShort,");
        // sql.addMainSql(" T2.EAR_THORN earThorn,T2.ELECTRONIC_EAR_NO electronicEarNo,");
        // sql.addMainSql(" T3.EAR_BRAND earBrandFather,T3.EAR_SHORT earShortFather,");
        // sql.addMainSql(" T3.EAR_THORN earThornFather,T3.ELECTRONIC_EAR_NO electronicEarNoFather,");
        // sql.addMainSql(" T4.EAR_BRAND earBrandMother,T4.EAR_SHORT earShortMother,");
        // sql.addMainSql(" T4.EAR_THORN earThornMother,T4.ELECTRONIC_EAR_NO electronicEarNoMother");
        // sql.addMainSql(" FROM PP_L_PIG T1");
        // sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T2 ON T1.EAR_CODE_ID=T2.ROW_ID AND T2.DELETED_FLAG='0' AND T2.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T3 ON T2.FATHER_EAR_ID=T3.ROW_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T4 ON T2.MOTHER_EAR_ID=T4.ROW_ID AND T4.DELETED_FLAG='0' AND T4.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN CD_L_PIG_CLASS T5 ON T1.PIG_CLASS=T5.ROW_ID AND T5.DELETED_FLAG='0' AND T5.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN CD_L_BREED T6 ON T1.BREED_ID=T6.ROW_ID AND T6.DELETED_FLAG='0' AND T6.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN CD_L_CODE_LIST T7 ON T7.TYPE_CODE='PIG_SEX' AND T7.CODE_VALUE=T1.SEX");
        // sql.addMainSql(" LEFT JOIN CD_L_CODE_LIST T8 ON T8.TYPE_CODE='PIG_TYPE' AND T8.CODE_VALUE=T1.PIG_TYPE");
        // sql.addMainSql(" WHERE T1.DELETED_FLAG='0' AND T1.STATUS='1' ");
        // sql.addMainSql(" AND T1.PIG_TYPE ='1' ");
        // sql.addCondition(farmId, " AND T1.FARM_ID=?");
        // sql.addMainSql(" UNION ALL");
        // sql.addMainSql(" SELECT T1.ROW_ID pigId,T1.PIG_CLASS pigClass,T5.PIG_CLASS_NAME pigClassName,");
        // sql.addMainSql(" T1.PIG_TYPE pigType,T8.CODE_NAME pigTypeName,T1.SEX sex,T7.CODE_NAME sexName,T1.COMPANY_ID companyId,");
        // sql.addMainSql(" T1.BILL_ID billId,T1.BREED_ID breedId,T6.BREED_NAME breedName,T1.COEFFICIENT_INBRED coefficientInbred,");
        // sql.addMainSql(" T1.FARM_ID farmId,T2.EAR_BRAND earBrand,T2.EAR_SHORT earShort,");
        // sql.addMainSql(" T2.EAR_THORN earThorn,T2.ELECTRONIC_EAR_NO electronicEarNo,");
        // sql.addMainSql(" T3.EAR_BRAND earBrandFather,T3.EAR_SHORT earShortFather,");
        // sql.addMainSql(" T3.EAR_THORN earThornFather,T3.ELECTRONIC_EAR_NO electronicEarNoFather,");
        // sql.addMainSql(" T4.EAR_BRAND earBrandMother,T4.EAR_SHORT earShortMother,");
        // sql.addMainSql(" T4.EAR_THORN earThornMother,T4.ELECTRONIC_EAR_NO electronicEarNoMother");
        // sql.addMainSql(" FROM PP_L_PIG T1");
        // sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T2 ON T1.EAR_CODE_ID=T2.ROW_ID AND T2.DELETED_FLAG='0' AND T2.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T3 ON T2.FATHER_EAR_ID=T3.ROW_ID AND T3.DELETED_FLAG='0' AND T3.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T4 ON T2.MOTHER_EAR_ID=T4.ROW_ID AND T4.DELETED_FLAG='0' AND T4.STATUS='1'");
        // sql.addMainSql(" LEFT JOIN CD_L_PIG_CLASS T5 ON T1.PIG_CLASS=T5.ROW_ID AND T5.DELETED_FLAG='0' AND T5.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN CD_L_BREED T6 ON T1.BREED_ID=T6.ROW_ID AND T6.DELETED_FLAG='0' AND T6.STATUS='1' ");
        // sql.addMainSql(" LEFT JOIN CD_L_CODE_LIST T7 ON T7.TYPE_CODE='PIG_SEX' AND T7.CODE_VALUE=T1.SEX");
        // sql.addMainSql(" LEFT JOIN CD_L_CODE_LIST T8 ON T8.TYPE_CODE='PIG_TYPE' AND T8.CODE_VALUE=T1.PIG_TYPE");
        // sql.addMainSql(" WHERE T1.DELETED_FLAG='0' AND T1.STATUS='1' ");
        // sql.addMainSql(" AND T1.PIG_TYPE ='2' ");
        // sql.addCondition(farmId, " AND T1.FARM_ID=?");
        // return sql.getCondition();
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT T1.ROW_ID pigId,T1.PIG_CLASS pigClass,T5.PIG_CLASS_NAME pigClassName,T1.PARITY parity,T1.LAST_BILL_ID lastBillId,");
        sql.addMainSql(" T1.PIG_TYPE pigType,T1.SEX sex,T1.SWINERY_ID swineryId ,T1.HOUSE_ID houseId,T1.PIGPEN_ID pigpenId,");
        sql.addMainSql(" T1.BILL_ID billId,T1.BREED_ID breedId,T6.BREED_NAME breedName,T1.COEFFICIENT_INBRED coefficientInbred,");
        sql.addMainSql(" T1.FARM_ID farmId,T2.EAR_BRAND earBrand,T2.EAR_SHORT earShort,");
        sql.addMainSql(" T2.EAR_THORN earThorn,T2.ELECTRONIC_EAR_NO electronicEarNo,T7.HOUSE_TYPE houseType ");
        sql.addMainSql(" ,T1.BIRTH_DATE birthDate,T7.HOUSE_NAME houseName,T8.PIGPEN_NAME pigpenName,T1.MATERIAL_ID materialId");
        sql.addMainSql(" FROM PP_L_PIG T1");
        sql.addMainSql(" LEFT JOIN PP_L_EAR_CODE T2 ON T1.EAR_CODE_ID=T2.ROW_ID AND T2.DELETED_FLAG='0' AND T2.STATUS='1' AND T2.FARM_ID=T1.FARM_ID");
        sql.addMainSql(" LEFT JOIN CD_L_PIG_CLASS T5 ON T1.PIG_CLASS=T5.ROW_ID AND T5.DELETED_FLAG='0' AND T5.STATUS='1' ");
        sql.addMainSql(" LEFT JOIN CD_L_BREED T6 ON T1.BREED_ID=T6.ROW_ID AND T6.DELETED_FLAG='0' AND T6.STATUS='1' ");
        sql.addMainSql(" LEFT JOIN PP_O_HOUSE T7 ON T1.HOUSE_ID=T7.ROW_ID AND T7.DELETED_FLAG='0' AND T7.STATUS='1' AND T7.FARM_ID=T1.FARM_ID");
        sql.addMainSql(" LEFT JOIN PP_O_PIGPEN T8 ON T1.PIGPEN_ID=T8.ROW_ID AND T8.DELETED_FLAG='0' AND T8.STATUS='1' AND T8.FARM_ID=T1.FARM_ID");
        sql.addMainSql(" WHERE T1.DELETED_FLAG='0' AND T1.STATUS='1' ");
        sql.addMainSql(" AND T1.PIG_TYPE !='3' ");
        sql.addCondition(farmId, "  AND T1.FARM_ID=?");
        return sql.getCondition();
    }

}
