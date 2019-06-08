package stomp.demo.config.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

/**
 * CustomWebSocketHandlerDecorator
 */
public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

    public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            String payload = msg.getPayload();

            if (payload.contains("DISCONNECT")) {

                System.out.println("avoid double disconnect");
                return;
            }
        }

        super.handleMessage(session, message);
    }

}