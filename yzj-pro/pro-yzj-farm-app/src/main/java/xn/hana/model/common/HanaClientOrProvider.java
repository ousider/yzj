package xn.hana.model.common;

import java.io.Serializable;
import java.util.List;

public class HanaClientOrProvider implements Serializable{

    private static final long serialVersionUID = -5168876401400388040L;

    // 业务伙伴类别
    private String MTC_CardType;

    // 客户/供应商编号
    private String MTC_CardCode;

    // 客户/供应商名称
    private String MTC_CardName;

    // 客户/供应商分类
    private String MTC_GroupCode;

    // 客户/供应商简称
    private String MTC_CardFName;

    // 客户/供应商地址
    private String MTC_AliasName;

    // 公司（法人）关系
    private List<MTC_Branch> detailList;

    public String getMTC_CardType() {
        return MTC_CardType;
    }

    public void setMTC_CardType(String mTC_CardType) {
        MTC_CardType = mTC_CardType;
    }

    public String getMTC_CardCode() {
        return MTC_CardCode;
    }

    public void setMTC_CardCode(String mTC_CardCode) {
        MTC_CardCode = mTC_CardCode;
    }

    public String getMTC_CardName() {
        return MTC_CardName;
    }

    public void setMTC_CardName(String mTC_CardName) {
        MTC_CardName = mTC_CardName;
    }

    public String getMTC_GroupCode() {
        return MTC_GroupCode;
    }

    public void setMTC_GroupCode(String mTC_GroupCode) {
        MTC_GroupCode = mTC_GroupCode;
    }

    public String getMTC_CardFName() {
        return MTC_CardFName;
    }

    public void setMTC_CardFName(String mTC_CardFName) {
        MTC_CardFName = mTC_CardFName;
    }

    public String getMTC_AliasName() {
        return MTC_AliasName;
    }

    public void setMTC_AliasName(String mTC_AliasName) {
        MTC_AliasName = mTC_AliasName;
    }

    public List<MTC_Branch> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<MTC_Branch> detailList) {
        this.detailList = detailList;
    }

    public static class MTC_Branch implements Serializable {

        private static final long serialVersionUID = -4558990091887758925L;

        private String MTC_Branch;

        public String getMTC_Branch() {
            return MTC_Branch;
        }

        public void setMTC_Branch(String mTC_Branch) {
            MTC_Branch = mTC_Branch;
        }
    }

}