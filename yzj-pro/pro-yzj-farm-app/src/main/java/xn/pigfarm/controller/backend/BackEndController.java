package xn.pigfarm.controller.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.model.system.BcodeModel;
import xn.core.model.system.BcodeTypeModel;
import xn.core.model.system.QuickMenusModel;
import xn.core.util.page.BasePageInfo;
import xn.pigfarm.model.backend.BreedModel;
import xn.pigfarm.model.backend.CdButtonModel;
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
import xn.pigfarm.model.basicinfo.IndicatorModel;
import xn.pigfarm.model.basicinfo.SettingModuleModel;
import xn.pigfarm.service.backend.IBackEndService;

/**
 * @Description: code_list控制类
 * @author li.zhou
 * @date 2016年4月13日 下午5:53:09
 */
@Component
@Controller
@RequestMapping("/backEnd")
public class BackEndController extends BaseController {

	@Autowired
	private IBackEndService backEndService;

	/**
     * @Description: cdcode查询 分页
     * @author
     * @param mainId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCdCodeTypeToPage")
	@ResponseBody
	public Map<String, Object> searchCdCodeListToPage(HttpServletResponse response) throws Exception {

        String typeCode = getString("typeCode");
        String typeName = getString("typeName");
        BasePageInfo page = backEndService.searchCdCodeListToPage(typeCode, typeName);
        return getReturnMap(page);
	}

	/**
     * @Description: cdcode查询 不分页
     * @author
     * @param mainId
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchCodeListToList")
	@ResponseBody
    public Map<String, Object> searchCodeListToList(HttpServletResponse response) throws Exception {

        List<CodeListModel> list = backEndService.searchCodeListToList();
		return getReturnMap(list);
	}

	/**
     * @Description: 查询明细
     * @author
     * @param mainId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCdCodeDetailToList")
	@ResponseBody
	public Map<String, Object> searchCdCodeDetailToList(HttpServletResponse response) throws Exception {

		List<CodeListModel> list = backEndService.searchCdCodeDetailToList(getString("typeCode"));
		return getReturnMap(list);
	}

	/**
     * @Description: 新增，修改CdCode
     * @author Zhangjc
     * @param cdCodeTypeMode
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/editCdCode")
	@ResponseBody
	public Map<String, Object> editCdCode(HttpServletRequest request) throws Exception {

		// String jsonString = request.("gridList");
		// backEndService.editCdCode(cdCodeTypeMode, getDetialListStr());
		CodeListModel codList = getBean(CodeListModel.class);
		backEndService.editCodeList(codList, getDetialListStr());
        basicRefresh(false);
		return getReturnMap();
	}

	/**
     * @Description:删除CdCode方法
     * @author Zhangjc
     * @param ids
     * @return
     * @throws Exception
     */
	@RequestMapping("/deleteCdCode")
	@ResponseBody
	public Map<String, Object> deleteCdCode(HttpServletRequest request) throws Exception {

		// Map<String, Object> map = getMap();
		// CodeListModel codList = getBean(CodeListModel.class);
		String[] typeCodes = getStrings("type_code");
		backEndService.deleteCdCode(typeCodes);

		// backEndService.deleteCdCode(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description:查询菜单
     * @author
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchMenuByUserId.do")
	@ResponseBody
	public Map<String, Object> searchMenuByUserId(HttpServletRequest request) throws Exception {

		List<CdModuleModel> list = backEndService.searchMenuByUserId();
        basicRefresh();
		return getReturnMap(list);
	}

	/**
     * @Description: 系统菜单主页分页
     * @author Zhangjc
     * @param mainId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCdModuelToPage")
	@ResponseBody
	public Map<String, Object> searchCdModuelToPage(HttpServletResponse response) throws Exception {
        String moduleName = getString("moduleName");
        String component = getString("component");
        String usingFlag = getString("usingFlag");

        BasePageInfo list = backEndService.searchCdModuelToPage(moduleName, component, usingFlag);
        basicRefresh();
		return getReturnMap(list);
	}

	/**
     * @Description: 系统菜单查询明细表
     * @author Zhangjc
     * @param mainId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCdModuelDetailToList")
	@ResponseBody
	public Map<String, Object> searchCdModuelDetailToList(HttpServletResponse response) throws Exception {

		List<CdButtonModel> list = backEndService.searchCdModuelDetailToList(getLong("mainId"));
        basicRefresh();
		return getReturnMap(list);
	}

	/**
     * @Description: 系统菜单主页查询 不分页
     * @author Zhangjc
     * @return
     * @throws Exception
     */
	@RequestMapping("/editCdModuel")
	@ResponseBody
	public Map<String, Object> editCdModuel(HttpServletRequest request) throws Exception {

		backEndService.editCdModuel(getBeanBaseModel(CdModuleModel.class), getDetialListStr());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 系统菜单主页查询 不分页
     * @author Zhangjc
     * @return
     */
	@RequestMapping("/searchCdModuelToList")
	@ResponseBody
	public Map<String, Object> searchCdModuelToList(HttpServletRequest request) {

		return getReturnMap(backEndService.searchCdModuelToList());
	}

	/**
     * @Description: 系统菜单删除
     * @author Zhangjc
     * @param ids
     * @return
     * @throws Exception
     */
	@RequestMapping("/deleteCdModuels")
	@ResponseBody
	public Map<String, Object> deleteCdModuels(HttpServletRequest request) throws Exception {

		backEndService.deleteCdModuels(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * 系统设置项：CUD操作
     * 
     * @param entity
     * @param ids
     * @param jsonObj
     * @return
     * @throws Exception
     */
	@RequestMapping("/editCdSetting")
	@ResponseBody
	public Map<String, Object> editCdSetting(HttpServletRequest request) throws Exception {
        backEndService.editCdSetting(getBean(SettingModuleModel.class), null, getString("jsonObj"));
        basicRefresh();
		return getReturnMap();
	}

    // /**
    // * 系统设置项：查询全部 没分页
    // *
    // * @param farmId
    // * @return
    // * @throws Exception
    // */
    // @RequestMapping("/searchCdMsettingModuleToList")
    // @ResponseBody
    // public Map<String, Object> searchCdMsettingModuleToList(HttpServletRequest request) throws Exception {
    //
    // return getReturnMap(backEndService.searchCdMsettingModuleToList(getFarmId()));
    // }

    /**
     * 系统设置项：查询全部 没分页
     * 
     * @param farmId
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSettingModuleToList")
    @ResponseBody
    public Map<String, Object> searchSettingModuleToList(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchSettingModuleToList());
    }

	/**
     * 系统设置项：查询全部 分页
     * 
     * @param farmId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCdMsettingModuleByPage")
	@ResponseBody
	public Map<String, Object> searchCdMsettingModuleByPage(HttpServletRequest request) throws Exception {
        String settingModule = getString("settingModule");
        String settingName = getString("settingName");
        String settingCode = getString("settingCode");
        String settingValue = getString("settingValue");
        String sowType = getString("sowType");

        return getReturnMap(backEndService.searchCdMsettingModuleByPage(settingModule, settingName, settingCode, settingValue, sowType));
	}

	/**
     * 系统设置项：删除
     * 
     * @param ids
     * @return
     * @throws Exception
     */
	@RequestMapping("/deletesCdSetting")
	@ResponseBody
	public Map<String, Object> deletesCdSetting(HttpServletRequest request) throws Exception {

		backEndService.editCdSetting(null, getIds(), null);
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 菜单建模分页查询
     * @author Cress
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchCdTemplateToPage")
	@ResponseBody
	public Map<String, Object> searchCdTemplateToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(backEndService.searchCdTemplateToPage(getString("templateName")));
	}

	/**
     * @Description: 根据模板Id查询菜单树
     * @author Cress
     * @param templateId
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchModuleByTemplateId")
	@ResponseBody
	public Map<String, Object> searchModuleByTemplateId(HttpServletRequest request) throws Exception {

		return getReturnMap(backEndService.searchModuleByTemplateId(getLong("templateId")));
	}

	@RequestMapping("/searchModuleExcludeById")
	@ResponseBody
	public Map<String, Object> searchModuleExcludeById(HttpServletRequest request) throws Exception {
		return getReturnMap(backEndService.searchModuleExcludeById(getIds()));
	}

	@RequestMapping("/editTemplate")
	@ResponseBody
	public Map<String, Object> editTemplate(HttpServletRequest request) throws Exception {

		backEndService.editTemplate(getBean(TemplateModel.class), getString("treeList"));
        basicRefresh();
		return getReturnMap();
	}

    // =========================猪只状态
	@RequestMapping("/searchPigClassToList")
	@ResponseBody
	public Map<String, Object> searchPigClassToList(HttpServletRequest request) throws Exception {

		return getReturnMap(backEndService.searchPigClassToList(getString("pigType")));
	}

	@RequestMapping("/searchPigClassToPage")
	@ResponseBody
	public Map<String, Object> searchPigClassToPage(HttpServletRequest request) throws Exception {
        String businessCode = getString("businessCode");
        String pigClassName = getString("pigClassName");
        String pigType = getString("pigType");
        return getReturnMap(backEndService.searchPigClassToPage(businessCode, pigClassName, pigType));
	}

	@RequestMapping("/editPigClass")
	@ResponseBody
	public Map<String, Object> editPigClass(HttpServletRequest request) throws Exception {

		backEndService.editPigClass(getBean(PigClassModel.class));
        cacheRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 指标库查询 明细 不分页
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchIndicatorToList")
	@ResponseBody
	public Map<String, Object> searchIndicatorToList(HttpServletRequest request) throws Exception {

		return getReturnMap(backEndService.searchIndicatorToList());
	}

	/**
     * @Description: 客户指标库查询不分页
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchIndicatorCustToList")
	@ResponseBody
	public Map<String, Object> searchIndicatorCustToList(HttpServletRequest request) throws Exception {
        Map<String, Object> inputMap = new HashMap<String, Object>();
        return getReturnMap(backEndService.searchIndicatorCustToList(inputMap));
	}

	/**
     * @Description:客户指标库编辑
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/eidtIndicatorCust")
	@ResponseBody
	public Map<String, Object> eidtIndicatorCust(HttpServletRequest request) throws Exception {
		List<IndicatorModel> list = getModelList(getDetialListStr(), IndicatorModel.class);
		backEndService.editIndicatorCust(list, getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 客户指标库恢复
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/recoverIndicatorCust")
	@ResponseBody
	public Map<String, Object> recoverIndicatorCust(HttpServletRequest request) throws Exception {
		backEndService.recoverIndicatorCust(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 指标库查询 明细 分页
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchIndicatorToPage")
    @ResponseBody
    public Map<String, Object> searchIndicatorToPage(HttpServletRequest request) throws Exception {
        String businessCode = getString("businessCode");
        String indName = getString("indName");
        String standardValue = getString("standardValue");
        String minValue = getString("minValue");
        String maxValue = getString("maxValue");
        String unit = getString("unit");
        return getReturnMap(backEndService.searchIndicatorToPage(businessCode, indName, standardValue, minValue, maxValue, unit));
    }

	/**
     * @Description: 指标库 编辑
     * @param ppIndicatorModel
     * @param indicator
     * @return
     * @throws Exception
     */
	@RequestMapping("/editIndicator")
	@ResponseBody
	public Map<String, Object> editIndicator(HttpServletRequest request) throws Exception {

		backEndService.editIndicator(getBean(IndicatorModel.class));
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 指标库 删除
     * @param ids
     * @return
     * @throws Exception
     */
	@RequestMapping("/deleteIndicators")
	@ResponseBody
	public Map<String, Object> deleteIndicators(HttpServletRequest request) throws Exception {

		backEndService.deleteIndicators(getIds());
        basicRefresh();
		return getReturnMap();
	}

	@RequestMapping("/deletePigClass")
	@ResponseBody
	public Map<String, Object> deletePigClass(HttpServletRequest request) throws Exception {

		backEndService.deletePigClass(getIds());
        cacheRefresh();
		return getReturnMap();
	}

	@RequestMapping("/searchBreedToList")
	@ResponseBody
	public Map<String, Object> searchBreedToList(HttpServletRequest request) throws Exception {
        String breedCode = getString("breedCode");
        String breedName = getString("breedName");
        return getReturnMap(backEndService.searchBreedToList(breedCode, breedName));
	}

	@RequestMapping("/searchBreedToPage")
	@ResponseBody
	public Map<String, Object> searchBreedToPage(HttpServletRequest request) throws Exception {
        return getReturnMap(backEndService.searchBreedToPage(getMap()));
	}

	@RequestMapping("/editBreed")
	@ResponseBody
	public Map<String, Object> editBreed(HttpServletRequest request) throws Exception {

		backEndService.editBreed(getBean(BreedModel.class), getDetialListStr());
        cacheRefresh();
		return getReturnMap();
	}

	@RequestMapping("/deleteBreeds")
	@ResponseBody
	public Map<String, Object> deleteCdBreeds(HttpServletRequest request) throws Exception {

		backEndService.deleteBreeds(getIds());
        cacheRefresh();
		return getReturnMap();
	}

    // =========================猪舍类型
	@RequestMapping("/searchPigHouseToList")
	@ResponseBody
	public Map<String, Object> searchPigHouseToList(HttpServletRequest request) throws Exception {
        String pigType = getString("pigType");
        return getReturnMap(backEndService.searchPigHouseToList(pigType));
	}

	@RequestMapping("/searchPigHouseToPage")
	@ResponseBody
	public Map<String, Object> searchPigHouseToPage(HttpServletRequest request) throws Exception {
        String houseTypeName = getString("houseTypeName");
        String disinfectDay = getString("disinfectDay");
        String disinfectMethod = getString("disinfectMethod");

        return getReturnMap(backEndService.searchPigHouseToPage(houseTypeName, disinfectDay, disinfectMethod));
	}

	@RequestMapping("/editPigHouse")
	@ResponseBody
	public Map<String, Object> editPigHouse(HttpServletRequest request) throws Exception {

		backEndService.editPigHouse(getBean(PigHouseModel.class));
        basicRefresh();
		return getReturnMap();
	}

	@RequestMapping("/deletePigHouses")
	@ResponseBody
	public Map<String, Object> deletePigHouses(HttpServletRequest request) throws Exception {

		backEndService.deletePigHouses(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 用户业务编码查询 明细 不分页
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchBcodeToList")
	@ResponseBody
	public Map<String, Object> searchBcodeToList(HttpServletRequest request) throws Exception {

		return getReturnMap(backEndService.searchBcodeToList());
	}

	/**
     * @Description: 用户业务编码查询 明细
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchBcodeCustToList")
	@ResponseBody
	public Map<String, Object> searchBcodeCustToList(HttpServletRequest request) throws Exception {

		return getReturnMap(backEndService.searchBcodeCustToList());
	}

	/**
     * @Description: 用户业务编码查询 明细 分页
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchBcodeToPage")
	@ResponseBody
	public Map<String, Object> searchBcodeToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchBcodeToPage(getMap()));
	}

	/**
     * @Description: 业务编码 编辑
     * @param cdBcodeTypeModel
     * @param bcode
     * @return
     * @throws Exception
     */
	@RequestMapping("/editBcode")
	@ResponseBody
	public Map<String, Object> editBcode(HttpServletRequest request) throws Exception {

		backEndService.editBcode(getBean(BcodeTypeModel.class), getBean(BcodeModel.class));
        basicRefresh(false);
		return getReturnMap();
	}

	/**
     * @Description: 用户编码编辑
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/editBcodeCust")
	@ResponseBody
	public Map<String, Object> editBcodeCust(HttpServletRequest request) throws Exception {

		List<BcodeModel> list = getModelList(getDetialListStr(), BcodeModel.class);
		backEndService.editBcodeCust(list);
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 恢复用户编码
     * @author Cress
     * @param request
     * @return
     * @throws Exception
     */
	@RequestMapping("/recoverBcodeCust")
	@ResponseBody
	public Map<String, Object> recoverBcodeCust(HttpServletRequest request) throws Exception {

		backEndService.recoverBcodeCust(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 用户业务编码 删除
     * @param ids
     * @return
     * @throws Exception
     */
	@RequestMapping("/deleteBcodes")
	@ResponseBody
	public Map<String, Object> deleteCdBcodes(HttpServletRequest request) throws Exception {

		backEndService.deleteBcodes(getIds());
        basicRefresh();
		return getReturnMap();
	}

	/**
     * 桌面小部件：CUD操作
     * 
     * @param entity
     * @param jsonObj
     * @return
     * @throws Exception
     */
    @RequestMapping("/editParts")
	@ResponseBody
    public Map<String, Object> editParts(HttpServletRequest request) throws Exception {
        backEndService.editParts(getBean(PartsModel.class), null, getString("jsonObj"));
        basicRefresh();
		return getReturnMap();
	}

	/**
     * 桌面小部件：查询集合 分页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPartsByPage")
	@ResponseBody
    public Map<String, Object> searchPartsByPage(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchPartsByPage());
	}

	/**
     * 桌面小部件：批量删除
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/deletesParts")
	@ResponseBody
    public Map<String, Object> deletesParts(HttpServletRequest request) throws Exception {
        backEndService.editParts(null, getIds(), null);
        basicRefresh();
		return getReturnMap();
	}

	/**
     * @Description: 用户编码查询 明细 不分页
     * @return
     * @throws Exception
     */
	// @RequestMapping("/searchCdBcodeToListT")
	// @ResponseBody
	// public Map<String, Object> searchCdBcodeTToList() throws Exception {
	//
	// return getReturnMap(backEndService.searchBcodeTToList());
	// }

	/**
     * @Description: 用户编码查询 明细 分页
     * @return
     * @throws Exception
     */
	// @RequestMapping("/searchCdBcodeTToPage")
	// @ResponseBody
	// public Map<String, Object> searchCdBcodeTToPage() throws Exception {
	//
	// return getReturnMap(backEndService.searchCdBcodeTToPage());
	// }

	/**
     * @Description: 用户编码 编辑
     * @param cdBcodeTypeModel
     * @param bcode
     * @return
     * @throws Exception
     */
	// @RequestMapping("/editCdBcodeT")
	// @ResponseBody
	// public Map<String, Object> editCdBcodeT(HttpServletRequest request)
	// throws Exception {
	// CdBcodeTypeModel typeModel = getBeanBaseModel(CdBcodeTypeModel.class);
	// CdBcodeModel codeModel = getBeanBaseModel(CdBcodeModel.class);
	// backEndService.editCdBcodeT(typeModel, codeModel);
	// return getReturnMap();
	// }

	/**
     * @Description: 用户编码 删除
     * @param ids
     * @return
     * @throws Exception
     */
	// @RequestMapping("/deleteCdBcodeTs")
	// @ResponseBody
	// public Map<String, Object> deleteCdBcodeTs(@RequestParam("ids[]") long[]
	// ids) throws Exception {
	//
	// backEndService.deleteCdBcodeTs(ids);
	// return getReturnMap();
	// }

	/**
     * 周次维护：查询集合 分页
     * 
     * @return
     * @throws Exception
     */
	@RequestMapping("/searchWeekByPage")
	@ResponseBody
	public Map<String, Object> searchWeekByPage(HttpServletRequest request) throws Exception {
        String companyId = getString("companyId");
        String year = getString("year");
        String week = getString("week");
        String startDate = getString("startDate");
        String endDate = getString("endDate");
        return getReturnMap(backEndService.searchWeekByPage(companyId, year, week, startDate, endDate));
	}

	/**
     * 保存设置
     * 
     * @param jsonObj
     * @throws Exception
     */
    @RequestMapping("/editSetting")
	@ResponseBody
	public Map<String, Object> editSetting(HttpServletRequest request) throws Exception {

		backEndService.editSetting(getMap());
        basicRefresh();
		return getReturnMap();
	}

    /**
     * @Description: 恢复默认
     * @author Administrator
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteSetting")
    @ResponseBody
    public Map<String, Object> deleteSetting(HttpServletRequest request) throws Exception {

        backEndService.deleteSetting(getFarmId());
        basicRefresh();
        return getReturnMap();
    }

	/**
     * 保存设置
     * 
     * @param jsonObj
     * @throws Exception
     */
	@RequestMapping("/deleteTemplate")
	@ResponseBody
	public Map<String, Object> deleteTemplate(HttpServletRequest request) throws Exception {

		backEndService.deleteTemplate(getIds());
        basicRefresh();
		return getReturnMap();
	}
	
    @RequestMapping("/searchCdBcodeToPageForAdvancedSearch")
    @ResponseBody
    public Map<String, Object> searchCdBcodeToPageForAdvancedSearch(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchCdBcodeToPageForAdvancedSearch(getMap()));
    }

    @RequestMapping("/searchHouseToList")
    @ResponseBody
    public Map<String, Object> searchHouseToList(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchHouseToList());
    }

    @RequestMapping("/searchHouseToListForAdvancedSearch")
    @ResponseBody
    public Map<String, Object> searchHouseToListForAdvancedSearch(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchHouseToListForAdvancedSearch());
    }

    @RequestMapping("/searchCdBcodeCustToListForAdvancedSearch")
    @ResponseBody
    public Map<String, Object> searchCdBcodeCustToListForAdvancedSearch(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchCdBcodeCustToListForAdvancedSearch(getMap()));
    }

    @RequestMapping("/searchModuleToList")
    @ResponseBody
    public Map<String, Object> searchModuleToList(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchModuleToList(getString("type")));
    }

    @RequestMapping("/searchMenuBobox")
    @ResponseBody
    public Map<String, Object> searchMenuBobox(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchMenuBobox());
    }

    /**
     * @Description: 修改快捷菜单
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editQuickMenus")
    @ResponseBody
    public Map<String, Object> editQuickMenus(HttpServletRequest request) throws Exception {
        List<QuickMenusModel> list = getModelList(getString("quickMenuList"), QuickMenusModel.class);
        backEndService.editQuickMenus(list);
        return getReturnMap();
    }

    /************************************ yangy start 绩效管理 ********************************************/
    /**
     * @Description: 绩效管理
     * @author yangy
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/performanceManageToPage")
    @ResponseBody
    public Map<String, Object> performanceManageToPage(HttpServletRequest request) throws Exception {
    	basicRefresh();
        return getReturnMap(backEndService.performanceManageToPage(getMap()));
    }

    /**
     * 保存绩效考核内容
     * 
     * @throws Exception
     */
    @RequestMapping("/editPerformanceManage")
    @ResponseBody
    public Map<String, Object> editPerformanceManage(HttpServletRequest request) throws Exception {
    	MonthPerformanceModuleModel monthPerformanceModuleModel = getBean(MonthPerformanceModuleModel.class);
        backEndService.editPerformanceManage(monthPerformanceModuleModel);
        basicRefresh();
        return getReturnMap();
    }
    
    /**
     * 删除绩效考核内容
     * 
     * @throws Exception
     */
    @RequestMapping("/deletePerformanceManage")
    @ResponseBody
    public Map<String, Object> deletePerformanceManage(HttpServletRequest request) throws Exception {
        backEndService.deletePerformanceManage(getIds());
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 绩效管理恢复
     * @author yangy
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/recoverPerformanceManage")
    @ResponseBody
    public Map<String, Object> recoverPerformanceManage(HttpServletRequest request) throws Exception {
        backEndService.recoverPerformanceManage();
        basicRefresh();
        return getReturnMap();
    }

    /************************************* yangy end 绩效管理 *******************************************/

    /************************************ yangy start 绩效计算 ********************************************/
    
    /**
     * @Description: 绩效计算
     * @author yangy
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPerformanceCalculateToList")
    @ResponseBody
    public Map<String, Object> searchPerformanceCalculateToList(HttpServletRequest request) throws Exception {
        return getReturnMap(backEndService.searchPerformanceCalculateToList(getMap()));
    }
    
    /**
     * @Description: 绩效计算
     * @author yangy
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/performanceCalculateToList")
    @ResponseBody
    public Map<String, Object> performanceCalculateToList(HttpServletRequest request) throws Exception {
        return getReturnMap(backEndService.performanceCalculateToList(getMap()));
    }

    /**
     * @Description: 绩效计算
     * @author yangy
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editPerformanceCalculate")
    @ResponseBody
    public Map<String, Object> editPerformanceCalculate(HttpServletRequest request) throws Exception {
        List<MonthPerformanceModel> monthPerformanceModels = getModelList(getDetialListStr(), MonthPerformanceModel.class);
        String roleType = getString("roleType");
        backEndService.editPerformanceCalculate(monthPerformanceModels,roleType);
        basicRefresh();
        return getReturnMap();
    }

    /************************************ yangy end 绩效计算 ********************************************/

    // 获取当前用户登陆类型
    @RequestMapping("/searchUserType")
    @ResponseBody
    public Map<String, Object> searchUserType(HttpServletRequest request) throws Exception {
        return getReturnMap(backEndService.searchUserType());
    }
    
    /************************************ 刷新缓存 ********************************************/
    // 刷新供应链物料主数据信息缓存
    @RequestMapping("/onBtnRefreshMaterialInfoCache")
    @ResponseBody
    public Map<String, Object> onBtnRefreshMaterialInfoCache(HttpServletRequest request) throws Exception {
        MaterialInfoCacheRefresh();
        return getReturnMap(null);
    }
    
    // 刷新猪只缓存
    @RequestMapping("/onBtnRefreshPigCache")
    @ResponseBody
    public Map<String, Object> onBtnRefreshPigCache(HttpServletRequest request) throws Exception {
        pigInfoRefresh(false);
        return getReturnMap(null);
    }
    
    // 刷新系统缓存
    @RequestMapping("/onBtnRefreshSYSCache")
    @ResponseBody
    public Map<String, Object> onBtnRefreshSYSCache(HttpServletRequest request) throws Exception {
        basicRefresh(false);
        return getReturnMap(null);
    }

    /************************************ 2016-12-26 yangy 新增修改猪只状态 ********************************************/
    @RequestMapping("/searchPigClassToListByEvent")
    @ResponseBody
    public Map<String, Object> searchPigClassToListByEvent(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchPigClassToListByEvent(getString("eventName"), getString("pigType")));
    }

    /************************************ 2016-12-26 yangy 新增修改猪只状态 ********************************************/

    @RequestMapping("/searchSurvivalTargetList")
    @ResponseBody
    public Map<String, Object> searchSurvivalTargetList(HttpServletRequest request) throws Exception {
        return getReturnMap(backEndService.searchSurvivalTargetList());
    }

    @RequestMapping("/editSurvaivalTargetList")
    @ResponseBody
    public Map<String, Object> editSurvaivalTargetList(HttpServletRequest request) throws Exception {
        SurvivalTargetModel survivalTargetModel = getBean(SurvivalTargetModel.class);
        backEndService.editSurvaivalTargetList(survivalTargetModel);
        return getReturnMap();
    }

    @RequestMapping("/deleteSurvaivalTarget")
    @ResponseBody
    public Map<String, Object> deleteSurvaivalTarget(HttpServletRequest request) throws Exception {
        backEndService.deleteSurvaivalTarget(getIds());
        return getReturnMap();
    }
    @RequestMapping("/updateLog")
    @ResponseBody
    public Map<String, Object> updateLog(HttpServletRequest request) throws Exception {
        SysUpdateLogModel sysUpdateLogModel = getBean(SysUpdateLogModel.class);
        // String billDate = getString("updateTime");
        // String updateNotes = getString("updateLog");
        // String updateType = getString("updateType");
        // String notes = getString("notes");
        // String rowId = getString("rowId");
        backEndService.updateLog(sysUpdateLogModel);
        return getReturnMap();
    }
    @RequestMapping("/searchUpdateLog")
    @ResponseBody
    public Map<String, Object> searchUpdateLog() throws Exception {
        return getReturnMap(backEndService.searchUpdateLog(getMap()));

    }
    @RequestMapping("/deleteUpdateLog")
    @ResponseBody
    public Map<String, Object> deleteUpdateLog(HttpServletRequest request) throws Exception {
    	
    	backEndService.deleteUpdateLog(getIds());
        return getReturnMap();

    
    }

    @RequestMapping("/searchSeedPigClassToList")
    @ResponseBody
    public Map<String, Object> searchSeedPigClassToList(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchSeedPigClassToList(getString("pigType")));

    }    

    /**
     * @Description: 消息推送CU操作
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editPushMessage")
    @ResponseBody
    public Map<String, Object> editPushMessage(HttpServletRequest request) throws Exception {
        backEndService.editPushMessage(getMap(), getDetialListStr(), getFarmId());
        return getReturnMap();

    }

    /**
     * @Description: 消息推送主页
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchpushMessageToPage")
    @ResponseBody
    public Map<String, Object> searchpushMessageToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchpushMessageToPage(getMap()));

    }

    /**
     * @Description: 消息推送删除
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deletePushMessage")
    @ResponseBody
    public Map<String, Object> deletePushMessage(HttpServletRequest request) throws Exception {
        backEndService.deletePushMessage(getIds());
        basicRefresh();
        return getReturnMap();

    }

    /**
     * @Description: 消息新增删除
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteAddPushMessage")
    @ResponseBody
    public Map<String, Object> deleteAddPushMessage(HttpServletRequest request) throws Exception {
        backEndService.deleteAddPushMessage(getIds());
        basicRefresh();
        return getReturnMap();

    }

    /**
     * @Description: 停用启用
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editDisableOrEnable")
    @ResponseBody
    public Map<String, Object> editDisableOrEnable(HttpServletRequest request) throws Exception {
        backEndService.editDisableOrEnable(getIds());
        return getReturnMap();

    }

    /**
     * @Description:消息推送选择人员搜索
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */

    @RequestMapping("/searchChooseUserToPage")
    @ResponseBody
    public Map<String, Object> searchChooseUserToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(backEndService.searchChooseUserToPage(getMap()));

    }

    @RequestMapping("/searchDetailList")
    @ResponseBody
    public Map<String, Object> searchHistoryLink() throws Exception {
        return getReturnMap(backEndService.searchDetailList(getMap()));
    }


}

