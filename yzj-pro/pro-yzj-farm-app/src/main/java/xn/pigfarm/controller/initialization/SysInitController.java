package xn.pigfarm.controller.initialization;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.execute.IPigInputExecute;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.service.basicinfo.IMaterialService;
import xn.pigfarm.service.initialization.IInputHouseService;
import xn.pigfarm.service.initialization.IInputPigService;


/**
 * @Description: 初始化系统
 * @author zhangjs
 * @date 2016年8月16日 下午5:10:01
 */
@Component
@Controller
@RequestMapping("/SysInit")
public class SysInitController extends BaseController {

    @Autowired
    private IPigInputExecute iPigInputExecute;
    
    @Autowired
    private IInputPigService inputService;

    @Autowired
    private IInputHouseService inputHouseService;
    
    @Autowired
    private IMaterialService materialService;

    /**
     * @Description: 初始化系统
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/inputPig.do")
    @ResponseBody
    public Map<String, Object> inputPig(HttpServletRequest request) throws Exception {
        Map<String, Object> countMap = iPigInputExecute.inputPig();
        pigInfoRefresh();
        basicRefresh();
        return getReturnMap(countMap);
    }

    /**
     * @Description: 查询导入猪只的（1：未执行 3：执行成功 4：执行失败）
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchInputPig.do")
    @ResponseBody
    public Map<String, Object> searchInputPig(HttpServletRequest request) throws Exception {

        String status = getString("status");
        return getReturnMap(inputService.searchInputPig(status));
    }

    /**
     * @Description: 删除导入未执行猪只 （真正删除）
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteInputPigs.do")
    @ResponseBody
    public Map<String, Object> deleteInputPigs(HttpServletRequest request) throws Exception {

        inputService.deleteInputPigs(getIds());
        return getReturnMap();
    }

    /**
     * @Description: 初始化猪舍
     * @author Administrator
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/inputHouse.do")
    @ResponseBody
    public Map<String, Object> inputHouse(HttpServletRequest request) throws Exception {

        String xmlName = getString("xmlName");
        inputHouseService.editInputHouse(getDetialListStr(), xmlName);
        cacheRefresh();
        return getReturnMap();
    }

    /**
     * @Description: 肉猪初始化
     * @author Administrator
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/inputPork.do")
    public Map<String, Object> inputPork(HttpServletRequest request) throws Exception{
        
        String xmlName = getString("xmlName");
        iPigInputExecute.inputPork(getDetialListStr(), xmlName);
        return getReturnMap();
    }
    
    /**
     * @Description: 肉猪主数据初始化
     * @author Administrator
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping("/inputMaterialPork.do")
    public Map<String, Object> inputMaterialPork(HttpServletRequest request) throws Exception{
        String materialType = getString("materialType").trim();
        
        Map<String, Object> map = getMap();
        
        String gridList = String.valueOf(map.get("gridList"));
        List<Map> jsonList = getJsonList(gridList, Map.class);
        for(int mm=0;mm<jsonList.size();mm++){
            Map maps = jsonList.get(mm);
            maps.put("rowId", 0);
            iPigInputExecute.inputMaterialPork(maps, materialType);
        }
        cacheRefresh();
        return getReturnMap();
    }
}
