
package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class ChangeHouseView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = 3241638428636639772L;

    // 行号
    private Long lineNumber;

    // 猪类别
    private String pigType;

    // 猪只ID
    private Long pigId;

    // 输入日期
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

    //
    private Long createId;

    // 最小有效日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minValidDate;

    // 最大有效日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maxValidDate;

    // ====================================上面为事件公共字段
    //
    private String changeHouseType;

    // 转入猪舍ID
    private Long inHouseId;

    // 转入单元ID
    private Long inPigpenId;

    // 转入体重
    private Double changeWeight;

    // 背膘
    private Double backfat;

    // 评分
    private String score;

    // 称重方式
    private String weighType;

    // 耳牌号
    private String earBrand;
    
    // 转入猪群
    private Long inSwineryId;

    // 猪只状态
    private Long pigClass;

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

    public String getChangeHouseType() {
        return changeHouseType;
    }

    public void setChangeHouseType(String changeHouseType) {
        this.changeHouseType = changeHouseType;
    }

    public Long getInHouseId() {
        return inHouseId;
    }

    public void setInHouseId(Long inHouseId) {
        this.inHouseId = inHouseId;
    }

    public Long getInPigpenId() {
        return inPigpenId;
    }

    public void setInPigpenId(Long inPigpenId) {
        this.inPigpenId = inPigpenId;
    }

    public Double getChangeWeight() {
        return changeWeight;
    }

    public void setChangeWeight(Double changeWeight) {
        this.changeWeight = changeWeight;
    }

    public Double getBackfat() {
        return backfat;
    }

    public void setBackfat(Double backfat) {
        this.backfat = backfat;
    }

    public String getWeighType() {
        return weighType;
    }

    public void setWeighType(String weighType) {
        this.weighType = weighType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEarBrand() {
        return earBrand;
    }

    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }

    public Long getInSwineryId() {
        return inSwineryId;
    }

    public void setInSwineryId(Long inSwineryId) {
        this.inSwineryId = inSwineryId;
    }

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }
    
}
