
package xn.pigfarm.controller.production;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.unityreturn.BuildReturnMapUtil;
import xn.pigfarm.service.production.ICancelService;

/**
 * @Description: 撤销事件
 * @author thl
 * @date 2016年7月20日 上午13:02:58
 */
@Component
@Controller
@RequestMapping("/cancel")
public class CancelController extends BaseController {

    @Autowired
    private ICancelService cancelService;

    /**
     * @Description: 单据撤销
     * @author thl
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editEventCancel")
    @ResponseBody
    public Map<String, Object> editEventCancel(HttpServletRequest request) throws Exception {
        cancelService.editEventCancel(getMap());
        // 刷新缓存
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

}
