package xn.pigfarm.model.production;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-5-17 10:36:18
 *       表：PP_L_PIG
 */
public class PigView  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 3774414635697720138L;

    // 行号: 系统保留字段，标识一条数据记录。
    private final String D_RowId="rowId";

    // 页面记录号
    private final String D_LineNumbers="lineNumbers";

    // 备注
    private final String D_Notes="notes";

    // 排序号
    private final String D_SortNbr="sortNbr";

    // 状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    private final String D_Status="status";

    // 记录删除标志: [0]-未删除;[1]-逻辑删除
    private final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private final String D_OriginApp="originApp";

    // 公司（猪场子公司）ID
    private final String D_CompanyId="companyId";

    // 猪场(子公司)ID
    private final String D_FarmId="farmId";

    // 记录行号
    private final String D_LineNumber="lineNumber";

    // 产线ID
    private final String D_LineId="lineId";

    // 猪群
    private final String D_SwineryId="swineryId";

    // 猪舍ID
    private final String D_HouseId="houseId";

    // 栏号ID
    private final String D_PigpenId="pigpenId";

    // 耳号ID
    private final String D_EarCodeId="earCodeId";

    // 代养母猪ID
    private final String D_BoardSowId="boardSowId";

    // 物料ID
    private final String D_MaterialId="materialId";

    // 猪类别
    private final String D_PigType="pigType";

    // 性别
    private final String D_Sex="sex";

    // 品种
    private final String D_BreedId="breedId";

    // 品系
    private final String D_Strain="strain";

    // 猪只状态
    private final String D_PigClass="pigClass";

    // 体况
    private final String D_BodyCondition="bodyCondition";

    // 胎次
    private final String D_Parity="parity";

    // 出生胎次
    private final String D_BirthParity="birthParity";

    // 出生窝号
    private final String D_HouseNum="houseNum";

    // 出生日期
    private final String D_BirthDate="birthDate";

    // 出生体重
    private final String D_BirthWeight="birthWeight";

    // 入场日期
    private final String D_EnterDate="enterDate";

    // 入场体重
    private final String D_EnterWeight="enterWeight";

    // 入场状态
    private final String D_EnterPigClass="enterPigClass";

    // 入场价
    private final String D_OnPrice="onPrice";

    // 母猪入场胎次
    private final String D_EnterParity="enterParity";

    // 负责人
    private final String D_Worker="worker";

    // 断奶日龄
    private final String D_MewDayAge="mewDayAge";

    // 断奶体重
    private final String D_MewWeight="mewWeight";

    // 右奶头数
    private final String D_RightTeatNum="rightTeatNum";

    // 左奶头数
    private final String D_LeftTeatNum="leftTeatNum";

    // 近交系数
    private final String D_CoefficientInbred="coefficientInbred";

    // 供应商
    private final String D_SupplierId="supplierId";

    // 来源
    private final String D_Origin="origin";

    // 最后一次单据号
    private final String D_LastBillId = "lastBillId";

    // 最后一次事件日期
    private final String D_LastEventDate="lastEventDate";
    
    // 最后状态日期
    private static final String D_LastClassDate="lastClassDate";
    
    // 最后状态日龄
    private static final String D_LastClassAge="lastClassAge";

    // 单据号
    private final String D_BillId="billId";

    // 关联猪只ID用于从一个场转到别一个场（表示原场ID)
    private final String D_RelationPigId="relationPigId";

    // 创建人
    private final String D_CreateId="createId";

    // 创建日期
    private final String D_CreateDate="createDate";

    // 耳牌号
    private final String D_EarBrand = "earBrand";

    // 代养母猪
    private final String D_BoardEarBrand = "boardEarBrand";

    // 耳缺号
    private final String D_EarShort = "earShort";

    // 耳刺号
    private final String D_EarThorn = "earThorn";

    // LAST_BILL_ID的EVENT_CODE
    private final String D_EventCode = "eventCode";

    // 猪只唯一标识
    private final String D_UniquePigFlag = "uniquePigFlag";
    

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
     * 设置页面记录号
     * 
     * @param LINE_NUMBERS
     */
	public void setLineNumbers(Long value) {
        set(D_LineNumbers,value);
    }
	
	/**
     * 获取页面记录号
     * 
     * @return LINE_NUMBERS
     */
    public Long getLineNumbers() {
        return getLong(D_LineNumbers);
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
     * 设置状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @param STATUS
     */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
    /**
     * 获取状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
     * 
     * @return STATUS
     */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
     * 设置记录删除标志: [0]-未删除;[1]-逻辑删除
     * 
     * @param DELETED_FLAG
     */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
    /**
     * 获取记录删除标志: [0]-未删除;[1]-逻辑删除
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
     * 设置公司（猪场子公司）ID
     * 
     * @param COMPANY_ID
     */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
     * 获取公司（猪场子公司）ID
     * 
     * @return COMPANY_ID
     */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

    /**
     * 设置猪场(子公司)ID
     * 
     * @param FARM_ID
     */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
     * 获取猪场(子公司)ID
     * 
     * @return FARM_ID
     */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

    /**
     * 设置记录行号
     * 
     * @param LINE_NUMBER
     */
	public void setLineNumber(Long value) {
        set(D_LineNumber,value);
    }
	
	/**
     * 获取记录行号
     * 
     * @return LINE_NUMBER
     */
    public Long getLineNumber() {
        return getLong(D_LineNumber);
    }

    /**
     * 设置产线ID
     * 
     * @param LINE_ID
     */
	public void setLineId(Long value) {
        set(D_LineId,value);
    }
	
	/**
     * 获取产线ID
     * 
     * @return LINE_ID
     */
    public Long getLineId() {
        return getLong(D_LineId);
    }

    /**
     * 设置猪群
     * 
     * @param SWINERY_ID
     */
	public void setSwineryId(Long value) {
        set(D_SwineryId,value);
    }
	
	/**
     * 获取猪群
     * 
     * @return SWINERY_ID
     */
    public Long getSwineryId() {
        return getLong(D_SwineryId);
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
     * 设置耳号ID
     * 
     * @param EAR_CODE_ID
     */
	public void setEarCodeId(Long value) {
        set(D_EarCodeId,value);
    }
	
	/**
     * 获取耳号ID
     * 
     * @return EAR_CODE_ID
     */
    public Long getEarCodeId() {
        return getLong(D_EarCodeId);
    }

    /**
     * 设置代养母猪ID
     * 
     * @param BOARD_SOW_ID
     */
	public void setBoardSowId(Long value) {
        set(D_BoardSowId,value);
    }
	
	/**
     * 获取代养母猪ID
     * 
     * @return BOARD_SOW_ID
     */
    public Long getBoardSowId() {
        return getLong(D_BoardSowId);
    }

    /**
     * 设置物料ID
     * 
     * @param MATERIAL_ID
     */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
     * 获取物料ID
     * 
     * @return MATERIAL_ID
     */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

	/**
     * 设置猪类别
     * 
     * @param PIG_TYPE
     */
	public void setPigType(String value) {
        set(D_PigType,value);
    }
	
    /**
     * 获取猪类别
     * 
     * @return PIG_TYPE
     */
    public String getPigType() {
        return getString(D_PigType);
    }

	/**
     * 设置性别
     * 
     * @param SEX
     */
	public void setSex(String value) {
        set(D_Sex,value);
    }
	
    /**
     * 获取性别
     * 
     * @return SEX
     */
    public String getSex() {
        return getString(D_Sex);
    }

    /**
     * 设置品种
     * 
     * @param BREED_ID
     */
	public void setBreedId(Long value) {
        set(D_BreedId,value);
    }
	
	/**
     * 获取品种
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
     * 设置猪只状态
     * 
     * @param PIG_CLASS
     */
	public void setPigClass(Long value) {
        set(D_PigClass,value);
    }
	
	/**
     * 获取猪只状态
     * 
     * @return PIG_CLASS
     */
    public Long getPigClass() {
        return getLong(D_PigClass);
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
     * 设置胎次
     * 
     * @param PARITY
     */
	public void setParity(Long value) {
        set(D_Parity,value);
    }
	
	/**
     * 获取胎次
     * 
     * @return PARITY
     */
    public Long getParity() {
        return getLong(D_Parity);
    }

    /**
     * 设置出生胎次
     * 
     * @param BIRTH_PARITY
     */
	public void setBirthParity(Long value) {
        set(D_BirthParity,value);
    }
	
	/**
     * 获取出生胎次
     * 
     * @return BIRTH_PARITY
     */
    public Long getBirthParity() {
        return getLong(D_BirthParity);
    }

    /**
     * 设置出生窝号
     * 
     * @param HOUSE_NUM
     */
    public void setHouseNum(String value) {
        set(D_HouseNum,value);
    }
	
	/**
     * 获取出生窝号
     * 
     * @return HOUSE_NUM
     */
    public String getHouseNum() {
        return getString(D_HouseNum);
    }

	/**
     * 设置出生日期
     * 
     * @param BIRTH_DATE
     */
	public void setBirthDate(Date value) {
        set(D_BirthDate,value);
    }
	
    /**
     * 获取出生日期
     * 
     * @return BIRTH_DATE
     */
    public Date getBirthDate() {
        return getDate(D_BirthDate);
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
     * 设置入场日期
     * 
     * @param ENTER_DATE
     */
	public void setEnterDate(Date value) {
        set(D_EnterDate,value);
    }
	
    /**
     * 获取入场日期
     * 
     * @return ENTER_DATE
     */
    public Date getEnterDate() {
        return getDate(D_EnterDate);
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
     * 设置入场状态
     * 
     * @param ENTER_PIG_CLASS
     */
	public void setEnterPigClass(Long value) {
        set(D_EnterPigClass,value);
    }
	
	/**
     * 获取入场状态
     * 
     * @return ENTER_PIG_CLASS
     */
    public Long getEnterPigClass() {
        return getLong(D_EnterPigClass);
    }

	/**
     * 设置入场价
     * 
     * @param ON_PRICE
     */
	public void setOnPrice(Double value) {
        set(D_OnPrice,value);
    }
	
    /**
     * 获取入场价
     * 
     * @return ON_PRICE
     */
    public Double getOnPrice() {
        return getDouble(D_OnPrice);
    }

    /**
     * 设置母猪入场胎次
     * 
     * @param ENTER_PARITY
     */
	public void setEnterParity(Long value) {
        set(D_EnterParity,value);
    }
	
	/**
     * 获取母猪入场胎次
     * 
     * @return ENTER_PARITY
     */
    public Long getEnterParity() {
        return getLong(D_EnterParity);
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
     * 设置断奶日龄
     * 
     * @param MEW_DAY_AGE
     */
	public void setMewDayAge(Long value) {
        set(D_MewDayAge,value);
    }
	
	/**
     * 获取断奶日龄
     * 
     * @return MEW_DAY_AGE
     */
    public Long getMewDayAge() {
        return getLong(D_MewDayAge);
    }

	/**
     * 设置断奶体重
     * 
     * @param MEW_WEIGHT
     */
	public void setMewWeight(Double value) {
        set(D_MewWeight,value);
    }
	
    /**
     * 获取断奶体重
     * 
     * @return MEW_WEIGHT
     */
    public Double getMewWeight() {
        return getDouble(D_MewWeight);
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
     * 设置近交系数
     * 
     * @param COEFFICIENT_INBRED
     */
	public void setCoefficientInbred(Double value) {
        set(D_CoefficientInbred,value);
    }
	
    /**
     * 获取近交系数
     * 
     * @return COEFFICIENT_INBRED
     */
    public Double getCoefficientInbred() {
        return getDouble(D_CoefficientInbred);
    }

    /**
     * 设置供应商
     * 
     * @param SUPPLIER_ID
     */
	public void setSupplierId(Long value) {
        set(D_SupplierId,value);
    }
	
	/**
     * 获取供应商
     * 
     * @return SUPPLIER_ID
     */
    public Long getSupplierId() {
        return getLong(D_SupplierId);
    }

	/**
     * 设置来源
     * 
     * @param ORIGIN
     */
	public void setOrigin(String value) {
        set(D_Origin,value);
    }
	
    /**
     * 获取来源
     * 
     * @return ORIGIN
     */
    public String getOrigin() {
        return getString(D_Origin);
    }

    /**
     * 设置最后一次单据号
     * 
     * @param BILL_ID
     */
    public void setLastBillId(Long value) {
        set(D_LastBillId, value);
    }

    /**
     * 获取最后一次单据号
     * 
     * @return BILL_ID
     */
    public Long getLastBillId() {
        return getLong(D_LastBillId);
    }

	/**
     * 设置最后一次事件日期
     * 
     * @param LAST_EVENT_DATE
     */
	public void setLastEventDate(Date value) {
        set(D_LastEventDate,value);
    }
	
    /**
     * 获取最后一次事件日期
     * 
     * @return LAST_EVENT_DATE
     */
    public Date getLastEventDate() {
        return getDate(D_LastEventDate);
    }
    
    /**
     * 设置最后一次状态日期
     * 
     * @param LAST_EVENT_DATE
     */
    public void setLastClassDate(Date value) {
    	set(D_LastClassDate,value);
    }
    
    /**
     * 获取最后一次状态日期
     * 
     * @return LAST_EVENT_DATE
     */
    public Date getLastClassDate() {
    	return getDate(D_LastClassDate);
    }
    
    /**
     * 设置最后一次状态日龄
     * 
     * @param LAST_EVENT_DATE
     */
    public void setLastClassAge(String value) {
    	set(D_LastClassAge,value);
    }
    
    /**
     * 获取最后一次状态日龄
     * 
     * @return LAST_EVENT_DATE
     */
    public String getLastClassAge() {
    	return getString(D_LastClassAge);
    }

    /**
     * 设置单据号
     * 
     * @param BILL_ID
     */
	public void setBillId(Long value) {
        set(D_BillId,value);
    }
	
	/**
     * 获取单据号
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

    /**
     * 设置关联猪只ID用于从一个场转到别一个场（表示原场ID)
     * 
     * @param RELATION_PIG_ID
     */
	public void setRelationPigId(Long value) {
        set(D_RelationPigId,value);
    }
	
	/**
     * 获取关联猪只ID用于从一个场转到别一个场（表示原场ID)
     * 
     * @return RELATION_PIG_ID
     */
    public Long getRelationPigId() {
        return getLong(D_RelationPigId);
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

    /**
     * 设置耳牌号
     */
    public void setEarBrand(String value) {
        set(D_EarBrand, value);
    }
    
    /**
     * 获取耳牌号
     */
    public String getEarBrand() {
        return getString(D_EarBrand);
    }

    /**
     * 设置
     */
    public void setBoardEarBrand(String value) {
        set(D_BoardEarBrand, value);
    }

    /**
     * 获取
     */
    public String getBoardEarBrand() {
        return getString(D_BoardEarBrand);
    }
    
    /**
     * 耳缺号
     */
    public void setEarShort(String value) {
        set(D_EarShort, value);
    }

    /**
     * 耳缺号
     */
    public String getEarShort() {
        return getString(D_EarShort);
    }

    /**
     * 耳刺号
     */
    public void setEarThorn(String value) {
        set(D_EarThorn, value);
    }

    /**
     * 耳刺号
     */
    public String getEarThorn() {
        return getString(D_EarThorn);
    }

    /**
     * 设置LAST_BILL_ID的EVENT_CODE
     */
    public void setEventCode(String value) {
        set(D_EventCode, value);
    }

    /**
     * 获取LAST_BILL_ID的EVENT_CODE
     */
    public String getEventCode() {
        return getString(D_EventCode);
    }

    /**
     * 设置备注
     * 
     * @param NOTES
     */
    public void setUniquePigFlag(String value) {
        set(D_UniquePigFlag, value);
    }

    /**
     * 获取备注
     * 
     * @return NOTES
     */
    public String getUniquePigFlag() {
        return getString(D_UniquePigFlag);
    }

    public List<String> getPropertes() {
        if (super.getPropertes() == null || super.getPropertes().isEmpty()) {
            setPropertes("rowId");
            setPropertes("lineNumbers");
            setPropertes("notes");
            setPropertes("sortNbr");
            setPropertes("status");
            setPropertes("deletedFlag");
            setPropertes("originFlag");
            setPropertes("originApp");
            setPropertes("companyId");
            setPropertes("farmId");
            setPropertes("lineNumber");
            setPropertes("lineId");
            setPropertes("swineryId");
            setPropertes("houseId");
            setPropertes("pigpenId");
            setPropertes("earCodeId");
            setPropertes("boardSowId");
            setPropertes("materialId");
            setPropertes("pigType");
            setPropertes("sex");
            setPropertes("breedId");
            setPropertes("strain");
            setPropertes("pigClass");
            setPropertes("bodyCondition");
            setPropertes("parity");
            setPropertes("birthParity");
            setPropertes("houseNum");
            setPropertes("birthDate");
            setPropertes("birthWeight");
            setPropertes("enterDate");
            setPropertes("enterWeight");
            setPropertes("enterPigClass");
            setPropertes("onPrice");
            setPropertes("enterParity");
            setPropertes("worker");
            setPropertes("mewDayAge");
            setPropertes("mewWeight");
            setPropertes("rightTeatNum");
            setPropertes("leftTeatNum");
            setPropertes("coefficientInbred");
            setPropertes("supplierId");
            setPropertes("origin");
            setPropertes("lastBillId");
            setPropertes("lastEventDate");
            setPropertes("lastClassDate");
            setPropertes("lastClassAge");
            setPropertes("billId");
            setPropertes("relationPigId");
            setPropertes("createId");
            setPropertes("createDate");
            setPropertes("earBrand");
            setPropertes("createDate");
            setPropertes("earShort");
            setPropertes("earThorn");
            setPropertes("eventCode");
            setPropertes("uniquePigFlag");
        }
        return super.getPropertes();
    }

}




