package xn.pigfarm.controller.portal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.model.portal.ParityDistributionModel;
import xn.pigfarm.service.backend.IBackEndService;
import xn.pigfarm.service.portal.IPortalService;

@Controller
@RequestMapping("/portal")
public class PortalController extends BaseController {

    @Autowired
    private IBackEndService backEndService;

    @Autowired
    private IPortalService portalService;

    /**
     * @Description: 母猪胎次查询不分页
     * @author wch
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchParityToList")
    @ResponseBody
    public Map<String, Object> searchParityToList(HttpServletRequest request) throws Exception {

        return getReturnMap(portalService.searchParityToList());
    }

    /**
     * @Description: 修改母猪胎次
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/editParity")
    @ResponseBody
    public Map<String, Object> editParity(HttpServletRequest request) throws Exception {
        List<ParityDistributionModel> parityList = getModelList(getDetialListStr(), ParityDistributionModel.class);
        portalService.editParity(parityList, getIds());
        basicRefresh();
        return getReturnMap();
    }
    
    @RequestMapping("/deleteParity")
    @ResponseBody
    public Map<String, Object> deleteParity(HttpServletRequest request) throws Exception {
        portalService.deleteParity(getIds());
        basicRefresh();
        return getReturnMap();
    }
    
    @RequestMapping("/editNewParity")
    @ResponseBody
    public Map<String, Object> editNewParity(HttpServletRequest request) throws Exception {
        List<ParityDistributionModel> parityList = getModelList(getDetialListStr(), ParityDistributionModel.class);

        portalService.editNewParity(parityList, getIds());
        basicRefresh();
        return getReturnMap();
    }
    
    /**
     * @Description: 生产参数,生产目标
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchIndicator")
    @ResponseBody
    public Map<String, Object> searchIndicator(HttpServletRequest request) throws Exception {
        
        Map<String, Object> mapResult = new HashMap<>();
        List<Map<String, Object>> searchIndicator = backEndService.searchIndicatorCustToList(getMap());
        Iterator<Map<String, Object>> iterator = searchIndicator.iterator();

        while (iterator.hasNext()) {

            Map<String, Object> next = iterator.next();
            switch (next.get("businessCode").toString()) {
            case "Z001":
                mapResult.put("Z001", next);
                break;
            case "Z002":
                mapResult.put("Z002", next);
                break;
            case "Z004":
                mapResult.put("Z004", next);
                break;
            case "Z013":
                mapResult.put("Z013", next);
                break;
            case "Z022":
                mapResult.put("Z022", next);
                break;
            case "Z038":
                mapResult.put("Z038", next);
                break;
            case "Z041":
                mapResult.put("Z041", next);
                break;
            case "Z045":
                mapResult.put("Z045", next);
                break;
            case "Z054":
                mapResult.put("Z054", next);
                break;
            case "Z055":
                mapResult.put("Z055", next);
                break;
            case "Z056":
                mapResult.put("Z056", next);
                break;
            default:
                break;
            }
        }

        return getReturnMap(mapResult);

    };

    /**
     * @Description: 存栏规模
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchAmountSize")
    @ResponseBody
    public Map<String, Object> searchAmountSize(HttpServletRequest request) throws Exception {
        String dateType = getString("dateType");
        Long farmId = getFarmId();
        List<Object> amountSize = portalService.searchAmountSize(dateType, farmId);

        return getReturnMap(amountSize);

    };

    /**
     * @Description: 生产情况 -- 生产分析
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchProductionByDate")
    @ResponseBody
    public Map<String, Object> searchProductionByDate(HttpServletRequest request) throws Exception {
        // dateType = "week or year"
        String dateType = getString("dateType");

        Long farmId = getFarmId();
        List<Object> productionByDate = portalService.searchProductionByDate(dateType, farmId);

        return getReturnMap(productionByDate);
    }

    /**
     * @Description: 死亡分析
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchDieByDate")
    @ResponseBody
    public Map<String, Object> searchDieByDate(HttpServletRequest request) throws Exception {
        // dateType = "week or year"
        String dateType = getString("dateType");

        Long farmId = getFarmId();
        List<Object> productionByDate = portalService.searchDieByDate(dateType, farmId);

        return getReturnMap(productionByDate);
    }

    /**
     * @Description: 胎次分布
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchParityByDate")
    @ResponseBody
    public Map<String, Object> searchParityByDate(HttpServletRequest request) throws Exception {
        Long farmId = getLong("farmId");
        if (farmId == null) {
            farmId = getFarmId();
        }
        Long farmId2 = get10000FarmId();
        String pigNum = getString("pigNum");
        String isGroup = getString("isGroup");
        String companyMark = getString("companyMark");
        if (isGroup == null) {
            isGroup = "false";
        }
        List<Object> productionByDate = portalService.searchParityByDate(farmId, farmId2, pigNum, isGroup, companyMark);

        return getReturnMap(productionByDate);
    }

    /**
     * @Description: 母猪批次蓝图
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSwineryClassNumByDate")
    @ResponseBody
    public Map<String, Object> searchSwineryClassNumByDate(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        // List<Map<String, Object>> searchSwineryClassNumByDate = portalService.searchSwineryClassNumBySwineryId(farmId);
        Map<String, Object> searchSwineryClassNumByDate = portalService.searchSwineryClassNumByDate(farmId);
        return getReturnMap(searchSwineryClassNumByDate);
    }

    /**
     * @Description: 母猪群分娩异常明细 查询
     * @author 程彬
     * @param request
     * @return
     */
    @RequestMapping("/searchDeliveryExceptionDetail")
    @ResponseBody
    public Map<String, Object> searchDeliveryExceptionDetail(HttpServletRequest request) {

        Long farmId = getFarmId();
        Long swineryId = getLong("swineryId");
        List<Map<String, Object>> searchDeliveryExceptionDetail = portalService.searchDeliveryExceptionDetail(farmId, swineryId);
        return getReturnMap(searchDeliveryExceptionDetail);
    }

    @RequestMapping("/searchSwineryPigExcepitionBySwineryId")
    @ResponseBody
    public Map<String, Object> searchSwineryPigExcepitionBySwineryId(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<List<List<Map<String, Object>>>> searchSwineryPigExcepition = portalService.searchSwineryPigExcepitionBySwineryId(farmId);
        return getReturnMap(searchSwineryPigExcepition);
    }

    /***
     * @Description: 生产异常提醒 - 配种异常
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/productionAbnormalByBreed")
    @ResponseBody
    public Map<String, Object> productionAbnormalByBreed(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<Map<String, Object>> productionAbnormalByBreed = portalService.productionAbnormalByBreed(farmId);
        return getReturnMap(productionAbnormalByBreed);
    }
    
    /***
     * @Description: 生产异常提醒 - 上产房异常
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/productionAbnormalByLaborHouse")
    @ResponseBody
    public Map<String, Object> productionAbnormalByLaborHouse(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<Map<String, Object>> productionAbnormalByLaborHouse = portalService.productionAbnormalByLaborHouse(farmId);
        return getReturnMap(productionAbnormalByLaborHouse);
    }
    
    /***
     * @Description: 生产异常提醒 - 待分娩异常
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/productionAbnormalByDelivery")
    @ResponseBody
    public Map<String, Object> productionAbnormalByDelivery(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<Map<String, Object>> productionAbnormalByDelivery = portalService.productionAbnormalByDelivery(farmId);
        return getReturnMap(productionAbnormalByDelivery);
    }
    
    /***
     * @Description: 生产异常提醒 - 待断奶异常
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/productionAbnormalByWean")
    @ResponseBody
    public Map<String, Object> productionAbnormalByWean(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<Map<String, Object>> productionAbnormalByWean = portalService.productionAbnormalByWean(farmId);
        return getReturnMap(productionAbnormalByWean);
    }
    
    /***
     * @Description: 生产异常提醒 - 待出栏异常
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/productionAbnormalBySell")
    @ResponseBody
    public Map<String, Object> productionAbnormalBySell(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<Map<String, Object>> productionAbnormalBySell = portalService.productionAbnormalBySell(farmId);
        return getReturnMap(productionAbnormalBySell);
    }

    /***
     * @Description: 生产异常提醒 - 待出栏异常
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/productionAbnormalByObsolete")
    @ResponseBody
    public Map<String, Object> productionAbnormalByObsolete(HttpServletRequest request) throws Exception {

        Long farmId = getFarmId();
        List<Map<String, Object>> productionAbnormalByObsolete = portalService.productionAbnormalByObsolete(farmId);
        return getReturnMap(productionAbnormalByObsolete);
    }

    /**
     * @description 综合指标
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSyntheticalIndicatorByDate")
    @ResponseBody
    public Map<String, Object> searchSyntheticalIndicatorByDate(HttpServletRequest request) throws Exception {
    	Map<String, Object> syntheticalIndicatorByDate = portalService.searchSyntheticalIndicatorByDate(getString("dateType"));
        return getReturnMap(syntheticalIndicatorByDate);
    }

    /**
     * @Description 栏舍周转
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchChangePigpenAndHouseByDateType")
    @ResponseBody
    public Map<String, Object> searchChangePigpenAndHouseByDateType(HttpServletRequest request) throws Exception {
    	String dateTyp = getString("dateType");
        Map<String, List<Map<String, Object>>> changePigpenAndHouseByPigType = portalService.searchChangePigpenAndHouseByDateType(dateTyp);
        // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "功能开发中");
        return getReturnMap(changePigpenAndHouseByPigType);
    }
    
    // ************************************ 销售门户 ***************************************************
    /**
     * @Description: 集团销售实时统计
     * @author 程彬
     * @param request
     * @return
     */
    @RequestMapping("/searchGroupSsalesOfRealTimeStatistics")
    @ResponseBody
    public Map<String,Object> searchGroupSsalesOfRealTimeStatistics(HttpServletRequest request) {
        Map<String, Object> searchGroupSsalesOfRealTimeStatistics = portalService.searchGroupSsalesOfRealTimeStatistics();
        return getReturnMap(searchGroupSsalesOfRealTimeStatistics);
    }
    
    /**
     * @Description: 集团销售实时统计--客户地址
     * @author 程彬
     * @param request
     * @return
     */
    @RequestMapping("/searchCompanyAddreed")
    @ResponseBody
    public Map<String,Object> searchCompanyAddreed(){
        List<Map<String, Object>> searchGroupSsalesOfRealTimeStatistics = portalService.searchCompanyAddreed();
        return getReturnMap(searchGroupSsalesOfRealTimeStatistics);
    }
    
    /**
     * @Description: 集团_场区商品猪上市销售情况
     * @author 程彬
     * @return
     */
    @RequestMapping("/searchFarmSalePig")
    @ResponseBody
    public Map<String,Object> searchFarmSalePig(){
        List<Map<String, Object>> searchGroupSsalesOfRealTimeStatistics = portalService.searchFarmSalePig();
        return getReturnMap(searchGroupSsalesOfRealTimeStatistics);
    }
    
    /**
     * @Description: 残次猪销售变化
     * @author 程彬
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchImperfectPigSaleChange")
    @ResponseBody
    public Map<String,Object> searchImperfectSalePig() throws Exception{
        List<Map<String, Object>> searchImperfectPigSaleChange = portalService.searchImperfectPigSaleChange();
        return getReturnMap(searchImperfectPigSaleChange);
    }
    
    
    /**
     * @Description: 客户分析
     * @author 程彬
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchCustomerAnalysis")
    @ResponseBody
    public Map<String,Object> searchCustomerAnalysis() throws Exception{
        Map<String, Object> searchCustomerAnalysis = portalService.searchCustomerAnalysis();
        return getReturnMap(searchCustomerAnalysis);
    }
    
    /**
     * @Description: 查询周次信息
     * @author 程彬
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchWeekInYear")
    @ResponseBody
    public Map<String,Object> searchWeekInYear() throws Exception{
        List<Map<String, Object>> searchWeekInYear = portalService.searchWeekInYear(getMap());
        return getReturnMap(searchWeekInYear);
    }
    
}
