package xn.pigfarm.mapper.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.backend.MonthPerformanceModuleModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-3-14 13:25:26
 */
public interface MonthPerformanceModuleMapper extends IBaseDataMapper<MonthPerformanceModuleModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    MonthPerformanceModuleModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<MonthPerformanceModuleModel> searchToList(long farmId);
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    int delete( @Param("rowId") long rowId );

    /**
     * @Description: 批量删除
     * @author 系统生成
     * @param ids
     * @return
     */
    int deletes(@Param("ids")long[] ids);
    
}
