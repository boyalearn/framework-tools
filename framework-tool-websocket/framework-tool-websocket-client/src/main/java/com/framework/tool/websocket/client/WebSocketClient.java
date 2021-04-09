package com.framework.tool.websocket.client;

import com.framework.tool.websocket.context.SessionContext;
import com.framework.tool.websocket.handler.HeartbeatHandler;
import com.framework.tool.websocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@ClientEndpoint
public class WebSocketClient {

    private static String URL;

    private HeartbeatHandler heartbeatHandler;

    private final static String PING = "ping";

    private static ScheduledExecutorService HEART_SIGNAL_POOL = Executors.newScheduledThreadPool(1);


    private SessionContext sessionContext;

    private ScheduledFuture<?> scheduledFuture;


    private boolean shutdown = false;

    public WebSocketClient() {
    }


    @OnOpen
    public void onOpen(Session session) {
        System.out.println("client connection...");
        this.sessionContext = new SessionContext(session);
        heartbeatHandler = new HeartbeatHandler(sessionContext);
        this.scheduledFuture = HEART_SIGNAL_POOL.scheduleAtFixedRate(heartbeatHandler, 2, 2, TimeUnit.SECONDS);

    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("Client onMessage: " + message);
        if (PING.equals(JsonUtils.jsonToMessage(message).getCommand())) {
            try {
                heartbeatHandler.handler();
            } catch (Exception e) {
                log.error("heartbeatHandler exception");
            }
            return;
        }
        try {
            Thread.sleep(4 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("on error happen ", error);
    }

    @OnClose
    public void onClose(Session session) {
        this.scheduledFuture.cancel(true);
        System.out.println("client close...");
        try {
            session.close();
        } catch (IOException e) {
            log.error("close session error");
        }

        if (!shutdown) {
            reConnect();
        }

    }

    public void shutDown() {
        shutdown = true;
        onClose(this.sessionContext.getSession());
    }


    public WebSocketClient(String url) {
        URL = url;
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = URI.create(URL);
            container.connectToServer(WebSocketClient.class, uri);
        } catch (DeploymentException | IOException e) {
            log.error("connection exception", e);
        }
    }

    public void reConnect() {
        while (!shutdown && !connect()) {
            sleep(2 * 1000);
        }
    }

    private boolean connect() {
        log.error("re connect ...");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = URI.create(URL);
            container.connectToServer(WebSocketClient.class, uri);
            return true;
        } catch (DeploymentException | IOException e) {
            return false;
        }
    }

    private void sleep(long timeOut) {
        try {
            Thread.sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
