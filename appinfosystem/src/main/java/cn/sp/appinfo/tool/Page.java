package cn.sp.appinfo.tool;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分页工具类
 */
@Component
public class Page {
    //全部页数,显示总页数
    private Integer pages;
    //总数量
    private Integer rows;
    //分页大小,从哪到哪
    private Integer pageSize = 5;
    //当前页数
    private Integer pageNo = 1;
    //返回数据
    private List list;

    public Integer getPages() {
        if (rows % pageSize == 0) {
            pages = rows / pageSize;
        } else {
            pages = rows / pageSize + 1;
        }
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    //获取分页的起始位置
    public Integer getFirst() {
        return (pageNo - 1) * getPageSize();
    }
}
