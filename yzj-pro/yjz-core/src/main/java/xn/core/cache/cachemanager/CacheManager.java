package xn.core.cache.cachemanager;

import xn.core.exception.CacheBusiException;
import xn.core.exception.Thrower;

/**
 * @Description: 缓存管理类
 * @author zhangjs5
 * @date 2016年5月4日 上午10:04:53
 */
public class CacheManager {

    public static <T extends AbstractReadOnlyCache> T getReadOnlyCache(Class<T> clazz) throws Exception {

        T t = (T) CacheFactory.getReadOnlyCache(clazz);

        if (t == null) {
            Thrower.throwException(CacheBusiException.CONFIG_TABLE_CAHCE_ERROR);
        }
        return t;
    }
}
