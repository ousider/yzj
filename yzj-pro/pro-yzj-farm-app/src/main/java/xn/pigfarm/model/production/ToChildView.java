

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class ToChildView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -7764990961003540143L;

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

    // 转入猪群ID
    private Long swineryId;
    
    // 转出猪群ID
    private Long oldSwineryId;

    // 猪舍ID
    private Long houseId;

    // 转猪舍ID
    private Long inHouseId;

    // 猪栏ID
    private String pigpenId;

    // 转猪栏ID
    private String inPigpenId;

    // 转舍类型
    private String changeHouseType;

    // 转重量
    private Double childWeight;

    // 转数量
    private Long childQty;

    // 合并群
    private String mergeSwinery;

    // 猪只数量
    private Long pigQty;

    // 猪只ids
    private String pigIds;

    // 创建出的群
    private Long createSwineryId;

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

    public Long getSwineryId() {
        return swineryId;
    }

    public void setSwineryId(Long swineryId) {
        this.swineryId = swineryId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getInHouseId() {
        return inHouseId;
    }

    public void setInHouseId(Long inHouseId) {
        this.inHouseId = inHouseId;
    }

    public String getPigpenId() {
        return pigpenId;
    }

    public void setPigpenId(String pigpenId) {
        this.pigpenId = pigpenId;
    }

    public String getInPigpenId() {
        return inPigpenId;
    }

    public void setInPigpenId(String inPigpenId) {
        this.inPigpenId = inPigpenId;
    }

    public String getChangeHouseType() {
        return changeHouseType;
    }

    public void setChangeHouseType(String changeHouseType) {
        this.changeHouseType = changeHouseType;
    }

    public Double getChildWeight() {
        return childWeight;
    }

    public void setChildWeight(Double childWeight) {
        this.childWeight = childWeight;
    }

    public Long getChildQty() {
        return childQty;
    }

    public void setChildQty(Long childQty) {
        this.childQty = childQty;
    }

    public String getMergeSwinery() {
        return mergeSwinery;
    }

    public void setMergeSwinery(String mergeSwinery) {
        this.mergeSwinery = mergeSwinery;
    }

    public Long getPigQty() {
        return pigQty;
    }

    public void setPigQty(Long pigQty) {
        this.pigQty = pigQty;
    }
    public Long getOldSwineryId() {
		return oldSwineryId;
	}

	public void setOldSwineryId(Long oldSwineryId) {
		this.oldSwineryId = oldSwineryId;
	}

    public String getPigIds() {
        return pigIds;
    }

    public void setPigIds(String pigIds) {
        this.pigIds = pigIds;
    }

    public Long getCreateSwineryId() {
        return createSwineryId;
    }

    public void setCreateSwineryId(Long createSwineryId) {
        this.createSwineryId = createSwineryId;
    }
}
