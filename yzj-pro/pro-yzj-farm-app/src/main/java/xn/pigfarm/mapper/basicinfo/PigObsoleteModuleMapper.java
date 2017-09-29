package xn.pigfarm.mapper.basicinfo;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.basicinfo.PigObsoleteModuleModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-9-27 20:26:12
 */
public interface PigObsoleteModuleMapper extends IBaseDataMapper<PigObsoleteModuleModel> {
    
    
    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<PigObsoleteModuleModel> searchToList();
    
    /**
     * @Description: 删除
     * @author Zhangjc
     * @param id
     * @return
     */
    int delete( @Param("rowId") long rowId );

    /**
     * @Description: 批量删除
     * @author Zhangjc
     * @param ids
     * @return
     */
    int deletes(@Param("ids")long[] ids);
    
}
