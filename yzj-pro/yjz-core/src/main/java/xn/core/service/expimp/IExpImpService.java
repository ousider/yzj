package xn.core.service.expimp;

import java.util.List;
import java.util.Map;

/**
 * @Description: 查询隐藏区域的值
 * @author zhangjs
 * @date 2016年7月27日 下午4:33:10
 */
public interface IExpImpService {

    /**
     * @Description: 查询隐藏区域值
     * @author 程彬
     * @param map
     * @return
     * @throws Throwable
     */
    public Map<String, List<Map<String, String>>> getHiddenData(Map<String, Object> map) throws Throwable;
}
