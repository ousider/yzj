package xn.core.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import xn.core.data.SqlCon;
import xn.core.mapper.base.IParamMapper;
import xn.core.util.SpringContextUtil;
import xn.core.util.data.Maps;

public class DynamicDataSource {
    private static Logger log = Logger.getLogger(DynamicDataSource.class);

    public static final String DATA_SOURCE = "dataSourceHANA";

    public static void init(ServletContextEvent event) {
        DynamicDataSource.refresh();
    }

    public static void refresh() {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        ChildBeanDefinition beanDefinition = null;

        BasicDataSource dataSource = (BasicDataSource) SpringContextUtil.getBean("dataSource");
        String dataSourceUrl = dataSource.getUrl();

        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID rowId, BEAN_NAME AS beanName, IP_AND_PORT AS ipAndPort");
        sqlCon.addMainSql(", DB_NAME AS dbName, DB_USER_NAME AS dbUserName, DB_PASSWORD AS dbPassword");
        sqlCon.addMainSql(" FROM SYS_HANA_DB_PROPERTIES");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> dbList = paramMapper.getObjectInfos(sqlMap);
        if (dbList != null) {
            for (Map<String, Object> dbMap : dbList) {
                String beanName = DATA_SOURCE + Maps.getString(dbMap, "rowId");
                String url = "jdbc:sap://" + Maps.getString(dbMap, "ipAndPort") + "?reconnect=true&currentschema=" + Maps.getString(dbMap, "dbName");
                beanDefinition = new ChildBeanDefinition("dataSource");
                beanDefinition.setDestroyMethodName("close");
                // 注意：必须先注册到容器中，再得到Bean进行修改，否则数据源属性不能有效修改
                acf.registerBeanDefinition(beanName, beanDefinition);
                // 再得到数据源Bean定义，并修改连接相关的属性
                BasicDataSource dataSourceHANA = (BasicDataSource) applicationContext.getBean(beanName);
                try {
                    // 这里需要先关闭数据源，才可以使新的数据源设置生效
                    dataSourceHANA.close();
                }
                catch (SQLException e) {
                    log.warn("关闭从Spring获取的数据源时出现异常！", e);
                }
                dataSourceHANA.setDriverClassName("com.sap.db.jdbc.Driver");
                if (dataSourceUrl.indexOf("jdbc:mysql://192.168.1.253:3306/xnjia_farm?") >= 0) {
                    // 正式库
                    dataSourceHANA.setUrl(url);
                    dataSourceHANA.setUsername(Maps.getString(dbMap, "dbUserName"));
                    dataSourceHANA.setPassword(Maps.getString(dbMap, "dbPassword"));
                } else {
                    // 测试库
                    dataSourceHANA.setUrl("jdbc:sap://192.168.1.200:30015?reconnect=true&currentschema=XN_BREED_0327");
                    dataSourceHANA.setUsername("SYSTEM");
                    dataSourceHANA.setPassword("W66282141e");
                }
                dataSourceHANA.setInitialSize(0);
                dataSourceHANA.setMaxActive(20);
                dataSourceHANA.setMaxIdle(20);
                dataSourceHANA.setMinIdle(1);
                dataSourceHANA.setMaxWait(60000);
                dataSourceHANA.setLogAbandoned(true);
                dataSourceHANA.setRemoveAbandoned(true);
                dataSourceHANA.setRemoveAbandonedTimeout(180);
                dataSourceHANA.setTimeBetweenEvictionRunsMillis(10000);
                dataSourceHANA.setNumTestsPerEvictionRun(10);
                dataSourceHANA.setMinEvictableIdleTimeMillis(10000);

                Connection dataSourceHANAConnection = null;
                try {
                    dataSourceHANAConnection = DataSourceUtils.getConnection(dataSourceHANA);
                }
                finally {
                    try {
                        DataSourceUtils.doReleaseConnection(dataSourceHANAConnection, dataSourceHANA);
                        dataSourceHANAConnection = null;
                    }
                    catch (SQLException e) {
                        log.warn("加载sys_hana_db_properties数据源失败！", e);
                    }
                }
            }
        }
    }
}
