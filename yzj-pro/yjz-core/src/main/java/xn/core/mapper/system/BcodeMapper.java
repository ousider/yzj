package xn.core.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import xn.core.mapper.base.IMapper;
import xn.core.model.system.BcodeModel;
import xn.core.model.system.BcodeView;

/**
 * @Description:系统生成
 * @author 系统生成
 * @date :2016-5-9 19:03:44
 */
public interface BcodeMapper extends IMapper<BcodeModel> {

    List<BcodeView> searchAllToPage(long farmId) throws Exception;

    List<BcodeView> searchByConToPage(@Param("farmId") long farmId, @Param("bcodeView") BcodeView bcodeView) throws Exception;

    /**
     * @Description: 更新
     * @author THL
     * @param record
     * @return
     */
    int updateByBcodeTypeId(BcodeModel record);
}
