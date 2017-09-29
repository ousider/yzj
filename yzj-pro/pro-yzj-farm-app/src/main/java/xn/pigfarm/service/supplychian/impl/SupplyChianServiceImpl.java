package xn.pigfarm.service.supplychian.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.bigDeciml.BigDecimalUtil;
import xn.core.util.cache.CacheUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.core.util.page.BasePageInfo;
import xn.core.util.time.TimeUtil;
import xn.core.util.uploadFile.UploadFileUtil;
import xn.hana.model.common.HanaClientOrProvider;
import xn.hana.model.common.HanaGatherInvoiceHeaderDetail;
import xn.hana.model.common.HanaGatherInvoiceRowsDetail;
import xn.hana.model.common.HanaInputBill;
import xn.hana.model.common.HanaInputBillDetail;
import xn.hana.model.common.HanaMaterial;
import xn.hana.model.common.HanaMaterialAllotBill;
import xn.hana.model.common.HanaMaterialAllotBillDetail;
import xn.hana.model.common.HanaMaterialOperateBill;
import xn.hana.model.common.HanaMaterialOperateBillDetail;
import xn.hana.model.common.HanaPigHouse;
import xn.hana.model.common.HanaPurchaseBill;
import xn.hana.model.common.HanaPurchaseBillDetail;
import xn.hana.model.common.HanaReceiptBill;
import xn.hana.model.common.HanaReceiptBillDetail;
import xn.hana.model.common.HanaWarehouse;
import xn.hana.model.common.MTC_ITFC;
import xn.hana.model.common.MTC_OITM;
import xn.hana.service.common.IhanaCommonService;
import xn.hana.util.HanaJacksonUtil;
import xn.hana.util.HanaUtil;
import xn.hana.util.constants.HanaConstants;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.supplychian.SupplyChianException;
import xn.pigfarm.mapper.basicinfo.CompanyMapper;
import xn.pigfarm.mapper.basicinfo.ConsumableMapper;
import xn.pigfarm.mapper.basicinfo.DeptMapper;
import xn.pigfarm.mapper.basicinfo.DeviceMapper;
import xn.pigfarm.mapper.basicinfo.DisinfectorMapper;
import xn.pigfarm.mapper.basicinfo.DrugMapper;
import xn.pigfarm.mapper.basicinfo.EmployeeMapper;
import xn.pigfarm.mapper.basicinfo.FeedMapper;
import xn.pigfarm.mapper.basicinfo.HardwareMapper;
import xn.pigfarm.mapper.basicinfo.MaterialMapper;
import xn.pigfarm.mapper.basicinfo.PpeMapper;
import xn.pigfarm.mapper.basicinfo.RawMaterialMapper;
import xn.pigfarm.mapper.basicinfo.SrmMapper;
import xn.pigfarm.mapper.basicinfo.VaccineMapper;
import xn.pigfarm.mapper.production.BillMapper;
import xn.pigfarm.mapper.production.EarCodeMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.mapper.supplychian.DailyRecordMapper;
import xn.pigfarm.mapper.supplychian.InputDetailMapper;
import xn.pigfarm.mapper.supplychian.InputMapper;
import xn.pigfarm.mapper.supplychian.MonthAccountMapper;
import xn.pigfarm.mapper.supplychian.OutputDetailMapper;
import xn.pigfarm.mapper.supplychian.OutputMapper;
import xn.pigfarm.mapper.supplychian.PriceListDetailMapper;
import xn.pigfarm.mapper.supplychian.PriceListMapper;
import xn.pigfarm.mapper.supplychian.PurchaseDetailMapper;
import xn.pigfarm.mapper.supplychian.PurchaseMapper;
import xn.pigfarm.mapper.supplychian.ReceiptDetailMapper;
import xn.pigfarm.mapper.supplychian.ReceiptInputMapper;
import xn.pigfarm.mapper.supplychian.ReceiptMapper;
import xn.pigfarm.mapper.supplychian.ReplaceAndPackageDetailMapper;
import xn.pigfarm.mapper.supplychian.ReplaceAndPackageMapper;
import xn.pigfarm.mapper.supplychian.RequireDetailMapper;
import xn.pigfarm.mapper.supplychian.RequireMapper;
import xn.pigfarm.mapper.supplychian.StoreMaterialMapper;
import xn.pigfarm.mapper.supplychian.WarehouseMapper;
import xn.pigfarm.mapper.supplychian.WarehouseMaterialTypeMapper;
import xn.pigfarm.model.basicinfo.CompanyModel;
import xn.pigfarm.model.basicinfo.ConsumableModel;
import xn.pigfarm.model.basicinfo.DeptModel;
import xn.pigfarm.model.basicinfo.DeviceModel;
import xn.pigfarm.model.basicinfo.DisinfectorModel;
import xn.pigfarm.model.basicinfo.DrugModel;
import xn.pigfarm.model.basicinfo.EmployeeModel;
import xn.pigfarm.model.basicinfo.FeedModel;
import xn.pigfarm.model.basicinfo.HardwareModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PpeModel;
import xn.pigfarm.model.basicinfo.RawMaterialModel;
import xn.pigfarm.model.basicinfo.SrmModel;
import xn.pigfarm.model.basicinfo.VaccineModel;
import xn.pigfarm.model.production.BillModel;
import xn.pigfarm.model.production.EarCodeModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.model.supplychian.DailyRecordModel;
import xn.pigfarm.model.supplychian.InputDetailModel;
import xn.pigfarm.model.supplychian.InputModel;
import xn.pigfarm.model.supplychian.MonthAccountModel;
import xn.pigfarm.model.supplychian.OutputDetailModel;
import xn.pigfarm.model.supplychian.OutputModel;
import xn.pigfarm.model.supplychian.PriceListDetailModel;
import xn.pigfarm.model.supplychian.PriceListModel;
import xn.pigfarm.model.supplychian.PurchaseDetailModel;
import xn.pigfarm.model.supplychian.PurchaseModel;
import xn.pigfarm.model.supplychian.ReceiptDetailModel;
import xn.pigfarm.model.supplychian.ReceiptInputModel;
import xn.pigfarm.model.supplychian.ReceiptModel;
import xn.pigfarm.model.supplychian.ReplaceAndPackageDetailModel;
import xn.pigfarm.model.supplychian.ReplaceAndPackageModel;
import xn.pigfarm.model.supplychian.RequireDetailModel;
import xn.pigfarm.model.supplychian.RequireModel;
import xn.pigfarm.model.supplychian.StoreMaterialModel;
import xn.pigfarm.model.supplychian.WarehouseMaterialTypeModel;
import xn.pigfarm.model.supplychian.WarehouseModel;
import xn.pigfarm.service.supplychian.ISupplyChianService;
import xn.pigfarm.util.TreeBuildUtil;
import xn.pigfarm.util.constants.CompanyConstants;
import xn.pigfarm.util.constants.MaterialConstants;
import xn.pigfarm.util.constants.PigConstants;
import xn.pigfarm.util.constants.SupplyChianConstants;
import xn.pigfarm.util.enums.MaterialInfoEnum;

/**
 * @Description: 供应链
 * @author THL
 * @date 2016年8月16日 下午9:31:51
 */
@Service("SupplyChianService")
public class SupplyChianServiceImpl extends BaseServiceImpl implements ISupplyChianService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private RawMaterialMapper rawMaterialMapper;

    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private VaccineMapper vaccineMapper;

    @Autowired
    private DisinfectorMapper disinfectorMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private ConsumableMapper consumableMapper;

    @Autowired
    private HardwareMapper hardwareMapper;

    @Autowired
    private PpeMapper ppeMapper;

    @Autowired
    private SrmMapper srmMapper;

    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @Autowired
    private RequireMapper requireMapper;

    @Autowired
    private RequireDetailMapper requireDetailMapper;

    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private InputMapper inputMapper;

    @Autowired
    private InputDetailMapper inputDetailMapper;

    @Autowired
    private OutputMapper outputMapper;

    @Autowired
    private OutputDetailMapper outputDetailMapper;

    @Autowired
    private StoreMaterialMapper storeMaterialMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private WarehouseMaterialTypeMapper warehouseMaterialTypeMapper;

    @Autowired
    private MonthAccountMapper monthAccountMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private ReplaceAndPackageMapper replaceAndPackageMapper;

    @Autowired
    private ReplaceAndPackageDetailMapper replaceAndPackageDetailMapper;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private ReceiptDetailMapper receiptDetailMapper;

    @Autowired
    private IhanaCommonService hanaCommonService;

    @Autowired
    private PriceListMapper priceListMapper;

    @Autowired
    private PriceListDetailMapper priceListDetailMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private PigMapper pigMapper;

    @Autowired
    private ReceiptInputMapper receiptInputMapper;

    @Autowired
    private EarCodeMapper earCodeMapper;

    // 新农 CompanyMark
    private final String COMPANY_MARK_XNFEED = "1,2,3";

    // 新农子公司 CompanyMark
    private final String COMPANY_MARK_IN_XNFEED = "1,2,3,";

    @Override
    public void editInsertMaterial() throws Exception {
        String path = "d:/InputMaterial/";
        String fileName = "InputMaterial";
        String fileType = "xls";
        read(path, fileName, fileType);

    }

    private Map<String, List<Object>> read(String path, String fileName, String fileType) throws Exception {
        InputStream stream = new FileInputStream(path + fileName + "." + fileType);
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        } else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet sheet1 = wb.getSheetAt(0);

        // 各种明细
        Map<String, List> map = new HashMap<String, List>();
        // 设备
        map.put("cd_o_device", new ArrayList());
        // 药品
        map.put("cd_o_drug", new ArrayList());
        // 疫苗
        map.put("cd_o_vaccine", new ArrayList());
        // 易耗品
        map.put("cd_o_consumable", new ArrayList());

        int i = 1;
        for (Row row : sheet1) {
            System.out.println("当前行数：" + i);
            if (i == 1) {
                i++;
                continue;
            } else {
                i++;

                if (i >= 301) {
                    break;
                }
            }

            Map<String, Object> materialMap = new HashMap<String, Object>();
            if (Cell.CELL_TYPE_BLANK != row.getCell(0).getCellType()) {
                materialMap.put("materialTypeName", row.getCell(0).getStringCellValue().trim());

                if (Maps.getString(materialMap, "materialTypeName").equals("药品")) {

                    materialMap.put("materialType", "Drug");

                    // 药品细分
                    if (Cell.CELL_TYPE_BLANK != row.getCell(1).getCellType()) {
                        String groupName = row.getCell(1).getStringCellValue().trim();
                        Long groupId = null;
                        if ("粉剂".equals(groupName)) {
                            groupId = 28L;
                        } else if ("消毒剂".equals(groupName)) {
                            groupId = 33L;
                        } else if ("针剂".equals(groupName)) {
                            groupId = 29L;
                        } else if ("外用药".equals(groupName)) {
                            groupId = 31L;
                        } else if ("输液用".equals(groupName)) {
                            groupId = 30L;
                        } else if ("其它".equals(groupName)) {
                            groupId = 32L;
                        } else {
                            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, "药品小分类错误");
                        }

                        materialMap.put("groupId", groupId);
                    }
                } else if (Maps.getString(materialMap, "materialTypeName").equals("疫苗")) {

                    materialMap.put("materialType", "Vaccine");

                } else if (Maps.getString(materialMap, "materialTypeName").equals("设备")) {

                    materialMap.put("materialType", "Device");

                } else if (Maps.getString(materialMap, "materialTypeName").equals("耗材")) {

                    materialMap.put("materialType", "Consumable");

                } else {
                    Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, "！@#￥%……&*");
                }

            }

            // 物料名称
            if (Cell.CELL_TYPE_BLANK != row.getCell(2).getCellType()) {
                materialMap.put("materialName", row.getCell(2).getStringCellValue().trim());
            }

            // 供应商
            if (Cell.CELL_TYPE_BLANK != row.getCell(3).getCellType()) {
                materialMap.put("supplierName", row.getCell(3).getStringCellValue());
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition((String) materialMap.get("supplierName"), " AND COMPANY_NAME = ?");
                List<CompanyModel> companyModelList = getList(companyMapper, sqlCon);
                if (companyModelList != null && !companyModelList.isEmpty()) {
                    materialMap.put("supplierId", companyModelList.get(0).getRowId());
                } else {
                    Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, "供应商" + Maps.getString(materialMap, "supplierName"));
                }
            }

            // 厂家
            if (Cell.CELL_TYPE_BLANK != row.getCell(4).getCellType()) {
                materialMap.put("manufacturer", row.getCell(4).getStringCellValue().trim());
            }

            // 规格（数量）
            if (Cell.CELL_TYPE_BLANK != row.getCell(6).getCellType()) {
                materialMap.put("specNum", row.getCell(6).getNumericCellValue());
            }

            // 规格（单位）
            if (Cell.CELL_TYPE_BLANK != row.getCell(7).getCellType()) {
                materialMap.put("spec", row.getCell(7).getStringCellValue().toUpperCase());
            }

            // 单位
            if (Cell.CELL_TYPE_BLANK != row.getCell(8).getCellType()) {
                materialMap.put("unit", row.getCell(8).getStringCellValue().toUpperCase());
            }

            // 最小领料比
            if (Cell.CELL_TYPE_BLANK != row.getCell(9).getCellType()) {
                materialMap.put("outputMinQty", row.getCell(9).getNumericCellValue());
            }

            // 价格
            if (Cell.CELL_TYPE_BLANK != row.getCell(10).getCellType()) {
                materialMap.put("price", row.getCell(10).getNumericCellValue());
            }

            // 赠送比例
            if (Cell.CELL_TYPE_BLANK != row.getCell(11).getCellType()) {
                materialMap.put("freePercent", row.getCell(11).getStringCellValue());
            }

            // 备注
            if (Cell.CELL_TYPE_BLANK != row.getCell(12).getCellType()) {
                materialMap.put("notes", row.getCell(12).getStringCellValue().toUpperCase().trim());
            }

            MaterialModel materialModel = getBean(MaterialModel.class, materialMap);
            String busCode = ParamUtil.getBCode("MATERIAL_" + materialModel.getMaterialType().toUpperCase(), getEmployeeId(), getCompanyId(),
                    getFarmId());
            materialModel.setBusinessCode(busCode);
            materialModel.setCompanyId(getCompanyId());
            materialModel.setFarmId(getFarmId());
            materialModel.setIsWarehouse("N");
            materialModel.setIsPurchase("Y");
            materialModel.setIsSell("N");
            materialModel.setOriginFlag("I");
            materialModel.setOriginApp("EXCEL");
            materialMapper.insert(materialModel);

            // BEGIN 更新RelatedId为它自己的ROW_ID,供应链需求
            materialModel.setRelatedId(materialModel.getRowId());
            materialMapper.update(materialModel);

            // 有效成分/型号等
            String effectiveMaterial = row.getCell(5).getStringCellValue();
            if (Maps.getString(materialMap, "materialTypeName").equals("药品")) {
                List list = map.get("cd_o_drug");
                DrugModel drugModel = new DrugModel();
                drugModel.setFarmId(getFarmId());
                drugModel.setCompanyId(getCompanyId());
                drugModel.setStatus("1");
                drugModel.setDeletedFlag("0");
                drugModel.setAppearance(effectiveMaterial);
                drugModel.setNotes(Maps.getString(materialMap, "notes"));
                drugModel.setMaterialId(materialModel.getRowId());
                drugModel.setOriginFlag("I");
                drugModel.setOriginApp("EXCEL");
                list.add(drugModel);
            } else if (Maps.getString(materialMap, "materialTypeName").equals("疫苗")) {
                List list = map.get("cd_o_vaccine");
                VaccineModel vaccineModel = new VaccineModel();
                vaccineModel.setFarmId(getFarmId());
                vaccineModel.setCompanyId(getCompanyId());
                vaccineModel.setStatus("1");
                vaccineModel.setDeletedFlag("0");
                vaccineModel.setMainContent(effectiveMaterial);
                vaccineModel.setNotes(Maps.getString(materialMap, "notes"));
                vaccineModel.setMaterialId(materialModel.getRowId());
                vaccineModel.setOriginFlag("I");
                vaccineModel.setOriginApp("EXCEL");
                list.add(vaccineModel);
            } else if (Maps.getString(materialMap, "materialTypeName").equals("设备")) {
                List list = map.get("cd_o_device");
                DeviceModel deviceModel = new DeviceModel();
                deviceModel.setFarmId(getFarmId());
                deviceModel.setCompanyId(getCompanyId());
                deviceModel.setStatus("1");
                deviceModel.setDeletedFlag("0");
                deviceModel.setDirection(effectiveMaterial);
                deviceModel.setNotes(Maps.getString(materialMap, "notes"));
                deviceModel.setMaterialId(materialModel.getRowId());
                deviceModel.setOriginFlag("I");
                deviceModel.setOriginApp("EXCEL");
                list.add(deviceModel);
            } else if (Maps.getString(materialMap, "materialTypeName").equals("耗材")) {
                List list = map.get("cd_o_consumable");
                ConsumableModel consumableModel = new ConsumableModel();
                consumableModel.setFarmId(getFarmId());
                consumableModel.setCompanyId(getCompanyId());
                consumableModel.setStatus("1");
                consumableModel.setDeletedFlag("0");
                consumableModel.setDirection(effectiveMaterial);
                consumableModel.setNotes(Maps.getString(materialMap, "notes"));
                consumableModel.setMaterialId(materialModel.getRowId());
                consumableModel.setOriginFlag("I");
                consumableModel.setOriginApp("EXCEL");
                list.add(consumableModel);
            }

        }

        // Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, "!@#$%^&*()");

        List deviceList = map.get("cd_o_device");
        if (!deviceList.isEmpty()) {
            deviceMapper.inserts(deviceList);
        }
        List drugList = map.get("cd_o_drug");
        if (!drugList.isEmpty()) {
            drugMapper.inserts(drugList);
        }
        List vaccineList = map.get("cd_o_vaccine");
        if (!vaccineList.isEmpty()) {
            vaccineMapper.inserts(vaccineList);
        }
        List consumableList = map.get("cd_o_consumable");
        if (!consumableList.isEmpty()) {
            consumableMapper.inserts(consumableList);
        }

        return null;
    }

    /* BEGIN供应链相关控件值 */
    @Override
    public List<EmployeeModel> searchEmployeeByIdToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT T1.* ");
        sqlCon.addMainSql(" FROM HR_O_EMPLOYEE T1 INNER JOIN HR_R_EMPLOYEE_POST T2 ON (T1.ROW_ID = T2.EMPLOYEE_ID ");
        sqlCon.addMainSql(" AND T2.STATUS='1' AND T2.DELETED_FLAG='0') INNER JOIN HR_O_POST T3 ");
        sqlCon.addMainSql(" ON (T2.POST_ID = T3.ROW_ID AND T3.STATUS='1' AND T3.DELETED_FLAG='0') ");
        sqlCon.addMainSql(" INNER JOIN HR_O_DEPT T4 ON(T3.DEPT_ID = T4.ROW_ID AND T4.STATUS='1' ");
        sqlCon.addMainSql(" AND T4.DELETED_FLAG='0') WHERE T1.STATUS='1' AND T1.DELETED_FLAG='0' ");
        sqlCon.addMainSql(" AND T1.EMPLOYEE_TYPE <> '9' ");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ? ");
        List<EmployeeModel> employeeModelList = setSql(employeeMapper, sqlCon);
        return employeeModelList;
    }

    @Override
    public List<CompanyModel> searchBrotherCompanyToList(Map<String, Object> inputMap) throws Exception {
        // 调拨关系修改
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" AND EXISTS (");
        sqlCon.addMainSql(" SELECT 1 FROM SC_R_ALLOT_WAREHOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND ALLOT_IN_FARM_ID = HR_M_COMPANY.ROW_ID");
        sqlCon.addCondition(getFarmId(), " AND ALLOT_OUT_FARM_ID = ? LIMIT 1)");
        List<CompanyModel> companyModelList = getList(companyMapper, sqlCon);

        return companyModelList;
    }

    @Override
    public List<Map<String, Object>> searchCompanyFromRequireBillToList(Map<String, Object> inputMap) throws Exception {
        // purchaseFlag判断可以采购的类型[1]-全部[2]-饲料[3]-兽药
        Long farmId = getFarmId();
        if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            farmId = getCompanyId();
        }

        SqlCon sqlCon = new SqlCon();
        if ("All".equals(Maps.getString(inputMap, "searchType"))) {
            sqlCon.addMainSql(" SELECT T2.ROW_ID AS supplierId");
            sqlCon.addMainSql(" FROM HR_R_SRM T1");
            sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T2 ON (T1.CUSSUP_ID = T2.ROW_ID AND T2.DELETED_FLAG='0')");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG='0'");
            sqlCon.addConditionWithNull(farmId, " AND T1.FARM_ID = ?");

            sqlCon.addConditionWithNull(CompanyConstants.CUS_SUP_TYPE_SUP, " AND T1.CUSSUP_TYPE= ?");
            if (!Maps.isEmpty(inputMap, "purchaseFlag")) {
                sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0'");
                sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                sqlCon.addMainSql(" AND SUPPLIER_ID = T2.ROW_ID");
                if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
                    sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
                    sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
                    sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
                } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
                    sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
                    sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
                    sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
                } else if (!"1".equals(Maps.getString(inputMap, "purchaseFlag"))) {
                    Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
                }
                sqlCon.addMainSql(" LIMIT 1)");
            }

            if (!"0".equals(Maps.getString(inputMap, "canPurchaseSupplierId")) && !Maps.isEmpty(inputMap, "canPurchaseSupplierId")) {
                // 专管饲料（新农，新农翔）
                sqlCon.addCondition(Maps.getLongClass(inputMap, "canPurchaseSupplierId"), " AND T2.ROW_ID = ?");

            }

        } else {
            sqlCon.addMainSql("SELECT C.SUPPLIER_ID AS supplierId FROM SC_M_BILL_REQUIRE A");
            sqlCon.addMainSql(" INNER JOIN SC_M_BILL_REQUIRE_DETAIL B");
            sqlCon.addMainSql(" ON(B.DELETED_FLAG = '0' AND B.STATUS = '1' AND B.REQUIRE_ID = A.ROW_ID AND B.PURCHASE_ID IS NULL)");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL C");
            sqlCon.addMainSql(" ON(C.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND B.MATERIAL_ID = C.ROW_ID)");
            sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");
            if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
                sqlCon.addMainSql(" AND A.APPLY_TYPE");
                sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
                sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
            } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
                sqlCon.addMainSql(" AND A.APPLY_TYPE");
                sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
                sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
            } else if (!"1".equals(Maps.getString(inputMap, "purchaseFlag"))) {
                Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
            }
            sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_PASS, " AND (A.STATUS = ?");
            sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_PART_COMPLETED, " OR A.STATUS = ?)");
            sqlCon.addCondition(Maps.getLongClass(inputMap, "canPurchaseSupplierId"), " AND C.SUPPLIER_ID = ?");
            sqlCon.addMainSql(" GROUP BY C.SUPPLIER_ID");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        } else {
            list = new ArrayList<Map<String, Object>>();
        }
        return list;
    }
    /* END供应链相关控件值 */

    /* BEGIN 查找有效物料主数据 */
    @Override
    public BasePageInfo searchSelectMaterialListTableToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS rowId");
        sqlCon.addMainSql(" ,(SELECT SUM(ACTUAL_QTY) FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND FARM_ID = CD_M_MATERIAL.FARM_ID");
        sqlCon.addMainSql(" AND MATERIAL_ID = CD_M_MATERIAL.ROW_ID GROUP BY MATERIAL_ID) AS existsQty");
        sqlCon.addMainSql(" FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0'");
        if (!SupplyChianConstants.IS_NOT_PURCHASE.equals(Maps.getString(inputMap, "isPurchase"))) {
            sqlCon.addCondition(SupplyChianConstants.IS_PURCHASE, " AND IS_PURCHASE = ?");
        }
        if (SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        } else if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        } else {
            sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        }

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getCompanyId(), " AND FARM_ID = ?");

        } else {
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        }

        if (Maps.isEmpty(inputMap, "materialFirstClass")) {
            if (!Maps.isEmpty(inputMap, "applyType")) {
                sqlCon.addCondition(Maps.getString(inputMap, "applyType"), " AND MATERIAL_FIRST_CLASS = ?");
            }
        } else {
            if (Maps.getString(inputMap, "materialFirstClass").contains(",")) {
                sqlCon.addCondition(Maps.getString(inputMap, "materialFirstClass"), " AND MATERIAL_FIRST_CLASS IN ?", false, true);
            } else {
                sqlCon.addCondition(Maps.getString(inputMap, "materialFirstClass"), " AND MATERIAL_FIRST_CLASS = ?");
                sqlCon.addCondition(Maps.getString(inputMap, "materialSecondClass"), " AND MATERIAL_SECOND_CLASS = ?");
            }
        }
        sqlCon.addCondition(Maps.getString(inputMap, "filterIds"), " AND ROW_ID NOT IN ?", false, true);
        sqlCon.addCondition(Maps.getString(inputMap, "supplierId"), " AND SUPPLIER_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "materialName"), " AND MATERIAL_NAME LIKE ?", true, false);

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "rowId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            map.put("defaultWarehouseName", CacheUtil.getName(Maps.getString(map, "defaultWarehouse"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
            map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
        }
        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchMaterialFromRequireTableToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',''+T2.ROW_ID,'}')),']') AS rowIds");
        // sqlCon.addMainSql(", GROUP_CONCAT(ROW_ID) AS filterIds");
        sqlCon.addMainSql(", CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',T2.REQUIRE_ID,'}')),']') AS requireIds");
        sqlCon.addMainSql(", T2.MATERIAL_ID AS materialId, _clearEndZero(SUM(T2.REQUIRE_QTY)) AS requireQty, MAX(T2.FARM_ID) AS requireFarm");
        sqlCon.addMainSql(", GROUP_CONCAT(T2.NOTES) AS requireDetailNotes, T1.REQUIRE_TYPE AS requireType");
        sqlCon.addMainSql(" FROM SC_M_BILL_REQUIRE T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_REQUIRE_DETAIL T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T2.STATUS <> '6' AND T2.PURCHASE_ID IS NULL AND T1.ROW_ID = T2.REQUIRE_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_PASS, " AND (T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_PART_COMPLETED, " OR T1.STATUS = ?)");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.ACCOUNTS_UNIT_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "filterIds"), " AND T2.ROW_ID NOT IN ?", false, true);
        sqlCon.addMainSql(" AND (SELECT 1 FROM CD_M_MATERIAL A WHERE A.DELETED_FLAG = '0' AND A.ROW_ID = T2.MATERIAL_ID");
        if (SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(" AND A.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        } else if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(" AND A.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        }
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_M_MATERIAL B WHERE B.DELETED_FLAG = '0' AND B.ROW_ID = A.RELATED_ID");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "supplierId"), " AND B.SUPPLIER_ID = ?");
        sqlCon.addMainSql(" )) GROUP BY MATERIAL_ID, MATERIAL_SPLIT, T1.REQUIRE_TYPE");
        sqlCon.addMainSql(" ORDER BY T2.FARM_ID, MATERIAL_ID, MATERIAL_SPLIT, T1.REQUIRE_TYPE");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
                map.put("emergencyTypeName", CacheUtil.getName(Maps.getString(map, "emergencyType"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.EMERGENCY_TYPE));
                map.put("requireTypeName", CacheUtil.getName(Maps.getString(map, "requireType"), NameEnum.CODE_NAME, CodeEnum.REQUIRE_TYPE));
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
                map.put("requireQtyUnit", Maps.getString(map, "requireQty") + Maps.getString(map, "unit"));
                Map<String, String> requireFarmInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "requireFarm"));
                map.put("requireFarmSortName", Maps.getString(requireFarmInfoMap, "SORT_NAME"));
                map.put("requireFarmName", Maps.getString(requireFarmInfoMap, "COMPANY_NAME"));

                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchMaterialFromRequireTableDetailToList(Map<String, Object> inputMap) throws Exception {
        setToPage();

        List<HashMap> requireIds = getJsonList(Maps.getString(inputMap, "rowIds"), HashMap.class);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < requireIds.size(); i++) {
            stringBuffer.append(Maps.getLong(requireIds.get(i), "rowId"));
            if (i != requireIds.size() - 1) {
                stringBuffer.append(",");
            }
        }

        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(Maps.getLong(inputMap, "materialId"), " AND MATERIAL_ID = ?");
        sqlCon.addCondition(stringBuffer.toString(), " AND REQUIRE_ID IN ?", false, true);
        sqlCon.addMainSql(" ORDER BY EMERGENCY_TYPE DESC, ROW_ID ASC");

        List<RequireDetailModel> list = getList(requireDetailMapper, sqlCon);

        if (list != null && !list.isEmpty()) {
            for (RequireDetailModel requireDetailModel : list) {
                Map<String, Object> map = requireDetailModel.getData();
                map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
                map.put("emergencyTypeName", CacheUtil.getName(Maps.getString(map, "emergencyType"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.EMERGENCY_TYPE));
                map.put("requireQtyUnit", Maps.getString(map, "requireQty") + Maps.getString(map, "unit"));

            }
        }

        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchMaterialFromPurchaseTableToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.BILL_CODE AS billCode, T1.BILL_DATE AS billDate, T1.STATUS AS status");
        sqlCon.addMainSql(" , T1.EVENT_CODE AS eventCode");
        sqlCon.addMainSql(" , ROUND(SUM(T2.PURCHASE_QTY * T2.ACTUAL_PRICE),2) AS totalPrice, T1.NOTES AS notes");
        sqlCon.addMainSql(" , (SELECT EMPLOYEE_NAME FROM HR_O_EMPLOYEE WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND ROW_ID = T2.PURCHASER_ID LIMIT 1) AS purchaserName");
        sqlCon.addMainSql(" , MAX(T2.SUPPLIER_ID) AS supplierId, MAX(T2.ARRIVE_DATE) AS arriveDate");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE T1 INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.ROW_ID = T2.PURCHASE_ID)");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS, " WHERE ( T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " OR T1.STATUS = ? )");
        sqlCon.addConditionWithNull(getFarmId(), " AND T2.REQUIRE_FARM = ?");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND PURCHASE_ID = T1.ROW_ID AND REQUIRE_FARM = T2.REQUIRE_FARM AND INPUT_ID IS NULL)");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_WAREHOUSE WHERE DELETED_FLAG = '0' AND STATUS = '2'");
        sqlCon.addMainSql(" AND FIND_IN_SET(WAREHOUSE_CATEGORY,IF(T1.EVENT_CODE='FEED_PURCHASE','2','1,3,5')) <> 0");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG= '0' AND ROLE_ID = SC_M_WAREHOUSE.OPERATION_ROLE");
        sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ?");
        sqlCon.addMainSql("  LIMIT 1) LIMIT 1)");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "filterId"), " AND T1.ROW_ID <> ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE DESC");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.put("billDate", TimeUtil.format(Maps.getString(map, "billDate"), TimeUtil.DATE_FORMAT));
                map.put("arriveDate", TimeUtil.format(Maps.getString(map, "arriveDate"), TimeUtil.DATE_FORMAT));
                map.put("totalPriceName", Maps.getString(map, "totalPrice") + "元");
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                // map.put("purchaserName", CacheUtil.getName(Maps.getString(map, "purchaserId"), NameEnum.EMPLOYEE_NAME));
                map.put("statusName", SupplyChianConstants.PURCHASE_STORE_STATUS_MAP.get(Maps.getString(map, "status")));
                map.put("eventCodeName", CacheUtil.getName(Maps.getString(map, "eventCode"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.SUPPLYCHIAN_EVENT_CODE));
            }
        }

        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchWarehouseMaterialToPage(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(inputMap, "eventName"))) {
            sqlCon.addMainSql(
                    "SELECT _clearEndZero(T1.actualQty - IFNULL(T2.outputQty,0)) AS existsQty, T1.materialId AS materialId, T1.warehouseId AS warehouseId");
            sqlCon.addMainSql(" FROM( SELECT SUM(A.ACTUAL_QTY) AS actualQty, A.MATERIAL_ID AS materialId, A.WAREHOUSE_ID AS warehouseId");
            sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL A");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL B");
            sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.MATERIAL_ID = B.ROW_ID)");
            sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0' AND A.ACTUAL_QTY > 0");

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getCompanyId(), " AND A.FARM_ID = ?");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

            sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND A.WAREHOUSE_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "filterMaterialIds"), " AND A.MATERIAL_ID NOT IN ?", false, true);
            sqlCon.addCondition(Maps.getString(inputMap, "materialName"), " AND B.MATERIAL_NAME LIKE ?", true, false);
            sqlCon.addMainSql(" GROUP BY A.WAREHOUSE_ID, A.MATERIAL_ID ) T1");
            sqlCon.addMainSql(" LEFT OUTER JOIN (");
            sqlCon.addMainSql(" SELECT SUM(B.OUTPUT_QTY) AS outputQty, B.MATERIAL_ID AS materialId, B.OUTPUT_WAREHOUSE_ID AS warehouseId");
            sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT A INNER JOIN SC_M_BILL_OUTPUT_DETAIL B");
            sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.ROW_ID = B.OUTPUT_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND B.OUTPUT_WAREHOUSE_ID = ?");
            sqlCon.addMainSql(" AND A.FARM_ID = B.FARM_ID)");
            sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND (A.FARM_ID = ?");
                sqlCon.addConditionWithNull(getFarmId(), " OR A.COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getCompanyId(), " AND (A.FARM_ID = ?");
                sqlCon.addConditionWithNull(getCompanyId(), " OR A.COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

            sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_OUTPUT_USE, " AND A.EVENT_CODE = ?");
            sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND A.STATUS = ?");
            sqlCon.addMainSql(" GROUP BY B.OUTPUT_WAREHOUSE_ID, B.MATERIAL_ID) T2");
            sqlCon.addMainSql(" ON (T1.materialId = T2.materialId AND T1.warehouseId = T2.warehouseId)");
            sqlCon.addMainSql(" WHERE T1.actualQty - IFNULL(T2.outputQty,0) > 0");
        } else {
            sqlCon.addMainSql("SELECT _clearEndZero(SUM(A.ACTUAL_QTY)) AS existsQty, A.MATERIAL_ID AS materialId, A.WAREHOUSE_ID AS warehouseId");
            sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL A");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL B");
            sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.MATERIAL_ID = B.ROW_ID)");
            sqlCon.addMainSql("  WHERE A.DELETED_FLAG = '0' AND A.ACTUAL_QTY > 0");
            sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");
            sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND A.WAREHOUSE_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "filterIds"), " AND A.ROW_ID NOT IN ?", false, true);
            sqlCon.addCondition(Maps.getString(inputMap, "materialName"), " AND B.MATERIAL_NAME LIKE ?", true, false);
            if (SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT.equals(Maps.getString(inputMap, "eventName")) && "1".equals(Maps.getString(inputMap,
                    "allotDestination"))) {
                sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_R_WAREHOUSE_MATERIAL_TYPE C");
                sqlCon.addMainSql(" WHERE C.DELETED_FLAG = '0' AND C.MATERIAL_TYPE = B.MATERIAL_FIRST_CLASS");
                sqlCon.addCondition(Maps.getLong(inputMap, "allotWareHouseId"), " AND C.WAREHOUSE_ID = ?)");
            }
            sqlCon.addMainSql(" GROUP BY A.WAREHOUSE_ID, A.MATERIAL_ID");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        setToPage();
        List<Map<String, Object>> warehouseMaterialMapList = paramMapper.getObjectInfos(sqlMap);
        if (warehouseMaterialMapList != null && !warehouseMaterialMapList.isEmpty()) {
            for (Map<String, Object> warehouseMaterialMap : warehouseMaterialMapList) {
                warehouseMaterialMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(warehouseMaterialMap, "materialId"),
                        MaterialInfoEnum.MATERIAL_INFO));
                warehouseMaterialMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(warehouseMaterialMap, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                warehouseMaterialMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(warehouseMaterialMap,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(warehouseMaterialMap, "materialFirstClass")));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(warehouseMaterialMap, "supplierId"));
                warehouseMaterialMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                warehouseMaterialMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                warehouseMaterialMap.put("warehouseName", CacheUtil.getName(Maps.getString(warehouseMaterialMap, "warehouseId"),
                        xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
                warehouseMaterialMap.put("outputMinQtyName", Maps.getString(warehouseMaterialMap, "outputMinQty") + Maps.getString(
                        warehouseMaterialMap, "unit"));
                warehouseMaterialMap.put("existsQtyName", Maps.getString(warehouseMaterialMap, "existsQty") + Maps.getString(warehouseMaterialMap,
                        "unit"));
            }
        }
        return getPageInfo(warehouseMaterialMapList);
    }

    @Override
    public List<Map<String, Object>> searchWarehouseMaterialToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(inputMap, "eventName"))) {
            sqlCon.addMainSql(
                    "SELECT T1.actualQty - IFNULL(T2.outputQty,0) AS existsQty, T1.materialId AS materialId, T1.warehouseId AS warehouseId");
            sqlCon.addMainSql(" FROM( SELECT SUM(A.ACTUAL_QTY) AS actualQty, A.MATERIAL_ID AS materialId, A.WAREHOUSE_ID AS warehouseId");
            sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL A");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL B");
            sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.MATERIAL_ID = B.ROW_ID)");
            sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0' AND A.ACTUAL_QTY > 0");

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getCompanyId(), " AND A.FARM_ID = ?");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

            sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND A.WAREHOUSE_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "filterMaterialIds"), " AND A.MATERIAL_ID NOT IN ?", false, true);
            sqlCon.addCondition(Maps.getString(inputMap, "materialName"), " AND B.MATERIAL_NAME LIKE ?", true, false);
            sqlCon.addMainSql(" GROUP BY A.WAREHOUSE_ID, A.MATERIAL_ID ) T1");
            sqlCon.addMainSql(" LEFT OUTER JOIN (");
            sqlCon.addMainSql(" SELECT SUM(B.OUTPUT_QTY) AS outputQty, B.MATERIAL_ID AS materialId, B.OUTPUT_WAREHOUSE_ID AS warehouseId");
            sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT A INNER JOIN SC_M_BILL_OUTPUT_DETAIL B");
            sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.ROW_ID = B.OUTPUT_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND B.OUTPUT_WAREHOUSE_ID = ?");
            sqlCon.addMainSql(" AND A.FARM_ID = B.FARM_ID)");
            sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND (A.FARM_ID = ?");
                sqlCon.addConditionWithNull(getFarmId(), " OR A.COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getCompanyId(), " AND (A.FARM_ID = ?");
                sqlCon.addConditionWithNull(getCompanyId(), " OR A.COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

            sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_OUTPUT_USE, " AND A.EVENT_CODE = ?");
            sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND A.STATUS = ?");
            sqlCon.addMainSql(" GROUP BY B.OUTPUT_WAREHOUSE_ID, B.MATERIAL_ID) T2");
            sqlCon.addMainSql(" ON (T1.materialId = T2.materialId AND T1.warehouseId = T2.warehouseId)");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T3");
            sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T1.materialId = T3.ROW_ID)");
            sqlCon.addMainSql(" WHERE T1.actualQty - IFNULL(T2.outputQty,0) > 0");
            sqlCon.addMainSql(" ORDER BY T3.MATERIAL_NAME");
        } else {
            sqlCon.addMainSql("SELECT SUM(A.ACTUAL_QTY) AS existsQty, A.MATERIAL_ID AS materialId, A.WAREHOUSE_ID AS warehouseId");
            sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL A");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL B");
            sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.MATERIAL_ID = B.ROW_ID)");
            sqlCon.addMainSql("  WHERE A.DELETED_FLAG = '0' AND A.ACTUAL_QTY > 0");
            sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");
            sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND A.WAREHOUSE_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "filterIds"), " AND A.ROW_ID NOT IN ?", false, true);
            sqlCon.addCondition(Maps.getString(inputMap, "materialName"), " AND B.MATERIAL_NAME LIKE ?", true, false);
            if (SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT.equals(Maps.getString(inputMap, "eventName")) && "1".equals(Maps.getString(inputMap,
                    "allotDestination"))) {
                sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_R_WAREHOUSE_MATERIAL_TYPE C");
                sqlCon.addMainSql(" WHERE C.DELETED_FLAG = '0' AND C.MATERIAL_TYPE = B.MATERIAL_FIRST_CLASS");
                sqlCon.addCondition(Maps.getLong(inputMap, "allotWareHouseId"), " AND C.WAREHOUSE_ID = ?)");
            }
            sqlCon.addMainSql(" GROUP BY A.WAREHOUSE_ID, A.MATERIAL_ID");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> warehouseMaterialMapList = paramMapper.getObjectInfos(sqlMap);
        if (warehouseMaterialMapList != null && !warehouseMaterialMapList.isEmpty()) {
            for (Map<String, Object> warehouseMaterialMap : warehouseMaterialMapList) {
                warehouseMaterialMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(warehouseMaterialMap, "materialId"),
                        MaterialInfoEnum.MATERIAL_INFO));
                warehouseMaterialMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(warehouseMaterialMap, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                warehouseMaterialMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(warehouseMaterialMap,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(warehouseMaterialMap, "materialFirstClass")));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(warehouseMaterialMap, "supplierId"));
                warehouseMaterialMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                warehouseMaterialMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                warehouseMaterialMap.put("warehouseName", CacheUtil.getName(Maps.getString(warehouseMaterialMap, "warehouseId"),
                        xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
                warehouseMaterialMap.put("outputMinQtyName", Maps.getString(warehouseMaterialMap, "outputMinQty") + Maps.getString(
                        warehouseMaterialMap, "unit"));
                warehouseMaterialMap.put("existsQtyName", Maps.getString(warehouseMaterialMap, "existsQty") + Maps.getString(warehouseMaterialMap,
                        "unit"));
            }
        }

        return warehouseMaterialMapList;
    }

    @Override
    public BasePageInfo searchSupplychianBillToPage(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.BILL_CODE AS billCode, T1.BILL_DATE AS billDate, T1.EVENT_CODE AS eventCode");
        sqlCon.addMainSql(", T1.OUTPUTER_ID AS outputerId, T1.USER_ID AS userId, T1.NOTES AS notes");
        sqlCon.addMainSql(", T3.EMPLOYEE_NAME AS userName, T1.FARM_ID AS outputFarmId");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T1 INNER JOIN SC_M_BILL_OUTPUT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.FARM_ID = T2.FARM_ID AND T1.ROW_ID = T2.OUTPUT_ID AND T2.OUTPUTER_ID IS NOT NULL");
        sqlCon.addMainSql(" AND T2.INPUT_ID IS NULL)");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_O_EMPLOYEE T3");
        sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T1.USER_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(Maps.getString(inputMap, "supplychianEventCode"), " AND T1.EVENT_CODE = ?");
        if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(inputMap, "supplychianEventCode"))) {
            sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND ( T1.STATUS = ?");
            sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, " OR T1.STATUS = ? )");

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND (T1.FARM_ID = ?");
                sqlCon.addConditionWithNull(getFarmId(), " OR T1.COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

        } else {
            if (!SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT.equals(Maps.getString(inputMap, "supplychianEventCode"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
            }
        }

        if (SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT.equals(Maps.getString(inputMap, "supplychianEventCode"))) {
            sqlCon.addCondition(SupplyChianConstants.OUTPUT_ALLOT_STATUS_PASS, " AND T1.STATUS = ?");
            sqlCon.addCondition(getFarmId(), " AND T1.OUTPUT_TO_FARM_ID = ?");
        }
        if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(inputMap, "supplychianEventCode"))
                || SupplyChianConstants.EVENT_CODE_OUTPUT_SCRAP.equals(Maps.getString(inputMap, "supplychianEventCode"))) {
            Map<String, Date> startDateAndEndDateMap = this.getStartDateAndEndDate();
            String startDateString = TimeUtil.format(startDateAndEndDateMap.get(SupplyChianConstants.START_DATE), TimeUtil.DATE_FORMAT);
            sqlCon.addCondition(startDateString, " AND T1.BILL_DATE >= ?");
            // sqlCon.addCondition(TimeUtil.format(endDate, TimeUtil.DATE_FORMAT), " AND T1.BILL_DATE <= ?");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "filterId"), " AND T1.ROW_ID <> ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE DESC, T1.ROW_ID DESC");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        setToPage();
        List<Map<String, Object>> supplychianBillMapList = paramMapper.getObjectInfos(sqlMap);

        if (supplychianBillMapList != null && !supplychianBillMapList.isEmpty()) {
            for (Map<String, Object> supplychianBillMap : supplychianBillMapList) {
                Map<String, String> outputFarmIdNameMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(supplychianBillMap, "outputFarmId"));
                supplychianBillMap.put("outputFarmIdName", Maps.getString(outputFarmIdNameMap, "SORT_NAME"));
                supplychianBillMap.put("billDate", TimeUtil.format(Maps.getString(supplychianBillMap, "billDate"), TimeUtil.DATE_FORMAT));
                supplychianBillMap.put("outputerName", CacheUtil.getName(Maps.getString(supplychianBillMap, "outputerId"), NameEnum.EMPLOYEE_NAME));
                // supplychianBillMap.put("userName", CacheUtil.getName(Maps.getString(supplychianBillMap, "userId"), NameEnum.EMPLOYEE_NAME));
                supplychianBillMap.put("eventCodeName", CacheUtil.getName(Maps.getString(supplychianBillMap, "eventCode"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.SUPPLYCHIAN_EVENT_CODE));
                supplychianBillMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(supplychianBillMap, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                supplychianBillMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(supplychianBillMap,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(supplychianBillMap, "materialFirstClass")));
            }
        }

        return getPageInfo(supplychianBillMapList);
    }

    @Override
    public List<WarehouseModel> searchWarehouseByMaterialTypeToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(SupplyChianConstants.WAREHOUSE_STATUS_USING, " AND STATUS = ?");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            SqlCon useWarehouseSqlCon = new SqlCon();
            useWarehouseSqlCon.addMainSql("SELECT 1 FROM SC_R_USE_WAREHOUSE WHERE DELETED_FLAG = '0'");
            useWarehouseSqlCon.addConditionWithNull(getFarmId(), " AND USE_FARM_ID = ? LIMIT 1");

            Map<String, String> useWarehouseSqlMap = new HashMap<String, String>();
            useWarehouseSqlMap.put("sql", useWarehouseSqlCon.getCondition());

            List<Map<String, Object>> supplychianBillMapList = paramMapper.getObjectInfos(useWarehouseSqlMap);
            // 判断是否含有专用仓库
            if (supplychianBillMapList != null && !supplychianBillMapList.isEmpty()) {
                sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_R_USE_WAREHOUSE WHERE DELETED_FLAG = '0'");
                sqlCon.addMainSql(" AND WAREHOUSE_ID = SC_M_WAREHOUSE.ROW_ID");
                sqlCon.addConditionWithNull(getFarmId(), " AND USE_FARM_ID = ? LIMIT 1)");
            } else {
                sqlCon.addConditionWithNull(getCompanyId(), " AND FARM_ID = ?");
                sqlCon.addMainSql(" AND NOT EXISTS (SELECT 1 FROM SC_R_USE_WAREHOUSE WHERE DELETED_FLAG = '0'");
                sqlCon.addMainSql(" AND WAREHOUSE_ID = SC_M_WAREHOUSE.ROW_ID");
                sqlCon.addMainSql(" AND UNIQUE_FLAG = 'Y' LIMIT 1)");
            }

        } else {
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        }

        sqlCon.addCondition(Maps.getString(inputMap, "warehouseCategory"), " AND WAREHOUSE_CATEGORY = ?");

        if ("true".equals(Maps.getString(inputMap, "roleFilterFlag"))) {
            sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG = '0' AND ROLE_ID = SC_M_WAREHOUSE.OPERATION_ROLE");
            sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ? LIMIT 1)");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "materialFirstClass"),
                " AND EXISTS(SELECT 1 FROM SC_R_WAREHOUSE_MATERIAL_TYPE A WHERE A.DELETED_FLAG = '0' AND A.WAREHOUSE_ID = SC_M_WAREHOUSE.ROW_ID AND A.MATERIAL_TYPE = ?)");
        List<WarehouseModel> warehouseModelList = getList(warehouseMapper, sqlCon);
        for (WarehouseModel warehouseModel : warehouseModelList) {
            if (SupplyChianConstants.WAREHOUSE_TYPE_ENTITY.equals(warehouseModel.getWarehouseType())) {
                warehouseModel.set("warehouseTypeName", "实体型");
            } else if (SupplyChianConstants.WAREHOUSE_TYPE_FICTITIOUS.equals(warehouseModel.getWarehouseType())) {
                warehouseModel.set("warehouseTypeName", "虚拟型");
            }
        }
        return warehouseModelList;
    }

    @Override
    public List<WarehouseModel> searchAllotWareHouseToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(SupplyChianConstants.WAREHOUSE_STATUS_USING, " AND STATUS = ?");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

        } else {
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

        }
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_R_WAREHOUSE_MATERIAL_TYPE A");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0' AND A.WAREHOUSE_ID = SC_M_WAREHOUSE.ROW_ID AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM SC_R_WAREHOUSE_MATERIAL_TYPE B WHERE B.DELETED_FLAG = '0' AND A.MATERIAL_TYPE = B.MATERIAL_TYPE");
        sqlCon.addCondition(Maps.getLong(inputMap, "allotOutputWarehouseId"), " AND B.WAREHOUSE_ID = ?))");
        sqlCon.addCondition(Maps.getLong(inputMap, "allotOutputWarehouseId"), " AND ROW_ID <> ?");
        List<WarehouseModel> warehouseModelList = getList(warehouseMapper, sqlCon);
        for (WarehouseModel warehouseModel : warehouseModelList) {
            if (SupplyChianConstants.WAREHOUSE_TYPE_ENTITY.equals(warehouseModel.getWarehouseType())) {
                warehouseModel.set("warehouseTypeName", "实体型");
            } else if (SupplyChianConstants.WAREHOUSE_TYPE_FICTITIOUS.equals(warehouseModel.getWarehouseType())) {
                warehouseModel.set("warehouseTypeName", "虚拟型");
            }
        }
        return warehouseModelList;
    }

    @Override
    public List<Map<String, String>> searchTurnMaterialToFarmMaterialToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS materialId FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0'");
        sqlCon.addCondition(Maps.getLong(inputMap, "farmId"), " AND FARM_ID = ?");
        sqlCon.addCondition(Maps.getLong(inputMap, "materialId"),
                " AND RELATED_ID = (SELECT RELATED_ID FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0' AND ROW_ID = ?)");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (Map<String, Object> map : list) {
            Map<String, String> resultMap = CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO);
            resultMap.put("materialId", Maps.getString(map, "materialId"));
            resultMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(resultMap, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            resultMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(resultMap, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(resultMap, "materialFirstClass")));
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(resultMap, "supplierId"));
            resultMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            resultMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            result.add(resultMap);
        }
        return result;
    }
    /* END 查找有效物料主数据 */

    /* BEGIN 拆分与合并 */
    @Override
    public void editSplitMaterial(Map<String, Object> inputMap) throws Exception {
        Date currentDate = new Date();

        Double needSplitQty = Maps.getDouble(inputMap, "splitQty");

        String editType = Maps.getString(inputMap, "editType");
        if ("require".equals(editType)) {
            String materialSplit = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_SPLIT_NUMBER, getEmployeeId(), getCompanyId(), getFarmId());

            List<HashMap> dataList = getJsonList(Maps.getString(inputMap, "data"), HashMap.class);

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < dataList.size(); i++) {
                stringBuffer.append(Maps.getString(dataList.get(i), "rowId"));
                if (i != dataList.size() - 1) {
                    stringBuffer.append(",");
                }
            }

            String idsString = stringBuffer.toString();

            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(idsString, " AND ROW_ID IN ?", false, true);
            List<RequireDetailModel> requireDetailModelList = getList(requireDetailMapper, sqlCon);
            List<RequireDetailModel> updateRequireDetailModelList = new ArrayList<RequireDetailModel>();
            List<RequireDetailModel> insertRequireDetailModelList = new ArrayList<RequireDetailModel>();
            for (RequireDetailModel requireDetailModel : requireDetailModelList) {
                // 这一条记录够分
                if (requireDetailModel.getRequireQty().compareTo(needSplitQty) > 0) {
                    requireDetailModel.setRequireQty(bigDecimalSubtract(requireDetailModel.getRequireQty(), needSplitQty));

                    Double splitQty = needSplitQty;
                    needSplitQty = 0D;

                    RequireDetailModel insertRequireDetailModel = new RequireDetailModel();
                    insertRequireDetailModel.setStatus(CommonConstants.STATUS);
                    insertRequireDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    insertRequireDetailModel.setNotes(requireDetailModel.getNotes());
                    insertRequireDetailModel.setFarmId(requireDetailModel.getFarmId());
                    insertRequireDetailModel.setCompanyId(requireDetailModel.getCompanyId());
                    insertRequireDetailModel.setRequireId(requireDetailModel.getRequireId());
                    insertRequireDetailModel.setRequireLn(requireDetailModel.getRequireLn());
                    insertRequireDetailModel.setMaterialId(requireDetailModel.getMaterialId());
                    insertRequireDetailModel.setMaterialOnly(requireDetailModel.getMaterialOnly());
                    insertRequireDetailModel.setMaterialSplit(materialSplit);
                    insertRequireDetailModel.setExistsQty(0D);
                    insertRequireDetailModel.setRequireQty(splitQty);
                    insertRequireDetailModel.setApplyId(requireDetailModel.getApplyId());
                    insertRequireDetailModel.setApplyUnitId(requireDetailModel.getApplyUnitId());
                    insertRequireDetailModel.setAccountsUnitId(requireDetailModel.getAccountsUnitId());
                    insertRequireDetailModel.setApplyType(requireDetailModel.getApplyType());
                    insertRequireDetailModel.setEmergencyType(requireDetailModel.getEmergencyType());
                    insertRequireDetailModel.setBillCode(requireDetailModel.getBillCode());
                    insertRequireDetailModel.setBillDate(requireDetailModel.getBillDate());
                    insertRequireDetailModel.setEventCode(requireDetailModel.getEventCode());
                    insertRequireDetailModel.setCreateId(getEmployeeId());
                    insertRequireDetailModel.setCreateDate(currentDate);
                    insertRequireDetailModelList.add(insertRequireDetailModel);
                } else {
                    // 这一条记录不够分或者正好

                    // 直接修改拆分号
                    requireDetailModel.setMaterialSplit(materialSplit);

                    needSplitQty = bigDecimalSubtract(needSplitQty, requireDetailModel.getRequireQty());
                }

                updateRequireDetailModelList.add(requireDetailModel);

                if (needSplitQty.compareTo(0D) == 0) {
                    break;
                }
            }

            if (insertRequireDetailModelList != null && !insertRequireDetailModelList.isEmpty()) {
                requireDetailMapper.inserts(insertRequireDetailModelList);
            }

            if (updateRequireDetailModelList != null && !updateRequireDetailModelList.isEmpty()) {
                requireDetailMapper.updates(updateRequireDetailModelList);
            }
        } else if ("purchase".equals(editType)) {
            String materialSplit = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_SPLIT_NUMBER, getEmployeeId(), getCompanyId(), getFarmId());

            long rowId = Maps.getLong(inputMap, "rowId");
            PurchaseDetailModel updatePurchaseDetailModel = purchaseDetailMapper.searchById(rowId);

            // 有未提交的入库单无法修改订单
            this.haveNotCommitInputBillCheck(updatePurchaseDetailModel.getMaterialOnly());

            updatePurchaseDetailModel.setPurchaseQty(bigDecimalSubtract(updatePurchaseDetailModel.getPurchaseQty(), needSplitQty));

            PurchaseDetailModel insertPurchaseDetailModel = new PurchaseDetailModel();
            insertPurchaseDetailModel.setStatus(CommonConstants.STATUS);
            insertPurchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            insertPurchaseDetailModel.setNotes(updatePurchaseDetailModel.getNotes());
            insertPurchaseDetailModel.setFarmId(updatePurchaseDetailModel.getFarmId());
            insertPurchaseDetailModel.setCompanyId(updatePurchaseDetailModel.getCompanyId());
            insertPurchaseDetailModel.setPurchaseId(updatePurchaseDetailModel.getPurchaseId());
            insertPurchaseDetailModel.setPurchaseLn(updatePurchaseDetailModel.getPurchaseLn());
            insertPurchaseDetailModel.setFreeLn(updatePurchaseDetailModel.getFreeLn());
            insertPurchaseDetailModel.setMaterialId(updatePurchaseDetailModel.getMaterialId());
            insertPurchaseDetailModel.setMaterialOnly(updatePurchaseDetailModel.getMaterialOnly());
            insertPurchaseDetailModel.setMaterialSplit(materialSplit);
            insertPurchaseDetailModel.setActualPrice(updatePurchaseDetailModel.getActualPrice());
            insertPurchaseDetailModel.setActualFreePercent(updatePurchaseDetailModel.getActualFreePercent());
            insertPurchaseDetailModel.setPassQty(0D);
            insertPurchaseDetailModel.setPurchaseQty(needSplitQty);
            insertPurchaseDetailModel.setFreeRelatedId(updatePurchaseDetailModel.getFreeRelatedId());
            insertPurchaseDetailModel.setIsPackage(updatePurchaseDetailModel.getIsPackage());
            insertPurchaseDetailModel.setArriveQty(0D);
            insertPurchaseDetailModel.setGroupOrSelf(updatePurchaseDetailModel.getGroupOrSelf());
            insertPurchaseDetailModel.setSupplierId(updatePurchaseDetailModel.getSupplierId());
            insertPurchaseDetailModel.setRequireFarm(updatePurchaseDetailModel.getRequireFarm());
            insertPurchaseDetailModel.setRebateReason(updatePurchaseDetailModel.getRebateReason());
            insertPurchaseDetailModel.setRebatePrice(0D);
            insertPurchaseDetailModel.setPurchaserId(updatePurchaseDetailModel.getPurchaserId());
            insertPurchaseDetailModel.setArriveDate(updatePurchaseDetailModel.getArriveDate());
            insertPurchaseDetailModel.setBillCode(updatePurchaseDetailModel.getBillCode());
            insertPurchaseDetailModel.setBillDate(updatePurchaseDetailModel.getBillDate());
            insertPurchaseDetailModel.setEventCode(updatePurchaseDetailModel.getEventCode());
            insertPurchaseDetailModel.setCreateId(getEmployeeId());
            insertPurchaseDetailModel.setCreateDate(currentDate);

            purchaseDetailMapper.update(updatePurchaseDetailModel);
            purchaseDetailMapper.insert(insertPurchaseDetailModel);
        } else if ("giveOut".equals(editType)) {
            long rowId = Maps.getLong(inputMap, "rowId");
            OutputDetailModel updateOutputDetailModel = outputDetailMapper.searchById(rowId);
            updateOutputDetailModel.setPlanQty(bigDecimalSubtract(updateOutputDetailModel.getPlanQty(), needSplitQty));
            updateOutputDetailModel.setOutputQty(bigDecimalSubtract(updateOutputDetailModel.getOutputQty(), needSplitQty));

            if (SupplyChianConstants.OUTPUT_USE_STATUS_WAITING.equals(updateOutputDetailModel.getStatus())) {
                Thrower.throwException(SupplyChianException.WAITING_OUTPUT_CAN_NOT_SPLIT);
            }

            OutputDetailModel insertOutputStoreModel = new OutputDetailModel();
            insertOutputStoreModel.setStatus(CommonConstants.STATUS);
            insertOutputStoreModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            insertOutputStoreModel.setNotes(updateOutputDetailModel.getNotes());
            insertOutputStoreModel.setFarmId(updateOutputDetailModel.getFarmId());
            insertOutputStoreModel.setCompanyId(updateOutputDetailModel.getCompanyId());
            insertOutputStoreModel.setOutputId(updateOutputDetailModel.getOutputId());
            insertOutputStoreModel.setOutputLn(updateOutputDetailModel.getOutputLn());
            insertOutputStoreModel.setMaterialId(updateOutputDetailModel.getMaterialId());
            insertOutputStoreModel.setPlanQty(needSplitQty);
            insertOutputStoreModel.setOutputQty(needSplitQty);
            insertOutputStoreModel.setOutputWarehouseId(updateOutputDetailModel.getOutputWarehouseId());
            insertOutputStoreModel.setOutputDestination(updateOutputDetailModel.getOutputDestination());
            insertOutputStoreModel.setOutputFarmId(updateOutputDetailModel.getOutputFarmId());
            insertOutputStoreModel.setOutputSwineryId(updateOutputDetailModel.getOutputSwineryId());
            insertOutputStoreModel.setOutputHouseId(updateOutputDetailModel.getOutputHouseId());
            insertOutputStoreModel.setOutputDeptId(updateOutputDetailModel.getOutputDeptId());
            insertOutputStoreModel.setPigType(updateOutputDetailModel.getPigType());
            insertOutputStoreModel.setPigQty(updateOutputDetailModel.getPigQty());
            insertOutputStoreModel.setUserId(updateOutputDetailModel.getUserId());
            insertOutputStoreModel.setBillCode(updateOutputDetailModel.getBillCode());
            insertOutputStoreModel.setBillDate(updateOutputDetailModel.getBillDate());
            insertOutputStoreModel.setEventCode(updateOutputDetailModel.getEventCode());
            insertOutputStoreModel.setCreateId(getEmployeeId());
            insertOutputStoreModel.setCreateDate(currentDate);

            outputDetailMapper.update(updateOutputDetailModel);
            outputDetailMapper.insert(insertOutputStoreModel);
        }
    }

    @Override
    public void editMergeMaterial(Map<String, Object> inputMap) throws Exception {
        String editType = Maps.getString(inputMap, "editType");

        if ("require".equals(editType)) {
            List<HashMap> firstDataList = getJsonList(Maps.getString(inputMap, "firstData"), HashMap.class);
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < firstDataList.size(); i++) {
                stringBuffer.append(Maps.getString(firstDataList.get(i), "rowId"));
                stringBuffer.append(",");
            }

            List<HashMap> secondDataList = getJsonList(Maps.getString(inputMap, "secondData"), HashMap.class);
            for (int i = 0; i < secondDataList.size(); i++) {
                stringBuffer.append(Maps.getString(secondDataList.get(i), "rowId"));
                if (i != secondDataList.size() - 1) {
                    stringBuffer.append(",");
                }
            }

            String idsString = stringBuffer.toString();

            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(idsString, " AND ROW_ID IN ?", false, true);
            sqlCon.addMainSql(" ORDER BY MATERIAL_SPLIT");
            List<RequireDetailModel> requireDetailModelList = getList(requireDetailMapper, sqlCon);

            // 更新第一条的数量
            RequireDetailModel updateRequireDetailModel = new RequireDetailModel();
            updateRequireDetailModel.setRowId(requireDetailModelList.get(0).getRowId());
            updateRequireDetailModel.setRequireQty(bigDecimalAdd(requireDetailModelList.get(0).getRequireQty(), requireDetailModelList.get(1)
                    .getRequireQty()));
            requireDetailMapper.update(updateRequireDetailModel);
            // 删除第二条
            requireDetailMapper.delete(requireDetailModelList.get(1).getRowId());

        } else if ("purchase".equals(editType)) {
            String idsString = Maps.getString(inputMap, "firstData") + "," + Maps.getString(inputMap, "secondData");
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(idsString, " AND ROW_ID IN ?", false, true);
            sqlCon.addMainSql(" ORDER BY MATERIAL_SPLIT");
            List<PurchaseDetailModel> purchaseDetailModelList = getList(purchaseDetailMapper, sqlCon);
            PurchaseDetailModel purchaseDetailModel01 = purchaseDetailModelList.get(0);
            PurchaseDetailModel purchaseDetailModel02 = purchaseDetailModelList.get(1);

            // 有未提交的入库单无法修改订单
            this.haveNotCommitInputBillCheck(purchaseDetailModel01.getMaterialOnly());

            if (purchaseDetailModel01.getPurchaseId().compareTo(purchaseDetailModel02.getPurchaseId()) != 0 || purchaseDetailModel01.getPurchaseLn()
                    .compareTo(purchaseDetailModel02.getPurchaseLn()) != 0 || purchaseDetailModel01.getFreeLn().compareTo(purchaseDetailModel02
                            .getFreeLn()) != 0) {
                Thrower.throwException(SupplyChianException.CAN_NOT_MERGE);
            }

            PurchaseDetailModel updatePurchaseDetailModel = new PurchaseDetailModel();
            updatePurchaseDetailModel.setRowId(purchaseDetailModel01.getRowId());
            updatePurchaseDetailModel.setPurchaseQty(bigDecimalAdd(purchaseDetailModel01.getPurchaseQty(), purchaseDetailModel02.getPurchaseQty()));
            purchaseDetailMapper.update(updatePurchaseDetailModel);

            purchaseDetailMapper.delete(purchaseDetailModel02.getRowId());

        } else if ("giveOut".equals(editType)) {
            String idsString = Maps.getString(inputMap, "firstData") + "," + Maps.getString(inputMap, "secondData");
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(idsString, " AND ROW_ID IN ?", false, true);
            sqlCon.addMainSql(" ORDER BY ROW_ID");
            List<OutputDetailModel> outputDetailModelList = getList(outputDetailMapper, sqlCon);
            OutputDetailModel outputDetailModel01 = outputDetailModelList.get(0);
            OutputDetailModel outputDetailModel02 = outputDetailModelList.get(1);

            if (outputDetailModel01.getOutputId().compareTo(outputDetailModel02.getOutputId()) != 0 || outputDetailModel01.getOutputLn().compareTo(
                    outputDetailModel02.getOutputLn()) != 0) {
                Thrower.throwException(SupplyChianException.CAN_NOT_MERGE);
            }

            if (SupplyChianConstants.OUTPUT_USE_STATUS_WAITING.equals(outputDetailModel01.getStatus())
                    || SupplyChianConstants.OUTPUT_USE_STATUS_WAITING.equals(outputDetailModel02.getStatus())) {
                Thrower.throwException(SupplyChianException.WAITING_OUTPUT_CAN_NOT_MERGE);
            }

            OutputDetailModel updateOutputDetailModel = new OutputDetailModel();
            updateOutputDetailModel.setRowId(outputDetailModel01.getRowId());
            updateOutputDetailModel.setPlanQty(bigDecimalAdd(outputDetailModel01.getPlanQty(), outputDetailModel02.getPlanQty()));
            updateOutputDetailModel.setOutputQty(bigDecimalAdd(outputDetailModel01.getOutputQty(), outputDetailModel02.getOutputQty()));
            outputDetailMapper.update(updateOutputDetailModel);

            outputDetailMapper.delete(outputDetailModel02.getRowId());
        }
    }
    /* END 拆分与合并 */

    /* BEGIN 供应商与物料页面 */
    @Override
    public BasePageInfo searchMaterialToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        Long farmId = getFarmId();

        SqlCon sql = new SqlCon();
        sql.addMainSql("SELECT T1.RELATED_ID AS rowId,T1.NOTES AS notes");
        sql.addMainSql(",T1.IS_WAREHOUSE AS isWarehouse,T1.IS_PURCHASE AS isPurchase,T1.IS_SELL AS isSell,T1.PRICE AS price");
        sql.addMainSql(" ,CONCAT('[',GROUP_CONCAT(CONCAT('{\"parentId\":',''+T1.COMPANY_ID,',')");
        sql.addMainSql(" ,CONCAT('\"farmId\":',''+T1.FARM_ID,'}')),']') AS requestUpdateFarm");
        sql.addMainSql(" FROM CD_M_MATERIAL T1");
        sql.addMainSql(" WHERE T1.STATUS = '1' AND T1.DELETED_FLAG = '0' ");
        sql.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
        sql.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
        sql.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        sql.addMainSql(" AND T1.RELATED_ID IS NOT NULL");
        sql.addMainSql(" AND EXISTS(SELECT 1 FROM CD_M_MATERIAL WHERE STATUS = '1' AND DELETED_FLAG = '0' AND ROW_ID = T1.RELATED_ID");
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ? LIMIT 1)");
        sql.addCondition(Maps.getLongClass(inputMap, "supplierId"), " AND T1.SUPPLIER_ID = ?");
        if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sql.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sql.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sql.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sql.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sql.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
            sql.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        } else if (!"1".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
        }
        sql.addCondition(Maps.getLongClass(inputMap, "canPurchaseSupplierId"), " AND T1.SUPPLIER_ID = ?");
        sql.addCondition(Maps.getString(inputMap, "materialName"), " AND T1.MATERIAL_NAME LIKE ?", true);
        sql.addCondition(Maps.getString(inputMap, "manufacturer"), " AND T1.MANUFACTURER LIKE ?", true);
        sql.addCondition(Maps.getString(inputMap, "materialFirstClass"), " AND T1.MATERIAL_FIRST_CLASS = ?");
        sql.addCondition(Maps.getString(inputMap, "materialSecondClass"), " AND T1.MATERIAL_SECOND_CLASS = ?");
        // sql.addMainSql(" AND T1.IS_PURCHASE = 'Y'");
        sql.addMainSql(" GROUP BY T1.RELATED_ID,T1.IS_WAREHOUSE,T1.IS_PURCHASE,T1.IS_SELL,T1.PRICE,T1.FREE_PERCENT");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sql.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "rowId"), MaterialInfoEnum.MATERIAL_INFO));
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));

                if ("N".equals(Maps.getString(map, "isPurchase"))) {
                    map.put("isPurchaseName", "否");
                } else {
                    map.put("isPurchaseName", "是");
                }
                if ("N".equals(Maps.getString(map, "isSell"))) {
                    map.put("isSellName", "否");
                } else {
                    map.put("isSellName", "是");
                }
                if ("N".equals(Maps.getString(map, "isWarehouse"))) {
                    map.put("isWarehouseName", "否");
                } else {
                    map.put("isWarehouseName", "是");
                }

                List<HashMap> requestUpdateFarmList = getJsonList(Maps.getString(map, "requestUpdateFarm"), HashMap.class);
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

                map.put("alreadyUpdateFarmCount", alreadyUpdateFarmCount);
                map.put("requestUpdateFarm", requestUpdateFarmList);

            }
        }

        return getPageInfo(list);
    }

    @Override
    public List<Map<String, Object>> searchCompanyByFarmIdToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        if ("true".equals(Maps.getString(inputMap, "hasOwnFlag"))) {
            sqlCon.addCondition(getCompanyMark(), " AND (COMPANY_MARK = ?");
            sqlCon.addCondition(getCompanyMark() + ",%", " OR COMPANY_MARK LIKE ?)");
        } else {
            sqlCon.addCondition(getCompanyMark() + ",%", " AND COMPANY_MARK LIKE ?");
        }
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_WAREHOUSE WHERE FARM_ID = HR_M_COMPANY.ROW_ID AND DELETED_FLAG = '0' LIMIT 1)");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "materialId"),
                " AND EXISTS (SELECT 1 FROM CD_M_MATERIAL WHERE FARM_ID = HR_M_COMPANY.ROW_ID AND RELATED_ID = ?)");
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
    public void editMaterialToFarms(Map<String, Object> inputMap) throws Exception {

        List<MaterialModel> updateMaterialModelList = new ArrayList<MaterialModel>();

        List<FeedModel> insertFeedModelList = null;
        List<FeedModel> updateFeedModelList = null;

        List<RawMaterialModel> insertRawMaterialModelList = null;
        List<RawMaterialModel> updateRawMaterialModelList = null;

        List<DrugModel> insertDrugModelList = null;
        List<DrugModel> updateDrugModelList = null;

        List<VaccineModel> insertVaccineModelList = null;
        List<VaccineModel> updateVaccineModelList = null;

        List<DisinfectorModel> insertDisinfectorModelList = null;
        List<DisinfectorModel> updateDisinfectorModelList = null;

        List<DeviceModel> insertDeviceModelList = null;
        List<DeviceModel> updateDeviceModelList = null;

        List<ConsumableModel> insertConsumableModelList = null;
        List<ConsumableModel> updateConsumableModelList = null;

        List<HardwareModel> insertHardwareModelList = null;
        List<HardwareModel> updateHardwareModelList = null;

        List<PpeModel> insertPpeModelList = null;
        List<PpeModel> updatePpeModelList = null;

        List<HashMap> list = getJsonList(Maps.getString(inputMap, "json"), HashMap.class);

        // START HANA
        Date currentDate = new Date();
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        // 控制供应商进行SAP操作
        Set<Long> supplierIdsToSAP = null;
        if (isToHana) {
            supplierIdsToSAP = new HashSet<Long>();
        }
        // END HANA

        for (Map<String, Object> map : list) {

            // 本猪场的MaterialModel
            MaterialModel updateMaterialModel = getBean(MaterialModel.class, map);
            updateMaterialModelList.add(updateMaterialModel);

            // 需要同步的猪场
            List<Map<String, Object>> farmList = Maps.getList(map, "requestUpdateFarm");
            for (Map<String, Object> farmMap : farmList) {
                // 需同步猪场是否有这个主数据
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql("DELETED_FLAG='0'");
                sqlCon.addConditionWithNull(Maps.getLong(farmMap, "farmId"), " AND FARM_ID = ?");
                sqlCon.addCondition(updateMaterialModel.getRowId(), " AND RELATED_ID = ?");
                List<MaterialModel> materialModelList = getAllList(materialMapper, sqlCon);

                Long parentId = Maps.getLong(farmMap, "parentId");
                Long farmId = Maps.getLong(farmMap, "farmId");

                if (materialModelList == null || materialModelList.isEmpty()) {
                    // 原来的猪场不存在这个主数据
                    SqlCon getSrmVersion = new SqlCon();
                    // 平台中公司和自建
                    getSrmVersion.addMainSql(" AND CREATE_TYPE IN ('1','3')");
                    getSrmVersion.addMainSql(" AND CUSSUP_TYPE = 'S'");
                    getSrmVersion.addCondition(updateMaterialModel.getSupplierId(), " AND CUSSUP_ID = ?");

                    List<SrmModel> originSrmModelList = getList(srmMapper, getSrmVersion);

                    SrmModel originSrmModel = originSrmModelList.get(0);

                    // 是否已有同步供的应商
                    SqlCon srmSqlCon = new SqlCon();
                    srmSqlCon.addCondition(farmId, " AND FARM_ID= ?");
                    srmSqlCon.addMainSql(" AND CUSSUP_TYPE= 'S'");
                    srmSqlCon.addCondition(updateMaterialModel.getSupplierId(), " AND CUSSUP_ID = ?");

                    List<SrmModel> srmModelList = getList(srmMapper, srmSqlCon);

                    if (srmModelList == null || srmModelList.isEmpty()) {
                        // START HANA
                        if (isToHana) {
                            supplierIdsToSAP.add(updateMaterialModel.getSupplierId());
                        }
                        // END HANA

                        SrmModel srmModel = new SrmModel();
                        srmModel.setStatus(CommonConstants.STATUS);
                        srmModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        srmModel.setOriginApp(CommonConstants.ORIGIN_APP_INSIDE);
                        srmModel.setOriginFlag(CommonConstants.ORIGIN_FLAG_SYSTEM);
                        srmModel.setFarmId(farmId);
                        srmModel.setCompanyId(parentId);
                        srmModel.setCussupId(updateMaterialModel.getSupplierId());
                        srmModel.setCussupType("S");
                        srmModel.setCreateType("2");
                        srmModel.setVersion(originSrmModel.getVersion());
                        srmMapper.insert(srmModel);

                    }

                    // 原来的猪场不存在这个主数据，同步插入
                    MaterialModel insertMaterialModel = getBean(MaterialModel.class, map);
                    String busCode = ParamUtil.getBCode("MATERIAL_" + insertMaterialModel.getMaterialType().toUpperCase(), getEmployeeId(), parentId,
                            farmId);
                    insertMaterialModel.setBusinessCode(busCode);
                    insertMaterialModel.setCompanyId(parentId);
                    insertMaterialModel.setFarmId(farmId);
                    insertMaterialModel.setRelatedId(updateMaterialModel.getRowId());
                    // 下面的插入操作需要这一次返回的ROWID，所以不能批量插入
                    materialMapper.insert(insertMaterialModel);

                    Long materialId = insertMaterialModel.getRowId();

                    SqlCon sqlCondition = new SqlCon();
                    sqlCondition.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                    sqlCondition.addCondition(updateMaterialModel.getRowId(), " AND MATERIAL_ID = ?");

                    if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertFeedModelList == null) {
                            insertFeedModelList = new ArrayList<FeedModel>();
                        }

                        List<FeedModel> feedModelList = getList(feedMapper, sqlCondition);
                        if (feedModelList != null && !feedModelList.isEmpty()) {
                            FeedModel feedModel = feedModelList.get(0);
                            feedModel.setCompanyId(parentId);
                            feedModel.setFarmId(farmId);
                            feedModel.setMaterialId(materialId);
                            insertFeedModelList.add(feedModel);
                        }

                    } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertRawMaterialModelList == null) {
                            insertRawMaterialModelList = new ArrayList<RawMaterialModel>();
                        }

                        List<RawMaterialModel> rawMaterialModelList = getList(rawMaterialMapper, sqlCondition);
                        if (rawMaterialModelList != null && !rawMaterialModelList.isEmpty()) {
                            RawMaterialModel rawMaterialModel = rawMaterialModelList.get(0);
                            rawMaterialModel.setCompanyId(parentId);
                            rawMaterialModel.setFarmId(farmId);
                            rawMaterialModel.setMaterialId(materialId);
                            insertRawMaterialModelList.add(rawMaterialModel);
                        }

                    } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertDrugModelList == null) {
                            insertDrugModelList = new ArrayList<DrugModel>();
                        }

                        List<DrugModel> drugModelList = getList(drugMapper, sqlCondition);
                        if (drugModelList != null && !drugModelList.isEmpty()) {
                            DrugModel drugModel = drugModelList.get(0);
                            drugModel.setCompanyId(parentId);
                            drugModel.setFarmId(farmId);
                            drugModel.setMaterialId(materialId);
                            insertDrugModelList.add(drugModel);
                        }

                    } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertVaccineModelList == null) {
                            insertVaccineModelList = new ArrayList<VaccineModel>();
                        }

                        List<VaccineModel> vaccineModelList = getList(vaccineMapper, sqlCondition);
                        if (vaccineModelList != null && !vaccineModelList.isEmpty()) {
                            VaccineModel vaccineModel = vaccineModelList.get(0);
                            vaccineModel.setCompanyId(parentId);
                            vaccineModel.setFarmId(farmId);
                            vaccineModel.setMaterialId(materialId);
                            insertVaccineModelList.add(vaccineModel);
                        }

                    } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertDisinfectorModelList == null) {
                            insertDisinfectorModelList = new ArrayList<DisinfectorModel>();
                        }

                        List<DisinfectorModel> disinfectorModelList = getList(disinfectorMapper, sqlCondition);
                        if (disinfectorModelList != null && !disinfectorModelList.isEmpty()) {
                            DisinfectorModel disinfectorModel = disinfectorModelList.get(0);
                            disinfectorModel.setCompanyId(parentId);
                            disinfectorModel.setFarmId(farmId);
                            disinfectorModel.setMaterialId(materialId);
                            insertDisinfectorModelList.add(disinfectorModel);
                        }

                    } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertDeviceModelList == null) {
                            insertDeviceModelList = new ArrayList<DeviceModel>();
                        }

                        List<DeviceModel> deviceModelList = getList(deviceMapper, sqlCondition);
                        if (deviceModelList != null && !deviceModelList.isEmpty()) {
                            DeviceModel deviceModel = deviceModelList.get(0);
                            deviceModel.setCompanyId(parentId);
                            deviceModel.setFarmId(farmId);
                            deviceModel.setMaterialId(materialId);
                            insertDeviceModelList.add(deviceModel);
                        }

                    } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertConsumableModelList == null) {
                            insertConsumableModelList = new ArrayList<ConsumableModel>();
                        }

                        List<ConsumableModel> consumableModelList = getList(consumableMapper, sqlCondition);
                        if (consumableModelList != null && !consumableModelList.isEmpty()) {
                            ConsumableModel consumableModel = consumableModelList.get(0);
                            consumableModel.setCompanyId(parentId);
                            consumableModel.setFarmId(farmId);
                            consumableModel.setMaterialId(materialId);
                            insertConsumableModelList.add(consumableModel);
                        }

                    } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertHardwareModelList == null) {
                            insertHardwareModelList = new ArrayList<HardwareModel>();
                        }

                        List<HardwareModel> hardwareModelList = getList(hardwareMapper, sqlCondition);
                        if (hardwareModelList != null && !hardwareModelList.isEmpty()) {
                            HardwareModel hardwareModel = hardwareModelList.get(0);
                            hardwareModel.setCompanyId(parentId);
                            hardwareModel.setFarmId(farmId);
                            hardwareModel.setMaterialId(materialId);
                            insertHardwareModelList.add(hardwareModel);
                        }

                    } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (insertPpeModelList == null) {
                            insertPpeModelList = new ArrayList<PpeModel>();
                        }

                        List<PpeModel> ppeModelList = getList(ppeMapper, sqlCondition);
                        if (ppeModelList != null && !ppeModelList.isEmpty()) {
                            PpeModel ppeModel = ppeModelList.get(0);
                            ppeModel.setCompanyId(parentId);
                            ppeModel.setFarmId(farmId);
                            ppeModel.setMaterialId(materialId);
                            insertPpeModelList.add(ppeModel);
                        }
                    }
                } else {
                    // 原来的猪场存在这个主数据，同步更新
                    if (materialModelList.size() > 1) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "数据不符合要求");
                    }

                    // 同步猪场的MaterialModel
                    MaterialModel materialModel = materialModelList.get(0);

                    Long materialId = materialModel.getRowId();

                    if (updateMaterialModelList == null) {
                        // 当需要更新时才实例化updateMaterialModelList
                        updateMaterialModelList = new ArrayList<MaterialModel>();
                    }

                    MaterialModel updateMaterialModelToFarm = getBean(MaterialModel.class, map);
                    updateMaterialModelToFarm.setRowId(materialId);
                    updateMaterialModelList.add(updateMaterialModelToFarm);

                    SqlCon sqlCondition = new SqlCon();
                    sqlCondition.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
                    sqlCondition.addCondition(updateMaterialModel.getRowId(), " AND MATERIAL_ID = ?");

                    SqlCon sqlConditionFromFarm = new SqlCon();
                    sqlConditionFromFarm.addMainSql("DELETED_FLAG='0'");
                    sqlConditionFromFarm.addConditionWithNull(farmId, " AND FARM_ID = ?");
                    sqlConditionFromFarm.addCondition(materialId, " AND MATERIAL_ID = ?");

                    if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateFeedModelList == null) {
                            updateFeedModelList = new ArrayList<FeedModel>();
                        }

                        List<FeedModel> feedModelList = getList(feedMapper, sqlCondition);

                        List<FeedModel> feedModelListFromFarm = getAllList(feedMapper, sqlConditionFromFarm);

                        if (feedModelList != null && !feedModelList.isEmpty() && feedModelListFromFarm != null && !feedModelListFromFarm.isEmpty()) {
                            FeedModel fromFeedModel = feedModelList.get(0);

                            FeedModel updateFeedModel = feedModelListFromFarm.get(0);
                            updateFeedModel.setNotes(fromFeedModel.getNotes());
                            updateFeedModel.setShelfLife(fromFeedModel.getShelfLife());
                            updateFeedModelList.add(updateFeedModel);
                        }

                    } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateRawMaterialModelList == null) {
                            updateRawMaterialModelList = new ArrayList<RawMaterialModel>();
                        }

                        List<RawMaterialModel> rawMaterialModelList = getList(rawMaterialMapper, sqlCondition);

                        List<RawMaterialModel> rawMaterialModelListFromFarm = getAllList(rawMaterialMapper, sqlConditionFromFarm);

                        if (rawMaterialModelList != null && !rawMaterialModelList.isEmpty() && rawMaterialModelListFromFarm != null
                                && !rawMaterialModelListFromFarm.isEmpty()) {
                            RawMaterialModel fromRawMaterialModel = rawMaterialModelList.get(0);

                            RawMaterialModel updateRawMaterialModel = rawMaterialModelListFromFarm.get(0);
                            updateRawMaterialModel.setNotes(fromRawMaterialModel.getNotes());
                            updateRawMaterialModel.setRawMaterialType(fromRawMaterialModel.getRawMaterialType());
                            updateRawMaterialModel.setShelfLife(fromRawMaterialModel.getShelfLife());
                            updateRawMaterialModelList.add(updateRawMaterialModel);
                        }

                    } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateDrugModelList == null) {
                            updateDrugModelList = new ArrayList<DrugModel>();
                        }

                        List<DrugModel> drugModelList = getList(drugMapper, sqlCondition);

                        List<DrugModel> drugModelListFromFarm = getAllList(drugMapper, sqlConditionFromFarm);

                        if (drugModelList != null && !drugModelList.isEmpty() && drugModelListFromFarm != null && !drugModelListFromFarm.isEmpty()) {
                            DrugModel fromDrugModel = drugModelList.get(0);

                            DrugModel updateDrugModel = drugModelListFromFarm.get(0);
                            updateDrugModel.setNotes(fromDrugModel.getNotes());
                            updateDrugModel.setDrugType(fromDrugModel.getDrugType());
                            updateDrugModel.setAppearance(fromDrugModel.getAppearance());
                            updateDrugModel.setShelflife(fromDrugModel.getShelflife());
                            updateDrugModel.setPackage(fromDrugModel.getPackage());
                            updateDrugModel.setAdverseReactions(fromDrugModel.getAdverseReactions());
                            updateDrugModel.setApplication(fromDrugModel.getApplication());
                            updateDrugModelList.add(updateDrugModel);
                        }

                    } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateVaccineModelList == null) {
                            updateVaccineModelList = new ArrayList<VaccineModel>();
                        }

                        List<VaccineModel> vaccineModelList = getList(vaccineMapper, sqlCondition);

                        List<VaccineModel> vaccineModelListFromFarm = getAllList(vaccineMapper, sqlConditionFromFarm);

                        if (vaccineModelList != null && !vaccineModelList.isEmpty() && vaccineModelListFromFarm != null && !vaccineModelListFromFarm
                                .isEmpty()) {
                            VaccineModel fromVaccineModel = vaccineModelList.get(0);

                            VaccineModel updateVaccineModel = vaccineModelListFromFarm.get(0);
                            updateVaccineModel.setNotes(fromVaccineModel.getNotes());
                            updateVaccineModel.setVaccineType(fromVaccineModel.getVaccineType());
                            updateVaccineModel.setMainContent(fromVaccineModel.getMainContent());
                            updateVaccineModel.setApperance(fromVaccineModel.getApperance());
                            updateVaccineModel.setEffect(fromVaccineModel.getEffect());
                            updateVaccineModel.setUsageDosage(fromVaccineModel.getUsageDosage());
                            updateVaccineModel.setAdverseReactions(fromVaccineModel.getAdverseReactions());
                            updateVaccineModel.setNotice(fromVaccineModel.getNotice());
                            updateVaccineModelList.add(updateVaccineModel);
                        }

                    } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateDisinfectorModelList == null) {
                            updateDisinfectorModelList = new ArrayList<DisinfectorModel>();
                        }

                        List<DisinfectorModel> disinfectorModelList = getList(disinfectorMapper, sqlCondition);

                        List<DisinfectorModel> disinfectorModelListFromFarm = getAllList(disinfectorMapper, sqlConditionFromFarm);

                        if (disinfectorModelList != null && !disinfectorModelList.isEmpty() && disinfectorModelListFromFarm != null
                                && !disinfectorModelListFromFarm.isEmpty()) {
                            DisinfectorModel fromDisinfectorModel = disinfectorModelList.get(0);

                            DisinfectorModel updateDisinfectorModel = disinfectorModelListFromFarm.get(0);
                            updateDisinfectorModel.setNotes(fromDisinfectorModel.getNotes());
                            updateDisinfectorModel.setDisinfectorModel(fromDisinfectorModel.getDisinfectorModel());
                            updateDisinfectorModel.setShelfLife(fromDisinfectorModel.getShelfLife());
                            updateDisinfectorModel.setApperance(fromDisinfectorModel.getApperance());
                            updateDisinfectorModel.setPack(fromDisinfectorModel.getPack());
                            updateDisinfectorModel.setDisinfectorType(fromDisinfectorModel.getDisinfectorType());
                            updateDisinfectorModel.setEffect(fromDisinfectorModel.getEffect());
                            updateDisinfectorModel.setAdvantage(fromDisinfectorModel.getAdvantage());
                            updateDisinfectorModel.setDisadvantage(fromDisinfectorModel.getDisadvantage());
                            updateDisinfectorModel.setUsageDosage(fromDisinfectorModel.getUsageDosage());
                            updateDisinfectorModel.setNotice(fromDisinfectorModel.getNotice());
                            updateDisinfectorModel.setXjfzt(fromDisinfectorModel.getXjfzt());
                            updateDisinfectorModel.setFzgj(fromDisinfectorModel.getFzgj());
                            updateDisinfectorModel.setZj(fromDisinfectorModel.getZj());
                            updateDisinfectorModel.setBmbd(fromDisinfectorModel.getBmbd());
                            updateDisinfectorModel.setFbmbd(fromDisinfectorModel.getFbmbd());
                            updateDisinfectorModel.setBz(fromDisinfectorModel.getBz());
                            updateDisinfectorModel.setYjw(fromDisinfectorModel.getYjw());
                            updateDisinfectorModel.setYs(fromDisinfectorModel.getYs());
                            updateDisinfectorModel.setXdj(fromDisinfectorModel.getXdj());
                            updateDisinfectorModelList.add(fromDisinfectorModel);
                        }

                    } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateDeviceModelList == null) {
                            updateDeviceModelList = new ArrayList<DeviceModel>();
                        }

                        List<DeviceModel> deviceModelList = getList(deviceMapper, sqlCondition);

                        List<DeviceModel> deviceModelListFromFarm = getAllList(deviceMapper, sqlConditionFromFarm);

                        if (deviceModelList != null && !deviceModelList.isEmpty() && deviceModelListFromFarm != null && !deviceModelListFromFarm
                                .isEmpty()) {
                            DeviceModel fromDeviceModel = deviceModelList.get(0);

                            DeviceModel updateDeviceModel = deviceModelListFromFarm.get(0);
                            updateDeviceModel.setNotes(fromDeviceModel.getNotes());
                            updateDeviceModel.setBrand(fromDeviceModel.getBrand());
                            updateDeviceModel.setModel(fromDeviceModel.getModel());
                            updateDeviceModel.setVoltage(fromDeviceModel.getVoltage());
                            updateDeviceModel.setPower(fromDeviceModel.getPower());
                            updateDeviceModel.setAnlb(fromDeviceModel.getAnlb());
                            updateDeviceModel.setDirection(fromDeviceModel.getDirection());
                            updateDeviceModelList.add(updateDeviceModel);
                        }

                    } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateConsumableModelList == null) {
                            updateConsumableModelList = new ArrayList<ConsumableModel>();
                        }

                        List<ConsumableModel> consumableModelList = getList(consumableMapper, sqlCondition);

                        List<ConsumableModel> consumableModelListFromFarm = getAllList(consumableMapper, sqlConditionFromFarm);

                        if (consumableModelList != null && !consumableModelList.isEmpty() && consumableModelListFromFarm != null
                                && !consumableModelListFromFarm.isEmpty()) {
                            ConsumableModel fromConsumableModel = consumableModelList.get(0);

                            ConsumableModel updateConsumableModel = consumableModelListFromFarm.get(0);
                            updateConsumableModel.setNotes(fromConsumableModel.getNotes());
                            updateConsumableModel.setBrand(fromConsumableModel.getBrand());
                            updateConsumableModel.setModel(fromConsumableModel.getModel());
                            updateConsumableModel.setDirection(fromConsumableModel.getDirection());
                            updateConsumableModelList.add(updateConsumableModel);
                        }

                    } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updateHardwareModelList == null) {
                            updateHardwareModelList = new ArrayList<HardwareModel>();
                        }

                        List<HardwareModel> hardwareModelList = getList(hardwareMapper, sqlCondition);

                        List<HardwareModel> hardwareModelListFromFarm = getAllList(hardwareMapper, sqlConditionFromFarm);

                        if (hardwareModelList != null && !hardwareModelList.isEmpty() && hardwareModelListFromFarm != null
                                && !hardwareModelListFromFarm.isEmpty()) {
                            HardwareModel fromHardwareModel = hardwareModelList.get(0);

                            HardwareModel updateHardwareModel = hardwareModelListFromFarm.get(0);
                            updateHardwareModel.setNotes(fromHardwareModel.getNotes());
                            updateHardwareModel.setBrand(fromHardwareModel.getBrand());
                            updateHardwareModel.setModel(fromHardwareModel.getModel());
                            updateHardwareModelList.add(updateHardwareModel);
                        }

                    } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(updateMaterialModel.getMaterialType())) {
                        if (updatePpeModelList == null) {
                            updatePpeModelList = new ArrayList<PpeModel>();
                        }

                        List<PpeModel> ppeModelList = getList(ppeMapper, sqlCondition);

                        List<PpeModel> ppeModelListFromFarm = getAllList(ppeMapper, sqlConditionFromFarm);

                        if (ppeModelList != null && !ppeModelList.isEmpty() && ppeModelListFromFarm != null && !ppeModelListFromFarm.isEmpty()) {
                            PpeModel fromPpeModel = ppeModelList.get(0);

                            PpeModel updatePpeModel = ppeModelListFromFarm.get(0);
                            updatePpeModel.setNotes(fromPpeModel.getNotes());
                            updatePpeModel.setBrand(fromPpeModel.getBrand());
                            updatePpeModel.setModel(fromPpeModel.getModel());
                            updatePpeModel.setDirection(fromPpeModel.getDirection());
                            updatePpeModelList.add(updatePpeModel);
                        }
                    }
                }
            }
        }

        if (updateMaterialModelList != null && !updateMaterialModelList.isEmpty()) {
            materialMapper.updates(updateMaterialModelList);
        }

        if (insertFeedModelList != null && !insertFeedModelList.isEmpty()) {
            feedMapper.inserts(insertFeedModelList);
        }

        if (updateFeedModelList != null && !updateFeedModelList.isEmpty()) {
            feedMapper.updates(updateFeedModelList);
        }

        if (insertRawMaterialModelList != null && !insertRawMaterialModelList.isEmpty()) {
            rawMaterialMapper.inserts(insertRawMaterialModelList);
        }

        if (updateRawMaterialModelList != null && !updateRawMaterialModelList.isEmpty()) {
            rawMaterialMapper.updates(updateRawMaterialModelList);
        }

        if (insertDrugModelList != null && !insertDrugModelList.isEmpty()) {
            drugMapper.inserts(insertDrugModelList);
        }

        if (updateDrugModelList != null && !updateDrugModelList.isEmpty()) {
            drugMapper.updates(updateDrugModelList);
        }

        if (insertVaccineModelList != null && !insertVaccineModelList.isEmpty()) {
            vaccineMapper.inserts(insertVaccineModelList);
        }

        if (updateVaccineModelList != null && !updateVaccineModelList.isEmpty()) {
            vaccineMapper.updates(updateVaccineModelList);
        }

        if (insertDisinfectorModelList != null && !insertDisinfectorModelList.isEmpty()) {
            disinfectorMapper.inserts(insertDisinfectorModelList);
        }

        if (updateDisinfectorModelList != null && !updateDisinfectorModelList.isEmpty()) {
            disinfectorMapper.updates(updateDisinfectorModelList);
        }

        if (insertDeviceModelList != null && !insertDeviceModelList.isEmpty()) {
            deviceMapper.inserts(insertDeviceModelList);
        }

        if (updateDeviceModelList != null && !updateDeviceModelList.isEmpty()) {
            deviceMapper.updates(updateDeviceModelList);
        }

        if (insertConsumableModelList != null && !insertConsumableModelList.isEmpty()) {
            consumableMapper.inserts(insertConsumableModelList);
        }

        if (updateConsumableModelList != null && !updateConsumableModelList.isEmpty()) {
            consumableMapper.updates(updateConsumableModelList);
        }

        if (insertHardwareModelList != null && !insertHardwareModelList.isEmpty()) {
            hardwareMapper.inserts(insertHardwareModelList);
        }

        if (updateHardwareModelList != null && !updateHardwareModelList.isEmpty()) {
            hardwareMapper.updates(updateHardwareModelList);
        }

        if (insertPpeModelList != null && !insertPpeModelList.isEmpty()) {
            ppeMapper.inserts(insertPpeModelList);
        }

        if (updatePpeModelList != null && !updatePpeModelList.isEmpty()) {
            ppeMapper.updates(updatePpeModelList);
        }

        // START HANA
        if (isToHana) {
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            if (!supplierIdsToSAP.isEmpty()) {
                for (Long supplierId : supplierIdsToSAP) {
                    CompanyModel supplierModel = companyMapper.searchById(supplierId);

                    HanaClientOrProvider hanaClientOrProvider = new HanaClientOrProvider();
                    // 业务伙伴类别
                    hanaClientOrProvider.setMTC_CardType("S");
                    // 供应商编号
                    hanaClientOrProvider.setMTC_CardCode(supplierModel.getCompanyCode());
                    // 供应商名称
                    hanaClientOrProvider.setMTC_CardName(supplierModel.getCompanyName());
                    // 供应商分类
                    hanaClientOrProvider.setMTC_GroupCode("101");
                    // 供应商简称
                    hanaClientOrProvider.setMTC_CardFName(supplierModel.getSortName());
                    // 供应商地址
                    hanaClientOrProvider.setMTC_AliasName(supplierModel.getCompanyAddress());
                    // 供应商-归属
                    SqlCon srmSqlCon = new SqlCon();
                    srmSqlCon.addCondition(supplierId, " AND CUSSUP_ID = ?");
                    srmSqlCon.addCondition(CompanyConstants.CUS_SUP_TYPE_SUP, " AND CUSSUP_TYPE = ?");
                    srmSqlCon.addCondition(CompanyConstants.CREATE_TYPE_BUILT, " AND CREATE_TYPE = ?");
                    List<SrmModel> srmModelList = getList(srmMapper, srmSqlCon);

                    Set<String> mtcBranchSet = new HashSet<String>();
                    List<HanaClientOrProvider.MTC_Branch> mtcBranchDetailList = new ArrayList<HanaClientOrProvider.MTC_Branch>();
                    for (SrmModel srmModel : srmModelList) {
                        Map<String, String> mtcBranchIDAndMTC_DeptID = HanaUtil.getMTC_BranchIDAndMTC_DeptID(srmModel.getFarmId());
                        String mtcBranchId = Maps.getString(mtcBranchIDAndMTC_DeptID, HanaUtil.MTC_BranchID);
                        if (!mtcBranchSet.contains(mtcBranchId)) {
                            mtcBranchSet.add(mtcBranchId);

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
                    // 业务代码:供应商
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1080);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(supplierModel.getCompanyCode());
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
                    mtcITFCList.add(mtcITFC);
                }
            }
            if (!mtcITFCList.isEmpty()) {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
        }
        // END HANA

    }

    @Override
    public void editUpdateMaterialDetailToFarms(Map<String, Object> inputMap) throws Exception {

        MaterialModel updateMaterialModel = new MaterialModel();
        updateMaterialModel.setNotes(Maps.getString(inputMap, "notes"));
        updateMaterialModel.setRelatedId(Maps.getLong(inputMap, "rowId"));
        updateMaterialModel.setMaterialName(Maps.getString(inputMap, "materialName"));
        updateMaterialModel.setGroupId(Maps.getLongClass(inputMap, "groupId"));
        updateMaterialModel.setUnit(Maps.getString(inputMap, "unit"));
        updateMaterialModel.setManufacturer(Maps.getString(inputMap, "manufacturer"));
        updateMaterialModel.setSupplierId(Maps.getLongClass(inputMap, "supplierId"));
        updateMaterialModel.setSpec(Maps.getString(inputMap, "spec"));
        updateMaterialModel.setSpecNum(Maps.getDoubleClass(inputMap, "specNum"));
        updateMaterialModel.setSpecAll(Maps.getString(inputMap, "specAll"));
        updateMaterialModel.setOutputMinQty(Maps.getDouble(inputMap, "outputMinQty"));
        updateMaterialModel.setMaterialFirstClass(Maps.getString(inputMap, "materialFirstClass"));
        updateMaterialModel.setMaterialSecondClass(Maps.getString(inputMap, "materialSecondClass"));
        updateMaterialModel.setMaterialClassNumber(Maps.getString(inputMap, "materialClassNumber"));
        updateMaterialModel.setFinanceSubject(Maps.getString(inputMap, "financeSubject"));

        // 判断是否通过修改和原有物料相同
        SqlCon existsSqlCon = new SqlCon();
        existsSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        existsSqlCon.addCondition(updateMaterialModel.getMaterialName(), " AND MATERIAL_NAME = ?");
        existsSqlCon.addCondition(updateMaterialModel.getSpecAll(), " AND SPEC_ALL = ?");
        existsSqlCon.addCondition(updateMaterialModel.getSupplierId(), " AND SUPPLIER_ID = ?");
        existsSqlCon.addCondition(updateMaterialModel.getManufacturer(), " AND MANUFACTURER = ?");
        existsSqlCon.addCondition(updateMaterialModel.getRelatedId(), " AND ROW_ID <> ?");
        List<MaterialModel> existsList = getList(materialMapper, existsSqlCon);
        if (existsList != null && !existsList.isEmpty()) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, updateMaterialModel.getMaterialName());
        }

        materialMapper.updateToFarms(updateMaterialModel);

        String materialType = Maps.getString(inputMap, "materialType");
        if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
            FeedModel feedModel = getBean(FeedModel.class, inputMap);
            feedMapper.updateToFarms(feedModel);
        } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
            RawMaterialModel rawMaterialModel = getBean(RawMaterialModel.class, inputMap);
            rawMaterialMapper.updateToFarms(rawMaterialModel);
        } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
            DrugModel drugModel = getBean(DrugModel.class, inputMap);
            drugMapper.updateToFarms(drugModel);
        } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
            VaccineModel vaccineModel = getBean(VaccineModel.class, inputMap);
            vaccineMapper.updateToFarms(vaccineModel);
        } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
            DisinfectorModel disinfectorModel = getBean(DisinfectorModel.class, inputMap);
            disinfectorMapper.updateToFarms(disinfectorModel);
        } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
            DeviceModel deviceModel = getBean(DeviceModel.class, inputMap);
            deviceMapper.updateToFarms(deviceModel);
        } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
            ConsumableModel consumableModel = getBean(ConsumableModel.class, inputMap);
            consumableMapper.updateToFarms(consumableModel);
        } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
            HardwareModel hardwareModel = getBean(HardwareModel.class, inputMap);
            hardwareMapper.updateToFarms(hardwareModel);
        } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
            PpeModel ppeModel = getBean(PpeModel.class, inputMap);
            ppeMapper.updateToFarms(ppeModel);
        }

        // START HANA
        // 不是猪只和精液
        if (!("Boar".equals(updateMaterialModel.getMaterialType()) || "Sow".equals(updateMaterialModel.getMaterialType()) || "PorkPig".equals(
                updateMaterialModel.getMaterialType()) || "Sperm".equals(updateMaterialModel.getMaterialType()))) {
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());

            if (isToHana) {
                Date currentDate = new Date();

                // 新农以外暂时无法同步到SAP
                if (!(("1,2,3").equals(getCompanyMark()))) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "新农以外暂时无法同步到SAP");
                }

                String mtcCardCode = HanaUtil.getMTC_CardCode(updateMaterialModel.getSupplierId());

                Map<String, String> materialInfoMap = CacheUtil.getMaterialInfo(String.valueOf(updateMaterialModel.getRelatedId()),
                        MaterialInfoEnum.MATERIAL_INFO);

                HanaMaterial hanaMaterial = new HanaMaterial();
                // 物料编号
                hanaMaterial.setMTC_ItemCode(Maps.getString(materialInfoMap, "materialClassNumber"));
                // 物料描述
                hanaMaterial.setMTC_ItemName(updateMaterialModel.getMaterialName());
                // 物料规格
                hanaMaterial.setMTC_Spec(updateMaterialModel.getSpecAll());
                // 物料组
                hanaMaterial.setMTC_ItmsGrpCod(Maps.getString(materialInfoMap, "financeSubject"));
                // 计量单位
                hanaMaterial.setMTC_Unit(updateMaterialModel.getUnit());
                // 供应商
                hanaMaterial.setMTC_CardCode(mtcCardCode);
                // 制造商
                hanaMaterial.setMTC_ValidComm(updateMaterialModel.getManufacturer());
                // 管理物料由
                // 物料类为：Y - 批次
                // 费用类：N - 无
                String mtcManBtchNum = null;
                if (hanaMaterial.getMTC_ItmsGrpCod() != null) {
                    switch (hanaMaterial.getMTC_ItmsGrpCod()) {
                    // 原材料-饲料
                    case "101":
                        mtcManBtchNum = "Y";
                        break;
                    // 原材料-药物-针剂
                    case "102":
                        mtcManBtchNum = "Y";
                        break;
                    // 原材料-药物-疫苗
                    case "103":
                        mtcManBtchNum = "Y";
                        break;
                    // 原材料-药物-兽用器械
                    case "104":
                        mtcManBtchNum = "Y";
                        break;
                    // 原材料-药物-粉剂
                    case "105":
                        mtcManBtchNum = "Y";
                        break;
                    // 原材料-药物-消毒剂
                    case "106":
                        mtcManBtchNum = "Y";
                        break;
                    default:
                        mtcManBtchNum = "N";
                        break;
                    }
                } else {
                    mtcManBtchNum = "N";
                }
                hanaMaterial.setMTC_ManBtchNum(mtcManBtchNum);

                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                // mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 业务代码: 物料
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1060);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterial.getMTC_ItemCode());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_U);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterial);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }
        // END HANA

    }
    /* END 供应商与物料页面 */

    /* BEGIN 预计用料记录 */
    @Override
    public BasePageInfo searchDailyRecordToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        // searchType:统计：group 个人: personal
        String searchType = Maps.getString(inputMap, "searchType");

        List<Map<String, Object>> dailyRecordMapList = null;
        if ("personal".equals(searchType)) {
            SqlCon personalSqlCon = new SqlCon();
            personalSqlCon.addMainSql("SELECT ROW_ID AS rowId, STATUS AS status, NOTES AS notes, MATERIAL_ID AS materialId");
            personalSqlCon.addMainSql(" , _clearEndZero(QUANTITY) AS quantity, PLAN_DATE AS planDate,REQUIRE_ID AS requireId FROM SC_M_DAILY_RECORD");
            personalSqlCon.addMainSql(" WHERE DELETED_FLAG='0'");
            personalSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            personalSqlCon.addCondition(getEmployeeId(), " AND CREATE_ID = ?");
            personalSqlCon.addMainSql(" ORDER BY STATUS ASC, PLAN_DATE DESC, ROW_ID DESC");

            Map<String, String> personalSqlMap = new HashMap<String, String>();
            personalSqlMap.put("sql", personalSqlCon.getCondition());

            dailyRecordMapList = paramMapper.getObjectInfos(personalSqlMap);

        } else {
            SqlCon groupSqlCon = new SqlCon();
            groupSqlCon.addMainSql("SELECT GROUP_CONCAT(''+ROW_ID) AS detailRowIds");
            groupSqlCon.addMainSql(" ,MATERIAL_ID AS materialId, _clearEndZero(SUM(QUANTITY)) AS quantity");
            groupSqlCon.addMainSql(
                    " ,_clearEndZero(IFNULL((SELECT SUM(ACTUAL_QTY) FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND FARM_ID = SC_M_DAILY_RECORD.FARM_ID");
            groupSqlCon.addMainSql(" AND MATERIAL_ID = SC_M_DAILY_RECORD.MATERIAL_ID GROUP BY MATERIAL_ID),0)) AS existsQty");
            groupSqlCon.addCondition(SupplyChianConstants.DAILY_RECORD_STATUS_UNCOMPLETED,
                    " FROM SC_M_DAILY_RECORD WHERE STATUS = ? AND DELETED_FLAG = '0'");

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                groupSqlCon.addConditionWithNull(getFarmId(), " AND (FARM_ID = ?");
                groupSqlCon.addConditionWithNull(getFarmId(), " OR COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                groupSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

            if (!Maps.isEmpty(inputMap, "applyType")) {
                groupSqlCon.addMainSql(" AND EXISTS ( SELECT 1 FROM CD_M_MATERIAL WHERE ROW_ID = SC_M_DAILY_RECORD.MATERIAL_ID");
                groupSqlCon.addCondition(Maps.getString(inputMap, "applyType"), " AND MATERIAL_FIRST_CLASS = ?");
                groupSqlCon.addMainSql(")");
            }
            groupSqlCon.addMainSql(" GROUP BY MATERIAL_ID");
            Map<String, String> groupSqlMap = new HashMap<String, String>();
            groupSqlMap.put("sql", groupSqlCon.getCondition());

            dailyRecordMapList = paramMapper.getObjectInfos(groupSqlMap);

        }

        if (dailyRecordMapList != null && !dailyRecordMapList.isEmpty()) {
            for (Map<String, Object> dailyRecordMap : dailyRecordMapList) {
                dailyRecordMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(dailyRecordMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
                dailyRecordMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(dailyRecordMap, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                dailyRecordMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(dailyRecordMap,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(dailyRecordMap, "materialFirstClass")));
                dailyRecordMap.put("quantityUnit", Maps.getString(dailyRecordMap, "quantity") + Maps.getString(dailyRecordMap, "unit"));
                if ("group".equals(searchType)) {
                    dailyRecordMap.put("existsQtyUnit", Maps.getString(dailyRecordMap, "existsQty") + Maps.getString(dailyRecordMap, "unit"));
                }
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(dailyRecordMap, "supplierId"));
                dailyRecordMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                dailyRecordMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                dailyRecordMap.put("statusName", SupplyChianConstants.DAILY_RECORD_STATUS_MAP.get(Maps.getString(dailyRecordMap, "status")));
            }
        }

        return getPageInfo(dailyRecordMapList);
    }

    @Override
    public void editDailyRecord(String editType, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        Date createDate = new Date();
        Long farmId = getFarmId();
        Long companyId = getCompanyId();
        Long createId = getEmployeeId();

        // 判断是否含有固定套餐，比例是否正确
        this.groupMaterialPurchaseCheck(detailList, "quantity");

        // 插入
        if ("insert".equals(editType)) {
            List<DailyRecordModel> insertDailyRecordModelList = new ArrayList<>();
            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "quantity") || Maps.getDouble(detailMap, "quantity") <= 0D) {
                    Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "需求量");
                }
                DailyRecordModel dailyRecordModel = new DailyRecordModel();
                dailyRecordModel.setStatus("1");
                dailyRecordModel.setDeletedFlag("0");
                dailyRecordModel.setNotes(Maps.getString(detailMap, "notes"));
                dailyRecordModel.setFarmId(farmId);
                dailyRecordModel.setCompanyId(companyId);
                dailyRecordModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                dailyRecordModel.setQuantity(Maps.getDouble(detailMap, "quantity"));
                dailyRecordModel.setPlanDate(Maps.getDate(detailMap, "planDate"));
                dailyRecordModel.setCreateId(createId);
                dailyRecordModel.setCreateDate(createDate);
                insertDailyRecordModelList.add(dailyRecordModel);
            }
            dailyRecordMapper.inserts(insertDailyRecordModelList);
        } else {
            List<DailyRecordModel> updateDailyRecordModelList = new ArrayList<DailyRecordModel>();
            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "quantity") || Maps.getDouble(detailMap, "quantity") <= 0D) {
                    Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "需求量");
                }
                DailyRecordModel dailyRecordModel = new DailyRecordModel();
                dailyRecordModel.setData(detailMap);
                updateDailyRecordModelList.add(dailyRecordModel);
            }
            dailyRecordMapper.updates(updateDailyRecordModelList);
        }
    }

    @Override
    public void deleteDailyRecord(long[] ids) throws Exception {
        dailyRecordMapper.deletes(ids);
    }
    /* END 预计用料记录 */

    /* START 统计用料记录 */
    @Override
    public List<Map<String, Object>> searchDailyRecordDetailToList(long[] ids) throws Exception {
        List<Map<String, Object>> dailyRecordList = null;
        if (ids.length > 0) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql(
                    "SELECT T1.PLAN_DATE AS planDate, T1.MATERIAL_ID AS materialId, _clearEndZero(T1.QUANTITY) AS quantity, T1.NOTES AS notes");
            sqlCon.addMainSql(", (SELECT EMPLOYEE_NAME FROM HR_O_EMPLOYEE WHERE DELETED_FLAG = '0' AND ROW_ID = T1.CREATE_ID LIMIT 1) AS worker");
            sqlCon.addMainSql(" FROM SC_M_DAILY_RECORD T1 WHERE T1.DELETED_FLAG='0'");
            sqlCon.addMainSql(" AND T1.ROW_ID IN (");
            for (int i = 0; i < ids.length; i++) {
                sqlCon.addMainSql(String.valueOf(ids[i]));
                if (i != ids.length - 1) {
                    sqlCon.addMainSql(",");
                }
            }
            sqlCon.addMainSql(") ORDER BY T1.PLAN_DATE DESC, T1.ROW_ID DESC");

            Map<String, String> sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            dailyRecordList = paramMapper.getObjectInfos(sqlMap);
            for (Map<String, Object> dailyRecordMap : dailyRecordList) {
                dailyRecordMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(dailyRecordMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
                dailyRecordMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(dailyRecordMap, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                dailyRecordMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(dailyRecordMap,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(dailyRecordMap, "materialFirstClass")));

                dailyRecordMap.put("quantityUnit", Maps.getString(dailyRecordMap, "quantity") + Maps.getString(dailyRecordMap, "unit"));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(dailyRecordMap, "supplierId"));
                dailyRecordMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                dailyRecordMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        } else {
            dailyRecordList = new ArrayList<Map<String, Object>>();
        }
        return dailyRecordList;
    }

    public void editWarehouseEnough(long[] ids) throws Exception {
        if (ids.length > 0) {
            List<DailyRecordModel> dailyRecordModelList = new ArrayList<DailyRecordModel>();
            for (long id : ids) {
                DailyRecordModel dailyRecordModel = new DailyRecordModel();
                dailyRecordModel.setRowId(id);
                dailyRecordModel.setStatus(SupplyChianConstants.DAILY_RECORD_STATUS_WAREHOUSE_ENOUGH);
                dailyRecordModelList.add(dailyRecordModel);
            }
            dailyRecordMapper.updates(dailyRecordModelList);
        }
    }

    /* END 统计用料记录 */

    /* START 需求单 */
    @Override
    public BasePageInfo searchRequireStoreToPage() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.EMERGENCY_TYPE AS emergencyType, T1.BILL_CODE AS billCode");
        sqlCon.addMainSql(" , T1.STATUS AS status, T1.BILL_DATE AS billDate, T1.APPLY_TYPE AS applyType");
        sqlCon.addMainSql(" , T1.APPLY_ID AS applyId, T1.NOTES AS notes, T1.EVENT_CODE AS eventCode");
        sqlCon.addMainSql(" , T1.REQUIRE_TYPE AS requireType");
        sqlCon.addMainSql(" FROM SC_M_BILL_REQUIRE T1");
        sqlCon.addConditionWithNull(getFarmId(), " WHERE T1.DELETED_FLAG = '0' AND T1.FARM_ID = ?");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE DESC, T1.ROW_ID DESC");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        setToPage();
        List<Map<String, Object>> requireStoreMapList = paramMapper.getObjectInfos(sqlMap);
        if (requireStoreMapList != null) {
            for (Map<String, Object> requireStoreMap : requireStoreMapList) {
                requireStoreMap.put("billDate", TimeUtil.format(Maps.getString(requireStoreMap, "billDate"), TimeUtil.DATE_FORMAT));
                requireStoreMap.put("emergencyTypeName", CacheUtil.getName(Maps.getString(requireStoreMap, "emergencyType"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.EMERGENCY_TYPE));
                requireStoreMap.put("applyTypeName", CacheUtil.getName(Maps.getString(requireStoreMap, "applyType"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                requireStoreMap.put("requireTypeName", CacheUtil.getName(Maps.getString(requireStoreMap, "requireType"), NameEnum.CODE_NAME,
                        CodeEnum.REQUIRE_TYPE));
                requireStoreMap.put("worker", CacheUtil.getName(String.valueOf(Maps.getString(requireStoreMap, "applyId")), NameEnum.EMPLOYEE_NAME));
                requireStoreMap.put("statusName", SupplyChianConstants.REQUIRE_STORE_STATUS_MAP.get(Maps.getString(requireStoreMap, "status")));
            }
        }
        return getPageInfo(requireStoreMapList);
    }

    @Override
    public List<StoreMaterialModel> searchRequireStoreDetailToList(Map<String, Object> inputMap) throws Exception {
        Long requireBillId = Maps.getLong(inputMap, "requiredBillId");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT MAX(ROW_ID) AS rowId, MAX(MATERIAL_ID) AS materialId, SUM(REQUIRE_QTY) AS requireQty, NOTES AS notes");
        sqlCon.addMainSql(
                ",(SELECT SUM(ACTUAL_QTY) FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND FARM_ID = SC_M_BILL_REQUIRE_DETAIL.FARM_ID");
        sqlCon.addMainSql(" AND MATERIAL_ID = SC_M_BILL_REQUIRE_DETAIL.MATERIAL_ID GROUP BY MATERIAL_ID) AS existsQty");
        sqlCon.addMainSql(" FROM SC_M_BILL_REQUIRE_DETAIL");
        sqlCon.addCondition(requireBillId, " WHERE DELETED_FLAG = '0' AND REQUIRE_ID = ?");
        sqlCon.addMainSql(" GROUP BY REQUIRE_LN");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> storeMaterialMaplist = paramMapper.getObjectInfos(sqlMap);

        List<StoreMaterialModel> storeMaterialModelList = new ArrayList<StoreMaterialModel>();

        if (storeMaterialMaplist != null) {
            for (Map<String, Object> storeMaterialMap : storeMaterialMaplist) {
                StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                storeMaterialModel.setData(storeMaterialMap);
                storeMaterialMap.putAll(CacheUtil.getMaterialInfo(String.valueOf(storeMaterialModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO));
                storeMaterialMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(storeMaterialMap, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                storeMaterialMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(storeMaterialMap,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(storeMaterialMap, "materialFirstClass")));
                storeMaterialModelList.add(storeMaterialModel);
            }
        } else {
            storeMaterialModelList = new ArrayList<StoreMaterialModel>();
        }

        return storeMaterialModelList;
    }

    @Override
    public List<Map<String, Object>> searchAccountsUnit() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.FARM_ID AS rowId FROM CD_M_MATERIAL T1 WHERE DELETED_FLAG = '0' AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0' AND ROW_ID <> RELATED_ID AND RELATED_ID = T1.ROW_ID");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ? ORDER BY RELATED_ID LIMIT 1) LIMIT 1");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> infos = paramMapper.getObjectInfos(sqlMap);

        if (infos != null && !infos.isEmpty()) {
            for (Map<String, Object> infoMap : infos) {
                infoMap.put("companyName", CacheUtil.getName(Maps.getString(infoMap, "rowId"), NameEnum.COMPANY_NAME));
            }
        } else {
            infos = new ArrayList<Map<String, Object>>();
            Map<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put("rowId", getFarmId());
            infoMap.put("companyName", CacheUtil.getName(Maps.getString(infoMap, "rowId"), NameEnum.COMPANY_NAME));
            infos.add(infoMap);
        }
        return infos;
    }

    @Override
    public void editRequireStore(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        // 判断是否含有固定套餐，比例是否正确
        this.groupMaterialPurchaseCheck(detailList, "requireQty");

        String editType = Maps.getString(inputMap, "editType");
        Date currentDate = new Date();
        // 新增
        if ("insert".equals(editType)) {
            String eventCode = SupplyChianConstants.EVENT_CODE_MATERIAL_REQUIRE;
            String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_REQUIRE_STORE, getEmployeeId(), getCompanyId(), getFarmId());
            RequireModel requireModel = getBean(RequireModel.class, inputMap);
            requireModel.setStatus(SupplyChianConstants.REQUIRE_STORE_STATUS_NOT_SUBMIT);
            requireModel.setFarmId(getFarmId());
            requireModel.setCompanyId(getCompanyId());
            requireModel.setBillCode(billCode);
            requireModel.setEventCode(eventCode);
            requireModel.setApplyId(getEmployeeId());
            requireModel.setApplyUnitId(Maps.getLong(inputMap, "applyUnitId"));
            requireModel.setAccountsUnitId(Maps.getLong(inputMap, "accountsUnitId"));
            requireModel.setApplyType(Maps.getString(inputMap, "applyType"));
            requireModel.setEmergencyType(Maps.getString(inputMap, "emergencyType"));
            requireModel.setCopyId(Maps.getLongClass(inputMap, "copyId"));
            requireModel.setRequireType(Maps.getString(inputMap, "requireType"));
            requireModel.setOaUsername(getOaUsername());
            requireModel.setCreateId(getEmployeeId());
            requireModel.setCreateDate(currentDate);
            requireMapper.insert(requireModel);

            List<RequireDetailModel> requireDetailModelList = new ArrayList<RequireDetailModel>();
            List<DailyRecordModel> dailyRecordModelList = new ArrayList<DailyRecordModel>();

            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "requireQty") || Maps.getDouble(detailMap, "requireQty") <= 0D) {
                    Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "需求量");
                }
                // 特殊：添加药饲料（J开头）必须整除2000KG
                if (SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(detailMap, "materialFirstClass"))) {
                    if (Maps.getString(detailMap, "materialName").startsWith("J")) {
                        BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "requireQty"), 2000D);
                        if (remainder[1].doubleValue() != 0D) {
                            Thrower.throwException(SupplyChianException.ADD_MATERIAL_FEED_REMAINDER_TWO_THOUSAND, Maps.getLong(detailMap,
                                    "lineNumber"), Maps.getString(detailMap, "materialName"));
                        }
                    }
                }

                RequireDetailModel requireDetailModel = new RequireDetailModel();
                requireDetailModel.setStatus(CommonConstants.STATUS);
                requireDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                requireDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                requireDetailModel.setFarmId(getFarmId());
                requireDetailModel.setCompanyId(getCompanyId());
                requireDetailModel.setRequireId(requireModel.getRowId());
                requireDetailModel.setRequireLn(Maps.getLong(detailMap, "lineNumber"));
                requireDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                requireDetailModel.setMaterialOnly(SupplyChianConstants.MATERIAL_ONLY_NUMBER_START);
                requireDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                requireDetailModel.setExistsQty(Maps.getDouble(detailMap, "existsQty", 0D));
                requireDetailModel.setRequireQty(Maps.getDouble(detailMap, "requireQty"));
                requireDetailModel.setApplyId(getEmployeeId());
                requireDetailModel.setApplyUnitId(Maps.getLong(inputMap, "applyUnitId"));
                requireDetailModel.setAccountsUnitId(Maps.getLong(inputMap, "accountsUnitId"));
                requireDetailModel.setApplyType(Maps.getString(inputMap, "applyType"));
                requireDetailModel.setEmergencyType(Maps.getString(inputMap, "emergencyType"));
                requireDetailModel.setRequireType(Maps.getString(inputMap, "requireType"));
                requireDetailModel.setBillCode(billCode);
                requireDetailModel.setBillDate(Maps.getDate(inputMap, "billDate"));
                requireDetailModel.setEventCode(eventCode);
                requireDetailModel.setCreateId(getEmployeeId());
                requireDetailModel.setCreateDate(currentDate);
                requireDetailModelList.add(requireDetailModel);

                String arrayListString = Maps.getString(detailMap, "detailRowIds");

                if (arrayListString != null) {
                    String[] ids = StringUtil.split(arrayListString, ",");
                    if (ids != null && ids.length > 0) {
                        for (int i = 0; i < ids.length; i++) {
                            DailyRecordModel dailyRecordModel = new DailyRecordModel();
                            dailyRecordModel.setRowId(Long.valueOf(ids[i]));
                            dailyRecordModel.setStatus(SupplyChianConstants.DAILY_RECORD_STATUS_COMPLETED);
                            dailyRecordModel.setRequireId(requireModel.getRowId());
                            dailyRecordModelList.add(dailyRecordModel);
                        }
                    }
                }
            }

            if (requireDetailModelList != null && !requireDetailModelList.isEmpty()) {
                requireDetailMapper.inserts(requireDetailModelList);
            }

            if (dailyRecordModelList != null && !dailyRecordModelList.isEmpty()) {
                dailyRecordMapper.updates(dailyRecordModelList);
            }
        } else {
            RequireModel requireModel = new RequireModel();
            requireModel.setRowId(Maps.getLongClass(inputMap, "rowId"));
            requireModel.setBillDate(Maps.getDate(inputMap, "billDate"));
            // supplychianBillModel.setStatus(SupplyChianConstants.REQUIRE_STORE_STATUS_PENDING);
            requireModel.setStatus(SupplyChianConstants.REQUIRE_STORE_STATUS_NOT_SUBMIT);
            requireModel.setEmergencyType(Maps.getString(inputMap, "emergencyType"));
            requireModel.setRequireType(Maps.getString(inputMap, "requireType"));
            requireModel.setNotes(Maps.getString(inputMap, "notes"));

            // 添加明细
            List<RequireDetailModel> insertRequireDetailModelList = new ArrayList<RequireDetailModel>();
            // 更新明细
            List<RequireDetailModel> updateRequireDetailModelList = new ArrayList<RequireDetailModel>();

            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "requireQty") || Maps.getDouble(detailMap, "requireQty") <= 0D) {
                    Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "需求量");
                }
                // 特殊：添加药饲料（J开头）必须整除2000KG
                if (SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(detailMap, "materialFirstClass"))) {
                    if (Maps.getString(detailMap, "materialName").startsWith("J")) {
                        BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "requireQty"), 2000D);
                        if (remainder[1].doubleValue() != 0D) {
                            Thrower.throwException(SupplyChianException.ADD_MATERIAL_FEED_REMAINDER_TWO_THOUSAND, Maps.getLong(detailMap,
                                    "lineNumber"), Maps.getString(detailMap, "materialName"));
                        }
                    }
                }

                if (Maps.getLongClass(detailMap, "rowId") == null) {
                    // 新增的明细
                    RequireDetailModel requireDetailModel = new RequireDetailModel();
                    requireDetailModel.setStatus("1");
                    requireDetailModel.setDeletedFlag("0");
                    requireDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                    requireDetailModel.setFarmId(getFarmId());
                    requireDetailModel.setCompanyId(getCompanyId());
                    requireDetailModel.setRequireId(requireModel.getRowId());
                    requireDetailModel.setRequireLn(Maps.getLong(detailMap, "lineNumber"));
                    requireDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                    requireDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                    requireDetailModel.setExistsQty(Maps.getDouble(detailMap, "existsQty", 0D));
                    requireDetailModel.setRequireQty(Maps.getDouble(detailMap, "requireQty"));
                    requireDetailModel.setApplyId(getEmployeeId());
                    requireDetailModel.setApplyUnitId(Maps.getLong(inputMap, "applyUnitId"));
                    requireDetailModel.setAccountsUnitId(Maps.getLong(inputMap, "accountsUnitId"));
                    requireDetailModel.setApplyType(Maps.getString(inputMap, "applyType"));
                    requireDetailModel.setEmergencyType(Maps.getString(inputMap, "emergencyType"));
                    requireDetailModel.setRequireType(Maps.getString(inputMap, "requireType"));
                    requireDetailModel.setBillDate(Maps.getDate(inputMap, "billDate"));
                    requireDetailModel.setCreateId(getEmployeeId());
                    requireDetailModel.setCreateDate(currentDate);
                    insertRequireDetailModelList.add(requireDetailModel);
                } else if (Maps.getLongClass(detailMap, "rowId") != null && Maps.getString(detailMap, "deletedFlag") == null) {
                    // 修改的明细
                    RequireDetailModel requireDetailModel = new RequireDetailModel();
                    requireDetailModel.setRowId(Maps.getLongClass(detailMap, "rowId"));
                    requireDetailModel.setEmergencyType(Maps.getString(inputMap, "emergencyType"));
                    requireDetailModel.setExistsQty(Maps.getDouble(detailMap, "existsQty", 0D));
                    requireDetailModel.setRequireQty(Maps.getDouble(detailMap, "requireQty"));
                    requireDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                    requireDetailModel.setRequireType(Maps.getString(inputMap, "requireType"));
                    updateRequireDetailModelList.add(requireDetailModel);
                }
            }

            requireMapper.update(requireModel);

            if (insertRequireDetailModelList != null && !insertRequireDetailModelList.isEmpty()) {
                requireDetailMapper.inserts(insertRequireDetailModelList);
            }

            if (updateRequireDetailModelList != null && !updateRequireDetailModelList.isEmpty()) {
                requireDetailMapper.updates(updateRequireDetailModelList);
            }
        }
    }

    @Override
    public void editScrapRequireStore(long[] ids, Map<String, Object> inputMap) throws Exception {
        List<RequireModel> requireModelList = new ArrayList<RequireModel>();
        for (long id : ids) {
            RequireModel requireModel = new RequireModel();
            requireModel.setRowId(id);
            requireModel.setNotes("(作废原因：" + Maps.getString(inputMap, "scrapReason", "") + ")");
            requireModel.setStatus(SupplyChianConstants.REQUIRE_STORE_STATUS_SCRAP);
            requireModelList.add(requireModel);
        }
        requireMapper.updatesForScrap(requireModelList);

        // 删除采购明细
        requireDetailMapper.updatesForScrap(ids);

        // 恢复日常用料
        String idsString = longArrayListToString(ids);
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(SupplyChianConstants.DAILY_RECORD_STATUS_UNCOMPLETED, "UPDATE SC_M_DAILY_RECORD SET STATUS = ?");
        sqlCon.addCondition(idsString, " , REQUIRE_ID = NULL WHERE DELETED_FLAG = '0' AND REQUIRE_ID IN ?", false, true);
        setSql(dailyRecordMapper, sqlCon);
    }

    @Override
    public void editSubmitRequireStore(long[] ids) throws Exception {
        List<RequireModel> requireModelList = new ArrayList<RequireModel>();
        for (long id : ids) {
            RequireModel requireModel = new RequireModel();
            requireModel.setRowId(id);
            requireModel.setStatus(SupplyChianConstants.REQUIRE_STORE_STATUS_PENDING);
            requireModelList.add(requireModel);
        }
        requireMapper.updates(requireModelList);

    }
    /* END 需求单 */

    /* START 采购订单 */
    /* START 集团集购 */

    @Override
    public List<Map<String, Object>> searchPurchaseTypeByEmployeeIdToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.PURCHASE_TYPE AS purchaseType, T1.SUPPLIER_ID AS canPurchaseSupplierId FROM CD_O_ROLE T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG = '0' AND T1.ROW_ID = ROLE_ID");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ?)");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> purchaseTypeList = paramMapper.getObjectInfos(sqlMap);
        if (purchaseTypeList.size() == 0) {
            Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
        } else if (purchaseTypeList.size() == 1) {
            if (Maps.isEmpty(purchaseTypeList.get(0), "purchaseType")) {
                Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
            }
            return purchaseTypeList;
        } else {
            boolean allPurchase = false;
            boolean feedPurchase = false;
            boolean materialPurchase = false;
            String canPurchaseSupplierId = null;

            for (Map<String, Object> map : purchaseTypeList) {
                if ("1".equals(Maps.getString(map, "purchaseType"))) {
                    allPurchase = true;
                    break;
                }
                if ("2".equals(Maps.getString(map, "purchaseType"))) {
                    feedPurchase = true;
                }
                if ("3".equals(Maps.getString(map, "purchaseType"))) {
                    materialPurchase = true;
                }
                if (!Maps.isEmpty(map, "canPurchaseSupplierId")) {
                    canPurchaseSupplierId = Maps.getString(map, "canPurchaseSupplierId");
                }
            }
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("canPurchaseSupplierId", canPurchaseSupplierId);
            if (allPurchase || (feedPurchase && materialPurchase)) {
                resultMap.put("purchaseType", "1");
                result.add(resultMap);
                return result;
            } else if (feedPurchase) {
                resultMap.put("purchaseType", "2");
                result.add(resultMap);
                return result;
            } else if (materialPurchase) {
                resultMap.put("purchaseType", "3");
                result.add(resultMap);
                return result;
            } else {
                Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
            }

        }
        return null;
    }

    @Override
    public BasePageInfo searchGroupPurchaseStoreToPage(Map<String, Object> inputMap) throws Exception {
        inputMap.put("searchType", "group");
        return searchPurchaseStoreToPage(inputMap);
    }

    @Override
    public List<Map<String, Object>> searchInputInfoFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception {
        List<HashMap> rowIdList = getJsonList(Maps.getString(inputMap, "rowIds"), HashMap.class);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < rowIdList.size(); i++) {
            stringBuffer.append(Maps.getString(rowIdList.get(i), "rowId"));
            if (i != rowIdList.size() - 1) {
                stringBuffer.append(",");
            }
        }
        String rowIdsString = stringBuffer.toString();

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.BILL_CODE AS billCode, T1.BILL_DATE AS billDate, T1.MATERIAL_ID AS materialId, T1.INPUT_QTY AS inputQty");
        sqlCon.addMainSql(", (SELECT PRODUCTION_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS productionDate");
        sqlCon.addMainSql(", (SELECT EFFECTIVE_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS effectiveDate");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(rowIdsString, " AND INPUT_ID = T1.INPUT_ID AND INPUT_LN = T1.INPUT_LN AND ROW_ID IN ?)", false, true);
        sqlCon.addMainSql("ORDER BY T1.INPUT_ID, T1.INPUT_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> inputInfoMapList = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> inputInfoMap : inputInfoMapList) {
            inputInfoMap.put("billDate", TimeUtil.format(Maps.getString(inputInfoMap, "billDate"), TimeUtil.DATE_FORMAT));
            inputInfoMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(inputInfoMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            inputInfoMap.put("inputQtyName", Maps.getString(inputInfoMap, "inputQty") + Maps.getString(inputInfoMap, "unit"));
        }

        return inputInfoMapList;
    }

    @Override
    public List<Map<String, Object>> searchInputingEditFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT B.minRowId, B.rowIds, B.requireFarm, B.notes, B.farmId, B.companyId, B.purchaseLn, B.freeLn,");
        sqlCon.addMainSql(" _clearEndZero (B.purchaseQty) AS purchaseQty, B.arriveQty, B.materialId,");
        sqlCon.addMainSql(" _clearEndZero (B.actualPrice) AS actualPrice, B.actualFreePercent, B.materialOnly,");
        sqlCon.addMainSql(" B.freeRelatedId, B.isPackage, B.billCode, B.billDate, B.eventCode,  B.arriveDate,");
        sqlCon.addMainSql(" B.rebateReason ,B.rebatePrice ,B.notInputId,");
        sqlCon.addMainSql(" _clearEndZero(B.purchaseQty*B.actualPrice) AS totalPrice FROM(");
        sqlCon.addMainSql("SELECT MIN(A.ROW_ID) AS minRowId,CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',''+A.ROW_ID,'}')),']') AS rowIds");
        sqlCon.addMainSql(", A.REQUIRE_FARM AS requireFarm, A.NOTES AS notes");
        sqlCon.addMainSql(", A.FARM_ID AS farmId, A.COMPANY_ID AS companyId, A.PURCHASE_LN AS purchaseLn, A.FREE_LN AS freeLn");
        sqlCon.addMainSql(", SUM(A.PURCHASE_QTY) AS purchaseQty, _clearEndZero(SUM(A.ARRIVE_QTY)) AS arriveQty");
        sqlCon.addMainSql(", A.MATERIAL_ID AS materialId");
        sqlCon.addMainSql(", A.ACTUAL_PRICE AS actualPrice");
        sqlCon.addMainSql(", A.ACTUAL_FREE_PERCENT AS actualFreePercent, A.MATERIAL_ONLY AS materialOnly");
        sqlCon.addMainSql(", A.FREE_RELATED_ID AS freeRelatedId, A.IS_PACKAGE AS isPackage");
        sqlCon.addMainSql(", A.BILL_CODE AS billCode, A.BILL_DATE AS billDate, A.EVENT_CODE AS eventCode, A.ARRIVE_DATE AS arriveDate");
        sqlCon.addMainSql(", A.REBATE_REASON AS rebateReason, _clearEndZero(SUM(A.REBATE_PRICE)) AS rebatePrice");
        if (SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(", (SELECT MIN(ROW_ID) FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
            sqlCon.addMainSql(" AND PURCHASE_ID = A.PURCHASE_ID AND PURCHASE_LN = A.PURCHASE_LN AND FREE_LN = A.FREE_LN LIMIT 1) AS notInputId");
        } else {
            sqlCon.addMainSql(", (SELECT ROW_ID FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1' AND INPUT_ID IS NULL");
            sqlCon.addMainSql(" AND PURCHASE_ID = A.PURCHASE_ID AND PURCHASE_LN = A.PURCHASE_LN AND FREE_LN = A.FREE_LN LIMIT 1) AS notInputId");
        }
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL A WHERE A.DELETED_FLAG='0'");
        sqlCon.addCondition(Maps.getLong(inputMap, "purchaseId"), " AND A.PURCHASE_ID = ?");
        sqlCon.addMainSql(" GROUP BY A.PURCHASE_ID, A.PURCHASE_LN, A.FREE_LN");
        sqlCon.addMainSql(" )B ");
        sqlCon.addMainSql(" ORDER BY B.purchaseLn, B.freeLn");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        MathContext mathContext = new MathContext(3, RoundingMode.HALF_UP);
        DecimalFormat df = new DecimalFormat("#0");

        List<Map<String, Object>> purchaseStoreMapList = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> purchaseStoreMap : purchaseStoreMapList) {
            purchaseStoreMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(purchaseStoreMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            purchaseStoreMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(purchaseStoreMap, "materialFirstClass"),
                    NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
            purchaseStoreMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(purchaseStoreMap,
                    "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(purchaseStoreMap, "materialFirstClass")));

            purchaseStoreMap.put("actualPriceName", Maps.getString(purchaseStoreMap, "actualPrice") + "元");
            purchaseStoreMap.put("totaliPriceName", Maps.getString(purchaseStoreMap, "totalPrice") + "元");

            purchaseStoreMap.put("oldPurchaseQty", Maps.getDouble(purchaseStoreMap, "purchaseQty"));
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(purchaseStoreMap, "requireFarm"));
            purchaseStoreMap.put("requireFarmSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            purchaseStoreMap.put("requireFarmName", Maps.getString(companyInfoMap, "COMPANY_NAME"));

            purchaseStoreMap.put("purchaseQtyName", Maps.getString(purchaseStoreMap, "purchaseQty") + Maps.getString(purchaseStoreMap, "unit"));
            purchaseStoreMap.put("arriveQtyName", Maps.getString(purchaseStoreMap, "arriveQty") + Maps.getString(purchaseStoreMap, "unit"));
            purchaseStoreMap.put("arrivePurchasePercent", df.format(bigDecimalDividePercent(Maps.getDouble(purchaseStoreMap, "arriveQty"), Maps
                    .getDouble(purchaseStoreMap, "purchaseQty"), mathContext)) + "%");

            if (Maps.getLong(purchaseStoreMap, "freeLn") == 0D) {
                purchaseStoreMap.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
            } else {
                purchaseStoreMap.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
            }

            if (SupplyChianConstants.REBATE_REASON_A.equals(Maps.getString(purchaseStoreMap, "rebateReason"))) {
                purchaseStoreMap.put("rebateReason", null);
                purchaseStoreMap.put("rebateReasonName", "");
            } else {
                purchaseStoreMap.put("rebateReasonName", SupplyChianConstants.REBATE_REASON_MAP.get(Maps.getString(purchaseStoreMap,
                        "rebateReason")));
            }
        }

        return purchaseStoreMapList;
    }

    @Override
    public List<Map<String, Object>> searchPurchaseStoreDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',''+T2.ROW_ID,'}')),']') AS rowIds");
        sqlCon.addMainSql(", CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',T2.REQUIRE_ID,'}')),']') AS requireIds");
        sqlCon.addMainSql(", SUM(T2.REQUIRE_QTY) AS requireQty, T1.ROW_ID AS rowId");
        sqlCon.addMainSql(", T1.REQUIRE_FARM AS requireFarm, T1.MATERIAL_ID AS materialId");
        sqlCon.addMainSql(", IFNULL(T2.REQUIRE_QTY,0) AS requireQty, T1.ACTUAL_PRICE AS actualPrice, T1.NOTES AS notes");
        sqlCon.addMainSql(", T1.ACTUAL_FREE_PERCENT AS actualFreePercent, SUM(T1.PURCHASE_QTY) AS purchaseQty, T1.IS_PACKAGE AS isPackage");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE T1");
        sqlCon.addMainSql(" LEFT OUTER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.PURCHASE_ID = T2.PURCHASE_ID AND T1.PURCHASE_LN = T2.PURCHASE_LN)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.FREE_RELATED_ID IS NULL");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLong(inputMap, "purchaseId"), " AND T1.PURCHASE_ID = ?");
        sqlCon.addMainSql(" GROUP BY T1.PURCHASE_ID, T1.PURCHASE_LN, T1.FREE_LN");
        sqlCon.addMainSql(" ORDER BY T1.PURCHASE_LN, T1.FREE_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> purchaseStoreModelList = paramMapper.getObjectInfos(sqlMap);

        if (purchaseStoreModelList != null) {
            SqlCon freeSqlCon = new SqlCon();
            freeSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            freeSqlCon.addCondition(Maps.getLong(inputMap, "purchaseId"), " AND PURCHASE_ID = ?");
            freeSqlCon.addMainSql(" AND FREE_RELATED_ID IS NOT NULL");
            List<PurchaseDetailModel> freeModelAllList = getList(purchaseDetailMapper, freeSqlCon);

            for (Map<String, Object> purchaseStoreModel : purchaseStoreModelList) {
                purchaseStoreModel.putAll(CacheUtil.getMaterialInfo(Maps.getString(purchaseStoreModel, "materialId"),
                        MaterialInfoEnum.MATERIAL_INFO));
                purchaseStoreModel.put("materialFirstClassName", CacheUtil.getName(Maps.getString(purchaseStoreModel, "materialFirstClass"),
                        NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
                purchaseStoreModel.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(purchaseStoreModel,
                        "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(purchaseStoreModel, "materialFirstClass")));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(purchaseStoreModel, "requireFarm"));
                purchaseStoreModel.put("requireFarmSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                purchaseStoreModel.put("requireFarmName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                purchaseStoreModel.put("totalPrice", bigDecimalMultiply(Maps.getDouble(purchaseStoreModel, "actualPrice"), Maps.getDouble(
                        purchaseStoreModel, "purchaseQty")));

                List<Map<String, Object>> freeModelList = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < freeModelAllList.size(); i++) {
                    Map<String, Object> freeMap = freeModelAllList.get(i).getData();
                    if (Maps.getLong(purchaseStoreModel, "rowId") == Maps.getLong(freeMap, "freeRelatedId")) {
                        freeMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(freeMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
                        freeModelList.add(freeMap);
                        freeModelAllList.remove(i);
                        i--;
                    }
                }
                purchaseStoreModel.put("freeInfo", freeModelList);
            }
        }

        return purchaseStoreModelList;
    }

    @Override
    public void editGroupPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception {
        String editType = Maps.getString(inputMap, "editType");
        inputMap.put("purchaseType", "group");

        if ("insert".equals(editType)) {
            insertPurchaseStore(inputMap, gridListString);
        } else {
            editPurchaseStore(inputMap, gridListString);
        }
    }

    @Override
    public void editScrapGroupPurchaseStore(long[] ids, Map<String, Object> inputMap) throws Exception {
        editScrapPurchaseStore(ids, inputMap);

        // 获取需要恢复的需求单
        String purchaseIdsString = longArrayListToString(ids);

        SqlCon getRequireBill = new SqlCon();
        getRequireBill.addMainSql("SELECT ROW_ID FROM SC_M_BILL_REQUIRE T1 WHERE DELETED_FLAG = '0' AND EXISTS (");
        getRequireBill.addMainSql(" SELECT 1 FROM SC_M_BILL_REQUIRE_DETAIL WHERE DELETED_FLAG = '0' AND REQUIRE_ID = T1.ROW_ID");
        getRequireBill.addCondition(purchaseIdsString, " AND PURCHASE_ID IN ? LIMIT 1)", false, true);

        Map<String, String> getRequireBillMap = new HashMap<String, String>();
        getRequireBillMap.put("sql", getRequireBill.getCondition());

        List<RequireModel> getRequireModelList = setSql(requireMapper, getRequireBill);

        // 恢复需求明细
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_ONLY_NUMBER_START,
                "UPDATE SC_M_BILL_REQUIRE_DETAIL SET PURCHASE_ID = NULL, PURCHASE_LN = NULL, MATERIAL_ONLY = ?");
        sqlCon.addCondition(purchaseIdsString, " WHERE DELETED_FLAG = '0' AND PURCHASE_ID IN ?", false, true);
        setSql(requireDetailMapper, sqlCon);

        // 恢复需求单
        if (getRequireModelList != null && !getRequireModelList.isEmpty()) {
            List<Long> requireIdsList = new ArrayList<Long>();
            for (RequireModel requireModel : getRequireModelList) {
                requireIdsList.add(requireModel.getRowId());
            }
            updateRequireStoreStatus(requireIdsList);
        }
    }

    @Override
    public void editOverGroupPurchaseStore(long[] ids) throws Exception {
        Date currentDate = new Date();

        List<PurchaseModel> purchaseModelList = new ArrayList<PurchaseModel>();
        Map<String, MTC_ITFC> mtcITFCMap = new HashMap<String, MTC_ITFC>();

        for (long id : ids) {
            PurchaseModel purchaseModel = purchaseMapper.searchById(id);
            purchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_OVER);
            purchaseModelList.add(purchaseModel);

            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(id, " AND PURCHASE_ID = ?");
            sqlCon.addMainSql(" AND STATUS = '1'");
            List<PurchaseDetailModel> purchaseDetailModelList = getList(purchaseDetailMapper, sqlCon);
            for (PurchaseDetailModel purchaseDetailModel : purchaseDetailModelList) {
                // START HANA
                boolean isToHana = HanaUtil.isToHanaCheck(purchaseDetailModel.getRequireFarm());
                if (isToHana) {
                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(purchaseDetailModel.getRequireFarm());
                    String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

                    String mtcDocNum = mtcBranchID + "-" + purchaseModel.getRowId() + "-" + purchaseModel.getBillCode();

                    if (!mtcITFCMap.containsKey(mtcDocNum)) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(purchaseDetailModel.getBillDate());
                        // 业务代码:采购订单
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                        // 新农+单据编号：分公司编码-单据ROW_ID-单据单号
                        mtcITFC.setMTC_DocNum(mtcDocNum);
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_C);
                        // 原始单据编号
                        mtcITFC.setMTC_BaseEntry(mtcDocNum);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(purchaseDetailModel.getRequireFarm()));
                        // JSON文件
                        HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                        hanaPurchaseBill.setMTC_BaseEntry(mtcDocNum);
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                        mtcITFCMap.put(mtcDocNum, mtcITFC);
                    }
                }
                // END HANA
            }
        }
        purchaseMapper.updates(purchaseModelList);

        // START HANA
        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
        for (MTC_ITFC mtcITFC : mtcITFCMap.values()) {
            mtcITFCList.add(mtcITFC);
        }

        if (!mtcITFCList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
        // END HANA

    }

    /* END 集团集购 */

    /* START 猪场自购 */
    @Override
    public BasePageInfo searchSelfPurchaseStoreToPage(Map<String, Object> inputMap) throws Exception {
        inputMap.put("searchType", "self");
        return searchPurchaseStoreToPage(inputMap);
    }

    @Override
    public void editSelfPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception {
        String editType = Maps.getString(inputMap, "editType");
        inputMap.put("purchaseType", "self");
        if ("insert".equals(editType)) {
            insertPurchaseStore(inputMap, gridListString);
        } else {
            editPurchaseStore(inputMap, gridListString);
        }
    }

    public void editScrapSelfPurchaseStore(long[] ids, Map<String, Object> inputMap) throws Exception {
        editScrapPurchaseStore(ids, inputMap);
    }
    /* END 猪场自购 */

    @Override
    public void editInputingPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "billDate"));

        Date currentDate = new Date();

        PurchaseModel purchaseModel = purchaseMapper.searchById(Maps.getLong(inputMap, "rowId"));
        if (!Maps.isEmpty(inputMap, "notes")) {
            purchaseModel.setNotes(Maps.getString(inputMap, "notes"));
        }

        List<PurchaseDetailModel> insertPurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
        List<PurchaseDetailModel> updatePurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
        List<Long> deleteIdsList = new ArrayList<Long>();
        List<PurchaseDetailModel> updateOtherInfoList = new ArrayList<PurchaseDetailModel>();

        // START HANA
        // 第一个String是dbBeanName，第二个String是baseEntry
        Map<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMap = new HashMap<String, Map<String, HanaPurchaseBill>>();
        // END HANA

        for (Map<String, Object> detailMap : detailList) {

            Double purchaseQty = Maps.getDouble(detailMap, "purchaseQty", 0D);

            // BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "purchaseQty"), 1D);
            // 数量必须为整数
            // if (remainder[1].doubleValue() != 0D) {
            // Thrower.throwException(SupplyChianException.PURCHASE_QTY_MUST_BE_INTEGER, Maps.getLong(detailMap, "lineNumber"));
            // }

            // 如果存在赠料比例
            if (!Maps.isEmpty(detailMap, "actualFreePercent")) {
                // 赠料比例中包含"/"，必须满足整除"/"之前的数字
                if (Maps.getString(detailMap, "actualFreePercent").contains("/")) {
                    String actualFreePercent = Maps.getString(detailMap, "actualFreePercent");
                    String actualFreePercentHead = actualFreePercent.substring(0, actualFreePercent.indexOf("/"));
                    BigDecimal[] actualFreePercentRemainder = bigDecimalDivideAndRemainder(purchaseQty, Double.valueOf(actualFreePercentHead));
                    if (actualFreePercentRemainder[1].doubleValue() != 0D) {
                        Thrower.throwException(SupplyChianException.PURCHASE_QTY_MUST_DIVIDED_WITH_NO_REMAINDER, Maps.getLong(detailMap,
                                "lineNumber"), Maps.getString(detailMap, "actualFreePercent"), actualFreePercentHead);
                    }
                }
            }

            if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
                // 新农旗下
                if (isXNCompany(getCompanyMark())) {
                    if (Maps.getDouble(detailMap, "actualPrice", 0D) == 0D) {
                        if (Maps.isEmpty(detailMap, "rebateReason")) {
                            Thrower.throwException(SupplyChianException.REBATE_REASON_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
                        }
                    }
                }
            }

            // START HANA
            Long requireFarmId = null;
            if (SupplyChianConstants.GROUP_OR_SELF_GROUP.equals(purchaseModel.getGroupOrSelf())) {
                if (Maps.isEmpty(detailMap, "requireFarm")) {
                    Thrower.throwException(SupplyChianException.REQUIRE_FARM_CAN_NOT_NULL, Maps.getLong(detailMap, "lineNumber"));
                }
                // 集团采购
                requireFarmId = Maps.getLong(detailMap, "requireFarm");
            } else {
                // 本场采购
                requireFarmId = getFarmId();
            }

            boolean isToHana = HanaUtil.isToHanaCheck(requireFarmId);

            List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = null;
            String mtcBranchID = null;
            String mtcDeptID = null;

            if (isToHana) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(requireFarmId);
                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                String mtcCardCode = HanaUtil.getMTC_CardCode(Maps.getLong(detailMap, "supplierId"));

                String dbBeanName = HanaUtil.getDbBeanName(requireFarmId);

                String baseEntry = mtcBranchID + "-" + String.valueOf(purchaseModel.getRowId()) + "-" + purchaseModel.getBillCode();

                // 如果dbBeanName不存在
                if (!dbBeanNameHanaPurchaseBillMap.containsKey(dbBeanName)) {
                    Map<String, HanaPurchaseBill> hanaPurchaseBillMap = new HashMap<String, HanaPurchaseBill>();

                    HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                    // 分公司编码
                    hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                    // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                    hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                    // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                    hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                    // 业务伙伴编号
                    hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                    // 采购日期
                    hanaPurchaseBill.setMTC_DocDate(purchaseModel.getBillDate());
                    // 到货日期
                    hanaPurchaseBill.setMTC_DocDueDate(purchaseModel.getArriveDate());
                    // 表行
                    hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                    hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                    hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                    dbBeanNameHanaPurchaseBillMap.put(dbBeanName, hanaPurchaseBillMap);
                } else {
                    // 如果dbBeanName存在,获取hanaPurchaseBillMap
                    Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMap.get(dbBeanName);

                    // 如果baseEntry不存在
                    if (!hanaPurchaseBillMap.containsKey(baseEntry)) {
                        HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                        // 分公司编码
                        hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                        // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                        // 业务伙伴编号
                        hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                        // 采购日期
                        hanaPurchaseBill.setMTC_DocDate(purchaseModel.getBillDate());
                        // 到货日期
                        hanaPurchaseBill.setMTC_DocDueDate(purchaseModel.getArriveDate());
                        // 表行
                        hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                        hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);
                        hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                    } else {
                        // 如果baseEntry存在
                        hanaPurchaseBillDetailList = hanaPurchaseBillMap.get(baseEntry).getDetailList();
                    }
                }

            }
            // END HANA

            // 有未提交的入库单无法修改订单
            this.haveNotCommitInputBillCheck(Maps.getString(detailMap, "materialOnly"), Maps.getLong(detailMap, "lineNumber"));

            // 减数量了
            if (Maps.getDouble(detailMap, "oldPurchaseQty") > purchaseQty) {
                // 修改数量不能少于已入库数
                if (Maps.getDouble(detailMap, "arriveQty") > purchaseQty) {
                    Thrower.throwException(SupplyChianException.PURCHASE_QTY_LESS_THAN_ARRIVE_QTY, Maps.getLong(detailMap, "lineNumber"));
                }

                // 采购的与到库的不相等 或者 采购数量一共为0
                if (purchaseQty != Maps.getDouble(detailMap, "arriveQty") || purchaseQty == 0D) {
                    // 修改未入库采购记录的数量
                    SqlCon purchaseDetailSqlCon = new SqlCon();
                    purchaseDetailSqlCon.addConditionWithNull(Maps.getLong(detailMap, "notInputId"), " AND ROW_ID = ? FOR UPDATE");
                    PurchaseDetailModel purchaseDetailModel = getList(purchaseDetailMapper, purchaseDetailSqlCon).get(0);

                    if (!SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
                        purchaseDetailModel.setPurchaseQty(bigDecimalSubtract(purchaseQty, Maps.getDouble(detailMap, "arriveQty")));
                    } else {
                        purchaseDetailModel.setPurchaseQty(purchaseQty);
                    }
                    updatePurchaseDetailModelList.add(purchaseDetailModel);
                } else {
                    // 饲料采购不需要删除
                    if (!SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
                        // 删除未入库采购记录
                        deleteIdsList.add(Maps.getLong(detailMap, "notInputId"));
                    }
                }

                // 加数量了
            } else if (Maps.getDouble(detailMap, "oldPurchaseQty") < purchaseQty) {
                if (Maps.isEmpty(detailMap, "notInputId")) {
                    PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
                    purchaseDetailModel.setStatus(CommonConstants.STATUS);
                    purchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    purchaseDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                    purchaseDetailModel.setFarmId(Maps.getLong(detailMap, "farmId"));
                    purchaseDetailModel.setCompanyId(Maps.getLong(detailMap, "companyId"));
                    purchaseDetailModel.setPurchaseId(Maps.getLong(inputMap, "rowId"));
                    purchaseDetailModel.setPurchaseLn(Maps.getLong(detailMap, "purchaseLn"));
                    purchaseDetailModel.setFreeLn(Maps.getLong(detailMap, "freeLn"));
                    purchaseDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                    purchaseDetailModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
                    purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                    purchaseDetailModel.setActualPrice(Maps.getDouble(detailMap, "actualPrice"));
                    purchaseDetailModel.setActualFreePercent(Maps.getString(detailMap, "actualFreePercent"));
                    purchaseDetailModel.setPassQty(0D);
                    if (!SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
                        purchaseDetailModel.setPurchaseQty(bigDecimalSubtract(purchaseQty, Maps.getDouble(detailMap, "arriveQty")));
                    } else {
                        purchaseDetailModel.setPurchaseQty(purchaseQty);
                    }
                    purchaseDetailModel.setFreeRelatedId(Maps.getLongClass(detailMap, "freeRelatedId"));
                    purchaseDetailModel.setIsPackage(Maps.getString(detailMap, "isPackage"));
                    purchaseDetailModel.setArriveQty(0D);
                    purchaseDetailModel.setGroupOrSelf(Maps.getString(inputMap, "groupOrSelf"));
                    purchaseDetailModel.setSupplierId(Maps.getLong(detailMap, "supplierId"));
                    purchaseDetailModel.setRequireFarm(Maps.getLong(detailMap, "requireFarm"));
                    if (Maps.getLong(detailMap, "freeLn") == 0L) {
                        purchaseDetailModel.setRebateReason(Maps.getString(detailMap, "rebateReason", SupplyChianConstants.REBATE_REASON_A));
                    } else {
                        purchaseDetailModel.setRebateReason(SupplyChianConstants.REBATE_REASON_B);
                    }
                    purchaseDetailModel.setRebatePrice(0D);
                    purchaseDetailModel.setPurchaserId(getEmployeeId());
                    purchaseDetailModel.setArriveDate(Maps.getDate(detailMap, "arriveDate"));
                    purchaseDetailModel.setBillCode(Maps.getString(detailMap, "billCode"));
                    purchaseDetailModel.setBillDate(Maps.getDate(detailMap, "billDate"));
                    purchaseDetailModel.setEventCode(Maps.getString(detailMap, "eventCode"));
                    purchaseDetailModel.setCreateId(getEmployeeId());
                    purchaseDetailModel.setCreateDate(currentDate);
                    insertPurchaseDetailModelList.add(purchaseDetailModel);
                } else {
                    SqlCon purchaseDetailSqlCon = new SqlCon();
                    purchaseDetailSqlCon.addConditionWithNull(Maps.getLong(detailMap, "notInputId"), " AND ROW_ID = ? FOR UPDATE");
                    PurchaseDetailModel purchaseDetailModel = getList(purchaseDetailMapper, purchaseDetailSqlCon).get(0);
                    if (!SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
                        purchaseDetailModel.setPurchaseQty(bigDecimalSubtract(purchaseQty, Maps.getDouble(detailMap, "arriveQty")));
                    } else {
                        purchaseDetailModel.setPurchaseQty(purchaseQty);
                    }
                    updatePurchaseDetailModelList.add(purchaseDetailModel);
                }
            }

            // 将返利金额填写到第一行
            PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
            purchaseDetailModel.setRowId(Maps.getLong(detailMap, "minRowId"));
            purchaseDetailModel.setRebatePrice(Maps.getDouble(detailMap, "rebatePrice", 0D));
            updatePurchaseDetailModelList.add(purchaseDetailModel);

            // 获取更新单价单中的单价
            Double actualPrice = getUpdatePriceForMaterial(inputMap, detailMap);

            if (Maps.getLong(detailMap, "freeLn") == 0L) {
                if (actualPrice == null) {
                    actualPrice = Maps.getDouble(detailMap, "actualPrice", 0D);
                }
            } else {
                actualPrice = Maps.getDouble(detailMap, "actualPrice", 0D);
            }

            PurchaseDetailModel otherInfoModel = new PurchaseDetailModel();
            otherInfoModel.setNotes(Maps.getString(detailMap, "notes"));
            otherInfoModel.setPurchaseId(Maps.getLong(inputMap, "rowId"));
            otherInfoModel.setPurchaseLn(Maps.getLong(detailMap, "purchaseLn"));
            otherInfoModel.setFreeLn(Maps.getLong(detailMap, "freeLn"));
            otherInfoModel.setRebateReason(Maps.getString(detailMap, "rebateReason", SupplyChianConstants.REBATE_REASON_A));
            otherInfoModel.setActualPrice(actualPrice);
            otherInfoModel.setActualFreePercent(Maps.getString(detailMap, "actualFreePercent"));
            updateOtherInfoList.add(otherInfoModel);

            // START HANA
            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(Maps.getLong(detailMap, "materialId"));
                HanaPurchaseBillDetail hanaPurchaseBillDetail = new HanaPurchaseBillDetail();
                // 分公司编码
                hanaPurchaseBillDetail.setMTC_BranchID(mtcBranchID);
                // 猪场编码
                hanaPurchaseBillDetail.setMTC_DeptID(mtcDeptID);
                // 新农+单据编号
                hanaPurchaseBillDetail.setMTC_BaseEntry(String.valueOf(purchaseModel.getRowId()));
                // 新农+单据行号
                hanaPurchaseBillDetail.setMTC_BaseLine(Maps.getString(detailMap, "purchaseLn") + "*" + Maps.getString(detailMap, "freeLn"));
                // 采购类型
                hanaPurchaseBillDetail.setMTC_PurType(Maps.getString(detailMap, "rebateReason", SupplyChianConstants.REBATE_REASON_A));
                // 返利金额
                hanaPurchaseBillDetail.setMTC_Rebate(Maps.getString(detailMap, "rebatePrice", "0"));
                // 业务类型
                hanaPurchaseBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                // 物料编号
                hanaPurchaseBillDetail.setMTC_ItemCode(mtcItemCode);
                // 采购数量
                hanaPurchaseBillDetail.setMTC_Qty(String.valueOf(purchaseQty));
                // 采购价格
                hanaPurchaseBillDetail.setMTC_QtyPrice(String.valueOf(actualPrice));
                // 采购金额
                Double totalPrice = bigDecimalMultiply(purchaseQty, actualPrice);
                hanaPurchaseBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                hanaPurchaseBillDetailList.add(hanaPurchaseBillDetail);
            }
            // END HANA

        }

        if (purchaseModel != null) {
            purchaseMapper.update(purchaseModel);
        }

        if (insertPurchaseDetailModelList != null && !insertPurchaseDetailModelList.isEmpty()) {
            purchaseDetailMapper.inserts(insertPurchaseDetailModelList);
        }

        if (updatePurchaseDetailModelList != null && !updatePurchaseDetailModelList.isEmpty()) {
            purchaseDetailMapper.updates(updatePurchaseDetailModelList);
        }

        if (deleteIdsList != null && !deleteIdsList.isEmpty()) {
            setDeletes(purchaseDetailMapper, deleteIdsList, "ROW_ID");
        }

        if (updateOtherInfoList != null && !updateOtherInfoList.isEmpty()) {
            purchaseDetailMapper.updatesOtherInfo(updateOtherInfoList);
        }

        if (!SupplyChianConstants.PURCHASE_STORE_STATUS_OVER.equals(Maps.getString(inputMap, "status"))
                && !SupplyChianConstants.PURCHASE_STORE_STATUS_AUTO_OVER.equals(Maps.getString(inputMap, "status"))) {
            updatePurchaseStoreStatus(Maps.getLong(inputMap, "rowId"), Maps.getString(inputMap, "eventCode"));
        }

        // START HANA
        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

        for (Entry<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMapEntry : dbBeanNameHanaPurchaseBillMap.entrySet()) {
            String dbBeanName = dbBeanNameHanaPurchaseBillMapEntry.getKey();
            Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMapEntry.getValue();
            for (Entry<String, HanaPurchaseBill> hanaPurchaseBillMapEntry : hanaPurchaseBillMap.entrySet()) {
                HanaPurchaseBill hanaPurchaseBill = hanaPurchaseBillMapEntry.getValue();
                if (!hanaPurchaseBill.getDetailList().isEmpty()) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(hanaPurchaseBill.getMTC_BranchID());
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:采购订单
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(hanaPurchaseBill.getMTC_BaseEntry());
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_U);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(dbBeanName);
                    // JSON文件
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                    mtcITFCList.add(mtcITFC);
                }
            }
        }

        if (!mtcITFCList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
        // END HANA

    }

    @Override
    public void editFinishPurchaseStore(Map<String, Object> inputMap) throws Exception {
        // Long purchaseId = Maps.getLong(inputMap, "rowId");
        // // 验证一下是否都填写了发票号
        // SqlCon checkReceiptNumberSqlCon = new SqlCon();
        // checkReceiptNumberSqlCon.addMainSql("SELECT ROW_ID AS rowId FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        // checkReceiptNumberSqlCon.addMainSql(" AND (RECEIPT_NUMBER IS NULL OR RECEIPTER_ID IS NULL)");
        // checkReceiptNumberSqlCon.addCondition(purchaseId, " AND PURCHASE_ID = ?");
        // checkReceiptNumberSqlCon.addMainSql(" LIMIT 1");
        //
        // Map<String, String> checkReceiptNumberSqlMap = new HashMap<String, String>();
        // checkReceiptNumberSqlMap.put("sql", checkReceiptNumberSqlCon.getCondition());
        //
        // List<Map<String, Object>> checkReceiptNumberList = paramMapper.getObjectInfos(checkReceiptNumberSqlMap);
        // if (checkReceiptNumberList != null && !checkReceiptNumberList.isEmpty()) {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "此功能暂时停用");
        // }
        //
        // PurchaseModel purchaseModel = new PurchaseModel();
        // purchaseModel.setRowId(purchaseId);
        // purchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_FINISHED);
        // purchaseMapper.update(purchaseModel);
    }

    private BasePageInfo searchPurchaseStoreToPage(Map<String, Object> inputMap) throws Exception {
        String searchType = Maps.getString(inputMap, "searchType");
        setToPage();
        List<Map<String, Object>> purchaseStoreModelList = null;
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT * FROM (");
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.STATUS AS status, T1.NOTES AS notes, T1.BILL_CODE AS billCode");
        sqlCon.addMainSql(", T1.BILL_DATE AS billDate, T1.EVENT_CODE AS eventCode, T1.PURCHASER_ID AS purchaserId");
        sqlCon.addMainSql(", _clearEndZero(ROUND(SUM(T2.PURCHASE_QTY*T2.ACTUAL_PRICE),2)) AS totalPrice, T1.SUPPLIER_ID AS supplierId");
        sqlCon.addMainSql(", T1.ARRIVE_DATE AS arriveDate");
        sqlCon.addMainSql(", GROUP_CONCAT(DISTINCT T3.SORT_NAME ORDER BY T3.SORT_NAME) AS farmNames");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PURCHASE_ID");
        sqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
        if ("group".equals(searchType)) {
            sqlCon.addCondition(SupplyChianConstants.GROUP_OR_SELF_GROUP, " AND T2.GROUP_OR_SELF = ?)");
        } else {
            sqlCon.addCondition(SupplyChianConstants.GROUP_OR_SELF_SELF, " AND T2.GROUP_OR_SELF = ?)");
        }
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY T3");
        sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T2.REQUIRE_FARM = T3.ROW_ID)");
        sqlCon.addConditionWithNull(getFarmId(), " WHERE T1.DELETED_FLAG = '0' AND T1.FARM_ID = ?");
        if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_PURCHASE, " AND T1.EVENT_CODE = ?");
            if (!Maps.isEmpty(inputMap, "canPurchaseSupplierId")) {
                sqlCon.addCondition(Maps.getLong(inputMap, "canPurchaseSupplierId"), " AND T1.SUPPLIER_ID = ?");
            }
        } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE, " AND T1.EVENT_CODE = ?");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "billCode"), " AND T1.BILL_CODE = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "searchStatus", "3,4,5"), " AND T1.STATUS IN ?", false, true);
        sqlCon.addCondition(Maps.getLongClass(inputMap, "searchFarmId"), " AND T3.ROW_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "searchSupplierId"), " AND T1.SUPPLIER_ID = ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID) A");
        sqlCon.addMainSql(" ORDER BY A.billDate DESC,A.rowId DESC");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        purchaseStoreModelList = paramMapper.getObjectInfos(sqlMap);

        if (purchaseStoreModelList != null) {
            for (Map<String, Object> purchaseStoreModel : purchaseStoreModelList) {
                purchaseStoreModel.put("billDate", TimeUtil.format(Maps.getString(purchaseStoreModel, "billDate"), TimeUtil.DATE_FORMAT));
                purchaseStoreModel.put("arriveDate", TimeUtil.format(Maps.getString(purchaseStoreModel, "arriveDate"), TimeUtil.DATE_FORMAT));
                purchaseStoreModel.put("purchaserName", CacheUtil.getName(Maps.getString(purchaseStoreModel, "purchaserId"), NameEnum.EMPLOYEE_NAME));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(purchaseStoreModel, "supplierId"));
                purchaseStoreModel.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                purchaseStoreModel.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                purchaseStoreModel.put("statusName", SupplyChianConstants.PURCHASE_STORE_STATUS_MAP.get(Maps.getString(purchaseStoreModel,
                        "status")));
                purchaseStoreModel.put("eventCodeName", CacheUtil.getName(Maps.getString(purchaseStoreModel, "eventCode"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.SUPPLYCHIAN_EVENT_CODE));
                purchaseStoreModel.put("totalPriceName", Maps.getDouble(purchaseStoreModel, "totalPrice") + "元");
            }
        }
        return getPageInfo(purchaseStoreModelList);
    }

    private void insertPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        String purchaseType = Maps.getString(inputMap, "purchaseType");

        if (Maps.isEmpty(inputMap, "eventCode")) {
            Thrower.throwException(SupplyChianException.PURCHASE_TYPE_NOT_NULL);
        }

        Date currentDate = new Date();

        String eventCode = Maps.getString(inputMap, "eventCode");
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_PURCHASE_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        PurchaseModel purchaseModel = getBean(PurchaseModel.class, inputMap);
        purchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS);
        purchaseModel.setFarmId(getFarmId());
        purchaseModel.setCompanyId(getCompanyId());
        purchaseModel.setBillCode(billCode);
        purchaseModel.setEventCode(eventCode);
        purchaseModel.setPurchaserId(getEmployeeId());
        purchaseModel.setOaUsername(getOaUsername());
        purchaseModel.setCreateId(getEmployeeId());
        purchaseModel.setCreateDate(currentDate);
        purchaseMapper.insert(purchaseModel);

        List<RequireDetailModel> updateRequireDetailModelList = null;
        Set<Long> requireIdsSet = null;
        List<Long> requireIdsList = null;
        if ("group".equals(purchaseType)) {
            // 需求单明细
            updateRequireDetailModelList = new ArrayList<RequireDetailModel>();
            // 记录主表单据Ids，并且去重
            requireIdsSet = new HashSet<Long>();
            requireIdsList = new ArrayList<Long>();
        }

        List<PurchaseDetailModel> freeMaterialList = new ArrayList<PurchaseDetailModel>();

        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("freeMaterialList", freeMaterialList);
        infoMap.put("billId", purchaseModel.getRowId());
        infoMap.put("currentDate", currentDate);
        infoMap.put("billCode", billCode);
        infoMap.put("eventCode", eventCode);

        // START HANA
        // 第一个String是dbBeanName，第二个String是baseEntry
        Map<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMap = new HashMap<String, Map<String, HanaPurchaseBill>>();
        // END HANA

        for (Map<String, Object> detailMap : detailList) {
            // 做到全平台共用，所以选用10000账套
            String materialOnly = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_ONLY_NUMBER, getEmployeeId(), get10000CompanyId(),
                    get10000FarmId());

            // START HANA
            Long requireFarmId = null;
            // 集团采购
            if ("group".equals(purchaseType)) {
                if (Maps.isEmpty(detailMap, "requireFarm")) {
                    Thrower.throwException(SupplyChianException.REQUIRE_FARM_CAN_NOT_NULL, Maps.getLong(detailMap, "lineNumber"));
                }
                requireFarmId = Maps.getLong(detailMap, "requireFarm");
            } else {
                requireFarmId = getFarmId();
            }

            boolean isToHana = HanaUtil.isToHanaCheck(requireFarmId);

            Map<String, Object> hanaInfo = new HashMap<String, Object>();

            if (isToHana) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLong(detailMap, "requireFarm",
                        getFarmId()));
                String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

                String mtcCardCode = HanaUtil.getMTC_CardCode(purchaseModel.getSupplierId());

                hanaInfo.put(HanaUtil.IS_TO_HANA, isToHana);
                hanaInfo.put(HanaUtil.MTC_BranchID, mtcBranchID);
                hanaInfo.put(HanaUtil.MTC_DeptID, Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID));

                String dbBeanName = HanaUtil.getDbBeanName(requireFarmId);

                String baseEntry = mtcBranchID + "-" + String.valueOf(purchaseModel.getRowId()) + "-" + purchaseModel.getBillCode();

                // 如果dbBeanName不存在
                if (!dbBeanNameHanaPurchaseBillMap.containsKey(dbBeanName)) {
                    Map<String, HanaPurchaseBill> hanaPurchaseBillMap = new HashMap<String, HanaPurchaseBill>();
                    HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                    // 分公司编码
                    hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                    // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                    hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                    // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                    hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                    // 业务伙伴编号
                    hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                    // 采购日期
                    hanaPurchaseBill.setMTC_DocDate(Maps.getDate(inputMap, "billDate"));
                    // 到货日期
                    hanaPurchaseBill.setMTC_DocDueDate(Maps.getDate(inputMap, "arriveDate"));
                    // 表行
                    List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                    hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                    hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                    dbBeanNameHanaPurchaseBillMap.put(dbBeanName, hanaPurchaseBillMap);

                    hanaInfo.put("hanaPurchaseBillDetailList", hanaPurchaseBillDetailList);
                } else {
                    // 如果dbBeanName存在,获取hanaPurchaseBillMap
                    Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMap.get(dbBeanName);

                    // 如果baseEntry不存在
                    if (!hanaPurchaseBillMap.containsKey(baseEntry)) {
                        HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                        // 分公司编码
                        hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                        // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                        // 业务伙伴编号
                        hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                        // 采购日期
                        hanaPurchaseBill.setMTC_DocDate(Maps.getDate(inputMap, "billDate"));
                        // 到货日期
                        hanaPurchaseBill.setMTC_DocDueDate(Maps.getDate(inputMap, "arriveDate"));
                        // 表行
                        List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                        hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);
                        hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);

                        hanaInfo.put("hanaPurchaseBillDetailList", hanaPurchaseBillDetailList);
                    } else {
                        List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = hanaPurchaseBillMap.get(baseEntry).getDetailList();
                        hanaInfo.put("hanaPurchaseBillDetailList", hanaPurchaseBillDetailList);
                    }
                }
            }
            // END HANA

            createInsertPurchaseStoreModel(materialOnly, infoMap, inputMap, detailMap, hanaInfo);

            if ("group".equals(purchaseType)) {
                if (!Maps.isEmpty(detailMap, "rowIds")) {
                    // 需求单明细Ids
                    List<HashMap> requireDetailIds = getJsonList(Maps.getString(detailMap, "rowIds"), HashMap.class);
                    for (Map<String, Object> requireDetailId : requireDetailIds) {
                        RequireDetailModel requireStoreModel = new RequireDetailModel();
                        requireStoreModel.setRowId(Maps.getLong(requireDetailId, "rowId"));
                        requireStoreModel.setPurchaseId(purchaseModel.getRowId());
                        requireStoreModel.setPurchaseLn(Maps.getLong(detailMap, "lineNumber"));
                        requireStoreModel.setMaterialOnly(materialOnly);
                        updateRequireDetailModelList.add(requireStoreModel);
                    }

                }

                if (!Maps.isEmpty(detailMap, "requireIds")) {
                    // 需求单主表Ids
                    List<HashMap> requireIds = getJsonList(Maps.getString(detailMap, "requireIds"), HashMap.class);
                    for (Map<String, Object> requireId : requireIds) {
                        Long rowId = Maps.getLong(requireId, "rowId");
                        if (!requireIdsSet.contains(rowId)) {
                            requireIdsSet.add(rowId);
                            requireIdsList.add(rowId);
                        }
                    }
                }
            }
        }

        if (freeMaterialList != null && !freeMaterialList.isEmpty()) {
            purchaseDetailMapper.inserts(freeMaterialList);
        }

        if ("group".equals(purchaseType)) {
            if (updateRequireDetailModelList != null && !updateRequireDetailModelList.isEmpty()) {
                requireDetailMapper.updates(updateRequireDetailModelList);
            }

            updateRequireStoreStatus(requireIdsList);
        }

        // START HANA
        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
        for (Entry<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMapEntry : dbBeanNameHanaPurchaseBillMap.entrySet()) {
            String dbBeanName = dbBeanNameHanaPurchaseBillMapEntry.getKey();
            Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMapEntry.getValue();
            for (Entry<String, HanaPurchaseBill> hanaPurchaseBillMapEntry : hanaPurchaseBillMap.entrySet()) {
                HanaPurchaseBill hanaPurchaseBill = hanaPurchaseBillMapEntry.getValue();
                if (!hanaPurchaseBill.getDetailList().isEmpty()) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(hanaPurchaseBill.getMTC_BranchID());
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:采购订单
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(hanaPurchaseBill.getMTC_BaseEntry());
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(dbBeanName);
                    // JSON文件
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                    mtcITFCList.add(mtcITFC);
                }
            }
        }

        if (!mtcITFCList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
        // END HANA

    }

    private void editPurchaseStore(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        String purchaseType = Maps.getString(inputMap, "purchaseType");
        Date currentDate = new Date();

        Long billId = Maps.getLong(inputMap, "rowId");

        PurchaseModel purchaseModel = getBean(PurchaseModel.class, inputMap);

        // 添加的购料及其赠料
        List<PurchaseDetailModel> insertPurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
        // 修改的购料
        List<PurchaseDetailModel> updatePurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
        // 删除的购料
        List<Long> deletePurchaseDetailModelList = new ArrayList<Long>();

        List<RequireDetailModel> updateRequireDetailModelList = null;
        List<Long> rebackRequireDetailModelList = null;
        Set<Long> requireIdsSet = null;
        List<Long> requireIdsList = null;
        if ("group".equals(purchaseType)) {
            // 需求单明细
            updateRequireDetailModelList = new ArrayList<RequireDetailModel>();
            // 还原需求单明细
            rebackRequireDetailModelList = new ArrayList<Long>();
            // 记录主表单据Ids，并且去重
            requireIdsSet = new HashSet<Long>();
            requireIdsList = new ArrayList<Long>();
        }

        Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("freeMaterialList", insertPurchaseDetailModelList);
        infoMap.put("billId", billId);
        infoMap.put("currentDate", currentDate);
        infoMap.put("billCode", purchaseModel.getBillCode());
        infoMap.put("eventCode", purchaseModel.getEventCode());

        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, "rowId")) {
                // 做到全平台共用，所以选用10000账套
                String materialOnly = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_ONLY_NUMBER, getEmployeeId(), get10000CompanyId(),
                        get10000FarmId());
                // 添加的购料
                createInsertPurchaseStoreModel(materialOnly, infoMap, inputMap, detailMap, null);

                if ("group".equals(purchaseType)) {
                    if (!Maps.isEmpty(detailMap, "rowIds")) {
                        // 需求单明细Ids
                        List<HashMap> requireDetailIds = getJsonList(Maps.getString(detailMap, "rowIds"), HashMap.class);
                        for (Map<String, Object> requireDetailId : requireDetailIds) {
                            RequireDetailModel requireDetailModel = new RequireDetailModel();
                            requireDetailModel.setRowId(Maps.getLong(requireDetailId, "rowId"));
                            requireDetailModel.setPurchaseId(purchaseModel.getRowId());
                            requireDetailModel.setPurchaseLn(Maps.getLong(detailMap, "lineNumber"));
                            requireDetailModel.setMaterialOnly(materialOnly);
                            updateRequireDetailModelList.add(requireDetailModel);
                        }

                    }

                    if (!Maps.isEmpty(detailMap, "requireIds")) {
                        // 需求单主表Ids
                        List<HashMap> requireIds = getJsonList(Maps.getString(detailMap, "requireIds"), HashMap.class);
                        for (Map<String, Object> requireId : requireIds) {
                            Long rowId = Maps.getLong(requireId, "rowId");
                            if (!requireIdsSet.contains(rowId)) {
                                requireIdsSet.add(rowId);
                                requireIdsList.add(rowId);
                            }
                        }
                    }
                }
            } else if (!Maps.isEmpty(detailMap, "rowId") && "1".equals(Maps.getString(detailMap, "deletedFlag"))) {
                // 删除的物料
                deletePurchaseDetailModelList.add(Maps.getLong(detailMap, "rowId"));

                if ("group".equals(purchaseType)) {
                    if (!Maps.isEmpty(detailMap, "rowIds")) {
                        // 需求单明细Ids
                        List<HashMap> requireDetailIds = getJsonList(Maps.getString(detailMap, "rowIds"), HashMap.class);
                        for (Map<String, Object> requireDetailId : requireDetailIds) {
                            rebackRequireDetailModelList.add(Maps.getLong(requireDetailId, "rowId"));
                        }
                    }

                    if (!Maps.isEmpty(detailMap, "requireIds")) {
                        // 需求单主表Ids
                        List<HashMap> requireIds = getJsonList(Maps.getString(detailMap, "requireIds"), HashMap.class);
                        for (Map<String, Object> requireId : requireIds) {
                            Long rowId = Maps.getLong(requireId, "rowId");
                            if (!requireIdsSet.contains(rowId)) {
                                requireIdsSet.add(rowId);
                                requireIdsList.add(rowId);
                            }
                        }
                    }
                }
            } else {

                // BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "purchaseQty", 0D), 1D);
                // 数量必须为整数
                // if (remainder[1].doubleValue() != 0D) {
                // Thrower.throwException(SupplyChianException.PURCHASE_QTY_MUST_BE_INTEGER, Maps.getLong(detailMap, "lineNumber"));
                // }

                // 如果存在赠料比例
                if (!Maps.isEmpty(detailMap, "actualFreePercent")) {
                    // 赠料比例中包含"/"，必须满足整除"/"之前的数字
                    if (Maps.getString(detailMap, "actualFreePercent").contains("/")) {
                        String actualFreePercent = Maps.getString(detailMap, "actualFreePercent");
                        String actualFreePercentHead = actualFreePercent.substring(0, actualFreePercent.indexOf("/"));
                        BigDecimal[] actualFreePercentRemainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "purchaseQty", 0D), Double
                                .valueOf(actualFreePercentHead));
                        if (actualFreePercentRemainder[1].doubleValue() != 0D) {
                            Thrower.throwException(SupplyChianException.PURCHASE_QTY_MUST_DIVIDED_WITH_NO_REMAINDER, Maps.getLong(detailMap,
                                    "lineNumber"), Maps.getString(detailMap, "actualFreePercent"), actualFreePercentHead);
                        }
                    }
                }

                // 更新的物料
                PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
                purchaseDetailModel.setRowId(Maps.getLong(detailMap, "rowId"));
                purchaseDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                purchaseDetailModel.setPurchaseLn(Maps.getLong(detailMap, "lineNumber"));
                purchaseDetailModel.setRequireFarm(Maps.getLong(detailMap, "requireFarm"));
                purchaseDetailModel.setActualPrice(Maps.getDouble(detailMap, "actualPrice", 0D));
                purchaseDetailModel.setPassQty(Maps.getDouble(detailMap, "purchaseQty", 0D));
                purchaseDetailModel.setPurchaseQty(Maps.getDouble(detailMap, "purchaseQty", 0D));
                purchaseDetailModel.setActualFreePercent(Maps.getString(detailMap, "actualFreePercent"));
                purchaseDetailModel.setIsPackage(Maps.getString(inputMap, "isPackage", "N"));
                purchaseDetailModel.setPurchaserId(getEmployeeId());
                purchaseDetailModel.setArriveDate(Maps.getDate(inputMap, "arriveDate"));
                updatePurchaseDetailModelList.add(purchaseDetailModel);

                createInsertFreeMaterialModel(infoMap, inputMap, detailMap, purchaseDetailModel.getRowId(), null);

            }
        }

        // 删除所有的赠料
        deleteAllFreeMaterial(billId);

        if ("group".equals(purchaseType)) {
            rebackRequireDetail(rebackRequireDetailModelList);
        }

        purchaseMapper.update(purchaseModel);

        if (deletePurchaseDetailModelList != null && !deletePurchaseDetailModelList.isEmpty()) {
            setDeletes(purchaseDetailMapper, deletePurchaseDetailModelList, "ROW_ID");
        }

        if (updatePurchaseDetailModelList != null && !updatePurchaseDetailModelList.isEmpty()) {
            purchaseDetailMapper.updates(updatePurchaseDetailModelList);
        }

        if (insertPurchaseDetailModelList != null && !insertPurchaseDetailModelList.isEmpty()) {
            purchaseDetailMapper.inserts(insertPurchaseDetailModelList);
        }

        if ("group".equals(purchaseType)) {
            if (updateRequireDetailModelList != null && !updateRequireDetailModelList.isEmpty()) {
                requireDetailMapper.updates(updateRequireDetailModelList);
            }

            updateRequireStoreStatus(requireIdsList);
        }
    }

    // 添加数据
    private void createInsertPurchaseStoreModel(String materialOnly, Map<String, Object> infoMap, Map<String, Object> inputMap,
            Map<String, Object> detailMap, Map<String, Object> hanaInfo) throws Exception {
        // BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "purchaseQty"), 1D);
        // 数量必须为整数
        // if (remainder[1].doubleValue() != 0D) {
        // Thrower.throwException(SupplyChianException.PURCHASE_QTY_MUST_BE_INTEGER, Maps.getLong(detailMap, "lineNumber"));
        // }
        // 如果存在赠料比例
        if (!Maps.isEmpty(detailMap, "actualFreePercent")) {
            // 赠料比例中包含"/"，必须满足整除"/"之前的数字
            if (Maps.getString(detailMap, "actualFreePercent").contains("/")) {
                String actualFreePercent = Maps.getString(detailMap, "actualFreePercent");
                String actualFreePercentHead = actualFreePercent.substring(0, actualFreePercent.indexOf("/"));
                BigDecimal[] actualFreePercentRemainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "purchaseQty", 0D), Double.valueOf(
                        actualFreePercentHead));
                if (actualFreePercentRemainder[1].doubleValue() != 0D) {
                    Thrower.throwException(SupplyChianException.PURCHASE_QTY_MUST_DIVIDED_WITH_NO_REMAINDER, Maps.getLong(detailMap, "lineNumber"),
                            Maps.getString(detailMap, "actualFreePercent"), actualFreePercentHead);
                }
            }
        }

        if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(Maps.getString(infoMap, "eventCode"))) {
            // 新农旗下
            if (isXNCompany(getCompanyMark())) {
                if (Maps.getDouble(detailMap, "actualPrice", 0D) == 0D) {
                    if (Maps.isEmpty(detailMap, "rebateReason")) {
                        Thrower.throwException(SupplyChianException.REBATE_REASON_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
                    }
                }
            }
        }

        Long billId = Maps.getLong(infoMap, "billId");
        Date currentDate = Maps.getDate(infoMap, "currentDate");

        // 获取更新单价单中的单价
        Double actualPrice = getUpdatePriceForMaterial(inputMap, detailMap);

        PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
        purchaseDetailModel.setStatus(CommonConstants.STATUS);
        purchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        purchaseDetailModel.setNotes(Maps.getString(detailMap, "notes"));
        purchaseDetailModel.setFarmId(getFarmId());
        purchaseDetailModel.setCompanyId(getCompanyId());
        purchaseDetailModel.setPurchaseId(billId);
        purchaseDetailModel.setPurchaseLn(Maps.getLong(detailMap, "lineNumber"));
        purchaseDetailModel.setFreeLn(0L);
        purchaseDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
        purchaseDetailModel.setMaterialOnly(materialOnly);
        purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
        if (actualPrice != null) {
            purchaseDetailModel.setActualPrice(actualPrice);
        } else {
            purchaseDetailModel.setActualPrice(Maps.getDouble(detailMap, "actualPrice", 0D));
        }
        purchaseDetailModel.setActualFreePercent(Maps.getString(detailMap, "actualFreePercent"));
        purchaseDetailModel.setPassQty(Maps.getDouble(detailMap, "purchaseQty", 0D));
        purchaseDetailModel.setPurchaseQty(Maps.getDouble(detailMap, "purchaseQty", 0D));
        purchaseDetailModel.setIsPackage(Maps.getString(inputMap, "isPackage", "N"));
        purchaseDetailModel.setArriveQty(0D);
        purchaseDetailModel.setGroupOrSelf(Maps.getString(inputMap, "groupOrSelf"));
        purchaseDetailModel.setSupplierId(Maps.getLong(inputMap, "supplierId"));
        if ("self".equals(Maps.getString(inputMap, "purchaseType"))) {
            purchaseDetailModel.setRequireFarm(getFarmId());
        } else {
            purchaseDetailModel.setRequireFarm(Maps.getLong(detailMap, "requireFarm"));
        }
        purchaseDetailModel.setRebateReason(Maps.getString(detailMap, "rebateReason", SupplyChianConstants.REBATE_REASON_A));
        purchaseDetailModel.setRebatePrice(Maps.getDouble(detailMap, "rebatePrice", 0D));
        purchaseDetailModel.setPurchaserId(getEmployeeId());
        purchaseDetailModel.setArriveDate(Maps.getDate(inputMap, "arriveDate"));
        purchaseDetailModel.setBillCode(Maps.getString(infoMap, "billCode"));
        purchaseDetailModel.setBillDate(Maps.getDate(inputMap, "billDate"));
        purchaseDetailModel.setEventCode(Maps.getString(infoMap, "eventCode"));
        purchaseDetailModel.setCreateId(getEmployeeId());
        purchaseDetailModel.setCreateDate(currentDate);
        purchaseDetailMapper.insert(purchaseDetailModel);

        // START HANA
        if (Maps.getBoolean(hanaInfo, "isToHana")) {
            String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(purchaseDetailModel.getMaterialId());
            List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = (List<HanaPurchaseBillDetail>) hanaInfo.get("hanaPurchaseBillDetailList");
            HanaPurchaseBillDetail hanaPurchaseBillDetail = new HanaPurchaseBillDetail();
            // 分公司编码
            hanaPurchaseBillDetail.setMTC_BranchID(Maps.getString(hanaInfo, HanaUtil.MTC_BranchID));
            // 猪场编码
            hanaPurchaseBillDetail.setMTC_DeptID(Maps.getString(hanaInfo, HanaUtil.MTC_DeptID));
            // 新农+单据编号
            hanaPurchaseBillDetail.setMTC_BaseEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
            // 新农+单据行号
            hanaPurchaseBillDetail.setMTC_BaseLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*0");
            // 采购类型
            hanaPurchaseBillDetail.setMTC_PurType(purchaseDetailModel.getRebateReason());
            // 返利金额
            hanaPurchaseBillDetail.setMTC_Rebate(String.valueOf(purchaseDetailModel.getRebatePrice()));
            // 业务类型
            hanaPurchaseBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
            // 物料编号
            hanaPurchaseBillDetail.setMTC_ItemCode(mtcItemCode);
            // 采购数量
            hanaPurchaseBillDetail.setMTC_Qty(String.valueOf(purchaseDetailModel.getPurchaseQty()));
            // 采购价格
            hanaPurchaseBillDetail.setMTC_QtyPrice(String.valueOf(purchaseDetailModel.getActualPrice()));
            // 采购金额
            Double totalPrice = bigDecimalMultiply(purchaseDetailModel.getPurchaseQty(), purchaseDetailModel.getActualPrice());
            hanaPurchaseBillDetail.setMTC_Amount(String.valueOf(totalPrice));
            hanaPurchaseBillDetailList.add(hanaPurchaseBillDetail);
        }
        // END HANA

        // 添加赠料
        createInsertFreeMaterialModel(infoMap, inputMap, detailMap, purchaseDetailModel.getRowId(), hanaInfo);

    }

    // 获取更新单价单中的单价
    private Double getUpdatePriceForMaterial(Map<String, Object> inputMap, Map<String, Object> detailMap) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.PRICE AS actualPrice FROM SC_M_BILL_PRICE_LIST_DETAIL T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(Maps.getString(inputMap, "billDate"), " AND T1.START_DATE <= ?");
        sqlCon.addCondition(Maps.getString(inputMap, "billDate"), " AND T1.END_DATE >= ?");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_M_MATERIAL A WHERE A.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T1.MATERIAL_ID = A.RELATED_ID");
        sqlCon.addCondition(Maps.getLong(detailMap, "materialId"), " AND A.ROW_ID = ? LIMIT 1)");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            return Maps.getDoubleClass(list.get(0), "actualPrice");
        }

        return null;
    }

    // 赠料
    private void createInsertFreeMaterialModel(Map<String, Object> infoMap, Map<String, Object> inputMap, Map<String, Object> detailMap,
            Long purchaseStoreId, Map<String, Object> hanaInfo) throws Exception {
        List<PurchaseDetailModel> freeMaterialList = Maps.getList(infoMap, "freeMaterialList");
        Long billId = Maps.getLong(infoMap, "billId");
        Date currentDate = Maps.getDate(infoMap, "currentDate");

        if (!Maps.isEmpty(detailMap, "freeInfo")) {
            // 赠料
            List<Map<String, Object>> freeMaterialMapList = Maps.getList(detailMap, "freeInfo");
            if (freeMaterialMapList != null && !freeMaterialMapList.isEmpty()) {
                for (int i = 0; i < freeMaterialMapList.size(); i++) {
                    Map<String, Object> freeMaterialMap = freeMaterialMapList.get(i);

                    BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(freeMaterialMap, "purchaseQty", 0D), 1D);
                    // 数量必须为整数
                    if (remainder[1].doubleValue() != 0D) {
                        Thrower.throwException(SupplyChianException.FREE_QTY_MUST_BE_INTEGER, Maps.getLong(detailMap, "lineNumber"), (i + 1));
                    }

                    PurchaseDetailModel freeMaterialModel = new PurchaseDetailModel();
                    freeMaterialModel.setStatus(CommonConstants.STATUS);
                    freeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    freeMaterialModel.setNotes(Maps.getString(freeMaterialMap, "notes"));
                    freeMaterialModel.setFarmId(getFarmId());
                    freeMaterialModel.setCompanyId(getCompanyId());
                    freeMaterialModel.setPurchaseId(billId);
                    freeMaterialModel.setPurchaseLn(Maps.getLong(detailMap, "lineNumber"));
                    freeMaterialModel.setFreeLn(Long.valueOf(i + 1));
                    freeMaterialModel.setMaterialId(Maps.getLong(freeMaterialMap, "materialId"));
                    // 做到全平台共用，所以选用10000账套
                    String freeMaterialOnly = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_ONLY_NUMBER, getEmployeeId(),
                            get10000CompanyId(), get10000FarmId());
                    freeMaterialModel.setMaterialOnly(freeMaterialOnly);
                    freeMaterialModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                    freeMaterialModel.setActualPrice(0D);
                    freeMaterialModel.setPassQty(Maps.getDouble(freeMaterialMap, "purchaseQty", 0D));
                    freeMaterialModel.setPurchaseQty(Maps.getDouble(freeMaterialMap, "purchaseQty", 0D));
                    freeMaterialModel.setFreeRelatedId(purchaseStoreId);
                    freeMaterialModel.setIsPackage(Maps.getString(inputMap, "isPackage", "N"));
                    freeMaterialModel.setArriveQty(0D);
                    freeMaterialModel.setGroupOrSelf(Maps.getString(inputMap, "groupOrSelf"));
                    freeMaterialModel.setSupplierId(Maps.getLong(inputMap, "supplierId"));
                    if ("self".equals(Maps.getString(inputMap, "purchaseType"))) {
                        freeMaterialModel.setRequireFarm(getFarmId());
                    } else {
                        freeMaterialModel.setRequireFarm(Maps.getLong(detailMap, "requireFarm"));
                    }
                    freeMaterialModel.setRebateReason(SupplyChianConstants.REBATE_REASON_B);
                    freeMaterialModel.setRebatePrice(0D);
                    freeMaterialModel.setPurchaserId(getEmployeeId());
                    freeMaterialModel.setArriveDate(Maps.getDate(inputMap, "arriveDate"));
                    freeMaterialModel.setBillCode(Maps.getString(infoMap, "billCode"));
                    freeMaterialModel.setBillDate(Maps.getDate(inputMap, "billDate"));
                    freeMaterialModel.setEventCode(Maps.getString(infoMap, "eventCode"));
                    freeMaterialModel.setCreateId(getEmployeeId());
                    freeMaterialModel.setCreateDate(currentDate);
                    freeMaterialList.add(freeMaterialModel);

                    // START HANA
                    if (Maps.getBoolean(hanaInfo, "isToHana")) {
                        String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(Maps.getLong(freeMaterialMap, "materialId"));
                        List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = (List<HanaPurchaseBillDetail>) hanaInfo.get(
                                "hanaPurchaseBillDetailList");
                        HanaPurchaseBillDetail hanaPurchaseBillDetail = new HanaPurchaseBillDetail();
                        // 分公司编码
                        hanaPurchaseBillDetail.setMTC_BranchID(Maps.getString(hanaInfo, HanaUtil.MTC_BranchID));
                        // 猪场编码
                        hanaPurchaseBillDetail.setMTC_DeptID(Maps.getString(hanaInfo, HanaUtil.MTC_DeptID));
                        // 新农+单据编号
                        hanaPurchaseBillDetail.setMTC_BaseEntry(String.valueOf(freeMaterialModel.getPurchaseId()));
                        // 新农+单据行号
                        hanaPurchaseBillDetail.setMTC_BaseLine(String.valueOf(freeMaterialModel.getPurchaseLn()) + "*" + String.valueOf(
                                freeMaterialModel.getFreeLn()));
                        // 采购类型
                        hanaPurchaseBillDetail.setMTC_PurType(freeMaterialModel.getRebateReason());
                        // 返利金额
                        hanaPurchaseBillDetail.setMTC_Rebate(String.valueOf(freeMaterialModel.getRebatePrice()));
                        // 业务类型
                        hanaPurchaseBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                        // 物料编号
                        hanaPurchaseBillDetail.setMTC_ItemCode(mtcItemCode);
                        // 采购数量
                        hanaPurchaseBillDetail.setMTC_Qty(String.valueOf(freeMaterialModel.getPurchaseQty()));
                        // 采购价格
                        hanaPurchaseBillDetail.setMTC_QtyPrice("0.0");
                        // 采购金额
                        hanaPurchaseBillDetail.setMTC_Amount("0.0");
                        hanaPurchaseBillDetailList.add(hanaPurchaseBillDetail);
                    }
                    // END HANA
                }
            }
        }
    }

    // 删除所有赠料
    private void deleteAllFreeMaterial(Long billId) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("DELETE FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND FREE_RELATED_ID IS NOT NULL");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addCondition(billId, " AND PURCHASE_ID = ?");
        setSql(purchaseDetailMapper, sqlCon);
    }

    // 将需求单改回原状态
    private void rebackRequireDetail(List<Long> rebackRequireDetailModelList) {
        if (rebackRequireDetailModelList != null && !rebackRequireDetailModelList.isEmpty()) {
            String idsString = longArrayListToString(rebackRequireDetailModelList);
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("UPDATE SC_M_BILL_REQUIRE_DETAIL SET PURCHASE_ID = NULL, PURCHASE_LN = NULL");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_ONLY_NUMBER_START, ", MATERIAL_ONLY = ?");
            sqlCon.addCondition(idsString, " WHERE ROW_ID IN ?", false, true);
            setSql(requireDetailMapper, sqlCon);
        }
    }

    // 更新需求表状态
    private void updateRequireStoreStatus(List<Long> requireIdsList) {
        if (requireIdsList != null && !requireIdsList.isEmpty()) {
            String requireIdsString = longArrayListToString(requireIdsList);
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_PART_COMPLETED, "SELECT T1.ROW_ID, ? AS STATUS FROM SC_M_BILL_REQUIRE T1");
            sqlCon.addCondition(requireIdsString, " WHERE T1.DELETED_FLAG = '0' AND T1.STATUS <> '6' AND T1.ROW_ID IN ?", false, true);
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_REQUIRE_DETAIL T2 WHERE T2.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T1.ROW_ID = T2.REQUIRE_ID AND T2.PURCHASE_ID IS NULL LIMIT 1)");
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_REQUIRE_DETAIL A WHERE A.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T1.ROW_ID = A.REQUIRE_ID AND A.PURCHASE_ID IS NOT NULL LIMIT 1)");
            sqlCon.addMainSql(" UNION");
            sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_COMPLETED, " SELECT T3.ROW_ID, ? AS STATUS FROM SC_M_BILL_REQUIRE T3");
            sqlCon.addCondition(requireIdsString, " WHERE T3.DELETED_FLAG = '0' AND T3.STATUS <> '6' AND T3.ROW_ID IN ?", false, true);
            sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_REQUIRE_DETAIL T4 WHERE T4.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T3.ROW_ID = T4.REQUIRE_ID AND T4.PURCHASE_ID IS NULL LIMIT 1)");
            sqlCon.addMainSql(" UNION");
            sqlCon.addCondition(SupplyChianConstants.REQUIRE_STORE_STATUS_PASS, " SELECT T5.ROW_ID, ? AS STATUS FROM SC_M_BILL_REQUIRE T5");
            sqlCon.addCondition(requireIdsString, " WHERE T5.DELETED_FLAG = '0' AND T5.STATUS <> '6' AND T5.ROW_ID IN ?", false, true);
            sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_REQUIRE_DETAIL T6 WHERE T6.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T5.ROW_ID = T6.REQUIRE_ID AND T6.PURCHASE_ID IS NOT NULL LIMIT 1)");
            List<RequireModel> requireModelList = setSql(requireMapper, sqlCon);
            requireMapper.updates(requireModelList);
        }
    }

    private void editScrapPurchaseStore(long[] ids, Map<String, Object> inputMap) throws Exception {
        Date currentDate = new Date();

        List<PurchaseModel> purchaseModelList = new ArrayList<PurchaseModel>();
        List<PurchaseDetailModel> updatePurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();

        Map<String, MTC_ITFC> mtcITFCMap = new HashMap<String, MTC_ITFC>();

        for (long id : ids) {
            PurchaseModel purchaseModel = purchaseMapper.searchById(id);
            purchaseModel.setNotes(purchaseModel.getNotes() + "(作废原因：" + Maps.getString(inputMap, "scrapReason", "") + ")");
            purchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_SCRAP);
            purchaseModelList.add(purchaseModel);

            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(id, " AND PURCHASE_ID = ?");
            sqlCon.addMainSql(" AND STATUS = '1'");
            List<PurchaseDetailModel> purchaseDetailModelList = getList(purchaseDetailMapper, sqlCon);
            for (PurchaseDetailModel purchaseDetailModel : purchaseDetailModelList) {
                purchaseDetailModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_SCRAP);

                // START HANA
                boolean isToHana = HanaUtil.isToHanaCheck(purchaseDetailModel.getRequireFarm());
                if (isToHana) {
                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(purchaseDetailModel.getRequireFarm());

                    String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

                    String mtcDocNum = mtcBranchID + "-" + purchaseModel.getRowId() + "-" + purchaseModel.getBillCode();

                    if (!mtcITFCMap.containsKey(mtcDocNum)) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(mtcBranchID);
                        // 业务日期
                        mtcITFC.setMTC_DocDate(purchaseDetailModel.getBillDate());
                        // 业务代码:采购订单
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                        // 新农+单据编号：分公司编码-单据ROW_ID-单据单号
                        mtcITFC.setMTC_DocNum(mtcDocNum);
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_D);
                        // 原始单据编号
                        mtcITFC.setMTC_BaseEntry(mtcDocNum);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(purchaseDetailModel.getRequireFarm()));
                        // JSON文件
                        HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                        hanaPurchaseBill.setMTC_BaseEntry(mtcDocNum);
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                        mtcITFCMap.put(mtcDocNum, mtcITFC);
                    }

                }
                // END HANA

            }
            updatePurchaseDetailModelList.addAll(purchaseDetailModelList);
        }
        purchaseMapper.updates(purchaseModelList);

        // 删除采购明细
        purchaseDetailMapper.updates(updatePurchaseDetailModelList);

        // START HANA
        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
        for (MTC_ITFC mtcITFC : mtcITFCMap.values()) {
            mtcITFCList.add(mtcITFC);
        }

        if (!mtcITFCList.isEmpty()) {
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
        // END HANA

    }

    public List<Map<String, Object>> searchSupplierHaveMaterialTypeToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND SUPPLIER_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "materialFirstClass"), " AND MATERIAL_FIRST_CLASS = ?");
        sqlCon.addMainSql(" LIMIT 1");

        List<MaterialModel> materialModelList = getList(materialMapper, sqlCon);

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (materialModelList != null && !materialModelList.isEmpty()) {
            resultMap.put("exists", true);
        } else {
            resultMap.put("exists", false);
        }
        result.add(resultMap);
        return result;
    }

    @Override
    public List<Map<String, Object>> searchFeedStartDateToList(Map<String, Object> inputMap) throws Exception {
        String companyMark = getCompanyMark();
        String parentMark = companyMark.substring(0, companyMark.lastIndexOf(","));
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.BILL_DATE AS startDate FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.INPUT_ID = T2.INPUT_ID AND T1.INPUT_LN = T2.INPUT_LN)");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_NOT_BILL, " WHERE T1.DELETED_FLAG = '0' AND T1.EVENT_CODE = ?");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM HR_M_COMPANY WHERE DELETED_FLAG = '0' AND ROW_ID = T1.FARM_ID");
        sqlCon.addMainSql(" AND COMPANY_MARK LIKE '" + parentMark + ",%')");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0' AND ROW_ID = T1.MATERIAL_ID");
        sqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND SUPPLIER_ID = ?");
        sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        sqlCon.addMainSql(" LIMIT 1)");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE LIMIT 1");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> startDateList = paramMapper.getObjectInfos(sqlMap);

        return startDateList;
    }

    @Override
    public BasePageInfo searchMaterialFeedFromInputingToPage(Map<String, Object> inputMap) throws Exception {
        String companyMark = getCompanyMark();
        String parentMark = companyMark.substring(0, companyMark.lastIndexOf(","));
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',''+ T1.ROW_ID,'}')),']') AS notBillIds");
        sqlCon.addMainSql(" , CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',''+ T3.ROW_ID,'}')),']') AS rowIds");
        sqlCon.addMainSql(" , GROUP_CONCAT(T3.MATERIAL_ONLY) AS materialOnlys");
        sqlCon.addMainSql(" , T3.REQUIRE_FARM AS requireFarm, T3.MATERIAL_ID AS materialId, SUM(T3.PASS_QTY) AS inputQty");
        sqlCon.addMainSql(" , MIN(T3.ROW_ID) AS transportId");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_INPUT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.INPUT_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T2.INPUT_ID = T3.INPUT_ID AND T2.INPUT_LN = T3.INPUT_LN)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.EVENT_CODE = 'INPUT_NOT_BILL'");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM HR_M_COMPANY WHERE DELETED_FLAG = '0' AND ROW_ID = T1.FARM_ID");
        sqlCon.addMainSql(" AND COMPANY_MARK LIKE '" + parentMark + ",%')");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0' AND ROW_ID = T2.MATERIAL_ID");
        sqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND SUPPLIER_ID = ?");
        sqlCon.addMainSql(" AND MATERIAL_FIRST_CLASS");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
        sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        sqlCon.addMainSql(" LIMIT 1)");
        sqlCon.addCondition(Maps.getString(inputMap, "startDate"), " AND T1.BILL_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, "endDate"), " AND T1.BILL_DATE <= ?");
        sqlCon.addMainSql(" GROUP BY T3.REQUIRE_FARM, T3.MATERIAL_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        setToPage();
        List<Map<String, Object>> feedFromInputingList = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> feedFromInputingMap : feedFromInputingList) {
            feedFromInputingMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(feedFromInputingMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            feedFromInputingMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(feedFromInputingMap, "materialFirstClass"),
                    NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
            feedFromInputingMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(feedFromInputingMap,
                    "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(feedFromInputingMap, "materialFirstClass")));
            feedFromInputingMap.put("inputQtyName", Maps.getDouble(feedFromInputingMap, "inputQty") + Maps.getString(feedFromInputingMap, "unit"));
            Map<String, String> requireFarmInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(feedFromInputingMap, "requireFarm"));
            feedFromInputingMap.put("requireFarmSortName", Maps.getString(requireFarmInfoMap, "SORT_NAME"));
            feedFromInputingMap.put("requireFarmName", Maps.getString(requireFarmInfoMap, "COMPANY_NAME"));
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(feedFromInputingMap, "supplierId"));
            feedFromInputingMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            feedFromInputingMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
        }

        return getPageInfo(feedFromInputingList);
    }

    /* END 采购订单 */

    /* START 入库单 */
    @Override
    public BasePageInfo searchInputStoreToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId,T1.STATUS AS status, T1.BILL_CODE AS billCode, T1.BILL_DATE AS billDate");
        sqlCon.addMainSql(", T1.EVENT_CODE AS eventCode, T1.INPUTER_ID AS inputerId, T1.NOTES AS notes");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "advancedBillCode"), " AND T1.BILL_CODE LIKE ?", true);
        sqlCon.addCondition(Maps.getString(inputMap, "advancedStartDate"), " AND T1.BILL_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, "advancedEndDate"), " AND T1.BILL_DATE <= ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "advancedInputerId"), " AND T1.INPUTER_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "advancedEventCode"), " AND T1.EVENT_CODE = ?");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE DESC, T1.ROW_ID DESC");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> inputStoreMapList = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> inputStoreMap : inputStoreMapList) {
            inputStoreMap.put("eventCodeName", CacheUtil.getName(Maps.getString(inputStoreMap, "eventCode"), NameEnum.CODE_NAME,
                    xn.pigfarm.util.enums.CodeEnum.SUPPLYCHIAN_EVENT_CODE));
            inputStoreMap.put("inputerName", CacheUtil.getName(Maps.getString(inputStoreMap, "inputerId"), NameEnum.EMPLOYEE_NAME));
            // 状态
            if (SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE.equals(Maps.getString(inputStoreMap, "eventCode"))
                    || SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE.equals(Maps.getString(inputStoreMap, "eventCode"))) {
                inputStoreMap.put("statusName", SupplyChianConstants.ARRIVE_INPUT_STATUS_MAP.get(Maps.getString(inputStoreMap, "status")));
            } else {
                inputStoreMap.put("statusName", "正常");
            }
        }
        return getPageInfo(inputStoreMapList);
    }

    @Override
    public List<Map<String, Object>> searchInputBillDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.MATERIAL_ID AS materialId, T1.NOTES AS notes");
        sqlCon.addMainSql(", T1.INPUT_WAREHOUSE_ID AS inputWarehouseId, _clearEndZero(T1.INPUT_QTY) AS inputQty");
        if ("1".equals(Maps.getString(inputMap, "status"))) {
            sqlCon.addMainSql(", (SELECT PRODUCTION_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
            sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS productionDate");
            sqlCon.addMainSql(", (SELECT EFFECTIVE_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
            sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS effectiveDate");
        } else {
            sqlCon.addMainSql(", (SELECT PRODUCTION_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '2'");
            sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
            sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS productionDate");
            sqlCon.addMainSql(", (SELECT EFFECTIVE_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '2'");
            sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
            sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS effectiveDate");
        }
        sqlCon.addMainSql(", (SELECT FREE_LN FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY LIMIT 1) AS freeLn");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLong(inputMap, "inputId"), " AND T1.INPUT_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "status"), " AND T1.STATUS = ?");
        sqlCon.addMainSql(" ORDER BY INPUT_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            map.put("inputWarehouseName", CacheUtil.getName(Maps.getString(map, "inputWarehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            map.put("inputQtyName", map.get("inputQty") + Maps.getString(map, "unit"));
            if (!Maps.isEmpty(map, "freeLn")) {
                if (Maps.getLong(map, "freeLn") == 0D) {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
                } else {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> searchInputFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.rowId, T1.materialId, T1.purchaseQty, T1.arriveQty");
        // 拆分用ID
        if (SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(", T1.splitId");
        } else {
            sqlCon.addMainSql(", T1.rowId AS splitId");
        }
        sqlCon.addMainSql(", T1.actualPrice, T1.actualFreePercent, T1.materialOnly, T1.materialSplit FROM(");
        // sqlCon.addMainSql(" SELECT CONCAT('[',GROUP_CONCAT(CONCAT('{\"rowId\":',''+ROW_ID,'}')),']') AS rowId");
        sqlCon.addMainSql(" SELECT (SELECT ROW_ID FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND MATERIAL_ID = A.MATERIAL_ID");
        if (SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(" AND ARRIVE_QTY = 0");
        } else {
            sqlCon.addMainSql(" AND PURCHASE_QTY <> ARRIVE_QTY");
        }
        sqlCon.addMainSql(" AND PURCHASE_ID = A.PURCHASE_ID AND MATERIAL_ONLY = A.MATERIAL_ONLY AND MATERIAL_SPLIT = A.MATERIAL_SPLIT) AS rowId");
        // 用于饲料拆分
        if (SupplyChianConstants.EVENT_CODE_FEED_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(",(SELECT MIN(ROW_ID) FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND MATERIAL_ID = A.MATERIAL_ID");
            sqlCon.addMainSql(" AND PASS_QTY <> 0 AND PURCHASE_ID = A.PURCHASE_ID");
            sqlCon.addMainSql(" AND MATERIAL_ONLY = A.MATERIAL_ONLY) AS splitId");
        }
        sqlCon.addMainSql(", MAX(A.MATERIAL_ID) AS materialId, SUM(A.PURCHASE_QTY) AS purchaseQty, SUM(A.ARRIVE_QTY) AS arriveQty");
        sqlCon.addMainSql(", MAX(A.ACTUAL_PRICE) AS actualPrice, MAX(A.ACTUAL_FREE_PERCENT) AS actualFreePercent");
        sqlCon.addMainSql(", A.MATERIAL_ONLY AS materialOnly, A.MATERIAL_SPLIT AS materialSplit, A.PURCHASE_LN AS purchaseLn, A.FREE_LN AS freeLn");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL A WHERE DELETED_FLAG='0' AND STATUS = '1'");
        sqlCon.addCondition(Maps.getLong(inputMap, "purchaseId"), " AND A.PURCHASE_ID = ?");
        sqlCon.addConditionWithNull(getFarmId(), " AND A.REQUIRE_FARM = ?");
        sqlCon.addMainSql(" GROUP BY A.MATERIAL_ONLY, A.MATERIAL_SPLIT");
        sqlCon.addMainSql(" ) T1");
        if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(" WHERE T1.purchaseQty <> T1.arriveQty");
        }
        sqlCon.addMainSql(" ORDER BY T1.purchaseLn, T1.freeLn, T1.materialSplit");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> purchaseStoreMapList = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> purchaseStoreMap : purchaseStoreMapList) {
            purchaseStoreMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(purchaseStoreMap, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            purchaseStoreMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(purchaseStoreMap, "materialFirstClass"),
                    NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
            purchaseStoreMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(purchaseStoreMap,
                    "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(purchaseStoreMap, "materialFirstClass")));
            purchaseStoreMap.put("warehouseId", Maps.getLongClass(purchaseStoreMap, "defaultWarehouse"));
            purchaseStoreMap.put("inputWarehouseName", CacheUtil.getName(Maps.getString(purchaseStoreMap, "warehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            purchaseStoreMap.put("totalPrice", bigDecimalMultiply(Maps.getDouble(purchaseStoreMap, "purchaseQty"), Maps.getDouble(purchaseStoreMap,
                    "actualPrice")));
        }
        return purchaseStoreMapList;
    }

    @Override
    public List<MaterialModel> searchMaterialByGroupIdToList(Map<String, Object> inputMap) throws Exception {
        List<MaterialModel> list = null;
        if (!Maps.isEmpty(inputMap, "groupId")) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(Maps.getLong(inputMap, "farmId", getFarmId()), " AND FARM_ID = ?");
            sqlCon.addCondition(Maps.getLong(inputMap, "groupId"), " AND GROUP_ID = ?");
            list = getList(materialMapper, sqlCon);
            for (MaterialModel materialModel : list) {
                Map<String, Object> map = materialModel.getData();
                map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "rowId"), MaterialInfoEnum.MATERIAL_INFO));
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            }
        } else {
            list = new ArrayList<MaterialModel>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rowId", Maps.getLong(inputMap, "materialId"));
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "rowId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));

            MaterialModel materialModel = new MaterialModel();
            materialModel.setData(map);
            list.add(materialModel);
        }
        return list;
    }

    @Override
    public void editMaterialInput(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkEditMaterialInput(Maps.getString(inputMap, "billDate"), Maps.getLong(inputMap, "purchaseId"));

        // 入库单添加提交按钮
        String scRkdtjtjbz = ParamUtil.getSettingValueByCode("SC_RKDTJTJBZ");

        Date currentDate = new Date();

        String eventCode = null;
        if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(Maps.getString(inputMap, "purchaseEventCode"))) {
            eventCode = SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE;
        } else {
            eventCode = SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE;
        }

        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        InputModel inputModel = getBean(InputModel.class, inputMap);
        String status = null;
        if ("ON".equals(scRkdtjtjbz)) {
            status = SupplyChianConstants.ARRIVE_INPUT_STATUS_UNCOMMIT;
        } else {
            status = SupplyChianConstants.ARRIVE_INPUT_STATUS_COMMITTED;
        }
        inputModel.setStatus(status);
        inputModel.setFarmId(getFarmId());
        inputModel.setCompanyId(getCompanyId());
        inputModel.setBillCode(billCode);
        inputModel.setEventCode(eventCode);
        inputModel.setInputerId(getEmployeeId());
        inputModel.setOaUsername(getOaUsername());
        inputModel.setCreateId(getEmployeeId());
        inputModel.setCreateDate(currentDate);
        inputModel.setPrintTimes(0L);
        inputMapper.insert(inputModel);

        // 入库单需要提交
        if ("ON".equals(scRkdtjtjbz)) {
            List<InputDetailModel> inputDetailModelList = new ArrayList<InputDetailModel>();
            for (Map<String, Object> detailMap : detailList) {
                // 入库通用CHECK
                this.materialInputCommonCheck(inputMap, detailMap);

                String materialBatch = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_BATCH_NUMBER, getEmployeeId(), get10000CompanyId(),
                        get10000FarmId());

                // 采购单
                SqlCon purchaseDetailSqlCon = new SqlCon();
                purchaseDetailSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
                PurchaseDetailModel purchaseDetailModel = getList(purchaseDetailMapper, purchaseDetailSqlCon).get(0);

                // 有未提交的入库单无法修改订单
                this.haveNotCommitInputBillCheck(purchaseDetailModel.getMaterialOnly());

                InputDetailModel inputDetailModel = new InputDetailModel();
                inputDetailModel.setStatus(SupplyChianConstants.ARRIVE_INPUT_STATUS_UNCOMMIT);
                inputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                inputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                inputDetailModel.setFarmId(getFarmId());
                inputDetailModel.setCompanyId(getCompanyId());
                inputDetailModel.setInputId(inputModel.getRowId());
                inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                inputDetailModel.setMaterialId(purchaseDetailModel.getMaterialId());
                inputDetailModel.setMaterialOnly(purchaseDetailModel.getMaterialOnly());
                // 记住批次号同时记住拆分号
                inputDetailModel.setMaterialBatch(materialBatch + "*" + purchaseDetailModel.getMaterialSplit());
                inputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
                inputDetailModel.setInputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                inputDetailModel.setInputerId(getEmployeeId());
                inputDetailModel.setBillCode(billCode);
                inputDetailModel.setBillDate(inputModel.getBillDate());
                inputDetailModel.setEventCode(eventCode);
                inputDetailModel.setReceiptQty(0D);
                inputDetailModel.setCreateId(getEmployeeId());
                inputDetailModel.setCreateDate(currentDate);
                inputDetailModelList.add(inputDetailModel);

                StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                // 删除标识设置为2标识未提交
                storeMaterialModel.setDeletedFlag("2");
                storeMaterialModel.setFarmId(getFarmId());
                storeMaterialModel.setCompanyId(getCompanyId());
                storeMaterialModel.setMaterialId(purchaseDetailModel.getMaterialId());
                storeMaterialModel.setMaterialOnly(purchaseDetailModel.getMaterialOnly());
                // 记住批次号同时记住拆分号
                storeMaterialModel.setMaterialBatch(materialBatch + "*" + purchaseDetailModel.getMaterialSplit());
                storeMaterialModel.setProductionDate(Maps.getDate(detailMap, "productionDate"));
                storeMaterialModel.setEffectiveDate(Maps.getDate(detailMap, "effectiveDate"));
                storeMaterialModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                storeMaterialModel.setActualQty(Maps.getDouble(detailMap, "inputQty"));
                storeMaterialModel.setCreateDate(currentDate);
                storeMaterialMapper.insert(storeMaterialModel);
            }

            if (inputDetailModelList != null && !inputDetailModelList.isEmpty()) {
                inputDetailMapper.inserts(inputDetailModelList);
            }

        } else {
            // 入库单无需提交
            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
            HanaInputBill hanaInputBill = null;
            List<HanaInputBillDetail> hanaInputBillDetailList = null;
            String mtcBranchID = null;
            String mtcDeptID = null;
            if (isToHana) {
                hanaInputBill = new HanaInputBill();

                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());

                mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                String mtcCardCode = HanaUtil.getMTC_CardCode(Maps.getLong(inputMap, "supplierId"));

                // 分公司编码
                hanaInputBill.setMTC_BranchID(mtcBranchID);
                // 新农+单据编号
                hanaInputBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
                // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                hanaInputBill.setMTC_NumAtCard(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
                // 业务伙伴编号
                hanaInputBill.setMTC_CardCode(mtcCardCode);
                // 入库日期
                hanaInputBill.setMTC_DocDate(Maps.getDate(inputMap, "billDate"));
                // 入库日期
                hanaInputBill.setMTC_DocDueDate(Maps.getDate(inputMap, "billDate"));
                // 表行
                hanaInputBillDetailList = new ArrayList<HanaInputBillDetail>();

                hanaInputBill.setDetailList(hanaInputBillDetailList);
            }
            // END HANA

            List<InputDetailModel> inputDetailModelList = new ArrayList<InputDetailModel>();
            List<MonthAccountModel> insertMonthAccountModelList = new ArrayList<MonthAccountModel>();

            // 饲料相关操作标识
            boolean feedFlag = false;
            if (SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE.equals(eventCode)) {
                feedFlag = true;
            }

            // 用于饲料还原采购数量
            Map<Long, Double> feedPurchaseQtyReturnMap = new HashMap<Long, Double>();

            for (Map<String, Object> detailMap : detailList) {
                // CHECK
                this.materialInputCommonCheck(inputMap, detailMap);

                String materialBatch = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_BATCH_NUMBER, getEmployeeId(), get10000CompanyId(),
                        get10000FarmId());

                InputDetailModel inputDetailModel = new InputDetailModel();
                inputDetailModel.setStatus(SupplyChianConstants.ARRIVE_INPUT_STATUS_COMMITTED);
                inputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                inputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                inputDetailModel.setFarmId(getFarmId());
                inputDetailModel.setCompanyId(getCompanyId());
                inputDetailModel.setInputId(inputModel.getRowId());
                inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                inputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                inputDetailModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
                inputDetailModel.setMaterialBatch(materialBatch);
                inputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
                inputDetailModel.setInputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                inputDetailModel.setInputerId(getEmployeeId());
                inputDetailModel.setBillCode(inputModel.getBillCode());
                inputDetailModel.setBillDate(Maps.getDate(inputMap, "billDate"));
                inputDetailModel.setEventCode(inputModel.getEventCode());
                inputDetailModel.setReceiptQty(0D);
                inputDetailModel.setCreateId(getEmployeeId());
                inputDetailModel.setCreateDate(currentDate);
                inputDetailModelList.add(inputDetailModel);

                StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                storeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                storeMaterialModel.setFarmId(getFarmId());
                storeMaterialModel.setCompanyId(getCompanyId());
                storeMaterialModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                storeMaterialModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
                storeMaterialModel.setMaterialBatch(materialBatch);
                storeMaterialModel.setProductionDate(Maps.getDate(detailMap, "productionDate"));
                storeMaterialModel.setEffectiveDate(Maps.getDate(detailMap, "effectiveDate"));
                storeMaterialModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                storeMaterialModel.setActualQty(Maps.getDouble(detailMap, "inputQty"));
                storeMaterialModel.setCreateDate(currentDate);
                storeMaterialMapper.insert(storeMaterialModel);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Maps.getDate(inputMap, "billDate"));
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date startDate = calendar.getTime();
                String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);

                SqlCon monthAccountExistsSqlCon = new SqlCon();
                monthAccountExistsSqlCon.addMainSql("SELECT ROW_ID AS rowId FROM SC_M_MONTH_ACCOUNT");
                monthAccountExistsSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                monthAccountExistsSqlCon.addCondition(Maps.getLong(detailMap, "materialId"), " AND MATERIAL_ID = ?");
                monthAccountExistsSqlCon.addCondition(Maps.getString(detailMap, "materialOnly"), " AND MATERIAL_ONLY = ?");
                monthAccountExistsSqlCon.addCondition(materialBatch, " AND MATERIAL_BATCH = ?");
                monthAccountExistsSqlCon.addCondition(startDateString, " AND START_DATE = ?");
                monthAccountExistsSqlCon.addCondition(Maps.getLong(detailMap, "warehouseId"), " AND WAREHOUSE_ID = ?");
                monthAccountExistsSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ? LIMIT 1");

                List<MonthAccountModel> monthAccountExistsList = setSql(monthAccountMapper, monthAccountExistsSqlCon);

                if (monthAccountExistsList.isEmpty()) {
                    MonthAccountModel monthAccountModel = new MonthAccountModel();
                    monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    monthAccountModel.setFarmId(getFarmId());
                    monthAccountModel.setCompanyId(getCompanyId());
                    monthAccountModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                    monthAccountModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
                    monthAccountModel.setMaterialBatch(materialBatch);
                    monthAccountModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                    monthAccountModel.setStartQty(0D);
                    monthAccountModel.setStartDate(startDate);
                    monthAccountModel.setLockFlag("N");
                    monthAccountModel.setEventCode(inputModel.getEventCode());
                    monthAccountModel.setCreateId(getEmployeeId());
                    monthAccountModel.setCreateDate(currentDate);
                    insertMonthAccountModelList.add(monthAccountModel);
                }

                // 采购单
                Double inputQty = Maps.getDouble(detailMap, "inputQty");
                SqlCon purchaseDetailSqlCon = new SqlCon();
                purchaseDetailSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
                PurchaseDetailModel purchaseDetailModel = getList(purchaseDetailMapper, purchaseDetailSqlCon).get(0);

                // 有未提交的入库单无法修改订单
                this.haveNotCommitInputBillCheck(purchaseDetailModel.getMaterialOnly());

                // 饲料入库
                if (feedFlag) {
                    // 如果这一行已经被入库了（说明数据已经被修改过）
                    if (purchaseDetailModel.getInputId() != null) {
                        Thrower.throwException(SupplyChianException.QTY_HAS_CHANGED, Maps.getLong(detailMap, "errorAlertLineNumber"));
                    }
                    // 需要还原回第一条数据的数量
                    Double feedPurchaseQtyReturnQty = purchaseDetailModel.getPurchaseQty();

                    purchaseDetailModel.setInputId(inputModel.getRowId());
                    purchaseDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                    purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                    // 不是第一条数据，将采购数量为空
                    if (Maps.getLong(detailMap, "rowId") != Maps.getLong(detailMap, "splitId")) {
                        purchaseDetailModel.setPurchaseQty(0D);
                    }

                    purchaseDetailModel.setArriveQty(inputQty);
                    purchaseDetailMapper.update(purchaseDetailModel);

                    // 饲料允许入库数量超过采购数量
                    if (feedFlag) {
                        // 判断是否存在ARRIVE_QTY = 0的数据
                        SqlCon arriveQtyIsZeroPurchaseDetailSqlCon = new SqlCon();
                        arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getPurchaseId(), " AND PURCHASE_ID = ?");
                        arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getPurchaseLn(), " AND PURCHASE_LN = ?");
                        arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getFreeLn(), " AND FREE_LN = ?");
                        arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getRowId(), " AND ROW_ID <> ?");
                        arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getMaterialSplit(), " AND MATERIAL_SPLIT = ?");
                        arriveQtyIsZeroPurchaseDetailSqlCon.addMainSql(" AND ARRIVE_QTY = 0");
                        List<PurchaseDetailModel> arriveQtyIsZeroPurchaseDetailModelList = getList(purchaseDetailMapper,
                                arriveQtyIsZeroPurchaseDetailSqlCon);

                        if (arriveQtyIsZeroPurchaseDetailModelList.isEmpty()) {
                            PurchaseDetailModel insertPurchaseDetailModel = new PurchaseDetailModel();
                            insertPurchaseDetailModel.setStatus(CommonConstants.STATUS);
                            insertPurchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                            insertPurchaseDetailModel.setNotes(purchaseDetailModel.getNotes());
                            insertPurchaseDetailModel.setFarmId(purchaseDetailModel.getFarmId());
                            insertPurchaseDetailModel.setCompanyId(purchaseDetailModel.getCompanyId());
                            insertPurchaseDetailModel.setPurchaseId(purchaseDetailModel.getPurchaseId());
                            insertPurchaseDetailModel.setPurchaseLn(purchaseDetailModel.getPurchaseLn());
                            insertPurchaseDetailModel.setFreeLn(purchaseDetailModel.getFreeLn());
                            insertPurchaseDetailModel.setMaterialId(purchaseDetailModel.getMaterialId());
                            insertPurchaseDetailModel.setMaterialOnly(purchaseDetailModel.getMaterialOnly());
                            insertPurchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                            insertPurchaseDetailModel.setActualPrice(purchaseDetailModel.getActualPrice());
                            insertPurchaseDetailModel.setActualFreePercent(purchaseDetailModel.getActualFreePercent());
                            insertPurchaseDetailModel.setPassQty(0D);
                            insertPurchaseDetailModel.setPurchaseQty(0D);
                            insertPurchaseDetailModel.setFreeRelatedId(purchaseDetailModel.getFreeRelatedId());
                            insertPurchaseDetailModel.setIsPackage(purchaseDetailModel.getIsPackage());
                            insertPurchaseDetailModel.setArriveQty(0D);
                            insertPurchaseDetailModel.setGroupOrSelf(purchaseDetailModel.getGroupOrSelf());
                            insertPurchaseDetailModel.setSupplierId(purchaseDetailModel.getSupplierId());
                            insertPurchaseDetailModel.setRequireFarm(purchaseDetailModel.getRequireFarm());
                            insertPurchaseDetailModel.setRebateReason(purchaseDetailModel.getRebateReason());
                            insertPurchaseDetailModel.setRebatePrice(0D);
                            insertPurchaseDetailModel.setPurchaserId(purchaseDetailModel.getPurchaserId());
                            insertPurchaseDetailModel.setArriveDate(purchaseDetailModel.getArriveDate());
                            insertPurchaseDetailModel.setBillCode(purchaseDetailModel.getBillCode());
                            insertPurchaseDetailModel.setBillDate(purchaseDetailModel.getBillDate());
                            insertPurchaseDetailModel.setEventCode(purchaseDetailModel.getEventCode());
                            insertPurchaseDetailModel.setCreateId(getEmployeeId());
                            insertPurchaseDetailModel.setCreateDate(currentDate);
                            purchaseDetailMapper.insert(insertPurchaseDetailModel);
                        }
                    }

                    // 如果不是第一条，将采购数量还原到第一条数据
                    if (Maps.getLong(detailMap, "rowId") != Maps.getLong(detailMap, "splitId")) {
                        // 如果还没有被记录
                        if (!feedPurchaseQtyReturnMap.containsKey(Maps.getLong(detailMap, "splitId"))) {
                            feedPurchaseQtyReturnMap.put(Maps.getLong(detailMap, "splitId"), feedPurchaseQtyReturnQty);
                        } else {
                            // 已经被记录，相加数量
                            feedPurchaseQtyReturnMap.put(Maps.getLong(detailMap, "splitId"), bigDecimalAdd(feedPurchaseQtyReturnMap.get(Maps.getLong(
                                    detailMap, "splitId")), feedPurchaseQtyReturnQty));
                        }
                    }
                } else {
                    // 如果剩余的数量不等于页面上采购数量（总采购数量）-已入库数量（说明数据已经被修改过）
                    if (purchaseDetailModel.getPurchaseQty().compareTo(bigDecimalSubtract(Maps.getDouble(detailMap, "purchaseQty"), Maps.getDouble(
                            detailMap, "arriveQty"))) != 0) {
                        Thrower.throwException(SupplyChianException.QTY_HAS_CHANGED, Maps.getLong(detailMap, "errorAlertLineNumber"));
                    }

                    if (purchaseDetailModel.getPurchaseQty().compareTo(inputQty) == 0) {
                        // 入库的正好等于买入的明细数量
                        purchaseDetailModel.setInputId(inputModel.getRowId());
                        purchaseDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                        purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                        purchaseDetailModel.setArriveQty(inputQty);
                        purchaseDetailMapper.update(purchaseDetailModel);

                    } else if (purchaseDetailModel.getPurchaseQty().compareTo(inputQty) < 0) {
                        Thrower.throwException(SupplyChianException.INPUT_QTY_MUST_LESS_THAN_PURCHASE_QTY, Maps.getLong(detailMap,
                                "errorAlertLineNumber"));
                    } else {

                        // 入库的小于买入的明细数量
                        // 做法：将原有的条数拆分为2条
                        // 更新原有数据为已入库数据（插入inputId,inputLn,还原materialSplit,采购数量和到货数量相等并且等于inputQty）
                        // 插入一条数据和原数据一样,只有采购数量为原采购数量减去入库数量，materialSplit为原入库行数的materialSplit
                        Double splitQty = inputQty;
                        Double oldPurchaseQty = purchaseDetailModel.getPurchaseQty();
                        String materialSplit = purchaseDetailModel.getMaterialSplit();

                        purchaseDetailModel.setInputId(inputModel.getRowId());
                        purchaseDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                        purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                        purchaseDetailModel.setPurchaseQty(splitQty);
                        purchaseDetailModel.setArriveQty(splitQty);
                        purchaseDetailMapper.update(purchaseDetailModel);

                        // 剩余可入库数量
                        Double purchaseQty = bigDecimalSubtract(oldPurchaseQty, inputQty);

                        PurchaseDetailModel insertPurchaseDetailModel = new PurchaseDetailModel();
                        insertPurchaseDetailModel.setStatus(CommonConstants.STATUS);
                        insertPurchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        insertPurchaseDetailModel.setNotes(purchaseDetailModel.getNotes());
                        insertPurchaseDetailModel.setFarmId(purchaseDetailModel.getFarmId());
                        insertPurchaseDetailModel.setCompanyId(purchaseDetailModel.getCompanyId());
                        insertPurchaseDetailModel.setPurchaseId(purchaseDetailModel.getPurchaseId());
                        insertPurchaseDetailModel.setPurchaseLn(purchaseDetailModel.getPurchaseLn());
                        insertPurchaseDetailModel.setFreeLn(purchaseDetailModel.getFreeLn());
                        insertPurchaseDetailModel.setMaterialId(purchaseDetailModel.getMaterialId());
                        insertPurchaseDetailModel.setMaterialOnly(purchaseDetailModel.getMaterialOnly());
                        insertPurchaseDetailModel.setMaterialSplit(materialSplit);
                        insertPurchaseDetailModel.setActualPrice(purchaseDetailModel.getActualPrice());
                        insertPurchaseDetailModel.setActualFreePercent(purchaseDetailModel.getActualFreePercent());
                        insertPurchaseDetailModel.setPassQty(0D);
                        insertPurchaseDetailModel.setPurchaseQty(purchaseQty);
                        insertPurchaseDetailModel.setFreeRelatedId(purchaseDetailModel.getFreeRelatedId());
                        insertPurchaseDetailModel.setIsPackage(purchaseDetailModel.getIsPackage());
                        insertPurchaseDetailModel.setArriveQty(0D);
                        insertPurchaseDetailModel.setGroupOrSelf(purchaseDetailModel.getGroupOrSelf());
                        insertPurchaseDetailModel.setSupplierId(purchaseDetailModel.getSupplierId());
                        insertPurchaseDetailModel.setRequireFarm(purchaseDetailModel.getRequireFarm());
                        insertPurchaseDetailModel.setRebateReason(purchaseDetailModel.getRebateReason());
                        insertPurchaseDetailModel.setRebatePrice(0D);
                        insertPurchaseDetailModel.setPurchaserId(purchaseDetailModel.getPurchaserId());
                        insertPurchaseDetailModel.setArriveDate(purchaseDetailModel.getArriveDate());
                        insertPurchaseDetailModel.setBillCode(purchaseDetailModel.getBillCode());
                        insertPurchaseDetailModel.setBillDate(purchaseDetailModel.getBillDate());
                        insertPurchaseDetailModel.setEventCode(purchaseDetailModel.getEventCode());
                        insertPurchaseDetailModel.setCreateId(getEmployeeId());
                        insertPurchaseDetailModel.setCreateDate(currentDate);
                        purchaseDetailMapper.insert(insertPurchaseDetailModel);
                    }
                }

                // START HANA
                if (isToHana) {
                    String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(Maps.getLong(detailMap, "materialId"));
                    HanaInputBillDetail hanaInputBillDetail = new HanaInputBillDetail();
                    // 分公司编码
                    hanaInputBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaInputBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号（入库单编号）
                    hanaInputBillDetail.setMTC_BaseEntry(String.valueOf(inputDetailModel.getInputId()));
                    // 新农+单据行号（入库单行号）
                    hanaInputBillDetail.setMTC_BaseLine(String.valueOf(inputDetailModel.getInputLn()));
                    // 新农+采购单编号:采购单RowID
                    hanaInputBillDetail.setMTC_OrignEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                    // 新农+采购单行号:采购单购料行号*赠料行号
                    hanaInputBillDetail.setMTC_OrignLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" + String.valueOf(
                            purchaseDetailModel.getFreeLn()));
                    // 采购类型
                    hanaInputBillDetail.setMTC_PurType(purchaseDetailModel.getRebateReason());
                    // 返利金额
                    hanaInputBillDetail.setMTC_Rebate("0");
                    // 业务类型
                    hanaInputBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                    // 物料编号
                    hanaInputBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 仓库编号
                    hanaInputBillDetail.setMTC_WhsCode(String.valueOf(inputDetailModel.getInputWarehouseId()));
                    // 入库数量
                    hanaInputBillDetail.setMTC_Qty(String.valueOf(inputDetailModel.getInputQty()));
                    // 入库单价
                    hanaInputBillDetail.setMTC_QtyPrice(Maps.getString(detailMap, "actualPrice"));
                    // 入库金额
                    Double totalPrice = bigDecimalMultiply(inputDetailModel.getInputQty(), Maps.getDouble(detailMap, "actualPrice", 0D));
                    hanaInputBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                    // 批次编号
                    hanaInputBillDetail.setMTC_BatchNum(materialBatch);
                    // 生产日期
                    hanaInputBillDetail.setMTC_ProDate(Maps.getDate(detailMap, "productionDate"));
                    // 有效日期
                    hanaInputBillDetail.setMTC_ValidDate(Maps.getDate(detailMap, "effectiveDate"));

                    hanaInputBillDetailList.add(hanaInputBillDetail);
                }
                // END HANA

            }

            if (inputDetailModelList != null && !inputDetailModelList.isEmpty()) {
                inputDetailMapper.inserts(inputDetailModelList);
            }

            if (insertMonthAccountModelList != null && !insertMonthAccountModelList.isEmpty()) {
                monthAccountMapper.inserts(insertMonthAccountModelList);
            }

            if (!feedPurchaseQtyReturnMap.isEmpty()) {
                List<PurchaseDetailModel> feedPurchaseQtyReturnMapList = new ArrayList<PurchaseDetailModel>();
                for (Map.Entry<Long, Double> entry : feedPurchaseQtyReturnMap.entrySet()) {
                    PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
                    purchaseDetailModel.setRowId(entry.getKey());
                    purchaseDetailModel.setPurchaseQty(entry.getValue());
                    feedPurchaseQtyReturnMapList.add(purchaseDetailModel);
                }
                purchaseDetailMapper.updatesFeedPurchaseQtyReturn(feedPurchaseQtyReturnMapList);
            }

            updatePurchaseStoreStatus(Maps.getLong(inputMap, "purchaseId"), Maps.getString(inputMap, "purchaseEventCode"));

            // START HANA
            if (isToHana) {
                if (!hanaInputBill.getDetailList().isEmpty()) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(hanaInputBill.getMTC_BranchID());
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaInputBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码：采购入库 - 物料
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2020);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(hanaInputBill.getMTC_BaseEntry());
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
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaInputBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                    hanaCommonService.insertMTC_ITFC(mtcITFC);
                }
            }
            // END HANA
        }
    }

    // 入库check
    private void materialInputCommonCheck(Map<String, Object> inputMap, Map<String, Object> detailMap) {
        // 必须填写仓库
        if (Maps.isEmpty(detailMap, "warehouseId")) {
            Thrower.throwException(SupplyChianException.WAREHOUSE_IS_NULL, Maps.getLong(detailMap, "errorAlertLineNumber"));
        }

        // 必须填写生产日期或者有效日期
        if (Maps.isEmpty(detailMap, "productionDate") && Maps.isEmpty(detailMap, "effectiveDate")) {
            Thrower.throwException(SupplyChianException.EFFECTIVE_DATE_IS_NULL, Maps.getLong(detailMap, "errorAlertLineNumber"));
        }

        // 生产日期必须小于等于入库日期
        if (!Maps.isEmpty(detailMap, "productionDate")) {
            if (Maps.getDate(detailMap, "productionDate").after(Maps.getDate(inputMap, "billDate"))) {
                Thrower.throwException(SupplyChianException.PRODUCTION_DATE_AFTER_BILL_DATE, Maps.getLong(detailMap, "errorAlertLineNumber"));
            }
        }

        // 有效日期必须大于等于入库日期
        if (!Maps.isEmpty(detailMap, "effectiveDate")) {
            if (Maps.getDate(detailMap, "effectiveDate").before(Maps.getDate(inputMap, "billDate"))) {
                Thrower.throwException(SupplyChianException.EFFECTIVE_DATE_BEFORE_BILL_DATE, Maps.getLong(detailMap, "errorAlertLineNumber"));
            }
        }
    }

    private void checkEditMaterialInput(String billDateString, Long purchaseId) throws Exception {

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(billDateString);

        // 根据供应商判断限制日期
        // 比如：新农和新农翔月中价格变动，所以上半月的入库只能选择上半月的采购单，下半月的入库只能选择下半月的采购单
        checkBillDateBySupplierId(billDateString, purchaseId);
    }

    private void checkBillDateBySupplierId(String billDateString, Long purchaseId) throws Exception {
        PurchaseModel purchaseModel = purchaseMapper.searchById(purchaseId);

        Date billDate = TimeUtil.parseDate(billDateString, TimeUtil.DATE_FORMAT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(TimeUtil.parseDate(billDateString, TimeUtil.DATE_FORMAT));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date monthOfbillDate = calendar.getTime();

        calendar.setTime(purchaseModel.getBillDate());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date monthOfPurchaseDate = calendar.getTime();

        // 如果入库日期和采购日期不是一个月份
        if (!monthOfbillDate.equals(monthOfPurchaseDate)) {
            Thrower.throwException(SupplyChianException.PURCHASE_BILL_DATE_AND_INPUT_BILL_DATE_IS_NOT_A_SAME_MONTH);
        }

        // 如果是新农（ID:3），新农翔（ID：305），中粮（ID：441），正大（ID：440）
        // 因为月中饲料存在价格变动
        if (purchaseModel.getSupplierId().compareTo(3L) == 0 || purchaseModel.getSupplierId().compareTo(305L) == 0 || purchaseModel.getSupplierId()
                .compareTo(441L) == 0 || purchaseModel.getSupplierId().compareTo(440L) == 0) {
            Date purchaseBillDate = purchaseModel.getBillDate();
            calendar.setTime(purchaseBillDate);
            calendar.set(Calendar.DAY_OF_MONTH, 15);
            Date midDateMonth = calendar.getTime();

            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(purchaseModel.getSupplierId()));

            // 入库时间<=月中 && 采购时间>月中
            if (billDate.compareTo(midDateMonth) <= 0 && purchaseBillDate.after(midDateMonth)) {
                Thrower.throwException(SupplyChianException.PURCHASE_PRICE_CHANGE_AT_MID_MONTH, Maps.getString(companyInfoMap, "SORT_NAME"),
                        billDateString, "月中前");
            }

            // 入库时间>月中 && 采购时间<=月中
            if (billDate.after(midDateMonth) && purchaseBillDate.compareTo(midDateMonth) <= 0) {
                Thrower.throwException(SupplyChianException.PURCHASE_PRICE_CHANGE_AT_MID_MONTH, Maps.getString(companyInfoMap, "SORT_NAME"),
                        billDateString, "月中后");
            }
        }
    }

    // 有未提交的入库单无法修改订单
    private void haveNotCommitInputBillCheck(String materialOnly) {
        this.haveNotCommitInputBillCheck(materialOnly, null);
    }

    // 有未提交的入库单无法修改订单
    private void haveNotCommitInputBillCheck(String materialOnly, Long lineNumber) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(SupplyChianConstants.ARRIVE_INPUT_STATUS_UNCOMMIT, " AND STATUS = ?");
        sqlCon.addCondition(materialOnly, " AND MATERIAL_ONLY = ? LIMIT 1");

        // 存在未提交的订单
        List<InputDetailModel> inputDetailModelList = getList(inputDetailMapper, sqlCon);
        if (!inputDetailModelList.isEmpty()) {
            if (lineNumber != null) {
                Thrower.throwException(SupplyChianException.PURCHASE_BILL_HAVE_BEEN_LOCKED_WITH_LINENUMBER, lineNumber);
            } else {
                Thrower.throwException(SupplyChianException.PURCHASE_BILL_HAVE_BEEN_LOCKED);
            }

        }
    }

    private Map<String, Object> getMaterialBatchNumber(String materialOnly, String materialBatch, long warehouseId) throws Exception {
        Map<String, Object> materialBatchMap = new HashMap<String, Object>();

        SqlCon materialBatchSqlCon = new SqlCon();
        materialBatchSqlCon.addMainSql("SELECT ROW_ID FROM SC_M_STORE_MATERIAL");
        materialBatchSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        materialBatchSqlCon.addCondition(warehouseId, " AND WAREHOUSE_ID = ?");
        materialBatchSqlCon.addCondition(materialOnly, " AND MATERIAL_ONLY = ?");
        materialBatchSqlCon.addCondition(materialBatch, " AND MATERIAL_BATCH = ?");
        materialBatchSqlCon.addMainSql(" LIMIT 1");

        List<StoreMaterialModel> materialBatchList = setSql(storeMaterialMapper, materialBatchSqlCon);

        // 存在
        if (!materialBatchList.isEmpty()) {
            StoreMaterialModel storeMaterialModel = materialBatchList.get(0);
            materialBatchMap.put("rowId", storeMaterialModel.getRowId());
        }

        return materialBatchMap;
    }

    private void updatePurchaseStoreStatus(Long purchaseId, String eventCode) {
        if (purchaseId != null) {
            if (SupplyChianConstants.EVENT_CODE_MATERIAL_PURCHASE.equals(eventCode)) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS, "SELECT T1.ROW_ID, ? AS STATUS FROM SC_M_BILL_PURCHASE T1");
                sqlCon.addCondition(purchaseId, " WHERE T1.DELETED_FLAG = '0' AND T1.ROW_ID = ?");
                sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL T2 WHERE T2.DELETED_FLAG = '0' AND T2.STATUS = '1'");
                sqlCon.addMainSql(" AND T1.ROW_ID = T2.PURCHASE_ID AND T2.PURCHASE_QTY = T2.ARRIVE_QTY LIMIT 1)");
                sqlCon.addMainSql(" UNION");
                sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " SELECT T3.ROW_ID, ? AS STATUS FROM SC_M_BILL_PURCHASE T3");
                sqlCon.addCondition(purchaseId, " WHERE T3.DELETED_FLAG = '0' AND T3.ROW_ID = ?");
                sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL T4 WHERE T4.DELETED_FLAG = '0' AND T4.STATUS = '1'");
                sqlCon.addMainSql(" AND T3.ROW_ID = T4.PURCHASE_ID AND T4.PURCHASE_QTY > T4.ARRIVE_QTY LIMIT 1)");
                sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL T5 WHERE T5.DELETED_FLAG = '0' AND T5.STATUS = '1'");
                sqlCon.addMainSql(" AND T3.ROW_ID = T5.PURCHASE_ID AND T5.PURCHASE_QTY = T5.ARRIVE_QTY LIMIT 1)");
                sqlCon.addMainSql(" UNION");
                sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_COMPLETED,
                        " SELECT T6.ROW_ID, ? AS STATUS FROM SC_M_BILL_PURCHASE T6");
                sqlCon.addCondition(purchaseId, " WHERE T6.DELETED_FLAG = '0' AND T6.ROW_ID = ?");
                sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL T7 WHERE T7.DELETED_FLAG = '0' AND T7.STATUS = '1'");
                sqlCon.addMainSql(" AND T6.ROW_ID = T7.PURCHASE_ID AND T7.PURCHASE_QTY > T7.ARRIVE_QTY LIMIT 1)");
                List<PurchaseModel> purchaseModelList = setSql(purchaseMapper, sqlCon);
                purchaseMapper.updates(purchaseModelList);
            } else {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS, "SELECT T1.ROW_ID, ? AS STATUS FROM SC_M_BILL_PURCHASE T1");
                sqlCon.addCondition(purchaseId, " WHERE T1.DELETED_FLAG = '0' AND T1.ROW_ID = ?");
                sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL T2 WHERE T2.DELETED_FLAG = '0' AND T2.STATUS = '1'");
                sqlCon.addMainSql(" AND T1.ROW_ID = T2.PURCHASE_ID AND T2.PURCHASE_QTY = T2.ARRIVE_QTY LIMIT 1)");
                sqlCon.addMainSql(" UNION");
                sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " SELECT T3.ROW_ID, ? AS STATUS FROM SC_M_BILL_PURCHASE T3");
                sqlCon.addCondition(purchaseId, " WHERE T3.DELETED_FLAG = '0' AND T3.ROW_ID = ?");
                sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL T4 WHERE T4.DELETED_FLAG = '0' AND T4.STATUS = '1'");
                sqlCon.addMainSql(" AND T3.ROW_ID = T4.PURCHASE_ID AND T4.ARRIVE_QTY <> 0 LIMIT 1)");
                List<PurchaseModel> purchaseModelList = setSql(purchaseMapper, sqlCon);
                purchaseMapper.updates(purchaseModelList);
            }
        }
    }

    @Override
    public List<Map<String, Object>> searchMaterialInputCommitDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT MATERIAL_ID materialId, INPUT_WAREHOUSE_ID AS inputWarehouseId, _clearEndZero(INPUT_QTY) AS inputQty");
        sqlCon.addMainSql(", _clearEndZero((SELECT ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY LIMIT 1)) AS actualPrice");
        sqlCon.addMainSql(", (SELECT FREE_LN FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY LIMIT 1) AS freeLn");
        sqlCon.addMainSql(", (SELECT PRODUCTION_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '2'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS productionDate");
        sqlCon.addMainSql(", (SELECT EFFECTIVE_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '2'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID LIMIT 1) AS effectiveDate");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.ARRIVE_INPUT_STATUS_UNCOMMIT, " AND T1.STATUS = ?");
        sqlCon.addCondition(Maps.getLong(inputMap, "inputId"), " AND T1.INPUT_ID = ?");
        sqlCon.addMainSql(" ORDER BY INPUT_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            map.put("inputWarehouseName", CacheUtil.getName(Maps.getString(map, "inputWarehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            map.put("inputQtyName", Maps.getString(map, "inputQty") + Maps.getString(map, "unit"));
            map.put("actualPriceName", Maps.getString(map, "actualPrice") + "元");

            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
            map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));

            if (Maps.getLong(map, "freeLn") == 0D) {
                map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
            } else {
                map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
            }
        }

        return list;
    }

    @Override
    public void editMaterialInputCommit(Map<String, Object> inputMap) throws Exception {
        Long inputId = Maps.getLong(inputMap, "commitInputId");

        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(inputId, " AND ROW_ID = ? FOR UPDATE");
        InputModel inputModel = getModel(inputMapper, sqlCon);
        if (inputModel == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "数据异常，请刷新页面！。。。");
        }
        if (!SupplyChianConstants.ARRIVE_INPUT_STATUS_UNCOMMIT.equals(inputModel.getStatus())) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "该入库单不是【未提交】状态,无法入库,请刷新页面，重新操作！。。。");
        }
        inputModel.setStatus(SupplyChianConstants.ARRIVE_INPUT_STATUS_COMMITTED);

        SqlCon inputDetailSqlCon = new SqlCon();
        inputDetailSqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T2.ROW_ID AS storeRowId");
        inputDetailSqlCon.addMainSql(", T1.INPUT_ID AS inputId, T1.INPUT_LN AS inputLn, T1.MATERIAL_ID AS materialId");
        inputDetailSqlCon.addMainSql(", T1.MATERIAL_ONLY AS materialOnly, T1.MATERIAL_BATCH AS materialBatch, T1.INPUT_QTY AS inputQty");
        inputDetailSqlCon.addMainSql(", T1.INPUT_WAREHOUSE_ID inputWarehouseId");
        inputDetailSqlCon.addMainSql(", T2.PRODUCTION_DATE AS productionDate, T2.EFFECTIVE_DATE AS effectiveDate");
        inputDetailSqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        inputDetailSqlCon.addMainSql(" INNER JOIN SC_M_STORE_MATERIAL T2");
        // 未提交 DELETED_FLAG = '2'
        inputDetailSqlCon.addMainSql(" ON (T2.DELETED_FLAG = '2'");
        inputDetailSqlCon.addMainSql(" AND T1.MATERIAL_ONLY = T2.MATERIAL_ONLY");
        inputDetailSqlCon.addMainSql(" AND T1.MATERIAL_BATCH = T2.MATERIAL_BATCH");
        inputDetailSqlCon.addMainSql(" AND T1.INPUT_WAREHOUSE_ID = T2.WAREHOUSE_ID)");
        // 未提交STATUS = '0'
        inputDetailSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND STATUS = '0'");
        inputDetailSqlCon.addCondition(inputId, " AND INPUT_ID = ?");
        inputDetailSqlCon.addMainSql(" ORDER BY T1.INPUT_LN");

        Map<String, String> inputDetailSqlMap = new HashMap<String, String>();
        inputDetailSqlMap.put("sql", inputDetailSqlCon.getCondition());

        List<Map<String, Object>> inputDetailMapList = paramMapper.getObjectInfos(inputDetailSqlMap);

        if (inputDetailMapList.isEmpty()) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "明细数据异常，请刷新页面！。。。");
        }

        List<InputDetailModel> updateInputDetailModelList = new ArrayList<InputDetailModel>();
        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<MonthAccountModel> insertMonthAccountModelList = new ArrayList<MonthAccountModel>();

        Long purchaseId = null;
        String purchaseEventCode = null;

        // 饲料相关操作标识
        boolean feedFlag = false;
        if (SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE.equals(inputModel.getEventCode())) {
            feedFlag = true;
        }

        // 用于饲料还原采购数量
        Map<Long, Double> feedPurchaseQtyReturnMap = new HashMap<Long, Double>();

        Date currentDate = new Date();

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(inputModel.getFarmId());
        HanaInputBill hanaInputBill = null;
        List<HanaInputBillDetail> hanaInputBillDetailList = null;
        String mtcBranchID = null;
        String mtcDeptID = null;
        Long supplierId = null;
        if (isToHana) {
            hanaInputBill = new HanaInputBill();
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(inputModel.getFarmId());

            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            // 分公司编码
            hanaInputBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaInputBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
            // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
            hanaInputBill.setMTC_NumAtCard(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
            // 入库日期
            hanaInputBill.setMTC_DocDate(inputModel.getBillDate());
            // 入库日期
            hanaInputBill.setMTC_DocDueDate(inputModel.getBillDate());
            // 表行
            hanaInputBillDetailList = new ArrayList<HanaInputBillDetail>();

            hanaInputBill.setDetailList(hanaInputBillDetailList);
        }
        // END HANA

        for (Map<String, Object> inputDetailMap : inputDetailMapList) {
            String materialSplitAndMaterialBatch = Maps.getString(inputDetailMap, "materialBatch");
            String[] splitAndBatch = materialSplitAndMaterialBatch.split("\\*");
            String materialBatch = splitAndBatch[0];
            String materialSplit = splitAndBatch[1];

            InputDetailModel updateInputDetailModel = new InputDetailModel();
            updateInputDetailModel.setRowId(Maps.getLong(inputDetailMap, "rowId"));
            updateInputDetailModel.setStatus(SupplyChianConstants.ARRIVE_INPUT_STATUS_COMMITTED);
            updateInputDetailModel.setMaterialBatch(materialBatch);
            updateInputDetailModelList.add(updateInputDetailModel);

            StoreMaterialModel updateStoreMaterialModel = new StoreMaterialModel();
            updateStoreMaterialModel.setRowId(Maps.getLong(inputDetailMap, "storeRowId"));
            updateStoreMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            updateStoreMaterialModel.setMaterialBatch(materialBatch);
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inputModel.getBillDate());
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);

            SqlCon monthAccountExistsSqlCon = new SqlCon();
            monthAccountExistsSqlCon.addMainSql("SELECT ROW_ID AS rowId FROM SC_M_MONTH_ACCOUNT");
            monthAccountExistsSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            monthAccountExistsSqlCon.addCondition(Maps.getLong(inputDetailMap, "materialId"), " AND MATERIAL_ID = ?");
            monthAccountExistsSqlCon.addCondition(Maps.getString(inputDetailMap, "materialOnly"), " AND MATERIAL_ONLY = ?");
            monthAccountExistsSqlCon.addCondition(materialBatch, " AND MATERIAL_BATCH = ?");
            monthAccountExistsSqlCon.addCondition(startDateString, " AND START_DATE = ?");
            monthAccountExistsSqlCon.addCondition(Maps.getLong(inputDetailMap, "inputWarehouseId"), " AND WAREHOUSE_ID = ?");
            monthAccountExistsSqlCon.addConditionWithNull(inputModel.getFarmId(), " AND FARM_ID = ? LIMIT 1");

            List<MonthAccountModel> monthAccountExistsList = setSql(monthAccountMapper, monthAccountExistsSqlCon);

            if (monthAccountExistsList.isEmpty()) {
                MonthAccountModel monthAccountModel = new MonthAccountModel();
                monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                monthAccountModel.setFarmId(getFarmId());
                monthAccountModel.setCompanyId(getCompanyId());
                monthAccountModel.setMaterialId(Maps.getLong(inputDetailMap, "materialId"));
                monthAccountModel.setMaterialOnly(Maps.getString(inputDetailMap, "materialOnly"));
                monthAccountModel.setMaterialBatch(materialBatch);
                monthAccountModel.setWarehouseId(Maps.getLong(inputDetailMap, "inputWarehouseId"));
                monthAccountModel.setStartQty(0D);
                monthAccountModel.setStartDate(startDate);
                monthAccountModel.setLockFlag("N");
                monthAccountModel.setEventCode(inputModel.getEventCode());
                monthAccountModel.setCreateId(getEmployeeId());
                monthAccountModel.setCreateDate(currentDate);
                insertMonthAccountModelList.add(monthAccountModel);
            }

            // 采购单
            Double inputQty = Maps.getDouble(inputDetailMap, "inputQty");
            SqlCon purchaseDetailSqlCon = new SqlCon();
            purchaseDetailSqlCon.addConditionWithNull(Maps.getString(inputDetailMap, "materialOnly"), " AND MATERIAL_ONLY = ?");
            purchaseDetailSqlCon.addConditionWithNull(materialSplit, " AND MATERIAL_SPLIT = ?");
            purchaseDetailSqlCon.addMainSql(" AND ARRIVE_QTY = 0 FOR UPDATE");
            List<PurchaseDetailModel> purchaseDetailModelList = getList(purchaseDetailMapper, purchaseDetailSqlCon);
            if (purchaseDetailModelList.size() > 1) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "明细数据异常，请联系管理员！。。。");
            }

            PurchaseDetailModel purchaseDetailModel = purchaseDetailModelList.get(0);

            if (purchaseId == null) {
                purchaseId = purchaseDetailModel.getPurchaseId();
            }

            if (purchaseEventCode == null) {
                purchaseEventCode = purchaseDetailModel.getEventCode();
            }

            if (supplierId == null) {
                PurchaseModel purchaseModel = purchaseMapper.searchById(purchaseDetailModel.getPurchaseId());
                supplierId = purchaseModel.getSupplierId();
            }

            // 饲料入库
            if (feedFlag) {
                SqlCon splitSqlCon = new SqlCon();
                splitSqlCon.addMainSql("SELECT MIN(ROW_ID) AS rowId FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0'");
                splitSqlCon.addMainSql(" AND PASS_QTY <> 0");
                splitSqlCon.addCondition(purchaseDetailModel.getMaterialId(), " AND MATERIAL_ID = ?");
                splitSqlCon.addCondition(purchaseDetailModel.getPurchaseId(), " AND PURCHASE_ID = ?");
                splitSqlCon.addCondition(purchaseDetailModel.getMaterialOnly(), " AND MATERIAL_ONLY = ?");

                List<PurchaseDetailModel> getPurchaseDetailMinRowIdModelList = setSql(purchaseDetailMapper, splitSqlCon);
                if (getPurchaseDetailMinRowIdModelList.isEmpty()) {
                    Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "采购单:【" + purchaseDetailModel.getBillCode()
                            + "】信息存在异常，请检查采购数量是否大于0！");
                }

                Long splitId = getPurchaseDetailMinRowIdModelList.get(0).getRowId();

                // 需要还原回第一条数据的数量
                Double feedPurchaseQtyReturnQty = purchaseDetailModel.getPurchaseQty();

                purchaseDetailModel.setInputId(Maps.getLong(inputDetailMap, "inputId"));
                purchaseDetailModel.setInputLn(Maps.getLong(inputDetailMap, "inputLn"));
                purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                // 不是第一条数据，将采购数量为空
                if (!splitId.equals(purchaseDetailModel.getRowId())) {
                    purchaseDetailModel.setPurchaseQty(0D);
                }

                purchaseDetailModel.setArriveQty(inputQty);
                purchaseDetailMapper.update(purchaseDetailModel);

                // 饲料允许入库数量超过采购数量
                if (feedFlag) {
                    // 判断是否存在ARRIVE_QTY = 0的数据
                    SqlCon arriveQtyIsZeroPurchaseDetailSqlCon = new SqlCon();
                    arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getPurchaseId(), " AND PURCHASE_ID = ?");
                    arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getPurchaseLn(), " AND PURCHASE_LN = ?");
                    arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getFreeLn(), " AND FREE_LN = ?");
                    arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getRowId(), " AND ROW_ID <> ?");
                    arriveQtyIsZeroPurchaseDetailSqlCon.addConditionWithNull(purchaseDetailModel.getMaterialSplit(), " AND MATERIAL_SPLIT = ?");
                    arriveQtyIsZeroPurchaseDetailSqlCon.addMainSql(" AND ARRIVE_QTY = 0");
                    List<PurchaseDetailModel> arriveQtyIsZeroPurchaseDetailModelList = getList(purchaseDetailMapper,
                            arriveQtyIsZeroPurchaseDetailSqlCon);

                    if (arriveQtyIsZeroPurchaseDetailModelList.isEmpty()) {
                        PurchaseDetailModel insertPurchaseDetailModel = new PurchaseDetailModel();
                        insertPurchaseDetailModel.setStatus(CommonConstants.STATUS);
                        insertPurchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                        insertPurchaseDetailModel.setNotes(purchaseDetailModel.getNotes());
                        insertPurchaseDetailModel.setFarmId(purchaseDetailModel.getFarmId());
                        insertPurchaseDetailModel.setCompanyId(purchaseDetailModel.getCompanyId());
                        insertPurchaseDetailModel.setPurchaseId(purchaseDetailModel.getPurchaseId());
                        insertPurchaseDetailModel.setPurchaseLn(purchaseDetailModel.getPurchaseLn());
                        insertPurchaseDetailModel.setFreeLn(purchaseDetailModel.getFreeLn());
                        insertPurchaseDetailModel.setMaterialId(purchaseDetailModel.getMaterialId());
                        insertPurchaseDetailModel.setMaterialOnly(purchaseDetailModel.getMaterialOnly());
                        insertPurchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                        insertPurchaseDetailModel.setActualPrice(purchaseDetailModel.getActualPrice());
                        insertPurchaseDetailModel.setActualFreePercent(purchaseDetailModel.getActualFreePercent());
                        insertPurchaseDetailModel.setPassQty(0D);
                        insertPurchaseDetailModel.setPurchaseQty(0D);
                        insertPurchaseDetailModel.setFreeRelatedId(purchaseDetailModel.getFreeRelatedId());
                        insertPurchaseDetailModel.setIsPackage(purchaseDetailModel.getIsPackage());
                        insertPurchaseDetailModel.setArriveQty(0D);
                        insertPurchaseDetailModel.setGroupOrSelf(purchaseDetailModel.getGroupOrSelf());
                        insertPurchaseDetailModel.setSupplierId(purchaseDetailModel.getSupplierId());
                        insertPurchaseDetailModel.setRequireFarm(purchaseDetailModel.getRequireFarm());
                        insertPurchaseDetailModel.setRebateReason(purchaseDetailModel.getRebateReason());
                        insertPurchaseDetailModel.setRebatePrice(0D);
                        insertPurchaseDetailModel.setPurchaserId(purchaseDetailModel.getPurchaserId());
                        insertPurchaseDetailModel.setArriveDate(purchaseDetailModel.getArriveDate());
                        insertPurchaseDetailModel.setBillCode(purchaseDetailModel.getBillCode());
                        insertPurchaseDetailModel.setBillDate(purchaseDetailModel.getBillDate());
                        insertPurchaseDetailModel.setEventCode(purchaseDetailModel.getEventCode());
                        insertPurchaseDetailModel.setCreateId(getEmployeeId());
                        insertPurchaseDetailModel.setCreateDate(currentDate);
                        purchaseDetailMapper.insert(insertPurchaseDetailModel);
                    }
                }

                // 如果不是第一条，将采购数量还原到第一条数据
                if (!splitId.equals(purchaseDetailModel.getRowId())) {
                    // 如果还没有被记录
                    if (!feedPurchaseQtyReturnMap.containsKey(splitId)) {
                        feedPurchaseQtyReturnMap.put(splitId, feedPurchaseQtyReturnQty);
                    } else {
                        // 已经被记录，相加数量
                        feedPurchaseQtyReturnMap.put(splitId, bigDecimalAdd(feedPurchaseQtyReturnMap.get(splitId), feedPurchaseQtyReturnQty));
                    }
                }
            } else {
                if (purchaseDetailModel.getPurchaseQty().compareTo(inputQty) == 0) {
                    // 入库的正好等于买入的明细数量
                    purchaseDetailModel.setInputId(Maps.getLong(inputDetailMap, "inputId"));
                    purchaseDetailModel.setInputLn(Maps.getLong(inputDetailMap, "inputLn"));
                    purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                    purchaseDetailModel.setArriveQty(inputQty);
                    purchaseDetailMapper.update(purchaseDetailModel);

                } else if (purchaseDetailModel.getPurchaseQty().compareTo(inputQty) < 0) {
                    Thrower.throwException(SupplyChianException.INPUT_QTY_MUST_LESS_THAN_PURCHASE_QTY, Maps.getString(inputDetailMap, "inputLn"),
                            bigDecimalSubtract(inputQty, purchaseDetailModel.getPurchaseQty()));

                } else {
                    // 入库的小于买入的明细数量
                    // 做法：将原有的条数拆分为2条
                    // 更新原有数据为已入库数据（插入inputId,inputLn,还原materialSplit,采购数量和到货数量相等并且等于inputQty）
                    // 插入一条数据和原数据一样,只有采购数量为原采购数量减去入库数量，materialSplit为原入库行数的materialSplit
                    Double splitQty = inputQty;
                    Double oldPurchaseQty = purchaseDetailModel.getPurchaseQty();

                    purchaseDetailModel.setInputId(Maps.getLong(inputDetailMap, "inputId"));
                    purchaseDetailModel.setInputLn(Maps.getLong(inputDetailMap, "inputLn"));
                    purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                    purchaseDetailModel.setPurchaseQty(splitQty);
                    purchaseDetailModel.setArriveQty(splitQty);
                    purchaseDetailMapper.update(purchaseDetailModel);

                    // 剩余可入库数量
                    Double purchaseQty = bigDecimalSubtract(oldPurchaseQty, inputQty);

                    PurchaseDetailModel insertPurchaseDetailModel = new PurchaseDetailModel();
                    insertPurchaseDetailModel.setStatus(CommonConstants.STATUS);
                    insertPurchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    insertPurchaseDetailModel.setNotes(purchaseDetailModel.getNotes());
                    insertPurchaseDetailModel.setFarmId(purchaseDetailModel.getFarmId());
                    insertPurchaseDetailModel.setCompanyId(purchaseDetailModel.getCompanyId());
                    insertPurchaseDetailModel.setPurchaseId(purchaseDetailModel.getPurchaseId());
                    insertPurchaseDetailModel.setPurchaseLn(purchaseDetailModel.getPurchaseLn());
                    insertPurchaseDetailModel.setFreeLn(purchaseDetailModel.getFreeLn());
                    insertPurchaseDetailModel.setMaterialId(purchaseDetailModel.getMaterialId());
                    insertPurchaseDetailModel.setMaterialOnly(purchaseDetailModel.getMaterialOnly());
                    insertPurchaseDetailModel.setMaterialSplit(materialSplit);
                    insertPurchaseDetailModel.setActualPrice(purchaseDetailModel.getActualPrice());
                    insertPurchaseDetailModel.setActualFreePercent(purchaseDetailModel.getActualFreePercent());
                    insertPurchaseDetailModel.setPassQty(0D);
                    insertPurchaseDetailModel.setPurchaseQty(purchaseQty);
                    insertPurchaseDetailModel.setFreeRelatedId(purchaseDetailModel.getFreeRelatedId());
                    insertPurchaseDetailModel.setIsPackage(purchaseDetailModel.getIsPackage());
                    insertPurchaseDetailModel.setArriveQty(0D);
                    insertPurchaseDetailModel.setGroupOrSelf(purchaseDetailModel.getGroupOrSelf());
                    insertPurchaseDetailModel.setSupplierId(purchaseDetailModel.getSupplierId());
                    insertPurchaseDetailModel.setRequireFarm(purchaseDetailModel.getRequireFarm());
                    insertPurchaseDetailModel.setRebateReason(purchaseDetailModel.getRebateReason());
                    insertPurchaseDetailModel.setRebatePrice(0D);
                    insertPurchaseDetailModel.setPurchaserId(purchaseDetailModel.getPurchaserId());
                    insertPurchaseDetailModel.setArriveDate(purchaseDetailModel.getArriveDate());
                    insertPurchaseDetailModel.setBillCode(purchaseDetailModel.getBillCode());
                    insertPurchaseDetailModel.setBillDate(purchaseDetailModel.getBillDate());
                    insertPurchaseDetailModel.setEventCode(purchaseDetailModel.getEventCode());
                    insertPurchaseDetailModel.setCreateId(getEmployeeId());
                    insertPurchaseDetailModel.setCreateDate(currentDate);
                    purchaseDetailMapper.insert(insertPurchaseDetailModel);
                }
            }

            // START HANA
            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(Maps.getLong(inputDetailMap, "materialId"));
                HanaInputBillDetail hanaInputBillDetail = new HanaInputBillDetail();
                // 分公司编码
                hanaInputBillDetail.setMTC_BranchID(mtcBranchID);
                // 猪场编码
                hanaInputBillDetail.setMTC_DeptID(mtcDeptID);
                // 新农+单据编号（入库单编号）
                hanaInputBillDetail.setMTC_BaseEntry(Maps.getString(inputDetailMap, "inputId"));
                // 新农+单据行号（入库单行号）
                hanaInputBillDetail.setMTC_BaseLine(Maps.getString(inputDetailMap, "inputLn"));
                // 新农+采购单编号:采购单RowID
                hanaInputBillDetail.setMTC_OrignEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                // 新农+采购单行号:采购单购料行号*赠料行号
                hanaInputBillDetail.setMTC_OrignLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" + String.valueOf(purchaseDetailModel
                        .getFreeLn()));
                // 采购类型
                hanaInputBillDetail.setMTC_PurType(purchaseDetailModel.getRebateReason());
                // 返利金额
                hanaInputBillDetail.setMTC_Rebate("0");
                // 业务类型
                hanaInputBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                // 物料编号
                hanaInputBillDetail.setMTC_ItemCode(mtcItemCode);
                // 仓库编号
                hanaInputBillDetail.setMTC_WhsCode(String.valueOf(Maps.getString(inputDetailMap, "inputWarehouseId")));
                // 入库数量
                hanaInputBillDetail.setMTC_Qty(String.valueOf(inputQty));
                // 入库单价
                hanaInputBillDetail.setMTC_QtyPrice(String.valueOf(purchaseDetailModel.getActualPrice()));
                // 入库金额
                Double totalPrice = bigDecimalMultiply(inputQty, purchaseDetailModel.getActualPrice());
                hanaInputBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                // 批次编号
                hanaInputBillDetail.setMTC_BatchNum(materialBatch);
                // 生产日期
                hanaInputBillDetail.setMTC_ProDate(Maps.getDate(inputDetailMap, "productionDate"));
                // 有效日期
                hanaInputBillDetail.setMTC_ValidDate(Maps.getDate(inputDetailMap, "effectiveDate"));

                hanaInputBillDetailList.add(hanaInputBillDetail);
            }
            // END HANA
        }

        inputMapper.update(inputModel);

        if (updateInputDetailModelList != null && !updateInputDetailModelList.isEmpty()) {
            inputDetailMapper.updates(updateInputDetailModelList);
        }

        if (updateStoreMaterialModelList != null && !updateStoreMaterialModelList.isEmpty()) {
            storeMaterialMapper.updates(updateStoreMaterialModelList);
        }

        if (insertMonthAccountModelList != null && !insertMonthAccountModelList.isEmpty()) {
            monthAccountMapper.inserts(insertMonthAccountModelList);
        }

        if (!feedPurchaseQtyReturnMap.isEmpty()) {
            List<PurchaseDetailModel> feedPurchaseQtyReturnMapList = new ArrayList<PurchaseDetailModel>();
            for (Map.Entry<Long, Double> entry : feedPurchaseQtyReturnMap.entrySet()) {
                PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
                purchaseDetailModel.setRowId(entry.getKey());
                purchaseDetailModel.setPurchaseQty(entry.getValue());
                feedPurchaseQtyReturnMapList.add(purchaseDetailModel);
            }
            purchaseDetailMapper.updatesFeedPurchaseQtyReturn(feedPurchaseQtyReturnMapList);
        }

        updatePurchaseStoreStatus(purchaseId, purchaseEventCode);

        // START HANA
        if (isToHana) {
            String mtcCardCode = HanaUtil.getMTC_CardCode(supplierId);

            // 业务伙伴编号
            hanaInputBill.setMTC_CardCode(mtcCardCode);

            if (!hanaInputBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaInputBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaInputBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码：采购入库 - 物料
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2020);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaInputBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(inputModel.getFarmId()));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaInputBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }
        // END HANA

    }

    @Override
    public void editMaterialInputScrap(Map<String, Object> inputMap) throws Exception {
        Long inputId = Maps.getLong(inputMap, "id");

        SqlCon sqlCon = new SqlCon();
        sqlCon.addConditionWithNull(inputId, " AND ROW_ID = ? FOR UPDATE");
        InputModel inputModel = getModel(inputMapper, sqlCon);
        if (inputModel == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "数据异常，请刷新页面！。。。");
        }
        if (!SupplyChianConstants.ARRIVE_INPUT_STATUS_UNCOMMIT.equals(inputModel.getStatus())) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "该入库单不是【未提交】状态,无法作废,请刷新页面，重新操作！。。。");
        }
        inputModel.setStatus(SupplyChianConstants.ARRIVE_INPUT_STATUS_SCRAP);

        SqlCon inputDetailSqlCon = new SqlCon();
        inputDetailSqlCon.addCondition(inputId, " AND INPUT_ID = ?");
        List<InputDetailModel> inputDetailModelList = getList(inputDetailMapper, inputDetailSqlCon);
        for (InputDetailModel inputDetailModel : inputDetailModelList) {
            inputDetailModel.setStatus(SupplyChianConstants.ARRIVE_INPUT_STATUS_SCRAP);
        }

        inputMapper.update(inputModel);

        if (inputDetailModelList != null && !inputDetailModelList.isEmpty()) {
            inputDetailMapper.updates(inputDetailModelList);
        }

    }

    @Override
    public void editSpermInput(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }
        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "开发中。。。");
    }

    @Override
    public List<Map<String, Object>> searchSupplychianBillDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.FARM_ID AS allotFarmId, T1.OUTPUT_QTY AS outputQty, T1.PLAN_QTY AS planQty");
        // , T1.STATUS AS outputStatus
        if (SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addMainSql(", (SELECT ROW_ID FROM CD_M_MATERIAL WHERE DELETED_FLAG = '0'");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" AND RELATED_ID = (SELECT RELATED_ID FROM CD_M_MATERIAL WHERE ROW_ID = T1.MATERIAL_ID)) AS materialId");
        } else {
            sqlCon.addMainSql(", T1.MATERIAL_ID AS materialId");
        }
        sqlCon.addMainSql(", T1.OUTPUT_WAREHOUSE_ID AS outputWarehouseId, T1.OUTPUT_TO_WAREHOUSE_ID AS outputToWarehouseId");
        sqlCon.addMainSql(", T1.MATERIAL_ONLY AS materialOnly, T1.MATERIAL_BATCH AS materialBatch");
        sqlCon.addMainSql(", T1.OUTPUT_DESTINATION AS outputDestination, T1.OUTPUT_FARM_ID AS outputFarmId, T1.OUTPUT_DEPT_ID AS outputDeptId");
        sqlCon.addMainSql(", T1.OUTPUT_HOUSE_ID AS outputHouseId, T1.OUTPUT_SWINERY_ID AS outputSwineryId");
        sqlCon.addMainSql(", (SELECT PRODUCTION_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND WAREHOUSE_ID = T1.OUTPUT_WAREHOUSE_ID LIMIT 1) AS productionDate");
        sqlCon.addMainSql(", (SELECT EFFECTIVE_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND WAREHOUSE_ID = T1.OUTPUT_WAREHOUSE_ID LIMIT 1) AS effectiveDate");
        sqlCon.addMainSql(", (SELECT ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND MATERIAL_ONLY = T1.MATERIAL_ONLY LIMIT 1) AS actualPrice");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT_DETAIL T1 WHERE T1.DELETED_FLAG = '0' AND T1.INPUT_ID IS NULL");
        if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(inputMap, "eventCode"))) {
            sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND T1.STATUS = ?");
            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND (T1.FARM_ID = ?");
                sqlCon.addConditionWithNull(getFarmId(), " OR T1.COMPANY_ID = ?)");

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }
        }

        sqlCon.addCondition(Maps.getLong(inputMap, "outputId"), " AND T1.OUTPUT_ID = ?");
        sqlCon.addMainSql(" ORDER BY T1.OUTPUT_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> supplychianBillDetailList = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> supplychianBillDetail : supplychianBillDetailList) {
            supplychianBillDetail.putAll(CacheUtil.getMaterialInfo(Maps.getString(supplychianBillDetail, "materialId"),
                    MaterialInfoEnum.MATERIAL_INFO));
            supplychianBillDetail.put("materialFirstClassName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "materialFirstClass"),
                    NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
            supplychianBillDetail.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(supplychianBillDetail,
                    "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(supplychianBillDetail, "materialFirstClass")));
            supplychianBillDetail.put("orginWarehouseName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "outputWarehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(supplychianBillDetail, "supplierId"));
            supplychianBillDetail.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            supplychianBillDetail.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            if (SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT.equals(Maps.getString(inputMap, "eventCode"))) {
                if (!Maps.isEmpty(supplychianBillDetail, "outputToWarehouseId")) {
                    supplychianBillDetail.put("warehouseId", Maps.getLong(supplychianBillDetail, "outputToWarehouseId"));
                } else {
                    supplychianBillDetail.put("warehouseId", Maps.getLongClass(supplychianBillDetail, "defaultWarehouse"));
                }
                supplychianBillDetail.put("inputQty", Maps.getDouble(supplychianBillDetail, "outputQty"));
                supplychianBillDetail.put("totalPrice", bigDecimalMultiply(Maps.getDouble(supplychianBillDetail, "actualPrice"), Maps.getDouble(
                        supplychianBillDetail, "outputQty")));
            } else {
                supplychianBillDetail.put("canReturnQty", Maps.getDouble(supplychianBillDetail, "outputQty"));
                supplychianBillDetail.put("outputUseDate", TimeUtil.format(Maps.getString(supplychianBillDetail, "outputUseDate"),
                        TimeUtil.TIME_FORMAT));
                supplychianBillDetail.put("warehouseId", Maps.getLong(supplychianBillDetail, "outputWarehouseId"));
                supplychianBillDetail.put("outputQtyUnit", Maps.getString(supplychianBillDetail, "outputQty") + Maps.getString(supplychianBillDetail,
                        "unit"));
                supplychianBillDetail.put("planQtyUnit", Maps.getString(supplychianBillDetail, "planQty") + Maps.getString(supplychianBillDetail,
                        "unit"));
            }
            supplychianBillDetail.put("inputWarehouseName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "warehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));

            if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(inputMap, "eventCode"))) {
                supplychianBillDetail.put("outputDestinationName", SupplyChianConstants.OUTPUT_DESTINATION_MAP.get(Maps.getString(
                        supplychianBillDetail, "outputDestination")));
                // 领用猪场
                Map<String, String> outputFarmIdNameMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(supplychianBillDetail, "outputFarmId"));
                supplychianBillDetail.put("outputFarmIdName", Maps.getString(outputFarmIdNameMap, "SORT_NAME"));
                // 领用部门
                Map<String, String> outputDeptIdNameMap = CacheUtil.getData(Maps.getLong(supplychianBillDetail, "outputFarmId"), "HR_O_DEPT", Maps
                        .getString(supplychianBillDetail, "outputDeptId"));
                supplychianBillDetail.put("outputDeptIdName", Maps.getString(outputDeptIdNameMap, "DEPT_NAME"));
                // 领用猪舍
                Map<String, String> outputHouseIdNameMap = CacheUtil.getData(Maps.getLong(supplychianBillDetail, "outputFarmId"), "PP_O_HOUSE", Maps
                        .getString(supplychianBillDetail, "outputHouseId"));
                supplychianBillDetail.put("outputHouseIdName", Maps.getString(outputHouseIdNameMap, "HOUSE_NAME"));
                // 领用批次
                Map<String, String> outputSwineryIdNameMap = CacheUtil.getData(Maps.getLong(supplychianBillDetail, "outputFarmId"), "PP_O_HOUSE", Maps
                        .getString(supplychianBillDetail, "outputSwineryId"));
                supplychianBillDetail.put("outputSwineryIdName", Maps.getString(outputSwineryIdNameMap, "SWINERY_NAME"));
            }
        }

        return supplychianBillDetailList;
    }

    @Override
    public void editReturnInput(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "returnDate"));

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_INPUT_OUTPUT;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        InputModel inputModel = getBean(InputModel.class, inputMap);
        inputModel.setStatus(CommonConstants.STATUS);
        inputModel.setFarmId(getFarmId());
        inputModel.setCompanyId(getCompanyId());
        inputModel.setBillCode(billCode);
        inputModel.setBillDate(Maps.getDate(inputMap, "returnDate"));
        inputModel.setEventCode(eventCode);
        inputModel.setInputerId(getEmployeeId());
        inputModel.setOaUsername(getOaUsername());
        inputModel.setCreateId(getEmployeeId());
        inputModel.setCreateDate(currentDate);
        inputModel.setPrintTimes(0L);
        inputMapper.insert(inputModel);

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        HanaMaterialOperateBill hanaMaterialOperateBill = null;
        List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = null;
        String mtcBranchID = null;
        String mtcDeptID = null;
        if (isToHana) {
            hanaMaterialOperateBill = new HanaMaterialOperateBill();

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLong(inputMap, "outputFarmId"));
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            // 分公司编码
            hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaMaterialOperateBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
            // 入库日期
            hanaMaterialOperateBill.setMTC_DocDate(inputModel.getBillDate());
            // 操作人
            hanaMaterialOperateBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(getFarmId(), getEmployeeId()));
            // 表行
            hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

            hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);

        }
        // END HANA

        List<InputDetailModel> insertInputDetailModelList = new ArrayList<InputDetailModel>();
        List<OutputDetailModel> updateOutputDetailModelList = new ArrayList<OutputDetailModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();
        Map<String, StoreMaterialModel> onlyAndBatchMap = new HashMap<String, StoreMaterialModel>();

        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, "warehouseId")) {
                Thrower.throwException(SupplyChianException.WAREHOUSE_IS_NULL, Maps.getLong(inputMap, "lineNumber"));
            }

            // 锁定出库表
            SqlCon outputDetailSqlCon = new SqlCon();
            outputDetailSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            OutputDetailModel updateOutputDetailModel = getList(outputDetailMapper, outputDetailSqlCon).get(0);
            if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "canReturnQty")) != 0) {
                Thrower.throwException(SupplyChianException.QTY_HAS_CHANGED, Maps.getLong(detailMap, "lineNumber"));
            }

            // 锁定库存表
            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(updateOutputDetailModel.getMaterialOnly(), " AND MATERIAL_ONLY = ?");
            storeMaterialSqlCon.addConditionWithNull(updateOutputDetailModel.getMaterialBatch(), " AND MATERIAL_BATCH = ?");
            storeMaterialSqlCon.addConditionWithNull(updateOutputDetailModel.getOutputWarehouseId(), " AND WAREHOUSE_ID = ?");
            storeMaterialSqlCon.addMainSql(" FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);

            InputDetailModel inputDetailModel = new InputDetailModel();
            inputDetailModel.setStatus(CommonConstants.STATUS);
            inputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            inputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            inputDetailModel.setFarmId(getFarmId());
            inputDetailModel.setCompanyId(getCompanyId());
            inputDetailModel.setInputId(inputModel.getRowId());
            inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
            inputDetailModel.setMaterialId(updateOutputDetailModel.getMaterialId());
            inputDetailModel.setMaterialOnly(updateOutputDetailModel.getMaterialOnly());
            inputDetailModel.setMaterialBatch(updateOutputDetailModel.getMaterialBatch());
            inputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
            inputDetailModel.setInputWarehouseId(updateOutputDetailModel.getOutputWarehouseId());
            inputDetailModel.setInputerId(getEmployeeId());
            inputDetailModel.setBillCode(billCode);
            inputDetailModel.setBillDate(Maps.getDate(inputMap, "returnDate"));
            inputDetailModel.setEventCode(eventCode);
            inputDetailModel.setCreateId(getEmployeeId());
            inputDetailModel.setCreateDate(currentDate);
            insertInputDetailModelList.add(inputDetailModel);

            if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "inputQty")) == 0) {
                updateOutputDetailModel.setInputId(inputModel.getRowId());
                updateOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                updateOutputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));

            } else if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "inputQty")) > 0) {
                updateOutputDetailModel.setOutputQty(bigDecimalSubtract(updateOutputDetailModel.getOutputQty(), Maps.getDouble(detailMap,
                        "inputQty")));

                OutputDetailModel insertOutputDetailModel = new OutputDetailModel();
                insertOutputDetailModel.setStatus(updateOutputDetailModel.getStatus());
                insertOutputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                insertOutputDetailModel.setNotes(updateOutputDetailModel.getNotes());
                insertOutputDetailModel.setFarmId(updateOutputDetailModel.getFarmId());
                insertOutputDetailModel.setCompanyId(updateOutputDetailModel.getCompanyId());
                insertOutputDetailModel.setOutputId(updateOutputDetailModel.getOutputId());
                insertOutputDetailModel.setOutputLn(updateOutputDetailModel.getOutputLn());
                insertOutputDetailModel.setInputId(inputModel.getRowId());
                insertOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                insertOutputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
                insertOutputDetailModel.setMaterialId(updateOutputDetailModel.getMaterialId());
                insertOutputDetailModel.setMaterialOnly(updateOutputDetailModel.getMaterialOnly());
                insertOutputDetailModel.setMaterialBatch(updateOutputDetailModel.getMaterialBatch());
                insertOutputDetailModel.setPlanQty(0D);
                insertOutputDetailModel.setOutputQty(Maps.getDouble(detailMap, "inputQty"));
                insertOutputDetailModel.setOutputWarehouseId(updateOutputDetailModel.getOutputWarehouseId());
                insertOutputDetailModel.setOutputerId(updateOutputDetailModel.getOutputerId());
                insertOutputDetailModel.setOutputDestination(updateOutputDetailModel.getOutputDestination());
                insertOutputDetailModel.setOutputFarmId(updateOutputDetailModel.getOutputFarmId());
                insertOutputDetailModel.setOutputSwineryId(updateOutputDetailModel.getOutputSwineryId());
                insertOutputDetailModel.setOutputHouseId(updateOutputDetailModel.getOutputHouseId());
                insertOutputDetailModel.setOutputDeptId(updateOutputDetailModel.getOutputDeptId());
                insertOutputDetailModel.setPigType(updateOutputDetailModel.getPigType());
                insertOutputDetailModel.setOutputUseDate(updateOutputDetailModel.getOutputUseDate());
                insertOutputDetailModel.setUserId(updateOutputDetailModel.getUserId());
                insertOutputDetailModel.setBillCode(updateOutputDetailModel.getBillCode());
                insertOutputDetailModel.setBillDate(updateOutputDetailModel.getBillDate());
                insertOutputDetailModel.setEventCode(updateOutputDetailModel.getEventCode());
                insertOutputDetailModel.setCreateId(getEmployeeId());
                insertOutputDetailModel.setCreateDate(currentDate);
                insertOutputDetailModelList.add(insertOutputDetailModel);

            } else {
                Thrower.throwException(SupplyChianException.INPUT_QTY_CAN_NOT_MORE_THAN_OUTPUT_QTY, Maps.getLong(detailMap, "lineNumber"));
            }

            updateOutputDetailModelList.add(updateOutputDetailModel);

            // 改变库存量
            String onlyAndBatch = updateOutputDetailModel.getMaterialOnly() + updateOutputDetailModel.getMaterialBatch();
            if (onlyAndBatchMap.containsKey(onlyAndBatch)) {
                StoreMaterialModel storeMaterialModel = onlyAndBatchMap.get(onlyAndBatch);
                storeMaterialModel.setActualQty(bigDecimalAdd(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap, "inputQty")));
            } else {
                updateStoreMaterialModel.setActualQty(bigDecimalAdd(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap, "inputQty")));
                onlyAndBatchMap.put(onlyAndBatch, updateStoreMaterialModel);
            }

            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(updateOutputDetailModel.getMaterialId());
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(updateOutputDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                    // 分公司编码
                    hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                    // 猪舍编号
                    hanaMaterialOperateBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(updateOutputDetailModel.getOutputHouseId()));
                    // 新农+单据编号
                    hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(inputDetailModel.getInputId()));
                    // 新农+单据行号
                    hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(inputDetailModel.getInputLn()));
                    // 物料编号
                    hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialOperateBillDetail.setMTC_BatchNum(inputDetailModel.getMaterialBatch());
                    // 仓库编号
                    hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(updateOutputDetailModel.getOutputWarehouseId()));
                    // 退货入库数量
                    hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(inputDetailModel.getInputQty()));
                    // 如果领用的为兽药，默认为A - 商品猪
                    // 核算对象：A - 商品猪 B - 生产猪C - 后备猪
                    if (SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialInfo, "materialFirstClass"))) {
                        // 商品猪
                        hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                    } else {
                        if ("1".equals(updateOutputDetailModel.getPigType())) {
                            // 后备猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_C);
                        } else if ("2".equals(updateOutputDetailModel.getPigType())) {
                            // 生产猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_B);
                        } else {
                            // 商品猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                        }
                    }

                    hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                }
            }
        }

        if (insertInputDetailModelList != null && !insertInputDetailModelList.isEmpty()) {
            inputDetailMapper.inserts(insertInputDetailModelList);
        }

        if (updateOutputDetailModelList != null && !updateOutputDetailModelList.isEmpty()) {
            outputDetailMapper.updates(updateOutputDetailModelList);
        }

        if (insertOutputDetailModelList != null && !insertOutputDetailModelList.isEmpty()) {
            outputDetailMapper.inserts(insertOutputDetailModelList);
        }

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        for (StoreMaterialModel storeMaterialModel : onlyAndBatchMap.values()) {
            updateStoreMaterialModelList.add(storeMaterialModel);
        }

        if (!updateStoreMaterialModelList.isEmpty()) {
            storeMaterialMapper.updates(updateStoreMaterialModelList);
        }

        if (isToHana) {
            if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码：生产领用退料
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
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
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }

    }

    @Override
    public void editTrunOverScrap(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "trunOverScrapDate"));

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_INPUT_RESCRAP;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        InputModel inputModel = getBean(InputModel.class, inputMap);
        inputModel.setStatus(CommonConstants.STATUS);
        inputModel.setFarmId(getFarmId());
        inputModel.setCompanyId(getCompanyId());
        inputModel.setBillCode(billCode);
        inputModel.setBillDate(Maps.getDate(inputMap, "trunOverScrapDate"));
        inputModel.setEventCode(eventCode);
        inputModel.setInputerId(getEmployeeId());
        inputModel.setOaUsername(getOaUsername());
        inputModel.setCreateId(getEmployeeId());
        inputModel.setCreateDate(currentDate);
        inputModel.setPrintTimes(0L);
        inputMapper.insert(inputModel);

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        HanaMaterialOperateBill hanaMaterialOperateBill = null;
        List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = null;
        String mtcBranchID = null;
        String mtcDeptID = null;
        if (isToHana) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            hanaMaterialOperateBill = new HanaMaterialOperateBill();
            // 分公司编码
            hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaMaterialOperateBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
            // 出库日期
            hanaMaterialOperateBill.setMTC_DocDate(inputModel.getBillDate());
            // 反报废人
            hanaMaterialOperateBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(getFarmId(), getEmployeeId()));
            // 表行
            hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

            hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);
        }
        // END HANA

        List<InputDetailModel> insertInputDetailModelList = new ArrayList<InputDetailModel>();
        List<OutputDetailModel> updateOutputDetailModelList = new ArrayList<OutputDetailModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();
        Map<String, StoreMaterialModel> onlyAndBatchMap = new HashMap<String, StoreMaterialModel>();

        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, "warehouseId")) {
                Thrower.throwException(SupplyChianException.WAREHOUSE_IS_NULL, Maps.getLong(inputMap, "lineNumber"));
            }

            // 锁定出库表
            SqlCon outputStoreSqlCon = new SqlCon();
            outputStoreSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            OutputDetailModel updateOutputDetailModel = getList(outputDetailMapper, outputStoreSqlCon).get(0);
            if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "canReturnQty")) != 0) {
                Thrower.throwException(SupplyChianException.QTY_HAS_CHANGED, Maps.getLong(detailMap, "lineNumber"));
            }

            // 锁定库存表
            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(updateOutputDetailModel.getMaterialOnly(), " AND MATERIAL_ONLY = ?");
            storeMaterialSqlCon.addConditionWithNull(updateOutputDetailModel.getMaterialBatch(), " AND MATERIAL_BATCH = ?");
            storeMaterialSqlCon.addConditionWithNull(updateOutputDetailModel.getOutputWarehouseId(), " AND WAREHOUSE_ID = ?");
            storeMaterialSqlCon.addMainSql(" FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);

            // 插入入库表
            InputDetailModel inputDetailModel = new InputDetailModel();
            inputDetailModel.setStatus(CommonConstants.STATUS);
            inputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            inputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            inputDetailModel.setFarmId(getFarmId());
            inputDetailModel.setCompanyId(getCompanyId());
            inputDetailModel.setInputId(inputModel.getRowId());
            inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
            inputDetailModel.setMaterialId(updateOutputDetailModel.getMaterialId());
            inputDetailModel.setMaterialOnly(updateOutputDetailModel.getMaterialOnly());
            inputDetailModel.setMaterialBatch(updateOutputDetailModel.getMaterialBatch());
            inputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
            inputDetailModel.setInputWarehouseId(updateOutputDetailModel.getOutputWarehouseId());
            inputDetailModel.setInputerId(getEmployeeId());
            inputDetailModel.setBillCode(billCode);
            inputDetailModel.setBillDate(Maps.getDate(inputMap, "trunOverScrapDate"));
            inputDetailModel.setEventCode(eventCode);
            inputDetailModel.setCreateId(getEmployeeId());
            inputDetailModel.setCreateDate(currentDate);
            insertInputDetailModelList.add(inputDetailModel);

            // 更新出库表
            if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "inputQty")) == 0) {
                updateOutputDetailModel.setInputId(inputModel.getRowId());
                updateOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                updateOutputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));

            } else if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "inputQty")) > 0) {
                updateOutputDetailModel.setPlanQty(bigDecimalSubtract(updateOutputDetailModel.getPlanQty(), Maps.getDouble(detailMap, "inputQty")));
                updateOutputDetailModel.setOutputQty(bigDecimalSubtract(updateOutputDetailModel.getOutputQty(), Maps.getDouble(detailMap,
                        "inputQty")));

                OutputDetailModel insertOutputDetailModel = new OutputDetailModel();
                insertOutputDetailModel.setStatus(updateOutputDetailModel.getStatus());
                insertOutputDetailModel.setDeletedFlag("0");
                insertOutputDetailModel.setNotes(updateOutputDetailModel.getNotes());
                insertOutputDetailModel.setFarmId(updateOutputDetailModel.getFarmId());
                insertOutputDetailModel.setCompanyId(updateOutputDetailModel.getCompanyId());
                insertOutputDetailModel.setOutputId(updateOutputDetailModel.getOutputId());
                insertOutputDetailModel.setOutputLn(updateOutputDetailModel.getOutputLn());
                insertOutputDetailModel.setInputId(inputModel.getRowId());
                insertOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                insertOutputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
                insertOutputDetailModel.setMaterialId(updateOutputDetailModel.getMaterialId());
                insertOutputDetailModel.setMaterialOnly(updateOutputDetailModel.getMaterialOnly());
                insertOutputDetailModel.setMaterialBatch(updateOutputDetailModel.getMaterialBatch());
                insertOutputDetailModel.setPlanQty(Maps.getDouble(detailMap, "inputQty"));
                insertOutputDetailModel.setOutputQty(Maps.getDouble(detailMap, "inputQty"));
                insertOutputDetailModel.setOutputWarehouseId(updateOutputDetailModel.getOutputWarehouseId());
                insertOutputDetailModel.setOutputerId(updateOutputDetailModel.getOutputerId());
                insertOutputDetailModel.setOutputDestination(updateOutputDetailModel.getOutputDestination());
                insertOutputDetailModel.setUserId(updateOutputDetailModel.getUserId());
                insertOutputDetailModel.setCreateId(getEmployeeId());
                insertOutputDetailModel.setCreateDate(currentDate);
                insertOutputDetailModelList.add(insertOutputDetailModel);

            } else {
                Thrower.throwException(SupplyChianException.INPUT_QTY_CAN_NOT_MORE_THAN_OUTPUT_QTY, Maps.getLong(detailMap, "lineNumber"));
            }

            updateOutputDetailModelList.add(updateOutputDetailModel);

            // 改变库存量
            String onlyAndBatch = updateOutputDetailModel.getMaterialOnly() + updateOutputDetailModel.getMaterialBatch();
            if (onlyAndBatchMap.containsKey(onlyAndBatch)) {
                StoreMaterialModel storeMaterialModel = onlyAndBatchMap.get(onlyAndBatch);
                storeMaterialModel.setActualQty(bigDecimalAdd(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap, "inputQty")));
            } else {
                updateStoreMaterialModel.setActualQty(bigDecimalAdd(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap, "inputQty")));
                onlyAndBatchMap.put(onlyAndBatch, updateStoreMaterialModel);
            }

            // START HANA
            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(inputDetailModel.getMaterialId());
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(inputDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                    // 分公司编码
                    hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号
                    hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(inputDetailModel.getInputId()));
                    // 新农+单据行号
                    hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(inputDetailModel.getInputLn()));
                    // 物料编号
                    hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialOperateBillDetail.setMTC_BatchNum(inputDetailModel.getMaterialBatch());
                    // 仓库编号
                    hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(inputDetailModel.getInputWarehouseId()));
                    // 入库数量
                    hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(inputDetailModel.getInputQty()));

                    hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                }
            }
            // END HANA

        }

        if (insertInputDetailModelList != null && !insertInputDetailModelList.isEmpty()) {
            inputDetailMapper.inserts(insertInputDetailModelList);
        }

        if (updateOutputDetailModelList != null && !updateOutputDetailModelList.isEmpty()) {
            outputDetailMapper.updates(updateOutputDetailModelList);
        }

        if (insertOutputDetailModelList != null && !insertOutputDetailModelList.isEmpty()) {
            outputDetailMapper.inserts(insertOutputDetailModelList);
        }

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        for (StoreMaterialModel storeMaterialModel : onlyAndBatchMap.values()) {
            updateStoreMaterialModelList.add(storeMaterialModel);
        }

        if (!updateStoreMaterialModelList.isEmpty()) {
            storeMaterialMapper.updates(updateStoreMaterialModelList);
        }

        // START HANA
        if (isToHana) {
            if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码:反报废
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2070);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
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
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }
        // END HANA

    }

    @Override
    public void editAllotInput(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "billDate"));

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_INPUT_ALLOT;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        InputModel inputModel = getBean(InputModel.class, inputMap);
        inputModel.setStatus(CommonConstants.STATUS);
        inputModel.setFarmId(getFarmId());
        inputModel.setCompanyId(getCompanyId());
        inputModel.setBillCode(billCode);
        inputModel.setEventCode(eventCode);
        inputModel.setInputerId(getEmployeeId());
        inputModel.setOaUsername(getOaUsername());
        inputModel.setCreateId(getEmployeeId());
        inputModel.setCreateDate(currentDate);
        inputModel.setPrintTimes(0L);
        inputMapper.insert(inputModel);

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        HanaMaterialAllotBill hanaMaterialAllotBill = null;
        List<HanaMaterialAllotBillDetail> hanaMaterialAllotBillDetailList = null;
        String mtcBranchID = null;
        String mtcFromWhsCode = null;
        String mtcToWhsCode = null;
        if (isToHana) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

            hanaMaterialAllotBill = new HanaMaterialAllotBill();
            // 分公司编码
            hanaMaterialAllotBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
            hanaMaterialAllotBill.setMTC_BaseEntry(mtcBranchID + "-" + inputModel.getRowId() + "-" + inputModel.getBillCode());
            // 调拨日期
            hanaMaterialAllotBill.setMTC_DocDate(inputModel.getBillDate());
            // 调拨人
            hanaMaterialAllotBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(getFarmId(), getEmployeeId()));
            // 表行
            hanaMaterialAllotBillDetailList = new ArrayList<HanaMaterialAllotBillDetail>();

            hanaMaterialAllotBill.setDetailList(hanaMaterialAllotBillDetailList);

        }
        // END HANA

        List<OutputDetailModel> updateOutputDetailModelList = new ArrayList<OutputDetailModel>();
        List<InputDetailModel> inputDetailModelList = new ArrayList<InputDetailModel>();
        // List<MonthAccountModel> insertMonthAccountModelList = new ArrayList<MonthAccountModel>();

        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, "warehouseId")) {
                Thrower.throwException(SupplyChianException.WAREHOUSE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
            }

            SqlCon outputDetailSqlCon = new SqlCon();
            outputDetailSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            OutputDetailModel updateOutputDetailModel = getList(outputDetailMapper, outputDetailSqlCon).get(0);
            if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "inputQty")) != 0) {
                Thrower.throwException(SupplyChianException.QTY_HAS_CHANGED, Maps.getLong(detailMap, "lineNumber"));
            }

            if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "inputQty")) == 0) {
                updateOutputDetailModel.setInputId(inputModel.getRowId());
                updateOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                updateOutputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
            } else {
                Thrower.throwException(SupplyChianException.ALLOT_INPUT_QTY_CAN_NOT_MORE_THAN_OUTPUT_QTY, Maps.getLong(detailMap, "lineNumber"));
            }

            updateOutputDetailModelList.add(updateOutputDetailModel);

            Map<String, Object> materialBatchMap = getMaterialBatchNumber(updateOutputDetailModel.getMaterialOnly(), updateOutputDetailModel
                    .getMaterialBatch(), Maps.getLong(detailMap, "warehouseId"));

            String materialBatch = updateOutputDetailModel.getMaterialBatch();

            if (Maps.isEmpty(materialBatchMap, "rowId")) {
                StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                storeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                storeMaterialModel.setFarmId(getFarmId());
                storeMaterialModel.setCompanyId(getCompanyId());
                storeMaterialModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                storeMaterialModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
                storeMaterialModel.setMaterialBatch(materialBatch);
                storeMaterialModel.setProductionDate(Maps.getDate(detailMap, "productionDate"));
                storeMaterialModel.setEffectiveDate(Maps.getDate(detailMap, "effectiveDate"));
                storeMaterialModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                storeMaterialModel.setActualQty(Maps.getDouble(detailMap, "inputQty"));
                storeMaterialModel.setCreateDate(currentDate);
                storeMaterialMapper.insert(storeMaterialModel);

            } else {
                SqlCon storeMaterialSqlCon = new SqlCon();
                storeMaterialSqlCon.addConditionWithNull(Maps.getLong(materialBatchMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
                StoreMaterialModel storeMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
                storeMaterialModel.setActualQty(bigDecimalAdd(storeMaterialModel.getActualQty(), Maps.getDouble(detailMap, "inputQty")));
                storeMaterialMapper.update(storeMaterialModel);

            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Maps.getDate(inputMap, "billDate"));
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);

            SqlCon monthAccountExistsSqlCon = new SqlCon();
            monthAccountExistsSqlCon.addMainSql("SELECT ROW_ID AS rowId FROM SC_M_MONTH_ACCOUNT");
            monthAccountExistsSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            monthAccountExistsSqlCon.addCondition(Maps.getLong(detailMap, "materialId"), " AND MATERIAL_ID = ?");
            monthAccountExistsSqlCon.addCondition(Maps.getString(detailMap, "materialOnly"), " AND MATERIAL_ONLY = ?");
            monthAccountExistsSqlCon.addCondition(materialBatch, " AND MATERIAL_BATCH = ?");
            monthAccountExistsSqlCon.addCondition(startDateString, " AND START_DATE = ?");
            monthAccountExistsSqlCon.addCondition(Maps.getLong(detailMap, "warehouseId"), " AND WAREHOUSE_ID = ?");
            monthAccountExistsSqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ? LIMIT 1");

            List<MonthAccountModel> monthAccountExistsList = setSql(monthAccountMapper, monthAccountExistsSqlCon);

            if (monthAccountExistsList.isEmpty()) {
                MonthAccountModel monthAccountModel = new MonthAccountModel();
                monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                monthAccountModel.setFarmId(getFarmId());
                monthAccountModel.setCompanyId(getCompanyId());
                monthAccountModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                monthAccountModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
                monthAccountModel.setMaterialBatch(materialBatch);
                monthAccountModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                monthAccountModel.setStartQty(0D);
                monthAccountModel.setStartDate(startDate);
                monthAccountModel.setLockFlag("N");
                monthAccountModel.setEventCode(eventCode);
                monthAccountModel.setCreateId(getEmployeeId());
                monthAccountModel.setCreateDate(currentDate);
                monthAccountMapper.insert(monthAccountModel);
                // insertMonthAccountModelList.add(monthAccountModel);
            }

            InputDetailModel inputDetailModel = new InputDetailModel();
            inputDetailModel.setStatus(CommonConstants.STATUS);
            inputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            inputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            inputDetailModel.setFarmId(getFarmId());
            inputDetailModel.setCompanyId(getCompanyId());
            inputDetailModel.setInputId(inputModel.getRowId());
            inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
            inputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            inputDetailModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
            inputDetailModel.setMaterialBatch(materialBatch);
            inputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
            inputDetailModel.setInputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
            inputDetailModel.setInputerId(getEmployeeId());
            inputDetailModel.setBillCode(billCode);
            inputDetailModel.setBillDate(Maps.getDate(inputMap, "billDate"));
            inputDetailModel.setEventCode(eventCode);
            inputDetailModel.setCreateId(getEmployeeId());
            inputDetailModel.setCreateDate(currentDate);
            inputDetailModelList.add(inputDetailModel);

            // START HANA
            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(inputDetailModel.getMaterialId());
                // if (StringUtil.isNonBlank(mtcItemCode)) {
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(inputDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialAllotBillDetail hanaMaterialAllotBillDetail = new HanaMaterialAllotBillDetail();
                    // 分公司编码
                    hanaMaterialAllotBillDetail.setMTC_BranchID(mtcBranchID);
                    // 新农+单据编号
                    hanaMaterialAllotBillDetail.setMTC_BaseEntry(String.valueOf(inputDetailModel.getInputId()));
                    // 新农+单据行号
                    hanaMaterialAllotBillDetail.setMTC_BaseLine(String.valueOf(inputDetailModel.getInputLn()));
                    // 物料编号
                    hanaMaterialAllotBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialAllotBillDetail.setMTC_BatchNum(inputDetailModel.getMaterialBatch());
                    // 调出仓库编号
                    hanaMaterialAllotBillDetail.setMTC_FromWhsCode(String.valueOf(updateOutputDetailModel.getOutputWarehouseId()));
                    // 调入仓库编号
                    hanaMaterialAllotBillDetail.setMTC_ToWhsCode(String.valueOf(inputDetailModel.getInputWarehouseId()));
                    // 调拨数量
                    hanaMaterialAllotBillDetail.setMTC_Qty(String.valueOf(inputDetailModel.getInputQty()));

                    hanaMaterialAllotBillDetailList.add(hanaMaterialAllotBillDetail);

                    if (mtcFromWhsCode == null) {
                        mtcFromWhsCode = String.valueOf(updateOutputDetailModel.getOutputWarehouseId());
                    }

                    if (mtcToWhsCode == null) {
                        mtcToWhsCode = String.valueOf(inputDetailModel.getInputWarehouseId());
                    }
                }
                // }
            }
            // END HANA

        }

        // START HANA
        if (isToHana) {
            // 从明细中取第一条调出仓库和调入仓库
            // 调出仓库
            hanaMaterialAllotBill.setMTC_FromWhsCode(mtcFromWhsCode);
            // 调入仓库
            hanaMaterialAllotBill.setMTC_ToWhsCode(mtcToWhsCode);
        }
        // END HANA

        inputDetailMapper.inserts(inputDetailModelList);

        // if (insertMonthAccountModelList != null && !insertMonthAccountModelList.isEmpty()) {
        // monthAccountMapper.inserts(insertMonthAccountModelList);
        // }

        if (updateOutputDetailModelList != null && !updateOutputDetailModelList.isEmpty()) {
            outputDetailMapper.updates(updateOutputDetailModelList);
        }

        // 修改OUTPUT_ALLOT为入库完成状态
        OutputModel outputAllotBill = new OutputModel();
        outputAllotBill.setRowId(Maps.getLong(inputMap, "allotId"));
        outputAllotBill.setStatus(SupplyChianConstants.OUTPUT_ALLOT_STATUS_COMPLETED);
        outputMapper.update(outputAllotBill);

        // START HANA
        if (isToHana) {
            if (!hanaMaterialAllotBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialAllotBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码：库存调拨
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2090);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialAllotBill.getMTC_BaseEntry());
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
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialAllotBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }
        // END HANA

    }

    @Override
    public void editNotBillInput(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "notBillInputDate"));

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_INPUT_NOT_BILL;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        InputModel inputModel = getBean(InputModel.class, inputMap);
        inputModel.setStatus(CommonConstants.STATUS);
        inputModel.setFarmId(getFarmId());
        inputModel.setCompanyId(getCompanyId());
        inputModel.setBillCode(billCode);
        inputModel.setBillDate(Maps.getDate(inputMap, "notBillInputDate"));
        inputModel.setEventCode(eventCode);
        inputModel.setInputerId(getEmployeeId());
        inputModel.setOaUsername(getOaUsername());
        inputModel.setCreateId(getEmployeeId());
        inputModel.setCreateDate(currentDate);
        inputModel.setPrintTimes(0L);
        inputMapper.insert(inputModel);

        List<InputDetailModel> inputDetailModelList = new ArrayList<InputDetailModel>();
        List<PurchaseDetailModel> insertPurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
        List<StoreMaterialModel> insertStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<MonthAccountModel> insertMonthAccountModelList = new ArrayList<MonthAccountModel>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Maps.getDate(inputMap, "notBillInputDate"));
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, "productionDate") && Maps.isEmpty(detailMap, "effectiveDate")) {
                Thrower.throwException(SupplyChianException.EFFECTIVE_DATE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
            }
            // 做到全平台共用，所以选用10000账套
            String materialOnly = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_ONLY_NUMBER, getEmployeeId(), get10000CompanyId(),
                    get10000FarmId());
            String materialBatch = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_BATCH_NUMBER, getEmployeeId(), get10000CompanyId(),
                    get10000FarmId());

            InputDetailModel inputDetailModel = new InputDetailModel();
            inputDetailModel.setStatus("1");
            inputDetailModel.setDeletedFlag("0");
            inputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            inputDetailModel.setFarmId(getFarmId());
            inputDetailModel.setCompanyId(getCompanyId());
            inputDetailModel.setInputId(inputModel.getRowId());
            inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
            inputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            inputDetailModel.setMaterialOnly(materialOnly);
            inputDetailModel.setMaterialBatch(materialBatch);
            inputDetailModel.setInputQty(Maps.getDouble(detailMap, "inputQty"));
            inputDetailModel.setInputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
            inputDetailModel.setInputerId(getEmployeeId());
            inputDetailModel.setBillCode(billCode);
            inputDetailModel.setBillDate(Maps.getDate(inputMap, "notBillInputDate"));
            inputDetailModel.setEventCode(eventCode);
            inputDetailModel.setCreateId(getEmployeeId());
            inputDetailModel.setCreateDate(currentDate);
            inputDetailModelList.add(inputDetailModel);

            PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
            purchaseDetailModel.setStatus(CommonConstants.STATUS);
            purchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            purchaseDetailModel.setFarmId(getFarmId());
            purchaseDetailModel.setCompanyId(getCompanyId());
            purchaseDetailModel.setFreeLn(0L);
            purchaseDetailModel.setInputId(inputModel.getRowId());
            purchaseDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
            purchaseDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            purchaseDetailModel.setMaterialOnly(materialOnly);
            purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
            purchaseDetailModel.setActualPrice(Maps.getDouble(detailMap, "initActualPrice", 0D));
            purchaseDetailModel.setPassQty(Maps.getDouble(detailMap, "inputQty"));
            purchaseDetailModel.setPurchaseQty(Maps.getDouble(detailMap, "inputQty"));
            purchaseDetailModel.setIsPackage("N");
            purchaseDetailModel.setArriveQty(Maps.getDouble(detailMap, "inputQty"));
            purchaseDetailModel.setGroupOrSelf(SupplyChianConstants.GROUP_OR_SELF_SELF);
            purchaseDetailModel.setSupplierId(Maps.getLong(detailMap, "supplierId"));
            purchaseDetailModel.setRequireFarm(getFarmId());
            purchaseDetailModel.setCreateId(getEmployeeId());
            purchaseDetailModel.setCreateDate(currentDate);
            insertPurchaseDetailModelList.add(purchaseDetailModel);

            StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
            storeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            storeMaterialModel.setFarmId(getFarmId());
            storeMaterialModel.setCompanyId(getCompanyId());
            storeMaterialModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            storeMaterialModel.setMaterialOnly(materialOnly);
            storeMaterialModel.setMaterialBatch(materialBatch);
            storeMaterialModel.setProductionDate(Maps.getDate(detailMap, "productionDate"));
            storeMaterialModel.setEffectiveDate(Maps.getDate(detailMap, "effectiveDate"));
            storeMaterialModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
            storeMaterialModel.setActualQty(Maps.getDouble(detailMap, "inputQty"));
            storeMaterialModel.setCreateDate(currentDate);
            insertStoreMaterialModelList.add(storeMaterialModel);

            MonthAccountModel monthAccountModel = new MonthAccountModel();
            monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            monthAccountModel.setFarmId(getFarmId());
            monthAccountModel.setCompanyId(getCompanyId());
            monthAccountModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            monthAccountModel.setMaterialOnly(materialOnly);
            monthAccountModel.setMaterialBatch(materialBatch);
            monthAccountModel.setWarehouseId(Maps.getLong(detailMap, "warehouseId"));
            monthAccountModel.setStartQty(0D);
            monthAccountModel.setStartDate(calendar.getTime());
            monthAccountModel.setLockFlag("N");
            monthAccountModel.setEventCode(eventCode);
            monthAccountModel.setCreateId(getEmployeeId());
            monthAccountModel.setCreateDate(currentDate);
            insertMonthAccountModelList.add(monthAccountModel);
        }

        inputDetailMapper.inserts(inputDetailModelList);

        purchaseDetailMapper.inserts(insertPurchaseDetailModelList);

        storeMaterialMapper.inserts(insertStoreMaterialModelList);

        monthAccountMapper.inserts(insertMonthAccountModelList);
    }
    /* END 入库单 */

    /* START 物料领用 控件URL */
    @Override
    public List<Map<String, Object>> searchHouseToList(Map<String, Object> inputMap) throws Exception {
        List<Map<String, Object>> list = null;

        String searchDateString = Maps.getString(inputMap, "searchDate");
        Date searchDate = TimeUtil.parseDate(searchDateString);
        Date currentDate = new Date();

        if (searchDate.after(currentDate)) {
            // 猪只数量
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID rowId,COUNT(*) notes FROM pp_l_pig T0 WHERE ");
            sqlCon.addMainSql(" T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND (T0.PIG_CLASS <=18 OR T0.PIG_CLASS = 24)");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID ");

            Map<String, String> sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list1 = paramMapper.getObjectInfos(sqlMap);

            // 猪舍容量
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID rowId,SUM(IFNULL(PIG_NUM,0)) notes FROM pp_o_pigpen T0 ");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1' ");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID");

            sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list2 = paramMapper.getObjectInfos(sqlMap);

            // 生产猪数量
            sqlCon = new SqlCon();
            // 生产公猪
            sqlCon.addMainSql(" SELECT A.rowId, COUNT(A.notes) AS notes FROM (");
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID AS rowId, 1 AS notes FROM pp_l_pig T0 WHERE");
            sqlCon.addMainSql(" T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS = 2");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" UNION ALL");
            // 生产母猪
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID AS rowId, 1 AS notes FROM pp_l_pig T0 WHERE");
            sqlCon.addMainSql(" T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS BETWEEN 5 AND 11");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" UNION ALL");
            // 淘汰生产猪
            sqlCon.addMainSql(" SELECT T1.HOUSE_ID AS rowId, 1 AS pigQty FROM PP_L_PIG T1");
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2");
            sqlCon.addMainSql(" ON ( T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID AND T2.PIG_CLASS_OUT = T1.PIG_CLASS");
            sqlCon.addMainSql(" AND ( T2.PIG_CLASS = 2 OR T2.PIG_CLASS BETWEEN 5 AND 11 ) )");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.PIG_CLASS = 24");
            sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID =?");
            sqlCon.addMainSql(" ) A GROUP BY A.rowId");

            sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list3 = paramMapper.getObjectInfos(sqlMap);

            // 生产猪数量
            sqlCon = new SqlCon();
            // 后备公猪
            sqlCon.addMainSql(" SELECT A.rowId, COUNT(A.notes) AS notes FROM (");
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID AS rowId, 1 AS notes FROM pp_l_pig T0 WHERE");
            sqlCon.addMainSql(" T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS = 1");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" UNION ALL");
            // 后备母猪
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID AS rowId, 1 AS notes FROM pp_l_pig T0 WHERE");
            sqlCon.addMainSql(" T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS BETWEEN 3 AND 4");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" UNION ALL");
            // 淘汰后备猪
            sqlCon.addMainSql(" SELECT T1.HOUSE_ID AS rowId, 1 AS pigQty FROM PP_L_PIG T1");
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2");
            sqlCon.addMainSql(" ON ( T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID AND T2.PIG_CLASS_OUT = T1.PIG_CLASS");
            sqlCon.addMainSql(" AND ( T2.PIG_CLASS = 1 OR T2.PIG_CLASS BETWEEN 3 AND 4 ) )");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.PIG_CLASS = 24");
            sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID =?");
            sqlCon.addMainSql(" ) A GROUP BY A.rowId");

            sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list31 = paramMapper.getObjectInfos(sqlMap);

            // 仔猪数量
            sqlCon = new SqlCon();
            sqlCon.addMainSql(" SELECT T0.HOUSE_ID rowId,COUNT(*) notes FROM pp_l_pig T0 WHERE ");
            sqlCon.addMainSql(" T0.DELETED_FLAG = '0' AND T0.STATUS = '1' AND T0.PIG_CLASS <=18 AND PIG_TYPE = '3'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
            sqlCon.addMainSql(" GROUP BY T0.HOUSE_ID ");

            sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list4 = paramMapper.getObjectInfos(sqlMap);

            sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT ROW_ID AS rowId, BUSINESS_CODE AS businessCode, HOUSE_NAME AS houseName, HOUSE_TYPE AS houseType");
            sqlCon.addMainSql(" FROM PP_O_HOUSE");
            sqlCon.addMainSql(" WHERE DELETED_FLAG='0' AND STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            // 由部门查出管理的猪舍
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM HR_R_DEPT_HOUSE WHERE DELETED_FLAG = '0' AND HOUSE_ID = PP_O_HOUSE.ROW_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "deptId"), " AND DEPT_ID = ?");
            sqlCon.addMainSql(" LIMIT 1)");
            sqlCon.addMainSql(" ORDER BY HOUSE_NAME");

            sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            list = paramMapper.getObjectInfos(sqlMap);

            for (Map<String, Object> model : list) {
                model.put("houseTypeName", CacheUtil.getName(Maps.getString(model, "houseType"), NameEnum.HOUSE_TYPE_NAME));

                // 默认为0
                model.put("pigQty", 0);

                for (int i = 0; i < list1.size(); i++) {
                    Map<String, Object> model1 = list1.get(i);
                    if (Maps.getString(model, "rowId").equals(Maps.getString(model1, "rowId"))) {
                        model.put("pigQty", Maps.getLong(model1, "notes"));
                        list1.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < list2.size(); i++) {
                    Map<String, Object> model2 = list2.get(i);
                    if (Maps.getString(model, "rowId").equals(Maps.getString(model2, "rowId"))) {
                        model.put("houseVolume", Maps.getLong(model2, "notes"));
                        list2.remove(i);
                        i--;
                    }
                }
                // 默认为0
                model.put("houseProductPigQty", 0);

                for (int i = 0; i < list3.size(); i++) {
                    Map<String, Object> model3 = list3.get(i);
                    if (Maps.getString(model, "rowId").equals(Maps.getString(model3, "rowId"))) {
                        model.put("houseProductPigQty", Maps.getLong(model3, "notes"));
                        list3.remove(i);
                        i--;
                    }
                }
                // 默认为0
                model.put("houseBackUpPigQty", 0);

                for (int i = 0; i < list31.size(); i++) {
                    Map<String, Object> model31 = list31.get(i);
                    if (Maps.getString(model, "rowId").equals(Maps.getString(model31, "rowId"))) {
                        model.put("houseBackUpPigQty", Maps.getLong(model31, "notes"));
                        list31.remove(i);
                        i--;
                    }
                }
                // 默认为0
                model.put("houseChildPigQty", 0);

                for (int i = 0; i < list4.size(); i++) {
                    Map<String, Object> model4 = list4.get(i);
                    if (Maps.getString(model, "rowId").equals(Maps.getString(model4, "rowId"))) {
                        model.put("houseChildPigQty", Maps.getLong(model4, "notes"));
                        list4.remove(i);
                        i--;
                    }
                }
            }
        } else {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T0.ROW_ID AS rowId,T0.HOUSE_NAME AS houseName,T0.HOUSE_TYPE AS houseType");
            sqlCon.addMainSql(",T0.BUSINESS_CODE AS businessCode, T2.PIG_NUM AS houseVolume,T1.HOUSE_TYPE_NAME AS houseTypeName");
            // 猪只总数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS BETWEEN 1 AND 18,1,0)) pigQty");
            // 生产猪数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS IN(2,5,6,7,8,9,10,11),1,0)) houseProductPigQty");
            // 后备猪数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS IN(1,3,4),1,0)) houseBackUpPigQty");
            // 肉猪数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS IN(12,13,14,15,16,17,18),1,0)) houseChildPigQty");
            sqlCon.addMainSql(" FROM PP_O_HOUSE T0");
            sqlCon.addMainSql(" LEFT JOIN CD_L_PIG_HOUSE T1");
            sqlCon.addMainSql(" ON(T0.HOUSE_TYPE = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.ROW_ID BETWEEN 1 AND 9)");
            sqlCon.addMainSql(" LEFT JOIN (SELECT T.HOUSE_ID,SUM(T.PIG_NUM) PIG_NUM FROM pp_o_pigpen T");
            sqlCon.addMainSql(" WHERE T.DELETED_FLAG = '0'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T.FARM_ID = ?");
            sqlCon.addMainSql(" GROUP BY T.HOUSE_ID) T2");
            sqlCon.addMainSql(" ON (T0.ROW_ID = T2.HOUSE_ID)");
            sqlCon.addMainSql(" LEFT JOIN(");
            sqlCon.addMainSql(" SELECT T0.PIG_ID, T0.HOUSE_ID");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.CHANGE_HOUSE_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T0");
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG T1");
            sqlCon.addMainSql(" ON (T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.ROW_ID AND T1.DELETED_FLAG ='0' AND T1.ORIGIN <> 3)");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.CHANGE_HOUSE_DATE <= DATE_SUB(?,INTERVAL 1 DAY)) T3");
            sqlCon.addMainSql(" ON (T0.ROW_ID = T3.HOUSE_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T3.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" LEFT JOIN (");
            sqlCon.addMainSql(" SELECT T0.PIG_ID,");
            sqlCon.addMainSql(" IF(T0.PIG_CLASS=24");
            sqlCon.addMainSql(" ,(SELECT PIG_CLASS FROM PP_L_BILL_TOWORK WHERE DELETED_FLAG = '0' AND PIG_CLASS_OUT = 24");
            sqlCon.addMainSql(" AND PIG_ID = T0.PIG_ID LIMIT 1)");
            sqlCon.addMainSql(" ,T0.PIG_CLASS) AS PIG_CLASS");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.TOWORK_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T0");
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_LEAVE T1");
            sqlCon.addMainSql(" ON (T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.PIG_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1')");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.TOWORK_DATE <= DATE_SUB(?,INTERVAL 1 DAY)) T4");
            sqlCon.addMainSql(" ON (T3.PIG_ID = T4.PIG_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T4.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            // 由部门查出管理的猪舍
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM HR_R_DEPT_HOUSE WHERE DELETED_FLAG = '0' AND HOUSE_ID = T0.ROW_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "deptId"), " AND DEPT_ID = ?");
            sqlCon.addMainSql(" LIMIT 1)");
            sqlCon.addMainSql(" GROUP BY T0.ROW_ID");
            sqlCon.addMainSql(" ORDER BY T0.HOUSE_NAME");

            Map<String, String> sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            list = paramMapper.getObjectInfos(sqlMap);

        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchHouseHavePigToList(Map<String, Object> inputMap) throws Exception {

        String searchDateString = Maps.getString(inputMap, "searchDate");
        Date searchDate = TimeUtil.parseDate(searchDateString);
        Date currentDate = new Date();

        SqlCon sqlCon = new SqlCon();

        if (searchDate.after(currentDate)) {
            sqlCon.addMainSql("SELECT T1.houseId, T1.housePigQty");
            sqlCon.addMainSql(", IFNULL(T2.houseProductPigQty,0) AS houseProductPigQty");
            sqlCon.addMainSql(", IFNULL(T3.houseChildPigQty,0) AS houseChildPigQty");
            sqlCon.addMainSql(", IFNULL(T21.houseBackUpPigQty,0) AS houseBackUpPigQty");
            sqlCon.addMainSql(", T4.HOUSE_TYPE AS houseType");
            sqlCon.addMainSql(", T4.HOUSE_NAME AS houseIdName");
            sqlCon.addMainSql(" FROM (SELECT HOUSE_ID AS houseId, COUNT(1) AS housePigQty FROM PP_L_PIG");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS <= 18");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" GROUP BY HOUSE_ID) T1");

            sqlCon.addMainSql(" LEFT OUTER JOIN (");
            // 生产猪
            sqlCon.addMainSql(" SELECT A.houseId, COUNT(A.pigQty) AS houseProductPigQty");
            sqlCon.addMainSql(" FROM (");
            // 生产公猪
            sqlCon.addMainSql(" SELECT HOUSE_ID AS houseId, 1 AS pigQty FROM PP_L_PIG");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS = 2");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" UNION ALL");
            // 生产母猪
            sqlCon.addMainSql(" SELECT HOUSE_ID AS houseId, 1 AS pigQty FROM PP_L_PIG");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS BETWEEN 5 AND 11");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" UNION ALL");
            // 淘汰生产猪
            sqlCon.addMainSql(" SELECT T1.HOUSE_ID AS houseId, 1 AS pigQty FROM PP_L_PIG T1");
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2");
            sqlCon.addMainSql(" ON ( T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID AND T2.PIG_CLASS_OUT = T1.PIG_CLASS");
            sqlCon.addMainSql(" AND ( T2.PIG_CLASS = 2 OR T2.PIG_CLASS BETWEEN 5 AND 11 ) )");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.PIG_CLASS = 24");
            sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
            sqlCon.addMainSql(" ) A GROUP BY A.houseId");
            sqlCon.addMainSql(" ) T2");
            sqlCon.addMainSql(" ON (T1.houseId = T2.houseId)");
            sqlCon.addMainSql(" LEFT OUTER JOIN (");
            // 后备猪
            sqlCon.addMainSql(" SELECT A.houseId, COUNT(A.pigQty) AS houseBackUpPigQty");
            sqlCon.addMainSql(" FROM (");
            // 后备公猪
            sqlCon.addMainSql(" SELECT HOUSE_ID AS houseId, 1 AS pigQty FROM PP_L_PIG");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS = 1");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" UNION ALL");
            // 后备母猪
            sqlCon.addMainSql(" SELECT HOUSE_ID AS houseId, 1 AS pigQty FROM PP_L_PIG");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS BETWEEN 3 AND 4");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" UNION ALL");
            // 淘汰后备猪
            sqlCon.addMainSql(" SELECT T1.HOUSE_ID AS houseId, 1 AS pigQty FROM PP_L_PIG T1");
            sqlCon.addMainSql(" INNER JOIN PP_L_BILL_TOWORK T2");
            sqlCon.addMainSql(" ON ( T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.PIG_ID AND T2.PIG_CLASS_OUT = T1.PIG_CLASS");
            sqlCon.addMainSql(" AND ( T2.PIG_CLASS = 1 OR T2.PIG_CLASS BETWEEN 3 AND 4 ) )");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND T1.PIG_CLASS = 24");
            sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
            sqlCon.addMainSql(" ) A GROUP BY A.houseId");
            sqlCon.addMainSql(" ) T21");
            sqlCon.addMainSql(" ON (T1.houseId = T21.houseId)");

            sqlCon.addMainSql(" LEFT OUTER JOIN");
            // 肉猪
            sqlCon.addMainSql(" (SELECT HOUSE_ID AS houseId, COUNT(1) AS houseChildPigQty FROM PP_L_PIG");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS <= 18 AND PIG_TYPE = '3'");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addMainSql(" GROUP BY HOUSE_ID) T3");
            sqlCon.addMainSql(" ON (T1.houseId = T3.houseId)");
            sqlCon.addMainSql(" INNER JOIN PP_O_HOUSE T4");
            sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T4.STATUS = '1'");
            sqlCon.addMainSql(" AND T1.houseId = T4.ROW_ID)");
            sqlCon.addMainSql(" WHERE 1=1");
            if (isXNCompany(getCompanyMark())) {
                sqlCon.addMainSql(" AND (T4.HOUSE_TYPE <> 6");
                sqlCon.addMainSql(" OR (T4.HOUSE_TYPE = 6 AND T2.houseProductPigQty <> 0 AND T2.houseProductPigQty IS NOT NULL)");
                sqlCon.addMainSql(" )");
            }
            sqlCon.addMainSql(" AND EXISTS(");
            sqlCon.addMainSql(" SELECT 1 FROM PP_O_HOUSE WHERE DELETED_FLAG='0' AND STATUS = '1' AND ROW_ID = T1.houseId");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            // 由部门查出管理的猪舍
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM HR_R_DEPT_HOUSE WHERE DELETED_FLAG = '0' AND HOUSE_ID = PP_O_HOUSE.ROW_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "deptId"), " AND DEPT_ID = ?");
            sqlCon.addMainSql(" LIMIT 1) LIMIT 1)");
            sqlCon.addMainSql(" ORDER BY T4.HOUSE_NAME");

        } else {
            sqlCon.addMainSql("SELECT * FROM (");
            sqlCon.addMainSql(" SELECT T0.ROW_ID AS houseId, T0.HOUSE_NAME AS houseIdName,T0.HOUSE_TYPE AS houseType");
            // 猪只总数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS BETWEEN 1 AND 18,1,0)) housePigQty");
            // 生产猪数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS IN(2,5,6,7,8,9,10,11),1,0)) houseProductPigQty");
            // 后备猪数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS IN(1,3,4),1,0)) houseBackUpPigQty");
            // 肉猪数
            sqlCon.addMainSql(",SUM(IF(T4.PIG_CLASS IN(12,13,14,15,16,17,18),1,0)) houseChildPigQty");
            sqlCon.addMainSql(" FROM PP_O_HOUSE T0");
            sqlCon.addMainSql(" LEFT JOIN CD_L_PIG_HOUSE T1");
            sqlCon.addMainSql(" ON(T0.HOUSE_TYPE = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.ROW_ID BETWEEN 1 AND 9)");
            sqlCon.addMainSql(" LEFT JOIN (SELECT T.HOUSE_ID,SUM(T.PIG_NUM) PIG_NUM FROM pp_o_pigpen T");
            sqlCon.addMainSql(" WHERE T.DELETED_FLAG = '0'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T.FARM_ID = ?");
            sqlCon.addMainSql(" GROUP BY T.HOUSE_ID) T2");
            sqlCon.addMainSql(" ON (T0.ROW_ID = T2.HOUSE_ID)");
            sqlCon.addMainSql(" LEFT JOIN(");
            sqlCon.addMainSql(" SELECT T0.PIG_ID, T0.HOUSE_ID");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.CHANGE_HOUSE_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T0");
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG T1");
            sqlCon.addMainSql(" ON (T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.ROW_ID AND T1.DELETED_FLAG ='0' AND T1.ORIGIN <> 3)");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.CHANGE_HOUSE_DATE <= DATE_SUB(?,INTERVAL 1 DAY)) T3");
            sqlCon.addMainSql(" ON (T0.ROW_ID = T3.HOUSE_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T3.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" LEFT JOIN (");
            sqlCon.addMainSql(" SELECT T0.PIG_ID,");
            sqlCon.addMainSql(" IF(T0.PIG_CLASS=24");
            sqlCon.addMainSql(" ,(SELECT PIG_CLASS FROM PP_L_BILL_TOWORK WHERE DELETED_FLAG = '0' AND PIG_CLASS_OUT = 24");
            sqlCon.addMainSql(" AND PIG_ID = T0.PIG_ID LIMIT 1)");
            sqlCon.addMainSql(" ,T0.PIG_CLASS) AS PIG_CLASS");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.TOWORK_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T0");
            sqlCon.addMainSql(" LEFT JOIN PP_L_BILL_LEAVE T1");
            sqlCon.addMainSql(" ON (T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.PIG_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1')");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.TOWORK_DATE <= DATE_SUB(?,INTERVAL 1 DAY)) T4");
            sqlCon.addMainSql(" ON (T3.PIG_ID = T4.PIG_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T4.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            // 由部门查出管理的猪舍
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM HR_R_DEPT_HOUSE WHERE DELETED_FLAG = '0' AND HOUSE_ID = T0.ROW_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "deptId"), " AND DEPT_ID = ?");
            sqlCon.addMainSql(" LIMIT 1)");

            sqlCon.addMainSql(" GROUP BY T0.ROW_ID");
            sqlCon.addMainSql(" ) A");
            sqlCon.addMainSql(" WHERE 1=1");
            if (isXNCompany(getCompanyMark())) {
                sqlCon.addMainSql(" AND (A.houseType <> 6");
                sqlCon.addMainSql(" OR (A.houseType = 6 AND A.houseProductPigQty <> 0 AND A.houseProductPigQty IS NOT NULL))");
            }
            sqlCon.addMainSql(" AND A.housePigQty > 0");
            sqlCon.addMainSql(" ORDER BY A.houseIdName");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        // 后备猪条数
        List<Map<String, Object>> houseBackUpList = new ArrayList<Map<String, Object>>();

        for (Map<String, Object> map : list) {
            // 新农旗下管理品名
            if (isXNCompany(getCompanyMark())) {
                if (Maps.getLong(map, "houseBackUpPigQty") != 0L) {
                    // 复制原记录，多增加一个后备记录
                    Map<String, Object> houseBackUpMap = new HashMap<String, Object>();
                    houseBackUpMap.put("houseId", Maps.getLong(map, "houseId"));
                    houseBackUpMap.put("houseProductPigQty", Maps.getLong(map, "houseProductPigQty"));
                    houseBackUpMap.put("houseChildPigQty", Maps.getLong(map, "houseChildPigQty"));
                    houseBackUpMap.put("houseBackUpPigQty", Maps.getLong(map, "houseBackUpPigQty"));
                    houseBackUpMap.put("houseType", Maps.getLong(map, "houseType"));
                    houseBackUpMap.put("houseIdName", Maps.getString(map, "houseIdName"));
                    houseBackUpMap.put("pigQty", Maps.getLong(houseBackUpMap, "houseBackUpPigQty"));
                    houseBackUpMap.put("pigType", 1L);
                    houseBackUpMap.put("pigTypeName", "后备猪");
                    houseBackUpList.add(houseBackUpMap);
                }

                if (Maps.getLong(map, "houseProductPigQty") != 0L) {
                    map.put("pigQty", Maps.getLong(map, "houseProductPigQty"));
                    map.put("pigType", 2L);
                    map.put("pigTypeName", "生产猪");
                } else {
                    map.put("pigQty", Maps.getLong(map, "housePigQty"));
                }
            } else {
                // 非新农旗下不区分品名
                map.put("pigQty", Maps.getLong(map, "housePigQty"));
            }
        }

        // 将后备猪放入list
        list.addAll(houseBackUpList);

        return list;
    }

    @Override
    public List<Map<String, Object>> searchSwineryHavePigToList(Map<String, Object> inputMap) throws Exception {

        String searchDateString = Maps.getString(inputMap, "searchDate");
        Date searchDate = TimeUtil.parseDate(searchDateString);
        Date currentDate = new Date();

        SqlCon sqlCon = new SqlCon();

        if (searchDate.after(currentDate)) {
            sqlCon.addMainSql("SELECT T1.houseId, T2.HOUSE_TYPE AS houseType, T1.swineryId, T1.pigSwineryQty, T1.pigSwineryQty AS pigQty");
            sqlCon.addMainSql(", T2.HOUSE_NAME AS houseIdName");
            sqlCon.addMainSql(" FROM ( SELECT HOUSE_ID AS houseId, SWINERY_ID AS swineryId, COUNT(1) AS pigSwineryQty ");
            // 肉猪
            sqlCon.addMainSql(" FROM PP_L_PIG WHERE DELETED_FLAG = '0' AND STATUS = '1' AND PIG_CLASS <= 18 AND PIG_TYPE = 3");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            sqlCon.addCondition(Maps.getLongClass(inputMap, "houseId"), " AND HOUSE_ID = ?");
            sqlCon.addMainSql(" GROUP BY HOUSE_ID, SWINERY_ID) T1");
            sqlCon.addMainSql(" INNER JOIN PP_O_HOUSE T2");
            sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.houseId = T2.ROW_ID)");
            sqlCon.addMainSql(" WHERE EXISTS(SELECT 1 FROM PP_O_HOUSE WHERE DELETED_FLAG='0' AND STATUS = '1' AND ROW_ID = T1.houseId");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
            // 由部门查出管理的猪舍
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM HR_R_DEPT_HOUSE WHERE DELETED_FLAG = '0' AND HOUSE_ID = PP_O_HOUSE.ROW_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "deptId"), " AND DEPT_ID = ?");
            sqlCon.addMainSql(" LIMIT 1) LIMIT 1)");
            sqlCon.addMainSql(" ORDER BY T2.HOUSE_NAME");

        } else {
            sqlCon.addMainSql("SELECT * FROM (");
            sqlCon.addMainSql("SELECT T0.ROW_ID AS houseId, T0.HOUSE_TYPE AS houseType, T0.HOUSE_NAME AS houseIdName");
            sqlCon.addMainSql(", T5.SWINERY_ID AS swineryId, T5.SWINERY_NAME AS swineryName");
            sqlCon.addMainSql(" ,SUM(IF(T4.PIG_CLASS BETWEEN 1 AND 18,1,0)) pigQty, SUM(IF(T4.PIG_CLASS BETWEEN 1 AND 18,1,0)) pigSwineryQty");
            sqlCon.addMainSql(" FROM PP_O_HOUSE T0");
            sqlCon.addMainSql(" LEFT JOIN CD_L_PIG_HOUSE T1");
            sqlCon.addMainSql(" ON (T0.HOUSE_TYPE = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.ROW_ID BETWEEN 1 AND 9)");
            sqlCon.addMainSql(" LEFT JOIN (SELECT T.HOUSE_ID,SUM(T.PIG_NUM) PIG_NUM FROM PP_O_PIGPEN T");
            sqlCon.addMainSql(" WHERE T.DELETED_FLAG = '0'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T.FARM_ID = ?");
            sqlCon.addMainSql(" GROUP BY T.HOUSE_ID) T2");
            sqlCon.addMainSql(" ON (T0.ROW_ID = T2.HOUSE_ID)");
            sqlCon.addMainSql(" LEFT JOIN(");
            sqlCon.addMainSql(" SELECT T0.PIG_ID,T0.HOUSE_ID");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.CHANGE_HOUSE_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_HOUSE T0");
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG T1");
            sqlCon.addMainSql(" ON (T0.FARM_ID = T1.FARM_ID AND T0.PIG_ID = T1.ROW_ID AND T1.DELETED_FLAG ='0' AND T1.ORIGIN <> 3");
            // 肉猪
            sqlCon.addMainSql(" AND T1.PIG_TYPE = '3')");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.CHANGE_HOUSE_DATE <= DATE_SUB(?,INTERVAL 1 DAY)) T3");
            sqlCon.addMainSql(" ON (T0.ROW_ID = T3.HOUSE_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T3.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" LEFT JOIN (");
            sqlCon.addMainSql(" SELECT T0.PIG_ID,T0.PIG_CLASS");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.TOWORK_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_TOWORK T0");
            sqlCon.addMainSql(" LEFT JOIN PP_L_PIG T1");
            sqlCon.addMainSql(" ON (T1.FARM_ID = T0.FARM_ID AND T1.ROW_ID = T0.PIG_ID AND T1.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.TOWORK_DATE <= DATE_SUB(?,INTERVAL 1 DAY))T4");
            sqlCon.addMainSql(" ON (T3.PIG_ID = T4.PIG_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T4.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" LEFT JOIN (");
            sqlCon.addMainSql(" SELECT T0.PIG_ID,T0.SWINERY_ID, T2.SWINERY_NAME");
            sqlCon.addMainSql(" ,DATE(IFNULL(T0.CHANGE_SWINERY_DATE_OUT,IFNULL(T1.LEAVE_DATE,DATE(DATE_ADD(NOW(),INTERVAL 1 DAY))))) END_DATE");
            sqlCon.addMainSql(" FROM PP_L_BILL_CHANGE_SWINERY T0");
            sqlCon.addMainSql(" LEFT JOIN PP_L_PIG T1");
            sqlCon.addMainSql(" ON (T1.FARM_ID = T0.FARM_ID AND T1.ROW_ID = T0.PIG_ID AND T1.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" LEFT JOIN PP_M_SWINERY T2");
            sqlCon.addMainSql(" ON (T0.FARM_ID = T2.FARM_ID AND T0.SWINERY_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T0.CHANGE_SWINERY_DATE <= DATE_SUB(?,INTERVAL 1 DAY)) T5");
            sqlCon.addMainSql(" ON (T3.PIG_ID = T5.PIG_ID");
            sqlCon.addCondition(Maps.getString(inputMap, "searchDate"), " AND T5.END_DATE > DATE_SUB(?,INTERVAL 1 DAY))");
            sqlCon.addMainSql(" WHERE T0.DELETED_FLAG = '0' AND T0.STATUS = '1'");
            sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
            sqlCon.addCondition(Maps.getLongClass(inputMap, "houseId"), " AND T0.ROW_ID = ?");
            // 由部门查出管理的猪舍
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM HR_R_DEPT_HOUSE WHERE DELETED_FLAG = '0' AND HOUSE_ID = T0.ROW_ID");
            sqlCon.addCondition(Maps.getLong(inputMap, "deptId"), " AND DEPT_ID = ?");
            sqlCon.addMainSql(" LIMIT 1)");

            sqlCon.addMainSql(" GROUP BY T0.ROW_ID,T5.SWINERY_ID");
            sqlCon.addMainSql(")A");
            sqlCon.addMainSql(" WHERE A.pigQty > 0");
            sqlCon.addMainSql(" ORDER BY A.houseIdName, A.swineryName");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> map : list) {
            map.put("swineryIdName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
            map.put("swineryName", CacheUtil.getName(Maps.getString(map, "swineryId"), NameEnum.SWINERY_NAME));
            map.put("pigType", 3L);
            map.put("pigTypeName", "商品猪");
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchDeptToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS deptId, DEPT_NAME AS deptName FROM HR_O_DEPT WHERE DELETED_FLAG = '0' AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM HR_O_POST WHERE DELETED_FLAG = '0' AND DEPT_ID = HR_O_DEPT.ROW_ID AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM HR_R_EMPLOYEE_POST WHERE DELETED_FLAG = '0' AND POST_ID = HR_O_POST.ROW_ID");
        sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ? LIMIT 1) LIMIT 1)");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        return list;
    }

    /* START 物料领用 控件URL */

    /* START 出库单 */
    public BasePageInfo searchOutputStoreToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId,T1.STATUS AS status, T1.BILL_CODE AS billCode");
        sqlCon.addMainSql(", T1.BILL_DATE AS billDate, T1.OUTPUT_WAREHOUSE_ID AS outputWarehouseId");
        sqlCon.addMainSql(", T1.EVENT_CODE AS eventCode, T1.USER_ID AS userId, T1.OUTPUTER_ID AS outputerId, T1.NOTES AS notes");
        sqlCon.addMainSql(", (SELECT EMPLOYEE_NAME FROM HR_O_EMPLOYEE WHERE DELETED_FLAG = '0' AND ROW_ID = T1.USER_ID LIMIT 1) AS userName");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T1");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(Maps.getString(inputMap, "advancedBillCode"), " AND T1.BILL_CODE LIKE ?", true);
        sqlCon.addCondition(Maps.getString(inputMap, "advancedStartDate"), " AND T1.BILL_DATE >= ?");
        sqlCon.addCondition(Maps.getString(inputMap, "advancedEndDate"), " AND T1.BILL_DATE <= ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "advancedOutputerId"), " AND T1.OUTPUTER_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "advancedUserId"), " AND T1.USER_ID = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "advancedEventCode"), " AND T1.EVENT_CODE = ?");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND (T1.FARM_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " OR T1.COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

        } else {
            sqlCon.addConditionWithNull(getFarmId(), "  AND T1.FARM_ID = ?");

        }

        if ("personal".equals(Maps.getString(inputMap, "searchType"))) {
            sqlCon.addCondition(getEmployeeId(), " AND T1.USER_ID = ?");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "eventCode"), " AND T1.EVENT_CODE = ?");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE DESC, T1.ROW_ID DESC");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> outputStoreMapList = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> outputStoreMap : outputStoreMapList) {
            Map<String, String> billStatusMap = SupplyChianConstants.EVENT_CODE_AND_BILL_STATUS.get(Maps.getString(outputStoreMap, "eventCode"));
            if (billStatusMap != null) {
                outputStoreMap.put("statusName", Maps.getString(billStatusMap, Maps.getString(outputStoreMap, "status")));
            } else {
                outputStoreMap.put("statusName", "正常");
            }
            outputStoreMap.put("billDate", TimeUtil.format(Maps.getString(outputStoreMap, "billDate"), TimeUtil.DATE_FORMAT));
            outputStoreMap.put("eventCodeName", CacheUtil.getName(Maps.getString(outputStoreMap, "eventCode"), NameEnum.CODE_NAME,
                    xn.pigfarm.util.enums.CodeEnum.SUPPLYCHIAN_EVENT_CODE));
            // outputStoreMap.put("userName", CacheUtil.getName(Maps.getString(outputStoreMap, "userId"), NameEnum.EMPLOYEE_NAME));
            outputStoreMap.put("outputerName", CacheUtil.getName(Maps.getString(outputStoreMap, "outputerId"), NameEnum.EMPLOYEE_NAME));
            outputStoreMap.put("outputWarehouseName", CacheUtil.getName(Maps.getString(outputStoreMap, "outputWarehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            outputStoreMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(outputStoreMap, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            outputStoreMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(outputStoreMap,
                    "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(outputStoreMap, "materialFirstClass")));
        }

        return getPageInfo(outputStoreMapList);
    }

    @Override
    public List<Map<String, Object>> searchOutputBillDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT MAX(T1.ROW_ID) AS rowId, T1.MATERIAL_ID AS materialId, T1.NOTES AS notes");
        sqlCon.addMainSql(" , T1.OUTPUT_WAREHOUSE_ID AS outputWarehouseId, _clearEndZero(SUM(T1.OUTPUT_QTY))  AS outputQty");
        sqlCon.addMainSql(" , T1.EVENT_CODE AS eventCode");
        sqlCon.addMainSql(" , IF(T1.EVENT_CODE = 'OUTPUT_USE',T1.OUTPUT_DESTINATION,T1.OUTPUT_TO_FARM_ID) AS outputDestination");
        sqlCon.addMainSql(",T4.DEPT_NAME AS outputDeptName, T2.HOUSE_NAME AS outputHoseName,T3.SWINERY_NAME AS outputSwineryName");
        sqlCon.addMainSql(", (SELECT FREE_LN FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY LIMIT 1) AS freeLn");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT_DETAIL T1");
        sqlCon.addMainSql(" LEFT OUTER JOIN PP_O_HOUSE T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.OUTPUT_HOUSE_ID = T2.ROW_ID)");
        sqlCon.addMainSql(" LEFT OUTER JOIN PP_M_SWINERY T3");
        sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T3.STATUS = '1' AND T1.OUTPUT_SWINERY_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_O_DEPT T4");
        sqlCon.addMainSql(" ON(T4.DELETED_FLAG = '0' AND T4.STATUS = '1' AND T1.OUTPUT_DEPT_ID = T4.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND (T1.FARM_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " OR T1.COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

        } else {
            sqlCon.addConditionWithNull(getFarmId(), "  AND T1.FARM_ID = ?");

        }

        sqlCon.addCondition(Maps.getLong(inputMap, "outputId"), " AND T1.OUTPUT_ID = ?");
        sqlCon.addMainSql(" GROUP BY T1.OUTPUT_LN ORDER BY T1.OUTPUT_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));

            map.put("outputWarehouseName", CacheUtil.getName(Maps.getString(map, "outputWarehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            map.put("outputQtyName", Maps.getString(map, "outputQty") + Maps.getString(map, "unit"));
            if (!Maps.isEmpty(map, "freeLn")) {
                if (Maps.getLong(map, "freeLn") == 0D) {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
                } else {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
                }
            } else {
                map.put("purchaseOrFree", "仓管未分配");
            }
            if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(map, "eventCode"))) {
                map.put("outputDestinationName", SupplyChianConstants.OUTPUT_DESTINATION_MAP.get(Maps.getString(map, "outputDestination")));
            } else {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "outputDestination"));
                map.put("outputDestinationName", Maps.getString(companyInfoMap, "SORT_NAME"));
            }

            if (SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(Maps.getString(map, "eventCode"))) {
                map.put("outputDestinationName", SupplyChianConstants.OUTPUT_DESTINATION_MAP.get(Maps.getString(map, "outputDestination")));
            } else {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "outputDestination"));
                map.put("outputDestinationName", Maps.getString(companyInfoMap, "SORT_NAME"));
            }

        }

        return list;
    }

    @Override
    public void editOutputUse(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDate(Maps.getString(inputMap, "billDate"));

        // 同一物料合计数
        Map<Long, Double> materialCount = new HashMap<Long, Double>();
        for (Map<String, Object> detailMap : detailList) {
            BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "outputQty"), Maps.getDouble(detailMap, "outputMinQty"));
            if (remainder[1].doubleValue() != 0D) {
                Thrower.throwException(SupplyChianException.OUTPUT_MIN_QTY_ERROR, Maps.getLong(detailMap, "lineNumber"), Maps.getString(detailMap,
                        "materialName"), Maps.getDouble(detailMap, "outputMinQty"));
            }

            if ("pigHouse".equals(Maps.getString(inputMap, "outputDestination")) || "swinery".equals(Maps.getString(inputMap, "outputDestination"))) {
                String outputDestinationName = "猪舍";
                if ("swinery".equals(Maps.getString(inputMap, "outputDestination"))) {
                    outputDestinationName = "批次";
                }
                if (Maps.isEmpty(detailMap, "houseId")) {
                    Thrower.throwException(SupplyChianException.PIG_HOUSE_IS_NULL, outputDestinationName, Maps.getLong(detailMap, "lineNumber"));
                }
                if ("swinery".equals(Maps.getString(inputMap, "outputDestination"))) {
                    if (Maps.isEmpty(detailMap, "swineryId")) {
                        Thrower.throwException(SupplyChianException.PIG_SWINERY_IS_NULL, outputDestinationName, Maps.getLong(detailMap,
                                "lineNumber"));
                    }
                }

                // 新农公司旗下，猪只品名不能为空
                if (isXNCompany(getCompanyMark())) {
                    if (StringUtil.isBlank(Maps.getString(detailMap, "pigType"))) {
                        Thrower.throwException(SupplyChianException.USE_MATERIAL_PIG_TYPE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
                    }
                }
            }

            // 饲料领用需精确到猪舍批次
            String sllyxjqdzspcFlag = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SC_SLLYXJQDZSPC");

            if ("ON".equals(sllyxjqdzspcFlag)) {
                if (SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(detailMap, "materialFirstClass"))
                        || SupplyChianConstants.MATERIAL_FIRST_CLASS_22.equals(Maps.getString(detailMap, "materialFirstClass"))) {
                    if (!"pigHouse".equals(Maps.getString(inputMap, "outputDestination")) && !"swinery".equals(Maps.getString(inputMap,
                            "outputDestination"))) {
                        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【饲料】的领用去向必须精确到【猪舍】或【批次】");
                    }

                    if (((PigConstants.HOUSE_CLASS_CAREPIG == Maps.getLong(detailMap, "houseType")) || (PigConstants.HOUSE_CLASS_FATTEN == Maps
                            .getLong(detailMap, "houseType"))) && (!"swinery".equals(Maps.getString(inputMap, "outputDestination")))) {
                        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【保育舍】和【育肥舍】领用【饲料】领用去向必须精确到【批次】");
                    }
                }
            }

            if (Maps.isEmpty(detailMap, "materialId")) {
                Thrower.throwException(SupplyChianException.MATERIAL_ID_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
            }

            if (Maps.isEmpty(materialCount, Maps.getLong(detailMap, "materialId"))) {
                // 如果没有重复 materialId
                materialCount.put(Maps.getLong(detailMap, "materialId"), Maps.getDouble(detailMap, "outputQty"));
            } else {
                // 如果重复 materialId
                Double outputQty = materialCount.get(Maps.getLong(detailMap, "materialId"));
                materialCount.put(Maps.getLong(detailMap, "materialId"), bigDecimalAdd(outputQty, Maps.getDouble(detailMap, "outputQty")));
            }
        }

        for (Map.Entry<Long, Double> entry : materialCount.entrySet()) {
            // CHECK 出库数量是否大于(库存数量减去已被其他技术员预出库的数量)
            Long farmId = null;

            if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
                farmId = getFarmId();

            } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
                farmId = getCompanyId();

            } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
                farmId = getFarmId();

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
            }

            StoreMaterialModel storeMaterialModel = storeMaterialMapper.searchTotalQtyExceptOtherUse(Maps.getString(inputMap, "dafengModel"), farmId,
                    entry.getKey(), Maps.getLong(inputMap, "warehouseId")).get(0);
            if (storeMaterialModel.getActualQty().compareTo(entry.getValue()) < 0) {
                Map<String, String> materialInfoMap = CacheUtil.getMaterialInfo(String.valueOf(entry.getKey()), MaterialInfoEnum.MATERIAL_INFO);
                Thrower.throwException(SupplyChianException.USE_QTY_IS_NOT_ENOUGH, Maps.getString(materialInfoMap, "materialName"),
                        bigDecimalSubtract(entry.getValue(), storeMaterialModel.getActualQty()));
            }
        }

        excuteOutputUseProcess(inputMap, detailList);
    }

    private synchronized void excuteOutputUseProcess(Map<String, Object> inputMap, List<HashMap> detailList) throws Exception {
        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_USE;

        String billCode = null;
        if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(getCompanyId()));
            Long supCompanyId = Maps.getLong(companyInfoMap, "SUP_COMPANY_ID");
            billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), supCompanyId, getCompanyId());

        } else {
            billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());

        }

        // 领料号
        String outputUseNumber = createOutputUseNumber(inputMap, getCompanyId(), getFarmId());

        OutputModel outputModel = getBean(OutputModel.class, inputMap);
        outputModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING);
        outputModel.setFarmId(getFarmId());
        outputModel.setCompanyId(getCompanyId());
        outputModel.setBillCode(billCode);
        outputModel.setEventCode(eventCode);
        outputModel.setOutputWarehouseId(Maps.getLong(inputMap, "warehouseId"));
        outputModel.setOutputDestination(Maps.getString(inputMap, "outputDestination"));
        outputModel.setOutputFarmId(getFarmId());
        if (!"farm".equals(Maps.getString(inputMap, "outputDestination"))) {
            outputModel.setOutputSwineryId(Maps.getLongClass(inputMap, "outputDesSwineryId"));
            outputModel.setOutputHouseId(Maps.getLongClass(inputMap, "outputDesHouseId"));
            outputModel.setOutputDeptId(Maps.getLong(inputMap, "outputDesDeptId"));
        }
        outputModel.setOutputUseNumber(outputUseNumber);
        outputModel.setUserId(getEmployeeId());
        outputModel.setOaUsername(getOaUsername());
        outputModel.setCreateId(getEmployeeId());
        outputModel.setCreateDate(currentDate);
        outputModel.setPrintTimes(0L);
        outputMapper.insert(outputModel);

        // START HANA
        boolean isToHana = false;
        if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            isToHana = HanaUtil.isToHanaCheck(getCompanyId());

        } else {
            isToHana = HanaUtil.isToHanaCheck(getFarmId());

        }
        // END HANA

        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {

            OutputDetailModel outputDetailModel = new OutputDetailModel();
            outputDetailModel.setStatus("1");
            outputDetailModel.setDeletedFlag("0");
            outputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            outputDetailModel.setFarmId(getFarmId());
            outputDetailModel.setCompanyId(getCompanyId());
            outputDetailModel.setOutputId(outputModel.getRowId());
            outputDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
            outputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            // outputStoreModel.setMaterialOnly(storeMaterialModel.getMaterialOnly());
            // outputStoreModel.setMaterialBatch(storeMaterialModel.getMaterialBatch());
            outputDetailModel.setPlanQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputWarehouseId(Maps.getLong(inputMap, "warehouseId"));
            outputDetailModel.setOutputDestination(Maps.getString(inputMap, "outputDestination"));
            outputDetailModel.setOutputFarmId(getFarmId());
            outputDetailModel.setPigType(Maps.getString(detailMap, "pigType", "3"));
            if (!"farm".equals(Maps.getString(inputMap, "outputDestination"))) {
                outputDetailModel.setOutputDeptId(Maps.getLong(inputMap, "outputDesDeptId"));
                if ("pigHouse".equals(Maps.getString(inputMap, "outputDestination")) || "swinery".equals(Maps.getString(inputMap,
                        "outputDestination"))) {
                    outputDetailModel.setOutputHouseId(Maps.getLong(detailMap, "houseId"));
                    outputDetailModel.setPigQty(Maps.getLong(detailMap, "pigQty"));
                    // START HANA
                    if (isToHana) {
                        // 验证猪舍单元号和栋号是否设置
                        HanaUtil.getMTC_Unit(Maps.getLong(detailMap, "houseId"));
                    }
                    // END HANA
                }
            }
            outputDetailModel.setOutputSwineryId(Maps.getLongClass(detailMap, "swineryId"));
            outputDetailModel.setBillCode(billCode);
            outputDetailModel.setBillDate(Maps.getDate(inputMap, "billDate"));
            outputDetailModel.setEventCode(eventCode);
            outputDetailModel.setUserId(getEmployeeId());
            outputDetailModel.setCreateId(getEmployeeId());
            outputDetailModel.setCreateDate(currentDate);
            insertOutputDetailModelList.add(outputDetailModel);
        }

        if (insertOutputDetailModelList != null && !insertOutputDetailModelList.isEmpty()) {
            outputDetailMapper.inserts(insertOutputDetailModelList);
        }
    }

    // 领料号
    private synchronized String createOutputUseNumber(Map<String, Object> inputMap, long companyId, long farmId) {
        String dateString = TimeUtil.format(Maps.getDate(inputMap, "billDate"), "yyyyMMdd") + "_";
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT OUTPUT_USE_NUMBER AS outputUseNumber");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT");
        sqlCon.addCondition(dateString + "%", " WHERE DELETED_FLAG = '0' AND OUTPUT_USE_NUMBER LIKE ?");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(farmId, " AND (FARM_ID = ?");
            sqlCon.addConditionWithNull(farmId, " OR COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(companyId, " AND (FARM_ID = ?");
            sqlCon.addConditionWithNull(companyId, " OR COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");

        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
        }

        sqlCon.addMainSql(" ORDER BY OUTPUT_USE_NUMBER DESC LIMIT 1");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> outputUseNumberList = paramMapper.getObjectInfos(sqlMap);

        if (outputUseNumberList != null && !outputUseNumberList.isEmpty()) {
            String oldOutputUseNumber = Maps.getString(outputUseNumberList.get(0), "outputUseNumber");
            String oldNumberString = oldOutputUseNumber.split("_")[1];
            Long number = Long.valueOf(oldNumberString) + 1L;
            // 补位
            String numberSting = String.format("%05d", number);
            return dateString + numberSting;
        } else {
            return dateString + "00001";
        }
    }

    @Override
    public void editOutputScrap(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "scrapDate"));

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_SCRAP;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        OutputModel outputModel = getBean(OutputModel.class, inputMap);
        outputModel.setStatus(CommonConstants.STATUS);
        outputModel.setFarmId(getFarmId());
        outputModel.setCompanyId(getCompanyId());
        outputModel.setBillCode(billCode);
        outputModel.setBillDate(Maps.getDate(inputMap, "scrapDate"));
        outputModel.setEventCode(eventCode);
        outputModel.setOutputWarehouseId(Maps.getLong(inputMap, "scrapOutputWarehouseId"));
        outputModel.setOutputerId(getEmployeeId());
        outputModel.setUserId(getEmployeeId());
        outputModel.setOaUsername(getOaUsername());
        outputModel.setCreateId(getEmployeeId());
        outputModel.setCreateDate(currentDate);
        outputModel.setPrintTimes(0L);
        outputMapper.insert(outputModel);

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        HanaMaterialOperateBill hanaMaterialOperateBill = null;
        List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = null;
        String mtcBranchID = null;
        String mtcDeptID = null;
        if (isToHana) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            hanaMaterialOperateBill = new HanaMaterialOperateBill();
            // 分公司编码
            hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaMaterialOperateBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(outputModel.getRowId()) + "-" + outputModel.getBillCode());
            // 出库日期
            hanaMaterialOperateBill.setMTC_DocDate(outputModel.getBillDate());
            // 报废人
            hanaMaterialOperateBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(getFarmId(), getEmployeeId()));
            // 表行
            hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

            hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);
        }
        // END HANA

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(Maps.getDouble(detailMap, "outputQty")) < 0) {
                Thrower.throwException(SupplyChianException.QTY_IS_NOT_ENOUGH, Maps.getLong(detailMap, "lineNumber"));
            }
            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap,
                    "outputQty")));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            OutputDetailModel outputDetailModel = new OutputDetailModel();
            outputDetailModel.setStatus(CommonConstants.STATUS);
            outputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            outputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            outputDetailModel.setFarmId(getFarmId());
            outputDetailModel.setCompanyId(getCompanyId());
            outputDetailModel.setOutputId(outputModel.getRowId());
            outputDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
            outputDetailModel.setMaterialId(updateStoreMaterialModel.getMaterialId());
            outputDetailModel.setMaterialOnly(updateStoreMaterialModel.getMaterialOnly());
            outputDetailModel.setMaterialBatch(updateStoreMaterialModel.getMaterialBatch());
            outputDetailModel.setPlanQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputWarehouseId(updateStoreMaterialModel.getWarehouseId());
            outputDetailModel.setOutputerId(getEmployeeId());
            outputDetailModel.setOutputDestination(Maps.getString(inputMap, "outputDestination"));
            outputDetailModel.setUserId(getEmployeeId());
            outputDetailModel.setBillCode(billCode);
            outputDetailModel.setBillDate(Maps.getDate(inputMap, "scrapDate"));
            outputDetailModel.setEventCode(eventCode);
            outputDetailModel.setCreateId(getEmployeeId());
            outputDetailModel.setCreateDate(currentDate);
            insertOutputDetailModelList.add(outputDetailModel);

            // START HANA
            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(outputDetailModel.getMaterialId());
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(outputDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                    // 分公司编码
                    hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号
                    hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(outputDetailModel.getOutputId()));
                    // 新农+单据行号
                    hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(outputDetailModel.getOutputLn()));
                    // 物料编号
                    hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialOperateBillDetail.setMTC_BatchNum(outputDetailModel.getMaterialBatch());
                    // 仓库编号
                    hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(outputDetailModel.getOutputWarehouseId()));
                    // 领用数量
                    hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(outputDetailModel.getOutputQty()));

                    hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                }
            }
            // END HANA

        }

        storeMaterialMapper.updates(updateStoreMaterialModelList);
        outputDetailMapper.inserts(insertOutputDetailModelList);

        // START HANA
        if (isToHana) {
            if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码:库存报废
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2060);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
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
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }
        // END HANA

    }

    @Override
    public BasePageInfo searchWareHouseMaterialFromPurchaseTableToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.BILL_CODE AS billCode, T1.BILL_DATE AS billDate, T1.STATUS AS status");
        sqlCon.addMainSql(", _clearEndZero(ROUND(SUM(T2.PURCHASE_QTY * T2.ACTUAL_PRICE),2)) AS totalPrice, T1.NOTES AS notes");
        sqlCon.addMainSql(", MAX(T2.PURCHASER_ID) AS purchaserId, MAX(T2.SUPPLIER_ID) AS supplierId, MAX(T2.ARRIVE_DATE) AS arriveDate");
        sqlCon.addMainSql(", MAX(T1.EVENT_CODE) AS eventCode");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE T1 INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" WHERE (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.ROW_ID = T2.PURCHASE_ID");
        sqlCon.addMainSql(" AND T2.FREE_RELATED_ID IS NULL");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " AND ( T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_COMPLETED, " OR T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_FINISHED, " OR T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_OVER, " OR T1.STATUS = ? )");
        sqlCon.addConditionWithNull(getFarmId(), " AND T2.REQUIRE_FARM = ?)");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY AND ACTUAL_QTY > 0 LIMIT 1)");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.put("billDate", TimeUtil.format(Maps.getString(map, "billDate"), TimeUtil.DATE_FORMAT));
                map.put("arriveDate", TimeUtil.format(Maps.getString(map, "arriveDate"), TimeUtil.DATE_FORMAT));
                map.put("totalPriceName", String.valueOf(Maps.getString(map, "totalPrice")) + "元");
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
                EmployeeModel employeeModel = employeeMapper.searchById(Maps.getLong(map, "purchaserId"));
                map.put("purchaserName", employeeModel.getEmployeeName());
                map.put("statusName", SupplyChianConstants.PURCHASE_STORE_STATUS_MAP.get(Maps.getString(map, "status")));
                map.put("eventCodeName", CacheUtil.getName(Maps.getString(map, "eventCode"), NameEnum.CODE_NAME,
                        xn.pigfarm.util.enums.CodeEnum.SUPPLYCHIAN_EVENT_CODE));
            }
        }
        return getPageInfo(list);
    }

    @Override
    public List<Map<String, Object>> searchOutputFromPurchaseDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T2.ROW_ID AS rowId, T1.MATERIAL_ID AS materialId, T2.MATERIAL_ONLY AS materialOnly");
        sqlCon.addMainSql(" , T2.MATERIAL_BATCH AS materialBatch, T2.WAREHOUSE_ID AS warehouseId, T2.ACTUAL_QTY AS existsQty");
        sqlCon.addMainSql(" , T2.PRODUCTION_DATE AS productionDate, T2.EFFECTIVE_DATE AS effectiveDate, T1.ACTUAL_PRICE AS actualPrice");
        sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL T1 INNER JOIN SC_M_STORE_MATERIAL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.MATERIAL_ONLY = T2.MATERIAL_ONLY AND T2.ACTUAL_QTY > 0)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addCondition(getFarmId(), " AND T1.REQUIRE_FARM = ?");
        sqlCon.addCondition(Maps.getLong(inputMap, "purchaseId"), " AND T1.PURCHASE_ID = ?");
        sqlCon.addMainSql(" GROUP BY T2.MATERIAL_BATCH");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));

            map.put("warehouseName", CacheUtil.getName(Maps.getString(map, "warehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
        }
        return list;
    }

    @Override
    public void editOutputPurchase(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(Maps.getString(inputMap, "returnDate"));

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_PURCHASE;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        OutputModel outputModel = getBean(OutputModel.class, inputMap);
        outputModel.setStatus(CommonConstants.STATUS);
        outputModel.setFarmId(getFarmId());
        outputModel.setCompanyId(getCompanyId());
        outputModel.setBillCode(billCode);
        outputModel.setBillDate(Maps.getDate(inputMap, "returnDate"));
        outputModel.setEventCode(eventCode);
        outputModel.setOutputWarehouseId(Maps.getLongClass(inputMap, "returnOutputWarehouseId"));
        outputModel.setOutputerId(getEmployeeId());
        outputModel.setReturnOrgin(Maps.getString(inputMap, "returnOrgin"));
        outputModel.setUserId(getEmployeeId());
        outputModel.setOaUsername(getOaUsername());
        outputModel.setCreateId(getEmployeeId());
        outputModel.setCreateDate(currentDate);
        outputModel.setPrintTimes(0L);
        outputMapper.insert(outputModel);

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());
        Map<String, HanaInputBill> hanaInputBillMap = null;
        String mtcBranchID = null;
        String mtcDeptID = null;
        if (isToHana) {
            hanaInputBillMap = new HashMap<String, HanaInputBill>();
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
        }
        // END HANA

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {

            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(Maps.getDouble(detailMap, "outputQty")) < 0) {
                Thrower.throwException(SupplyChianException.QTY_IS_NOT_ENOUGH, Maps.getLong(detailMap, "lineNumber"));
            }
            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap,
                    "outputQty")));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            OutputDetailModel outputDetailModel = new OutputDetailModel();
            outputDetailModel.setStatus(CommonConstants.STATUS);
            outputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            outputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            outputDetailModel.setFarmId(getFarmId());
            outputDetailModel.setCompanyId(getCompanyId());
            outputDetailModel.setOutputId(outputModel.getRowId());
            outputDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
            outputDetailModel.setMaterialId(updateStoreMaterialModel.getMaterialId());
            outputDetailModel.setMaterialOnly(updateStoreMaterialModel.getMaterialOnly());
            outputDetailModel.setMaterialBatch(updateStoreMaterialModel.getMaterialBatch());
            outputDetailModel.setPlanQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputWarehouseId(updateStoreMaterialModel.getWarehouseId());
            outputDetailModel.setOutputerId(getEmployeeId());
            outputDetailModel.setOutputDestination(Maps.getString(inputMap, "outputDestination"));
            outputDetailModel.setReturnOrgin(Maps.getString(inputMap, "returnOrgin"));
            outputDetailModel.setUserId(getEmployeeId());
            outputDetailModel.setBillCode(billCode);
            outputDetailModel.setBillDate(Maps.getDate(inputMap, "returnDate"));
            outputDetailModel.setEventCode(eventCode);
            outputDetailModel.setCreateId(getEmployeeId());
            outputDetailModel.setCreateDate(currentDate);
            insertOutputDetailModelList.add(outputDetailModel);

            // START HANA
            if (isToHana) {
                // 不需要了
                // 获取采购单单号和行号
                // SqlCon getPurchaseInfoSqlCon = new SqlCon();
                // getPurchaseInfoSqlCon.addCondition(updateStoreMaterialModel.getMaterialOnly(), " AND MATERIAL_ONLY = ? LIMIT 1");
                // List<PurchaseDetailModel> purchaseDetailModelList = getList(purchaseDetailMapper, getPurchaseInfoSqlCon);
                // PurchaseDetailModel purchaseDetailModel = purchaseDetailModelList.get(0);
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(updateStoreMaterialModel.getMaterialId());
                // if (StringUtil.isNonBlank(mtcItemCode)) {
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(updateStoreMaterialModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    String mtcCardCode = HanaUtil.getMTC_CardCode(Maps.getLong(detailMap, "supplierId"));

                    HanaInputBillDetail hanaInputBillDetail = new HanaInputBillDetail();
                    // 分公司编码
                    hanaInputBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaInputBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号
                    hanaInputBillDetail.setMTC_BaseEntry(String.valueOf(outputDetailModel.getOutputId()));
                    // 新农+单据行号
                    hanaInputBillDetail.setMTC_BaseLine(String.valueOf(outputDetailModel.getOutputLn()));
                    // 新农+采购单编号
                    // hanaInputBillDetail.setMTC_OrignEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                    // 新农+采购单行号
                    // hanaInputBillDetail.setMTC_OrignLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" +
                    // String.valueOf(purchaseDetailModel
                    // .getFreeLn()));
                    // 采购类型
                    hanaInputBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                    // 物料编号
                    hanaInputBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 仓库编号
                    hanaInputBillDetail.setMTC_WhsCode(String.valueOf(updateStoreMaterialModel.getWarehouseId()));
                    // 入库数量
                    hanaInputBillDetail.setMTC_Qty(String.valueOf(outputDetailModel.getOutputQty()));
                    // 入库单价
                    hanaInputBillDetail.setMTC_QtyPrice(Maps.getString(detailMap, "actualPrice"));
                    // 入库金额
                    Double totalPrice = bigDecimalMultiply(outputDetailModel.getOutputQty(), Maps.getDouble(detailMap, "actualPrice"));
                    hanaInputBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                    // 批次编号
                    hanaInputBillDetail.setMTC_BatchNum(updateStoreMaterialModel.getMaterialBatch());
                    // 生产日期
                    hanaInputBillDetail.setMTC_ProDate(updateStoreMaterialModel.getProductionDate());
                    // 有效日期
                    hanaInputBillDetail.setMTC_ValidDate(updateStoreMaterialModel.getEffectiveDate());

                    // 不存在
                    if (!hanaInputBillMap.containsKey(mtcCardCode)) {
                        HanaInputBill hanaInputBill = new HanaInputBill();
                        // 分公司编码
                        hanaInputBill.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                        hanaInputBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(outputModel.getRowId()) + "-" + billCode);
                        // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                        hanaInputBill.setMTC_NumAtCard(mtcBranchID + "-" + String.valueOf(outputModel.getRowId()) + "-" + billCode);
                        // 业务伙伴编号
                        hanaInputBill.setMTC_CardCode(mtcCardCode);
                        // 采购日期
                        hanaInputBill.setMTC_DocDate(outputModel.getBillDate());
                        // 到货日期
                        hanaInputBill.setMTC_DocDueDate(outputModel.getBillDate());
                        // 表行
                        List<HanaInputBillDetail> hanaInputBillDetailList = new ArrayList<HanaInputBillDetail>();
                        hanaInputBill.setDetailList(hanaInputBillDetailList);

                        hanaInputBillDetailList.add(hanaInputBillDetail);

                        hanaInputBillMap.put(mtcCardCode, hanaInputBill);
                    } else {
                        // 存在
                        List<HanaInputBillDetail> hanaInputBillDetailList = hanaInputBillMap.get(mtcCardCode).getDetailList();
                        hanaInputBillDetailList.add(hanaInputBillDetail);
                    }
                }
                // }
            }
            // END HANA

        }

        storeMaterialMapper.updates(updateStoreMaterialModelList);
        outputDetailMapper.inserts(insertOutputDetailModelList);

        // START HANA
        if (isToHana) {
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            for (HanaInputBill hanaInputBill : hanaInputBillMap.values()) {
                if (!hanaInputBill.getDetailList().isEmpty()) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(hanaInputBill.getMTC_BranchID());
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaInputBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码:采购退货
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2030);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(hanaInputBill.getMTC_BaseEntry());
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
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaInputBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                    mtcITFCList.add(mtcITFC);
                }
            }

            if (!mtcITFCList.isEmpty()) {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
        }
        // END HANA

    }

    @Override
    public void editOutputAllot(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
        OutputModel outputModel = getBean(OutputModel.class, inputMap);
        outputModel.setStatus(SupplyChianConstants.OUTPUT_ALLOT_STATUS_PASS);
        outputModel.setFarmId(getFarmId());
        outputModel.setCompanyId(getCompanyId());
        outputModel.setBillCode(billCode);
        outputModel.setBillDate(Maps.getDate(inputMap, "allotDate"));
        outputModel.setEventCode(eventCode);
        outputModel.setOutputWarehouseId(Maps.getLong(inputMap, "allotOutputWarehouseId"));
        outputModel.setOutputerId(getEmployeeId());
        outputModel.setAllotDestination(Maps.getString(inputMap, "allotDestination"));
        if (SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_SAME_FARM.equals(Maps.getString(inputMap, "allotDestination"))) {
            outputModel.setOutputToFarmId(getFarmId());
            outputModel.setOutputToWarehouseId(Maps.getLong(inputMap, "allotWareHouseId"));
        } else {
            outputModel.setOutputToFarmId(Maps.getLong(inputMap, "allotFarmId"));
        }
        outputModel.setUserId(getEmployeeId());
        outputModel.setOaUsername(getOaUsername());
        outputModel.setCreateId(getEmployeeId());
        outputModel.setCreateDate(currentDate);
        outputModel.setPrintTimes(0L);
        outputMapper.insert(outputModel);

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(Maps.getLong(detailMap, "relatedId"), " AND RELATED_ID = ?");
            if (SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_SAME_FARM.equals(Maps.getString(inputMap, "allotDestination"))) {
                sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
            } else {
                sqlCon.addCondition(Maps.getLong(inputMap, "allotFarmId"), " AND FARM_ID = ?");
            }
            List<MaterialModel> materialModelList = getList(materialMapper, sqlCon);
            if (materialModelList.isEmpty()) {
                Thrower.throwException(SupplyChianException.ALLOT_FARM_HAS_NOT_THIS_MATERIAL, Maps.getLong(detailMap, "lineNumber"));
            }

            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(Maps.getDouble(detailMap, "outputQty")) < 0) {
                Thrower.throwException(SupplyChianException.QTY_IS_NOT_ENOUGH, Maps.getLong(detailMap, "lineNumber"));
            }
            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap,
                    "outputQty")));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            OutputDetailModel outputDetailModel = new OutputDetailModel();
            outputDetailModel.setStatus("1");
            outputDetailModel.setDeletedFlag("0");
            outputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            outputDetailModel.setFarmId(getFarmId());
            outputDetailModel.setCompanyId(getCompanyId());
            outputDetailModel.setOutputId(outputModel.getRowId());
            outputDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
            outputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            outputDetailModel.setMaterialOnly(Maps.getString(detailMap, "materialOnly"));
            outputDetailModel.setMaterialBatch(Maps.getString(detailMap, "materialBatch"));
            outputDetailModel.setPlanQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputQty(Maps.getDouble(detailMap, "outputQty"));
            outputDetailModel.setOutputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
            outputDetailModel.setOutputerId(getEmployeeId());
            outputDetailModel.setAllotDestination(Maps.getString(inputMap, "allotDestination"));
            if (SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_SAME_FARM.equals(Maps.getString(inputMap, "allotDestination"))) {
                outputDetailModel.setOutputToFarmId(getFarmId());
                outputDetailModel.setOutputToWarehouseId(Maps.getLong(inputMap, "allotWareHouseId"));
            } else {
                outputDetailModel.setOutputToFarmId(Maps.getLong(inputMap, "allotFarmId"));
            }
            outputDetailModel.setUserId(getEmployeeId());
            outputDetailModel.setBillCode(billCode);
            outputDetailModel.setBillDate(Maps.getDate(inputMap, "allotDate"));
            outputDetailModel.setEventCode(eventCode);
            outputDetailModel.setCreateId(getEmployeeId());
            outputDetailModel.setCreateDate(currentDate);
            insertOutputDetailModelList.add(outputDetailModel);

        }

        storeMaterialMapper.updates(updateStoreMaterialModelList);
        outputDetailMapper.inserts(insertOutputDetailModelList);

    }
    /* END 出库单 */

    /* yangy begin 仓库新增编辑 */
    @Override
    public BasePageInfo searchWarehouseSetting() throws Exception {
        setToPage();
        List<WarehouseModel> warehouseModels = warehouseMapper.searchToList(getFarmId());
        List<WarehouseMaterialTypeModel> warehouseMaterialTypeModels = null;
        if (warehouseModels != null && warehouseModels.size() > 0) {
            for (WarehouseModel warehouseModel : warehouseModels) {
                if (SupplyChianConstants.WAREHOUSE_STATUS_INITIALIZE.equals(warehouseModel.getStatus())) {
                    warehouseModel.set("statusName", SupplyChianConstants.WAREHOUSE_STATUS_INITIALIZE_NAME);
                } else if (SupplyChianConstants.WAREHOUSE_STATUS_USING.equals(warehouseModel.getStatus())) {
                    warehouseModel.set("statusName", SupplyChianConstants.WAREHOUSE_STATUS_USING_NAME);
                } else if (SupplyChianConstants.WAREHOUSE_STATUS_FORBIDDEN.equals(warehouseModel.getStatus())) {
                    warehouseModel.set("statusName", SupplyChianConstants.WAREHOUSE_STATUS_FORBIDDEN_NAME);
                }
                if (SupplyChianConstants.WAREHOUSE_TYPE_ENTITY.equals(warehouseModel.getWarehouseType())) {
                    warehouseModel.set("warehouseTypeName", SupplyChianConstants.WAREHOUSE_TYPE_ENTITY_NAME);
                } else if (SupplyChianConstants.WAREHOUSE_TYPE_FICTITIOUS.equals(warehouseModel.getWarehouseType())) {
                    warehouseModel.set("warehouseTypeName", SupplyChianConstants.WAREHOUSE_TYPE_FICTITIOUS_NAME);
                }
                SqlCon sqlCon = new SqlCon();
                sqlCon.addConditionWithNull(warehouseModel.getRowId(), " AND WAREHOUSE_ID = ?");
                warehouseMaterialTypeModels = getList(warehouseMaterialTypeMapper, sqlCon);
                if (warehouseMaterialTypeModels != null && warehouseMaterialTypeModels.size() > 0) {
                    StringBuilder materialType = new StringBuilder();
                    for (WarehouseMaterialTypeModel warehouseMaterialTypeModel : warehouseMaterialTypeModels) {
                        materialType.append(warehouseMaterialTypeModel.getMaterialType());
                        materialType.append(",");
                    }
                    String materialTypeString = materialType.substring(0, materialType.length() - 1);
                    warehouseModel.set("materialType", materialTypeString);
                    warehouseModel.set("materialFirstClass", materialTypeString);
                }

            }
        }
        return getPageInfo(warehouseModels);
    }

    @Override
    public List<Map<String, Object>> searchRoleByFarmIdToList() throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT T1.ROW_ID AS rowId, T1.ROLE_NAME AS roleName, T3.ROW_ID AS moduleId, T3.MODULE_NAME AS moduleName,T4.ROW_ID AS wechatModuleId,T4.MODULE_NAME AS wechatModuleName");
        sqlCon.addMainSql(" FROM CD_O_ROLE T1 INNER JOIN CD_O_TEMPLATE T2 ");
        sqlCon.addMainSql(" ON (T1.TEMPLATE_ID = T2.ROW_ID AND T2.DELETED_FLAG = '0' AND T2.STATUS = '1') ");
        sqlCon.addMainSql(" INNER JOIN CD_O_MODULE T3  ON (T2.MODULE_ID = T3.ROW_ID AND T3.DELETED_FLAG = '0'  AND T3.STATUS = '1')");
        sqlCon.addMainSql(" LEFT JOIN CD_O_MODULE T4  ON (T2.WECHAT_MODULE_ID = T4.ROW_ID AND T4.DELETED_FLAG = '0'  AND T4.STATUS = '1')");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1' AND INSTR('1,2,3',T1.ROLE_TYPE) <= 0 ");
        sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID =?");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", sqlCon.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public void editWarehouseSetting(Map<String, Object> inputMap) throws Exception {

        WarehouseModel warehouseModel = getBean(WarehouseModel.class, inputMap);
        if (warehouseModel.getWarehouseName() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "仓库名称不能为空！");
        }
        if (warehouseModel.getWarehouseType() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "仓库类型不能为空！");
        }
        if (warehouseModel.getOperationRole() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "管理角色不能为空！");
        }
        if (warehouseModel.getWarehouseCategory() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "仓库具体分类不能为空！");
        }
        WarehouseMaterialTypeModel warehouseMaterialTypeModel = getBean(WarehouseMaterialTypeModel.class, inputMap);
        if (warehouseMaterialTypeModel.getMaterialType() == null) {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "物料种类不能为空！");
        }
        long nameIsExist = ParamUtil.isExist("sc_m_warehouse", warehouseModel.getRowId(), new String[] { warehouseModel.getWarehouseName(), String
                .valueOf(getFarmId()) }, "WAREHOUSE_NAME, FARM_ID");
        if (nameIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, warehouseModel.getWarehouseName());
        }

        Date currentDate = new Date();

        String[] materialTypes = warehouseMaterialTypeModel.getMaterialType().split(",");
        // 新增
        if (warehouseModel.getRowId() == null) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT IFNULL(MAX(SORT_NBR),0)+1 AS sortNbr FROM SC_M_WAREHOUSE WHERE DELETED_FLAG = '0'");
            sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");

            Map<String, String> sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

            warehouseModel.setSortNbr(Maps.getLong(list.get(0), "sortNbr"));
            warehouseModel.setCompanyId(getCompanyId());
            warehouseModel.setFarmId(getFarmId());
            warehouseModel.setCreateId(getEmployeeId());
            warehouseModel.setCreateDate(currentDate);
            warehouseModel.setStatus(SupplyChianConstants.WAREHOUSE_STATUS_INITIALIZE);
            warehouseMapper.insert(warehouseModel);

            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());

            if (isToHana) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

                Map<String, String> companyInfoMap01 = CacheUtil.getData("HR_M_COMPANY", String.valueOf(getFarmId()));
                String companyName = Maps.getString(companyInfoMap01, "COMPANY_NAME");

                HanaWarehouse hanaWarehouse = new HanaWarehouse();
                // 分公司编号
                hanaWarehouse.setMTC_BranchID(mtcBranchID);
                // 分公司名称
                hanaWarehouse.setMTC_BranchName(mtcBranchID + " - " + companyName);
                // 仓库编号
                hanaWarehouse.setMTC_WhsCode(String.valueOf(warehouseModel.getRowId()));
                // 仓库名称
                hanaWarehouse.setMTC_WhsName(warehouseModel.getWarehouseName());

                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaWarehouse.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 业务代码:仓库
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(String.valueOf(warehouseModel.getRowId()));
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
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaWarehouse);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                hanaCommonService.insertMTC_ITFC(mtcITFC);

            }
            // END HANA

        } else {
            int warehouseUpdateFlag = warehouseMapper.update(warehouseModel);
            if (warehouseUpdateFlag == 1) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql("UPDATE SC_R_WAREHOUSE_MATERIAL_TYPE SET DELETED_FLAG = '1' WHERE ");
                sqlCon.addCondition(warehouseModel.getRowId(), " WAREHOUSE_ID = ? ");
                Map<String, String> sqlMap = new HashMap<String, String>();
                sqlMap.put("sql", sqlCon.getCondition());
                warehouseMaterialTypeMapper.operSql(sqlMap);
            }

            // START HANA
            boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());

            if (isToHana) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(getFarmId());
                String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

                Map<String, String> companyInfoMap01 = CacheUtil.getData("HR_M_COMPANY", String.valueOf(getFarmId()));
                String companyName = Maps.getString(companyInfoMap01, "COMPANY_NAME");

                HanaWarehouse hanaWarehouse = new HanaWarehouse();
                // 分公司编号
                hanaWarehouse.setMTC_BranchID(mtcBranchID);
                // 分公司名称
                hanaWarehouse.setMTC_BranchName(mtcBranchID + " - " + companyName);
                // 仓库编号
                hanaWarehouse.setMTC_WhsCode(String.valueOf(warehouseModel.getRowId()));
                // 仓库名称
                hanaWarehouse.setMTC_WhsName(warehouseModel.getWarehouseName());

                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaWarehouse.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 业务代码:仓库
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(String.valueOf(warehouseModel.getRowId()));
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
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaWarehouse);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                hanaCommonService.insertMTC_ITFC(mtcITFC);

            }
            // END HANA
        }

        // 新增或者编辑的时候，保存物料信息
        List<WarehouseMaterialTypeModel> warehouseMaterialTypeModels = new ArrayList<WarehouseMaterialTypeModel>();
        if (materialTypes != null && materialTypes.length > 0) {
            for (String materialType : materialTypes) {
                materialType = materialType.replace("[", "").replace("]", "");
                if (!("").equals(materialType) && !(" ").equals(materialType)) {
                    WarehouseMaterialTypeModel warehouseMaterialTypeModel_ = new WarehouseMaterialTypeModel();

                    warehouseMaterialTypeModel_.setWarehouseId(warehouseModel.getRowId());
                    warehouseMaterialTypeModel_.setDeletedFlag("0");
                    warehouseMaterialTypeModel_.setCreateId(getEmployeeId());
                    warehouseMaterialTypeModel_.setCreateDate(currentDate);
                    warehouseMaterialTypeModel_.setMaterialType(materialType.trim());
                    warehouseMaterialTypeModels.add(warehouseMaterialTypeModel_);
                }
            }
        }
        if (warehouseMaterialTypeModels != null && warehouseMaterialTypeModels.size() > 0) {
            warehouseMaterialTypeMapper.inserts(warehouseMaterialTypeModels);
        }

    }
    /* yangy END 仓库新增编辑 */

    /* yangy begin 删除仓库信息 */
    @Override
    public void deleteWarehouseSetting(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            for (long id : ids) {
                WarehouseModel warehouseModel = warehouseMapper.searchById(id);
                if (!SupplyChianConstants.WAREHOUSE_CATEGORY_SPERM.equals(warehouseModel.getWarehouseCategory())) {
                    // 不是精液仓库
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql("SELECT ROW_ID FROM SC_M_STORE_MATERIAL");
                    sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                    sqlCon.addCondition(id, " AND WAREHOUSE_ID = ? LIMIT 1");

                    List<Map<String, Object>> existsList = paramMapperSetSQL(paramMapper, sqlCon);
                    if (!existsList.isEmpty()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【" + warehouseModel.getWarehouseName() + "】仓库下已存在物料，无法删除！");
                    }
                } else {
                    // 精液仓库
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql("SELECT ROW_ID FROM PP_L_SPERM_INFO");
                    sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                    sqlCon.addCondition(id, " AND WAREHOUSE_ID = ? LIMIT 1");

                    List<Map<String,Object>> existsList = paramMapperSetSQL(paramMapper, sqlCon);
                    if (!existsList.isEmpty()) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【" + warehouseModel.getWarehouseName() + "】仓库下已存在精液，无法删除！");
                    }
                }
            }

            warehouseMapper.deletes(ids);
            setDeletes(warehouseMaterialTypeMapper, ids, "WAREHOUSE_ID");
            // SqlCon sqlCon = new SqlCon();
            // sqlCon.addMainSql(" SELECT COUNT(*) warehouseCount FROM sc_m_warehouse T0 ");
            // sqlCon.addMainSql(" INNER JOIN sc_m_store_material T1 ON (T0.ROW_ID = T1.WAREHOUSE_ID AND T1.DELETED_FLAG= '0'");
            // sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?)");
            // sqlCon.addCondition(StringUtil.arrayToString(ids), " WHERE T0.DELETED_FLAG= '0' AND T0.ROW_ID IN ? ", false, true);
            // sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ? ");
            // Map<String, String> sqlMap = new HashMap<String, String>();
            // sqlMap.put("sql", sqlCon.getCondition());
            // List<Map<String, String>> list = warehouseMapper.getInfos(sqlMap);
            // if (list != null && !list.isEmpty()) {
            // if (Maps.getLong(list.get(0), "warehouseCount") > 0) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该仓库下已有物料！");
            // } else {
                    warehouseMapper.deletes(ids);
                    setDeletes(warehouseMaterialTypeMapper, ids, "WAREHOUSE_ID");
            // }
            // }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择仓库！");
        }

    }
    /* yangy END 删除仓库信息 */

    /* yangy begin 禁用仓库信息 */
    @Override
    public void editForbiddenWarehouseSetting(long[] ids) throws Exception {
        if (ids != null && ids.length > 0) {
            // SqlCon sqlCon = new SqlCon();
            // sqlCon.addMainSql(" SELECT COUNT(*) warehouseCount FROM sc_m_warehouse T0 ");
            // sqlCon.addMainSql(" INNER JOIN sc_m_store_material T1 ON T0.ROW_ID = T1.WAREHOUSE_ID AND T1.DELETED_FLAG=0 ");
            // sqlCon.addCondition(getFarmId(), " AND T1.FARM_ID = ?");
            // sqlCon.addCondition(StringUtil.arrayToString(ids), " WHERE T0.DELETED_FLAG=0 AND T0.ROW_ID IN ? ", false, true);
            // sqlCon.addCondition(getFarmId(), " AND T0.FARM_ID = ? ");
            // Map<String, String> sqlMap = new HashMap<String, String>();
            // sqlMap.put("sql", sqlCon.getCondition());
            // List<Map<String, String>> list = warehouseMapper.getInfos(sqlMap);
            // if (list != null && !list.isEmpty()) {
            // if (Maps.getLong(list.get(0), "warehouseCount") > 0) {
            // Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "该仓库下已有物料！");
            // } else {
            warehouseMapper.forbiddens(ids);
            // }
            // }
        } else {
            Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "请选择仓库！");
        }
    }
    /* yangy end 禁用仓库信息 */

    /* yangy begin 启用仓库信息 */
    @Override
    public void editUseWarehouseSetting(long[] ids) throws Exception {
        warehouseMapper.uses(ids);
    }
    /* yangy end 启用仓库信息 */

    /* yangy start 查询仓库 */
    @Override
    public List<WarehouseModel> searchWarehouseToList() throws Exception {
        SqlCon sql = new SqlCon();
        sql.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        List<WarehouseModel> warehouseModels = getList(warehouseMapper, sql);
        return warehouseModels;
    }
    /* yangy end 查询仓库 */

    /* yangy start 初始化 保存仓库物料信息 */
    @Override
    public void editWarehouseStoreMaterial(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList != null && !detailList.isEmpty()) {
            Date currentDate = new Date();

            List<InputDetailModel> inputDetailModelList = new ArrayList<InputDetailModel>();
            List<PurchaseDetailModel> insertPurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
            List<StoreMaterialModel> insertStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
            List<MonthAccountModel> insertMonthAccountModelList = new ArrayList<MonthAccountModel>();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            String eventCode = SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE;

            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "productionDate") && Maps.isEmpty(detailMap, "effectiveDate")) {
                    Thrower.throwException(SupplyChianException.EFFECTIVE_DATE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
                }
                // 做到全平台共用，所以选用10000账套
                String materialOnly = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_ONLY_NUMBER, getEmployeeId(), get10000CompanyId(),
                        get10000FarmId());
                String materialBatch = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_BATCH_NUMBER, getEmployeeId(), getCompanyId(),
                        getFarmId());

                InputDetailModel inputDetailModel = new InputDetailModel();
                inputDetailModel.setStatus(CommonConstants.STATUS);
                inputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                inputDetailModel.setFarmId(getFarmId());
                inputDetailModel.setCompanyId(getCompanyId());
                inputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                inputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                inputDetailModel.setMaterialOnly(materialOnly);
                inputDetailModel.setMaterialBatch(materialBatch);
                inputDetailModel.setInputQty(Maps.getDouble(detailMap, "actualQty"));
                inputDetailModel.setInputWarehouseId(Maps.getLong(inputMap, "warehouseId"));
                inputDetailModel.setInputerId(getEmployeeId());
                inputDetailModel.setBillDate(currentDate);
                inputDetailModel.setEventCode(eventCode);
                inputDetailModel.setCreateId(getEmployeeId());
                inputDetailModel.setCreateDate(currentDate);
                inputDetailModelList.add(inputDetailModel);

                PurchaseDetailModel purchaseDetailModel = new PurchaseDetailModel();
                purchaseDetailModel.setStatus(CommonConstants.STATUS);
                purchaseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                purchaseDetailModel.setFarmId(getFarmId());
                purchaseDetailModel.setCompanyId(getCompanyId());
                purchaseDetailModel.setFreeLn(0L);
                purchaseDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                purchaseDetailModel.setMaterialOnly(materialOnly);
                purchaseDetailModel.setMaterialSplit(SupplyChianConstants.MATERIAL_SPLIT_NUMBER_START);
                purchaseDetailModel.setActualPrice(Maps.getDouble(detailMap, "initActualPrice", 0D));
                purchaseDetailModel.setPassQty(Maps.getDouble(detailMap, "actualQty"));
                purchaseDetailModel.setPurchaseQty(Maps.getDouble(detailMap, "actualQty"));
                purchaseDetailModel.setIsPackage("N");
                purchaseDetailModel.setArriveQty(Maps.getDouble(detailMap, "actualQty"));
                purchaseDetailModel.setGroupOrSelf(SupplyChianConstants.GROUP_OR_SELF_SELF);
                purchaseDetailModel.setSupplierId(Maps.getLong(detailMap, "supplierId"));
                purchaseDetailModel.setRequireFarm(getFarmId());
                purchaseDetailModel.setBillDate(currentDate);
                purchaseDetailModel.setEventCode(eventCode);
                purchaseDetailModel.setCreateId(getEmployeeId());
                purchaseDetailModel.setCreateDate(currentDate);
                insertPurchaseDetailModelList.add(purchaseDetailModel);

                StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                storeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                storeMaterialModel.setFarmId(getFarmId());
                storeMaterialModel.setCompanyId(getCompanyId());
                storeMaterialModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                storeMaterialModel.setMaterialOnly(materialOnly);
                storeMaterialModel.setMaterialBatch(materialBatch);
                storeMaterialModel.setProductionDate(Maps.getDate(detailMap, "productionDate"));
                storeMaterialModel.setEffectiveDate(Maps.getDate(detailMap, "effectiveDate"));
                storeMaterialModel.setWarehouseId(Maps.getLong(inputMap, "warehouseId"));
                storeMaterialModel.setActualQty(Maps.getDouble(detailMap, "actualQty"));
                storeMaterialModel.setCreateDate(currentDate);
                insertStoreMaterialModelList.add(storeMaterialModel);

                MonthAccountModel monthAccountModel = new MonthAccountModel();
                monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                monthAccountModel.setFarmId(getFarmId());
                monthAccountModel.setCompanyId(getCompanyId());
                monthAccountModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                monthAccountModel.setMaterialOnly(materialOnly);
                monthAccountModel.setMaterialBatch(materialBatch);
                monthAccountModel.setWarehouseId(Maps.getLong(inputMap, "warehouseId"));
                monthAccountModel.setStartQty(Maps.getDouble(detailMap, "actualQty"));
                monthAccountModel.setStartDate(calendar.getTime());
                monthAccountModel.setLockFlag("N");
                monthAccountModel.setEventCode(eventCode);
                monthAccountModel.setCreateId(getEmployeeId());
                monthAccountModel.setCreateDate(currentDate);
                insertMonthAccountModelList.add(monthAccountModel);
            }

            inputDetailMapper.inserts(inputDetailModelList);

            purchaseDetailMapper.inserts(insertPurchaseDetailModelList);

            storeMaterialMapper.inserts(insertStoreMaterialModelList);

            monthAccountMapper.inserts(insertMonthAccountModelList);
        }

        WarehouseModel warehouseModel = new WarehouseModel();
        warehouseModel.setRowId(Maps.getLong(inputMap, "warehouseId"));
        warehouseModel.setStatus(SupplyChianConstants.WAREHOUSE_STATUS_USING);
        warehouseMapper.update(warehouseModel);
    }

    @Override
    public void editDefaultHouse(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList != null && !detailList.isEmpty()) {
            List<MaterialModel> materialModelList = new ArrayList<MaterialModel>();
            for (Map<String, Object> detailMap : detailList) {
                MaterialModel materialModel = new MaterialModel();
                materialModel.setRowId(Maps.getLong(detailMap, "materialId"));
                materialModel.setDefaultWarehouse(Maps.getLong(inputMap, "warehouseId"));
                materialModelList.add(materialModel);
            }
            materialMapper.updates(materialModelList);
        }
    }

    /* yangy end 保存仓库物料信息 */

    /* yangy start 根据仓库id查询物料种类 */
    @Override
    public List<WarehouseMaterialTypeModel> searchMaterialTypeByWarehouseToList(Map<String, Object> inputMap) throws Exception {
        Long warehouseId = Maps.getLong(inputMap, "warehouseId");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(warehouseId, " AND WAREHOUSE_ID = ?");
        List<WarehouseMaterialTypeModel> warehouseMaterialTypeModels = getList(warehouseMaterialTypeMapper, sqlCon);
        if (warehouseMaterialTypeModels != null && !warehouseMaterialTypeModels.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < warehouseMaterialTypeModels.size(); i++) {
                WarehouseMaterialTypeModel warehouseMaterialTypeModel = warehouseMaterialTypeModels.get(i);
                warehouseMaterialTypeModel.set("materialFirstClass", warehouseMaterialTypeModel.getMaterialType());
                warehouseMaterialTypeModel.set("materialFirstClassName", CacheUtil.getName(Maps.getString(warehouseMaterialTypeModel.getData(),
                        "materialFirstClass"), NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));

                stringBuffer.append(warehouseMaterialTypeModel.getMaterialType());
                if (i != warehouseMaterialTypeModels.size() - 1) {
                    stringBuffer.append(",");
                }
            }
            // 全部（将【允许的类型】全部放入，而不是默认的全部类型）
            WarehouseMaterialTypeModel warehouseMaterialTypeModel = new WarehouseMaterialTypeModel();
            warehouseMaterialTypeModel.set("materialFirstClassName", "全部");
            warehouseMaterialTypeModel.set("materialFirstClass", stringBuffer.toString());
            warehouseMaterialTypeModel.setMaterialType(stringBuffer.toString());
            warehouseMaterialTypeModels.add(0, warehouseMaterialTypeModel);
        }
        return warehouseMaterialTypeModels;
    }
    /* yangy end 根据仓库id查询物料种类 */

    /* START 仓管发料 */
    @Override
    public BasePageInfo searchOutputUseToPage(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.STATUS AS status, T1.BILL_DATE AS billDate, T1.OUTPUT_USE_NUMBER AS outputUseNumber");
        sqlCon.addMainSql(", (SELECT EMPLOYEE_NAME FROM HR_O_EMPLOYEE WHERE DELETED_FLAG = '0' AND ROW_ID = T2.USER_ID LIMIT 1) AS userName");
        sqlCon.addMainSql(", MAX(T2.USER_ID) AS userId, T1.NOTES AS notes, T1.OUTPUT_WAREHOUSE_ID AS outputWarehouseId");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T1 INNER JOIN SC_M_BILL_OUTPUT_DETAIL T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.FARM_ID = T2.FARM_ID AND T1.ROW_ID = T2.OUTPUT_ID");
        sqlCon.addMainSql(" AND T2.OUTPUTER_ID IS NULL)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND (T1.FARM_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " OR T1.COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

        } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");

        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
        }

        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_OUTPUT_USE, " AND T1.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND (T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " OR T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, " OR T1.STATUS = ?)");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_WAREHOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND ROW_ID = T1.OUTPUT_WAREHOUSE_ID");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_R_EMPLOYEE_ROLE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND CD_R_EMPLOYEE_ROLE.ROLE_ID = SC_M_WAREHOUSE.OPERATION_ROLE");
        sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ?))");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        setToPage();
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.put("billDate", TimeUtil.format(Maps.getString(map, "billDate"), TimeUtil.DATE_FORMAT));
            map.put("outputWarehouseName", CacheUtil.getName(Maps.getString(map, "outputWarehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            map.put("statusName", SupplyChianConstants.OUTPUT_USE_STATUS_MAP.get(Maps.getString(map, "status")));
        }
        return getPageInfo(list);
    }

    @Override
    public List<Map<String, Object>> searchMaterialByMaterialIdToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.rowId, T1.materialId, T1.existsQty, T1.effectiveDate, T1.productionDate, T1.materialOnly, T1.materialBatch");
        sqlCon.addMainSql(", T1.warehouseId, T1.actualPrice, T1.freeLn FROM (");
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, MATERIAL_ID AS materialId, ACTUAL_QTY AS existsQty");
        sqlCon.addMainSql(" , PRODUCTION_DATE AS productionDate, EFFECTIVE_DATE AS effectiveDate");
        sqlCon.addMainSql(", MATERIAL_ONLY AS materialOnly, MATERIAL_BATCH AS materialBatch, WAREHOUSE_ID AS warehouseId");
        sqlCon.addMainSql(", (SELECT ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = SC_M_STORE_MATERIAL.MATERIAL_ONLY LIMIT 1) AS actualPrice");
        sqlCon.addMainSql(", (SELECT FREE_LN FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = SC_M_STORE_MATERIAL.MATERIAL_ONLY LIMIT 1) AS freeLn");
        sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND ACTUAL_QTY > 0");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addConditionWithNull(Maps.getLongClass(inputMap, "warehouseId"), " AND WAREHOUSE_ID = ?");
        if ("true".equals(Maps.getString(inputMap, "roleFilterFlag"))) {
            sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_WAREHOUSE WHERE DELETED_FLAG = '0' AND ROW_ID = SC_M_STORE_MATERIAL.WAREHOUSE_ID");
            sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG = '0' AND ROLE_ID = SC_M_WAREHOUSE.OPERATION_ROLE");
            sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ?");
            sqlCon.addMainSql(" LIMIT 1) LIMIT 1)");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "filterIds"), " AND ROW_ID NOT IN ?", false, true);
        sqlCon.addCondition(Maps.getString(inputMap, "materialIds"), " AND MATERIAL_ID IN ?", false, true);
        sqlCon.addMainSql(") T1 ORDER BY T1.productionDate, T1.materialId, T1.effectiveDate, T1.freeLn, T1.rowId");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            map.put("existsQtyName", Maps.getString(map, "existsQty") + Maps.getString(map, "unit"));
            map.put("outputMinQtyName", Maps.getString(map, "outputMinQty") + Maps.getString(map, "unit"));
            map.put("actualPriceName", Maps.getDouble(map, "actualPrice") + "元");
            map.put("warehouseName", CacheUtil.getName(Maps.getString(map, "warehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            if (Maps.getLong(map, "freeLn") == 0D) {
                map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
            } else {
                map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchGiveOutMaterialByMaterialIdToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.rowId, T1.materialId, T1.existsQty, T1.effectiveDate, T1.productionDate, T1.materialOnly, T1.materialBatch");
        sqlCon.addMainSql(", T1.warehouseId, T1.actualPrice, T1.freeLn, T1.inputDate FROM (");
        sqlCon.addMainSql(" SELECT ROW_ID AS rowId, MATERIAL_ID AS materialId, ACTUAL_QTY AS existsQty");
        sqlCon.addMainSql(", PRODUCTION_DATE AS productionDate, EFFECTIVE_DATE AS effectiveDate");
        sqlCon.addMainSql(", MATERIAL_ONLY AS materialOnly, MATERIAL_BATCH AS materialBatch, WAREHOUSE_ID AS warehouseId");
        sqlCon.addMainSql(", (SELECT ACTUAL_PRICE FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = SC_M_STORE_MATERIAL.MATERIAL_ONLY LIMIT 1) AS actualPrice");
        sqlCon.addMainSql(", (SELECT FREE_LN FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = SC_M_STORE_MATERIAL.MATERIAL_ONLY LIMIT 1) AS freeLn");
        sqlCon.addMainSql(", (SELECT MIN(BILL_DATE) FROM SC_M_BILL_INPUT_DETAIL WHERE DELETED_FLAG = '0' AND STATUS = '1'");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE, " AND (EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " OR EVENT_CODE = ?)");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = SC_M_STORE_MATERIAL.MATERIAL_ONLY AND MATERIAL_BATCH = SC_M_STORE_MATERIAL.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND INPUT_WAREHOUSE_ID = SC_M_STORE_MATERIAL.WAREHOUSE_ID");
        sqlCon.addMainSql(" LIMIT 1) AS inputDate ");
        sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND ACTUAL_QTY > 0");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addConditionWithNull(Maps.getLongClass(inputMap, "warehouseId"), " AND WAREHOUSE_ID = ?");
        if ("true".equals(Maps.getString(inputMap, "roleFilterFlag"))) {
            sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_WAREHOUSE WHERE DELETED_FLAG = '0' AND ROW_ID = SC_M_STORE_MATERIAL.WAREHOUSE_ID");
            sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG = '0' AND ROLE_ID = SC_M_WAREHOUSE.OPERATION_ROLE");
            sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ?");
            sqlCon.addMainSql(" LIMIT 1) LIMIT 1)");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "filterIds"), " AND ROW_ID NOT IN ?", false, true);
        sqlCon.addCondition(Maps.getLongClass(inputMap, "materialId"), " AND MATERIAL_ID = ?");
        sqlCon.addMainSql(") T1 ORDER BY T1.inputDate, T1.productionDate, T1.effectiveDate, T1.freeLn, T1.rowId DESC");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            if (!Maps.isEmpty(map, "productionDate")) {
                map.put("effectiveDate", TimeUtil.format(Maps.getString(map, "productionDate"), TimeUtil.DATE_FORMAT));
            } else {
                map.put("effectiveDate", TimeUtil.format(Maps.getString(map, "effectiveDate"), TimeUtil.DATE_FORMAT));
            }
            map.put("existsQtyName", Maps.getString(map, "existsQty") + Maps.getString(map, "unit"));
            map.put("outputMinQtyName", Maps.getString(map, "outputMinQty") + Maps.getString(map, "unit"));
            map.put("actualPriceName", Maps.getDouble(map, "actualPrice") + "元");
            map.put("warehouseName", CacheUtil.getName(Maps.getString(map, "warehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            if (Maps.getLong(map, "freeLn") == 0D) {
                map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
            } else {
                map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
            }
        }

        return list;
    }

    @Override
    public void editGiveOutTruePrepared(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        // 同一物料合计数
        Map<Long, Double> materialCount = new HashMap<Long, Double>();
        for (Map<String, Object> detailMap : detailList) {
            BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "outputQty"), Maps.getDouble(detailMap, "outputMinQty"));
            if (remainder[1].doubleValue() != 0D) {
                Thrower.throwException(SupplyChianException.OUTPUT_MIN_QTY_ERROR, Maps.getLong(detailMap, "lineNumber"), Maps.getString(detailMap,
                        "materialName"), Maps.getDouble(detailMap, "outputMinQty"));
            }

            if (Maps.isEmpty(detailMap, "effectiveDateId")) {
                Thrower.throwException(SupplyChianException.EFFECTIVE_DATE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
            }

            if (Maps.isEmpty(materialCount, Maps.getLong(detailMap, "effectiveDateId"))) {
                // 如果没有重复 materialId
                materialCount.put(Maps.getLong(detailMap, "effectiveDateId"), Maps.getDouble(detailMap, "outputQty"));
            } else {
                // 如果重复 materialId
                Double outputQty = materialCount.get(Maps.getLong(detailMap, "effectiveDateId"));
                materialCount.put(Maps.getLong(detailMap, "effectiveDateId"), bigDecimalAdd(outputQty, Maps.getDouble(detailMap, "outputQty")));
            }
        }

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        //
        Map<Long, Map<String, String>> materialOnlyAndMaterialBatchMapList = new HashMap<Long, Map<String, String>>();

        for (Map.Entry<Long, Double> entry : materialCount.entrySet()) {
            // CHECK 出库数量是否大于分配的数量
            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(entry.getKey(), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(entry.getValue()) < 0) {
                Map<String, String> materialInfoMap = CacheUtil.getMaterialInfo(String.valueOf(updateStoreMaterialModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                Thrower.throwException(SupplyChianException.OUTPUT_QTY_MORE_THAN_EXISTS_QTY, Maps.getString(materialInfoMap, "materialName"),
                        bigDecimalSubtract(entry.getValue(), updateStoreMaterialModel.getActualQty()));
            }

            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), entry.getValue()));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            SqlCon inputDateSqlCon = new SqlCon();
            inputDateSqlCon.addMainSql("SELECT MIN(BILL_DATE) AS inputDate FROM SC_M_BILL_INPUT_DETAIL");
            inputDateSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE, " AND (EVENT_CODE = ?");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR EVENT_CODE = ?");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR EVENT_CODE = ?");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " OR EVENT_CODE = ?)");
            inputDateSqlCon.addCondition(updateStoreMaterialModel.getMaterialOnly(), " AND MATERIAL_ONLY = ?");
            inputDateSqlCon.addCondition(updateStoreMaterialModel.getMaterialBatch(), " AND MATERIAL_BATCH = ?");
            inputDateSqlCon.addCondition(updateStoreMaterialModel.getWarehouseId(), " AND INPUT_WAREHOUSE_ID = ?");

            Map<String, String> inputDateSqlMap = new HashMap<String, String>();
            inputDateSqlMap.put("sql", inputDateSqlCon.getCondition());

            Map<String, Object> inputDateMap = paramMapper.getObjectInfos(inputDateSqlMap).get(0);

            Map<String, String> materialOnlyAndMaterialBatchMap = new HashMap<String, String>();
            materialOnlyAndMaterialBatchMap.put("materialOnly", updateStoreMaterialModel.getMaterialOnly());
            materialOnlyAndMaterialBatchMap.put("materialBatch", updateStoreMaterialModel.getMaterialBatch());
            materialOnlyAndMaterialBatchMap.put("inputDate", Maps.getString(inputDateMap, "inputDate"));
            materialOnlyAndMaterialBatchMapList.put(entry.getKey(), materialOnlyAndMaterialBatchMap);
        }

        List<OutputDetailModel> updateOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            OutputDetailModel outputDetailModel = outputDetailMapper.searchById(Maps.getLong(detailMap, "detailRowId"));
            if (SupplyChianConstants.OUTPUT_USE_STATUS_WAITING.equals(outputDetailModel.getStatus())) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "内容已经提交，请勿二次提交");
            }
            Map<String, String> materialOnlyAndMaterialBatchMap = materialOnlyAndMaterialBatchMapList.get(Maps.getLong(detailMap, "effectiveDateId"));

            Date billDate = Maps.getDate(detailMap, "billDate");
            Date inputDate = Maps.getDate(materialOnlyAndMaterialBatchMap, "inputDate");

            // 入库日期必须小于等于出库日期
            if (inputDate.after(billDate)) {
                Thrower.throwException(SupplyChianException.INPUT_DATE_NOT_AFTER_OUTPUT_DATE, Maps.getLong(detailMap, "lineNumber"));
            }

            OutputDetailModel updateOutputDetailModel = new OutputDetailModel();
            updateOutputDetailModel.setRowId(Maps.getLong(detailMap, "detailRowId"));
            updateOutputDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING);
            updateOutputDetailModel.setMaterialOnly(Maps.getString(materialOnlyAndMaterialBatchMap, "materialOnly"));
            updateOutputDetailModel.setMaterialBatch(Maps.getString(materialOnlyAndMaterialBatchMap, "materialBatch"));
            updateOutputDetailModel.setOutputQty(Maps.getDouble(detailMap, "outputQty"));
            updateOutputDetailModelList.add(updateOutputDetailModel);

        }

        storeMaterialMapper.updates(updateStoreMaterialModelList);

        outputDetailMapper.updates(updateOutputDetailModelList);

        OutputModel outputModel = new OutputModel();
        outputModel.setRowId(Maps.getLong(inputMap, "rowId"));
        outputModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING);
        outputMapper.update(outputModel);
    }

    @Override
    public void editGiveOutTrue(long[] ids) throws Exception {
        Date currentDate = new Date();
        String idsString = longArrayListToString(ids);

        Map<String, Object> updateStatusMap = new HashMap<String, Object>();
        updateStatusMap.put("status", SupplyChianConstants.OUTPUT_USE_STATUS_USE);
        updateStatusMap.put("outputerId", getEmployeeId());
        updateStatusMap.put("outputUseDate", currentDate);
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(idsString, " ROW_ID IN ?", false, true);
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " AND (STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, " OR STATUS = ?)");
        updateStatusMap.put("condition", sqlCon.getCondition());
        int i = outputMapper.updateStatus(updateStatusMap);
        if (i != ids.length) {
            Thrower.throwException(SupplyChianException.INFOS_IS_ERROR);
        }

        SqlCon outputDetailSqlCon = new SqlCon();
        outputDetailSqlCon.addCondition(idsString, " AND OUTPUT_ID IN ? ", false, true);
        outputDetailSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " AND STATUS = ?");
        List<OutputDetailModel> outputDetailModelList = getList(outputDetailMapper, outputDetailSqlCon);
        for (OutputDetailModel outputDetailModel : outputDetailModelList) {
            outputDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
            outputDetailModel.setOutputerId(getEmployeeId());
            outputDetailModel.setOutputUseDate(currentDate);
        }

        outputDetailMapper.updates(outputDetailModelList);

        // 更新出库单状态
        updateOutputStoreStatus(idsString, currentDate);
    }

    @Override
    public void editGiveOutFalse(long[] ids) throws Exception {
        Date currentDate = new Date();
        String idsString = longArrayListToString(ids);

        Map<String, Object> updateStatusMap = new HashMap<String, Object>();
        updateStatusMap.put("status", SupplyChianConstants.OUTPUT_USE_STATUS_UNUSE);
        updateStatusMap.put("outputerId", getEmployeeId());
        updateStatusMap.put("outputUseDate", currentDate);
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(idsString, " ROW_ID IN ?", false, true);
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND (STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " OR STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, " OR STATUS = ?)");
        updateStatusMap.put("condition", sqlCon.getCondition());
        int i = outputMapper.updateStatus(updateStatusMap);
        if (i != ids.length) {
            Thrower.throwException(SupplyChianException.INFOS_IS_ERROR);
        }

        SqlCon outputDetailSqlCon = new SqlCon();
        outputDetailSqlCon.addCondition(idsString, " AND OUTPUT_ID IN ?", false, true);
        outputDetailSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND (STATUS = ?");
        outputDetailSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " OR STATUS = ?)");
        List<OutputDetailModel> outputDetailModelList = getList(outputDetailMapper, outputDetailSqlCon);

        List<Map<String, Object>> onlyAndBatchMapList = new ArrayList<Map<String, Object>>();

        for (OutputDetailModel outputDetailModel : outputDetailModelList) {
            outputDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_UNUSE);
            outputDetailModel.setOutputerId(getEmployeeId());
            outputDetailModel.setOutputUseDate(currentDate);

            if (outputDetailModel.getMaterialOnly() != null && outputDetailModel.getMaterialBatch() != null) {
                String onlyAndBatch = outputDetailModel.getMaterialOnly() + outputDetailModel.getMaterialBatch();
                boolean notExistsFlag = true;
                for (Map<String, Object> onlyAndBatchMap : onlyAndBatchMapList) {
                    if (onlyAndBatch.equals(Maps.getString(onlyAndBatchMap, "onlyAndBatch"))) {
                        onlyAndBatchMap.put("qty", bigDecimalAdd(Maps.getDouble(onlyAndBatchMap, "qty"), outputDetailModel.getOutputQty()));
                        notExistsFlag = false;
                        break;
                    }
                }

                if (notExistsFlag) {
                    Map<String, Object> onlyAndBatchMap = new HashMap<String, Object>();
                    onlyAndBatchMap.put("onlyAndBatch", onlyAndBatch);
                    onlyAndBatchMap.put("materialOnly", outputDetailModel.getMaterialOnly());
                    onlyAndBatchMap.put("materialBatch", outputDetailModel.getMaterialBatch());
                    onlyAndBatchMap.put("qty", outputDetailModel.getOutputQty());
                    onlyAndBatchMap.put("warehouseId", outputDetailModel.getOutputWarehouseId());
                    onlyAndBatchMap.put("farmId", getFarmId());
                    onlyAndBatchMapList.add(onlyAndBatchMap);
                }
            }
        }

        if (onlyAndBatchMapList != null && !onlyAndBatchMapList.isEmpty()) {
            storeMaterialMapper.updateActualQtyToStore(onlyAndBatchMapList);
        }

        outputDetailMapper.updates(outputDetailModelList);

        // 更新出库单状态
        updateOutputStoreStatus(idsString, currentDate);
    }
    /* END 仓管发料 */

    /* START 仓管发料（明细显示） */
    @Override
    public List<Map<String, Object>> searchOutputUseDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS billRowId, T2.ROW_ID AS detailRowId, T2.STATUS AS status, T1.OUTPUT_USE_NUMBER AS outputUseNumber");
        sqlCon.addMainSql(", T2.USER_ID AS userId, T2.BILL_DATE AS billDate, T2.OUTPUT_DESTINATION AS outputDestination");
        sqlCon.addMainSql(", T4.EMPLOYEE_NAME AS userName");
        sqlCon.addMainSql(", T2.OUTPUT_DEPT_ID AS outputDeptId, T3.DEPT_NAME AS outputDeptName");
        sqlCon.addMainSql(", T2.OUTPUT_HOUSE_ID AS outputHouseId, T2.OUTPUT_SWINERY_ID AS outputSwineryId");
        sqlCon.addMainSql(", T2.PIG_QTY AS pigQty, T2.MATERIAL_ID AS materialId, T2.OUTPUT_QTY AS outputQty");
        sqlCon.addMainSql(", T2.OUTPUT_WAREHOUSE_ID AS outputWarehouseId, T2.MATERIAL_ONLY AS materialOnly, T2.MATERIAL_BATCH AS materialBatch");
        sqlCon.addMainSql(", (SELECT PRODUCTION_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY AND MATERIAL_BATCH = T2.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T2.FARM_ID LIMIT 1) AS productionDate");
        sqlCon.addMainSql(", (SELECT EFFECTIVE_DATE FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY AND MATERIAL_BATCH = T2.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T2.FARM_ID LIMIT 1) AS effectiveDate");
        sqlCon.addMainSql(", (SELECT ROW_ID FROM SC_M_STORE_MATERIAL WHERE DELETED_FLAG = '0' AND FARM_ID = T2.FARM_ID");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY AND MATERIAL_BATCH = T2.MATERIAL_BATCH");
        sqlCon.addMainSql(" AND FARM_ID = T2.FARM_ID LIMIT 1) AS effectiveDateId");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_OUTPUT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.OUTPUT_ID AND T1.FARM_ID = T2.FARM_ID)");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_O_DEPT T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T2.OUTPUT_DEPT_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_O_EMPLOYEE T4");
        sqlCon.addMainSql(" ON(T4.DELETED_FLAG = '0' AND T2.USER_ID = T4.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "outputId"), " AND T1.ROW_ID = ?");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_WAREHOUSE WHERE ROW_ID = T2.OUTPUT_WAREHOUSE_ID");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM CD_R_EMPLOYEE_ROLE WHERE DELETED_FLAG = '0' AND ROLE_ID = SC_M_WAREHOUSE.OPERATION_ROLE");
        sqlCon.addCondition(getEmployeeId(), " AND EMPLOYEE_ID = ?");
        sqlCon.addMainSql(" LIMIT 1)LIMIT 1)");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND (T1.FARM_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " OR T1.COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你所在不是仓库总公司，请切换到总公司。。。");

        } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");

        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
        }

        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_OUTPUT_USE, " AND T2.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND (T2.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " OR T2.STATUS = ?)");
        sqlCon.addMainSql(" ORDER BY T1.OUTPUT_USE_NUMBER,T2.OUTPUT_ID,T2.OUTPUT_LN,T2.ROW_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> supplychianBillDetailList = paramMapper.getObjectInfos(sqlMap);

        for (Map<String, Object> supplychianBillDetail : supplychianBillDetailList) {
            // supplychianBillDetail.put("userName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "userId"), NameEnum.EMPLOYEE_NAME));
            supplychianBillDetail.put("outputDestinationName", SupplyChianConstants.OUTPUT_DESTINATION_MAP.get(Maps.getString(supplychianBillDetail,
                    "outputDestination")));
            supplychianBillDetail.put("outputHouseName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "outputHouseId"),
                    NameEnum.HOUSE_NAME));
            supplychianBillDetail.put("outputSwineryName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "outputSwineryId"),
                    NameEnum.SWINERY_NAME));
            supplychianBillDetail.putAll(CacheUtil.getMaterialInfo(Maps.getString(supplychianBillDetail, "materialId"),
                    MaterialInfoEnum.MATERIAL_INFO));
            supplychianBillDetail.put("materialFirstClassName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "materialFirstClass"),
                    NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
            supplychianBillDetail.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(supplychianBillDetail,
                    "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(supplychianBillDetail, "materialFirstClass")));
            supplychianBillDetail.put("outputWarehouseName", CacheUtil.getName(Maps.getString(supplychianBillDetail, "outputWarehouseId"),
                    xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(supplychianBillDetail, "supplierId"));
            supplychianBillDetail.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            supplychianBillDetail.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            supplychianBillDetail.put("outputQtyUnit", Maps.getString(supplychianBillDetail, "outputQty") + Maps.getString(supplychianBillDetail,
                    "unit"));
            if (Maps.isEmpty(supplychianBillDetail, "effectiveDate") && !Maps.isEmpty(supplychianBillDetail, "productionDate")) {
                supplychianBillDetail.put("effectiveDate", TimeUtil.format(Maps.getDate(supplychianBillDetail, "productionDate"),
                        TimeUtil.DATE_FORMAT));
            }
        }

        return supplychianBillDetailList;
    }

    @Override
    public void editGiveOutDetailTrue(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        List<Long> billRowIdIdList = new ArrayList<Long>();
        Set<Long> billRowIdIdSet = new HashSet<Long>();
        // 同一物料合计数
        Map<Long, Double> materialCount = new HashMap<Long, Double>();
        for (Map<String, Object> detailMap : detailList) {
            BigDecimal[] remainder = bigDecimalDivideAndRemainder(Maps.getDouble(detailMap, "outputQty"), Maps.getDouble(detailMap, "outputMinQty"));
            if (remainder[1].doubleValue() != 0D) {
                Thrower.throwException(SupplyChianException.OUTPUT_MIN_QTY_ERROR, Maps.getLong(detailMap, "lineNumber"), Maps.getString(detailMap,
                        "materialName"), Maps.getDouble(detailMap, "outputMinQty"));
            }

            if (Maps.isEmpty(detailMap, "effectiveDateId")) {
                Thrower.throwException(SupplyChianException.EFFECTIVE_DATE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
            }

            // 获取主表ID
            if (!billRowIdIdSet.contains(Maps.getLong(detailMap, "billRowId"))) {
                billRowIdIdSet.add(Maps.getLong(detailMap, "billRowId"));
                billRowIdIdList.add(Maps.getLong(detailMap, "billRowId"));
            }

            if (SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING.equals(Maps.getString(detailMap, "status"))) {
                if (Maps.isEmpty(materialCount, Maps.getLong(detailMap, "effectiveDateId"))) {
                    // 如果没有重复 materialId
                    materialCount.put(Maps.getLong(detailMap, "effectiveDateId"), Maps.getDouble(detailMap, "outputQty"));
                } else {
                    // 如果重复 materialId
                    Double outputQty = materialCount.get(Maps.getLong(detailMap, "effectiveDateId"));
                    materialCount.put(Maps.getLong(detailMap, "effectiveDateId"), bigDecimalAdd(outputQty, Maps.getDouble(detailMap, "outputQty")));
                }
            }
        }

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        //
        Map<Long, Map<String, String>> materialOnlyAndMaterialBatchMapList = new HashMap<Long, Map<String, String>>();

        for (Map.Entry<Long, Double> entry : materialCount.entrySet()) {
            // CHECK 出库数量是否大于分配的数量
            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(entry.getKey(), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(entry.getValue()) < 0) {
                Map<String, String> materialInfoMap = CacheUtil.getMaterialInfo(String.valueOf(updateStoreMaterialModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                Thrower.throwException(SupplyChianException.OUTPUT_QTY_MORE_THAN_EXISTS_QTY, Maps.getString(materialInfoMap, "materialName"),
                        bigDecimalSubtract(entry.getValue(), updateStoreMaterialModel.getActualQty()));
            }

            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), entry.getValue()));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            SqlCon inputDateSqlCon = new SqlCon();
            inputDateSqlCon.addMainSql("SELECT MIN(BILL_DATE) AS inputDate FROM SC_M_BILL_INPUT_DETAIL");
            inputDateSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_WAREHOUSE_INITIALIZE, " AND (EVENT_CODE = ?");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR EVENT_CODE = ?");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR EVENT_CODE = ?");
            inputDateSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " OR EVENT_CODE = ?)");
            inputDateSqlCon.addCondition(updateStoreMaterialModel.getMaterialOnly(), " AND MATERIAL_ONLY = ?");
            inputDateSqlCon.addCondition(updateStoreMaterialModel.getMaterialBatch(), " AND MATERIAL_BATCH = ?");
            inputDateSqlCon.addCondition(updateStoreMaterialModel.getWarehouseId(), " AND INPUT_WAREHOUSE_ID = ?");

            Map<String, String> inputDateSqlMap = new HashMap<String, String>();
            inputDateSqlMap.put("sql", inputDateSqlCon.getCondition());

            Map<String, Object> inputDateMap = paramMapper.getObjectInfos(inputDateSqlMap).get(0);

            Map<String, String> materialOnlyAndMaterialBatchMap = new HashMap<String, String>();
            materialOnlyAndMaterialBatchMap.put("materialOnly", updateStoreMaterialModel.getMaterialOnly());
            materialOnlyAndMaterialBatchMap.put("materialBatch", updateStoreMaterialModel.getMaterialBatch());
            materialOnlyAndMaterialBatchMap.put("inputDate", Maps.getString(inputDateMap, "inputDate"));
            materialOnlyAndMaterialBatchMapList.put(entry.getKey(), materialOnlyAndMaterialBatchMap);
        }

        Date currentDate = new Date();

        List<OutputDetailModel> updateOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(Maps.getLong(detailMap, "detailRowId"), " AND ROW_ID = ? FOR UPDATE");
            OutputDetailModel updateOutputDetailModel = getList(outputDetailMapper, sqlCon).get(0);
            if (!updateOutputDetailModel.getStatus().equals(Maps.getString(detailMap, "status"))) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "页面信息过期,请刷新页面再操作");
            }

            if (SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING.equals(updateOutputDetailModel.getStatus())) {
                Map<String, String> materialOnlyAndMaterialBatchMap = materialOnlyAndMaterialBatchMapList.get(Maps.getLong(detailMap,
                        "effectiveDateId"));
                Date billDate = Maps.getDate(detailMap, "billDate");
                Date inputDate = Maps.getDate(materialOnlyAndMaterialBatchMap, "inputDate");

                // 入库日期必须小于等于出库日期
                if (inputDate.after(billDate)) {
                    Thrower.throwException(SupplyChianException.INPUT_DATE_NOT_AFTER_OUTPUT_DATE, Maps.getLong(detailMap, "lineNumber"));
                }

                updateOutputDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
                updateOutputDetailModel.setMaterialOnly(Maps.getString(materialOnlyAndMaterialBatchMap, "materialOnly"));
                updateOutputDetailModel.setMaterialBatch(Maps.getString(materialOnlyAndMaterialBatchMap, "materialBatch"));
                updateOutputDetailModel.setOutputQty(Maps.getDouble(detailMap, "outputQty"));
                updateOutputDetailModel.setOutputerId(getEmployeeId());
                updateOutputDetailModel.setOutputUseDate(currentDate);
                updateOutputDetailModelList.add(updateOutputDetailModel);
            } else if (SupplyChianConstants.OUTPUT_USE_STATUS_WAITING.equals(updateOutputDetailModel.getStatus())) {
                if (updateOutputDetailModel.getOutputQty().compareTo(Maps.getDouble(detailMap, "outputQty")) != 0) {
                    Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "页面信息过期,请刷新页面再操作");
                }
                updateOutputDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
                updateOutputDetailModel.setOutputerId(getEmployeeId());
                updateOutputDetailModel.setOutputUseDate(currentDate);
                updateOutputDetailModelList.add(updateOutputDetailModel);
            }
        }

        if (updateStoreMaterialModelList != null && !updateStoreMaterialModelList.isEmpty()) {
            storeMaterialMapper.updates(updateStoreMaterialModelList);
        }

        outputDetailMapper.updates(updateOutputDetailModelList);

        // 更新出库单状态
        updateOutputStoreStatus(billRowIdIdList, currentDate);
    }

    @Override
    public void editGiveOutDetailFalse(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        List<Long> billRowIdIdList = new ArrayList<Long>();
        Set<Long> billRowIdIdSet = new HashSet<Long>();
        // 同一物料合计数
        Map<Long, Double> materialCount = new HashMap<Long, Double>();
        for (Map<String, Object> detailMap : detailList) {

            // 获取主表ID
            if (!billRowIdIdSet.contains(Maps.getLong(detailMap, "billRowId"))) {
                billRowIdIdSet.add(Maps.getLong(detailMap, "billRowId"));
                billRowIdIdList.add(Maps.getLong(detailMap, "billRowId"));
            }

            if (SupplyChianConstants.OUTPUT_USE_STATUS_WAITING.equals(Maps.getString(detailMap, "status"))) {
                if (Maps.isEmpty(materialCount, Maps.getLong(detailMap, "effectiveDateId"))) {
                    // 如果没有重复 materialId
                    materialCount.put(Maps.getLong(detailMap, "effectiveDateId"), Maps.getDouble(detailMap, "outputQty"));
                } else {
                    // 如果重复 materialId
                    Double outputQty = materialCount.get(Maps.getLong(detailMap, "effectiveDateId"));
                    materialCount.put(Maps.getLong(detailMap, "effectiveDateId"), bigDecimalAdd(outputQty, Maps.getDouble(detailMap, "outputQty")));
                }
            }
        }

        // 将备料状态的还原回去
        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();

        for (Map.Entry<Long, Double> entry : materialCount.entrySet()) {
            // CHECK 出库数量是否大于分配的数量
            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(entry.getKey(), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);

            updateStoreMaterialModel.setActualQty(bigDecimalAdd(updateStoreMaterialModel.getActualQty(), entry.getValue()));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);
        }

        Date currentDate = new Date();

        List<OutputDetailModel> updateOutputDetailModelList = new ArrayList<OutputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addCondition(Maps.getLong(detailMap, "detailRowId"), " AND ROW_ID = ? FOR UPDATE");
            OutputDetailModel updateOutputDetailModel = getList(outputDetailMapper, sqlCon).get(0);
            if (!updateOutputDetailModel.getStatus().equals(Maps.getString(detailMap, "status"))) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "页面信息过期,请刷新页面再操作");
            }

            updateOutputDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_UNUSE);
            updateOutputDetailModel.setOutputerId(getEmployeeId());
            updateOutputDetailModel.setOutputUseDate(currentDate);
            updateOutputDetailModelList.add(updateOutputDetailModel);
        }

        if (updateStoreMaterialModelList != null && !updateStoreMaterialModelList.isEmpty()) {
            storeMaterialMapper.updates(updateStoreMaterialModelList);
        }

        outputDetailMapper.updates(updateOutputDetailModelList);

        // 更新出库单状态
        updateOutputStoreStatus(billRowIdIdList, currentDate);
    }

    // 更新出库单主表状态
    private void updateOutputStoreStatus(List<Long> outputIdsList, Date currentDate) throws Exception {
        if (outputIdsList != null && !outputIdsList.isEmpty()) {
            String outputIdsString = longArrayListToString(outputIdsList);
            updateOutputStoreStatus(outputIdsString, currentDate);
        }
    }

    // 更新出库单主表状态
    private void updateOutputStoreStatus(String outputIdsString, Date currentDate) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, "SELECT T1.ROW_ID, ? AS STATUS");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T1");
        sqlCon.addCondition(outputIdsString, " WHERE T1.DELETED_FLAG = '0' AND T1.ROW_ID IN ?", false, true);
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL T2 WHERE T2.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND T1.ROW_ID = T2.OUTPUT_ID AND T2.STATUS = ? LIMIT 1)");
        sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL T3 WHERE T3.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND T1.ROW_ID = T3.OUTPUT_ID AND T3.STATUS <> ? LIMIT 1)");
        sqlCon.addMainSql(" UNION");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " SELECT T4.ROW_ID, ? AS STATUS");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T4");
        sqlCon.addCondition(outputIdsString, " WHERE T4.DELETED_FLAG = '0' AND T4.ROW_ID IN ?", false, true);
        sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL T5 WHERE T5.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND T4.ROW_ID = T5.OUTPUT_ID AND T5.STATUS <> ? LIMIT 1)");
        sqlCon.addMainSql(" UNION");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_UNUSE, " SELECT T6.ROW_ID, ? AS STATUS");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T6");
        sqlCon.addCondition(outputIdsString, " WHERE T6.DELETED_FLAG = '0' AND T6.ROW_ID IN ?", false, true);
        sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL T7 WHERE T7.DELETED_FLAG = '0'");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_UNUSE, " AND T6.ROW_ID = T7.OUTPUT_ID AND T7.STATUS <> ? LIMIT 1)");
        List<OutputModel> outputModelList = setSql(outputMapper, sqlCon);

        if (outputModelList != null && !outputModelList.isEmpty()) {
            for (OutputModel outputModel : outputModelList) {
                outputModel.setOutputerId(getEmployeeId());
                outputModel.setOutputUseDate(currentDate);
            }
            outputMapper.updates(outputModelList);
        }

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(getFarmId());

        if (isToHana) {
            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

            SqlCon hanaSqlCon = new SqlCon();
            hanaSqlCon.addMainSql("SELECT T1.ROW_ID, T1.FARM_ID, T1.BILL_CODE, T1.BILL_DATE, T1.USER_ID");
            hanaSqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT T1");
            hanaSqlCon.addCondition(outputIdsString, " WHERE T1.DELETED_FLAG = '0' AND T1.ROW_ID IN ?", false, true);
            hanaSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND ( T1.STATUS = ?");
            hanaSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, " OR T1.STATUS = ? )");
            hanaSqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL T2 WHERE T2.DELETED_FLAG = '0'");
            hanaSqlCon.addMainSql(" AND T1.ROW_ID = T2.OUTPUT_ID");
            hanaSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND ( T2.STATUS = ?");
            hanaSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " OR T2.STATUS = ? )");
            hanaSqlCon.addMainSql(" LIMIT 1)");
            List<OutputModel> hanaOutputModelList = setSql(outputMapper, hanaSqlCon);

            if (hanaOutputModelList != null && !hanaOutputModelList.isEmpty()) {
                for (OutputModel hanaOutputModel : hanaOutputModelList) {
                    HanaMaterialOperateBill hanaMaterialOperateBill = new HanaMaterialOperateBill();

                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(hanaOutputModel.getFarmId());
                    String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                    String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                    // 分公司编码
                    hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
                    // 新农+单据编号
                    hanaMaterialOperateBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(hanaOutputModel.getRowId()) + "-" + hanaOutputModel
                            .getBillCode());
                    // 出库日期
                    hanaMaterialOperateBill.setMTC_DocDate(hanaOutputModel.getBillDate());
                    // 领用人
                    hanaMaterialOperateBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(hanaOutputModel.getFarmId(), hanaOutputModel.getUserId()));
                    // 表行
                    List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

                    hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);

                    SqlCon outputDetailSqlCon = new SqlCon();
                    outputDetailSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND STATUS = ?");
                    outputDetailSqlCon.addCondition(hanaOutputModel.getRowId(), " AND OUTPUT_ID = ?");

                    List<OutputDetailModel> outputDetailModelList = getList(outputDetailMapper, outputDetailSqlCon);
                    for (OutputDetailModel outputDetailModel : outputDetailModelList) {
                        String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(outputDetailModel.getMaterialId());
                        Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(outputDetailModel.getMaterialId()),
                                MaterialInfoEnum.MATERIAL_INFO);
                        // SAP是否库存管理，库存管理才有出库和反出库操作
                        if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                                "materialSecondClass"))) {
                            HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                            // 分公司编码
                            hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                            // 猪场编码
                            hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                            // 猪舍编号
                            hanaMaterialOperateBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(outputDetailModel.getOutputHouseId()));
                            // 新农+单据编号
                            hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(outputDetailModel.getOutputId()));
                            // 新农+单据行号
                            hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(outputDetailModel.getOutputLn()));
                            // 物料编号
                            hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                            // 批次编号
                            hanaMaterialOperateBillDetail.setMTC_BatchNum(outputDetailModel.getMaterialBatch());
                            // 仓库编号
                            hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(outputDetailModel.getOutputWarehouseId()));
                            // 领用数量
                            hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(outputDetailModel.getOutputQty()));
                            // 如果领用的为兽药，默认为A - 商品猪
                            // 核算对象：A - 商品猪 B - 生产猪C - 后备猪
                            if(SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialInfo, "materialFirstClass"))){
                                // 商品猪
                                hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                            } else {
                                if ("1".equals(outputDetailModel.getPigType())) {
                                    // 后备猪
                                    hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_C);
                                } else if ("2".equals(outputDetailModel.getPigType())) {
                                    // 生产猪
                                    hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_B);
                                } else {
                                    // 商品猪
                                    hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                                }
                            }
                            hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                        }
                    }

                    if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:生产领用
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2040);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
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
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                        mtcITFCList.add(mtcITFC);
                    }
                }

                if (!mtcITFCList.isEmpty()) {
                    hanaCommonService.insertsMTC_ITFC(mtcITFCList);
                }
            }
        }
        // END HANA

    }

    /* START 仓管发料（明细显示） */

    /* START 套餐以及物料替换 */

    @Override
    public BasePageInfo searchReplaceAndPackageToPage() throws Exception {
        setToPage();
        List<ReplaceAndPackageModel> replaceAndPackageModelList = replaceAndPackageMapper.searchToList(getFarmId());
        if (replaceAndPackageModelList != null && !replaceAndPackageModelList.isEmpty()) {
            for (ReplaceAndPackageModel replaceAndPackageModel : replaceAndPackageModelList) {
                Map<String, Object> map = replaceAndPackageModel.getData();
                if ("1".equals(Maps.getString(map, "groupModel"))) {
                    map.put("groupModelName", "互相替换");
                } else if ("2".equals(Maps.getString(map, "groupModel"))) {
                    map.put("groupModelName", "固定赠料");
                } else if ("3".equals(Maps.getString(map, "groupModel"))) {
                    map.put("groupModelName", "固定套餐");
                } else if ("4".equals(Maps.getString(map, "groupModel"))) {
                    map.put("groupModelName", "固定领用");
                }

                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        } else {
            replaceAndPackageModelList = new ArrayList<ReplaceAndPackageModel>();
        }
        return getPageInfo(replaceAndPackageModelList);
    }

    @Override
    public List<ReplaceAndPackageDetailModel> searchReplaceAndPackageDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(Maps.getLong(inputMap, "mainId"), " AND MAIN_ID = ?");
        List<ReplaceAndPackageDetailModel> replaceAndPackageDetailModelList = getList(replaceAndPackageDetailMapper, sqlCon);
        for (ReplaceAndPackageDetailModel replaceAndPackageDetailModel : replaceAndPackageDetailModelList) {
            Map<String, Object> replaceAndPackageDetailMap = replaceAndPackageDetailModel.getData();
            replaceAndPackageDetailMap.putAll(CacheUtil.getMaterialInfo(Maps.getString(replaceAndPackageDetailMap, "materialId"),
                    MaterialInfoEnum.MATERIAL_INFO));
            replaceAndPackageDetailMap.put("materialFirstClassName", CacheUtil.getName(Maps.getString(replaceAndPackageDetailMap,
                    "materialFirstClass"), NameEnum.CODE_NAME, CodeEnum.MATERIAL_FIRST_CLASS));
            replaceAndPackageDetailMap.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(
                    replaceAndPackageDetailMap, "materialSecondClass"), CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(replaceAndPackageDetailMap,
                            "materialFirstClass")));

            if ("1".equals(Maps.getString(replaceAndPackageDetailMap, "purchaseOrFree"))) {
                replaceAndPackageDetailMap.put("purchaseOrFreeName", "购料");
            } else if ("2".equals(Maps.getString(replaceAndPackageDetailMap, "purchaseOrFree"))) {
                replaceAndPackageDetailMap.put("purchaseOrFreeName", "赠料");
            }

            if ("Y".equals(Maps.getString(replaceAndPackageDetailMap, "mustBeFree"))) {
                replaceAndPackageDetailMap.put("mustBeFreeName", "是");
            } else {
                replaceAndPackageDetailMap.put("mustBeFreeName", "否");
            }

            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(replaceAndPackageDetailMap, "supplierId"));
            replaceAndPackageDetailMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            replaceAndPackageDetailMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));

        }
        return replaceAndPackageDetailModelList;
    }

    @Override
    public void editReplaceAndPackage(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        if (detailList.size() <= 1) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "明细数量必须大于1条！");
        }

        if ("2".equals(Maps.getString(inputMap, "groupModel"))) {
            int existsPurchase = 0;
            for (Map<String, Object> detailMap : detailList) {
                if (!"1".equals(Maps.getString(detailMap, "deletedFlag"))) {
                    if (Maps.isEmpty(detailMap, "purchaseOrFree")) {
                        Thrower.throwException(SupplyChianException.PURCHASE_OR_FREE_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
                    }

                    if ("1".equals(Maps.getString(detailMap, "purchaseOrFree"))) {
                        existsPurchase++;
                    }
                }
            }

            if (existsPurchase != 1) {
                Thrower.throwException(SupplyChianException.PURCHASE_MUST_BE_ONLY);
            }
        }

        Date currentDate = new Date();

        if (Maps.getLong(inputMap, "rowId") == 0L) {

            // 新增
            ReplaceAndPackageModel replaceAndPackageModel = getBean(ReplaceAndPackageModel.class, inputMap);
            replaceAndPackageModel.setFarmId(getFarmId());
            replaceAndPackageModel.setCompanyId(getCompanyId());
            replaceAndPackageModel.setGroupModel(Maps.getString(inputMap, "groupModel"));
            replaceAndPackageModel.setCreateId(getEmployeeId());
            replaceAndPackageModel.setCreateDate(currentDate);
            replaceAndPackageMapper.insert(replaceAndPackageModel);

            List<ReplaceAndPackageDetailModel> replaceAndPackageDetailModelList = new ArrayList<ReplaceAndPackageDetailModel>();
            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "mainProportionQty") || Maps.getDouble(detailMap, "mainProportionQty") <= 0D) {
                    Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "比例数量");
                }

                ReplaceAndPackageDetailModel replaceAndPackageDetailModel = new ReplaceAndPackageDetailModel();
                replaceAndPackageDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                replaceAndPackageDetailModel.setMainId(replaceAndPackageModel.getRowId());
                replaceAndPackageDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                replaceAndPackageDetailModel.setMainProportionQty(Maps.getLong(detailMap, "mainProportionQty"));
                replaceAndPackageDetailModel.setGroupModel(Maps.getString(inputMap, "groupModel"));
                if ("2".equals(Maps.getString(inputMap, "groupModel"))) {
                    replaceAndPackageDetailModel.setPurchaseOrFree(Maps.getString(detailMap, "purchaseOrFree"));
                    replaceAndPackageDetailModel.setMustBeFree(Maps.getString(detailMap, "mustBeFree"));
                }
                replaceAndPackageDetailModel.setCreateId(getEmployeeId());
                replaceAndPackageDetailModel.setCreateDate(currentDate);
                replaceAndPackageDetailModelList.add(replaceAndPackageDetailModel);
            }

            replaceAndPackageDetailMapper.inserts(replaceAndPackageDetailModelList);
        } else {
            // 修改
            if (!Maps.isEmpty(inputMap, "notes")) {
                ReplaceAndPackageModel replaceAndPackageModel = new ReplaceAndPackageModel();
                replaceAndPackageModel.setRowId(Maps.getLong(inputMap, "rowId"));
                replaceAndPackageModel.setNotes(Maps.getString(inputMap, "notes"));
                replaceAndPackageMapper.update(replaceAndPackageModel);
            }

            List<ReplaceAndPackageDetailModel> insertReplaceAndPackageDetailModelList = new ArrayList<ReplaceAndPackageDetailModel>();
            List<ReplaceAndPackageDetailModel> updateReplaceAndPackageDetailModelList = new ArrayList<ReplaceAndPackageDetailModel>();
            List<Long> deleteReplaceAndPackageDetailModelList = new ArrayList<Long>();

            for (Map<String, Object> detailMap : detailList) {
                if (Maps.isEmpty(detailMap, "mainProportionQty") || Maps.getDouble(detailMap, "mainProportionQty") <= 0D) {
                    Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "比例数量");
                }

                if (Maps.isEmpty(detailMap, "rowId")) {
                    ReplaceAndPackageDetailModel replaceAndPackageDetailModel = new ReplaceAndPackageDetailModel();
                    replaceAndPackageDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    replaceAndPackageDetailModel.setMainId(Maps.getLong(inputMap, "rowId"));
                    replaceAndPackageDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                    replaceAndPackageDetailModel.setMainProportionQty(Maps.getLong(detailMap, "mainProportionQty"));
                    replaceAndPackageDetailModel.setGroupModel(Maps.getString(inputMap, "groupModel"));
                    if ("2".equals(Maps.getString(inputMap, "groupModel"))) {
                        replaceAndPackageDetailModel.setPurchaseOrFree(Maps.getString(detailMap, "purchaseOrFree"));
                        replaceAndPackageDetailModel.setMustBeFree(Maps.getString(detailMap, "mustBeFree"));
                    }
                    replaceAndPackageDetailModel.setCreateId(getEmployeeId());
                    replaceAndPackageDetailModel.setCreateDate(currentDate);
                    insertReplaceAndPackageDetailModelList.add(replaceAndPackageDetailModel);

                } else if ("1".equals(Maps.getString(detailMap, "deletedFlag"))) {
                    deleteReplaceAndPackageDetailModelList.add(Maps.getLong(detailMap, "rowId"));

                } else {
                    ReplaceAndPackageDetailModel replaceAndPackageDetailModel = new ReplaceAndPackageDetailModel();
                    replaceAndPackageDetailModel.setRowId(Maps.getLong(detailMap, "rowId"));
                    replaceAndPackageDetailModel.setMainProportionQty(Maps.getLong(detailMap, "mainProportionQty"));
                    if ("2".equals(Maps.getString(inputMap, "groupModel"))) {
                        replaceAndPackageDetailModel.setPurchaseOrFree(Maps.getString(detailMap, "purchaseOrFree"));
                        replaceAndPackageDetailModel.setMustBeFree(Maps.getString(detailMap, "mustBeFree"));
                    }
                    updateReplaceAndPackageDetailModelList.add(replaceAndPackageDetailModel);
                }
            }

            if (insertReplaceAndPackageDetailModelList != null && !insertReplaceAndPackageDetailModelList.isEmpty()) {
                replaceAndPackageDetailMapper.inserts(insertReplaceAndPackageDetailModelList);
            }

            if (updateReplaceAndPackageDetailModelList != null && !updateReplaceAndPackageDetailModelList.isEmpty()) {
                replaceAndPackageDetailMapper.updates(updateReplaceAndPackageDetailModelList);
            }

            if (deleteReplaceAndPackageDetailModelList != null && !deleteReplaceAndPackageDetailModelList.isEmpty()) {
                replaceAndPackageDetailMapper.deletes(longArrayListTolong(deleteReplaceAndPackageDetailModelList));
            }
        }
    }

    @Override
    public void deleteReplaceAndPackage(long[] ids) throws Exception {
        replaceAndPackageMapper.deletes(ids);
        setDeletes(replaceAndPackageDetailMapper, ids, " MAIN_ID");
    }

    @Override
    public List<Map<String, Object>> searchReplaceMaterialToList(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "物料替换功能暂停使用！");
        return searchGroupMaterial(Maps.getLong(inputMap, "relatedId"), getFarmId(), "1", null);
    }

    @Override
    public List<Map<String, Object>> searchFixedFreeMaterialToList(Map<String, Object> inputMap) throws Exception {
        List<Map<String, Object>> fixedGroupMaterialList = searchGroupMaterial(Maps.getLong(inputMap, "relatedId"), Maps.getLong(inputMap,
                "requireFarm"), "2", Maps.getString(inputMap, "mustBeFree"));

        if ("true".equals(Maps.getString(inputMap, "onlyFree"))) {
            // 购料Map
            Map<String, Object> purchaseMaterialMap = null;
            // 赠料List
            List<Map<String, Object>> fixedFreeMaterialList = new ArrayList<Map<String, Object>>();

            // 分离出购料和赠料
            for (Map<String, Object> fixedGroupMaterialMap : fixedGroupMaterialList) {
                if (Maps.getLong(fixedGroupMaterialMap, "relatedId") == Maps.getLong(inputMap, "relatedId") && "1".equals(Maps.getString(
                        fixedGroupMaterialMap, "purchaseOrFree"))) {
                    // 购料
                    purchaseMaterialMap = fixedGroupMaterialMap;
                } else {
                    // 赠料
                    fixedFreeMaterialList.add(fixedGroupMaterialMap);
                }
            }

            for (Map<String, Object> fixedFreeMaterialMap : fixedFreeMaterialList) {
                // 购料比例数
                fixedFreeMaterialMap.put("groupPurchaseMaterialQty", Maps.getString(purchaseMaterialMap, "mainProportionQty"));
                // 赠料比例数
                fixedFreeMaterialMap.put("groupFreeMaterialQty", Maps.getString(fixedFreeMaterialMap, "mainProportionQty"));
                fixedFreeMaterialMap.put("notes", "固定赠料");
            }

            return fixedFreeMaterialList;
        } else {
            return fixedGroupMaterialList;
        }
    }

    @Override
    public List<Map<String, Object>> searchFixedGroupMaterialToList(Map<String, Object> inputMap) throws Exception {
        return searchGroupMaterial(Maps.getLong(inputMap, "relatedId"), getFarmId(), "3", null);
    }

    @Override
    public List<Map<String, Object>> searchFixedUseMaterialToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.actualQty - IFNULL(T2.outputQty,0) AS existsQty, T1.materialId AS materialId, T1.warehouseId AS warehouseId");
        sqlCon.addMainSql(" FROM( SELECT SUM(A.ACTUAL_QTY) AS actualQty, A.MATERIAL_ID AS materialId, A.WAREHOUSE_ID AS warehouseId");
        sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL A");
        sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL B");
        sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.MATERIAL_ID = B.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_M_REPLACE_AND_PACKAGE_DETAIL C");
        sqlCon.addMainSql(" ON(C.DELETED_FLAG = '0' AND B.ROW_ID = C.MATERIAL_ID)");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0' AND A.ACTUAL_QTY > 0");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getCompanyId(), " AND A.FARM_ID = ?");

        } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
        }

        sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND A.WAREHOUSE_ID = ?");
        sqlCon.addMainSql(" AND EXISTS( SELECT 1 FROM SC_M_REPLACE_AND_PACKAGE_DETAIL D");
        sqlCon.addMainSql(" WHERE D.DELETED_FLAG = '0' AND C.MAIN_ID = D.MAIN_ID");
        sqlCon.addCondition(Maps.getLong(inputMap, "materialId"), " AND D.MATERIAL_ID = ?");
        sqlCon.addMainSql(" AND D.GROUP_MODEL = '4')");
        sqlCon.addMainSql(" GROUP BY A.WAREHOUSE_ID, A.MATERIAL_ID ) T1");
        sqlCon.addMainSql(" LEFT OUTER JOIN (");
        sqlCon.addMainSql(" SELECT SUM(B.OUTPUT_QTY) AS outputQty, B.MATERIAL_ID AS materialId, B.OUTPUT_WAREHOUSE_ID AS warehouseId");
        sqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT A INNER JOIN SC_M_BILL_OUTPUT_DETAIL B");
        sqlCon.addMainSql(" ON (B.DELETED_FLAG = '0' AND A.ROW_ID = B.OUTPUT_ID");
        sqlCon.addCondition(Maps.getLong(inputMap, "warehouseId"), " AND B.OUTPUT_WAREHOUSE_ID = ?");
        sqlCon.addMainSql(" AND A.FARM_ID = B.FARM_ID)");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0'");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND (A.FARM_ID = ?");
            sqlCon.addConditionWithNull(getFarmId(), " OR A.COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getCompanyId(), " AND (A.FARM_ID = ?");
            sqlCon.addConditionWithNull(getCompanyId(), " OR A.COMPANY_ID = ?)");

        } else if (SupplyChianConstants.DAFENG_MODEL_NOT.equals(Maps.getString(inputMap, "dafengModel"))) {
            sqlCon.addConditionWithNull(getFarmId(), " AND A.FARM_ID = ?");

        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "系统异常。。。请刷新页面!!!");
        }

        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_OUTPUT_USE, " AND A.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND A.STATUS = ?");
        sqlCon.addMainSql(" GROUP BY B.OUTPUT_WAREHOUSE_ID, B.MATERIAL_ID) T2");
        sqlCon.addMainSql(" ON (T1.materialId = T2.materialId AND T1.warehouseId = T2.warehouseId)");
        sqlCon.addMainSql(" WHERE T1.actualQty - IFNULL(T2.outputQty,0) > 0");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
                map.put("existsQtyName", Maps.getString(map, "existsQty") + Maps.getString(map, "unit"));
            }
        }

        return list;
    }

    // 寻找互相替换，固定套餐，固定赠料
    // 参数：物料主数据的relatedId，分组模式(1:互相替换,2:固定赠料,3:固定套餐),去除本身
    private List<Map<String, Object>> searchGroupMaterial(Long relatedId, Long farmId, String groupModel, String mustBeFree) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T2.ROW_ID AS materialId, T2.PURCHASE_OR_FREE AS purchaseOrFree");
        sqlCon.addMainSql(", T2.MAIN_ID AS mainRelatedId, T2.MAIN_PROPORTION_QTY AS mainProportionQty");
        sqlCon.addMainSql(" FROM CD_M_MATERIAL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_REPLACE_AND_PACKAGE_DETAIL T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T1.RELATED_ID = T2.MATERIAL_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(farmId, " AND T1.FARM_ID = ?");
        if ("2".equals(groupModel)) {
            if ("Y".equals(mustBeFree)) {
                sqlCon.addCondition(mustBeFree, " AND T2.MUST_BE_FREE = ?");
            }
        }
        sqlCon.addMainSql(" AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM SC_M_REPLACE_AND_PACKAGE_DETAIL T3");
        sqlCon.addMainSql(" WHERE T3.DELETED_FLAG = '0' AND T2.MAIN_ID = T3.MAIN_ID");
        sqlCon.addCondition(relatedId, " AND T3.MATERIAL_ID = ?");
        sqlCon.addCondition(groupModel, " AND T3.GROUP_MODEL = ?");
        if ("2".equals(groupModel)) {
            sqlCon.addCondition("1", " AND T3.PURCHASE_OR_FREE = ?");
        }
        sqlCon.addMainSql(")");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "rowId"), MaterialInfoEnum.MATERIAL_INFO));
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
                map.put("defaultWarehouseName", CacheUtil.getName(Maps.getString(map, "defaultWarehouse"),
                        xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return list;
    }

    /* END 套餐以及物料替换 */

    // START 判断是否含有固定套餐，比例是否正确
    private void groupMaterialPurchaseCheck(List<HashMap> detailList, String filedName) throws Exception {
        Map<String, List<Map<String, Object>>> groupMaterialMap = new HashMap<String, List<Map<String, Object>>>();
        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, filedName) || Maps.getDouble(detailMap, filedName) <= 0D) {
                Thrower.throwException(SupplyChianException.NUMBER_IS_ZERO, Maps.getLong(detailMap, "lineNumber"), "需求量");
            }

            if (!Maps.isEmpty(detailMap, "mainRelatedId")) {

                Map<String, Object> groupMaterialDetailMap = new HashMap<String, Object>();
                groupMaterialDetailMap.put("lineNumber", Maps.getLong(detailMap, "lineNumber"));
                groupMaterialDetailMap.put("qty", Maps.getDouble(detailMap, filedName));
                groupMaterialDetailMap.put("mainProportionQty", Maps.getLong(detailMap, "mainProportionQty"));

                if (!groupMaterialMap.containsKey(Maps.getString(detailMap, "mainRelatedId"))) {
                    List<Map<String, Object>> groupMaterialDetailList = new ArrayList<Map<String, Object>>();
                    groupMaterialDetailList.add(groupMaterialDetailMap);
                    groupMaterialMap.put(Maps.getString(detailMap, "mainRelatedId"), groupMaterialDetailList);

                } else {
                    List<Map<String, Object>> groupMaterialDetailList = groupMaterialMap.get(Maps.getString(detailMap, "mainRelatedId"));
                    groupMaterialDetailList.add(groupMaterialDetailMap);
                }

            }
        }

        if (groupMaterialMap != null && !groupMaterialMap.isEmpty()) {
            boolean errorFlag = false;
            StringBuffer lineNumberStringBuffer = new StringBuffer();
            StringBuffer mainProportionQtyBuffer = new StringBuffer();

            for (Entry<String, List<Map<String, Object>>> entry : groupMaterialMap.entrySet()) {
                List<Map<String, Object>> groupMaterialDetailList = (List<Map<String, Object>>) entry.getValue();

                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" AND MAIN_ID =" + entry.getKey());
                List<ReplaceAndPackageDetailModel> groupMaterialInDbList = getList(replaceAndPackageDetailMapper, sqlCon);
                if (groupMaterialInDbList.size() != groupMaterialDetailList.size()) {
                    Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "第" + Maps.getString(groupMaterialDetailList.get(0),
                            "lineNumber") + "行必须按套餐购买，缺少套餐物料！");
                }

                for (int i = 0; i < groupMaterialDetailList.size() - 1; i++) {
                    Map<String, Object> map1 = groupMaterialDetailList.get(i);
                    Map<String, Object> map2 = groupMaterialDetailList.get(i + 1);

                    if (bigDecimalDivide(Maps.getDouble(map1, "qty"), Maps.getDouble(map2, "qty"), 5).compareTo(bigDecimalDivide(Maps.getDouble(map1,
                            "mainProportionQty"), Maps.getDouble(map2, "mainProportionQty"), 5)) != 0) {
                        errorFlag = true;
                    }

                    // 记录行数与比例
                    lineNumberStringBuffer.append(Maps.getString(map1, "lineNumber"));
                    lineNumberStringBuffer.append(",");
                    mainProportionQtyBuffer.append(Maps.getString(map1, "mainProportionQty"));
                    mainProportionQtyBuffer.append("：");
                    if (i == groupMaterialDetailList.size() - 2) {
                        lineNumberStringBuffer.append(Maps.getString(map2, "lineNumber"));
                        mainProportionQtyBuffer.append(Maps.getString(map2, "mainProportionQty"));
                    }
                }
                if (errorFlag) {
                    Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "第" + lineNumberStringBuffer.toString() + "行的比例必须为】["
                            + mainProportionQtyBuffer.toString() + "]");
                }

            }

        }
    }
    // END 判断是否含有固定套餐，比例是否正确

    /* START 发票填写 */
    @Override
    public BasePageInfo searchReceiptBillToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.BILL_DATE AS billDate");
        sqlCon.addMainSql(", T1.RECEIPT_BRANCH_ID AS receiptBranchId");
        sqlCon.addMainSql(", T1.SUPPLIER_ID AS supplierId");
        sqlCon.addMainSql(", GROUP_CONCAT(DISTINCT T3.SORT_NAME) AS requireFarmSortName");
        sqlCon.addMainSql(", T1.NOTES AS notes");
        sqlCon.addMainSql(" FROM SC_M_BILL_RECEIPT T1");
        sqlCon.addMainSql(" INNER JOIN SC_R_RECEIPT_INPUT T2");
        sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.RECEIPT_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T3");
        sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T2.FARM_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG='0'");
        sqlCon.addConditionWithNull(getFarmId(), " AND T1.FARM_ID = ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");
        sqlCon.addMainSql(" ORDER BY T1.BILL_DATE DESC, T1.ROW_ID DESC");

        List<Map<String, Object>> receiptMapList = paramMapperSetSQL(paramMapper, sqlCon);

        if (receiptMapList != null && !receiptMapList.isEmpty()) {
            for (Map<String, Object> receiptMap : receiptMapList) {
                Map<String, String> requireFarmInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(receiptMap, "receiptBranchId"));
                receiptMap.put("receiptBranchName", Maps.getString(requireFarmInfoMap, "COMPANY_NAME"));

                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(receiptMap, "supplierId"));
                receiptMap.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                receiptMap.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return getPageInfo(receiptMapList);
    }

    @Override
    public List<Map<String, Object>> searchSupplierForReceiptBillToList(Map<String, Object> inputMap) throws Exception {
        // purchaseFlag判断可以采购的类型[1]-全部[2]-饲料[3]-兽药
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT SUPPLIER_ID AS supplierId ");
        sqlCon.addMainSql(" FROM CD_M_MATERIAL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_INPUT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T2.MATERIAL_ID = T1.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T2.INPUT_QTY <> T2.RECEIPT_QTY");
        // sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ARRIVE, " AND (T2.EVENT_CODE = ?");
        // sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR T2.EVENT_CODE = ?");
        // sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T2.EVENT_CODE = ?)");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " AND (T2.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T2.EVENT_CODE = ?)");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ID = T2.MATERIAL_ID");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?)");
        if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        } else if (!"1".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
        } else {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        }
        sqlCon.addMainSql(" GROUP BY T1.SUPPLIER_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchBranchIdForReceiptBillToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T2.FARM_ID AS farmId ");
        sqlCon.addMainSql(" FROM CD_M_MATERIAL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_INPUT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T2.MATERIAL_ID = T1.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T2.INPUT_QTY <> T2.RECEIPT_QTY");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " AND (T2.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T2.EVENT_CODE = ?)");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ID = T2.MATERIAL_ID");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?)");
        if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        } else if (!"1".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
        } else {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        }
        sqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND T1.SUPPLIER_ID = ?");
        sqlCon.addMainSql(" GROUP BY T2.FARM_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        // 去除重复的branchId
        Set<String> branchIdSet = new HashSet<String>();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLong(map, "farmId"));
                String branchId = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                if (!branchIdSet.contains(branchId)) {
                    branchIdSet.add(branchId);
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("branchId", Maps.getLong(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchIDRowId));
                    resultMap.put("branchName", Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchIDName));
                    result.add(resultMap);
                }
            }
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> searchFarmIdForReceiptBillToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT A.farmId, A.branchId");
        sqlCon.addMainSql(" FROM (");
        sqlCon.addMainSql("SELECT T2.FARM_ID AS farmId");
        sqlCon.addMainSql(", (IF(T3.ACCOUNT_COMPANY_CLASS IN (2,4),T3.ROW_ID");
        sqlCon.addMainSql(" ,IF(T4.ACCOUNT_COMPANY_CLASS IN (2,4),T4.ROW_ID,''))) AS branchId");
        sqlCon.addMainSql(" FROM CD_M_MATERIAL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_INPUT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T2.MATERIAL_ID = T1.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T2.FARM_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY T4");
        sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0' AND T3.SUP_COMPANY_ID = T4.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T2.INPUT_QTY <> T2.RECEIPT_QTY");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " AND (T2.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T2.EVENT_CODE = ?)");
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM SC_M_BILL_PURCHASE_DETAIL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND MATERIAL_ID = T2.MATERIAL_ID");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T2.MATERIAL_ONLY");
        sqlCon.addConditionWithNull(getFarmId(), " AND FARM_ID = ?)");
        if ("2".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " AND ?");
        } else if ("3".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        } else if (!"1".equals(Maps.getString(inputMap, "purchaseFlag"))) {
            Thrower.throwException(SupplyChianException.NOT_PURCHASE_USER);
        } else {
            sqlCon.addMainSql(" AND T1.MATERIAL_FIRST_CLASS");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_10, " BETWEEN ?");
            sqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_90, " AND ?");
        }
        sqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND T1.SUPPLIER_ID = ?");
        Maps.getLong(inputMap, "branchId");
        sqlCon.addMainSql(" GROUP BY T2.FARM_ID");
        sqlCon.addMainSql(") A ");
        sqlCon.addCondition(Maps.getLong(inputMap, "branchId"), " WHERE A.branchId = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "farmId"));
                map.put("farmSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("farmName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchInvoiceTableDetialToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(Maps.getLong(inputMap, "receiptId"), " AND RECEIPT_ID = ?");
        sqlCon.addMainSql(" ORDER BY RECEIPT_LN");

        List<ReceiptDetailModel> receiptDetailModelList = getList(receiptDetailMapper, sqlCon);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        for (ReceiptDetailModel receiptDetailModel : receiptDetailModelList) {
            Map<String, Object> map = receiptDetailModel.getData();

            // 路径
            String relativePath = Maps.getString(map, "relativePath");
            // 文件系统名
            String fileName = Maps.getString(map, "fileName");
            // 文件后缀名
            String fileSuffix = Maps.getString(map, "fileSuffix");

            map.put("file", "上传1个附件");
            List<String> picSrcList = new ArrayList<String>();
            map.put("picSrc", picSrcList);
            List<String> fileNameList = new ArrayList<String>();
            map.put("fileNameList", fileNameList);
            picSrcList.add(relativePath + fileName + fileSuffix);
            fileNameList.add(fileName + fileSuffix);

            result.add(map);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> searchListTableDetialToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T2.BILL_DATE AS purchaseBillDate, T2.BILL_CODE AS purchaseBillCode");
        sqlCon.addMainSql(", T1.FARM_ID AS farmId, T1.BILL_DATE AS inputBillDate, T1.BILL_CODE AS inputBillCode, T2.FREE_LN AS freeLn");
        sqlCon.addMainSql(", T3.MATERIAL_NAME AS materialName, T3.SPEC_ALL AS specAll, T3.UNIT AS unit");
        sqlCon.addMainSql(", _clearEndZero(T1.INPUT_QTY) AS inputQty");
        sqlCon.addMainSql(", CONCAT(_clearEndZero(T1.RECEIPT_QTY),'/',_clearEndZero(T1.INPUT_QTY)) AS receiptQtyInputQty");
        sqlCon.addMainSql(", CONCAT(_clearEndZero(T4.RECEIPT_QTY),'/',_clearEndZero(T1.INPUT_QTY)) AS thisReceiptQtyInputQty");
        sqlCon.addMainSql(", _clearEndZero(T4.RECEIPT_QTY) AS qty");
        sqlCon.addMainSql(", _clearEndZero(T4.RECEIPT_PRICE) AS actualPrice");
        sqlCon.addMainSql(", _clearEndZero(T4.TOTAL_PRICE) AS totalPrice");
        sqlCon.addMainSql(", T3.MATERIAL_FIRST_CLASS AS materialFirstClass, T3.MATERIAL_SECOND_CLASS AS materialSecondClass");
        sqlCon.addMainSql(", T3.MATERIAL_CLASS_NUMBER AS materialClassNumber");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.INPUT_ID = T2.INPUT_ID AND T1.INPUT_LN = T2.INPUT_LN)");
        sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T3");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.MATERIAL_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_R_RECEIPT_INPUT T4");
        sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0' AND T1.MATERIAL_ID = T4.MATERIAL_ID");
        sqlCon.addMainSql(" AND T1.MATERIAL_ONLY = T4.MATERIAL_ONLY AND T1.MATERIAL_BATCH = T4.MATERIAL_BATCH)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        // sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ARRIVE, " AND (T1.EVENT_CODE = ?");
        // sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " OR T1.EVENT_CODE = ?");
        // sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T1.EVENT_CODE = ?)");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " AND (T1.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T1.EVENT_CODE = ?)");
        sqlCon.addCondition(Maps.getLong(inputMap, "receiptId"), " AND T4.RECEIPT_ID = ?");
        sqlCon.addMainSql(" ORDER BY T4.MATERIAL_INFO_LN");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));

                if (Maps.getLong(map, "freeLn") == 0D) {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
                } else {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
                }

                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "farmId"));
                map.put("farmSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("farmName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return list;
    }

    @Override
    public List<Map<String, Object>> searchReceiptFillInInputDetailToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT rowId, purchaseBillDate, purchaseBillCode, inputBillDate, inputBillCode, freeLn");
        sqlCon.addMainSql(", rebateReason, farmId, materialName, specAll, unit, inputQty, receiptQtyInputQty");
        sqlCon.addMainSql(", qty, actualPrice, totalPrice, materialFirstClass, materialSecondClass, materialClassNumber");
        sqlCon.addMainSql(", branchId");
        sqlCon.addMainSql(" FROM (");
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T2.BILL_DATE AS purchaseBillDate, T2.BILL_CODE AS purchaseBillCode");
        sqlCon.addMainSql(", T1.BILL_DATE AS inputBillDate, T1.BILL_CODE AS inputBillCode, T2.FREE_LN AS freeLn");
        sqlCon.addMainSql(", T2.REBATE_REASON AS rebateReason");
        sqlCon.addMainSql(", T1.FARM_ID AS farmId, T1.INPUT_ID AS inputId, T1.INPUT_LN AS inputLn");
        sqlCon.addMainSql(", T3.MATERIAL_NAME AS materialName, T3.SPEC_ALL AS specAll, T3.UNIT AS unit");
        sqlCon.addMainSql(", _clearEndZero(T1.INPUT_QTY) AS inputQty");
        sqlCon.addMainSql(", CONCAT(_clearEndZero(T1.RECEIPT_QTY),'/',_clearEndZero(T1.INPUT_QTY)) AS receiptQtyInputQty");
        sqlCon.addMainSql(", _clearEndZero(T1.INPUT_QTY - T1.RECEIPT_QTY) AS qty");
        sqlCon.addMainSql(", _clearEndZero(T2.ACTUAL_PRICE) AS actualPrice");
        sqlCon.addMainSql(", _clearEndZero(ROUND((T1.INPUT_QTY - T1.RECEIPT_QTY) * T2.ACTUAL_PRICE,2)) AS totalPrice");
        sqlCon.addMainSql(", T3.MATERIAL_FIRST_CLASS AS materialFirstClass, T3.MATERIAL_SECOND_CLASS AS materialSecondClass");
        sqlCon.addMainSql(", T3.MATERIAL_CLASS_NUMBER AS materialClassNumber");
        sqlCon.addMainSql(", (IF(T4.ACCOUNT_COMPANY_CLASS IN (2,4), T4.ROW_ID");
        sqlCon.addMainSql(" , IF(T5.ACCOUNT_COMPANY_CLASS IN (2,4), T5.ROW_ID, NULL))) AS branchId");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.INPUT_ID = T2.INPUT_ID AND T1.INPUT_LN = T2.INPUT_LN)");
        sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T1.MATERIAL_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T4");
        sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0' AND T1.FARM_ID = T4.ROW_ID)");
        sqlCon.addMainSql(" LEFT OUTER JOIN HR_M_COMPANY T5");
        sqlCon.addMainSql(" ON (T5.DELETED_FLAG = '0' AND T4.SUP_COMPANY_ID = T5.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0' AND T1.STATUS = '1'");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE, " AND (T1.EVENT_CODE = ?");
        sqlCon.addCondition(SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE, " OR T1.EVENT_CODE = ?)");
        sqlCon.addMainSql(" AND T1.RECEIPT_QTY <> T1.INPUT_QTY");
        sqlCon.addCondition(getFarmId(), " AND T2.FARM_ID = ?");
        // sqlCon.addCondition(Maps.getLong(inputMap, "farmId"), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND T2.SUPPLIER_ID = ?");
        sqlCon.addMainSql(" ) A");
        sqlCon.addCondition(Maps.getLong(inputMap, "branchId"), " WHERE A.branchId = ?");
        sqlCon.addCondition(Maps.getString(inputMap, "farmId"), " AND A.farmId IN ?", false, true);
        if (Maps.isEmpty(inputMap, "sort")) {
            sqlCon.addMainSql(" ORDER BY A.farmId, A.purchaseBillCode, A.inputBillDate, A.inputId, A.inputLn");
        } else {
            sqlCon.addMainSql(" ORDER BY " + Maps.getString(inputMap, "sort") + " " + Maps.getString(inputMap, "order"));
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                        CodeEnum.MATERIAL_FIRST_CLASS));
                map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                        CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
                if (Maps.getLong(map, "freeLn") == 0D) {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_PURCHASE);
                } else {
                    map.put("purchaseOrFree", SupplyChianConstants.PURCHASE_OR_FREE_FREE);
                }
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "farmId"));
                map.put("farmSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("farmName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return list;
    }

    @Override
    public List<String> editReceiptTempFile(HttpServletRequest request) throws Exception {
        return UploadFileUtil.uploadTempFile(request, "RFI", "SC_M_BILL_RECEIPT_DETAIL", "FILE_NAME");
    }

    @Override
    public void editReceiptFillIn(Map<String, Object> inputMap, String relativePath, String absolutePath, String tempFilePath) throws Exception {
        List<HashMap> listTableRecordList = getJsonList(Maps.getString(inputMap, "listTableRecord"), HashMap.class);
        if (listTableRecordList.isEmpty()) {
            Thrower.throwException(SupplyChianException.INPUT_RECORD_LIST_IS_EMPTY);
        }

        // 真实发票上传
        boolean actualReceiptFlag = true;

        List<HashMap> receiptRecordList = getJsonList(Maps.getString(inputMap, "receiptRecord"), HashMap.class);
        if (receiptRecordList.isEmpty()) {
            if ("Y".equals(Maps.getString(inputMap, "noReceipt"))) {
                // 虚拟发票上传
                actualReceiptFlag = false;
                inputMap.put("totalReceiptPrice", Maps.getDouble(inputMap, "totalMaterialPrice"));
                HashMap<String, Object> receiptRecordMap = new HashMap<String, Object>();
                receiptRecordMap.put("lineNumber", 1L);
                receiptRecordMap.put("receiptNumber", "XXXXXXXX");
                receiptRecordMap.put("price", Maps.getDouble(inputMap, "totalReceiptPrice"));
                receiptRecordMap.put("transportPrice", 0D);
                List<String> fileNameList = new ArrayList<String>();
                fileNameList.add("RFIXXXXXXXX.jpg");
                receiptRecordMap.put("fileNameList", fileNameList);
                receiptRecordList.add(receiptRecordMap);
            } else {
                Thrower.throwException(SupplyChianException.RECEIPT_RECORD_LIST_IS_EMPTY);
            }
        }

        if (Maps.getDouble(inputMap, "totalReceiptPrice") != Maps.getDouble(inputMap, "totalMaterialPrice")) {
            Thrower.throwException(SupplyChianException.PRICE_UNEQUAL);
        }

        Date currentDate = new Date();

        String eventCode = SupplyChianConstants.EVENT_CODE_RECEIPT_FILL_IN;
        String billCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_MATERIAL_RECEIPT_FILL_IN, getEmployeeId(), getCompanyId(), getFarmId());
        ReceiptModel receiptModel = getBean(ReceiptModel.class, inputMap);
        receiptModel.setFarmId(getFarmId());
        receiptModel.setCompanyId(getCompanyId());
        receiptModel.setBillCode(billCode);
        receiptModel.setBillDate(Maps.getDate(inputMap, "billDate"));
        receiptModel.setEventCode(eventCode);
        receiptModel.setReceiptBranchId(Maps.getLong(inputMap, "branchId"));
        // receiptModel.setReceiptFarmId(Maps.getLong(inputMap, "farmId"));
        receiptModel.setInputerId(getEmployeeId());
        receiptModel.setCreateId(getEmployeeId());
        receiptModel.setCreateDate(currentDate);
        receiptMapper.insert(receiptModel);

        // START HANA
        List<HanaReceiptBillDetail> hanaReceiptBillDetailList = new ArrayList<HanaReceiptBillDetail>();
        // END HANA

        // 入库单
        List<InputDetailModel> inputDetailModelList = new ArrayList<InputDetailModel>();
        List<ReceiptInputModel> receiptInputModelList = new ArrayList<ReceiptInputModel>();
        for (Map<String, Object> listTableRecordMap : listTableRecordList) {
            if (Maps.getDouble(listTableRecordMap, "qty", 0D) == 0D) {
                Thrower.throwException(SupplyChianException.RECEIPT_QTY_CAN_NOT_BE_ZERO, Maps.getLong(listTableRecordMap, "lineNumber"));
            }

            SqlCon inputDetailModelSqlCon = new SqlCon();
            inputDetailModelSqlCon.addCondition(Maps.getLong(listTableRecordMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            InputDetailModel inputDetailModel = getList(inputDetailMapper, inputDetailModelSqlCon).get(0);
            // 开票数量 = 已开票数量 + 开票数量（此次）
            Double receiptQty = bigDecimalAdd(inputDetailModel.getReceiptQty(), Maps.getDouble(listTableRecordMap, "qty"));
            // 开票数量不能大于入库数量
            if (receiptQty > inputDetailModel.getInputQty()) {
                Thrower.throwException(SupplyChianException.RECEIPT_QTY_MUST_LESS_THAN_INPUTQTY, Maps.getLong(listTableRecordMap, "lineNumber"));
            }
            inputDetailModel.setReceiptQty(receiptQty);
            inputDetailModelList.add(inputDetailModel);

            ReceiptInputModel receiptInputModel = new ReceiptInputModel();
            receiptInputModel.setStatus(CommonConstants.STATUS);
            receiptInputModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            receiptInputModel.setFarmId(inputDetailModel.getFarmId());
            receiptInputModel.setReceiptId(receiptModel.getRowId());
            receiptInputModel.setMaterialInfoLn(Maps.getLong(listTableRecordMap, "lineNumber"));
            receiptInputModel.setInputId(inputDetailModel.getInputId());
            receiptInputModel.setInputLn(inputDetailModel.getInputLn());
            receiptInputModel.setMaterialId(inputDetailModel.getMaterialId());
            receiptInputModel.setMaterialOnly(inputDetailModel.getMaterialOnly());
            receiptInputModel.setMaterialBatch(inputDetailModel.getMaterialBatch());
            receiptInputModel.setReceiptPrice(Maps.getDouble(listTableRecordMap, "actualPrice"));
            receiptInputModel.setReceiptQty(Maps.getDouble(listTableRecordMap, "qty"));
            receiptInputModel.setTotalPrice(Maps.getDouble(listTableRecordMap, "totalPrice"));
            receiptInputModel.setCreateId(getEmployeeId());
            receiptInputModel.setCreateDate(currentDate);
            receiptInputModelList.add(receiptInputModel);

            // START HANA
            if (HanaUtil.isToHanaCheck(inputDetailModel.getFarmId())) {
                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(inputDetailModel.getFarmId());
                String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                // 2025 - 采购应付发票单草稿 - 表行
                HanaReceiptBillDetail hanaReceiptBillDetail = new HanaReceiptBillDetail();
                // 分公司编码
                hanaReceiptBillDetail.setMTC_BranchID(mtcBranchID);
                // 猪场编码
                hanaReceiptBillDetail.setMTC_DeptID(mtcDeptID);
                // 新农+单据编号
                hanaReceiptBillDetail.setMTC_BaseEntry(String.valueOf(receiptModel.getRowId()));
                // 新农+单据行号
                hanaReceiptBillDetail.setMTC_BaseLine(Maps.getString(listTableRecordMap, "lineNumber"));
                // 新农+采购收货单编号(入库单)
                hanaReceiptBillDetail.setMTC_OrignEntry(String.valueOf(inputDetailModel.getInputId()));
                // 新农+采购收货单行号(入库单)
                hanaReceiptBillDetail.setMTC_OrignLine(String.valueOf(inputDetailModel.getInputLn()));
                // 采购类型
                hanaReceiptBillDetail.setMTC_PurType(Maps.getString(listTableRecordMap, "rebateReason"));
                // 业务类型
                hanaReceiptBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(inputDetailModel.getMaterialId());
                // 物料编号
                hanaReceiptBillDetail.setMTC_ItemCode(mtcItemCode);
                // 仓库编号
                hanaReceiptBillDetail.setMTC_WhsCode(String.valueOf(inputDetailModel.getInputWarehouseId()));
                // 结账数量
                hanaReceiptBillDetail.setMTC_Qty(Maps.getString(listTableRecordMap, "qty"));
                // 结账金额
                hanaReceiptBillDetail.setMTC_Amount(Maps.getString(listTableRecordMap, "totalPrice"));
                hanaReceiptBillDetailList.add(hanaReceiptBillDetail);
            }
            // END HANA
        }

        // SAP用发票号
        List<String> receiptNumberList = new ArrayList<String>();

        // 发票信息
        List<ReceiptDetailModel> receiptDetailModelList = new ArrayList<ReceiptDetailModel>();
        for (Map<String, Object> receiptRecordMap : receiptRecordList) {
            List<String> fileNameList = Maps.getList(receiptRecordMap, "fileNameList");

            if (Maps.isEmpty(receiptRecordMap, "receiptNumber")) {
                Thrower.throwException(SupplyChianException.RECEIPT_RECORD_RECEIPTNUMBER_IS_EMPTY, Maps.getLong(receiptRecordMap, "lineNumber"));
            }

            if (Maps.isEmpty(receiptRecordMap, "price") || Maps.getDouble(receiptRecordMap, "price") <= 0) {
                // 非真实发票（虚拟发票）时，金额可以为0
                if (actualReceiptFlag) {
                    Thrower.throwException(SupplyChianException.RECEIPT_RECORD_PRICE_IS_EMPTY, Maps.getLong(receiptRecordMap, "lineNumber"));
                }
            }

            if (Maps.isEmpty(receiptRecordMap, "transportPrice") || Maps.getDouble(receiptRecordMap, "transportPrice") < 0) {
                Thrower.throwException(SupplyChianException.RECEIPT_RECORD_TRANSPORTPRICE_IS_EMPTY, Maps.getLong(receiptRecordMap, "lineNumber"));
            }

            if (fileNameList == null || fileNameList.isEmpty()) {
                Thrower.throwException(SupplyChianException.RECEIPT_RECORD_FILE_IS_EMPTY, Maps.getLong(receiptRecordMap, "lineNumber"));
            }

            // 目前一行只允许上传一个附件(前台页面已控制)
            if (fileNameList.size() > 1) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "发票明细中,第" + Maps.getLong(receiptRecordMap, "lineNumber")
                        + "行的【上传附件】不能上传多个附件");
            }

            try{
                Long.valueOf(Maps.getString(receiptRecordMap, "receiptNumber"));
            }catch(NumberFormatException e){
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "发票明细中,第" + Maps.getLong(receiptRecordMap, "lineNumber") + "行的【"
                        + Maps.getString(receiptRecordMap, "receiptNumber") + "】不是纯数字，存在非法字符。");
            }
            receiptNumberList.add(Maps.getString(receiptRecordMap, "receiptNumber"));

            String fileName = fileNameList.get(0);
            String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
            String fileSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());

            ReceiptDetailModel receiptDetailModel = new ReceiptDetailModel();
            receiptDetailModel.setStatus(CommonConstants.STATUS);
            receiptDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            receiptDetailModel.setNotes(Maps.getString(receiptRecordMap, "notes"));
            receiptDetailModel.setFarmId(getFarmId());
            receiptDetailModel.setCompanyId(getCompanyId());
            receiptDetailModel.setReceiptId(receiptModel.getRowId());
            receiptDetailModel.setReceiptLn(Maps.getLong(receiptRecordMap, "lineNumber"));
            receiptDetailModel.setReceiptNumber(Maps.getString(receiptRecordMap, "receiptNumber"));
            receiptDetailModel.setPrice(Maps.getDouble(receiptRecordMap, "price"));
            receiptDetailModel.setTransportPrice(Maps.getDouble(receiptRecordMap, "transportPrice"));
            receiptDetailModel.setRelativePath(relativePath);
            receiptDetailModel.setAbsolutePath(absolutePath);
            // 取消上传原名
            // String fileOldNameAndSuffix = Maps.getString(receiptRecordMap, "file");
            // String fileOldName = fileOldNameAndSuffix.substring(0, fileOldNameAndSuffix.lastIndexOf(Maps.getString(fileInfoMap,
            // "fileSuffix")));
            // receiptDetailModel.setFileOldName(fileOldName);
            receiptDetailModel.setFileName(fileNameWithoutSuffix);
            receiptDetailModel.setFileSuffix(fileSuffix);
            receiptDetailModel.setCreateId(getEmployeeId());
            receiptDetailModel.setCreateDate(currentDate);
            receiptDetailModelList.add(receiptDetailModel);

            // 真实发票拷贝
            if(actualReceiptFlag){
                UploadFileUtil.copyFile(tempFilePath, absolutePath, fileName);
            }
        }

        if (inputDetailModelList != null && !inputDetailModelList.isEmpty()) {
            inputDetailMapper.updates(inputDetailModelList);
        }

        if (receiptInputModelList != null && !receiptInputModelList.isEmpty()) {
            receiptInputMapper.inserts(receiptInputModelList);
        }

        if (receiptDetailModelList != null && !receiptDetailModelList.isEmpty()) {
            receiptDetailMapper.inserts(receiptDetailModelList);
        }

        if (!hanaReceiptBillDetailList.isEmpty()) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(receiptModel.getReceiptBranchId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);

            // 2025 - 采购应付发票单草稿 - 表头
            HanaReceiptBill hanaReceiptBill = new HanaReceiptBill();
            // 分公司编码
            hanaReceiptBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaReceiptBill.setMTC_BaseEntry(mtcBranchID + "-" + receiptModel.getRowId());

            Collections.sort(receiptNumberList, new Comparator<String>() {
                @Override
                public int compare(String string1, String string2) {
                    Long value1 = Long.valueOf(string1);
                    Long value2 = Long.valueOf(string2);
                    return value1.compareTo(value2);
                }
            });

            StringBuffer stringBuffer = new StringBuffer();
            // 上一个发票号
            for (int i = 0; i < receiptNumberList.size(); i++) {
                Long receiptNumber = Long.valueOf(receiptNumberList.get(i));
                Long lastReceiptNumber = null;
                if (i != 0) {
                    lastReceiptNumber = Long.valueOf(receiptNumberList.get(i - 1));
                }

                // 第一次遍历lastReceiptNumber为null
                if (lastReceiptNumber == null) {
                    stringBuffer.append(receiptNumber);
                } else {
                    String subString = stringBuffer.substring(stringBuffer.length() - 1, stringBuffer.length());
                    if (receiptNumber == lastReceiptNumber + 1L) {
                        // 如果结尾字符不是拼接符
                        if (!"-".equals(subString)) {
                            stringBuffer.append("-");
                        }
                        if (i == receiptNumberList.size() - 1) {
                            stringBuffer.append(receiptNumber);
                        }
                    } else {
                        // 如果结尾字符是拼接符
                        if ("-".equals(subString)) {
                            // 拼接符上上一个字符
                            stringBuffer.append(lastReceiptNumber);
                        }
                        stringBuffer.append(",");
                        stringBuffer.append(receiptNumber);
                    }
                }
            }

            // 增值税发票号
            hanaReceiptBill.setMTC_FeeCode(stringBuffer.toString());
            // 业务伙伴编号
            hanaReceiptBill.setMTC_CardCode(HanaUtil.getMTC_CardCode(receiptModel.getSupplierId()));
            // 采购日期
            hanaReceiptBill.setMTC_DocDate(receiptModel.getBillDate());
            // 到货日期
            hanaReceiptBill.setMTC_DocDueDate(receiptModel.getBillDate());
            Double logistCost = bigDecimalSubtract(Maps.getDouble(inputMap, "totalReceiptPriceHaveRransportPrice"), Maps.getDouble(inputMap,
                    "totalMaterialPrice"));
            // 采购运费
            hanaReceiptBill.setMTC_LogistCost(String.valueOf(logistCost));
            // 备注
            hanaReceiptBill.setMTC_Comments(receiptModel.getNotes());
            // 表行
            hanaReceiptBill.setDetailList(hanaReceiptBillDetailList);

            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(hanaReceiptBill.getMTC_BranchID());
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            // 业务代码: 物料
            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2025);
            // 新农+单据编号
            mtcITFC.setMTC_DocNum(hanaReceiptBill.getMTC_BaseEntry());
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // DB
            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(receiptModel.getReceiptBranchId()));
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaReceiptBill);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

            hanaCommonService.insertMTC_ITFC(mtcITFC);
        }

    }

    /* END 发票填写 */

    /* START 发票查询 */
    @Override
    public BasePageInfo searchReceiptDetailListToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS receiptId, T2.ROW_ID AS receiptDetailId, T1.BILL_DATE AS billDate,");
        sqlCon.addMainSql(" T1.SUPPLIER_ID AS supplierId, T2.RECEIPT_NUMBER AS receiptNumber, T1.RECEIPT_BRANCH_ID AS companyId,");
        sqlCon.addMainSql(" GROUP_CONCAT(DISTINCT T4.SORT_NAME) AS farmSortName,");
        sqlCon.addMainSql(" T2.PRICE AS price, T2.TRANSPORT_PRICE AS transportPrice,");
        sqlCon.addMainSql(" CONCAT(T2.RELATIVE_PATH, T2.FILE_NAME, T2.FILE_SUFFIX) AS fileName");
        sqlCon.addMainSql(" FROM SC_M_BILL_RECEIPT T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_RECEIPT_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.RECEIPT_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_R_RECEIPT_INPUT T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T1.ROW_ID = T3.RECEIPT_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T4");
        sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0' AND T3.FARM_ID = T4.ROW_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        // START 条件语句-判断记录需要输出
        sqlCon.addMainSql(" AND EXISTS (SELECT 1 FROM");
        sqlCon.addMainSql(" (SELECT R1.ROW_ID AS receiptId");
        sqlCon.addMainSql(" FROM SC_M_BILL_RECEIPT R1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_RECEIPT_DETAIL R2");
        sqlCon.addMainSql(" ON (R2.DELETED_FLAG = '0' AND R1.ROW_ID = R2.RECEIPT_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_R_RECEIPT_INPUT R3");
        sqlCon.addMainSql(" ON (R3.DELETED_FLAG = '0' AND R1.ROW_ID = R3.RECEIPT_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY R4");
        sqlCon.addMainSql(" ON (R4.DELETED_FLAG = '0' AND R3.FARM_ID = R4.ROW_ID)");
        sqlCon.addMainSql(" WHERE R1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getCompanyMark(), " AND (R4.COMPANY_MARK = ?");
        sqlCon.addCondition(getCompanyMark() + ",%", " OR R4.COMPANY_MARK LIKE ?)");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "farmId"), " AND R4.ROW_ID = ?");
        if (!Maps.isEmpty(inputMap, "receiptNumber")) {
            sqlCon.addMainSql(" AND R2.RECEIPT_NUMBER LIKE '" + Maps.getString(inputMap, "receiptNumber").trim() + "%'");
        }
        sqlCon.addCondition(Maps.getLongClass(inputMap, "supplierId"), " AND R1.SUPPLIER_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "companyId"), " AND R1.RECEIPT_BRANCH_ID = ?");
        sqlCon.addMainSql(" GROUP BY R1.ROW_ID");
        sqlCon.addMainSql(" ) R5");
        sqlCon.addMainSql(" WHERE R5.receiptId = T1.ROW_ID)");
        // END 条件语句-判断记录需要输出
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID, T2.ROW_ID");
        // sqlCon.addMainSql("SELECT * FROM(");
        // sqlCon.addMainSql(" SELECT T1.ROW_ID AS receiptId, T2.ROW_ID AS receiptDetailId");
        // sqlCon.addMainSql(" , T1.BILL_DATE AS billDate, T1.SUPPLIER_ID AS supplierId, T2.RECEIPT_NUMBER AS receiptNumber");
        // sqlCon.addMainSql(" , (CASE WHEN LOCATE('有限公司', T3.COMPANY_NAME) = 0 THEN T4.ROW_ID ELSE T3.ROW_ID END) AS companyId");
        // sqlCon.addMainSql(" , T3.ROW_ID AS farmId");
        // sqlCon.addMainSql(" , T2.PRICE AS price,T2.TRANSPORT_PRICE AS transportPrice");
        // sqlCon.addMainSql(" , CONCAT(T2.FILE_NAME,T2.FILE_SUFFIX) AS fileName");
        // sqlCon.addMainSql(" FROM SC_M_BILL_RECEIPT T1");
        // sqlCon.addMainSql(" INNER JOIN SC_M_BILL_RECEIPT_DETAIL T2");
        // sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.ROW_ID = T2.RECEIPT_ID)");
        // sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T3");
        // sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T1.RECEIPT_FARM_ID = T3.ROW_ID)");
        // sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T4");
        // sqlCon.addMainSql(" ON(T4.DELETED_FLAG = '0' AND T3.SUP_COMPANY_ID = T4.ROW_ID)");
        // sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        // sqlCon.addCondition(getCompanyMark(), " AND (T3.COMPANY_MARK = ?");
        // sqlCon.addCondition(getCompanyMark() + ",%", " OR T3.COMPANY_MARK LIKE ?)");
        // sqlCon.addCondition(Maps.getLongClass(inputMap, "farmId"), " AND T3.ROW_ID = ?");
        // if (!Maps.isEmpty(inputMap, "receiptNumber")) {
        // sqlCon.addMainSql(" AND T2.RECEIPT_NUMBER LIKE '" + Maps.getString(inputMap, "receiptNumber") + "%'");
        // }
        // sqlCon.addCondition(Maps.getLongClass(inputMap, "supplierId"), " AND T1.SUPPLIER_ID = ?");
        // sqlCon.addMainSql(" )A");
        // sqlCon.addMainSql(" WHERE 1 = 1");
        // sqlCon.addCondition(Maps.getLongClass(inputMap, "companyId"), " AND A.companyId = ?");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "companyId"));
                map.put("companySortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("companyName", Maps.getString(companyInfoMap, "COMPANY_NAME"));

                // Map<String, String> farmInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "farmId"));
                // map.put("farmSortName", Maps.getString(farmInfoMap, "SORT_NAME"));
                // map.put("farmName", Maps.getString(farmInfoMap, "COMPANY_NAME"));

                Map<String, String> supplierInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(supplierInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(supplierInfoMap, "COMPANY_NAME"));

            }
        }

        return getPageInfo(list);
    }

    @Override
    public BasePageInfo searchReceiptMaterialDetailListToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.BILL_DATE AS inputBillDate, T1.BILL_CODE AS inputBillCode,");
        sqlCon.addMainSql(" T3.MATERIAL_NAME AS materialName, T3.SPEC_ALL AS specAll, T3.UNIT AS unit,");
        sqlCon.addMainSql(" CONCAT(_clearEndZero(T1.RECEIPT_QTY),'/',_clearEndZero(T1.INPUT_QTY)) AS receiptQtyInputQty,");
        sqlCon.addMainSql(" _clearEndZero((SELECT RECEIPT_PRICE FROM SC_R_RECEIPT_INPUT WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND FARM_ID = T1.FARM_ID AND MATERIAL_ID = T1.MATERIAL_ID");
        sqlCon.addMainSql(" AND INPUT_ID = T1.INPUT_ID AND INPUT_LN = T1.INPUT_LN");
        sqlCon.addMainSql(" AND MATERIAL_ONLY = T1.MATERIAL_ONLY AND MATERIAL_BATCH = T1.MATERIAL_BATCH");
        sqlCon.addMainSql(" ORDER BY ROW_ID LIMIT 1)) AS actualPrice,");
        sqlCon.addMainSql(" _clearEndZero(SUM(T2.TOTAL_PRICE)) AS totalPrice,");
        sqlCon.addMainSql(" T3.SUPPLIER_ID AS supplierId, T5.RECEIPT_BRANCH_ID AS companyId, T4.ROW_ID AS farmId,");
        sqlCon.addMainSql(" CAST(CONCAT('[',GROUP_CONCAT(CONCAT('{\"receiptNumber\":\"',T6.RECEIPT_NUMBER,'\",'),");
        sqlCon.addMainSql(" CONCAT('\"fileName\":\"',T6.RELATIVE_PATH,T6.FILE_NAME,T6.FILE_SUFFIX,'\"}')),']') AS CHAR) AS receiptInfo");
        sqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL T1");
        sqlCon.addMainSql(" INNER JOIN SC_R_RECEIPT_INPUT T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T1.FARM_ID = T2.FARM_ID AND T1.MATERIAL_ID = T2.MATERIAL_ID");
        sqlCon.addMainSql(" AND T1.INPUT_ID = T2.INPUT_ID AND T1.INPUT_LN = T2.INPUT_LN");
        sqlCon.addMainSql(" AND T1.MATERIAL_ONLY = T2.MATERIAL_ONLY AND T1.MATERIAL_BATCH = T2.MATERIAL_BATCH)");
        sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T3");
        sqlCon.addMainSql(" ON (T3.DELETED_FLAG = '0' AND T1.MATERIAL_ID = T3.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T4");
        sqlCon.addMainSql(" ON (T4.DELETED_FLAG = '0' AND T1.FARM_ID = T4.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_RECEIPT T5");
        sqlCon.addMainSql(" ON(T5.DELETED_FLAG = '0' AND T2.RECEIPT_ID = T5.ROW_ID)");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_RECEIPT_DETAIL T6");
        sqlCon.addMainSql(" ON (T6.DELETED_FLAG = '0' AND T2.RECEIPT_ID = T6.RECEIPT_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addCondition(getCompanyMark(), " AND (T4.COMPANY_MARK = ?");
        sqlCon.addCondition(getCompanyMark() + ",%", " OR T4.COMPANY_MARK LIKE ?)");
        if (!Maps.isEmpty(inputMap, "receiptNumber")) {
            sqlCon.addMainSql(" AND EXISTS(SELECT 1 FROM (SELECT RECEIPT_ID AS receiptId FROM SC_M_BILL_RECEIPT_DETAIL");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND RECEIPT_NUMBER LIKE '" + Maps.getString(inputMap, "receiptNumber").trim() + "%'");
            sqlCon.addMainSql(" GROUP BY RECEIPT_ID)A WHERE A.receiptId = T5.ROW_ID)");
        }
        if (!Maps.isEmpty(inputMap, "inputBillCode")) {
            sqlCon.addMainSql(" AND T1.BILL_CODE LIKE '" + Maps.getString(inputMap, "inputBillCode").trim() + "%'");
        }
        sqlCon.addCondition(Maps.getString(inputMap, "materialName"), " AND T3.MATERIAL_NAME LIKE ?", true, false);
        sqlCon.addCondition(Maps.getLongClass(inputMap, "farmId"), " AND T1.FARM_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "supplierId"), " AND T3.SUPPLIER_ID = ?");
        sqlCon.addCondition(Maps.getLongClass(inputMap, "companyId"), " AND T5.RECEIPT_BRANCH_ID = ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");
        if ("2".equals(Maps.getString(inputMap, "searchModel"))) {
            sqlCon.addMainSql(" ORDER BY T1.BILL_DATE, T1.INPUT_ID, T1.INPUT_LN, T3.MATERIAL_NAME");
        } else {
            sqlCon.addMainSql(" ORDER BY T3.SUPPLIER_ID, T5.RECEIPT_BRANCH_ID, T4.ROW_ID ,T1.BILL_DATE,");
            sqlCon.addMainSql(" T1.INPUT_ID, T1.INPUT_LN, T3.MATERIAL_NAME");
        }

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "companyId"));
                map.put("companySortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("companyName", Maps.getString(companyInfoMap, "COMPANY_NAME"));

                Map<String, String> farmInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "farmId"));
                map.put("farmSortName", Maps.getString(farmInfoMap, "SORT_NAME"));
                map.put("farmName", Maps.getString(farmInfoMap, "COMPANY_NAME"));

                Map<String, String> supplierInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
                map.put("supplierSortName", Maps.getString(supplierInfoMap, "SORT_NAME"));
                map.put("supplierName", Maps.getString(supplierInfoMap, "COMPANY_NAME"));

                map.put("receiptInfo", getJsonList(Maps.getString(map, "receiptInfo"), HashMap.class));

            }
        }

        return getPageInfo(list);
    }

    @Override
    public List<Map<String, Object>> searchCompanyToList(Map<String, Object> inputMap) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS companyId");
        sqlCon.addMainSql(" FROM HR_M_COMPANY WHERE DELETED_FLAG = '0'");
        sqlCon.addCondition(getCompanyMark(), " AND (COMPANY_MARK = ?");
        sqlCon.addMainSql(" OR COMPANY_MARK LIKE '" + getCompanyMark() + ",%')");
        sqlCon.addMainSql(" AND ACCOUNT_COMPANY_CLASS IN ('2','4')");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> map : list) {
                Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "companyId"));
                map.put("companySortName", Maps.getString(companyInfoMap, "SORT_NAME"));
                map.put("companyName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            }
        }

        return list;
    }

    /* END 发票查询 */

    /* START 物料期末盘存 */
    public List<Map<String, Object>> searchMonthAccountToList(Map<String, Object> inputMap) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        Date currentDate = calendar.getTime();

        // 获取设置的盘存截止天数
        int day = this.getOverDay();
        // 锁定上个月盘存日期
        calendar.set(Calendar.DAY_OF_MONTH, day);
        // 如果这个月是12月，设置天数为5，那么currentMonthLockDate为12-05
        // 如果这个月是1月，设置天数为5，那么currentMonthLockDate为01-05
        Date currentMonthLockDate = calendar.getTime();

        String startDate = null;
        String endDate = null;

        if (currentDate.before(currentMonthLockDate)) {
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = df.format(calendar.getTime());

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = df.format(calendar.getTime());
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = df.format(calendar.getTime());

            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            endDate = df.format(calendar.getTime());
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("warehouseId", Maps.getLong(inputMap, "warehouseId"));
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);

        List<Map<String, Object>> list = monthAccountMapper.searchMonthAccountList(paramMap);
        for (Map<String, Object> map : list) {
            map.putAll(CacheUtil.getMaterialInfo(Maps.getString(map, "materialId"), MaterialInfoEnum.MATERIAL_INFO));
            map.put("existsQtyName", Maps.getString(map, "existsQty") + Maps.getString(map, "unit"));
            Map<String, String> supplierInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
            map.put("supplierSortName", Maps.getString(supplierInfoMap, "SORT_NAME"));
            map.put("supplierName", Maps.getString(supplierInfoMap, "COMPANY_NAME"));
            map.put("materialFirstClassName", CacheUtil.getName(Maps.getString(map, "materialFirstClass"), NameEnum.CODE_NAME,
                    CodeEnum.MATERIAL_FIRST_CLASS));
            map.put("materialSecondClassName", CacheUtil.getNameInCdCodeListByLinkValue(Maps.getString(map, "materialSecondClass"),
                    CodeEnum.MATERIAL_SECOND_CLASS, Maps.getString(map, "materialFirstClass")));
            if (!SupplyChianConstants.MATERIAL_FIRST_CLASS_10.equals(Maps.getString(map, "materialFirstClass"))
                    && !SupplyChianConstants.MATERIAL_FIRST_CLASS_20.equals(Maps.getString(map, "materialFirstClass"))
                    && !SupplyChianConstants.MATERIAL_FIRST_CLASS_21.equals(Maps.getString(map, "materialFirstClass"))
                    && !SupplyChianConstants.MATERIAL_FIRST_CLASS_22.equals(Maps.getString(map, "materialFirstClass"))) {
                map.put("minEffectiveDate", TimeUtil.format(Maps.getDate(map, "minEffectiveDate"), TimeUtil.DATE_FORMAT));
            } else {
                map.put("minEffectiveDate", TimeUtil.format(Maps.getDate(map, "minProductionDate"), TimeUtil.DATE_FORMAT));
            }
        }
        return list;
    }

    public void editMonthAccount(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        Calendar calendar = Calendar.getInstance();

        Date currentDate = calendar.getTime();

        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));

        Date nextStartDate = null;

        // 不是月末
        if (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
            // 获取设置的盘存截止天数
            int day = this.getOverDay();
            // 锁定上个月盘存日期
            calendar.set(Calendar.DAY_OF_MONTH, day);
            // 如果这个月是12月，设置天数为5，那么currentMonthLockDate为12-05
            // 如果这个月是1月，设置天数为5，那么currentMonthLockDate为01-05
            Date currentMonthLockDate = calendar.getTime();
            // 判断这个日期是否在4号之前
            if (!currentDate.before(currentMonthLockDate)) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "当前日期不是盘点时间，盘点时间为月末到下个月" + day + "号之前（不包含" + day + "号）！");
            } else {
                // nextStartDate为这个月的1号
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                nextStartDate = calendar.getTime();
            }
        } else {
            // 是月末
            // nextStartDate为下个月的1号
            nextStartDate = calendar.getTime();

        }

        // 本月月初
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String currentStartDateString = TimeUtil.format(calendar.getTime(), TimeUtil.DATE_FORMAT);
        String nextStartDateString = TimeUtil.format(nextStartDate, TimeUtil.DATE_FORMAT);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("currentStartDate", currentStartDateString);
        param.put("nextStartDate", nextStartDateString);
        param.put("warehouseId", Maps.getLong(inputMap, "warehouseId"));
        param.put("countUser", getEmployeeId());
        param.put("countDate", currentDate);
        param.put("eventCode", SupplyChianConstants.EVENT_CODE_MONTH_ACCOUNT);

        monthAccountMapper.updateCurrentStartDateEndQtyToZero(param);
        monthAccountMapper.deleteNextMonthRecord(param);

        List<MonthAccountModel> monthAccountModelList = new ArrayList<MonthAccountModel>();
        List<MonthAccountModel> insertNextMonthAccountModelList = new ArrayList<MonthAccountModel>();

        for (Map<String, Object> detailMap : detailList) {
            if (Maps.isEmpty(detailMap, "comfirmQty")) {
                Thrower.throwException(SupplyChianException.COMFIRM_QTY_IS_NULL, Maps.getLong(detailMap, "lineNumber"));
            }
            if (Maps.getDouble(detailMap, "existsQty") != Maps.getDouble(detailMap, "comfirmQty")) {
                Thrower.throwException(SupplyChianException.COMFIRM_QTY_IS_NOT_EQUAL_EXISTS_QTY, Maps.getLong(detailMap, "lineNumber"));
            }

            List<HashMap> endQtyInfos = getJsonList(Maps.getString(detailMap, "endQtyInfos"), HashMap.class);

            for (Map<String, Object> endQtyInfoMap : endQtyInfos) {
                MonthAccountModel monthAccountModel = monthAccountMapper.searchById(Maps.getLong(endQtyInfoMap, "rowId"));
                monthAccountModel.setEndQty(Maps.getDouble(endQtyInfoMap, "endQty"));
                monthAccountModelList.add(monthAccountModel);

                MonthAccountModel insertNextMonthAccountModel = new MonthAccountModel();
                insertNextMonthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                insertNextMonthAccountModel.setFarmId(getFarmId());
                insertNextMonthAccountModel.setCompanyId(getCompanyId());
                insertNextMonthAccountModel.setMaterialId(monthAccountModel.getMaterialId());
                insertNextMonthAccountModel.setMaterialOnly(monthAccountModel.getMaterialOnly());
                insertNextMonthAccountModel.setMaterialBatch(monthAccountModel.getMaterialBatch());
                insertNextMonthAccountModel.setWarehouseId(monthAccountModel.getWarehouseId());
                insertNextMonthAccountModel.setStartQty(monthAccountModel.getEndQty());
                insertNextMonthAccountModel.setStartDate(nextStartDate);
                insertNextMonthAccountModel.setLockFlag("N");
                insertNextMonthAccountModel.setEventCode(SupplyChianConstants.EVENT_CODE_MONTH_ACCOUNT);
                insertNextMonthAccountModel.setCreateId(getEmployeeId());
                insertNextMonthAccountModel.setCreateDate(currentDate);
                insertNextMonthAccountModelList.add(insertNextMonthAccountModel);
            }

        }

        monthAccountMapper.inserts(insertNextMonthAccountModelList);
        monthAccountMapper.updates(monthAccountModelList);
    }

    /* END 物料期末盘存 */

    // 判断单据日期大于开始日期
    private void checkBillDateAfterStartDate(String billDateString) throws Exception {

        this.checkBillDate(billDateString, true, false);
    }

    // 判断单据日期小于等于当前时间
    private void checkBillDateBeforeOrEqualCurrentDate(String billDateString) throws Exception {

        this.checkBillDate(billDateString, false, true);
    }

    // 判断单据日期是不是在盘点日期限制区间之内，大于限制时间小于等与当前时间
    private void checkBillDateAfterStartDateAndBeforeOrEqualCurrentDate(String billDateString) throws Exception {

        this.checkBillDate(billDateString, true, true);
    }

    // 判断单据日期是否在最大最小日期内
    private void checkBillDate(String billDateString, boolean checkStartDate, boolean checkEndDate) throws Exception {
        // 单据日期
        Date billDate = TimeUtil.parseDate(billDateString, TimeUtil.DATE_FORMAT);

        // 获取出入库最小最大日期
        Map<String, Date> map = this.getStartDateAndEndDate();

        Date startDate = map.get(SupplyChianConstants.START_DATE);
        Date endDate = map.get(SupplyChianConstants.END_DATE);
        if (checkStartDate) {
            if (billDate.before(startDate)) {
                Thrower.throwException(SupplyChianException.BEFORE_CHECK_START_DATE, TimeUtil.format(startDate, TimeUtil.DATE_FORMAT));
            }
        }

        if (checkEndDate) {
            if (billDate.after(endDate)) {
                Thrower.throwException(SupplyChianException.AFTER_CHECK_END_DATE, TimeUtil.format(endDate, TimeUtil.DATE_FORMAT));
            }
        }
    }

    // 获取出入库最小最大日期
    private Map<String, Date> getStartDateAndEndDate() throws Exception {
        Map<String, Date> map = new HashMap<>();
        // 截止日期
        Date overTimeDate = this.getOverTimeDate();

        // 当前日期
        Calendar calendar = Calendar.getInstance();
        // 清除时分秒毫秒
        calendar.setTime(TimeUtil.parseDate(calendar.getTime(), TimeUtil.DATE_FORMAT));

        Date currentDate = calendar.getTime();

        Date startDate = null;
        Date endDate = null;

        // 当前日期小于截止日期
        if (currentDate.before(overTimeDate)) {
            // 开始时间为上个月1号
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();

        } else {
            // 开始时间为本月1号
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();

        }

        // 结束日期为今天
        endDate = currentDate;

        map.put(SupplyChianConstants.START_DATE, startDate);
        map.put(SupplyChianConstants.END_DATE, endDate);
        return map;
    }

    // 获取猪场截止时间
    private String getOverTimeString() throws Exception {

        return TimeUtil.format(this.getOverTimeDate(), TimeUtil.DATE_FORMAT);
    }

    // 获取猪场截止时间
    private Date getOverTimeDate() throws Exception {
        // 获取盘存截止天数
        int day = this.getOverDay();

        // 获取截止日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        // 清除时分秒
        return TimeUtil.parseDate(calendar.getTime(), TimeUtil.DATE_FORMAT);
    }

    private int getOverDay() {
        // 获取盘存截止天数
        String dayString = paramMapper.getSettingValueByCode(getCompanyId(), getFarmId(), "SC_WLPDJZR");

        int day = 0;
        try {
            day = Integer.valueOf(dayString);

            // 如果设置不合法(不在1-21之间)，默认设置5（4号之前包含4号）
            if (day < 1 || day > 20) {
                day = 5;
            }
        }
        catch (Exception e) {
            // 如果设置不合法，默认设置5（4号之前包含4号）
            day = 5;
        }

        return day;
    }

    private String longArrayListToString(List<Long> longList) {
        if (longList != null && !longList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < longList.size(); i++) {
                stringBuffer.append(longList.get(i).toString());
                if (i != longList.size() - 1) {
                    stringBuffer.append(",");
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }

    private String longArrayListToString(long[] longList) {
        if (longList != null && longList.length > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < longList.length; i++) {
                stringBuffer.append(String.valueOf(longList[i]));
                if (i != longList.length - 1) {
                    stringBuffer.append(",");
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }

    private long[] longArrayListTolong(List<Long> longList) {
        if (longList != null && !longList.isEmpty()) {
            long[] longArray = new long[longList.size()];
            for (int i = 0; i < longList.size(); i++) {
                longArray[i] = longList.get(i);
            }
            return longArray;
        }
        return new long[0];
    }

    // 精确计算（加法）
    private Double bigDecimalAdd(Double d1, Double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.add(b2).doubleValue();
    }

    // 精确计算（减法）
    private Double bigDecimalSubtract(Double d1, Double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.subtract(b2).doubleValue();
    }

    // 精确计算（乘法）
    private Double bigDecimalMultiply(Double d1, Double d2) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.multiply(b2).doubleValue();
    }

    // 精确计算（计算百分比（已加百分号））
    private Double bigDecimalDividePercent(Double d1, Double d2, MathContext mc) {
        if (d2 == 0D) {
            return 100D;
        }
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.divide(b2, mc).multiply(new BigDecimal("100")).doubleValue();
    }

    // 精确计算（除法）
    private Double bigDecimalDivide(Double d1, Double d2) {
        return bigDecimalDivide(d1, d2, 0);
    }

    // 精确计算（除法）
    private Double bigDecimalDivide(Double d1, Double d2, int scale) {
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        if (scale != 0) {
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            return b1.divide(b2).doubleValue();
        }
    }

    // 精确计取余数,返回BigDecimal[0]整除倍数,BigDecimal[1]余数
    // BigDecimal[1] == 0 即整除（取余为0）
    private BigDecimal[] bigDecimalDivideAndRemainder(Double d1, Double d2) {
        if (d2 == 0D) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "被除数不能为0！");
        }
        BigDecimal b1 = BigDecimal.valueOf(d1);
        BigDecimal b2 = BigDecimal.valueOf(d2);
        return b1.divideAndRemainder(b2);
    }

    @Override
    public List<Map<String, Object>> searchDepTree(Long farmId, Long endDepartId) throws Exception {
        farmId = farmId == null ? getFarmId() : farmId;
        List<DeptModel> listDept = deptMapper.searchToList(farmId);
        String farmName = getUserDetail().getFarmName();
        List<Map<String, Object>> list = getDepTree(listDept, null, farmId, farmName);
        if (endDepartId != null) {
            list = TreeBuildUtil.getTreeByEndId(list, "id", endDepartId);
        }

        return list;
    }

    // 组织架构树形结构 获取部门明细
    private List<Map<String, Object>> getDepTree(List<DeptModel> list, Long parentId, Long companyId, String companyName) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (DeptModel model : list) {
            Map<String, Object> map = new HashMap<>();

            Long deptId = model.getRowId();
            Long supDeptId = model.getSupDeptId();

            // 第一条记录
            if (parentId == null) {
                if (supDeptId == null) {
                    map.put("id", model.getRowId());
                    // parentId==null 则为公司下直属部门
                    map.put("parentId", companyId);
                    map.put("deptId", model.getRowId());
                    map.put("text", model.getDeptName());
                    map.put("farmId", model.getFarmId());
                    map.put("type", "部门");
                    map.put("iconCls", "treeDept");
                    map.put("companyName", companyName);
                    List<Map<String, Object>> children = getDepTree(list, deptId, companyId, companyName);
                    map.put("children", children);
                    result.add(map);
                }
            } else if (parentId.equals(supDeptId)) {
                map.put("id", model.getRowId());
                // parentId==null 则为公司下直属部门
                map.put("parentId", "dep" + parentId);
                map.put("deptId", model.getRowId());
                map.put("text", model.getDeptName());
                map.put("farmId", model.getFarmId());
                map.put("type", "部门");
                map.put("iconCls", "treeDept");
                map.put("companyName", companyName);
                List<Map<String, Object>> children = getDepTree(list, deptId, companyId, companyName);
                map.put("children", children);
                result.add(map);
            }
        }
        return result;
    }

    // 判断是否是大丰模式
    // MODEL值为1为大丰模式总公司
    // MODEL值为2为大丰模式子公司
    // MODEL值为-1为非大丰模式公司
    public List<Map<String, Object>> searchDafengModelFlag() throws Exception {
        return searchDafengModelFlag(getFarmId());
    }

    private List<Map<String, Object>> searchDafengModelFlag(Long farmId) throws Exception {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT '1' AS dafengModel FROM SC_R_DAFENGMODEL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(farmId, " AND PARENT_FARM_ID = ?");
        sqlCon.addMainSql(" UNION");
        sqlCon.addMainSql(" SELECT '2' AS dafengModel FROM SC_R_DAFENGMODEL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addConditionWithNull(farmId, " AND CHILD_FARM_ID = ?");
        sqlCon.addMainSql(" UNION");
        sqlCon.addMainSql(" SELECT '-1' AS dafengModel FROM DUAL");
        sqlCon.addMainSql(" LIMIT 1");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        return list;
    }

    // START 更新单价
    @Override
    public BasePageInfo searchUpdatePriceToPage(Map<String, Object> inputMap) throws Exception {
        setToPage();
        // SqlCon sqlCon = new SqlCon();
        // sqlCon.addMainSql("select SUPPLIER_ID as supplierId,MONTH as month");
        // sqlCon.addMainSql(", DATE_RANGE as daterange,CREATE_ID as createid");
        // sqlCon.addMainSql(", NOTES as notes");
        // sqlCon.addMainSql(" FROM SC_M_BILL_PRICE_LIST ");
        // sqlCon.addMainSql(" ORDER BY supplierid,MONTH,daterange");
        // Map<String, String> sqlMap=new HashMap<String,String>();
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" SELECT * FROM SC_M_BILL_PRICE_LIST WHERE DELETED_FLAG=0");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID=? ");
        if (Maps.getString(inputMap, "searchSupplierId") != null) {
            sqlCon.addCondition(Maps.getLongClass(inputMap, "searchSupplierId"), " AND SUPPLIER_ID=? ");
        }
        if (Maps.getString(inputMap, "searchMonth") != null) {
            sqlCon.addCondition(Maps.getLongClass(inputMap, "searchMonth"), " AND MONTH=? ");
        }
        if (Maps.getString(inputMap, "serchDateRange") != null) {
            sqlCon.addCondition(Maps.getLongClass(inputMap, "serchDateRange"), " AND DATE_RANGE=? ");
        }
        List<PriceListModel> priceList = setSql(priceListMapper, sqlCon);

        // List<PriceListModel> priceList = priceListMapper.searchToList(getFarmId());
        for (PriceListModel priceListModel : priceList) {
            Map<String, Object> map = priceListModel.getData();
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
            map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
            // Map<String, String> createInfoMap = CacheUtil.getData("HR_O_EMPLOYEE", Maps.getString(map, "createId"));
            // map.put("createName", CacheUtil.getName(String.valueOf(getEmployeeId()), NameEnum.EMPLOYEE_NAME));
            map.put("monthName", CacheUtil.getName(Maps.getString(map, "month"), NameEnum.CODE_NAME, CodeEnum.MONTH));
            map.put("dateRangeName", CacheUtil.getName(Maps.getString(map, "dateRange"), NameEnum.CODE_NAME, CodeEnum.MONTH_SPLIT));
            map.put("startDateEndDate", TimeUtil.format(Maps.getDate(map, "startDate"), TimeUtil.DATE_FORMAT) + "~" + TimeUtil.format(Maps.getDate(
                    map, "endDate"), TimeUtil.DATE_FORMAT));
            map.put("createName", CacheUtil.getName(Maps.getString(map, "createId"), NameEnum.EMPLOYEE_NAME));
        }

        return getPageInfo(priceList);
    }

    @Override
    public List<Map<String, Object>> searchEditUpdatePriceDetailToList(Map<String, Object> inputMap) {
        // long supplierId = Maps.getLong(inputMap, "supplierId");
        // SqlCon sqlCon = new SqlCon();
        // sqlCon.addMainSql(" SELECT ROW_ID AS materialId,MATERIAL_NAME AS materialName,UNIT AS unit,SPEC_ALL AS specAll");
        // sqlCon.addMainSql(" FROM cd_m_material");
        // sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        // sqlCon.addMainSql(" AND RELATED_ID = ROW_ID");
        // sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        // sqlCon.addCondition(supplierId, " AND SUPPLIER_ID = ?");
        // sqlCon.addMainSql(" ORDER BY MATERIAL_NAME");
        //
        // Map<String, String> sqlMap = new HashMap<String, String>();
        // sqlMap.put("sql", sqlCon.getCondition());
        //
        // List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        //
        // return list;

        long supplierId = Maps.getLong(inputMap, "supplierId");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS materialId,MATERIAL_NAME AS materialName");
        sqlCon.addMainSql(", SPEC_ALL AS specAll,UNIT AS unit");
        sqlCon.addMainSql(" FROM CD_M_MATERIAL");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND RELATED_ID = ROW_ID");
        sqlCon.addCondition(supplierId, " AND SUPPLIER_ID = ?");
        sqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        sqlCon.addMainSql(" ORDER BY MATERIAl_NAME");
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);
        return list;

    }

    @Override
    public List<Map<String, Object>> searchUpdatePriceSupplierToList(Map<String, Object> inputMap) throws Exception {
        // 新农(3),新农翔(305),中粮饲料东台有限公司(441),南通正大有限公司(440),淮安正昌饲料有限公司(798)
        // 鑫天泽（636），北泉天康（637），天康（638）， 郑州新农(648)， 六和饲料（淮安）有限公司(1120)
        Long[] supplierIds = new Long[] { 3L, 305L, 441L, 440L, 798L, 636L, 637L, 638L, 648L, 1120L };
        List<Map<String, Object>> companyList = new ArrayList<Map<String, Object>>();

        for (Long supplierId : supplierIds) {
            Map<String, Object> supplier = new HashMap<String, Object>();
            supplier.put("supplierId", supplierId);
            companyList.add(supplier);
        }

        for (Map<String, Object> map : companyList) {
            Map<String, String> companyInfoMap = CacheUtil.getData("HR_M_COMPANY", Maps.getString(map, "supplierId"));
            map.put("supplierSortName", Maps.getString(companyInfoMap, "SORT_NAME"));
            map.put("supplierName", Maps.getString(companyInfoMap, "COMPANY_NAME"));
        }

        return companyList;
    }

    @Override
    public void editUpdatePrice(Map<String, Object> inputMap, String gridListString) throws Exception {

        List<Map<String, Object>> detailList = getMapList(gridListString);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }

        if (Maps.isEmpty(inputMap, "month")) {
            Thrower.throwException(SupplyChianException.MONTH_IS_NULL);
        }
        if (Maps.isEmpty(inputMap, "monthSplit")) {
            Thrower.throwException(SupplyChianException.MONTHSPLIT_IS_NULL);
        }

        if (Maps.isEmpty(inputMap, "supplierId")) {
            Thrower.throwException(SupplyChianException.SUPPLYID_IS_NULL);
        }

        Calendar calendar = Calendar.getInstance();
        // 去除時分秒
        calendar.setTime(TimeUtil.parseDate(calendar.getTime(), TimeUtil.DATE_FORMAT));
        calendar.set(Calendar.MONTH, Maps.getInt(inputMap, "month") - 1);

        Date startDate = null;
        Date endDate = null;

        // 上半月
        if ("1".equals(Maps.getString(inputMap, "monthSplit"))) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();

            calendar.set(Calendar.DAY_OF_MONTH, 15);
            endDate = calendar.getTime();

            // 下半月
        } else if ("2".equals(Maps.getString(inputMap, "monthSplit"))) {
            calendar.set(Calendar.DAY_OF_MONTH, 16);
            startDate = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            endDate = calendar.getTime();

            // 全月
        } else if ("3".equals(Maps.getString(inputMap, "monthSplit"))) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            endDate = calendar.getTime();

        }

        String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);
        String endDateString = TimeUtil.format(endDate, TimeUtil.DATE_FORMAT);

        SqlCon existsSqlCon = new SqlCon();
        existsSqlCon.addMainSql("SELECT * FROM SC_M_BILL_PRICE_LIST ");
        existsSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' ");
        existsSqlCon.addCondition(getFarmId(), " AND FARM_ID = ?");
        existsSqlCon.addCondition(Maps.getLong(inputMap, "supplierId"), " AND SUPPLIER_ID = ?");
        existsSqlCon.addCondition(startDateString, " AND (( START_DATE >= ?");
        existsSqlCon.addCondition(endDateString, " AND START_DATE <= ?)");
        existsSqlCon.addCondition(startDateString, " OR ( END_DATE >= ?");
        existsSqlCon.addCondition(endDateString, " AND END_DATE <= ?))");
        List<PriceListModel> existsList = setSql(priceListMapper, existsSqlCon);

        if (existsList.size() > 0) {

            if (Maps.getBoolean(inputMap, "isCheck")) {
                // 报错提示前台弹出提示框
                Thrower.throwException(SupplyChianException.SHOW_PROMPT_MESSAGE);
            }

            long[] deleteIds = new long[existsList.size()];
            List<Map<String, Object>> deleteDetails = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < existsList.size(); i++) {
                PriceListModel priceListModel = existsList.get(i);
                deleteIds[i] = priceListModel.getRowId();
                Map<String, Object> deleteMap = new HashMap<String, Object>();
                deleteMap.put("RECORD_CON", "PRICE_LIST_ID");
                deleteMap.put("RECORD_VALUES", priceListModel.getRowId());
                deleteDetails.add(deleteMap);
            }

            priceListMapper.deletes(deleteIds);
            priceListDetailMapper.deletesByCon(deleteDetails, getFarmId());
        }

        Date currentDate = new Date();

        PriceListModel priceListModel = new PriceListModel();
        priceListModel.setStatus(CommonConstants.STATUS);
        priceListModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
        priceListModel.setNotes(Maps.getString(inputMap, "notes"));
        priceListModel.setFarmId(getFarmId());
        priceListModel.setCompanyId(getCompanyId());
        priceListModel.setSupplierId(Maps.getLongClass(inputMap, "supplierId"));
        priceListModel.setMonth(Maps.getLong(inputMap, "month"));
        priceListModel.setDateRange(Maps.getString(inputMap, "monthSplit"));
        priceListModel.setStartDate(startDate);
        priceListModel.setEndDate(endDate);
        priceListModel.setCreateId(getEmployeeId());
        priceListModel.setCreateDate(currentDate);
        priceListMapper.insert(priceListModel);

        List<PriceListDetailModel> priceListDetailModelList = new ArrayList<PriceListDetailModel>();
        List<PurchaseDetailModel> allUpdatePurchaseDetailModelList = new ArrayList<PurchaseDetailModel>();
        Set<Long> purchaseIdSet = new HashSet<Long>();

        for (Map<String, Object> detailMap : detailList) {

            if (Maps.isEmpty(detailMap, "price") || Maps.getDouble(detailMap, "price") == 0D) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "价格不能为0！");
            }

            PriceListDetailModel priceListDetailModel = new PriceListDetailModel();
            priceListDetailModel.setStatus(CommonConstants.STATUS);
            priceListDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            priceListDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            priceListDetailModel.setFarmId(getFarmId());
            priceListDetailModel.setCompanyId(getCompanyId());
            priceListDetailModel.setPriceListId(priceListModel.getRowId());
            priceListDetailModel.setPriceListLn(Maps.getLong(detailMap, "lineNumber"));
            priceListDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
            priceListDetailModel.setPrice(Maps.getDouble(detailMap, "price"));
            priceListDetailModel.setStartDate(startDate);
            priceListDetailModel.setEndDate(endDate);
            priceListDetailModel.setCreateId(getEmployeeId());
            priceListDetailModel.setCreateDate(currentDate);
            priceListDetailModelList.add(priceListDetailModel);

            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T1.ROW_ID AS rowId, T1.PURCHASE_ID AS purchaseId, T1.REQUIRE_FARM AS requireFarm");
            sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL T1");
            sqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T2");
            sqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T1.MATERIAL_ID = T2.ROW_ID)");
            sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T3");
            sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0' AND T1.REQUIRE_FARM = T3.ROW_ID)");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T1.FREE_LN = 0");
            sqlCon.addCondition(Maps.getLong(detailMap, "materialId"), " AND T2.RELATED_ID = ?");
            sqlCon.addCondition(startDateString, " AND T1.BILL_DATE >= ?");
            sqlCon.addCondition(endDateString, " AND T1.BILL_DATE <= ?");
            sqlCon.addMainSql(" AND (T3.COMPANY_MARK = '1,2,3'");
            sqlCon.addMainSql(" OR T3.COMPANY_MARK LIKE '1,2,3,%')");

            List<PurchaseDetailModel> updatePurchaseDetailModelList = setSql(purchaseDetailMapper, sqlCon);

            for (PurchaseDetailModel updatePurchaseDetailMode : updatePurchaseDetailModelList) {
                updatePurchaseDetailMode.setActualPrice(Maps.getDouble(detailMap, "price"));

                // START HANA
                // boolean isToHana = HanaUtil.isToHanaCheck(updatePurchaseDetailMode.getRequireFarm());
                // if (isToHana) {
                // purchaseIdSet.add(updatePurchaseDetailMode.getPurchaseId());
                // }
                // END HANA

                allUpdatePurchaseDetailModelList.add(updatePurchaseDetailMode);
            }
        }

        priceListDetailMapper.inserts(priceListDetailModelList);

        if (allUpdatePurchaseDetailModelList != null && !allUpdatePurchaseDetailModelList.isEmpty()) {
            purchaseDetailMapper.updates(allUpdatePurchaseDetailModelList);
        }

        // START HANA
        if (!purchaseIdSet.isEmpty()) {

            // 第一个String是dbBeanName，第二个String是baseEntry
            Map<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMap = new HashMap<String, Map<String, HanaPurchaseBill>>();

            for (Long purchaseId : purchaseIdSet) {
                SqlCon purchaseDetailSqlCon = new SqlCon();
                purchaseDetailSqlCon.addMainSql("SELECT PURCHASE_ID, PURCHASE_LN, FREE_LN, MATERIAL_ID, SUM(PURCHASE_QTY) AS PURCHASE_QTY");
                purchaseDetailSqlCon.addMainSql(" , ACTUAL_PRICE, BILL_DATE, ARRIVE_DATE, BILL_CODE, REQUIRE_FARM, SUPPLIER_ID");
                purchaseDetailSqlCon.addMainSql(" , REBATE_REASON , SUM(REBATE_PRICE) AS REBATE_PRICE");
                purchaseDetailSqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL");
                purchaseDetailSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1'");
                purchaseDetailSqlCon.addCondition(purchaseId, " AND PURCHASE_ID = ?");
                purchaseDetailSqlCon.addMainSql(" GROUP BY PURCHASE_ID,PURCHASE_LN,FREE_LN");
                purchaseDetailSqlCon.addMainSql(" ORDER BY PURCHASE_ID,PURCHASE_LN,FREE_LN");

                Map<String, String> purchaseDetailSqlMap = new HashMap<String, String>();
                purchaseDetailSqlMap.put("sql", purchaseDetailSqlCon.getCondition());

                List<PurchaseDetailModel> purchaseDetailModelList = setSql(purchaseDetailMapper, purchaseDetailSqlCon);

                for (PurchaseDetailModel purchaseDetailModel : purchaseDetailModelList) {
                    boolean isToHana = HanaUtil.isToHanaCheck(purchaseDetailModel.getRequireFarm());

                    List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = null;
                    String mtcBranchID = null;
                    String mtcDeptID = null;

                    if (isToHana) {
                        Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(purchaseDetailModel.getRequireFarm());
                        mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                        mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                        String mtcCardCode = HanaUtil.getMTC_CardCode(purchaseDetailModel.getSupplierId());

                        String dbBeanName = HanaUtil.getDbBeanName(purchaseDetailModel.getRequireFarm());

                        String baseEntry = mtcBranchID + "-" + String.valueOf(purchaseDetailModel.getPurchaseId()) + "-" + purchaseDetailModel.getBillCode();
                        // 如果dbBeanName不存在
                        if (!dbBeanNameHanaPurchaseBillMap.containsKey(dbBeanName)) {
                            Map<String, HanaPurchaseBill> hanaPurchaseBillMap = new HashMap<String, HanaPurchaseBill>();

                            HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                            // 分公司编码
                            hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                            // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                            // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                            // 业务伙伴编号
                            hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                            // 采购日期
                            hanaPurchaseBill.setMTC_DocDate(purchaseDetailModel.getBillDate());
                            // 到货日期
                            hanaPurchaseBill.setMTC_DocDueDate(purchaseDetailModel.getArriveDate());
                            // 表行
                            hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                            hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                            hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                            dbBeanNameHanaPurchaseBillMap.put(dbBeanName, hanaPurchaseBillMap);
                        }else{
                            // 如果dbBeanName存在,获取hanaPurchaseBillMap
                            Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMap.get(dbBeanName);

                            // 如果baseEntry不存在
                            if (!hanaPurchaseBillMap.containsKey(baseEntry)) {
                                HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                                // 分公司编码
                                hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                                // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                                hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                                // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                                hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                                // 业务伙伴编号
                                hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                                // 采购日期
                                hanaPurchaseBill.setMTC_DocDate(purchaseDetailModel.getBillDate());
                                // 到货日期
                                hanaPurchaseBill.setMTC_DocDueDate(purchaseDetailModel.getArriveDate());
                                // 表行
                                hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                                hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                                hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                            }else{
                                hanaPurchaseBillDetailList = hanaPurchaseBillMap.get(baseEntry).getDetailList();
                            }
                        }

                        String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(purchaseDetailModel.getMaterialId());
                        HanaPurchaseBillDetail hanaPurchaseBillDetail = new HanaPurchaseBillDetail();
                        // 分公司编码
                        hanaPurchaseBillDetail.setMTC_BranchID(mtcBranchID);
                        // 猪场编码
                        hanaPurchaseBillDetail.setMTC_DeptID(mtcDeptID);
                        // 新农+单据编号
                        hanaPurchaseBillDetail.setMTC_BaseEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                        // 新农+单据行号
                        hanaPurchaseBillDetail.setMTC_BaseLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" + String.valueOf(
                                purchaseDetailModel.getFreeLn()));
                        // 采购类型
                        hanaPurchaseBillDetail.setMTC_PurType(purchaseDetailModel.getRebateReason());
                        // 返利金额
                        hanaPurchaseBillDetail.setMTC_Rebate(String.valueOf(purchaseDetailModel.getRebatePrice()));
                        // 业务类型
                        hanaPurchaseBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                        // 物料编号
                        hanaPurchaseBillDetail.setMTC_ItemCode(mtcItemCode);
                        // 采购数量
                        hanaPurchaseBillDetail.setMTC_Qty(String.valueOf(purchaseDetailModel.getPurchaseQty()));
                        // 采购价格
                        hanaPurchaseBillDetail.setMTC_QtyPrice(String.valueOf(purchaseDetailModel.getActualPrice()));
                        // 采购金额
                        Double totalPrice = bigDecimalMultiply(purchaseDetailModel.getPurchaseQty(), purchaseDetailModel.getActualPrice());
                        hanaPurchaseBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                        hanaPurchaseBillDetailList.add(hanaPurchaseBillDetail);
                    }
                }
            }

            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            for (Entry<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMapEntry : dbBeanNameHanaPurchaseBillMap.entrySet()) {
                String dbBeanName = dbBeanNameHanaPurchaseBillMapEntry.getKey();
                Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMapEntry.getValue();
                for (Entry<String, HanaPurchaseBill> hanaPurchaseBillMapEntry : hanaPurchaseBillMap.entrySet()) {
                    HanaPurchaseBill hanaPurchaseBill = hanaPurchaseBillMapEntry.getValue();
                    if (!hanaPurchaseBill.getDetailList().isEmpty()) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(hanaPurchaseBill.getMTC_BranchID());
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:采购订单
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaPurchaseBill.getMTC_BaseEntry());
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_U);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(dbBeanName);
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);
                    }
                }
            }

            if (!mtcITFCList.isEmpty()) {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
        }
        // END HANA
    }
    // END 更新单价

    // START 添加药领用
    @Override
    public List<CompanyModel> searchAdditiveUseMaterialFarmToList(Map<String, Object> inputMap) throws Exception {
        // 调拨关系修改
        SqlCon sqlCon = new SqlCon();
        sqlCon.addCondition(getCompanyMark(), " AND ((COMPANY_MARK = ?");
        sqlCon.addCondition(SupplyChianConstants.ACCOUNT_COMPANY_CLASS_4, " AND ACCOUNT_COMPANY_CLASS = ?)");
        sqlCon.addMainSql(" OR");
        sqlCon.addCondition(getCompanyMark() + ",%", " (COMPANY_MARK LIKE ?");
        sqlCon.addCondition(SupplyChianConstants.ACCOUNT_COMPANY_CLASS_3, " AND ACCOUNT_COMPANY_CLASS = ?))");
        List<CompanyModel> companyModelList = getList(companyMapper, sqlCon);

        return companyModelList;
    }

    @Override
    public void editAdditiveUseMaterial(Map<String, Object> inputMap, String gridListString) throws Exception {
        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }
        checkBillDateAfterStartDate(Maps.getString(inputMap, "billDate"));

        Date currentDate = new Date();

        // 调入猪场farmId
        long allotFarmId = Maps.getLong(inputMap, "allotFarmId");
        // 调入猪场相关信息
        Map<String, String> allotFarmInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(allotFarmId));
        // 调入猪场的CompanyId
        long allotCompanyId = Maps.getLong(allotFarmInfoMap, "SUP_COMPANY_ID");
        // 获取调入猪场的EmployeeId
        long allotEmployeeId = this.getAllotFarmEmployeeId(getUserId(), Maps.getString(allotFarmInfoMap, "COMPANY_CODE"), getFarmId(), allotFarmId);
        // 获取调入猪场的添加药仓库
        long allotWarehouseId = this.getAdditiveWarehouseId(allotFarmId);

        // 是否需要调拨，如果两个场相同，不需要调拨，直接领用
        boolean allotFlag = true;
        if (allotFarmId == getFarmId()) {
            allotFlag = false;
        }

        // START HANA
        boolean isToHana = HanaUtil.isToHanaCheck(allotFarmId);
        String mtcBranchID = null;
        String mtcDeptID = null;
        String mtcFromWhsCode = null;
        String mtcToWhsCode = null;
        // 调拨单
        HanaMaterialAllotBill hanaMaterialAllotBill = null;
        List<HanaMaterialAllotBillDetail> hanaMaterialAllotBillDetailList = null;
        // 领用单
        HanaMaterialOperateBill hanaMaterialOperateBill = null;
        List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = null;

        if (isToHana) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(allotFarmId);
            mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
        }
        // END HANA

        OutputModel allotOutputModel = null;
        InputModel allotInputModel = null;
        if (allotFlag) {
            // 调拨出库
            String allotOutputEventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT;
            String allotOutputBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
            allotOutputModel = getBean(OutputModel.class, inputMap);
            allotOutputModel.setStatus(SupplyChianConstants.OUTPUT_ALLOT_STATUS_COMPLETED);
            allotOutputModel.setFarmId(getFarmId());
            allotOutputModel.setCompanyId(getCompanyId());
            allotOutputModel.setBillCode(allotOutputBillCode);
            allotOutputModel.setEventCode(allotOutputEventCode);
            allotOutputModel.setOutputWarehouseId(Maps.getLong(inputMap, "allotOutputWarehouseId"));
            allotOutputModel.setOutputerId(getEmployeeId());
            allotOutputModel.setAllotDestination(SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_OTHER_FARM);
            allotOutputModel.setOutputToFarmId(Maps.getLong(inputMap, "allotFarmId"));
            allotOutputModel.setUserId(getEmployeeId());
            allotOutputModel.setCreateId(getEmployeeId());
            allotOutputModel.setCreateDate(currentDate);
            allotOutputModel.setPrintTimes(0L);
            allotOutputModel.setOaUsername(getOaUsername());
            outputMapper.insert(allotOutputModel);

            // 调拨入库
            String allotInputEventCode = SupplyChianConstants.EVENT_CODE_INPUT_ALLOT;
            String allotInputBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, allotEmployeeId, allotCompanyId, allotFarmId);
            allotInputModel = getBean(InputModel.class, inputMap);
            allotInputModel.setStatus(CommonConstants.STATUS);
            allotInputModel.setFarmId(allotFarmId);
            allotInputModel.setCompanyId(allotCompanyId);
            allotInputModel.setBillCode(allotInputBillCode);
            allotInputModel.setEventCode(allotInputEventCode);
            allotInputModel.setInputerId(allotEmployeeId);
            allotInputModel.setCreateId(allotEmployeeId);
            allotInputModel.setCreateDate(currentDate);
            allotInputModel.setPrintTimes(0L);
            allotInputModel.setOaUsername(getOaUsername());
            inputMapper.insert(allotInputModel);

            // START HANA
            if (isToHana) {
                // 调拨单
                hanaMaterialAllotBill = new HanaMaterialAllotBill();
                // 分公司编码
                hanaMaterialAllotBill.setMTC_BranchID(mtcBranchID);
                // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                hanaMaterialAllotBill.setMTC_BaseEntry(mtcBranchID + "-" + allotInputModel.getRowId() + "-" + allotInputModel.getBillCode());
                // 调拨日期
                hanaMaterialAllotBill.setMTC_DocDate(allotInputModel.getBillDate());
                // 调拨人
                hanaMaterialAllotBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(allotFarmId, allotEmployeeId));
                // 表行
                hanaMaterialAllotBillDetailList = new ArrayList<HanaMaterialAllotBillDetail>();

                hanaMaterialAllotBill.setDetailList(hanaMaterialAllotBillDetailList);
            }
            // END HANA
        }

        // 领用出库
        String outputUseEventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_USE;
        String outputUseBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, allotEmployeeId, allotCompanyId, allotFarmId);

        Map<String, Object> dafengModelMap = this.searchDafengModelFlag(allotFarmId).get(0);
        inputMap.put("dafengModel", Maps.getString(dafengModelMap, "dafengModel"));
        // 领料号
        String outputUseNumber = createOutputUseNumber(inputMap, allotCompanyId, allotFarmId);

        OutputModel outputUseModel = getBean(OutputModel.class, inputMap);
        outputUseModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
        outputUseModel.setFarmId(allotFarmId);
        outputUseModel.setCompanyId(allotCompanyId);
        outputUseModel.setBillCode(outputUseBillCode);
        outputUseModel.setEventCode(outputUseEventCode);
        outputUseModel.setOutputWarehouseId(allotWarehouseId);
        outputUseModel.setOutputDestination(SupplyChianConstants.OUTPUT_DESTINATION_FARM);
        outputUseModel.setOutputFarmId(allotFarmId);
        outputUseModel.setOutputUseNumber(outputUseNumber);
        outputUseModel.setUserId(allotEmployeeId);
        outputUseModel.setOutputerId(allotEmployeeId);
        outputUseModel.setOutputUseDate(currentDate);
        outputUseModel.setCreateId(allotEmployeeId);
        outputUseModel.setCreateDate(currentDate);
        outputUseModel.setPrintTimes(0L);
        outputUseModel.setOaUsername(getOaUsername());
        outputMapper.insert(outputUseModel);

        // START HANA
        if (isToHana) {
            // 领用单
            hanaMaterialOperateBill = new HanaMaterialOperateBill();
            // 分公司编码
            hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaMaterialOperateBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(outputUseModel.getRowId()) + "-" + outputUseModel
                    .getBillCode());
            // 出库日期
            hanaMaterialOperateBill.setMTC_DocDate(outputUseModel.getBillDate());
            // 领用人
            hanaMaterialOperateBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(allotFarmId, allotEmployeeId));
            // 表行
            hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

            hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);
        }
        // END HANA

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();
        List<InputDetailModel> insertAllotInputDetailModel = new ArrayList<InputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            long allotMaterialId = Maps.getLong(detailMap, "materialId");

            // 调拨搜索调拨场的materialId
            if (allotFlag) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(Maps.getLong(detailMap, "relatedId"), " AND RELATED_ID = ?");
                sqlCon.addCondition(allotFarmId, " AND FARM_ID = ?");
                List<MaterialModel> materialModelList = getList(materialMapper, sqlCon);
                if (materialModelList.isEmpty()) {
                    Thrower.throwException(SupplyChianException.ALLOT_FARM_HAS_NOT_THIS_MATERIAL, Maps.getLong(detailMap, "lineNumber"));
                }

                allotMaterialId = materialModelList.get(0).getRowId();
            }

            Double qty = Maps.getDouble(detailMap, "outputQty");

            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(Maps.getDouble(detailMap, "outputQty")) < 0) {
                Thrower.throwException(SupplyChianException.QTY_IS_NOT_ENOUGH, Maps.getLong(detailMap, "lineNumber"));
            }
            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap,
                    "outputQty")));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            String materialOnly = updateStoreMaterialModel.getMaterialOnly();
            String materialBatch = updateStoreMaterialModel.getMaterialBatch();

            if (allotFlag) {
                // 调拨出库
                OutputDetailModel allotOutputDetailModel = new OutputDetailModel();
                allotOutputDetailModel.setStatus(CommonConstants.STATUS);
                allotOutputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                allotOutputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                allotOutputDetailModel.setFarmId(getFarmId());
                allotOutputDetailModel.setCompanyId(getCompanyId());
                allotOutputDetailModel.setOutputId(allotOutputModel.getRowId());
                allotOutputDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
                allotOutputDetailModel.setInputId(allotInputModel.getRowId());
                allotOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                allotOutputDetailModel.setInputQty(qty);
                allotOutputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                allotOutputDetailModel.setMaterialOnly(materialOnly);
                allotOutputDetailModel.setMaterialBatch(materialBatch);
                allotOutputDetailModel.setPlanQty(qty);
                allotOutputDetailModel.setOutputQty(qty);
                allotOutputDetailModel.setOutputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                allotOutputDetailModel.setOutputerId(allotOutputModel.getOutputerId());
                allotOutputDetailModel.setAllotDestination(SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_OTHER_FARM);
                allotOutputDetailModel.setOutputToFarmId(Maps.getLong(inputMap, "allotFarmId"));
                allotOutputDetailModel.setUserId(getEmployeeId());
                allotOutputDetailModel.setBillCode(allotOutputModel.getBillCode());
                allotOutputDetailModel.setBillDate(allotOutputModel.getBillDate());
                allotOutputDetailModel.setEventCode(allotOutputModel.getEventCode());
                allotOutputDetailModel.setCreateId(allotOutputModel.getCreateId());
                allotOutputDetailModel.setCreateDate(currentDate);
                insertOutputDetailModelList.add(allotOutputDetailModel);

                // 调拨入库
                Map<String, Object> materialBatchMap = getMaterialBatchNumber(materialOnly, materialBatch, allotWarehouseId);

                if (Maps.isEmpty(materialBatchMap, "rowId")) {
                    StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                    storeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    storeMaterialModel.setFarmId(allotFarmId);
                    storeMaterialModel.setCompanyId(allotCompanyId);
                    storeMaterialModel.setMaterialId(allotMaterialId);
                    storeMaterialModel.setMaterialOnly(materialOnly);
                    storeMaterialModel.setMaterialBatch(materialBatch);
                    storeMaterialModel.setProductionDate(updateStoreMaterialModel.getProductionDate());
                    storeMaterialModel.setEffectiveDate(updateStoreMaterialModel.getEffectiveDate());
                    storeMaterialModel.setWarehouseId(allotWarehouseId);
                    storeMaterialModel.setActualQty(0D);
                    storeMaterialModel.setCreateDate(currentDate);
                    storeMaterialMapper.insert(storeMaterialModel);
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Maps.getDate(inputMap, "billDate"));
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date startDate = calendar.getTime();
                String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);

                SqlCon monthAccountExistsSqlCon = new SqlCon();
                monthAccountExistsSqlCon.addMainSql("SELECT ROW_ID AS rowId FROM SC_M_MONTH_ACCOUNT");
                monthAccountExistsSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                monthAccountExistsSqlCon.addCondition(allotMaterialId, " AND MATERIAL_ID = ?");
                monthAccountExistsSqlCon.addCondition(materialOnly, " AND MATERIAL_ONLY = ?");
                monthAccountExistsSqlCon.addCondition(materialBatch, " AND MATERIAL_BATCH = ?");
                monthAccountExistsSqlCon.addCondition(startDateString, " AND START_DATE = ?");
                monthAccountExistsSqlCon.addCondition(allotWarehouseId, " AND WAREHOUSE_ID = ?");
                monthAccountExistsSqlCon.addConditionWithNull(allotFarmId, " AND FARM_ID = ? LIMIT 1");

                List<MonthAccountModel> monthAccountExistsList = setSql(monthAccountMapper, monthAccountExistsSqlCon);

                if (monthAccountExistsList.isEmpty()) {
                    MonthAccountModel monthAccountModel = new MonthAccountModel();
                    monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    monthAccountModel.setFarmId(allotFarmId);
                    monthAccountModel.setCompanyId(allotCompanyId);
                    monthAccountModel.setMaterialId(allotMaterialId);
                    monthAccountModel.setMaterialOnly(materialOnly);
                    monthAccountModel.setMaterialBatch(materialBatch);
                    monthAccountModel.setWarehouseId(allotWarehouseId);
                    monthAccountModel.setStartQty(0D);
                    monthAccountModel.setStartDate(startDate);
                    monthAccountModel.setLockFlag("N");
                    monthAccountModel.setEventCode(allotInputModel.getEventCode());
                    monthAccountModel.setCreateId(allotEmployeeId);
                    monthAccountModel.setCreateDate(currentDate);
                    monthAccountMapper.insert(monthAccountModel);
                }

                InputDetailModel allotInputDetailModel = new InputDetailModel();
                allotInputDetailModel.setStatus(CommonConstants.STATUS);
                allotInputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                allotInputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                allotInputDetailModel.setFarmId(allotFarmId);
                allotInputDetailModel.setCompanyId(allotCompanyId);
                allotInputDetailModel.setInputId(allotInputModel.getRowId());
                allotInputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                allotInputDetailModel.setMaterialId(allotMaterialId);
                allotInputDetailModel.setMaterialOnly(materialOnly);
                allotInputDetailModel.setMaterialBatch(materialBatch);
                allotInputDetailModel.setInputQty(qty);
                allotInputDetailModel.setInputWarehouseId(allotWarehouseId);
                allotInputDetailModel.setInputerId(allotInputModel.getInputerId());
                allotInputDetailModel.setBillCode(allotInputModel.getBillCode());
                allotInputDetailModel.setBillDate(allotInputModel.getBillDate());
                allotInputDetailModel.setEventCode(allotInputModel.getEventCode());
                allotInputDetailModel.setCreateId(allotInputModel.getCreateId());
                allotInputDetailModel.setCreateDate(currentDate);
                insertAllotInputDetailModel.add(allotInputDetailModel);

                // START HANA
                if (isToHana) {
                    String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(allotInputDetailModel.getMaterialId());
                    Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(allotInputDetailModel.getMaterialId()),
                            MaterialInfoEnum.MATERIAL_INFO);
                    // SAP是否库存管理，库存管理才有出库和反出库操作
                    if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                            "materialSecondClass"))) {
                        HanaMaterialAllotBillDetail hanaMaterialAllotBillDetail = new HanaMaterialAllotBillDetail();
                        // 分公司编码
                        hanaMaterialAllotBillDetail.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号
                        hanaMaterialAllotBillDetail.setMTC_BaseEntry(String.valueOf(allotInputDetailModel.getInputId()));
                        // 新农+单据行号
                        hanaMaterialAllotBillDetail.setMTC_BaseLine(String.valueOf(allotInputDetailModel.getInputLn()));
                        // 物料编号
                        hanaMaterialAllotBillDetail.setMTC_ItemCode(mtcItemCode);
                        // 批次编号
                        hanaMaterialAllotBillDetail.setMTC_BatchNum(allotInputDetailModel.getMaterialBatch());
                        // 调出仓库编号
                        hanaMaterialAllotBillDetail.setMTC_FromWhsCode(String.valueOf(allotOutputDetailModel.getOutputWarehouseId()));
                        // 调入仓库编号
                        hanaMaterialAllotBillDetail.setMTC_ToWhsCode(String.valueOf(allotInputDetailModel.getInputWarehouseId()));
                        // 调拨数量
                        hanaMaterialAllotBillDetail.setMTC_Qty(String.valueOf(allotInputDetailModel.getInputQty()));

                        hanaMaterialAllotBillDetailList.add(hanaMaterialAllotBillDetail);

                        if (mtcFromWhsCode == null) {
                            mtcFromWhsCode = String.valueOf(allotOutputDetailModel.getOutputWarehouseId());
                        }

                        if (mtcToWhsCode == null) {
                            mtcToWhsCode = String.valueOf(allotInputDetailModel.getInputWarehouseId());
                        }
                    }
                }
                // END HANA
            }

            // 出库领用
            OutputDetailModel outputUseDetailModel = new OutputDetailModel();
            outputUseDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
            outputUseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            outputUseDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            outputUseDetailModel.setFarmId(allotFarmId);
            outputUseDetailModel.setCompanyId(allotCompanyId);
            outputUseDetailModel.setOutputId(outputUseModel.getRowId());
            outputUseDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
            outputUseDetailModel.setMaterialId(allotMaterialId);
            outputUseDetailModel.setMaterialOnly(materialOnly);
            outputUseDetailModel.setMaterialBatch(materialBatch);
            outputUseDetailModel.setPlanQty(qty);
            outputUseDetailModel.setOutputQty(qty);
            outputUseDetailModel.setOutputWarehouseId(allotWarehouseId);
            outputUseDetailModel.setOutputDestination(SupplyChianConstants.OUTPUT_DESTINATION_FARM);
            outputUseDetailModel.setOutputFarmId(allotFarmId);
            outputUseDetailModel.setPigType("3");
            outputUseDetailModel.setOutputerId(outputUseModel.getOutputerId());
            outputUseDetailModel.setOutputUseDate(currentDate);
            outputUseDetailModel.setUserId(outputUseModel.getUserId());
            outputUseDetailModel.setBillCode(outputUseModel.getBillCode());
            outputUseDetailModel.setBillDate(outputUseModel.getBillDate());
            outputUseDetailModel.setEventCode(outputUseModel.getEventCode());
            outputUseDetailModel.setCreateId(outputUseModel.getCreateId());
            outputUseDetailModel.setCreateDate(currentDate);
            insertOutputDetailModelList.add(outputUseDetailModel);

            // START HANA
            if (isToHana) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(outputUseDetailModel.getMaterialId());
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(outputUseDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                    // 分公司编码
                    hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                    // 添加药领用到全场
                    // 猪舍编号
                    // hanaMaterialOperateBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(outputUseDetailModel.getOutputHouseId()));
                    // 新农+单据编号
                    hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(outputUseDetailModel.getOutputId()));
                    // 新农+单据行号
                    hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(outputUseDetailModel.getOutputLn()));
                    // 物料编号
                    hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialOperateBillDetail.setMTC_BatchNum(outputUseDetailModel.getMaterialBatch());
                    // 仓库编号
                    hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(outputUseDetailModel.getOutputWarehouseId()));
                    // 领用数量
                    hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(outputUseDetailModel.getOutputQty()));
                    // 如果领用的为兽药，默认为A - 商品猪
                    // 核算对象：A - 商品猪 B - 生产猪C - 后备猪
                    if (SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialInfo, "materialFirstClass"))) {
                        // 商品猪
                        hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                    } else {
                        if ("1".equals(outputUseDetailModel.getPigType())) {
                            // 后备猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_C);
                        } else if ("2".equals(outputUseDetailModel.getPigType())) {
                            // 生产猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_B);
                        } else {
                            // 商品猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                        }
                    }

                    hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                }
            }
            // END HANA

        }

        // START HANA
        if (allotFlag && isToHana) {
            // 从明细中取第一条调出仓库和调入仓库
            // 调出仓库
            hanaMaterialAllotBill.setMTC_FromWhsCode(mtcFromWhsCode);
            // 调入仓库
            hanaMaterialAllotBill.setMTC_ToWhsCode(mtcToWhsCode);
        }
        // END HANA

        storeMaterialMapper.updates(updateStoreMaterialModelList);
        outputDetailMapper.inserts(insertOutputDetailModelList);
        if (!insertAllotInputDetailModel.isEmpty()) {
            inputDetailMapper.inserts(insertAllotInputDetailModel);
        }

        // 调拨单
        if (allotFlag && isToHana) {
            if (!hanaMaterialAllotBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialAllotBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码：库存调拨
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2090);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialAllotBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(allotFarmId));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialAllotBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }

        if (isToHana) {
            if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码:生产领用
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2040);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(allotFarmId));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }

    }

    // 获取调拨入库场的EmployeeId
    private long getAllotFarmEmployeeId(long userId, String companyCode, long farmId, long allotFarmId) throws Exception {
        if (farmId != allotFarmId) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T1.EMPLOYEE_ID AS employeeId FROM HR_M_USER T1");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
            sqlCon.addCondition(companyCode, " AND T1.COMPANY_CODE = ?");
            sqlCon.addMainSql(" AND EXISTS(");
            sqlCon.addMainSql(" SELECT 1");
            sqlCon.addMainSql(" FROM (");
            sqlCon.addMainSql(" SELECT BIND_USER_ID, FARM_ID FROM HR_R_FARM_BIND");
            sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            sqlCon.addCondition(userId, " AND USER_ID = ?");
            sqlCon.addMainSql(" UNION");
            sqlCon.addMainSql(" SELECT T1.BIND_USER_ID, T1.FARM_ID FROM HR_R_FARM_BIND T1");
            sqlCon.addMainSql(" INNER JOIN HR_R_FARM_BIND T2");
            sqlCon.addMainSql(" ON (T1.USER_ID = T2.BIND_USER_ID AND T2.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
            sqlCon.addCondition(userId, " AND T2.USER_ID = ?");
            sqlCon.addConditionWithNull(farmId, " AND T1.FARM_ID <> ?");
            sqlCon.addMainSql(" ) A");
            sqlCon.addConditionWithNull(allotFarmId, " WHERE A.FARM_ID = ?");
            sqlCon.addMainSql(" AND T1.ROW_ID = A.BIND_USER_ID LIMIT 1)");

            Map<String, String> map = new HashMap<String, String>();
            map.put("sql", sqlCon.getCondition());

            List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
            if (list == null || list.isEmpty()) {
                String companyName =  CacheUtil.getName(String.valueOf(allotFarmId), NameEnum.COMPANY_NAME);
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "你没有与猪场【" + companyName + "】存在【绑定账号】，请先创建绑定账号！。。。");
            }
            return Maps.getLong(list.get(0), "employeeId");
        } else {

            return getEmployeeId();
        }
    }

    // 获取调拨入库场的添加药仓库
    private long getAdditiveWarehouseId(long farmId) throws Exception {
        List<Map<String, Object>> list = this.getWarehouseIdByWarehouseCategory(farmId, SupplyChianConstants.WAREHOUSE_CATEGORY_ADDITIVE);
        if (list == null || list.isEmpty()) {
            String companyName = CacheUtil.getName(String.valueOf(farmId), NameEnum.COMPANY_NAME);
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "猪场【" + companyName + "】中【不存在实验室仓库】或【没有启用的实验室仓库】！");
        }
        return Maps.getLong(list.get(0), "warehouseId");
    }
    // END 添加药领用

    // 按照warehouseCategory获取仓库
    private List<Map<String, Object>> getWarehouseIdByWarehouseCategory(long farmId, String warehouseCategory) {
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT ROW_ID AS warehouseId");
        sqlCon.addMainSql(" FROM SC_M_WAREHOUSE");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND STATUS = '2'");
        sqlCon.addCondition(farmId, " AND FARM_ID = ?");
        sqlCon.addCondition(warehouseCategory, " AND WAREHOUSE_CATEGORY = ?");

        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());

        List<Map<String, Object>> list = paramMapper.getObjectInfos(map);
        return list;
    }

    // START 实验室领用
    @Override
    public List<CompanyModel> searchLaboratoryUseMaterialFarmToList(Map<String, Object> inputMap) throws Exception {
        // 存在猪的集团内公司
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" AND COMPANY_MARK LIKE '1,2,3,%'");
        sqlCon.addMainSql(" AND ROW_ID <> 469 AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1 FROM PP_L_PIG WHERE DELETED_FLAG = '0' AND PIG_CLASS <= 18 AND ORIGIN <> 3");
        sqlCon.addMainSql(" AND FARM_ID = HR_M_COMPANY.ROW_ID LIMIT 1)");
        sqlCon.addMainSql(" ORDER BY ROW_ID");
        List<CompanyModel> companyModelList = getList(companyMapper, sqlCon);

        return companyModelList;
    }

    @Override
    public void editLaboratoryUseMaterial(Map<String, Object> inputMap, String gridListString) throws Exception {

        List<HashMap> detailList = getJsonList(gridListString, HashMap.class);
        if (detailList.isEmpty()) {
            Thrower.throwException(SupplyChianException.NOT_HAVE_EFFECTIVE_DETAIL);
        }
        checkBillDateAfterStartDate(Maps.getString(inputMap, "billDate"));

        Date currentDate = new Date();

        // 调出（销售）猪场farmId
        long allotOutOrSaleFarmId = getFarmId();
        // 调出（销售）猪场CompanyId
        long allotOutOrSaleCompanyId = getCompanyId();
        // 调出（销售）仓库
        long allotOutOrSaleWarehouseId = Maps.getLong(inputMap, "allotOutputWarehouseId");
        // 调出（销售）EmployeeId
        long allotOutOrSaleEmployeeId = getEmployeeId();
        // 调出（销售）OaUerName
        String allotOutOrSaleOaUsername = getOaUsername();

        // 领用的FarmId
        long useFarmId = Maps.getLong(inputMap, "allotFarmId");
        // 领用的猪场相关信息
        Map<String, String> useFarmInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(useFarmId));
        // 领用的猪场的CompanyId
        long useCompanyId = Maps.getLong(useFarmInfoMap, "SUP_COMPANY_ID");
        // 领用的EmployeeId
        long useEmployeeId = this.getAllotFarmEmployeeId(getUserId(), Maps.getString(useFarmInfoMap, "COMPANY_CODE"), allotOutOrSaleFarmId,
                useFarmId);

        // 调入（采购）猪场farmId
        Long allotInOrPurchaseFarmId = null;

        // 判断是否是大丰模式
        String dafengModel = Maps.getString(searchDafengModelFlag(useFarmId).get(0), "dafengModel");

        if (SupplyChianConstants.DAFENG_MODEL_PARENT.equals(dafengModel) || SupplyChianConstants.DAFENG_MODEL_NOT.equals(dafengModel)) {
            // 如果是大丰模式主场 || 如果不是大丰模式
            // 调入（采购）猪场farmId = 领用猪场farmId
            allotInOrPurchaseFarmId = useFarmId;

        } else if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(dafengModel)) {
            // 如果是大丰模式子场，调入（采购）到主场
            // 调入（采购）猪场farmId = 领用猪场companyId
            allotInOrPurchaseFarmId = useCompanyId;

        }

        // 调入（采购）猪场相关信息
        Map<String, String> allotInOrPurchaseFarmInfoMap = CacheUtil.getData("HR_M_COMPANY", String.valueOf(allotInOrPurchaseFarmId));
        // 调入（采购）猪场的CompanyId
        long allotInOrPurchaseCompanyId = Maps.getLong(allotInOrPurchaseFarmInfoMap, "SUP_COMPANY_ID");
        // 获取调入（采购）猪场的EmployeeId
        long allotInOrPurchaseEmployeeId = this.getAllotFarmEmployeeId(getUserId(), Maps.getString(allotInOrPurchaseFarmInfoMap, "COMPANY_CODE"),
                allotOutOrSaleFarmId, allotInOrPurchaseFarmId);
        // 获取调入（采购）猪场的实验室仓库
        long allotInOrPurchaseWarehouseId = this.getLaboratoryWarehouseId(allotInOrPurchaseFarmId);

        // START HANA
        // 调拨标识
        boolean allotFlag = false;
        // 销售标识
        boolean saleFlag = false;
        // 调拨出库（销售）方
        // boolean allotOutOrSaleIsToHana = HanaUtil.isToHanaCheck(allotOutOrSaleFarmId);
        String allotOutOrSaleMtcBranchID = null;
        // String allotOutOrSaleMtcDeptID = null;
        // 领用（采购）方
        // boolean allotInOrPurchaseIsToHana = HanaUtil.isToHanaCheck(allotInOrPurchaseFarmId);
        String allotInOrPurchaseMtcBranchID = null;
        String allotInOrPurchaseMtcDeptID = null;
        String allotInOrPurchaseMtcFromWhsCode = null;
        String allotInOrPurchaseMtcToWhsCode = null;

        // 两场相同，不需要调拨（allotFlag = false），不需要销售(saleFlag = false)
        // 如果两个场不同
        if (allotInOrPurchaseFarmId != allotOutOrSaleFarmId) {
            // 调拨出库（销售）法人
            Map<String, String> allotOutOrSaleMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(allotOutOrSaleFarmId);
            allotOutOrSaleMtcBranchID = Maps.getString(allotOutOrSaleMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            // allotOutOrSaleMtcDeptID = Maps.getString(allotOutOrSaleMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            // 领用（采购）猪场法人
            Map<String, String> allotInOrPurchaseMtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(allotInOrPurchaseFarmId);
            allotInOrPurchaseMtcBranchID = Maps.getString(allotInOrPurchaseMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            allotInOrPurchaseMtcDeptID = Maps.getString(allotInOrPurchaseMtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            if (allotOutOrSaleMtcBranchID.equals(allotInOrPurchaseMtcBranchID)) {
                // 法人相同：调拨
                allotFlag = true;
            } else {
                // 法人不同：销售
                saleFlag = true;
            }
        }
        // END HANA

        OutputModel allotOutputModel = null;
        InputModel allotInputModel = null;
        // 调拨
        if (allotFlag) {
            // 调拨出库
            String allotOutputEventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT;
            String allotOutputBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
            allotOutputModel = getBean(OutputModel.class, inputMap);
            allotOutputModel.setStatus(SupplyChianConstants.OUTPUT_ALLOT_STATUS_COMPLETED);
            allotOutputModel.setFarmId(allotOutOrSaleFarmId);
            allotOutputModel.setCompanyId(allotOutOrSaleCompanyId);
            allotOutputModel.setBillCode(allotOutputBillCode);
            allotOutputModel.setEventCode(allotOutputEventCode);
            // 调出仓库
            allotOutputModel.setOutputWarehouseId(allotOutOrSaleWarehouseId);
            allotOutputModel.setOutputerId(allotOutOrSaleEmployeeId);
            allotOutputModel.setAllotDestination(SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_OTHER_FARM);
            // 调入猪场
            allotOutputModel.setOutputToFarmId(allotInOrPurchaseFarmId);
            allotOutputModel.setUserId(allotOutOrSaleEmployeeId);
            allotOutputModel.setCreateId(allotOutOrSaleEmployeeId);
            allotOutputModel.setCreateDate(currentDate);
            allotOutputModel.setPrintTimes(0L);
            allotOutputModel.setOaUsername(allotOutOrSaleOaUsername);
            outputMapper.insert(allotOutputModel);

            // 调拨入库
            String allotInputEventCode = SupplyChianConstants.EVENT_CODE_INPUT_ALLOT;
            String allotInputBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, allotInOrPurchaseEmployeeId,
                    allotInOrPurchaseCompanyId, allotInOrPurchaseFarmId);
            allotInputModel = getBean(InputModel.class, inputMap);
            allotInputModel.setStatus(CommonConstants.STATUS);
            allotInputModel.setFarmId(allotInOrPurchaseFarmId);
            allotInputModel.setCompanyId(allotInOrPurchaseCompanyId);
            allotInputModel.setBillCode(allotInputBillCode);
            allotInputModel.setEventCode(allotInputEventCode);
            allotInputModel.setInputerId(allotInOrPurchaseEmployeeId);
            allotInputModel.setCreateId(allotInOrPurchaseEmployeeId);
            allotInputModel.setCreateDate(currentDate);
            allotInputModel.setPrintTimes(0L);
            allotInputModel.setOaUsername(allotOutOrSaleOaUsername);
            inputMapper.insert(allotInputModel);
        }

        OutputModel saleOutputModel = null;
        InputModel purchaseInputModel = null;
        // 销售
        if (saleFlag) {
            // 销售出库
            String saleOutputEventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_SALE;
            String saleOutputBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, getEmployeeId(), getCompanyId(), getFarmId());
            saleOutputModel = getBean(OutputModel.class, inputMap);
            saleOutputModel.setStatus(SupplyChianConstants.OUTPUT_SALE_STATUS_COMPLETED);
            saleOutputModel.setFarmId(allotOutOrSaleFarmId);
            saleOutputModel.setCompanyId(allotOutOrSaleCompanyId);
            saleOutputModel.setBillCode(saleOutputBillCode);
            saleOutputModel.setEventCode(saleOutputEventCode);
            saleOutputModel.setOutputWarehouseId(allotOutOrSaleWarehouseId);
            saleOutputModel.setOutputerId(allotOutOrSaleEmployeeId);
            saleOutputModel.setAllotDestination(SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_OTHER_FARM);
            saleOutputModel.setOutputToFarmId(allotInOrPurchaseFarmId);
            saleOutputModel.setUserId(allotOutOrSaleEmployeeId);
            saleOutputModel.setCreateId(allotOutOrSaleEmployeeId);
            saleOutputModel.setCreateDate(currentDate);
            saleOutputModel.setPrintTimes(0L);
            saleOutputModel.setOaUsername(allotOutOrSaleOaUsername);
            outputMapper.insert(saleOutputModel);

            // 采购入库
            String purchaseInputEventCode = SupplyChianConstants.EVENT_CODE_INPUT_SALE;
            String purchaseInputBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_INPUT_STORE, allotInOrPurchaseEmployeeId,
                    allotInOrPurchaseCompanyId, allotInOrPurchaseFarmId);
            purchaseInputModel = getBean(InputModel.class, inputMap);
            purchaseInputModel.setStatus(CommonConstants.STATUS);
            purchaseInputModel.setFarmId(allotInOrPurchaseFarmId);
            purchaseInputModel.setCompanyId(allotInOrPurchaseCompanyId);
            purchaseInputModel.setBillCode(purchaseInputBillCode);
            purchaseInputModel.setEventCode(purchaseInputEventCode);
            purchaseInputModel.setInputerId(allotInOrPurchaseEmployeeId);
            purchaseInputModel.setCreateId(allotInOrPurchaseEmployeeId);
            purchaseInputModel.setCreateDate(currentDate);
            purchaseInputModel.setPrintTimes(0L);
            purchaseInputModel.setOaUsername(allotOutOrSaleOaUsername);
            inputMapper.insert(purchaseInputModel);
        }

        // 领用出库
        String outputUseEventCode = SupplyChianConstants.EVENT_CODE_OUTPUT_USE;
        String outputUseBillCode = null;
        if (SupplyChianConstants.DAFENG_MODEL_CHILD.equals(dafengModel)) {
            outputUseBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, allotInOrPurchaseEmployeeId, allotInOrPurchaseCompanyId,
                    allotInOrPurchaseFarmId);
        } else {
            outputUseBillCode = ParamUtil.getBCode(SupplyChianConstants.BCODE_OUTPUT_STORE, useEmployeeId, useCompanyId, useFarmId);
        }

        inputMap.put("dafengModel", dafengModel);
        // 领料号
        String outputUseNumber = createOutputUseNumber(inputMap, useCompanyId, useFarmId);

        OutputModel outputUseModel = getBean(OutputModel.class, inputMap);
        outputUseModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
        outputUseModel.setFarmId(useFarmId);
        outputUseModel.setCompanyId(useCompanyId);
        outputUseModel.setBillCode(outputUseBillCode);
        outputUseModel.setEventCode(outputUseEventCode);
        outputUseModel.setOutputWarehouseId(allotInOrPurchaseWarehouseId);
        outputUseModel.setOutputDestination(SupplyChianConstants.OUTPUT_DESTINATION_FARM);
        outputUseModel.setOutputFarmId(useFarmId);
        outputUseModel.setOutputUseNumber(outputUseNumber);
        outputUseModel.setUserId(useEmployeeId);
        outputUseModel.setOutputerId(allotInOrPurchaseEmployeeId);
        outputUseModel.setOutputUseDate(currentDate);
        outputUseModel.setCreateId(useEmployeeId);
        outputUseModel.setCreateDate(currentDate);
        outputUseModel.setPrintTimes(0L);
        outputUseModel.setOaUsername(allotOutOrSaleOaUsername);
        outputMapper.insert(outputUseModel);

        List<StoreMaterialModel> updateStoreMaterialModelList = new ArrayList<StoreMaterialModel>();
        List<OutputDetailModel> insertOutputDetailModelList = new ArrayList<OutputDetailModel>();
        List<InputDetailModel> insertAllotInputDetailModel = new ArrayList<InputDetailModel>();

        for (Map<String, Object> detailMap : detailList) {
            long allotInOrPurchaseMaterialId = Maps.getLong(detailMap, "materialId");

            // 搜索调入（采购）场相对应的物料Id
            if (allotFlag || saleFlag) {
                SqlCon sqlCon = new SqlCon();
                sqlCon.addCondition(Maps.getLong(detailMap, "relatedId"), " AND RELATED_ID = ?");
                sqlCon.addCondition(allotInOrPurchaseFarmId, " AND FARM_ID = ?");
                List<MaterialModel> materialModelList = getList(materialMapper, sqlCon);
                if (materialModelList.isEmpty()) {
                    Thrower.throwException(SupplyChianException.ALLOT_FARM_HAS_NOT_THIS_MATERIAL, Maps.getLong(detailMap, "lineNumber"));
                }

                allotInOrPurchaseMaterialId = materialModelList.get(0).getRowId();
            }

            Double qty = Maps.getDouble(detailMap, "outputQty");

            SqlCon storeMaterialSqlCon = new SqlCon();
            storeMaterialSqlCon.addConditionWithNull(Maps.getLong(detailMap, "rowId"), " AND ROW_ID = ? FOR UPDATE");
            StoreMaterialModel updateStoreMaterialModel = getList(storeMaterialMapper, storeMaterialSqlCon).get(0);
            if (updateStoreMaterialModel.getActualQty().compareTo(Maps.getDouble(detailMap, "outputQty")) < 0) {
                Thrower.throwException(SupplyChianException.QTY_IS_NOT_ENOUGH, Maps.getLong(detailMap, "lineNumber"));
            }
            updateStoreMaterialModel.setActualQty(bigDecimalSubtract(updateStoreMaterialModel.getActualQty(), Maps.getDouble(detailMap,
                    "outputQty")));
            updateStoreMaterialModelList.add(updateStoreMaterialModel);

            String materialOnly = updateStoreMaterialModel.getMaterialOnly();
            String materialBatch = updateStoreMaterialModel.getMaterialBatch();

            if (allotFlag || saleFlag) {
                Long allotOutOrSaleOutputId = null;
                String allotOutOrSaleBillCode = null;
                Date allotOutOrSaleBillDate = null;
                String allotOutOrSaleEventCode = null;

                Long allotInOrPurchaseInputId = null;
                String allotInOrPurchaseBillCode = null;
                Date allotInOrPurchaseBillDate = null;
                String allotInOrPurchaseEventCode = null;
                if (allotFlag) {
                    allotOutOrSaleOutputId = allotOutputModel.getRowId();
                    allotOutOrSaleBillCode = allotOutputModel.getBillCode();
                    allotOutOrSaleBillDate = allotOutputModel.getBillDate();
                    allotOutOrSaleEventCode = allotOutputModel.getEventCode();

                    allotInOrPurchaseInputId = allotInputModel.getRowId();
                    allotInOrPurchaseBillCode = allotInputModel.getBillCode();
                    allotInOrPurchaseBillDate = allotInputModel.getBillDate();
                    allotInOrPurchaseEventCode = allotInputModel.getEventCode();
                }
                if (saleFlag) {
                    allotOutOrSaleOutputId = saleOutputModel.getRowId();
                    allotOutOrSaleBillCode = saleOutputModel.getBillCode();
                    allotOutOrSaleBillDate = saleOutputModel.getBillDate();
                    allotOutOrSaleEventCode = saleOutputModel.getEventCode();

                    allotInOrPurchaseInputId = purchaseInputModel.getRowId();
                    allotInOrPurchaseBillCode = purchaseInputModel.getBillCode();
                    allotInOrPurchaseBillDate = purchaseInputModel.getBillDate();
                    allotInOrPurchaseEventCode = purchaseInputModel.getEventCode();
                }
                // 调拨出库
                OutputDetailModel allotOutOrSaleOutputDetailModel = new OutputDetailModel();
                allotOutOrSaleOutputDetailModel.setStatus(CommonConstants.STATUS);
                allotOutOrSaleOutputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                allotOutOrSaleOutputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                allotOutOrSaleOutputDetailModel.setFarmId(allotOutOrSaleFarmId);
                allotOutOrSaleOutputDetailModel.setCompanyId(allotOutOrSaleCompanyId);
                allotOutOrSaleOutputDetailModel.setOutputId(allotOutOrSaleOutputId);
                allotOutOrSaleOutputDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
                allotOutOrSaleOutputDetailModel.setInputId(allotInOrPurchaseInputId);
                allotOutOrSaleOutputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                allotOutOrSaleOutputDetailModel.setInputQty(qty);
                allotOutOrSaleOutputDetailModel.setMaterialId(Maps.getLong(detailMap, "materialId"));
                allotOutOrSaleOutputDetailModel.setMaterialOnly(materialOnly);
                allotOutOrSaleOutputDetailModel.setMaterialBatch(materialBatch);
                allotOutOrSaleOutputDetailModel.setPlanQty(qty);
                allotOutOrSaleOutputDetailModel.setOutputQty(qty);
                allotOutOrSaleOutputDetailModel.setOutputWarehouseId(Maps.getLong(detailMap, "warehouseId"));
                allotOutOrSaleOutputDetailModel.setOutputerId(allotOutOrSaleEmployeeId);
                allotOutOrSaleOutputDetailModel.setAllotDestination(SupplyChianConstants.OUTPUT_ALLOT_TRANSDESTINATION_OTHER_FARM);
                allotOutOrSaleOutputDetailModel.setOutputToFarmId(allotInOrPurchaseFarmId);
                allotOutOrSaleOutputDetailModel.setUserId(allotOutOrSaleEmployeeId);
                allotOutOrSaleOutputDetailModel.setBillCode(allotOutOrSaleBillCode);
                allotOutOrSaleOutputDetailModel.setBillDate(allotOutOrSaleBillDate);
                allotOutOrSaleOutputDetailModel.setEventCode(allotOutOrSaleEventCode);
                allotOutOrSaleOutputDetailModel.setCreateId(allotOutOrSaleEmployeeId);
                allotOutOrSaleOutputDetailModel.setCreateDate(currentDate);
                insertOutputDetailModelList.add(allotOutOrSaleOutputDetailModel);

                // 调拨入库
                Map<String, Object> materialBatchMap = getMaterialBatchNumber(materialOnly, materialBatch, allotInOrPurchaseWarehouseId);

                if (Maps.isEmpty(materialBatchMap, "rowId")) {
                    StoreMaterialModel storeMaterialModel = new StoreMaterialModel();
                    storeMaterialModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    storeMaterialModel.setFarmId(allotInOrPurchaseFarmId);
                    storeMaterialModel.setCompanyId(allotInOrPurchaseCompanyId);
                    storeMaterialModel.setMaterialId(allotInOrPurchaseMaterialId);
                    storeMaterialModel.setMaterialOnly(materialOnly);
                    storeMaterialModel.setMaterialBatch(materialBatch);
                    storeMaterialModel.setProductionDate(updateStoreMaterialModel.getProductionDate());
                    storeMaterialModel.setEffectiveDate(updateStoreMaterialModel.getEffectiveDate());
                    storeMaterialModel.setWarehouseId(allotInOrPurchaseWarehouseId);
                    storeMaterialModel.setActualQty(0D);
                    storeMaterialModel.setCreateDate(currentDate);
                    storeMaterialMapper.insert(storeMaterialModel);
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Maps.getDate(inputMap, "billDate"));
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date startDate = calendar.getTime();
                String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);

                SqlCon monthAccountExistsSqlCon = new SqlCon();
                monthAccountExistsSqlCon.addMainSql("SELECT ROW_ID AS rowId FROM SC_M_MONTH_ACCOUNT");
                monthAccountExistsSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
                monthAccountExistsSqlCon.addCondition(allotInOrPurchaseMaterialId, " AND MATERIAL_ID = ?");
                monthAccountExistsSqlCon.addCondition(materialOnly, " AND MATERIAL_ONLY = ?");
                monthAccountExistsSqlCon.addCondition(materialBatch, " AND MATERIAL_BATCH = ?");
                monthAccountExistsSqlCon.addCondition(startDateString, " AND START_DATE = ?");
                monthAccountExistsSqlCon.addCondition(allotInOrPurchaseWarehouseId, " AND WAREHOUSE_ID = ?");
                monthAccountExistsSqlCon.addConditionWithNull(allotInOrPurchaseFarmId, " AND FARM_ID = ? LIMIT 1");

                List<MonthAccountModel> monthAccountExistsList = setSql(monthAccountMapper, monthAccountExistsSqlCon);

                if (monthAccountExistsList.isEmpty()) {
                    MonthAccountModel monthAccountModel = new MonthAccountModel();
                    monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                    monthAccountModel.setFarmId(allotInOrPurchaseFarmId);
                    monthAccountModel.setCompanyId(allotInOrPurchaseCompanyId);
                    monthAccountModel.setMaterialId(allotInOrPurchaseMaterialId);
                    monthAccountModel.setMaterialOnly(materialOnly);
                    monthAccountModel.setMaterialBatch(materialBatch);
                    monthAccountModel.setWarehouseId(allotInOrPurchaseWarehouseId);
                    monthAccountModel.setStartQty(0D);
                    monthAccountModel.setStartDate(startDate);
                    monthAccountModel.setLockFlag("N");
                    monthAccountModel.setEventCode(allotInOrPurchaseEventCode);
                    monthAccountModel.setCreateId(allotInOrPurchaseEmployeeId);
                    monthAccountModel.setCreateDate(currentDate);
                    monthAccountMapper.insert(monthAccountModel);
                }

                InputDetailModel allotInputDetailModel = new InputDetailModel();
                allotInputDetailModel.setStatus(CommonConstants.STATUS);
                allotInputDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                allotInputDetailModel.setNotes(Maps.getString(detailMap, "notes"));
                allotInputDetailModel.setFarmId(allotInOrPurchaseFarmId);
                allotInputDetailModel.setCompanyId(allotInOrPurchaseCompanyId);
                allotInputDetailModel.setInputId(allotInOrPurchaseInputId);
                allotInputDetailModel.setInputLn(Maps.getLong(detailMap, "lineNumber"));
                allotInputDetailModel.setMaterialId(allotInOrPurchaseMaterialId);
                allotInputDetailModel.setMaterialOnly(materialOnly);
                allotInputDetailModel.setMaterialBatch(materialBatch);
                allotInputDetailModel.setInputQty(qty);
                allotInputDetailModel.setInputWarehouseId(allotInOrPurchaseWarehouseId);
                allotInputDetailModel.setInputerId(allotInOrPurchaseEmployeeId);
                allotInputDetailModel.setBillCode(allotInOrPurchaseBillCode);
                allotInputDetailModel.setBillDate(allotInOrPurchaseBillDate);
                allotInputDetailModel.setEventCode(allotInOrPurchaseEventCode);
                allotInputDetailModel.setCreateId(allotInOrPurchaseEmployeeId);
                allotInputDetailModel.setCreateDate(currentDate);
                insertAllotInputDetailModel.add(allotInputDetailModel);
            }

            // 出库领用
            OutputDetailModel outputUseDetailModel = new OutputDetailModel();
            outputUseDetailModel.setStatus(SupplyChianConstants.OUTPUT_USE_STATUS_USE);
            outputUseDetailModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            outputUseDetailModel.setNotes(Maps.getString(detailMap, "notes"));
            outputUseDetailModel.setFarmId(outputUseModel.getFarmId());
            outputUseDetailModel.setCompanyId(outputUseModel.getCompanyId());
            outputUseDetailModel.setOutputId(outputUseModel.getRowId());
            outputUseDetailModel.setOutputLn(Maps.getLong(detailMap, "lineNumber"));
            outputUseDetailModel.setMaterialId(allotInOrPurchaseMaterialId);
            outputUseDetailModel.setMaterialOnly(materialOnly);
            outputUseDetailModel.setMaterialBatch(materialBatch);
            outputUseDetailModel.setPlanQty(qty);
            outputUseDetailModel.setOutputQty(qty);
            outputUseDetailModel.setOutputWarehouseId(allotInOrPurchaseWarehouseId);
            outputUseDetailModel.setOutputDestination(SupplyChianConstants.OUTPUT_DESTINATION_FARM);
            outputUseDetailModel.setOutputFarmId(useFarmId);
            outputUseDetailModel.setPigType("3");
            outputUseDetailModel.setOutputerId(outputUseModel.getOutputerId());
            outputUseDetailModel.setOutputUseDate(currentDate);
            outputUseDetailModel.setUserId(outputUseModel.getUserId());
            outputUseDetailModel.setBillCode(outputUseModel.getBillCode());
            outputUseDetailModel.setBillDate(outputUseModel.getBillDate());
            outputUseDetailModel.setEventCode(outputUseModel.getEventCode());
            outputUseDetailModel.setCreateId(outputUseModel.getCreateId());
            outputUseDetailModel.setCreateDate(currentDate);
            insertOutputDetailModelList.add(outputUseDetailModel);
        }

        storeMaterialMapper.updates(updateStoreMaterialModelList);
        outputDetailMapper.inserts(insertOutputDetailModelList);
        if (!insertAllotInputDetailModel.isEmpty()) {
            inputDetailMapper.inserts(insertAllotInputDetailModel);
        }

    }

    // 获取调拨入库场的实验室仓库
    private long getLaboratoryWarehouseId(long farmId) throws Exception {
        List<Map<String, Object>> list = this.getWarehouseIdByWarehouseCategory(farmId, SupplyChianConstants.WAREHOUSE_CATEGORY_LABORATORY);
        if (list == null || list.isEmpty()) {
            String companyName = CacheUtil.getName(String.valueOf(farmId), NameEnum.COMPANY_NAME);
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "猪场【" + companyName + "】中【不存在实验室仓库】或【没有启用的实验室仓库】！");
        }
        return Maps.getLong(list.get(0), "warehouseId");
    }
    // END 实验室领用

    // 更新价格详情表
    @Override
    public List<Map<String, Object>> searchUpdatePriceDetailToList(String supplierId, String startDateEndDate) throws Exception {
        String startDate = null;
        String endDate = null;
        String startDateEndDateArray[] = startDateEndDate.split("~");
        if (startDateEndDateArray != null && startDateEndDateArray.length > 0) {
            startDate = startDateEndDateArray[0];
            endDate = startDateEndDateArray[1];
        }
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(
                " SELECT t0.MATERIAL_NAME materialName,t0.SPEC_ALL specAll,t0.UNIT unit,t1.PRICE price,t1.NOTES notes FROM cd_m_material t0 ");
        sqlCon.addMainSql(" INNER JOIN sc_m_bill_price_list_detail t1 ON t0.ROW_ID=t1.MATERIAL_ID AND t0.DELETED_FLAG=0 AND t0.STATUS=1 ");
        sqlCon.addMainSql(" INNER JOIN sc_m_bill_price_list t3 ON t3.ROW_ID=t1.PRICE_LIST_ID AND t3.DELETED_FLAG=0 AND t3.STATUS=1 ");
        sqlCon.addMainSql(" WHERE t0.DELETED_FLAG=0 AND t0.STATUS=1 ");
        sqlCon.addCondition(getFarmId(), " AND t0.FARM_ID=? ");
        sqlCon.addCondition(supplierId, " AND t0.SUPPLIER_ID=? ");
        sqlCon.addCondition(startDate, " AND t1.START_DATE=? ");
        sqlCon.addCondition(endDate, " AND t1.END_DATE=? ");
        Map<String, String> map = new HashMap<String, String>();
        map.put("sql", sqlCon.getCondition());
        return paramMapper.getObjectInfos(map);

    }

    // 是否是新农旗下公司
    private boolean isXNCompany(String companyMark) {
        if (companyMark == null) {
            return false;
        }

        // 新农旗下判定
        if (COMPANY_MARK_XNFEED.equals(companyMark) || companyMark.startsWith(COMPANY_MARK_IN_XNFEED)) {
            return true;
        }
        return false;
    }

    /* START 导入期初到SAP中 */
    @Override
    public void editMaterialInfoToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" AND FARM_ID = 3");
        sqlCon.addMainSql(" AND MATERIAL_CLASS_NUMBER IS NOT NULL");
        List<MaterialModel> materialModelList = getList(materialMapper, sqlCon);

        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
        for (MaterialModel materialModel : materialModelList) {
            Date currentDate = new Date();

            String mtcCardCode = HanaUtil.getMTC_CardCode(materialModel.getSupplierId());

            HanaMaterial hanaMaterial = new HanaMaterial();
            // 物料编号
            hanaMaterial.setMTC_ItemCode(materialModel.getMaterialClassNumber());
            // 物料描述
            hanaMaterial.setMTC_ItemName(materialModel.getMaterialName());
            // 物料规格
            hanaMaterial.setMTC_Spec(materialModel.getSpecAll());
            // 物料组(科目)
            hanaMaterial.setMTC_ItmsGrpCod(materialModel.getFinanceSubject());
            // 计量单位
            hanaMaterial.setMTC_Unit(materialModel.getUnit());
            // 供应商
            hanaMaterial.setMTC_CardCode(mtcCardCode);
            // 制造商
            hanaMaterial.setMTC_ValidComm(materialModel.getManufacturer());
            // 管理物料由
            // 物料类为：Y - 批次
            // 费用类：N - 无
            String mtcManBtchNum = null;
            if (hanaMaterial.getMTC_ItmsGrpCod() != null) {
                switch (hanaMaterial.getMTC_ItmsGrpCod()) {
                // 原材料-饲料
                case "101":
                    mtcManBtchNum = "Y";
                    break;
                // 原材料-药物-针剂
                case "102":
                    mtcManBtchNum = "Y";
                    break;
                // 原材料-药物-疫苗
                case "103":
                    mtcManBtchNum = "Y";
                    break;
                // 原材料-药物-兽用器械
                case "104":
                    mtcManBtchNum = "Y";
                    break;
                // 原材料-药物-粉剂
                case "105":
                    mtcManBtchNum = "Y";
                    break;
                // 原材料-药物-消毒剂
                case "106":
                    mtcManBtchNum = "Y";
                    break;
                default:
                    mtcManBtchNum = "N";
                    break;
                }
            } else {
                mtcManBtchNum = "N";
            }
            hanaMaterial.setMTC_ManBtchNum(mtcManBtchNum);

            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            // mtcITFC.setMTC_Branch(mtcBranchID);
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            // 业务代码: 物料
            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1060);
            // 新农+单据编号
            mtcITFC.setMTC_DocNum(materialModel.getMaterialClassNumber());
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // DB
            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterial);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

            mtcITFCList.add(mtcITFC);
        }

        hanaCommonService.insertsMTC_ITFC(mtcITFCList);
    }

    @Override
    public void editMaterialSupplierToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql(" AND EXISTS (");
        sqlCon.addMainSql(" SELECT 1 FROM  hr_r_srm T2");
        sqlCon.addMainSql(" WHERE T2.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND HR_M_COMPANY.ROW_ID = T2.CUSSUP_ID AND T2.FARM_ID = 3)");

        List<CompanyModel> companyModelList = getList(companyMapper, sqlCon);

        Date currentDate = new Date();

        List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

        for (CompanyModel company : companyModelList) {

            HanaClientOrProvider hanaClientOrProvider = new HanaClientOrProvider();
            // 客户/供应商编号
            String mtcCardCode = HanaUtil.getMTC_CardCode(company.getRowId());
            // 业务伙伴类别
            hanaClientOrProvider.setMTC_CardType(company.getCussupType());

            hanaClientOrProvider.setMTC_CardCode(mtcCardCode);
            // 客户/供应商名称
            hanaClientOrProvider.setMTC_CardName(company.getCompanyName());
            // 客户/供应商分类
            // 客户
            // hanaClientOrProvider.setMTC_GroupCode("100");
            // 供应商分类
            hanaClientOrProvider.setMTC_GroupCode("101");

            // 客户/供应商简称
            hanaClientOrProvider.setMTC_CardFName(company.getSortName());
            // 客户/供应商地址
            hanaClientOrProvider.setMTC_AliasName(company.getCompanyAddress());

            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            // mtcITFC.setMTC_Branch(mtcBranchID);
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
            // 客户
            // mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1070);
            // 供应商
            mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1080);
            // 新农+单据编号
            mtcITFC.setMTC_DocNum(mtcCardCode);
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // DB
            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaClientOrProvider);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
            mtcITFCList.add(mtcITFC);

        }
        hanaCommonService.insertsMTC_ITFC(mtcITFCList);
    }

    @Override
    public void editMaterialToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        // 参数END_DATE和COMPANY_CODE
        String companyCode = Maps.getString(inputMap, "COMPANY_CODE");
        if (companyCode == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【COMPANY_CODE】");
        }
        Date endDate = Maps.getDate(inputMap, "END_DATE");
        if (endDate == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【END_DATE】，格式为yyyy-MM-dd");
        }
        Calendar calendar = Calendar.getInstance();

        Date currentDate = calendar.getTime();

        calendar.setTime(endDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();
        String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date nextStartDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date rightEndDate = calendar.getTime();
        if (!rightEndDate.equals(endDate)) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "参数【END_DATE】不是月末");
        }

        SqlCon companySqlCon = new SqlCon();
        companySqlCon.addConditionWithNull(companyCode, " AND COMPANY_CODE = ?");

        List<CompanyModel> companyModelList = getList(companyMapper, companySqlCon);

        if (companyModelList.isEmpty()) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "错误的【COMPANY_CODE】");
        }

        long farmId = companyModelList.get(0).getRowId();
        boolean isToHana = HanaUtil.isToHanaCheck(farmId);
        if (!isToHana) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "指定猪场没有开启【SAP开关】");
        }

        // 查找是否没有盘点的仓库
        SqlCon checkSqlCon = new SqlCon();
        checkSqlCon.addMainSql("SELECT T1.WAREHOUSE_ID AS warehouseId FROM SC_M_MONTH_ACCOUNT T1");
        checkSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        checkSqlCon.addCondition(startDateString, " AND T1.START_DATE = ?");
        checkSqlCon.addMainSql(" AND T1.END_QTY IS NULL");
        checkSqlCon.addCondition(farmId, " AND T1.FARM_ID = ?");
        checkSqlCon.addMainSql(" AND EXISTS( SELECT 1 FROM SC_M_STORE_MATERIAL T2");
        checkSqlCon.addMainSql(" WHERE T2.DELETED_FLAG = '0' AND T2.ACTUAL_QTY <> 0");
        checkSqlCon.addMainSql(" AND T1.FARM_ID = T2.FARM_ID");
        checkSqlCon.addMainSql(" AND T1.WAREHOUSE_ID = T2.WAREHOUSE_ID");
        checkSqlCon.addMainSql(" AND T1.MATERIAL_ONLY = T2.MATERIAL_ONLY");
        checkSqlCon.addMainSql(" AND T1.MATERIAL_BATCH = T2.MATERIAL_BATCH LIMIT 1)");
        checkSqlCon.addMainSql(" GROUP BY T1.WAREHOUSE_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", checkSqlCon.getCondition());

        List<Map<String, Object>> checkList = paramMapper.getObjectInfos(sqlMap);

        if (!checkList.isEmpty()) {
            StringBuffer stringBuffer = new StringBuffer();
            for (Map<String, Object> checkMap : checkList) {
                stringBuffer.append(CacheUtil.getName(Maps.getString(checkMap, "warehouseId"), xn.pigfarm.util.enums.NameEnum.WAREHOUSE_NAME));
                stringBuffer.append(",");
            }
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【" + stringBuffer.toString() + "】仓库没有盘点！。。。");
        }

        // 领料退货没价格
        SqlCon warehouseSqlCon = new SqlCon();
        warehouseSqlCon.addMainSql("SELECT T1.MATERIAL_ID, T1.MATERIAL_BATCH");
        warehouseSqlCon.addMainSql(" ,T1.WAREHOUSE_ID, T1.END_QTY");
        warehouseSqlCon.addMainSql(" FROM SC_M_MONTH_ACCOUNT T1");
        warehouseSqlCon.addMainSql(" INNER JOIN CD_M_MATERIAL T2");
        warehouseSqlCon.addMainSql(" ON(T2.DELETED_FLAG = '0' AND T1.MATERIAL_ID = T2.ROW_ID)");
        warehouseSqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        warehouseSqlCon.addMainSql(" AND T1.END_QTY <> 0");
        warehouseSqlCon.addCondition(farmId, " AND T1.FARM_ID = ?");
        warehouseSqlCon.addCondition(startDateString, " AND T1.START_DATE = ?");
        // 去除 制造费用（费用化物料）
        warehouseSqlCon.addMainSql(" AND T2.MATERIAL_FIRST_CLASS IN ");
        warehouseSqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_20, " (?,");
        warehouseSqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_21, " ?,");
        warehouseSqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_22, " ?,");
        warehouseSqlCon.addCondition(SupplyChianConstants.MATERIAL_FIRST_CLASS_30, " ?)");

        List<MonthAccountModel> monthAccountModelList = setSql(monthAccountMapper, warehouseSqlCon);

        if (!monthAccountModelList.isEmpty()) {
            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            HanaMaterialOperateBill hanaMaterialOperateBill = new HanaMaterialOperateBill();

            // 分公司编码
            hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
            // 入库日期
            hanaMaterialOperateBill.setMTC_DocDate(endDate);
            // 操作人
            hanaMaterialOperateBill.setMTC_EmpName("期初导入");
            // 表行
            List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

            hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);

            for (int i = 0; i < monthAccountModelList.size(); i++) {
                MonthAccountModel monthAccountModel = monthAccountModelList.get(i);

                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(monthAccountModel.getMaterialId());
                // if (StringUtil.isNonBlank(mtcItemCode)) {
                HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                // 分公司编码
                hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                // 猪场编码
                hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                // 猪舍编号
                // hanaMaterialOperateBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(updateOutputDetailModel.getOutputHouseId()));
                // 新农+单据编号
                // hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(inputModel.getRowId()));
                // 新农+单据行号
                hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(i));
                // 物料编号
                hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                // 批次编号
                hanaMaterialOperateBillDetail.setMTC_BatchNum(monthAccountModel.getMaterialBatch());
                // 仓库编号
                hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(monthAccountModel.getWarehouseId()));
                // 退货入库数量
                hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(monthAccountModel.getEndQty()));
                // 商品猪
                hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);

                hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                // }
            }

            if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码：生产领用退料
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2050);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "没有相关数据！。。。");
        }
    }

    @Override
    public void editMaterialPurchaseBillToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");

//        Date startDate = Maps.getDate(inputMap, "START_DATE");
//        if (startDate == null) {
//            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【START_DATE】，格式为yyyy-MM-dd");
//        }

//        String startDateString = TimeUtil.format(startDate, TimeUtil.DATE_FORMAT);

        Date currentDate = new Date();
        Long[] purchaseId = new Long[] { 3421L };
        for (Long long1 : purchaseId) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT ROW_ID,NOTES,FARM_ID,COMPANY_ID,FARM_ID,COMPANY_ID,PURCHASE_ID,PURCHASE_LN,FREE_LN,INPUT_ID,INPUT_LN");
            sqlCon.addMainSql(",MATERIAL_ID,MATERIAL_ONLY,ACTUAL_PRICE,ACTUAL_FREE_PERCENT,PASS_QTY,SUM(PURCHASE_QTY) AS PURCHASE_QTY");
            sqlCon.addMainSql(",FREE_RELATED_ID,IS_PACKAGE,ARRIVE_QTY,GROUP_OR_SELF,SUPPLIER_ID,REQUIRE_FARM,PURCHASER_ID,ARRIVE_DATE");
            sqlCon.addMainSql(",BILL_CODE,BILL_DATE,EVENT_CODE,CREATE_ID,CREATE_DATE,REBATE_REASON,REBATE_PRICE");
            sqlCon.addMainSql(" FROM SC_M_BILL_PURCHASE_DETAIL WHERE DELETED_FLAG = '0'");
            sqlCon.addCondition(long1, " AND PURCHASE_ID = ?");
            sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_SCRAP, " AND STATUS <> ?");
            sqlCon.addMainSql(" GROUP BY PURCHASE_ID, PURCHASE_LN, FREE_LN");
            sqlCon.addMainSql(" ORDER BY PURCHASE_ID, PURCHASE_LN, FREE_LN");
            List<PurchaseDetailModel> purchaseDetailModelList = setSql(purchaseDetailMapper, sqlCon);

            // START HANA
            // 第一个String是dbBeanName，第二个String是baseEntry
            Map<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMap = new HashMap<String, Map<String, HanaPurchaseBill>>();
            // END HANA

            for (PurchaseDetailModel purchaseDetailModel : purchaseDetailModelList) {
                // START HANA
                boolean isToHana = HanaUtil.isToHanaCheck(purchaseDetailModel.getRequireFarm());

                if (isToHana) {
                    Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(purchaseDetailModel.getRequireFarm());

                    String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                    String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                    String mtcCardCode = HanaUtil.getMTC_CardCode(purchaseDetailModel.getSupplierId());

                    String dbBeanName = HanaUtil.getDbBeanName(purchaseDetailModel.getRequireFarm());

                    // 新增单据
                    String baseEntry = mtcBranchID + "-" + String.valueOf(purchaseDetailModel.getPurchaseId()) + "-" + purchaseDetailModel
                            .getBillCode();

                    List<HanaPurchaseBillDetail> hanaPurchaseBillDetailList = null;
                    // 如果dbBeanName不存在
                    if (!dbBeanNameHanaPurchaseBillMap.containsKey(dbBeanName)) {
                        Map<String, HanaPurchaseBill> hanaPurchaseBillMap = new HashMap<String, HanaPurchaseBill>();

                        HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                        // 分公司编码
                        hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                        // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                        // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                        hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                        // 业务伙伴编号
                        hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                        // 采购日期
                        hanaPurchaseBill.setMTC_DocDate(purchaseDetailModel.getBillDate());
                        // 到货日期
                        hanaPurchaseBill.setMTC_DocDueDate(purchaseDetailModel.getArriveDate());
                        // 表行
                        hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                        hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);

                        hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                        dbBeanNameHanaPurchaseBillMap.put(dbBeanName, hanaPurchaseBillMap);
                    } else {
                        // 如果dbBeanName存在,获取hanaPurchaseBillMap
                        Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMap.get(dbBeanName);

                        // 如果baseEntry不存在
                        if (!hanaPurchaseBillMap.containsKey(baseEntry)) {
                            HanaPurchaseBill hanaPurchaseBill = new HanaPurchaseBill();
                            // 分公司编码
                            hanaPurchaseBill.setMTC_BranchID(mtcBranchID);
                            // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_BaseEntry(baseEntry);
                            // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                            hanaPurchaseBill.setMTC_NumAtCard(baseEntry);
                            // 业务伙伴编号
                            hanaPurchaseBill.setMTC_CardCode(mtcCardCode);
                            // 采购日期
                            hanaPurchaseBill.setMTC_DocDate(purchaseDetailModel.getBillDate());
                            // 到货日期
                            hanaPurchaseBill.setMTC_DocDueDate(purchaseDetailModel.getArriveDate());
                            // 表行
                            hanaPurchaseBillDetailList = new ArrayList<HanaPurchaseBillDetail>();
                            hanaPurchaseBill.setDetailList(hanaPurchaseBillDetailList);
                            hanaPurchaseBillMap.put(baseEntry, hanaPurchaseBill);
                        } else {
                            hanaPurchaseBillDetailList = hanaPurchaseBillMap.get(baseEntry).getDetailList();
                        }
                    }

                    String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(purchaseDetailModel.getMaterialId());

                    HanaPurchaseBillDetail hanaPurchaseBillDetail = new HanaPurchaseBillDetail();
                    // 分公司编码
                    hanaPurchaseBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaPurchaseBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号
                    hanaPurchaseBillDetail.setMTC_BaseEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                    // 新农+单据行号
                    hanaPurchaseBillDetail.setMTC_BaseLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" + String.valueOf(
                            purchaseDetailModel.getFreeLn()));
                    // 采购类型
                    hanaPurchaseBillDetail.setMTC_PurType(purchaseDetailModel.getRebateReason());
                    // 返利金额
                    hanaPurchaseBillDetail.setMTC_Rebate(String.valueOf(purchaseDetailModel.getRebatePrice()));
                    // 业务类型
                    hanaPurchaseBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                    // 物料编号
                    hanaPurchaseBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 采购数量
                    hanaPurchaseBillDetail.setMTC_Qty(String.valueOf(purchaseDetailModel.getPurchaseQty()));
                    // 采购价格
                    hanaPurchaseBillDetail.setMTC_QtyPrice(String.valueOf(purchaseDetailModel.getActualPrice()));
                    // 采购金额
                    Double totalPrice = BigDecimalUtil.bigDecimalMultiply(purchaseDetailModel.getPurchaseQty(), purchaseDetailModel.getActualPrice());
                    hanaPurchaseBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                    hanaPurchaseBillDetailList.add(hanaPurchaseBillDetail);
                }
                // END HANA
            }

            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();
            for (Entry<String, Map<String, HanaPurchaseBill>> dbBeanNameHanaPurchaseBillMapEntry : dbBeanNameHanaPurchaseBillMap.entrySet()) {
                String dbBeanName = dbBeanNameHanaPurchaseBillMapEntry.getKey();
                Map<String, HanaPurchaseBill> hanaPurchaseBillMap = dbBeanNameHanaPurchaseBillMapEntry.getValue();
                for (Entry<String, HanaPurchaseBill> hanaPurchaseBillMapEntry : hanaPurchaseBillMap.entrySet()) {
                    HanaPurchaseBill hanaPurchaseBill = hanaPurchaseBillMapEntry.getValue();
                    if (!hanaPurchaseBill.getDetailList().isEmpty()) {
                        MTC_ITFC mtcITFC = new MTC_ITFC();
                        // 分公司编号
                        mtcITFC.setMTC_Branch(hanaPurchaseBill.getMTC_BranchID());
                        // 业务日期
                        mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaPurchaseBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                        // 业务代码:采购订单
                        mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2010);
                        // 新农+单据编号
                        mtcITFC.setMTC_DocNum(hanaPurchaseBill.getMTC_BaseEntry());
                        // 优先级
                        mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                        // 处理区分
                        mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                        // 创建日期
                        mtcITFC.setMTC_CreateTime(currentDate);
                        // 同步状态
                        mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                        // DB
                        mtcITFC.setDB_Bean_Name(dbBeanName);
                        // JSON文件
                        String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPurchaseBill);
                        mtcITFC.setMTC_DataFile(jsonDataFile);
                        // JSON文件长度
                        mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                        mtcITFCList.add(mtcITFC);
                    }
                }
            }

            if (!mtcITFCList.isEmpty()) {
                hanaCommonService.insertsMTC_ITFC(mtcITFCList);
            }
        }
    }

    @Override
    public void editMaterialInputBillToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        // 参数BILL_CODE和COMPANY_CODE
        // String companyCode = Maps.getString(inputMap, "COMPANY_CODE");
        // if (companyCode == null) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【COMPANY_CODE】");
        // }
        // String billCode = Maps.getString(inputMap, "BILL_CODE");
        // if (billCode == null) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【BILL_CODE】");
        // }
        //
        // SqlCon companySqlCon = new SqlCon();
        // companySqlCon.addConditionWithNull(companyCode, " AND COMPANY_CODE = ?");
        //
        // List<CompanyModel> companyModelList = getList(companyMapper, companySqlCon);
        //
        // if (companyModelList.isEmpty()) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "错误的【COMPANY_CODE】");
        // }
        //
        // long farmId = companyModelList.get(0).getRowId();
        //
        // boolean isToHana = HanaUtil.isToHanaCheck(farmId);
        // if (!isToHana) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "指定猪场没有开启【SAP开关】");
        // }

        // Long[] inputBillIds = new Long[] { 5019L };

        Long[] inputBillIds = new Long[] { 5450L };

        for (Long inputBillId : inputBillIds) {
        // 采购入库
        // if (billCode.startsWith("IS")) {
            SqlCon inputBillSqlCon = new SqlCon();
            inputBillSqlCon.addCondition(CommonConstants.STATUS, " AND STATUS = ?");
            inputBillSqlCon.addConditionWithNull(inputBillId, " AND ROW_ID = ?");
            // inputBillSqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");
            // inputBillSqlCon.addCondition(billCode, " AND BILL_CODE = ?");

            List<InputModel> inputModelList = getList(inputMapper, inputBillSqlCon);

            if (inputModelList.size() == 1) {
                Date currentDate = new Date();

                InputModel inputModel = inputModelList.get(0);

                if (!(SupplyChianConstants.EVENT_CODE_MATERIAL_INPUT_ARRIVE.equals(inputModel.getEventCode())
                        || SupplyChianConstants.EVENT_CODE_FEED_INPUT_ARRIVE.equals(inputModel.getEventCode()))) {
                    Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "这个单据不是【到货入库】！。。。");
                }

                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(inputModel.getFarmId());
                String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

                HanaInputBill hanaInputBill = new HanaInputBill();
                // 分公司编码
                hanaInputBill.setMTC_BranchID(mtcBranchID);
                // 新农+单据编号
                hanaInputBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
                // 参考号(合同号):分公司编码-单据ROW_ID-单据单号
                hanaInputBill.setMTC_NumAtCard(mtcBranchID + "-" + String.valueOf(inputModel.getRowId()) + "-" + inputModel.getBillCode());
                // 入库日期
                hanaInputBill.setMTC_DocDate(inputModel.getBillDate());
                // 入库日期
                hanaInputBill.setMTC_DocDueDate(inputModel.getBillDate());
                // 表行
                List<HanaInputBillDetail> hanaInputBillDetailList = new ArrayList<HanaInputBillDetail>();

                hanaInputBill.setDetailList(hanaInputBillDetailList);
                SqlCon inputDetailSqlCon = new SqlCon();
                inputDetailSqlCon.addCondition(inputModel.getRowId(), " AND INPUT_ID = ?");

                List<InputDetailModel> inputDetailModelList = getList(inputDetailMapper, inputDetailSqlCon);

                Long supplierId = null;

                for (InputDetailModel inputDetailModel : inputDetailModelList) {
                    SqlCon purchaseDetailSqlCon = new SqlCon();
                    purchaseDetailSqlCon.addCondition(inputDetailModel.getInputId(), " AND INPUT_ID = ?");
                    purchaseDetailSqlCon.addCondition(inputDetailModel.getInputLn(), " AND INPUT_LN = ?");
                    List<PurchaseDetailModel> purchaseDetailModelList = getList(purchaseDetailMapper, purchaseDetailSqlCon);

                    if (purchaseDetailModelList.isEmpty()) {
                        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【purchaseDetail】异常！。。。");
                    }

                    PurchaseDetailModel purchaseDetailModel = purchaseDetailModelList.get(0);

                    if (supplierId == null) {
                        supplierId = purchaseDetailModel.getSupplierId();
                    }

                    SqlCon storeMaterialSqlCon = new SqlCon();
                    storeMaterialSqlCon.addCondition(inputDetailModel.getMaterialOnly(), " AND MATERIAL_ONLY = ?");
                    storeMaterialSqlCon.addCondition(inputDetailModel.getMaterialBatch(), " AND MATERIAL_BATCH = ?");
                    List<StoreMaterialModel> storeMaterialModelList = getList(storeMaterialMapper, storeMaterialSqlCon);

                    if (storeMaterialModelList.isEmpty()) {
                        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【storeMaterial】异常！。。。");
                    }

                    StoreMaterialModel storeMaterialModel = storeMaterialModelList.get(0);

                    String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(inputDetailModel.getMaterialId());

                    HanaInputBillDetail hanaInputBillDetail = new HanaInputBillDetail();
                    // 分公司编码
                    hanaInputBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaInputBillDetail.setMTC_DeptID(mtcDeptID);
                    // 新农+单据编号（入库单编号）
                    hanaInputBillDetail.setMTC_BaseEntry(String.valueOf(inputDetailModel.getInputId()));
                    // 新农+单据行号（入库单行号）
                    hanaInputBillDetail.setMTC_BaseLine(String.valueOf(inputDetailModel.getInputLn()));
                    // 新农+采购单编号:采购单RowID
                    hanaInputBillDetail.setMTC_OrignEntry(String.valueOf(purchaseDetailModel.getPurchaseId()));
                    // 新农+采购单行号:采购单购料行号*赠料行号
                    hanaInputBillDetail.setMTC_OrignLine(String.valueOf(purchaseDetailModel.getPurchaseLn()) + "*" + String.valueOf(
                            purchaseDetailModel.getFreeLn()));
                    // 采购类型
                    hanaInputBillDetail.setMTC_PurType(purchaseDetailModel.getRebateReason());
                    // 返利金额
                    hanaInputBillDetail.setMTC_Rebate("0");
                    // 业务类型
                    hanaInputBillDetail.setMTC_SalesType(HanaConstants.MTC_ITFC_SALES_TYPE_PO);
                    // 物料编号
                    hanaInputBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 仓库编号
                    hanaInputBillDetail.setMTC_WhsCode(String.valueOf(inputDetailModel.getInputWarehouseId()));
                    // 入库数量
                    hanaInputBillDetail.setMTC_Qty(String.valueOf(inputDetailModel.getInputQty()));
                    // 入库单价
                    hanaInputBillDetail.setMTC_QtyPrice(String.valueOf(purchaseDetailModel.getActualPrice()));
                    // 入库金额
                    Double totalPrice = bigDecimalMultiply(inputDetailModel.getInputQty(), purchaseDetailModel.getActualPrice());
                    hanaInputBillDetail.setMTC_Amount(String.valueOf(totalPrice));
                    // 批次编号
                    hanaInputBillDetail.setMTC_BatchNum(inputDetailModel.getMaterialBatch());
                    // 生产日期
                    hanaInputBillDetail.setMTC_ProDate(storeMaterialModel.getProductionDate());
                    // 有效日期
                    hanaInputBillDetail.setMTC_ValidDate(storeMaterialModel.getEffectiveDate());

                    hanaInputBillDetailList.add(hanaInputBillDetail);
                }

                String mtcCardCode = HanaUtil.getMTC_CardCode(supplierId);
                // 业务伙伴编号
                hanaInputBill.setMTC_CardCode(mtcCardCode);

                if (!hanaInputBill.getDetailList().isEmpty()) {
                    MTC_ITFC mtcITFC = new MTC_ITFC();
                    // 分公司编号
                    mtcITFC.setMTC_Branch(hanaInputBill.getMTC_BranchID());
                    // 业务日期
                    mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaInputBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                    // 业务代码：采购入库 - 物料
                    mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2020);
                    // 新农+单据编号
                    mtcITFC.setMTC_DocNum(hanaInputBill.getMTC_BaseEntry());
                    // 优先级
                    mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                    // 处理区分
                    mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                    // 创建日期
                    mtcITFC.setMTC_CreateTime(currentDate);
                    // 同步状态
                    mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                    // DB
                    mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(inputModel.getFarmId()));
                    // JSON文件
                    String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaInputBill);
                    mtcITFC.setMTC_DataFile(jsonDataFile);
                    // JSON文件长度
                    mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                    hanaCommonService.insertMTC_ITFC(mtcITFC);
                }

            } else {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "不存在数据或存在多条相关数据！。。。");
            }
        // } else if (billCode.startsWith("OS")) {
        //
        // } else {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "【BILL_CODE】不正确！。。。");
        // }
        }
    }

    @Override
    public void editMaterialOutputUseBillToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        // // 参数BILL_CODE和COMPANY_CODE
        // String companyCode = Maps.getString(inputMap, "COMPANY_CODE");
        // if (companyCode == null) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【COMPANY_CODE】");
        // }
        // String billCode = Maps.getString(inputMap, "BILL_CODE");
        // if (billCode == null) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【BILL_CODE】");
        // }
        //
        // SqlCon companySqlCon = new SqlCon();
        // companySqlCon.addConditionWithNull(companyCode, " AND COMPANY_CODE = ?");
        //
        // List<CompanyModel> companyModelList = getList(companyMapper, companySqlCon);
        //
        // if (companyModelList.isEmpty()) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "错误的【COMPANY_CODE】");
        // }
        //
        // long farmId = companyModelList.get(0).getRowId();
        //
        // boolean isToHana = HanaUtil.isToHanaCheck(farmId);
        // if (!isToHana) {
        // Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "指定猪场没有开启【SAP开关】");
        // }

        // Long[] ouputBillRowIds = new Long[] { 16985L };

        Long[] ouputBillRowIds = new Long[] { 17039L, 17046L, 17104L, 17109L, 17110L, 17190L, 17194L, 17195L, 17241L, 17243L, 17244L, 17245L, 17247L,
                17310L, 17314L, 17315L, 17392L, 17393L, 17395L, 17437L, 17438L, 17443L, 17538L, 17539L, 17540L, 17636L, 17637L, 17640L, 17737L,
                17739L, 17741L, 17760L, 17810L, 17812L, 17830L, 17887L, 17888L, 17899L, 17972L, 17974L, 18047L, 18049L, 18057L, 18120L, 18124L,
                18135L, 18203L, 18331L, 18332L, 18336L, 18433L, 18448L, 18449L, 18451L, 18452L, 18453L, 18544L, 18632L, 18637L, 18710L, 18711L,
                18781L, 18785L, 18864L, 18870L, 18874L, 18932L, 18936L, 18937L, 19009L, 19012L, 19013L, 19089L, 19090L, 19153L, 19154L, 19158L,
                19160L, 19233L, 19234L, 19357L, 19360L, 19361L, 19367L, 19475L, 19476L, 19477L, 19490L, 19620L, 19667L, 19668L, 19669L, 19718L,
                19720L, 19721L, 19723L, 19775L, 19776L, 19855L, 19856L, 19916L, 19919L, 19923L, 19925L, 20017L, 20019L, 20024L, 20025L, 20031L,
                20098L, 20099L, 20100L, 20127L, 20134L, 20247L, 20248L, 20261L, 20341L, 20344L, 20478L, 20484L, 20488L, 20522L, 20618L, 20621L,
                20622L, 20662L };

        for (Long rowId : ouputBillRowIds) {

            SqlCon ouputBillSqlCon = new SqlCon();

            // ouputBillSqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");
            // ouputBillSqlCon.addCondition(billCode, " AND BILL_CODE = ?");
            ouputBillSqlCon.addCondition(rowId, " AND ROW_ID = ?");
            ouputBillSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_USE, " AND ( STATUS = ?");
            ouputBillSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PART_USE, " OR STATUS = ? )");
            ouputBillSqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_BILL_OUTPUT_DETAIL WHERE DELETED_FLAG = '0'");
            ouputBillSqlCon.addMainSql(" AND SC_M_BILL_OUTPUT.ROW_ID = OUTPUT_ID");
            ouputBillSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_PREPARING, " AND ( STATUS = ?");
            ouputBillSqlCon.addCondition(SupplyChianConstants.OUTPUT_USE_STATUS_WAITING, " OR STATUS = ? )");
            ouputBillSqlCon.addMainSql(" LIMIT 1)");

            List<OutputModel> ouputModelList = getList(outputMapper, ouputBillSqlCon);

            Date currentDate = new Date();

            OutputModel outputModel = ouputModelList.get(0);

            if (!SupplyChianConstants.EVENT_CODE_OUTPUT_USE.equals(outputModel.getEventCode())) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "这个单据不是【出库领用】！。。。");
            }

            HanaMaterialOperateBill hanaMaterialOperateBill = new HanaMaterialOperateBill();

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(outputModel.getFarmId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            // 分公司编码
            hanaMaterialOperateBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaMaterialOperateBill.setMTC_BaseEntry(mtcBranchID + "-" + String.valueOf(outputModel.getRowId()) + "-" + outputModel.getBillCode());
            // 出库日期
            hanaMaterialOperateBill.setMTC_DocDate(outputModel.getBillDate());
            // 领用人
            hanaMaterialOperateBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(outputModel.getFarmId(), outputModel.getUserId()));
            // 表行
            List<HanaMaterialOperateBillDetail> hanaMaterialOperateBillDetailList = new ArrayList<HanaMaterialOperateBillDetail>();

            hanaMaterialOperateBill.setDetailList(hanaMaterialOperateBillDetailList);

            SqlCon outputDetailSqlCon = new SqlCon();
            outputDetailSqlCon.addMainSql("SELECT OUTPUT_ID, OUTPUT_LN ,OUTPUT_HOUSE_ID ,MATERIAL_ID ,MATERIAL_BATCH");
            outputDetailSqlCon.addMainSql(" ,OUTPUT_WAREHOUSE_ID ,SUM(OUTPUT_QTY) AS OUTPUT_QTY ,PIG_TYPE");
            outputDetailSqlCon.addMainSql(" FROM SC_M_BILL_OUTPUT_DETAIL");
            outputDetailSqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '3'");
            outputDetailSqlCon.addMainSql(" AND EVENT_CODE = 'OUTPUT_USE'");
            outputDetailSqlCon.addCondition(outputModel.getRowId(), " AND OUTPUT_ID = ?");
            outputDetailSqlCon.addMainSql(" GROUP BY OUTPUT_ID ,OUTPUT_LN ,MATERIAL_ONLY ,MATERIAL_BATCH");

            List<OutputDetailModel> outputDetailModelList = setSql(outputDetailMapper, outputDetailSqlCon);

            for (OutputDetailModel outputDetailModel : outputDetailModelList) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(outputDetailModel.getMaterialId());
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(outputDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialOperateBillDetail hanaMaterialOperateBillDetail = new HanaMaterialOperateBillDetail();
                    // 分公司编码
                    hanaMaterialOperateBillDetail.setMTC_BranchID(mtcBranchID);
                    // 猪场编码
                    hanaMaterialOperateBillDetail.setMTC_DeptID(mtcDeptID);
                    // 猪舍编号
                    hanaMaterialOperateBillDetail.setMTC_Unit(HanaUtil.getMTC_Unit(outputDetailModel.getOutputHouseId()));
                    // 新农+单据编号
                    hanaMaterialOperateBillDetail.setMTC_BaseEntry(String.valueOf(outputDetailModel.getOutputId()));
                    // 新农+单据行号
                    hanaMaterialOperateBillDetail.setMTC_BaseLine(String.valueOf(outputDetailModel.getOutputLn()));
                    // 物料编号
                    hanaMaterialOperateBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialOperateBillDetail.setMTC_BatchNum(outputDetailModel.getMaterialBatch());
                    // 仓库编号
                    hanaMaterialOperateBillDetail.setMTC_WhsCode(String.valueOf(outputDetailModel.getOutputWarehouseId()));
                    // 领用数量
                    hanaMaterialOperateBillDetail.setMTC_Qty(String.valueOf(outputDetailModel.getOutputQty()));
                    // 如果领用的为兽药，默认为A - 商品猪
                    // 核算对象：A - 商品猪 B - 生产猪C - 后备猪
                    if (SupplyChianConstants.MATERIAL_FIRST_CLASS_30.equals(Maps.getString(materialInfo, "materialFirstClass"))) {
                        // 商品猪
                        hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                    } else {
                        if ("1".equals(outputDetailModel.getPigType())) {
                            // 后备猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_C);
                        } else if ("2".equals(outputDetailModel.getPigType())) {
                            // 生产猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_B);
                        } else {
                            // 商品猪
                            hanaMaterialOperateBillDetail.setMTC_Collection(HanaConstants.MTC_COLLECTION_A);
                        }
                    }

                    hanaMaterialOperateBillDetailList.add(hanaMaterialOperateBillDetail);
                }
            }

            if (!hanaMaterialOperateBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(hanaMaterialOperateBill.getMTC_BranchID());
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialOperateBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码:生产领用
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2040);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialOperateBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(outputModel.getFarmId()));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialOperateBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }
        }

    }

    @Override
    public void editMaterialOutputAllotInputBillToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        // 参数BILL_CODE和COMPANY_CODE
        String companyCode = Maps.getString(inputMap, "COMPANY_CODE");
        if (companyCode == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【COMPANY_CODE】");
        }
        String billCode = Maps.getString(inputMap, "BILL_CODE");
        if (billCode == null) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "请输入参数【BILL_CODE】");
        }

        SqlCon companySqlCon = new SqlCon();
        companySqlCon.addConditionWithNull(companyCode, " AND COMPANY_CODE = ?");

        List<CompanyModel> companyModelList = getList(companyMapper, companySqlCon);

        if (companyModelList.isEmpty()) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "错误的【COMPANY_CODE】");
        }

        long farmId = companyModelList.get(0).getRowId();

        boolean isToHana = HanaUtil.isToHanaCheck(farmId);
        if (!isToHana) {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "指定猪场没有开启【SAP开关】");
        }

        SqlCon allotInputBillSqlCon = new SqlCon();

        allotInputBillSqlCon.addConditionWithNull(farmId, " AND FARM_ID = ?");
        allotInputBillSqlCon.addCondition(billCode, " AND BILL_CODE = ?");
        allotInputBillSqlCon.addMainSql(" LIMIT 1");

        List<InputModel> inputModelList = getList(inputMapper, allotInputBillSqlCon);

        if (inputModelList.size() == 1) {
            Date currentDate = new Date();

            InputModel inputModel = inputModelList.get(0);

            if (!SupplyChianConstants.EVENT_CODE_INPUT_ALLOT.equals(inputModel.getEventCode())) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "这个单据不是【调拨入库单】！。。。");
            }

            HanaMaterialOperateBill hanaMaterialOperateBill = new HanaMaterialOperateBill();

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(inputModel.getFarmId());
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);
            String mtcFromWhsCode = null;
            String mtcToWhsCode = null;

            HanaMaterialAllotBill hanaMaterialAllotBill = new HanaMaterialAllotBill();
            // 分公司编码
            hanaMaterialAllotBill.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号:分公司编码-单据ROW_ID-单据单号
            hanaMaterialAllotBill.setMTC_BaseEntry(mtcBranchID + "-" + inputModel.getRowId() + "-" + inputModel.getBillCode());
            // 调拨日期
            hanaMaterialAllotBill.setMTC_DocDate(inputModel.getBillDate());
            // 调拨人
            hanaMaterialAllotBill.setMTC_EmpName(HanaUtil.getMTC_EmpName(farmId, inputModel.getInputerId()));
            // 表行
            List<HanaMaterialAllotBillDetail> hanaMaterialAllotBillDetailList = new ArrayList<HanaMaterialAllotBillDetail>();

            hanaMaterialAllotBill.setDetailList(hanaMaterialAllotBillDetailList);

            SqlCon inputDetailSqlCon = new SqlCon();
            inputDetailSqlCon.addMainSql("SELECT INPUT_ID, INPUT_LN, MATERIAL_ID ,MATERIAL_BATCH");
            inputDetailSqlCon.addMainSql(" ,INPUT_WAREHOUSE_ID ,INPUT_QTY");
            inputDetailSqlCon.addMainSql(" FROM SC_M_BILL_INPUT_DETAIL");
            inputDetailSqlCon.addMainSql(" WHERE DELETED_FLAG = '0'");
            inputDetailSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_INPUT_ALLOT, " AND EVENT_CODE = ?");
            inputDetailSqlCon.addCondition(inputModel.getRowId(), " AND INPUT_ID = ?");
            inputDetailSqlCon.addMainSql(" ORDER BY INPUT_ID, INPUT_LN");

            List<InputDetailModel> inputDetailModelList = setSql(inputDetailMapper, inputDetailSqlCon);

            SqlCon outputDetailSqlCon = new SqlCon();
            outputDetailSqlCon.addCondition(inputDetailModelList.get(0).getInputId(), " AND INPUT_ID = ?");
            outputDetailSqlCon.addCondition(inputDetailModelList.get(0).getInputLn(), " AND INPUT_LN = ?");
            outputDetailSqlCon.addCondition(SupplyChianConstants.EVENT_CODE_OUTPUT_ALLOT, " AND EVENT_CODE = ?");
            OutputDetailModel outputDetailModel = getModel(outputDetailMapper, outputDetailSqlCon);

            if (outputDetailModel == null) {
                Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "不存在调拨出库单！。。。");
            }

            for (InputDetailModel inputDetailModel : inputDetailModelList) {
                String mtcItemCode = HanaUtil.getMTC_ItemCodeOfMaterial(inputDetailModel.getMaterialId());
                Map<String, String> materialInfo = CacheUtil.getMaterialInfo(String.valueOf(inputDetailModel.getMaterialId()),
                        MaterialInfoEnum.MATERIAL_INFO);
                // SAP是否库存管理，库存管理才有出库和反出库操作
                if (HanaUtil.isWarehouseMaterialToHanaCheck(Maps.getString(materialInfo, "materialFirstClass"), Maps.getString(materialInfo,
                        "materialSecondClass"))) {
                    HanaMaterialAllotBillDetail hanaMaterialAllotBillDetail = new HanaMaterialAllotBillDetail();
                    // 分公司编码
                    hanaMaterialAllotBillDetail.setMTC_BranchID(mtcBranchID);
                    // 新农+单据编号
                    hanaMaterialAllotBillDetail.setMTC_BaseEntry(String.valueOf(inputDetailModel.getInputId()));
                    // 新农+单据行号
                    hanaMaterialAllotBillDetail.setMTC_BaseLine(String.valueOf(inputDetailModel.getInputLn()));
                    // 物料编号
                    hanaMaterialAllotBillDetail.setMTC_ItemCode(mtcItemCode);
                    // 批次编号
                    hanaMaterialAllotBillDetail.setMTC_BatchNum(inputDetailModel.getMaterialBatch());
                    // 调出仓库编号
                    hanaMaterialAllotBillDetail.setMTC_FromWhsCode(String.valueOf(outputDetailModel.getOutputWarehouseId()));
                    // 调入仓库编号
                    hanaMaterialAllotBillDetail.setMTC_ToWhsCode(String.valueOf(inputDetailModel.getInputWarehouseId()));
                    // 调拨数量
                    hanaMaterialAllotBillDetail.setMTC_Qty(String.valueOf(inputDetailModel.getInputQty()));

                    hanaMaterialAllotBillDetailList.add(hanaMaterialAllotBillDetail);

                    if (mtcFromWhsCode == null) {
                        mtcFromWhsCode = String.valueOf(outputDetailModel.getOutputWarehouseId());
                    }

                    if (mtcToWhsCode == null) {
                        mtcToWhsCode = String.valueOf(inputDetailModel.getInputWarehouseId());
                    }
                }
            }

            // 从明细中取第一条调出仓库和调入仓库
            // 调出仓库
            hanaMaterialAllotBill.setMTC_FromWhsCode(mtcFromWhsCode);
            // 调入仓库
            hanaMaterialAllotBill.setMTC_ToWhsCode(mtcToWhsCode);

            if (!hanaMaterialAllotBill.getDetailList().isEmpty()) {
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(hanaMaterialAllotBill.getMTC_DocDate(), TimeUtil.TIME_FORMAT));
                // 业务代码：库存调拨
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_2090);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(hanaMaterialAllotBill.getMTC_BaseEntry());
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaMaterialAllotBill);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

                hanaCommonService.insertMTC_ITFC(mtcITFC);
            }

        } else {
            Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "不存在数据或存在多条相关数据或者此单据为调拨出库单（SAP无需传调拨出库，只需传调拨单[调拨入库]）！。。。");
        }

    }

    @Override
    public void editHouseToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        String[] companyMark = new String[] { "1,2,3,4,8", "1,2,3,4,10", "1,2,3,4,11", "1,2,3,178,179" };
        for (String string : companyMark) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T1.farmId,T1.houseNumber FROM (");
            sqlCon.addMainSql("SELECT CONCAT(T1.HOUSE_PRIFIX_CODE,T1.`HOUSE_UNIT_NUMBER`,T1.`HOUSE_BUILDING_NUMBER`) AS houseNumber");
            sqlCon.addMainSql(",T1.FARM_ID AS farmId");
            sqlCon.addMainSql(" FROM `pp_o_house` T1 ");
            sqlCon.addMainSql(" INNER JOIN `hr_m_company` T2");
            sqlCon.addMainSql(" ON(T1.FARM_ID = T2.ROW_ID");
            sqlCon.addMainSql(" AND T2.DELETED_FLAG = '0')");
            sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
            sqlCon.addCondition(string, " AND T2.COMPANY_MARK = ?");
            sqlCon.addMainSql(" AND T1.HOUSE_PRIFIX_CODE IS NOT NULL");
            sqlCon.addMainSql(" AND T1.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" ) T1");
            sqlCon.addMainSql(" GROUP BY T1.houseNumber ORDER BY T1.houseNumber");

            Map<String, String> sqlMap = new HashMap<String, String>();
            sqlMap.put("sql", sqlCon.getCondition());

            Date currentDate = new Date();

            List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

            for (Map<String, Object> map : list) {
                HanaPigHouse hanaPigHouse = new HanaPigHouse();
                // 猪舍编号
                hanaPigHouse.setMTC_UnitID(Maps.getString(map, "houseNumber"));

                Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(Maps.getLong(map, "farmId"));
                String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 业务代码:主数据 - 栋舍
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1030);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(Maps.getString(map, "houseNumber"));
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(new Date());
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(Maps.getLong(map, "farmId")));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaPigHouse);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                mtcITFCList.add(mtcITFC);

            }

            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
    }

    @Override
    public void editCustomerToHANA(Map<String, Object> inputMap) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        String[] companyCode = new String[] { "CIC17050018", "CIC17050019", "CIC17050021", "CIC17050008", "CIC17050022", "CIC17050009", "CIC17050023",
                "CIC17050025", "CIC17050011", "CIC17050026", "CIC17050012", "CIC17050013", "CIC17050017", "CIC17070002" };
        for (String string : companyCode) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T1.* FROM `hr_m_company` T1");
            sqlCon.addConditionWithNull(string, " WHERE T1.COMPANY_CODE = ?");
            // sqlCon.addMainSql(" INNER JOIN `hr_r_srm` T2");
            // sqlCon.addMainSql(" ON (T1.DELETED_FLAG = '0'");
            // sqlCon.addMainSql(" AND T2.CUSSUP_ID = T1.ROW_ID)");
            // sqlCon.addMainSql(" INNER JOIN hr_m_company T3");
            // sqlCon.addMainSql(" ON(T3.DELETED_FLAG = '0'");
            // sqlCon.addMainSql(" AND T2.FARM_ID = T3.ROW_ID)");
            // sqlCon.addMainSql(" WHERE T2.CUSSUP_TYPE = 'C'");
            // sqlCon.addMainSql(" AND T1.CUSSUP_TYPE = 'C'");
            // sqlCon.addMainSql(" AND T3.COMPANY_MARK LIKE '1,2,3,%'");
            // sqlCon.addMainSql(" GROUP BY T1.ROW_ID ORDER BY T1.ROW_ID");

            List<CompanyModel> companyModelList = setSql(companyMapper, sqlCon);
            Date currentDate = new Date();

            List<MTC_ITFC> mtcITFCList = new ArrayList<MTC_ITFC>();

            for (CompanyModel company : companyModelList) {
                HanaClientOrProvider hanaClientOrProvider = new HanaClientOrProvider();
                // 客户/供应商编号
                String mtcCardCode = HanaUtil.getMTC_CardCode(company.getRowId());
                // 业务伙伴类别
                hanaClientOrProvider.setMTC_CardType(company.getCussupType());

                hanaClientOrProvider.setMTC_CardCode(mtcCardCode);
                // 客户/供应商名称
                hanaClientOrProvider.setMTC_CardName(company.getCompanyName());
                // 客户/供应商分类
                // 客户
                hanaClientOrProvider.setMTC_GroupCode("100");
                // 供应商分类
                // hanaClientOrProvider.setMTC_GroupCode("101");

                // 客户/供应商简称
                hanaClientOrProvider.setMTC_CardFName(company.getSortName());
                // 客户/供应商地址
                hanaClientOrProvider.setMTC_AliasName(company.getCompanyAddress());

                MTC_ITFC mtcITFC = new MTC_ITFC();
                // 分公司编号
                // mtcITFC.setMTC_Branch(mtcBranchID);
                // 业务日期
                mtcITFC.setMTC_DocDate(TimeUtil.parseDate(currentDate, TimeUtil.DATE_FORMAT));
                // 客户
                mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1070);
                // 供应商
                // mtcITFC.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_1080);
                // 新农+单据编号
                mtcITFC.setMTC_DocNum(mtcCardCode);
                // 优先级
                mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
                // 处理区分
                mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
                // 创建日期
                mtcITFC.setMTC_CreateTime(currentDate);
                // 同步状态
                mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
                // DB
                mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(3L));
                // JSON文件
                String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaClientOrProvider);
                mtcITFC.setMTC_DataFile(jsonDataFile);
                // JSON文件长度
                mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
                mtcITFCList.add(mtcITFC);
            }
            hanaCommonService.insertsMTC_ITFC(mtcITFCList);
        }
    }

    @Override
    public void editSowToProductToHANA(Map<String, Object> map) throws Exception {
         Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        Date currentDate = new Date();

        String[] values = new String[] { "C1018.482063.C1202", "C1018.521113.F0777", "C1018.521116.F0795", "C1038.890610.A0765",
                "C1038.890614.AN0050", "C1038.890620.AN0117", "C1038.890602.A0767", "C1038.890609.AN0048", "C1038.890611.AN0138",
                "C1038.890615.AN0067", "C1038.890617.AN0070" };

        // String[] values = new String[] { "C1019.859610.71491"
        // ,"C1019.898595.71388"
        // ,"C1019.898600.71378"
        // ,"C1019.903689.71733"
        // ,"C1019.903704.71409"
        // ,"C1019.903705.71883"
        // ,"C1019.906223.71924"
        // ,"C1019.906229.71880"
        // ,"C1019.906231.71830"
        // ,"C1019.906232.71858"
        // ,"C1019.906236.71908"
        // ,"C1019.902315.71135"
        // ,"C1019.902316.71385"
        // ,"C1019.903701.71745"
        // ,"C1019.903702.71887"
        // ,"C1019.906225.71853"
        // ,"C1019.906233.71901"
        // ,"C1019.840534.71119"
        // ,"C1019.840548.71488"
        // ,"C1019.860987.71521"
        // ,"C1019.898593.71405"
        // ,"C1019.902319.71380"
        // ,"C1019.902321.71486"
        // ,"C1019.902326.71357"
        // ,"C1019.902332.71397"
        // ,"C1019.903686.71746"
        // ,"C1019.903688.71400"
        // ,"C1019.903695.71738"
        // ,"C1019.903696.71842"
        // ,"C1019.903698.71749"
        // ,"C1019.903699.71525"
        // ,"C1019.903706.71747"
        // ,"C1019.903708.71744"
        // ,"C1019.906227.71876"
        // };

        for (String value : values) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T1.PIG_ID,T4.TOWORK_DATE,IFNULL(T5.HOUSE_ID,T4.HOUSE_ID) AS HOUSE_ID,T4.BILL_ID,T4.FARM_ID,T4.LINE_NUMBER");
            sqlCon.addMainSql(" FROM `pp_l_bill_towork` T1");
            sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T2");
            sqlCon.addMainSql(" ON(T1.FARM_ID = T2.ROW_ID)");
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG T3");
            sqlCon.addMainSql(" ON(T1.PIG_ID = T3.ROW_ID)");
            sqlCon.addMainSql(" INNER JOIN pp_l_bill_towork T4");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T4.CHANGE_PIG_CLASS_ID)");
            sqlCon.addMainSql(" LEFT OUTER JOIN pp_l_bill_change_house T5");
            sqlCon.addMainSql(" ON(T4.PIG_ID = T5.PIG_ID AND T4.BILL_ID = T5.BILL_ID)");
            sqlCon.addMainSql(" WHERE T1.PIG_CLASS = '4' AND T1.PIG_CLASS_OUT = '9'");
            sqlCon.addCondition(value, " AND T3.SAP_FIXED_ASSETS_EARBRAND = ?");
            sqlCon.addMainSql(" AND T1.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T2.COMPANY_MARK LIKE '1,2,3,%'");

            Map<String, Object> infoMap = paramMapperSetSQL(paramMapper, sqlCon).get(0);

            Long farmId = Maps.getLong(infoMap, "FARM_ID");
            Long billId = Maps.getLong(infoMap, "BILL_ID");
            Long pigId = Maps.getLong(infoMap, "PIG_ID");
            Date enterDate = Maps.getDate(infoMap, "TOWORK_DATE");
            Long houseId = Maps.getLong(infoMap, "HOUSE_ID");
            Long lineNumber = Maps.getLong(infoMap, "LINE_NUMBER");
            PigModel pigModel = pigMapper.searchById(pigId);

            String mtcObjCode = HanaConstants.MTC_ITFC_OBJ_CODE_3010;

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            BillModel billModel = billMapper.searchById(billId);

            // 3010 - 后备转生产【应收发票】 - 表头
            String baseEntry = mtcBranchID + "-" + billModel.getRowId() + "-" + billModel.getBusinessCode();
            HanaGatherInvoiceHeaderDetail hanaGatherInvoiceHeaderDetail = new HanaGatherInvoiceHeaderDetail();
            // 分公司编码
            hanaGatherInvoiceHeaderDetail.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaGatherInvoiceHeaderDetail.setMTC_BaseEntry(baseEntry);
            // 业务伙伴编号
            hanaGatherInvoiceHeaderDetail.setMTC_CardCode(mtcBranchID);
            // 单据日期
            hanaGatherInvoiceHeaderDetail.setMTC_DocDate(enterDate);
            // 销售费用
            hanaGatherInvoiceHeaderDetail.setMTC_Fee("0");
            // 销售费用说明
            hanaGatherInvoiceHeaderDetail.setMTC_FeeRmk("0");
            // 货款差异
            hanaGatherInvoiceHeaderDetail.setMTC_AmtDiff("0");

            // 3010 - 后备转生产【应收发票】 - 表行
            HanaGatherInvoiceRowsDetail hanaGatherInvoiceRowsDetail = new HanaGatherInvoiceRowsDetail();
            // 分公司编码
            hanaGatherInvoiceRowsDetail.setMTC_BranchID(mtcBranchID);
            // 猪场编码
            hanaGatherInvoiceRowsDetail.setMTC_DeptID(mtcDeptID);
            // 新农+单据编号
            hanaGatherInvoiceRowsDetail.setMTC_BaseEntry(String.valueOf(billModel.getRowId()));
            // 新农+单据行号
            hanaGatherInvoiceRowsDetail.setMTC_BaseLine(String.valueOf(lineNumber));

            // 销售类型
            String mtcSalesType = null;
            // 销售类型：固定值：SP - 后备转生产
            mtcSalesType = HanaConstants.MTC_SALES_TYPE_SP;

            hanaGatherInvoiceRowsDetail.setMTC_SalesType(mtcSalesType);

            // 品名：固定值：701005 - 商品猪 - 后备种猪
            String mtcItemCode = HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;

            hanaGatherInvoiceRowsDetail.setMTC_ItemCode(mtcItemCode);

            // 猪只耳号：初始耳号
            hanaGatherInvoiceRowsDetail.setMTC_BatchNum(pigModel.getSapFixedAssetsEarbrand());
            // 猪舍编号
            hanaGatherInvoiceRowsDetail.setMTC_Unit(HanaUtil.getMTC_Unit(houseId));
            // 猪只品种
            hanaGatherInvoiceRowsDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigModel.getBreedId()));
            // 猪只性别
            hanaGatherInvoiceRowsDetail.setMTC_Sex(HanaUtil.getMTC_Sex(pigModel.getSex()));
            // 转生产头数
            hanaGatherInvoiceRowsDetail.setMTC_Qty("1");
            // 价格
            hanaGatherInvoiceRowsDetail.setMTC_Price("0");
            // 金额
            hanaGatherInvoiceRowsDetail.setMTC_Total("0");

            List<HanaGatherInvoiceRowsDetail> listRows = new ArrayList<HanaGatherInvoiceRowsDetail>();
            listRows.add(hanaGatherInvoiceRowsDetail);
            hanaGatherInvoiceHeaderDetail.setDetailList(listRows);

            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(mtcBranchID);
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(enterDate, TimeUtil.TIME_FORMAT));
            // 业务代码:3010 后备转生产 - A 应收发票
            mtcITFC.setMTC_ObjCode(mtcObjCode);
            // 新农+单据编号
            mtcITFC.setMTC_DocNum(baseEntry);
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaGatherInvoiceHeaderDetail);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());
            // DbBeanName
            mtcITFC.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));

            hanaCommonService.insertMTC_ITFC(mtcITFC);

            // 资产卡片
            EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
            MTC_OITM mtcOITM = new MTC_OITM();
            // 猪只耳号
            mtcOITM.setMTC_ItemCode(pigModel.getSapFixedAssetsEarbrand());
            // 猪只描述
            mtcOITM.setMTC_ItemName(CacheUtil.getName(pigModel.getBreedId().toString(), NameEnum.BREED_NAME) + "." + earCodeModel.getEarBrand());
            // 猪只性别
            mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(pigModel.getSex()));
            // 资产类型
            mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(mtcBranchID, pigModel.getSex()));
            // 转生产日龄
            if (pigModel.getToProductDayage() != null) {
                mtcOITM.setMTC_DaysOld(String.valueOf(pigModel.getToProductDayage()));
            }
            // 猪场编号
            mtcOITM.setMTC_DeptID(mtcDeptID);
            // 棚舍编号
            mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(houseId));

            // 3021 - 后备转生产【固定资产卡片 - OITM】的MTC_ITFC
            MTC_ITFC mtcITFC01 = new MTC_ITFC();
            // 分公司编号
            mtcITFC01.setMTC_Branch(mtcBranchID);
            // 业务日期
            mtcITFC01.setMTC_DocDate(billModel.getBillDate());
            // 业务代码：后备转生产 - B 固定资产卡片
            mtcITFC01.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3011);
            // 新农+单据编号
            mtcITFC01.setMTC_DocNum(pigModel.getSapFixedAssetsEarbrand());
            // 优先级
            mtcITFC01.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC01.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC01.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC01.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // JSON文件
            String jsonDataFile01 = HanaJacksonUtil.objectToJackson(mtcOITM);
            mtcITFC01.setMTC_DataFile(jsonDataFile01);
            // JSON文件长度
            mtcITFC01.setMTC_DataFileLen(jsonDataFile01.length());
            // DbBeanName
            mtcITFC01.setDB_Bean_Name(HanaUtil.getDbBeanName(farmId));
            hanaCommonService.insertMTC_ITFC(mtcITFC01);
        }
    }

    @Override
    public void editBoarToProductToHANA(Map<String, Object> map) throws Exception {
        Thrower.throwException(SupplyChianException.SELF_DEFINITION_ERROR, "如需使用，请联系管理员。。。");
        Date currentDate = new Date();

        String[] values = new String[] {};

        // String[] values = new String[] { "C1019.859610.71491"
        // ,"C1019.898595.71388"
        // ,"C1019.898600.71378"
        // ,"C1019.903689.71733"
        // ,"C1019.903704.71409"
        // ,"C1019.903705.71883"
        // ,"C1019.906223.71924"
        // ,"C1019.906229.71880"
        // ,"C1019.906231.71830"
        // ,"C1019.906232.71858"
        // ,"C1019.906236.71908"
        // ,"C1019.902315.71135"
        // ,"C1019.902316.71385"
        // ,"C1019.903701.71745"
        // ,"C1019.903702.71887"
        // ,"C1019.906225.71853"
        // ,"C1019.906233.71901"
        // ,"C1019.840534.71119"
        // ,"C1019.840548.71488"
        // ,"C1019.860987.71521"
        // ,"C1019.898593.71405"
        // ,"C1019.902319.71380"
        // ,"C1019.902321.71486"
        // ,"C1019.902326.71357"
        // ,"C1019.902332.71397"
        // ,"C1019.903686.71746"
        // ,"C1019.903688.71400"
        // ,"C1019.903695.71738"
        // ,"C1019.903696.71842"
        // ,"C1019.903698.71749"
        // ,"C1019.903699.71525"
        // ,"C1019.903706.71747"
        // ,"C1019.903708.71744"
        // ,"C1019.906227.71876"
        // };

        for (String value : values) {
            SqlCon sqlCon = new SqlCon();
            sqlCon.addMainSql("SELECT T1.PIG_ID,T4.TOWORK_DATE,IFNULL(T5.HOUSE_ID,T4.HOUSE_ID) AS HOUSE_ID,T4.BILL_ID,T4.FARM_ID,T4.LINE_NUMBER");
            sqlCon.addMainSql(" FROM `pp_l_bill_towork` T1");
            sqlCon.addMainSql(" INNER JOIN HR_M_COMPANY T2");
            sqlCon.addMainSql(" ON(T1.FARM_ID = T2.ROW_ID)");
            sqlCon.addMainSql(" INNER JOIN PP_L_PIG T3");
            sqlCon.addMainSql(" ON(T1.PIG_ID = T3.ROW_ID)");
            sqlCon.addMainSql(" INNER JOIN pp_l_bill_towork T4");
            sqlCon.addMainSql(" ON(T1.ROW_ID = T4.CHANGE_PIG_CLASS_ID)");
            sqlCon.addMainSql(" LEFT OUTER JOIN pp_l_bill_change_house T5");
            sqlCon.addMainSql(" ON(T4.PIG_ID = T5.PIG_ID AND T4.BILL_ID = T5.BILL_ID)");
            sqlCon.addMainSql(" WHERE T1.PIG_CLASS = '4' AND T1.PIG_CLASS_OUT = '9'");
            sqlCon.addCondition(value, " AND T3.SAP_FIXED_ASSETS_EARBRAND = ?");
            sqlCon.addMainSql(" AND T1.DELETED_FLAG = '0'");
            sqlCon.addMainSql(" AND T2.COMPANY_MARK LIKE '1,2,3,5,%'");

            Map<String, Object> infoMap = paramMapperSetSQL(paramMapper, sqlCon).get(0);

            Long farmId = Maps.getLong(infoMap, "FARM_ID");
            Long billId = Maps.getLong(infoMap, "BILL_ID");
            Long pigId = Maps.getLong(infoMap, "PIG_ID");
            Date enterDate = Maps.getDate(infoMap, "TOWORK_DATE");
            Long houseId = Maps.getLong(infoMap, "HOUSE_ID");
            Long lineNumber = Maps.getLong(infoMap, "LINE_NUMBER");
            PigModel pigModel = pigMapper.searchById(pigId);

            String mtcObjCode = HanaConstants.MTC_ITFC_OBJ_CODE_3010;

            Map<String, String> mtcBranchIDAndMTC_DeptIDMap = HanaUtil.getMTC_BranchIDAndMTC_DeptID(farmId);
            String mtcBranchID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_BranchID);
            String mtcDeptID = Maps.getString(mtcBranchIDAndMTC_DeptIDMap, HanaUtil.MTC_DeptID);

            BillModel billModel = billMapper.searchById(billId);

            // 3010 - 后备转生产【应收发票】 - 表头
            String baseEntry = mtcBranchID + "-" + billModel.getRowId() + "-" + billModel.getBusinessCode();
            HanaGatherInvoiceHeaderDetail hanaGatherInvoiceHeaderDetail = new HanaGatherInvoiceHeaderDetail();
            // 分公司编码
            hanaGatherInvoiceHeaderDetail.setMTC_BranchID(mtcBranchID);
            // 新农+单据编号
            hanaGatherInvoiceHeaderDetail.setMTC_BaseEntry(baseEntry);
            // 业务伙伴编号
            hanaGatherInvoiceHeaderDetail.setMTC_CardCode(mtcBranchID);
            // 单据日期
            hanaGatherInvoiceHeaderDetail.setMTC_DocDate(enterDate);
            // 销售费用
            hanaGatherInvoiceHeaderDetail.setMTC_Fee("0");
            // 销售费用说明
            hanaGatherInvoiceHeaderDetail.setMTC_FeeRmk("0");
            // 货款差异
            hanaGatherInvoiceHeaderDetail.setMTC_AmtDiff("0");

            // 3010 - 后备转生产【应收发票】 - 表行
            HanaGatherInvoiceRowsDetail hanaGatherInvoiceRowsDetail = new HanaGatherInvoiceRowsDetail();
            // 分公司编码
            hanaGatherInvoiceRowsDetail.setMTC_BranchID(mtcBranchID);
            // 猪场编码
            hanaGatherInvoiceRowsDetail.setMTC_DeptID(mtcDeptID);
            // 新农+单据编号
            hanaGatherInvoiceRowsDetail.setMTC_BaseEntry(String.valueOf(billModel.getRowId()));
            // 新农+单据行号
            hanaGatherInvoiceRowsDetail.setMTC_BaseLine(String.valueOf(lineNumber));

            // 销售类型
            String mtcSalesType = null;
            // 销售类型：固定值：SP - 后备转生产
            mtcSalesType = HanaConstants.MTC_SALES_TYPE_SP;

            hanaGatherInvoiceRowsDetail.setMTC_SalesType(mtcSalesType);

            // 品名：固定值：701005 - 商品猪 - 后备种猪
            String mtcItemCode = HanaConstants.MTC_ITEM_CODE_RESERVE_PIG;

            hanaGatherInvoiceRowsDetail.setMTC_ItemCode(mtcItemCode);

            // 猪只耳号：初始耳号
            hanaGatherInvoiceRowsDetail.setMTC_BatchNum(pigModel.getSapFixedAssetsEarbrand());
            // 猪舍编号
            hanaGatherInvoiceRowsDetail.setMTC_Unit(HanaUtil.getMTC_Unit(houseId));
            // 猪只品种
            hanaGatherInvoiceRowsDetail.setMTC_Breed(HanaUtil.getMTC_Breed(pigModel.getBreedId()));
            // 猪只性别
            hanaGatherInvoiceRowsDetail.setMTC_Sex(HanaUtil.getMTC_Sex(pigModel.getSex()));
            // 转生产头数
            hanaGatherInvoiceRowsDetail.setMTC_Qty("1");
            // 价格
            hanaGatherInvoiceRowsDetail.setMTC_Price("0");
            // 金额
            hanaGatherInvoiceRowsDetail.setMTC_Total("0");

            List<HanaGatherInvoiceRowsDetail> listRows = new ArrayList<HanaGatherInvoiceRowsDetail>();
            listRows.add(hanaGatherInvoiceRowsDetail);
            hanaGatherInvoiceHeaderDetail.setDetailList(listRows);

            MTC_ITFC mtcITFC = new MTC_ITFC();
            // 分公司编号
            mtcITFC.setMTC_Branch(mtcBranchID);
            // 业务日期
            mtcITFC.setMTC_DocDate(TimeUtil.parseDate(enterDate, TimeUtil.TIME_FORMAT));
            // 业务代码:3010 后备转生产 - A 应收发票
            mtcITFC.setMTC_ObjCode(mtcObjCode);
            // 新农+单据编号
            mtcITFC.setMTC_DocNum(baseEntry);
            // 优先级
            mtcITFC.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC.setMTC_CreateTime(TimeUtil.parseDate(currentDate, TimeUtil.TIME_FORMAT));
            // 同步状态
            mtcITFC.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // JSON文件
            String jsonDataFile = HanaJacksonUtil.objectToJackson(hanaGatherInvoiceHeaderDetail);
            mtcITFC.setMTC_DataFile(jsonDataFile);
            // JSON文件长度
            mtcITFC.setMTC_DataFileLen(jsonDataFile.length());

            hanaCommonService.insertMTC_ITFC(mtcITFC);

            // 资产卡片
            EarCodeModel earCodeModel = earCodeMapper.searchById(pigModel.getEarCodeId());
            MTC_OITM mtcOITM = new MTC_OITM();
            // 猪只耳号
            mtcOITM.setMTC_ItemCode(pigModel.getSapFixedAssetsEarbrand());
            // 猪只描述
            mtcOITM.setMTC_ItemName(CacheUtil.getName(pigModel.getBreedId().toString(), NameEnum.BREED_NAME) + "." + earCodeModel.getEarBrand());
            // 猪只性别
            mtcOITM.setMTC_ItmsGrpCod(HanaUtil.getMTC_ItmsGrpCod(pigModel.getSex()));
            // 资产类型
            mtcOITM.setMTC_Type(HanaUtil.getMTC_Type(mtcBranchID, pigModel.getSex()));
            // 转生产日龄
            if (pigModel.getToProductDayage() != null) {
                mtcOITM.setMTC_DaysOld(String.valueOf(pigModel.getToProductDayage()));
            }
            // 猪场编号
            mtcOITM.setMTC_DeptID(mtcDeptID);
            // 棚舍编号
            mtcOITM.setMTC_Unit(HanaUtil.getMTC_Unit(houseId));

            // 3021 - 后备转生产【固定资产卡片 - OITM】的MTC_ITFC
            MTC_ITFC mtcITFC01 = new MTC_ITFC();
            // 分公司编号
            mtcITFC01.setMTC_Branch(mtcBranchID);
            // 业务日期
            mtcITFC01.setMTC_DocDate(billModel.getBillDate());
            // 业务代码：后备转生产 - B 固定资产卡片
            mtcITFC01.setMTC_ObjCode(HanaConstants.MTC_ITFC_OBJ_CODE_3011);
            // 新农+单据编号
            mtcITFC01.setMTC_DocNum(pigModel.getSapFixedAssetsEarbrand());
            // 优先级
            mtcITFC01.setMTC_Priority(HanaConstants.MTC_ITFC_PRIORITY_1);
            // 处理区分
            mtcITFC01.setMTC_TransType(HanaConstants.MTC_ITFC_TRANS_TYPE_A);
            // 创建日期
            mtcITFC01.setMTC_CreateTime(currentDate);
            // 同步状态
            mtcITFC01.setMTC_Status(HanaConstants.MTC_ITFC_STATUS_N);
            // JSON文件
            String jsonDataFile01 = HanaJacksonUtil.objectToJackson(mtcOITM);
            mtcITFC01.setMTC_DataFile(jsonDataFile01);
            // JSON文件长度
            mtcITFC01.setMTC_DataFileLen(jsonDataFile01.length());

            hanaCommonService.insertMTC_ITFC(mtcITFC01);
        }
    }

    /* END 导入期初到SAP中 */

}
