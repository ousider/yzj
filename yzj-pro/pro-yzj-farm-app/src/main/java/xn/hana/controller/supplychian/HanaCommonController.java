package xn.hana.controller.supplychian;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.hana.service.common.IhanaCommonService;

/**
 * @Description: 供应链模块
 * @author THL
 * @date 2016年8月15日 上午8:58:33
 */
@Component
@Controller
@RequestMapping("/hanaCommon")
public class HanaCommonController extends BaseController {

    @Autowired
    private IhanaCommonService hanaCommonService;

    /**
     * @Description:
     * @author THL
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchData")
    @ResponseBody
    public Map<String, Object> searchData(HttpServletRequest request) throws Exception {
        hanaCommonService.searchData(request);
        return getReturnMap();
    }

}
