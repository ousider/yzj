package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-30 15:07:29
 *       表：CD_O_SOW
 */
public class SowModel  extends BaseDataModel implements Serializable{
    private static final long serialVersionUID =1L;
    
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

    // 初情日龄
    private static final String D_PubertyDayAge="pubertyDayAge";

    // 初情体重
    private static final String D_PubertyWeight="pubertyWeight";

    // 初配日龄
    private static final String D_SexDayAge="sexDayAge";

    // 初配体重
    private static final String D_SexWeight="sexWeight";

    // 利用年限
    private static final String D_UseYear="useYear";

    // 利用胎次
    private static final String D_UseParity="useParity";

    // 发情周期
    private static final String D_HeatCycle="heatCycle";

    // 配种后转产房(天)
    private static final String D_ChangeLabor="changeLabor";

    // 配种后转产房区间(±误差)
    private static final String D_ErrorLabor="errorLabor";

    // 妊娠天数
    private static final String D_PregnancyDays="pregnancyDays";

    // 误差
    private static final String D_ErrorLimit="errorLimit";

    // 妊检天数
    private static final String D_PregnancyCheckDays="pregnancyCheckDays";

    // 哺乳天数
    private static final String D_LacationDays="lacationDays";

    // 断奶后发情(天)
    private static final String D_DnhfqDays="dnhfqDays";

    // 年生产力(头数)
    private static final String D_YearBabys="yearBabys";
	

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
     * 设置初情日龄
     * 
     * @param PUBERTY_DAY_AGE
     */
	public void setPubertyDayAge(Long value) {
        set(D_PubertyDayAge,value);
    }
	
	/**
     * 获取初情日龄
     * 
     * @return PUBERTY_DAY_AGE
     */
    public Long getPubertyDayAge() {
        return getLong(D_PubertyDayAge);
    }

	/**
     * 设置初情体重
     * 
     * @param PUBERTY_WEIGHT
     */
	public void setPubertyWeight(Double value) {
        set(D_PubertyWeight,value);
    }
	
    /**
     * 获取初情体重
     * 
     * @return PUBERTY_WEIGHT
     */
    public Double getPubertyWeight() {
        return getDouble(D_PubertyWeight);
    }

    /**
     * 设置初配日龄
     * 
     * @param SEX_DAY_AGE
     */
	public void setSexDayAge(Long value) {
        set(D_SexDayAge,value);
    }
	
	/**
     * 获取初配日龄
     * 
     * @return SEX_DAY_AGE
     */
    public Long getSexDayAge() {
        return getLong(D_SexDayAge);
    }

	/**
     * 设置初配体重
     * 
     * @param SEX_WEIGHT
     */
	public void setSexWeight(Double value) {
        set(D_SexWeight,value);
    }
	
    /**
     * 获取初配体重
     * 
     * @return SEX_WEIGHT
     */
    public Double getSexWeight() {
        return getDouble(D_SexWeight);
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
     * 设置利用胎次
     * 
     * @param USE_PARITY
     */
	public void setUseParity(Long value) {
        set(D_UseParity,value);
    }
	
	/**
     * 获取利用胎次
     * 
     * @return USE_PARITY
     */
    public Long getUseParity() {
        return getLong(D_UseParity);
    }

    /**
     * 设置发情周期
     * 
     * @param HEAT_CYCLE
     */
	public void setHeatCycle(Long value) {
        set(D_HeatCycle,value);
    }
	
	/**
     * 获取发情周期
     * 
     * @return HEAT_CYCLE
     */
    public Long getHeatCycle() {
        return getLong(D_HeatCycle);
    }

    /**
     * 设置配种后转产房(天)
     * 
     * @param CHANGE_LABOR
     */
	public void setChangeLabor(Long value) {
        set(D_ChangeLabor,value);
    }
	
	/**
     * 获取配种后转产房(天)
     * 
     * @return CHANGE_LABOR
     */
    public Long getChangeLabor() {
        return getLong(D_ChangeLabor);
    }

    /**
     * 设置配种后转产房区间(±误差)
     * 
     * @param ERROR_LABOR
     */
	public void setErrorLabor(Long value) {
        set(D_ErrorLabor,value);
    }
	
	/**
     * 获取配种后转产房区间(±误差)
     * 
     * @return ERROR_LABOR
     */
    public Long getErrorLabor() {
        return getLong(D_ErrorLabor);
    }

    /**
     * 设置妊娠天数
     * 
     * @param PREGNANCY_DAYS
     */
	public void setPregnancyDays(Long value) {
        set(D_PregnancyDays,value);
    }
	
	/**
     * 获取妊娠天数
     * 
     * @return PREGNANCY_DAYS
     */
    public Long getPregnancyDays() {
        return getLong(D_PregnancyDays);
    }

    /**
     * 设置误差
     * 
     * @param ERROR_LIMIT
     */
	public void setErrorLimit(Long value) {
        set(D_ErrorLimit,value);
    }
	
	/**
     * 获取误差
     * 
     * @return ERROR_LIMIT
     */
    public Long getErrorLimit() {
        return getLong(D_ErrorLimit);
    }

    /**
     * 设置妊检天数
     * 
     * @param PREGNANCY_CHECK_DAYS
     */
	public void setPregnancyCheckDays(Long value) {
        set(D_PregnancyCheckDays,value);
    }
	
	/**
     * 获取妊检天数
     * 
     * @return PREGNANCY_CHECK_DAYS
     */
    public Long getPregnancyCheckDays() {
        return getLong(D_PregnancyCheckDays);
    }

    /**
     * 设置哺乳天数
     * 
     * @param LACATION_DAYS
     */
	public void setLacationDays(Long value) {
        set(D_LacationDays,value);
    }
	
	/**
     * 获取哺乳天数
     * 
     * @return LACATION_DAYS
     */
    public Long getLacationDays() {
        return getLong(D_LacationDays);
    }

    /**
     * 设置断奶后发情(天)
     * 
     * @param DNHFQ_DAYS
     */
	public void setDnhfqDays(Long value) {
        set(D_DnhfqDays,value);
    }
	
	/**
     * 获取断奶后发情(天)
     * 
     * @return DNHFQ_DAYS
     */
    public Long getDnhfqDays() {
        return getLong(D_DnhfqDays);
    }

    /**
     * 设置年生产力(头数)
     * 
     * @param YEAR_BABYS
     */
	public void setYearBabys(Long value) {
        set(D_YearBabys,value);
    }
	
	/**
     * 获取年生产力(头数)
     * 
     * @return YEAR_BABYS
     */
    public Long getYearBabys() {
        return getLong(D_YearBabys);
    }
	
	
	@Override
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
	        setPropertes(D_PubertyDayAge);
	        setPropertes(D_PubertyWeight);
	        setPropertes(D_SexDayAge);
	        setPropertes(D_SexWeight);
	        setPropertes(D_UseYear);
	        setPropertes(D_UseParity);
	        setPropertes(D_HeatCycle);
	        setPropertes(D_ChangeLabor);
	        setPropertes(D_ErrorLabor);
	        setPropertes(D_PregnancyDays);
	        setPropertes(D_ErrorLimit);
	        setPropertes(D_PregnancyCheckDays);
	        setPropertes(D_LacationDays);
	        setPropertes(D_DnhfqDays);
	        setPropertes(D_YearBabys);
	    }
	    return super.getPropertes();
	}

}




