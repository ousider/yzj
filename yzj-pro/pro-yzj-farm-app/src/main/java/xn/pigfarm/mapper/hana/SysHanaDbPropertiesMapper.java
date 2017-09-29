package xn.pigfarm.mapper.hana;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.hana.SysHanaDbPropertiesModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-15 11:43:36
 */
public interface SysHanaDbPropertiesMapper extends IBaseDataMapper<SysHanaDbPropertiesModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SysHanaDbPropertiesModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SysHanaDbPropertiesModel> searchToList();
    
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
