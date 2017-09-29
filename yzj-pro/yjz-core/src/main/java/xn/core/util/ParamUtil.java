package xn.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xn.core.cache.controllersession.ServiceContext;
import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;
import xn.core.model.ParamModel;
import xn.core.model.system.CacheTablesModel;
import xn.core.service.IParamService;
import xn.core.util.data.StringUtil;

/**
 * @Description: 参数管理工具类
 * @author Zhangjc
 * @date 2016年4月15日 下午2:05:47
 */
public class ParamUtil {

    /**
     * @Description: 获取IParamService
     * @author Zhangjc
     * @return
     */
    public static IParamService getParamService() {
        return SpringContextUtil.getBean("ParamService", IParamService.class);
    }


    /**
     * @Description: 验证重复
     * @author Zhangjc
     * @param tableName
     * @param rowId
     * @param field
     * @param columnName
     * @return
     * @throws Exception
     */
    public static long isExist(String tableName, Long rowId, String[] field, String columnName) throws Exception {

        String[] colArr = StringUtil.isNonBlank(columnName) ? StringUtil.split(columnName, ",") : new String[] {};
        return isExist(tableName, rowId, field, colArr);
    }

    /**
     * @Description: 验证重复
     * @author Zhangjc
     * @param tableName
     * @param rowId
     * @param field
     * @param columnName
     * @return
     * @throws Exception
     */
    public static long isExist(String tableName, Long rowId, String field, String columnName) throws Exception {
        
        String[] fieldArr = StringUtil.isNonBlank(field) ? StringUtil.split(field, ",") : new String[] {};
        String[] colArr = StringUtil.isNonBlank(columnName) ? StringUtil.split(columnName, ",") : new String[] {};
        return isExist(tableName, rowId, fieldArr, colArr);
    }

    /**
     * @Description: 验证重复
     * @author Zhangjc
     * @param tableName
     * @param row_id
     * @param field
     * @param columnName
     * @return
     * @throws Exception
     */
    public static long isExist(String tableName, Long rowId, String[] field, String[] columnName) throws Exception {

        List<ParamModel> paramModels = new ArrayList<ParamModel>();

        long fieldSize = field.length;
        long columnNameSize = columnName.length;

        if (fieldSize != columnNameSize) {
            Thrower.throwException(CoreBusiException.SIZE_NOT_SAME_ERROR);
        }
        for (int i = 0; i < fieldSize; i++) {
            ParamModel paramModel = new ParamModel();
            paramModel.setColumnName(columnName[i]);
            paramModel.setField(field[i]);
            paramModels.add(paramModel);
        }

        long recordQty = getParamService().isExist(tableName, rowId, paramModels);
        return recordQty;

    }

    /**
     * @Description: 根据代码类别、创建人、公司ID创建编码
     * @author Zhangjc
     * @param typeCode
     * @param createId
     * @param companyId
     * @return
     * @throws Exception
     */
    public static String getBCode(String typeCode, long createId, long companyId, long farmId) throws Exception {

        String map = getParamService().getBCode(typeCode, createId, companyId, farmId);
        return map;
    }

    /**
     * @Description:根据登陆公司ID 和设置代码ID获取设置值
     * @author Zhangjc
     * @param companyId
     * @param settingCode
     * @return
     * @throws Exception
     */
    public static String getSettingValueByCode(String settingCode) throws Exception {

        String str = getParamService().getSettingValueByCode(settingCode);
        return str;
    }

    /**
     * @Description:根据传入公司ID 和设置代码ID获取设置值
     * @author Zhangjc
     * @param companyId
     * @param settingCode
     * @return
     * @throws Exception
     */
    public static String getSettingValueByCode(String settingCode, long companyId, long farmId) throws Exception {

        String str = getParamService().getSettingValueByCode(settingCode, companyId, farmId);
        return str;
    }

    /**
     * @Description: 根据Sql获取缓存信息
     * @author zhangjs5
     * @throws Exception
     */
    public static List<Map<String, String>> getCacheDataBySql(String sql) throws Exception {

        return getParamService().getCacheDataBySql(sql);
    }

    /**
     * @Description: 查询所有需要缓存的表数据
     * @author zhangjs5
     * @throws Exception
     */
    public static Map<String, List<Map<String, String>>> getCacheTableData(boolean farmFlag) throws Exception {

        return getCacheTableData(farmFlag, null);
    }

    /**
     * @Description: 查询所有需要缓存的表数据
     * @author zhangjs5
     * @throws Exception
     */
    public static Map<String, List<Map<String, String>>> getCacheTableData(boolean farmFlag, Long farmId) throws Exception {

        return getParamService().getCacheTableData(farmFlag, farmId);
    }

    /**
     * @Description: 查询所有需要缓存的表数据
     * @author zhangjs5
     * @throws Exception
     */
    public static List<CacheTablesModel> getCacheTableConfig(boolean farmFlag) throws Exception {

        return getParamService().getCacheTableConfig(farmFlag);
    }

    /**
     * @Description: 判断是否在这个表中存在这个ID的数据
     * @author zhangjs5
     * @throws Exception
     */
    public static boolean isExitDetail(String tableName, String columnName, long[] ids) throws Exception {

        return isExitDetail(tableName, columnName, true, ids);
    }

    /**
     * @Description: 判断是否在这个表中存在这个ID的数据
     * @author zhangjs5
     * @throws Exception
     */
    public static boolean isExitDetail(String tableName, String columnName, boolean hasFarm, long[] ids) throws Exception {
        
        return getParamService().isExitDetail(tableName, columnName, ids, hasFarm);
    }

    /**
     * @Description: 获取猪场的周次信息
     * @author zhangjs
     * @return
     * @throws Exception
     */
    public static Map<String, String> getWeekInfo() throws Exception {
        Map<String, Object> inputMap = new HashMap<String, Object>();
        return getParamService().getWeekInfo(inputMap);
    }

    /**
     * @Description: 获取入参
     * @author THL
     * @return Map
     */
    public static Map<String, Object> getInputMap() {

        return ServiceContext.getData() == null ? new HashMap<String, Object>() : ServiceContext.getData();
    }

    /**
     * @Description: 获取批次日龄
     * @author THL
     * @return Map
     */
    public static Long getSwineryAge(Long swineryId, Date date) throws Exception {

        return getParamService().getSwineryAge(swineryId, date);
    }
    
    /**
     * @Description: 获取批次日龄
     * @author THL
     * @return Map
     */
    public static Double getSwineryAgeDouble(Long swineryId, Date date) throws Exception {

        return getParamService().getSwineryAgeDouble(swineryId, date);
    }

}
