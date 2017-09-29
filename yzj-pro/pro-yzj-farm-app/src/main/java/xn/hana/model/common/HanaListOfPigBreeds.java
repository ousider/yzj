package xn.hana.model.common;

import java.io.Serializable;

public class HanaListOfPigBreeds implements Serializable {

    private static final long serialVersionUID = -8833556104071691033L;

    // 品种ID
    private String MTC_Code;

    // 品种描述
    private String MTC_Description;

    public String getMTC_Code() {
        return MTC_Code;
    }

    public void setMTC_Code(String mTC_Code) {
        MTC_Code = mTC_Code;
    }

    public String getMTC_Description() {
        return MTC_Description;
    }

    public void setMTC_Description(String mTC_Description) {
        MTC_Description = mTC_Description;
    }

}
