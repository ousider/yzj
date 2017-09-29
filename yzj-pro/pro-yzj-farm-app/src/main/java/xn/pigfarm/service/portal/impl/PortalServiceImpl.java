package xn.pigfarm.service.portal.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.data.Maps;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.mapper.backend.WeekMapper;
import xn.pigfarm.mapper.portal.ParityDistributionMapper;
import xn.pigfarm.mapper.portal.PortalMapper;
import xn.pigfarm.model.portal.ParityDistributionModel;
import xn.pigfarm.service.portal.IPortalService;
import xn.pigfarm.util.constants.PigConstants;

@Service("PortalService")
public class PortalServiceImpl extends BaseServiceImpl implements IPortalService {

    @Autowired
    private PortalMapper portalMapper;

    @Autowired
    private ParityDistributionMapper parityMapper;

    @Autowired
    private WeekMapper weekMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Override
    public List<ParityDistributionModel> searchParityToList() {

        return parityMapper.searchToList();
    }

    @Override
    public List<Object> searchAmountSize(String dateType, Long farmId) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();

        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Object> searchToList = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            Map<String, String> map = new HashMap<>();
            if ("year".equals(dateType)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, (int) (-52.3 * i));
                date = sdf.format(c.getTime());
            } else if ("month".equals(dateType)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, (int) (-4.3 * i));
                date = sdf.format(c.getTime());
            } else if ("week".equals(dateType)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, -1 * i);
                date = sdf.format(c.getTime());
            }

            map.put("date", date);
            map.put("farmId", String.valueOf(farmId));
            list.add(map);
        }
        List<Object> searchList = portalMapper.searchListByDate(list);
        return searchList;
    }

    @Override
    public List<Object> searchProductionByDate(String dateType, Long farmId) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdate = "";
        String enddate = "";

        // if ("year".equals(dateType)) {
        // for (int i = 11; i >= 0; i--) {
        // Map<String, String> map = new HashMap<>();
        // Calendar c = Calendar.getInstance();
        // c.add(Calendar.DAY_OF_YEAR, +1);
        // c.add(Calendar.MONTH, -1 * i);
        // if (i != 0) {
        // c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        // }
        // enddate = sdf.format(c.getTime());
        //
        // c.set(Calendar.DAY_OF_MONTH, 1);
        // startdate = sdf.format(c.getTime());
        //
        // int month = c.get(Calendar.MONTH) + 1;
        //
        // map.put("index", String.valueOf(month));
        // map.put("startdate", startdate);
        // map.put("enddate", enddate);
        // map.put("farmId", String.valueOf(farmId));
        // list.add(map);
        // }
        // }
        // if ("week".equals(dateType)) {
        // for (int i = 16; i >= 0; i--) {
        // Map<String, String> mapweek = new HashMap<>();
        // Calendar c = Calendar.getInstance();
        // c.add(Calendar.DAY_OF_YEAR, +1);
        // c.add(Calendar.WEEK_OF_YEAR, -1 * i);
        // c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // startdate = sdf.format(c.getTime());
        //
        // c.add(Calendar.WEEK_OF_YEAR, +1);
        // int weekIndex = c.get(Calendar.WEEK_OF_YEAR);
        // c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // enddate = sdf.format(c.getTime());
        // if (i == 0) {
        // Calendar calendar = Calendar.getInstance();
        // enddate = sdf.format(calendar.getTime());
        // }
        //
        // mapweek.put("index", String.valueOf(weekIndex));
        // mapweek.put("startdate", startdate);
        // mapweek.put("enddate", enddate);
        // mapweek.put("farmId", String.valueOf(farmId));
        // list.add(mapweek);
        // }
        // }

        if ("year".equals(dateType)) {
            for (int i = 11; i >= 0; i--) {
                Map<String, Object> map = new HashMap<>();
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_YEAR, +1);
                c.add(Calendar.MONTH, -1 * i);
                if (i != 0) {
                    c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
                }
                enddate = sdf.format(c.getTime());

                c.set(Calendar.DAY_OF_MONTH, 1);
                startdate = sdf.format(c.getTime());

                int month = c.get(Calendar.MONTH) + 1;

                map.put("index", String.valueOf(month));
                map.put("startdate", startdate);
                map.put("enddate", enddate);
                map.put("farmId", String.valueOf(farmId));
                list.add(map);
            }
        } else {
            Map<String, Long> mapWeekParam = new HashMap<>();
            mapWeekParam.put("farmId", farmId);
            mapWeekParam.put("weekHeight", 17L);
            List<Map<String, Object>> searchWeekInYear = portalMapper.searchWeekInYear(mapWeekParam);
            for (int i = 0; i <= 16; i++) {
                Map<String, Object> map = new HashMap<>();
                Integer WEEK = (Integer) searchWeekInYear.get(i).get("WEEK");
                String startdate1 = String.valueOf(searchWeekInYear.get(i).get("START_DATE"));
                String enddate1 = String.valueOf(searchWeekInYear.get(i).get("END_DATE"));
                map.put("index", WEEK);
                map.put("startdate", startdate1);
                map.put("enddate", enddate1);
                map.put("farmId", String.valueOf(farmId));
                list.add(map);
            }
        }

        List<Object> searchProductionByDate = portalMapper.searchProductionByDate(list);
        return searchProductionByDate;
    }

    @Override
    public List<Object> searchDieByDate(String dateType, Long farmId) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startdate = "";
        String enddate = "";
        for (int i = 6; i >= 0; i--) {
            Map<String, String> map = new HashMap<>();
            if ("year".equals(dateType)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, (int) (-52.3 * i));
                enddate = sdf.format(c.getTime());
                Calendar ca = Calendar.getInstance();
                ca.add(Calendar.DAY_OF_MONTH, (int) (-52.3 * (i + 1)));
                startdate = sdf.format(ca.getTime());
            } else if ("month".equals(dateType)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, (int) (-4.3 * i));
                enddate = sdf.format(c.getTime());
                Calendar ca = Calendar.getInstance();
                ca.add(Calendar.DAY_OF_MONTH, (int) (-4.3 * (i + 1)));
                startdate = sdf.format(ca.getTime());
            } else if ("week".equals(dateType)) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, -1 * i);
                enddate = sdf.format(c.getTime());
                Calendar ca = Calendar.getInstance();
                ca.add(Calendar.DAY_OF_MONTH, -1 * (i + 1));
                startdate = sdf.format(ca.getTime());
            }

            map.put("startdate", startdate);
            map.put("enddate", enddate);
            map.put("farmId", String.valueOf(farmId));

            list.add(map);
        }

        List<Object> searchDieByDate = portalMapper.searchDieByDate(list);
        return searchDieByDate;
    }

    /**
     * 胎次分布
     */
    @Override
    public List<Object> searchParityByDate(Long farmId, Long farmId2, String pigNum, String isGroup, String companyMark) throws Exception {
        Map<String, String> map = new HashMap<>();
        List<Object> searchParityByDate = new ArrayList<>();
        if (isGroup.equals("false")) {
            map = new HashMap<>();
            map.put("farmId2", String.valueOf(farmId2));
            map.put("farmIds", String.valueOf(farmId));
            map.put("pigNum", pigNum);

            searchParityByDate = portalMapper.searchParityGroup(map);
        } else {
            map = new HashMap<>();
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT GROUP_CONCAT(T.ROW_ID +'') farmIds ");
            sqlCon.addMainSql(" FROM HR_M_COMPANY T ");
            sqlCon.addMainSql(" WHERE T.DELETED_FLAG = '0' ");
            sqlCon.addMainSql(" AND (T.COMPANY_MARK LIKE '" + companyMark + "%' ");
            sqlCon.addCondition(companyMark, " OR T.COMPANY_MARK = ? ) ");
            map.put("sql", sqlCon.getCondition());
            List<Map<String, Object>> farmIdMaps = paramMapper.getObjectInfos(map);
            String farmIds = Maps.getString(farmIdMaps.get(0), "farmIds");

            map = new HashMap<>();
            map.put("farmId2", String.valueOf(farmId2));
            map.put("farmIds", String.valueOf(farmIds));
            map.put("pigNum", pigNum);

            searchParityByDate = portalMapper.searchParityGroup(map);
        }
        return searchParityByDate;

    }

    /* 母猪群蓝图 */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> searchSwineryClassNumByDate(Long farmId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        DecimalFormat df = new DecimalFormat("0.00");
        List<Map<String, Object>> resultlistlistmap = new ArrayList<>();
        // 获取周次
        List<Map<String, String>> weekList = portalMapper.searchWeek(farmId);
        if (weekList.isEmpty()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("startDate", TimeUtil.getSysDate());
            weekList.add(map);
        }
        Date weekSatrtDate = Maps.getDate(weekList.get(weekList.size() - 1), "startDate");
        // 获取猪群猪只状态历史
        List<Map<String, String>> swineryClassList = portalMapper.searchSwineryClass(farmId);
        // 判断猪群猪只状态历史是否为空
        if (swineryClassList.isEmpty()) {
            return resultMap;
        }
        // 获取猪群名称数组
        List<String> swineryNameList = new ArrayList<>();
        for (Iterator<Map<String, String>> it = swineryClassList.iterator(); it.hasNext();) {
            Map<String, String> swineryClass = (Map<String, String>) it.next();
            if (!swineryNameList.contains(swineryClass.get("SWINERY_NAME"))) {
                swineryNameList.add(swineryClass.get("SWINERY_NAME"));
            }
        }
        // 按猪群名称分类
        List<Map<String, Object>> swineryGroupList = new ArrayList<>();
        for (String swineryName : swineryNameList) {
            Map<String, Object> swineryGroup = new HashMap<String, Object>();
            List<Map<String, String>> newSwineyList = new ArrayList<>();
            swineryGroup.put("swineryName", swineryName);
            for (Iterator<Map<String, String>> it = swineryClassList.iterator(); it.hasNext();) {
                Map<String, String> swineryClass = (Map<String, String>) it.next();
                if (swineryName.equals(swineryClass.get("SWINERY_NAME"))) {
                    swineryGroup.put("swineryId", swineryClass.get("SWINERY_ID"));
                    newSwineyList.add(swineryClass);
                }
            }
            swineryGroup.put("swineryList", newSwineyList);
            swineryGroupList.add(swineryGroup);
        }
        // 按不同状态分类
        float totalFQ = 0, totalKH = 0, totalLC = 0, totalBreed = 0;
        for (Iterator<Map<String, Object>> it = swineryGroupList.iterator(); it.hasNext();) {
            Map<String, Object> swineryClass = (Map<String, Object>) it.next();
            List<Map<String, String>> newSwineyList = (List<Map<String, String>>) swineryClass.get("swineryList");
            Map<String, Object> newSwineryClass = new HashMap<String, Object>();
            newSwineryClass.put("swineyName", swineryClass.get("swineryName"));
            newSwineryClass.put("swineryId", swineryClass.get("swineryId"));
            // 获取21周之前的猪只操作数量
            int breedNum = 0, preCheckNunm = 0, preDeliveryNum = 0, preWeanNum = 0;
            float breedNum_d = 0, deliveryNum_d = 0;
            for (Iterator<Map<String, String>> ns = newSwineyList.iterator(); ns.hasNext();) {
                Map<String, String> newSwiney = (Map<String, String>) ns.next();
                Date toWorkDate = Maps.getDate(newSwiney, "TOWORK_DATE");
                if (Maps.getString(newSwiney, "PIG_CLASS").equals("9")) {
                    breedNum_d += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                    totalBreed += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                }
                if (Maps.getString(newSwiney, "PIG_CLASS").equals("10")) {
                    deliveryNum_d += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                }
                if (Maps.getString(newSwiney, "PIG_CLASS").equals("6")) {
                    totalFQ += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                }
                if (Maps.getString(newSwiney, "PIG_CLASS").equals("8")) {
                    totalKH += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                }
                if (Maps.getString(newSwiney, "PIG_CLASS").equals("7")) {
                    totalLC += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                }
                if (TimeUtil.daysBetween(toWorkDate, weekSatrtDate) < 0) {
                    if (Maps.getString(newSwiney, "PIG_CLASS").equals("6") || Maps.getString(newSwiney, "PIG_CLASS").equals("7") || Maps.getString(
                            newSwiney, "PIG_CLASS").equals("8")) {
                        preCheckNunm += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                    }
                    if (Maps.getString(newSwiney, "PIG_CLASS").equals("9")) {
                        breedNum += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                    }
                    if (Maps.getString(newSwiney, "PIG_CLASS").equals("10")) {
                        preDeliveryNum += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                    }
                    if (Maps.getString(newSwiney, "PIG_CLASS").equals("11")) {
                        preWeanNum += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                    }
                }
            }
            // 母猪群蓝图配种头数大于应配头数时显示异常修改，修改人：张晓芸 修改日期：2016-10-09 --begin
            /* if (deliveryNum_d == 0) {
             * newSwineryClass.put("breedNum", 0);
             * newSwineryClass.put("deliveryRate", "100%");
             * } else {
             * newSwineryClass.put("breedNum", breedNum_d);
             * newSwineryClass.put("deliveryRate", df.format(deliveryNum_d / breedNum_d * 100) + "%");
             * } */
            if (deliveryNum_d == 0) {
                newSwineryClass.put("deliveryRate", "100%");
            } else {
                newSwineryClass.put("deliveryRate", df.format(deliveryNum_d / breedNum_d * 100) + "%");
            }
            newSwineryClass.put("breedNum", breedNum_d);
            // 母猪群蓝图配种头数大于应配头数时显示异常修改，修改人：张晓芸 修改日期：2016-10-09 --end
            // 周次数据赋值
            List<Map<String, Object>> weekSwineryList = new ArrayList<>();
            // 周次数据初始化
            if (breedNum == 0) {
                for (int i = weekList.size() - 1; i >= 0; i--) {
                    Map<String, String> weekMap = weekList.get(i);
                    Map<String, Object> weekClass = new HashMap<String, Object>();
                    weekClass.put("week", weekMap.get("week"));
                    weekClass.put("breedNum", 0);
                    weekClass.put("pregnancyNum", 0);
                    weekClass.put("deliveryNum", 0);
                    weekClass.put("weanNum", 0);
                    weekSwineryList.add(weekClass);
                }
            } else {
                for (int i = weekList.size() - 1; i >= 0; i--) {
                    Map<String, String> weekMap = weekList.get(i);
                    Map<String, Object> weekClass = new HashMap<String, Object>();
                    weekClass.put("week", weekMap.get("week"));
                    weekClass.put("breedNum", 0);
                    weekClass.put("pregnancyNum", breedNum - preCheckNunm - (preWeanNum != 0 ? preDeliveryNum - preWeanNum : preDeliveryNum)
                            - preWeanNum);
                    weekClass.put("deliveryNum", preDeliveryNum - preWeanNum);
                    weekClass.put("weanNum", preWeanNum);
                    weekSwineryList.add(weekClass);
                }
            }
            // 循环一条猪群数据
            int tempBreedNum = 0;
            for (Iterator<Map<String, String>> ns = newSwineyList.iterator(); ns.hasNext();) {
                Map<String, String> newSwiney = (Map<String, String>) ns.next();
                Date toWorkDate = Maps.getDate(newSwiney, "TOWORK_DATE");
                for (int i = weekList.size() - 1; i >= 0; i--) {
                    Map<String, String> weekMap = weekList.get(i);
                    // 周次开始时间，周次结束时间
                    Date startDate = Maps.getDate(weekMap, "startDate");
                    Date endDate = Maps.getDate(weekMap, "endDate");
                    // 转状态日期在周次开始时间和结束时间中间
                    if (TimeUtil.daysBetween(toWorkDate, startDate) >= 0 && TimeUtil.daysBetween(toWorkDate, endDate) <= 0) {
                        if (Maps.getString(newSwiney, "PIG_CLASS").equals("9")) {
                            // 配种数量
                            tempBreedNum += Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                            weekSwineryList.get(weekList.size() - 1 - i).put("breedNum", tempBreedNum);
                            if (i != 0) {
                                int tempNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "pregnancyNum"))
                                        + tempBreedNum;
                                for (int n = weekList.size() - 1 - i; n < weekList.size() - 1; n++) {
                                    weekSwineryList.get(n + 1).put("pregnancyNum", tempNum);
                                }
                            }
                        }
                        if (Maps.getString(newSwiney, "PIG_CLASS").equals("10")) {
                            int tempNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "pregnancyNum")) - Integer
                                    .parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                            int tempDelveryNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "deliveryNum"))
                                    + Integer.parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                            weekSwineryList.get(weekList.size() - 1 - i).put("pregnancyNum", tempNum);
                            weekSwineryList.get(weekList.size() - 1 - i).put("deliveryNum", tempDelveryNum);
                            if (i != 0) {
                                for (int n = weekList.size() - 1 - i; n < weekList.size() - 1; n++) {
                                    weekSwineryList.get(n + 1).put("pregnancyNum", tempNum);
                                    weekSwineryList.get(n + 1).put("deliveryNum", tempDelveryNum);
                                }

                            }
                        }
                        if (Maps.getString(newSwiney, "PIG_CLASS").equals("11")) {
                            int tempNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "deliveryNum")) - Integer
                                    .parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                            int tempWeanNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "weanNum")) + Integer
                                    .parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                            weekSwineryList.get(weekList.size() - 1 - i).put("deliveryNum", tempNum);
                            weekSwineryList.get(weekList.size() - 1 - i).put("weanNum", tempWeanNum);
                            if (i != 0) {
                                for (int n = weekList.size() - 1 - i; n < weekList.size() - 1; n++) {
                                    weekSwineryList.get(n + 1).put("deliveryNum", tempNum);
                                    weekSwineryList.get(n + 1).put("weanNum", tempWeanNum);
                                }
                            }
                        }
                        if (Maps.getString(newSwiney, "PIG_CLASS").equals("6") || Maps.getString(newSwiney, "PIG_CLASS").equals("7") || Maps
                                .getString(newSwiney, "PIG_CLASS").equals("8")) {
                            // 修改母猪群蓝图同一周次内配种后返情流产空怀数据显示异常 修改人：张晓芸 修改日期：2016-10-09 --begin
                            /* int tempNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "pregnancyNum")) - Integer
                             * .parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                             * weekSwineryList.get(weekList.size() - 1 - i).put("pregnancyNum", tempNum); */
                            int tempNum = 0;
                            if (Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "pregnancyNum")) == 0) {
                                tempNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "breedNum")) - Integer
                                        .parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                                weekSwineryList.get(weekList.size() - 1 - i).put("breedNum", tempNum);
                            } else {
                                tempNum = Integer.parseInt(Maps.getString(weekSwineryList.get(weekList.size() - 1 - i), "pregnancyNum")) - Integer
                                        .parseInt(Maps.getString(newSwiney, "PIG_NUM"));
                                weekSwineryList.get(weekList.size() - 1 - i).put("pregnancyNum", tempNum);
                            }
                            // 修改母猪群蓝图同一周次内配种后返情流产空怀数据显示异常 修改人：张晓芸 修改日期：2016-10-09 --end
                            if (i != 0) {
                                for (int n = weekList.size() - 1 - i; n < weekList.size() - 1; n++) {
                                    weekSwineryList.get(n + 1).put("pregnancyNum", tempNum);
                                }
                            }
                        }
                    }
                }
                newSwineryClass.put("weekList", weekSwineryList);
            }
            resultlistlistmap.add(newSwineryClass);
        }
        if (totalBreed == 0) {
            resultMap.put("FQRate", "00.00%");
            resultMap.put("KHRate", "00.00%");
            resultMap.put("LCRate", "00.00%");
            resultMap.put("YCRate", "00.00%");
        } else {
            resultMap.put("FQRate", df.format(totalFQ / totalBreed * 100) + "%");
            resultMap.put("KHRate", df.format(totalKH / totalBreed * 100) + "%");
            resultMap.put("LCRate", df.format(totalLC / totalBreed * 100) + "%");
            resultMap.put("YCRate", df.format((totalFQ + totalKH + totalLC) / totalBreed * 100) + "%");
        }
        resultMap.put("swineryList", resultlistlistmap);
        return resultMap;
    }
    // @SuppressWarnings("unused")
    // @Override
    // public List<Map<String, Object>> searchSwineryClassNumByDate(Long farmId)
    // throws Exception {
    //
    // List<Map<String, Object>> resultlistlistmap = new ArrayList<>();
    //
    // // 整理查询到的结果集
    // Map<String, List<Map<String, Object>>> resultMaplistAll = new
    // HashMap<>();
    // // List<List<Map<String, Object>>> listAll = new ArrayList<>();
    //
    // List<Map<String, Object>> listparam = new ArrayList<>();
    //
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // String startdate = "";
    // // String enddate = "";
    // for (int i = 20; i >= 0; i--) {
    //
    // Map<String, Object> swinery = new HashMap<>();
    //
    // Calendar c21 = Calendar.getInstance();
    // c21.add(Calendar.WEEK_OF_YEAR, -1 * 21);
    // c21.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    // startdate = sdf.format(c21.getTime());
    //
    // swinery.put("farmId", String.valueOf(farmId));
    // swinery.put("startdate", startdate);
    // swinery.put("weekindex", i);
    // listparam.add(swinery);
    // }
    // // 查询母猪信息
    // List<Map<String, Object>> searchSwineryClassNumByDate =
    // portalMapper.searchSwineryClassNumByDate(listparam);
    //
    // Map<String, List<Map<String, Object>>> maplist = new HashMap<>();
    //
    // // 存母猪批次号
    // List<String> listswinery = new ArrayList<>();
    // // 配种母猪总数
    // Long pigAllNum = 0L;
    // // 整合结果集 按批次
    // for (int m = 0; m < searchSwineryClassNumByDate.size(); m++) {
    //
    // List<Map<String, Object>> rerultpicilist = new ArrayList<>();
    // Map<String, Object> ms = searchSwineryClassNumByDate.get(m);
    // Long swid = (Long) ms.get("SWINERY_ID");
    // String swiname = (String) ms.get("SWINERY_NAME");
    // Long pigClass = (Long) ms.get("PIG_CLASS");
    // Long pigNum = (Long) ms.get("PIG_NUM");
    // Integer weekIndex = (Integer) ms.get("WEEK_INDEX");
    // Integer weekYear = (Integer) ms.get("WEEK_YEAR");
    // String classname = (String) ms.get("PIG_CLASS_NAME");
    // Timestamp toworkDate = (Timestamp) ms.get("TOWORK_DATE");
    //
    // String swineryId = String.valueOf(swid);
    //
    // if (listswinery.size() == 0 || !listswinery.contains(swineryId)) {
    // listswinery.add(swineryId);
    // }
    //
    // Set<String> keySet = maplist.keySet();
    //
    // if (keySet.isEmpty()) {
    // if (weekIndex == 1) {
    // Map<String, Object> tem = new HashMap<String, Object>();
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(weekIndex));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear));
    //
    // tem.put("PIG_CLASS_NAME", classname);
    // rerultpicilist.add(tem);
    // maplist.put(swineryId, rerultpicilist);
    //
    // } else {
    // for (int week = 1; week <= weekIndex; week++) {
    //
    // Map<String, Object> tem = new HashMap<String, Object>();
    //
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(week));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear - weekIndex + week));
    //
    // if (week == weekIndex) {
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(weekIndex));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear));
    // tem.put("PIG_CLASS_NAME", "配种");
    // }
    // rerultpicilist.add(tem);
    // }
    // maplist.put(swineryId, rerultpicilist);
    // }
    //
    // } else if (keySet.contains(swineryId)) {
    // List<Map<String, Object>> listSwineryId = maplist.get(swineryId);
    // Map<String, Object> lsmap = listSwineryId.get(listSwineryId.size() - 1);
    //
    // // 已保存最大周
    // Integer dweekIndex = (Integer) lsmap.get("WEEK_INDEX");
    //
    // if (weekIndex - dweekIndex > 1) {
    // for (int weeknum = 1; weeknum < (weekIndex - dweekIndex); weeknum++) {
    // Map<String, Object> map = listSwineryId.get(listSwineryId.size() - 1);
    // Map<String, Object> tem = new HashMap<String, Object>();
    //
    // String weekclassname = (String) map.get("PIG_CLASS_NAME");
    // Long weekpigNum = (Long) map.get("PIG_NUM");
    //
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_CLASS_NAME", weekclassname);
    // tem.put("PIG_NUM", weekpigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(dweekIndex + weeknum));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear - (weekIndex - dweekIndex)
    // + weeknum));
    //
    // listSwineryId.add(tem);
    // if (weekIndex - dweekIndex - weeknum == 1) {
    // tem = new HashMap<String, Object>();
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_CLASS_NAME", classname);
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(weekIndex));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear));
    // listSwineryId.add(tem);
    // }
    // }
    // } else {
    // Map<String, Object> tem = new HashMap<String, Object>();
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_CLASS_NAME", classname);
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(weekIndex));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear));
    // listSwineryId.add(tem);
    // }
    //
    // maplist.put(swineryId, listSwineryId);
    //
    // } else if (!keySet.contains(swineryId)) {
    // if (weekIndex == 1) {
    // Map<String, Object> tem = new HashMap<String, Object>();
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_CLASS_NAME", classname);
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(weekIndex));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear));
    //
    // rerultpicilist.add(tem);
    // maplist.put(swineryId, rerultpicilist);
    //
    // } else {
    // for (int week = 1; week <= weekIndex; week++) {
    // Map<String, Object> tem = new HashMap<String, Object>();
    //
    // tem.put("PIG_CLASS_NAME", "待配种");
    // tem.put("WEEK_INDEX", Integer.valueOf(week));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear - weekIndex + week));
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_NUM", pigNum);
    //
    // if (week == weekIndex) {
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_CLASS_NAME", "配种");
    // tem.put("PIG_NUM", pigNum);
    // tem.put("WEEK_INDEX", Integer.valueOf(weekIndex));
    // tem.put("WEEK_YEAR", Integer.valueOf(weekYear));
    //
    // // pigAllNum = pigAllNum + weekpigNum;
    // }
    //
    // rerultpicilist.add(tem);
    // }
    // maplist.put(swineryId, rerultpicilist);
    // }
    // // List<Map<String, Object>> listSwineryId = maplist.get(swineryId);
    // // listSwineryId.add(ms);
    // // maplist.put(swineryId, listSwineryId);
    // }
    // }
    //
    // Map<String, String> esmap = new HashMap<>();
    // StringBuffer stbf = new StringBuffer();
    // esmap.put("farmId", String.valueOf(farmId));
    //
    // // int size = listswinery.size();
    // for (int index = 0; index < listswinery.size(); index++) {
    // String swineryId = listswinery.get(index);
    // List<Map<String, Object>> listpc = maplist.get(listswinery.get(index));
    //
    // for (Map<String, Object> m : listpc) {
    // if ("配种".equals(m.get("PIG_CLASS_NAME"))) {
    // Long pigNum = (Long) m.get("PIG_NUM");
    // pigAllNum = pigAllNum + pigNum;
    // }
    // }
    //
    // resultMaplistAll.put(swineryId, listpc);
    //
    // if (index == 0) {
    // stbf.append(swineryId);
    // } else {
    // stbf.append("," + swineryId);
    // }
    //
    // if (index >= 3) {
    // break;
    // }
    // }
    // String sbtst = stbf.toString();
    // esmap.put("swineryid", sbtst);
    // esmap.put("pigallnum", String.valueOf(pigAllNum));
    //
    // // 异常
    // List<Map<String, Object>> searchPigException = new ArrayList<>();
    // if (resultMaplistAll.size() > 0) {
    // searchPigException = portalMapper.searchPigExceptionScale(esmap);
    // }
    //
    // // 返回结果集 按 批次
    // List<Map<String, Object>> resultlist = new ArrayList<>();
    //
    // Map<String, Object> scalemap = new HashMap<>();
    // DecimalFormat df = new DecimalFormat("0.00");
    // double formatscale = 0;
    //
    // // 配种母猪数
    // Long pzNum = 0L;
    // // 分娩母猪数
    // Long ffNum = 0L;
    //
    // // 异常率
    // // double exceptionScale = 0;
    //
    // for (int maplistindex = 0; maplistindex < resultMaplistAll.size();
    // maplistindex++) {
    // // 批次map集合
    // Map<String, Object> resultmaplist = new HashMap<>();
    // // 分娩率
    // double scale = 0;
    //
    // // 批次list数据
    // List<List<Map<String, Object>>> resullistmap = new ArrayList<>();
    // // 周次
    // Integer weekIndext = null;
    // // 周数据
    // List<Map<String, Object>> resulmap = new ArrayList<>();
    // String swineryId = listswinery.get(maplistindex);
    // // 获取批次数据
    // List<Map<String, Object>> listmapobj = resultMaplistAll.get(swineryId);
    //
    // String swname = "";
    // for (int rsindex = 0; rsindex < listmapobj.size(); rsindex++) {
    // Map<String, Object> weekdatemap = listmapobj.get(listmapobj.size() - 1);
    //
    // Map<String, Object> map = listmapobj.get(rsindex);
    // Map<String, Object> mapzn = new HashMap<>();
    //
    // if ("配种".equals(map.get("PIG_CLASS_NAME"))) {
    // pzNum = (Long) map.get("PIG_NUM");
    // }
    //
    // if ("分娩".equals(map.get("PIG_CLASS_NAME"))) {
    // ffNum = (Long) map.get("PIG_NUM");
    // }
    //
    // String weekclassname = (String) map.get("PIG_CLASS_NAME");
    // Long weekpigNum = Long.valueOf(String.valueOf(map.get("PIG_NUM")));
    // Integer weekYear = (Integer) map.get("WEEK_YEAR");
    // Integer weekIndex = (Integer) map.get("WEEK_INDEX");
    // String swiname = (String) map.get("SWINERY_NAME");
    // swname = swiname;
    //
    // mapzn.put("PIG_CLASS_NAME", weekclassname);
    // mapzn.put("PIG_NUM", weekpigNum);
    // mapzn.put("WEEK_YEAR", weekYear);
    // mapzn.put("WEEK_INDEX", weekIndex);
    // mapzn.put("SWINERY_NAME", swiname);
    //
    // if (resulmap.size() > 0) {
    // weekIndext = (Integer) resulmap.get(resulmap.size() -
    // 1).get("WEEK_INDEX");
    // }
    // if (weekIndext == null) {
    // resulmap.add(mapzn);
    // } else if (weekIndext == weekIndex) {
    // resulmap.add(mapzn);
    // } else if (weekIndext != weekIndex) {
    // resulmap = new ArrayList<>();
    // resulmap.add(mapzn);
    // }
    //
    // // if (resulmap.size() > 0 && weekIndext != weekIndex) {
    // // resullistmap.add(resulmap);
    // // }
    // resullistmap.add(resulmap);
    //
    // if (listmapobj.size() < 21 && weekIndex != null && rsindex ==
    // listmapobj.size() - 1) {
    // for (int weeknum = 1; weeknum <= (21 - listmapobj.size()); weeknum++) {
    // resulmap = new ArrayList<>();
    // Map<String, Object> tem = new HashMap<String, Object>();
    //
    // tem.put("SWINERY_NAME", swiname);
    // tem.put("PIG_CLASS_NAME", weekclassname);
    // tem.put("PIG_NUM", weekpigNum);
    // tem.put("WEEK_INDEX", ++weekIndex);
    // tem.put("WEEK_YEAR", weekYear + weeknum);
    //
    // resulmap.add(tem);
    // resullistmap.add(resulmap);
    // }
    // }
    //
    // }
    // resultmaplist.put("name", swname);
    // resultmaplist.put("map", resullistmap);
    //
    // if (pzNum > 0) {
    // scale = (double) ffNum / pzNum * 100;
    // String fscale = df.format(scale);
    // formatscale = Double.valueOf(fscale);
    // }
    //
    // resultmaplist.put("pcscale", formatscale + "%");
    //
    // resultlistlistmap.add(resultmaplist);
    // }
    //
    // Double pigexception = 0d;
    // for (Map<String, Object> msp : searchPigException) {
    // String allPigScale = String.valueOf(msp.get("PIG_SCALE"));
    // pigexception = pigexception + Double.valueOf(allPigScale);
    // String exceptionclass = (String) msp.get("PREGNANCY_RESULT");
    // scalemap.put(exceptionclass, allPigScale + "%");
    // }
    //
    // scalemap.put("AE", pigexception + "%");
    // resultlistlistmap.add(scalemap);
    //
    // // 批次 周 状态 母猪数
    // return resultlistlistmap;
    // }

    public static void main(String[] args) throws Exception {

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        String nowenddate = TimeUtil.getSysDate();
        for (int i = 0; i < TimeUtil.getMM(); i++) {
            map = new HashMap<>();
            String firstDateOfMonth = TimeUtil.dateAddMonth(TimeUtil.getFirstDateOfMonth(nowenddate), -i);
            String lastDateOfMonth = i == 0 ? nowenddate : TimeUtil.dateAddMonth(TimeUtil.getLastDateOfMonth(nowenddate), -i);
            map.put("startDate", firstDateOfMonth);
            map.put("endDate", lastDateOfMonth);
            list.add(map);
        }
        System.out.println(list.toString());

    }

    @Override
    public void editParity(List<ParityDistributionModel> parityList, long[] ids) throws Exception {

        List<Long> validList = new ArrayList<Long>();
        for (ParityDistributionModel parity : parityList) {
            validList.add(parity.getParity());
        }

        HashSet<Long> set = new HashSet<Long>();
        for (Long parity : validList) {
            set.add(parity);
        }

        if (validList.size() != set.size()) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "胎次有重复，请检查输入的胎次！");
        }

        Long maxVersion = parityMapper.getMaxVersion();
        if (maxVersion == null) {
            for (ParityDistributionModel pmodel : parityList) {
                if (pmodel.getParity() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "输入胎次不能为空！");
                }
                if (pmodel.getPercent() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "输入比例不能为空！");
                }
                pmodel.setVersion(1L);
            }
        } else {
            for (ParityDistributionModel pmodel : parityList) {
                if (pmodel.getParity() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "输入胎次不能为空！");
                }
                if (pmodel.getPercent() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "输入比例不能为空！");
                }
                pmodel.setVersion(maxVersion);
            }
        }

        if (ids != null && ids.length > 0) {
            parityMapper.deletes(ids);
        }
        parityMapper.inserts(parityList);
    }

    @Override
    public void deleteParity(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            parityMapper.deletes(ids);
        }
    }

    @Override
    public void editNewParity(List<ParityDistributionModel> parityList, long[] ids) throws Exception {
        Long maxVersion = parityMapper.getMaxVersion();
        if (maxVersion == null) {
            maxVersion = 0L;
        }
        for (ParityDistributionModel pmodel : parityList) {
            if (pmodel.getParity() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "输入胎次不能为空！");
            }
            if (pmodel.getPercent() == null) {
                Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "输入比例不能为空！");
            }
            pmodel.setVersion(maxVersion + 1);
        }
        if (ids != null && ids.length > 0) {
            parityMapper.upStatus(ids);
        }
        parityMapper.inserts(parityList);
    }

    @Override
    public List<Map<String, Object>> searchSwineryClassNumBySwineryId(Long farmId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Long> mapWeekParam = new HashMap<>();
        mapWeekParam.put("farmId", farmId);
        mapWeekParam.put("weekHeight", 21L);
        // 21周，周次年份数据
        List<Map<String, Object>> searchWeekInYear = portalMapper.searchWeekInYear(mapWeekParam);

        List<Integer> week21 = new ArrayList<>();
        for (int index = 0; index < searchWeekInYear.size(); index++) {
            Map<String, Object> weekInYear = searchWeekInYear.get(index);
            Integer weekIndexOfYear = (Integer) weekInYear.get("WEEK");
            week21.add(weekIndexOfYear);
        }
        // 21周最近4个猪群信息
        List<Map<String, Object>> searchSwineryId = portalMapper.searchSwineryIdLimit4(farmId);

        StringBuffer sb = new StringBuffer();
        for (int index = 0; index < searchSwineryId.size(); index++) {
            Map<String, Object> swineryId = searchSwineryId.get(index);
            Long swid = (Long) swineryId.get("SWINERY_ID");
            if (index == 0) {
                sb.append(swid);
            } else {
                sb.append("," + swid);
            }
        }
        map.put("farmId", farmId);
        map.put("swineryId", sb.toString());

        List<Map<String, Object>> searchSwineryClassNumBySwineryId = new ArrayList<>();
        if (searchSwineryId.size() > 0) {
            // 查询这几个猪群的信息
            searchSwineryClassNumBySwineryId = portalMapper.searchSwineryClassNumBySwineryId(map);
        } else {
            List<Map<String, Object>> resultAll = new ArrayList<>();
            // Map<String, Object> scalemap = new HashMap<>();
            // scalemap.put("EXCEPTION", 0 + "%");
            // scalemap.put("KH", 0 + "%");
            // scalemap.put("LC", 0 + "%");
            // scalemap.put("FQ", 0 + "%");
            // scalemap.put("EXCEPTION", 0 + "%");
            // resultAll.add(scalemap);
            return resultAll;
        }
        // 按照批次 整理结果集
        Map<String, List<List<Map<String, Object>>>> mapList = new HashMap<>();

        // 猪群名称
        List<String> listSwineryName = new ArrayList<>();

        Set<String> keySet = mapList.keySet();
        for (Map<String, Object> mso : searchSwineryClassNumBySwineryId) {
            // 周次
            List<List<Map<String, Object>>> listListMap = new ArrayList<List<Map<String, Object>>>();
            // 周数据
            List<Map<String, Object>> listMap = new ArrayList<>();
            Map<String, Object> msomap = new HashMap<>();

            String swineryName = (String) mso.get("SWINERY_NAME");
            Integer weekOfYear = (Integer) mso.get("WEEK");
            Long pigNum = (Long) mso.get("PIG_NUM");
            String pigClassName = (String) mso.get("PIG_CLASS_NAME");

            if (!keySet.contains(swineryName)) {
                msomap.put("SWINERY_NAME", swineryName);
                msomap.put("WEEK", weekOfYear);
                msomap.put("PIG_NUM", pigNum);
                msomap.put("PIG_CLASS_NAME", "PZ");

                listMap.add(msomap);
                listListMap.add(listMap);

                mapList.put(swineryName, listListMap);

                listSwineryName.add(swineryName);
            } else if (keySet.contains(swineryName)) {
                List<List<Map<String, Object>>> listMapSwineryName = mapList.get(swineryName);
                List<Map<String, Object>> listMapSwineryObj = new ArrayList<>();

                List<Map<String, Object>> lastMapSwineryNameObj = listMapSwineryName.get(listMapSwineryName.size() - 1);
                // 该猪群中已保存最后一周数据
                Map<String, Object> lastMapObj = lastMapSwineryNameObj.get(lastMapSwineryNameObj.size() - 1);
                Integer lastListMapObjWeek = (Integer) lastMapObj.get("WEEK");

                msomap.put("SWINERY_NAME", swineryName);
                msomap.put("WEEK", weekOfYear);
                msomap.put("PIG_NUM", pigNum);
                msomap.put("PIG_CLASS_NAME", pigClassName);
                if (lastListMapObjWeek == weekOfYear) {
                    lastMapSwineryNameObj.add(msomap);
                    // listMapSwineryName.add(lastMapSwineryNameObj);
                } else {
                    if (weekOfYear - lastListMapObjWeek > 1) {

                        String lastSwineryName = (String) lastMapObj.get("SWINERY_NAME");
                        Long lastPigNum = (Long) lastMapObj.get("PIG_NUM");
                        String lastPigClassName = (String) lastMapObj.get("PIG_CLASS_NAME");

                        Integer weekHight = 0;

                        // 同一年
                        Integer wheight = 0;
                        if (weekOfYear > lastListMapObjWeek) {
                            weekHight = weekOfYear - lastListMapObjWeek;
                        } else {
                            mapWeekParam.put("weekHeight", 41L);
                            List<Map<String, Object>> searchWeekInYear2 = portalMapper.searchWeekInYear(mapWeekParam);
                            for (Map<String, Object> sm : searchWeekInYear2) {
                                wheight = wheight >= (Integer) sm.get("WEEK") ? wheight : (Integer) sm.get("WEEK");
                            }

                            weekHight = weekOfYear + wheight - lastListMapObjWeek;
                        }

                        for (int weekNum = 1; weekNum < weekHight; weekNum++) {
                            Map<String, Object> copMap = new HashMap<>();

                            copMap.put("SWINERY_NAME", lastSwineryName);
                            copMap.put("WEEK", lastListMapObjWeek + weekNum - wheight);
                            copMap.put("PIG_NUM", lastPigNum);
                            copMap.put("PIG_CLASS_NAME", lastPigClassName);

                            listMapSwineryObj.add(copMap);
                            listMapSwineryName.add(listMapSwineryObj);
                            copMap = new HashMap<>();
                            listMapSwineryObj = new ArrayList<>();
                        }

                        listMapSwineryObj.add(msomap);
                        listMapSwineryName.add(listMapSwineryObj);
                    } else {
                        listMapSwineryObj.add(msomap);
                        listMapSwineryName.add(listMapSwineryObj);
                    }
                }

                mapList.put(swineryName, listMapSwineryName);
            }
        }

        // 补漏 截取 有效 数据
        for (String si : listSwineryName) {
            List<List<Map<String, Object>>> listOfMapListBySwineryId = mapList.get(si);

            List<Map<String, Object>> mapWeekDate = listOfMapListBySwineryId.get(listOfMapListBySwineryId.size() - 1);
            List<Map<String, Object>> copyMapWeekDate = new ArrayList<>();

            // 当前所在周
            Integer weekIndexOf = (Integer) mapWeekDate.get(0).get("WEEK");
            while (weekIndexOf != week21.get(week21.size() - 1)) {
                mapWeekDate = listOfMapListBySwineryId.get(listOfMapListBySwineryId.size() - 1);
                for (int index = 0; index < mapWeekDate.size(); index++) {
                    Map<String, Object> maDate = mapWeekDate.get(index);
                    Map<String, Object> mp = new HashMap<>();

                    String swiName = (String) maDate.get("SWINERY_NAME");
                    Integer week = (Integer) maDate.get("WEEK");
                    Long pNum = (Long) maDate.get("PIG_NUM");
                    String pName = (String) maDate.get("PIG_CLASS_NAME");

                    Integer indexOf = week21.indexOf(week);
                    weekIndexOf = (Integer) searchWeekInYear.get(indexOf + 1).get("WEEK");
                    mp.put("SWINERY_NAME", swiName);
                    mp.put("WEEK", weekIndexOf);
                    mp.put("PIG_NUM", pNum);
                    mp.put("PIG_CLASS_NAME", pName);
                    copyMapWeekDate.add(mp);
                }

                listOfMapListBySwineryId.add(copyMapWeekDate);
                copyMapWeekDate = new ArrayList<>();
            }
            mapList.put(si, listOfMapListBySwineryId);
        }

        List<Map<String, Object>> resultAll = new ArrayList<>();
        Map<String, Object> restListMap = new HashMap<>();

        // 配种母猪数
        Long pzNum = 0L;

        // 分娩母猪数
        Long ffNum = 0L;

        // 分娩率
        double scale = 0;

        // 配种母猪总数
        Long pigAllNum = 0L;
        for (String sin : listSwineryName) {
            boolean onceFM = true;
            List<List<Map<String, Object>>> listOfMapListBySwineryId = mapList.get(sin);
            int remove = 0;
            for (int ii = listOfMapListBySwineryId.size() - 1; ii >= 0; ii--) {

                if ("PZ".equals(listOfMapListBySwineryId.get(ii).get(0).get("PIG_CLASS_NAME"))) {
                    Map<String, Object> mapObj = listOfMapListBySwineryId.get(ii).get(0);
                    pzNum = (Long) mapObj.get("PIG_NUM");
                }
                if (ii == 0) {
                    pigAllNum = pigAllNum + pzNum;
                }
                if ("FM".equals(listOfMapListBySwineryId.get(ii).get(0).get("PIG_CLASS_NAME")) && onceFM) {
                    Map<String, Object> mapObj = listOfMapListBySwineryId.get(ii).get(0);
                    ffNum = (Long) mapObj.get("PIG_NUM");
                    onceFM = false;
                }

                if (++remove > 21) {
                    listOfMapListBySwineryId.remove(ii);
                }
            }
            DecimalFormat df = new DecimalFormat("0.00");
            double formatscale = 0;
            if (pzNum > 0) {
                scale = (double) ffNum / pzNum * 100;
                String fscale = df.format(scale);
                formatscale = Double.valueOf(fscale);
            }
            restListMap.put("name", sin);
            restListMap.put("map", listOfMapListBySwineryId);
            restListMap.put("pcscale", formatscale + "%");
            resultAll.add(restListMap);
            restListMap = new HashMap<>();
            listOfMapListBySwineryId = new ArrayList<>();
        }

        Map<String, String> esmap = new HashMap<>();
        esmap.put("swineryid", sb.toString());
        esmap.put("pigallnum", String.valueOf(pigAllNum));
        esmap.put("farmId", String.valueOf(farmId));

        // 异常
        List<Map<String, Object>> searchPigException = new ArrayList<>();
        if (resultAll.size() > 0) {
            searchPigException = portalMapper.searchPigExceptionScale(esmap);
        }

        Map<String, Object> scalemap = new HashMap<>();
        Double pigexception = 0d;
        for (Map<String, Object> msp : searchPigException) {
            Double KH = Double.valueOf(String.valueOf(msp.get("KH")));
            Double LC = Double.valueOf(String.valueOf(msp.get("LC")));
            Double FQ = Double.valueOf(String.valueOf(msp.get("FQ")));
            pigexception = KH + LC + FQ;
            scalemap.put("EXCEPTION", pigexception + "%");
            scalemap.put("KH", pigexception + "%");
            scalemap.put("LC", pigexception + "%");
            scalemap.put("FQ", pigexception + "%");
            scalemap.put("EXCEPTION", pigexception + "%");
        }
        resultAll.add(scalemap);
        System.out.println(resultAll.toString());
        return resultAll; // return List<Map<String, Object>>//restListMap
                          // Map<String, List<List<Map<String, Object>>>>
    }

    @Override
    public List<List<List<Map<String, Object>>>> searchSwineryPigExcepitionBySwineryId(Long farmId) {
        Map<String, String> map = new HashMap<>();
        portalMapper.searchSwineryPigExcepitionBySwineryId(map);
        return null;
    }

    @Override
    public List<Map<String, Object>> productionAbnormalByBreed(Long farmId) {

        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        List<Map<String, Object>> resultAll = new ArrayList<>();
        resultAll = portalMapper.productionAbnormalByBreed(map);
        return resultAll;
    }

    @Override
    public List<Map<String, Object>> productionAbnormalByLaborHouse(Long farmId) {

        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        List<Map<String, Object>> resultAll = new ArrayList<>();
        resultAll = portalMapper.productionAbnormalByLaborHouse(map);
        return resultAll;
    }

    @Override
    public List<Map<String, Object>> productionAbnormalByDelivery(Long farmId) {

        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        List<Map<String, Object>> resultAll = new ArrayList<>();
        resultAll = portalMapper.productionAbnormalByDelivery(map);
        return resultAll;
    }

    @Override
    public List<Map<String, Object>> productionAbnormalByWean(Long farmId) {

        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        List<Map<String, Object>> resultAll = new ArrayList<>();
        resultAll = portalMapper.productionAbnormalByWean(map);
        return resultAll;
    }

    @Override
    public List<Map<String, Object>> productionAbnormalBySell(Long farmId) {

        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        List<Map<String, Object>> resultAll = new ArrayList<>();
        resultAll = portalMapper.productionAbnormalBySell(map);
        return resultAll;
    }

    /**
     * 栏舍周转
     */
    @Override
    public Map<String, List<Map<String, Object>>> searchChangePigpenAndHouseByDateType(String dateTyp) {
        String startDate = "";

        Map<String, String> map = new HashMap<>();
        map.put("farmId", String.valueOf(getFarmId()));
        if ("month".equals(dateTyp)) {
            startDate = TimeUtil.getFirstDateOfMonth(String.valueOf(TimeUtil.getYYYYMMDD()));
        } else {
            startDate = TimeUtil.getCurrYearFirst();
        }
        map.put("startDate", startDate);

        Map<String, Object> hm = new HashMap<>();
        hm.put("HOUSE_NAME", "无");
        hm.put("HOUSE_CLASS", "无");
        hm.put("SWINERY_NUM", "无");
        hm.put("START_NUM", "无");
        hm.put("BITH_NUM", "无");
        hm.put("INTO_NUM", "无");
        hm.put("OUT_NUM", "无");
        hm.put("DIE_NUM", "无");
        hm.put("NOW_NUM", "无");

        Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
        StringBuffer pigClass = new StringBuffer();
        pigClass.append(PigConstants.PIG_CLASS_SCGZ);
        pigClass.append("," + PigConstants.PIG_CLASS_HBDP);
        pigClass.append("," + PigConstants.PIG_CLASS_FQ);
        pigClass.append("," + PigConstants.PIG_CLASS_FQ1);
        pigClass.append("," + PigConstants.PIG_CLASS_LC);
        pigClass.append("," + PigConstants.PIG_CLASS_KH);
        pigClass.append("," + PigConstants.PIG_CLASS_RS);
        pigClass.append("," + PigConstants.PIG_CLASS_BR);
        pigClass.append("," + PigConstants.PIG_CLASS_SOW_DN);
        map.put("pigClass", pigClass.toString());
        map.put("pigClassType", PigConstants.PRODUCTION_PIG);
        List<Map<String, Object>> changePigpenAndHouseByPigType1 = portalMapper.searchChangePigpenAndHouseByDateType(map);
        if (changePigpenAndHouseByPigType1.isEmpty()) {
            changePigpenAndHouseByPigType1.add(hm);
        }
        resultMap.put(PigConstants.PRODUCTION_PIG, changePigpenAndHouseByPigType1);

        pigClass = new StringBuffer();
        pigClass.append(PigConstants.PIG_CLASS_BF);
        map.put("pigClass", pigClass.toString());
        map.put("pigClassType", PigConstants.NURSING_PIG);
        List<Map<String, Object>> changePigpenAndHouseByPigType2 = portalMapper.searchChangePigpenAndHouseByDateType(map);
        if (changePigpenAndHouseByPigType2.isEmpty()) {
            changePigpenAndHouseByPigType2.add(hm);
        }
        resultMap.put(PigConstants.NURSING_PIG, changePigpenAndHouseByPigType2);

        pigClass = new StringBuffer();
        pigClass.append(PigConstants.PIG_CLASS_YH);
        map.put("pigClass", pigClass.toString());
        map.put("pigClassType", PigConstants.FATTENING_PIG);
        List<Map<String, Object>> changePigpenAndHouseByPigType3 = portalMapper.searchChangePigpenAndHouseByDateType(map);
        if (changePigpenAndHouseByPigType3.isEmpty()) {
            changePigpenAndHouseByPigType3.add(hm);
        }
        resultMap.put(PigConstants.FATTENING_PIG, changePigpenAndHouseByPigType3);

        pigClass = new StringBuffer();
        pigClass.append(PigConstants.PIG_CLASS_BRJZ);
        pigClass.append("," + PigConstants.PIG_CLASS_BRRZ);
        map.put("pigClass", pigClass.toString());
        map.put("pigClassType", PigConstants.LACTATION_PIG);
        List<Map<String, Object>> changePigpenAndHouseByPigType4 = portalMapper.searchChangePigpenAndHouseByDateType(map);
        if (changePigpenAndHouseByPigType4.isEmpty()) {
            changePigpenAndHouseByPigType4.add(hm);
        }
        resultMap.put(PigConstants.LACTATION_PIG, changePigpenAndHouseByPigType4);

        return resultMap;
    }

    /**
     * 母猪群分娩异常明细
     */
    @Override
    public List<Map<String, Object>> searchDeliveryExceptionDetail(Long farmId, Long swineryId) {
        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        map.put("swineryId", swineryId);
        List<Map<String, Object>> searchDeliveryExceptionDetail = portalMapper.searchDeliveryExceptionDetail(map);
        return searchDeliveryExceptionDetail;
    }

    /**
     * 生产异常提醒 - 淘汰提醒
     */
    @Override
    public List<Map<String, Object>> productionAbnormalByObsolete(Long farmId) {

        Map<String, Long> map = new HashMap<>();
        map.put("farmId", farmId);
        List<Map<String, Object>> productionAbnormalByObsolete = portalMapper.productionAbnormalByObsolete(map);
        return productionAbnormalByObsolete;
    }

    /**
     * 综合指标
     * 
     * @throws Exception
     */
    @SuppressWarnings("unused")
    @Override
    public Map<String, Object> searchSyntheticalIndicatorByDate(String dateType) throws Exception {
        Map<String, String> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        map.put("farmId", String.valueOf(getFarmId()));
        map.put("dateType", dateType);
        // 非生产天数
        Double nonProductionDays = searchNonProductionDays(map);
        // 七天断配率
        Double weanBreedOf7Day = search7DayAlreadyBreedProbabilityByDate(map);
        // 分娩率
        Double searchDeliveryProbability = searchDeliveryProbabilityByDate(map);
        // 年产胎次
        Double searchYearParity = searchParityOfYearByDate(map);
        // 出生重
        Double searchPigBirthWeight = searchPigBirthWeightByDate(map);

        // 综合指标 24日龄断奶重 --产房转保育
        Double search24DayWeanPigWeight = search24DayWeanPigWeightByDate(map);
        // 综合指标 7030重 -- 保育转育肥
        Double search7030PigWeight = search7030PigWeightByDate(map);

        // 产房全程成活率
        Double searchDeliverySurvivalRate = searchDeliverySurvivalRateByDate(map);
        // 综合指标 保育全程成活率
        Double searchWelfareSurvivalRate = searchWelfareSurvivalRateByDate(map);
        // 综合指标 育肥全程成活率
        Double searchFattenSurvivalRate = searchFattenSurvivalRateByDate(map);
        // 综合指标 全程成活率
        Double searchCompleteSurvivalRate = searchCompleteSurvivalRateByDate(map);

        // 综合指标 110KG出栏日龄 -- 出售
        Double search110kgOutHouse = search110kgOutHouseByDate(map);
        // 综合指标 PSY（预测）
        Double searchPSYByDate = searchPSYByDate(map);
        // 综合指标 MSY（预测）
        Double searchMSYByDate = searchMSYByDate(map);

        resultMap.put("nonProductionDays", nonProductionDays);
        resultMap.put("weanBreedOf7Day", weanBreedOf7Day);
        resultMap.put("searchDeliveryProbability", searchDeliveryProbability);
        resultMap.put("searchYearParity", searchYearParity);
        resultMap.put("searchPigBirthWeight", searchPigBirthWeight);
        resultMap.put("search24DayWeanPigWeight", search24DayWeanPigWeight);
        resultMap.put("search7030PigWeight", search7030PigWeight);
        resultMap.put("searchDeliverySurvivalRate", searchDeliverySurvivalRate);
        resultMap.put("searchWelfareSurvivalRate", searchWelfareSurvivalRate);
        resultMap.put("searchFattenSurvivalRate", searchFattenSurvivalRate);
        resultMap.put("searchCompleteSurvivalRate", searchCompleteSurvivalRate);
        resultMap.put("search110kgOutHouse", search110kgOutHouse);
        resultMap.put("searchPSYByDate", searchPSYByDate);
        resultMap.put("searchMSYByDate", searchMSYByDate);

        return resultMap;
    }

    // 非生产天数
    @Override
    public Double searchNonProductionDays(Map<String, String> map) {

        // 期间生产母猪非生产天数之和
        Map<String, Object> syntheticalIndicatorByDate = portalMapper.searchNonProductionDays(map);

        Double FSCTS = 0d;
        Double days = Double.valueOf(String.valueOf(syntheticalIndicatorByDate.get("DAYS")));

        map.put("beforeDay", "0");
        // 生产母猪 日均存栏量
        Double avgSowPigSum = avgDaySowPigSum(map) == 0 ? 1 : avgDaySowPigSum(map);
        FSCTS = days / avgSowPigSum;
        return FSCTS;
    }

    // 期初至今时间区间 生产母猪 日均存栏量
    private Double avgDaySowPigSum(Map<String, String> map) {
        // 期初存栏量及期间天数
        Map<String, Object> searchDayIndexByDate = portalMapper.searchDayIndexByDate(map);
        // 期间内转生产母猪数量 + 入场即为生产母猪数量
        Map<String, Object> searchDayIndexByProductDate = portalMapper.searchDayIndexByProductDate(map);
        // 期间离场生产母猪数
        Map<String, Object> searchDayIndexByLeaveDate = portalMapper.searchDayIndexByLeaveDate(map);

        // 期初母猪存栏量
        Double mzPg = Double.valueOf(String.valueOf(searchDayIndexByDate.get("MZ_PIG")));
        Double productSum = Double.valueOf(String.valueOf(searchDayIndexByProductDate.get("PRODUCT_SUM")));
        Double leaveSum = Double.valueOf(String.valueOf(searchDayIndexByLeaveDate.get("LEAVE_SUM")));
        // 期间天数
        Double timeStampDiff = Double.valueOf(String.valueOf(searchDayIndexByDate.get("TIMESTAMPDIFF")));

        return (productSum - leaveSum) / timeStampDiff + mzPg;
    }

    // 7天断配率
    @Override
    public Double search7DayAlreadyBreedProbabilityByDate(Map<String, String> map) {
        Map<String, Object> search7DayProbability = portalMapper.search7DayAlreadyBreedProbabilityByDate(map);
        Double weanBreedOf7Day = Double.valueOf(String.valueOf(search7DayProbability.get("wean_breed_of_7day")));
        return weanBreedOf7Day;
    }

    // 综合指标 分娩率
    @Override
    public Double searchDeliveryProbabilityByDate(Map<String, String> map) {
        Map<String, Object> searchDeliveryProbabilityByDate = portalMapper.searchDeliveryProbabilityByDate(map);
        Double deliveryProbability = 0.0d;
        if (searchDeliveryProbabilityByDate != null) {
            deliveryProbability = Double.valueOf(String.valueOf(searchDeliveryProbabilityByDate.get("DELIVERY_PROBABILITY")));
        }
        return deliveryProbability;
    }

    // 综合指标 年产胎次
    @Override
    public Double searchParityOfYearByDate(Map<String, String> map) throws Exception {
        String nowenddate = TimeUtil.getSysDate();
        String oldenddate = TimeUtil.dateAddYear(nowenddate, -1);
        String dateAddDay = TimeUtil.dateAddDay(oldenddate, 365);
        Double middleDays = 365d;
        if (!nowenddate.equals(dateAddDay)) {
            middleDays = 366d;
        }

        map.put("beforeDay", "0");
        // 生产母猪 日均存栏量
        Double avgSowPigSum = avgDaySowPigSum(map);

        // 生产天数之和
        Map<String, Object> searchProductPigProductionDaysByDate = portalMapper.searchProductPigProductionDaysByDate(map);

        // 非生产天数
        Double nonProductionDays = searchNonProductionDays(map);

        Double productionDays = 0.0d;
        if (searchProductPigProductionDaysByDate != null) {
            productionDays = Double.valueOf(String.valueOf(searchProductPigProductionDaysByDate.get("DAYS")));
        }
        if (avgSowPigSum == 0 || productionDays == 0) {
            return 0d;
        }

        // 平均生产天数
        Double avgNotProductionDays = productionDays / avgSowPigSum;
        // 年产胎次
        Double search24DayWeanPigWeight = (middleDays - nonProductionDays) / avgNotProductionDays;
        return search24DayWeanPigWeight;
    }

    // 初生重
    @Override
    public Double searchPigBirthWeightByDate(Map<String, String> map) {
        Map<String, Object> searchPigBirthWeightByDate = portalMapper.searchPigBirthWeightByDate(map);
        Double BirthWeight = 0.0d;
        if (searchPigBirthWeightByDate != null) {
            BirthWeight = Double.valueOf(String.valueOf(searchPigBirthWeightByDate.get("Birth_Weight")));
        }
        return BirthWeight;
    }

    // 综合指标 24日龄断奶重
    @Override
    public Double search24DayWeanPigWeightByDate(Map<String, String> map) throws Exception {

        Map<String, Object> search24DayWeanPigWeightByDate = portalMapper.search24DayWeanPigWeightByDate(map);
        String avgDay = "";
        String avgWeight = "";
        if (search24DayWeanPigWeightByDate != null) {
            avgWeight = String.valueOf(search24DayWeanPigWeightByDate.get("AVG_WEIGHT"));
            avgDay = String.valueOf(search24DayWeanPigWeightByDate.get("AVG_DAY"));
        } else {
            return 0D;
        }
        map.put("correctionType", PigConstants.WCM_001);
        map.put("avgDay", avgDay);
        map.put("avgWeight", avgWeight);
        return getWeightByDayAge(map);
    }

    // 综合指标 7030重 转育肥
    @Override
    public Double search7030PigWeightByDate(Map<String, String> map) {
        Map<String, Object> search7030PigWeightByDate = portalMapper.search7030PigWeightByDate(map);
        String avgDay = "";
        String avgWeight = "";
        if (search7030PigWeightByDate != null) {
            avgWeight = String.valueOf(search7030PigWeightByDate.get("AVG_WEIGHT"));
            avgDay = String.valueOf(search7030PigWeightByDate.get("AVG_DAY"));
        } else {
            return 0D;
        }
        map.put("correctionType", PigConstants.WCM_002);
        map.put("avgDay", avgDay);
        map.put("avgWeight", avgWeight);
        return getWeightByDayAge(map);
    }

    // 综合指标 产房全程成活率
    @Override
    public Double searchDeliverySurvivalRateByDate(Map<String, String> map) {
        map.put("houseType", String.valueOf(PigConstants.HOUSE_CLASS_DELIVERY));
        Map<String, Object> searchDeliverySurvivalRateByDate = portalMapper.searchDeliverySurvivalRateByDate(map);
        Double searchDeliverySurvivalRate = 0.0d;
        if (searchDeliverySurvivalRateByDate != null) {
            searchDeliverySurvivalRate = Double.valueOf(String.valueOf(searchDeliverySurvivalRateByDate.get("SURVIVAL_RATE")));
        }
        return searchDeliverySurvivalRate;
    }

    // 综合指标 保育全程成活率
    @Override
    public Double searchWelfareSurvivalRateByDate(Map<String, String> map) {
        map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_BF));
        Map<String, Object> searchWelfareSurvivalRateByDate = portalMapper.searchXXSurvivalRateByDate(map);
        Double searchWelfareSurvivalRate = 0.0d;
        if (searchWelfareSurvivalRateByDate != null) {
            searchWelfareSurvivalRate = Double.valueOf(String.valueOf(searchWelfareSurvivalRateByDate.get("SURVIVAL_RATE")));
        }
        return searchWelfareSurvivalRate;
    }

    // 综合指标 育肥全程成活率
    @Override
    public Double searchFattenSurvivalRateByDate(Map<String, String> map) {
        map.put("pigClass", String.valueOf(PigConstants.PIG_CLASS_YH));
        Map<String, Object> searchFattenSurvivalRateByDate = portalMapper.searchXXSurvivalRateByDate(map);
        Double searchFattenSurvivalRate = 0.0d;
        if (searchFattenSurvivalRateByDate != null) {
            searchFattenSurvivalRate = Double.valueOf(String.valueOf(searchFattenSurvivalRateByDate.get("SURVIVAL_RATE")));
        }
        return searchFattenSurvivalRate;
    }

    // 综合指标 全程成活率
    @Override
    public Double searchCompleteSurvivalRateByDate(Map<String, String> map) {
        map.put("houseType", String.valueOf(PigConstants.HOUSE_CLASS_DELIVERY));
        map.put("pigClass", PigConstants.PIG_CLASS_BF + "," + PigConstants.PIG_CLASS_YH);
        Map<String, Object> searchCompleteSurvivalRateByDate = portalMapper.searchCompleteSurvivalRateByDate(map);
        Double searchCompleteSurvivalRate = 0.0d;
        if (searchCompleteSurvivalRateByDate != null) {
            searchCompleteSurvivalRate = Double.valueOf(String.valueOf(searchCompleteSurvivalRateByDate.get("SURVIVAL_RATE")));
        }
        return searchCompleteSurvivalRate;
    }

    // 综合指标 110KG出栏日龄
    @Override
    public Double search110kgOutHouseByDate(Map<String, String> map) {
        Map<String, Object> search110kgOutHouseByDate = portalMapper.search110kgOutHouseByDate(map);
        String avgDay = "";
        String avgWeight = "";
        if (search110kgOutHouseByDate != null) {
            avgWeight = String.valueOf(search110kgOutHouseByDate.get("AVG_WEIGHT"));
            avgDay = String.valueOf(search110kgOutHouseByDate.get("AVG_DAY"));
        } else {
            return 0D;
        }
        map.put("correctionType", PigConstants.WCM_003);
        map.put("avgDay", avgDay);
        map.put("avgWeight", avgWeight);
        return getWeightByDayAge(map);
    }

    // 综合指标 PSY（预测）
    @Override
    public Double searchPSYByDate(Map<String, String> map) {
        Map<String, Object> searchPSYByDate = portalMapper.searchPSYByDate(map);
        Double searchPSY = 0.0d;
        Double weanChildPigSum = 0.0d;
        Double dayLength = 0.0d;
        if (searchPSYByDate != null) {
            weanChildPigSum = Double.valueOf(String.valueOf(searchPSYByDate.get("SOW_SUM")));
            dayLength = Double.valueOf(String.valueOf(searchPSYByDate.get("DAY_LENGTH")));
        }
        map.put("beforeDay", "140");
        // 生产母猪 日均存栏量
        Double avgSowPigSum = avgDaySowPigSum(map);
        if (avgSowPigSum == 0 || dayLength == 0 || dayLength == 0) {
            return 0d;
        }
        searchPSY = weanChildPigSum / avgSowPigSum / dayLength * dayLength;
        return searchPSY;
    }

    // 综合指标 MSY（预测）
    @Override
    public Double searchMSYByDate(Map<String, String> map) {
        // Map<String, Object> searchMSYByDate = portalMapper.searchMSYByDate(map);
        Double searchMSY = 0.0d;
        // Double bYandYFCHL =0.0d;
        // if(searchMSYByDate != null){
        // bYandYFCHL = Double.valueOf(String.valueOf(searchMSYByDate.get("BY_AND_YF_CHL")));
        // }
        // 综合指标 PSY（预测）
        Double searchPSYByDate = searchPSYByDate(map);
        // 产房成活率
        Double searchDeliverySurvivalRateByDate = searchDeliverySurvivalRateByDate(map) / 100;
        // 保育成活率
        Double searchWelfareSurvivalRateByDate = searchWelfareSurvivalRateByDate(map) / 100;
        // 育肥成活率
        Double searchFattenSurvivalRateByDate = searchFattenSurvivalRateByDate(map) / 100;

        searchMSY = searchPSYByDate * searchDeliverySurvivalRateByDate * searchWelfareSurvivalRateByDate * searchFattenSurvivalRateByDate;
        return searchMSY;
    }

    // 获取校正后的体重
    private Double getWeightByDayAge(Map<String, String> map) {

        Map<String, Object> getWeightByDayAge = portalMapper.getWeightByDayAge(map);
        Double correctionWeight = 0.0d;
        if (getWeightByDayAge != null) {
            correctionWeight = Double.valueOf(String.valueOf(getWeightByDayAge.get("CORRECTION_WEIGHT")));
        }
        return correctionWeight;
    }

    @Override
    public Map<String, Object> searchGroupSsalesOfRealTimeStatistics() {
        Map<String, Object> groupSsalesOfRealTimeStatistics = new HashMap<>();
        Map<String, Object> searchGroupSsalesOfRealTimeStatistics1 = portalMapper.searchGroupSsalesOfRealTimeStatistics1();
        Map<String, Object> searchGroupSsalesOfRealTimeStatistics2 = portalMapper.searchGroupSsalesOfRealTimeStatistics2();
        groupSsalesOfRealTimeStatistics.put("saleNumLastMonth", searchGroupSsalesOfRealTimeStatistics1.get("SALE_NUM_LAST_MONTH"));
        groupSsalesOfRealTimeStatistics.put("saleNumLastWeek", searchGroupSsalesOfRealTimeStatistics1.get("SALE_NUM_LAST_WEEK"));
        groupSsalesOfRealTimeStatistics.put("saleNumThisDay", searchGroupSsalesOfRealTimeStatistics1.get("SALE_NUM_THIS_DAY"));
        groupSsalesOfRealTimeStatistics.put("saleNumThisMonth", searchGroupSsalesOfRealTimeStatistics1.get("SALE_NUM_THIS_MONTH"));
        groupSsalesOfRealTimeStatistics.put("saleProbability", searchGroupSsalesOfRealTimeStatistics1.get("SALE_PROBABILITY"));
        groupSsalesOfRealTimeStatistics.put("marketingLastMonth", searchGroupSsalesOfRealTimeStatistics1.get("MARKETING_LAST_MONTH"));
        groupSsalesOfRealTimeStatistics.put("marketingLasWeek", searchGroupSsalesOfRealTimeStatistics1.get("MARKETING_LAST_WEEK"));
        groupSsalesOfRealTimeStatistics.put("marketingThisDay", searchGroupSsalesOfRealTimeStatistics1.get("MARKETING_THIS_DAY"));
        groupSsalesOfRealTimeStatistics.put("marketingThisMonth", searchGroupSsalesOfRealTimeStatistics1.get("MARKETING_THIS_MONTH"));
        groupSsalesOfRealTimeStatistics.put("marketingProbability", searchGroupSsalesOfRealTimeStatistics1.get("MARKETING_PROBABILITY"));
        groupSsalesOfRealTimeStatistics.put("saleNumThisYear", searchGroupSsalesOfRealTimeStatistics1.get("SALE_NUM_THIS_YEAR"));
        groupSsalesOfRealTimeStatistics.put("marketingThisYear", searchGroupSsalesOfRealTimeStatistics1.get("MARKETING_THIS_YEAR"));
        // 年度销售总额
        groupSsalesOfRealTimeStatistics.put("marketingTotalPriceThisYear", searchGroupSsalesOfRealTimeStatistics1.get(
                "MARKETING_TOTAL_PRICE_THIS_YEAR"));

        groupSsalesOfRealTimeStatistics.put("companyLastMonth", searchGroupSsalesOfRealTimeStatistics2.get("COMPANY_LAST_MONTH"));
        groupSsalesOfRealTimeStatistics.put("companyLastWeek", searchGroupSsalesOfRealTimeStatistics2.get("COMPANY_LAST_WEEK"));
        // 较上月新增客户率
        groupSsalesOfRealTimeStatistics.put("companyProbability", searchGroupSsalesOfRealTimeStatistics2.get("COMPANY_PROBABILITY"));
        groupSsalesOfRealTimeStatistics.put("companyThisDay", searchGroupSsalesOfRealTimeStatistics2.get("COMPANY_THIS_DAY"));
        groupSsalesOfRealTimeStatistics.put("companyThisMonth", searchGroupSsalesOfRealTimeStatistics2.get("COMPANY_THIS_MONTH"));
        groupSsalesOfRealTimeStatistics.put("companyYear", searchGroupSsalesOfRealTimeStatistics2.get("COMPANY_YEAR"));
        return groupSsalesOfRealTimeStatistics;
    }

    @Override
    public List<Map<String, Object>> searchCompanyAddreed() {
        List<Map<String, Object>> searchCompanyAddreed = portalMapper.searchCompanyAddreed();
        return searchCompanyAddreed;
    }

    @Override
    public List<Map<String, Object>> searchFarmSalePig() {
        /**
         * sql待完善
         */
        List<Map<String, Object>> searchFarmSalePig = portalMapper.searchFarmSalePig();
        return searchFarmSalePig;
    }

    @Override
    public List<Map<String, Object>> searchImperfectPigSaleChange() throws Exception {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        String nowenddate = TimeUtil.getSysDate();
        for (int i = 0; i < TimeUtil.getMM(); i++) {
            map = new HashMap<>();
            String firstDateOfMonth = TimeUtil.dateAddMonth(TimeUtil.getFirstDateOfMonth(nowenddate), -i);
            String lastDateOfMonth = i == 0 ? nowenddate : TimeUtil.dateAddMonth(TimeUtil.getLastDateOfMonth(nowenddate), -i);
            map.put("startDate", firstDateOfMonth);
            map.put("endDate", lastDateOfMonth);
            list.add(map);
        }

        List<Map<String, Object>> searchImperfectPigSaleChange = portalMapper.searchImperfectPigSaleChange(list);
        return searchImperfectPigSaleChange;
    }

    @Override
    public Map<String, Object> searchCustomerAnalysis() {
        List<Map<String, Object>> searchCustomerAnalysis = portalMapper.searchCustomerAnalysis();

        Integer totalSaleNum = 0;
        Double totalTotalWeight = 0d;
        Double totalTotalPrice = 0d;
        for (int i = 0; i < searchCustomerAnalysis.size(); i++) {
            Map<String, Object> m = searchCustomerAnalysis.get(i);
            totalSaleNum = totalSaleNum + Integer.valueOf(String.valueOf(m.get("SALE_NUM")));
            totalTotalWeight = totalTotalWeight + Double.valueOf(String.valueOf(m.get("TOTAL_WEIGHT")));
            totalTotalPrice = totalTotalPrice + Double.valueOf(String.valueOf(m.get("TOTAL_PRICE")));
        }
        Map<String, Object> total = new HashMap<>();
        total.put("totalSaleNum", totalSaleNum);
        total.put("totalTotalWeight", totalTotalWeight);
        total.put("totalTotalPrice", totalTotalPrice);

        Map<String, Object> map = new HashMap<>();
        map.put("list", searchCustomerAnalysis);
        map.put("total", total);
        return map;
    }

    @Override
    public List<Map<String, Object>> searchWeekInYear(Map<String, Object> map) {
        Map<String, Long> mapWeekParam = new HashMap<>();
        mapWeekParam.put("farmId", getFarmId());
        mapWeekParam.put("weekHeight", Long.valueOf(String.valueOf(map.get("weekHeight"))));
        List<Map<String, Object>> searchWeekInYear = portalMapper.searchWeekInYear(mapWeekParam);
        String sysDate = TimeUtil.getSysDate();
        searchWeekInYear.get(searchWeekInYear.size() - 1).put("END_DATE", sysDate);

        return searchWeekInYear;
    }

}
