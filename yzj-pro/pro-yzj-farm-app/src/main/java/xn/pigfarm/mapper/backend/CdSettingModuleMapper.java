package xn.pigfarm.mapper.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.backend.CdSettingModuleModel;

/**
 * @Description: .
 * @author:li.zhou
 * @date:2016-4-26
 */
public interface CdSettingModuleMapper extends IMapper<CdSettingModuleModel> {
  
	/**
	 * 根据公司编号查询
	 * @param CompanyId
	 * @return
	 */
    List<CdSettingModuleModel> searchCdMsettingModuleToList(@Param("farmId") long farmId) throws Exception;
}
