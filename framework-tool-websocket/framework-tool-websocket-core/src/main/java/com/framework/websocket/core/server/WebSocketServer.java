package com.framework.websocket.core.server;

import com.framework.websocket.core.acceptor.Acceptor;
import com.framework.websocket.core.acceptor.DefaultAcceptor;
import com.framework.websocket.core.config.ServerConfig;
import com.framework.websocket.core.context.ServerContext;
import com.framework.websocket.core.event.DefaultEventPublisher;
import com.framework.websocket.core.event.EventPublisher;
import com.framework.websocket.core.listener.EventListener;
import com.framework.websocket.core.listener.HeartbeatListener;
import com.framework.websocket.core.listener.MessageReceiveListener;
import com.framework.websocket.core.protocol.MessageProtocol;
import com.framework.websocket.core.protocol.Protocol;
import com.framework.websocket.core.reactor.DefaultReactor;
import com.framework.websocket.core.reactor.Reactor;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.ArrayList;
import java.util.List;

@Import(ServerEndpointExporter.class)
public class WebSocketServer {

    private EventPublisher publisher;

    private Acceptor acceptor;

    private Reactor reactor;

    private Protocol protocol;

    private ServerConfig serverConfig;

    public WebSocketServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        buildProtocol();
        buildReactor();
        buildAcceptor();
        buildPublisher();
        addListenerToPublisher();
        ServerContext.setEventPublisher(this.publisher);
    }

    private void buildReactor() {
        if (null == this.serverConfig.getReactor()) {
            this.reactor = new DefaultReactor();
        } else {
            this.reactor = this.serverConfig.getReactor();
        }
        this.reactor.addHandlers(this.serverConfig.getHandlers());
    }

    private void buildProtocol() {
        if (null == this.serverConfig.getProtocol()) {
            this.protocol = new MessageProtocol();
        } else {
            this.protocol = this.serverConfig.getProtocol();
        }
    }

    private void buildAcceptor() {
        if (null == this.serverConfig.getAcceptor()) {
            this.acceptor = new DefaultAcceptor(this.protocol, this.reactor);
        } else {
            this.acceptor = this.serverConfig.getAcceptor();
        }
    }

    private void buildPublisher() {
        if (null == this.serverConfig.getPublisher()) {
            this.publisher = new DefaultEventPublisher();
        } else {
            this.publisher = this.serverConfig.getPublisher();
        }
    }

    private void addListenerToPublisher() {
        List<EventListener> defaultListeners = new ArrayList<>();
        defaultListeners.add(new HeartbeatListener());
        defaultListeners.add(new MessageReceiveListener(this.acceptor));
        this.publisher.addListeners(defaultListeners);
        if (null != serverConfig.getListeners()) {
            this.publisher.addListeners(serverConfig.getListeners());
        }
    }

}
