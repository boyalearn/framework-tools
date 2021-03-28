package com.framework.websocket.context;

import lombok.Getter;
import lombok.Setter;

import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

@Getter
@Setter
public class ChannelContext {

    private Session session;

    private EndpointConfig endpointConfig;

    private HandshakeRequest request;

    private HandshakeResponse response;

}
