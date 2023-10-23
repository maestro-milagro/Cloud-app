package maestro.milagro.UserService.advice;

import maestro.milagro.UserService.exceptions.BedCredentials;
import maestro.milagro.UserService.model.ResponseError;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BedCredentials.class)
    public ResponseEntity<ResponseError> bcHandler(BedCredentials e){
        return new ResponseEntity<>(new ResponseError(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }
}
