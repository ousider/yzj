package xn.pigfarm.mapper.portal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-07-25 16:52:01
 */
public interface PortalMapper extends IMapper<Object> {

    List<Object> searchListByDate(List<Map<String, String>> list);

    List<Object> searchProductionByDate(@Param("list") List<Map<String, Object>> list);

    List<Object> searchDieByDate(@Param("list") List<Map<String, String>> list);

    List<Object> searchParity(@Param("list") List<Map<String, String>> list);

    List<Object> searchParityGroup(Map<String, String> map);

    List<Map<String, Object>> searchSwineryClassNumByDate(@Param("list") List<Map<String, Object>> list);

    List<Map<String, Object>> searchSwineryClassNumBySwineryId(Map<String, Object> map);

    List<Map<String, Object>> searchSwineryIdLimit4(Long farmId);

    List<Map<String, Object>> searchWeekInYear(Map<String, Long> mapWeekParam);

    List<Map<String, Object>> searchPigExceptionScale(Map<String, String> map);

    List<Map<String, Object>> searchSwineryPigExcepitionBySwineryId(Map<String, String> map);

    List<Map<String, String>> searchWeek(@Param("farmId") long farmId);

    List<Map<String, String>> searchSwineryClass(@Param("farmId") long farmId);

    List<Map<String, Object>> productionAbnormalByBreed(Map<String, Long> map);

    List<Map<String, Object>> productionAbnormalByLaborHouse(Map<String, Long> map);

    List<Map<String, Object>> productionAbnormalByDelivery(Map<String, Long> map);

    List<Map<String, Object>> productionAbnormalByWean(Map<String, Long> map);

    List<Map<String, Object>> productionAbnormalBySell(Map<String, Long> map);

    List<Map<String, Object>> searchDeliveryExceptionDetail(Map<String, Long> map);

    List<Map<String, Object>> productionAbnormalByObsolete(Map<String, Long> map);

    List<Map<String, Object>> searchChangePigpenAndHouseByDateType(Map<String, String> map);

    Map<String, Object> searchNonProductionDays(Map<String, String> map);

    List<Map<String, Object>> searchDayIndexByDate(List<Map<String, String>> list);

    Map<String, Object> searchDayIndexByDate(Map<String, String> map);

    Map<String, Object> searchDayIndexByProductDate(Map<String, String> map);

    Map<String, Object> searchDayIndexByLeaveDate(Map<String, String> map);

    Map<String, Object> search7DayAlreadyBreedProbabilityByDate(Map<String, String> map);

    Map<String, Object> searchDeliveryProbabilityByDate(Map<String, String> map);

    Map<String, Object> searchPigBirthWeightByDate(Map<String, String> map);

    Map<String, Object> search24DayWeanPigWeightByDate(Map<String, String> map);

    Map<String, Object> search7030PigWeightByDate(Map<String, String> map);

    Map<String, Object> searchXXSurvivalRateByDate(Map<String, String> map);

    Map<String, Object> searchCompleteSurvivalRateByDate(Map<String, String> map);

    Map<String, Object> searchDeliverySurvivalRateByDate(Map<String, String> map);

    Map<String, Object> search110kgOutHouseByDate(Map<String, String> map);

    Map<String, Object> searchPSYByDate(Map<String, String> map);

    Map<String, Object> searchMSYByDate(Map<String, String> map);

    Map<String, Object> searchProductPigProductionDaysByDate(Map<String, String> map);

    Map<String, Object> getWeightByDayAge(Map<String, String> map);

    Map<String, Object> searchGroupSsalesOfRealTimeStatistics1();

    Map<String, Object> searchGroupSsalesOfRealTimeStatistics2();

    Map<String, Object> searchGroupSsalesOfRealTimeStatistics3();

    List<Map<String, Object>> searchCompanyAddreed();

    List<Map<String, Object>> searchFarmSalePig();

    List<Map<String, Object>> searchImperfectPigSaleChange(List<Map<String, String>> list);

    List<Map<String, Object>> searchCustomerAnalysis();
}
