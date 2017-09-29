
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
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.service.production.IEventService;

/**
 * @Description: 留种选种
 * @author yangy
 * @date 2016年10月10日 上午11:47:58
 */
@Component
@Controller
@RequestMapping("/event")
public class EventController extends BaseController {

    @Autowired
    private IEventService eventService;


    // 留种事件保存
    @RequestMapping("/editSeedPig")
    @ResponseBody
    public Map<String, Object> editSeedPig(HttpServletRequest request) throws Exception {
        BillModel model = getBean(BillModel.class);
        eventService.editSeedPig(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    // 选种事件保存
    @RequestMapping("/editSelectionPig")
    @ResponseBody
    public Map<String, Object> editSelectionPig(HttpServletRequest request) throws Exception {
        BillModel model = getBean(BillModel.class);
        eventService.editSelectionPig(model, getString("eventName"), getDetialListStr());
        pigInfoRefresh();
        return BuildReturnMapUtil.getReturnMap();
    };

    
}
