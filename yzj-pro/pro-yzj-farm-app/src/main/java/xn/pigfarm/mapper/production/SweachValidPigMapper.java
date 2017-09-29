

package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.SearchValidPigModel;
import xn.pigfarm.model.production.ValidPigModel;


/**
 * @Description: 表单
 * @author fangc
 * @date 2016年4月25日 下午12:35:03
 */
public interface SweachValidPigMapper{

    public List<ValidPigModel> searchValidPigToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchPorkToList(Map<String, Object> map) throws Exception;
    
    public List<ValidPigModel> searchChildCareToList(Map<String, Object> map) throws Exception;
    
    public List<ValidPigModel> searchSeedPigToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchBreedPigToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchSeedPigDieToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchGoodPigToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchBreedPigByGoodToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchPorkCheckToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchGoodPigByBreedToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchLivestockByProduceToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<Map<String, Object>> searchBreedChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchPregnancyCheckChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchDeliveryChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchWeanChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchToChildCareChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchToFattenChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchBreedPigDieChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchChildPigDieChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchChildCareDieChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchFattenDieChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchChildCareSaleChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchFattenSaleChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<Map<String, Object>> searchDefectsSaleChangeToList(@Param("companyMark") String companyMark, @Param("startDate") String startDate,
            @Param("endDate") String endDate) throws Exception;

    public List<ValidPigModel> searchSaleSemenToList(SearchValidPigModel selectValidPigModel) throws Exception;
    
    public List<ValidPigModel> operSql(Map<String, String> map);

    public List<ValidPigModel> searchPorkCheckByLastMonthToList(@Param("farmId") long farmId, @Param("billDate") String billDate) throws Exception;

    public List<Map<String, Object>> searchPorkCheckByLastMonthBoarToList(@Param("farmId") long farmId, @Param("billDate") String billDate)
            throws Exception;

    public List<ValidPigModel> searchGoodPigDieToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchTochildPigToList(SearchValidPigModel selectValidPigModel) throws Exception;

    public List<ValidPigModel> searchToFattenPigToList(SearchValidPigModel selectValidPigModel) throws Exception;
}
