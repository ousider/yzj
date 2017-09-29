package xn.pigfarm.mapper.supplychian;

import java.util.List;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.RequireDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-20 13:37:15
 */
public interface RequireDetailMapper extends IBaseDataMapper<RequireDetailModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    RequireDetailModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<RequireDetailModel> searchToList(long farmId);
    
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

    /**
     * @Description: 批量作废
     * @author THL
     * @param record
     * @return
     */
    int updatesForScrap(@Param("ids") long[] ids);

}
