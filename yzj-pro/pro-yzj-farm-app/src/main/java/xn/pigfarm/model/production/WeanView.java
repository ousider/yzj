

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class WeanView extends BaseModel implements java.io.Serializable {

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
    // 断奶数量
    private Long weanNum;

    // 带仔数量
    private Long pigQty;

    // 死亡数量
    private Long dieNum;

    // 母猪窝重、仔猪体重
    private Double weanWeight;

    // 系统导入数据
    private String sysInput;

    // 自动寄养、死亡
    private String autoDieBoard;

    // 转入猪舍
    private Long inHouseId;

    // 转入猪栏
    private Long inPigpenId;

    // 背膘
    private Double backfat;

    // 评分
    private String score;

    // 标识（Y/N）：判断母猪是否需要做断奶
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

    public Long getWeanNum() {
        return weanNum;
    }

    public void setWeanNum(Long weanNum) {
        this.weanNum = weanNum;
    }

    public Long getPigQty() {
        return pigQty;
    }

    public void setPigQty(Long pigQty) {
        this.pigQty = pigQty;
    }

    public Long getDieNum() {
        return dieNum;
    }

    public void setDieNum(Long dieNum) {
        this.dieNum = dieNum;
    }

    public Double getWeanWeight() {
        return weanWeight;
    }

    public void setWeanWeight(Double weanWeight) {
        this.weanWeight = weanWeight;
    }

    public String getSysInput() {
        return sysInput;
    }

    public void setSysInput(String sysInput) {
        this.sysInput = sysInput;
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

    public String getAutoDieBoard() {
        return autoDieBoard;
    }

    public void setAutoDieBoard(String autoDieBoard) {
        this.autoDieBoard = autoDieBoard;
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

    public Double getBackfat() {
        return backfat;
    }

    public void setBackfat(Double backfat) {
        this.backfat = backfat;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
