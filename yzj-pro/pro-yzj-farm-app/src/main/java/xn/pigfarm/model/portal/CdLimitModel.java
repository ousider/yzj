
package xn.pigfarm.model.portal;

import java.io.Serializable;

import xn.core.model.BaseModel;

/**
 * Created Eclipse Java EE.
 * User:li.zhou
 * Date:2016-4-9
 * Time:14:03:08
 * 表：CdRlimit
 */
public class CdLimitModel extends BaseModel implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -106719245916269926L;
	
	//角色ID 
	private Integer roleId;
	//对象ID 
	private Integer objId;
	//对象类型（菜单、桌面部件、按钮) 
	private String objType;


	/**
	* 设置角色ID
	* @param ROLE_ID - 角色ID 
	*/
	public void setRoleId(Integer value) {
		this.roleId = value;
	}
	/**
	* 获取角色ID
	* @return ROLE_ID - 角色ID 
	*/
	public Integer getRoleId() {
		return this.roleId;
	}
	/**
	* 设置对象ID
	* @param OBJ_ID - 对象ID 
	*/
	public void setObjId(Integer value) {
		this.objId = value;
	}
	/**
	* 获取对象ID
	* @return OBJ_ID - 对象ID 
	*/
	public Integer getObjId() {
		return this.objId;
	}
	/**
	* 设置对象类型（菜单、桌面部件、按钮)
	* @param OBJ_TYPE - 对象类型（菜单、桌面部件、按钮) 
	*/
	public void setObjType(String value) {
		this.objType = value;
	}
	/**
	* 获取对象类型（菜单、桌面部件、按钮)
	* @return OBJ_TYPE - 对象类型（菜单、桌面部件、按钮) 
	*/
	public String getObjType() {
		return this.objType;
	}


}




