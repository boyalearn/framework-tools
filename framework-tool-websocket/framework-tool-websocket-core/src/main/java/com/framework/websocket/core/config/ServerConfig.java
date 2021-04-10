package com.framework.websocket.core.config;

import com.framework.websocket.core.acceptor.Acceptor;
import com.framework.websocket.core.acceptor.DefaultAcceptor;
import com.framework.websocket.core.context.ServerContext;
import com.framework.websocket.core.event.DefaultEventPublisher;
import com.framework.websocket.core.event.EventPublisher;
import com.framework.websocket.core.handler.Handler;
import com.framework.websocket.core.listener.EventListener;
import com.framework.websocket.core.listener.HeartbeatListener;
import com.framework.websocket.core.listener.MessageReceiveListener;
import com.framework.websocket.core.protocol.MessageProtocol;
import com.framework.websocket.core.protocol.Protocol;
import com.framework.websocket.core.reactor.DefaultReactor;
import com.framework.websocket.core.reactor.Reactor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServerConfig {

    private EventPublisher publisher;

    private Protocol protocol;

    private Acceptor acceptor;

    private Reactor reactor;

    private List<Handler> handlers = new ArrayList<>();

    private Builder builder;


    public ServerConfig(Builder builder) {
        this.builder = builder;
    }


    public void parserConfig() {
        buildProtocol();
        buildReactor();
        buildAcceptor();
        buildPublisher();
        addListenerToPublisher();
    }

    private void buildReactor() {
        if (null == this.builder.getReactor()) {
            this.reactor = new DefaultReactor();
        } else {
            this.reactor = this.builder.getReactor();
        }
        this.reactor.addHandlers(this.builder.getHandlers());
    }

    private void buildProtocol() {
        if (null == this.builder.getProtocol()) {
            this.protocol = new MessageProtocol();
        } else {
            this.protocol = this.builder.getProtocol();
        }
    }

    private void buildAcceptor() {
        if (null == this.builder.getAcceptor()) {
            this.acceptor = new DefaultAcceptor(this.protocol, this.reactor);
        } else {
            this.acceptor = this.builder.getAcceptor();
        }
    }

    private void buildPublisher() {
        if (null == this.builder.getPublisher()) {
            this.publisher = new DefaultEventPublisher();
        } else {
            this.publisher = this.builder.getPublisher();
        }
    }

    private void addListenerToPublisher() {
        List<EventListener> defaultListeners = new ArrayList<>();
        defaultListeners.add(new HeartbeatListener());
        defaultListeners.add(new MessageReceiveListener(this.acceptor));
        this.publisher.addListeners(defaultListeners);
        if (null != builder.getListeners()) {
            this.publisher.addListeners(builder.getListeners());
        }
    }


    @Getter
    public static class Builder {
        private EventPublisher publisher;

        private Acceptor acceptor;

        private Reactor reactor;

        private Protocol protocol;

        private List<EventListener> listeners;

        private List<Handler> handlers = new ArrayList<>();

        public ServerConfig build() {
            return new ServerConfig(this);
        }


        public Builder publisher(EventPublisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder acceptor(Acceptor acceptor) {
            this.acceptor = acceptor;
            return this;
        }

        public Builder reactor(Reactor reactor) {
            this.reactor = reactor;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder handlers(List<Handler> handlers) {
            this.handlers = handlers;
            return this;
        }

        public Builder listeners(List<EventListener> listeners) {
            this.listeners = listeners;
            return this;
        }
    }
}
