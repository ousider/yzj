package xn.pigfarm.model.supplychian;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-12-15 19:58:59
 *       表：SC_M_REPLACE_AND_PACKAGE_DETAIL 
 */
public class ReplaceAndPackageDetailModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -6697295240185323005L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //主表ID 
    private static final String D_MainId="mainId";
	 //物料主数据ID 
    private static final String D_MaterialId="materialId";
	 //物料主数据比例数 
    private static final String D_MainProportionQty="mainProportionQty";
	 //分组模式[1]-互相替换[2]-固定赠料[3]-固定套餐 
    private static final String D_GroupModel="groupModel";
	 //购/赠料区分[1]-购料[2]赠料 
    private static final String D_PurchaseOrFree="purchaseOrFree";
	 //是否必须赠料[Y]-必须赠料（添加购料时自动带出）[N]-手动添加赠料时可见 
    private static final String D_MustBeFree="mustBeFree";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	

   /**
    * 设置行号: 系统保留字段，标识一条数据记录。
    * @param ROW_ID
    */
	public void setRowId(Long value) {
        set(D_RowId,value);
    }
	
	/**
    * 获取行号: 系统保留字段，标识一条数据记录。
    * @return ROW_ID
    */
    public Long getRowId() {
        return getLong(D_RowId);
    }

	/**
    * 设置[0]-未删除;[1]-逻辑删除
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取[0]-未删除;[1]-逻辑删除
    * @return DELETED_FLAG
    */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
    * 设置备注
    * @param NOTES
    */
	public void setNotes(String value) {
        set(D_Notes,value);
    }
	
   /**
    * 获取备注
    * @return NOTES
    */
    public String getNotes() {
        return getString(D_Notes);
    }

   /**
    * 设置主表ID
    * @param MAIN_ID
    */
	public void setMainId(Long value) {
        set(D_MainId,value);
    }
	
	/**
    * 获取主表ID
    * @return MAIN_ID
    */
    public Long getMainId() {
        return getLong(D_MainId);
    }

   /**
    * 设置物料主数据ID
    * @param MATERIAL_ID
    */
	public void setMaterialId(Long value) {
        set(D_MaterialId,value);
    }
	
	/**
    * 获取物料主数据ID
    * @return MATERIAL_ID
    */
    public Long getMaterialId() {
        return getLong(D_MaterialId);
    }

   /**
    * 设置物料主数据比例数
    * @param MAIN_PROPORTION_QTY
    */
	public void setMainProportionQty(Long value) {
        set(D_MainProportionQty,value);
    }
	
	/**
    * 获取物料主数据比例数
    * @return MAIN_PROPORTION_QTY
    */
    public Long getMainProportionQty() {
        return getLong(D_MainProportionQty);
    }

	/**
    * 设置分组模式[1]-互相替换[2]-固定赠料[3]-固定套餐
    * @param GROUP_MODEL
    */
	public void setGroupModel(String value) {
        set(D_GroupModel,value);
    }
	
   /**
    * 获取分组模式[1]-互相替换[2]-固定赠料[3]-固定套餐
    * @return GROUP_MODEL
    */
    public String getGroupModel() {
        return getString(D_GroupModel);
    }

	/**
    * 设置购/赠料区分[1]-购料[2]赠料
    * @param PURCHASE_OR_FREE
    */
	public void setPurchaseOrFree(String value) {
        set(D_PurchaseOrFree,value);
    }
	
   /**
    * 获取购/赠料区分[1]-购料[2]赠料
    * @return PURCHASE_OR_FREE
    */
    public String getPurchaseOrFree() {
        return getString(D_PurchaseOrFree);
    }

	/**
    * 设置是否必须赠料[Y]-必须赠料（添加购料时自动带出）[N]-手动添加赠料时可见
    * @param MUST_BE_FREE
    */
	public void setMustBeFree(String value) {
        set(D_MustBeFree,value);
    }
	
   /**
    * 获取是否必须赠料[Y]-必须赠料（添加购料时自动带出）[N]-手动添加赠料时可见
    * @return MUST_BE_FREE
    */
    public String getMustBeFree() {
        return getString(D_MustBeFree);
    }

   /**
    * 设置创建人
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建人
    * @return CREATE_ID
    */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
    * 设置创建日期
    * @param CREATE_DATE
    */
    public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建日期
    * @return CREATE_DATE
    */
    public Date getCreateDate() {
        return getTimestamp(D_CreateDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("mainId");
        propertes.add("materialId");
        propertes.add("mainProportionQty");
        propertes.add("groupModel");
        propertes.add("purchaseOrFree");
        propertes.add("mustBeFree");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




