package xn.pigfarm.model.production;

public class SemenBillModel extends BillModel implements java.io.Serializable {

    private static final long serialVersionUID = 7472201423819854438L;

    // 精液来源
    private String origin;

    // 相关单据id
    private Long relateBillId;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getRelateBillId() {
        return relateBillId;
    }

    public void setRelateBillId(Long relateBillId) {
        this.relateBillId = relateBillId;
    }
    
}
