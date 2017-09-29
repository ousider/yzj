
package xn.core.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.service.expimp.IExpImpService;
import xn.core.util.SpringContextUtil;
import xn.core.util.constants.ExpImpConstants;
import xn.core.util.excel.ExcelUtil;

/**
 * @Description: 导入导出工具类
 * @author zhangjs
 * @date 2016年8月24日 下午4:15:41
 */
@Controller
@RequestMapping("/ExpImp")
public class ExpImpController extends BaseController {
    private static Logger log = Logger.getLogger(ExpImpController.class);

    /**
     * @Description: 下载模板
     * @author 程彬
     * @param response
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping("/downExcelTemplate")
    @ResponseBody
    public Map<String, Object> downExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Throwable {
        log.debug("============BEGIN 下载模板================");

        String xmlName = getString("xmlName");
        String hiddenServiceName = getString("serviceName") == null ? "ExpImpService" : getString("serviceName");
        String downFlag = getString("downFlag");
        
        IExpImpService impExp = (IExpImpService) SpringContextUtil.getBean(hiddenServiceName);
        Map<String, List<Map<String, String>>> hiddenData = impExp.getHiddenData(getMap());
        Map<String, String> template = ExcelUtil.downExcelTemplate(xmlName, hiddenData);
        String file = template.get(ExpImpConstants.EXCEL_FILE_NAME);
        String downName = template.get(ExpImpConstants.EXCEL_NAME);
        if ("true".equals(downFlag)) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-exce");
            downName = URLEncoder.encode(downName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + downName);

            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            // 这里主要关闭。
            os.close();
            inputStream.close();
        }

        return getReturnMap();
    }

}
