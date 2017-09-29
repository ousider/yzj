
package xn.pigfarm.model.backend;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-5-1
 * Time:9:17:15
 * 表：CdOparts
 */
public class CdPartsModel extends BaseModel implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5727994061734675248L;
	//部件编号 
	private String partsCode;
	//部件名称 
	private String partsName;
	//模块名：能够描述此模块信息的名称。 
	private String moduleName;
	//图标地址 
	private String iconCls;
	//图标字体 
	private String glyph;
	//能否使用 
	private String usingFlag;


	/**
	* 设置部件编号
	* @param PARTS_CODE - 部件编号 
	*/
	public void setPartsCode(String value) {
		this.partsCode = value;
	}
	/**
	* 获取部件编号
	* @return PARTS_CODE - 部件编号 
	*/
	public String getPartsCode() {
		return this.partsCode;
	}
	/**
	* 设置部件名称
	* @param PARTS_NAME - 部件名称 
	*/
	public void setPartsName(String value) {
		this.partsName = value;
	}
	/**
	* 获取部件名称
	* @return PARTS_NAME - 部件名称 
	*/
	public String getPartsName() {
		return this.partsName;
	}
	/**
	* 设置模块名：能够描述此模块信息的名称。
	* @param MODULE_NAME - 模块名：能够描述此模块信息的名称。 
	*/
	public void setModuleName(String value) {
		this.moduleName = value;
	}
	/**
	* 获取模块名：能够描述此模块信息的名称。
	* @return MODULE_NAME - 模块名：能够描述此模块信息的名称。 
	*/
	public String getModuleName() {
		return this.moduleName;
	}
	/**
	* 设置图标地址
	* @param ICON_CLS - 图标地址 
	*/
	public void setIconCls(String value) {
		this.iconCls = value;
	}
	/**
	* 获取图标地址
	* @return ICON_CLS - 图标地址 
	*/
	public String getIconCls() {
		return this.iconCls;
	}
	/**
	* 设置图标字体
	* @param GLYPH - 图标字体 
	*/
	public void setGlyph(String value) {
		this.glyph = value;
	}
	/**
	* 获取图标字体
	* @return GLYPH - 图标字体 
	*/
	public String getGlyph() {
		return this.glyph;
	}
	/**
	* 设置能否使用
	* @param USING_FLAG - 能否使用 
	*/
	public void setUsingFlag(String value) {
		this.usingFlag = value;
	}
	/**
	* 获取能否使用
	* @return USING_FLAG - 能否使用 
	*/
	public String getUsingFlag() {
		return this.usingFlag;
	}
	


}




