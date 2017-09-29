package xn.pigfarm.service.hana;

import java.util.Map;

import xn.core.util.page.BasePageInfo;

public interface ISendToSAPProductionService {
    /**
     * @Description: 上传SAP
     * @author chengb
     * @param billId
     * @return
     * @throws Exception
     */
    public Object insertSAPDateBase(Long code) throws Exception;

    /**
     * @Description: 查询上传信息及限制状态
     * @author 程彬
     * @return
     */
    public BasePageInfo searchSendSAPHana();

    /**
     * @Description: 新增上传SAP记录
     * @author chengb
     * @param name
     * @param billId
     * @return
     * @throws Exception
     */
    public void editInsertSendSap(String json, Boolean isSummery) throws Exception;

    /**
     * @Description: 修改上传sap财务猪只数据限制
     * @author 程彬
     */
    public int editSendSAPHanaLimit();

    /**
     * @Description:查询汇总数据
     * @author 程彬
     * @param code
     * @return
     * @throws Exception
     */
    public Object editToSapSummaryDetail(Long code) throws Exception;

    /**
     * @Description: 判断是否可以查询上传至SAP财务系统
     * @author 程彬
     * @param endDate
     * @throws Exception
     */
    void searchHanaLogSign(String endDate) throws Exception;

    /**
     * @Description: 获取上传SAP财务系统数据的起止日期
     * @author 程彬
     * @param endDate
     * @throws Exception
     */
    Map<String, String> getStartAndEndDate() throws Exception;

    /**
     * @Description: 查询上传sap记录明细
     * @author CB
     * @param rowId
     * @return
     * @throws Exception
     */
    public Object searchSendToHanaLogtDetail(Integer tableCode, Long rowId);

}
