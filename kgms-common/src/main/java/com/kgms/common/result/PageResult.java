package com.kgms.common.result;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PageResult<T> implements Serializable {
    private List<T> records;
    private Long total;
    private Integer page;
    private Integer pageSize;
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
