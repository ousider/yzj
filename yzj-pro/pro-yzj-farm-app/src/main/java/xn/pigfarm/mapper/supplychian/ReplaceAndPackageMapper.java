package xn.pigfarm.mapper.supplychian;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.ReplaceAndPackageModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-12-7 18:59:32
 */
public interface ReplaceAndPackageMapper extends IBaseDataMapper<ReplaceAndPackageModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    ReplaceAndPackageModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<ReplaceAndPackageModel> searchToList(long farmId);
    
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
