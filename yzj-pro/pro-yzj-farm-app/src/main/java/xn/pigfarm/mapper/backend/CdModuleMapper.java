package xn.pigfarm.mapper.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.core.model.system.MenuView;
import xn.pigfarm.model.backend.CdModuleModel;

/**
 * @Description: 系统菜单对应的Mapper
 * @author Zhangjc
 * @date 2016年4月24日 下午8:56:51
 */
public interface CdModuleMapper extends IMapper<CdModuleModel>{

    /**
     * @Description: 查询菜单
     * @author Zhangjc
     * @param companyId
     * @param employeeId
     * @return
     * @throws Exception
     */
    public List<CdModuleModel> searchMenuByUserId(@Param("companyId") long companyId, @Param("employeeId") long employeeId) throws Exception;

    /**
     * @Description: 查找子节点
     * @author Cress
     * @param templateId
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<CdModuleModel> searchModuleChildren(@Param("templateId") long templateId, @Param("parentId") long parentId) throws Exception;

    /**
     * @Description: 根据模板Id获取建模模板
     * @author Cress
     * @param templateId
     * @return
     * @throws Exception
     */
    public List<MenuView> searchModuleByTemplateId(@Param("templateId") long templateId) throws Exception;
    
    /**
     * @Description:根据Id排除菜单
     * @author Cress
     * @param templateId
     * @return
     * @throws Exception
     */
    public List<CdModuleModel> searchModuleExcludeById(@Param("ids") long[] ids) throws Exception;

}

