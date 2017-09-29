package xn.core.model.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:15:39
 *       表：CD_L_BCODE
 */
public class BcodeModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 6018890526534843394L;

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

    // 编码类型ID
    private static final String D_BcodeTypeId="bcodeTypeId";
	 //serialLength 
    private static final String D_SerialLength="serialLength";
	 //serialMax 
    private static final String D_SerialMax="serialMax";
	 //serialMin 
    private static final String D_SerialMin="serialMin";
	 //prifixCode 
    private static final String D_PrifixCode="prifixCode";
	 //houseId 
    private static final String D_HouseId="houseId";
	 //isUseBdate 
    private static final String D_IsUseBdate="isUseBdate";
	 //lastNum 
    private static final String D_LastNum="lastNum";
	 //nextSeial 
    private static final String D_NextSeial="nextSeial";

    private static final List<String> propertes = new ArrayList<>();
	

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
     * 设置编码类型ID
     * 
     * @param BCODE_TYPE_ID
     */
	public void setBcodeTypeId(Long value) {
        set(D_BcodeTypeId,value);
    }
	
	/**
     * 获取编码类型ID
     * 
     * @return BCODE_TYPE_ID
     */
    public Long getBcodeTypeId() {
        return getLong(D_BcodeTypeId);
    }

    /**
     * 设置serialLength
     * 
     * @param SERIAL_LENGTH
     */
	public void setSerialLength(Long value) {
        set(D_SerialLength,value);
    }
	
	/**
     * 获取serialLength
     * 
     * @return SERIAL_LENGTH
     */
    public Long getSerialLength() {
        return getLong(D_SerialLength);
    }

    /**
     * 设置serialMax
     * 
     * @param SERIAL_MAX
     */
	public void setSerialMax(Long value) {
        set(D_SerialMax,value);
    }
	
	/**
     * 获取serialMax
     * 
     * @return SERIAL_MAX
     */
    public Long getSerialMax() {
        return getLong(D_SerialMax);
    }

    /**
     * 设置serialMin
     * 
     * @param SERIAL_MIN
     */
	public void setSerialMin(Long value) {
        set(D_SerialMin,value);
    }
	
	/**
     * 获取serialMin
     * 
     * @return SERIAL_MIN
     */
    public Long getSerialMin() {
        return getLong(D_SerialMin);
    }

	/**
     * 设置prifixCode
     * 
     * @param PRIFIX_CODE
     */
	public void setPrifixCode(String value) {
        set(D_PrifixCode,value);
    }
	
    /**
     * 获取prifixCode
     * 
     * @return PRIFIX_CODE
     */
    public String getPrifixCode() {
        return getString(D_PrifixCode);
    }

    /**
     * 设置houseId
     * 
     * @param HOUSE_ID
     */
	public void setHouseId(Long value) {
        set(D_HouseId,value);
    }
	
	/**
     * 获取houseId
     * 
     * @return HOUSE_ID
     */
    public Long getHouseId() {
        return getLong(D_HouseId);
    }

	/**
     * 设置isUseBdate
     * 
     * @param IS_USE_BDATE
     */
	public void setIsUseBdate(String value) {
        set(D_IsUseBdate,value);
    }
	
    /**
     * 获取isUseBdate
     * 
     * @return IS_USE_BDATE
     */
    public String getIsUseBdate() {
        return getString(D_IsUseBdate);
    }

	/**
     * 设置lastNum
     * 
     * @param LAST_NUM
     */
	public void setLastNum(String value) {
        set(D_LastNum,value);
    }
	
    /**
     * 获取lastNum
     * 
     * @return LAST_NUM
     */
    public String getLastNum() {
        return getString(D_LastNum);
    }

	/**
     * 设置nextSeial
     * 
     * @param NEXT_SEIAL
     */
	public void setNextSeial(String value) {
        set(D_NextSeial,value);
    }
	
    /**
     * 获取nextSeial
     * 
     * @return NEXT_SEIAL
     */
    public String getNextSeial() {
        return getString(D_NextSeial);
    }
	
	
	public List<String> getPropertes() {
        return propertes;
	}

    static {
        propertes.add(D_RowId);
        propertes.add(D_SortNbr);
        propertes.add(D_Status);
        propertes.add(D_DeletedFlag);
        propertes.add(D_OriginFlag);
        propertes.add(D_OriginApp);
        propertes.add(D_Notes);
        propertes.add(D_FarmId);
        propertes.add(D_CompanyId);
        propertes.add(D_BcodeTypeId);
        propertes.add(D_SerialLength);
        propertes.add(D_SerialMax);
        propertes.add(D_SerialMin);
        propertes.add(D_PrifixCode);
        propertes.add(D_HouseId);
        propertes.add(D_IsUseBdate);
        propertes.add(D_LastNum);
        propertes.add(D_NextSeial);
    }
}




