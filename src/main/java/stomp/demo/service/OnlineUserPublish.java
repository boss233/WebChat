package stomp.demo.service;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * OnlineUserPublish
 */
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class OnlineUserPublish implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String command = "connect";

    @NonNull
    private String username;

    @NonNull
    private String sessionId;

    @Override
    public String toString() {
        return "OnlineUserPublish [sessionId=" + sessionId + ", username=" + username + "]";
    }
    
}