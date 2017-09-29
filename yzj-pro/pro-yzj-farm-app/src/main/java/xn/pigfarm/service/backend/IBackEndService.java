package xn.pigfarm.service.backend;

import java.util.List;
import java.util.Map;

import xn.core.model.system.BcodeModel;
import xn.core.model.system.BcodeTypeModel;
import xn.core.model.system.BcodeView;
import xn.core.model.system.QuickMenusModel;
import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.backend.BreedModel;
import xn.pigfarm.model.backend.CdButtonModel;
import xn.pigfarm.model.backend.CdCodeListModel;
import xn.pigfarm.model.backend.CdModuleModel;
import xn.pigfarm.model.backend.CodeListModel;
import xn.pigfarm.model.backend.MonthPerformanceModel;
import xn.pigfarm.model.backend.MonthPerformanceModuleModel;
import xn.pigfarm.model.backend.PartsModel;
import xn.pigfarm.model.backend.PigClassModel;
import xn.pigfarm.model.backend.PigHouseModel;
import xn.pigfarm.model.backend.SurvivalTargetModel;
import xn.pigfarm.model.backend.SysUpdateLogModel;
import xn.pigfarm.model.backend.TemplateModel;
import xn.pigfarm.model.backend.WeekModel;
import xn.pigfarm.model.basicinfo.IndicatorModel;
import xn.pigfarm.model.basicinfo.SettingModuleModel;

/**
 * @Description:B
 * @author li.zhou
 * @date 2016年4月13日 下午6:54:46
 */
public interface IBackEndService {

	/**
     * @Description: CdCode主页分页查询
     * @author Zhangjc
     * @param id
     * @return
     * @throws Exception
     */
    public BasePageInfo searchCdCodeListToPage(String typeCode, String typeName) throws Exception;

	/**
     * @Description: CodeList主页查询
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    public List<CodeListModel> searchCodeListToList() throws Exception;

	/**
     * @Description: CdCode删除
     * @author Zhangjc
     * @param cdCodeTypeModel
     * @param codelist
     * @throws Exception
     */
	// public void deleteCdCode(long[] ids) throws Exception;
	public void deleteCdCode(String[] typeCodes) throws Exception;

	/**
     * @Description:CdCode表明细查询
     * @author Zhangjc
     * @param mainId
     * @return
     */
	public List<CodeListModel> searchCdCodeDetailToList(String typeCode) throws Exception;

	/**
     * @Description: 根据TypeCode查询CdCode
     * @author Zhangjc
     * @param typeCode
     * @param linkValue
     * @return
     * @throws Exception
     */
	public List<CdCodeListModel> searchCdCodeByTypeCode(String typeCode, String linkValue) throws Exception;

	/**
     * @Description: 查询导航栏
     * @author Zhangjc
     * @param companyId
     * @param employeeId
     * @return
     * @throws Exception
     */
	public List<CdModuleModel> searchMenuByUserId() throws Exception;

	/**
     * @Description: 系统菜单查询明细表
     * @author Zhangjc
     * @param mainId
     * @return
     */
	public List<CdButtonModel> searchCdModuelDetailToList(long mainId) throws Exception;

	/**
     * @Description: 查询系统查询 分页
     * @author 程彬
     * @return
     * @throws Exception
     */
    public BasePageInfo searchCdModuelToPage(String moduleName, String component, String usingFlag) throws Exception;

	/**
     * @Description: 查询系统查询 不分页
     * @author Zhangjc
     * @return
     */
	public List<CdModuleModel> searchCdModuelToList();

	/**
     * @Description: 系统菜单编辑
     * @author Zhangjc
     * @param cdModuleModel
     * @param buttonList
     * @throws Exception
     */
	public void editCdModuel(CdModuleModel cdModuleModel, String buttonList) throws Exception;

	/**
     * @Description: 系统菜单删除
     * @author Zhangjc
     * @return
     */
	public void deleteCdModuels(long[] ids) throws Exception;

    /**
     * 系统设置项:根据公司编号查询
     * 
     * @param CompanyId
     * @return
     */
    public List<Map<String, Object>> searchSettingModuleToList() throws Exception;

    // /**
    // * 系统设置项:根据公司编号查询
    // *
    // * @param CompanyId
    // * @return
    // */
    // public List<CdSettingModuleModel> searchCdMsettingModuleToList(long CompanyId) throws Exception;

	/**
     * 系统设置项:根据公司编号查询 分页
     * 
     * @param CompanyId
     * @return
     */
    public BasePageInfo searchCdMsettingModuleByPage(String settingModule, String settingName, String settingCode, String settingValue,
            String sowType) throws Exception;

	/**
     * 系统设置项: CUD操作
     * 
     * @param entity
     * @param item
     */
    public void editCdSetting(SettingModuleModel entity, long[] ids, String jsonObj) throws Exception;

    /**
     * @Description:系统设置：恢复默认
     * @author Administrator
     * @param farmId
     * @throws Exception
     */
    public void deleteSetting(long farmId) throws Exception;
	
    /**
     * @Description:查询建模信息分页
     * @author Cress
     * @return
     * @throws Exception
     */
    public BasePageInfo searchCdTemplateToPage(String templateName) throws Exception;

	/**
     * @Description:根据模板Id获取建模模板
     * @author Cress
     * @param templateId
     * @return
     */
	public List<Map<String, Object>> searchModuleByTemplateId(long templateId) throws Exception;

	/**
     * @Description:根据Id排除菜单
     * @author Cress
     * @param ids
     * @return
     * @throws Exception
     */
	public List<CdModuleModel> searchModuleExcludeById(long[] ids) throws Exception;

	// =======================================
	/**
     * @Description:查询猪群列表 不分页
     * @author fangc
     * @throws Exception
     */
	public List<PigClassModel> searchPigClassToList(String pigType) throws Exception;

	/**
     * @Description:查询猪群列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchPigClassToPage(String businessCode, String pigClassName, String pigType) throws Exception;

	/**
     * *
     * 
     * @Description: 编辑猪群
     * @author fangc
     * @param ppHouseModel
     * @param houselist
     * @throws Exception
     */
	public void editPigClass(PigClassModel pigClassModel) throws Exception;

	/**
     * @Description:查询指标库列表 不分页
     * @return
     * @throws Exception
     */
	public List<IndicatorModel> searchIndicatorToList() throws Exception;

	/**
     * @Description: 查询客户指标库列表 不分页
     * @author Cress
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchIndicatorCustToList(Map<String, Object> inputMap) throws Exception;

	/**
     * @Description:查询指标库列表 分页
     * @return
     * @throws Exception
     */
    public BasePageInfo searchIndicatorToPage(String businessCode, String indName, String standardValue, String minValue, String maxValue,
            String unit) throws Exception;

	/**
     * @Description:编辑指标库
     * @param ppIndicatorModel
     * @param indicator
     * @throws Exception
     */
	public void editIndicator(IndicatorModel indicatorModel) throws Exception;

	/**
     * @Description: 客户指标库编辑
     * @author Cress
     * @param indicatorList
     * @throws Exception
     */
	public void editIndicatorCust(List<IndicatorModel> indicatorList, long[] ids) throws Exception;

	/**
     * @Description:客户指标库恢复
     * @author Cress
     * @param ids
     * @throws Exception
     */
	public void recoverIndicatorCust(long[] ids) throws Exception;

	/**
     * @Description:删除指标
     * @param ids
     * @throws Exception
     */
	public void deleteIndicators(long[] ids) throws Exception;

	/**
     * @Description:根据模板Id获取建模模板
     * @author Cress
     * @param templateId
     * @return
     * @Description: 删除猪群
     * @author fangcj
     * @param ids
     * @throws Exception
     */
	public void deletePigClass(long[] ids) throws Exception;

	// =======================================
	/**
     * @Description:查询品种列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<BreedModel> searchBreedToList(String breedCode, String breedName) throws Exception;

	/**
     * @Description:查询品种列表 分页
     * @author fangc
     * @param inputMap
     * @throws Exception
     */
    public BasePageInfo searchBreedToPage(Map<String, Object> inputMap) throws Exception;

	/**
     * *
     * 
     * @Description: 编辑品种
     * @author fangc
     * @param ppHouseModel
     * @param houselist
     * @throws Exception
     */
	public void editBreed(BreedModel breedModel, String breedList) throws Exception;

	/**
     * @Description: 删除品种
     * @author fangcj
     * @param ids
     * @throws Exception
     */
	public void deleteBreeds(long[] ids) throws Exception;

	// =======================================
	/**
     * @Description:查询猪舍类别列表 不分页
     * @author fangc
     * @throws Exception
     */
    public List<PigHouseModel> searchPigHouseToList(String pigType) throws Exception;

	/**
     * @Description:查询猪舍类别列表 分页
     * @author fangc
     * @throws Exception
     */
    public BasePageInfo searchPigHouseToPage(String houseTypeName, String disinfectDay, String disinfectMethod) throws Exception;

	/**
     * *
     * 
     * @Description: 编辑猪舍类别
     * @author fangc
     * @param ppHouseModel
     * @param houselist
     * @throws Exception
     */
	public void editPigHouse(PigHouseModel pigHouseModel) throws Exception;

	/**
     * @Description: 删除猪舍类别
     * @author fangcj
     * @param ids
     * @throws Exception
     */
	public void deletePigHouses(long[] ids) throws Exception;

	/**
     * @Description:查询用户业务编码列表 不分页
     * @return
     * @throws Exception
     */
	public List<BcodeView> searchBcodeToList() throws Exception;

	/**
     * @Description:查询用户业务编码列表 分页
     * @return
     * @throws Exception
     */
    public BasePageInfo searchBcodeToPage(Map<String, Object> inputMap) throws Exception;

	/**
     * @Description: 查询用户编码不分页
     * @author Cress
     * @return
     * @throws Exception
     */
	public List<BcodeModel> searchBcodeCustToList() throws Exception;

	/**
     * @Description:编辑 用户业务编码
     * @param ppIndicatorModel
     * @param indicator
     * @throws Exception
     */
	public void editBcode(BcodeTypeModel model, BcodeModel bcodeModel) throws Exception;

	/**
     * @Description: 编辑用户编码
     * @author Cress
     * @param model
     * @param bcodeModel
     * @throws Exception
     */
	public void editBcodeCust(List<BcodeModel> bcodeList) throws Exception;

	/**
     * @Description: 恢复用户编码
     * @author Cress
     * @param ids
     * @throws Exception
     */
	public void recoverBcodeCust(long[] ids) throws Exception;

	/**
     * @Description:删除 用户业务编码
     * @param ids
     * @throws Exception
     */
	public void deleteBcodes(long[] ids) throws Exception;

	/**
     * 周次维护：根据公司id，日期查询所在周
     * 
     * @param companyId
     * @param date
     * @return
     */
	// public int searchWeekByCompanyID(long companyId, Date date) throws
	// Exception;

	/**
     * 周次维护：根据年，公司id，创建人id 生成 一年的周次
     * 
     * @param param
     */
	// public void createWeek(HashMap<String, Object> param) throws Exception;

	/**
     * 桌面小部件： 查询桌面小部件集合
     * 
     * @return
     */
    public List<PartsModel> searchPartsToList() throws Exception;

	/**
     * 桌面小部件：查询桌面小部件集合 分页
     * 
     * @return
     */
    public BasePageInfo searchPartsByPage() throws Exception;

	/**
     * 桌面小部件：CUD操作
     */
    public void editParts(PartsModel entity, long[] ids, String jsonObj) throws Exception;

	/**
     * @Description:查询 用户编码列表 不分页
     * @return
     * @throws Exception
     */
	// public List<BcodeTypeModel> searchBcodeTToList() throws Exception;

	/**
     * @Description:查询 用户编码列表 分页
     * @return
     * @throws Exception
     */
	// public BasePageInfo searchBcodeTToPage() throws Exception;

	/**
     * @Description:编辑 用户编码
     * @param ppIndicatorModel
     * @param indicator
     * @throws Exception
     */
	// public void editCdBcodeT(BcodeTypeModel model, BcodeModel bcodeModel)
	// throws Exception;

	/**
     * @Description:删除 用户编码
     * @param ids
     * @throws Exception
     */
	// public void deleteBcodeTs(long[] ids) throws Exception;

	/**
     * 周次维护：查询集合
     * 
     * @return
     * @throws Exception
     */
	public List<WeekModel> searchWeekToList() throws Exception;

	/**
     * 周次维护：查询集合 分页
     * 
     * @return
     * @throws Exception
     */
    public BasePageInfo searchWeekByPage(String companyId, String year, String week, String startDate, String endDate) throws Exception;

	/**
     * 模板CU 操作
     * 
     * @throws Exception
     */
	public void editTemplate(TemplateModel entity, String jsonObj) throws Exception;

	/**
     * @Description:修改后的CdCode编辑
     * @author 程彬
     * @param codList
     * @param detialListStr
     * @throws Exception
     */
	public void editCodeList(CodeListModel codList, String detialListStr) throws Exception;

	/**
     * 保存设置
     * 
     * @param jsonObj
     * @throws Exception
     */
	public void editSetting(Map<String, Object> map) throws Exception;

	/**
     * 删除 菜单模板
     * 
     * @param ids
     * @throws Exception
     */
	public void deleteTemplate(long[] ids) throws Exception;

    /**
     * 业务编码的高级搜索，根据业务编码，业务名称，前缀，猪舍
     * 
     * @author THL
     * @param map
     * @return BasePageInfo
     * @throws Exception
     */
    public BasePageInfo searchCdBcodeToPageForAdvancedSearch(Map<String, Object> map) throws Exception;

    /**
     * 业务编码中新增功能中猪舍加载
     * 
     * @author THL
     * @return List
     * @throws Exception
     */
    public List<Map<String, Object>> searchHouseToList() throws Exception;

    /**
     * 业务编码高级查询中猪舍的加载
     * 
     * @author THL
     * @return List
     * @throws Exception
     */
    public List<Map<String, Object>> searchHouseToListForAdvancedSearch() throws Exception;

    /**
     * 用户编码高级查询
     * 
     * @author THL
     * @return List
     * @throws Exception
     */
    public List<Map<String, Object>> searchCdBcodeCustToListForAdvancedSearch(Map<String, Object> map) throws Exception;

    /**
     * 菜单建模 主页的Combobox
     * 
     * @author THL
     * @return
     */
    public List<Map<String, Object>> searchModuleToList(String type) throws Exception;

    /**
     * @Description: 桌面小部件 模块名 下拉框
     * @author 程彬
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> searchMenuBobox() throws Exception;

    /**
     * @Description:修改快捷菜单
     * @author zhangjs
     * @param list
     * @return
     * @throws Exception
     */
    public void editQuickMenus(List<QuickMenusModel> list) throws Exception;

    /**
     * @Description:绩效管理
     * @author yangy
     * @param list
     * @return
     * @throws Exception
     */
    public BasePageInfo performanceManageToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description:保存考核绩效
     * @author yangy
     * @param monthPerformanceModuleModels
     * @return
     * @throws Exception
     */
    public void editPerformanceManage(MonthPerformanceModuleModel monthPerformanceModuleModel)
            throws Exception;

    /**
     * @Description:绩效管理恢复
     * @author yangy
     * @throws Exception
     */
    public void recoverPerformanceManage() throws Exception;

    /**
     * @Description:绩效计算
     * @author yangy
     * @param list
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> performanceCalculateToList(Map<String, Object> map) throws Exception;

    /**
     * @Description:保存考核计算
     * @author yangy
     * @param monthPerformanceModels
     * @return
     * @throws Exception
     */
    public void editPerformanceCalculate(List<MonthPerformanceModel> monthPerformanceModels,String roleType) throws Exception;
    
    /**
     * @Description:查询考核计算
     * @author yangy
     * @param monthPerformanceModels
     * @return
     * @throws Exception
     */
    public List<MonthPerformanceModel> searchPerformanceCalculateToList(Map<String, Object> map) throws Exception;

    /**
     * @Description:获取登陆用户类型
     * @author yangy
     * @param monthPerformanceModels
     * @return
     * @throws Exception
     */
    public Boolean searchUserType() throws Exception;

    /**
     * @Description: 根据条件获取猪只状态
     * @author yangy
     * @param eventName
     * @param pigType
     * @return
     * @throws Exception
     */
    public List<PigClassModel> searchPigClassToListByEvent(String eventName, String pigType) throws Exception;

    /**
     * @Description:获取目标数值
     * @author yangy
     * @return
     * @throws Exception
     */
    public BasePageInfo searchSurvivalTargetList() throws Exception;

    /**
     * @Description:获取目标数值
     * @author yangy
     * @return
     * @throws Exception
     */
    public void editSurvaivalTargetList(SurvivalTargetModel survivalTargetModel) throws Exception;

    /**
     * @Description:删除目标数值
     * @author yangy
     * @return
     * @throws Exception
     */
    public void deleteSurvaivalTarget(long[] rowId) throws Exception;

    /**
     * @Description: 保存更新信息
     * @param farmId
     * @return
     * @throws Exception
     */     
    public void updateLog(SysUpdateLogModel sysUpdateLogModel) throws Exception;

    /**
     * @Description: 展示更新信息
     * @param inputMap
     * @return
     * @throws Exception
     */
    public BasePageInfo searchUpdateLog(Map<String, Object> inputMap) throws Exception;

    /**
     * s
     * 
     * @Description: 删除更新信息
     * @param farmId
     * @return
     * @throws Exception
     */     
    public void deleteUpdateLog(long[] ids) throws Exception;

    /**
     * @Description:删除绩效管理
     * @author yangy
     * @return
     * @throws Exception
     */
    public void deletePerformanceManage(long[] ids);

    /**
     * @Description:根据日期获取更新内容
     * @author Cress
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getUpdateContentByDate(String date) throws Exception;

    /**
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author Administrator
     * @return
     * @throws Exception
     */
    public List<PigClassModel> searchSeedPigClassToList(String pigType) throws Exception;

    /**
     * @Description: 消息推送增，删改
     * @author ZJ
     * @param map
     * @param farmId
     * @throws Exception
     */
    public void editPushMessage(Map<String, Object> map, String pushMessageList, Long farmId) throws Exception;

    /**
     * @Description: 消息推送主页
     * @author ZJ
     * @param map
     * @return
     * @throws Exception
     */
    public BasePageInfo searchpushMessageToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description: 消息推送删除
     * @author ZJ
     * @param ids
     * @throws Exception
     */
    public void deletePushMessage(long[] ids) throws Exception;

    /**
     * @Description: 停用启用
     * @author ZJ
     * @param ids
     */
    public void editDisableOrEnable(long[] ids) throws Exception;

    /**
     * @Description: 消息推送选择人员搜索
     * @author ZJ
     * @param map
     * @return
     */
    public BasePageInfo searchChooseUserToPage(Map<String, Object> map) throws Exception;

    /**
     * @Description: 消推送新增删除
     * @author ZJ
     * @param ids
     * @throws Exception
     */
    public void deleteAddPushMessage(long[] ids) throws Exception;

    /**
     * @Description: 查询消息推送人员关系明细
     * @author Cress
     * @param map
     * @return
     */
    public List<Map<String, Object>> searchDetailList(Map<String, Object> map);

    /**
     * @Description: 根据CODE查询推送消息
     * @author Cress
     * @param messageCode
     * @return
     */
    public List<Map<String, Object>> searchMessageByCode(String messageCode);

    /**
     * @Description: 根据消息ID查询推送人员
     * @author Cress
     * @param messageId
     * @return
     */
    public List<Map<String, Object>> searchUserIdByMessageId(long messageId);

}
