package xn.pigfarm.mapper.production;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.production.SemenSaleModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-8-17 14:39:39
 */
public interface SemenSaleMapper extends IBaseDataMapper<SemenSaleModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SemenSaleModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SemenSaleModel> searchToList(long farmId);
    
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
     * @Description: 撤销所用删除 DELETED_FLAG = 2
     * @author THL
     * @param records
     * @return
     */
    public int deletesForCancel(@Param("records") List<SemenSaleModel> records);

}
