package xn.pigfarm.mapper.supplychian;

import java.util.List;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.ReceiptInputModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-7-21 11:36:50
 */
public interface ReceiptInputMapper extends IBaseDataMapper<ReceiptInputModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReceiptInputModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReceiptInputModel> searchToList(long farmId);
    
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
