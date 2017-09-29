package xn.pigfarm.mapper.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.backend.ButtonView;
import xn.pigfarm.model.backend.MenusModel;
import xn.pigfarm.model.backend.MenusView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-9 19:03:50
 */
public interface MenusMapper extends IMapper<MenusModel> {
  
	
	/**
	 * 根据 公司id 和角色id查询菜单
	 * @param farmId
	 * @return
	 */
	List<MenusView> searchMenuView(@Param("farmId") long farmId,@Param("roleId") long roleId);
		
	/**
	 * 根据 模板id 查询菜单
	 * @param modelId
	 * @return
	 */
	List<MenusView> searchMenuByTempId(@Param("templateId")long modelId);
	
	/**
	 * 根据 公司id 和角色id查询按钮
	 * @param farmId
	 * @param roleId
	 * @return
	 */
	List<ButtonView> searchMenuByBtn(@Param("farmId") long farmId,@Param("roleId") long roleId);
	
}
