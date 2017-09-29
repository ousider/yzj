package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.production.ChangeHouseInfoView;
import xn.pigfarm.model.production.ChangeHouseModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-2-15 10:14:53
 */
public interface ChangeHouseMapper extends IBaseDataMapper<ChangeHouseModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ChangeHouseModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ChangeHouseModel> searchToList(long farmId);
    
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
     * @Description: 根据猪只id查转舍所需信息
     * @author Administrator
     * @param ids
     * @return
     */
    List<ChangeHouseInfoView> searchChangeHouseInfo(@Param("ids") List<Long> ids);

    /**
     * @Description: 修改上一条HOUSE_ID_OUT和CHANGE_HOUSE_DATE_OUT和PIGPEN_ID_OUT为NULL
     * @author THL
     * @param records
     * @return
     */
    int updatesOutFieldToNULL(@Param("records") List<ChangeHouseModel> records);
    
}
