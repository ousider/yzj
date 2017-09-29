

package xn.pigfarm.mapper.production;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.pigfarm.model.production.PpPigModel;


/**
 * @Description: 猪只
 * @author fangc
 * @date 2016年4月29日 下午9:06:57
 */
public interface PpPigMapper {

    public void deleteByKey(@Param("ids") long[] ids) throws Exception;
	
    public List<PpPigModel> searchPigToPage(@Param("farmId") long farmId, @Param("pigType") String pigType) throws Exception;

}
