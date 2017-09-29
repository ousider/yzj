

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class ChildDieView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -3219059097894966440L;

    // 行号
    private Long lineNumber;

    // 猪类别
    private String pigType;

    // 猪只ID
    private Long pigId;

    // 输入日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date enterDate;

    // 输入日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date saleDate;

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

    // 猪群
    private Long houseId;

    // 客户
    private Long customerId;

    // 猪舍
    private Long swineryId;

    // 离场类型
    private String leaveReason;

    // 离场重量
    private Double leaveWeight     ;

    // 离场数量
    private Long leaveQty;

    // 带仔数量
    private Long pigQty;

    // 金额
    private Double money;

    // 猪舍类型
    private String houseType;

    // 猪栏id
    private Long pigpenId;

    // 标识 区分仔猪死亡和断奶猪死亡
    private String flag;

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

    public Long getPigQty() {
        return pigQty;
    }

    public void setPigQty(Long pigQty) {
        this.pigQty = pigQty;
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

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getSwineryId() {
        return swineryId;
    }

    public void setSwineryId(Long swineryId) {
        this.swineryId = swineryId;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Double getLeaveWeight() {
        return leaveWeight;
    }
    public void setLeaveWeight(Double leaveWeight) {
        this.leaveWeight = leaveWeight;
    }

    public Long getLeaveQty() {
        return leaveQty;
    }

    public void setLeaveQty(Long leaveQty) {
        this.leaveQty = leaveQty;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Long getPigpenId() {
        return pigpenId;
    }

    public void setPigpenId(Long pigpenId) {
        this.pigpenId = pigpenId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
