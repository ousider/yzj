package xn.hana.model.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 中间表结构
 * @author THL
 * @date 2017年3月9日 下午3:21:50
 */
public class MTC_ITFC implements Serializable {

    private static final long serialVersionUID = -1415305244262527786L;

    // 序号:自增列，流水码
    private Long MTC_ID;

    // 分公司编码：沁侬，大丰，新疆，太仓，安农
    private String MTC_Branch;

    // 业务日期:业务发生的日期
    private Date MTC_DocDate;

    // 业务代码:接口清单中业务类型代码
    private String MTC_ObjCode;

    // 新农+系统中的单据编号:分公司编码-单据ROW_ID-单据单号
    private String MTC_DocNum;

    // 优先级:值越小优先级越高
    private int MTC_Priority;

    // 处理区分：A-新增，B-更新，C-取消，D-关闭单据
    private String MTC_TransType;

    // 原始单据编号：当处理区分为 D / C 时，写入被取消的单据编号
    private String MTC_BaseEntry;

    // 业务标识
    private String MTC_Flag;

    // 创建日期:接口数据写入中间表的日期和时间
    private Date MTC_CreateTime;

    // 同步状态:N-未同步，P-处理中，Y-已同步，E-同步错误
    private String MTC_Status;

    // 处理日期：接口数据同步至SAP的系统日期和时间
    private Date MTC_UpdateTime;

    // 错误日志：同步出错时的错误日志
    private String MTC_ErrorMsg;

    // 处理次数：接口程序对该记录行的处理次数
    private int MTC_Times;

    // JSON格式:存放业务单据的json文件
    private String MTC_DataFile;

    // 传输json文件的长度，SAP接口取数据时进行数据完整性校验
    private int MTC_DataFileLen;

    // HANA DB
    private String DB_Bean_Name;

    public Long getMTC_ID() {
        return MTC_ID;
    }

    public void setMTC_ID(Long mTC_ID) {
        MTC_ID = mTC_ID;
    }

    public String getMTC_Branch() {
        return MTC_Branch;
    }

    public void setMTC_Branch(String mTC_Branch) {
        MTC_Branch = mTC_Branch;
    }

    public Date getMTC_DocDate() {
        return MTC_DocDate;
    }

    public void setMTC_DocDate(Date mTC_DocDate) {
        MTC_DocDate = mTC_DocDate;
    }

    public String getMTC_ObjCode() {
        return MTC_ObjCode;
    }

    public void setMTC_ObjCode(String mTC_ObjCode) {
        MTC_ObjCode = mTC_ObjCode;
    }

    public String getMTC_DocNum() {
        return MTC_DocNum;
    }

    public void setMTC_DocNum(String mTC_DocNum) {
        MTC_DocNum = mTC_DocNum;
    }

    public int getMTC_Priority() {
        return MTC_Priority;
    }

    public void setMTC_Priority(int mTC_Priority) {
        MTC_Priority = mTC_Priority;
    }

    public String getMTC_TransType() {
        return MTC_TransType;
    }

    public void setMTC_TransType(String mTC_TransType) {
        MTC_TransType = mTC_TransType;
    }

    public String getMTC_BaseEntry() {
        return MTC_BaseEntry;
    }

    public void setMTC_BaseEntry(String mTC_BaseEntry) {
        MTC_BaseEntry = mTC_BaseEntry;
    }

    public String getMTC_Flag() {
        return MTC_Flag;
    }

    public void setMTC_Flag(String mTC_Flag) {
        MTC_Flag = mTC_Flag;
    }

    public Date getMTC_CreateTime() {
        return MTC_CreateTime;
    }

    public void setMTC_CreateTime(Date mTC_CreateTime) {
        MTC_CreateTime = mTC_CreateTime;
    }

    public String getMTC_Status() {
        return MTC_Status;
    }

    public void setMTC_Status(String mTC_Status) {
        MTC_Status = mTC_Status;
    }


    public Date getMTC_UpdateTime() {
        return MTC_UpdateTime;
    }

    public void setMTC_UpdateTime(Date mTC_UpdateTime) {
        MTC_UpdateTime = mTC_UpdateTime;
    }

    public String getMTC_ErrorMsg() {
        return MTC_ErrorMsg;
    }

    public void setMTC_ErrorMsg(String mTC_ErrorMsg) {
        MTC_ErrorMsg = mTC_ErrorMsg;
    }

    public int getMTC_Times() {
        return MTC_Times;
    }

    public void setMTC_Times(int mTC_Times) {
        MTC_Times = mTC_Times;
    }

    public String getMTC_DataFile() {
        return MTC_DataFile;
    }

    public void setMTC_DataFile(String mTC_DataFile) {
        MTC_DataFile = mTC_DataFile;
    }

    public int getMTC_DataFileLen() {
        return MTC_DataFileLen;
    }

    public void setMTC_DataFileLen(int mTC_DataFileLen) {
        MTC_DataFileLen = mTC_DataFileLen;
    }

    public String getDB_Bean_Name() {
        return DB_Bean_Name;
    }

    public void setDB_Bean_Name(String dB_Bean_Name) {
        DB_Bean_Name = dB_Bean_Name;
    }

}
