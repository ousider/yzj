package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.SemenSaleDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-8-21 16:29:04
 */
public interface SemenSaleDetailMapper extends IBaseDataMapper<SemenSaleDetailModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SemenSaleDetailModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SemenSaleDetailModel> searchToList();
    
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
    public int deletesForCancel(@Param("records") List<SemenSaleDetailModel> records);


}
