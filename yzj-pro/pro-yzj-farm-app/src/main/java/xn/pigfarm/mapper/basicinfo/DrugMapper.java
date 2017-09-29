package xn.pigfarm.mapper.basicinfo;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.basicinfo.DrugModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-8-17 10:43:27
 */
public interface DrugMapper extends IMapper<DrugModel> {

    /**
     * @Description: 供应链管理批量更新到所有猪场
     * @author THL
     * @param record
     * @return
     */
    int updateToFarms(DrugModel record);

}
