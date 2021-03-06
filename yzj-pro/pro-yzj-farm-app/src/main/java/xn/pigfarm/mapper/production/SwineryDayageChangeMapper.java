package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.SwineryDayageChangeModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-9-6 11:24:54
 */
public interface SwineryDayageChangeMapper extends IBaseDataMapper<SwineryDayageChangeModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SwineryDayageChangeModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SwineryDayageChangeModel> searchToList(long farmId);
    
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
     * @author yangy
     * @param map
     * @return
     */
    int deletesByCon2(Map<String, Object> map);
}
