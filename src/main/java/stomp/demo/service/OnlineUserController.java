package stomp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OnlineUserController {

    @Autowired
    private SimpUserRegistry userRegistry;

    @Autowired
    private OnlineUserData onlineUserData;

    @GetMapping(value = { "/", "" })
    public String toIndex() {
        return "index";
    }

    @MessageMapping("/userOnline")
    @SendTo("/topic/userLogin")
    public OnlineUserPublish onlineUserPublish(@Payload OnlineUserMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        String username = message.getName();
        

        headerAccessor.setUser(new ClientUser(sessionId));
        onlineUserData.addUser(sessionId, username);

        return new OnlineUserPublish(username, sessionId);
    }


    @SubscribeMapping("/alreadyUser")
    public OnlineUser getALLOnlineUser() {
        return new OnlineUser(onlineUserData.getDataCopy(), onlineUserData.getUserCount());
    }


}