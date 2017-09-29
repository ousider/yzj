package xn.pigfarm.controller.production;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.model.production.SaleAccountModel;
import xn.pigfarm.service.production.IAccountService;

/**
 * @Description: 销售结算单
 * @author Administrator
 * @date 2017年3月6日 下午3:59:05
 */
@Component
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private IAccountService accountService;

    // 查询销售结算单
    @RequestMapping("/searchSaleAccountToPage")
    @ResponseBody
    public Map<String, Object> searchSaleAccountToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(accountService.searchSaleAccountToPage());
    }

    // 查询销售结算单明细
    @RequestMapping("/searchSaleAccountDetailToList")
    @ResponseBody
    public Map<String, Object> searchSaleAccountDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(accountService.searchSaleAccountDetailToList(getLong("mainId"), getString("itemType")));
    }

    // 查询需要销售结算 的客户
    @RequestMapping("/searchCustomerSaleAccountToList")
    @ResponseBody
    public Map<String, Object> searchCustomerSaleAccountToList(HttpServletRequest request) throws Exception {

        return getReturnMap(accountService.searchCustomerSaleAccountToList());
    }

    // 根据客户查询销售单据
    @RequestMapping("/selectSaleBillByCustomer")
    @ResponseBody
    public Map<String, Object> selectSaleBillByCustomer(HttpServletRequest request) throws Exception {

        return getReturnMap(accountService.selectSaleBillByCustomer(getMap()));
    }

    // 根据客户查询销售项目
    @RequestMapping("/searchCusSaleItem")
    @ResponseBody
    public Map<String, Object> searchCusSaleItem(HttpServletRequest request) throws Exception {

        return getReturnMap(accountService.searchCusSaleItem(getLong("customerId"), getString("itemType")));
    }

    // 保存结算单
    @RequestMapping("/editSaleAccount")
    @ResponseBody
    public Map<String, Object> editSaleAccount(HttpServletRequest request) throws Exception {

        accountService.editSaleAccount(getMap(), getBean(SaleAccountModel.class), getDetialListStr());
        return getReturnMap();
    }
}
