package maestro.milagro.UserService.controller;

import maestro.milagro.UserService.client.JWTClient;
import maestro.milagro.UserService.model.ResponseLog;
import maestro.milagro.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    JWTClient jwtClient;
    @PostMapping("/login")
    public ResponseEntity<ResponseLog> login(@RequestBody User user){
        return jwtClient.authToken(user);
    }
}
