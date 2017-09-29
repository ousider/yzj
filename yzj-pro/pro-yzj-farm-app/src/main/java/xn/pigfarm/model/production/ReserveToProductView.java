

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class ReserveToProductView extends BaseModel implements java.io.Serializable {
    
    private static final long serialVersionUID = -7473304289617162251L;

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

    // 新耳牌号
    private String newEarBrand;

    // 转入猪舍
    private Long newHouseId;

    // 转入猪栏
    private Long newPigpen;

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

    public String getNewEarBrand() {
        return newEarBrand;
    }

    public void setNewEarBrand(String newEarBrand) {
        this.newEarBrand = newEarBrand;
    }

    public Long getNewHouseId() {
        return newHouseId;
    }

    public void setNewHouseId(Long newHouseId) {
        this.newHouseId = newHouseId;
    }

    public Long getNewPigpen() {
        return newPigpen;
    }

    public void setNewPigpen(Long newPigpen) {
        this.newPigpen = newPigpen;
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

}
