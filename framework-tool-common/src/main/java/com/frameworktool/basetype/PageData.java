package com.frameworktool.basetype;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageData<T> {

    private int total;

    private int page;

    private int pageSize;

    private List<T> list;
}
