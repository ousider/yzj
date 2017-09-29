package xn.pigfarm.service.portal;

import java.util.List;
import java.util.Map;

import xn.pigfarm.model.portal.ParityDistributionModel;

public interface IPortalService {


    List<Object> searchAmountSize(String dateType, Long farmId) throws Exception;

    List<Object> searchProductionByDate(String dateType, Long farmId) throws Exception;

    List<Object> searchDieByDate(String dateType, Long farmId) throws Exception;

    List<Object> searchParityByDate(Long farmId, Long farmId2, String pigNum, String isGroup, String companyMark) throws Exception;

    /**
     * @Description: 母猪群蓝图
     * @author 程彬
     * @param farmId
     * @return
     * @throws Exception
     */
    Map<String, Object> searchSwineryClassNumByDate(Long farmId) throws Exception;

    List<Map<String, Object>> searchSwineryClassNumBySwineryId(Long farmId) throws Exception;

    /**
     * @Description: 母猪胎次查询 不分页
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<ParityDistributionModel> searchParityToList() throws Exception;

    /**
     * @Description: 母猪修改
     * @author Administrator
     * @throws Exception
     */
    public void editParity(List<ParityDistributionModel> parityList, long[] ids) throws Exception;

    public void deleteParity(long[] ids) throws Exception;

    public void editNewParity(List<ParityDistributionModel> parityList, long[] ids) throws Exception;

    List<List<List<Map<String, Object>>>> searchSwineryPigExcepitionBySwineryId(Long farmId);

    List<Map<String, Object>> searchDeliveryExceptionDetail(Long farmId, Long swineryId);

    /***
     * @Description: 生产异常提醒 - 配种异常
     * @author fangc
     * @param farmId
     * @return
     */
    List<Map<String, Object>> productionAbnormalByBreed(Long farmId);

    /***
     * @Description: 生产异常提醒 - 上产房异常
     * @author fangc
     * @param farmId
     * @return
     */
    List<Map<String, Object>> productionAbnormalByLaborHouse(Long farmId);

    /***
     * @Description: 生产异常提醒 - 待分娩异常
     * @author fangc
     * @param farmId
     * @return
     */
    List<Map<String, Object>> productionAbnormalByDelivery(Long farmId);

    /***
     * @Description: 生产异常提醒 - 待断奶异常
     * @author fangc
     * @param farmId
     * @return
     */
    List<Map<String, Object>> productionAbnormalByWean(Long farmId);

    /***
     * @Description: 生产异常提醒 - 待出栏异常
     * @author fangc
     * @param farmId
     * @return
     */
    List<Map<String, Object>> productionAbnormalBySell(Long farmId);
    
    /***
     * @Description: 生产异常提醒 - 淘汰提醒
     * @author fangc
     * @param farmId
     * @return
     */
    List<Map<String, Object>> productionAbnormalByObsolete(Long farmId);

    /***
     * @Description: 栏舍周转
     * @author fangc
     * @param dateTyp
     * @return
     */
    Map<String, List<Map<String, Object>>> searchChangePigpenAndHouseByDateType(String dateTyp);

    /***
     * @Description: 综合指标
     * @author fangc
     * @param dateTyp
     * @return
     * @throws Exception
     */
    Map<String, Object> searchSyntheticalIndicatorByDate(String dateType) throws Exception;

    /***
     * @Description: 非生产天数
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchNonProductionDays(Map<String, String> map);
	
	/***
     * @Description: 七天断配率
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double search7DayAlreadyBreedProbabilityByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 分娩率
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchDeliveryProbabilityByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 年产胎次
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchParityOfYearByDate(Map<String, String> map) throws Exception;
	
	/***
     * @Description: 综合指标 初生重
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchPigBirthWeightByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 24日龄断奶重
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double search24DayWeanPigWeightByDate(Map<String, String> map) throws Exception;
	
	/***
     * @Description: 综合指标 7030重
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double search7030PigWeightByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 产房全程成活率
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchDeliverySurvivalRateByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 保育全程成活率
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchWelfareSurvivalRateByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 育肥全程成活率
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchFattenSurvivalRateByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 全场全程成活率
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchCompleteSurvivalRateByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 110KG出栏日龄
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double search110kgOutHouseByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 PSY（预测）
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchPSYByDate(Map<String, String> map);
	
	/***
     * @Description: 综合指标 MSY（预测）
     * @author chengbin
     * @param dateTyp
     * @return
     * @throws Exception
     */
	Double searchMSYByDate(Map<String, String> map);

    /**
     * @Description: 集团销售实时统计
     * @author 程彬
     * @param request
     * @return
     */
    Map<String, Object> searchGroupSsalesOfRealTimeStatistics();
    
    /**
     * @Description: 集团销售实时统计--客户地址
     * @author 程彬
     * @param request
     * @return
     */
    List<Map<String, Object>> searchCompanyAddreed();
    
    /**
     * @Description: 集团_场区商品猪上市销售情况
     * @author 程彬
     * @return
     */
    List<Map<String, Object>> searchFarmSalePig();
    
    /**
     * @Description: 残次猪销售变化
     * @author 程彬
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> searchImperfectPigSaleChange() throws Exception;
    
    /**
     * @Description: 客户分析
     * @author 程彬
     * @return
     * @throws Exception
     */
    Map<String, Object> searchCustomerAnalysis();
    
    /**
     * @Description: 查询周次信息
     * @author 程彬
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> searchWeekInYear(Map<String, Object> map);

}
