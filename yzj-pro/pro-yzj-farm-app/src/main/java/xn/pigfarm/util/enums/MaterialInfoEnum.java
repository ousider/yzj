package xn.pigfarm.util.enums;

import xn.core.data.enums.interfaces.IInfoEnum;

/**
 * @Description:需要赋值名称的枚举
 * @author Zhangjc
 * @date 2016年5月13日 下午1:44:32
 */
public enum MaterialInfoEnum implements IInfoEnum {

    /* 需要区分猪场ID模板 */
    MATERIAL_INFO(
            "materialId,groupId,businessCode,relatedId,materialName,materialType,unit,price,specNum,outputMinQty,spec,specAll,freePercent,supplierId,manufacturer,materialNotes,firstFinanceMaterialType,secondFinanceMaterialType,materialFirstClass,materialSecondClass,materialClassNumber,financeSubject",
            "materialId,groupId,businessCode,relatedId,materialName,materialType,unit,price,specNum,outputMinQty,spec,specAll,freePercent,supplierId,manufacturer,materialNotes,firstFinanceMaterialType,secondFinanceMaterialType,materialFirstClass,materialSecondClass,materialClassNumber,financeSubject");

    // MaterialInfo缓存中存放字段
    private final String id;

    private final String name;

    MaterialInfoEnum(String id, String name) {
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
