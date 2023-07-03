package com.hh.jushuitan.base;

/**
 * @author gx
 * @version 1.0.0
 * @since 7/1/23
 **/

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public class PageResultFactory {
    /**
     * 默认页结果
     *
     * @param page 页面
     * @return {@link PageResult}<{@link List}<{@link T}>>
     */
    public static <T> PageResult<T> defaultPageResult(IPage<T> page) {
        PageResult<T> result = new PageResult<>();
        if (ObjectUtil.isNotNull(page)) {
            result.setList(page.getRecords());
            result.setTotal(page.getTotal());
            result.setPageIndex(page.getCurrent());
            result.setPageSize(page.getSize());
        }
        return result;
    }
}
