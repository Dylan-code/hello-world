package com.marx.vo;

import com.wobangkj.bean.Pageable;
import com.wobangkj.domain.Condition;
import lombok.Data;


@Data
public class PageInfo{
    private Integer page;
    private Integer limit;

    public Condition toCon() {
        Condition condition = Condition.builder().build();
        condition.setPage(this.page);
        condition.setSize(this.limit);
        return condition;
    }
}
