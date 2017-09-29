package xn.pigfarm.mapper.hana;

import java.util.List;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.hana.SysHanaDbFarmModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-27 9:39:15
 */
public interface SysHanaDbFarmMapper extends IBaseDataMapper<SysHanaDbFarmModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SysHanaDbFarmModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SysHanaDbFarmModel> searchToList(long farmId);
    
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
    
}
