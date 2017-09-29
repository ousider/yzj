package xn.core.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.model.system.MenuView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-17 15:08:10
 */
public interface MenuMapper  {
  
    /**
     * @Description: 根据用户名查询菜单
     * @author 系统生成
     * @return MenuView;
     */
    public List<MenuView> searchMenuByUserId(@Param("employeeId") long employeeId, @Param("farmId") long companyId,
            @Param("menuType") String menuType);

    /**
     * @Description: 根据模板ID查询菜单
     * @author Zhangjc
     * @param templateId
     * @return
     */
    public List<MenuView> searchMenuByTemplateId(@Param("templateId") long templateId, @Param("menuType") String menuType);
}
