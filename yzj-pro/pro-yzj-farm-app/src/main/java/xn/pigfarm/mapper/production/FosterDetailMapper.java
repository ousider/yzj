package xn.pigfarm.mapper.production;

import java.util.Map;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.production.FosterDetailModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-7-27 14:18:10
 */
public interface FosterDetailMapper extends IMapper<FosterDetailModel> {
  
    /**
     * @Description:根据条件删除
     * @author THL
     * @param map
     * @return
     */
    int deletesByCon2(Map<String, Object> map);

}
