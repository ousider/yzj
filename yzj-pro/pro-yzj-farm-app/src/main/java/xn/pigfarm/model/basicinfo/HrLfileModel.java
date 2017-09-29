package xn.pigfarm.model.basicinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-13 12:01:52
 *       表：HR_L_FILE 
 */
public class HrLfileModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = 4969793277002205900L;

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
	 //主表名称（例：hr_l_papers） 
    private static final String D_MainTable="mainTable";
	 //主表row_id 
    private static final String D_MainId="mainId";
	 //系统文件全名称 
    private static final String D_SysFileName="sysFileName";
	 //文件路径 
    private static final String D_FilePath="filePath";
	 //文件夹相对路径 
    private static final String D_RelativePath="relativePath";
	

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
    * 设置主表名称（例：hr_l_papers）
    * @param MAIN_TABLE
    */
	public void setMainTable(String value) {
        set(D_MainTable,value);
    }
	
   /**
    * 获取主表名称（例：hr_l_papers）
    * @return MAIN_TABLE
    */
    public String getMainTable() {
        return getString(D_MainTable);
    }

   /**
    * 设置主表row_id
    * @param MAIN_ID
    */
	public void setMainId(Long value) {
        set(D_MainId,value);
    }
	
	/**
    * 获取主表row_id
    * @return MAIN_ID
    */
    public Long getMainId() {
        return getLong(D_MainId);
    }

	/**
    * 设置系统文件全名称
    * @param SYS_FILE_NAME
    */
	public void setSysFileName(String value) {
        set(D_SysFileName,value);
    }
	
   /**
    * 获取系统文件全名称
    * @return SYS_FILE_NAME
    */
    public String getSysFileName() {
        return getString(D_SysFileName);
    }

	/**
    * 设置文件路径
    * @param FILE_PATH
    */
	public void setFilePath(String value) {
        set(D_FilePath,value);
    }
	
   /**
    * 获取文件路径
    * @return FILE_PATH
    */
    public String getFilePath() {
        return getString(D_FilePath);
    }

	/**
    * 设置文件夹相对路径
    * @param RELATIVE_PATH
    */
	public void setRelativePath(String value) {
        set(D_RelativePath,value);
    }
	
   /**
    * 获取文件夹相对路径
    * @return RELATIVE_PATH
    */
    public String getRelativePath() {
        return getString(D_RelativePath);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("sortNbr");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("originFlag");
        propertes.add("originApp");
        propertes.add("notes");
        propertes.add("mainTable");
        propertes.add("mainId");
        propertes.add("sysFileName");
        propertes.add("filePath");
        propertes.add("relativePath");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




