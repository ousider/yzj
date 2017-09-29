package xn.core.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.core.model.system.QuickMenusModel;
import xn.core.model.system.QuickMenusView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-6-29 14:02:09
 */
public interface QuickMenusMapper extends IMapper<QuickMenusModel> {
  
    /**
     * @Description: 根据用户ID查询快捷菜单
     * @author 系统生成
     * @return QuickMenusView;
     */
    public List<QuickMenusView> searchQuickMenusByUserId(@Param("userId") Long userId);
}
