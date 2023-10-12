package maestro.milagro.JWTService.controller;

import maestro.milagro.JWTService.model.ResponseLog;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import maestro.milagro.JWTService.repository.RedisRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {
    @Autowired
    RedisRepositoryImp redisRepository;
    @PostMapping("/token")
    public ResponseEntity<ResponseLog> authToken(@RequestBody User user){
        return new ResponseEntity<>(new ResponseLog("re"), HttpStatus.OK);
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
        System.out.println(redisRepository.findById(userAndTokens.getRefreshToken()));
    }
}
