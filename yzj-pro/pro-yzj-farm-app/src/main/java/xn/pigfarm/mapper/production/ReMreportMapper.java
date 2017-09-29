package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.ReMreportModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-28 14:53:58
 */
public interface ReMreportMapper extends IBaseDataMapper<ReMreportModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReMreportModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReMreportModel> searchToList(long farmId);
    
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
     * @Description: 批量删除
     * @author 系统生成
     * @param ids
     * @return
     */
    List<Map<String,Object>> searchProductionCollectPage(Map<String,Object> map);
    
}
