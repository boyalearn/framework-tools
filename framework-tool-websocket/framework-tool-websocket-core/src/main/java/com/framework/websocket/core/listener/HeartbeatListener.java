package com.framework.websocket.core.listener;

import com.framework.websocket.core.context.ChannelContext;
import com.framework.websocket.core.context.DefaultChannelContext;
import com.framework.websocket.core.event.CloseEvent;
import com.framework.websocket.core.event.ConnectionEvent;
import com.framework.websocket.core.thread.HeartbeatWorker;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class HeartbeatListener<Event> implements EventListener<Event> {

    private long delay = 2;

    private static ScheduledExecutorService HEART_POOL = Executors.newScheduledThreadPool(10);

    private Map<Session, Future> runnableMap = new ConcurrentHashMap<>();

    @Override
    public void onEvent(Event event) {
        if (event instanceof ConnectionEvent) {
            ChannelContext channelContext = ((ConnectionEvent) event).getChannelContext();
            Session session = ((DefaultChannelContext) channelContext).getSession();
            System.out.println(session.hashCode());

            startHeartBeat(channelContext);
        }

        if (event instanceof CloseEvent) {
            ChannelContext channelContext = ((CloseEvent) event).getChannelContext();
            Session session = ((DefaultChannelContext) channelContext).getSession();
            System.out.println(session.hashCode());
            stopHeartBeat(channelContext);
        }
    }

    public void startHeartBeat(ChannelContext channelContext) {
        DefaultChannelContext context = (DefaultChannelContext) channelContext;
        Session session = context.getSession();
        ScheduledFuture<?> future = HEART_POOL.scheduleWithFixedDelay(new HeartbeatWorker(context), this.delay, this.delay, TimeUnit.SECONDS);
        runnableMap.put(session, future);
    }

    public void stopHeartBeat(ChannelContext channelContext) {
        DefaultChannelContext context = (DefaultChannelContext) channelContext;
        Session session = context.getSession();
        Future future = runnableMap.get(session);
        if (null != future) {
            future.cancel(true);
            runnableMap.remove(session);
        }

    }
}
