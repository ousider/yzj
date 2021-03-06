package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:15
 *       表：CD_O_VACCINE
 */
public class VaccineModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -7642305072873793894L;

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //materialId 
    private static final String D_MaterialId="materialId";

    // 疫苗种类
    private static final String D_VaccineType="vaccineType";

    // 主要成分与含量
    private static final String D_MainContent="mainContent";

    // 性状
    private static final String D_Apperance="apperance";

    // 作用与用途
    private static final String D_Effect="effect";

    // 用法与用量
    private static final String D_UsageDosage="usageDosage";

    // 不良反应
    private static final String D_AdverseReactions="adverseReactions";

    // 注意事项
    private static final String D_Notice="notice";
	

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
     * 获取行号: 系统保留字段，标识一条数据记录。
     * 
     * @return ROW_ID
     */
    public Long getRowId() {
        return getLong(D_RowId);
    }

    /**
     * 设置排序号
     * 
     * @param SORT_NBR
     */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
     * 获取排序号
     * 
     * @return SORT_NBR
     */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
     * 设置表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置[0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取[0]-未删除;[1]-逻辑删除
     * 
     * @return DELETED_FLAG
     */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
     * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @param ORIGIN_FLAG
     */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
    /**
     * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
     * 
     * @return ORIGIN_FLAG
     */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
     * 设置数据来源应用的代码
     * 
     * @param ORIGIN_APP
     */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
    /**
     * 获取数据来源应用的代码
     * 
     * @return ORIGIN_APP
     */
    public String getOriginApp() {
        return getString(D_OriginApp);
    }

	/**
     * 设置备注
     * 
     * @param NOTES
     */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
    /**
     * 获取备注
     * 
     * @return NOTES
     */
    public String getNotes() {
        return getString(D_Notes);
    }

    /**
     * 设置对应的公司ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取对应的公司ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置companyId
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取companyId
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置materialId
     * 
     * @param MATERIAL_ID
     */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
     * 获取materialId
     * 
     * @return MATERIAL_ID
     */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

	/**
     * 设置疫苗种类
     * 
     * @param VACCINE_TYPE
     */
	public void setVaccineType(String value) {
        set(D_VaccineType,value);
    }
	
    /**
     * 获取疫苗种类
     * 
     * @return VACCINE_TYPE
     */
    public String getVaccineType() {
        return getString(D_VaccineType);
    }

	/**
     * 设置主要成分与含量
     * 
     * @param MAIN_CONTENT
     */
	public void setMainContent(String value) {
        set(D_MainContent,value);
    }
	
    /**
     * 获取主要成分与含量
     * 
     * @return MAIN_CONTENT
     */
    public String getMainContent() {
        return getString(D_MainContent);
    }

	/**
     * 设置性状
     * 
     * @param APPERANCE
     */
	public void setApperance(String value) {
        set(D_Apperance,value);
    }
	
    /**
     * 获取性状
     * 
     * @return APPERANCE
     */
    public String getApperance() {
        return getString(D_Apperance);
    }

	/**
     * 设置作用与用途
     * 
     * @param EFFECT
     */
	public void setEffect(String value) {
        set(D_Effect,value);
    }
	
    /**
     * 获取作用与用途
     * 
     * @return EFFECT
     */
    public String getEffect() {
        return getString(D_Effect);
    }

	/**
     * 设置用法与用量
     * 
     * @param USAGE_DOSAGE
     */
	public void setUsageDosage(String value) {
        set(D_UsageDosage,value);
    }
	
    /**
     * 获取用法与用量
     * 
     * @return USAGE_DOSAGE
     */
    public String getUsageDosage() {
        return getString(D_UsageDosage);
    }

	/**
     * 设置不良反应
     * 
     * @param ADVERSE_REACTIONS
     */
	public void setAdverseReactions(String value) {
        set(D_AdverseReactions,value);
    }
	
    /**
     * 获取不良反应
     * 
     * @return ADVERSE_REACTIONS
     */
    public String getAdverseReactions() {
        return getString(D_AdverseReactions);
    }

	/**
     * 设置注意事项
     * 
     * @param NOTICE
     */
	public void setNotice(String value) {
        set(D_Notice,value);
    }
	
    /**
     * 获取注意事项
     * 
     * @return NOTICE
     */
    public String getNotice() {
        return getString(D_Notice);
    }
	
	
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_SortNbr);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_OriginFlag);
	        setPropertes(D_OriginApp);
	        setPropertes(D_Notes);
	        setPropertes(D_FarmId);
	        setPropertes(D_CompanyId);
	        setPropertes(D_MaterialId);
	        setPropertes(D_VaccineType);
	        setPropertes(D_MainContent);
	        setPropertes(D_Apperance);
	        setPropertes(D_Effect);
	        setPropertes(D_UsageDosage);
	        setPropertes(D_AdverseReactions);
	        setPropertes(D_Notice);
	    }
	    return super.getPropertes();
	}

}




