package maestro.milagro.StorageServer.advice;

import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(BedCredentials.class)
    public static ResponseEntity<ResponseError> bcHandler(BedCredentials e){
        return new ResponseEntity<>(new ResponseError(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseError> ueHandler(UnauthorizedException e){
        return new ResponseEntity<>(new ResponseError(e.getMessage(), 401), HttpStatus.UNAUTHORIZED);
    }
}
