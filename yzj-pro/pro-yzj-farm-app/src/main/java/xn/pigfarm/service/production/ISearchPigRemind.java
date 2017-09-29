package xn.pigfarm.service.production;

import java.util.Map;

public interface ISearchPigRemind {
    
    /**
     *  @Description: 猪只提醒
     * @param eventName
     * @param earBrand
     * @return
     * @throws Exception
     */
    public String searchPigRemind(String eventName, String earBrand,String pigIds) throws Exception ;
    
    /**
     * @Description: 查询猪只是否存在或者离场
     * @author 程彬
     * @param eventName
     * @param earBrand
     * @return
     * @throws Exception
     */
    public String searchPigExists(Map<String, String> map) throws Exception;

    /**
     * @Description: 查询猪只找不到提醒
     * @author 程彬
     * @param eventName
     * @param earBrand
     * @return
     * @throws Exception
     */
    public String searchPigRemind(Map<String, String> map) throws Exception;
}
