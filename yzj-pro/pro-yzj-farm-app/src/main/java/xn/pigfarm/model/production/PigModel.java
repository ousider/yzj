package xn.pigfarm.model.production;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-24 13:15:24
 *       表：PP_L_PIG
 */
public class PigModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = -3041040547830746227L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();

    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId = "rowId";

    // 排序号
    private static final String D_SortNbr = "sortNbr";

    // 表示该对象实例的业务状态 通常用“1/2”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status = "status";

    // [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag = "deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag = "originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp = "originApp";

    // 备注
    private static final String D_Notes = "notes";

    // 对应的公司ID
    private static final String D_FarmId = "farmId";

    // companyId
    private static final String D_CompanyId = "companyId";

    // 页面记录号
    private static final String D_LineNumbers = "lineNumbers";

    // 记录行号
    private static final String D_LineNumber = "lineNumber";

    // 产线ID
    private static final String D_LineId = "lineId";

    // 猪群
    private static final String D_SwineryId = "swineryId";

    // 猪舍ID
    private static final String D_HouseId = "houseId";

    // 栏号ID
    private static final String D_PigpenId = "pigpenId";

    // 耳号ID
    private static final String D_EarCodeId = "earCodeId";

    // 代养母猪ID
    private static final String D_BoardSowId = "boardSowId";

    // 物料ID
    private static final String D_MaterialId = "materialId";

    // 品种
    private static final String D_BreedId = "breedId";

    // 猪类别
    private static final String D_PigType = "pigType";

    // 性别
    private static final String D_Sex = "sex";

    // 品系
    private static final String D_Strain = "strain";

    // 猪只状态
    private static final String D_PigClass = "pigClass";

    // 体况
    private static final String D_BodyCondition = "bodyCondition";

    // 胎次
    private static final String D_Parity = "parity";

    // 出生胎次
    private static final String D_BirthParity = "birthParity";

    // 出生窝号
    private static final String D_HouseNum = "houseNum";

    // 出生日期
    private static final String D_BirthDate = "birthDate";

    // 出生体重
    private static final String D_BirthWeight = "birthWeight";

    // 入场日期
    private static final String D_EnterDate = "enterDate";

    // 入场体重
    private static final String D_EnterWeight = "enterWeight";

    // 入场状态
    private static final String D_EnterPigClass = "enterPigClass";

    // 入场价
    private static final String D_OnPrice = "onPrice";

    // 母猪入场胎次
    private static final String D_EnterParity = "enterParity";

    // 负责人
    private static final String D_Worker = "worker";

    // 断奶日龄
    private static final String D_MewDayAge = "mewDayAge";

    // 断奶体重
    private static final String D_MewWeight = "mewWeight";

    // 右奶头数
    private static final String D_RightTeatNum = "rightTeatNum";

    // 左奶头数
    private static final String D_LeftTeatNum = "leftTeatNum";

    // 近交系数
    private static final String D_CoefficientInbred = "coefficientInbred";

    // 供应商
    private static final String D_SupplierId = "supplierId";

    // 来源:[1]本厂,[2]外购,[3]虚拟
    private static final String D_Origin = "origin";

    // 生产号:母猪配种时产生
    private static final String D_ProNo = "proNo";

    // 最后一次单据号
    private static final String D_LastBillId = "lastBillId";

    // 最后一次事件日期
    private static final String D_LastEventDate = "lastEventDate";

    // 最后状态日期
    private static final String D_LastClassDate = "lastClassDate";

    // 离场日期
    private static final String D_LeaveDate = "leaveDate";

    // 入场单据号
    private static final String D_BillId = "billId";

    // 表示原场ID(关联猪只ID用于从一个场转到别一个场)
    private static final String D_RelationPigId = "relationPigId";

    // createId
    private static final String D_CreateId = "createId";

    // 入场方式
    private static final String D_OptType = "optType";

    // 创建日期
    private static final String D_CreateDate = "createDate";

    // 留种标示 Y:是 / N:不是
    private static final String D_SeedFlag = "seedFlag";

    // 是否批量留种 Y:是 N:不是
    private static final String D_IsBatchSeed = "isBatchSeed";

    // SAP固定资产种猪耳号(MTC_BranchID+N'.'+RowID+N'.'+初始耳号)
    private static final String D_SapFixedAssetsEarbrand = "sapFixedAssetsEarbrand";

    // 猪只唯一标识
    private static final String D_UniquePigFlag = "uniquePigFlag";

    // 转生产时的日龄
    private static final String D_ToProductDayage = "toProductDayage";

    /**
     * 设置行号: 系统保留字段，标识一条数据记录。
     * 
     * @param ROW_ID
     */
    public void setRowId(Long value) {
        set(D_RowId, value);
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
        set(D_SortNbr, value);
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
        set(D_Status, value);
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
        set(D_DeletedFlag, value);
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
        set(D_OriginFlag, value);
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
        set(D_OriginApp, value);
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
        set(D_Notes, value);
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
        set(D_FarmId, value);
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
        set(D_CompanyId, value);
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
     * 设置页面记录号
     * 
     * @param LINE_NUMBERS
     */
    public void setLineNumbers(Long value) {
        set(D_LineNumbers, value);
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
     * 设置记录行号
     * 
     * @param LINE_NUMBER
     */
    public void setLineNumber(Long value) {
        set(D_LineNumber, value);
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
        set(D_LineId, value);
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
        set(D_SwineryId, value);
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
        set(D_HouseId, value);
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
        set(D_PigpenId, value);
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
        set(D_EarCodeId, value);
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
        set(D_BoardSowId, value);
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
        set(D_MaterialId, value);
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
     * 设置品种
     * 
     * @param BREED_ID
     */
    public void setBreedId(Long value) {
        set(D_BreedId, value);
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
     * 设置猪类别
     * 
     * @param PIG_TYPE
     */
    public void setPigType(String value) {
        set(D_PigType, value);
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
        set(D_Sex, value);
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
     * 设置品系
     * 
     * @param STRAIN
     */
    public void setStrain(String value) {
        set(D_Strain, value);
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
        set(D_PigClass, value);
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
        set(D_BodyCondition, value);
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
        set(D_Parity, value);
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
        set(D_BirthParity, value);
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
        set(D_HouseNum, value);
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
        set(D_BirthDate, value);
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
        set(D_BirthWeight, value);
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
        set(D_EnterDate, value);
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
        set(D_EnterWeight, value);
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
        set(D_EnterPigClass, value);
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
        set(D_OnPrice, value);
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
        set(D_EnterParity, value);
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
        set(D_Worker, value);
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
        set(D_MewDayAge, value);
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
        set(D_MewWeight, value);
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
        set(D_RightTeatNum, value);
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
        set(D_LeftTeatNum, value);
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
        set(D_CoefficientInbred, value);
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
        set(D_SupplierId, value);
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
     * 设置来源:[1]本厂,[2]外购,[3]虚拟
     * 
     * @param ORIGIN
     */
    public void setOrigin(String value) {
        set(D_Origin, value);
    }

    /**
     * 获取来源:[1]本厂,[2]外购,[3]虚拟
     * 
     * @return ORIGIN
     */
    public String getOrigin() {
        return getString(D_Origin);
    }

    /**
     * 设置生产号:母猪配种时产生
     * 
     * @param PRO_NO
     */
    public void setProNo(Long value) {
        set(D_ProNo, value);
    }

    /**
     * 获取生产号:母猪配种时产生
     * 
     * @return PRO_NO
     */
    public Long getProNo() {
        return getLong(D_ProNo);
    }

    /**
     * 设置最后一次单据号
     * 
     * @param LAST_BILL_ID
     */
    public void setLastBillId(Long value) {
        set(D_LastBillId, value);
    }

    /**
     * 获取最后一次单据号
     * 
     * @return LAST_BILL_ID
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
        set(D_LastEventDate, value);
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
     * 设置最后状态日期
     * 
     * @param LAST_CLASS_DATE
     */
    public void setLastClassDate(Date value) {
        set(D_LastClassDate, value);
    }

    /**
     * 获取最后状态日期
     * 
     * @return LAST_CLASS_DATE
     */
    public Date getLastClassDate() {
        return getDate(D_LastClassDate);
    }

    /**
     * 设置离场日期
     * 
     * @param LEAVE_DATE
     */
    public void setLeaveDate(Date value) {
        set(D_LeaveDate, value);
    }

    /**
     * 获取离场日期
     * 
     * @return LEAVE_DATE
     */
    public Date getLeaveDate() {
        return getDate(D_LeaveDate);
    }

    /**
     * 设置入场单据号
     * 
     * @param BILL_ID
     */
    public void setBillId(Long value) {
        set(D_BillId, value);
    }

    /**
     * 获取入场单据号
     * 
     * @return BILL_ID
     */
    public Long getBillId() {
        return getLong(D_BillId);
    }

    /**
     * 设置表示原场ID(关联猪只ID用于从一个场转到别一个场)
     * 
     * @param RELATION_PIG_ID
     */
    public void setRelationPigId(Long value) {
        set(D_RelationPigId, value);
    }

    /**
     * 获取表示原场ID(关联猪只ID用于从一个场转到别一个场)
     * 
     * @return RELATION_PIG_ID
     */
    public Long getRelationPigId() {
        return getLong(D_RelationPigId);
    }

    /**
     * 设置createId
     * 
     * @param CREATE_ID
     */
    public void setCreateId(Long value) {
        set(D_CreateId, value);
    }

    /**
     * 获取createId
     * 
     * @return CREATE_ID
     */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

    /**
     * 设置入场方式
     * 
     * @param OPT_TYPE
     */
    public void setOptType(String value) {
        set(D_OptType, value);
    }

    /**
     * 获取入场方式
     * 
     * @return OPT_TYPE
     */
    public String getOptType() {
        return getString(D_OptType);
    }

    /**
     * 设置创建日期
     * 
     * @param CREATE_DATE
     */
    public void setCreateDate(Timestamp value) {
        set(D_CreateDate, value);
    }

    /**
     * 获取创建日期
     * 
     * @return CREATE_DATE
     */
    public Timestamp getCreateDate() {
        return getTimestamp(D_CreateDate);
    }

    /**
     * 设置留种标示 Y:是 / N:不是
     * 
     * @param SEED_FLAG
     */
    public void setSeedFlag(String value) {
        set(D_SeedFlag, value);
    }

    /**
     * 获取留种标示 Y:是 / N:不是
     * 
     * @return SEED_FLAG
     */
    public String getSeedFlag() {
        return getString(D_SeedFlag);
    }

    /**
     * 设置是否批量留种 Y:是 N:不是
     * 
     * @param IS_BATCH_SEED
     */
    public void setIsBatchSeed(String value) {
        set(D_IsBatchSeed, value);
    }

    /**
     * 获取是否批量留种 Y:是 N:不是
     * 
     * @return IS_BATCH_SEED
     */
    public String getIsBatchSeed() {
        return getString(D_IsBatchSeed);
    }

    /**
     * 设置SAP固定资产种猪耳号(MTC_BranchID+N'.'+RowID+N'.'+初始耳号)
     * 
     * @param SAP_FIXED_ASSETS_EARBRAND
     */
    public void setSapFixedAssetsEarbrand(String value) {
        set(D_SapFixedAssetsEarbrand, value);
    }

    /**
     * 获取SAP固定资产种猪耳号(MTC_BranchID+N'.'+RowID+N'.'+初始耳号)
     * 
     * @return SAP_FIXED_ASSETS_EARBRAND
     */
    public String getSapFixedAssetsEarbrand() {
        return getString(D_SapFixedAssetsEarbrand);
    }

    /**
     * 设置猪只唯一标识
     * 
     * @param UNIQUE_PIG_FLAG
     */
    public void setUniquePigFlag(String value) {
        set(D_UniquePigFlag, value);
    }

    /**
     * 获取猪只唯一标识
     * 
     * @return UNIQUE_PIG_FLAG
     */
    public String getUniquePigFlag() {
        return getString(D_UniquePigFlag);
    }

    /**
     * 设置转生产时的日龄
     * 
     * @param TO_PRODUCT_DAYAGE
     */
    public void setToProductDayage(Long value) {
        set(D_ToProductDayage, value);
    }

    /**
     * 获取转生产时的日龄
     * 
     * @return TO_PRODUCT_DAYAGE
     */
    public Long getToProductDayage() {
        return getLong(D_ToProductDayage);
    }

    static {
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("lineNumbers");
        propertes.add("lineNumber");
        propertes.add("lineId");
        propertes.add("swineryId");
        propertes.add("houseId");
        propertes.add("pigpenId");
        propertes.add("earCodeId");
        propertes.add("boardSowId");
        propertes.add("materialId");
        propertes.add("breedId");
        propertes.add("pigType");
        propertes.add("sex");
        propertes.add("strain");
        propertes.add("pigClass");
        propertes.add("bodyCondition");
        propertes.add("parity");
        propertes.add("birthParity");
        propertes.add("houseNum");
        propertes.add("birthDate");
        propertes.add("birthWeight");
        propertes.add("enterDate");
        propertes.add("enterWeight");
        propertes.add("enterPigClass");
        propertes.add("onPrice");
        propertes.add("enterParity");
        propertes.add("worker");
        propertes.add("mewDayAge");
        propertes.add("mewWeight");
        propertes.add("rightTeatNum");
        propertes.add("leftTeatNum");
        propertes.add("coefficientInbred");
        propertes.add("supplierId");
        propertes.add("origin");
        propertes.add("proNo");
        propertes.add("lastBillId");
        propertes.add("lastEventDate");
        propertes.add("lastClassDate");
        propertes.add("leaveDate");
        propertes.add("billId");
        propertes.add("relationPigId");
        propertes.add("createId");
        propertes.add("optType");
        propertes.add("createDate");
        propertes.add("seedFlag");
        propertes.add("isBatchSeed");
        propertes.add("sapFixedAssetsEarbrand");
        propertes.add("uniquePigFlag");
        propertes.add("toProductDayage");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
