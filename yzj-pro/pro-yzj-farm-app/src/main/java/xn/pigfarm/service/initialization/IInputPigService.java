package xn.pigfarm.service.initialization;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.initialization.PpInputPigModel;

/**
 * @Description: 初始化猪场
 * @author zhangjs
 * @date 2016年8月16日 上午11:49:24
 */
public interface IInputPigService {


    /**
     * @Description: 查询所有需要初始化猪只
     * @author zhangjs
     * @return
     * @throws Exception
     */
    public List<PpInputPigModel> editAndSearchInputPig() throws Exception;

    /**
     * @Description: 修改猪只导入表状态
     * @author zhangjs
     * @param list
     * @param status
     * @return
     * @throws Exception
     */
    public int editInputPigStatus(List<PpInputPigModel> list, String status) throws Exception;

    /**
     * @Description: 初始化导入猪只(单头母猪)
     * @author zhangjs
     * @param pigList
     * @throws Exception
     */
    public void executeInputSow(List<PpInputPigModel> pigList) throws Exception;

    /**
     * @Description: 初始化导入猪只(单头公猪猪)
     * @author zhangjs
     * @param pigList
     * @throws Exception
     */
    public void executeInputBoar(PpInputPigModel ppInputPigModel) throws Exception;

    /**
     * @Description: 查询初始化猪只
     * @author zhangjs
     * @param status
     * @return
     * @throws Exception
     */
    public BasePageInfo searchInputPig(String status) throws Exception;

    /**
     * @Description: 删除导出未执行的初始化猪只
     * @author zhangjs
     * @param ids
     * @throws Exception
     */
    public void deleteInputPigs(long[] ids) throws Exception;

    /**
     * @Description: 初始化肉猪
     * @author Administrator
     */
    public void excuteInputPorker(List<Map> uploadData) throws Exception;

}
