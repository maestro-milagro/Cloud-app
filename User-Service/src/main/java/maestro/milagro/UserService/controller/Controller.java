package maestro.milagro.UserService.controller;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.UserService.client.JWTClient;
import maestro.milagro.UserService.exceptions.BedCredentials;
import maestro.milagro.UserService.model.ResponseLog;
import maestro.milagro.UserService.model.User;
import maestro.milagro.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    JWTClient jwtClient;

    UserRepository userRepository;
    @Autowired
    public Controller(JWTClient jwtClient, UserRepository userRepository){
        this.jwtClient = jwtClient;
        this.userRepository = userRepository;
    }
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
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String accessToken) throws AuthException{
        jwtClient.logout(accessToken);
        return new ResponseEntity<>("Success logout", HttpStatus.OK);
    }
    @GetMapping("/login")
    public ResponseEntity<String> get() {
        return new ResponseEntity<>("Success log", HttpStatus.OK);
    }
}
