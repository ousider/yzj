package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-21 11:47:41
 *       表：CD_O_PORK_PIG
 */
public class PorkPigModel extends BaseDataModel implements Serializable {

    private static final long serialVersionUID = 4127840664289469247L;

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

    // materialId
    private static final String D_MaterialId = "materialId";

    // 品种ID
    private static final String D_BreedId = "breedId";

    // 品系
    private static final String D_Strain = "strain";

    // 体况
    private static final String D_BodyCondition = "bodyCondition";

    // 毛色
    private static final String D_Color = "color";

    // 右奶头数
    private static final String D_RightTeatNum = "rightTeatNum";

    // 左奶头数
    private static final String D_LeftTeatNum = "leftTeatNum";

    // 出生体重
    private static final String D_BirthWeight = "birthWeight";

    // 入场日龄
    private static final String D_EnterDayAge = "enterDayAge";

    // 入场体重
    private static final String D_EnterWeight = "enterWeight";

    // 断奶日龄
    private static final String D_MewDayAge = "mewDayAge";

    // 断奶体重
    private static final String D_MewWeight = "mewWeight";

    // 转保育日龄
    private static final String D_ChildCareDayAge = "childCareDayAge";

    // 转保育体重
    private static final String D_ChildCareWeight = "childCareWeight";

    // 转育肥日龄
    private static final String D_FattenDayAge = "fattenDayAge";

    // 转育肥体重
    private static final String D_FattenWeight = "fattenWeight";

    // 出售日龄
    private static final String D_SellDayAge = "sellDayAge";

    // 出售体重
    private static final String D_SellWeight = "sellWeight";

    // 料肉比
    private static final String D_Frc = "frc";

    // boarId
    private static final String D_BoarId = "boarId";

    // sowId
    private static final String D_SowId = "sowId";

    // 种公猪主数据
    private static final String D_StockBoarId = "stockBoarId";

    // broodSowId
    private static final String D_BroodSowId = "broodSowId";

    // 是否选种 0是 1否
    private static final String D_IsSelect = "isSelect";

    // 分娩区分性别
    private static final String D_IsDifSex = "isDifSex";

    // 是否留种 0是 1否
    private static final String D_IsSeed = "isSeed";

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
     * 设置materialId
     * 
     * @param MATERIAL_ID
     */
    public void setMaterialId(Long value) {
        set(D_MaterialId, value);
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
        set(D_BreedId, value);
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
     * 设置毛色
     * 
     * @param COLOR
     */
    public void setColor(String value) {
        set(D_Color, value);
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
     * 设置入场日龄
     * 
     * @param ENTER_DAY_AGE
     */
    public void setEnterDayAge(Long value) {
        set(D_EnterDayAge, value);
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
     * 设置转保育日龄
     * 
     * @param CHILD_CARE_DAY_AGE
     */
    public void setChildCareDayAge(Long value) {
        set(D_ChildCareDayAge, value);
    }

    /**
     * 获取转保育日龄
     * 
     * @return CHILD_CARE_DAY_AGE
     */
    public Long getChildCareDayAge() {
        return getLong(D_ChildCareDayAge);
    }

    /**
     * 设置转保育体重
     * 
     * @param CHILD_CARE_WEIGHT
     */
    public void setChildCareWeight(Double value) {
        set(D_ChildCareWeight, value);
    }

    /**
     * 获取转保育体重
     * 
     * @return CHILD_CARE_WEIGHT
     */
    public Double getChildCareWeight() {
        return getDouble(D_ChildCareWeight);
    }

    /**
     * 设置转育肥日龄
     * 
     * @param FATTEN_DAY_AGE
     */
    public void setFattenDayAge(Long value) {
        set(D_FattenDayAge, value);
    }

    /**
     * 获取转育肥日龄
     * 
     * @return FATTEN_DAY_AGE
     */
    public Long getFattenDayAge() {
        return getLong(D_FattenDayAge);
    }

    /**
     * 设置转育肥体重
     * 
     * @param FATTEN_WEIGHT
     */
    public void setFattenWeight(Double value) {
        set(D_FattenWeight, value);
    }

    /**
     * 获取转育肥体重
     * 
     * @return FATTEN_WEIGHT
     */
    public Double getFattenWeight() {
        return getDouble(D_FattenWeight);
    }

    /**
     * 设置出售日龄
     * 
     * @param SELL_DAY_AGE
     */
    public void setSellDayAge(Long value) {
        set(D_SellDayAge, value);
    }

    /**
     * 获取出售日龄
     * 
     * @return SELL_DAY_AGE
     */
    public Long getSellDayAge() {
        return getLong(D_SellDayAge);
    }

    /**
     * 设置出售体重
     * 
     * @param SELL_WEIGHT
     */
    public void setSellWeight(Double value) {
        set(D_SellWeight, value);
    }

    /**
     * 获取出售体重
     * 
     * @return SELL_WEIGHT
     */
    public Double getSellWeight() {
        return getDouble(D_SellWeight);
    }

    /**
     * 设置料肉比
     * 
     * @param FRC
     */
    public void setFrc(Double value) {
        set(D_Frc, value);
    }

    /**
     * 获取料肉比
     * 
     * @return FRC
     */
    public Double getFrc() {
        return getDouble(D_Frc);
    }

    /**
     * 设置boarId
     * 
     * @param BOAR_ID
     */
    public void setBoarId(Long value) {
        set(D_BoarId, value);
    }

    /**
     * 获取boarId
     * 
     * @return BOAR_ID
     */
    public Long getBoarId() {
        return getLong(D_BoarId);
    }

    /**
     * 设置sowId
     * 
     * @param SOW_ID
     */
    public void setSowId(Long value) {
        set(D_SowId, value);
    }

    /**
     * 获取sowId
     * 
     * @return SOW_ID
     */
    public Long getSowId() {
        return getLong(D_SowId);
    }

    /**
     * 设置种公猪主数据
     * 
     * @param STOCK_BOAR_ID
     */
    public void setStockBoarId(Long value) {
        set(D_StockBoarId, value);
    }

    /**
     * 获取种公猪主数据
     * 
     * @return STOCK_BOAR_ID
     */
    public Long getStockBoarId() {
        return getLong(D_StockBoarId);
    }

    /**
     * 设置broodSowId
     * 
     * @param BROOD_SOW_ID
     */
    public void setBroodSowId(Long value) {
        set(D_BroodSowId, value);
    }

    /**
     * 获取broodSowId
     * 
     * @return BROOD_SOW_ID
     */
    public Long getBroodSowId() {
        return getLong(D_BroodSowId);
    }

    /**
     * 设置是否选种 0是 1否
     * 
     * @param IS_SELECT
     */
    public void setIsSelect(String value) {
        set(D_IsSelect, value);
    }

    /**
     * 获取是否选种 0是 1否
     * 
     * @return IS_SELECT
     */
    public String getIsSelect() {
        return getString(D_IsSelect);
    }

    /**
     * 设置分娩区分性别
     * 
     * @param IS_DIF_SEX
     */
    public void setIsDifSex(String value) {
        set(D_IsDifSex, value);
    }

    /**
     * 获取分娩区分性别
     * 
     * @return IS_DIF_SEX
     */
    public String getIsDifSex() {
        return getString(D_IsDifSex);
    }

    /**
     * 设置是否留种 0是 1否
     * 
     * @param IS_SEED
     */
    public void setIsSeed(String value) {
        set(D_IsSeed, value);
    }

    /**
     * 获取是否留种 0是 1否
     * 
     * @return IS_SEED
     */
    public String getIsSeed() {
        return getString(D_IsSeed);
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
        propertes.add("materialId");
        propertes.add("breedId");
        propertes.add("strain");
        propertes.add("bodyCondition");
        propertes.add("color");
        propertes.add("rightTeatNum");
        propertes.add("leftTeatNum");
        propertes.add("birthWeight");
        propertes.add("enterDayAge");
        propertes.add("enterWeight");
        propertes.add("mewDayAge");
        propertes.add("mewWeight");
        propertes.add("childCareDayAge");
        propertes.add("childCareWeight");
        propertes.add("fattenDayAge");
        propertes.add("fattenWeight");
        propertes.add("sellDayAge");
        propertes.add("sellWeight");
        propertes.add("frc");
        propertes.add("boarId");
        propertes.add("sowId");
        propertes.add("stockBoarId");
        propertes.add("broodSowId");
        propertes.add("isSelect");
        propertes.add("isDifSex");
        propertes.add("isSeed");
    }

    @Override
    public List<String> getPropertes() {
        return propertes;
    }

}
