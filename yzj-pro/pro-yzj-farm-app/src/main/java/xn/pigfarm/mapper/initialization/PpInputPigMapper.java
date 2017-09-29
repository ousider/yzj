package xn.pigfarm.mapper.initialization;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.initialization.PpInputPigModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-8-24 14:42:54
 */
public interface PpInputPigMapper extends IMapper<PpInputPigModel> {

    /**
     * @Description: 批量改变导入表状态
     * @author zhangjs
     * @param updateStatus
     * @param records
     * @return
     */
    public int batchUpdateStatus(@Param("updateStatus") String updateStatus, @Param("records") List<PpInputPigModel> records);

    /**
     * @Description: 改变导入表状态
     * @author zhangjs
     * @param updateStatus
     * @param pigModel
     * @return
     */
    public int updateStatus(@Param("updateStatus") String updateStatus, @Param("pigModel") PpInputPigModel pigModel);
    
}
