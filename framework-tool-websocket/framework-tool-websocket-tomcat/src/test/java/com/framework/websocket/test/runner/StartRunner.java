package com.framework.websocket.test.runner;

import com.framework.websocket.DefaultWebSocketServer;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//@Component
public class StartRunner implements ApplicationRunner , ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DefaultWebSocketServer bean = applicationContext.getBean(DefaultWebSocketServer.class);
        System.out.println(bean);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
