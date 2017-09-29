
package xn.pigfarm.controller.basicinfo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.cache.CacheUtil;
import xn.pigfarm.model.basicinfo.BoarModel;
import xn.pigfarm.model.basicinfo.MaterialGroupModel;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.basicinfo.SowModel;
import xn.pigfarm.service.basicinfo.IMaterialService;

/**
 * @Description: 基础模块物料主数据
 * @author fangc
 * @date 2016年5月3日 下午3:04:38
 */
@Component
@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {

	@Autowired
    private IMaterialService materialService;

    // 查询物料
    @RequestMapping("/searchMaterialToList")
    @ResponseBody
    public Map<String, Object> searchMaterialToList(HttpServletRequest request) throws Exception {
        return getReturnMap(materialService.searchMaterialToList(getString("pigType")));
    }

    // 精液主数据对应公猪物料
    @RequestMapping("/searchBoarMaterialToList")
    @ResponseBody
    public Map<String, Object> searchBoarMaterialToList(HttpServletRequest request) throws Exception {
        return getReturnMap(materialService.searchBoarMaterialToList(getString("pigType"), getString("rowId")));
    }

    @RequestMapping("/searchDetailMaterialToList")
    @ResponseBody
    public Map<String, Object> searchDetailMaterialToList(HttpServletRequest request) throws Exception {

        String materialType = getString("materialType");
        long rowId = getLong("rowId");

        // 将明细的rowId传到页面的rowId，主表的rowId直接取明细表的materialId，方便编辑操作
        Map<String, Object> result = new HashMap<String, Object>();
        result.putAll(getMap());
        result.putAll(materialService.searchDetailMaterialToList(rowId, materialType));
        return getReturnMap(result);
    }

    @RequestMapping("/searchMaterialToPage")
    @ResponseBody
    public Map<String, Object> searchMaterialToPage(HttpServletRequest request) throws Exception {

        String materialType = getString("materialType");
        return getReturnMap(materialService.searchMaterialToPage(materialType));
    }

    @RequestMapping("/editMaterial")
    @ResponseBody
    public Map<String, Object> editMaterial(HttpServletRequest request) throws Exception {

        String materialType = getString("materialType").trim();
        materialService.editMaterial(getMap(), materialType);
        basicRefresh();
        // CacheUtil.MaterialInfoCacheRefresh(getFarmId());
        CacheUtil.MaterialInfoCacheRefresh();
        return getReturnMap();
    }

    @RequestMapping("/deleteMaterials")
    @ResponseBody
    public Map<String, Object> deleteMaterials(HttpServletRequest request) throws Exception {

        materialService.deleteMaterials(getMap());
        basicRefresh();
        CacheUtil.MaterialInfoCacheRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 查询 物料组维护 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchCdMaterialGroupToList")
    @ResponseBody
    public Map<String, Object> searchCdMaterialGroupToList(HttpServletRequest request) throws Exception {

        return getReturnMap(materialService.searchMaterialGroupToList(getString("pigType")));
    }

    /**
     * @Description: 查询 物料组维护 分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchCdMaterialGroupToPage")
    @ResponseBody
    public Map<String, Object> searchCdMaterialGroupToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(materialService.searchMaterialGroupToPage());
    }

    /**
     * @Description: 编辑 物料组维护
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    @RequestMapping("/editCdMaterialGroup")
    @ResponseBody
    public Map<String, Object> editCdMaterialGroup(HttpServletRequest request) throws Exception {

        materialService.editMaterialGroup(getBean(MaterialGroupModel.class), getDetialListStr());
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 删除 物料组维护
     * @author guang
     * @param ids
     * @throws Exception
     */
    @RequestMapping("/deleteCdMaterialGroups")
    @ResponseBody
    public Map<String, Object> deleteCdMaterialGroups(HttpServletRequest request) throws Exception {

        materialService.deleteMaterialGroups(getIds());
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 查询 公猪 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchBoarToList")
    @ResponseBody
    public Map<String, Object> searchBoarToList(HttpServletRequest request) throws Exception {
        return getReturnMap(materialService.searchBoarToList(getString("pigType")));
    }

    /**
     * @Description: 查询 公猪 分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchBoarToPage")
    @ResponseBody
    public Map<String, Object> searchBoarToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(materialService.searchBoarToPage());
    }

    /**
     * @Description: 编辑 公猪
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    @RequestMapping("/editBoar")
    @ResponseBody
    public Map<String, Object> editBoar(HttpServletRequest request) throws Exception {

        materialService.editBoar(getBean(BoarModel.class));
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 删除 公猪
     * @author guang
     * @param ids
     * @throws Exception
     */
    @RequestMapping("/deleteBoars")
    @ResponseBody
    public Map<String, Object> deleteBoars(HttpServletRequest request) throws Exception {

        materialService.deleteBoars(getIds());
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 查询 母猪 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSowToList")
    @ResponseBody
    public Map<String, Object> searchSowToList(HttpServletRequest request) throws Exception {
        return getReturnMap(materialService.searchSowToList(getString("pigType")));
    }

    /**
     * @Description: 查询 母猪 分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchSowToPage")
    @ResponseBody
    public Map<String, Object> searchSowToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(materialService.searchSowToPage());
    }

    /**
     * @Description: 编辑 母猪
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    @RequestMapping("/editSow")
    @ResponseBody
    public Map<String, Object> editSow(HttpServletRequest request) throws Exception {

        materialService.editSow(getBean(SowModel.class));
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 删除 母猪
     * @author guang
     * @param ids
     * @throws Exception
     */
    @RequestMapping("/deleteSows")
    @ResponseBody
    public Map<String, Object> deleteSows() throws Exception {

        materialService.deleteSows(getIds());
        basicRefresh();
        return getReturnMap();
    }
    
    /**
     * @Description: 查询 肉猪 不分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPorkPigToList")
    @ResponseBody
    public Map<String, Object> searchPorkPigToList(HttpServletRequest request) throws Exception {
        return getReturnMap(materialService.searchPorkPigToList(getString("pigType")));
    }

    /**
     * @Description: 查询 肉猪 分页
     * @author guang
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPorkPigToPage")
    @ResponseBody
    public Map<String, Object> searchPorkPigToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(materialService.searchPorkPigToPage());
    }

    /**
     * @Description: 编辑 肉猪
     * @author guang
     * @param cdMaterialGroupModel
     * @param lineList
     * @throws Exception
     */
    @RequestMapping("/editPorkPig")
    @ResponseBody
    public Map<String, Object> editPorkPig(HttpServletRequest request) throws Exception {

        materialService.editPorkPig(getBean(PorkPigModel.class));
        basicRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 删除 肉猪
     * @author guang
     * @param ids
     * @throws Exception
     */
    @RequestMapping("/deletePorkPigs")
    @ResponseBody
    public Map<String, Object> deletePorkPigs(HttpServletRequest request) throws Exception {

        materialService.deletePorkPigs(getIds());
        basicRefresh();
        return getReturnMap();
    }

    @RequestMapping("/searchCdMaterialToPageForAdvancedSearch")
    @ResponseBody
    public Map<String, Object> searchCdMaterialToPageForAdvancedSearch(HttpServletRequest request) throws Exception {

        return getReturnMap(materialService.searchCdMaterialToPageForAdvancedSearch(getMap()));
    }

}
