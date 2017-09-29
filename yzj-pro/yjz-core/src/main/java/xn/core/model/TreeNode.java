package xn.core.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private long cid;
	private String cname;
	private long pid;
	private List<?> children = new ArrayList<>();
	private List<?> hrDept = new ArrayList<>();
	private String type = "公司";
	private long deptCId;

	private long deptPId;

	private int groupDept;
	
	private long farmId;

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public List<?> getChildren() {
		return children;
	}

	public void setChildren(List<?> children) {
		this.children = children;
	}

	public List<?> getHrDept() {
		return hrDept;
	}

	public void setHrDept(List<?> hrDept) {
		this.hrDept = hrDept;
	}

	public long getDeptCId() {
		return deptCId;
	}

	public void setDeptCId(long deptCId) {
		this.deptCId = deptCId;
	}

	public long getDeptPId() {
		return deptPId;
	}

	public void setDeptPId(long deptPId) {
		this.deptPId = deptPId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getGroupDept() {
		return groupDept;
	}

	public void setGroupDept(int groupDept) {
		this.groupDept = groupDept;
	}

	public long getFarmId() {
		return farmId;
	}

	public void setFarmId(long farmId) {
		this.farmId = farmId;
	}
	
}
