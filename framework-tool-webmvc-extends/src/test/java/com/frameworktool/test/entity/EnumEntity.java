package com.frameworktool.test.entity;

import com.frameworktool.test.enums.TypeEnum;
import lombok.Data;

@Data
public class EnumEntity {
    private TypeEnum type;

    private String name;
}
