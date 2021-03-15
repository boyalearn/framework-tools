package com.framework.tool.websocket.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Message<T> {

    Command command;

    T body;
}
