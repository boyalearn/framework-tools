package com.framework.websocket.core.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String cmd;

    private String body;
}
