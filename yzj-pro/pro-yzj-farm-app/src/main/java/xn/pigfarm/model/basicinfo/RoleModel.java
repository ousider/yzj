package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-12-14 16:55:01
 *       表：CD_O_ROLE 
 */
public class RoleModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 7299030546923094885L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //排序号 
    private static final String D_SortNbr="sortNbr";
	 //表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //[0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。 
    private static final String D_OriginFlag="originFlag";
	 //数据来源应用的代码 
    private static final String D_OriginApp="originApp";
	 //备注 
    private static final String D_Notes="notes";
	 //对应的公司ID 
    private static final String D_FarmId="farmId";
	 //companyId 
    private static final String D_CompanyId="companyId";
	 //角色编码 
    private static final String D_BusinessCode="businessCode";
	 //角色名称 
    private static final String D_RoleName="roleName";
	 //菜单模板ID 
    private static final String D_TemplateId="templateId";
	 //角色类型 
    private static final String D_RoleType="roleType";
	 //采购类型[1]-全部[2]-饲料[3]-兽药 
    private static final String D_PurchaseType="purchaseType";
	 //供应链价格显示 
    private static final String D_ShowPrice="showPrice";
	 //拦截Url 
    private static final String D_IterceptUrl="iterceptUrl";
	

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
    * 设置排序号
    * @param SORT_NBR
    */
	public void setSortNbr(Long value) {
        set(D_SortNbr,value);
    }
	
	/**
    * 获取排序号
    * @return SORT_NBR
    */
    public Long getSortNbr() {
        return getLong(D_SortNbr);
    }

	/**
    * 设置表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取表示该对象实例的业务状态             通常用“1/2”表示其是否有效，其他的状态相对复杂。
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
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
    * 设置数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @param ORIGIN_FLAG
    */
	public void setOriginFlag(String value) {
        set(D_OriginFlag,value);
    }
	
   /**
    * 获取数据来源的标志: []或[I]-(Input)系统录入;[O]-(Out)外部接口导入;[S]-(System)系统保留。本标志不能挪为它用。
    * @return ORIGIN_FLAG
    */
    public String getOriginFlag() {
        return getString(D_OriginFlag);
    }

	/**
    * 设置数据来源应用的代码
    * @param ORIGIN_APP
    */
	public void setOriginApp(String value) {
        set(D_OriginApp,value);
    }
	
   /**
    * 获取数据来源应用的代码
    * @return ORIGIN_APP
    */
    public String getOriginApp() {
        return getString(D_OriginApp);
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
    * 设置对应的公司ID
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取对应的公司ID
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

   /**
    * 设置companyId
    * @param COMPANY_ID
    */
	public void setCompanyId(Long value) {
        set(D_CompanyId,value);
    }
	
	/**
    * 获取companyId
    * @return COMPANY_ID
    */
    public Long getCompanyId() {
        return getLong(D_CompanyId);
    }

	/**
    * 设置角色编码
    * @param BUSINESS_CODE
    */
	public void setBusinessCode(String value) {
        set(D_BusinessCode,value);
    }
	
   /**
    * 获取角色编码
    * @return BUSINESS_CODE
    */
    public String getBusinessCode() {
        return getString(D_BusinessCode);
    }

	/**
    * 设置角色名称
    * @param ROLE_NAME
    */
	public void setRoleName(String value) {
        set(D_RoleName,value);
    }
	
   /**
    * 获取角色名称
    * @return ROLE_NAME
    */
    public String getRoleName() {
        return getString(D_RoleName);
    }

   /**
    * 设置菜单模板ID
    * @param TEMPLATE_ID
    */
	public void setTemplateId(Long value) {
        set(D_TemplateId,value);
    }
	
	/**
    * 获取菜单模板ID
    * @return TEMPLATE_ID
    */
    public Long getTemplateId() {
        return getLong(D_TemplateId);
    }

	/**
    * 设置角色类型
    * @param ROLE_TYPE
    */
	public void setRoleType(String value) {
        set(D_RoleType,value);
    }
	
   /**
    * 获取角色类型
    * @return ROLE_TYPE
    */
    public String getRoleType() {
        return getString(D_RoleType);
    }

	/**
    * 设置采购类型[1]-全部[2]-饲料[3]-兽药
    * @param PURCHASE_TYPE
    */
	public void setPurchaseType(String value) {
        set(D_PurchaseType,value);
    }
	
   /**
    * 获取采购类型[1]-全部[2]-饲料[3]-兽药
    * @return PURCHASE_TYPE
    */
    public String getPurchaseType() {
        return getString(D_PurchaseType);
    }

	/**
    * 设置供应链价格显示
    * @param SHOW_PRICE
    */
	public void setShowPrice(String value) {
        set(D_ShowPrice,value);
    }
	
   /**
    * 获取供应链价格显示
    * @return SHOW_PRICE
    */
    public String getShowPrice() {
        return getString(D_ShowPrice);
    }

	/**
    * 设置拦截Url
    * @param ITERCEPT_URL
    */
	public void setIterceptUrl(String value) {
        set(D_IterceptUrl,value);
    }
	
   /**
    * 获取拦截Url
    * @return ITERCEPT_URL
    */
    public String getIterceptUrl() {
        return getString(D_IterceptUrl);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("farmId");
        propertes.add("companyId");
        propertes.add("businessCode");
        propertes.add("roleName");
        propertes.add("templateId");
        propertes.add("roleType");
        propertes.add("purchaseType");
        propertes.add("showPrice");
        propertes.add("iterceptUrl");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




