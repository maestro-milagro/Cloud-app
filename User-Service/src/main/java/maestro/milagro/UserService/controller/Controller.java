package maestro.milagro.UserService.controller;

import maestro.milagro.UserService.client.JWTClient;
import maestro.milagro.UserService.model.ResponseLog;
import maestro.milagro.UserService.model.User;
import maestro.milagro.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    JWTClient jwtClient;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<ResponseLog> login(@RequestBody User user) {
        if (userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()).isPresent()) {

        }
        return jwtClient.newUser(user);
    }
}
