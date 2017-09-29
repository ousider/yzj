/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package xn.pigfarm.model.backend;

import xn.core.model.BaseModel;

/**
 * 表：CdCodeList
 */
//@Table(name = "cd_o_module")
public class CdCodeListModel extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 分类ID
	 */
    private long typeId;

    /**
     * 分类代码
     */
    private String typeCode;

    /**
     * 值
     */
	private String codeValue;
	/**
	 * 名称
	 */
	private String codeName;
	/**
	 * 关联值
	 */
	private String linkValue;
	/**
	 * 是否默认值: Y 默认值，N 非默认值，一类代码最多仅有一个为''Y''。
	 */
	private String isDefault;
	
	/**
	 * 所属类型
	 */

    public long getTypeId() {
		return typeId;
	}

    public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getLinkValue() {
		return linkValue;
	}

	public void setLinkValue(String linkValue) {
		this.linkValue = linkValue;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
