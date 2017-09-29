
package xn.pigfarm.controller.analyticalData;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.unityreturn.BuildReturnMapUtil;
import xn.pigfarm.service.analyticalData.IAnalyticalDataService;

/**
 * @Description: 分析数据
 * @author thl
 * @date 2016年7月20日 上午13:02:58
 */
@Component
@Controller
@RequestMapping("/analyticalData")
public class AnalyticalDataController extends BaseController {

    @Autowired
    private IAnalyticalDataService analyticalDataService;

    // 搜索种猪死亡总数
    @RequestMapping("/searchBoarAndSowDieTotalToList")
    @ResponseBody
    public Map<String, Object> searchBoarAndSowDieTotalToList(HttpServletRequest request) throws Exception {

        return BuildReturnMapUtil.getReturnMap(analyticalDataService.searchBoarAndSowDieTotalToList(getMap()));

    }

    // 搜索种猪淘汰总数
    @RequestMapping("/searchBoarAndSowObsoleteTotalToList")
    @ResponseBody
    public Map<String, Object> searchBoarAndSowObsoleteTotalToList(HttpServletRequest request) throws Exception {

        return BuildReturnMapUtil.getReturnMap(analyticalDataService.searchBoarAndSowObsoleteTotalToList(getMap()));

    }

    // 产房死淘率
    @RequestMapping("/searchDiePercentOfDelivery")
    @ResponseBody
    public Map<String, Object> searchDiePercentOfDelivery(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchDiePercentOfDelivery(getMap()));
    }

    // 保育死淘率
    @RequestMapping("/searchDiePercentOfChlidCare")
    @ResponseBody
    public Map<String, Object> searchDiePercentOfChlidCare(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchDiePercentOfChlidCare(getMap()));
    }

    // 育肥死淘率
    @RequestMapping("/searchDiePercentOfToFat")
    @ResponseBody
    public Map<String, Object> searchDiePercentOfToFat(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchDiePercentOfToFat(getMap()));
    }

    // 全程死淘率
    @RequestMapping("/searchDiePercentOfAll")
    @ResponseBody
    public Map<String, Object> searchDiePercentOfAll(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchDiePercentOfAll(getMap()));
    }

    // 全程死亡头数
    @RequestMapping("/searchDieNumOfAll")
    @ResponseBody
    public Map<String, Object> searchDieNumOfAll(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchDieNumOfAll(getMap()));
    }

    // 存栏分析_比例
    @RequestMapping("/searchPopulationAnalysis")
    @ResponseBody
    public Map<String, Object> searchPopulationAnalysis(HttpServletRequest request) throws Exception {
        
        return getReturnMap(analyticalDataService.searchPopulationAnalysis(getMap()));
    }

    // 存栏分析_数值
    @RequestMapping("/searchPopulationAnalysis2")
    @ResponseBody
    public Map<String, Object> searchPopulationAnalysis2(HttpServletRequest request) throws Exception {
        
        return getReturnMap(analyticalDataService.searchPopulationAnalysis2(getMap()));
    }

    // 各场死淘汰率
    @RequestMapping("/searchEachPigfarmDiePercentOfAll")
    @ResponseBody
    public Map<String, Object> searchEachPigfarmDiePercentOfAll(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchEachPigfarmDiePercentOfAll(getMap()));
    }
    
    // 存栏详情
    @RequestMapping("/searchStockDetails")
    @ResponseBody
    public Map<String, Object> searchStockDetails(HttpServletRequest request) throws Exception {

        return getReturnMap(analyticalDataService.searchStockDetails(getMap()));
    }
    
    // 猪场存栏
    @RequestMapping("/searchStockDetailsForFarmId")
    @ResponseBody
    public Map<String, Object> searchStockDetailsForFarmId(HttpServletRequest request) throws Exception {
        
        return getReturnMap(analyticalDataService.searchStockDetailsForFarmId(getMap()));
    }
    
    // 销售总计
    @RequestMapping("/searchSaleTotal")
    @ResponseBody
    public Map<String, Object> searchSaleTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleTotal(getMap()));
    }

    // 销售苗猪总计
    @RequestMapping("/searchSalePiggyPigTotal")
    @ResponseBody
    public Map<String, Object> searchSalePiggyPigTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchSalePiggyPigTotal(getMap()));
    }

    // 销售肥猪总计
    @RequestMapping("/searchSaleHogPigTotal")
    @ResponseBody
    public Map<String, Object> searchSaleHogPigTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleHogPigTotal(getMap()));
    }

    // 销售种猪总计
    @RequestMapping("/searchSaleBoarTotal")
    @ResponseBody
    public Map<String, Object> searchSaleBoarTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleBoarTotal(getMap()));
    }

    // 销售其他类型总计
    @RequestMapping("/searchSaleOtherTotal")
    @ResponseBody
    public Map<String, Object> searchSaleOtherTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleOtherTotal(getMap()));
    }

    // 可上市肥猪头数
    @RequestMapping("/searchGoPublicHogPig")
    @ResponseBody
    public Map<String, Object> searchGoPublicHogPig() throws Exception {

        return getReturnMap(analyticalDataService.searchGoPublicHogPig(getMap()));
    }

    // 选择猪场
    @RequestMapping("/searchFarmByOrigin")
    @ResponseBody
    public Map<String, Object> searchFarmByOrigin() throws Exception {

        return getReturnMap(analyticalDataService.searchFarmByOrigin(getMap()));
    }

    // 种猪死亡数量详情
    @RequestMapping("/searchBoarDieTotal")
    @ResponseBody
    public Map<String, Object> searchBoarDieTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchBoarDieTotal(getMap()));
    }

    // 种猪淘汰数量详情
    @RequestMapping("/searchBoarObsoleteTotal")
    @ResponseBody
    public Map<String, Object> searchBoarObsoleteTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchBoarObsoleteTotal(getMap()));
    }

    // 猪舍死亡总计
    @RequestMapping("/searchFarmHouseDieTotal")
    @ResponseBody
    public Map<String, Object> searchFarmHouseDieTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchFarmHouseDieTotal(getMap()));
    }

    // 死亡原因总计
    @RequestMapping("/searchLeaveReasonDieTotal")
    @ResponseBody
    public Map<String, Object> searchLeaveReasonDieTotal() throws Exception {

        return getReturnMap(analyticalDataService.searchLeaveReasonDieTotal(getMap()));
    }

    // 集团门户生产分析
    @RequestMapping("/searchGroupHomeProData")
    @ResponseBody
    public Map<String, Object> searchGroupHomeProData() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupHomeProData(getMap()));
    }

    // 集团门户销售分析
    @RequestMapping("/searchGroupHomeSaleData")
    @ResponseBody
    public Map<String, Object> searchGroupHomeSaleData() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupHomeSaleData(getMap()));
    }

    // 集团门户大生物安全
    @RequestMapping("/searchGroupHomeSafetyData")
    @ResponseBody
    public Map<String, Object> searchGroupHomeSafetyData() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupHomeSafetyData(getMap()));
    }

    // 综合指标
    @RequestMapping("/searchGroupIndicator")
    @ResponseBody
    public Map<String, Object> searchGroupIndicator() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupIndicator(getMap()));
    }

    // 集团繁殖情况
    @RequestMapping("/searchGroupReproductionCondition")
    @ResponseBody
    public Map<String, Object> searchGroupReproductionCondition() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupReproductionCondition(getMap()));
    }

    // 集团产仔情况
    @RequestMapping("/searchGroupBirthCondition")
    @ResponseBody
    public Map<String, Object> searchGroupBirthCondition() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupBirthCondition(getMap()));
    }

    // 集团生产动态配种明细
    @RequestMapping("/searchGroupBreedDetail")
    @ResponseBody
    public Map<String, Object> searchGroupBreedDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupBreedDetail(getMap()));
    }

    // 集团生产动态妊检明细
    @RequestMapping("/searchGroupPregnancyCheckDetail")
    @ResponseBody
    public Map<String, Object> searchGroupPregnancyCheckDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupPregnancyCheckDetail(getMap()));
    }

    // 集团生产动态分娩明细
    @RequestMapping("/searchGroupDeliveryDetail")
    @ResponseBody
    public Map<String, Object> searchGroupDeliveryDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupDeliveryDetail(getMap()));
    }

    // 集团生产动态选种明细
    @RequestMapping("/searchGroupSeedPigDetail")
    @ResponseBody
    public Map<String, Object> searchGroupSeedPigDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupSeedPigDetail(getMap()));
    }

    // 集团生产动态死亡明细
    @RequestMapping("/searchGroupDieDetail")
    @ResponseBody
    public Map<String, Object> searchGroupDieDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupDieDetail(getMap()));
    }

    // 集团大生物安全供应链动态本月购入
    @RequestMapping("/searchGroupSupplyMonthInput")
    @ResponseBody
    public Map<String, Object> searchGroupSupplyMonthInput() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupSupplyMonthInput(getMap()));
    }

    // 集团大生物安全供应链动态本月使用
    @RequestMapping("/searchGroupSupplyMonthUsed")
    @ResponseBody
    public Map<String, Object> searchGroupSupplyMonthUsed() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupSupplyMonthUsed(getMap()));
    }

    // 集团大生物安全供应链动态本月库存
    @RequestMapping("/searchGroupSupplyMonthStore")
    @ResponseBody
    public Map<String, Object> searchGroupSupplyMonthStore() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupSupplyMonthStore(getMap()));
    }

    // 变动情况
    @RequestMapping("/searchGroupChangeCondition")
    @ResponseBody
    public Map<String, Object> searchGroupChangeCondition() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupChangeCondition(getMap()));
    }

    // 变动情况明细
    @RequestMapping("/searchGroupChangeConditionDetail")
    @ResponseBody
    public Map<String, Object> searchGroupChangeConditionDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchGroupChangeConditionDetail(getMap()));
    }

    // 绩效数据-产活仔数
    @RequestMapping("/searchPerformanceAliveNum")
    @ResponseBody
    public Map<String, Object> searchPerformanceAliveNum() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceAliveNum(getMap()));
    }

    // 绩效数据-分娩率
    @RequestMapping("/searchPerformanceDeliveryRate")
    @ResponseBody
    public Map<String, Object> searchPerformanceDeliveryRate() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceDeliveryRate(getMap()));
    }

    // 绩效数据-膘情13-18
    @RequestMapping("/searchPerformanceBackFat")
    @ResponseBody
    public Map<String, Object> searchPerformanceBackFat() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceBackFat(getMap()));
    }

    // 绩效数据-窝均断奶重
    @RequestMapping("/searchPerformanceAvgWeanWeight")
    @ResponseBody
    public Map<String, Object> searchPerformanceAvgWeanWeight() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceAvgWeanWeight(getMap()));
    }

    // 绩效数据-窝均断奶头数
    @RequestMapping("/searchPerformanceAvgWeanNum")
    @ResponseBody
    public Map<String, Object> searchPerformanceAvgWeanNum() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceAvgWeanNum(getMap()));
    }

    // 绩效数据-保育成活率
    @RequestMapping("/searchPerformanceToCareAliveRate")
    @ResponseBody
    public Map<String, Object> searchPerformanceToCareAliveRate() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceToCareAliveRate(getMap()));
    }

    // 绩效数据-7030
    @RequestMapping("/searchPerformance7030")
    @ResponseBody
    public Map<String, Object> searchPerformance7030() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformance7030(getMap()));
    }

    // 绩效数据-育肥成活率
    @RequestMapping("/searchPerformanceToFattenAliveRate")
    @ResponseBody
    public Map<String, Object> searchPerformanceToFattenAliveRate() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformanceToFattenAliveRate(getMap()));
    }

    // 绩效数据-110KG出栏
    @RequestMapping("/searchPerformance110KGOut")
    @ResponseBody
    public Map<String, Object> searchPerformance110KGOut() throws Exception {

        return getReturnMap(analyticalDataService.searchPerformance110KGOut(getMap()));
    }

    // 7天断配率
    @RequestMapping("/search7DayWeanBreedRate")
    @ResponseBody
    public Map<String, Object> search7DayWeanBreedRate() throws Exception {

        return getReturnMap(analyticalDataService.search7DayWeanBreedRate(getMap()));
    }

    // 销售情况-外销
    @RequestMapping("/searchSaleCondition")
    @ResponseBody
    public Map<String, Object> searchSaleCondition() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleCondition(getMap()));
    }

    // 销售情况-内销/调拨
    @RequestMapping("/searchSaleConditionInsideAndAllocation")
    @ResponseBody
    public Map<String, Object> searchSaleConditionInsideAndAllocation() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleConditionInsideAndAllocation(getMap()));
    }

    // 销售未结算
    @RequestMapping("/searchSaleNotBalanceNum")
    @ResponseBody
    public Map<String, Object> searchSaleNotBalanceNum() throws Exception {

        return getReturnMap(analyticalDataService.searchSaleNotBalanceNum(getMap()));
    }

    // 供应链提醒-过期提醒
    @RequestMapping("/searchSupplyOverdueRemind")
    @ResponseBody
    public Map<String, Object> searchSupplyOverdueRemind() throws Exception {

        return getReturnMap(analyticalDataService.searchSupplyOverdueRemind(getMap()));
    }

    // 供应链提醒-过期提醒明细
    @RequestMapping("/searchSupplyOverdueRemindDetail")
    @ResponseBody
    public Map<String, Object> searchSupplyOverdueRemindDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchSupplyOverdueRemindDetail(getMap()));
    }

    // 供应链提醒-使用提醒
    @RequestMapping("/searchSupplyUsedRemind")
    @ResponseBody
    public Map<String, Object> searchSupplyUsedRemind() throws Exception {

        return getReturnMap(analyticalDataService.searchSupplyUsedRemind(getMap()));
    }

    // 供应链提醒-使用提醒明细
    @RequestMapping("/searchSupplyUsedRemindDetail")
    @ResponseBody
    public Map<String, Object> searchSupplyUsedRemindDetail() throws Exception {

        return getReturnMap(analyticalDataService.searchSupplyUsedRemindDetail(getMap()));
    }

    // 查询生产日报
    @RequestMapping("/searchProductionDaily")
    @ResponseBody
    public Map<String, Object> searchProductionDaily() throws Exception {

        return getReturnMap(analyticalDataService.searchProductionDaily(getMap()));
    }

    // 查询生产日报
    @RequestMapping("/editProductionDailySure")
    @ResponseBody
    public Map<String, Object> editProductionDailySure() throws Exception {
        analyticalDataService.editProductionDailySure(getMap());
        return getReturnMap();
    }
}
