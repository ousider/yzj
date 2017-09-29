package xn.core.mapper.portal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IBaseDataMapper;
import xn.core.model.portal.UserModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-11-18 14:10:20
 */
public interface UserMapper extends IBaseDataMapper<UserModel> {
    
    
    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<UserModel> searchToList();
    
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
    
    /**
     * @Description: 根据ID查Model
     * @author Zhangjc
     * @param rowId
     * @return
     */
    UserModel searchById(long rowId);

}
