package maestro.milagro.StorageServer.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class StorageController {
    @Autowired
    private final StorageService service;
    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestParam("file") MultipartFile file) throws UnauthorizedException, AuthException, BedCredentials, IOException, NoSuchAlgorithmException {
        String msg = service.saveFile(authToken, filename, file);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        String msg = service.deleteFile(authToken, filename);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.downloadFile(authToken, filename);
    }
    @PutMapping("/file")
    public ResponseEntity<String> editFilename(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestBody NewFileName name) throws UnauthorizedException, AuthException, BedCredentials {
        service.editFilename(authToken, filename, name.filename());
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ListUnit>> getList(@RequestHeader("auth-token") String authToken, @QueryParam("limit") int limit) throws UnauthorizedException, AuthException, BedCredentials {
        List<ListUnit> list = service.getAllWithLimit(authToken, limit);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
