package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 11:51:32
 *       表：PP_L_EAR_CODE
 */
public class EarCodeModel  extends BaseDataModel implements Serializable{
  
    private static final long serialVersionUID = 7705142645327039143L;

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

    // 编号
    private static final String D_BusinessCode="businessCode";

    // 耳牌号
    private static final String D_EarBrand="earBrand";

    // 耳缺号
    private static final String D_EarShort="earShort";

    // 耳刺号
    private static final String D_EarThorn="earThorn";

    // 电子耳号
    private static final String D_ElectronicEarNo="electronicEarNo";

    // 父亲耳号
    private static final String D_FatherEarId="fatherEarId";

    // 母亲耳号
    private static final String D_MotherEarId="motherEarId";
	

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
     * 设置编号
     * 
     * @param BUSINESS_CODE
     */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
    /**
     * 获取编号
     * 
     * @return BUSINESS_CODE
     */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

	/**
     * 设置耳牌号
     * 
     * @param EAR_BRAND
     */
	public void setEarBrand(String value) {
        set(D_EarBrand,value);
    }
	
    /**
     * 获取耳牌号
     * 
     * @return EAR_BRAND
     */
    public String getEarBrand() {
        return getString(D_EarBrand);
    }

	/**
     * 设置耳缺号
     * 
     * @param EAR_SHORT
     */
	public void setEarShort(String value) {
        set(D_EarShort,value);
    }
	
    /**
     * 获取耳缺号
     * 
     * @return EAR_SHORT
     */
    public String getEarShort() {
        return getString(D_EarShort);
    }

	/**
     * 设置耳刺号
     * 
     * @param EAR_THORN
     */
	public void setEarThorn(String value) {
        set(D_EarThorn,value);
    }
	
    /**
     * 获取耳刺号
     * 
     * @return EAR_THORN
     */
    public String getEarThorn() {
        return getString(D_EarThorn);
    }

	/**
     * 设置电子耳号
     * 
     * @param ELECTRONIC_EAR_NO
     */
	public void setElectronicEarNo(String value) {
        set(D_ElectronicEarNo,value);
    }
	
    /**
     * 获取电子耳号
     * 
     * @return ELECTRONIC_EAR_NO
     */
    public String getElectronicEarNo() {
        return getString(D_ElectronicEarNo);
    }

    /**
     * 设置父亲耳号
     * 
     * @param FATHER_EAR_ID
     */
	public void setFatherEarId(Long value) {
        set(D_FatherEarId,value);
    }
	
	/**
     * 获取父亲耳号
     * 
     * @return FATHER_EAR_ID
     */
    public Long getFatherEarId() {
        return getLong(D_FatherEarId);
    }

    /**
     * 设置母亲耳号
     * 
     * @param MOTHER_EAR_ID
     */
	public void setMotherEarId(Long value) {
        set(D_MotherEarId,value);
    }
	
	/**
     * 获取母亲耳号
     * 
     * @return MOTHER_EAR_ID
     */
    public Long getMotherEarId() {
        return getLong(D_MotherEarId);
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
	        setPropertes(D_BusinessCode);
	        setPropertes(D_EarBrand);
	        setPropertes(D_EarShort);
	        setPropertes(D_EarThorn);
	        setPropertes(D_ElectronicEarNo);
	        setPropertes(D_FatherEarId);
	        setPropertes(D_MotherEarId);
	    }
	    return super.getPropertes();
	}

}




