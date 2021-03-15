package com.framework.tool.websocket.endpoint;

import javax.servlet.http.HttpSession;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class DefaultEndpoint extends Endpoint {
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        HttpSession httpSession = (HttpSession)endpointConfig.getUserProperties().get(HttpSession.class.getName());
        System.out.println(httpSession.getId());
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                System.out.println(s);
            }
        });
    }
}
