package xn.pigfarm.mapper.supplychian;

import java.util.List;

import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.PurchaseDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-5-9 11:32:31
 */
public interface PurchaseDetailMapper extends IBaseDataMapper<PurchaseDetailModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    PurchaseDetailModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<PurchaseDetailModel> searchToList(long farmId);
    
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
     * @Description: 批量修改(单价，赠送比例)
     * @author THL
     * @param record
     * @return
     */
    int updatesOtherInfo(@Param("records") List<PurchaseDetailModel> records);

    /**
     * @Description: 更新返回饲料到第一条采购记录中
     * @author THL
     * @param records
     * @return
     */
    int updatesFeedPurchaseQtyReturn(@Param("records") List<PurchaseDetailModel> records);

}
