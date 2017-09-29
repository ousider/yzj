package xn.core.model.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-11-4 13:42:28
 *       表：SYS_L_ACTION_TRACK 
 */
public class ActionTrackModel  extends BaseDataModel implements Serializable{
    
	private static final long serialVersionUID = -7635754308963373621L;

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
	 //猪场id 
    private static final String D_FarmId="farmId";
	 //来访标识 
    private static final String D_ActobjId="actobjId";
	 //来访时间 
    private static final String D_StartTime="startTime";
	 //结束时间 
    private static final String D_OverTime="overTime";
	 //来访IP 
    private static final String D_Ip="ip";
	 //浏览器 
    private static final String D_Explorer="explorer";
	 //浏览器版本 
    private static final String D_ExplorerVer="explorerVer";
	 //客户端 
    private static final String D_Client="client";
	 //客户端语言 
    private static final String D_Language="language";
	 //国家 
    private static final String D_Country="country";
	 //省份 
    private static final String D_Province="province";
	 //城市 
    private static final String D_City="city";
	 //区域 
    private static final String D_Area="area";
	 //屏宽 
    private static final String D_ScreenWidth="screenWidth";
	 //屏高 
    private static final String D_ScreenHeight="screenHeight";
	 //MAC地址 
    private static final String D_Mac="mac";
	

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
    * 设置猪场id
    * @param FARM_ID
    */
	public void setFarmId(Long value) {
        set(D_FarmId,value);
    }
	
	/**
    * 获取猪场id
    * @return FARM_ID
    */
    public Long getFarmId() {
        return getLong(D_FarmId);
    }

	/**
    * 设置来访标识
    * @param ACTOBJ_ID
    */
	public void setActobjId(Long value) {
        set(D_ActobjId,value);
    }
	
   /**
    * 获取来访标识
    * @return ACTOBJ_ID
    */
    public Long getActobjId() {
        return getLong(D_ActobjId);
    }

	/**
    * 设置来访时间
    * @param START_TIME
    */
	public void setStartTime(Timestamp value) {
        set(D_StartTime,value);
    }
	
   /**
    * 获取来访时间
    * @return START_TIME
    */
    public Timestamp getStartTime() {
        return getTimestamp(D_StartTime);
    }

	/**
    * 设置结束时间
    * @param OVER_TIME
    */
	public void setOverTime(Timestamp value) {
        set(D_OverTime,value);
    }
	
   /**
    * 获取结束时间
    * @return OVER_TIME
    */
    public Timestamp getOverTime() {
        return getTimestamp(D_OverTime);
    }

	/**
    * 设置来访IP
    * @param IP
    */
	public void setIp(String value) {
        set(D_Ip,value);
    }
	
   /**
    * 获取来访IP
    * @return IP
    */
    public String getIp() {
        return getString(D_Ip);
    }

	/**
    * 设置浏览器
    * @param EXPLORER
    */
	public void setExplorer(String value) {
        set(D_Explorer,value);
    }
	
   /**
    * 获取浏览器
    * @return EXPLORER
    */
    public String getExplorer() {
        return getString(D_Explorer);
    }

	/**
    * 设置浏览器版本
    * @param EXPLORER_VER
    */
	public void setExplorerVer(String value) {
        set(D_ExplorerVer,value);
    }
	
   /**
    * 获取浏览器版本
    * @return EXPLORER_VER
    */
    public String getExplorerVer() {
        return getString(D_ExplorerVer);
    }

	/**
    * 设置客户端
    * @param CLIENT
    */
	public void setClient(String value) {
        set(D_Client,value);
    }
	
   /**
    * 获取客户端
    * @return CLIENT
    */
    public String getClient() {
        return getString(D_Client);
    }

	/**
    * 设置客户端语言
    * @param LANGUAGE
    */
	public void setLanguage(String value) {
        set(D_Language,value);
    }
	
   /**
    * 获取客户端语言
    * @return LANGUAGE
    */
    public String getLanguage() {
        return getString(D_Language);
    }

	/**
    * 设置国家
    * @param COUNTRY
    */
	public void setCountry(String value) {
        set(D_Country,value);
    }
	
   /**
    * 获取国家
    * @return COUNTRY
    */
    public String getCountry() {
        return getString(D_Country);
    }

	/**
    * 设置省份
    * @param PROVINCE
    */
	public void setProvince(String value) {
        set(D_Province,value);
    }
	
   /**
    * 获取省份
    * @return PROVINCE
    */
    public String getProvince() {
        return getString(D_Province);
    }

	/**
    * 设置城市
    * @param CITY
    */
	public void setCity(String value) {
        set(D_City,value);
    }
	
   /**
    * 获取城市
    * @return CITY
    */
    public String getCity() {
        return getString(D_City);
    }

	/**
    * 设置区域
    * @param AREA
    */
	public void setArea(String value) {
        set(D_Area,value);
    }
	
   /**
    * 获取区域
    * @return AREA
    */
    public String getArea() {
        return getString(D_Area);
    }

	/**
    * 设置屏宽
    * @param SCREEN_WIDTH
    */
	public void setScreenWidth(String value) {
        set(D_ScreenWidth,value);
    }
	
   /**
    * 获取屏宽
    * @return SCREEN_WIDTH
    */
    public String getScreenWidth() {
        return getString(D_ScreenWidth);
    }

	/**
    * 设置屏高
    * @param SCREEN_HEIGHT
    */
	public void setScreenHeight(String value) {
        set(D_ScreenHeight,value);
    }
	
   /**
    * 获取屏高
    * @return SCREEN_HEIGHT
    */
    public String getScreenHeight() {
        return getString(D_ScreenHeight);
    }

	/**
    * 设置MAC地址
    * @param MAC
    */
	public void setMac(String value) {
        set(D_Mac,value);
    }
	
   /**
    * 获取MAC地址
    * @return MAC
    */
    public String getMac() {
        return getString(D_Mac);
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
        propertes.add("actobjId");
        propertes.add("startTime");
        propertes.add("overTime");
        propertes.add("ip");
        propertes.add("explorer");
        propertes.add("explorerVer");
        propertes.add("client");
        propertes.add("language");
        propertes.add("country");
        propertes.add("province");
        propertes.add("city");
        propertes.add("area");
        propertes.add("screenWidth");
        propertes.add("screenHeight");
        propertes.add("mac");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




