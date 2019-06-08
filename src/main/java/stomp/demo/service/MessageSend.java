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

    // public void sendOnlineUser(String username, String sessionId) {
    //     OnlineUserPublish greeting = new OnlineUserPublish(username, sessionId);
    //     System.out.println("sendMessage: " + greeting);

    //     template.convertAndSend("/topic/userLogin", greeting);
        
    // }

    public void sendOfflineUser(String sessionId) {
        template.convertAndSend("/topic/userLogin", new OfflineUserPublish(sessionId));
    }

    // public void sendToUser(HelloMessage message, Principal principal) {
    //     template.convertAndSend("/topic/greeting", new Greeting("Hi, " + message.getName() + "!"));
    //     System.out.println(principal.getName());
    //     template.convertAndSendToUser(principal.getName(), "/queue/notice", new Greeting("User: Hi, " + message.getName() + "!"));
    // }
    
}