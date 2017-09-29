package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.ToworkModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-11-21 13:34:06
 */
public interface ToworkMapper extends IBaseDataMapper<ToworkModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ToworkModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ToworkModel> searchToList(long farmId);
    
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
     * @Description:根据条件删除
     * @author THL
     * @param map
     * @return
     */
    int deletesByCon2(Map<String, Object> map);
    
    /**
     * @Description: 修改上一条记录的TOWORK_DATE_OUT和PIG_CLASS_OUT为NULL
     * @author THL
     * @param records
     * @return
     */
    int updatesToworkDateOutAndPigClassOutToNULL(@Param("records") List<ToworkModel> records);
}
