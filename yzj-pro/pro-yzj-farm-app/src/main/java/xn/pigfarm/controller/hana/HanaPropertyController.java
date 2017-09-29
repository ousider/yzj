package xn.pigfarm.controller.hana;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.service.hana.IHanaPropertyService;

/**
 * @Description: (Hana配置)
 * @author zj
 * @date 2017年4月24日 上午11:20:46
 */
@Component
@Controller
@RequestMapping("/hanaProperty")
public class HanaPropertyController extends BaseController {
    @Autowired
    private IHanaPropertyService hanaPropertyService;


    /**
     * @Description: hanaPrpty配置增，改
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editHana")
    @ResponseBody
    public Map<String, Object> editHana(HttpServletRequest request) throws Exception {
        hanaPropertyService.editHana(getMap());
        return getReturnMap();
    }

    /**
     * @Description: hanaPrpty配置 主页分页
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchHanaToPage")
    @ResponseBody
    public Map<String, Object> searchHanaToPage(HttpServletRequest request) throws Exception {

        return getReturnMap(hanaPropertyService.searchHanaToPage(getMap()));

    }

    /**
     * @Description: hanaPrpty配置 主页不分页
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchHanaToList")
    @ResponseBody
    public Map<String, Object> searchHanaToList(HttpServletRequest request) throws Exception {

        return getReturnMap(hanaPropertyService.searchHanaToPage(getMap()));

    }

    /**
     * @Description:hanaPrpty配置 删除
     * @author ZJ
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/deleteHanas")
    @ResponseBody
    public Map<String, Object> deleteHanas(HttpServletRequest request) throws Exception {

        hanaPropertyService.deleteHanas(getIds());
        return getReturnMap();
    }

    /**
     * @Description:测试批次入场
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/testDbConnect")
    @ResponseBody
    public Map<String, Object> testDbConnect(HttpServletRequest request) throws Exception {
        hanaPropertyService.testDbConnect(getMap());
        return getReturnMap();
    }

    /**
     * @Description:编辑数据源和猪场
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editDbAndFarm")
    @ResponseBody
    public Map<String, Object> editDbAndFarm(HttpServletRequest request) throws Exception {
        hanaPropertyService.editDbAndFarm(getMap());
        return getReturnMap();
    }

    /**
     * @Description:搜索数据源和猪场
     * @author THL
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchDbAndFarmToList")
    @ResponseBody
    public Map<String, Object> searchDbAndFarmToList(HttpServletRequest request) throws Exception {

        return getReturnMap(hanaPropertyService.searchDbAndFarmToList(getMap()));

    }

}
