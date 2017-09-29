package xn.hana.model.common;

import java.io.Serializable;

public class HanaMaterial implements Serializable {

    private static final long serialVersionUID = -8252046892566218531L;

    // 物料编号
    private String MTC_ItemCode;

    // 物料描述
    private String MTC_ItemName;

    // 物料规格
    private String MTC_Spec;

    // 物料组
    private String MTC_ItmsGrpCod;

    // 计量单位
    private String MTC_Unit;


    // 供应商
    private String MTC_CardCode;

    // 制造商
    private String MTC_ValidComm;

    // 管理物料由
    private String MTC_ManBtchNum;

    // 管理方法
    // private String MTC_MngMtthod;

    public String getMTC_ItemCode() {
        return MTC_ItemCode;
    }

    public void setMTC_ItemCode(String mTC_ItemCode) {
        MTC_ItemCode = mTC_ItemCode;
    }

    public String getMTC_ItemName() {
        return MTC_ItemName;
    }

    public void setMTC_ItemName(String mTC_ItemName) {
        MTC_ItemName = mTC_ItemName;
    }

    public String getMTC_Spec() {
        return MTC_Spec;
    }

    public void setMTC_Spec(String mTC_Spec) {
        MTC_Spec = mTC_Spec;
    }

    public String getMTC_ItmsGrpCod() {
        return MTC_ItmsGrpCod;
    }

    public void setMTC_ItmsGrpCod(String mTC_ItmsGrpCod) {
        MTC_ItmsGrpCod = mTC_ItmsGrpCod;
    }

    public String getMTC_Unit() {
        return MTC_Unit;
    }

    public void setMTC_Unit(String mTC_Unit) {
        MTC_Unit = mTC_Unit;
    }

    public String getMTC_CardCode() {
        return MTC_CardCode;
    }

    public void setMTC_CardCode(String mTC_CardCode) {
        MTC_CardCode = mTC_CardCode;
    }

    public String getMTC_ValidComm() {
        return MTC_ValidComm;
    }

    public void setMTC_ValidComm(String mTC_ValidComm) {
        MTC_ValidComm = mTC_ValidComm;
    }

    public String getMTC_ManBtchNum() {
        return MTC_ManBtchNum;
    }

    public void setMTC_ManBtchNum(String mTC_ManBtchNum) {
        MTC_ManBtchNum = mTC_ManBtchNum;
    }
    //
    // public String getMTC_MngMtthod() {
    // return MTC_MngMtthod;
    // }
    //
    // public void setMTC_MngMtthod(String mTC_MngMtthod) {
    // MTC_MngMtthod = mTC_MngMtthod;
    // }

}
