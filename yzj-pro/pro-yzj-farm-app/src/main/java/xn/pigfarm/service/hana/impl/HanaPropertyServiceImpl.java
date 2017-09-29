package xn.pigfarm.service.hana.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.NameEnum;
import xn.core.datasource.DynamicDataSource;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.pigfarm.exception.hana.HanaPropertyException;
import xn.pigfarm.mapper.hana.SysHanaDbFarmMapper;
import xn.pigfarm.mapper.hana.SysHanaDbPropertiesMapper;
import xn.pigfarm.model.hana.SysHanaDbFarmModel;
import xn.pigfarm.model.hana.SysHanaDbPropertiesModel;
import xn.pigfarm.service.hana.IHanaPropertyService;


@Service("HanaPropertyService")
public class HanaPropertyServiceImpl extends BaseServiceImpl implements IHanaPropertyService {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private SysHanaDbPropertiesMapper sysHanaDbPropertiesMapper;

    @Autowired
    private SysHanaDbFarmMapper sysHanaDbFarmMapper;

    /**
     * hanaPrpty配置
     * 新增,编辑，复制新增
     */

    public void editHana(Map<String, Object> inputMap) throws Exception {
        String beanName = Maps.getString(inputMap, "beanName");
        String ipAndPort = Maps.getString(inputMap, "ipAndPort");
        String dbName = Maps.getString(inputMap, "dbName");
        String dbUserName = Maps.getString(inputMap, "dbUserName");
        String dbPassword = Maps.getString(inputMap, "dbPassword");
        if (StringUtil.isBlank(beanName)) {
            Thrower.throwException(HanaPropertyException.BEAN_NAME_IS_NULL);
        } else {
            beanName = beanName.trim();
        }

        if (beanName.equalsIgnoreCase("dataSource")) {
            Thrower.throwException(HanaPropertyException.BEAN_NAME_DATASOURCE);
        }

        if (StringUtil.isBlank(ipAndPort)) {
            Thrower.throwException(HanaPropertyException.IP_AND_PORT_NULL);
        } else {
            ipAndPort = ipAndPort.trim();
        }
        if (StringUtil.isBlank(dbName)) {
            Thrower.throwException(HanaPropertyException.DB_NAME_IS_NULL);
        } else {
            dbName = dbName.trim();
        }
        if (StringUtil.isBlank(dbUserName)) {
            Thrower.throwException(HanaPropertyException.DB_USER_NAME_IS_NULL);
        } else {
            dbUserName = dbUserName.trim();
        }
        if (StringUtil.isBlank(dbPassword)) {
            Thrower.throwException(HanaPropertyException.DB_PASSWORD_IS_NULL);
        } else {
            dbPassword = dbPassword.trim();
        }

        Date createDate = new Date();
        Long rowId = Maps.getLong(inputMap, "rowId");
        SysHanaDbPropertiesModel sysHanaDbPropertiesModel = new SysHanaDbPropertiesModel();
        sysHanaDbPropertiesModel.setRowId(rowId);
        long farmIsExist = ParamUtil.isExist("sys_hana_db_properties", sysHanaDbPropertiesModel.getRowId(), new String[] { Maps.getString(inputMap,
                "beanName") }, "BEAN_NAME");
        if (farmIsExist > 0) {
            Thrower.throwException(HanaPropertyException.BEAN_NAME_DUPLICATE_ERROR);
        }

        if (sysHanaDbPropertiesModel.getRowId() == 0L) {
            sysHanaDbPropertiesModel.setStatus(CommonConstants.STATUS);
            sysHanaDbPropertiesModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            sysHanaDbPropertiesModel.setBeanName(beanName);
            sysHanaDbPropertiesModel.setIpAndPort(ipAndPort);
            sysHanaDbPropertiesModel.setDbName(dbName);
            sysHanaDbPropertiesModel.setDbUserName(dbUserName);
            sysHanaDbPropertiesModel.setDbPassword(dbPassword);
            sysHanaDbPropertiesModel.setCreateId(getEmployeeId());
            sysHanaDbPropertiesModel.setCreateDate(createDate);
            sysHanaDbPropertiesMapper.insert(sysHanaDbPropertiesModel);
        } else {
            sysHanaDbPropertiesModel.setRowId(rowId);
            sysHanaDbPropertiesModel.setBeanName(beanName);
            sysHanaDbPropertiesModel.setIpAndPort(ipAndPort);
            sysHanaDbPropertiesModel.setDbName(dbName);
            sysHanaDbPropertiesModel.setDbUserName(dbUserName);
            sysHanaDbPropertiesModel.setDbPassword(dbPassword);
            sysHanaDbPropertiesMapper.update(sysHanaDbPropertiesModel);
        }

        // 加载HANA配置
        DynamicDataSource.refresh();
    }

    /**
     * hanaPrpty配置
     * 分页查询
     */

    @Override
    public BasePageInfo searchHanaToPage(Map<String, Object> map) throws Exception {
        setToPage();
        List<SysHanaDbPropertiesModel> SysHanaDbPropertiesList = sysHanaDbPropertiesMapper.searchToList();
        return getPageInfo(SysHanaDbPropertiesList);

    }

    /**
     * hanaPrpty配置
     * 不分页查询
     */

    @Override
    public List<SysHanaDbPropertiesModel> searchHanaToList(Map<String, Object> map) throws Exception {
        List<SysHanaDbPropertiesModel> SysHanaDbPropertiesList = sysHanaDbPropertiesMapper.searchToList();
        return SysHanaDbPropertiesList;

    }

    /**
     * hanaPrpty配置
     * 删除
     */
    @Override
    public void deleteHanas(long[] ids) throws Exception {
        if (ids != null) {
            sysHanaDbPropertiesMapper.deletes(ids);
            setDeletes(sysHanaDbFarmMapper, ids, "DB_BEAN_ID");
        }

        // 加载HANA配置
        DynamicDataSource.refresh();
    }

    @Override
    public void testDbConnect(Map<String, Object> inputMap) throws Exception {
        String ipAndPort = Maps.getString(inputMap, "ipAndPort");
        String dbName = Maps.getString(inputMap, "dbName");
        String dbUserName = Maps.getString(inputMap, "dbUserName");
        String dbPassword = Maps.getString(inputMap, "dbPassword");
        if (StringUtil.isBlank(ipAndPort)) {
            Thrower.throwException(HanaPropertyException.IP_AND_PORT_NULL);
        } else {
            ipAndPort = ipAndPort.trim();
        }
        if (StringUtil.isBlank(dbName)) {
            Thrower.throwException(HanaPropertyException.DB_NAME_IS_NULL);
        } else {
            dbName = dbName.trim();
        }
        if (StringUtil.isBlank(dbUserName)) {
            Thrower.throwException(HanaPropertyException.DB_USER_NAME_IS_NULL);
        } else {
            dbUserName = dbUserName.trim();
        }
        if (StringUtil.isBlank(dbPassword)) {
            Thrower.throwException(HanaPropertyException.DB_PASSWORD_IS_NULL);
        } else {
            dbPassword = dbPassword.trim();
        }
        String url = "jdbc:sap://" + ipAndPort + "?reconnect=true&currentschema=" + dbName;
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.sap.db.jdbc.Driver");
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(dbUserName);
        basicDataSource.setPassword(dbPassword);

        Connection dataSourceHANAConnection = null;
        try {
            dataSourceHANAConnection = DataSourceUtils.getConnection(basicDataSource);
        }
        finally {
            DataSourceUtils.doReleaseConnection(dataSourceHANAConnection, basicDataSource);
            dataSourceHANAConnection = null;
        }
    }

    @Override
    public void editDbAndFarm(Map<String, Object> inputMap) throws Exception {
        Date currentDate = new Date();

        Long dbBeanId = Maps.getLong(inputMap, "beanNameSelect");

        Object farmIdsObject = Maps.get(inputMap, "farmIds", null);

        List<String> farmIds = null;

        if (farmIdsObject instanceof List) {
            farmIds = (List<String>) farmIdsObject;
        } else if (farmIdsObject instanceof String) {
            farmIds = new ArrayList<String>();
            farmIds.add((String) farmIdsObject);
        }

        List<SysHanaDbFarmModel> sysHanaDbFarmModelList = new ArrayList<SysHanaDbFarmModel>();

        // 覆盖原有猪场的配置
        for (String farmId : farmIds) {
            // 将原有猪场的配置删除
            setDeletes(sysHanaDbFarmMapper, Long.parseLong(farmId), "FARM_ID");

            SysHanaDbFarmModel sysHanaDbFarmModel = new SysHanaDbFarmModel();
            sysHanaDbFarmModel.setStatus(CommonConstants.STATUS);
            sysHanaDbFarmModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            sysHanaDbFarmModel.setFarmId(Long.parseLong(farmId));
            sysHanaDbFarmModel.setDbBeanId(dbBeanId);
            sysHanaDbFarmModel.setCreateId(getEmployeeId());
            sysHanaDbFarmModel.setCreateDate(currentDate);
            sysHanaDbFarmModelList.add(sysHanaDbFarmModel);
        }

        // 将原有的数据源配置删除
        setDeletes(sysHanaDbFarmMapper, dbBeanId, "DB_BEAN_ID");

        if (!sysHanaDbFarmModelList.isEmpty()) {
            sysHanaDbFarmMapper.inserts(sysHanaDbFarmModelList);
        }
    }

    @Override
    public List<SysHanaDbFarmModel> searchDbAndFarmToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(Maps.getLongClass(inputMap, "dbBeanId"), " AND DB_BEAN_ID = ?");
        List<SysHanaDbFarmModel> sysHanaDbFarmModelList = getList(sysHanaDbFarmMapper, sqlCon);
        for (SysHanaDbFarmModel sysHanaDbFarmModel : sysHanaDbFarmModelList) {
            sysHanaDbFarmModel.set("farmName", CacheUtil.getName(sysHanaDbFarmModel.getFarmId().toString(), NameEnum.COMPANY_NAME));
        }
        return sysHanaDbFarmModelList;
    }

}
