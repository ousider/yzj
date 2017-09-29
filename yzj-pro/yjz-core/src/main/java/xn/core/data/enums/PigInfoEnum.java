package xn.core.data.enums;

import xn.core.data.enums.interfaces.IPigInfoEnum;

/**
 * @Description:需要赋值名称的枚举
 * @author Zhangjc
 * @date 2016年5月13日 下午1:44:32
 */
public enum PigInfoEnum implements IPigInfoEnum {

    /* 需要区分猪场ID */
    BILL_VIEW("earBrand,pigClassName,materialId", "earBrand,pigClassName,materialId"),
    EAR_BRAND("earBrand", "earBrand"),
    PIG_VIEW_INFO("earBrand,earShort,earThorn,electronicEarNo,pigType,sex,breedId,breedName,houseId,houseName,pigpenId,pigpenName,farmId,birthDate,materialId",
            "earBrand,earShort,earThorn,electronicEarNo,pigType,sex,breedId,breedName,houseId,houseName,pigpenId,pigpenName,farmId,birthDate,materialId");

    // pigInfo缓存中存放字段
    private final String id;

    private final String name;

    PigInfoEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
