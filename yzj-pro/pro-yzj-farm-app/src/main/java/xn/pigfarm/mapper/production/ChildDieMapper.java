package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.ChildDieModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-29 10:45:33
 */
public interface ChildDieMapper extends IBaseDataMapper<ChildDieModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ChildDieModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ChildDieModel> searchToList(long farmId);
    
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

}
