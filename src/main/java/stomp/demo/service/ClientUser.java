package stomp.demo.service;

import java.security.Principal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClientUser
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientUser implements Principal {

    private String name;

    @Override
    public String toString() {
        return "ClientUser [name=" + name + "]";
    }
}