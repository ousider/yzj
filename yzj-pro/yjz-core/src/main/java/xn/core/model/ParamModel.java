package xn.core.model;

import java.io.Serializable;

/**
 * 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author fangc
 * @date 2016年4月14日 下午6:03:06
 */
public class ParamModel implements Serializable {
    /**
     * @Description: TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = -5051406498105566049L;
	
    
    // 字段名
    private String field;
	
    // 表列名
    private String columnName;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}
