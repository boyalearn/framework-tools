package com.framework.websocket.test.handler;

import com.framework.websocket.annotation.EndpointPath;
import com.framework.websocket.handler.ErrorHandler;
import org.springframework.stereotype.Component;

import javax.websocket.Session;

@EndpointPath("/ws")
@Component
public class DemoErrorHandler implements ErrorHandler {
    @Override
    public void handlingException(Session session, Throwable error) {

    }
}
