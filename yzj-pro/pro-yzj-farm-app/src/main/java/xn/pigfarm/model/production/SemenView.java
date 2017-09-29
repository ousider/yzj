

package xn.pigfarm.model.production;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class SemenView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -6978693526471792809L;

    // 行号
    private Long lineNumber;

    // 猪类别
    private String pigType;

    // 猪只ID
    private Long pigId;

    // 输入日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date enterDate;

    // 采精日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date semenDate;

    // 入库日期
    @DateTimeFormat(
        pattern = "yyyy-MM-dd")
    @JsonFormat(
        pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date inputDate;

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

    // ====================================上面为事件公共字段
    // 采精方式 // 1 本交 2 假母台 精液入库方式
    private String semenType;

    // 精液量（ML）
    private Double semenQty;

    // 稀释分数
    private Double anhNum;

    // 单价
    private Double price;

    // 化验员
    private Long analust;

    // 精子活力（%）
    private Double spermMotility;

    // 精子密度
    private Double spermDensity;

    // 畸形率（%）
    private Double abnormationRate;

    // 凝聚度
    private String cohesion;

    // 气味
    private String odour;

    // 颜色
    private String color;

    // 包装
    private String pack;

    // 规格（ML）
    private String spec;

    // 仓位
    private Long warehouseId;
    
    // 最小有效日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date minValidDate;

    // 最大有效日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maxValidDate;
    
    // 精液来源
    private String origin;

    // 供应商
    private Long supplierId;

    // 有效日期
    @DateTimeFormat( pattern = "yyyy-MM-dd")
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;

    // 精液主数据
    private Long materialId;

    // 精液批号
    private String semenBatchNo;

    // 传出精液ROW_ID 只传出最后一条
    private Integer spermInfoId;

    // 日龄
    private Integer dayAge;

    // 品种名
    private String breedName;

    // 耳号
    private String earBrand;

    // 有效期天数
    private Long shelfLife;

    // 公猪主数据
    private Long boarMaterialId;

    // 品种ID
    private Long breedId;

    // 实际精液数量
    private Double spermNum;

    // PP_L_BILL_SEMEN_SALE的ROW_ID
    private Long semenSaleRowId;

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

    public String getSemenType() {
        return semenType;
    }

    public void setSemenType(String semenType) {
        this.semenType = semenType;
    }

    public Double getSemenQty() {
        return semenQty;
    }

    public void setSemenQty(Double semenQty) {
        this.semenQty = semenQty;
    }

    public Double getAnhNum() {
        return anhNum;
    }

    public void setAnhNum(Double anhNum) {
        this.anhNum = anhNum;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAnalust() {
        return analust;
    }

    public void setAnalust(Long analust) {
        this.analust = analust;
    }

    public Double getSpermMotility() {
        return spermMotility;
    }

    public void setSpermMotility(Double spermMotility) {
        this.spermMotility = spermMotility;
    }

    public Double getSpermDensity() {
        return spermDensity;
    }

    public void setSpermDensity(Double spermDensity) {
        this.spermDensity = spermDensity;
    }

    public Double getAbnormationRate() {
        return abnormationRate;
    }

    public void setAbnormationRate(Double abnormationRate) {
        this.abnormationRate = abnormationRate;
    }

    public String getCohesion() {
        return cohesion;
    }

    public void setCohesion(String cohesion) {
        this.cohesion = cohesion;
    }

    public String getOdour() {
        return odour;
    }

    public void setOdour(String odour) {
        this.odour = odour;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getSemenBatchNo() {
        return semenBatchNo;
    }

    public void setSemenBatchNo(String semenBatchNo) {
        this.semenBatchNo = semenBatchNo;
    }

    public Integer getSpermInfoId() {
        return spermInfoId;
    }

    public void setSpermInfoId(Integer spermInfoId) {
        this.spermInfoId = spermInfoId;
    }

    public Date getSemenDate() {
        return semenDate;
    }

    public void setSemenDate(Date semenDate) {
        this.semenDate = semenDate;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Integer getDayAge() {
        return dayAge;
    }

    public void setDayAge(Integer dayAge) {
        this.dayAge = dayAge;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getEarBrand() {
        return earBrand;
    }

    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }

    public Long getBoarMaterialId() {
        return boarMaterialId;
    }

    public void setBoarMaterialId(Long boarMaterialId) {
        this.boarMaterialId = boarMaterialId;
    }

    public Long getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Long shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Long getBreedId() {
        return breedId;
    }

    public void setBreedId(Long breedId) {
        this.breedId = breedId;
    }

    public Double getSpermNum() {
        return spermNum;
    }

    public void setSpermNum(Double spermNum) {
        this.spermNum = spermNum;
    }

    public Long getSemenSaleRowId() {
        return semenSaleRowId;
    }

    public void setSemenSaleRowId(Long semenSaleRowId) {
        this.semenSaleRowId = semenSaleRowId;
    }

}
