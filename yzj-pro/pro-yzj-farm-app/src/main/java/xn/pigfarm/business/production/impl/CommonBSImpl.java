package xn.pigfarm.business.production.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import xn.core.data.SqlCon;
import xn.core.mapper.base.IParamMapper;
import xn.core.service.impl.BaseServiceImpl;
import xn.core.util.data.StringUtil;
import xn.pigfarm.business.production.ICommonBS;
import xn.pigfarm.mapper.production.PigMapper;
import xn.pigfarm.model.production.PigModel;
import xn.pigfarm.util.constants.PigConstants;

@Component("commonBS")
public class CommonBSImpl extends BaseServiceImpl implements ICommonBS {

    @Autowired
    private IParamMapper paramMapper;

    @Autowired
    private PigMapper pigMapper;


    @Override
    public List<Map<String, Object>> getPigBaseInfo(List<Long> pigIds, String tableName) {
        SqlCon pigInfoSql = new SqlCon();
        pigInfoSql.addMainSql("SELECT P.ROW_ID pigId,P.COMPANY_ID companyId,P.FARM_ID farmId,P.SWINERY_ID swineryId,P.PIG_TYPE pigType,P.SEX sex,");
        pigInfoSql.addMainSql(" P.LINE_ID lineId,P.HOUSE_ID houseId,P.PIGPEN_ID pigpenId,P.PIG_CLASS pigClass,P.PARITY parity,");
        pigInfoSql.addMainSql(" MAX(H.ROW_ID) lastTableId,IFNULL(MAX(H.SORT_NBR),0)+1 lastTableSortNbr,MAX(B.PRO_NO) proNo,");
        pigInfoSql.addMainSql(" IFNULL(MAX(E.SORT_NBR),0)+1 hisSortNbr");
        pigInfoSql.addMainSql(" FROM PP_L_PIG P");
        pigInfoSql.addMainSql(" LEFT JOIN " + tableName + " H ON P.ROW_ID = H.PIG_ID AND H.STATUS = 1 AND H.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" LEFT JOIN PP_L_BILL_BREED B ON P.ROW_ID = B.PIG_ID AND P.PARITY = B.PARITY AND B.STATUS = 1 AND B.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" LEFT JOIN PP_L_PIG_EVENT_HIS E ON P.ROW_ID = E.PIG_ID AND E.STATUS = 1 AND E.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" WHERE P.STATUS = 1 AND P.DELETED_FLAG = 0");
        pigInfoSql.addCondition(StringUtil.listLongToString(pigIds), " AND P.ROW_ID IN ?", false, true);
        pigInfoSql.addMainSql(" GROUP BY P.ROW_ID ORDER BY FIELD(P.ROW_ID," + StringUtil.listLongToString(pigIds) + ")");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", pigInfoSql.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public List<Map<String, Object>> getChildBaseInfo(List<Long> pigIds, String tableName) {
        SqlCon pigInfoSql = new SqlCon();
        pigInfoSql.addMainSql("SELECT P.ROW_ID pigId,P.COMPANY_ID companyId,P.FARM_ID farmId,P.SWINERY_ID swineryId,P.PIG_TYPE pigType,P.SEX sex,");
        pigInfoSql.addMainSql(" P.LINE_ID lineId,P.HOUSE_ID houseId,P.PIGPEN_ID pigpenId,P.PIG_CLASS pigClass,");
        pigInfoSql.addMainSql(" P.PARITY parity,P.BOARD_SOW_ID boardSowId,C.EAR_BRAND earBrand,");
        pigInfoSql.addMainSql(" MAX(H.ROW_ID) lastTableId,IFNULL(MAX(H.SORT_NBR),0)+1 lastTableSortNbr,MAX(B.PRO_NO) proNo,");
        pigInfoSql.addMainSql(" IFNULL(MAX(E.SORT_NBR),0)+1 hisSortNbr");
        pigInfoSql.addMainSql(" FROM PP_L_PIG P");
        pigInfoSql.addMainSql(" INNER JOIN PP_L_EAR_CODE C ON P.EAR_CODE_ID = C.ROW_ID AND C.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" LEFT JOIN " + tableName + " H ON P.ROW_ID = H.PIG_ID AND H.STATUS = 1 AND H.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" LEFT JOIN PP_L_BILL_BREED B ON P.ROW_ID = B.PIG_ID AND P.PARITY = B.PARITY AND B.STATUS = 1 AND B.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" LEFT JOIN PP_L_PIG_EVENT_HIS E ON P.ROW_ID = E.PIG_ID AND E.STATUS = 1 AND E.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" WHERE P.STATUS = 1 AND P.DELETED_FLAG = 0");
        pigInfoSql.addMainSql(" AND P.PIG_CLASS IN(" + PigConstants.PIG_CLASS_BRJZ + "," + PigConstants.PIG_CLASS_BRRZ + ")");
        pigInfoSql.addCondition(StringUtil.listLongToString(pigIds), " AND P.BOARD_SOW_ID IN ?", false, true);
        pigInfoSql.addMainSql(" GROUP BY P.ROW_ID");

        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("sql", pigInfoSql.getCondition());

        return paramMapper.getObjectInfos(sqlMap);
    }

    @Override
    public void updatePigs(List<PigModel> pigModelList) {
        pigMapper.updates(pigModelList);
    }

    @Override
    public List<PigModel> createPigModelList(List<? extends Object> viewList) throws Exception {

        List<PigModel> pigModelList = new ArrayList<PigModel>();
        for (Object view : viewList) {
            Class viewClass = view.getClass();
            Method getMethod = viewClass.getMethod("getPigId");// 获得对应get方法名
            Object value = getMethod.invoke(view);
            PigModel pigModel = new PigModel();
            pigModel.setRowId((Long) value);
            pigModelList.add(pigModel);
        }
        return pigModelList;
    }
}
