package xn.pigfarm.mapper.hana;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.hana.SysHanaInsertLogModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-8-4 13:16:38
 */
public interface SysHanaInsertLogMapper extends IBaseDataMapper<SysHanaInsertLogModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SysHanaInsertLogModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SysHanaInsertLogModel> searchToList(long farmId);
    
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
