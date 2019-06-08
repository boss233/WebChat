package stomp.demo.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserMessage
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserMessage {

    private String sendFrom;

    private String sendTo;

    private String content;

    @Override
    public String toString() {
        return "UserMessage [content=" + content + ", sendFrom=" + sendFrom + ", sendTo=" + sendTo + "]";
    }
}