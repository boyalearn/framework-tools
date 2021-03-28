package com.framework.tool.websocket.endpoint;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import java.io.IOException;

public class DefaultEndpoint extends Endpoint {
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        try {
            CloseReason closeReason=new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY,"auth error");
            session.close(closeReason);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(true){
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String s) {
                System.out.println(s);
            }
        });
    }
}
