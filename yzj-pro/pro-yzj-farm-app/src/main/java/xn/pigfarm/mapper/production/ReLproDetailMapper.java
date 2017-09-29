package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.production.ReLproDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-4-27 10:35:16
 */
public interface ReLproDetailMapper extends IBaseDataMapper<ReLproDetailModel> {

    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReLproDetailModel searchById(@Param("rowId") long rowId);

    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReLproDetailModel> searchToList(long farmId);

    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    int delete(@Param("rowId") long rowId);

    /**
     * @Description: 批量删除
     * @author 系统生成
     * @param ids
     * @return
     */
    int deletes(@Param("ids") long[] ids);

    /**
     * @Description: 生产数据数据汇总目标
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByTarget(@Param("selectFarm") String selectFarm, @Param("reportType") String reportType);

    /**
     * @Description: 生产数据汇总存栏
     * @author 系统生成
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByBreed(Map<String, Object> map);

    /**
     * @Description: 生产数据汇总-返+假+流
     * @author 系统生成
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByFJL(Map<String, Object> map);

    /**
     * @Description: 生产数据汇总存栏
     * @author 系统生成
     * @param companyMark,startDate,endDate
     * @return
     */
    Map<String, Object> searchProduceDataByLivestock(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    /**
     * @Description: 生产数据汇总存栏
     * @author companyMark,startDate,endDate
     * @param id
     * @return
     */
    Map<String, Object> searchProduceDataByGoodsSell(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    /**
     * @Description: 死亡率
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByTotalMortality(Map<String, Object> map);

    /**
     * @Description: 死亡率-分场死亡率
     * @author yangy
     * @param map
     * @return
     */
    Map<String, Object> searchProduceDataByAllMortality(Map<String, Object> map);

    /**
     * @Description: 死亡率-分场死亡率
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByMortality(Map<String, Object> map);

    /**
     * @Description: 流程指标-窝均产活仔数
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByLitterSize(Map<String, Object> map);

    /**
     * @Description: 流程指标-分娩率（月）
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByDeliveryRate(Map<String, Object> map);

    /**
     * @Description: 流程指标-7030(分场)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataBy7030(Map<String, Object> map);

    /**
     * @Description: 流程指标-110(总场/分场)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByTotal110SSRL(Map<String, Object> map);

    /**
     * @Description: 流程指标-窝均断奶数(总场/分场)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByTotalWeaningNumber(Map<String, Object> map);

    /**
     * @Description: 流程指标-窝均断奶重量(总场)
     * @author yangy
     * @param map
     * @return
     */
    Map<String, Object> searchProduceDataByTotalWeaningWeight(Map<String, Object> map);

    /**
     * @Description: 流程指标-窝均断奶重量(分场)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByWeaningWeight(Map<String, Object> map);

    /**
     * @Description: 各场关键指标对标排名(母猪存栏数/商品猪存栏数)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataBySowAndGoodPig(Map<String, Object> map);

    /**
     * @Description: "头均代乳粉使用量(G)"/"头均教槽王使用量(G)"
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByDrfAndJcwPig(Map<String, Object> map);

    /**
     * @Description: 肥猪料肉比(月)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByFZLRB(Map<String, Object> map);

    /**
     * @Description: 分场肥猪料肉比(月)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByFCFZLRB(Map<String, Object> map);

    /**
     * @Description: 全场料肉比(月)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByQCLRB(Map<String, Object> map);

    /**
     * @Description: （分场）全场料肉比(月)
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByFCQCLRB(Map<String, Object> map);

    /**
     * @Description: 窝均活仔数
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByWJHZS(Map<String, Object> map);

    /**
     * @Description: 销售猪只均重
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByXSZZJZ(Map<String, Object> map);

    /**
     * @Description: 库存猪只均重
     * @author yangy
     * @param map
     * @return
     */
    List<Map<String, Object>> searchProduceDataByKCZZJZ(Map<String, Object> map);

}
