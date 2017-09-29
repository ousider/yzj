package xn.core.util.excel;


/**
 * @Description: 级联下拉框隐藏域中，每组每个猪栏起别名的相关信息（起始结束行、对应列）
 * @author zhangjs
 * @date 2016年9月6日 下午12:02:35
 */
public class CascadeHeight {

    // 起始行
    private int startNum;

    // 结束行
    private int endNum;

    // 对应列
    private String column;

    // 对应组数
    private int groupNum;

    // 别名
    private String alias;

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
