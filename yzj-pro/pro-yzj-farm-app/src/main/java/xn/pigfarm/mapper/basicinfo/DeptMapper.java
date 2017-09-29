package xn.pigfarm.mapper.basicinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.basicinfo.DeptModel;
import xn.pigfarm.model.basicinfo.DeptView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-9 19:52:29
 */
public interface DeptMapper extends IMapper<DeptModel> {
 
	/**
	 * 根据公司Id查询所属部门
	 * @param farmId
	 * @return
	 */
	public List<DeptView> searchByFarmId(@Param("farmId") long farmId);

}
