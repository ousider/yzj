package xn.hana.mapper.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import xn.hana.model.common.MTC_ITFC;

/**
 * @Description:HANA公共Mapper
 * @author THL
 * @date 2017年3月9日 上午10:33:36
 */
public interface IHanaParamMapper {

    /**
     * @Description: 根据sql获取数据
     * @author THL
     * @param map
     * @return
     */
    public List<Map<String, Object>> getObjectInfos(Map<String, String> map);

    /**
     * @Description: 新增单条记录
     * @author Zhangjc
     * @param record
     * @return
     */
    int insert(MTC_ITFC record);

    /**
     * @Description: 批量插入中间表
     * @author THL
     * @param record
     * @return
     */
    int inserts(@Param("records") List<MTC_ITFC> records);

}
