package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */

    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        System.out.println(msg);

        // add send message method.
        // For each active session, send a message
        onlineSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                System.out.println("An error occurred while trying to send the message: ");
                e.printStackTrace();
            }
        });

    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        //add on open connection.
        onlineSessions.put(session.getId(), session);
        sendMessageToAll(Message.jsonToStr(Message.MessageType.ENTER, "", "", onlineSessions.size()));
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //add send message
        Message message = JSON.parseObject(jsonStr, Message.class);
        sendMessageToAll(Message.jsonToStr(Message.MessageType.SPEAK, message.getUsername(), message.getMessage(), onlineSessions.size()));
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //add close connection
        onlineSessions.remove(session.getId());
        sendMessageToAll(Message.jsonToStr(Message.MessageType.LEAVE,"","", onlineSessions.size()));
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}



















