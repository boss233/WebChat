package stomp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OnlineUserController {


    @Autowired
    private OnlineUserData onlineUserData;

    @Autowired
    private MessageSend sendService;

    @GetMapping(value = { "/", "" })
    public String toIndex() {
        return "index";
    }

    @MessageMapping("/userOnline")
    @SendTo("/topic/userLogin")
    public OnlineUserPublish onlineUserPublish(@Payload OnlineUserMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        String username = message.getName();
                
        onlineUserData.addUser(sessionId, username);

        return new OnlineUserPublish(username, sessionId);
    }


    @SubscribeMapping("/alreadyUser")
    public OnlineUser getALLOnlineUser() {
        return new OnlineUser(onlineUserData.getDataCopy(), onlineUserData.getUserCount());
    }

    @MessageMapping("/userMessage")
    public void sendUserMessage(@Payload UserMessage message) {

        sendService.sendUserMessage(message.getSendTo(), message);
    }
}