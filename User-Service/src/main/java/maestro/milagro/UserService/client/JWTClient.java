package maestro.milagro.UserService.client;

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
    public ResponseEntity<ResponseLog> newUser(@RequestBody User user);
}
