

package xn.pigfarm.model.basicinfo;

import xn.core.model.BaseModel;

/**
 * 表：cd_m_material_group
 */
public class CdMaterialGroupModel extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -7510051015523743139L;

    // 公司ID
    private Long companyId;

    // 物料组编码
    private String businessCode;
    
    // 物料组名称
    private String groupName;

    // 上级物料组
    private Long supGroupId;

    // 对应科目
    private Long subjectId;

    // 物料类型
    private String materialType;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getSupGroupId() {
        return supGroupId;
    }

    public void setSupGroupId(Long supGroupId) {
        this.supGroupId = supGroupId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
    
}
