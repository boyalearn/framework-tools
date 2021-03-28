package com.framework.websocket.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message<T> {

    private String command;

    private T body;

}
