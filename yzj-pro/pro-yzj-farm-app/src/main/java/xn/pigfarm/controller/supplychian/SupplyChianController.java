
package xn.pigfarm.controller.supplychian;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.cache.CacheUtil;
import xn.core.util.uploadFile.UploadFileUtil;
import xn.pigfarm.service.supplychian.ISupplyChianService;
import xn.pigfarm.util.constants.SupplyChianConstants;

/**
 * @Description: 供应链模块
 * @author THL
 * @date 2016年8月15日 上午8:58:33
 */
@Component
@Controller
@RequestMapping("/supplyChian")
public class SupplyChianController extends BaseController {

    @Autowired
    private ISupplyChianService supplyChianService;

    // @RequestMapping("/editInsertMaterial")
    // @ResponseBody
    // public Map<String, Object> editInsertMaterial() throws Exception {
    // supplyChianService.editInsertMaterial();
    // return getReturnMap();
    // }

//    @RequestMapping("/getPigModelList")
//    @ResponseBody
//    public void getPigModelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> inputMap = getMap();
//        if ("1".equals(Maps.getString(inputMap, "model"))) {
//            Map<String, Object> map1 = new HashMap<String, Object>();
//            map1.put("ID", 11L);
//            map1.put("NAME", "李4");
//            list.add(map1);
//        }
//        Map<String, Object> map2 = new HashMap<String, Object>();
//        map2.put("ID", 12L);
//        map2.put("NAME", "李5");
//
//        list.add(map2);
//
//        Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("LIST");
//        for (Map<String, Object> map : list) {
//            Element mapElement = root.addElement("MAP");
//            for (Entry<String, Object> entry : map.entrySet()) {
//                // Element data = mapElement.addElement("ITEM");
//                // Element id1 = data.addElement("KEY");
//                // Element name1 = data.addElement("VALUE");
//                // id1.setText(entry.getKey());
//                // name1.setText(String.valueOf(entry.getValue()));
//                Element id1 = mapElement.addElement(entry.getKey());
//                id1.setText(String.valueOf(entry.getValue()));
//            }
//        }

        // 创建字符串缓冲区
//        response.setContentType("text/xml; charset=utf-8");
//        PrintWriter out = response.getWriter();
        // 设置文件编码
//        OutputFormat xmlFormat = new OutputFormat();
//        xmlFormat.setEncoding("UTF-8");
//         设置换行
//        xmlFormat.setNewlines(true);
        // 生成缩进
//        xmlFormat.setIndent(true);
        // 使用4个空格进行缩进, 可以兼容文本编辑器
//        xmlFormat.setIndent(" ");

        // 创建写文件方法
//        XMLWriter xmlWriter = new XMLWriter(out, xmlFormat);
        // 写入文件
//        xmlWriter.write(document);
        //
//        xmlWriter.flush();
        // 关闭
//        xmlWriter.close();
        // 输出xml
        // System.out.println(fileWriter.toString());

        // return fileWriter.toString();
//    }

    // public static void main(String[] args) throws IOException {
    //
    // List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    // Map<String, Object> map1 = new HashMap<String, Object>();
    // map1.put("ID", 11L);
    // map1.put("NAME", "李4");
    // list.add(map1);
    //
    // Map<String, Object> map2 = new HashMap<String, Object>();
    // map2.put("ID", 12L);
    // map2.put("NAME", "李5");
    //
    // list.add(map2);
    //
    // Document document = DocumentHelper.createDocument();
    // Element root = document.addElement("LIST");
    // for (Map<String, Object> map : list) {
    // Element mapElement = root.addElement("MAP");
    // for (Entry<String, Object> entry : map.entrySet()) {
    // // Element data = mapElement.addElement("ITEM");
    // // Element id1 = data.addElement("KEY");
    // // Element name1 = data.addElement("VALUE");
    // // id1.setText(entry.getKey());
    // // name1.setText(String.valueOf(entry.getValue()));
    // Element id1 = mapElement.addElement(entry.getKey());
    // id1.setText(String.valueOf(entry.getValue()));
    // }
    // }
    //
    // // 创建字符串缓冲区
    // StringWriter fileWriter = new StringWriter();
    // // 设置文件编码
    // OutputFormat xmlFormat = new OutputFormat();
    // xmlFormat.setEncoding("UTF-8");
    // // 设置换行
    // xmlFormat.setNewlines(true);
    // // 生成缩进
    // // xmlFormat.setIndent(true);
    // // 使用4个空格进行缩进, 可以兼容文本编辑器
    // xmlFormat.setIndent(" ");
    //
    // // 创建写文件方法
    // XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
    // // 写入文件
    // xmlWriter.write(document);
    // // 关闭
    // xmlWriter.close();
    // // 输出xml
    // System.out.println(fileWriter.toString());
    //
    // }
    /* BEGIN供应链相关控件值 */
    @RequestMapping("/searchEmployeeByIdToList")
    @ResponseBody
    public Map<String, Object> searchEmployeeByIdToList() throws Exception {
        return getReturnMap(supplyChianService.searchEmployeeByIdToList(getMap()));
    }

    @RequestMapping("/searchBrotherCompanyToList")
    @ResponseBody
    public Map<String, Object> searchBrotherCompanyToList() throws Exception {
        return getReturnMap(supplyChianService.searchBrotherCompanyToList(getMap()));
    }

    @RequestMapping("/searchCompanyFromRequireBillToList")
    @ResponseBody
    public Map<String, Object> searchCompanyFromRequireBillToList() throws Exception {
        return getReturnMap(supplyChianService.searchCompanyFromRequireBillToList(getMap()));
    }
    /* END供应链相关控件值 */

    /* BEGIN 查找有效物料主数据 */
    @RequestMapping("/searchSelectMaterialListTableToPage")
    @ResponseBody
    public Map<String, Object> searchSelectMaterialListTableToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchSelectMaterialListTableToPage(getMap()));

    }

    @RequestMapping("/searchMaterialFromRequireTableToPage")
    @ResponseBody
    public Map<String, Object> searchMaterialFromRequireTableToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchMaterialFromRequireTableToPage(getMap()));

    }

    @RequestMapping("/searchMaterialFromRequireTableDetailToList")
    @ResponseBody
    public Map<String, Object> searchMaterialFromRequireTableDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchMaterialFromRequireTableDetailToList(getMap()));

    }

    @RequestMapping("/searchMaterialFromPurchaseTableToPage")
    @ResponseBody
    public Map<String, Object> searchMaterialFromPurchaseTableToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchMaterialFromPurchaseTableToPage(getMap()));

    }

    @RequestMapping("/searchWarehouseMaterialToPage")
    @ResponseBody
    public Map<String, Object> searchWarehouseMaterialToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchWarehouseMaterialToPage(getMap()));

    }

    @RequestMapping("/searchWarehouseMaterialToList")
    @ResponseBody
    public Map<String, Object> searchWarehouseMaterialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchWarehouseMaterialToList(getMap()));

    }

    @RequestMapping("/searchSupplychianBillToPage")
    @ResponseBody
    public Map<String, Object> searchSupplychianBillToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchSupplychianBillToPage(getMap()));

    }

    @RequestMapping("/searchWarehouseByMaterialTypeToList")
    @ResponseBody
    public Map<String, Object> searchWarehouseByMaterialTypeToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchWarehouseByMaterialTypeToList(getMap()));

    }

    @RequestMapping("/searchAllotWareHouseToList")
    @ResponseBody
    public Map<String, Object> searchAllotWareHouseToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchAllotWareHouseToList(getMap()));

    }

    @RequestMapping("/searchTurnMaterialToFarmMaterialToList")
    @ResponseBody
    public Map<String, Object> searchTurnMaterialToFarmMaterialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchTurnMaterialToFarmMaterialToList(getMap()));

    }
    /* END 查找有效物料主数据 */

    /* BEGIN 拆分与合并 */
    @RequestMapping("/editSplitMaterial")
    @ResponseBody
    public Map<String, Object> editSplitMaterial(HttpServletRequest request) throws Exception {
        supplyChianService.editSplitMaterial(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editMergeMaterial")
    @ResponseBody
    public Map<String, Object> editMergeMaterial(HttpServletRequest request) throws Exception {
        supplyChianService.editMergeMaterial(getMap());
        return getReturnMap();

    }
    /* END 拆分与合并 */
    
    /* BEGIN 供应商与物料页面 */
    @RequestMapping("/searchMaterialToPage")
    @ResponseBody
    public Map<String, Object> searchMaterialToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchMaterialToPage(getMap()));

    }

    @RequestMapping("/searchCompanyByFarmIdToList")
    @ResponseBody
    public Map<String, Object> searchCompanyByFarmIdToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchCompanyByFarmIdToList(getMap()));
    }

    @RequestMapping("/editMaterialToFarms")
    @ResponseBody
    public Map<String, Object> editMaterialToFarms(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialToFarms(getMap());
        CacheUtil.MaterialInfoCacheRefresh();
        return getReturnMap();

    }

    @RequestMapping("/editUpdateMaterialDetailToFarms")
    @ResponseBody
    public Map<String, Object> editUpdateMaterialDetailToFarms(HttpServletRequest request) throws Exception {
        supplyChianService.editUpdateMaterialDetailToFarms(getMap());
        CacheUtil.MaterialInfoCacheRefresh();
        return getReturnMap();

    }
    /* END 供应商与物料页面 */

    /* BEGIN 预计用料记录 */
    @RequestMapping("/searchDailyRecordToPage")
    @ResponseBody
    public Map<String, Object> searchDailyRecordToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchDailyRecordToPage(getMap()));
    }

    @RequestMapping("/editDailyRecord")
    @ResponseBody
    public Map<String, Object> editDailyRecord(HttpServletRequest request) throws Exception {
        supplyChianService.editDailyRecord(getString("editType"), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/deleteDailyRecord")
    @ResponseBody
    public Map<String, Object> deleteDailyRecord(HttpServletRequest request) throws Exception {
        supplyChianService.deleteDailyRecord(getIds());
        return getReturnMap();
    }




    /* END 预计用料记录 */

    /* START 统计用料记录 */
    @RequestMapping("/searchDailyRecordDetailToList")
    @ResponseBody
    public Map<String, Object> searchDailyRecordDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchDailyRecordDetailToList(getIds()));
    }

    @RequestMapping("/editWarehouseEnough")
    @ResponseBody
    public Map<String, Object> editWarehouseEnough(HttpServletRequest request) throws Exception {
        supplyChianService.editWarehouseEnough(getIds());
        return getReturnMap();
    }
    /* END 统计用料记录 */

    /* START 需求单 */
    @RequestMapping("/searchRequireStoreToPage")
    @ResponseBody
    public Map<String, Object> searchRequireStoreToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchRequireStoreToPage());
    }

    @RequestMapping("/searchRequireStoreDetailToList")
    @ResponseBody
    public Map<String, Object> searchRequireStoreDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchRequireStoreDetailToList(getMap()));
    }

    @RequestMapping("/searchAccountsUnit")
    @ResponseBody
    public Map<String, Object> searchAccountsUnit(HttpServletRequest request) throws Exception {
        
        return getReturnMap(supplyChianService.searchAccountsUnit());
    }

    @RequestMapping("/editRequireStore")
    @ResponseBody
    public Map<String, Object> editRequireStore(HttpServletRequest request) throws Exception {
        supplyChianService.editRequireStore(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editScrapRequireStore")
    @ResponseBody
    public Map<String, Object> editScrapRequireStore(HttpServletRequest request) throws Exception {
        supplyChianService.editScrapRequireStore(getIds(), getMap());
        return getReturnMap();
    }

    @RequestMapping("/editSubmitRequireStore")
    @ResponseBody
    public Map<String, Object> editSubmitRequireStore(HttpServletRequest request) throws Exception {
        supplyChianService.editSubmitRequireStore(getIds());
        return getReturnMap();
    }

    /* END 需求单 */

    /* START 采购订单 */
    /* START 集团采购 */
    
    @RequestMapping("/searchPurchaseTypeByEmployeeIdToList")
    @ResponseBody
    public Map<String, Object> searchPurchaseTypeByEmployeeIdToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchPurchaseTypeByEmployeeIdToList(getMap()));
    }
    
    @RequestMapping("/searchGroupPurchaseStoreToPage")
    @ResponseBody
    public Map<String, Object> searchGroupPurchaseStoreToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchGroupPurchaseStoreToPage(getMap()));
    }

    @RequestMapping("/searchInputInfoFromPurchaseDetailToList")
    @ResponseBody
    public Map<String, Object> searchInputInfoFromPurchaseDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchInputInfoFromPurchaseDetailToList(getMap()));
    }

    @RequestMapping("/searchInputingEditFromPurchaseDetailToList")
    @ResponseBody
    public Map<String, Object> searchInputingEditFromPurchaseDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchInputingEditFromPurchaseDetailToList(getMap()));
    }

    @RequestMapping("/searchPurchaseStoreDetailToList")
    @ResponseBody
    public Map<String, Object> searchPurchaseStoreDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchPurchaseStoreDetailToList(getMap()));
    }

    @RequestMapping("/editGroupPurchaseStore")
    @ResponseBody
    public Map<String, Object> editGroupPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editGroupPurchaseStore(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editScrapGroupPurchaseStore")
    @ResponseBody
    public Map<String, Object> editScrapGroupPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editScrapGroupPurchaseStore(getIds(), getMap());
        return getReturnMap();
    }

    @RequestMapping("/editOverGroupPurchaseStore")
    @ResponseBody
    public Map<String, Object> editOverGroupPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editOverGroupPurchaseStore(getIds());
        return getReturnMap();
    }

    /* END 集团采购 */

    /* START 猪场自购 */
    @RequestMapping("/searchSelfPurchaseStoreToPage")
    @ResponseBody
    public Map<String, Object> searchSelfPurchaseStoreToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchSelfPurchaseStoreToPage(getMap()));
    }

    @RequestMapping("/editSelfPurchaseStore")
    @ResponseBody
    public Map<String, Object> editSelfPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editSelfPurchaseStore(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editScrapSelfPurchaseStore")
    @ResponseBody
    public Map<String, Object> editScrapSelfPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editScrapSelfPurchaseStore(getIds(), getMap());
        return getReturnMap();
    }
    /* END 猪场自购 */
    @RequestMapping("/editInputingPurchaseStore")
    @ResponseBody
    public Map<String, Object> editInputingPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editInputingPurchaseStore(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editFinishPurchaseStore")
    @ResponseBody
    public Map<String, Object> editFinishPurchaseStore(HttpServletRequest request) throws Exception {
        supplyChianService.editFinishPurchaseStore(getMap());
        return getReturnMap();
    }
    
    @RequestMapping("/searchSupplierHaveMaterialTypeToList")
    @ResponseBody
    public Map<String, Object> searchSupplierHaveMaterialTypeToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchSupplierHaveMaterialTypeToList(getMap()));
    }

    @RequestMapping("/searchFeedStartDateToList")
    @ResponseBody
    public Map<String, Object> searchFeedStartDateToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchFeedStartDateToList(getMap()));
    }

    @RequestMapping("/searchMaterialFeedFromInputingToPage")
    @ResponseBody
    public Map<String, Object> searchMaterialFeedFromInputingToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchMaterialFeedFromInputingToPage(getMap()));
    }

    /* END 采购订单 */

    /* START 入库单 */
    @RequestMapping("/searchInputStoreToPage")
    @ResponseBody
    public Map<String, Object> searchInputStoreToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchInputStoreToPage(getMap()));
    }

    @RequestMapping("/searchInputBillDetailToList")
    @ResponseBody
    public Map<String, Object> searchInputBillDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchInputBillDetailToList(getMap()));
    }

    @RequestMapping("/searchInputFromPurchaseDetailToList")
    @ResponseBody
    public Map<String, Object> searchInputFromPurchaseDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchInputFromPurchaseDetailToList(getMap()));
    }

    @RequestMapping("/searchMaterialByGroupIdToList")
    @ResponseBody
    public Map<String, Object> searchMaterialByGroupIdToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchMaterialByGroupIdToList(getMap()));
    }

    @RequestMapping("/editMaterialInput")
    @ResponseBody
    public Map<String, Object> editMaterialInput(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialInput(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/searchMaterialInputCommitDetailToList")
    @ResponseBody
    public Map<String, Object> searchMaterialInputCommitDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchMaterialInputCommitDetailToList(getMap()));
    }

    @RequestMapping("/editMaterialInputCommit")
    @ResponseBody
    public Map<String, Object> editMaterialInputCommit(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialInputCommit(getMap());
        return getReturnMap();
    }

    @RequestMapping("/editMaterialInputScrap")
    @ResponseBody
    public Map<String, Object> editMaterialInputScrap(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialInputScrap(getMap());
        return getReturnMap();
    }

    @RequestMapping("/editSpermInput")
    @ResponseBody
    public Map<String, Object> editSpermInput(HttpServletRequest request) throws Exception {
        supplyChianService.editSpermInput(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/searchSupplychianBillDetailToList")
    @ResponseBody
    public Map<String, Object> searchSupplychianBillDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchSupplychianBillDetailToList(getMap()));
    }
    
    @RequestMapping("/editReturnInput")
    @ResponseBody
    public Map<String, Object> editReturnInput(HttpServletRequest request) throws Exception {
        supplyChianService.editReturnInput(getMap(), getDetialListStr());
        return getReturnMap();
    }
    
    @RequestMapping("/editTrunOverScrap")
    @ResponseBody
    public Map<String, Object> editTrunOverScrap(HttpServletRequest request) throws Exception {
        supplyChianService.editTrunOverScrap(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editAllotInput")
    @ResponseBody
    public Map<String, Object> editAllotInput(HttpServletRequest request) throws Exception {
        supplyChianService.editAllotInput(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editNotBillInput")
    @ResponseBody
    public Map<String, Object> editNotBillInput(HttpServletRequest request) throws Exception {
        supplyChianService.editNotBillInput(getMap(), getDetialListStr());
        return getReturnMap();
    }
    /* END 入库单 */

    /* START 物料领用 控件URL */
    @RequestMapping("/searchHouseToList")
    @ResponseBody
    public Map<String, Object> searchHouseToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchHouseToList(getMap()));
    }

    @RequestMapping("/searchHouseHavePigToList")
    @ResponseBody
    public Map<String, Object> searchHouseHavePigToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchHouseHavePigToList(getMap()));
    }

    @RequestMapping("/searchSwineryHavePigToList")
    @ResponseBody
    public Map<String, Object> searchSwineryHavePigToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchSwineryHavePigToList(getMap()));
    }

    @RequestMapping("/searchDeptToList")
    @ResponseBody
    public Map<String, Object> searchDeptToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchDeptToList(getMap()));
    }
    /* END 物料领用 控件URL */

    /* START 出库单 */
    @RequestMapping("/searchOutputStoreToPage")
    @ResponseBody
    public Map<String, Object> searchOutputStoreToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchOutputStoreToPage(getMap()));
    }

    @RequestMapping("/searchOutputBillDetailToList")
    @ResponseBody
    public Map<String, Object> searchOutputBillDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchOutputBillDetailToList(getMap()));
    }

    @RequestMapping("/editOutputUse")
    @ResponseBody
    public Map<String, Object> editOutputUse(HttpServletRequest request) throws Exception {
        supplyChianService.editOutputUse(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editOutputScrap")
    @ResponseBody
    public Map<String, Object> editOutputScrap(HttpServletRequest request) throws Exception {
        supplyChianService.editOutputScrap(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/searchWareHouseMaterialFromPurchaseTableToPage")
    @ResponseBody
    public Map<String, Object> searchWareHouseMaterialFromPurchaseTableToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchWareHouseMaterialFromPurchaseTableToPage(getMap()));
    }

    @RequestMapping("/searchOutputFromPurchaseDetailToList")
    @ResponseBody
    public Map<String, Object> searchOutputFromPurchaseDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchOutputFromPurchaseDetailToList(getMap()));
    }

    @RequestMapping("/editOutputPurchase")
    @ResponseBody
    public Map<String, Object> editOutputPurchase(HttpServletRequest request) throws Exception {
        supplyChianService.editOutputPurchase(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editOutputAllot")
    @ResponseBody
    public Map<String, Object> editOutputAllot(HttpServletRequest request) throws Exception {
        supplyChianService.editOutputAllot(getMap(), getDetialListStr());
        return getReturnMap();
    }
    /* END 出库单 */

    /* yangy START 仓库设置 */
    @RequestMapping("/searchWarehouseSettingToPage")
    @ResponseBody
    public Map<String, Object> searchWarehouseSetting(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchWarehouseSetting());
    }

    @RequestMapping("/searchRoleByFarmIdToList")
    @ResponseBody
    public Map<String, Object> searchRoleByFarmIdToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchRoleByFarmIdToList());
    }

    @RequestMapping("/editWarehouseSetting")
    @ResponseBody
    public Map<String, Object> editWarehouseSetting(HttpServletRequest request) throws Exception {
        supplyChianService.editWarehouseSetting(getMap());
        basicRefresh(false);
        return getReturnMap();
    }

    @RequestMapping("/deleteWarehouseSetting")
    @ResponseBody
    public Map<String, Object> deleteWarehouseSetting(HttpServletRequest request) throws Exception {
        supplyChianService.deleteWarehouseSetting(getIds());
        return getReturnMap();
    }

    @RequestMapping("/editForbiddenWarehouseSetting")
    @ResponseBody
    public Map<String, Object> editForbiddenWarehouseSetting(HttpServletRequest request) throws Exception {
        supplyChianService.editForbiddenWarehouseSetting(getIds());
        return getReturnMap();
    }

    @RequestMapping("/editUseWarehouseSetting")
    @ResponseBody
    public Map<String, Object> editUseWarehouseSetting(HttpServletRequest request) throws Exception {
        supplyChianService.editUseWarehouseSetting(getIds());
        return getReturnMap();
    }

    @RequestMapping("/searchWarehouseToList")
    @ResponseBody
    public Map<String, Object> searchWarehouseToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchWarehouseToList());
    }

    @RequestMapping("/searchMaterialTypeByWarehouseToList")
    @ResponseBody
    public Map<String, Object> searchMaterialTypeByWarehouseToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchMaterialTypeByWarehouseToList(getMap()));
    }
    
    @RequestMapping("/editWarehouseStoreMaterial")
    @ResponseBody
    public Map<String, Object> editWarehouseStoreMaterial(HttpServletRequest request) throws Exception {
        supplyChianService.editWarehouseStoreMaterial(getMap(), getDetialListStr());
        // basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/editDefaultHouse")
    @ResponseBody
    public Map<String, Object> editDefaultHouse(HttpServletRequest request) throws Exception {
        supplyChianService.editDefaultHouse(getMap(), getDetialListStr());
        CacheUtil.MaterialInfoCacheRefresh();
        return getReturnMap();
    }
    /* yangy END 仓库设置 */

    /* START 仓管发料 */
    @RequestMapping("/searchOutputUseToPage")
    @ResponseBody
    public Map<String, Object> searchOutputUseToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchOutputUseToPage(getMap()));
    }

    // @RequestMapping("/searchOutputUseDetailToList")
    // @ResponseBody
    // public Map<String, Object> searchOutputUseDetailToList(HttpServletRequest request) throws Exception {
    // return getReturnMap(supplyChianService.searchSupplychianBillDetailToList(getMap()));
    // }

    @RequestMapping("/searchMaterialByMaterialIdToList")
    @ResponseBody
    public Map<String, Object> searchMaterialByMaterialIdToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchMaterialByMaterialIdToList(getMap()));
    }

    @RequestMapping("/searchGiveOutMaterialByMaterialIdToList")
    @ResponseBody
    public Map<String, Object> searchGiveOutMaterialByMaterialIdToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchGiveOutMaterialByMaterialIdToList(getMap()));
    }

    @RequestMapping("/editGiveOutTruePrepared")
    @ResponseBody
    public Map<String, Object> editGiveOutTruePrepared(HttpServletRequest request) throws Exception {
        supplyChianService.editGiveOutTruePrepared(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editGiveOutTrue")
    @ResponseBody
    public Map<String, Object> editGiveOutTrue(HttpServletRequest request) throws Exception {
        supplyChianService.editGiveOutTrue(getIds());
        return getReturnMap();
    }

    @RequestMapping("/editGiveOutFalse")
    @ResponseBody
    public Map<String, Object> editGiveOutFalse(HttpServletRequest request) throws Exception {
        supplyChianService.editGiveOutFalse(getIds());
        return getReturnMap();
    }

    /* END 仓管发料 */

    /* START 仓管发料（明细显示） */
    @RequestMapping("/searchOutputUseDetailToList")
    @ResponseBody
    public Map<String, Object> searchOutputUseDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchOutputUseDetailToList(getMap()));
    }

    @RequestMapping("/editGiveOutDetailTrue")
    @ResponseBody
    public Map<String, Object> editGiveOutDetailTrue(HttpServletRequest request) throws Exception {
        supplyChianService.editGiveOutDetailTrue(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/editGiveOutDetailFalse")
    @ResponseBody
    public Map<String, Object> editGiveOutDetailFalse(HttpServletRequest request) throws Exception {
        supplyChianService.editGiveOutDetailFalse(getMap(), getDetialListStr());
        return getReturnMap();
    }

    /* END 仓管发料（明细显示） */
    /**
     * @Description: 获取部门树
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchDepTree")
    @ResponseBody
    public Map<String, Object> searchDepTree(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchDepTree(getLong("farmId"), getLong("endDeptId")));
    }

    /* START 套餐以及物料替换 */
    @RequestMapping("/searchReplaceAndPackageToPage")
    @ResponseBody
    public Map<String, Object> searchReplaceAndPackageToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReplaceAndPackageToPage());
    }

    @RequestMapping("/searchReplaceAndPackageDetailToList")
    @ResponseBody
    public Map<String, Object> searchReplaceAndPackageDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReplaceAndPackageDetailToList(getMap()));
    }

    @RequestMapping("/editReplaceAndPackage")
    @ResponseBody
    public Map<String, Object> editReplaceAndPackage(HttpServletRequest request) throws Exception {
        supplyChianService.editReplaceAndPackage(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/deleteReplaceAndPackage")
    @ResponseBody
    public Map<String, Object> deleteReplaceAndPackage(HttpServletRequest request) throws Exception {
        supplyChianService.deleteReplaceAndPackage(getIds());
        return getReturnMap();
    }

    @RequestMapping("/searchReplaceMaterialToList")
    @ResponseBody
    public Map<String, Object> searchReplaceMaterialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReplaceMaterialToList(getMap()));
    }

    @RequestMapping("/searchFixedFreeMaterialToList")
    @ResponseBody
    public Map<String, Object> searchFixedFreeMaterialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchFixedFreeMaterialToList(getMap()));
    }


    @RequestMapping("/searchFixedGroupMaterialToList")
    @ResponseBody
    public Map<String, Object> searchFixedGroupMaterialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchFixedGroupMaterialToList(getMap()));
    }

    @RequestMapping("/searchFixedUseMaterialToList")
    @ResponseBody
    public Map<String, Object> searchFixedUseMaterialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchFixedUseMaterialToList(getMap()));
    }
    /* END 套餐以及物料替换 */

    /* START 发票填写 */
    @RequestMapping("/searchReceiptBillToPage")
    @ResponseBody
    public Map<String, Object> searchReceiptBillToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReceiptBillToPage(getMap()));
    }

    @RequestMapping("/searchSupplierForReceiptBillToList")
    @ResponseBody
    public Map<String, Object> searchSupplierForReceiptBillToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchSupplierForReceiptBillToList(getMap()));
    }

    @RequestMapping("/searchBranchIdForReceiptBillToList")
    @ResponseBody
    public Map<String, Object> searchBranchIdForReceiptBillToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchBranchIdForReceiptBillToList(getMap()));
    }

    @RequestMapping("/searchFarmIdForReceiptBillToList")
    @ResponseBody
    public Map<String, Object> searchFarmIdForReceiptBillToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchFarmIdForReceiptBillToList(getMap()));
    }

    @RequestMapping("/searchInvoiceTableDetialToList")
    @ResponseBody
    public Map<String, Object> searchInvoiceTableDetialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchInvoiceTableDetialToList(getMap()));
    }

    @RequestMapping("/searchListTableDetialToList")
    @ResponseBody
    public Map<String, Object> searchListTableDetialToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchListTableDetialToList(getMap()));
    }

    @RequestMapping("/searchReceiptFillInInputDetailToList")
    @ResponseBody
    public Map<String, Object> searchReceiptFillInInputDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReceiptFillInInputDetailToList(getMap()));
    }

    @RequestMapping("/editReceiptTempFile")
    @ResponseBody
    public Map<String, Object> editReceiptTempFile(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.editReceiptTempFile(request));
    }

    @RequestMapping("/editReceiptFillIn")
    @ResponseBody
    public Map<String, Object> editReceiptFillIn(HttpServletRequest request) throws Exception {
        String supplyChainReceiptRelativePath = (String) request.getServletContext().getAttribute(
                SupplyChianConstants.SUPPLYCHAIN_RECEIPT_RELATIVEPATH);
        String supplyChainReceiptAbsolutePath = (String) request.getServletContext().getAttribute(
                SupplyChianConstants.SUPPLYCHAIN_RECEIPT_ABSOLUTEPATH);
        String tempFilePath = (String) request.getServletContext().getAttribute(UploadFileUtil.FILE_UPLOADTEMPFILEABSOLUTEPATH);
        supplyChianService.editReceiptFillIn(getMap(), supplyChainReceiptRelativePath, supplyChainReceiptAbsolutePath, tempFilePath);
        return getReturnMap();
    }

    /* END 发票填写 */

    /* START 发票查询 */
    @RequestMapping("/searchReceiptDetailListToPage")
    @ResponseBody
    public Map<String, Object> searchReceiptDetailListToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReceiptDetailListToPage(getMap()));
    }

    @RequestMapping("/searchReceiptMaterialDetailListToPage")
    @ResponseBody
    public Map<String, Object> searchReceiptMaterialDetailListToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchReceiptMaterialDetailListToPage(getMap()));
    }

    @RequestMapping("/searchCompanyToList")
    @ResponseBody
    public Map<String, Object> searchCompanyToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchCompanyToList(getMap()));
    }

    /* END 发票查询 */

    /* START 物料期末盘存 */

    @RequestMapping("/searchMonthAccountToList")
    @ResponseBody
    public Map<String, Object> searchMonthAccountToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchMonthAccountToList(getMap()));
    }

    @RequestMapping("/editMonthAccount")
    @ResponseBody
    public Map<String, Object> editMonthAccount(HttpServletRequest request) throws Exception {
        supplyChianService.editMonthAccount(getMap(), getDetialListStr());
        return getReturnMap();
    }
    /* END 物料期末盘存 */

    /* START 添加药领用 */
    @RequestMapping("/searchAdditiveUseMaterialFarmToList")
    @ResponseBody
    public Map<String, Object> searchAdditiveUseMaterialFarmToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchAdditiveUseMaterialFarmToList(getMap()));
    }

    @RequestMapping("/editAdditiveUseMaterial")
    @ResponseBody
    public Map<String, Object> editAdditiveUseMaterial(HttpServletRequest request) throws Exception {
        supplyChianService.editAdditiveUseMaterial(getMap(), getDetialListStr());
        return getReturnMap();
    }
    /* END 添加药领用 */

    /* START 实验室领用 */
    @RequestMapping("/searchLaboratoryUseMaterialFarmToList")
    @ResponseBody
    public Map<String, Object> searchLaboratoryUseMaterialFarmToList(HttpServletRequest request) throws Exception {
        return getReturnMap(supplyChianService.searchLaboratoryUseMaterialFarmToList(getMap()));
    }

    @RequestMapping("/editLaboratoryUseMaterial")
    @ResponseBody
    public Map<String, Object> editLaboratoryUseMaterial(HttpServletRequest request) throws Exception {
        supplyChianService.editLaboratoryUseMaterial(getMap(), getDetialListStr());
        return getReturnMap();
    }
    /* END 实验室领用 */

    /* START 大丰模式判断 */

    @RequestMapping("/searchDafengModelFlag")
    @ResponseBody
    public Map<String, Object> searchDafengModelFlag(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchDafengModelFlag());
    }

    /* END 大丰模式判断 */

    /* START 更新单价 */

    @RequestMapping("/searchUpdatePriceToPage")
    @ResponseBody
    public Map<String, Object> searchUpdatePriceToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchUpdatePriceToPage(getMap()));

    }

    @RequestMapping("/searchEditUpdatePriceDetailToList")
    @ResponseBody
    public Map<String, Object> searchEditUpdatePriceDetailToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchEditUpdatePriceDetailToList(getMap()));
    }

    @RequestMapping("/searchUpdatePriceSupplierToList")
    @ResponseBody
    public Map<String, Object> searchUpdatePriceSupplierToList(HttpServletRequest request) throws Exception {

        return getReturnMap(supplyChianService.searchUpdatePriceSupplierToList(getMap()));
    }

    @RequestMapping("/editUpdatePrice")
    @ResponseBody
    public Map<String, Object> editUpdatePrice(HttpServletRequest request) throws Exception {
        supplyChianService.editUpdatePrice(getMap(), getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/searchUpdatePriceDetailToList")
    @ResponseBody
    public Map<String, Object> searchUpdatePriceDetailToList(HttpServletRequest request) throws Exception {
        String supplierId = getString("supplierId");
        String startDateEndDate = getString("startDateEndDate");
        return getReturnMap(supplyChianService.searchUpdatePriceDetailToList(supplierId, startDateEndDate));

    }

    /* END 更新单价 */

    /* START 导入期初到SAP中 */
    // 导入物料主数据
    @RequestMapping("/editMaterialInfoToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialInfoToHANA(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialInfoToHANA(getMap());
        return getReturnMap();

    }

    // 导入供应商
    @RequestMapping("/editMaterialSupplierToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialSupplierToHANA(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialSupplierToHANA(getMap());
        return getReturnMap();

    }

    // 导入期末库存
    @RequestMapping("/editMaterialToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialToHANA(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialToHANA(getMap());
        return getReturnMap();

    }

    // 导入采购订单
    @RequestMapping("/editMaterialPurchaseBillToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialPurchaseBillToHANA(HttpServletRequest request) throws Exception {
        supplyChianService.editMaterialPurchaseBillToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editMaterialInputBillToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialInputBillToHANA(HttpServletRequest request) throws Exception {
        // 用于采购入库和采购退货
        supplyChianService.editMaterialInputBillToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editMaterialOutputUseBillToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialOutputUseBillToHANA(HttpServletRequest request) throws Exception {
        // 用于出库领用
        supplyChianService.editMaterialOutputUseBillToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editMaterialOutputAllotInputBillToHANA.do")
    @ResponseBody
    public Map<String, Object> editMaterialOutputAllotInputBillToHANA(HttpServletRequest request) throws Exception {
        // 用于出库领用
        supplyChianService.editMaterialOutputAllotInputBillToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editHouseToHANA.do")
    @ResponseBody
    public Map<String, Object> editHouseToHANA(HttpServletRequest request) throws Exception {
        // 猪舍
        supplyChianService.editHouseToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editCustomerToHANA.do")
    @ResponseBody
    public Map<String, Object> editCustomerToHANA(HttpServletRequest request) throws Exception {
        // 客户
        supplyChianService.editCustomerToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editSowToProductToHANA.do")
    @ResponseBody
    public Map<String, Object> editSowToProductToHANA(HttpServletRequest request) throws Exception {
        // 生产母猪
        supplyChianService.editSowToProductToHANA(getMap());
        return getReturnMap();

    }

    @RequestMapping("/editBoarToProductToHANA.do")
    @ResponseBody
    public Map<String, Object> editBoarToProductToHANA(HttpServletRequest request) throws Exception {
        // 生产公猪
        supplyChianService.editBoarToProductToHANA(getMap());
        return getReturnMap();

    }

    /* END 导入期初到SAP中 */

}
