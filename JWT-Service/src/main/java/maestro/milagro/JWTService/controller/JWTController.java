package maestro.milagro.JWTService.controller;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.JWTService.model.ResponseLog;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import maestro.milagro.JWTService.repository.TokenRepository;
import maestro.milagro.JWTService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {
    @Autowired
    AuthService authService;
    @Autowired
    TokenRepository redisRepository;
    @PostMapping("/new/token")
    public ResponseEntity<ResponseLog> newUser(@RequestBody User user){
        return new ResponseEntity<>(authService.login(user), HttpStatus.OK);
    }
    @PostMapping("/old/token")
    public ResponseEntity<ResponseLog> oldUser(@RequestBody User user) throws AuthException {
        return new ResponseEntity<>(authService.getOldToken(user), HttpStatus.OK);
    }
    @PostMapping("/refresh/Atoken")
    public ResponseEntity<ResponseLog> refreshAuthToken(@RequestBody String accessToken) throws AuthException {
        return new ResponseEntity<>(authService.getAccessToken(accessToken), HttpStatus.OK);
    }
    @PostMapping("/refresh/Rtoken")
    public ResponseEntity<ResponseLog> refreshRefToken(@RequestBody String accessToken) throws AuthException {
        return new ResponseEntity<>(authService.refresh(accessToken), HttpStatus.OK);
    }
    @PostMapping("/check")
    public void check(@RequestBody UserAndTokens userAndTokens){
//        System.out.println(userAndTokens.getUser());
//        System.out.println(userAndTokens.getRefreshToken());
//        redisRepository.add(userAndTokens.getUser(), userAndTokens.getRefreshToken());
//        System.out.println(redisRepository.findToken(userAndTokens.getUser()));
        System.out.println(userAndTokens.getUser());
        System.out.println(userAndTokens.getRefreshToken());
        redisRepository.save(userAndTokens);
        UserAndTokens s = userAndTokens;
        s.setAccessToken("2313");
        redisRepository.save(s);
        System.out.println(redisRepository.findAll());
    }

}
