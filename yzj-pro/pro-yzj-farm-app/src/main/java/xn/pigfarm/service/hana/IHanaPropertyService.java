package xn.pigfarm.service.hana;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.hana.SysHanaDbFarmModel;
import xn.pigfarm.model.hana.SysHanaDbPropertiesModel;

public interface IHanaPropertyService {
    /**
     * @Description: Hana数据库配置编辑
     * @author ZJ
     * @param sysHanaDbPropertiesModel
     * @throws Exception
     */
    public void editHana(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 分页查询数据库配置
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public BasePageInfo searchHanaToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description: 不分页查询数据库配置
     * @author THL
     * @param map
     * @return
     * @throws Exception
     */
    public List<SysHanaDbPropertiesModel> searchHanaToList(Map<String, Object> map) throws Exception;

    /**
     * @Description: 删除
     * @author THL
     * @param ids
     * @throws Exception
     */
    public void deleteHanas(long[] ids) throws Exception;

    /**
     * @Description: 测试DB连接
     * @author THL
     * @param map
     * @throws Exception
     */
    public void testDbConnect(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 编辑数据源和猪场
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editDbAndFarm(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索数据源和猪场
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public List<SysHanaDbFarmModel> searchDbAndFarmToList(Map<String, Object> inputMap) throws Exception;

}
