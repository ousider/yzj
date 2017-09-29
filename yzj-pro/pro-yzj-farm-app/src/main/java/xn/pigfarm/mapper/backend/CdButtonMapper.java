

package xn.pigfarm.mapper.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.backend.CdButtonModel;

/**
 * @Description: 系统菜单按钮Mapper
 * @author Zhangjc
 * @date 2016年4月22日 下午1:35:25
 */
public interface CdButtonMapper extends IMapper<CdButtonModel> {
	
    /**
     * @Description: 根据菜单Id查询按钮
     * @author Zhangjc
     * @param moduleId
     * @return
     * @throws Exception
     */
    public List<CdButtonModel> searchDetailToList(@Param("moduleId") long moduleId)throws Exception;
}
