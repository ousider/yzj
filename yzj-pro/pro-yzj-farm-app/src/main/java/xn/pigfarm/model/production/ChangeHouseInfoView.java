package xn.pigfarm.model.production;

import xn.core.model.BaseModel;

public class ChangeHouseInfoView extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = 6974125475943542638L;

    // 猪只id
    private Long pigId;

    // 公司id
    private Long companyId;

    // 猪场id
    private Long farmId;

    // 猪群id
    private Long swineryId;

    // 猪类别
    private String pigType;

    // 猪性别
    private String sex;

    // 产线id
    private Long lineId;

    // 猪舍id
    private Long houseId;

    // 猪栏id
    private Long pigPenId;

    // 猪只状态
    private Long pigClass;

    // 胎次
    private Long parity;

    // 上次转舍记录
    private Long changeHouseId;

    // changeHouse表排序号
    private Long houseSortNbr;

    // breed表生产号
    private Long proNo;

    // his表排序号
    private Long hisSortNbr;

    public Long getPigId() {
        return pigId;
    }

    public void setPigId(Long pigId) {
        this.pigId = pigId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFarmId() {
        return farmId;
    }

    public void setFarmId(Long farmId) {
        this.farmId = farmId;
    }

    public Long getSwineryId() {
        return swineryId;
    }

    public void setSwineryId(Long swineryId) {
        this.swineryId = swineryId;
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

    public Long getPigPenId() {
        return pigPenId;
    }

    public void setPigPenId(Long pigPenId) {
        this.pigPenId = pigPenId;
    }

    public Long getPigClass() {
        return pigClass;
    }

    public void setPigClass(Long pigClass) {
        this.pigClass = pigClass;
    }

    public Long getParity() {
        return parity;
    }

    public void setParity(Long parity) {
        this.parity = parity;
    }

    public Long getChangeHouseId() {
        return changeHouseId;
    }

    public void setChangeHouseId(Long changeHouseId) {
        this.changeHouseId = changeHouseId;
    }

    public Long getHouseSortNbr() {
        return houseSortNbr;
    }

    public void setHouseSortNbr(Long houseSortNbr) {
        this.houseSortNbr = houseSortNbr;
    }

    public Long getProNo() {
        return proNo;
    }

    public void setProNo(Long proNo) {
        this.proNo = proNo;
    }

    public Long getHisSortNbr() {
        return hisSortNbr;
    }

    public void setHisSortNbr(Long hisSortNbr) {
        this.hisSortNbr = hisSortNbr;
    }

}
