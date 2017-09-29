package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:03
 *       表：CD_O_BOAR
 */
public class BoarModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 7480645499989596239L;

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

    // 品种ID
    private static final String D_BreedId="breedId";

    // 品系
    private static final String D_Strain="strain";

    // 体况
    private static final String D_BodyCondition="bodyCondition";

    // 毛色
    private static final String D_Color="color";

    // 右奶头数
    private static final String D_RightTeatNum="rightTeatNum";

    // 左奶头数
    private static final String D_LeftTeatNum="leftTeatNum";

    // 出生体重
    private static final String D_BirthWeight="birthWeight";

    // 入场日龄
    private static final String D_EnterDayAge="enterDayAge";

    // 入场体重
    private static final String D_EnterWeight="enterWeight";

    // 利用年限
    private static final String D_UseYear="useYear";

    // 公母比
    private static final String D_SexRatio="sexRatio";

    // 调教日龄
    private static final String D_TeachDayAge="teachDayAge";

    // 调教区间
    private static final String D_ErrorLimit="errorLimit";

    // 转生产日龄
    private static final String D_ProductionDayAge="productionDayAge";

    // 转生产日龄区间
    private static final String D_DayAgeSection="dayAgeSection";

    // 淘汰日龄
    private static final String D_EliminateDayAge="eliminateDayAge";

    // 总采精次数
    private static final String D_CollectionNum="collectionNum";

    // 总精液量
    private static final String D_SpermQty="spermQty";

    // 预期总仔数
    private static final String D_LitterNum="litterNum";
	

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
     * 设置品种ID
     * 
     * @param BREED_ID
     */
	public void setBreedId(Long value) {
        set(D_BreedId,value);
    }
	
	/**
     * 获取品种ID
     * 
     * @return BREED_ID
     */
    public Long getBreedId() {
        return getLong(D_BreedId);
    }

	/**
     * 设置品系
     * 
     * @param STRAIN
     */
	public void setStrain(String value) {
        set(D_Strain,value);
    }
	
    /**
     * 获取品系
     * 
     * @return STRAIN
     */
    public String getStrain() {
        return getString(D_Strain);
    }

	/**
     * 设置体况
     * 
     * @param BODY_CONDITION
     */
	public void setBodyCondition(String value) {
        set(D_BodyCondition,value);
    }
	
    /**
     * 获取体况
     * 
     * @return BODY_CONDITION
     */
    public String getBodyCondition() {
        return getString(D_BodyCondition);
    }

	/**
     * 设置毛色
     * 
     * @param COLOR
     */
	public void setColor(String value) {
        set(D_Color,value);
    }
	
    /**
     * 获取毛色
     * 
     * @return COLOR
     */
    public String getColor() {
        return getString(D_Color);
    }

    /**
     * 设置右奶头数
     * 
     * @param RIGHT_TEAT_NUM
     */
	public void setRightTeatNum(Long value) {
        set(D_RightTeatNum,value);
    }
	
	/**
     * 获取右奶头数
     * 
     * @return RIGHT_TEAT_NUM
     */
    public Long getRightTeatNum() {
        return getLong(D_RightTeatNum);
    }

    /**
     * 设置左奶头数
     * 
     * @param LEFT_TEAT_NUM
     */
	public void setLeftTeatNum(Long value) {
        set(D_LeftTeatNum,value);
    }
	
	/**
     * 获取左奶头数
     * 
     * @return LEFT_TEAT_NUM
     */
    public Long getLeftTeatNum() {
        return getLong(D_LeftTeatNum);
    }

	/**
     * 设置出生体重
     * 
     * @param BIRTH_WEIGHT
     */
	public void setBirthWeight(Double value) {
        set(D_BirthWeight,value);
    }
	
    /**
     * 获取出生体重
     * 
     * @return BIRTH_WEIGHT
     */
    public Double getBirthWeight() {
        return getDouble(D_BirthWeight);
    }

    /**
     * 设置入场日龄
     * 
     * @param ENTER_DAY_AGE
     */
	public void setEnterDayAge(Long value) {
        set(D_EnterDayAge,value);
    }
	
	/**
     * 获取入场日龄
     * 
     * @return ENTER_DAY_AGE
     */
    public Long getEnterDayAge() {
        return getLong(D_EnterDayAge);
    }

	/**
     * 设置入场体重
     * 
     * @param ENTER_WEIGHT
     */
	public void setEnterWeight(Double value) {
        set(D_EnterWeight,value);
    }
	
    /**
     * 获取入场体重
     * 
     * @return ENTER_WEIGHT
     */
    public Double getEnterWeight() {
        return getDouble(D_EnterWeight);
    }

    /**
     * 设置利用年限
     * 
     * @param USE_YEAR
     */
	public void setUseYear(Long value) {
        set(D_UseYear,value);
    }
	
	/**
     * 获取利用年限
     * 
     * @return USE_YEAR
     */
    public Long getUseYear() {
        return getLong(D_UseYear);
    }

	/**
     * 设置公母比
     * 
     * @param SEX_RATIO
     */
	public void setSexRatio(String value) {
        set(D_SexRatio,value);
    }
	
    /**
     * 获取公母比
     * 
     * @return SEX_RATIO
     */
    public String getSexRatio() {
        return getString(D_SexRatio);
    }

    /**
     * 设置调教日龄
     * 
     * @param TEACH_DAY_AGE
     */
	public void setTeachDayAge(Long value) {
        set(D_TeachDayAge,value);
    }
	
	/**
     * 获取调教日龄
     * 
     * @return TEACH_DAY_AGE
     */
    public Long getTeachDayAge() {
        return getLong(D_TeachDayAge);
    }

    /**
     * 设置调教区间
     * 
     * @param ERROR_LIMIT
     */
	public void setErrorLimit(Long value) {
        set(D_ErrorLimit,value);
    }
	
	/**
     * 获取调教区间
     * 
     * @return ERROR_LIMIT
     */
    public Long getErrorLimit() {
        return getLong(D_ErrorLimit);
    }

    /**
     * 设置转生产日龄
     * 
     * @param PRODUCTION_DAY_AGE
     */
	public void setProductionDayAge(Long value) {
        set(D_ProductionDayAge,value);
    }
	
	/**
     * 获取转生产日龄
     * 
     * @return PRODUCTION_DAY_AGE
     */
    public Long getProductionDayAge() {
        return getLong(D_ProductionDayAge);
    }

    /**
     * 设置转生产日龄区间
     * 
     * @param DAY_AGE_SECTION
     */
	public void setDayAgeSection(Long value) {
        set(D_DayAgeSection,value);
    }
	
	/**
     * 获取转生产日龄区间
     * 
     * @return DAY_AGE_SECTION
     */
    public Long getDayAgeSection() {
        return getLong(D_DayAgeSection);
    }

    /**
     * 设置淘汰日龄
     * 
     * @param ELIMINATE_DAY_AGE
     */
	public void setEliminateDayAge(Long value) {
        set(D_EliminateDayAge,value);
    }
	
	/**
     * 获取淘汰日龄
     * 
     * @return ELIMINATE_DAY_AGE
     */
    public Long getEliminateDayAge() {
        return getLong(D_EliminateDayAge);
    }

    /**
     * 设置总采精次数
     * 
     * @param COLLECTION_NUM
     */
	public void setCollectionNum(Long value) {
        set(D_CollectionNum,value);
    }
	
	/**
     * 获取总采精次数
     * 
     * @return COLLECTION_NUM
     */
    public Long getCollectionNum() {
        return getLong(D_CollectionNum);
    }

	/**
     * 设置总精液量
     * 
     * @param SPERM_QTY
     */
	public void setSpermQty(Double value) {
        set(D_SpermQty,value);
    }
	
    /**
     * 获取总精液量
     * 
     * @return SPERM_QTY
     */
    public Double getSpermQty() {
        return getDouble(D_SpermQty);
    }

    /**
     * 设置预期总仔数
     * 
     * @param LITTER_NUM
     */
	public void setLitterNum(Long value) {
        set(D_LitterNum,value);
    }
	
	/**
     * 获取预期总仔数
     * 
     * @return LITTER_NUM
     */
    public Long getLitterNum() {
        return getLong(D_LitterNum);
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
	        setPropertes(D_BreedId);
	        setPropertes(D_Strain);
	        setPropertes(D_BodyCondition);
	        setPropertes(D_Color);
	        setPropertes(D_RightTeatNum);
	        setPropertes(D_LeftTeatNum);
	        setPropertes(D_BirthWeight);
	        setPropertes(D_EnterDayAge);
	        setPropertes(D_EnterWeight);
	        setPropertes(D_UseYear);
	        setPropertes(D_SexRatio);
	        setPropertes(D_TeachDayAge);
	        setPropertes(D_ErrorLimit);
	        setPropertes(D_ProductionDayAge);
	        setPropertes(D_DayAgeSection);
	        setPropertes(D_EliminateDayAge);
	        setPropertes(D_CollectionNum);
	        setPropertes(D_SpermQty);
	        setPropertes(D_LitterNum);
	    }
	    return super.getPropertes();
	}

}




