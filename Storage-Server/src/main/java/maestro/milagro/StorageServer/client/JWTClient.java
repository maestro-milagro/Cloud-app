package maestro.milagro.StorageServer.client;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.StorageServer.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface JWTClient {
    @PostExchange("/get/user")
    User getUser(@RequestBody String accessToken) throws AuthException;
}
