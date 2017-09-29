package xn.core.mapper.system;

import java.util.List;
import java.util.Map;
import xn.core.mapper.base.IBaseDataMapper;
import org.apache.ibatis.annotations.Param;

import xn.core.model.system.IpAddressesModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-10-24 9:38:18
 */
public interface IpAddressesMapper extends IBaseDataMapper<IpAddressesModel> {
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    IpAddressesModel searchById( @Param("ip")String ip);

    
    /**
     * @Description: 查询
     * @author 系统生成
     * @return
     */
    List<IpAddressesModel> searchToList();
    
    /**
     * @Description: 删除
     * @author 系统生成
     * @param id
     * @return
     */
    int delete( @Param("ip")String ip);

    /**
     * @Description: 批量删除
     * @author 系统生成
     * @param ids
     * @return
     */
    int deletes(@Param("ids")List<String> ip);
    
}
