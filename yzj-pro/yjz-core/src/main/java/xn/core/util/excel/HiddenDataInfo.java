package xn.core.util.excel;

/**
 * @Description: 隐藏域列信息
 * @author zhangjs
 * @date 2016年9月2日 上午9:46:37
 */
public class HiddenDataInfo {

    // 隐藏域当前列别名
    private String alias;

    // 当前名称是隐藏域中第几组（普通下拉框为2列一组，2级级联下拉框隐藏域为5列一组）
    private int groupNum;

    // 隐藏域当前下拉框对应的数据长度
    private int size;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

}
