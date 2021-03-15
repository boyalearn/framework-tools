package com.framework.tool;

import com.framework.tool.config.HttpSessionConfigurator;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/*@Component
@ServerEndpoint(value = "/ws", configurator = HttpSessionConfigurator.class)*/
public class WebSocketServer {
    @OnOpen
    public void onOpen(Session session) {
        String id = session.getId();
        System.out.println(id);

    }
}
