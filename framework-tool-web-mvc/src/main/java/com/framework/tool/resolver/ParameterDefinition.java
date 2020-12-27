package com.framework.tool.resolver;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ParameterDefinition {
    private Class<?> type;

    private List<FieldDefinition> fieldDefinitions=new ArrayList<>();
}
