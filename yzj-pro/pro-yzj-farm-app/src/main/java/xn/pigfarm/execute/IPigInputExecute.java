package xn.pigfarm.execute;

import java.util.Map;

/**
 * @Description: 处理工具类
 * @author zhangjs
 * @date 2016年8月24日 上午11:31:08
 */
public interface IPigInputExecute {

    /**
     * @Description: 初始化猪只
     * @author zhangjs
     * @return
     * @throws Exception
     */
    public Map<String, Object> inputPig() throws Exception;

    /**
     * @Description: 肉猪初始化
     * @author Administrator
     * @return
     * @throws Exception
     */
    public Map<String, Object> inputPork(String detailList, String xmlName) throws Exception;
    
    /**
     * @Description: 肉猪主数据初始化
     * @author 程彬
     * @param detailList
     * @param xmlName
     * @return
     * @throws Exception
     */
    public Map<String, Object> inputMaterialPork(Map<String, Object> map, String materialType) throws Exception;

}
