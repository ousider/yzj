package xn.pigfarm.service.production;

import java.util.List;
import java.util.Map;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.BoarObsoleteModel;
import xn.pigfarm.model.production.ParaPigMoveInModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.production.PorkCheckModel;
import xn.pigfarm.model.production.SearchSemenModel;
import xn.pigfarm.model.production.SearchValidPigModel;
import xn.pigfarm.model.production.SemenBillModel;
import xn.pigfarm.model.production.SwineryModel;

/**
 * @Description: 生产管理
 * @author fangc
 * @date 2016年4月25日 下午12:29:08
 */
public interface IProductionService {
    // 事件查询有效猪只
    public BasePageInfo editValidPigToPage(SearchValidPigModel searchValidPigModel) throws Exception;

    // 事件查询配种精液及公猪
    public BasePageInfo editSemenToList(SearchSemenModel searchSemenModel) throws Exception;

    // 事件查询肉猪数量
    public BasePageInfo searchPorkToList(String eventName, Long houseId, Long pigpenId, Long swineryId, Long breedId, String changeType)
            throws Exception;

    // 事件查询肉猪数量（猪栏）
    public BasePageInfo searchPorkToList2(String eventName, Long houseId, String pigpenId, Long swineryId, Long breedId, String changeType)
            throws Exception;

    // 猪只入场
    public void editPigMoveIn(ParaPigMoveInModel paraPigMoveInModel, String eventName, String pigMoveIn) throws Exception;

    // 换耳号
    public void editChangeEarBand(BillModel billModel, String eventName, String changeEarBandList) throws Exception;

    // 背膘测定
    public void editBackfat(BillModel billModel, String eventName, String backfatViewList) throws Exception;

    // 转舍
    public void editChangeLaborHouse(BillModel billModel, String eventName, String changeHouseViewList, String zcfxycbbFlag, String requiredPigpen)
            throws Exception;

    // 转群
    public void editChangeSwinery(BillModel billModel, String eventName, String changeSwineryViewList) throws Exception;

    // 转生产公猪
    public void editBoarReserveToProduct(BillModel billModel, String eventName, String boarReserveToProductList) throws Exception;

    // 采精
    public void editSemen(SemenBillModel semenBillModel, String eventName, String semenList) throws Exception;

    // 后备情期鉴定
    public void editPrepubertalCheck(BillModel billModel, String eventName, String breedViewList) throws Exception;

    // 配种
    public void editBreed(BillModel billModel, String eventName, String breedViewList) throws Exception;

    // 妊娠检查
    public void editPregnancyCheck(BillModel billModel, String eventName, String pregnancyCheckViewList) throws Exception;

    // 分娩
    public void editDelivery(BillModel billModel, String eventName, String deliveryViewList, String FMSFLZ_flag) throws Exception;

    // 仔猪死亡
    public void editChildPigDie(BillModel billModel, String eventName, String childPigDieViewList) throws Exception;

    // 寄养
    public void editFoster(BillModel billModel, String eventName, String fosterView) throws Exception;

    // 断奶
    public void editWean(BillModel billModel, String eventName, String weanViewList, String dnxycbbFlag, String autoFosterDieFlag) throws Exception;

    // 转保育
    public void editToChildCare(BillModel billModel, String eventName, String weanViewList) throws Exception;

    // 转育肥
    public void editToChildFatten(BillModel billModel, String eventName, String weanViewList) throws Exception;

    // 商品猪死亡
    public void editGoodPigDie(BillModel billModel, String eventName, String childPigDieViewList) throws Exception;

    // 商品猪销售
    public void editGoodPigSell(Map<String, Object> inputMap, String childPigDieViewList) throws Exception;

    // 种猪淘汰
    public void editObsolete(BillModel billModel, String eventName, String leaveList) throws Exception;

    // 离场
    public void editLeave(BillModel billModel, String eventName, String leaveViewList) throws Exception;

    // 种猪历史导入
    public void editInputPig() throws Exception;

    /**
     * @Description: 查询单据
     * @author Zhangjc
     * @param map
     * @return
     * @throws Exception
     */
    public BasePageInfo searchBillToPage(String businessCode, String createName, String billDate, String billDate2, String createDate,
            String createDate2, String eventCode) throws Exception;

    // 查询入场猪只明细
    public BasePageInfo searchPigMoveInToList(long billId) throws Exception;

    // =======================================
    /**
     * @Description:查询猪群列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<SwineryModel> searchSwineryToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:查询猪群列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchSwineryToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * *
     * 
     * @Description: 编辑猪群
     * @author fangc
     * @param ppHouseModel
     * @param houselist
     * @throws Exception
     */
    public void editSwinery(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 删除猪群
     * @author fangcj
     * @param ids
     * @throws Exception
     */
    public void deleteSwinerys(long[] ids) throws Exception;

    // ======================================= 猪只
    /**
     * @Description:查询猪只列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<PigModel> searchPigToList(String pigType) throws Exception;

    /**
     * @Description:查询猪只列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchPigToPage(Map<String, Object> map) throws Exception;

    /**
     * *
     * 
     * @Description: 编辑猪只
     * @author fangc
     * @param ppPigModel
     * @throws Exception
     */
    public void editPig(PigModel pigModel) throws Exception;

    /**
     * @Description: 删除猪群
     * @author fangcj
     * @param ids
     * @throws Exception
     */
    public void deletePigs(long[] ids) throws Exception;

    /**
     * @Description: 查询淘汰的单据（审批）
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<BillModel> searchObsoleteBill() throws Exception;

    /**
     * @Description: 种猪淘汰申请（审批页面）
     * @author wch
     * @param billId
     * @return
     * @throws Exception
     */
    public BasePageInfo searchBoarObsolete(Long billId) throws Exception;

    /**
     * @Description: 审批
     * @author wch
     * @param ids
     * @param status
     * @param billId
     * @param pigIds
     * @throws Exception
     */
    public void editBoarObsolete(Map<String, Object> map) throws Exception;
    // public void editBoarObsolete(long ids[], String status, Long billId, List<String> pigIdsList) throws Exception;

    /**
     * @Description: 选择审批通过的种猪
     * @author wch
     * @return
     * @throws Exception
     */
    public List<BoarObsoleteModel> searchBoarObsoletePass(String pigids) throws Exception;

    /**
     * @Description: 得到背标分数
     * @author Administrator
     * @param backfat
     * @return
     */
    public Map<String, String> searchBackfatScoreMap() throws Exception;

    /**
     * @Description: 关闭猪群
     * @author Administrator
     * @throws Exception
     */
    public void editCloseSwinery(long[] ids) throws Exception;

    /**
     * @Description: 开启猪群
     * @author Administrator
     * @throws Exception
     */
    public void editOpenSwinery(long[] ids) throws Exception;

    /**
     * @Description: 奶妈转舍
     * @author Administrator
     * @param billModel
     * @param eventName
     * @param changeHouseViewList
     * @param zcfxycbbFlag
     * @throws Exception
     */
    public void editNurseChangeHouse(BillModel billModel, String eventName, String changeHouseViewList, String zcfxycbbFlag) throws Exception;

    /**
     * @Description: 查询栏位猪只
     * @author Administrator
     * @param pigpenId
     * @throws Exception
     */
    public List<Map<String, String>> searchValidPigByPigpenId(Long pigpenId) throws Exception;

    /**
     * @Description: 查询肉猪盘点bill数据
     * @param beanBaseModel
     * @return
     */
    public BasePageInfo searchPorkCountBillList(String billId, String createDate, String checkOrgan, String accountUser) throws Exception;

    /**
     * @Description: 查询肉猪盘点数据
     * @param beanBaseModel
     * @return
     */
    public List<PorkCheckModel> searchPorkPigCountList(String houseTypeIds, String billId, String billDate) throws Exception;

    /**
     * @Description: 保存肉猪盘点数据
     * @param beanBaseModel
     * @return
     */
    public void editPorkPigCountList(List<PorkCheckModel> porkCheckModels, String eventName, String billDate, String accountUser, String checkOrgan,
            String notes, String checkType) throws Exception;

    /**
     * @Description: 选择相关销售单据
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectSellBill(String pigType) throws Exception;

    /**
     * @Description: 根据单据ID查询未入场猪只信息
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectPigListByBillId(Long billId, String pigType) throws Exception;

    /**
     * @Description: 根据公司查询存栏
     * @author Administrator
     * @return
     * @throws Exception
     */

    public List<Map<String, Object>> searchLivestockByProduceToList(String farmId) throws Exception;

    /**
     * @Description: 根据公司查询生产动态
     * @author Administrator
     * @return
     * @throws Exception
     */

    public List<Map<String, Object>> searchProduceChangeToList(String farmId, String dateType) throws Exception;

    /**
     * @Description: 根据公司查询绩效对比
     * @author Administrator
     * @return
     * @throws Exception
     */

    public List<Map<String, Object>> searchPerformanceComparisonToList(String farmId, String dateContrast) throws Exception;

    /**
     * @Description: 保存精液销售数据
     * @author Administrator
     * @return
     * @throws Exception
     */

    public void editSaleSemenList(BillModel billModel, String saleSemenList, String customerId) throws Exception;

    /**
     * @Description: 选择相关精液单据
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> editSemenBill() throws Exception;

    /**
     * @Description: 选择精液单据明细
     * @author Administrator
     * @param billId
     * @param pigType
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> editSemenListByBillId(Long billId) throws Exception;

    /**
     * @Description: 根据猪只ID获取耳牌号
     * @author Cress
     * @param billId
     * @return
     * @throws Exception
     */
    public String getEarBrandByPigId(Long pigId) throws Exception;

    /**
     * @Description: 根据计划查询猪场存栏
     * @param pigId
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFarmHandByPlanType(Map<String, Object> inputMap) throws Exception;

    /**
     * s
     * 
     * @Description: 保存销售计划
     * @param farmId
     * @return
     * @throws Exception
     */
    public void editNewPlan(String planType, String planMonth, String planYear, String detialListStr) throws Exception;

    /**
     * @Description: 展示销售计划
     * @param farmId
     * @return
     * @throws Exception
     */
    public BasePageInfo searchPlanByALL(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 根据计划年月周查询猪场预测
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchHistoryByPlanType(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 生产数据汇总
     * @author Administrator
     * @param runType 0:个人run 1：系统定时跑
     * @throws Exception
     */
    public void editProduceDataCollect(String selectFarm, String reportType, String startDate, String endDate, String searchMonth, String searchWeek,
            String searchQuarter, String searchYear, String runType) throws Exception;

    /**
     * 猪只管理 猪只详情批量标识操作
     * 
     * @param ids
     * @throws Exception
     */
    public void pigCheckLogo(long[] ids) throws Exception;

    /**
     * 猪只管理 猪只详情批量取消标识标识操作
     * 
     * @param ids
     * @throws Exception
     */
    public void pigCancelLogo(long[] ids) throws Exception;

    /**
     * @Description: 上传sap 生产猪
     * @author Administrator
     * @throws Exception
     */
    public void sendProductSaleToHana() throws Exception;

    /**
     * @Description: 上传销售常规和内销
     * @author Administrator
     * @throws Exception
     */
    public void sendSaleBillTotal() throws Exception;

    /**
     * @Description: 内部销售单
     * @author Administrator
     * @throws Exception
     */
    public void sendPurchaseToHanaTotal() throws Exception;

    /**
     * @Description: 查询生产数据汇总
     * @author Administrator
     * @throws Exception
     */
    public BasePageInfo searchProduceDataCollectPage(String searchReportType, String startDate, String endDate, String version, String isHis)
            throws Exception;

    /**
     * @Description: 查询生产数据汇总
     * @author Administrator
     * @throws Exception
     */
    public List<Map<String, Object>> searchProduceDataCollectToList(String rowId, String searchType) throws Exception;

    /**
     * @Description: 填写备注信息
     * @author Administrator
     * @throws Exception
     */
    public void editProduceDataCollectNotes(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询上传信息及限制状态
     * @author 程彬
     * @return
     */
    public BasePageInfo searchSendSAPHana();

    /**
     * @Description: 精液管理分页查询
     * @author ZJ
     * @param map
     * @return
     */
    public BasePageInfo searchSpermToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description: 精液管理详情查看
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public BasePageInfo searchSpermDetailToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description: 精液管理批量反报废
     * @author ZJ
     * @param map
     */

    public void editBatchTrunOverScrapEdit(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:精液管理批量报废
     * @author ZJ
     * @param ids
     * @param scrapReason
     * @throws Exception
     */
    public void editBatchScrapEdit(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 精液批号详情
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSemenBatchNoDetailToList(Map<String, Object> map) throws Exception;

    /**
     * @Description:报废
     * @author ZJ
     * @param ids
     * @param scrapReason
     * @throws Exception
     */
    public void editScrapEdit(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 反报废
     * @author ZJ
     * @param ids
     * @throws Exception
     */
    public void editTrunOverScrapEdit(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 精液销售选择精液搜索
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public BasePageInfo selectSpermSearchToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description: 精液销售选择精液数量 显示可用精液
     * @author THL
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectUsableSpermToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询所要入场的虚拟公猪
     * @author Administrator
     * @return
     * @throws Exception
     */
    public BasePageInfo searchXNpigToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询厂区（虚拟公猪入场）
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> searchXNFarm() throws Exception;

    /**
     * @Description: 删除生产数据汇总记录
     * @author Administrator
     * @return
     * @throws Exception
     */
    public void deleteProductionCollect(long[] ids) throws Exception;

    /**
     * @Description:虚拟公猪入场
     * @author Administrator
     * @param beanCom
     * @param string
     * @param detialListStr
     * @throws Exception
     */
    public void editXNPigMoveIn(ParaPigMoveInModel beanCom, String string, String detialListStr) throws Exception;

    /**
     * @Description: 精液批量打印
     * @author ZJ
     * @param map
     * @throws Exception
     */
    public void editBatchPrint(Map<String, Object> map) throws Exception;

    public void sendToHanaProductSellTotal() throws Exception;

    public int editSendSAPHanaLimit() throws Exception;

    /**
     * @Description: 采精事件仓库默认填写精液仓库
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSpermWarehouseName(Map<String, Object> map) throws Exception;

    /**
     * @Description: 精液销售选择搜索
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSpermToSaleToList(Map<String, Object> map) throws Exception;

    // 根据猪场ID和时间获取生产数据报表ROW_ID
    public long searchReportIdByFarmIdAndDate(Map<String, Object> map) throws Exception;

    /**
     * @Description: 用于微信扫一扫选择精液
     * @author Cress
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSemenListById(Map<String, Object> map) throws Exception;

    /**
     * @Description: 手动传sap，生产猪采购（3020）
     * @author Administrator
     */
    public void sendToHanaProductPurchaseTotal3020() throws Exception;

}
