package xn.core.service;

import java.util.List;

import xn.core.util.page.BasePageInfo;

/**
 * @Description: 服务通用接口
 * @author lizhou
 * @date 2016年4月14日 下午12:56:14
 */
public interface IService<T> {
	
    /**
     * @Description: 新增，修改方法
     * @author lizhou
     * @param entity
     * @param jsonString
     * @throws Exception
     */
    void editByModel(T entity, String jsonString) throws Exception;
	
    /**
     * @Description: 删除方法
     * @author lizhou
     * @param ids
     * @throws Exception
     */
    void deletes(long[] ids) throws Exception;

    /**
     * @Description: 根据ID查询结果
     * @author lizhou
     * @param rowId
     * @return
     * @throws Exception
     */
    T searchById(long rowId) throws Exception;

    /**
     * @Description: 查询列表
     * @author lizhou
     * @return
     * @throws Exception
     */
    List<T> searchToList() throws Exception;

    /**
     * @Description: 分页查询
     * @author Zhangjc
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    BasePageInfo searchToPage() throws Exception;
	
	// TODO 其他...

}
