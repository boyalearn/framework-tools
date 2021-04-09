package com.framework.websocket.server.endpoint;


import com.framework.websocket.DefaultWebSocketServer;
import com.framework.websocket.configurator.HttpConfigurator;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws", configurator = HttpConfigurator.class)
public class DemoServletEndpoint extends DefaultWebSocketServer {
}
