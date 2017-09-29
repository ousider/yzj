package xn.pigfarm.service.production;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.production.SaleAccountModel;

/**
 * @Description: 销售结算单
 * @author Administrator
 * @date 2017年3月6日 下午4:01:14
 */
public interface IAccountService {

    // 查询销售结算单
    public BasePageInfo searchSaleAccountToPage() throws Exception;
    
    // 查询销售结算单明细
    public List<Map<String, String>> searchSaleAccountDetailToList(Long mainId, String itemType) throws Exception;

    // 查询销售结算单客户
    public List<Map<String, String>> searchCustomerSaleAccountToList() throws Exception;

    // 根据客户查询销售单据
    public List<Map<String, String>> selectSaleBillByCustomer(Map<String, Object> inputMap) throws Exception;

    // 根据客户查询销售项目
    public List<Map<String, String>> searchCusSaleItem(Long customerId, String itemType) throws Exception;

    // 保存结算单
    public void editSaleAccount(Map<String, Object> map, SaleAccountModel model, String detailListString) throws Exception;
}
