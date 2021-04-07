package com.framework.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.framework.websocket.annotation.CmdName;
import com.framework.websocket.annotation.EndpointPath;
import com.framework.websocket.context.ChannelContext;
import com.framework.websocket.context.SessionContext;
import com.framework.websocket.exception.ConnectionException;
import com.framework.websocket.handler.CommandHandler;
import com.framework.websocket.handler.CommandParser;
import com.framework.websocket.handler.ConnectionHandler;
import com.framework.websocket.handler.ErrorHandler;
import com.framework.websocket.handler.HeartbeatHandler;
import com.framework.websocket.session.DefaultSessionManager;
import com.framework.websocket.session.SessionManager;
import com.framework.websocket.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ConcurrentReferenceHashMap;
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
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DefaultWebSocketServer implements ApplicationContextAware, InitializingBean {

    private final static String PING = "ping";

    private final static String PONG = "pong";

    public static SessionManager sessionManager = new DefaultSessionManager();

    private static ApplicationContext applicationContext;

    private static Map<String, CommandHandler> commandMap = new ConcurrentReferenceHashMap<>();

    private static ConnectionHandler connectionHandler;

    private static CommandParser commandParser;

    private static ScheduledExecutorService HEART_POOL = Executors.newScheduledThreadPool(10);

    private HeartbeatHandler heartbeatHandler;

    private ErrorHandler errorHandler;

    private SessionContext sessionContext;

    private ScheduledFuture<?> scheduledFuture;


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
        this.sessionContext = new SessionContext(session);
        heartbeatHandler = new HeartbeatHandler(sessionContext);
        this.scheduledFuture = HEART_POOL.scheduleWithFixedDelay(heartbeatHandler, 2, 2, TimeUnit.SECONDS);
        try {
            this.connectionHandler.onOpen(channelContext);
            sessionManager.addSession(session);
            log.debug("one connection on open , session id is {}", session.getId());
        } catch (ConnectionException e) {
            throw new ConnectionException();
        }
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.debug("connection {} is close", session.getId());
        this.scheduledFuture.cancel(true);
        this.connectionHandler.onClose(sessionContext, closeReason);
        this.sessionManager.removeSession(session);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        log.debug("收到消息:{}", message);
        String cmd = "";
        try {
            cmd = commandParser.parse(JsonUtils.jsonToMessage(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Processing Exception");
        }

        if (PING.equals(cmd) || PONG.equals(cmd)) {
            heartbeatHandler.handler(cmd);
            return;
        }

        CommandHandler handler = commandMap.get(cmd);
        if (null == handler) {
            log.error("no cmd handler");
            return;
        }
        handler.handle(session, message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.debug("on error happen", error);
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
            EndpointPath endpointPath = commandHandler.getClass().getAnnotation(EndpointPath.class);
            if (path.equals(endpointPath.value())) {
                CmdName cmd = commandHandler.getClass().getAnnotation(CmdName.class);
                commandMap.put(cmd.value(), commandHandler);
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

        String[] CommandParserNames = applicationContext.getBeanNamesForType(CommandParser.class);
        List<CommandParser> commandParsers = new ArrayList<>();
        for (String CommandParserName : CommandParserNames) {
            CommandParser CommandParser = (CommandParser) applicationContext.getBean(CommandParserName);
            EndpointPath endpointPath = CommandParser.getClass().getAnnotation(EndpointPath.class);
            if (path.equals(endpointPath.value())) {
                commandParsers.add(CommandParser);
            }
        }
        if (commandParsers.size() == 1) {
            this.commandParser = commandParsers.get(0);
        } else {
            throw new Exception("CommandParser for " + path + " path too match or no have");
        }
    }
}
