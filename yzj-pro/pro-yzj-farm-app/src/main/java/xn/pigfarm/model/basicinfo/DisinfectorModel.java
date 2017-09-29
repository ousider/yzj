package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-8-18 9:50:06
 *       表：CD_O_DISINFECTOR
 */
public class DisinfectorModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 895496122885427115L;

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

    // 型号
    private static final String D_DisinfectorModel="disinfectorModel";

    // 保质期
    private static final String D_ShelfLife="shelfLife";

    // 性状
    private static final String D_Apperance="apperance";

    // 包装
    private static final String D_Pack="pack";

    // 类别
    private static final String D_DisinfectorType="disinfectorType";

    // 作用机理
    private static final String D_Effect="effect";

    // 优点
    private static final String D_Advantage="advantage";

    // 缺点
    private static final String D_Disadvantage="disadvantage";

    // 使用方法
    private static final String D_UsageDosage="usageDosage";

    // 注意事项
    private static final String D_Notice="notice";

    // 细菌繁殖体
    private static final String D_Xjfzt="xjfzt";

    // 分支杆菌
    private static final String D_Fzgj="fzgj";

    // 真菌
    private static final String D_Zj="zj";

    // 包膜病毒
    private static final String D_Bmbd="bmbd";

    // 非包膜病毒
    private static final String D_Fbmbd="fbmbd";

    // 孢子
    private static final String D_Bz="bz";

    // 有机物存在时的效果
    private static final String D_Yjw="yjw";

    // 硬水存在时的效果
    private static final String D_Ys="ys";

    // 有肥皂或洗涤剂存在时的效果
    private static final String D_Xdj="xdj";
	

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
     * 设置型号
     * 
     * @param DISINFECTOR_MODEL
     */
	public void setDisinfectorModel(String value) {
        set(D_DisinfectorModel,value);
    }
	
    /**
     * 获取型号
     * 
     * @return DISINFECTOR_MODEL
     */
    public String getDisinfectorModel() {
        return getString(D_DisinfectorModel);
    }

	/**
     * 设置保质期
     * 
     * @param SHELF_LIFE
     */
	public void setShelfLife(String value) {
        set(D_ShelfLife,value);
    }
	
    /**
     * 获取保质期
     * 
     * @return SHELF_LIFE
     */
    public String getShelfLife() {
        return getString(D_ShelfLife);
    }

	/**
     * 设置性状
     * 
     * @param APPERANCE
     */
	public void setApperance(String value) {
        set(D_Apperance,value);
    }
	
    /**
     * 获取性状
     * 
     * @return APPERANCE
     */
    public String getApperance() {
        return getString(D_Apperance);
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

	/**
     * 设置类别
     * 
     * @param DISINFECTOR_TYPE
     */
	public void setDisinfectorType(String value) {
        set(D_DisinfectorType,value);
    }
	
    /**
     * 获取类别
     * 
     * @return DISINFECTOR_TYPE
     */
    public String getDisinfectorType() {
        return getString(D_DisinfectorType);
    }

	/**
     * 设置作用机理
     * 
     * @param EFFECT
     */
	public void setEffect(String value) {
        set(D_Effect,value);
    }
	
    /**
     * 获取作用机理
     * 
     * @return EFFECT
     */
    public String getEffect() {
        return getString(D_Effect);
    }

	/**
     * 设置优点
     * 
     * @param ADVANTAGE
     */
	public void setAdvantage(String value) {
        set(D_Advantage,value);
    }
	
    /**
     * 获取优点
     * 
     * @return ADVANTAGE
     */
    public String getAdvantage() {
        return getString(D_Advantage);
    }

	/**
     * 设置缺点
     * 
     * @param DISADVANTAGE
     */
	public void setDisadvantage(String value) {
        set(D_Disadvantage,value);
    }
	
    /**
     * 获取缺点
     * 
     * @return DISADVANTAGE
     */
    public String getDisadvantage() {
        return getString(D_Disadvantage);
    }

	/**
     * 设置使用方法
     * 
     * @param USAGE_DOSAGE
     */
	public void setUsageDosage(String value) {
        set(D_UsageDosage,value);
    }
	
    /**
     * 获取使用方法
     * 
     * @return USAGE_DOSAGE
     */
    public String getUsageDosage() {
        return getString(D_UsageDosage);
    }

	/**
     * 设置注意事项
     * 
     * @param NOTICE
     */
	public void setNotice(String value) {
        set(D_Notice,value);
    }
	
    /**
     * 获取注意事项
     * 
     * @return NOTICE
     */
    public String getNotice() {
        return getString(D_Notice);
    }

	/**
     * 设置细菌繁殖体
     * 
     * @param XJFZT
     */
	public void setXjfzt(String value) {
        set(D_Xjfzt,value);
    }
	
    /**
     * 获取细菌繁殖体
     * 
     * @return XJFZT
     */
    public String getXjfzt() {
        return getString(D_Xjfzt);
    }

	/**
     * 设置分支杆菌
     * 
     * @param FZGJ
     */
	public void setFzgj(String value) {
        set(D_Fzgj,value);
    }
	
    /**
     * 获取分支杆菌
     * 
     * @return FZGJ
     */
    public String getFzgj() {
        return getString(D_Fzgj);
    }

	/**
     * 设置真菌
     * 
     * @param ZJ
     */
	public void setZj(String value) {
        set(D_Zj,value);
    }
	
    /**
     * 获取真菌
     * 
     * @return ZJ
     */
    public String getZj() {
        return getString(D_Zj);
    }

	/**
     * 设置包膜病毒
     * 
     * @param BMBD
     */
	public void setBmbd(String value) {
        set(D_Bmbd,value);
    }
	
    /**
     * 获取包膜病毒
     * 
     * @return BMBD
     */
    public String getBmbd() {
        return getString(D_Bmbd);
    }

	/**
     * 设置非包膜病毒
     * 
     * @param FBMBD
     */
	public void setFbmbd(String value) {
        set(D_Fbmbd,value);
    }
	
    /**
     * 获取非包膜病毒
     * 
     * @return FBMBD
     */
    public String getFbmbd() {
        return getString(D_Fbmbd);
    }

	/**
     * 设置孢子
     * 
     * @param BZ
     */
	public void setBz(String value) {
        set(D_Bz,value);
    }
	
    /**
     * 获取孢子
     * 
     * @return BZ
     */
    public String getBz() {
        return getString(D_Bz);
    }

	/**
     * 设置有机物存在时的效果
     * 
     * @param YJW
     */
	public void setYjw(String value) {
        set(D_Yjw,value);
    }
	
    /**
     * 获取有机物存在时的效果
     * 
     * @return YJW
     */
    public String getYjw() {
        return getString(D_Yjw);
    }

	/**
     * 设置硬水存在时的效果
     * 
     * @param YS
     */
	public void setYs(String value) {
        set(D_Ys,value);
    }
	
    /**
     * 获取硬水存在时的效果
     * 
     * @return YS
     */
    public String getYs() {
        return getString(D_Ys);
    }

	/**
     * 设置有肥皂或洗涤剂存在时的效果
     * 
     * @param XDJ
     */
	public void setXdj(String value) {
        set(D_Xdj,value);
    }
	
    /**
     * 获取有肥皂或洗涤剂存在时的效果
     * 
     * @return XDJ
     */
    public String getXdj() {
        return getString(D_Xdj);
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
	        setPropertes(D_DisinfectorModel);
	        setPropertes(D_ShelfLife);
	        setPropertes(D_Apperance);
	        setPropertes(D_Pack);
	        setPropertes(D_DisinfectorType);
	        setPropertes(D_Effect);
	        setPropertes(D_Advantage);
	        setPropertes(D_Disadvantage);
	        setPropertes(D_UsageDosage);
	        setPropertes(D_Notice);
	        setPropertes(D_Xjfzt);
	        setPropertes(D_Fzgj);
	        setPropertes(D_Zj);
	        setPropertes(D_Bmbd);
	        setPropertes(D_Fbmbd);
	        setPropertes(D_Bz);
	        setPropertes(D_Yjw);
	        setPropertes(D_Ys);
	        setPropertes(D_Xdj);
	    }
	    return super.getPropertes();
	}

}




