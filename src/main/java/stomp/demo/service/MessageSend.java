package stomp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * MessageSend
 */
@Service
public class MessageSend {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendOfflineUser(String sessionId) {
        template.convertAndSend("/topic/userLogin", new OfflineUserPublish(sessionId));
    }

    public void sendUserMessage(String sessionId, UserMessage message) {
        template.convertAndSendToUser(sessionId, "/queue/message", message);
    }
    
}