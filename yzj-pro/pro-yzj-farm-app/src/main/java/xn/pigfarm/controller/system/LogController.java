package xn.pigfarm.controller.system;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.core.util.data.StringUtil;
import xn.pigfarm.service.system.ILogService;

@Controller
@RequestMapping("/logs")
public class LogController extends BaseController {

    @Autowired
    private ILogService logService;

    @RequestMapping("editActionUrl")
    @ResponseBody
    public Map<String, Object> editActionUrl(HttpServletResponse response) {

        try {
            String url = getString("url");
            String text = getString("text");
            Long id = getLong("id");
            logService.editActionUrl(id, url, text);
        }
        catch (Exception e) {
            log.error("插入追踪信息错误" + StringUtil.substring(e.getMessage(), 0, 250));
        }
        return getReturnMap();
    }


}

