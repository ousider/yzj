package xn.pigfarm.business.log.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xn.core.data.SqlCon;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.data.Maps;
import xn.pigfarm.business.log.ILogBS;
import xn.pigfarm.mapper.production.PigEventHisMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.util.constants.EventConstants;
import xn.pigfarm.util.constants.PigConstants;

/**
 * @Description: 事件历史记录BS
 * @author Administrator
 * @date 2016年9月22日 下午3:38:48
 */
@Component("logBS")
public class LogBSImpl extends BaseServiceImpl implements ILogBS {

    @Autowired
    private PigEventHisMapper pigEventHisMapper;

    @Autowired
    private PigMapper pigMapper;

    @Override
    public void createPigEventHises(List<PigEventHisModel> pigEventHisModels, List<PigModel> pigModelList,
            List<Map<String, Object>> changeHouseList) {
        // 取第一个查出eventName，以及billId
        String eventName = pigEventHisModels.get(0).getEventName();
        long billId = pigEventHisModels.get(0).getBillId();

        for (int i = 0; i < pigEventHisModels.size(); i++) {
            PigEventHisModel eventModel = pigEventHisModels.get(i);
            PigModel pigModel = pigModelList.get(i);
            if (!EventConstants.BREED.equals(eventName) || !PigConstants.PIG_TYPE_BOAR.equals(eventModel.getPigType())) {
                if (EventConstants.BACKFAT.equals(eventName)) {
                    pigModel.setLastBillId(billId);
                } else {
                    pigModel.setLastBillId(billId);
                    pigModel.setLastEventDate(eventModel.getEnterDate());
                }
                /******************************** 2017-1-24 yangy 猪栏为空，更新pigpneId为null ********************************************/
                if (pigModel.getPigpenId() == null) {
                    SqlCon sqlCon = new SqlCon();
                    sqlCon.addMainSql(" UPDATE PP_L_PIG SET PIGPEN_ID=NULL ");
                    sqlCon.addCondition(pigModel.getRowId(), " WHERE ROW_ID=? ");
                    setSql(pigMapper, sqlCon);
                }
                /******************************** 2017-1-24 yangy 猪栏为空，更新pigpneId为null ********************************************/
            }
            if (changeHouseList != null && !changeHouseList.isEmpty()) {
                for (Map<String, Object> map : changeHouseList) {
                    if (pigModel.getRowId().equals(Maps.getLong(map, "pigId"))) {
                        pigEventHisModels.get(i).setTableRowId(Maps.getLongClass(map, "changeHouseRowId"));
                    }
                }
            }
        }
        pigEventHisMapper.inserts(pigEventHisModels);
        pigMapper.updates(pigModelList);
    }

}
