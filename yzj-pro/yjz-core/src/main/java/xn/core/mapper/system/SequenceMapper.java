package xn.core.mapper.system;

import xn.core.mapper.base.IMapper;
import xn.core.model.system.SequenceModel;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-8-18 17:33:46
 */
public interface SequenceMapper extends IMapper<SequenceModel> {
    /**
     * @Description: 获取序列号
     * @author zhangjs
     * @param seqName
     * @return
     * @throws Exception
     */
    public long getSeq(String seqName) throws Exception;
}
