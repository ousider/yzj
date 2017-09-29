

package xn.pigfarm.model.production;

import java.util.Date;

import xn.core.model.BaseModel;

/**
 * 表：
 */
public class ValidSemenModel extends BaseModel implements java.io.Serializable {

    private static final long serialVersionUID = -8722033129640546819L;

    // 猪只ID
    private Long pigId;

    // 耳号
    private String earBrand;

    // 精液代码
    private String semenCode;

    // 精液号
    private Long semenId;

    // 采精日期
    private Date semenCollectionDate;

    public Long getPigId() {
        return pigId;
    }

    public void setPigId(Long pigId) {
        this.pigId = pigId;
    }

    public String getEarBrand() {
        return earBrand;
    }

    public void setEarBrand(String earBrand) {
        this.earBrand = earBrand;
    }

    public String getSemenCode() {
        return semenCode;
    }

    public void setSemenCode(String semenCode) {
        this.semenCode = semenCode;
    }

    public Long getSemenId() {
        return semenId;
    }

    public void setSemenId(Long semenId) {
        this.semenId = semenId;
    }

    public Date getSemenCollectionDate() {
        return semenCollectionDate;
    }

    public void setSemenCollectionDate(Date semenCollectionDate) {
        this.semenCollectionDate = semenCollectionDate;
    }

}
