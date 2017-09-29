package xn.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import xn.core.data.SysCfg;
import xn.core.util.JacksonUtil;

/**
 * @Description:文件处理
 * @author Zhangjc
 * @date 2016年5月20日 上午10:50:21
 */
@Controller
@RequestMapping("/fileOperate")
public class FileController extends BaseController {

    private static Logger log = Logger.getLogger(FileController.class);

    /**
     * @Description: 上传文件
     * @author Zhangjc
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unused" })
    @RequestMapping("/upload.do")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) throws Exception {
        Map map = getMap();

        // if (!ServletFileUpload.isMultipartContent(request)) {
        // Thrower.throwException(CoreBusiException.UPLOAD_REQUERST_ERROR);
        // }
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        // 怎么设计一个页面多个输入框
        MultipartFile item = fileMap.get("fileId");

        // 超过大小的文件不能上传，配置文件中已经配置，检查是否需要判断大小 TODO
        long size = item.getSize();
        SysCfg.getProperty("maxUploadSize");

        String fileName = item.getOriginalFilename();
        String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();


        // 真实目录名
        String filePath = SysCfg.getProperty("file.dir");
        // // 临时目录
        String tempPath = SysCfg.getProperty(" file.dir.temp");
        // 创建文件唯一名称
        String uuid = UUID.randomUUID().toString();
        // 真实上传路径
        StringBuffer sbRealPath = new StringBuffer();
        sbRealPath.append(filePath).append(uuid).append(".").append(fileEnd);
        String realPath = sbRealPath.toString();
        // 保存
        try {
            // 写入文件
            File file = new File(realPath);
            item.transferTo(file);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }

        // 上传成功，向父窗体返回数据:真实文件名,虚拟文件名,文件大小
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("isd", uuid);

        return getReturnMap(result);
    }

    /**
     * @Description: 上传文件
     * @author Zhangjc
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/upload2.do")
    public ResponseEntity<String> upload2(HttpServletRequest request) throws Exception {
        try {
            Map map = getMap();

            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            // 怎么设计一个页面多个输入框
            MultipartFile uploadFile = fileMap.get("fileId");

            String filename = uploadFile.getOriginalFilename();
            InputStream is = uploadFile.getInputStream();
            // 真实目录名
            String uploadFilePath = SysCfg.getProperty("file.dir");
            // 如果服务器已经存在和上传文件同名的文件，则输出提示信息
            File tempFile = new File(uploadFilePath + filename);
            if (tempFile.exists()) {
                boolean delResult = tempFile.delete();
                System.out.println("删除已存在的文件：" + delResult);
            }
            // 开始保存文件到服务器
            if (!filename.equals("")) {
                FileOutputStream fos = new FileOutputStream(uploadFilePath + filename);
                byte[] buffer = new byte[8192]; // 每次读8K字节
                int count = 0;
                // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count); // 向服务端文件写入字节流
                }
                fos.close(); // 关闭FileOutputStream对象
                is.close(); // InputStream对象
            }
        }
        catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        catch (IOException e) {
            log.error(e.getMessage());
        }

        // 上传成功，向父窗体返回数据:真实文件名,虚拟文件名,文件大小
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("isd", "ceshi ");

        HttpHeaders headers = new HttpHeaders();  
        MediaType mt=new MediaType("text","html",Charset.forName("utf-8"));  
        headers.setContentType(mt);
        ResponseEntity<String> re=null;  
        re = new ResponseEntity<String>(JacksonUtil.objectToJackson(getReturnMap(result)), headers, HttpStatus.OK);
        return re;  
        // return getReturnMap(result);
    }

    /**
     * @Description: 下载文件
     * @author Zhangjc
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download.do")
    @ResponseBody
    public Map<String, Object> download(HttpServletResponse response,HttpServletRequest request) throws Exception {
        
        String template = getString("template");
        String fileName = getString("fileName");

        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "downloadtemplate";
            template = path + File.separator + template;
            InputStream inputStream = new FileInputStream(new File(template));

            OutputStream os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            // 这里主要关闭。
            os.close();

            inputStream.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        // 返回值要注意，要不然就出现下面这句错误！
            //java+getOutputStream() has already been called for this response
        return null;
    }
	    /**
     * 上传文件
     * 
     * @author geloin
     * @date 2012-3-29 下午4:01:41
     * @param request
     * @return
     * @throws Exception
     */
    // @RequestMapping("upload.do")
    // public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //
    // ServletFileUpload.isMultipartContent(request);
    //
    // MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
    // Map<String, MultipartFile> fileMap = mRequest.getFileMap();
    // MultipartFile item = fileMap.get("fileId");
    //
    // // 获得文件名：
    // String filename = item.getOriginalFilename();
    // // 获得输入流：
    // InputStream input = item.getInputStream();
    // // 写入文件
    //
    //
    // request.setCharacterEncoding("utf-8");
    // DiskFileItemFactory factory = new DiskFileItemFactory();
    // // 最大缓存
    // // factory.setSizeThreshold(5 * 1024);
    // // 设置临时文件目录
    // // factory.setRepository(new File("C:/work/upload/temp"));
    // // ServletFileUpload upload = new ServletFileUpload(factory);// 专门用来完成文件上传的类
    // // List<FileItem> items = upload.parseRequest(request);
    //
    // Map<String, Object> map = new HashMap<String, Object>();
    //
    // Object obj = request.getAttribute("fileId");
    // Object obj1 = request.getParameter("fileId");
    //
    // // 获取并解析文件类型和支持最大值
    // String functionId = request.getParameter("functionId");
    // String fileType = request.getParameter("fileType");
    //
    //
    // // 真实目录名
    // String filePath = SysCfg.getProperty("file.dir");
    // String tempPath = SysCfg.getProperty(" file.dir.temp");
    //
    // String maxSize = SysCfg.getProperty("file.maxSize");
    // initFileItemFactory();
    // Iterator<FileItem> files = getFileIterator(request);
    // // for (FileItem item : items) {
    // if ((item.getName() != null) && (!"".equals(item.getName()))) {
    //
    // // 文件名
    // String fileName = item.getName();
    // // 检查文件后缀格式
    // String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    //
    // // String nfileId = upload(file, filePath, file.getName(), ftpSite, isNeedSuffix);
    // // bus.getDataObject().put("fileId", nfileId);
    //
    // // //检查文件后缀格式 TODO
    // // String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
    // // if(fileType!=null && !"".equals(fileType.trim())){
    // // boolean isRealType = false;
    // // String[] arrType = fileType.split(",");
    // // for (String str : arrType) {
    // // if(fileEnd.equals(str.toLowerCase())){
    // // isRealType = true;
    // // break;
    // // }
    // // }
    // // if(!isRealType){
    // // //提示错误信息:文件格式不正确
    // // super.printJsMsgBack(response, "文件格式不正确!");
    // // return null;
    // // }
    // // }
    //
    // // 创建文件唯一名称
    // String uuid = UUID.randomUUID().toString();
    // // 真实上传路径
    // StringBuffer sbRealPath = new StringBuffer();
    // sbRealPath.append("C:/work/upload/temp").append(uuid).append(".").append(fileEnd);
    // // 写入文件
    // File file = new File(sbRealPath.toString());
    // item.transferTo(file);
    // // 上传成功，向父窗体返回数据:真实文件名,虚拟文件名,文件大小
    // StringBuffer sb = new StringBuffer();
    // sb.append("window.returnValue='").append(fileName).append(",").append(uuid).append(".").append(fileEnd).append(",").append(file
    // .length()).append("';");
    // sb.append("window.close();");
    //
    // response.setHeader("Content-type", "text/html;charset=UTF-8");
    // response.setCharacterEncoding("UTF-8");
    // String str = JacksonUtil.objectToJackson(sb.toString());
    // response.getWriter().print(str);
    // log.info("上传文件成功,JS信息：" + sb.toString());
    // // }
    // }
    // // }
    //
    // // 别名
    // // String[] alaises = ServletRequestUtils.getStringParameters(request, "上传");
    // //
    // // String[] params = new String[] { "alais" };
    // // Map<String, Object[]> values = new HashMap<String, Object[]>();
    // // values.put("alais", alaises);
    // //
    // // List<Map<String, Object>> result = FileUtil.upload(request, params, values);
    // //
    // // map.put("result", result);
    //
    // }


    /**
     * 下载
     * 
     * @author geloin
     * @date 2012-3-29 下午5:24:14
     * @param attachment
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    // @RequestMapping("download")
    // public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //
    // String storeName = "201205051340364510870879724.zip";
    // String realName = "Java设计模式.zip";
    // String contentType = "application/octet-stream";
    //
    // FileUtil.download(request, response, storeName, contentType, realName);
    //
    // return null;
    // }
}
