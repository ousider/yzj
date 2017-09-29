
package xn.pigfarm.controller.production;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.data.Maps;
import xn.core.util.page.BasePageInfo;
import xn.core.util.unityreturn.BuildReturnMapUtil;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.ParaPigMoveInModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.PorkCheckModel;
import xn.pigfarm.model.production.SearchSemenModel;
import xn.pigfarm.model.production.SearchValidPigModel;
import xn.pigfarm.model.production.SemenBillModel;
import xn.pigfarm.service.hana.ISendToSAPProductionService;
import xn.pigfarm.service.production.IBillViewService;
import xn.pigfarm.service.production.IProductionService;
import xn.pigfarm.service.production.ISearchPigRemind;

/**
 * @Description: 生产事件录入
 * @author fangc
 * @date 2016年4月25日 上午11:47:58
 */
@Component
@Controller
@RequestMapping("/production")
public class ProductionController extends BaseController {

    @Autowired
    private IProductionService productionService;

    @Autowired
    private IBillViewService billViewSerive;

    @Autowired
    private ISearchPigRemind searchPigRemind;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private ISendToSAPProductionService sendToSAPProductionService;

    /**
     * @Description: 根据事件类别查询有效猪只
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchValidPigToPage")
    @ResponseBody
    public Map<String, Object> searchValidPigToPage(HttpServletRequest request) throws Exception {
        SearchValidPigModel beanBaseModel = getBeanBaseModel(SearchValidPigModel.class);
        BasePageInfo pageInfo = productionService.editValidPigToPage(beanBaseModel);
        Map<String, Object> returnMap = BuildReturnMapUtil.getReturnMap(pageInfo);

        pageInfo.getEndRow();
        String query = beanBaseModel.getQuery();
        // List list = pageInfo.getList();

        if (pageInfo.getList().size() == 0 && query != null) {
            String pigIds = request.getParameterMap().get("pigIds")[0];
            String[] split = query.split(",");
            StringBuffer msgs = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                String pigRemind = searchPigRemind.searchPigRemind(beanBaseModel.getEventName(), beanBaseModel.getQuery(), pigIds);
                msgs.append(pigRemind);
            }
            returnMap.put("errorMsg", msgs);
            returnMap.put("searchSuccess", false);
        } else {
            returnMap.put("searchSuccess", true);
        }
        return returnMap;
    };

    /**
     * @Description: 根据猪舍查询肉猪只
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPorkToList")
    @ResponseBody
    public Map<String, Object> searchPorkToList(HttpServletRequest request) throws Exception {

        BasePageInfo pageInfo = productionService.searchPorkToList(getString("eventName"), getLong("houseId"), getLong("pigpenId"), getLong(
                "swineryId"), getLong("breedId"), getString("changeType"));
        return BuildReturnMapUtil.getReturnMap(pageInfo);

    };

    /**
     * @Description: 根据猪舍查询肉猪只（猪栏）
     * @author yangy
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPorkToList2")
    @ResponseBody
    public Map<String, Object> searchPorkToList2(HttpServletRequest request) throws Exception {

        BasePageInfo pageInfo = productionService.searchPorkToList2(getString("eventName"), getLong("houseId"), getString("pigpenId"), getLong(
                "swineryId"), getLong("breedId"), getString("changeType"));
        return BuildReturnMapUtil.getReturnMap(pageInfo);

    };

    /**
     * @Description: 选择配种精液
     * @author fangc
     * @param searchSemenToList
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSemenToList")
    @ResponseBody
    public Map<String, Object> searchSemenToList(HttpServletRequest request) throws Exception {

        BasePageInfo pageInfo = productionService.editSemenToList(getBean(SearchSemenModel.class));
        return BuildReturnMapUtil.getReturnMap(pageInfo);

    };

    /**
     * @Description: 查询表单
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchBillToPage")
    @ResponseBody
    public Map<String, Object> searchBillToPage(HttpServletRequest request) throws Exception {
        String businessCode = getString("businessCode");
        String createName = getString("createName");
        String billDate = getString("billDate");
        String billDate2 = getString("billDate2");
        String createDate = getString("createDate") == null ? null : getString("createDate") + " 00:00:00";
        String createDate2 = getString("createDate2") == null ? null : getString("createDate2") + " 23:59:59";
        String eventCode = getString("eventCode");
        BasePageInfo pageInfo = productionService.searchBillToPage(businessCode, createName, billDate, billDate2, createDate, createDate2, eventCode);
        return BuildReturnMapUtil.getReturnMap(pageInfo);

    };

    /**
     * @Description: 猪只入场
     * @author fangc
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editPigMoveIn")
    @ResponseBody
    public Map<String, Object> editPigMoveIn(HttpServletRequest request) throws Exception {

        productionService.editPigMoveIn(getBeanCom(ParaPigMoveInModel.class), getString("eventName"), getDetialListStr());
        // 刷新缓存
        cacheRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    @RequestMapping("/changeEarBand")
    @ResponseBody
    public Map<String, Object> changeEarBand(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editChangeEarBand(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 背膘测定
    @RequestMapping("/backfat")
    @ResponseBody
    public Map<String, Object> backfat(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editBackfat(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    };

    // 转舍
    @RequestMapping("/changeLaborHouse")
    @ResponseBody
    public Map<String, Object> changeLaborHouse(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editChangeLaborHouse(model, getString("eventName"), getDetialListStr(), getString("zcfxycbbFlag"), getString(
                "requiredPigpen"));
        pigInfoRefresh();
        basicRefresh(true);
        return BuildReturnMapUtil.getReturnMap();
    };

    // 转群
    @RequestMapping("/changeSwinery")
    @ResponseBody
    public Map<String, Object> changeSwinery(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editChangeSwinery(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 后备转生产公猪
    @RequestMapping("/boarReserveToProduct")
    @ResponseBody
    public Map<String, Object> boarReserveToProduct(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editBoarReserveToProduct(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 采精
    @RequestMapping("/semen")
    @ResponseBody
    public Map<String, Object> semen(HttpServletRequest request) throws Exception {

        SemenBillModel model = getBeanCom(SemenBillModel.class);
        productionService.editSemen(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    };

    // 采精事件仓库默认填写精液仓库

    @RequestMapping("/searchSpermWarehouseName")
    @ResponseBody
    public Map<String, Object> searchSpermWarehouseName(HttpServletRequest request) throws Exception {

        return getReturnMap(productionService.searchSpermWarehouseName(getMap()));
    }

    // 后备情期鉴定
    @RequestMapping("/prepubertalCheck")
    @ResponseBody
    public Map<String, Object> prepubertalCheck(HttpServletRequest request) throws Exception {

        productionService.editPrepubertalCheck(getBean(BillModel.class), getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 配种
    @RequestMapping("/breed")
    @ResponseBody
    public Map<String, Object> breed(HttpServletRequest request) throws Exception {

        productionService.editBreed(getBean(BillModel.class), getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 妊娠检查
    @RequestMapping("/pregnancyCheck")
    @ResponseBody
    public Map<String, Object> pregnancyCheck(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editPregnancyCheck(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 分娩
    @RequestMapping("/delivery")
    @ResponseBody
    public Map<String, Object> delivery(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editDelivery(model, getString("eventName"), getDetialListStr(), getString("FMSFLZ_flag"));
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 寄养
    @RequestMapping("/foster")
    @ResponseBody
    public Map<String, Object> foster(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editFoster(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    };

    // 仔猪死亡
    @RequestMapping("/childPigDie")
    @ResponseBody
    public Map<String, Object> childPigDie(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editChildPigDie(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 断奶
    @RequestMapping("/wean")
    @ResponseBody
    public Map<String, Object> wean(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editWean(model, getString("eventName"), getDetialListStr(), getString("dnxycbbFlag"), getString("autoFosterDieFlag"));
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 转保育
    @RequestMapping("/toChildCare")
    @ResponseBody
    public Map<String, Object> toChildCare(HttpServletRequest request) throws Exception {
        BillModel model = getBean(BillModel.class);
        productionService.editToChildCare(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    }

    // 转育肥
    @RequestMapping("/toChildFatten")
    @ResponseBody
    public Map<String, Object> toChildFatten(HttpServletRequest request) throws Exception {
        BillModel model = getBean(BillModel.class);
        productionService.editToChildFatten(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    }

    // 商品猪死亡
    @RequestMapping("/goodPigDie")
    @ResponseBody
    public Map<String, Object> goodPigDie(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editGoodPigDie(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    };

    // 商品猪销售
    @RequestMapping("/goodPigSell")
    @ResponseBody
    public Map<String, Object> goodPigSell(HttpServletRequest request) throws Exception {
        productionService.editGoodPigSell(getMap(), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 淘汰
    @RequestMapping("/obsolete")
    @ResponseBody
    public Map<String, Object> obsolete(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editObsolete(model, getString("eventName"), getDetialListStr());
        return BuildReturnMapUtil.getReturnMap();
    }

    // 离场
    @RequestMapping("/leave")
    @ResponseBody
    public Map<String, Object> leave(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editLeave(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    }

    /**
     * @Description: 初始化系统
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/inputPig")
    @ResponseBody
    public Map<String, Object> inputPig(HttpServletRequest request) throws Exception {
        productionService.editInputPig();
        return BuildReturnMapUtil.getReturnMap();
    }

    // =========================查询猪群
    @RequestMapping("/searchSwineryToList")
    @ResponseBody
    public Map<String, Object> searchSwineryToList(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchSwineryToList(getMap()));
    }

    @RequestMapping("/searchSwineryToPage")
    @ResponseBody
    public Map<String, Object> searchSwineryToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(productionService.searchSwineryToPage(getMap()));
    }

    @RequestMapping("/editSwinery")
    @ResponseBody
    public Map<String, Object> editSwinery(HttpServletRequest request) throws Exception {
        productionService.editSwinery(getMap());
        return getReturnMap();
    }

    @RequestMapping("/deleteSwinerys")
    @ResponseBody
    public Map<String, Object> deletePpSwinerys(HttpServletRequest request) throws Exception {

        productionService.deleteSwinerys(getIds());
        return getReturnMap();
    }

    // =========================猪只
    @RequestMapping("/searchPigToList")
    @ResponseBody
    public Map<String, Object> searchPigToList(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchPigToList(getString("pigType")));
    }

    @RequestMapping("/searchPigToPage")
    @ResponseBody
    public Map<String, Object> searchPigToPage() throws Exception {
        // earBrand, earShort, swineryName, houseName, materialId, pigTypeName, breedName, pigClass, typeName
        return getReturnMap(productionService.searchPigToPage(getMap()));
    }

    @RequestMapping("/editPig")
    @ResponseBody
    public Map<String, Object> editPig(HttpServletRequest request) throws Exception {

        productionService.editPig(getBean(PigModel.class));
        return getReturnMap();
    }

    @RequestMapping("/deletePigs")
    @ResponseBody
    public Map<String, Object> deletePigs(HttpServletRequest request) throws Exception {

        productionService.deletePigs(getIds());
        // 刷新缓存
        pigInfoRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 事件查询 查询明细
     * @author Zhangjc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchDetailBillToList")
    @ResponseBody
    public Map<String, Object> searchDetailBillToList(HttpServletRequest request) throws Exception {
        // 撤销单据的事件名称
        String eventCode = getString("eventCode");
        // 撤销单据的PP_M_BILL的ROW_ID
        Long cancelBillId = getLong("cancelBillId");
        // 被撤销单据的PP_M_BILL的ROW_ID
        Long billId = getLong("billId");
        // 被撤销单据的事件名称
        String oldEventCode = getString("oldEventCode");

        return getReturnMap(billViewSerive.searchDetailBillToList(cancelBillId, eventCode, billId, oldEventCode));
    }

    // 审批时查询淘汰单据
    @RequestMapping("/searchObsoleteBill")
    @ResponseBody
    public Map<String, Object> searchObsoleteBill() throws Exception {
        return getReturnMap(productionService.searchObsoleteBill());
    }

    // 查询淘汰申请单据明细
    @RequestMapping("/searchBoarObsolete")
    @ResponseBody
    public Map<String, Object> searchBoarObsolete(HttpServletRequest request) throws Exception {

        Long billId = getLong("billId");
        if (billId == null) {
            return getReturnMap();
        }
        return getReturnMap(productionService.searchBoarObsolete(billId));
    }

    // 审批
    @RequestMapping("/editBoarObsolete")
    @ResponseBody
    public Map<String, Object> editBoarObsolete(HttpServletRequest request) throws Exception {

        // String status = getString("status");
        // Long billId = getLong("billId");
        // String enterDate = getString("enterDate");
        // productionService.editBoarObsolete(getIds(), status, billId, getStrList("pigIds"));
        productionService.editBoarObsolete(getMap());
        pigInfoRefresh();
        return getReturnMap();
    }

    // 获取审批通过的种猪
    @RequestMapping("/searchBoarObsoletePass")
    @ResponseBody
    public Map<String, Object> searchBoarObsoletePass(HttpServletRequest request) throws Exception {
        String pigIds = getString("pigIds");
        // String leaveReason = getString("leaveReason");
        return getReturnMap(productionService.searchBoarObsoletePass(pigIds));
    }

    // 查询获得撤销了什么单据事件
    @RequestMapping("/searchWhichEventCancel")
    @ResponseBody
    public Map<String, Object> searchWhichEventCancel(HttpServletRequest request) throws Exception {
        return getReturnMap(billViewSerive.searchWhichEventCancel(getLong("billId")));
    }

    // 背膘和评分
    @RequestMapping("/searchBackfatScore")
    @ResponseBody
    public Map<String, Object> searchBackfatScore(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchBackfatScoreMap());
    }

    // 猪群关闭
    @RequestMapping("/closeSwinery")
    @ResponseBody
    public Map<String, Object> closeSwinery(HttpServletRequest request) throws Exception {
        productionService.editCloseSwinery(getIds());
        return getReturnMap();
    }

    // 猪群开启
    @RequestMapping("/openSwinery")
    @ResponseBody
    public Map<String, Object> openSwinery(HttpServletRequest request) throws Exception {
        productionService.editOpenSwinery(getIds());
        return getReturnMap();
    }

    // 奶妈转舍
    @RequestMapping("/nurseChangeHouse")
    @ResponseBody
    public Map<String, Object> nurseChangeHouse(HttpServletRequest request) throws Exception {

        BillModel model = getBean(BillModel.class);
        productionService.editNurseChangeHouse(model, getString("eventName"), getDetialListStr(), getString("zcfxycbbFlag"));
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    }

    // 根据猪栏查询母猪
    @RequestMapping("/searchValidPigByPigpen")
    @ResponseBody
    public Map<String, Object> searchValidPigByPigpenId(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchValidPigByPigpenId(getLong("inPigpenId")));
    }

    /********************************** yangy 2016-11-22 肉猪盘点 begin ***********************************/
    @RequestMapping("/searchPorkCountBillList")
    @ResponseBody
    public Map<String, Object> searchPorkCountBillList(HttpServletRequest request) throws Exception {
        String billId = getString("billId");
        String billDate = getString("billDate");
        String checkOrgan = getString("checkOrgan");
        String accountUser = getString("checkUser");
        return getReturnMap(productionService.searchPorkCountBillList(billId, billDate, checkOrgan, accountUser));
    }

    @RequestMapping("/searchPorkPigCountList")
    @ResponseBody
    public Map<String, Object> searchPorkPigCountList(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchPorkPigCountList(getString("billId"), getString("billDate"), getString("checkType")));
    }

    @RequestMapping("/editPorkPigCountList")
    @ResponseBody
    public Map<String, Object> editPorkPigCountList(HttpServletRequest request) throws Exception {
        String billDate = getString("billDate");
        String accountUser = getString("accountUser");
        String checkOrgan = getString("checkOrgan");
        String notes = getString("notes");
        String eventName = getString("eventName");
        String checkType = getString("checkType");
        List<PorkCheckModel> porkCheckModels = getModelList(getDetialListStr(), PorkCheckModel.class);
        productionService.editPorkPigCountList(porkCheckModels, eventName, billDate, accountUser, checkOrgan, notes, checkType);
        return getReturnMap();
    }

    /********************************** yangy 2016-11-22 肉猪盘点 end ************************************/

    // 选择相关销售单据
    @RequestMapping("/selectSellBill")
    @ResponseBody
    public Map<String, Object> selectSellBill(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.selectSellBill(getString("pigType")));
    }

    // 根据单据ID查询未入场猪只信息
    @RequestMapping("/selectPigListByBillId")
    @ResponseBody
    public Map<String, Object> selectPigListByBillId() throws Exception {
        return getReturnMap(productionService.selectPigListByBillId(getLong("billId"), getString("pigType")));
    }

    /********************************** yangy 2016-11-29 生产动态 begin ***********************************/
    @RequestMapping("/searchLivestockByProduceToList")
    @ResponseBody
    public Map<String, Object> searchLivestockByProduceToList() throws Exception {
        return getReturnMap(productionService.searchLivestockByProduceToList(getString("farmId")));
    }

    @RequestMapping("/searchProduceChangeToList")
    @ResponseBody
    public Map<String, Object> searchProduceChangeToList() throws Exception {
        return getReturnMap(productionService.searchProduceChangeToList(getString("farmId"), getString("dateType")));
    }

    @RequestMapping("/searchPerformanceComparisonToList")
    @ResponseBody
    public Map<String, Object> searchPerformanceComparisonToList() throws Exception {
        return getReturnMap(productionService.searchPerformanceComparisonToList(getString("farmId"), getString("dateContrast")));
    }

    /********************************** yangy 2016-11-29 肉猪动态 end ***********************************/

    @RequestMapping("/editSaleSemenList")
    @ResponseBody
    public Map<String, Object> editSaleSemenList(HttpServletRequest request) throws Exception {
        BillModel model = getBean(BillModel.class);
        String customerId = getString("customerId");
        productionService.editSaleSemenList(model, getDetialListStr(), customerId);
        pigInfoRefresh();
        return getReturnMap();
    }

    @RequestMapping("/editSemenBill")
    @ResponseBody
    public Map<String, Object> editSemenBill() throws Exception {
        return getReturnMap(productionService.editSemenBill());
    }

    @RequestMapping("/editSemenListByBillId")
    @ResponseBody
    public Map<String, Object> selectSemenListByBillId() throws Exception {
        return getReturnMap(productionService.editSemenListByBillId(getLong("billId")));
    }

    @RequestMapping("/getEarBrandByPigId")
    @ResponseBody
    public Map<String, Object> getEarBrandByPigId() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("earBrand", productionService.getEarBrandByPigId(getLong("pigId")));
        return map;
    }

    @RequestMapping("/searchFarmHandByPlanType")
    @ResponseBody
    public Map<String, Object> searchFarmHandByPlanType() throws Exception {
        return getReturnMap(productionService.searchFarmHandByPlanType(getMap()));
    }

    @RequestMapping("/editNewPlan")
    @ResponseBody
    public Map<String, Object> editNewPlan() throws Exception {
        String planType = getString("planType");
        String planMonth = getString("planMonth");
        String planYear = getString("planYear");
        productionService.editNewPlan(planType, planMonth, planYear, getDetialListStr());
        return getReturnMap();
    }

    @RequestMapping("/searchPlanByALL")
    @ResponseBody
    public Map<String, Object> searchPlanByALL() throws Exception {
        return getReturnMap(productionService.searchPlanByALL(getMap()));

    }

    @RequestMapping("/searchHistoryByPlanType")
    @ResponseBody
    public Map<String, Object> searchHistoryByPlanType() throws Exception {
        return getReturnMap(productionService.searchHistoryByPlanType(getMap()));
    }

    // 废弃的方法 -- 运行没有问题,则在9月份删除
    // 生成明细数据
    @RequestMapping("/insertSAPDateBase")
    @ResponseBody
    public Map<String, Object> insertSAPDateBase(HttpServletRequest request) throws Exception {
        Long code = getLong("code");
        Object insertSAPDate = sendToSAPProductionService.insertSAPDateBase(code);
        return getReturnMap(insertSAPDate);
    }

    // 生成上传SAP数据(包含汇总-明细)
    @RequestMapping("/editToSapSummaryDetail")
    @ResponseBody
    public Map<String, Object> editToSapSummaryDetail() throws Exception {
        Long code = getLong("code");
        Object editToSapSummaryDetail = sendToSAPProductionService.editToSapSummaryDetail(code);
        cacheRefresh();
        return BuildReturnMapUtil.getReturnMap(editToSapSummaryDetail);
    }

    // 新增上传SAP记录
    @RequestMapping("/editInsertSendSap")
    @ResponseBody
    public Map<String, Object> editInsertSendSap(HttpServletRequest request) throws Exception {
        String name = getString("name");
        Map<String, Object> map = getMap();
        String json = String.valueOf(map.get(name));

        Boolean isSummery = Boolean.valueOf(getString("isSummery"));

        sendToSAPProductionService.editInsertSendSap(json, isSummery);
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 修改上传sap财务猪只数据限制
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editSendSAPHanaLimit")
    @ResponseBody
    public Map<String, Object> editSendSAPHanaLimit(HttpServletRequest request) throws Exception {
        sendToSAPProductionService.editSendSAPHanaLimit();
        return BuildReturnMapUtil.getReturnMap();
    };

    /**
     * @Description: 查询上传信息及限制状态
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSendSAPHana")
    @ResponseBody
    public Map<String, Object> searchSendSAPHana(HttpServletRequest request) throws Exception {
        return BuildReturnMapUtil.getReturnMap(sendToSAPProductionService.searchSendSAPHana());
    };

    @RequestMapping("/pigCheckLogo")
    @ResponseBody
    public Map<String, Object> pigCheckLogo(HttpServletRequest request) throws Exception {

        productionService.pigCheckLogo(getIds());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/pigCancelLogo")
    @ResponseBody
    public Map<String, Object> pigCancelLogo(HttpServletRequest request) throws Exception {

        productionService.pigCancelLogo(getIds());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/sendProductSaleToHana")
    @ResponseBody
    public Map<String, Object> sendProductSaleToHana(HttpServletRequest request) throws Exception {
        productionService.sendProductSaleToHana();
        return getReturnMap();
    }

    @RequestMapping("/sendSaleBillTotal2100")
    @ResponseBody
    public Map<String, Object> sendSaleBillTotal2100(HttpServletRequest request) throws Exception {
        productionService.sendSaleBillTotal();
        return getReturnMap();
    }

    @RequestMapping("/sendPurchaseToHanaTotal2110")
    @ResponseBody
    public Map<String, Object> sendPurchaseToHanaTotal2110(HttpServletRequest request) throws Exception {
        productionService.sendPurchaseToHanaTotal();
        return getReturnMap();
    }

    /**
     * @Description: 查询生产数据汇总
     * @author yangy
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchProduceDataCollectPage")
    @ResponseBody
    public Map<String, Object> searchProduceDataCollectPage(HttpServletRequest request) throws Exception {
        String searchReportType = getString("searchReportType");
        String startDate = getString("startDate");
        String endDate = getString("endDate");
        String version = getString("version");
        String isHis = getString("isHis");
        BasePageInfo pageInfo = productionService.searchProduceDataCollectPage(searchReportType, startDate, endDate, version, isHis);
        return getReturnMap(pageInfo);
    };

    /**
     * @Description: 查询生产数据汇总明细
     * @author yangy
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchProduceDataCollectToList")
    @ResponseBody
    public Map<String, Object> searchProduceDataCollectToList(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchProduceDataCollectToList(getString("rowId"), getString("searchType")));
    };

    /**
     * @Description: 填写备注信息
     * @author yangy
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editProduceDataCollectNotes")
    @ResponseBody
    public Map<String, Object> editProduceDataCollectNotes(HttpServletRequest request) throws Exception {
        productionService.editProduceDataCollectNotes(getMap());
        return BuildReturnMapUtil.getReturnMap();
    };

    /**
     * @Description: 生产数据汇总
     * @author yangy
     * @param entity
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editProduceDataCollect")
    @ResponseBody
    public Map<String, Object> editProduceDataCollect(HttpServletRequest request) throws Exception {
        productionService.editProduceDataCollect(getString("selectFarm"), getString("reportType"), getString("startDate"), getString("endDate"),
                getString("searchMonth"), getString("searchWeek"), getString("searchQuarter"), getString("searchYear"), "0");
        return getReturnMap();
    };

    /**
     * @Description: 精液管理主页分页查询
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping("/searchSpermToPage")
    @ResponseBody
    public Map<String, Object> searchSpermToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(productionService.searchSpermToPage(getMap()));

    }

    /**
     * @Description: 精液管理主页选中进入销售分页查询
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping("/searchSpermToSaleToList")
    @ResponseBody
    public Map<String, Object> searchSpermToSaleToList(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchSpermToSaleToList(getMap()));

    }

    /**
     * @Description: 精液管理查看
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSpermDetailToPage")
    @ResponseBody
    public Map<String, Object> searchSpermDetailToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(productionService.searchSpermDetailToPage(getMap()));
    }

    /**
     * @Description: 精液管理批量报废
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping("/editBatchScrapEdit")
    @ResponseBody
    public Map<String, Object> editBatchScrapEdit(HttpServletRequest request) throws Exception {
        String scrapReason = getString("scrapReason");
        productionService.editBatchScrapEdit(getMap());
        return getReturnMap();
    }

    /**
     * @Description: 精液管理批量反报废
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editBatchTrunOverScrapEdit")
    @ResponseBody
    public Map<String, Object> editBatchTrunOverScrapEdit(HttpServletRequest request) throws Exception {
        productionService.editBatchTrunOverScrapEdit(getMap());
        return getReturnMap();
    }

    /**
     * @Description: 精液管理精液批号详情
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSemenBatchNoDetailToList")
    @ResponseBody
    public Map<String, Object> searchSemenBatchNoDetailToList(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchSemenBatchNoDetailToList(getMap()));
    }

    /**
     * @Description: 报废)
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editScrapEdit")
    @ResponseBody
    public Map<String, Object> editScrapEdit(HttpServletRequest request) throws Exception {
        productionService.editScrapEdit(getMap());
        return getReturnMap();
    }

    /**
     * @Description: 反报废
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editTrunOverScrapEdit")
    @ResponseBody
    public Map<String, Object> editTrunOverScrapEdit(HttpServletRequest request) throws Exception {
        productionService.editTrunOverScrapEdit(getMap());
        return getReturnMap();
    }

    /**
     * @Description: 标签批量打印
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editBatchPrint")
    @ResponseBody
    public Map<String, Object> editBatchPrint(HttpServletRequest request) throws Exception {
        productionService.editBatchPrint(getMap());
        return getReturnMap();
    }

    /**
     * @Description:精液销售选择精液搜索
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectSpermSearchToPage")
    @ResponseBody
    public Map<String, Object> selectSpermSearchToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(productionService.selectSpermSearchToPage(getMap()));
    }

    /**
     * @Description:精液销售选择精液数量 显示可用精液
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectUsableSpermToList")
    @ResponseBody
    public Map<String, Object> selectUsableSpermToList(HttpServletRequest request) throws Exception {

        return getReturnMap(productionService.selectUsableSpermToList(getMap()));
    }

    /**
     * @Description: 查询所要入场的公猪（虚拟公猪入场）
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchXNpigToPage")
    @ResponseBody
    public Map<String, Object> searchXNpigToPage() throws Exception {
        return BuildReturnMapUtil.getReturnMap(productionService.searchXNpigToPage(getMap()));
    }

    /**
     * @Description: 查询场区（虚拟公猪入场）
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchXNFarm")
    @ResponseBody
    public Map<String, Object> searchXNFarm() throws Exception {
        return BuildReturnMapUtil.getReturnMap(productionService.searchXNFarm());
    }

    /**
     * @Description: 删除生产数据汇总记录
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteProductionCollect")
    @ResponseBody
    public Map<String, Object> deleteProductionCollect() throws Exception {
        productionService.deleteProductionCollect(getIds());
        return getReturnMap();
    }

    @RequestMapping("/editXNPigMoveIn")
    @ResponseBody
    public Map<String, Object> editXNPigMoveIn() throws Exception {
        productionService.editXNPigMoveIn(getBeanCom(ParaPigMoveInModel.class), getString("eventName"), getDetialListStr());
        cacheRefresh();
        return BuildReturnMapUtil.getReturnMap();
    }

    /**
     * @Description: sap生产猪销售
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendToHanaProductSellTotal3050")
    @ResponseBody
    public Map<String, Object> sendToHanaProductSellTotal3050() throws Exception {
        productionService.sendToHanaProductSellTotal();
        return BuildReturnMapUtil.getReturnMap();
    }

    /**
     * @Description: 判断是否可以上传至SAP财务系统
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchHanaLogSign")
    @ResponseBody
    public Map<String, Object> searchHanaLogSign() throws Exception {
        sendToSAPProductionService.searchHanaLogSign(Maps.getString(sendToSAPProductionService.getStartAndEndDate(), "endDate"));
        return BuildReturnMapUtil.getReturnMap();
    }

    /**
     * @Description: 查询上传sap记录明细
     * @author CB
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSendToHanaLogtDetail")
    @ResponseBody
    public Map<String, Object> searchSendToHanaLogtDetail(HttpServletRequest request) throws Exception {
        Integer tableCode = Maps.getInt(getMap(), "code");
        Long rowId = Maps.getLong(getMap(), "rowId");
        Object sendToHanaLogtDetail = sendToSAPProductionService.searchSendToHanaLogtDetail(tableCode, rowId);
        return BuildReturnMapUtil.getReturnMap(sendToHanaLogtDetail);
    }

    /**
     * @Description: 微信扫一扫选择精液
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSemenListById")
    @ResponseBody
    public Map<String, Object> searchSemenListById(HttpServletRequest request) throws Exception {
        return getReturnMap(productionService.searchSemenListById(getMap()));
    }

    /**
     * @Description: sap生产猪销售
     * @author Administrator
     * @return
     * @throws Exception
     */
    @RequestMapping("/sendToHanaProductPurchaseTotal3020")
    @ResponseBody
    public Map<String, Object> sendToHanaProductPurchaseTotal3020() throws Exception {
        productionService.sendToHanaProductPurchaseTotal3020();
        return BuildReturnMapUtil.getReturnMap();
    }
}
