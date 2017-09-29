package xn.pigfarm.mapper.portal;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.portal.ParityDistributionModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-7-26 10:08:36
 */
public interface ParityDistributionMapper extends IMapper<ParityDistributionModel> {
  
    public Long getMaxVersion();

    public void upStatus(@Param("ids") long ids[]);
}
