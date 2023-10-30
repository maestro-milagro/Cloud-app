package maestro.milagro.StorageServer.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.QueryParam;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.MyFile;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.model.NewFileName;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StorageController {
    @Autowired
    StorageService service;
    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestParam("file") MultipartFile file) throws UnauthorizedException, AuthException, BedCredentials, IOException, NoSuchAlgorithmException {
        return service.saveFile(authToken, filename, file);
    }
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.deleteFile(authToken, filename);
    }
    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.downloadFile(authToken, filename);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ListUnit>> getList(@RequestHeader("auth-token") String authToken, @QueryParam("limit") int limit) throws UnauthorizedException, AuthException, BedCredentials {
        return service.getAllWithLimit(authToken, limit);
    }
    @PutMapping("/file")
    public ResponseEntity<String> editFilename(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestBody NewFileName name) throws UnauthorizedException, AuthException, BedCredentials {
        service.editFilename(authToken, filename, name.getFilename());
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }
}
