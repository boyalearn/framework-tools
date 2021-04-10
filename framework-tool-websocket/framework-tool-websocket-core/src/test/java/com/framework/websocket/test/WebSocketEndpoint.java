package com.framework.websocket.test;

import com.framework.websocket.core.configurator.HttpConfigurator;
import com.framework.websocket.core.endpoint.AbstractWebSocketServerEndpoint;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws", configurator = HttpConfigurator.class)
public class WebSocketEndpoint extends AbstractWebSocketServerEndpoint {

}
