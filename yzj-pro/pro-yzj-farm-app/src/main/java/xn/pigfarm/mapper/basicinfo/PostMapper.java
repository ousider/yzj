package xn.pigfarm.mapper.basicinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.basicinfo.PostModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-9 19:52:28
 */
public interface PostMapper extends IMapper<PostModel> {
 
	/**
	 * 根据部门id查询所属岗位
	 * @param deptId
	 * @return
	 */
	public List<PostModel> searchByDeptId(@Param("deptId") long deptId);
}
