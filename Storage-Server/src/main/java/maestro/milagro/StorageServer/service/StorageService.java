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
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
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
    public ResponseEntity<String> saveFile(String authToken, String filename, MultipartFile file) throws AuthException, UnauthorizedException, BedCredentials, IOException, NoSuchAlgorithmException {
        if(authToken == null || filename == null || file == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        byte[] data = file.getBytes();
        byte[] bytes = MessageDigest.getInstance("MD5").digest(data);
        String hash = new BigInteger(1, bytes).toString(16);
        repository.save(new StoredUnit(filename, user, new MyFile(hash, new Binary(BsonBinarySubType.BINARY,file.getBytes()))));
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
    public ResponseEntity<Resource> downloadFile(String authToken, String filename) throws BedCredentials, AuthException, UnauthorizedException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.getLogin() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        MyFile myFile = repository.findByFilenameAndUser(filename, user).get().getMyFile();
        Resource resource = new ByteArrayResource(myFile.getFile().getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; hash=\"" + myFile.getHash() + "\"")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
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
        if(list1.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        for (StoredUnit s : list1) {
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
        StoredUnit storedUnit = repository.findByFilenameAndUser(filename, user).get();
        storedUnit.setFilename(name);
        repository.save(storedUnit);
        repository.deleteByFilename(filename);
    }

}
