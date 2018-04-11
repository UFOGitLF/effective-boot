package com.fly.common.utils;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * <p>
 * @author liufei
 */
@Data
public class PageData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private int totalCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int currPage;
    /**
     * 列表数据
     */
    private List<?> list;

    public PageData(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * 分页
     */
    public PageData(Page<?> page) {
        this.list = page.getContent();
        this.totalCount = page.getNumber();
        this.pageSize = page.getSize();
        this.currPage = page.getNumberOfElements();
        this.totalPage = page.getTotalPages();
    }

}
