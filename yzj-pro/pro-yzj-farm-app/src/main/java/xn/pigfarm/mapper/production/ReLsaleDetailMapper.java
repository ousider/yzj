package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.ReLsaleDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-5-31 22:33:15
 */
public interface ReLsaleDetailMapper extends IBaseDataMapper<ReLsaleDetailModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReLsaleDetailModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReLsaleDetailModel> searchToList(long farmId);
    
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
     * @Description: 商品猪销售
     * @author 系统生成
     * @return
     */
    List<ReLsaleDetailModel> searchProduceDataByGoodPigSell(Map<String,Object> map);
    
    /**
     * @Description: 商品猪销售
     * @author 系统生成
     * @return
     */
    List<Map<String,Object>> searchProduceDataByGoodPigSellNote(Map<String,Object> map);
    
}
