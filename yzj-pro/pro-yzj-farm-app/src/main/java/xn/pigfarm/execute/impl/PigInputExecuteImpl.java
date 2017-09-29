package xn.pigfarm.execute.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xn.core.data.SqlCon;
import xn.core.exception.Thrower;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.ParamUtil;
import xn.core.util.data.Maps;
import xn.core.util.data.StringUtil;
import xn.pigfarm.exception.BaseBusiException;
import xn.pigfarm.exception.initialization.InputPigException;
import xn.pigfarm.execute.IPigInputExecute;
import xn.pigfarm.mapper.basicinfo.BoarMapper;
import xn.pigfarm.mapper.basicinfo.ConsumableMapper;
import xn.pigfarm.mapper.basicinfo.DeviceMapper;
import xn.pigfarm.mapper.basicinfo.DisinfectorMapper;
import xn.pigfarm.mapper.basicinfo.DrugMapper;
import xn.pigfarm.mapper.basicinfo.FeedMapper;
import xn.pigfarm.mapper.basicinfo.HardwareMapper;
import xn.pigfarm.mapper.basicinfo.MaterialGroupMapper;
import xn.pigfarm.mapper.basicinfo.MaterialMapper;
import xn.pigfarm.mapper.basicinfo.PorkPigMapper;
import xn.pigfarm.mapper.basicinfo.PpeMapper;
import xn.pigfarm.mapper.basicinfo.RawMaterialMapper;
import xn.pigfarm.mapper.basicinfo.SowMapper;
import xn.pigfarm.mapper.basicinfo.SpermMapper;
import xn.pigfarm.mapper.basicinfo.VaccineMapper;
import xn.pigfarm.model.basicinfo.BoarModel;
import xn.pigfarm.model.basicinfo.ConsumableModel;
import xn.pigfarm.model.basicinfo.DeviceModel;
import xn.pigfarm.model.basicinfo.DisinfectorModel;
import xn.pigfarm.model.basicinfo.DrugModel;
import xn.pigfarm.model.basicinfo.FeedModel;
import xn.pigfarm.model.basicinfo.HardwareModel;
import xn.pigfarm.model.basicinfo.MaterialModel;
import xn.pigfarm.model.basicinfo.PorkPigModel;
import xn.pigfarm.model.basicinfo.PpeModel;
import xn.pigfarm.model.basicinfo.RawMaterialModel;
import xn.pigfarm.model.basicinfo.SowModel;
import xn.pigfarm.model.basicinfo.SpermModel;
import xn.pigfarm.model.basicinfo.VaccineModel;
import xn.pigfarm.model.initialization.PpInputPigModel;
import xn.pigfarm.service.initialization.IInputPigService;
import xn.pigfarm.util.constants.MaterialConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * @Description: 处理猪只入场
 * @author zhangjs
 * @date 2016年8月24日 上午11:30:14
 */
@Component("PigInputExecute")
public class PigInputExecuteImpl extends BaseServiceImpl implements IPigInputExecute {

    @Autowired
    private IInputPigService inputService;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialGroupMapper materialGroupMapper;

    @Autowired
    private BoarMapper boarMapper;

    @Autowired
    private PorkPigMapper porkPigMapper;

    @Autowired
    private SowMapper sowMapper;
    
    @Autowired
    private VaccineMapper vaccineMapper;

    @Autowired
    private SpermMapper spermMapper;

    @Autowired
    private RawMaterialMapper rawMaterialMapper;

    @Autowired
    private PpeMapper ppeMapper;

    @Autowired
    private HardwareMapper hardwareMapper;

    @Autowired
    private FeedMapper feedMapper;

    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private DisinfectorMapper disinfectorMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private ConsumableMapper consumableMapper;

    @Autowired
    private IParamMapper paramMapper;


    /**
     * @Description: 初始化系统
     * @author zhangjs
     * @param request
     * @return
     * @throws Exception
     */
    public Map<String, Object> inputPig() throws Exception {

        /* 将进行的情况返回前台 */
        Map<String, Object> countMap = new HashMap<>();
        // 导入总公猪只数
        countMap.put("totalBoarNum", 0);
        // 导入失败公猪只数
        countMap.put("errorBoarNum", 0);
        // 导入总母猪只数
        countMap.put("totalSowNum", 0);
        // 导入失败母猪只数
        countMap.put("errorSowNum", 0);

        // 失败记录
        List<PpInputPigModel> errorList = new ArrayList<>();

        /* 1、查询所有需要入场的猪只 */
        List<PpInputPigModel> inputPigs = inputService.editAndSearchInputPig();
        if (inputPigs == null || inputPigs.isEmpty()) {
            Thrower.throwException(InputPigException.HAS_NO_INPUT_PIG);
        }

        /* 2、处理查询出来的数据 */
        Map<String, List<PpInputPigModel>> map = handleInputPigList(inputPigs, errorList);

        /* 3、初始化猪只 */
        for (Entry<String, List<PpInputPigModel>> pigSet : map.entrySet()) {
            // 处理每一头猪
            countMap = preInputPig(pigSet.getValue(), pigSet.getKey(), errorList, countMap);
        }

        /* 4、修改失败的猪只导入表的状态 */
        if (!errorList.isEmpty()) {
            inputService.editInputPigStatus(errorList, PigConstants.PIG_INIT_STATUS_ERROR);
        }

        return countMap;
    }

    /**
     * @Description: 处理需要导入的猪只数据 ，相同耳号数据放在一起
     * @author zhangjs
     * @param inputPigs
     * @return
     */
    private Map<String, List<PpInputPigModel>> handleInputPigList(List<PpInputPigModel> inputPigs, List<PpInputPigModel> errorList) {
        // 处理的数据存放的map
        Map<String, List<PpInputPigModel>> result = new HashMap<>();
        for (PpInputPigModel pigModel : inputPigs) {
            String earBrand = pigModel.getEarBrand();
            // 耳号为空直接放入错误list中 已在导入的时候进行了验证
            // if (StringUtil.isBlank(earBrand)) {
            // pigModel.setNotes("耳牌号为空，不可以导入");
            // errorList.add(pigModel);
            // continue;
            // }

            // 处理数据
            List<PpInputPigModel> list = result.get(earBrand);
            if (list != null) {
                list.add(pigModel);
            } else {
                list = new ArrayList<>();
                list.add(pigModel);
                result.put(earBrand, list);
            }
        }

        return result;
    }

    /**
     * @Description: 处理每一头猪
     * @author zhangjs
     * @param list
     * @param earBrand
     * @param errorList
     * @param totalBoarNum
     * @param errorBoarNum
     * @param totalSowNum
     * @param errorSowNum
     * @throws Exception
     */
    private Map<String, Object> preInputPig(List<PpInputPigModel> list, String earBrand, List<PpInputPigModel> errorList,
            Map<String, Object> countMap) throws Exception {

        int totalBoarNum = Maps.getInt(countMap, "totalBoarNum");
        int errorBoarNum = Maps.getInt(countMap, "errorBoarNum");
        int totalSowNum = Maps.getInt(countMap, "totalSowNum");
        int errorSowNum = Maps.getInt(countMap, "errorSowNum");

        /* 1 、排序 根据PIG_TYPE , PARITY */
        Collections.sort(list, new Comparator<PpInputPigModel>() {
            @Override
            public int compare(PpInputPigModel o1, PpInputPigModel o2) {
                if (o1.getPigType().compareTo(o2.getPigType()) > 0) {
                    return 1;
                } else if (o1.getPigType().compareTo(o2.getPigType()) < 0) {
                    return -1;
                } else {
                    if (o1.getParity() == null || o2.getParity() == null) {
                        return 0;
                    }
                    if (o1.getParity() > o2.getParity()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });

        /* 2、 取第一条记录判断猪类别 */
        PpInputPigModel prePigModel = list.get(0);
        String pigType = prePigModel.getPigType();

        // 2.1、处理公猪
        if (PigConstants.PIG_TYPE_BOAR.equals(pigType)) {
            totalBoarNum += 1;
            // 一个耳牌号公猪只能有一条记录 导入已经验证过
            // if (list.size() > 1) {
            // for (PpInputPigModel ppInputPigModel : list) {
            // ppInputPigModel.setNotes("公猪耳号【" + earBrand + "】出现重复");
            // }
            // errorList.addAll(list);
            // errorBoarNum += 1;
            // countMap.put("totalBoarNum", totalBoarNum);
            // countMap.put("errorBoarNum", errorBoarNum);
            // return countMap;
            // }
            try {
                inputService.executeInputBoar(prePigModel);
            }
            catch (Exception e) {

                for (PpInputPigModel ppInputPigModel : list) {
                    ppInputPigModel.setNotes(handleErrorMessage(e, "公猪耳号【" + earBrand + "】出现异常："));
                }
                errorList.addAll(list);
                errorBoarNum += 1;
                countMap.put("totalBoarNum", totalBoarNum);
                countMap.put("errorBoarNum", errorBoarNum);
                return countMap;
            }
        }

        // 2.2、处理母猪
        else if (PigConstants.PIG_TYPE_SOW.equals(pigType)) {
            totalSowNum += 1;
            try {
                inputService.executeInputSow(list);
            }
            catch (Exception e) {
                for (PpInputPigModel ppInputPigModel : list) {
                    ppInputPigModel.setNotes(handleErrorMessage(e, "母猪耳号【" + earBrand + "】出现异常："));
                }
                errorList.addAll(list);
                errorSowNum += 1;
                countMap.put("totalSowNum", totalSowNum);
                countMap.put("errorSowNum", errorSowNum);
                return countMap;
            }
        }

        countMap.put("totalBoarNum", totalBoarNum);
        countMap.put("errorBoarNum", errorBoarNum);
        countMap.put("totalSowNum", totalSowNum);
        countMap.put("errorSowNum", errorSowNum);
        return countMap;
    }

    /**
     * @Description: 处理异常信息
     * @author zhangjs
     * @param e
     * @param error
     * @return
     */
    private String handleErrorMessage(Exception e, String error) {
        return StringUtil.substring(error + e.getMessage(), 0, 250);
    }

    @Override
    public Map<String, Object> inputPork(String detailList, String xmlName) throws Exception {
        List<Map> uploadData = getJsonList(detailList, Map.class);
        inputService.excuteInputPorker(uploadData);
        return null;
    }

    @Override
    public Map<String, Object> inputMaterialPork(Map<String, Object> map, String materialType) throws Exception {
        MaterialModel materialModel = getBean(MaterialModel.class, map);
        // 验证名称重复
        long breedCodeIsExist = ParamUtil.isExist("cd_m_material", materialModel.getRowId(), materialModel.getMaterialName()+","+getFarmId(), "MATERIAL_NAME,FARM_ID");
        if (breedCodeIsExist > 0) {
            Thrower.throwException(BaseBusiException.NAME_DUPLICATE_ERROR, materialModel.getMaterialName());
        }

        // 统一单位大写
        if (materialModel.getUnit() != null) {
            materialModel.setUnit(materialModel.getUnit().toUpperCase());
        }

        // 统一单位大写
        if (materialModel.getSpec() != null) {
            materialModel.setSpec(materialModel.getSpec().toUpperCase());
        }

        // 去除空格
        if (materialModel.getManufacturer() != null) {
            materialModel.setManufacturer(materialModel.getManufacturer().trim());
        }

        Long materialId = 0l;
        if (materialModel.getRowId() == 0) {
            // 自动生成编码
            String busCode= ParamUtil.getBCode("MATERIAL_" + materialType.toUpperCase(), getEmployeeId(), getCompanyId(), getFarmId());
            materialModel.setBusinessCode(busCode);
            materialModel.setCompanyId(getCompanyId());
            materialModel.setFarmId(getFarmId());
            materialModel.setOriginApp("EXCEL");
            materialMapper.insert(materialModel);

            // BEGIN 更新RelatedId为它自己的ROW_ID,供应链需求
            materialModel.setRelatedId(materialModel.getRowId());
            materialMapper.update(materialModel);
            // END

            map.put("farmId", getFarmId());
            map.put("companyId", getCompanyId());
            materialId = materialModel.getRowId();

            if (MaterialConstants.MATERIAL_BOAR.equalsIgnoreCase(materialType)) {
                BoarModel boarModel = getBean(BoarModel.class, map);
                boarModel.setMaterialId(materialId);
                if (boarModel.getProductionDayAge() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【转生产日龄【不能为空！");
                }
                if (boarModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种【不能为空！");
                }
                boarModel.setOriginApp("EXCEL");
                boarMapper.insert(boarModel);
            } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
                VaccineModel vaccineModel = getBean(VaccineModel.class, map);
                vaccineModel.setMaterialId(materialId);
                vaccineModel.setOriginApp("EXCEL");
                vaccineMapper.insert(vaccineModel);
            }
            else if (MaterialConstants.MATERIAL_SPERM.equalsIgnoreCase(materialType)) {
                SpermModel spermModel = getBean(SpermModel.class, map);
                spermModel.setMaterialId(materialId);
                spermModel.setOriginApp("EXCEL");
                spermMapper.insert(spermModel);
            } else if (MaterialConstants.MATERIAL_SOW.equalsIgnoreCase(materialType)) {
                SowModel sowModel = getBean(SowModel.class, map);
                if (sowModel.getChangeLabor() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【配种后转入产房区间】不能为空！");
                }
                if (sowModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (sowModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (sowModel.getPregnancyCheckDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊检天数】不能为空！");
                }
                if (sowModel.getPregnancyDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠天数】不能为空！");
                }
                if (sowModel.getErrorLimit() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠误差】不能为空！");
                }
                sowModel.setMaterialId(materialId);
                sowModel.setOriginApp("EXCEL");
                sowMapper.insert(sowModel);
            } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
                RawMaterialModel rawMaterialModel = getBean(RawMaterialModel.class, map);
                rawMaterialModel.setMaterialId(materialId);
                rawMaterialModel.setOriginApp("EXCEL");
                rawMaterialMapper.insert(rawMaterialModel);
            } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
                PpeModel ppeModel = getBean(PpeModel.class, map);
                ppeModel.setMaterialId(materialId);
                ppeModel.setOriginApp("EXCEL");
                ppeMapper.insert(ppeModel);
            } else if (MaterialConstants.MATERIAL_PORK_PIG.equalsIgnoreCase(materialType)) {
                PorkPigModel porkPigModel = getBean(PorkPigModel.class, map);
                porkPigModel.setMaterialId(materialId);
                porkPigModel.setOriginApp("EXCEL");
                if (porkPigModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (porkPigModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (porkPigModel.getBoarId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【父本】不能为空！");
                }
                if (porkPigModel.getSowId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【母本】不能为空！");
                }
                // 肉猪主数据是否存在父本，母本重复
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" SELECT  CONCAT(IFNULL(T1.MATERIAL_NAME,''),'【',IFNULL(T1.BUSINESS_CODE,''),'】' ) NOTES FROM cd_o_pork_pig  T0    "
                        + "INNER JOIN cd_m_material T1    ON T0.MATERIAL_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
                sqlCon.addMainSql("  WHERE T0.BOAR_ID = " + porkPigModel.getBoarId() + " AND T0.SOW_ID = " + porkPigModel.getSowId()
                        + " AND T0.DELETED_FLAG = '0' AND T0.ROW_ID <>" + materialId);
                sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID =?");
                List<PorkPigModel> list1 = setSql(porkPigMapper, sqlCon);
                if (list1.size() > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, list1.get(0).getNotes() + "肉猪主数据已存在父本与母本配对，不能重复！");
                }

                porkPigMapper.insert(porkPigModel);
            } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
                HardwareModel hardwareModel = getBean(HardwareModel.class, map);
                hardwareModel.setMaterialId(materialId);
                hardwareModel.setOriginApp("EXCEL");
                hardwareMapper.insert(hardwareModel);
            } else if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
                FeedModel feedModel = getBean(FeedModel.class, map);
                feedModel.setMaterialId(materialId);
                feedModel.setOriginApp("EXCEL");
                feedMapper.insert(feedModel);
            } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
                DrugModel drugModel = getBean(DrugModel.class, map);
                drugModel.setMaterialId(materialId);
                drugModel.setOriginApp("EXCEL");
                drugMapper.insert(drugModel);
            } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
                DisinfectorModel disinfectorModel = getBean(DisinfectorModel.class, map);
                disinfectorModel.setMaterialId(materialId);
                disinfectorModel.setOriginApp("EXCEL");
                disinfectorMapper.insert(disinfectorModel);
            } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
                DeviceModel deviceModel = getBean(DeviceModel.class, map);
                deviceModel.setMaterialId(materialId);
                deviceModel.setOriginApp("EXCEL");
                deviceMapper.insert(deviceModel);
            } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
                ConsumableModel consumableModel = getBean(ConsumableModel.class, map);
                consumableModel.setMaterialId(materialId);
                consumableModel.setOriginApp("EXCEL");
                consumableMapper.insert(consumableModel);
            }
        } else {

            if (MaterialConstants.MATERIAL_BOAR.equalsIgnoreCase(materialType)) {
                BoarModel boarModel = getBean(BoarModel.class, map);

                if (boarModel.getProductionDayAge() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【转生产日龄】不能为空！");
                }
                if (boarModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                boarModel.setRowId(Maps.getLongClass(map, "childTableId"));
                materialId = boarModel.getMaterialId();
                boarMapper.update(boarModel);
            } else if (MaterialConstants.MATERIAL_VACCINE.equalsIgnoreCase(materialType)) {
                VaccineModel vaccineModel = getBean(VaccineModel.class, map);
                vaccineModel.setRowId(Maps.getLongClass(map, "childTableId"));
                vaccineMapper.update(vaccineModel);
                materialId = vaccineModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_SPERM.equalsIgnoreCase(materialType)) {
                SpermModel spermModel = getBean(SpermModel.class, map);
                spermModel.setRowId(Maps.getLongClass(map, "childTableId"));
                spermMapper.update(spermModel);
                materialId = spermModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_SOW.equalsIgnoreCase(materialType)) {
                SowModel sowModel = getBean(SowModel.class, map);
                if (sowModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (sowModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (sowModel.getPregnancyCheckDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊检天数】不能为空！");
                }
                if (sowModel.getPregnancyDays() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠天数】不能为空！");
                }
                if (sowModel.getErrorLimit() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【妊娠误差】不能为空！");
                }
                sowModel.setRowId(Maps.getLongClass(map, "childTableId"));
                sowMapper.update(sowModel);
                materialId = sowModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_RAW_MATERIAL.equalsIgnoreCase(materialType)) {
                RawMaterialModel rawMaterialModel = getBean(RawMaterialModel.class, map);
                rawMaterialModel.setRowId(Maps.getLongClass(map, "childTableId"));
                rawMaterialMapper.update(rawMaterialModel);
                materialId = rawMaterialModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_PPE.equalsIgnoreCase(materialType)) {
                PpeModel ppeModel = getBean(PpeModel.class, map);
                ppeModel.setRowId(Maps.getLongClass(map, "childTableId"));
                ppeMapper.update(ppeModel);
                materialId = ppeModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_PORK_PIG.equalsIgnoreCase(materialType)) {
                PorkPigModel porkPigModel = getBean(PorkPigModel.class, map);
                if (porkPigModel.getBreedId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品种】不能为空！");
                }
                if (porkPigModel.getStrain() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【品系】不能为空！");
                }
                if (porkPigModel.getBoarId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【父本】不能为空！");
                }
                if (porkPigModel.getSowId() == null) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【母本】不能为空！");
                }
                // 肉猪主数据是否存在父本，母本重复
                SqlCon sqlCon = new SqlCon();
                sqlCon.addMainSql(" SELECT  CONCAT(IFNULL(T1.MATERIAL_NAME,''),'【',IFNULL(T1.BUSINESS_CODE,''),'】' ) NOTES FROM cd_o_pork_pig  T0    "
                        + "INNER JOIN cd_m_material T1    ON T0.MATERIAL_ID = T1.ROW_ID AND T1.DELETED_FLAG = '0' AND T1.STATUS = '1' ");
                sqlCon.addMainSql("  WHERE T0.BOAR_ID = " + porkPigModel.getBoarId() + " AND T0.SOW_ID = " + porkPigModel.getSowId()
                        + " AND T0.DELETED_FLAG = '0' AND T1.ROW_ID <> " + materialModel.getRowId());
                sqlCon.addConditionWithNull(getFarmId(), " AND T0.FARM_ID = ?");
                List<PorkPigModel> list1 = setSql(porkPigMapper, sqlCon);
                if (list1.size() > 0) {
                    Thrower.throwException(BaseBusiException.ERROR_MESSAGE, list1.get(0).getNotes() + "肉猪主数据已存在父本与母本配对，不能重复！");
                }
                if ("Y".equals(porkPigModel.getIsSelect())) {
                    if (porkPigModel.getStockBoarId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【种公猪主数据】不能为空！");
                    }
                    if (porkPigModel.getBroodSowId() == null) {
                        Thrower.throwException(BaseBusiException.ERROR_MESSAGE, "【种母猪主数据】不能为空！");
                    }
                }
                porkPigModel.setRowId(Maps.getLongClass(map, "childTableId"));
                porkPigMapper.update(porkPigModel);
                materialId = porkPigModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_HARDWARE.equalsIgnoreCase(materialType)) {
                HardwareModel hardwareModel = getBean(HardwareModel.class, map);
                hardwareModel.setRowId(Maps.getLongClass(map, "childTableId"));
                hardwareMapper.update(hardwareModel);
                materialId = hardwareModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_FEED.equalsIgnoreCase(materialType)) {
                FeedModel feedModel = getBean(FeedModel.class, map);
                feedModel.setRowId(Maps.getLongClass(map, "childTableId"));
                feedMapper.update(feedModel);
                materialId = feedModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_DRUG.equalsIgnoreCase(materialType)) {
                DrugModel drugModel = getBean(DrugModel.class, map);
                drugModel.setRowId(Maps.getLongClass(map, "childTableId"));
                drugMapper.update(drugModel);
                materialId = drugModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_DISINFECTOR.equalsIgnoreCase(materialType)) {
                DisinfectorModel disinfectorModel = getBean(DisinfectorModel.class, map);
                disinfectorModel.setRowId(Maps.getLongClass(map, "childTableId"));
                disinfectorMapper.update(disinfectorModel);
                materialId = disinfectorModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_DEVICE.equalsIgnoreCase(materialType)) {
                DeviceModel deviceModel = getBean(DeviceModel.class, map);
                deviceModel.setRowId(Maps.getLongClass(map, "childTableId"));
                deviceMapper.update(deviceModel);
                materialId = deviceModel.getMaterialId();
            } else if (MaterialConstants.MATERIAL_CONSUMABLE.equalsIgnoreCase(materialType)) {
                ConsumableModel consumableModel = getBean(ConsumableModel.class, map);
                consumableModel.setRowId(Maps.getLongClass(map, "childTableId"));
                consumableMapper.update(consumableModel);
                materialId = consumableModel.getMaterialId();
            }
            materialModel.setRowId(materialId);
            materialMapper.update(materialModel);
        }
        return null;
    }

}
