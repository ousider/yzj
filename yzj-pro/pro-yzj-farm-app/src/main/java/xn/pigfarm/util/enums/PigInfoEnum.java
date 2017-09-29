package xn.pigfarm.util.enums;

import xn.core.data.enums.interfaces.IPigInfoEnum;

/**
 * @Description:需要赋值名称的枚举
 * @author Zhangjc
 * @date 2016年5月13日 下午1:44:32
 */
public enum PigInfoEnum implements IPigInfoEnum {

    /* 需要区分猪场ID 模板 */
    PIG_INFO("earBrand,earShort,pigType,pigClassName,breedName,birthDate,houseName,pigpenName",
            "earBrand,earShort,pigType,pigClassName,breedName,birthDate,houseName,pigpenName"),
    PIG_INFO_WITHID(
            "swineryId,parity,lastBillId,houseId,pigpenId,earBrand,earShort,pigType,pigTypeName,sex,pigClass,pigClassName,breedId,breedName,birthDate,houseName,pigpenName",
            "swineryId,parity,lastBillId,houseId,pigpenId,earBrand,earShort,pigType,pigTypeName,sex,pigClass,pigClassName,breedId,breedName,birthDate,houseName,pigpenName"),
    PIG_INFO_HOUSE_TYPE("earBrand,houseType", "earBrand,houseType");

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
