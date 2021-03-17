package com.framework.websocket.message;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {

    private String command;

    private Object body;

}
