/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package xn.pigfarm.model.backend;

import xn.core.model.BaseModel;

/**
 * 表：CdCodeType
 */

public class CdCodeTypeModel extends BaseModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String typeCode;
	/**
	 * ID
	 */
	private String typeName;
	/**
	 * 编号
	 */
	private String supType;
	
    private int listQty;
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSupType() {
		return supType;
	}
	public void setSupType(String supType) {
		this.supType = supType;
	}
    public int getListQty() {
        return listQty;
    }
    public void setListQty(int listQty) {
        this.listQty = listQty;
    }

	
}
