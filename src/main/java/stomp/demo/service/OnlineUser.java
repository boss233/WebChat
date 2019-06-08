package stomp.demo.service;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OnlineUser
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {

    private Set<?> onlineUser;

    private int onlineUserCount;

}