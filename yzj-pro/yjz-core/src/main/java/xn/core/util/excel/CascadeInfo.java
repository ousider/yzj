package xn.core.util.excel;

/**
 * @Description: 级联下拉框隐藏域列信息
 * @author zhangjs
 * @date 2016年9月2日 上午9:46:37
 */
public class CascadeInfo {

    // 当前列的INDEX(XML中一致)
    private String index;

    // 上一级列的INDEX(XML中一致)
    private String supIndex;

    // 对应组数
    private int groupNum;

    // 该组的条数
    private int size;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSupIndex() {
        return supIndex;
    }

    public void setSupIndex(String supIndex) {
        this.supIndex = supIndex;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
