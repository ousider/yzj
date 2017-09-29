package xn.pigfarm.mapper.supplychian;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.supplychian.MonthAccountModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-2-22 14:52:26
 */
public interface MonthAccountMapper extends IBaseDataMapper<MonthAccountModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    MonthAccountModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<MonthAccountModel> searchToList(long farmId);
    
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
     * @Description: 搜索这个月需要盘点物料
     * @author THL
     * @return
     */
    List<Map<String, Object>> searchMonthAccountList(Map<String, Object> record);

    /**
     * @Description: 默认将盘点月份的期末数量设置为0
     * @author THL
     * @param record
     * @return
     */
    int updateCurrentStartDateEndQtyToZero(Map<String, Object> record);

    /**
     * @Description: 将下个月的期初数据删除并且重新插入
     * @author THL
     * @param record
     * @return
     */
    int deleteNextMonthRecord(Map<String, Object> record);

}
