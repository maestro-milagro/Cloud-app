package maestro.milagro.UserService.client;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.UserService.model.ResponseLog;
import maestro.milagro.UserService.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface JWTClient {
    @PostExchange("/new/token")
    ResponseEntity<ResponseLog> newUser(@RequestBody User user);

    @PostExchange("/old/token")
    ResponseEntity<ResponseLog> oldUser(@RequestBody User user) throws AuthException;

    @PostExchange("/logout")
    void logout(@RequestBody String accessToken) throws AuthException;
}
