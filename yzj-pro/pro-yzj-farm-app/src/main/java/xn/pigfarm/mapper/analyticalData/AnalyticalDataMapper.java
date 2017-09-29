package xn.pigfarm.mapper.analyticalData;

import java.util.Map;

import xn.core.mapper.base.IMapper;

public interface AnalyticalDataMapper extends IMapper<Object> {

    Map<String, Object> searchPsyByDate(Map<String, String> map);
}
