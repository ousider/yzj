package xn.core.util.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

import xn.core.util.data.Maps;

public class BasePageInfo implements Serializable {

	private static final long serialVersionUID = -4066692514374768755L;

    // 当前页,名字必须为page
    // private int pageNum;
    //
    // // 每页大小,名字必须为rows
    // private int rows;
    //
    // // 排序字段
    // private String sort;
    //
    // // 排序规则
    // private String order;
    //
    // // 总条数
    // private long total;
    //
    // // 查询记录
    // private List list;

    private Page pageList;

    // private BasePageInfo() {
    //
    // pageList = new Page();
    // }

    public BasePageInfo(Map<String, Object> map) {

        pageList = new Page();
        if (map != null) {
            int pageNum = Maps.getInt(map, "page", 1);
            int rows = Maps.getInt(map, "rows", 25);
            pageList = new Page(pageNum, rows);
            // pageList.set("sort", Maps.getString(map, "sort"));
            // pageList.setOrderBy(Maps.getString(map, "order"));
        }
    }

    public int getPage() {

        int pageNum = pageList.getPageNum();
        if (pageNum <= 0) {
            pageNum = 1;
            pageList.setPageNum(pageNum);
        }
        return pageNum;
    }

    public void setPage(int page) {

        pageList.setPageNum(page);
    }

    public int getRows() {

        int rows = pageList.getPageSize();
        if (rows <= 0) {
            rows = 25;
            pageList.setPageSize(rows);
        }
        return rows;
    }

    public void setRows(int rows) {

        pageList.setPageSize(rows);
    }

    // public String getSort() {
    // return sort;
    // }
    //
    // public void setSort(String sort) {
    // this.sort = sort;
    // }

    public String getOrder() {

        return pageList.getOrderBy();
    }

    public void setOrder(String order) {

        pageList.setOrderBy(order);
    }

    public long getTotal() {

        return pageList.getTotal();
    }

    public void setTotal(long total) {

        pageList.setTotal(total);
    }

    public void setList(List list) {

        pageList.clear();
        pageList.addAll(list);
        if (list instanceof Page) {
            Page inPage = (Page) list;
            pageList.setTotal(inPage.getTotal());
        }
    }

    public List getList() {

        return pageList;
    }

    public int getSartRow() {

        return pageList.getStartRow();
    }

    public int getEndRow() {

        return pageList.getEndRow();
    }
}
