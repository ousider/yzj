package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.production.ChangeSwineryModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-12-19 9:20:17
 */
public interface ChangeSwineryMapper extends IBaseDataMapper<ChangeSwineryModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ChangeSwineryModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ChangeSwineryModel> searchToList(long farmId);
    
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
     * @Description: 修改上一条CHANGE_SWINERY_DATE_OUT和SWINERY_ID_OUT为NULL
     * @author THL
     * @param records
     * @return
     */
    int updatesOutFieldToNULL(@Param("records") List<ChangeSwineryModel> records);

}
