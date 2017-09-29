package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.SalePlanModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-5-26 12:04:48
 */
public interface SalePlanMapper extends IBaseDataMapper<SalePlanModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SalePlanModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SalePlanModel> searchToList(long farmId);
    
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
