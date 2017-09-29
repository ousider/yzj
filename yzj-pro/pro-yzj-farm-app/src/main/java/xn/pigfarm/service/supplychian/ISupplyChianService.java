package xn.pigfarm.service.supplychian;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.supplychian.ReplaceAndPackageDetailModel;
import xn.pigfarm.model.supplychian.StoreMaterialModel;
import xn.pigfarm.model.supplychian.WarehouseMaterialTypeModel;
import xn.pigfarm.model.supplychian.WarehouseModel;

/**
 * @Description: 基础信息服务接口 物料主数据
 * @author THL
 * @date 2016年5月3日 下午3:08:13
 */
public interface ISupplyChianService {

    /**
     * @Description: 临时导入
     * @author THL
     */
    public void editInsertMaterial() throws Exception;

    /**
     * @Description: 供应链人员
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<EmployeeModel> searchEmployeeByIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索出来兄弟公司
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<CompanyModel> searchBrotherCompanyToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索出来需求单中的供应商
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchCompanyFromRequireBillToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:查询能够选择的物料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchSelectMaterialListTableToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询需求单中的物料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchMaterialFromRequireTableToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询需求单中的统筹明细（数量，备注）
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchMaterialFromRequireTableDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询采购单中的物料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchMaterialFromPurchaseTableToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询仓库中存料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchWarehouseMaterialToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询仓库中存料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchWarehouseMaterialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询各种出库单据
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchSupplychianBillToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询符合的仓库
     * @author THL
     * @return
     * @throws Exception
     */
    public List<WarehouseModel> searchWarehouseByMaterialTypeToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询符合的调入(与调出仓库，物料类型具有交集)仓库
     * @author THL
     * @return
     * @throws Exception
     */
    public List<WarehouseModel> searchAllotWareHouseToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 将所选物料信息转化为指定猪场的物料信息（某个猪场同步后的物料信息）
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> searchTurnMaterialToFarmMaterialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 拆分物料
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editSplitMaterial(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 合并物料
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editMergeMaterial(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:查询主数据列表 分页
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchMaterialToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询集团之下带仓库的猪场
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchCompanyByFarmIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步更新猪场的主数据
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editMaterialToFarms(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 修改物料的明细（同步到猪场）
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editUpdateMaterialDetailToFarms(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询日常用料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchDailyRecordToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 增加,编辑日常用料
     * @author THL
     * @param editType
     * @param gridListJson
     * @throws Exception
     */
    public void editDailyRecord(String editType, String gridListJson) throws Exception;

    /**
     * @Description: 删除日常用料
     * @author THL
     * @param ids
     * @throws Exception
     */
    public void deleteDailyRecord(long[] ids) throws Exception;

    /**
     * @Description: 统计用料页面查看明细
     * @author THL
     * @param ids
     * @throws Exception
     */
    public List<Map<String, Object>> searchDailyRecordDetailToList(long[] ids) throws Exception;

    /**
     * @Description: 统计用料页面查看明细
     * @author THL
     * @param ids
     * @throws Exception
     */
    public void editWarehouseEnough(long[] ids) throws Exception;

    /**
     * @Description: 查询需求单
     * @author THL
     * @return
     * @throws Exception
     */
    public BasePageInfo searchRequireStoreToPage() throws Exception;

    /**
     * @Description: 查询需求单明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<StoreMaterialModel> searchRequireStoreDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 获得核算单位
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchAccountsUnit() throws Exception;

    /**
     * @Description: 增加,编辑需求单
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editRequireStore(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 作废需求单
     * @author THL
     * @param ids
     * @param inputMap
     * @throws Exception
     */
    public void editScrapRequireStore(long[] ids, Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 提交需求单
     * @author THL
     * @param ids
     * @param inputMap
     * @throws Exception
     */
    public void editSubmitRequireStore(long[] ids) throws Exception;

    /**
     * @Description: 判断登陆的角色可以做什么采购
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchPurchaseTypeByEmployeeIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询集团采购订单
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchGroupPurchaseStoreToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询采购单入库具体情况
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchInputInfoFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询采购单入库中的明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchInputingEditFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询采购订单明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchPurchaseStoreDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 添加,编辑集团采购订单（待审批，审批未通过）
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editGroupPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 作废集团采购订单
     * @author THL
     * @param ids
     * @param inputMap
     * @throws Exception
     */
    public void editScrapGroupPurchaseStore(long[] ids, Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 完结采购订单
     * @author THL
     * @param ids
     * @throws Exception
     */
    public void editOverGroupPurchaseStore(long[] ids) throws Exception;

    /**
     * @Description: 查询猪场自购采购订单
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchSelfPurchaseStoreToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 添加,编辑猪场自购采购订单（待审批，审批未通过）
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editSelfPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 作废猪场自购采购订单
     * @author THL
     * @param ids
     * @param inputMap
     */
    public void editScrapSelfPurchaseStore(long[] ids, Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 添加,编辑猪场采购订单（审批通过，入库中，完成入库）
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editInputingPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 标志采购订单彻底完成，无法做任何修改
     * @author THL
     * @param map
     * @throws Exception
     */
    public void editFinishPurchaseStore(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 判断这个供应商是否含有这个类型的主数据
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSupplierHaveMaterialTypeToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索没有生成订单最开始的日期
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFeedStartDateToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索已入库但是未生成订单的
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchMaterialFeedFromInputingToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询入库单
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchInputStoreToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询入库单据明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchInputBillDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询可入库订单明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchInputFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询同一个物料组数据
     * @author THL
     * @param map
     * @return
     */
    public List<MaterialModel> searchMaterialByGroupIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 订单入库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editMaterialInput(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 搜索需要提交的明细
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public List<Map<String, Object>> searchMaterialInputCommitDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 确认提交入库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editMaterialInputCommit(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 报废入库单
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editMaterialInputScrap(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 精液入库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editSpermInput(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 查询各种出库单据明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchSupplychianBillDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 退货入库
     * @author THL
     * @param inputMap
     * @param detialListStr
     * @throws Exception
     */
    public void editReturnInput(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 反报废
     * @author THL
     * @param inputMap
     * @param detialListStr
     * @throws Exception
     */
    public void editTrunOverScrap(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 调拨入库
     * @author THL
     * @param inputMap
     * @param detialListStr
     * @throws Exception
     */
    public void editAllotInput(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 非订单入库
     * @author THL
     * @param inputMap
     * @param detialListStr
     * @throws Exception
     */
    public void editNotBillInput(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 物料领用 猪舍控件
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public List<Map<String, Object>> searchHouseToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 物料领用 自动获取猪舍
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public List<Map<String, Object>> searchHouseHavePigToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 物料领用 自动获取猪舍、猪群
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public List<Map<String, Object>> searchSwineryHavePigToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 物料领用 搜索登陆人员的部门
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public List<Map<String, Object>> searchDeptToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询出库单
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchOutputStoreToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询出库单明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchOutputBillDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 领料出库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editOutputUse(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 报废出库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editOutputScrap(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 采购退货订单
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchWareHouseMaterialFromPurchaseTableToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 采购退货采购订单明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchOutputFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 采购退货
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editOutputPurchase(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 调拨出库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editOutputAllot(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 仓库明细
     * @author YY
     * @throws Exception
     */
    public BasePageInfo searchWarehouseSetting() throws Exception;

    /**
     * @Description: 仓库管理页面角色选择
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchRoleByFarmIdToList() throws Exception;

    /**
     * @Description: 仓库明细新增
     * @author YY
     * @param inputMap
     * @throws Exception
     */
    public void editWarehouseSetting(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 删除仓位
     * @author YY
     * @param id
     * @throws Exception
     */
    public void deleteWarehouseSetting(long[] ids) throws Exception;

    /**
     * @Description: 禁用仓位
     * @author YY
     * @param id
     * @throws Exception
     */
    public void editForbiddenWarehouseSetting(long[] ids) throws Exception;

    /**
     * @Description: 启用仓位
     * @author YY
     * @param id
     * @throws Exception
     */
    public void editUseWarehouseSetting(long[] ids) throws Exception;

    /**
     * @Description: 查询仓位
     * @author YY
     * @param id
     * @throws Exception
     */
    public List<WarehouseModel> searchWarehouseToList() throws Exception;
    
    /**
     * @Description: 根据仓库查询物料
     * @author YY
     * @param id
     * @throws Exception
     */
    public List<WarehouseMaterialTypeModel> searchMaterialTypeByWarehouseToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 保存仓库物料信息
     * @author YY
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editWarehouseStoreMaterial(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 修改主数据默认仓库
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editDefaultHouse(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 仓管发料
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchOutputUseToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查看有多少种有效期和其数量
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchMaterialByMaterialIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查看有多少种有效期和其数量
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGiveOutMaterialByMaterialIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 仓管确认发料的详情
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editGiveOutTruePrepared(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 确认领用
     * @author THL
     * @param ids
     */
    public void editGiveOutTrue(long[] ids) throws Exception;

    /**
     * @Description: 未领用
     * @author THL
     * @param ids
     */
    public void editGiveOutFalse(long[] ids) throws Exception;

    /**
     * @Description:查找出库领用明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchOutputUseDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:领用明细确认领用
     * @author THL
     * @param inputMap
     * @param gridListString
     * @return
     * @throws Exception
     */
    public void editGiveOutDetailTrue(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description:领用明细确认未领用
     * @author THL
     * @param inputMap
     * @param gridListString
     * @return
     * @throws Exception
     */
    public void editGiveOutDetailFalse(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 查询公司的部门树 ,并且去除endDepartId下面的树
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDepTree(Long farmId, Long endDepartId) throws Exception;

    /**
     * @Description: 查看套餐及赠料
     * @author THL
     * @return
     * @throws Exception
     */
    public BasePageInfo searchReplaceAndPackageToPage() throws Exception;

    /**
     * @Description: 查看套餐及赠料明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<ReplaceAndPackageDetailModel> searchReplaceAndPackageDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 新增、修改套餐及赠料
     * @author THL
     * @param map
     * @param detialListStr
     */
    public void editReplaceAndPackage(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 删除套餐及赠料
     * @author THL
     * @param map
     * @param detialListStr
     */
    public void deleteReplaceAndPackage(long[] ids) throws Exception;

    /**
     * @Description: 选择互相替换物料
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchReplaceMaterialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找固定赠料
     * @author THL
     * @return
     * @throws Exception
     */

    public List<Map<String, Object>> searchFixedFreeMaterialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:查找固定套餐
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFixedGroupMaterialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:查找固定领用物料
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchFixedUseMaterialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找填写发票主页面
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchReceiptBillToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找需要填写发票的供应商
     * @author THL
     * @param inputMap
     * @return
     */
    public List<Map<String, Object>> searchSupplierForReceiptBillToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找需要填写发票的法人公司
     * @author THL
     * @param inputMap
     * @return
     */
    public List<Map<String, Object>> searchBranchIdForReceiptBillToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找需要填写发票的猪场
     * @author THL
     * @param inputMap
     * @return
     */
    public List<Map<String, Object>> searchFarmIdForReceiptBillToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找发票明细
     * @author THL
     * @param inputMap
     * @return
     */
    public List<Map<String, Object>> searchInvoiceTableDetialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找发票关联明细
     * @author THL
     * @param inputMap
     * @return
     */
    public List<Map<String, Object>> searchListTableDetialToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查找入库的明细
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchReceiptFillInInputDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 上传发票临时文件
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    public List<String> editReceiptTempFile(HttpServletRequest request) throws Exception;

    /**
     * @Description: 上传发票和信息
     * @author THL
     * @param inputMap
     * @param relativePath
     * @param absolutePath
     * @param tempFilePath
     * @throws Exception
     */
    public void editReceiptFillIn(Map<String, Object> inputMap, String relativePath, String absolutePath, String tempFilePath) throws Exception;

    /**
     * @Description: 按发票明细查询发票
     * @author THL
     * @param map
     * @return
     */
    public BasePageInfo searchReceiptDetailListToPage(Map<String, Object> inputMap) throws Exception;


    /**
     * @Description: 按物料明细查询发票
     * @author THL
     * @param map
     * @return
     */
    public BasePageInfo searchReceiptMaterialDetailListToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 发票查询高级搜索 公司
     * @author THL
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchCompanyToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询盘存的物料和数量
     * @author THL
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchMonthAccountToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 物料期末盘存
     * @author THL
     * @param gridListJson
     * @param map
     * @return
     */
    public void editMonthAccount(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 查询大丰模式
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchDafengModelFlag() throws Exception;

    /**
     * @Description: 更新单价主表搜索
     * @author ZJ
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchUpdatePriceToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 搜索更新单价的物料
     * @author THL
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchEditUpdatePriceDetailToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 更新单价供应商
     * @author THL
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchUpdatePriceSupplierToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 更新单价
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public void editUpdatePrice(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 查询添加药领用猪场
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<CompanyModel> searchAdditiveUseMaterialFarmToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 添加药领用
     * @author HXJ
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editAdditiveUseMaterial(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description: 查询实验室领用猪场
     * @author THL
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<CompanyModel> searchLaboratoryUseMaterialFarmToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 实验室领用
     * @author THL
     * @param inputMap
     * @param gridListString
     * @throws Exception
     */
    public void editLaboratoryUseMaterial(Map<String, Object> inputMap, String gridListString) throws Exception;

    /**
     * @Description:更新单价详情
     * @author ZJ
     * @param inputMap
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchUpdatePriceDetailToList(String supplierId, String startDateEndDate) throws Exception;

    /****************** 导入功能 **********************/
    /**
     * @Description: 把物料主数据 导入到SAP中
     * @author THL
     * @param map
     */
    public void editMaterialInfoToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 把物料主数据供应商 导入到SAP中
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editMaterialSupplierToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 把采购订单 导入到SAP中
     * @author THL
     * @param inputMap
     * @throws Exception
     */
    public void editMaterialPurchaseBillToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步期初到SAP中，传入参数期末END_DATE，场区的COMPANY_CODE
     * @author THL
     * @param map
     * @throws Exception
     */
    public void editMaterialToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步入库到SAP中，传入参数BILL_CODE，场区的COMPANY_CODE
     * @author THL
     * @param map
     * @throws Exception
     */
    public void editMaterialInputBillToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步出库领用到SAP中，传入参数BILL_CODE，场区的COMPANY_CODE
     * @author THL
     * @param map
     * @throws Exception
     */
    public void editMaterialOutputUseBillToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步调拨到SAP中，传入参数BILL_CODE，场区的COMPANY_CODE
     * @author THL
     * @param map
     */
    public void editMaterialOutputAllotInputBillToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步猪舍到SAP
     * @author THL
     * @param map
     */
    public void editHouseToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步客户到SAP
     * @author THL
     * @param map
     */
    public void editCustomerToHANA(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步母猪转生产到SAP
     * @author THL
     * @param map
     * @throws Exception
     */
    public void editSowToProductToHANA(Map<String, Object> map) throws Exception;

    /**
     * @Description: 同步公猪转生产到SAP
     * @author THL
     * @param map
     * @throws Exception
     */
    public void editBoarToProductToHANA(Map<String, Object> map) throws Exception;

}
