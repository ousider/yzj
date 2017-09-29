
package xn.pigfarm.controller.basicinfo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.uploadFile.UploadFileUtil;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.LineModel;
import xn.pigfarm.service.basicinfo.IBasicInfoService;

/**
 * @Description: 基础信息控制层
 * @author fangc
 * @date 2016年4月22日 上午8:17:33
 */
@Component
@Controller
@RequestMapping("/basicInfo")
public class BasicInfoController extends BaseController {

    @Autowired
    private IBasicInfoService basicInfoService;

    // 查询产线
    @RequestMapping("/searchLineToList")
    @ResponseBody
    public Map<String, Object> searchLineToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchLineToList());
    }

    @RequestMapping("/searchLineToPage")
    @ResponseBody
    public Map<String, Object> searchLineToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchLineToPage());
    }

    @RequestMapping("/editLine")
    @ResponseBody
    public Map<String, Object> editLine(HttpServletRequest request) throws Exception {

        basicInfoService.editLine(getBean(LineModel.class), getDetialListStr());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/deleteLines")
    @ResponseBody
    public Map<String, Object> deleteLines(HttpServletRequest request) throws Exception {

        basicInfoService.deleteLines(getIds());
        basicRefresh();
        return getReturnMap();
    }

    // =========================猪舍
    @RequestMapping("/searchHouseToList")
    @ResponseBody
    public Map<String, Object> searchHouseToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchHouseToList(getString("changeType"), getString("houseType"), getLong("lineId"), getString(
                "eventName"), getString("saleDescribe")));
    }

    @RequestMapping("/searchHouseDeptToList")
    @ResponseBody
    public Map<String, Object> searchHouseDeptToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchHouseDeptToList());
    }

    @RequestMapping("/searchHouseToPage")
    @ResponseBody
    public Map<String, Object> searchHouseToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchHouseToPage(getMap()));
    }

    @RequestMapping("/editHouse")
    @ResponseBody
    public Map<String, Object> editHouse(HttpServletRequest request) throws Exception {

        basicInfoService.editHouse(getBean(HouseModel.class), getDetialListStr());
        cacheRefresh();
        return getReturnMap();
    }

    @RequestMapping("/deleteHouses")
    @ResponseBody
    public Map<String, Object> deleteHouses(HttpServletRequest request) throws Exception {

        basicInfoService.deleteHouses(getIds());
        basicRefresh();
        return getReturnMap();
    }

    // =========================猪栏 无须写删除和修改方法，因在编辑猪舍中已实现
    @RequestMapping("/searchPigpenToList")
    @ResponseBody
    public Map<String, Object> searchPigpenToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchPigpenToList(getLong("mainId"), getString("eventName")));
    }

    /********************************************* yangy begin 新增猪栏search方法 *********************************************/
    @RequestMapping("/searchValidPigpenToList")
    @ResponseBody
    public Map<String, Object> searchValidPigpenToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchValidPigpenToList(getString("changeType"), getLong("houseId"), getLong("lineId"), getString(
                "eventName"), getString("saleDescribe")));
    }

    /********************************************* yangy end 新增猪栏search方法 *********************************************/

    @RequestMapping("/searchPigpenToPage")
    @ResponseBody
    public Map<String, Object> searchPigpenToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchPigpenToPage());
    }

    // =========================客户，供应商公共方法

    @RequestMapping("/searchGroupCustomerAndSupplierToPage")
    @ResponseBody
    public Map<String, Object> searchGroupCustomerAndSupplierToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchGroupCustomerAndSupplierToPage(getMap()));
    }

    @RequestMapping("/searchCustomerAndSupplierToPage")
    @ResponseBody
    public Map<String, Object> searchCustomerAndSupplierToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchCustomerAndSupplierToPage(getMap()));
    }

    @RequestMapping("/searchCustomerAndSupplierToList")
    @ResponseBody
    public Map<String, Object> searchCustomerAndSupplierToList(HttpServletRequest request) throws Exception {
        return getReturnMap(basicInfoService.searchCustomerAndSupplierToList(getString("cussupType")));
    }

    @RequestMapping("/editUploadCustomerAndSupplierTempFile")
    @ResponseBody
    public Map<String, Object> uploadTempFile(HttpServletRequest request) throws Exception {
        return getReturnMap(basicInfoService.editUploadCustomerAndSupplierTempFile(request));
    }

    @RequestMapping("/editCustomerAndSupplier")
    @ResponseBody
    public Map<String, Object> editCustomerAndSupplierToList(HttpServletRequest request) throws Exception {
        String basicInfoPaperRelativePath = (String) request.getServletContext().getAttribute("basicInfo_paper_relativePath");
        String basicInfoPaperAbsolutePath = (String) request.getServletContext().getAttribute("baiscInfo_paper_absolutePath");
        String tempFilePath = (String) request.getServletContext().getAttribute(UploadFileUtil.FILE_UPLOADTEMPFILEABSOLUTEPATH);
        basicInfoService.editCustomerAndSupplierToList(getMap(), basicInfoPaperRelativePath, basicInfoPaperAbsolutePath, tempFilePath);
        basicRefresh(false);
        return getReturnMap();
    }

    @RequestMapping("/deleteCustomesrAndSuppliers")
    @ResponseBody
    public Map<String, Object> deleteCustomesrAndSuppliers(HttpServletRequest request) throws Exception {

        basicInfoService.deleteCustomesrAndSuppliers(getMap());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/saerchLineByConditionToPage")
    @ResponseBody
    public Map<String, Object> saerchLineByConditionToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.saerchLineByConditionToPage(getMap()));
    }

    @RequestMapping("/searchPigHouseToListForAdvancedSearch")
    @ResponseBody
    public Map<String, Object> searchPigHouseToListForAdvancedSearch(HttpServletRequest request) throws Exception {
        return getReturnMap(basicInfoService.searchPigHouseToListForAdvancedSearch());
    }

    @RequestMapping("/searchHouseToPageForAdvancedSearch")
    @ResponseBody
    public Map<String, Object> searchHouseToPageForAdvancedSearch(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchHouseToPageForAdvancedSearch(getMap()));
    }

    @RequestMapping("/searchCusSupForCreate")
    @ResponseBody
    public Map<String, Object> searchCusSupForCreate(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchCusSupForCreate(getString("createType"), getString("cussupType"), getString("companyName")));
    }

    @RequestMapping("/editFarmBind")
    @ResponseBody
    public Map<String, Object> editFarmBind(HttpServletRequest request) throws Exception {
        basicInfoService.editFarmBind(getMap());
        return getReturnMap();
    }

    @RequestMapping("/searchFarmBind")
    @ResponseBody
    public Map<String, Object> searchFarmBind(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchFarmBind());
    }

    @RequestMapping("/deleteFarmBind")
    @ResponseBody
    public Map<String, Object> deleteFarmBind(HttpServletRequest request) throws Exception {

        basicInfoService.deleteFarmBind(getIds(), getStrList("isAsyncs"));
        return getReturnMap();
    }

    @RequestMapping("/searchFarmBindToList")
    @ResponseBody
    public Map<String, Object> searchFarmBindToList(HttpServletRequest request) throws Exception {
        return getReturnMap(basicInfoService.searchFarmBindToList(getLong("mainId")));
    }

    @RequestMapping("/searchHousePigDetailedToPage")
    @ResponseBody
    public Map<String, Object> searchHousePigDetailedToPage(HttpServletRequest request) throws Exception {
        Long farmId = getFarmId();
        String houseId = getString("houseId");
        return getReturnMap(basicInfoService.searchHousePigDetailedToPage(farmId, houseId));
    }

    @RequestMapping("/searchHouseDetailedToPage")
    @ResponseBody
    public Map<String, Object> searchHouseDetailedToPage(HttpServletRequest request) throws Exception {
        Long farmId = getFarmId();
        String houseId = getString("houseId");
        return getReturnMap(basicInfoService.searchHouseDetailedToPage(farmId, houseId));
    }

    @RequestMapping("/saveSynchronizeFarm")
    @ResponseBody
    public Map<String, Object> saveSynchronizeFarm(HttpServletRequest request) throws Exception {

        basicInfoService.editSynchronizeFarm(getStrList("farmIds"), getStrList("companyIds"), getStrList("companyCodes"));
        return getReturnMap();
    }

    @RequestMapping("/searchSaleItemToPage")
    @ResponseBody
    public Map<String, Object> searchSaleItemToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchSaleItemToPage());
    }

    @RequestMapping("/searchSaleItemDetailToList")
    @ResponseBody
    public Map<String, Object> searchSaleItemDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchSaleItemDetailToList(getLong("mainId")));
    }

    @RequestMapping("/editSaleItem")
    @ResponseBody
    public Map<String, Object> editSaleItem(HttpServletRequest request) throws Exception {

        basicInfoService.editSaleItem(getLong("rowId"), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editDisablesPigHouse")
    @ResponseBody
    public Map<String, Object> editDisablesPigHouse(HttpServletRequest request) throws Exception {

        basicInfoService.editDisablesPigHouse(getIds(), getString("stopDate"));
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/editEnablesPigHouse")
    @ResponseBody
    public Map<String, Object> editEnablesPigHouse(HttpServletRequest request) throws Exception {

        basicInfoService.editEnablesPigHouse(getIds());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/searchHistoryPaper")
    @ResponseBody
    public Map<String, Object> searchHistoryPaper() throws Exception {
        return getReturnMap(basicInfoService.searchHistoryPaper(getMap()));
    }

    @RequestMapping("/searchHistoryAccount")
    @ResponseBody
    public Map<String, Object> searchHistoryAccount() throws Exception {
        return getReturnMap(basicInfoService.searchHistoryAccount(getMap()));
    }

    @RequestMapping("/searchHistoryLink")
    @ResponseBody
    public Map<String, Object> searchHistoryLink() throws Exception {
        return getReturnMap(basicInfoService.searchHistoryLink(getMap()));
    }

    @RequestMapping("/searchChangeHistory")
    @ResponseBody
    public Map<String, Object> searchChangeHistory(HttpServletRequest request) throws Exception {
        return getReturnMap(basicInfoService.searchChangeHistory(getMap()));

    }

    @RequestMapping("/editCustomerAndSupplierToFarms")
    @ResponseBody
    public Map<String, Object> editCustomerAndSupplierToFarms(HttpServletRequest request) throws Exception {
        basicInfoService.editCustomerAndSupplierToFarms(getMap());
        return getReturnMap();

    }

    @RequestMapping("/searchGroupCompanyByFarmIdToList")
    @ResponseBody
    public Map<String, Object> searchGroupCompanyByFarmIdToList(HttpServletRequest request) throws Exception {

        return getReturnMap(basicInfoService.searchGroupCompanyByFarmIdToList(getMap()));
    }

    @RequestMapping("/searchHrCompanyClienteleBillToList")
    @ResponseBody
    public Map<String, Object> searchHrCompanyClienteleBillToList(HttpServletRequest request) throws Exception {
        return getReturnMap(basicInfoService.searchHrCompanyClienteleBillToList(getString("cussupType")));
    }
}
