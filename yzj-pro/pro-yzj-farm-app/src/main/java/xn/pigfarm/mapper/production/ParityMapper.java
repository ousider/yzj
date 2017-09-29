package xn.pigfarm.mapper.production;

import java.util.Map;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.production.ParityModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-6-2 14:22:51
 */
public interface ParityMapper extends IMapper<ParityModel> {
  
    /**
     * @Description:根据条件删除
     * @author THL
     * @param map
     * @return
     */
    int deletesByCon2(Map<String, Object> map);

}
