package xn.pigfarm.service.analyticalData;

import java.util.List;
import java.util.Map;

/**
 * @Description:B
 * @author li.zhou
 * @date 2016年4月13日 下午6:54:46
 */
public interface IAnalyticalDataService {

    /**
     * @Description: 搜索种猪死亡总数
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchBoarAndSowDieTotalToList(Map<String, Object> inputMap) throws Exception;


    /**
     * @Description: 搜索种猪淘汰总数
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchBoarAndSowObsoleteTotalToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索产房死淘率
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDiePercentOfDelivery(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索保育死淘率
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDiePercentOfChlidCare(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索育肥死淘率
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDiePercentOfToFat(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索全程死淘率
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDiePercentOfAll(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索全程死亡头数
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDieNumOfAll(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 存栏分析_比例
     * @author CB
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Object searchPopulationAnalysis(Map<String, Object> map) throws Exception;

    /**
     * @Description: 存栏分析_数值
     * @author CB
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Object searchPopulationAnalysis2(Map<String, Object> map) throws Exception;

    /**
     * @Description: 各场死淘率
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchEachPigfarmDiePercentOfAll(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 存栏详情
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> searchStockDetails(Map<String, Object> map) throws Exception;

    /**
     * @Description: 猪场存栏
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    List<Map<String, String>> searchStockDetailsForFarmId(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售总计
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSaleTotal(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售苗猪总计
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSalePiggyPigTotal(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售肥猪总计
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSaleHogPigTotal(Map<String, Object> inputMap) throws Exception;
    
    /**
     * @Description: 销售种猪总计
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSaleBoarTotal(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售其他类型总计
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSaleOtherTotal(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 可上市肥猪头数
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGoPublicHogPig(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 死淘数量详情
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDieNumOfHouse(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 选择猪场
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFarmByOrigin(Map<String, Object> inputMap) throws Exception;
    
    /**
     * @Description: 种猪死亡详情
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchBoarDieTotal(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 种猪淘汰详情
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> searchBoarObsoleteTotal(Map<String,Object> inputMap) throws Exception;

    /**
     * @Description: 猪舍死亡数量
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFarmHouseDieTotal(Map<String, Object> inputMap) throws Exception;
    
    /**
     * @Description: 死亡原因总计
     * @author Administrator
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchLeaveReasonDieTotal(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:集团门户生产分析
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupHomeProData(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团门户销售分析
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupHomeSaleData(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:集团门户大生物安全
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupHomeSafetyData(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 获取集团综合指标
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupIndicator(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:获取集团繁殖情况
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupReproductionCondition(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 获取集团产仔情况
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupBirthCondition(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团生产动态配种明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupBreedDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团生产动态妊检明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupPregnancyCheckDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团生产动态分娩明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupDeliveryDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团生产动态留种明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupSeedPigDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团死亡情况明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupDieDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:集团大生物安全供应链动态本月购入
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupSupplyMonthInput(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团大生物安全供应链动态本月使用
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupSupplyMonthUsed(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 集团大生物安全供应链动态本月库存
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupSupplyMonthStore(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 变动情况
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchGroupChangeCondition(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:绩效数据-产活仔数
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceAliveNum(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:绩效数据-分娩率
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceDeliveryRate(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:绩效数据-膘情13-18
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceBackFat(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:绩效数据-窝均断奶重
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceAvgWeanWeight(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 绩效数据-窝均断奶头数
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceAvgWeanNum(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:绩效数据-保育成活率
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceToCareAliveRate(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 绩效数据-7030
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformance7030(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 绩效数据-育肥成活率
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformanceToFattenAliveRate(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 绩效数据-110KG出栏
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchPerformance110KGOut(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 7天断配率
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> search7DayWeanBreedRate(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售情况-外销
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchSaleCondition(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售情况-内销/调拨
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchSaleConditionInsideAndAllocation(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 销售未结算
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchSaleNotBalanceNum(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 供应链提醒-过期提醒
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchSupplyOverdueRemind(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:供应链提醒-过期提醒明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSupplyOverdueRemindDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 供应链提醒-使用提醒
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public Map<String, Object> searchSupplyUsedRemind(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 供应链提醒-使用提醒明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSupplyUsedRemindDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 变动情况明细
     * @author Cress
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupChangeConditionDetail(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询当天日报
     * @author Cress
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchProductionDaily(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 确定日报数据核对无误
     * @author Cress
     * @param ids
     * @throws Exception
     */
    public void editProductionDailySure(Map<String, Object> inputMap) throws Exception;
}
