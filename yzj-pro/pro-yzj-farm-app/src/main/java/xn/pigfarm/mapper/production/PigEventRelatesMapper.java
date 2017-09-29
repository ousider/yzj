package xn.pigfarm.mapper.production;

import java.util.Map;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.production.PigEventRelatesModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-8-1 13:52:34
 */
public interface PigEventRelatesMapper extends IMapper<PigEventRelatesModel> {
  
    /**
     * @Description:根据条件删除
     * @author THL
     * @param map
     * @return
     */
    int deletesByCon2(Map<String, Object> map);

}
