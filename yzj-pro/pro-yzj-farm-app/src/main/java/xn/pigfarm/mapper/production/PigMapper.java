package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;

import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.PigView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-5-26 16:52:31
 */
public interface PigMapper extends IBaseDataMapper<PigModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    PigModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<PigModel> searchToList(long farmId);
    
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
     * @Description:根据条件查询
     * @author cb
     * @param map
     * @return
     */
    List<PigView> searchListByCon2(Map<String, String> map);

    /**
     * @Description:撤销用批量删除（DELETED_FLAG = 2）
     * @author thl
     * @param map
     * @return
     */
    int deletesForCancel(@Param("ids") long[] ids);

    /**
     * @Description: 离场+状态查询
     * @param map
     * @return
     */
    List<PigView> searchListByCon3(Map<String, String> map);    

    /**
     * @Description: 修改猪只表LEAVE_DATE为NULL
     * @author THL
     * @param records
     * @return
     */
    int updatesLeaveDateToNULL(@Param("records") List<PigModel> records);

    /**
     * @Description: 修改猪只表SAP_FIXED_ASSETS_EARBRAND为NULL
     * @author THL
     * @param updateSapFixedAssetsEarbrandList
     */
    int updatesSapFixedAssetsEarbrandToNULL(@Param("records") List<PigModel> updateSapFixedAssetsEarbrandList);

}
