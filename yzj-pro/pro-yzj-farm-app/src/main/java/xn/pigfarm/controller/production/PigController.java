package xn.pigfarm.controller.production;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.page.BasePageInfo;
import xn.core.util.unityreturn.BuildReturnMapUtil;
import xn.pigfarm.service.production.IPigService;


/**
 * @Description: 猪只信息处理
 * @author Zhangjc
 * @date 2016年5月27日 下午1:37:32
 */
@Controller
@RequestMapping("/pig")
public class PigController extends BaseController {

    @Autowired
    private IPigService pigService;

    /**
     * @Description: 根据猪舍查询肉猪只
     * @author fangc
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/searchPigHisToPage")
    @ResponseBody
    public Map<String, Object> searchPigHisToPage(HttpServletRequest request) throws Exception {

        Map<String, Object> map = getMap();
        BasePageInfo pageInfo = pigService.searchPigHisToPage(map);
        return BuildReturnMapUtil.getReturnMap(pageInfo);

    };
}
