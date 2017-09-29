package xn.core.util.uploadFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.StringUtil;

import xn.core.data.SqlCon;
import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.SpringContextUtil;
import xn.core.util.time.TimeUtil;

/**
 * @Description: 上传文件工具类
 * @author THL
 * @date 2017年6月8日 上午10:55:10
 */
public class UploadFileUtil {

    // 上传临时文件路径
    public static final String FILE_UPLOADTEMPFILEABSOLUTEPATH = "file_uploadTempFileAbsolutePath";

    // 上传文件名0-Z随机数
    private static final String[] FILE_NAME_RANDOM = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    // 日期格式
    private static final String DATE_FORMAT = "yyyyMMdd";

    private UploadFileUtil() {

    }

    /**
     * 上传临时文件
     * 
     * @author THL
     * @param request
     * @param prefix 文件前缀名
     * @param tableName 需要去重文件名的表名
     * @param fieldName 需要去重文件名的字段名
     * @return 上传的文件名
     * @throws Exception
     */
    public static List<String> uploadTempFile(HttpServletRequest request, String prefix, String tableName, String fieldName)
            throws Exception {
        return uploadFile(request, (String) request.getServletContext().getAttribute(FILE_UPLOADTEMPFILEABSOLUTEPATH), prefix, tableName, fieldName);
    }

    /**
     * 上传文件
     * 
     * @author THL
     * @param request
     * @param ctxPath 上传文件路径
     * @param prefix 文件前缀名
     * @param sqlCon 需要去除重复名字的Sql
     * @return 上传的文件名
     * @throws Exception
     */
    public static synchronized List<String> uploadFile(HttpServletRequest request, String ctxPath, String prefix, String tableName,
            String fieldName)
            throws Exception {
        List<String> fileNameList = new ArrayList<String>();

        if (request instanceof MultipartHttpServletRequest) {
            // 获取配置文件中tempFile（零时文件）上传的路径（文件夹）
            File file = new File(ctxPath);
            if (!file.exists()) {
                file.mkdir();
            }

            // 文件上传的请求
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            // 获取请求的参数
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();

            // 用hasNext() 判断是否有值，用next()方法把元素取出。
            while (it.hasNext()) {
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                String fileNameWithSuffix = null;

                if (mFile.getSize() != 0 && !"".equals(mFile.getName())) {
                    // 原文件名（带后缀）
                    String originalFilename = mFile.getOriginalFilename();
                    // 文件后缀
                    String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());

                    // 新文件名（带后缀）
                    fileNameWithSuffix = createUnqiueName(ctxPath, prefix, fileSuffix, tableName, fieldName);

                    InputStream inputStream = null;
                    OutputStream outputStream = null;
                    int byteread = 0;
                    try {
                        inputStream = mFile.getInputStream();
                        outputStream = new FileOutputStream(ctxPath + fileNameWithSuffix);
                        byte[] buffer = new byte[1024];
                        while ((byteread = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, byteread);
                        }
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally {
                        try {
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "请不要上传空文件和空文件名");
                }

                fileNameList.add(fileNameWithSuffix);
            }
        } else {
            Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "传入的Request不是" + MultipartHttpServletRequest.class.getName()
                    + "请使用jAjax.submit_file完成文件功能");
        }

        return fileNameList;
    }

    // 生成不重复的系统存名称
    private static String createUnqiueName(String ctxPath, String prefix, String fileSuffix, String tableName, String fieldName) throws Exception {
        Date date = new Date();
        String currentDate = TimeUtil.format(date, DATE_FORMAT);

        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);

        StringBuffer stringBuffer = null;
        Random random = new Random();
        boolean fileNameExistsFlag = true;
        while (fileNameExistsFlag) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(prefix);
            stringBuffer.append(currentDate);
            for (int i = 0; i < 5; i++) {
                stringBuffer.append(UploadFileUtil.FILE_NAME_RANDOM[random.nextInt(UploadFileUtil.FILE_NAME_RANDOM.length)]);
            }

            // 拼接上后缀
            stringBuffer.append(fileSuffix);

            // 文件名带后缀
            String checkFileName = stringBuffer.toString();

            File file = new File(ctxPath+checkFileName);

            // 不存在这个文件时
            if (!(file.isFile() && file.exists())) {
                if (StringUtil.isNotEmpty(tableName)) {
                    // 需要CHECK表中是否存在这个文件
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql("SELECT ROW_ID FROM " + tableName);
                    sqlCon.addCondition(checkFileName, " WHERE " + fieldName + " = ?");
    
                    Map<String, String> sqlMap = new HashMap<String, String>();
                    sqlMap.put("sql", sqlCon.getCondition());
    
                    List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
                    // 数据库中不存在这个名字
                    if (list == null || list.isEmpty()) {
                        // 现生成的文件名中不存在这个名字
                        fileNameExistsFlag = false;
                    }
                }else{
                    // 不需要CHECK表中是否存在这个文件
                    // 现生成的文件名中不存在这个名字
                    fileNameExistsFlag = false;
                }
            }
        }
        return stringBuffer.toString();
    }


    /**
     * @Description: 拷贝文件
     * @author THL
     * @param originPath
     * @param targetPath
     * @param fileName
     * @return
     * @throws Exception
     */
    public static void copyFile(String originPath, String targetPath, String fileName)
            throws Exception {
        File file = new File(originPath + fileName);
        if (file.isFile() && file.exists()) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            int byteread = 0;
            try{
                inputStream = new FileInputStream(file);
                outputStream = new FileOutputStream(targetPath + fileName);
                byte[] buffer = new byte[1024];
                while ((byteread = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteread);
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        } else {
            Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "系统异常，临时文件不存在！");
        }
    }

}
