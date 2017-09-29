package xn.pigfarm.mapper.supplychian;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.supplychian.StoreMaterialModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-11-30 22:16:29
 */
public interface StoreMaterialMapper extends IBaseDataMapper<StoreMaterialModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    StoreMaterialModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<StoreMaterialModel> searchToList(long farmId);
    
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
     * @Description: 批量加入存库数量
     * @author THL
     * @param record
     * @return
     */
    int updateActualQtyToStore(@Param("records") List<Map<String, Object>> records);

    /**
     * @Description: 查询当时库存某个物料的总数减去已被其他技术员预出库的数量
     * @author THL
     * @return
     */
    List<StoreMaterialModel> searchTotalQtyExceptOtherUse(@Param("dafengModel") String dafengModel, @Param("farmId") long farmId,
            @Param("materialId") long materialId, @Param("warehouseId") long warehouseId);

}
