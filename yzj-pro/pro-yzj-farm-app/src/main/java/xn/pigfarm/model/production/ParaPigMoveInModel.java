
package xn.pigfarm.model.production;

import java.util.Date;

/**
 * 表：
 */
public class ParaPigMoveInModel extends BillModel implements java.io.Serializable {

    private static final long serialVersionUID = -7473304289617162251L;

    // 猪类别
    private String pigType;

    // 入场类型
    private String enterType;

    // 物料ID
    private Long materialId;

    // 供应商
    private Long supplierId;

    // 猪只状态
    private Long pigClass;

    // 产线
    private Long lineId;

    // 猪舍
    private Long houseId;

    // 猪栏
    private Long pigpenId;

    // 猪群
    private Long swineryId;

    // 数量
    private Double totalNum;

    // 总重
    private Double averageWeight;

    // 总价
    private Double averagePrice;

    // 日龄
    private int dayAge;

    // 单据日期
    private Date enterDate;

    // 相关单据
    private Long relateBillId;

    // 相关单据code
    private String relateBillCode;

    // 是否选择销售相关单据
    private String isSell;

    // 原场houseId
    private Long preHouseId;

    // 原场swineryId
    private Long preSwineryId;

    // 出生日期
    private Date birthDate;

    // 行号
    private Long preLineNumber;

    private String notes;

    // 销售离场日期
    private Date leaveDate;

    public String getPigType() {
        return pigType;
    }

    public void setPigType(String pigType) {
        this.pigType = pigType;
    }

    public String getEnterType() {
        return enterType;
    }

    public void setEnterType(String enterType) {
        this.enterType = enterType;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getPigpenId() {
        return pigpenId;
    }

    public void setPigpenId(Long pigpenId) {
        this.pigpenId = pigpenId;
    }

    public Long getSwineryId() {
        return swineryId;
    }

    public void setSwineryId(Long swineryId) {
        this.swineryId = swineryId;
    }

    public Double getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Double totalNum) {
        this.totalNum = totalNum;
    }

    public Double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(Double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public int getDayAge() {
        return dayAge;
    }

    public void setDayAge(int dayAge) {
        this.dayAge = dayAge;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Long getRelateBillId() {
        return relateBillId;
    }

    public void setRelateBillId(Long relateBillId) {
        this.relateBillId = relateBillId;
    }

    public String getIsSell() {
        return isSell;
    }

    public void setIsSell(String isSell) {
        this.isSell = isSell;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getPreLineNumber() {
        return preLineNumber;
    }

    public void setPreLineNumber(Long preLineNumber) {
        this.preLineNumber = preLineNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRelateBillCode() {
        return relateBillCode;
    }

    public void setRelateBillCode(String relateBillCode) {
        this.relateBillCode = relateBillCode;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

}
