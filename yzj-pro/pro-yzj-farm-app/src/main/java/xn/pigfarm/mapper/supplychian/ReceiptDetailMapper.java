package xn.pigfarm.mapper.supplychian;

import java.util.List;

import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.ReceiptDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-12-21 13:40:46
 */
public interface ReceiptDetailMapper extends IBaseDataMapper<ReceiptDetailModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReceiptDetailModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReceiptDetailModel> searchToList(long farmId);
    
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
