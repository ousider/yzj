package xn.pigfarm.service.basicinfo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.data.enums.SeqEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.portal.CoreUserMapper;
import xn.core.mapper.portal.UserMapper;
import xn.core.model.portal.CoreUserModel;
import xn.core.model.portal.UserModel;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.service.portal.ISecurityLoginService;
import xn.core.shiro.UserDetail;
import xn.core.util.BeanUtil;
import xn.core.util.MD5Util;
import xn.core.util.ParamUtil;
import xn.core.util.SeqUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.core.util.uploadFile.UploadFileUtil;
import xn.hana.model.common.HanaClientOrProvider;
import xn.hana.model.common.HanaPigHouse;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.basicInfo.BasicInfoException;
import xn.pigfarm.mapper.backend.PigHouseMapper;
import xn.pigfarm.mapper.basicinfo.CompanyHisMapper;
import xn.pigfarm.mapper.basicinfo.CompanyMapper;
import xn.pigfarm.mapper.basicinfo.DeptHouseMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeRoleMapper;
import xn.pigfarm.mapper.basicinfo.FarmBindMapper;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.basicinfo.HrLaccountMapper;
import xn.pigfarm.mapper.basicinfo.HrLfileMapper;
import xn.pigfarm.mapper.basicinfo.HrLlinkMapper;
import xn.pigfarm.mapper.basicinfo.HrLpapersMapper;
import xn.pigfarm.mapper.basicinfo.LimitMapper;
import xn.pigfarm.mapper.basicinfo.LineMapper;
import xn.pigfarm.mapper.basicinfo.PigpenMapper;
import xn.pigfarm.mapper.basicinfo.RoleMapper;
import xn.pigfarm.mapper.basicinfo.SaleAccountItemMapper;
import xn.pigfarm.mapper.basicinfo.SrmMapper;
import xn.pigfarm.model.backend.PigHouseModel;
import xn.pigfarm.model.basicinfo.CompanyHisModel;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.EmployeeRoleModel;
import xn.pigfarm.model.basicinfo.FarmBindModel;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.HrLaccountModel;
import xn.pigfarm.model.basicinfo.HrLfileModel;
import xn.pigfarm.model.basicinfo.HrLlinkModel;
import xn.pigfarm.model.basicinfo.HrLpapersModel;
import xn.pigfarm.model.basicinfo.LimitModel;
import xn.pigfarm.model.basicinfo.LineModel;
import xn.pigfarm.model.basicinfo.PigpenModel;
import xn.pigfarm.model.basicinfo.RoleModel;
import xn.pigfarm.model.basicinfo.SaleAccountItemModel;
import xn.pigfarm.model.basicinfo.SrmModel;
import xn.pigfarm.service.basicinfo.IBasicInfoService;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;
import xn.pigfarm.util.constants.RoleEmployeeConstans;

/**
 * @Description:
 * @author fangc
 * @date 2016年4月21日 下午9:38:57
 */

@Service("BasicInfoService")
public class BasicInfoServiceImpl extends BaseServiceImpl implements IBasicInfoService {
    @Autowired
    private LineMapper lineMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private PigpenMapper pigpenMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyHisMapper companyHisMapper;

    @Autowired
    private SrmMapper srmMapper;

    @Autowired
    private PigHouseMapper pigHouseMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private DeptHouseMapper deptHouseMapper;

    @Autowired
    private CoreUserMapper hrUserMapper;

    @Autowired
    private FarmBindMapper farmBindMapper;

    @Autowired
    private ISecurityLoginService iSecurityLogin;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private LimitMapper limitMapper;

    @Autowired
    private SaleAccountItemMapper saleAccountItemMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private HrLpapersMapper hrLpapersMapper;

    @Autowired
    private HrLaccountMapper hrLaccountMapper;

    @Autowired
    private HrLlinkMapper hrLlinkMapper;

    @Autowired
    private HrLfileMapper hrLfileMapper;

    @Override
    public List<LineModel> searchLineToList() throws Exception {
        return lineMapper.searchToList(getFarmId());
    }

    @Override
    public BasePageInfo searchLineToPage() throws Exception {
        setToPage();
        return getPageInfo(lineMapper.searchToList(getFarmId()));
    }

    @Override
    public void editLine(LineModel lineModel, String codelist) throws Exception {

        long companyId = getUserDetail().getCompanyId();
        long farmId = getUserDetail().getFarmId();

        // 后台自动生成产线编码
        String businessCode = ParamUtil.getBCode("PRODUCTION_LINE", getEmployeeId(), getCompanyId(), getFarmId());
        lineModel.setBusinessCode(businessCode);

        // 验证代码重复
        long breedNameIsExist = ParamUtil.isExist("pp_o_line", lineModel.getRowId(), lineModel.getLineName() + "," + lineModel.getFarmId(),
                "LINE_NAME,FARM_ID");
        if (breedNameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, lineModel.getLineName());
        }
        if (lineModel.getRowId() == null) {
            lineModel.setCompanyId(companyId);
            lineModel.setFarmId(farmId);
            lineMapper.insert(lineModel);
        } else {
            lineMapper.update(lineModel);
        }
    }

    @Override
    public void deleteLines(long[] ids) throws Exception {

        lineMapper.deletes(ids);

    }

    // =======================================
    @Override
    public List<HouseModel> searchHouseToList(String changeType, String houseType, long lineId, String eventName, String saleDescribe)
            throws Exception {
        // 猪只数量
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.HOUSE_ID ROW_ID,COUNT(*) NOTES FROM pp_l_pig T0 WHERE ");
        sqlCon.addMainSql("  T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 ");
        sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
        if ("TO_CHILD_CARE".equals(eventName)) {
            if (changeType.equals("3")) {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '15'");
            } else {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '14'");
            }
        } else if ("TO_CHILD_FATTEN".equals(eventName)) {
            if (changeType.equals("5")) {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '16'");
            } else {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '15'");
            }
        } else if ("GOOD_PIG_SELL".equals(eventName)) {
            sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16,17,18)");
        } else if ("GOOD_PIG_DIE".equals(eventName)) {
            // 商品猪死亡不能查出留种猪
            sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16)");
        }
        sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID ");
        List<HouseModel> list1 = setSql(houseMapper, sqlCon);

        // 猪舍容量
        sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.HOUSE_ID ROW_ID,SUM(IFNULL(PIG_NUM,0)) NOTES FROM pp_o_pigpen T0 ");
        sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
        sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
        sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID");
        List<HouseModel> list2 = setSql(houseMapper, sqlCon);

        sqlCon = new SqlCon();
        if (lineId != 0) {
            sqlCon.addConditionWithNull(lineId, "AND LINE_ID = ?");
        }
        if (StringUtil.isNonBlank(houseType)) {
            sqlCon.addMainSql(" AND HOUSE_TYPE IN( " + houseType + ")");
        } else {
            if (EventConstants.PIG_MOVE_IN.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,8,9) ");
            } else if (EventConstants.CHANGE_EAR_BAND.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,8,9) ");
            } else if (EventConstants.BACKFAT.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,9) ");
            } else if (EventConstants.BOAR_RESERVE_TO_PRODUCT.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.SEMEN.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,6,7,9) ");
            } else if (EventConstants.SEMEN_CHECK.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.PREPUBERTAL_CHECK.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,7) ");
            } else if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.BREED_PIG_CHANGE_HOUSE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,9) ");
            } else if (EventConstants.BREED.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,9) ");
            } else if (EventConstants.PREGNANCY_CHECK.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.MISSCARRY.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.DELIVERY.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.FOSTER.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.WEAN.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.BREED_PIG_OBSOLETE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,8,9) ");
            } else if (EventConstants.BREED_PIG_DIE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,8,9) ");
            } else if (EventConstants.CHILD_PIG_DIE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.TO_CHILD_CARE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,6,8,9) ");
            } else if (EventConstants.TO_CHILD_FATTEN.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,6,8,9) ");
            } else if (EventConstants.GOOD_PIG_DIE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,6,8,9) ");
            } else if (EventConstants.GOOD_PIG_SELL.equals(eventName)) {
                if (PigConstants.SELL_GOOD_HOG_PIG.equals(saleDescribe)) {
                    sqlCon.addMainSql(" AND HOUSE_TYPE IN(" + PigConstants.HOUSE_CLASS_FATTEN + "," + PigConstants.HOUSE_CLASS_ISOLATION + ") ");
                } else if (PigConstants.SELL_GOOD_IMPERFECT_PIG.equals(saleDescribe)) {
                    sqlCon.addMainSql(" AND HOUSE_TYPE IN(" + PigConstants.HOUSE_CLASS_FATTEN + "," + PigConstants.HOUSE_CLASS_ISOLATION + ","
                            + PigConstants.HOUSE_CLASS_CAREPIG + ") ");
                } else if (PigConstants.SELL_GOOD_PIGGY_PIG.equals(saleDescribe)) {
                    StringBuffer houseTypeId = new StringBuffer();
                    houseTypeId.append(PigConstants.HOUSE_CLASS_DELIVERY);
                    houseTypeId.append("," + PigConstants.HOUSE_CLASS_CAREPIG);
                    houseTypeId.append("," + PigConstants.HOUSE_CLASS_FATTEN);
                    houseTypeId.append("," + PigConstants.HOUSE_CLASS_ISOLATION);
                    sqlCon.addMainSql(" AND HOUSE_TYPE IN(" + houseTypeId + ") ");
                }
            } else if (EventConstants.BILL_CANCEL.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.PIG_EVENT_CANCEL.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.EACH_RECORD_CANCEL.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.CHANGE_HOUSE.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.SEED_PIG.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else if (EventConstants.SELECT_BREED_PIG.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,5,8,9) ");
            } else if (EventConstants.PORK_PIG_CHECK.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.SALE_SEMEN.equals(eventName)) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该事件不支持搜索猪舍");
            } else if (EventConstants.NURSE_CHANGE_HOUSE.equals(eventName)) {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7) ");
            } else {
                sqlCon.addMainSql(" AND HOUSE_TYPE IN(1,2,3,4,5,6,7,8,9) ");
            }
        }
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        List<HouseModel> list = getList(houseMapper, sqlCon);
        list = CacheUtil.setName(list, NameEnum.HOUSE_TYPE_NAME);

        // String sqlWhere = "";
        // if ("TO_CHILD_CARE".equals(eventName)) {
        // sqlWhere = " AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '14'";
        // } else if ("TO_CHILD_FATTEN".equals(eventName)) {
        // sqlWhere = " AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '15'";
        // } else if ("GOOD_PIG_SELL".equals(eventName)) {
        // sqlWhere = " AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16,17,18)";
        // } else if ("GOOD_PIG_DIE".equals(eventName)) {
        // sqlWhere = " AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16,17,18)";
        // }
        //
        // String condition = "";
        // if (lineId != 0) {
        // condition = " AND LINE_ID = " + lineId;
        // }
        // if (!"".equals(houseType) && houseType != null) {
        // condition = " AND HOUSE_TYPE IN( " + houseType + ")";
        // }
        // // 猪只数量
        // String sql = "SELECT T0.HOUSE_ID ROW_ID,COUNT(*) NOTES FROM pp_l_pig T0 WHERE T0.FARM_ID = " + getFarmId()
        // + " AND T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 " + sqlWhere + " GROUP BY T0.HOUSE_ID ";
        // List<HouseModel> list1 = setSql(houseMapper, sql);
        // // 猪舍容量
        // String sql2 = "SELECT T0.HOUSE_ID ROW_ID,SUM(IFNULL(PIG_NUM,0)) NOTES FROM pp_o_pigpen T0 WHERE T0.FARM_ID = " + getFarmId()
        // + " AND T0.DELETED_FLAG = '0' AND T0.STATUS = '1' GROUP BY T0.HOUSE_ID ";
        // List<HouseModel> list2 = setSql(houseMapper, sql2);
        //
        // List<HouseModel> list = getList(houseMapper, " AND FARM_ID = " + getFarmId() + condition);
        // list = CacheUtil.setHouseTypeName(list);
        for (HouseModel model1 : list) {
            for (int i = 0; i < list1.size(); i++) {
                HouseModel model2 = list1.get(i);
                if (model1.getRowId().equals(model2.getRowId())) {
                    model1.set("pigQty", model2.getNotes());
                    list1.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < list2.size(); i++) {
                HouseModel model2 = list2.get(i);
                if (model1.getRowId().equals(model2.getRowId())) {
                    model1.set("houseVolume", model2.getNotes());
                    list2.remove(i);
                    i--;
                }
            }
        }
        if ("GOOD_PIG_SELL".equals(eventName)) {
            List<HouseModel> newList = new ArrayList<HouseModel>();
            for (HouseModel model : list) {
                if (StringUtil.isNonBlank((String) model.get("pigQty"))) {
                    newList.add(model);
                }
            }
            return newList;
        } else
            return list;

    }

    // 猪舍（部门编辑）
    public List<HouseModel> searchHouseDeptToList() throws Exception {

        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" ORDER BY HOUSE_TYPE ");
        List<HouseModel> houselist = getList(houseMapper, sqlCon);
        houselist = CacheUtil.setName(houselist, NameEnum.HOUSE_TYPE_NAME);

        return houselist;
    }

    @Override
    public BasePageInfo searchHouseToPage(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, DELETED_FLAG AS deletedFlag, AREA AS area,BUSINESS_CODE AS businessCode, ");
        sqlCon.addMainSql(" HOUSE_NAME AS houseName, HOUSE_TYPE AS houseType, DEPRECIATION_POLICY AS depreciationPolicy,");
        sqlCon.addMainSql(" COST AS cost, NOTES AS notes,");
        sqlCon.addMainSql(" HOUSE_PRIFIX_CODE AS housePrifixCode,HOUSE_UNIT_NUMBER AS houseUnitNumber,");
        sqlCon.addMainSql(" HOUSE_BUILDING_NUMBER AS houseBuildingNumber,IS_FOSTER_HOUSE AS isFosterHouse,");
        sqlCon.addMainSql(" CONCAT(HOUSE_PRIFIX_CODE,HOUSE_UNIT_NUMBER,HOUSE_BUILDING_NUMBER) AS sapCode,");
        sqlCon.addMainSql(" (SELECT T1.pigQty FROM");
        sqlCon.addMainSql(" (SELECT HOUSE_ID,COUNT(1) AS pigQty FROM PP_L_PIG WHERE ");
        sqlCon.addMainSql(" DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS <=18 ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY HOUSE_ID) T1");
        sqlCon.addMainSql(" WHERE T1.HOUSE_ID = ROW_ID)");
        sqlCon.addMainSql(" AS pigQty,");
        sqlCon.addMainSql(" (SELECT T2.houseVolume FROM ");
        sqlCon.addMainSql(" (SELECT HOUSE_ID, SUM(IFNULL(PIG_NUM,0)) AS houseVolume FROM PP_O_PIGPEN WHERE");
        sqlCon.addMainSql(" DELETED_FLAG = '0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY HOUSE_ID) T2");
        sqlCon.addMainSql(" WHERE T2.HOUSE_ID = ROW_ID)");
        sqlCon.addMainSql("  AS houseVolume,CASE STATUS WHEN 1 THEN '启用' WHEN 0 THEN '停用' END AS status FROM PP_O_HOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG='0' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(inputMap.get("businessCode"), " AND BUSINESS_CODE LIKE ?", true);
        sqlCon.addCondition(inputMap.get("houseName"), " AND HOUSE_NAME LIKE ?", true);
        if (inputMap.get("houseType") != null && ((String) inputMap.get("houseType")).trim() != "" && !inputMap.get("houseType").equals("-1")) {
            sqlCon.addCondition(inputMap.get("houseType"), " AND HOUSE_TYPE = ?");
        }
        sqlCon.addMainSql(" ORDER BY HOUSE_TYPE");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        setToPage();
        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : result) {
            if ("9".equals(Maps.getString(map, "houseType")) && "Y".equals(Maps.getString(map, "isFosterHouse"))) {
                map.put("houseTypeName", "培育舍");
            } else {
                map.put("houseTypeName", CacheUtil.getName(Maps.getString(map, "houseType"), NameEnum.HOUSE_TYPE_NAME));
            }
        }
        return getPageInfo(result);
        // // 查询猪只
        // SqlCon sqlCon = new SqlCon();
        // sqlCon.addMainSql(" SELECT T0.HOUSE_ID ROW_ID,COUNT(*) NOTES FROM pp_l_pig T0");
        // sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 ");
        // sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
        // sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID");
        // List<HouseModel> list1 = setSql(houseMapper, sqlCon);
        //
        // // 猪舍容量
        // sqlCon = new SqlCon();
        // sqlCon.addMainSql(" SELECT T0.HOUSE_ID ROW_ID,SUM(IFNULL(PIG_NUM,0)) NOTES FROM pp_o_pigpen T0");
        // sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
        // sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
        // sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID");
        // List<HouseModel> list2 = setSql(houseMapper, sqlCon);
        //
        // setToPage();
        // // List<HouseModel> list = houseMapper.searchToList(getFarmId());
        // HashMap<String, String> map = new HashMap<String, String>();
        // SqlCon sql = new SqlCon();
        // sql.addMainSql("SELECT T1.* FROM (");
        // sql.addMainSql(
        // "SELECT
        // ROW_ID,SORT_NBR,STATUS,DELETED_FLAG,ORIGIN_FLAG,ORIGIN_APP,NOTES,FARM_ID,COMPANY_ID,BUSINESS_CODE,HOUSE_NAME,BREEDER_ID,HOUSE_TYPE,LINE_ID,PIG_CLASS,DISINFECT_DAY,DISINFECT_METHOD,COLUMN_NUM,SHAPE,AREA,LENGTH,WIDTH,HEIGHT,HOUSE_CLASS,DEPRECIATION_POLICY,COST,PEN_NUM,MAX_CAPACITY
        // ");
        // sql.addMainSql("FROM PP_O_HOUSE WHERE DELETED_FLAG='0' AND STATUS = '1'");
        // sql.addConditionWithNull(getFarmId(), " AND FARM_ID =?");
        // sql.addMainSql(" AND ( HOUSE_TYPE BETWEEN 1 AND 5 OR HOUSE_TYPE = 7 )");
        // sql.addMainSql(" ORDER BY HOUSE_TYPE,HOUSE_NAME ) T1");
        //
        // sql.addMainSql(" UNION ");
        //
        // sql.addMainSql("SELECT T2.* FROM (");
        // sql.addMainSql(
        // "SELECT
        // ROW_ID,SORT_NBR,STATUS,DELETED_FLAG,ORIGIN_FLAG,ORIGIN_APP,NOTES,FARM_ID,COMPANY_ID,BUSINESS_CODE,HOUSE_NAME,BREEDER_ID,HOUSE_TYPE,LINE_ID,PIG_CLASS,DISINFECT_DAY,DISINFECT_METHOD,COLUMN_NUM,SHAPE,AREA,LENGTH,WIDTH,HEIGHT,HOUSE_CLASS,DEPRECIATION_POLICY,COST,PEN_NUM,MAX_CAPACITY
        // ");
        // sql.addMainSql("FROM PP_O_HOUSE WHERE DELETED_FLAG='0' AND STATUS = '1'");
        // sql.addConditionWithNull(getFarmId(), " AND FARM_ID =?");
        // sql.addMainSql(" AND ( HOUSE_TYPE = 6 OR HOUSE_TYPE BETWEEN 8 AND 11 )");
        // sql.addMainSql(" ORDER BY HOUSE_TYPE,HOUSE_NAME ) T2");
        // map.put("sql", sql.getCondition());
        // List<HouseModel> list = houseMapper.operSql(map);
        //
        // list = CacheUtil.setHouseTypeName(list);
        // for (HouseModel model1 : list) {
        //
        // for (int i = 0; i < list1.size(); i++) {
        // HouseModel model2 = list1.get(i);
        // if (model1.getRowId().equals(model2.getRowId())) {
        // model1.set("pigQty", model2.getNotes());
        // list1.remove(i);
        // i--;
        // }
        // }
        // for (int i = 0; i < list2.size(); i++) {
        // HouseModel model2 = list2.get(i);
        // if (model1.getRowId().equals(model2.getRowId())) {
        // model1.set("houseVolume", model2.getNotes());
        // list2.remove(i);
        // i--;
        // }
        // }
        // }
        //
        // return getPageInfo(list);
    }

    @Override
    public void editHouse(HouseModel houseModel, String pigpenList) throws Exception {
        Long copyInsertOrUpdate = houseModel.getRowId();
        // 0 表示新增，1表示更新
        int flag = 0;
        if (houseModel.getRowId() != null) {
            flag = 1;
        }
        if (houseModel.getHouseType() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪舍类型不能为空！");
        }
        if (houseModel.getHouseName() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪舍名字不能为空！");
        }
        List<PigpenModel> pigpenLists = getModelList(pigpenList, PigpenModel.class);
        houseModel.setCompanyId(getCompanyId());
        houseModel.setFarmId(getFarmId());

        long nameIsExist = ParamUtil.isExist("pp_o_house", houseModel.getRowId(), new String[] { houseModel.getHouseName(), houseModel.getFarmId()
                + "" }, "HOUSE_NAME, FARM_ID");
        if (nameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, houseModel.getHouseName());
        }

        boolean fosterHouseFlag = false;
        // 猪舍编码
        String eventName = "";
        if (houseModel.getHouseType() == 1) {
            // 隔离舍
            eventName = "HOUSE_TYPE_S";
        } else if (houseModel.getHouseType() == 2) {
            // 后备舍
            eventName = "HOUSE_TYPE_R";
        } else if (houseModel.getHouseType() == 3) {
            // 空怀舍
            eventName = "HOUSE_TYPE_P";
        } else if (houseModel.getHouseType() == 4) {
            // 重怀舍
            eventName = "HOUSE_TYPE_P";
        } else if (houseModel.getHouseType() == 5) {
            // 妊娠舍
            eventName = "HOUSE_TYPE_P";
        } else if (houseModel.getHouseType() == 6) {
            // 产房舍
            eventName = "HOUSE_TYPE_F";
        } else if (houseModel.getHouseType() == 7) {
            // 公猪舍
            eventName = "HOUSE_TYPE_B";
        } else if (houseModel.getHouseType() == 8) {
            // 保育舍
            eventName = "HOUSE_TYPE_N";
        } else if (houseModel.getHouseType() == 9) {
            // 育肥舍
            eventName = "HOUSE_TYPE_G";
            if (houseModel.getHouseType() == 9) {
                // 是培育舍
                if ("Y".equals(houseModel.getIsFosterHouse())) {
                    fosterHouseFlag = true;
                }
            }
        } else if (houseModel.getHouseType() == 10) {
            // 育肥舍
            eventName = "HOUSE_TYPE_Z";
        }

        // SAP接口需要
        houseModel.setHousePrifixCode(this.getHousePrifixCode(fosterHouseFlag, eventName));
        Date currentDate = new Date();

        long sapCodeIsExist = ParamUtil.isExist("pp_o_house", houseModel.getRowId(), new String[] { houseModel.getHousePrifixCode(), String.valueOf(
                houseModel.getHouseUnitNumber()), String.valueOf(houseModel.getHouseBuildingNumber()), String.valueOf(houseModel.getFarmId()) },
                "HOUSE_PRIFIX_CODE, HOUSE_UNIT_NUMBER, HOUSE_BUILDING_NUMBER, FARM_ID");
        if (sapCodeIsExist > 0) {
            String houseTypeName = null;
            if (fosterHouseFlag) {
                // 培育舍
                houseTypeName = "培育舍";
            } else {
                houseTypeName = CacheUtil.getName(String.valueOf(houseModel.getHouseType()), NameEnum.HOUSE_TYPE_NAME);
            }

            Thrower.throwException(BaseBusiException.SAP_CODE_DUPLICATE_ERROR, houseTypeName);
        }

        SqlCon condition = new SqlCon();
        condition.addCondition(getFarmId(), " AND FARM_ID = ? ");
        condition.addCondition(houseModel.getRowId(), " AND ROW_ID <> ? ");
        condition.addCondition(houseModel.getHousePrifixCode(), " AND HOUSE_PRIFIX_CODE = ? ");
        condition.addCondition(houseModel.getHouseUnitNumber(), " AND HOUSE_UNIT_NUMBER = ? ");
        condition.addCondition(houseModel.getHouseBuildingNumber(), " AND HOUSE_BUILDING_NUMBER = ? ");
        List<HouseModel> list = getList(houseMapper, condition);
        if (list.size() > 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "[单元号-栋号]不能同时重复!");
        }
        if ((houseModel.getRowId() == null || houseModel.getRowId() == 0) && "0".equals(houseModel.getDeletedFlag())) {
            // 后台生成猪舍代码
            String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
            houseModel.setBusinessCode(businessCode);
            houseMapper.insert(houseModel);
            // START HANA
            // 1030 - 主数据 - 栋舍 - OPRC
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            String mtcBranchID = null;
            HanaPigHouse hanaPigHouse = null;
            if (isToHana) {
                hanaPigHouse = new HanaPigHouse();
                StringBuilder builder = new StringBuilder();
                builder.append(houseModel.getHousePrifixCode());
                builder.append(String.format("%03d", houseModel.getHouseUnitNumber()));
                builder.append(String.format("%02d", houseModel.getHouseBuildingNumber()));
                // builder.append(String.valueOf(houseModel.getFarmId()));
                String stringBuilder = builder.toString();
                // 猪舍编号
                hanaPigHouse.setMTC_UnitID(stringBuilder);

                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 业务代码:主数据 - 栋舍
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1030);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(stringBuilder);
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(new Date());
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigHouse);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
            // END HANA

        } else {
            houseMapper.update(houseModel);
            // 删除原因：栋舍不能改变单元号和栋号，无需传给SAP
            // START HANA
            // 1030 - 主数据 - 栋舍 - OPRC
            // boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            // String mtcBranchID = null;
            // HanaPigHouse hanaPigHouse = null;
            // if (isToHana) {
            // hanaPigHouse = new HanaPigHouse();
            // StringBuilder builder = new StringBuilder();
            // builder.append(houseModel.getHousePrifixCode());
            // builder.append(String.format("%03d", houseModel.getHouseUnitNumber()));
            // builder.append(String.format("%02d", houseModel.getHouseBuildingNumber()));
            // String stringBuilder = builder.toString();
            // // 猪舍编号
            // hanaPigHouse.setMTC_UnitID(stringBuilder);
            //
            // Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            // mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            // MTC_ITFC mtcITFC = new MTC_ITFC();
            // // 分公司编号
            // mtcITFC.setMTC_Branch(mtcBranchID);
            // // 业务日期
            // mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            // // 业务代码:主数据 - 栋舍
            // mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1030);
            // // 新农+单据编号
            // mtcITFC.setMTC_DocNum(stringBuilder);
            // // 优先级
            // mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // // 处理区分
            // mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_U);
            // // 创建日期
            // mtcITFC.setMTC_CreateTime(new Date());
            // // 同步状态
            // mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // // JSON文件
            // String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigHouse);
            // mtcITFC.setMTC_DataFile(jsonDataFile);
            // // JSON文件长度
            // mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
            // hanaCommonService.insertMTC_ITFC(mtcITFC);
            // }
            // END HANA

        }
        List<PigpenModel> ppPigpenByInsert = new ArrayList<PigpenModel>();
        List<PigpenModel> ppPigpenListByUpdate = new ArrayList<PigpenModel>();

        Set<String> pigpenNameSet = new HashSet<String>();

        for (Iterator<PigpenModel> it = pigpenLists.iterator(); it.hasNext();) {
            PigpenModel pigpenListModel = (PigpenModel) it.next();
            // 获取猪舍ID
            pigpenListModel.setHouseId(houseModel.getRowId());
            pigpenListModel.setCompanyId(getCompanyId());
            pigpenListModel.setFarmId(getFarmId());

            pigpenListModel.setBusinessCode(ParamUtil.getBCode("PIG_STY", getEmployeeId(), getCompanyId(), getFarmId()));
            // 猪栏名字和代码的重复验证
            // if (pigpenListModel.getBusinessCode() == null) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪栏编码不能为空！");
            // }
            if ("".equals(pigpenListModel.getPigpenName()) || pigpenListModel.getPigpenName() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪栏名字不能为空！");
            }

            // 猪栏名称不能重复
            if (!pigpenNameSet.contains(pigpenListModel.getPigpenName())) {
                pigpenNameSet.add(pigpenListModel.getPigpenName());
            } else {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪栏名称【" + pigpenListModel.getPigpenName() + "】存在重复！");
            }

            long codeValueIsExist = ParamUtil.isExist("pp_o_pigpen", pigpenListModel.getRowId(), new String[] { pigpenListModel.getBusinessCode(),
                    pigpenListModel.getHouseId() + "" }, "BUSINESS_CODE, HOUSE_ID");

            long codeNameIsExist = ParamUtil.isExist("pp_o_pigpen", pigpenListModel.getRowId(), new String[] { pigpenListModel.getPigpenName(),
                    pigpenListModel.getHouseId() + "" }, new String[] { "PIGPEN_NAME", "HOUSE_ID" });
            if (codeValueIsExist > 0) {
                Thrower.throwException(BaseBusiException.CODE_DUPLICATE_ERROR, pigpenListModel.getBusinessCode());
            }
            if (codeNameIsExist > 0) {
                Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, pigpenListModel.getPigpenName());
            }
            // 新增、修改、删除明细表
            if (flag == 0) {
                ppPigpenByInsert.add(pigpenListModel);
            } else {
                if (pigpenListModel.getRowId() == null && "0".equals(pigpenListModel.getDeletedFlag())) {
                    ppPigpenByInsert.add(pigpenListModel);
                } else if (pigpenListModel.getRowId() != null && "0".equals(pigpenListModel.getDeletedFlag()) && copyInsertOrUpdate == 0) {
                    ppPigpenByInsert.add(pigpenListModel);
                } else if (pigpenListModel.getRowId() != null && "0".equals(pigpenListModel.getDeletedFlag()) && copyInsertOrUpdate != 0) {
                    ppPigpenListByUpdate.add(pigpenListModel);

                } else if ("1".equals(pigpenListModel.getDeletedFlag())) {
                    long[] ids = new long[1];
                    ids[0] = pigpenListModel.getRowId();
                    // houseMapper.deletes(ids);
                    pigpenMapper.deletes(ids);
                }
            }

        }
        if (ppPigpenByInsert.size() > 0) {
            pigpenMapper.inserts(ppPigpenByInsert);

        }
        if (ppPigpenListByUpdate.size() > 0 && copyInsertOrUpdate != 0) {
            pigpenMapper.updates(ppPigpenListByUpdate);
        }

    }

    private String getHousePrifixCode(boolean fosterHouseFlag, String eventName) {
        SqlCon getHousePrifixCodeSqlCon = new SqlCon();
        getHousePrifixCodeSqlCon.addMainSql("SELECT PRIFIX_CODE AS prifixCode");
        getHousePrifixCodeSqlCon.addMainSql(" FROM cd_l_bcode_type T0 LEFT JOIN cd_l_bcode T1");
        getHousePrifixCodeSqlCon.addMainSql(" ON (T0.ROW_ID = T1.BCODE_TYPE_ID AND T1.STATUS = '1' AND T1.DELETED_FLAG = '0'");
        getHousePrifixCodeSqlCon.addCondition(get10000FarmId(), " AND FARM_ID = ?)");
        getHousePrifixCodeSqlCon.addMainSql(" WHERE T0.STATUS = '1' AND T0.DELETED_FLAG = '0'");
        if (fosterHouseFlag) {
            getHousePrifixCodeSqlCon.addMainSql(" AND T0.TYPE_CODE = 'HOUSE_TYPE_E'");
        } else {
            getHousePrifixCodeSqlCon.addCondition(eventName, " AND T0.TYPE_CODE = ?");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", getHousePrifixCodeSqlCon.getCondition());

        List<Map<String, String>> housePrifixCode = paramMapper.getInfos(sqlMap);

        return Maps.getString(housePrifixCode.get(0), "prifixCode");
    }

    @Override
    public void deleteHouses(long[] ids) throws Exception {

        boolean flag = ParamUtil.isExitDetail("PP_L_PIG", "HOUSE_ID", ids);

        if (flag) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "猪舍已经有过猪只，不能删除！");
        }
        houseMapper.deletes(ids);
        setDeletes(pigpenMapper, ids, "HOUSE_ID");
    }

    // =======================================
    @Override
    public List<PigpenModel> searchPigpenToList(long houseId, String eventName) throws Exception {

        // 猪只数量
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.PIGPEN_ID ROW_ID,COUNT(1) NOTES FROM pp_l_pig T0 WHERE ");
        sqlCon.addMainSql("  T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 ");
        sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
        sqlCon.addCondition(houseId, " AND T0.HOUSE_ID = ?");
        if (EventConstants.GOOD_PIG_DIE.equals(eventName)) {
            // 商品猪死亡只显示 断奶仔猪的数量
            sqlCon.addCondition(PigConstants.PIG_CLASS_PORK_DN, " AND T0.PIG_CLASS = ?");
        }
        sqlCon.addMainSql(" GROUP BY T0.PIGPEN_ID ");
        List<PigpenModel> pigQtyList = setSql(pigpenMapper, sqlCon);

        sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(houseId, " AND HOUSE_ID = ?");
        // 上产房过滤已有猪只的猪栏（目前微信端用，传入eventName参数）
        if (EventConstants.CHANGE_LABOR_HOUSE.equals(eventName)) {
            sqlCon.addMainSql(" AND NOT EXISTS (");
            sqlCon.addMainSql(" SELECT 1 FROM PP_L_PIG WHERE DELETED_FLAG = '0' AND PIG_CLASS <=18 AND ORIGIN <> '3'");
            sqlCon.addMainSql(" AND HOUSE_ID =PP_O_PIGPEN.HOUSE_ID  AND PIGPEN_ID = PP_O_PIGPEN.ROW_ID LIMIT 1)");
        }
        List<PigpenModel> list = getList(pigpenMapper, sqlCon);

        if (pigQtyList.isEmpty() || list.isEmpty()) {
            return list;
        }

        // 为猪栏赋值猪只数量
        for (int i = 0; i < list.size(); i++) {
            PigpenModel pigpen = list.get(i);

            for (int j = 0; j < pigQtyList.size(); j++) {
                PigpenModel qtyPigpen = pigQtyList.get(j);
                if (pigpen.getRowId().equals(qtyPigpen.getRowId())) {
                    pigpen.set("pigQty", qtyPigpen.getNotes());
                    pigQtyList.remove(j);
                    j--;
                }
            }
        }
        return list;
    }

    @Override
    public BasePageInfo searchPigpenToPage() throws Exception {

        setToPage();
        return getPageInfo(pigpenMapper.searchToList());
    }

    @Override
    public void deletePigpens(long[] ids) throws Exception {
        pigpenMapper.deletes(ids);
    }

    // ===================================客户，供应商公共方法
    @Override
    public BasePageInfo searchGroupCustomerAndSupplierToPage(Map<String, Object> inputMap) throws Exception {
        Long farmId = getFarmId();
        String cussupType = Maps.getString(inputMap, "cussupType");
        SqlCon sql = new SqlCon();
        sql.addMainSql("SELECT N.*, ");
        sql.addMainSql("CONCAT('[',GROUP_CONCAT(CONCAT('{\"parentId\":',''+N3.COMPANY_ID,',') ,CONCAT('\"farmId\":',''+N3.FARM_ID,'}')),']')");
        sql.addMainSql(" AS requestUpdateFarm FROM ( ");
        sql.addMainSql(
                " SELECT T2.ROW_ID srmId,T1.ROW_ID rowId,T1.ROW_ID cussupId,T1.COMPANY_NAME_EN companyNameEn,T1.PROVINCE province,T1.CITY city,");
        sql.addMainSql(" T1.COUNTY county,T1.COUNTRY country,T1.POSTCODE postcode,T1.COMPANY_ADDRESS companyAddress,T1.LONGITUDE longitude,");
        sql.addMainSql(" T1.LATITUDE latitude,T1.COMPANY_CODE companyCode,T1.COMPANY_NAME companyName,T1.SORT_NAME sortName,T1.NOTES notes,");
        sql.addMainSql(" (SELECT CREATE_TYPE FROM HR_R_SRM WHERE DELETED_FLAG = '0' AND CUSSUP_ID = T1.ROW_ID");
        sql.addConditionWithNull(cussupType, " AND CUSSUP_TYPE = ?");
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?) AS createType,");
        sql.addMainSql(" T2.TRADE_CURRENCY tradeCurrency,T2.PAY_CONDITION payCondition,");
        sql.addMainSql(" T2.CREDIT_LIMIT creditLimit,T2.CUSSUP_TYPE cussupType,t2.FARM_ID farmId,");
        sql.addMainSql(" T3.CHANGE_WORKER creater,T3.CHANGE_DATE createDate,");
        sql.addMainSql(" T4.EMPLOYEE_NAME AS createrName,");
        sql.addMainSql(" T5.CHANGE_WORKER changer,T5.CHANGE_DATE changeDate,T5.CHANGE_CONTENT changeContent,");
        sql.addMainSql(" T6.EMPLOYEE_NAME changeName,");
        sql.addMainSql(" '变更历史' changeHistory,");
        if ("S".equals(cussupType)) {
            sql.addMainSql(
                    " T2.LEGAL_PERSON legalPerson,T2.SUPPLIER_TYPE supplierType,T2.BUSINESS_PARTNER_TYPE businessPartnerType,T2.PAY_DAYS payDays,");
            // sql.addMainSql(" T2.PAY_CONDITION payCondition,");
            sql.addMainSql(" '采购目录' catalog,");
            sql.addMainSql(" CONCAT((SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'TERM_OF_PAYMENT'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.PAY_CONDITION),");
            sql.addMainSql(" (SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'PAY_DATE_LIST'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.PAY_DAYS)) AS paymnent");
        } else {
            sql.addMainSql(
                    " T2.CUSTOMERPARENT customerParent,T2.COLLECT_CONDITION collectCondition,T2.COLLECT_DAYS collectDays,T2.IS_SALE_ACCOUNT isSaleAccount,");
            sql.addMainSql(" T2.CUSTOMER_TYPE customerType,T2.SOW_SIZE sowSize,T2.CUSTOMER_LEVEL customerLevel,T2.SELL_TYPE sellType,");
            sql.addMainSql(" T2.SELL_DIVISION sellDivision,T2.SELL_AREA sellArea,T2.SALESMAN salesman,T2.FIXED_BALANCE_DAY fixedBalanceDay,");
            sql.addMainSql(" '销售数量' catalog,");
            sql.addMainSql(" CONCAT((SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'TERM_OF_COLLECTION'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.COLLECT_CONDITION),");
            sql.addMainSql(" (SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'PAY_DATE_LIST'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.COLLECT_DAYS)) AS collectmnent");
        }

        sql.addMainSql(" FROM HR_M_COMPANY T1");
        sql.addMainSql(" INNER JOIN HR_R_SRM T2");
        sql.addMainSql(" ON(T2.DELETED_FLAG = '0'");
        sql.addMainSql(" AND T1.ROW_ID = T2.CUSSUP_ID");
        sql.addConditionWithNull(cussupType, " AND T2.CUSSUP_TYPE = ?");
        sql.addMainSql(" AND T2.CREATE_TYPE IN ('1','3'))");
        // 创建信息
        sql.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY_HIS T3");
        sql.addMainSql(" ON (T1.ROW_ID = T3.CUSSUP_ID");
        sql.addMainSql(" AND T2.CUSSUP_TYPE = T3.CUSSUP_TYPE");
        sql.addMainSql(" AND T3.VERSION = '1')");
        // 创建人
        sql.addMainSql(" LEFT OUTER JOIN HR_O_EMPLOYEE T4");
        sql.addMainSql(" ON(T3.CHANGE_WORKER = T4.ROW_ID)");
        // 最后一次变更信息
        sql.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY_HIS T5");
        sql.addMainSql(" ON (T5.DELETED_FLAG = '0' AND T1.ROW_ID = T5.CUSSUP_ID AND T2.FARM_ID = T5.FARM_ID AND T2.VERSION = T5.VERSION)");
        // 最后一次修改人
        sql.addMainSql(" LEFT OUTER JOIN HR_O_EMPLOYEE T6");
        sql.addMainSql(" ON(T5.CHANGE_WORKER = T6.ROW_ID)");
        sql.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sql.addMainSql(" AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE DELETED_FLAG = '0'");
        sql.addConditionWithNull(cussupType, " AND CUSSUP_TYPE = ? AND CUSSUP_ID = T1.ROW_ID");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? )");
        sql.addMainSql(" ) N");
        sql.addMainSql(" LEFT JOIN HR_M_COMPANY N2 ON N2.ROW_ID = N.farmId AND N2.DELETED_FLAG = '0' AND N2.STATUS = '1' ");
        sql.addMainSql(
                " LEFT JOIN hr_r_srm N3 ON N.cussupId = N3.CUSSUP_ID AND N.cussupType = N3.CUSSUP_TYPE AND N3.DELETED_FLAG = '0' AND N3.STATUS = '1' ");
        sql.addMainSql(" LEFT JOIN HR_M_COMPANY N4 ON N4.ROW_ID = N3.FARM_ID AND N4.DELETED_FLAG = '0' AND N4.STATUS = '1' ");
        sql.addConditionWithNull(getFarmId(), " WHERE N.farmId = ?");
        sql.addCondition(cussupType, " AND N.cussupType= ?");
        sql.addConditionWithNull(cussupType, " AND N.cussupType =?");
        sql.addCondition(inputMap.get("sortName"), " AND N.sortName LIKE ?", true);
        sql.addCondition(inputMap.get("companyName"), " AND N.companyName LIKE ?", true);
        sql.addCondition(inputMap.get("createDate"), " AND N.createDate = ?");
        sql.addCondition(inputMap.get("createrName"), "  AND N.createrName LIKE ?", true);
        sql.addCondition(inputMap.get("changeDate"), " AND N.changeDate = ?");
        sql.addCondition(inputMap.get("changeName"), "  AND N.changeName LIKE ?", true);
        sql.addMainSql("  AND (N4.COMPANY_MARK LIKE CONCAT(N2.COMPANY_MARK,',%') OR N4.COMPANY_MARK = N2.COMPANY_MARK ) GROUP BY N.srmId ");
        sql.addMainSql(" ORDER BY N.rowId");

        setToPage();
        Map<String, String> map = new HashMap<>();
        map.put("sql", sql.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);

        // 赋值创建类型
        if (list != null && !list.isEmpty()) {

            CodeEnum codeEnum = CodeEnum.CREATE_SUP_TYPE;
            if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                codeEnum = CodeEnum.CREATE_CUS_TYPE;
            }

            for (Map<String, Object> info : list) {
                String name = CacheUtil.getName(Maps.getString(info, "createType"), NameEnum.CODE_NAME, codeEnum);
                info.put("createTypeName", name);
                // if (CompanyConstants.CREATE_TYPE_SELF.equals(info.get("createType"))) {
                // // 自建的cussupId存放公司名称
                // info.put("cussupId", info.get("companyName"));
                // }
                List<HashMap> requestUpdateFarmList = getJsonList(Maps.getString(info, "requestUpdateFarm"), HashMap.class);
                int alreadyUpdateFarmCount = 0;
                // 删除本公司的记录
                for (int i = 0; i < requestUpdateFarmList.size(); i++) {
                    Map<String, Object> requestUpdateFarmMap = requestUpdateFarmList.get(i);
                    if (farmId.compareTo(Maps.getLongClass(requestUpdateFarmMap, "farmId")) == 0) {
                        requestUpdateFarmList.remove(i);
                        i--;
                        continue;
                    } else {
                        alreadyUpdateFarmCount++;
                    }
                }

                info.put("alreadyUpdateFarmCount", alreadyUpdateFarmCount);
                info.put("requestUpdateFarm", requestUpdateFarmList);
            }
        }
        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchCustomerAndSupplierToPage(Map<String, Object> inputMap) throws Exception {
        String cussupType = Maps.getString(inputMap, "cussupType");
        SqlCon sql = new SqlCon();
        sql.addMainSql("SELECT T1.ROW_ID rowId,T1.ROW_ID cussupId,T1.COMPANY_NAME_EN companyNameEn,T1.PROVINCE province,T1.CITY city,");
        sql.addMainSql(" T1.COUNTY county,T1.COUNTRY country,T1.POSTCODE postcode,T1.COMPANY_ADDRESS companyAddress,T1.LONGITUDE longitude,");
        sql.addMainSql(" T1.LATITUDE latitude,T1.COMPANY_CODE companyCode,T1.COMPANY_NAME companyName,T1.SORT_NAME sortName,T1.NOTES notes,");
        sql.addMainSql(" (SELECT CREATE_TYPE FROM HR_R_SRM WHERE DELETED_FLAG = '0' AND CUSSUP_ID = T1.ROW_ID");
        sql.addConditionWithNull(cussupType, " AND CUSSUP_TYPE = ?");
        sql.addConditionWithNull(getFarmId(), "  AND FARM_ID = ?) AS createType,");
        sql.addMainSql(" T2.TRADE_CURRENCY tradeCurrency,T2.PAY_CONDITION payCondition,");
        sql.addMainSql(" T2.CREDIT_LIMIT creditLimit,T2.CUSSUP_TYPE cussupType,");
        sql.addMainSql(" T3.CHANGE_DATE createDate,");
        sql.addMainSql(" T4.EMPLOYEE_NAME AS createrName,");
        sql.addMainSql(" T5.CHANGE_DATE changeDate,T5.CHANGE_CONTENT changeContent,");
        sql.addMainSql(" T6.EMPLOYEE_NAME changeName,");
        sql.addMainSql(" '变更历史' changeHistory,");
        if ("S".equals(cussupType)) {
            sql.addMainSql(" T2.LEGAL_PERSON legalPerson,T2.SUPPLIER_TYPE supplierType,T2.BUSINESS_PARTNER_TYPE businessPartnerType,");
            sql.addMainSql(" T2.PAY_DAYS AS payDays,");
            sql.addMainSql(" '采购目录' catalog,");
            sql.addMainSql(" CONCAT((SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'TERM_OF_PAYMENT'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.PAY_CONDITION),");
            sql.addMainSql(" (SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'PAY_DATE_LIST'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.PAY_DAYS)) AS paymnent");
        } else {
            sql.addMainSql(" T2.COLLECT_CONDITION collectCondition,T2.COLLECT_DAYS collectDays,T2.IS_SALE_ACCOUNT isSaleAccount,");
            sql.addMainSql(" T2.CUSTOMER_TYPE customerType,T2.SOW_SIZE sowSize,T2.CUSTOMER_LEVEL customerLevel,T2.SELL_TYPE sellType,");
            sql.addMainSql(" T2.SELL_DIVISION sellDivision,T2.SELL_AREA sellArea,T2.SALESMAN salesman,T2.FIXED_BALANCE_DAY fixedBalanceDay,");
            sql.addMainSql(" '销售数量' catalog,");
            sql.addMainSql(" CONCAT((SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'TERM_OF_COLLECTION'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.COLLECT_CONDITION),");
            sql.addMainSql(" (SELECT CODE_NAME FROM CD_L_CODE_LIST WHERE TYPE_CODE = 'PAY_DATE_LIST'");
            sql.addMainSql(" AND DELETED_FLAG = '0' AND CODE_VALUE = T2.COLLECT_DAYS)) AS collectmnent");
        }

        sql.addMainSql(" FROM HR_M_COMPANY T1");
        sql.addMainSql(" INNER JOIN HR_R_SRM T2");
        sql.addMainSql(" ON(T2.DELETED_FLAG = '0'");
        sql.addMainSql(" AND T1.ROW_ID = T2.CUSSUP_ID");
        sql.addConditionWithNull(cussupType, " AND T2.CUSSUP_TYPE = ?");
        sql.addMainSql(" AND T2.CREATE_TYPE IN ('1','3'))");
        // 创建信息
        sql.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY_HIS T3");
        sql.addMainSql(" ON (T1.ROW_ID = T3.CUSSUP_ID");
        sql.addMainSql(" AND T2.CUSSUP_TYPE = T3.CUSSUP_TYPE");
        sql.addMainSql(" AND T3.VERSION = '1')");
        // 创建人
        sql.addMainSql(" LEFT OUTER JOIN HR_O_EMPLOYEE T4");
        sql.addMainSql(" ON(T3.CHANGE_WORKER = T4.ROW_ID)");
        // 最后一次变更信息
        sql.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY_HIS T5");
        sql.addMainSql(" ON (T5.DELETED_FLAG = '0' AND T1.ROW_ID = T5.CUSSUP_ID AND T2.FARM_ID = T5.FARM_ID AND T2.VERSION = T5.VERSION)");
        // 最后一次修改人
        sql.addMainSql(" LEFT OUTER JOIN HR_O_EMPLOYEE T6");
        sql.addMainSql(" ON(T5.CHANGE_WORKER = T6.ROW_ID)");
        sql.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sql.addMainSql(" AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE DELETED_FLAG = '0'");
        sql.addConditionWithNull(cussupType, " AND CUSSUP_TYPE = ? AND CUSSUP_ID = T1.ROW_ID");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? )");

        sql.addCondition(inputMap.get("sortName"), " AND T1.SORT_NAME LIKE ?", true);
        sql.addCondition(inputMap.get("companyName"), " AND T1.COMPANY_NAME LIKE ?", true);
        sql.addCondition(inputMap.get("createDate"), " AND T3.CHANGE_DATE = ?");
        sql.addCondition(inputMap.get("createrName"), "  AND T4.EMPLOYEE_NAME LIKE ?", true);
        sql.addCondition(inputMap.get("changeDate"), " AND T5.CHANGE_DATE = ?");
        sql.addCondition(inputMap.get("changeName"), "  AND T6.EMPLOYEE_NAME LIKE ?", true);
        sql.addMainSql(" ORDER BY T1.ROW_ID");

        setToPage();
        Map<String, String> map = new HashMap<>();
        map.put("sql", sql.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);

        // 赋值创建类型
        if (list != null && !list.isEmpty()) {

            CodeEnum codeEnum = CodeEnum.CREATE_SUP_TYPE;
            if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                codeEnum = CodeEnum.CREATE_CUS_TYPE;
            }

            for (Map<String, Object> info : list) {
                String name = CacheUtil.getName(Maps.getString(info, "createType"), NameEnum.CODE_NAME, codeEnum);
                info.put("createTypeName", name);
            }
        }
        return getPageInfo(list);
    }

    @Override
    public List<Map<String, String>> searchCustomerAndSupplierToList(String cussupType) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT t1.ROW_ID rowId, t1.ROW_ID cussupId,t1.COMPANY_CODE companyCode,t1.COMPANY_NAME companyName,");
        sql.addMainSql(" t1.SORT_NAME sortName,t1.COMPANY_NAME_EN companyNameEn");
        // sql.addMainSql(" ,t.CREATE_TYPE createType,t1.COMPANY_ADDRESS companyAddress");
        sql.addMainSql(" FROM HR_R_SRM t");
        sql.addMainSql(" INNER JOIN HR_M_COMPANY t1 ON t.CUSSUP_ID = t1.ROW_ID AND t1.DELETED_FLAG='0'");
        sql.addMainSql(" WHERE t.DELETED_FLAG='0' ");
        sql.addConditionWithNull(getFarmId(), " AND t.FARM_ID = ?");
        sql.addConditionWithNull(cussupType, " AND t.CUSSUP_TYPE= ?");

        Map<String, String> map = new HashMap<>();
        map.put("sql", sql.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(map);
        return list;
    }

    @Override
    public List<String> editUploadCustomerAndSupplierTempFile(HttpServletRequest request) throws Exception {
        return UploadFileUtil.uploadTempFile(request, "PAPER", "HR_L_FILE", "SYS_FILE_NAME");
    }

    @Override
    public void editCustomerAndSupplierToList(Map<String, Object> inputMap, String relativePath, String absolutePath, String tempFilePath)
            throws Exception {
        if (getCompanyMark().startsWith("1,2,3,")) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新农旗下公司无法自己创建供应商和客户，请联系管理员在集团账套下创建！");
        }

        CompanyModel company = BeanUtil.getBean(CompanyModel.class, inputMap);
        // 版本信息
        CompanyHisModel companyHis = BeanUtil.getBean(CompanyHisModel.class, inputMap);
        // 创建类型
        String createType = Maps.getString(inputMap, "createType");
        // 创建类型不为自建时，选择公司ID
        String cussupId = Maps.getString(inputMap, "cussupId");
        // 是否销售结算
        String isSaleAccount = Maps.getString(inputMap, "isSaleAccount");
        // 变更内容
        String changeContent = Maps.getString(inputMap, "changeContent");
        // 用于生成文件code
        // String paperCode = "PAPER";
        // 证照信息
        // List<HrLpapersModel> hrLpapersModels = new ArrayList<HrLpapersModel>();
        List<HashMap> papperRecordMapList = getJsonList(Maps.getString(inputMap, "paperGridList"), HashMap.class);
        // 账户信息
        List<HrLaccountModel> hrLaccountModels = getJsonList(Maps.getString(inputMap, "accountGridList"), HrLaccountModel.class);
        // 联系方式
        List<HrLlinkModel> hrLlinkModels = getJsonList(Maps.getString(inputMap, "linkGridList"), HrLlinkModel.class);

        String cussupType = company.getCussupType();

        Date currentDate = new Date();

        /* 验证重名 */
        if (CompanyConstants.CREATE_TYPE_SELF.equals(createType)) {
            String companyName = cussupId;
            SqlCon sql = getCusSupSql(null, cussupType, null);
            sql.addCondition(companyName, " AND T1.COMPANY_NAME = ?");
            sql.addCondition(company.getRowId(), " AND T1.ROW_ID <> ?");

            List<CompanyModel> list = setSql(companyMapper, sql);
            if (list != null && !list.isEmpty()) {
                Thrower.throwException(BaseBusiException.CUSSUP_NAME_DUPLICATE_ERROR);
            }

            company.setCompanyName(companyName);
            companyHis.setCompanyName(companyName);

            // 每种证照类型只能上传一张
            if (papperRecordMapList.size() > 0) {
                // 每种证照类型只能存在一条记录
                Set<String> paperTypeSet = new HashSet<String>();
                for (Map<String, Object> papperRecordMap : papperRecordMapList) {
                    String paperType = Maps.getString(papperRecordMap, "paperType");
                    if (!paperTypeSet.contains(paperType)) {
                        paperTypeSet.add(paperType);
                    } else {
                        String paperTypeName = CacheUtil.getName(paperType, xn.core.data.enums.NameEnum.CODE_NAME, CodeEnum.LICENSE_TYPE);
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLong(papperRecordMap, "lineNumber") + "行的【"
                                + paperTypeName + "】存在重复，每种证照只能存在一条记录！");
                    }
                }
            }

            // 编辑
            if (company.getRowId() != 0 && company.getRowId() != null) {
                companyMapper.update(company);

                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");
                sqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");
                List<CompanyHisModel> verList = getList(companyHisMapper, sqlCon);

                if (verList.size() > 1) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统错误，历史表存在两条记录，请联系管理员！");
                }

                Long version = 1L;
                if (!verList.isEmpty()) {
                    CompanyHisModel companyHisModel = verList.get(0);
                    version = companyHisModel.getVersion() + 1L;
                }
                String companyCodeEdit = Maps.getString(CacheUtil.getData("HR_M_COMPANY", String.valueOf(company.getRowId())), "COMPANY_CODE");
                String legalPerson = Maps.getString(inputMap, "legalPerson");
                String supplierType = Maps.getString(inputMap, "supplierType");
                String businessPartnerType = Maps.getString(inputMap, "businessPartnerType");
                String tradeCurrency = Maps.getString(inputMap, "tradeCurrency");
                String payCondition = Maps.getString(inputMap, "payCondition");
                String payDays = Maps.getString(inputMap, "payDays");
                Long creditLimit = Maps.getLongClass(inputMap, "creditLimit");
                SrmModel srmModelEdit = new SrmModel();
                if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                    String customerType = Maps.getString(inputMap, "customerType");
                    Long sowSize = Maps.getLongClass(inputMap, "sowSize");
                    String customerLevel = Maps.getString(inputMap, "customerLevel");
                    String sellType = Maps.getString(inputMap, "sellType");
                    Long sellDivision = Maps.getLongClass(inputMap, "sellDivision");
                    Long sellArea = Maps.getLongClass(inputMap, "sellArea");
                    Long salesman = Maps.getLongClass(inputMap, "salesman");
                    Long fixedBalanceDay = Maps.getLongClass(inputMap, "fixedBalanceDay");
                    String collectCondition = Maps.getString(inputMap, "collectCondition");
                    String collectDays = Maps.getString(inputMap, "collectDays");
                    Long customerParent = Maps.getLongClass(inputMap, "customerParent");
                    srmModelEdit.setCustomerType(customerType);
                    srmModelEdit.setSowSize(sowSize);
                    srmModelEdit.setCustomerLevel(customerLevel);
                    srmModelEdit.setSellType(sellType);
                    srmModelEdit.setSellDivision(sellDivision);
                    srmModelEdit.setSellArea(sellArea);
                    srmModelEdit.setSalesman(salesman);
                    srmModelEdit.setFixedBalanceDay(fixedBalanceDay);
                    srmModelEdit.setCollectCondition(collectCondition);
                    srmModelEdit.setCollectDays(collectDays);
                    srmModelEdit.setCustomerparent(customerParent);
                }
                srmModelEdit.setNotes(company.getNotes());
                srmModelEdit.setStatus(PigConstants.STATUS_NOR);
                srmModelEdit.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                srmModelEdit.setCussupType(cussupType);
                // 公司id
                srmModelEdit.setCompanyId(getCompanyId());
                // 猪场标识
                srmModelEdit.setFarmId(getFarmId());
                srmModelEdit.setCreateType(createType);
                srmModelEdit.setIsSaleAccount(isSaleAccount);
                srmModelEdit.setVersion(version);
                srmModelEdit.setLegalPerson(legalPerson);
                srmModelEdit.setSupplierType(supplierType);
                srmModelEdit.setBusinessPartnerType(businessPartnerType);
                srmModelEdit.setTradeCurrency(tradeCurrency);
                srmModelEdit.setPayCondition(payCondition);
                srmModelEdit.setPayDays(payDays);
                srmModelEdit.setCreditLimit(creditLimit);
                srmModelEdit.setCussupId(company.getRowId());
                srmModelEdit.setRowId(null);

                // 编辑时也是srm 原有数据删除
                SqlCon sqlSrm = new SqlCon();
                sqlSrm.addMainSql("UPDATE hr_r_srm SET DELETED_FLAG = 1 ");
                sqlSrm.addCondition(company.getRowId(), "WHERE CUSSUP_ID = ?");
                sqlSrm.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
                sqlSrm.addConditionWithNull(getFarmId(), " AND  FARM_ID= ? ");
                setSql(srmMapper, sqlSrm);
                // 编辑时也是srm新增一条
                srmMapper.insert(srmModelEdit);
                companyHis.setRegisterTime(TimeUtil.getSysTimestamp());
                companyHis.setOpenTime(TimeUtil.getSysTimestamp());
                companyHis.setCompanyCode(companyCodeEdit);
                companyHis.setCussupId(company.getRowId());
                companyHis.setChangeContent(changeContent);
                companyHis.setChangeDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                companyHis.setChangeWorker(getEmployeeId());
                companyHis.setFarmId(getFarmId());
                companyHis.setVersion(version);
                companyHis.setRowId(null);
                // 编辑时也是companyhis 原有数据删除
                SqlCon sqlHis = new SqlCon();
                sqlHis.addMainSql("UPDATE hr_m_company_his SET DELETED_FLAG = '1'");
                sqlHis.addCondition(company.getRowId(), "WHERE CUSSUP_ID = ?");
                sqlHis.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
                sqlHis.addConditionWithNull(getFarmId(), " AND  FARM_ID= ? ");
                setSql(companyHisMapper, sqlHis);
                // 编辑时companyhis表新增一条
                companyHisMapper.insert(companyHis);

                List<Long> notDeletePapperRowIdList = new ArrayList<Long>();

                if (papperRecordMapList.size() > 0) {
                    // PAPERS版本自增(不跟随SRM)
                    for (Map<String, Object> papperRecordMap : papperRecordMapList) {
                        List<String> fileNameList = Maps.getList(papperRecordMap, "fileNameList");
                        if (fileNameList == null || fileNameList.isEmpty()) {
                            Thrower.throwException(BasicInfoException.PARPER_RECORD_FILE_IS_EMPTY, Maps.getLong(papperRecordMap, "lineNumber"));
                        }

                        String paperType = Maps.getString(papperRecordMap, "paperType");

                        // 用于判断这个证照类型是否有改变
                        SqlCon hrLpapersChangeSqlCon = new SqlCon();
                        hrLpapersChangeSqlCon.addMainSql("SELECT * FROM HR_L_PAPERS WHERE DELETED_FLAG = '0'");
                        hrLpapersChangeSqlCon.addCondition(paperType, " AND PAPER_TYPE = ?");
                        hrLpapersChangeSqlCon.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");

                        List<HrLpapersModel> hrLpapersModelList = setSql(hrLpapersMapper, hrLpapersChangeSqlCon);

                        // 同一个证照类型存在多条合格数据
                        if (hrLpapersModelList.size() > 1) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLong(papperRecordMap, "lineNumber")
                                    + "行证照，同一证照类型存在多条合格数据，请联系管理员！。。。");
                        }

                        Long papperRecordLineNumber = Maps.getLong(papperRecordMap, "lineNumber");
                        String paperCode = Maps.getString(papperRecordMap, "paperCode");
                        String expiryType = Maps.getString(papperRecordMap, "expiryType");
                        Date expiryDate = Maps.getDate(papperRecordMap, "expiryDate");

                        // 此证照类型的附件从来没有上传过
                        if (hrLpapersModelList.isEmpty()) {
                            Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber, paperType,
                                    paperCode, expiryType, expiryDate, 1L, tempFilePath, absolutePath, relativePath, fileNameList);
                            notDeletePapperRowIdList.add(pappersRowId);
                        } else {
                            HrLpapersModel hrLpapersModel = hrLpapersModelList.get(0);
                            Long pappersVersion = hrLpapersModel.getVersion();
                            // 此证照类型的附件上传过
                            // 原来此证照类型上传的附件
                            SqlCon originFileSqlCon = new SqlCon();
                            originFileSqlCon.addMainSql("SELECT SYS_FILE_NAME FROM HR_L_FILE WHERE DELETED_FLAG = '0'");
                            originFileSqlCon.addMainSql(" AND MAIN_TABLE = 'HR_L_PAPERS'");
                            originFileSqlCon.addCondition(hrLpapersModel.getRowId(), " AND MAIN_ID = ? ORDER BY SORT_NBR");

                            List<HrLfileModel> hrLfileModelList = setSql(hrLfileMapper, originFileSqlCon);

                            if (hrLfileModelList.isEmpty()) {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLong(papperRecordMap, "lineNumber")
                                        + "行证照，原证照附件个数为0，请联系管理员！。。。");
                            }

                            // 个数相同
                            if (hrLfileModelList.size() == fileNameList.size()) {
                                // 比较明细
                                boolean insertPaperFlag = false;
                                for (int i = 0; i < hrLfileModelList.size(); i++) {
                                    if (!hrLfileModelList.get(i).getSysFileName().equals(fileNameList.get(i))) {
                                        // 存在不同的文件
                                        insertPaperFlag = true;
                                        break;
                                    }
                                }

                                // 明细不同直接重新上传
                                if (insertPaperFlag) {
                                    Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber,
                                            paperType, paperCode, expiryType, expiryDate, pappersVersion + 1L, tempFilePath, absolutePath,
                                            relativePath, fileNameList);
                                    notDeletePapperRowIdList.add(pappersRowId);
                                } else {
                                    // 证照编码改变 || 有效期类型改变 || （有效期类型为有效期 && 时间有改变）
                                    if (!hrLpapersModel.getPaperCode().equals(paperCode) || !hrLpapersModel.getExpiryType().equals(expiryType) || ("1"
                                            .equals(hrLpapersModel.getExpiryType()) && !hrLpapersModel.getExpiryDate().equals(expiryDate))) {
                                        Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(),
                                                papperRecordLineNumber, paperType, paperCode, expiryType, expiryDate, pappersVersion + 1L,
                                                tempFilePath, absolutePath, relativePath, fileNameList);
                                        notDeletePapperRowIdList.add(pappersRowId);
                                    } else {
                                        notDeletePapperRowIdList.add(hrLpapersModel.getRowId());
                                    }
                                }
                            } else {
                                // 个数不同直接重新上传
                                Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber,
                                        paperType, paperCode, expiryType, expiryDate, pappersVersion + 1L, tempFilePath, absolutePath, relativePath,
                                        fileNameList);
                                notDeletePapperRowIdList.add(pappersRowId);
                            }
                        }
                    }
                }

                // 删除paper多余数据
                SqlCon deletedPappersSqlCon = new SqlCon();
                deletedPappersSqlCon.addMainSql("UPDATE HR_L_PAPERS SET DELETED_FLAG = '1'");
                deletedPappersSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                deletedPappersSqlCon.addConditionWithNull(company.getRowId(), " AND CUSSUP_ID = ?");
                if (!notDeletePapperRowIdList.isEmpty()) {
                    deletedPappersSqlCon.addMainSql(" AND ROW_ID NOT IN (");
                    for (int i = 0; i < notDeletePapperRowIdList.size(); i++) {
                        deletedPappersSqlCon.addMainSql(notDeletePapperRowIdList.get(i).toString());
                        if (i != notDeletePapperRowIdList.size() - 1) {
                            deletedPappersSqlCon.addMainSql(",");
                        }
                    }
                    deletedPappersSqlCon.addMainSql(")");
                }
                setSql(hrLpapersMapper, deletedPappersSqlCon);

                if (hrLaccountModels.size() > 0) {
                    // 账户信息
                    for (HrLaccountModel hrLaccountModel : hrLaccountModels) {
                        hrLaccountModel.setSrmId(srmModelEdit.getRowId());
                        hrLaccountModel.setVersion(version);
                        hrLaccountModel.setStatus(PigConstants.STATUS_NOR);
                        hrLaccountModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                        hrLaccountModel.setRowId(null);
                    }
                    // 编辑时时account 原有数据删除
                    SqlCon sqlAccount = new SqlCon();
                    sqlAccount.addMainSql("UPDATE HR_L_ACCOUNT SET DELETED_FLAG = '1' WHERE DELETED_FLAG = '0' ");
                    sqlAccount.addCondition(company.getRowId(),
                            " AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE CUSSUP_ID = ? AND DELETED_FLAG = '1' AND ROW_ID = HR_L_ACCOUNT.SRM_ID)");
                    setSql(hrLaccountMapper, sqlAccount);
                    hrLaccountMapper.inserts(hrLaccountModels);
                }
                if (hrLlinkModels.size() > 0) {
                    // 联系人信息
                    for (HrLlinkModel hrLlinkModel : hrLlinkModels) {
                        hrLlinkModel.setSrmId(srmModelEdit.getRowId());
                        hrLlinkModel.setVersion(version);
                        hrLlinkModel.setStatus(PigConstants.STATUS_NOR);
                        hrLlinkModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                        hrLlinkModel.setRowId(null);

                    }
                    // 编辑时时link 原有数据删除
                    SqlCon sqlLink = new SqlCon();
                    sqlLink.addMainSql("UPDATE HR_L_LINK SET DELETED_FLAG = '1' WHERE DELETED_FLAG = '0' ");
                    sqlLink.addCondition(company.getRowId(),
                            " AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE CUSSUP_ID = ? AND DELETED_FLAG = '1' AND ROW_ID = HR_L_LINK.SRM_ID)");
                    setSql(hrLlinkMapper, sqlLink);
                    hrLlinkMapper.inserts(hrLlinkModels);
                }

                if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                    SqlCon srmSql = new SqlCon();
                    srmSql.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");
                    srmSql.addCondition(getFarmId(), " AND FARM_ID = ?");
                    srmSql.addCondition(CompanyConstants.CUS_SUP_TYPE_CUS, " AND CUSSUP_TYPE = ?");
                    SrmModel srmModel = getModel(srmMapper, srmSql);
                    srmModel.setIsSaleAccount(isSaleAccount);
                    srmMapper.update(srmModel);
                }

                // START HANA
                if (HanaUtil.isToHanaCheck(getFarmId())) {
                    SqlCon srmSqlCon = new SqlCon();
                    srmSqlCon.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");
                    srmSqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");
                    srmSqlCon.addCondition(CompanyConstants.CREATE_TYPE_BUILT, " AND CREATE_TYPE = ?");
                    List<SrmModel> srmModelList = getList(srmMapper, srmSqlCon);

                    // 存在已经同步的场
                    if (srmModelList != null && !srmModelList.isEmpty()) {
                        HanaClientOrProvider hanaClientOrProvider = new HanaClientOrProvider();
                        // 业务伙伴类别
                        hanaClientOrProvider.setMTC_CardType(cussupType);
                        // 客户/供应商编号
                        Map<String, Object> map2 = company.getData();
                        Map<String, String> infoMap2 = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map2, "rowId"));
                        String companyCode = infoMap2.get("COMPANY_CODE");

                        hanaClientOrProvider.setMTC_CardCode(companyCode);
                        // 客户/供应商名称
                        hanaClientOrProvider.setMTC_CardName(company.getCompanyName());
                        // 客户/供应商分类
                        if ("C".equalsIgnoreCase(cussupType)) {
                            hanaClientOrProvider.setMTC_GroupCode("100");
                        } else if ("S".equalsIgnoreCase(cussupType)) {
                            hanaClientOrProvider.setMTC_GroupCode("101");
                        }
                        // 客户/供应商简称
                        hanaClientOrProvider.setMTC_CardFName(company.getSortName());
                        // 客户/供应商地址
                        hanaClientOrProvider.setMTC_AliasName(company.getCompanyAddress());
                        // 客户/供应商-归属
                        Set<String> mtcBranchIdSet = new HashSet<String>();
                        List<HanaClientOrProvider.MTC_Branch> mtcBranchList = new ArrayList<HanaClientOrProvider.MTC_Branch>();
                        for (SrmModel srmModel : srmModelList) {
                            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(srmModel.getFarmId());
                            String mtcBranchId = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                            if (!mtcBranchIdSet.contains(mtcBranchId)) {
                                mtcBranchIdSet.add(mtcBranchId);

                                // 除去太仓场
                                if (!"C1028".equals(mtcBranchId)) {
                                    HanaClientOrProvider.MTC_Branch mtcBranch = new HanaClientOrProvider.MTC_Branch();
                                    mtcBranch.setMTC_Branch(mtcBranchId);
                                    mtcBranchList.add(mtcBranch);
                                }
                            }
                        }
                        hanaClientOrProvider.setDetailList(mtcBranchList);

                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        // mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                        // 业务代码:客户/供应商
                        if (cussupType.equalsIgnoreCase("C")) {
                            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1070);
                        } else if (cussupType.equalsIgnoreCase("S")) {
                            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1080);
                        }
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaClientOrProvider.getMTC_CardCode());
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_U);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaClientOrProvider);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        hanaCommonService.insertMTC_ITFC(mtcITFC);
                    }
                }
                // END HANA
            } else {
                // 新增
                company.setRegisterTime(TimeUtil.getSysTimestamp());
                company.setOpenTime(TimeUtil.getSysTimestamp());
                companyHis.setRegisterTime(TimeUtil.getSysTimestamp());
                companyHis.setOpenTime(TimeUtil.getSysTimestamp());

                // 关系表
                Long version = 1L;
                String legalPerson = Maps.getString(inputMap, "legalPerson");
                String supplierType = Maps.getString(inputMap, "supplierType");
                String businessPartnerType = Maps.getString(inputMap, "businessPartnerType");
                String tradeCurrency = Maps.getString(inputMap, "tradeCurrency");
                String payCondition = Maps.getString(inputMap, "payCondition");
                String payDays = Maps.getString(inputMap, "payDays");
                Long creditLimit = Maps.getLongClass(inputMap, "creditLimit");
                SrmModel srmModel = new SrmModel();
                if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                    String customerType = Maps.getString(inputMap, "customerType");
                    Long sowSize = Maps.getLongClass(inputMap, "sowSize");
                    String customerLevel = Maps.getString(inputMap, "customerLevel");
                    String sellType = Maps.getString(inputMap, "sellType");
                    Long sellDivision = Maps.getLongClass(inputMap, "sellDivision");
                    Long sellArea = Maps.getLongClass(inputMap, "sellArea");
                    Long salesman = Maps.getLongClass(inputMap, "salesman");
                    Long fixedBalanceDay = Maps.getLongClass(inputMap, "fixedBalanceDay");
                    String collectCondition = Maps.getString(inputMap, "collectCondition");
                    String collectDays = Maps.getString(inputMap, "collectDays");
                    Long customerParent = Maps.getLongClass(inputMap, "customerParent");
                    srmModel.setCustomerType(customerType);
                    srmModel.setSowSize(sowSize);
                    srmModel.setCustomerLevel(customerLevel);
                    srmModel.setSellType(sellType);
                    srmModel.setSellDivision(sellDivision);
                    srmModel.setSellArea(sellArea);
                    srmModel.setSalesman(salesman);
                    srmModel.setFixedBalanceDay(fixedBalanceDay);
                    srmModel.setCollectCondition(collectCondition);
                    srmModel.setCollectDays(collectDays);
                    srmModel.setCustomerparent(customerParent);
                }

                srmModel.setNotes(company.getNotes());
                srmModel.setStatus(PigConstants.STATUS_NOR);
                srmModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                srmModel.setCussupType(cussupType);
                // 公司id
                srmModel.setCompanyId(getCompanyId());
                // 猪场标识
                srmModel.setFarmId(getFarmId());
                srmModel.setCreateType(createType);
                srmModel.setIsSaleAccount(isSaleAccount);
                srmModel.setVersion(version);
                srmModel.setLegalPerson(legalPerson);
                srmModel.setSupplierType(supplierType);
                srmModel.setBusinessPartnerType(businessPartnerType);
                srmModel.setTradeCurrency(tradeCurrency);
                srmModel.setPayCondition(payCondition);
                srmModel.setPayDays(payDays);
                srmModel.setCreditLimit(creditLimit);

                String eventName = "CUSTOMER_INFO_CODE";
                if (CompanyConstants.CUS_SUP_TYPE_SUP.equals(cussupType)) {
                    eventName = "SUPPLIER_CODE";
                }
                company.setCompanyCode(ParamUtil.getBCode(eventName, getEmployeeId(), get10000CompanyId(), get10000FarmId()));
                companyHis.setCompanyCode(company.getCompanyCode());

                long companyId = SeqUtil.getSeq(SeqEnum.COMPANY);
                company.setRowId(companyId);
                companyHis.setCussupId(companyId);
                companyHis.setChangeContent(changeContent);
                companyHis.setChangeDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                companyHis.setChangeWorker(getEmployeeId());
                companyHis.setFarmId(getFarmId());
                companyHis.setVersion(1L);
                companyMapper.insert(company);
                companyHisMapper.insert(companyHis);
                srmModel.setCussupId(companyId);
                srmMapper.insert(srmModel);

                // 证照信息
                if (papperRecordMapList.size() > 0) {
                    for (Map<String, Object> papperRecordMap : papperRecordMapList) {
                        List<String> fileNameList = Maps.getList(papperRecordMap, "fileNameList");
                        if (fileNameList == null || fileNameList.isEmpty()) {
                            Thrower.throwException(BasicInfoException.PARPER_RECORD_FILE_IS_EMPTY, Maps.getLong(papperRecordMap, "lineNumber"));
                        }

                        Long papperRecordLineNumber = Maps.getLong(papperRecordMap, "lineNumber");
                        String paperType = Maps.getString(papperRecordMap, "paperType");
                        String paperCode = Maps.getString(papperRecordMap, "paperCode");
                        String expiryType = Maps.getString(papperRecordMap, "expiryType");
                        Date expiryDate = Maps.getDate(papperRecordMap, "expiryDate");

                        this.editPapersAndFile(srmModel.getRowId(), company.getRowId(), papperRecordLineNumber, paperType, paperCode, expiryType,
                                expiryDate, 1L, tempFilePath, absolutePath, relativePath, fileNameList);
                    }
                }

                if (hrLaccountModels.size() > 0) {
                    // 账户信息
                    for (HrLaccountModel hrLaccountModel : hrLaccountModels) {
                        hrLaccountModel.setSrmId(srmModel.getRowId());
                        hrLaccountModel.setVersion(Long.parseLong("1"));
                    }
                    hrLaccountMapper.inserts(hrLaccountModels);
                }
                if (hrLlinkModels.size() > 0) {
                    // 联系人信息
                    for (HrLlinkModel hrLlinkModel : hrLlinkModels) {
                        hrLlinkModel.setSrmId(srmModel.getRowId());
                        hrLlinkModel.setVersion(Long.parseLong("1"));
                    }
                    hrLlinkMapper.inserts(hrLlinkModels);
                }

                Object farmIdsObject = Maps.get(inputMap, "initialFarmIds", null);
                List<String> farmIdsList = null;
                if (farmIdsObject instanceof List) {
                    farmIdsList = (List<String>) farmIdsObject;
                } else if (farmIdsObject instanceof String) {
                    farmIdsList = new ArrayList<String>();
                    farmIdsList.add((String) farmIdsObject);
                }

                // 初始使用猪场
                if (farmIdsList != null && !farmIdsList.isEmpty()) {
                    for (String farmIdString : farmIdsList) {
                        Long farmId = Long.valueOf(farmIdString);
                        CompanyModel farmModel = companyMapper.searchById(farmId);

                        if (farmId != getFarmId()) {
                            SrmModel srmModelEdit = new SrmModel();
                            srmModelEdit.setStatus(PigConstants.STATUS_NOR);
                            srmModelEdit.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                            srmModelEdit.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                            srmModelEdit.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                            srmModelEdit.setCussupType(cussupType);
                            srmModelEdit.setCompanyId(farmModel.getSupCompanyId());
                            srmModelEdit.setFarmId(farmModel.getRowId());
                            srmModelEdit.setCreateType("2");
                            srmModelEdit.setIsSaleAccount(isSaleAccount);
                            srmModelEdit.setVersion(version);
                            srmModelEdit.setCussupId(companyId);
                            srmMapper.insert(srmModelEdit);
                        }
                    }

                    // START HANA
                    if (HanaUtil.isToHanaCheck(getFarmId())) {
                        // 新建时，供应商（添加初始使用猪场）时传入SAP
                        if ("S".equalsIgnoreCase(company.getCussupType())) {
                            HanaClientOrProvider hanaClientOrProvider = new HanaClientOrProvider();
                            // 业务伙伴类别
                            hanaClientOrProvider.setMTC_CardType(company.getCussupType());
                            // 客户/供应商编号
                            hanaClientOrProvider.setMTC_CardCode(company.getCompanyCode());
                            // 客户/供应商名称
                            hanaClientOrProvider.setMTC_CardName(company.getCompanyName());
                            // 客户/供应商分类
                            if ("C".equalsIgnoreCase(company.getCussupType())) {
                                hanaClientOrProvider.setMTC_GroupCode("100");
                            } else if ("S".equalsIgnoreCase(company.getCussupType())) {
                                hanaClientOrProvider.setMTC_GroupCode("101");
                            }
                            // 客户/供应商简称
                            hanaClientOrProvider.setMTC_CardFName(company.getSortName());
                            // 客户/供应商地址
                            hanaClientOrProvider.setMTC_AliasName(company.getCompanyAddress());
                            // 客户/供应商-归属
                            // 获取去重的mtcBranchId
                            Set<String> mtcBranchIdSet = new HashSet<String>();
                            List<HanaClientOrProvider.MTC_Branch> mtcBranchDetailList = new ArrayList<HanaClientOrProvider.MTC_Branch>();
                            // 初始使用猪场
                            if (farmIdsList != null && !farmIdsList.isEmpty()) {
                                for (String farmIdString : farmIdsList) {
                                    Long farmId = Long.valueOf(farmIdString);
                                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
                                    String mtcBranchId = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                                    if (!mtcBranchIdSet.contains(mtcBranchId)) {
                                        mtcBranchIdSet.add(mtcBranchId);

                                        // 除去太仓场
                                        if (!"C1028".equals(mtcBranchId)) {
                                            HanaClientOrProvider.MTC_Branch mtcBranch = new HanaClientOrProvider.MTC_Branch();
                                            mtcBranch.setMTC_Branch(mtcBranchId);
                                            mtcBranchDetailList.add(mtcBranch);
                                        }
                                    }
                                }
                            } else {
                                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "需要同步到SAP，必须至少选择一个【初始使用猪场】");
                            }
                            hanaClientOrProvider.setDetailList(mtcBranchDetailList);

                            MTC_ITFC mtcITFC = new MTC_ITFC();
                            // 分公司编号
                            // mtcITFC.setMTC_Branch(mtcBranchID);
                            // 业务日期
                            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                            // 业务代码:客户/供应商
                            if (cussupType.equalsIgnoreCase("C")) {
                                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1070);
                            } else if (cussupType.equalsIgnoreCase("S")) {
                                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1080);
                            }
                            // 新农+单据编号
                            mtcITFC.setMTC_DocNum(company.getCompanyCode());
                            // 优先级
                            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                            // 处理区分
                            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                            // 创建日期
                            mtcITFC.setMTC_CreateTime(currentDate);
                            // 同步状态
                            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                            // DB
                            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                            // JSON文件
                            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaClientOrProvider);
                            mtcITFC.setMTC_DataFile(jsonDataFile);
                            // JSON文件长度
                            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                            hanaCommonService.insertMTC_ITFC(mtcITFC);
                        }
                    }
                    // END HANA
                } else {
                    // START HANA
                    if (HanaUtil.isToHanaCheck(getFarmId())) {
                        // 新建时，供应商（添加所属公司（法人））时传入SAP
                        if ("S".equalsIgnoreCase(company.getCussupType())) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "需要同步到SAP，必须至少选择一个【初始使用猪场】");
                        }
                    }
                    // END HANA
                }
            }
        } else {
            // 已建和平台中公司时，页面上cussupId为COMBOBOX框，cussupId值为companyId
            long companyId = Long.valueOf(cussupId);

            // 新增
            if (!(company.getRowId() != 0 && company.getRowId() != null)) {
                // 判断是否已经创建好平台中公司
                SqlCon alreadyCreateSqlCon = new SqlCon();
                alreadyCreateSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
                alreadyCreateSqlCon.addCondition(companyId, " AND CUSSUP_ID = ?");
                alreadyCreateSqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");

                List<SrmModel> srmModelList = getList(srmMapper, alreadyCreateSqlCon);
                if (srmModelList.size() > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "已经创建了平台中公司，请不要重复创建！");
                }
            }

            company = companyMapper.searchById(companyId);

            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(companyId, " AND CUSSUP_ID = ?");
            sqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");

            List<CompanyHisModel> verList = getList(companyHisMapper, sqlCon);
            if (verList.size() > 1) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统错误，历史表存在两条记录，请联系管理员！");
            }

            Long version = 1L;
            if (!verList.isEmpty()) {
                CompanyHisModel companyHisModel = verList.get(0);
                version = companyHisModel.getVersion() + 1L;
            }
            String companyCodeEdit = Maps.getString(CacheUtil.getData("HR_M_COMPANY", String.valueOf(company.getRowId())), "COMPANY_CODE");
            String legalPerson = Maps.getString(inputMap, "legalPerson");
            String supplierType = Maps.getString(inputMap, "supplierType");
            String businessPartnerType = Maps.getString(inputMap, "businessPartnerType");
            String tradeCurrency = Maps.getString(inputMap, "tradeCurrency");
            String payCondition = Maps.getString(inputMap, "payCondition");
            String payDays = Maps.getString(inputMap, "payDays");
            Long creditLimit = Maps.getLongClass(inputMap, "creditLimit");
            SrmModel srmModelEdit = new SrmModel();
            if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                String customerType = Maps.getString(inputMap, "customerType");
                Long sowSize = Maps.getLongClass(inputMap, "sowSize");
                String customerLevel = Maps.getString(inputMap, "customerLevel");
                String sellType = Maps.getString(inputMap, "sellType");
                Long sellDivision = Maps.getLongClass(inputMap, "sellDivision");
                Long sellArea = Maps.getLongClass(inputMap, "sellArea");
                Long salesman = Maps.getLongClass(inputMap, "salesman");
                Long fixedBalanceDay = Maps.getLongClass(inputMap, "fixedBalanceDay");
                String collectCondition = Maps.getString(inputMap, "collectCondition");
                String collectDays = Maps.getString(inputMap, "collectDays");
                Long customerParent = Maps.getLongClass(inputMap, "customerParent");
                srmModelEdit.setCustomerType(customerType);
                srmModelEdit.setSowSize(sowSize);
                srmModelEdit.setCustomerLevel(customerLevel);
                srmModelEdit.setSellType(sellType);
                srmModelEdit.setSellDivision(sellDivision);
                srmModelEdit.setSellArea(sellArea);
                srmModelEdit.setSalesman(salesman);
                srmModelEdit.setFixedBalanceDay(fixedBalanceDay);
                srmModelEdit.setCollectCondition(collectCondition);
                srmModelEdit.setCollectDays(collectDays);
                srmModelEdit.setCustomerparent(customerParent);
            }
            srmModelEdit.setNotes(company.getNotes());
            srmModelEdit.setStatus(PigConstants.STATUS_NOR);
            srmModelEdit.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
            srmModelEdit.setCussupType(cussupType);
            // 公司id
            srmModelEdit.setCompanyId(getCompanyId());
            // 猪场标识
            srmModelEdit.setFarmId(getFarmId());
            srmModelEdit.setCreateType(createType);
            srmModelEdit.setIsSaleAccount(isSaleAccount);
            srmModelEdit.setVersion(version);
            srmModelEdit.setLegalPerson(legalPerson);
            srmModelEdit.setSupplierType(supplierType);
            srmModelEdit.setBusinessPartnerType(businessPartnerType);
            srmModelEdit.setTradeCurrency(tradeCurrency);
            srmModelEdit.setPayCondition(payCondition);
            srmModelEdit.setPayDays(payDays);
            srmModelEdit.setCreditLimit(creditLimit);
            srmModelEdit.setCussupId(company.getRowId());
            srmModelEdit.setRowId(null);

            // 编辑时也是srm 原有数据删除
            SqlCon sqlSrm = new SqlCon();
            sqlSrm.addMainSql("UPDATE hr_r_srm SET DELETED_FLAG = 1 ");
            sqlSrm.addCondition(company.getRowId(), "WHERE CUSSUP_ID = ?");
            sqlSrm.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
            sqlSrm.addConditionWithNull(getFarmId(), " AND  FARM_ID= ? ");
            setSql(srmMapper, sqlSrm);
            // 编辑时也是srm新增一条
            srmMapper.insert(srmModelEdit);
            companyHis.setRegisterTime(TimeUtil.getSysTimestamp());
            companyHis.setOpenTime(TimeUtil.getSysTimestamp());
            companyHis.setCompanyCode(companyCodeEdit);
            companyHis.setCussupId(company.getRowId());
            companyHis.setChangeContent(changeContent);
            companyHis.setChangeDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            companyHis.setChangeWorker(getEmployeeId());
            companyHis.setFarmId(getFarmId());
            companyHis.setVersion(version);
            companyHis.setRowId(null);
            // 编辑时也是companyhis 原有数据删除
            SqlCon sqlHis = new SqlCon();
            sqlHis.addMainSql("UPDATE hr_m_company_his SET DELETED_FLAG = '1'");
            sqlHis.addCondition(company.getRowId(), "WHERE CUSSUP_ID = ?");
            sqlHis.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
            sqlHis.addConditionWithNull(getFarmId(), " AND  FARM_ID= ? ");
            setSql(companyHisMapper, sqlHis);
            // 编辑时companyhis表新增一条
            companyHisMapper.insert(companyHis);

            List<Long> notDeletePapperRowIdList = new ArrayList<Long>();

            if (papperRecordMapList.size() > 0) {
                // PAPERS版本自增(不跟随SRM)
                for (Map<String, Object> papperRecordMap : papperRecordMapList) {
                    List<String> fileNameList = Maps.getList(papperRecordMap, "fileNameList");
                    if (fileNameList == null || fileNameList.isEmpty()) {
                        Thrower.throwException(BasicInfoException.PARPER_RECORD_FILE_IS_EMPTY, Maps.getLong(papperRecordMap, "lineNumber"));
                    }

                    String paperType = Maps.getString(papperRecordMap, "paperType");

                    // 用于判断这个证照类型是否有改变
                    SqlCon hrLpapersChangeSqlCon = new SqlCon();
                    hrLpapersChangeSqlCon.addMainSql("SELECT * FROM HR_L_PAPERS WHERE DELETED_FLAG = '0'");
                    hrLpapersChangeSqlCon.addCondition(paperType, " AND PAPER_TYPE = ?");
                    hrLpapersChangeSqlCon.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");

                    List<HrLpapersModel> hrLpapersModelList = setSql(hrLpapersMapper, hrLpapersChangeSqlCon);

                    // 同一个证照类型存在多条合格数据
                    if (hrLpapersModelList.size() > 1) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLong(papperRecordMap, "lineNumber")
                                + "行证照，同一证照类型存在多条合格数据，请联系管理员！。。。");
                    }

                    Long papperRecordLineNumber = Maps.getLong(papperRecordMap, "lineNumber");
                    String paperCode = Maps.getString(papperRecordMap, "paperCode");
                    String expiryType = Maps.getString(papperRecordMap, "expiryType");
                    Date expiryDate = Maps.getDate(papperRecordMap, "expiryDate");

                    // 此证照类型的附件从来没有上传过
                    if (hrLpapersModelList.isEmpty()) {
                        Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber, paperType,
                                paperCode, expiryType, expiryDate, 1L, tempFilePath, absolutePath, relativePath, fileNameList);
                        notDeletePapperRowIdList.add(pappersRowId);
                    } else {
                        HrLpapersModel hrLpapersModel = hrLpapersModelList.get(0);
                        Long pappersVersion = hrLpapersModel.getVersion();
                        // 此证照类型的附件上传过
                        // 原来此证照类型上传的附件
                        SqlCon originFileSqlCon = new SqlCon();
                        originFileSqlCon.addMainSql("SELECT SYS_FILE_NAME FROM HR_L_FILE WHERE DELETED_FLAG = '0'");
                        originFileSqlCon.addMainSql(" AND MAIN_TABLE = 'HR_L_PAPERS'");
                        originFileSqlCon.addCondition(hrLpapersModel.getRowId(), " AND MAIN_ID = ? ORDER BY SORT_NBR");

                        List<HrLfileModel> hrLfileModelList = setSql(hrLfileMapper, originFileSqlCon);

                        if (hrLfileModelList.isEmpty()) {
                            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + Maps.getLong(papperRecordMap, "lineNumber")
                                    + "行证照，原证照附件个数为0，请联系管理员！。。。");
                        }

                        // 个数相同
                        if (hrLfileModelList.size() == fileNameList.size()) {
                            // 比较明细
                            boolean insertPaperFlag = false;
                            for (int i = 0; i < hrLfileModelList.size(); i++) {
                                if (!hrLfileModelList.get(i).getSysFileName().equals(fileNameList.get(i))) {
                                    // 存在不同的文件
                                    insertPaperFlag = true;
                                    break;
                                }
                            }

                            // 明细不同直接重新上传
                            if (insertPaperFlag) {
                                Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber,
                                        paperType, paperCode, expiryType, expiryDate, pappersVersion + 1L, tempFilePath, absolutePath, relativePath,
                                        fileNameList);
                                notDeletePapperRowIdList.add(pappersRowId);
                            } else {
                                // 证照编码改变 || 有效期类型改变 || （有效期类型为有效期 && 时间有改变）
                                if (!hrLpapersModel.getPaperCode().equals(paperCode) || !hrLpapersModel.getExpiryType().equals(expiryType) || ("1"
                                        .equals(hrLpapersModel.getExpiryType()) && !hrLpapersModel.getExpiryDate().equals(expiryDate))) {
                                    Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber,
                                            paperType, paperCode, expiryType, expiryDate, pappersVersion + 1L, tempFilePath, absolutePath,
                                            relativePath, fileNameList);
                                    notDeletePapperRowIdList.add(pappersRowId);
                                } else {
                                    notDeletePapperRowIdList.add(hrLpapersModel.getRowId());
                                }
                            }
                        } else {
                            // 个数不同直接重新上传
                            Long pappersRowId = this.editPapersAndFile(srmModelEdit.getRowId(), company.getRowId(), papperRecordLineNumber, paperType,
                                    paperCode, expiryType, expiryDate, pappersVersion + 1L, tempFilePath, absolutePath, relativePath, fileNameList);
                            notDeletePapperRowIdList.add(pappersRowId);
                        }
                    }
                }
            }

            // 删除paper多余数据
            SqlCon deletedPappersSqlCon = new SqlCon();
            deletedPappersSqlCon.addMainSql("UPDATE HR_L_PAPERS SET DELETED_FLAG = '1'");
            deletedPappersSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            deletedPappersSqlCon.addConditionWithNull(company.getRowId(), " AND CUSSUP_ID = ?");
            if (!notDeletePapperRowIdList.isEmpty()) {
                deletedPappersSqlCon.addMainSql(" AND ROW_ID NOT IN (");
                for (int i = 0; i < notDeletePapperRowIdList.size(); i++) {
                    deletedPappersSqlCon.addMainSql(notDeletePapperRowIdList.get(i).toString());
                    if (i != notDeletePapperRowIdList.size() - 1) {
                        deletedPappersSqlCon.addMainSql(",");
                    }
                }
                deletedPappersSqlCon.addMainSql(")");
            }
            setSql(hrLpapersMapper, deletedPappersSqlCon);

            if (hrLaccountModels.size() > 0) {
                // 账户信息
                for (HrLaccountModel hrLaccountModel : hrLaccountModels) {
                    hrLaccountModel.setSrmId(srmModelEdit.getRowId());
                    hrLaccountModel.setVersion(version);
                    hrLaccountModel.setStatus(PigConstants.STATUS_NOR);
                    hrLaccountModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                    hrLaccountModel.setRowId(null);
                }
                // 编辑时时account 原有数据删除
                SqlCon sqlAccount = new SqlCon();
                sqlAccount.addMainSql("UPDATE HR_L_ACCOUNT SET DELETED_FLAG = '1' WHERE DELETED_FLAG = '0' ");
                sqlAccount.addCondition(company.getRowId(),
                        " AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE CUSSUP_ID = ? AND DELETED_FLAG = '1' AND ROW_ID = HR_L_ACCOUNT.SRM_ID)");
                setSql(hrLaccountMapper, sqlAccount);
                hrLaccountMapper.inserts(hrLaccountModels);
            }
            if (hrLlinkModels.size() > 0) {
                // 联系人信息
                for (HrLlinkModel hrLlinkModel : hrLlinkModels) {
                    hrLlinkModel.setSrmId(srmModelEdit.getRowId());
                    hrLlinkModel.setVersion(version);
                    hrLlinkModel.setStatus(PigConstants.STATUS_NOR);
                    hrLlinkModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                    hrLlinkModel.setRowId(null);

                }
                // 编辑时时link 原有数据删除
                SqlCon sqlLink = new SqlCon();
                sqlLink.addMainSql("UPDATE HR_L_LINK SET DELETED_FLAG = '1' WHERE DELETED_FLAG = '0' ");
                sqlLink.addCondition(company.getRowId(),
                        " AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE CUSSUP_ID = ? AND DELETED_FLAG = '1' AND ROW_ID = HR_L_LINK.SRM_ID)");
                setSql(hrLlinkMapper, sqlLink);
                hrLlinkMapper.inserts(hrLlinkModels);
            }

            if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                SqlCon srmSql = new SqlCon();
                srmSql.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");
                srmSql.addCondition(getFarmId(), " AND FARM_ID = ?");
                srmSql.addCondition(CompanyConstants.CUS_SUP_TYPE_CUS, " AND CUSSUP_TYPE = ?");
                SrmModel srmModel = getModel(srmMapper, srmSql);
                srmModel.setIsSaleAccount(isSaleAccount);
                srmMapper.update(srmModel);
            }

            // SqlCon sqlCon = new SqlCon();
            // sqlCon.addCondition(companyId, " AND CUSSUP_ID = ?");
            //
            // List<CompanyHisModel> companyHisMpdelList = getList(companyHisMapper, sqlCon);
            // if (companyHisMpdelList.size() > 1) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统错误，历史表存在两条记录，请联系管理员！");
            // } else if (companyHisMpdelList.isEmpty()) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "系统错误，历史表中不存在历史，请联系管理员！");
            // }
            //
            // // 关系表
            // Long version = companyHisMpdelList.get(0).getVersion();
            // String legalPerson = Maps.getString(inputMap, "legalPerson");
            // String supplierType = Maps.getString(inputMap, "supplierType");
            // String businessPartnerType = Maps.getString(inputMap, "businessPartnerType");
            // String tradeCurrency = Maps.getString(inputMap, "tradeCurrency");
            // String payCondition = Maps.getString(inputMap, "payCondition");
            // String payDays = Maps.getString(inputMap, "payDays");
            // Long creditLimit = Maps.getLong(inputMap, "creditLimit");
            //
            // SrmModel srmModel = new SrmModel();
            // srmModel.setNotes(company.getNotes());
            // srmModel.setStatus(PigConstants.STATUS_NOR);
            // srmModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
            // srmModel.setCussupType(cussupType);
            // // 公司id
            // srmModel.setCompanyId(getCompanyId());
            // // 猪场标识
            // srmModel.setFarmId(getFarmId());
            // srmModel.setCreateType(createType);
            // srmModel.setIsSaleAccount(isSaleAccount);
            // srmModel.setVersion(version);
            // srmModel.setLegalPerson(legalPerson);
            // srmModel.setSupplierType(supplierType);
            // srmModel.setBusinessPartnerType(businessPartnerType);
            // srmModel.setTradeCurrency(tradeCurrency);
            // srmModel.setPayCondition(payCondition);
            // srmModel.setPayDays(payDays);
            // srmModel.setCreditLimit(creditLimit);
            // srmModel.setCussupId(companyId);
            // srmMapper.insert(srmModel);
        }
    }

    // 编辑PapersAndFile
    private Long editPapersAndFile(Long srmModelRowId, Long cussupId, Long papperRecordLineNumber, String paperType, String paperCode,
            String expiryType, Date expiryDate, Long version, String tempFilePath, String absolutePath, String relativePath,
            List<String> fileNameList) throws Exception {
        if (StringUtil.isBlank(paperType)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + papperRecordLineNumber + "行的证照类型不能为空！");
        } else {
            paperType = paperType.trim();
        }

        if (StringUtil.isBlank(paperCode)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + papperRecordLineNumber + "行的证照编码不能为空！");
        } else {
            paperCode = paperCode.trim();
        }

        if (expiryType == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + papperRecordLineNumber + "行的有效期类型不能为空！");
        } else {
            // 有效期类型是有效期,必须填写有效期时间
            if ("1".equals(expiryType)) {
                if (expiryDate == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第" + papperRecordLineNumber + "行的有效期不能为空！");
                }
            }
        }

        HrLpapersModel hrLpapersModel = new HrLpapersModel();
        hrLpapersModel.setStatus(PigConstants.STATUS_NOR);
        hrLpapersModel.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
        hrLpapersModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        hrLpapersModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        hrLpapersModel.setCussupId(cussupId);
        hrLpapersModel.setSrmId(srmModelRowId);
        hrLpapersModel.setPaperType(paperType);
        hrLpapersModel.setPaperCode(paperCode);
        hrLpapersModel.setExpiryType(expiryType);
        hrLpapersModel.setExpiryDate(expiryDate);
        hrLpapersModel.setVersion(version);
        hrLpapersModel.setLineNumber(papperRecordLineNumber);
        hrLpapersMapper.insert(hrLpapersModel);

        for (int i = 0; i < fileNameList.size(); i++) {
            String fileName = fileNameList.get(i);
            // 判断此文件是否已经上传过
            SqlCon existsFileSqlCon = new SqlCon();
            existsFileSqlCon.addMainSql("SELECT ROW_ID FROM HR_L_FILE");
            existsFileSqlCon.addCondition(fileName, " WHERE SYS_FILE_NAME = ? LIMIT 1");

            List<Map<String, Object>> existsFileList = paramMapperSetSQL(paramMapper, existsFileSqlCon);

            // 文件是新上传的，去临时目录里拷贝
            if (existsFileList.isEmpty()) {
                UploadFileUtil.copyFile(tempFilePath, absolutePath, fileName);
            }

            HrLfileModel hrLfileModel = new HrLfileModel();
            hrLfileModel.setSortNbr(Long.valueOf(i + 1));
            hrLfileModel.setStatus(CommonConstants.STATUS);
            hrLfileModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            hrLfileModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            hrLfileModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            hrLfileModel.setMainTable("HR_L_PAPERS");
            hrLfileModel.setMainId(hrLpapersModel.getRowId());
            hrLfileModel.setSysFileName(fileName);
            hrLfileModel.setFilePath(absolutePath);
            hrLfileModel.setRelativePath(relativePath);
            hrLfileMapper.insert(hrLfileModel);
        }

        return hrLpapersModel.getRowId();
    }

    @Override
    public void deleteCustomesrAndSuppliers(Map<String, Object> inParam) throws Exception {

        // 获取入参
        List<Map> list = getJsonList(Maps.getString(inParam, "deleteList"), Map.class);
        String cussupType = Maps.getString(inParam, "cussupType");

        if (list == null || list.isEmpty()) {
            Thrower.throwException(BasicInfoException.DELETE_CUSSUP_NO_ID_ERROR);
        }

        // 存放错误信息
        List<CompanyModel> errorList = new ArrayList<>();

        int len = list.size();
        long[] ids = new long[len];
        List<Long> updateCompanyList = new ArrayList<>();

        // 客户页面需要查是有该猪场有供应商使用该公司（供应商页面查客户）
        String selfCussupType = CompanyConstants.CUS_SUP_TYPE_CUS;
        if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
            selfCussupType = CompanyConstants.CUS_SUP_TYPE_SUP;
        }

        for (int i = 0; i < len; i++) {
            Map<String, Object> map = list.get(i);
            String createType = Maps.getString(map, "createType");
            long id = Maps.getLong(map, "id");
            ids[i] = id;

            /* 自建的需判断是否有已建使用到，如果有，抛出异常 ,自建的可以删除公司 */
            if (CompanyConstants.CREATE_TYPE_SELF.equals(createType)) {
                updateCompanyList.add(id);

                SqlCon sql = getCusSupSql(null, selfCussupType, null);
                sql.addConditionWithNull(id, " AND t1.ROW_ID= ? ");
                List<CompanyModel> companyList = setSql(companyMapper, sql);
                if (companyList != null && !companyList.isEmpty()) {
                    errorList.addAll(companyList);
                }
            }
        }
        // 错误列表不为空抛出异常
        if (!errorList.isEmpty()) {
            StringBuilder companyCodes = new StringBuilder();
            for (CompanyModel model : errorList) {
                companyCodes.append(model.getCompanyCode() + "| ");
            }

            String pageName = "供应商";
            String toPageName = "客户";
            if (CompanyConstants.CUS_SUP_TYPE_CUS.equals(cussupType)) {
                pageName = "客户";
                toPageName = "供应商";
            }
            Thrower.throwException(BasicInfoException.DELETE_CUSSUP_HAS_RECORDS_ERROR, companyCodes.toString(), pageName, toPageName, toPageName);
        }

        // 删除公司
        if (!updateCompanyList.isEmpty()) {
            setDeletes(companyMapper, updateCompanyList, "ROW_ID");
        }

        /* 所有的类型都需删除关系表 */
        List<SqlCon> sqlCons = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            SqlCon sql = new SqlCon();
            sql.addConditionWithNull(getFarmId(), " AND  FARM_ID= ? ");
            sql.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
            sql.addConditionWithNull(ids[i], " AND CUSSUP_ID = ?");
            sqlCons.add(sql);
        }
        setDeletesM(srmMapper, sqlCons);

        List<SqlCon> sqlConHis = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            SqlCon sqlHis = new SqlCon();
            sqlHis.addConditionWithNull(getFarmId(), " AND  FARM_ID= ? ");
            sqlHis.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
            sqlHis.addConditionWithNull(ids[i], " AND CUSSUP_ID = ?");
            sqlConHis.add(sqlHis);
        }
        setDeletesM(companyHisMapper, sqlConHis);

        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sqlConSrm = new SqlCon();
        sqlConSrm.addMainSql("SELECT row_id rowId FROM hr_r_srm where deleted_flag = 0 and status = 1 ");
        sqlConSrm.addConditionWithNull(getFarmId(), " AND  FARM_ID =  ? ");
        sqlConSrm.addConditionWithNull(cussupType, " AND CUSSUP_TYPE= ? ");
        sqlConSrm.addCondition(rowIds, " and CUSSUP_ID in ? ", false, true);
        List<SrmModel> srmId = setSql(srmMapper, sqlConSrm);
        long[] srmIdArry = new long[srmId.size()];
        for (int i = 0; i < srmId.size(); i++) {
            SrmModel srmModel = srmId.get(i);
            srmIdArry[i] = srmModel.getRowId();
        }

        List<SqlCon> sqlConPaper = new ArrayList<>();
        for (int i = 0; i < srmId.size(); i++) {
            SqlCon sqlPaper = new SqlCon();
            sqlPaper.addConditionWithNull(srmIdArry[i], " AND SRM_ID = ?");
            sqlConPaper.add(sqlPaper);
        }
        setDeletesM(hrLpapersMapper, sqlConPaper);

        List<SqlCon> sqlConAccount = new ArrayList<>();

        for (int i = 0; i < srmId.size(); i++) {
            SqlCon sqlAccount = new SqlCon();
            sqlAccount.addConditionWithNull(srmIdArry[i], " AND SRM_ID = ?");
            sqlConAccount.add(sqlAccount);
        }
        setDeletesM(hrLaccountMapper, sqlConAccount);

        List<SqlCon> sqlConLink = new ArrayList<>();

        for (int i = 0; i < srmId.size(); i++) {
            SqlCon sqlLink = new SqlCon();
            sqlLink.addConditionWithNull(srmIdArry[i], " AND SRM_ID = ?");
            sqlConLink.add(sqlLink);
        }
        setDeletesM(hrLlinkMapper, sqlConLink);

    }

    @Override
    public BasePageInfo saerchLineByConditionToPage(Map<String, Object> map) throws Exception {
        setToPage();
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sql.addCondition(map.get("businessCode"), " AND BUSINESS_CODE LIKE ?", true);
        sql.addCondition(map.get("lineName"), " AND LINE_NAME LIKE ?", true);

        return getPageInfo(getList(lineMapper, sql));
    }

    @Override
    public Object searchPigHouseToListForAdvancedSearch() {
        List<PigHouseModel> pigHouseList = pigHouseMapper.searchToList();
        PigHouseModel pigHouseModel = new PigHouseModel();
        pigHouseModel.set("rowId", "-1");
        pigHouseModel.set("houseTypeName", "任意类别");
        pigHouseList.add(0, pigHouseModel);
        return pigHouseList;
    }

    @Override
    public BasePageInfo searchHouseToPageForAdvancedSearch(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, DELETED_FLAG AS deletedFlag, BUSINESS_CODE AS businessCode, ");
        sqlCon.addMainSql(" HOUSE_NAME AS houseName, HOUSE_TYPE AS houseType, DEPRECIATION_POLICY AS depreciationPolicy,");
        sqlCon.addMainSql(" COST AS cost, NOTES AS notes,");
        sqlCon.addMainSql(" (SELECT T1.pigQty FROM");
        sqlCon.addMainSql(" (SELECT HOUSE_ID,COUNT(1) AS pigQty FROM PP_L_PIG WHERE ");
        sqlCon.addMainSql(" DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS <=18 ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY HOUSE_ID) T1");
        sqlCon.addMainSql(" WHERE T1.HOUSE_ID = ROW_ID)");
        sqlCon.addMainSql(" AS pigQty,");
        sqlCon.addMainSql(" (SELECT T2.houseVolume FROM ");
        sqlCon.addMainSql(" (SELECT HOUSE_ID, SUM(IFNULL(PIG_NUM,0)) AS houseVolume FROM PP_O_PIGPEN WHERE");
        sqlCon.addMainSql(" DELETED_FLAG = '0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY HOUSE_ID) T2");
        sqlCon.addMainSql(" WHERE T2.HOUSE_ID = ROW_ID)");
        sqlCon.addMainSql("  AS houseVolume FROM PP_O_HOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG='0' AND STATUS = '1' ");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(inputMap.get("businessCode"), " AND BUSINESS_CODE LIKE ?", true);
        sqlCon.addCondition(inputMap.get("houseName"), " AND HOUSE_NAME LIKE ?", true);
        if (inputMap.get("houseType") != null && ((String) inputMap.get("houseType")).trim() != "" && !inputMap.get("houseType").equals("-1")) {
            sqlCon.addCondition(inputMap.get("houseType"), " AND HOUSE_TYPE = ?");
        }
        sqlCon.addMainSql(" ORDER BY HOUSE_TYPE");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        setToPage();
        List<Map<String, Object>> result = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : result) {
            map.put("houseTypeName", CacheUtil.getName(String.valueOf(map.get("houseType")), NameEnum.HOUSE_TYPE_NAME));
        }
        return getPageInfo(result);
    }

    @Override
    public List<CompanyModel> searchCusSupForCreate(String createType, String cussupType, String companyName) throws Exception {
        // 2、CompanyConstants.CREATE_TYPE_PLAT 查询平台中公司
        if (CompanyConstants.CREATE_TYPE_PLAT.equals(createType)) {
            SqlCon sql = new SqlCon();
            sql.addConditionWithNull(getFarmId(), " AND ROW_ID <> ?");
            // 去除其余公司建的客户、供应商的公司
            sql.addMainSql(" AND CUSSUP_TYPE IS NULL ");
            sql.addCondition(companyName, " AND COMPANY_NAME like ?", true);
            sql.addMainSql(" AND COMPANY_CODE NOT IN ('SYS','10000') ");
            return getList(companyMapper, sql);
        }

        // 1、查询自建供应商客户 || 3、 查询已建（同步的）供应商客户
        if (CompanyConstants.CREATE_TYPE_SELF.equals(createType) || CompanyConstants.CREATE_TYPE_BUILT.equals(createType)) {
            // 供应商页面查询的是已经自建的客户
            SqlCon sql = getCusSupSql(createType, cussupType, companyName);
            return setSql(companyMapper, sql);
        }

        return new ArrayList<>();
    }

    /**
     * @Description: 查询客户供应商
     * @author zhangjs
     * @param createType
     * @param cussupType
     * @param companyName
     * @return
     */
    private SqlCon getCusSupSql(String createType, String cussupType, String companyName) {
        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT t1.ROW_ID rowId, t1.ROW_ID cussupId,t1.COMPANY_CODE companyCode,t1.COMPANY_NAME companyName,");
        sql.addMainSql(" t1.SORT_NAME sortName,t1.COMPANY_NAME_EN companyNameEn,t1.PROVINCE province,t1.CITY city,t1.COUNTY  county ");
        sql.addMainSql(" ,t.CREATE_TYPE createType,t1.COMPANY_ADDRESS companyAddress,t1.LONGITUDE longitude,t1.LATITUDE latitude,t1.NOTES notes,");
        sql.addMainSql(" t.IS_SALE_ACCOUNT isSaleAccount,t1.PHONE phone");
        sql.addMainSql(" FROM HR_R_SRM t");
        sql.addMainSql(" INNER JOIN HR_M_COMPANY t1 ON t.CUSSUP_ID = t1.ROW_ID AND t1.DELETED_FLAG='0'");
        sql.addMainSql(" WHERE t.DELETED_FLAG='0' ");
        sql.addConditionWithNull(getFarmId(), " AND t.FARM_ID = ?");
        sql.addCondition(createType, " AND t.CREATE_TYPE =?");
        sql.addCondition(cussupType, " AND t.CUSSUP_TYPE= ?");
        sql.addCondition(companyName, " AND t1.COMPANY_NAME like ?", true);
        return sql;
    }

    @Override
    public List<PigpenModel> searchValidPigpenToList(String changeType, long houseId, long lineId, String eventName, String saleDescribe)
            throws Exception {

        // 猪只数量
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T0.PIGPEN_ID ROW_ID,COUNT(*) NOTES FROM pp_l_pig T0 WHERE ");
        sqlCon.addMainSql("  T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 ");
        sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
        if ("TO_CHILD_CARE".equals(eventName)) {
            if (changeType.equals("3")) {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '15'");
            } else {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '14'");
            }
        } else if ("TO_CHILD_FATTEN".equals(eventName)) {
            if (changeType.equals("5")) {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '16'");
            } else {
                sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS = '15'");
            }
        } else if ("GOOD_PIG_SELL".equals(eventName)) {
            sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16,17,18)");
        } else if ("GOOD_PIG_DIE".equals(eventName)) {
            // 商品猪死亡不能查出留种猪
            sqlCon.addMainSql(" AND T0.PIG_TYPE = 3 AND T0.PIG_CLASS IN(14,15,16)");
        }
        sqlCon.addMainSql(" GROUP BY T0.PIGPEN_ID ");
        // 根据猪栏分组，该pigClass下的猪只
        List<PigpenModel> pigQtyList = setSql(pigpenMapper, sqlCon);

        sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(houseId, " AND HOUSE_ID = ?");
        // 该猪舍下的猪栏
        List<PigpenModel> list = getList(pigpenMapper, sqlCon);

        if (pigQtyList.isEmpty() || list.isEmpty()) {
            return list;
        }

        // 为猪栏赋值猪只数量
        for (int i = 0; i < list.size(); i++) {
            PigpenModel pigpen = list.get(i);

            for (int j = 0; j < pigQtyList.size(); j++) {
                PigpenModel qtyPigpen = pigQtyList.get(j);
                if (pigpen.getRowId().equals(qtyPigpen.getRowId())) {
                    pigpen.set("pigQty", qtyPigpen.getNotes());
                    pigQtyList.remove(j);
                    j--;
                }
            }
            if (pigpen.get("pigQty") == null) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Override
    public void editFarmBind(Map<String, Object> inputMap) throws Exception {
        String companyCode = Maps.getString(inputMap, "companyCode").toUpperCase();
        String userName = Maps.getString(inputMap, "userName");
        String password = Maps.getString(inputMap, "password");
        String notes = Maps.getString(inputMap, "notes");
        Long userId = getUserId();

        CoreUserModel cuser = new CoreUserModel();

        cuser.setCompanyCode(companyCode);
        cuser.setUserName(userName);
        CoreUserModel userModel = iSecurityLogin.userLogin(cuser);

        /** 验证帐号是否正确 */
        if (userModel == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该账号公司编码或用户名不正确！");
        }

        if (!userModel.getPassword().equals(MD5Util.generateMD5code(password))) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该账号密码不正确！");
        }
        if (userModel.getRowId().equals(userId)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "账号无法绑定自身！");
        }

        /** 根据帐号的Id去判断是否已经绑定过 */
        SqlCon isBindSql = new SqlCon();
        isBindSql.addMainSql("SELECT COUNT(1) AS rowId FROM hr_r_farm_bind");
        isBindSql.addMainSql(" WHERE DELETED_FLAG = 0");
        isBindSql.addCondition(userId, " AND USER_ID = ?");
        isBindSql.addCondition(userModel.getRowId(), " AND BIND_USER_ID = ?");
        FarmBindModel bindModel = setSql(farmBindMapper, isBindSql).get(0);
        if (bindModel.getRowId() > 0) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "已经绑定过改账号，请不要重复操作！");
        }

        /** 若没有则插入bind表，两条 */
        List<FarmBindModel> bindList = new ArrayList<>();
        FarmBindModel model = new FarmBindModel();
        model.setBindUserId(userModel.getRowId());
        model.setFarmId(userModel.getHrCompany().getRowId());
        model.setCompanyId(userModel.getHrCompany().getSupCompanyId());
        model.setCompanyCode(userModel.getCompanyCode());
        model.setUserName(userName);
        model.setDeletedFlag(CommonConstants.DELETED_FLAG);
        model.setNotes(notes);
        model.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        model.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        model.setPassword(userModel.getPassword());
        model.setStatus(CommonConstants.STATUS);
        model.setUserId(userId);
        model.setIsAsync("N");
        bindList.add(model);

        model = new FarmBindModel();
        UserDetail userDetail = getUserDetail();
        model.setBindUserId(userId);
        model.setFarmId(getFarmId());
        model.setCompanyId(getCompanyId());
        model.setCompanyCode(userDetail.getCompanyCode());
        model.setUserName(userDetail.getUsername());
        model.setDeletedFlag(CommonConstants.DELETED_FLAG);
        model.setNotes(notes);
        model.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
        model.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
        model.setPassword(userDetail.getPassword());
        model.setStatus(CommonConstants.STATUS);
        model.setUserId(userModel.getRowId());
        model.setIsAsync("N");
        bindList.add(model);
        farmBindMapper.inserts(bindList);

    }

    @Override
    public List<FarmBindModel> searchFarmBind() throws Exception {
        SqlCon bindSql = new SqlCon();
        bindSql.addMainSql("SELECT * FROM hr_r_farm_bind WHERE DELETED_FLAG = 0");
        bindSql.addCondition(getUserId(), " AND USER_ID = ?");
        bindSql.addMainSql(" UNION");
        bindSql.addMainSql(" SELECT b1.* FROM hr_r_farm_bind b1");
        bindSql.addMainSql(" INNER JOIN hr_r_farm_bind b2");
        bindSql.addMainSql(" ON b1.USER_ID = b2.BIND_USER_ID AND b2.DELETED_FLAG = 0");
        bindSql.addMainSql(" WHERE b1.DELETED_FLAG = 0");
        bindSql.addCondition(getUserId(), " AND b2.USER_ID = ?");
        bindSql.addCondition(getFarmId(), " AND b1.FARM_ID <> ?");
        List<FarmBindModel> bindList = setSql(farmBindMapper, bindSql);
        for (FarmBindModel model : bindList) {
            Map<String, Object> map = model.getData();
            map.put("farmName", CacheUtil.getName(String.valueOf(model.getFarmId()), NameEnum.COMPANY_NAME));
        }
        return bindList;
    }

    @Override
    public void deleteFarmBind(long ids[], List<String> isAsyncs) throws Exception {
        SqlCon updateSql = new SqlCon();
        updateSql.addMainSql("UPDATE hr_r_farm_bind SET DELETED_FLAG = 1");
        updateSql.addMainSql(" WHERE DELETED_FLAG = 0 AND (");
        for (int i = 0; i < ids.length; i++) {
            if (i == 0) {
                updateSql.addCondition(getUserId(), "(USER_ID = ?");
                updateSql.addCondition(ids[i], " AND BIND_USER_ID = ?)");
                updateSql.addCondition(ids[i], " || (USER_ID = ?");
                updateSql.addCondition(getUserId(), " AND BIND_USER_ID = ?)");
            } else {
                updateSql.addCondition(getUserId(), " || (USER_ID = ?");
                updateSql.addCondition(ids[i], " AND BIND_USER_ID = ?)");
                updateSql.addCondition(ids[i], " || (USER_ID = ?");
                updateSql.addCondition(getUserId(), " AND BIND_USER_ID = ?)");
            }
        }
        updateSql.addMainSql(")");
        setSql(farmBindMapper, updateSql);

        // 若是账号同步，则还需要删除人员，角色，权限，以及人员和角色的关系表
        Set<Long> userSet = null;
        Set<Long> employeeSet = null;
        Set<Long> erSet = null;
        Set<Long> roleSet = null;
        Set<Long> limitSet = null;
        for (int i = 0; i < isAsyncs.size(); i++) {
            if ("Y".equals(isAsyncs.get(i))) {
                userSet = new HashSet<>();
                employeeSet = new HashSet<>();
                erSet = new HashSet<>();
                roleSet = new HashSet<>();
                limitSet = new HashSet<>();
                SqlCon deleteSql = new SqlCon();
                deleteSql.addMainSql("SELECT u.ROW_ID userId,e.ROW_ID employeId,er.ROW_ID erId,r.ROW_ID roleId,l.ROW_ID limitId");
                deleteSql.addMainSql(" FROM hr_m_user u");
                deleteSql.addMainSql(" INNER JOIN hr_o_employee e");
                deleteSql.addMainSql(" ON u.EMPLOYEE_ID = e.ROW_ID AND e.DELETED_FLAG = 0 AND e.STATUS = 1 ");
                deleteSql.addMainSql(" INNER JOIN cd_r_employee_role er");
                deleteSql.addMainSql(" ON e.ROW_ID = er.EMPLOYEE_ID AND er.DELETED_FLAG = 0");
                deleteSql.addMainSql(" INNER JOIN cd_o_role r");
                deleteSql.addMainSql(" ON er.role_id = r.row_id AND r.deleted_flag = 0");
                deleteSql.addMainSql(" INNER JOIN cd_r_limit l");
                deleteSql.addMainSql(" ON r.row_id = l.role_id AND l.deleted_flag = 0");
                deleteSql.addMainSql(" WHERE u.DELETED_FLAG = 0");
                deleteSql.addCondition(ids[i], " AND u.ROW_ID = ?");
                Map<String, String> sqlMap = new HashMap<>();
                sqlMap.put("sql", deleteSql.getCondition());
                List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
                for (Map<String, String> map : list) {
                    userSet.add(Maps.getLong(map, "userId"));
                    employeeSet.add(Maps.getLong(map, "employeId"));
                    erSet.add(Maps.getLong(map, "erId"));
                    roleSet.add(Maps.getLong(map, "roleId"));
                    limitSet.add(Maps.getLong(map, "limitId"));
                }
                userMapper.deletes(setToArray(userSet));
                employeeMapper.deletes(setToArray(employeeSet));
                employeeRoleMapper.deletes(setToArray(erSet));
            }
        }
    }

    private long[] setToArray(Set<Long> idSet) {
        int i = 0;
        long[] ids = new long[idSet.size()];
        for (Long id : idSet) {
            ids[i++] = id;
        }
        return ids;
    }

    @Override
    public List<FarmBindModel> searchFarmBindToList(long mainId) throws Exception {
        SqlCon bindSql = new SqlCon();
        bindSql.addCondition(mainId, " AND ROW_ID = ?");
        return getList(farmBindMapper, bindSql);
    }

    @Override
    public void editSynchronizeFarm(List<String> farmIds, List<String> companyIds, List<String> companyCodes) throws Exception {
        String userName = getUserDetail().getUsername();
        if ("sysadmin".equals(userName)) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新农+管理员角色无法同步！");
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < farmIds.size(); i++) {
            if (i == 0) {
                buffer.append(farmIds.get(i));
            } else {
                buffer.append(",");
                buffer.append(farmIds.get(i));
            }
        }
        String strFarmIds = buffer.toString();
        SqlCon farmSql = new SqlCon();
        farmSql.addMainSql("SELECT e.farm_id farmId,u.user_name FROM hr_o_employee e");
        farmSql.addMainSql(" INNER JOIN hr_m_user u");
        farmSql.addMainSql(" ON e.ROW_ID = u.EMPLOYEE_ID and u.DELETED_FLAG = 0");
        farmSql.addMainSql(" WHERE e.DELETED_FLAG = 0");
        farmSql.addCondition(userName, " AND u.USER_NAME = ?");
        farmSql.addCondition(strFarmIds, " AND e.FARM_ID IN ?", false, true);

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", farmSql.getCondition());
        List<Map<String, String>> userList = paramMapper.getInfos(sqlMap);
        for (Map<String, String> map : userList) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【" + CacheUtil.getName(Maps.getString(map, "farmId"), NameEnum.COMPANY_NAME)
                    + "】的【" + userName + "】账号已存在，如需绑定，请手动绑定");
        }

        SqlCon employeeSql = new SqlCon();
        employeeSql.addCondition(getEmployeeId(), " AND ROW_ID = ?");
        EmployeeModel employeeModel = getModel(employeeMapper, employeeSql);

        SqlCon userSql = new SqlCon();
        userSql.addCondition(getUserId(), " AND ROW_ID = ?");
        UserModel userModel = getModel(userMapper, userSql);

        SqlCon employeeRoleSql = new SqlCon();
        employeeRoleSql.addCondition(employeeModel.getRowId(), " AND EMPLOYEE_ID = ?");
        List<EmployeeRoleModel> erlist = getList(employeeRoleMapper, employeeRoleSql);

        List<Long> roleIds = new ArrayList<>();
        for (EmployeeRoleModel employeeRoleModel : erlist) {
            roleIds.add(employeeRoleModel.getRoleId());
        }

        SqlCon roleSql = new SqlCon();
        roleSql.addCondition(StringUtil.listLongToString(roleIds), " AND ROW_ID IN ?", false, true);
        List<RoleModel> roleList = getList(roleMapper, roleSql);

        SqlCon limitSql = new SqlCon();
        limitSql.addCondition(StringUtil.listLongToString(roleIds), " AND ROLE_ID IN ?", false, true);
        List<LimitModel> limitList = getList(limitMapper, limitSql);

        Map<Long, List<LimitModel>> limitMap = new HashMap<>();
        for (LimitModel model : limitList) {
            Long roleId = model.getRoleId();
            if (limitMap.containsKey(roleId)) {
                limitMap.get(roleId).add(model);
            } else {
                List<LimitModel> lList = new ArrayList<LimitModel>();
                lList.add(model);
                limitMap.put(roleId, lList);
            }
        }

        for (int i = 0; i < farmIds.size(); i++) {
            // 创建员工
            employeeModel.setRowId(null);
            employeeModel.setFarmId(Long.valueOf(farmIds.get(i)));
            employeeModel.setCompanyId(Long.valueOf(companyIds.get(i)));
            if (i == 0) {
                employeeModel.setEmployeeName(employeeModel.getEmployeeName() + "-同步");
            } else {
                employeeModel.setEmployeeName(employeeModel.getEmployeeName());
            }
            employeeModel.setEmployeeType(RoleEmployeeConstans.EMPLOYEE_OF_ANSYS);
            employeeMapper.insert(employeeModel);

            // 创建用户
            userModel.setStatus(CommonConstants.STATUS);
            userModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            userModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            userModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            userModel.setRowId(null);
            userModel.setEmployeeId(employeeModel.getRowId());
            userModel.setIsInitPw("N");
            userModel.setCompanyCode(companyCodes.get(i));
            userMapper.insert(userModel);

            for (RoleModel roleModel : roleList) {

                RoleModel rmodel = new RoleModel();
                SqlCon hasRoleSql = new SqlCon();
                hasRoleSql.addCondition(farmIds.get(i), " AND FARM_ID = ?");
                hasRoleSql.addCondition(roleModel.getRoleName() + "-同步", " AND ROLE_NAME = ?");
                RoleModel hasRoleModel = getModel(roleMapper, hasRoleSql);
                if (hasRoleModel == null) {
                    // 没有同步角色 则创建角色
                    rmodel.setBusinessCode(roleModel.getBusinessCode());
                    rmodel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    rmodel.setIterceptUrl(roleModel.getIterceptUrl());
                    rmodel.setNotes(roleModel.getNotes());
                    rmodel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    rmodel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    rmodel.setRoleType(roleModel.getRoleType());
                    rmodel.setSortNbr(roleModel.getSortNbr());
                    rmodel.setStatus(CommonConstants.STATUS);
                    rmodel.setTemplateId(roleModel.getTemplateId());
                    rmodel.setFarmId(Long.valueOf(farmIds.get(i)));
                    rmodel.setCompanyId(Long.valueOf(companyIds.get(i)));
                    rmodel.setRoleName(roleModel.getRoleName() + "-同步");
                    rmodel.setRoleType(RoleEmployeeConstans.ROLE_OF_ANSYS);
                    roleMapper.insert(rmodel);
                } else {
                    // 否则不必创建
                    rmodel = hasRoleModel;
                    rmodel.setTemplateId(roleModel.getTemplateId());
                    roleMapper.update(rmodel);
                }

                // 创建用户和角色的关系表
                EmployeeRoleModel employeeRoleModel = new EmployeeRoleModel();
                employeeRoleModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                employeeRoleModel.setEmployeeId(employeeModel.getRowId());
                employeeRoleModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                employeeRoleModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                employeeRoleModel.setRoleId(rmodel.getRowId());
                employeeRoleModel.setStatus(CommonConstants.STATUS);
                employeeRoleMapper.insert(employeeRoleModel);

                // 跟新权限（删除原来的权限，并跟新新的权限）
                List<LimitModel> lList = limitMap.get(roleModel.getRowId());
                SqlCon deleteSql = new SqlCon();
                deleteSql.addMainSql("UPDATE cd_r_limit SET DELETED_FLAG = 1");
                deleteSql.addCondition(rmodel.getRowId(), " AND ROLE_ID = ?");
                setSql(limitMapper, deleteSql);
                for (LimitModel limitModel : lList) {
                    limitModel.setRowId(null);
                    limitModel.setRoleId(rmodel.getRowId());
                }
                limitMapper.inserts(lList);
            }
            // 绑定账号
            List<FarmBindModel> bindList = new ArrayList<>();
            FarmBindModel model = new FarmBindModel();
            model.setBindUserId(userModel.getRowId());
            // 注意：farmId为 绑定帐号的farmId
            model.setFarmId(Long.valueOf(farmIds.get(i)));
            model.setCompanyId(Long.valueOf(companyIds.get(i)));
            model.setCompanyCode(companyCodes.get(i));
            model.setUserName(userName);
            model.setDeletedFlag(CommonConstants.DELETED_FLAG);
            model.setNotes(userModel.getNotes());
            model.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            model.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            model.setPassword(userModel.getPassword());
            model.setStatus(CommonConstants.STATUS);
            model.setUserId(getUserId());
            model.setIsAsync("Y");
            bindList.add(model);

            model = new FarmBindModel();
            model.setBindUserId(getUserId());
            model.setFarmId(getFarmId());
            model.setCompanyId(getCompanyId());
            model.setCompanyCode(getUserDetail().getCompanyCode());
            model.setUserName(userName);
            model.setDeletedFlag(CommonConstants.DELETED_FLAG);
            model.setNotes(userModel.getNotes());
            model.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
            model.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
            model.setPassword(userModel.getPassword());
            model.setStatus(CommonConstants.STATUS);
            model.setUserId(userModel.getRowId());
            model.setIsAsync("Y");
            bindList.add(model);
            farmBindMapper.inserts(bindList);
        }
    }

    @Override
    public BasePageInfo searchHousePigDetailedToPage(Long farmId, String houseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("farmId", farmId);
        map.put("houseId", houseId);
        setToPage();
        List<Map<String, Object>> searchHousePigDetailedToPage = houseMapper.searchHousePigDetailedToPage(map);

        return getPageInfo(searchHousePigDetailedToPage);
    }

    @Override
    public List<Map<String, Object>> searchHouseDetailedToPage(Long farmId, String houseId) {
        Map<String, Object> map = new HashMap<>();
        map.put("farmId", farmId);
        map.put("houseId", houseId);
        List<Map<String, Object>> searchHouseDetailedToPage = houseMapper.searchHouseDetailedToPage(map);
        return searchHouseDetailedToPage;
    }

    @Override
    public BasePageInfo searchSaleItemToPage() {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT c.row_id rowId,c.deleted_flag deletedFlag,c.company_name customerName,c.notes notes FROM hr_m_company c");
        sqlCon.addMainSql(" INNER JOIN hr_r_srm s");
        sqlCon.addMainSql(" ON c.ROW_ID = s.CUSSUP_ID AND s.DELETED_FLAG = 0");
        sqlCon.addMainSql(" WHERE c.DELETED_FLAG = 0");
        sqlCon.addCondition(getFarmId(), " AND s.FARM_ID = ?");
        sqlCon.addCondition(CompanyConstants.SALE_ACCOUNT_SURE, " AND s.IS_SALE_ACCOUNT = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);
        return getPageInfo(list);
    }

    @Override
    public List<Map<String, String>> searchSaleItemDetailToList(Long customerId) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID rowId,ITEM_TYPE itemType,ITEM_STAGE itemStage,ITEM_NAME itemName,DELETED_FLAG deletedFlag,");
        sqlCon.addMainSql(" REWARD_PUNISH_STANDARD rewardPunishStandard,NOTES notes FROM cd_o_sale_account_item");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = 0");
        sqlCon.addCondition(customerId, " AND CUSTOMER_ID = ?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(sqlMap);

        for (Map<String, String> map : list) {
            map.put("itemTypeName", CacheUtil.getName(Maps.getString(map, "itemType"), NameEnum.CODE_NAME, CodeEnum.ITEM_TYPE));
            map.put("itemStageName", CacheUtil.getName(Maps.getString(map, "itemStage"), NameEnum.CODE_NAME, CodeEnum.ITEM_STAGE));
        }
        return list;
    }

    @Override
    public void editSaleItem(Long customerId, String detailList) throws Exception {
        // 验证重复
        // long codeIsExist = ParamUtil.isExist("cd_l_code_list", codeList.getRowId(), codeList.getTypeCode(), "TYPE_CODE");
        List<SaleAccountItemModel> list = getModelList(detailList, SaleAccountItemModel.class);
        List<SaleAccountItemModel> addList = new ArrayList<SaleAccountItemModel>();
        List<SaleAccountItemModel> updateList = new ArrayList<SaleAccountItemModel>();
        long[] ids = new long[list.size()];
        int sumDelete = 0;

        for (SaleAccountItemModel model : list) {
            if ("0".equals(model.getDeletedFlag())) {
                long codeIsExist = 0;
                if (model.getRowId() == null || model.getRowId() == 0) {
                    // codeIsExist = ParamUtil.isExist("cd_o_sale_account_item", model.getRowId(), model.getItemName(), "ITEM_NAME");
                    model.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    model.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    model.setCustomerId(customerId);
                    addList.add(model);
                } else {
                    // codeIsExist = ParamUtil.isExist("cd_o_sale_account_item", model.getRowId(), model.getItemName(), "ITEM_NAME");
                    updateList.add(model);
                }
                // if (codeIsExist > 0) {
                // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "项目名称【" + model.getItemName() + "】存在重复！请重新输入");
                // }
            } else if ("1".equals(model.getDeletedFlag())) {
                ids[sumDelete] = model.getRowId();
                sumDelete++;
            }
        }

        // 新增
        if (addList.size() > 0) {
            saleAccountItemMapper.inserts(addList);
        }

        // 删除
        if (ids[0] != 0) {
            saleAccountItemMapper.deletes(ids);
        }

        // 修改
        if (updateList.size() > 0) {
            saleAccountItemMapper.updates(updateList);
        }
    }

    @Override
    public void editDisablesPigHouse(long[] ids, String stopDate) throws Exception {
        String status = "0";// 停用
        String stopDate1 = stopDate;
        String rowIds = StringUtil.arrayToString(ids);
        SqlCon sql = new SqlCon();
        sql.addMainSql("UPDATE pp_o_house SET ");
        sql.addCondition(status, " STATUS = ? ");
        sql.addCondition(stopDate1, " ,STOP_DATE = ? ");
        sql.addCondition(rowIds, "WHERE row_id IN ?", false, true);
        setSql(houseMapper, sql);
    }

    @Override
    public void editEnablesPigHouse(long[] ids) throws Exception {
        Map<String, String> map = new HashMap<>();
        String status = "1";// 启用
        String rowIds = StringUtil.arrayToString(ids);

        int index = 0;
        for (long id : ids) {
            index++;
            SqlCon condition = new SqlCon();
            condition.addMainSql(" SELECT COUNT(t2.ROW_ID) QTY ");
            condition.addMainSql(" FROM PP_O_HOUSE T1 ");
            condition.addMainSql(
                    " LEFT JOIN PP_O_HOUSE T2 ON T1.FARM_ID = T2.FARM_ID AND T1.HOUSE_BUILDING_NUMBER = T2.HOUSE_BUILDING_NUMBER AND T1.HOUSE_UNIT_NUMBER = T2.HOUSE_UNIT_NUMBER  AND T1.ROW_ID <> T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1'  ");
            condition.addMainSql(" WHERE T1.DELETED_FLAG = '0' ");
            condition.addCondition(getFarmId(), " AND T1.FARM_ID = ? ");
            condition.addCondition(id, " AND T1.ROW_ID = ? ");
            map.put("sql", condition.getCondition());
            List<Map<String, Object>> list = paramMapper.getObjectInfos(map);

            if (list != null) {
                if (Maps.getLong(list.get(0), "QTY") > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "第[" + index + "]行, [单元号-栋号]存在重复数据!");
                }
            }

            SqlCon sql = new SqlCon();
            sql.addMainSql("UPDATE pp_o_house SET ");
            sql.addCondition(status, " STATUS = ? ");
            sql.addCondition(rowIds, "WHERE row_id IN ?", false, true);
            setSql(houseMapper, sql);
        }
    }

    @Override
    public List<Map<String, Object>> searchHistoryPaper(Map<String, Object> inputMap) throws Exception {
        // String result = null;
        String cussupId = Maps.getString(inputMap, "cussupId");

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT ROW_ID AS papersRowId, PAPER_TYPE paperType, PAPER_CODE paperCode, EXPIRY_TYPE expiryType, EXPIRY_DATE expiryDate");
        sqlCon.addMainSql(" FROM HR_L_PAPERS WHERE DELETED_FLAG = '0' ");
        sqlCon.addCondition(cussupId, " AND CUSSUP_ID = ?");

        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        for (Map<String, Object> listMap : list) {
            String paperTypeName = CacheUtil.getName(Maps.getString(listMap, "paperType"), xn.core.data.enums.NameEnum.CODE_NAME,
                    CodeEnum.LICENSE_TYPE);
            listMap.put("paperTypeName", paperTypeName);
            String expiryTypeName = CacheUtil.getName(Maps.getString(listMap, "expiryType"), xn.core.data.enums.NameEnum.CODE_NAME,
                    CodeEnum.EXPIRY_TYPE);
            listMap.put("expiryTypeName", expiryTypeName);

            SqlCon fileSqlCon = new SqlCon();
            fileSqlCon.addCondition("HR_L_PAPERS", " AND MAIN_TABLE = ?");
            fileSqlCon.addCondition(Maps.getLong(listMap, "papersRowId"), " AND MAIN_ID = ? ORDER BY SORT_NBR");

            List<HrLfileModel> hrLfileModelList = getList(hrLfileMapper, fileSqlCon);
            listMap.put("file", "上传" + hrLfileModelList.size() + "个附件");
            List<String> picSrcList = new ArrayList<String>();
            listMap.put("picSrc", picSrcList);
            List<String> fileNameList = new ArrayList<String>();
            listMap.put("fileNameList", fileNameList);
            for (HrLfileModel hrLfileModel : hrLfileModelList) {
                picSrcList.add(hrLfileModel.getRelativePath() + hrLfileModel.getSysFileName());
                fileNameList.add(hrLfileModel.getSysFileName());
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchHistoryAccount(Map<String, Object> inputMap) throws Exception {
        Long cussupId = Maps.getLong(inputMap, "cussupId");
        String cussupType = Maps.getString(inputMap, "cussupType");

        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql("SELECT ROW_ID rowId,BANK_NAME bankName,BANK_ACCOUNT bankAccount,NOTES notes FROM HR_L_ACCOUNT WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE DELETED_FLAG = '0' AND ROW_ID = HR_L_ACCOUNT.SRM_ID");
        sqlCon.addMainSql(" AND CREATE_TYPE IN ('1','3')");
        sqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");
        sqlCon.addCondition(cussupId, " AND CUSSUP_ID = ?)");
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    @Override
    public List<Map<String, Object>> searchHistoryLink(Map<String, Object> inputMap) throws Exception {
        Long cussupId = Maps.getLong(inputMap, "cussupId");
        String cussupType = Maps.getString(inputMap, "cussupType");

        SqlCon sqlCon = new SqlCon();
        Map<String, String> map = new HashMap<>();
        sqlCon.addMainSql("SELECT ROW_ID rowId,LINKMAN_NAME linkmanName,MOBILE_NUMBER mobileNumber,TEL_NUMBER telNumber,NOTES notes");
        sqlCon.addMainSql(" FROM HR_L_LINK WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM HR_R_SRM WHERE DELETED_FLAG = '0' AND ROW_ID = HR_L_LINK.SRM_ID");
        sqlCon.addMainSql(" AND CREATE_TYPE IN ('1','3')");
        sqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");
        sqlCon.addCondition(cussupId, " AND CUSSUP_ID = ?)");
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    @Override
    public BasePageInfo searchChangeHistory(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT N.*,N0.EMPLOYEE_NAME changeName FROM ");
        sqlCon.addMainSql(
                " ( SELECT N1.CODE_NAME paperName,t1.VERSION version,T1.ROW_ID papersRowId, T1.PAPER_CODE paperCode, T2.cussup_id cussupId");
        sqlCon.addMainSql(" , T2.CHANGE_WORKER changeWorker, T2.CHANGE_DATE changeDate, T2.CHANGE_CONTENT changeContent, T2.notes");
        sqlCon.addMainSql(" FROM HR_R_SRM T INNER JOIN HR_L_PAPERS T1 ON (T.ROW_ID = T1.SRM_ID)");
        sqlCon.addMainSql("INNER JOIN HR_M_COMPANY_HIS T2 ON (T.CUSSUP_ID = T2.CUSSUP_ID AND T1.VERSION = T2.VERSION");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "cussupId"), " and  t.cussup_id = ? )");
        sqlCon.addMainSql("INNER JOIN cd_l_code_list N1 ON N1.TYPE_CODE = 'LICENSE_TYPE' AND N1.CODE_VALUE = t1.PAPER_TYPE ");
        sqlCon.addMainSql("  ) N LEFT JOIN hr_o_employee N0 ON (N.changeWorker = N0.ROW_ID AND N0.DELETED_FLAG ='0')");
        sqlCon.addMainSql(" ORDER BY N.papersRowId");

        Map<String, String> map = new HashMap<>();
        map.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> returnList = paramMapper.getObjectInfos(map);
        for (Map<String, Object> listMap : returnList) {
            SqlCon fileSqlCon = new SqlCon();
            fileSqlCon.addCondition("HR_L_PAPERS", " AND MAIN_TABLE = ?");
            fileSqlCon.addCondition(Maps.getLong(listMap, "papersRowId"), " AND MAIN_ID = ? ORDER BY SORT_NBR");

            List<HrLfileModel> hrLfileModelList = getList(hrLfileMapper, fileSqlCon);
            listMap.put("file", "上传" + hrLfileModelList.size() + "个附件");
            List<String> picSrcList = new ArrayList<String>();
            listMap.put("picSrc", picSrcList);
            List<String> fileNameList = new ArrayList<String>();
            listMap.put("fileNameList", fileNameList);
            for (HrLfileModel hrLfileModel : hrLfileModelList) {
                picSrcList.add(hrLfileModel.getRelativePath() + hrLfileModel.getSysFileName());
                fileNameList.add(hrLfileModel.getSysFileName());
            }
        }
        getPageInfo().setTotal(returnList.size());
        return getPageInfo(returnList);
    }

    @Override
    public void editCustomerAndSupplierToFarms(Map<String, Object> inputMap) throws Exception {

        List<HashMap> list = getJsonList(Maps.getString(inputMap, "json"), HashMap.class);

        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

        Date currentDate = new Date();

        for (Map<String, Object> map : list) {
            // 需要同步的猪场
            List<Map<String, Object>> farmList = Maps.getList(map, "requestUpdateFarm");
            Long rowId = Maps.getLong(map, "srmId");
            Long cussupId = Maps.getLong(map, "cussupId");
            String cussupType = Maps.getString(map, "cussupType");

            SrmModel srmModel = srmMapper.searchById(rowId);
            Long version = srmModel.getVersion();
            String isSaleAccount = srmModel.getIsSaleAccount();

            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            String mtcTransType = null;
            if (isToHana) {
                // 默认为新增
                mtcTransType = HanaConstants.MTC_ITFC_TRANS_TYPE_A;
            }
            // END HANA

            for (Map<String, Object> farmMap : farmList) {
                Long farmId = Maps.getLong(farmMap, "farmId");
                Long parentId = Maps.getLong(farmMap, "parentId");
                // 需同步供应商是否已有
                SqlCon sqlSrm = new SqlCon();
                sqlSrm.addCondition(cussupId, " AND CUSSUP_ID = ?");
                sqlSrm.addCondition(cussupType, " AND CUSSUP_TYPE= ? ");
                sqlSrm.addCondition(farmId, " AND  FARM_ID= ? ");
                List<SrmModel> srmModelList = getList(srmMapper, sqlSrm);

                if (srmModelList == null || srmModelList.isEmpty()) {
                    SrmModel srmModelEdit = new SrmModel();

                    srmModelEdit.setStatus(PigConstants.STATUS_NOR);
                    srmModelEdit.setDeletedFlag(PigConstants.DELETED_FLAG_NOT);
                    srmModelEdit.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                    srmModelEdit.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                    srmModelEdit.setCussupType(cussupType);
                    srmModelEdit.setCompanyId(parentId);
                    srmModelEdit.setFarmId(farmId);
                    srmModelEdit.setCreateType("2");
                    srmModelEdit.setIsSaleAccount(isSaleAccount);
                    srmModelEdit.setVersion(version);
                    srmModelEdit.setCussupId(cussupId);
                    srmMapper.insert(srmModelEdit);
                } else {
                    // START HANA
                    if (isToHana) {
                        // 已经存在过同步，改为更新
                        mtcTransType = HanaConstants.MTC_ITFC_TRANS_TYPE_U;
                    }
                    // END HANA
                }
            }

            // START HANA
            if (isToHana) {
                CompanyModel company = companyMapper.searchById(cussupId);

                HanaClientOrProvider hanaClientOrProvider = new HanaClientOrProvider();
                // 业务伙伴类别
                hanaClientOrProvider.setMTC_CardType(cussupType);
                // 客户/供应商编号
                hanaClientOrProvider.setMTC_CardCode(company.getCompanyCode());
                // 客户/供应商名称
                hanaClientOrProvider.setMTC_CardName(company.getCompanyName());
                // 客户/供应商分类
                if ("C".equalsIgnoreCase(cussupType)) {
                    hanaClientOrProvider.setMTC_GroupCode("100");
                } else if ("S".equalsIgnoreCase(cussupType)) {
                    hanaClientOrProvider.setMTC_GroupCode("101");
                }
                // 客户/供应商简称
                hanaClientOrProvider.setMTC_CardFName(company.getSortName());
                // 客户/供应商地址
                hanaClientOrProvider.setMTC_AliasName(company.getCompanyAddress());
                // 客户/供应商-归属
                SqlCon srmSqlCon = new SqlCon();
                srmSqlCon.addCondition(company.getRowId(), " AND CUSSUP_ID = ?");
                srmSqlCon.addCondition(cussupType, " AND CUSSUP_TYPE = ?");
                srmSqlCon.addCondition(CompanyConstants.CREATE_TYPE_BUILT, " AND CREATE_TYPE = ?");
                List<SrmModel> srmModelList = getList(srmMapper, srmSqlCon);

                // 获取去重的mtcBranchId
                Set<String> mtcBranchIdSet = new HashSet<String>();
                List<HanaClientOrProvider.MTC_Branch> mtcBranchDetailList = new ArrayList<HanaClientOrProvider.MTC_Branch>();
                for (SrmModel srmModel2 : srmModelList) {
                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(srmModel2.getFarmId());
                    String mtcBranchId = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                    if (!mtcBranchIdSet.contains(mtcBranchId)) {
                        mtcBranchIdSet.add(mtcBranchId);
                        // 除去太仓场
                        if (!"C1028".equals(mtcBranchId)) {
                            HanaClientOrProvider.MTC_Branch mtcBranch = new HanaClientOrProvider.MTC_Branch();
                            mtcBranch.setMTC_Branch(mtcBranchId);
                            mtcBranchDetailList.add(mtcBranch);
                        }
                    }
                }
                hanaClientOrProvider.setDetailList(mtcBranchDetailList);

                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                // mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 业务代码:客户/供应商
                if (cussupType.equalsIgnoreCase("C")) {
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1070);
                } else if (cussupType.equalsIgnoreCase("S")) {
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1080);
                }
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaClientOrProvider.getMTC_CardCode());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(mtcTransType);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(getFarmId()));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaClientOrProvider);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                mtcITFCList.add(mtcITFC);
            }
            // END HANA
        }

        if (!mtcITFCList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
    }

    @Override
    public List<Map<String, Object>> searchGroupCompanyByFarmIdToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(getCompanyMark() + ",%", " AND COMPANY_MARK LIKE ?");
        List<CompanyModel> companyModelList = getList(companyMapper, sqlCon);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (CompanyModel companyModel : companyModelList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("farmId", companyModel.getRowId());
            map.put("parentId", companyModel.getSupCompanyId());
            map.put("farmName", companyModel.getCompanyName());
            map.put("farmSortName", companyModel.getSortName());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, String>> searchHrCompanyClienteleBillToList(String cussupType) throws Exception {

        SqlCon sql = new SqlCon();
        sql.addMainSql(" SELECT t1.row_id clienteleId,t1.COMPANY_NAME clienteleName FROM HR_R_SRM t  ");
        sql.addMainSql(" INNER JOIN HR_M_COMPANY t1 ON t.CUSSUP_ID = t1.ROW_ID AND t1.DELETED_FLAG='0'");
        sql.addMainSql(" WHERE t.DELETED_FLAG='0' ");
        sql.addConditionWithNull(getFarmId(), " AND t.FARM_ID = ?");
        sql.addConditionWithNull(cussupType, " AND t.CUSSUP_TYPE= ?");

        Map<String, String> map = new HashMap<>();
        map.put("sql", sql.getCondition());
        List<Map<String, String>> list = paramMapper.getInfos(map);
        return list;
    }
}
