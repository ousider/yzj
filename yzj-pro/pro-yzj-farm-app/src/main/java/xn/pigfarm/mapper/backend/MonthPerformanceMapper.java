package xn.pigfarm.mapper.backend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.backend.MonthPerformanceModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-8-18 11:48:45
 */
public interface MonthPerformanceMapper extends IBaseDataMapper<MonthPerformanceModel> {

    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    MonthPerformanceModel searchById(@Param("rowId") long rowId);

    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<MonthPerformanceModel> searchToList(long farmId);

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
     * @Description: 查询系统产活数
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionBySurvive(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark);

    /**
     * @Description: 查询分娩率
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByDeliver(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark);

    /**
     * @Description: 查询膘情
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByFatness(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark);

    /**
     * @Description: 查询窝均断奶重
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByWean(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark, @Param("farmId") long farmId, @Param("weanNum") double weanNum);

    /**
     * @Description: 查询保育成活率
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByChildSurvive(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("supCompanyMark") String supCompanyMark, @Param("companyMark") String companyMark);

    /**
     * @Description: 查询7030
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByChild(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark, @Param("farmId") long farmId);

    /**
     * @Description: 查询110公交出栏
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByFatField(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark, @Param("farmId") long farmId);

    /**
     * @Description: 查询育肥成活率
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByFatSurvive(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("supCompanyMark") String supCompanyMark, @Param("companyMark") String companyMark);

    /**
     * @Description: 查询窝均断奶头数
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByWeanNum(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark, @Param("minBirthDate") String minBirthDate);

    /**
     * @Description: 查询全程死亡率
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByWholeDie(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark);

    /**
     * @Description: 猪只最小出生日期
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchMinPorkBirthDate(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("companyMark") String companyMark, @Param("changeType") String changeType, @Param("houseType") long houseType);

    /**
     * @Description: 集团分娩率
     * @author Cress
     * @param startDate
     * @param endDate
     * @param origin
     * @return
     */
    MonthPerformanceModel searchProductionByDeliverGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("origin") String origin);

    /**
     * @Description: 集团猪只最小出生日期
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchMinPorkBirthDateGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("origin") String origin, @Param("changeType") String changeType, @Param("houseType") long houseType);

    /**
     * @Description: 查询集团窝均断奶头数
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByWeanNumGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("origin") String origin, @Param("minBirthDate") String minBirthDate);

    /**
     * @Description: 查询集团窝均断奶重
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByWeanGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("origin") String origin, @Param("farmId") long farmId, @Param("weanNum") double weanNum);

    /**
     * @Description: 查询集团7030
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByChildGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("origin") String origin, @Param("farmId") long farmId);

    /**
     * @Description: 查询集团110KG出栏
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByFatFieldGroup(@Param("startDate") String startDate, @Param("endDate") String endDate,
            @Param("origin") String origin, @Param("farmId") long farmId);

    /**
     * @Description: 查询上产房背膘
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionBySCFFatness(Map<String, Object> parmMap);

    /**
     * @Description: 查询断奶背膘
     * @author yangy
     * @param
     * @return
     */
    MonthPerformanceModel searchProductionByDNFatness(Map<String, Object> parmMap);

    /**
     * @Description: 窝均断奶头数(头)/窝均断奶窝重（KG）
     * @author yangy
     * @param
     * @return
     */
    Map<String, Object> searchProductionByWjdnsAndWjdnwz(Map<String, Object> parmMap);

    /**
     * @Description: 集团窝均断奶头数(头)/窝均断奶窝重（KG）
     * @author yangy
     * @param
     * @return
     */
    Map<String, Object> searchProductionByWjdnsAndWjdnwzGroup(Map<String, Object> parmMap);

    /**
     * @Description: 获取生产日报存栏
     * @author yangy
     * @param
     * @return
     */
    public List<Map<String, Object>> searchLivestock(Map<String, Object> map) throws Exception;

}
