package xn.pigfarm.mapper.basicinfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.UserManageView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-9 19:52:27
 */
public interface EmployeeMapper extends IMapper<EmployeeModel> {

	/**
     * 根据公司id查询人员
     * 
     * @param farmId
     * @return
     */
    public List<UserManageView> searchByFarmId(@Param("farmId") long farmId);

	/**
     * 根据公司id 查询人员
     * 
     * @param farmId
     * @param roleId
     * @return
     */
	public List<UserManageView> searchEmpByRole(@Param("farmId") long farmId);

	/**
     * 根据公司id，角色id查询人员
     * 
     * @param farmId
     * @param roleId
     * @return
     */
    public List<UserManageView> searchEmpByRoleId(@Param("roleId") long roleId);

    /**
     * @Description: 查询分页和高级查询
     * @author Administrator
     * @param farmId
     * @param model
     * @return
     */
    public List<UserManageView> searchByConToPage(@Param("farmId") long farmId, @Param("employeeModel") EmployeeModel model);
}
