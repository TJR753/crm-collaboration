package com.example.crm.workbench.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageVo<T> {
    private Integer total;
    private List<T> pageList;
}
