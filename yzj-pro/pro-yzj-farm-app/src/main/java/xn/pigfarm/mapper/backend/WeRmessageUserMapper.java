package xn.pigfarm.mapper.backend;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.backend.WeRmessageUserModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2017-7-6 18:10:54
 */
public interface WeRmessageUserMapper extends IBaseDataMapper<WeRmessageUserModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    WeRmessageUserModel searchById( @Param("rowId") long rowId );

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<WeRmessageUserModel> searchToList();
    
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
