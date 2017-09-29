package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:14
 *       表：CD_O_SPERM
 */
public class SpermModel  extends BaseDataModel implements Serializable{
 
    private static final long serialVersionUID = 3146392544325753989L;

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

    // 对应公猪主数据ID
    private static final String D_BoarId="boarId";

    // 精子活力（%）
    private static final String D_SpermMotility="spermMotility";

    // 精子密度
    private static final String D_SpermDensity="spermDensity";

    // 畸形率（%）
    private static final String D_AbnormationRate="abnormationRate";

    // 凝聚度
    private static final String D_Cohesion="cohesion";

    // 颜色
    private static final String D_Color="color";

    // 保存温度
    private static final String D_Tstg="tstg";

    // 保质期（天）
    private static final String D_ShelfLife="shelfLife";

    // 包装
    private static final String D_Pack="pack";
	

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
     * 设置对应公猪主数据ID
     * 
     * @param BOAR_ID
     */
	public void setBoarId(Long value) {
        set(D_BoarId,value);
    }
	
	/**
     * 获取对应公猪主数据ID
     * 
     * @return BOAR_ID
     */
    public Long getBoarId() {
        return getLong(D_BoarId);
    }

	/**
     * 设置精子活力（%）
     * 
     * @param SPERM_MOTILITY
     */
	public void setSpermMotility(Double value) {
        set(D_SpermMotility,value);
    }
	
    /**
     * 获取精子活力（%）
     * 
     * @return SPERM_MOTILITY
     */
    public Double getSpermMotility() {
        return getDouble(D_SpermMotility);
    }

	/**
     * 设置精子密度
     * 
     * @param SPERM_DENSITY
     */
	public void setSpermDensity(Double value) {
        set(D_SpermDensity,value);
    }
	
    /**
     * 获取精子密度
     * 
     * @return SPERM_DENSITY
     */
    public Double getSpermDensity() {
        return getDouble(D_SpermDensity);
    }

	/**
     * 设置畸形率（%）
     * 
     * @param ABNORMATION_RATE
     */
	public void setAbnormationRate(Double value) {
        set(D_AbnormationRate,value);
    }
	
    /**
     * 获取畸形率（%）
     * 
     * @return ABNORMATION_RATE
     */
    public Double getAbnormationRate() {
        return getDouble(D_AbnormationRate);
    }

	/**
     * 设置凝聚度
     * 
     * @param COHESION
     */
	public void setCohesion(Double value) {
        set(D_Cohesion,value);
    }
	
    /**
     * 获取凝聚度
     * 
     * @return COHESION
     */
    public Double getCohesion() {
        return getDouble(D_Cohesion);
    }

	/**
     * 设置颜色
     * 
     * @param COLOR
     */
	public void setColor(String value) {
        set(D_Color,value);
    }
	
    /**
     * 获取颜色
     * 
     * @return COLOR
     */
    public String getColor() {
        return getString(D_Color);
    }

	/**
     * 设置保存温度
     * 
     * @param TSTG
     */
	public void setTstg(Double value) {
        set(D_Tstg,value);
    }
	
    /**
     * 获取保存温度
     * 
     * @return TSTG
     */
    public Double getTstg() {
        return getDouble(D_Tstg);
    }

    /**
     * 设置保质期（天）
     * 
     * @param SHELF_LIFE
     */
	public void setShelfLife(Long value) {
        set(D_ShelfLife,value);
    }
	
	/**
     * 获取保质期（天）
     * 
     * @return SHELF_LIFE
     */
    public Long getShelfLife() {
        return getLong(D_ShelfLife);
    }

	/**
     * 设置包装
     * 
     * @param PACK
     */
	public void setPack(String value) {
        set(D_Pack,value);
    }
	
    /**
     * 获取包装
     * 
     * @return PACK
     */
    public String getPack() {
        return getString(D_Pack);
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
	        setPropertes(D_BoarId);
	        setPropertes(D_SpermMotility);
	        setPropertes(D_SpermDensity);
	        setPropertes(D_AbnormationRate);
	        setPropertes(D_Cohesion);
	        setPropertes(D_Color);
	        setPropertes(D_Tstg);
	        setPropertes(D_ShelfLife);
	        setPropertes(D_Pack);
	    }
	    return super.getPropertes();
	}

}




