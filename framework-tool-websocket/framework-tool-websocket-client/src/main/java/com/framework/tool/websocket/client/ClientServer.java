package com.framework.tool.websocket.client;

public class ClientServer {
    private static String URL = "ws://localhost:8000/ws";

    public static void main(String[] args) {
        new WebSocketClient(URL);
    }
}
