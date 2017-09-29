package xn.core.model;

import java.io.Serializable;

/**
 * Created Eclipse Java EE. User:li.zhou Date:2016-3-30 Time:13:33:16
 */
public class BaseModel implements Serializable {


    private static final Long serialVersionUID = 80825613297008620L;

    // 行号: 系统保留字段，标识一条数据记录。
    private Long rowId;

    // 备注
	private String notes;

    // 排序号
    private Long sortNbr;

    // 状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
	private String status;

    // 记录删除标志: [0]-未删除;[1]-逻辑删除
	private String deletedFlag;

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
	private String originFlag = "S";

    // 数据来源应用的代码
	private String originApp = "XN1.0";

    // 行号
    private Long lineNumber = null;

    // 公司ID
    private Long companyId;

    // 猪场ID
    private Long farmId;

    public void setRowId(Long value) {
		this.rowId = value;
	}

    public Long getRowId() {
		return this.rowId;
	}

	public void setNotes(String value) {
		this.notes = value;
	}

	public String getNotes() {
		return this.notes;
	}

    public void setSortNbr(Long value) {
		this.sortNbr = value;
	}

    public Long getSortNbr() {
		return this.sortNbr;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	public String getStatus() {
		return this.status;
	}

	public void setDeletedFlag(String value) {
		this.deletedFlag = value;
	}

	public String getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setOriginFlag(String value) {
		this.originFlag = value;
	}

	public String getOriginFlag() {
		return this.originFlag;
	}

	public void setOriginApp(String value) {
		this.originApp = value;
	}

	public String getOriginApp() {
		return this.originApp;
	}

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

}
