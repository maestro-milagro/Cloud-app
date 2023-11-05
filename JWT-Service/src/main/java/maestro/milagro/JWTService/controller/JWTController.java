package maestro.milagro.JWTService.controller;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class JWTController {
    @Autowired
    private final AuthService authService;
    @Autowired
    private final TokenRepository redisRepository;
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
        return new ResponseEntity<>(authService.getAccessToken(accessToken.substring(7)), HttpStatus.OK);
    }
    @PostMapping("/refresh/Rtoken")
    public ResponseEntity<ResponseLog> refreshRefToken(@RequestBody String accessToken) throws AuthException {
        return new ResponseEntity<>(authService.refresh(accessToken.substring(7)), HttpStatus.OK);
    }
    @PostMapping("/get/user")
    public User getUser(@RequestBody String accessToken) throws AuthException {
        return authService.findUser(accessToken.substring(7));
    }
    @PostMapping("/logout")
    public void logout(@RequestBody String accessToken) throws AuthException {
        authService.logout(accessToken.substring(7));
    }

}
