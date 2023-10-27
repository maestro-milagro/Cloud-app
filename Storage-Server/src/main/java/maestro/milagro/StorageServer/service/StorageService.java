package maestro.milagro.StorageServer.service;

import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.QueryParam;
import maestro.milagro.StorageServer.client.JWTClient;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.File;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import maestro.milagro.StorageServer.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    StorageRepository repository;
    JWTClient jwtClient;
    @Autowired
    public StorageService(StorageRepository repository, JWTClient jwtClient){
        this.jwtClient = jwtClient;
        this.repository = repository;
    }
    public ResponseEntity<String> saveFile(String authToken, String filename, File file) throws AuthException, UnauthorizedException, BedCredentials {
        if(authToken == null || filename == null || file == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        repository.save(new StoredUnit(filename, user, file));
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }
    public ResponseEntity<String> deleteFile(String authToken, String filename) throws BedCredentials, AuthException, UnauthorizedException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        repository.deleteByFilename(filename);

        return new ResponseEntity<>("Success deleted", HttpStatus.OK);
    }
    public ResponseEntity<File> downloadFile(String authToken, String filename) throws BedCredentials, AuthException, UnauthorizedException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        File file = repository.findByFilenameAndUser(filename, user).get().getFile();
        return new ResponseEntity<>(file, HttpStatus.OK);
    }
    public ResponseEntity<List<ListUnit>> getAllWithLimit(String authToken, Integer limit) throws BedCredentials, UnauthorizedException, AuthException {
        if(authToken == null || limit == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        List<StoredUnit> list1 = repository.findByUser(user);
        List<ListUnit> list = new ArrayList<>();
        for (StoredUnit s: list1) {
            list.add(new ListUnit(s.getFilename(), s.getFile().getFile().getBytes().length));
        }
        if(limit > list.size()){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return  new ResponseEntity<>(list.subList(0, limit), HttpStatus.OK);
    }
    public void editFilename(String authToken, String filename, String name) throws BedCredentials, UnauthorizedException, AuthException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        repository.findByFilenameAndUser(filename, user).get().setFilename(name);
    }

}
