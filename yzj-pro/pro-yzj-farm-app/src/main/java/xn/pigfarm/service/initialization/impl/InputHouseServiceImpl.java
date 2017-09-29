package xn.pigfarm.service.initialization.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.exception.Thrower;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.BeanUtil;
import xn.core.util.ParamUtil;
import xn.core.util.data.Maps;
import xn.pigfarm.exception.initialization.InputHouseException;
import xn.pigfarm.mapper.basicinfo.HouseMapper;
import xn.pigfarm.mapper.basicinfo.PigpenMapper;
import xn.pigfarm.model.basicinfo.HouseModel;
import xn.pigfarm.model.basicinfo.PigpenModel;
import xn.pigfarm.service.initialization.IInputHouseService;

@Service("InputHouseService")
public class InputHouseServiceImpl extends BaseServiceImpl implements IInputHouseService {

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    PigpenMapper pigpenMapper;

    @Override
    public void editInputHouse(String detailList, String xmlName) throws Exception {
        List<Map> uploadData = getJsonList(detailList, Map.class);
        if (uploadData.isEmpty()) {
            Thrower.throwException(InputHouseException.HAS_NO_INPUT_HOUSE);
        }
        // 筛选出house
        Map<String, Map<String, Object>> houseMap = new LinkedHashMap<String, Map<String, Object>>();
        for (Map<String, Object> map : uploadData) {
            houseMap.put(Maps.getString(map, "houseName"), map);
        }

        // 筛选出每个house对应的pigpen
        Map<String, List<Map<String, Object>>> pigpenMap = new HashMap<String, List<Map<String, Object>>>();

        for (Map<String, Object> map : uploadData) {
            String key = Maps.getString(map, "houseName");
            if (pigpenMap.containsKey(key)) {
                pigpenMap.get(key).add(map);
            } else {
                List<Map<String, Object>> houseList = new ArrayList<Map<String, Object>>();
                houseList.add(map);
                pigpenMap.put(key, houseList);
            }
        }

        for (Map.Entry<String, Map<String, Object>> entry : houseMap.entrySet()) {
            HouseModel houseModel = BeanUtil.getBean(HouseModel.class, entry.getValue());
            houseModel.setFarmId(getFarmId());
            houseModel.setCompanyId(getCompanyId());
            if ((houseModel.getRowId() == null || houseModel.getRowId() == 0) && "0".equals(houseModel.getDeletedFlag())) {
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
                } else if (houseModel.getHouseType() == 10) {
                    // 育肥舍
                    eventName = "HOUSE_TYPE_Z";
                }

                // 后台生成猪舍代码
                String businessCode = ParamUtil.getBCode(eventName, getEmployeeId(), getCompanyId(), getFarmId());
                houseModel.setBusinessCode(businessCode);
                houseMapper.insert(houseModel);

                List<PigpenModel> ppPigpenByInsert = new ArrayList<PigpenModel>();

                for (Map<String, Object> map : pigpenMap.get(houseModel.getHouseName())) {
                    PigpenModel pigpenModel = BeanUtil.getBean(PigpenModel.class, map);
                    if(pigpenModel.getPigNum()==null&&pigpenModel.getPigpenName()==null&&pigpenModel.getLength()==null&&pigpenModel.getWidth()==null){
                        break;// 如果猪栏没填写任何信息，则跳过
                    }
                    pigpenModel.setHouseId(houseModel.getRowId());
                    pigpenModel.setFarmId(getFarmId());
                    pigpenModel.setCompanyId(getCompanyId());
                    pigpenModel.setBusinessCode(ParamUtil.getBCode("PIG_STY", getEmployeeId(), getCompanyId(), getFarmId()));
                    ppPigpenByInsert.add(pigpenModel);
                }
                if (!ppPigpenByInsert.isEmpty()) {
                    pigpenMapper.inserts(ppPigpenByInsert);
                }
            }
        }
    }
}
