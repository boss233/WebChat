package stomp.demo.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * OnlineUserData
 */
@Component
public class OnlineUserData {

    private ConcurrentHashMap<String, String> onlineUser;

    public OnlineUserData() {
        this.onlineUser = new ConcurrentHashMap<>();
    }

    public void addUser(String sessionId, String username) {
        onlineUser.put(sessionId, username);
    }

    public String getUsername(String sessionId) {
        return onlineUser.get(sessionId);
    }

    public void deleteUser(String sessionId) {
        onlineUser.remove(sessionId);
    }

    public boolean containsUser(String sessionId) {
        return onlineUser.containsKey(sessionId);
    }

    public Set<?> getDataCopy() {
        return onlineUser.entrySet();
    }

    public int getUserCount() {
        return onlineUser.size();
    }
    
}