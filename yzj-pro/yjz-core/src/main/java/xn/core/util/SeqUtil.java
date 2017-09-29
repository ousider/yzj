package xn.core.util;

import xn.core.data.enums.SeqEnum;
import xn.core.service.IParamService;

/**
 * @Description: 获取序列
 * @author zhangjs
 * @date 2016年8月18日 下午5:32:24
 */
public class SeqUtil {

    /**
     * @Description: 获取IParamService
     * @author Zhangjc
     * @return
     */
    public static IParamService getParamService() {
        return SpringContextUtil.getBean("ParamService", IParamService.class);
    }

    /**
     * @Description: 获取序列号
     * @author zhangjs
     * @param seqName
     * @return
     * @throws Exception
     */
    public static long getSeq(SeqEnum seqEnum) throws Exception {
        return getParamService().getSeq(seqEnum.getTableName());
    }
}
