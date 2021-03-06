package xn.pigfarm.mapper.basicinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.pigfarm.model.basicinfo.PorkPigModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-7-21 11:47:41
 */
public interface PorkPigMapper extends IBaseDataMapper<PorkPigModel> {

    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    PorkPigModel searchById(@Param("rowId") long rowId);

    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<PorkPigModel> searchToList(long farmId);

    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    int delete(@Param("rowId") long rowId);

    /**
     * @Description: 批量删除
     * @author 系统生成
     * @param ids
     * @return
     */
    int deletes(@Param("ids") long[] ids);

}
