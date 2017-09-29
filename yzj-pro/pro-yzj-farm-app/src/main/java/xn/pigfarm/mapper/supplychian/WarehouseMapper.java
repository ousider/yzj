package xn.pigfarm.mapper.supplychian;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.WarehouseModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-1-23 9:35:10
 */
public interface WarehouseMapper extends IBaseDataMapper<WarehouseModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    WarehouseModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<WarehouseModel> searchToList(long farmId);
    
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
     * @Description: 批量禁用
     * @author yangy
     * @param ids
     * @return
     */
    int forbiddens(@Param("ids") long[] ids);

    /**
     * @Description: 批量启用
     * @author yangy
     * @param ids
     * @return
     */
    int uses(@Param("ids") long[] ids);

    /**
     * @Description: 根据sql获取数据
     * @author yangy
     * @param map
     * @return
     */
    public List<Map<String, String>> getInfos(Map<String, String> map);

}
