package xn.pigfarm.mapper.supplychian;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.supplychian.DailyRecordModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-9-28 14:47:05
 */
public interface DailyRecordMapper extends IBaseDataMapper<DailyRecordModel> {
    
    
    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<DailyRecordModel> searchToList(long farmId);
    
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
