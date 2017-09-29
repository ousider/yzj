package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-3-12 21:55:13
 *       表：CD_O_SALE_ACCOUNT_ITEM
 */
public class SaleAccountItemModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 5690975224419456755L;

    // 存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
    // 行号: 系统保留字段，标识一条数据记录。
    private static final String D_RowId="rowId";

    // 排序号
    private static final String D_SortNbr="sortNbr";

    // 状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    private static final String D_Status="status";

    // 记录删除标志: [0]-未删除;[1]-逻辑删除
    private static final String D_DeletedFlag="deletedFlag";

    // 数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    private static final String D_OriginFlag="originFlag";

    // 数据来源应用的代码
    private static final String D_OriginApp="originApp";

    // 备注
    private static final String D_Notes="notes";

    // 客户ID
    private static final String D_CustomerId="customerId";

    // 项目类别
    private static final String D_ItemType="itemType";

    // 项目阶段
    private static final String D_ItemStage="itemStage";

    // 项目名称
    private static final String D_ItemName="itemName";

    // 项目代码
    private static final String D_ItemCode="itemCode";

    // 是否必填
    private static final String D_IsRequire="isRequire";

    // 是否可用
    private static final String D_IsAvailable="isAvailable";

    // 不可用时间
    private static final String D_DisabledDate="disabledDate";

    // 奖惩标准（每头）
    private static final String D_RewardPunishStandard="rewardPunishStandard";
	

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
     * 设置客户ID
     * 
     * @param CUSTOMER_ID
     */
	public void setCustomerId(Long value) {
        set(D_CustomerId,value);
    }
	
	/**
     * 获取客户ID
     * 
     * @return CUSTOMER_ID
     */
    public Long getCustomerId() {
        return getLong(D_CustomerId);
    }

	/**
     * 设置项目类别
     * 
     * @param ITEM_TYPE
     */
	public void setItemType(String value) {
        set(D_ItemType,value);
    }
	
    /**
     * 获取项目类别
     * 
     * @return ITEM_TYPE
     */
    public String getItemType() {
        return getString(D_ItemType);
    }

	/**
     * 设置项目阶段
     * 
     * @param ITEM_STAGE
     */
	public void setItemStage(String value) {
        set(D_ItemStage,value);
    }
	
    /**
     * 获取项目阶段
     * 
     * @return ITEM_STAGE
     */
    public String getItemStage() {
        return getString(D_ItemStage);
    }

	/**
     * 设置项目名称
     * 
     * @param ITEM_NAME
     */
	public void setItemName(String value) {
        set(D_ItemName,value);
    }
	
    /**
     * 获取项目名称
     * 
     * @return ITEM_NAME
     */
    public String getItemName() {
        return getString(D_ItemName);
    }

	/**
     * 设置项目代码
     * 
     * @param ITEM_CODE
     */
	public void setItemCode(String value) {
        set(D_ItemCode,value);
    }
	
    /**
     * 获取项目代码
     * 
     * @return ITEM_CODE
     */
    public String getItemCode() {
        return getString(D_ItemCode);
    }

	/**
     * 设置是否必填
     * 
     * @param IS_REQUIRE
     */
	public void setIsRequire(String value) {
        set(D_IsRequire,value);
    }
	
    /**
     * 获取是否必填
     * 
     * @return IS_REQUIRE
     */
    public String getIsRequire() {
        return getString(D_IsRequire);
    }

	/**
     * 设置是否可用
     * 
     * @param IS_AVAILABLE
     */
	public void setIsAvailable(String value) {
        set(D_IsAvailable,value);
    }
	
    /**
     * 获取是否可用
     * 
     * @return IS_AVAILABLE
     */
    public String getIsAvailable() {
        return getString(D_IsAvailable);
    }

	/**
     * 设置不可用时间
     * 
     * @param DISABLED_DATE
     */
	public void setDisabledDate(Date value) {
        set(D_DisabledDate,value);
    }
	
    /**
     * 获取不可用时间
     * 
     * @return DISABLED_DATE
     */
    public Date getDisabledDate() {
        return getDate(D_DisabledDate);
    }

	/**
     * 设置奖惩标准（每头）
     * 
     * @param REWARD_PUNISH_STANDARD
     */
	public void setRewardPunishStandard(Double value) {
        set(D_RewardPunishStandard,value);
    }
	
    /**
     * 获取奖惩标准（每头）
     * 
     * @return REWARD_PUNISH_STANDARD
     */
    public Double getRewardPunishStandard() {
        return getDouble(D_RewardPunishStandard);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("customerId");
        propertes.add("itemType");
        propertes.add("itemStage");
        propertes.add("itemName");
        propertes.add("itemCode");
        propertes.add("isRequire");
        propertes.add("isAvailable");
        propertes.add("disabledDate");
        propertes.add("rewardPunishStandard");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




