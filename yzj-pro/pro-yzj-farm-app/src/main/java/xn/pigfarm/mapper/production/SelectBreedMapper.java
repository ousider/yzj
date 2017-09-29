package xn.pigfarm.mapper.production;

import java.util.List;

import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.SelectBreedModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-11-18 15:13:50
 */
public interface SelectBreedMapper extends IBaseDataMapper<SelectBreedModel> {
    
    
    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<SelectBreedModel> searchToList(long farmId);
    
    /**
     * @Description: 删除
     * @author Zhangjc
     * @param id
     * @return
     */
    int delete( @Param("rowId") long rowId );

    /**
     * @Description: 批量删除
     * @author Zhangjc
     * @param ids
     * @return
     */
    int deletes(@Param("ids")long[] ids);
    
}
