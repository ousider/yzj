package xn.pigfarm.mapper.hana;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.hana.SapMtcPgstatDtlModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-6-28 10:40:57
 */
public interface SapMtcPgstatDtlMapper extends IBaseDataMapper<SapMtcPgstatDtlModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    SapMtcPgstatDtlModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<SapMtcPgstatDtlModel> searchToList(long farmId);
    
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
