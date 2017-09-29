package xn.core.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import xn.core.exception.CoreBusiException;
import xn.core.exception.Thrower;
import xn.core.exception.expimp.ExpimpException;
import xn.core.util.constants.ExpImpConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.time.TimeUtil;
import xn.core.util.xml.XML;
import xn.core.util.xml.XMLElement;

public class ExcelUtil {

    // ************************************ 下载模板BEGIN*********************************************************//
    /**
     * @Description: 下载模板
     * @author 程彬
     * @param path
     * @param hiddenDate
     * @return
     * @throws Exception
     */
    public static Map<String, String> downExcelTemplate(String path, Map<String, List<Map<String, String>>> hiddenDate) throws Exception {
        Map<String, Object> xmlAttribute = readTemplateXml(path);
        return createExcelTemplate(xmlAttribute, hiddenDate);
    }

    /**
     * @Description:读取配置文件信息
     * @author 程彬
     * @param fileName 配置文件名
     * @return
     * @throws Exception
     */
    public static Map<String, Object> readTemplateXml(String fileName) throws Exception {
        Map<String, String> hteadMap = new HashMap<String, String>();

        Map<String, Object> attributes = new HashMap<>();

        List<Map<String, String>> colList = new ArrayList<Map<String, String>>();
        // 获取服务器中资源文件路径
        String url = ExcelUtil.class.getResource("/").getFile();
        XML xml = null;
        try {
            xml = XML.getInstance(new File(url + "downloadtemplate/production/" + fileName));
        }
        catch (Exception e) {
            Thrower.throwException(CoreBusiException.XMLFARMAT_OR_XMLPATH);
        }

        XMLElement root = xml.getRootElement();

        XMLElement thead = root.getChild(ExpImpConstants.EXCEL_THEAD);

        // 获取到thead名称，用于sheetName
        String sheetName = thead.getAttributeValue(ExpImpConstants.EXCEL_SHEET_NAME);
        hteadMap.put(ExpImpConstants.EXCEL_SHEET_NAME, sheetName);
        colList.add(hteadMap);
        attributes.put(ExpImpConstants.EXCEL_THEAD, colList);

        colList = new ArrayList<>();
        List<XMLElement> cols = thead.getChildren(ExpImpConstants.EXCEL_COL);

        for (XMLElement col : cols) {
            colList.add(col.getAttributes());
        }
        attributes.put(ExpImpConstants.EXCEL_COL, colList);
        colList = new ArrayList<>();

        attributes.put(ExpImpConstants.EXCEL_ROOT, root.getAttributes());
        return attributes;
    }

    /**
     * @Description: 创建下载模板
     * @author 程彬
     * @param xmlattribute
     * @param hiddenDate
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unused", "unchecked" })
    private static Map<String, String> createExcelTemplate(Map<String, Object> xmlAttribute, Map<String, List<Map<String, String>>> hiddenData)
            throws Exception {
        List<Map<String, String>> theadList = (List<Map<String, String>>) xmlAttribute.get("thead");
        List<Map<String, String>> colList = (List<Map<String, String>>) xmlAttribute.get("col");

        File file = new File("");
        String template = "";
        for (int i = 0; i < theadList.size(); i++) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(theadList.get(i).get("name"));
            String filePath = ExcelUtil.class.getResource("/").getFile();
            String filePathResouse = ExcelUtil.class.getResource("").getFile();
            file = new File(filePath + "downloadtemplate/model");
            file.mkdirs();

            // 判断是否需要创建新的sheet工作簿
            boolean sheetCreate = true;
            boolean sheetCreate2 = true;
            // 记录name名称及对应名称的结果集大小
            Map<String, Integer> hiddenEnumNameAndLength = new HashMap<String, Integer>();

            // 按序记录下拉框名称
            List<String> listName = new ArrayList<String>();

            // 记录最大隐藏页,级联关系表(sheet3)最大行数
            int maxRowOfSupAndSonNum = 0;
            // 创建行
            for (int rowNum = 0; rowNum <= 50; rowNum++) {
                HSSFRow row = sheet.createRow(rowNum);
                // 记录enum类型的个数
                int enumNumIndex = 0;
                // 级联个数
                int relevanceNum = 0;

                // 记录supson级联关系下拉框个数
                int supAndSonNum = 0;

                // 统计级联下拉框数量 -- 用于下级下拉框
                int comboboxSupAndSon = 0;

                // 创建行中的cell表格
                for (int colNum = 0; colNum < colList.size(); colNum++) {

                    HSSFCell cell = row.createCell(colNum);

                    if (rowNum == 0) {
                        // 设置列宽度
                        sheet.setColumnWidth(colNum, 256 * Integer.valueOf(colList.get(colNum).get("width")));
                        // 设置标题
                        HSSFCellStyle style = workbook.createCellStyle();

                        // // 设置单元格的使用这种风格被锁定
                        // style.setLocked(true);

                        HSSFFont font = workbook.createFont();
                        font.setFontName("华文楷体");
                        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
                        font.setFontHeightInPoints((short) 12);
                        style.setFont(font);
                        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                        cell.setCellStyle(style);
                        // 首行cell赋值
                        cell.setCellValue(colList.get(colNum).get("value"));

                        if (ExpImpConstants.EXCEL_TYPE_6.equals(colList.get(colNum).get("type"))) {
                            CellRangeAddressList regions = new CellRangeAddressList(1, 65535, colNum, colNum);// 选定一个区域
                            String tenYearAgo = TimeUtil.dateAddYear(TimeUtil.getSysDate(), -10);
                            String currntDate = TimeUtil.getSysDate();
                            String dateFormat = TimeUtil.DATE_FORMAT;
                            DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, tenYearAgo, currntDate,
                                    dateFormat);
                            HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
                            dataValidate.createErrorBox("错误", "请输入格式为'yyyy-MM-dd',并且在" + tenYearAgo + "-" + currntDate + "之间的日期!");
                            sheet.addValidationData(dataValidate);
                        }
                        // 下拉框
                        if (ExpImpConstants.EXCEL_TYPE_2.equals(colList.get(colNum).get("type")) && "name".equals(colList.get(colNum).get("class"))) {

                            enumNumIndex++;
                            // 隐藏页填充的下拉框数据的别名
                            String farmName = colList.get(colNum).get("index");

                            // 第一个enum 创建隐藏的工作页 并赋值
                            // ************************************一次读写开始***********************************************
                            /************************************** 创建普通下拉框开始 **********************************************/
                            // 一次性将所有的下拉框都创建好
                            if (sheetCreate) {
                                sheetCreate = false;
                                /**
                                 * 逻辑问题,有待考究和删除
                                 */
                                // createOrdinaryCombobox(workbook, hiddenData, colList, listName, hiddenEnumNameAndLength, colNum);

                                Sheet sheet2 = workbook.createSheet("DataDictionary");
                                workbook.setSheetHidden(workbook.getSheetIndex("DataDictionary"), true);
                                // 按配置文件顺序记录下拉框名称，为后续从结果集取值填充隐藏页数据做准备
                                for (Map<String, String> col : colList) {
                                    if (ExpImpConstants.EXCEL_TYPE_2.equals(col.get("type")) && "name".equals(col.get("class"))) {
                                        // 隐藏页填充的下拉框数据的别名
                                        String farmName1 = col.get("index");
                                        listName.add(farmName1);
                                        if (col.get("sup") != null) {
                                        }
                                    }
                                }

                                // 记录隐藏页最大行，为后续创建隐藏页行做准备
                                int rowMaxIndex = 0;
                                for (String key : hiddenData.keySet()) {
                                    int size = hiddenData.get(key).size();
                                    if (size == 0) {
                                        Thrower.throwException(CoreBusiException.NOTFOUND_HIDDEN_DATA, colList.get(colNum).get("value") + "或" + key
                                                + "列所需要的数据");
                                    }
                                    hiddenEnumNameAndLength.put(key, size);
                                    rowMaxIndex = rowMaxIndex > size ? rowMaxIndex : size;
                                }

                                // hiddenData结果集Map<String1,List<Map<String2,String3>>> String1 名称 --- String2属性名称 --- String3属性值
                                // 创建隐藏页行,并赋值
                                for (int rowHiddenNum = 0; rowHiddenNum < rowMaxIndex; rowHiddenNum++) {
                                    Row newRow = sheet2.createRow(rowHiddenNum);
                                    // 创建隐藏页列
                                    for (int nameIndex = 0; nameIndex < hiddenData.keySet().size(); nameIndex++) {
                                        // 对应隐藏也中的name-id，按序对应读取其中的name值所对应的下拉框值，用于下拉框取值
                                        List<Map<String, String>> list = hiddenData.get(listName.get(nameIndex));
                                        if (rowHiddenNum < list.size() && nameIndex <= hiddenData.keySet().size()) {
                                            newRow.createCell(nameIndex * 2).setCellValue(list.get(rowHiddenNum).get("name"));
                                            newRow.createCell(nameIndex * 2 + 1).setCellValue(list.get(rowHiddenNum).get("id"));
                                        }
                                    }
                                }
                            }
                            /************************************** 创建普通下拉框结束 **********************************************/

                            // 父级下拉框值，空间名称
                            List<Map<String, String>> listSupNameAndSonHeightMap = new ArrayList<>();
                            // 保存所有级联用到的空间名称
                            List<List<Map<String, String>>> listSupNameAndSonAllNamAndHeight = new ArrayList<>();
                            // 设置关联下拉框 , 创建关系表，并赋值，获取并封装对应数据；
                            if (colList.get(colNum).get("son") != null && sheetCreate2 == true) {
                                sheetCreate2 = false;
                                HSSFSheet sheet3 = workbook.createSheet("SupAndSon");
                                workbook.setSheetHidden(workbook.getSheetIndex("SupAndSon"), true);

                                /************************************** 设置关联下拉框 , 创建关系表 开始 **********************************************/
                                // createCascadeCombobox(workbook, sheet3, hiddenData, colList, colNum, listSupNameAndSonHeightMap,
                                // maxRowOfSupAndSonNum);

                                List<Map<String, String>> listSup = hiddenData.get(colList.get(colNum).get("index"));
                                List<Map<String, String>> listIndex = hiddenData.get(colList.get(colNum).get("son"));
                                // 保存单个级联对应数据
                                List<Map<String, String>> listSupAndSonMap = new ArrayList<>();
                                // 保存所有的级联对应数据
                                List<List<Map<String, String>>> listRelevanceNum = new ArrayList<>();

                                // 定义级联关系用到的名称空间个数
                                int supIndex = 0;
                                for (int colNumStartIndex = colNum; colNumStartIndex < colList.size(); colNumStartIndex++) {

                                    if (colList.get(colNumStartIndex).get("son") != null) {
                                        relevanceNum++;
                                        String son = colList.get(colNumStartIndex).get("son");
                                        String index = colList.get(colNumStartIndex).get("index");
                                        String sup = colList.get(colNumStartIndex).get("sup");
                                        listSup = hiddenData.get(colList.get(colNumStartIndex).get("index"));
                                        listIndex = hiddenData.get(colList.get(colNumStartIndex).get("son"));

                                        int rowIndex = 0;
                                        int supNotHaveSon = 0;
                                        // 遍历父级下拉框值
                                        for (Map<String, String> m : listSup) {
                                            ++supIndex;
                                            Map<String, String> supNameAndSonHeight = new HashMap<>();
                                            String strId = m.get("id");
                                            String name = m.get("name");
                                            Integer sonHight = 0;

                                            // 起始行=起始行+没有栏位的猪舍个数(没有栏位的猪舍独占一行)
                                            Integer startRowNum = 0;
                                            boolean onceHight = true;

                                            // 记录父级菜单对应自己菜单的内容 --猪舍 对 猪栏
                                            List<Map<String, Object>> listSupAndSon = new ArrayList<>();
                                            // 遍历子级下拉框值
                                            for (Map<String, String> mc : listIndex) {
                                                ++startRowNum;
                                                // 级联关系数据
                                                Map<String, String> supAndSonMap = new HashMap<>();
                                                String strSupId = mc.get("supId");
                                                String id = mc.get("id");
                                                String chName = mc.get("name");

                                                // 父级节点 有 子级节点
                                                if (strId.equals(strSupId)) {
                                                    supAndSonMap.put("supIndex", "SUPANDSON" + supIndex);
                                                    supAndSonMap.put("supAndSonName", strSupId + "卐" + chName);
                                                    supAndSonMap.put("supId", strId);
                                                    supAndSonMap.put("supName", name);
                                                    supAndSonMap.put("sonName", chName);
                                                    supAndSonMap.put("sonId", id);
                                                    listSupAndSonMap.add(supAndSonMap);
                                                    // 记录名称用于后面的名称管理器名称的创建
                                                    if (onceHight) {
                                                        supNameAndSonHeight.put("supIndex", "SUPANDSON" + supIndex);
                                                        supNameAndSonHeight.put("supId", strSupId);
                                                        supNameAndSonHeight.put("supAndSonName", strSupId + "卐" + chName);
                                                        supNameAndSonHeight.put("supName", name);
                                                        supNameAndSonHeight.put("startRowNum", String.valueOf(listSupAndSonMap.size()));
                                                        supNameAndSonHeight.put("relevanceGroupIndex", String.valueOf(relevanceNum));
                                                        onceHight = false;
                                                    }
                                                    ++sonHight;
                                                } else
                                                // 遍历到最后
                                                if (startRowNum == listIndex.size()) {
                                                    if (listSupAndSonMap.size() == 0) {
                                                        supAndSonMap = new HashMap<>();
                                                        supAndSonMap.put("supIndex", "SUPANDSON" + supIndex);
                                                        supAndSonMap.put("supAndSonName", strSupId + "卐" + chName);
                                                        supAndSonMap.put("supId", strId);
                                                        supAndSonMap.put("supName", name);
                                                        supAndSonMap.put("sonName", "");
                                                        supAndSonMap.put("sonId", "");
                                                        listSupAndSonMap.add(supAndSonMap);

                                                        supNameAndSonHeight = new HashMap<>();
                                                        supNameAndSonHeight.put("supIndex", "SUPANDSON" + supIndex);
                                                        supNameAndSonHeight.put("supId", strSupId);
                                                        supNameAndSonHeight.put("supAndSonName", strSupId + "卐" + chName);
                                                        supNameAndSonHeight.put("supName", name);
                                                        supNameAndSonHeight.put("startRowNum", String.valueOf(listSupAndSonMap.size()));
                                                        supNameAndSonHeight.put("relevanceGroupIndex", String.valueOf(relevanceNum));
                                                    } else if (listSupAndSonMap.size() > 0) {
                                                        // 父级节点 没有 子级节点
                                                        Map<String, String> lastSumAndSonMap = listSupAndSonMap.get(listSupAndSonMap.size() - 1);
                                                        if (!name.equals(lastSumAndSonMap.get("supName"))) {
                                                            supAndSonMap = new HashMap<>();
                                                            supAndSonMap.put("supIndex", "SUPANDSON" + supIndex);
                                                            supAndSonMap.put("supAndSonName", strSupId + "卐" + chName);
                                                            supAndSonMap.put("supId", strId);
                                                            supAndSonMap.put("supName", name);
                                                            supAndSonMap.put("sonName", "");
                                                            supAndSonMap.put("sonId", "");
                                                            listSupAndSonMap.add(supAndSonMap);

                                                            supNameAndSonHeight = new HashMap<>();
                                                            supNameAndSonHeight.put("supIndex", "SUPANDSON" + supIndex);
                                                            supNameAndSonHeight.put("supId", strSupId);
                                                            supNameAndSonHeight.put("supAndSonName", strSupId + "卐" + chName);
                                                            supNameAndSonHeight.put("supName", name);
                                                            supNameAndSonHeight.put("startRowNum", String.valueOf(listSupAndSonMap.size()));
                                                            supNameAndSonHeight.put("relevanceGroupIndex", String.valueOf(relevanceNum));
                                                        }
                                                    }
                                                }
                                            }
                                            supNotHaveSon = sonHight == 0 ? supNotHaveSon + 1 : supNotHaveSon;
                                            supNameAndSonHeight.put("sonHight", String.valueOf(sonHight = sonHight == 0 ? 1 : sonHight));
                                            int maxRow = Integer.valueOf(supNameAndSonHeight.get("startRowNum")) + sonHight;
                                            maxRowOfSupAndSonNum = maxRow >= maxRowOfSupAndSonNum ? maxRow : maxRowOfSupAndSonNum;

                                            // 记录空间名称
                                            listSupNameAndSonHeightMap.add(supNameAndSonHeight);

                                        }
                                        listRelevanceNum.add(listSupAndSonMap);
                                        listSupAndSonMap = new ArrayList<Map<String, String>>();

                                        // 记录空间名称
                                        listSupNameAndSonAllNamAndHeight.add(listSupNameAndSonHeightMap);
                                        listSupNameAndSonHeightMap = new ArrayList<>();

                                    }
                                }
                                // 创建隐藏页 上下级 关系表
                                for (int supAndSonIndex = 0; supAndSonIndex < maxRowOfSupAndSonNum; supAndSonIndex++) {
                                    HSSFRow createRow = sheet3.createRow(supAndSonIndex);
                                    for (int num = 1; num <= listRelevanceNum.size(); num++) {
                                        listSupAndSonMap = listRelevanceNum.get(num - 1);
                                        if (supAndSonIndex < listSupAndSonMap.size()) {
                                            Map<String, String> map = listSupAndSonMap.get(supAndSonIndex);
                                            createRow.createCell(num * 5 - 5).setCellValue(map.get("supName"));
                                            createRow.createCell(num * 5 - 4).setCellValue(map.get("supIndex"));
                                            createRow.createCell(num * 5 - 3).setCellValue(map.get("supAndSonName"));
                                            createRow.createCell(num * 5 - 2).setCellValue(map.get("sonName"));
                                            createRow.createCell(num * 5 - 1).setCellValue(map.get("sonId"));
                                        }
                                    }
                                }

                                /************************************** 设置关联下拉框 , 创建关系表 结束 **********************************************/
                            }

                            // ************************************一次读写结束***********************************************

                            // 获取对应下拉框下拉框值大小
                            int hiddenLength = 0;
                            if (hiddenEnumNameAndLength.size() > 0 || !hiddenEnumNameAndLength.isEmpty()) {
                                hiddenLength = hiddenEnumNameAndLength.get(farmName);
                            }
                            // 设置下拉框数据的有效性
                            if (colList.get(colNum).get("sup") == null) {

                                // 设置下拉框值--名称(普通下拉框和父级下拉框相同处理、子级下拉框特殊处理)
                                creatExcelNameLists(workbook, "DataDictionary", farmName, enumNumIndex, 1, hiddenLength);
                                // 设置下拉框
                                validationData(sheet, farmName, colNum);

                                // 父级下拉框，分别按照父级菜单设置名称，为下级的引用做准备

                                /**
                                 * && relevanceNum ==1 删除
                                 */
                                if (colList.get(colNum).get("son") != null) {
                                    // Integer integer = hiddenEnumNameAndLength.get(colList.get(colNum).get("son"));
                                    for (int supAndSonNameNum = 0; supAndSonNameNum < listSupNameAndSonAllNamAndHeight.size(); supAndSonNameNum++) {
                                        listSupNameAndSonHeightMap = listSupNameAndSonAllNamAndHeight.get(supAndSonNameNum);

                                        for (int cc = 0; cc < listSupNameAndSonHeightMap.size(); cc++) {
                                            Map<String, String> supNameAndSonId = listSupNameAndSonHeightMap.get(cc);
                                            String sonHight = supNameAndSonId.get("sonHight");
                                            String supName = supNameAndSonId.get("supName");
                                            String supFarmatName = supNameAndSonId.get("supIndex");
                                            String supAndSonName = supNameAndSonId.get("supAndSonName");
                                            String supId = supNameAndSonId.get("supId");
                                            String startRowNum = supNameAndSonId.get("startRowNum");
                                            String relevanceGroupIndex = supNameAndSonId.get("relevanceGroupIndex");

                                            // 设置下拉框值--名称
                                            creatExcelSupAndSonNameLists(workbook, "SupAndSon", supFarmatName, Integer.valueOf(relevanceGroupIndex),
                                                    Integer.valueOf(startRowNum), Integer.valueOf(startRowNum) + Integer.valueOf(sonHight) - 1);
                                        }
                                    }
                                }
                            } else
                            // 子级下拉框
                            if (colList.get(colNum).get("sup") != null) {
                                // 读到第comboboxSupAndSon++个子级级联下拉框
                                comboboxSupAndSon++;
                                // 设置下拉框
                                Integer intFarmule = Integer.valueOf(changeValue(farmName)) - 2;
                                // INDIRECT(VLOOKUP($C1,SUPANDSON!$A$1:$B$670,2,FALSE))
                                // 设置下拉框数据 -- 公式
                                String farm = "INDIRECT(VLOOKUP($" + changeValue(intFarmule) + (rowNum + 1) + ",SupAndSon!$" + changeValue(
                                        comboboxSupAndSon * 5 - 5) + "$1:$" + changeValue(comboboxSupAndSon * 5 - 4) + "$" + maxRowOfSupAndSonNum
                                        + ",2,FALSE))";
                                validationData(sheet, farm, colNum);
                            }
                        }

                    } else {

                        // 下拉框附属id框
                        if ("id".equals(colList.get(colNum).get("class")) && ExpImpConstants.EXCEL_TYPE_2.equals(colList.get(colNum).get("type"))) {
                            // 隐藏id值
                            sheet.setColumnHidden(colNum, true);
                            String farmName = colList.get(colNum - 1).get("index");
                            enumNumIndex++;

                            int hiddenLength = 0;
                            if (hiddenEnumNameAndLength.size() > 0 || !hiddenEnumNameAndLength.isEmpty()) {
                                hiddenLength = hiddenEnumNameAndLength.get(farmName);
                            }
                            // String indexindex = colList.get(colNum).get("index");
                            // id框赋值
                            getCellValue(cell, rowNum, colList.get(colNum).get("index"), enumNumIndex, hiddenLength);

                            if (colList.get(colNum - 1).get("sup") != null) {
                                // 记录supson级联关系下拉框的个数 supAndSonNum
                                // 取得下级下拉框对应的 id
                                getSonCellValue(cell, rowNum, supAndSonNum, colNum, maxRowOfSupAndSonNum);
                                supAndSonNum++;
                            }

                        } else if (ExpImpConstants.EXCEL_TYPE_0.equals(colList.get(colNum).get("type"))) {

                            HSSFCellStyle cellStyle = workbook.createCellStyle();
                            HSSFDataFormat format = workbook.createDataFormat();
                            // cellStyle.setLocked(false);
                            String formatNum = colList.get(colNum).get("format");
                            String maxNum = colList.get(colNum).get("formatMax");
                            if (formatNum.equals("#")) {
                                // 整数 特殊处理 最大整数最好写到xml文件中可以对应每一列
                                validationIntegerData(sheet, colNum, maxNum);
                            } else {
                                cellStyle.setDataFormat(format.getFormat(formatNum));
                                cell.setCellStyle(cellStyle);
                            }

                        } else {
                            // 其他定义为String类型
                            HSSFCellStyle cellStyle = workbook.createCellStyle();
                            HSSFDataFormat format = workbook.createDataFormat();
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cellStyle.setDataFormat(format.getFormat("@"));
                            // // 其他
                            // HSSFCellStyle cellStyle = workbook.createCellStyle();
                            // cellStyle.setLocked(false);
                            // cell.setCellStyle(cellStyle);

                        }

                    }
                }
            }

            // sheet.protectSheet("123123");
            template = file + "\\" + theadList.get(i).get(ExpImpConstants.EXCEL_SHEET_NAME) + ".xls";
            // 将工作簿保存到文件
            FileOutputStream fos = new FileOutputStream(template);
            workbook.write(fos);
            fos.close();

        }
        // excel信息
        Map<String, String> excelMap = (Map<String, String>) xmlAttribute.get(ExpImpConstants.EXCEL_ROOT);
        Map<String, String> result = new HashMap<>();
        result.put(ExpImpConstants.EXCEL_NAME, excelMap.get(ExpImpConstants.EXCEL_NAME) + ".xls");
        result.put(ExpImpConstants.EXCEL_FILE_NAME, template);
        return result;
    }

    /**
     * @Description: 设置名称
     * @author 程彬
     * @param workbook 工作簿
     * @param sheetName 隐藏页工作表名称
     * @param farmatName 名称
     * @param enumNumIndex 下拉框个数 用于计算下拉框值起始位置
     * @param startRowNum 名称空间起始行
     * @param hiddenLength 行数 用于计算下拉框值结束位置 -- 该列有效数值的最大行号
     */
    private static void creatExcelNameLists(Workbook workbook, String sheetName, String farmatName, int enumNumIndex, int startRowNum,
            int hiddenLength) {
        startRowNum = startRowNum == 0 ? 1 : startRowNum;
        Name name;
        // 创建名称
        name = workbook.createName();
        // 设置名称
        name.setNameName(farmatName);

        // 判断是否有下拉框值
        hiddenLength = hiddenLength == 0 ? 1 : hiddenLength;
        // 设置公式,范围
        String formula = sheetName + "!$" + changeValue((enumNumIndex - 1) * 2) + "$" + startRowNum + ":$" + changeValue((enumNumIndex - 1) * 2) + "$"
                + hiddenLength;

        // 将公式应用于该名称
        name.setRefersToFormula(formula);
    }

    /**
     * @Description: 设置supandson的名称
     * @author 程彬
     * @param workbook 工作簿
     * @param sheetName 隐藏页工作表名称
     * @param farmatName 名称
     * @param enumNumIndex 级联关系个数 用于计算下拉框值起始位置
     * @param startRowNum 名称空间起始行
     * @param hiddenLength 行数 用于计算下拉框值结束位置 -- 该列有效数值的最大行号
     */
    private static void creatExcelSupAndSonNameLists(Workbook workbook, String sheetName, String farmatName, int enumNumIndex, int startRowNum,
            int hiddenLength) {
        startRowNum = startRowNum == 0 ? 1 : startRowNum;
        Name name;
        // 创建名称
        name = workbook.createName();
        // 设置名称
        name.setNameName(farmatName);

        // 判断是否有下拉框值
        hiddenLength = hiddenLength == 0 ? 1 : hiddenLength;
        // 设置公式,范围
        String formula = sheetName + "!$" + changeValue((enumNumIndex) * 5 - 2) + "$" + startRowNum + ":$" + changeValue((enumNumIndex) * 5 - 2) + "$"
                + hiddenLength;

        // 将公式应用于该名称
        name.setRefersToFormula(formula);

    }

    // 对应下拉框从隐藏页获取name的id值
    private static void getCellValue(Cell newCell, int rowNum, String index, int enumNumIndex, int hiddenLength) {

        // 判断是否有下拉框值
        hiddenLength = hiddenLength == 0 ? 1 : hiddenLength;
        String stringIndex = changeValue(index);
        int intIdex = Integer.valueOf(stringIndex);
        String indexBefor1 = (changeValue(intIdex - 1));

        String biaodashi = "VLOOKUP($" + indexBefor1 + (rowNum + 1) + ",DataDictionary!$" + changeValue((enumNumIndex - 1) * 2) + "$1:$"
                + changeValue(((enumNumIndex * 2) - 1)) + "$" + hiddenLength + "," + 2 + ",FALSE)";
        newCell.setCellFormula(biaodashi);
    }

    /**
     * 对应下拉框从隐藏页获取name的id值
     * 
     * @author 程彬
     * @param newCell 单元格
     * @param rowNum 行号
     * @param supAndSonNum 第 supAndSonNum 个级联
     * @param colNum 级联下拉框 id 位置
     * @param hiddenLength 行数 用于计算下拉框值结束位置 -- 该列有效数值的最大行号
     */
    private static void getSonCellValue(Cell newCell, int rowNum, int supAndSonNum, int colNum, int hiddenLength) {
        String change1 = changeValue(colNum - 2);
        String change2 = changeValue(colNum - 1);
        // 判断是否有下拉框值
        hiddenLength = hiddenLength == 0 ? 1 : hiddenLength;
        // VLOOKUP($D2&"卐"&$E2,SupAndSon!$C$1:$E$664,3,FALSE)
        String biaodashi = "VLOOKUP($" + change1 + (rowNum + 1) + "&\"卐\"&$" + change2 + (rowNum + 1) + ",SupAndSon!$" + changeValue(5 * supAndSonNum
                + 2) + "$1:$" + changeValue(5 * supAndSonNum + 4) + "$" + hiddenLength + ",3,FALSE)";
        newCell.setCellFormula(biaodashi);
    }

    // 字母数字转换 0-A 25-Z
    private static String changeValue(Object obj) {
        String resultChange = "";
        String type = obj.getClass().getName();
        if ("java.lang.Integer".equals(type)) {
            Double temp = Double.valueOf((int) obj);
            if ((temp + 1) / 26 > 1) {
                int tt = (int) obj;
                String changeValue = changeValue((tt - 26) / 26);
                String changeValue2 = changeValue(tt % 26);
                resultChange = changeValue + changeValue2;

                return resultChange;
            } else {
                int intObj = (int) obj + 65;
                resultChange = ((char) intObj) + "";
            }
        } else if ("java.lang.String".equals(type)) {
            String strobj = (String) obj;
            char[] charArray = strobj.toCharArray();
            if (charArray.length == 2) {
                resultChange = 26 * (Integer.valueOf(changeValue(charArray[0])) + 1) + Integer.valueOf(changeValue(charArray[1])) + "";

                return resultChange;
            } else {
                resultChange = (((int) ((String) obj).charAt(0)) - 65) + "";
            }
        } else if ("java.lang.Character".equals(type)) {
            resultChange = (((int) (String.valueOf(obj)).charAt(0)) - 65) + "";
        }

        return resultChange;
    }

    // ************************************ 下载模板END*********************************************************//

    /**
     * @Description: excel导出
     * @author 程彬
     * @param path xml文件名
     * @param hiddenDate 查询的结果集，用于下拉框的数据
     * @throws Exception
     */
    public String exmport(String path, Map<String, List<Map<String, String>>> hiddenDate) throws Exception {
        Map<String, List<Map<String, String>>> xmlattribute = readXml(path);
        return CreateFile(xmlattribute, hiddenDate);
    }

    // 创建文件
    @SuppressWarnings({ "unused" })
    private String CreateFile(Map<String, List<Map<String, String>>> xmlattribute, Map<String, List<Map<String, String>>> hiddenDate)
            throws Exception {
        List<Map<String, String>> theadList = xmlattribute.get("thead");
        List<Map<String, String>> colList = xmlattribute.get("col");
        File file = new File("");
        String template = "";
        for (int i = 0; i < theadList.size(); i++) {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(theadList.get(i).get("name"));
            String filePath = ExcelUtil.class.getResource("/").getFile();
            String filePathResouse = ExcelUtil.class.getResource("").getFile();
            file = new File(filePath + "downloadtemplate/model");
            file.mkdirs();

            // 判断是否需要创建新的sheet工作簿
            boolean sheetCeate = true;
            // 记录name名称及对应名称的结果集大小
            Map<String, Integer> hiddenEnumNameAndLength = new HashMap<String, Integer>();

            // 按序记录下拉框名称
            List<String> listName = new ArrayList<String>();

            // 创建行
            for (int rowNum = 0; rowNum <= 50; rowNum++) {
                HSSFRow row = sheet.createRow(rowNum);
                // 记录enum类型的个数
                int enumNumIndex = 0;

                // 创建行中的cell表格
                for (int colNum = 0; colNum < colList.size(); colNum++) {

                    HSSFCell cell = row.createCell(colNum);

                    if (rowNum == 0) {
                        // 设置列宽度
                        sheet.setColumnWidth(colNum, 256 * Integer.valueOf(colList.get(colNum).get("width")));
                        // 设置标题
                        HSSFCellStyle style = workbook.createCellStyle();

                        // // 设置单元格的使用这种风格被锁定
                        // style.setLocked(true);

                        HSSFFont font = workbook.createFont();
                        font.setFontName("华文楷体");
                        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
                        font.setFontHeightInPoints((short) 12);
                        style.setFont(font);
                        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

                        cell.setCellStyle(style);
                        // 首行cell赋值
                        cell.setCellValue(colList.get(colNum).get("value"));

                        if ("6".equals(colList.get(colNum).get("type"))) {
                            // 选定一个区域
                            CellRangeAddressList regions = new CellRangeAddressList(1, 65535, colNum, colNum);
                            String tenYearAgo = TimeUtil.dateAddYear(TimeUtil.getSysDate(), -10);
                            DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, tenYearAgo, TimeUtil
                                    .getSysDate(), "YYYY-MM-DD");
                            HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
                            dataValidate.createErrorBox("错误", "你必须输入一个格式为“年-月-日”的时间！");
                            sheet.addValidationData(dataValidate);
                        }
                        // 下拉框
                        if ("2".equals(colList.get(colNum).get("type")) && "name".equals(colList.get(colNum).get("class"))) {
                            enumNumIndex++;
                            // 隐藏页填充的下拉框数据的别名
                            String farmName = colList.get(colNum).get("index");

                            // 第一个enum 创建隐藏的工作页
                            Sheet sheet2 = null;
                            if (sheetCeate) {
                                sheetCeate = false;
                                sheet2 = workbook.createSheet("DataDictionary");
                                workbook.setSheetHidden(workbook.getSheetIndex("DataDictionary"), true);

                                // 获取结果集
                                Map<String, List<Map<String, String>>> hiddenData = hiddenDate;

                                int rowMaxIndex = 0;
                                for (String key : hiddenData.keySet()) {

                                    int size = hiddenData.get(key).size();
                                    hiddenEnumNameAndLength.put(key, size);
                                    rowMaxIndex = rowMaxIndex > size ? rowMaxIndex : size;
                                }
                                for (Map<String, String> col : colList) {
                                    if ("2".equals(col.get("type")) && "name".equals(col.get("class"))) {
                                        // 隐藏页填充的下拉框数据的别名
                                        String farmName1 = col.get("index");

                                        listName.add(farmName1);
                                    }
                                }
                                // hiddenData结果集Map<String1,List<Map<String2,String3>>> String1 名称 --- String2属性名称 --- String3属性值
                                // 创建隐藏页行
                                for (int rowHiddenNum = 0; rowHiddenNum < rowMaxIndex; rowHiddenNum++) {
                                    Row newRow = sheet2.createRow(rowHiddenNum);

                                    // 创建隐藏页列
                                    for (int nameIndex = 0; nameIndex < hiddenData.keySet().size(); nameIndex++) {

                                        // 对应隐藏也中的name-id，按序对应读取其中的name值所对应的下拉框值，用于下拉框取值
                                        List<Map<String, String>> list = hiddenData.get(listName.get(nameIndex));

                                        if (rowHiddenNum < list.size() && nameIndex <= hiddenData.keySet().size()) {

                                            newRow.createCell(nameIndex * 2).setCellValue(list.get(rowHiddenNum).get("name"));
                                            newRow.createCell(nameIndex * 2 + 1).setCellValue(list.get(rowHiddenNum).get("id"));
                                        }
                                    }

                                }

                            }

                            int hiddenLength = hiddenEnumNameAndLength.get(farmName);
                            // 设置下拉框值--名称
                            creatExcelNameList(workbook, farmName, enumNumIndex, hiddenLength);

                            // 设置下拉框
                            validationData(sheet, farmName, colNum);
                        }

                    } else {

                        // id框
                        if ("id".equals(colList.get(colNum).get("class")) && "2".equals(colList.get(colNum).get("type"))) {
                            // 隐藏id框
                            sheet.setColumnHidden(colNum, true);
                            String farmName = colList.get(colNum - 1).get("index");
                            enumNumIndex++;

                            int hiddenLength = hiddenEnumNameAndLength.get(farmName);

                            String indexindex = colList.get(colNum).get("index");

                            // id框赋值
                            getIdCellValue(cell, rowNum, colList.get(colNum).get("index"), enumNumIndex, hiddenLength);

                        } else if (ExpImpConstants.EXCEL_TYPE_0.equals(colList.get(colNum).get("type"))) {

                            HSSFCellStyle cellStyle = workbook.createCellStyle();
                            HSSFDataFormat format = workbook.createDataFormat();
                            // cellStyle.setLocked(false);
                            cellStyle.setDataFormat(format.getFormat(colList.get(colNum).get("format")));
                            cell.setCellStyle(cellStyle);

                        } else {
                            // 其他定义为String类型
                            HSSFCellStyle cellStyle = workbook.createCellStyle();
                            HSSFDataFormat format = workbook.createDataFormat();
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cellStyle.setDataFormat(format.getFormat("@"));
                            // // 其他
                            // HSSFCellStyle cellStyle = workbook.createCellStyle();
                            // cellStyle.setLocked(false);
                            // cell.setCellStyle(cellStyle);

                        }

                    }
                }
            }

            // sheet.protectSheet("123123");
            // 将工作簿保存到文件
            FileOutputStream fos = new FileOutputStream(file + "\\" + theadList.get(i).get("sheetName") + ".xls");
            template = file + "\\" + theadList.get(i).get("sheetName") + ".xls";
            workbook.write(fos);
            fos.close();

        }
        return template;
    }

    // 获取下拉框对应id
    private void getIdCellValue(Cell newCell, int rowNum, String index, int enumNumIndex, int hiddenLength) {
        // 判断是否有下拉框值
        hiddenLength = hiddenLength == 0 ? 1 : hiddenLength;
        String stringIndex = change(index);
        int intIdex = Integer.valueOf(stringIndex);
        String indexBefor1 = (change(intIdex - 1));

        String biaodashi = "VLOOKUP($" + indexBefor1 + (rowNum + 1) + ",DataDictionary!$" + change((enumNumIndex - 1) * 2) + "$1:$" + change(
                ((enumNumIndex * 2) - 1)) + "$" + hiddenLength + "," + 2 + ",FALSE)";
        newCell.setCellFormula(biaodashi);
    }

    /**
     * @Description: 整数限制
     * @author 程彬
     * @param sheet 工作表
     * @param maxNum 最大整数
     * @param cellIndex 下拉框所在列
     */
    private static void validationIntegerData(Sheet sheet, int cellIndex, String maxNum) {
        maxNum = maxNum == null ? "25" : maxNum;
        CellRangeAddressList regions = new CellRangeAddressList(1, 65535, cellIndex, cellIndex);
        DVConstraint constraint = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.INTEGER, DVConstraint.OperatorType.BETWEEN, "0",
                maxNum);
        HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
        dataValidate.createErrorBox("错误", "你必须输入0-" + maxNum + "的整数");
        sheet.addValidationData(dataValidate);
    }

    /**
     * @Description: 设置下拉框的数据有效性 主要是给子级下拉框用 获取id使用
     * @author 程彬
     * @param sheet 工作表
     * @param farmatName 限制名称
     * @param cellIndex 下拉框所在列
     */
    private static void validationData(Sheet sheet, String farmatName, int cellIndex) {
        CellRangeAddressList regions = new CellRangeAddressList(1, 65535, cellIndex, cellIndex);
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(farmatName);
        HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidate);
    }

    /**
     * @Description: 设置名称
     * @author 程彬
     * @param workbook 工作簿
     * @param farmatName 名称
     * @param enumNumIndex enum个数 用于计算下拉框值起始位置
     * @param hiddenLength 行数 用于计算下拉框值结束位置 -- 该列有效数值的最大行号
     */
    private void creatExcelNameList(Workbook workbook, String farmatName, int enumNumIndex, int hiddenLength) {
        Name name;
        // 创建名称
        name = workbook.createName();
        // 设置名称
        name.setNameName(farmatName);

        // 判断是否有下拉框值
        hiddenLength = hiddenLength == 0 ? 1 : hiddenLength;

        // 设置公式,范围
        String formula = "DataDictionary!$" + change((enumNumIndex - 1) * 2) + "$" + 1 + ":$" + change((enumNumIndex - 1) * 2) + "$" + hiddenLength;

        // 将公式应用于该名称
        name.setRefersToFormula(formula);
    }

    private String change(Object obj) {
        String resultChange = "";
        String type = obj.getClass().getName();
        if ("java.lang.Integer".equals(type)) {
            int intObj = (int) obj + 65;
            resultChange = ((char) intObj) + "";
        } else if ("java.lang.String".equals(type)) {
            resultChange = (((int) ((String) obj).charAt(0)) - 65) + "";
        }
        return resultChange;
    }

    /**
     * @Description: 读取配置文件信息
     * @author 程彬
     * @param fileName 文件名称，含后缀
     * @return
     * @throws Exception
     */
    public Map<String, List<Map<String, String>>> readXml(String fileName) throws Exception {
        Map<String, String> hteadMap = new HashMap<String, String>();

        Map<String, List<Map<String, String>>> attributes = new HashMap<String, List<Map<String, String>>>();

        List<Map<String, String>> colList = new ArrayList<Map<String, String>>();
        // 获取服务器中资源文件路径
        String url = ExcelUtil.class.getResource("/").getFile();
        XML xml = null;
        try {
            xml = XML.getInstance(new File(url + "/downloadtemplate/production/" + fileName));
        }
        catch (Exception e) {
            Thrower.throwException(CoreBusiException.XML_EXCEPTION, "路径");
            System.out.println("路径问题！");
            return null;
        }

        XMLElement root = xml.getRootElement();
        XMLElement thead = root.getChild("thead");

        // 获取到thead名称，用于sheetName
        String sheetName = thead.getAttributeValue("name");
        hteadMap.put("name", sheetName);
        colList.add(hteadMap);
        attributes.put("thead", colList);
        colList = new ArrayList<Map<String, String>>();
        List<XMLElement> cols = thead.getChildren("col");

        for (XMLElement col : cols) {
            Map<String, String> colMap = new HashMap<String, String>();

            colMap.put("index", col.getAttributeValue("index"));
            colMap.put("width", col.getAttributeValue("width"));
            colMap.put("type", col.getAttributeValue("type"));
            colMap.put("key", col.getAttributeValue("key"));
            colMap.put("value", col.getAttributeValue("value"));

            if ("0".equals(col.getAttributeValue("type"))) {
                colMap.put("format", col.getAttributeValue("format"));
            }
            // enum类型
            if ("2".equals(col.getAttributeValue("type"))) {
                colMap.put("class", col.getAttributeValue("class"));
                if ("name".equals(col.getAttributeValue("class"))) {
                    colMap.put("name", col.getAttributeValue("index"));
                    colMap.put("path", col.getAttributeValue("path"));
                }
            }

            colList.add(colMap);
        }
        attributes.put("col", colList);
        colList = new ArrayList<Map<String, String>>();

        return attributes;
    }

    // ************************************ 上传 excel 文件数据 *********************************************************//
    /**
     * @Description: 上传文件到服务器
     * @author 程彬
     * @param request
     * @param fileName 配置文件名
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> excelExport(HttpServletRequest request, String fileName) throws Exception {
        String path = "";
        List<Map<String, Object>> excelData = new ArrayList<>();
        String filePath = ExcelUtil.class.getResource("/").getFile();
        filePath = filePath.substring(0, filePath.lastIndexOf(":") + 1);

        // 转换request对象
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = (DefaultMultipartHttpServletRequest) request;

        // 获取上传文件
        MultipartFile file = defaultMultipartHttpServletRequest.getFile("uploadFile");
        // 文件名(包括扩展名)
        String originalFilename = file.getOriginalFilename();
        if (StringUtil.isBlank(originalFilename)) {
            Thrower.throwException(ExpimpException.NO_EXCEL_FILE_EXPORT);
        }

        // 获取文件后缀
        String lowerCase = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!".xls".equals(lowerCase)) {
            Thrower.throwException(ExpimpException.NOT_FORMAT_EXCEL_FILE_EXPORT);
        }

        InputStream inputStream = file.getInputStream();
        // 创建文件夹
        String uuidFileName = "uploadFile" + lowerCase;
        File newfile = new File(filePath + "/Upload");

        // 开始保存文件到服务器
        if (!originalFilename.equals("")) {
            // 创建文件夹
            newfile.mkdirs();
            FileOutputStream outputStream = new FileOutputStream(newfile + "/" + uuidFileName);
            // 每次读8K字节
            byte[] buffer = new byte[8192];
            int count = 0;
            // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
            while ((count = inputStream.read(buffer)) > 0) {
                // 向服务端文件写入字节流
                outputStream.write(buffer, 0, count);
            }
            // 关闭FileOutputStream对象
            outputStream.close();
            // InputStream对象
            inputStream.close();
            path = newfile + "/" + uuidFileName;

            excelData = insertExcelData(path, fileName);
        }
        return excelData;

    }

    /**
     * @Description: 根据xml配置文件对应excel字段，读取数据
     * @author 程彬
     * @param path 上传文件全路径
     * @param fileName 配置文件名
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public static List<Map<String, Object>> insertExcelData(String path, String fileName) throws Exception {
        // 导入excel表中的数据
        HSSFWorkbook workbook = null;
        InputStream fileinput = new FileInputStream(path);
        POIFSFileSystem poifsFileSystem;
        // 获取配置文件中的数据
        ExcelUtil excelutil = new ExcelUtil();
        Map<String, List<Map<String, String>>> readXml = excelutil.readXml(fileName);
        // 获取配置文件中所有的col数据
        List<Map<String, String>> listCol = readXml.get("col");
        List<Map<String, String>> listThead = readXml.get("thead");

        try {
            poifsFileSystem = new POIFSFileSystem(fileinput);
            workbook = new HSSFWorkbook(poifsFileSystem);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        int sheetIndex = workbook.getSheetIndex(listThead.get(0).get("name"));

        if (sheetIndex == -1) {
            Thrower.throwException(CoreBusiException.EXCEPTION_DATA, "excel工作簿中找不到表名为：" + listThead.get(0).get("name") + "的工作表");
        }

        HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum > 0) {
            // 判断是否继续执行
            if (sheet.getRow(0).getLastCellNum() != listCol.size()) {
                Thrower.throwException(CoreBusiException.EXCEPTION_DATA, "excel模板不是最新版，请下载最新版excel模板后倒入数据！");
                System.out.println("excel文件中的字段与xml配置文件长度不一致！");
                return null;
            }
            List<Map<String, Object>> listExcel = new ArrayList<Map<String, Object>>();
            HSSFRow row = sheet.getRow(0);
            short lastCellNum = row.getLastCellNum();
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                HSSFRow newRow = sheet.getRow(rowNum);
                Map<String, Object> map = new HashMap<String, Object>();

                if (StringUtil.isBlank(readCell(listCol, newRow.getCell(0), 0))) {
                    continue;
                }

                for (int cellNum = 0; cellNum < sheet.getRow(rowNum).getLastCellNum(); cellNum++) {
                    String celldata = "";
                    HSSFCell newCell = newRow.getCell(cellNum);
                    if (newCell == null || newCell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        continue;
                    }

                    celldata = readCell(listCol, newCell, cellNum);

                    String key = listCol.get(cellNum).get("key");
                    if (StringUtil.isNonBlank(key) && StringUtil.isNonBlank(celldata)) {
                        map.put(key, celldata);
                    }
                }
                if (!map.isEmpty()) {
                    listExcel.add(map);
                }
            }
            return listExcel;
        }
        return null;
    }

    // 获取cell值
    public static String readCell(List<Map<String, String>> listCol, Cell newCell, int cellNum) {
        String celldata = "";
        if (newCell == null) {
            return celldata;
        }

        // 根据cell中的类型来输出数据
        int cellType = newCell.getCellType();
        switch (cellType) {
        case HSSFCell.CELL_TYPE_NUMERIC:

            if (HSSFDateUtil.isCellDateFormatted(newCell)) {
                // 格式时间格式数据
                celldata = TimeUtil.format(newCell.getDateCellValue(), TimeUtil.DATE_FORMAT);
            } else {
                // 获取当前cell的格式类型
                celldata = String.valueOf(newCell.getNumericCellValue());
                double vale = newCell.getNumericCellValue();
                String format = listCol.get(cellNum).get("format");
                celldata = formatNumeric(vale, format);

            }
            break;
        case HSSFCell.CELL_TYPE_STRING:
            celldata = newCell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            celldata = String.valueOf(newCell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            break;
        case HSSFCell.CELL_TYPE_ERROR:
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            try {
                celldata = newCell.getStringCellValue();
                break;
            }
            catch (IllegalStateException e) {
                try {
                    if (HSSFDateUtil.isCellDateFormatted(newCell)) {
                        // 格式时间格式数据
                        celldata = TimeUtil.format(newCell.getDateCellValue(), TimeUtil.DATE_FORMAT);
                    } else {
                        celldata = String.valueOf(newCell.getNumericCellValue());
                        celldata = formatNumeric(newCell.getNumericCellValue(), listCol.get(cellNum).get("format"));
                    }
                    break;
                }
                catch (Exception e1) {
                    break;
                }
            }
        default:
            break;
        }
        return celldata;
    }

    public static String formatNumeric(Double value, String format) {
        if (StringUtil.isBlank(format)) {
            format = "#";
        }
        DecimalFormat formater = new DecimalFormat(format);
        return formater.format(value);
    }

    // **************************************BEGIN 生成xlsx文档**************************************************//
    /**
     * @Description: 下载xlsx模板
     * @author zhangjs
     * @param path
     * @param hiddenDate
     * @return
     * @throws Exception
     */
    public static Map<String, String> downXSSFTemplate(String path, Map<String, List<Map<String, String>>> hiddenDate) throws Exception {
        Map<String, Object> xmlAttribute = readTemplateXml(path);
        return createXSSFTemplate(xmlAttribute, hiddenDate);
    }

    /**
     * @Description:通过 XSSFWorkbook创建xlsx
     * @author zhangjs
     * @param xmlAttribute
     * @param hiddenData
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static Map<String, String> createXSSFTemplate(Map<String, Object> attribute, Map<String, List<Map<String, String>>> hidden)
            throws Exception {

        // excel信息
        Map<String, String> excelMap = (Map<String, String>) attribute.get(ExpImpConstants.EXCEL_ROOT);
        // sheet信息
        List<Map<String, String>> theadList = (List<Map<String, String>>) attribute.get(ExpImpConstants.EXCEL_THEAD);
        // 列信息
        List<Map<String, String>> colList = (List<Map<String, String>>) attribute.get(ExpImpConstants.EXCEL_COL);

        // 创建存放excel的服务器目录
        String filePath = ExcelUtil.class.getResource("/").getFile();
        File file = new File(filePath + "downloadtemplate/model");
        file.mkdirs();
        // 创建workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        for (int i = 0; i < theadList.size(); i++) {
            Map<String, String> thead = theadList.get(i);
            // 创建sheet
            XSSFSheet sheet = workbook.createSheet(thead.get(ExpImpConstants.EXCEL_SHEET_NAME));
            DataValidationHelper help = new XSSFDataValidationHelper(sheet);

            /* 设置字体、单元格风格 */
            XSSFFont font = workbook.createFont();
            XSSFCellStyle style = workbook.createCellStyle();
            font.setFontName("华文楷体");
            // 粗体
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 12);
            style.setFont(font);
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);

            /* 1、设置隐藏域数据 */
            Map<String, HiddenDataInfo> hiddenNameAndSize = createHiddenSheet(workbook, hidden);

            // 查询需要级联查询的隐藏域
            List<CascadeInfo> cascadeInfoList = new ArrayList<>();
            for (Map<String, String> col : colList) {
                String colType = col.get(ExpImpConstants.EXCEL_COL_TYPE);
                String cascadeSup = col.get(ExpImpConstants.EXCEL_COL_CASCADESUP);
                String index = col.get(ExpImpConstants.EXCEL_COL_INDEX);

                if (ExpImpConstants.EXCEL_TYPE_2.equals(colType) && StringUtil.isNonBlank(cascadeSup) && !index.equals(cascadeSup)) {
                    CascadeInfo cascadeInfo = new CascadeInfo();
                    cascadeInfo.setIndex(index);
                    cascadeInfo.setSupIndex(cascadeSup);
                    cascadeInfoList.add(cascadeInfo);
                }
            }
            // 级联下拉框隐藏域
            Map<String, CascadeInfo> cascadeMap = createXSSFCascadeHiddenSheet(workbook, cascadeInfoList, hidden);

            /* 2、创建ROW */
            /* 2.1、建HEAD cell以及EXCEL时间格式限制 */
            createXSSFHeadAndFormat(workbook, sheet, colList, style, help, hiddenNameAndSize, cascadeMap);

            /* 2.2 CONTENT cell */
            for (int rowNum = 1; rowNum <= 500; rowNum++) {
                XSSFRow row = sheet.createRow(rowNum);
                createXSSFCell(workbook, row, rowNum, colList, hiddenNameAndSize, cascadeMap);
            }
        }

        // 将EXCEL保存到系统中
        String template = file + "\\" + excelMap.get(ExpImpConstants.EXCEL_NAME) + ".xlsx";
        FileOutputStream fos = new FileOutputStream(template);
        workbook.write(fos);
        fos.close();

        Map<String, String> result = new HashMap<>();
        result.put(ExpImpConstants.EXCEL_NAME, excelMap.get(ExpImpConstants.EXCEL_NAME) + ".xlsx");
        result.put(ExpImpConstants.EXCEL_FILE_NAME, template);
        return result;
    }

    private static void createXSSFCell(XSSFWorkbook workbook, XSSFRow row, int rowNum, List<Map<String, String>> colList,
            Map<String, HiddenDataInfo> hiddenNameAndSize, Map<String, CascadeInfo> cascadeMap) {

        for (int colNum = 0; colNum < colList.size(); colNum++) {

            Map<String, String> col = colList.get(colNum);
            XSSFCell cell = row.createCell(colNum);

            // 下拉框为名字框的index
            String comboName = col.get(ExpImpConstants.EXCEL_COL_COMBONAME);
            // 列的index
            String index = col.get(ExpImpConstants.EXCEL_COL_INDEX);
            // 列的数据类型
            String colType = col.get(ExpImpConstants.EXCEL_COL_TYPE);
            String cascadeSup = col.get(ExpImpConstants.EXCEL_COL_CASCADESUP);

            /* 下拉框的ID框 */
            if (ExpImpConstants.EXCEL_TYPE_2.equals(colType) && !index.equals(comboName)) {
                /* id框赋值 */
                // 不是级联下拉框或者是级联下拉框中的上级下拉框
                if (StringUtil.isBlank(cascadeSup) || index.equals(cascadeSup)) {
                    getXSSFComboBoxId(cell, rowNum, comboName, hiddenNameAndSize.get(comboName));
                }
                // 为级联下拉框，并且是下级的ID框
                else if (StringUtil.isNonBlank(cascadeSup) && !index.equals(cascadeSup)) {

                    addXSSFCascadeIdData(cell, rowNum, cascadeSup, comboName, cascadeMap.get(index));
                }
            }

            /* 数字类型 */
            else if (ExpImpConstants.EXCEL_TYPE_0.equals(colType)) {
                String formatNum = col.get(ExpImpConstants.EXCEL_COL_FORMAT);
                if (!("#").equals(formatNum)) {
                    XSSFCellStyle cellStyle = workbook.createCellStyle();
                    XSSFDataFormat format = workbook.createDataFormat();
                    cellStyle.setDataFormat(format.getFormat(formatNum));
                    cell.setCellStyle(cellStyle);
                }
            }

            /* 其他定义为String类型 */
            else {
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                XSSFDataFormat format = workbook.createDataFormat();
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cellStyle.setDataFormat(format.getFormat("@"));
            }

        }
    }

    /**
     * @Description:级联下拉框，下级的名称下拉框
     * @author zhangjs
     * @param help
     * @param sheet
     * @param index
     * @param size
     * @param colNum
     */
    private static void addXSSFCascadeNameData(DataValidationHelper help, XSSFSheet sheet, String index, int groupNum, int size, int colNum) {
        String begin = changeIndexType(groupNum * 5 - 4);
        String end = changeIndexType(groupNum * 5 - 3);

        StringBuilder formulas = new StringBuilder(" INDIRECT(VLOOKUP($");
        formulas.append(index + "2,");
        formulas.append(ExpImpConstants.EXCEL_CASCADE_HIDDEN_SHEET);
        formulas.append("!$" + begin + "$1:$" + end + "$" + size);
        formulas.append(" ,2,FALSE))");
        validationXSSFComboBoxData(help, sheet, colNum, formulas.toString());
    }

    /**
     * @Description: 级联下拉框，下级的ID的值获取规则
     * @author zhangjs
     * @param cell
     * @param rowNum
     * @param nameIndex
     * @param info
     */
    private static void addXSSFCascadeIdData(XSSFCell cell, int rowNum, String supIdIndex, String nameIndex, CascadeInfo info) {
        // 判断是否有下拉框值
        int length = info.getSize() == 0 ? 1 : info.getSize();
        String sheetName = ExpImpConstants.EXCEL_HIDDEN_SHEET;
        String begin = changeIndexType(info.getGroupNum() * 5 - 2);
        String end = changeIndexType(info.getGroupNum() * 5);
        StringBuilder formula = new StringBuilder();
        formula.append("VLOOKUP($" + supIdIndex + (rowNum + 1));
        formula.append("&\"" + ExpImpConstants.EXCEL_SPLIT_CASCADE + "\"&$");
        formula.append(nameIndex + ",");
        formula.append(sheetName + "!$");
        formula.append(begin + "$1:$");
        formula.append(end + "$" + length + ",3,FALSE)");
        cell.setCellFormula(formula.toString());
    }

    /**
     * @Description: 对应下拉框从隐藏页获取name的id值
     * @author zhangjs
     * @param cell
     * @param rowNum
     * @param nameIndex
     * @param enumNumIndex
     * @param hiddenLength
     *            最终公式样式 =VLOOKUP($L2,DataDictionary!$G$1:$H$45,2,FALSE)
     */
    private static void getXSSFComboBoxId(XSSFCell cell, int rowNum, String nameIndex, HiddenDataInfo info) {
        // 判断是否有下拉框值
        int length = info.getSize() == 0 ? 1 : info.getSize();
        String sheetName = ExpImpConstants.EXCEL_HIDDEN_SHEET;
        String begin = changeIndexType(info.getGroupNum() * 2 - 1);
        String end = changeIndexType(info.getGroupNum() * 2);
        String formula = "VLOOKUP($" + nameIndex + (rowNum + 1) + "," + sheetName + "!$" + begin + "$1:$" + end + "$" + length + "," + 2 + ",FALSE)";
        cell.setCellFormula(formula);
    }

    /**
     * @Description: 列的添加限制
     * @author zhangjs
     * @param help
     * @param sheet
     * @param colNum
     * @param errorMsg
     * @param validationType
     * @param operator
     * @param formula1
     * @param formula2
     */
    private static void setXSSFValidationData(DataValidationHelper help, XSSFSheet sheet, int colNum, String errorMsg, int validationType,
            int operator, String formula1, String formula2) {

        // 选定一个区域 行 1 到 65535行 colNum列起 colNum列止
        CellRangeAddressList regions = new CellRangeAddressList(1, 25000, colNum, colNum);
        DataValidationConstraint constrain = new XSSFDataValidationConstraint(validationType, operator, formula1, formula2);
        DataValidation vali = help.createValidation(constrain, regions);
        vali.createErrorBox("错误", errorMsg);
        vali.setShowErrorBox(true);
        sheet.addValidationData(vali);
    }

    /**
     * @Description: 下拉框列的限制
     * @author zhangjs
     * @param help
     * @param colNum
     * @param alias
     */
    private static void validationXSSFComboBoxData(DataValidationHelper help, XSSFSheet sheet, int colNum, String alias) {
        CellRangeAddressList regions = new CellRangeAddressList(1, 25000, colNum, colNum);
        XSSFDataValidationConstraint constrain = (XSSFDataValidationConstraint) help.createFormulaListConstraint(alias);
        DataValidation dataValidate = help.createValidation(constrain, regions);
        sheet.addValidationData(dataValidate);
    }

    /**
     * @Description: 建立表头以及需验证列
     * @author zhangjs
     * @param sheet
     * @param colList
     * @param style
     * @param help
     * @throws Exception
     */
    private static List<String> createXSSFHeadAndFormat(XSSFWorkbook workbook, XSSFSheet sheet, List<Map<String, String>> colList,
            XSSFCellStyle style, DataValidationHelper help, Map<String, HiddenDataInfo> hiddenNameAndSize, Map<String, CascadeInfo> cascadeMap)
            throws Exception {
        // 按序记录下拉框别名
        List<String> aliasNameList = new ArrayList<>();

        Row head = sheet.createRow(0);

        for (int colNum = 0; colNum < colList.size(); colNum++) {
            Map<String, String> col = colList.get(colNum);
            Cell cell = head.createCell(colNum);
            // 设置单元格样式
            cell.setCellStyle(style);
            // 设置列宽度
            sheet.setColumnWidth(colNum, 256 * Maps.getInt(col, ExpImpConstants.EXCEL_COL_WIDTH));
            // 设置表头的名称
            cell.setCellValue(col.get(ExpImpConstants.EXCEL_COL_VALUE));

            // 当前列的格式类型
            String colType = col.get(ExpImpConstants.EXCEL_COL_TYPE);
            /* 设置EXCEL时间格式限制 */
            if (ExpImpConstants.EXCEL_TYPE_6.equals(colType)) {
                String errorMsg = "你必须输入一个格式为“年-月-日”的时间！";
                int type = DataValidationConstraint.ValidationType.DATE;
                int operator = DataValidationConstraint.OperatorType.BETWEEN;
                String tenYearAgo = TimeUtil.dateAddYear(TimeUtil.getSysDate(), -10);
                setXSSFValidationData(help, sheet, colNum, errorMsg, type, operator, tenYearAgo, TimeUtil.getSysDate());
            }

            /* 按配置文件顺序记录下拉框别名，为后续从结果集取值填充隐藏页数据做准备 */
            else if (ExpImpConstants.EXCEL_TYPE_2.equals(colType)) {
                String comboName = col.get(ExpImpConstants.EXCEL_COL_COMBONAME);
                String index = col.get(ExpImpConstants.EXCEL_COL_INDEX);
                // 用KEY作为下拉框隐藏域的别名
                String alias = col.get(ExpImpConstants.EXCEL_COL_KEY);
                String cascadeSup = col.get(ExpImpConstants.EXCEL_COL_CASCADESUP);

                /* 下拉框的ID，需要隐藏 */
                if (!index.equals(comboName)) {
                    sheet.setColumnHidden(colNum, true);
                }
                /* 下拉框显示的名称 */
                // 非级联下拉框或者是级联下拉框的上级才可以这样显示名称
                else if (StringUtil.isBlank(cascadeSup) || index.equals(cascadeSup)) {
                    // 隐藏页填充的下拉框数据的别名
                    String aliasName = col.get(ExpImpConstants.EXCEL_COL_INDEX);
                    aliasNameList.add(aliasName);

                    /* 设置名称的下拉框 */
                    // 设置下拉框值--名称(普通下拉框和父级下拉框相同处理、子级下拉框特殊处理)
                    creatComboBoxAlias(workbook.createName(), ExpImpConstants.EXCEL_HIDDEN_SHEET, alias, 1, hiddenNameAndSize.get(index));
                    // 设置下拉框
                    validationXSSFComboBoxData(help, sheet, colNum, alias);
                } else if (StringUtil.isNonBlank(cascadeSup) && !index.equals(cascadeSup)) {
                    addXSSFCascadeNameData(help, sheet, cascadeSup, cascadeMap.get(index).getGroupNum(), cascadeMap.get(index).getSize(), colNum);
                }
            }

            /* 数字类型 */
            else if (ExpImpConstants.EXCEL_TYPE_0.equals(colType)) {

                String formatNum = col.get(ExpImpConstants.EXCEL_COL_FORMAT);
                if (formatNum.equals("#")) {
                    String min = Maps.getString(col, ExpImpConstants.EXCEL_COL_FORMAT_MIN, "0");
                    String max = Maps.getString(col, ExpImpConstants.EXCEL_COL_FORMAT_MAX, "25");
                    String errorMsg = "你必须输入0-" + max + "的整数";
                    int type = DataValidationConstraint.ValidationType.INTEGER;
                    int operator = DataValidationConstraint.OperatorType.BETWEEN;
                    setXSSFValidationData(help, sheet, colNum, errorMsg, type, operator, min, max);
                }
            }
        }
        return aliasNameList;
    }

    /**
     * @Description: 创建隐藏域数据
     * @author zhangjs
     * @param workbook
     * @param cols
     * @param hiddenData
     * @return
     */
    private static Map<String, HiddenDataInfo> createHiddenSheet(XSSFWorkbook workbook, Map<String, List<Map<String, String>>> hiddenData) {

        // 记录下拉框名称及对应名称的结果集大小
        Map<String, HiddenDataInfo> hiddenNameAndSize = new HashMap<>();

        if (hiddenData == null || hiddenData.isEmpty()) {
            return hiddenNameAndSize;
        }

        XSSFSheet sheet = workbook.createSheet(ExpImpConstants.EXCEL_HIDDEN_SHEET);
        workbook.setSheetHidden(workbook.getSheetIndex(ExpImpConstants.EXCEL_HIDDEN_SHEET), true);

        // 记录隐藏页最大行，为后续创建隐藏页行做准备
        int rowMaxIndex = 0;
        int hiddenIndex = 0;
        for (String key : hiddenData.keySet()) {
            int size = hiddenData.get(key).size();
            HiddenDataInfo info = new HiddenDataInfo();
            info.setSize(size);
            info.setGroupNum(++hiddenIndex);
            info.setAlias(key);
            hiddenNameAndSize.put(key, info);
            rowMaxIndex = rowMaxIndex > size ? rowMaxIndex : size;
        }

        // 创建隐藏页行,并赋值
        for (int rowHiddenNum = 0; rowHiddenNum < rowMaxIndex; rowHiddenNum++) {
            XSSFRow row = sheet.createRow(rowHiddenNum);

            // 列
            int colNum = 0;
            for (String key : hiddenNameAndSize.keySet()) {
                List<Map<String, String>> list = hiddenData.get(key);
                if (rowHiddenNum < list.size()) {
                    row.createCell(colNum * 2).setCellValue(list.get(rowHiddenNum).get(ExpImpConstants.EXCEL_COL_CLASS_NAME));
                    row.createCell(colNum * 2 + 1).setCellValue(list.get(rowHiddenNum).get(ExpImpConstants.EXCEL_COL_CLASS_ID));
                }
                colNum++;
            }
        }

        return hiddenNameAndSize;
    }

    /**
     * @Description: 设置名称
     * @author zhangjs
     * @param name
     * @param sheetName
     * @param alias
     * @param enumNumIndex
     * @param startRowNum
     * @param hiddenLength
     */
    private static void creatComboBoxAlias(Name name, String sheetName, String alias, int startRowNum, HiddenDataInfo info) {
        startRowNum = startRowNum == 0 ? 1 : startRowNum;
        // 设置名称
        name.setNameName(alias);
        // 判断是否有下拉框值
        int length = info.getSize() == 0 ? 1 : info.getSize();
        String index = changeIndexType(info.getGroupNum() * 2 - 1);
        // 设置公式,范围
        String formula = sheetName + "!$" + index + "$" + startRowNum + ":$" + index + "$" + length;
        // 将公式应用于该名称
        name.setRefersToFormula(formula);
    }

    /**
     * @Description:创建级联下拉的隐藏数据域
     * @author zhangjs
     * @param workbook
     * @param cols
     * @param hiddenData
     * @return
     */
    private static Map<String, CascadeInfo> createXSSFCascadeHiddenSheet(XSSFWorkbook workbook, List<CascadeInfo> cols,
            Map<String, List<Map<String, String>>> hiddenData) {

        // 返回值
        Map<String, CascadeInfo> result = new HashMap<>();

        String sheetName = ExpImpConstants.EXCEL_CASCADE_HIDDEN_SHEET;
        XSSFSheet sheet = workbook.createSheet(sheetName);
        workbook.setSheetHidden(workbook.getSheetIndex(sheetName), true);

        Map<String, List<CascadeHiddenDataInfo>> cascadeMap = new HashMap<>();
        /* 创建每组级联隐藏域点（每组5列） */
        for (CascadeInfo info : cols) {
            String supIndex = info.getSupIndex();
            String index = info.getIndex();
            List<Map<String, String>> hiddenInfoList = hiddenData.get(index);
            List<Map<String, String>> supHiddenInfoList = hiddenData.get(supIndex);

            if (hiddenInfoList == null || hiddenInfoList.isEmpty() || supHiddenInfoList == null || supHiddenInfoList.isEmpty()) {
                continue;
            }

            List<CascadeHiddenDataInfo> cascadeDataList = new ArrayList<>();
            int supSize = 0;
            for (Map<String, String> supIndexInfo : supHiddenInfoList) {
                supSize++;
                String supId = supIndexInfo.get(ExpImpConstants.HIDDEN_ID);
                String supName = supIndexInfo.get(ExpImpConstants.HIDDEN_NAME);
                for (Map<String, String> indexInfo : hiddenInfoList) {
                    String indexSupId = indexInfo.get(ExpImpConstants.HIDDEN_SUP_ID);
                    if (supId.equals(indexSupId)) {
                        String id = indexInfo.get(ExpImpConstants.HIDDEN_ID);
                        String name = indexInfo.get(ExpImpConstants.HIDDEN_NAME);
                        CascadeHiddenDataInfo cascadeData = new CascadeHiddenDataInfo();
                        // 1、上级的名称
                        cascadeData.setSupName(supName);
                        // 2、上级的名称对应别名
                        String alias = ExpImpConstants.EXCEL_CASCADE_HIDDEN_SHEET + "_" + info.getIndex() + "_" + supSize;
                        cascadeData.setAlias(alias);
                        // 3、上级ID+"|"+下级ID 匹配字段
                        cascadeData.setMergeCol(indexSupId + "|" + id);
                        // 4、下级名称
                        cascadeData.setName(name);
                        // 5、下级ID
                        cascadeData.setId(id);
                        cascadeDataList.add(cascadeData);
                    }
                }
            }
            cascadeMap.put(index, cascadeDataList);
        }

        if (cascadeMap.isEmpty()) {
            return new HashMap<>();
        }

        /* 生成二级下拉框的隐藏域的最大行以及隐藏域别名信息 */
        // 创建隐藏域的最大行
        int maxRow = 0;
        List<CascadeHeight> heightList = new ArrayList<>();
        int groupNum = 1;
        for (Entry<String, List<CascadeHiddenDataInfo>> entry : cascadeMap.entrySet()) {
            List<CascadeHiddenDataInfo> list = entry.getValue();
            String index = entry.getKey();

            CascadeInfo cascadeInfo = new CascadeInfo();
            cascadeInfo.setGroupNum(groupNum);
            cascadeInfo.setIndex(index);
            cascadeInfo.setSize(list.size());
            result.put(index, cascadeInfo);

            CascadeHeight height = new CascadeHeight();
            height.setColumn(changeIndexType(groupNum * 5 - 1));
            height.setStartNum(1);
            height.setGroupNum(groupNum);
            height.setAlias("_" + changeIndexType(groupNum * 5 - 1) + "_" + 0);
            if (list == null || list.isEmpty()) {
                height.setEndNum(1);
                heightList.add(height);
                continue;
            }

            int size = list.size();
            maxRow = maxRow > size ? maxRow : size;

            String alias = "";
            for (int i = 0; i < size; i++) {
                CascadeHiddenDataInfo cascadeHiddenDataInfo = list.get(i);
                if (i == 0) {
                    alias = cascadeHiddenDataInfo.getAlias();
                }
                String currentAlias = cascadeHiddenDataInfo.getAlias();
                if (!currentAlias.equals(alias)) {
                    height.setAlias(alias);
                    height.setEndNum(i + 1);
                    heightList.add(height);

                    alias = currentAlias;

                    height = new CascadeHeight();
                    height.setColumn(changeIndexType(groupNum * 5 - 1));
                    height.setStartNum(i + 2);
                    height.setGroupNum(groupNum);
                }
                // 最后一条数据
                else if (i == size - 1) {
                    height.setAlias(alias);
                    height.setEndNum(i + 1);
                    heightList.add(height);
                }
            }
            groupNum++;
        }

        /* 创建级联隐藏域数据 */
        for (int rowNum = 0; rowNum < maxRow; rowNum++) {
            XSSFRow row = sheet.createRow(rowNum);

            // 列
            int colNum = 0;
            for (String key : cascadeMap.keySet()) {
                List<CascadeHiddenDataInfo> list = cascadeMap.get(key);
                if (rowNum < list.size()) {
                    row.createCell(colNum * 5).setCellValue(list.get(rowNum).getSupName());
                    row.createCell(colNum * 5 + 1).setCellValue(list.get(rowNum).getAlias());
                    row.createCell(colNum * 5 + 2).setCellValue(list.get(rowNum).getMergeCol());
                    row.createCell(colNum * 5 + 3).setCellValue(list.get(rowNum).getName());
                    row.createCell(colNum * 5 + 4).setCellValue(list.get(rowNum).getId());
                }
                colNum++;
            }
        }

        /* 创建别名 */
        if (!heightList.isEmpty()) {
            for (CascadeHeight height : heightList) {
                createCascadeAlias(workbook.createName(), sheetName, height.getAlias(), height.getStartNum(), height.getEndNum(), height.getColumn());
            }
        }

        return result;
    }

    /**
     * @Description: 设置名称
     * @author zhangjs
     * @param name
     * @param sheetName
     * @param alias
     * @param startRow
     * @param endRow
     * @param index
     */
    private static void createCascadeAlias(Name name, String sheetName, String alias, int startRow, int endRow, String index) {
        startRow = startRow == 0 ? 1 : startRow;
        endRow = endRow == 0 ? 1 : endRow;
        // 设置名称
        name.setNameName(alias);
        // 设置公式,范围
        String formula = sheetName + "!$" + index + "$" + startRow + ":$" + index + "$" + endRow;
        // 将公式应用于该名称
        name.setRefersToFormula(formula);
    }

    /**
     * @Description: EXCEL列的字符与数字的转换 字符为大写的
     * @author 程彬
     * @param obj index
     */
    public static String changeIndexType(Object obj) {
        String resultChange = "";
        String type = obj.getClass().getName();
        if ("java.lang.Integer".equals(type)) {
            Double temp = Double.valueOf((int) obj);
            if ((temp + 1) / 26 > 1) {
                int tt = (int) obj;
                String changeValue = changeValue((tt - 26) / 26);
                String changeValue2 = changeValue(tt % 26);
                resultChange = changeValue + changeValue2;

                return resultChange;
            } else {
                int intObj = (int) obj + 65;
                resultChange = ((char) intObj) + "";
            }
        } else if ("java.lang.String".equals(type)) {
            String strobj = (String) obj;
            char[] charArray = strobj.toCharArray();
            if (charArray.length == 2) {
                resultChange = 26 * (Integer.valueOf(changeValue(charArray[0])) + 1) + Integer.valueOf(changeValue(charArray[1])) + "";

                return resultChange;
            } else {
                resultChange = (((int) ((String) obj).charAt(0)) - 65) + "";
            }
        } else if ("java.lang.Character".equals(type)) {
            resultChange = (((int) (String.valueOf(obj)).charAt(0)) - 65) + "";
        }

        return resultChange;
    }
    // **************************************END 生成xlsx文档**************************************************//
}
