package xn.pigfarm.timedtask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.data.SqlCon;
import xn.core.util.SpringContextUtil;
import xn.pigfarm.mapper.supplychian.PurchaseMapper;
import xn.pigfarm.model.supplychian.PurchaseModel;
import xn.pigfarm.util.constants.SupplyChianConstants;

/**
 * @Description: 定时任务：每周一执行，完结上个星期的所有饲料订单
 * @author THL
 * @date 2016年6月14日 上午9:39:54
 */
public class OverEveryWeekFeedPurchaseBillTask implements Job {

    private static Logger log = Logger.getLogger(OverEveryWeekFeedPurchaseBillTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行OverEveryWeekFeedPurchaseBillTask...........");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // 周日
        String endDate = df.format(calendar.getTime());

        calendar.add(Calendar.WEEK_OF_YEAR, -1);//
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // 周一
        String startDate = df.format(calendar.getTime());

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT T1.ROW_ID FROM SC_M_BILL_PURCHASE T1");
        sqlCon.addMainSql(" INNER JOIN SC_M_BILL_PURCHASE_DETAIL T2");
        sqlCon.addMainSql(" ON (T2.DELETED_FLAG = '0' AND T2.STATUS = '1' AND T1.ROW_ID = T2.PURCHASE_ID)");
        sqlCon.addMainSql(" WHERE T1.DELETED_FLAG = '0'");
        sqlCon.addMainSql(" AND T1.GROUP_OR_SELF = '1'");
        sqlCon.addMainSql(" AND T1.EVENT_CODE = 'FEED_PURCHASE' AND T1.SUPPLIER_ID = 3");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_PASS, " AND ( T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_INPUTING, " OR T1.STATUS = ?");
        sqlCon.addCondition(SupplyChianConstants.PURCHASE_STORE_STATUS_COMPLETED, " OR T1.STATUS = ? )");
        // sqlCon.addCondition(startDate, " AND T1.BILL_DATE >= ?");
        sqlCon.addCondition(endDate, " AND T1.BILL_DATE <=  ?");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        PurchaseMapper purchaseMapper = SpringContextUtil.getBean("purchaseMapper", PurchaseMapper.class);
        
        List<PurchaseModel> purchaseModelList = purchaseMapper.operSql(sqlMap);
        if (purchaseModelList != null && !purchaseModelList.isEmpty()) {
            for (PurchaseModel purchaseModel : purchaseModelList) {
                purchaseModel.setStatus(SupplyChianConstants.PURCHASE_STORE_STATUS_AUTO_OVER);
            }
            purchaseMapper.updates(purchaseModelList);
        }

        log.info("结束执行OverEveryWeekFeedPurchaseBillTask...........");
    }

}
