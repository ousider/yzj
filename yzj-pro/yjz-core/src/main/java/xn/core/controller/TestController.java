package xn.core.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xn.core.cache.cachemanager.CacheManager;
import xn.core.cache.cachemanager.CacheTable;
import xn.core.cache.readonlycache.BasicTableCache;
import xn.core.mapper.base.IParamMapper;
import xn.core.mapper.system.CacheTablesMapper;
import xn.core.service.IParamService;
import xn.core.util.data.StringUtil;

/**
 * @Description:页面组件（下拉框等）取值控制层
 * @author Zhangjc
 * @date 2016年4月14日 上午9:10:02
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    // @Autowired
    // private CodeListMapper codeListMap;
    @Autowired
    private CacheTablesMapper map;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private IParamService paramService;
    /**
     * @Description: 直接操作的sql
     * @author Zhangjc
     * @return
     * @throws Exception
     */
    @RequestMapping("/oper.do")
    @ResponseBody
    public Map<String, Object> oper() throws Exception {
        // http://localhost:8081/pigfarm/test/oper.do
        
        BasicTableCache tableCache = (BasicTableCache) CacheManager.getReadOnlyCache(BasicTableCache.class);
        CacheTable cacheTable = tableCache.getCacheTable(1, "PP_O_PIGPEN");
        Map<String, String> map = cacheTable.getMap(1);
        

        // map.searchById(rowId);
        // List<CacheTablesModel> list = getList(map, " AND FRAM_ID=''");
        // setSql(map, "");

        // String houseName = CacheTableUtil.getPigHouseName("1");

        // String sql = "SELECT TABLE_NAME,CACHE_COND,CACHE_COLUMNS FROM SYS_L_CACHE_TABLES WHERE STATUS='1' AND DELETED_FLAG='0' ";
        // List<CacheTablesModel> cacheList = setSql(map, sql);
        //
        // String sqlP = "SELECT HOUSE_NAME,ROW_ID FROM PP_O_HOUSE WHERE 1=1 and status='1' and deleted_flag='0'";
        // Map<String, String> map = new HashMap<String, String>();
        // map.put("sql", sqlP);
        // List<Map<String, String>> datas = paramMapper.getCacheTableInfos(map);
        // List<CacheTablesModel> list = map.searchToList();

        // List<CacheTablesModel> list = map.searchToList();
        
        // CacheTableUtil.getPigHouseName("");
        // Map<String, String> map1 = CacheUtil.getMapByPrimaryKey("CD_L_BCODE_TYPE", "1");
        // String a1 = map1.get("BCODE_NAME");
        //
        // Map<String, String> map2 = CacheTableUtil.getMapByPrimaryKey("CD_L_BREED", "1");
        // String a2 = map2.get("BREED_NAME");
        //
        // Map<String, String> map3 = CacheTableUtil.getMapByPrimaryKey("CD_L_PIG_CLASS", "1");
        // String a3 = map3.get("PIG_CLASS_NAME");
        //
        // Map<String, String> map4 = CacheTableUtil.getMapByPrimaryKey("CD_L_PIG_HOUSE", "1");
        // String a4 = map4.get("HOUSE_TYPE_NAME");
        //
        // Map<String, String> map5 = CacheTableUtil.getMapByPrimaryKey("CD_M_CODE_TYPE", "1");
        // String a5 = map5.get("TYPE_NAME");
        //
        // Map<String, String> map6 = CacheTableUtil.getMapByPrimaryKey("CD_M_MATERIAL", "1");
        // String a6 = map6.get("MATERIAL_NAME");
        //
        // Map<String, String> map7 = CacheTableUtil.getMapByPrimaryKey("CD_M_MATERIAL_GROUP", "1");
        // String a7 = map7.get("GROUP_NAME");
        //
        // Map<String, String> map8 = CacheTableUtil.getMapByPrimaryKey("CD_M_SETTING", "1");
        // String a8 = map8.get("SETTING_NAME");

        // List<CodeListModel> list = new ArrayList<CodeListModel>();
        //
        // CodeListModel codeList = new CodeListModel();
        // codeList.setRowId(254);
        // codeList.setTypeId(12);
        // codeList.setCodeName("测3");
        // codeList.setDeletedFlag("0");
        // codeList.setStatus("1");
        // codeList.setSortNbr(5);
        // list.add(codeList);
        // codeList = new CodeListModel();
        // codeList.setRowId(255);
        // codeList.setTypeId(12);
        // codeList.setCodeName("测4");
        // codeList.setDeletedFlag("0");
        // codeList.setStatus("1");
        // codeList.setSortNbr(7);
        // list.add(codeList);
        //
        //// SqlSession sqlSession = sqlh.getSqlSession();
        //// CodeListMapper countryMapper = sqlSession.getMapper(CodeListMapper.class);
        // // SqlHelper.get
        // // codeListMap.inserts(list);
        //
        // // codeListMap.searchById(250);
        //
        // // List<CodeListModel> result1 =
        //
        // codeListMap.delete(254);
        // codeListMap.deletes(new long[] { 255, 259 });
        //
        return getReturnMap(map);
    };

    public void test() {
        /* */

        /* setSql(codeListMap, "SELECT * FROM CD_L_CODE_LIST where ROW_ID = 254");
         * getList(codeListMap, "AND ROW_ID = 254");
         * getAllList(codeListMap, "AND type_id = 12");
         * codeListMap.delete(id);
         * codeListMap.deletes(id);
         * codeListMap.searchToList(farmId);
         * codeListMap.searchToList();
         * codeListMap.inserts(records);
         * codeListMap.update(record);
         * codeListMap.updates(records);
         * codeListMap.searchById(rowId);
         * codeListMap.insert(record); */
    }

    public static String getList(String sql, Map<String, String> map) {
        for (String str : map.keySet()) {
            sql = StringUtil.replace(sql, ":" + str.toUpperCase(), map.get(str));
        }
        return sql;
    }

    public static void main(String[] args) {
        // String sql = "SELECT * FROM A WHERE B=:B AND C=:C";
        // Map<String, String> map = new HashMap<String, String>();
        // map.put("B", "TEST");
        // map.put("C", "1");
        // System.out.println(getList(sql, map));

        // double a = 1;
        // double b = 2;
        // double c = a / b;
        // System.out.println(c);
        // StringBuffer ab = new StringBuffer();
        // Long a = new Long(1);
        // Long c = 1l;
        // long b = 1;
        // if (a == c) {
        // System.out.println("c");
        // }
        // if (a.equals(c)) {
        // System.out.println("b");
        // }

        // int c = 5;
        // c = c++;
        // System.out.println(c);
        // String s = new String("xyz");// 创建了几个String Object?
        // String a = "63521";
        // String b = "4354";
        // for (int i = 0; i < a.length(); i++) {
        // for (int j = 0; j < a.length(); j++) {
        // }
        // }
        int f = f(6);
        System.out.println(f);
    }

    public static int f(int x) {
        if(x==1||x==2)
            return 1;
        else
            return f(x - 1) + f(x - 2);
    }
}
