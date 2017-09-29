package xn.core.mapper.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:Mapper的基类
 * @author Zhangjc
 * @date 2016年4月25日 上午10:01:37
 */
public interface IMapper<T> {

    /**
     * @Description: 直接操作的sql
     * @author Zhangjc
     * @return
     */
    List<T> operSql(Map<String, String> map);

    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<T> searchToList(long farmId);

    /**
     * @Description: 根据条件查询有效数据
     * @author Zhangjc
     * @return
     */
    List<T> searchListByCon(Map<String, String> map);

    /**
     * @Description: 根据条件查询所有数据 DELETED_FLAG='0' AND STATUS = '1'
     * @author Zhangjc
     * @return
     */
    List<T> searchAllListByCon(Map<String, String> map);

    /**
     * @Description: 查询所有数据（包括 farmId companyId deletedFlag sortNbr originFlag originApp status）
     * @author Zhangjc
     * @return
     */
    List<T> searchAll(Map<String, String> map);

    /**
     * @Description: 查询
     * @author Zhangjc
     * @return
     */
    List<T> searchToList();

    /**
     * @Description: 根据ID查Model
     * @author Zhangjc
     * @param rowId
     * @return
     */
    T searchById(long rowId);

    /**
     * @Description: 新增
     * @author Zhangjc
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * @Description: 批量新增
     * @author Zhangjc
     * @param record
     * @return
     */
    int inserts(@Param("records") List<T> records);

    /**
     * @Description: 修改
     * @author Zhangjc
     * @param record
     * @return
     */
    int update(T record);

    /**
     * @Description: 批量修改
     * @author Zhangjc
     * @param record
     * @return
     */
    int updates(@Param("records") List<T> records);

    /**
     * @Description: 删除
     * @author Zhangjc
     * @param id
     * @return
     */
    int delete(long id);

    /**
     * @Description: 批量删除
     * @author Zhangjc
     * @param ids
     * @return
     */
    int deletes(@Param("ids") long[] ids);

    /**
     * @Description:根据条件删除
     * @author Zhangjc
     * @param records
     * @return
     */
    int deletesByCon(@Param("records") List<Map<String, Object>> records, @Param("farmId") long farmId);

    /**
     * @Description:根据多个条件删除
     * @author Zhangjc
     * @param records
     * @return
     */
    int deletesByCons(@Param("records") List<String> records);
}
