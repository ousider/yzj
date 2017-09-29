package xn.pigfarm.mapper.production;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.NurseModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-10-19 15:33:40
 */
public interface NurseMapper extends IBaseDataMapper<NurseModel> {
    
    
    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<NurseModel> searchToList(long farmId);
    
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
