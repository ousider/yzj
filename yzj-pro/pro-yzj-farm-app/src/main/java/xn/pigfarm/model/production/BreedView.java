

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class BreedView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -3044936766769080436L;

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

    // 最小有效日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minValidDate;

    // 最大有效日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maxValidDate;

    //
    private Long createId;

    // ====================================上面为事件公共字段
    // 配种方式
    private String breedType;

    // 1配种公猪
    private Long breedBoarFirst;

    // 2配种日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date breedDateSecond;

    // 2配种公猪
    private Long breedBoarSecond;

    // 3配种日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date breedDateThird;

    // 3配种公猪
    private Long breedBoarThird;

    // 静立分
    private String jlScore;

    // 锁住分
    private String szScore;

    // 倒流分
    private String dlScore;

    // 精液质量
    private String semenQuality;

    private Long inHouseId;

    private Long inPigpenId;

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

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public String getBreedType() {
        return breedType;
    }

    public void setBreedType(String breedType) {
        this.breedType = breedType;
    }

    public Long getBreedBoarFirst() {
        return breedBoarFirst;
    }

    public void setBreedBoarFirst(Long breedBoarFirst) {
        this.breedBoarFirst = breedBoarFirst;
    }

    public Date getBreedDateSecond() {
        return breedDateSecond;
    }

    public void setBreedDateSecond(Date breedDateSecond) {
        this.breedDateSecond = breedDateSecond;
    }

    public Long getBreedBoarSecond() {
        return breedBoarSecond;
    }

    public void setBreedBoarSecond(Long breedBoarSecond) {
        this.breedBoarSecond = breedBoarSecond;
    }

    public Date getBreedDateThird() {
        return breedDateThird;
    }

    public void setBreedDateThird(Date breedDateThird) {
        this.breedDateThird = breedDateThird;
    }

    public Long getBreedBoarThird() {
        return breedBoarThird;
    }

    public void setBreedBoarThird(Long breedBoarThird) {
        this.breedBoarThird = breedBoarThird;
    }

    public String getJlScore() {
        return jlScore;
    }

    public void setJlScore(String jlScore) {
        this.jlScore = jlScore;
    }

    public String getSzScore() {
        return szScore;
    }

    public void setSzScore(String szScore) {
        this.szScore = szScore;
    }

    public String getDlScore() {
        return dlScore;
    }

    public void setDlScore(String dlScore) {
        this.dlScore = dlScore;
    }

    public String getSemenQuality() {
        return semenQuality;
    }

    public void setSemenQuality(String semenQuality) {
        this.semenQuality = semenQuality;
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

}
