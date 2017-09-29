
package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.util.Date;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-4-26
 * Time:15:54:53
 * 表：CdMsettingModule
 */
public class CdSettingModuleModel extends BaseModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7091072633821676369L;
	//公司类别 
    private String farmType;
	//系统模块 
	private String settingModule;
	//分组编号 
	private String groupCode;
	//分组名称 
	private String groupName;
	//设置项名称 
	private String settingName;
	//设置项代码 
	private String settingCode;
	//设置值 ON默认值 
	private String settingValue;
	//显示类型checkbox、text、number、int、lookup、radio 
	private String sowType;
	//说明 
	private String memo;
	//相关ID 
	private long correlationId;
	//创建人 
	private long createId;
	//创建日期 
	private Date createDate;
	//更新人 
	private long updateId;
	//更新日期 
	private Date updateDate;


    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    /**
     * 设置系统模块
     * 
     * @param SETTING_MODULE - 系统模块
     */
	public void setSettingModule(String value) {
		this.settingModule = value;
	}
	/**
	* 获取系统模块
	* @return SETTING_MODULE - 系统模块 
	*/
	public String getSettingModule() {
		return this.settingModule;
	}
	/**
	* 设置分组编号
	* @param GROUP_CODE - 分组编号 
	*/
	public void setGroupCode(String value) {
		this.groupCode = value;
	}
	/**
	* 获取分组编号
	* @return GROUP_CODE - 分组编号 
	*/
	public String getGroupCode() {
		return this.groupCode;
	}
	/**
	* 设置分组名称
	* @param GROUP_NAME - 分组名称 
	*/
	public void setGroupName(String value) {
		this.groupName = value;
	}
	/**
	* 获取分组名称
	* @return GROUP_NAME - 分组名称 
	*/
	public String getGroupName() {
		return this.groupName;
	}
	/**
	* 设置设置项名称
	* @param SETTING_NAME - 设置项名称 
	*/
	public void setSettingName(String value) {
		this.settingName = value;
	}
	/**
	* 获取设置项名称
	* @return SETTING_NAME - 设置项名称 
	*/
	public String getSettingName() {
		return this.settingName;
	}
	/**
	* 设置设置项代码
	* @param SETTING_CODE - 设置项代码 
	*/
	public void setSettingCode(String value) {
		this.settingCode = value;
	}
	/**
	* 获取设置项代码
	* @return SETTING_CODE - 设置项代码 
	*/
	public String getSettingCode() {
		return this.settingCode;
	}
	/**
	* 设置设置值 ON默认值
	* @param SETTING_VALUE - 设置值 ON默认值 
	*/
	public void setSettingValue(String value) {
		this.settingValue = value;
	}
	/**
	* 获取设置值 ON默认值
	* @return SETTING_VALUE - 设置值 ON默认值 
	*/
	public String getSettingValue() {
		return this.settingValue;
	}
	/**
	* 设置显示类型checkbox、text、number、int、lookup、radio
	* @param SOW_TYPE - 显示类型checkbox、text、number、int、lookup、radio 
	*/
	public void setSowType(String value) {
		this.sowType = value;
	}
	/**
	* 获取显示类型checkbox、text、number、int、lookup、radio
	* @return SOW_TYPE - 显示类型checkbox、text、number、int、lookup、radio 
	*/
	public String getSowType() {
		return this.sowType;
	}
	/**
	* 设置说明
	* @param MEMO - 说明 
	*/
	public void setMemo(String value) {
		this.memo = value;
	}
	/**
	* 获取说明
	* @return MEMO - 说明 
	*/
	public String getMemo() {
		return this.memo;
	}
	/**
	* 设置相关ID
	* @param CORRELATION_ID - 相关ID 
	*/
	public void setCorrelationId(long value) {
		this.correlationId = value;
	}
	/**
	* 获取相关ID
	* @return CORRELATION_ID - 相关ID 
	*/
	public long getCorrelationId() {
		return this.correlationId;
	}
	/**
	* 设置创建人
	* @param CREATE_ID - 创建人 
	*/
	public void setCreateId(long value) {
		this.createId = value;
	}
	/**
	* 获取创建人
	* @return CREATE_ID - 创建人 
	*/
	public long getCreateId() {
		return this.createId;
	}
	
	/**
	* 设置创建日期
	* @param CREATE_DATE - 创建日期 
	*/
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	/**
	* 获取创建日期
	* @return CREATE_DATE - 创建日期 
	*/
	public Date getCreateDate() {
		return this.createDate;
	}
	/**
	* 设置更新人
	* @param UPDATE_ID - 更新人 
	*/
	public void setUpdateId(long value) {
		this.updateId = value;
	}
	/**
	* 获取更新人
	* @return UPDATE_ID - 更新人 
	*/
	public long getUpdateId() {
		return this.updateId;
	}
	
	/**
	* 设置更新日期
	* @param UPDATE_DATE - 更新日期 
	*/
	public void setUpdateDate(Date value) {
		this.updateDate = value;
	}
	/**
	* 获取更新日期
	* @return UPDATE_DATE - 更新日期 
	*/
	public Date getUpdateDate() {
		return this.updateDate;
	}


}




