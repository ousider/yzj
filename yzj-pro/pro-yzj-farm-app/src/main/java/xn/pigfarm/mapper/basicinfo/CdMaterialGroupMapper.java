

package xn.pigfarm.mapper.basicinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.basicinfo.CdMaterialGroupModel;


/**
 * @Description: 表 cd_m_material_group
 * @author guang
 * @date 2016年5月5日 上午9:33:56
 */
public interface CdMaterialGroupMapper extends IMapper<CdMaterialGroupModel> {
	
	
    public void deleteByKey(@Param("ids") long[] ids) throws Exception;

    public List<CdMaterialGroupModel> searchMaterialGroupToList(@Param("farmId") long farmId, @Param("materialType") String materialType)
            throws Exception;
}
