package maestro.milagro.StorageServer.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.ws.rs.QueryParam;
import maestro.milagro.StorageServer.model.File;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import maestro.milagro.StorageServer.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StorageController {
    @Autowired
    StorageRepository repository;
    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestHeader String authToken, @QueryParam("filename") String filename, @RequestBody File file){
        User user = new User("Sergey", "32");
        repository.save(new StoredUnit(filename, user, file));
        System.out.println(repository.findAll());
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader String authToken, @QueryParam("filename") String filename){
        repository.deleteByFilename(filename);
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }
}