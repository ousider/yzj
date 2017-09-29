package xn.pigfarm.model.hana;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-6-15 11:43:36
 *       表：SYS_HANA_DB_PROPERTIES 
 */
public class SysHanaDbPropertiesModel  extends BaseDataModel implements Serializable{

    private static final long serialVersionUID = -9133532715379608911L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。 
    private static final String D_Status="status";
	 //记录删除标志: [0]-未删除;[1]-逻辑删除 
    private static final String D_DeletedFlag="deletedFlag";
	 //备注 
    private static final String D_Notes="notes";
	 //数据源名称 
    private static final String D_BeanName="beanName";
	 //数据源地址及端口号 
    private static final String D_IpAndPort="ipAndPort";
	 //DB名 
    private static final String D_DbName="dbName";
	 //数据库账号 
    private static final String D_DbUserName="dbUserName";
	 //数据库密码 
    private static final String D_DbPassword="dbPassword";
	 //创建者 
    private static final String D_CreateId="createId";
	 //创建时间 
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
    * 设置状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取状态: 表示该对象实例的业务状态。通常用“Y/N”表示其是否有效，其他的状态相对复杂。
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
    * 设置记录删除标志: [0]-未删除;[1]-逻辑删除
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取记录删除标志: [0]-未删除;[1]-逻辑删除
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
    * 设置数据源名称
    * @param BEAN_NAME
    */
	public void setBeanName(String value) {
        set(D_BeanName,value);
    }
	
   /**
    * 获取数据源名称
    * @return BEAN_NAME
    */
    public String getBeanName() {
        return getString(D_BeanName);
    }

	/**
    * 设置数据源地址及端口号
    * @param IP_AND_PORT
    */
	public void setIpAndPort(String value) {
        set(D_IpAndPort,value);
    }
	
   /**
    * 获取数据源地址及端口号
    * @return IP_AND_PORT
    */
    public String getIpAndPort() {
        return getString(D_IpAndPort);
    }

	/**
    * 设置DB名
    * @param DB_NAME
    */
	public void setDbName(String value) {
        set(D_DbName,value);
    }
	
   /**
    * 获取DB名
    * @return DB_NAME
    */
    public String getDbName() {
        return getString(D_DbName);
    }

	/**
    * 设置数据库账号
    * @param DB_USER_NAME
    */
	public void setDbUserName(String value) {
        set(D_DbUserName,value);
    }
	
   /**
    * 获取数据库账号
    * @return DB_USER_NAME
    */
    public String getDbUserName() {
        return getString(D_DbUserName);
    }

	/**
    * 设置数据库密码
    * @param DB_PASSWORD
    */
	public void setDbPassword(String value) {
        set(D_DbPassword,value);
    }
	
   /**
    * 获取数据库密码
    * @return DB_PASSWORD
    */
    public String getDbPassword() {
        return getString(D_DbPassword);
    }

   /**
    * 设置创建者
    * @param CREATE_ID
    */
	public void setCreateId(Long value) {
        set(D_CreateId,value);
    }
	
	/**
    * 获取创建者
    * @return CREATE_ID
    */
    public Long getCreateId() {
        return getLong(D_CreateId);
    }

	/**
    * 设置创建时间
    * @param CREATE_DATE
    */
    public void setCreateDate(Date value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建时间
    * @return CREATE_DATE
    */
    public Date getCreateDate() {
        return getDate(D_CreateDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("notes");
        propertes.add("beanName");
        propertes.add("ipAndPort");
        propertes.add("dbName");
        propertes.add("dbUserName");
        propertes.add("dbPassword");
        propertes.add("createId");
        propertes.add("createDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




