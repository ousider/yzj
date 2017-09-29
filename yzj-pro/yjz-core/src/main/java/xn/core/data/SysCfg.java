package xn.core.data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

public final class SysCfg {
    private static final transient Logger log = Logger.getLogger(SysCfg.class);

    private static SysCfg sysCfg = null;

    private static Map<String, String> data = new HashMap<>();

    private static String webVersion = "";

    private static String finereportUrl = "";

    private static String finereportUsername = "";

    private static String basicUrl = "";
    private static String serviceUrl = "";
    private static String baseUrl = "";

    private static String supplyChainReceiptRelativePath = "";
    private static String supplyChainReceiptAbsolutePath = "";
    
    private static String basicInfoPaperRelativePath = "";
    private static String basicInfoPaperAbsolutePath = "";
    
    private static String fileUploadTempFileAbsolutePath = "";

    public static SysCfg getInstance(ServletContextEvent event) {
        event.getServletContext().setAttribute("webVersion", webVersion);
        event.getServletContext().setAttribute("finereport_url", finereportUrl);
        event.getServletContext().setAttribute("finereport_username", finereportUsername);
        event.getServletContext().setAttribute("basic_url", basicUrl);
        event.getServletContext().setAttribute("service_url", serviceUrl);
        baseUrl = event.getServletContext().getContextPath();
        event.getServletContext().setAttribute("base_url", baseUrl);
        event.getServletContext().setAttribute("supplyChain_receipt_relativePath", supplyChainReceiptRelativePath);
        event.getServletContext().setAttribute("supplyChain_receipt_absolutePath", supplyChainReceiptAbsolutePath);
        event.getServletContext().setAttribute("basicInfo_paper_relativePath", basicInfoPaperRelativePath);
        event.getServletContext().setAttribute("baiscInfo_paper_absolutePath", basicInfoPaperAbsolutePath);
        event.getServletContext().setAttribute("file_uploadTempFileAbsolutePath", fileUploadTempFileAbsolutePath);

        return sysCfg;
    }

    public static final String getProperty(String name) {
        return (String) data.get(name);
    }

    public static final String getProperty(String name, String defval) {
        String value = getProperty(name);
        if (value == null) {
            return defval;
        }
        return value.trim();
    }

    public static final void cleanup() {
        data = null;
    }

    public static final int getFileMaxSize() {
        return Integer.parseInt(getProperty("file.maxsize", "30"));
    }


    public static String getWebVersion() {
        return webVersion;
    }

    static {
        sysCfg = new SysCfg();
        try {
            PropertiesConfig cfg = null;
            InputStream in = SysCfg.class.getClassLoader().getResourceAsStream("syscfg.properties");
            cfg = new PropertiesConfig(in);
            data = cfg.getProperties();
            webVersion = data.get("webVersion");
            finereportUrl = data.get("finereport.url");
            finereportUsername = data.get("finereport.username");
            basicUrl = data.get("basic_url");
            serviceUrl = data.get("service_url");
            supplyChainReceiptRelativePath = data.get("supplyChain.receipt.relativePath");
            supplyChainReceiptAbsolutePath = data.get("supplyChain.receipt.absolutePath");
            basicInfoPaperRelativePath = data.get("basicInfo.paper.relativePath");
            basicInfoPaperAbsolutePath = data.get("basicInfo.paper.absolutePath");
            fileUploadTempFileAbsolutePath = data.get("file.uploadTempFileAbsolutePath");
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

