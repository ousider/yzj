package xn.pigfarm.model.backend;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xn.core.model.BaseDataModel;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2017-7-5 15:11:26
 *       表：WE_M_PUSH_MESSAGE 
 */
public class WeMpushMessageModel  extends BaseDataModel implements Serializable{
    
    private static final long serialVersionUID = 4279351788273128779L;

    //存放类的属性值
    private static final List<String> propertes = new ArrayList<>();
    
	 //行号: 系统保留字段，标识一条数据记录。 
    private static final String D_RowId="rowId";
	 //状态 
    private static final String D_Status="status";
	 //标记 
    private static final String D_DeletedFlag="deletedFlag";
	 //消息代码 
    private static final String D_MessageCode="messageCode";
	 //消息标题 
    private static final String D_MessageTitle="messageTitle";
	 //消息类型（text、news） 
    private static final String D_MessageType="messageType";
	 //描述 
    private static final String D_Description="description";
	 //推送图片地址 
    private static final String D_PicUrl="picUrl";
	 //推送内容地址 
    private static final String D_ContentUrl="contentUrl";
	 //内容 
    private static final String D_Content="content";
	 //创建人 
    private static final String D_CreateId="createId";
	 //创建日期 
    private static final String D_CreateDate="createDate";
	 //是否启用（Y/N） 
    private static final String D_UseFlag="useFlag";
	 //关闭日期 
    private static final String D_ClosedDate="closedDate";
	

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
    * 设置状态
    * @param STATUS
    */
	public void setStatus(String value) {
        set(D_Status,value);
    }
	
   /**
    * 获取状态
    * @return STATUS
    */
    public String getStatus() {
        return getString(D_Status);
    }

	/**
    * 设置标记
    * @param DELETED_FLAG
    */
	public void setDeletedFlag(String value) {
        set(D_DeletedFlag,value);
    }
	
   /**
    * 获取标记
    * @return DELETED_FLAG
    */
    public String getDeletedFlag() {
        return getString(D_DeletedFlag);
    }

	/**
    * 设置消息代码
    * @param MESSAGE_CODE
    */
	public void setMessageCode(String value) {
        set(D_MessageCode,value);
    }
	
   /**
    * 获取消息代码
    * @return MESSAGE_CODE
    */
    public String getMessageCode() {
        return getString(D_MessageCode);
    }

	/**
    * 设置消息标题
    * @param MESSAGE_TITLE
    */
	public void setMessageTitle(String value) {
        set(D_MessageTitle,value);
    }
	
   /**
    * 获取消息标题
    * @return MESSAGE_TITLE
    */
    public String getMessageTitle() {
        return getString(D_MessageTitle);
    }

	/**
    * 设置消息类型（text、news）
    * @param MESSAGE_TYPE
    */
	public void setMessageType(String value) {
        set(D_MessageType,value);
    }
	
   /**
    * 获取消息类型（text、news）
    * @return MESSAGE_TYPE
    */
    public String getMessageType() {
        return getString(D_MessageType);
    }

	/**
    * 设置描述
    * @param DESCRIPTION
    */
	public void setDescription(String value) {
        set(D_Description,value);
    }
	
   /**
    * 获取描述
    * @return DESCRIPTION
    */
    public String getDescription() {
        return getString(D_Description);
    }

	/**
    * 设置推送图片地址
    * @param PIC_URL
    */
	public void setPicUrl(String value) {
        set(D_PicUrl,value);
    }
	
   /**
    * 获取推送图片地址
    * @return PIC_URL
    */
    public String getPicUrl() {
        return getString(D_PicUrl);
    }

	/**
    * 设置推送内容地址
    * @param CONTENT_URL
    */
	public void setContentUrl(String value) {
        set(D_ContentUrl,value);
    }
	
   /**
    * 获取推送内容地址
    * @return CONTENT_URL
    */
    public String getContentUrl() {
        return getString(D_ContentUrl);
    }

	/**
    * 设置内容
    * @param CONTENT
    */
	public void setContent(String value) {
        set(D_Content,value);
    }
	
   /**
    * 获取内容
    * @return CONTENT
    */
    public String getContent() {
        return getString(D_Content);
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
	public void setCreateDate(Timestamp value) {
        set(D_CreateDate,value);
    }
	
   /**
    * 获取创建日期
    * @return CREATE_DATE
    */
    public Timestamp getCreateDate() {
        return getTimestamp(D_CreateDate);
    }

	/**
    * 设置是否启用（Y/N）
    * @param USE_FLAG
    */
	public void setUseFlag(String value) {
        set(D_UseFlag,value);
    }
	
   /**
    * 获取是否启用（Y/N）
    * @return USE_FLAG
    */
    public String getUseFlag() {
        return getString(D_UseFlag);
    }

	/**
    * 设置关闭日期
    * @param CLOSED_DATE
    */
	public void setClosedDate(Date value) {
        set(D_ClosedDate,value);
    }
	
   /**
    * 获取关闭日期
    * @return CLOSED_DATE
    */
    public Date getClosedDate() {
        return getDate(D_ClosedDate);
    }
	
	
   static{
        propertes.add("rowId");
        propertes.add("status");
        propertes.add("deletedFlag");
        propertes.add("messageCode");
        propertes.add("messageTitle");
        propertes.add("messageType");
        propertes.add("description");
        propertes.add("picUrl");
        propertes.add("contentUrl");
        propertes.add("content");
        propertes.add("createId");
        propertes.add("createDate");
        propertes.add("useFlag");
        propertes.add("closedDate");
    }
	
	@Override
	public List<String> getPropertes() {
	    return propertes;
	}

}




