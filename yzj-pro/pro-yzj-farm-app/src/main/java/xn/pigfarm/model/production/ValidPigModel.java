package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

public class ValidPigModel extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = 5709310757437220140L;

    // 猪只ID
    private Long pigId;

    // 性别
    private String sex;

    // 性别名称
    private String sexName;

    // 选种类型
    private String selectBreedType;

    // 选中类型名称
    private String selectBreedTypeName;

    // 猪类型
    private Long pigType;

    // 猪类型名称
    private String pigTypeName;

    // 耳牌号
    private String earBrand;

    // 品种日龄
    private String breedAndDayAge;

    public String getBreedAndDayAge() {
        return breedAndDayAge;
    }

    public void setBreedAndDayAge(String breedAndDayAge) {
        this.breedAndDayAge = breedAndDayAge;
    }

    // 耳缺号
    private String earShort;

    // 耳刺号
    private String earThorn;

    // 电子耳号
    private String electronicEarNo;

    // 耳号ID
    private Long earCodeId;

    // 产线ID
    private Long lineId;

    // 产线名字
    private String lineName;

    // 猪群名字
    private String swineryName;

    // 猪群ID
    private Long swineryId;

    // 品种名字
    private String breedName;

    // 品种ID
    private Long breedId;

    // 猪只状态名字
    private String pigClassName;

    // 猪只状态
    private Long pigClass;

    // 猪舍名字
    private String houseName;

    // 猪舍ID
    private Long houseId;

    // 猪栏名字
    private String pigpenName;

    // 猪栏ID
    private Long pigpenId;

    // 物料ID
    private Long materialId;

    // 物料名字
    private String materialName;

    // 日龄
    private double dateAge;

    // 猪只数量
    private int pigQty;

    // 胎次
    private int parity;

    // 父亲耳号
    private String fatherEarName;

    // 母猪耳号
    private String motherEarName;

    // 最后事件日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date lastEventDate;

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

    //
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    // 配种日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date breedDate;

    // 分娩日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date preDeliveryDate;

    // 备注
    private String memo;

    // 操作日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date operDate;

    // 操作数量
    private int operQity;

    // 操作重量
    private double operWeight;

    // 位置
    private String location;

    // 猪只信息
    private String pigInfo;

    // 生日
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthDate;

    // 销售品名
    private long saleType;

    // 是否留种
    private String seedFlag;

    // 精液数量
    private Long semenNum;

    // 精液有效日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;

    // 精液批号
    private String semenBatchNo;

    // 采精日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date semenDate;

    // 仓库ID
    private Long warehouseId;

    // 仓库名称
    private String warehouseName;

    // 活仔公
    private Long seedMale;

    // 活仔母
    private Long seedFemale;

    // 批次日龄
    private Double swineryAge;

    // 盘点猪只类型
    private Long checkPigType;

    // 猪舍类型
    private Long houseType;

    public Long getPigId() {
        return pigId;
    }

    public void setPigId(Long pigId) {
        this.pigId = pigId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getPigType() {
        return pigType;
    }

    public void setPigType(Long pigType) {
        this.pigType = pigType;
    }

    public String getPigTypeName() {
        return pigTypeName;
    }

    public void setPigTypeName(String pigTypeName) {
        this.pigTypeName = pigTypeName;
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

    public Long getEarCodeId() {
        return earCodeId;
    }

    public void setEarCodeId(Long earCodeId) {
        this.earCodeId = earCodeId;
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

    public String getSwineryName() {
        return swineryName;
    }

    public void setSwineryName(String swineryName) {
        this.swineryName = swineryName;
    }

    public Long getSwineryId() {
        return swineryId;
    }

    public void setSwineryId(Long swineryId) {
        this.swineryId = swineryId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }

    public String getPigClassName() {
        return pigClassName;
    }

    public void setPigClassName(String pigClassName) {
        this.pigClassName = pigClassName;
    }

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getPigpenName() {
        return pigpenName;
    }

    public void setPigpenName(String pigpenName) {
        this.pigpenName = pigpenName;
    }

    public Long getPigpenId() {
        return pigpenId;
    }

    public void setPigpenId(Long pigpenId) {
        this.pigpenId = pigpenId;
    }

    public double getDateAge() {
        return dateAge;
    }

    public void setDateAge(double dateAge) {
        this.dateAge = dateAge;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public int getPigQty() {
        return pigQty;
    }

    public void setPigQty(int pigQty) {
        this.pigQty = pigQty;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public String getFatherEarName() {
        return fatherEarName;
    }

    public void setFatherEarName(String fatherEarName) {
        this.fatherEarName = fatherEarName;
    }

    public String getMotherEarName() {
        return motherEarName;
    }

    public void setMotherEarName(String motherEarName) {
        this.motherEarName = motherEarName;
    }

    public Date getLastEventDate() {
        return lastEventDate;
    }

    public void setLastEventDate(Date lastEventDate) {
        this.lastEventDate = lastEventDate;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getBreedDate() {
        return breedDate;
    }

    public void setBreedDate(Date breedDate) {
        this.breedDate = breedDate;
    }

    public Date getPreDeliveryDate() {
        return preDeliveryDate;
    }

    public void setPreDeliveryDate(Date preDeliveryDate) {
        this.preDeliveryDate = preDeliveryDate;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public int getOperQity() {
        return operQity;
    }

    public void setOperQity(int operQity) {
        this.operQity = operQity;
    }

    public double getOperWeight() {
        return operWeight;
    }

    public void setOperWeight(double operWeight) {
        this.operWeight = operWeight;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getSelectBreedType() {
        return selectBreedType;
    }

    public void setSelectBreedType(String selectBreedType) {
        this.selectBreedType = selectBreedType;
    }

    public String getSelectBreedTypeName() {
        return selectBreedTypeName;
    }

    public void setSelectBreedTypeName(String selectBreedTypeName) {
        this.selectBreedTypeName = selectBreedTypeName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getSaleType() {
        return saleType;
    }

    public void setSaleType(long saleType) {
        this.saleType = saleType;
    }

    public String getSeedFlag() {
        return seedFlag;
    }

    public void setSeedFlag(String seedFlag) {
        this.seedFlag = seedFlag;
    }

    public Long getSemenNum() {
        return semenNum;
    }

    public void setSemenNum(Long semenNum) {
        this.semenNum = semenNum;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public String getSemenBatchNo() {
        return semenBatchNo;
    }

    public void setSemenBatchNo(String semenBatchNo) {
        this.semenBatchNo = semenBatchNo;
    }

    public Date getSemenDate() {
        return semenDate;
    }

    public void setSemenDate(Date semenDate) {
        this.semenDate = semenDate;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getSeedMale() {
        return seedMale;
    }

    public void setSeedMale(Long seedMale) {
        this.seedMale = seedMale;
    }

    public Long getSeedFemale() {
        return seedFemale;
    }

    public void setSeedFemale(Long seedFemale) {
        this.seedFemale = seedFemale;
    }

    public Double getSwineryAge() {
        return swineryAge;
    }

    public void setSwineryAge(Double swineryAge) {
        this.swineryAge = swineryAge;
    }

    public Long getCheckPigType() {
        return checkPigType;
    }

    public void setCheckPigType(Long checkPigType) {
        this.checkPigType = checkPigType;
    }

    public Long getHouseType() {
        return houseType;
    }

    public void setHouseType(Long houseType) {
        this.houseType = houseType;
    }
}
