package com.kgms.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 */
@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据列表 */
    private List<T> records;

    /** 总记录数 */
    private Long total;

    /** 当前页码 */
    private Integer page;

    /** 每页条数 */
    private Integer pageSize;

    /** 总页数 */
    private Integer pages;

    public PageResult(List<T> records, Long total, Integer page, Integer pageSize) {
        this.records = records;
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

    public static <T> PageResult<T> of(List<T> records, Long total, Integer page, Integer pageSize) {
        return new PageResult<>(records, total, page, pageSize);
    }
}
