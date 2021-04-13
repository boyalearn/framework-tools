package com.framework.websocket.server.controller;

import com.broheim.websocket.spring.annonation.Command;
import com.broheim.websocket.spring.annonation.WebSocketController;
import com.framework.websocket.context.ChannelContext;
import org.springframework.stereotype.Component;

@Component
@WebSocketController("/ws")
public class DemoController {
    @Command("hello")
    public void hello(ChannelContext channelContext,String message){
        System.out.println(message);
    }
}
