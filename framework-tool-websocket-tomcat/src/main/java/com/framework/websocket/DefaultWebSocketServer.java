package com.framework.websocket;

import com.framework.websocket.annotation.EndpointPath;
import com.framework.websocket.context.ChannelContext;
import com.framework.websocket.exception.ConnectionException;
import com.framework.websocket.handler.CommandHandler;
import com.framework.websocket.handler.ConnectionHandler;
import com.framework.websocket.handler.ErrorHandler;
import com.framework.websocket.session.DefaultSessionManager;
import com.framework.websocket.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DefaultWebSocketServer implements ApplicationContextAware, InitializingBean {

    private ConnectionHandler connectionHandler;

    public static SessionManager sessionManager = new DefaultSessionManager();

    private ErrorHandler errorHandler;

    private List<CommandHandler> commandHandlers = new ArrayList<>();

    private static ApplicationContext applicationContext;

    public DefaultWebSocketServer() {
        try {
            if (null != applicationContext) {
                this.afterPropertiesSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        ChannelContext channelContext = new ChannelContext();
        channelContext.setRequest((HandshakeRequest) config.getUserProperties().get(HandshakeRequest.class.getName()));
        channelContext.setResponse((HandshakeResponse) config.getUserProperties().get(HandshakeResponse.class.getName()));
        channelContext.setEndpointConfig(config);
        channelContext.setSession(session);
        try {
            this.connectionHandler.onOpen(channelContext);
            sessionManager.addSession(session);
        } catch (ConnectionException e) {
            throw new ConnectionException();
        }
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.connectionHandler.onClose(session, closeReason);
        this.sessionManager.removeSession(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.debug("收到来自窗口:{}", message);
        for (CommandHandler commandHandler : commandHandlers) {
            commandHandler.handle(session, message);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        errorHandler.handlingException(session, error);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServerEndpoint endpointAnnotation = this.getClass().getAnnotation(ServerEndpoint.class);
        if (null == endpointAnnotation) {
            throw new Exception("ServerEndpoint must have a @ServerEndpoint on class");
        }
        String path = endpointAnnotation.value();
        if (StringUtils.isEmpty(path)) {
            throw new Exception("@ServerEndpoint must have a path value");
        }

        String[] connectionHandlerNames = applicationContext.getBeanNamesForType(ConnectionHandler.class);
        List<ConnectionHandler> connectionHandles = new ArrayList<>();
        for (String connectionHandlerName : connectionHandlerNames) {
            ConnectionHandler connectionHandler = (ConnectionHandler) applicationContext.getBean(connectionHandlerName);
            EndpointPath endpointPath = connectionHandler.getClass().getAnnotation(EndpointPath.class);
            if (path.equals(endpointPath.value())) {
                connectionHandles.add(connectionHandler);
            }
        }
        if (connectionHandles.size() == 1) {
            this.connectionHandler = connectionHandles.get(0);
        } else {
            throw new Exception("ConnectionHandler for " + path + " path too match or no have");
        }

        String[] commandHandlerNames = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String commandHandlerName : commandHandlerNames) {
            CommandHandler commandHandler = (CommandHandler) applicationContext.getBean(commandHandlerName);
            EndpointPath endpointPath = connectionHandler.getClass().getAnnotation(EndpointPath.class);
            if (path.equals(endpointPath.value())) {
                this.commandHandlers.add(commandHandler);
            }
        }

        String[] errorHandlerNames = applicationContext.getBeanNamesForType(ErrorHandler.class);
        List<ErrorHandler> errorHandles = new ArrayList<>();
        for (String errorHandlerName : errorHandlerNames) {
            ErrorHandler errorHandler = (ErrorHandler) applicationContext.getBean(errorHandlerName);
            EndpointPath endpointPath = connectionHandler.getClass().getAnnotation(EndpointPath.class);
            if (path.equals(endpointPath.value())) {
                errorHandles.add(errorHandler);
            }
        }
        if (errorHandles.size() == 1) {
            this.errorHandler = errorHandles.get(0);
        } else {
            throw new Exception("ErrorHandler for " + path + " path too match or no have");
        }
    }
}
