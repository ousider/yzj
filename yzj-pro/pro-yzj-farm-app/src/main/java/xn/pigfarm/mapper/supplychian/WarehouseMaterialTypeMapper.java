package xn.pigfarm.mapper.supplychian;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.supplychian.WarehouseMaterialTypeModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-9-18 17:10:45
 */
public interface WarehouseMaterialTypeMapper extends IBaseDataMapper<WarehouseMaterialTypeModel> {
    
    
    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<WarehouseMaterialTypeModel> searchToList();
    
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
