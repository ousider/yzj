

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import xn.core.model.BaseModel;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 表：pp_o_pig
 */
public class PpPigModel extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -3393620906475766677L;

    // 猪只ID
    private Long pigId;

    // 耳号ID
    private Long earCodeId;

    // 耳号
    private String earBrand;

    // 耳缺号

    private String earShort;

    // 耳刺号
    private String earThorn;
    // 猪场ID
    private String farmName;

    // 产线ID
    private Long lineId;

    // 产线名字
    private String lineName;

    // 猪舍ID
    private Long houseId;

    // 猪舍名字
    private String houseName;

    // 猪栏ID
    private Long pigpenId;

    // 猪栏名字
    private String pigpenName;

    // 猪群ID
    private Long swineryId;

    // 猪群名字
    private String swineryName;

    // 物料ID
    private Long materialId;

    // 物料名字
    private String materialName;

    // 猪类别
    private String pigType;

    // 猪类别名字
    private String pigTypeName;

    // 性别
    private String sex;

    // 性别
    private String sexName;

    // 品种ID
    private Long breedId;

    // 品种名字
    private String breedName;

    // 猪只状态
    private Long pigClass;

    // 猪只状态名字
    private String pigClassName;

    // 胎次
    private Long parity;

    // 出生日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    // 日龄
    private Long dateage;

    // 最后事件日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastEventDate;

    // 品系
    private String strain;

    // 体况
    private String bodyCondition;

    // 出生胎次
    private Long birthParity;

    // 出生窝号
    private Long houseNum;

    // 出生体重
    private Double birthWeight;

    // 入场日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date enterDate;

    // 入场体重
    private Long enterWeight;

    // 入场状态
    private String enterPigClass;

    // 母猪入场胎次
    private Long enterParity;

    // 负责人
    private Long worker;

    // 代养母猪ID
    private Long boardSowId;

    // 代养母猪耳号
    private String boardEarBrand;

    // 断奶日龄
    private Double mewDayAge;

    // 断奶体重
    private Double mewWeight;

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

    // 猪只位置
    private String location;

    // 猪只信息
    private String pigInfo;

    public Long getPigId() {
        return pigId;
    }

    public void setPigId(Long pigId) {
        this.pigId = pigId;
    }

    public Long getEarCodeId() {
        return earCodeId;
    }

    public void setEarCodeId(Long earCodeId) {
        this.earCodeId = earCodeId;
    }

    public String getEarBrand() {
        return earBrand;
    }

    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
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

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Long getPigpenId() {
        return pigpenId;
    }

    public void setPigpenId(Long pigpenId) {
        this.pigpenId = pigpenId;
    }

    public String getPigpenName() {
        return pigpenName;
    }

    public void setPigpenName(String pigpenName) {
        this.pigpenName = pigpenName;
    }

    public Long getSwineryId() {
        return swineryId;
    }

    public void setSwineryId(Long swineryId) {
        this.swineryId = swineryId;
    }

    public String getSwineryName() {
        return swineryName;
    }

    public void setSwineryName(String swineryName) {
        this.swineryName = swineryName;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public String getPigTypeName() {
        return pigTypeName;
    }

    public void setPigTypeName(String pigTypeName) {
        this.pigTypeName = pigTypeName;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }

    public String getPigClassName() {
        return pigClassName;
    }

    public void setPigClassName(String pigClassName) {
        this.pigClassName = pigClassName;
    }

    public Long getParity() {
        return parity;
    }

    public void setParity(Long parity) {
        this.parity = parity;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getDateage() {
        return dateage;
    }

    public void setDateage(Long dateage) {
        this.dateage = dateage;
    }

    public Date getLastEventDate() {
        return lastEventDate;
    }

    public void setLastEventDate(Date lastEventDate) {
        this.lastEventDate = lastEventDate;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public String getBodyCondition() {
        return bodyCondition;
    }

    public void setBodyCondition(String bodyCondition) {
        this.bodyCondition = bodyCondition;
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

    public Long getEnterWeight() {
        return enterWeight;
    }

    public void setEnterWeight(Long enterWeight) {
        this.enterWeight = enterWeight;
    }

    public String getEnterPigClass() {
        return enterPigClass;
    }

    public void setEnterPigClass(String enterPigClass) {
        this.enterPigClass = enterPigClass;
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

    public Long getBoardSowId() {
        return boardSowId;
    }

    public void setBoardSowId(Long boardSowId) {
        this.boardSowId = boardSowId;
    }

    public String getBoardEarBrand() {
        return boardEarBrand;
    }

    public void setBoardEarBrand(String boardEarBrand) {
        this.boardEarBrand = boardEarBrand;
    }

    public Double getMewDayAge() {
        return mewDayAge;
    }

    public void setMewDayAge(Double mewDayAge) {
        this.mewDayAge = mewDayAge;
    }

    public Double getMewWeight() {
        return mewWeight;
    }

    public void setMewWeight(Double mewWeight) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPigInfo() {
        return pigInfo;
    }

    public void setPigInfo(String pigInfo) {
        this.pigInfo = pigInfo;
    }

}
