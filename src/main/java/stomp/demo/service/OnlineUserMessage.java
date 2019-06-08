package stomp.demo.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * OnlineUserMessage
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OnlineUserMessage {

    private String name;

    @Override
    public String toString() {
        return "OnlineUserMessage [name=" + name + "]";
    }

}