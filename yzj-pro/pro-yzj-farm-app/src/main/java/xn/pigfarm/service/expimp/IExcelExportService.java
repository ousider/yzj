package xn.pigfarm.service.expimp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IExcelExportService {

    /**
     * @Description: 初始化猪只上传excel文件到服务器，并插入到数据库
     * @author 程彬
     * @param request
     * @param xmlName 配置文件名
     * @param pigType 猪只类型
     * @throws Exception
     * @throws Throwable
     */
    public void exportPig(HttpServletRequest request, String xmlName, String pigType) throws Exception;

    public List<Map<String, Object>> earBlandUpload(HttpServletRequest request, String xmlName, String pigType) throws Exception;

    public List<Map<String, Object>> houseUpload(HttpServletRequest request, String xmlName) throws Exception;

    public List<Map<String, Object>> porkUpload(HttpServletRequest request, String xmlName) throws Exception;
    
    /**
     * @Description: 初始化猪只上传excel文件到服务器
     * @author 程彬
     * @param request
     * @param xmlName
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> exportMaterialTemplatePork(HttpServletRequest request, String xmlName) throws Exception;
}
