

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class FosterView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = 2082917709221178840L;

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
    // 带仔数

    private Long pigQty;

    // 代养母猪
    private Long boardSowId;

    // 寄养原因
    private String fosterReason;

    // 寄养重量
    private Double fosterWeight;

    // 寄数量
    private Long fosterQty;

    // 标识：区分仔猪是否需要转舍(Y/N)
    private String flag;

    // 奶妈事件中 原栏位母猪id
    private Long inPigId;

    // 奶妈事件中 奶妈转入猪栏
    private Long inPigpenId;

    // 耳牌号
    private String earBrand;

    // 代养母猪耳牌号
    private String boardSowEarBrand;

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

    public Long getPigQty() {
        return pigQty;
    }

    public void setPigQty(Long pigQty) {
        this.pigQty = pigQty;
    }

    public Long getBoardSowId() {
        return boardSowId;
    }

    public void setBoardSowId(Long boardSowId) {
        this.boardSowId = boardSowId;
    }

    public String getFosterReason() {
        return fosterReason;
    }

    public void setFosterReason(String fosterReason) {
        this.fosterReason = fosterReason;
    }

    public Double getFosterWeight() {
        return fosterWeight;
    }

    public void setFosterWeight(Double fosterWeight) {
        this.fosterWeight = fosterWeight;
    }

    public Long getFosterQty() {
        return fosterQty;
    }

    public void setFosterQty(Long fosterQty) {
        this.fosterQty = fosterQty;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getInPigId() {
        return inPigId;
    }

    public void setInPigId(Long inPigId) {
        this.inPigId = inPigId;
    }

    public Long getInPigpenId() {
        return inPigpenId;
    }

    public void setInPigpenId(Long inPigpenId) {
        this.inPigpenId = inPigpenId;
    }

    public String getEarBrand() {
        return earBrand;
    }

    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }

    public String getBoardSowEarBrand() {
        return boardSowEarBrand;
    }

    public void setBoardSowEarBrand(String boardSowEarBrand) {
        this.boardSowEarBrand = boardSowEarBrand;
    }

}
