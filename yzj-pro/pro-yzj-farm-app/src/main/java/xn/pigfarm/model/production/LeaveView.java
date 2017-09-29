

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class LeaveView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -3219059097894966440L;

    // 行号
    private Long lineNumber;

    // 猪类别
    private String pigType;

    // 猪只状态
    private Long pigClass;

    // 猪只ID
    private Long pigId;

    // 输入日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minValidDate;

    // 最大有效日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maxValidDate;
    // ====================================上面为事件公共字段
    // 离场类型
    private String leaveType       ; 

    // 离场重量
    private Double leaveWeight     ;

    // 离场单价
    private Double leavePrice      ;

    // 离场原因
    private String leaveReason;

    // 客户
    private Long customerId;

    // 猪舍
    private Long houseId;

    // 猪栏
    private Long pigpenId;

    // 断奶窝重
    private Double weanWeight;

    // 带仔数
    private Long pigQty;

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

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
    public String getLeaveType() {
        return leaveType;
    }
    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
    public Double getLeaveWeight() {
        return leaveWeight;
    }
    public void setLeaveWeight(Double leaveWeight) {
        this.leaveWeight = leaveWeight;
    }
    public Double getLeavePrice() {
        return leavePrice;
    }
    public void setLeavePrice(Double leavePrice) {
        this.leavePrice = leavePrice;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Double getWeanWeight() {
        return weanWeight;
    }

    public void setWeanWeight(Double weanWeight) {
        this.weanWeight = weanWeight;
    }

    public Long getPigpenId() {
        return pigpenId;
    }

    public void setPigpenId(Long pigpenId) {
        this.pigpenId = pigpenId;
    }

    public Long getPigQty() {
        return pigQty;
    }

    public void setPigQty(Long pigQty) {
        this.pigQty = pigQty;
    }

}
