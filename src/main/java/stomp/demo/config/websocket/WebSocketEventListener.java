package stomp.demo.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import stomp.demo.service.MessageSend;
import stomp.demo.service.OnlineUserData;

/**
 * WebSocketEventListener
 */
@Component
public class WebSocketEventListener {

    @Autowired
    private MessageSend messageSend;

    @Autowired
    private OnlineUserData onlineUserData;

    // @Autowired
    // private SimpUserRegistry userRegistry;


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {

    }


    @EventListener
    public void handleWebSocketConnectedListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("user connected: " + headerAccessor.getSessionId());
    }

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {

    }

    @EventListener
    public void handleWebSocketUnsubscribeListener(SessionUnsubscribeEvent event) {

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

        messageSend.sendOfflineUser(sessionId);

        System.out.println("disconnect: " + sessionId);
        onlineUserData.deleteUser(sessionId);

    }

}