package com.framework.websocket.core.context;

import com.framework.websocket.core.message.Message;

/**
 * 发送响应数据的统一对外接口
 */
public interface ChannelContext {
    void sendMessage(Message message);

    void sendMessage(String message);
}
