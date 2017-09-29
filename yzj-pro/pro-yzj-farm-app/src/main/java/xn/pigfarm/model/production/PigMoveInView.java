

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：P_PigMoveIn
 */
public class PigMoveInView extends BaseModel implements java.io.Serializable {
    private static final long serialVersionUID = -2834347089584721322L;

    // 业务编码
    private String businessCode;

    // 操作类型：1 批次入场 2 耳号入场 3 分娩入场 4 虚拟公猪入场
    private String optType;

    // 产线ID
    private Long lineId;

    // 猪群
    private Long swineryId;

    // 猪舍ID
    private Long houseId;

    // 栏号ID
    private Long pigPenId;

    // 耳号 需要创建后获取 _EAR_CODE_ID
    private String earBrand;

    // 耳缺号
    private String earShort;

    // 耳刺号
    private String earThorn;

    // 电子耳号
    private String electronicEarNo;

    // 父亲耳号
    private String fatherEar;

    // 母亲耳号
    private String motherEar;

    // 代养母猪ID
    private Long boardSowId;

    // 物料ID
    private Long materialId;

    // 猪类别
    private String pigType;

    // 性别
    private String sex;

    // 品种
    private Long breedId;

    // 品系
    private String strain;

    // 猪只状态
    private Long pigClass;

    // 体况
    private String bodyCondition;

    // 胎次
    private Long parity;

    // 出生胎次
    private Long birthParity;

    // 出生窝号
    @DateTimeFormat(pattern = "yyyy-MM-dd")  
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Long houseNum;

    // 出生日期
    private Date birthDate;

    // 出生体重
    private Double birthWeight;

    // 入场日期
    private Date enterDate;

    // 入场体重
    private double enterWeight;

    // 入场价
    private double onPrice;

    // 母猪入场胎次
    private Long enterParity;

    // 负责人
    private Long worker;

    // 断奶日龄
    private Long mewDayAge;

    // 断奶体重
    private double mewWeight;

    // 右奶头数
    private Long rightTeatNum;

    // 左奶头数
    private Long leftTeatNum;

    // 近交系数
	private String coefficientInbred;  

    // 供应商
    private Long supplierId;

    // 来源
    private String origin;

    // 单据号
    private Long billId;

    // 创建人
    private Long createId;

    // 事件名称
	private String eventName;

    // 留种标识
    private String seedFlag;

    // 错误代码
	private String  errorCode;

    // 错误消息
	private String   errorMessage;

    // 关联猪只ID用于从一个场转到别一个场（表示原场ID)
    private Long relationPigId;

    // 原猪舍ID
    private Long preHouseId;

    // 原场猪群ID
    private Long preSwineryId;

    // 原场行号
    private Long preLineNumber;

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }
    public String getOptType() {
        return optType;
    }
    public void setOptType(String optType) {
        this.optType = optType;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
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

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }
    public String getEarBrand() {
        return earBrand;
    }
    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }
    public String getEarShort() {
        return earShort;
    }
    public void setEarShort(String earShort) {
        this.earShort = earShort;
    }
    public String getEarThorn() {
        return earThorn;
    }
    public void setEarThorn(String earThorn) {
        this.earThorn = earThorn;
    }
    public String getElectronicEarNo() {
        return electronicEarNo;
    }
    public void setElectronicEarNo(String electronicEarNo) {
        this.electronicEarNo = electronicEarNo;
    }
    public String getFatherEar() {
        return fatherEar;
    }
    public void setFatherEar(String fatherEar) {
        this.fatherEar = fatherEar;
    }
    public String getMotherEar() {
        return motherEar;
    }
    public void setMotherEar(String motherEar) {
        this.motherEar = motherEar;
    }

    public Long getBoardSowId() {
        return boardSowId;
    }

    public void setBoardSowId(Long boardSowId) {
        this.boardSowId = boardSowId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }
    public String getPigType() {
        return pigType;
    }
    public void setPigType(String pigType) {
        this.pigType = pigType;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }
    public String getStrain() {
        return strain;
    }
    public void setStrain(String strain) {
        this.strain = strain;
    }

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }
    public String getBodyCondition() {
        return bodyCondition;
    }
    public void setBodyCondition(String bodyCondition) {
        this.bodyCondition = bodyCondition;
    }

    public Long getParity() {
        return parity;
    }

    public void setParity(Long parity) {
        this.parity = parity;
    }

    public Long getBirthParity() {
        return birthParity;
    }

    public void setBirthParity(Long birthParity) {
        this.birthParity = birthParity;
    }

    public Long getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Long houseNum) {
        this.houseNum = houseNum;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBirthWeight() {
        return birthWeight;
    }

    public void setBirthWeight(Double birthWeight) {
        this.birthWeight = birthWeight;
    }
    public Date getEnterDate() {
        return enterDate;
    }
    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }
    public double getEnterWeight() {
        return enterWeight;
    }
    public void setEnterWeight(double enterWeight) {
        this.enterWeight = enterWeight;
    }
    public double getOnPrice() {
        return onPrice;
    }
    public void setOnPrice(double onPrice) {
        this.onPrice = onPrice;
    }

    public Long getEnterParity() {
        return enterParity;
    }

    public void setEnterParity(Long enterParity) {
        this.enterParity = enterParity;
    }

    public Long getWorker() {
        return worker;
    }

    public void setWorker(Long worker) {
        this.worker = worker;
    }

    public Long getMewDayAge() {
        return mewDayAge;
    }

    public void setMewDayAge(Long mewDayAge) {
        this.mewDayAge = mewDayAge;
    }
    public double getMewWeight() {
        return mewWeight;
    }
    public void setMewWeight(double mewWeight) {
        this.mewWeight = mewWeight;
    }

    public Long getRightTeatNum() {
        return rightTeatNum;
    }

    public void setRightTeatNum(Long rightTeatNum) {
        this.rightTeatNum = rightTeatNum;
    }

    public Long getLeftTeatNum() {
        return leftTeatNum;
    }

    public void setLeftTeatNum(Long leftTeatNum) {
        this.leftTeatNum = leftTeatNum;
    }
    public String getCoefficientInbred() {
        return coefficientInbred;
    }
    public void setCoefficientInbred(String coefficientInbred) {
        this.coefficientInbred = coefficientInbred;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    public Long getRelationPigId() {
        return relationPigId;
    }

    public void setRelationPigId(Long relationPigId) {
        this.relationPigId = relationPigId;
    }

    public String getSeedFlag() {
        return seedFlag;
    }

    public void setSeedFlag(String seedFlag) {
        this.seedFlag = seedFlag;
    }

    public Long getPreHouseId() {
        return preHouseId;
    }

    public void setPreHouseId(Long preHouseId) {
        this.preHouseId = preHouseId;
    }

    public Long getPreSwineryId() {
        return preSwineryId;
    }

    public void setPreSwineryId(Long preSwineryId) {
        this.preSwineryId = preSwineryId;
    }

    public Long getPreLineNumber() {
        return preLineNumber;
    }

    public void setPreLineNumber(Long preLineNumber) {
        this.preLineNumber = preLineNumber;
    }
	
}
