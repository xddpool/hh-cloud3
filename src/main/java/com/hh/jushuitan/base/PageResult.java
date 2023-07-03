package com.hh.jushuitan.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/
@Setter
@Getter
public class PageResult<T> implements Serializable {

    private static final Long DEFAULT_PAGE_INDEX = 1L;
    private static final Long DEFAULT_PAGE_SIZE = 20L;

    private Long total;
    private List<T> list;
    private Long pageIndex;
    private Long pageSize;
    private Long pages;

    private Boolean hasPrevious;
    private Boolean hasNext;
    private Boolean isFirst;
    private Boolean isLast;

    public PageResult(Long pageIndex, Long pageSize, Long pages,
                      Long total, List<T> list,
                      boolean hasPrevious, boolean hasNext, boolean isFirst, boolean isLast) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
        this.list = list;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.isFirst = isFirst;
        this.isLast = isLast;
    }

    public PageResult(Long pageIndex, Long pageSize, Long pages,
                      Long total, List<T> list) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.pages = pages;
        this.total = total;
        this.list = list;
        this.judgePageResult();
    }

    public PageResult(Long pageSize, Long pages, Long total, List<T> list) {
        this(DEFAULT_PAGE_INDEX, pageSize, pages, total, list, false, false, false, false);
        this.judgePageResult();
    }

    public PageResult(Long pages, Long total, List<T> list) {
        this(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE, pages, total, list, false, false, false, false);
        this.judgePageResult();
    }

    public PageResult() {
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    private void judgePageResult() {
        this.isFirst = this.pageIndex == 1L;
        this.hasPrevious = this.pageIndex > 1L;
        this.hasNext = this.pageIndex < this.pages;
        this.isLast = this.pageIndex.equals(this.pages) || this.pages == 0L;
    }

}
