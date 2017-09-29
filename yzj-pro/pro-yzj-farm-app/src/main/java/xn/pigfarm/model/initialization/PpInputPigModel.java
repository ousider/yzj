package xn.pigfarm.model.initialization;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-9-5 11:57:39
 *       表：PP_INPUT_PIG
 */
public class PpInputPigModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = -4412576865088806377L;

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 1:未执行 2:执行中 3:执行成功 4:执行失败
    private static final String D_Status="status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 备注
    private static final String D_Notes="notes";

    // 对应的公司ID
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";

    // 猪舍ID
    private static final String D_HouseId="houseId";

    // 栏号ID
    private static final String D_PigpenId="pigpenId";

    // 猪只ID
    private static final String D_PigId="pigId";
	 //earBrand 
    private static final String D_EarBrand="earBrand";
	 //earShort 
    private static final String D_EarShort="earShort";
	 //materialId 
    private static final String D_MaterialId="materialId";
	 //earThorn 
    private static final String D_EarThorn="earThorn";
	 //breedId 
    private static final String D_BreedId="breedId";
	 //pigType 
    private static final String D_PigType="pigType";
	 //sex 
    private static final String D_Sex="sex";
	 //strain 
    private static final String D_Strain="strain";
	 //pigClass 
    private static final String D_PigClass="pigClass";
	 //parity 
    private static final String D_Parity="parity";

    // 负责人
    private static final String D_Worker="worker";
	 //birthDate 
    private static final String D_BirthDate="birthDate";
	 //rightTeatNum 
    private static final String D_RightTeatNum="rightTeatNum";
	 //leftTeatNum 
    private static final String D_LeftTeatNum="leftTeatNum";
	 //supplierId 
    private static final String D_SupplierId="supplierId";
	 //productDate 
    private static final String D_ProductDate="productDate";
	 //enterDate 
    private static final String D_EnterDate="enterDate";
	 //onPrice 
    private static final String D_OnPrice="onPrice";

    // 配种公猪耳号
    private static final String D_BreedBoarName="breedBoarName";
	 //breedDate 
    private static final String D_BreedDate="breedDate";
	 //breedBoar 
    private static final String D_BreedBoar="breedBoar";
	 //pregnancyDate 
    private static final String D_PregnancyDate="pregnancyDate";
	 //pregnancyResult 
    private static final String D_PregnancyResult="pregnancyResult";
	 //changeHouseDate 
    private static final String D_ChangeHouseDate="changeHouseDate";
	 //changeHouseId 
    private static final String D_ChangeHouseId="changeHouseId";
    //changePigpenId 
    private static final String D_ChangePigpenId="changePigpenId";
	 //deliveryDate 
    private static final String D_DeliveryDate="deliveryDate";
	 //aliveLitterWeight 
    private static final String D_AliveLitterWeight="aliveLitterWeight";
	 //healthyNum 
    private static final String D_HealthyNum="healthyNum";
	 //weakNum 
    private static final String D_WeakNum="weakNum";
	 //stillbirthNum 
    private static final String D_StillbirthNum="stillbirthNum";
	 //mummyNum 
    private static final String D_MummyNum="mummyNum";
	 //mutantNum 
    private static final String D_MutantNum="mutantNum";
	 //weanDate 
    private static final String D_WeanDate="weanDate";
	 //weanNum 
    private static final String D_WeanNum="weanNum";
	 //weanWeight 
    private static final String D_WeanWeight="weanWeight";

    // 创建人
    private static final String D_CreateId="createId";

    // 创建日期
    private static final String D_CreateDate="createDate";
	

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
     * 设置1:未执行 2:执行中 3:执行成功 4:执行失败
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取1:未执行 2:执行中 3:执行成功 4:执行失败
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
     * 设置猪舍ID
     * 
     * @param HOUSE_ID
     */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
     * 获取猪舍ID
     * 
     * @return HOUSE_ID
     */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

    /**
     * 设置栏号ID
     * 
     * @param PIGPEN_ID
     */
	public void setPigpenId(Long value) {
        set(D_PigpenId,value);
    }
	
	/**
     * 获取栏号ID
     * 
     * @return PIGPEN_ID
     */
    public Long getPigpenId() {
        return getLong(D_PigpenId);
    }

    /**
     * 设置猪只ID
     * 
     * @param PIG_ID
     */
	public void setPigId(Long value) {
        set(D_PigId,value);
    }
	
	/**
     * 获取猪只ID
     * 
     * @return PIG_ID
     */
    public Long getPigId() {
        return getLong(D_PigId);
    }

	/**
     * 设置earBrand
     * 
     * @param EAR_BRAND
     */
	public void setEarBrand(String value) {
        set(D_EarBrand,value);
    }
	
    /**
     * 获取earBrand
     * 
     * @return EAR_BRAND
     */
    public String getEarBrand() {
        return getString(D_EarBrand);
    }

	/**
     * 设置earShort
     * 
     * @param EAR_SHORT
     */
	public void setEarShort(String value) {
        set(D_EarShort,value);
    }
	
    /**
     * 获取earShort
     * 
     * @return EAR_SHORT
     */
    public String getEarShort() {
        return getString(D_EarShort);
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
     * 设置earThorn
     * 
     * @param EAR_THORN
     */
	public void setEarThorn(String value) {
        set(D_EarThorn,value);
    }
	
    /**
     * 获取earThorn
     * 
     * @return EAR_THORN
     */
    public String getEarThorn() {
        return getString(D_EarThorn);
    }

    /**
     * 设置breedId
     * 
     * @param BREED_ID
     */
	public void setBreedId(Long value) {
        set(D_BreedId,value);
    }
	
	/**
     * 获取breedId
     * 
     * @return BREED_ID
     */
    public Long getBreedId() {
        return getLong(D_BreedId);
    }

	/**
     * 设置pigType
     * 
     * @param PIG_TYPE
     */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
    /**
     * 获取pigType
     * 
     * @return PIG_TYPE
     */
    public String getPigType() {
        return getString(D_PigType);
    }

	/**
     * 设置sex
     * 
     * @param SEX
     */
	public void setSex(String value) {
        set(D_Sex,value);
    }
	
    /**
     * 获取sex
     * 
     * @return SEX
     */
    public String getSex() {
        return getString(D_Sex);
    }

	/**
     * 设置strain
     * 
     * @param STRAIN
     */
	public void setStrain(String value) {
        set(D_Strain,value);
    }
	
    /**
     * 获取strain
     * 
     * @return STRAIN
     */
    public String getStrain() {
        return getString(D_Strain);
    }

    /**
     * 设置pigClass
     * 
     * @param PIG_CLASS
     */
	public void setPigClass(Long value) {
        set(D_PigClass,value);
    }
	
	/**
     * 获取pigClass
     * 
     * @return PIG_CLASS
     */
    public Long getPigClass() {
        return getLong(D_PigClass);
    }

    /**
     * 设置parity
     * 
     * @param PARITY
     */
	public void setParity(Long value) {
        set(D_Parity,value);
    }
	
	/**
     * 获取parity
     * 
     * @return PARITY
     */
    public Long getParity() {
        return getLong(D_Parity);
    }

    /**
     * 设置负责人
     * 
     * @param WORKER
     */
	public void setWorker(Long value) {
        set(D_Worker,value);
    }
	
	/**
     * 获取负责人
     * 
     * @return WORKER
     */
    public Long getWorker() {
        return getLong(D_Worker);
    }

	/**
     * 设置birthDate
     * 
     * @param BIRTH_DATE
     */
	public void setBirthDate(Date value) {
        set(D_BirthDate,value);
    }
	
    /**
     * 获取birthDate
     * 
     * @return BIRTH_DATE
     */
    public Date getBirthDate() {
        return getDate(D_BirthDate);
    }

    /**
     * 设置rightTeatNum
     * 
     * @param RIGHT_TEAT_NUM
     */
	public void setRightTeatNum(Long value) {
        set(D_RightTeatNum,value);
    }
	
	/**
     * 获取rightTeatNum
     * 
     * @return RIGHT_TEAT_NUM
     */
    public Long getRightTeatNum() {
        return getLong(D_RightTeatNum);
    }

    /**
     * 设置leftTeatNum
     * 
     * @param LEFT_TEAT_NUM
     */
	public void setLeftTeatNum(Long value) {
        set(D_LeftTeatNum,value);
    }
	
	/**
     * 获取leftTeatNum
     * 
     * @return LEFT_TEAT_NUM
     */
    public Long getLeftTeatNum() {
        return getLong(D_LeftTeatNum);
    }

    /**
     * 设置supplierId
     * 
     * @param SUPPLIER_ID
     */
	public void setSupplierId(Long value) {
        set(D_SupplierId,value);
    }
	
	/**
     * 获取supplierId
     * 
     * @return SUPPLIER_ID
     */
    public Long getSupplierId() {
        return getLong(D_SupplierId);
    }

	/**
     * 设置productDate
     * 
     * @param PRODUCT_DATE
     */
	public void setProductDate(Date value) {
        set(D_ProductDate,value);
    }
	
    /**
     * 获取productDate
     * 
     * @return PRODUCT_DATE
     */
    public Date getProductDate() {
        return getDate(D_ProductDate);
    }

	/**
     * 设置enterDate
     * 
     * @param ENTER_DATE
     */
	public void setEnterDate(Date value) {
        set(D_EnterDate,value);
    }
	
    /**
     * 获取enterDate
     * 
     * @return ENTER_DATE
     */
    public Date getEnterDate() {
        return getDate(D_EnterDate);
    }

	/**
     * 设置onPrice
     * 
     * @param ON_PRICE
     */
	public void setOnPrice(Double value) {
        set(D_OnPrice,value);
    }
	
    /**
     * 获取onPrice
     * 
     * @return ON_PRICE
     */
    public Double getOnPrice() {
        return getDouble(D_OnPrice);
    }

	/**
     * 设置配种公猪耳号
     * 
     * @param BREED_BOAR_NAME
     */
	public void setBreedBoarName(String value) {
        set(D_BreedBoarName,value);
    }
	
    /**
     * 获取配种公猪耳号
     * 
     * @return BREED_BOAR_NAME
     */
    public String getBreedBoarName() {
        return getString(D_BreedBoarName);
    }

	/**
     * 设置breedDate
     * 
     * @param BREED_DATE
     */
	public void setBreedDate(Date value) {
        set(D_BreedDate,value);
    }
	
    /**
     * 获取breedDate
     * 
     * @return BREED_DATE
     */
    public Date getBreedDate() {
        return getDate(D_BreedDate);
    }

    /**
     * 设置breedBoar
     * 
     * @param BREED_BOAR
     */
	public void setBreedBoar(Long value) {
        set(D_BreedBoar,value);
    }
	
	/**
     * 获取breedBoar
     * 
     * @return BREED_BOAR
     */
    public Long getBreedBoar() {
        return getLong(D_BreedBoar);
    }

	/**
     * 设置pregnancyDate
     * 
     * @param PREGNANCY_DATE
     */
	public void setPregnancyDate(Date value) {
        set(D_PregnancyDate,value);
    }
	
    /**
     * 获取pregnancyDate
     * 
     * @return PREGNANCY_DATE
     */
    public Date getPregnancyDate() {
        return getDate(D_PregnancyDate);
    }

	/**
     * 设置pregnancyResult
     * 
     * @param PREGNANCY_RESULT
     */
	public void setPregnancyResult(String value) {
        set(D_PregnancyResult,value);
    }
	
    /**
     * 获取pregnancyResult
     * 
     * @return PREGNANCY_RESULT
     */
    public String getPregnancyResult() {
        return getString(D_PregnancyResult);
    }

	/**
     * 设置changeHouseDate
     * 
     * @param CHANGE_HOUSE_DATE
     */
	public void setChangeHouseDate(Date value) {
        set(D_ChangeHouseDate,value);
    }
	
    /**
     * 获取changeHouseDate
     * 
     * @return CHANGE_HOUSE_DATE
     */
    public Date getChangeHouseDate() {
        return getDate(D_ChangeHouseDate);
    }

    /**
     * 设置changeHouseId
     * 
     * @param CHANGE_HOUSE_ID
     */
	public void setChangeHouseId(Long value) {
        set(D_ChangeHouseId,value);
    }
	
	/**
     * 获取changeHouseId
     * 
     * @return CHANGE_HOUSE_ID
     */
    public Long getChangeHouseId() {
        return getLong(D_ChangeHouseId);
    }
    
    /**
     * 设置CHANGE_PIGPEN_ID
     * 
     * @param changePigpenId
     */
    public void setChangePigpenId(Long value) {
        set(D_ChangePigpenId,value);
    }
    
    /**
     * 获取CHANGE_PIGPEN_ID
     * 
     * @return changePigpenId
     */
    public Long getChangePigpenId() {
        return getLong(D_ChangePigpenId);
    }

	/**
     * 设置deliveryDate
     * 
     * @param DELIVERY_DATE
     */
	public void setDeliveryDate(Date value) {
        set(D_DeliveryDate,value);
    }
	
    /**
     * 获取deliveryDate
     * 
     * @return DELIVERY_DATE
     */
    public Date getDeliveryDate() {
        return getDate(D_DeliveryDate);
    }

	/**
     * 设置aliveLitterWeight
     * 
     * @param ALIVE_LITTER_WEIGHT
     */
	public void setAliveLitterWeight(Double value) {
        set(D_AliveLitterWeight,value);
    }
	
    /**
     * 获取aliveLitterWeight
     * 
     * @return ALIVE_LITTER_WEIGHT
     */
    public Double getAliveLitterWeight() {
        return getDouble(D_AliveLitterWeight);
    }

    /**
     * 设置healthyNum
     * 
     * @param HEALTHY_NUM
     */
	public void setHealthyNum(Long value) {
        set(D_HealthyNum,value);
    }
	
	/**
     * 获取healthyNum
     * 
     * @return HEALTHY_NUM
     */
    public Long getHealthyNum() {
        return getLong(D_HealthyNum);
    }

    /**
     * 设置weakNum
     * 
     * @param WEAK_NUM
     */
	public void setWeakNum(Long value) {
        set(D_WeakNum,value);
    }
	
	/**
     * 获取weakNum
     * 
     * @return WEAK_NUM
     */
    public Long getWeakNum() {
        return getLong(D_WeakNum);
    }

    /**
     * 设置stillbirthNum
     * 
     * @param STILLBIRTH_NUM
     */
	public void setStillbirthNum(Long value) {
        set(D_StillbirthNum,value);
    }
	
	/**
     * 获取stillbirthNum
     * 
     * @return STILLBIRTH_NUM
     */
    public Long getStillbirthNum() {
        return getLong(D_StillbirthNum);
    }

    /**
     * 设置mummyNum
     * 
     * @param MUMMY_NUM
     */
	public void setMummyNum(Long value) {
        set(D_MummyNum,value);
    }
	
	/**
     * 获取mummyNum
     * 
     * @return MUMMY_NUM
     */
    public Long getMummyNum() {
        return getLong(D_MummyNum);
    }

    /**
     * 设置mutantNum
     * 
     * @param MUTANT_NUM
     */
	public void setMutantNum(Long value) {
        set(D_MutantNum,value);
    }
	
	/**
     * 获取mutantNum
     * 
     * @return MUTANT_NUM
     */
    public Long getMutantNum() {
        return getLong(D_MutantNum);
    }

	/**
     * 设置weanDate
     * 
     * @param WEAN_DATE
     */
	public void setWeanDate(Date value) {
        set(D_WeanDate,value);
    }
	
    /**
     * 获取weanDate
     * 
     * @return WEAN_DATE
     */
    public Date getWeanDate() {
        return getDate(D_WeanDate);
    }

    /**
     * 设置weanNum
     * 
     * @param WEAN_NUM
     */
	public void setWeanNum(Long value) {
        set(D_WeanNum,value);
    }
	
	/**
     * 获取weanNum
     * 
     * @return WEAN_NUM
     */
    public Long getWeanNum() {
        return getLong(D_WeanNum);
    }

	/**
     * 设置weanWeight
     * 
     * @param WEAN_WEIGHT
     */
	public void setWeanWeight(Double value) {
        set(D_WeanWeight,value);
    }
	
    /**
     * 获取weanWeight
     * 
     * @return WEAN_WEIGHT
     */
    public Double getWeanWeight() {
        return getDouble(D_WeanWeight);
    }

    /**
     * 设置创建人
     * 
     * @param CREATE_ID
     */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
     * 获取创建人
     * 
     * @return CREATE_ID
     */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
     * 设置创建日期
     * 
     * @param CREATE_DATE
     */
	public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }
	
	
	@Override
	public List<String> getPropertes() {
	    if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
	        setPropertes(D_RowId);
	        setPropertes(D_SortNbr);
	        setPropertes(D_Status);
	        setPropertes(D_DeletedFlag);
	        setPropertes(D_Notes);
	        setPropertes(D_FarmId);
	        setPropertes(D_CompanyId);
	        setPropertes(D_HouseId);
	        setPropertes(D_PigpenId);
	        setPropertes(D_PigId);
	        setPropertes(D_EarBrand);
	        setPropertes(D_EarShort);
	        setPropertes(D_MaterialId);
	        setPropertes(D_EarThorn);
	        setPropertes(D_BreedId);
	        setPropertes(D_PigType);
	        setPropertes(D_Sex);
	        setPropertes(D_Strain);
	        setPropertes(D_PigClass);
	        setPropertes(D_Parity);
	        setPropertes(D_Worker);
	        setPropertes(D_BirthDate);
	        setPropertes(D_RightTeatNum);
	        setPropertes(D_LeftTeatNum);
	        setPropertes(D_SupplierId);
	        setPropertes(D_ProductDate);
	        setPropertes(D_EnterDate);
	        setPropertes(D_OnPrice);
	        setPropertes(D_BreedBoarName);
	        setPropertes(D_BreedDate);
	        setPropertes(D_BreedBoar);
	        setPropertes(D_PregnancyDate);
	        setPropertes(D_PregnancyResult);
	        setPropertes(D_ChangeHouseDate);
	        setPropertes(D_ChangeHouseId);
	        setPropertes(D_ChangePigpenId);
	        setPropertes(D_DeliveryDate);
	        setPropertes(D_AliveLitterWeight);
	        setPropertes(D_HealthyNum);
	        setPropertes(D_WeakNum);
	        setPropertes(D_StillbirthNum);
	        setPropertes(D_MummyNum);
	        setPropertes(D_MutantNum);
	        setPropertes(D_WeanDate);
	        setPropertes(D_WeanNum);
	        setPropertes(D_WeanWeight);
	        setPropertes(D_CreateId);
	        setPropertes(D_CreateDate);
	    }
	    return super.getPropertes();
	}

}




