package xn.core.model.system;

import java.io.Serializable;

/**
 * @Description:系统生成
 * @author :系统生成
 * @date :2016-5-12 14:48:38
 *       表：
 */
public class BcodeView implements Serializable {
    
    private static final long serialVersionUID = 465428218279966281L;

    private Long rowId;

    private String bcodeName;

    private String typeCode;

    private Long limitNum;

    private String level;

    private Long serialLength;

    private Long serialMax;

    private Long serialMin;

    private String prifixCode;

    private Long houseId;

    private String isUseBdate;

    private String lastNum;

    private String nextSeial;

    private String notes;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getBcodeName() {
        return bcodeName;
    }

    public void setBcodeName(String bcodeName) {
        this.bcodeName = bcodeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Long getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Long limitNum) {
        this.limitNum = limitNum;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getSerialLength() {
        return serialLength;
    }

    public void setSerialLength(Long serialLength) {
        this.serialLength = serialLength;
    }

    public Long getSerialMax() {
        return serialMax;
    }

    public void setSerialMax(Long serialMax) {
        this.serialMax = serialMax;
    }

    public Long getSerialMin() {
        return serialMin;
    }

    public void setSerialMin(Long serialMin) {
        this.serialMin = serialMin;
    }

    public String getPrifixCode() {
        return prifixCode;
    }

    public void setPrifixCode(String prifixCode) {
        this.prifixCode = prifixCode;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getIsUseBdate() {
        return isUseBdate;
    }

    public void setIsUseBdate(String isUseBdate) {
        this.isUseBdate = isUseBdate;
    }

    public String getLastNum() {
        return lastNum;
    }

    public void setLastNum(String lastNum) {
        this.lastNum = lastNum;
    }

    public String getNextSeial() {
        return nextSeial;
    }

    public void setNextSeial(String nextSeial) {
        this.nextSeial = nextSeial;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}




