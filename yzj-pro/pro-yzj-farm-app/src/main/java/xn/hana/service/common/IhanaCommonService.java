package xn.hana.service.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xn.hana.model.common.MTC_ITFC;

public interface IhanaCommonService {
    public List<Map<String, Object>> searchData(HttpServletRequest request) throws Exception;

    public int insertMTC_ITFC(MTC_ITFC mtcITFC) throws Exception;

    public int insertsMTC_ITFC(List<MTC_ITFC> list) throws Exception;

}
