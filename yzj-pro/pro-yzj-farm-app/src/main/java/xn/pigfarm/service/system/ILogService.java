package xn.pigfarm.service.system;

/**
 * @Description: 记录日志
 * @author zhangjs
 * @date 2016年10月11日 上午9:25:24
 */
public interface ILogService {

    /**
     * @Description: 记录用户访问请求的URL
     * @author zhangjs
     * @param id
     * @param url
     * @param name
     * @throws Exception
     */
    void editActionUrl(Long id, String url, String name) throws Exception;

}
