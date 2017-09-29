package xn.pigfarm.mapper.basicinfo;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.basicinfo.PpeModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-8-17 10:43:24
 */
public interface PpeMapper extends IMapper<PpeModel> {

    /**
     * @Description: 供应链管理批量更新到所有猪场
     * @author THL
     * @param record
     * @return
     */
    int updateToFarms(PpeModel record);

}
