package xn.pigfarm.service.wechat;

import java.util.List;
import java.util.Map;

import xn.pigfarm.model.backend.CodeListModel;
import xn.pigfarm.model.basicinfo.HouseStatusModel;

public interface IWechatHouseStatusEveryDayService {

    /**
     * @Description: 获取需要编辑的猪舍
     * @author CB
     * @param groupFarmId
     * @return
     */
    public List<Map<String, Object>> gethouse(Map<String, Object> map);

    /**
     * @Description: 新增
     * @author CB
     * @param map
     * @return
     * @throws Exception
     */
    public List<HouseStatusModel> editInsertToWechatHouseStatus(Map<String, Object> map) throws Exception;

    /**
     * @Description: 获取对应7天的数据
     * @author CB
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getHouse7DayByHouseIdAndDate(Map<String, Object> map) throws Exception;

    /**
     * @Description: 猪舍明细查询
     * @author CB
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchWechatHouseStatusByHouseIdAndDate(Map<String, Object> map) throws Exception;

    /**
     * @Description: 查询微信端猪舍每日存栏相关固定值
     * @author CB
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public List<CodeListModel> searchWechatHouseFinal(String wechatHouseFinal);

}
