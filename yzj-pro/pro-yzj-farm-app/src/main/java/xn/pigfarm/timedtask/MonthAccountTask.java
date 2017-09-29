package xn.pigfarm.timedtask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import xn.core.data.SqlCon;
import xn.core.util.SpringContextUtil;
import xn.core.util.constants.CommonConstants;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.supplychian.MonthAccountMapper;
import xn.pigfarm.model.supplychian.MonthAccountModel;

/**
 * @Description: 定时任务：每个月的第一天计算出月初的数量
 * @author THL
 * @date 2016年6月14日 上午9:39:54
 */
public class MonthAccountTask implements Job {

    private static Logger log = Logger.getLogger(MonthAccountTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行MonthAccountTask...........");
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // 盘存功能 剔除盘存数量

        SqlCon sqlCon = new SqlCon();
        sqlCon.addMainSql("SELECT A.FARM_ID AS farmId, A.COMPANY_ID AS companyId, A.MATERIAL_ID AS materialId, A.MATERIAL_ONLY AS materialOnly");
        sqlCon.addMainSql(", A.MATERIAL_BATCH AS materialBatch, A.WAREHOUSE_ID AS warehouseId , A.ACTUAL_QTY AS startQty");
        sqlCon.addMainSql(" FROM SC_M_STORE_MATERIAL A");
        sqlCon.addMainSql(" WHERE A.DELETED_FLAG = '0' AND A.ACTUAL_QTY <> 0");
        sqlCon.addMainSql(" AND NOT EXISTS(SELECT 1 FROM SC_M_MONTH_ACCOUNT");
        sqlCon.addMainSql(" WHERE MATERIAL_ID = A.MATERIAL_ID AND MATERIAL_ONLY = A.MATERIAL_ONLY");
        sqlCon.addMainSql(" AND MATERIAL_BATCH = A.MATERIAL_BATCH AND WAREHOUSE_ID = A.WAREHOUSE_ID");
        sqlCon.addCondition(TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT), " AND START_DATE = ?)");

        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlMap.put("sql", sqlCon.getCondition());

        MonthAccountMapper monthAccountMapper = SpringContextUtil.getBean("monthAccountMapper", MonthAccountMapper.class);

        List<MonthAccountModel> monthAccountModelList = monthAccountMapper.operSql(sqlMap);
        if (monthAccountModelList != null && !monthAccountModelList.isEmpty()) {
            for (MonthAccountModel monthAccountModel : monthAccountModelList) {
                monthAccountModel.setDeletedFlag(CommonConstants.DELETED_FLAG);
                monthAccountModel.setLockFlag("N");
                monthAccountModel.setStartDate(calendar.getTime());
                monthAccountModel.setCreateDate(currentDate);
            }
            monthAccountMapper.inserts(monthAccountModelList);
        }

        log.info("结束执行MonthAccountTask...........");
    }

}
