package xn.pigfarm.mapper.supplychian;

import java.util.List;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.RequireModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-20 13:37:14
 */
public interface RequireMapper extends IBaseDataMapper<RequireModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    RequireModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<RequireModel> searchToList(long farmId);
    
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
     * @Description: 批量作废，填写作废原因
     * @author THL
     * @param record
     * @return
     */
    int updatesForScrap(@Param("records") List<RequireModel> records);

}
