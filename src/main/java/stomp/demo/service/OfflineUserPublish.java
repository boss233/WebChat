package stomp.demo.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * OfflineUserPublish
 */
@Setter
@Getter
@RequiredArgsConstructor
public class OfflineUserPublish {

    private String command = "disconnect";

    @NonNull
    private String sessionId;
}