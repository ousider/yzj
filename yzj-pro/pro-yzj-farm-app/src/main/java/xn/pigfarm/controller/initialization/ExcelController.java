
package xn.pigfarm.controller.initialization;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.controller.BaseController;
import xn.pigfarm.service.expimp.IExcelExportService;

/**
 * @Description: 生产事件录入
 * @author fangc
 * @date 2016年4月25日 上午11:47:58
 */
@Component
@Controller
@RequestMapping("/excel")
public class ExcelController extends BaseController {
    private static Logger log = Logger.getLogger(ExcelController.class);

    @Autowired
    private IExcelExportService excelExportService;

    /**
     * @Description:excel导出
     * @author 程彬
     * @param request
     * @return
     * @throws Exception
     */
    // @RequestMapping("/exportExcelModel")
    // @ResponseBody
    // public Map<String, Object> exportExcelModel(HttpServletResponse response, HttpServletRequest request) throws Exception {

    // String fileName = "猪只入场.xls";
        //
        // fileName = URLEncoder.encode(fileName, "UTF-8");
        // response.setCharacterEncoding("utf-8");
        // response.setContentType("application/vnd.ms-exce");
        // response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        // try {
        //
        // String file = excelExportModel.exportExcelMo();
        //
        // InputStream inputStream = new FileInputStream(file);
        //
        // OutputStream os = response.getOutputStream();
        // byte[] b = new byte[2048];
        // int length;
        // while ((length = inputStream.read(b)) > 0) {
        // os.write(b, 0, length);
        // }
        //
    // // 这里主要关闭。
        // os.close();
        //
        // inputStream.close();
        // }
        // catch (FileNotFoundException e) {
        // log.error(e.getMessage());
        // }
        // catch (IOException e) {
        // log.error(e.getMessage());
        // }

    // return getReturnMap();
    // }

    /**
     * @Description: 系统初始化猪只上传excel文件到服务器，并返回excel数据到页面
     * @author 程彬
     * @param response
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping("/exportPig.do")
    @ResponseBody
    public Map<String, Object> exportPig(HttpServletRequest request) throws Exception {

        log.info("开始导入EXCEL.......");
        String xmlName = getString("xmlName");
        String pigType = getString("pigType");
        excelExportService.exportPig(request, xmlName, pigType);

        return getReturnMap();
    }

    @RequestMapping("/exportPork.do")
    @ResponseBody
    public Map<String,Object> exportPork(HttpServletRequest request) throws Exception{
        log.info("开始导入EXCEL.......");
        String xmlName = getString("xmlName");
        return getReturnMap(excelExportService.porkUpload(request, xmlName));
    }
    
    /**
     * @Description: 导入猪舍excel文件
     * @author wch
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportPigHouse.do")
    @ResponseBody
    public Map<String, Object> exportPigHouse(HttpServletRequest request) throws Exception {

        log.info("开始导入EXCEL.......");
        String xmlName = getString("xmlName");
        String pigType = getString("pigType");
        List<Map<String, Object>> earBlandUpload = excelExportService.houseUpload(request, xmlName);

        return getReturnMap(earBlandUpload);
    }

    /**
     * @Description: 猪只耳牌号入场上传excel数据到服务器，并返回excel数据到页面
     * @author 程彬
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/earBlandUpload")
    @ResponseBody
    public Map<String, Object> earBlandUpload(HttpServletResponse response, HttpServletRequest request) throws Exception {

        String xmlName = getString("xmlName");
        String pigType = getString("pigType");
        List<Map<String, Object>> earBlandUpload = excelExportService.earBlandUpload(request, xmlName, pigType);

        // getReturnMap(basicInfoService.searchLineToPage());
        return getReturnMap(earBlandUpload);
    }
    
    /**
     * @Description: 导入肉猪主数据 excel文件
     * @author cb
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/exportMaterialTemplatePork.do")
    @ResponseBody
    public Map<String, Object> exportMaterialTemplatePork(HttpServletRequest request) throws Exception {

        log.info("开始导入EXCEL.......");
        String xmlName = getString("xmlName");
        List<Map<String, Object>> earBlandUpload = excelExportService.exportMaterialTemplatePork(request, xmlName);

        return getReturnMap(earBlandUpload);
    }
}
