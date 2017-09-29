package xn.pigfarm.service.production;

import java.util.List;
import java.util.Map;

/**
 * @Description: 事件查询
 * @author Zhangjc
 * @date 2016年6月2日 下午2:19:50
 */
public interface IBillViewService {

    /**
     * @Description:事件查询 查询明细
     * @author Zhangjc
     * @prarm cancelBillId
     * @param eventCode
     * @param billId
     * @param oldEventCode
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDetailBillToList(Long cancelBillId, String eventCode, Long billId, String oldEventCode) throws Exception;

    /**
     * @Description: 查询获得撤销了什么单据事件
     * @author THL
     * @param billId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchWhichEventCancel(Long billId) throws Exception;

}
