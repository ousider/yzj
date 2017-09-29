package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.ReLweanDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-5-29 16:27:20
 */
public interface ReLweanDetailMapper extends IBaseDataMapper<ReLweanDetailModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReLweanDetailModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReLweanDetailModel> searchToList(long farmId);
    
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
    List<ReLweanDetailModel> searchProduceDataByWeanData(Map<String,Object> map);
}
