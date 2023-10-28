package maestro.milagro.StorageServer.service;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.StorageServer.client.JWTClient;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.MyFile;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import maestro.milagro.StorageServer.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<String> saveFile(String authToken, String filename, MyFile myFile) throws AuthException, UnauthorizedException, BedCredentials {
        if(authToken == null || filename == null || myFile == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        repository.save(new StoredUnit(filename, user, myFile));
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
    public ResponseEntity<String> downloadFile(String authToken, String filename) throws BedCredentials, AuthException, UnauthorizedException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        MyFile myFile = repository.findByFilenameAndUser(filename, user).get().getMyFile();
        return new ResponseEntity<>(myFile.getFile(), HttpStatus.OK);
//        return new ResponseEntity<>(myFile, HttpStatus.OK);
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
            list.add(new ListUnit(s.getFilename(), s.getMyFile().getFile().length()));
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
