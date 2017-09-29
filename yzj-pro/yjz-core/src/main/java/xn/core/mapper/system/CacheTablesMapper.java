package xn.core.mapper.system;

import xn.core.mapper.base.IMapper;
import xn.core.model.system.CacheTablesModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-9 20:09:45
 */
public interface CacheTablesMapper extends IMapper<CacheTablesModel> {

    /**
     * @Description: 根据主键查Model
     * @author Zhangjc
     * @param rowId
     * @return
     */
    CacheTablesModel searchById(String tableName);
}
