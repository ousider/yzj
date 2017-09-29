

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class PregnancyCheckView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = 6771455535070468569L;

    // 行号
    private Long lineNumber;

    // 猪类别
    private String pigType;

    // 猪只ID
    private Long pigId;

    // 输入日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date enterDate;

    // 事件名称
    private String eventName;

    // 错误代码
    private String errorCode;

    // 错误消息
    private String errorMessage;

    // 负责人
    private Long worker;

    // 表单ID
    private Long billId;

    // 创建人ID
    private Long createId;

    // 最小有效日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minValidDate;

    // 最大有效日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maxValidDate;
    
    
    // ====================================上面为事件公共字段
    // 妊娠检查方式
    private String pregnancyType;

    // 妊娠检查结果
    private String pregnancyResult;

    // 未孕原因
    private String unpregnancyCause;

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getPigType() {
        return pigType;
    }

    public void setPigType(String pigType) {
        this.pigType = pigType;
    }

    public Long getPigId() {
        return pigId;
    }

    public void setPigId(Long pigId) {
        this.pigId = pigId;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getWorker() {
        return worker;
    }

    public void setWorker(Long worker) {
        this.worker = worker;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public String getPregnancyType() {
        return pregnancyType;
    }

    public void setPregnancyType(String pregnancyType) {
        this.pregnancyType = pregnancyType;
    }

    public String getPregnancyResult() {
        return pregnancyResult;
    }

    public void setPregnancyResult(String pregnancyResult) {
        this.pregnancyResult = pregnancyResult;
    }

    public String getUnpregnancyCause() {
        return unpregnancyCause;
    }

    public void setUnpregnancyCause(String unpregnancyCause) {
        this.unpregnancyCause = unpregnancyCause;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getMinValidDate() {
        return minValidDate;
    }

    public void setMinValidDate(Date minValidDate) {
        this.minValidDate = minValidDate;
    }

    public Date getMaxValidDate() {
        return maxValidDate;
    }

    public void setMaxValidDate(Date maxValidDate) {
        this.maxValidDate = maxValidDate;
    }

}
