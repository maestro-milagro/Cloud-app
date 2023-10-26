package maestro.milagro.StorageServer.controller;

import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.QueryParam;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.File;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StorageController {
    @Autowired
    StorageService service;
    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestBody File file) throws UnauthorizedException, AuthException, BedCredentials {
        return service.saveFile(authToken, filename, file);
    }
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.deleteFile(authToken, filename);
    }
    @GetMapping("/file")
    public ResponseEntity<File> downloadFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.downloadFile(authToken, filename);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ListUnit>> getList(@RequestHeader("auth-token") String authToken, @QueryParam("limit") int limit) throws UnauthorizedException, AuthException, BedCredentials {
        return service.getAllWithLimit(authToken, limit);
    }
}
