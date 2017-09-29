package xn.pigfarm.service.production.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xn.core.data.SqlCon;
import xn.core.data.enums.CodeEnum;
import xn.core.data.enums.NameEnum;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.cache.CacheUtil;
import xn.core.util.data.Maps;
import xn.core.util.page.BasePageInfo;
import xn.pigfarm.mapper.production.PigEventHisMapper;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.model.production.PigEventHisModel;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.service.production.IPigService;

/**
 * @Description: 猪只处理页面逻辑层
 * @author Zhangjc
 * @date 2016年5月27日 下午1:40:15
 */
@Service("pigService")
public class PigServiceImpl extends BaseServiceImpl implements IPigService {

    @Autowired
    private PigEventHisMapper pigEventHisMapper;

    @Autowired
    private PigMapper pigMapper;

    @Override
    public BasePageInfo searchPigHisToPage(Map<String, Object> map) throws Exception {

        SqlCon pigSql = new SqlCon();
        pigSql.addMainSql("SELECT GROUP_CONCAT(ROW_ID) notes FROM pp_l_pig");
        pigSql.addMainSql(" WHERE UNIQUE_PIG_FLAG = (SELECT UNIQUE_PIG_FLAG FROM pp_l_pig WHERE");
        pigSql.addCondition(Maps.getLongClass(map, "pigId"), " ROW_ID = ?)");
        PigModel pigModel = setSql(pigMapper, pigSql).get(0);

        PigModel leaveModel = pigMapper.searchById(Maps.getLongClass(map, "pigId"));

        SqlCon condition = new SqlCon();
        condition.addCondition(pigModel.getNotes(), " AND PIG_ID IN ?", false, true);
        condition.addCondition(Maps.getString(map, "beginEventDate"), " AND LAST_EVENT_DATE >=?");
        condition.addCondition(Maps.getString(map, "endEventDate"), " AND LAST_EVENT_DATE <=?");
        condition.addCondition(Maps.getString(map, "beginCreateDate"), " AND CREATE_DATE >=?");
        condition.addCondition(Maps.getString(map, "endCreateDate"), " AND CREATE_DATE <=?");
        condition.addCondition(Maps.getString(map, "eventName"), " AND EVENT_NAME =?");
        condition.addCondition(Maps.getString(leaveModel.getData(), "leaveDate"), " AND LAST_EVENT_DATE <= ?");
        condition.addMainSql(" ORDER BY LAST_EVENT_DATE DESC, ROW_ID DESC");

        setToPage();
        List<PigEventHisModel> list = getList(pigEventHisMapper, condition);
        list = CacheUtil.setName(list, new NameEnum[] { NameEnum.HOUSE_NAME, NameEnum.CODE_NAME, NameEnum.EMPLOYEE_NAME, NameEnum.FARM_NAME },
                CodeEnum.EVENT_NAME);
        for(PigEventHisModel model : list){
            Map<String, Object> data = model.getData();

            Map<String, String> houseMap = CacheUtil.getData(model.getFarmId(), "PP_O_HOUSE", String.valueOf(model.getHouseId()));
            Map<String, String> workMap = CacheUtil.getData(model.getFarmId(), "HR_O_EMPLOYEE", String.valueOf(model.getWorker()));
            data.put("workerName", Maps.getString(workMap, "EMPLOYEE_NAME"));
            data.put("houseName", Maps.getString(houseMap, "HOUSE_NAME"));
        }
        return getPageInfo(list);
    }

}
