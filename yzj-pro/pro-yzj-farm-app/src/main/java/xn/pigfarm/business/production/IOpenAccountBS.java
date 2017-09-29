package xn.pigfarm.business.production;

/**
 * @Description: 开新增户BS层接口
 * @author zhangjs
 * @date 2016年8月23日 下午1:36:45
 */
public interface IOpenAccountBS {

    /**
     * @Description:开户方法
     * @author zhangjs
     * @param companyClass
     * @param companyType
     * @param companyCode
     * @param companyId
     * @param farmId
     * @throws Exception
     */
    public void openAccount(String companyClass, String companyType, String companyCode, Long companyId, Long farmId,Long employeeId) throws Exception;

    /**
     * @Description: 创建周次
     * @author Administrator
     * @param farmId
     * @param companyId
     * @throws Exception
     */
    public void createWeek(Long farmId, Long companyId) throws Exception;

    /**
     * @Description: 获取settingValue
     * @author Administrator
     * @param farmId
     * @param settingCode
     * @return
     * @throws Exception
     */
    public String getSettingValueByCode(Long farmId, String settingCode) throws Exception;
}
