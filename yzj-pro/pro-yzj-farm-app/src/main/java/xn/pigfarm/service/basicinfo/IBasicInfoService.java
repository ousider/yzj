package xn.pigfarm.service.basicinfo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.FarmBindModel;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.LineModel;
import xn.pigfarm.model.basicinfo.PigpenModel;

/**
 * @Description:基础信息服务接口
 * @author Zhangjc
 * @date 2016年4月25日 上午10:38:04
 */
public interface IBasicInfoService {

    /**
     * @Description:查询产线列表 不分页
     * @author Zhangjc
     * @throws Exception
     */
    public List<LineModel> searchLineToList() throws Exception;

    /**
     * @Description:查询产线列表 分页
     * @author Zhangjc
     * @throws Exception
     */
    public BasePageInfo searchLineToPage() throws Exception;

    /**
     * @Description: 编辑产线
     * @author Zhangjc
     * @param ppLineModel
     * @param codelist
     * @throws Exception
     */
    public void editLine(LineModel lineModel, String lineList) throws Exception;

    /**
     * @Description: 删除产线
     * @author Zhangjc
     * @param ids
     * @throws Exception
     */
    public void deleteLines(long[] ids) throws Exception;

    // =======================================
    /**
     * @Description:查询猪舍列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<HouseModel> searchHouseToList(String changeType, String houseType, long lineId, String eventName, String saleDescribe)
            throws Exception;

    // =======================================
    /**
     * @Description:查询猪舍列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<HouseModel> searchHouseDeptToList() throws Exception;

    /**
     * @Description:查询猪舍列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchHouseToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * *
     * 
     * @Description: 编辑猪舍
     * @author fangc
     * @param ppHouseModel
     * @param houselist
     * @throws Exception
     */
    public void editHouse(HouseModel houseModel, String houselist) throws Exception;

    /**
     * @Description: 删除猪舍
     * @author fangcj
     * @param ids
     * @throws Exception
     */
    public void deleteHouses(long[] ids) throws Exception;

    // =======================================
    /**
     * @Description:查询猪栏列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<PigpenModel> searchPigpenToList(long houseId, String eventName) throws Exception;

    /**
     * @Description:查询有效猪栏列表
     * @author fangc
     * @throws Exception
     */
    public List<PigpenModel> searchValidPigpenToList(String changeType, long houseId, long lineId, String eventName, String saleDescribe)
            throws Exception;

    /**
     * @Description:查询猪栏列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchPigpenToPage() throws Exception;

    /**
     * @Description: 删除猪栏
     * @author fangcj
     * @param ids
     * @throws Exception
     */
    public void deletePigpens(long[] ids) throws Exception;

    /**
     * 高级查询
     * 
     * @author THL
     * @param map
     * @return BasePageInfo
     * @throws Exception
     */
    public BasePageInfo saerchLineByConditionToPage(Map<String, Object> map) throws Exception;

    /**
     * 高级查询所用猪舍类型,添加了一个任意类别
     * 
     * @author THL
     * @return
     */
    public Object searchPigHouseToListForAdvancedSearch();

    /**
     * 高级查询：根据猪舍代码，猪舍名称，猪舍类型
     * 
     * @author THL
     * @param map
     * @return BasePageInfo
     */
    public BasePageInfo searchHouseToPageForAdvancedSearch(Map<String, Object> map) throws Exception;

    // =========================客户，供应商公共方法
    /**
     * @Description: 集团供应商客户搜索
     * @author zhangjs
     * @param cussupType
     * @return
     * @throws Exception
     */
    public BasePageInfo searchGroupCustomerAndSupplierToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 各厂供应商客户搜索
     * @author zhangjs
     * @param cussupType
     * @return
     * @throws Exception
     */
    public BasePageInfo searchCustomerAndSupplierToPage(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询供应商、客户下拉框
     * @author zhangjs
     * @param cussupType
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> searchCustomerAndSupplierToList(String cussupType) throws Exception;

    /**
     * @Description: 上传供应商和客户的临时文件
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    public List<String> editUploadCustomerAndSupplierTempFile(HttpServletRequest request) throws Exception;

    /**
     * @Description: 新增修改供应商、客户
     * @author THL
     * @param inputMap
     * @param relativePath
     * @param absolutePath
     * @param tempFilePath
     * @throws Exception
     */
    public void editCustomerAndSupplierToList(Map<String, Object> inputMap, String relativePath, String absolutePath, String tempFilePath)
            throws Exception;

    /**
     * @Description: 删除供应商、客户
     * @author zhangjs
     * @param map
     * @throws Exception
     */
    public void deleteCustomesrAndSuppliers(Map<String, Object> map) throws Exception;

    /**
     * @Description: 查询供应商客户信息
     * @author zhangjs
     * @param map
     * @return
     * @throws Exception
     */
    public List<CompanyModel> searchCusSupForCreate(String createType, String cussupType, String companyName) throws Exception;

    /**
     * @Description: 猪场帐号绑定
     * @author Administrator
     * @param inputMap
     * @throws Exception
     */
    public void editFarmBind(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description:查询绑定的账号
     * @author Administrator
     * @throws Exceptin
     */
    public List<FarmBindModel> searchFarmBind() throws Exception;

    /**
     * @Description: 删除绑定账号
     * @author Administrator
     * @throws Exception
     */
    public void deleteFarmBind(long ids[], List<String> isAsyncs) throws Exception;
    
    /**
     * @Description: 查询明细
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<FarmBindModel> searchFarmBindToList(long mainId) throws Exception;

    /**
     * @Description: 同步猪场
     * @author Administrator
     * @throws Exception
     */
    public void editSynchronizeFarm(List<String> farmIds, List<String> companyIds, List<String> companyCodes) throws Exception;

    /**
     * @Description: 根据公司猪舍id查询猪舍明细
     * @author Administrator
     * @return
     * @throws Exception
     */
    public BasePageInfo searchHousePigDetailedToPage(Long farmId, String houseId);
    
    /**
     * @Description: 根据公司猪舍id查询猪舍信息
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchHouseDetailedToPage(Long farmId, String houseId);
    
    /**
     * @Description: 查询销售结算单客户
     * @author Administrator
     * @return
     */
    public BasePageInfo searchSaleItemToPage();

    /**
     * @Description: 查询销售结算单客户 项目维护
     * @author Administrator
     * @param customerId
     * @return
     */
    public List<Map<String, String>> searchSaleItemDetailToList(Long customerId) throws Exception;

    /**
     * @Description: editSaleItem
     * @author Administrator
     * @throws Exception
     */
    public void editSaleItem(Long customerId, String detailList) throws Exception;

    /**
     * 人员管理 猪舍详情批量停用操作
     * 
     * @param ids
     * @throws Exception
     */
    public void editDisablesPigHouse(long[] ids, String stopDate) throws Exception;

    /**
     * 人员管理 猪舍详情批量启用操作
     * 
     * @param ids
     * @throws Exception
     */
    public void editEnablesPigHouse(long[] ids) throws Exception;

    /**
     * 查询证照信息
     * 
     * @param ids
     * @throws Exception
     */
    public List<Map<String, Object>> searchHistoryPaper(Map<String, Object> inputMap) throws Exception;

    /**
     * 查询账户信息
     * 
     * @param ids
     * @throws Exception
     */
    public List<Map<String, Object>> searchHistoryAccount(Map<String, Object> inputMap) throws Exception;

    /**
     * 查询联系方式
     * 
     * @param ids
     * @throws Exception
     */
    public List<Map<String, Object>> searchHistoryLink(Map<String, Object> inputMap) throws Exception;

    /**
     * 查询变更历史
     * 
     * @param ids
     * @throws Exception
     */
    public BasePageInfo searchChangeHistory(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 同步供应商、客户数据
     * @author zhangjs
     * @param companyModel
     * @param createType
     * @param isSaleAccount
     * @throws Exception
     */
    public void editCustomerAndSupplierToFarms(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 查询集团之下猪场
     * @author THL
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchGroupCompanyByFarmIdToList(Map<String, Object> inputMap) throws Exception;

    /**
     * @Description: 上级客户下拉
     * @author zhangjs
     * @param cussupType
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> searchHrCompanyClienteleBillToList(String cussupType) throws Exception;
}
