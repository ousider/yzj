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
import xn.core.mapper.base.IParamMapper;
import xn.core.util.SpringContextUtil;
import xn.core.util.time.TimeUtil;
import xn.pigfarm.mapper.production.SpermInfoMapper;

/**
 * @Description: 定时任务：每个月的第一天完结上个月的所有订单，并且生成所有未到货的订单
 * @author THL
 * @date 2016年6月14日 上午9:39:54
 */
public class ScrapSpermInfoTask implements Job {

    private static Logger log = Logger.getLogger(ScrapSpermInfoTask.class);

    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        log.info("开始执行ScrapSpermInfoTask...........");
        Calendar calendar = Calendar.getInstance();

        Date currentDate = calendar.getTime();

        String currentDateString = TimeUtil.format(currentDate, TimeUtil.DATE_FORMAT);

        IParamMapper paramMapper = SpringContextUtil.getBean("IParamMapper", IParamMapper.class);

        SpermInfoMapper spermInfoMapper = SpringContextUtil.getBean("spermInfoMapper", SpermInfoMapper.class);

        SqlCon sqlCon = new SqlCon();
        Map<String, String> sqlMap = new HashMap<String, String>();
        sqlCon.addMainSql(" UPDATE pp_l_sperm_info T SET STATUS = '5' ");
        sqlCon.addMainSql(" WHERE DELETED_FLAG = '0' AND STATUS = '1' AND EXISTS(");
        sqlCon.addMainSql(" SELECT 1");
        sqlCon.addMainSql(" FROM pp_l_bill_semen T1 ");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_sperm_info t2 ON( T1.row_id = T2.SEMEN_ID AND T2.DELETED_FLAG='0')");
        sqlCon.addMainSql(" LEFT JOIN pp_l_pig T3 ON (T1.PIG_ID=T3.ROW_ID AND T3.DELETED_FLAG='0' AND  T3.STATUS='1')");
        // sqlCon.addMainSql(" LEFT JOIN cd_l_breed T4 ON (T3.BREED_ID=T4.ROW_ID AND T4.DELETED_FLAG='0' AND T4.STATUS='1')");
        // sqlCon.addMainSql(" LEFT JOIN pp_l_bill_breed T5 ON (T3.ROW_ID=T5.BREED_BOAR_FIRST AND T5.DELETED_FLAG='0' AND T5.STATUS='1')");
        sqlCon.addMainSql(" LEFT JOIN cd_m_material T6 ON (T3.MATERIAL_ID=T6.ROW_ID AND T6.DELETED_FLAG='0')");
        sqlCon.addMainSql(" LEFT JOIN cd_o_sperm T7 ON (T6.ROW_ID=T7.BOAR_ID AND T7.DELETED_FLAG='0')");
        sqlCon.addMainSql(
                " WHERE  T1.DELETED_FLAG='0' AND T1.ROW_ID=T.SEMEN_ID AND Date(date_add(T1.SEMEN_DATE,INTERVAL T7.SHELF_LIFE DAY)) < CURRENT_DATE");
        sqlCon.addMainSql(" GROUP BY T1.ROW_ID ) ");
        sqlMap.put("sql", sqlCon.getCondition());
        List<Map<String, Object>> list = paramMapper.getObjectInfos(sqlMap);

        log.info("结束执行ScrapSpermInfoTask...........");
    }

}
