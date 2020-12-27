package com.framework.tool.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReturnValueDefinition {
    List<GetFieldDefinition> fieldDefinitions = new ArrayList<>();
}
