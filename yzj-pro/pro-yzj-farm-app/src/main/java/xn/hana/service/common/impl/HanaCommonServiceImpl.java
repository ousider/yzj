package xn.hana.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;
import xn.core.util.SpringContextUtil;
import xn.core.util.data.StringUtil;
import xn.hana.mapper.common.IHanaParamMapper;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;

@Service("HANACommonService")
public class HanaCommonServiceImpl implements IhanaCommonService {

    // @Autowired
    // private IHanaParamMapper hanaParamMapper;

    @Override
    public List<Map<String, Object>> searchData(HttpServletRequest request) throws Exception {
        // SqlCon sqlCon = new SqlCon();
        // sqlCon.addMainSql("SELECT * FROM XN_BREED_TEST.OPRC");
        // sqlCon.addMainSql("SELECT * FROM XN_BREED_TEST.MTC_ITFC");
        // // sqlCon.addMainSql("SELECT * FROM MTC_ITFC");
        // Map<String, String> map = new HashMap<String, String>();
        // map.put("sql", sqlCon.getCondition());
        // List<Map<String, Object>> ss = hanaParamMapper.getObjectInfos(map);

        // MTC_ITFC mtcITFC = new MTC_ITFC();
        // mtcITFC.setMTC_BaseEntry("Test");
        // PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // Resource[] resource = resolver.getResources("classpath:xn/hana/mapper/common/HanaParamMapper.xml");


        // Resource[] resource = new PathResource[] { new PathResource(
        // "D:/JAVA/workspace/xnjia/xnjia-farm-app/src/main/resources/xn/hana/mapper/common/HanaParamMapper.xml") };

        // String modelUrl = "xn.hana.model";
        // Properties properties = new Properties();
        // properties.setProperty("driverClassName", "com.sap.db.jdbc.Driver");
        // properties.setProperty("url", "jdbc:sap://192.168.1.200:30015?reconnect=true&currentschema=XN_BREED_0320");
        // properties.setProperty("username", "SYSTEM");
        // properties.setProperty("password", "W66282141e");
        // properties.setProperty("initialSize", "0");
        // properties.setProperty("maxActive", "20");
        // properties.setProperty("maxIdle", "20");
        // properties.setProperty("minIdle", "1");
        // properties.setProperty("maxWait", "60000");
        // properties.setProperty("logAbandoned", "true");
        // properties.setProperty("removeAbandoned", "true");
        // properties.setProperty("removeAbandonedTimeout", "180");
        // properties.setProperty("timeBetweenEvictionRunsMillis", "10000");
        // properties.setProperty("numTestsPerEvictionRun", "10");
        // properties.setProperty("minEvictableIdleTimeMillis", "10000");
        // SqlSession sqlSession = null;
        // try {
        // DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        // SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // factoryBean.setDataSource(dataSource);
        // factoryBean.setMapperLocations(resource);
        // factoryBean.setTypeAliasesPackage(modelUrl);
        // SqlSessionFactory sessionFactory = factoryBean.getObject();
        // sqlSession = sessionFactory.openSession();
        // IHanaParamMapper hanaParamMapper02 = sqlSession.getMapper(IHanaParamMapper.class);
        // hanaParamMapper02.insert(mtcITFC);
        // }
        // finally {
        // if (sqlSession != null) {
        // sqlSession.close();
        // sqlSession = null;
        // }
        // }

        // Properties properties01 = new Properties();
        // properties01.setProperty("driverClassName", "com.sap.db.jdbc.Driver");
        // properties01.setProperty("url", "jdbc:sap://192.168.1.200:30015?reconnect=true&currentschema=XN_BREED_0327");
        // properties01.setProperty("username", "SYSTEM");
        // properties01.setProperty("password", "W66282141e");
        // properties01.setProperty("initialSize", "0");
        // properties01.setProperty("maxActive", "20");
        // properties01.setProperty("maxIdle", "20");
        // properties01.setProperty("minIdle", "1");
        // properties01.setProperty("maxWait", "60000");
        // properties01.setProperty("logAbandoned", "true");
        // properties01.setProperty("removeAbandoned", "true");
        // properties01.setProperty("removeAbandonedTimeout", "180");
        // properties01.setProperty("timeBetweenEvictionRunsMillis", "10000");
        // properties01.setProperty("numTestsPerEvictionRun", "10");
        // properties01.setProperty("minEvictableIdleTimeMillis", "10000");
        // SqlSession sqlSession01 = null;
        // try {
        // DataSource dataSource01 = BasicDataSourceFactory.createDataSource(properties01);
        // SqlSessionFactoryBean factoryBean01 = new SqlSessionFactoryBean();
        // factoryBean01.setDataSource(dataSource01);
        // factoryBean01.setMapperLocations(resource);
        // factoryBean01.setTypeAliasesPackage(modelUrl);
        // SqlSessionFactory sessionFactory01 = factoryBean01.getObject();
        // sqlSession01 = sessionFactory01.openSession();
        // IHanaParamMapper hanaParamMapper01 = sqlSession01.getMapper(IHanaParamMapper.class);
        // hanaParamMapper01.insert(mtcITFC);
        // }
        // finally {
        // if (sqlSession01 != null) {
        // sqlSession01.close();
        // sqlSession01 = null;
        // }
        // }

        return new ArrayList<>();
    }

    @Override
    public int insertMTC_ITFC(MTC_ITFC mtcITFC) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = resolver.getResources("classpath:xn/hana/mapper/common/HanaParamMapper.xml");
        Class<Object>[] typeAliases = new Class[] { MTC_ITFC.class };

        SqlSession sqlSession = null;
        SqlSessionFactory sqlSessionFactory = null;
        try {
            String beanName = mtcITFC.getDB_Bean_Name();
            if (StringUtil.isBlank(beanName)) {
                Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "【" + mtcITFC.getMTC_DataFile() + "】，mtc_ITFC.getDB_Bean_Name不能为空！");
            }
            BasicDataSource dataSource = (BasicDataSource) SpringContextUtil.getBean(beanName);
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);
            factoryBean.setMapperLocations(resource);
            factoryBean.setTypeAliases(typeAliases);
            sqlSessionFactory = factoryBean.getObject();
            sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
            DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
            dataSourceTransactionManager.setDataSource(dataSource);
            IHanaParamMapper hanaParamMapper = sqlSession.getMapper(IHanaParamMapper.class);
            return hanaParamMapper.insert(mtcITFC);
        }
        finally {
            if (sqlSession != null) {
                SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
                sqlSession = null;
            }
        }
    }

    @Override
    public int insertsMTC_ITFC(List<MTC_ITFC> list) throws Exception {
        Map<String, List<MTC_ITFC>> dbBeanNameAndMTC_ITFCList = new HashMap<String, List<MTC_ITFC>>();
        for (MTC_ITFC mtcITFC : list) {
            if (StringUtil.isBlank(mtcITFC.getDB_Bean_Name())) {
                Thrower.throwException(CoreBusiException.ERROR_MESSAGE, "【" + mtcITFC.getMTC_DataFile() + "】，mtc_ITFC.getDB_Bean_Name不能为空！");
            }
            // 如果dbBeanNameAndMTC_ITFCList已存在
            if (dbBeanNameAndMTC_ITFCList.containsKey(mtcITFC.getDB_Bean_Name())) {
                dbBeanNameAndMTC_ITFCList.get(mtcITFC.getDB_Bean_Name()).add(mtcITFC);
            } else {
                // 如果dbBeanNameAndMTC_ITFCList不存在
                List<MTC_ITFC> mtcItfcList = new ArrayList<MTC_ITFC>();
                mtcItfcList.add(mtcITFC);
                dbBeanNameAndMTC_ITFCList.put(mtcITFC.getDB_Bean_Name(), mtcItfcList);
            }
        }

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = resolver.getResources("classpath:xn/hana/mapper/common/HanaParamMapper.xml");
        Class<Object>[] typeAliases = new Class[] { MTC_ITFC.class };

        SqlSession sqlSession = null;
        SqlSessionFactory sqlSessionFactory = null;
        int i = 0;
        for (Entry<String, List<MTC_ITFC>> entry : dbBeanNameAndMTC_ITFCList.entrySet()) {
            try{
                String beanName = entry.getKey();

                BasicDataSource dataSource = (BasicDataSource) SpringContextUtil.getBean(beanName);
                SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
                factoryBean.setDataSource(dataSource);
                factoryBean.setMapperLocations(resource);
                factoryBean.setTypeAliases(typeAliases);
                sqlSessionFactory = factoryBean.getObject();
                sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
                // DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
                // dataSourceTransactionManager.setDataSource(dataSource);
                IHanaParamMapper hanaParamMapper = sqlSession.getMapper(IHanaParamMapper.class);
                i = i + hanaParamMapper.inserts(entry.getValue());
            }
            finally {
                if (sqlSession != null) {
                    SqlSessionUtils.closeSqlSession(sqlSession, sqlSessionFactory);
                    sqlSession = null;
                }
            }
        }
        return i;
    }

}
