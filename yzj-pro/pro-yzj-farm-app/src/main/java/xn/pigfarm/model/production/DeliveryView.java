

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import xn.core.model.BaseModel;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 表：
 */
public class DeliveryView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = 8781410480618306985L;

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

    // 创建人
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
    // 产程
    private Double labor;

    // 活仔窝重
    private Double aliveLitterWeight;

    // 健仔数
    private Long healthyNum;

    // 弱仔数
    private Long weakNum;

    // 死胎
    private Long stillbirthNum;

    // 畸形
    private Long mutantNum;

    // 木乃伊
    private Long mummyNum;

    // 黑胎
    private Long blackNum;

    // 活仔公
    private Long aliveLitterY;

    // 活仔母
    private Long aliveLitterX;

    // 分娩状况
    private String deliveryType;

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

    public Double getLabor() {
        return labor;
    }

    public void setLabor(Double labor) {
        this.labor = labor;
    }

    public Double getAliveLitterWeight() {
        return aliveLitterWeight;
    }

    public void setAliveLitterWeight(Double aliveLitterWeight) {
        this.aliveLitterWeight = aliveLitterWeight;
    }

    public Long getHealthyNum() {
        return healthyNum;
    }

    public void setHealthyNum(Long healthyNum) {
        this.healthyNum = healthyNum;
    }

    public Long getWeakNum() {
        return weakNum;
    }

    public void setWeakNum(Long weakNum) {
        this.weakNum = weakNum;
    }

    public Long getStillbirthNum() {
        return stillbirthNum;
    }

    public void setStillbirthNum(Long stillbirthNum) {
        this.stillbirthNum = stillbirthNum;
    }

    public Long getMutantNum() {
        return mutantNum;
    }

    public void setMutantNum(Long mutantNum) {
        this.mutantNum = mutantNum;
    }

    public Long getMummyNum() {
        return mummyNum;
    }

    public void setMummyNum(Long mummyNum) {
        this.mummyNum = mummyNum;
    }

    public Long getBlackNum() {
        return blackNum;
    }

    public void setBlackNum(Long blackNum) {
        this.blackNum = blackNum;
    }

    public Long getAliveLitterY() {
        return aliveLitterY;
    }

    public void setAliveLitterY(Long aliveLitterY) {
        this.aliveLitterY = aliveLitterY;
    }

    public Long getAliveLitterX() {
        return aliveLitterX;
    }

    public void setAliveLitterX(Long aliveLitterX) {
        this.aliveLitterX = aliveLitterX;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
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
