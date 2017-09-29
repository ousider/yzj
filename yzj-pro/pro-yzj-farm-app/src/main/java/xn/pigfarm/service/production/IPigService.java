package xn.pigfarm.service.production;

import java.util.Map;

import xn.core.util.page.BasePageInfo;

/**
 * @Description: 猪只
 * @author Zhangjc
 * @date 2016年5月27日 下午1:39:46
 */
public interface IPigService {

    /**
     * @Description: 查询猪只历史
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public BasePageInfo searchPigHisToPage(Map<String, Object> map) throws Exception;

}
