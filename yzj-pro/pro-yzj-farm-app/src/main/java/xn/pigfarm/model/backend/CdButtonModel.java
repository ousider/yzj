package xn.pigfarm.model.backend;

import xn.core.model.BaseModel;

public class CdButtonModel extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1669864215991943026L;
	//菜单ID
    private long moduleId;

	//页面ID
    private long pageId;

	//类型
	private String btnType;  
	//按钮编码
	private String btnCode;  
	//名称
	private String btnName;  
	//图标
	private String iconCls; 
	//方法名
	private String funName;
	
    public long getModuleId() {
		return moduleId;
	}

    public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

    public long getPageId() {
		return pageId;
	}

    public void setPageId(long pageId) {
		this.pageId = pageId;
	}
	public String getBtnType() {
		return btnType;
	}
	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}
	public String getBtnCode() {
		return btnCode;
	}
	public void setBtnCode(String btnCode) {
		this.btnCode = btnCode;
	}
	public String getBtnName() {
		return btnName;
	}
	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}  

}
