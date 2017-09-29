package xn.pigfarm.business.production;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import xn.pigfarm.model.production.PigModel;

/**
 * @Description: 一些基本的BS
 * @author Administrator
 * @date 2016年11月1日 上午9:14:14
 */
public interface ICommonBS {

    /**
     * @Description: 获取猪只基本信息
     * @author Administrator
     * @param pigIds
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> getPigBaseInfo(List<Long> pigIds, String tableName);

    /**
     * @Description: 获取仔猪的基本信息
     * @author Administrator
     * @param pigIds
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> getChildBaseInfo(List<Long> pigIds, String tableName);

    /**
     * @Description: 更新猪只
     * @author Administrator
     * @param pigModelList
     */
    public void updatePigs(List<PigModel> pigModelList);

    /**
     * @Description: 根据views创建更新猪只的list
     * @author Administrator
     * @param viewList
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public List<PigModel> createPigModelList(List<? extends Object> viewList) throws Exception;

}
