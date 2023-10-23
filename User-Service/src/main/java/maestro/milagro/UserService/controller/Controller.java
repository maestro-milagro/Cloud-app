package maestro.milagro.UserService.controller;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.UserService.client.JWTClient;
import maestro.milagro.UserService.exceptions.BedCredentials;
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
    public ResponseEntity<ResponseLog> login(@RequestBody User user) throws AuthException, BedCredentials {
        if(user.getLogin() == null || user.getPassword() == null){
            throw new BedCredentials("Отсутствуют логин или пароль");
        }
        if (userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()).isPresent()) {
            return jwtClient.oldUser(user);
        }
        return jwtClient.newUser(user);
    }
}
