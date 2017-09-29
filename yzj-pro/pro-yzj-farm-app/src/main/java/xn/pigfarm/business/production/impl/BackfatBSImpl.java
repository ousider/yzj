package xn.pigfarm.business.production.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xn.core.data.enums.SeqEnum;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.SeqUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.data.Maps;
import xn.pigfarm.business.log.ILogBS;
import xn.pigfarm.business.production.IBackfatBS;
import xn.pigfarm.business.production.ICommonBS;
import xn.pigfarm.mapper.production.BackfatMapper;
import xn.pigfarm.model.production.BackfatModel;
import xn.pigfarm.model.production.BackfatView;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;

@Component("backfatBS")
public class BackfatBSImpl extends BaseServiceImpl implements IBackfatBS {

    @Autowired
    private ICommonBS commonBSImpl;
    
    @Autowired
    private BackfatMapper backfatMapper;

    @Autowired
    private ILogBS logBSImpl;

    @Override
    public void backFat(List<BackfatView> backfatViews, String eventName, long billId) throws Exception {

        // 获取猪只基本信息
        List<Long> pigIds = new ArrayList<Long>();
        for (BackfatView backfatView : backfatViews) {
            pigIds.add(backfatView.getPigId());
        }
        List<Map<String, Object>> pigInfos = commonBSImpl.getPigBaseInfo(pigIds, "pp_l_bill_backfat");
        
        List<PigModel> pigModelList = commonBSImpl.createPigModelList(backfatViews);

        Date createDate = new Date();
        List<BackfatModel> backfatList = new ArrayList<BackfatModel>();
        List<PigEventHisModel> hisList = new ArrayList<PigEventHisModel>();
        for (int i = 0; i < pigInfos.size(); i++) {
            BackfatModel backfatModel = new BackfatModel();
            BackfatView backfatView = backfatViews.get(i);
            Map<String, Object> pigMap = pigInfos.get(i);

            long rowId = SeqUtil.getSeq(SeqEnum.BACKFAT);
            backfatModel.setRowId(rowId);
            backfatModel.setBackfat(String.valueOf(backfatView.getBackfat()));
            backfatModel.setBackfatDate(backfatView.getEnterDate());
            backfatModel.setBackfatId(Maps.getLongClass(pigMap, "lastTableId"));
            backfatModel.setBackfatStage(backfatView.getBackfatStage());
            backfatModel.setBillId(billId);
            backfatModel.setCompanyId(getCompanyId());
            backfatModel.setCreateDate(createDate);
            backfatModel.setCreateId(getEmployeeId());
            backfatModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            backfatModel.setFarmId(getFarmId());
            backfatModel.setHouseId(Maps.getLong(pigMap, "houseId"));
            backfatModel.setLineNumber(backfatView.getLineNumber());
            backfatModel.setNotes(backfatView.getNotes());
            backfatModel.setOriginApp(backfatView.getOriginApp());
            backfatModel.setOriginFlag(backfatView.getOriginFlag());
            backfatModel.setParity(Maps.getLong(pigMap, "parity"));
            backfatModel.setPigId(backfatView.getPigId());
            backfatModel.setPigpenId(Maps.getLongClass(pigMap, "pigpenId"));
            backfatModel.setProNo(Maps.getLongClass(pigMap, "proNo"));
            backfatModel.setScore(backfatView.getScore());
            backfatModel.setSortNbr(Maps.getLong(pigMap, "lastTableSortNbr"));
            backfatModel.setStatus(CommonConstants.STATUS);
            backfatModel.setSwineryId(Maps.getLongClass(pigMap, "swineryId"));
            backfatModel.setWorker(backfatView.getWorker());
            backfatList.add(backfatModel);

            PigEventHisModel hisModel = new PigEventHisModel();
            hisModel.setBillId(billId);
            hisModel.setCompanyId(getCompanyId());
            hisModel.setCreateDate(createDate);
            hisModel.setCreateId(getEmployeeId());
            hisModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
            hisModel.setEarBrand(backfatView.getEarBrand());
            hisModel.setEnterDate(backfatView.getEnterDate());
            hisModel.setEventName(eventName);
            hisModel.setEventNotes("耳牌号为：" + backfatView.getEarBrand() + "测定背膘为：【" + backfatView.getBackfat() + "】");
            hisModel.setFarmId(getFarmId());
            hisModel.setHouseId(Maps.getLong(pigMap, "houseId"));
            hisModel.setLastEventDate(backfatView.getEnterDate());
            hisModel.setLineNumber(backfatView.getLineNumber());
            hisModel.setNotes(backfatView.getNotes());
            hisModel.setOriginApp(backfatView.getOriginApp());
            hisModel.setOriginFlag(backfatView.getOriginFlag());
            hisModel.setParity(Maps.getLong(pigMap, "parity"));
            hisModel.setPigClass(Maps.getLong(pigMap, "pigClass"));
            hisModel.setPigId(backfatView.getPigId());
            hisModel.setPigpenId(Maps.getLongClass(pigMap, "pigpenId"));
            hisModel.setPigType(Maps.getString(pigMap, "pigType"));
            hisModel.setSex(Maps.getString(pigMap, "sex"));
            hisModel.setSortNbr(Maps.getLong(pigMap, "hisSortNbr"));
            hisModel.setStatus(CommonConstants.STATUS);
            hisModel.setSwineryId(Maps.getLongClass(pigMap, "swineryId"));
            hisModel.setTableName("pp_l_bill_backfat");
            hisModel.setWorker(backfatView.getWorker());
            hisModel.setTableRowId(rowId);
            hisList.add(hisModel);

        }
        backfatMapper.inserts(backfatList);
        
        logBSImpl.createPigEventHises(hisList, pigModelList, null);
    }

}
