package xn.pigfarm.mapper.basicinfo;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.basicinfo.HouseModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-3-15 16:49:16
 */
public interface HouseMapper extends IBaseDataMapper<HouseModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    HouseModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<HouseModel> searchToList(long farmId);
    
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

    List<Map<String, Object>> searchHouseDetailedToPage(Map<String,Object> map );

    List<Map<String, Object>> searchHousePigDetailedToPage(Map<String,Object> map );

}
