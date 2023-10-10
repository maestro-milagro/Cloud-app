package maestro.milagro.JWTService.controller;

import maestro.milagro.JWTService.model.ResponseLog;
import maestro.milagro.JWTService.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {
    @PostMapping("/token")
    public ResponseEntity<ResponseLog> authToken(@RequestBody User user){
        return new ResponseEntity<>(new ResponseLog("re"), HttpStatus.OK);
    }
}
