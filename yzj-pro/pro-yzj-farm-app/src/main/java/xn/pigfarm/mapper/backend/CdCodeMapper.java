package xn.pigfarm.mapper.backend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.pigfarm.model.backend.CdCodeListModel;


/**
 * @Description: CdCode Mapper类
 * @author Zhangjc
 * @date 2016年4月22日 下午2:23:13
 */
public interface CdCodeMapper extends IMapper<CdCodeListModel> {
	
    /**
     * @Description: 根据TypeId删除
     * @author Zhangjc
     * @param ids
     * @throws Exception
     */
    public void deleteCodeListByTypeId(@Param("ids") long[] ids)throws Exception;
	
    /**
     * @Description: 根据TypeId查询
     * @author Zhangjc
     * @param typeId
     * @return
     * @throws Exception
     */
    // public List<CdCodeListModel> searchToListByTypeId(@Param("typeId") long typeId) throws Exception;
	
    /**
     * @Description: 根据TypeId查询
     * @author Zhangjc
     * @param typeId
     * @return
     * @throws Exception
     */
    public List<CdCodeListModel> searchDetailToList(@Param("typeId") long typeId) throws Exception;
	
    /**
     * @Description: 根据TypeCode查询
     * @author Zhangjc
     * @param typeCode
     * @param linkValue
     * @return
     * @throws Exception
     */
	public List<CdCodeListModel> searchByTypeCode(@Param("typeCode")String typeCode,@Param("linkValue")String linkValue)throws Exception;
	
	
	/***
	 * 根据公司id,typeCode查询
	 * @param typeCode
	 * @param farmId
	 * @return
	 */
	public List<CdCodeListModel>  searchRoleType(@Param("typeCode") String typeCode,@Param("farmId") long farmId);
}
